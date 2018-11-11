package eu.isakels.rest.misc;

import eu.isakels.rest.misc.Types;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Util {

    public static boolean isNegativeOrZero(final BigDecimal amount) {
        return amount != null && amount.signum() <= 0;
    }

    public static BigDecimal requireNonNullPositive(final BigDecimal amount, final String msg) {
        if (amount == null || isNegativeOrZero(amount)) throw new IllegalArgumentException(msg);

        return amount;
    }

    public static <T> T requireNonNull(final T obj, final String msg) {
        if (obj == null) throw new IllegalArgumentException(msg);

        return obj;
    }

    public static String requireNonNullNotBlank(final String str, final String msg) {
        if (StringUtils.isBlank(str)) throw new IllegalArgumentException(msg);

        return str;
    }

    public static <T> boolean nonNullNotBlank(final T obj) {
        return obj != null && StringUtils.isNotBlank(obj.toString());
    }

    public static void checkApplicableCurrencies(final Types.Currency currency, final Types.Currency... applicableCurrencies) {
        if (Arrays.stream(applicableCurrencies).noneMatch(curr -> curr.equals(currency))) {
            final var applicableCurrenciesStr = Arrays.stream(applicableCurrencies)
                    .map(curr -> curr.toString())
                    .collect(Collectors.joining(", "));

            throw new IllegalArgumentException(String.format("Only %s currency is applicable", applicableCurrenciesStr));
        }
    }

    public static BigDecimal setScale(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.FLOOR);
    }
}
