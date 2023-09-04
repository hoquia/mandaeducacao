package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.InstituicaoEnsino} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.InstituicaoEnsinoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /instituicao-ensinos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InstituicaoEnsinoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter unidadeOrganica;

    private StringFilter nomeFiscal;

    private StringFilter numero;

    private StringFilter nif;

    private StringFilter cae;

    private StringFilter niss;

    private StringFilter fundador;

    private LocalDateFilter fundacao;

    private StringFilter dimensao;

    private StringFilter slogam;

    private StringFilter telefone;

    private StringFilter telemovel;

    private StringFilter email;

    private StringFilter website;

    private StringFilter codigoPostal;

    private StringFilter enderecoDetalhado;

    private DoubleFilter latitude;

    private DoubleFilter longitude;

    private BooleanFilter isComparticipada;

    private LongFilter instituicaoEnsinoId;

    private LongFilter provedorNotificacaoId;

    private LongFilter categoriaInstituicaoId;

    private LongFilter unidadePagadoraId;

    private LongFilter tipoVinculoId;

    private LongFilter tipoInstalacaoId;

    private LongFilter sedeId;

    private Boolean distinct;

    public InstituicaoEnsinoCriteria() {}

    public InstituicaoEnsinoCriteria(InstituicaoEnsinoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.unidadeOrganica = other.unidadeOrganica == null ? null : other.unidadeOrganica.copy();
        this.nomeFiscal = other.nomeFiscal == null ? null : other.nomeFiscal.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.cae = other.cae == null ? null : other.cae.copy();
        this.niss = other.niss == null ? null : other.niss.copy();
        this.fundador = other.fundador == null ? null : other.fundador.copy();
        this.fundacao = other.fundacao == null ? null : other.fundacao.copy();
        this.dimensao = other.dimensao == null ? null : other.dimensao.copy();
        this.slogam = other.slogam == null ? null : other.slogam.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.telemovel = other.telemovel == null ? null : other.telemovel.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.website = other.website == null ? null : other.website.copy();
        this.codigoPostal = other.codigoPostal == null ? null : other.codigoPostal.copy();
        this.enderecoDetalhado = other.enderecoDetalhado == null ? null : other.enderecoDetalhado.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.isComparticipada = other.isComparticipada == null ? null : other.isComparticipada.copy();
        this.instituicaoEnsinoId = other.instituicaoEnsinoId == null ? null : other.instituicaoEnsinoId.copy();
        this.provedorNotificacaoId = other.provedorNotificacaoId == null ? null : other.provedorNotificacaoId.copy();
        this.categoriaInstituicaoId = other.categoriaInstituicaoId == null ? null : other.categoriaInstituicaoId.copy();
        this.unidadePagadoraId = other.unidadePagadoraId == null ? null : other.unidadePagadoraId.copy();
        this.tipoVinculoId = other.tipoVinculoId == null ? null : other.tipoVinculoId.copy();
        this.tipoInstalacaoId = other.tipoInstalacaoId == null ? null : other.tipoInstalacaoId.copy();
        this.sedeId = other.sedeId == null ? null : other.sedeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InstituicaoEnsinoCriteria copy() {
        return new InstituicaoEnsinoCriteria(this);
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

    public StringFilter getUnidadeOrganica() {
        return unidadeOrganica;
    }

    public StringFilter unidadeOrganica() {
        if (unidadeOrganica == null) {
            unidadeOrganica = new StringFilter();
        }
        return unidadeOrganica;
    }

    public void setUnidadeOrganica(StringFilter unidadeOrganica) {
        this.unidadeOrganica = unidadeOrganica;
    }

    public StringFilter getNomeFiscal() {
        return nomeFiscal;
    }

    public StringFilter nomeFiscal() {
        if (nomeFiscal == null) {
            nomeFiscal = new StringFilter();
        }
        return nomeFiscal;
    }

    public void setNomeFiscal(StringFilter nomeFiscal) {
        this.nomeFiscal = nomeFiscal;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public StringFilter numero() {
        if (numero == null) {
            numero = new StringFilter();
        }
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public StringFilter getNif() {
        return nif;
    }

    public StringFilter nif() {
        if (nif == null) {
            nif = new StringFilter();
        }
        return nif;
    }

    public void setNif(StringFilter nif) {
        this.nif = nif;
    }

    public StringFilter getCae() {
        return cae;
    }

    public StringFilter cae() {
        if (cae == null) {
            cae = new StringFilter();
        }
        return cae;
    }

    public void setCae(StringFilter cae) {
        this.cae = cae;
    }

    public StringFilter getNiss() {
        return niss;
    }

    public StringFilter niss() {
        if (niss == null) {
            niss = new StringFilter();
        }
        return niss;
    }

    public void setNiss(StringFilter niss) {
        this.niss = niss;
    }

    public StringFilter getFundador() {
        return fundador;
    }

    public StringFilter fundador() {
        if (fundador == null) {
            fundador = new StringFilter();
        }
        return fundador;
    }

    public void setFundador(StringFilter fundador) {
        this.fundador = fundador;
    }

    public LocalDateFilter getFundacao() {
        return fundacao;
    }

    public LocalDateFilter fundacao() {
        if (fundacao == null) {
            fundacao = new LocalDateFilter();
        }
        return fundacao;
    }

    public void setFundacao(LocalDateFilter fundacao) {
        this.fundacao = fundacao;
    }

    public StringFilter getDimensao() {
        return dimensao;
    }

    public StringFilter dimensao() {
        if (dimensao == null) {
            dimensao = new StringFilter();
        }
        return dimensao;
    }

    public void setDimensao(StringFilter dimensao) {
        this.dimensao = dimensao;
    }

    public StringFilter getSlogam() {
        return slogam;
    }

    public StringFilter slogam() {
        if (slogam == null) {
            slogam = new StringFilter();
        }
        return slogam;
    }

    public void setSlogam(StringFilter slogam) {
        this.slogam = slogam;
    }

    public StringFilter getTelefone() {
        return telefone;
    }

    public StringFilter telefone() {
        if (telefone == null) {
            telefone = new StringFilter();
        }
        return telefone;
    }

    public void setTelefone(StringFilter telefone) {
        this.telefone = telefone;
    }

    public StringFilter getTelemovel() {
        return telemovel;
    }

    public StringFilter telemovel() {
        if (telemovel == null) {
            telemovel = new StringFilter();
        }
        return telemovel;
    }

    public void setTelemovel(StringFilter telemovel) {
        this.telemovel = telemovel;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getWebsite() {
        return website;
    }

    public StringFilter website() {
        if (website == null) {
            website = new StringFilter();
        }
        return website;
    }

    public void setWebsite(StringFilter website) {
        this.website = website;
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

    public StringFilter getEnderecoDetalhado() {
        return enderecoDetalhado;
    }

    public StringFilter enderecoDetalhado() {
        if (enderecoDetalhado == null) {
            enderecoDetalhado = new StringFilter();
        }
        return enderecoDetalhado;
    }

    public void setEnderecoDetalhado(StringFilter enderecoDetalhado) {
        this.enderecoDetalhado = enderecoDetalhado;
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

    public BooleanFilter getIsComparticipada() {
        return isComparticipada;
    }

    public BooleanFilter isComparticipada() {
        if (isComparticipada == null) {
            isComparticipada = new BooleanFilter();
        }
        return isComparticipada;
    }

    public void setIsComparticipada(BooleanFilter isComparticipada) {
        this.isComparticipada = isComparticipada;
    }

    public LongFilter getInstituicaoEnsinoId() {
        return instituicaoEnsinoId;
    }

    public LongFilter instituicaoEnsinoId() {
        if (instituicaoEnsinoId == null) {
            instituicaoEnsinoId = new LongFilter();
        }
        return instituicaoEnsinoId;
    }

    public void setInstituicaoEnsinoId(LongFilter instituicaoEnsinoId) {
        this.instituicaoEnsinoId = instituicaoEnsinoId;
    }

    public LongFilter getProvedorNotificacaoId() {
        return provedorNotificacaoId;
    }

    public LongFilter provedorNotificacaoId() {
        if (provedorNotificacaoId == null) {
            provedorNotificacaoId = new LongFilter();
        }
        return provedorNotificacaoId;
    }

    public void setProvedorNotificacaoId(LongFilter provedorNotificacaoId) {
        this.provedorNotificacaoId = provedorNotificacaoId;
    }

    public LongFilter getCategoriaInstituicaoId() {
        return categoriaInstituicaoId;
    }

    public LongFilter categoriaInstituicaoId() {
        if (categoriaInstituicaoId == null) {
            categoriaInstituicaoId = new LongFilter();
        }
        return categoriaInstituicaoId;
    }

    public void setCategoriaInstituicaoId(LongFilter categoriaInstituicaoId) {
        this.categoriaInstituicaoId = categoriaInstituicaoId;
    }

    public LongFilter getUnidadePagadoraId() {
        return unidadePagadoraId;
    }

    public LongFilter unidadePagadoraId() {
        if (unidadePagadoraId == null) {
            unidadePagadoraId = new LongFilter();
        }
        return unidadePagadoraId;
    }

    public void setUnidadePagadoraId(LongFilter unidadePagadoraId) {
        this.unidadePagadoraId = unidadePagadoraId;
    }

    public LongFilter getTipoVinculoId() {
        return tipoVinculoId;
    }

    public LongFilter tipoVinculoId() {
        if (tipoVinculoId == null) {
            tipoVinculoId = new LongFilter();
        }
        return tipoVinculoId;
    }

    public void setTipoVinculoId(LongFilter tipoVinculoId) {
        this.tipoVinculoId = tipoVinculoId;
    }

    public LongFilter getTipoInstalacaoId() {
        return tipoInstalacaoId;
    }

    public LongFilter tipoInstalacaoId() {
        if (tipoInstalacaoId == null) {
            tipoInstalacaoId = new LongFilter();
        }
        return tipoInstalacaoId;
    }

    public void setTipoInstalacaoId(LongFilter tipoInstalacaoId) {
        this.tipoInstalacaoId = tipoInstalacaoId;
    }

    public LongFilter getSedeId() {
        return sedeId;
    }

    public LongFilter sedeId() {
        if (sedeId == null) {
            sedeId = new LongFilter();
        }
        return sedeId;
    }

    public void setSedeId(LongFilter sedeId) {
        this.sedeId = sedeId;
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
        final InstituicaoEnsinoCriteria that = (InstituicaoEnsinoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(unidadeOrganica, that.unidadeOrganica) &&
            Objects.equals(nomeFiscal, that.nomeFiscal) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(cae, that.cae) &&
            Objects.equals(niss, that.niss) &&
            Objects.equals(fundador, that.fundador) &&
            Objects.equals(fundacao, that.fundacao) &&
            Objects.equals(dimensao, that.dimensao) &&
            Objects.equals(slogam, that.slogam) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(telemovel, that.telemovel) &&
            Objects.equals(email, that.email) &&
            Objects.equals(website, that.website) &&
            Objects.equals(codigoPostal, that.codigoPostal) &&
            Objects.equals(enderecoDetalhado, that.enderecoDetalhado) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(isComparticipada, that.isComparticipada) &&
            Objects.equals(instituicaoEnsinoId, that.instituicaoEnsinoId) &&
            Objects.equals(provedorNotificacaoId, that.provedorNotificacaoId) &&
            Objects.equals(categoriaInstituicaoId, that.categoriaInstituicaoId) &&
            Objects.equals(unidadePagadoraId, that.unidadePagadoraId) &&
            Objects.equals(tipoVinculoId, that.tipoVinculoId) &&
            Objects.equals(tipoInstalacaoId, that.tipoInstalacaoId) &&
            Objects.equals(sedeId, that.sedeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            unidadeOrganica,
            nomeFiscal,
            numero,
            nif,
            cae,
            niss,
            fundador,
            fundacao,
            dimensao,
            slogam,
            telefone,
            telemovel,
            email,
            website,
            codigoPostal,
            enderecoDetalhado,
            latitude,
            longitude,
            isComparticipada,
            instituicaoEnsinoId,
            provedorNotificacaoId,
            categoriaInstituicaoId,
            unidadePagadoraId,
            tipoVinculoId,
            tipoInstalacaoId,
            sedeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstituicaoEnsinoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (unidadeOrganica != null ? "unidadeOrganica=" + unidadeOrganica + ", " : "") +
            (nomeFiscal != null ? "nomeFiscal=" + nomeFiscal + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (nif != null ? "nif=" + nif + ", " : "") +
            (cae != null ? "cae=" + cae + ", " : "") +
            (niss != null ? "niss=" + niss + ", " : "") +
            (fundador != null ? "fundador=" + fundador + ", " : "") +
            (fundacao != null ? "fundacao=" + fundacao + ", " : "") +
            (dimensao != null ? "dimensao=" + dimensao + ", " : "") +
            (slogam != null ? "slogam=" + slogam + ", " : "") +
            (telefone != null ? "telefone=" + telefone + ", " : "") +
            (telemovel != null ? "telemovel=" + telemovel + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (website != null ? "website=" + website + ", " : "") +
            (codigoPostal != null ? "codigoPostal=" + codigoPostal + ", " : "") +
            (enderecoDetalhado != null ? "enderecoDetalhado=" + enderecoDetalhado + ", " : "") +
            (latitude != null ? "latitude=" + latitude + ", " : "") +
            (longitude != null ? "longitude=" + longitude + ", " : "") +
            (isComparticipada != null ? "isComparticipada=" + isComparticipada + ", " : "") +
            (instituicaoEnsinoId != null ? "instituicaoEnsinoId=" + instituicaoEnsinoId + ", " : "") +
            (provedorNotificacaoId != null ? "provedorNotificacaoId=" + provedorNotificacaoId + ", " : "") +
            (categoriaInstituicaoId != null ? "categoriaInstituicaoId=" + categoriaInstituicaoId + ", " : "") +
            (unidadePagadoraId != null ? "unidadePagadoraId=" + unidadePagadoraId + ", " : "") +
            (tipoVinculoId != null ? "tipoVinculoId=" + tipoVinculoId + ", " : "") +
            (tipoInstalacaoId != null ? "tipoInstalacaoId=" + tipoInstalacaoId + ", " : "") +
            (sedeId != null ? "sedeId=" + sedeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
