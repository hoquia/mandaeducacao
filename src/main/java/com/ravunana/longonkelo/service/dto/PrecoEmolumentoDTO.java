package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.PrecoEmolumento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrecoEmolumentoDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal preco;

    private Boolean isEspecificoCurso;

    private Boolean isEspecificoAreaFormacao;

    private Boolean isEspecificoClasse;

    private Boolean isEspecificoTurno;

    private UserDTO utilizador;

    private EmolumentoDTO emolumento;

    private AreaFormacaoDTO areaFormacao;

    private CursoDTO curso;

    private ClasseDTO classe;

    private TurnoDTO turno;

    private PlanoMultaDTO planoMulta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getIsEspecificoCurso() {
        return isEspecificoCurso;
    }

    public void setIsEspecificoCurso(Boolean isEspecificoCurso) {
        this.isEspecificoCurso = isEspecificoCurso;
    }

    public Boolean getIsEspecificoAreaFormacao() {
        return isEspecificoAreaFormacao;
    }

    public void setIsEspecificoAreaFormacao(Boolean isEspecificoAreaFormacao) {
        this.isEspecificoAreaFormacao = isEspecificoAreaFormacao;
    }

    public Boolean getIsEspecificoClasse() {
        return isEspecificoClasse;
    }

    public void setIsEspecificoClasse(Boolean isEspecificoClasse) {
        this.isEspecificoClasse = isEspecificoClasse;
    }

    public Boolean getIsEspecificoTurno() {
        return isEspecificoTurno;
    }

    public void setIsEspecificoTurno(Boolean isEspecificoTurno) {
        this.isEspecificoTurno = isEspecificoTurno;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public EmolumentoDTO getEmolumento() {
        return emolumento;
    }

    public void setEmolumento(EmolumentoDTO emolumento) {
        this.emolumento = emolumento;
    }

    public AreaFormacaoDTO getAreaFormacao() {
        return areaFormacao;
    }

    public void setAreaFormacao(AreaFormacaoDTO areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    public CursoDTO getCurso() {
        return curso;
    }

    public void setCurso(CursoDTO curso) {
        this.curso = curso;
    }

    public ClasseDTO getClasse() {
        return classe;
    }

    public void setClasse(ClasseDTO classe) {
        this.classe = classe;
    }

    public TurnoDTO getTurno() {
        return turno;
    }

    public void setTurno(TurnoDTO turno) {
        this.turno = turno;
    }

    public PlanoMultaDTO getPlanoMulta() {
        return planoMulta;
    }

    public void setPlanoMulta(PlanoMultaDTO planoMulta) {
        this.planoMulta = planoMulta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrecoEmolumentoDTO)) {
            return false;
        }

        PrecoEmolumentoDTO precoEmolumentoDTO = (PrecoEmolumentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, precoEmolumentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrecoEmolumentoDTO{" +
            "id=" + getId() +
            ", preco=" + getPreco() +
            ", isEspecificoCurso='" + getIsEspecificoCurso() + "'" +
            ", isEspecificoAreaFormacao='" + getIsEspecificoAreaFormacao() + "'" +
            ", isEspecificoClasse='" + getIsEspecificoClasse() + "'" +
            ", isEspecificoTurno='" + getIsEspecificoTurno() + "'" +
            ", utilizador=" + getUtilizador() +
            ", emolumento=" + getEmolumento() +
            ", areaFormacao=" + getAreaFormacao() +
            ", curso=" + getCurso() +
            ", classe=" + getClasse() +
            ", turno=" + getTurno() +
            ", planoMulta=" + getPlanoMulta() +
            "}";
    }
}
