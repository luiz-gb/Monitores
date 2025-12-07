package org.example.view.screens;

import org.example.model.Aluno;
import org.example.service.AlunoService;
import org.example.view.components.base.BaseTela;
import org.example.view.components.buttons.BotaoPrimario;
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
import java.util.List;
import java.util.stream.Collectors;

public class TelaListarAlunos extends BaseTela {

    private LabelTitulo titulo;

    private LabelTexto lblNome;
    private InputTexto campoBuscaNome;
    private BotaoPrimario btnBuscar;
    private BotaoSecundario btnVoltar;

    private TabelaPadrao tabelaAlunos;
    private DefaultTableModel modelAlunos;
    private JScrollPane scrollPane;

    private AlunoService alunoService;
    private List<Aluno> listaCompletaAlunos;

    public TelaListarAlunos() {
        super("Listagem de Alunos", 600, 750);
        getContentPane().setBackground(Color.WHITE);

        alunoService = new AlunoService();
        listaCompletaAlunos = alunoService.retornarAlunos();

        initView();
        carregarTabela("");
    }

    @Override
    public void initComponents() {
        titulo = new LabelTitulo("Alunos Existentes");

        lblNome = new LabelTexto("Nome do Aluno:");
        campoBuscaNome = new InputTexto();

        btnBuscar = new BotaoPrimario("Buscar Alunos");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));

        btnVoltar = new BotaoSecundario("Voltar");

        String[] colunas = {"Nome do Aluno", "Matrícula", "Email", "Ação"};
        modelAlunos = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaAlunos = new TabelaPadrao(modelAlunos);
        tabelaAlunos.transformarColunaEmLink(3, new Color(0, 102, 204));

        scrollPane = new JScrollPane(tabelaAlunos);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
    }

    @Override
    public void initListeners() {
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaHomeCoordenador();
        });

        btnBuscar.addActionListener(e -> {
            String termo = campoBuscaNome.getText();
            carregarTabela(termo);
        });

        tabelaAlunos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabelaAlunos.getSelectedRow();
                int coluna = tabelaAlunos.getSelectedColumn();

                if (linha >= 0 && coluna == 3) {
                    String matricula = (String) modelAlunos.getValueAt(linha, 1);

                    Aluno alunoSelecionado = encontrarAlunoPorMatricula(matricula);

                    if (alunoSelecionado != null) {
                        dispose();
                        new TelaPerfilAluno(alunoSelecionado, true).setVisible(true);
                    }
                }
            }
        });
    }

    @Override
    public void initLayout() {
        titulo.setBounds(0, 20, 600, 30);
        add(titulo);

        lblNome.setBounds(30, 70, 200, 20);
        add(lblNome);

        campoBuscaNome.setBounds(30, 95, 540, 40);
        add(campoBuscaNome);

        btnBuscar.setBounds(420, 145, 150, 35);
        add(btnBuscar);

        scrollPane.setBounds(30, 200, 540, 430);
        add(scrollPane);

        btnVoltar.setBounds(30, 650, 120, 40);
        add(btnVoltar);
    }

    private void carregarTabela(String filtroNome) {
        modelAlunos.setRowCount(0);

        if (listaCompletaAlunos == null) return;

        List<Aluno> listaFiltrada = listaCompletaAlunos.stream()
                .filter(a -> a.getNome().toLowerCase().contains(filtroNome.toLowerCase()))
                .collect(Collectors.toList());

        for (Aluno a : listaFiltrada) {
            modelAlunos.addRow(new Object[]{
                    a.getNome(),
                    a.getMatricula(),
                    a.getEmail(),
                    "Ver perfil"
            });
        }
    }

    private Aluno encontrarAlunoPorMatricula(String matricula) {
        return listaCompletaAlunos.stream()
                .filter(a -> a.getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
    }
}