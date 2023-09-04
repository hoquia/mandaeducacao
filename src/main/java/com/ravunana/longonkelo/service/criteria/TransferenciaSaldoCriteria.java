package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.TransferenciaSaldo} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.TransferenciaSaldoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transferencia-saldos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransferenciaSaldoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter montante;

    private BooleanFilter isMesmaConta;

    private ZonedDateTimeFilter timestamp;

    private LongFilter deId;

    private LongFilter paraId;

    private LongFilter utilizadorId;

    private LongFilter motivoTransferenciaId;

    private LongFilter transacoesId;

    private Boolean distinct;

    public TransferenciaSaldoCriteria() {}

    public TransferenciaSaldoCriteria(TransferenciaSaldoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.montante = other.montante == null ? null : other.montante.copy();
        this.isMesmaConta = other.isMesmaConta == null ? null : other.isMesmaConta.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.deId = other.deId == null ? null : other.deId.copy();
        this.paraId = other.paraId == null ? null : other.paraId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.motivoTransferenciaId = other.motivoTransferenciaId == null ? null : other.motivoTransferenciaId.copy();
        this.transacoesId = other.transacoesId == null ? null : other.transacoesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TransferenciaSaldoCriteria copy() {
        return new TransferenciaSaldoCriteria(this);
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

    public BigDecimalFilter getMontante() {
        return montante;
    }

    public BigDecimalFilter montante() {
        if (montante == null) {
            montante = new BigDecimalFilter();
        }
        return montante;
    }

    public void setMontante(BigDecimalFilter montante) {
        this.montante = montante;
    }

    public BooleanFilter getIsMesmaConta() {
        return isMesmaConta;
    }

    public BooleanFilter isMesmaConta() {
        if (isMesmaConta == null) {
            isMesmaConta = new BooleanFilter();
        }
        return isMesmaConta;
    }

    public void setIsMesmaConta(BooleanFilter isMesmaConta) {
        this.isMesmaConta = isMesmaConta;
    }

    public ZonedDateTimeFilter getTimestamp() {
        return timestamp;
    }

    public ZonedDateTimeFilter timestamp() {
        if (timestamp == null) {
            timestamp = new ZonedDateTimeFilter();
        }
        return timestamp;
    }

    public void setTimestamp(ZonedDateTimeFilter timestamp) {
        this.timestamp = timestamp;
    }

    public LongFilter getDeId() {
        return deId;
    }

    public LongFilter deId() {
        if (deId == null) {
            deId = new LongFilter();
        }
        return deId;
    }

    public void setDeId(LongFilter deId) {
        this.deId = deId;
    }

    public LongFilter getParaId() {
        return paraId;
    }

    public LongFilter paraId() {
        if (paraId == null) {
            paraId = new LongFilter();
        }
        return paraId;
    }

    public void setParaId(LongFilter paraId) {
        this.paraId = paraId;
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

    public LongFilter getMotivoTransferenciaId() {
        return motivoTransferenciaId;
    }

    public LongFilter motivoTransferenciaId() {
        if (motivoTransferenciaId == null) {
            motivoTransferenciaId = new LongFilter();
        }
        return motivoTransferenciaId;
    }

    public void setMotivoTransferenciaId(LongFilter motivoTransferenciaId) {
        this.motivoTransferenciaId = motivoTransferenciaId;
    }

    public LongFilter getTransacoesId() {
        return transacoesId;
    }

    public LongFilter transacoesId() {
        if (transacoesId == null) {
            transacoesId = new LongFilter();
        }
        return transacoesId;
    }

    public void setTransacoesId(LongFilter transacoesId) {
        this.transacoesId = transacoesId;
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
        final TransferenciaSaldoCriteria that = (TransferenciaSaldoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(montante, that.montante) &&
            Objects.equals(isMesmaConta, that.isMesmaConta) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(deId, that.deId) &&
            Objects.equals(paraId, that.paraId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(motivoTransferenciaId, that.motivoTransferenciaId) &&
            Objects.equals(transacoesId, that.transacoesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            montante,
            isMesmaConta,
            timestamp,
            deId,
            paraId,
            utilizadorId,
            motivoTransferenciaId,
            transacoesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferenciaSaldoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (montante != null ? "montante=" + montante + ", " : "") +
            (isMesmaConta != null ? "isMesmaConta=" + isMesmaConta + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (deId != null ? "deId=" + deId + ", " : "") +
            (paraId != null ? "paraId=" + paraId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (motivoTransferenciaId != null ? "motivoTransferenciaId=" + motivoTransferenciaId + ", " : "") +
            (transacoesId != null ? "transacoesId=" + transacoesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
