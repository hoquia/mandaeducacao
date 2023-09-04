package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.ProvedorNotificacao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProvedorNotificacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String telefone;

    @NotNull
    private String email;

    @NotNull
    private String link;

    @NotNull
    private String token;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String hash;

    private Boolean isPadrao;

    private InstituicaoEnsinoDTO instituicao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Boolean getIsPadrao() {
        return isPadrao;
    }

    public void setIsPadrao(Boolean isPadrao) {
        this.isPadrao = isPadrao;
    }

    public InstituicaoEnsinoDTO getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoEnsinoDTO instituicao) {
        this.instituicao = instituicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvedorNotificacaoDTO)) {
            return false;
        }

        ProvedorNotificacaoDTO provedorNotificacaoDTO = (ProvedorNotificacaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, provedorNotificacaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProvedorNotificacaoDTO{" +
            "id=" + getId() +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", link='" + getLink() + "'" +
            ", token='" + getToken() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", hash='" + getHash() + "'" +
            ", isPadrao='" + getIsPadrao() + "'" +
            ", instituicao=" + getInstituicao() +
            "}";
    }
}
