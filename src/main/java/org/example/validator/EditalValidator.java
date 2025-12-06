package org.example.validator;

import org.example.exception.*;

import java.time.LocalDate;

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

    public static void validarDataInicio (LocalDate dataInicio) throws DataInvalidaException {
        LocalDate dataAtual = LocalDate.now();
        if (dataAtual.isAfter(dataInicio)) throw new DataInvalidaException("A data de início só pode ser alterada antes dela começar.");
    }

    public static void validarDataFinal (LocalDate dataFinal) throws DataInvalidaException {
        if (dataFinal.isBefore(LocalDate.now())) throw new DataInvalidaException("A nova data final não pode ser no passado.");
    }

    public static void validarDatas (LocalDate dataInicio, LocalDate dataFinal) throws DataInvalidaException {
        if (!dataFinal.isAfter(dataInicio)) throw new DataInvalidaException("A data final deve ser maior que a data de início!");
    }

    public static void validarMaxInscricoesEditar (int numeroAntigo, int numeroNovo) throws CampoInvalidoException {
        if (numeroNovo < numeroAntigo) throw new CampoInvalidoException("O máximo de inscrições deve ser maior ou igual ao número antigo!");
    }

    public static Boolean validarDentroPeriodoInscricoes (LocalDate dataInicio, LocalDate dataFinal) throws DataInvalidaException{
        LocalDate dataAtual = LocalDate.now();

        if (dataAtual.isBefore(dataInicio) || dataAtual.isAfter(dataFinal)) {
            return false;
        }

        return true;
    }

    public static void validarCancelarEncerramentoEdital (LocalDate dataFinal) {
        LocalDate dataAtual = LocalDate.now();

        if (dataFinal.isBefore(dataAtual)) throw new DataInvalidaException("O encerramento do edital não pode ser cancelado, a data final já passou!");
    }
}
