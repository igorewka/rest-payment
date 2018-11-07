package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.reqresp.CreatePaymentReq;

public abstract class PaymentFactory {
    public static BasePayment forReq(final CreatePaymentReq req) {
        final BasePayment payment;
// TODO: fix all NPEs
        switch (req.getType()) {
            case TYPE1:
                payment = new PaymentT1(Types.Amount.ofValue(req.getAmount().getValue()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban().getValue()),
                        Types.CreditorIban.ofValue(req.getCreditorIban().getValue()),
                        Types.Details.ofValue(req.getDetails().getValue()));
                break;
            case TYPE2:
                payment = new PaymentT2(Types.Amount.ofValue(req.getAmount().getValue()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban().getValue()),
                        Types.CreditorIban.ofValue(req.getCreditorIban().getValue()),
                        Types.Details.ofValue(req.getDetails().getValue()));
                break;
            case TYPE3:
                payment = new PaymentT3(Types.Amount.ofValue(req.getAmount().getValue()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban().getValue()),
                        Types.CreditorIban.ofValue(req.getCreditorIban().getValue()),
                        Types.CreditorBankBic.ofValue(req.getCreditorBankBic().getValue()));
                break;
            default:
                throw new RuntimeException("unknown payment type");
        }
        return payment;
    }
}
