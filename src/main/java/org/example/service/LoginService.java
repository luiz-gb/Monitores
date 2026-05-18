package org.example.service;

import org.example.interfaces.IAlunoRepository;
import org.example.interfaces.ICoordenadorRepository;
import org.example.interfaces.UsuarioAutenticavel;
import org.example.model.Aluno;
import org.example.model.Coordenador;
import org.example.repository.AlunoRepository;
import org.example.repository.CoordenadorRepository;

public class LoginService {
    private final IAlunoRepository alunoRepository;
    private final ICoordenadorRepository coordenadorRepository;

    public LoginService (IAlunoRepository alunoRepository, ICoordenadorRepository coordenadorRepository) {
        this.alunoRepository = alunoRepository;
        this.coordenadorRepository = coordenadorRepository;
    }

    public UsuarioAutenticavel fazerLogin (String email, String senha) {
        Aluno aluno = alunoRepository.buscarPorEmail(email);

        if (aluno != null && aluno.getSenha().equals(senha)) {
            return aluno;
        }

        Coordenador coordenador = coordenadorRepository.buscarPorEmail(email);

        if (coordenador != null && coordenador.getSenha().equals(senha)) {
            return coordenador;
        }

        return null;
    }
}
