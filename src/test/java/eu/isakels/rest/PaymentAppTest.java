package eu.isakels.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentAppTest {

    private static final Logger logger = LoggerFactory.getLogger(PaymentAppTest.class);
    @Autowired
    private MockMvc mvc;

    @Test
    public void createPayment() throws Exception {
//        final var req = new CreatePaymentReq(new BigDecimal("10.35"), "DBTRIBAN");
//
//        final var reqStr = objMapper.writeValueAsString(req);
//        logger.info("reqStr: {}", reqStr);
//
////        var reqRestored = objMapper.readValue(reqStr, CreatePaymentReq.class);
////        logger.info("reqRestored: {}", reqRestored);
//
//        final MvcResult result = mvc.perform(post("/payment")
//                .content(reqStr)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isOk())
//                .andReturn();
//
//        var respStr = result.getResponse().getContentAsString();
//        logger.info("respStr: {}", respStr);
//        var resp = objMapper.readValue(respStr, CreatePaymentResp.class);
//
//        assertTrue(!resp.getId().isEmpty());
        // TODO: fix test
        assertTrue(false);
    }
}
