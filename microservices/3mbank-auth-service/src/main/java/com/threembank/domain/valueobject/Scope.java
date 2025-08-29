package com.threembank.domain.valueobject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * EN: Represents the possible scopes of access that can be granted to a client application or user.
 * Scopes define the extent of permissions for performing actions or accessing resources.
 * <br><br>
 * PT-BR: Representa os possíveis escopos de acesso que podem ser concedidos a uma aplicação cliente ou usuário.
 * Escopos definem a extensão das permissões para realizar ações ou acessar recursos.
 */
@Getter
@RequiredArgsConstructor
public enum Scope {
    /**
     * EN: Grants permission to read resources.
     * <br><br>
     * PT-BR: Concede permissão para ler recursos.
     */
    READ("read"),
    /**
     * EN: Grants permission to write or modify resources.
     * <br><br>
     * PT-BR: Concede permissão para escrever ou modificar recursos.
     */
    WRITE("write"),
    /**
     * EN: Grants permission to perform transfer operations.
     * <br><br>
     * PT-BR: Concede permissão para realizar operações de transferência.
     */
    TRANSFER("transfer");

    /**
     * EN: The string representation of the scope.
     * <br><br>
     * PT-BR: A representação em string do escopo.
     */
    private final String code;
}