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
 * A Classe.
 */
@Entity
@Table(name = "classe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Classe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    @OneToMany(mappedBy = "classe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "turmas", "utilizador", "classe", "curso", "disciplinasCurriculars" }, allowSetters = true)
    private Set<PlanoCurricular> planoCurriculars = new HashSet<>();

    @OneToMany(mappedBy = "classe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "utilizador", "emolumento", "areaFormacao", "curso", "classe", "turno", "planoMulta" },
        allowSetters = true
    )
    private Set<PrecoEmolumento> precoEmolumentos = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_classe__nives_ensino",
        joinColumns = @JoinColumn(name = "classe_id"),
        inverseJoinColumns = @JoinColumn(name = "nives_ensino_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nivelEnsinos", "areaFormacaos", "referencia", "anoLectivos", "classes" }, allowSetters = true)
    private Set<NivelEnsino> nivesEnsinos = new HashSet<>();

    @ManyToMany(mappedBy = "classes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilizador", "classes" }, allowSetters = true)
    private Set<PeriodoLancamentoNota> periodosLancamentoNotas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Classe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Classe descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<PlanoCurricular> getPlanoCurriculars() {
        return this.planoCurriculars;
    }

    public void setPlanoCurriculars(Set<PlanoCurricular> planoCurriculars) {
        if (this.planoCurriculars != null) {
            this.planoCurriculars.forEach(i -> i.setClasse(null));
        }
        if (planoCurriculars != null) {
            planoCurriculars.forEach(i -> i.setClasse(this));
        }
        this.planoCurriculars = planoCurriculars;
    }

    public Classe planoCurriculars(Set<PlanoCurricular> planoCurriculars) {
        this.setPlanoCurriculars(planoCurriculars);
        return this;
    }

    public Classe addPlanoCurricular(PlanoCurricular planoCurricular) {
        this.planoCurriculars.add(planoCurricular);
        planoCurricular.setClasse(this);
        return this;
    }

    public Classe removePlanoCurricular(PlanoCurricular planoCurricular) {
        this.planoCurriculars.remove(planoCurricular);
        planoCurricular.setClasse(null);
        return this;
    }

    public Set<PrecoEmolumento> getPrecoEmolumentos() {
        return this.precoEmolumentos;
    }

    public void setPrecoEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        if (this.precoEmolumentos != null) {
            this.precoEmolumentos.forEach(i -> i.setClasse(null));
        }
        if (precoEmolumentos != null) {
            precoEmolumentos.forEach(i -> i.setClasse(this));
        }
        this.precoEmolumentos = precoEmolumentos;
    }

    public Classe precoEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        this.setPrecoEmolumentos(precoEmolumentos);
        return this;
    }

    public Classe addPrecoEmolumento(PrecoEmolumento precoEmolumento) {
        this.precoEmolumentos.add(precoEmolumento);
        precoEmolumento.setClasse(this);
        return this;
    }

    public Classe removePrecoEmolumento(PrecoEmolumento precoEmolumento) {
        this.precoEmolumentos.remove(precoEmolumento);
        precoEmolumento.setClasse(null);
        return this;
    }

    public Set<NivelEnsino> getNivesEnsinos() {
        return this.nivesEnsinos;
    }

    public void setNivesEnsinos(Set<NivelEnsino> nivelEnsinos) {
        this.nivesEnsinos = nivelEnsinos;
    }

    public Classe nivesEnsinos(Set<NivelEnsino> nivelEnsinos) {
        this.setNivesEnsinos(nivelEnsinos);
        return this;
    }

    public Classe addNivesEnsino(NivelEnsino nivelEnsino) {
        this.nivesEnsinos.add(nivelEnsino);
        nivelEnsino.getClasses().add(this);
        return this;
    }

    public Classe removeNivesEnsino(NivelEnsino nivelEnsino) {
        this.nivesEnsinos.remove(nivelEnsino);
        nivelEnsino.getClasses().remove(this);
        return this;
    }

    public Set<PeriodoLancamentoNota> getPeriodosLancamentoNotas() {
        return this.periodosLancamentoNotas;
    }

    public void setPeriodosLancamentoNotas(Set<PeriodoLancamentoNota> periodoLancamentoNotas) {
        if (this.periodosLancamentoNotas != null) {
            this.periodosLancamentoNotas.forEach(i -> i.removeClasse(this));
        }
        if (periodoLancamentoNotas != null) {
            periodoLancamentoNotas.forEach(i -> i.addClasse(this));
        }
        this.periodosLancamentoNotas = periodoLancamentoNotas;
    }

    public Classe periodosLancamentoNotas(Set<PeriodoLancamentoNota> periodoLancamentoNotas) {
        this.setPeriodosLancamentoNotas(periodoLancamentoNotas);
        return this;
    }

    public Classe addPeriodosLancamentoNota(PeriodoLancamentoNota periodoLancamentoNota) {
        this.periodosLancamentoNotas.add(periodoLancamentoNota);
        periodoLancamentoNota.getClasses().add(this);
        return this;
    }

    public Classe removePeriodosLancamentoNota(PeriodoLancamentoNota periodoLancamentoNota) {
        this.periodosLancamentoNotas.remove(periodoLancamentoNota);
        periodoLancamentoNota.getClasses().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classe)) {
            return false;
        }
        return id != null && id.equals(((Classe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Classe{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
