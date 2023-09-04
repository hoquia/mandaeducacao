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
 * A CampoActuacaoDissertacao.
 */
@Entity
@Table(name = "campo_actuacao_dissertacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CampoActuacaoDissertacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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

    @ManyToMany
    @JoinTable(
        name = "rel_campo_actuacao_dissertacao__cursos",
        joinColumns = @JoinColumn(name = "campo_actuacao_dissertacao_id"),
        inverseJoinColumns = @JoinColumn(name = "cursos_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "planoCurriculars", "responsaveis", "precoEmolumentos", "areaFormacao", "camposActuacaos" },
        allowSetters = true
    )
    private Set<Curso> cursos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CampoActuacaoDissertacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public CampoActuacaoDissertacao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public CampoActuacaoDissertacao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getIsActivo() {
        return this.isActivo;
    }

    public CampoActuacaoDissertacao isActivo(Boolean isActivo) {
        this.setIsActivo(isActivo);
        return this;
    }

    public void setIsActivo(Boolean isActivo) {
        this.isActivo = isActivo;
    }

    public Set<Curso> getCursos() {
        return this.cursos;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    public CampoActuacaoDissertacao cursos(Set<Curso> cursos) {
        this.setCursos(cursos);
        return this;
    }

    public CampoActuacaoDissertacao addCursos(Curso curso) {
        this.cursos.add(curso);
        curso.getCamposActuacaos().add(this);
        return this;
    }

    public CampoActuacaoDissertacao removeCursos(Curso curso) {
        this.cursos.remove(curso);
        curso.getCamposActuacaos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampoActuacaoDissertacao)) {
            return false;
        }
        return id != null && id.equals(((CampoActuacaoDissertacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampoActuacaoDissertacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", isActivo='" + getIsActivo() + "'" +
            "}";
    }
}
