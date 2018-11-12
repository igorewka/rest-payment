package eu.isakels.rest.dao.factory;

import eu.isakels.rest.misc.Constants;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.model.payment.PaymentT1;
import eu.isakels.rest.model.payment.PaymentT2;
import eu.isakels.rest.model.payment.PaymentT3;
import eu.isakels.rest.repo.dto.PaymentDto;

public abstract class PaymentDtoFactory {

    public static PaymentDto ofPayment(final BasePayment payment) {
        final PaymentDto paymentDto;
        switch (payment.getType()) {
            case TYPE1:
                final var p1 = (PaymentT1) payment;
                paymentDto = new PaymentDto(
                        p1.getIdUnwrapped(),
                        p1.getType(),
                        p1.getAmount().getValue(),
                        p1.getCurrency(),
                        p1.getDebtorIban().getValue(),
                        p1.getCreditorIban().getValue(),
                        p1.getDetails().getValue(),
                        null,
                        p1.getCreatedInstant(),
                        p1.isCancelled(),
                        p1.getCancelledInstant().orElse(null),
                        p1.getCancelFee().map(val -> val.getValue()).orElse(null));
                break;
            case TYPE2:
                final var p2 = (PaymentT2) payment;
                paymentDto = new PaymentDto(
                        p2.getIdUnwrapped(),
                        p2.getType(),
                        p2.getAmount().getValue(),
                        p2.getCurrency(),
                        p2.getDebtorIban().getValue(),
                        p2.getCreditorIban().getValue(),
                        p2.getDetails().map(val -> val.getValue()).orElse(null),
                        null,
                        p2.getCreatedInstant(),
                        p2.isCancelled(),
                        p2.getCancelledInstant().orElse(null),
                        p2.getCancelFee().map(val -> val.getValue()).orElse(null));
                break;
            case TYPE3:
                final var p3 = (PaymentT3) payment;
                paymentDto = new PaymentDto(
                        p3.getIdUnwrapped(),
                        p3.getType(),
                        p3.getAmount().getValue(),
                        p3.getCurrency(),
                        p3.getDebtorIban().getValue(),
                        p3.getCreditorIban().getValue(),
                        null,
                        p3.getCreditorBankBic().getValue(),
                        p3.getCreatedInstant(),
                        p3.isCancelled(),
                        p3.getCancelledInstant().orElse(null),
                        p3.getCancelFee().map(val -> val.getValue()).orElse(null));
                break;
            default:
                throw new RuntimeException(Constants.unknownPaymentType);
        }
        return paymentDto;
    }

    private PaymentDtoFactory() {
    }
}
