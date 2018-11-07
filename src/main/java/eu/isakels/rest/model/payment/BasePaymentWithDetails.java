package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;

abstract class BasePaymentWithDetails extends BasePayment {

    // TODO: create type
    final String details;

    BasePaymentWithDetails(final String id,
                           final BigDecimal amount,
                           final Types.Currency currency,
                           final String debtorIban,
                           final String creditorIban,
                           final String details) {
        super(id, amount, currency, debtorIban, creditorIban);

        this.details = details;
    }
}
