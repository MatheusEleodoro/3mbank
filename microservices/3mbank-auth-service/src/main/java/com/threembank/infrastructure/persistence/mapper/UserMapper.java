package com.threembank.infrastructure.persistence.mapper;

import com.threembank.domain.entity.User;
import com.threembank.infrastructure.persistence.entity.CredentialEntity;
import com.threembank.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface UserMapper {
    @Mapping(source = "password", target = "userCredential.password")
    UserEntity toEntity(User user);

    @Mapping(source = "userCredential.password", target = "password")
    User toDto(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(User user, @MappingTarget UserEntity userEntity);


    @AfterMapping
    default void linkUserAndCredential(@MappingTarget UserEntity userEntity, User user) {
        if (user.getPassword() != null) {
            CredentialEntity credential = new CredentialEntity();
            credential.setPassword(user.getPassword());

            credential.setUser(userEntity); // ESSENCIAL para @MapsId funcionar

            userEntity.setUserCredential(credential);
        }
    }
}