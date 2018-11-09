package eu.isakels.rest.repo;

import eu.isakels.rest.model.Constants;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.model.payment.Types;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// TODO: ask/switch to H2
@Component
public class PaymentRepo {
    private final Map<UUID, BasePayment> paymentRepo = new ConcurrentHashMap<>();
    // Local cache must be added for coefficient fetching from real DB
    private final Map<Types.PaymentType, BigDecimal> coeffRepo = new ConcurrentHashMap<>();

    {
        coeffRepo.put(Types.PaymentType.TYPE1, new BigDecimal("0.05"));
        coeffRepo.put(Types.PaymentType.TYPE2, new BigDecimal("0.1"));
        coeffRepo.put(Types.PaymentType.TYPE3, new BigDecimal("0.15"));
    }

    public void create(final BasePayment payment) {
        paymentRepo.put(
                payment.getId().orElseThrow(() -> new RuntimeException(Constants.expectedIdMissing)),
                payment);
    }

    public Optional<BasePayment> getPayment(final UUID id) {
        return Optional.ofNullable(paymentRepo.get(id));
    }

    public void cancel(final BasePayment payment) {
        paymentRepo.put(
                payment.getId().orElseThrow(() -> new RuntimeException(Constants.expectedIdMissing)),
                payment);
    }

    public BigDecimal getCoeff(final Types.PaymentType type) {
        return coeffRepo.get(type);
    }
}
