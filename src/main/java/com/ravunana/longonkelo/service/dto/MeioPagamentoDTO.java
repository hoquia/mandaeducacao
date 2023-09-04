package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.MeioPagamento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MeioPagamentoDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] imagem;

    private String imagemContentType;

    @NotNull
    private String codigo;

    @NotNull
    private String nome;

    @Min(value = 0)
    private Integer numeroDigitoReferencia;

    private Boolean isPagamentoInstantanio;

    private String hash;

    private String link;

    private String token;

    private String username;

    private String password;

    private String formatoReferencia;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumeroDigitoReferencia() {
        return numeroDigitoReferencia;
    }

    public void setNumeroDigitoReferencia(Integer numeroDigitoReferencia) {
        this.numeroDigitoReferencia = numeroDigitoReferencia;
    }

    public Boolean getIsPagamentoInstantanio() {
        return isPagamentoInstantanio;
    }

    public void setIsPagamentoInstantanio(Boolean isPagamentoInstantanio) {
        this.isPagamentoInstantanio = isPagamentoInstantanio;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFormatoReferencia() {
        return formatoReferencia;
    }

    public void setFormatoReferencia(String formatoReferencia) {
        this.formatoReferencia = formatoReferencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeioPagamentoDTO)) {
            return false;
        }

        MeioPagamentoDTO meioPagamentoDTO = (MeioPagamentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, meioPagamentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeioPagamentoDTO{" +
            "id=" + getId() +
            ", imagem='" + getImagem() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", numeroDigitoReferencia=" + getNumeroDigitoReferencia() +
            ", isPagamentoInstantanio='" + getIsPagamentoInstantanio() + "'" +
            ", hash='" + getHash() + "'" +
            ", link='" + getLink() + "'" +
            ", token='" + getToken() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", formatoReferencia='" + getFormatoReferencia() + "'" +
            "}";
    }
}
