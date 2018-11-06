package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.CreatePaymentReq;

public abstract class PaymentFactory {
    public static BasePayment forReq(final CreatePaymentReq req) {
        final BasePayment payment;
        switch (req.getType()) {
            case TYPE1:
                payment = new PaymentT1(req.getAmount(),
                        req.getCurrency(),
                        req.getDebtorIban(),
                        req.getCreditorIban(),
                        req.getDetails());
                break;
            case TYPE2:
                payment = new PaymentT2(req.getAmount(),
                        req.getCurrency(),
                        req.getDebtorIban(),
                        req.getCreditorIban(),
                        req.getDetails());
                break;
            case TYPE3:
                payment = new PaymentT3(req.getAmount(),
                        req.getCurrency(),
                        req.getDebtorIban(),
                        req.getCreditorIban(),
                        req.getCreditorBankBic());
                break;
            default:
                throw new RuntimeException("unknown payment type");
        }
        return payment;
    }
}
