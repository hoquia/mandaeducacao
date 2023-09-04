package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.CategoriaOcorrencia} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriaOcorrenciaDTO implements Serializable {

    private Long id;

    @NotNull
    private String codigo;

    private String sansaoDisicplinar;

    private Boolean isNotificaEncaregado;

    private Boolean isSendEmail;

    private Boolean isSendSms;

    private Boolean isSendPush;

    @NotNull
    private String descricao;

    @Lob
    private String observacao;

    private DocenteDTO encaminhar;

    private CategoriaOcorrenciaDTO referencia;

    private MedidaDisciplinarDTO medidaDisciplinar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSansaoDisicplinar() {
        return sansaoDisicplinar;
    }

    public void setSansaoDisicplinar(String sansaoDisicplinar) {
        this.sansaoDisicplinar = sansaoDisicplinar;
    }

    public Boolean getIsNotificaEncaregado() {
        return isNotificaEncaregado;
    }

    public void setIsNotificaEncaregado(Boolean isNotificaEncaregado) {
        this.isNotificaEncaregado = isNotificaEncaregado;
    }

    public Boolean getIsSendEmail() {
        return isSendEmail;
    }

    public void setIsSendEmail(Boolean isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    public Boolean getIsSendSms() {
        return isSendSms;
    }

    public void setIsSendSms(Boolean isSendSms) {
        this.isSendSms = isSendSms;
    }

    public Boolean getIsSendPush() {
        return isSendPush;
    }

    public void setIsSendPush(Boolean isSendPush) {
        this.isSendPush = isSendPush;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public DocenteDTO getEncaminhar() {
        return encaminhar;
    }

    public void setEncaminhar(DocenteDTO encaminhar) {
        this.encaminhar = encaminhar;
    }

    public CategoriaOcorrenciaDTO getReferencia() {
        return referencia;
    }

    public void setReferencia(CategoriaOcorrenciaDTO referencia) {
        this.referencia = referencia;
    }

    public MedidaDisciplinarDTO getMedidaDisciplinar() {
        return medidaDisciplinar;
    }

    public void setMedidaDisciplinar(MedidaDisciplinarDTO medidaDisciplinar) {
        this.medidaDisciplinar = medidaDisciplinar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaOcorrenciaDTO)) {
            return false;
        }

        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoriaOcorrenciaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriaOcorrenciaDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", sansaoDisicplinar='" + getSansaoDisicplinar() + "'" +
            ", isNotificaEncaregado='" + getIsNotificaEncaregado() + "'" +
            ", isSendEmail='" + getIsSendEmail() + "'" +
            ", isSendSms='" + getIsSendSms() + "'" +
            ", isSendPush='" + getIsSendPush() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", encaminhar=" + getEncaminhar() +
            ", referencia=" + getReferencia() +
            ", medidaDisciplinar=" + getMedidaDisciplinar() +
            "}";
    }
}
