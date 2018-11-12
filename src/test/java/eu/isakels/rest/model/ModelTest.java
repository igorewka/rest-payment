package eu.isakels.rest.model;

import eu.isakels.rest.controller.ControllerPaymentFactory;
import eu.isakels.rest.misc.Types;
import eu.isakels.rest.model.payment.PaymentT1;
import eu.isakels.rest.model.payment.PaymentT2;
import eu.isakels.rest.model.payment.PaymentT3;
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
            final var payment = ControllerPaymentFactory.ofReq(TestUtil.paymentReqT1(), clock);
            Assert.assertEquals(Types.PaymentType.TYPE1, payment.getType());
        }
        {
            final var payment = ControllerPaymentFactory.ofReq(TestUtil.paymentReqT2(), clock);
            Assert.assertEquals(Types.PaymentType.TYPE2, payment.getType());
        }
        {
            final var payment = ControllerPaymentFactory.ofReq(TestUtil.paymentReqT3(Types.Currency.USD), clock);
            Assert.assertEquals(Types.PaymentType.TYPE3, payment.getType());
        }
        {
            final var payment = ControllerPaymentFactory.ofReq(TestUtil.paymentReqT3(Types.Currency.EUR), clock);
            Assert.assertEquals(Types.PaymentType.TYPE3, payment.getType());
        }
    }

    @Test
    public void paymentT1Failing() {
        TestUtil.assertExc("amount", () -> new PaymentT1(
                null,
                Types.Currency.EUR,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                ModelTypes.Details.ofValue("payment type1 details"),
                null));

        TestUtil.assertExc("amount", () -> new PaymentT1(
                ModelTypes.Amount.ofValue(new BigDecimal("-10.35")),
                Types.Currency.EUR,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                ModelTypes.Details.ofValue("payment type1 details"),
                null));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                null,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                ModelTypes.Details.ofValue("payment type1 details"),
                null));

        TestUtil.assertExc("currency", () -> new PaymentT1(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.USD,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                ModelTypes.Details.ofValue("payment type1 details"),
                null));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                null,
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                ModelTypes.Details.ofValue("payment type1 details"),
                null));

        TestUtil.assertExc("debtorIban", () -> new PaymentT1(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                ModelTypes.DebtorIban.ofValue(" "),
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                ModelTypes.Details.ofValue("payment type1 details"),
                null));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                null,
                Instant.now(),
                false,
                null,
                null,
                ModelTypes.Details.ofValue("payment type1 details"),
                null));

        TestUtil.assertExc("creditorIban", () -> new PaymentT1(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                ModelTypes.CreditorIban.ofValue(" "),
                Instant.now(),
                false,
                null,
                null,
                ModelTypes.Details.ofValue("payment type1 details"),
                null));

        TestUtil.assertExc("details", () -> new PaymentT1(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                null,
                null));

        TestUtil.assertExc("details", () -> new PaymentT1(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                ModelTypes.Details.ofValue(" "),
                null));
    }

    @Test
    public void paymentT2Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT2(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                null,
                null));
    }

    @Test
    public void paymentT3Failing() {
        TestUtil.assertExc("currency", () -> new PaymentT3(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.GBP,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                ModelTypes.CreditorBankBic.ofValue("CRDTRBANKBIC"),
                null));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                null,
                null));

        TestUtil.assertExc("creditorBankBic", () -> new PaymentT3(
                ModelTypes.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                ModelTypes.DebtorIban.ofValue("DBTRIBAN"),
                ModelTypes.CreditorIban.ofValue("CRDTRIBAN"),
                Instant.now(),
                false,
                null,
                null,
                ModelTypes.CreditorBankBic.ofValue(" "),
                null));
    }
}