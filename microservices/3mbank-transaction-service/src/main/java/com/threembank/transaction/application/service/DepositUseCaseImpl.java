package com.threembank.transaction.application.service;

import com.threembank.transaction.application.usecase.DepositUseCase;
import com.threembank.transaction.domain.entity.Transaction;
import com.threembank.transaction.interfaces.dto.request.DepositRequest;
import org.springframework.stereotype.Service;

@Service
public class DepositUseCaseImpl implements DepositUseCase {

    @Override
    public Transaction execute(DepositRequest request) {
        return null;
    }
}
