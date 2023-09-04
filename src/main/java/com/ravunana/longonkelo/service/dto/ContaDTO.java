package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.TipoConta;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Conta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContaDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] imagem;

    private String imagemContentType;

    @NotNull
    private TipoConta tipo;

    @NotNull
    private String titulo;

    @NotNull
    private String numero;

    private String iban;

    @NotNull
    private String titular;

    private Boolean isPadrao;

    private LookupItemDTO moeda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return imagemContentType;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Boolean getIsPadrao() {
        return isPadrao;
    }

    public void setIsPadrao(Boolean isPadrao) {
        this.isPadrao = isPadrao;
    }

    public LookupItemDTO getMoeda() {
        return moeda;
    }

    public void setMoeda(LookupItemDTO moeda) {
        this.moeda = moeda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContaDTO)) {
            return false;
        }

        ContaDTO contaDTO = (ContaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContaDTO{" +
            "id=" + getId() +
            ", imagem='" + getImagem() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", numero='" + getNumero() + "'" +
            ", iban='" + getIban() + "'" +
            ", titular='" + getTitular() + "'" +
            ", isPadrao='" + getIsPadrao() + "'" +
            ", moeda=" + getMoeda() +
            "}";
    }
}
