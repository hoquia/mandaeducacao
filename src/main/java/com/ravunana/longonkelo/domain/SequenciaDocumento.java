package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SequenciaDocumento.
 */
@Entity
@Table(name = "sequencia_documento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SequenciaDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 1L)
    @Column(name = "sequencia", nullable = false)
    private Long sequencia;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull
    @Column(name = "hash", nullable = false)
    private String hash;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "sequenciaDocumentos", "tipoDocumento" }, allowSetters = true)
    private SerieDocumento serie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SequenciaDocumento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSequencia() {
        return this.sequencia;
    }

    public SequenciaDocumento sequencia(Long sequencia) {
        this.setSequencia(sequencia);
        return this;
    }

    public void setSequencia(Long sequencia) {
        this.sequencia = sequencia;
    }

    public LocalDate getData() {
        return this.data;
    }

    public SequenciaDocumento data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHash() {
        return this.hash;
    }

    public SequenciaDocumento hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public SequenciaDocumento timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public SerieDocumento getSerie() {
        return this.serie;
    }

    public void setSerie(SerieDocumento serieDocumento) {
        this.serie = serieDocumento;
    }

    public SequenciaDocumento serie(SerieDocumento serieDocumento) {
        this.setSerie(serieDocumento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenciaDocumento)) {
            return false;
        }
        return id != null && id.equals(((SequenciaDocumento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SequenciaDocumento{" +
            "id=" + getId() +
            ", sequencia=" + getSequencia() +
            ", data='" + getData() + "'" +
            ", hash='" + getHash() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
