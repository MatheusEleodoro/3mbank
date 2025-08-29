package com.threembank.infrastructure.persistence.repository;

import com.threembank.domain.entity.User;
import com.threembank.domain.repository.UserRepository;
import com.threembank.infrastructure.persistence.mapper.UserMapper;
import com.threembank.infrastructure.persistence.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;
    private final UserMapper userMapper;

    @Override
    public boolean exists(String username) {
        return repository.existsByEmail(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByEmail(username).map(userMapper::toDto);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id).map(userMapper::toDto);
    }

    @Override
    public void save(User user) {
        user.isValid();
        userMapper.toDto(repository.save(userMapper.toEntity(user)));
    }
}
