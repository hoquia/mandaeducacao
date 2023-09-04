package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.PlanoCurricular} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoCurricularDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private String formulaClassificacaoFinal;

    @NotNull
    @Min(value = 0)
    private Integer numeroDisciplinaAprova;

    @NotNull
    @Min(value = 0)
    private Integer numeroDisciplinaReprova;

    @NotNull
    @Min(value = 0)
    private Integer numeroDisciplinaRecurso;

    @NotNull
    @Min(value = 0)
    private Integer numeroDisciplinaExame;

    @NotNull
    @Min(value = 0)
    private Integer numeroDisciplinaExameEspecial;

    @NotNull
    @Min(value = 0)
    private Integer numeroFaltaReprova;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double pesoMedia1;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double pesoMedia2;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double pesoMedia3;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double pesoRecurso;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double pesoExame;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double pesoExameEspecial;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double pesoNotaCoselho;

    @NotNull
    private String siglaProva1;

    @NotNull
    private String siglaProva2;

    @NotNull
    private String siglaProva3;

    @NotNull
    private String siglaMedia1;

    @NotNull
    private String siglaMedia2;

    @NotNull
    private String siglaMedia3;

    @NotNull
    private String formulaMedia;

    @NotNull
    private String formulaDispensa;

    @NotNull
    private String formulaExame;

    @NotNull
    private String formulaRecurso;

    @NotNull
    private String formulaExameEspecial;

    private UserDTO utilizador;

    private ClasseDTO classe;

    private CursoDTO curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFormulaClassificacaoFinal() {
        return formulaClassificacaoFinal;
    }

    public void setFormulaClassificacaoFinal(String formulaClassificacaoFinal) {
        this.formulaClassificacaoFinal = formulaClassificacaoFinal;
    }

    public Integer getNumeroDisciplinaAprova() {
        return numeroDisciplinaAprova;
    }

    public void setNumeroDisciplinaAprova(Integer numeroDisciplinaAprova) {
        this.numeroDisciplinaAprova = numeroDisciplinaAprova;
    }

    public Integer getNumeroDisciplinaReprova() {
        return numeroDisciplinaReprova;
    }

    public void setNumeroDisciplinaReprova(Integer numeroDisciplinaReprova) {
        this.numeroDisciplinaReprova = numeroDisciplinaReprova;
    }

    public Integer getNumeroDisciplinaRecurso() {
        return numeroDisciplinaRecurso;
    }

    public void setNumeroDisciplinaRecurso(Integer numeroDisciplinaRecurso) {
        this.numeroDisciplinaRecurso = numeroDisciplinaRecurso;
    }

    public Integer getNumeroDisciplinaExame() {
        return numeroDisciplinaExame;
    }

    public void setNumeroDisciplinaExame(Integer numeroDisciplinaExame) {
        this.numeroDisciplinaExame = numeroDisciplinaExame;
    }

    public Integer getNumeroDisciplinaExameEspecial() {
        return numeroDisciplinaExameEspecial;
    }

    public void setNumeroDisciplinaExameEspecial(Integer numeroDisciplinaExameEspecial) {
        this.numeroDisciplinaExameEspecial = numeroDisciplinaExameEspecial;
    }

    public Integer getNumeroFaltaReprova() {
        return numeroFaltaReprova;
    }

    public void setNumeroFaltaReprova(Integer numeroFaltaReprova) {
        this.numeroFaltaReprova = numeroFaltaReprova;
    }

    public Double getPesoMedia1() {
        return pesoMedia1;
    }

    public void setPesoMedia1(Double pesoMedia1) {
        this.pesoMedia1 = pesoMedia1;
    }

    public Double getPesoMedia2() {
        return pesoMedia2;
    }

    public void setPesoMedia2(Double pesoMedia2) {
        this.pesoMedia2 = pesoMedia2;
    }

    public Double getPesoMedia3() {
        return pesoMedia3;
    }

    public void setPesoMedia3(Double pesoMedia3) {
        this.pesoMedia3 = pesoMedia3;
    }

    public Double getPesoRecurso() {
        return pesoRecurso;
    }

    public void setPesoRecurso(Double pesoRecurso) {
        this.pesoRecurso = pesoRecurso;
    }

    public Double getPesoExame() {
        return pesoExame;
    }

    public void setPesoExame(Double pesoExame) {
        this.pesoExame = pesoExame;
    }

    public Double getPesoExameEspecial() {
        return pesoExameEspecial;
    }

    public void setPesoExameEspecial(Double pesoExameEspecial) {
        this.pesoExameEspecial = pesoExameEspecial;
    }

    public Double getPesoNotaCoselho() {
        return pesoNotaCoselho;
    }

    public void setPesoNotaCoselho(Double pesoNotaCoselho) {
        this.pesoNotaCoselho = pesoNotaCoselho;
    }

    public String getSiglaProva1() {
        return siglaProva1;
    }

    public void setSiglaProva1(String siglaProva1) {
        this.siglaProva1 = siglaProva1;
    }

    public String getSiglaProva2() {
        return siglaProva2;
    }

    public void setSiglaProva2(String siglaProva2) {
        this.siglaProva2 = siglaProva2;
    }

    public String getSiglaProva3() {
        return siglaProva3;
    }

    public void setSiglaProva3(String siglaProva3) {
        this.siglaProva3 = siglaProva3;
    }

    public String getSiglaMedia1() {
        return siglaMedia1;
    }

    public void setSiglaMedia1(String siglaMedia1) {
        this.siglaMedia1 = siglaMedia1;
    }

    public String getSiglaMedia2() {
        return siglaMedia2;
    }

    public void setSiglaMedia2(String siglaMedia2) {
        this.siglaMedia2 = siglaMedia2;
    }

    public String getSiglaMedia3() {
        return siglaMedia3;
    }

    public void setSiglaMedia3(String siglaMedia3) {
        this.siglaMedia3 = siglaMedia3;
    }

    public String getFormulaMedia() {
        return formulaMedia;
    }

    public void setFormulaMedia(String formulaMedia) {
        this.formulaMedia = formulaMedia;
    }

    public String getFormulaDispensa() {
        return formulaDispensa;
    }

    public void setFormulaDispensa(String formulaDispensa) {
        this.formulaDispensa = formulaDispensa;
    }

    public String getFormulaExame() {
        return formulaExame;
    }

    public void setFormulaExame(String formulaExame) {
        this.formulaExame = formulaExame;
    }

    public String getFormulaRecurso() {
        return formulaRecurso;
    }

    public void setFormulaRecurso(String formulaRecurso) {
        this.formulaRecurso = formulaRecurso;
    }

    public String getFormulaExameEspecial() {
        return formulaExameEspecial;
    }

    public void setFormulaExameEspecial(String formulaExameEspecial) {
        this.formulaExameEspecial = formulaExameEspecial;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public ClasseDTO getClasse() {
        return classe;
    }

    public void setClasse(ClasseDTO classe) {
        this.classe = classe;
    }

    public CursoDTO getCurso() {
        return curso;
    }

    public void setCurso(CursoDTO curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoCurricularDTO)) {
            return false;
        }

        PlanoCurricularDTO planoCurricularDTO = (PlanoCurricularDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, planoCurricularDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoCurricularDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", formulaClassificacaoFinal='" + getFormulaClassificacaoFinal() + "'" +
            ", numeroDisciplinaAprova=" + getNumeroDisciplinaAprova() +
            ", numeroDisciplinaReprova=" + getNumeroDisciplinaReprova() +
            ", numeroDisciplinaRecurso=" + getNumeroDisciplinaRecurso() +
            ", numeroDisciplinaExame=" + getNumeroDisciplinaExame() +
            ", numeroDisciplinaExameEspecial=" + getNumeroDisciplinaExameEspecial() +
            ", numeroFaltaReprova=" + getNumeroFaltaReprova() +
            ", pesoMedia1=" + getPesoMedia1() +
            ", pesoMedia2=" + getPesoMedia2() +
            ", pesoMedia3=" + getPesoMedia3() +
            ", pesoRecurso=" + getPesoRecurso() +
            ", pesoExame=" + getPesoExame() +
            ", pesoExameEspecial=" + getPesoExameEspecial() +
            ", pesoNotaCoselho=" + getPesoNotaCoselho() +
            ", siglaProva1='" + getSiglaProva1() + "'" +
            ", siglaProva2='" + getSiglaProva2() + "'" +
            ", siglaProva3='" + getSiglaProva3() + "'" +
            ", siglaMedia1='" + getSiglaMedia1() + "'" +
            ", siglaMedia2='" + getSiglaMedia2() + "'" +
            ", siglaMedia3='" + getSiglaMedia3() + "'" +
            ", formulaMedia='" + getFormulaMedia() + "'" +
            ", formulaDispensa='" + getFormulaDispensa() + "'" +
            ", formulaExame='" + getFormulaExame() + "'" +
            ", formulaRecurso='" + getFormulaRecurso() + "'" +
            ", formulaExameEspecial='" + getFormulaExameEspecial() + "'" +
            ", utilizador=" + getUtilizador() +
            ", classe=" + getClasse() +
            ", curso=" + getCurso() +
            "}";
    }
}
