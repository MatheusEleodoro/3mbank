package com.threembank.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsIn...") String accessToken,
        @Schema(description = "JWT Refresh token", example = "eyJhbGciOiJIUzI1NiIsIn...") String refreshToken,
        @Schema(description = "JWT Token Type", example = "eyJhbGciOiJIUzI1NiIsIn...") String tokenType,
        @Schema(description = "Token expiration time in seconds", example = "3600000") long expiresIn) {}
