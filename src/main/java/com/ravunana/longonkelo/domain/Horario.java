package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.DiaSemana;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Horario.
 */
@Entity
@Table(name = "horario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "chave_composta_1", unique = true)
    private String chaveComposta1;

    @Column(name = "chave_composta_2", unique = true)
    private String chaveComposta2;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DiaSemana diaSemana;

    @OneToMany(mappedBy = "referencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "horarios", "licaos", "anoLectivos", "utilizador", "turma", "referencia", "periodo", "docente", "disciplinaCurricular" },
        allowSetters = true
    )
    private Set<Horario> horarios = new HashSet<>();

    @OneToMany(mappedBy = "horario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ocorrencias", "anoLectivos", "utilizador", "planoAula", "horario" }, allowSetters = true)
    private Set<Licao> licaos = new HashSet<>();

    @OneToMany(mappedBy = "horario")
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

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "horarios", "licaos", "anoLectivos", "utilizador", "turma", "referencia", "periodo", "docente", "disciplinaCurricular" },
        allowSetters = true
    )
    private Horario referencia;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "horarios", "turno" }, allowSetters = true)
    private PeriodoHorario periodo;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Horario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChaveComposta1() {
        return this.chaveComposta1;
    }

    public Horario chaveComposta1(String chaveComposta1) {
        this.setChaveComposta1(chaveComposta1);
        return this;
    }

    public void setChaveComposta1(String chaveComposta1) {
        this.chaveComposta1 = chaveComposta1;
    }

    public String getChaveComposta2() {
        return this.chaveComposta2;
    }

    public Horario chaveComposta2(String chaveComposta2) {
        this.setChaveComposta2(chaveComposta2);
        return this;
    }

    public void setChaveComposta2(String chaveComposta2) {
        this.chaveComposta2 = chaveComposta2;
    }

    public DiaSemana getDiaSemana() {
        return this.diaSemana;
    }

    public Horario diaSemana(DiaSemana diaSemana) {
        this.setDiaSemana(diaSemana);
        return this;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Set<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        if (this.horarios != null) {
            this.horarios.forEach(i -> i.setReferencia(null));
        }
        if (horarios != null) {
            horarios.forEach(i -> i.setReferencia(this));
        }
        this.horarios = horarios;
    }

    public Horario horarios(Set<Horario> horarios) {
        this.setHorarios(horarios);
        return this;
    }

    public Horario addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setReferencia(this);
        return this;
    }

    public Horario removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setReferencia(null);
        return this;
    }

    public Set<Licao> getLicaos() {
        return this.licaos;
    }

    public void setLicaos(Set<Licao> licaos) {
        if (this.licaos != null) {
            this.licaos.forEach(i -> i.setHorario(null));
        }
        if (licaos != null) {
            licaos.forEach(i -> i.setHorario(this));
        }
        this.licaos = licaos;
    }

    public Horario licaos(Set<Licao> licaos) {
        this.setLicaos(licaos);
        return this;
    }

    public Horario addLicao(Licao licao) {
        this.licaos.add(licao);
        licao.setHorario(this);
        return this;
    }

    public Horario removeLicao(Licao licao) {
        this.licaos.remove(licao);
        licao.setHorario(null);
        return this;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setHorario(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setHorario(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public Horario anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public Horario addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setHorario(this);
        return this;
    }

    public Horario removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setHorario(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Horario utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Turma getTurma() {
        return this.turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Horario turma(Turma turma) {
        this.setTurma(turma);
        return this;
    }

    public Horario getReferencia() {
        return this.referencia;
    }

    public void setReferencia(Horario horario) {
        this.referencia = horario;
    }

    public Horario referencia(Horario horario) {
        this.setReferencia(horario);
        return this;
    }

    public PeriodoHorario getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(PeriodoHorario periodoHorario) {
        this.periodo = periodoHorario;
    }

    public Horario periodo(PeriodoHorario periodoHorario) {
        this.setPeriodo(periodoHorario);
        return this;
    }

    public Docente getDocente() {
        return this.docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Horario docente(Docente docente) {
        this.setDocente(docente);
        return this;
    }

    public DisciplinaCurricular getDisciplinaCurricular() {
        return this.disciplinaCurricular;
    }

    public void setDisciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinaCurricular = disciplinaCurricular;
    }

    public Horario disciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.setDisciplinaCurricular(disciplinaCurricular);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Horario)) {
            return false;
        }
        return id != null && id.equals(((Horario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Horario{" +
            "id=" + getId() +
            ", chaveComposta1='" + getChaveComposta1() + "'" +
            ", chaveComposta2='" + getChaveComposta2() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            "}";
    }
}
