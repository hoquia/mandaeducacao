package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.DocumentoFiscal;
import com.ravunana.longonkelo.domain.enumeration.ModuloDocumento;
import com.ravunana.longonkelo.domain.enumeration.OrigemDocumento;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.DocumentoComercial} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentoComercialDTO implements Serializable {

    private Long id;

    @NotNull
    private ModuloDocumento modulo;

    @NotNull
    private OrigemDocumento origem;

    @NotNull
    @Size(max = 6)
    private String siglaInterna;

    @NotNull
    private String descricao;

    @NotNull
    private DocumentoFiscal siglaFiscal;

    private Boolean isMovimentaEstoque;

    private Boolean isMovimentaCaixa;

    private Boolean isNotificaEntidade;

    private Boolean isNotificaGerente;

    private Boolean isEnviaSMS;

    private Boolean isEnviaEmail;

    private Boolean isEnviaPush;

    private Boolean validaCreditoDisponivel;

    private DocumentoComercialDTO transformaEm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModuloDocumento getModulo() {
        return modulo;
    }

    public void setModulo(ModuloDocumento modulo) {
        this.modulo = modulo;
    }

    public OrigemDocumento getOrigem() {
        return origem;
    }

    public void setOrigem(OrigemDocumento origem) {
        this.origem = origem;
    }

    public String getSiglaInterna() {
        return siglaInterna;
    }

    public void setSiglaInterna(String siglaInterna) {
        this.siglaInterna = siglaInterna;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public DocumentoFiscal getSiglaFiscal() {
        return siglaFiscal;
    }

    public void setSiglaFiscal(DocumentoFiscal siglaFiscal) {
        this.siglaFiscal = siglaFiscal;
    }

    public Boolean getIsMovimentaEstoque() {
        return isMovimentaEstoque;
    }

    public void setIsMovimentaEstoque(Boolean isMovimentaEstoque) {
        this.isMovimentaEstoque = isMovimentaEstoque;
    }

    public Boolean getIsMovimentaCaixa() {
        return isMovimentaCaixa;
    }

    public void setIsMovimentaCaixa(Boolean isMovimentaCaixa) {
        this.isMovimentaCaixa = isMovimentaCaixa;
    }

    public Boolean getIsNotificaEntidade() {
        return isNotificaEntidade;
    }

    public void setIsNotificaEntidade(Boolean isNotificaEntidade) {
        this.isNotificaEntidade = isNotificaEntidade;
    }

    public Boolean getIsNotificaGerente() {
        return isNotificaGerente;
    }

    public void setIsNotificaGerente(Boolean isNotificaGerente) {
        this.isNotificaGerente = isNotificaGerente;
    }

    public Boolean getIsEnviaSMS() {
        return isEnviaSMS;
    }

    public void setIsEnviaSMS(Boolean isEnviaSMS) {
        this.isEnviaSMS = isEnviaSMS;
    }

    public Boolean getIsEnviaEmail() {
        return isEnviaEmail;
    }

    public void setIsEnviaEmail(Boolean isEnviaEmail) {
        this.isEnviaEmail = isEnviaEmail;
    }

    public Boolean getIsEnviaPush() {
        return isEnviaPush;
    }

    public void setIsEnviaPush(Boolean isEnviaPush) {
        this.isEnviaPush = isEnviaPush;
    }

    public Boolean getValidaCreditoDisponivel() {
        return validaCreditoDisponivel;
    }

    public void setValidaCreditoDisponivel(Boolean validaCreditoDisponivel) {
        this.validaCreditoDisponivel = validaCreditoDisponivel;
    }

    public DocumentoComercialDTO getTransformaEm() {
        return transformaEm;
    }

    public void setTransformaEm(DocumentoComercialDTO transformaEm) {
        this.transformaEm = transformaEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentoComercialDTO)) {
            return false;
        }

        DocumentoComercialDTO documentoComercialDTO = (DocumentoComercialDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentoComercialDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentoComercialDTO{" +
            "id=" + getId() +
            ", modulo='" + getModulo() + "'" +
            ", origem='" + getOrigem() + "'" +
            ", siglaInterna='" + getSiglaInterna() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", siglaFiscal='" + getSiglaFiscal() + "'" +
            ", isMovimentaEstoque='" + getIsMovimentaEstoque() + "'" +
            ", isMovimentaCaixa='" + getIsMovimentaCaixa() + "'" +
            ", isNotificaEntidade='" + getIsNotificaEntidade() + "'" +
            ", isNotificaGerente='" + getIsNotificaGerente() + "'" +
            ", isEnviaSMS='" + getIsEnviaSMS() + "'" +
            ", isEnviaEmail='" + getIsEnviaEmail() + "'" +
            ", isEnviaPush='" + getIsEnviaPush() + "'" +
            ", validaCreditoDisponivel='" + getValidaCreditoDisponivel() + "'" +
            ", transformaEm=" + getTransformaEm() +
            "}";
    }
}
