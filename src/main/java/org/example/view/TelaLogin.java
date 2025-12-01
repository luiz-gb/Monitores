package org.example.view;
import org.example.model.Aluno;
import org.example.model.Coordenador;
import org.example.service.LoginService;

import javax.swing.*;

public class TelaLogin extends BaseTela {

    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton btnEntrar;
    private LoginService loginService;

    public TelaLogin() {
        super("Login", 400, 250);
        loginService = new LoginService();
        setVisible(true);
    }

    @Override
    public void initComponents() {
        campoEmail = new JTextField();
        campoSenha = new JPasswordField();
        btnEntrar = new JButton("Entrar");
    }

    @Override
    public void initListeners() {
        btnEntrar.addActionListener(e -> {
            String email = campoEmail.getText();
            String senha = new String(campoSenha.getPassword());

            Object usuario = loginService.fazerLogin(email, senha);

            if (usuario == null) JOptionPane.showMessageDialog(this, "Email ou senha inválidos, tente novamente!");

            if (usuario instanceof Aluno) {
                System.out.println("Aluno logado!");
            }

            else if (usuario instanceof Coordenador) {
                System.out.println("Coordenador logado!");
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

        painel.add(btnEntrar);

        add(painel);
    }
}