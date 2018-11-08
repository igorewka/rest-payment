package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class PaymentT2 extends BasePaymentWithDetails {

    public PaymentT2(final UUID id,
                     final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final LocalDateTime created,
                     final boolean cancelled,
                     final Types.Details details) {
        super(id, amount, currency, debtorIban, creditorIban, created, cancelled, details);

        Util.checkApplicableCurrencies(currency, Types.Currency.USD);
    }

    public PaymentT2(final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final LocalDateTime created,
                     final boolean cancelled,
                     // there're clear issues with Optional design/implementation in Java comparing to e.g. Scala
                     // Java Optional is not designed to be used in class properties/fields,
                     // also Java Optional usage in method/constructor parameters is considered a bad practice,
                     // but Java don't provide default/optional method parameters as well
                     // there're not any 100% good solution to the mentioned before issues
                     // don't have huge experience with Java Optionals, would be great to discuss.
                     final Types.Details details) {
        this(null, amount, currency, debtorIban, creditorIban, created, cancelled, details);
    }

    public Optional<Types.Details> getDetails() {
        return Optional.ofNullable(details);
    }
}
