package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MeioPagamento.
 */
@Entity
@Table(name = "meio_pagamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MeioPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Min(value = 0)
    @Column(name = "numero_digito_referencia")
    private Integer numeroDigitoReferencia;

    @Column(name = "is_pagamento_instantanio")
    private Boolean isPagamentoInstantanio;

    @Column(name = "hash")
    private String hash;

    @Column(name = "link")
    private String link;

    @Column(name = "token")
    private String token;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "formato_referencia")
    private String formatoReferencia;

    @OneToMany(mappedBy = "meioPagamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "recibos", "utilizador", "moeda", "matricula", "meioPagamento", "conta", "transferenciaSaldos" },
        allowSetters = true
    )
    private Set<Transacao> transacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MeioPagamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return this.imagem;
    }

    public MeioPagamento imagem(byte[] imagem) {
        this.setImagem(imagem);
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return this.imagemContentType;
    }

    public MeioPagamento imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public MeioPagamento codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public MeioPagamento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumeroDigitoReferencia() {
        return this.numeroDigitoReferencia;
    }

    public MeioPagamento numeroDigitoReferencia(Integer numeroDigitoReferencia) {
        this.setNumeroDigitoReferencia(numeroDigitoReferencia);
        return this;
    }

    public void setNumeroDigitoReferencia(Integer numeroDigitoReferencia) {
        this.numeroDigitoReferencia = numeroDigitoReferencia;
    }

    public Boolean getIsPagamentoInstantanio() {
        return this.isPagamentoInstantanio;
    }

    public MeioPagamento isPagamentoInstantanio(Boolean isPagamentoInstantanio) {
        this.setIsPagamentoInstantanio(isPagamentoInstantanio);
        return this;
    }

    public void setIsPagamentoInstantanio(Boolean isPagamentoInstantanio) {
        this.isPagamentoInstantanio = isPagamentoInstantanio;
    }

    public String getHash() {
        return this.hash;
    }

    public MeioPagamento hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getLink() {
        return this.link;
    }

    public MeioPagamento link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getToken() {
        return this.token;
    }

    public MeioPagamento token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return this.username;
    }

    public MeioPagamento username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public MeioPagamento password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFormatoReferencia() {
        return this.formatoReferencia;
    }

    public MeioPagamento formatoReferencia(String formatoReferencia) {
        this.setFormatoReferencia(formatoReferencia);
        return this;
    }

    public void setFormatoReferencia(String formatoReferencia) {
        this.formatoReferencia = formatoReferencia;
    }

    public Set<Transacao> getTransacaos() {
        return this.transacaos;
    }

    public void setTransacaos(Set<Transacao> transacaos) {
        if (this.transacaos != null) {
            this.transacaos.forEach(i -> i.setMeioPagamento(null));
        }
        if (transacaos != null) {
            transacaos.forEach(i -> i.setMeioPagamento(this));
        }
        this.transacaos = transacaos;
    }

    public MeioPagamento transacaos(Set<Transacao> transacaos) {
        this.setTransacaos(transacaos);
        return this;
    }

    public MeioPagamento addTransacao(Transacao transacao) {
        this.transacaos.add(transacao);
        transacao.setMeioPagamento(this);
        return this;
    }

    public MeioPagamento removeTransacao(Transacao transacao) {
        this.transacaos.remove(transacao);
        transacao.setMeioPagamento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeioPagamento)) {
            return false;
        }
        return id != null && id.equals(((MeioPagamento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeioPagamento{" +
            "id=" + getId() +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", numeroDigitoReferencia=" + getNumeroDigitoReferencia() +
            ", isPagamentoInstantanio='" + getIsPagamentoInstantanio() + "'" +
            ", hash='" + getHash() + "'" +
            ", link='" + getLink() + "'" +
            ", token='" + getToken() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", formatoReferencia='" + getFormatoReferencia() + "'" +
            "}";
    }
}
