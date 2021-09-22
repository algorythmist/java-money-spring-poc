package com.tecacet.money;

import com.tecacet.money.config.TestingExchangeRateProvider;
import com.tecacet.money.util.MoneyUtil;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryQueries;
import org.javamoney.moneta.spi.DefaultNumberValue;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryException;
import javax.money.NumberValue;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.RateType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    @Test
    void testCurrencyUnit() {
        MonetaryAmount dollars = Money.of(100.2473, "USD");
        assertEquals("USD", dollars.getCurrency().getCurrencyCode());
        assertEquals(2, dollars.getCurrency().getDefaultFractionDigits());
        assertEquals(840, dollars.getCurrency().getNumericCode());

        MonetaryAmount yen = Money.of(100.2473, "JPY");
        assertEquals("JPY", yen.getCurrency().getCurrencyCode());
        assertEquals(0, yen.getCurrency().getDefaultFractionDigits());
        assertEquals(392, yen.getCurrency().getNumericCode());

        MonetaryAmount dinars = Money.of(100.2473, "BHD");
        assertEquals("BHD", dinars.getCurrency().getCurrencyCode());
        assertEquals(3, dinars.getCurrency().getDefaultFractionDigits());
        assertEquals(48, dinars.getCurrency().getNumericCode());
    }

    @Test
    void testQueries() {
        MonetaryAmount dollars = Money.of(100.2473, "USD");
        assertEquals(10024, dollars.query(MonetaryQueries.convertMinorPart()));
        assertEquals(100, dollars.query(MonetaryQueries.extractMajorPart()));
        assertEquals(24, dollars.query(MonetaryQueries.extractMinorPart()));

        MonetaryAmount yen = Money.of(100.2473, "JPY");
        assertEquals(100, yen.query(MonetaryQueries.convertMinorPart()));
        assertEquals(100, yen.query(MonetaryQueries.extractMajorPart()));
        assertEquals(0, yen.query(MonetaryQueries.extractMinorPart()));

        MonetaryAmount dinars = Money.of(100.2473, "BHD");
        assertEquals(100247, dinars.query(MonetaryQueries.convertMinorPart()));
        assertEquals(100, dinars.query(MonetaryQueries.extractMajorPart()));
        assertEquals(247, dinars.query(MonetaryQueries.extractMinorPart()));

    }

    @Test
    void testRounding() {
        MonetaryAmount dollars = Money.of(100.2473, "USD");
        assertEquals("USD 100.25", dollars.toString());
        MonetaryAmount roundedDollars = dollars.with(Monetary.getDefaultRounding());
        assertEquals("100.25", roundedDollars.getNumber().toString());

        MonetaryAmount euros = Money.of(100.2473, "EUR");
        assertEquals("EUR 100.25", euros.toString());
        MonetaryAmount roundedEuros = euros.with(Monetary.getDefaultRounding());
        assertEquals("100.25", roundedEuros.getNumber().toString());

        MonetaryAmount yen = Money.of(100.2473, "JPY");
        assertEquals("JPY 100.00", yen.toString());
        MonetaryAmount roundedYen = yen.with(Monetary.getDefaultRounding());
        assertEquals("100", roundedYen.getNumber().toString());

        MonetaryAmount dinars = Money.of(100.2473, "BHD");
        assertEquals("BHD 100.25", dinars.toString());
        MonetaryAmount roundedDinars = dinars.with(Monetary.getDefaultRounding());
        assertEquals("100.247", roundedDinars.getNumber().toString());
    }

    @Test
    void testNumberValue() {
        NumberValue numberValue = DefaultNumberValue.of(123.8232);
        assertEquals(10000, numberValue.getAmountFractionDenominator());
        assertEquals(8232, numberValue.getAmountFractionNumerator());
        assertEquals(7, numberValue.getPrecision());
        assertEquals(Double.class, numberValue.getNumberType());
    }

    @Test
    void testCurrencyConversion() {
        ExchangeRateProvider exchangeRateProvider = new TestingExchangeRateProvider();

        ExchangeRate exchangeRate = exchangeRateProvider.getExchangeRate("USD", "JPY");
        assertEquals(1.368, exchangeRate.getFactor().doubleValueExact(), 0.0001);

        CurrencyConversion currencyConversion = exchangeRateProvider.getCurrencyConversion("USD");
        assertEquals(RateType.ANY, currencyConversion.getContext().getRateType());

        MonetaryAmount result = Money.of(70.2145862, "EUR").with(currencyConversion);
        assertEquals(60.04751411824, MoneyUtil.extractAmount(result).doubleValue(), 0.0000001);
    }

    @Test
    void testAddition() {
        MonetaryAmount us = Money.of(100.2473, "USD");
        MonetaryAmount canada = Money.of(23.20, "CAD");
        assertThrows(MonetaryException.class, () -> canada.add(us));
    }


}
