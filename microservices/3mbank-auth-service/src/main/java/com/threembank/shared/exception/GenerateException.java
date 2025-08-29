package com.threembank.shared.exception;

public class GenerateException extends RuntimeException {
    public GenerateException(String message,Throwable e) {
        super(message,e);
    }
}
