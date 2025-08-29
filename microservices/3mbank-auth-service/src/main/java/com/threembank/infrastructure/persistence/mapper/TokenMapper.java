package com.threembank.infrastructure.persistence.mapper;

import com.threembank.domain.entity.Token;
import com.threembank.infrastructure.persistence.entity.TokenEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface TokenMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "token", source = "refreshToken")
    TokenEntity toEntity(Token token);

    @InheritInverseConfiguration(name = "toEntity")
    Token toDomain(TokenEntity tokenEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TokenEntity partialUpdate(Token token, @MappingTarget TokenEntity tokenEntity);
}