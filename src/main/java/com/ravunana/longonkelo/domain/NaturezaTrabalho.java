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
 * A NaturezaTrabalho.
 */
@Entity
@Table(name = "natureza_trabalho")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NaturezaTrabalho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "is_activo")
    private Boolean isActivo;

    @OneToMany(mappedBy = "natureza")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "orientador", "especialidade", "discente", "estado", "natureza" },
        allowSetters = true
    )
    private Set<DissertacaoFinalCurso> dissertacaoFinalCursos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NaturezaTrabalho id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public NaturezaTrabalho nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public NaturezaTrabalho descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getIsActivo() {
        return this.isActivo;
    }

    public NaturezaTrabalho isActivo(Boolean isActivo) {
        this.setIsActivo(isActivo);
        return this;
    }

    public void setIsActivo(Boolean isActivo) {
        this.isActivo = isActivo;
    }

    public Set<DissertacaoFinalCurso> getDissertacaoFinalCursos() {
        return this.dissertacaoFinalCursos;
    }

    public void setDissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        if (this.dissertacaoFinalCursos != null) {
            this.dissertacaoFinalCursos.forEach(i -> i.setNatureza(null));
        }
        if (dissertacaoFinalCursos != null) {
            dissertacaoFinalCursos.forEach(i -> i.setNatureza(this));
        }
        this.dissertacaoFinalCursos = dissertacaoFinalCursos;
    }

    public NaturezaTrabalho dissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        this.setDissertacaoFinalCursos(dissertacaoFinalCursos);
        return this;
    }

    public NaturezaTrabalho addDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.add(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setNatureza(this);
        return this;
    }

    public NaturezaTrabalho removeDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.remove(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setNatureza(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NaturezaTrabalho)) {
            return false;
        }
        return id != null && id.equals(((NaturezaTrabalho) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NaturezaTrabalho{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", isActivo='" + getIsActivo() + "'" +
            "}";
    }
}
