package eu.isakels.rest.controller;

import eu.isakels.rest.model.CreatePaymentReq;
import eu.isakels.rest.model.CreatePaymentResp;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.repo.PaymentRepo;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class PaymentController {

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    @ResponseBody
    public CreatePaymentResp create(@RequestBody CreatePaymentReq req) {
        final String id = PaymentRepo.create(new BasePayment(BigDecimal.TEN/*req.getAmount()*/));

        return new CreatePaymentResp(id);
    }
}
