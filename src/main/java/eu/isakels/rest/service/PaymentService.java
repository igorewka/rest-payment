package eu.isakels.rest.service;

import eu.isakels.rest.model.payment.BasePayment;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface PaymentService {

    UUID create(final BasePayment payment);

    CancelResult cancel(final UUID id);

    BasePayment query(final UUID id);

    Set<BasePayment> queryWithParams(final Map<String, ? extends Serializable> params);
}
