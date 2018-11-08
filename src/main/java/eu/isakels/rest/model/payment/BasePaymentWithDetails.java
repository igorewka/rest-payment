package eu.isakels.rest.model.payment;

import java.time.LocalDateTime;
import java.util.UUID;

abstract class BasePaymentWithDetails extends BasePayment {

    final Types.Details details;

    BasePaymentWithDetails(final UUID id,
                           final Types.Amount amount,
                           final Types.Currency currency,
                           final Types.DebtorIban debtorIban,
                           final Types.CreditorIban creditorIban,
                           final LocalDateTime created,
                           final boolean cancelled,
                           final Types.Details details) {
        super(id, amount, currency, debtorIban, creditorIban, created, cancelled);

        this.details = details;
    }
}
