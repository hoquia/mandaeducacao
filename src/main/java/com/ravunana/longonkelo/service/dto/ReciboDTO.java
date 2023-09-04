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
 * A DTO for the {@link com.ravunana.longonkelo.domain.Recibo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReciboDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate data;

    private LocalDate vencimento;

    @NotNull
    private String numero;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalSemImposto;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalComImposto;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalDescontoComercial;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalDescontoFinanceiro;

    @NotNull
    private BigDecimal totalIVA;

    @NotNull
    private BigDecimal totalRetencao;

    @NotNull
    private BigDecimal totalJuro;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal cambio;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalMoedaEstrangeira;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalPagar;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalPago;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalFalta;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalTroco;

    private Boolean isNovo;

    @NotNull
    private ZonedDateTime timestamp;

    @Lob
    private String descricao;

    @DecimalMin(value = "0")
    private BigDecimal debito;

    @DecimalMin(value = "0")
    private BigDecimal credito;

    private Boolean isFiscalizado;

    private String signText;

    @Size(max = 172)
    private String hash;

    private String hashShort;

    @Size(max = 70)
    private String hashControl;

    private Integer keyVersion;

    @NotNull
    private EstadoDocumentoComercial estado;

    @NotNull
    private String origem;

    private UserDTO utilizador;

    private MatriculaDTO matricula;

    private DocumentoComercialDTO documentoComercial;

    private TransacaoDTO transacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getTotalSemImposto() {
        return totalSemImposto;
    }

    public void setTotalSemImposto(BigDecimal totalSemImposto) {
        this.totalSemImposto = totalSemImposto;
    }

    public BigDecimal getTotalComImposto() {
        return totalComImposto;
    }

    public void setTotalComImposto(BigDecimal totalComImposto) {
        this.totalComImposto = totalComImposto;
    }

    public BigDecimal getTotalDescontoComercial() {
        return totalDescontoComercial;
    }

    public void setTotalDescontoComercial(BigDecimal totalDescontoComercial) {
        this.totalDescontoComercial = totalDescontoComercial;
    }

    public BigDecimal getTotalDescontoFinanceiro() {
        return totalDescontoFinanceiro;
    }

    public void setTotalDescontoFinanceiro(BigDecimal totalDescontoFinanceiro) {
        this.totalDescontoFinanceiro = totalDescontoFinanceiro;
    }

    public BigDecimal getTotalIVA() {
        return totalIVA;
    }

    public void setTotalIVA(BigDecimal totalIVA) {
        this.totalIVA = totalIVA;
    }

    public BigDecimal getTotalRetencao() {
        return totalRetencao;
    }

    public void setTotalRetencao(BigDecimal totalRetencao) {
        this.totalRetencao = totalRetencao;
    }

    public BigDecimal getTotalJuro() {
        return totalJuro;
    }

    public void setTotalJuro(BigDecimal totalJuro) {
        this.totalJuro = totalJuro;
    }

    public BigDecimal getCambio() {
        return cambio;
    }

    public void setCambio(BigDecimal cambio) {
        this.cambio = cambio;
    }

    public BigDecimal getTotalMoedaEstrangeira() {
        return totalMoedaEstrangeira;
    }

    public void setTotalMoedaEstrangeira(BigDecimal totalMoedaEstrangeira) {
        this.totalMoedaEstrangeira = totalMoedaEstrangeira;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public BigDecimal getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
    }

    public BigDecimal getTotalFalta() {
        return totalFalta;
    }

    public void setTotalFalta(BigDecimal totalFalta) {
        this.totalFalta = totalFalta;
    }

    public BigDecimal getTotalTroco() {
        return totalTroco;
    }

    public void setTotalTroco(BigDecimal totalTroco) {
        this.totalTroco = totalTroco;
    }

    public Boolean getIsNovo() {
        return isNovo;
    }

    public void setIsNovo(Boolean isNovo) {
        this.isNovo = isNovo;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public MatriculaDTO getMatricula() {
        return matricula;
    }

    public void setMatricula(MatriculaDTO matricula) {
        this.matricula = matricula;
    }

    public DocumentoComercialDTO getDocumentoComercial() {
        return documentoComercial;
    }

    public void setDocumentoComercial(DocumentoComercialDTO documentoComercial) {
        this.documentoComercial = documentoComercial;
    }

    public TransacaoDTO getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoDTO transacao) {
        this.transacao = transacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReciboDTO)) {
            return false;
        }

        ReciboDTO reciboDTO = (ReciboDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reciboDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReciboDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", vencimento='" + getVencimento() + "'" +
            ", numero='" + getNumero() + "'" +
            ", totalSemImposto=" + getTotalSemImposto() +
            ", totalComImposto=" + getTotalComImposto() +
            ", totalDescontoComercial=" + getTotalDescontoComercial() +
            ", totalDescontoFinanceiro=" + getTotalDescontoFinanceiro() +
            ", totalIVA=" + getTotalIVA() +
            ", totalRetencao=" + getTotalRetencao() +
            ", totalJuro=" + getTotalJuro() +
            ", cambio=" + getCambio() +
            ", totalMoedaEstrangeira=" + getTotalMoedaEstrangeira() +
            ", totalPagar=" + getTotalPagar() +
            ", totalPago=" + getTotalPago() +
            ", totalFalta=" + getTotalFalta() +
            ", totalTroco=" + getTotalTroco() +
            ", isNovo='" + getIsNovo() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", debito=" + getDebito() +
            ", credito=" + getCredito() +
            ", isFiscalizado='" + getIsFiscalizado() + "'" +
            ", signText='" + getSignText() + "'" +
            ", hash='" + getHash() + "'" +
            ", hashShort='" + getHashShort() + "'" +
            ", hashControl='" + getHashControl() + "'" +
            ", keyVersion=" + getKeyVersion() +
            ", estado='" + getEstado() + "'" +
            ", origem='" + getOrigem() + "'" +
            ", utilizador=" + getUtilizador() +
            ", matricula=" + getMatricula() +
            ", documentoComercial=" + getDocumentoComercial() +
            ", transacao=" + getTransacao() +
            "}";
    }
}
