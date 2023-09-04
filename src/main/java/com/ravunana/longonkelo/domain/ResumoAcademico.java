package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A ResumoAcademico.
 */
@Entity
@Table(name = "resumo_academico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResumoAcademico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tema_projecto", nullable = false, unique = true)
    private String temaProjecto;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    @Column(name = "nota_projecto", nullable = false)
    private Double notaProjecto;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao")
    private String observacao;

    @Column(name = "local_estagio")
    private String localEstagio;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    @Column(name = "nota_estagio", nullable = false)
    private Double notaEstagio;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    @Column(name = "media_final_disciplina", nullable = false)
    private Double mediaFinalDisciplina;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    @Column(name = "classificacao_final", nullable = false)
    private Double classificacaoFinal;

    @NotNull
    @Column(name = "numero_grupo", nullable = false)
    private String numeroGrupo;

    @NotNull
    @Column(name = "mesa_defesa", nullable = false)
    private String mesaDefesa;

    @NotNull
    @Column(name = "livro_registro", nullable = false)
    private String livroRegistro;

    @NotNull
    @Column(name = "numero_folha", nullable = false)
    private String numeroFolha;

    @NotNull
    @Column(name = "chefe_secretaria_pedagogica", nullable = false)
    private String chefeSecretariaPedagogica;

    @NotNull
    @Column(name = "sub_director_pedagogico", nullable = false)
    private String subDirectorPedagogico;

    @NotNull
    @Column(name = "director_geral", nullable = false)
    private String directorGeral;

    @NotNull
    @Column(name = "tutor_projecto", nullable = false)
    private String tutorProjecto;

    @NotNull
    @Column(name = "juri_mesa", nullable = false)
    private String juriMesa;

    @NotNull
    @Column(name = "empresa_estagio", nullable = false)
    private String empresaEstagio;

    @Lob
    @Column(name = "assinatura_digital", nullable = false)
    private byte[] assinaturaDigital;

    @NotNull
    @Column(name = "assinatura_digital_content_type", nullable = false)
    private String assinaturaDigitalContentType;

    @NotNull
    @Column(name = "hash", nullable = false)
    private String hash;

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
    private Turma ultimaTurmaMatriculada;

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
    private EstadoDisciplinaCurricular situacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResumoAcademico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemaProjecto() {
        return this.temaProjecto;
    }

    public ResumoAcademico temaProjecto(String temaProjecto) {
        this.setTemaProjecto(temaProjecto);
        return this;
    }

    public void setTemaProjecto(String temaProjecto) {
        this.temaProjecto = temaProjecto;
    }

    public Double getNotaProjecto() {
        return this.notaProjecto;
    }

    public ResumoAcademico notaProjecto(Double notaProjecto) {
        this.setNotaProjecto(notaProjecto);
        return this;
    }

    public void setNotaProjecto(Double notaProjecto) {
        this.notaProjecto = notaProjecto;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public ResumoAcademico observacao(String observacao) {
        this.setObservacao(observacao);
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getLocalEstagio() {
        return this.localEstagio;
    }

    public ResumoAcademico localEstagio(String localEstagio) {
        this.setLocalEstagio(localEstagio);
        return this;
    }

    public void setLocalEstagio(String localEstagio) {
        this.localEstagio = localEstagio;
    }

    public Double getNotaEstagio() {
        return this.notaEstagio;
    }

    public ResumoAcademico notaEstagio(Double notaEstagio) {
        this.setNotaEstagio(notaEstagio);
        return this;
    }

    public void setNotaEstagio(Double notaEstagio) {
        this.notaEstagio = notaEstagio;
    }

    public Double getMediaFinalDisciplina() {
        return this.mediaFinalDisciplina;
    }

    public ResumoAcademico mediaFinalDisciplina(Double mediaFinalDisciplina) {
        this.setMediaFinalDisciplina(mediaFinalDisciplina);
        return this;
    }

    public void setMediaFinalDisciplina(Double mediaFinalDisciplina) {
        this.mediaFinalDisciplina = mediaFinalDisciplina;
    }

    public Double getClassificacaoFinal() {
        return this.classificacaoFinal;
    }

    public ResumoAcademico classificacaoFinal(Double classificacaoFinal) {
        this.setClassificacaoFinal(classificacaoFinal);
        return this;
    }

    public void setClassificacaoFinal(Double classificacaoFinal) {
        this.classificacaoFinal = classificacaoFinal;
    }

    public String getNumeroGrupo() {
        return this.numeroGrupo;
    }

    public ResumoAcademico numeroGrupo(String numeroGrupo) {
        this.setNumeroGrupo(numeroGrupo);
        return this;
    }

    public void setNumeroGrupo(String numeroGrupo) {
        this.numeroGrupo = numeroGrupo;
    }

    public String getMesaDefesa() {
        return this.mesaDefesa;
    }

    public ResumoAcademico mesaDefesa(String mesaDefesa) {
        this.setMesaDefesa(mesaDefesa);
        return this;
    }

    public void setMesaDefesa(String mesaDefesa) {
        this.mesaDefesa = mesaDefesa;
    }

    public String getLivroRegistro() {
        return this.livroRegistro;
    }

    public ResumoAcademico livroRegistro(String livroRegistro) {
        this.setLivroRegistro(livroRegistro);
        return this;
    }

    public void setLivroRegistro(String livroRegistro) {
        this.livroRegistro = livroRegistro;
    }

    public String getNumeroFolha() {
        return this.numeroFolha;
    }

    public ResumoAcademico numeroFolha(String numeroFolha) {
        this.setNumeroFolha(numeroFolha);
        return this;
    }

    public void setNumeroFolha(String numeroFolha) {
        this.numeroFolha = numeroFolha;
    }

    public String getChefeSecretariaPedagogica() {
        return this.chefeSecretariaPedagogica;
    }

    public ResumoAcademico chefeSecretariaPedagogica(String chefeSecretariaPedagogica) {
        this.setChefeSecretariaPedagogica(chefeSecretariaPedagogica);
        return this;
    }

    public void setChefeSecretariaPedagogica(String chefeSecretariaPedagogica) {
        this.chefeSecretariaPedagogica = chefeSecretariaPedagogica;
    }

    public String getSubDirectorPedagogico() {
        return this.subDirectorPedagogico;
    }

    public ResumoAcademico subDirectorPedagogico(String subDirectorPedagogico) {
        this.setSubDirectorPedagogico(subDirectorPedagogico);
        return this;
    }

    public void setSubDirectorPedagogico(String subDirectorPedagogico) {
        this.subDirectorPedagogico = subDirectorPedagogico;
    }

    public String getDirectorGeral() {
        return this.directorGeral;
    }

    public ResumoAcademico directorGeral(String directorGeral) {
        this.setDirectorGeral(directorGeral);
        return this;
    }

    public void setDirectorGeral(String directorGeral) {
        this.directorGeral = directorGeral;
    }

    public String getTutorProjecto() {
        return this.tutorProjecto;
    }

    public ResumoAcademico tutorProjecto(String tutorProjecto) {
        this.setTutorProjecto(tutorProjecto);
        return this;
    }

    public void setTutorProjecto(String tutorProjecto) {
        this.tutorProjecto = tutorProjecto;
    }

    public String getJuriMesa() {
        return this.juriMesa;
    }

    public ResumoAcademico juriMesa(String juriMesa) {
        this.setJuriMesa(juriMesa);
        return this;
    }

    public void setJuriMesa(String juriMesa) {
        this.juriMesa = juriMesa;
    }

    public String getEmpresaEstagio() {
        return this.empresaEstagio;
    }

    public ResumoAcademico empresaEstagio(String empresaEstagio) {
        this.setEmpresaEstagio(empresaEstagio);
        return this;
    }

    public void setEmpresaEstagio(String empresaEstagio) {
        this.empresaEstagio = empresaEstagio;
    }

    public byte[] getAssinaturaDigital() {
        return this.assinaturaDigital;
    }

    public ResumoAcademico assinaturaDigital(byte[] assinaturaDigital) {
        this.setAssinaturaDigital(assinaturaDigital);
        return this;
    }

    public void setAssinaturaDigital(byte[] assinaturaDigital) {
        this.assinaturaDigital = assinaturaDigital;
    }

    public String getAssinaturaDigitalContentType() {
        return this.assinaturaDigitalContentType;
    }

    public ResumoAcademico assinaturaDigitalContentType(String assinaturaDigitalContentType) {
        this.assinaturaDigitalContentType = assinaturaDigitalContentType;
        return this;
    }

    public void setAssinaturaDigitalContentType(String assinaturaDigitalContentType) {
        this.assinaturaDigitalContentType = assinaturaDigitalContentType;
    }

    public String getHash() {
        return this.hash;
    }

    public ResumoAcademico hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public ResumoAcademico utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Turma getUltimaTurmaMatriculada() {
        return this.ultimaTurmaMatriculada;
    }

    public void setUltimaTurmaMatriculada(Turma turma) {
        this.ultimaTurmaMatriculada = turma;
    }

    public ResumoAcademico ultimaTurmaMatriculada(Turma turma) {
        this.setUltimaTurmaMatriculada(turma);
        return this;
    }

    public Discente getDiscente() {
        return this.discente;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }

    public ResumoAcademico discente(Discente discente) {
        this.setDiscente(discente);
        return this;
    }

    public EstadoDisciplinaCurricular getSituacao() {
        return this.situacao;
    }

    public void setSituacao(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.situacao = estadoDisciplinaCurricular;
    }

    public ResumoAcademico situacao(EstadoDisciplinaCurricular estadoDisciplinaCurricular) {
        this.setSituacao(estadoDisciplinaCurricular);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumoAcademico)) {
            return false;
        }
        return id != null && id.equals(((ResumoAcademico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumoAcademico{" +
            "id=" + getId() +
            ", temaProjecto='" + getTemaProjecto() + "'" +
            ", notaProjecto=" + getNotaProjecto() +
            ", observacao='" + getObservacao() + "'" +
            ", localEstagio='" + getLocalEstagio() + "'" +
            ", notaEstagio=" + getNotaEstagio() +
            ", mediaFinalDisciplina=" + getMediaFinalDisciplina() +
            ", classificacaoFinal=" + getClassificacaoFinal() +
            ", numeroGrupo='" + getNumeroGrupo() + "'" +
            ", mesaDefesa='" + getMesaDefesa() + "'" +
            ", livroRegistro='" + getLivroRegistro() + "'" +
            ", numeroFolha='" + getNumeroFolha() + "'" +
            ", chefeSecretariaPedagogica='" + getChefeSecretariaPedagogica() + "'" +
            ", subDirectorPedagogico='" + getSubDirectorPedagogico() + "'" +
            ", directorGeral='" + getDirectorGeral() + "'" +
            ", tutorProjecto='" + getTutorProjecto() + "'" +
            ", juriMesa='" + getJuriMesa() + "'" +
            ", empresaEstagio='" + getEmpresaEstagio() + "'" +
            ", assinaturaDigital='" + getAssinaturaDigital() + "'" +
            ", assinaturaDigitalContentType='" + getAssinaturaDigitalContentType() + "'" +
            ", hash='" + getHash() + "'" +
            "}";
    }
}
