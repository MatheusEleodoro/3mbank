package com.threembank.domain.entity;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

/**
 * EN: Represents a refresh token in the system.
 * This entity stores information about a refresh token, including its value, associated user, expiration, and revocation status.
 * <br><br>
 * PT-BR: Representa um token de atualização no sistema.
 * Esta entidade armazena informações sobre um token de atualização, incluindo seu valor, usuário associado, expiração e status de revogação.
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(setterPrefix = "with")
public class Token {
    /**
     * EN: The refresh token string.
     * <br><br>
     * PT-BR: A string do token de atualização.
     */
    private String refreshToken;

    /**
     * EN: The unique identifier of the user to whom this token belongs.
     * <br><br>
     * PT-BR: O identificador único do usuário ao qual este token pertence.
     */
    private UUID userId;

    /**
     * EN: The exact moment when this token expires.
     * <br><br>
     * PT-BR: O momento exato em que este token expira.
     */
    private Instant expiresAt;

    /**
     * EN: A flag indicating whether this token has been revoked.
     * <br><br>
     * PT-BR: Um indicador que informa se este token foi revogado.
     */
    private boolean revoked;

    /**
     * EN: Checks if the token has expired.
     * A token is considered expired if its expiration time is before the current time.
     * <br><br>
     * PT-BR: Verifica se o token expirou.
     * Um token é considerado expirado se o seu tempo de expiração for anterior ao tempo atual.
     *
     * @return {@code true} if the token is expired, {@code false} otherwise.
     */
    public boolean isExpired() {
        return expiresAt.isBefore(Instant.now());
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}