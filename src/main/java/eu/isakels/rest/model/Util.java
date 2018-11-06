package eu.isakels.rest.model;

import eu.isakels.rest.model.payment.Types;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Util {

    public static boolean isNegativeOrZero(final BigDecimal amount) {
        return amount.signum() <= 0;
    }

    public static BigDecimal requireNonNullPositive(final BigDecimal amount, final String msg) {
        if (amount == null || isNegativeOrZero(amount)) throw new IllegalArgumentException(msg);

        return amount;
    }

    public static String requireNonNullNotBlank(final String str, final String msg) {
        if (StringUtils.isBlank(str)) throw new IllegalArgumentException(msg);

        return str;
    }

    public static void checkApplicableCurrencies(final Types.Currency currency, final Types.Currency... applicableCurrencies) {
        if (Arrays.stream(applicableCurrencies).noneMatch(curr -> curr.equals(currency))) {
            final var applicableCurrenciesStr = Arrays.stream(applicableCurrencies)
                    .map(curr -> curr.toString())
                    .collect(Collectors.joining(", "));

            throw new IllegalArgumentException(String.format("Only %s currency is applicable", applicableCurrenciesStr));
        }
    }
}
