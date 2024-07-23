package com.api.equipo.exception;

public class JugadorAlreadyExistException extends RuntimeException{
    public JugadorAlreadyExistException(String message){ super(message); }
}
