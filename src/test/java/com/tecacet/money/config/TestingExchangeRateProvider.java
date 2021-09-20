package com.tecacet.money.config;

import org.javamoney.moneta.convert.ExchangeRateBuilder;
import org.javamoney.moneta.spi.AbstractRateProvider;
import org.javamoney.moneta.spi.DefaultNumberValue;

import javax.money.CurrencyUnit;
import javax.money.convert.ConversionQuery;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ProviderContext;
import javax.money.convert.RateType;

public class TestingExchangeRateProvider extends AbstractRateProvider {

    public TestingExchangeRateProvider() {
        super(ProviderContext.of("TEST"));
    }

    @Override
    public ExchangeRate getExchangeRate(ConversionQuery conversionQuery) {
        CurrencyUnit baseCurrency = conversionQuery.getBaseCurrency();
        CurrencyUnit fromCurrency = conversionQuery.getCurrency();
        double factor = baseCurrency.equals(fromCurrency) ? 1.0 : 1.4396;
        return new ExchangeRateBuilder(getContext().getProviderName(), RateType.ANY)
                .setBase(baseCurrency)
                .setTerm(conversionQuery.getCurrency())
                .setFactor(DefaultNumberValue.of(factor))
                .build();
    }
}
