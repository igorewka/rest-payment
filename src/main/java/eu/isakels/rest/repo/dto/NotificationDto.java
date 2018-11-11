package eu.isakels.rest.repo.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "notification")
public class NotificationDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final UUID id;
    private final UUID paymentId;
    private final boolean success;

    private NotificationDto(final UUID id, final UUID paymentId, final boolean success) {
        this.id = id;
        this.paymentId = paymentId;
        this.success = success;
    }

    public NotificationDto(final UUID paymentId, final boolean success) {
        this(null, paymentId, success);
    }

    public UUID getId() {
        return id;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public boolean isSuccess() {
        return success;
    }
}
