package org.example.view.components;


import org.example.model.Edital;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EditalTableModel extends AbstractTableModel {

    private final String[] colunas = {
            "Id", "Data Início", "Data Fim", "Status", "Detalhar"
    };

    private List<Edital> editais;

    public EditalTableModel(List<Edital> editais) {
        this.editais = editais;
    }

    @Override
    public int getRowCount() {
        return editais.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Edital edital = editais.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> edital.getId();
            case 1 -> edital.getDataInicio();
            case 2 -> edital.getDataFinal();
            case 3 -> edital.getDataFinal().isBefore(java.time.LocalDate.now())
                    ? "Encerrado"
                    : "Aberto";
            case 4 -> "Clique aqui";

            default -> null;
        };
    }
}
