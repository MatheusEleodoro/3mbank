package com.threembank.infrastructure.persistence.repository.jpa;

import com.eleodorodev.specification.DynamicRepository;
import com.threembank.infrastructure.persistence.entity.TokenEntity;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenJpaRepository extends DynamicRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByToken(String token);

    @Modifying
    @Query("update TokenEntity u set u.revoked = true where u.user.id = :id")
    void revokeAllById(@Parameter(name = "id") UUID id);
}