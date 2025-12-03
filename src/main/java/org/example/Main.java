package org.example;

import org.example.repository.CoordenaorRepository;
import org.example.view.screens.TelaCadastroCoordenador;
import org.example.view.screens.TelaLogin;

public class Main {
    public static void main(String[] args) {
        CoordenaorRepository coordenaorRepository = new CoordenaorRepository();

        if (!coordenaorRepository.verificarSeTemRegistro()) {
            TelaCadastroCoordenador telaCadastroCoordenador = new TelaCadastroCoordenador();
            telaCadastroCoordenador.setVisible(true);
        }
        else {
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setVisible(true);
        }
    }
}