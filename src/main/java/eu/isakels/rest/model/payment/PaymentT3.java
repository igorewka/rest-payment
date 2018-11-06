package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;

public class PaymentT3 extends BasePayment {
    // TODO: create type
    private final String creditorBankBic;

    public PaymentT3(final String id,
                     final BigDecimal amount,
                     final Types.Currency currency,
                     final String debtorIban,
                     final String creditorIban,
                     final String creditorBankBic) {
        super(id, amount, currency, debtorIban, creditorIban);

        Util.checkApplicableCurrencies(currency, Types.Currency.EUR, Types.Currency.USD);

        this.creditorBankBic = Util.requireNonNullNotBlank(creditorBankBic, "creditorBankBic is mandatory");
    }

    public PaymentT3(final BigDecimal amount,
                     final Types.Currency currency,
                     final String debtorIban,
                     final String creditorIban,
                     final String creditorBankBic) {
        this(null, amount, currency, debtorIban, creditorIban, creditorBankBic);
    }

    public String getCreditorBankBic() {
        return creditorBankBic;
    }
}
