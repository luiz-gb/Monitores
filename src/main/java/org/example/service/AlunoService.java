package org.example.service;

import org.example.exception.UsuarioJaExisteException;
import org.example.interfaces.IAlunoRepository;
import org.example.interfaces.ICoordenadorRepository;
import org.example.model.Aluno;
import org.example.model.Coordenador;

import java.util.List;

public class AlunoService {
    private final IAlunoRepository alunoRepository;
    private final ICoordenadorRepository coordenadorRepository;

    public AlunoService (IAlunoRepository alunoRepository, ICoordenadorRepository coordenadorRepository) {
        this.alunoRepository = alunoRepository;
        this.coordenadorRepository = coordenadorRepository;
    }

    public List<Aluno> retornarAlunos () {
        return alunoRepository.retornarTodosAlunos();
    }

    public void atualizarAluno(Aluno aluno) { alunoRepository.editar(aluno); }

    /**
     * Realiza o cadastro de um aluno no sistema.
     *
     * @param email     Email institucional do aluno.
     * @param senha     Senha do aluno.
     * @param matricula Matrícula do aluno.
     * @param nome      Nome completo do aluno.
     *
     * @throws UsuarioJaExisteException Caso email ou matrícula já estejam cadastrados.
     */
    public void cadastrarAluno(String email, String senha, String matricula, String nome) throws UsuarioJaExisteException {
        Aluno aluno = alunoRepository.buscarPorEmail(email);
        Coordenador coordenador = coordenadorRepository.buscarPorEmail(email);

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
