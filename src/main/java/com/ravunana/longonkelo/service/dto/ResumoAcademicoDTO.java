package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.ResumoAcademico} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResumoAcademicoDTO implements Serializable {

    private Long id;

    @NotNull
    private String temaProjecto;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    private Double notaProjecto;

    @Lob
    private String observacao;

    private String localEstagio;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    private Double notaEstagio;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    private Double mediaFinalDisciplina;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    private Double classificacaoFinal;

    @NotNull
    private String numeroGrupo;

    @NotNull
    private String mesaDefesa;

    @NotNull
    private String livroRegistro;

    @NotNull
    private String numeroFolha;

    @NotNull
    private String chefeSecretariaPedagogica;

    @NotNull
    private String subDirectorPedagogico;

    @NotNull
    private String directorGeral;

    @NotNull
    private String tutorProjecto;

    @NotNull
    private String juriMesa;

    @NotNull
    private String empresaEstagio;

    @Lob
    private byte[] assinaturaDigital;

    private String assinaturaDigitalContentType;

    @NotNull
    private String hash;

    private UserDTO utilizador;

    private TurmaDTO ultimaTurmaMatriculada;

    private DiscenteDTO discente;

    private EstadoDisciplinaCurricularDTO situacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemaProjecto() {
        return temaProjecto;
    }

    public void setTemaProjecto(String temaProjecto) {
        this.temaProjecto = temaProjecto;
    }

    public Double getNotaProjecto() {
        return notaProjecto;
    }

    public void setNotaProjecto(Double notaProjecto) {
        this.notaProjecto = notaProjecto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getLocalEstagio() {
        return localEstagio;
    }

    public void setLocalEstagio(String localEstagio) {
        this.localEstagio = localEstagio;
    }

    public Double getNotaEstagio() {
        return notaEstagio;
    }

    public void setNotaEstagio(Double notaEstagio) {
        this.notaEstagio = notaEstagio;
    }

    public Double getMediaFinalDisciplina() {
        return mediaFinalDisciplina;
    }

    public void setMediaFinalDisciplina(Double mediaFinalDisciplina) {
        this.mediaFinalDisciplina = mediaFinalDisciplina;
    }

    public Double getClassificacaoFinal() {
        return classificacaoFinal;
    }

    public void setClassificacaoFinal(Double classificacaoFinal) {
        this.classificacaoFinal = classificacaoFinal;
    }

    public String getNumeroGrupo() {
        return numeroGrupo;
    }

    public void setNumeroGrupo(String numeroGrupo) {
        this.numeroGrupo = numeroGrupo;
    }

    public String getMesaDefesa() {
        return mesaDefesa;
    }

    public void setMesaDefesa(String mesaDefesa) {
        this.mesaDefesa = mesaDefesa;
    }

    public String getLivroRegistro() {
        return livroRegistro;
    }

    public void setLivroRegistro(String livroRegistro) {
        this.livroRegistro = livroRegistro;
    }

    public String getNumeroFolha() {
        return numeroFolha;
    }

    public void setNumeroFolha(String numeroFolha) {
        this.numeroFolha = numeroFolha;
    }

    public String getChefeSecretariaPedagogica() {
        return chefeSecretariaPedagogica;
    }

    public void setChefeSecretariaPedagogica(String chefeSecretariaPedagogica) {
        this.chefeSecretariaPedagogica = chefeSecretariaPedagogica;
    }

    public String getSubDirectorPedagogico() {
        return subDirectorPedagogico;
    }

    public void setSubDirectorPedagogico(String subDirectorPedagogico) {
        this.subDirectorPedagogico = subDirectorPedagogico;
    }

    public String getDirectorGeral() {
        return directorGeral;
    }

    public void setDirectorGeral(String directorGeral) {
        this.directorGeral = directorGeral;
    }

    public String getTutorProjecto() {
        return tutorProjecto;
    }

    public void setTutorProjecto(String tutorProjecto) {
        this.tutorProjecto = tutorProjecto;
    }

    public String getJuriMesa() {
        return juriMesa;
    }

    public void setJuriMesa(String juriMesa) {
        this.juriMesa = juriMesa;
    }

    public String getEmpresaEstagio() {
        return empresaEstagio;
    }

    public void setEmpresaEstagio(String empresaEstagio) {
        this.empresaEstagio = empresaEstagio;
    }

    public byte[] getAssinaturaDigital() {
        return assinaturaDigital;
    }

    public void setAssinaturaDigital(byte[] assinaturaDigital) {
        this.assinaturaDigital = assinaturaDigital;
    }

    public String getAssinaturaDigitalContentType() {
        return assinaturaDigitalContentType;
    }

    public void setAssinaturaDigitalContentType(String assinaturaDigitalContentType) {
        this.assinaturaDigitalContentType = assinaturaDigitalContentType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public TurmaDTO getUltimaTurmaMatriculada() {
        return ultimaTurmaMatriculada;
    }

    public void setUltimaTurmaMatriculada(TurmaDTO ultimaTurmaMatriculada) {
        this.ultimaTurmaMatriculada = ultimaTurmaMatriculada;
    }

    public DiscenteDTO getDiscente() {
        return discente;
    }

    public void setDiscente(DiscenteDTO discente) {
        this.discente = discente;
    }

    public EstadoDisciplinaCurricularDTO getSituacao() {
        return situacao;
    }

    public void setSituacao(EstadoDisciplinaCurricularDTO situacao) {
        this.situacao = situacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumoAcademicoDTO)) {
            return false;
        }

        ResumoAcademicoDTO resumoAcademicoDTO = (ResumoAcademicoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resumoAcademicoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumoAcademicoDTO{" +
            "id=" + getId() +
            ", temaProjecto='" + getTemaProjecto() + "'" +
            ", notaProjecto=" + getNotaProjecto() +
            ", observacao='" + getObservacao() + "'" +
            ", localEstagio='" + getLocalEstagio() + "'" +
            ", notaEstagio=" + getNotaEstagio() +
            ", mediaFinalDisciplina=" + getMediaFinalDisciplina() +
            ", classificacaoFinal=" + getClassificacaoFinal() +
            ", numeroGrupo='" + getNumeroGrupo() + "'" +
            ", mesaDefesa='" + getMesaDefesa() + "'" +
            ", livroRegistro='" + getLivroRegistro() + "'" +
            ", numeroFolha='" + getNumeroFolha() + "'" +
            ", chefeSecretariaPedagogica='" + getChefeSecretariaPedagogica() + "'" +
            ", subDirectorPedagogico='" + getSubDirectorPedagogico() + "'" +
            ", directorGeral='" + getDirectorGeral() + "'" +
            ", tutorProjecto='" + getTutorProjecto() + "'" +
            ", juriMesa='" + getJuriMesa() + "'" +
            ", empresaEstagio='" + getEmpresaEstagio() + "'" +
            ", assinaturaDigital='" + getAssinaturaDigital() + "'" +
            ", hash='" + getHash() + "'" +
            ", utilizador=" + getUtilizador() +
            ", ultimaTurmaMatriculada=" + getUltimaTurmaMatriculada() +
            ", discente=" + getDiscente() +
            ", situacao=" + getSituacao() +
            "}";
    }
}
