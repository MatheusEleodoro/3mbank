package com.threembank.application.service;

import com.threembank.application.usecase.AccountUseCase;
import com.threembank.core.security.model.User;
import com.threembank.domain.entity.Account;
import com.threembank.domain.repository.AccountRepository;
import com.threembank.domain.valueobject.AccountStatus;

import com.threembank.interfaces.dto.CreateRequest;
import com.threembank.shared.exception.BasicValidationException;
import com.threembank.shared.exception.NotFoundException;
import com.threembank.shared.message.ValidationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountUseCase {
    private final AccountRepository repository;
    @Value("${configurations.bussines.agencyNumber}")
    private Long agency;


    @Override
    public void create(CreateRequest request, User user) {
        var account = Account.create(user.id(),request.getType(), agency);
        if(repository.exists(account))
            throw new BasicValidationException(getClass(),ValidationMessage.of("account","Account already exists"));

        repository.save(account);
    }

    @Override
    public Collection<Account> getAll(User user) {
        return repository.findByUser(user.id());
    }

    @Override
    public Account get(User user,Long number) {
        return repository.findById(user.id(),number)
                .orElse(null);
    }

    @Override
    public void updateStatus(User user,Long number, AccountStatus status) throws NotFoundException {
        var account = repository.findById(user.id(),number)
                .orElseThrow(() -> new NotFoundException(this.getClass(),ValidationMessage.of("account","Account not found")));
        account.setStatus(status);
        repository.save(account);
    }
}
