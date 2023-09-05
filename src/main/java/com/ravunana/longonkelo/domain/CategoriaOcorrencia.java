package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A CategoriaOcorrencia.
 */
@Entity
@Table(name = "categoria_ocorrencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriaOcorrencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Column(name = "sansao_disicplinar")
    private String sansaoDisicplinar;

    @Column(name = "is_notifica_encaregado")
    private Boolean isNotificaEncaregado;

    @Column(name = "is_send_email")
    private Boolean isSendEmail;

    @Column(name = "is_send_sms")
    private Boolean isSendSms;

    @Column(name = "is_send_push")
    private Boolean isSendPush;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao")
    private String observacao;

    @OneToMany(mappedBy = "referencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "categoriaOcorrencias", "ocorrencias", "encaminhar", "referencia", "medidaDisciplinar" },
        allowSetters = true
    )
    private Set<CategoriaOcorrencia> categoriaOcorrencias = new HashSet<>();

    @OneToMany(mappedBy = "estado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "ocorrencias", "anoLectivos", "utilizador", "referencia", "docente", "matricula", "estado", "licao" },
        allowSetters = true
    )
    private Set<Ocorrencia> ocorrencias = new HashSet<>();

    @ManyToOne
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
    private Docente encaminhar;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "categoriaOcorrencias", "ocorrencias", "encaminhar", "referencia", "medidaDisciplinar" },
        allowSetters = true
    )
    private CategoriaOcorrencia referencia;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "categoriaOcorrencias" }, allowSetters = true)
    private MedidaDisciplinar medidaDisciplinar;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CategoriaOcorrencia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public CategoriaOcorrencia codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSansaoDisicplinar() {
        return this.sansaoDisicplinar;
    }

    public CategoriaOcorrencia sansaoDisicplinar(String sansaoDisicplinar) {
        this.setSansaoDisicplinar(sansaoDisicplinar);
        return this;
    }

    public void setSansaoDisicplinar(String sansaoDisicplinar) {
        this.sansaoDisicplinar = sansaoDisicplinar;
    }

    public Boolean getIsNotificaEncaregado() {
        return this.isNotificaEncaregado;
    }

    public CategoriaOcorrencia isNotificaEncaregado(Boolean isNotificaEncaregado) {
        this.setIsNotificaEncaregado(isNotificaEncaregado);
        return this;
    }

    public void setIsNotificaEncaregado(Boolean isNotificaEncaregado) {
        this.isNotificaEncaregado = isNotificaEncaregado;
    }

    public Boolean getIsSendEmail() {
        return this.isSendEmail;
    }

    public CategoriaOcorrencia isSendEmail(Boolean isSendEmail) {
        this.setIsSendEmail(isSendEmail);
        return this;
    }

    public void setIsSendEmail(Boolean isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    public Boolean getIsSendSms() {
        return this.isSendSms;
    }

    public CategoriaOcorrencia isSendSms(Boolean isSendSms) {
        this.setIsSendSms(isSendSms);
        return this;
    }

    public void setIsSendSms(Boolean isSendSms) {
        this.isSendSms = isSendSms;
    }

    public Boolean getIsSendPush() {
        return this.isSendPush;
    }

    public CategoriaOcorrencia isSendPush(Boolean isSendPush) {
        this.setIsSendPush(isSendPush);
        return this;
    }

    public void setIsSendPush(Boolean isSendPush) {
        this.isSendPush = isSendPush;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public CategoriaOcorrencia descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public CategoriaOcorrencia observacao(String observacao) {
        this.setObservacao(observacao);
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Set<CategoriaOcorrencia> getCategoriaOcorrencias() {
        return this.categoriaOcorrencias;
    }

    public void setCategoriaOcorrencias(Set<CategoriaOcorrencia> categoriaOcorrencias) {
        if (this.categoriaOcorrencias != null) {
            this.categoriaOcorrencias.forEach(i -> i.setReferencia(null));
        }
        if (categoriaOcorrencias != null) {
            categoriaOcorrencias.forEach(i -> i.setReferencia(this));
        }
        this.categoriaOcorrencias = categoriaOcorrencias;
    }

    public CategoriaOcorrencia categoriaOcorrencias(Set<CategoriaOcorrencia> categoriaOcorrencias) {
        this.setCategoriaOcorrencias(categoriaOcorrencias);
        return this;
    }

    public CategoriaOcorrencia addCategoriaOcorrencia(CategoriaOcorrencia categoriaOcorrencia) {
        this.categoriaOcorrencias.add(categoriaOcorrencia);
        categoriaOcorrencia.setReferencia(this);
        return this;
    }

    public CategoriaOcorrencia removeCategoriaOcorrencia(CategoriaOcorrencia categoriaOcorrencia) {
        this.categoriaOcorrencias.remove(categoriaOcorrencia);
        categoriaOcorrencia.setReferencia(null);
        return this;
    }

    public Set<Ocorrencia> getOcorrencias() {
        return this.ocorrencias;
    }

    public void setOcorrencias(Set<Ocorrencia> ocorrencias) {
        if (this.ocorrencias != null) {
            this.ocorrencias.forEach(i -> i.setEstado(null));
        }
        if (ocorrencias != null) {
            ocorrencias.forEach(i -> i.setEstado(this));
        }
        this.ocorrencias = ocorrencias;
    }

    public CategoriaOcorrencia ocorrencias(Set<Ocorrencia> ocorrencias) {
        this.setOcorrencias(ocorrencias);
        return this;
    }

    public CategoriaOcorrencia addOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
        ocorrencia.setEstado(this);
        return this;
    }

    public CategoriaOcorrencia removeOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.remove(ocorrencia);
        ocorrencia.setEstado(null);
        return this;
    }

    public Docente getEncaminhar() {
        return this.encaminhar;
    }

    public void setEncaminhar(Docente docente) {
        this.encaminhar = docente;
    }

    public CategoriaOcorrencia encaminhar(Docente docente) {
        this.setEncaminhar(docente);
        return this;
    }

    public CategoriaOcorrencia getReferencia() {
        return this.referencia;
    }

    public void setReferencia(CategoriaOcorrencia categoriaOcorrencia) {
        this.referencia = categoriaOcorrencia;
    }

    public CategoriaOcorrencia referencia(CategoriaOcorrencia categoriaOcorrencia) {
        this.setReferencia(categoriaOcorrencia);
        return this;
    }

    public MedidaDisciplinar getMedidaDisciplinar() {
        return this.medidaDisciplinar;
    }

    public void setMedidaDisciplinar(MedidaDisciplinar medidaDisciplinar) {
        this.medidaDisciplinar = medidaDisciplinar;
    }

    public CategoriaOcorrencia medidaDisciplinar(MedidaDisciplinar medidaDisciplinar) {
        this.setMedidaDisciplinar(medidaDisciplinar);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaOcorrencia)) {
            return false;
        }
        return id != null && id.equals(((CategoriaOcorrencia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriaOcorrencia{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", sansaoDisicplinar='" + getSansaoDisicplinar() + "'" +
            ", isNotificaEncaregado='" + getIsNotificaEncaregado() + "'" +
            ", isSendEmail='" + getIsSendEmail() + "'" +
            ", isSendSms='" + getIsSendSms() + "'" +
            ", isSendPush='" + getIsSendPush() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
