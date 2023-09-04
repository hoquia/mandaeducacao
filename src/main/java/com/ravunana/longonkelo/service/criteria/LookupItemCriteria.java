package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.LookupItem} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.LookupItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lookup-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LookupItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private IntegerFilter ordem;

    private BooleanFilter isSistema;

    private StringFilter descricao;

    private LongFilter lookupId;

    private Boolean distinct;

    public LookupItemCriteria() {}

    public LookupItemCriteria(LookupItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.ordem = other.ordem == null ? null : other.ordem.copy();
        this.isSistema = other.isSistema == null ? null : other.isSistema.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.lookupId = other.lookupId == null ? null : other.lookupId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LookupItemCriteria copy() {
        return new LookupItemCriteria(this);
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

    public IntegerFilter getOrdem() {
        return ordem;
    }

    public IntegerFilter ordem() {
        if (ordem == null) {
            ordem = new IntegerFilter();
        }
        return ordem;
    }

    public void setOrdem(IntegerFilter ordem) {
        this.ordem = ordem;
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

    public StringFilter getDescricao() {
        return descricao;
    }

    public StringFilter descricao() {
        if (descricao == null) {
            descricao = new StringFilter();
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public LongFilter getLookupId() {
        return lookupId;
    }

    public LongFilter lookupId() {
        if (lookupId == null) {
            lookupId = new LongFilter();
        }
        return lookupId;
    }

    public void setLookupId(LongFilter lookupId) {
        this.lookupId = lookupId;
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
        final LookupItemCriteria that = (LookupItemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(ordem, that.ordem) &&
            Objects.equals(isSistema, that.isSistema) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(lookupId, that.lookupId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, ordem, isSistema, descricao, lookupId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LookupItemCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigo != null ? "codigo=" + codigo + ", " : "") +
            (ordem != null ? "ordem=" + ordem + ", " : "") +
            (isSistema != null ? "isSistema=" + isSistema + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (lookupId != null ? "lookupId=" + lookupId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
