package eu.isakels.rest.model;

import eu.isakels.rest.misc.Types;

import java.math.BigDecimal;
import java.util.Map;

// intentionally not using all caps underscore syntax for constants
public abstract class ModelConstants {

    public static final String expectedIdMissing = "expected id is missing";
    public static final String expectedCancelFeeMissing = "Expected cancelFee missing";

    public static final String timeZoneIdRiga = "Europe/Riga";

    // TODO: move to DB
    public static final Map<Types.PaymentType, BigDecimal> coefficients =
            Map.ofEntries(
                    Map.entry(Types.PaymentType.TYPE1, new BigDecimal("0.05")),
                    Map.entry(Types.PaymentType.TYPE2, new BigDecimal("0.1")),
                    Map.entry(Types.PaymentType.TYPE3, new BigDecimal("0.15")));
}
