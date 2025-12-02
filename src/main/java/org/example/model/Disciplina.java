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

    @Column(name = "nome_disciplina")
    private String nomeDisciplina;

    @Column(name = "vagas_remuneradas")
    private Integer vagasRemunerada;

    @Column(name = "vagas_voluntarias")
    private Integer vagasVoluntarias;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_edital")
    private UUID idEdital;

    public Disciplina () {}

    public Disciplina(UUID id, String nomeDisciplina, Integer vagasRemunerada, Integer vagasVoluntarias, UUID idEdital) {
        this.id = id;
        this.nomeDisciplina = nomeDisciplina;
        this.vagasRemunerada = vagasRemunerada;
        this.vagasVoluntarias = vagasVoluntarias;
        this.idEdital = idEdital;
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

    public UUID getIdEdital() {
        return idEdital;
    }

    public void setIdEdital(UUID idEdital) {
        this.idEdital = idEdital;
    }
}
