package com.tecacet.money.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

@Configuration
public class MoneyConfig {

    /**
     * Starting Exchange rate services can be expensive, so we so it only once at startup
     * @return The configured exchange rate provider
     */
    @Bean
    public ExchangeRateProvider exchangeRateProvider() {
        return MonetaryConversions.getExchangeRateProvider("IMF");
    }
}

