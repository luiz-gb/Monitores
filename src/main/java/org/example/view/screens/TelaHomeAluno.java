package org.example.view.screens;

import org.example.enums.StatusEdital;
import org.example.model.Aluno;
import org.example.model.Edital;
import org.example.service.HomeService;
import org.example.view.components.base.BaseTela;
import org.example.view.components.header.BarraSuperior;
import org.example.view.components.tables.ModeloTabelaEdital;
import org.example.view.components.tables.TabelaPadrao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaHomeAluno extends BaseTela {

    private BarraSuperior header;
    List<Edital> listaEditais;
    private JLabel labelTituloSecao;
    private TabelaPadrao tabelaEditais;
    private JScrollPane scrollPane;
    private HomeService homeService;
    private Aluno aluno;

    public TelaHomeAluno(Aluno aluno) {
        super("Home Aluno", 600, 750);
        getContentPane().setBackground(Color.WHITE);
        this.aluno = aluno;
        initView();
    }

    @Override
    public void initComponents() {
        homeService = new HomeService();

        header = new BarraSuperior( "Aluno", false,
                () -> { dispose(); new TelaLogin().setVisible(true); },
                () -> { dispose(); new TelaPerfilAluno(this.aluno, false).setVisible(true); }
        );

        labelTituloSecao = new JLabel("Gerenciamento de Editais");
        labelTituloSecao.setFont(new Font("Arial", Font.BOLD, 18));
        labelTituloSecao.setForeground(new Color(30, 30, 30));

        listaEditais = homeService.retornarEditais();

        tabelaEditais = new TabelaPadrao(new ModeloTabelaEdital(listaEditais));

        tabelaEditais.transformarColunaEmLink(4, new Color(0, 102, 204));

        scrollPane = new JScrollPane(tabelaEditais);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
    }

    @Override
    public void initListeners() {

        tabelaEditais.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabelaEditais.getSelectedRow();
                int coluna = tabelaEditais.getSelectedColumn();

                if (coluna == 4 && linha >= 0) {
                    dispose();

                    Edital editalSelecionado = listaEditais.get(linha);
                    StatusEdital status = editalSelecionado.getStatus();

                    if (status == StatusEdital.ENCERRADO || status == StatusEdital.ABERTO) {
                        new TelaDetalharEditalSemResultadoAluno(editalSelecionado, aluno).setVisible(true);
                    }
                    else if (status == StatusEdital.RESULTADO_FINAL || status == StatusEdital.RESULTADO_PRELIMINAR) {
                        new TelaDetalharEditalComResultadoAluno(editalSelecionado, aluno).setVisible(true);
                    }
                }
            }
        });
    }

    @Override
    public void initLayout() {
        header.setBounds(0, 0, 600, 70);
        add(header);

        labelTituloSecao.setBounds(20, 100, 500, 30);
        add(labelTituloSecao);

        scrollPane.setBounds(20, 140, 540, 500);
        add(scrollPane);
    }
}