package org.example.service;

import org.example.enums.StatusEdital;
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

/**
 * Serviço responsável pelo cadastro de usuários (aluno e coordenador)
 * e gerenciamento básico de editais.
 *
 * <p>Esta classe se comunica com os repositórios e aplica regras
 * fundamentais de validação como verificação de existência de email,
 * matrícula duplicada e lista de disciplinas obrigatória no edital.</p>
 *
 * @author Davi Melo
 */
public class CadastroService {
    private AlunoRepository alunoRepository;
    private CoordenaorRepository coordenaorRepository;
    private EditalRepository editalRepository;

    /**
     * Construtor responsável por inicializar os repositórios
     * utilizados pelo serviço.
     */
    public CadastroService (){
        alunoRepository = new AlunoRepository();
        coordenaorRepository = new CoordenaorRepository();
        editalRepository = new EditalRepository();
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
        Coordenador coordenador = coordenaorRepository.buscarPorEmail(email);

        if (aluno != null || coordenador != null) {
            throw new UsuarioJaExisteException("O email já existe no sistema, tente novamente!");
        }

        Coordenador coordenadorNovo = new Coordenador();

        coordenadorNovo.setEmail(email);
        coordenadorNovo.setSenha(senha);

        coordenaorRepository.salvar(coordenadorNovo);
    }

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

    /**
     * Realiza o cadastro de um edital.
     *
     * @param dataInicio       Data de início do edital.
     * @param dataFinal        Data final do edital.
     * @param maximoInscricoes Máximo de inscrições permitidas por aluno.
     * @param pesoCre          Peso relativo do CRE.
     * @param pesoMedia        Peso relativo da média da disciplina.
     * @param listaDisciplinas Lista de disciplinas relacionadas ao edital.
     *
     * @throws ListaVaziaException Caso a lista de disciplinas esteja vazia.
     */
    public void cadastrarEdital(LocalDate dataInicio, LocalDate dataFinal, int maximoInscricoes,
                                double pesoCre, double pesoMedia, List<Disciplina> listaDisciplinas)
            throws ListaVaziaException {

        Edital edital = new Edital();

        edital.setDataInicio(dataInicio);
        edital.setDataFinal(dataFinal);
        edital.setListaDisciplinas(listaDisciplinas);
        edital.setPesoCre(pesoCre);
        edital.setPesoMedia(pesoMedia);
        edital.setMaximoInscricoesPorAluno(maximoInscricoes);
        edital.setStatus(StatusEdital.ABERTO);

        if (listaDisciplinas.isEmpty()) throw new ListaVaziaException("Você deve cadastrar ao menos uma disciplina no edital");

        for (Disciplina disciplina : listaDisciplinas) {
            disciplina.setEdital(edital);
        }

        editalRepository.salvar(edital);
    }

    /**
     * Atualiza os dados de um edital já salvo.
     *
     * @param edital Objeto edital atualizado.
     */
    public void salvarEdital(Edital edital) {
        editalRepository.editar(edital);
    }
}
