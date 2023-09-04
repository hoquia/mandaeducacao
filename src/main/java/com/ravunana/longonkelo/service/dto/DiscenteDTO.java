package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.Sexo;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Discente} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiscenteDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] fotografia;

    private String fotografiaContentType;

    @NotNull
    private String nome;

    @NotNull
    private LocalDate nascimento;

    @NotNull
    private String documentoNumero;

    @NotNull
    private LocalDate documentoEmissao;

    @NotNull
    private LocalDate documentoValidade;

    private String nif;

    @NotNull
    private Sexo sexo;

    @NotNull
    private String pai;

    @NotNull
    private String mae;

    private String telefonePrincipal;

    private String telefoneParente;

    private String email;

    private Boolean isEncarregadoEducacao;

    private Boolean isTrabalhador;

    private Boolean isFilhoAntigoConbatente;

    private Boolean isAtestadoPobreza;

    private String nomeMedico;

    @Size(max = 9)
    private String telefoneMedico;

    private String instituicaoParticularSaude;

    @Min(value = 0)
    private Integer altura;

    @DecimalMin(value = "0")
    private Double peso;

    private Boolean isAsmatico;

    private Boolean isAlergico;

    private Boolean isPraticaEducacaoFisica;

    private Boolean isAutorizadoMedicacao;

    @Lob
    private String cuidadosEspeciaisSaude;

    @NotNull
    private String numeroProcesso;

    private ZonedDateTime dataIngresso;

    private String hash;

    @Lob
    private String observacao;

    private LookupItemDTO nacionalidade;

    private LookupItemDTO naturalidade;

    private LookupItemDTO tipoDocumento;

    private LookupItemDTO profissao;

    private LookupItemDTO grupoSanguinio;

    private LookupItemDTO necessidadeEspecial;

    private EncarregadoEducacaoDTO encarregadoEducacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public String getFotografiaContentType() {
        return fotografiaContentType;
    }

    public void setFotografiaContentType(String fotografiaContentType) {
        this.fotografiaContentType = fotografiaContentType;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getDocumentoNumero() {
        return documentoNumero;
    }

    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    public LocalDate getDocumentoEmissao() {
        return documentoEmissao;
    }

    public void setDocumentoEmissao(LocalDate documentoEmissao) {
        this.documentoEmissao = documentoEmissao;
    }

    public LocalDate getDocumentoValidade() {
        return documentoValidade;
    }

    public void setDocumentoValidade(LocalDate documentoValidade) {
        this.documentoValidade = documentoValidade;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getTelefonePrincipal() {
        return telefonePrincipal;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneParente() {
        return telefoneParente;
    }

    public void setTelefoneParente(String telefoneParente) {
        this.telefoneParente = telefoneParente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsEncarregadoEducacao() {
        return isEncarregadoEducacao;
    }

    public void setIsEncarregadoEducacao(Boolean isEncarregadoEducacao) {
        this.isEncarregadoEducacao = isEncarregadoEducacao;
    }

    public Boolean getIsTrabalhador() {
        return isTrabalhador;
    }

    public void setIsTrabalhador(Boolean isTrabalhador) {
        this.isTrabalhador = isTrabalhador;
    }

    public Boolean getIsFilhoAntigoConbatente() {
        return isFilhoAntigoConbatente;
    }

    public void setIsFilhoAntigoConbatente(Boolean isFilhoAntigoConbatente) {
        this.isFilhoAntigoConbatente = isFilhoAntigoConbatente;
    }

    public Boolean getIsAtestadoPobreza() {
        return isAtestadoPobreza;
    }

    public void setIsAtestadoPobreza(Boolean isAtestadoPobreza) {
        this.isAtestadoPobreza = isAtestadoPobreza;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getTelefoneMedico() {
        return telefoneMedico;
    }

    public void setTelefoneMedico(String telefoneMedico) {
        this.telefoneMedico = telefoneMedico;
    }

    public String getInstituicaoParticularSaude() {
        return instituicaoParticularSaude;
    }

    public void setInstituicaoParticularSaude(String instituicaoParticularSaude) {
        this.instituicaoParticularSaude = instituicaoParticularSaude;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Boolean getIsAsmatico() {
        return isAsmatico;
    }

    public void setIsAsmatico(Boolean isAsmatico) {
        this.isAsmatico = isAsmatico;
    }

    public Boolean getIsAlergico() {
        return isAlergico;
    }

    public void setIsAlergico(Boolean isAlergico) {
        this.isAlergico = isAlergico;
    }

    public Boolean getIsPraticaEducacaoFisica() {
        return isPraticaEducacaoFisica;
    }

    public void setIsPraticaEducacaoFisica(Boolean isPraticaEducacaoFisica) {
        this.isPraticaEducacaoFisica = isPraticaEducacaoFisica;
    }

    public Boolean getIsAutorizadoMedicacao() {
        return isAutorizadoMedicacao;
    }

    public void setIsAutorizadoMedicacao(Boolean isAutorizadoMedicacao) {
        this.isAutorizadoMedicacao = isAutorizadoMedicacao;
    }

    public String getCuidadosEspeciaisSaude() {
        return cuidadosEspeciaisSaude;
    }

    public void setCuidadosEspeciaisSaude(String cuidadosEspeciaisSaude) {
        this.cuidadosEspeciaisSaude = cuidadosEspeciaisSaude;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public ZonedDateTime getDataIngresso() {
        return dataIngresso;
    }

    public void setDataIngresso(ZonedDateTime dataIngresso) {
        this.dataIngresso = dataIngresso;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LookupItemDTO getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(LookupItemDTO nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public LookupItemDTO getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(LookupItemDTO naturalidade) {
        this.naturalidade = naturalidade;
    }

    public LookupItemDTO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(LookupItemDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public LookupItemDTO getProfissao() {
        return profissao;
    }

    public void setProfissao(LookupItemDTO profissao) {
        this.profissao = profissao;
    }

    public LookupItemDTO getGrupoSanguinio() {
        return grupoSanguinio;
    }

    public void setGrupoSanguinio(LookupItemDTO grupoSanguinio) {
        this.grupoSanguinio = grupoSanguinio;
    }

    public LookupItemDTO getNecessidadeEspecial() {
        return necessidadeEspecial;
    }

    public void setNecessidadeEspecial(LookupItemDTO necessidadeEspecial) {
        this.necessidadeEspecial = necessidadeEspecial;
    }

    public EncarregadoEducacaoDTO getEncarregadoEducacao() {
        return encarregadoEducacao;
    }

    public void setEncarregadoEducacao(EncarregadoEducacaoDTO encarregadoEducacao) {
        this.encarregadoEducacao = encarregadoEducacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscenteDTO)) {
            return false;
        }

        DiscenteDTO discenteDTO = (DiscenteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, discenteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscenteDTO{" +
            "id=" + getId() +
            ", fotografia='" + getFotografia() + "'" +
            ", nome='" + getNome() + "'" +
            ", nascimento='" + getNascimento() + "'" +
            ", documentoNumero='" + getDocumentoNumero() + "'" +
            ", documentoEmissao='" + getDocumentoEmissao() + "'" +
            ", documentoValidade='" + getDocumentoValidade() + "'" +
            ", nif='" + getNif() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", pai='" + getPai() + "'" +
            ", mae='" + getMae() + "'" +
            ", telefonePrincipal='" + getTelefonePrincipal() + "'" +
            ", telefoneParente='" + getTelefoneParente() + "'" +
            ", email='" + getEmail() + "'" +
            ", isEncarregadoEducacao='" + getIsEncarregadoEducacao() + "'" +
            ", isTrabalhador='" + getIsTrabalhador() + "'" +
            ", isFilhoAntigoConbatente='" + getIsFilhoAntigoConbatente() + "'" +
            ", isAtestadoPobreza='" + getIsAtestadoPobreza() + "'" +
            ", nomeMedico='" + getNomeMedico() + "'" +
            ", telefoneMedico='" + getTelefoneMedico() + "'" +
            ", instituicaoParticularSaude='" + getInstituicaoParticularSaude() + "'" +
            ", altura=" + getAltura() +
            ", peso=" + getPeso() +
            ", isAsmatico='" + getIsAsmatico() + "'" +
            ", isAlergico='" + getIsAlergico() + "'" +
            ", isPraticaEducacaoFisica='" + getIsPraticaEducacaoFisica() + "'" +
            ", isAutorizadoMedicacao='" + getIsAutorizadoMedicacao() + "'" +
            ", cuidadosEspeciaisSaude='" + getCuidadosEspeciaisSaude() + "'" +
            ", numeroProcesso='" + getNumeroProcesso() + "'" +
            ", dataIngresso='" + getDataIngresso() + "'" +
            ", hash='" + getHash() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", nacionalidade=" + getNacionalidade() +
            ", naturalidade=" + getNaturalidade() +
            ", tipoDocumento=" + getTipoDocumento() +
            ", profissao=" + getProfissao() +
            ", grupoSanguinio=" + getGrupoSanguinio() +
            ", necessidadeEspecial=" + getNecessidadeEspecial() +
            ", encarregadoEducacao=" + getEncarregadoEducacao() +
            "}";
    }
}
