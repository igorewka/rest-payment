package eu.isakels.rest.model.payment;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Types {
    public enum Currency {EUR, USD, GBP}

    public enum PaymentType {TYPE1, TYPE2, TYPE3}

    public interface Value<T> {
        T getValue();
    }

    public static final class Amount implements Value<BigDecimal> {

        private final BigDecimal value;

        private Amount(final BigDecimal value) {
            this.value = value;
        }

        public static Amount ofValue(final BigDecimal value) {
            return value != null ? new Amount(value) : null;
        }

        public static Amount ofObj(final Amount obj) {
            return obj != null ? new Amount(obj.getValue()) : null;
        }

        @Override
        public BigDecimal getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Amount{" +
                    "value=" + value +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Amount amount = (Amount) o;
            return Objects.equals(value, amount.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static final class DebtorIban implements Value<String> {

        private final String value;

        private DebtorIban(final String value) {
            this.value = value;
        }

        public static DebtorIban ofValue(final String value) {
            return value != null ? new DebtorIban(value) : null;
        }

        public static DebtorIban ofObj(final DebtorIban obj) {
            return obj != null ? new DebtorIban(obj.getValue()) : null;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "DebtorIban{" +
                    "value='" + value + '\'' +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final DebtorIban that = (DebtorIban) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static final class CreditorIban implements Value<String> {

        private final String value;

        private CreditorIban(final String value) {
            this.value = value;
        }

        public static CreditorIban ofValue(final String value) {
            return value != null ? new CreditorIban(value) : null;
        }

        public static CreditorIban ofObj(final CreditorIban obj) {
            return obj != null ? new CreditorIban(obj.getValue()) : null;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "CreditorIban{" +
                    "value='" + value + '\'' +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final CreditorIban that = (CreditorIban) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static final class Details implements Value<String> {

        private final String value;

        private Details(final String value) {
            this.value = value;
        }

        public static Details ofValue(final String value) {
            return value != null ? new Details(value) : null;
        }

        public static Details ofObj(final Details obj) {
            return obj != null ? new Details(obj.getValue()) : null;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Details{" +
                    "value='" + value + '\'' +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Details details = (Details) o;
            return Objects.equals(value, details.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static final class CreditorBankBic implements Value<String> {

        private final String value;

        private CreditorBankBic(final String value) {
            this.value = value;
        }

        public static CreditorBankBic ofValue(final String value) {
            return value != null ? new CreditorBankBic(value) : null;
        }

        public static CreditorBankBic ofObj(final CreditorBankBic obj) {
            return obj != null ? new CreditorBankBic(obj.getValue()) : null;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "CreditorBankBic{" +
                    "value='" + value + '\'' +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final CreditorBankBic that = (CreditorBankBic) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
