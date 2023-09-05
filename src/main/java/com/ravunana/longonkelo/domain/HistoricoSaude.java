package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A HistoricoSaude.
 */
@Entity
@Table(name = "historico_saude")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HistoricoSaude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "inicio", nullable = false)
    private ZonedDateTime inicio;

    @Column(name = "fim")
    private ZonedDateTime fim;

    @Column(name = "situacao_prescricao")
    private String situacaoPrescricao;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @Column(name = "hash")
    private String hash;

    @ManyToOne
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "enderecos",
            "processosSelectivos",
            "anexoDiscentes",
            "matriculas",
            "resumoAcademicos",
            "historicosSaudes",
            "dissertacaoFinalCursos",
            "nacionalidade",
            "naturalidade",
            "tipoDocumento",
            "profissao",
            "grupoSanguinio",
            "necessidadeEspecial",
            "encarregadoEducacao",
        },
        allowSetters = true
    )
    private Discente discente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HistoricoSaude id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public HistoricoSaude nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public HistoricoSaude descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getInicio() {
        return this.inicio;
    }

    public HistoricoSaude inicio(ZonedDateTime inicio) {
        this.setInicio(inicio);
        return this;
    }

    public void setInicio(ZonedDateTime inicio) {
        this.inicio = inicio;
    }

    public ZonedDateTime getFim() {
        return this.fim;
    }

    public HistoricoSaude fim(ZonedDateTime fim) {
        this.setFim(fim);
        return this;
    }

    public void setFim(ZonedDateTime fim) {
        this.fim = fim;
    }

    public String getSituacaoPrescricao() {
        return this.situacaoPrescricao;
    }

    public HistoricoSaude situacaoPrescricao(String situacaoPrescricao) {
        this.setSituacaoPrescricao(situacaoPrescricao);
        return this;
    }

    public void setSituacaoPrescricao(String situacaoPrescricao) {
        this.situacaoPrescricao = situacaoPrescricao;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public HistoricoSaude timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getHash() {
        return this.hash;
    }

    public HistoricoSaude hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public HistoricoSaude utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Discente getDiscente() {
        return this.discente;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }

    public HistoricoSaude discente(Discente discente) {
        this.setDiscente(discente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoricoSaude)) {
            return false;
        }
        return id != null && id.equals(((HistoricoSaude) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistoricoSaude{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            ", situacaoPrescricao='" + getSituacaoPrescricao() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", hash='" + getHash() + "'" +
            "}";
    }
}
