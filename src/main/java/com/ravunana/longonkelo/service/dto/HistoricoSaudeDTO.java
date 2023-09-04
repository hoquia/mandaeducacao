package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.HistoricoSaude} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HistoricoSaudeDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @Lob
    private String descricao;

    @NotNull
    private ZonedDateTime inicio;

    private ZonedDateTime fim;

    private String situacaoPrescricao;

    @NotNull
    private ZonedDateTime timestamp;

    private String hash;

    private UserDTO utilizador;

    private DiscenteDTO discente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getInicio() {
        return inicio;
    }

    public void setInicio(ZonedDateTime inicio) {
        this.inicio = inicio;
    }

    public ZonedDateTime getFim() {
        return fim;
    }

    public void setFim(ZonedDateTime fim) {
        this.fim = fim;
    }

    public String getSituacaoPrescricao() {
        return situacaoPrescricao;
    }

    public void setSituacaoPrescricao(String situacaoPrescricao) {
        this.situacaoPrescricao = situacaoPrescricao;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
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
        if (!(o instanceof HistoricoSaudeDTO)) {
            return false;
        }

        HistoricoSaudeDTO historicoSaudeDTO = (HistoricoSaudeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, historicoSaudeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistoricoSaudeDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            ", situacaoPrescricao='" + getSituacaoPrescricao() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", hash='" + getHash() + "'" +
            ", utilizador=" + getUtilizador() +
            ", discente=" + getDiscente() +
            "}";
    }
}
