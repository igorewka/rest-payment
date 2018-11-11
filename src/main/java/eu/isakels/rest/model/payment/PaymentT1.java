package eu.isakels.rest.model.payment;

import eu.isakels.rest.misc.Util;
import eu.isakels.rest.misc.Types;
import eu.isakels.rest.model.ModelTypes;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

public class PaymentT1 extends BasePaymentWithDetails {

    public PaymentT1(final UUID id,
                     final ModelTypes.Amount amount,
                     final Types.Currency currency,
                     final ModelTypes.DebtorIban debtorIban,
                     final ModelTypes.CreditorIban creditorIban,
                     final Instant createdInstant,
                     final boolean cancelled,
                     final Instant cancelledInstant,
                     final ModelTypes.Amount cancelFee,
                     final ModelTypes.Details details) {
        super(id, Types.PaymentType.TYPE1, amount, currency, debtorIban, creditorIban, createdInstant,
                cancelled, cancelledInstant, cancelFee, details);

        Util.checkApplicableCurrencies(currency, Types.Currency.EUR);
        ModelTypes.requireNonNullNotBlank(details, "details is mandatory");
    }

    public PaymentT1(final ModelTypes.Amount amount,
                     final Types.Currency currency,
                     final ModelTypes.DebtorIban debtorIban,
                     final ModelTypes.CreditorIban creditorIban,
                     final Instant createdInstant,
                     final boolean cancelled,
                     final Instant cancelledInstant,
                     final ModelTypes.Amount cancelFee,
                     final ModelTypes.Details details) {
        this(null, amount, currency, debtorIban, creditorIban, createdInstant, cancelled,
                cancelledInstant, cancelFee, details);
    }

    public ModelTypes.Details getDetails() {
        return details;
    }

    @Override
    public BasePayment cancelledInstance(final BigDecimal cancelFee, final Clock clock) {
        return new PaymentT1(
                this.getIdUnwrapped(),
                this.getAmount(),
                this.getCurrency(),
                this.getDebtorIban(),
                this.getCreditorIban(),
                this.getCreatedInstant(),
                true,
                Instant.now(clock),
                ModelTypes.Amount.ofValue(cancelFee),
                this.getDetails());
    }

    @Override
    public BasePayment idInstance(final UUID id) {
        return new PaymentT1(
                id,
                this.getAmount(),
                this.getCurrency(),
                this.getDebtorIban(),
                this.getCreditorIban(),
                this.getCreatedInstant(),
                false,
                null,
                null,
                this.getDetails());
    }
}
