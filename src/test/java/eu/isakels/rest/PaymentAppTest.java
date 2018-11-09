package eu.isakels.rest;

import eu.isakels.rest.model.Constants;
import eu.isakels.rest.model.Util;
import eu.isakels.rest.reqresp.CancelPaymentResp;
import eu.isakels.rest.reqresp.CreatePaymentReq;
import eu.isakels.rest.reqresp.CreatePaymentResp;
import eu.isakels.rest.util.TestUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

import static eu.isakels.rest.util.TestUtil.objMapper;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentAppTest {

    private static final Logger logger = LoggerFactory.getLogger(PaymentAppTest.class);
    @Autowired
    private MockMvc mvc;
    @MockBean
    private Clock clock;

    @Before
    public void setUp() {
        given(clock.instant()).willReturn(Instant.now());
    }

    @Test
    public void createPaymentSuccessful() throws Exception {
        assertCreatePayment(TestUtil.paymentReqT1(),
                (resp) -> assertTrue(Util.nonNullNotBlank(resp.getId())));
        assertCreatePayment(TestUtil.paymentReqT2(),
                (resp) -> assertTrue(Util.nonNullNotBlank(resp.getId())));
        assertCreatePayment(TestUtil.paymentReqT3(),
                (resp) -> assertTrue(Util.nonNullNotBlank(resp.getId())));
    }

    @Test
    public void createPaymentFailing() throws Exception {
        assertCreatePayment(TestUtil.paymentReqT1Failing(),
                (resp) -> assertTrue(StringUtils.isNotBlank(resp.getError())));
    }

    @Test
    public void cancelPaymentSuccessful() throws Exception {
        final var instant3HoursInThePast = Instant.now().minus(Duration.ofHours(3));
        given(clock.instant()).willReturn(instant3HoursInThePast);
        {
            final var id = assertCreatePayment(TestUtil.paymentReqT1(),
                    (resp) -> assertTrue(Util.nonNullNotBlank(resp.getId())));

            assertCancelPayment(id, (resp) -> {
                assertTrue(Util.nonNullNotBlank(resp.getId()));
                assertEquals(new BigDecimal("0.15"), resp.getCancelFee());
                assertEquals(Constants.msgSuccessfulCancel, resp.getMsg());
                assertTrue(StringUtils.isBlank(resp.getError()));
            });
        }
        {
            final var id = assertCreatePayment(TestUtil.paymentReqT2(),
                    (resp) -> assertTrue(Util.nonNullNotBlank(resp.getId())));

            assertCancelPayment(id, (resp) -> {
                assertTrue(Util.nonNullNotBlank(resp.getId()));
                assertEquals(new BigDecimal("0.30"), resp.getCancelFee());
                assertEquals(Constants.msgSuccessfulCancel, resp.getMsg());
                assertTrue(StringUtils.isBlank(resp.getError()));
            });
        }
        {
            final var id = assertCreatePayment(TestUtil.paymentReqT3(),
                    (resp) -> assertTrue(Util.nonNullNotBlank(resp.getId())));

            assertCancelPayment(id, (resp) -> {
                assertTrue(Util.nonNullNotBlank(resp.getId()));
                assertEquals(new BigDecimal("0.45"), resp.getCancelFee());
                assertEquals(Constants.msgSuccessfulCancel, resp.getMsg());
                assertTrue(StringUtils.isBlank(resp.getError()));
            });
        }
    }

    @Test
    public void cancelPaymentExpired() throws Exception {
        final var instant25HoursInThePast = Instant.now().minus(Duration.ofHours(25));
        given(clock.instant()).willReturn(instant25HoursInThePast);

        final var id = assertCreatePayment(TestUtil.paymentReqT1(),
                (resp) -> assertTrue(Util.nonNullNotBlank(resp.getId())));

        assertCancelPayment(id, (resp) -> {
            assertTrue(Util.nonNullNotBlank(resp.getId()));
            assertNull(resp.getCancelFee());
            assertEquals(Constants.msgExpiredCancel, resp.getMsg());
            assertTrue(StringUtils.isBlank(resp.getError()));
        });
    }

    private UUID assertCreatePayment(final CreatePaymentReq req
            , final Consumer<CreatePaymentResp> azzert) throws Exception {
        final var reqMarshalled = objMapper.writeValueAsString(req);
        logger.info("reqMarshalled: {}", reqMarshalled);

        final MvcResult result = mvc.perform(post(Constants.pathPayments)
                .content(reqMarshalled)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        var respMarshalled = result.getResponse().getContentAsString();
        logger.info("respMarshalled: {}", respMarshalled);
        var resp = objMapper.readValue(respMarshalled, CreatePaymentResp.class);
        azzert.accept(resp);

        return resp.getId();
    }

    private void assertCancelPayment(final UUID id
            , final Consumer<CancelPaymentResp> azzert) throws Exception {
        final MvcResult result = mvc.perform(
                put(Constants.pathPayments + Constants.pathVarId, id))
                .andExpect(status().isOk())
                .andReturn();

        var respMarshalled = result.getResponse().getContentAsString();
        logger.info("respMarshalled: {}", respMarshalled);
        var resp = objMapper.readValue(respMarshalled, CancelPaymentResp.class);
        azzert.accept(resp);
    }
}
