package eu.isakels.rest.model.payment;

import java.time.Instant;
import java.util.UUID;

abstract class BasePaymentWithDetails extends BasePayment {

    final Types.Details details;

    BasePaymentWithDetails(final UUID id,
                           final Types.PaymentType type,
                           final Types.Amount amount,
                           final Types.Currency currency,
                           final Types.DebtorIban debtorIban,
                           final Types.CreditorIban creditorIban,
                           final Instant createdInstant,
                           final boolean cancelled,
                           final Instant cancelledInstant,
                           final Types.Amount cancelFee,
                           final Types.Details details) {
        super(id, type, amount, currency, debtorIban, creditorIban, createdInstant, cancelled,
                cancelledInstant, cancelFee);

        this.details = details;
    }
}
