package com.commerz.eth;

import com.commerz.eth.entity.EthereumTransaction;
import com.commerz.eth.repository.EthereumTransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EthereumTransactionControllerIntegrationTest {

    private final TestRestTemplate restTemplate;
    private final EthereumTransactionRepository repository;

    public EthereumTransactionControllerIntegrationTest(TestRestTemplate restTemplate, EthereumTransactionRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @Test
    public void testCreateTransaction() {
        String addressFrom = "0xAddressFrom";
        String addressTo = "0xAddressTo";
        BigDecimal amount = new BigDecimal("1.0");

        ResponseEntity<EthereumTransaction> response = restTemplate.postForEntity("/api/eth-transactions/create?addressFrom=" + addressFrom + "&addressTo=" + addressTo + "&amount=" + amount, null, EthereumTransaction.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        EthereumTransaction transaction = response.getBody();
        assertThat(transaction).isNotNull();
        assertThat(transaction.getAddressFrom()).isEqualTo(addressFrom);
        assertThat(transaction.getAddressTo()).isEqualTo(addressTo);
        assertThat(transaction.getAmount()).isEqualTo(amount);

        // Clean up
        repository.deleteAll();
    }

    @Test
    public void testGetAllTransactions() {
        // Add a transaction to the repository for testing
        EthereumTransaction transaction = new EthereumTransaction();
        transaction.setAddressFrom("0xAddressFrom");
        transaction.setAddressTo("0xAddressTo");
        transaction.setAmount(new BigDecimal("1.0"));
        repository.save(transaction);

        ResponseEntity<EthereumTransaction[]> response = restTemplate.getForEntity("/api/eth-transactions/all", EthereumTransaction[].class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        EthereumTransaction[] transactions = response.getBody();
        assertThat(transactions).isNotEmpty();
        assertThat(transactions[0].getAddressFrom()).isEqualTo("0xAddressFrom");
        assertThat(transactions[0].getAddressTo()).isEqualTo("0xAddressTo");
        assertThat(transactions[0].getAmount()).isEqualTo(new BigDecimal("1.0"));

        // Clean up
        repository.deleteAll();
    }
}
