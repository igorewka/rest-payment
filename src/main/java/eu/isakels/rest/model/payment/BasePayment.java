package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public abstract class BasePayment {
    private final UUID id;
    private final Types.Amount amount;
    private final Types.Currency currency;
    private final Types.DebtorIban debtorIban;
    private final Types.CreditorIban creditorIban;
    private final LocalDateTime created;
    private final boolean cancelled;

    // TODO: think about converting some params into objects
    BasePayment(final UUID id,
                // TODO: combine amount with currency
                final Types.Amount amount,
                final Types.Currency currency,
                final Types.DebtorIban debtorIban,
                final Types.CreditorIban creditorIban, final LocalDateTime created, final boolean cancelled) {
        this.id = id;
        this.amount = (Types.Amount) Util.requireNonNullPositive(amount,
                "amount is mandatory and must be positive");
        this.currency = Util.requireNonNull(currency, "currency is mandatory");
        this.debtorIban = (Types.DebtorIban) Util.requireNonNullNotBlank(debtorIban,
                "debtorIban is mandatory");
        this.creditorIban = (Types.CreditorIban) Util.requireNonNullNotBlank(creditorIban,
                "creditorIban is mandatory");
        this.created = Util.requireNonNull(created, "created is mandatory");
        this.cancelled = cancelled;
    }

    public Optional<UUID> getId() {
        return Optional.ofNullable(id);
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

    public LocalDateTime getCreated() {
        return created;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
