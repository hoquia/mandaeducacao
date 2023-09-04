package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.TipoConta;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Conta} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.ContaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoConta
     */
    public static class TipoContaFilter extends Filter<TipoConta> {

        public TipoContaFilter() {}

        public TipoContaFilter(TipoContaFilter filter) {
            super(filter);
        }

        @Override
        public TipoContaFilter copy() {
            return new TipoContaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TipoContaFilter tipo;

    private StringFilter titulo;

    private StringFilter numero;

    private StringFilter iban;

    private StringFilter titular;

    private BooleanFilter isPadrao;

    private LongFilter transacoesId;

    private LongFilter moedaId;

    private Boolean distinct;

    public ContaCriteria() {}

    public ContaCriteria(ContaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.iban = other.iban == null ? null : other.iban.copy();
        this.titular = other.titular == null ? null : other.titular.copy();
        this.isPadrao = other.isPadrao == null ? null : other.isPadrao.copy();
        this.transacoesId = other.transacoesId == null ? null : other.transacoesId.copy();
        this.moedaId = other.moedaId == null ? null : other.moedaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ContaCriteria copy() {
        return new ContaCriteria(this);
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

    public TipoContaFilter getTipo() {
        return tipo;
    }

    public TipoContaFilter tipo() {
        if (tipo == null) {
            tipo = new TipoContaFilter();
        }
        return tipo;
    }

    public void setTipo(TipoContaFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public StringFilter titulo() {
        if (titulo == null) {
            titulo = new StringFilter();
        }
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
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

    public StringFilter getIban() {
        return iban;
    }

    public StringFilter iban() {
        if (iban == null) {
            iban = new StringFilter();
        }
        return iban;
    }

    public void setIban(StringFilter iban) {
        this.iban = iban;
    }

    public StringFilter getTitular() {
        return titular;
    }

    public StringFilter titular() {
        if (titular == null) {
            titular = new StringFilter();
        }
        return titular;
    }

    public void setTitular(StringFilter titular) {
        this.titular = titular;
    }

    public BooleanFilter getIsPadrao() {
        return isPadrao;
    }

    public BooleanFilter isPadrao() {
        if (isPadrao == null) {
            isPadrao = new BooleanFilter();
        }
        return isPadrao;
    }

    public void setIsPadrao(BooleanFilter isPadrao) {
        this.isPadrao = isPadrao;
    }

    public LongFilter getTransacoesId() {
        return transacoesId;
    }

    public LongFilter transacoesId() {
        if (transacoesId == null) {
            transacoesId = new LongFilter();
        }
        return transacoesId;
    }

    public void setTransacoesId(LongFilter transacoesId) {
        this.transacoesId = transacoesId;
    }

    public LongFilter getMoedaId() {
        return moedaId;
    }

    public LongFilter moedaId() {
        if (moedaId == null) {
            moedaId = new LongFilter();
        }
        return moedaId;
    }

    public void setMoedaId(LongFilter moedaId) {
        this.moedaId = moedaId;
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
        final ContaCriteria that = (ContaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(iban, that.iban) &&
            Objects.equals(titular, that.titular) &&
            Objects.equals(isPadrao, that.isPadrao) &&
            Objects.equals(transacoesId, that.transacoesId) &&
            Objects.equals(moedaId, that.moedaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo, titulo, numero, iban, titular, isPadrao, transacoesId, moedaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tipo != null ? "tipo=" + tipo + ", " : "") +
            (titulo != null ? "titulo=" + titulo + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (iban != null ? "iban=" + iban + ", " : "") +
            (titular != null ? "titular=" + titular + ", " : "") +
            (isPadrao != null ? "isPadrao=" + isPadrao + ", " : "") +
            (transacoesId != null ? "transacoesId=" + transacoesId + ", " : "") +
            (moedaId != null ? "moedaId=" + moedaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
