package org.example.service;

import org.example.enums.ResultadoInscricao;
import org.example.enums.StatusEdital;
import org.example.exception.AlunoJaInscritoException;
import org.example.exception.InscricaoInvalida;
import org.example.model.Aluno;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;
import org.example.repository.InscricaoRepository;
import org.example.util.CalcularPontuacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        verificarEditalEncerrado(inscricao);
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

    public void verificarEditalEncerrado (Inscricao inscricao) throws InscricaoInvalida {
        Edital edital = inscricao.getDisciplina().getEdital();

        if (edital.getStatus() == StatusEdital.ENCERRADO) throw new InscricaoInvalida("O edital está encerrado, não aceita mais incrições!");
    }

    public List<Inscricao> retornarInscricoesDaDisciplina (Disciplina disciplina) {
        return inscricaoRepository.retornarInscricoesNaDisciplina(disciplina);
    }

    public List<Inscricao> processarResultadoDaDisciplina (Disciplina disciplina) {
        List<Inscricao> listaInscricoes = retornarInscricoesDaDisciplina(disciplina);

        listaInscricoes.sort((a, b) -> {

            if (a.getResultadoInscricao().equals(ResultadoInscricao.DESISTENTE) &&
                    !b.getResultadoInscricao().equals(ResultadoInscricao.DESISTENTE)) {
                return 1;
            }

            if (b.getResultadoInscricao().equals(ResultadoInscricao.DESISTENTE) &&
                    !a.getResultadoInscricao().equals(ResultadoInscricao.DESISTENTE)) {
                return -1;
            }

            double pontuacao1 = CalcularPontuacao.calcularPontuacaoAluno(
                    a.getDisciplina().getEdital().getPesoCre(),
                    a.getDisciplina().getEdital().getPesoMedia(),
                    a.getAlunoCRE(),
                    a.getAlunoMedia()
            );

            double pontuacao2 = CalcularPontuacao.calcularPontuacaoAluno(
                    b.getDisciplina().getEdital().getPesoCre(),
                    b.getDisciplina().getEdital().getPesoMedia(),
                    b.getAlunoCRE(),
                    b.getAlunoMedia()
            );

            return Double.compare(pontuacao2, pontuacao1);
        });

        int vagasRemuneradas = disciplina.getVagasRemunerada();
        int vagasVoluntarias = disciplina.getVagasVoluntarias();

        listaInscricoes.forEach(e -> {
            int posicao = listaInscricoes.indexOf(e) + 1;

            if (e.getResultadoInscricao() != ResultadoInscricao.DESISTENTE) {
                if (posicao <= vagasRemuneradas) {
                    e.setResultadoInscricao(ResultadoInscricao.APROVADO_BOLSA);
                }

                else if (posicao <= vagasRemuneradas + vagasVoluntarias)  {
                    e.setResultadoInscricao(ResultadoInscricao.APROVADO_VOLUNTARIO);
                }
            }

            inscricaoRepository.atualizar(e);
        });

        return listaInscricoes;
    }

    public List<Inscricao> retornarTodasInscricoes () {
        return inscricaoRepository.retornarTodasInscricoes();
    }

    public void desistirInscricao (Aluno aluno, Disciplina disciplina) {
        Inscricao inscricao = inscricaoRepository.retornarAlunoInscritoDisciplina(aluno, disciplina);

        inscricao.setResultadoInscricao(ResultadoInscricao.DESISTENTE);
        inscricaoRepository.atualizar(inscricao);
    }
}
