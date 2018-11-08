package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentT1 extends BasePaymentWithDetails {

    public PaymentT1(final UUID id,
                     final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final LocalDateTime created,
                     final boolean cancelled,
                     final Types.Details details) {
        super(id, amount, currency, debtorIban, creditorIban, created, cancelled, details);

        Util.checkApplicableCurrencies(currency, Types.Currency.EUR);
        Util.requireNonNullNotBlank(details, "details is mandatory");
    }

    public PaymentT1(final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final LocalDateTime created,
                     final boolean cancelled,
                     final Types.Details details) {
        this(null, amount, currency, debtorIban, creditorIban, created, cancelled, details);
    }

    public Types.Details getDetails() {
        return details;
    }
}
