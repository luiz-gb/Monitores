package org.example.model;

import org.example.enums.ResultadoInscricao;

import java.util.Date;
import java.util.UUID;

public class Inscricao {

    private UUID id;
    private UUID alunoId;
    private UUID disciplinaId;
    private Date dataInscricao;
    private Double alunoCRE;
    private Double alunoMedia;
    private ResultadoInscricao resultadoInscricao;
    private Aluno aluno;
    private Disciplina disciplina;

    public Inscricao () {

    }

    public Inscricao(UUID id, UUID alunoId, UUID disciplinaId, Date dataInscricao, ResultadoInscricao resultadoInscricao, Double alunoCRE, Double alunoMedia) {
        this.id = id;
        this.alunoId = alunoId;
        this.disciplinaId = disciplinaId;
        this.dataInscricao = dataInscricao;
        this.resultadoInscricao = resultadoInscricao;
        this.alunoCRE = alunoCRE;
        this.alunoMedia = alunoMedia;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(UUID alunoId) {
        this.alunoId = alunoId;
    }

    public UUID getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(UUID id) {
        this.disciplinaId = id;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public ResultadoInscricao getResultadoInscricao() {
        return resultadoInscricao;
    }

    public void setResultadoInscricao(ResultadoInscricao resultadoInscricao) {
        this.resultadoInscricao = resultadoInscricao;
    }

    public Double getAlunoCRE() {
        return alunoCRE;
    }

    public void setAlunoCRE(Double alunoCRE) {
        this.alunoCRE = alunoCRE;
    }

    public Double getAlunoMedia() {
        return alunoMedia;
    }

    public void setAlunoMedia(Double alunoMedia) {
        this.alunoMedia = alunoMedia;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
