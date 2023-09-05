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
 * A Turno.
 */
@Entity
@Table(name = "turno")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @OneToMany(mappedBy = "turno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "turno" }, allowSetters = true)
    private Set<ResponsavelTurno> responsavelTurnos = new HashSet<>();

    @OneToMany(mappedBy = "turno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "utilizador", "emolumento", "areaFormacao", "curso", "classe", "turno", "planoMulta" },
        allowSetters = true
    )
    private Set<PrecoEmolumento> precoEmolumentos = new HashSet<>();

    @OneToMany(mappedBy = "turno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "horarios", "turno" }, allowSetters = true)
    private Set<PeriodoHorario> periodoHorarios = new HashSet<>();

    @OneToMany(mappedBy = "turno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Turma> turmas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Turno id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Turno codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public Turno nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<ResponsavelTurno> getResponsavelTurnos() {
        return this.responsavelTurnos;
    }

    public void setResponsavelTurnos(Set<ResponsavelTurno> responsavelTurnos) {
        if (this.responsavelTurnos != null) {
            this.responsavelTurnos.forEach(i -> i.setTurno(null));
        }
        if (responsavelTurnos != null) {
            responsavelTurnos.forEach(i -> i.setTurno(this));
        }
        this.responsavelTurnos = responsavelTurnos;
    }

    public Turno responsavelTurnos(Set<ResponsavelTurno> responsavelTurnos) {
        this.setResponsavelTurnos(responsavelTurnos);
        return this;
    }

    public Turno addResponsavelTurno(ResponsavelTurno responsavelTurno) {
        this.responsavelTurnos.add(responsavelTurno);
        responsavelTurno.setTurno(this);
        return this;
    }

    public Turno removeResponsavelTurno(ResponsavelTurno responsavelTurno) {
        this.responsavelTurnos.remove(responsavelTurno);
        responsavelTurno.setTurno(null);
        return this;
    }

    public Set<PrecoEmolumento> getPrecoEmolumentos() {
        return this.precoEmolumentos;
    }

    public void setPrecoEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        if (this.precoEmolumentos != null) {
            this.precoEmolumentos.forEach(i -> i.setTurno(null));
        }
        if (precoEmolumentos != null) {
            precoEmolumentos.forEach(i -> i.setTurno(this));
        }
        this.precoEmolumentos = precoEmolumentos;
    }

    public Turno precoEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        this.setPrecoEmolumentos(precoEmolumentos);
        return this;
    }

    public Turno addPrecoEmolumento(PrecoEmolumento precoEmolumento) {
        this.precoEmolumentos.add(precoEmolumento);
        precoEmolumento.setTurno(this);
        return this;
    }

    public Turno removePrecoEmolumento(PrecoEmolumento precoEmolumento) {
        this.precoEmolumentos.remove(precoEmolumento);
        precoEmolumento.setTurno(null);
        return this;
    }

    public Set<PeriodoHorario> getPeriodoHorarios() {
        return this.periodoHorarios;
    }

    public void setPeriodoHorarios(Set<PeriodoHorario> periodoHorarios) {
        if (this.periodoHorarios != null) {
            this.periodoHorarios.forEach(i -> i.setTurno(null));
        }
        if (periodoHorarios != null) {
            periodoHorarios.forEach(i -> i.setTurno(this));
        }
        this.periodoHorarios = periodoHorarios;
    }

    public Turno periodoHorarios(Set<PeriodoHorario> periodoHorarios) {
        this.setPeriodoHorarios(periodoHorarios);
        return this;
    }

    public Turno addPeriodoHorario(PeriodoHorario periodoHorario) {
        this.periodoHorarios.add(periodoHorario);
        periodoHorario.setTurno(this);
        return this;
    }

    public Turno removePeriodoHorario(PeriodoHorario periodoHorario) {
        this.periodoHorarios.remove(periodoHorario);
        periodoHorario.setTurno(null);
        return this;
    }

    public Set<Turma> getTurmas() {
        return this.turmas;
    }

    public void setTurmas(Set<Turma> turmas) {
        if (this.turmas != null) {
            this.turmas.forEach(i -> i.setTurno(null));
        }
        if (turmas != null) {
            turmas.forEach(i -> i.setTurno(this));
        }
        this.turmas = turmas;
    }

    public Turno turmas(Set<Turma> turmas) {
        this.setTurmas(turmas);
        return this;
    }

    public Turno addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.setTurno(this);
        return this;
    }

    public Turno removeTurma(Turma turma) {
        this.turmas.remove(turma);
        turma.setTurno(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Turno)) {
            return false;
        }
        return id != null && id.equals(((Turno) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Turno{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
