package com.threembank.domain.repository;

import com.threembank.domain.entity.Token;

import java.util.Optional;
import java.util.UUID;

/**
 * EN: Repository interface for managing {@link Token} entities.
 * Provides methods for common data access operations related to authentication tokens.
 * <br><br>
 * PT-BR: Interface de repositório para gerenciar entidades {@link Token}.
 * Fornece métodos para operações comuns de acesso a dados relacionadas a tokens de autenticação.
 */
public interface TokenRepository {
    /**
     * EN: Saves a token entity. This can be used for both creating a new token and updating an existing one.
     * <br><br>
     * PT-BR: Salva uma entidade de token. Isso pode ser usado tanto para criar um novo token quanto para atualizar um existente.
     *
     * @param token The {@link Token} entity to save.
     */
    void save(Token token);

    /**
     * EN: Finds a token by its string value.
     * <br><br>
     * PT-BR: Encontra um token pelo seu valor em string.
     *
     * @param token The string value of the token to find.
     * @return An {@link Optional} containing the {@link Token} if found, or an empty {@link Optional} if not.
     */
    Optional<Token> findByToken(String token);

    /**
     * EN: Revokes a specific token, marking it as invalid.
     * <br><br>
     * PT-BR: Revoga um token específico, marcando-o como inválido.
     *
     * @param token The {@link Token} to revoke.
     */
    void revoke(Token token);

    /**
     * EN: Revokes all tokens associated with a specific user.
     * <br><br>
     * PT-BR: Revoga todos os tokens associados a um usuário específico.
     *
     * @param userId The ID of the user whose tokens are to be revoked.
     */
    void revokeAll(UUID userId);
}