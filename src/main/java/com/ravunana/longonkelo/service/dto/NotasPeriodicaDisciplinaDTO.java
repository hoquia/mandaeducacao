package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.Comporamento;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotasPeriodicaDisciplinaDTO implements Serializable {

    private Long id;

    private String chaveComposta;

    @Min(value = 0)
    @Max(value = 3)
    private Integer periodoLancamento;

    @DecimalMin(value = "0")
    private Double nota1;

    @DecimalMin(value = "0")
    private Double nota2;

    @DecimalMin(value = "0")
    private Double nota3;

    @NotNull
    @DecimalMin(value = "0")
    private Double media;

    @Min(value = 0)
    private Integer faltaJusticada;

    @Min(value = 0)
    private Integer faltaInjustificada;

    private Comporamento comportamento;

    private String hash;

    @NotNull
    private ZonedDateTime timestamp;

    private UserDTO utilizador;

    private TurmaDTO turma;

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

    public Double getNota1() {
        return nota1;
    }

    public void setNota1(Double nota1) {
        this.nota1 = nota1;
    }

    public Double getNota2() {
        return nota2;
    }

    public void setNota2(Double nota2) {
        this.nota2 = nota2;
    }

    public Double getNota3() {
        return nota3;
    }

    public void setNota3(Double nota3) {
        this.nota3 = nota3;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
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

    public Comporamento getComportamento() {
        return comportamento;
    }

    public void setComportamento(Comporamento comportamento) {
        this.comportamento = comportamento;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
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
        if (!(o instanceof NotasPeriodicaDisciplinaDTO)) {
            return false;
        }

        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = (NotasPeriodicaDisciplinaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notasPeriodicaDisciplinaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotasPeriodicaDisciplinaDTO{" +
            "id=" + getId() +
            ", chaveComposta='" + getChaveComposta() + "'" +
            ", periodoLancamento=" + getPeriodoLancamento() +
            ", nota1=" + getNota1() +
            ", nota2=" + getNota2() +
            ", nota3=" + getNota3() +
            ", media=" + getMedia() +
            ", faltaJusticada=" + getFaltaJusticada() +
            ", faltaInjustificada=" + getFaltaInjustificada() +
            ", comportamento='" + getComportamento() + "'" +
            ", hash='" + getHash() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", utilizador=" + getUtilizador() +
            ", turma=" + getTurma() +
            ", docente=" + getDocente() +
            ", disciplinaCurricular=" + getDisciplinaCurricular() +
            ", matricula=" + getMatricula() +
            ", estado=" + getEstado() +
            "}";
    }
}
