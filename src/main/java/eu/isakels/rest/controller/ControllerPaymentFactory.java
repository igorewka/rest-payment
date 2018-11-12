package eu.isakels.rest.controller;

import eu.isakels.rest.controller.dto.CreatePaymentReq;
import eu.isakels.rest.misc.Constants;
import eu.isakels.rest.model.ModelTypes;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.model.payment.PaymentT1;
import eu.isakels.rest.model.payment.PaymentT2;
import eu.isakels.rest.model.payment.PaymentT3;

import java.time.Clock;
import java.time.Instant;

public abstract class ControllerPaymentFactory {

    public static BasePayment ofReq(final CreatePaymentReq req, final Clock clock) {
        final BasePayment payment;
        switch (req.getType()) {
            case TYPE1:
                payment = new PaymentT1(
                        ModelTypes.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        ModelTypes.DebtorIban.ofValue(req.getDebtorIban()),
                        ModelTypes.CreditorIban.ofValue(req.getCreditorIban()),
                        Instant.now(clock),
                        false,
                        null,
                        null,
                        ModelTypes.Details.ofValue(req.getDetails()));
                break;
            case TYPE2:
                payment = new PaymentT2(
                        ModelTypes.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        ModelTypes.DebtorIban.ofValue(req.getDebtorIban()),
                        ModelTypes.CreditorIban.ofValue(req.getCreditorIban()),
                        Instant.now(clock),
                        false,
                        null,
                        null,
                        ModelTypes.Details.ofValue(req.getDetails()));
                break;
            case TYPE3:
                payment = new PaymentT3(
                        ModelTypes.Amount.ofValue(req.getAmount()),
                        req.getCurrency(),
                        ModelTypes.DebtorIban.ofValue(req.getDebtorIban()),
                        ModelTypes.CreditorIban.ofValue(req.getCreditorIban()),
                        Instant.now(clock),
                        false,
                        null,
                        null,
                        ModelTypes.CreditorBankBic.ofValue(req.getCreditorBankBic()));
                break;
            default:
                throw new RuntimeException(Constants.unknownPaymentType);
        }
        return payment;
    }

    private ControllerPaymentFactory() {
    }
}
