package org.example.view.screens;

import org.example.exception.*;
import org.example.service.CadastroService;
import org.example.validator.ComponentValidator;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoPrimario;
import org.example.view.components.input.InputTexto;
import org.example.view.components.text.LabelTexto;
import org.example.view.components.text.LabelTitulo;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroAluno extends BaseTela {

    private LabelTitulo labelTitulo;
    private LabelTexto labelNome;
    private LabelTexto labelEmail;
    private LabelTexto labelMatricula;
    private LabelTexto labelSenha;
    private InputTexto campoNome;
    private InputTexto campoEmail;
    private InputTexto campoMatricula;
    private JPasswordField campoSenha;
    private BotaoPrimario btnCadastrar;
    private BotaoPrimario btnVoltar;
    private CadastroService cadastroService;

    public TelaCadastroAluno() {
        super("Cadastro de Aluno", 400, 500);
        cadastroService = new CadastroService();
    }

    @Override
    public void initComponents() {
        labelTitulo = new LabelTitulo("Cadastro de Aluno");

        labelNome = new LabelTexto("Nome: ");
        labelEmail = new LabelTexto("Email: ");
        labelMatricula = new LabelTexto("Matricula: ");
        labelSenha = new LabelTexto("Senha");

        campoNome = new InputTexto();
        campoEmail = new InputTexto();
        campoMatricula = new InputTexto();

        campoSenha = new JPasswordField();
        campoSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        campoSenha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        btnCadastrar = new BotaoPrimario("Cadastrar");
        btnVoltar = new BotaoPrimario("Voltar");
    }

    @Override
    public void initListeners() {
        btnCadastrar.addActionListener(e -> {
            String nome = campoNome.getText();
            String email = campoEmail.getText();
            String matricula = campoMatricula.getText();
            String senha = new String(campoSenha.getPassword());

            try {
                ComponentValidator.validarNome(nome);
                ComponentValidator.validarEmail(email);
                ComponentValidator.validarMatricula(matricula);
                ComponentValidator.validarSenha(senha);

                cadastroService.cadastrarAluno(email, senha, matricula, nome);

                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                dispose();
                new TelaLogin();

            } catch (CampoInvalidoException | CampoVazioException | CampoTamanhoInvalidoException |
                     UsuarioJaExisteException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaLogin();
        });
    }

    @Override
    public void initLayout() {
        labelTitulo.setBounds(0, 30, 400, 30);
        add(labelTitulo);

        labelNome.setBounds(50, 70, 300, 20);
        add(labelNome);

        campoNome.setBounds(50, 95, 300, 40);
        add(campoNome);

        labelEmail.setBounds(50, 145, 300, 20);
        add(labelEmail);

        campoEmail.setBounds(50, 170, 300, 40);
        add(campoEmail);

        labelMatricula.setBounds(50, 220, 300, 20);
        add(labelMatricula);

        campoMatricula.setBounds(50, 245, 300, 40);
        add(campoMatricula);

        labelSenha.setBounds(50, 295, 300, 20);
        add(labelSenha);

        campoSenha.setBounds(50, 320, 300, 40);
        add(campoSenha);

        btnVoltar.setBounds(50, 390, 145, 45);
        add(btnVoltar);

        btnCadastrar.setBounds(205, 390, 145, 45);
        add(btnCadastrar);
    }
}