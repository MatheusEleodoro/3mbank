package com.threembank.application.exception;

import com.threembank.shared.exception.BasicValidationException;
import com.threembank.shared.message.ValidationMessage;

public class UseCaseValidationException extends BasicValidationException {
    public UseCaseValidationException(ValidationMessage... message) {
        super(message);
    }
}
