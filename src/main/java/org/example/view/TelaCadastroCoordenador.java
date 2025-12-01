package org.example.view;

import org.example.exception.CampoInvalidoException;
import org.example.exception.CampoTamanhoInvalidoException;
import org.example.exception.CampoVazioException;
import org.example.exception.UsuarioJaExisteException;
import org.example.service.CadastroService;
import org.example.validator.ComponentValidator;

import javax.swing.*;

public class TelaCadastroCoordenador  extends BaseTela{
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton btnLogin;
    private CadastroService cadastroService;

    public TelaCadastroCoordenador () {
        super("Cadastro coordenador", 400, 500);
        cadastroService = new CadastroService();
    }

    @Override
    public void initComponents () {
        campoEmail = new JTextField();
        campoSenha = new JPasswordField();
        btnLogin = new JButton("Login");
    }

    @Override
    public void initListeners() {
        btnLogin.addActionListener(e -> {
            String email = campoEmail.getText().strip();
            String senha = new String(campoSenha.getPassword());

            try {
                ComponentValidator.validarEmail(email);
                ComponentValidator.validarSenha(senha);

                cadastroService.cadastrarCoordenador(email, senha);

                JOptionPane.showMessageDialog(this, "Cadastro de coordenador concluído!");
                dispose();
                TelaLogin telaLogin = new TelaLogin();
            }

            catch (CampoTamanhoInvalidoException | CampoVazioException | CampoInvalidoException | UsuarioJaExisteException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage());
            }
        });
    }

    @Override
    public void initLayout() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        painel.add(new JLabel("Email:"));
        painel.add(campoEmail);

        painel.add(new JLabel("Senha:"));
        painel.add(campoSenha);

        painel.add(btnLogin);

        add(painel);
    }
}
