package org.example.view.screens;

import org.example.exception.*;
import org.example.service.CadastroService;
import org.example.validator.ComponentValidator;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoPrimario;
import org.example.view.components.input.InputSenha;
import org.example.view.components.input.InputTexto;
import org.example.view.components.text.LabelTexto;
import org.example.view.components.text.LabelTitulo;

import javax.swing.*;

public class TelaCadastroCoordenador extends BaseTela {

    private LabelTitulo titulo; // Componente Novo
    private InputTexto campoEmail;
    private InputSenha campoSenha;
    private BotaoPrimario btnCadastrar;
    private CadastroService cadastroService;

    public TelaCadastroCoordenador() {
        super("Cadastro de Coordenador", 400, 500);
        cadastroService = new CadastroService();
    }

    @Override
    public void initComponents() {
        titulo = new LabelTitulo("Cadastro de Coordenador");

        campoEmail = new InputTexto();
        campoSenha = new InputSenha();

        btnCadastrar = new BotaoPrimario("Cadastrar");
    }

    @Override
    public void initListeners() {
        btnCadastrar.addActionListener(e -> {
            String email = campoEmail.getText().strip();
            String senha = new String(campoSenha.getPassword());

            try {
                ComponentValidator.validarEmail(email);
                ComponentValidator.validarSenha(senha);

                cadastroService.cadastrarCoordenador(email, senha);

                JOptionPane.showMessageDialog(this, "Cadastro de coordenador concluído!");
                dispose();
                new TelaLogin();
            } catch (CampoTamanhoInvalidoException | CampoVazioException |
                     CampoInvalidoException | UsuarioJaExisteException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void initLayout() {
        titulo.setBounds(0, 30, 400, 30);
        add(titulo);

        LabelTexto lblEmail = new LabelTexto("Email:");
        lblEmail.setBounds(50, 90, 300, 20);
        add(lblEmail);

        campoEmail.setBounds(50, 115, 300, 40);
        add(campoEmail);

        LabelTexto lblSenha = new LabelTexto("Senha:");
        lblSenha.setBounds(50, 170, 300, 20);
        add(lblSenha);

        campoSenha.setBounds(50, 195, 300, 40);
        add(campoSenha);

        btnCadastrar.setBounds(50, 270, 300, 45);
        add(btnCadastrar);
    }
}