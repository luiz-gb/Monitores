package org.example.view.components.base;

import javax.swing.*;

public abstract class BaseTela extends JFrame {

    public BaseTela(String titulo, int largura, int altura) {
        super(titulo);

        setSize(largura, altura);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(null);

        setResizable(false);

        initComponents();
        initLayout();
        initListeners();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public abstract void initComponents();
    public abstract void initLayout();
    public abstract void initListeners();
}