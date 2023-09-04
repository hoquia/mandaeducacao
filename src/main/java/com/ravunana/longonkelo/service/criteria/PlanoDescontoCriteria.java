package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.PlanoDesconto} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.PlanoDescontoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plano-descontos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoDescontoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private StringFilter nome;

    private BooleanFilter isIsentoMulta;

    private BooleanFilter isIsentoJuro;

    private BigDecimalFilter desconto;

    private LongFilter categoriasEmolumentoId;

    private LongFilter matriculasId;

    private Boolean distinct;

    public PlanoDescontoCriteria() {}

    public PlanoDescontoCriteria(PlanoDescontoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.isIsentoMulta = other.isIsentoMulta == null ? null : other.isIsentoMulta.copy();
        this.isIsentoJuro = other.isIsentoJuro == null ? null : other.isIsentoJuro.copy();
        this.desconto = other.desconto == null ? null : other.desconto.copy();
        this.categoriasEmolumentoId = other.categoriasEmolumentoId == null ? null : other.categoriasEmolumentoId.copy();
        this.matriculasId = other.matriculasId == null ? null : other.matriculasId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PlanoDescontoCriteria copy() {
        return new PlanoDescontoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodigo() {
        return codigo;
    }

    public StringFilter codigo() {
        if (codigo == null) {
            codigo = new StringFilter();
        }
        return codigo;
    }

    public void setCodigo(StringFilter codigo) {
        this.codigo = codigo;
    }

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public BooleanFilter getIsIsentoMulta() {
        return isIsentoMulta;
    }

    public BooleanFilter isIsentoMulta() {
        if (isIsentoMulta == null) {
            isIsentoMulta = new BooleanFilter();
        }
        return isIsentoMulta;
    }

    public void setIsIsentoMulta(BooleanFilter isIsentoMulta) {
        this.isIsentoMulta = isIsentoMulta;
    }

    public BooleanFilter getIsIsentoJuro() {
        return isIsentoJuro;
    }

    public BooleanFilter isIsentoJuro() {
        if (isIsentoJuro == null) {
            isIsentoJuro = new BooleanFilter();
        }
        return isIsentoJuro;
    }

    public void setIsIsentoJuro(BooleanFilter isIsentoJuro) {
        this.isIsentoJuro = isIsentoJuro;
    }

    public BigDecimalFilter getDesconto() {
        return desconto;
    }

    public BigDecimalFilter desconto() {
        if (desconto == null) {
            desconto = new BigDecimalFilter();
        }
        return desconto;
    }

    public void setDesconto(BigDecimalFilter desconto) {
        this.desconto = desconto;
    }

    public LongFilter getCategoriasEmolumentoId() {
        return categoriasEmolumentoId;
    }

    public LongFilter categoriasEmolumentoId() {
        if (categoriasEmolumentoId == null) {
            categoriasEmolumentoId = new LongFilter();
        }
        return categoriasEmolumentoId;
    }

    public void setCategoriasEmolumentoId(LongFilter categoriasEmolumentoId) {
        this.categoriasEmolumentoId = categoriasEmolumentoId;
    }

    public LongFilter getMatriculasId() {
        return matriculasId;
    }

    public LongFilter matriculasId() {
        if (matriculasId == null) {
            matriculasId = new LongFilter();
        }
        return matriculasId;
    }

    public void setMatriculasId(LongFilter matriculasId) {
        this.matriculasId = matriculasId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PlanoDescontoCriteria that = (PlanoDescontoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(isIsentoMulta, that.isIsentoMulta) &&
            Objects.equals(isIsentoJuro, that.isIsentoJuro) &&
            Objects.equals(desconto, that.desconto) &&
            Objects.equals(categoriasEmolumentoId, that.categoriasEmolumentoId) &&
            Objects.equals(matriculasId, that.matriculasId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, nome, isIsentoMulta, isIsentoJuro, desconto, categoriasEmolumentoId, matriculasId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoDescontoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigo != null ? "codigo=" + codigo + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (isIsentoMulta != null ? "isIsentoMulta=" + isIsentoMulta + ", " : "") +
            (isIsentoJuro != null ? "isIsentoJuro=" + isIsentoJuro + ", " : "") +
            (desconto != null ? "desconto=" + desconto + ", " : "") +
            (categoriasEmolumentoId != null ? "categoriasEmolumentoId=" + categoriasEmolumentoId + ", " : "") +
            (matriculasId != null ? "matriculasId=" + matriculasId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
