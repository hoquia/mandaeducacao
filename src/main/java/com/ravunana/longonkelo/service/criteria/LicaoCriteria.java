package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.EstadoLicao;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Licao} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.LicaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /licaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LicaoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EstadoLicao
     */
    public static class EstadoLicaoFilter extends Filter<EstadoLicao> {

        public EstadoLicaoFilter() {}

        public EstadoLicaoFilter(EstadoLicaoFilter filter) {
            super(filter);
        }

        @Override
        public EstadoLicaoFilter copy() {
            return new EstadoLicaoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter chaveComposta;

    private IntegerFilter numero;

    private EstadoLicaoFilter estado;

    private LongFilter ocorrenciasId;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter planoAulaId;

    private LongFilter horarioId;

    private Boolean distinct;

    public LicaoCriteria() {}

    public LicaoCriteria(LicaoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.chaveComposta = other.chaveComposta == null ? null : other.chaveComposta.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.ocorrenciasId = other.ocorrenciasId == null ? null : other.ocorrenciasId.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.planoAulaId = other.planoAulaId == null ? null : other.planoAulaId.copy();
        this.horarioId = other.horarioId == null ? null : other.horarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LicaoCriteria copy() {
        return new LicaoCriteria(this);
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

    public StringFilter getChaveComposta() {
        return chaveComposta;
    }

    public StringFilter chaveComposta() {
        if (chaveComposta == null) {
            chaveComposta = new StringFilter();
        }
        return chaveComposta;
    }

    public void setChaveComposta(StringFilter chaveComposta) {
        this.chaveComposta = chaveComposta;
    }

    public IntegerFilter getNumero() {
        return numero;
    }

    public IntegerFilter numero() {
        if (numero == null) {
            numero = new IntegerFilter();
        }
        return numero;
    }

    public void setNumero(IntegerFilter numero) {
        this.numero = numero;
    }

    public EstadoLicaoFilter getEstado() {
        return estado;
    }

    public EstadoLicaoFilter estado() {
        if (estado == null) {
            estado = new EstadoLicaoFilter();
        }
        return estado;
    }

    public void setEstado(EstadoLicaoFilter estado) {
        this.estado = estado;
    }

    public LongFilter getOcorrenciasId() {
        return ocorrenciasId;
    }

    public LongFilter ocorrenciasId() {
        if (ocorrenciasId == null) {
            ocorrenciasId = new LongFilter();
        }
        return ocorrenciasId;
    }

    public void setOcorrenciasId(LongFilter ocorrenciasId) {
        this.ocorrenciasId = ocorrenciasId;
    }

    public LongFilter getAnoLectivoId() {
        return anoLectivoId;
    }

    public LongFilter anoLectivoId() {
        if (anoLectivoId == null) {
            anoLectivoId = new LongFilter();
        }
        return anoLectivoId;
    }

    public void setAnoLectivoId(LongFilter anoLectivoId) {
        this.anoLectivoId = anoLectivoId;
    }

    public LongFilter getUtilizadorId() {
        return utilizadorId;
    }

    public LongFilter utilizadorId() {
        if (utilizadorId == null) {
            utilizadorId = new LongFilter();
        }
        return utilizadorId;
    }

    public void setUtilizadorId(LongFilter utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public LongFilter getPlanoAulaId() {
        return planoAulaId;
    }

    public LongFilter planoAulaId() {
        if (planoAulaId == null) {
            planoAulaId = new LongFilter();
        }
        return planoAulaId;
    }

    public void setPlanoAulaId(LongFilter planoAulaId) {
        this.planoAulaId = planoAulaId;
    }

    public LongFilter getHorarioId() {
        return horarioId;
    }

    public LongFilter horarioId() {
        if (horarioId == null) {
            horarioId = new LongFilter();
        }
        return horarioId;
    }

    public void setHorarioId(LongFilter horarioId) {
        this.horarioId = horarioId;
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
        final LicaoCriteria that = (LicaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(chaveComposta, that.chaveComposta) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(ocorrenciasId, that.ocorrenciasId) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(planoAulaId, that.planoAulaId) &&
            Objects.equals(horarioId, that.horarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chaveComposta, numero, estado, ocorrenciasId, anoLectivoId, utilizadorId, planoAulaId, horarioId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LicaoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (chaveComposta != null ? "chaveComposta=" + chaveComposta + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (estado != null ? "estado=" + estado + ", " : "") +
            (ocorrenciasId != null ? "ocorrenciasId=" + ocorrenciasId + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (planoAulaId != null ? "planoAulaId=" + planoAulaId + ", " : "") +
            (horarioId != null ? "horarioId=" + horarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
