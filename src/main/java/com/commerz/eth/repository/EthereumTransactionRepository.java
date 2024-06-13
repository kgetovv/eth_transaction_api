package com.commerz.eth.repository;

import com.commerz.eth.entity.EthereumTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EthereumTransactionRepository extends JpaRepository<EthereumTransaction, Long> {
}
