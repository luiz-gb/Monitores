package org.example.mapper;

import org.bson.Document;
import org.example.model.Disciplina;
import org.example.model.Inscricao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DisciplinaMapper {
    public static Document toDocument (Disciplina disciplina, List<Document> inscricoes) {
        Document documentDisciplina = new Document();

        documentDisciplina.append("id", disciplina.getId().toString());
        documentDisciplina.append("nomeDisciplina", disciplina.getNomeDisciplina());
        documentDisciplina.append("vagasRemuneradas", disciplina.getVagasRemunerada());
        documentDisciplina.append("vagasVoluntarias", disciplina.getVagasVoluntarias());
        documentDisciplina.append("inscricoes", inscricoes);


        return documentDisciplina;
    }

    public static Disciplina toEntity (Document disciplinaDocument) {
        Disciplina disciplina = new Disciplina();

        disciplina.setId(UUID.fromString(disciplinaDocument.getString("id")));
        disciplina.setNomeDisciplina(disciplinaDocument.getString("nomeDisciplina"));
        disciplina.setVagasRemunerada(disciplinaDocument.getInteger("vagasRemuneradas"));
        disciplina.setVagasVoluntarias(disciplinaDocument.getInteger("vagasVoluntarias"));

        List<Document> inscricoesDocument = disciplinaDocument.getList("inscricoes", Document.class);

        List<Inscricao> listaInscricoes = new ArrayList<>();

        for (Document inscricaoDocument : inscricoesDocument) {
            Inscricao inscricao = InscricaoMapper.toEntity(inscricaoDocument);
            listaInscricoes.add(inscricao);
        }

        disciplina.setInscricoes(listaInscricoes);

        return disciplina;
    }
}
