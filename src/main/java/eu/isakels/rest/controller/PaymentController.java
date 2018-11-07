package eu.isakels.rest.controller;

import eu.isakels.rest.model.reqresp.CreatePaymentReq;
import eu.isakels.rest.model.reqresp.CreatePaymentResp;
import eu.isakels.rest.model.payment.PaymentFactory;
import eu.isakels.rest.repo.PaymentRepo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @PostMapping(value = "/payment",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CreatePaymentResp create(@RequestBody CreatePaymentReq req) {
        final String id = PaymentRepo.create(PaymentFactory.forReq(req));

        return new CreatePaymentResp(id);
    }
}
