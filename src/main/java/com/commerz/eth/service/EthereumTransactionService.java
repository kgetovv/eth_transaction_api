package com.commerz.eth.service;

import com.commerz.eth.entity.EthereumTransaction;
import com.commerz.eth.repository.EthereumTransactionRepository;
import com.commerz.eth.service.dto.EthereumTransactionDTO;
import com.commerz.eth.service.mapper.EthereumTransactionMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class EthereumTransactionService {

    private final EthereumTransactionRepository repository;
    private final Web3j web3j;
    private final EthereumTransactionMapper transactionMapper;

    @Value("${ethereum.gas.limit}")
    private BigInteger gasLimit;

    @Value("${ethereum.gas.price}")
    private BigInteger gasPriceGwei;

    public EthereumTransactionService(EthereumTransactionRepository repository, Web3j web3j, EthereumTransactionMapper transactionMapper) {
        this.repository = repository;
        this.web3j = web3j;
        this.transactionMapper = transactionMapper;
    }

    public EthereumTransactionDTO createTransaction(String addressFrom, String addressTo, BigDecimal amount) throws Exception {

        if (gasPriceGwei == null) {
            throw new IllegalArgumentException("Gas price (ethereum.gas.price) is not configured properly.");
        }

        BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
        BigInteger gasPrice = Convert.toWei(gasPriceGwei.toString(), Convert.Unit.GWEI).toBigInteger();

        Transaction transaction = Transaction.createEtherTransaction(addressFrom, null, gasPrice, gasLimit, addressTo, value);

        EthSendTransaction response = web3j.ethSendTransaction(transaction).send();
        String transactionHash = response.getTransactionHash();

        EthereumTransaction ethTransaction = new EthereumTransaction();
        ethTransaction.setAddressFrom(addressFrom);
        ethTransaction.setAddressTo(addressTo);
        ethTransaction.setAmount(amount);
        ethTransaction.setTransactionHash(transactionHash);

        EthereumTransaction savedTransaction = repository.save(ethTransaction);
        return this.transactionMapper.toDTO(savedTransaction);
    }

    public List<EthereumTransactionDTO> getAllTransactions() {
        return this.repository.findAll().stream().map(this.transactionMapper::toDTO).toList();
    }
}
