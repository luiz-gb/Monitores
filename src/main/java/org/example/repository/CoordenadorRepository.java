package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.interfaces.ICoordenadorRepository;
import org.example.model.Coordenador;
import org.example.util.JPAUtil;

public class CoordenadorRepository implements ICoordenadorRepository {

    @Override
    public void salvar (Coordenador coordenador) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(coordenador);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Coordenador buscarPorEmail (String email) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("select a from Coordenador a where a.email = :email", Coordenador.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean verificarSeTemRegistro () {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.createQuery("select a from Coordenador a").setMaxResults(1).getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}