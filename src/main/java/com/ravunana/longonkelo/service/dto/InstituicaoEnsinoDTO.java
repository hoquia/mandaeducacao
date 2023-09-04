package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.InstituicaoEnsino} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InstituicaoEnsinoDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] logotipo;

    private String logotipoContentType;

    @NotNull
    @Size(min = 5, max = 100)
    private String unidadeOrganica;

    @Size(min = 5, max = 100)
    private String nomeFiscal;

    @NotNull
    private String numero;

    @Size(max = 15)
    private String nif;

    private String cae;

    @Size(max = 15)
    private String niss;

    private String fundador;

    private LocalDate fundacao;

    private String dimensao;

    private String slogam;

    @NotNull
    private String telefone;

    private String telemovel;

    @NotNull
    private String email;

    private String website;

    @Size(max = 10)
    private String codigoPostal;

    @NotNull
    @Size(min = 5)
    private String enderecoDetalhado;

    private Double latitude;

    private Double longitude;

    @Lob
    private String descricao;

    private Boolean isComparticipada;

    @Lob
    private String termosCompromissos;

    private LookupItemDTO categoriaInstituicao;

    private LookupItemDTO unidadePagadora;

    private LookupItemDTO tipoVinculo;

    private LookupItemDTO tipoInstalacao;

    private InstituicaoEnsinoDTO sede;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getLogotipoContentType() {
        return logotipoContentType;
    }

    public void setLogotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
    }

    public String getUnidadeOrganica() {
        return unidadeOrganica;
    }

    public void setUnidadeOrganica(String unidadeOrganica) {
        this.unidadeOrganica = unidadeOrganica;
    }

    public String getNomeFiscal() {
        return nomeFiscal;
    }

    public void setNomeFiscal(String nomeFiscal) {
        this.nomeFiscal = nomeFiscal;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCae() {
        return cae;
    }

    public void setCae(String cae) {
        this.cae = cae;
    }

    public String getNiss() {
        return niss;
    }

    public void setNiss(String niss) {
        this.niss = niss;
    }

    public String getFundador() {
        return fundador;
    }

    public void setFundador(String fundador) {
        this.fundador = fundador;
    }

    public LocalDate getFundacao() {
        return fundacao;
    }

    public void setFundacao(LocalDate fundacao) {
        this.fundacao = fundacao;
    }

    public String getDimensao() {
        return dimensao;
    }

    public void setDimensao(String dimensao) {
        this.dimensao = dimensao;
    }

    public String getSlogam() {
        return slogam;
    }

    public void setSlogam(String slogam) {
        this.slogam = slogam;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getEnderecoDetalhado() {
        return enderecoDetalhado;
    }

    public void setEnderecoDetalhado(String enderecoDetalhado) {
        this.enderecoDetalhado = enderecoDetalhado;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getIsComparticipada() {
        return isComparticipada;
    }

    public void setIsComparticipada(Boolean isComparticipada) {
        this.isComparticipada = isComparticipada;
    }

    public String getTermosCompromissos() {
        return termosCompromissos;
    }

    public void setTermosCompromissos(String termosCompromissos) {
        this.termosCompromissos = termosCompromissos;
    }

    public LookupItemDTO getCategoriaInstituicao() {
        return categoriaInstituicao;
    }

    public void setCategoriaInstituicao(LookupItemDTO categoriaInstituicao) {
        this.categoriaInstituicao = categoriaInstituicao;
    }

    public LookupItemDTO getUnidadePagadora() {
        return unidadePagadora;
    }

    public void setUnidadePagadora(LookupItemDTO unidadePagadora) {
        this.unidadePagadora = unidadePagadora;
    }

    public LookupItemDTO getTipoVinculo() {
        return tipoVinculo;
    }

    public void setTipoVinculo(LookupItemDTO tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
    }

    public LookupItemDTO getTipoInstalacao() {
        return tipoInstalacao;
    }

    public void setTipoInstalacao(LookupItemDTO tipoInstalacao) {
        this.tipoInstalacao = tipoInstalacao;
    }

    public InstituicaoEnsinoDTO getSede() {
        return sede;
    }

    public void setSede(InstituicaoEnsinoDTO sede) {
        this.sede = sede;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstituicaoEnsinoDTO)) {
            return false;
        }

        InstituicaoEnsinoDTO instituicaoEnsinoDTO = (InstituicaoEnsinoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, instituicaoEnsinoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstituicaoEnsinoDTO{" +
            "id=" + getId() +
            ", logotipo='" + getLogotipo() + "'" +
            ", unidadeOrganica='" + getUnidadeOrganica() + "'" +
            ", nomeFiscal='" + getNomeFiscal() + "'" +
            ", numero='" + getNumero() + "'" +
            ", nif='" + getNif() + "'" +
            ", cae='" + getCae() + "'" +
            ", niss='" + getNiss() + "'" +
            ", fundador='" + getFundador() + "'" +
            ", fundacao='" + getFundacao() + "'" +
            ", dimensao='" + getDimensao() + "'" +
            ", slogam='" + getSlogam() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", telemovel='" + getTelemovel() + "'" +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", enderecoDetalhado='" + getEnderecoDetalhado() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", descricao='" + getDescricao() + "'" +
            ", isComparticipada='" + getIsComparticipada() + "'" +
            ", termosCompromissos='" + getTermosCompromissos() + "'" +
            ", categoriaInstituicao=" + getCategoriaInstituicao() +
            ", unidadePagadora=" + getUnidadePagadora() +
            ", tipoVinculo=" + getTipoVinculo() +
            ", tipoInstalacao=" + getTipoInstalacao() +
            ", sede=" + getSede() +
            "}";
    }
}
