package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.DissertacaoFinalCurso} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DissertacaoFinalCursoDTO implements Serializable {

    private Long id;

    @NotNull
    private String numero;

    private ZonedDateTime timestamp;

    @NotNull
    private LocalDate data;

    @NotNull
    private String tema;

    @NotNull
    private String objectivoGeral;

    @Lob
    private String objectivosEspecificos;

    @Lob
    private String introducao;

    @Lob
    private String resumo;

    @Lob
    private String problema;

    @Lob
    private String resultado;

    @Lob
    private String metodologia;

    @Lob
    private String referenciasBibliograficas;

    @Lob
    private String observacaoOrientador;

    @Lob
    private String observacaoAreaFormacao;

    @Lob
    private String observacaoInstituicao;

    private String hash;

    @Lob
    private String termosCompromissos;

    private Boolean isAceiteTermosCompromisso;

    private UserDTO utilizador;

    private TurmaDTO turma;

    private DocenteDTO orientador;

    private AreaFormacaoDTO especialidade;

    private DiscenteDTO discente;

    private EstadoDissertacaoDTO estado;

    private NaturezaTrabalhoDTO natureza;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getObjectivoGeral() {
        return objectivoGeral;
    }

    public void setObjectivoGeral(String objectivoGeral) {
        this.objectivoGeral = objectivoGeral;
    }

    public String getObjectivosEspecificos() {
        return objectivosEspecificos;
    }

    public void setObjectivosEspecificos(String objectivosEspecificos) {
        this.objectivosEspecificos = objectivosEspecificos;
    }

    public String getIntroducao() {
        return introducao;
    }

    public void setIntroducao(String introducao) {
        this.introducao = introducao;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public String getReferenciasBibliograficas() {
        return referenciasBibliograficas;
    }

    public void setReferenciasBibliograficas(String referenciasBibliograficas) {
        this.referenciasBibliograficas = referenciasBibliograficas;
    }

    public String getObservacaoOrientador() {
        return observacaoOrientador;
    }

    public void setObservacaoOrientador(String observacaoOrientador) {
        this.observacaoOrientador = observacaoOrientador;
    }

    public String getObservacaoAreaFormacao() {
        return observacaoAreaFormacao;
    }

    public void setObservacaoAreaFormacao(String observacaoAreaFormacao) {
        this.observacaoAreaFormacao = observacaoAreaFormacao;
    }

    public String getObservacaoInstituicao() {
        return observacaoInstituicao;
    }

    public void setObservacaoInstituicao(String observacaoInstituicao) {
        this.observacaoInstituicao = observacaoInstituicao;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTermosCompromissos() {
        return termosCompromissos;
    }

    public void setTermosCompromissos(String termosCompromissos) {
        this.termosCompromissos = termosCompromissos;
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

    public TurmaDTO getTurma() {
        return turma;
    }

    public void setTurma(TurmaDTO turma) {
        this.turma = turma;
    }

    public DocenteDTO getOrientador() {
        return orientador;
    }

    public void setOrientador(DocenteDTO orientador) {
        this.orientador = orientador;
    }

    public AreaFormacaoDTO getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(AreaFormacaoDTO especialidade) {
        this.especialidade = especialidade;
    }

    public DiscenteDTO getDiscente() {
        return discente;
    }

    public void setDiscente(DiscenteDTO discente) {
        this.discente = discente;
    }

    public EstadoDissertacaoDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoDissertacaoDTO estado) {
        this.estado = estado;
    }

    public NaturezaTrabalhoDTO getNatureza() {
        return natureza;
    }

    public void setNatureza(NaturezaTrabalhoDTO natureza) {
        this.natureza = natureza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DissertacaoFinalCursoDTO)) {
            return false;
        }

        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = (DissertacaoFinalCursoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dissertacaoFinalCursoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DissertacaoFinalCursoDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", data='" + getData() + "'" +
            ", tema='" + getTema() + "'" +
            ", objectivoGeral='" + getObjectivoGeral() + "'" +
            ", objectivosEspecificos='" + getObjectivosEspecificos() + "'" +
            ", introducao='" + getIntroducao() + "'" +
            ", resumo='" + getResumo() + "'" +
            ", problema='" + getProblema() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", metodologia='" + getMetodologia() + "'" +
            ", referenciasBibliograficas='" + getReferenciasBibliograficas() + "'" +
            ", observacaoOrientador='" + getObservacaoOrientador() + "'" +
            ", observacaoAreaFormacao='" + getObservacaoAreaFormacao() + "'" +
            ", observacaoInstituicao='" + getObservacaoInstituicao() + "'" +
            ", hash='" + getHash() + "'" +
            ", termosCompromissos='" + getTermosCompromissos() + "'" +
            ", isAceiteTermosCompromisso='" + getIsAceiteTermosCompromisso() + "'" +
            ", utilizador=" + getUtilizador() +
            ", turma=" + getTurma() +
            ", orientador=" + getOrientador() +
            ", especialidade=" + getEspecialidade() +
            ", discente=" + getDiscente() +
            ", estado=" + getEstado() +
            ", natureza=" + getNatureza() +
            "}";
    }
}
