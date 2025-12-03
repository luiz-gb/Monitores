package org.example.view.screens;

import org.example.exception.*;
import org.example.model.Disciplina;
import org.example.service.CadastroService;
import org.example.validator.DisciplinaValidator;
import org.example.validator.EditalValidator;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoPrimario;
import org.example.view.components.buttons.BotaoSecundario;
import org.example.view.components.input.InputData;
import org.example.view.components.input.InputTexto;
import org.example.view.components.links.LinkTexto;
import org.example.view.components.tables.TabelaPadrao;
import org.example.view.components.text.LabelTexto;
import org.example.view.components.text.LabelTituloSecao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TelaCadastroEdital extends BaseTela {

    private LabelTexto labelIncio;
    private LabelTexto labelFim;
    private LabelTexto labelMaxInscricoes;
    private LabelTexto labelPesoCre;
    private LabelTexto labelPesoMedia;

    private InputData campoDataInicio;
    private InputData campoDataFinal;
    private InputTexto campoMaxInscricoes;
    private InputTexto campoPesoCre;
    private InputTexto campoPesoMedia;

    private LabelTexto labelNomeDisciplina;
    private LabelTexto labelVagasRemuneradas;
    private LabelTexto labelVagasVoluntarias;

    private InputTexto campoNomeDisciplina;
    private InputTexto campoVagasRemuneradas;
    private InputTexto campoVagasVoluntarias;
    private LinkTexto btnAdicionarDisciplina;

    private TabelaPadrao tabelaDisciplinas;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private List<Disciplina> listaDisciplinas;

    private BotaoPrimario btnSalvar;
    private BotaoSecundario btnCancelar;
    private CadastroService cadastroService;

    public TelaCadastroEdital() {
        super("Cadastro de Edital", 500, 600);
        cadastroService = new CadastroService();
        listaDisciplinas = new ArrayList<>();
    }

    @Override
    public void initComponents() {
        labelIncio = new LabelTexto("Data Início:");
        labelFim = new LabelTexto("Data Limite:");
        labelMaxInscricoes = new LabelTexto("Max. Inscrições:");
        labelPesoCre = new LabelTexto("Peso do CRE:");
        labelPesoMedia = new LabelTexto("Peso da Média:");

        campoDataInicio = new InputData();
        campoDataFinal = new InputData();
        campoMaxInscricoes = new InputTexto();
        campoPesoCre = new InputTexto();
        campoPesoMedia = new InputTexto();

        labelNomeDisciplina = new LabelTexto("Nome:");
        labelVagasRemuneradas = new LabelTexto("Remun.:");
        labelVagasVoluntarias = new LabelTexto("Volunt.:");

        campoNomeDisciplina = new InputTexto();
        campoVagasRemuneradas = new InputTexto();
        campoVagasVoluntarias = new InputTexto();

        btnAdicionarDisciplina = new LinkTexto("+ Adicionar Disciplina", SwingConstants.RIGHT);

        String[] colunas = {"Nome", "Remun.", "Volunt.", "Ação"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaDisciplinas = new TabelaPadrao(tableModel);
        tabelaDisciplinas.transformarColunaEmLink(3, Color.RED);

        scrollPane = new JScrollPane(tabelaDisciplinas);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        btnSalvar = new BotaoPrimario("Salvar Edital");
        btnCancelar = new BotaoSecundario("Cancelar");
    }

    @Override
    public void initListeners() {
        btnAdicionarDisciplina.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adicionarDisciplinaNaLista();
            }
        });

        tabelaDisciplinas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tabelaDisciplinas.getSelectedRow();
                int col = tabelaDisciplinas.getSelectedColumn();

                if (col == 3 && row >= 0) {
                    listaDisciplinas.remove(row);
                    tableModel.removeRow(row);
                }
            }
        });

        btnSalvar.addActionListener(e -> salvarEdital());

        btnCancelar.addActionListener(e -> {
            dispose();
            new TelaHomeCoordenador();
        });
    }

    private void adicionarDisciplinaNaLista() {
        String nome = campoNomeDisciplina.getText();
        String vRem = campoVagasRemuneradas.getText();
        String vVol = campoVagasVoluntarias.getText();

        try {
            DisciplinaValidator.validarNome(nome);
            DisciplinaValidator.validarQuantidadeVagas(vRem);
            DisciplinaValidator.validarQuantidadeVagas(vVol);

            Disciplina d = new Disciplina();
            d.setNomeDisciplina(nome);
            d.setVagasRemunerada(Integer.parseInt(vRem));
            d.setVagasVoluntarias(Integer.parseInt(vVol));

            listaDisciplinas.add(d);

            tableModel.addRow(new Object[]{d.getNomeDisciplina(), d.getVagasRemunerada(), d.getVagasVoluntarias(), "Remover"});

            campoNomeDisciplina.setText("");
            campoVagasRemuneradas.setText("");
            campoVagasVoluntarias.setText("");

        } catch (CampoVazioException | NumeroInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void salvarEdital() {
        try {
            String maxInscricoes = campoMaxInscricoes.getText();
            String pesoCre = campoPesoCre.getText();
            String pesoMedia = campoPesoMedia.getText();

            LocalDate dataInicio = campoDataInicio.getData();
            LocalDate dataFinal = campoDataFinal.getData();

            EditalValidator.validarMaxInscricoes(maxInscricoes);
            EditalValidator.validarPeso(pesoCre);
            EditalValidator.validarPeso(pesoMedia);
            EditalValidator.validarPesos(Float.parseFloat(pesoCre), Float.parseFloat(pesoMedia));

            if (listaDisciplinas.isEmpty()) {
                throw new ListaVaziaException("O edital precisa ter pelo menos uma disciplina.");
            }

            cadastroService.cadastrarEdital(dataInicio, dataFinal, Integer.parseInt(maxInscricoes),
                    Double.parseDouble(pesoCre), Double.parseDouble(pesoMedia), listaDisciplinas);

            JOptionPane.showMessageDialog(this, "Edital cadastrado com sucesso!");
            dispose();
            new TelaHomeCoordenador();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida ou incompleta.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void initLayout() {
        JLabel labelTitulo = new JLabel("Cadastro de Edital");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitulo.setForeground(new Color(30, 30, 30));
        labelTitulo.setBounds(20, 20, 300, 30);
        add(labelTitulo);

        labelIncio.setBounds(20, 60, 100, 20);
        add(labelIncio);

        labelFim.setBounds(150, 60, 100, 20);
        add(labelFim);

        labelMaxInscricoes.setBounds(280, 60, 150, 20);
        add(labelMaxInscricoes);

        campoDataInicio.setBounds(20, 85, 110, 40);
        add(campoDataInicio);

        campoDataFinal.setBounds(150, 85, 110, 40);
        add(campoDataFinal);

        campoMaxInscricoes.setBounds(280, 85, 180, 40);
        add(campoMaxInscricoes);

        labelPesoCre.setBounds(20, 135, 200, 20);
        add(labelPesoCre);

        labelPesoMedia.setBounds(250, 135, 200, 20);
        add(labelPesoMedia);

        campoPesoCre.setBounds(20, 160, 210, 40);
        add(campoPesoCre);

        campoPesoMedia.setBounds(250, 160, 210, 40);
        add(campoPesoMedia);

        LabelTituloSecao tituloDisciplinas = new LabelTituloSecao("Disciplinas do Edital");
        tituloDisciplinas.setBounds(20, 220, 440, 40);
        add(tituloDisciplinas);

        labelNomeDisciplina.setFont(new Font("Arial", Font.PLAIN, 12));
        labelNomeDisciplina.setBounds(20, 260, 100, 15);
        add(labelNomeDisciplina);

        campoNomeDisciplina.setBounds(20, 275, 200, 35);
        add(campoNomeDisciplina);

        labelVagasVoluntarias.setFont(new Font("Arial", Font.PLAIN, 12));
        labelVagasRemuneradas.setBounds(230, 260, 80, 15);
        add(labelVagasRemuneradas);

        campoVagasRemuneradas.setBounds(230, 275, 80, 35);
        add(campoVagasRemuneradas);

        labelVagasVoluntarias.setFont(new Font("Arial", Font.PLAIN, 12));
        labelVagasVoluntarias.setBounds(320, 260, 80, 15);
        add(labelVagasVoluntarias);

        campoVagasVoluntarias.setBounds(320, 275, 80, 35);
        add(campoVagasVoluntarias);

        btnAdicionarDisciplina.setBounds(20, 315, 440, 20);
        add(btnAdicionarDisciplina);

        scrollPane.setBounds(20, 345, 440, 135);
        add(scrollPane);

        btnCancelar.setBounds(20, 500, 150, 40);
        add(btnCancelar);

        btnSalvar.setBounds(180, 500, 280, 40);
        add(btnSalvar);
    }
}