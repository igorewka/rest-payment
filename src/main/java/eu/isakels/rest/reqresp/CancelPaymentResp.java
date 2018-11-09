package eu.isakels.rest.reqresp;

import eu.isakels.rest.model.payment.Types;

import java.util.UUID;

public class CancelPaymentResp extends BasePaymentResp {

    private final Types.Amount cancelFee;

    private CancelPaymentResp(final UUID id,
                              final Types.Amount cancelFee,
                              final String msg,
                              final String error) {
        super(id, msg, error);
        this.cancelFee = cancelFee;
    }

    public static CancelPaymentResp ofMsg(final UUID id, final Types.Amount cancelFee, final String msg) {
        return new CancelPaymentResp(id, cancelFee, msg, null);
    }

    public static CancelPaymentResp ofError(final UUID id, final String error) {
        return new CancelPaymentResp(id, null, null, error);
    }

    public Types.Amount getCancelFee() {
        return cancelFee;
    }
}
