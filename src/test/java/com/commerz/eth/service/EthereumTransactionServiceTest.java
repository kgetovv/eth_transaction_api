package com.commerz.eth.service;

import com.commerz.eth.entity.EthereumTransaction;
import com.commerz.eth.repository.EthereumTransactionRepository;
import com.commerz.eth.service.dto.EthereumTransactionDTO;
import com.commerz.eth.service.mapper.EthereumTransactionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.TestPropertySource;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
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
@RunWith(MockitoJUnitRunner.class)
public class EthereumTransactionServiceTest {

    @InjectMocks
    private EthereumTransactionService service;

    @Mock
    private EthereumTransactionRepository repository;

    @Mock
    private Web3j web3j;

    @Mock
    private EthereumTransactionMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction() throws Exception {
        EthSendTransaction ethSendTransaction = new EthSendTransaction();
        ethSendTransaction.setResult("0xMockTransactionHash");

        Request<?, EthSendTransaction> request = mock(Request.class);
        Mockito.when(request.send()).thenReturn(ethSendTransaction);

        when(web3j.ethSendTransaction(any(Transaction.class))).thenReturn(request);

        EthereumTransaction transaction = new EthereumTransaction();
        transaction.setAddressFrom("0xAddressFrom");
        transaction.setAddressTo("0xAddressTo");
        transaction.setAmount(new BigDecimal("1.0"));
        transaction.setTransactionHash("0xMockTransactionHash");

        when(repository.save(any(EthereumTransaction.class))).thenReturn(transaction);

        EthereumTransactionDTO createdTransaction = service.createTransaction("0xAddressFrom", "0xAddressTo", new BigDecimal("1.0"));
        assertThat(createdTransaction.getTransactionHash()).isEqualTo("0xMockTransactionHash");
    }

    @Test
    public void testGetAllTransactions() {
        EthereumTransaction transaction = new EthereumTransaction();
        transaction.setAddressFrom("0xAddressFrom");
        transaction.setAddressTo("0xAddressTo");
        transaction.setAmount(new BigDecimal("1.0"));

        when(repository.findAll()).thenReturn(List.of(transaction));

        List<EthereumTransactionDTO> transactions = service.getAllTransactions();
        assertThat(transactions).isNotEmpty();
        assertThat(transactions.get(0)).isEqualTo(mapper.toDTO(transaction));
    }
}
