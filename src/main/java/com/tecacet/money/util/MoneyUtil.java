package com.tecacet.money.util;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

/**
 * Extract BigDecimal and currency from potentially null monetary amounts
 */
public class MoneyUtil {

    private MoneyUtil() {

    }

    public static BigDecimal extractAmount(MonetaryAmount monetaryAmount) {
        if (monetaryAmount == null) {
            return null;
        }
        return monetaryAmount.getNumber().numberValue(BigDecimal.class);
    }

    public static String extractCurrencyCode(MonetaryAmount monetaryAmount) {
        if (monetaryAmount == null) {
            return null;
        }
        return monetaryAmount.getCurrency().getCurrencyCode();
    }
}
