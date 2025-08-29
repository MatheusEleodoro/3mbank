package com.threembank.domain.valueobject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <b>English</b><br>
 * Represents the type of a bank account.
 * <br><br>
 * <b>Portuguese</b><br>
 * Representa o tipo de uma conta bancária.
 */
@Getter
@RequiredArgsConstructor
public enum AccountType {
    /**
     * <b>English</b><br>
     * Checking account, for daily transactions.
     * <br><br>
     * <b>Portuguese</b><br>
     * Conta corrente, para transações diárias.
     */
    CHECKING(1),

    /**
     * <b>English</b><br>
     * Savings account, for accumulating funds.
     * <br><br>
     * <b>Portuguese</b><br>
     * Conta poupança, para acumular fundos.
     */
    SAVING(2),

    /**
     * <b>English</b><br>
     * Investment account, for investment purposes.
     * <br><br>
     * <b>Portuguese</b><br>
     * Conta de investimento, para fins de investimento.
     */
    INVESTMENT(3);

    private final int code;
}