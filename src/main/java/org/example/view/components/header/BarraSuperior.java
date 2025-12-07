package org.example.view.components.header;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class BarraSuperior extends JPanel {

    private JLabel labelBemVindo;
    private JLabel btnSair;
    private JLabel btnAcaoSecundaria;

    private Runnable acaoSair;
    private Runnable acaoSecundaria;

    public BarraSuperior(String nomeUsuario, boolean isCoordenador, Runnable acaoSair, Runnable acaoSecundaria) {
        this.acaoSair = acaoSair;
        this.acaoSecundaria = acaoSecundaria;

        setLayout(null);
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        initComponents(nomeUsuario, isCoordenador);
        initLayout();
        initListeners();
    }

    private void initComponents(String nome, boolean isCoordenador) {
        labelBemVindo = new JLabel("Olá, " + nome);
        labelBemVindo.setFont(new Font("Arial", Font.PLAIN, 14));
        labelBemVindo.setForeground(new Color(50, 50, 50));
        labelBemVindo.setVerticalAlignment(SwingConstants.CENTER);

        String textoBotao = isCoordenador ? "Alunos" : "Meu Perfil";
        String iconePath = isCoordenador ? "/images/group.png" : "/images/profile.png";

        btnAcaoSecundaria = criarBotaoComIcone(textoBotao, iconePath, new Color(30, 30, 30));

        btnSair = criarBotaoComIcone("Sair", "/images/logout.png", Color.BLACK);
    }

    private JLabel criarBotaoComIcone(String texto, String caminhoIcone, Color corTexto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 11));
        label.setForeground(corTexto);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon icon = carregarIcone(caminhoIcone);
        if (icon != null) {
            label.setIcon(icon);
            label.setVerticalTextPosition(SwingConstants.BOTTOM);
            label.setHorizontalTextPosition(SwingConstants.CENTER);
            label.setHorizontalAlignment(SwingConstants.CENTER);
        }

        return label;
    }

    private ImageIcon carregarIcone(String caminho) {
        try {
            URL imgUrl = getClass().getResource(caminho);
            if (imgUrl != null) {
                ImageIcon original = new ImageIcon(imgUrl);
                Image imgEscalada = original.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                return new ImageIcon(imgEscalada);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar ícone: " + caminho);
        }
        return null;
    }

    private void initListeners() {
        btnSair.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (acaoSair != null) acaoSair.run();
            }
        });

        btnAcaoSecundaria.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (acaoSecundaria != null) acaoSecundaria.run();
            }
        });
    }

    private void initLayout() {
        labelBemVindo.setBounds(20, 0, 400, 70);
        add(labelBemVindo);

        btnAcaoSecundaria.setBounds(450, 0, 70, 70);
        add(btnAcaoSecundaria);

        btnSair.setBounds(530, 0, 50, 70);
        add(btnSair);
    }
}