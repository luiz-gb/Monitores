package org.example.domain;

import org.example.enums.Sexo;

public class Aluno {
    private String nome;
    private String matricula;
    private String email;
    private Sexo sexo;

    public Aluno(String nome, String matricula, String email, Sexo sexo) {
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String toString() {
        return this.nome;
    }
}
