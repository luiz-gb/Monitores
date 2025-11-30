package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String nome;
    private int quantidadeVagas;
    private List<Aluno> listaAlunos = new ArrayList<>();

    public Disciplina (String nome, int quantidadeVagas) {
        this.nome = nome;
        this.quantidadeVagas = quantidadeVagas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeVagas() {
        return quantidadeVagas;
    }

    public void setQuantidadeVagas(int quantidadeVagas) {
        this.quantidadeVagas = quantidadeVagas;
    }

    public List<Aluno> getListaAlunos() {
        return listaAlunos;
    }

    public boolean verificarAlunoExiste (String matricula) {
        if (!listaAlunos.isEmpty()) {
            for (Aluno aluno : listaAlunos) {
                if (aluno.getMatricula().equals(matricula)) return true;
            }
        }

        return false;
    }
}
