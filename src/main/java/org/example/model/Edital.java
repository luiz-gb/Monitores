package org.example.model;

import jakarta.persistence.*;
import org.example.enums.StatusEdital;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Representa um Edital de monitoria, contendo informações essenciais como datas,
 * pesos de avaliação, disciplinas vinculadas e status do processo.
 *
 * <p>Esta classe é mapeada para a tabela <strong>edital</strong> no banco de dados,
 * utilizando anotações JPA para persistência. Cada edital pode possuir diversas
 * disciplinas associadas.</p>
 *
 * <p>A documentação desta classe foi escrita manualmente e o HTML correspondente
 * deve ser gerado utilizando a ferramenta <strong>Javadoc</strong>, conforme solicitado
 * no relatório final.</p>
 */
@Entity
@Table(name = "edital")
public class Edital {

    /**
     * Identificador único do edital.
     * Gerado automaticamente utilizando UUID.
     */
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Data de início do período de vigência do edital.
     */
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    /**
     * Data de término do período de vigência do edital.
     */
    @Column(name = "data_final", nullable = false)
    private LocalDate dataFinal;

    /**
     * Número máximo de inscrições permitidas para cada aluno.
     */
    @Column(name = "maximo_inscricoes_aluno", nullable = false)
    private int maximoInscricoesPorAluno;

    /**
     * Peso atribuído ao Coeficiente de Rendimento Escolar (CRE)
     * na avaliação do candidato.
     */
    @Column(name = "peso_cre", nullable = false)
    private Double pesoCre;

    /**
     * Peso atribuído à média do aluno na avaliação final.
     */
    @Column(name = "peso_media", nullable = false)
    private Double pesoMedia;

    /**
     * Status atual do edital (ABERTO, ENCERRADO, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEdital status;

    /**
     * Lista de disciplinas associadas ao edital.
     * Cada disciplina referencia este edital através do atributo "edital".
     */
    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Disciplina> listaDisciplinas;

    /**
     * Construtor padrão exigido pelo JPA.
     */
    public Edital () {}

    /**
     * Construtor utilizado para criar um edital com seus dados iniciais.
     *
     * @param id identificador único
     * @param dataInicio data de início
     * @param dataFinal data de término
     * @param maximoInscricoesPorAluno limite de inscrições por aluno
     * @param pesoCre peso do CRE na avaliação
     * @param pesoMedia peso da média na avaliação
     */
    public Edital(UUID id, LocalDate dataInicio, LocalDate dataFinal, int maximoInscricoesPorAluno, double pesoCre, double pesoMedia) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.maximoInscricoesPorAluno = maximoInscricoesPorAluno;
        this.pesoCre = pesoCre;
        this.pesoMedia = pesoMedia;
    }

    /** @return id do edital */
    public UUID getId() {
        return id;
    }

    /** @param id define um novo identificador */
    public void setId(UUID id) {
        this.id = id;
    }

    /** @return data de início do edital */
    public LocalDate getDataInicio() {
        return dataInicio;
    }

    /** @param dataInicio define a data de início do edital */
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    /** @return data final do edital */
    public LocalDate getDataFinal() {
        return dataFinal;
    }

    /** @param dataFinal define a data final do edital */
    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    /** @return máximo de inscrições por aluno */
    public int getMaximoInscricoesPorAluno() {
        return maximoInscricoesPorAluno;
    }

    /** @param maximoInscricoesPorAluno define o limite de inscrições */
    public void setMaximoInscricoesPorAluno(int maximoInscricoesPorAluno) {
        this.maximoInscricoesPorAluno = maximoInscricoesPorAluno;
    }

    /** @return peso do CRE */
    public double getPesoCre() {
        return pesoCre;
    }

    /** @param pesoCre define o peso do CRE */
    public void setPesoCre(double pesoCre) {
        this.pesoCre = pesoCre;
    }

    /** @return peso da média */
    public double getPesoMedia() {
        return pesoMedia;
    }

    /** @param pesoMedia define o peso da média */
    public void setPesoMedia(double pesoMedia) {
        this.pesoMedia = pesoMedia;
    }

    /** @return lista de disciplinas do edital */
    public List<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }

    /** @param listaDisciplinas define a lista de disciplinas */
    public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
        this.listaDisciplinas = listaDisciplinas;
    }

    /** @return status atual do edital */
    public StatusEdital getStatus() {
        return status;
    }

    /** @param status define o status atual */
    public void setStatus(StatusEdital status) {
        this.status = status;
    }
}
