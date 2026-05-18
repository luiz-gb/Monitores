package org.example.interfaces;

import org.example.model.Aluno;
import java.util.List;

public interface IAlunoRepository {
    void salvar(Aluno aluno);
    void editar(Aluno aluno);
    Aluno buscarPorEmail(String email);
    Aluno buscarPorMatricula(String matricula);
    List<Aluno> retornarTodosAlunos();
}