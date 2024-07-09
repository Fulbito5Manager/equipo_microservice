package com.api.equipo.exception;

public class JugadorNotFoundException extends RuntimeException {
    public JugadorNotFoundException(String message) {
        super(message);
    }
}
