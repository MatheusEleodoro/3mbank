package com.threembank.interfaces.dto;

import com.threembank.domain.valueobject.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRequest {
    @NotNull
    private AccountType type;
}
