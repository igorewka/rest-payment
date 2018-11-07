package eu.isakels.rest.model;

import eu.isakels.rest.model.payment.Types;
import eu.isakels.rest.model.reqresp.CreatePaymentReq;
import eu.isakels.rest.model.reqresp.CreatePaymentResp;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static eu.isakels.rest.util.TestUtil.objMapper;

public class MarshallingTest {

    private static final Logger logger = LoggerFactory.getLogger(MarshallingTest.class);

    private <T> void marshallUnmarshall(final T obj, Class<T> clazz) throws java.io.IOException {
        final var objMarshalled = objMapper.writeValueAsString(obj);
        logger.info("objMarshalled: {}", objMarshalled);

        var objUnmarshalled = objMapper.readValue(objMarshalled, clazz);
        Assert.assertEquals(obj, objUnmarshalled);
    }

    @Test
    public void createPaymentReq() throws Exception {
        final var req = new CreatePaymentReq(Types.PaymentType.TYPE1, new BigDecimal("10.35"), Types.Currency.EUR, "DBTRIBAN", "CRDTRIBAN", "payment type1 details", null);
        marshallUnmarshall(req, CreatePaymentReq.class);
    }

    @Test
    public void createPaymentResp() throws Exception {
        final var resp = new CreatePaymentResp("afsfhdhsdfgdhshdhajkjh");
        marshallUnmarshall(resp, CreatePaymentResp.class);
    }
}
