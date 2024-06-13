package com.commerz.eth.controller;

import com.commerz.eth.entity.EthereumTransaction;
import com.commerz.eth.service.EthereumTransactionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/eth-transactions")
public class EthereumTransactionController {

    private final EthereumTransactionService service;

    public EthereumTransactionController(EthereumTransactionService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public EthereumTransaction createTransaction(@RequestParam String addressFrom,
                                                 @RequestParam String addressTo,
                                                 @RequestParam BigDecimal amount) throws Exception {
        return service.createTransaction(addressFrom, addressTo, amount);
    }

    @GetMapping("/all")
    public List<EthereumTransaction> getAllTransactions() {
        return service.getAllTransactions();
    }
}
