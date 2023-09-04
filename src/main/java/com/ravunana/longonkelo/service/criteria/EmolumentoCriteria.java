package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Emolumento} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.EmolumentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /emolumentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmolumentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numero;

    private StringFilter nome;

    private BigDecimalFilter preco;

    private DoubleFilter quantidade;

    private IntegerFilter periodo;

    private IntegerFilter inicioPeriodo;

    private IntegerFilter fimPeriodo;

    private BooleanFilter isObrigatorioMatricula;

    private BooleanFilter isObrigatorioConfirmacao;

    private LongFilter itemFacturaId;

    private LongFilter emolumentoId;

    private LongFilter precosEmolumentoId;

    private LongFilter categoriaId;

    private LongFilter impostoId;

    private LongFilter referenciaId;

    private LongFilter planoMultaId;

    private Boolean distinct;

    public EmolumentoCriteria() {}

    public EmolumentoCriteria(EmolumentoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.preco = other.preco == null ? null : other.preco.copy();
        this.quantidade = other.quantidade == null ? null : other.quantidade.copy();
        this.periodo = other.periodo == null ? null : other.periodo.copy();
        this.inicioPeriodo = other.inicioPeriodo == null ? null : other.inicioPeriodo.copy();
        this.fimPeriodo = other.fimPeriodo == null ? null : other.fimPeriodo.copy();
        this.isObrigatorioMatricula = other.isObrigatorioMatricula == null ? null : other.isObrigatorioMatricula.copy();
        this.isObrigatorioConfirmacao = other.isObrigatorioConfirmacao == null ? null : other.isObrigatorioConfirmacao.copy();
        this.itemFacturaId = other.itemFacturaId == null ? null : other.itemFacturaId.copy();
        this.emolumentoId = other.emolumentoId == null ? null : other.emolumentoId.copy();
        this.precosEmolumentoId = other.precosEmolumentoId == null ? null : other.precosEmolumentoId.copy();
        this.categoriaId = other.categoriaId == null ? null : other.categoriaId.copy();
        this.impostoId = other.impostoId == null ? null : other.impostoId.copy();
        this.referenciaId = other.referenciaId == null ? null : other.referenciaId.copy();
        this.planoMultaId = other.planoMultaId == null ? null : other.planoMultaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmolumentoCriteria copy() {
        return new EmolumentoCriteria(this);
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

    public StringFilter getNumero() {
        return numero;
    }

    public StringFilter numero() {
        if (numero == null) {
            numero = new StringFilter();
        }
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public BigDecimalFilter getPreco() {
        return preco;
    }

    public BigDecimalFilter preco() {
        if (preco == null) {
            preco = new BigDecimalFilter();
        }
        return preco;
    }

    public void setPreco(BigDecimalFilter preco) {
        this.preco = preco;
    }

    public DoubleFilter getQuantidade() {
        return quantidade;
    }

    public DoubleFilter quantidade() {
        if (quantidade == null) {
            quantidade = new DoubleFilter();
        }
        return quantidade;
    }

    public void setQuantidade(DoubleFilter quantidade) {
        this.quantidade = quantidade;
    }

    public IntegerFilter getPeriodo() {
        return periodo;
    }

    public IntegerFilter periodo() {
        if (periodo == null) {
            periodo = new IntegerFilter();
        }
        return periodo;
    }

    public void setPeriodo(IntegerFilter periodo) {
        this.periodo = periodo;
    }

    public IntegerFilter getInicioPeriodo() {
        return inicioPeriodo;
    }

    public IntegerFilter inicioPeriodo() {
        if (inicioPeriodo == null) {
            inicioPeriodo = new IntegerFilter();
        }
        return inicioPeriodo;
    }

    public void setInicioPeriodo(IntegerFilter inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    public IntegerFilter getFimPeriodo() {
        return fimPeriodo;
    }

    public IntegerFilter fimPeriodo() {
        if (fimPeriodo == null) {
            fimPeriodo = new IntegerFilter();
        }
        return fimPeriodo;
    }

    public void setFimPeriodo(IntegerFilter fimPeriodo) {
        this.fimPeriodo = fimPeriodo;
    }

    public BooleanFilter getIsObrigatorioMatricula() {
        return isObrigatorioMatricula;
    }

    public BooleanFilter isObrigatorioMatricula() {
        if (isObrigatorioMatricula == null) {
            isObrigatorioMatricula = new BooleanFilter();
        }
        return isObrigatorioMatricula;
    }

    public void setIsObrigatorioMatricula(BooleanFilter isObrigatorioMatricula) {
        this.isObrigatorioMatricula = isObrigatorioMatricula;
    }

    public BooleanFilter getIsObrigatorioConfirmacao() {
        return isObrigatorioConfirmacao;
    }

    public BooleanFilter isObrigatorioConfirmacao() {
        if (isObrigatorioConfirmacao == null) {
            isObrigatorioConfirmacao = new BooleanFilter();
        }
        return isObrigatorioConfirmacao;
    }

    public void setIsObrigatorioConfirmacao(BooleanFilter isObrigatorioConfirmacao) {
        this.isObrigatorioConfirmacao = isObrigatorioConfirmacao;
    }

    public LongFilter getItemFacturaId() {
        return itemFacturaId;
    }

    public LongFilter itemFacturaId() {
        if (itemFacturaId == null) {
            itemFacturaId = new LongFilter();
        }
        return itemFacturaId;
    }

    public void setItemFacturaId(LongFilter itemFacturaId) {
        this.itemFacturaId = itemFacturaId;
    }

    public LongFilter getEmolumentoId() {
        return emolumentoId;
    }

    public LongFilter emolumentoId() {
        if (emolumentoId == null) {
            emolumentoId = new LongFilter();
        }
        return emolumentoId;
    }

    public void setEmolumentoId(LongFilter emolumentoId) {
        this.emolumentoId = emolumentoId;
    }

    public LongFilter getPrecosEmolumentoId() {
        return precosEmolumentoId;
    }

    public LongFilter precosEmolumentoId() {
        if (precosEmolumentoId == null) {
            precosEmolumentoId = new LongFilter();
        }
        return precosEmolumentoId;
    }

    public void setPrecosEmolumentoId(LongFilter precosEmolumentoId) {
        this.precosEmolumentoId = precosEmolumentoId;
    }

    public LongFilter getCategoriaId() {
        return categoriaId;
    }

    public LongFilter categoriaId() {
        if (categoriaId == null) {
            categoriaId = new LongFilter();
        }
        return categoriaId;
    }

    public void setCategoriaId(LongFilter categoriaId) {
        this.categoriaId = categoriaId;
    }

    public LongFilter getImpostoId() {
        return impostoId;
    }

    public LongFilter impostoId() {
        if (impostoId == null) {
            impostoId = new LongFilter();
        }
        return impostoId;
    }

    public void setImpostoId(LongFilter impostoId) {
        this.impostoId = impostoId;
    }

    public LongFilter getReferenciaId() {
        return referenciaId;
    }

    public LongFilter referenciaId() {
        if (referenciaId == null) {
            referenciaId = new LongFilter();
        }
        return referenciaId;
    }

    public void setReferenciaId(LongFilter referenciaId) {
        this.referenciaId = referenciaId;
    }

    public LongFilter getPlanoMultaId() {
        return planoMultaId;
    }

    public LongFilter planoMultaId() {
        if (planoMultaId == null) {
            planoMultaId = new LongFilter();
        }
        return planoMultaId;
    }

    public void setPlanoMultaId(LongFilter planoMultaId) {
        this.planoMultaId = planoMultaId;
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
        final EmolumentoCriteria that = (EmolumentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(preco, that.preco) &&
            Objects.equals(quantidade, that.quantidade) &&
            Objects.equals(periodo, that.periodo) &&
            Objects.equals(inicioPeriodo, that.inicioPeriodo) &&
            Objects.equals(fimPeriodo, that.fimPeriodo) &&
            Objects.equals(isObrigatorioMatricula, that.isObrigatorioMatricula) &&
            Objects.equals(isObrigatorioConfirmacao, that.isObrigatorioConfirmacao) &&
            Objects.equals(itemFacturaId, that.itemFacturaId) &&
            Objects.equals(emolumentoId, that.emolumentoId) &&
            Objects.equals(precosEmolumentoId, that.precosEmolumentoId) &&
            Objects.equals(categoriaId, that.categoriaId) &&
            Objects.equals(impostoId, that.impostoId) &&
            Objects.equals(referenciaId, that.referenciaId) &&
            Objects.equals(planoMultaId, that.planoMultaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            numero,
            nome,
            preco,
            quantidade,
            periodo,
            inicioPeriodo,
            fimPeriodo,
            isObrigatorioMatricula,
            isObrigatorioConfirmacao,
            itemFacturaId,
            emolumentoId,
            precosEmolumentoId,
            categoriaId,
            impostoId,
            referenciaId,
            planoMultaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmolumentoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (preco != null ? "preco=" + preco + ", " : "") +
            (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
            (periodo != null ? "periodo=" + periodo + ", " : "") +
            (inicioPeriodo != null ? "inicioPeriodo=" + inicioPeriodo + ", " : "") +
            (fimPeriodo != null ? "fimPeriodo=" + fimPeriodo + ", " : "") +
            (isObrigatorioMatricula != null ? "isObrigatorioMatricula=" + isObrigatorioMatricula + ", " : "") +
            (isObrigatorioConfirmacao != null ? "isObrigatorioConfirmacao=" + isObrigatorioConfirmacao + ", " : "") +
            (itemFacturaId != null ? "itemFacturaId=" + itemFacturaId + ", " : "") +
            (emolumentoId != null ? "emolumentoId=" + emolumentoId + ", " : "") +
            (precosEmolumentoId != null ? "precosEmolumentoId=" + precosEmolumentoId + ", " : "") +
            (categoriaId != null ? "categoriaId=" + categoriaId + ", " : "") +
            (impostoId != null ? "impostoId=" + impostoId + ", " : "") +
            (referenciaId != null ? "referenciaId=" + referenciaId + ", " : "") +
            (planoMultaId != null ? "planoMultaId=" + planoMultaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
