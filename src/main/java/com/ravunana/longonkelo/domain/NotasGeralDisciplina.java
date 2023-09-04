package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NotasGeralDisciplina.
 */
@Entity
@Table(name = "notas_geral_disciplina")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotasGeralDisciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "chave_composta", unique = true)
    private String chaveComposta;

    @Min(value = 0)
    @Max(value = 3)
    @Column(name = "periodo_lancamento")
    private Integer periodoLancamento;

    @DecimalMin(value = "0")
    @Column(name = "media_1")
    private Double media1;

    @DecimalMin(value = "0")
    @Column(name = "media_2")
    private Double media2;

    @DecimalMin(value = "0")
    @Column(name = "media_3")
    private Double media3;

    @DecimalMin(value = "0")
    @Column(name = "exame")
    private Double exame;

    @DecimalMin(value = "0")
    @Column(name = "recurso")
    private Double recurso;

    @DecimalMin(value = "0")
    @Column(name = "exame_especial")
    private Double exameEspecial;

    @DecimalMin(value = "0")
    @Column(name = "nota_conselho")
    private Double notaConselho;

    @DecimalMin(value = "0")
    @Column(name = "media_final_disciplina")
    private Double mediaFinalDisciplina;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @Column(name = "hash")
    private String hash;

    @Min(value = 0)
    @Column(name = "falta_justicada")
    private Integer faltaJusticada;

    @Min(value = 0)
    @Column(name = "falta_injustificada")
    private Integer faltaInjustificada;

    @OneToMany(mappedBy = "notasGeralDisciplina")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "directorGeral",
            "subDirectorPdagogico",
            "subDirectorAdministrativo",
            "responsavelSecretariaGeral",
            "responsavelSecretariaPedagogico",
            "utilizador",
            "nivesEnsinos",
            "turma",
            "horario",
            "planoAula",
            "licao",
            "processoSelectivoMatricula",
            "ocorrencia",
            "notasPeriodicaDisciplina",
            "notasGeralDisciplina",
            "dissertacaoFinalCurso",
            "factura",
            "recibo",
            "responsavelTurno",
            "responsavelAreaFormacao",
            "responsavelCurso",
            "responsavelDisciplina",
            "responsavelTurma",
        },
        allowSetters = true
    )
    private Set<AnoLectivo> anoLectivos = new HashSet<>();

    @ManyToOne
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
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
    private Docente docente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "disciplinaCurriculars",
            "horarios",
            "planoAulas",
            "notasGeralDisciplinas",
            "notasPeriodicaDisciplinas",
            "componente",
            "regime",
            "planosCurriculars",
            "disciplina",
            "referencia",
            "estados",
        },
        allowSetters = true
    )
    private DisciplinaCurricular disciplinaCurricular;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "matriculas",
            "facturas",
            "transacoes",
            "recibos",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "transferenciaTurmas",
            "ocorrencias",
            "utilizador",
            "categoriasMatriculas",
            "turma",
            "responsavelFinanceiro",
            "discente",
            "referencia",
        },
        allowSetters = true
    )
    private Matricula matricula;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "estadoDisciplinaCurriculars",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "resumoAcademicos",
            "disciplinasCurriculars",
            "referencia",
        },
        allowSetters = true
    )
    private EstadoDisciplinaCurricular estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NotasGeralDisciplina id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChaveComposta() {
        return this.chaveComposta;
    }

    public NotasGeralDisciplina chaveComposta(String chaveComposta) {
        this.setChaveComposta(chaveComposta);
        return this;
    }

    public void setChaveComposta(String chaveComposta) {
        this.chaveComposta = chaveComposta;
    }

    public Integer getPeriodoLancamento() {
        return this.periodoLancamento;
    }

    public NotasGeralDisciplina periodoLancamento(Integer periodoLancamento) {
        this.setPeriodoLancamento(periodoLancamento);
        return this;
    }

    public void setPeriodoLancamento(Integer periodoLancamento) {
        this.periodoLancamento = periodoLancamento;
    }

    public Double getMedia1() {
        return this.media1;
    }

    public NotasGeralDisciplina media1(Double media1) {
        this.setMedia1(media1);
        return this;
    }

    public void setMedia1(Double media1) {
        this.media1 = media1;
    }

    public Double getMedia2() {
        return this.media2;
    }

    public NotasGeralDisciplina media2(Double media2) {
        this.setMedia2(media2);
        return this;
    }

    public void setMedia2(Double media2) {
        this.media2 = media2;
    }

    public Double getMedia3() {
        return this.media3;
    }

    public NotasGeralDisciplina media3(Double media3) {
        this.setMedia3(media3);
        return this;
    }

    public void setMedia3(Double media3) {
        this.media3 = media3;
    }

    public Double getExame() {
        return this.exame;
    }

    public NotasGeralDisciplina exame(Double exame) {
        this.setExame(exame);
        return this;
    }

    public void setExame(Double exame) {
        this.exame = exame;
    }

    public Double getRecurso() {
        return this.recurso;
    }

    public NotasGeralDisciplina recurso(Double recurso) {
        this.setRecurso(recurso);
        return this;
    }

    public void setRecurso(Double recurso) {
        this.recurso = recurso;
    }

    public Double getExameEspecial() {
        return this.exameEspecial;
    }

    public NotasGeralDisciplina exameEspecial(Double exameEspecial) {
        this.setExameEspecial(exameEspecial);
        return this;
    }

    public void setExameEspecial(Double exameEspecial) {
        this.exameEspecial = exameEspecial;
    }

    public Double getNotaConselho() {
        return this.notaConselho;
    }

    public NotasGeralDisciplina notaConselho(Double notaConselho) {
        this.setNotaConselho(notaConselho);
        return this;
    }

    public void setNotaConselho(Double notaConselho) {
        this.notaConselho = notaConselho;
    }

    public Double getMediaFinalDisciplina() {
        return this.mediaFinalDisciplina;
    }

    public NotasGeralDisciplina mediaFinalDisciplina(Double mediaFinalDisciplina) {
        this.setMediaFinalDisciplina(mediaFinalDisciplina);
        return this;
    }

    public void setMediaFinalDisciplina(Double mediaFinalDisciplina) {
        this.mediaFinalDisciplina = mediaFinalDisciplina;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public NotasGeralDisciplina timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getHash() {
        return this.hash;
    }

    public NotasGeralDisciplina hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getFaltaJusticada() {
        return this.faltaJusticada;
    }

    public NotasGeralDisciplina faltaJusticada(Integer faltaJusticada) {
        this.setFaltaJusticada(faltaJusticada);
        return this;
    }

    public void setFaltaJusticada(Integer faltaJusticada) {
        this.faltaJusticada = faltaJusticada;
    }

    public Integer getFaltaInjustificada() {
        return this.faltaInjustificada;
    }

    public NotasGeralDisciplina faltaInjustificada(Integer faltaInjustificada) {
        this.setFaltaInjustificada(faltaInjustificada);
        return this;
    }

    public void setFaltaInjustificada(Integer faltaInjustificada) {
        this.faltaInjustificada = faltaInjustificada;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setNotasGeralDisciplina(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setNotasGeralDisciplina(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public NotasGeralDisciplina anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public NotasGeralDisciplina addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setNotasGeralDisciplina(this);
        return this;
    }

    public NotasGeralDisciplina removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setNotasGeralDisciplina(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public NotasGeralDisciplina utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Docente getDocente() {
        return this.docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public NotasGeralDisciplina docente(Docente docente) {
        this.setDocente(docente);
        return this;
    }

    public DisciplinaCurricular getDisciplinaCurricular() {
        return this.disciplinaCurricular;
    }

    public void setDisciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinaCurricular = disciplinaCurricular;
    }

    public NotasGeralDisciplina disciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.setDisciplinaCurricular(disciplinaCurricular);
        return this;
    }

    public Matricula getMatricula() {
        return this.matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public NotasGeralDisciplina matricula(Matricula matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public EstadoDisciplinaCurricular getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.estado = estadoDisciplinaCurricular;
    }

    public NotasGeralDisciplina estado(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.setEstado(estadoDisciplinaCurricular);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotasGeralDisciplina)) {
            return false;
        }
        return id != null && id.equals(((NotasGeralDisciplina) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotasGeralDisciplina{" +
            "id=" + getId() +
            ", chaveComposta='" + getChaveComposta() + "'" +
            ", periodoLancamento=" + getPeriodoLancamento() +
            ", media1=" + getMedia1() +
            ", media2=" + getMedia2() +
            ", media3=" + getMedia3() +
            ", exame=" + getExame() +
            ", recurso=" + getRecurso() +
            ", exameEspecial=" + getExameEspecial() +
            ", notaConselho=" + getNotaConselho() +
            ", mediaFinalDisciplina=" + getMediaFinalDisciplina() +
            ", timestamp='" + getTimestamp() + "'" +
            ", hash='" + getHash() + "'" +
            ", faltaJusticada=" + getFaltaJusticada() +
            ", faltaInjustificada=" + getFaltaInjustificada() +
            "}";
    }
}
