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

    @Test
    public void createPaymentReq() throws Exception {
        final var req = new CreatePaymentReq(Types.PaymentType.TYPE1, new BigDecimal("10.35"), Types.Currency.EUR, "DBTRIBAN", "CRDTRIBAN", "payment type1 details", null);

        final var reqMarshalled = objMapper.writeValueAsString(req);
        logger.info("reqMarshalled: {}", reqMarshalled);

        var reqUnmarshalled = objMapper.readValue(reqMarshalled, CreatePaymentReq.class);
        Assert.assertEquals(req, reqUnmarshalled);
    }

    @Test
    public void createPaymentResp() throws Exception {
        final var resp = new CreatePaymentResp("afsfhdhsdfgdhshdhajkjh");

        final var respMarshalled = objMapper.writeValueAsString(resp);
        logger.info("respMarshalled: {}", respMarshalled);

        var respUnmarshalled = objMapper.readValue(respMarshalled, CreatePaymentResp.class);
        Assert.assertEquals(resp, respUnmarshalled);
    }
}
