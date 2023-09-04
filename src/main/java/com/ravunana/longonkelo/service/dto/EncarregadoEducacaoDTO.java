package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.Sexo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.EncarregadoEducacao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EncarregadoEducacaoDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] fotografia;

    private String fotografiaContentType;

    @NotNull
    private String nome;

    @NotNull
    private LocalDate nascimento;

    private String nif;

    @NotNull
    private Sexo sexo;

    @NotNull
    private String documentoNumero;

    @NotNull
    private String telefonePrincipal;

    private String telefoneAlternativo;

    private String email;

    private String residencia;

    private String enderecoTrabalho;

    @DecimalMin(value = "0")
    private BigDecimal rendaMensal;

    private String empresaTrabalho;

    private String hash;

    private LookupItemDTO grauParentesco;

    private LookupItemDTO tipoDocumento;

    private LookupItemDTO profissao;

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

    public String getDocumentoNumero() {
        return documentoNumero;
    }

    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    public String getTelefonePrincipal() {
        return telefonePrincipal;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneAlternativo() {
        return telefoneAlternativo;
    }

    public void setTelefoneAlternativo(String telefoneAlternativo) {
        this.telefoneAlternativo = telefoneAlternativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getEnderecoTrabalho() {
        return enderecoTrabalho;
    }

    public void setEnderecoTrabalho(String enderecoTrabalho) {
        this.enderecoTrabalho = enderecoTrabalho;
    }

    public BigDecimal getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(BigDecimal rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public String getEmpresaTrabalho() {
        return empresaTrabalho;
    }

    public void setEmpresaTrabalho(String empresaTrabalho) {
        this.empresaTrabalho = empresaTrabalho;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public LookupItemDTO getGrauParentesco() {
        return grauParentesco;
    }

    public void setGrauParentesco(LookupItemDTO grauParentesco) {
        this.grauParentesco = grauParentesco;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EncarregadoEducacaoDTO)) {
            return false;
        }

        EncarregadoEducacaoDTO encarregadoEducacaoDTO = (EncarregadoEducacaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, encarregadoEducacaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EncarregadoEducacaoDTO{" +
            "id=" + getId() +
            ", fotografia='" + getFotografia() + "'" +
            ", nome='" + getNome() + "'" +
            ", nascimento='" + getNascimento() + "'" +
            ", nif='" + getNif() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", documentoNumero='" + getDocumentoNumero() + "'" +
            ", telefonePrincipal='" + getTelefonePrincipal() + "'" +
            ", telefoneAlternativo='" + getTelefoneAlternativo() + "'" +
            ", email='" + getEmail() + "'" +
            ", residencia='" + getResidencia() + "'" +
            ", enderecoTrabalho='" + getEnderecoTrabalho() + "'" +
            ", rendaMensal=" + getRendaMensal() +
            ", empresaTrabalho='" + getEmpresaTrabalho() + "'" +
            ", hash='" + getHash() + "'" +
            ", grauParentesco=" + getGrauParentesco() +
            ", tipoDocumento=" + getTipoDocumento() +
            ", profissao=" + getProfissao() +
            "}";
    }
}
