package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.DocumentoFiscal;
import com.ravunana.longonkelo.domain.enumeration.ModuloDocumento;
import com.ravunana.longonkelo.domain.enumeration.OrigemDocumento;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DocumentoComercial.
 */
@Entity
@Table(name = "documento_comercial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentoComercial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "modulo", nullable = false)
    private ModuloDocumento modulo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "origem", nullable = false)
    private OrigemDocumento origem;

    @NotNull
    @Size(max = 6)
    @Column(name = "sigla_interna", length = 6, nullable = false, unique = true)
    private String siglaInterna;

    @NotNull
    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sigla_fiscal", nullable = false)
    private DocumentoFiscal siglaFiscal;

    @Column(name = "is_movimenta_estoque")
    private Boolean isMovimentaEstoque;

    @Column(name = "is_movimenta_caixa")
    private Boolean isMovimentaCaixa;

    @Column(name = "is_notifica_entidade")
    private Boolean isNotificaEntidade;

    @Column(name = "is_notifica_gerente")
    private Boolean isNotificaGerente;

    @Column(name = "is_envia_sms")
    private Boolean isEnviaSMS;

    @Column(name = "is_envia_email")
    private Boolean isEnviaEmail;

    @Column(name = "is_envia_push")
    private Boolean isEnviaPush;

    @Column(name = "valida_credito_disponivel")
    private Boolean validaCreditoDisponivel;

    @OneToMany(mappedBy = "tipoDocumento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sequenciaDocumentos", "tipoDocumento" }, allowSetters = true)
    private Set<SerieDocumento> serieDocumentos = new HashSet<>();

    @OneToMany(mappedBy = "documentoComercial")
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

    @OneToMany(mappedBy = "documentoComercial")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "aplicacoesRecibos", "anoLectivos", "utilizador", "matricula", "documentoComercial", "transacao" },
        allowSetters = true
    )
    private Set<Recibo> recibos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "serieDocumentos", "facturas", "recibos", "transformaEm" }, allowSetters = true)
    private DocumentoComercial transformaEm;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocumentoComercial id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModuloDocumento getModulo() {
        return this.modulo;
    }

    public DocumentoComercial modulo(ModuloDocumento modulo) {
        this.setModulo(modulo);
        return this;
    }

    public void setModulo(ModuloDocumento modulo) {
        this.modulo = modulo;
    }

    public OrigemDocumento getOrigem() {
        return this.origem;
    }

    public DocumentoComercial origem(OrigemDocumento origem) {
        this.setOrigem(origem);
        return this;
    }

    public void setOrigem(OrigemDocumento origem) {
        this.origem = origem;
    }

    public String getSiglaInterna() {
        return this.siglaInterna;
    }

    public DocumentoComercial siglaInterna(String siglaInterna) {
        this.setSiglaInterna(siglaInterna);
        return this;
    }

    public void setSiglaInterna(String siglaInterna) {
        this.siglaInterna = siglaInterna;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public DocumentoComercial descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public DocumentoFiscal getSiglaFiscal() {
        return this.siglaFiscal;
    }

    public DocumentoComercial siglaFiscal(DocumentoFiscal siglaFiscal) {
        this.setSiglaFiscal(siglaFiscal);
        return this;
    }

    public void setSiglaFiscal(DocumentoFiscal siglaFiscal) {
        this.siglaFiscal = siglaFiscal;
    }

    public Boolean getIsMovimentaEstoque() {
        return this.isMovimentaEstoque;
    }

    public DocumentoComercial isMovimentaEstoque(Boolean isMovimentaEstoque) {
        this.setIsMovimentaEstoque(isMovimentaEstoque);
        return this;
    }

    public void setIsMovimentaEstoque(Boolean isMovimentaEstoque) {
        this.isMovimentaEstoque = isMovimentaEstoque;
    }

    public Boolean getIsMovimentaCaixa() {
        return this.isMovimentaCaixa;
    }

    public DocumentoComercial isMovimentaCaixa(Boolean isMovimentaCaixa) {
        this.setIsMovimentaCaixa(isMovimentaCaixa);
        return this;
    }

    public void setIsMovimentaCaixa(Boolean isMovimentaCaixa) {
        this.isMovimentaCaixa = isMovimentaCaixa;
    }

    public Boolean getIsNotificaEntidade() {
        return this.isNotificaEntidade;
    }

    public DocumentoComercial isNotificaEntidade(Boolean isNotificaEntidade) {
        this.setIsNotificaEntidade(isNotificaEntidade);
        return this;
    }

    public void setIsNotificaEntidade(Boolean isNotificaEntidade) {
        this.isNotificaEntidade = isNotificaEntidade;
    }

    public Boolean getIsNotificaGerente() {
        return this.isNotificaGerente;
    }

    public DocumentoComercial isNotificaGerente(Boolean isNotificaGerente) {
        this.setIsNotificaGerente(isNotificaGerente);
        return this;
    }

    public void setIsNotificaGerente(Boolean isNotificaGerente) {
        this.isNotificaGerente = isNotificaGerente;
    }

    public Boolean getIsEnviaSMS() {
        return this.isEnviaSMS;
    }

    public DocumentoComercial isEnviaSMS(Boolean isEnviaSMS) {
        this.setIsEnviaSMS(isEnviaSMS);
        return this;
    }

    public void setIsEnviaSMS(Boolean isEnviaSMS) {
        this.isEnviaSMS = isEnviaSMS;
    }

    public Boolean getIsEnviaEmail() {
        return this.isEnviaEmail;
    }

    public DocumentoComercial isEnviaEmail(Boolean isEnviaEmail) {
        this.setIsEnviaEmail(isEnviaEmail);
        return this;
    }

    public void setIsEnviaEmail(Boolean isEnviaEmail) {
        this.isEnviaEmail = isEnviaEmail;
    }

    public Boolean getIsEnviaPush() {
        return this.isEnviaPush;
    }

    public DocumentoComercial isEnviaPush(Boolean isEnviaPush) {
        this.setIsEnviaPush(isEnviaPush);
        return this;
    }

    public void setIsEnviaPush(Boolean isEnviaPush) {
        this.isEnviaPush = isEnviaPush;
    }

    public Boolean getValidaCreditoDisponivel() {
        return this.validaCreditoDisponivel;
    }

    public DocumentoComercial validaCreditoDisponivel(Boolean validaCreditoDisponivel) {
        this.setValidaCreditoDisponivel(validaCreditoDisponivel);
        return this;
    }

    public void setValidaCreditoDisponivel(Boolean validaCreditoDisponivel) {
        this.validaCreditoDisponivel = validaCreditoDisponivel;
    }

    public Set<SerieDocumento> getSerieDocumentos() {
        return this.serieDocumentos;
    }

    public void setSerieDocumentos(Set<SerieDocumento> serieDocumentos) {
        if (this.serieDocumentos != null) {
            this.serieDocumentos.forEach(i -> i.setTipoDocumento(null));
        }
        if (serieDocumentos != null) {
            serieDocumentos.forEach(i -> i.setTipoDocumento(this));
        }
        this.serieDocumentos = serieDocumentos;
    }

    public DocumentoComercial serieDocumentos(Set<SerieDocumento> serieDocumentos) {
        this.setSerieDocumentos(serieDocumentos);
        return this;
    }

    public DocumentoComercial addSerieDocumento(SerieDocumento serieDocumento) {
        this.serieDocumentos.add(serieDocumento);
        serieDocumento.setTipoDocumento(this);
        return this;
    }

    public DocumentoComercial removeSerieDocumento(SerieDocumento serieDocumento) {
        this.serieDocumentos.remove(serieDocumento);
        serieDocumento.setTipoDocumento(null);
        return this;
    }

    public Set<Factura> getFacturas() {
        return this.facturas;
    }

    public void setFacturas(Set<Factura> facturas) {
        if (this.facturas != null) {
            this.facturas.forEach(i -> i.setDocumentoComercial(null));
        }
        if (facturas != null) {
            facturas.forEach(i -> i.setDocumentoComercial(this));
        }
        this.facturas = facturas;
    }

    public DocumentoComercial facturas(Set<Factura> facturas) {
        this.setFacturas(facturas);
        return this;
    }

    public DocumentoComercial addFactura(Factura factura) {
        this.facturas.add(factura);
        factura.setDocumentoComercial(this);
        return this;
    }

    public DocumentoComercial removeFactura(Factura factura) {
        this.facturas.remove(factura);
        factura.setDocumentoComercial(null);
        return this;
    }

    public Set<Recibo> getRecibos() {
        return this.recibos;
    }

    public void setRecibos(Set<Recibo> recibos) {
        if (this.recibos != null) {
            this.recibos.forEach(i -> i.setDocumentoComercial(null));
        }
        if (recibos != null) {
            recibos.forEach(i -> i.setDocumentoComercial(this));
        }
        this.recibos = recibos;
    }

    public DocumentoComercial recibos(Set<Recibo> recibos) {
        this.setRecibos(recibos);
        return this;
    }

    public DocumentoComercial addRecibo(Recibo recibo) {
        this.recibos.add(recibo);
        recibo.setDocumentoComercial(this);
        return this;
    }

    public DocumentoComercial removeRecibo(Recibo recibo) {
        this.recibos.remove(recibo);
        recibo.setDocumentoComercial(null);
        return this;
    }

    public DocumentoComercial getTransformaEm() {
        return this.transformaEm;
    }

    public void setTransformaEm(DocumentoComercial documentoComercial) {
        this.transformaEm = documentoComercial;
    }

    public DocumentoComercial transformaEm(DocumentoComercial documentoComercial) {
        this.setTransformaEm(documentoComercial);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentoComercial)) {
            return false;
        }
        return id != null && id.equals(((DocumentoComercial) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentoComercial{" +
            "id=" + getId() +
            ", modulo='" + getModulo() + "'" +
            ", origem='" + getOrigem() + "'" +
            ", siglaInterna='" + getSiglaInterna() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", siglaFiscal='" + getSiglaFiscal() + "'" +
            ", isMovimentaEstoque='" + getIsMovimentaEstoque() + "'" +
            ", isMovimentaCaixa='" + getIsMovimentaCaixa() + "'" +
            ", isNotificaEntidade='" + getIsNotificaEntidade() + "'" +
            ", isNotificaGerente='" + getIsNotificaGerente() + "'" +
            ", isEnviaSMS='" + getIsEnviaSMS() + "'" +
            ", isEnviaEmail='" + getIsEnviaEmail() + "'" +
            ", isEnviaPush='" + getIsEnviaPush() + "'" +
            ", validaCreditoDisponivel='" + getValidaCreditoDisponivel() + "'" +
            "}";
    }
}
