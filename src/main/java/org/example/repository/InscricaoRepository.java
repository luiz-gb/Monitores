package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.interfaces.IInscricaoRepository;
import org.example.model.Aluno;
import org.example.model.Disciplina;
import org.example.model.Edital;
import org.example.model.Inscricao;
import org.example.util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class InscricaoRepository implements IInscricaoRepository {

    @Override
    public void salvar(Inscricao inscricao) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(inscricao);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void atualizar(Inscricao inscricao) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(inscricao);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Inscricao> retornarTodasInscricoes() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("select i from Inscricao i", Inscricao.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int retornarQuantidadeInscricoesAluno(Aluno aluno, Edital edital) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("select count(i) from Inscricao i where i.aluno = :aluno and i.disciplina.edital = :edital", Long.class)
                    .setParameter("aluno", aluno)
                    .setParameter("edital", edital)
                    .getSingleResult()
                    .intValue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    @Override
    public Inscricao retornarAlunoInscritoDisciplina(Aluno aluno, Disciplina disciplina) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("select i from Inscricao i where i.aluno = :aluno and i.disciplina = :disciplina", Inscricao.class)
                    .setParameter("aluno", aluno)
                    .setParameter("disciplina", disciplina)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Inscricao> retornarInscricoesNaDisciplina(Disciplina disciplina) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("select i from Inscricao i where i.disciplina = :disciplina", Inscricao.class)
                    .setParameter("disciplina", disciplina)
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Inscricao> retornarInscricoesDoEdital(Edital edital) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("select i from Inscricao i where i.disciplina.edital = :edital", Inscricao.class)
                    .setParameter("edital", edital)
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Inscricao> retornarInscricoesDoAluno(Aluno aluno) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("select i from Inscricao i where i.aluno = :aluno", Inscricao.class)
                    .setParameter("aluno", aluno)
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}