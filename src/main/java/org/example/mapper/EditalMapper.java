package org.example.mapper;

import org.bson.Document;
import org.example.enums.StatusEdital;
import org.example.model.Disciplina;
import org.example.model.Edital;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EditalMapper {

    public static Document toDocument (Edital edital, List<Document> disciplinasDoc) {
        Document editalDoc = new Document();

        editalDoc.append("id", edital.getId().toString());
        editalDoc.append("dataInicio", edital.getDataInicio());
        editalDoc.append("dataFinal", edital.getDataFinal());
        editalDoc.append("maximoInscricoesPorAluno", edital.getMaximoInscricoesPorAluno());
        editalDoc.append("pesoCre", edital.getPesoCre());
        editalDoc.append("pesoMedia", edital.getPesoMedia());
        editalDoc.append("status", edital.getStatus());
        editalDoc.append("listaDisciplinas", disciplinasDoc);

        return editalDoc;
    }

    public static Edital toEntity(Document editalDocument) {
        Edital edital = new Edital();

        edital.setId(UUID.fromString(editalDocument.getString("id")));
        edital.setDataInicio(editalDocument.getDate("dataInicio"));
        edital.setDataFinal(editalDocument.getDate("dataFinal"));
        edital.setMaximoInscricoesPorAluno(editalDocument.getInteger("maximoInscricoesPorAluno"));
        edital.setPesoCre(editalDocument.getDouble("pesoCre"));
        edital.setPesoMedia(editalDocument.getDouble("pesoMedia"));
        edital.setStatus(StatusEdital.valueOf(editalDocument.getString("status")));
        List<Document> disciplinasDoc = editalDocument.getList("listaDisciplinas", Document.class);

        List<Disciplina> disciplinas = new ArrayList<>();

        for (Document disciplinaDoc : disciplinasDoc) {
            disciplinas.add(
                    DisciplinaMapper.toEntity(disciplinaDoc)
            );
        }

        edital.setListaDisciplinas(disciplinas);

        return edital;
    }
}
