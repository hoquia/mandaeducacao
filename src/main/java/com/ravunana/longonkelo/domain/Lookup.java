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
 * A Lookup.
 */
@Entity
@Table(name = "lookup")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Lookup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "is_sistema")
    private Boolean isSistema;

    @Column(name = "is_modificavel")
    private Boolean isModificavel;

    @OneToMany(mappedBy = "lookup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private Set<LookupItem> lookupItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lookup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Lookup codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public Lookup nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Lookup descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getIsSistema() {
        return this.isSistema;
    }

    public Lookup isSistema(Boolean isSistema) {
        this.setIsSistema(isSistema);
        return this;
    }

    public void setIsSistema(Boolean isSistema) {
        this.isSistema = isSistema;
    }

    public Boolean getIsModificavel() {
        return this.isModificavel;
    }

    public Lookup isModificavel(Boolean isModificavel) {
        this.setIsModificavel(isModificavel);
        return this;
    }

    public void setIsModificavel(Boolean isModificavel) {
        this.isModificavel = isModificavel;
    }

    public Set<LookupItem> getLookupItems() {
        return this.lookupItems;
    }

    public void setLookupItems(Set<LookupItem> lookupItems) {
        if (this.lookupItems != null) {
            this.lookupItems.forEach(i -> i.setLookup(null));
        }
        if (lookupItems != null) {
            lookupItems.forEach(i -> i.setLookup(this));
        }
        this.lookupItems = lookupItems;
    }

    public Lookup lookupItems(Set<LookupItem> lookupItems) {
        this.setLookupItems(lookupItems);
        return this;
    }

    public Lookup addLookupItems(LookupItem lookupItem) {
        this.lookupItems.add(lookupItem);
        lookupItem.setLookup(this);
        return this;
    }

    public Lookup removeLookupItems(LookupItem lookupItem) {
        this.lookupItems.remove(lookupItem);
        lookupItem.setLookup(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lookup)) {
            return false;
        }
        return id != null && id.equals(((Lookup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lookup{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", isSistema='" + getIsSistema() + "'" +
            ", isModificavel='" + getIsModificavel() + "'" +
            "}";
    }
}
