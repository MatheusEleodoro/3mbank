package com.threembank.domain.validation.impl;

import com.threembank.domain.validation.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * EN: Implements the validation logic for the {@link StrongPassword} annotation.
 * This validator checks if a given string meets the criteria for a strong password,
 * which includes a minimum length, and the presence of lowercase letters, uppercase
 * letters, digits, and special characters.
 * <br><br>
 * PT-BR: Implementa a lógica de validação para a anotação {@link StrongPassword}.
 * Este validador verifica se uma determinada string atende aos critérios para uma senha forte,
 * que inclui um comprimento mínimo e a presença de letras minúsculas, letras maiúsculas,
 * dígitos e caracteres especiais.
 */
public class DefaultPswImpl implements ConstraintValidator<StrongPassword, String> {

    /**
     * EN: Regular expression for a strong password.
     * A strong password must:
     * - Be at least 12 characters long
     * - Contain at least one lowercase letter
     * - Contain at least one uppercase letter
     * - Contain at least one digit
     * - Contain at least one special character from the set @#$%^&+=!
     * <br><br>
     * PT-BR: Expressão regular para uma senha forte.
     * Uma senha forte deve:
     * - Ter pelo menos 12 caracteres
     * - Conter pelo menos uma letra minúscula
     * - Conter pelo menos uma letra maiúscula
     * - Conter pelo menos um dígito
     * - Conter pelo menos um caractere especial do conjunto @#$%^&+=!
     */
    private static final String STRONG_PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{12,}$";

    /**
     * EN: Validates whether the given string is a strong password.
     * It checks if the value is not null and matches the {@code STRONG_PASSWORD_REGEX}.
     * <br><br>
     * PT-BR: Valida se a string fornecida é uma senha forte.
     * Verifica se o valor não é nulo e corresponde à {@code STRONG_PASSWORD_REGEX}.
     *
     * @param value   EN: The password string to validate.
     *                PT-BR: A string da senha a ser validada.
     * @param context EN: Context in which the constraint is evaluated.
     *                PT-BR: Contexto no qual a restrição é avaliada.
     * @return EN: {@code true} if the password is valid (matches the regex), {@code false} otherwise (including if the value is null).
     *         PT-BR: {@code true} se a senha for válida (corresponde à regex), {@code false} caso contrário (incluindo se o valor for nulo).
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.matches(STRONG_PASSWORD_REGEX);
    }
}