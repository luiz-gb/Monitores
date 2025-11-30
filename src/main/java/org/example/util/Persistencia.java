package org.example.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;

public class Persistencia {

    private XStream xstream;
    private File arquivo = new File("central.xml");

    public Persistencia () {
        this.xstream = new XStream(new DomDriver());
        xstream.allowTypesByWildcard(new String[]{"org.example.**"}); //permitir que as classes o utilizem
    }

    public boolean salvarCentral (CentralDeInformacoes central) throws IOException {

        String xml = xstream.toXML(central);

        try{
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            PrintWriter gravar = new PrintWriter(arquivo);
            gravar.print(xml);
            gravar.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public CentralDeInformacoes recuperarCentral () throws FileNotFoundException {
        try {
            if (arquivo.exists()) {
                FileInputStream fis = new FileInputStream(arquivo);
                return (CentralDeInformacoes) xstream.fromXML(fis);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("O arquivo não existe!");
        }

        return new CentralDeInformacoes();
    }

    public void listarAlunos() throws FileNotFoundException {
        CentralDeInformacoes central = recuperarCentral();

        if (central.getTodosAlunos().toArray().length > 0) {
            System.out.println("Listagem de alunos:");
            for (int i = 0; i < central.getTodosAlunos().toArray().length; i++) {
                System.out.println((i + 1) + ". " + central.getTodosAlunos().get(i).getNome());
            }
        }
        else System.out.println("Não existem alunos cadastrados!");
    }
}
