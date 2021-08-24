package com.tecacet.money.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tecacet.money.domain.Contract;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.money.convert.ExchangeRateProvider;

@SpringBootTest
public class ContractRepositoryTest {

    @Autowired
    private ContractRepository contractRepository;

    @MockBean
    private ExchangeRateProvider exchangeRateProvider;

    @Test
    void findByClientId() {
        String client1 = "client1";
        String client2 = "client2";
        createContract(client2);
        assertFalse(contractRepository.findByClientId(client1).isPresent());
        assertTrue(contractRepository.findByClientId(client2).isPresent());
    }

    private Contract createContract(String clientId) {
        Contract contract = new Contract();
        contract.setClientId(clientId);
        return contractRepository.save(contract);
    }
}
