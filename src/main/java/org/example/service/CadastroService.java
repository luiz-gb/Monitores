package org.example.service;

import org.example.exception.ListaVaziaException;
import org.example.exception.UsuarioJaExisteException;
import org.example.model.Aluno;
import org.example.model.Coordenador;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.repository.AlunoRepository;
import org.example.repository.CoordenaorRepository;
import org.example.repository.EditalRepository;

import java.time.LocalDate;
import java.util.List;

public class CadastroService {
    private AlunoRepository alunoRepository;
    private CoordenaorRepository coordenaorRepository;
    private EditalRepository editalRepository;

    public CadastroService (){
        alunoRepository = new AlunoRepository();
        coordenaorRepository = new CoordenaorRepository();
        editalRepository = new EditalRepository();
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

    public void cadastrarEdital (LocalDate dataInicio, LocalDate dataFinal, int maximoInscricoes, double pesoCre, double pesoMedia, List<Disciplina> listaDisciplinas) throws ListaVaziaException {
        Edital edital = new Edital();

        edital.setDataInicio(dataInicio);
        edital.setDataFinal(dataFinal);
        edital.setListaDisciplinas(listaDisciplinas);
        edital.setPesoCre(pesoCre);
        edital.setPesoMedia(pesoMedia);
        edital.setMaximoInscricoesPorDisciplina(maximoInscricoes);

        if (listaDisciplinas.isEmpty()) throw new ListaVaziaException("Você deve cadastrar ao menos uma disciplina no edital");

        for (Disciplina disciplina : listaDisciplinas) {
            disciplina.setEdital(edital);
        }

        editalRepository.salvar(edital);
    }
}