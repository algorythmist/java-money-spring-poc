package com.tecacet.money.service;

import com.tecacet.money.domain.Fee;
import com.tecacet.money.domain.Invoice;
import com.tecacet.money.repository.FeeRepository;
import com.tecacet.money.repository.InvoiceRepository;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final FeeRepository feeRepository;
    private final InvoiceRepository invoiceRepository;
    private final ExchangeRateProvider exchangeRateProvider;

    public Optional<Invoice> createClientInvoice(String clientId, LocalDate date,
            String invoiceCurrency) {
        List<Fee> fees = feeRepository.findByClientId(clientId);
        if (fees.isEmpty()) {
            return Optional.empty();
        }
        CurrencyConversion conversion = exchangeRateProvider.getCurrencyConversion(invoiceCurrency);
        Money total = Money.of(0, invoiceCurrency);
        for (Fee fee : fees) {
            String currencyCode = fee.getAmount().getCurrency().getCurrencyCode();
            MonetaryAmount amount = invoiceCurrency.equals(currencyCode) ?
                    fee.getAmount() :
                    conversion.apply(fee.getAmount());
            total = total.add(amount);
        }
        Invoice invoice = new Invoice();
        invoice.setClientId(clientId);
        invoice.setTotal(total);
        invoice.setInvoiceDate(date);
        invoice.setDueDate(date.plusMonths(1));
        invoiceRepository.save(invoice);
        return Optional.of(invoice);
    }

}
