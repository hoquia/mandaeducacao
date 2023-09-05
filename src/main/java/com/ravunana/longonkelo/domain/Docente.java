package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.Sexo;
import java.io.Serializable;
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
 * A Docente.
 */
@Entity
@Table(name = "docente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "fotografia")
    private byte[] fotografia;

    @Column(name = "fotografia_content_type")
    private String fotografiaContentType;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "nascimento", nullable = false)
    private LocalDate nascimento;

    @Column(name = "nif", unique = true)
    private String nif;

    @Column(name = "inss", unique = true)
    private String inss;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    @NotNull
    @Column(name = "pai", nullable = false)
    private String pai;

    @NotNull
    @Column(name = "mae", nullable = false)
    private String mae;

    @NotNull
    @Column(name = "documento_numero", nullable = false, unique = true)
    private String documentoNumero;

    @NotNull
    @Column(name = "documento_emissao", nullable = false)
    private LocalDate documentoEmissao;

    @NotNull
    @Column(name = "documento_validade", nullable = false)
    private LocalDate documentoValidade;

    @NotNull
    @Column(name = "residencia", nullable = false)
    private String residencia;

    @NotNull
    @Column(name = "data_inicio_funcoes", nullable = false)
    private LocalDate dataInicioFuncoes;

    @NotNull
    @Column(name = "telefone_principal", nullable = false, unique = true)
    private String telefonePrincipal;

    @Column(name = "telefone_parente")
    private String telefoneParente;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "numero_agente", unique = true)
    private String numeroAgente;

    @Column(name = "tem_agregacao_pedagogica")
    private Boolean temAgregacaoPedagogica;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao")
    private String observacao;

    @Column(name = "hash")
    private String hash;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @OneToMany(mappedBy = "docente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "ocorrencias", "anoLectivos", "utilizador", "referencia", "docente", "matricula", "estado", "licao" },
        allowSetters = true
    )
    private Set<Ocorrencia> ocorrencias = new HashSet<>();

    @OneToMany(mappedBy = "docente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "horarios", "licaos", "anoLectivos", "utilizador", "turma", "referencia", "periodo", "docente", "disciplinaCurricular" },
        allowSetters = true
    )
    private Set<Horario> horarios = new HashSet<>();

    @OneToMany(mappedBy = "docente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "detalhes",
            "licaos",
            "anoLectivos",
            "utilizador",
            "unidadeTematica",
            "subUnidadeTematica",
            "turma",
            "docente",
            "disciplinaCurricular",
        },
        allowSetters = true
    )
    private Set<PlanoAula> planoAulas = new HashSet<>();

    @OneToMany(mappedBy = "docente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "docente", "disciplinaCurricular", "matricula", "estado" },
        allowSetters = true
    )
    private Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas = new HashSet<>();

    @OneToMany(mappedBy = "docente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "docente", "disciplinaCurricular", "matricula", "estado" },
        allowSetters = true
    )
    private Set<NotasGeralDisciplina> notasGeralDisciplinas = new HashSet<>();

    @OneToMany(mappedBy = "orientador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "orientador", "especialidade", "discente", "estado", "natureza" },
        allowSetters = true
    )
    private Set<DissertacaoFinalCurso> dissertacaoFinalCursos = new HashSet<>();

    @OneToMany(mappedBy = "encaminhar")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "categoriaOcorrencias", "ocorrencias", "encaminhar", "referencia", "medidaDisciplinar" },
        allowSetters = true
    )
    private Set<CategoriaOcorrencia> categoriaOcorrencias = new HashSet<>();

    @OneToMany(mappedBy = "docente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "grauAcademico", "docente" }, allowSetters = true)
    private Set<FormacaoDocente> formacoes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem nacionalidade;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem naturalidade;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem tipoDocumento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem grauAcademico;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem categoriaProfissional;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem unidadeOrganica;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem estadoCivil;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "turno" }, allowSetters = true)
    private ResponsavelTurno responsavelTurno;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "areaFormacao" }, allowSetters = true)
    private ResponsavelAreaFormacao responsavelAreaFormacao;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "curso" }, allowSetters = true)
    private ResponsavelCurso responsavelCurso;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "disciplina" }, allowSetters = true)
    private ResponsavelDisciplina responsavelDisciplina;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "turma" }, allowSetters = true)
    private ResponsavelTurma responsavelTurma;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Docente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFotografia() {
        return this.fotografia;
    }

    public Docente fotografia(byte[] fotografia) {
        this.setFotografia(fotografia);
        return this;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public String getFotografiaContentType() {
        return this.fotografiaContentType;
    }

    public Docente fotografiaContentType(String fotografiaContentType) {
        this.fotografiaContentType = fotografiaContentType;
        return this;
    }

    public void setFotografiaContentType(String fotografiaContentType) {
        this.fotografiaContentType = fotografiaContentType;
    }

    public String getNome() {
        return this.nome;
    }

    public Docente nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return this.nascimento;
    }

    public Docente nascimento(LocalDate nascimento) {
        this.setNascimento(nascimento);
        return this;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getNif() {
        return this.nif;
    }

    public Docente nif(String nif) {
        this.setNif(nif);
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getInss() {
        return this.inss;
    }

    public Docente inss(String inss) {
        this.setInss(inss);
        return this;
    }

    public void setInss(String inss) {
        this.inss = inss;
    }

    public Sexo getSexo() {
        return this.sexo;
    }

    public Docente sexo(Sexo sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getPai() {
        return this.pai;
    }

    public Docente pai(String pai) {
        this.setPai(pai);
        return this;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getMae() {
        return this.mae;
    }

    public Docente mae(String mae) {
        this.setMae(mae);
        return this;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getDocumentoNumero() {
        return this.documentoNumero;
    }

    public Docente documentoNumero(String documentoNumero) {
        this.setDocumentoNumero(documentoNumero);
        return this;
    }

    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    public LocalDate getDocumentoEmissao() {
        return this.documentoEmissao;
    }

    public Docente documentoEmissao(LocalDate documentoEmissao) {
        this.setDocumentoEmissao(documentoEmissao);
        return this;
    }

    public void setDocumentoEmissao(LocalDate documentoEmissao) {
        this.documentoEmissao = documentoEmissao;
    }

    public LocalDate getDocumentoValidade() {
        return this.documentoValidade;
    }

    public Docente documentoValidade(LocalDate documentoValidade) {
        this.setDocumentoValidade(documentoValidade);
        return this;
    }

    public void setDocumentoValidade(LocalDate documentoValidade) {
        this.documentoValidade = documentoValidade;
    }

    public String getResidencia() {
        return this.residencia;
    }

    public Docente residencia(String residencia) {
        this.setResidencia(residencia);
        return this;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public LocalDate getDataInicioFuncoes() {
        return this.dataInicioFuncoes;
    }

    public Docente dataInicioFuncoes(LocalDate dataInicioFuncoes) {
        this.setDataInicioFuncoes(dataInicioFuncoes);
        return this;
    }

    public void setDataInicioFuncoes(LocalDate dataInicioFuncoes) {
        this.dataInicioFuncoes = dataInicioFuncoes;
    }

    public String getTelefonePrincipal() {
        return this.telefonePrincipal;
    }

    public Docente telefonePrincipal(String telefonePrincipal) {
        this.setTelefonePrincipal(telefonePrincipal);
        return this;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneParente() {
        return this.telefoneParente;
    }

    public Docente telefoneParente(String telefoneParente) {
        this.setTelefoneParente(telefoneParente);
        return this;
    }

    public void setTelefoneParente(String telefoneParente) {
        this.telefoneParente = telefoneParente;
    }

    public String getEmail() {
        return this.email;
    }

    public Docente email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroAgente() {
        return this.numeroAgente;
    }

    public Docente numeroAgente(String numeroAgente) {
        this.setNumeroAgente(numeroAgente);
        return this;
    }

    public void setNumeroAgente(String numeroAgente) {
        this.numeroAgente = numeroAgente;
    }

    public Boolean getTemAgregacaoPedagogica() {
        return this.temAgregacaoPedagogica;
    }

    public Docente temAgregacaoPedagogica(Boolean temAgregacaoPedagogica) {
        this.setTemAgregacaoPedagogica(temAgregacaoPedagogica);
        return this;
    }

    public void setTemAgregacaoPedagogica(Boolean temAgregacaoPedagogica) {
        this.temAgregacaoPedagogica = temAgregacaoPedagogica;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public Docente observacao(String observacao) {
        this.setObservacao(observacao);
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getHash() {
        return this.hash;
    }

    public Docente hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public Docente timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Set<Ocorrencia> getOcorrencias() {
        return this.ocorrencias;
    }

    public void setOcorrencias(Set<Ocorrencia> ocorrencias) {
        if (this.ocorrencias != null) {
            this.ocorrencias.forEach(i -> i.setDocente(null));
        }
        if (ocorrencias != null) {
            ocorrencias.forEach(i -> i.setDocente(this));
        }
        this.ocorrencias = ocorrencias;
    }

    public Docente ocorrencias(Set<Ocorrencia> ocorrencias) {
        this.setOcorrencias(ocorrencias);
        return this;
    }

    public Docente addOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
        ocorrencia.setDocente(this);
        return this;
    }

    public Docente removeOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.remove(ocorrencia);
        ocorrencia.setDocente(null);
        return this;
    }

    public Set<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        if (this.horarios != null) {
            this.horarios.forEach(i -> i.setDocente(null));
        }
        if (horarios != null) {
            horarios.forEach(i -> i.setDocente(this));
        }
        this.horarios = horarios;
    }

    public Docente horarios(Set<Horario> horarios) {
        this.setHorarios(horarios);
        return this;
    }

    public Docente addHorarios(Horario horario) {
        this.horarios.add(horario);
        horario.setDocente(this);
        return this;
    }

    public Docente removeHorarios(Horario horario) {
        this.horarios.remove(horario);
        horario.setDocente(null);
        return this;
    }

    public Set<PlanoAula> getPlanoAulas() {
        return this.planoAulas;
    }

    public void setPlanoAulas(Set<PlanoAula> planoAulas) {
        if (this.planoAulas != null) {
            this.planoAulas.forEach(i -> i.setDocente(null));
        }
        if (planoAulas != null) {
            planoAulas.forEach(i -> i.setDocente(this));
        }
        this.planoAulas = planoAulas;
    }

    public Docente planoAulas(Set<PlanoAula> planoAulas) {
        this.setPlanoAulas(planoAulas);
        return this;
    }

    public Docente addPlanoAula(PlanoAula planoAula) {
        this.planoAulas.add(planoAula);
        planoAula.setDocente(this);
        return this;
    }

    public Docente removePlanoAula(PlanoAula planoAula) {
        this.planoAulas.remove(planoAula);
        planoAula.setDocente(null);
        return this;
    }

    public Set<NotasPeriodicaDisciplina> getNotasPeriodicaDisciplinas() {
        return this.notasPeriodicaDisciplinas;
    }

    public void setNotasPeriodicaDisciplinas(Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas) {
        if (this.notasPeriodicaDisciplinas != null) {
            this.notasPeriodicaDisciplinas.forEach(i -> i.setDocente(null));
        }
        if (notasPeriodicaDisciplinas != null) {
            notasPeriodicaDisciplinas.forEach(i -> i.setDocente(this));
        }
        this.notasPeriodicaDisciplinas = notasPeriodicaDisciplinas;
    }

    public Docente notasPeriodicaDisciplinas(Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas) {
        this.setNotasPeriodicaDisciplinas(notasPeriodicaDisciplinas);
        return this;
    }

    public Docente addNotasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplinas.add(notasPeriodicaDisciplina);
        notasPeriodicaDisciplina.setDocente(this);
        return this;
    }

    public Docente removeNotasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplinas.remove(notasPeriodicaDisciplina);
        notasPeriodicaDisciplina.setDocente(null);
        return this;
    }

    public Set<NotasGeralDisciplina> getNotasGeralDisciplinas() {
        return this.notasGeralDisciplinas;
    }

    public void setNotasGeralDisciplinas(Set<NotasGeralDisciplina> notasGeralDisciplinas) {
        if (this.notasGeralDisciplinas != null) {
            this.notasGeralDisciplinas.forEach(i -> i.setDocente(null));
        }
        if (notasGeralDisciplinas != null) {
            notasGeralDisciplinas.forEach(i -> i.setDocente(this));
        }
        this.notasGeralDisciplinas = notasGeralDisciplinas;
    }

    public Docente notasGeralDisciplinas(Set<NotasGeralDisciplina> notasGeralDisciplinas) {
        this.setNotasGeralDisciplinas(notasGeralDisciplinas);
        return this;
    }

    public Docente addNotasGeralDisciplina(NotasGeralDisciplina notasGeralDisciplina) {
        this.notasGeralDisciplinas.add(notasGeralDisciplina);
        notasGeralDisciplina.setDocente(this);
        return this;
    }

    public Docente removeNotasGeralDisciplina(NotasGeralDisciplina notasGeralDisciplina) {
        this.notasGeralDisciplinas.remove(notasGeralDisciplina);
        notasGeralDisciplina.setDocente(null);
        return this;
    }

    public Set<DissertacaoFinalCurso> getDissertacaoFinalCursos() {
        return this.dissertacaoFinalCursos;
    }

    public void setDissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        if (this.dissertacaoFinalCursos != null) {
            this.dissertacaoFinalCursos.forEach(i -> i.setOrientador(null));
        }
        if (dissertacaoFinalCursos != null) {
            dissertacaoFinalCursos.forEach(i -> i.setOrientador(this));
        }
        this.dissertacaoFinalCursos = dissertacaoFinalCursos;
    }

    public Docente dissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        this.setDissertacaoFinalCursos(dissertacaoFinalCursos);
        return this;
    }

    public Docente addDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.add(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setOrientador(this);
        return this;
    }

    public Docente removeDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.remove(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setOrientador(null);
        return this;
    }

    public Set<CategoriaOcorrencia> getCategoriaOcorrencias() {
        return this.categoriaOcorrencias;
    }

    public void setCategoriaOcorrencias(Set<CategoriaOcorrencia> categoriaOcorrencias) {
        if (this.categoriaOcorrencias != null) {
            this.categoriaOcorrencias.forEach(i -> i.setEncaminhar(null));
        }
        if (categoriaOcorrencias != null) {
            categoriaOcorrencias.forEach(i -> i.setEncaminhar(this));
        }
        this.categoriaOcorrencias = categoriaOcorrencias;
    }

    public Docente categoriaOcorrencias(Set<CategoriaOcorrencia> categoriaOcorrencias) {
        this.setCategoriaOcorrencias(categoriaOcorrencias);
        return this;
    }

    public Docente addCategoriaOcorrencia(CategoriaOcorrencia categoriaOcorrencia) {
        this.categoriaOcorrencias.add(categoriaOcorrencia);
        categoriaOcorrencia.setEncaminhar(this);
        return this;
    }

    public Docente removeCategoriaOcorrencia(CategoriaOcorrencia categoriaOcorrencia) {
        this.categoriaOcorrencias.remove(categoriaOcorrencia);
        categoriaOcorrencia.setEncaminhar(null);
        return this;
    }

    public Set<FormacaoDocente> getFormacoes() {
        return this.formacoes;
    }

    public void setFormacoes(Set<FormacaoDocente> formacaoDocentes) {
        if (this.formacoes != null) {
            this.formacoes.forEach(i -> i.setDocente(null));
        }
        if (formacaoDocentes != null) {
            formacaoDocentes.forEach(i -> i.setDocente(this));
        }
        this.formacoes = formacaoDocentes;
    }

    public Docente formacoes(Set<FormacaoDocente> formacaoDocentes) {
        this.setFormacoes(formacaoDocentes);
        return this;
    }

    public Docente addFormacoes(FormacaoDocente formacaoDocente) {
        this.formacoes.add(formacaoDocente);
        formacaoDocente.setDocente(this);
        return this;
    }

    public Docente removeFormacoes(FormacaoDocente formacaoDocente) {
        this.formacoes.remove(formacaoDocente);
        formacaoDocente.setDocente(null);
        return this;
    }

    public LookupItem getNacionalidade() {
        return this.nacionalidade;
    }

    public void setNacionalidade(LookupItem lookupItem) {
        this.nacionalidade = lookupItem;
    }

    public Docente nacionalidade(LookupItem lookupItem) {
        this.setNacionalidade(lookupItem);
        return this;
    }

    public LookupItem getNaturalidade() {
        return this.naturalidade;
    }

    public void setNaturalidade(LookupItem lookupItem) {
        this.naturalidade = lookupItem;
    }

    public Docente naturalidade(LookupItem lookupItem) {
        this.setNaturalidade(lookupItem);
        return this;
    }

    public LookupItem getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(LookupItem lookupItem) {
        this.tipoDocumento = lookupItem;
    }

    public Docente tipoDocumento(LookupItem lookupItem) {
        this.setTipoDocumento(lookupItem);
        return this;
    }

    public LookupItem getGrauAcademico() {
        return this.grauAcademico;
    }

    public void setGrauAcademico(LookupItem lookupItem) {
        this.grauAcademico = lookupItem;
    }

    public Docente grauAcademico(LookupItem lookupItem) {
        this.setGrauAcademico(lookupItem);
        return this;
    }

    public LookupItem getCategoriaProfissional() {
        return this.categoriaProfissional;
    }

    public void setCategoriaProfissional(LookupItem lookupItem) {
        this.categoriaProfissional = lookupItem;
    }

    public Docente categoriaProfissional(LookupItem lookupItem) {
        this.setCategoriaProfissional(lookupItem);
        return this;
    }

    public LookupItem getUnidadeOrganica() {
        return this.unidadeOrganica;
    }

    public void setUnidadeOrganica(LookupItem lookupItem) {
        this.unidadeOrganica = lookupItem;
    }

    public Docente unidadeOrganica(LookupItem lookupItem) {
        this.setUnidadeOrganica(lookupItem);
        return this;
    }

    public LookupItem getEstadoCivil() {
        return this.estadoCivil;
    }

    public void setEstadoCivil(LookupItem lookupItem) {
        this.estadoCivil = lookupItem;
    }

    public Docente estadoCivil(LookupItem lookupItem) {
        this.setEstadoCivil(lookupItem);
        return this;
    }

    public ResponsavelTurno getResponsavelTurno() {
        return this.responsavelTurno;
    }

    public void setResponsavelTurno(ResponsavelTurno responsavelTurno) {
        this.responsavelTurno = responsavelTurno;
    }

    public Docente responsavelTurno(ResponsavelTurno responsavelTurno) {
        this.setResponsavelTurno(responsavelTurno);
        return this;
    }

    public ResponsavelAreaFormacao getResponsavelAreaFormacao() {
        return this.responsavelAreaFormacao;
    }

    public void setResponsavelAreaFormacao(ResponsavelAreaFormacao responsavelAreaFormacao) {
        this.responsavelAreaFormacao = responsavelAreaFormacao;
    }

    public Docente responsavelAreaFormacao(ResponsavelAreaFormacao responsavelAreaFormacao) {
        this.setResponsavelAreaFormacao(responsavelAreaFormacao);
        return this;
    }

    public ResponsavelCurso getResponsavelCurso() {
        return this.responsavelCurso;
    }

    public void setResponsavelCurso(ResponsavelCurso responsavelCurso) {
        this.responsavelCurso = responsavelCurso;
    }

    public Docente responsavelCurso(ResponsavelCurso responsavelCurso) {
        this.setResponsavelCurso(responsavelCurso);
        return this;
    }

    public ResponsavelDisciplina getResponsavelDisciplina() {
        return this.responsavelDisciplina;
    }

    public void setResponsavelDisciplina(ResponsavelDisciplina responsavelDisciplina) {
        this.responsavelDisciplina = responsavelDisciplina;
    }

    public Docente responsavelDisciplina(ResponsavelDisciplina responsavelDisciplina) {
        this.setResponsavelDisciplina(responsavelDisciplina);
        return this;
    }

    public ResponsavelTurma getResponsavelTurma() {
        return this.responsavelTurma;
    }

    public void setResponsavelTurma(ResponsavelTurma responsavelTurma) {
        this.responsavelTurma = responsavelTurma;
    }

    public Docente responsavelTurma(ResponsavelTurma responsavelTurma) {
        this.setResponsavelTurma(responsavelTurma);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Docente)) {
            return false;
        }
        return id != null && id.equals(((Docente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Docente{" +
            "id=" + getId() +
            ", fotografia='" + getFotografia() + "'" +
            ", fotografiaContentType='" + getFotografiaContentType() + "'" +
            ", nome='" + getNome() + "'" +
            ", nascimento='" + getNascimento() + "'" +
            ", nif='" + getNif() + "'" +
            ", inss='" + getInss() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", pai='" + getPai() + "'" +
            ", mae='" + getMae() + "'" +
            ", documentoNumero='" + getDocumentoNumero() + "'" +
            ", documentoEmissao='" + getDocumentoEmissao() + "'" +
            ", documentoValidade='" + getDocumentoValidade() + "'" +
            ", residencia='" + getResidencia() + "'" +
            ", dataInicioFuncoes='" + getDataInicioFuncoes() + "'" +
            ", telefonePrincipal='" + getTelefonePrincipal() + "'" +
            ", telefoneParente='" + getTelefoneParente() + "'" +
            ", email='" + getEmail() + "'" +
            ", numeroAgente='" + getNumeroAgente() + "'" +
            ", temAgregacaoPedagogica='" + getTemAgregacaoPedagogica() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", hash='" + getHash() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
