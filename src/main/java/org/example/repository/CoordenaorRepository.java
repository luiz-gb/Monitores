package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Coordenador;
import org.example.util.JPAUtil;

public class CoordenaorRepository {
    public void salvar (Coordenador coordeandor) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(coordeandor);
            em.getTransaction().commit();
        }

        finally {
            em.close();
        }
    }

    public Coordenador buscarPorEmail (String email) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select a from Coordenador a where a.email = :email", Coordenador.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean verificarSeTemRegistro () {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.createQuery("select a from Coordenador a").getSingleResult();
            return true;
        }

        catch (Exception e) {
            return false;
        }

        finally {
            em.close();
        }
    }
}
