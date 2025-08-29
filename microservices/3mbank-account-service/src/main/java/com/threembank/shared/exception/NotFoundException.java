package com.threembank.shared.exception;

import com.threembank.shared.message.ValidationMessage;


public class NotFoundException extends BasicValidationException {

    public NotFoundException(Class<?> root, ValidationMessage... message) {
        super(root, message);
    }

    public NotFoundException(Class<?> root, String message) {
        super(root, message);
    }
}
