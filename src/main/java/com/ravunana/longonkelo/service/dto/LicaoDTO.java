package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.EstadoLicao;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Licao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LicaoDTO implements Serializable {

    private Long id;

    private String chaveComposta;

    @NotNull
    @Min(value = 1)
    private Integer numero;

    @NotNull
    private EstadoLicao estado;

    @Lob
    private String descricao;

    private UserDTO utilizador;

    private PlanoAulaDTO planoAula;

    private HorarioDTO horario;

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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public EstadoLicao getEstado() {
        return estado;
    }

    public void setEstado(EstadoLicao estado) {
        this.estado = estado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public PlanoAulaDTO getPlanoAula() {
        return planoAula;
    }

    public void setPlanoAula(PlanoAulaDTO planoAula) {
        this.planoAula = planoAula;
    }

    public HorarioDTO getHorario() {
        return horario;
    }

    public void setHorario(HorarioDTO horario) {
        this.horario = horario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicaoDTO)) {
            return false;
        }

        LicaoDTO licaoDTO = (LicaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, licaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LicaoDTO{" +
            "id=" + getId() +
            ", chaveComposta='" + getChaveComposta() + "'" +
            ", numero=" + getNumero() +
            ", estado='" + getEstado() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", utilizador=" + getUtilizador() +
            ", planoAula=" + getPlanoAula() +
            ", horario=" + getHorario() +
            "}";
    }
}
