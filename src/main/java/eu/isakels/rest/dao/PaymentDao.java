package eu.isakels.rest.dao;

import eu.isakels.rest.model.Notification;
import eu.isakels.rest.model.payment.BasePayment;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PaymentDao {
    BasePayment create(final BasePayment payment);

    Notification create(final Notification notif);

    Optional<BasePayment> query(final UUID id);

    Set<BasePayment> query(final Map<String, ? extends Serializable> params);
}
