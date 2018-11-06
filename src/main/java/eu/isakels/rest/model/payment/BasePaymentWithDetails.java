package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;

public abstract class BasePaymentWithDetails extends BasePayment {

    // TODO: create type
    protected final String details;

    public BasePaymentWithDetails(final String id,
                                  final BigDecimal amount,
                                  final Types.Currency currency,
                                  final String debtorIban,
                                  final String creditorIban,
                                  final String details) {
        super(id, amount, currency, debtorIban, creditorIban);

        this.details = details;
    }
}
