package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.reqresp.CreatePaymentReq;

public abstract class PaymentFactory {
    public static BasePayment forReq(final CreatePaymentReq req) {
        final BasePayment payment;
        switch (req.getType()) {
            case TYPE1:
                payment = new PaymentT1(Types.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban()),
                        Types.CreditorIban.ofValue(req.getCreditorIban()),
                        Types.Details.ofValue(req.getDetails()));
                break;
            case TYPE2:
                payment = new PaymentT2(Types.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban()),
                        Types.CreditorIban.ofValue(req.getCreditorIban()),
                        Types.Details.ofValue(req.getDetails()));
                break;
            case TYPE3:
                payment = new PaymentT3(Types.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban()),
                        Types.CreditorIban.ofValue(req.getCreditorIban()),
                        Types.CreditorBankBic.ofValue(req.getCreditorBankBic()));
                break;
            default:
                throw new RuntimeException("unknown payment type");
        }
        return payment;
    }
}
