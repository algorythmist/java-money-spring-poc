package com.tecacet.money.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.tecacet.money.domain.Fee;
import com.tecacet.money.repository.FeeRepository;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class InvoiceControllerTest {

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ExchangeRateProvider exchangeRateProvider;

    @Test
    void createInvoice_notFound() {
        String clientId = "123";
        ResponseEntity<InvoiceDto> response =
                testRestTemplate.postForEntity("/invoice/"+clientId,
                        clientId,
                        InvoiceDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createInvoice() {
        mockCurrencyConversion();

        String clientId = "123";
        Fee fee1 = createFee(clientId, 100.0, "USD");
        Fee fee2 = createFee(clientId, 200.0, "EUR");
        Fee fee3 = createFee(clientId, 300.0, "GBP");
        feeRepository.saveAll(List.of(fee1, fee2, fee3));
        ResponseEntity<InvoiceDto> response =
                testRestTemplate.postForEntity("/invoice/"+clientId,
                        clientId,
                        InvoiceDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InvoiceDto invoice = response.getBody();
        assertEquals("USD", invoice.getCurrency());
        assertEquals(850.00, invoice.getAmount().doubleValue(), 0.001);
    }

    /**
     * Mock external dependency
     */
    private void mockCurrencyConversion() {
        CurrencyConversion currencyConversion = Mockito.mock(CurrencyConversion.class);
        Mockito.when(currencyConversion.apply(Mockito.any(MonetaryAmount.class)))
                .thenAnswer((Answer<MonetaryAmount>) invocation -> {
                    Object[] args = invocation.getArguments();
                    MonetaryAmount amount = (MonetaryAmount) args[0];
                    return Money.of(amount.getNumber().intValue() * 1.5, "USD");
                });
        Mockito.when(exchangeRateProvider.getCurrencyConversion("USD"))
                .thenReturn(currencyConversion);
    }

    private Fee createFee(String clientId, double amount, String currency) {
        Fee fee = new Fee();
        fee.setClientId(clientId);
        fee.setAmount(Money.of(amount, currency));
        return fee;
    }
}