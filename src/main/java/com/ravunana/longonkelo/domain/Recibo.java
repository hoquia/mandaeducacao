package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.EstadoDocumentoComercial;
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
import org.hibernate.annotations.Type;

/**
 * A Recibo.
 */
@Entity
@Table(name = "recibo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Recibo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "vencimento")
    private LocalDate vencimento;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_sem_imposto", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalSemImposto;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_com_imposto", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalComImposto;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_desconto_comercial", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalDescontoComercial;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_desconto_financeiro", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalDescontoFinanceiro;

    @NotNull
    @Column(name = "total_iva", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalIVA;

    @NotNull
    @Column(name = "total_retencao", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalRetencao;

    @NotNull
    @Column(name = "total_juro", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalJuro;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "cambio", precision = 21, scale = 2, nullable = false)
    private BigDecimal cambio;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_moeda_estrangeira", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalMoedaEstrangeira;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_pagar", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalPagar;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_pago", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalPago;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_falta", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalFalta;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_troco", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalTroco;

    @Column(name = "is_novo")
    private Boolean isNovo;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @DecimalMin(value = "0")
    @Column(name = "debito", precision = 21, scale = 2)
    private BigDecimal debito;

    @DecimalMin(value = "0")
    @Column(name = "credito", precision = 21, scale = 2)
    private BigDecimal credito;

    @Column(name = "is_fiscalizado")
    private Boolean isFiscalizado;

    @Column(name = "sign_text")
    private String signText;

    @Size(max = 172)
    @Column(name = "hash", length = 172)
    private String hash;

    @Column(name = "hash_short")
    private String hashShort;

    @Size(max = 70)
    @Column(name = "hash_control", length = 70)
    private String hashControl;

    @Column(name = "key_version")
    private Integer keyVersion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoDocumentoComercial estado;

    @NotNull
    @Column(name = "origem", nullable = false)
    private String origem;

    @OneToMany(mappedBy = "recibo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "itemFactura", "factura", "recibo" }, allowSetters = true)
    private Set<AplicacaoRecibo> aplicacoesRecibos = new HashSet<>();

    @OneToMany(mappedBy = "recibo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "directorGeral",
            "subDirectorPdagogico",
            "subDirectorAdministrativo",
            "responsavelSecretariaGeral",
            "responsavelSecretariaPedagogico",
            "utilizador",
            "nivesEnsinos",
            "turma",
            "horario",
            "planoAula",
            "licao",
            "processoSelectivoMatricula",
            "ocorrencia",
            "notasPeriodicaDisciplina",
            "notasGeralDisciplina",
            "dissertacaoFinalCurso",
            "factura",
            "recibo",
            "responsavelTurno",
            "responsavelAreaFormacao",
            "responsavelCurso",
            "responsavelDisciplina",
            "responsavelTurma",
        },
        allowSetters = true
    )
    private Set<AnoLectivo> anoLectivos = new HashSet<>();

    @ManyToOne
    private User utilizador;

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
    @JsonIgnoreProperties(value = { "serieDocumentos", "facturas", "recibos", "transformaEm" }, allowSetters = true)
    private DocumentoComercial documentoComercial;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "recibos", "utilizador", "moeda", "matricula", "meioPagamento", "conta", "transferenciaSaldos" },
        allowSetters = true
    )
    private Transacao transacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Recibo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public Recibo data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDate getVencimento() {
        return this.vencimento;
    }

    public Recibo vencimento(LocalDate vencimento) {
        this.setVencimento(vencimento);
        return this;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public String getNumero() {
        return this.numero;
    }

    public Recibo numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getTotalSemImposto() {
        return this.totalSemImposto;
    }

    public Recibo totalSemImposto(BigDecimal totalSemImposto) {
        this.setTotalSemImposto(totalSemImposto);
        return this;
    }

    public void setTotalSemImposto(BigDecimal totalSemImposto) {
        this.totalSemImposto = totalSemImposto;
    }

    public BigDecimal getTotalComImposto() {
        return this.totalComImposto;
    }

    public Recibo totalComImposto(BigDecimal totalComImposto) {
        this.setTotalComImposto(totalComImposto);
        return this;
    }

    public void setTotalComImposto(BigDecimal totalComImposto) {
        this.totalComImposto = totalComImposto;
    }

    public BigDecimal getTotalDescontoComercial() {
        return this.totalDescontoComercial;
    }

    public Recibo totalDescontoComercial(BigDecimal totalDescontoComercial) {
        this.setTotalDescontoComercial(totalDescontoComercial);
        return this;
    }

    public void setTotalDescontoComercial(BigDecimal totalDescontoComercial) {
        this.totalDescontoComercial = totalDescontoComercial;
    }

    public BigDecimal getTotalDescontoFinanceiro() {
        return this.totalDescontoFinanceiro;
    }

    public Recibo totalDescontoFinanceiro(BigDecimal totalDescontoFinanceiro) {
        this.setTotalDescontoFinanceiro(totalDescontoFinanceiro);
        return this;
    }

    public void setTotalDescontoFinanceiro(BigDecimal totalDescontoFinanceiro) {
        this.totalDescontoFinanceiro = totalDescontoFinanceiro;
    }

    public BigDecimal getTotalIVA() {
        return this.totalIVA;
    }

    public Recibo totalIVA(BigDecimal totalIVA) {
        this.setTotalIVA(totalIVA);
        return this;
    }

    public void setTotalIVA(BigDecimal totalIVA) {
        this.totalIVA = totalIVA;
    }

    public BigDecimal getTotalRetencao() {
        return this.totalRetencao;
    }

    public Recibo totalRetencao(BigDecimal totalRetencao) {
        this.setTotalRetencao(totalRetencao);
        return this;
    }

    public void setTotalRetencao(BigDecimal totalRetencao) {
        this.totalRetencao = totalRetencao;
    }

    public BigDecimal getTotalJuro() {
        return this.totalJuro;
    }

    public Recibo totalJuro(BigDecimal totalJuro) {
        this.setTotalJuro(totalJuro);
        return this;
    }

    public void setTotalJuro(BigDecimal totalJuro) {
        this.totalJuro = totalJuro;
    }

    public BigDecimal getCambio() {
        return this.cambio;
    }

    public Recibo cambio(BigDecimal cambio) {
        this.setCambio(cambio);
        return this;
    }

    public void setCambio(BigDecimal cambio) {
        this.cambio = cambio;
    }

    public BigDecimal getTotalMoedaEstrangeira() {
        return this.totalMoedaEstrangeira;
    }

    public Recibo totalMoedaEstrangeira(BigDecimal totalMoedaEstrangeira) {
        this.setTotalMoedaEstrangeira(totalMoedaEstrangeira);
        return this;
    }

    public void setTotalMoedaEstrangeira(BigDecimal totalMoedaEstrangeira) {
        this.totalMoedaEstrangeira = totalMoedaEstrangeira;
    }

    public BigDecimal getTotalPagar() {
        return this.totalPagar;
    }

    public Recibo totalPagar(BigDecimal totalPagar) {
        this.setTotalPagar(totalPagar);
        return this;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public BigDecimal getTotalPago() {
        return this.totalPago;
    }

    public Recibo totalPago(BigDecimal totalPago) {
        this.setTotalPago(totalPago);
        return this;
    }

    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
    }

    public BigDecimal getTotalFalta() {
        return this.totalFalta;
    }

    public Recibo totalFalta(BigDecimal totalFalta) {
        this.setTotalFalta(totalFalta);
        return this;
    }

    public void setTotalFalta(BigDecimal totalFalta) {
        this.totalFalta = totalFalta;
    }

    public BigDecimal getTotalTroco() {
        return this.totalTroco;
    }

    public Recibo totalTroco(BigDecimal totalTroco) {
        this.setTotalTroco(totalTroco);
        return this;
    }

    public void setTotalTroco(BigDecimal totalTroco) {
        this.totalTroco = totalTroco;
    }

    public Boolean getIsNovo() {
        return this.isNovo;
    }

    public Recibo isNovo(Boolean isNovo) {
        this.setIsNovo(isNovo);
        return this;
    }

    public void setIsNovo(Boolean isNovo) {
        this.isNovo = isNovo;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public Recibo timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Recibo descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getDebito() {
        return this.debito;
    }

    public Recibo debito(BigDecimal debito) {
        this.setDebito(debito);
        return this;
    }

    public void setDebito(BigDecimal debito) {
        this.debito = debito;
    }

    public BigDecimal getCredito() {
        return this.credito;
    }

    public Recibo credito(BigDecimal credito) {
        this.setCredito(credito);
        return this;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
    }

    public Boolean getIsFiscalizado() {
        return this.isFiscalizado;
    }

    public Recibo isFiscalizado(Boolean isFiscalizado) {
        this.setIsFiscalizado(isFiscalizado);
        return this;
    }

    public void setIsFiscalizado(Boolean isFiscalizado) {
        this.isFiscalizado = isFiscalizado;
    }

    public String getSignText() {
        return this.signText;
    }

    public Recibo signText(String signText) {
        this.setSignText(signText);
        return this;
    }

    public void setSignText(String signText) {
        this.signText = signText;
    }

    public String getHash() {
        return this.hash;
    }

    public Recibo hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHashShort() {
        return this.hashShort;
    }

    public Recibo hashShort(String hashShort) {
        this.setHashShort(hashShort);
        return this;
    }

    public void setHashShort(String hashShort) {
        this.hashShort = hashShort;
    }

    public String getHashControl() {
        return this.hashControl;
    }

    public Recibo hashControl(String hashControl) {
        this.setHashControl(hashControl);
        return this;
    }

    public void setHashControl(String hashControl) {
        this.hashControl = hashControl;
    }

    public Integer getKeyVersion() {
        return this.keyVersion;
    }

    public Recibo keyVersion(Integer keyVersion) {
        this.setKeyVersion(keyVersion);
        return this;
    }

    public void setKeyVersion(Integer keyVersion) {
        this.keyVersion = keyVersion;
    }

    public EstadoDocumentoComercial getEstado() {
        return this.estado;
    }

    public Recibo estado(EstadoDocumentoComercial estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoDocumentoComercial estado) {
        this.estado = estado;
    }

    public String getOrigem() {
        return this.origem;
    }

    public Recibo origem(String origem) {
        this.setOrigem(origem);
        return this;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public Set<AplicacaoRecibo> getAplicacoesRecibos() {
        return this.aplicacoesRecibos;
    }

    public void setAplicacoesRecibos(Set<AplicacaoRecibo> aplicacaoRecibos) {
        if (this.aplicacoesRecibos != null) {
            this.aplicacoesRecibos.forEach(i -> i.setRecibo(null));
        }
        if (aplicacaoRecibos != null) {
            aplicacaoRecibos.forEach(i -> i.setRecibo(this));
        }
        this.aplicacoesRecibos = aplicacaoRecibos;
    }

    public Recibo aplicacoesRecibos(Set<AplicacaoRecibo> aplicacaoRecibos) {
        this.setAplicacoesRecibos(aplicacaoRecibos);
        return this;
    }

    public Recibo addAplicacoesRecibo(AplicacaoRecibo aplicacaoRecibo) {
        this.aplicacoesRecibos.add(aplicacaoRecibo);
        aplicacaoRecibo.setRecibo(this);
        return this;
    }

    public Recibo removeAplicacoesRecibo(AplicacaoRecibo aplicacaoRecibo) {
        this.aplicacoesRecibos.remove(aplicacaoRecibo);
        aplicacaoRecibo.setRecibo(null);
        return this;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setRecibo(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setRecibo(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public Recibo anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public Recibo addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setRecibo(this);
        return this;
    }

    public Recibo removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setRecibo(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Recibo utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Matricula getMatricula() {
        return this.matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Recibo matricula(Matricula matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public DocumentoComercial getDocumentoComercial() {
        return this.documentoComercial;
    }

    public void setDocumentoComercial(DocumentoComercial documentoComercial) {
        this.documentoComercial = documentoComercial;
    }

    public Recibo documentoComercial(DocumentoComercial documentoComercial) {
        this.setDocumentoComercial(documentoComercial);
        return this;
    }

    public Transacao getTransacao() {
        return this.transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }

    public Recibo transacao(Transacao transacao) {
        this.setTransacao(transacao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recibo)) {
            return false;
        }
        return id != null && id.equals(((Recibo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recibo{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", vencimento='" + getVencimento() + "'" +
            ", numero='" + getNumero() + "'" +
            ", totalSemImposto=" + getTotalSemImposto() +
            ", totalComImposto=" + getTotalComImposto() +
            ", totalDescontoComercial=" + getTotalDescontoComercial() +
            ", totalDescontoFinanceiro=" + getTotalDescontoFinanceiro() +
            ", totalIVA=" + getTotalIVA() +
            ", totalRetencao=" + getTotalRetencao() +
            ", totalJuro=" + getTotalJuro() +
            ", cambio=" + getCambio() +
            ", totalMoedaEstrangeira=" + getTotalMoedaEstrangeira() +
            ", totalPagar=" + getTotalPagar() +
            ", totalPago=" + getTotalPago() +
            ", totalFalta=" + getTotalFalta() +
            ", totalTroco=" + getTotalTroco() +
            ", isNovo='" + getIsNovo() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", debito=" + getDebito() +
            ", credito=" + getCredito() +
            ", isFiscalizado='" + getIsFiscalizado() + "'" +
            ", signText='" + getSignText() + "'" +
            ", hash='" + getHash() + "'" +
            ", hashShort='" + getHashShort() + "'" +
            ", hashControl='" + getHashControl() + "'" +
            ", keyVersion=" + getKeyVersion() +
            ", estado='" + getEstado() + "'" +
            ", origem='" + getOrigem() + "'" +
            "}";
    }
}
