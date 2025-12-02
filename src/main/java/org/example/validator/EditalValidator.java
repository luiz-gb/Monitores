package org.example.validator;

import org.example.exception.CampoVazioException;
import org.example.exception.NumeroInvalidoException;
import org.example.exception.SomaPesosException;

public class EditalValidator {
    public static void validarMaxInscricoes (String maxInscricoes) throws  CampoVazioException, NumeroInvalidoException {
        if (maxInscricoes.isBlank()) throw new CampoVazioException("A quantidade de inscrições não pode estar vazia!");

        Integer quantidade = null;

        try {
            quantidade = Integer.parseInt(maxInscricoes);
        }
        catch (NumberFormatException e) {
            throw new NumeroInvalidoException("O máximo de inscrições deve ser um número!");
        }

        if (quantidade < 0) throw new NumeroInvalidoException("O máximo de inscrições não pode ser menor que zero!");
    }

    public static void validarPeso (String peso) throws CampoVazioException, NumeroInvalidoException {
        if (peso.isBlank()) throw new CampoVazioException("O peso não pode estar vazio!");

        Double numero = null;

        try {
            numero = Double.parseDouble(peso);
        }

        catch (NumberFormatException e) {
            throw new NumeroInvalidoException("O peso deve ser um número!");
        }

        if (numero < 0) throw new NumeroInvalidoException("O peso não pode ser menor que zero!");
    }

    public static void validarPesos(float peso1, float peso2) throws SomaPesosException {
        if (peso1 + peso2 != 1) throw new SomaPesosException("A soma dos pesos deve dar 1!");
    }
}
