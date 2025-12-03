package org.example.view.components.input;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class InputSenha extends JPasswordField {
    public InputSenha() {
        super();
        configurarEstilo();
    }

    private void configurarEstilo() {
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBackground(Color.WHITE);
        setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)
        ));
    }
}