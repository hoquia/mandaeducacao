package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.SequenciaDocumento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SequenciaDocumentoDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1L)
    private Long sequencia;

    @NotNull
    private LocalDate data;

    @NotNull
    private String hash;

    @NotNull
    private ZonedDateTime timestamp;

    private SerieDocumentoDTO serie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSequencia() {
        return sequencia;
    }

    public void setSequencia(Long sequencia) {
        this.sequencia = sequencia;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public SerieDocumentoDTO getSerie() {
        return serie;
    }

    public void setSerie(SerieDocumentoDTO serie) {
        this.serie = serie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenciaDocumentoDTO)) {
            return false;
        }

        SequenciaDocumentoDTO sequenciaDocumentoDTO = (SequenciaDocumentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sequenciaDocumentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SequenciaDocumentoDTO{" +
            "id=" + getId() +
            ", sequencia=" + getSequencia() +
            ", data='" + getData() + "'" +
            ", hash='" + getHash() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", serie=" + getSerie() +
            "}";
    }
}
