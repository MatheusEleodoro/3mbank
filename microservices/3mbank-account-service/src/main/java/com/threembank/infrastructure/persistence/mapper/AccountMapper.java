package com.threembank.infrastructure.persistence.mapper;

import com.threembank.domain.entity.Account;
import com.threembank.infrastructure.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        implementationName = "AccountMapperInterfaceImpl")
public interface AccountMapper {
    Account toDto(AccountEntity accountEntity);
    AccountEntity toEntity(Account account);

}