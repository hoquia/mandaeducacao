package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.EstadoDocumentoComercial;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Recibo} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.ReciboResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /recibos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReciboCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EstadoDocumentoComercial
     */
    public static class EstadoDocumentoComercialFilter extends Filter<EstadoDocumentoComercial> {

        public EstadoDocumentoComercialFilter() {}

        public EstadoDocumentoComercialFilter(EstadoDocumentoComercialFilter filter) {
            super(filter);
        }

        @Override
        public EstadoDocumentoComercialFilter copy() {
            return new EstadoDocumentoComercialFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter data;

    private LocalDateFilter vencimento;

    private StringFilter numero;

    private BigDecimalFilter totalSemImposto;

    private BigDecimalFilter totalComImposto;

    private BigDecimalFilter totalDescontoComercial;

    private BigDecimalFilter totalDescontoFinanceiro;

    private BigDecimalFilter totalIVA;

    private BigDecimalFilter totalRetencao;

    private BigDecimalFilter totalJuro;

    private BigDecimalFilter cambio;

    private BigDecimalFilter totalMoedaEstrangeira;

    private BigDecimalFilter totalPagar;

    private BigDecimalFilter totalPago;

    private BigDecimalFilter totalFalta;

    private BigDecimalFilter totalTroco;

    private BooleanFilter isNovo;

    private ZonedDateTimeFilter timestamp;

    private BigDecimalFilter debito;

    private BigDecimalFilter credito;

    private BooleanFilter isFiscalizado;

    private StringFilter signText;

    private StringFilter hash;

    private StringFilter hashShort;

    private StringFilter hashControl;

    private IntegerFilter keyVersion;

    private EstadoDocumentoComercialFilter estado;

    private StringFilter origem;

    private LongFilter aplicacoesReciboId;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter matriculaId;

    private LongFilter documentoComercialId;

    private LongFilter transacaoId;

    private Boolean distinct;

    public ReciboCriteria() {}

    public ReciboCriteria(ReciboCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.vencimento = other.vencimento == null ? null : other.vencimento.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.totalSemImposto = other.totalSemImposto == null ? null : other.totalSemImposto.copy();
        this.totalComImposto = other.totalComImposto == null ? null : other.totalComImposto.copy();
        this.totalDescontoComercial = other.totalDescontoComercial == null ? null : other.totalDescontoComercial.copy();
        this.totalDescontoFinanceiro = other.totalDescontoFinanceiro == null ? null : other.totalDescontoFinanceiro.copy();
        this.totalIVA = other.totalIVA == null ? null : other.totalIVA.copy();
        this.totalRetencao = other.totalRetencao == null ? null : other.totalRetencao.copy();
        this.totalJuro = other.totalJuro == null ? null : other.totalJuro.copy();
        this.cambio = other.cambio == null ? null : other.cambio.copy();
        this.totalMoedaEstrangeira = other.totalMoedaEstrangeira == null ? null : other.totalMoedaEstrangeira.copy();
        this.totalPagar = other.totalPagar == null ? null : other.totalPagar.copy();
        this.totalPago = other.totalPago == null ? null : other.totalPago.copy();
        this.totalFalta = other.totalFalta == null ? null : other.totalFalta.copy();
        this.totalTroco = other.totalTroco == null ? null : other.totalTroco.copy();
        this.isNovo = other.isNovo == null ? null : other.isNovo.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.debito = other.debito == null ? null : other.debito.copy();
        this.credito = other.credito == null ? null : other.credito.copy();
        this.isFiscalizado = other.isFiscalizado == null ? null : other.isFiscalizado.copy();
        this.signText = other.signText == null ? null : other.signText.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.hashShort = other.hashShort == null ? null : other.hashShort.copy();
        this.hashControl = other.hashControl == null ? null : other.hashControl.copy();
        this.keyVersion = other.keyVersion == null ? null : other.keyVersion.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.origem = other.origem == null ? null : other.origem.copy();
        this.aplicacoesReciboId = other.aplicacoesReciboId == null ? null : other.aplicacoesReciboId.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.matriculaId = other.matriculaId == null ? null : other.matriculaId.copy();
        this.documentoComercialId = other.documentoComercialId == null ? null : other.documentoComercialId.copy();
        this.transacaoId = other.transacaoId == null ? null : other.transacaoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ReciboCriteria copy() {
        return new ReciboCriteria(this);
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

    public LocalDateFilter getVencimento() {
        return vencimento;
    }

    public LocalDateFilter vencimento() {
        if (vencimento == null) {
            vencimento = new LocalDateFilter();
        }
        return vencimento;
    }

    public void setVencimento(LocalDateFilter vencimento) {
        this.vencimento = vencimento;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public StringFilter numero() {
        if (numero == null) {
            numero = new StringFilter();
        }
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public BigDecimalFilter getTotalSemImposto() {
        return totalSemImposto;
    }

    public BigDecimalFilter totalSemImposto() {
        if (totalSemImposto == null) {
            totalSemImposto = new BigDecimalFilter();
        }
        return totalSemImposto;
    }

    public void setTotalSemImposto(BigDecimalFilter totalSemImposto) {
        this.totalSemImposto = totalSemImposto;
    }

    public BigDecimalFilter getTotalComImposto() {
        return totalComImposto;
    }

    public BigDecimalFilter totalComImposto() {
        if (totalComImposto == null) {
            totalComImposto = new BigDecimalFilter();
        }
        return totalComImposto;
    }

    public void setTotalComImposto(BigDecimalFilter totalComImposto) {
        this.totalComImposto = totalComImposto;
    }

    public BigDecimalFilter getTotalDescontoComercial() {
        return totalDescontoComercial;
    }

    public BigDecimalFilter totalDescontoComercial() {
        if (totalDescontoComercial == null) {
            totalDescontoComercial = new BigDecimalFilter();
        }
        return totalDescontoComercial;
    }

    public void setTotalDescontoComercial(BigDecimalFilter totalDescontoComercial) {
        this.totalDescontoComercial = totalDescontoComercial;
    }

    public BigDecimalFilter getTotalDescontoFinanceiro() {
        return totalDescontoFinanceiro;
    }

    public BigDecimalFilter totalDescontoFinanceiro() {
        if (totalDescontoFinanceiro == null) {
            totalDescontoFinanceiro = new BigDecimalFilter();
        }
        return totalDescontoFinanceiro;
    }

    public void setTotalDescontoFinanceiro(BigDecimalFilter totalDescontoFinanceiro) {
        this.totalDescontoFinanceiro = totalDescontoFinanceiro;
    }

    public BigDecimalFilter getTotalIVA() {
        return totalIVA;
    }

    public BigDecimalFilter totalIVA() {
        if (totalIVA == null) {
            totalIVA = new BigDecimalFilter();
        }
        return totalIVA;
    }

    public void setTotalIVA(BigDecimalFilter totalIVA) {
        this.totalIVA = totalIVA;
    }

    public BigDecimalFilter getTotalRetencao() {
        return totalRetencao;
    }

    public BigDecimalFilter totalRetencao() {
        if (totalRetencao == null) {
            totalRetencao = new BigDecimalFilter();
        }
        return totalRetencao;
    }

    public void setTotalRetencao(BigDecimalFilter totalRetencao) {
        this.totalRetencao = totalRetencao;
    }

    public BigDecimalFilter getTotalJuro() {
        return totalJuro;
    }

    public BigDecimalFilter totalJuro() {
        if (totalJuro == null) {
            totalJuro = new BigDecimalFilter();
        }
        return totalJuro;
    }

    public void setTotalJuro(BigDecimalFilter totalJuro) {
        this.totalJuro = totalJuro;
    }

    public BigDecimalFilter getCambio() {
        return cambio;
    }

    public BigDecimalFilter cambio() {
        if (cambio == null) {
            cambio = new BigDecimalFilter();
        }
        return cambio;
    }

    public void setCambio(BigDecimalFilter cambio) {
        this.cambio = cambio;
    }

    public BigDecimalFilter getTotalMoedaEstrangeira() {
        return totalMoedaEstrangeira;
    }

    public BigDecimalFilter totalMoedaEstrangeira() {
        if (totalMoedaEstrangeira == null) {
            totalMoedaEstrangeira = new BigDecimalFilter();
        }
        return totalMoedaEstrangeira;
    }

    public void setTotalMoedaEstrangeira(BigDecimalFilter totalMoedaEstrangeira) {
        this.totalMoedaEstrangeira = totalMoedaEstrangeira;
    }

    public BigDecimalFilter getTotalPagar() {
        return totalPagar;
    }

    public BigDecimalFilter totalPagar() {
        if (totalPagar == null) {
            totalPagar = new BigDecimalFilter();
        }
        return totalPagar;
    }

    public void setTotalPagar(BigDecimalFilter totalPagar) {
        this.totalPagar = totalPagar;
    }

    public BigDecimalFilter getTotalPago() {
        return totalPago;
    }

    public BigDecimalFilter totalPago() {
        if (totalPago == null) {
            totalPago = new BigDecimalFilter();
        }
        return totalPago;
    }

    public void setTotalPago(BigDecimalFilter totalPago) {
        this.totalPago = totalPago;
    }

    public BigDecimalFilter getTotalFalta() {
        return totalFalta;
    }

    public BigDecimalFilter totalFalta() {
        if (totalFalta == null) {
            totalFalta = new BigDecimalFilter();
        }
        return totalFalta;
    }

    public void setTotalFalta(BigDecimalFilter totalFalta) {
        this.totalFalta = totalFalta;
    }

    public BigDecimalFilter getTotalTroco() {
        return totalTroco;
    }

    public BigDecimalFilter totalTroco() {
        if (totalTroco == null) {
            totalTroco = new BigDecimalFilter();
        }
        return totalTroco;
    }

    public void setTotalTroco(BigDecimalFilter totalTroco) {
        this.totalTroco = totalTroco;
    }

    public BooleanFilter getIsNovo() {
        return isNovo;
    }

    public BooleanFilter isNovo() {
        if (isNovo == null) {
            isNovo = new BooleanFilter();
        }
        return isNovo;
    }

    public void setIsNovo(BooleanFilter isNovo) {
        this.isNovo = isNovo;
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

    public BigDecimalFilter getDebito() {
        return debito;
    }

    public BigDecimalFilter debito() {
        if (debito == null) {
            debito = new BigDecimalFilter();
        }
        return debito;
    }

    public void setDebito(BigDecimalFilter debito) {
        this.debito = debito;
    }

    public BigDecimalFilter getCredito() {
        return credito;
    }

    public BigDecimalFilter credito() {
        if (credito == null) {
            credito = new BigDecimalFilter();
        }
        return credito;
    }

    public void setCredito(BigDecimalFilter credito) {
        this.credito = credito;
    }

    public BooleanFilter getIsFiscalizado() {
        return isFiscalizado;
    }

    public BooleanFilter isFiscalizado() {
        if (isFiscalizado == null) {
            isFiscalizado = new BooleanFilter();
        }
        return isFiscalizado;
    }

    public void setIsFiscalizado(BooleanFilter isFiscalizado) {
        this.isFiscalizado = isFiscalizado;
    }

    public StringFilter getSignText() {
        return signText;
    }

    public StringFilter signText() {
        if (signText == null) {
            signText = new StringFilter();
        }
        return signText;
    }

    public void setSignText(StringFilter signText) {
        this.signText = signText;
    }

    public StringFilter getHash() {
        return hash;
    }

    public StringFilter hash() {
        if (hash == null) {
            hash = new StringFilter();
        }
        return hash;
    }

    public void setHash(StringFilter hash) {
        this.hash = hash;
    }

    public StringFilter getHashShort() {
        return hashShort;
    }

    public StringFilter hashShort() {
        if (hashShort == null) {
            hashShort = new StringFilter();
        }
        return hashShort;
    }

    public void setHashShort(StringFilter hashShort) {
        this.hashShort = hashShort;
    }

    public StringFilter getHashControl() {
        return hashControl;
    }

    public StringFilter hashControl() {
        if (hashControl == null) {
            hashControl = new StringFilter();
        }
        return hashControl;
    }

    public void setHashControl(StringFilter hashControl) {
        this.hashControl = hashControl;
    }

    public IntegerFilter getKeyVersion() {
        return keyVersion;
    }

    public IntegerFilter keyVersion() {
        if (keyVersion == null) {
            keyVersion = new IntegerFilter();
        }
        return keyVersion;
    }

    public void setKeyVersion(IntegerFilter keyVersion) {
        this.keyVersion = keyVersion;
    }

    public EstadoDocumentoComercialFilter getEstado() {
        return estado;
    }

    public EstadoDocumentoComercialFilter estado() {
        if (estado == null) {
            estado = new EstadoDocumentoComercialFilter();
        }
        return estado;
    }

    public void setEstado(EstadoDocumentoComercialFilter estado) {
        this.estado = estado;
    }

    public StringFilter getOrigem() {
        return origem;
    }

    public StringFilter origem() {
        if (origem == null) {
            origem = new StringFilter();
        }
        return origem;
    }

    public void setOrigem(StringFilter origem) {
        this.origem = origem;
    }

    public LongFilter getAplicacoesReciboId() {
        return aplicacoesReciboId;
    }

    public LongFilter aplicacoesReciboId() {
        if (aplicacoesReciboId == null) {
            aplicacoesReciboId = new LongFilter();
        }
        return aplicacoesReciboId;
    }

    public void setAplicacoesReciboId(LongFilter aplicacoesReciboId) {
        this.aplicacoesReciboId = aplicacoesReciboId;
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

    public LongFilter getDocumentoComercialId() {
        return documentoComercialId;
    }

    public LongFilter documentoComercialId() {
        if (documentoComercialId == null) {
            documentoComercialId = new LongFilter();
        }
        return documentoComercialId;
    }

    public void setDocumentoComercialId(LongFilter documentoComercialId) {
        this.documentoComercialId = documentoComercialId;
    }

    public LongFilter getTransacaoId() {
        return transacaoId;
    }

    public LongFilter transacaoId() {
        if (transacaoId == null) {
            transacaoId = new LongFilter();
        }
        return transacaoId;
    }

    public void setTransacaoId(LongFilter transacaoId) {
        this.transacaoId = transacaoId;
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
        final ReciboCriteria that = (ReciboCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(data, that.data) &&
            Objects.equals(vencimento, that.vencimento) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(totalSemImposto, that.totalSemImposto) &&
            Objects.equals(totalComImposto, that.totalComImposto) &&
            Objects.equals(totalDescontoComercial, that.totalDescontoComercial) &&
            Objects.equals(totalDescontoFinanceiro, that.totalDescontoFinanceiro) &&
            Objects.equals(totalIVA, that.totalIVA) &&
            Objects.equals(totalRetencao, that.totalRetencao) &&
            Objects.equals(totalJuro, that.totalJuro) &&
            Objects.equals(cambio, that.cambio) &&
            Objects.equals(totalMoedaEstrangeira, that.totalMoedaEstrangeira) &&
            Objects.equals(totalPagar, that.totalPagar) &&
            Objects.equals(totalPago, that.totalPago) &&
            Objects.equals(totalFalta, that.totalFalta) &&
            Objects.equals(totalTroco, that.totalTroco) &&
            Objects.equals(isNovo, that.isNovo) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(debito, that.debito) &&
            Objects.equals(credito, that.credito) &&
            Objects.equals(isFiscalizado, that.isFiscalizado) &&
            Objects.equals(signText, that.signText) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(hashShort, that.hashShort) &&
            Objects.equals(hashControl, that.hashControl) &&
            Objects.equals(keyVersion, that.keyVersion) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(origem, that.origem) &&
            Objects.equals(aplicacoesReciboId, that.aplicacoesReciboId) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(matriculaId, that.matriculaId) &&
            Objects.equals(documentoComercialId, that.documentoComercialId) &&
            Objects.equals(transacaoId, that.transacaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            data,
            vencimento,
            numero,
            totalSemImposto,
            totalComImposto,
            totalDescontoComercial,
            totalDescontoFinanceiro,
            totalIVA,
            totalRetencao,
            totalJuro,
            cambio,
            totalMoedaEstrangeira,
            totalPagar,
            totalPago,
            totalFalta,
            totalTroco,
            isNovo,
            timestamp,
            debito,
            credito,
            isFiscalizado,
            signText,
            hash,
            hashShort,
            hashControl,
            keyVersion,
            estado,
            origem,
            aplicacoesReciboId,
            anoLectivoId,
            utilizadorId,
            matriculaId,
            documentoComercialId,
            transacaoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReciboCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (data != null ? "data=" + data + ", " : "") +
            (vencimento != null ? "vencimento=" + vencimento + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (totalSemImposto != null ? "totalSemImposto=" + totalSemImposto + ", " : "") +
            (totalComImposto != null ? "totalComImposto=" + totalComImposto + ", " : "") +
            (totalDescontoComercial != null ? "totalDescontoComercial=" + totalDescontoComercial + ", " : "") +
            (totalDescontoFinanceiro != null ? "totalDescontoFinanceiro=" + totalDescontoFinanceiro + ", " : "") +
            (totalIVA != null ? "totalIVA=" + totalIVA + ", " : "") +
            (totalRetencao != null ? "totalRetencao=" + totalRetencao + ", " : "") +
            (totalJuro != null ? "totalJuro=" + totalJuro + ", " : "") +
            (cambio != null ? "cambio=" + cambio + ", " : "") +
            (totalMoedaEstrangeira != null ? "totalMoedaEstrangeira=" + totalMoedaEstrangeira + ", " : "") +
            (totalPagar != null ? "totalPagar=" + totalPagar + ", " : "") +
            (totalPago != null ? "totalPago=" + totalPago + ", " : "") +
            (totalFalta != null ? "totalFalta=" + totalFalta + ", " : "") +
            (totalTroco != null ? "totalTroco=" + totalTroco + ", " : "") +
            (isNovo != null ? "isNovo=" + isNovo + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (debito != null ? "debito=" + debito + ", " : "") +
            (credito != null ? "credito=" + credito + ", " : "") +
            (isFiscalizado != null ? "isFiscalizado=" + isFiscalizado + ", " : "") +
            (signText != null ? "signText=" + signText + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (hashShort != null ? "hashShort=" + hashShort + ", " : "") +
            (hashControl != null ? "hashControl=" + hashControl + ", " : "") +
            (keyVersion != null ? "keyVersion=" + keyVersion + ", " : "") +
            (estado != null ? "estado=" + estado + ", " : "") +
            (origem != null ? "origem=" + origem + ", " : "") +
            (aplicacoesReciboId != null ? "aplicacoesReciboId=" + aplicacoesReciboId + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (matriculaId != null ? "matriculaId=" + matriculaId + ", " : "") +
            (documentoComercialId != null ? "documentoComercialId=" + documentoComercialId + ", " : "") +
            (transacaoId != null ? "transacaoId=" + transacaoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
