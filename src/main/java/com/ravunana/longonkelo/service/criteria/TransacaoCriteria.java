package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.EstadoPagamento;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Transacao} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.TransacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransacaoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EstadoPagamento
     */
    public static class EstadoPagamentoFilter extends Filter<EstadoPagamento> {

        public EstadoPagamentoFilter() {}

        public EstadoPagamentoFilter(EstadoPagamentoFilter filter) {
            super(filter);
        }

        @Override
        public EstadoPagamentoFilter copy() {
            return new EstadoPagamentoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter montante;

    private LocalDateFilter data;

    private StringFilter referencia;

    private EstadoPagamentoFilter estado;

    private BigDecimalFilter saldo;

    private ZonedDateTimeFilter timestamp;

    private LongFilter recibosId;

    private LongFilter utilizadorId;

    private LongFilter moedaId;

    private LongFilter matriculaId;

    private LongFilter meioPagamentoId;

    private LongFilter contaId;

    private LongFilter transferenciaSaldoId;

    private Boolean distinct;

    public TransacaoCriteria() {}

    public TransacaoCriteria(TransacaoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.montante = other.montante == null ? null : other.montante.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.referencia = other.referencia == null ? null : other.referencia.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.saldo = other.saldo == null ? null : other.saldo.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.recibosId = other.recibosId == null ? null : other.recibosId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.moedaId = other.moedaId == null ? null : other.moedaId.copy();
        this.matriculaId = other.matriculaId == null ? null : other.matriculaId.copy();
        this.meioPagamentoId = other.meioPagamentoId == null ? null : other.meioPagamentoId.copy();
        this.contaId = other.contaId == null ? null : other.contaId.copy();
        this.transferenciaSaldoId = other.transferenciaSaldoId == null ? null : other.transferenciaSaldoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TransacaoCriteria copy() {
        return new TransacaoCriteria(this);
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

    public LocalDateFilter getData() {
        return data;
    }

    public LocalDateFilter data() {
        if (data == null) {
            data = new LocalDateFilter();
        }
        return data;
    }

    public void setData(LocalDateFilter data) {
        this.data = data;
    }

    public StringFilter getReferencia() {
        return referencia;
    }

    public StringFilter referencia() {
        if (referencia == null) {
            referencia = new StringFilter();
        }
        return referencia;
    }

    public void setReferencia(StringFilter referencia) {
        this.referencia = referencia;
    }

    public EstadoPagamentoFilter getEstado() {
        return estado;
    }

    public EstadoPagamentoFilter estado() {
        if (estado == null) {
            estado = new EstadoPagamentoFilter();
        }
        return estado;
    }

    public void setEstado(EstadoPagamentoFilter estado) {
        this.estado = estado;
    }

    public BigDecimalFilter getSaldo() {
        return saldo;
    }

    public BigDecimalFilter saldo() {
        if (saldo == null) {
            saldo = new BigDecimalFilter();
        }
        return saldo;
    }

    public void setSaldo(BigDecimalFilter saldo) {
        this.saldo = saldo;
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

    public LongFilter getRecibosId() {
        return recibosId;
    }

    public LongFilter recibosId() {
        if (recibosId == null) {
            recibosId = new LongFilter();
        }
        return recibosId;
    }

    public void setRecibosId(LongFilter recibosId) {
        this.recibosId = recibosId;
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

    public LongFilter getMoedaId() {
        return moedaId;
    }

    public LongFilter moedaId() {
        if (moedaId == null) {
            moedaId = new LongFilter();
        }
        return moedaId;
    }

    public void setMoedaId(LongFilter moedaId) {
        this.moedaId = moedaId;
    }

    public LongFilter getMatriculaId() {
        return matriculaId;
    }

    public LongFilter matriculaId() {
        if (matriculaId == null) {
            matriculaId = new LongFilter();
        }
        return matriculaId;
    }

    public void setMatriculaId(LongFilter matriculaId) {
        this.matriculaId = matriculaId;
    }

    public LongFilter getMeioPagamentoId() {
        return meioPagamentoId;
    }

    public LongFilter meioPagamentoId() {
        if (meioPagamentoId == null) {
            meioPagamentoId = new LongFilter();
        }
        return meioPagamentoId;
    }

    public void setMeioPagamentoId(LongFilter meioPagamentoId) {
        this.meioPagamentoId = meioPagamentoId;
    }

    public LongFilter getContaId() {
        return contaId;
    }

    public LongFilter contaId() {
        if (contaId == null) {
            contaId = new LongFilter();
        }
        return contaId;
    }

    public void setContaId(LongFilter contaId) {
        this.contaId = contaId;
    }

    public LongFilter getTransferenciaSaldoId() {
        return transferenciaSaldoId;
    }

    public LongFilter transferenciaSaldoId() {
        if (transferenciaSaldoId == null) {
            transferenciaSaldoId = new LongFilter();
        }
        return transferenciaSaldoId;
    }

    public void setTransferenciaSaldoId(LongFilter transferenciaSaldoId) {
        this.transferenciaSaldoId = transferenciaSaldoId;
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
        final TransacaoCriteria that = (TransacaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(montante, that.montante) &&
            Objects.equals(data, that.data) &&
            Objects.equals(referencia, that.referencia) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(saldo, that.saldo) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(recibosId, that.recibosId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(moedaId, that.moedaId) &&
            Objects.equals(matriculaId, that.matriculaId) &&
            Objects.equals(meioPagamentoId, that.meioPagamentoId) &&
            Objects.equals(contaId, that.contaId) &&
            Objects.equals(transferenciaSaldoId, that.transferenciaSaldoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            montante,
            data,
            referencia,
            estado,
            saldo,
            timestamp,
            recibosId,
            utilizadorId,
            moedaId,
            matriculaId,
            meioPagamentoId,
            contaId,
            transferenciaSaldoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransacaoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (montante != null ? "montante=" + montante + ", " : "") +
            (data != null ? "data=" + data + ", " : "") +
            (referencia != null ? "referencia=" + referencia + ", " : "") +
            (estado != null ? "estado=" + estado + ", " : "") +
            (saldo != null ? "saldo=" + saldo + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (recibosId != null ? "recibosId=" + recibosId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (moedaId != null ? "moedaId=" + moedaId + ", " : "") +
            (matriculaId != null ? "matriculaId=" + matriculaId + ", " : "") +
            (meioPagamentoId != null ? "meioPagamentoId=" + meioPagamentoId + ", " : "") +
            (contaId != null ? "contaId=" + contaId + ", " : "") +
            (transferenciaSaldoId != null ? "transferenciaSaldoId=" + transferenciaSaldoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
