package org.example.model;

import jakarta.persistence.*;

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

    @Column(name = "maximo_inscricoes_disciplina", nullable = false)
    private int maximoInscricoesPorDisciplina;

    @Column(name = "peso_cre", nullable = false)
    private Double pesoCre;

    @Column(name = "peso_media",nullable = false)
    private Double pesoMedia;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL)
    private List<Disciplina> listaDisciplinas;

    public Edital () {}

    public Edital(UUID id, LocalDate dataInicio, LocalDate dataFinal, int maximoInscricoesPorDisciplina, double pesoCre, double pesoMedia) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.maximoInscricoesPorDisciplina = maximoInscricoesPorDisciplina;
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

    public int getMaximoInscricoesPorDisciplina() {
        return maximoInscricoesPorDisciplina;
    }

    public void setMaximoInscricoesPorDisciplina(int maximoInscricoesPorDisciplina) {
        this.maximoInscricoesPorDisciplina = maximoInscricoesPorDisciplina;
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
}
