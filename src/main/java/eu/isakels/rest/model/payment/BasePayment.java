package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;
import java.util.Optional;

public abstract class BasePayment {
    // TODO: create type
    private final String id;
    // TODO: create type
    private final BigDecimal amount;
    private final Types.Currency currency;
    // TODO: create type
    private final String debtorIban;
    // TODO: create type
    private final String creditorIban;

    // TODO: think about converting some params into objects
    BasePayment(final String id,
                final BigDecimal amount,
                final Types.Currency currency,
                final String debtorIban,
                final String creditorIban) {
        this.id = id;
        this.amount = Util.requireNonNullPositive(amount, "amount is mandatory and must be positive");
        this.currency = Util.requireNonNull(currency, "currency is mandatory");
        this.debtorIban = Util.requireNonNullNotBlank(debtorIban, "debtorIban is mandatory");
        this.creditorIban = Util.requireNonNullNotBlank(creditorIban, "creditorIban is mandatory");
    }

    public Optional<String> getId() {
        return Optional.ofNullable(id);
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
}
