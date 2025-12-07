package org.example.view.screens;

import jakarta.mail.MessagingException;
import org.example.util.Mensageiro;
import org.example.exception.CampoVazioException;
import org.example.model.Aluno;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoPrimario;
import org.example.view.components.buttons.BotaoSecundario;
import org.example.view.components.input.InputTexto;
import org.example.view.components.text.LabelTexto;
import org.example.view.components.text.LabelTitulo;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TelaContatoCoordenador extends BaseTela {

    private Aluno aluno;
    private InputTexto campoDestinatario;
    private InputTexto campoAssunto;
    private JTextArea campoMensagem;
    private JScrollPane scrollMensagem;

    private BotaoPrimario btnEnviar;
    private BotaoSecundario btnVoltar;

    public TelaContatoCoordenador(Aluno aluno) {
        super("Contatar Estudante", 500, 500);
        this.aluno = aluno;
        initView();
        preencherDados();
    }

    @Override
    public void initComponents() {
        campoDestinatario = new InputTexto();
        campoDestinatario.setEditable(false);
        campoDestinatario.setBackground(new Color(245, 245, 245));

        campoAssunto = new InputTexto();

        campoMensagem = new JTextArea();
        campoMensagem.setFont(new Font("Arial", Font.PLAIN, 14));
        campoMensagem.setLineWrap(true);
        campoMensagem.setWrapStyleWord(true);

        scrollMensagem = new JScrollPane(campoMensagem);
        scrollMensagem.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)
        ));
        scrollMensagem.getViewport().setBackground(Color.WHITE);

        btnEnviar = new BotaoPrimario("Enviar E-mail");
        btnVoltar = new BotaoSecundario("Cancelar");
    }

    private void preencherDados() {
        if (aluno != null) {
            campoDestinatario.setText(aluno.getNome() + " <" + aluno.getEmail() + ">");
        }
    }

    @Override
    public void initListeners() {
        btnVoltar.addActionListener(e -> dispose());

        btnEnviar.addActionListener(e -> {
            String assunto = campoAssunto.getText();
            String mensagem = campoMensagem.getText();

            try {
                if (assunto.isEmpty() || mensagem.isEmpty()) {
                    throw new CampoVazioException("Assunto e Mensagem são obrigatórios.");
                }

                btnEnviar.setText("Enviando...");
                btnEnviar.setEnabled(false);

                Mensageiro.enviarEmail(aluno.getEmail(), assunto, mensagem);

                JOptionPane.showMessageDialog(this, "E-mail enviado com sucesso para " + aluno.getNome() + "!");
                dispose();

            } catch (CampoVazioException | MessagingException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao enviar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                btnEnviar.setText("Enviar E-mail");
                btnEnviar.setEnabled(true);
            }
        });
    }

    @Override
    public void initLayout() {
        LabelTitulo titulo = new LabelTitulo("Contatar Estudante");
        titulo.setBounds(0, 20, 500, 30);
        add(titulo);

        add(new LabelTexto("Para:") {{ setBounds(30, 70, 440, 20); }});
        campoDestinatario.setBounds(30, 95, 425, 40);
        add(campoDestinatario);

        add(new LabelTexto("Assunto:") {{ setBounds(30, 145, 440, 20); }});
        campoAssunto.setBounds(30, 170, 425, 40);
        add(campoAssunto);

        add(new LabelTexto("Mensagem:") {{ setBounds(30, 220, 440, 20); }});
        scrollMensagem.setBounds(30, 245, 425, 130);
        add(scrollMensagem);

        btnVoltar.setBounds(30, 400, 120, 40);
        add(btnVoltar);

        btnEnviar.setBounds(315, 400, 140, 40);
        add(btnEnviar);
    }
}