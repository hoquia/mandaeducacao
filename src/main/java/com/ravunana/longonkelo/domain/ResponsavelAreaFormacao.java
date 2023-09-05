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
 * A ResponsavelAreaFormacao.
 */
@Entity
@Table(name = "responsavel_area_formacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResponsavelAreaFormacao implements Serializable {

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

    @OneToMany(mappedBy = "responsavelAreaFormacao")
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

    @OneToMany(mappedBy = "responsavelAreaFormacao")
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
        value = { "cursos", "dissertacaoFinalCursos", "responsaveis", "precoEmolumentos", "nivelEnsino" },
        allowSetters = true
    )
    private AreaFormacao areaFormacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResponsavelAreaFormacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDe() {
        return this.de;
    }

    public ResponsavelAreaFormacao de(LocalDate de) {
        this.setDe(de);
        return this;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public LocalDate getAte() {
        return this.ate;
    }

    public ResponsavelAreaFormacao ate(LocalDate ate) {
        this.setAte(ate);
        return this;
    }

    public void setAte(LocalDate ate) {
        this.ate = ate;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public ResponsavelAreaFormacao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public ResponsavelAreaFormacao timestamp(ZonedDateTime timestamp) {
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
            this.responsavels.forEach(i -> i.setResponsavelAreaFormacao(null));
        }
        if (docentes != null) {
            docentes.forEach(i -> i.setResponsavelAreaFormacao(this));
        }
        this.responsavels = docentes;
    }

    public ResponsavelAreaFormacao responsavels(Set<Docente> docentes) {
        this.setResponsavels(docentes);
        return this;
    }

    public ResponsavelAreaFormacao addResponsavel(Docente docente) {
        this.responsavels.add(docente);
        docente.setResponsavelAreaFormacao(this);
        return this;
    }

    public ResponsavelAreaFormacao removeResponsavel(Docente docente) {
        this.responsavels.remove(docente);
        docente.setResponsavelAreaFormacao(null);
        return this;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setResponsavelAreaFormacao(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setResponsavelAreaFormacao(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public ResponsavelAreaFormacao anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public ResponsavelAreaFormacao addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setResponsavelAreaFormacao(this);
        return this;
    }

    public ResponsavelAreaFormacao removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setResponsavelAreaFormacao(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public ResponsavelAreaFormacao utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public AreaFormacao getAreaFormacao() {
        return this.areaFormacao;
    }

    public void setAreaFormacao(AreaFormacao areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    public ResponsavelAreaFormacao areaFormacao(AreaFormacao areaFormacao) {
        this.setAreaFormacao(areaFormacao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponsavelAreaFormacao)) {
            return false;
        }
        return id != null && id.equals(((ResponsavelAreaFormacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponsavelAreaFormacao{" +
            "id=" + getId() +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
