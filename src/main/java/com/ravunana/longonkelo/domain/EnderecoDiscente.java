package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.TipoEndereco;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EnderecoDiscente.
 */
@Entity
@Table(name = "endereco_discente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnderecoDiscente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoEndereco tipo;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "rua")
    private String rua;

    @Column(name = "numero_casa")
    private String numeroCasa;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem pais;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem provincia;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem municipio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "enderecos",
            "processosSelectivos",
            "anexoDiscentes",
            "matriculas",
            "resumoAcademicos",
            "historicosSaudes",
            "dissertacaoFinalCursos",
            "nacionalidade",
            "naturalidade",
            "tipoDocumento",
            "profissao",
            "grupoSanguinio",
            "necessidadeEspecial",
            "encarregadoEducacao",
        },
        allowSetters = true
    )
    private Discente discente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EnderecoDiscente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEndereco getTipo() {
        return this.tipo;
    }

    public EnderecoDiscente tipo(TipoEndereco tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoEndereco tipo) {
        this.tipo = tipo;
    }

    public String getBairro() {
        return this.bairro;
    }

    public EnderecoDiscente bairro(String bairro) {
        this.setBairro(bairro);
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return this.rua;
    }

    public EnderecoDiscente rua(String rua) {
        this.setRua(rua);
        return this;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumeroCasa() {
        return this.numeroCasa;
    }

    public EnderecoDiscente numeroCasa(String numeroCasa) {
        this.setNumeroCasa(numeroCasa);
        return this;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public EnderecoDiscente codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public EnderecoDiscente latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public EnderecoDiscente longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LookupItem getPais() {
        return this.pais;
    }

    public void setPais(LookupItem lookupItem) {
        this.pais = lookupItem;
    }

    public EnderecoDiscente pais(LookupItem lookupItem) {
        this.setPais(lookupItem);
        return this;
    }

    public LookupItem getProvincia() {
        return this.provincia;
    }

    public void setProvincia(LookupItem lookupItem) {
        this.provincia = lookupItem;
    }

    public EnderecoDiscente provincia(LookupItem lookupItem) {
        this.setProvincia(lookupItem);
        return this;
    }

    public LookupItem getMunicipio() {
        return this.municipio;
    }

    public void setMunicipio(LookupItem lookupItem) {
        this.municipio = lookupItem;
    }

    public EnderecoDiscente municipio(LookupItem lookupItem) {
        this.setMunicipio(lookupItem);
        return this;
    }

    public Discente getDiscente() {
        return this.discente;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }

    public EnderecoDiscente discente(Discente discente) {
        this.setDiscente(discente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnderecoDiscente)) {
            return false;
        }
        return id != null && id.equals(((EnderecoDiscente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnderecoDiscente{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", rua='" + getRua() + "'" +
            ", numeroCasa='" + getNumeroCasa() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
