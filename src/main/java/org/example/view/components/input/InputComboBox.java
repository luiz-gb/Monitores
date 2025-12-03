package org.example.view.components.input;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class InputComboBox<E> extends JComboBox<E> {

    public InputComboBox() {
        super();
        configurarEstilo();
    }

    public InputComboBox(E[] items) {
        super(items);
        configurarEstilo();
    }

    private void configurarEstilo() {
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBackground(Color.WHITE);
        setFocusable(false);

        setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)
        ));

        ((JComponent) getRenderer()).setOpaque(true);
    }
}