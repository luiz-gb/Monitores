package org.example.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.example.exception.CampoInvalidoException;
import org.example.exception.CampoTamanhoInvalidoException;
import org.example.exception.CampoVazioException;

public class ComponentValidator {
    private static final EmailValidator validator = EmailValidator.getInstance();

    public static void validarEmail (String email) throws CampoVazioException, CampoInvalidoException {

        if (email.isBlank()) {
            throw new CampoVazioException("O email não pode estar vazio, tente novamente!");
        }

        else if (!validator.isValid(email)){
            throw new CampoInvalidoException("Formato do email inválido, tente novamente!");
        }
    }

    public static void validarSenha (String senha) throws CampoVazioException, CampoTamanhoInvalidoException {
        if (senha.isBlank()) {
            throw new CampoVazioException("A senha não pode estar vazia, tente novamente");
        }

        else if (senha.length() < 6) {
            throw new CampoTamanhoInvalidoException("A senha deve ter pelo menos 6 dígitos, tente novamente!");
        }
    }
}
