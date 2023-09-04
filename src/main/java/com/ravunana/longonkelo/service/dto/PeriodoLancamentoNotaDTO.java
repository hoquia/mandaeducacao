package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.TipoAvaliacao;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.PeriodoLancamentoNota} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeriodoLancamentoNotaDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoAvaliacao tipoAvaliacao;

    @NotNull
    private ZonedDateTime de;

    @NotNull
    private ZonedDateTime ate;

    private ZonedDateTime timestamp;

    private UserDTO utilizador;

    private Set<ClasseDTO> classes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoAvaliacao getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public ZonedDateTime getDe() {
        return de;
    }

    public void setDe(ZonedDateTime de) {
        this.de = de;
    }

    public ZonedDateTime getAte() {
        return ate;
    }

    public void setAte(ZonedDateTime ate) {
        this.ate = ate;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public Set<ClasseDTO> getClasses() {
        return classes;
    }

    public void setClasses(Set<ClasseDTO> classes) {
        this.classes = classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodoLancamentoNotaDTO)) {
            return false;
        }

        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = (PeriodoLancamentoNotaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, periodoLancamentoNotaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodoLancamentoNotaDTO{" +
            "id=" + getId() +
            ", tipoAvaliacao='" + getTipoAvaliacao() + "'" +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", utilizador=" + getUtilizador() +
            ", classes=" + getClasses() +
            "}";
    }
}
