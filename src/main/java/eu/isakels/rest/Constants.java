package eu.isakels.rest;

import eu.isakels.rest.model.payment.Types;

import java.math.BigDecimal;
import java.util.Map;

// intentionally not using all caps underscore syntax for constants
public abstract class Constants {
    public static final String pathPayments = "/payments";
    public static final String pathVarId = "/{id}";
    // TODO: enum for params
    public static final String cancelledParam = "cancelled";
    public static final String amountGtParam = "amountGt";
    public static final String amountLtParam = "amountLt";

    // TODO: move to DB
    public static final Map<Types.PaymentType, BigDecimal> coefficients =
            Map.ofEntries(
                    Map.entry(Types.PaymentType.TYPE1, new BigDecimal("0.05")),
                    Map.entry(Types.PaymentType.TYPE2, new BigDecimal("0.1")),
                    Map.entry(Types.PaymentType.TYPE3, new BigDecimal("0.15")));

    // TODO: enum for service urls or move to DB if they need to change runtime
    public static final String geoLocationServiceUrl = "http://extreme-ip-lookup.com/json/";
    public static final String type1ServiceUrl = "http://www.google.com/";
    public static final String type2ServiceUrl = "http://www.bing.com/";
}
