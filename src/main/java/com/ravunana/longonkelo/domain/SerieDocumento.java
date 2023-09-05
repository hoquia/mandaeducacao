package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SerieDocumento.
 */
@Entity
@Table(name = "serie_documento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SerieDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ano_fiscal")
    private Integer anoFiscal;

    @NotNull
    @Min(value = 0)
    @Column(name = "versao", nullable = false)
    private Integer versao;

    @NotNull
    @Column(name = "serie", nullable = false, unique = true)
    private String serie;

    @Column(name = "is_ativo")
    private Boolean isAtivo;

    @Column(name = "is_padrao")
    private Boolean isPadrao;

    @OneToMany(mappedBy = "serie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "serie" }, allowSetters = true)
    private Set<SequenciaDocumento> sequenciaDocumentos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "serieDocumentos", "facturas", "recibos", "transformaEm" }, allowSetters = true)
    private DocumentoComercial tipoDocumento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SerieDocumento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnoFiscal() {
        return this.anoFiscal;
    }

    public SerieDocumento anoFiscal(Integer anoFiscal) {
        this.setAnoFiscal(anoFiscal);
        return this;
    }

    public void setAnoFiscal(Integer anoFiscal) {
        this.anoFiscal = anoFiscal;
    }

    public Integer getVersao() {
        return this.versao;
    }

    public SerieDocumento versao(Integer versao) {
        this.setVersao(versao);
        return this;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }

    public String getSerie() {
        return this.serie;
    }

    public SerieDocumento serie(String serie) {
        this.setSerie(serie);
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Boolean getIsAtivo() {
        return this.isAtivo;
    }

    public SerieDocumento isAtivo(Boolean isAtivo) {
        this.setIsAtivo(isAtivo);
        return this;
    }

    public void setIsAtivo(Boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public Boolean getIsPadrao() {
        return this.isPadrao;
    }

    public SerieDocumento isPadrao(Boolean isPadrao) {
        this.setIsPadrao(isPadrao);
        return this;
    }

    public void setIsPadrao(Boolean isPadrao) {
        this.isPadrao = isPadrao;
    }

    public Set<SequenciaDocumento> getSequenciaDocumentos() {
        return this.sequenciaDocumentos;
    }

    public void setSequenciaDocumentos(Set<SequenciaDocumento> sequenciaDocumentos) {
        if (this.sequenciaDocumentos != null) {
            this.sequenciaDocumentos.forEach(i -> i.setSerie(null));
        }
        if (sequenciaDocumentos != null) {
            sequenciaDocumentos.forEach(i -> i.setSerie(this));
        }
        this.sequenciaDocumentos = sequenciaDocumentos;
    }

    public SerieDocumento sequenciaDocumentos(Set<SequenciaDocumento> sequenciaDocumentos) {
        this.setSequenciaDocumentos(sequenciaDocumentos);
        return this;
    }

    public SerieDocumento addSequenciaDocumento(SequenciaDocumento sequenciaDocumento) {
        this.sequenciaDocumentos.add(sequenciaDocumento);
        sequenciaDocumento.setSerie(this);
        return this;
    }

    public SerieDocumento removeSequenciaDocumento(SequenciaDocumento sequenciaDocumento) {
        this.sequenciaDocumentos.remove(sequenciaDocumento);
        sequenciaDocumento.setSerie(null);
        return this;
    }

    public DocumentoComercial getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(DocumentoComercial documentoComercial) {
        this.tipoDocumento = documentoComercial;
    }

    public SerieDocumento tipoDocumento(DocumentoComercial documentoComercial) {
        this.setTipoDocumento(documentoComercial);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SerieDocumento)) {
            return false;
        }
        return id != null && id.equals(((SerieDocumento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SerieDocumento{" +
            "id=" + getId() +
            ", anoFiscal=" + getAnoFiscal() +
            ", versao=" + getVersao() +
            ", serie='" + getSerie() + "'" +
            ", isAtivo='" + getIsAtivo() + "'" +
            ", isPadrao='" + getIsPadrao() + "'" +
            "}";
    }
}
