package eu.isakels.rest.service;

import eu.isakels.rest.model.payment.BasePayment;

public class CancelResult {
    private final BasePayment payment;
    private final boolean result;

    public CancelResult(final BasePayment payment, final boolean result) {
        this.payment = payment;
        this.result = result;
    }

    public BasePayment getPayment() {
        return payment;
    }

    public boolean isResult() {
        return result;
    }
}
