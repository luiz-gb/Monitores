package org.example.view;
import javax.swing.*;

public class Login extends BaseTela {

    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton btnEntrar;

    public Login() {
        super("Login", 400, 250);
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
            System.out.println("Login tentado: " + email + " / " + senha);
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