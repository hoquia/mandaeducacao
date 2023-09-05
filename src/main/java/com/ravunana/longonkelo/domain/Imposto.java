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
 * A Imposto.
 */
@Entity
@Table(name = "imposto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Imposto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    @Column(name = "pais")
    private String pais;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "taxa", nullable = false)
    private Double taxa;

    @Column(name = "is_retencao")
    private Boolean isRetencao;

    @Column(name = "motivo_descricao")
    private String motivoDescricao;

    @Column(name = "motivo_codigo")
    private String motivoCodigo;

    @OneToMany(mappedBy = "imposto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "itemFacturas", "emolumentos", "precosEmolumentos", "categoria", "imposto", "referencia", "planoMulta" },
        allowSetters = true
    )
    private Set<Emolumento> emolumentos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem tipoImposto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem codigoImposto;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem motivoIsencaoCodigo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem motivoIsencaoDescricao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Imposto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Imposto descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPais() {
        return this.pais;
    }

    public Imposto pais(String pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Double getTaxa() {
        return this.taxa;
    }

    public Imposto taxa(Double taxa) {
        this.setTaxa(taxa);
        return this;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public Boolean getIsRetencao() {
        return this.isRetencao;
    }

    public Imposto isRetencao(Boolean isRetencao) {
        this.setIsRetencao(isRetencao);
        return this;
    }

    public void setIsRetencao(Boolean isRetencao) {
        this.isRetencao = isRetencao;
    }

    public String getMotivoDescricao() {
        return this.motivoDescricao;
    }

    public Imposto motivoDescricao(String motivoDescricao) {
        this.setMotivoDescricao(motivoDescricao);
        return this;
    }

    public void setMotivoDescricao(String motivoDescricao) {
        this.motivoDescricao = motivoDescricao;
    }

    public String getMotivoCodigo() {
        return this.motivoCodigo;
    }

    public Imposto motivoCodigo(String motivoCodigo) {
        this.setMotivoCodigo(motivoCodigo);
        return this;
    }

    public void setMotivoCodigo(String motivoCodigo) {
        this.motivoCodigo = motivoCodigo;
    }

    public Set<Emolumento> getEmolumentos() {
        return this.emolumentos;
    }

    public void setEmolumentos(Set<Emolumento> emolumentos) {
        if (this.emolumentos != null) {
            this.emolumentos.forEach(i -> i.setImposto(null));
        }
        if (emolumentos != null) {
            emolumentos.forEach(i -> i.setImposto(this));
        }
        this.emolumentos = emolumentos;
    }

    public Imposto emolumentos(Set<Emolumento> emolumentos) {
        this.setEmolumentos(emolumentos);
        return this;
    }

    public Imposto addEmolumento(Emolumento emolumento) {
        this.emolumentos.add(emolumento);
        emolumento.setImposto(this);
        return this;
    }

    public Imposto removeEmolumento(Emolumento emolumento) {
        this.emolumentos.remove(emolumento);
        emolumento.setImposto(null);
        return this;
    }

    public LookupItem getTipoImposto() {
        return this.tipoImposto;
    }

    public void setTipoImposto(LookupItem lookupItem) {
        this.tipoImposto = lookupItem;
    }

    public Imposto tipoImposto(LookupItem lookupItem) {
        this.setTipoImposto(lookupItem);
        return this;
    }

    public LookupItem getCodigoImposto() {
        return this.codigoImposto;
    }

    public void setCodigoImposto(LookupItem lookupItem) {
        this.codigoImposto = lookupItem;
    }

    public Imposto codigoImposto(LookupItem lookupItem) {
        this.setCodigoImposto(lookupItem);
        return this;
    }

    public LookupItem getMotivoIsencaoCodigo() {
        return this.motivoIsencaoCodigo;
    }

    public void setMotivoIsencaoCodigo(LookupItem lookupItem) {
        this.motivoIsencaoCodigo = lookupItem;
    }

    public Imposto motivoIsencaoCodigo(LookupItem lookupItem) {
        this.setMotivoIsencaoCodigo(lookupItem);
        return this;
    }

    public LookupItem getMotivoIsencaoDescricao() {
        return this.motivoIsencaoDescricao;
    }

    public void setMotivoIsencaoDescricao(LookupItem lookupItem) {
        this.motivoIsencaoDescricao = lookupItem;
    }

    public Imposto motivoIsencaoDescricao(LookupItem lookupItem) {
        this.setMotivoIsencaoDescricao(lookupItem);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Imposto)) {
            return false;
        }
        return id != null && id.equals(((Imposto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Imposto{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", pais='" + getPais() + "'" +
            ", taxa=" + getTaxa() +
            ", isRetencao='" + getIsRetencao() + "'" +
            ", motivoDescricao='" + getMotivoDescricao() + "'" +
            ", motivoCodigo='" + getMotivoCodigo() + "'" +
            "}";
    }
}
