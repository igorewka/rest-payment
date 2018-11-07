package eu.isakels.rest.model;

import eu.isakels.rest.model.payment.*;
import eu.isakels.rest.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ModelTest {
    @Test
    public void paymentSuccessful() {
        {
            final var payment = PaymentFactory.forReq(TestUtil.paymentReqT1());
            Assert.assertTrue(payment instanceof PaymentT1);
        }
        {
            final var payment = PaymentFactory.forReq(TestUtil.paymentReqT2());
            Assert.assertTrue(payment instanceof PaymentT2);
        }
        {
            final var payment = PaymentFactory.forReq(TestUtil.paymentReqT3(Types.Currency.USD));
            Assert.assertTrue(payment instanceof PaymentT3);
        }
        {
            final var payment = PaymentFactory.forReq(TestUtil.paymentReqT3(Types.Currency.EUR));
            Assert.assertTrue(payment instanceof PaymentT3);
        }
    }

    @Test
    public void paymentT1Failing() {
        TestUtil.assertExc("amount", () -> new PaymentT1(
                null,
                Types.Currency.EUR,
                "DBTRIBAN",
                "CRDTRIBAN",
                "payment type1 details"));

        TestUtil.assertExc("amount", () -> new PaymentT1(
                new BigDecimal("-10.35"),
                Types.Currency.EUR,
                "DBTRIBAN",
                "CRDTRIBAN",
                "payment type1 details"));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                new BigDecimal("10.35"),
                null,
                "DBTRIBAN",
                "CRDTRIBAN",
                "payment type1 details"));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                new BigDecimal("10.35"),
                Types.Currency.USD,
                "DBTRIBAN",
                "CRDTRIBAN",
                "payment type1 details"));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                new BigDecimal("10.35"),
                Types.Currency.EUR,
                null,
                "CRDTRIBAN",
                "payment type1 details"));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                new BigDecimal("10.35"),
                Types.Currency.EUR,
                " ",
                "CRDTRIBAN",
                "payment type1 details"));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                new BigDecimal("10.35"),
                Types.Currency.EUR,
                "DBTRIBAN",
                null,
                "payment type1 details"));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                new BigDecimal("10.35"),
                Types.Currency.EUR,
                "DBTRIBAN",
                " ",
                "payment type1 details"));

        TestUtil.assertExc("details", () -> new PaymentT1(
                new BigDecimal("10.35"),
                Types.Currency.EUR,
                "DBTRIBAN",
                "CRDTRIBAN",
                null));

        TestUtil.assertExc("details", () -> new PaymentT1(
                new BigDecimal("10.35"),
                Types.Currency.EUR,
                "DBTRIBAN",
                "CRDTRIBAN",
                " "));
    }

    @Test
    public void paymentT2Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT2(
                new BigDecimal("10.35"),
                Types.Currency.EUR,
                "DBTRIBAN",
                "CRDTRIBAN",
                null));
    }

    @Test
    public void paymentT3Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT3(
                new BigDecimal("10.35"),
                Types.Currency.GBP,
                "DBTRIBAN",
                "CRDTRIBAN",
                "CRDTRBANKBIC"));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                new BigDecimal("10.35"),
                Types.Currency.EUR,
                "DBTRIBAN",
                "CRDTRIBAN",
                null));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                new BigDecimal("10.35"),
                Types.Currency.EUR,
                "DBTRIBAN",
                "CRDTRIBAN",
                " "));
    }
}