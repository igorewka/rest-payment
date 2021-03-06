package eu.isakels.rest.model.payment;

import eu.isakels.rest.misc.Types;
import eu.isakels.rest.misc.Util;
import eu.isakels.rest.model.ModelTypes;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

public class PaymentT3 extends BasePayment {

    private final ModelTypes.CreditorBankBic creditorBankBic;

    public PaymentT3(final UUID id,
                     final ModelTypes.Amount amount,
                     final Types.Currency currency,
                     final ModelTypes.DebtorIban debtorIban,
                     final ModelTypes.CreditorIban creditorIban,
                     final Instant createdInstant,
                     final boolean cancelled,
                     final Instant cancelledInstant,
                     final ModelTypes.Amount cancelFee,
                     final ModelTypes.CreditorBankBic creditorBankBic,
                     final Long version) {
        super(id, Types.PaymentType.TYPE3, amount, currency, debtorIban, creditorIban,
                createdInstant, cancelled, cancelledInstant, cancelFee, version);

        Util.checkApplicableCurrencies(currency, Types.Currency.EUR, Types.Currency.USD);

        this.creditorBankBic = (ModelTypes.CreditorBankBic) ModelTypes.requireNonNullNotBlank(creditorBankBic, "creditorBankBic is mandatory");
    }

    public PaymentT3(final ModelTypes.Amount amount,
                     final Types.Currency currency,
                     final ModelTypes.DebtorIban debtorIban,
                     final ModelTypes.CreditorIban creditorIban,
                     final Instant createdInstant,
                     final boolean cancelled,
                     final Instant cancelledInstant,
                     final ModelTypes.Amount cancelFee,
                     final ModelTypes.CreditorBankBic creditorBankBic,
                     final Long version) {
        this(null, amount, currency, debtorIban, creditorIban, createdInstant, cancelled,
                cancelledInstant, cancelFee, creditorBankBic, version);
    }

    public ModelTypes.CreditorBankBic getCreditorBankBic() {
        return creditorBankBic;
    }

    @Override
    public BasePayment cancelledInstance(final BigDecimal cancelFee, final Clock clock) {
        return new PaymentT3(
                this.getIdUnwrapped(),
                this.getAmount(),
                this.getCurrency(),
                this.getDebtorIban(),
                this.getCreditorIban(),
                this.getCreatedInstant(),
                true,
                Instant.now(clock),
                ModelTypes.Amount.ofValue(cancelFee),
                this.getCreditorBankBic(),
                this.getVersion());
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
                this.getCreditorBankBic(),
                this.getVersion());
    }
}
