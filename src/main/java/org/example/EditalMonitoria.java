package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EditalMonitoria {
    private long id;
    private String numeroEdital;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<Disciplina> listaDisciplinas = new ArrayList<>();

    public EditalMonitoria(String numeroEdital, LocalDate dataInicio, LocalDate dataFim) {
        this.id = System.currentTimeMillis();
        this.numeroEdital = numeroEdital;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public List<Disciplina> getListaDisciplinas () {
        return listaDisciplinas;
    }

    public EditalMonitoria() {
        this.id = System.currentTimeMillis();
    }

    public String getNumeroEdital() {
        return numeroEdital;
    }

    public void setNumeroEdital(String numeroEdital) {
        this.numeroEdital = numeroEdital;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public boolean inscrever(Aluno aluno, String disciplinaDesejada) {
        boolean disciplinaExiste = false;
        Integer idDisciplina = null;
        LocalDate dataAtual = LocalDate.now();

        for (int i = 0; i < this.listaDisciplinas.size(); i++) {
            if (listaDisciplinas.get(i).getNome().equalsIgnoreCase(disciplinaDesejada)) {
                disciplinaExiste = true;
                idDisciplina = i;
            }
        }

        if (disciplinaExiste && !this.dataInicio.isAfter(dataAtual) && !this.dataFim.isBefore(dataAtual) && this.listaDisciplinas.get(idDisciplina).getQuantidadeVagas() > 0) {
            this.listaDisciplinas.get(idDisciplina).getListaAlunos().add(aluno);
            this.listaDisciplinas.get(idDisciplina).setQuantidadeVagas(this.listaDisciplinas.get(idDisciplina).getQuantidadeVagas() - 1);
            return true;
        }

        return false;
    }

    public boolean jaAcabou() {
        LocalDate dataAtual = LocalDate.now();

        if (!this.dataFim.isAfter(dataAtual)) {
            return true;
        }

        return false;
    }


    @Override
    public String toString () {
        String textoEdital = "Edital de Monitoria " + this.numeroEdital + " \n#Disciplinas\n";
        String status = null;

        if (!this.listaDisciplinas.isEmpty()) {
            for (Disciplina disciplina : listaDisciplinas) {
                textoEdital += disciplina.getNome() + " - " + disciplina.getQuantidadeVagas() + " vagas\n";
            }
        } else textoEdital += "Sem disciplinas...\n";

        if (this.jaAcabou()) {
            status = "encerradas";
        }

        else {
            status = "abertas";
        }

        textoEdital += "Inscrições " + status + ".";

        return textoEdital;
    }
}
