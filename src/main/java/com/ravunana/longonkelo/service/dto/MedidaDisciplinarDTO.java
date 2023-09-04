package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.Suspensao;
import com.ravunana.longonkelo.domain.enumeration.UnidadeDuracao;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.MedidaDisciplinar} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedidaDisciplinarDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private UnidadeDuracao periodo;

    @NotNull
    private Suspensao suspensao;

    @Min(value = 0)
    private Integer tempo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UnidadeDuracao getPeriodo() {
        return periodo;
    }

    public void setPeriodo(UnidadeDuracao periodo) {
        this.periodo = periodo;
    }

    public Suspensao getSuspensao() {
        return suspensao;
    }

    public void setSuspensao(Suspensao suspensao) {
        this.suspensao = suspensao;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedidaDisciplinarDTO)) {
            return false;
        }

        MedidaDisciplinarDTO medidaDisciplinarDTO = (MedidaDisciplinarDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, medidaDisciplinarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedidaDisciplinarDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", periodo='" + getPeriodo() + "'" +
            ", suspensao='" + getSuspensao() + "'" +
            ", tempo=" + getTempo() +
            "}";
    }
}
