package eu.isakels.rest.model.reqresp;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class CreatePaymentResp {
    // TODO: create type
    private final String id;

    // @JsonCreator is required for constructors with one parameter
    @JsonCreator
    public CreatePaymentResp(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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
