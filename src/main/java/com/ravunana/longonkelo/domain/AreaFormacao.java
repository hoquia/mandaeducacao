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
 * A AreaFormacao.
 */
@Entity
@Table(name = "area_formacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AreaFormacao implements Serializable {

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

    @OneToMany(mappedBy = "areaFormacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "planoCurriculars", "responsaveis", "precoEmolumentos", "areaFormacao", "camposActuacaos" },
        allowSetters = true
    )
    private Set<Curso> cursos = new HashSet<>();

    @OneToMany(mappedBy = "especialidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anoLectivos", "utilizador", "turma", "orientador", "especialidade", "discente", "estado", "natureza" },
        allowSetters = true
    )
    private Set<DissertacaoFinalCurso> dissertacaoFinalCursos = new HashSet<>();

    @OneToMany(mappedBy = "areaFormacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "responsavels", "anoLectivos", "utilizador", "areaFormacao" }, allowSetters = true)
    private Set<ResponsavelAreaFormacao> responsaveis = new HashSet<>();

    @OneToMany(mappedBy = "areaFormacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "utilizador", "emolumento", "areaFormacao", "curso", "classe", "turno", "planoMulta" },
        allowSetters = true
    )
    private Set<PrecoEmolumento> precoEmolumentos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "nivelEnsinos", "areaFormacaos", "referencia", "anoLectivos", "classes" }, allowSetters = true)
    private NivelEnsino nivelEnsino;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AreaFormacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return this.imagem;
    }

    public AreaFormacao imagem(byte[] imagem) {
        this.setImagem(imagem);
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return this.imagemContentType;
    }

    public AreaFormacao imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public AreaFormacao codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public AreaFormacao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public AreaFormacao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Curso> getCursos() {
        return this.cursos;
    }

    public void setCursos(Set<Curso> cursos) {
        if (this.cursos != null) {
            this.cursos.forEach(i -> i.setAreaFormacao(null));
        }
        if (cursos != null) {
            cursos.forEach(i -> i.setAreaFormacao(this));
        }
        this.cursos = cursos;
    }

    public AreaFormacao cursos(Set<Curso> cursos) {
        this.setCursos(cursos);
        return this;
    }

    public AreaFormacao addCursos(Curso curso) {
        this.cursos.add(curso);
        curso.setAreaFormacao(this);
        return this;
    }

    public AreaFormacao removeCursos(Curso curso) {
        this.cursos.remove(curso);
        curso.setAreaFormacao(null);
        return this;
    }

    public Set<DissertacaoFinalCurso> getDissertacaoFinalCursos() {
        return this.dissertacaoFinalCursos;
    }

    public void setDissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        if (this.dissertacaoFinalCursos != null) {
            this.dissertacaoFinalCursos.forEach(i -> i.setEspecialidade(null));
        }
        if (dissertacaoFinalCursos != null) {
            dissertacaoFinalCursos.forEach(i -> i.setEspecialidade(this));
        }
        this.dissertacaoFinalCursos = dissertacaoFinalCursos;
    }

    public AreaFormacao dissertacaoFinalCursos(Set<DissertacaoFinalCurso> dissertacaoFinalCursos) {
        this.setDissertacaoFinalCursos(dissertacaoFinalCursos);
        return this;
    }

    public AreaFormacao addDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.add(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setEspecialidade(this);
        return this;
    }

    public AreaFormacao removeDissertacaoFinalCurso(DissertacaoFinalCurso dissertacaoFinalCurso) {
        this.dissertacaoFinalCursos.remove(dissertacaoFinalCurso);
        dissertacaoFinalCurso.setEspecialidade(null);
        return this;
    }

    public Set<ResponsavelAreaFormacao> getResponsaveis() {
        return this.responsaveis;
    }

    public void setResponsaveis(Set<ResponsavelAreaFormacao> responsavelAreaFormacaos) {
        if (this.responsaveis != null) {
            this.responsaveis.forEach(i -> i.setAreaFormacao(null));
        }
        if (responsavelAreaFormacaos != null) {
            responsavelAreaFormacaos.forEach(i -> i.setAreaFormacao(this));
        }
        this.responsaveis = responsavelAreaFormacaos;
    }

    public AreaFormacao responsaveis(Set<ResponsavelAreaFormacao> responsavelAreaFormacaos) {
        this.setResponsaveis(responsavelAreaFormacaos);
        return this;
    }

    public AreaFormacao addResponsaveis(ResponsavelAreaFormacao responsavelAreaFormacao) {
        this.responsaveis.add(responsavelAreaFormacao);
        responsavelAreaFormacao.setAreaFormacao(this);
        return this;
    }

    public AreaFormacao removeResponsaveis(ResponsavelAreaFormacao responsavelAreaFormacao) {
        this.responsaveis.remove(responsavelAreaFormacao);
        responsavelAreaFormacao.setAreaFormacao(null);
        return this;
    }

    public Set<PrecoEmolumento> getPrecoEmolumentos() {
        return this.precoEmolumentos;
    }

    public void setPrecoEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        if (this.precoEmolumentos != null) {
            this.precoEmolumentos.forEach(i -> i.setAreaFormacao(null));
        }
        if (precoEmolumentos != null) {
            precoEmolumentos.forEach(i -> i.setAreaFormacao(this));
        }
        this.precoEmolumentos = precoEmolumentos;
    }

    public AreaFormacao precoEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        this.setPrecoEmolumentos(precoEmolumentos);
        return this;
    }

    public AreaFormacao addPrecoEmolumento(PrecoEmolumento precoEmolumento) {
        this.precoEmolumentos.add(precoEmolumento);
        precoEmolumento.setAreaFormacao(this);
        return this;
    }

    public AreaFormacao removePrecoEmolumento(PrecoEmolumento precoEmolumento) {
        this.precoEmolumentos.remove(precoEmolumento);
        precoEmolumento.setAreaFormacao(null);
        return this;
    }

    public NivelEnsino getNivelEnsino() {
        return this.nivelEnsino;
    }

    public void setNivelEnsino(NivelEnsino nivelEnsino) {
        this.nivelEnsino = nivelEnsino;
    }

    public AreaFormacao nivelEnsino(NivelEnsino nivelEnsino) {
        this.setNivelEnsino(nivelEnsino);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaFormacao)) {
            return false;
        }
        return id != null && id.equals(((AreaFormacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaFormacao{" +
            "id=" + getId() +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
