package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LookupItem.
 */
@Entity
@Table(name = "lookup_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LookupItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Min(value = 0)
    @Column(name = "ordem")
    private Integer ordem;

    @Column(name = "is_sistema")
    private Boolean isSistema;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lookupItems" }, allowSetters = true)
    private Lookup lookup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LookupItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public LookupItem codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getOrdem() {
        return this.ordem;
    }

    public LookupItem ordem(Integer ordem) {
        this.setOrdem(ordem);
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getIsSistema() {
        return this.isSistema;
    }

    public LookupItem isSistema(Boolean isSistema) {
        this.setIsSistema(isSistema);
        return this;
    }

    public void setIsSistema(Boolean isSistema) {
        this.isSistema = isSistema;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public LookupItem descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Lookup getLookup() {
        return this.lookup;
    }

    public void setLookup(Lookup lookup) {
        this.lookup = lookup;
    }

    public LookupItem lookup(Lookup lookup) {
        this.setLookup(lookup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LookupItem)) {
            return false;
        }
        return id != null && id.equals(((LookupItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LookupItem{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", ordem=" + getOrdem() +
            ", isSistema='" + getIsSistema() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
