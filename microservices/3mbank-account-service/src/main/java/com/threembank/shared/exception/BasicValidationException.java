package com.threembank.shared.exception;

import com.threembank.shared.message.ValidationMessage;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;


public class BasicValidationException extends RuntimeException  {
    @Getter
    private final Class<?> root;
    private final Set<ValidationMessage> messages;

    public BasicValidationException(Class<?> root,ValidationMessage... message) {
        this.messages = new HashSet<>(List.of(message));
        this.root = root;
    }

    public BasicValidationException(Class<?> root, String message) {
        super(message);
        this.root = root;
        this.messages = Collections.emptySet();
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
