package org.example.view.components.input;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class InputData extends JFormattedTextField {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

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

    public Date getData() throws ParseException {
        String texto = getText();

        if (texto.contains("_")) {
            throw new ParseException("Data incompleta", 0);
        }
        return FORMATTER.parse(texto);
    }
}