package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Edital;
import org.example.util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class EditalRepository {
    public void salvar (Edital edital) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(edital);
            em.getTransaction().commit();
        }

        finally {
            em.close();
        }
    }

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
}
