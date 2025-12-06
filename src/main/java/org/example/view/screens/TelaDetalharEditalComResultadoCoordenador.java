package org.example.view.screens;

import org.example.enums.StatusEdital;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;
import org.example.service.CadastroService;
import org.example.service.InscricaoService;
import org.example.util.CalcularPontuacao;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoSecundario;
import org.example.view.components.input.InputComboBox;
import org.example.view.components.tables.TabelaPadrao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaDetalharEditalComResultadoCoordenador extends BaseTela {

    private JScrollPane scrollAlunos;
    private Edital edital;
    private InscricaoService inscricaoService;
    private InputComboBox<Disciplina> comboDisciplinas;
    private DefaultTableModel modelAlunos;
    private TabelaPadrao tabelaAlunos;
    private BotaoSecundario btnVoltar;
    private BotaoSecundario btnFecharEdital;
    private CadastroService cadastroService;
    private BotaoSecundario btnGerarPdf;

    public TelaDetalharEditalComResultadoCoordenador(Edital edital) {
        super("Resultado Preliminar Do Edital", 700, 800);

        this.edital = edital;
        this.inscricaoService = new InscricaoService();
        this.cadastroService = new CadastroService();

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
        btnFecharEdital = new BotaoSecundario("Fechar Edital");
        btnGerarPdf = new BotaoSecundario("Gerar pdf");
        btnGerarPdf.setVisible(false);

        if (edital.getStatus() != StatusEdital.RESULTADO_PRELIMINAR) {
            btnFecharEdital.setVisible(false);
            btnGerarPdf.setVisible(true);
        }

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

        btnFecharEdital.addActionListener(e -> {
            edital.setStatus(StatusEdital.RESULADO_FINAL);
            cadastroService.salvarEdital(edital);
            dispose();
            JOptionPane.showMessageDialog(this, "Resultado Final Lançado!");
            new TelaDetalharEditalComResultadoCoordenador(edital);
        });
    }

    @Override
    public void initLayout() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBounds(0, 0, getWidth(), getHeight());

        painel.add(comboDisciplinas, BorderLayout.NORTH);
        painel.add(scrollAlunos, BorderLayout.CENTER);

        JPanel rodape = new JPanel();
        rodape.add(btnVoltar);
        rodape.add(btnFecharEdital);
        rodape.add(btnGerarPdf);

        painel.add(rodape, BorderLayout.SOUTH);

        add(painel);
    }

    private void estilizarScroll(JScrollPane scroll) {
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
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

                Object[] linha = {
                        listaInscricoes.indexOf(e) + 1 + "º",
                        e.getAluno().getNome(),
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