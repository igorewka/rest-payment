package eu.isakels.rest;

import eu.isakels.rest.model.reqresp.CreatePaymentReq;
import eu.isakels.rest.model.reqresp.CreatePaymentResp;
import eu.isakels.rest.util.TestUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.function.Consumer;

import static eu.isakels.rest.util.TestUtil.objMapper;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentAppTest {

    private static final Logger logger = LoggerFactory.getLogger(PaymentAppTest.class);
    @Autowired
    private MockMvc mvc;

    @Test
    public void createPaymentSuccessful() throws Exception {
        assertCreatePayment(TestUtil.paymentReqT1(),
                (resp) -> assertTrue(StringUtils.isNotBlank(resp.getId())));
        assertCreatePayment(TestUtil.paymentReqT2(),
                (resp) -> assertTrue(StringUtils.isNotBlank(resp.getId())));
        assertCreatePayment(TestUtil.paymentReqT3(),
                (resp) -> assertTrue(StringUtils.isNotBlank(resp.getId())));
    }

    @Test
    public void createPaymentFailing() throws Exception {
        assertCreatePayment(TestUtil.paymentReqT1Failing(),
                (resp) -> assertTrue(StringUtils.isNotBlank(resp.getError())));
    }

    private void assertCreatePayment(final CreatePaymentReq req, final Consumer<CreatePaymentResp> azzert) throws Exception {
        final var reqMarshalled = objMapper.writeValueAsString(req);
        logger.info("reqMarshalled: {}", reqMarshalled);

        final MvcResult result = mvc.perform(post("/payment")
                .content(reqMarshalled)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        var respMarshalled = result.getResponse().getContentAsString();
        logger.info("respMarshalled: {}", respMarshalled);
        var resp = objMapper.readValue(respMarshalled, CreatePaymentResp.class);
        azzert.accept(resp);
    }
}
