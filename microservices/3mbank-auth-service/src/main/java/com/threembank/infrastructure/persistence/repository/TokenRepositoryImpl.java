package com.threembank.infrastructure.persistence.repository;

import com.threembank.domain.entity.Token;
import com.threembank.domain.repository.TokenRepository;
import com.threembank.infrastructure.persistence.mapper.TokenMapper;
import com.threembank.infrastructure.persistence.repository.jpa.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {
    private final TokenJpaRepository repository;
    private final TokenMapper mapper;

    @Override
    public void save(Token token) {
           repository.save(mapper.toEntity(token));
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return repository.findByToken(token).map(mapper::toDomain);
    }

    @Override
    public void revoke(Token token) {
        repository.findByToken(token.getRefreshToken())
                .ifPresent(entity -> {
                    entity.setRevoked(true);
                    repository.save(entity);
                });
    }

    @Transactional
    @Override
    public void revokeAll(UUID userId) {
        repository.revokeAllById(userId);
    }
}
