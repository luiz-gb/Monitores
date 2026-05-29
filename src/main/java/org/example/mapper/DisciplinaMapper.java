package org.example.mapper;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.example.model.Disciplina;
import org.example.model.Inscricao;
import org.example.util.MongoConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DisciplinaMapper {
    public static Document toDocument (Disciplina disciplina) {
        Document documentDisciplina = new Document();

        documentDisciplina.append("_id", disciplina.getId().toString());
        documentDisciplina.append("nomeDisciplina", disciplina.getNomeDisciplina());
        documentDisciplina.append("vagasRemuneradas", disciplina.getVagasRemunerada());
        documentDisciplina.append("vagasVoluntarias", disciplina.getVagasVoluntarias());


        return documentDisciplina;
    }

    public static Disciplina toEntity (Document disciplinaDocument) {
        Disciplina disciplina = new Disciplina();

        disciplina.setId(UUID.fromString(disciplinaDocument.getString("_id")));
        disciplina.setNomeDisciplina(disciplinaDocument.getString("nomeDisciplina"));
        disciplina.setVagasRemunerada(disciplinaDocument.getInteger("vagasRemuneradas"));
        disciplina.setVagasVoluntarias(disciplinaDocument.getInteger("vagasVoluntarias"));

        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> inscricoesCollection = db.getCollection("incricoes");

        List<Document> inscricoesDocument = inscricoesCollection.find(Filters.and(
                Filters.eq("disciplinaId", disciplina.getId().toString())
        )).into(new ArrayList<>());

        if (inscricoesDocument.isEmpty()) {
            disciplina.setInscricoes(List.of());
        }
        else {
            disciplina.setInscricoes(inscricoesDocument.stream().map(InscricaoMapper::toEntity).toList());
        }

        return disciplina;
    }
}
