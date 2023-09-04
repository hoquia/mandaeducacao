package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.EstadoAcademico;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Matricula} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatriculaDTO implements Serializable {

    private Long id;

    private String chaveComposta1;

    private String chaveComposta2;

    @NotNull
    private String numeroMatricula;

    @Min(value = 0)
    private Integer numeroChamada;

    @NotNull
    private EstadoAcademico estado;

    private ZonedDateTime timestamp;

    @Lob
    private String descricao;

    @Lob
    private byte[] termosCompromissos;

    private String termosCompromissosContentType;
    private Boolean isAceiteTermosCompromisso;

    private UserDTO utilizador;

    private Set<PlanoDescontoDTO> categoriasMatriculas = new HashSet<>();

    private TurmaDTO turma;

    private EncarregadoEducacaoDTO responsavelFinanceiro;

    private DiscenteDTO discente;

    private MatriculaDTO referencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChaveComposta1() {
        return chaveComposta1;
    }

    public void setChaveComposta1(String chaveComposta1) {
        this.chaveComposta1 = chaveComposta1;
    }

    public String getChaveComposta2() {
        return chaveComposta2;
    }

    public void setChaveComposta2(String chaveComposta2) {
        this.chaveComposta2 = chaveComposta2;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public Integer getNumeroChamada() {
        return numeroChamada;
    }

    public void setNumeroChamada(Integer numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public EstadoAcademico getEstado() {
        return estado;
    }

    public void setEstado(EstadoAcademico estado) {
        this.estado = estado;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getTermosCompromissos() {
        return termosCompromissos;
    }

    public void setTermosCompromissos(byte[] termosCompromissos) {
        this.termosCompromissos = termosCompromissos;
    }

    public String getTermosCompromissosContentType() {
        return termosCompromissosContentType;
    }

    public void setTermosCompromissosContentType(String termosCompromissosContentType) {
        this.termosCompromissosContentType = termosCompromissosContentType;
    }

    public Boolean getIsAceiteTermosCompromisso() {
        return isAceiteTermosCompromisso;
    }

    public void setIsAceiteTermosCompromisso(Boolean isAceiteTermosCompromisso) {
        this.isAceiteTermosCompromisso = isAceiteTermosCompromisso;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    public Set<PlanoDescontoDTO> getCategoriasMatriculas() {
        return categoriasMatriculas;
    }

    public void setCategoriasMatriculas(Set<PlanoDescontoDTO> categoriasMatriculas) {
        this.categoriasMatriculas = categoriasMatriculas;
    }

    public TurmaDTO getTurma() {
        return turma;
    }

    public void setTurma(TurmaDTO turma) {
        this.turma = turma;
    }

    public EncarregadoEducacaoDTO getResponsavelFinanceiro() {
        return responsavelFinanceiro;
    }

    public void setResponsavelFinanceiro(EncarregadoEducacaoDTO responsavelFinanceiro) {
        this.responsavelFinanceiro = responsavelFinanceiro;
    }

    public DiscenteDTO getDiscente() {
        return discente;
    }

    public void setDiscente(DiscenteDTO discente) {
        this.discente = discente;
    }

    public MatriculaDTO getReferencia() {
        return referencia;
    }

    public void setReferencia(MatriculaDTO referencia) {
        this.referencia = referencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatriculaDTO)) {
            return false;
        }

        MatriculaDTO matriculaDTO = (MatriculaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matriculaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatriculaDTO{" +
            "id=" + getId() +
            ", chaveComposta1='" + getChaveComposta1() + "'" +
            ", chaveComposta2='" + getChaveComposta2() + "'" +
            ", numeroMatricula='" + getNumeroMatricula() + "'" +
            ", numeroChamada=" + getNumeroChamada() +
            ", estado='" + getEstado() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", termosCompromissos='" + getTermosCompromissos() + "'" +
            ", isAceiteTermosCompromisso='" + getIsAceiteTermosCompromisso() + "'" +
            ", utilizador=" + getUtilizador() +
            ", categoriasMatriculas=" + getCategoriasMatriculas() +
            ", turma=" + getTurma() +
            ", responsavelFinanceiro=" + getResponsavelFinanceiro() +
            ", discente=" + getDiscente() +
            ", referencia=" + getReferencia() +
            "}";
    }
}
