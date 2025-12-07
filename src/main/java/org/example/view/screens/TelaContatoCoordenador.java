package org.example.view.screens;

import jakarta.mail.MessagingException;
import org.example.domain.Mensageiro;
import org.example.enums.ResultadoInscricao;
import org.example.enums.StatusEdital;
import org.example.exception.CampoVazioException;
import org.example.model.Aluno;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;
import org.example.service.InscricaoService;
import org.example.util.CalcularPontuacao;
import org.example.validator.ComponentValidator;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoSecundario;
import org.example.view.components.input.InputComboBox;
import org.example.view.components.tables.TabelaPadrao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaContatoCoordenador extends BaseTela {

    private JScrollPane scrollAlunos;
    private Edital edital;
    private InscricaoService inscricaoService;
    private InputComboBox<Disciplina> comboDisciplinas;
    private DefaultTableModel modelAlunos;
    private TabelaPadrao tabelaAlunos;
    private BotaoSecundario btnVoltar;
    private JLabel labelDesistir;
    private Aluno aluno;
    private JTextField campoAssunto;
    private JTextArea campoMensagem;
    private JButton btnEnviarEmail;

    public TelaContatoCoordenador(Aluno aluno) {
        super("Contato", 700, 800);

        this.aluno = aluno;
        this.inscricaoService = new InscricaoService();

        initView();
    }

    @Override
    public void initComponents() {

        campoAssunto = new JTextField("");
        campoMensagem = new JTextArea("");
        btnEnviarEmail = new JButton("Enviar");
    }

    @Override
    public void initListeners() {
        btnEnviarEmail.addActionListener(e -> {
            String titulo = campoAssunto.getText();
            String mensagem = campoMensagem.getText();

            try {
                ComponentValidator.validarCampoEmail(titulo);
                ComponentValidator.validarCampoEmail(mensagem);

                Mensageiro.enviarEmail(aluno.getEmail(), titulo, mensagem);
                JOptionPane.showMessageDialog(this, "Email enviado com sucesso!");
            }

            catch (CampoVazioException | MessagingException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage());
            }
        });
    }

    @Override
    public void initLayout() {

        JLabel labelAssunto = new JLabel("Assunto: ");
        labelAssunto.setBounds(30, 30, 100, 20);
        add(labelAssunto);

        campoAssunto.setBounds(30, 55, 620, 30);
        add(campoAssunto);

        JLabel labelMensagem = new JLabel("Mensagem: ");
        labelMensagem.setBounds(30, 100, 100, 20);
        add(labelMensagem);

        campoMensagem.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        campoMensagem.setBounds(30, 125, 620, 200);
        add(campoMensagem);

        btnEnviarEmail.setBounds(550, 340, 100, 30);
        add(btnEnviarEmail);
    }


}