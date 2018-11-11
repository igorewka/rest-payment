package eu.isakels.rest.service;

import eu.isakels.rest.model.payment.BasePayment;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PaymentService {

    BasePayment create(final BasePayment payment);

    CompletableFuture notify(final BasePayment payment);

    BasePayment cancel(final UUID id);

    BasePayment query(final UUID id);

    Set<BasePayment> queryWithParams(final Map<String, ? extends Serializable> params);
}
