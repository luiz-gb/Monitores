package org.example.view;

import org.example.exception.CampoInvalidoException;
import org.example.exception.CampoTamanhoInvalidoException;
import org.example.exception.CampoVazioException;
import org.example.exception.UsuarioJaExisteException;
import org.example.service.CadastroService;
import org.example.validator.ComponentValidator;

import javax.swing.*;

public class TelaCadastroAluno extends BaseTela{
    private JTextField campoNome;
    private JTextField campoEmail;
    private JTextField campoMatricula;
    private JPasswordField campoSenha;
    private JButton btnSalvar;
    private CadastroService cadastroService;

    public TelaCadastroAluno () {
        super("Cadastro aluno", 400, 500);
        setVisible(true);
        cadastroService = new CadastroService();
    }

    @Override
    public void initComponents () {
        campoNome = new JTextField();
        campoEmail = new JTextField();
        campoMatricula = new JTextField();
        campoSenha = new JPasswordField();

        btnSalvar = new JButton("Cadastrar");
    }

    @Override
    public void initListeners() {
        btnSalvar.addActionListener(e -> {
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

                System.out.println("Cadastro concluído!");
            }

            catch (CampoInvalidoException | CampoVazioException | CampoTamanhoInvalidoException |
                   UsuarioJaExisteException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage());
            }
        });
    }

    @Override
    public void initLayout() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);

        painel.add(new JLabel("Email:"));
        painel.add(campoEmail);

        painel.add(new JLabel("Matrícula:"));
        painel.add(campoMatricula);

        painel.add(new JLabel("Senha:"));
        painel.add(campoSenha);

        painel.add(btnSalvar);

        add(painel);
    }
}
