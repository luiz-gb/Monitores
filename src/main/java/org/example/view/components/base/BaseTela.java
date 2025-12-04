package org.example.view.components.base;

import javax.swing.*;

public abstract class BaseTela extends JFrame {


    public BaseTela(String titulo, int largura, int altura) {
        super(titulo);

        setSize(largura, altura);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(null);

        setResizable(false);

        setLocationRelativeTo(null);
    }

    public abstract void initComponents();
    public abstract void initLayout();
    public abstract void initListeners();

    public void initView () {
        initComponents();
        initLayout();
        initListeners();
        setVisible(true);
    }
}