package com.threembank.application.usecase;

import com.threembank.core.security.model.User;
import com.threembank.domain.entity.Account;
import com.threembank.domain.valueobject.AccountStatus;

import com.threembank.interfaces.dto.CreateRequest;
import com.threembank.shared.exception.NotFoundException;


import java.util.Collection;

public interface AccountUseCase {

    void create(CreateRequest request, User user);
    Collection<Account> getAll(User user);
    Account get(User user,Long number);
    void updateStatus(User user,Long number, AccountStatus status) throws NotFoundException;
}
