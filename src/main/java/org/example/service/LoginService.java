package org.example.service;

import org.example.model.Aluno;
import org.example.model.Coordenador;
import org.example.repository.AlunoRepository;
import org.example.repository.CoordenaorRepository;

public class LoginService {
    AlunoRepository alunoRepository;
    CoordenaorRepository coordenaorRepository;

    public LoginService () {
        alunoRepository = new AlunoRepository();
        coordenaorRepository = new CoordenaorRepository();
    }

    public Object fazerLogin (String email, String senha) {
        Aluno aluno = alunoRepository.buscarPorEmail(email);

        if (aluno != null && aluno.getSenha().equals(senha)) {
            return aluno;
        }

        Coordenador coordenador = coordenaorRepository.buscarPorEmail(email);

        if (coordenador != null && coordenador.getSenha().equals(senha)) {
            return coordenador;
        }

        return null;
    }
}
