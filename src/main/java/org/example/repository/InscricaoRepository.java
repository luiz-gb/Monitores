package org.example.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jakarta.persistence.EntityManager;
import org.bson.Document;
import org.example.exception.EntidadeNaoExisteException;
import org.example.mapper.EditalMapper;
import org.example.mapper.InscricaoMapper;
import org.example.model.Aluno;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;
import org.example.util.JPAUtil;
import org.example.util.MongoConnection;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InscricaoRepository {


    private EditalRepository editalRepository;
    private MongoDatabase db;

    //  Falta verificar se deu problema no service e telas
    public InscricaoRepository () {
        this.editalRepository = new EditalRepository();
        this.db = MongoConnection.getDatabase();
    }

    //  Falta verificar se deu problema no service e telas
    public void salvar (Inscricao inscricao) {
        MongoCollection<Document> editalCollection = db.getCollection("inscricoes");

        Document inscricaoDoc = InscricaoMapper.toDocument(inscricao);

        editalCollection.insertOne(inscricaoDoc);
    }

    //  Falta verificar se deu problema no service e telas
    public void atualizar (Inscricao inscricao) {
        MongoCollection<Document> editais = db.getCollection("inscricoes");

        if (inscricao.getId() == null) throw new EntidadeNaoExisteException("A inscrição não existe no sistema");

        Document inscricaoDoc = InscricaoMapper.toDocument(inscricao);

        editais.replaceOne(new Document("_id", inscricao.getId().toString()), inscricaoDoc);
    }

    //  Falta verificar se deu problema no service e telas
    public List<Inscricao> retornarTodasInscricoes () {
        MongoCollection<Document> inscricoesCollection = db.getCollection("inscricoes");

        List<Inscricao> inscricoes = inscricoesCollection.find().map(InscricaoMapper::toEntity)
                .into(new ArrayList<>());

        return inscricoes;
    }

    //  Falta verificar se deu problema no service e telas
    public int retornarQuantidadeInscricoesAluno (Aluno aluno, Edital edital) {
        MongoCollection<Document> inscricoes = db.getCollection("inscricoes");

        List<String> disciplinaIds = edital.getListaDisciplinas().stream()
                .map(d -> d.getId().toString())
                .toList();

        // vendo quantas inscricoes batem seu id da disciplina com o id da lista com um certo aluno
        long quantidade = inscricoes.countDocuments(Filters.and(
                Filters.eq("alunoId", aluno.getId().toString()),
                Filters.in("disciplinaId", disciplinaIds)
        ));

        return Integer.parseInt(String.valueOf(quantidade));
    }

    //  Falta verificar se deu problema no service e telas
    public Inscricao retornarAlunoInscritoDisciplina (Aluno aluno, Disciplina disciplina) {
        MongoCollection<Document> inscricaoCollection = db.getCollection("inscricoes");

        Document inscricaoDocument = inscricaoCollection.find(Filters.and(
                Filters.eq("alunoId", aluno.getId().toString()),
                Filters.eq("disciplinaId", disciplina.getId().toString())
        )).first();

        if (inscricaoDocument == null) return null;

        return InscricaoMapper.toEntity(inscricaoDocument);
    }

    //  Falta verificar se deu problema no service e telas
    public List<Inscricao> retornarInscricoesNaDisciplina (Disciplina disciplina) {
        MongoCollection<Document> inscricoesCollection = db.getCollection("inscricoes");

        List<Inscricao> inscricoes = inscricoesCollection.find(Filters.eq("disciplinaId",
                        disciplina.getId().toString()))
                .map(InscricaoMapper::toEntity)
                .into(new ArrayList<>());

        return inscricoes;
    }

    // falta passar pro mongo
    public List<Inscricao> retornarInscricoesDoEdital (Edital edital) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select i from Inscricao i where i.disciplina.edital = :edital", Inscricao.class)
                    .setParameter("edital", edital)
                    .getResultList();
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        finally {
            em.close();
        }
    }

    //  Falta passar pra mongodb
    public List<Inscricao> retornarInscricoesDoAluno (Aluno aluno) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select i from Inscricao i where i.aluno = :aluno", Inscricao.class)
                    .setParameter("aluno", aluno)
                    .getResultList();
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        finally {
            em.close();
        }
    }

}
