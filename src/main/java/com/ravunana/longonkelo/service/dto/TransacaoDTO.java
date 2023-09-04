package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.EstadoPagamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Transacao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransacaoDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal montante;

    @NotNull
    private LocalDate data;

    @NotNull
    private String referencia;

    @NotNull
    private EstadoPagamento estado;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal saldo;

    @Lob
    private byte[] anexo;

    private String anexoContentType;
    private ZonedDateTime timestamp;

    private UserDTO utilizador;

    private LookupItemDTO moeda;

    private MatriculaDTO matricula;

    private MeioPagamentoDTO meioPagamento;

    private ContaDTO conta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontante() {
        return montante;
    }

    public void setMontante(BigDecimal montante) {
        this.montante = montante;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public EstadoPagamento getEstado() {
        return estado;
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public String getAnexoContentType() {
        return anexoContentType;
    }

    public void setAnexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public LookupItemDTO getMoeda() {
        return moeda;
    }

    public void setMoeda(LookupItemDTO moeda) {
        this.moeda = moeda;
    }

    public MatriculaDTO getMatricula() {
        return matricula;
    }

    public void setMatricula(MatriculaDTO matricula) {
        this.matricula = matricula;
    }

    public MeioPagamentoDTO getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(MeioPagamentoDTO meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public ContaDTO getConta() {
        return conta;
    }

    public void setConta(ContaDTO conta) {
        this.conta = conta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransacaoDTO)) {
            return false;
        }

        TransacaoDTO transacaoDTO = (TransacaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transacaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransacaoDTO{" +
            "id=" + getId() +
            ", montante=" + getMontante() +
            ", data='" + getData() + "'" +
            ", referencia='" + getReferencia() + "'" +
            ", estado='" + getEstado() + "'" +
            ", saldo=" + getSaldo() +
            ", anexo='" + getAnexo() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", utilizador=" + getUtilizador() +
            ", moeda=" + getMoeda() +
            ", matricula=" + getMatricula() +
            ", meioPagamento=" + getMeioPagamento() +
            ", conta=" + getConta() +
            "}";
    }
}
