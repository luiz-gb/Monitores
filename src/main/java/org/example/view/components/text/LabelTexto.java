package org.example.view.components.text;

import javax.swing.*;
import java.awt.*;

public class LabelTexto extends JLabel {
    public LabelTexto(String texto) {
        super(texto);
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(new Color(50, 50, 50));
    }
}