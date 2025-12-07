package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Aluno;
import org.example.model.Edital;
import org.example.util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class AlunoRepository {

    public void salvar (Aluno aluno) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(aluno);
            em.getTransaction().commit();
        }

        finally {
            em.close();
        }
    }

    public void editar(Aluno aluno) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(aluno);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;

        } finally {
            em.close();
        }
    }

    public Aluno buscarPorEmail (String email) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select a from Aluno a where a.email = :email", Aluno.class)
                    .setParameter("email", email)
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

    public Aluno buscarPorMatricula (String matricula) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select a from Aluno a where a.matricula = :matricula", Aluno.class)
                    .setParameter("matricula", matricula)
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

    public List<Aluno> retornarTodosAlunos () {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select a from Aluno a", Aluno.class).getResultList();
        }

        catch (Exception e) {
            return new ArrayList<>();
        }

        finally {
            em.close();
        }
    }
}
