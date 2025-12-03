package org.example.view.components.text;

import javax.swing.*;
import java.awt.*;

public class LabelTituloSecao extends JPanel {

    public LabelTituloSecao(String texto) {
        setLayout(null);
        setOpaque(false);

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(200, 200, 200));
        separator.setBounds(0, 0, 440, 1);
        add(separator);

        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(30, 30, 30));
        label.setBounds(0, 10, 300, 20);
        add(label);
    }
}