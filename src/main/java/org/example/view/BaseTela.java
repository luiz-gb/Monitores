package org.example.view;

import javax.swing.*;
import java.awt.*;

public abstract class BaseTela extends JFrame {
    public BaseTela (String titulo, int largura, int altura) {
        super(titulo);

        setSize(largura, altura);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponents();
        initLayout();
        initListeners();
    }

    public abstract void initComponents ();
    public abstract void initListeners ();
    public abstract void initLayout ();
}
