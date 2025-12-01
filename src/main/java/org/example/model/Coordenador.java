package org.example.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "coordenador")
public class Coordenador {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String email;

    @Column
    private String senha;

    public Coordenador () {}

    public Coordenador(UUID id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
