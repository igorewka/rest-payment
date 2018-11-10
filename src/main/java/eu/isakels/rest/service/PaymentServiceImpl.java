package eu.isakels.rest.service;

import eu.isakels.rest.Constants;
import eu.isakels.rest.model.ModelConstants;
import eu.isakels.rest.model.NotificationInfo;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.repo.PaymentRepo;
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

    private PaymentRepo repo;
    private Clock clock;
    private final RestTemplate restTemplate;

    @Autowired
    public PaymentServiceImpl(final PaymentRepo repo,
                              final Clock clock,
                              final RestTemplate restTemplate) {
        this.repo = repo;
        this.clock = clock;
        this.restTemplate = restTemplate;
    }

    @Override
    public UUID create(final BasePayment payment) {
        final var id = UUID.randomUUID();
        final BasePayment paymentWithId = payment.idInstance(id);
        repo.create(paymentWithId);

        notifyOnCreate(paymentWithId);

        return id;
    }

    @Override
    public CancelResult cancel(final UUID id) {
        final var paymentOpt = repo.getPayment(id);
        final var payment = paymentOpt.orElseThrow(
                () -> new RuntimeException(String.format(ModelConstants.expectedPaymentNotFound, id)));

        final CancelResult result;
        if (payment.isCancellable(clock)) {
            final var coeff = repo.getCoeff(payment.getType());
            final var fee = payment.computeCancelFee(coeff, clock);
            final var cancelledPayment = payment.cancelledInstance(fee, clock);
            repo.cancel(cancelledPayment);

            result = new CancelResult(cancelledPayment, true);
        } else {
            result = new CancelResult(payment, false);
        }
        return result;
    }

    @Override
    public BasePayment query(final UUID id) {
        final var paymentOpt = repo.getPayment(id);
        final var payment = paymentOpt.orElseThrow(
                () -> new RuntimeException(String.format(ModelConstants.expectedPaymentNotFound, id)));

        return payment;
    }

    @Override
    public Set<BasePayment> queryWithParams(final Map<String, ? extends Serializable> params) {
        return repo.query(params);
    }

    // TODO: make testable and create test
    private void notifyOnCreate(final BasePayment payment) {
        CompletableFuture.runAsync(() -> {
            switch (payment.getType()) {
                case TYPE1:
                    notifyAndCreate(payment, Constants.type1ServiceUrl);
                    break;
                case TYPE2:
                    notifyAndCreate(payment, Constants.type2ServiceUrl);
                    break;
            }
        }).exceptionally((exc) -> {
            logger.error("notifyOnCreate future", exc);
            return null;
        });
    }

    private void notifyAndCreate(final BasePayment payment, final String serviceUrl) {
        final var respOpt = requestService(serviceUrl);
        final var success = respOpt.isPresent() && isRespSuccessful(respOpt.get());

        final UUID id = payment.getIdUnwrapped();
        if (success) {
            logger.info("payment[{}] notification succeeded", id);
        } else {
            logger.info("payment[{}] notification failed", id);
        }
        var result = new NotificationInfo(id, success);
        repo.createNotification(result);
    }

    private boolean isRespSuccessful(final ResponseEntity<String> resp) {
        return HttpStatus.OK.equals(resp.getStatusCode());
    }

    private Optional<ResponseEntity<String>> requestService(final String url) {
        return Optional.ofNullable(restTemplate.getForEntity(url, String.class));
    }
}
