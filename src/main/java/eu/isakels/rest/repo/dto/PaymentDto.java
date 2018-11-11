package eu.isakels.rest.repo.dto;

import eu.isakels.rest.model.ModelConstants;
import eu.isakels.rest.model.payment.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

// Simple DTO is intentionally used instead of class hierarchy,
// which would unnecessary complicate ORM configuration
@Entity(name = "payment")
public class PaymentDto {
    // final properties are impossible because of empty constructor required by JPA/Hibernate
    @Id
    private UUID id;
    private Types.PaymentType type;
    private BigDecimal amount;
    private Types.Currency currency;
    // Model types not used here because of unnecessary complicated ORM configuration
    private String debtorIban;
    private String creditorIban;
    private String details;
    private String creditorBankBic;
    private Instant createdInstant;
    private boolean cancelled;
    private Instant cancelledInstant;
    private BigDecimal cancelFee;

    // required by JPA/Hibernate
    public PaymentDto() {
    }

    public PaymentDto(final UUID id,
                      final Types.PaymentType type,
                      final BigDecimal amount,
                      final Types.Currency currency,
                      final String debtorIban,
                      final String creditorIban,
                      final String details,
                      final String creditorBankBic,
                      final Instant createdInstant,
                      final boolean cancelled,
                      final Instant cancelledInstant,
                      final BigDecimal cancelFee) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.debtorIban = debtorIban;
        this.creditorIban = creditorIban;
        this.details = details;
        this.creditorBankBic = creditorBankBic;
        this.createdInstant = createdInstant;
        this.cancelled = cancelled;
        this.cancelledInstant = cancelledInstant;
        this.cancelFee = cancelFee;
    }

    public UUID getId() {
        return id;
    }

    public Types.PaymentType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Types.Currency getCurrency() {
        return currency;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public String getDetails() {
        return details;
    }

    public String getCreditorBankBic() {
        return creditorBankBic;
    }

    public Instant getCreatedInstant() {
        return createdInstant;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Instant getCancelledInstant() {
        return cancelledInstant;
    }

    public BigDecimal getCancelFee() {
        return cancelFee;
    }

    public static PaymentDto ofPayment(final BasePayment payment) {
        final PaymentDto paymentDto;
        switch (payment.getType()) {
            case TYPE1:
                final var p1 = (PaymentT1) payment;
                paymentDto = new PaymentDto(
                        p1.getIdUnwrapped(),
                        p1.getType(),
                        p1.getAmount().getValue(),
                        p1.getCurrency(),
                        p1.getDebtorIban().getValue(),
                        p1.getCreditorIban().getValue(),
                        p1.getDetails().getValue(),
                        null,
                        p1.getCreatedInstant(),
                        p1.isCancelled(),
                        p1.getCancelledInstant().orElse(null),
                        p1.getCancelFee().map(val -> val.getValue()).orElse(null));
                break;
            case TYPE2:
                final var p2 = (PaymentT2) payment;
                paymentDto = new PaymentDto(
                        p2.getIdUnwrapped(),
                        p2.getType(),
                        p2.getAmount().getValue(),
                        p2.getCurrency(),
                        p2.getDebtorIban().getValue(),
                        p2.getCreditorIban().getValue(),
                        p2.getDetails().map(val -> val.getValue()).orElse(null),
                        null,
                        p2.getCreatedInstant(),
                        p2.isCancelled(),
                        p2.getCancelledInstant().orElse(null),
                        p2.getCancelFee().map(val -> val.getValue()).orElse(null));
                break;
            case TYPE3:
                final var p3 = (PaymentT3) payment;
                paymentDto = new PaymentDto(
                        p3.getIdUnwrapped(),
                        p3.getType(),
                        p3.getAmount().getValue(),
                        p3.getCurrency(),
                        p3.getDebtorIban().getValue(),
                        p3.getCreditorIban().getValue(),
                        null,
                        p3.getCreditorBankBic().getValue(),
                        p3.getCreatedInstant(),
                        p3.isCancelled(),
                        p3.getCancelledInstant().orElse(null),
                        p3.getCancelFee().map(val -> val.getValue()).orElse(null));
                break;
            default:
                throw new RuntimeException(ModelConstants.unknownPaymentType);
        }
        return paymentDto;
    }
}
