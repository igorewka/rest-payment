package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.reqresp.CreatePaymentReq;

public abstract class PaymentFactory {
    public static BasePayment forReq(final CreatePaymentReq req) {
        final BasePayment payment;
        switch (req.getType()) {
            case TYPE1:
                payment = new PaymentT1(new Types.Amount(req.getAmount()),
                        req.getCurrency(),
                        new Types.DebtorIban(req.getDebtorIban()),
                        new Types.CreditorIban(req.getCreditorIban()),
                        new Types.Details(req.getDetails()));
                break;
            case TYPE2:
                payment = new PaymentT2(new Types.Amount(req.getAmount()),
                        req.getCurrency(),
                        new Types.DebtorIban(req.getDebtorIban()),
                        new Types.CreditorIban(req.getCreditorIban()),
                        new Types.Details(req.getDetails()));
                break;
            case TYPE3:
                payment = new PaymentT3(new Types.Amount(req.getAmount()),
                        req.getCurrency(),
                        new Types.DebtorIban(req.getDebtorIban()),
                        new Types.CreditorIban(req.getCreditorIban()),
                        new Types.CreditorBankBic(req.getCreditorBankBic()));
                break;
            default:
                throw new RuntimeException("unknown payment type");
        }
        return payment;
    }
}
