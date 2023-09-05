package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.UnidadeDuracao;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A NivelEnsino.
 */
@Entity
@Table(name = "nivel_ensino")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NivelEnsino implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Min(value = 0)
    @Column(name = "idade_minima")
    private Integer idadeMinima;

    @Min(value = 0)
    @Column(name = "idade_maxima")
    private Integer idadeMaxima;

    @DecimalMin(value = "0")
    @Column(name = "duracao")
    private Double duracao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_duracao", nullable = false)
    private UnidadeDuracao unidadeDuracao;

    @Min(value = 0)
    @Column(name = "classe_inicial")
    private Integer classeInicial;

    @Min(value = 0)
    @Column(name = "classe_final")
    private Integer classeFinal;

    @Column(name = "classe_exame")
    private Integer classeExame;

    @Column(name = "total_disciplina")
    private Integer totalDisciplina;

    @Column(name = "responsavel_turno")
    private String responsavelTurno;

    @Column(name = "responsavel_area_formacao")
    private String responsavelAreaFormacao;

    @Column(name = "responsavel_curso")
    private String responsavelCurso;

    @Column(name = "responsavel_disciplina")
    private String responsavelDisciplina;

    @Column(name = "responsavel_turma")
    private String responsavelTurma;

    @Column(name = "responsavel_geral")
    private String responsavelGeral;

    @Column(name = "responsavel_pedagogico")
    private String responsavelPedagogico;

    @Column(name = "responsavel_administrativo")
    private String responsavelAdministrativo;

    @Column(name = "responsavel_secretaria_geral")
    private String responsavelSecretariaGeral;

    @Column(name = "responsavel_secretaria_pedagogico")
    private String responsavelSecretariaPedagogico;

    @Column(name = "descricao_docente")
    private String descricaoDocente;

    @Column(name = "descricao_discente")
    private String descricaoDiscente;

    @OneToMany(mappedBy = "referencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nivelEnsinos", "areaFormacaos", "referencia", "anoLectivos", "classes" }, allowSetters = true)
    private Set<NivelEnsino> nivelEnsinos = new HashSet<>();

    @OneToMany(mappedBy = "nivelEnsino")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "cursos", "dissertacaoFinalCursos", "responsaveis", "precoEmolumentos", "nivelEnsino" },
        allowSetters = true
    )
    private Set<AreaFormacao> areaFormacaos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "nivelEnsinos", "areaFormacaos", "referencia", "anoLectivos", "classes" }, allowSetters = true)
    private NivelEnsino referencia;

    @ManyToMany(mappedBy = "nivesEnsinos")
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

    @ManyToMany(mappedBy = "nivesEnsinos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "planoCurriculars", "precoEmolumentos", "nivesEnsinos", "periodosLancamentoNotas" },
        allowSetters = true
    )
    private Set<Classe> classes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NivelEnsino id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public NivelEnsino codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public NivelEnsino nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public NivelEnsino descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdadeMinima() {
        return this.idadeMinima;
    }

    public NivelEnsino idadeMinima(Integer idadeMinima) {
        this.setIdadeMinima(idadeMinima);
        return this;
    }

    public void setIdadeMinima(Integer idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public Integer getIdadeMaxima() {
        return this.idadeMaxima;
    }

    public NivelEnsino idadeMaxima(Integer idadeMaxima) {
        this.setIdadeMaxima(idadeMaxima);
        return this;
    }

    public void setIdadeMaxima(Integer idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
    }

    public Double getDuracao() {
        return this.duracao;
    }

    public NivelEnsino duracao(Double duracao) {
        this.setDuracao(duracao);
        return this;
    }

    public void setDuracao(Double duracao) {
        this.duracao = duracao;
    }

    public UnidadeDuracao getUnidadeDuracao() {
        return this.unidadeDuracao;
    }

    public NivelEnsino unidadeDuracao(UnidadeDuracao unidadeDuracao) {
        this.setUnidadeDuracao(unidadeDuracao);
        return this;
    }

    public void setUnidadeDuracao(UnidadeDuracao unidadeDuracao) {
        this.unidadeDuracao = unidadeDuracao;
    }

    public Integer getClasseInicial() {
        return this.classeInicial;
    }

    public NivelEnsino classeInicial(Integer classeInicial) {
        this.setClasseInicial(classeInicial);
        return this;
    }

    public void setClasseInicial(Integer classeInicial) {
        this.classeInicial = classeInicial;
    }

    public Integer getClasseFinal() {
        return this.classeFinal;
    }

    public NivelEnsino classeFinal(Integer classeFinal) {
        this.setClasseFinal(classeFinal);
        return this;
    }

    public void setClasseFinal(Integer classeFinal) {
        this.classeFinal = classeFinal;
    }

    public Integer getClasseExame() {
        return this.classeExame;
    }

    public NivelEnsino classeExame(Integer classeExame) {
        this.setClasseExame(classeExame);
        return this;
    }

    public void setClasseExame(Integer classeExame) {
        this.classeExame = classeExame;
    }

    public Integer getTotalDisciplina() {
        return this.totalDisciplina;
    }

    public NivelEnsino totalDisciplina(Integer totalDisciplina) {
        this.setTotalDisciplina(totalDisciplina);
        return this;
    }

    public void setTotalDisciplina(Integer totalDisciplina) {
        this.totalDisciplina = totalDisciplina;
    }

    public String getResponsavelTurno() {
        return this.responsavelTurno;
    }

    public NivelEnsino responsavelTurno(String responsavelTurno) {
        this.setResponsavelTurno(responsavelTurno);
        return this;
    }

    public void setResponsavelTurno(String responsavelTurno) {
        this.responsavelTurno = responsavelTurno;
    }

    public String getResponsavelAreaFormacao() {
        return this.responsavelAreaFormacao;
    }

    public NivelEnsino responsavelAreaFormacao(String responsavelAreaFormacao) {
        this.setResponsavelAreaFormacao(responsavelAreaFormacao);
        return this;
    }

    public void setResponsavelAreaFormacao(String responsavelAreaFormacao) {
        this.responsavelAreaFormacao = responsavelAreaFormacao;
    }

    public String getResponsavelCurso() {
        return this.responsavelCurso;
    }

    public NivelEnsino responsavelCurso(String responsavelCurso) {
        this.setResponsavelCurso(responsavelCurso);
        return this;
    }

    public void setResponsavelCurso(String responsavelCurso) {
        this.responsavelCurso = responsavelCurso;
    }

    public String getResponsavelDisciplina() {
        return this.responsavelDisciplina;
    }

    public NivelEnsino responsavelDisciplina(String responsavelDisciplina) {
        this.setResponsavelDisciplina(responsavelDisciplina);
        return this;
    }

    public void setResponsavelDisciplina(String responsavelDisciplina) {
        this.responsavelDisciplina = responsavelDisciplina;
    }

    public String getResponsavelTurma() {
        return this.responsavelTurma;
    }

    public NivelEnsino responsavelTurma(String responsavelTurma) {
        this.setResponsavelTurma(responsavelTurma);
        return this;
    }

    public void setResponsavelTurma(String responsavelTurma) {
        this.responsavelTurma = responsavelTurma;
    }

    public String getResponsavelGeral() {
        return this.responsavelGeral;
    }

    public NivelEnsino responsavelGeral(String responsavelGeral) {
        this.setResponsavelGeral(responsavelGeral);
        return this;
    }

    public void setResponsavelGeral(String responsavelGeral) {
        this.responsavelGeral = responsavelGeral;
    }

    public String getResponsavelPedagogico() {
        return this.responsavelPedagogico;
    }

    public NivelEnsino responsavelPedagogico(String responsavelPedagogico) {
        this.setResponsavelPedagogico(responsavelPedagogico);
        return this;
    }

    public void setResponsavelPedagogico(String responsavelPedagogico) {
        this.responsavelPedagogico = responsavelPedagogico;
    }

    public String getResponsavelAdministrativo() {
        return this.responsavelAdministrativo;
    }

    public NivelEnsino responsavelAdministrativo(String responsavelAdministrativo) {
        this.setResponsavelAdministrativo(responsavelAdministrativo);
        return this;
    }

    public void setResponsavelAdministrativo(String responsavelAdministrativo) {
        this.responsavelAdministrativo = responsavelAdministrativo;
    }

    public String getResponsavelSecretariaGeral() {
        return this.responsavelSecretariaGeral;
    }

    public NivelEnsino responsavelSecretariaGeral(String responsavelSecretariaGeral) {
        this.setResponsavelSecretariaGeral(responsavelSecretariaGeral);
        return this;
    }

    public void setResponsavelSecretariaGeral(String responsavelSecretariaGeral) {
        this.responsavelSecretariaGeral = responsavelSecretariaGeral;
    }

    public String getResponsavelSecretariaPedagogico() {
        return this.responsavelSecretariaPedagogico;
    }

    public NivelEnsino responsavelSecretariaPedagogico(String responsavelSecretariaPedagogico) {
        this.setResponsavelSecretariaPedagogico(responsavelSecretariaPedagogico);
        return this;
    }

    public void setResponsavelSecretariaPedagogico(String responsavelSecretariaPedagogico) {
        this.responsavelSecretariaPedagogico = responsavelSecretariaPedagogico;
    }

    public String getDescricaoDocente() {
        return this.descricaoDocente;
    }

    public NivelEnsino descricaoDocente(String descricaoDocente) {
        this.setDescricaoDocente(descricaoDocente);
        return this;
    }

    public void setDescricaoDocente(String descricaoDocente) {
        this.descricaoDocente = descricaoDocente;
    }

    public String getDescricaoDiscente() {
        return this.descricaoDiscente;
    }

    public NivelEnsino descricaoDiscente(String descricaoDiscente) {
        this.setDescricaoDiscente(descricaoDiscente);
        return this;
    }

    public void setDescricaoDiscente(String descricaoDiscente) {
        this.descricaoDiscente = descricaoDiscente;
    }

    public Set<NivelEnsino> getNivelEnsinos() {
        return this.nivelEnsinos;
    }

    public void setNivelEnsinos(Set<NivelEnsino> nivelEnsinos) {
        if (this.nivelEnsinos != null) {
            this.nivelEnsinos.forEach(i -> i.setReferencia(null));
        }
        if (nivelEnsinos != null) {
            nivelEnsinos.forEach(i -> i.setReferencia(this));
        }
        this.nivelEnsinos = nivelEnsinos;
    }

    public NivelEnsino nivelEnsinos(Set<NivelEnsino> nivelEnsinos) {
        this.setNivelEnsinos(nivelEnsinos);
        return this;
    }

    public NivelEnsino addNivelEnsino(NivelEnsino nivelEnsino) {
        this.nivelEnsinos.add(nivelEnsino);
        nivelEnsino.setReferencia(this);
        return this;
    }

    public NivelEnsino removeNivelEnsino(NivelEnsino nivelEnsino) {
        this.nivelEnsinos.remove(nivelEnsino);
        nivelEnsino.setReferencia(null);
        return this;
    }

    public Set<AreaFormacao> getAreaFormacaos() {
        return this.areaFormacaos;
    }

    public void setAreaFormacaos(Set<AreaFormacao> areaFormacaos) {
        if (this.areaFormacaos != null) {
            this.areaFormacaos.forEach(i -> i.setNivelEnsino(null));
        }
        if (areaFormacaos != null) {
            areaFormacaos.forEach(i -> i.setNivelEnsino(this));
        }
        this.areaFormacaos = areaFormacaos;
    }

    public NivelEnsino areaFormacaos(Set<AreaFormacao> areaFormacaos) {
        this.setAreaFormacaos(areaFormacaos);
        return this;
    }

    public NivelEnsino addAreaFormacao(AreaFormacao areaFormacao) {
        this.areaFormacaos.add(areaFormacao);
        areaFormacao.setNivelEnsino(this);
        return this;
    }

    public NivelEnsino removeAreaFormacao(AreaFormacao areaFormacao) {
        this.areaFormacaos.remove(areaFormacao);
        areaFormacao.setNivelEnsino(null);
        return this;
    }

    public NivelEnsino getReferencia() {
        return this.referencia;
    }

    public void setReferencia(NivelEnsino nivelEnsino) {
        this.referencia = nivelEnsino;
    }

    public NivelEnsino referencia(NivelEnsino nivelEnsino) {
        this.setReferencia(nivelEnsino);
        return this;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.removeNivesEnsino(this));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.addNivesEnsino(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public NivelEnsino anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public NivelEnsino addAnoLectivos(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.getNivesEnsinos().add(this);
        return this;
    }

    public NivelEnsino removeAnoLectivos(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.getNivesEnsinos().remove(this);
        return this;
    }

    public Set<Classe> getClasses() {
        return this.classes;
    }

    public void setClasses(Set<Classe> classes) {
        if (this.classes != null) {
            this.classes.forEach(i -> i.removeNivesEnsino(this));
        }
        if (classes != null) {
            classes.forEach(i -> i.addNivesEnsino(this));
        }
        this.classes = classes;
    }

    public NivelEnsino classes(Set<Classe> classes) {
        this.setClasses(classes);
        return this;
    }

    public NivelEnsino addClasses(Classe classe) {
        this.classes.add(classe);
        classe.getNivesEnsinos().add(this);
        return this;
    }

    public NivelEnsino removeClasses(Classe classe) {
        this.classes.remove(classe);
        classe.getNivesEnsinos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NivelEnsino)) {
            return false;
        }
        return id != null && id.equals(((NivelEnsino) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NivelEnsino{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", idadeMinima=" + getIdadeMinima() +
            ", idadeMaxima=" + getIdadeMaxima() +
            ", duracao=" + getDuracao() +
            ", unidadeDuracao='" + getUnidadeDuracao() + "'" +
            ", classeInicial=" + getClasseInicial() +
            ", classeFinal=" + getClasseFinal() +
            ", classeExame=" + getClasseExame() +
            ", totalDisciplina=" + getTotalDisciplina() +
            ", responsavelTurno='" + getResponsavelTurno() + "'" +
            ", responsavelAreaFormacao='" + getResponsavelAreaFormacao() + "'" +
            ", responsavelCurso='" + getResponsavelCurso() + "'" +
            ", responsavelDisciplina='" + getResponsavelDisciplina() + "'" +
            ", responsavelTurma='" + getResponsavelTurma() + "'" +
            ", responsavelGeral='" + getResponsavelGeral() + "'" +
            ", responsavelPedagogico='" + getResponsavelPedagogico() + "'" +
            ", responsavelAdministrativo='" + getResponsavelAdministrativo() + "'" +
            ", responsavelSecretariaGeral='" + getResponsavelSecretariaGeral() + "'" +
            ", responsavelSecretariaPedagogico='" + getResponsavelSecretariaPedagogico() + "'" +
            ", descricaoDocente='" + getDescricaoDocente() + "'" +
            ", descricaoDiscente='" + getDescricaoDiscente() + "'" +
            "}";
    }
}
