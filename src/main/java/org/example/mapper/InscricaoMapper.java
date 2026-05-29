package org.example.mapper;

import org.bson.Document;
import org.example.enums.ResultadoInscricao;
import org.example.model.Inscricao;

import java.util.List;
import java.util.UUID;

public class InscricaoMapper {

    public static Document toDocument (Inscricao inscricao) {
        Document documentDisciplina = new Document();

        documentDisciplina.append("_id", inscricao.getId().toString());
        documentDisciplina.append("alunoId", inscricao.getAluno().getId().toString());
        documentDisciplina.append("disciplinaId", inscricao.getDisciplina().getId().toString());
        documentDisciplina.append("dataInscricao", inscricao.getDataInscricao());
        documentDisciplina.append("resultadoInscricao", inscricao.getResultadoInscricao().toString());
        documentDisciplina.append("alunoCRE", inscricao.getAlunoCRE());
        documentDisciplina.append("alunoMedia", inscricao.getAlunoMedia());

        return documentDisciplina;
    }

    public static Inscricao toEntity(Document inscricaoDocument) {
        Inscricao inscricao = new Inscricao();

        inscricao.setId(UUID.fromString(inscricaoDocument.getString("_id")));
        inscricao.setAlunoId(UUID.fromString(inscricaoDocument.getString("alunoId")));
        inscricao.setDisciplinaId(UUID.fromString(inscricaoDocument.getString("disciplinaId")));
        inscricao.setDataInscricao(inscricaoDocument.getDate("dataInscricao"));
        inscricao.setResultadoInscricao(ResultadoInscricao.valueOf(inscricaoDocument.getString("resultadoInscricao")));
        inscricao.setAlunoCRE(inscricaoDocument.getDouble("alunoCRE"));
        inscricao.setAlunoMedia(inscricaoDocument.getDouble("alunoMedia"));

        return inscricao;
    }
}
