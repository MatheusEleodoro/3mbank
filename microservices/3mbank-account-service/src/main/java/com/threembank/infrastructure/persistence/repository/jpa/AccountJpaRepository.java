package com.threembank.infrastructure.persistence.repository.jpa;

import com.eleodorodev.specification.DynamicRepository;
import com.threembank.infrastructure.persistence.entity.AccountEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaRepository extends DynamicRepository<AccountEntity, Long> {
}