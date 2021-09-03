package com.tecacet.money.controller;

import com.tecacet.money.domain.Contract;
import com.tecacet.money.domain.Fee;
import com.tecacet.money.repository.ContractRepository;
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

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InvoiceControllerTest {

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Mocking the exchange rate because:
     * - We need a consistent result
     * - It takes a long time to start an actual provider
     */
    @MockBean
    private ExchangeRateProvider exchangeRateProvider;

    @Test
    void createInvoice_notFound() {
        String clientId = "123";
        ResponseEntity<InvoiceDto> response =
                testRestTemplate.postForEntity("/invoice/" + clientId,
                        clientId,
                        InvoiceDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createInvoice() {
        mockCurrencyConversion();

        String clientId = "123";
        createContract(clientId);

        Fee fee1 = createFee(clientId, 100.33, "USD");
        Fee fee2 = createFee(clientId, 200.45, "EUR");
        Fee fee3 = createFee(clientId, 300.99, "GBP");
        feeRepository.saveAll(List.of(fee1, fee2, fee3));
        ResponseEntity<InvoiceDto> response =
                testRestTemplate.postForEntity("/invoice/" + clientId,
                        clientId,
                        InvoiceDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InvoiceDto invoice = response.getBody();
        assertEquals("USD", invoice.getCurrency());
        assertEquals(737.94, invoice.getAmount().doubleValue(), 0.001);
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
                    return Money.of(amount.getNumber().intValue() * 1.4392, "USD");
                });
        Mockito.when(exchangeRateProvider.getCurrencyConversion("USD"))
                .thenReturn(currencyConversion);
    }

    private Fee createFee(String clientId, double amount, String currency) {
        return Fee.builder()
                .clientId(clientId)
                .amount(Money.of(amount, currency))
                .build();
    }

    private Contract createContract(String clientId) {
        Contract contract = Contract.builder()
                .clientId(clientId)
                .invoiceCurrency(Currency.getInstance("USD"))
                .discountPercent(BigDecimal.TEN)
                .build();
        return contractRepository.save(contract);
    }
}