package eu.isakels.rest.controller.dto;

import java.util.Objects;

public abstract class BasePaymentResp {

    private final String msg;
    private final String error;
    // TODO: add error code

    BasePaymentResp(final String msg, final String error) {
        this.msg = msg;
        this.error = error;
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
        return Objects.equals(msg, that.msg) &&
                Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg, error);
    }
}
