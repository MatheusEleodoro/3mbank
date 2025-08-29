package com.threembank.infrastructure.persistence.repository.jpa;

import com.eleodorodev.specification.DynamicRepository;
import com.threembank.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends DynamicRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}