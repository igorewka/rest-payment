package eu.isakels.rest.dao.factory;

import eu.isakels.rest.misc.Constants;
import eu.isakels.rest.model.ModelTypes;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.model.payment.PaymentT1;
import eu.isakels.rest.model.payment.PaymentT2;
import eu.isakels.rest.model.payment.PaymentT3;
import eu.isakels.rest.repo.dto.PaymentDto;

public abstract class DaoPaymentFactory {

    public static BasePayment ofDto(final PaymentDto dto) {
        final BasePayment payment;
        switch (dto.getType()) {
            case TYPE1:
                payment = new PaymentT1(
                        dto.getId(),
                        ModelTypes.Amount.ofValue(dto.getAmount()),
                        dto.getCurrency(),
                        ModelTypes.DebtorIban.ofValue(dto.getDebtorIban()),
                        ModelTypes.CreditorIban.ofValue(dto.getCreditorIban()),
                        dto.getCreatedInstant(),
                        dto.isCancelled(),
                        dto.getCancelledInstant(),
                        ModelTypes.Amount.ofValue(dto.getCancelFee()),
                        ModelTypes.Details.ofValue(dto.getDetails()));
                break;
            case TYPE2:
                payment = new PaymentT2(
                        dto.getId(),
                        ModelTypes.Amount.ofValue(dto.getAmount()),
                        dto.getCurrency(),
                        ModelTypes.DebtorIban.ofValue(dto.getDebtorIban()),
                        ModelTypes.CreditorIban.ofValue(dto.getCreditorIban()),
                        dto.getCreatedInstant(),
                        dto.isCancelled(),
                        dto.getCancelledInstant(),
                        ModelTypes.Amount.ofValue(dto.getCancelFee()),
                        ModelTypes.Details.ofValue(dto.getDetails()));
                break;
            case TYPE3:
                payment = new PaymentT3(
                        dto.getId(),
                        ModelTypes.Amount.ofValue(dto.getAmount()),
                        dto.getCurrency(),
                        ModelTypes.DebtorIban.ofValue(dto.getDebtorIban()),
                        ModelTypes.CreditorIban.ofValue(dto.getCreditorIban()),
                        dto.getCreatedInstant(),
                        dto.isCancelled(),
                        dto.getCancelledInstant(),
                        ModelTypes.Amount.ofValue(dto.getCancelFee()),
                        ModelTypes.CreditorBankBic.ofValue(dto.getCreditorBankBic()));
                break;
            default:
                throw new RuntimeException(Constants.unknownPaymentType);
        }
        return payment;
    }
}
