package eu.isakels.rest.model.reqresp;

import java.util.Objects;
import java.util.UUID;

public class CreatePaymentResp {

    private final UUID id;
    private final String error;

    public CreatePaymentResp(final UUID id, final String error) {
        this.id = id;
        this.error = error;
    }

    public CreatePaymentResp(final UUID id) {
        this(id, null);
    }

    public CreatePaymentResp(final String error) {
        this(null, error);
    }

    public UUID getId() {
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
