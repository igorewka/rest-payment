package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentT3 extends BasePayment {

    private final Types.CreditorBankBic creditorBankBic;

    public PaymentT3(final UUID id,
                     final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final LocalDateTime createdDateTime,
                     final boolean cancelled,
                     final LocalDateTime cancelledDateTime,
                     final Types.Amount cancelFee,
                     final Types.CreditorBankBic creditorBankBic) {
        super(id, Types.PaymentType.TYPE3, amount, currency, debtorIban, creditorIban,
                createdDateTime, cancelled, cancelledDateTime, cancelFee);

        Util.checkApplicableCurrencies(currency, Types.Currency.EUR, Types.Currency.USD);

        this.creditorBankBic = (Types.CreditorBankBic) Util.requireNonNullNotBlank(creditorBankBic, "creditorBankBic is mandatory");
    }

    public PaymentT3(final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final LocalDateTime created,
                     final boolean cancelled,
                     final LocalDateTime cancelledDateTime,
                     final Types.Amount cancelFee,
                     final Types.CreditorBankBic creditorBankBic) {
        this(null, amount, currency, debtorIban, creditorIban, created, cancelled,
                cancelledDateTime, cancelFee, creditorBankBic);
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
                this.getCreatedDateTime(),
                true,
                LocalDateTime.now(),
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
                this.getCreatedDateTime(),
                false,
                null,
                null,
                this.getCreditorBankBic());
    }
}
