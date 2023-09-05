package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A TransferenciaSaldo.
 */
@Entity
@Table(name = "transferencia_saldo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransferenciaSaldo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @DecimalMin(value = "0")
    @Column(name = "montante", precision = 21, scale = 2)
    private BigDecimal montante;

    @Column(name = "is_mesma_conta")
    private Boolean isMesmaConta;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

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
    private Discente de;

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
    private Discente para;

    @ManyToOne
    private User utilizador;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem motivoTransferencia;

    @ManyToMany
    @JoinTable(
        name = "rel_transferencia_saldo__transacoes",
        joinColumns = @JoinColumn(name = "transferencia_saldo_id"),
        inverseJoinColumns = @JoinColumn(name = "transacoes_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "recibos", "utilizador", "moeda", "matricula", "meioPagamento", "conta", "transferenciaSaldos" },
        allowSetters = true
    )
    private Set<Transacao> transacoes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransferenciaSaldo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontante() {
        return this.montante;
    }

    public TransferenciaSaldo montante(BigDecimal montante) {
        this.setMontante(montante);
        return this;
    }

    public void setMontante(BigDecimal montante) {
        this.montante = montante;
    }

    public Boolean getIsMesmaConta() {
        return this.isMesmaConta;
    }

    public TransferenciaSaldo isMesmaConta(Boolean isMesmaConta) {
        this.setIsMesmaConta(isMesmaConta);
        return this;
    }

    public void setIsMesmaConta(Boolean isMesmaConta) {
        this.isMesmaConta = isMesmaConta;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public TransferenciaSaldo descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public TransferenciaSaldo timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Discente getDe() {
        return this.de;
    }

    public void setDe(Discente discente) {
        this.de = discente;
    }

    public TransferenciaSaldo de(Discente discente) {
        this.setDe(discente);
        return this;
    }

    public Discente getPara() {
        return this.para;
    }

    public void setPara(Discente discente) {
        this.para = discente;
    }

    public TransferenciaSaldo para(Discente discente) {
        this.setPara(discente);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public TransferenciaSaldo utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public LookupItem getMotivoTransferencia() {
        return this.motivoTransferencia;
    }

    public void setMotivoTransferencia(LookupItem lookupItem) {
        this.motivoTransferencia = lookupItem;
    }

    public TransferenciaSaldo motivoTransferencia(LookupItem lookupItem) {
        this.setMotivoTransferencia(lookupItem);
        return this;
    }

    public Set<Transacao> getTransacoes() {
        return this.transacoes;
    }

    public void setTransacoes(Set<Transacao> transacaos) {
        this.transacoes = transacaos;
    }

    public TransferenciaSaldo transacoes(Set<Transacao> transacaos) {
        this.setTransacoes(transacaos);
        return this;
    }

    public TransferenciaSaldo addTransacoes(Transacao transacao) {
        this.transacoes.add(transacao);
        transacao.getTransferenciaSaldos().add(this);
        return this;
    }

    public TransferenciaSaldo removeTransacoes(Transacao transacao) {
        this.transacoes.remove(transacao);
        transacao.getTransferenciaSaldos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferenciaSaldo)) {
            return false;
        }
        return id != null && id.equals(((TransferenciaSaldo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferenciaSaldo{" +
            "id=" + getId() +
            ", montante=" + getMontante() +
            ", isMesmaConta='" + getIsMesmaConta() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
