package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.CampoActuacaoDissertacao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CampoActuacaoDissertacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @Lob
    private String descricao;

    private Boolean isActivo;

    private Set<CursoDTO> cursos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getIsActivo() {
        return isActivo;
    }

    public void setIsActivo(Boolean isActivo) {
        this.isActivo = isActivo;
    }

    public Set<CursoDTO> getCursos() {
        return cursos;
    }

    public void setCursos(Set<CursoDTO> cursos) {
        this.cursos = cursos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampoActuacaoDissertacaoDTO)) {
            return false;
        }

        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO = (CampoActuacaoDissertacaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, campoActuacaoDissertacaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampoActuacaoDissertacaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", isActivo='" + getIsActivo() + "'" +
            ", cursos=" + getCursos() +
            "}";
    }
}
