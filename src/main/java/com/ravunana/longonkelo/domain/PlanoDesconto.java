package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlanoDesconto.
 */
@Entity
@Table(name = "plano_desconto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoDesconto implements Serializable {

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

    @Column(name = "is_isento_multa")
    private Boolean isIsentoMulta;

    @Column(name = "is_isento_juro")
    private Boolean isIsentoJuro;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "desconto", precision = 21, scale = 2, nullable = false)
    private BigDecimal desconto;

    @ManyToMany
    @JoinTable(
        name = "rel_plano_desconto__categorias_emolumento",
        joinColumns = @JoinColumn(name = "plano_desconto_id"),
        inverseJoinColumns = @JoinColumn(name = "categorias_emolumento_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "emolumentos", "planoMulta", "planosDescontos" }, allowSetters = true)
    private Set<CategoriaEmolumento> categoriasEmolumentos = new HashSet<>();

    @ManyToMany(mappedBy = "categoriasMatriculas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "matriculas",
            "facturas",
            "transacoes",
            "recibos",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "transferenciaTurmas",
            "ocorrencias",
            "utilizador",
            "categoriasMatriculas",
            "turma",
            "responsavelFinanceiro",
            "discente",
            "referencia",
        },
        allowSetters = true
    )
    private Set<Matricula> matriculas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanoDesconto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public PlanoDesconto codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public PlanoDesconto nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getIsIsentoMulta() {
        return this.isIsentoMulta;
    }

    public PlanoDesconto isIsentoMulta(Boolean isIsentoMulta) {
        this.setIsIsentoMulta(isIsentoMulta);
        return this;
    }

    public void setIsIsentoMulta(Boolean isIsentoMulta) {
        this.isIsentoMulta = isIsentoMulta;
    }

    public Boolean getIsIsentoJuro() {
        return this.isIsentoJuro;
    }

    public PlanoDesconto isIsentoJuro(Boolean isIsentoJuro) {
        this.setIsIsentoJuro(isIsentoJuro);
        return this;
    }

    public void setIsIsentoJuro(Boolean isIsentoJuro) {
        this.isIsentoJuro = isIsentoJuro;
    }

    public BigDecimal getDesconto() {
        return this.desconto;
    }

    public PlanoDesconto desconto(BigDecimal desconto) {
        this.setDesconto(desconto);
        return this;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Set<CategoriaEmolumento> getCategoriasEmolumentos() {
        return this.categoriasEmolumentos;
    }

    public void setCategoriasEmolumentos(Set<CategoriaEmolumento> categoriaEmolumentos) {
        this.categoriasEmolumentos = categoriaEmolumentos;
    }

    public PlanoDesconto categoriasEmolumentos(Set<CategoriaEmolumento> categoriaEmolumentos) {
        this.setCategoriasEmolumentos(categoriaEmolumentos);
        return this;
    }

    public PlanoDesconto addCategoriasEmolumento(CategoriaEmolumento categoriaEmolumento) {
        this.categoriasEmolumentos.add(categoriaEmolumento);
        categoriaEmolumento.getPlanosDescontos().add(this);
        return this;
    }

    public PlanoDesconto removeCategoriasEmolumento(CategoriaEmolumento categoriaEmolumento) {
        this.categoriasEmolumentos.remove(categoriaEmolumento);
        categoriaEmolumento.getPlanosDescontos().remove(this);
        return this;
    }

    public Set<Matricula> getMatriculas() {
        return this.matriculas;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        if (this.matriculas != null) {
            this.matriculas.forEach(i -> i.removeCategoriasMatriculas(this));
        }
        if (matriculas != null) {
            matriculas.forEach(i -> i.addCategoriasMatriculas(this));
        }
        this.matriculas = matriculas;
    }

    public PlanoDesconto matriculas(Set<Matricula> matriculas) {
        this.setMatriculas(matriculas);
        return this;
    }

    public PlanoDesconto addMatriculas(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.getCategoriasMatriculas().add(this);
        return this;
    }

    public PlanoDesconto removeMatriculas(Matricula matricula) {
        this.matriculas.remove(matricula);
        matricula.getCategoriasMatriculas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoDesconto)) {
            return false;
        }
        return id != null && id.equals(((PlanoDesconto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoDesconto{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", isIsentoMulta='" + getIsIsentoMulta() + "'" +
            ", isIsentoJuro='" + getIsIsentoJuro() + "'" +
            ", desconto=" + getDesconto() +
            "}";
    }
}
