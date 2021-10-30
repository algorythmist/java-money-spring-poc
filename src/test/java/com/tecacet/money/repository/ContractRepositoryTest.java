package com.tecacet.money.repository;

import com.tecacet.money.domain.Contract;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContractRepositoryTest {

    @Autowired
    private ContractRepository contractRepository;

    @Test
    void findByClientId() {
        String client1 = "client1";
        String client2 = "client2";
        createContract(client2, Currency.getInstance("USD"));
        assertFalse(contractRepository.findByClientId(client1).isPresent());
        assertTrue(contractRepository.findByClientId(client2).isPresent());

        Contract contract = contractRepository.findByClientId(client2).get();
        assertEquals(new BigDecimal("0.00"), contract.getDiscountPercent());
    }

    @Test
    void missingRequiredProperty() {
        assertThrows(Exception.class,
                () -> createContract("client", null));
    }

    private Contract createContract(String clientId, Currency currency) {
        Contract contract = new Contract();
        contract.setClientId(clientId);
        contract.setInvoiceCurrency(currency);
        return contractRepository.save(contract);
    }
}
