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
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT1());
            Assert.assertTrue(payment instanceof PaymentT1);
        }
        {
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT2());
            Assert.assertTrue(payment instanceof PaymentT2);
        }
        {
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT3(Types.Currency.USD));
            Assert.assertTrue(payment instanceof PaymentT3);
        }
        {
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT3(Types.Currency.EUR));
            Assert.assertTrue(payment instanceof PaymentT3);
        }
    }

    @Test
    public void paymentT1Failing() {
        TestUtil.assertExc("amount", () -> new PaymentT1(
                null,
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("amount", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("-10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                null,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.USD,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                null,
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue(" "),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                null,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue(" "),
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("details", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                null));

        TestUtil.assertExc("details", () -> new PaymentT1(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Types.Details.ofValue(" ")));
    }

    @Test
    public void paymentT2Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT2(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                null));
    }

    @Test
    public void paymentT3Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT3(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.GBP,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Types.CreditorBankBic.ofValue("CRDTRBANKBIC")));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                null));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Types.CreditorBankBic.ofValue(" ")));
    }
}