package org.example.view.components.input;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputData extends JFormattedTextField {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public InputData() {
        super(criarMascara());
        configurarEstilo();
    }

    private static MaskFormatter criarMascara() {
        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter('_');
            return mask;
        } catch (Exception e) {
            return new MaskFormatter();
        }
    }

    private void configurarEstilo() {
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBackground(Color.WHITE);
        setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)
        ));
    }

    public LocalDate getData() throws DateTimeParseException {
        String texto = getText();
        if (texto.contains("_")) {
            throw new DateTimeParseException("Data incompleta", texto, 0);
        }
        return LocalDate.parse(texto, FORMATTER);
    }
}