package eu.isakels.rest.controller.dto;

import eu.isakels.rest.model.payment.Types;

import java.math.BigDecimal;
import java.util.UUID;

public class CancelPaymentResp extends BasePaymentRespWithCancelFee {

    private CancelPaymentResp(final UUID id,
                              final BigDecimal cancelFee,
                              final Types.Currency currency,
                              final String msg,
                              final String error) {
        super(id, cancelFee, currency, msg, error);
    }

    public static CancelPaymentResp ofCancelFee(final UUID id, final BigDecimal cancelFee,
                                                final Types.Currency currency, final String msg) {
        return new CancelPaymentResp(id, cancelFee, currency, msg, null);
    }

    public static CancelPaymentResp ofMsg(final UUID id, final String msg) {
        return new CancelPaymentResp(id, null, null, msg, null);
    }

    public static CancelPaymentResp ofError(final UUID id, final String error) {
        return new CancelPaymentResp(id, null, null, null, error);
    }
}
