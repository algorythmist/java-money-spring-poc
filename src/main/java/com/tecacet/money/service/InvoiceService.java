package com.tecacet.money.service;

import com.tecacet.money.domain.Contract;
import com.tecacet.money.domain.Fee;
import com.tecacet.money.domain.Invoice;
import com.tecacet.money.repository.ContractRepository;
import com.tecacet.money.repository.FeeRepository;
import com.tecacet.money.repository.InvoiceRepository;
import com.tecacet.money.util.MoneyUtil;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final FeeRepository feeRepository;
    private final InvoiceRepository invoiceRepository;
    private final ContractRepository contractRepository;
    private final ExchangeRateProvider exchangeRateProvider;

    public Optional<Invoice> createClientInvoice(String clientId, LocalDate date) {
        List<Fee> fees = feeRepository.findByClientId(clientId);
        if (fees.isEmpty()) {
            return Optional.empty();
        }
        Optional<Contract> optionalContract = contractRepository.findByClientId(clientId);
        if (optionalContract.isEmpty()) {
            return Optional.empty();
        }
        Contract contract = optionalContract.get();
        String invoiceCurrency = contract.getInvoiceCurrency().toString();
        CurrencyConversion conversion = exchangeRateProvider.getCurrencyConversion(invoiceCurrency);
        MonetaryAmount total =
                fees.stream()
                        .map(fee -> getMonetaryAmount(invoiceCurrency, conversion, fee))
                        .reduce(Money.of(0, invoiceCurrency), MonetaryAmount::add);

        total = total.subtract(total.multiply(contract.getDiscountPercent()).divide(BigDecimal.valueOf(100)));
        total = total.with(Monetary.getDefaultRounding());
        Invoice invoice = createInvoice(clientId, date, total);
        return Optional.of(invoice);
    }

    private Invoice createInvoice(String clientId, LocalDate date, MonetaryAmount total) {
        Invoice invoice = Invoice.builder()
                .clientId(clientId)
                .total(total)
                .invoiceDate(date)
                .dueDate(date.plusMonths(1))
                .build();
        return invoiceRepository.save(invoice);
    }

    private MonetaryAmount getMonetaryAmount(String invoiceCurrency, CurrencyConversion conversion, Fee fee) {
        String currencyCode = MoneyUtil.extractCurrencyCode(fee.getAmount());
        return invoiceCurrency.equals(currencyCode) ? fee.getAmount() : conversion.apply(fee.getAmount());
    }

}
