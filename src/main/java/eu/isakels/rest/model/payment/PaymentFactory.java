package eu.isakels.rest.model.payment;

import eu.isakels.rest.controller.dto.CreatePaymentReq;
import eu.isakels.rest.model.ModelConstants;
import eu.isakels.rest.repo.dto.PaymentDto;

import java.time.Clock;
import java.time.Instant;

public abstract class PaymentFactory {

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
                throw new RuntimeException(ModelConstants.unknownPaymentType);
        }
        return payment;
    }

    public static BasePayment ofDto(final PaymentDto dto) {
        final BasePayment payment;
        switch (dto.getType()) {
            case TYPE1:
                payment = new PaymentT1(
                        dto.getId(),
                        Types.Amount.ofValue(dto.getAmount()),
                        dto.getCurrency(),
                        Types.DebtorIban.ofValue(dto.getDebtorIban()),
                        Types.CreditorIban.ofValue(dto.getCreditorIban()),
                        dto.getCreatedInstant(),
                        dto.isCancelled(),
                        dto.getCancelledInstant(),
                        Types.Amount.ofValue(dto.getCancelFee()),
                        Types.Details.ofValue(dto.getDetails()));
                break;
            case TYPE2:
                payment = new PaymentT2(
                        dto.getId(),
                        Types.Amount.ofValue(dto.getAmount()),
                        dto.getCurrency(),
                        Types.DebtorIban.ofValue(dto.getDebtorIban()),
                        Types.CreditorIban.ofValue(dto.getCreditorIban()),
                        dto.getCreatedInstant(),
                        dto.isCancelled(),
                        dto.getCancelledInstant(),
                        Types.Amount.ofValue(dto.getCancelFee()),
                        Types.Details.ofValue(dto.getDetails()));
                break;
            case TYPE3:
                payment = new PaymentT3(
                        dto.getId(),
                        Types.Amount.ofValue(dto.getAmount()),
                        dto.getCurrency(),
                        Types.DebtorIban.ofValue(dto.getDebtorIban()),
                        Types.CreditorIban.ofValue(dto.getCreditorIban()),
                        dto.getCreatedInstant(),
                        dto.isCancelled(),
                        dto.getCancelledInstant(),
                        Types.Amount.ofValue(dto.getCancelFee()),
                        Types.CreditorBankBic.ofValue(dto.getCreditorBankBic()));
                break;
            default:
                throw new RuntimeException(ModelConstants.unknownPaymentType);
        }
        return payment;
    }
}
