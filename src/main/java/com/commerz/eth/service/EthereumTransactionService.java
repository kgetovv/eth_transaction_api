package com.commerz.eth.service;

import com.commerz.eth.entity.EthereumTransaction;
import com.commerz.eth.repository.EthereumTransactionRepository;
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

    @Value("${ethereum.gas.limit}")
    private BigInteger gasLimit;

    @Value("${ethereum.gas.price}")
    private BigInteger gasPriceGwei;

    public EthereumTransactionService(EthereumTransactionRepository repository, Web3j web3j) {
        this.repository = repository;
        this.web3j = web3j;
    }

    public EthereumTransaction createTransaction(String addressFrom, String addressTo, BigDecimal amount) throws Exception {
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

        return repository.save(ethTransaction);
    }

    public List<EthereumTransaction> getAllTransactions() {
        return repository.findAll();
    }
}
