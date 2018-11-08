package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.reqresp.CreatePaymentReq;

public abstract class PaymentFactory {
    public static BasePayment ofReq(final CreatePaymentReq req) {
        final BasePayment payment;
        switch (req.getType()) {
            case TYPE1:
                payment = new PaymentT1(Types.Amount.ofObj(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofObj(req.getDebtorIban()),
                        Types.CreditorIban.ofObj(req.getCreditorIban()),
                        Types.Details.ofObj(req.getDetails()));
                break;
            case TYPE2:
                payment = new PaymentT2(Types.Amount.ofObj(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofObj(req.getDebtorIban()),
                        Types.CreditorIban.ofObj(req.getCreditorIban()),
                        Types.Details.ofObj(req.getDetails()));
                break;
            case TYPE3:
                payment = new PaymentT3(Types.Amount.ofObj(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofObj(req.getDebtorIban()),
                        Types.CreditorIban.ofObj(req.getCreditorIban()),
                        Types.CreditorBankBic.ofObj(req.getCreditorBankBic()));
                break;
            default:
                throw new RuntimeException("unknown payment type");
        }
        return payment;
    }
}
