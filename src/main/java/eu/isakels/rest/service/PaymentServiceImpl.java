package eu.isakels.rest.service;

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
                () -> new RuntimeException(String.format("expected payment[%s] not found", id)));

        final CancelResult result;
        if (payment.isCancellable()) {
            var coeff = repo.getCoeff(payment.getType());
            var fee = payment.computeCancelFee(coeff);
            repo.cancel(payment.cancelledInstance(fee));

            result = new CancelResult(payment, true);
        } else {
            result = new CancelResult(payment, false);
        }
        return result;
    }
}
