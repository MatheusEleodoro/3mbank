package com.threembank.infrastructure.persistence.repository.jpa;

import com.eleodorodev.specification.DynamicRepository;
import com.threembank.infrastructure.persistence.entity.ServiceEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceJpaRepository extends DynamicRepository<ServiceEntity, String> {
}
