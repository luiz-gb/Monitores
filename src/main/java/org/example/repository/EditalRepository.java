package org.example.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jakarta.persistence.EntityManager;
import org.bson.Document;
import org.example.exception.EntidadeNaoExisteException;
import org.example.mapper.DisciplinaMapper;
import org.example.mapper.EditalMapper;
import org.example.mapper.InscricaoMapper;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;
import org.example.util.JPAUtil;
import org.example.util.MongoConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EditalRepository {

    private MongoDatabase db;

    public EditalRepository () {
        this.db = MongoConnection.getDatabase();
    }

    //  Falta verificar se deu problema no service e telas
    public void salvar (Edital edital) {
        MongoCollection<Document> editais = db.getCollection("editais");
        List<Document> disciplinasDoc = retornarListaDeDisciplinas(edital);

        if (edital.getId() == null) {
            edital.setId(UUID.randomUUID());
        }

        Document editalDoc = EditalMapper.toDocument(edital, disciplinasDoc);

        editais.insertOne(editalDoc);
    }

    //  Falta verificar se deu problema no service e telas
    public void editar (Edital edital) {
        MongoCollection<Document> editais = db.getCollection("editais");

        List<Document> disciplinasDoc = retornarListaDeDisciplinas(edital);

        if (edital.getId() == null) throw new EntidadeNaoExisteException("O edital não existe no sistema");

        Document editalDoc = EditalMapper.toDocument(edital, disciplinasDoc);

        editais.replaceOne(new Document("_id", edital.getId().toString()), editalDoc);
    }

    // falta passar pro mongo
    public List<Edital> retornarTodosEditais () {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select e from Edital e", Edital.class).getResultList();
        }

        catch (Exception e) {
            return new ArrayList<>();
        }

        finally {
            em.close();
        }
    }

    private List<Document> retornarListaDeDisciplinas (Edital edital) {
        List<Document> disciplinasDoc = new ArrayList<>();

        for (Disciplina disciplina: edital.getListaDisciplinas()) {
            if (disciplina.getId() == null) {
                disciplina.setId(UUID.randomUUID());
            }

            Document documentDisciplina = DisciplinaMapper.toDocument(disciplina);

            disciplinasDoc.add(documentDisciplina);
        }

        return disciplinasDoc;
    }
}
