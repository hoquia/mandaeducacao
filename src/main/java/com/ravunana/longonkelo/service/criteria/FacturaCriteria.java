package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.EstadoDocumentoComercial;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Factura} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.FacturaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /facturas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FacturaCriteria implements Serializable, Criteria {

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

    private StringFilter numero;

    private StringFilter codigoEntrega;

    private LocalDateFilter dataEmissao;

    private LocalDateFilter dataVencimento;

    private StringFilter cae;

    private ZonedDateTimeFilter inicioTransporte;

    private ZonedDateTimeFilter fimTransporte;

    private EstadoDocumentoComercialFilter estado;

    private StringFilter origem;

    private ZonedDateTimeFilter timestamp;

    private BooleanFilter isMoedaEntrangeira;

    private StringFilter moeda;

    private BigDecimalFilter cambio;

    private BigDecimalFilter totalMoedaEntrangeira;

    private BigDecimalFilter totalIliquido;

    private BigDecimalFilter totalDescontoComercial;

    private BigDecimalFilter totalLiquido;

    private BigDecimalFilter totalImpostoIVA;

    private BigDecimalFilter totalImpostoEspecialConsumo;

    private BigDecimalFilter totalDescontoFinanceiro;

    private BigDecimalFilter totalFactura;

    private BigDecimalFilter totalImpostoRetencaoFonte;

    private BigDecimalFilter totalPagar;

    private BigDecimalFilter debito;

    private BigDecimalFilter credito;

    private BigDecimalFilter totalPago;

    private BigDecimalFilter totalDiferenca;

    private BooleanFilter isAutoFacturacao;

    private BooleanFilter isRegimeCaixa;

    private BooleanFilter isEmitidaNomeEContaTerceiro;

    private BooleanFilter isNovo;

    private BooleanFilter isFiscalizado;

    private StringFilter signText;

    private StringFilter hash;

    private StringFilter hashShort;

    private StringFilter hashControl;

    private IntegerFilter keyVersion;

    private LongFilter facturaId;

    private LongFilter itemsFacturaId;

    private LongFilter aplicacoesFacturaId;

    private LongFilter resumosImpostoId;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter motivoAnulacaoId;

    private LongFilter matriculaId;

    private LongFilter referenciaId;

    private LongFilter documentoComercialId;

    private Boolean distinct;

    public FacturaCriteria() {}

    public FacturaCriteria(FacturaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.codigoEntrega = other.codigoEntrega == null ? null : other.codigoEntrega.copy();
        this.dataEmissao = other.dataEmissao == null ? null : other.dataEmissao.copy();
        this.dataVencimento = other.dataVencimento == null ? null : other.dataVencimento.copy();
        this.cae = other.cae == null ? null : other.cae.copy();
        this.inicioTransporte = other.inicioTransporte == null ? null : other.inicioTransporte.copy();
        this.fimTransporte = other.fimTransporte == null ? null : other.fimTransporte.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.origem = other.origem == null ? null : other.origem.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.isMoedaEntrangeira = other.isMoedaEntrangeira == null ? null : other.isMoedaEntrangeira.copy();
        this.moeda = other.moeda == null ? null : other.moeda.copy();
        this.cambio = other.cambio == null ? null : other.cambio.copy();
        this.totalMoedaEntrangeira = other.totalMoedaEntrangeira == null ? null : other.totalMoedaEntrangeira.copy();
        this.totalIliquido = other.totalIliquido == null ? null : other.totalIliquido.copy();
        this.totalDescontoComercial = other.totalDescontoComercial == null ? null : other.totalDescontoComercial.copy();
        this.totalLiquido = other.totalLiquido == null ? null : other.totalLiquido.copy();
        this.totalImpostoIVA = other.totalImpostoIVA == null ? null : other.totalImpostoIVA.copy();
        this.totalImpostoEspecialConsumo = other.totalImpostoEspecialConsumo == null ? null : other.totalImpostoEspecialConsumo.copy();
        this.totalDescontoFinanceiro = other.totalDescontoFinanceiro == null ? null : other.totalDescontoFinanceiro.copy();
        this.totalFactura = other.totalFactura == null ? null : other.totalFactura.copy();
        this.totalImpostoRetencaoFonte = other.totalImpostoRetencaoFonte == null ? null : other.totalImpostoRetencaoFonte.copy();
        this.totalPagar = other.totalPagar == null ? null : other.totalPagar.copy();
        this.debito = other.debito == null ? null : other.debito.copy();
        this.credito = other.credito == null ? null : other.credito.copy();
        this.totalPago = other.totalPago == null ? null : other.totalPago.copy();
        this.totalDiferenca = other.totalDiferenca == null ? null : other.totalDiferenca.copy();
        this.isAutoFacturacao = other.isAutoFacturacao == null ? null : other.isAutoFacturacao.copy();
        this.isRegimeCaixa = other.isRegimeCaixa == null ? null : other.isRegimeCaixa.copy();
        this.isEmitidaNomeEContaTerceiro = other.isEmitidaNomeEContaTerceiro == null ? null : other.isEmitidaNomeEContaTerceiro.copy();
        this.isNovo = other.isNovo == null ? null : other.isNovo.copy();
        this.isFiscalizado = other.isFiscalizado == null ? null : other.isFiscalizado.copy();
        this.signText = other.signText == null ? null : other.signText.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.hashShort = other.hashShort == null ? null : other.hashShort.copy();
        this.hashControl = other.hashControl == null ? null : other.hashControl.copy();
        this.keyVersion = other.keyVersion == null ? null : other.keyVersion.copy();
        this.facturaId = other.facturaId == null ? null : other.facturaId.copy();
        this.itemsFacturaId = other.itemsFacturaId == null ? null : other.itemsFacturaId.copy();
        this.aplicacoesFacturaId = other.aplicacoesFacturaId == null ? null : other.aplicacoesFacturaId.copy();
        this.resumosImpostoId = other.resumosImpostoId == null ? null : other.resumosImpostoId.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.motivoAnulacaoId = other.motivoAnulacaoId == null ? null : other.motivoAnulacaoId.copy();
        this.matriculaId = other.matriculaId == null ? null : other.matriculaId.copy();
        this.referenciaId = other.referenciaId == null ? null : other.referenciaId.copy();
        this.documentoComercialId = other.documentoComercialId == null ? null : other.documentoComercialId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FacturaCriteria copy() {
        return new FacturaCriteria(this);
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

    public StringFilter getCodigoEntrega() {
        return codigoEntrega;
    }

    public StringFilter codigoEntrega() {
        if (codigoEntrega == null) {
            codigoEntrega = new StringFilter();
        }
        return codigoEntrega;
    }

    public void setCodigoEntrega(StringFilter codigoEntrega) {
        this.codigoEntrega = codigoEntrega;
    }

    public LocalDateFilter getDataEmissao() {
        return dataEmissao;
    }

    public LocalDateFilter dataEmissao() {
        if (dataEmissao == null) {
            dataEmissao = new LocalDateFilter();
        }
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateFilter dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDateFilter getDataVencimento() {
        return dataVencimento;
    }

    public LocalDateFilter dataVencimento() {
        if (dataVencimento == null) {
            dataVencimento = new LocalDateFilter();
        }
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateFilter dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public StringFilter getCae() {
        return cae;
    }

    public StringFilter cae() {
        if (cae == null) {
            cae = new StringFilter();
        }
        return cae;
    }

    public void setCae(StringFilter cae) {
        this.cae = cae;
    }

    public ZonedDateTimeFilter getInicioTransporte() {
        return inicioTransporte;
    }

    public ZonedDateTimeFilter inicioTransporte() {
        if (inicioTransporte == null) {
            inicioTransporte = new ZonedDateTimeFilter();
        }
        return inicioTransporte;
    }

    public void setInicioTransporte(ZonedDateTimeFilter inicioTransporte) {
        this.inicioTransporte = inicioTransporte;
    }

    public ZonedDateTimeFilter getFimTransporte() {
        return fimTransporte;
    }

    public ZonedDateTimeFilter fimTransporte() {
        if (fimTransporte == null) {
            fimTransporte = new ZonedDateTimeFilter();
        }
        return fimTransporte;
    }

    public void setFimTransporte(ZonedDateTimeFilter fimTransporte) {
        this.fimTransporte = fimTransporte;
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

    public BooleanFilter getIsMoedaEntrangeira() {
        return isMoedaEntrangeira;
    }

    public BooleanFilter isMoedaEntrangeira() {
        if (isMoedaEntrangeira == null) {
            isMoedaEntrangeira = new BooleanFilter();
        }
        return isMoedaEntrangeira;
    }

    public void setIsMoedaEntrangeira(BooleanFilter isMoedaEntrangeira) {
        this.isMoedaEntrangeira = isMoedaEntrangeira;
    }

    public StringFilter getMoeda() {
        return moeda;
    }

    public StringFilter moeda() {
        if (moeda == null) {
            moeda = new StringFilter();
        }
        return moeda;
    }

    public void setMoeda(StringFilter moeda) {
        this.moeda = moeda;
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

    public BigDecimalFilter getTotalMoedaEntrangeira() {
        return totalMoedaEntrangeira;
    }

    public BigDecimalFilter totalMoedaEntrangeira() {
        if (totalMoedaEntrangeira == null) {
            totalMoedaEntrangeira = new BigDecimalFilter();
        }
        return totalMoedaEntrangeira;
    }

    public void setTotalMoedaEntrangeira(BigDecimalFilter totalMoedaEntrangeira) {
        this.totalMoedaEntrangeira = totalMoedaEntrangeira;
    }

    public BigDecimalFilter getTotalIliquido() {
        return totalIliquido;
    }

    public BigDecimalFilter totalIliquido() {
        if (totalIliquido == null) {
            totalIliquido = new BigDecimalFilter();
        }
        return totalIliquido;
    }

    public void setTotalIliquido(BigDecimalFilter totalIliquido) {
        this.totalIliquido = totalIliquido;
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

    public BigDecimalFilter getTotalLiquido() {
        return totalLiquido;
    }

    public BigDecimalFilter totalLiquido() {
        if (totalLiquido == null) {
            totalLiquido = new BigDecimalFilter();
        }
        return totalLiquido;
    }

    public void setTotalLiquido(BigDecimalFilter totalLiquido) {
        this.totalLiquido = totalLiquido;
    }

    public BigDecimalFilter getTotalImpostoIVA() {
        return totalImpostoIVA;
    }

    public BigDecimalFilter totalImpostoIVA() {
        if (totalImpostoIVA == null) {
            totalImpostoIVA = new BigDecimalFilter();
        }
        return totalImpostoIVA;
    }

    public void setTotalImpostoIVA(BigDecimalFilter totalImpostoIVA) {
        this.totalImpostoIVA = totalImpostoIVA;
    }

    public BigDecimalFilter getTotalImpostoEspecialConsumo() {
        return totalImpostoEspecialConsumo;
    }

    public BigDecimalFilter totalImpostoEspecialConsumo() {
        if (totalImpostoEspecialConsumo == null) {
            totalImpostoEspecialConsumo = new BigDecimalFilter();
        }
        return totalImpostoEspecialConsumo;
    }

    public void setTotalImpostoEspecialConsumo(BigDecimalFilter totalImpostoEspecialConsumo) {
        this.totalImpostoEspecialConsumo = totalImpostoEspecialConsumo;
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

    public BigDecimalFilter getTotalFactura() {
        return totalFactura;
    }

    public BigDecimalFilter totalFactura() {
        if (totalFactura == null) {
            totalFactura = new BigDecimalFilter();
        }
        return totalFactura;
    }

    public void setTotalFactura(BigDecimalFilter totalFactura) {
        this.totalFactura = totalFactura;
    }

    public BigDecimalFilter getTotalImpostoRetencaoFonte() {
        return totalImpostoRetencaoFonte;
    }

    public BigDecimalFilter totalImpostoRetencaoFonte() {
        if (totalImpostoRetencaoFonte == null) {
            totalImpostoRetencaoFonte = new BigDecimalFilter();
        }
        return totalImpostoRetencaoFonte;
    }

    public void setTotalImpostoRetencaoFonte(BigDecimalFilter totalImpostoRetencaoFonte) {
        this.totalImpostoRetencaoFonte = totalImpostoRetencaoFonte;
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

    public BigDecimalFilter getTotalDiferenca() {
        return totalDiferenca;
    }

    public BigDecimalFilter totalDiferenca() {
        if (totalDiferenca == null) {
            totalDiferenca = new BigDecimalFilter();
        }
        return totalDiferenca;
    }

    public void setTotalDiferenca(BigDecimalFilter totalDiferenca) {
        this.totalDiferenca = totalDiferenca;
    }

    public BooleanFilter getIsAutoFacturacao() {
        return isAutoFacturacao;
    }

    public BooleanFilter isAutoFacturacao() {
        if (isAutoFacturacao == null) {
            isAutoFacturacao = new BooleanFilter();
        }
        return isAutoFacturacao;
    }

    public void setIsAutoFacturacao(BooleanFilter isAutoFacturacao) {
        this.isAutoFacturacao = isAutoFacturacao;
    }

    public BooleanFilter getIsRegimeCaixa() {
        return isRegimeCaixa;
    }

    public BooleanFilter isRegimeCaixa() {
        if (isRegimeCaixa == null) {
            isRegimeCaixa = new BooleanFilter();
        }
        return isRegimeCaixa;
    }

    public void setIsRegimeCaixa(BooleanFilter isRegimeCaixa) {
        this.isRegimeCaixa = isRegimeCaixa;
    }

    public BooleanFilter getIsEmitidaNomeEContaTerceiro() {
        return isEmitidaNomeEContaTerceiro;
    }

    public BooleanFilter isEmitidaNomeEContaTerceiro() {
        if (isEmitidaNomeEContaTerceiro == null) {
            isEmitidaNomeEContaTerceiro = new BooleanFilter();
        }
        return isEmitidaNomeEContaTerceiro;
    }

    public void setIsEmitidaNomeEContaTerceiro(BooleanFilter isEmitidaNomeEContaTerceiro) {
        this.isEmitidaNomeEContaTerceiro = isEmitidaNomeEContaTerceiro;
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

    public LongFilter getFacturaId() {
        return facturaId;
    }

    public LongFilter facturaId() {
        if (facturaId == null) {
            facturaId = new LongFilter();
        }
        return facturaId;
    }

    public void setFacturaId(LongFilter facturaId) {
        this.facturaId = facturaId;
    }

    public LongFilter getItemsFacturaId() {
        return itemsFacturaId;
    }

    public LongFilter itemsFacturaId() {
        if (itemsFacturaId == null) {
            itemsFacturaId = new LongFilter();
        }
        return itemsFacturaId;
    }

    public void setItemsFacturaId(LongFilter itemsFacturaId) {
        this.itemsFacturaId = itemsFacturaId;
    }

    public LongFilter getAplicacoesFacturaId() {
        return aplicacoesFacturaId;
    }

    public LongFilter aplicacoesFacturaId() {
        if (aplicacoesFacturaId == null) {
            aplicacoesFacturaId = new LongFilter();
        }
        return aplicacoesFacturaId;
    }

    public void setAplicacoesFacturaId(LongFilter aplicacoesFacturaId) {
        this.aplicacoesFacturaId = aplicacoesFacturaId;
    }

    public LongFilter getResumosImpostoId() {
        return resumosImpostoId;
    }

    public LongFilter resumosImpostoId() {
        if (resumosImpostoId == null) {
            resumosImpostoId = new LongFilter();
        }
        return resumosImpostoId;
    }

    public void setResumosImpostoId(LongFilter resumosImpostoId) {
        this.resumosImpostoId = resumosImpostoId;
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

    public LongFilter getMotivoAnulacaoId() {
        return motivoAnulacaoId;
    }

    public LongFilter motivoAnulacaoId() {
        if (motivoAnulacaoId == null) {
            motivoAnulacaoId = new LongFilter();
        }
        return motivoAnulacaoId;
    }

    public void setMotivoAnulacaoId(LongFilter motivoAnulacaoId) {
        this.motivoAnulacaoId = motivoAnulacaoId;
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
        final FacturaCriteria that = (FacturaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(codigoEntrega, that.codigoEntrega) &&
            Objects.equals(dataEmissao, that.dataEmissao) &&
            Objects.equals(dataVencimento, that.dataVencimento) &&
            Objects.equals(cae, that.cae) &&
            Objects.equals(inicioTransporte, that.inicioTransporte) &&
            Objects.equals(fimTransporte, that.fimTransporte) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(origem, that.origem) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(isMoedaEntrangeira, that.isMoedaEntrangeira) &&
            Objects.equals(moeda, that.moeda) &&
            Objects.equals(cambio, that.cambio) &&
            Objects.equals(totalMoedaEntrangeira, that.totalMoedaEntrangeira) &&
            Objects.equals(totalIliquido, that.totalIliquido) &&
            Objects.equals(totalDescontoComercial, that.totalDescontoComercial) &&
            Objects.equals(totalLiquido, that.totalLiquido) &&
            Objects.equals(totalImpostoIVA, that.totalImpostoIVA) &&
            Objects.equals(totalImpostoEspecialConsumo, that.totalImpostoEspecialConsumo) &&
            Objects.equals(totalDescontoFinanceiro, that.totalDescontoFinanceiro) &&
            Objects.equals(totalFactura, that.totalFactura) &&
            Objects.equals(totalImpostoRetencaoFonte, that.totalImpostoRetencaoFonte) &&
            Objects.equals(totalPagar, that.totalPagar) &&
            Objects.equals(debito, that.debito) &&
            Objects.equals(credito, that.credito) &&
            Objects.equals(totalPago, that.totalPago) &&
            Objects.equals(totalDiferenca, that.totalDiferenca) &&
            Objects.equals(isAutoFacturacao, that.isAutoFacturacao) &&
            Objects.equals(isRegimeCaixa, that.isRegimeCaixa) &&
            Objects.equals(isEmitidaNomeEContaTerceiro, that.isEmitidaNomeEContaTerceiro) &&
            Objects.equals(isNovo, that.isNovo) &&
            Objects.equals(isFiscalizado, that.isFiscalizado) &&
            Objects.equals(signText, that.signText) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(hashShort, that.hashShort) &&
            Objects.equals(hashControl, that.hashControl) &&
            Objects.equals(keyVersion, that.keyVersion) &&
            Objects.equals(facturaId, that.facturaId) &&
            Objects.equals(itemsFacturaId, that.itemsFacturaId) &&
            Objects.equals(aplicacoesFacturaId, that.aplicacoesFacturaId) &&
            Objects.equals(resumosImpostoId, that.resumosImpostoId) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(motivoAnulacaoId, that.motivoAnulacaoId) &&
            Objects.equals(matriculaId, that.matriculaId) &&
            Objects.equals(referenciaId, that.referenciaId) &&
            Objects.equals(documentoComercialId, that.documentoComercialId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            numero,
            codigoEntrega,
            dataEmissao,
            dataVencimento,
            cae,
            inicioTransporte,
            fimTransporte,
            estado,
            origem,
            timestamp,
            isMoedaEntrangeira,
            moeda,
            cambio,
            totalMoedaEntrangeira,
            totalIliquido,
            totalDescontoComercial,
            totalLiquido,
            totalImpostoIVA,
            totalImpostoEspecialConsumo,
            totalDescontoFinanceiro,
            totalFactura,
            totalImpostoRetencaoFonte,
            totalPagar,
            debito,
            credito,
            totalPago,
            totalDiferenca,
            isAutoFacturacao,
            isRegimeCaixa,
            isEmitidaNomeEContaTerceiro,
            isNovo,
            isFiscalizado,
            signText,
            hash,
            hashShort,
            hashControl,
            keyVersion,
            facturaId,
            itemsFacturaId,
            aplicacoesFacturaId,
            resumosImpostoId,
            anoLectivoId,
            utilizadorId,
            motivoAnulacaoId,
            matriculaId,
            referenciaId,
            documentoComercialId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacturaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (codigoEntrega != null ? "codigoEntrega=" + codigoEntrega + ", " : "") +
            (dataEmissao != null ? "dataEmissao=" + dataEmissao + ", " : "") +
            (dataVencimento != null ? "dataVencimento=" + dataVencimento + ", " : "") +
            (cae != null ? "cae=" + cae + ", " : "") +
            (inicioTransporte != null ? "inicioTransporte=" + inicioTransporte + ", " : "") +
            (fimTransporte != null ? "fimTransporte=" + fimTransporte + ", " : "") +
            (estado != null ? "estado=" + estado + ", " : "") +
            (origem != null ? "origem=" + origem + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (isMoedaEntrangeira != null ? "isMoedaEntrangeira=" + isMoedaEntrangeira + ", " : "") +
            (moeda != null ? "moeda=" + moeda + ", " : "") +
            (cambio != null ? "cambio=" + cambio + ", " : "") +
            (totalMoedaEntrangeira != null ? "totalMoedaEntrangeira=" + totalMoedaEntrangeira + ", " : "") +
            (totalIliquido != null ? "totalIliquido=" + totalIliquido + ", " : "") +
            (totalDescontoComercial != null ? "totalDescontoComercial=" + totalDescontoComercial + ", " : "") +
            (totalLiquido != null ? "totalLiquido=" + totalLiquido + ", " : "") +
            (totalImpostoIVA != null ? "totalImpostoIVA=" + totalImpostoIVA + ", " : "") +
            (totalImpostoEspecialConsumo != null ? "totalImpostoEspecialConsumo=" + totalImpostoEspecialConsumo + ", " : "") +
            (totalDescontoFinanceiro != null ? "totalDescontoFinanceiro=" + totalDescontoFinanceiro + ", " : "") +
            (totalFactura != null ? "totalFactura=" + totalFactura + ", " : "") +
            (totalImpostoRetencaoFonte != null ? "totalImpostoRetencaoFonte=" + totalImpostoRetencaoFonte + ", " : "") +
            (totalPagar != null ? "totalPagar=" + totalPagar + ", " : "") +
            (debito != null ? "debito=" + debito + ", " : "") +
            (credito != null ? "credito=" + credito + ", " : "") +
            (totalPago != null ? "totalPago=" + totalPago + ", " : "") +
            (totalDiferenca != null ? "totalDiferenca=" + totalDiferenca + ", " : "") +
            (isAutoFacturacao != null ? "isAutoFacturacao=" + isAutoFacturacao + ", " : "") +
            (isRegimeCaixa != null ? "isRegimeCaixa=" + isRegimeCaixa + ", " : "") +
            (isEmitidaNomeEContaTerceiro != null ? "isEmitidaNomeEContaTerceiro=" + isEmitidaNomeEContaTerceiro + ", " : "") +
            (isNovo != null ? "isNovo=" + isNovo + ", " : "") +
            (isFiscalizado != null ? "isFiscalizado=" + isFiscalizado + ", " : "") +
            (signText != null ? "signText=" + signText + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (hashShort != null ? "hashShort=" + hashShort + ", " : "") +
            (hashControl != null ? "hashControl=" + hashControl + ", " : "") +
            (keyVersion != null ? "keyVersion=" + keyVersion + ", " : "") +
            (facturaId != null ? "facturaId=" + facturaId + ", " : "") +
            (itemsFacturaId != null ? "itemsFacturaId=" + itemsFacturaId + ", " : "") +
            (aplicacoesFacturaId != null ? "aplicacoesFacturaId=" + aplicacoesFacturaId + ", " : "") +
            (resumosImpostoId != null ? "resumosImpostoId=" + resumosImpostoId + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (motivoAnulacaoId != null ? "motivoAnulacaoId=" + motivoAnulacaoId + ", " : "") +
            (matriculaId != null ? "matriculaId=" + matriculaId + ", " : "") +
            (referenciaId != null ? "referenciaId=" + referenciaId + ", " : "") +
            (documentoComercialId != null ? "documentoComercialId=" + documentoComercialId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
