package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcessoSelectivoMatriculaDTO implements Serializable {

    private Long id;

    private String localTeste;

    private ZonedDateTime dataTeste;

    @DecimalMin(value = "0")
    private Double notaTeste;

    private Boolean isAdmitido;

    private UserDTO utilizador;

    private TurmaDTO turma;

    private DiscenteDTO discente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalTeste() {
        return localTeste;
    }

    public void setLocalTeste(String localTeste) {
        this.localTeste = localTeste;
    }

    public ZonedDateTime getDataTeste() {
        return dataTeste;
    }

    public void setDataTeste(ZonedDateTime dataTeste) {
        this.dataTeste = dataTeste;
    }

    public Double getNotaTeste() {
        return notaTeste;
    }

    public void setNotaTeste(Double notaTeste) {
        this.notaTeste = notaTeste;
    }

    public Boolean getIsAdmitido() {
        return isAdmitido;
    }

    public void setIsAdmitido(Boolean isAdmitido) {
        this.isAdmitido = isAdmitido;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public TurmaDTO getTurma() {
        return turma;
    }

    public void setTurma(TurmaDTO turma) {
        this.turma = turma;
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
        if (!(o instanceof ProcessoSelectivoMatriculaDTO)) {
            return false;
        }

        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO = (ProcessoSelectivoMatriculaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processoSelectivoMatriculaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoSelectivoMatriculaDTO{" +
            "id=" + getId() +
            ", localTeste='" + getLocalTeste() + "'" +
            ", dataTeste='" + getDataTeste() + "'" +
            ", notaTeste=" + getNotaTeste() +
            ", isAdmitido='" + getIsAdmitido() + "'" +
            ", utilizador=" + getUtilizador() +
            ", turma=" + getTurma() +
            ", discente=" + getDiscente() +
            "}";
    }
}
