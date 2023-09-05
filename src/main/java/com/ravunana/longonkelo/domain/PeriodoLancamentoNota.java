package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.TipoAvaliacao;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PeriodoLancamentoNota.
 */
@Entity
@Table(name = "periodo_lancamento_nota")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeriodoLancamentoNota implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_avaliacao", nullable = false)
    private TipoAvaliacao tipoAvaliacao;

    @NotNull
    @Column(name = "de", nullable = false)
    private ZonedDateTime de;

    @NotNull
    @Column(name = "ate", nullable = false)
    private ZonedDateTime ate;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @ManyToOne
    private User utilizador;

    @ManyToMany
    @JoinTable(
        name = "rel_periodo_lancamento_nota__classe",
        joinColumns = @JoinColumn(name = "periodo_lancamento_nota_id"),
        inverseJoinColumns = @JoinColumn(name = "classe_id")
    )
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

    public PeriodoLancamentoNota id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoAvaliacao getTipoAvaliacao() {
        return this.tipoAvaliacao;
    }

    public PeriodoLancamentoNota tipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
        this.setTipoAvaliacao(tipoAvaliacao);
        return this;
    }

    public void setTipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public ZonedDateTime getDe() {
        return this.de;
    }

    public PeriodoLancamentoNota de(ZonedDateTime de) {
        this.setDe(de);
        return this;
    }

    public void setDe(ZonedDateTime de) {
        this.de = de;
    }

    public ZonedDateTime getAte() {
        return this.ate;
    }

    public PeriodoLancamentoNota ate(ZonedDateTime ate) {
        this.setAte(ate);
        return this;
    }

    public void setAte(ZonedDateTime ate) {
        this.ate = ate;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public PeriodoLancamentoNota timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getUtilizador() {
        return this.utilizador;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public PeriodoLancamentoNota utilizador(User user) {
        this.setUtilizador(user);
        return this;
    }

    public Set<Classe> getClasses() {
        return this.classes;
    }

    public void setClasses(Set<Classe> classes) {
        this.classes = classes;
    }

    public PeriodoLancamentoNota classes(Set<Classe> classes) {
        this.setClasses(classes);
        return this;
    }

    public PeriodoLancamentoNota addClasse(Classe classe) {
        this.classes.add(classe);
        classe.getPeriodosLancamentoNotas().add(this);
        return this;
    }

    public PeriodoLancamentoNota removeClasse(Classe classe) {
        this.classes.remove(classe);
        classe.getPeriodosLancamentoNotas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodoLancamentoNota)) {
            return false;
        }
        return id != null && id.equals(((PeriodoLancamentoNota) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodoLancamentoNota{" +
            "id=" + getId() +
            ", tipoAvaliacao='" + getTipoAvaliacao() + "'" +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
