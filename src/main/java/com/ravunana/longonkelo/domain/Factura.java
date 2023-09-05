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
 * A Factura.
 */
@Entity
@Table(name = "factura")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "numero", length = 60, nullable = false, unique = true)
    private String numero;

    @Column(name = "codigo_entrega")
    private String codigoEntrega;

    @NotNull
    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @NotNull
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Size(max = 10)
    @Column(name = "cae", length = 10)
    private String cae;

    @NotNull
    @Column(name = "inicio_transporte", nullable = false)
    private ZonedDateTime inicioTransporte;

    @NotNull
    @Column(name = "fim_transporte", nullable = false)
    private ZonedDateTime fimTransporte;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao_geral")
    private String observacaoGeral;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao_interna")
    private String observacaoInterna;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoDocumentoComercial estado;

    @NotNull
    @Column(name = "origem", nullable = false)
    private String origem;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @Column(name = "is_moeda_entrangeira")
    private Boolean isMoedaEntrangeira;

    @NotNull
    @Size(max = 12)
    @Column(name = "moeda", length = 12, nullable = false)
    private String moeda;

    @DecimalMin(value = "0")
    @Column(name = "cambio", precision = 21, scale = 2)
    private BigDecimal cambio;

    @DecimalMin(value = "0")
    @Column(name = "total_moeda_entrangeira", precision = 21, scale = 2)
    private BigDecimal totalMoedaEntrangeira;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_iliquido", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalIliquido;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_desconto_comercial", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalDescontoComercial;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_liquido", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalLiquido;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_imposto_iva", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalImpostoIVA;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_imposto_especial_consumo", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalImpostoEspecialConsumo;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_desconto_financeiro", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalDescontoFinanceiro;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_factura", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalFactura;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_imposto_retencao_fonte", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalImpostoRetencaoFonte;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_pagar", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalPagar;

    @DecimalMin(value = "0")
    @Column(name = "debito", precision = 21, scale = 2)
    private BigDecimal debito;

    @DecimalMin(value = "0")
    @Column(name = "credito", precision = 21, scale = 2)
    private BigDecimal credito;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_pago", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalPago;

    @NotNull
    @Column(name = "total_diferenca", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalDiferenca;

    @Column(name = "is_auto_facturacao")
    private Boolean isAutoFacturacao;

    @Column(name = "is_regime_caixa")
    private Boolean isRegimeCaixa;

    @Column(name = "is_emitida_nome_e_conta_terceiro")
    private Boolean isEmitidaNomeEContaTerceiro;

    @Column(name = "is_novo")
    private Boolean isNovo;

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

    @OneToMany(mappedBy = "referencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "facturas",
            "itemsFacturas",
            "aplicacoesFacturas",
            "resumosImpostos",
            "anoLectivos",
            "utilizador",
            "motivoAnulacao",
            "matricula",
            "referencia",
            "documentoComercial",
        },
        allowSetters = true
    )
    private Set<Factura> facturas = new HashSet<>();

    @OneToMany(mappedBy = "factura")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "factura", "emolumento" }, allowSetters = true)
    private Set<ItemFactura> itemsFacturas = new HashSet<>();

    @OneToMany(mappedBy = "factura")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "itemFactura", "factura", "recibo" }, allowSetters = true)
    private Set<AplicacaoRecibo> aplicacoesFacturas = new HashSet<>();

    @OneToMany(mappedBy = "factura")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "factura" }, allowSetters = true)
    private Set<ResumoImpostoFactura> resumosImpostos = new HashSet<>();

    @OneToMany(mappedBy = "factura")
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem motivoAnulacao;

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

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "facturas",
            "itemsFacturas",
            "aplicacoesFacturas",
            "resumosImpostos",
            "anoLectivos",
            "utilizador",
            "motivoAnulacao",
            "matricula",
            "referencia",
            "documentoComercial",
        },
        allowSetters = true
    )
    private Factura referencia;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "serieDocumentos", "facturas", "recibos", "transformaEm" }, allowSetters = true)
    private DocumentoComercial documentoComercial;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Factura id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return this.numero;
    }

    public Factura numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoEntrega() {
        return this.codigoEntrega;
    }

    public Factura codigoEntrega(String codigoEntrega) {
        this.setCodigoEntrega(codigoEntrega);
        return this;
    }

    public void setCodigoEntrega(String codigoEntrega) {
        this.codigoEntrega = codigoEntrega;
    }

    public LocalDate getDataEmissao() {
        return this.dataEmissao;
    }

    public Factura dataEmissao(LocalDate dataEmissao) {
        this.setDataEmissao(dataEmissao);
        return this;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataVencimento() {
        return this.dataVencimento;
    }

    public Factura dataVencimento(LocalDate dataVencimento) {
        this.setDataVencimento(dataVencimento);
        return this;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getCae() {
        return this.cae;
    }

    public Factura cae(String cae) {
        this.setCae(cae);
        return this;
    }

    public void setCae(String cae) {
        this.cae = cae;
    }

    public ZonedDateTime getInicioTransporte() {
        return this.inicioTransporte;
    }

    public Factura inicioTransporte(ZonedDateTime inicioTransporte) {
        this.setInicioTransporte(inicioTransporte);
        return this;
    }

    public void setInicioTransporte(ZonedDateTime inicioTransporte) {
        this.inicioTransporte = inicioTransporte;
    }

    public ZonedDateTime getFimTransporte() {
        return this.fimTransporte;
    }

    public Factura fimTransporte(ZonedDateTime fimTransporte) {
        this.setFimTransporte(fimTransporte);
        return this;
    }

    public void setFimTransporte(ZonedDateTime fimTransporte) {
        this.fimTransporte = fimTransporte;
    }

    public String getObservacaoGeral() {
        return this.observacaoGeral;
    }

    public Factura observacaoGeral(String observacaoGeral) {
        this.setObservacaoGeral(observacaoGeral);
        return this;
    }

    public void setObservacaoGeral(String observacaoGeral) {
        this.observacaoGeral = observacaoGeral;
    }

    public String getObservacaoInterna() {
        return this.observacaoInterna;
    }

    public Factura observacaoInterna(String observacaoInterna) {
        this.setObservacaoInterna(observacaoInterna);
        return this;
    }

    public void setObservacaoInterna(String observacaoInterna) {
        this.observacaoInterna = observacaoInterna;
    }

    public EstadoDocumentoComercial getEstado() {
        return this.estado;
    }

    public Factura estado(EstadoDocumentoComercial estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoDocumentoComercial estado) {
        this.estado = estado;
    }

    public String getOrigem() {
        return this.origem;
    }

    public Factura origem(String origem) {
        this.setOrigem(origem);
        return this;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public Factura timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsMoedaEntrangeira() {
        return this.isMoedaEntrangeira;
    }

    public Factura isMoedaEntrangeira(Boolean isMoedaEntrangeira) {
        this.setIsMoedaEntrangeira(isMoedaEntrangeira);
        return this;
    }

    public void setIsMoedaEntrangeira(Boolean isMoedaEntrangeira) {
        this.isMoedaEntrangeira = isMoedaEntrangeira;
    }

    public String getMoeda() {
        return this.moeda;
    }

    public Factura moeda(String moeda) {
        this.setMoeda(moeda);
        return this;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public BigDecimal getCambio() {
        return this.cambio;
    }

    public Factura cambio(BigDecimal cambio) {
        this.setCambio(cambio);
        return this;
    }

    public void setCambio(BigDecimal cambio) {
        this.cambio = cambio;
    }

    public BigDecimal getTotalMoedaEntrangeira() {
        return this.totalMoedaEntrangeira;
    }

    public Factura totalMoedaEntrangeira(BigDecimal totalMoedaEntrangeira) {
        this.setTotalMoedaEntrangeira(totalMoedaEntrangeira);
        return this;
    }

    public void setTotalMoedaEntrangeira(BigDecimal totalMoedaEntrangeira) {
        this.totalMoedaEntrangeira = totalMoedaEntrangeira;
    }

    public BigDecimal getTotalIliquido() {
        return this.totalIliquido;
    }

    public Factura totalIliquido(BigDecimal totalIliquido) {
        this.setTotalIliquido(totalIliquido);
        return this;
    }

    public void setTotalIliquido(BigDecimal totalIliquido) {
        this.totalIliquido = totalIliquido;
    }

    public BigDecimal getTotalDescontoComercial() {
        return this.totalDescontoComercial;
    }

    public Factura totalDescontoComercial(BigDecimal totalDescontoComercial) {
        this.setTotalDescontoComercial(totalDescontoComercial);
        return this;
    }

    public void setTotalDescontoComercial(BigDecimal totalDescontoComercial) {
        this.totalDescontoComercial = totalDescontoComercial;
    }

    public BigDecimal getTotalLiquido() {
        return this.totalLiquido;
    }

    public Factura totalLiquido(BigDecimal totalLiquido) {
        this.setTotalLiquido(totalLiquido);
        return this;
    }

    public void setTotalLiquido(BigDecimal totalLiquido) {
        this.totalLiquido = totalLiquido;
    }

    public BigDecimal getTotalImpostoIVA() {
        return this.totalImpostoIVA;
    }

    public Factura totalImpostoIVA(BigDecimal totalImpostoIVA) {
        this.setTotalImpostoIVA(totalImpostoIVA);
        return this;
    }

    public void setTotalImpostoIVA(BigDecimal totalImpostoIVA) {
        this.totalImpostoIVA = totalImpostoIVA;
    }

    public BigDecimal getTotalImpostoEspecialConsumo() {
        return this.totalImpostoEspecialConsumo;
    }

    public Factura totalImpostoEspecialConsumo(BigDecimal totalImpostoEspecialConsumo) {
        this.setTotalImpostoEspecialConsumo(totalImpostoEspecialConsumo);
        return this;
    }

    public void setTotalImpostoEspecialConsumo(BigDecimal totalImpostoEspecialConsumo) {
        this.totalImpostoEspecialConsumo = totalImpostoEspecialConsumo;
    }

    public BigDecimal getTotalDescontoFinanceiro() {
        return this.totalDescontoFinanceiro;
    }

    public Factura totalDescontoFinanceiro(BigDecimal totalDescontoFinanceiro) {
        this.setTotalDescontoFinanceiro(totalDescontoFinanceiro);
        return this;
    }

    public void setTotalDescontoFinanceiro(BigDecimal totalDescontoFinanceiro) {
        this.totalDescontoFinanceiro = totalDescontoFinanceiro;
    }

    public BigDecimal getTotalFactura() {
        return this.totalFactura;
    }

    public Factura totalFactura(BigDecimal totalFactura) {
        this.setTotalFactura(totalFactura);
        return this;
    }

    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }

    public BigDecimal getTotalImpostoRetencaoFonte() {
        return this.totalImpostoRetencaoFonte;
    }

    public Factura totalImpostoRetencaoFonte(BigDecimal totalImpostoRetencaoFonte) {
        this.setTotalImpostoRetencaoFonte(totalImpostoRetencaoFonte);
        return this;
    }

    public void setTotalImpostoRetencaoFonte(BigDecimal totalImpostoRetencaoFonte) {
        this.totalImpostoRetencaoFonte = totalImpostoRetencaoFonte;
    }

    public BigDecimal getTotalPagar() {
        return this.totalPagar;
    }

    public Factura totalPagar(BigDecimal totalPagar) {
        this.setTotalPagar(totalPagar);
        return this;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public BigDecimal getDebito() {
        return this.debito;
    }

    public Factura debito(BigDecimal debito) {
        this.setDebito(debito);
        return this;
    }

    public void setDebito(BigDecimal debito) {
        this.debito = debito;
    }

    public BigDecimal getCredito() {
        return this.credito;
    }

    public Factura credito(BigDecimal credito) {
        this.setCredito(credito);
        return this;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
    }

    public BigDecimal getTotalPago() {
        return this.totalPago;
    }

    public Factura totalPago(BigDecimal totalPago) {
        this.setTotalPago(totalPago);
        return this;
    }

    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
    }

    public BigDecimal getTotalDiferenca() {
        return this.totalDiferenca;
    }

    public Factura totalDiferenca(BigDecimal totalDiferenca) {
        this.setTotalDiferenca(totalDiferenca);
        return this;
    }

    public void setTotalDiferenca(BigDecimal totalDiferenca) {
        this.totalDiferenca = totalDiferenca;
    }

    public Boolean getIsAutoFacturacao() {
        return this.isAutoFacturacao;
    }

    public Factura isAutoFacturacao(Boolean isAutoFacturacao) {
        this.setIsAutoFacturacao(isAutoFacturacao);
        return this;
    }

    public void setIsAutoFacturacao(Boolean isAutoFacturacao) {
        this.isAutoFacturacao = isAutoFacturacao;
    }

    public Boolean getIsRegimeCaixa() {
        return this.isRegimeCaixa;
    }

    public Factura isRegimeCaixa(Boolean isRegimeCaixa) {
        this.setIsRegimeCaixa(isRegimeCaixa);
        return this;
    }

    public void setIsRegimeCaixa(Boolean isRegimeCaixa) {
        this.isRegimeCaixa = isRegimeCaixa;
    }

    public Boolean getIsEmitidaNomeEContaTerceiro() {
        return this.isEmitidaNomeEContaTerceiro;
    }

    public Factura isEmitidaNomeEContaTerceiro(Boolean isEmitidaNomeEContaTerceiro) {
        this.setIsEmitidaNomeEContaTerceiro(isEmitidaNomeEContaTerceiro);
        return this;
    }

    public void setIsEmitidaNomeEContaTerceiro(Boolean isEmitidaNomeEContaTerceiro) {
        this.isEmitidaNomeEContaTerceiro = isEmitidaNomeEContaTerceiro;
    }

    public Boolean getIsNovo() {
        return this.isNovo;
    }

    public Factura isNovo(Boolean isNovo) {
        this.setIsNovo(isNovo);
        return this;
    }

    public void setIsNovo(Boolean isNovo) {
        this.isNovo = isNovo;
    }

    public Boolean getIsFiscalizado() {
        return this.isFiscalizado;
    }

    public Factura isFiscalizado(Boolean isFiscalizado) {
        this.setIsFiscalizado(isFiscalizado);
        return this;
    }

    public void setIsFiscalizado(Boolean isFiscalizado) {
        this.isFiscalizado = isFiscalizado;
    }

    public String getSignText() {
        return this.signText;
    }

    public Factura signText(String signText) {
        this.setSignText(signText);
        return this;
    }

    public void setSignText(String signText) {
        this.signText = signText;
    }

    public String getHash() {
        return this.hash;
    }

    public Factura hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHashShort() {
        return this.hashShort;
    }

    public Factura hashShort(String hashShort) {
        this.setHashShort(hashShort);
        return this;
    }

    public void setHashShort(String hashShort) {
        this.hashShort = hashShort;
    }

    public String getHashControl() {
        return this.hashControl;
    }

    public Factura hashControl(String hashControl) {
        this.setHashControl(hashControl);
        return this;
    }

    public void setHashControl(String hashControl) {
        this.hashControl = hashControl;
    }

    public Integer getKeyVersion() {
        return this.keyVersion;
    }

    public Factura keyVersion(Integer keyVersion) {
        this.setKeyVersion(keyVersion);
        return this;
    }

    public void setKeyVersion(Integer keyVersion) {
        this.keyVersion = keyVersion;
    }

    public Set<Factura> getFacturas() {
        return this.facturas;
    }

    public void setFacturas(Set<Factura> facturas) {
        if (this.facturas != null) {
            this.facturas.forEach(i -> i.setReferencia(null));
        }
        if (facturas != null) {
            facturas.forEach(i -> i.setReferencia(this));
        }
        this.facturas = facturas;
    }

    public Factura facturas(Set<Factura> facturas) {
        this.setFacturas(facturas);
        return this;
    }

    public Factura addFactura(Factura factura) {
        this.facturas.add(factura);
        factura.setReferencia(this);
        return this;
    }

    public Factura removeFactura(Factura factura) {
        this.facturas.remove(factura);
        factura.setReferencia(null);
        return this;
    }

    public Set<ItemFactura> getItemsFacturas() {
        return this.itemsFacturas;
    }

    public void setItemsFacturas(Set<ItemFactura> itemFacturas) {
        if (this.itemsFacturas != null) {
            this.itemsFacturas.forEach(i -> i.setFactura(null));
        }
        if (itemFacturas != null) {
            itemFacturas.forEach(i -> i.setFactura(this));
        }
        this.itemsFacturas = itemFacturas;
    }

    public Factura itemsFacturas(Set<ItemFactura> itemFacturas) {
        this.setItemsFacturas(itemFacturas);
        return this;
    }

    public Factura addItemsFactura(ItemFactura itemFactura) {
        this.itemsFacturas.add(itemFactura);
        itemFactura.setFactura(this);
        return this;
    }

    public Factura removeItemsFactura(ItemFactura itemFactura) {
        this.itemsFacturas.remove(itemFactura);
        itemFactura.setFactura(null);
        return this;
    }

    public Set<AplicacaoRecibo> getAplicacoesFacturas() {
        return this.aplicacoesFacturas;
    }

    public void setAplicacoesFacturas(Set<AplicacaoRecibo> aplicacaoRecibos) {
        if (this.aplicacoesFacturas != null) {
            this.aplicacoesFacturas.forEach(i -> i.setFactura(null));
        }
        if (aplicacaoRecibos != null) {
            aplicacaoRecibos.forEach(i -> i.setFactura(this));
        }
        this.aplicacoesFacturas = aplicacaoRecibos;
    }

    public Factura aplicacoesFacturas(Set<AplicacaoRecibo> aplicacaoRecibos) {
        this.setAplicacoesFacturas(aplicacaoRecibos);
        return this;
    }

    public Factura addAplicacoesFactura(AplicacaoRecibo aplicacaoRecibo) {
        this.aplicacoesFacturas.add(aplicacaoRecibo);
        aplicacaoRecibo.setFactura(this);
        return this;
    }

    public Factura removeAplicacoesFactura(AplicacaoRecibo aplicacaoRecibo) {
        this.aplicacoesFacturas.remove(aplicacaoRecibo);
        aplicacaoRecibo.setFactura(null);
        return this;
    }

    public Set<ResumoImpostoFactura> getResumosImpostos() {
        return this.resumosImpostos;
    }

    public void setResumosImpostos(Set<ResumoImpostoFactura> resumoImpostoFacturas) {
        if (this.resumosImpostos != null) {
            this.resumosImpostos.forEach(i -> i.setFactura(null));
        }
        if (resumoImpostoFacturas != null) {
            resumoImpostoFacturas.forEach(i -> i.setFactura(this));
        }
        this.resumosImpostos = resumoImpostoFacturas;
    }

    public Factura resumosImpostos(Set<ResumoImpostoFactura> resumoImpostoFacturas) {
        this.setResumosImpostos(resumoImpostoFacturas);
        return this;
    }

    public Factura addResumosImposto(ResumoImpostoFactura resumoImpostoFactura) {
        this.resumosImpostos.add(resumoImpostoFactura);
        resumoImpostoFactura.setFactura(this);
        return this;
    }

    public Factura removeResumosImposto(ResumoImpostoFactura resumoImpostoFactura) {
        this.resumosImpostos.remove(resumoImpostoFactura);
        resumoImpostoFactura.setFactura(null);
        return this;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setFactura(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setFactura(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public Factura anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public Factura addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setFactura(this);
        return this;
    }

    public Factura removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setFactura(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Factura utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public LookupItem getMotivoAnulacao() {
        return this.motivoAnulacao;
    }

    public void setMotivoAnulacao(LookupItem lookupItem) {
        this.motivoAnulacao = lookupItem;
    }

    public Factura motivoAnulacao(LookupItem lookupItem) {
        this.setMotivoAnulacao(lookupItem);
        return this;
    }

    public Matricula getMatricula() {
        return this.matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Factura matricula(Matricula matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public Factura getReferencia() {
        return this.referencia;
    }

    public void setReferencia(Factura factura) {
        this.referencia = factura;
    }

    public Factura referencia(Factura factura) {
        this.setReferencia(factura);
        return this;
    }

    public DocumentoComercial getDocumentoComercial() {
        return this.documentoComercial;
    }

    public void setDocumentoComercial(DocumentoComercial documentoComercial) {
        this.documentoComercial = documentoComercial;
    }

    public Factura documentoComercial(DocumentoComercial documentoComercial) {
        this.setDocumentoComercial(documentoComercial);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Factura)) {
            return false;
        }
        return id != null && id.equals(((Factura) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Factura{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", codigoEntrega='" + getCodigoEntrega() + "'" +
            ", dataEmissao='" + getDataEmissao() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", cae='" + getCae() + "'" +
            ", inicioTransporte='" + getInicioTransporte() + "'" +
            ", fimTransporte='" + getFimTransporte() + "'" +
            ", observacaoGeral='" + getObservacaoGeral() + "'" +
            ", observacaoInterna='" + getObservacaoInterna() + "'" +
            ", estado='" + getEstado() + "'" +
            ", origem='" + getOrigem() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", isMoedaEntrangeira='" + getIsMoedaEntrangeira() + "'" +
            ", moeda='" + getMoeda() + "'" +
            ", cambio=" + getCambio() +
            ", totalMoedaEntrangeira=" + getTotalMoedaEntrangeira() +
            ", totalIliquido=" + getTotalIliquido() +
            ", totalDescontoComercial=" + getTotalDescontoComercial() +
            ", totalLiquido=" + getTotalLiquido() +
            ", totalImpostoIVA=" + getTotalImpostoIVA() +
            ", totalImpostoEspecialConsumo=" + getTotalImpostoEspecialConsumo() +
            ", totalDescontoFinanceiro=" + getTotalDescontoFinanceiro() +
            ", totalFactura=" + getTotalFactura() +
            ", totalImpostoRetencaoFonte=" + getTotalImpostoRetencaoFonte() +
            ", totalPagar=" + getTotalPagar() +
            ", debito=" + getDebito() +
            ", credito=" + getCredito() +
            ", totalPago=" + getTotalPago() +
            ", totalDiferenca=" + getTotalDiferenca() +
            ", isAutoFacturacao='" + getIsAutoFacturacao() + "'" +
            ", isRegimeCaixa='" + getIsRegimeCaixa() + "'" +
            ", isEmitidaNomeEContaTerceiro='" + getIsEmitidaNomeEContaTerceiro() + "'" +
            ", isNovo='" + getIsNovo() + "'" +
            ", isFiscalizado='" + getIsFiscalizado() + "'" +
            ", signText='" + getSignText() + "'" +
            ", hash='" + getHash() + "'" +
            ", hashShort='" + getHashShort() + "'" +
            ", hashControl='" + getHashControl() + "'" +
            ", keyVersion=" + getKeyVersion() +
            "}";
    }
}
