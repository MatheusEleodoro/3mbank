package com.threembank.transaction.application.usecase;

import com.threembank.transaction.domain.entity.Transaction;
import com.threembank.transaction.interfaces.dto.request.DepositRequest;

public interface DepositUseCase {
    Transaction execute(DepositRequest request);
}
