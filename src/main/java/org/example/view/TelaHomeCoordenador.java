package org.example.view;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaHomeCoordenador extends BaseTela{
    private JLabel rotaCadastroEdital;

    public TelaHomeCoordenador () {
        super("Home Coordenador", 400, 500);
        setVisible(true);

    }

    @Override
    public void initComponents () {

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

