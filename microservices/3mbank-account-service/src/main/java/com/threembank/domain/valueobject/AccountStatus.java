package com.threembank.domain.valueobject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <b>English</b><br>
 * Represents the status of a bank account.
 * <br><br>
 * <b>Portuguese</b><br>
 * Representa o status de uma conta bancária.
 */
@Getter
@RequiredArgsConstructor
public enum AccountStatus {
    /**
     * <b>English</b><br>
     * The account is active and can be used for transactions.
     * <br><br>
     * <b>Portuguese</b><br>
     * A conta está ativa e pode ser usada para transações.
     */
    ACTIVE(1),

    /**
     * <b>English</b><br>
     * The account is temporarily blocked, usually for security reasons.
     * <br><br>
     * <b>Portuguese</b><br>
     * A conta está temporariamente bloqueada, geralmente por motivos de segurança.
     */
    BLOCKED(2),

    /**
     * <b>English</b><br>
     * The account has been inactive for a long period and may require action to be reactivated.
     * <br><br>
     * <b>Portuguese</b><br>
     * A conta está inativa por um longo período e pode exigir uma ação para ser reativada.
     */
    INACTIVE(3),

    /**
     * <b>English</b><br>
     * The account is pending activation or verification.
     * <br><br>
     * <b>Portuguese</b><br>
     * A conta está pendente de ativação ou verificação.
     */
    PENDING(4),

    /**
     * <b>English</b><br>
     * The account has been suspended due to policy violations or other serious issues.
     * <br><br>
     * <b>Portuguese</b><br>
     * A conta foi suspensa devido a violações de políticas ou outros problemas graves.
     */
    SUSPENDED(5);

    private final int code;
}