package org.example.interfaces;

import org.example.model.Aluno;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;

import java.util.List;

public interface IInscricaoRepository {
    void salvar(Inscricao inscricao);
    void atualizar(Inscricao inscricao);
    List<Inscricao> retornarTodasInscricoes();
    int retornarQuantidadeInscricoesAluno(Aluno aluno, Edital edital);
    Inscricao retornarAlunoInscritoDisciplina(Aluno aluno, Disciplina disciplina);
    List<Inscricao> retornarInscricoesNaDisciplina(Disciplina disciplina);
    List<Inscricao> retornarInscricoesDoEdital(Edital edital);
    List<Inscricao> retornarInscricoesDoAluno(Aluno aluno);
}