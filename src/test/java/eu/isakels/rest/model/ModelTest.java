package eu.isakels.rest.model;

import eu.isakels.rest.model.payment.*;
import eu.isakels.rest.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;

public class ModelTest {
    @Test
    public void paymentSuccessful() {
        final var clock = Clock.systemDefaultZone();
        {
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT1(), clock);
            Assert.assertEquals(Types.PaymentType.TYPE1, payment.getType());
        }
        {
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT2(), clock);
            Assert.assertEquals(Types.PaymentType.TYPE2, payment.getType());
        }
        {
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT3(Types.Currency.USD), clock);
            Assert.assertEquals(Types.PaymentType.TYPE3, payment.getType());
        }
        {
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT3(Types.Currency.EUR), clock);
            Assert.assertEquals(Types.PaymentType.TYPE3, payment.getType());
        }
    }

    @Test
    public void paymentT1Failing() {
        TestUtil.assertExc("amount", () -> new PaymentT1(
                null,
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("amount", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("-10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                null,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.USD,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                null,
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue(" "),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                null,
                Instant.now(),
                false,
                null,
                null,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue(" "),
                Instant.now(),
                false,
                null,
                null,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("details", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                null));

        TestUtil.assertExc("details", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                Types.Details.ofValue(" ")));
    }

    @Test
    public void paymentT2Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT2(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                null));
    }

    @Test
    public void paymentT3Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT3(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.GBP,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                Types.CreditorBankBic.ofValue("CRDTRBANKBIC")));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                null));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                Types.CreditorBankBic.ofValue(" ")));
    }
}