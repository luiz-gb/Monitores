package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Aluno;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;
import org.example.util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class InscricaoRepository {
    public void salvar (Inscricao inscricao) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(inscricao);
            em.getTransaction().commit();
        }

        finally {
            em.close();
        }
    }

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
}
