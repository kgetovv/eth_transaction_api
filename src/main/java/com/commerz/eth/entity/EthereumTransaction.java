package com.commerz.eth.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ethereum_transaction")
public class EthereumTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "et_id")
    private Long id;

    @Column(name = "et_address_from")
    private String addressFrom;

    @Column(name = "et_address_to")
    private String addressTo;

    @Column(name = "et_amount")
    private BigDecimal amount;

    @Column(name = "et_transaction_hash")
    private String transactionHash;
}
