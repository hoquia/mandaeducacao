package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.DisciplinaCurricular} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DisciplinaCurricularDTO implements Serializable {

    private Long id;

    private String uniqueDisciplinaCurricular;

    @NotNull
    private String descricao;

    @DecimalMin(value = "0")
    private Double cargaSemanal;

    private Boolean isTerminal;

    @NotNull
    @DecimalMin(value = "0")
    private Double mediaParaExame;

    @NotNull
    @DecimalMin(value = "0")
    private Double mediaParaRecurso;

    @NotNull
    @DecimalMin(value = "0")
    private Double mediaParaExameEspecial;

    @NotNull
    @DecimalMin(value = "0")
    private Double mediaParaDespensar;

    private LookupItemDTO componente;

    private LookupItemDTO regime;

    private Set<PlanoCurricularDTO> planosCurriculars = new HashSet<>();

    private DisciplinaDTO disciplina;

    private DisciplinaCurricularDTO referencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueDisciplinaCurricular() {
        return uniqueDisciplinaCurricular;
    }

    public void setUniqueDisciplinaCurricular(String uniqueDisciplinaCurricular) {
        this.uniqueDisciplinaCurricular = uniqueDisciplinaCurricular;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getCargaSemanal() {
        return cargaSemanal;
    }

    public void setCargaSemanal(Double cargaSemanal) {
        this.cargaSemanal = cargaSemanal;
    }

    public Boolean getIsTerminal() {
        return isTerminal;
    }

    public void setIsTerminal(Boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

    public Double getMediaParaExame() {
        return mediaParaExame;
    }

    public void setMediaParaExame(Double mediaParaExame) {
        this.mediaParaExame = mediaParaExame;
    }

    public Double getMediaParaRecurso() {
        return mediaParaRecurso;
    }

    public void setMediaParaRecurso(Double mediaParaRecurso) {
        this.mediaParaRecurso = mediaParaRecurso;
    }

    public Double getMediaParaExameEspecial() {
        return mediaParaExameEspecial;
    }

    public void setMediaParaExameEspecial(Double mediaParaExameEspecial) {
        this.mediaParaExameEspecial = mediaParaExameEspecial;
    }

    public Double getMediaParaDespensar() {
        return mediaParaDespensar;
    }

    public void setMediaParaDespensar(Double mediaParaDespensar) {
        this.mediaParaDespensar = mediaParaDespensar;
    }

    public LookupItemDTO getComponente() {
        return componente;
    }

    public void setComponente(LookupItemDTO componente) {
        this.componente = componente;
    }

    public LookupItemDTO getRegime() {
        return regime;
    }

    public void setRegime(LookupItemDTO regime) {
        this.regime = regime;
    }

    public Set<PlanoCurricularDTO> getPlanosCurriculars() {
        return planosCurriculars;
    }

    public void setPlanosCurriculars(Set<PlanoCurricularDTO> planosCurriculars) {
        this.planosCurriculars = planosCurriculars;
    }

    public DisciplinaDTO getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaDTO disciplina) {
        this.disciplina = disciplina;
    }

    public DisciplinaCurricularDTO getReferencia() {
        return referencia;
    }

    public void setReferencia(DisciplinaCurricularDTO referencia) {
        this.referencia = referencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisciplinaCurricularDTO)) {
            return false;
        }

        DisciplinaCurricularDTO disciplinaCurricularDTO = (DisciplinaCurricularDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, disciplinaCurricularDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisciplinaCurricularDTO{" +
            "id=" + getId() +
            ", uniqueDisciplinaCurricular='" + getUniqueDisciplinaCurricular() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", cargaSemanal=" + getCargaSemanal() +
            ", isTerminal='" + getIsTerminal() + "'" +
            ", mediaParaExame=" + getMediaParaExame() +
            ", mediaParaRecurso=" + getMediaParaRecurso() +
            ", mediaParaExameEspecial=" + getMediaParaExameEspecial() +
            ", mediaParaDespensar=" + getMediaParaDespensar() +
            ", componente=" + getComponente() +
            ", regime=" + getRegime() +
            ", planosCurriculars=" + getPlanosCurriculars() +
            ", disciplina=" + getDisciplina() +
            ", referencia=" + getReferencia() +
            "}";
    }
}
