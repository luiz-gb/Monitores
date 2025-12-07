package org.example.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "disciplina")
public class Disciplina {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_disciplina", nullable = false)
    private String nomeDisciplina;

    @Column(name = "vagas_remuneradas", nullable = false)
    private Integer vagasRemunerada;

    @Column(name = "vagas_voluntarias", nullable = false)
    private Integer vagasVoluntarias;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_edital")
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

    @Override
    public String toString() {
        return nomeDisciplina;
    }
}
