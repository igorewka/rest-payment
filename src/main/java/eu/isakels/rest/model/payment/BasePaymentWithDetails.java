package eu.isakels.rest.model.payment;

import eu.isakels.rest.misc.Types;
import eu.isakels.rest.model.ModelTypes;

import java.time.Instant;
import java.util.UUID;

abstract class BasePaymentWithDetails extends BasePayment {

    final ModelTypes.Details details;

    BasePaymentWithDetails(final UUID id,
                           final Types.PaymentType type,
                           final ModelTypes.Amount amount,
                           final Types.Currency currency,
                           final ModelTypes.DebtorIban debtorIban,
                           final ModelTypes.CreditorIban creditorIban,
                           final Instant createdInstant,
                           final boolean cancelled,
                           final Instant cancelledInstant,
                           final ModelTypes.Amount cancelFee,
                           final ModelTypes.Details details,
                           final Long version) {
        super(id, type, amount, currency, debtorIban, creditorIban, createdInstant, cancelled,
                cancelledInstant, cancelFee, version);

        this.details = details;
    }
}
