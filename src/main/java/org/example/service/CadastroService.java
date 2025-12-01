package org.example.service;

import org.example.exception.UsuarioJaExisteException;
import org.example.model.Aluno;
import org.example.model.Coordenador;
import org.example.repository.AlunoRepository;
import org.example.repository.CoordenaorRepository;

public class CadastroService {
    private AlunoRepository alunoRepository;
    private CoordenaorRepository coordenaorRepository;

    public CadastroService (){
        alunoRepository = new AlunoRepository();
        coordenaorRepository = new CoordenaorRepository();
    }

    public void cadastrarCoordenador (String email, String senha) throws UsuarioJaExisteException{
        Aluno aluno = alunoRepository.buscarPorEmail(email);
        Coordenador coordenador = coordenaorRepository.buscarPorEmail(email);

        if (aluno != null || coordenador != null) {
            throw new UsuarioJaExisteException("O email já existe no sistema, tente novamente!");
        }

        Coordenador coordenadorNovo = new Coordenador();

        coordenadorNovo.setEmail(email);
        coordenadorNovo.setSenha(senha);

        coordenaorRepository.salvar(coordenadorNovo);
    }

    public void cadastrarAluno (String email, String senha, String matricula, String nome) throws UsuarioJaExisteException{
        Aluno aluno = alunoRepository.buscarPorEmail(email);
        Coordenador coordenador = coordenaorRepository.buscarPorEmail(email);

        if (aluno != null || coordenador != null) {
            throw new UsuarioJaExisteException("O email já existe no sistema, tente novamente!");
        }

        aluno = alunoRepository.buscarPorMatricula(matricula);

        if (aluno != null) {
            throw new UsuarioJaExisteException("A matrícula já existe no sistema, tente novamente!");
        }

        Aluno alunoNovo = new Aluno();

        alunoNovo.setEmail(email);
        alunoNovo.setSenha(senha);
        alunoNovo.setMatricula(matricula);
        alunoNovo.setNome(nome);

        alunoRepository.salvar(alunoNovo);
    }
}
