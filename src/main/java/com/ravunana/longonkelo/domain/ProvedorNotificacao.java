package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProvedorNotificacao.
 */
@Entity
@Table(name = "provedor_notificacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProvedorNotificacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "link", nullable = false)
    private String link;

    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "is_padrao")
    private Boolean isPadrao;

    @ManyToOne(optional = false)
    @NotNull
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
    private InstituicaoEnsino instituicao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProvedorNotificacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public ProvedorNotificacao telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public ProvedorNotificacao email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return this.link;
    }

    public ProvedorNotificacao link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getToken() {
        return this.token;
    }

    public ProvedorNotificacao token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return this.username;
    }

    public ProvedorNotificacao username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public ProvedorNotificacao password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHash() {
        return this.hash;
    }

    public ProvedorNotificacao hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Boolean getIsPadrao() {
        return this.isPadrao;
    }

    public ProvedorNotificacao isPadrao(Boolean isPadrao) {
        this.setIsPadrao(isPadrao);
        return this;
    }

    public void setIsPadrao(Boolean isPadrao) {
        this.isPadrao = isPadrao;
    }

    public InstituicaoEnsino getInstituicao() {
        return this.instituicao;
    }

    public void setInstituicao(InstituicaoEnsino instituicaoEnsino) {
        this.instituicao = instituicaoEnsino;
    }

    public ProvedorNotificacao instituicao(InstituicaoEnsino instituicaoEnsino) {
        this.setInstituicao(instituicaoEnsino);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvedorNotificacao)) {
            return false;
        }
        return id != null && id.equals(((ProvedorNotificacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProvedorNotificacao{" +
            "id=" + getId() +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            ", link='" + getLink() + "'" +
            ", token='" + getToken() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", hash='" + getHash() + "'" +
            ", isPadrao='" + getIsPadrao() + "'" +
            "}";
    }
}
