package org.example.interfaces;

import org.example.model.Coordenador;

public interface ICoordenadorRepository {
    void salvar(Coordenador coordenador);
    Coordenador buscarPorEmail(String email);
    boolean verificarSeTemRegistro();
}