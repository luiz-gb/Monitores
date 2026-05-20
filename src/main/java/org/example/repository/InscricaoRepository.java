package org.example.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import org.bson.Document;
import org.example.exception.EntidadeNaoExisteException;
import org.example.mapper.InscricaoMapper;
import org.example.model.Aluno;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;
import org.example.util.JPAUtil;
import org.example.util.MongoConnection;

import java.util.ArrayList;
import java.util.List;

public class InscricaoRepository {


    private EditalRepository editalRepository;
    private MongoDatabase db;

    public InscricaoRepository () {
        this.editalRepository = new EditalRepository();
        this.db = MongoConnection.getDatabase();
    }

//  Falta passar pra mongodb
//    public void salvar (Inscricao inscricao) {
//        Edital edital = editalRepository.retornarEditalPorDisplina(inscricao.getDisciplina().getId());
//
//        if (edital == null) throw new EntidadeNaoExisteException("Disciplina não encontrada para salvra edital.");
//
//        MongoCollection<Document> editalCollection = db.getCollection("editais");
//
//        Document inscricaoDoc = InscricaoMapper.toDocument(inscricao);
//    }

    //  Falta passar pra mongodb
    public void atualizar (Inscricao inscricao) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(inscricao);
            em.getTransaction().commit();
        }

        finally {
            em.close();
        }
    }

    //  Falta passar pra mongodb
    public List<Inscricao> retornarTodasInscricoes () {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select i from Inscricao i", Inscricao.class).getResultList();
        }

        catch (Exception e) {
            return new ArrayList<>();
        }

        finally {
            em.close();
        }
    }

    //  Falta passar pra mongodb
    public int retornarQuantidadeInscricoesAluno (Aluno aluno, Edital edital) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select count(i) from Inscricao i where i.aluno = :aluno and i.disciplina.edital = :edital", Long.class)
                    .setParameter("aluno", aluno)
                    .setParameter("edital", edital)
                    .getSingleResult()
                    .intValue();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        finally {
            em.close();
        }
    }

    // falta passar pro mongo
    public Inscricao retornarAlunoInscritoDisciplina (Aluno aluno, Disciplina disciplina) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select i from Inscricao i where i.aluno = :aluno and i.disciplina = :disciplina", Inscricao.class)
                    .setParameter("aluno", aluno)
                    .setParameter("disciplina", disciplina)
                    .getSingleResult();
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        finally {
            em.close();
        }
    }

    // falta passar pro mongo
    public List<Inscricao> retornarInscricoesNaDisciplina (Disciplina disciplina) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select i from Inscricao i where i.disciplina = :disciplina", Inscricao.class)
                    .setParameter("disciplina", disciplina)
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
