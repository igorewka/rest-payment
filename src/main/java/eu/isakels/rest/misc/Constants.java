package eu.isakels.rest.misc;

// intentionally not using all caps underscore syntax for constants
public abstract class Constants {

    public static final String pathPayments = "/payments";
    public static final String pathVarId = "/{id}";
    // TODO: enum for params
    public static final String cancelledParam = "cancelled";
    public static final String amountGtParam = "amount-gt";
    public static final String amountLtParam = "amount-lt";

    // TODO: enum for service urls or move to DB if they need to change runtime
    public static final String geoLocationServiceUrl = "http://extreme-ip-lookup.com/json/";
    public static final String type1ServiceUrl = "http://www.google.com/";
    public static final String type2ServiceUrl = "http://www.bing.com/";

    public static final String expectedPaymentMssing = "expected payment missing";
    public static final String unknownPaymentType = "unknown payment type";
    public static final String msgSuccessfulCancel = "cancel successful";
    public static final String msgExpiredCancel = "cancel expired";

    private Constants() {
    }
}
