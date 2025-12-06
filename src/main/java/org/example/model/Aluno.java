package org.example.model;

import jakarta.persistence.*;
import org.example.enums.TipoPerfil;

import java.util.UUID;

@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String matricula;

    @Column
    private String email;

    @Column
    private String nome;

    @Column
    private String senha;

    public Aluno() {

    }


    public Aluno(String matricula, String email, String nome, String senha) {
        this.matricula = matricula;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
