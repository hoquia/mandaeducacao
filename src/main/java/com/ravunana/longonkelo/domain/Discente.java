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
 * A Discente.
 */
@Entity
@Table(name = "discente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Discente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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

    @NotNull
    @Column(name = "documento_numero", nullable = false, unique = true)
    private String documentoNumero;

    @NotNull
    @Column(name = "documento_emissao", nullable = false)
    private LocalDate documentoEmissao;

    @NotNull
    @Column(name = "documento_validade", nullable = false)
    private LocalDate documentoValidade;

    @Column(name = "nif", unique = true)
    private String nif;

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

    @Column(name = "telefone_principal", unique = true)
    private String telefonePrincipal;

    @Column(name = "telefone_parente")
    private String telefoneParente;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "is_encarregado_educacao")
    private Boolean isEncarregadoEducacao;

    @Column(name = "is_trabalhador")
    private Boolean isTrabalhador;

    @Column(name = "is_filho_antigo_conbatente")
    private Boolean isFilhoAntigoConbatente;

    @Column(name = "is_atestado_pobreza")
    private Boolean isAtestadoPobreza;

    @Column(name = "nome_medico")
    private String nomeMedico;

    @Size(max = 9)
    @Column(name = "telefone_medico", length = 9)
    private String telefoneMedico;

    @Column(name = "instituicao_particular_saude")
    private String instituicaoParticularSaude;

    @Min(value = 0)
    @Column(name = "altura")
    private Integer altura;

    @DecimalMin(value = "0")
    @Column(name = "peso")
    private Double peso;

    @Column(name = "is_asmatico")
    private Boolean isAsmatico;

    @Column(name = "is_alergico")
    private Boolean isAlergico;

    @Column(name = "is_pratica_educacao_fisica")
    private Boolean isPraticaEducacaoFisica;

    @Column(name = "is_autorizado_medicacao")
    private Boolean isAutorizadoMedicacao;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "cuidados_especiais_saude")
    private String cuidadosEspeciaisSaude;

    @NotNull
    @Column(name = "numero_processo", nullable = false, unique = true)
    private String numeroProcesso;

    @Column(name = "data_ingresso")
    private ZonedDateTime dataIngresso;

    @Column(name = "hash")
    private String hash;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao")
    private String observacao;

    @OneToMany(mappedBy = "discente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pais", "provincia", "municipio", "discente" }, allowSetters = true)
    private Set<EnderecoDiscente> enderecos = new HashSet<>();

    @OneToMany(mappedBy = "discente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anoLectivos", "utilizador", "turma", "discente" }, allowSetters = true)
    private Set<ProcessoSelectivoMatricula> processosSelectivos = new HashSet<>();

    @OneToMany(mappedBy = "discente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "discente" }, allowSetters = true)
    private Set<AnexoDiscente> anexoDiscentes = new HashSet<>();

    @OneToMany(mappedBy = "discente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Matricula> matriculas = new HashSet<>();

    @OneToMany(mappedBy = "discente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilizador", "ultimaTurmaMatriculada", "discente", "situacao" }, allowSetters = true)
    private Set<ResumoAcademico> resumoAcademicos = new HashSet<>();

    @OneToMany(mappedBy = "discente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilizador", "discente" }, allowSetters = true)
    private Set<HistoricoSaude> historicosSaudes = new HashSet<>();

    @OneToMany(mappedBy = "discente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "orientador", "especialidade", "discente", "estado", "natureza" },
        allowSetters = true
    )
    private Set<DissertacaoFinalCurso> dissertacaoFinalCursos = new HashSet<>();

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem profissao;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem grupoSanguinio;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem necessidadeEspecial;

    @ManyToOne
    @JsonIgnoreProperties(value = { "discentes", "matriculas", "grauParentesco", "tipoDocumento", "profissao" }, allowSetters = true)
    private EncarregadoEducacao encarregadoEducacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Discente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFotografia() {
        return this.fotografia;
    }

    public Discente fotografia(byte[] fotografia) {
        this.setFotografia(fotografia);
        return this;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public String getFotografiaContentType() {
        return this.fotografiaContentType;
    }

    public Discente fotografiaContentType(String fotografiaContentType) {
        this.fotografiaContentType = fotografiaContentType;
        return this;
    }

    public void setFotografiaContentType(String fotografiaContentType) {
        this.fotografiaContentType = fotografiaContentType;
    }

    public String getNome() {
        return this.nome;
    }

    public Discente nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return this.nascimento;
    }

    public Discente nascimento(LocalDate nascimento) {
        this.setNascimento(nascimento);
        return this;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getDocumentoNumero() {
        return this.documentoNumero;
    }

    public Discente documentoNumero(String documentoNumero) {
        this.setDocumentoNumero(documentoNumero);
        return this;
    }

    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    public LocalDate getDocumentoEmissao() {
        return this.documentoEmissao;
    }

    public Discente documentoEmissao(LocalDate documentoEmissao) {
        this.setDocumentoEmissao(documentoEmissao);
        return this;
    }

    public void setDocumentoEmissao(LocalDate documentoEmissao) {
        this.documentoEmissao = documentoEmissao;
    }

    public LocalDate getDocumentoValidade() {
        return this.documentoValidade;
    }

    public Discente documentoValidade(LocalDate documentoValidade) {
        this.setDocumentoValidade(documentoValidade);
        return this;
    }

    public void setDocumentoValidade(LocalDate documentoValidade) {
        this.documentoValidade = documentoValidade;
    }

    public String getNif() {
        return this.nif;
    }

    public Discente nif(String nif) {
        this.setNif(nif);
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Sexo getSexo() {
        return this.sexo;
    }

    public Discente sexo(Sexo sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getPai() {
        return this.pai;
    }

    public Discente pai(String pai) {
        this.setPai(pai);
        return this;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getMae() {
        return this.mae;
    }

    public Discente mae(String mae) {
        this.setMae(mae);
        return this;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getTelefonePrincipal() {
        return this.telefonePrincipal;
    }

    public Discente telefonePrincipal(String telefonePrincipal) {
        this.setTelefonePrincipal(telefonePrincipal);
        return this;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneParente() {
        return this.telefoneParente;
    }

    public Discente telefoneParente(String telefoneParente) {
        this.setTelefoneParente(telefoneParente);
        return this;
    }

    public void setTelefoneParente(String telefoneParente) {
        this.telefoneParente = telefoneParente;
    }

    public String getEmail() {
        return this.email;
    }

    public Discente email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsEncarregadoEducacao() {
        return this.isEncarregadoEducacao;
    }

    public Discente isEncarregadoEducacao(Boolean isEncarregadoEducacao) {
        this.setIsEncarregadoEducacao(isEncarregadoEducacao);
        return this;
    }

    public void setIsEncarregadoEducacao(Boolean isEncarregadoEducacao) {
        this.isEncarregadoEducacao = isEncarregadoEducacao;
    }

    public Boolean getIsTrabalhador() {
        return this.isTrabalhador;
    }

    public Discente isTrabalhador(Boolean isTrabalhador) {
        this.setIsTrabalhador(isTrabalhador);
        return this;
    }

    public void setIsTrabalhador(Boolean isTrabalhador) {
        this.isTrabalhador = isTrabalhador;
    }

    public Boolean getIsFilhoAntigoConbatente() {
        return this.isFilhoAntigoConbatente;
    }

    public Discente isFilhoAntigoConbatente(Boolean isFilhoAntigoConbatente) {
        this.setIsFilhoAntigoConbatente(isFilhoAntigoConbatente);
        return this;
    }

    public void setIsFilhoAntigoConbatente(Boolean isFilhoAntigoConbatente) {
        this.isFilhoAntigoConbatente = isFilhoAntigoConbatente;
    }

    public Boolean getIsAtestadoPobreza() {
        return this.isAtestadoPobreza;
    }

    public Discente isAtestadoPobreza(Boolean isAtestadoPobreza) {
        this.setIsAtestadoPobreza(isAtestadoPobreza);
        return this;
    }

    public void setIsAtestadoPobreza(Boolean isAtestadoPobreza) {
        this.isAtestadoPobreza = isAtestadoPobreza;
    }

    public String getNomeMedico() {
        return this.nomeMedico;
    }

    public Discente nomeMedico(String nomeMedico) {
        this.setNomeMedico(nomeMedico);
        return this;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getTelefoneMedico() {
        return this.telefoneMedico;
    }

    public Discente telefoneMedico(String telefoneMedico) {
        this.setTelefoneMedico(telefoneMedico);
        return this;
    }

    public void setTelefoneMedico(String telefoneMedico) {
        this.telefoneMedico = telefoneMedico;
    }

    public String getInstituicaoParticularSaude() {
        return this.instituicaoParticularSaude;
    }

    public Discente instituicaoParticularSaude(String instituicaoParticularSaude) {
        this.setInstituicaoParticularSaude(instituicaoParticularSaude);
        return this;
    }

    public void setInstituicaoParticularSaude(String instituicaoParticularSaude) {
        this.instituicaoParticularSaude = instituicaoParticularSaude;
    }

    public Integer getAltura() {
        return this.altura;
    }

    public Discente altura(Integer altura) {
        this.setAltura(altura);
        return this;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return this.peso;
    }

    public Discente peso(Double peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Boolean getIsAsmatico() {
        return this.isAsmatico;
    }

    public Discente isAsmatico(Boolean isAsmatico) {
        this.setIsAsmatico(isAsmatico);
        return this;
    }

    public void setIsAsmatico(Boolean isAsmatico) {
        this.isAsmatico = isAsmatico;
    }

    public Boolean getIsAlergico() {
        return this.isAlergico;
    }

    public Discente isAlergico(Boolean isAlergico) {
        this.setIsAlergico(isAlergico);
        return this;
    }

    public void setIsAlergico(Boolean isAlergico) {
        this.isAlergico = isAlergico;
    }

    public Boolean getIsPraticaEducacaoFisica() {
        return this.isPraticaEducacaoFisica;
    }

    public Discente isPraticaEducacaoFisica(Boolean isPraticaEducacaoFisica) {
        this.setIsPraticaEducacaoFisica(isPraticaEducacaoFisica);
        return this;
    }

    public void setIsPraticaEducacaoFisica(Boolean isPraticaEducacaoFisica) {
        this.isPraticaEducacaoFisica = isPraticaEducacaoFisica;
    }

    public Boolean getIsAutorizadoMedicacao() {
        return this.isAutorizadoMedicacao;
    }

    public Discente isAutorizadoMedicacao(Boolean isAutorizadoMedicacao) {
        this.setIsAutorizadoMedicacao(isAutorizadoMedicacao);
        return this;
    }

    public void setIsAutorizadoMedicacao(Boolean isAutorizadoMedicacao) {
        this.isAutorizadoMedicacao = isAutorizadoMedicacao;
    }

    public String getCuidadosEspeciaisSaude() {
        return this.cuidadosEspeciaisSaude;
    }

    public Discente cuidadosEspeciaisSaude(String cuidadosEspeciaisSaude) {
        this.setCuidadosEspeciaisSaude(cuidadosEspeciaisSaude);
        return this;
    }

    public void setCuidadosEspeciaisSaude(String cuidadosEspeciaisSaude) {
        this.cuidadosEspeciaisSaude = cuidadosEspeciaisSaude;
    }

    public String getNumeroProcesso() {
        return this.numeroProcesso;
    }

    public Discente numeroProcesso(String numeroProcesso) {
        this.setNumeroProcesso(numeroProcesso);
        return this;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public ZonedDateTime getDataIngresso() {
        return this.dataIngresso;
    }

    public Discente dataIngresso(ZonedDateTime dataIngresso) {
        this.setDataIngresso(dataIngresso);
        return this;
    }

    public void setDataIngresso(ZonedDateTime dataIngresso) {
        this.dataIngresso = dataIngresso;
    }

    public String getHash() {
        return this.hash;
    }

    public Discente hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public Discente observacao(String observacao) {
        this.setObservacao(observacao);
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Set<EnderecoDiscente> getEnderecos() {
        return this.enderecos;
    }

    public void setEnderecos(Set<EnderecoDiscente> enderecoDiscentes) {
        if (this.enderecos != null) {
            this.enderecos.forEach(i -> i.setDiscente(null));
        }
        if (enderecoDiscentes != null) {
            enderecoDiscentes.forEach(i -> i.setDiscente(this));
        }
        this.enderecos = enderecoDiscentes;
    }

    public Discente enderecos(Set<EnderecoDiscente> enderecoDiscentes) {
        this.setEnderecos(enderecoDiscentes);
        return this;
    }

    public Discente addEnderecos(EnderecoDiscente enderecoDiscente) {
        this.enderecos.add(enderecoDiscente);
        enderecoDiscente.setDiscente(this);
        return this;
    }

    public Discente removeEnderecos(EnderecoDiscente enderecoDiscente) {
        this.enderecos.remove(enderecoDiscente);
        enderecoDiscente.setDiscente(null);
        return this;
    }

    public Set<ProcessoSelectivoMatricula> getProcessosSelectivos() {
        return this.processosSelectivos;
    }

    public void setProcessosSelectivos(Set<ProcessoSelectivoMatricula> processoSelectivoMatriculas) {
        if (this.processosSelectivos != null) {
            this.processosSelectivos.forEach(i -> i.setDiscente(null));
        }
        if (processoSelectivoMatriculas != null) {
            processoSelectivoMatriculas.forEach(i -> i.setDiscente(this));
        }
        this.processosSelectivos = processoSelectivoMatriculas;
    }

    public Discente processosSelectivos(Set<ProcessoSelectivoMatricula> processoSelectivoMatriculas) {
        this.setProcessosSelectivos(processoSelectivoMatriculas);
        return this;
    }

    public Discente addProcessosSelectivo(ProcessoSelectivoMatricula processoSelectivoMatricula) {
        this.processosSelectivos.add(processoSelectivoMatricula);
        processoSelectivoMatricula.setDiscente(this);
        return this;
    }

    public Discente removeProcessosSelectivo(ProcessoSelectivoMatricula processoSelectivoMatricula) {
        this.processosSelectivos.remove(processoSelectivoMatricula);
        processoSelectivoMatricula.setDiscente(null);
        return this;
    }

    public Set<AnexoDiscente> getAnexoDiscentes() {
        return this.anexoDiscentes;
    }

    public void setAnexoDiscentes(Set<AnexoDiscente> anexoDiscentes) {
        if (this.anexoDiscentes != null) {
            this.anexoDiscentes.forEach(i -> i.setDiscente(null));
        }
        if (anexoDiscentes != null) {
            anexoDiscentes.forEach(i -> i.setDiscente(this));
        }
        this.anexoDiscentes = anexoDiscentes;
    }

    public Discente anexoDiscentes(Set<AnexoDiscente> anexoDiscentes) {
        this.setAnexoDiscentes(anexoDiscentes);
        return this;
    }

    public Discente addAnexoDiscente(AnexoDiscente anexoDiscente) {
        this.anexoDiscentes.add(anexoDiscente);
        anexoDiscente.setDiscente(this);
        return this;
    }

    public Discente removeAnexoDiscente(AnexoDiscente anexoDiscente) {
        this.anexoDiscentes.remove(anexoDiscente);
        anexoDiscente.setDiscente(null);
        return this;
    }

    public Set<Matricula> getMatriculas() {
        return this.matriculas;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        if (this.matriculas != null) {
            this.matriculas.forEach(i -> i.setDiscente(null));
        }
        if (matriculas != null) {
            matriculas.forEach(i -> i.setDiscente(this));
        }
        this.matriculas = matriculas;
    }

    public Discente matriculas(Set<Matricula> matriculas) {
        this.setMatriculas(matriculas);
        return this;
    }

    public Discente addMatriculas(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setDiscente(this);
        return this;
    }

    public Discente removeMatriculas(Matricula matricula) {
        this.matriculas.remove(matricula);
        matricula.setDiscente(null);
        return this;
    }

    public Set<ResumoAcademico> getResumoAcademicos() {
        return this.resumoAcademicos;
    }

    public void setResumoAcademicos(Set<ResumoAcademico> resumoAcademicos) {
        if (this.resumoAcademicos != null) {
            this.resumoAcademicos.forEach(i -> i.setDiscente(null));
        }
        if (resumoAcademicos != null) {
            resumoAcademicos.forEach(i -> i.setDiscente(this));
        }
        this.resumoAcademicos = resumoAcademicos;
    }

    public Discente resumoAcademicos(Set<ResumoAcademico> resumoAcademicos) {
        this.setResumoAcademicos(resumoAcademicos);
        return this;
    }

    public Discente addResumoAcademico(ResumoAcademico resumoAcademico) {
        this.resumoAcademicos.add(resumoAcademico);
        resumoAcademico.setDiscente(this);
        return this;
    }

    public Discente removeResumoAcademico(ResumoAcademico resumoAcademico) {
        this.resumoAcademicos.remove(resumoAcademico);
        resumoAcademico.setDiscente(null);
        return this;
    }

    public Set<HistoricoSaude> getHistoricosSaudes() {
        return this.historicosSaudes;
    }

    public void setHistoricosSaudes(Set<HistoricoSaude> historicoSaudes) {
        if (this.historicosSaudes != null) {
            this.historicosSaudes.forEach(i -> i.setDiscente(null));
        }
        if (historicoSaudes != null) {
            historicoSaudes.forEach(i -> i.setDiscente(this));
        }
        this.historicosSaudes = historicoSaudes;
    }

    public Discente historicosSaudes(Set<HistoricoSaude> historicoSaudes) {
        this.setHistoricosSaudes(historicoSaudes);
        return this;
    }

    public Discente addHistoricosSaude(HistoricoSaude historicoSaude) {
        this.historicosSaudes.add(historicoSaude);
        historicoSaude.setDiscente(this);
        return this;
    }

    public Discente removeHistoricosSaude(HistoricoSaude historicoSaude) {
        this.historicosSaudes.remove(historicoSaude);
        historicoSaude.setDiscente(null);
        return this;
    }

    public Set<DissertacaoFinalCurso> getDissertacaoFinalCursos() {
        return this.dissertacaoFinalCursos;
    }

    public void setDissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        if (this.dissertacaoFinalCursos != null) {
            this.dissertacaoFinalCursos.forEach(i -> i.setDiscente(null));
        }
        if (dissertacaoFinalCursos != null) {
            dissertacaoFinalCursos.forEach(i -> i.setDiscente(this));
        }
        this.dissertacaoFinalCursos = dissertacaoFinalCursos;
    }

    public Discente dissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        this.setDissertacaoFinalCursos(dissertacaoFinalCursos);
        return this;
    }

    public Discente addDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.add(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setDiscente(this);
        return this;
    }

    public Discente removeDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.remove(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setDiscente(null);
        return this;
    }

    public LookupItem getNacionalidade() {
        return this.nacionalidade;
    }

    public void setNacionalidade(LookupItem lookupItem) {
        this.nacionalidade = lookupItem;
    }

    public Discente nacionalidade(LookupItem lookupItem) {
        this.setNacionalidade(lookupItem);
        return this;
    }

    public LookupItem getNaturalidade() {
        return this.naturalidade;
    }

    public void setNaturalidade(LookupItem lookupItem) {
        this.naturalidade = lookupItem;
    }

    public Discente naturalidade(LookupItem lookupItem) {
        this.setNaturalidade(lookupItem);
        return this;
    }

    public LookupItem getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(LookupItem lookupItem) {
        this.tipoDocumento = lookupItem;
    }

    public Discente tipoDocumento(LookupItem lookupItem) {
        this.setTipoDocumento(lookupItem);
        return this;
    }

    public LookupItem getProfissao() {
        return this.profissao;
    }

    public void setProfissao(LookupItem lookupItem) {
        this.profissao = lookupItem;
    }

    public Discente profissao(LookupItem lookupItem) {
        this.setProfissao(lookupItem);
        return this;
    }

    public LookupItem getGrupoSanguinio() {
        return this.grupoSanguinio;
    }

    public void setGrupoSanguinio(LookupItem lookupItem) {
        this.grupoSanguinio = lookupItem;
    }

    public Discente grupoSanguinio(LookupItem lookupItem) {
        this.setGrupoSanguinio(lookupItem);
        return this;
    }

    public LookupItem getNecessidadeEspecial() {
        return this.necessidadeEspecial;
    }

    public void setNecessidadeEspecial(LookupItem lookupItem) {
        this.necessidadeEspecial = lookupItem;
    }

    public Discente necessidadeEspecial(LookupItem lookupItem) {
        this.setNecessidadeEspecial(lookupItem);
        return this;
    }

    public EncarregadoEducacao getEncarregadoEducacao() {
        return this.encarregadoEducacao;
    }

    public void setEncarregadoEducacao(EncarregadoEducacao encarregadoEducacao) {
        this.encarregadoEducacao = encarregadoEducacao;
    }

    public Discente encarregadoEducacao(EncarregadoEducacao encarregadoEducacao) {
        this.setEncarregadoEducacao(encarregadoEducacao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Discente)) {
            return false;
        }
        return id != null && id.equals(((Discente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Discente{" +
            "id=" + getId() +
            ", fotografia='" + getFotografia() + "'" +
            ", fotografiaContentType='" + getFotografiaContentType() + "'" +
            ", nome='" + getNome() + "'" +
            ", nascimento='" + getNascimento() + "'" +
            ", documentoNumero='" + getDocumentoNumero() + "'" +
            ", documentoEmissao='" + getDocumentoEmissao() + "'" +
            ", documentoValidade='" + getDocumentoValidade() + "'" +
            ", nif='" + getNif() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", pai='" + getPai() + "'" +
            ", mae='" + getMae() + "'" +
            ", telefonePrincipal='" + getTelefonePrincipal() + "'" +
            ", telefoneParente='" + getTelefoneParente() + "'" +
            ", email='" + getEmail() + "'" +
            ", isEncarregadoEducacao='" + getIsEncarregadoEducacao() + "'" +
            ", isTrabalhador='" + getIsTrabalhador() + "'" +
            ", isFilhoAntigoConbatente='" + getIsFilhoAntigoConbatente() + "'" +
            ", isAtestadoPobreza='" + getIsAtestadoPobreza() + "'" +
            ", nomeMedico='" + getNomeMedico() + "'" +
            ", telefoneMedico='" + getTelefoneMedico() + "'" +
            ", instituicaoParticularSaude='" + getInstituicaoParticularSaude() + "'" +
            ", altura=" + getAltura() +
            ", peso=" + getPeso() +
            ", isAsmatico='" + getIsAsmatico() + "'" +
            ", isAlergico='" + getIsAlergico() + "'" +
            ", isPraticaEducacaoFisica='" + getIsPraticaEducacaoFisica() + "'" +
            ", isAutorizadoMedicacao='" + getIsAutorizadoMedicacao() + "'" +
            ", cuidadosEspeciaisSaude='" + getCuidadosEspeciaisSaude() + "'" +
            ", numeroProcesso='" + getNumeroProcesso() + "'" +
            ", dataIngresso='" + getDataIngresso() + "'" +
            ", hash='" + getHash() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
