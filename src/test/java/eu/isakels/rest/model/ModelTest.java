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
                new Types.DebtorIban("DBTRIBAN"),
                new Types.CreditorIban("CRDTRIBAN"),
                new Types.Details("payment type1 details")));

        TestUtil.assertExc("amount", () -> new PaymentT1(
                new Types.Amount(new BigDecimal("-10.35")),
                Types.Currency.EUR,
                new Types.DebtorIban("DBTRIBAN"),
                new Types.CreditorIban("CRDTRIBAN"),
                new Types.Details("payment type1 details")));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                new Types.Amount(new BigDecimal("10.35")),
                null,
                new Types.DebtorIban("DBTRIBAN"),
                new Types.CreditorIban("CRDTRIBAN"),
                new Types.Details("payment type1 details")));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                new Types.Amount(new BigDecimal("10.35")),
                Types.Currency.USD,
                new Types.DebtorIban("DBTRIBAN"),
                new Types.CreditorIban("CRDTRIBAN"),
                new Types.Details("payment type1 details")));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                new Types.Amount(new BigDecimal("10.35")),
                Types.Currency.EUR,
                null,
                new Types.CreditorIban("CRDTRIBAN"),
                new Types.Details("payment type1 details")));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                new Types.Amount(new BigDecimal("10.35")),
                Types.Currency.EUR,
                new Types.DebtorIban(" "),
                new Types.CreditorIban("CRDTRIBAN"),
                new Types.Details("payment type1 details")));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                new Types.Amount(new BigDecimal("10.35")),
                Types.Currency.EUR,
                new Types.DebtorIban("DBTRIBAN"),
                null,
                new Types.Details("payment type1 details")));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                new Types.Amount(new BigDecimal("10.35")),
                Types.Currency.EUR,
                new Types.DebtorIban("DBTRIBAN"),
                new Types.CreditorIban(" "),
                new Types.Details("payment type1 details")));

        TestUtil.assertExc("details", () -> new PaymentT1(
                new Types.Amount(new BigDecimal("10.35")),
                Types.Currency.EUR,
                new Types.DebtorIban("DBTRIBAN"),
                new Types.CreditorIban("CRDTRIBAN"),
                null));

        TestUtil.assertExc("details", () -> new PaymentT1(
                new Types.Amount(new BigDecimal("10.35")),
                Types.Currency.EUR,
                new Types.DebtorIban("DBTRIBAN"),
                new Types.CreditorIban("CRDTRIBAN"),
                new Types.Details(" ")));
    }

    @Test
    public void paymentT2Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT2(
                new Types.Amount(new BigDecimal("10.35")),
                Types.Currency.EUR,
                new Types.DebtorIban("DBTRIBAN"),
                new Types.CreditorIban("CRDTRIBAN"),
                null));
    }

    @Test
    public void paymentT3Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT3(
                new Types.Amount(new BigDecimal("10.35")),
                Types.Currency.GBP,
                new Types.DebtorIban("DBTRIBAN"),
                new Types.CreditorIban("CRDTRIBAN"),
                new Types.CreditorBankBic("CRDTRBANKBIC")));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                new Types.Amount(new BigDecimal("10.35")),
                Types.Currency.EUR,
                new Types.DebtorIban("DBTRIBAN"),
                new Types.CreditorIban("CRDTRIBAN"),
                null));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                new Types.Amount(new BigDecimal("10.35")),
                Types.Currency.EUR,
                new Types.DebtorIban("DBTRIBAN"),
                new Types.CreditorIban("CRDTRIBAN"),
                new Types.CreditorBankBic(" ")));
    }
}