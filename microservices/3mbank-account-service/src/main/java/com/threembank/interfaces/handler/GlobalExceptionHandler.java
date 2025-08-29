package com.threembank.interfaces.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.threembank.interfaces.valueobject.Error;
import com.threembank.shared.exception.BasicValidationException;
import com.threembank.shared.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BasicValidationException.class)
    public ResponseEntity<Error> handler(BasicValidationException ex) {
        var messages = !ex.erros().isEmpty() ? ex.erros() : ex.getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Error("1.0.0", getRoot(ex), getOrigin(ex), messages));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> handler(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new Error("1.0.0", getRoot(ex), getOrigin(ex), ex.getMessage()));
    }



    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Error> handler(InvalidFormatException ex) {
        var value = ex.getValue();
        var property = ex.getPath().getFirst().getFieldName();
        var message = "Value %s is not permitted for %s input".formatted(value, property);
        if (ex.getTargetType().isEnum()) {
            message += " please choose between [%s]".formatted(Arrays.stream(ex.getTargetType().getEnumConstants())
                    .map(Object::toString).collect(Collectors.joining(",")));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Error("1.0.0",getRoot(ex), ex.getClass().getSimpleName(), message));
    }

    private static String getRoot(Exception ex) {
        if(ex instanceof BasicValidationException bve)
            return log.isDebugEnabled() ? bve.getRoot().getCanonicalName() :null;
        return log.isDebugEnabled() ? ex.initCause(ex.getCause()).getClass().getCanonicalName() :null;
    }

    private static String  getOrigin(Exception ex){
        return log.isDebugEnabled() ? ex.getClass().getSimpleName() :null;

    }


}
