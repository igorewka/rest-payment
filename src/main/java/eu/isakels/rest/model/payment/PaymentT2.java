package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;
import java.util.Optional;

public class PaymentT2 extends BasePaymentWithDetails {

    public PaymentT2(final String id,
                     final BigDecimal amount,
                     final Types.Currency currency,
                     final String debtorIban,
                     final String creditorIban,
                     final String details) {
        super(id, amount, currency, debtorIban, creditorIban, details);

        Util.checkApplicableCurrencies(currency, Types.Currency.USD);
    }

    public PaymentT2(final BigDecimal amount,
                     final Types.Currency currency,
                     final String debtorIban,
                     final String creditorIban,
                     // there're clear issues with Optional design/implementation in Java comparing to e.g. Scala
                     // Java Optional is not designed to be used in class properties/fields,
                     // also Java Optional usage in method/constructor parameters is considered a bad practice,
                     // but Java don't provide default/optional method parameters as well
                     // there're not any 100% good solution to the mentioned before issues
                     // don't have huge experience with Java Optionals, would be great to discuss.
                     final String details) {
        this(null, amount, currency, debtorIban, creditorIban, details);
    }

    public Optional<String> getDetails() {
        return Optional.ofNullable(details);
    }
}
