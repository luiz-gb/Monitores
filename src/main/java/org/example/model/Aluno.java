package org.example.model;

import jakarta.persistence.*;
import org.example.enums.TipoPerfil;
import org.example.interfaces.UsuarioAutenticavel;

import java.util.UUID;

@Entity
@Table(name = "aluno")
public class Aluno implements UsuarioAutenticavel {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String matricula;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
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

    public TipoPerfil getTipoPerfil() {
        return TipoPerfil.ALUNO;
    }
}
