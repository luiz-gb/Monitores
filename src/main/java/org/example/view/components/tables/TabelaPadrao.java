package org.example.view.components.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TabelaPadrao extends JTable {

    public TabelaPadrao(javax.swing.table.TableModel model) {
        super(model);
        configurarEstilo();
    }

    private void configurarEstilo() {
        setShowVerticalLines(false);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(40);
        setIntercellSpacing(new Dimension(0, 0));
        setFont(new Font("Arial", Font.PLAIN, 12));

        setSelectionBackground(new Color(232, 242, 254));
        setSelectionForeground(Color.BLACK);

        setFocusable(false);

        getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        getTableHeader().setBackground(new Color(248, 249, 250)); // Cinza bem clarinho
        getTableHeader().setForeground(new Color(50, 50, 50));
        getTableHeader().setReorderingAllowed(false); // Trava colunas

        getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setBorder(noFocusBorder); // Remove borda pontilhada de foco
                return this;
            }
        });
    }

    public void transformarColunaEmLink(int indiceColuna, Color corLink) {
        getColumnModel().getColumn(indiceColuna).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);

                setText((value != null) ? value.toString() : "");
                setForeground(corLink);
                setFont(new Font("Arial", Font.BOLD, 11));
                setCursor(new Cursor(Cursor.HAND_CURSOR));

                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                } else {
                    setBackground(Color.WHITE);
                }
                return this;
            }
        });
    }
}