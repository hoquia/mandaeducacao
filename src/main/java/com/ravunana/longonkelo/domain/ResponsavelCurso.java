package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A ResponsavelCurso.
 */
@Entity
@Table(name = "responsavel_curso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResponsavelCurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "de", nullable = false)
    private LocalDate de;

    @NotNull
    @Column(name = "ate", nullable = false)
    private LocalDate ate;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @OneToMany(mappedBy = "responsavelCurso")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "ocorrencias",
            "horarios",
            "planoAulas",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "dissertacaoFinalCursos",
            "categoriaOcorrencias",
            "formacoes",
            "nacionalidade",
            "naturalidade",
            "tipoDocumento",
            "grauAcademico",
            "categoriaProfissional",
            "unidadeOrganica",
            "estadoCivil",
            "responsavelTurno",
            "responsavelAreaFormacao",
            "responsavelCurso",
            "responsavelDisciplina",
            "responsavelTurma",
        },
        allowSetters = true
    )
    private Set<Docente> responsavels = new HashSet<>();

    @OneToMany(mappedBy = "responsavelCurso")
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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "planoCurriculars", "responsaveis", "precoEmolumentos", "areaFormacao", "camposActuacaos" },
        allowSetters = true
    )
    private Curso curso;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResponsavelCurso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDe() {
        return this.de;
    }

    public ResponsavelCurso de(LocalDate de) {
        this.setDe(de);
        return this;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public LocalDate getAte() {
        return this.ate;
    }

    public ResponsavelCurso ate(LocalDate ate) {
        this.setAte(ate);
        return this;
    }

    public void setAte(LocalDate ate) {
        this.ate = ate;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public ResponsavelCurso descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public ResponsavelCurso timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Set<Docente> getResponsavels() {
        return this.responsavels;
    }

    public void setResponsavels(Set<Docente> docentes) {
        if (this.responsavels != null) {
            this.responsavels.forEach(i -> i.setResponsavelCurso(null));
        }
        if (docentes != null) {
            docentes.forEach(i -> i.setResponsavelCurso(this));
        }
        this.responsavels = docentes;
    }

    public ResponsavelCurso responsavels(Set<Docente> docentes) {
        this.setResponsavels(docentes);
        return this;
    }

    public ResponsavelCurso addResponsavel(Docente docente) {
        this.responsavels.add(docente);
        docente.setResponsavelCurso(this);
        return this;
    }

    public ResponsavelCurso removeResponsavel(Docente docente) {
        this.responsavels.remove(docente);
        docente.setResponsavelCurso(null);
        return this;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setResponsavelCurso(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setResponsavelCurso(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public ResponsavelCurso anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public ResponsavelCurso addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setResponsavelCurso(this);
        return this;
    }

    public ResponsavelCurso removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setResponsavelCurso(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public ResponsavelCurso utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public ResponsavelCurso curso(Curso curso) {
        this.setCurso(curso);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponsavelCurso)) {
            return false;
        }
        return id != null && id.equals(((ResponsavelCurso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponsavelCurso{" +
            "id=" + getId() +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
