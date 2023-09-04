package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.NotasGeralDisciplina} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotasGeralDisciplinaDTO implements Serializable {

    private Long id;

    private String chaveComposta;

    @Min(value = 0)
    @Max(value = 3)
    private Integer periodoLancamento;

    @DecimalMin(value = "0")
    private Double media1;

    @DecimalMin(value = "0")
    private Double media2;

    @DecimalMin(value = "0")
    private Double media3;

    @DecimalMin(value = "0")
    private Double exame;

    @DecimalMin(value = "0")
    private Double recurso;

    @DecimalMin(value = "0")
    private Double exameEspecial;

    @DecimalMin(value = "0")
    private Double notaConselho;

    @DecimalMin(value = "0")
    private Double mediaFinalDisciplina;

    @NotNull
    private ZonedDateTime timestamp;

    private String hash;

    @Min(value = 0)
    private Integer faltaJusticada;

    @Min(value = 0)
    private Integer faltaInjustificada;

    private UserDTO utilizador;

    private DocenteDTO docente;

    private DisciplinaCurricularDTO disciplinaCurricular;

    private MatriculaDTO matricula;

    private EstadoDisciplinaCurricularDTO estado;

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

    public Integer getPeriodoLancamento() {
        return periodoLancamento;
    }

    public void setPeriodoLancamento(Integer periodoLancamento) {
        this.periodoLancamento = periodoLancamento;
    }

    public Double getMedia1() {
        return media1;
    }

    public void setMedia1(Double media1) {
        this.media1 = media1;
    }

    public Double getMedia2() {
        return media2;
    }

    public void setMedia2(Double media2) {
        this.media2 = media2;
    }

    public Double getMedia3() {
        return media3;
    }

    public void setMedia3(Double media3) {
        this.media3 = media3;
    }

    public Double getExame() {
        return exame;
    }

    public void setExame(Double exame) {
        this.exame = exame;
    }

    public Double getRecurso() {
        return recurso;
    }

    public void setRecurso(Double recurso) {
        this.recurso = recurso;
    }

    public Double getExameEspecial() {
        return exameEspecial;
    }

    public void setExameEspecial(Double exameEspecial) {
        this.exameEspecial = exameEspecial;
    }

    public Double getNotaConselho() {
        return notaConselho;
    }

    public void setNotaConselho(Double notaConselho) {
        this.notaConselho = notaConselho;
    }

    public Double getMediaFinalDisciplina() {
        return mediaFinalDisciplina;
    }

    public void setMediaFinalDisciplina(Double mediaFinalDisciplina) {
        this.mediaFinalDisciplina = mediaFinalDisciplina;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getFaltaJusticada() {
        return faltaJusticada;
    }

    public void setFaltaJusticada(Integer faltaJusticada) {
        this.faltaJusticada = faltaJusticada;
    }

    public Integer getFaltaInjustificada() {
        return faltaInjustificada;
    }

    public void setFaltaInjustificada(Integer faltaInjustificada) {
        this.faltaInjustificada = faltaInjustificada;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
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

    public MatriculaDTO getMatricula() {
        return matricula;
    }

    public void setMatricula(MatriculaDTO matricula) {
        this.matricula = matricula;
    }

    public EstadoDisciplinaCurricularDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoDisciplinaCurricularDTO estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotasGeralDisciplinaDTO)) {
            return false;
        }

        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO = (NotasGeralDisciplinaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notasGeralDisciplinaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotasGeralDisciplinaDTO{" +
            "id=" + getId() +
            ", chaveComposta='" + getChaveComposta() + "'" +
            ", periodoLancamento=" + getPeriodoLancamento() +
            ", media1=" + getMedia1() +
            ", media2=" + getMedia2() +
            ", media3=" + getMedia3() +
            ", exame=" + getExame() +
            ", recurso=" + getRecurso() +
            ", exameEspecial=" + getExameEspecial() +
            ", notaConselho=" + getNotaConselho() +
            ", mediaFinalDisciplina=" + getMediaFinalDisciplina() +
            ", timestamp='" + getTimestamp() + "'" +
            ", hash='" + getHash() + "'" +
            ", faltaJusticada=" + getFaltaJusticada() +
            ", faltaInjustificada=" + getFaltaInjustificada() +
            ", utilizador=" + getUtilizador() +
            ", docente=" + getDocente() +
            ", disciplinaCurricular=" + getDisciplinaCurricular() +
            ", matricula=" + getMatricula() +
            ", estado=" + getEstado() +
            "}";
    }
}
