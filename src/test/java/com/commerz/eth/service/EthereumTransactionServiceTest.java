package com.commerz.eth.service;

import com.commerz.eth.entity.EthereumTransaction;
import com.commerz.eth.repository.EthereumTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestPropertySource(properties = {
        "ethereum.gas.limit=21000",
        "ethereum.gas.price=20"
})
public class EthereumTransactionServiceTest {

    @InjectMocks
    private EthereumTransactionService service;

    @Mock
    private EthereumTransactionRepository repository;

    @Mock
    private Web3j web3j;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction() throws Exception {
        EthereumTransaction transaction = new EthereumTransaction();
        transaction.setAddressFrom("0xAddressFrom");
        transaction.setAddressTo("0xAddressTo");
        transaction.setAmount(new BigDecimal("1.0"));
        transaction.setTransactionHash("0xMockTransactionHash");

        EthSendTransaction ethSendTransaction = mock(EthSendTransaction.class);
        when(ethSendTransaction.getTransactionHash()).thenReturn("0xMockTransactionHash");

        when(web3j.ethSendTransaction(any(Transaction.class))).thenReturn(ethSendTransaction);

        when(repository.save(any(EthereumTransaction.class))).thenReturn(transaction);

        EthereumTransaction createdTransaction = service.createTransaction("0xAddressFrom", "0xAddressTo", new BigDecimal("1.0"));
        assertThat(createdTransaction.getTransactionHash()).isEqualTo("0xMockTransactionHash");
    }

    @Test
    public void testGetAllTransactions() {
        EthereumTransaction transaction = new EthereumTransaction();
        transaction.setAddressFrom("0xAddressFrom");
        transaction.setAddressTo("0xAddressTo");
        transaction.setAmount(new BigDecimal("1.0"));

        when(repository.findAll()).thenReturn(List.of(transaction));

        List<EthereumTransaction> transactions = service.getAllTransactions();
        assertThat(transactions).isNotEmpty();
        assertThat(transactions.get(0)).isEqualTo(transaction);
    }
}
