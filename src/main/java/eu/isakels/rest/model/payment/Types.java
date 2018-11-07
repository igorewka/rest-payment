package eu.isakels.rest.model.payment;

import java.math.BigDecimal;

public abstract class Types {
    public enum Currency {EUR, USD, GBP}

    public enum PaymentType {TYPE1, TYPE2, TYPE3}

    public interface Value<T> {
        T getValue();
    }

    public static class Amount implements Value<BigDecimal> {
        private final BigDecimal value;

        public Amount(final BigDecimal value) {
            this.value = value;
        }

        public BigDecimal getValue() {
            return value;
        }
    }

    public static class DebtorIban implements Value<String> {
        private final String value;

        public DebtorIban(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class CreditorIban implements Value<String> {
        private final String value;

        public CreditorIban(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class Details implements Value<String> {
        private final String value;

        public Details(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class CreditorBankBic implements Value<String> {
        private final String value;

        public CreditorBankBic(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
