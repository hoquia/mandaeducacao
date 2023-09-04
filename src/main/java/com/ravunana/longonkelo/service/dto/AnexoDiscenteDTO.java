package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.CategoriaAnexo;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.AnexoDiscente} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoDiscenteDTO implements Serializable {

    private Long id;

    @NotNull
    private CategoriaAnexo categoria;

    @Lob
    private byte[] anexo;

    private String anexoContentType;

    @Lob
    private String descricao;

    private LocalDate validade;

    private ZonedDateTime timestamp;

    private DiscenteDTO discente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaAnexo getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAnexo categoria) {
        this.categoria = categoria;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public DiscenteDTO getDiscente() {
        return discente;
    }

    public void setDiscente(DiscenteDTO discente) {
        this.discente = discente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoDiscenteDTO)) {
            return false;
        }

        AnexoDiscenteDTO anexoDiscenteDTO = (AnexoDiscenteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anexoDiscenteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoDiscenteDTO{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            ", anexo='" + getAnexo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", validade='" + getValidade() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", discente=" + getDiscente() +
            "}";
    }
}
