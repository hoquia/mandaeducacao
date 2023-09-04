package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Lookup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LookupDTO implements Serializable {

    private Long id;

    @NotNull
    private String codigo;

    @NotNull
    private String nome;

    @Lob
    private String descricao;

    private Boolean isSistema;

    private Boolean isModificavel;

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

    public Boolean getIsSistema() {
        return isSistema;
    }

    public void setIsSistema(Boolean isSistema) {
        this.isSistema = isSistema;
    }

    public Boolean getIsModificavel() {
        return isModificavel;
    }

    public void setIsModificavel(Boolean isModificavel) {
        this.isModificavel = isModificavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LookupDTO)) {
            return false;
        }

        LookupDTO lookupDTO = (LookupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, lookupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LookupDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", isSistema='" + getIsSistema() + "'" +
            ", isModificavel='" + getIsModificavel() + "'" +
            "}";
    }
}
