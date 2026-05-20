package org.example.model;

import org.example.enums.StatusEdital;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Edital {

    private UUID id;
    private Date dataInicio;
    private Date dataFinal;
    private int maximoInscricoesPorAluno;
    private Double pesoCre;
    private Double pesoMedia;
    private StatusEdital status;
    private List<Disciplina> listaDisciplinas;

    public Edital () {}

    public Edital(UUID id, Date dataInicio, Date dataFinal, int maximoInscricoesPorAluno, double pesoCre, double pesoMedia) {
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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
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
