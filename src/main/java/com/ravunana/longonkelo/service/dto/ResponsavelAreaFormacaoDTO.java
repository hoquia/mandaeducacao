package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.ResponsavelAreaFormacao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResponsavelAreaFormacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate de;

    @NotNull
    private LocalDate ate;

    @Lob
    private String descricao;

    private ZonedDateTime timestamp;

    private UserDTO utilizador;

    private AreaFormacaoDTO areaFormacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDe() {
        return de;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public LocalDate getAte() {
        return ate;
    }

    public void setAte(LocalDate ate) {
        this.ate = ate;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public AreaFormacaoDTO getAreaFormacao() {
        return areaFormacao;
    }

    public void setAreaFormacao(AreaFormacaoDTO areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponsavelAreaFormacaoDTO)) {
            return false;
        }

        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = (ResponsavelAreaFormacaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, responsavelAreaFormacaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponsavelAreaFormacaoDTO{" +
            "id=" + getId() +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", utilizador=" + getUtilizador() +
            ", areaFormacao=" + getAreaFormacao() +
            "}";
    }
}
