package org.example.service;

import org.example.enums.ResultadoInscricao;
import org.example.exception.AlunoJaInscritoException;
import org.example.exception.InscricaoInvalida;
import org.example.model.Aluno;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;
import org.example.repository.InscricaoRepository;

import java.time.LocalDateTime;
import java.util.List;

public class InscricaoService {
    private InscricaoRepository inscricaoRepository;

    public InscricaoService () {
        inscricaoRepository = new InscricaoRepository();
    }

    public void criarInscricao (Aluno aluno, Disciplina disciplina, Double cre, Double media) throws InscricaoInvalida, AlunoJaInscritoException {
        Inscricao inscricao = new Inscricao();
        inscricao.setAluno(aluno);
        inscricao.setDisciplina(disciplina);
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setAlunoCRE(cre);
        inscricao.setAlunoMedia(media);
        inscricao.setResultadoInscricao(ResultadoInscricao.PENDENTE);

        verificarAlunoJaPossuiInscricao(inscricao);
        verificarAlunoPassouLimiteInscricoes(inscricao);
        inscricaoRepository.salvar(inscricao);
    }

    public void verificarAlunoPassouLimiteInscricoes (Inscricao inscricao) throws InscricaoInvalida {
        Aluno aluno = inscricao.getAluno();
        Edital edital = inscricao.getDisciplina().getEdital();

        int quantidadeInscricoes = inscricaoRepository.retornarQuantidadeInscricoesAluno(aluno, edital);

        if (quantidadeInscricoes >= edital.getMaximoInscricoesPorAluno()) throw new InscricaoInvalida("O aluno atingiu o limite máximo de inscrições nesta disciplina!");
    }

    public void verificarAlunoJaPossuiInscricao (Inscricao inscricao) throws AlunoJaInscritoException {
        Aluno aluno = inscricao.getAluno();
        Disciplina disciplina = inscricao.getDisciplina();

        if (inscricaoRepository.retornarAlunoInscritoDisciplina(aluno, disciplina) != null) throw new AlunoJaInscritoException("Aluno já inscrito nessa disciplina!");
    }

    public List<Inscricao> retornarTodasInscricoes () {
        return inscricaoRepository.retornarTodasInscricoes();
    }
}
