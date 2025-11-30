package org.example.domain;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.util.CentralDeInformacoes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class GeradorDeRelatorios {
    public static void obterComprovanteDeInscricoesAluno (String matriculaAluno, long idEdital, CentralDeInformacoes central) throws FileNotFoundException {
        Document documento = new Document(PageSize.A4);

        List<Disciplina> listaDisciplinas = central.recuperarInscricoesDeUmAlunoEmUmEdital(matriculaAluno, idEdital);
        Aluno aluno = central.recuperarAlunoPorMatricula(matriculaAluno);
        StringBuilder textoInscricoes = new StringBuilder();

        if (!listaDisciplinas.isEmpty()) {
            for (Disciplina disciplina : listaDisciplinas) {
                textoInscricoes.append(disciplina.getNome()).append("\n");
            }
        }

        else textoInscricoes.append("Nenhuma inscrição encontrada!");

        try {
            OutputStream os = new FileOutputStream("relatório.pdf");
            PdfWriter.getInstance(documento, os);
            documento.open();
            Paragraph titulo = new Paragraph("RELATÓRIO DE INSCRIÇÕES\n" + aluno.getNome().toUpperCase() + "\n#" + aluno.getMatricula() + "\n\n");
            titulo.setAlignment(Element.ALIGN_CENTER);
            Paragraph paragrafo = new Paragraph(String.valueOf(textoInscricoes));
            paragrafo.setAlignment(Element.ALIGN_CENTER);

            documento.add(titulo);
            documento.add(paragrafo);
            documento.close();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Arquivo não encontrado!");
        }
        catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
