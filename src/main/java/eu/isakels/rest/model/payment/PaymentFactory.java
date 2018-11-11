package eu.isakels.rest.model.payment;

import eu.isakels.rest.controller.dto.CreatePaymentReq;

import java.time.Clock;
import java.time.Instant;

public abstract class PaymentFactory {

    // Clock is required only for testing, that's a slight smell of course
    public static BasePayment ofReq(final CreatePaymentReq req, final Clock clock) {
        final BasePayment payment;
        switch (req.getType()) {
            case TYPE1:
                payment = new PaymentT1(
                        Types.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban()),
                        Types.CreditorIban.ofValue(req.getCreditorIban()),
                        Instant.now(clock),
                        false,
                        null,
                        null,
                        Types.Details.ofValue(req.getDetails()));
                break;
            case TYPE2:
                payment = new PaymentT2(
                        Types.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban()),
                        Types.CreditorIban.ofValue(req.getCreditorIban()),
                        Instant.now(clock),
                        false,
                        null,
                        null,
                        Types.Details.ofValue(req.getDetails()));
                break;
            case TYPE3:
                payment = new PaymentT3(
                        Types.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        Types.DebtorIban.ofValue(req.getDebtorIban()),
                        Types.CreditorIban.ofValue(req.getCreditorIban()),
                        Instant.now(clock),
                        false,
                        null,
                        null,
                        Types.CreditorBankBic.ofValue(req.getCreditorBankBic()));
                break;
            default:
                throw new RuntimeException("unknown payment type");
        }
        return payment;
    }
}
