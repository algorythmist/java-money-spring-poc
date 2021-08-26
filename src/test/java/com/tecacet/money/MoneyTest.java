package com.tecacet.money;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.convert.IdentityRateProvider;
import org.javamoney.moneta.function.MonetaryQueries;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

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
    void testCurrencyConversion() {
        ExchangeRateProvider exchangeRateProvider = new IdentityRateProvider();
    }
}
