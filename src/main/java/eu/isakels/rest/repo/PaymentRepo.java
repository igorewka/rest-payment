package eu.isakels.rest.repo;

import eu.isakels.rest.model.Constants;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.model.payment.Types;
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
    // Local cache must be added for coefficient fetching from real DB
    private final Map<Types.PaymentType, BigDecimal> coeffRepo = new ConcurrentHashMap<>();

    {
        coeffRepo.put(Types.PaymentType.TYPE1, new BigDecimal("0.05"));
        coeffRepo.put(Types.PaymentType.TYPE2, new BigDecimal("0.1"));
        coeffRepo.put(Types.PaymentType.TYPE3, new BigDecimal("0.15"));
    }

    public void create(final BasePayment payment) {
        paymentRepo.put(
                payment.getId().orElseThrow(() -> new RuntimeException(Constants.expectedIdMissing)),
                payment);
    }

    public Optional<BasePayment> getPayment(final UUID id) {
        return Optional.ofNullable(paymentRepo.get(id));
    }

    public void cancel(final BasePayment payment) {
        paymentRepo.put(
                payment.getId().orElseThrow(() -> new RuntimeException(Constants.expectedIdMissing)),
                payment);
    }

    public BigDecimal getCoeff(final Types.PaymentType type) {
        return coeffRepo.get(type);
    }

    public Set<BasePayment> query(final Map<String, ? extends Serializable> params) {
        return Set.copyOf(paymentRepo.values().stream()
                .filter((payment) ->
                        isNewerThan3Days(payment) && paramsCondition(payment, params))
                .collect(Collectors.toSet()));
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
