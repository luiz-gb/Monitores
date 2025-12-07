package org.example.view.screens;

import org.example.enums.ResultadoInscricao;
import org.example.enums.StatusEdital;
import org.example.model.Aluno;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;
import org.example.service.InscricaoService;
import org.example.util.CalcularPontuacao;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoSecundario;
import org.example.view.components.input.InputComboBox;
import org.example.view.components.links.LinkTexto;
import org.example.view.components.tables.TabelaPadrao;
import org.example.view.components.text.LabelTexto;
import org.example.view.components.text.LabelTitulo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaDetalharEditalComResultadoAluno extends BaseTela {

    private Edital edital;
    private Aluno aluno;
    private InscricaoService inscricaoService;

    private InputComboBox<Disciplina> comboDisciplinas;
    private TabelaPadrao tabelaAlunos;
    private DefaultTableModel modelAlunos;
    private JScrollPane scrollAlunos;

    private BotaoSecundario btnVoltar;
    private LinkTexto linkDesistir;

    public TelaDetalharEditalComResultadoAluno(Edital edital, Aluno aluno) {
        super("Resultado do Edital", 600, 750);
        this.edital = edital;
        this.aluno = aluno;
        this.inscricaoService = new InscricaoService();

        if (comboDisciplinas == null) {
            initView();
        }

        carregarDisciplinasNoCombo();
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

        linkDesistir = new LinkTexto("Desistir desta vaga", SwingConstants.RIGHT);
        linkDesistir.setForeground(Color.RED);
        linkDesistir.setVisible(false);
    }

    private void carregarDisciplinasNoCombo() {
        if (edital != null && edital.getListaDisciplinas() != null) {
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
            new TelaHomeAluno(aluno).setVisible(true);
        });

        linkDesistir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!linkDesistir.isVisible()) return;

                Object[] options = {"Sim", "Não"};

                int resposta = JOptionPane.showOptionDialog(
                        TelaDetalharEditalComResultadoAluno.this,
                        "Tem certeza que deseja desistir desta vaga?\nEssa ação é irreversível.",
                        "Confirmar Desistência",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (resposta == JOptionPane.YES_OPTION) {
                    Disciplina disciplinaSelecionada = (Disciplina) comboDisciplinas.getSelectedItem();
                    try {
                        inscricaoService.desistirInscricao(aluno, disciplinaSelecionada);
                        JOptionPane.showMessageDialog(TelaDetalharEditalComResultadoAluno.this, "Desistência registrada com sucesso.");

                        carregarTabelaAlunos(disciplinaSelecionada);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(TelaDetalharEditalComResultadoAluno.this, "Erro ao desistir: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private void carregarTabelaAlunos(Disciplina disciplina) {
        List<Inscricao> listaInscricoes = inscricaoService.processarResultadoDaDisciplina(disciplina);
        modelAlunos.setRowCount(0);
        linkDesistir.setVisible(false);

        if (!listaInscricoes.isEmpty()) {
            for (int i = 0; i < listaInscricoes.size(); i++) {
                Inscricao inscricao = listaInscricoes.get(i);

                double pontuacao = CalcularPontuacao.calcularPontuacaoAluno(
                        edital.getPesoCre(),
                        edital.getPesoMedia(),
                        inscricao.getAlunoCRE(),
                        inscricao.getAlunoMedia()
                );

                boolean isAlunoLogado = inscricao.getAluno().getEmail().equals(aluno.getEmail());

                String nomeAluno = isAlunoLogado ? inscricao.getAluno().getNome() + " (Você)" : inscricao.getAluno().getNome();

                if (isAlunoLogado &&
                        edital.getStatus() != StatusEdital.RESULTADO_FINAL &&
                        inscricao.getResultadoInscricao() != ResultadoInscricao.DESISTENTE) {
                    linkDesistir.setVisible(true);
                }

                Object[] linha = {
                    (i + 1) + "º",
                    nomeAluno,
                    inscricao.getAlunoCRE(),
                    inscricao.getAlunoMedia(),
                    String.format("%.2f", pontuacao),
                    inscricao.getResultadoInscricao()
                };
                modelAlunos.addRow(linha);
            }
        }
    }

    @Override
    public void initLayout() {
        LabelTitulo titulo = new LabelTitulo("Resultado Preliminar");
        titulo.setBounds(0, 20, 600, 30);
        add(titulo);

        add(new LabelTexto("Selecione a Disciplina:") {{ setBounds(30, 70, 300, 20); }});
        comboDisciplinas.setBounds(30, 95, 300, 40);
        add(comboDisciplinas);

        JLabel lblRanking = new JLabel("Classificação dos Candidatos");
        lblRanking.setFont(new Font("Arial", Font.BOLD, 14));
        lblRanking.setBounds(30, 150, 300, 20);
        add(lblRanking);

        scrollAlunos.setBounds(30, 175, 540, 400);
        add(scrollAlunos);

        linkDesistir.setBounds(30, 585, 540, 20);
        add(linkDesistir);

        btnVoltar.setBounds(30, 640, 120, 40);
        add(btnVoltar);
    }
}