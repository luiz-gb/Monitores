package org.example.exception;

public class ListaVaziaException extends RuntimeException {
    public ListaVaziaException(String message) {
        super(message);
    }
}
