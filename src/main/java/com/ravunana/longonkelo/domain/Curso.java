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
 * A Curso.
 */
@Entity
@Table(name = "curso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

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

    @OneToMany(mappedBy = "curso")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "turmas", "utilizador", "classe", "curso", "disciplinasCurriculars" }, allowSetters = true)
    private Set<PlanoCurricular> planoCurriculars = new HashSet<>();

    @OneToMany(mappedBy = "curso")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "curso" }, allowSetters = true)
    private Set<ResponsavelCurso> responsaveis = new HashSet<>();

    @OneToMany(mappedBy = "curso")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "utilizador", "emolumento", "areaFormacao", "curso", "classe", "turno", "planoMulta" },
        allowSetters = true
    )
    private Set<PrecoEmolumento> precoEmolumentos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "cursos", "dissertacaoFinalCursos", "responsaveis", "precoEmolumentos", "nivelEnsino" },
        allowSetters = true
    )
    private AreaFormacao areaFormacao;

    @ManyToMany(mappedBy = "cursos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cursos" }, allowSetters = true)
    private Set<CampoActuacaoDissertacao> camposActuacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Curso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return this.imagem;
    }

    public Curso imagem(byte[] imagem) {
        this.setImagem(imagem);
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return this.imagemContentType;
    }

    public Curso imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Curso codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public Curso nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Curso descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<PlanoCurricular> getPlanoCurriculars() {
        return this.planoCurriculars;
    }

    public void setPlanoCurriculars(Set<PlanoCurricular> planoCurriculars) {
        if (this.planoCurriculars != null) {
            this.planoCurriculars.forEach(i -> i.setCurso(null));
        }
        if (planoCurriculars != null) {
            planoCurriculars.forEach(i -> i.setCurso(this));
        }
        this.planoCurriculars = planoCurriculars;
    }

    public Curso planoCurriculars(Set<PlanoCurricular> planoCurriculars) {
        this.setPlanoCurriculars(planoCurriculars);
        return this;
    }

    public Curso addPlanoCurricular(PlanoCurricular planoCurricular) {
        this.planoCurriculars.add(planoCurricular);
        planoCurricular.setCurso(this);
        return this;
    }

    public Curso removePlanoCurricular(PlanoCurricular planoCurricular) {
        this.planoCurriculars.remove(planoCurricular);
        planoCurricular.setCurso(null);
        return this;
    }

    public Set<ResponsavelCurso> getResponsaveis() {
        return this.responsaveis;
    }

    public void setResponsaveis(Set<ResponsavelCurso> responsavelCursos) {
        if (this.responsaveis != null) {
            this.responsaveis.forEach(i -> i.setCurso(null));
        }
        if (responsavelCursos != null) {
            responsavelCursos.forEach(i -> i.setCurso(this));
        }
        this.responsaveis = responsavelCursos;
    }

    public Curso responsaveis(Set<ResponsavelCurso> responsavelCursos) {
        this.setResponsaveis(responsavelCursos);
        return this;
    }

    public Curso addResponsaveis(ResponsavelCurso responsavelCurso) {
        this.responsaveis.add(responsavelCurso);
        responsavelCurso.setCurso(this);
        return this;
    }

    public Curso removeResponsaveis(ResponsavelCurso responsavelCurso) {
        this.responsaveis.remove(responsavelCurso);
        responsavelCurso.setCurso(null);
        return this;
    }

    public Set<PrecoEmolumento> getPrecoEmolumentos() {
        return this.precoEmolumentos;
    }

    public void setPrecoEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        if (this.precoEmolumentos != null) {
            this.precoEmolumentos.forEach(i -> i.setCurso(null));
        }
        if (precoEmolumentos != null) {
            precoEmolumentos.forEach(i -> i.setCurso(this));
        }
        this.precoEmolumentos = precoEmolumentos;
    }

    public Curso precoEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        this.setPrecoEmolumentos(precoEmolumentos);
        return this;
    }

    public Curso addPrecoEmolumento(PrecoEmolumento precoEmolumento) {
        this.precoEmolumentos.add(precoEmolumento);
        precoEmolumento.setCurso(this);
        return this;
    }

    public Curso removePrecoEmolumento(PrecoEmolumento precoEmolumento) {
        this.precoEmolumentos.remove(precoEmolumento);
        precoEmolumento.setCurso(null);
        return this;
    }

    public AreaFormacao getAreaFormacao() {
        return this.areaFormacao;
    }

    public void setAreaFormacao(AreaFormacao areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    public Curso areaFormacao(AreaFormacao areaFormacao) {
        this.setAreaFormacao(areaFormacao);
        return this;
    }

    public Set<CampoActuacaoDissertacao> getCamposActuacaos() {
        return this.camposActuacaos;
    }

    public void setCamposActuacaos(Set<CampoActuacaoDissertacao> campoActuacaoDissertacaos) {
        if (this.camposActuacaos != null) {
            this.camposActuacaos.forEach(i -> i.removeCursos(this));
        }
        if (campoActuacaoDissertacaos != null) {
            campoActuacaoDissertacaos.forEach(i -> i.addCursos(this));
        }
        this.camposActuacaos = campoActuacaoDissertacaos;
    }

    public Curso camposActuacaos(Set<CampoActuacaoDissertacao> campoActuacaoDissertacaos) {
        this.setCamposActuacaos(campoActuacaoDissertacaos);
        return this;
    }

    public Curso addCamposActuacao(CampoActuacaoDissertacao campoActuacaoDissertacao) {
        this.camposActuacaos.add(campoActuacaoDissertacao);
        campoActuacaoDissertacao.getCursos().add(this);
        return this;
    }

    public Curso removeCamposActuacao(CampoActuacaoDissertacao campoActuacaoDissertacao) {
        this.camposActuacaos.remove(campoActuacaoDissertacao);
        campoActuacaoDissertacao.getCursos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Curso)) {
            return false;
        }
        return id != null && id.equals(((Curso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Curso{" +
            "id=" + getId() +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
