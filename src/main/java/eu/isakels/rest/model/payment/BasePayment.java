package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public abstract class BasePayment {
    private final UUID id;
    private final Types.PaymentType type;
    private final Types.Amount amount;
    private final Types.Currency currency;
    private final Types.DebtorIban debtorIban;
    private final Types.CreditorIban creditorIban;
    private final LocalDateTime created;
    private final boolean cancelled;

    // TODO: think about converting some params into objects
    BasePayment(final UUID id,
                // TODO: combine amount with currency
                final Types.PaymentType type,
                final Types.Amount amount,
                final Types.Currency currency,
                final Types.DebtorIban debtorIban,
                final Types.CreditorIban creditorIban, final LocalDateTime created, final boolean cancelled) {
        this.id = id;
        this.type = type;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isCancellable() {
        final var createdDate = created.toLocalDate();
        final var nowDate = LocalDate.now();

        return !isCancelled() && nowDate.isAfter(createdDate);
    }

    public BigDecimal getCancelFee(final double coeff) {
        final var hoursPassed = Duration.between(created, LocalDateTime.now()).toHours();
        // TODO: check scale
        return new BigDecimal(hoursPassed * coeff);
    }

    public abstract BasePayment cancelledInstance(final BigDecimal fee);
}
