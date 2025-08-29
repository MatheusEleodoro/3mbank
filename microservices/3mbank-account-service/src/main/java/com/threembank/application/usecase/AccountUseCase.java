package com.threembank.application.usecase;

import com.threembank.domain.entity.Account;
import com.threembank.domain.valueobject.AccountStatus;
import com.threembank.infrastructure.security.User;
import com.threembank.interfaces.dto.CreateRequest;
import com.threembank.shared.exception.NotFoundException;


import java.util.Collection;

public interface AccountUseCase {

    void create(CreateRequest request, User user);
    Collection<Account> getAll(User user);
    Account get(Long number);
    void updateStatus(Long number, AccountStatus status) throws NotFoundException;
}
