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

/**
 * A ProcessoSelectivoMatricula.
 */
@Entity
@Table(name = "processo_selectivo_matricula")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcessoSelectivoMatricula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "local_teste")
    private String localTeste;

    @Column(name = "data_teste")
    private ZonedDateTime dataTeste;

    @DecimalMin(value = "0")
    @Column(name = "nota_teste")
    private Double notaTeste;

    @Column(name = "is_admitido")
    private Boolean isAdmitido;

    @OneToMany(mappedBy = "processoSelectivoMatricula")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProcessoSelectivoMatricula id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalTeste() {
        return this.localTeste;
    }

    public ProcessoSelectivoMatricula localTeste(String localTeste) {
        this.setLocalTeste(localTeste);
        return this;
    }

    public void setLocalTeste(String localTeste) {
        this.localTeste = localTeste;
    }

    public ZonedDateTime getDataTeste() {
        return this.dataTeste;
    }

    public ProcessoSelectivoMatricula dataTeste(ZonedDateTime dataTeste) {
        this.setDataTeste(dataTeste);
        return this;
    }

    public void setDataTeste(ZonedDateTime dataTeste) {
        this.dataTeste = dataTeste;
    }

    public Double getNotaTeste() {
        return this.notaTeste;
    }

    public ProcessoSelectivoMatricula notaTeste(Double notaTeste) {
        this.setNotaTeste(notaTeste);
        return this;
    }

    public void setNotaTeste(Double notaTeste) {
        this.notaTeste = notaTeste;
    }

    public Boolean getIsAdmitido() {
        return this.isAdmitido;
    }

    public ProcessoSelectivoMatricula isAdmitido(Boolean isAdmitido) {
        this.setIsAdmitido(isAdmitido);
        return this;
    }

    public void setIsAdmitido(Boolean isAdmitido) {
        this.isAdmitido = isAdmitido;
    }

    public Set<AnoLectivo> getAnoLectivos() {
        return this.anoLectivos;
    }

    public void setAnoLectivos(Set<AnoLectivo> anoLectivos) {
        if (this.anoLectivos != null) {
            this.anoLectivos.forEach(i -> i.setProcessoSelectivoMatricula(null));
        }
        if (anoLectivos != null) {
            anoLectivos.forEach(i -> i.setProcessoSelectivoMatricula(this));
        }
        this.anoLectivos = anoLectivos;
    }

    public ProcessoSelectivoMatricula anoLectivos(Set<AnoLectivo> anoLectivos) {
        this.setAnoLectivos(anoLectivos);
        return this;
    }

    public ProcessoSelectivoMatricula addAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.add(anoLectivo);
        anoLectivo.setProcessoSelectivoMatricula(this);
        return this;
    }

    public ProcessoSelectivoMatricula removeAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivos.remove(anoLectivo);
        anoLectivo.setProcessoSelectivoMatricula(null);
        return this;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public ProcessoSelectivoMatricula utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Turma getTurma() {
        return this.turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public ProcessoSelectivoMatricula turma(Turma turma) {
        this.setTurma(turma);
        return this;
    }

    public Discente getDiscente() {
        return this.discente;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }

    public ProcessoSelectivoMatricula discente(Discente discente) {
        this.setDiscente(discente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessoSelectivoMatricula)) {
            return false;
        }
        return id != null && id.equals(((ProcessoSelectivoMatricula) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoSelectivoMatricula{" +
            "id=" + getId() +
            ", localTeste='" + getLocalTeste() + "'" +
            ", dataTeste='" + getDataTeste() + "'" +
            ", notaTeste=" + getNotaTeste() +
            ", isAdmitido='" + getIsAdmitido() + "'" +
            "}";
    }
}
