package org.example.mapper;

import org.bson.Document;
import org.example.model.Disciplina;

public class DisciplinaMapper {
    public static Document toDocument (Disciplina disciplina) {
        Document documentDisciplina = new Document();

        documentDisciplina.append("id", disciplina.getId());
        documentDisciplina.append("nomeDisciplina", disciplina.getNomeDisciplina());
        documentDisciplina.append("vagasRemuneradas", disciplina.getVagasRemunerada());
        documentDisciplina.append("vagasVoluntarias", disciplina.getVagasVoluntarias());

        return documentDisciplina;
    }
}
