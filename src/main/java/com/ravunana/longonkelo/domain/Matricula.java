package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.EstadoAcademico;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Matricula.
 */
@Entity
@Table(name = "matricula")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Matricula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "chave_composta_1", unique = true)
    private String chaveComposta1;

    @Column(name = "chave_composta_2", unique = true)
    private String chaveComposta2;

    @NotNull
    @Column(name = "numero_matricula", nullable = false)
    private String numeroMatricula;

    @Min(value = 0)
    @Column(name = "numero_chamada")
    private Integer numeroChamada;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoAcademico estado;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Lob
    @Column(name = "termos_compromissos")
    private byte[] termosCompromissos;

    @Column(name = "termos_compromissos_content_type")
    private String termosCompromissosContentType;

    @Column(name = "is_aceite_termos_compromisso")
    private Boolean isAceiteTermosCompromisso;

    @OneToMany(mappedBy = "referencia")
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

    @OneToMany(mappedBy = "matricula")
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

    @OneToMany(mappedBy = "matricula")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "recibos", "utilizador", "moeda", "matricula", "meioPagamento", "conta", "transferenciaSaldos" },
        allowSetters = true
    )
    private Set<Transacao> transacoes = new HashSet<>();

    @OneToMany(mappedBy = "matricula")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "aplicacoesRecibos", "anoLectivos", "utilizador", "matricula", "documentoComercial", "transacao" },
        allowSetters = true
    )
    private Set<Recibo> recibos = new HashSet<>();

    @OneToMany(mappedBy = "matricula")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "docente", "disciplinaCurricular", "matricula", "estado" },
        allowSetters = true
    )
    private Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas = new HashSet<>();

    @OneToMany(mappedBy = "matricula")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "docente", "disciplinaCurricular", "matricula", "estado" },
        allowSetters = true
    )
    private Set<NotasGeralDisciplina> notasGeralDisciplinas = new HashSet<>();

    @OneToMany(mappedBy = "matricula")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "de", "para", "utilizador", "motivoTransferencia", "matricula" }, allowSetters = true)
    private Set<TransferenciaTurma> transferenciaTurmas = new HashSet<>();

    @OneToMany(mappedBy = "matricula")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "ocorrencias", "anoLectivos", "utilizador", "referencia", "docente", "matricula", "estado", "licao" },
        allowSetters = true
    )
    private Set<Ocorrencia> ocorrencias = new HashSet<>();

    @ManyToOne
    private User utilizador;

    @ManyToMany
    @JoinTable(
        name = "rel_matricula__categorias_matriculas",
        joinColumns = @JoinColumn(name = "matricula_id"),
        inverseJoinColumns = @JoinColumn(name = "categorias_matriculas_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categoriasEmolumentos", "matriculas" }, allowSetters = true)
    private Set<PlanoDesconto> categoriasMatriculas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "turmas",
            "horarios",
            "notasPeriodicaDisciplinas",
            "processoSelectivoMatriculas",
            "planoAulas",
            "matriculas",
            "resumoAcademicos",
            "responsaveis",
            "dissertacaoFinalCursos",
            "anoLectivos",
            "utilizador",
            "referencia",
            "planoCurricular",
            "turno",
        },
        allowSetters = true
    )
    private Turma turma;

    @ManyToOne
    @JsonIgnoreProperties(value = { "discentes", "matriculas", "grauParentesco", "tipoDocumento", "profissao" }, allowSetters = true)
    private EncarregadoEducacao responsavelFinanceiro;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "enderecos",
            "processosSelectivos",
            "anexoDiscentes",
            "matriculas",
            "resumoAcademicos",
            "historicosSaudes",
            "dissertacaoFinalCursos",
            "nacionalidade",
            "naturalidade",
            "tipoDocumento",
            "profissao",
            "grupoSanguinio",
            "necessidadeEspecial",
            "encarregadoEducacao",
        },
        allowSetters = true
    )
    private Discente discente;

    @ManyToOne
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
    private Matricula referencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Matricula id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChaveComposta1() {
        return this.chaveComposta1;
    }

    public Matricula chaveComposta1(String chaveComposta1) {
        this.setChaveComposta1(chaveComposta1);
        return this;
    }

    public void setChaveComposta1(String chaveComposta1) {
        this.chaveComposta1 = chaveComposta1;
    }

    public String getChaveComposta2() {
        return this.chaveComposta2;
    }

    public Matricula chaveComposta2(String chaveComposta2) {
        this.setChaveComposta2(chaveComposta2);
        return this;
    }

    public void setChaveComposta2(String chaveComposta2) {
        this.chaveComposta2 = chaveComposta2;
    }

    public String getNumeroMatricula() {
        return this.numeroMatricula;
    }

    public Matricula numeroMatricula(String numeroMatricula) {
        this.setNumeroMatricula(numeroMatricula);
        return this;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public Integer getNumeroChamada() {
        return this.numeroChamada;
    }

    public Matricula numeroChamada(Integer numeroChamada) {
        this.setNumeroChamada(numeroChamada);
        return this;
    }

    public void setNumeroChamada(Integer numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public EstadoAcademico getEstado() {
        return this.estado;
    }

    public Matricula estado(EstadoAcademico estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoAcademico estado) {
        this.estado = estado;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public Matricula timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Matricula descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getTermosCompromissos() {
        return this.termosCompromissos;
    }

    public Matricula termosCompromissos(byte[] termosCompromissos) {
        this.setTermosCompromissos(termosCompromissos);
        return this;
    }

    public void setTermosCompromissos(byte[] termosCompromissos) {
        this.termosCompromissos = termosCompromissos;
    }

    public String getTermosCompromissosContentType() {
        return this.termosCompromissosContentType;
    }

    public Matricula termosCompromissosContentType(String termosCompromissosContentType) {
        this.termosCompromissosContentType = termosCompromissosContentType;
        return this;
    }

    public void setTermosCompromissosContentType(String termosCompromissosContentType) {
        this.termosCompromissosContentType = termosCompromissosContentType;
    }

    public Boolean getIsAceiteTermosCompromisso() {
        return this.isAceiteTermosCompromisso;
    }

    public Matricula isAceiteTermosCompromisso(Boolean isAceiteTermosCompromisso) {
        this.setIsAceiteTermosCompromisso(isAceiteTermosCompromisso);
        return this;
    }

    public void setIsAceiteTermosCompromisso(Boolean isAceiteTermosCompromisso) {
        this.isAceiteTermosCompromisso = isAceiteTermosCompromisso;
    }

    public Set<Matricula> getMatriculas() {
        return this.matriculas;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        if (this.matriculas != null) {
            this.matriculas.forEach(i -> i.setReferencia(null));
        }
        if (matriculas != null) {
            matriculas.forEach(i -> i.setReferencia(this));
        }
        this.matriculas = matriculas;
    }

    public Matricula matriculas(Set<Matricula> matriculas) {
        this.setMatriculas(matriculas);
        return this;
    }

    public Matricula addMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setReferencia(this);
        return this;
    }

    public Matricula removeMatricula(Matricula matricula) {
        this.matriculas.remove(matricula);
        matricula.setReferencia(null);
        return this;
    }

    public Set<Factura> getFacturas() {
        return this.facturas;
    }

    public void setFacturas(Set<Factura> facturas) {
        if (this.facturas != null) {
            this.facturas.forEach(i -> i.setMatricula(null));
        }
        if (facturas != null) {
            facturas.forEach(i -> i.setMatricula(this));
        }
        this.facturas = facturas;
    }

    public Matricula facturas(Set<Factura> facturas) {
        this.setFacturas(facturas);
        return this;
    }

    public Matricula addFacturas(Factura factura) {
        this.facturas.add(factura);
        factura.setMatricula(this);
        return this;
    }

    public Matricula removeFacturas(Factura factura) {
        this.facturas.remove(factura);
        factura.setMatricula(null);
        return this;
    }

    public Set<Transacao> getTransacoes() {
        return this.transacoes;
    }

    public void setTransacoes(Set<Transacao> transacaos) {
        if (this.transacoes != null) {
            this.transacoes.forEach(i -> i.setMatricula(null));
        }
        if (transacaos != null) {
            transacaos.forEach(i -> i.setMatricula(this));
        }
        this.transacoes = transacaos;
    }

    public Matricula transacoes(Set<Transacao> transacaos) {
        this.setTransacoes(transacaos);
        return this;
    }

    public Matricula addTransacoes(Transacao transacao) {
        this.transacoes.add(transacao);
        transacao.setMatricula(this);
        return this;
    }

    public Matricula removeTransacoes(Transacao transacao) {
        this.transacoes.remove(transacao);
        transacao.setMatricula(null);
        return this;
    }

    public Set<Recibo> getRecibos() {
        return this.recibos;
    }

    public void setRecibos(Set<Recibo> recibos) {
        if (this.recibos != null) {
            this.recibos.forEach(i -> i.setMatricula(null));
        }
        if (recibos != null) {
            recibos.forEach(i -> i.setMatricula(this));
        }
        this.recibos = recibos;
    }

    public Matricula recibos(Set<Recibo> recibos) {
        this.setRecibos(recibos);
        return this;
    }

    public Matricula addRecibos(Recibo recibo) {
        this.recibos.add(recibo);
        recibo.setMatricula(this);
        return this;
    }

    public Matricula removeRecibos(Recibo recibo) {
        this.recibos.remove(recibo);
        recibo.setMatricula(null);
        return this;
    }

    public Set<NotasPeriodicaDisciplina> getNotasPeriodicaDisciplinas() {
        return this.notasPeriodicaDisciplinas;
    }

    public void setNotasPeriodicaDisciplinas(Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas) {
        if (this.notasPeriodicaDisciplinas != null) {
            this.notasPeriodicaDisciplinas.forEach(i -> i.setMatricula(null));
        }
        if (notasPeriodicaDisciplinas != null) {
            notasPeriodicaDisciplinas.forEach(i -> i.setMatricula(this));
        }
        this.notasPeriodicaDisciplinas = notasPeriodicaDisciplinas;
    }

    public Matricula notasPeriodicaDisciplinas(Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas) {
        this.setNotasPeriodicaDisciplinas(notasPeriodicaDisciplinas);
        return this;
    }

    public Matricula addNotasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplinas.add(notasPeriodicaDisciplina);
        notasPeriodicaDisciplina.setMatricula(this);
        return this;
    }

    public Matricula removeNotasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplinas.remove(notasPeriodicaDisciplina);
        notasPeriodicaDisciplina.setMatricula(null);
        return this;
    }

    public Set<NotasGeralDisciplina> getNotasGeralDisciplinas() {
        return this.notasGeralDisciplinas;
    }

    public void setNotasGeralDisciplinas(Set<NotasGeralDisciplina> notasGeralDisciplinas) {
        if (this.notasGeralDisciplinas != null) {
            this.notasGeralDisciplinas.forEach(i -> i.setMatricula(null));
        }
        if (notasGeralDisciplinas != null) {
            notasGeralDisciplinas.forEach(i -> i.setMatricula(this));
        }
        this.notasGeralDisciplinas = notasGeralDisciplinas;
    }

    public Matricula notasGeralDisciplinas(Set<NotasGeralDisciplina> notasGeralDisciplinas) {
        this.setNotasGeralDisciplinas(notasGeralDisciplinas);
        return this;
    }

    public Matricula addNotasGeralDisciplina(NotasGeralDisciplina notasGeralDisciplina) {
        this.notasGeralDisciplinas.add(notasGeralDisciplina);
        notasGeralDisciplina.setMatricula(this);
        return this;
    }

    public Matricula removeNotasGeralDisciplina(NotasGeralDisciplina notasGeralDisciplina) {
        this.notasGeralDisciplinas.remove(notasGeralDisciplina);
        notasGeralDisciplina.setMatricula(null);
        return this;
    }

    public Set<TransferenciaTurma> getTransferenciaTurmas() {
        return this.transferenciaTurmas;
    }

    public void setTransferenciaTurmas(Set<TransferenciaTurma> transferenciaTurmas) {
        if (this.transferenciaTurmas != null) {
            this.transferenciaTurmas.forEach(i -> i.setMatricula(null));
        }
        if (transferenciaTurmas != null) {
            transferenciaTurmas.forEach(i -> i.setMatricula(this));
        }
        this.transferenciaTurmas = transferenciaTurmas;
    }

    public Matricula transferenciaTurmas(Set<TransferenciaTurma> transferenciaTurmas) {
        this.setTransferenciaTurmas(transferenciaTurmas);
        return this;
    }

    public Matricula addTransferenciaTurma(TransferenciaTurma transferenciaTurma) {
        this.transferenciaTurmas.add(transferenciaTurma);
        transferenciaTurma.setMatricula(this);
        return this;
    }

    public Matricula removeTransferenciaTurma(TransferenciaTurma transferenciaTurma) {
        this.transferenciaTurmas.remove(transferenciaTurma);
        transferenciaTurma.setMatricula(null);
        return this;
    }

    public Set<Ocorrencia> getOcorrencias() {
        return this.ocorrencias;
    }

    public void setOcorrencias(Set<Ocorrencia> ocorrencias) {
        if (this.ocorrencias != null) {
            this.ocorrencias.forEach(i -> i.setMatricula(null));
        }
        if (ocorrencias != null) {
            ocorrencias.forEach(i -> i.setMatricula(this));
        }
        this.ocorrencias = ocorrencias;
    }

    public Matricula ocorrencias(Set<Ocorrencia> ocorrencias) {
        this.setOcorrencias(ocorrencias);
        return this;
    }

    public Matricula addOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
        ocorrencia.setMatricula(this);
        return this;
    }

    public Matricula removeOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.remove(ocorrencia);
        ocorrencia.setMatricula(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Matricula utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Set<PlanoDesconto> getCategoriasMatriculas() {
        return this.categoriasMatriculas;
    }

    public void setCategoriasMatriculas(Set<PlanoDesconto> planoDescontos) {
        this.categoriasMatriculas = planoDescontos;
    }

    public Matricula categoriasMatriculas(Set<PlanoDesconto> planoDescontos) {
        this.setCategoriasMatriculas(planoDescontos);
        return this;
    }

    public Matricula addCategoriasMatriculas(PlanoDesconto planoDesconto) {
        this.categoriasMatriculas.add(planoDesconto);
        planoDesconto.getMatriculas().add(this);
        return this;
    }

    public Matricula removeCategoriasMatriculas(PlanoDesconto planoDesconto) {
        this.categoriasMatriculas.remove(planoDesconto);
        planoDesconto.getMatriculas().remove(this);
        return this;
    }

    public Turma getTurma() {
        return this.turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Matricula turma(Turma turma) {
        this.setTurma(turma);
        return this;
    }

    public EncarregadoEducacao getResponsavelFinanceiro() {
        return this.responsavelFinanceiro;
    }

    public void setResponsavelFinanceiro(EncarregadoEducacao encarregadoEducacao) {
        this.responsavelFinanceiro = encarregadoEducacao;
    }

    public Matricula responsavelFinanceiro(EncarregadoEducacao encarregadoEducacao) {
        this.setResponsavelFinanceiro(encarregadoEducacao);
        return this;
    }

    public Discente getDiscente() {
        return this.discente;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }

    public Matricula discente(Discente discente) {
        this.setDiscente(discente);
        return this;
    }

    public Matricula getReferencia() {
        return this.referencia;
    }

    public void setReferencia(Matricula matricula) {
        this.referencia = matricula;
    }

    public Matricula referencia(Matricula matricula) {
        this.setReferencia(matricula);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matricula)) {
            return false;
        }
        return id != null && id.equals(((Matricula) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Matricula{" +
            "id=" + getId() +
            ", chaveComposta1='" + getChaveComposta1() + "'" +
            ", chaveComposta2='" + getChaveComposta2() + "'" +
            ", numeroMatricula='" + getNumeroMatricula() + "'" +
            ", numeroChamada=" + getNumeroChamada() +
            ", estado='" + getEstado() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", termosCompromissos='" + getTermosCompromissos() + "'" +
            ", termosCompromissosContentType='" + getTermosCompromissosContentType() + "'" +
            ", isAceiteTermosCompromisso='" + getIsAceiteTermosCompromisso() + "'" +
            "}";
    }
}
