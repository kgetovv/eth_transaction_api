package com.commerz.eth.service.mapper;

import com.commerz.eth.entity.EthereumTransaction;
import com.commerz.eth.service.dto.EthereumTransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EthereumTransactionMapper {


    EthereumTransactionDTO toDTO(EthereumTransaction entity);

    EthereumTransaction toEntity(EthereumTransactionDTO dto);

}
