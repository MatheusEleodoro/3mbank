package com.threembank.domain.repository;

import com.threembank.domain.entity.Account;
import com.threembank.domain.validation.Validatable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface

AccountRepository {
    boolean exists(Account account);

    Optional<Account> findById(Long id);

    Collection<Account> findByUser(UUID uuid);

    <T extends Account & Validatable> void save(Account account);
}
