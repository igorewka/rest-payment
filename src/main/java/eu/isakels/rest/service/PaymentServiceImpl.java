package eu.isakels.rest.service;

import eu.isakels.rest.Constants;
import eu.isakels.rest.dao.PaymentDao;
import eu.isakels.rest.model.ModelConstants;
import eu.isakels.rest.model.Notification;
import eu.isakels.rest.model.payment.BasePayment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.time.Clock;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final Clock clock;
    private final RestTemplate restTemplate;
    private final PaymentDao dao;

    @Autowired
    public PaymentServiceImpl(final Clock clock,
                              final RestTemplate restTemplate,
                              final PaymentDao dao) {
        this.clock = clock;
        this.restTemplate = restTemplate;
        this.dao = dao;
    }

    @Override
    public BasePayment create(final BasePayment payment) {
        final var id = UUID.randomUUID();
        final BasePayment paymentWithId = payment.idInstance(id);
        dao.create(paymentWithId);

        return paymentWithId;
    }

    // TODO: create test
    // Could be moved into separate service as well
    // @Async could be used as well
    @Override
    public CompletableFuture notify(final BasePayment payment) {
        return CompletableFuture.runAsync(() -> {
            switch (payment.getType()) {
                case TYPE1:
                    notifyAndCreate(payment, Constants.type1ServiceUrl);
                    break;
                case TYPE2:
                    notifyAndCreate(payment, Constants.type2ServiceUrl);
                    break;
            }
        }).exceptionally((exc) -> {
            logger.error("notify future", exc);
            return null;
        });
    }

    @Override
    public BasePayment cancel(final UUID id) {
        final var paymentOpt = dao.query(id);
        // This logic depends on the fact that it is not normal to pass wrong ids
        final var payment = paymentOpt.orElseThrow(
                () -> new RuntimeException(String.format(ModelConstants.expectedPaymentMssing, id)));

        final BasePayment result;
        if (payment.isCancellable(clock)) {
            final var coeff = Constants.coefficients.get(payment.getType());
            final var fee = payment.computeCancelFee(coeff, clock);
            final var cancelledPayment = payment.cancelledInstance(fee, clock);
            dao.create(cancelledPayment);

            result = cancelledPayment;
        } else {
            result = payment;
        }
        return result;
    }

    @Override
    public BasePayment query(final UUID id) {
        final var paymentOpt = dao.query(id);
        // This logic depends on the fact that it is not normal to pass wrong ids
        final var payment = paymentOpt.orElseThrow(
                () -> new RuntimeException(String.format(ModelConstants.expectedPaymentMssing, id)));

        return payment;
    }

    @Override
    public Set<BasePayment> query(final Map<String, ? extends Serializable> params) {
        return dao.query(params);
    }

    private boolean notifyAndCreate(final BasePayment payment, final String serviceUrl) {
        final var respOpt = requestService(serviceUrl);
        final var success = respOpt.isPresent() && isRespSuccessful(respOpt.get());

        final UUID id = payment.getIdUnwrapped();
        if (success) {
            logger.info("payment[{}] notification succeeded", id);
        } else {
            logger.info("payment[{}] notification failed", id);
        }
        final var notif = new Notification(id, success);
        dao.create(notif);

        return success;
    }

    private boolean isRespSuccessful(final ResponseEntity<String> resp) {
        return HttpStatus.OK.equals(resp.getStatusCode());
    }

    private Optional<ResponseEntity<String>> requestService(final String url) {
        return Optional.ofNullable(restTemplate.getForEntity(url, String.class));
    }
}
