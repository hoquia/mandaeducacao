package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlanoCurricular.
 */
@Entity
@Table(name = "plano_curricular")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoCurricular implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "formula_classificacao_final", nullable = false)
    private String formulaClassificacaoFinal;

    @NotNull
    @Min(value = 0)
    @Column(name = "numero_disciplina_aprova", nullable = false)
    private Integer numeroDisciplinaAprova;

    @NotNull
    @Min(value = 0)
    @Column(name = "numero_disciplina_reprova", nullable = false)
    private Integer numeroDisciplinaReprova;

    @NotNull
    @Min(value = 0)
    @Column(name = "numero_disciplina_recurso", nullable = false)
    private Integer numeroDisciplinaRecurso;

    @NotNull
    @Min(value = 0)
    @Column(name = "numero_disciplina_exame", nullable = false)
    private Integer numeroDisciplinaExame;

    @NotNull
    @Min(value = 0)
    @Column(name = "numero_disciplina_exame_especial", nullable = false)
    private Integer numeroDisciplinaExameEspecial;

    @NotNull
    @Min(value = 0)
    @Column(name = "numero_falta_reprova", nullable = false)
    private Integer numeroFaltaReprova;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "peso_media_1")
    private Double pesoMedia1;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "peso_media_2")
    private Double pesoMedia2;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "peso_media_3")
    private Double pesoMedia3;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "peso_recurso")
    private Double pesoRecurso;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "peso_exame")
    private Double pesoExame;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "peso_exame_especial")
    private Double pesoExameEspecial;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "peso_nota_coselho")
    private Double pesoNotaCoselho;

    @NotNull
    @Column(name = "sigla_prova_1", nullable = false)
    private String siglaProva1;

    @NotNull
    @Column(name = "sigla_prova_2", nullable = false)
    private String siglaProva2;

    @NotNull
    @Column(name = "sigla_prova_3", nullable = false)
    private String siglaProva3;

    @NotNull
    @Column(name = "sigla_media_1", nullable = false)
    private String siglaMedia1;

    @NotNull
    @Column(name = "sigla_media_2", nullable = false)
    private String siglaMedia2;

    @NotNull
    @Column(name = "sigla_media_3", nullable = false)
    private String siglaMedia3;

    @NotNull
    @Column(name = "formula_media", nullable = false)
    private String formulaMedia;

    @NotNull
    @Column(name = "formula_dispensa", nullable = false)
    private String formulaDispensa;

    @NotNull
    @Column(name = "formula_exame", nullable = false)
    private String formulaExame;

    @NotNull
    @Column(name = "formula_recurso", nullable = false)
    private String formulaRecurso;

    @NotNull
    @Column(name = "formula_exame_especial", nullable = false)
    private String formulaExameEspecial;

    @OneToMany(mappedBy = "planoCurricular")
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

    @ManyToOne
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "planoCurriculars", "precoEmolumentos", "nivesEnsinos", "periodosLancamentoNotas" },
        allowSetters = true
    )
    private Classe classe;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "planoCurriculars", "responsaveis", "precoEmolumentos", "areaFormacao", "camposActuacaos" },
        allowSetters = true
    )
    private Curso curso;

    @ManyToMany(mappedBy = "planosCurriculars")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "disciplinaCurriculars",
            "horarios",
            "planoAulas",
            "notasGeralDisciplinas",
            "notasPeriodicaDisciplinas",
            "componente",
            "regime",
            "planosCurriculars",
            "disciplina",
            "referencia",
            "estados",
        },
        allowSetters = true
    )
    private Set<DisciplinaCurricular> disciplinasCurriculars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanoCurricular id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public PlanoCurricular descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFormulaClassificacaoFinal() {
        return this.formulaClassificacaoFinal;
    }

    public PlanoCurricular formulaClassificacaoFinal(String formulaClassificacaoFinal) {
        this.setFormulaClassificacaoFinal(formulaClassificacaoFinal);
        return this;
    }

    public void setFormulaClassificacaoFinal(String formulaClassificacaoFinal) {
        this.formulaClassificacaoFinal = formulaClassificacaoFinal;
    }

    public Integer getNumeroDisciplinaAprova() {
        return this.numeroDisciplinaAprova;
    }

    public PlanoCurricular numeroDisciplinaAprova(Integer numeroDisciplinaAprova) {
        this.setNumeroDisciplinaAprova(numeroDisciplinaAprova);
        return this;
    }

    public void setNumeroDisciplinaAprova(Integer numeroDisciplinaAprova) {
        this.numeroDisciplinaAprova = numeroDisciplinaAprova;
    }

    public Integer getNumeroDisciplinaReprova() {
        return this.numeroDisciplinaReprova;
    }

    public PlanoCurricular numeroDisciplinaReprova(Integer numeroDisciplinaReprova) {
        this.setNumeroDisciplinaReprova(numeroDisciplinaReprova);
        return this;
    }

    public void setNumeroDisciplinaReprova(Integer numeroDisciplinaReprova) {
        this.numeroDisciplinaReprova = numeroDisciplinaReprova;
    }

    public Integer getNumeroDisciplinaRecurso() {
        return this.numeroDisciplinaRecurso;
    }

    public PlanoCurricular numeroDisciplinaRecurso(Integer numeroDisciplinaRecurso) {
        this.setNumeroDisciplinaRecurso(numeroDisciplinaRecurso);
        return this;
    }

    public void setNumeroDisciplinaRecurso(Integer numeroDisciplinaRecurso) {
        this.numeroDisciplinaRecurso = numeroDisciplinaRecurso;
    }

    public Integer getNumeroDisciplinaExame() {
        return this.numeroDisciplinaExame;
    }

    public PlanoCurricular numeroDisciplinaExame(Integer numeroDisciplinaExame) {
        this.setNumeroDisciplinaExame(numeroDisciplinaExame);
        return this;
    }

    public void setNumeroDisciplinaExame(Integer numeroDisciplinaExame) {
        this.numeroDisciplinaExame = numeroDisciplinaExame;
    }

    public Integer getNumeroDisciplinaExameEspecial() {
        return this.numeroDisciplinaExameEspecial;
    }

    public PlanoCurricular numeroDisciplinaExameEspecial(Integer numeroDisciplinaExameEspecial) {
        this.setNumeroDisciplinaExameEspecial(numeroDisciplinaExameEspecial);
        return this;
    }

    public void setNumeroDisciplinaExameEspecial(Integer numeroDisciplinaExameEspecial) {
        this.numeroDisciplinaExameEspecial = numeroDisciplinaExameEspecial;
    }

    public Integer getNumeroFaltaReprova() {
        return this.numeroFaltaReprova;
    }

    public PlanoCurricular numeroFaltaReprova(Integer numeroFaltaReprova) {
        this.setNumeroFaltaReprova(numeroFaltaReprova);
        return this;
    }

    public void setNumeroFaltaReprova(Integer numeroFaltaReprova) {
        this.numeroFaltaReprova = numeroFaltaReprova;
    }

    public Double getPesoMedia1() {
        return this.pesoMedia1;
    }

    public PlanoCurricular pesoMedia1(Double pesoMedia1) {
        this.setPesoMedia1(pesoMedia1);
        return this;
    }

    public void setPesoMedia1(Double pesoMedia1) {
        this.pesoMedia1 = pesoMedia1;
    }

    public Double getPesoMedia2() {
        return this.pesoMedia2;
    }

    public PlanoCurricular pesoMedia2(Double pesoMedia2) {
        this.setPesoMedia2(pesoMedia2);
        return this;
    }

    public void setPesoMedia2(Double pesoMedia2) {
        this.pesoMedia2 = pesoMedia2;
    }

    public Double getPesoMedia3() {
        return this.pesoMedia3;
    }

    public PlanoCurricular pesoMedia3(Double pesoMedia3) {
        this.setPesoMedia3(pesoMedia3);
        return this;
    }

    public void setPesoMedia3(Double pesoMedia3) {
        this.pesoMedia3 = pesoMedia3;
    }

    public Double getPesoRecurso() {
        return this.pesoRecurso;
    }

    public PlanoCurricular pesoRecurso(Double pesoRecurso) {
        this.setPesoRecurso(pesoRecurso);
        return this;
    }

    public void setPesoRecurso(Double pesoRecurso) {
        this.pesoRecurso = pesoRecurso;
    }

    public Double getPesoExame() {
        return this.pesoExame;
    }

    public PlanoCurricular pesoExame(Double pesoExame) {
        this.setPesoExame(pesoExame);
        return this;
    }

    public void setPesoExame(Double pesoExame) {
        this.pesoExame = pesoExame;
    }

    public Double getPesoExameEspecial() {
        return this.pesoExameEspecial;
    }

    public PlanoCurricular pesoExameEspecial(Double pesoExameEspecial) {
        this.setPesoExameEspecial(pesoExameEspecial);
        return this;
    }

    public void setPesoExameEspecial(Double pesoExameEspecial) {
        this.pesoExameEspecial = pesoExameEspecial;
    }

    public Double getPesoNotaCoselho() {
        return this.pesoNotaCoselho;
    }

    public PlanoCurricular pesoNotaCoselho(Double pesoNotaCoselho) {
        this.setPesoNotaCoselho(pesoNotaCoselho);
        return this;
    }

    public void setPesoNotaCoselho(Double pesoNotaCoselho) {
        this.pesoNotaCoselho = pesoNotaCoselho;
    }

    public String getSiglaProva1() {
        return this.siglaProva1;
    }

    public PlanoCurricular siglaProva1(String siglaProva1) {
        this.setSiglaProva1(siglaProva1);
        return this;
    }

    public void setSiglaProva1(String siglaProva1) {
        this.siglaProva1 = siglaProva1;
    }

    public String getSiglaProva2() {
        return this.siglaProva2;
    }

    public PlanoCurricular siglaProva2(String siglaProva2) {
        this.setSiglaProva2(siglaProva2);
        return this;
    }

    public void setSiglaProva2(String siglaProva2) {
        this.siglaProva2 = siglaProva2;
    }

    public String getSiglaProva3() {
        return this.siglaProva3;
    }

    public PlanoCurricular siglaProva3(String siglaProva3) {
        this.setSiglaProva3(siglaProva3);
        return this;
    }

    public void setSiglaProva3(String siglaProva3) {
        this.siglaProva3 = siglaProva3;
    }

    public String getSiglaMedia1() {
        return this.siglaMedia1;
    }

    public PlanoCurricular siglaMedia1(String siglaMedia1) {
        this.setSiglaMedia1(siglaMedia1);
        return this;
    }

    public void setSiglaMedia1(String siglaMedia1) {
        this.siglaMedia1 = siglaMedia1;
    }

    public String getSiglaMedia2() {
        return this.siglaMedia2;
    }

    public PlanoCurricular siglaMedia2(String siglaMedia2) {
        this.setSiglaMedia2(siglaMedia2);
        return this;
    }

    public void setSiglaMedia2(String siglaMedia2) {
        this.siglaMedia2 = siglaMedia2;
    }

    public String getSiglaMedia3() {
        return this.siglaMedia3;
    }

    public PlanoCurricular siglaMedia3(String siglaMedia3) {
        this.setSiglaMedia3(siglaMedia3);
        return this;
    }

    public void setSiglaMedia3(String siglaMedia3) {
        this.siglaMedia3 = siglaMedia3;
    }

    public String getFormulaMedia() {
        return this.formulaMedia;
    }

    public PlanoCurricular formulaMedia(String formulaMedia) {
        this.setFormulaMedia(formulaMedia);
        return this;
    }

    public void setFormulaMedia(String formulaMedia) {
        this.formulaMedia = formulaMedia;
    }

    public String getFormulaDispensa() {
        return this.formulaDispensa;
    }

    public PlanoCurricular formulaDispensa(String formulaDispensa) {
        this.setFormulaDispensa(formulaDispensa);
        return this;
    }

    public void setFormulaDispensa(String formulaDispensa) {
        this.formulaDispensa = formulaDispensa;
    }

    public String getFormulaExame() {
        return this.formulaExame;
    }

    public PlanoCurricular formulaExame(String formulaExame) {
        this.setFormulaExame(formulaExame);
        return this;
    }

    public void setFormulaExame(String formulaExame) {
        this.formulaExame = formulaExame;
    }

    public String getFormulaRecurso() {
        return this.formulaRecurso;
    }

    public PlanoCurricular formulaRecurso(String formulaRecurso) {
        this.setFormulaRecurso(formulaRecurso);
        return this;
    }

    public void setFormulaRecurso(String formulaRecurso) {
        this.formulaRecurso = formulaRecurso;
    }

    public String getFormulaExameEspecial() {
        return this.formulaExameEspecial;
    }

    public PlanoCurricular formulaExameEspecial(String formulaExameEspecial) {
        this.setFormulaExameEspecial(formulaExameEspecial);
        return this;
    }

    public void setFormulaExameEspecial(String formulaExameEspecial) {
        this.formulaExameEspecial = formulaExameEspecial;
    }

    public Set<Turma> getTurmas() {
        return this.turmas;
    }

    public void setTurmas(Set<Turma> turmas) {
        if (this.turmas != null) {
            this.turmas.forEach(i -> i.setPlanoCurricular(null));
        }
        if (turmas != null) {
            turmas.forEach(i -> i.setPlanoCurricular(this));
        }
        this.turmas = turmas;
    }

    public PlanoCurricular turmas(Set<Turma> turmas) {
        this.setTurmas(turmas);
        return this;
    }

    public PlanoCurricular addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.setPlanoCurricular(this);
        return this;
    }

    public PlanoCurricular removeTurma(Turma turma) {
        this.turmas.remove(turma);
        turma.setPlanoCurricular(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public PlanoCurricular utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Classe getClasse() {
        return this.classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public PlanoCurricular classe(Classe classe) {
        this.setClasse(classe);
        return this;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public PlanoCurricular curso(Curso curso) {
        this.setCurso(curso);
        return this;
    }

    public Set<DisciplinaCurricular> getDisciplinasCurriculars() {
        return this.disciplinasCurriculars;
    }

    public void setDisciplinasCurriculars(Set<DisciplinaCurricular> disciplinaCurriculars) {
        if (this.disciplinasCurriculars != null) {
            this.disciplinasCurriculars.forEach(i -> i.removePlanosCurricular(this));
        }
        if (disciplinaCurriculars != null) {
            disciplinaCurriculars.forEach(i -> i.addPlanosCurricular(this));
        }
        this.disciplinasCurriculars = disciplinaCurriculars;
    }

    public PlanoCurricular disciplinasCurriculars(Set<DisciplinaCurricular> disciplinaCurriculars) {
        this.setDisciplinasCurriculars(disciplinaCurriculars);
        return this;
    }

    public PlanoCurricular addDisciplinasCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinasCurriculars.add(disciplinaCurricular);
        disciplinaCurricular.getPlanosCurriculars().add(this);
        return this;
    }

    public PlanoCurricular removeDisciplinasCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinasCurriculars.remove(disciplinaCurricular);
        disciplinaCurricular.getPlanosCurriculars().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoCurricular)) {
            return false;
        }
        return id != null && id.equals(((PlanoCurricular) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoCurricular{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", formulaClassificacaoFinal='" + getFormulaClassificacaoFinal() + "'" +
            ", numeroDisciplinaAprova=" + getNumeroDisciplinaAprova() +
            ", numeroDisciplinaReprova=" + getNumeroDisciplinaReprova() +
            ", numeroDisciplinaRecurso=" + getNumeroDisciplinaRecurso() +
            ", numeroDisciplinaExame=" + getNumeroDisciplinaExame() +
            ", numeroDisciplinaExameEspecial=" + getNumeroDisciplinaExameEspecial() +
            ", numeroFaltaReprova=" + getNumeroFaltaReprova() +
            ", pesoMedia1=" + getPesoMedia1() +
            ", pesoMedia2=" + getPesoMedia2() +
            ", pesoMedia3=" + getPesoMedia3() +
            ", pesoRecurso=" + getPesoRecurso() +
            ", pesoExame=" + getPesoExame() +
            ", pesoExameEspecial=" + getPesoExameEspecial() +
            ", pesoNotaCoselho=" + getPesoNotaCoselho() +
            ", siglaProva1='" + getSiglaProva1() + "'" +
            ", siglaProva2='" + getSiglaProva2() + "'" +
            ", siglaProva3='" + getSiglaProva3() + "'" +
            ", siglaMedia1='" + getSiglaMedia1() + "'" +
            ", siglaMedia2='" + getSiglaMedia2() + "'" +
            ", siglaMedia3='" + getSiglaMedia3() + "'" +
            ", formulaMedia='" + getFormulaMedia() + "'" +
            ", formulaDispensa='" + getFormulaDispensa() + "'" +
            ", formulaExame='" + getFormulaExame() + "'" +
            ", formulaRecurso='" + getFormulaRecurso() + "'" +
            ", formulaExameEspecial='" + getFormulaExameEspecial() + "'" +
            "}";
    }
}
