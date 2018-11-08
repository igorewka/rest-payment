package eu.isakels.rest.model.payment;

import eu.isakels.rest.reqresp.CreatePaymentReq;

import java.time.LocalDateTime;

public abstract class PaymentFactory {

    public static BasePayment ofReq(final CreatePaymentReq req) {
        final BasePayment payment;
        switch (req.getType()) {
            case TYPE1:
                payment = new PaymentT1(Types.PaymentType.TYPE1,
                        Types.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban()),
                        Types.CreditorIban.ofValue(req.getCreditorIban()),
                        LocalDateTime.now(),
                        false,
                        Types.Details.ofValue(req.getDetails()));
                break;
            case TYPE2:
                payment = new PaymentT2(Types.PaymentType.TYPE2,
                        Types.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban()),
                        Types.CreditorIban.ofValue(req.getCreditorIban()),
                        LocalDateTime.now(),
                        false,
                        Types.Details.ofValue(req.getDetails()));
                break;
            case TYPE3:
                payment = new PaymentT3(Types.PaymentType.TYPE3,
                        Types.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban()),
                        Types.CreditorIban.ofValue(req.getCreditorIban()),
                        LocalDateTime.now(),
                        false,
                        Types.CreditorBankBic.ofValue(req.getCreditorBankBic()));
                break;
            default:
                throw new RuntimeException("unknown payment type");
        }
        return payment;
    }
}
