package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PrecoEmolumento.
 */
@Entity
@Table(name = "preco_emolumento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrecoEmolumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "preco", precision = 21, scale = 2, nullable = false)
    private BigDecimal preco;

    @Column(name = "is_especifico_curso")
    private Boolean isEspecificoCurso;

    @Column(name = "is_especifico_area_formacao")
    private Boolean isEspecificoAreaFormacao;

    @Column(name = "is_especifico_classe")
    private Boolean isEspecificoClasse;

    @Column(name = "is_especifico_turno")
    private Boolean isEspecificoTurno;

    @ManyToOne
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "itemFacturas", "emolumentos", "precosEmolumentos", "categoria", "imposto", "referencia", "planoMulta" },
        allowSetters = true
    )
    private Emolumento emolumento;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "cursos", "dissertacaoFinalCursos", "responsaveis", "precoEmolumentos", "nivelEnsino" },
        allowSetters = true
    )
    private AreaFormacao areaFormacao;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "planoCurriculars", "responsaveis", "precoEmolumentos", "areaFormacao", "camposActuacaos" },
        allowSetters = true
    )
    private Curso curso;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "planoCurriculars", "precoEmolumentos", "nivesEnsinos", "periodosLancamentoNotas" },
        allowSetters = true
    )
    private Classe classe;

    @ManyToOne
    @JsonIgnoreProperties(value = { "responsavelTurnos", "precoEmolumentos", "periodoHorarios", "turmas" }, allowSetters = true)
    private Turno turno;

    @ManyToOne
    @JsonIgnoreProperties(value = { "categoriaEmolumentos", "emolumentos", "precoEmolumentos", "utilizador" }, allowSetters = true)
    private PlanoMulta planoMulta;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PrecoEmolumento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPreco() {
        return this.preco;
    }

    public PrecoEmolumento preco(BigDecimal preco) {
        this.setPreco(preco);
        return this;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getIsEspecificoCurso() {
        return this.isEspecificoCurso;
    }

    public PrecoEmolumento isEspecificoCurso(Boolean isEspecificoCurso) {
        this.setIsEspecificoCurso(isEspecificoCurso);
        return this;
    }

    public void setIsEspecificoCurso(Boolean isEspecificoCurso) {
        this.isEspecificoCurso = isEspecificoCurso;
    }

    public Boolean getIsEspecificoAreaFormacao() {
        return this.isEspecificoAreaFormacao;
    }

    public PrecoEmolumento isEspecificoAreaFormacao(Boolean isEspecificoAreaFormacao) {
        this.setIsEspecificoAreaFormacao(isEspecificoAreaFormacao);
        return this;
    }

    public void setIsEspecificoAreaFormacao(Boolean isEspecificoAreaFormacao) {
        this.isEspecificoAreaFormacao = isEspecificoAreaFormacao;
    }

    public Boolean getIsEspecificoClasse() {
        return this.isEspecificoClasse;
    }

    public PrecoEmolumento isEspecificoClasse(Boolean isEspecificoClasse) {
        this.setIsEspecificoClasse(isEspecificoClasse);
        return this;
    }

    public void setIsEspecificoClasse(Boolean isEspecificoClasse) {
        this.isEspecificoClasse = isEspecificoClasse;
    }

    public Boolean getIsEspecificoTurno() {
        return this.isEspecificoTurno;
    }

    public PrecoEmolumento isEspecificoTurno(Boolean isEspecificoTurno) {
        this.setIsEspecificoTurno(isEspecificoTurno);
        return this;
    }

    public void setIsEspecificoTurno(Boolean isEspecificoTurno) {
        this.isEspecificoTurno = isEspecificoTurno;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public PrecoEmolumento utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Emolumento getEmolumento() {
        return this.emolumento;
    }

    public void setEmolumento(Emolumento emolumento) {
        this.emolumento = emolumento;
    }

    public PrecoEmolumento emolumento(Emolumento emolumento) {
        this.setEmolumento(emolumento);
        return this;
    }

    public AreaFormacao getAreaFormacao() {
        return this.areaFormacao;
    }

    public void setAreaFormacao(AreaFormacao areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    public PrecoEmolumento areaFormacao(AreaFormacao areaFormacao) {
        this.setAreaFormacao(areaFormacao);
        return this;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public PrecoEmolumento curso(Curso curso) {
        this.setCurso(curso);
        return this;
    }

    public Classe getClasse() {
        return this.classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public PrecoEmolumento classe(Classe classe) {
        this.setClasse(classe);
        return this;
    }

    public Turno getTurno() {
        return this.turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public PrecoEmolumento turno(Turno turno) {
        this.setTurno(turno);
        return this;
    }

    public PlanoMulta getPlanoMulta() {
        return this.planoMulta;
    }

    public void setPlanoMulta(PlanoMulta planoMulta) {
        this.planoMulta = planoMulta;
    }

    public PrecoEmolumento planoMulta(PlanoMulta planoMulta) {
        this.setPlanoMulta(planoMulta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrecoEmolumento)) {
            return false;
        }
        return id != null && id.equals(((PrecoEmolumento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrecoEmolumento{" +
            "id=" + getId() +
            ", preco=" + getPreco() +
            ", isEspecificoCurso='" + getIsEspecificoCurso() + "'" +
            ", isEspecificoAreaFormacao='" + getIsEspecificoAreaFormacao() + "'" +
            ", isEspecificoClasse='" + getIsEspecificoClasse() + "'" +
            ", isEspecificoTurno='" + getIsEspecificoTurno() + "'" +
            "}";
    }
}
