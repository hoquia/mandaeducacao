package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.TipoEndereco;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.EnderecoDiscente} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnderecoDiscenteDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoEndereco tipo;

    private String bairro;

    private String rua;

    private String numeroCasa;

    private String codigoPostal;

    private Double latitude;

    private Double longitude;

    private LookupItemDTO pais;

    private LookupItemDTO provincia;

    private LookupItemDTO municipio;

    private DiscenteDTO discente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEndereco getTipo() {
        return tipo;
    }

    public void setTipo(TipoEndereco tipo) {
        this.tipo = tipo;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LookupItemDTO getPais() {
        return pais;
    }

    public void setPais(LookupItemDTO pais) {
        this.pais = pais;
    }

    public LookupItemDTO getProvincia() {
        return provincia;
    }

    public void setProvincia(LookupItemDTO provincia) {
        this.provincia = provincia;
    }

    public LookupItemDTO getMunicipio() {
        return municipio;
    }

    public void setMunicipio(LookupItemDTO municipio) {
        this.municipio = municipio;
    }

    public DiscenteDTO getDiscente() {
        return discente;
    }

    public void setDiscente(DiscenteDTO discente) {
        this.discente = discente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnderecoDiscenteDTO)) {
            return false;
        }

        EnderecoDiscenteDTO enderecoDiscenteDTO = (EnderecoDiscenteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, enderecoDiscenteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnderecoDiscenteDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", rua='" + getRua() + "'" +
            ", numeroCasa='" + getNumeroCasa() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", pais=" + getPais() +
            ", provincia=" + getProvincia() +
            ", municipio=" + getMunicipio() +
            ", discente=" + getDiscente() +
            "}";
    }
}
