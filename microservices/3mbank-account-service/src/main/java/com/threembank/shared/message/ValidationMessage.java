package com.threembank.shared.message;

import java.io.Serializable;

public record ValidationMessage(String property, String message) implements Serializable {
    public static ValidationMessage of(String property, String message) {
        return new ValidationMessage(property, message);
    }
}
