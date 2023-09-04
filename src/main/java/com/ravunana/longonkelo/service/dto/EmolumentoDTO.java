package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Emolumento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmolumentoDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] imagem;

    private String imagemContentType;

    @NotNull
    private String numero;

    @NotNull
    private String nome;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal preco;

    @NotNull
    @DecimalMin(value = "0")
    private Double quantidade;

    @Min(value = 1)
    @Max(value = 12)
    private Integer periodo;

    @Min(value = 1)
    @Max(value = 12)
    private Integer inicioPeriodo;

    @Min(value = 1)
    @Max(value = 12)
    private Integer fimPeriodo;

    private Boolean isObrigatorioMatricula;

    private Boolean isObrigatorioConfirmacao;

    private CategoriaEmolumentoDTO categoria;

    private ImpostoDTO imposto;

    private EmolumentoDTO referencia;

    private PlanoMultaDTO planoMulta;

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getInicioPeriodo() {
        return inicioPeriodo;
    }

    public void setInicioPeriodo(Integer inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    public Integer getFimPeriodo() {
        return fimPeriodo;
    }

    public void setFimPeriodo(Integer fimPeriodo) {
        this.fimPeriodo = fimPeriodo;
    }

    public Boolean getIsObrigatorioMatricula() {
        return isObrigatorioMatricula;
    }

    public void setIsObrigatorioMatricula(Boolean isObrigatorioMatricula) {
        this.isObrigatorioMatricula = isObrigatorioMatricula;
    }

    public Boolean getIsObrigatorioConfirmacao() {
        return isObrigatorioConfirmacao;
    }

    public void setIsObrigatorioConfirmacao(Boolean isObrigatorioConfirmacao) {
        this.isObrigatorioConfirmacao = isObrigatorioConfirmacao;
    }

    public CategoriaEmolumentoDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEmolumentoDTO categoria) {
        this.categoria = categoria;
    }

    public ImpostoDTO getImposto() {
        return imposto;
    }

    public void setImposto(ImpostoDTO imposto) {
        this.imposto = imposto;
    }

    public EmolumentoDTO getReferencia() {
        return referencia;
    }

    public void setReferencia(EmolumentoDTO referencia) {
        this.referencia = referencia;
    }

    public PlanoMultaDTO getPlanoMulta() {
        return planoMulta;
    }

    public void setPlanoMulta(PlanoMultaDTO planoMulta) {
        this.planoMulta = planoMulta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmolumentoDTO)) {
            return false;
        }

        EmolumentoDTO emolumentoDTO = (EmolumentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, emolumentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmolumentoDTO{" +
            "id=" + getId() +
            ", imagem='" + getImagem() + "'" +
            ", numero='" + getNumero() + "'" +
            ", nome='" + getNome() + "'" +
            ", preco=" + getPreco() +
            ", quantidade=" + getQuantidade() +
            ", periodo=" + getPeriodo() +
            ", inicioPeriodo=" + getInicioPeriodo() +
            ", fimPeriodo=" + getFimPeriodo() +
            ", isObrigatorioMatricula='" + getIsObrigatorioMatricula() + "'" +
            ", isObrigatorioConfirmacao='" + getIsObrigatorioConfirmacao() + "'" +
            ", categoria=" + getCategoria() +
            ", imposto=" + getImposto() +
            ", referencia=" + getReferencia() +
            ", planoMulta=" + getPlanoMulta() +
            "}";
    }
}
