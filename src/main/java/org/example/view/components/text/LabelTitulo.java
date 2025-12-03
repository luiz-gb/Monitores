package org.example.view.components.text;

import javax.swing.*;
import java.awt.*;

public class LabelTitulo extends JLabel {

    public LabelTitulo(String texto) {
        super(texto);
        configurarEstilo();
    }

    private void configurarEstilo() {
        setFont(new Font("Arial", Font.BOLD, 22));
        setForeground(new Color(30, 30, 30));
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}