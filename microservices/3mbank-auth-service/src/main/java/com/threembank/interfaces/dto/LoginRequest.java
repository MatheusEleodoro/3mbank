package com.threembank.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Tag(name = "LoginRequest", description = "Login object containing username and strong password")
public class LoginRequest implements Serializable {
    @Schema(
            description = "User email address",
            example = "user@3mbank.com.br",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @Schema(
            description = "Strong password with at least 12 characters, including at least one uppercase letter, one lowercase letter, one digit, and one special character (@#$%^&+=!)",
            example = "Example&123Pwd",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;
}
