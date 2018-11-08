package eu.isakels.rest.model;

import eu.isakels.rest.model.payment.*;
import eu.isakels.rest.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ModelTest {
    @Test
    public void paymentSuccessful() {
        {
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT1());
            Assert.assertEquals(Types.PaymentType.TYPE1, payment.getType());
        }
        {
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT2());
            Assert.assertEquals(Types.PaymentType.TYPE2, payment.getType());
        }
        {
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT3(Types.Currency.USD));
            Assert.assertEquals(Types.PaymentType.TYPE3, payment.getType());
        }
        {
            final var payment = PaymentFactory.ofReq(TestUtil.paymentReqT3(Types.Currency.EUR));
            Assert.assertEquals(Types.PaymentType.TYPE3, payment.getType());
        }
    }

    @Test
    public void paymentT1Failing() {
        TestUtil.assertExc("amount", () -> new PaymentT1(
                Types.PaymentType.TYPE1,
                null,
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("amount", () -> new PaymentT1(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("-10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                null,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.USD,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                null,
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue(" "),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                null,
                LocalDateTime.now(),
                false,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue(" "),
                LocalDateTime.now(),
                false,
                Types.Details.ofValue("payment type1 details")));

        TestUtil.assertExc("details", () -> new PaymentT1(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                null));

        TestUtil.assertExc("details", () -> new PaymentT1(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                Types.Details.ofValue(" ")));
    }

    @Test
    public void paymentT2Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT2(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                null));
    }

    @Test
    public void paymentT3Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT3(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.GBP,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                Types.CreditorBankBic.ofValue("CRDTRBANKBIC")));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                null));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                LocalDateTime.now(),
                false,
                Types.CreditorBankBic.ofValue(" ")));
    }
}