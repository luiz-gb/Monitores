package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.interfaces.IEditalRepository;
import org.example.model.Edital;
import org.example.util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class EditalRepository implements IEditalRepository {

    @Override
    public void salvar (Edital edital) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(edital);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void editar (Edital edital) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(edital);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Edital> retornarTodosEditais () {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("select e from Edital e", Edital.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}