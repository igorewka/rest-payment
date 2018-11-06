package eu.isakels.rest.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static eu.isakels.rest.util.TestUtil.objMapper;

public class MarshallingTest {

    private static final Logger logger = LoggerFactory.getLogger(MarshallingTest.class);

    @Test
    public void createPayment() throws Exception {
        final var req = new CreatePaymentReq(new BigDecimal("10.35"), "DBTRIBAN");
        final var reqStr = objMapper.writeValueAsString(req);
        logger.info("reqStr: {}", reqStr);

        var reqRestored = objMapper.readValue(reqStr, CreatePaymentReq.class);
        logger.info("reqRestored: {}", reqRestored);
    }
}
