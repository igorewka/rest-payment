package eu.isakels.rest.model;

import eu.isakels.rest.model.payment.Types;

import java.math.BigDecimal;
import java.util.Objects;

public class CreatePaymentReq {
    private final Types.PaymentType type;
    // TODO: create type
    private final BigDecimal amount;
    private final Types.Currency currency;
    // TODO: create type
    private final String debtorIban;
    // TODO: create type
    private final String creditorIban;
    // TODO: create type
    private final String details;
    // TODO: create type
    private final String creditorBankBic;

    // TODO: think about converting some params into objects
    public CreatePaymentReq(final Types.PaymentType type,
                            final BigDecimal amount,
                            final Types.Currency currency,
                            final String debtorIban,
                            final String creditorIban,
                            final String details,
                            final String creditorBankBic) {
        this.type = Objects.requireNonNull(type, "type is mandatory");
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

    public BigDecimal getAmount() {
        return amount;
    }

    public Types.Currency getCurrency() {
        return currency;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public String getDetails() {
        return details;
    }

    public String getCreditorBankBic() {
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
