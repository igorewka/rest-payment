package eu.isakels.rest.reqresp;

import java.util.Objects;
import java.util.Set;

public class QueryPaymentWithParamsResp extends BasePaymentResp {

    private final Set<QueryPaymentResp> payments;

    private QueryPaymentWithParamsResp(
            final Set<QueryPaymentResp> payments,
            final String msg,
            final String error) {
        super(msg, error);
        this.payments = payments;
    }

    public static QueryPaymentWithParamsResp ofPayments(final Set<QueryPaymentResp> payments) {
        return new QueryPaymentWithParamsResp(payments, null, null);
    }

    public static QueryPaymentWithParamsResp ofError(final String error) {
        return new QueryPaymentWithParamsResp(null, null, error);
    }

    public Set<QueryPaymentResp> getPayments() {
        return payments;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final QueryPaymentWithParamsResp that = (QueryPaymentWithParamsResp) o;
        return Objects.equals(payments, that.payments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), payments);
    }
}
