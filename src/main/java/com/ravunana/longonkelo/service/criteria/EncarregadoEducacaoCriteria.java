package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.Sexo;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.EncarregadoEducacao} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.EncarregadoEducacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /encarregado-educacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EncarregadoEducacaoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Sexo
     */
    public static class SexoFilter extends Filter<Sexo> {

        public SexoFilter() {}

        public SexoFilter(SexoFilter filter) {
            super(filter);
        }

        @Override
        public SexoFilter copy() {
            return new SexoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private LocalDateFilter nascimento;

    private StringFilter nif;

    private SexoFilter sexo;

    private StringFilter documentoNumero;

    private StringFilter telefonePrincipal;

    private StringFilter telefoneAlternativo;

    private StringFilter email;

    private StringFilter residencia;

    private StringFilter enderecoTrabalho;

    private BigDecimalFilter rendaMensal;

    private StringFilter empresaTrabalho;

    private StringFilter hash;

    private LongFilter discentesId;

    private LongFilter matriculaId;

    private LongFilter grauParentescoId;

    private LongFilter tipoDocumentoId;

    private LongFilter profissaoId;

    private Boolean distinct;

    public EncarregadoEducacaoCriteria() {}

    public EncarregadoEducacaoCriteria(EncarregadoEducacaoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.nascimento = other.nascimento == null ? null : other.nascimento.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.sexo = other.sexo == null ? null : other.sexo.copy();
        this.documentoNumero = other.documentoNumero == null ? null : other.documentoNumero.copy();
        this.telefonePrincipal = other.telefonePrincipal == null ? null : other.telefonePrincipal.copy();
        this.telefoneAlternativo = other.telefoneAlternativo == null ? null : other.telefoneAlternativo.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.residencia = other.residencia == null ? null : other.residencia.copy();
        this.enderecoTrabalho = other.enderecoTrabalho == null ? null : other.enderecoTrabalho.copy();
        this.rendaMensal = other.rendaMensal == null ? null : other.rendaMensal.copy();
        this.empresaTrabalho = other.empresaTrabalho == null ? null : other.empresaTrabalho.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.discentesId = other.discentesId == null ? null : other.discentesId.copy();
        this.matriculaId = other.matriculaId == null ? null : other.matriculaId.copy();
        this.grauParentescoId = other.grauParentescoId == null ? null : other.grauParentescoId.copy();
        this.tipoDocumentoId = other.tipoDocumentoId == null ? null : other.tipoDocumentoId.copy();
        this.profissaoId = other.profissaoId == null ? null : other.profissaoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EncarregadoEducacaoCriteria copy() {
        return new EncarregadoEducacaoCriteria(this);
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

    public LocalDateFilter getNascimento() {
        return nascimento;
    }

    public LocalDateFilter nascimento() {
        if (nascimento == null) {
            nascimento = new LocalDateFilter();
        }
        return nascimento;
    }

    public void setNascimento(LocalDateFilter nascimento) {
        this.nascimento = nascimento;
    }

    public StringFilter getNif() {
        return nif;
    }

    public StringFilter nif() {
        if (nif == null) {
            nif = new StringFilter();
        }
        return nif;
    }

    public void setNif(StringFilter nif) {
        this.nif = nif;
    }

    public SexoFilter getSexo() {
        return sexo;
    }

    public SexoFilter sexo() {
        if (sexo == null) {
            sexo = new SexoFilter();
        }
        return sexo;
    }

    public void setSexo(SexoFilter sexo) {
        this.sexo = sexo;
    }

    public StringFilter getDocumentoNumero() {
        return documentoNumero;
    }

    public StringFilter documentoNumero() {
        if (documentoNumero == null) {
            documentoNumero = new StringFilter();
        }
        return documentoNumero;
    }

    public void setDocumentoNumero(StringFilter documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    public StringFilter getTelefonePrincipal() {
        return telefonePrincipal;
    }

    public StringFilter telefonePrincipal() {
        if (telefonePrincipal == null) {
            telefonePrincipal = new StringFilter();
        }
        return telefonePrincipal;
    }

    public void setTelefonePrincipal(StringFilter telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public StringFilter getTelefoneAlternativo() {
        return telefoneAlternativo;
    }

    public StringFilter telefoneAlternativo() {
        if (telefoneAlternativo == null) {
            telefoneAlternativo = new StringFilter();
        }
        return telefoneAlternativo;
    }

    public void setTelefoneAlternativo(StringFilter telefoneAlternativo) {
        this.telefoneAlternativo = telefoneAlternativo;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getResidencia() {
        return residencia;
    }

    public StringFilter residencia() {
        if (residencia == null) {
            residencia = new StringFilter();
        }
        return residencia;
    }

    public void setResidencia(StringFilter residencia) {
        this.residencia = residencia;
    }

    public StringFilter getEnderecoTrabalho() {
        return enderecoTrabalho;
    }

    public StringFilter enderecoTrabalho() {
        if (enderecoTrabalho == null) {
            enderecoTrabalho = new StringFilter();
        }
        return enderecoTrabalho;
    }

    public void setEnderecoTrabalho(StringFilter enderecoTrabalho) {
        this.enderecoTrabalho = enderecoTrabalho;
    }

    public BigDecimalFilter getRendaMensal() {
        return rendaMensal;
    }

    public BigDecimalFilter rendaMensal() {
        if (rendaMensal == null) {
            rendaMensal = new BigDecimalFilter();
        }
        return rendaMensal;
    }

    public void setRendaMensal(BigDecimalFilter rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public StringFilter getEmpresaTrabalho() {
        return empresaTrabalho;
    }

    public StringFilter empresaTrabalho() {
        if (empresaTrabalho == null) {
            empresaTrabalho = new StringFilter();
        }
        return empresaTrabalho;
    }

    public void setEmpresaTrabalho(StringFilter empresaTrabalho) {
        this.empresaTrabalho = empresaTrabalho;
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

    public LongFilter getDiscentesId() {
        return discentesId;
    }

    public LongFilter discentesId() {
        if (discentesId == null) {
            discentesId = new LongFilter();
        }
        return discentesId;
    }

    public void setDiscentesId(LongFilter discentesId) {
        this.discentesId = discentesId;
    }

    public LongFilter getMatriculaId() {
        return matriculaId;
    }

    public LongFilter matriculaId() {
        if (matriculaId == null) {
            matriculaId = new LongFilter();
        }
        return matriculaId;
    }

    public void setMatriculaId(LongFilter matriculaId) {
        this.matriculaId = matriculaId;
    }

    public LongFilter getGrauParentescoId() {
        return grauParentescoId;
    }

    public LongFilter grauParentescoId() {
        if (grauParentescoId == null) {
            grauParentescoId = new LongFilter();
        }
        return grauParentescoId;
    }

    public void setGrauParentescoId(LongFilter grauParentescoId) {
        this.grauParentescoId = grauParentescoId;
    }

    public LongFilter getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public LongFilter tipoDocumentoId() {
        if (tipoDocumentoId == null) {
            tipoDocumentoId = new LongFilter();
        }
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(LongFilter tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public LongFilter getProfissaoId() {
        return profissaoId;
    }

    public LongFilter profissaoId() {
        if (profissaoId == null) {
            profissaoId = new LongFilter();
        }
        return profissaoId;
    }

    public void setProfissaoId(LongFilter profissaoId) {
        this.profissaoId = profissaoId;
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
        final EncarregadoEducacaoCriteria that = (EncarregadoEducacaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(nascimento, that.nascimento) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(sexo, that.sexo) &&
            Objects.equals(documentoNumero, that.documentoNumero) &&
            Objects.equals(telefonePrincipal, that.telefonePrincipal) &&
            Objects.equals(telefoneAlternativo, that.telefoneAlternativo) &&
            Objects.equals(email, that.email) &&
            Objects.equals(residencia, that.residencia) &&
            Objects.equals(enderecoTrabalho, that.enderecoTrabalho) &&
            Objects.equals(rendaMensal, that.rendaMensal) &&
            Objects.equals(empresaTrabalho, that.empresaTrabalho) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(discentesId, that.discentesId) &&
            Objects.equals(matriculaId, that.matriculaId) &&
            Objects.equals(grauParentescoId, that.grauParentescoId) &&
            Objects.equals(tipoDocumentoId, that.tipoDocumentoId) &&
            Objects.equals(profissaoId, that.profissaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            nascimento,
            nif,
            sexo,
            documentoNumero,
            telefonePrincipal,
            telefoneAlternativo,
            email,
            residencia,
            enderecoTrabalho,
            rendaMensal,
            empresaTrabalho,
            hash,
            discentesId,
            matriculaId,
            grauParentescoId,
            tipoDocumentoId,
            profissaoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EncarregadoEducacaoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (nascimento != null ? "nascimento=" + nascimento + ", " : "") +
            (nif != null ? "nif=" + nif + ", " : "") +
            (sexo != null ? "sexo=" + sexo + ", " : "") +
            (documentoNumero != null ? "documentoNumero=" + documentoNumero + ", " : "") +
            (telefonePrincipal != null ? "telefonePrincipal=" + telefonePrincipal + ", " : "") +
            (telefoneAlternativo != null ? "telefoneAlternativo=" + telefoneAlternativo + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (residencia != null ? "residencia=" + residencia + ", " : "") +
            (enderecoTrabalho != null ? "enderecoTrabalho=" + enderecoTrabalho + ", " : "") +
            (rendaMensal != null ? "rendaMensal=" + rendaMensal + ", " : "") +
            (empresaTrabalho != null ? "empresaTrabalho=" + empresaTrabalho + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (discentesId != null ? "discentesId=" + discentesId + ", " : "") +
            (matriculaId != null ? "matriculaId=" + matriculaId + ", " : "") +
            (grauParentescoId != null ? "grauParentescoId=" + grauParentescoId + ", " : "") +
            (tipoDocumentoId != null ? "tipoDocumentoId=" + tipoDocumentoId + ", " : "") +
            (profissaoId != null ? "profissaoId=" + profissaoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
