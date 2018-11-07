package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;

public class PaymentT1 extends BasePaymentWithDetails {

    public PaymentT1(final String id,
                     final BigDecimal amount,
                     final Types.Currency currency,
                     final String debtorIban,
                     final String creditorIban,
                     final String details) {
        super(id, amount, currency, debtorIban, creditorIban, details);

        Util.checkApplicableCurrencies(currency, Types.Currency.EUR);
        Util.requireNonNullNotBlank(details, "details is mandatory");
    }

    public PaymentT1(final BigDecimal amount,
                     final Types.Currency currency,
                     final String debtorIban,
                     final String creditorIban,
                     final String details) {
        this(null, amount, currency, debtorIban, creditorIban, details);
    }

    public String getDetails() {
        return details;
    }
}
