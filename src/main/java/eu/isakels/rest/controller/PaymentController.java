package eu.isakels.rest.controller;

import eu.isakels.rest.model.payment.PaymentFactory;
import eu.isakels.rest.model.reqresp.CreatePaymentReq;
import eu.isakels.rest.model.reqresp.CreatePaymentResp;
import eu.isakels.rest.repo.PaymentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping(value = "/payment",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CreatePaymentResp create(@RequestBody CreatePaymentReq req) {
        CreatePaymentResp resp;
        try {
            final UUID id = PaymentRepo.create(PaymentFactory.ofReq(req));
            resp = new CreatePaymentResp(id);
        } catch (Throwable exc) {
            logger.error("", exc);
            resp = new CreatePaymentResp(exc.getMessage());
        }
        return resp;
    }
}
