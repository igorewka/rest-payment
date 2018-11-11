package eu.isakels.rest.model;

import eu.isakels.rest.controller.dto.*;
import eu.isakels.rest.model.payment.Types;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Set;
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
        final var resp = CancelPaymentResp.ofCancelFee(
                UUID.randomUUID(),
                new BigDecimal("10"),
                Types.Currency.EUR,
                "cancelled successfully");
        assertMarshallUnmarshall(resp, CancelPaymentResp.class);
    }

    @Test
    public void queryPaymentResp() throws Exception {
        final var resp = QueryPaymentResp.ofCancelFee(
                UUID.randomUUID(),
                new BigDecimal("10"),
                Types.Currency.EUR);
        assertMarshallUnmarshall(resp, QueryPaymentResp.class);
    }

    @Test
    public void queryPaymentWithParamsResp() throws Exception {
        final var respPayments = Set.of(
                QueryPaymentResp.ofCancelFee(
                        UUID.randomUUID(),
                        new BigDecimal("10"),
                        Types.Currency.EUR),
                QueryPaymentResp.ofId(UUID.randomUUID()),
                QueryPaymentResp.ofCancelFee(
                        UUID.randomUUID(),
                        new BigDecimal("11"),
                        Types.Currency.USD),
                QueryPaymentResp.ofId(UUID.randomUUID()));
        final var resp = QueryPaymentWithParamsResp.ofPayments(respPayments);
        assertMarshallUnmarshall(resp, QueryPaymentWithParamsResp.class);
    }

    @Test
    public void geoLocationResp() throws Exception {
        final var resp = new GeoLocationResp("Latvia", "success");
        assertMarshallUnmarshall(resp, GeoLocationResp.class);
    }

    @Test
    public void geoLocationRespUnknownProps() throws Exception {
        final var objMarshalled = "{\"status\": \"fail\",\n" +
                " \"message\": \"query is not a valid IP address\"}";
        logger.info("objMarshalled: {}", objMarshalled);

        var objUnmarshalled = objMapper.readValue(objMarshalled, GeoLocationResp.class);
        Assert.assertEquals("fail", objUnmarshalled.getStatus());
        Assert.assertTrue(StringUtils.isBlank(objUnmarshalled.getCountry()));
    }

    private <T> void assertMarshallUnmarshall(final T obj, Class<T> clazz) throws java.io.IOException {
        final var objMarshalled = objMapper.writeValueAsString(obj);
        logger.info("objMarshalled: {}", objMarshalled);

        var objUnmarshalled = objMapper.readValue(objMarshalled, clazz);
        Assert.assertEquals(obj, objUnmarshalled);
    }
}
