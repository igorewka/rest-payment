package eu.isakels.rest.reqresp;

import java.util.Objects;
import java.util.UUID;

public abstract class BasePaymentResp {

    private final UUID id;
    private final String msg;
    private final String error;
    // TODO: add error code

    BasePaymentResp(final UUID id, final String msg, final String error) {
        this.id = id;
        this.msg = msg;
        this.error = error;
    }

    public UUID getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BasePaymentResp that = (BasePaymentResp) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(msg, that.msg) &&
                Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, msg, error);
    }
}
