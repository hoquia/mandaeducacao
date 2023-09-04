package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.TransferenciaTurma} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransferenciaTurmaDTO implements Serializable {

    private Long id;

    private ZonedDateTime timestamp;

    private TurmaDTO de;

    private TurmaDTO para;

    private UserDTO utilizador;

    private LookupItemDTO motivoTransferencia;

    private MatriculaDTO matricula;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TurmaDTO getDe() {
        return de;
    }

    public void setDe(TurmaDTO de) {
        this.de = de;
    }

    public TurmaDTO getPara() {
        return para;
    }

    public void setPara(TurmaDTO para) {
        this.para = para;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public LookupItemDTO getMotivoTransferencia() {
        return motivoTransferencia;
    }

    public void setMotivoTransferencia(LookupItemDTO motivoTransferencia) {
        this.motivoTransferencia = motivoTransferencia;
    }

    public MatriculaDTO getMatricula() {
        return matricula;
    }

    public void setMatricula(MatriculaDTO matricula) {
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferenciaTurmaDTO)) {
            return false;
        }

        TransferenciaTurmaDTO transferenciaTurmaDTO = (TransferenciaTurmaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transferenciaTurmaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferenciaTurmaDTO{" +
            "id=" + getId() +
            ", timestamp='" + getTimestamp() + "'" +
            ", de=" + getDe() +
            ", para=" + getPara() +
            ", utilizador=" + getUtilizador() +
            ", motivoTransferencia=" + getMotivoTransferencia() +
            ", matricula=" + getMatricula() +
            "}";
    }
}
