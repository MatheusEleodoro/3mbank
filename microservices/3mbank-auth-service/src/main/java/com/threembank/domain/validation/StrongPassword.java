package com.threembank.domain.validation;

import com.threembank.domain.validation.impl.DefaultPswImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * EN: Custom validation annotation to ensure that a password meets strong password criteria.
 * It is validated by {@link com.threembank.domain.validation.impl.DefaultPswImpl}.
 * This annotation can be applied to fields or parameters.
 * <br><br>
 * PT-BR: Anotação de validação personalizada para garantir que uma senha atenda aos critérios de senha forte.
 * É validada por {@link com.threembank.domain.validation.impl.DefaultPswImpl}.
 * Esta anotação pode ser aplicada a campos ou parâmetros.
 */
@Documented
@Constraint(validatedBy = DefaultPswImpl.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    /**
     * EN: The error message to be displayed if the password validation fails.
     * Defaults to "invalid password".
     * <br><br>
     * PT-BR: A mensagem de erro a ser exibida se a validação da senha falhar.
     * O padrão é "invalid password".
     *
     * @return The error message.
     */
    String message() default "invalid password";

    /**
     * EN: Allows the specification of validation groups, to control when this constraint is applied.
     * <br><br>
     * PT-BR: Permite a especificação de grupos de validação, para controlar quando esta restrição é aplicada.
     *
     * @return An array of classes representing the validation groups.
     */
    Class<?>[] groups() default {};

    /**
     * EN: Can be used by clients of the Bean Validation API to assign custom payload objects to a constraint.
     * <br><br>
     * PT-BR: Pode ser usado por clientes da API de Bean Validation para atribuir objetos de payload personalizados a uma restrição.
     *
     * @return An array of classes extending {@link Payload}.
     */
    Class<? extends Payload>[] payload() default {};
}