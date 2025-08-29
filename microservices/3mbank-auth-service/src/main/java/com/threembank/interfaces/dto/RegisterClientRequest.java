package com.threembank.interfaces.dto;

import com.threembank.domain.valueobject.Scope;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RegisterClientRequest {
    @NotNull
    private String clientId;
    @NotNull
    private List<Scope> scopes;
}
