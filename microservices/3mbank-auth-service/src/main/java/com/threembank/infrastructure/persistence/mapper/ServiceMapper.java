package com.threembank.infrastructure.persistence.mapper;

import com.threembank.domain.entity.Client;
import com.threembank.infrastructure.persistence.entity.ServiceEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceMapper {
    ServiceEntity toEntity(Client client);

    Client toClient(ServiceEntity serviceEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ServiceEntity partialUpdate(Client client, @MappingTarget ServiceEntity serviceEntity);
}