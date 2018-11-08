package eu.isakels.rest.model.payment;

import eu.isakels.rest.model.Util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentT1 extends BasePaymentWithDetails {

    public PaymentT1(final UUID id,
                     final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final LocalDateTime created,
                     final boolean cancelled,
                     final LocalDateTime cancelledDateTime,
                     final Types.Amount cancelFee,
                     final Types.Details details) {
        super(id, Types.PaymentType.TYPE1, amount, currency, debtorIban, creditorIban, created,
                cancelled, cancelledDateTime, cancelFee, details);

        Util.checkApplicableCurrencies(currency, Types.Currency.EUR);
        Util.requireNonNullNotBlank(details, "details is mandatory");
    }

    public PaymentT1(final Types.Amount amount,
                     final Types.Currency currency,
                     final Types.DebtorIban debtorIban,
                     final Types.CreditorIban creditorIban,
                     final LocalDateTime created,
                     final boolean cancelled,
                     final LocalDateTime cancelledDateTime,
                     final Types.Amount cancelFee,
                     final Types.Details details) {
        this(null, amount, currency, debtorIban, creditorIban, created, cancelled,
                cancelledDateTime, cancelFee, details);
    }

    public Types.Details getDetails() {
        return details;
    }

    @Override
    public BasePayment cancelledInstance(final BigDecimal cancelFee) {
        return new PaymentT1(
                this.getId().orElse(null),
                this.getAmount(),
                this.getCurrency(),
                this.getDebtorIban(),
                this.getCreditorIban(),
                this.getCreatedDateTime(),
                true,
                LocalDateTime.now(),
                Types.Amount.ofValue(cancelFee),
                this.getDetails());
    }

    @Override
    public BasePayment idInstance(final UUID id) {
        return new PaymentT1(
                id,
                this.getAmount(),
                this.getCurrency(),
                this.getDebtorIban(),
                this.getCreditorIban(),
                this.getCreatedDateTime(),
                false,
                null,
                null,
                this.getDetails());
    }
}
