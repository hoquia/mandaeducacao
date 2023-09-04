package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.CategoriaEmolumento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriaEmolumentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private Boolean isServico;

    private String cor;

    @Lob
    private String descricao;

    private Boolean isIsentoMulta;

    private Boolean isIsentoJuro;

    private PlanoMultaDTO planoMulta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getIsServico() {
        return isServico;
    }

    public void setIsServico(Boolean isServico) {
        this.isServico = isServico;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getIsIsentoMulta() {
        return isIsentoMulta;
    }

    public void setIsIsentoMulta(Boolean isIsentoMulta) {
        this.isIsentoMulta = isIsentoMulta;
    }

    public Boolean getIsIsentoJuro() {
        return isIsentoJuro;
    }

    public void setIsIsentoJuro(Boolean isIsentoJuro) {
        this.isIsentoJuro = isIsentoJuro;
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
        if (!(o instanceof CategoriaEmolumentoDTO)) {
            return false;
        }

        CategoriaEmolumentoDTO categoriaEmolumentoDTO = (CategoriaEmolumentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoriaEmolumentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriaEmolumentoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", isServico='" + getIsServico() + "'" +
            ", cor='" + getCor() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", isIsentoMulta='" + getIsIsentoMulta() + "'" +
            ", isIsentoJuro='" + getIsIsentoJuro() + "'" +
            ", planoMulta=" + getPlanoMulta() +
            "}";
    }
}
