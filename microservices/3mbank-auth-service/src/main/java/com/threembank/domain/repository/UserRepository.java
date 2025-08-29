package com.threembank.domain.repository;

import com.threembank.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

/**
 * EN: Repository interface for managing {@link User} entities.
 * Provides methods for common data access operations related to users.
 * <br><br>
 * PT-BR: Interface de repositório para gerenciar entidades {@link User}.
 * Fornece métodos para operações comuns de acesso a dados relacionadas a usuários.
 */
public interface UserRepository {
    /**
     * EN: Checks if a user exists with the given username.
     * <br><br>
     * PT-BR: Verifica se existe um usuário com o nome de usuário fornecido.
     *
     * @param username The username to check.
     * @return {@code true} if a user with the given username exists, {@code false} otherwise.
     */
    boolean exists(String username);

    /**
     * EN: Finds a user by their username.
     * <br><br>
     * PT-BR: Encontra um usuário pelo seu nome de usuário.
     *
     * @param username The username of the user to find.
     * @return An {@link Optional} containing the user if found, or an empty {@link Optional} if not.
     */
    Optional<User> findByUsername(String username);

    /**
     * EN: Finds a user by their unique identifier (ID).
     * <br><br>
     * PT-BR: Encontra um usuário pelo seu identificador único (ID).
     *
     * @param id The ID of the user to find.
     * @return An {@link Optional} containing the user if found, or an empty {@link Optional} if not.
     */
    Optional<User> findById(UUID id);

    /**
     * EN: Saves a user entity. This can be used for both creating a new user and updating an existing one.
     * <br><br>
     * PT-BR: Salva uma entidade de usuário. Isso pode ser usado tanto para criar um novo usuário quanto para atualizar um existente.
     *
     * @param user The user entity to save.
     */
    void save(User user);
}