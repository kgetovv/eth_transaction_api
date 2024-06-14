package com.commerz.eth.controller;

import com.commerz.eth.service.EthereumTransactionService;
import com.commerz.eth.service.dto.EthereumTransactionDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eth-transactions")
public class EthereumTransactionController {

    private final EthereumTransactionService service;

    public EthereumTransactionController(EthereumTransactionService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<EthereumTransactionDTO> createTransaction(@RequestBody @Valid EthereumTransactionDTO requestBody) throws Exception {
        return ResponseEntity.ok(service.createTransaction(requestBody.getAddressFrom(), requestBody.getAddressTo(), requestBody.getAmount()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EthereumTransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(service.getAllTransactions());
    }
}
