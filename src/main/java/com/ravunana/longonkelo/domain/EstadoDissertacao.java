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
 * A EstadoDissertacao.
 */
@Entity
@Table(name = "estado_dissertacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoDissertacao implements Serializable {

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

    @Min(value = 0)
    @Column(name = "etapa")
    private Integer etapa;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "estado")
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

    public EstadoDissertacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public EstadoDissertacao codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public EstadoDissertacao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getEtapa() {
        return this.etapa;
    }

    public EstadoDissertacao etapa(Integer etapa) {
        this.setEtapa(etapa);
        return this;
    }

    public void setEtapa(Integer etapa) {
        this.etapa = etapa;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public EstadoDissertacao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<DissertacaoFinalCurso> getDissertacaoFinalCursos() {
        return this.dissertacaoFinalCursos;
    }

    public void setDissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        if (this.dissertacaoFinalCursos != null) {
            this.dissertacaoFinalCursos.forEach(i -> i.setEstado(null));
        }
        if (dissertacaoFinalCursos != null) {
            dissertacaoFinalCursos.forEach(i -> i.setEstado(this));
        }
        this.dissertacaoFinalCursos = dissertacaoFinalCursos;
    }

    public EstadoDissertacao dissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        this.setDissertacaoFinalCursos(dissertacaoFinalCursos);
        return this;
    }

    public EstadoDissertacao addDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.add(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setEstado(this);
        return this;
    }

    public EstadoDissertacao removeDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.remove(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setEstado(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoDissertacao)) {
            return false;
        }
        return id != null && id.equals(((EstadoDissertacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoDissertacao{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", etapa=" + getEtapa() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
