package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Aluno;
import org.example.util.JPAUtil;

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

//    public Aluno buscarPorMatricula (String email) {
//        EntityManager em = JPAUtil.getEntityManager();
//
//        try {
//            em.getTransaction().begin();
//            em.persist(aluno);
//            em.getTransaction().commit();
//        }
//    }
}
