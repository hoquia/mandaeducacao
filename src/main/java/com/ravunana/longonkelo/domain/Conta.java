package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.TipoConta;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Conta.
 */
@Entity
@Table(name = "conta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoConta tipo;

    @NotNull
    @Column(name = "titulo", nullable = false, unique = true)
    private String titulo;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Column(name = "iban", unique = true)
    private String iban;

    @NotNull
    @Column(name = "titular", nullable = false)
    private String titular;

    @Column(name = "is_padrao")
    private Boolean isPadrao;

    @OneToMany(mappedBy = "conta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "recibos", "utilizador", "moeda", "matricula", "meioPagamento", "conta", "transferenciaSaldos" },
        allowSetters = true
    )
    private Set<Transacao> transacoes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem moeda;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Conta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return this.imagem;
    }

    public Conta imagem(byte[] imagem) {
        this.setImagem(imagem);
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return this.imagemContentType;
    }

    public Conta imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public TipoConta getTipo() {
        return this.tipo;
    }

    public Conta tipo(TipoConta tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Conta titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNumero() {
        return this.numero;
    }

    public Conta numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIban() {
        return this.iban;
    }

    public Conta iban(String iban) {
        this.setIban(iban);
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getTitular() {
        return this.titular;
    }

    public Conta titular(String titular) {
        this.setTitular(titular);
        return this;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Boolean getIsPadrao() {
        return this.isPadrao;
    }

    public Conta isPadrao(Boolean isPadrao) {
        this.setIsPadrao(isPadrao);
        return this;
    }

    public void setIsPadrao(Boolean isPadrao) {
        this.isPadrao = isPadrao;
    }

    public Set<Transacao> getTransacoes() {
        return this.transacoes;
    }

    public void setTransacoes(Set<Transacao> transacaos) {
        if (this.transacoes != null) {
            this.transacoes.forEach(i -> i.setConta(null));
        }
        if (transacaos != null) {
            transacaos.forEach(i -> i.setConta(this));
        }
        this.transacoes = transacaos;
    }

    public Conta transacoes(Set<Transacao> transacaos) {
        this.setTransacoes(transacaos);
        return this;
    }

    public Conta addTransacoes(Transacao transacao) {
        this.transacoes.add(transacao);
        transacao.setConta(this);
        return this;
    }

    public Conta removeTransacoes(Transacao transacao) {
        this.transacoes.remove(transacao);
        transacao.setConta(null);
        return this;
    }

    public LookupItem getMoeda() {
        return this.moeda;
    }

    public void setMoeda(LookupItem lookupItem) {
        this.moeda = lookupItem;
    }

    public Conta moeda(LookupItem lookupItem) {
        this.setMoeda(lookupItem);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conta)) {
            return false;
        }
        return id != null && id.equals(((Conta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Conta{" +
            "id=" + getId() +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", numero='" + getNumero() + "'" +
            ", iban='" + getIban() + "'" +
            ", titular='" + getTitular() + "'" +
            ", isPadrao='" + getIsPadrao() + "'" +
            "}";
    }
}
