package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class PaymentT1 extends BasePaymentWithDetails {

    public PaymentT1(final UUID id,
                     final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final Instant createdInstant,
                     final boolean cancelled,
                     final Instant cancelledInstant,
                     final Types.Amount cancelFee,
                     final Types.Details details) {
        super(id, Types.PaymentType.TYPE1, amount, currency, debtorIban, creditorIban, createdInstant,
                cancelled, cancelledInstant, cancelFee, details);

        Util.checkApplicableCurrencies(currency, Types.Currency.EUR);
        Util.requireNonNullNotBlank(details, "details is mandatory");
    }

    public PaymentT1(final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final Instant createdInstant,
                     final boolean cancelled,
                     final Instant cancelledInstant,
                     final Types.Amount cancelFee,
                     final Types.Details details) {
        this(null, amount, currency, debtorIban, creditorIban, createdInstant, cancelled,
                cancelledInstant, cancelFee, details);
    }

    public Types.Details getDetails() {
        return details;
    }

    @Override
    public BasePayment cancelledInstance(final BigDecimal cancelFee) {
        return new PaymentT1(
                this.getId().orElse(null),
                this.getAmount(),
                this.getCurrency(),
                this.getDebtorIban(),
                this.getCreditorIban(),
                this.getCreatedInstant(),
                true,
                Instant.now(),
                Types.Amount.ofValue(cancelFee),
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
