package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.MeioPagamento} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.MeioPagamentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /meio-pagamentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MeioPagamentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private StringFilter nome;

    private IntegerFilter numeroDigitoReferencia;

    private BooleanFilter isPagamentoInstantanio;

    private StringFilter hash;

    private StringFilter link;

    private StringFilter token;

    private StringFilter username;

    private StringFilter password;

    private StringFilter formatoReferencia;

    private LongFilter transacaoId;

    private Boolean distinct;

    public MeioPagamentoCriteria() {}

    public MeioPagamentoCriteria(MeioPagamentoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.numeroDigitoReferencia = other.numeroDigitoReferencia == null ? null : other.numeroDigitoReferencia.copy();
        this.isPagamentoInstantanio = other.isPagamentoInstantanio == null ? null : other.isPagamentoInstantanio.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.token = other.token == null ? null : other.token.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.formatoReferencia = other.formatoReferencia == null ? null : other.formatoReferencia.copy();
        this.transacaoId = other.transacaoId == null ? null : other.transacaoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MeioPagamentoCriteria copy() {
        return new MeioPagamentoCriteria(this);
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

    public StringFilter getCodigo() {
        return codigo;
    }

    public StringFilter codigo() {
        if (codigo == null) {
            codigo = new StringFilter();
        }
        return codigo;
    }

    public void setCodigo(StringFilter codigo) {
        this.codigo = codigo;
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

    public IntegerFilter getNumeroDigitoReferencia() {
        return numeroDigitoReferencia;
    }

    public IntegerFilter numeroDigitoReferencia() {
        if (numeroDigitoReferencia == null) {
            numeroDigitoReferencia = new IntegerFilter();
        }
        return numeroDigitoReferencia;
    }

    public void setNumeroDigitoReferencia(IntegerFilter numeroDigitoReferencia) {
        this.numeroDigitoReferencia = numeroDigitoReferencia;
    }

    public BooleanFilter getIsPagamentoInstantanio() {
        return isPagamentoInstantanio;
    }

    public BooleanFilter isPagamentoInstantanio() {
        if (isPagamentoInstantanio == null) {
            isPagamentoInstantanio = new BooleanFilter();
        }
        return isPagamentoInstantanio;
    }

    public void setIsPagamentoInstantanio(BooleanFilter isPagamentoInstantanio) {
        this.isPagamentoInstantanio = isPagamentoInstantanio;
    }

    public StringFilter getHash() {
        return hash;
    }

    public StringFilter hash() {
        if (hash == null) {
            hash = new StringFilter();
        }
        return hash;
    }

    public void setHash(StringFilter hash) {
        this.hash = hash;
    }

    public StringFilter getLink() {
        return link;
    }

    public StringFilter link() {
        if (link == null) {
            link = new StringFilter();
        }
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public StringFilter getToken() {
        return token;
    }

    public StringFilter token() {
        if (token == null) {
            token = new StringFilter();
        }
        return token;
    }

    public void setToken(StringFilter token) {
        this.token = token;
    }

    public StringFilter getUsername() {
        return username;
    }

    public StringFilter username() {
        if (username == null) {
            username = new StringFilter();
        }
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPassword() {
        return password;
    }

    public StringFilter password() {
        if (password == null) {
            password = new StringFilter();
        }
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public StringFilter getFormatoReferencia() {
        return formatoReferencia;
    }

    public StringFilter formatoReferencia() {
        if (formatoReferencia == null) {
            formatoReferencia = new StringFilter();
        }
        return formatoReferencia;
    }

    public void setFormatoReferencia(StringFilter formatoReferencia) {
        this.formatoReferencia = formatoReferencia;
    }

    public LongFilter getTransacaoId() {
        return transacaoId;
    }

    public LongFilter transacaoId() {
        if (transacaoId == null) {
            transacaoId = new LongFilter();
        }
        return transacaoId;
    }

    public void setTransacaoId(LongFilter transacaoId) {
        this.transacaoId = transacaoId;
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
        final MeioPagamentoCriteria that = (MeioPagamentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(numeroDigitoReferencia, that.numeroDigitoReferencia) &&
            Objects.equals(isPagamentoInstantanio, that.isPagamentoInstantanio) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(link, that.link) &&
            Objects.equals(token, that.token) &&
            Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(formatoReferencia, that.formatoReferencia) &&
            Objects.equals(transacaoId, that.transacaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            codigo,
            nome,
            numeroDigitoReferencia,
            isPagamentoInstantanio,
            hash,
            link,
            token,
            username,
            password,
            formatoReferencia,
            transacaoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeioPagamentoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigo != null ? "codigo=" + codigo + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (numeroDigitoReferencia != null ? "numeroDigitoReferencia=" + numeroDigitoReferencia + ", " : "") +
            (isPagamentoInstantanio != null ? "isPagamentoInstantanio=" + isPagamentoInstantanio + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (link != null ? "link=" + link + ", " : "") +
            (token != null ? "token=" + token + ", " : "") +
            (username != null ? "username=" + username + ", " : "") +
            (password != null ? "password=" + password + ", " : "") +
            (formatoReferencia != null ? "formatoReferencia=" + formatoReferencia + ", " : "") +
            (transacaoId != null ? "transacaoId=" + transacaoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
