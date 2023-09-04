package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.Suspensao;
import com.ravunana.longonkelo.domain.enumeration.UnidadeDuracao;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MedidaDisciplinar.
 */
@Entity
@Table(name = "medida_disciplinar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedidaDisciplinar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "periodo", nullable = false)
    private UnidadeDuracao periodo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "suspensao", nullable = false)
    private Suspensao suspensao;

    @Min(value = 0)
    @Column(name = "tempo")
    private Integer tempo;

    @OneToMany(mappedBy = "medidaDisciplinar")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "categoriaOcorrencias", "ocorrencias", "encaminhar", "referencia", "medidaDisciplinar" },
        allowSetters = true
    )
    private Set<CategoriaOcorrencia> categoriaOcorrencias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MedidaDisciplinar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public MedidaDisciplinar descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UnidadeDuracao getPeriodo() {
        return this.periodo;
    }

    public MedidaDisciplinar periodo(UnidadeDuracao periodo) {
        this.setPeriodo(periodo);
        return this;
    }

    public void setPeriodo(UnidadeDuracao periodo) {
        this.periodo = periodo;
    }

    public Suspensao getSuspensao() {
        return this.suspensao;
    }

    public MedidaDisciplinar suspensao(Suspensao suspensao) {
        this.setSuspensao(suspensao);
        return this;
    }

    public void setSuspensao(Suspensao suspensao) {
        this.suspensao = suspensao;
    }

    public Integer getTempo() {
        return this.tempo;
    }

    public MedidaDisciplinar tempo(Integer tempo) {
        this.setTempo(tempo);
        return this;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public Set<CategoriaOcorrencia> getCategoriaOcorrencias() {
        return this.categoriaOcorrencias;
    }

    public void setCategoriaOcorrencias(Set<CategoriaOcorrencia> categoriaOcorrencias) {
        if (this.categoriaOcorrencias != null) {
            this.categoriaOcorrencias.forEach(i -> i.setMedidaDisciplinar(null));
        }
        if (categoriaOcorrencias != null) {
            categoriaOcorrencias.forEach(i -> i.setMedidaDisciplinar(this));
        }
        this.categoriaOcorrencias = categoriaOcorrencias;
    }

    public MedidaDisciplinar categoriaOcorrencias(Set<CategoriaOcorrencia> categoriaOcorrencias) {
        this.setCategoriaOcorrencias(categoriaOcorrencias);
        return this;
    }

    public MedidaDisciplinar addCategoriaOcorrencia(CategoriaOcorrencia categoriaOcorrencia) {
        this.categoriaOcorrencias.add(categoriaOcorrencia);
        categoriaOcorrencia.setMedidaDisciplinar(this);
        return this;
    }

    public MedidaDisciplinar removeCategoriaOcorrencia(CategoriaOcorrencia categoriaOcorrencia) {
        this.categoriaOcorrencias.remove(categoriaOcorrencia);
        categoriaOcorrencia.setMedidaDisciplinar(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedidaDisciplinar)) {
            return false;
        }
        return id != null && id.equals(((MedidaDisciplinar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedidaDisciplinar{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", periodo='" + getPeriodo() + "'" +
            ", suspensao='" + getSuspensao() + "'" +
            ", tempo=" + getTempo() +
            "}";
    }
}
