package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentT3 extends BasePayment {

    private final Types.CreditorBankBic creditorBankBic;

    public PaymentT3(final UUID id,
                     final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final LocalDateTime created,
                     final boolean cancelled,
                     final Types.CreditorBankBic creditorBankBic) {
        super(id, amount, currency, debtorIban, creditorIban, created, cancelled);

        Util.checkApplicableCurrencies(currency, Types.Currency.EUR, Types.Currency.USD);

        this.creditorBankBic = (Types.CreditorBankBic) Util.requireNonNullNotBlank(creditorBankBic, "creditorBankBic is mandatory");
    }

    public PaymentT3(final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final LocalDateTime created,
                     final boolean cancelled,
                     final Types.CreditorBankBic creditorBankBic) {
        this(null, amount, currency, debtorIban, creditorIban, created, cancelled, creditorBankBic);
    }

    public Types.CreditorBankBic getCreditorBankBic() {
        return creditorBankBic;
    }
}
