package org.example.view.screens;

import org.example.exception.CampoInvalidoException;
import org.example.exception.CampoTamanhoInvalidoException;
import org.example.exception.CampoVazioException;
import org.example.model.Aluno;
import org.example.service.AlunoService;
import org.example.validator.ComponentValidator;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoPrimario;
import org.example.view.components.buttons.BotaoSecundario;
import org.example.view.components.input.InputTexto;
import org.example.view.components.tables.TabelaPadrao;
import org.example.view.components.text.LabelTexto;
import org.example.view.components.text.LabelTitulo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class TelaPerfilAluno extends BaseTela {

    private Aluno aluno;
    private boolean isCoordenador;
    private AlunoService alunoService;

    private InputTexto campoNome, campoEmail, campoMatricula;

    private TabelaPadrao tabelaHistorico;
    private DefaultTableModel modelHistorico;
    private JScrollPane scrollPane;

    private BotaoSecundario btnVoltar;
    private BotaoPrimario btnEditar;

    private BotaoPrimario btnSalvar;
    private BotaoSecundario btnCancelarEdicao;

    public TelaPerfilAluno(Aluno aluno, boolean isCoordenador) {
        super("Perfil do Aluno", 600, 750);
        this.aluno = aluno;
        this.isCoordenador = isCoordenador;
        this.alunoService = new AlunoService();

        initView();
        preencherDados();
    }

    @Override
    public void initComponents() {
        campoNome = criarInputLeitura();
        campoEmail = criarInputLeitura();
        campoMatricula = criarInputLeitura();

        String[] colunas = {"Disciplina", "Tipo Aprovação", "Data", "Pontuação"};
        modelHistorico = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaHistorico = new TabelaPadrao(modelHistorico);
        scrollPane = new JScrollPane(tabelaHistorico);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        btnVoltar = new BotaoSecundario("Voltar");
        btnEditar = new BotaoPrimario("Editar Perfil");

        btnSalvar = new BotaoPrimario("Salvar Alterações");
        btnCancelarEdicao = new BotaoSecundario("Cancelar");

        btnSalvar.setVisible(false);
        btnCancelarEdicao.setVisible(false);
    }

    private InputTexto criarInputLeitura() {
        InputTexto input = new InputTexto();
        configurarInput(input, false);
        return input;
    }

    private void configurarInput(JTextComponent input, boolean editavel) {
        input.setEditable(editavel);
        input.setBackground(editavel ? Color.WHITE : new Color(245, 245, 245));
    }

    private void preencherDados() {
        if (aluno != null) {
            campoNome.setText(aluno.getNome());
            campoEmail.setText(aluno.getEmail());
            campoMatricula.setText(aluno.getMatricula());
        }
    }

    private void alternarModoEdicao(boolean editando) {
        if (editando) {
            JOptionPane.showMessageDialog(this, "Edição habilitada!");
        }

        configurarInput(campoNome, editando);
        configurarInput(campoEmail, editando);
        configurarInput(campoMatricula, editando);

        btnVoltar.setVisible(!editando);
        btnEditar.setVisible(!editando);

        btnCancelarEdicao.setVisible(editando);
        btnSalvar.setVisible(editando);
    }

    @Override
    public void initListeners() {
        btnVoltar.addActionListener(e -> {
            dispose();
            if (isCoordenador) {
                new TelaListarAlunos().setVisible(true);
            } else {
                new TelaHomeAluno(aluno).setVisible(true);
            }
        });

        btnEditar.addActionListener(e -> alternarModoEdicao(true));

        btnCancelarEdicao.addActionListener(e -> {
            preencherDados();
            alternarModoEdicao(false);
        });

        btnSalvar.addActionListener(e -> {
            String novoNome = campoNome.getText();
            String novoEmail = campoEmail.getText();
            String novaMatricula = campoMatricula.getText();

            try {
                ComponentValidator.validarNome(novoNome);
                ComponentValidator.validarEmail(novoEmail);
                ComponentValidator.validarMatricula(novaMatricula);

                aluno.setNome(novoNome);
                aluno.setEmail(novoEmail);
                aluno.setMatricula(novaMatricula);

                alunoService.atualizarAluno(aluno);

                JOptionPane.showMessageDialog(this, "Perfil atualizado com sucesso!");
                alternarModoEdicao(false);

            } catch (CampoInvalidoException | CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar no banco: " + ex.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void initLayout() {
        LabelTitulo titulo = new LabelTitulo("Perfil do Aluno");
        titulo.setBounds(0, 20, 600, 30);
        add(titulo);

        add(new LabelTexto("Nome:") {{ setBounds(30, 70, 500, 20); }});
        campoNome.setBounds(30, 95, 540, 40);
        add(campoNome);

        add(new LabelTexto("Email:") {{ setBounds(30, 145, 500, 20); }});
        campoEmail.setBounds(30, 170, 540, 40);
        add(campoEmail);

        add(new LabelTexto("Matrícula:") {{ setBounds(30, 220, 500, 20); }});
        campoMatricula.setBounds(30, 245, 540, 40);
        add(campoMatricula);

        if (isCoordenador) {
            JSeparator sep = new JSeparator();
            sep.setBounds(30, 310, 540, 2);
            add(sep);

            JLabel lblHistorico = new JLabel("Histórico de Monitoria");
            lblHistorico.setFont(new Font("Arial", Font.BOLD, 14));
            lblHistorico.setBounds(30, 320, 300, 20);
            add(lblHistorico);

            scrollPane.setBounds(30, 350, 540, 250);
            add(scrollPane);
        }

        btnVoltar.setBounds(30, 640, 150, 45);
        add(btnVoltar);

        btnCancelarEdicao.setBounds(30, 640, 150, 45);
        add(btnCancelarEdicao);

        btnEditar.setBounds(420, 640, 150, 45);
        add(btnEditar);

        btnSalvar.setBounds(420, 640, 150, 45);
        add(btnSalvar);
    }
}