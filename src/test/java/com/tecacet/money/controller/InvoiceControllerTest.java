package com.tecacet.money.controller;

import com.tecacet.money.domain.Contract;
import com.tecacet.money.domain.Fee;
import com.tecacet.money.repository.ContractRepository;
import com.tecacet.money.repository.FeeRepository;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InvoiceControllerTest {

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void createInvoice_notFound() {
        var clientId = "123";
        ResponseEntity<InvoiceDto> response =
                testRestTemplate.postForEntity("/invoice/" + clientId,
                        clientId,
                        InvoiceDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createInvoice() {
        var clientId = "123";
        createContract(clientId);

        var fee1 = createFee(clientId, 100.12, "USD");
        var fee2 = createFee(clientId, 150.55, "EUR");
        var fee3 = createFee(clientId, 200.77, "GBP");
        feeRepository.saveAll(List.of(fee1, fee2, fee3));
        ResponseEntity<InvoiceDto> response =
                testRestTemplate.postForEntity("/invoice/" + clientId,
                        clientId,
                        InvoiceDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InvoiceDto invoice = response.getBody();
        assertEquals("USD", invoice.getCurrency());
        assertEquals(453.17, invoice.getAmount().doubleValue(), 0.0001);
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