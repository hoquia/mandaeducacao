package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.EstadoDocumentoComercial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Factura} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FacturaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 60)
    private String numero;

    private String codigoEntrega;

    @NotNull
    private LocalDate dataEmissao;

    @NotNull
    private LocalDate dataVencimento;

    @Size(max = 10)
    private String cae;

    @NotNull
    private ZonedDateTime inicioTransporte;

    @NotNull
    private ZonedDateTime fimTransporte;

    @Lob
    private String observacaoGeral;

    @Lob
    private String observacaoInterna;

    @NotNull
    private EstadoDocumentoComercial estado;

    @NotNull
    private String origem;

    @NotNull
    private ZonedDateTime timestamp;

    private Boolean isMoedaEntrangeira;

    @NotNull
    @Size(max = 12)
    private String moeda;

    @DecimalMin(value = "0")
    private BigDecimal cambio;

    @DecimalMin(value = "0")
    private BigDecimal totalMoedaEntrangeira;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalIliquido;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalDescontoComercial;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalLiquido;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalImpostoIVA;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalImpostoEspecialConsumo;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalDescontoFinanceiro;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalFactura;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalImpostoRetencaoFonte;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalPagar;

    @DecimalMin(value = "0")
    private BigDecimal debito;

    @DecimalMin(value = "0")
    private BigDecimal credito;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalPago;

    @NotNull
    private BigDecimal totalDiferenca;

    private Boolean isAutoFacturacao;

    private Boolean isRegimeCaixa;

    private Boolean isEmitidaNomeEContaTerceiro;

    private Boolean isNovo;

    private Boolean isFiscalizado;

    private String signText;

    @Size(max = 172)
    private String hash;

    private String hashShort;

    @Size(max = 70)
    private String hashControl;

    private Integer keyVersion;

    private UserDTO utilizador;

    private LookupItemDTO motivoAnulacao;

    private MatriculaDTO matricula;

    private FacturaDTO referencia;

    private DocumentoComercialDTO documentoComercial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoEntrega() {
        return codigoEntrega;
    }

    public void setCodigoEntrega(String codigoEntrega) {
        this.codigoEntrega = codigoEntrega;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getCae() {
        return cae;
    }

    public void setCae(String cae) {
        this.cae = cae;
    }

    public ZonedDateTime getInicioTransporte() {
        return inicioTransporte;
    }

    public void setInicioTransporte(ZonedDateTime inicioTransporte) {
        this.inicioTransporte = inicioTransporte;
    }

    public ZonedDateTime getFimTransporte() {
        return fimTransporte;
    }

    public void setFimTransporte(ZonedDateTime fimTransporte) {
        this.fimTransporte = fimTransporte;
    }

    public String getObservacaoGeral() {
        return observacaoGeral;
    }

    public void setObservacaoGeral(String observacaoGeral) {
        this.observacaoGeral = observacaoGeral;
    }

    public String getObservacaoInterna() {
        return observacaoInterna;
    }

    public void setObservacaoInterna(String observacaoInterna) {
        this.observacaoInterna = observacaoInterna;
    }

    public EstadoDocumentoComercial getEstado() {
        return estado;
    }

    public void setEstado(EstadoDocumentoComercial estado) {
        this.estado = estado;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsMoedaEntrangeira() {
        return isMoedaEntrangeira;
    }

    public void setIsMoedaEntrangeira(Boolean isMoedaEntrangeira) {
        this.isMoedaEntrangeira = isMoedaEntrangeira;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public BigDecimal getCambio() {
        return cambio;
    }

    public void setCambio(BigDecimal cambio) {
        this.cambio = cambio;
    }

    public BigDecimal getTotalMoedaEntrangeira() {
        return totalMoedaEntrangeira;
    }

    public void setTotalMoedaEntrangeira(BigDecimal totalMoedaEntrangeira) {
        this.totalMoedaEntrangeira = totalMoedaEntrangeira;
    }

    public BigDecimal getTotalIliquido() {
        return totalIliquido;
    }

    public void setTotalIliquido(BigDecimal totalIliquido) {
        this.totalIliquido = totalIliquido;
    }

    public BigDecimal getTotalDescontoComercial() {
        return totalDescontoComercial;
    }

    public void setTotalDescontoComercial(BigDecimal totalDescontoComercial) {
        this.totalDescontoComercial = totalDescontoComercial;
    }

    public BigDecimal getTotalLiquido() {
        return totalLiquido;
    }

    public void setTotalLiquido(BigDecimal totalLiquido) {
        this.totalLiquido = totalLiquido;
    }

    public BigDecimal getTotalImpostoIVA() {
        return totalImpostoIVA;
    }

    public void setTotalImpostoIVA(BigDecimal totalImpostoIVA) {
        this.totalImpostoIVA = totalImpostoIVA;
    }

    public BigDecimal getTotalImpostoEspecialConsumo() {
        return totalImpostoEspecialConsumo;
    }

    public void setTotalImpostoEspecialConsumo(BigDecimal totalImpostoEspecialConsumo) {
        this.totalImpostoEspecialConsumo = totalImpostoEspecialConsumo;
    }

    public BigDecimal getTotalDescontoFinanceiro() {
        return totalDescontoFinanceiro;
    }

    public void setTotalDescontoFinanceiro(BigDecimal totalDescontoFinanceiro) {
        this.totalDescontoFinanceiro = totalDescontoFinanceiro;
    }

    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }

    public BigDecimal getTotalImpostoRetencaoFonte() {
        return totalImpostoRetencaoFonte;
    }

    public void setTotalImpostoRetencaoFonte(BigDecimal totalImpostoRetencaoFonte) {
        this.totalImpostoRetencaoFonte = totalImpostoRetencaoFonte;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public BigDecimal getDebito() {
        return debito;
    }

    public void setDebito(BigDecimal debito) {
        this.debito = debito;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
    }

    public BigDecimal getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
    }

    public BigDecimal getTotalDiferenca() {
        return totalDiferenca;
    }

    public void setTotalDiferenca(BigDecimal totalDiferenca) {
        this.totalDiferenca = totalDiferenca;
    }

    public Boolean getIsAutoFacturacao() {
        return isAutoFacturacao;
    }

    public void setIsAutoFacturacao(Boolean isAutoFacturacao) {
        this.isAutoFacturacao = isAutoFacturacao;
    }

    public Boolean getIsRegimeCaixa() {
        return isRegimeCaixa;
    }

    public void setIsRegimeCaixa(Boolean isRegimeCaixa) {
        this.isRegimeCaixa = isRegimeCaixa;
    }

    public Boolean getIsEmitidaNomeEContaTerceiro() {
        return isEmitidaNomeEContaTerceiro;
    }

    public void setIsEmitidaNomeEContaTerceiro(Boolean isEmitidaNomeEContaTerceiro) {
        this.isEmitidaNomeEContaTerceiro = isEmitidaNomeEContaTerceiro;
    }

    public Boolean getIsNovo() {
        return isNovo;
    }

    public void setIsNovo(Boolean isNovo) {
        this.isNovo = isNovo;
    }

    public Boolean getIsFiscalizado() {
        return isFiscalizado;
    }

    public void setIsFiscalizado(Boolean isFiscalizado) {
        this.isFiscalizado = isFiscalizado;
    }

    public String getSignText() {
        return signText;
    }

    public void setSignText(String signText) {
        this.signText = signText;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHashShort() {
        return hashShort;
    }

    public void setHashShort(String hashShort) {
        this.hashShort = hashShort;
    }

    public String getHashControl() {
        return hashControl;
    }

    public void setHashControl(String hashControl) {
        this.hashControl = hashControl;
    }

    public Integer getKeyVersion() {
        return keyVersion;
    }

    public void setKeyVersion(Integer keyVersion) {
        this.keyVersion = keyVersion;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public LookupItemDTO getMotivoAnulacao() {
        return motivoAnulacao;
    }

    public void setMotivoAnulacao(LookupItemDTO motivoAnulacao) {
        this.motivoAnulacao = motivoAnulacao;
    }

    public MatriculaDTO getMatricula() {
        return matricula;
    }

    public void setMatricula(MatriculaDTO matricula) {
        this.matricula = matricula;
    }

    public FacturaDTO getReferencia() {
        return referencia;
    }

    public void setReferencia(FacturaDTO referencia) {
        this.referencia = referencia;
    }

    public DocumentoComercialDTO getDocumentoComercial() {
        return documentoComercial;
    }

    public void setDocumentoComercial(DocumentoComercialDTO documentoComercial) {
        this.documentoComercial = documentoComercial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FacturaDTO)) {
            return false;
        }

        FacturaDTO facturaDTO = (FacturaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, facturaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacturaDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", codigoEntrega='" + getCodigoEntrega() + "'" +
            ", dataEmissao='" + getDataEmissao() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", cae='" + getCae() + "'" +
            ", inicioTransporte='" + getInicioTransporte() + "'" +
            ", fimTransporte='" + getFimTransporte() + "'" +
            ", observacaoGeral='" + getObservacaoGeral() + "'" +
            ", observacaoInterna='" + getObservacaoInterna() + "'" +
            ", estado='" + getEstado() + "'" +
            ", origem='" + getOrigem() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", isMoedaEntrangeira='" + getIsMoedaEntrangeira() + "'" +
            ", moeda='" + getMoeda() + "'" +
            ", cambio=" + getCambio() +
            ", totalMoedaEntrangeira=" + getTotalMoedaEntrangeira() +
            ", totalIliquido=" + getTotalIliquido() +
            ", totalDescontoComercial=" + getTotalDescontoComercial() +
            ", totalLiquido=" + getTotalLiquido() +
            ", totalImpostoIVA=" + getTotalImpostoIVA() +
            ", totalImpostoEspecialConsumo=" + getTotalImpostoEspecialConsumo() +
            ", totalDescontoFinanceiro=" + getTotalDescontoFinanceiro() +
            ", totalFactura=" + getTotalFactura() +
            ", totalImpostoRetencaoFonte=" + getTotalImpostoRetencaoFonte() +
            ", totalPagar=" + getTotalPagar() +
            ", debito=" + getDebito() +
            ", credito=" + getCredito() +
            ", totalPago=" + getTotalPago() +
            ", totalDiferenca=" + getTotalDiferenca() +
            ", isAutoFacturacao='" + getIsAutoFacturacao() + "'" +
            ", isRegimeCaixa='" + getIsRegimeCaixa() + "'" +
            ", isEmitidaNomeEContaTerceiro='" + getIsEmitidaNomeEContaTerceiro() + "'" +
            ", isNovo='" + getIsNovo() + "'" +
            ", isFiscalizado='" + getIsFiscalizado() + "'" +
            ", signText='" + getSignText() + "'" +
            ", hash='" + getHash() + "'" +
            ", hashShort='" + getHashShort() + "'" +
            ", hashControl='" + getHashControl() + "'" +
            ", keyVersion=" + getKeyVersion() +
            ", utilizador=" + getUtilizador() +
            ", motivoAnulacao=" + getMotivoAnulacao() +
            ", matricula=" + getMatricula() +
            ", referencia=" + getReferencia() +
            ", documentoComercial=" + getDocumentoComercial() +
            "}";
    }
}
