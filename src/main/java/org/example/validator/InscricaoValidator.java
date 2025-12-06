package org.example.validator;

import org.example.exception.NumeroInvalidoException;

public class InscricaoValidator {
    public static void validarNota (Double nota) throws NumeroInvalidoException{
        if (nota < 0 || nota > 100) {
            throw new NumeroInvalidoException("A média ou cre deve estar entre 0 e 100!");
        }
    }
}
