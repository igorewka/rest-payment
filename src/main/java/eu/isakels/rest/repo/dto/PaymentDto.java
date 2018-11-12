package eu.isakels.rest.repo.dto;

import eu.isakels.rest.misc.Types;

import javax.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private Types.PaymentType type;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
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
    @Version
    // For JPA/Hibernate internal use only
    private Long version;

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
                      final BigDecimal cancelFee,
                      final Long version) {
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
        this.version = version;
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

    public Long getVersion() {
        return version;
    }
}
