package org.example.util;

public class CalcularPontuacao {
    public static Double calcularPontuacaoAluno (double pesoCre, double pesoMedia, double creAluno, double mediaAluno) {
        return (pesoCre * creAluno) + (pesoMedia * mediaAluno);
    }
}
