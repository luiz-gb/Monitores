package org.example.view.components.links;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LinkTexto extends JLabel {

    public LinkTexto(String texto) {
        super(texto);
        configurarEstilo();
    }

    public LinkTexto(String texto, int alinhamento) {
        super(texto);
        configurarEstilo();
        setHorizontalAlignment(alinhamento);
    }

    private void configurarEstilo() {
        setForeground(new Color(0, 102, 204));
        setFont(new Font("Arial", Font.BOLD, 12));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                setForeground(new Color(0, 50, 150));
            }
            public void mouseExited(MouseEvent evt) {
                setForeground(new Color(0, 102, 204));
            }
        });
    }
}