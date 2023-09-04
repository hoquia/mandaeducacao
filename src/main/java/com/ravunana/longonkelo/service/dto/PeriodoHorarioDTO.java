package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.PeriodoHorario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeriodoHorarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    @Min(value = 1)
    private Integer tempo;

    @NotNull
    private String inicio;

    @NotNull
    private String fim;

    private TurnoDTO turno;

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

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public TurnoDTO getTurno() {
        return turno;
    }

    public void setTurno(TurnoDTO turno) {
        this.turno = turno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodoHorarioDTO)) {
            return false;
        }

        PeriodoHorarioDTO periodoHorarioDTO = (PeriodoHorarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, periodoHorarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodoHorarioDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", tempo=" + getTempo() +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            ", turno=" + getTurno() +
            "}";
    }
}
