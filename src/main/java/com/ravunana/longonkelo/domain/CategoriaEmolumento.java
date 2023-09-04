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
 * A CategoriaEmolumento.
 */
@Entity
@Table(name = "categoria_emolumento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriaEmolumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "is_servico")
    private Boolean isServico;

    @Column(name = "cor")
    private String cor;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "is_isento_multa")
    private Boolean isIsentoMulta;

    @Column(name = "is_isento_juro")
    private Boolean isIsentoJuro;

    @OneToMany(mappedBy = "categoria")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "itemFacturas", "emolumentos", "precosEmolumentos", "categoria", "imposto", "referencia", "planoMulta" },
        allowSetters = true
    )
    private Set<Emolumento> emolumentos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "categoriaEmolumentos", "emolumentos", "precoEmolumentos", "utilizador" }, allowSetters = true)
    private PlanoMulta planoMulta;

    @ManyToMany(mappedBy = "categoriasEmolumentos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categoriasEmolumentos", "matriculas" }, allowSetters = true)
    private Set<PlanoDesconto> planosDescontos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CategoriaEmolumento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public CategoriaEmolumento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getIsServico() {
        return this.isServico;
    }

    public CategoriaEmolumento isServico(Boolean isServico) {
        this.setIsServico(isServico);
        return this;
    }

    public void setIsServico(Boolean isServico) {
        this.isServico = isServico;
    }

    public String getCor() {
        return this.cor;
    }

    public CategoriaEmolumento cor(String cor) {
        this.setCor(cor);
        return this;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public CategoriaEmolumento descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getIsIsentoMulta() {
        return this.isIsentoMulta;
    }

    public CategoriaEmolumento isIsentoMulta(Boolean isIsentoMulta) {
        this.setIsIsentoMulta(isIsentoMulta);
        return this;
    }

    public void setIsIsentoMulta(Boolean isIsentoMulta) {
        this.isIsentoMulta = isIsentoMulta;
    }

    public Boolean getIsIsentoJuro() {
        return this.isIsentoJuro;
    }

    public CategoriaEmolumento isIsentoJuro(Boolean isIsentoJuro) {
        this.setIsIsentoJuro(isIsentoJuro);
        return this;
    }

    public void setIsIsentoJuro(Boolean isIsentoJuro) {
        this.isIsentoJuro = isIsentoJuro;
    }

    public Set<Emolumento> getEmolumentos() {
        return this.emolumentos;
    }

    public void setEmolumentos(Set<Emolumento> emolumentos) {
        if (this.emolumentos != null) {
            this.emolumentos.forEach(i -> i.setCategoria(null));
        }
        if (emolumentos != null) {
            emolumentos.forEach(i -> i.setCategoria(this));
        }
        this.emolumentos = emolumentos;
    }

    public CategoriaEmolumento emolumentos(Set<Emolumento> emolumentos) {
        this.setEmolumentos(emolumentos);
        return this;
    }

    public CategoriaEmolumento addEmolumento(Emolumento emolumento) {
        this.emolumentos.add(emolumento);
        emolumento.setCategoria(this);
        return this;
    }

    public CategoriaEmolumento removeEmolumento(Emolumento emolumento) {
        this.emolumentos.remove(emolumento);
        emolumento.setCategoria(null);
        return this;
    }

    public PlanoMulta getPlanoMulta() {
        return this.planoMulta;
    }

    public void setPlanoMulta(PlanoMulta planoMulta) {
        this.planoMulta = planoMulta;
    }

    public CategoriaEmolumento planoMulta(PlanoMulta planoMulta) {
        this.setPlanoMulta(planoMulta);
        return this;
    }

    public Set<PlanoDesconto> getPlanosDescontos() {
        return this.planosDescontos;
    }

    public void setPlanosDescontos(Set<PlanoDesconto> planoDescontos) {
        if (this.planosDescontos != null) {
            this.planosDescontos.forEach(i -> i.removeCategoriasEmolumento(this));
        }
        if (planoDescontos != null) {
            planoDescontos.forEach(i -> i.addCategoriasEmolumento(this));
        }
        this.planosDescontos = planoDescontos;
    }

    public CategoriaEmolumento planosDescontos(Set<PlanoDesconto> planoDescontos) {
        this.setPlanosDescontos(planoDescontos);
        return this;
    }

    public CategoriaEmolumento addPlanosDesconto(PlanoDesconto planoDesconto) {
        this.planosDescontos.add(planoDesconto);
        planoDesconto.getCategoriasEmolumentos().add(this);
        return this;
    }

    public CategoriaEmolumento removePlanosDesconto(PlanoDesconto planoDesconto) {
        this.planosDescontos.remove(planoDesconto);
        planoDesconto.getCategoriasEmolumentos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaEmolumento)) {
            return false;
        }
        return id != null && id.equals(((CategoriaEmolumento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriaEmolumento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", isServico='" + getIsServico() + "'" +
            ", cor='" + getCor() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", isIsentoMulta='" + getIsIsentoMulta() + "'" +
            ", isIsentoJuro='" + getIsIsentoJuro() + "'" +
            "}";
    }
}
