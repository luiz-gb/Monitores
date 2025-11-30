package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        System.out.println("Iniciando JPA...");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("monitoriaPU");

        emf.close();

        System.out.println("Finalizado.");
    }
}