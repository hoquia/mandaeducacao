package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.CategoriaOcorrencia} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.CategoriaOcorrenciaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categoria-ocorrencias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriaOcorrenciaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private StringFilter sansaoDisicplinar;

    private BooleanFilter isNotificaEncaregado;

    private BooleanFilter isSendEmail;

    private BooleanFilter isSendSms;

    private BooleanFilter isSendPush;

    private StringFilter descricao;

    private LongFilter categoriaOcorrenciaId;

    private LongFilter ocorrenciaId;

    private LongFilter encaminharId;

    private LongFilter referenciaId;

    private LongFilter medidaDisciplinarId;

    private Boolean distinct;

    public CategoriaOcorrenciaCriteria() {}

    public CategoriaOcorrenciaCriteria(CategoriaOcorrenciaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.sansaoDisicplinar = other.sansaoDisicplinar == null ? null : other.sansaoDisicplinar.copy();
        this.isNotificaEncaregado = other.isNotificaEncaregado == null ? null : other.isNotificaEncaregado.copy();
        this.isSendEmail = other.isSendEmail == null ? null : other.isSendEmail.copy();
        this.isSendSms = other.isSendSms == null ? null : other.isSendSms.copy();
        this.isSendPush = other.isSendPush == null ? null : other.isSendPush.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.categoriaOcorrenciaId = other.categoriaOcorrenciaId == null ? null : other.categoriaOcorrenciaId.copy();
        this.ocorrenciaId = other.ocorrenciaId == null ? null : other.ocorrenciaId.copy();
        this.encaminharId = other.encaminharId == null ? null : other.encaminharId.copy();
        this.referenciaId = other.referenciaId == null ? null : other.referenciaId.copy();
        this.medidaDisciplinarId = other.medidaDisciplinarId == null ? null : other.medidaDisciplinarId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CategoriaOcorrenciaCriteria copy() {
        return new CategoriaOcorrenciaCriteria(this);
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

    public StringFilter getSansaoDisicplinar() {
        return sansaoDisicplinar;
    }

    public StringFilter sansaoDisicplinar() {
        if (sansaoDisicplinar == null) {
            sansaoDisicplinar = new StringFilter();
        }
        return sansaoDisicplinar;
    }

    public void setSansaoDisicplinar(StringFilter sansaoDisicplinar) {
        this.sansaoDisicplinar = sansaoDisicplinar;
    }

    public BooleanFilter getIsNotificaEncaregado() {
        return isNotificaEncaregado;
    }

    public BooleanFilter isNotificaEncaregado() {
        if (isNotificaEncaregado == null) {
            isNotificaEncaregado = new BooleanFilter();
        }
        return isNotificaEncaregado;
    }

    public void setIsNotificaEncaregado(BooleanFilter isNotificaEncaregado) {
        this.isNotificaEncaregado = isNotificaEncaregado;
    }

    public BooleanFilter getIsSendEmail() {
        return isSendEmail;
    }

    public BooleanFilter isSendEmail() {
        if (isSendEmail == null) {
            isSendEmail = new BooleanFilter();
        }
        return isSendEmail;
    }

    public void setIsSendEmail(BooleanFilter isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    public BooleanFilter getIsSendSms() {
        return isSendSms;
    }

    public BooleanFilter isSendSms() {
        if (isSendSms == null) {
            isSendSms = new BooleanFilter();
        }
        return isSendSms;
    }

    public void setIsSendSms(BooleanFilter isSendSms) {
        this.isSendSms = isSendSms;
    }

    public BooleanFilter getIsSendPush() {
        return isSendPush;
    }

    public BooleanFilter isSendPush() {
        if (isSendPush == null) {
            isSendPush = new BooleanFilter();
        }
        return isSendPush;
    }

    public void setIsSendPush(BooleanFilter isSendPush) {
        this.isSendPush = isSendPush;
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

    public LongFilter getCategoriaOcorrenciaId() {
        return categoriaOcorrenciaId;
    }

    public LongFilter categoriaOcorrenciaId() {
        if (categoriaOcorrenciaId == null) {
            categoriaOcorrenciaId = new LongFilter();
        }
        return categoriaOcorrenciaId;
    }

    public void setCategoriaOcorrenciaId(LongFilter categoriaOcorrenciaId) {
        this.categoriaOcorrenciaId = categoriaOcorrenciaId;
    }

    public LongFilter getOcorrenciaId() {
        return ocorrenciaId;
    }

    public LongFilter ocorrenciaId() {
        if (ocorrenciaId == null) {
            ocorrenciaId = new LongFilter();
        }
        return ocorrenciaId;
    }

    public void setOcorrenciaId(LongFilter ocorrenciaId) {
        this.ocorrenciaId = ocorrenciaId;
    }

    public LongFilter getEncaminharId() {
        return encaminharId;
    }

    public LongFilter encaminharId() {
        if (encaminharId == null) {
            encaminharId = new LongFilter();
        }
        return encaminharId;
    }

    public void setEncaminharId(LongFilter encaminharId) {
        this.encaminharId = encaminharId;
    }

    public LongFilter getReferenciaId() {
        return referenciaId;
    }

    public LongFilter referenciaId() {
        if (referenciaId == null) {
            referenciaId = new LongFilter();
        }
        return referenciaId;
    }

    public void setReferenciaId(LongFilter referenciaId) {
        this.referenciaId = referenciaId;
    }

    public LongFilter getMedidaDisciplinarId() {
        return medidaDisciplinarId;
    }

    public LongFilter medidaDisciplinarId() {
        if (medidaDisciplinarId == null) {
            medidaDisciplinarId = new LongFilter();
        }
        return medidaDisciplinarId;
    }

    public void setMedidaDisciplinarId(LongFilter medidaDisciplinarId) {
        this.medidaDisciplinarId = medidaDisciplinarId;
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
        final CategoriaOcorrenciaCriteria that = (CategoriaOcorrenciaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(sansaoDisicplinar, that.sansaoDisicplinar) &&
            Objects.equals(isNotificaEncaregado, that.isNotificaEncaregado) &&
            Objects.equals(isSendEmail, that.isSendEmail) &&
            Objects.equals(isSendSms, that.isSendSms) &&
            Objects.equals(isSendPush, that.isSendPush) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(categoriaOcorrenciaId, that.categoriaOcorrenciaId) &&
            Objects.equals(ocorrenciaId, that.ocorrenciaId) &&
            Objects.equals(encaminharId, that.encaminharId) &&
            Objects.equals(referenciaId, that.referenciaId) &&
            Objects.equals(medidaDisciplinarId, that.medidaDisciplinarId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            codigo,
            sansaoDisicplinar,
            isNotificaEncaregado,
            isSendEmail,
            isSendSms,
            isSendPush,
            descricao,
            categoriaOcorrenciaId,
            ocorrenciaId,
            encaminharId,
            referenciaId,
            medidaDisciplinarId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriaOcorrenciaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigo != null ? "codigo=" + codigo + ", " : "") +
            (sansaoDisicplinar != null ? "sansaoDisicplinar=" + sansaoDisicplinar + ", " : "") +
            (isNotificaEncaregado != null ? "isNotificaEncaregado=" + isNotificaEncaregado + ", " : "") +
            (isSendEmail != null ? "isSendEmail=" + isSendEmail + ", " : "") +
            (isSendSms != null ? "isSendSms=" + isSendSms + ", " : "") +
            (isSendPush != null ? "isSendPush=" + isSendPush + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (categoriaOcorrenciaId != null ? "categoriaOcorrenciaId=" + categoriaOcorrenciaId + ", " : "") +
            (ocorrenciaId != null ? "ocorrenciaId=" + ocorrenciaId + ", " : "") +
            (encaminharId != null ? "encaminharId=" + encaminharId + ", " : "") +
            (referenciaId != null ? "referenciaId=" + referenciaId + ", " : "") +
            (medidaDisciplinarId != null ? "medidaDisciplinarId=" + medidaDisciplinarId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
