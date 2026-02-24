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

}
