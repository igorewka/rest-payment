package eu.isakels.rest.model;

import java.util.UUID;

public class NotificationInfo {
    private final UUID paymentId;
    private final boolean success;

    public NotificationInfo(final UUID paymentId, final boolean success) {
        this.paymentId = paymentId;
        this.success = success;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public boolean isSuccess() {
        return success;
    }
}
