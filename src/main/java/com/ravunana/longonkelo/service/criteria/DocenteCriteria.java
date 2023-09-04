package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.Sexo;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Docente} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.DocenteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /docentes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocenteCriteria implements Serializable, Criteria {

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

    private StringFilter inss;

    private SexoFilter sexo;

    private StringFilter pai;

    private StringFilter mae;

    private StringFilter documentoNumero;

    private LocalDateFilter documentoEmissao;

    private LocalDateFilter documentoValidade;

    private StringFilter residencia;

    private LocalDateFilter dataInicioFuncoes;

    private StringFilter telefonePrincipal;

    private StringFilter telefoneParente;

    private StringFilter email;

    private StringFilter numeroAgente;

    private BooleanFilter temAgregacaoPedagogica;

    private StringFilter hash;

    private ZonedDateTimeFilter timestamp;

    private LongFilter ocorrenciaId;

    private LongFilter horariosId;

    private LongFilter planoAulaId;

    private LongFilter notasPeriodicaDisciplinaId;

    private LongFilter notasGeralDisciplinaId;

    private LongFilter dissertacaoFinalCursoId;

    private LongFilter categoriaOcorrenciaId;

    private LongFilter formacoesId;

    private LongFilter nacionalidadeId;

    private LongFilter naturalidadeId;

    private LongFilter tipoDocumentoId;

    private LongFilter grauAcademicoId;

    private LongFilter categoriaProfissionalId;

    private LongFilter unidadeOrganicaId;

    private LongFilter estadoCivilId;

    private LongFilter responsavelTurnoId;

    private LongFilter responsavelAreaFormacaoId;

    private LongFilter responsavelCursoId;

    private LongFilter responsavelDisciplinaId;

    private LongFilter responsavelTurmaId;

    private Boolean distinct;

    public DocenteCriteria() {}

    public DocenteCriteria(DocenteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.nascimento = other.nascimento == null ? null : other.nascimento.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.inss = other.inss == null ? null : other.inss.copy();
        this.sexo = other.sexo == null ? null : other.sexo.copy();
        this.pai = other.pai == null ? null : other.pai.copy();
        this.mae = other.mae == null ? null : other.mae.copy();
        this.documentoNumero = other.documentoNumero == null ? null : other.documentoNumero.copy();
        this.documentoEmissao = other.documentoEmissao == null ? null : other.documentoEmissao.copy();
        this.documentoValidade = other.documentoValidade == null ? null : other.documentoValidade.copy();
        this.residencia = other.residencia == null ? null : other.residencia.copy();
        this.dataInicioFuncoes = other.dataInicioFuncoes == null ? null : other.dataInicioFuncoes.copy();
        this.telefonePrincipal = other.telefonePrincipal == null ? null : other.telefonePrincipal.copy();
        this.telefoneParente = other.telefoneParente == null ? null : other.telefoneParente.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.numeroAgente = other.numeroAgente == null ? null : other.numeroAgente.copy();
        this.temAgregacaoPedagogica = other.temAgregacaoPedagogica == null ? null : other.temAgregacaoPedagogica.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.ocorrenciaId = other.ocorrenciaId == null ? null : other.ocorrenciaId.copy();
        this.horariosId = other.horariosId == null ? null : other.horariosId.copy();
        this.planoAulaId = other.planoAulaId == null ? null : other.planoAulaId.copy();
        this.notasPeriodicaDisciplinaId = other.notasPeriodicaDisciplinaId == null ? null : other.notasPeriodicaDisciplinaId.copy();
        this.notasGeralDisciplinaId = other.notasGeralDisciplinaId == null ? null : other.notasGeralDisciplinaId.copy();
        this.dissertacaoFinalCursoId = other.dissertacaoFinalCursoId == null ? null : other.dissertacaoFinalCursoId.copy();
        this.categoriaOcorrenciaId = other.categoriaOcorrenciaId == null ? null : other.categoriaOcorrenciaId.copy();
        this.formacoesId = other.formacoesId == null ? null : other.formacoesId.copy();
        this.nacionalidadeId = other.nacionalidadeId == null ? null : other.nacionalidadeId.copy();
        this.naturalidadeId = other.naturalidadeId == null ? null : other.naturalidadeId.copy();
        this.tipoDocumentoId = other.tipoDocumentoId == null ? null : other.tipoDocumentoId.copy();
        this.grauAcademicoId = other.grauAcademicoId == null ? null : other.grauAcademicoId.copy();
        this.categoriaProfissionalId = other.categoriaProfissionalId == null ? null : other.categoriaProfissionalId.copy();
        this.unidadeOrganicaId = other.unidadeOrganicaId == null ? null : other.unidadeOrganicaId.copy();
        this.estadoCivilId = other.estadoCivilId == null ? null : other.estadoCivilId.copy();
        this.responsavelTurnoId = other.responsavelTurnoId == null ? null : other.responsavelTurnoId.copy();
        this.responsavelAreaFormacaoId = other.responsavelAreaFormacaoId == null ? null : other.responsavelAreaFormacaoId.copy();
        this.responsavelCursoId = other.responsavelCursoId == null ? null : other.responsavelCursoId.copy();
        this.responsavelDisciplinaId = other.responsavelDisciplinaId == null ? null : other.responsavelDisciplinaId.copy();
        this.responsavelTurmaId = other.responsavelTurmaId == null ? null : other.responsavelTurmaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DocenteCriteria copy() {
        return new DocenteCriteria(this);
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

    public StringFilter getInss() {
        return inss;
    }

    public StringFilter inss() {
        if (inss == null) {
            inss = new StringFilter();
        }
        return inss;
    }

    public void setInss(StringFilter inss) {
        this.inss = inss;
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

    public StringFilter getPai() {
        return pai;
    }

    public StringFilter pai() {
        if (pai == null) {
            pai = new StringFilter();
        }
        return pai;
    }

    public void setPai(StringFilter pai) {
        this.pai = pai;
    }

    public StringFilter getMae() {
        return mae;
    }

    public StringFilter mae() {
        if (mae == null) {
            mae = new StringFilter();
        }
        return mae;
    }

    public void setMae(StringFilter mae) {
        this.mae = mae;
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

    public LocalDateFilter getDocumentoEmissao() {
        return documentoEmissao;
    }

    public LocalDateFilter documentoEmissao() {
        if (documentoEmissao == null) {
            documentoEmissao = new LocalDateFilter();
        }
        return documentoEmissao;
    }

    public void setDocumentoEmissao(LocalDateFilter documentoEmissao) {
        this.documentoEmissao = documentoEmissao;
    }

    public LocalDateFilter getDocumentoValidade() {
        return documentoValidade;
    }

    public LocalDateFilter documentoValidade() {
        if (documentoValidade == null) {
            documentoValidade = new LocalDateFilter();
        }
        return documentoValidade;
    }

    public void setDocumentoValidade(LocalDateFilter documentoValidade) {
        this.documentoValidade = documentoValidade;
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

    public LocalDateFilter getDataInicioFuncoes() {
        return dataInicioFuncoes;
    }

    public LocalDateFilter dataInicioFuncoes() {
        if (dataInicioFuncoes == null) {
            dataInicioFuncoes = new LocalDateFilter();
        }
        return dataInicioFuncoes;
    }

    public void setDataInicioFuncoes(LocalDateFilter dataInicioFuncoes) {
        this.dataInicioFuncoes = dataInicioFuncoes;
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

    public StringFilter getTelefoneParente() {
        return telefoneParente;
    }

    public StringFilter telefoneParente() {
        if (telefoneParente == null) {
            telefoneParente = new StringFilter();
        }
        return telefoneParente;
    }

    public void setTelefoneParente(StringFilter telefoneParente) {
        this.telefoneParente = telefoneParente;
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

    public StringFilter getNumeroAgente() {
        return numeroAgente;
    }

    public StringFilter numeroAgente() {
        if (numeroAgente == null) {
            numeroAgente = new StringFilter();
        }
        return numeroAgente;
    }

    public void setNumeroAgente(StringFilter numeroAgente) {
        this.numeroAgente = numeroAgente;
    }

    public BooleanFilter getTemAgregacaoPedagogica() {
        return temAgregacaoPedagogica;
    }

    public BooleanFilter temAgregacaoPedagogica() {
        if (temAgregacaoPedagogica == null) {
            temAgregacaoPedagogica = new BooleanFilter();
        }
        return temAgregacaoPedagogica;
    }

    public void setTemAgregacaoPedagogica(BooleanFilter temAgregacaoPedagogica) {
        this.temAgregacaoPedagogica = temAgregacaoPedagogica;
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

    public ZonedDateTimeFilter getTimestamp() {
        return timestamp;
    }

    public ZonedDateTimeFilter timestamp() {
        if (timestamp == null) {
            timestamp = new ZonedDateTimeFilter();
        }
        return timestamp;
    }

    public void setTimestamp(ZonedDateTimeFilter timestamp) {
        this.timestamp = timestamp;
    }

    public LongFilter getOcorrenciaId() {
        return ocorrenciaId;
    }

    public LongFilter ocorrenciaId() {
        if (ocorrenciaId == null) {
            ocorrenciaId = new LongFilter();
        }
        return ocorrenciaId;
    }

    public void setOcorrenciaId(LongFilter ocorrenciaId) {
        this.ocorrenciaId = ocorrenciaId;
    }

    public LongFilter getHorariosId() {
        return horariosId;
    }

    public LongFilter horariosId() {
        if (horariosId == null) {
            horariosId = new LongFilter();
        }
        return horariosId;
    }

    public void setHorariosId(LongFilter horariosId) {
        this.horariosId = horariosId;
    }

    public LongFilter getPlanoAulaId() {
        return planoAulaId;
    }

    public LongFilter planoAulaId() {
        if (planoAulaId == null) {
            planoAulaId = new LongFilter();
        }
        return planoAulaId;
    }

    public void setPlanoAulaId(LongFilter planoAulaId) {
        this.planoAulaId = planoAulaId;
    }

    public LongFilter getNotasPeriodicaDisciplinaId() {
        return notasPeriodicaDisciplinaId;
    }

    public LongFilter notasPeriodicaDisciplinaId() {
        if (notasPeriodicaDisciplinaId == null) {
            notasPeriodicaDisciplinaId = new LongFilter();
        }
        return notasPeriodicaDisciplinaId;
    }

    public void setNotasPeriodicaDisciplinaId(LongFilter notasPeriodicaDisciplinaId) {
        this.notasPeriodicaDisciplinaId = notasPeriodicaDisciplinaId;
    }

    public LongFilter getNotasGeralDisciplinaId() {
        return notasGeralDisciplinaId;
    }

    public LongFilter notasGeralDisciplinaId() {
        if (notasGeralDisciplinaId == null) {
            notasGeralDisciplinaId = new LongFilter();
        }
        return notasGeralDisciplinaId;
    }

    public void setNotasGeralDisciplinaId(LongFilter notasGeralDisciplinaId) {
        this.notasGeralDisciplinaId = notasGeralDisciplinaId;
    }

    public LongFilter getDissertacaoFinalCursoId() {
        return dissertacaoFinalCursoId;
    }

    public LongFilter dissertacaoFinalCursoId() {
        if (dissertacaoFinalCursoId == null) {
            dissertacaoFinalCursoId = new LongFilter();
        }
        return dissertacaoFinalCursoId;
    }

    public void setDissertacaoFinalCursoId(LongFilter dissertacaoFinalCursoId) {
        this.dissertacaoFinalCursoId = dissertacaoFinalCursoId;
    }

    public LongFilter getCategoriaOcorrenciaId() {
        return categoriaOcorrenciaId;
    }

    public LongFilter categoriaOcorrenciaId() {
        if (categoriaOcorrenciaId == null) {
            categoriaOcorrenciaId = new LongFilter();
        }
        return categoriaOcorrenciaId;
    }

    public void setCategoriaOcorrenciaId(LongFilter categoriaOcorrenciaId) {
        this.categoriaOcorrenciaId = categoriaOcorrenciaId;
    }

    public LongFilter getFormacoesId() {
        return formacoesId;
    }

    public LongFilter formacoesId() {
        if (formacoesId == null) {
            formacoesId = new LongFilter();
        }
        return formacoesId;
    }

    public void setFormacoesId(LongFilter formacoesId) {
        this.formacoesId = formacoesId;
    }

    public LongFilter getNacionalidadeId() {
        return nacionalidadeId;
    }

    public LongFilter nacionalidadeId() {
        if (nacionalidadeId == null) {
            nacionalidadeId = new LongFilter();
        }
        return nacionalidadeId;
    }

    public void setNacionalidadeId(LongFilter nacionalidadeId) {
        this.nacionalidadeId = nacionalidadeId;
    }

    public LongFilter getNaturalidadeId() {
        return naturalidadeId;
    }

    public LongFilter naturalidadeId() {
        if (naturalidadeId == null) {
            naturalidadeId = new LongFilter();
        }
        return naturalidadeId;
    }

    public void setNaturalidadeId(LongFilter naturalidadeId) {
        this.naturalidadeId = naturalidadeId;
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

    public LongFilter getGrauAcademicoId() {
        return grauAcademicoId;
    }

    public LongFilter grauAcademicoId() {
        if (grauAcademicoId == null) {
            grauAcademicoId = new LongFilter();
        }
        return grauAcademicoId;
    }

    public void setGrauAcademicoId(LongFilter grauAcademicoId) {
        this.grauAcademicoId = grauAcademicoId;
    }

    public LongFilter getCategoriaProfissionalId() {
        return categoriaProfissionalId;
    }

    public LongFilter categoriaProfissionalId() {
        if (categoriaProfissionalId == null) {
            categoriaProfissionalId = new LongFilter();
        }
        return categoriaProfissionalId;
    }

    public void setCategoriaProfissionalId(LongFilter categoriaProfissionalId) {
        this.categoriaProfissionalId = categoriaProfissionalId;
    }

    public LongFilter getUnidadeOrganicaId() {
        return unidadeOrganicaId;
    }

    public LongFilter unidadeOrganicaId() {
        if (unidadeOrganicaId == null) {
            unidadeOrganicaId = new LongFilter();
        }
        return unidadeOrganicaId;
    }

    public void setUnidadeOrganicaId(LongFilter unidadeOrganicaId) {
        this.unidadeOrganicaId = unidadeOrganicaId;
    }

    public LongFilter getEstadoCivilId() {
        return estadoCivilId;
    }

    public LongFilter estadoCivilId() {
        if (estadoCivilId == null) {
            estadoCivilId = new LongFilter();
        }
        return estadoCivilId;
    }

    public void setEstadoCivilId(LongFilter estadoCivilId) {
        this.estadoCivilId = estadoCivilId;
    }

    public LongFilter getResponsavelTurnoId() {
        return responsavelTurnoId;
    }

    public LongFilter responsavelTurnoId() {
        if (responsavelTurnoId == null) {
            responsavelTurnoId = new LongFilter();
        }
        return responsavelTurnoId;
    }

    public void setResponsavelTurnoId(LongFilter responsavelTurnoId) {
        this.responsavelTurnoId = responsavelTurnoId;
    }

    public LongFilter getResponsavelAreaFormacaoId() {
        return responsavelAreaFormacaoId;
    }

    public LongFilter responsavelAreaFormacaoId() {
        if (responsavelAreaFormacaoId == null) {
            responsavelAreaFormacaoId = new LongFilter();
        }
        return responsavelAreaFormacaoId;
    }

    public void setResponsavelAreaFormacaoId(LongFilter responsavelAreaFormacaoId) {
        this.responsavelAreaFormacaoId = responsavelAreaFormacaoId;
    }

    public LongFilter getResponsavelCursoId() {
        return responsavelCursoId;
    }

    public LongFilter responsavelCursoId() {
        if (responsavelCursoId == null) {
            responsavelCursoId = new LongFilter();
        }
        return responsavelCursoId;
    }

    public void setResponsavelCursoId(LongFilter responsavelCursoId) {
        this.responsavelCursoId = responsavelCursoId;
    }

    public LongFilter getResponsavelDisciplinaId() {
        return responsavelDisciplinaId;
    }

    public LongFilter responsavelDisciplinaId() {
        if (responsavelDisciplinaId == null) {
            responsavelDisciplinaId = new LongFilter();
        }
        return responsavelDisciplinaId;
    }

    public void setResponsavelDisciplinaId(LongFilter responsavelDisciplinaId) {
        this.responsavelDisciplinaId = responsavelDisciplinaId;
    }

    public LongFilter getResponsavelTurmaId() {
        return responsavelTurmaId;
    }

    public LongFilter responsavelTurmaId() {
        if (responsavelTurmaId == null) {
            responsavelTurmaId = new LongFilter();
        }
        return responsavelTurmaId;
    }

    public void setResponsavelTurmaId(LongFilter responsavelTurmaId) {
        this.responsavelTurmaId = responsavelTurmaId;
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
        final DocenteCriteria that = (DocenteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(nascimento, that.nascimento) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(inss, that.inss) &&
            Objects.equals(sexo, that.sexo) &&
            Objects.equals(pai, that.pai) &&
            Objects.equals(mae, that.mae) &&
            Objects.equals(documentoNumero, that.documentoNumero) &&
            Objects.equals(documentoEmissao, that.documentoEmissao) &&
            Objects.equals(documentoValidade, that.documentoValidade) &&
            Objects.equals(residencia, that.residencia) &&
            Objects.equals(dataInicioFuncoes, that.dataInicioFuncoes) &&
            Objects.equals(telefonePrincipal, that.telefonePrincipal) &&
            Objects.equals(telefoneParente, that.telefoneParente) &&
            Objects.equals(email, that.email) &&
            Objects.equals(numeroAgente, that.numeroAgente) &&
            Objects.equals(temAgregacaoPedagogica, that.temAgregacaoPedagogica) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(ocorrenciaId, that.ocorrenciaId) &&
            Objects.equals(horariosId, that.horariosId) &&
            Objects.equals(planoAulaId, that.planoAulaId) &&
            Objects.equals(notasPeriodicaDisciplinaId, that.notasPeriodicaDisciplinaId) &&
            Objects.equals(notasGeralDisciplinaId, that.notasGeralDisciplinaId) &&
            Objects.equals(dissertacaoFinalCursoId, that.dissertacaoFinalCursoId) &&
            Objects.equals(categoriaOcorrenciaId, that.categoriaOcorrenciaId) &&
            Objects.equals(formacoesId, that.formacoesId) &&
            Objects.equals(nacionalidadeId, that.nacionalidadeId) &&
            Objects.equals(naturalidadeId, that.naturalidadeId) &&
            Objects.equals(tipoDocumentoId, that.tipoDocumentoId) &&
            Objects.equals(grauAcademicoId, that.grauAcademicoId) &&
            Objects.equals(categoriaProfissionalId, that.categoriaProfissionalId) &&
            Objects.equals(unidadeOrganicaId, that.unidadeOrganicaId) &&
            Objects.equals(estadoCivilId, that.estadoCivilId) &&
            Objects.equals(responsavelTurnoId, that.responsavelTurnoId) &&
            Objects.equals(responsavelAreaFormacaoId, that.responsavelAreaFormacaoId) &&
            Objects.equals(responsavelCursoId, that.responsavelCursoId) &&
            Objects.equals(responsavelDisciplinaId, that.responsavelDisciplinaId) &&
            Objects.equals(responsavelTurmaId, that.responsavelTurmaId) &&
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
            inss,
            sexo,
            pai,
            mae,
            documentoNumero,
            documentoEmissao,
            documentoValidade,
            residencia,
            dataInicioFuncoes,
            telefonePrincipal,
            telefoneParente,
            email,
            numeroAgente,
            temAgregacaoPedagogica,
            hash,
            timestamp,
            ocorrenciaId,
            horariosId,
            planoAulaId,
            notasPeriodicaDisciplinaId,
            notasGeralDisciplinaId,
            dissertacaoFinalCursoId,
            categoriaOcorrenciaId,
            formacoesId,
            nacionalidadeId,
            naturalidadeId,
            tipoDocumentoId,
            grauAcademicoId,
            categoriaProfissionalId,
            unidadeOrganicaId,
            estadoCivilId,
            responsavelTurnoId,
            responsavelAreaFormacaoId,
            responsavelCursoId,
            responsavelDisciplinaId,
            responsavelTurmaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocenteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (nascimento != null ? "nascimento=" + nascimento + ", " : "") +
            (nif != null ? "nif=" + nif + ", " : "") +
            (inss != null ? "inss=" + inss + ", " : "") +
            (sexo != null ? "sexo=" + sexo + ", " : "") +
            (pai != null ? "pai=" + pai + ", " : "") +
            (mae != null ? "mae=" + mae + ", " : "") +
            (documentoNumero != null ? "documentoNumero=" + documentoNumero + ", " : "") +
            (documentoEmissao != null ? "documentoEmissao=" + documentoEmissao + ", " : "") +
            (documentoValidade != null ? "documentoValidade=" + documentoValidade + ", " : "") +
            (residencia != null ? "residencia=" + residencia + ", " : "") +
            (dataInicioFuncoes != null ? "dataInicioFuncoes=" + dataInicioFuncoes + ", " : "") +
            (telefonePrincipal != null ? "telefonePrincipal=" + telefonePrincipal + ", " : "") +
            (telefoneParente != null ? "telefoneParente=" + telefoneParente + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (numeroAgente != null ? "numeroAgente=" + numeroAgente + ", " : "") +
            (temAgregacaoPedagogica != null ? "temAgregacaoPedagogica=" + temAgregacaoPedagogica + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (ocorrenciaId != null ? "ocorrenciaId=" + ocorrenciaId + ", " : "") +
            (horariosId != null ? "horariosId=" + horariosId + ", " : "") +
            (planoAulaId != null ? "planoAulaId=" + planoAulaId + ", " : "") +
            (notasPeriodicaDisciplinaId != null ? "notasPeriodicaDisciplinaId=" + notasPeriodicaDisciplinaId + ", " : "") +
            (notasGeralDisciplinaId != null ? "notasGeralDisciplinaId=" + notasGeralDisciplinaId + ", " : "") +
            (dissertacaoFinalCursoId != null ? "dissertacaoFinalCursoId=" + dissertacaoFinalCursoId + ", " : "") +
            (categoriaOcorrenciaId != null ? "categoriaOcorrenciaId=" + categoriaOcorrenciaId + ", " : "") +
            (formacoesId != null ? "formacoesId=" + formacoesId + ", " : "") +
            (nacionalidadeId != null ? "nacionalidadeId=" + nacionalidadeId + ", " : "") +
            (naturalidadeId != null ? "naturalidadeId=" + naturalidadeId + ", " : "") +
            (tipoDocumentoId != null ? "tipoDocumentoId=" + tipoDocumentoId + ", " : "") +
            (grauAcademicoId != null ? "grauAcademicoId=" + grauAcademicoId + ", " : "") +
            (categoriaProfissionalId != null ? "categoriaProfissionalId=" + categoriaProfissionalId + ", " : "") +
            (unidadeOrganicaId != null ? "unidadeOrganicaId=" + unidadeOrganicaId + ", " : "") +
            (estadoCivilId != null ? "estadoCivilId=" + estadoCivilId + ", " : "") +
            (responsavelTurnoId != null ? "responsavelTurnoId=" + responsavelTurnoId + ", " : "") +
            (responsavelAreaFormacaoId != null ? "responsavelAreaFormacaoId=" + responsavelAreaFormacaoId + ", " : "") +
            (responsavelCursoId != null ? "responsavelCursoId=" + responsavelCursoId + ", " : "") +
            (responsavelDisciplinaId != null ? "responsavelDisciplinaId=" + responsavelDisciplinaId + ", " : "") +
            (responsavelTurmaId != null ? "responsavelTurmaId=" + responsavelTurmaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
