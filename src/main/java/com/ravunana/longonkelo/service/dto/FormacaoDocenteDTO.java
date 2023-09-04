package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.FormacaoDocente} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormacaoDocenteDTO implements Serializable {

    private Long id;

    @NotNull
    private String instituicaoEnsino;

    @NotNull
    private String areaFormacao;

    private String curso;

    private String especialidade;

    @NotNull
    private String grau;

    @NotNull
    private LocalDate inicio;

    private LocalDate fim;

    @Lob
    private byte[] anexo;

    private String anexoContentType;
    private LookupItemDTO grauAcademico;

    private DocenteDTO docente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstituicaoEnsino() {
        return instituicaoEnsino;
    }

    public void setInstituicaoEnsino(String instituicaoEnsino) {
        this.instituicaoEnsino = instituicaoEnsino;
    }

    public String getAreaFormacao() {
        return areaFormacao;
    }

    public void setAreaFormacao(String areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public String getAnexoContentType() {
        return anexoContentType;
    }

    public void setAnexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
    }

    public LookupItemDTO getGrauAcademico() {
        return grauAcademico;
    }

    public void setGrauAcademico(LookupItemDTO grauAcademico) {
        this.grauAcademico = grauAcademico;
    }

    public DocenteDTO getDocente() {
        return docente;
    }

    public void setDocente(DocenteDTO docente) {
        this.docente = docente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormacaoDocenteDTO)) {
            return false;
        }

        FormacaoDocenteDTO formacaoDocenteDTO = (FormacaoDocenteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, formacaoDocenteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormacaoDocenteDTO{" +
            "id=" + getId() +
            ", instituicaoEnsino='" + getInstituicaoEnsino() + "'" +
            ", areaFormacao='" + getAreaFormacao() + "'" +
            ", curso='" + getCurso() + "'" +
            ", especialidade='" + getEspecialidade() + "'" +
            ", grau='" + getGrau() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            ", anexo='" + getAnexo() + "'" +
            ", grauAcademico=" + getGrauAcademico() +
            ", docente=" + getDocente() +
            "}";
    }
}
