package com.threembank.domain.valueobject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * EN: Represents the roles that can be assigned to a user, defining their level of access and permissions within the system.
 * <br><br>
 * PT-BR: Representa os papéis que podem ser atribuídos a um usuário, definindo seu nível de acesso e permissões dentro do sistema.
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    /**
     * EN: Administrator role, typically has full access to system functionalities.
     * <br><br>
     * PT-BR: Papel de Administrador, geralmente possui acesso total às funcionalidades do sistema.
     */
    ADMIN(1),
    /**
     * EN: Standard user role, with limited access to system functionalities.
     * <br><br>
     * PT-BR: Papel de Usuário padrão, com acesso limitado às funcionalidades do sistema.
     */
    USER(2),
    /**
     * EN: Service role, used for system-to-system interactions or automated processes.
     * <br><br>
     * PT-BR: Papel de Serviço, usado para interações sistema-a-sistema ou processos automatizados.
     */
    SERVICE(3);

    /**
     * EN: The numeric code associated with the role.
     * <br><br>
     * PT-BR: O código numérico associado ao papel.
     */
    private final int code;
}