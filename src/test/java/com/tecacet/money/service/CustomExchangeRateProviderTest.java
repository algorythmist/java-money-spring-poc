package com.tecacet.money.service;

import org.junit.jupiter.api.Test;

import javax.money.convert.ExchangeRateProvider;

import static org.junit.jupiter.api.Assertions.*;

class CustomExchangeRateProviderTest {

    @Test
    void getExchangeRate() {
        ExchangeRateProvider exchangeRateProvider = new CustomExchangeRateProvider();
        var rate = exchangeRateProvider.getExchangeRate("USD", "GBP");
        var doubleRate = rate.getFactor().doubleValue();
        assertTrue(doubleRate < 1.0);
    }
}