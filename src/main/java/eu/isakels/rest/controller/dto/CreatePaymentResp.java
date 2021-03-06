package eu.isakels.rest.controller.dto;

import java.util.UUID;

public class CreatePaymentResp extends BasePaymentRespWithId {

    private CreatePaymentResp(final UUID id, final String msg, final String error) {
        super(id, msg, error);
    }

    public CreatePaymentResp(final UUID id) {
        this(id, null, null);
    }

    public CreatePaymentResp(final String error) {
        this(null, null, error);
    }
}
