package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class PaymentT3 extends BasePayment {

    private final Types.CreditorBankBic creditorBankBic;

    public PaymentT3(final UUID id,
                     final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final Instant createdInstant,
                     final boolean cancelled,
                     final Instant cancelledInstant,
                     final Types.Amount cancelFee,
                     final Types.CreditorBankBic creditorBankBic) {
        super(id, Types.PaymentType.TYPE3, amount, currency, debtorIban, creditorIban,
                createdInstant, cancelled, cancelledInstant, cancelFee);

        Util.checkApplicableCurrencies(currency, Types.Currency.EUR, Types.Currency.USD);

        this.creditorBankBic = (Types.CreditorBankBic) Util.requireNonNullNotBlank(creditorBankBic, "creditorBankBic is mandatory");
    }

    public PaymentT3(final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final Instant createdInstant,
                     final boolean cancelled,
                     final Instant cancelledInstant,
                     final Types.Amount cancelFee,
                     final Types.CreditorBankBic creditorBankBic) {
        this(null, amount, currency, debtorIban, creditorIban, createdInstant, cancelled,
                cancelledInstant, cancelFee, creditorBankBic);
    }

    public Types.CreditorBankBic getCreditorBankBic() {
        return creditorBankBic;
    }

    @Override
    public BasePayment cancelledInstance(final BigDecimal cancelFee) {
        return new PaymentT3(
                this.getId().orElse(null),
                this.getAmount(),
                this.getCurrency(),
                this.getDebtorIban(),
                this.getCreditorIban(),
                this.getCreatedInstant(),
                true,
                Instant.now(),
                Types.Amount.ofValue(cancelFee),
                this.getCreditorBankBic());
    }

    @Override
    public BasePayment idInstance(final UUID id) {
        return new PaymentT3(
                id,
                this.getAmount(),
                this.getCurrency(),
                this.getDebtorIban(),
                this.getCreditorIban(),
                this.getCreatedInstant(),
                false,
                null,
                null,
                this.getCreditorBankBic());
    }
}
