package eu.isakels.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.isakels.rest.Constants;
import eu.isakels.rest.controller.dto.GeoLocationResp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class GeoLocationServiceImpl implements GeoLocationService {

    private static final Logger logger = LoggerFactory.getLogger(GeoLocationServiceImpl.class);

    private final ObjectMapper objMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public GeoLocationServiceImpl(final ObjectMapper objMapper, final RestTemplate restTemplate) {
        this.objMapper = objMapper;
        this.restTemplate = restTemplate;
    }

    // TODO: create test
    @Override
    public CompletableFuture logCountry(final String ip) {
        return CompletableFuture.runAsync(() -> {
            logger.debug("client's ip: {}", ip);
            requestGeoLocation(ip).map((resp) -> {
                logger.debug("geo location service resp: {}", resp.replaceAll("\n", ""));
                unmarshallAndLogCountry(ip, resp);
                return resp;
            });
        }).exceptionally((exc) -> {
            logger.debug("logCountry future", exc);
            return null;
        });
    }

    private Optional<String> requestGeoLocation(final String ip) {
        // This code doesn't work, because of strange unmarshalling issue
//                final var respOpt = Optional.ofNullable(restTemplate.getForObject(
//                        Constants.geoLocationServiceUrl + ip, GeoLocationResp.class));
        return Optional.ofNullable(restTemplate.getForObject(
                Constants.geoLocationServiceUrl + ip, String.class));
    }

    private void unmarshallAndLogCountry(final String ip, final String resp) {
        try {
            final var respObj = objMapper.readValue(resp, GeoLocationResp.class);
            final var country = respObj.getCountry();
            if (StringUtils.isNotBlank(country)) {
                logger.info(String.format("client's ip[%s], country[%s]", ip, country));
            } else {
                logger.debug("client's country is blank or missing");
            }
        } catch (Exception e) {
            logger.debug("GeoLocationResp unmarshalling", e);
        }
    }
}
