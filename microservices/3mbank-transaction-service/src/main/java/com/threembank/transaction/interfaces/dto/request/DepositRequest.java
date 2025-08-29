package com.threembank.transaction.interfaces.dto.request;

import java.math.BigDecimal;

/**
 * DTO para requisições de depósito.
 * <p>
 * Representa os dados necessários para realizar uma operação de depósito em uma conta.
 * </p>
 *
 * @param accountId   Identificador da conta que receberá o depósito.
 * @param amount      Valor a ser depositado.
 * @param currency    Moeda do depósito (ex: "BRL", "USD").
 * @param description Descrição ou observação sobre o depósito.
 */
public record DepositRequest(String accountId, BigDecimal amount, String currency, String description) {}