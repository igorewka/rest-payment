package eu.isakels.rest.reqresp;

import java.util.UUID;

public class CancelPaymentResp extends BasePaymentResp {

    private CancelPaymentResp(final UUID id, final String msg, final String error) {
        super(id, msg, error);
    }

    public static CancelPaymentResp ofMsg(final UUID id, final String msg) {
        return new CancelPaymentResp(id, msg, null);
    }

    public static CancelPaymentResp ofError(final UUID id, final String error) {
        return new CancelPaymentResp(id, null, error);
    }
}
