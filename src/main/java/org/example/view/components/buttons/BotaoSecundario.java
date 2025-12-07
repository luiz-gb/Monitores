package org.example.view.components.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotaoSecundario extends JButton {

    public BotaoSecundario(String texto) {
        super(texto);
        configurarEstilo();
    }

    private void configurarEstilo() {
        setBackground(new Color(220, 220, 220));
        setForeground(new Color(50, 50, 50));
        setFont(new Font("Arial", Font.BOLD, 14));

        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                setBackground(new Color(200, 200, 200));
            }
            public void mouseExited(MouseEvent evt) {
                setBackground(new Color(220, 220, 220));
            }
        });
    }
}