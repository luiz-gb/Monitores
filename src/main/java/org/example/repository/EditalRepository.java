package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Edital;
import org.example.util.JPAUtil;

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
}
