package org.example.view.screens;

import org.example.exception.AlunoJaInscritoException;
import org.example.exception.InscricaoInvalida;
import org.example.exception.NumeroInvalidoException;
import org.example.model.Aluno;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.service.InscricaoService;
import org.example.service.LoginService;
import org.example.validator.InscricaoValidator;
import org.example.view.components.base.BaseTela;
import org.example.view.components.tables.TabelaPadrao;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class TelaDetalharEditalSemResultadoAluno extends BaseTela {

    private TabelaPadrao tabelaEditais;
    private DefaultTableModel modelEdital;
    private TabelaPadrao tabelaDisciplinas;
    private DefaultTableModel modelDisciplina;
    private JScrollPane scrollEditais;
    private JScrollPane scrollDisciplinas;
    private JTextField campoAlunoCRE;
    private JTextField campoAlunoMedia;
    private Edital edital;
    private JButton botaoVoltar;
    private InscricaoService inscricaoService;
    private Aluno aluno;

    public TelaDetalharEditalSemResultadoAluno(Edital edital, Aluno aluno) {
        super("Login", 400, 250);

        this.edital = edital;
        this.aluno = aluno;
        this.inscricaoService = new InscricaoService();

        initView();
    }

    @Override
    public void initComponents() {
        String[] colEdital = {"Data Início", "Data Fim", "Max. Inscricões", "Peso CRE", "Peso Média"};
        modelEdital = new DefaultTableModel(colEdital, 0);
        tabelaEditais = new TabelaPadrao(modelEdital);

        String[] colDiscDisciplinas = {"Nome", "Vagas Remuneradas", "Vagas Voluntárias", "Ação"};
        modelDisciplina = new DefaultTableModel(colDiscDisciplinas, 0);
        tabelaDisciplinas = new TabelaPadrao(modelDisciplina);
        tabelaDisciplinas.transformarColunaEmLink(3, Color.BLUE);

        campoAlunoCRE = new JTextField();
        campoAlunoMedia = new JTextField();

        scrollEditais = new JScrollPane(tabelaEditais);
        estilizarScroll(scrollEditais);
        scrollDisciplinas = new JScrollPane(tabelaDisciplinas);
        estilizarScroll(scrollDisciplinas);

        botaoVoltar = new JButton("Voltar");

        carregarComponents();
    }

    @Override
    public void initListeners() {
        tabelaDisciplinas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabelaDisciplinas.getSelectedRow();
                int coluna = tabelaDisciplinas.getSelectedColumn();

                if (linha >= 0 && coluna == 3) {
                    Disciplina disciplina = edital.getListaDisciplinas().get(linha);

                    try {
                        Double cre = Double.parseDouble(campoAlunoCRE.getText());
                        Double media = Double.parseDouble(campoAlunoMedia.getText());

                        InscricaoValidator.validarNota(cre);
                        InscricaoValidator.validarNota(media);

                        inscricaoService.criarInscricao(aluno, disciplina, cre, media);

                        JOptionPane.showMessageDialog(TelaDetalharEditalSemResultadoAluno.this, "Inscrição concluída!");
                    }

                    catch (InscricaoInvalida | NumeroInvalidoException | AlunoJaInscritoException ex) {
                        JOptionPane.showMessageDialog(TelaDetalharEditalSemResultadoAluno.this, ex.getMessage());
                    }

                    catch (NumberFormatException exNumber) {
                        JOptionPane.showMessageDialog(TelaDetalharEditalSemResultadoAluno.this, "Média e CRE devem ser fornecido como número na inscrição!");
                    }
                }
            }
        });
    }

    @Override
    public void initLayout() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBounds(0, 0, getWidth(), getHeight());
        JLabel alunoCRELabel = new JLabel("Cre atual: ");
        JLabel alunoMediaLabel = new JLabel("Média na Disciplina: ");

        painel.add(scrollEditais);

        painel.add(alunoCRELabel);
        painel.add(campoAlunoCRE);
        painel.add(alunoMediaLabel);
        painel.add(campoAlunoMedia);
        painel.add(scrollDisciplinas);
        painel.add(botaoVoltar);

        add(painel);
    }

    private void estilizarScroll(JScrollPane scroll) {
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
    }

    private void carregarComponents() {
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Object[] linhaEdital = {
                edital.getDataInicio().format(formatadorData),
                edital.getDataFinal().format(formatadorData),
                edital.getMaximoInscricoesPorAluno(),
                edital.getPesoCre(),
                edital.getPesoMedia()
        };

        modelEdital.addRow(linhaEdital);

        List<Disciplina> listaDisciplinas = edital.getListaDisciplinas();

        listaDisciplinas.forEach(e -> {
            Object[] linhaDisciplina = {
                    e.getNomeDisciplina(),
                    e.getVagasRemunerada(),
                    e.getVagasVoluntarias(),
                    "inscrever-se"
            };

            modelDisciplina.addRow(linhaDisciplina);
        });
    }

}