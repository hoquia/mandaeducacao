package com.ravunana.longonkelo.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A LongonkeloHistorico.
 */
@Entity
@Table(name = "longonkelo_historico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LongonkeloHistorico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "operacao", nullable = false)
    private String operacao;

    @NotNull
    @Column(name = "entidade_nome", nullable = false)
    private String entidadeNome;

    @NotNull
    @Column(name = "entidade_codigo", nullable = false)
    private String entidadeCodigo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "payload", nullable = false)
    private String payload;

    @NotNull
    @Column(name = "host", nullable = false)
    private String host;

    @Column(name = "hash")
    private String hash;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @ManyToOne
    private User utilizador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LongonkeloHistorico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperacao() {
        return this.operacao;
    }

    public LongonkeloHistorico operacao(String operacao) {
        this.setOperacao(operacao);
        return this;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getEntidadeNome() {
        return this.entidadeNome;
    }

    public LongonkeloHistorico entidadeNome(String entidadeNome) {
        this.setEntidadeNome(entidadeNome);
        return this;
    }

    public void setEntidadeNome(String entidadeNome) {
        this.entidadeNome = entidadeNome;
    }

    public String getEntidadeCodigo() {
        return this.entidadeCodigo;
    }

    public LongonkeloHistorico entidadeCodigo(String entidadeCodigo) {
        this.setEntidadeCodigo(entidadeCodigo);
        return this;
    }

    public void setEntidadeCodigo(String entidadeCodigo) {
        this.entidadeCodigo = entidadeCodigo;
    }

    public String getPayload() {
        return this.payload;
    }

    public LongonkeloHistorico payload(String payload) {
        this.setPayload(payload);
        return this;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getHost() {
        return this.host;
    }

    public LongonkeloHistorico host(String host) {
        this.setHost(host);
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHash() {
        return this.hash;
    }

    public LongonkeloHistorico hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public LongonkeloHistorico timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public LongonkeloHistorico utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LongonkeloHistorico)) {
            return false;
        }
        return id != null && id.equals(((LongonkeloHistorico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LongonkeloHistorico{" +
            "id=" + getId() +
            ", operacao='" + getOperacao() + "'" +
            ", entidadeNome='" + getEntidadeNome() + "'" +
            ", entidadeCodigo='" + getEntidadeCodigo() + "'" +
            ", payload='" + getPayload() + "'" +
            ", host='" + getHost() + "'" +
            ", hash='" + getHash() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
