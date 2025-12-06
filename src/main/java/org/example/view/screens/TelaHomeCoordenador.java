package org.example.view.screens;

import org.example.model.Edital;
import org.example.service.HomeService;
import org.example.view.components.header.BarraSuperior;
import org.example.view.components.base.BaseTela;
import org.example.view.components.tables.ModeloTabelaEdital;
import org.example.view.components.tables.TabelaPadrao;
import org.example.view.components.links.LinkTexto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaHomeCoordenador extends BaseTela {

    private BarraSuperior header;
    List<Edital> listaEditais;
    private JLabel labelTituloSecao;
    private TabelaPadrao tabelaEditais;
    private JScrollPane scrollPane;
    private LinkTexto linkCadastroEdital;
    private HomeService homeService;

    public TelaHomeCoordenador() {
        super("Home Coordenador", 500, 600);
        getContentPane().setBackground(Color.WHITE);
        initView();
    }

    @Override
    public void initComponents() {
        homeService = new HomeService();

        header = new BarraSuperior("Coordenador", true, () -> {
            dispose();
            new TelaLogin();
        });

        labelTituloSecao = new JLabel("Gerenciamento de Editais");
        labelTituloSecao.setFont(new Font("Arial", Font.BOLD, 18));
        labelTituloSecao.setForeground(new Color(30, 30, 30));

        listaEditais = homeService.retornarEditais();

        tabelaEditais = new TabelaPadrao(new ModeloTabelaEdital(listaEditais));

        tabelaEditais.transformarColunaEmLink(4, new Color(0, 102, 204));

        scrollPane = new JScrollPane(tabelaEditais);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        linkCadastroEdital = new LinkTexto("Cadastrar um Edital", SwingConstants.RIGHT);
    }

    @Override
    public void initListeners() {
        linkCadastroEdital.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TelaCadastroEdital();
            }
        });

        tabelaEditais.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabelaEditais.getSelectedRow();
                int coluna = tabelaEditais.getSelectedColumn();

                if (coluna == 4 && linha >= 0) {
                    dispose();
                    new TelaDetalharEditalSemResultadoCoordenador(listaEditais.get(linha));
                }
            }
        });
    }

    @Override
    public void initLayout() {
        header.setBounds(0, 0, 500, 70);
        add(header);

        labelTituloSecao.setBounds(20, 100, 400, 30);
        add(labelTituloSecao);

        scrollPane.setBounds(20, 140, 440, 360);
        add(scrollPane);

        linkCadastroEdital.setBounds(20, 520, 440, 20);
        add(linkCadastroEdital);
    }
}