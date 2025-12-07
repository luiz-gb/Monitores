package org.example.view.screens;

import org.example.enums.StatusEdital;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;
import org.example.service.CadastroService;
import org.example.service.InscricaoService;
import org.example.util.CalcularPontuacao;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoPrimario;
import org.example.view.components.buttons.BotaoSecundario;
import org.example.view.components.input.InputComboBox;
import org.example.view.components.tables.TabelaPadrao;
import org.example.view.components.text.LabelTexto;
import org.example.view.components.text.LabelTitulo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaDetalharEditalComResultadoCoordenador extends BaseTela {

    private Edital edital;
    private InscricaoService inscricaoService;
    private CadastroService cadastroService;

    private InputComboBox<Disciplina> comboDisciplinas;
    private TabelaPadrao tabelaAlunos;
    private DefaultTableModel modelAlunos;
    private JScrollPane scrollAlunos;

    private BotaoSecundario btnVoltar, btnGerarPdf;
    private BotaoPrimario btnFecharEdital;

    public TelaDetalharEditalComResultadoCoordenador(Edital edital) {
        super("Resultado do Edital", 700, 800);
        this.edital = edital;
        this.inscricaoService = new InscricaoService();
        this.cadastroService = new CadastroService();

        if (comboDisciplinas == null) {
            initComponents();
            initLayout();
            initListeners();
        }

        carregarDisciplinasNoCombo();
        configurarVisibilidadeBotoes();
    }

    @Override
    public void initComponents() {
        comboDisciplinas = new InputComboBox<>();
        comboDisciplinas.addItem(null);

        String[] colAlunos = {"Pos.", "Aluno", "CRE", "Média", "Pontuação", "Status"};
        modelAlunos = new DefaultTableModel(colAlunos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaAlunos = new TabelaPadrao(modelAlunos);

        scrollAlunos = new JScrollPane(tabelaAlunos);
        scrollAlunos.getViewport().setBackground(Color.WHITE);
        scrollAlunos.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        btnVoltar = new BotaoSecundario("Voltar");
        btnFecharEdital = new BotaoPrimario("Fechar Edital");
        btnGerarPdf = new BotaoSecundario("Gerar PDF");
    }

    private void configurarVisibilidadeBotoes() {
        if (edital.getStatus() == StatusEdital.RESULTADO_PRELIMINAR) {
            btnFecharEdital.setVisible(true);
            btnGerarPdf.setVisible(false);
        } else {
            btnFecharEdital.setVisible(false);
            btnGerarPdf.setVisible(true);
        }
    }

    private void carregarDisciplinasNoCombo() {
        if (edital.getListaDisciplinas() != null) {
            edital.getListaDisciplinas().forEach(d -> comboDisciplinas.addItem(d));
        }
    }

    @Override
    public void initListeners() {
        comboDisciplinas.addActionListener(e -> {
            Disciplina disciplinaSelecionada = (Disciplina) comboDisciplinas.getSelectedItem();
            if (disciplinaSelecionada != null) {
                carregarTabelaAlunos(disciplinaSelecionada);
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeCoordenador().setVisible(true);
        });

        btnFecharEdital.addActionListener(e -> {
            Object[] options = {"Sim", "Não"};
            int resposta = JOptionPane.showOptionDialog(
                this,
                "Deseja realmente encerrar o edital?\nO resultado será consolidado como FINAL.",
                "Confirmar Fechamento",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (resposta == JOptionPane.YES_OPTION) {
                edital.setStatus(StatusEdital.RESULTADO_FINAL);
                cadastroService.salvarEdital(edital);

                JOptionPane.showMessageDialog(this, "Resultado Final Lançado com Sucesso!");
                dispose();
                new TelaDetalharEditalComResultadoCoordenador(edital).setVisible(true);
            }
        });

        btnGerarPdf.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidade de PDF em desenvolvimento.");
        });
    }

    private void carregarTabelaAlunos(Disciplina disciplina) {
        List<Inscricao> listaInscricoes = inscricaoService.processarResultadoDaDisciplina(disciplina);
        modelAlunos.setRowCount(0);

        if (!listaInscricoes.isEmpty()) {
            for (int i = 0; i < listaInscricoes.size(); i++) {
                Inscricao inscricao = listaInscricoes.get(i);

                double pontuacao = CalcularPontuacao.calcularPontuacaoAluno(
                        edital.getPesoCre(),
                        edital.getPesoMedia(),
                        inscricao.getAlunoCRE(),
                        inscricao.getAlunoMedia()
                );

                Object[] linha = {
                        (i + 1) + "º",
                        inscricao.getAluno().getNome(),
                        inscricao.getAlunoCRE(),
                        inscricao.getAlunoMedia(),
                        String.format("%.2f", pontuacao),
                        inscricao.getResultadoInscricao().toString().toLowerCase()
                };
                modelAlunos.addRow(linha);
            }
        }
    }

    @Override
    public void initLayout() {
        LabelTitulo titulo = new LabelTitulo("Resultado do Edital");
        titulo.setBounds(0, 20, 700, 30);
        add(titulo);

        add(new LabelTexto("Selecione a Disciplina:") {{ setBounds(50, 70, 300, 20); }});
        comboDisciplinas.setBounds(50, 95, 300, 40);
        add(comboDisciplinas);

        JLabel lblRanking = new JLabel("Classificação Geral");
        lblRanking.setFont(new Font("Arial", Font.BOLD, 14));
        lblRanking.setBounds(50, 150, 300, 20);
        add(lblRanking);

        scrollAlunos.setBounds(50, 180, 600, 480);
        add(scrollAlunos);

        btnVoltar.setBounds(50, 690, 120, 40);
        add(btnVoltar);

        btnFecharEdital.setBounds(530, 690, 120, 40);
        add(btnFecharEdital);

        btnGerarPdf.setBounds(530, 690, 120, 40);
        add(btnGerarPdf);
    }
}