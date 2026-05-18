package org.example.interfaces;

import org.example.model.Edital;
import java.util.List;

public interface IEditalRepository {
    void salvar(Edital edital);
    void editar(Edital edital);
    List<Edital> retornarTodosEditais();
}