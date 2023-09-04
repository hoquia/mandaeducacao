package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.TransferenciaSaldo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransferenciaSaldoDTO implements Serializable {

    private Long id;

    @DecimalMin(value = "0")
    private BigDecimal montante;

    private Boolean isMesmaConta;

    @Lob
    private String descricao;

    private ZonedDateTime timestamp;

    private DiscenteDTO de;

    private DiscenteDTO para;

    private UserDTO utilizador;

    private LookupItemDTO motivoTransferencia;

    private Set<TransacaoDTO> transacoes = new HashSet<>();

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

    public Boolean getIsMesmaConta() {
        return isMesmaConta;
    }

    public void setIsMesmaConta(Boolean isMesmaConta) {
        this.isMesmaConta = isMesmaConta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public DiscenteDTO getDe() {
        return de;
    }

    public void setDe(DiscenteDTO de) {
        this.de = de;
    }

    public DiscenteDTO getPara() {
        return para;
    }

    public void setPara(DiscenteDTO para) {
        this.para = para;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public LookupItemDTO getMotivoTransferencia() {
        return motivoTransferencia;
    }

    public void setMotivoTransferencia(LookupItemDTO motivoTransferencia) {
        this.motivoTransferencia = motivoTransferencia;
    }

    public Set<TransacaoDTO> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(Set<TransacaoDTO> transacoes) {
        this.transacoes = transacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferenciaSaldoDTO)) {
            return false;
        }

        TransferenciaSaldoDTO transferenciaSaldoDTO = (TransferenciaSaldoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transferenciaSaldoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferenciaSaldoDTO{" +
            "id=" + getId() +
            ", montante=" + getMontante() +
            ", isMesmaConta='" + getIsMesmaConta() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", de=" + getDe() +
            ", para=" + getPara() +
            ", utilizador=" + getUtilizador() +
            ", motivoTransferencia=" + getMotivoTransferencia() +
            ", transacoes=" + getTransacoes() +
            "}";
    }
}
