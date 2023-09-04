package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Lookup} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.LookupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lookups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LookupCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private StringFilter nome;

    private BooleanFilter isSistema;

    private BooleanFilter isModificavel;

    private LongFilter lookupItemsId;

    private Boolean distinct;

    public LookupCriteria() {}

    public LookupCriteria(LookupCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.isSistema = other.isSistema == null ? null : other.isSistema.copy();
        this.isModificavel = other.isModificavel == null ? null : other.isModificavel.copy();
        this.lookupItemsId = other.lookupItemsId == null ? null : other.lookupItemsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LookupCriteria copy() {
        return new LookupCriteria(this);
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

    public BooleanFilter getIsSistema() {
        return isSistema;
    }

    public BooleanFilter isSistema() {
        if (isSistema == null) {
            isSistema = new BooleanFilter();
        }
        return isSistema;
    }

    public void setIsSistema(BooleanFilter isSistema) {
        this.isSistema = isSistema;
    }

    public BooleanFilter getIsModificavel() {
        return isModificavel;
    }

    public BooleanFilter isModificavel() {
        if (isModificavel == null) {
            isModificavel = new BooleanFilter();
        }
        return isModificavel;
    }

    public void setIsModificavel(BooleanFilter isModificavel) {
        this.isModificavel = isModificavel;
    }

    public LongFilter getLookupItemsId() {
        return lookupItemsId;
    }

    public LongFilter lookupItemsId() {
        if (lookupItemsId == null) {
            lookupItemsId = new LongFilter();
        }
        return lookupItemsId;
    }

    public void setLookupItemsId(LongFilter lookupItemsId) {
        this.lookupItemsId = lookupItemsId;
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
        final LookupCriteria that = (LookupCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(isSistema, that.isSistema) &&
            Objects.equals(isModificavel, that.isModificavel) &&
            Objects.equals(lookupItemsId, that.lookupItemsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, nome, isSistema, isModificavel, lookupItemsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LookupCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigo != null ? "codigo=" + codigo + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (isSistema != null ? "isSistema=" + isSistema + ", " : "") +
            (isModificavel != null ? "isModificavel=" + isModificavel + ", " : "") +
            (lookupItemsId != null ? "lookupItemsId=" + lookupItemsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
