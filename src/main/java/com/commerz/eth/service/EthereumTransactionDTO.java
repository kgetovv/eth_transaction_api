package com.commerz.eth.service;

import lombok.*;

import java.math.BigDecimal;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
public class EthereumTransactionDTO {

    private Long id;
    private String addressFrom;
    private String addressTo;
    private BigDecimal amount;
    private String transactionHash;
}
