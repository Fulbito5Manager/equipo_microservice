package com.api.equipo.exception;

public class EquipoNotFoundException extends RuntimeException {
    public EquipoNotFoundException(String message) {
        super(message);
    }
}
