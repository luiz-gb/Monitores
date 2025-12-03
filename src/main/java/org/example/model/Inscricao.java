package org.example.model;

import jakarta.persistence.*;
import org.example.enums.ResultadoInscricao;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inscricao")
public class Inscricao {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina disciplina;

    @Column(name = "data_inscricao")
    private LocalDateTime dataInscricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "resultado_inscricao", nullable = false)
    private ResultadoInscricao resultadoInscricao;

    public Inscricao () {

    }

    public Inscricao(UUID id, Aluno aluno, Disciplina disciplina, LocalDateTime dataInscricao, ResultadoInscricao resultadoInscricao) {
        this.id = id;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.dataInscricao = dataInscricao;
        this.resultadoInscricao = resultadoInscricao;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public ResultadoInscricao getResultadoInscricao() {
        return resultadoInscricao;
    }

    public void setResultadoInscricao(ResultadoInscricao resultadoInscricao) {
        this.resultadoInscricao = resultadoInscricao;
    }
}
