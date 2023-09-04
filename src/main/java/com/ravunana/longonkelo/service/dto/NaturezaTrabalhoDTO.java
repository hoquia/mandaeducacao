package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.NaturezaTrabalho} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NaturezaTrabalhoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @Lob
    private String descricao;

    private Boolean isActivo;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getIsActivo() {
        return isActivo;
    }

    public void setIsActivo(Boolean isActivo) {
        this.isActivo = isActivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NaturezaTrabalhoDTO)) {
            return false;
        }

        NaturezaTrabalhoDTO naturezaTrabalhoDTO = (NaturezaTrabalhoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, naturezaTrabalhoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NaturezaTrabalhoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", isActivo='" + getIsActivo() + "'" +
            "}";
    }
}
