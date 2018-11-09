package eu.isakels.rest.service;

import eu.isakels.rest.model.payment.BasePayment;

import java.util.UUID;

public interface PaymentService {

    UUID create(final BasePayment payment);

    CancelResult cancel(final UUID id);

    BasePayment query(UUID id);
}
