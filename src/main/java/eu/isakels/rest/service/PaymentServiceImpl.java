package eu.isakels.rest.service;

import eu.isakels.rest.model.Constants;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Clock;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepo repo;
    private Clock clock;

    @Autowired
    public PaymentServiceImpl(final PaymentRepo repo, final Clock clock) {
        this.repo = repo;
        this.clock = clock;
    }

    @Override
    public UUID create(final BasePayment payment) {
        final var id = UUID.randomUUID();
        repo.create(payment.idInstance(id));

        return id;
    }

    @Override
    public CancelResult cancel(final UUID id) {
        final var paymentOpt = repo.getPayment(id);
        final var payment = paymentOpt.orElseThrow(
                () -> new RuntimeException(String.format(Constants.expectedPaymentNotFound, id)));

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
                () -> new RuntimeException(String.format(Constants.expectedPaymentNotFound, id)));

        return payment;
    }

    @Override
    public Set<BasePayment> queryWithParams(final Map<String, ? extends Serializable> params) {
        return repo.query(params);
    }
}
