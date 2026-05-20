package org.example.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

public class Disciplina {

    private UUID id;
    private String nomeDisciplina;
    private Integer vagasRemunerada;
    private Integer vagasVoluntarias;
    private List<Inscricao> inscricoes;
    private Edital edital;

    public Disciplina () {}

    public Disciplina(UUID id, String nomeDisciplina, Integer vagasRemunerada, Integer vagasVoluntarias, Edital idEdital) {
        this.id = id;
        this.nomeDisciplina = nomeDisciplina;
        this.vagasRemunerada = vagasRemunerada;
        this.vagasVoluntarias = vagasVoluntarias;
        this.edital = idEdital;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getVagasRemunerada() {
        return vagasRemunerada;
    }

    public void setVagasRemunerada(Integer vagasRemunerada) {
        this.vagasRemunerada = vagasRemunerada;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public Integer getVagasVoluntarias() {
        return vagasVoluntarias;
    }

    public void setVagasVoluntarias(Integer vagasVoluntarias) {
        this.vagasVoluntarias = vagasVoluntarias;
    }

    public Edital getEdital() {
        return edital;
    }

    public void setEdital(Edital idEdital) {
        this.edital = idEdital;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(List<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }

    @Override
    public String toString() {
        return nomeDisciplina;
    }
}
