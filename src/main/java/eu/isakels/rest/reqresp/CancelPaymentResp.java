package eu.isakels.rest.reqresp;

import eu.isakels.rest.model.payment.Types;

import java.math.BigDecimal;
import java.util.UUID;

public class CancelPaymentResp extends BasePaymentResp {

    private final BigDecimal cancelFee;
    private final Types.Currency currency;

    private CancelPaymentResp(final UUID id,
                              final BigDecimal cancelFee,
                              final Types.Currency currency,
                              final String msg,
                              final String error) {
        super(id, msg, error);
        this.cancelFee = cancelFee;
        this.currency = currency;
    }

    public static CancelPaymentResp ofMsg(final UUID id, final BigDecimal cancelFee,
                                          final Types.Currency currency, final String msg) {
        return new CancelPaymentResp(id, cancelFee, currency, msg, null);
    }

    public static CancelPaymentResp ofError(final UUID id, final String error) {
        return new CancelPaymentResp(id, null, null, null, error);
    }

    public BigDecimal getCancelFee() {
        return cancelFee;
    }

    public Types.Currency getCurrency() {
        return currency;
    }
}
