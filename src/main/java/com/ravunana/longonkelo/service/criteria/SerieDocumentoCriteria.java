package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.SerieDocumento} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.SerieDocumentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /serie-documentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SerieDocumentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter anoFiscal;

    private IntegerFilter versao;

    private StringFilter serie;

    private BooleanFilter isAtivo;

    private BooleanFilter isPadrao;

    private LongFilter sequenciaDocumentoId;

    private LongFilter tipoDocumentoId;

    private Boolean distinct;

    public SerieDocumentoCriteria() {}

    public SerieDocumentoCriteria(SerieDocumentoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.anoFiscal = other.anoFiscal == null ? null : other.anoFiscal.copy();
        this.versao = other.versao == null ? null : other.versao.copy();
        this.serie = other.serie == null ? null : other.serie.copy();
        this.isAtivo = other.isAtivo == null ? null : other.isAtivo.copy();
        this.isPadrao = other.isPadrao == null ? null : other.isPadrao.copy();
        this.sequenciaDocumentoId = other.sequenciaDocumentoId == null ? null : other.sequenciaDocumentoId.copy();
        this.tipoDocumentoId = other.tipoDocumentoId == null ? null : other.tipoDocumentoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SerieDocumentoCriteria copy() {
        return new SerieDocumentoCriteria(this);
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

    public IntegerFilter getAnoFiscal() {
        return anoFiscal;
    }

    public IntegerFilter anoFiscal() {
        if (anoFiscal == null) {
            anoFiscal = new IntegerFilter();
        }
        return anoFiscal;
    }

    public void setAnoFiscal(IntegerFilter anoFiscal) {
        this.anoFiscal = anoFiscal;
    }

    public IntegerFilter getVersao() {
        return versao;
    }

    public IntegerFilter versao() {
        if (versao == null) {
            versao = new IntegerFilter();
        }
        return versao;
    }

    public void setVersao(IntegerFilter versao) {
        this.versao = versao;
    }

    public StringFilter getSerie() {
        return serie;
    }

    public StringFilter serie() {
        if (serie == null) {
            serie = new StringFilter();
        }
        return serie;
    }

    public void setSerie(StringFilter serie) {
        this.serie = serie;
    }

    public BooleanFilter getIsAtivo() {
        return isAtivo;
    }

    public BooleanFilter isAtivo() {
        if (isAtivo == null) {
            isAtivo = new BooleanFilter();
        }
        return isAtivo;
    }

    public void setIsAtivo(BooleanFilter isAtivo) {
        this.isAtivo = isAtivo;
    }

    public BooleanFilter getIsPadrao() {
        return isPadrao;
    }

    public BooleanFilter isPadrao() {
        if (isPadrao == null) {
            isPadrao = new BooleanFilter();
        }
        return isPadrao;
    }

    public void setIsPadrao(BooleanFilter isPadrao) {
        this.isPadrao = isPadrao;
    }

    public LongFilter getSequenciaDocumentoId() {
        return sequenciaDocumentoId;
    }

    public LongFilter sequenciaDocumentoId() {
        if (sequenciaDocumentoId == null) {
            sequenciaDocumentoId = new LongFilter();
        }
        return sequenciaDocumentoId;
    }

    public void setSequenciaDocumentoId(LongFilter sequenciaDocumentoId) {
        this.sequenciaDocumentoId = sequenciaDocumentoId;
    }

    public LongFilter getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public LongFilter tipoDocumentoId() {
        if (tipoDocumentoId == null) {
            tipoDocumentoId = new LongFilter();
        }
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(LongFilter tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
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
        final SerieDocumentoCriteria that = (SerieDocumentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(anoFiscal, that.anoFiscal) &&
            Objects.equals(versao, that.versao) &&
            Objects.equals(serie, that.serie) &&
            Objects.equals(isAtivo, that.isAtivo) &&
            Objects.equals(isPadrao, that.isPadrao) &&
            Objects.equals(sequenciaDocumentoId, that.sequenciaDocumentoId) &&
            Objects.equals(tipoDocumentoId, that.tipoDocumentoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, anoFiscal, versao, serie, isAtivo, isPadrao, sequenciaDocumentoId, tipoDocumentoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SerieDocumentoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (anoFiscal != null ? "anoFiscal=" + anoFiscal + ", " : "") +
            (versao != null ? "versao=" + versao + ", " : "") +
            (serie != null ? "serie=" + serie + ", " : "") +
            (isAtivo != null ? "isAtivo=" + isAtivo + ", " : "") +
            (isPadrao != null ? "isPadrao=" + isPadrao + ", " : "") +
            (sequenciaDocumentoId != null ? "sequenciaDocumentoId=" + sequenciaDocumentoId + ", " : "") +
            (tipoDocumentoId != null ? "tipoDocumentoId=" + tipoDocumentoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
