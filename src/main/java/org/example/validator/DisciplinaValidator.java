package org.example.validator;

import org.example.exception.CampoVazioException;
import org.example.exception.NumeroInvalidoException;

public class DisciplinaValidator {
    public static void validarNome(String nome) throws CampoVazioException {
        if (nome.isBlank()) throw new CampoVazioException("O nome da disciplina não pode estar vazio!");
    }

    public static void validarQuantidadeVagas (String quantidadeVagas) throws CampoVazioException, NumeroInvalidoException{

        if (quantidadeVagas.isBlank()) throw new CampoVazioException("A quantidade de vagas não pode estar vazia!");

        Integer quantidade = null;

        try {
            quantidade = Integer.parseInt(quantidadeVagas);
        }
        catch (NumberFormatException e) {
            throw new NumeroInvalidoException("A quantidade deve ser um número!");
        }

        if (quantidade < 0) throw new NumeroInvalidoException("A quantidade de vagas não pode ser menor que zero!");
    }
}
