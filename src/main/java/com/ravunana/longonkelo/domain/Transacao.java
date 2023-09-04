package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.EstadoPagamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transacao.
 */
@Entity
@Table(name = "transacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Transacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "montante", precision = 21, scale = 2, nullable = false)
    private BigDecimal montante;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull
    @Column(name = "referencia", nullable = false, unique = true)
    private String referencia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPagamento estado;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "saldo", precision = 21, scale = 2, nullable = false)
    private BigDecimal saldo;

    @Lob
    @Column(name = "anexo")
    private byte[] anexo;

    @Column(name = "anexo_content_type")
    private String anexoContentType;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @OneToMany(mappedBy = "transacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "aplicacoesRecibos", "anoLectivos", "utilizador", "matricula", "documentoComercial", "transacao" },
        allowSetters = true
    )
    private Set<Recibo> recibos = new HashSet<>();

    @ManyToOne
    private User utilizador;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem moeda;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "matriculas",
            "facturas",
            "transacoes",
            "recibos",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "transferenciaTurmas",
            "ocorrencias",
            "utilizador",
            "categoriasMatriculas",
            "turma",
            "responsavelFinanceiro",
            "discente",
            "referencia",
        },
        allowSetters = true
    )
    private Matricula matricula;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "transacaos" }, allowSetters = true)
    private MeioPagamento meioPagamento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "transacoes", "moeda" }, allowSetters = true)
    private Conta conta;

    @ManyToMany(mappedBy = "transacoes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "de", "para", "utilizador", "motivoTransferencia", "transacoes" }, allowSetters = true)
    private Set<TransferenciaSaldo> transferenciaSaldos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontante() {
        return this.montante;
    }

    public Transacao montante(BigDecimal montante) {
        this.setMontante(montante);
        return this;
    }

    public void setMontante(BigDecimal montante) {
        this.montante = montante;
    }

    public LocalDate getData() {
        return this.data;
    }

    public Transacao data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getReferencia() {
        return this.referencia;
    }

    public Transacao referencia(String referencia) {
        this.setReferencia(referencia);
        return this;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public EstadoPagamento getEstado() {
        return this.estado;
    }

    public Transacao estado(EstadoPagamento estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado;
    }

    public BigDecimal getSaldo() {
        return this.saldo;
    }

    public Transacao saldo(BigDecimal saldo) {
        this.setSaldo(saldo);
        return this;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public byte[] getAnexo() {
        return this.anexo;
    }

    public Transacao anexo(byte[] anexo) {
        this.setAnexo(anexo);
        return this;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public String getAnexoContentType() {
        return this.anexoContentType;
    }

    public Transacao anexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
        return this;
    }

    public void setAnexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public Transacao timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Set<Recibo> getRecibos() {
        return this.recibos;
    }

    public void setRecibos(Set<Recibo> recibos) {
        if (this.recibos != null) {
            this.recibos.forEach(i -> i.setTransacao(null));
        }
        if (recibos != null) {
            recibos.forEach(i -> i.setTransacao(this));
        }
        this.recibos = recibos;
    }

    public Transacao recibos(Set<Recibo> recibos) {
        this.setRecibos(recibos);
        return this;
    }

    public Transacao addRecibos(Recibo recibo) {
        this.recibos.add(recibo);
        recibo.setTransacao(this);
        return this;
    }

    public Transacao removeRecibos(Recibo recibo) {
        this.recibos.remove(recibo);
        recibo.setTransacao(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Transacao utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public LookupItem getMoeda() {
        return this.moeda;
    }

    public void setMoeda(LookupItem lookupItem) {
        this.moeda = lookupItem;
    }

    public Transacao moeda(LookupItem lookupItem) {
        this.setMoeda(lookupItem);
        return this;
    }

    public Matricula getMatricula() {
        return this.matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Transacao matricula(Matricula matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public MeioPagamento getMeioPagamento() {
        return this.meioPagamento;
    }

    public void setMeioPagamento(MeioPagamento meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public Transacao meioPagamento(MeioPagamento meioPagamento) {
        this.setMeioPagamento(meioPagamento);
        return this;
    }

    public Conta getConta() {
        return this.conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Transacao conta(Conta conta) {
        this.setConta(conta);
        return this;
    }

    public Set<TransferenciaSaldo> getTransferenciaSaldos() {
        return this.transferenciaSaldos;
    }

    public void setTransferenciaSaldos(Set<TransferenciaSaldo> transferenciaSaldos) {
        if (this.transferenciaSaldos != null) {
            this.transferenciaSaldos.forEach(i -> i.removeTransacoes(this));
        }
        if (transferenciaSaldos != null) {
            transferenciaSaldos.forEach(i -> i.addTransacoes(this));
        }
        this.transferenciaSaldos = transferenciaSaldos;
    }

    public Transacao transferenciaSaldos(Set<TransferenciaSaldo> transferenciaSaldos) {
        this.setTransferenciaSaldos(transferenciaSaldos);
        return this;
    }

    public Transacao addTransferenciaSaldo(TransferenciaSaldo transferenciaSaldo) {
        this.transferenciaSaldos.add(transferenciaSaldo);
        transferenciaSaldo.getTransacoes().add(this);
        return this;
    }

    public Transacao removeTransferenciaSaldo(TransferenciaSaldo transferenciaSaldo) {
        this.transferenciaSaldos.remove(transferenciaSaldo);
        transferenciaSaldo.getTransacoes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transacao)) {
            return false;
        }
        return id != null && id.equals(((Transacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transacao{" +
            "id=" + getId() +
            ", montante=" + getMontante() +
            ", data='" + getData() + "'" +
            ", referencia='" + getReferencia() + "'" +
            ", estado='" + getEstado() + "'" +
            ", saldo=" + getSaldo() +
            ", anexo='" + getAnexo() + "'" +
            ", anexoContentType='" + getAnexoContentType() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
