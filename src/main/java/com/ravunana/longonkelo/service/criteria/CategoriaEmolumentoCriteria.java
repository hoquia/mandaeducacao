package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.CategoriaEmolumento} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.CategoriaEmolumentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categoria-emolumentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriaEmolumentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BooleanFilter isServico;

    private StringFilter cor;

    private BooleanFilter isIsentoMulta;

    private BooleanFilter isIsentoJuro;

    private LongFilter emolumentoId;

    private LongFilter planoMultaId;

    private LongFilter planosDescontoId;

    private Boolean distinct;

    public CategoriaEmolumentoCriteria() {}

    public CategoriaEmolumentoCriteria(CategoriaEmolumentoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.isServico = other.isServico == null ? null : other.isServico.copy();
        this.cor = other.cor == null ? null : other.cor.copy();
        this.isIsentoMulta = other.isIsentoMulta == null ? null : other.isIsentoMulta.copy();
        this.isIsentoJuro = other.isIsentoJuro == null ? null : other.isIsentoJuro.copy();
        this.emolumentoId = other.emolumentoId == null ? null : other.emolumentoId.copy();
        this.planoMultaId = other.planoMultaId == null ? null : other.planoMultaId.copy();
        this.planosDescontoId = other.planosDescontoId == null ? null : other.planosDescontoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CategoriaEmolumentoCriteria copy() {
        return new CategoriaEmolumentoCriteria(this);
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

    public BooleanFilter getIsServico() {
        return isServico;
    }

    public BooleanFilter isServico() {
        if (isServico == null) {
            isServico = new BooleanFilter();
        }
        return isServico;
    }

    public void setIsServico(BooleanFilter isServico) {
        this.isServico = isServico;
    }

    public StringFilter getCor() {
        return cor;
    }

    public StringFilter cor() {
        if (cor == null) {
            cor = new StringFilter();
        }
        return cor;
    }

    public void setCor(StringFilter cor) {
        this.cor = cor;
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

    public LongFilter getEmolumentoId() {
        return emolumentoId;
    }

    public LongFilter emolumentoId() {
        if (emolumentoId == null) {
            emolumentoId = new LongFilter();
        }
        return emolumentoId;
    }

    public void setEmolumentoId(LongFilter emolumentoId) {
        this.emolumentoId = emolumentoId;
    }

    public LongFilter getPlanoMultaId() {
        return planoMultaId;
    }

    public LongFilter planoMultaId() {
        if (planoMultaId == null) {
            planoMultaId = new LongFilter();
        }
        return planoMultaId;
    }

    public void setPlanoMultaId(LongFilter planoMultaId) {
        this.planoMultaId = planoMultaId;
    }

    public LongFilter getPlanosDescontoId() {
        return planosDescontoId;
    }

    public LongFilter planosDescontoId() {
        if (planosDescontoId == null) {
            planosDescontoId = new LongFilter();
        }
        return planosDescontoId;
    }

    public void setPlanosDescontoId(LongFilter planosDescontoId) {
        this.planosDescontoId = planosDescontoId;
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
        final CategoriaEmolumentoCriteria that = (CategoriaEmolumentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(isServico, that.isServico) &&
            Objects.equals(cor, that.cor) &&
            Objects.equals(isIsentoMulta, that.isIsentoMulta) &&
            Objects.equals(isIsentoJuro, that.isIsentoJuro) &&
            Objects.equals(emolumentoId, that.emolumentoId) &&
            Objects.equals(planoMultaId, that.planoMultaId) &&
            Objects.equals(planosDescontoId, that.planosDescontoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, isServico, cor, isIsentoMulta, isIsentoJuro, emolumentoId, planoMultaId, planosDescontoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriaEmolumentoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (isServico != null ? "isServico=" + isServico + ", " : "") +
            (cor != null ? "cor=" + cor + ", " : "") +
            (isIsentoMulta != null ? "isIsentoMulta=" + isIsentoMulta + ", " : "") +
            (isIsentoJuro != null ? "isIsentoJuro=" + isIsentoJuro + ", " : "") +
            (emolumentoId != null ? "emolumentoId=" + emolumentoId + ", " : "") +
            (planoMultaId != null ? "planoMultaId=" + planoMultaId + ", " : "") +
            (planosDescontoId != null ? "planosDescontoId=" + planosDescontoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
