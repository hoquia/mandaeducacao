package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Ocorrencia.
 */
@Entity
@Table(name = "ocorrencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ocorrencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_ocorrencia", unique = true)
    private String uniqueOcorrencia;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Lob
    @Column(name = "evidencia")
    private byte[] evidencia;

    @Column(name = "evidencia_content_type")
    private String evidenciaContentType;

    @Column(name = "hash")
    private String hash;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @OneToMany(mappedBy = "referencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "ocorrencias", "anoLectivos", "utilizador", "referencia", "docente", "matricula", "estado", "licao" },
        allowSetters = true
    )
    private Set<Ocorrencia> ocorrencias = new HashSet<>();

    @OneToMany(mappedBy = "ocorrencia")
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
        value = { "ocorrencias", "anoLectivos", "utilizador", "referencia", "docente", "matricula", "estado", "licao" },
        allowSetters = true
    )
    private Ocorrencia referencia;

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
    private Matricula matricula;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "categoriaOcorrencias", "ocorrencias", "encaminhar", "referencia", "medidaDisciplinar" },
        allowSetters = true
    )
    private CategoriaOcorrencia estado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "ocorrencias", "anoLectivos", "utilizador", "planoAula", "horario" }, allowSetters = true)
    private Licao licao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ocorrencia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueOcorrencia() {
        return this.uniqueOcorrencia;
    }

    public Ocorrencia uniqueOcorrencia(String uniqueOcorrencia) {
        this.setUniqueOcorrencia(uniqueOcorrencia);
        return this;
    }

    public void setUniqueOcorrencia(String uniqueOcorrencia) {
        this.uniqueOcorrencia = uniqueOcorrencia;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Ocorrencia descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getEvidencia() {
        return this.evidencia;
    }

    public Ocorrencia evidencia(byte[] evidencia) {
        this.setEvidencia(evidencia);
        return this;
    }

    public void setEvidencia(byte[] evidencia) {
        this.evidencia = evidencia;
    }

    public String getEvidenciaContentType() {
        return this.evidenciaContentType;
    }

    public Ocorrencia evidenciaContentType(String evidenciaContentType) {
        this.evidenciaContentType = evidenciaContentType;
        return this;
    }

    public void setEvidenciaContentType(String evidenciaContentType) {
        this.evidenciaContentType = evidenciaContentType;
    }

    public String getHash() {
        return this.hash;
    }

    public Ocorrencia hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public Ocorrencia timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Set<Ocorrencia> getOcorrencias() {
        return this.ocorrencias;
    }

    public void setOcorrencias(Set<Ocorrencia> ocorrencias) {
        if (this.ocorrencias != null) {
            this.ocorrencias.forEach(i -> i.setReferencia(null));
        }
        if (ocorrencias != null) {
            ocorrencias.forEach(i -> i.setReferencia(this));
        }
        this.ocorrencias = ocorrencias;
    }

    public Ocorrencia ocorrencias(Set<Ocorrencia> ocorrencias) {
        this.setOcorrencias(ocorrencias);
        return this;
    }

    public Ocorrencia addOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
        ocorrencia.setReferencia(this);
        return this;
    }

    public Ocorrencia removeOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.remove(ocorrencia);
        ocorrencia.setReferencia(null);
        return this;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setOcorrencia(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setOcorrencia(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public Ocorrencia anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public Ocorrencia addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setOcorrencia(this);
        return this;
    }

    public Ocorrencia removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setOcorrencia(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Ocorrencia utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Ocorrencia getReferencia() {
        return this.referencia;
    }

    public void setReferencia(Ocorrencia ocorrencia) {
        this.referencia = ocorrencia;
    }

    public Ocorrencia referencia(Ocorrencia ocorrencia) {
        this.setReferencia(ocorrencia);
        return this;
    }

    public Docente getDocente() {
        return this.docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Ocorrencia docente(Docente docente) {
        this.setDocente(docente);
        return this;
    }

    public Matricula getMatricula() {
        return this.matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Ocorrencia matricula(Matricula matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public CategoriaOcorrencia getEstado() {
        return this.estado;
    }

    public void setEstado(CategoriaOcorrencia categoriaOcorrencia) {
        this.estado = categoriaOcorrencia;
    }

    public Ocorrencia estado(CategoriaOcorrencia categoriaOcorrencia) {
        this.setEstado(categoriaOcorrencia);
        return this;
    }

    public Licao getLicao() {
        return this.licao;
    }

    public void setLicao(Licao licao) {
        this.licao = licao;
    }

    public Ocorrencia licao(Licao licao) {
        this.setLicao(licao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ocorrencia)) {
            return false;
        }
        return id != null && id.equals(((Ocorrencia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ocorrencia{" +
            "id=" + getId() +
            ", uniqueOcorrencia='" + getUniqueOcorrencia() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", evidencia='" + getEvidencia() + "'" +
            ", evidenciaContentType='" + getEvidenciaContentType() + "'" +
            ", hash='" + getHash() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
