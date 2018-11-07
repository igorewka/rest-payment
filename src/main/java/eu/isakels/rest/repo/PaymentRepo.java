package eu.isakels.rest.repo;

import eu.isakels.rest.model.payment.BasePayment;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// TODO: ask/switch to H2
public class PaymentRepo {
    private static final Map<String, BasePayment> repo = new ConcurrentHashMap<>();

    public static String create(BasePayment payment) {
        var id = UUID.randomUUID().toString();
        repo.put(id, payment);

        return id;
    }
}
