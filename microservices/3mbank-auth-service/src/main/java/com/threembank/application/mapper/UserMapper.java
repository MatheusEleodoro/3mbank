package com.threembank.application.mapper;

import com.threembank.domain.entity.User;
import com.threembank.interfaces.dto.RegisterUserRequest;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        implementationName = "UserMapperApplicationImpl" )
public interface UserMapper {

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    User toUser(RegisterUserRequest registerUserRequest);

    RegisterUserRequest toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(RegisterUserRequest registerUserRequest, @MappingTarget User user);
}