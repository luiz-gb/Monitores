package org.example.view;

import org.example.model.Edital;
import org.example.service.HomeService;
import org.example.view.components.EditalTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaHomeCoordenador extends BaseTela{
    private JLabel rotaCadastroEdital;
    private HomeService homeService;
    private JTable tabelaEditais;
    private JScrollPane scrollPane;

    public TelaHomeCoordenador () {
        super("Home Coordenador", 400, 500);
        setVisible(true);
    }

    @Override
    public void initComponents () {
        homeService = new HomeService();

        List<Edital> listaEditais = homeService.retornarEditais();

        tabelaEditais = new JTable(new EditalTableModel(listaEditais));
        scrollPane = new JScrollPane(tabelaEditais);
    }

    @Override
    public void initListeners() {
        rotaCadastroEdital.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TelaHomeCoordenador.this.dispose();
                new TelaCadastroEdital();
            }
        });
    }

    @Override
    public void initLayout() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        rotaCadastroEdital = new JLabel("Cadastrar um edital");
        painel.add(scrollPane);
        painel.add(rotaCadastroEdital);
//        painel.add(campoNome);
//
//        painel.add(new JLabel("Email:"));
//        painel.add(campoEmail);
//
//        painel.add(new JLabel("Matrícula:"));
//        painel.add(campoMatricula);
//
//        painel.add(new JLabel("Senha:"));
//        painel.add(campoSenha);
//
//        painel.add(btnSalvar);
//
        add(painel);
    }
}

