package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.EstadoLicao;
import com.ravunana.longonkelo.domain.enumeration.TipoAula;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A PlanoAula.
 */
@Entity
@Table(name = "plano_aula")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoAula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_aula", nullable = false)
    private TipoAula tipoAula;

    @NotNull
    @Min(value = 0)
    @Column(name = "semana_lectiva", nullable = false)
    private Integer semanaLectiva;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "perfil_entrada", nullable = false)
    private String perfilEntrada;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "perfil_saida", nullable = false)
    private String perfilSaida;

    @NotNull
    @Column(name = "assunto", nullable = false)
    private String assunto;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "objectivo_geral", nullable = false)
    private String objectivoGeral;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "objectivos_especificos", nullable = false)
    private String objectivosEspecificos;

    @Min(value = 0)
    @Column(name = "tempo_total_licao")
    private Integer tempoTotalLicao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoLicao estado;

    @OneToMany(mappedBy = "planoAula")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "planoAula" }, allowSetters = true)
    private Set<DetalhePlanoAula> detalhes = new HashSet<>();

    @OneToMany(mappedBy = "planoAula")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ocorrencias", "anoLectivos", "utilizador", "planoAula", "horario" }, allowSetters = true)
    private Set<Licao> licaos = new HashSet<>();

    @OneToMany(mappedBy = "planoAula")
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
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem unidadeTematica;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem subUnidadeTematica;

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

    @ManyToOne(optional = false)
    @NotNull
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
    private Docente docente;

    @ManyToOne(optional = false)
    @NotNull
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
    private DisciplinaCurricular disciplinaCurricular;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanoAula id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoAula getTipoAula() {
        return this.tipoAula;
    }

    public PlanoAula tipoAula(TipoAula tipoAula) {
        this.setTipoAula(tipoAula);
        return this;
    }

    public void setTipoAula(TipoAula tipoAula) {
        this.tipoAula = tipoAula;
    }

    public Integer getSemanaLectiva() {
        return this.semanaLectiva;
    }

    public PlanoAula semanaLectiva(Integer semanaLectiva) {
        this.setSemanaLectiva(semanaLectiva);
        return this;
    }

    public void setSemanaLectiva(Integer semanaLectiva) {
        this.semanaLectiva = semanaLectiva;
    }

    public String getPerfilEntrada() {
        return this.perfilEntrada;
    }

    public PlanoAula perfilEntrada(String perfilEntrada) {
        this.setPerfilEntrada(perfilEntrada);
        return this;
    }

    public void setPerfilEntrada(String perfilEntrada) {
        this.perfilEntrada = perfilEntrada;
    }

    public String getPerfilSaida() {
        return this.perfilSaida;
    }

    public PlanoAula perfilSaida(String perfilSaida) {
        this.setPerfilSaida(perfilSaida);
        return this;
    }

    public void setPerfilSaida(String perfilSaida) {
        this.perfilSaida = perfilSaida;
    }

    public String getAssunto() {
        return this.assunto;
    }

    public PlanoAula assunto(String assunto) {
        this.setAssunto(assunto);
        return this;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getObjectivoGeral() {
        return this.objectivoGeral;
    }

    public PlanoAula objectivoGeral(String objectivoGeral) {
        this.setObjectivoGeral(objectivoGeral);
        return this;
    }

    public void setObjectivoGeral(String objectivoGeral) {
        this.objectivoGeral = objectivoGeral;
    }

    public String getObjectivosEspecificos() {
        return this.objectivosEspecificos;
    }

    public PlanoAula objectivosEspecificos(String objectivosEspecificos) {
        this.setObjectivosEspecificos(objectivosEspecificos);
        return this;
    }

    public void setObjectivosEspecificos(String objectivosEspecificos) {
        this.objectivosEspecificos = objectivosEspecificos;
    }

    public Integer getTempoTotalLicao() {
        return this.tempoTotalLicao;
    }

    public PlanoAula tempoTotalLicao(Integer tempoTotalLicao) {
        this.setTempoTotalLicao(tempoTotalLicao);
        return this;
    }

    public void setTempoTotalLicao(Integer tempoTotalLicao) {
        this.tempoTotalLicao = tempoTotalLicao;
    }

    public EstadoLicao getEstado() {
        return this.estado;
    }

    public PlanoAula estado(EstadoLicao estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoLicao estado) {
        this.estado = estado;
    }

    public Set<DetalhePlanoAula> getDetalhes() {
        return this.detalhes;
    }

    public void setDetalhes(Set<DetalhePlanoAula> detalhePlanoAulas) {
        if (this.detalhes != null) {
            this.detalhes.forEach(i -> i.setPlanoAula(null));
        }
        if (detalhePlanoAulas != null) {
            detalhePlanoAulas.forEach(i -> i.setPlanoAula(this));
        }
        this.detalhes = detalhePlanoAulas;
    }

    public PlanoAula detalhes(Set<DetalhePlanoAula> detalhePlanoAulas) {
        this.setDetalhes(detalhePlanoAulas);
        return this;
    }

    public PlanoAula addDetalhes(DetalhePlanoAula detalhePlanoAula) {
        this.detalhes.add(detalhePlanoAula);
        detalhePlanoAula.setPlanoAula(this);
        return this;
    }

    public PlanoAula removeDetalhes(DetalhePlanoAula detalhePlanoAula) {
        this.detalhes.remove(detalhePlanoAula);
        detalhePlanoAula.setPlanoAula(null);
        return this;
    }

    public Set<Licao> getLicaos() {
        return this.licaos;
    }

    public void setLicaos(Set<Licao> licaos) {
        if (this.licaos != null) {
            this.licaos.forEach(i -> i.setPlanoAula(null));
        }
        if (licaos != null) {
            licaos.forEach(i -> i.setPlanoAula(this));
        }
        this.licaos = licaos;
    }

    public PlanoAula licaos(Set<Licao> licaos) {
        this.setLicaos(licaos);
        return this;
    }

    public PlanoAula addLicao(Licao licao) {
        this.licaos.add(licao);
        licao.setPlanoAula(this);
        return this;
    }

    public PlanoAula removeLicao(Licao licao) {
        this.licaos.remove(licao);
        licao.setPlanoAula(null);
        return this;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setPlanoAula(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setPlanoAula(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public PlanoAula anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public PlanoAula addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setPlanoAula(this);
        return this;
    }

    public PlanoAula removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setPlanoAula(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public PlanoAula utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public LookupItem getUnidadeTematica() {
        return this.unidadeTematica;
    }

    public void setUnidadeTematica(LookupItem lookupItem) {
        this.unidadeTematica = lookupItem;
    }

    public PlanoAula unidadeTematica(LookupItem lookupItem) {
        this.setUnidadeTematica(lookupItem);
        return this;
    }

    public LookupItem getSubUnidadeTematica() {
        return this.subUnidadeTematica;
    }

    public void setSubUnidadeTematica(LookupItem lookupItem) {
        this.subUnidadeTematica = lookupItem;
    }

    public PlanoAula subUnidadeTematica(LookupItem lookupItem) {
        this.setSubUnidadeTematica(lookupItem);
        return this;
    }

    public Turma getTurma() {
        return this.turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public PlanoAula turma(Turma turma) {
        this.setTurma(turma);
        return this;
    }

    public Docente getDocente() {
        return this.docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public PlanoAula docente(Docente docente) {
        this.setDocente(docente);
        return this;
    }

    public DisciplinaCurricular getDisciplinaCurricular() {
        return this.disciplinaCurricular;
    }

    public void setDisciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.disciplinaCurricular = disciplinaCurricular;
    }

    public PlanoAula disciplinaCurricular(DisciplinaCurricular disciplinaCurricular) {
        this.setDisciplinaCurricular(disciplinaCurricular);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoAula)) {
            return false;
        }
        return id != null && id.equals(((PlanoAula) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoAula{" +
            "id=" + getId() +
            ", tipoAula='" + getTipoAula() + "'" +
            ", semanaLectiva=" + getSemanaLectiva() +
            ", perfilEntrada='" + getPerfilEntrada() + "'" +
            ", perfilSaida='" + getPerfilSaida() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", objectivoGeral='" + getObjectivoGeral() + "'" +
            ", objectivosEspecificos='" + getObjectivosEspecificos() + "'" +
            ", tempoTotalLicao=" + getTempoTotalLicao() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
