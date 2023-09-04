package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.LookupItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LookupItemDTO implements Serializable {

    private Long id;

    private String codigo;

    @Min(value = 0)
    private Integer ordem;

    private Boolean isSistema;

    @NotNull
    private String descricao;

    private LookupDTO lookup;

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

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getIsSistema() {
        return isSistema;
    }

    public void setIsSistema(Boolean isSistema) {
        this.isSistema = isSistema;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LookupDTO getLookup() {
        return lookup;
    }

    public void setLookup(LookupDTO lookup) {
        this.lookup = lookup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LookupItemDTO)) {
            return false;
        }

        LookupItemDTO lookupItemDTO = (LookupItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, lookupItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LookupItemDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", ordem=" + getOrdem() +
            ", isSistema='" + getIsSistema() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", lookup=" + getLookup() +
            "}";
    }
}
