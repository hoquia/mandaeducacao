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
 * A DissertacaoFinalCurso.
 */
@Entity
@Table(name = "dissertacao_final_curso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DissertacaoFinalCurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull
    @Column(name = "tema", nullable = false, unique = true)
    private String tema;

    @NotNull
    @Column(name = "objectivo_geral", nullable = false)
    private String objectivoGeral;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "objectivos_especificos", nullable = false)
    private String objectivosEspecificos;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "introducao", nullable = false)
    private String introducao;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "resumo", nullable = false)
    private String resumo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "problema", nullable = false)
    private String problema;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "resultado", nullable = false)
    private String resultado;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "metodologia", nullable = false)
    private String metodologia;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "referencias_bibliograficas", nullable = false)
    private String referenciasBibliograficas;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao_orientador")
    private String observacaoOrientador;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao_area_formacao")
    private String observacaoAreaFormacao;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao_instituicao")
    private String observacaoInstituicao;

    @Column(name = "hash")
    private String hash;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "termos_compromissos")
    private String termosCompromissos;

    @Column(name = "is_aceite_termos_compromisso")
    private Boolean isAceiteTermosCompromisso;

    @OneToMany(mappedBy = "dissertacaoFinalCurso")
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
    private Docente orientador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "cursos", "dissertacaoFinalCursos", "responsaveis", "precoEmolumentos", "nivelEnsino" },
        allowSetters = true
    )
    private AreaFormacao especialidade;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "dissertacaoFinalCursos" }, allowSetters = true)
    private EstadoDissertacao estado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "dissertacaoFinalCursos" }, allowSetters = true)
    private NaturezaTrabalho natureza;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DissertacaoFinalCurso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return this.numero;
    }

    public DissertacaoFinalCurso numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public DissertacaoFinalCurso timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDate getData() {
        return this.data;
    }

    public DissertacaoFinalCurso data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTema() {
        return this.tema;
    }

    public DissertacaoFinalCurso tema(String tema) {
        this.setTema(tema);
        return this;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getObjectivoGeral() {
        return this.objectivoGeral;
    }

    public DissertacaoFinalCurso objectivoGeral(String objectivoGeral) {
        this.setObjectivoGeral(objectivoGeral);
        return this;
    }

    public void setObjectivoGeral(String objectivoGeral) {
        this.objectivoGeral = objectivoGeral;
    }

    public String getObjectivosEspecificos() {
        return this.objectivosEspecificos;
    }

    public DissertacaoFinalCurso objectivosEspecificos(String objectivosEspecificos) {
        this.setObjectivosEspecificos(objectivosEspecificos);
        return this;
    }

    public void setObjectivosEspecificos(String objectivosEspecificos) {
        this.objectivosEspecificos = objectivosEspecificos;
    }

    public String getIntroducao() {
        return this.introducao;
    }

    public DissertacaoFinalCurso introducao(String introducao) {
        this.setIntroducao(introducao);
        return this;
    }

    public void setIntroducao(String introducao) {
        this.introducao = introducao;
    }

    public String getResumo() {
        return this.resumo;
    }

    public DissertacaoFinalCurso resumo(String resumo) {
        this.setResumo(resumo);
        return this;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getProblema() {
        return this.problema;
    }

    public DissertacaoFinalCurso problema(String problema) {
        this.setProblema(problema);
        return this;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getResultado() {
        return this.resultado;
    }

    public DissertacaoFinalCurso resultado(String resultado) {
        this.setResultado(resultado);
        return this;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMetodologia() {
        return this.metodologia;
    }

    public DissertacaoFinalCurso metodologia(String metodologia) {
        this.setMetodologia(metodologia);
        return this;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public String getReferenciasBibliograficas() {
        return this.referenciasBibliograficas;
    }

    public DissertacaoFinalCurso referenciasBibliograficas(String referenciasBibliograficas) {
        this.setReferenciasBibliograficas(referenciasBibliograficas);
        return this;
    }

    public void setReferenciasBibliograficas(String referenciasBibliograficas) {
        this.referenciasBibliograficas = referenciasBibliograficas;
    }

    public String getObservacaoOrientador() {
        return this.observacaoOrientador;
    }

    public DissertacaoFinalCurso observacaoOrientador(String observacaoOrientador) {
        this.setObservacaoOrientador(observacaoOrientador);
        return this;
    }

    public void setObservacaoOrientador(String observacaoOrientador) {
        this.observacaoOrientador = observacaoOrientador;
    }

    public String getObservacaoAreaFormacao() {
        return this.observacaoAreaFormacao;
    }

    public DissertacaoFinalCurso observacaoAreaFormacao(String observacaoAreaFormacao) {
        this.setObservacaoAreaFormacao(observacaoAreaFormacao);
        return this;
    }

    public void setObservacaoAreaFormacao(String observacaoAreaFormacao) {
        this.observacaoAreaFormacao = observacaoAreaFormacao;
    }

    public String getObservacaoInstituicao() {
        return this.observacaoInstituicao;
    }

    public DissertacaoFinalCurso observacaoInstituicao(String observacaoInstituicao) {
        this.setObservacaoInstituicao(observacaoInstituicao);
        return this;
    }

    public void setObservacaoInstituicao(String observacaoInstituicao) {
        this.observacaoInstituicao = observacaoInstituicao;
    }

    public String getHash() {
        return this.hash;
    }

    public DissertacaoFinalCurso hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTermosCompromissos() {
        return this.termosCompromissos;
    }

    public DissertacaoFinalCurso termosCompromissos(String termosCompromissos) {
        this.setTermosCompromissos(termosCompromissos);
        return this;
    }

    public void setTermosCompromissos(String termosCompromissos) {
        this.termosCompromissos = termosCompromissos;
    }

    public Boolean getIsAceiteTermosCompromisso() {
        return this.isAceiteTermosCompromisso;
    }

    public DissertacaoFinalCurso isAceiteTermosCompromisso(Boolean isAceiteTermosCompromisso) {
        this.setIsAceiteTermosCompromisso(isAceiteTermosCompromisso);
        return this;
    }

    public void setIsAceiteTermosCompromisso(Boolean isAceiteTermosCompromisso) {
        this.isAceiteTermosCompromisso = isAceiteTermosCompromisso;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setDissertacaoFinalCurso(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setDissertacaoFinalCurso(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public DissertacaoFinalCurso anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public DissertacaoFinalCurso addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setDissertacaoFinalCurso(this);
        return this;
    }

    public DissertacaoFinalCurso removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setDissertacaoFinalCurso(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public DissertacaoFinalCurso utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Turma getTurma() {
        return this.turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public DissertacaoFinalCurso turma(Turma turma) {
        this.setTurma(turma);
        return this;
    }

    public Docente getOrientador() {
        return this.orientador;
    }

    public void setOrientador(Docente docente) {
        this.orientador = docente;
    }

    public DissertacaoFinalCurso orientador(Docente docente) {
        this.setOrientador(docente);
        return this;
    }

    public AreaFormacao getEspecialidade() {
        return this.especialidade;
    }

    public void setEspecialidade(AreaFormacao areaFormacao) {
        this.especialidade = areaFormacao;
    }

    public DissertacaoFinalCurso especialidade(AreaFormacao areaFormacao) {
        this.setEspecialidade(areaFormacao);
        return this;
    }

    public Discente getDiscente() {
        return this.discente;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }

    public DissertacaoFinalCurso discente(Discente discente) {
        this.setDiscente(discente);
        return this;
    }

    public EstadoDissertacao getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoDissertacao estadoDissertacao) {
        this.estado = estadoDissertacao;
    }

    public DissertacaoFinalCurso estado(EstadoDissertacao estadoDissertacao) {
        this.setEstado(estadoDissertacao);
        return this;
    }

    public NaturezaTrabalho getNatureza() {
        return this.natureza;
    }

    public void setNatureza(NaturezaTrabalho naturezaTrabalho) {
        this.natureza = naturezaTrabalho;
    }

    public DissertacaoFinalCurso natureza(NaturezaTrabalho naturezaTrabalho) {
        this.setNatureza(naturezaTrabalho);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DissertacaoFinalCurso)) {
            return false;
        }
        return id != null && id.equals(((DissertacaoFinalCurso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DissertacaoFinalCurso{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", data='" + getData() + "'" +
            ", tema='" + getTema() + "'" +
            ", objectivoGeral='" + getObjectivoGeral() + "'" +
            ", objectivosEspecificos='" + getObjectivosEspecificos() + "'" +
            ", introducao='" + getIntroducao() + "'" +
            ", resumo='" + getResumo() + "'" +
            ", problema='" + getProblema() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", metodologia='" + getMetodologia() + "'" +
            ", referenciasBibliograficas='" + getReferenciasBibliograficas() + "'" +
            ", observacaoOrientador='" + getObservacaoOrientador() + "'" +
            ", observacaoAreaFormacao='" + getObservacaoAreaFormacao() + "'" +
            ", observacaoInstituicao='" + getObservacaoInstituicao() + "'" +
            ", hash='" + getHash() + "'" +
            ", termosCompromissos='" + getTermosCompromissos() + "'" +
            ", isAceiteTermosCompromisso='" + getIsAceiteTermosCompromisso() + "'" +
            "}";
    }
}
