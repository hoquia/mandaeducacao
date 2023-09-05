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
 * A PeriodoHorario.
 */
@Entity
@Table(name = "periodo_horario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeriodoHorario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    @NotNull
    @Min(value = 1)
    @Column(name = "tempo", nullable = false)
    private Integer tempo;

    @NotNull
    @Column(name = "inicio", nullable = false)
    private String inicio;

    @NotNull
    @Column(name = "fim", nullable = false)
    private String fim;

    @OneToMany(mappedBy = "periodo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "horarios", "licaos", "anoLectivos", "utilizador", "turma", "referencia", "periodo", "docente", "disciplinaCurricular" },
        allowSetters = true
    )
    private Set<Horario> horarios = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "responsavelTurnos", "precoEmolumentos", "periodoHorarios", "turmas" }, allowSetters = true)
    private Turno turno;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PeriodoHorario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public PeriodoHorario descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getTempo() {
        return this.tempo;
    }

    public PeriodoHorario tempo(Integer tempo) {
        this.setTempo(tempo);
        return this;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public String getInicio() {
        return this.inicio;
    }

    public PeriodoHorario inicio(String inicio) {
        this.setInicio(inicio);
        return this;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return this.fim;
    }

    public PeriodoHorario fim(String fim) {
        this.setFim(fim);
        return this;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public Set<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        if (this.horarios != null) {
            this.horarios.forEach(i -> i.setPeriodo(null));
        }
        if (horarios != null) {
            horarios.forEach(i -> i.setPeriodo(this));
        }
        this.horarios = horarios;
    }

    public PeriodoHorario horarios(Set<Horario> horarios) {
        this.setHorarios(horarios);
        return this;
    }

    public PeriodoHorario addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setPeriodo(this);
        return this;
    }

    public PeriodoHorario removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setPeriodo(null);
        return this;
    }

    public Turno getTurno() {
        return this.turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public PeriodoHorario turno(Turno turno) {
        this.setTurno(turno);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodoHorario)) {
            return false;
        }
        return id != null && id.equals(((PeriodoHorario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodoHorario{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", tempo=" + getTempo() +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            "}";
    }
}
