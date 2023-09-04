package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnoLectivo.
 */
@Entity
@Table(name = "ano_lectivo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnoLectivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ano", nullable = false)
    private Integer ano;

    @NotNull
    @Column(name = "inicio", nullable = false)
    private LocalDate inicio;

    @NotNull
    @Column(name = "fim", nullable = false)
    private LocalDate fim;

    @NotNull
    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    @Column(name = "timestam")
    private ZonedDateTime timestam;

    @Column(name = "is_actual")
    private Boolean isActual;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "ocorrencias",
            "horarios",
            "planoAulas",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "dissertacaoFinalCursos",
            "categoriaOcorrencias",
            "formacoes",
            "nacionalidade",
            "naturalidade",
            "tipoDocumento",
            "grauAcademico",
            "categoriaProfissional",
            "unidadeOrganica",
            "estadoCivil",
            "responsavelTurno",
            "responsavelAreaFormacao",
            "responsavelCurso",
            "responsavelDisciplina",
            "responsavelTurma",
        },
        allowSetters = true
    )
    private Docente directorGeral;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "ocorrencias",
            "horarios",
            "planoAulas",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "dissertacaoFinalCursos",
            "categoriaOcorrencias",
            "formacoes",
            "nacionalidade",
            "naturalidade",
            "tipoDocumento",
            "grauAcademico",
            "categoriaProfissional",
            "unidadeOrganica",
            "estadoCivil",
            "responsavelTurno",
            "responsavelAreaFormacao",
            "responsavelCurso",
            "responsavelDisciplina",
            "responsavelTurma",
        },
        allowSetters = true
    )
    private Docente subDirectorPdagogico;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "ocorrencias",
            "horarios",
            "planoAulas",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "dissertacaoFinalCursos",
            "categoriaOcorrencias",
            "formacoes",
            "nacionalidade",
            "naturalidade",
            "tipoDocumento",
            "grauAcademico",
            "categoriaProfissional",
            "unidadeOrganica",
            "estadoCivil",
            "responsavelTurno",
            "responsavelAreaFormacao",
            "responsavelCurso",
            "responsavelDisciplina",
            "responsavelTurma",
        },
        allowSetters = true
    )
    private Docente subDirectorAdministrativo;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "ocorrencias",
            "horarios",
            "planoAulas",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "dissertacaoFinalCursos",
            "categoriaOcorrencias",
            "formacoes",
            "nacionalidade",
            "naturalidade",
            "tipoDocumento",
            "grauAcademico",
            "categoriaProfissional",
            "unidadeOrganica",
            "estadoCivil",
            "responsavelTurno",
            "responsavelAreaFormacao",
            "responsavelCurso",
            "responsavelDisciplina",
            "responsavelTurma",
        },
        allowSetters = true
    )
    private Docente responsavelSecretariaGeral;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "ocorrencias",
            "horarios",
            "planoAulas",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "dissertacaoFinalCursos",
            "categoriaOcorrencias",
            "formacoes",
            "nacionalidade",
            "naturalidade",
            "tipoDocumento",
            "grauAcademico",
            "categoriaProfissional",
            "unidadeOrganica",
            "estadoCivil",
            "responsavelTurno",
            "responsavelAreaFormacao",
            "responsavelCurso",
            "responsavelDisciplina",
            "responsavelTurma",
        },
        allowSetters = true
    )
    private Docente responsavelSecretariaPedagogico;

    @ManyToOne
    private User utilizador;

    @ManyToMany
    @JoinTable(
        name = "rel_ano_lectivo__nives_ensino",
        joinColumns = @JoinColumn(name = "ano_lectivo_id"),
        inverseJoinColumns = @JoinColumn(name = "nives_ensino_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nivelEnsinos", "areaFormacaos", "referencia", "anoLectivos", "classes" }, allowSetters = true)
    private Set<NivelEnsino> nivesEnsinos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "turmas",
            "horarios",
            "notasPeriodicaDisciplinas",
            "processoSelectivoMatriculas",
            "planoAulas",
            "matriculas",
            "resumoAcademicos",
            "responsaveis",
            "dissertacaoFinalCursos",
            "anoLectivos",
            "utilizador",
            "referencia",
            "planoCurricular",
            "turno",
        },
        allowSetters = true
    )
    private Turma turma;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "horarios", "licaos", "anoLectivos", "utilizador", "turma", "referencia", "periodo", "docente", "disciplinaCurricular" },
        allowSetters = true
    )
    private Horario horario;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "detalhes",
            "licaos",
            "anoLectivos",
            "utilizador",
            "unidadeTematica",
            "subUnidadeTematica",
            "turma",
            "docente",
            "disciplinaCurricular",
        },
        allowSetters = true
    )
    private PlanoAula planoAula;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ocorrencias", "anoLectivos", "utilizador", "planoAula", "horario" }, allowSetters = true)
    private Licao licao;

    @ManyToOne
    @JsonIgnoreProperties(value = { "anoLectivos", "utilizador", "turma", "discente" }, allowSetters = true)
    private ProcessoSelectivoMatricula processoSelectivoMatricula;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "ocorrencias", "anoLectivos", "utilizador", "referencia", "docente", "matricula", "estado", "licao" },
        allowSetters = true
    )
    private Ocorrencia ocorrencia;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "docente", "disciplinaCurricular", "matricula", "estado" },
        allowSetters = true
    )
    private NotasPeriodicaDisciplina notasPeriodicaDisciplina;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "docente", "disciplinaCurricular", "matricula", "estado" },
        allowSetters = true
    )
    private NotasGeralDisciplina notasGeralDisciplina;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "orientador", "especialidade", "discente", "estado", "natureza" },
        allowSetters = true
    )
    private DissertacaoFinalCurso dissertacaoFinalCurso;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "facturas",
            "itemsFacturas",
            "aplicacoesFacturas",
            "resumosImpostos",
            "anoLectivos",
            "utilizador",
            "motivoAnulacao",
            "matricula",
            "referencia",
            "documentoComercial",
        },
        allowSetters = true
    )
    private Factura factura;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "aplicacoesRecibos", "anoLectivos", "utilizador", "matricula", "documentoComercial", "transacao" },
        allowSetters = true
    )
    private Recibo recibo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "turno" }, allowSetters = true)
    private ResponsavelTurno responsavelTurno;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "areaFormacao" }, allowSetters = true)
    private ResponsavelAreaFormacao responsavelAreaFormacao;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "curso" }, allowSetters = true)
    private ResponsavelCurso responsavelCurso;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "disciplina" }, allowSetters = true)
    private ResponsavelDisciplina responsavelDisciplina;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "turma" }, allowSetters = true)
    private ResponsavelTurma responsavelTurma;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnoLectivo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAno() {
        return this.ano;
    }

    public AnoLectivo ano(Integer ano) {
        this.setAno(ano);
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public LocalDate getInicio() {
        return this.inicio;
    }

    public AnoLectivo inicio(LocalDate inicio) {
        this.setInicio(inicio);
        return this;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return this.fim;
    }

    public AnoLectivo fim(LocalDate fim) {
        this.setFim(fim);
        return this;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public AnoLectivo descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getTimestam() {
        return this.timestam;
    }

    public AnoLectivo timestam(ZonedDateTime timestam) {
        this.setTimestam(timestam);
        return this;
    }

    public void setTimestam(ZonedDateTime timestam) {
        this.timestam = timestam;
    }

    public Boolean getIsActual() {
        return this.isActual;
    }

    public AnoLectivo isActual(Boolean isActual) {
        this.setIsActual(isActual);
        return this;
    }

    public void setIsActual(Boolean isActual) {
        this.isActual = isActual;
    }

    public Docente getDirectorGeral() {
        return this.directorGeral;
    }

    public void setDirectorGeral(Docente docente) {
        this.directorGeral = docente;
    }

    public AnoLectivo directorGeral(Docente docente) {
        this.setDirectorGeral(docente);
        return this;
    }

    public Docente getSubDirectorPdagogico() {
        return this.subDirectorPdagogico;
    }

    public void setSubDirectorPdagogico(Docente docente) {
        this.subDirectorPdagogico = docente;
    }

    public AnoLectivo subDirectorPdagogico(Docente docente) {
        this.setSubDirectorPdagogico(docente);
        return this;
    }

    public Docente getSubDirectorAdministrativo() {
        return this.subDirectorAdministrativo;
    }

    public void setSubDirectorAdministrativo(Docente docente) {
        this.subDirectorAdministrativo = docente;
    }

    public AnoLectivo subDirectorAdministrativo(Docente docente) {
        this.setSubDirectorAdministrativo(docente);
        return this;
    }

    public Docente getResponsavelSecretariaGeral() {
        return this.responsavelSecretariaGeral;
    }

    public void setResponsavelSecretariaGeral(Docente docente) {
        this.responsavelSecretariaGeral = docente;
    }

    public AnoLectivo responsavelSecretariaGeral(Docente docente) {
        this.setResponsavelSecretariaGeral(docente);
        return this;
    }

    public Docente getResponsavelSecretariaPedagogico() {
        return this.responsavelSecretariaPedagogico;
    }

    public void setResponsavelSecretariaPedagogico(Docente docente) {
        this.responsavelSecretariaPedagogico = docente;
    }

    public AnoLectivo responsavelSecretariaPedagogico(Docente docente) {
        this.setResponsavelSecretariaPedagogico(docente);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public AnoLectivo utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Set<NivelEnsino> getNivesEnsinos() {
        return this.nivesEnsinos;
    }

    public void setNivesEnsinos(Set<NivelEnsino> nivelEnsinos) {
        this.nivesEnsinos = nivelEnsinos;
    }

    public AnoLectivo nivesEnsinos(Set<NivelEnsino> nivelEnsinos) {
        this.setNivesEnsinos(nivelEnsinos);
        return this;
    }

    public AnoLectivo addNivesEnsino(NivelEnsino nivelEnsino) {
        this.nivesEnsinos.add(nivelEnsino);
        nivelEnsino.getAnoLectivos().add(this);
        return this;
    }

    public AnoLectivo removeNivesEnsino(NivelEnsino nivelEnsino) {
        this.nivesEnsinos.remove(nivelEnsino);
        nivelEnsino.getAnoLectivos().remove(this);
        return this;
    }

    public Turma getTurma() {
        return this.turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public AnoLectivo turma(Turma turma) {
        this.setTurma(turma);
        return this;
    }

    public Horario getHorario() {
        return this.horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public AnoLectivo horario(Horario horario) {
        this.setHorario(horario);
        return this;
    }

    public PlanoAula getPlanoAula() {
        return this.planoAula;
    }

    public void setPlanoAula(PlanoAula planoAula) {
        this.planoAula = planoAula;
    }

    public AnoLectivo planoAula(PlanoAula planoAula) {
        this.setPlanoAula(planoAula);
        return this;
    }

    public Licao getLicao() {
        return this.licao;
    }

    public void setLicao(Licao licao) {
        this.licao = licao;
    }

    public AnoLectivo licao(Licao licao) {
        this.setLicao(licao);
        return this;
    }

    public ProcessoSelectivoMatricula getProcessoSelectivoMatricula() {
        return this.processoSelectivoMatricula;
    }

    public void setProcessoSelectivoMatricula(ProcessoSelectivoMatricula processoSelectivoMatricula) {
        this.processoSelectivoMatricula = processoSelectivoMatricula;
    }

    public AnoLectivo processoSelectivoMatricula(ProcessoSelectivoMatricula processoSelectivoMatricula) {
        this.setProcessoSelectivoMatricula(processoSelectivoMatricula);
        return this;
    }

    public Ocorrencia getOcorrencia() {
        return this.ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public AnoLectivo ocorrencia(Ocorrencia ocorrencia) {
        this.setOcorrencia(ocorrencia);
        return this;
    }

    public NotasPeriodicaDisciplina getNotasPeriodicaDisciplina() {
        return this.notasPeriodicaDisciplina;
    }

    public void setNotasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplina = notasPeriodicaDisciplina;
    }

    public AnoLectivo notasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.setNotasPeriodicaDisciplina(notasPeriodicaDisciplina);
        return this;
    }

    public NotasGeralDisciplina getNotasGeralDisciplina() {
        return this.notasGeralDisciplina;
    }

    public void setNotasGeralDisciplina(NotasGeralDisciplina notasGeralDisciplina) {
        this.notasGeralDisciplina = notasGeralDisciplina;
    }

    public AnoLectivo notasGeralDisciplina(NotasGeralDisciplina notasGeralDisciplina) {
        this.setNotasGeralDisciplina(notasGeralDisciplina);
        return this;
    }

    public DissertacaoFinalCurso getDissertacaoFinalCurso() {
        return this.dissertacaoFinalCurso;
    }

    public void setDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCurso = dissertacaoFinalCurso;
    }

    public AnoLectivo dissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.setDissertacaoFinalCurso(dissertacaoFinalCurso);
        return this;
    }

    public Factura getFactura() {
        return this.factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public AnoLectivo factura(Factura factura) {
        this.setFactura(factura);
        return this;
    }

    public Recibo getRecibo() {
        return this.recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

    public AnoLectivo recibo(Recibo recibo) {
        this.setRecibo(recibo);
        return this;
    }

    public ResponsavelTurno getResponsavelTurno() {
        return this.responsavelTurno;
    }

    public void setResponsavelTurno(ResponsavelTurno responsavelTurno) {
        this.responsavelTurno = responsavelTurno;
    }

    public AnoLectivo responsavelTurno(ResponsavelTurno responsavelTurno) {
        this.setResponsavelTurno(responsavelTurno);
        return this;
    }

    public ResponsavelAreaFormacao getResponsavelAreaFormacao() {
        return this.responsavelAreaFormacao;
    }

    public void setResponsavelAreaFormacao(ResponsavelAreaFormacao responsavelAreaFormacao) {
        this.responsavelAreaFormacao = responsavelAreaFormacao;
    }

    public AnoLectivo responsavelAreaFormacao(ResponsavelAreaFormacao responsavelAreaFormacao) {
        this.setResponsavelAreaFormacao(responsavelAreaFormacao);
        return this;
    }

    public ResponsavelCurso getResponsavelCurso() {
        return this.responsavelCurso;
    }

    public void setResponsavelCurso(ResponsavelCurso responsavelCurso) {
        this.responsavelCurso = responsavelCurso;
    }

    public AnoLectivo responsavelCurso(ResponsavelCurso responsavelCurso) {
        this.setResponsavelCurso(responsavelCurso);
        return this;
    }

    public ResponsavelDisciplina getResponsavelDisciplina() {
        return this.responsavelDisciplina;
    }

    public void setResponsavelDisciplina(ResponsavelDisciplina responsavelDisciplina) {
        this.responsavelDisciplina = responsavelDisciplina;
    }

    public AnoLectivo responsavelDisciplina(ResponsavelDisciplina responsavelDisciplina) {
        this.setResponsavelDisciplina(responsavelDisciplina);
        return this;
    }

    public ResponsavelTurma getResponsavelTurma() {
        return this.responsavelTurma;
    }

    public void setResponsavelTurma(ResponsavelTurma responsavelTurma) {
        this.responsavelTurma = responsavelTurma;
    }

    public AnoLectivo responsavelTurma(ResponsavelTurma responsavelTurma) {
        this.setResponsavelTurma(responsavelTurma);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnoLectivo)) {
            return false;
        }
        return id != null && id.equals(((AnoLectivo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnoLectivo{" +
            "id=" + getId() +
            ", ano=" + getAno() +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", timestam='" + getTimestam() + "'" +
            ", isActual='" + getIsActual() + "'" +
            "}";
    }
}
