package org.example.exception;

public class UsuarioJaExisteException extends Exception{
    public UsuarioJaExisteException (String msg) {
        super(msg);
    }
}
