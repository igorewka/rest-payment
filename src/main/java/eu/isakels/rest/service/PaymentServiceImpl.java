package eu.isakels.rest.service;

import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepo repo;


    @Override
    public UUID create(final BasePayment payment) {
        return repo.create(payment);
    }

    @Override
    public boolean cancel(final UUID id) {
        final var paymentOpt = repo.getPayment(id);
        var payment = paymentOpt.orElseThrow(
                () -> new RuntimeException(String.format("expected payment[%s] not found", id)));

        final boolean result;
        if (payment.isCancellable()) {
            var coeff = repo.getCoeff(payment.getType());
            var fee = payment.getCancelFee(coeff);
            repo.cancel(id, payment.cancelledInstance(fee));

            result = true;
        } else {
            result = false;
        }
        return result;
    }

}
