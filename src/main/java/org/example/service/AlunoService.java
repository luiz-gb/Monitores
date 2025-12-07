package org.example.service;

import org.example.model.Aluno;
import org.example.repository.AlunoRepository;

import java.util.List;

public class AlunoService {
    private AlunoRepository alunoRepository;

    public AlunoService () {
        alunoRepository = new AlunoRepository();
    }

    public List<Aluno> retornarAlunos () {
        return alunoRepository.retornarTodosAlunos();
    }
    public void atualizarAluno(Aluno aluno) { alunoRepository.editar(aluno); }
}
