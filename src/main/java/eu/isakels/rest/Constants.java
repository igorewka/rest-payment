package eu.isakels.rest;

// intentionally not using all caps underscore syntax for constants
public abstract class Constants {
    public static final String pathPayments = "/payments";
    public static final String pathVarId = "/{id}";
    // TODO: enum for params
    public static final String cancelledParam = "cancelled";
    public static final String amountGtParam = "amountGt";
    public static final String amountLtParam = "amountLt";
    // TODO: enum for service urls
    public static final String geoLocationServiceUrl = "http://extreme-ip-lookup.com/json/";
    public static final String type1ServiceUrl = "http://www.google.com/";
    public static final String type2ServiceUrl = "http://www.bing.com/";
}
