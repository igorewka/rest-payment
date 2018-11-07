package eu.isakels.rest.model.reqresp;

import java.util.Objects;

public class CreatePaymentResp {
    // TODO: create type
    private final String id;
    // TODO: create type
    private final String error;

    public CreatePaymentResp(final String id, final String error) {
        this.id = id;
        this.error = error;
    }

    public CreatePaymentResp(final String id) {
        this(id, null);
    }

    public static CreatePaymentResp ofError(final String error) {
        return new CreatePaymentResp(null, error);
    }

    public String getId() {
        return id;
    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreatePaymentResp that = (CreatePaymentResp) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
