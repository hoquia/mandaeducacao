package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.CategoriaClassificacao;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EstadoDisciplinaCurricular.
 */
@Entity
@Table(name = "estado_disciplina_curricular")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoDisciplinaCurricular implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_situacao_disciplina", unique = true)
    private String uniqueSituacaoDisciplina;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao", nullable = false)
    private CategoriaClassificacao classificacao;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "cor", nullable = false)
    private String cor;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor", nullable = false)
    private Double valor;

    @OneToMany(mappedBy = "referencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "estadoDisciplinaCurriculars",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "resumoAcademicos",
            "disciplinasCurriculars",
            "referencia",
        },
        allowSetters = true
    )
    private Set<EstadoDisciplinaCurricular> estadoDisciplinaCurriculars = new HashSet<>();

    @OneToMany(mappedBy = "estado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "docente", "disciplinaCurricular", "matricula", "estado" },
        allowSetters = true
    )
    private Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas = new HashSet<>();

    @OneToMany(mappedBy = "estado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "docente", "disciplinaCurricular", "matricula", "estado" },
        allowSetters = true
    )
    private Set<NotasGeralDisciplina> notasGeralDisciplinas = new HashSet<>();

    @OneToMany(mappedBy = "situacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilizador", "ultimaTurmaMatriculada", "discente", "situacao" }, allowSetters = true)
    private Set<ResumoAcademico> resumoAcademicos = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_estado_disciplina_curricular__disciplinas_curriculars",
        joinColumns = @JoinColumn(name = "estado_disciplina_curricular_id"),
        inverseJoinColumns = @JoinColumn(name = "disciplinas_curriculars_id")
    )
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

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "estadoDisciplinaCurriculars",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "resumoAcademicos",
            "disciplinasCurriculars",
            "referencia",
        },
        allowSetters = true
    )
    private EstadoDisciplinaCurricular referencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EstadoDisciplinaCurricular id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueSituacaoDisciplina() {
        return this.uniqueSituacaoDisciplina;
    }

    public EstadoDisciplinaCurricular uniqueSituacaoDisciplina(String uniqueSituacaoDisciplina) {
        this.setUniqueSituacaoDisciplina(uniqueSituacaoDisciplina);
        return this;
    }

    public void setUniqueSituacaoDisciplina(String uniqueSituacaoDisciplina) {
        this.uniqueSituacaoDisciplina = uniqueSituacaoDisciplina;
    }

    public CategoriaClassificacao getClassificacao() {
        return this.classificacao;
    }

    public EstadoDisciplinaCurricular classificacao(CategoriaClassificacao classificacao) {
        this.setClassificacao(classificacao);
        return this;
    }

    public void setClassificacao(CategoriaClassificacao classificacao) {
        this.classificacao = classificacao;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public EstadoDisciplinaCurricular codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public EstadoDisciplinaCurricular descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return this.cor;
    }

    public EstadoDisciplinaCurricular cor(String cor) {
        this.setCor(cor);
        return this;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Double getValor() {
        return this.valor;
    }

    public EstadoDisciplinaCurricular valor(Double valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Set<EstadoDisciplinaCurricular> getEstadoDisciplinaCurriculars() {
        return this.estadoDisciplinaCurriculars;
    }

    public void setEstadoDisciplinaCurriculars(Set<EstadoDisciplinaCurricular> estadoDisciplinaCurriculars) {
        if (this.estadoDisciplinaCurriculars != null) {
            this.estadoDisciplinaCurriculars.forEach(i -> i.setReferencia(null));
        }
        if (estadoDisciplinaCurriculars != null) {
            estadoDisciplinaCurriculars.forEach(i -> i.setReferencia(this));
        }
        this.estadoDisciplinaCurriculars = estadoDisciplinaCurriculars;
    }

    public EstadoDisciplinaCurricular estadoDisciplinaCurriculars(Set<EstadoDisciplinaCurricular> estadoDisciplinaCurriculars) {
        this.setEstadoDisciplinaCurriculars(estadoDisciplinaCurriculars);
        return this;
    }

    public EstadoDisciplinaCurricular addEstadoDisciplinaCurricular(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.estadoDisciplinaCurriculars.add(estadoDisciplinaCurricular);
        estadoDisciplinaCurricular.setReferencia(this);
        return this;
    }

    public EstadoDisciplinaCurricular removeEstadoDisciplinaCurricular(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.estadoDisciplinaCurriculars.remove(estadoDisciplinaCurricular);
        estadoDisciplinaCurricular.setReferencia(null);
        return this;
    }

    public Set<NotasPeriodicaDisciplina> getNotasPeriodicaDisciplinas() {
        return this.notasPeriodicaDisciplinas;
    }

    public void setNotasPeriodicaDisciplinas(Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas) {
        if (this.notasPeriodicaDisciplinas != null) {
            this.notasPeriodicaDisciplinas.forEach(i -> i.setEstado(null));
        }
        if (notasPeriodicaDisciplinas != null) {
            notasPeriodicaDisciplinas.forEach(i -> i.setEstado(this));
        }
        this.notasPeriodicaDisciplinas = notasPeriodicaDisciplinas;
    }

    public EstadoDisciplinaCurricular notasPeriodicaDisciplinas(Set<NotasPeriodicaDisciplina> notasPeriodicaDisciplinas) {
        this.setNotasPeriodicaDisciplinas(notasPeriodicaDisciplinas);
        return this;
    }

    public EstadoDisciplinaCurricular addNotasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplinas.add(notasPeriodicaDisciplina);
        notasPeriodicaDisciplina.setEstado(this);
        return this;
    }

    public EstadoDisciplinaCurricular removeNotasPeriodicaDisciplina(NotasPeriodicaDisciplina notasPeriodicaDisciplina) {
        this.notasPeriodicaDisciplinas.remove(notasPeriodicaDisciplina);
        notasPeriodicaDisciplina.setEstado(null);
        return this;
    }

    public Set<NotasGeralDisciplina> getNotasGeralDisciplinas() {
        return this.notasGeralDisciplinas;
    }

    public void setNotasGeralDisciplinas(Set<NotasGeralDisciplina> notasGeralDisciplinas) {
        if (this.notasGeralDisciplinas != null) {
            this.notasGeralDisciplinas.forEach(i -> i.setEstado(null));
        }
        if (notasGeralDisciplinas != null) {
            notasGeralDisciplinas.forEach(i -> i.setEstado(this));
        }
        this.notasGeralDisciplinas = notasGeralDisciplinas;
    }

    public EstadoDisciplinaCurricular notasGeralDisciplinas(Set<NotasGeralDisciplina> notasGeralDisciplinas) {
        this.setNotasGeralDisciplinas(notasGeralDisciplinas);
        return this;
    }

    public EstadoDisciplinaCurricular addNotasGeralDisciplina(NotasGeralDisciplina notasGeralDisciplina) {
        this.notasGeralDisciplinas.add(notasGeralDisciplina);
        notasGeralDisciplina.setEstado(this);
        return this;
    }

    public EstadoDisciplinaCurricular removeNotasGeralDisciplina(NotasGeralDisciplina notasGeralDisciplina) {
        this.notasGeralDisciplinas.remove(notasGeralDisciplina);
        notasGeralDisciplina.setEstado(null);
        return this;
    }

    public Set<ResumoAcademico> getResumoAcademicos() {
        return this.resumoAcademicos;
    }

    public void setResumoAcademicos(Set<ResumoAcademico> resumoAcademicos) {
        if (this.resumoAcademicos != null) {
            this.resumoAcademicos.forEach(i -> i.setSituacao(null));
        }
        if (resumoAcademicos != null) {
            resumoAcademicos.forEach(i -> i.setSituacao(this));
        }
        this.resumoAcademicos = resumoAcademicos;
    }

    public EstadoDisciplinaCurricular resumoAcademicos(Set<ResumoAcademico> resumoAcademicos) {
        this.setResumoAcademicos(resumoAcademicos);
        return this;
    }

    public EstadoDisciplinaCurricular addResumoAcademico(ResumoAcademico resumoAcademico) {
        this.resumoAcademicos.add(resumoAcademico);
        resumoAcademico.setSituacao(this);
        return this;
    }

    public EstadoDisciplinaCurricular removeResumoAcademico(ResumoAcademico resumoAcademico) {
        this.resumoAcademicos.remove(resumoAcademico);
        resumoAcademico.setSituacao(null);
        return this;
    }

    public Set<DisciplinaCurricular> getDisciplinasCurriculars() {
        return this.disciplinasCurriculars;
    }

    public void setDisciplinasCurriculars(Set<DisciplinaCurricular> disciplinaCurriculars) {
        this.disciplinasCurriculars = disciplinaCurriculars;
    }

    public EstadoDisciplinaCurricular disciplinasCurriculars(Set<DisciplinaCurricular> disciplinaCurriculars) {
        this.setDisciplinasCurriculars(disciplinaCurriculars);
        return this;
    }

    public EstadoDisciplinaCurricular addDisciplinasCurriculars(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinasCurriculars.add(disciplinaCurricular);
        disciplinaCurricular.getEstados().add(this);
        return this;
    }

    public EstadoDisciplinaCurricular removeDisciplinasCurriculars(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinasCurriculars.remove(disciplinaCurricular);
        disciplinaCurricular.getEstados().remove(this);
        return this;
    }

    public EstadoDisciplinaCurricular getReferencia() {
        return this.referencia;
    }

    public void setReferencia(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.referencia = estadoDisciplinaCurricular;
    }

    public EstadoDisciplinaCurricular referencia(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.setReferencia(estadoDisciplinaCurricular);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoDisciplinaCurricular)) {
            return false;
        }
        return id != null && id.equals(((EstadoDisciplinaCurricular) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoDisciplinaCurricular{" +
            "id=" + getId() +
            ", uniqueSituacaoDisciplina='" + getUniqueSituacaoDisciplina() + "'" +
            ", classificacao='" + getClassificacao() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", cor='" + getCor() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
