package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.LongonkeloHistorico} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LongonkeloHistoricoDTO implements Serializable {

    private Long id;

    @NotNull
    private String operacao;

    @NotNull
    private String entidadeNome;

    @NotNull
    private String entidadeCodigo;

    @Lob
    private String payload;

    @NotNull
    private String host;

    private String hash;

    @NotNull
    private ZonedDateTime timestamp;

    private UserDTO utilizador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getEntidadeNome() {
        return entidadeNome;
    }

    public void setEntidadeNome(String entidadeNome) {
        this.entidadeNome = entidadeNome;
    }

    public String getEntidadeCodigo() {
        return entidadeCodigo;
    }

    public void setEntidadeCodigo(String entidadeCodigo) {
        this.entidadeCodigo = entidadeCodigo;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LongonkeloHistoricoDTO)) {
            return false;
        }

        LongonkeloHistoricoDTO longonkeloHistoricoDTO = (LongonkeloHistoricoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, longonkeloHistoricoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LongonkeloHistoricoDTO{" +
            "id=" + getId() +
            ", operacao='" + getOperacao() + "'" +
            ", entidadeNome='" + getEntidadeNome() + "'" +
            ", entidadeCodigo='" + getEntidadeCodigo() + "'" +
            ", payload='" + getPayload() + "'" +
            ", host='" + getHost() + "'" +
            ", hash='" + getHash() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", utilizador=" + getUtilizador() +
            "}";
    }
}
