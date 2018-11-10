package eu.isakels.rest.model;

// intentionally not using all caps underscore syntax for constants
public abstract class Constants {
    public static final String pathPayments = "/payments";
    public static final String pathVarId = "/{id}";

    public static final String msgSuccessfulCancel = "cancel successful";
    public static final String msgExpiredCancel = "cancel expired";

    public static final String expectedIdMissing = "expected id is missing";
    public static final String expectedCancelFeeMissing = "Expected cancelFee missing";
    public static final String expectedPaymentNotFound = "expected payment[%s] not found";

    public static final String timeZoneIdRiga = "Europe/Riga";

    // TODO: enum for params
    public static final String cancelledParam = "cancelled";
    public static final String amountGtParam = "amountGt";
    public static final String amountLtParam = "amountLt";
}
