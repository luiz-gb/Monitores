package org.example.mapper;

import org.bson.Document;
import org.example.model.Edital;

import java.util.List;

public class EditalMapper {

    public static Document toDocument (Edital edital, List<Document> disciplinasDoc) {
        Document editalDoc = new Document();

        editalDoc.append("id", edital.getId());
        editalDoc.append("dataInicio", edital.getDataInicio());
        editalDoc.append("dataFinal", edital.getDataFinal());
        editalDoc.append("maximoInscricoesPorAluno", edital.getMaximoInscricoesPorAluno());
        editalDoc.append("pesoCre", edital.getPesoCre());
        editalDoc.append("pesoMedia", edital.getPesoMedia());
        editalDoc.append("status", edital.getStatus());
        editalDoc.append("listaDisciplinas", disciplinasDoc);

        return editalDoc;
    }
}
