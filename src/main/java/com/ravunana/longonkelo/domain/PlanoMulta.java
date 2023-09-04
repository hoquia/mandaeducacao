package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.MetodoAplicacaoMulta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlanoMulta.
 */
@Entity
@Table(name = "plano_multa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoMulta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    @NotNull
    @Min(value = 1)
    @Max(value = 31)
    @Column(name = "dia_aplicacao_multa", nullable = false)
    private Integer diaAplicacaoMulta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_aplicacao_multa", nullable = false)
    private MetodoAplicacaoMulta metodoAplicacaoMulta;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "taxa_multa", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxaMulta;

    @Column(name = "is_taxa_multa_percentual")
    private Boolean isTaxaMultaPercentual;

    @Min(value = 1)
    @Max(value = 31)
    @Column(name = "dia_aplicacao_juro")
    private Integer diaAplicacaoJuro;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_aplicacao_juro")
    private MetodoAplicacaoMulta metodoAplicacaoJuro;

    @DecimalMin(value = "0")
    @Column(name = "taxa_juro", precision = 21, scale = 2)
    private BigDecimal taxaJuro;

    @Column(name = "is_taxa_juro_percentual")
    private Boolean isTaxaJuroPercentual;

    @Min(value = 0)
    @Column(name = "aumentar_juro_em_dias")
    private Integer aumentarJuroEmDias;

    @Column(name = "is_ativo")
    private Boolean isAtivo;

    @OneToMany(mappedBy = "planoMulta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "emolumentos", "planoMulta", "planosDescontos" }, allowSetters = true)
    private Set<CategoriaEmolumento> categoriaEmolumentos = new HashSet<>();

    @OneToMany(mappedBy = "planoMulta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "itemFacturas", "emolumentos", "precosEmolumentos", "categoria", "imposto", "referencia", "planoMulta" },
        allowSetters = true
    )
    private Set<Emolumento> emolumentos = new HashSet<>();

    @OneToMany(mappedBy = "planoMulta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "utilizador", "emolumento", "areaFormacao", "curso", "classe", "turno", "planoMulta" },
        allowSetters = true
    )
    private Set<PrecoEmolumento> precoEmolumentos = new HashSet<>();

    @ManyToOne
    private User utilizador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanoMulta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public PlanoMulta descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDiaAplicacaoMulta() {
        return this.diaAplicacaoMulta;
    }

    public PlanoMulta diaAplicacaoMulta(Integer diaAplicacaoMulta) {
        this.setDiaAplicacaoMulta(diaAplicacaoMulta);
        return this;
    }

    public void setDiaAplicacaoMulta(Integer diaAplicacaoMulta) {
        this.diaAplicacaoMulta = diaAplicacaoMulta;
    }

    public MetodoAplicacaoMulta getMetodoAplicacaoMulta() {
        return this.metodoAplicacaoMulta;
    }

    public PlanoMulta metodoAplicacaoMulta(MetodoAplicacaoMulta metodoAplicacaoMulta) {
        this.setMetodoAplicacaoMulta(metodoAplicacaoMulta);
        return this;
    }

    public void setMetodoAplicacaoMulta(MetodoAplicacaoMulta metodoAplicacaoMulta) {
        this.metodoAplicacaoMulta = metodoAplicacaoMulta;
    }

    public BigDecimal getTaxaMulta() {
        return this.taxaMulta;
    }

    public PlanoMulta taxaMulta(BigDecimal taxaMulta) {
        this.setTaxaMulta(taxaMulta);
        return this;
    }

    public void setTaxaMulta(BigDecimal taxaMulta) {
        this.taxaMulta = taxaMulta;
    }

    public Boolean getIsTaxaMultaPercentual() {
        return this.isTaxaMultaPercentual;
    }

    public PlanoMulta isTaxaMultaPercentual(Boolean isTaxaMultaPercentual) {
        this.setIsTaxaMultaPercentual(isTaxaMultaPercentual);
        return this;
    }

    public void setIsTaxaMultaPercentual(Boolean isTaxaMultaPercentual) {
        this.isTaxaMultaPercentual = isTaxaMultaPercentual;
    }

    public Integer getDiaAplicacaoJuro() {
        return this.diaAplicacaoJuro;
    }

    public PlanoMulta diaAplicacaoJuro(Integer diaAplicacaoJuro) {
        this.setDiaAplicacaoJuro(diaAplicacaoJuro);
        return this;
    }

    public void setDiaAplicacaoJuro(Integer diaAplicacaoJuro) {
        this.diaAplicacaoJuro = diaAplicacaoJuro;
    }

    public MetodoAplicacaoMulta getMetodoAplicacaoJuro() {
        return this.metodoAplicacaoJuro;
    }

    public PlanoMulta metodoAplicacaoJuro(MetodoAplicacaoMulta metodoAplicacaoJuro) {
        this.setMetodoAplicacaoJuro(metodoAplicacaoJuro);
        return this;
    }

    public void setMetodoAplicacaoJuro(MetodoAplicacaoMulta metodoAplicacaoJuro) {
        this.metodoAplicacaoJuro = metodoAplicacaoJuro;
    }

    public BigDecimal getTaxaJuro() {
        return this.taxaJuro;
    }

    public PlanoMulta taxaJuro(BigDecimal taxaJuro) {
        this.setTaxaJuro(taxaJuro);
        return this;
    }

    public void setTaxaJuro(BigDecimal taxaJuro) {
        this.taxaJuro = taxaJuro;
    }

    public Boolean getIsTaxaJuroPercentual() {
        return this.isTaxaJuroPercentual;
    }

    public PlanoMulta isTaxaJuroPercentual(Boolean isTaxaJuroPercentual) {
        this.setIsTaxaJuroPercentual(isTaxaJuroPercentual);
        return this;
    }

    public void setIsTaxaJuroPercentual(Boolean isTaxaJuroPercentual) {
        this.isTaxaJuroPercentual = isTaxaJuroPercentual;
    }

    public Integer getAumentarJuroEmDias() {
        return this.aumentarJuroEmDias;
    }

    public PlanoMulta aumentarJuroEmDias(Integer aumentarJuroEmDias) {
        this.setAumentarJuroEmDias(aumentarJuroEmDias);
        return this;
    }

    public void setAumentarJuroEmDias(Integer aumentarJuroEmDias) {
        this.aumentarJuroEmDias = aumentarJuroEmDias;
    }

    public Boolean getIsAtivo() {
        return this.isAtivo;
    }

    public PlanoMulta isAtivo(Boolean isAtivo) {
        this.setIsAtivo(isAtivo);
        return this;
    }

    public void setIsAtivo(Boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public Set<CategoriaEmolumento> getCategoriaEmolumentos() {
        return this.categoriaEmolumentos;
    }

    public void setCategoriaEmolumentos(Set<CategoriaEmolumento> categoriaEmolumentos) {
        if (this.categoriaEmolumentos != null) {
            this.categoriaEmolumentos.forEach(i -> i.setPlanoMulta(null));
        }
        if (categoriaEmolumentos != null) {
            categoriaEmolumentos.forEach(i -> i.setPlanoMulta(this));
        }
        this.categoriaEmolumentos = categoriaEmolumentos;
    }

    public PlanoMulta categoriaEmolumentos(Set<CategoriaEmolumento> categoriaEmolumentos) {
        this.setCategoriaEmolumentos(categoriaEmolumentos);
        return this;
    }

    public PlanoMulta addCategoriaEmolumento(CategoriaEmolumento categoriaEmolumento) {
        this.categoriaEmolumentos.add(categoriaEmolumento);
        categoriaEmolumento.setPlanoMulta(this);
        return this;
    }

    public PlanoMulta removeCategoriaEmolumento(CategoriaEmolumento categoriaEmolumento) {
        this.categoriaEmolumentos.remove(categoriaEmolumento);
        categoriaEmolumento.setPlanoMulta(null);
        return this;
    }

    public Set<Emolumento> getEmolumentos() {
        return this.emolumentos;
    }

    public void setEmolumentos(Set<Emolumento> emolumentos) {
        if (this.emolumentos != null) {
            this.emolumentos.forEach(i -> i.setPlanoMulta(null));
        }
        if (emolumentos != null) {
            emolumentos.forEach(i -> i.setPlanoMulta(this));
        }
        this.emolumentos = emolumentos;
    }

    public PlanoMulta emolumentos(Set<Emolumento> emolumentos) {
        this.setEmolumentos(emolumentos);
        return this;
    }

    public PlanoMulta addEmolumento(Emolumento emolumento) {
        this.emolumentos.add(emolumento);
        emolumento.setPlanoMulta(this);
        return this;
    }

    public PlanoMulta removeEmolumento(Emolumento emolumento) {
        this.emolumentos.remove(emolumento);
        emolumento.setPlanoMulta(null);
        return this;
    }

    public Set<PrecoEmolumento> getPrecoEmolumentos() {
        return this.precoEmolumentos;
    }

    public void setPrecoEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        if (this.precoEmolumentos != null) {
            this.precoEmolumentos.forEach(i -> i.setPlanoMulta(null));
        }
        if (precoEmolumentos != null) {
            precoEmolumentos.forEach(i -> i.setPlanoMulta(this));
        }
        this.precoEmolumentos = precoEmolumentos;
    }

    public PlanoMulta precoEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        this.setPrecoEmolumentos(precoEmolumentos);
        return this;
    }

    public PlanoMulta addPrecoEmolumento(PrecoEmolumento precoEmolumento) {
        this.precoEmolumentos.add(precoEmolumento);
        precoEmolumento.setPlanoMulta(this);
        return this;
    }

    public PlanoMulta removePrecoEmolumento(PrecoEmolumento precoEmolumento) {
        this.precoEmolumentos.remove(precoEmolumento);
        precoEmolumento.setPlanoMulta(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public PlanoMulta utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoMulta)) {
            return false;
        }
        return id != null && id.equals(((PlanoMulta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoMulta{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", diaAplicacaoMulta=" + getDiaAplicacaoMulta() +
            ", metodoAplicacaoMulta='" + getMetodoAplicacaoMulta() + "'" +
            ", taxaMulta=" + getTaxaMulta() +
            ", isTaxaMultaPercentual='" + getIsTaxaMultaPercentual() + "'" +
            ", diaAplicacaoJuro=" + getDiaAplicacaoJuro() +
            ", metodoAplicacaoJuro='" + getMetodoAplicacaoJuro() + "'" +
            ", taxaJuro=" + getTaxaJuro() +
            ", isTaxaJuroPercentual='" + getIsTaxaJuroPercentual() + "'" +
            ", aumentarJuroEmDias=" + getAumentarJuroEmDias() +
            ", isAtivo='" + getIsAtivo() + "'" +
            "}";
    }
}
