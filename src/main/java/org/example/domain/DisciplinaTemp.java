package org.example.domain;

public class DisciplinaTemp {
    private String nomeDisciplina;
    private int vagasRemuneradas;
    private int vagasVoluntarias;

    public DisciplinaTemp(String nomeDisciplina, int vagasRemuneradas, int vagasVoluntarias) {
        this.nomeDisciplina = nomeDisciplina;
        this.vagasRemuneradas = vagasRemuneradas;
        this.vagasVoluntarias = vagasVoluntarias;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public int getVagasRemuneradas() {
        return vagasRemuneradas;
    }

    public void setVagasRemuneradas(int vagasRemuneradas) {
        this.vagasRemuneradas = vagasRemuneradas;
    }

    public int getVagasVoluntarias() {
        return vagasVoluntarias;
    }

    public void setVagasVoluntarias(int vagasVoluntarias) {
        this.vagasVoluntarias = vagasVoluntarias;
    }
}
