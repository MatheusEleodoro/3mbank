package com.threembank.transaction.infrastructure.persistence.mapper;

import com.threembank.transaction.domain.entity.Transaction;
import com.threembank.transaction.infrastructure.persistence.entity.TransactionEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    TransactionEntity toEntity(Transaction transaction);
    Transaction toDomain(TransactionEntity transactionEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TransactionEntity partialUpdate(Transaction transaction, @MappingTarget TransactionEntity transactionEntity);
}