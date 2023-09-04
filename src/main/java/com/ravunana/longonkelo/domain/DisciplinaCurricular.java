package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DisciplinaCurricular.
 */
@Entity
@Table(name = "disciplina_curricular")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DisciplinaCurricular implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_disciplina_curricular", unique = true)
    private String uniqueDisciplinaCurricular;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @DecimalMin(value = "0")
    @Column(name = "carga_semanal")
    private Double cargaSemanal;

    @Column(name = "is_terminal")
    private Boolean isTerminal;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "media_para_exame", nullable = false)
    private Double mediaParaExame;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "media_para_recurso", nullable = false)
    private Double mediaParaRecurso;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "media_para_exame_especial", nullable = false)
    private Double mediaParaExameEspecial;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "media_para_despensar", nullable = false)
    private Double mediaParaDespensar;

    @OneToMany(mappedBy = "referencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<DisciplinaCurricular> disciplinaCurriculars = new HashSet<>();

    @OneToMany(mappedBy = "disciplinaCurricular")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "horarios", "licaos", "anoLectivos", "utilizador", "turma", "referencia", "periodo", "docente", "disciplinaCurricular" },
        allowSetters = true
    )
    private Set<Horario> horarios = new HashSet<>();

    @OneToMany(mappedBy = "disciplinaCurricular")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<PlanoAula> planoAulas = new HashSet<>();

    @OneToMany(mappedBy = "disciplinaCurricular")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "docente", "disciplinaCurricular", "matricula", "estado" },
        allowSetters = true
    )
    private Set<NotasGeralDisciplina> notasGeralDisciplinas = new HashSet<>();

    @OneToMany(mappedBy = "disciplinaCurricular")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "docente", "disciplinaCurricular", "matricula", "estado" },
        allowSetters = true
    )
    private Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem componente;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem regime;

    @ManyToMany
    @JoinTable(
        name = "rel_disciplina_curricular__planos_curricular",
        joinColumns = @JoinColumn(name = "disciplina_curricular_id"),
        inverseJoinColumns = @JoinColumn(name = "planos_curricular_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "turmas", "utilizador", "classe", "curso", "disciplinasCurriculars" }, allowSetters = true)
    private Set<PlanoCurricular> planosCurriculars = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "responsaveis", "disciplinaCurriculars" }, allowSetters = true)
    private Disciplina disciplina;

    @ManyToOne
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
    private DisciplinaCurricular referencia;

    @ManyToMany(mappedBy = "disciplinasCurriculars")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<EstadoDisciplinaCurricular> estados = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DisciplinaCurricular id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueDisciplinaCurricular() {
        return this.uniqueDisciplinaCurricular;
    }

    public DisciplinaCurricular uniqueDisciplinaCurricular(String uniqueDisciplinaCurricular) {
        this.setUniqueDisciplinaCurricular(uniqueDisciplinaCurricular);
        return this;
    }

    public void setUniqueDisciplinaCurricular(String uniqueDisciplinaCurricular) {
        this.uniqueDisciplinaCurricular = uniqueDisciplinaCurricular;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public DisciplinaCurricular descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getCargaSemanal() {
        return this.cargaSemanal;
    }

    public DisciplinaCurricular cargaSemanal(Double cargaSemanal) {
        this.setCargaSemanal(cargaSemanal);
        return this;
    }

    public void setCargaSemanal(Double cargaSemanal) {
        this.cargaSemanal = cargaSemanal;
    }

    public Boolean getIsTerminal() {
        return this.isTerminal;
    }

    public DisciplinaCurricular isTerminal(Boolean isTerminal) {
        this.setIsTerminal(isTerminal);
        return this;
    }

    public void setIsTerminal(Boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

    public Double getMediaParaExame() {
        return this.mediaParaExame;
    }

    public DisciplinaCurricular mediaParaExame(Double mediaParaExame) {
        this.setMediaParaExame(mediaParaExame);
        return this;
    }

    public void setMediaParaExame(Double mediaParaExame) {
        this.mediaParaExame = mediaParaExame;
    }

    public Double getMediaParaRecurso() {
        return this.mediaParaRecurso;
    }

    public DisciplinaCurricular mediaParaRecurso(Double mediaParaRecurso) {
        this.setMediaParaRecurso(mediaParaRecurso);
        return this;
    }

    public void setMediaParaRecurso(Double mediaParaRecurso) {
        this.mediaParaRecurso = mediaParaRecurso;
    }

    public Double getMediaParaExameEspecial() {
        return this.mediaParaExameEspecial;
    }

    public DisciplinaCurricular mediaParaExameEspecial(Double mediaParaExameEspecial) {
        this.setMediaParaExameEspecial(mediaParaExameEspecial);
        return this;
    }

    public void setMediaParaExameEspecial(Double mediaParaExameEspecial) {
        this.mediaParaExameEspecial = mediaParaExameEspecial;
    }

    public Double getMediaParaDespensar() {
        return this.mediaParaDespensar;
    }

    public DisciplinaCurricular mediaParaDespensar(Double mediaParaDespensar) {
        this.setMediaParaDespensar(mediaParaDespensar);
        return this;
    }

    public void setMediaParaDespensar(Double mediaParaDespensar) {
        this.mediaParaDespensar = mediaParaDespensar;
    }

    public Set<DisciplinaCurricular> getDisciplinaCurriculars() {
        return this.disciplinaCurriculars;
    }

    public void setDisciplinaCurriculars(Set<DisciplinaCurricular> disciplinaCurriculars) {
        if (this.disciplinaCurriculars != null) {
            this.disciplinaCurriculars.forEach(i -> i.setReferencia(null));
        }
        if (disciplinaCurriculars != null) {
            disciplinaCurriculars.forEach(i -> i.setReferencia(this));
        }
        this.disciplinaCurriculars = disciplinaCurriculars;
    }

    public DisciplinaCurricular disciplinaCurriculars(Set<DisciplinaCurricular> disciplinaCurriculars) {
        this.setDisciplinaCurriculars(disciplinaCurriculars);
        return this;
    }

    public DisciplinaCurricular addDisciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinaCurriculars.add(disciplinaCurricular);
        disciplinaCurricular.setReferencia(this);
        return this;
    }

    public DisciplinaCurricular removeDisciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinaCurriculars.remove(disciplinaCurricular);
        disciplinaCurricular.setReferencia(null);
        return this;
    }

    public Set<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        if (this.horarios != null) {
            this.horarios.forEach(i -> i.setDisciplinaCurricular(null));
        }
        if (horarios != null) {
            horarios.forEach(i -> i.setDisciplinaCurricular(this));
        }
        this.horarios = horarios;
    }

    public DisciplinaCurricular horarios(Set<Horario> horarios) {
        this.setHorarios(horarios);
        return this;
    }

    public DisciplinaCurricular addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setDisciplinaCurricular(this);
        return this;
    }

    public DisciplinaCurricular removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setDisciplinaCurricular(null);
        return this;
    }

    public Set<PlanoAula> getPlanoAulas() {
        return this.planoAulas;
    }

    public void setPlanoAulas(Set<PlanoAula> planoAulas) {
        if (this.planoAulas != null) {
            this.planoAulas.forEach(i -> i.setDisciplinaCurricular(null));
        }
        if (planoAulas != null) {
            planoAulas.forEach(i -> i.setDisciplinaCurricular(this));
        }
        this.planoAulas = planoAulas;
    }

    public DisciplinaCurricular planoAulas(Set<PlanoAula> planoAulas) {
        this.setPlanoAulas(planoAulas);
        return this;
    }

    public DisciplinaCurricular addPlanoAula(PlanoAula planoAula) {
        this.planoAulas.add(planoAula);
        planoAula.setDisciplinaCurricular(this);
        return this;
    }

    public DisciplinaCurricular removePlanoAula(PlanoAula planoAula) {
        this.planoAulas.remove(planoAula);
        planoAula.setDisciplinaCurricular(null);
        return this;
    }

    public Set<NotasGeralDisciplina> getNotasGeralDisciplinas() {
        return this.notasGeralDisciplinas;
    }

    public void setNotasGeralDisciplinas(Set<NotasGeralDisciplina> notasGeralDisciplinas) {
        if (this.notasGeralDisciplinas != null) {
            this.notasGeralDisciplinas.forEach(i -> i.setDisciplinaCurricular(null));
        }
        if (notasGeralDisciplinas != null) {
            notasGeralDisciplinas.forEach(i -> i.setDisciplinaCurricular(this));
        }
        this.notasGeralDisciplinas = notasGeralDisciplinas;
    }

    public DisciplinaCurricular notasGeralDisciplinas(Set<NotasGeralDisciplina> notasGeralDisciplinas) {
        this.setNotasGeralDisciplinas(notasGeralDisciplinas);
        return this;
    }

    public DisciplinaCurricular addNotasGeralDisciplina(NotasGeralDisciplina notasGeralDisciplina) {
        this.notasGeralDisciplinas.add(notasGeralDisciplina);
        notasGeralDisciplina.setDisciplinaCurricular(this);
        return this;
    }

    public DisciplinaCurricular removeNotasGeralDisciplina(NotasGeralDisciplina notasGeralDisciplina) {
        this.notasGeralDisciplinas.remove(notasGeralDisciplina);
        notasGeralDisciplina.setDisciplinaCurricular(null);
        return this;
    }

    public Set<NotasPeriodicaDisciplina> getNotasPeriodicaDisciplinas() {
        return this.notasPeriodicaDisciplinas;
    }

    public void setNotasPeriodicaDisciplinas(Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas) {
        if (this.notasPeriodicaDisciplinas != null) {
            this.notasPeriodicaDisciplinas.forEach(i -> i.setDisciplinaCurricular(null));
        }
        if (notasPeriodicaDisciplinas != null) {
            notasPeriodicaDisciplinas.forEach(i -> i.setDisciplinaCurricular(this));
        }
        this.notasPeriodicaDisciplinas = notasPeriodicaDisciplinas;
    }

    public DisciplinaCurricular notasPeriodicaDisciplinas(Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas) {
        this.setNotasPeriodicaDisciplinas(notasPeriodicaDisciplinas);
        return this;
    }

    public DisciplinaCurricular addNotasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplinas.add(notasPeriodicaDisciplina);
        notasPeriodicaDisciplina.setDisciplinaCurricular(this);
        return this;
    }

    public DisciplinaCurricular removeNotasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplinas.remove(notasPeriodicaDisciplina);
        notasPeriodicaDisciplina.setDisciplinaCurricular(null);
        return this;
    }

    public LookupItem getComponente() {
        return this.componente;
    }

    public void setComponente(LookupItem lookupItem) {
        this.componente = lookupItem;
    }

    public DisciplinaCurricular componente(LookupItem lookupItem) {
        this.setComponente(lookupItem);
        return this;
    }

    public LookupItem getRegime() {
        return this.regime;
    }

    public void setRegime(LookupItem lookupItem) {
        this.regime = lookupItem;
    }

    public DisciplinaCurricular regime(LookupItem lookupItem) {
        this.setRegime(lookupItem);
        return this;
    }

    public Set<PlanoCurricular> getPlanosCurriculars() {
        return this.planosCurriculars;
    }

    public void setPlanosCurriculars(Set<PlanoCurricular> planoCurriculars) {
        this.planosCurriculars = planoCurriculars;
    }

    public DisciplinaCurricular planosCurriculars(Set<PlanoCurricular> planoCurriculars) {
        this.setPlanosCurriculars(planoCurriculars);
        return this;
    }

    public DisciplinaCurricular addPlanosCurricular(PlanoCurricular planoCurricular) {
        this.planosCurriculars.add(planoCurricular);
        planoCurricular.getDisciplinasCurriculars().add(this);
        return this;
    }

    public DisciplinaCurricular removePlanosCurricular(PlanoCurricular planoCurricular) {
        this.planosCurriculars.remove(planoCurricular);
        planoCurricular.getDisciplinasCurriculars().remove(this);
        return this;
    }

    public Disciplina getDisciplina() {
        return this.disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public DisciplinaCurricular disciplina(Disciplina disciplina) {
        this.setDisciplina(disciplina);
        return this;
    }

    public DisciplinaCurricular getReferencia() {
        return this.referencia;
    }

    public void setReferencia(DisciplinaCurricular disciplinaCurricular) {
        this.referencia = disciplinaCurricular;
    }

    public DisciplinaCurricular referencia(DisciplinaCurricular disciplinaCurricular) {
        this.setReferencia(disciplinaCurricular);
        return this;
    }

    public Set<EstadoDisciplinaCurricular> getEstados() {
        return this.estados;
    }

    public void setEstados(Set<EstadoDisciplinaCurricular> estadoDisciplinaCurriculars) {
        if (this.estados != null) {
            this.estados.forEach(i -> i.removeDisciplinasCurriculars(this));
        }
        if (estadoDisciplinaCurriculars != null) {
            estadoDisciplinaCurriculars.forEach(i -> i.addDisciplinasCurriculars(this));
        }
        this.estados = estadoDisciplinaCurriculars;
    }

    public DisciplinaCurricular estados(Set<EstadoDisciplinaCurricular> estadoDisciplinaCurriculars) {
        this.setEstados(estadoDisciplinaCurriculars);
        return this;
    }

    public DisciplinaCurricular addEstados(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.estados.add(estadoDisciplinaCurricular);
        estadoDisciplinaCurricular.getDisciplinasCurriculars().add(this);
        return this;
    }

    public DisciplinaCurricular removeEstados(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.estados.remove(estadoDisciplinaCurricular);
        estadoDisciplinaCurricular.getDisciplinasCurriculars().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisciplinaCurricular)) {
            return false;
        }
        return id != null && id.equals(((DisciplinaCurricular) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisciplinaCurricular{" +
            "id=" + getId() +
            ", uniqueDisciplinaCurricular='" + getUniqueDisciplinaCurricular() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", cargaSemanal=" + getCargaSemanal() +
            ", isTerminal='" + getIsTerminal() + "'" +
            ", mediaParaExame=" + getMediaParaExame() +
            ", mediaParaRecurso=" + getMediaParaRecurso() +
            ", mediaParaExameEspecial=" + getMediaParaExameEspecial() +
            ", mediaParaDespensar=" + getMediaParaDespensar() +
            "}";
    }
}
