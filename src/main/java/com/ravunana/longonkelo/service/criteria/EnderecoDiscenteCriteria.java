package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.TipoEndereco;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.EnderecoDiscente} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.EnderecoDiscenteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /endereco-discentes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnderecoDiscenteCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoEndereco
     */
    public static class TipoEnderecoFilter extends Filter<TipoEndereco> {

        public TipoEnderecoFilter() {}

        public TipoEnderecoFilter(TipoEnderecoFilter filter) {
            super(filter);
        }

        @Override
        public TipoEnderecoFilter copy() {
            return new TipoEnderecoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TipoEnderecoFilter tipo;

    private StringFilter bairro;

    private StringFilter rua;

    private StringFilter numeroCasa;

    private StringFilter codigoPostal;

    private DoubleFilter latitude;

    private DoubleFilter longitude;

    private LongFilter paisId;

    private LongFilter provinciaId;

    private LongFilter municipioId;

    private LongFilter discenteId;

    private Boolean distinct;

    public EnderecoDiscenteCriteria() {}

    public EnderecoDiscenteCriteria(EnderecoDiscenteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.bairro = other.bairro == null ? null : other.bairro.copy();
        this.rua = other.rua == null ? null : other.rua.copy();
        this.numeroCasa = other.numeroCasa == null ? null : other.numeroCasa.copy();
        this.codigoPostal = other.codigoPostal == null ? null : other.codigoPostal.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.paisId = other.paisId == null ? null : other.paisId.copy();
        this.provinciaId = other.provinciaId == null ? null : other.provinciaId.copy();
        this.municipioId = other.municipioId == null ? null : other.municipioId.copy();
        this.discenteId = other.discenteId == null ? null : other.discenteId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EnderecoDiscenteCriteria copy() {
        return new EnderecoDiscenteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TipoEnderecoFilter getTipo() {
        return tipo;
    }

    public TipoEnderecoFilter tipo() {
        if (tipo == null) {
            tipo = new TipoEnderecoFilter();
        }
        return tipo;
    }

    public void setTipo(TipoEnderecoFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getBairro() {
        return bairro;
    }

    public StringFilter bairro() {
        if (bairro == null) {
            bairro = new StringFilter();
        }
        return bairro;
    }

    public void setBairro(StringFilter bairro) {
        this.bairro = bairro;
    }

    public StringFilter getRua() {
        return rua;
    }

    public StringFilter rua() {
        if (rua == null) {
            rua = new StringFilter();
        }
        return rua;
    }

    public void setRua(StringFilter rua) {
        this.rua = rua;
    }

    public StringFilter getNumeroCasa() {
        return numeroCasa;
    }

    public StringFilter numeroCasa() {
        if (numeroCasa == null) {
            numeroCasa = new StringFilter();
        }
        return numeroCasa;
    }

    public void setNumeroCasa(StringFilter numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public StringFilter getCodigoPostal() {
        return codigoPostal;
    }

    public StringFilter codigoPostal() {
        if (codigoPostal == null) {
            codigoPostal = new StringFilter();
        }
        return codigoPostal;
    }

    public void setCodigoPostal(StringFilter codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public DoubleFilter getLatitude() {
        return latitude;
    }

    public DoubleFilter latitude() {
        if (latitude == null) {
            latitude = new DoubleFilter();
        }
        return latitude;
    }

    public void setLatitude(DoubleFilter latitude) {
        this.latitude = latitude;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public DoubleFilter longitude() {
        if (longitude == null) {
            longitude = new DoubleFilter();
        }
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public LongFilter getPaisId() {
        return paisId;
    }

    public LongFilter paisId() {
        if (paisId == null) {
            paisId = new LongFilter();
        }
        return paisId;
    }

    public void setPaisId(LongFilter paisId) {
        this.paisId = paisId;
    }

    public LongFilter getProvinciaId() {
        return provinciaId;
    }

    public LongFilter provinciaId() {
        if (provinciaId == null) {
            provinciaId = new LongFilter();
        }
        return provinciaId;
    }

    public void setProvinciaId(LongFilter provinciaId) {
        this.provinciaId = provinciaId;
    }

    public LongFilter getMunicipioId() {
        return municipioId;
    }

    public LongFilter municipioId() {
        if (municipioId == null) {
            municipioId = new LongFilter();
        }
        return municipioId;
    }

    public void setMunicipioId(LongFilter municipioId) {
        this.municipioId = municipioId;
    }

    public LongFilter getDiscenteId() {
        return discenteId;
    }

    public LongFilter discenteId() {
        if (discenteId == null) {
            discenteId = new LongFilter();
        }
        return discenteId;
    }

    public void setDiscenteId(LongFilter discenteId) {
        this.discenteId = discenteId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnderecoDiscenteCriteria that = (EnderecoDiscenteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(bairro, that.bairro) &&
            Objects.equals(rua, that.rua) &&
            Objects.equals(numeroCasa, that.numeroCasa) &&
            Objects.equals(codigoPostal, that.codigoPostal) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(paisId, that.paisId) &&
            Objects.equals(provinciaId, that.provinciaId) &&
            Objects.equals(municipioId, that.municipioId) &&
            Objects.equals(discenteId, that.discenteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tipo,
            bairro,
            rua,
            numeroCasa,
            codigoPostal,
            latitude,
            longitude,
            paisId,
            provinciaId,
            municipioId,
            discenteId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnderecoDiscenteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tipo != null ? "tipo=" + tipo + ", " : "") +
            (bairro != null ? "bairro=" + bairro + ", " : "") +
            (rua != null ? "rua=" + rua + ", " : "") +
            (numeroCasa != null ? "numeroCasa=" + numeroCasa + ", " : "") +
            (codigoPostal != null ? "codigoPostal=" + codigoPostal + ", " : "") +
            (latitude != null ? "latitude=" + latitude + ", " : "") +
            (longitude != null ? "longitude=" + longitude + ", " : "") +
            (paisId != null ? "paisId=" + paisId + ", " : "") +
            (provinciaId != null ? "provinciaId=" + provinciaId + ", " : "") +
            (municipioId != null ? "municipioId=" + municipioId + ", " : "") +
            (discenteId != null ? "discenteId=" + discenteId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
