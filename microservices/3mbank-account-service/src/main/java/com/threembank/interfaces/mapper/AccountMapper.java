package com.threembank.interfaces.mapper;

import com.threembank.domain.entity.Account;
import com.threembank.interfaces.dto.AccountResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    Account toAccount(AccountResponse accountResponse);

    AccountResponse toResponse(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(AccountResponse accountResponse, @MappingTarget Account account);
}