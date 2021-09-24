package com.tecacet.money.util;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

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


    public static List<CurrencyUnit> getAllCurrencies() {

        List<CurrencyUnit> currencies =
                Currency.getAvailableCurrencies().stream()
                        .map(c -> Money.of(1, c.getCurrencyCode()))
                        .map(MonetaryAmount::getCurrency)
                        .sorted()
                        .collect(Collectors.toList());

        currencies.forEach(c -> System.out.printf("%s %d\n", c.getCurrencyCode(), c.getDefaultFractionDigits()));
        return currencies;
    }
}
