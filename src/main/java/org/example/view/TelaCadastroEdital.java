package org.example.view;

import org.example.domain.DisciplinaTemp;
import org.example.exception.CampoVazioException;
import org.example.exception.ListaVaziaException;
import org.example.exception.NumeroInvalidoException;
import org.example.exception.SomaPesosException;
import org.example.model.Disciplina;
import org.example.repository.EditalRepository;
import org.example.service.CadastroService;
import org.example.validator.DisciplinaValidator;
import org.example.validator.EditalValidator;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TelaCadastroEdital extends BaseTela{
    private JFormattedTextField campoDataInicio;
    private JFormattedTextField campoDataFinal;
    private JTextField campoMaximoInscricoes;
    private JTextField campoPesoCre;
    private JTextField campoPesoMedia;
    private JTextField campoNomeDisciplina;
    private JTextField campoVagasRemuneradas;
    private JTextField campoVagasVoluntarias;
    private JLabel adicionarDisciplina;
    private List<Disciplina> listaDisciplinas;
    private CadastroService cadastroService;
    private JButton btnCasdastrar;

    public TelaCadastroEdital () {
        super("Cadastro edital", 400, 500);
        setVisible(true);
        cadastroService = new CadastroService();
        listaDisciplinas = new ArrayList<>();
    }

    @Override
    public void initComponents () {

        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');

            campoDataInicio = new JFormattedTextField(mascaraData);
            campoDataFinal = new JFormattedTextField(mascaraData);
        }

        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao passar formatação de data!");
        }

        campoMaximoInscricoes = new JTextField();
        campoPesoCre = new JTextField();
        campoPesoMedia = new JTextField();
        btnCasdastrar = new JButton("Cadastrar");

        campoNomeDisciplina = new JTextField();
        campoVagasVoluntarias = new JTextField();
        campoVagasRemuneradas = new JTextField();

    }

    @Override
    public void initListeners() {
        btnCasdastrar.addActionListener(e -> {
            String maximoInscricoes = campoMaximoInscricoes.getText();
            String pesoCre = campoPesoCre.getText();
            String pesoMedia = campoPesoMedia.getText();

            try {
                LocalDate dataInicio = LocalDate.parse(campoDataInicio.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate dataFinal = LocalDate.parse(campoDataInicio.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                EditalValidator.validarMaxInscricoes(maximoInscricoes);
                EditalValidator.validarPeso(pesoCre);
                EditalValidator.validarPeso(pesoMedia);
                EditalValidator.validarPesos(Float.parseFloat(pesoCre), Float.parseFloat(pesoMedia));

                cadastroService.cadastrarEdital(dataInicio, dataFinal, Integer.parseInt(maximoInscricoes), Double.parseDouble(pesoCre), Double.parseDouble(pesoMedia), listaDisciplinas);
            }

            catch (NumeroInvalidoException | CampoVazioException | ListaVaziaException | SomaPesosException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage());
            }

            catch (DateTimeParseException exceptionDate) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido!");
            }

        });

        adicionarDisciplina.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String nomeDisciplina = campoNomeDisciplina.getText();
                String vagasRemuneradas = campoVagasRemuneradas.getText();
                String vagasVoluntarias = campoVagasVoluntarias.getText();

                try {
                    DisciplinaValidator.validarNome(nomeDisciplina);
                    DisciplinaValidator.validarQuantidadeVagas(vagasRemuneradas);
                    DisciplinaValidator.validarQuantidadeVagas(vagasVoluntarias);

                    Disciplina disciplina = new Disciplina();

                    disciplina.setNomeDisciplina(nomeDisciplina);
                    disciplina.setVagasRemunerada(Integer.parseInt(vagasRemuneradas));
                    disciplina.setVagasVoluntarias(Integer.parseInt(vagasVoluntarias));

                    listaDisciplinas.add(disciplina);

                    campoNomeDisciplina.setText("");
                    campoVagasRemuneradas.setText("");
                    campoVagasVoluntarias.setText("");
                }

                catch (CampoVazioException | NumeroInvalidoException exception) {
                    JOptionPane.showMessageDialog(TelaCadastroEdital.this, exception.getMessage());
                }
            }
        });
    }

    @Override
    public void initLayout() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        painel.add(new JLabel("Data Início:"));
        painel.add(campoDataInicio);

        painel.add(new JLabel("Data Final:"));
        painel.add(campoDataFinal);

        painel.add(new JLabel("Máximo de Inscrições por Disciplina:"));
        painel.add(campoMaximoInscricoes);

        painel.add(new JLabel("Peso do CRE:"));
        painel.add(campoPesoCre);

        painel.add(new JLabel("Peso da Média:"));
        painel.add(campoPesoMedia);

        painel.add(new JLabel("Nome da Disciplina:"));
        painel.add(campoNomeDisciplina);

        painel.add(new JLabel("Vagas Remuneradas:"));
        painel.add(campoVagasRemuneradas);

        painel.add(new JLabel("Vagas Voluntárias:"));
        painel.add(campoVagasVoluntarias);

        painel.add(Box.createVerticalStrut(15));

        adicionarDisciplina = new JLabel("Adicionar Disciplina");
        painel.add(adicionarDisciplina);

        painel.add(btnCasdastrar);

        add(painel);
    }
}
