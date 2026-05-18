package org.example;

import org.example.interfaces.ICoordenadorRepository;
import org.example.repository.CoordenadorRepository;
import org.example.view.screens.TelaCadastroCoordenador;
import org.example.view.screens.TelaLogin;

public class Main {
    public static void main(String[] args) {
        ICoordenadorRepository coordenadorRepository = new CoordenadorRepository();

        if (!coordenadorRepository.verificarSeTemRegistro()) {
            TelaCadastroCoordenador telaCadastroCoordenador = new TelaCadastroCoordenador();
            telaCadastroCoordenador.setVisible(true);
        }
        else {
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setVisible(true);
        }
    }
}