package org.example.view.components.tables;

import org.example.enums.StatusEdital;
import org.example.model.Edital;
import org.example.validator.EditalValidator;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ModeloTabelaEdital extends AbstractTableModel {

    private final String[] colunas = {
            "Id", "Data Início", "Data Fim", "Status", "Detalhar"
    };

    private List<Edital> editais;

    public ModeloTabelaEdital(List<Edital> editais) {
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

        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return switch (columnIndex) {
            case 0 -> edital.getId();
            case 1 -> edital.getDataInicio().format(formatadorData);
            case 2 -> edital.getDataFinal().format(formatadorData); // edital.getDataFinal().isBefore(java.time.LocalDate.now())
            case 3 -> String.valueOf(calcularStatus(edital)).toLowerCase();
            case 4 -> "Clique aqui";

            default -> null;
        };
    }

    private StatusEdital calcularStatus (Edital edital) {
        if (edital.getStatus() == StatusEdital.ABERTO && !EditalValidator.validarDentroPeriodoInscricoes(edital.getDataInicio(), edital.getDataFinal())) {
            return StatusEdital.ENCERRADO;
        }

        return edital.getStatus();
    }
}
