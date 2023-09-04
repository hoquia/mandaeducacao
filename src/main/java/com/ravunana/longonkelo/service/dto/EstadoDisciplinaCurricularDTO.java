package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.CategoriaClassificacao;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoDisciplinaCurricularDTO implements Serializable {

    private Long id;

    private String uniqueSituacaoDisciplina;

    @NotNull
    private CategoriaClassificacao classificacao;

    @NotNull
    private String codigo;

    @NotNull
    private String descricao;

    @NotNull
    private String cor;

    @NotNull
    @DecimalMin(value = "0")
    private Double valor;

    private Set<DisciplinaCurricularDTO> disciplinasCurriculars = new HashSet<>();

    private EstadoDisciplinaCurricularDTO referencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueSituacaoDisciplina() {
        return uniqueSituacaoDisciplina;
    }

    public void setUniqueSituacaoDisciplina(String uniqueSituacaoDisciplina) {
        this.uniqueSituacaoDisciplina = uniqueSituacaoDisciplina;
    }

    public CategoriaClassificacao getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(CategoriaClassificacao classificacao) {
        this.classificacao = classificacao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Set<DisciplinaCurricularDTO> getDisciplinasCurriculars() {
        return disciplinasCurriculars;
    }

    public void setDisciplinasCurriculars(Set<DisciplinaCurricularDTO> disciplinasCurriculars) {
        this.disciplinasCurriculars = disciplinasCurriculars;
    }

    public EstadoDisciplinaCurricularDTO getReferencia() {
        return referencia;
    }

    public void setReferencia(EstadoDisciplinaCurricularDTO referencia) {
        this.referencia = referencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoDisciplinaCurricularDTO)) {
            return false;
        }

        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = (EstadoDisciplinaCurricularDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estadoDisciplinaCurricularDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoDisciplinaCurricularDTO{" +
            "id=" + getId() +
            ", uniqueSituacaoDisciplina='" + getUniqueSituacaoDisciplina() + "'" +
            ", classificacao='" + getClassificacao() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", cor='" + getCor() + "'" +
            ", valor=" + getValor() +
            ", disciplinasCurriculars=" + getDisciplinasCurriculars() +
            ", referencia=" + getReferencia() +
            "}";
    }
}
