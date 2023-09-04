package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Disciplina.
 */
@Entity
@Table(name = "disciplina")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "disciplina")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "disciplina" }, allowSetters = true)
    private Set<ResponsavelDisciplina> responsaveis = new HashSet<>();

    @OneToMany(mappedBy = "disciplina")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Disciplina id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Disciplina codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public Disciplina nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Disciplina descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<ResponsavelDisciplina> getResponsaveis() {
        return this.responsaveis;
    }

    public void setResponsaveis(Set<ResponsavelDisciplina> responsavelDisciplinas) {
        if (this.responsaveis != null) {
            this.responsaveis.forEach(i -> i.setDisciplina(null));
        }
        if (responsavelDisciplinas != null) {
            responsavelDisciplinas.forEach(i -> i.setDisciplina(this));
        }
        this.responsaveis = responsavelDisciplinas;
    }

    public Disciplina responsaveis(Set<ResponsavelDisciplina> responsavelDisciplinas) {
        this.setResponsaveis(responsavelDisciplinas);
        return this;
    }

    public Disciplina addResponsaveis(ResponsavelDisciplina responsavelDisciplina) {
        this.responsaveis.add(responsavelDisciplina);
        responsavelDisciplina.setDisciplina(this);
        return this;
    }

    public Disciplina removeResponsaveis(ResponsavelDisciplina responsavelDisciplina) {
        this.responsaveis.remove(responsavelDisciplina);
        responsavelDisciplina.setDisciplina(null);
        return this;
    }

    public Set<DisciplinaCurricular> getDisciplinaCurriculars() {
        return this.disciplinaCurriculars;
    }

    public void setDisciplinaCurriculars(Set<DisciplinaCurricular> disciplinaCurriculars) {
        if (this.disciplinaCurriculars != null) {
            this.disciplinaCurriculars.forEach(i -> i.setDisciplina(null));
        }
        if (disciplinaCurriculars != null) {
            disciplinaCurriculars.forEach(i -> i.setDisciplina(this));
        }
        this.disciplinaCurriculars = disciplinaCurriculars;
    }

    public Disciplina disciplinaCurriculars(Set<DisciplinaCurricular> disciplinaCurriculars) {
        this.setDisciplinaCurriculars(disciplinaCurriculars);
        return this;
    }

    public Disciplina addDisciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinaCurriculars.add(disciplinaCurricular);
        disciplinaCurricular.setDisciplina(this);
        return this;
    }

    public Disciplina removeDisciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinaCurriculars.remove(disciplinaCurricular);
        disciplinaCurricular.setDisciplina(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disciplina)) {
            return false;
        }
        return id != null && id.equals(((Disciplina) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Disciplina{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
