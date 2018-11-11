package eu.isakels.rest.repo;

import eu.isakels.rest.Constants;
import eu.isakels.rest.model.Notification;
import eu.isakels.rest.model.payment.BasePayment;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.Period;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// TODO: ask/switch to H2
// TODO: make async db/repo access with dedicated Executor
@Component
public class PaymentRepo {

    private final Map<UUID, BasePayment> paymentRepo = new ConcurrentHashMap<>();
    private final Map<UUID, Notification> notificationRepo = new ConcurrentHashMap<>();

    public void create(final BasePayment payment) {
        paymentRepo.put(payment.getIdUnwrapped(), payment);
    }

    public Optional<BasePayment> getPayment(final UUID id) {
        return Optional.ofNullable(paymentRepo.get(id));
    }

    public void cancel(final BasePayment payment) {
        paymentRepo.put(payment.getIdUnwrapped(), payment);
    }

    public Set<BasePayment> query(final Map<String, ? extends Serializable> params) {
        return Set.copyOf(paymentRepo.values().stream()
                .filter((payment) ->
                        isNewerThan3Days(payment) && paramsCondition(payment, params))
                .collect(Collectors.toSet()));
    }

    public void createNotification(final Notification notification) {
        notificationRepo.put(notification.getPaymentId(), notification);
    }

    private boolean paramsCondition(final BasePayment payment,
                                    final Map<String, ? extends Serializable> params) {
        final boolean cancelledCond;
        if (params.containsKey(Constants.cancelledParam)) {
            cancelledCond = payment.isCancelled() == (Boolean) params.get(Constants.cancelledParam);
        } else {
            cancelledCond = true;
        }

        final boolean amountGtCond;
        if (params.containsKey(Constants.amountGtParam)) {
            amountGtCond = payment.getAmount().getValue()
                    .compareTo((BigDecimal) params.get(Constants.amountGtParam)) > 0;
        } else {
            amountGtCond = true;
        }

        final boolean amountLtCond;
        if (params.containsKey(Constants.amountLtParam)) {
            amountLtCond = payment.getAmount().getValue()
                    .compareTo((BigDecimal) params.get(Constants.amountLtParam)) < 0;
        } else {
            amountLtCond = true;
        }

        return cancelledCond && amountGtCond && amountLtCond;
    }

    private boolean isNewerThan3Days(final BasePayment payment) {
        final var inst3DaysInThePast = Instant.now().minus(Period.ofDays(3));

        return payment.getCreatedInstant().isAfter(inst3DaysInThePast);
    }
}
