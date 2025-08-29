package com.threembank.domain.repository;

import com.threembank.domain.entity.Client;

import java.util.Optional;

/**
 * EN: Repository interface for managing {@link Client} entities.
 * Provides methods for common data access operations related to OAuth2 clients.
 * <br><br>
 * PT-BR: Interface de repositório para gerenciar entidades {@link Client}.
 * Fornece métodos para operações comuns de acesso a dados relacionadas a clientes OAuth2.
 */
public interface ServiceRepository {
    /**
     * EN: Finds a client by its unique identifier (clientId).
     * <br><br>
     * PT-BR: Encontra um cliente pelo seu identificador único (clientId).
     *
     * @param clientId The unique identifier of the client to find.
     * @return An {@link Optional} containing the {@link Client} if found, or an empty {@link Optional} if not.
     */
    Optional<Client> findClientById(String clientId);

    /**
     * EN: Checks if a client exists with the given client ID.
     * <br><br>
     * PT-BR: Verifica se existe um cliente com o ID de cliente fornecido.
     *
     * @param clientId The client ID to check.
     * @return {@code true} if a client with the given ID exists, {@code false} otherwise.
     */
    boolean exists(String clientId);

    /**
     * EN: Saves a client entity. This can be used for both creating a new client and updating an existing one.
     * <br><br>
     * PT-BR: Salva uma entidade de cliente. Isso pode ser usado tanto para criar um novo cliente quanto para atualizar um existente.
     *
     * @param client The {@link Client} entity to save.
     */
    void save(Client client);
}