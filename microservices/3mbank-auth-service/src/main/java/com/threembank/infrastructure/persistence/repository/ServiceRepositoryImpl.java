package com.threembank.infrastructure.persistence.repository;

import com.threembank.domain.entity.Client;
import com.threembank.domain.repository.ServiceRepository;
import com.threembank.infrastructure.persistence.mapper.ServiceMapper;
import com.threembank.infrastructure.persistence.repository.jpa.ServiceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepository {

    private final ServiceJpaRepository repository;
    private final ServiceMapper mapper;


    @Override
    public Optional<Client> findClientById(String clientId) {
        return repository.findById(clientId).map(mapper::toClient);
    }

    @Override
    public boolean exists(String clientId) {
        return repository.existsById(clientId);
    }

    @Override
    public void save(Client client) {
        repository.save(mapper.toEntity(client));
    }
}
