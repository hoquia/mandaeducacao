package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.EstadoLicao;
import com.ravunana.longonkelo.domain.enumeration.TipoAula;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.PlanoAula} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoAulaDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoAula tipoAula;

    @NotNull
    @Min(value = 0)
    private Integer semanaLectiva;

    @Lob
    private String perfilEntrada;

    @Lob
    private String perfilSaida;

    @NotNull
    private String assunto;

    @Lob
    private String objectivoGeral;

    @Lob
    private String objectivosEspecificos;

    @Min(value = 0)
    private Integer tempoTotalLicao;

    @NotNull
    private EstadoLicao estado;

    private UserDTO utilizador;

    private LookupItemDTO unidadeTematica;

    private LookupItemDTO subUnidadeTematica;

    private TurmaDTO turma;

    private DocenteDTO docente;

    private DisciplinaCurricularDTO disciplinaCurricular;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoAula getTipoAula() {
        return tipoAula;
    }

    public void setTipoAula(TipoAula tipoAula) {
        this.tipoAula = tipoAula;
    }

    public Integer getSemanaLectiva() {
        return semanaLectiva;
    }

    public void setSemanaLectiva(Integer semanaLectiva) {
        this.semanaLectiva = semanaLectiva;
    }

    public String getPerfilEntrada() {
        return perfilEntrada;
    }

    public void setPerfilEntrada(String perfilEntrada) {
        this.perfilEntrada = perfilEntrada;
    }

    public String getPerfilSaida() {
        return perfilSaida;
    }

    public void setPerfilSaida(String perfilSaida) {
        this.perfilSaida = perfilSaida;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getObjectivoGeral() {
        return objectivoGeral;
    }

    public void setObjectivoGeral(String objectivoGeral) {
        this.objectivoGeral = objectivoGeral;
    }

    public String getObjectivosEspecificos() {
        return objectivosEspecificos;
    }

    public void setObjectivosEspecificos(String objectivosEspecificos) {
        this.objectivosEspecificos = objectivosEspecificos;
    }

    public Integer getTempoTotalLicao() {
        return tempoTotalLicao;
    }

    public void setTempoTotalLicao(Integer tempoTotalLicao) {
        this.tempoTotalLicao = tempoTotalLicao;
    }

    public EstadoLicao getEstado() {
        return estado;
    }

    public void setEstado(EstadoLicao estado) {
        this.estado = estado;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public LookupItemDTO getUnidadeTematica() {
        return unidadeTematica;
    }

    public void setUnidadeTematica(LookupItemDTO unidadeTematica) {
        this.unidadeTematica = unidadeTematica;
    }

    public LookupItemDTO getSubUnidadeTematica() {
        return subUnidadeTematica;
    }

    public void setSubUnidadeTematica(LookupItemDTO subUnidadeTematica) {
        this.subUnidadeTematica = subUnidadeTematica;
    }

    public TurmaDTO getTurma() {
        return turma;
    }

    public void setTurma(TurmaDTO turma) {
        this.turma = turma;
    }

    public DocenteDTO getDocente() {
        return docente;
    }

    public void setDocente(DocenteDTO docente) {
        this.docente = docente;
    }

    public DisciplinaCurricularDTO getDisciplinaCurricular() {
        return disciplinaCurricular;
    }

    public void setDisciplinaCurricular(DisciplinaCurricularDTO disciplinaCurricular) {
        this.disciplinaCurricular = disciplinaCurricular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoAulaDTO)) {
            return false;
        }

        PlanoAulaDTO planoAulaDTO = (PlanoAulaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, planoAulaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoAulaDTO{" +
            "id=" + getId() +
            ", tipoAula='" + getTipoAula() + "'" +
            ", semanaLectiva=" + getSemanaLectiva() +
            ", perfilEntrada='" + getPerfilEntrada() + "'" +
            ", perfilSaida='" + getPerfilSaida() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", objectivoGeral='" + getObjectivoGeral() + "'" +
            ", objectivosEspecificos='" + getObjectivosEspecificos() + "'" +
            ", tempoTotalLicao=" + getTempoTotalLicao() +
            ", estado='" + getEstado() + "'" +
            ", utilizador=" + getUtilizador() +
            ", unidadeTematica=" + getUnidadeTematica() +
            ", subUnidadeTematica=" + getSubUnidadeTematica() +
            ", turma=" + getTurma() +
            ", docente=" + getDocente() +
            ", disciplinaCurricular=" + getDisciplinaCurricular() +
            "}";
    }
}
