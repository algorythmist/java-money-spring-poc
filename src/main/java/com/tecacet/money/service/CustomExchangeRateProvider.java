package com.tecacet.money.service;

import com.tecacet.finance.service.currency.CurrencyExchangeService;
import com.tecacet.finance.service.currency.GrandtrunkCurrencyExchangeService;
import org.javamoney.moneta.convert.ExchangeRateBuilder;
import org.javamoney.moneta.spi.AbstractRateProvider;
import org.javamoney.moneta.spi.DefaultNumberValue;

import javax.money.CurrencyUnit;
import javax.money.convert.ConversionQuery;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ProviderContext;
import javax.money.convert.RateType;

public class CustomExchangeRateProvider extends AbstractRateProvider {

    public CustomExchangeRateProvider() {
        super(ProviderContext.of("GRAND"));
    }

    @Override
    public ExchangeRate getExchangeRate(ConversionQuery conversionQuery) {
        CurrencyUnit baseCurrency = conversionQuery.getBaseCurrency();
        CurrencyUnit currency = conversionQuery.getCurrency();
        CurrencyExchangeService currencyExchangeService =
                new GrandtrunkCurrencyExchangeService();
        double rate = currencyExchangeService.getCurrentExchangeRate(baseCurrency.getCurrencyCode(),
                currency.getCurrencyCode());
        return new ExchangeRateBuilder(getContext().getProviderName(), RateType.ANY)
                .setBase(baseCurrency)
                .setTerm(conversionQuery.getCurrency())
                .setFactor(DefaultNumberValue.of(rate))
                .build();
    }
}
