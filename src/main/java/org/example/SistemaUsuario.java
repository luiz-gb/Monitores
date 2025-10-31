package org.example;

import jakarta.mail.MessagingException;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaUsuario {
    public static void main (String[] args) throws IOException, FileNotFoundException, MessagingException {
        Scanner input = new Scanner(System.in);

        EmailValidator validator = EmailValidator.getInstance();
        Persistencia persistencia = new Persistencia();

        CentralDeInformacoes central;

        try {
            central = persistencia.recuperarCentral();
        } catch (FileNotFoundException e) {
            central = new CentralDeInformacoes();
        }

        System.out.println("=====SISTEMA DE ALUNOS=====");
        while (true) {

            System.out.println("Verifique as opções a seguir:");
            System.out.println("1. Novo Aluno \n2. Listar Alunos\n3. Exibir Informações do Aluno\n4. Novo Edital\n5. Informar Quantidade de Editais Cadastrados\n" +
                    "6. Exibir Informações do Edital\n7. Inscrever Aluno\n8. Gerar Comprovante de Todas as Inscrições de Um Aluno\n9. Sair");
            System.out.print("Digite a opção desejada: ");

            Integer opcao = null;

            try {
                opcao = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Apenas números são permitidos!");
            }

            if (opcao == 1) {
                System.out.println("=====CADASTRO DE ALUNO=====");
                System.out.print("Digite o nome: ");
                String nome = input.nextLine();

                if (nome.strip().isEmpty()) {
                    System.out.println("O nome não pode estar vazio, tente novamente!");
                    continue;
                }

                System.out.print("Digite a matrícula: ");
                String matricula = input.nextLine();

                if (matricula.strip().isEmpty()) {
                    System.out.println("A matrícula não pode estar vazia, tente novamente!");
                    continue;
                } else if (!matricula.matches("\\d+") || matricula.length() != 5) {
                    System.out.println("Matrícula inválida, digite um número de 5 caracteres!");
                    continue;
                }

                System.out.print("Digite o email do aluno: ");
                String email = input.nextLine();

                if (email.strip().isEmpty()) {
                    System.out.println("O email não pode estar vazio, tente novamente!");
                    continue;
                }

                else if (!validator.isValid(email)){
                    System.out.println("Formato do email inválido, tente novamente!");
                    continue;
                }

                System.out.print("Digite o sexo do aluno (MASCULINO/FEMININO): ");
                String sexoString = input.nextLine();

                Sexo sexo;

                try {
                    sexo = Sexo.valueOf(sexoString.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("O campo digitado não condiz com as opções válidas, tente novamente!");
                    continue;
                }

                Aluno aluno = new Aluno(nome, matricula, email, sexo);

                if (!central.adicionarAluno(aluno)) {
                    System.out.println("Erro ao cadastrar aluno, tente novamente!");
                    continue;
                }

                persistencia.salvarCentral(central);
                System.out.println("Aluno cadastrado!");
            }

            else if (opcao == 2) {
                try {
                    persistencia.listarAlunos();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }

            else if (opcao == 3) {
                try {
                    CentralDeInformacoes centralRecuperada = persistencia.recuperarCentral();

                    System.out.print("Digite a matrícula que deseja buscar: ");
                    String matricula = input.nextLine();

                    if (centralRecuperada.recuperarAlunoPorMatricula(matricula) != null) {
                        Aluno alunoEncontrado = centralRecuperada.recuperarAlunoPorMatricula(matricula);

                        System.out.println("Segue o aluno encontrado abaixo: ");
                        System.out.println("Informações: Nome - " + alunoEncontrado.getNome() + " | Matrícula - " + alunoEncontrado.getMatricula() + " | Email - " + alunoEncontrado.getEmail()
                        + " | Sexo - " + alunoEncontrado.getSexo().name().toLowerCase());
                    }

                    else {
                        System.out.println("Matrícula não encontrada, tente novamente!");
                    }
                }
                catch (Exception e) {
                    System.out.print("Erro: ");
                    e.printStackTrace();
                }
            }

            else if (opcao == 4) {
                System.out.println("=====CADASTRO DE EDITAL=====");

                System.out.print("Digite a matrícula: ");
                String matricula = input.nextLine();

                if (matricula.strip().isEmpty()) {
                    System.out.println("A matrícula não pode estar vazia, tente novamente!");
                    continue;
                } else if (!matricula.matches("\\d+") || matricula.length() != 5) {
                    System.out.println("Matrícula inválida, digite um número de 5 caracteres!");
                    continue;
                }

                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                System.out.print("Digite a data de início (dd/mm/yyyyy): ");
                String dataInicioStr = input.nextLine();

                LocalDate dataInicio = null;

                try {
                    dataInicio = LocalDate.parse(dataInicioStr, formatador);
                }
                catch (DateTimeParseException e) {
                    System.out.println("Formato passado inválido, tente novamente!");
                    continue;
                }

                System.out.print("Digite a data final (dd/mm/yyyyy): ");
                String dataFinalStr = input.nextLine();

                LocalDate dataFinal = null;

                try {
                    dataFinal = LocalDate.parse(dataFinalStr, formatador);
                }
                catch (DateTimeParseException e) {
                    System.out.println("Formato passado inválido, tente novamente!");
                    continue;
                }

                System.out.print("Quantas disciplinas vão fazer parte do seu edital? ");

                int quantidadeDisciplinas;

                try {
                    quantidadeDisciplinas = Integer.parseInt(input.nextLine());
                }
                catch (NumberFormatException e) {
                    System.out.println("A quantidade deve ser um número, tente novamente!");
                    continue;
                }

                if (quantidadeDisciplinas <= 0) {
                    System.out.println("Você digitou um número menor ou igual a zero, tente novamente!");
                    continue;
                }

                EditalMonitoria edital = new EditalMonitoria(matricula, dataInicio, dataFinal);

                System.out.println("Inicializando cadastro de disciplinas...");
                for (int i = 0; i < quantidadeDisciplinas; i++) {
                    System.out.println("Cadastro " + (i + 1) + ":");

                    System.out.print("Digite o nome da disciplina: ");
                    String nomeDisciplina = input.nextLine();

                    if (nomeDisciplina.strip().isEmpty()) {
                        System.out.println("O nome não pode estar vazio, tente novamente!");
                        i--;
                        continue;
                    }

                    System.out.print("Digite a quantidade de vagas da disciplina: ");
                    Integer quantidadeVagas = null;

                    try {
                        quantidadeVagas = Integer.parseInt(input.nextLine());
                    }
                    catch (NumberFormatException e) {
                        System.out.println("A quantidade deve ser um número, tente novamente!");
                        i--;
                        continue;
                    }

                    if (quantidadeVagas <= 0) {
                        System.out.println("Você digitou um número menor ou igual a zero, tente novamente!");
                        i--;
                        continue;
                    }

                    Disciplina disciplina = new Disciplina(nomeDisciplina, quantidadeVagas);
                    edital.getListaDisciplinas().add(disciplina);
                }

                try {
                    central.adicionarEdital(edital);
                    persistencia.salvarCentral(central);
                }
                catch (Exception e) {
                    System.out.println("Erro ao cadastrar edital, tente novamente!");
                    continue;
                }

                System.out.println("Edital cadastrado com sucesso!");
            }

            else if (opcao == 5) {
                System.out.println("Quantidade de editais cadastrados até o momento: " + central.quantidadeEditaisCadastrados());
            }

            else if (opcao == 6) {
                System.out.print("Digite o id do edital que você deseja: ");
                Long id = null;

                try {
                    id = Long.parseLong(input.nextLine());
                }
                catch (NumberFormatException e) {
                    System.out.println("Valor inválido, tente novamente!");
                    continue;
                }

                EditalMonitoria edital = central.recuperarEditalPorId(id);

                if (edital != null) {
                    System.out.println(edital);
                }

                else {
                    System.out.println("Edital não encontrado, tente novamente!");
                }
            }

            else if (opcao == 7) {
                System.out.println("=====INSCRIÇÃO EM EDITAL=====");
                System.out.print("Digite a matrícula do aluno que você deseja inscrever: ");
                String matricula = null;

                matricula = input.nextLine();

                if (matricula.strip().isEmpty()) {
                    System.out.println("A matrícula não pode estar vazia, tente novamente!");
                    continue;
                } else if (!matricula.matches("\\d+") || matricula.length() != 5) {
                    System.out.println("Matrícula inválida, digite um número de 5 caracteres!");
                    continue;
                }

                Aluno aluno = central.recuperarAlunoPorMatricula(matricula);

                if (aluno == null) {
                    System.out.println("Aluno não encontrado, tente novamente!");
                    continue;
                }

                System.out.print("Digite o id do edital que você deseja: ");
                Long id = null;

                try {
                    id = Long.parseLong(input.nextLine());
                }
                catch (NumberFormatException e) {
                    System.out.println("Valor inválido, tente novamente!");
                    continue;
                }

                EditalMonitoria edital = central.recuperarEditalPorId(id);

                if (edital == null) {
                    System.out.println("Edital não encontrado, tente novamente!");
                    continue;
                }

                System.out.println(edital);

                System.out.print("Digite a disciplina desejada: ");
                String disciplina = input.nextLine();

                if (disciplina.strip().isEmpty()) {
                    System.out.println("O nome da disciplina não pode estar vazio, tente novamente!");
                    continue;
                }

                if (edital.inscrever(aluno, disciplina) && persistencia.salvarCentral(central)) {
                    System.out.println("Inscrição concluída com sucesso!");

                    try {
                        Mensageiro.enviarEmail(aluno.getEmail(), "Olá " + aluno.getNome() + ", você foi inscrito no edital #" + edital.getNumeroEdital());
                    }
                    catch (MessagingException e) {
                        System.out.println("Erro ao enviar email!");
                    }
                }

                else {
                    System.out.println("Houve um erro com a inscrição, tente novamente! (Recomendamos verificar o nome da disciplina e o período de inscrição)");
                }
            }

            else if (opcao == 8) {
                System.out.print("Digite a matrícula do aluno: ");
                String matricula = null;

                matricula = input.nextLine();

                if (matricula.strip().isEmpty()) {
                    System.out.println("A matrícula não pode estar vazia, tente novamente!");
                    continue;
                } else if (!matricula.matches("\\d+") || matricula.length() != 5) {
                    System.out.println("Matrícula inválida, digite um número de 5 caracteres!");
                    continue;
                }

                Aluno aluno = central.recuperarAlunoPorMatricula(matricula);

                if (aluno == null) {
                    System.out.println("Aluno não encontrado, tente novamente!");
                    continue;
                }

                System.out.print("Digite o id do edital que o(a) aluno(a) se inscreveu: ");
                Long id = null;

                try {
                    id = Long.parseLong(input.nextLine());
                }
                catch (NumberFormatException e) {
                    System.out.println("Valor inválido, tente novamente!");
                    continue;
                }

                EditalMonitoria edital = central.recuperarEditalPorId(id);

                if (edital == null) {
                    System.out.println("Edital não encontrado, tente novamente!");
                    continue;
                }

                GeradorDeRelatorios.obterComprovanteDeInscricoesAluno(aluno.getMatricula(), edital.getId(), central);
                System.out.println("Relatório gerado com sucesso!");
            }

            else if (opcao == 9) {
                System.out.println("Programa encerrado, agradecemos a preferência!");
                break;
            }

            else {
                System.out.println("Opção inválida, tente novamente!");
            }

            continue;
        }
    }
}
