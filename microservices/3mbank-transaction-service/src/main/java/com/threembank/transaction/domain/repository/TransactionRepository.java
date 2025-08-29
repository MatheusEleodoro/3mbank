package com.threembank.transaction.domain.repository;

import com.threembank.transaction.domain.entity.Transaction;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
}
