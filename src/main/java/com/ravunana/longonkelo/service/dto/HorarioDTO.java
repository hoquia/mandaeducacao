package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.DiaSemana;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Horario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HorarioDTO implements Serializable {

    private Long id;

    private String chaveComposta1;

    private String chaveComposta2;

    @NotNull
    private DiaSemana diaSemana;

    private UserDTO utilizador;

    private TurmaDTO turma;

    private HorarioDTO referencia;

    private PeriodoHorarioDTO periodo;

    private DocenteDTO docente;

    private DisciplinaCurricularDTO disciplinaCurricular;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChaveComposta1() {
        return chaveComposta1;
    }

    public void setChaveComposta1(String chaveComposta1) {
        this.chaveComposta1 = chaveComposta1;
    }

    public String getChaveComposta2() {
        return chaveComposta2;
    }

    public void setChaveComposta2(String chaveComposta2) {
        this.chaveComposta2 = chaveComposta2;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public TurmaDTO getTurma() {
        return turma;
    }

    public void setTurma(TurmaDTO turma) {
        this.turma = turma;
    }

    public HorarioDTO getReferencia() {
        return referencia;
    }

    public void setReferencia(HorarioDTO referencia) {
        this.referencia = referencia;
    }

    public PeriodoHorarioDTO getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoHorarioDTO periodo) {
        this.periodo = periodo;
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
        if (!(o instanceof HorarioDTO)) {
            return false;
        }

        HorarioDTO horarioDTO = (HorarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, horarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HorarioDTO{" +
            "id=" + getId() +
            ", chaveComposta1='" + getChaveComposta1() + "'" +
            ", chaveComposta2='" + getChaveComposta2() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            ", utilizador=" + getUtilizador() +
            ", turma=" + getTurma() +
            ", referencia=" + getReferencia() +
            ", periodo=" + getPeriodo() +
            ", docente=" + getDocente() +
            ", disciplinaCurricular=" + getDisciplinaCurricular() +
            "}";
    }
}
