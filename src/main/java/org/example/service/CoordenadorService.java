package org.example.service;

import org.example.exception.UsuarioJaExisteException;
import org.example.model.Aluno;
import org.example.model.Coordenador;
import org.example.repository.AlunoRepository;
import org.example.repository.CoordenaorRepository;
import org.example.repository.EditalRepository;

public class CoordenadorService {
    private AlunoRepository alunoRepository;
    private CoordenaorRepository coordenadorRepository;

    public CoordenadorService(){
        alunoRepository = new AlunoRepository();
        coordenadorRepository = new CoordenaorRepository();
    }

    /**
     * Realiza o cadastro de um coordenador.
     *
     * @param email Email do coordenador.
     * @param senha Senha de acesso.
     *
     * @throws UsuarioJaExisteException Caso o email já esteja registrado
     * como aluno ou coordenador no sistema.
     */

    public void cadastrarCoordenador(String email, String senha) throws UsuarioJaExisteException {
        Aluno aluno = alunoRepository.buscarPorEmail(email);
        Coordenador coordenador = coordenadorRepository.buscarPorEmail(email);

        if (aluno != null || coordenador != null) {
            throw new UsuarioJaExisteException("O email já existe no sistema, tente novamente!");
        }

        Coordenador coordenadorNovo = new Coordenador();

        coordenadorNovo.setEmail(email);
        coordenadorNovo.setSenha(senha);

        coordenadorRepository.salvar(coordenadorNovo);
    }
}
