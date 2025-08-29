package com.threembank.domain.validation;

import com.threembank.domain.exception.DomainValidationException;
import com.threembank.shared.message.ValidationMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

/**
 * EN: A utility class for performing bean validation using Jakarta Bean Validation.
 * This class provides a static method to validate objects and throws a {@link DomainValidationException}
 * if any constraint violations are found.
 * <br><br>
 * PT-BR: Uma classe utilitária para realizar validação de beans usando Jakarta Bean Validation.
 * Esta classe fornece um método estático para validar objetos e lança uma {@link DomainValidationException}
 * se quaisquer violações de restrição forem encontradas.
 */
public class Validate {
    private final Validator validator;

    /**
     * EN: Private constructor to initialize the validator.
     * It builds a default validator factory and gets a validator instance.
     * The factory is closed automatically using a try-with-resources statement.
     * <br><br>
     * PT-BR: Construtor privado para inicializar o validador.
     * Ele constrói uma fábrica de validadores padrão e obtém uma instância do validador.
     * A fábrica é fechada automaticamente usando uma instrução try-with-resources.
     */
    private Validate() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }

    /**
     * EN: Validates the given object using the initialized validator.
     * If validation violations are found, it collects them into {@link ValidationMessage}
     * objects and throws a {@link DomainValidationException}.
     * <br><br>
     * PT-BR: Valida o objeto fornecido usando o validador inicializado.
     * Se violações de validação forem encontradas, ele as coleta em objetos {@link ValidationMessage}
     * e lança uma {@link DomainValidationException}.
     *
     * @param obj The object to be validated.
     * @param <T> The type of the object to be validated.
     * @throws DomainValidationException if validation violations are found.
     *                                   The exception contains an array of {@link ValidationMessage}
     *                                   detailing each violation.
     */
    public static <T> void of(T obj) {
        var instance = new Validate();
        Set<ConstraintViolation<T>> violations = instance.validator.validate(obj);
        if (!violations.isEmpty()) {
            throw new DomainValidationException(
                    violations.stream()
                            .map(v -> new ValidationMessage(
                                    v.getPropertyPath().toString(), v.getMessage()))
                            .toArray(ValidationMessage[]::new)
            );
        }
    }
}