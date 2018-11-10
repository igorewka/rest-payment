package eu.isakels.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.isakels.rest.Constants;
import eu.isakels.rest.model.ModelConstants;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.model.payment.PaymentFactory;
import eu.isakels.rest.reqresp.*;
import eu.isakels.rest.service.CancelResult;
import eu.isakels.rest.service.PaymentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private static final Duration timeout = Duration.ofSeconds(5);
    private static final RestTemplate restTemplate = new RestTemplateBuilder()
            .setReadTimeout(timeout)
            .setConnectTimeout(timeout)
            .build();

    private PaymentService service;
    private Clock clock;

    @Autowired
    private ObjectMapper objMapper;

    @Autowired
    public PaymentController(final PaymentService service, final Clock clock) {
        this.service = service;
        this.clock = clock;
    }

    @PostMapping(value = Constants.pathPayments,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CreatePaymentResp create(@RequestBody CreatePaymentReq req, HttpServletRequest httpReq) {
        logClientCountry(httpReq);

        CreatePaymentResp resp;
        try {
            final UUID id = service.create(PaymentFactory.ofReq(req, clock));
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
    public CancelPaymentResp cancel(@PathVariable UUID id, HttpServletRequest httpReq) {
        logClientCountry(httpReq);

        CancelPaymentResp resp;
        try {
            final CancelResult result = service.cancel(id);
            if (result.isResult()) {
                resp = CancelPaymentResp.ofCancelFee(
                        id,
                        result.getPayment().getCancelFee()
                                .orElseThrow(() -> new RuntimeException(ModelConstants.expectedCancelFeeMissing))
                                .getValue(),
                        result.getPayment().getCurrency(),
                        ModelConstants.msgSuccessfulCancel);
            } else {
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
        logClientCountry(httpReq);

        QueryPaymentResp resp;
        try {
            final BasePayment payment = service.query(id);
            if (payment.isCancelled()) {
                resp = QueryPaymentResp.ofCancelFee(
                        id,
                        payment.getCancelFee()
                                .orElseThrow(() -> new RuntimeException(ModelConstants.expectedCancelFeeMissing))
                                .getValue(),
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
        logClientCountry(httpReq);

        QueryPaymentWithParamsResp resp;
        try {
            final var params = getParams(cancelled, amountGt, amountLt);

            final Set<BasePayment> payments = service.queryWithParams(params);
            final var respPayments = payments.stream().map((payment) -> {
                final QueryPaymentResp paymentResp;
                if (payment.isCancelled()) {
                    paymentResp = QueryPaymentResp.ofCancelFee(
                            payment.getId().orElseThrow(
                                    () -> new RuntimeException(ModelConstants.expectedIdMissing)),
                            payment.getCancelFee().orElseThrow(
                                    () -> new RuntimeException(ModelConstants.expectedCancelFeeMissing))
                                    .getValue(),
                            payment.getCurrency());
                } else {
                    paymentResp = QueryPaymentResp.ofId(
                            payment.getId().orElseThrow(
                                    () -> new RuntimeException(ModelConstants.expectedIdMissing)));
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

    private void logClientCountry(final HttpServletRequest req) {
        CompletableFuture.runAsync(() -> {
            final var ip = getIp(req);
            try {
                // This code doesn't work, because of strange unmarshalling issue
//                final var respOpt = Optional.ofNullable(restTemplate.getForObject(
//                        Constants.geoLocationServiceUrl + ip, GeoLocationResp.class));
                final var respOpt = Optional.ofNullable(restTemplate.getForObject(
                        Constants.geoLocationServiceUrl + ip, String.class));
                respOpt.map((resp) -> {
                    try {
                        logger.debug("client's ip: {}", ip);
                        logger.debug("geo location service resp: {}"
                                , resp.replaceAll("\n", ""));
                        unmarshallAndLogCountry(ip, resp);
                    } catch (Exception e) {
                        logger.debug("geo location service", e);
                    }
                    return "";
                });
            } catch (Exception e) {
                logger.debug("geo location service", e);
            }
        });
    }

    private void unmarshallAndLogCountry(final String ip, final String resp) throws java.io.IOException {
        final var respObj = objMapper.readValue(resp, GeoLocationResp.class);
        final var country = respObj.getCountry();
        if (StringUtils.isNotBlank(country)) {
            logger.info(String.format("client's ip[%s], country[%s]", ip, country));
        } else {
            logger.debug("client's country is blank or missing");
        }
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
