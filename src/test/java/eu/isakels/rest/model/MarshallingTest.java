package eu.isakels.rest.model;

import eu.isakels.rest.model.payment.Types;
import eu.isakels.rest.reqresp.CancelPaymentResp;
import eu.isakels.rest.reqresp.CreatePaymentReq;
import eu.isakels.rest.reqresp.CreatePaymentResp;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.UUID;

import static eu.isakels.rest.util.TestUtil.objMapper;

public class MarshallingTest {

    private static final Logger logger = LoggerFactory.getLogger(MarshallingTest.class);

    @Test
    public void createPaymentReq() throws Exception {
        final var req = new CreatePaymentReq(Types.PaymentType.TYPE1,
                new BigDecimal("10.35"),
                Types.Currency.EUR,
                "DBTRIBAN",
                "CRDTRIBAN",
                "payment type1 details",
                null);
        assertMarshallUnmarshall(req, CreatePaymentReq.class);
    }

    @Test
    public void createPaymentResp() throws Exception {
        final var resp = new CreatePaymentResp("property is mandatory");
        assertMarshallUnmarshall(resp, CreatePaymentResp.class);
    }

    @Test
    public void cancelPaymentResp() throws Exception {
        final var resp = CancelPaymentResp.ofMsg(
                UUID.randomUUID(),
                new BigDecimal("10"),
                "cancelled successfully");
        assertMarshallUnmarshall(resp, CancelPaymentResp.class);
    }

    private <T> void assertMarshallUnmarshall(final T obj, Class<T> clazz) throws java.io.IOException {
        final var objMarshalled = objMapper.writeValueAsString(obj);
        logger.info("objMarshalled: {}", objMarshalled);

        var objUnmarshalled = objMapper.readValue(objMarshalled, clazz);
        Assert.assertEquals(obj, objUnmarshalled);
    }
}
