package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.PlanoDesconto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoDescontoDTO implements Serializable {

    private Long id;

    @NotNull
    private String codigo;

    @NotNull
    private String nome;

    private Boolean isIsentoMulta;

    private Boolean isIsentoJuro;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal desconto;

    private Set<CategoriaEmolumentoDTO> categoriasEmolumentos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getIsIsentoMulta() {
        return isIsentoMulta;
    }

    public void setIsIsentoMulta(Boolean isIsentoMulta) {
        this.isIsentoMulta = isIsentoMulta;
    }

    public Boolean getIsIsentoJuro() {
        return isIsentoJuro;
    }

    public void setIsIsentoJuro(Boolean isIsentoJuro) {
        this.isIsentoJuro = isIsentoJuro;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Set<CategoriaEmolumentoDTO> getCategoriasEmolumentos() {
        return categoriasEmolumentos;
    }

    public void setCategoriasEmolumentos(Set<CategoriaEmolumentoDTO> categoriasEmolumentos) {
        this.categoriasEmolumentos = categoriasEmolumentos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoDescontoDTO)) {
            return false;
        }

        PlanoDescontoDTO planoDescontoDTO = (PlanoDescontoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, planoDescontoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoDescontoDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", isIsentoMulta='" + getIsIsentoMulta() + "'" +
            ", isIsentoJuro='" + getIsIsentoJuro() + "'" +
            ", desconto=" + getDesconto() +
            ", categoriasEmolumentos=" + getCategoriasEmolumentos() +
            "}";
    }
}
