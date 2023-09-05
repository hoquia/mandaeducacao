package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.Comporamento;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NotasPeriodicaDisciplina.
 */
@Entity
@Table(name = "notas_periodica_disciplina")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotasPeriodicaDisciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chave_composta", unique = true)
    private String chaveComposta;

    @Min(value = 0)
    @Max(value = 3)
    @Column(name = "periodo_lancamento")
    private Integer periodoLancamento;

    @DecimalMin(value = "0")
    @Column(name = "nota_1")
    private Double nota1;

    @DecimalMin(value = "0")
    @Column(name = "nota_2")
    private Double nota2;

    @DecimalMin(value = "0")
    @Column(name = "nota_3")
    private Double nota3;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "media", nullable = false)
    private Double media;

    @Min(value = 0)
    @Column(name = "falta_justicada")
    private Integer faltaJusticada;

    @Min(value = 0)
    @Column(name = "falta_injustificada")
    private Integer faltaInjustificada;

    @Enumerated(EnumType.STRING)
    @Column(name = "comportamento")
    private Comporamento comportamento;

    @Column(name = "hash")
    private String hash;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @OneToMany(mappedBy = "notasPeriodicaDisciplina")
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

    public NotasPeriodicaDisciplina id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChaveComposta() {
        return this.chaveComposta;
    }

    public NotasPeriodicaDisciplina chaveComposta(String chaveComposta) {
        this.setChaveComposta(chaveComposta);
        return this;
    }

    public void setChaveComposta(String chaveComposta) {
        this.chaveComposta = chaveComposta;
    }

    public Integer getPeriodoLancamento() {
        return this.periodoLancamento;
    }

    public NotasPeriodicaDisciplina periodoLancamento(Integer periodoLancamento) {
        this.setPeriodoLancamento(periodoLancamento);
        return this;
    }

    public void setPeriodoLancamento(Integer periodoLancamento) {
        this.periodoLancamento = periodoLancamento;
    }

    public Double getNota1() {
        return this.nota1;
    }

    public NotasPeriodicaDisciplina nota1(Double nota1) {
        this.setNota1(nota1);
        return this;
    }

    public void setNota1(Double nota1) {
        this.nota1 = nota1;
    }

    public Double getNota2() {
        return this.nota2;
    }

    public NotasPeriodicaDisciplina nota2(Double nota2) {
        this.setNota2(nota2);
        return this;
    }

    public void setNota2(Double nota2) {
        this.nota2 = nota2;
    }

    public Double getNota3() {
        return this.nota3;
    }

    public NotasPeriodicaDisciplina nota3(Double nota3) {
        this.setNota3(nota3);
        return this;
    }

    public void setNota3(Double nota3) {
        this.nota3 = nota3;
    }

    public Double getMedia() {
        return this.media;
    }

    public NotasPeriodicaDisciplina media(Double media) {
        this.setMedia(media);
        return this;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    public Integer getFaltaJusticada() {
        return this.faltaJusticada;
    }

    public NotasPeriodicaDisciplina faltaJusticada(Integer faltaJusticada) {
        this.setFaltaJusticada(faltaJusticada);
        return this;
    }

    public void setFaltaJusticada(Integer faltaJusticada) {
        this.faltaJusticada = faltaJusticada;
    }

    public Integer getFaltaInjustificada() {
        return this.faltaInjustificada;
    }

    public NotasPeriodicaDisciplina faltaInjustificada(Integer faltaInjustificada) {
        this.setFaltaInjustificada(faltaInjustificada);
        return this;
    }

    public void setFaltaInjustificada(Integer faltaInjustificada) {
        this.faltaInjustificada = faltaInjustificada;
    }

    public Comporamento getComportamento() {
        return this.comportamento;
    }

    public NotasPeriodicaDisciplina comportamento(Comporamento comportamento) {
        this.setComportamento(comportamento);
        return this;
    }

    public void setComportamento(Comporamento comportamento) {
        this.comportamento = comportamento;
    }

    public String getHash() {
        return this.hash;
    }

    public NotasPeriodicaDisciplina hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public NotasPeriodicaDisciplina timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setNotasPeriodicaDisciplina(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setNotasPeriodicaDisciplina(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public NotasPeriodicaDisciplina anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public NotasPeriodicaDisciplina addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setNotasPeriodicaDisciplina(this);
        return this;
    }

    public NotasPeriodicaDisciplina removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setNotasPeriodicaDisciplina(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public NotasPeriodicaDisciplina utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Turma getTurma() {
        return this.turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public NotasPeriodicaDisciplina turma(Turma turma) {
        this.setTurma(turma);
        return this;
    }

    public Docente getDocente() {
        return this.docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public NotasPeriodicaDisciplina docente(Docente docente) {
        this.setDocente(docente);
        return this;
    }

    public DisciplinaCurricular getDisciplinaCurricular() {
        return this.disciplinaCurricular;
    }

    public void setDisciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinaCurricular = disciplinaCurricular;
    }

    public NotasPeriodicaDisciplina disciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.setDisciplinaCurricular(disciplinaCurricular);
        return this;
    }

    public Matricula getMatricula() {
        return this.matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public NotasPeriodicaDisciplina matricula(Matricula matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public EstadoDisciplinaCurricular getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.estado = estadoDisciplinaCurricular;
    }

    public NotasPeriodicaDisciplina estado(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.setEstado(estadoDisciplinaCurricular);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotasPeriodicaDisciplina)) {
            return false;
        }
        return id != null && id.equals(((NotasPeriodicaDisciplina) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotasPeriodicaDisciplina{" +
            "id=" + getId() +
            ", chaveComposta='" + getChaveComposta() + "'" +
            ", periodoLancamento=" + getPeriodoLancamento() +
            ", nota1=" + getNota1() +
            ", nota2=" + getNota2() +
            ", nota3=" + getNota3() +
            ", media=" + getMedia() +
            ", faltaJusticada=" + getFaltaJusticada() +
            ", faltaInjustificada=" + getFaltaInjustificada() +
            ", comportamento='" + getComportamento() + "'" +
            ", hash='" + getHash() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
