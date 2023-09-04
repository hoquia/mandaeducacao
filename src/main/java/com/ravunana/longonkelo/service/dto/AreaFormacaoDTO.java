package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.AreaFormacao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AreaFormacaoDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] imagem;

    private String imagemContentType;

    @NotNull
    private String codigo;

    @NotNull
    private String nome;

    @Lob
    private String descricao;

    private NivelEnsinoDTO nivelEnsino;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public NivelEnsinoDTO getNivelEnsino() {
        return nivelEnsino;
    }

    public void setNivelEnsino(NivelEnsinoDTO nivelEnsino) {
        this.nivelEnsino = nivelEnsino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaFormacaoDTO)) {
            return false;
        }

        AreaFormacaoDTO areaFormacaoDTO = (AreaFormacaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, areaFormacaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaFormacaoDTO{" +
            "id=" + getId() +
            ", imagem='" + getImagem() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", nivelEnsino=" + getNivelEnsino() +
            "}";
    }
}
