package eu.isakels.rest.reqresp;

import java.util.Objects;
import java.util.UUID;

public abstract class BasePaymentRespWithId extends BasePaymentResp {

    private final UUID id;

    BasePaymentRespWithId(final UUID id, final String msg, final String error) {
        super(msg, error);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final BasePaymentRespWithId that = (BasePaymentRespWithId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
