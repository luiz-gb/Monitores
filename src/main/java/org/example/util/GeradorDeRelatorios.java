package org.example.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.domain.Aluno;
import org.example.model.Disciplina;
import org.example.model.Inscricao;
import org.example.service.InscricaoService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeradorDeRelatorios {
    public static void gerarPdfEdital (List<Inscricao> listaInscricoes) throws FileNotFoundException {
        Document documento = new Document(PageSize.A4);

        StringBuilder textoInscricoes = new StringBuilder();
        InscricaoService inscricaoService = new InscricaoService();
        List<Disciplina> listaDisciplinasDoEdital = new ArrayList<>();

        System.out.println(Arrays.toString(listaDisciplinasDoEdital.toArray()));

        listaInscricoes.forEach(e -> {
            Disciplina disciplina = e.getDisciplina();

            boolean possuiDisciplina = false;

            for (Disciplina disciplinaAtual : listaDisciplinasDoEdital) {
                if (disciplinaAtual.getId().equals(disciplina.getId())) possuiDisciplina = true;
            }

            if (!possuiDisciplina) listaDisciplinasDoEdital.add(disciplina);

        });

        try {
            OutputStream os = new FileOutputStream("relatório.pdf");
            PdfWriter.getInstance(documento, os);
            documento.open();
            Paragraph titulo = new Paragraph("RELATÓRIO DE INSCRIÇÕES\n\n");
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            for (Disciplina disciplina : listaDisciplinasDoEdital) {
                System.out.println("opaaa");
                Paragraph nomeDisciplina = new Paragraph(disciplina.getNomeDisciplina(), new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
                nomeDisciplina.setSpacingBefore(10);
                documento.add(nomeDisciplina);

                PdfPTable tabela = new PdfPTable(6);
                tabela.setWidthPercentage(100);
                tabela.setSpacingBefore(5);
                tabela.setSpacingAfter(10);

                tabela.addCell("Pos.");
                tabela.addCell("Aluno");
                tabela.addCell("CRE");
                tabela.addCell("Média");
                tabela.addCell("Pontuação");
                tabela.addCell("Status");

                List<Inscricao> inscricoesDisciplina = inscricaoService.processarResultadoDaDisciplina(disciplina);

                inscricoesDisciplina.forEach(e -> {

                    double pontuacao = CalcularPontuacao.calcularPontuacaoAluno(
                            e.getDisciplina().getEdital().getPesoCre(),
                            e.getDisciplina().getEdital().getPesoMedia(),
                            e.getAlunoCRE(),
                            e.getAlunoMedia()
                    );

                    tabela.addCell(inscricoesDisciplina.indexOf(e) + 1 + "º");
                    tabela.addCell(e.getAluno().getNome());
                    tabela.addCell(String.valueOf(e.getAlunoCRE()));
                    tabela.addCell(String.valueOf(e.getAlunoMedia()));
                    tabela.addCell(String.valueOf(pontuacao));
                    tabela.addCell(String.valueOf(e.getResultadoInscricao()).toLowerCase());
                });

                documento.add(tabela);
            }

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
