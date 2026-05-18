package org.example.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.model.Disciplina;
import org.example.model.Inscricao;
import org.example.service.InscricaoService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class GeradorDeRelatorios {
    private final InscricaoService inscricaoService;

    public GeradorDeRelatorios(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    public void gerarPdfEdital(List<Inscricao> listaInscricoes) throws FileNotFoundException {
        Document documento = new Document(PageSize.A4);

        List<Disciplina> listaDisciplinasDoEdital = listaInscricoes.stream()
                .map(Inscricao::getDisciplina)
                .distinct()
                .collect(Collectors.toList());

        try (OutputStream os = new FileOutputStream("relatório.pdf")) {
            PdfWriter.getInstance(documento, os);
            documento.open();

            Paragraph titulo = new Paragraph("RELATÓRIO DE INSCRIÇÕES\n\n");
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            for (Disciplina disciplina : listaDisciplinasDoEdital) {
                Paragraph nomeDisciplina = new Paragraph(disciplina.getNomeDisciplina(),
                        new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
                nomeDisciplina.setSpacingBefore(10);
                documento.add(nomeDisciplina);

                // Criando a tabela iText
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

                // Chamando o service
                List<Inscricao> inscricoesDisciplina = inscricaoService.processarResultadoDaDisciplina(disciplina);

                for (Inscricao e : inscricoesDisciplina) {
                    double pontuacao = CalcularPontuacao.calcularPontuacaoAluno(
                            e.getDisciplina().getEdital().getPesoCre(),
                            e.getDisciplina().getEdital().getPesoMedia(),
                            e.getAlunoCRE(),
                            e.getAlunoMedia()
                    );

                    tabela.addCell((inscricoesDisciplina.indexOf(e) + 1) + "º");
                    tabela.addCell(e.getAluno().getNome());
                    tabela.addCell(String.valueOf(e.getAlunoCRE()));
                    tabela.addCell(String.valueOf(e.getAlunoMedia()));
                    tabela.addCell(String.valueOf(pontuacao));
                    tabela.addCell(String.valueOf(e.getResultadoInscricao()).toLowerCase());
                }

                documento.add(tabela);
            }

            documento.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Arquivo não encontrado!");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o relatório PDF: " + e.getMessage(), e);
        }
    }
}