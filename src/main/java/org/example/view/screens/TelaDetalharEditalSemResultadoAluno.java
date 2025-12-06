package org.example.view.screens;

import org.example.exception.AlunoJaInscritoException;
import org.example.exception.InscricaoInvalida;
import org.example.exception.NumeroInvalidoException;
import org.example.model.Aluno;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.service.InscricaoService;
import org.example.validator.InscricaoValidator;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoSecundario;
import org.example.view.components.input.InputTexto;
import org.example.view.components.tables.TabelaPadrao;
import org.example.view.components.text.LabelTexto;
import org.example.view.components.text.LabelTitulo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaDetalharEditalSemResultadoAluno extends BaseTela {

    private Edital edital;
    private Aluno aluno;
    private InscricaoService inscricaoService;

    private InputTexto campoDataInicio, campoDataFim, campoMaxInscricoes;
    private InputTexto campoPesoCre, campoPesoMedia;
    private InputTexto campoAlunoCRE, campoAlunoMedia;

    private TabelaPadrao tabelaDisciplinas;
    private DefaultTableModel modelDisciplina;
    private JScrollPane scrollDisciplinas;

    private BotaoSecundario btnVoltar;

    public TelaDetalharEditalSemResultadoAluno(Edital edital, Aluno aluno) {
        super("Detalhes do Edital", 600, 750);
        this.edital = edital;
        this.aluno = aluno;
        this.inscricaoService = new InscricaoService();

        if (campoDataInicio == null) {
            initComponents();
            initLayout();
            initListeners();
        }

        preencherDadosEdital();
        carregarDisciplinas();
    }

    @Override
    public void initComponents() {
        campoDataInicio = criarInputLeitura();
        campoDataFim = criarInputLeitura();
        campoMaxInscricoes = criarInputLeitura();
        campoPesoCre = criarInputLeitura();
        campoPesoMedia = criarInputLeitura();

        campoAlunoCRE = new InputTexto();
        campoAlunoMedia = new InputTexto();

        String[] colDiscDisciplinas = {"Nome", "Vagas Rem.", "Vagas Vol.", "Ação"};
        modelDisciplina = new DefaultTableModel(colDiscDisciplinas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaDisciplinas = new TabelaPadrao(modelDisciplina);
        tabelaDisciplinas.transformarColunaEmLink(3, new Color(0, 102, 204));

        scrollDisciplinas = new JScrollPane(tabelaDisciplinas);
        scrollDisciplinas.getViewport().setBackground(Color.WHITE);
        scrollDisciplinas.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        btnVoltar = new BotaoSecundario("Voltar");
    }

    private InputTexto criarInputLeitura() {
        InputTexto input = new InputTexto();
        input.setEditable(false);
        input.setBackground(new Color(245, 245, 245));
        return input;
    }

    private void preencherDadosEdital() {
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        campoDataInicio.setText(edital.getDataInicio().format(formatadorData));
        campoDataFim.setText(edital.getDataFinal().format(formatadorData));
        campoMaxInscricoes.setText(String.valueOf(edital.getMaximoInscricoesPorAluno()));
        campoPesoCre.setText(String.valueOf(edital.getPesoCre()));
        campoPesoMedia.setText(String.valueOf(edital.getPesoMedia()));
    }

    private void carregarDisciplinas() {
        List<Disciplina> lista = edital.getListaDisciplinas();
        if (lista != null) {
            lista.forEach(d -> modelDisciplina.addRow(new Object[]{
                d.getNomeDisciplina(),
                d.getVagasRemunerada(),
                d.getVagasVoluntarias(),
                "Inscrever-se"
            }));
        }
    }

    @Override
    public void initListeners() {
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeAluno(this.aluno).setVisible(true);
        });

        tabelaDisciplinas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabelaDisciplinas.getSelectedRow();
                int coluna = tabelaDisciplinas.getSelectedColumn();

                if (linha >= 0 && coluna == 3) {
                    realizarInscricao(linha);
                }
            }
        });
    }

    private void realizarInscricao(int linha) {
        Disciplina disciplina = edital.getListaDisciplinas().get(linha);

        try {
            String txtCre = campoAlunoCRE.getText().replace(",", ".");
            String txtMedia = campoAlunoMedia.getText().replace(",", ".");

            if (txtCre.isEmpty() || txtMedia.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe seu CRE e Média antes de se inscrever.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Double cre = Double.parseDouble(txtCre);
            Double media = Double.parseDouble(txtMedia);

            InscricaoValidator.validarNota(cre);
            InscricaoValidator.validarNota(media);

            inscricaoService.criarInscricao(aluno, disciplina, cre, media);

            JOptionPane.showMessageDialog(this, "Inscrição realizada com sucesso!");
        } catch (InscricaoInvalida | NumeroInvalidoException | AlunoJaInscritoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException exNumber) {
            JOptionPane.showMessageDialog(this, "CRE e Média devem ser numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void initLayout() {
        LabelTitulo titulo = new LabelTitulo("Detalhes do Edital");
        titulo.setBounds(0, 20, 600, 30);
        add(titulo);

        add(new LabelTexto("Data Início:") {{ setBounds(30, 60, 100, 20); }});
        campoDataInicio.setBounds(30, 85, 120, 40);
        add(campoDataInicio);

        add(new LabelTexto("Data Limite:") {{ setBounds(170, 60, 100, 20); }});
        campoDataFim.setBounds(170, 85, 120, 40);
        add(campoDataFim);

        add(new LabelTexto("Máx. Inscrições:") {{ setBounds(310, 60, 150, 20); }});
        campoMaxInscricoes.setBounds(310, 85, 250, 40);
        add(campoMaxInscricoes);

        add(new LabelTexto("Peso do CRE:") {{ setBounds(30, 135, 150, 20); }});
        campoPesoCre.setBounds(30, 160, 250, 40);
        add(campoPesoCre);

        add(new LabelTexto("Peso da Média:") {{ setBounds(310, 135, 150, 20); }});
        campoPesoMedia.setBounds(310, 160, 250, 40);
        add(campoPesoMedia);

        JSeparator sep1 = new JSeparator();
        sep1.setBounds(30, 220, 530, 2);
        add(sep1);

        JLabel lblDados = new JLabel("Seus Dados para Inscrição");
        lblDados.setFont(new Font("Arial", Font.BOLD, 14));
        lblDados.setBounds(30, 230, 250, 20);
        add(lblDados);

        add(new LabelTexto("Seu CRE:") {{ setBounds(30, 260, 150, 20); }});
        campoAlunoCRE.setBounds(30, 285, 250, 40);
        add(campoAlunoCRE);

        add(new LabelTexto("Média na Disciplina:") {{ setBounds(310, 260, 200, 20); }});
        campoAlunoMedia.setBounds(310, 285, 250, 40);
        add(campoAlunoMedia);

        JSeparator sep2 = new JSeparator();
        sep2.setBounds(30, 345, 530, 2);
        add(sep2);

        JLabel lblDisc = new JLabel("Disciplinas Disponíveis");
        lblDisc.setFont(new Font("Arial", Font.BOLD, 14));
        lblDisc.setBounds(30, 355, 250, 20);
        add(lblDisc);

        scrollDisciplinas.setBounds(30, 385, 530, 250);
        add(scrollDisciplinas);

        btnVoltar.setBounds(30, 650, 120, 40);
        add(btnVoltar);
    }
}