package eu.isakels.rest.controller.dto;

import eu.isakels.rest.model.payment.Types;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BasePaymentRespWithCancelFee extends BasePaymentRespWithId {

    private final BigDecimal cancelFee;
    private final Types.Currency currency;

    BasePaymentRespWithCancelFee(final UUID id,
                                 final BigDecimal cancelFee,
                                 final Types.Currency currency,
                                 final String msg,
                                 final String error) {
        super(id, msg, error);
        this.cancelFee = cancelFee;
        this.currency = currency;
    }

    public BigDecimal getCancelFee() {
        return cancelFee;
    }

    public Types.Currency getCurrency() {
        return currency;
    }
}
