package com.threembank.shared.exception;

import com.threembank.shared.message.ValidationMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicValidationException extends RuntimeException  {
    private final Set<ValidationMessage> messages;

    public BasicValidationException(ValidationMessage... message) {
        this.messages = new HashSet<>(List.of(message));
    }
    public Map<String, Object> erros() {
        return messages.stream()
                .collect(Collectors.toMap(
                        ValidationMessage::property,
                        ValidationMessage::message,
                        (existing, _) -> existing
                ));
    }
}
