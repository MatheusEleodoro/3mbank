package com.threembank.domain.exception;

import com.threembank.shared.exception.BasicValidationException;
import com.threembank.shared.message.ValidationMessage;

public class DomainValidationException extends BasicValidationException {

    public DomainValidationException(ValidationMessage... message) {
        super(message);
    }
}