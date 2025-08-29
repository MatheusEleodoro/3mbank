package com.threembank.infrastructure.persistence.repository;

import com.eleodorodev.specification.DynamicFilter;
import com.eleodorodev.specification.DynamicSpecification;
import com.threembank.domain.entity.Account;
import com.threembank.domain.repository.AccountRepository;
import com.threembank.domain.validation.Validatable;
import com.threembank.infrastructure.persistence.entity.AccountEntity;
import com.threembank.infrastructure.persistence.mapper.AccountMapper;
import com.threembank.infrastructure.persistence.repository.jpa.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository repository;
    private final AccountMapper accountMapper;


    @Override
    public boolean exists(Account account) {
        return repository.exists(DynamicSpecification
                .<AccountEntity>where(DynamicFilter.toEquals(account.getNumber(), "number"))
                .and(DynamicFilter.toEquals(account.getType(), "type"))
                .and(DynamicFilter.toEquals(account.getUserId(), "userId")));
    }

    @Override
    public Optional<Account> findById(Long id) {
        return repository.findById(id).map(accountMapper::toDto);
    }

    @Override
    public Collection<Account> findByUser(UUID uuid) {
        return repository.findAll(DynamicSpecification.where(DynamicFilter.toEquals(uuid, "userId")))
                .stream()
                .map(accountMapper::toDto).toList();
    }

    @Override
    public <T extends Account & Validatable> void save(Account account) {
        account.validate();
        repository.save(accountMapper.toEntity(account));
    }


}
