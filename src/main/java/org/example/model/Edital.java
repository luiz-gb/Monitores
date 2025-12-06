package org.example.model;

import jakarta.persistence.*;
import org.example.enums.StatusEdital;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "edital")
public class Edital {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_final", nullable = false)
    private LocalDate dataFinal;

    @Column(name = "maximo_inscricoes_aluno", nullable = false)
    private int maximoInscricoesPorAluno;

    @Column(name = "peso_cre", nullable = false)
    private Double pesoCre;

    @Column(name = "peso_media",nullable = false)
    private Double pesoMedia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEdital status;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Disciplina> listaDisciplinas;

    public Edital () {}

    public Edital(UUID id, LocalDate dataInicio, LocalDate dataFinal, int maximoInscricoesPorAluno, double pesoCre, double pesoMedia) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.maximoInscricoesPorAluno = maximoInscricoesPorAluno;
        this.pesoCre = pesoCre;
        this.pesoMedia = pesoMedia;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public int getMaximoInscricoesPorAluno() {
        return maximoInscricoesPorAluno;
    }

    public void setMaximoInscricoesPorAluno(int maximoInscricoesPorAluno) {
        this.maximoInscricoesPorAluno = maximoInscricoesPorAluno;
    }

    public double getPesoCre() {
        return pesoCre;
    }

    public void setPesoCre(double pesoCre) {
        this.pesoCre = pesoCre;
    }

    public double getPesoMedia() {
        return pesoMedia;
    }

    public void setPesoMedia(double pesoMedia) {
        this.pesoMedia = pesoMedia;
    }

    public List<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }

    public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
        this.listaDisciplinas = listaDisciplinas;
    }

    public StatusEdital getStatus() {
        return status;
    }

    public void setStatus(StatusEdital status) {
        this.status = status;
    }
}
