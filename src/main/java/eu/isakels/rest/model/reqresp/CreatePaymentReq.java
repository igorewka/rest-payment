package eu.isakels.rest.model.reqresp;

import eu.isakels.rest.model.Util;
import eu.isakels.rest.model.payment.Types;

import java.util.Objects;

public class CreatePaymentReq {
    private final Types.PaymentType type;
    private final Types.Amount amount;
    private final Types.Currency currency;
    private final Types.DebtorIban debtorIban;
    private final Types.CreditorIban creditorIban;
    private final Types.Details details;
    private final Types.CreditorBankBic creditorBankBic;

    // TODO: think about converting some params into objects
    public CreatePaymentReq(final Types.PaymentType type,
                            final Types.Amount amount,
                            final Types.Currency currency,
                            final Types.DebtorIban debtorIban,
                            final Types.CreditorIban creditorIban,
                            final Types.Details details,
                            final Types.CreditorBankBic creditorBankBic) {
        this.type = Util.requireNonNull(type, "type is mandatory");
        this.amount = amount;
        this.currency = currency;
        this.debtorIban = debtorIban;
        this.creditorIban = creditorIban;
        this.details = details;
        this.creditorBankBic = creditorBankBic;
    }

    public Types.PaymentType getType() {
        return type;
    }

    public Types.Amount getAmount() {
        return amount;
    }

    public Types.Currency getCurrency() {
        return currency;
    }

    public Types.DebtorIban getDebtorIban() {
        return debtorIban;
    }

    public Types.CreditorIban getCreditorIban() {
        return creditorIban;
    }

    public Types.Details getDetails() {
        return details;
    }

    public Types.CreditorBankBic getCreditorBankBic() {
        return creditorBankBic;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreatePaymentReq that = (CreatePaymentReq) o;
        return type == that.type &&
                Objects.equals(amount, that.amount) &&
                currency == that.currency &&
                Objects.equals(debtorIban, that.debtorIban) &&
                Objects.equals(creditorIban, that.creditorIban) &&
                Objects.equals(details, that.details) &&
                Objects.equals(creditorBankBic, that.creditorBankBic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, amount, currency, debtorIban, creditorIban, details, creditorBankBic);
    }
}
