package eu.isakels.rest.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import eu.isakels.rest.model.payment.Types;
import eu.isakels.rest.model.reqresp.CreatePaymentReq;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.function.Supplier;

public abstract class TestUtil {

    public static final ObjectMapper objMapper = new ObjectMapper() {{
        registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        enable(SerializationFeature.INDENT_OUTPUT);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }};

    public static CreatePaymentReq paymentReqT1() {
        return new CreatePaymentReq(Types.PaymentType.TYPE1,
                Types.Amount.ofValue(new BigDecimal("10.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Types.Details.ofValue("payment type1 details"),
                null);
    }

    public static CreatePaymentReq paymentReqT1Failing() {
        return new CreatePaymentReq(Types.PaymentType.TYPE1,
                null,
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                Types.Details.ofValue("payment type1 details"),
                null);
    }

    public static CreatePaymentReq paymentReqT2() {
        return new CreatePaymentReq(Types.PaymentType.TYPE2,
                Types.Amount.ofValue(new BigDecimal("20.35")),
                Types.Currency.USD,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                null,
                null);
    }

    public static CreatePaymentReq paymentReqT3() {
        return new CreatePaymentReq(Types.PaymentType.TYPE3,
                Types.Amount.ofValue(new BigDecimal("30.35")),
                Types.Currency.EUR,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                null,
                Types.CreditorBankBic.ofValue("CRDTRBANKBIC"));
    }

    public static CreatePaymentReq paymentReqT3(final Types.Currency currency) {
        return new CreatePaymentReq(Types.PaymentType.TYPE3,
                Types.Amount.ofValue(new BigDecimal("30.35")),
                currency,
                Types.DebtorIban.ofValue("DBTRIBAN"),
                Types.CreditorIban.ofValue("CRDTRIBAN"),
                null,
                Types.CreditorBankBic.ofValue("CRDTRBANKBIC"));
    }

    public static void assertExc(final String excMsgSubstring, final Supplier sup) {
        boolean excCaught;
        try {
            sup.get();
            excCaught = false;
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(e.getMessage().contains(excMsgSubstring));
            excCaught = true;
        }
        Assert.assertTrue(excCaught);
    }
}
