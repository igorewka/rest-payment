package eu.isakels.rest.service;

import eu.isakels.rest.model.payment.BasePayment;

public class CancelResult {
    private final BasePayment payment;
    private final boolean success;

    public CancelResult(final BasePayment payment, final boolean success) {
        this.payment = payment;
        this.success = success;
    }

    public BasePayment getPayment() {
        return payment;
    }

    public boolean isSuccess() {
        return success;
    }
}
