package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.view.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

//        System.out.println("Iniciando JPA...");

//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("monitoriaPU");

        SwingUtilities.invokeLater(Login::new);
        Login login = new Login();
        login.setVisible(true);

//        emf.close();

        System.out.println("Finalizado.");
    }
}