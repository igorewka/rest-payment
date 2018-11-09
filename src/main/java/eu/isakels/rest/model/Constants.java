package eu.isakels.rest.model;

// intentionally not using all caps underscore syntax for constants
public abstract class Constants {
    public static final String pathPayments = "/payments";
    public static final String pathVarId = "/{id}";

    public static final String msgSuccessfulCancel = "cancel successful";
    public static final String msgExpiredCancel = "cancel expired";

    public static final String expectedIdMissing = "expected id is missing";

    public static final String timeZoneIdRiga = "Europe/Riga";
}
