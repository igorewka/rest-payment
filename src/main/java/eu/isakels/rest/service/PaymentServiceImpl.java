package eu.isakels.rest.service;

import eu.isakels.rest.model.Constants;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepo repo;

    @Autowired
    public PaymentServiceImpl(final PaymentRepo repo) {
        this.repo = repo;
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
        var payment = paymentOpt.orElseThrow(
                () -> new RuntimeException(String.format(Constants.expectedPaymentNotFound, id)));

        final CancelResult result;
        if (payment.isCancellable()) {
            var coeff = repo.getCoeff(payment.getType());
            var fee = payment.computeCancelFee(coeff);
            var cancelledPayment = payment.cancelledInstance(fee);
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
        var payment = paymentOpt.orElseThrow(
                () -> new RuntimeException(String.format(Constants.expectedPaymentNotFound, id)));

        return payment;
    }
}
