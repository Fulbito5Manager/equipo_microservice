package com.api.equipo.exception;

public class ListaJugadoresIsFullException extends RuntimeException {
    public ListaJugadoresIsFullException(String message){
        super(message);
    }
}
