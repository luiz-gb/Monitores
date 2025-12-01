package org.example.view;

import org.example.model.Aluno;
import org.example.model.Coordenador;

import javax.swing.*;

public class TelaCadastroCoordenador  extends BaseTela{
    private JTextField campoEmail;

    public TelaCadastroCoordenador () {
        super("Cadastro coordenador", 400, 500);
    }

    @Override
    public void initComponents () {
        campoEmail = new JTextField();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initLayout() {
        
    }
}
