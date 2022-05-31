package com.tecacet.money.repository;

import com.tecacet.money.domain.Fee;
import com.tecacet.money.util.MoneyUtil;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.money.Monetary;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FeeRepositoryTest {

    @Autowired
    private FeeRepository feeRepository;

    @Test
    void findByClientId() {
        Fee fee1 = createFee("1", 100.0, "USD");
        Fee fee2 = createFee("2", 200.0, "EUR");
        Fee fee3 = createFee("1", 300.0, "GBP");
        feeRepository.saveAll(List.of(fee1, fee2, fee3));

        List<Fee> client1Fees = feeRepository.findByClientId("1");
        assertEquals(2, client1Fees.size());

        List<Fee> client2Fees = feeRepository.findByClientId("2");
        assertEquals(1, client2Fees.size());
        Fee fee = client2Fees.get(0);
        assertNotNull(fee.getCreated());
        assertEquals(200.00, MoneyUtil.extractAmount(fee.getAmount()).doubleValue(), 0.001);
        assertEquals(Monetary.getCurrency("EUR"), fee.getAmount().getCurrency());
    }

    private Fee createFee(String clientId, double amount, String currency) {
        Fee fee = new Fee();
        fee.setClientId(clientId);
        fee.setAmount(Money.of(amount, currency));
        return fee;
    }
}