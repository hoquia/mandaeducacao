package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.CriterioDescricaoTurma;
import com.ravunana.longonkelo.domain.enumeration.CriterioNumeroChamada;
import com.ravunana.longonkelo.domain.enumeration.TipoTurma;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Turma} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TurmaDTO implements Serializable {

    private Long id;

    private String chaveComposta;

    @NotNull
    private TipoTurma tipoTurma;

    @NotNull
    @Min(value = 1)
    private Integer sala;

    @NotNull
    private String descricao;

    @NotNull
    @Min(value = 1)
    private Integer lotacao;

    @NotNull
    @Min(value = 0)
    private Integer confirmado;

    private LocalDate abertura;

    private LocalDate encerramento;

    private CriterioDescricaoTurma criterioDescricao;

    private CriterioNumeroChamada criterioOrdenacaoNumero;

    private Boolean fazInscricaoDepoisMatricula;

    private Boolean isDisponivel;

    private UserDTO utilizador;

    private TurmaDTO referencia;

    private PlanoCurricularDTO planoCurricular;

    private TurnoDTO turno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChaveComposta() {
        return chaveComposta;
    }

    public void setChaveComposta(String chaveComposta) {
        this.chaveComposta = chaveComposta;
    }

    public TipoTurma getTipoTurma() {
        return tipoTurma;
    }

    public void setTipoTurma(TipoTurma tipoTurma) {
        this.tipoTurma = tipoTurma;
    }

    public Integer getSala() {
        return sala;
    }

    public void setSala(Integer sala) {
        this.sala = sala;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLotacao() {
        return lotacao;
    }

    public void setLotacao(Integer lotacao) {
        this.lotacao = lotacao;
    }

    public Integer getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Integer confirmado) {
        this.confirmado = confirmado;
    }

    public LocalDate getAbertura() {
        return abertura;
    }

    public void setAbertura(LocalDate abertura) {
        this.abertura = abertura;
    }

    public LocalDate getEncerramento() {
        return encerramento;
    }

    public void setEncerramento(LocalDate encerramento) {
        this.encerramento = encerramento;
    }

    public CriterioDescricaoTurma getCriterioDescricao() {
        return criterioDescricao;
    }

    public void setCriterioDescricao(CriterioDescricaoTurma criterioDescricao) {
        this.criterioDescricao = criterioDescricao;
    }

    public CriterioNumeroChamada getCriterioOrdenacaoNumero() {
        return criterioOrdenacaoNumero;
    }

    public void setCriterioOrdenacaoNumero(CriterioNumeroChamada criterioOrdenacaoNumero) {
        this.criterioOrdenacaoNumero = criterioOrdenacaoNumero;
    }

    public Boolean getFazInscricaoDepoisMatricula() {
        return fazInscricaoDepoisMatricula;
    }

    public void setFazInscricaoDepoisMatricula(Boolean fazInscricaoDepoisMatricula) {
        this.fazInscricaoDepoisMatricula = fazInscricaoDepoisMatricula;
    }

    public Boolean getIsDisponivel() {
        return isDisponivel;
    }

    public void setIsDisponivel(Boolean isDisponivel) {
        this.isDisponivel = isDisponivel;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public TurmaDTO getReferencia() {
        return referencia;
    }

    public void setReferencia(TurmaDTO referencia) {
        this.referencia = referencia;
    }

    public PlanoCurricularDTO getPlanoCurricular() {
        return planoCurricular;
    }

    public void setPlanoCurricular(PlanoCurricularDTO planoCurricular) {
        this.planoCurricular = planoCurricular;
    }

    public TurnoDTO getTurno() {
        return turno;
    }

    public void setTurno(TurnoDTO turno) {
        this.turno = turno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TurmaDTO)) {
            return false;
        }

        TurmaDTO turmaDTO = (TurmaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, turmaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TurmaDTO{" +
            "id=" + getId() +
            ", chaveComposta='" + getChaveComposta() + "'" +
            ", tipoTurma='" + getTipoTurma() + "'" +
            ", sala=" + getSala() +
            ", descricao='" + getDescricao() + "'" +
            ", lotacao=" + getLotacao() +
            ", confirmado=" + getConfirmado() +
            ", abertura='" + getAbertura() + "'" +
            ", encerramento='" + getEncerramento() + "'" +
            ", criterioDescricao='" + getCriterioDescricao() + "'" +
            ", criterioOrdenacaoNumero='" + getCriterioOrdenacaoNumero() + "'" +
            ", fazInscricaoDepoisMatricula='" + getFazInscricaoDepoisMatricula() + "'" +
            ", isDisponivel='" + getIsDisponivel() + "'" +
            ", utilizador=" + getUtilizador() +
            ", referencia=" + getReferencia() +
            ", planoCurricular=" + getPlanoCurricular() +
            ", turno=" + getTurno() +
            "}";
    }
}
