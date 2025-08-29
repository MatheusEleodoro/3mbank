package com.threembank.application.service;

import com.threembank.application.usecase.AccountUseCase;
import com.threembank.domain.entity.Account;
import com.threembank.domain.repository.AccountRepository;
import com.threembank.domain.valueobject.AccountStatus;
import com.threembank.infrastructure.security.User;
import com.threembank.interfaces.dto.CreateRequest;
import com.threembank.shared.exception.BasicValidationException;
import com.threembank.shared.exception.NotFoundException;
import com.threembank.shared.message.ValidationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountUseCase {
    private final AccountRepository repository;
    @Value("${services.bussines.agencyNumber}")
    private Long agency;


    @Override
    public void create(CreateRequest request,User user) {
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
    public Account get(Long number) {
        return repository.findById(number)
                .orElse(null);
    }

    @Override
    public void updateStatus(Long number, AccountStatus status) throws NotFoundException {
        var account = repository.findById(number)
                .orElseThrow(() -> new NotFoundException(this.getClass(),ValidationMessage.of("account","Account not found")));
        account.setStatus(status);
        repository.save(account);
    }
}
