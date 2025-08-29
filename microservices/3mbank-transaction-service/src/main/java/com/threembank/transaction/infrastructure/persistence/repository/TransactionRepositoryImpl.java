package com.threembank.transaction.infrastructure.persistence.repository;

import com.threembank.transaction.domain.entity.Transaction;
import com.threembank.transaction.domain.repository.TransactionRepository;
import com.threembank.transaction.infrastructure.persistence.mapper.TransactionMapper;
import com.threembank.transaction.infrastructure.persistence.repository.jpa.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {
    private final TransactionJpaRepository repository;
    private final TransactionMapper transactionMapper;

    @Override
    public Transaction save(Transaction transaction) {
        var entity = transactionMapper.toEntity(transaction);
            entity = repository.save(entity);

        return transactionMapper.toDomain(entity);
    }
}
