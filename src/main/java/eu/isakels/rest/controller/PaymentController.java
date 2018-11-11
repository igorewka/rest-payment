package eu.isakels.rest.controller;

import eu.isakels.rest.Constants;
import eu.isakels.rest.model.ModelConstants;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.model.payment.PaymentFactory;
import eu.isakels.rest.controller.dto.*;
import eu.isakels.rest.service.GeoLocationService;
import eu.isakels.rest.service.PaymentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Clock;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService service;
    private final GeoLocationService geoService;
    private final Clock clock;

    @Autowired
    public PaymentController(final PaymentService service,
                             final GeoLocationService geoService,
                             final Clock clock) {
        this.service = service;
        this.geoService = geoService;
        this.clock = clock;
    }

    @PostMapping(value = Constants.pathPayments,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CreatePaymentResp create(@RequestBody CreatePaymentReq req, HttpServletRequest httpReq) {
        geoService.logCountry(getIp(httpReq));

        CreatePaymentResp resp;
        try {
            final BasePayment result = service.create(PaymentFactory.ofReq(req, clock));
            logger.info("payment[{}] created", result.getIdUnwrapped());

            service.notify(result);

            resp = new CreatePaymentResp(result.getIdUnwrapped());
        } catch (Throwable exc) {
            logger.error("", exc);
            resp = new CreatePaymentResp(exc.getMessage());
        }
        return resp;
    }

    @PutMapping(value = Constants.pathPayments + Constants.pathVarId,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CancelPaymentResp cancel(@PathVariable UUID id, HttpServletRequest httpReq) {
        geoService.logCountry(getIp(httpReq));

        CancelPaymentResp resp;
        try {
            final BasePayment result = service.cancel(id);
            if (result.isCancelled()) {
                logger.info("payment[{}] cancelled", id);
                resp = CancelPaymentResp.ofCancelFee(
                        id,
                        result.getCancelFeeUnwrapped().getValue(),
                        result.getCurrency(),
                        ModelConstants.msgSuccessfulCancel);
            } else {
                logger.info("payment[{}] cancel expired", id);
                resp = CancelPaymentResp.ofMsg(id, ModelConstants.msgExpiredCancel);
            }
        } catch (Throwable exc) {
            logger.error("", exc);
            resp = CancelPaymentResp.ofError(id, exc.getMessage());
        }
        return resp;
    }

    @GetMapping(value = Constants.pathPayments + Constants.pathVarId,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public QueryPaymentResp query(@PathVariable UUID id, HttpServletRequest httpReq) {
        geoService.logCountry(getIp(httpReq));

        QueryPaymentResp resp;
        try {
            final BasePayment payment = service.query(id);
            logger.info("payment[{}] queried", id);
            if (payment.isCancelled()) {
                resp = QueryPaymentResp.ofCancelFee(
                        id,
                        payment.getCancelFeeUnwrapped().getValue(),
                        payment.getCurrency());
            } else {
                resp = QueryPaymentResp.ofId(id);
            }
        } catch (Throwable exc) {
            logger.error("", exc);
            resp = QueryPaymentResp.ofError(id, exc.getMessage());
        }
        return resp;
    }

    // TODO: pagination or limiting by period of time should be implemented
    // there's a hardcoded limit of 3 days in the query method of repository
    @GetMapping(value = Constants.pathPayments,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public QueryPaymentWithParamsResp queryWithParams(@RequestParam final Optional<Boolean> cancelled,
                                                      @RequestParam final Optional<BigDecimal> amountGt,
                                                      @RequestParam final Optional<BigDecimal> amountLt,
                                                      HttpServletRequest httpReq) {
        geoService.logCountry(getIp(httpReq));

        QueryPaymentWithParamsResp resp;
        try {
            final var params = getParams(cancelled, amountGt, amountLt);

            final Set<BasePayment> payments = service.queryWithParams(params);
            final var respPayments = payments.stream().map((payment) -> {
                final QueryPaymentResp paymentResp;
                if (payment.isCancelled()) {
                    paymentResp = QueryPaymentResp.ofCancelFee(
                            payment.getIdUnwrapped(),
                            payment.getCancelFeeUnwrapped().getValue(),
                            payment.getCurrency());
                } else {
                    paymentResp = QueryPaymentResp.ofId(payment.getIdUnwrapped());
                }
                return paymentResp;
            }).collect(Collectors.toSet());
            resp = QueryPaymentWithParamsResp.ofPayments(respPayments);
        } catch (Throwable exc) {
            logger.error("", exc);
            resp = QueryPaymentWithParamsResp.ofError(exc.getMessage());
        }
        return resp;
    }

    private Map<String, ? extends Serializable> getParams(final Optional<Boolean> cancelled,
                                                          final Optional<BigDecimal> amountGt,
                                                          final Optional<BigDecimal> amountLt) {
        final var paramsOpt = Map.ofEntries(
                Map.entry(Constants.cancelledParam, cancelled),
                Map.entry(Constants.amountGtParam, amountGt),
                Map.entry(Constants.amountLtParam, amountLt));

        return paramsOpt.entrySet().stream()
                .filter((entry) -> entry.getValue().isPresent())
                .collect(Collectors.toUnmodifiableMap(
                        (entry) -> entry.getKey(),
                        (entry) -> entry.getValue().orElseThrow(
                                () -> new RuntimeException("unexpected empty Optional"))));
    }

    private String getIp(final HttpServletRequest req) {
        final String proxiedIp = req.getHeader("X-Forwarded-For");
        final String ip;
        if (StringUtils.isNotBlank(proxiedIp)) {
            ip = proxiedIp;
        } else {
            ip = req.getRemoteAddr();
        }
        return ip;
    }
}
