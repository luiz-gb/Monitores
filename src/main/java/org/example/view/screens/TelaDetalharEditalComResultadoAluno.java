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
import org.example.view.components.tables.TabelaPadrao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaDetalharEditalComResultadoAluno extends BaseTela {

    private JScrollPane scrollAlunos;
    private Edital edital;
    private InscricaoService inscricaoService;
    private InputComboBox<Disciplina> comboDisciplinas;
    private DefaultTableModel modelAlunos;
    private TabelaPadrao tabelaAlunos;
    private BotaoSecundario btnVoltar;
    private JLabel labelDesistir;
    private Aluno aluno;

    public TelaDetalharEditalComResultadoAluno(Edital edital, Aluno aluno) {
        super("Resultado Preliminar Do Edital", 700, 800);

        this.edital = edital;
        this.aluno = aluno;
        this.inscricaoService = new InscricaoService();

        initView();
    }

    @Override
    public void initComponents() {

        comboDisciplinas = new InputComboBox<>();
        comboDisciplinas.addItem(null);

        String[] colAlunos = {"Posição", "Aluno", "CRE", "Média", "Pontuação", "Status"};
        modelAlunos = new DefaultTableModel(colAlunos, 0);
        tabelaAlunos = new TabelaPadrao(modelAlunos);

        scrollAlunos = new JScrollPane(tabelaAlunos);

        btnVoltar = new BotaoSecundario("Voltar");

        labelDesistir = new JLabel("Desistir da inscrição!");
        labelDesistir.setVisible(false);

        carregarComponents();
    }

    @Override
    public void initListeners() {
        comboDisciplinas.addActionListener(e -> {
            Disciplina disciplinaSelecionada = (Disciplina) comboDisciplinas.getSelectedItem();

            if (disciplinaSelecionada != null) carregarTabelaAlunos(disciplinaSelecionada);
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeCoordenador();
        });

        labelDesistir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int resposta = JOptionPane.showConfirmDialog(
                        TelaDetalharEditalComResultadoAluno.this,
                        "Tem certeza que deseja cancelar sua inscrição (não poderá mais inscrever-se nesta disciplina)?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (resposta == JOptionPane.YES_OPTION) {
                    Disciplina disciplinaSelecionada = (Disciplina) comboDisciplinas.getSelectedItem();

                    inscricaoService.desistirInscricao(aluno, disciplinaSelecionada);
                    JOptionPane.showMessageDialog(TelaDetalharEditalComResultadoAluno.this, "Desistência confirmada!");
                    TelaDetalharEditalComResultadoAluno.this.dispose();
                    new TelaDetalharEditalComResultadoAluno(edital, aluno);
                }
            }
        });
    }

    @Override
    public void initLayout() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBounds(0, 0, getWidth(), getHeight());

        painel.add(comboDisciplinas, BorderLayout.NORTH);
        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new BorderLayout());
        painelCentro.add(scrollAlunos, BorderLayout.CENTER);
        painelCentro.add(labelDesistir, BorderLayout.SOUTH);

        painel.add(painelCentro, BorderLayout.CENTER);

        JPanel rodape = new JPanel();
        rodape.add(btnVoltar);

        painel.add(rodape, BorderLayout.SOUTH);

        add(painel);
    }

    private void carregarComponents() {
        if (!edital.getListaDisciplinas().isEmpty()) edital.getListaDisciplinas().forEach(i -> {
            comboDisciplinas.addItem(i);
        });
    }

    private void carregarTabelaAlunos(Disciplina disciplina) {
        List<Inscricao> listaInscricoes = inscricaoService.processarResultadoDaDisciplina(disciplina);

        modelAlunos.setRowCount(0);

        if (!listaInscricoes.isEmpty()) {
            for (int i = 0; i < listaInscricoes.size(); i++) {
                Inscricao e = listaInscricoes.get(i);
                int posicao = i + 1;

                double pontuacaoAluno = CalcularPontuacao.calcularPontuacaoAluno(
                        e.getDisciplina().getEdital().getPesoCre(),
                        e.getDisciplina().getEdital().getPesoMedia(),
                        e.getAlunoCRE(),
                        e.getAlunoMedia()
                );

                Aluno alunoAtual = e.getAluno();

                boolean alunoExitseNaTabela = alunoAtual.getEmail().equals(aluno.getEmail());

                if (alunoExitseNaTabela && edital.getStatus() != StatusEdital.RESULADO_FINAL && e.getResultadoInscricao() != ResultadoInscricao.DESISTENTE) {
                    System.out.println("Aluno encontrado!");
                    labelDesistir.setVisible(true);
                }

                Object[] linha = {
                        listaInscricoes.indexOf(e) + 1 + "º",
                        alunoExitseNaTabela ? e.getAluno().getNome() + " (eu)": e.getAluno().getNome(),
                        String.valueOf(e.getAlunoCRE()),
                        String.valueOf(e.getAlunoMedia()),
                        String.valueOf(pontuacaoAluno),
                        String.valueOf(e.getResultadoInscricao()).toLowerCase()
                };

                modelAlunos.addRow(linha);
            };
        }
    }

}