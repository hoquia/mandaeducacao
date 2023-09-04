package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Ocorrencia} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OcorrenciaDTO implements Serializable {

    private Long id;

    private String uniqueOcorrencia;

    @Lob
    private String descricao;

    @Lob
    private byte[] evidencia;

    private String evidenciaContentType;
    private String hash;

    @NotNull
    private ZonedDateTime timestamp;

    private UserDTO utilizador;

    private OcorrenciaDTO referencia;

    private DocenteDTO docente;

    private MatriculaDTO matricula;

    private CategoriaOcorrenciaDTO estado;

    private LicaoDTO licao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueOcorrencia() {
        return uniqueOcorrencia;
    }

    public void setUniqueOcorrencia(String uniqueOcorrencia) {
        this.uniqueOcorrencia = uniqueOcorrencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(byte[] evidencia) {
        this.evidencia = evidencia;
    }

    public String getEvidenciaContentType() {
        return evidenciaContentType;
    }

    public void setEvidenciaContentType(String evidenciaContentType) {
        this.evidenciaContentType = evidenciaContentType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public OcorrenciaDTO getReferencia() {
        return referencia;
    }

    public void setReferencia(OcorrenciaDTO referencia) {
        this.referencia = referencia;
    }

    public DocenteDTO getDocente() {
        return docente;
    }

    public void setDocente(DocenteDTO docente) {
        this.docente = docente;
    }

    public MatriculaDTO getMatricula() {
        return matricula;
    }

    public void setMatricula(MatriculaDTO matricula) {
        this.matricula = matricula;
    }

    public CategoriaOcorrenciaDTO getEstado() {
        return estado;
    }

    public void setEstado(CategoriaOcorrenciaDTO estado) {
        this.estado = estado;
    }

    public LicaoDTO getLicao() {
        return licao;
    }

    public void setLicao(LicaoDTO licao) {
        this.licao = licao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OcorrenciaDTO)) {
            return false;
        }

        OcorrenciaDTO ocorrenciaDTO = (OcorrenciaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ocorrenciaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OcorrenciaDTO{" +
            "id=" + getId() +
            ", uniqueOcorrencia='" + getUniqueOcorrencia() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", evidencia='" + getEvidencia() + "'" +
            ", hash='" + getHash() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", utilizador=" + getUtilizador() +
            ", referencia=" + getReferencia() +
            ", docente=" + getDocente() +
            ", matricula=" + getMatricula() +
            ", estado=" + getEstado() +
            ", licao=" + getLicao() +
            "}";
    }
}
