package eu.isakels.rest.controller.dto;

import eu.isakels.rest.model.payment.Types;

import java.math.BigDecimal;
import java.util.UUID;

public class QueryPaymentResp extends BasePaymentRespWithCancelFee {

    private QueryPaymentResp(final UUID id,
                             final BigDecimal cancelFee,
                             final Types.Currency currency,
                             final String msg,
                             final String error) {
        super(id, cancelFee, currency, msg, error);
    }

    public static QueryPaymentResp ofId(final UUID id) {
        return new QueryPaymentResp(id, null, null, null, null);
    }

    public static QueryPaymentResp ofCancelFee(final UUID id, final BigDecimal cancelFee,
                                               final Types.Currency currency) {
        return new QueryPaymentResp(id, cancelFee, currency, null, null);
    }

    public static QueryPaymentResp ofError(final UUID id, final String error) {
        return new QueryPaymentResp(id, null, null, null, error);
    }
}
