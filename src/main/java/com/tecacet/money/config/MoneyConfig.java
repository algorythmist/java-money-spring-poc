package com.tecacet.money.config;

import com.tecacet.money.service.CustomExchangeRateProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

@Configuration
@Profile("!test")
public class MoneyConfig {

    /**
     * Starting Exchange rate services can be expensive, so we so it only once at startup
     * @return The configured exchange rate provider
     */
    @Bean
    public ExchangeRateProvider exchangeRateProvider() {
        return new CustomExchangeRateProvider();
    }
}

