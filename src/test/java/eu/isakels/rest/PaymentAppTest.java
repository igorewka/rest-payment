package eu.isakels.rest;

import eu.isakels.rest.controller.dto.*;
import eu.isakels.rest.model.ModelConstants;
import eu.isakels.rest.model.Util;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static eu.isakels.rest.util.TestUtil.objMapper;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @MockBean
    private RestTemplate restTemplate;

    private final static Instant instant = LocalDateTime
            .of(2018, Month.NOVEMBER, 9, 15, 0)
            .toInstant(ZoneOffset.UTC);

    @Before
    public void setUp() {
        moveForwardInTimeHours(0);
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
        {
            final var id = createPayment(TestUtil.paymentReqT1()).getId();

            moveForwardInTimeHours(3);
            assertCancelPayment(id, (resp) -> {
                assertTrue(Util.nonNullNotBlank(resp.getId()));
                assertEquals(new BigDecimal("0.15"), resp.getCancelFee());
                assertEquals(ModelConstants.msgSuccessfulCancel, resp.getMsg());
                assertTrue(StringUtils.isBlank(resp.getError()));
            });
        }
        {
            final var id = createPayment(TestUtil.paymentReqT2()).getId();

            moveForwardInTimeHours(3);
            assertCancelPayment(id, (resp) -> {
                assertTrue(Util.nonNullNotBlank(resp.getId()));
                assertEquals(new BigDecimal("0.30"), resp.getCancelFee());
                assertEquals(ModelConstants.msgSuccessfulCancel, resp.getMsg());
                assertTrue(StringUtils.isBlank(resp.getError()));
            });
        }
        {
            final var id = createPayment(TestUtil.paymentReqT3()).getId();

            moveForwardInTimeHours(3);
            assertCancelPayment(id, (resp) -> {
                assertTrue(Util.nonNullNotBlank(resp.getId()));
                assertEquals(new BigDecimal("0.45"), resp.getCancelFee());
                assertEquals(ModelConstants.msgSuccessfulCancel, resp.getMsg());
                assertTrue(StringUtils.isBlank(resp.getError()));
            });
        }
    }

    @Test
    public void cancelPaymentExpired() throws Exception {
        final var id = createPayment(TestUtil.paymentReqT1()).getId();

        moveForwardInTimeHours(25);
        assertCancelPayment(id, (resp) -> {
            assertTrue(Util.nonNullNotBlank(resp.getId()));
            assertNull(resp.getCancelFee());
            assertEquals(ModelConstants.msgExpiredCancel, resp.getMsg());
            assertTrue(StringUtils.isBlank(resp.getError()));
        });
    }

    @Test
    public void queryNonCancelledPaymentSuccessful() throws Exception {
        final var id = createPayment(TestUtil.paymentReqT1()).getId();

        assertQueryPayment(id, (resp) -> {
            assertTrue(Util.nonNullNotBlank(resp.getId()));
            assertNull(resp.getCancelFee());
            assertTrue(StringUtils.isBlank(resp.getMsg()));
            assertTrue(StringUtils.isBlank(resp.getError()));
        });
    }

    @Test
    public void queryCancelledPaymentSuccessful() throws Exception {
        final var id = createPayment(TestUtil.paymentReqT3()).getId();
        moveForwardInTimeHours(3);
        final var respCancelled = cancelPayment(id);

        assertQueryPayment(id, (resp) -> {
            assertTrue(Util.nonNullNotBlank(resp.getId()));
            assertEquals(respCancelled.getCancelFee(), resp.getCancelFee());
            assertTrue(StringUtils.isBlank(resp.getMsg()));
            assertTrue(StringUtils.isBlank(resp.getError()));
        });
    }

    @Test
    public void queryPaymentWithParamsAllSuccessful() throws Exception {
        final var id = createPayment(TestUtil.paymentReqT3()).getId();

        final var idCancelled = createPayment(TestUtil.paymentReqT3()).getId();
        moveForwardInTimeHours(3);
        final var respCancelled = cancelPayment(idCancelled);

        final var params = new LinkedMultiValueMap<String, String>();
        assertQueryPaymentWithParams(params, (resp) -> {
            assertTrue(resp.getPayments().stream().anyMatch(
                    (paymentResp) -> paymentResp.equals(QueryPaymentResp.ofId(id))));
            assertTrue(resp.getPayments().stream().anyMatch(
                    (paymentResp) -> paymentResp.equals(
                            QueryPaymentResp.ofCancelFee(
                                    idCancelled,
                                    respCancelled.getCancelFee(),
                                    respCancelled.getCurrency()))));
            assertTrue(StringUtils.isBlank(resp.getMsg()));
            assertTrue(StringUtils.isBlank(resp.getError()));
        });
    }

    @Test
    public void queryPaymentWithParamsCancelledSuccessful() throws Exception {
        final var id = createPayment(TestUtil.paymentReqT2()).getId();

        final var idCancelled = createPayment(TestUtil.paymentReqT2()).getId();
        moveForwardInTimeHours(3);
        final var respCancelled = cancelPayment(idCancelled);

        final var params = new LinkedMultiValueMap<>(Map.ofEntries(
                Map.entry(Constants.cancelledParam, List.of("true"))));
        assertQueryPaymentWithParams(params, (resp) -> {
            assertTrue(resp.getPayments().stream().noneMatch(
                    (paymentResp) -> paymentResp.equals(QueryPaymentResp.ofId(id))));
            assertTrue(resp.getPayments().stream().anyMatch(
                    (paymentResp) -> paymentResp.equals(
                            QueryPaymentResp.ofCancelFee(
                                    idCancelled,
                                    respCancelled.getCancelFee(),
                                    respCancelled.getCurrency()))));
            assertTrue(StringUtils.isBlank(resp.getMsg()));
            assertTrue(StringUtils.isBlank(resp.getError()));
        });
    }

    @Test
    public void queryPaymentWithParamsNonCancelledSuccessful() throws Exception {
        final var id = createPayment(TestUtil.paymentReqT2()).getId();

        final var idCancelled = createPayment(TestUtil.paymentReqT2()).getId();
        moveForwardInTimeHours(3);
        final var respCancelled = cancelPayment(idCancelled);

        final var params = new LinkedMultiValueMap<>(Map.ofEntries(
                Map.entry(Constants.cancelledParam, List.of("false"))));
        assertQueryPaymentWithParams(params, (resp) -> {
            assertTrue(resp.getPayments().stream().anyMatch(
                    (paymentResp) -> paymentResp.equals(QueryPaymentResp.ofId(id))));
            assertTrue(resp.getPayments().stream().noneMatch(
                    (paymentResp) -> paymentResp.equals(
                            QueryPaymentResp.ofCancelFee(
                                    idCancelled,
                                    respCancelled.getCancelFee(),
                                    respCancelled.getCurrency()))));
            assertTrue(StringUtils.isBlank(resp.getMsg()));
            assertTrue(StringUtils.isBlank(resp.getError()));
        });
    }

    @Test
    public void queryPaymentWithParamsNonCancelledAmountSuccessful() throws Exception {
        final var id1 = createPayment(TestUtil.paymentReqT1()).getId();
        final var id2 = createPayment(TestUtil.paymentReqT2()).getId();

        final var idCancelled = createPayment(TestUtil.paymentReqT3()).getId();
        moveForwardInTimeHours(3);
        final var respCancelled = cancelPayment(idCancelled);

        final var params = new LinkedMultiValueMap<>(Map.ofEntries(
                Map.entry(Constants.cancelledParam, List.of("false")),
                Map.entry(Constants.amountGtParam, List.of("15")),
                Map.entry(Constants.amountLtParam, List.of("35"))));
        assertQueryPaymentWithParams(params, (resp) -> {
            assertTrue(resp.getPayments().stream().noneMatch(
                    (paymentResp) -> paymentResp.equals(QueryPaymentResp.ofId(id1))));
            assertTrue(resp.getPayments().stream().anyMatch(
                    (paymentResp) -> paymentResp.equals(QueryPaymentResp.ofId(id2))));
            assertTrue(resp.getPayments().stream().noneMatch(
                    (paymentResp) -> paymentResp.equals(
                            QueryPaymentResp.ofCancelFee(
                                    idCancelled,
                                    respCancelled.getCancelFee(),
                                    respCancelled.getCurrency()))));
            assertTrue(StringUtils.isBlank(resp.getMsg()));
            assertTrue(StringUtils.isBlank(resp.getError()));
        });
    }

    private void assertQueryPaymentWithParams(
            final MultiValueMap<String, String> params,
            final Consumer<QueryPaymentWithParamsResp> azzert) throws Exception {
        final MvcResult result = mvc.perform(
                get(Constants.pathPayments)
                        .params(params))
                .andExpect(status().isOk())
                .andReturn();

        var respMarshalled = result.getResponse().getContentAsString();
        logger.info("respMarshalled: {}", respMarshalled);
        var resp = objMapper.readValue(respMarshalled, QueryPaymentWithParamsResp.class);
        azzert.accept(resp);
    }

    private void moveForwardInTimeHours(final int i) {
        final var instantHoursInTheFuture = instant.plus(Duration.ofHours(i));
        given(clock.instant()).willReturn(instantHoursInTheFuture);
    }

    private void moveBackInTimeHours(final int i) {
        final var instantHoursInThePast = instant.minus(Duration.ofHours(i));
        given(clock.instant()).willReturn(instantHoursInThePast);
    }

    private void assertQueryPayment(final UUID id,
                                    final Consumer<QueryPaymentResp> azzert) throws Exception {
        final MvcResult result = mvc.perform(
                get(Constants.pathPayments + Constants.pathVarId, id))
                .andExpect(status().isOk())
                .andReturn();

        var respMarshalled = result.getResponse().getContentAsString();
        logger.info("respMarshalled: {}", respMarshalled);
        var resp = objMapper.readValue(respMarshalled, QueryPaymentResp.class);
        azzert.accept(resp);
    }

    private void assertCancelPayment(final UUID id,
                                     final Consumer<CancelPaymentResp> azzert) throws Exception {
        CancelPaymentResp resp = cancelPayment(id);
        azzert.accept(resp);
    }

    private CancelPaymentResp cancelPayment(final UUID id) throws Exception {
        final MvcResult result = mvc.perform(
                put(Constants.pathPayments + Constants.pathVarId, id))
                .andExpect(status().isOk())
                .andReturn();

        var respMarshalled = result.getResponse().getContentAsString();
        logger.info("respMarshalled: {}", respMarshalled);
        return objMapper.readValue(respMarshalled, CancelPaymentResp.class);
    }

    private void assertCreatePayment(final CreatePaymentReq req,
                                     final Consumer<CreatePaymentResp> azzert) throws Exception {
        CreatePaymentResp resp = createPayment(req);
        azzert.accept(resp);
    }

    private CreatePaymentResp createPayment(final CreatePaymentReq req) throws Exception {
        moveForwardInTimeHours(0);

        final var reqMarshalled = objMapper.writeValueAsString(req);
        logger.info("reqMarshalled: {}", reqMarshalled);

        final MvcResult result = mvc.perform(
                post(Constants.pathPayments)
                        .content(reqMarshalled)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var respMarshalled = result.getResponse().getContentAsString();
        logger.info("respMarshalled: {}", respMarshalled);
        return objMapper.readValue(respMarshalled, CreatePaymentResp.class);
    }
}
