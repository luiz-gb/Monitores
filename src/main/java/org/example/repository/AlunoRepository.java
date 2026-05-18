package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.interfaces.IAlunoRepository;
import org.example.model.Aluno;
import org.example.util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class AlunoRepository implements IAlunoRepository {

    @Override
    public void salvar(Aluno aluno) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(aluno);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void editar(Aluno aluno) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(aluno);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Aluno buscarPorEmail(String email) {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("select a from Aluno a where a.email = :email", Aluno.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Aluno buscarPorMatricula(String matricula) {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("select a from Aluno a where a.matricula = :matricula", Aluno.class)
                    .setParameter("matricula", matricula)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Aluno> retornarTodosAlunos() {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("select a from Aluno a", Aluno.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}