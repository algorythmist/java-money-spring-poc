package com.tecacet.money.config;

import org.javamoney.moneta.convert.ExchangeRateBuilder;
import org.javamoney.moneta.spi.AbstractRateProvider;
import org.javamoney.moneta.spi.DefaultNumberValue;

import java.math.BigDecimal;

import javax.money.convert.ConversionQuery;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ProviderContext;
import javax.money.convert.RateType;

public class TestingExchangeRateProvider extends AbstractRateProvider {

    public TestingExchangeRateProvider() {
        super(ProviderContext.of("ONE"));
    }

    @Override
    public ExchangeRate getExchangeRate(ConversionQuery conversionQuery) {
        ExchangeRateBuilder builder = new ExchangeRateBuilder(getContext().getProviderName(), RateType.OTHER)
                .setBase(conversionQuery.getBaseCurrency());
        builder.setTerm(conversionQuery.getCurrency());
        builder.setFactor(DefaultNumberValue.of(BigDecimal.ONE)); //TODO
        return builder.build();
    }
}
