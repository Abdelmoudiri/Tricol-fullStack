package com.tricol.backend_java.exceptions;

public class NotValidRequestException extends RuntimeException {
    public NotValidRequestException(String message) {
        super(message);
    }
}
