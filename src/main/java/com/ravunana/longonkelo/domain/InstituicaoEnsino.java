package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A InstituicaoEnsino.
 */
@Entity
@Table(name = "instituicao_ensino")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InstituicaoEnsino implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "logotipo", nullable = false)
    private byte[] logotipo;

    @NotNull
    @Column(name = "logotipo_content_type", nullable = false)
    private String logotipoContentType;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "unidade_organica", length = 100, nullable = false, unique = true)
    private String unidadeOrganica;

    @Size(min = 5, max = 100)
    @Column(name = "nome_fiscal", length = 100, unique = true)
    private String nomeFiscal;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Size(max = 15)
    @Column(name = "nif", length = 15, unique = true)
    private String nif;

    @Column(name = "cae", unique = true)
    private String cae;

    @Size(max = 15)
    @Column(name = "niss", length = 15, unique = true)
    private String niss;

    @Column(name = "fundador")
    private String fundador;

    @Column(name = "fundacao")
    private LocalDate fundacao;

    @Column(name = "dimensao")
    private String dimensao;

    @Column(name = "slogam")
    private String slogam;

    @NotNull
    @Column(name = "telefone", nullable = false, unique = true)
    private String telefone;

    @Column(name = "telemovel", unique = true)
    private String telemovel;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "website", unique = true)
    private String website;

    @Size(max = 10)
    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

    @NotNull
    @Size(min = 5)
    @Column(name = "endereco_detalhado", nullable = false)
    private String enderecoDetalhado;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "is_comparticipada")
    private Boolean isComparticipada;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "termos_compromissos")
    private String termosCompromissos;

    @OneToMany(mappedBy = "sede")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "instituicaoEnsinos",
            "provedorNotificacaos",
            "categoriaInstituicao",
            "unidadePagadora",
            "tipoVinculo",
            "tipoInstalacao",
            "sede",
        },
        allowSetters = true
    )
    private Set<InstituicaoEnsino> instituicaoEnsinos = new HashSet<>();

    @OneToMany(mappedBy = "instituicao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "instituicao" }, allowSetters = true)
    private Set<ProvedorNotificacao> provedorNotificacaos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem categoriaInstituicao;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem unidadePagadora;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem tipoVinculo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem tipoInstalacao;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "instituicaoEnsinos",
            "provedorNotificacaos",
            "categoriaInstituicao",
            "unidadePagadora",
            "tipoVinculo",
            "tipoInstalacao",
            "sede",
        },
        allowSetters = true
    )
    private InstituicaoEnsino sede;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InstituicaoEnsino id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLogotipo() {
        return this.logotipo;
    }

    public InstituicaoEnsino logotipo(byte[] logotipo) {
        this.setLogotipo(logotipo);
        return this;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getLogotipoContentType() {
        return this.logotipoContentType;
    }

    public InstituicaoEnsino logotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
        return this;
    }

    public void setLogotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
    }

    public String getUnidadeOrganica() {
        return this.unidadeOrganica;
    }

    public InstituicaoEnsino unidadeOrganica(String unidadeOrganica) {
        this.setUnidadeOrganica(unidadeOrganica);
        return this;
    }

    public void setUnidadeOrganica(String unidadeOrganica) {
        this.unidadeOrganica = unidadeOrganica;
    }

    public String getNomeFiscal() {
        return this.nomeFiscal;
    }

    public InstituicaoEnsino nomeFiscal(String nomeFiscal) {
        this.setNomeFiscal(nomeFiscal);
        return this;
    }

    public void setNomeFiscal(String nomeFiscal) {
        this.nomeFiscal = nomeFiscal;
    }

    public String getNumero() {
        return this.numero;
    }

    public InstituicaoEnsino numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNif() {
        return this.nif;
    }

    public InstituicaoEnsino nif(String nif) {
        this.setNif(nif);
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCae() {
        return this.cae;
    }

    public InstituicaoEnsino cae(String cae) {
        this.setCae(cae);
        return this;
    }

    public void setCae(String cae) {
        this.cae = cae;
    }

    public String getNiss() {
        return this.niss;
    }

    public InstituicaoEnsino niss(String niss) {
        this.setNiss(niss);
        return this;
    }

    public void setNiss(String niss) {
        this.niss = niss;
    }

    public String getFundador() {
        return this.fundador;
    }

    public InstituicaoEnsino fundador(String fundador) {
        this.setFundador(fundador);
        return this;
    }

    public void setFundador(String fundador) {
        this.fundador = fundador;
    }

    public LocalDate getFundacao() {
        return this.fundacao;
    }

    public InstituicaoEnsino fundacao(LocalDate fundacao) {
        this.setFundacao(fundacao);
        return this;
    }

    public void setFundacao(LocalDate fundacao) {
        this.fundacao = fundacao;
    }

    public String getDimensao() {
        return this.dimensao;
    }

    public InstituicaoEnsino dimensao(String dimensao) {
        this.setDimensao(dimensao);
        return this;
    }

    public void setDimensao(String dimensao) {
        this.dimensao = dimensao;
    }

    public String getSlogam() {
        return this.slogam;
    }

    public InstituicaoEnsino slogam(String slogam) {
        this.setSlogam(slogam);
        return this;
    }

    public void setSlogam(String slogam) {
        this.slogam = slogam;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public InstituicaoEnsino telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelemovel() {
        return this.telemovel;
    }

    public InstituicaoEnsino telemovel(String telemovel) {
        this.setTelemovel(telemovel);
        return this;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public String getEmail() {
        return this.email;
    }

    public InstituicaoEnsino email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return this.website;
    }

    public InstituicaoEnsino website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public InstituicaoEnsino codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getEnderecoDetalhado() {
        return this.enderecoDetalhado;
    }

    public InstituicaoEnsino enderecoDetalhado(String enderecoDetalhado) {
        this.setEnderecoDetalhado(enderecoDetalhado);
        return this;
    }

    public void setEnderecoDetalhado(String enderecoDetalhado) {
        this.enderecoDetalhado = enderecoDetalhado;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public InstituicaoEnsino latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public InstituicaoEnsino longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public InstituicaoEnsino descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getIsComparticipada() {
        return this.isComparticipada;
    }

    public InstituicaoEnsino isComparticipada(Boolean isComparticipada) {
        this.setIsComparticipada(isComparticipada);
        return this;
    }

    public void setIsComparticipada(Boolean isComparticipada) {
        this.isComparticipada = isComparticipada;
    }

    public String getTermosCompromissos() {
        return this.termosCompromissos;
    }

    public InstituicaoEnsino termosCompromissos(String termosCompromissos) {
        this.setTermosCompromissos(termosCompromissos);
        return this;
    }

    public void setTermosCompromissos(String termosCompromissos) {
        this.termosCompromissos = termosCompromissos;
    }

    public Set<InstituicaoEnsino> getInstituicaoEnsinos() {
        return this.instituicaoEnsinos;
    }

    public void setInstituicaoEnsinos(Set<InstituicaoEnsino> instituicaoEnsinos) {
        if (this.instituicaoEnsinos != null) {
            this.instituicaoEnsinos.forEach(i -> i.setSede(null));
        }
        if (instituicaoEnsinos != null) {
            instituicaoEnsinos.forEach(i -> i.setSede(this));
        }
        this.instituicaoEnsinos = instituicaoEnsinos;
    }

    public InstituicaoEnsino instituicaoEnsinos(Set<InstituicaoEnsino> instituicaoEnsinos) {
        this.setInstituicaoEnsinos(instituicaoEnsinos);
        return this;
    }

    public InstituicaoEnsino addInstituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
        this.instituicaoEnsinos.add(instituicaoEnsino);
        instituicaoEnsino.setSede(this);
        return this;
    }

    public InstituicaoEnsino removeInstituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
        this.instituicaoEnsinos.remove(instituicaoEnsino);
        instituicaoEnsino.setSede(null);
        return this;
    }

    public Set<ProvedorNotificacao> getProvedorNotificacaos() {
        return this.provedorNotificacaos;
    }

    public void setProvedorNotificacaos(Set<ProvedorNotificacao> provedorNotificacaos) {
        if (this.provedorNotificacaos != null) {
            this.provedorNotificacaos.forEach(i -> i.setInstituicao(null));
        }
        if (provedorNotificacaos != null) {
            provedorNotificacaos.forEach(i -> i.setInstituicao(this));
        }
        this.provedorNotificacaos = provedorNotificacaos;
    }

    public InstituicaoEnsino provedorNotificacaos(Set<ProvedorNotificacao> provedorNotificacaos) {
        this.setProvedorNotificacaos(provedorNotificacaos);
        return this;
    }

    public InstituicaoEnsino addProvedorNotificacao(ProvedorNotificacao provedorNotificacao) {
        this.provedorNotificacaos.add(provedorNotificacao);
        provedorNotificacao.setInstituicao(this);
        return this;
    }

    public InstituicaoEnsino removeProvedorNotificacao(ProvedorNotificacao provedorNotificacao) {
        this.provedorNotificacaos.remove(provedorNotificacao);
        provedorNotificacao.setInstituicao(null);
        return this;
    }

    public LookupItem getCategoriaInstituicao() {
        return this.categoriaInstituicao;
    }

    public void setCategoriaInstituicao(LookupItem lookupItem) {
        this.categoriaInstituicao = lookupItem;
    }

    public InstituicaoEnsino categoriaInstituicao(LookupItem lookupItem) {
        this.setCategoriaInstituicao(lookupItem);
        return this;
    }

    public LookupItem getUnidadePagadora() {
        return this.unidadePagadora;
    }

    public void setUnidadePagadora(LookupItem lookupItem) {
        this.unidadePagadora = lookupItem;
    }

    public InstituicaoEnsino unidadePagadora(LookupItem lookupItem) {
        this.setUnidadePagadora(lookupItem);
        return this;
    }

    public LookupItem getTipoVinculo() {
        return this.tipoVinculo;
    }

    public void setTipoVinculo(LookupItem lookupItem) {
        this.tipoVinculo = lookupItem;
    }

    public InstituicaoEnsino tipoVinculo(LookupItem lookupItem) {
        this.setTipoVinculo(lookupItem);
        return this;
    }

    public LookupItem getTipoInstalacao() {
        return this.tipoInstalacao;
    }

    public void setTipoInstalacao(LookupItem lookupItem) {
        this.tipoInstalacao = lookupItem;
    }

    public InstituicaoEnsino tipoInstalacao(LookupItem lookupItem) {
        this.setTipoInstalacao(lookupItem);
        return this;
    }

    public InstituicaoEnsino getSede() {
        return this.sede;
    }

    public void setSede(InstituicaoEnsino instituicaoEnsino) {
        this.sede = instituicaoEnsino;
    }

    public InstituicaoEnsino sede(InstituicaoEnsino instituicaoEnsino) {
        this.setSede(instituicaoEnsino);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstituicaoEnsino)) {
            return false;
        }
        return id != null && id.equals(((InstituicaoEnsino) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstituicaoEnsino{" +
            "id=" + getId() +
            ", logotipo='" + getLogotipo() + "'" +
            ", logotipoContentType='" + getLogotipoContentType() + "'" +
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
            "}";
    }
}
