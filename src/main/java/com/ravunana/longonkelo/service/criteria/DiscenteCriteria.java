package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.Sexo;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Discente} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.DiscenteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /discentes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiscenteCriteria implements Serializable, Criteria {

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

    private StringFilter documentoNumero;

    private LocalDateFilter documentoEmissao;

    private LocalDateFilter documentoValidade;

    private StringFilter nif;

    private SexoFilter sexo;

    private StringFilter pai;

    private StringFilter mae;

    private StringFilter telefonePrincipal;

    private StringFilter telefoneParente;

    private StringFilter email;

    private BooleanFilter isEncarregadoEducacao;

    private BooleanFilter isTrabalhador;

    private BooleanFilter isFilhoAntigoConbatente;

    private BooleanFilter isAtestadoPobreza;

    private StringFilter nomeMedico;

    private StringFilter telefoneMedico;

    private StringFilter instituicaoParticularSaude;

    private IntegerFilter altura;

    private DoubleFilter peso;

    private BooleanFilter isAsmatico;

    private BooleanFilter isAlergico;

    private BooleanFilter isPraticaEducacaoFisica;

    private BooleanFilter isAutorizadoMedicacao;

    private StringFilter numeroProcesso;

    private ZonedDateTimeFilter dataIngresso;

    private StringFilter hash;

    private LongFilter enderecosId;

    private LongFilter processosSelectivoId;

    private LongFilter anexoDiscenteId;

    private LongFilter matriculasId;

    private LongFilter resumoAcademicoId;

    private LongFilter historicosSaudeId;

    private LongFilter dissertacaoFinalCursoId;

    private LongFilter nacionalidadeId;

    private LongFilter naturalidadeId;

    private LongFilter tipoDocumentoId;

    private LongFilter profissaoId;

    private LongFilter grupoSanguinioId;

    private LongFilter necessidadeEspecialId;

    private LongFilter encarregadoEducacaoId;

    private Boolean distinct;

    public DiscenteCriteria() {}

    public DiscenteCriteria(DiscenteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.nascimento = other.nascimento == null ? null : other.nascimento.copy();
        this.documentoNumero = other.documentoNumero == null ? null : other.documentoNumero.copy();
        this.documentoEmissao = other.documentoEmissao == null ? null : other.documentoEmissao.copy();
        this.documentoValidade = other.documentoValidade == null ? null : other.documentoValidade.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.sexo = other.sexo == null ? null : other.sexo.copy();
        this.pai = other.pai == null ? null : other.pai.copy();
        this.mae = other.mae == null ? null : other.mae.copy();
        this.telefonePrincipal = other.telefonePrincipal == null ? null : other.telefonePrincipal.copy();
        this.telefoneParente = other.telefoneParente == null ? null : other.telefoneParente.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.isEncarregadoEducacao = other.isEncarregadoEducacao == null ? null : other.isEncarregadoEducacao.copy();
        this.isTrabalhador = other.isTrabalhador == null ? null : other.isTrabalhador.copy();
        this.isFilhoAntigoConbatente = other.isFilhoAntigoConbatente == null ? null : other.isFilhoAntigoConbatente.copy();
        this.isAtestadoPobreza = other.isAtestadoPobreza == null ? null : other.isAtestadoPobreza.copy();
        this.nomeMedico = other.nomeMedico == null ? null : other.nomeMedico.copy();
        this.telefoneMedico = other.telefoneMedico == null ? null : other.telefoneMedico.copy();
        this.instituicaoParticularSaude = other.instituicaoParticularSaude == null ? null : other.instituicaoParticularSaude.copy();
        this.altura = other.altura == null ? null : other.altura.copy();
        this.peso = other.peso == null ? null : other.peso.copy();
        this.isAsmatico = other.isAsmatico == null ? null : other.isAsmatico.copy();
        this.isAlergico = other.isAlergico == null ? null : other.isAlergico.copy();
        this.isPraticaEducacaoFisica = other.isPraticaEducacaoFisica == null ? null : other.isPraticaEducacaoFisica.copy();
        this.isAutorizadoMedicacao = other.isAutorizadoMedicacao == null ? null : other.isAutorizadoMedicacao.copy();
        this.numeroProcesso = other.numeroProcesso == null ? null : other.numeroProcesso.copy();
        this.dataIngresso = other.dataIngresso == null ? null : other.dataIngresso.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.enderecosId = other.enderecosId == null ? null : other.enderecosId.copy();
        this.processosSelectivoId = other.processosSelectivoId == null ? null : other.processosSelectivoId.copy();
        this.anexoDiscenteId = other.anexoDiscenteId == null ? null : other.anexoDiscenteId.copy();
        this.matriculasId = other.matriculasId == null ? null : other.matriculasId.copy();
        this.resumoAcademicoId = other.resumoAcademicoId == null ? null : other.resumoAcademicoId.copy();
        this.historicosSaudeId = other.historicosSaudeId == null ? null : other.historicosSaudeId.copy();
        this.dissertacaoFinalCursoId = other.dissertacaoFinalCursoId == null ? null : other.dissertacaoFinalCursoId.copy();
        this.nacionalidadeId = other.nacionalidadeId == null ? null : other.nacionalidadeId.copy();
        this.naturalidadeId = other.naturalidadeId == null ? null : other.naturalidadeId.copy();
        this.tipoDocumentoId = other.tipoDocumentoId == null ? null : other.tipoDocumentoId.copy();
        this.profissaoId = other.profissaoId == null ? null : other.profissaoId.copy();
        this.grupoSanguinioId = other.grupoSanguinioId == null ? null : other.grupoSanguinioId.copy();
        this.necessidadeEspecialId = other.necessidadeEspecialId == null ? null : other.necessidadeEspecialId.copy();
        this.encarregadoEducacaoId = other.encarregadoEducacaoId == null ? null : other.encarregadoEducacaoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DiscenteCriteria copy() {
        return new DiscenteCriteria(this);
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

    public BooleanFilter getIsEncarregadoEducacao() {
        return isEncarregadoEducacao;
    }

    public BooleanFilter isEncarregadoEducacao() {
        if (isEncarregadoEducacao == null) {
            isEncarregadoEducacao = new BooleanFilter();
        }
        return isEncarregadoEducacao;
    }

    public void setIsEncarregadoEducacao(BooleanFilter isEncarregadoEducacao) {
        this.isEncarregadoEducacao = isEncarregadoEducacao;
    }

    public BooleanFilter getIsTrabalhador() {
        return isTrabalhador;
    }

    public BooleanFilter isTrabalhador() {
        if (isTrabalhador == null) {
            isTrabalhador = new BooleanFilter();
        }
        return isTrabalhador;
    }

    public void setIsTrabalhador(BooleanFilter isTrabalhador) {
        this.isTrabalhador = isTrabalhador;
    }

    public BooleanFilter getIsFilhoAntigoConbatente() {
        return isFilhoAntigoConbatente;
    }

    public BooleanFilter isFilhoAntigoConbatente() {
        if (isFilhoAntigoConbatente == null) {
            isFilhoAntigoConbatente = new BooleanFilter();
        }
        return isFilhoAntigoConbatente;
    }

    public void setIsFilhoAntigoConbatente(BooleanFilter isFilhoAntigoConbatente) {
        this.isFilhoAntigoConbatente = isFilhoAntigoConbatente;
    }

    public BooleanFilter getIsAtestadoPobreza() {
        return isAtestadoPobreza;
    }

    public BooleanFilter isAtestadoPobreza() {
        if (isAtestadoPobreza == null) {
            isAtestadoPobreza = new BooleanFilter();
        }
        return isAtestadoPobreza;
    }

    public void setIsAtestadoPobreza(BooleanFilter isAtestadoPobreza) {
        this.isAtestadoPobreza = isAtestadoPobreza;
    }

    public StringFilter getNomeMedico() {
        return nomeMedico;
    }

    public StringFilter nomeMedico() {
        if (nomeMedico == null) {
            nomeMedico = new StringFilter();
        }
        return nomeMedico;
    }

    public void setNomeMedico(StringFilter nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public StringFilter getTelefoneMedico() {
        return telefoneMedico;
    }

    public StringFilter telefoneMedico() {
        if (telefoneMedico == null) {
            telefoneMedico = new StringFilter();
        }
        return telefoneMedico;
    }

    public void setTelefoneMedico(StringFilter telefoneMedico) {
        this.telefoneMedico = telefoneMedico;
    }

    public StringFilter getInstituicaoParticularSaude() {
        return instituicaoParticularSaude;
    }

    public StringFilter instituicaoParticularSaude() {
        if (instituicaoParticularSaude == null) {
            instituicaoParticularSaude = new StringFilter();
        }
        return instituicaoParticularSaude;
    }

    public void setInstituicaoParticularSaude(StringFilter instituicaoParticularSaude) {
        this.instituicaoParticularSaude = instituicaoParticularSaude;
    }

    public IntegerFilter getAltura() {
        return altura;
    }

    public IntegerFilter altura() {
        if (altura == null) {
            altura = new IntegerFilter();
        }
        return altura;
    }

    public void setAltura(IntegerFilter altura) {
        this.altura = altura;
    }

    public DoubleFilter getPeso() {
        return peso;
    }

    public DoubleFilter peso() {
        if (peso == null) {
            peso = new DoubleFilter();
        }
        return peso;
    }

    public void setPeso(DoubleFilter peso) {
        this.peso = peso;
    }

    public BooleanFilter getIsAsmatico() {
        return isAsmatico;
    }

    public BooleanFilter isAsmatico() {
        if (isAsmatico == null) {
            isAsmatico = new BooleanFilter();
        }
        return isAsmatico;
    }

    public void setIsAsmatico(BooleanFilter isAsmatico) {
        this.isAsmatico = isAsmatico;
    }

    public BooleanFilter getIsAlergico() {
        return isAlergico;
    }

    public BooleanFilter isAlergico() {
        if (isAlergico == null) {
            isAlergico = new BooleanFilter();
        }
        return isAlergico;
    }

    public void setIsAlergico(BooleanFilter isAlergico) {
        this.isAlergico = isAlergico;
    }

    public BooleanFilter getIsPraticaEducacaoFisica() {
        return isPraticaEducacaoFisica;
    }

    public BooleanFilter isPraticaEducacaoFisica() {
        if (isPraticaEducacaoFisica == null) {
            isPraticaEducacaoFisica = new BooleanFilter();
        }
        return isPraticaEducacaoFisica;
    }

    public void setIsPraticaEducacaoFisica(BooleanFilter isPraticaEducacaoFisica) {
        this.isPraticaEducacaoFisica = isPraticaEducacaoFisica;
    }

    public BooleanFilter getIsAutorizadoMedicacao() {
        return isAutorizadoMedicacao;
    }

    public BooleanFilter isAutorizadoMedicacao() {
        if (isAutorizadoMedicacao == null) {
            isAutorizadoMedicacao = new BooleanFilter();
        }
        return isAutorizadoMedicacao;
    }

    public void setIsAutorizadoMedicacao(BooleanFilter isAutorizadoMedicacao) {
        this.isAutorizadoMedicacao = isAutorizadoMedicacao;
    }

    public StringFilter getNumeroProcesso() {
        return numeroProcesso;
    }

    public StringFilter numeroProcesso() {
        if (numeroProcesso == null) {
            numeroProcesso = new StringFilter();
        }
        return numeroProcesso;
    }

    public void setNumeroProcesso(StringFilter numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public ZonedDateTimeFilter getDataIngresso() {
        return dataIngresso;
    }

    public ZonedDateTimeFilter dataIngresso() {
        if (dataIngresso == null) {
            dataIngresso = new ZonedDateTimeFilter();
        }
        return dataIngresso;
    }

    public void setDataIngresso(ZonedDateTimeFilter dataIngresso) {
        this.dataIngresso = dataIngresso;
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

    public LongFilter getEnderecosId() {
        return enderecosId;
    }

    public LongFilter enderecosId() {
        if (enderecosId == null) {
            enderecosId = new LongFilter();
        }
        return enderecosId;
    }

    public void setEnderecosId(LongFilter enderecosId) {
        this.enderecosId = enderecosId;
    }

    public LongFilter getProcessosSelectivoId() {
        return processosSelectivoId;
    }

    public LongFilter processosSelectivoId() {
        if (processosSelectivoId == null) {
            processosSelectivoId = new LongFilter();
        }
        return processosSelectivoId;
    }

    public void setProcessosSelectivoId(LongFilter processosSelectivoId) {
        this.processosSelectivoId = processosSelectivoId;
    }

    public LongFilter getAnexoDiscenteId() {
        return anexoDiscenteId;
    }

    public LongFilter anexoDiscenteId() {
        if (anexoDiscenteId == null) {
            anexoDiscenteId = new LongFilter();
        }
        return anexoDiscenteId;
    }

    public void setAnexoDiscenteId(LongFilter anexoDiscenteId) {
        this.anexoDiscenteId = anexoDiscenteId;
    }

    public LongFilter getMatriculasId() {
        return matriculasId;
    }

    public LongFilter matriculasId() {
        if (matriculasId == null) {
            matriculasId = new LongFilter();
        }
        return matriculasId;
    }

    public void setMatriculasId(LongFilter matriculasId) {
        this.matriculasId = matriculasId;
    }

    public LongFilter getResumoAcademicoId() {
        return resumoAcademicoId;
    }

    public LongFilter resumoAcademicoId() {
        if (resumoAcademicoId == null) {
            resumoAcademicoId = new LongFilter();
        }
        return resumoAcademicoId;
    }

    public void setResumoAcademicoId(LongFilter resumoAcademicoId) {
        this.resumoAcademicoId = resumoAcademicoId;
    }

    public LongFilter getHistoricosSaudeId() {
        return historicosSaudeId;
    }

    public LongFilter historicosSaudeId() {
        if (historicosSaudeId == null) {
            historicosSaudeId = new LongFilter();
        }
        return historicosSaudeId;
    }

    public void setHistoricosSaudeId(LongFilter historicosSaudeId) {
        this.historicosSaudeId = historicosSaudeId;
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

    public LongFilter getGrupoSanguinioId() {
        return grupoSanguinioId;
    }

    public LongFilter grupoSanguinioId() {
        if (grupoSanguinioId == null) {
            grupoSanguinioId = new LongFilter();
        }
        return grupoSanguinioId;
    }

    public void setGrupoSanguinioId(LongFilter grupoSanguinioId) {
        this.grupoSanguinioId = grupoSanguinioId;
    }

    public LongFilter getNecessidadeEspecialId() {
        return necessidadeEspecialId;
    }

    public LongFilter necessidadeEspecialId() {
        if (necessidadeEspecialId == null) {
            necessidadeEspecialId = new LongFilter();
        }
        return necessidadeEspecialId;
    }

    public void setNecessidadeEspecialId(LongFilter necessidadeEspecialId) {
        this.necessidadeEspecialId = necessidadeEspecialId;
    }

    public LongFilter getEncarregadoEducacaoId() {
        return encarregadoEducacaoId;
    }

    public LongFilter encarregadoEducacaoId() {
        if (encarregadoEducacaoId == null) {
            encarregadoEducacaoId = new LongFilter();
        }
        return encarregadoEducacaoId;
    }

    public void setEncarregadoEducacaoId(LongFilter encarregadoEducacaoId) {
        this.encarregadoEducacaoId = encarregadoEducacaoId;
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
        final DiscenteCriteria that = (DiscenteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(nascimento, that.nascimento) &&
            Objects.equals(documentoNumero, that.documentoNumero) &&
            Objects.equals(documentoEmissao, that.documentoEmissao) &&
            Objects.equals(documentoValidade, that.documentoValidade) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(sexo, that.sexo) &&
            Objects.equals(pai, that.pai) &&
            Objects.equals(mae, that.mae) &&
            Objects.equals(telefonePrincipal, that.telefonePrincipal) &&
            Objects.equals(telefoneParente, that.telefoneParente) &&
            Objects.equals(email, that.email) &&
            Objects.equals(isEncarregadoEducacao, that.isEncarregadoEducacao) &&
            Objects.equals(isTrabalhador, that.isTrabalhador) &&
            Objects.equals(isFilhoAntigoConbatente, that.isFilhoAntigoConbatente) &&
            Objects.equals(isAtestadoPobreza, that.isAtestadoPobreza) &&
            Objects.equals(nomeMedico, that.nomeMedico) &&
            Objects.equals(telefoneMedico, that.telefoneMedico) &&
            Objects.equals(instituicaoParticularSaude, that.instituicaoParticularSaude) &&
            Objects.equals(altura, that.altura) &&
            Objects.equals(peso, that.peso) &&
            Objects.equals(isAsmatico, that.isAsmatico) &&
            Objects.equals(isAlergico, that.isAlergico) &&
            Objects.equals(isPraticaEducacaoFisica, that.isPraticaEducacaoFisica) &&
            Objects.equals(isAutorizadoMedicacao, that.isAutorizadoMedicacao) &&
            Objects.equals(numeroProcesso, that.numeroProcesso) &&
            Objects.equals(dataIngresso, that.dataIngresso) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(enderecosId, that.enderecosId) &&
            Objects.equals(processosSelectivoId, that.processosSelectivoId) &&
            Objects.equals(anexoDiscenteId, that.anexoDiscenteId) &&
            Objects.equals(matriculasId, that.matriculasId) &&
            Objects.equals(resumoAcademicoId, that.resumoAcademicoId) &&
            Objects.equals(historicosSaudeId, that.historicosSaudeId) &&
            Objects.equals(dissertacaoFinalCursoId, that.dissertacaoFinalCursoId) &&
            Objects.equals(nacionalidadeId, that.nacionalidadeId) &&
            Objects.equals(naturalidadeId, that.naturalidadeId) &&
            Objects.equals(tipoDocumentoId, that.tipoDocumentoId) &&
            Objects.equals(profissaoId, that.profissaoId) &&
            Objects.equals(grupoSanguinioId, that.grupoSanguinioId) &&
            Objects.equals(necessidadeEspecialId, that.necessidadeEspecialId) &&
            Objects.equals(encarregadoEducacaoId, that.encarregadoEducacaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            nascimento,
            documentoNumero,
            documentoEmissao,
            documentoValidade,
            nif,
            sexo,
            pai,
            mae,
            telefonePrincipal,
            telefoneParente,
            email,
            isEncarregadoEducacao,
            isTrabalhador,
            isFilhoAntigoConbatente,
            isAtestadoPobreza,
            nomeMedico,
            telefoneMedico,
            instituicaoParticularSaude,
            altura,
            peso,
            isAsmatico,
            isAlergico,
            isPraticaEducacaoFisica,
            isAutorizadoMedicacao,
            numeroProcesso,
            dataIngresso,
            hash,
            enderecosId,
            processosSelectivoId,
            anexoDiscenteId,
            matriculasId,
            resumoAcademicoId,
            historicosSaudeId,
            dissertacaoFinalCursoId,
            nacionalidadeId,
            naturalidadeId,
            tipoDocumentoId,
            profissaoId,
            grupoSanguinioId,
            necessidadeEspecialId,
            encarregadoEducacaoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscenteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (nascimento != null ? "nascimento=" + nascimento + ", " : "") +
            (documentoNumero != null ? "documentoNumero=" + documentoNumero + ", " : "") +
            (documentoEmissao != null ? "documentoEmissao=" + documentoEmissao + ", " : "") +
            (documentoValidade != null ? "documentoValidade=" + documentoValidade + ", " : "") +
            (nif != null ? "nif=" + nif + ", " : "") +
            (sexo != null ? "sexo=" + sexo + ", " : "") +
            (pai != null ? "pai=" + pai + ", " : "") +
            (mae != null ? "mae=" + mae + ", " : "") +
            (telefonePrincipal != null ? "telefonePrincipal=" + telefonePrincipal + ", " : "") +
            (telefoneParente != null ? "telefoneParente=" + telefoneParente + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (isEncarregadoEducacao != null ? "isEncarregadoEducacao=" + isEncarregadoEducacao + ", " : "") +
            (isTrabalhador != null ? "isTrabalhador=" + isTrabalhador + ", " : "") +
            (isFilhoAntigoConbatente != null ? "isFilhoAntigoConbatente=" + isFilhoAntigoConbatente + ", " : "") +
            (isAtestadoPobreza != null ? "isAtestadoPobreza=" + isAtestadoPobreza + ", " : "") +
            (nomeMedico != null ? "nomeMedico=" + nomeMedico + ", " : "") +
            (telefoneMedico != null ? "telefoneMedico=" + telefoneMedico + ", " : "") +
            (instituicaoParticularSaude != null ? "instituicaoParticularSaude=" + instituicaoParticularSaude + ", " : "") +
            (altura != null ? "altura=" + altura + ", " : "") +
            (peso != null ? "peso=" + peso + ", " : "") +
            (isAsmatico != null ? "isAsmatico=" + isAsmatico + ", " : "") +
            (isAlergico != null ? "isAlergico=" + isAlergico + ", " : "") +
            (isPraticaEducacaoFisica != null ? "isPraticaEducacaoFisica=" + isPraticaEducacaoFisica + ", " : "") +
            (isAutorizadoMedicacao != null ? "isAutorizadoMedicacao=" + isAutorizadoMedicacao + ", " : "") +
            (numeroProcesso != null ? "numeroProcesso=" + numeroProcesso + ", " : "") +
            (dataIngresso != null ? "dataIngresso=" + dataIngresso + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (enderecosId != null ? "enderecosId=" + enderecosId + ", " : "") +
            (processosSelectivoId != null ? "processosSelectivoId=" + processosSelectivoId + ", " : "") +
            (anexoDiscenteId != null ? "anexoDiscenteId=" + anexoDiscenteId + ", " : "") +
            (matriculasId != null ? "matriculasId=" + matriculasId + ", " : "") +
            (resumoAcademicoId != null ? "resumoAcademicoId=" + resumoAcademicoId + ", " : "") +
            (historicosSaudeId != null ? "historicosSaudeId=" + historicosSaudeId + ", " : "") +
            (dissertacaoFinalCursoId != null ? "dissertacaoFinalCursoId=" + dissertacaoFinalCursoId + ", " : "") +
            (nacionalidadeId != null ? "nacionalidadeId=" + nacionalidadeId + ", " : "") +
            (naturalidadeId != null ? "naturalidadeId=" + naturalidadeId + ", " : "") +
            (tipoDocumentoId != null ? "tipoDocumentoId=" + tipoDocumentoId + ", " : "") +
            (profissaoId != null ? "profissaoId=" + profissaoId + ", " : "") +
            (grupoSanguinioId != null ? "grupoSanguinioId=" + grupoSanguinioId + ", " : "") +
            (necessidadeEspecialId != null ? "necessidadeEspecialId=" + necessidadeEspecialId + ", " : "") +
            (encarregadoEducacaoId != null ? "encarregadoEducacaoId=" + encarregadoEducacaoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
