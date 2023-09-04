package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.CriterioDescricaoTurma;
import com.ravunana.longonkelo.domain.enumeration.CriterioNumeroChamada;
import com.ravunana.longonkelo.domain.enumeration.TipoTurma;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Turma.
 */
@Entity
@Table(name = "turma")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "chave_composta", unique = true)
    private String chaveComposta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_turma", nullable = false)
    private TipoTurma tipoTurma;

    @NotNull
    @Min(value = 1)
    @Column(name = "sala", nullable = false)
    private Integer sala;

    @NotNull
    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    @NotNull
    @Min(value = 1)
    @Column(name = "lotacao", nullable = false)
    private Integer lotacao;

    @NotNull
    @Min(value = 0)
    @Column(name = "confirmado", nullable = false)
    private Integer confirmado;

    @Column(name = "abertura")
    private LocalDate abertura;

    @Column(name = "encerramento")
    private LocalDate encerramento;

    @Enumerated(EnumType.STRING)
    @Column(name = "criterio_descricao")
    private CriterioDescricaoTurma criterioDescricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "criterio_ordenacao_numero")
    private CriterioNumeroChamada criterioOrdenacaoNumero;

    @Column(name = "faz_inscricao_depois_matricula")
    private Boolean fazInscricaoDepoisMatricula;

    @Column(name = "is_disponivel")
    private Boolean isDisponivel;

    @OneToMany(mappedBy = "referencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Turma> turmas = new HashSet<>();

    @OneToMany(mappedBy = "turma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "horarios", "licaos", "anoLectivos", "utilizador", "turma", "referencia", "periodo", "docente", "disciplinaCurricular" },
        allowSetters = true
    )
    private Set<Horario> horarios = new HashSet<>();

    @OneToMany(mappedBy = "turma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "docente", "disciplinaCurricular", "matricula", "estado" },
        allowSetters = true
    )
    private Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas = new HashSet<>();

    @OneToMany(mappedBy = "turma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anoLectivos", "utilizador", "turma", "discente" }, allowSetters = true)
    private Set<ProcessoSelectivoMatricula> processoSelectivoMatriculas = new HashSet<>();

    @OneToMany(mappedBy = "turma")
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

    @OneToMany(mappedBy = "turma")
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

    @OneToMany(mappedBy = "ultimaTurmaMatriculada")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilizador", "ultimaTurmaMatriculada", "discente", "situacao" }, allowSetters = true)
    private Set<ResumoAcademico> resumoAcademicos = new HashSet<>();

    @OneToMany(mappedBy = "turma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "turma" }, allowSetters = true)
    private Set<ResponsavelTurma> responsaveis = new HashSet<>();

    @OneToMany(mappedBy = "turma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "orientador", "especialidade", "discente", "estado", "natureza" },
        allowSetters = true
    )
    private Set<DissertacaoFinalCurso> dissertacaoFinalCursos = new HashSet<>();

    @OneToMany(mappedBy = "turma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "directorGeral",
            "subDirectorPdagogico",
            "subDirectorAdministrativo",
            "responsavelSecretariaGeral",
            "responsavelSecretariaPedagogico",
            "utilizador",
            "nivesEnsinos",
            "turma",
            "horario",
            "planoAula",
            "licao",
            "processoSelectivoMatricula",
            "ocorrencia",
            "notasPeriodicaDisciplina",
            "notasGeralDisciplina",
            "dissertacaoFinalCurso",
            "factura",
            "recibo",
            "responsavelTurno",
            "responsavelAreaFormacao",
            "responsavelCurso",
            "responsavelDisciplina",
            "responsavelTurma",
        },
        allowSetters = true
    )
    private Set<AnoLectivo> anoLectivos = new HashSet<>();

    @ManyToOne
    private User utilizador;

    @ManyToOne
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
    private Turma referencia;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "turmas", "utilizador", "classe", "curso", "disciplinasCurriculars" }, allowSetters = true)
    private PlanoCurricular planoCurricular;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "responsavelTurnos", "precoEmolumentos", "periodoHorarios", "turmas" }, allowSetters = true)
    private Turno turno;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Turma id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChaveComposta() {
        return this.chaveComposta;
    }

    public Turma chaveComposta(String chaveComposta) {
        this.setChaveComposta(chaveComposta);
        return this;
    }

    public void setChaveComposta(String chaveComposta) {
        this.chaveComposta = chaveComposta;
    }

    public TipoTurma getTipoTurma() {
        return this.tipoTurma;
    }

    public Turma tipoTurma(TipoTurma tipoTurma) {
        this.setTipoTurma(tipoTurma);
        return this;
    }

    public void setTipoTurma(TipoTurma tipoTurma) {
        this.tipoTurma = tipoTurma;
    }

    public Integer getSala() {
        return this.sala;
    }

    public Turma sala(Integer sala) {
        this.setSala(sala);
        return this;
    }

    public void setSala(Integer sala) {
        this.sala = sala;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Turma descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLotacao() {
        return this.lotacao;
    }

    public Turma lotacao(Integer lotacao) {
        this.setLotacao(lotacao);
        return this;
    }

    public void setLotacao(Integer lotacao) {
        this.lotacao = lotacao;
    }

    public Integer getConfirmado() {
        return this.confirmado;
    }

    public Turma confirmado(Integer confirmado) {
        this.setConfirmado(confirmado);
        return this;
    }

    public void setConfirmado(Integer confirmado) {
        this.confirmado = confirmado;
    }

    public LocalDate getAbertura() {
        return this.abertura;
    }

    public Turma abertura(LocalDate abertura) {
        this.setAbertura(abertura);
        return this;
    }

    public void setAbertura(LocalDate abertura) {
        this.abertura = abertura;
    }

    public LocalDate getEncerramento() {
        return this.encerramento;
    }

    public Turma encerramento(LocalDate encerramento) {
        this.setEncerramento(encerramento);
        return this;
    }

    public void setEncerramento(LocalDate encerramento) {
        this.encerramento = encerramento;
    }

    public CriterioDescricaoTurma getCriterioDescricao() {
        return this.criterioDescricao;
    }

    public Turma criterioDescricao(CriterioDescricaoTurma criterioDescricao) {
        this.setCriterioDescricao(criterioDescricao);
        return this;
    }

    public void setCriterioDescricao(CriterioDescricaoTurma criterioDescricao) {
        this.criterioDescricao = criterioDescricao;
    }

    public CriterioNumeroChamada getCriterioOrdenacaoNumero() {
        return this.criterioOrdenacaoNumero;
    }

    public Turma criterioOrdenacaoNumero(CriterioNumeroChamada criterioOrdenacaoNumero) {
        this.setCriterioOrdenacaoNumero(criterioOrdenacaoNumero);
        return this;
    }

    public void setCriterioOrdenacaoNumero(CriterioNumeroChamada criterioOrdenacaoNumero) {
        this.criterioOrdenacaoNumero = criterioOrdenacaoNumero;
    }

    public Boolean getFazInscricaoDepoisMatricula() {
        return this.fazInscricaoDepoisMatricula;
    }

    public Turma fazInscricaoDepoisMatricula(Boolean fazInscricaoDepoisMatricula) {
        this.setFazInscricaoDepoisMatricula(fazInscricaoDepoisMatricula);
        return this;
    }

    public void setFazInscricaoDepoisMatricula(Boolean fazInscricaoDepoisMatricula) {
        this.fazInscricaoDepoisMatricula = fazInscricaoDepoisMatricula;
    }

    public Boolean getIsDisponivel() {
        return this.isDisponivel;
    }

    public Turma isDisponivel(Boolean isDisponivel) {
        this.setIsDisponivel(isDisponivel);
        return this;
    }

    public void setIsDisponivel(Boolean isDisponivel) {
        this.isDisponivel = isDisponivel;
    }

    public Set<Turma> getTurmas() {
        return this.turmas;
    }

    public void setTurmas(Set<Turma> turmas) {
        if (this.turmas != null) {
            this.turmas.forEach(i -> i.setReferencia(null));
        }
        if (turmas != null) {
            turmas.forEach(i -> i.setReferencia(this));
        }
        this.turmas = turmas;
    }

    public Turma turmas(Set<Turma> turmas) {
        this.setTurmas(turmas);
        return this;
    }

    public Turma addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.setReferencia(this);
        return this;
    }

    public Turma removeTurma(Turma turma) {
        this.turmas.remove(turma);
        turma.setReferencia(null);
        return this;
    }

    public Set<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        if (this.horarios != null) {
            this.horarios.forEach(i -> i.setTurma(null));
        }
        if (horarios != null) {
            horarios.forEach(i -> i.setTurma(this));
        }
        this.horarios = horarios;
    }

    public Turma horarios(Set<Horario> horarios) {
        this.setHorarios(horarios);
        return this;
    }

    public Turma addHorarios(Horario horario) {
        this.horarios.add(horario);
        horario.setTurma(this);
        return this;
    }

    public Turma removeHorarios(Horario horario) {
        this.horarios.remove(horario);
        horario.setTurma(null);
        return this;
    }

    public Set<NotasPeriodicaDisciplina> getNotasPeriodicaDisciplinas() {
        return this.notasPeriodicaDisciplinas;
    }

    public void setNotasPeriodicaDisciplinas(Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas) {
        if (this.notasPeriodicaDisciplinas != null) {
            this.notasPeriodicaDisciplinas.forEach(i -> i.setTurma(null));
        }
        if (notasPeriodicaDisciplinas != null) {
            notasPeriodicaDisciplinas.forEach(i -> i.setTurma(this));
        }
        this.notasPeriodicaDisciplinas = notasPeriodicaDisciplinas;
    }

    public Turma notasPeriodicaDisciplinas(Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas) {
        this.setNotasPeriodicaDisciplinas(notasPeriodicaDisciplinas);
        return this;
    }

    public Turma addNotasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplinas.add(notasPeriodicaDisciplina);
        notasPeriodicaDisciplina.setTurma(this);
        return this;
    }

    public Turma removeNotasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplinas.remove(notasPeriodicaDisciplina);
        notasPeriodicaDisciplina.setTurma(null);
        return this;
    }

    public Set<ProcessoSelectivoMatricula> getProcessoSelectivoMatriculas() {
        return this.processoSelectivoMatriculas;
    }

    public void setProcessoSelectivoMatriculas(Set<ProcessoSelectivoMatricula> processoSelectivoMatriculas) {
        if (this.processoSelectivoMatriculas != null) {
            this.processoSelectivoMatriculas.forEach(i -> i.setTurma(null));
        }
        if (processoSelectivoMatriculas != null) {
            processoSelectivoMatriculas.forEach(i -> i.setTurma(this));
        }
        this.processoSelectivoMatriculas = processoSelectivoMatriculas;
    }

    public Turma processoSelectivoMatriculas(Set<ProcessoSelectivoMatricula> processoSelectivoMatriculas) {
        this.setProcessoSelectivoMatriculas(processoSelectivoMatriculas);
        return this;
    }

    public Turma addProcessoSelectivoMatricula(ProcessoSelectivoMatricula processoSelectivoMatricula) {
        this.processoSelectivoMatriculas.add(processoSelectivoMatricula);
        processoSelectivoMatricula.setTurma(this);
        return this;
    }

    public Turma removeProcessoSelectivoMatricula(ProcessoSelectivoMatricula processoSelectivoMatricula) {
        this.processoSelectivoMatriculas.remove(processoSelectivoMatricula);
        processoSelectivoMatricula.setTurma(null);
        return this;
    }

    public Set<PlanoAula> getPlanoAulas() {
        return this.planoAulas;
    }

    public void setPlanoAulas(Set<PlanoAula> planoAulas) {
        if (this.planoAulas != null) {
            this.planoAulas.forEach(i -> i.setTurma(null));
        }
        if (planoAulas != null) {
            planoAulas.forEach(i -> i.setTurma(this));
        }
        this.planoAulas = planoAulas;
    }

    public Turma planoAulas(Set<PlanoAula> planoAulas) {
        this.setPlanoAulas(planoAulas);
        return this;
    }

    public Turma addPlanoAula(PlanoAula planoAula) {
        this.planoAulas.add(planoAula);
        planoAula.setTurma(this);
        return this;
    }

    public Turma removePlanoAula(PlanoAula planoAula) {
        this.planoAulas.remove(planoAula);
        planoAula.setTurma(null);
        return this;
    }

    public Set<Matricula> getMatriculas() {
        return this.matriculas;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        if (this.matriculas != null) {
            this.matriculas.forEach(i -> i.setTurma(null));
        }
        if (matriculas != null) {
            matriculas.forEach(i -> i.setTurma(this));
        }
        this.matriculas = matriculas;
    }

    public Turma matriculas(Set<Matricula> matriculas) {
        this.setMatriculas(matriculas);
        return this;
    }

    public Turma addMatriculas(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setTurma(this);
        return this;
    }

    public Turma removeMatriculas(Matricula matricula) {
        this.matriculas.remove(matricula);
        matricula.setTurma(null);
        return this;
    }

    public Set<ResumoAcademico> getResumoAcademicos() {
        return this.resumoAcademicos;
    }

    public void setResumoAcademicos(Set<ResumoAcademico> resumoAcademicos) {
        if (this.resumoAcademicos != null) {
            this.resumoAcademicos.forEach(i -> i.setUltimaTurmaMatriculada(null));
        }
        if (resumoAcademicos != null) {
            resumoAcademicos.forEach(i -> i.setUltimaTurmaMatriculada(this));
        }
        this.resumoAcademicos = resumoAcademicos;
    }

    public Turma resumoAcademicos(Set<ResumoAcademico> resumoAcademicos) {
        this.setResumoAcademicos(resumoAcademicos);
        return this;
    }

    public Turma addResumoAcademico(ResumoAcademico resumoAcademico) {
        this.resumoAcademicos.add(resumoAcademico);
        resumoAcademico.setUltimaTurmaMatriculada(this);
        return this;
    }

    public Turma removeResumoAcademico(ResumoAcademico resumoAcademico) {
        this.resumoAcademicos.remove(resumoAcademico);
        resumoAcademico.setUltimaTurmaMatriculada(null);
        return this;
    }

    public Set<ResponsavelTurma> getResponsaveis() {
        return this.responsaveis;
    }

    public void setResponsaveis(Set<ResponsavelTurma> responsavelTurmas) {
        if (this.responsaveis != null) {
            this.responsaveis.forEach(i -> i.setTurma(null));
        }
        if (responsavelTurmas != null) {
            responsavelTurmas.forEach(i -> i.setTurma(this));
        }
        this.responsaveis = responsavelTurmas;
    }

    public Turma responsaveis(Set<ResponsavelTurma> responsavelTurmas) {
        this.setResponsaveis(responsavelTurmas);
        return this;
    }

    public Turma addResponsaveis(ResponsavelTurma responsavelTurma) {
        this.responsaveis.add(responsavelTurma);
        responsavelTurma.setTurma(this);
        return this;
    }

    public Turma removeResponsaveis(ResponsavelTurma responsavelTurma) {
        this.responsaveis.remove(responsavelTurma);
        responsavelTurma.setTurma(null);
        return this;
    }

    public Set<DissertacaoFinalCurso> getDissertacaoFinalCursos() {
        return this.dissertacaoFinalCursos;
    }

    public void setDissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        if (this.dissertacaoFinalCursos != null) {
            this.dissertacaoFinalCursos.forEach(i -> i.setTurma(null));
        }
        if (dissertacaoFinalCursos != null) {
            dissertacaoFinalCursos.forEach(i -> i.setTurma(this));
        }
        this.dissertacaoFinalCursos = dissertacaoFinalCursos;
    }

    public Turma dissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        this.setDissertacaoFinalCursos(dissertacaoFinalCursos);
        return this;
    }

    public Turma addDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.add(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setTurma(this);
        return this;
    }

    public Turma removeDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.remove(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setTurma(null);
        return this;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setTurma(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setTurma(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public Turma anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public Turma addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setTurma(this);
        return this;
    }

    public Turma removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setTurma(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Turma utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Turma getReferencia() {
        return this.referencia;
    }

    public void setReferencia(Turma turma) {
        this.referencia = turma;
    }

    public Turma referencia(Turma turma) {
        this.setReferencia(turma);
        return this;
    }

    public PlanoCurricular getPlanoCurricular() {
        return this.planoCurricular;
    }

    public void setPlanoCurricular(PlanoCurricular planoCurricular) {
        this.planoCurricular = planoCurricular;
    }

    public Turma planoCurricular(PlanoCurricular planoCurricular) {
        this.setPlanoCurricular(planoCurricular);
        return this;
    }

    public Turno getTurno() {
        return this.turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Turma turno(Turno turno) {
        this.setTurno(turno);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Turma)) {
            return false;
        }
        return id != null && id.equals(((Turma) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Turma{" +
            "id=" + getId() +
            ", chaveComposta='" + getChaveComposta() + "'" +
            ", tipoTurma='" + getTipoTurma() + "'" +
            ", sala=" + getSala() +
            ", descricao='" + getDescricao() + "'" +
            ", lotacao=" + getLotacao() +
            ", confirmado=" + getConfirmado() +
            ", abertura='" + getAbertura() + "'" +
            ", encerramento='" + getEncerramento() + "'" +
            ", criterioDescricao='" + getCriterioDescricao() + "'" +
            ", criterioOrdenacaoNumero='" + getCriterioOrdenacaoNumero() + "'" +
            ", fazInscricaoDepoisMatricula='" + getFazInscricaoDepoisMatricula() + "'" +
            ", isDisponivel='" + getIsDisponivel() + "'" +
            "}";
    }
}
