package org.example.service;

import org.example.enums.StatusEdital;
import org.example.exception.ListaVaziaException;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.repository.AlunoRepository;
import org.example.repository.CoordenaorRepository;
import org.example.repository.EditalRepository;

import java.time.LocalDate;
import java.util.List;

public class HomeService {
    public List<Edital> retornarEditais () {
        return editalRepository.retornarTodosEditais();
    }

    private EditalRepository editalRepository;

    public HomeService () {
        editalRepository = new EditalRepository();
    }

    /**
     * Construtor responsável por inicializar os repositórios
     * utilizados pelo serviço.
     */
    /**
     * Realiza o cadastro de um edital.
     *
     * @param dataInicio       Data de início do edital.
     * @param dataFinal        Data final do edital.
     * @param maximoInscricoes Máximo de inscrições permitidas por aluno.
     * @param pesoCre          Peso relativo do CRE.
     * @param pesoMedia        Peso relativo da média da disciplina.
     * @param listaDisciplinas Lista de disciplinas relacionadas ao edital.
     *
     * @throws ListaVaziaException Caso a lista de disciplinas esteja vazia.
     */
    public void cadastrarEdital(LocalDate dataInicio, LocalDate dataFinal, int maximoInscricoes,
                                double pesoCre, double pesoMedia, List<Disciplina> listaDisciplinas)
            throws ListaVaziaException {

        Edital edital = new Edital();

        edital.setDataInicio(dataInicio);
        edital.setDataFinal(dataFinal);
        edital.setListaDisciplinas(listaDisciplinas);
        edital.setPesoCre(pesoCre);
        edital.setPesoMedia(pesoMedia);
        edital.setMaximoInscricoesPorAluno(maximoInscricoes);
        edital.setStatus(StatusEdital.ABERTO);

        if (listaDisciplinas.isEmpty()) throw new ListaVaziaException("Você deve cadastrar ao menos uma disciplina no edital");

        for (Disciplina disciplina : listaDisciplinas) {
            disciplina.setEdital(edital);
        }

        editalRepository.salvar(edital);
    }

    /**
     * Atualiza os dados de um edital já salvo.
     *
     * @param edital Objeto edital atualizado.
     */
    public void salvarEdital(Edital edital) {
        editalRepository.editar(edital);
    }
}
