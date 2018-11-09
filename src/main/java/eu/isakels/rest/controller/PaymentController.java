package eu.isakels.rest.controller;

import eu.isakels.rest.model.Constants;
import eu.isakels.rest.model.payment.PaymentFactory;
import eu.isakels.rest.reqresp.CancelPaymentResp;
import eu.isakels.rest.reqresp.CreatePaymentReq;
import eu.isakels.rest.reqresp.CreatePaymentResp;
import eu.isakels.rest.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private PaymentService service;

    @Autowired
    public PaymentController(final PaymentService service) {
        this.service = service;
    }

    @PostMapping(value = Constants.pathPayments,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CreatePaymentResp create(@RequestBody CreatePaymentReq req) {
        CreatePaymentResp resp;
        try {
            final UUID id = service.create(PaymentFactory.ofReq(req));
            resp = new CreatePaymentResp(id);
        } catch (Throwable exc) {
            logger.error("", exc);
            resp = new CreatePaymentResp(exc.getMessage());
        }
        return resp;
    }

    @PutMapping(value = Constants.pathPayments + Constants.pathVarId,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CancelPaymentResp cancel(@PathVariable UUID id) {
        CancelPaymentResp resp;
        try {
            final boolean result = service.cancel(id);
            if (result) {
                resp = CancelPaymentResp.ofMsg(id, Constants.msgSuccessfulCancel);
            } else {
                resp = CancelPaymentResp.ofMsg(id, Constants.msgExpiredCancel);
            }
        } catch (Throwable exc) {
            logger.error("", exc);
            resp = CancelPaymentResp.ofError(id, exc.getMessage());
        }
        return resp;
    }
}
