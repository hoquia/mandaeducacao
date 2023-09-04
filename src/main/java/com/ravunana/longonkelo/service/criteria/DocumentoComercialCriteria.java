package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.DocumentoFiscal;
import com.ravunana.longonkelo.domain.enumeration.ModuloDocumento;
import com.ravunana.longonkelo.domain.enumeration.OrigemDocumento;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.DocumentoComercial} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.DocumentoComercialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /documento-comercials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentoComercialCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ModuloDocumento
     */
    public static class ModuloDocumentoFilter extends Filter<ModuloDocumento> {

        public ModuloDocumentoFilter() {}

        public ModuloDocumentoFilter(ModuloDocumentoFilter filter) {
            super(filter);
        }

        @Override
        public ModuloDocumentoFilter copy() {
            return new ModuloDocumentoFilter(this);
        }
    }

    /**
     * Class for filtering OrigemDocumento
     */
    public static class OrigemDocumentoFilter extends Filter<OrigemDocumento> {

        public OrigemDocumentoFilter() {}

        public OrigemDocumentoFilter(OrigemDocumentoFilter filter) {
            super(filter);
        }

        @Override
        public OrigemDocumentoFilter copy() {
            return new OrigemDocumentoFilter(this);
        }
    }

    /**
     * Class for filtering DocumentoFiscal
     */
    public static class DocumentoFiscalFilter extends Filter<DocumentoFiscal> {

        public DocumentoFiscalFilter() {}

        public DocumentoFiscalFilter(DocumentoFiscalFilter filter) {
            super(filter);
        }

        @Override
        public DocumentoFiscalFilter copy() {
            return new DocumentoFiscalFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ModuloDocumentoFilter modulo;

    private OrigemDocumentoFilter origem;

    private StringFilter siglaInterna;

    private StringFilter descricao;

    private DocumentoFiscalFilter siglaFiscal;

    private BooleanFilter isMovimentaEstoque;

    private BooleanFilter isMovimentaCaixa;

    private BooleanFilter isNotificaEntidade;

    private BooleanFilter isNotificaGerente;

    private BooleanFilter isEnviaSMS;

    private BooleanFilter isEnviaEmail;

    private BooleanFilter isEnviaPush;

    private BooleanFilter validaCreditoDisponivel;

    private LongFilter serieDocumentoId;

    private LongFilter facturaId;

    private LongFilter reciboId;

    private LongFilter transformaEmId;

    private Boolean distinct;

    public DocumentoComercialCriteria() {}

    public DocumentoComercialCriteria(DocumentoComercialCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.modulo = other.modulo == null ? null : other.modulo.copy();
        this.origem = other.origem == null ? null : other.origem.copy();
        this.siglaInterna = other.siglaInterna == null ? null : other.siglaInterna.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.siglaFiscal = other.siglaFiscal == null ? null : other.siglaFiscal.copy();
        this.isMovimentaEstoque = other.isMovimentaEstoque == null ? null : other.isMovimentaEstoque.copy();
        this.isMovimentaCaixa = other.isMovimentaCaixa == null ? null : other.isMovimentaCaixa.copy();
        this.isNotificaEntidade = other.isNotificaEntidade == null ? null : other.isNotificaEntidade.copy();
        this.isNotificaGerente = other.isNotificaGerente == null ? null : other.isNotificaGerente.copy();
        this.isEnviaSMS = other.isEnviaSMS == null ? null : other.isEnviaSMS.copy();
        this.isEnviaEmail = other.isEnviaEmail == null ? null : other.isEnviaEmail.copy();
        this.isEnviaPush = other.isEnviaPush == null ? null : other.isEnviaPush.copy();
        this.validaCreditoDisponivel = other.validaCreditoDisponivel == null ? null : other.validaCreditoDisponivel.copy();
        this.serieDocumentoId = other.serieDocumentoId == null ? null : other.serieDocumentoId.copy();
        this.facturaId = other.facturaId == null ? null : other.facturaId.copy();
        this.reciboId = other.reciboId == null ? null : other.reciboId.copy();
        this.transformaEmId = other.transformaEmId == null ? null : other.transformaEmId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DocumentoComercialCriteria copy() {
        return new DocumentoComercialCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ModuloDocumentoFilter getModulo() {
        return modulo;
    }

    public ModuloDocumentoFilter modulo() {
        if (modulo == null) {
            modulo = new ModuloDocumentoFilter();
        }
        return modulo;
    }

    public void setModulo(ModuloDocumentoFilter modulo) {
        this.modulo = modulo;
    }

    public OrigemDocumentoFilter getOrigem() {
        return origem;
    }

    public OrigemDocumentoFilter origem() {
        if (origem == null) {
            origem = new OrigemDocumentoFilter();
        }
        return origem;
    }

    public void setOrigem(OrigemDocumentoFilter origem) {
        this.origem = origem;
    }

    public StringFilter getSiglaInterna() {
        return siglaInterna;
    }

    public StringFilter siglaInterna() {
        if (siglaInterna == null) {
            siglaInterna = new StringFilter();
        }
        return siglaInterna;
    }

    public void setSiglaInterna(StringFilter siglaInterna) {
        this.siglaInterna = siglaInterna;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public StringFilter descricao() {
        if (descricao == null) {
            descricao = new StringFilter();
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public DocumentoFiscalFilter getSiglaFiscal() {
        return siglaFiscal;
    }

    public DocumentoFiscalFilter siglaFiscal() {
        if (siglaFiscal == null) {
            siglaFiscal = new DocumentoFiscalFilter();
        }
        return siglaFiscal;
    }

    public void setSiglaFiscal(DocumentoFiscalFilter siglaFiscal) {
        this.siglaFiscal = siglaFiscal;
    }

    public BooleanFilter getIsMovimentaEstoque() {
        return isMovimentaEstoque;
    }

    public BooleanFilter isMovimentaEstoque() {
        if (isMovimentaEstoque == null) {
            isMovimentaEstoque = new BooleanFilter();
        }
        return isMovimentaEstoque;
    }

    public void setIsMovimentaEstoque(BooleanFilter isMovimentaEstoque) {
        this.isMovimentaEstoque = isMovimentaEstoque;
    }

    public BooleanFilter getIsMovimentaCaixa() {
        return isMovimentaCaixa;
    }

    public BooleanFilter isMovimentaCaixa() {
        if (isMovimentaCaixa == null) {
            isMovimentaCaixa = new BooleanFilter();
        }
        return isMovimentaCaixa;
    }

    public void setIsMovimentaCaixa(BooleanFilter isMovimentaCaixa) {
        this.isMovimentaCaixa = isMovimentaCaixa;
    }

    public BooleanFilter getIsNotificaEntidade() {
        return isNotificaEntidade;
    }

    public BooleanFilter isNotificaEntidade() {
        if (isNotificaEntidade == null) {
            isNotificaEntidade = new BooleanFilter();
        }
        return isNotificaEntidade;
    }

    public void setIsNotificaEntidade(BooleanFilter isNotificaEntidade) {
        this.isNotificaEntidade = isNotificaEntidade;
    }

    public BooleanFilter getIsNotificaGerente() {
        return isNotificaGerente;
    }

    public BooleanFilter isNotificaGerente() {
        if (isNotificaGerente == null) {
            isNotificaGerente = new BooleanFilter();
        }
        return isNotificaGerente;
    }

    public void setIsNotificaGerente(BooleanFilter isNotificaGerente) {
        this.isNotificaGerente = isNotificaGerente;
    }

    public BooleanFilter getIsEnviaSMS() {
        return isEnviaSMS;
    }

    public BooleanFilter isEnviaSMS() {
        if (isEnviaSMS == null) {
            isEnviaSMS = new BooleanFilter();
        }
        return isEnviaSMS;
    }

    public void setIsEnviaSMS(BooleanFilter isEnviaSMS) {
        this.isEnviaSMS = isEnviaSMS;
    }

    public BooleanFilter getIsEnviaEmail() {
        return isEnviaEmail;
    }

    public BooleanFilter isEnviaEmail() {
        if (isEnviaEmail == null) {
            isEnviaEmail = new BooleanFilter();
        }
        return isEnviaEmail;
    }

    public void setIsEnviaEmail(BooleanFilter isEnviaEmail) {
        this.isEnviaEmail = isEnviaEmail;
    }

    public BooleanFilter getIsEnviaPush() {
        return isEnviaPush;
    }

    public BooleanFilter isEnviaPush() {
        if (isEnviaPush == null) {
            isEnviaPush = new BooleanFilter();
        }
        return isEnviaPush;
    }

    public void setIsEnviaPush(BooleanFilter isEnviaPush) {
        this.isEnviaPush = isEnviaPush;
    }

    public BooleanFilter getValidaCreditoDisponivel() {
        return validaCreditoDisponivel;
    }

    public BooleanFilter validaCreditoDisponivel() {
        if (validaCreditoDisponivel == null) {
            validaCreditoDisponivel = new BooleanFilter();
        }
        return validaCreditoDisponivel;
    }

    public void setValidaCreditoDisponivel(BooleanFilter validaCreditoDisponivel) {
        this.validaCreditoDisponivel = validaCreditoDisponivel;
    }

    public LongFilter getSerieDocumentoId() {
        return serieDocumentoId;
    }

    public LongFilter serieDocumentoId() {
        if (serieDocumentoId == null) {
            serieDocumentoId = new LongFilter();
        }
        return serieDocumentoId;
    }

    public void setSerieDocumentoId(LongFilter serieDocumentoId) {
        this.serieDocumentoId = serieDocumentoId;
    }

    public LongFilter getFacturaId() {
        return facturaId;
    }

    public LongFilter facturaId() {
        if (facturaId == null) {
            facturaId = new LongFilter();
        }
        return facturaId;
    }

    public void setFacturaId(LongFilter facturaId) {
        this.facturaId = facturaId;
    }

    public LongFilter getReciboId() {
        return reciboId;
    }

    public LongFilter reciboId() {
        if (reciboId == null) {
            reciboId = new LongFilter();
        }
        return reciboId;
    }

    public void setReciboId(LongFilter reciboId) {
        this.reciboId = reciboId;
    }

    public LongFilter getTransformaEmId() {
        return transformaEmId;
    }

    public LongFilter transformaEmId() {
        if (transformaEmId == null) {
            transformaEmId = new LongFilter();
        }
        return transformaEmId;
    }

    public void setTransformaEmId(LongFilter transformaEmId) {
        this.transformaEmId = transformaEmId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DocumentoComercialCriteria that = (DocumentoComercialCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(modulo, that.modulo) &&
            Objects.equals(origem, that.origem) &&
            Objects.equals(siglaInterna, that.siglaInterna) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(siglaFiscal, that.siglaFiscal) &&
            Objects.equals(isMovimentaEstoque, that.isMovimentaEstoque) &&
            Objects.equals(isMovimentaCaixa, that.isMovimentaCaixa) &&
            Objects.equals(isNotificaEntidade, that.isNotificaEntidade) &&
            Objects.equals(isNotificaGerente, that.isNotificaGerente) &&
            Objects.equals(isEnviaSMS, that.isEnviaSMS) &&
            Objects.equals(isEnviaEmail, that.isEnviaEmail) &&
            Objects.equals(isEnviaPush, that.isEnviaPush) &&
            Objects.equals(validaCreditoDisponivel, that.validaCreditoDisponivel) &&
            Objects.equals(serieDocumentoId, that.serieDocumentoId) &&
            Objects.equals(facturaId, that.facturaId) &&
            Objects.equals(reciboId, that.reciboId) &&
            Objects.equals(transformaEmId, that.transformaEmId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            modulo,
            origem,
            siglaInterna,
            descricao,
            siglaFiscal,
            isMovimentaEstoque,
            isMovimentaCaixa,
            isNotificaEntidade,
            isNotificaGerente,
            isEnviaSMS,
            isEnviaEmail,
            isEnviaPush,
            validaCreditoDisponivel,
            serieDocumentoId,
            facturaId,
            reciboId,
            transformaEmId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentoComercialCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (modulo != null ? "modulo=" + modulo + ", " : "") +
            (origem != null ? "origem=" + origem + ", " : "") +
            (siglaInterna != null ? "siglaInterna=" + siglaInterna + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (siglaFiscal != null ? "siglaFiscal=" + siglaFiscal + ", " : "") +
            (isMovimentaEstoque != null ? "isMovimentaEstoque=" + isMovimentaEstoque + ", " : "") +
            (isMovimentaCaixa != null ? "isMovimentaCaixa=" + isMovimentaCaixa + ", " : "") +
            (isNotificaEntidade != null ? "isNotificaEntidade=" + isNotificaEntidade + ", " : "") +
            (isNotificaGerente != null ? "isNotificaGerente=" + isNotificaGerente + ", " : "") +
            (isEnviaSMS != null ? "isEnviaSMS=" + isEnviaSMS + ", " : "") +
            (isEnviaEmail != null ? "isEnviaEmail=" + isEnviaEmail + ", " : "") +
            (isEnviaPush != null ? "isEnviaPush=" + isEnviaPush + ", " : "") +
            (validaCreditoDisponivel != null ? "validaCreditoDisponivel=" + validaCreditoDisponivel + ", " : "") +
            (serieDocumentoId != null ? "serieDocumentoId=" + serieDocumentoId + ", " : "") +
            (facturaId != null ? "facturaId=" + facturaId + ", " : "") +
            (reciboId != null ? "reciboId=" + reciboId + ", " : "") +
            (transformaEmId != null ? "transformaEmId=" + transformaEmId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
