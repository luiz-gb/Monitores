package org.example.view.screens;

import org.example.exception.ListaVaziaException;
import org.example.model.Edital;
import org.example.service.CadastroService;
import org.example.validator.EditalValidator;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoPrimario;
import org.example.view.components.buttons.BotaoSecundario;
import org.example.view.components.input.InputComboBox;
import org.example.view.components.input.InputData;
import org.example.view.components.input.InputTexto;
import org.example.view.components.tables.TabelaPadrao;
import org.example.view.components.text.LabelTexto;
import org.example.view.components.text.LabelTitulo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaDetalharEditalSemResultado extends BaseTela {

    private InputData campoDataInicio, campoDataFim;
    private InputTexto campoMaxInscricoes, campoPesoCre, campoPesoMedia;
    private Edital edital;
    private CadastroService cadastroService;
    private TabelaPadrao tabelaDisciplinas;
    private TabelaPadrao tabelaAlunos;
    private DefaultTableModel modelDisc;
    private JScrollPane scrollDisciplinas, scrollAlunos;
    private InputComboBox<String> comboDisciplinas;

    private BotaoSecundario btnVoltar, btnCancelarEdicao;
    private BotaoPrimario btnClonar, btnEncerrar, btnEditar, btnSalvarEdicao;

    public TelaDetalharEditalSemResultado(Edital edital) {
        super("Detalhes do Edital", 600, 750);
        this.edital = edital;
        cadastroService = new CadastroService();
        initView();
    }

    @Override
    public void initComponents() {
        campoDataInicio = new InputData();
        configurarInputLeituraInicial(campoDataInicio);

        campoDataFim = new InputData();
        configurarInputLeituraInicial(campoDataFim);

        campoMaxInscricoes = criarInputTextoLeitura();

        campoPesoCre = criarInputTextoLeitura();

        campoPesoMedia = criarInputTextoLeitura();

        String[] colDisc = {"Disciplina", "Vagas Rem.", "Vagas Vol.", "Ação"};
        modelDisc = new DefaultTableModel(colDisc, 0);

        tabelaDisciplinas = new TabelaPadrao(modelDisc);
        tabelaDisciplinas.transformarColunaEmLink(3, Color.RED);

        scrollDisciplinas = new JScrollPane(tabelaDisciplinas);
        estilizarScroll(scrollDisciplinas);

        comboDisciplinas = new InputComboBox<>();
        comboDisciplinas.addItem("Selecione a Disciplina...");

        String[] colAlunos = {"Posição", "Aluno", "CRE", "Média", "Pontuação", "Situação", "Desistiu"};
        DefaultTableModel modelAlunos = new DefaultTableModel(colAlunos, 0);
        tabelaAlunos = new TabelaPadrao(modelAlunos);

        scrollAlunos = new JScrollPane(tabelaAlunos);
        estilizarScroll(scrollAlunos);

        btnVoltar = new BotaoSecundario("Voltar");
        btnClonar = new BotaoPrimario("Clonar Edital");
        btnEncerrar = new BotaoPrimario("Encerrar Edital");
        btnEditar = new BotaoPrimario("Editar Edital");

        btnSalvarEdicao = new BotaoPrimario("Salvar");
        btnCancelarEdicao = new BotaoSecundario("Cancelar");

        carregarValoresComponents();

        btnSalvarEdicao.setVisible(false);
        btnCancelarEdicao.setVisible(false);
    }

    private InputTexto criarInputTextoLeitura() {
        InputTexto input = new InputTexto();
        configurarInputLeituraInicial(input);
        return input;
    }

    private void configuringInputLeituraInicial(JTextComponent input) {
        input.setEditable(false);
        input.setBackground(new Color(230, 230, 230));
    }

    private void configurarInputLeituraInicial(JTextComponent input) {
        input.setEditable(false);
        input.setBackground(new Color(230, 230, 230));
    }

    private void estilizarScroll(JScrollPane scroll) {
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
    }

    private void configurarInput(JTextComponent input, boolean editavel) {
        input.setEditable(editavel);
        input.setBackground(editavel ? Color.WHITE : new Color(230, 230, 230));
    }

    private void alternarModoEdicao(boolean editando) {
        configurarInput(campoDataInicio, editando);
        configurarInput(campoDataFim, editando);
        configurarInput(campoPesoCre, editando);
        configurarInput(campoPesoMedia, editando);

        btnVoltar.setVisible(!editando);
        btnClonar.setVisible(!editando);
        btnEncerrar.setVisible(!editando);
        btnEditar.setVisible(!editando);

        btnCancelarEdicao.setVisible(editando);
        btnSalvarEdicao.setVisible(editando);

        if (editando) {
            JOptionPane.showMessageDialog(this, "Edição habilitada!");
        }
    }

    private void carregarValoresComponents () {
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        campoDataInicio.setText(edital.getDataInicio().format(formatadorData));
        campoDataFim.setText(edital.getDataFinal().format(formatadorData));
        campoMaxInscricoes.setText(String.valueOf(edital.getMaximoInscricoesPorAluno()));
        campoPesoCre.setText(String.valueOf(edital.getPesoCre()));
        campoPesoMedia.setText(String.valueOf(edital.getPesoMedia()));

        modelDisc.setRowCount(0);

        edital.getListaDisciplinas().forEach(e -> {
            Object[] linha = {e.getNomeDisciplina(), e.getVagasRemunerada(), e.getVagasVoluntarias(), "Remover"};
            modelDisc.addRow(linha);
        });
    }

    @Override
    public void initListeners() {
        btnVoltar.addActionListener(e -> dispose());
        btnEditar.addActionListener(e -> alternarModoEdicao(true));

        btnCancelarEdicao.addActionListener(e -> {
            alternarModoEdicao(false);
            carregarValoresComponents();
        });

        btnSalvarEdicao.addActionListener(e -> {
            salvarEdital();
        });

        btnClonar.addActionListener(e -> {
            dispose();
            new TelaCadastroEdital(edital);
        });

        btnEncerrar.addActionListener(e -> {
            edital.setEncerrado(true);
            cadastroService.salvarEdital(edital);
            JOptionPane.showMessageDialog(this, "Edital encerrado com sucesso!");
        });
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

        JLabel lblTab1 = new JLabel("Disciplinas Ofertadas");
        lblTab1.setFont(new Font("Arial", Font.BOLD, 14));
        lblTab1.setBounds(30, 215, 200, 20);
        add(lblTab1);

        scrollDisciplinas.setBounds(30, 240, 530, 100);
        add(scrollDisciplinas);

        JSeparator sep = new JSeparator();
        sep.setBounds(30, 355, 530, 2);
        add(sep);

        add(new LabelTexto("Selecione a Disciplina:") {{ setBounds(30, 365, 200, 20); }});
        comboDisciplinas.setBounds(30, 390, 250, 40);
        add(comboDisciplinas);

        JLabel lblTab2 = new JLabel("Alunos Inscritos / Ranking");
        lblTab2.setFont(new Font("Arial", Font.BOLD, 14));
        lblTab2.setBounds(30, 440, 250, 20);
        add(lblTab2);

        scrollAlunos.setBounds(30, 465, 530, 150);
        add(scrollAlunos);

        btnVoltar.setBounds(30, 640, 125, 40);
        add(btnVoltar);

        btnClonar.setBounds(165, 640, 125, 40);
        add(btnClonar);

        btnEncerrar.setBounds(300, 640, 125, 40);
        add(btnEncerrar);

        btnEditar.setBounds(435, 640, 125, 40);
        add(btnEditar);

        btnCancelarEdicao.setBounds(300, 640, 125, 40);
        add(btnCancelarEdicao);

        btnSalvarEdicao.setBounds(435, 640, 125, 40);
        add(btnSalvarEdicao);
    }

    private void salvarEdital() {
        try {
            String maxInscricoes = campoMaxInscricoes.getText();
            String pesoCre = campoPesoCre.getText();
            String pesoMedia = campoPesoMedia.getText();

            LocalDate dataInicio = campoDataInicio.getData();
            LocalDate dataFinal = campoDataFim.getData();

            EditalValidator.validarMaxInscricoes(maxInscricoes);
            EditalValidator.validarPeso(pesoCre);
            EditalValidator.validarPeso(pesoMedia);
            EditalValidator.validarPesos(Float.parseFloat(pesoCre), Float.parseFloat(pesoMedia));

            edital.setMaximoInscricoesPorAluno(Integer.parseInt(maxInscricoes));
            edital.setPesoCre(Double.parseDouble(pesoCre));
            edital.setPesoMedia(Double.parseDouble(pesoMedia));
            edital.setDataInicio(dataInicio);
            edital.setDataFinal(dataFinal);

//            if (listaDisciplinas.isEmpty()) {
//                throw new ListaVaziaException("O edital precisa ter pelo menos uma disciplina.");
//            }

            cadastroService.salvarEdital(edital);
            carregarValoresComponents();
            alternarModoEdicao(false);

            JOptionPane.showMessageDialog(this, "Edital alterado com sucesso!");

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida ou incompleta.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

}