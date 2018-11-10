package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class PaymentT2 extends BasePaymentWithDetails {

    public PaymentT2(final UUID id,
                     final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final Instant createdInstant,
                     final boolean cancelled,
                     final Instant cancelledInstant,
                     final Types.Amount cancelFee,
                     final Types.Details details) {
        super(id, Types.PaymentType.TYPE2, amount, currency, debtorIban, creditorIban, createdInstant,
                cancelled, cancelledInstant, cancelFee, details);

        Util.checkApplicableCurrencies(currency, Types.Currency.USD);
    }

    public PaymentT2(final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final Instant createdInstant,
                     final boolean cancelled,
                     final Instant cancelledInstant,
                     final Types.Amount cancelFee,
                     // there're clear issues with Optional design/implementation in Java comparing to e.g. Scala
                     // Java Optional is not designed to be used in class properties/fields,
                     // also Java Optional usage in method/constructor parameters is considered a bad practice,
                     // but Java don't provide default/optional method parameters as well
                     // there're not any 100% good solution to the mentioned before issues
                     // don't have huge experience with Java Optionals, would be great to discuss.
                     final Types.Details details) {
        this(null, amount, currency, debtorIban, creditorIban, createdInstant, cancelled,
                cancelledInstant, cancelFee, details);
    }

    public Optional<Types.Details> getDetails() {
        return Optional.ofNullable(details);
    }

    @Override
    public BasePayment cancelledInstance(final BigDecimal cancelFee, final Clock clock) {
        return new PaymentT2(
                this.getId().orElse(null),
                this.getAmount(),
                this.getCurrency(),
                this.getDebtorIban(),
                this.getCreditorIban(),
                this.getCreatedInstant(),
                true,
                Instant.now(clock),
                Types.Amount.ofValue(cancelFee),
                this.getDetails().orElse(null));
    }

    @Override
    public BasePayment idInstance(final UUID id) {
        return new PaymentT2(
                id,
                this.getAmount(),
                this.getCurrency(),
                this.getDebtorIban(),
                this.getCreditorIban(),
                this.getCreatedInstant(),
                false,
                null,
                null,
                this.getDetails().orElse(null));
    }
}
