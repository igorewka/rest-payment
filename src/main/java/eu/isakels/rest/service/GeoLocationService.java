package eu.isakels.rest.service;

import java.util.concurrent.CompletableFuture;

public interface GeoLocationService {

    CompletableFuture logCountry(final String ip);
}
