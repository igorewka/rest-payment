package eu.isakels.rest.reqresp;

import java.math.BigDecimal;
import java.util.UUID;

public class CancelPaymentResp extends BasePaymentResp {

    private final BigDecimal cancelFee;

    private CancelPaymentResp(final UUID id,
                              final BigDecimal cancelFee,
                              final String msg,
                              final String error) {
        super(id, msg, error);
        this.cancelFee = cancelFee;
    }

    public static CancelPaymentResp ofMsg(final UUID id, final BigDecimal cancelFee, final String msg) {
        return new CancelPaymentResp(id, cancelFee, msg, null);
    }

    public static CancelPaymentResp ofError(final UUID id, final String error) {
        return new CancelPaymentResp(id, null, null, error);
    }

    public BigDecimal getCancelFee() {
        return cancelFee;
    }
}
