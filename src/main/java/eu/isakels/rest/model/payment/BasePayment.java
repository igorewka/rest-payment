package eu.isakels.rest.model.payment;

import eu.isakels.rest.misc.Types;
import eu.isakels.rest.model.ModelConstants;
import eu.isakels.rest.misc.Util;
import eu.isakels.rest.model.ModelTypes;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

public abstract class BasePayment {
    private final UUID id;
    private final Types.PaymentType type;
    private final ModelTypes.Amount amount;
    private final Types.Currency currency;
    private final ModelTypes.DebtorIban debtorIban;
    private final ModelTypes.CreditorIban creditorIban;
    private final Instant createdInstant;
    private final boolean cancelled;
    private final Instant cancelledInstant;
    private final ModelTypes.Amount cancelFee;

    // TODO: think about grouping some params
    BasePayment(final UUID id,
                final Types.PaymentType type,
                // TODO: combine amount with currency
                final ModelTypes.Amount amount,
                final Types.Currency currency,
                final ModelTypes.DebtorIban debtorIban,
                final ModelTypes.CreditorIban creditorIban,
                final Instant createdInstant,
                final boolean cancelled,
                // TODO: combine cancel related stuff with adding currency
                final Instant cancelledInstant,
                final ModelTypes.Amount cancelFee) {
        this.id = id;
        this.type = type;
        this.amount = (ModelTypes.Amount) ModelTypes.requireNonNullPositive(amount,
                "amount is mandatory and must be positive");
        this.currency = Util.requireNonNull(currency, "currency is mandatory");
        this.debtorIban = (ModelTypes.DebtorIban) ModelTypes.requireNonNullNotBlank(debtorIban,
                "debtorIban is mandatory");
        this.creditorIban = (ModelTypes.CreditorIban) ModelTypes.requireNonNullNotBlank(creditorIban,
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

    public ModelTypes.Amount getAmount() {
        return amount;
    }

    public Types.Currency getCurrency() {
        return currency;
    }

    public ModelTypes.DebtorIban getDebtorIban() {
        return debtorIban;
    }

    public ModelTypes.CreditorIban getCreditorIban() {
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

    public Optional<ModelTypes.Amount> getCancelFee() {
        return Optional.ofNullable(cancelFee);
    }

    public boolean isCancellable(final Clock clock) {
        // TODO: probably client's time zone should be used here, depends on requirements
        final var createdDate = createdInstant.atZone(ZoneId.of(ModelConstants.timeZoneIdRiga)).toLocalDate();
        final var nowDate = Instant.now(clock).atZone(ZoneId.of(ModelConstants.timeZoneIdRiga)).toLocalDate();

        return !isCancelled() && nowDate.isEqual(createdDate);
    }

    public BigDecimal computeCancelFee(final BigDecimal coeff, final Clock clock) {
        final var hoursPassed = Duration.between(createdInstant, Instant.now(clock)).toHours();
        var res = new BigDecimal(hoursPassed).multiply(coeff);

        return Util.setScale(res);
    }

    public abstract BasePayment cancelledInstance(final BigDecimal cancelFee, final Clock clock);

    public abstract BasePayment idInstance(final UUID id);

    public UUID getIdUnwrapped() {
        return this.getId().orElseThrow(() ->
                new RuntimeException(ModelConstants.expectedIdMissing));
    }

    public ModelTypes.Amount getCancelFeeUnwrapped() {
        return this.getCancelFee().orElseThrow(() ->
                new RuntimeException(ModelConstants.expectedCancelFeeMissing));
    }
}
