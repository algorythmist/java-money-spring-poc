package com.tecacet.money.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

@Profile("test")
@Configuration
public class TestConfig {

    @Bean
    public ExchangeRateProvider exchangeRateProvider() {
        return new TestingExchangeRateProvider();
    }
}
