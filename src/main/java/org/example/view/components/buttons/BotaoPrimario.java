package org.example.view.components.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotaoPrimario extends JButton {
    public BotaoPrimario(String texto) {
        super(texto);
        setBackground(new Color(50, 50, 50));
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 14));
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { setBackground(new Color(70, 70, 70)); }
            public void mouseExited(MouseEvent evt) { setBackground(new Color(50, 50, 50)); }
        });
    }
}