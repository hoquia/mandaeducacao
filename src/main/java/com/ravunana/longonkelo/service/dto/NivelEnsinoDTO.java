package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.UnidadeDuracao;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.NivelEnsino} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NivelEnsinoDTO implements Serializable {

    private Long id;

    @NotNull
    private String codigo;

    @NotNull
    private String nome;

    @Lob
    private String descricao;

    @Min(value = 0)
    private Integer idadeMinima;

    @Min(value = 0)
    private Integer idadeMaxima;

    @DecimalMin(value = "0")
    private Double duracao;

    @NotNull
    private UnidadeDuracao unidadeDuracao;

    @Min(value = 0)
    private Integer classeInicial;

    @Min(value = 0)
    private Integer classeFinal;

    private Integer classeExame;

    private Integer totalDisciplina;

    private String responsavelTurno;

    private String responsavelAreaFormacao;

    private String responsavelCurso;

    private String responsavelDisciplina;

    private String responsavelTurma;

    private String responsavelGeral;

    private String responsavelPedagogico;

    private String responsavelAdministrativo;

    private String responsavelSecretariaGeral;

    private String responsavelSecretariaPedagogico;

    private String descricaoDocente;

    private String descricaoDiscente;

    private NivelEnsinoDTO referencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdadeMinima() {
        return idadeMinima;
    }

    public void setIdadeMinima(Integer idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public Integer getIdadeMaxima() {
        return idadeMaxima;
    }

    public void setIdadeMaxima(Integer idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
    }

    public Double getDuracao() {
        return duracao;
    }

    public void setDuracao(Double duracao) {
        this.duracao = duracao;
    }

    public UnidadeDuracao getUnidadeDuracao() {
        return unidadeDuracao;
    }

    public void setUnidadeDuracao(UnidadeDuracao unidadeDuracao) {
        this.unidadeDuracao = unidadeDuracao;
    }

    public Integer getClasseInicial() {
        return classeInicial;
    }

    public void setClasseInicial(Integer classeInicial) {
        this.classeInicial = classeInicial;
    }

    public Integer getClasseFinal() {
        return classeFinal;
    }

    public void setClasseFinal(Integer classeFinal) {
        this.classeFinal = classeFinal;
    }

    public Integer getClasseExame() {
        return classeExame;
    }

    public void setClasseExame(Integer classeExame) {
        this.classeExame = classeExame;
    }

    public Integer getTotalDisciplina() {
        return totalDisciplina;
    }

    public void setTotalDisciplina(Integer totalDisciplina) {
        this.totalDisciplina = totalDisciplina;
    }

    public String getResponsavelTurno() {
        return responsavelTurno;
    }

    public void setResponsavelTurno(String responsavelTurno) {
        this.responsavelTurno = responsavelTurno;
    }

    public String getResponsavelAreaFormacao() {
        return responsavelAreaFormacao;
    }

    public void setResponsavelAreaFormacao(String responsavelAreaFormacao) {
        this.responsavelAreaFormacao = responsavelAreaFormacao;
    }

    public String getResponsavelCurso() {
        return responsavelCurso;
    }

    public void setResponsavelCurso(String responsavelCurso) {
        this.responsavelCurso = responsavelCurso;
    }

    public String getResponsavelDisciplina() {
        return responsavelDisciplina;
    }

    public void setResponsavelDisciplina(String responsavelDisciplina) {
        this.responsavelDisciplina = responsavelDisciplina;
    }

    public String getResponsavelTurma() {
        return responsavelTurma;
    }

    public void setResponsavelTurma(String responsavelTurma) {
        this.responsavelTurma = responsavelTurma;
    }

    public String getResponsavelGeral() {
        return responsavelGeral;
    }

    public void setResponsavelGeral(String responsavelGeral) {
        this.responsavelGeral = responsavelGeral;
    }

    public String getResponsavelPedagogico() {
        return responsavelPedagogico;
    }

    public void setResponsavelPedagogico(String responsavelPedagogico) {
        this.responsavelPedagogico = responsavelPedagogico;
    }

    public String getResponsavelAdministrativo() {
        return responsavelAdministrativo;
    }

    public void setResponsavelAdministrativo(String responsavelAdministrativo) {
        this.responsavelAdministrativo = responsavelAdministrativo;
    }

    public String getResponsavelSecretariaGeral() {
        return responsavelSecretariaGeral;
    }

    public void setResponsavelSecretariaGeral(String responsavelSecretariaGeral) {
        this.responsavelSecretariaGeral = responsavelSecretariaGeral;
    }

    public String getResponsavelSecretariaPedagogico() {
        return responsavelSecretariaPedagogico;
    }

    public void setResponsavelSecretariaPedagogico(String responsavelSecretariaPedagogico) {
        this.responsavelSecretariaPedagogico = responsavelSecretariaPedagogico;
    }

    public String getDescricaoDocente() {
        return descricaoDocente;
    }

    public void setDescricaoDocente(String descricaoDocente) {
        this.descricaoDocente = descricaoDocente;
    }

    public String getDescricaoDiscente() {
        return descricaoDiscente;
    }

    public void setDescricaoDiscente(String descricaoDiscente) {
        this.descricaoDiscente = descricaoDiscente;
    }

    public NivelEnsinoDTO getReferencia() {
        return referencia;
    }

    public void setReferencia(NivelEnsinoDTO referencia) {
        this.referencia = referencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NivelEnsinoDTO)) {
            return false;
        }

        NivelEnsinoDTO nivelEnsinoDTO = (NivelEnsinoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nivelEnsinoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NivelEnsinoDTO{" +
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
            ", referencia=" + getReferencia() +
            "}";
    }
}
