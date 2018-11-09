package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Constants;
import eu.isakels.rest.model.Util;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

public abstract class BasePayment {
    private final UUID id;
    private final Types.PaymentType type;
    private final Types.Amount amount;
    private final Types.Currency currency;
    private final Types.DebtorIban debtorIban;
    private final Types.CreditorIban creditorIban;
    private final Instant createdInstant;
    private final boolean cancelled;
    private final Instant cancelledInstant;
    private final Types.Amount cancelFee;

    // TODO: think about grouping some params
    BasePayment(final UUID id,
                // TODO: combine amount with currency
                final Types.PaymentType type,
                final Types.Amount amount,
                final Types.Currency currency,
                final Types.DebtorIban debtorIban,
                final Types.CreditorIban creditorIban,
                final Instant createdInstant,
                final boolean cancelled,
                final Instant cancelledInstant,
                final Types.Amount cancelFee) {
        this.id = id;
        this.type = type;
        this.amount = (Types.Amount) Util.requireNonNullPositive(amount,
                "amount is mandatory and must be positive");
        this.currency = Util.requireNonNull(currency, "currency is mandatory");
        this.debtorIban = (Types.DebtorIban) Util.requireNonNullNotBlank(debtorIban,
                "debtorIban is mandatory");
        this.creditorIban = (Types.CreditorIban) Util.requireNonNullNotBlank(creditorIban,
                "creditorIban is mandatory");
        this.createdInstant = Util.requireNonNull(createdInstant, "createdInstant is mandatory");
        this.cancelled = cancelled;
        this.cancelledInstant = cancelledInstant;
        this.cancelFee = cancelFee;
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

    public Instant getCreatedInstant() {
        return createdInstant;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Optional<Instant> getCancelledInstant() {
        return Optional.ofNullable(cancelledInstant);
    }

    public Optional<Types.Amount> getCancelFee() {
        return Optional.ofNullable(cancelFee);
    }

    public boolean isCancellable() {
        final var createdDate = createdInstant.atZone(ZoneId.of(Constants.timeZoneIdRiga)).toLocalDate();
        final var nowDate = Instant.now().atZone(ZoneId.of(Constants.timeZoneIdRiga)).toLocalDate();

        return !isCancelled() && nowDate.isEqual(createdDate);
    }

    public BigDecimal computeCancelFee(final BigDecimal coeff) {
        final var hoursPassed = Duration.between(createdInstant, Instant.now()).toHours();
        var res = new BigDecimal(hoursPassed).multiply(coeff);

        return Util.setScale(res);
    }

    public abstract BasePayment cancelledInstance(final BigDecimal cancelFee);

    public abstract BasePayment idInstance(final UUID id);
}
