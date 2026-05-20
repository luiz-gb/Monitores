package org.example.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import org.bson.Document;
import org.example.exception.EntidadeNaoExisteException;
import org.example.mapper.DisciplinaMapper;
import org.example.mapper.EditalMapper;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.util.JPAUtil;
import org.example.util.MongoConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EditalRepository {

    public void salvar (Edital edital) {
        MongoDatabase db = MongoConnection.getDatabase();

        MongoCollection<Document> editais = db.getCollection("editais");
        List<Document> disciplinasDoc = retornarListaDeDisciplinas(edital);

        if (edital.getId() == null) {
            edital.setId(UUID.randomUUID());
        }

        Document editalDoc = EditalMapper.toDocument(edital, disciplinasDoc);

        editais.insertOne(editalDoc);
    }

    public void editar (Edital edital) {
        MongoDatabase db = MongoConnection.getDatabase();

        MongoCollection<Document> editais = db.getCollection("editais");

        List<Document> disciplinasDoc = retornarListaDeDisciplinas(edital);

        if (edital.getId() == null) throw new EntidadeNaoExisteException("O edital não existe no sistema");

        Document editalDoc = EditalMapper.toDocument(edital, disciplinasDoc);

        editais.replaceOne(new Document("id", edital.getId()), editalDoc);
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
