package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.Sexo;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Docente} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocenteDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] fotografia;

    private String fotografiaContentType;

    @NotNull
    private String nome;

    @NotNull
    private LocalDate nascimento;

    private String nif;

    private String inss;

    @NotNull
    private Sexo sexo;

    @NotNull
    private String pai;

    @NotNull
    private String mae;

    @NotNull
    private String documentoNumero;

    @NotNull
    private LocalDate documentoEmissao;

    @NotNull
    private LocalDate documentoValidade;

    @NotNull
    private String residencia;

    @NotNull
    private LocalDate dataInicioFuncoes;

    @NotNull
    private String telefonePrincipal;

    private String telefoneParente;

    private String email;

    private String numeroAgente;

    private Boolean temAgregacaoPedagogica;

    @Lob
    private String observacao;

    private String hash;

    private ZonedDateTime timestamp;

    private LookupItemDTO nacionalidade;

    private LookupItemDTO naturalidade;

    private LookupItemDTO tipoDocumento;

    private LookupItemDTO grauAcademico;

    private LookupItemDTO categoriaProfissional;

    private LookupItemDTO unidadeOrganica;

    private LookupItemDTO estadoCivil;

    private ResponsavelTurnoDTO responsavelTurno;

    private ResponsavelAreaFormacaoDTO responsavelAreaFormacao;

    private ResponsavelCursoDTO responsavelCurso;

    private ResponsavelDisciplinaDTO responsavelDisciplina;

    private ResponsavelTurmaDTO responsavelTurma;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public String getFotografiaContentType() {
        return fotografiaContentType;
    }

    public void setFotografiaContentType(String fotografiaContentType) {
        this.fotografiaContentType = fotografiaContentType;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getInss() {
        return inss;
    }

    public void setInss(String inss) {
        this.inss = inss;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getDocumentoNumero() {
        return documentoNumero;
    }

    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    public LocalDate getDocumentoEmissao() {
        return documentoEmissao;
    }

    public void setDocumentoEmissao(LocalDate documentoEmissao) {
        this.documentoEmissao = documentoEmissao;
    }

    public LocalDate getDocumentoValidade() {
        return documentoValidade;
    }

    public void setDocumentoValidade(LocalDate documentoValidade) {
        this.documentoValidade = documentoValidade;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public LocalDate getDataInicioFuncoes() {
        return dataInicioFuncoes;
    }

    public void setDataInicioFuncoes(LocalDate dataInicioFuncoes) {
        this.dataInicioFuncoes = dataInicioFuncoes;
    }

    public String getTelefonePrincipal() {
        return telefonePrincipal;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneParente() {
        return telefoneParente;
    }

    public void setTelefoneParente(String telefoneParente) {
        this.telefoneParente = telefoneParente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroAgente() {
        return numeroAgente;
    }

    public void setNumeroAgente(String numeroAgente) {
        this.numeroAgente = numeroAgente;
    }

    public Boolean getTemAgregacaoPedagogica() {
        return temAgregacaoPedagogica;
    }

    public void setTemAgregacaoPedagogica(Boolean temAgregacaoPedagogica) {
        this.temAgregacaoPedagogica = temAgregacaoPedagogica;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    public LookupItemDTO getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(LookupItemDTO nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public LookupItemDTO getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(LookupItemDTO naturalidade) {
        this.naturalidade = naturalidade;
    }

    public LookupItemDTO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(LookupItemDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public LookupItemDTO getGrauAcademico() {
        return grauAcademico;
    }

    public void setGrauAcademico(LookupItemDTO grauAcademico) {
        this.grauAcademico = grauAcademico;
    }

    public LookupItemDTO getCategoriaProfissional() {
        return categoriaProfissional;
    }

    public void setCategoriaProfissional(LookupItemDTO categoriaProfissional) {
        this.categoriaProfissional = categoriaProfissional;
    }

    public LookupItemDTO getUnidadeOrganica() {
        return unidadeOrganica;
    }

    public void setUnidadeOrganica(LookupItemDTO unidadeOrganica) {
        this.unidadeOrganica = unidadeOrganica;
    }

    public LookupItemDTO getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(LookupItemDTO estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public ResponsavelTurnoDTO getResponsavelTurno() {
        return responsavelTurno;
    }

    public void setResponsavelTurno(ResponsavelTurnoDTO responsavelTurno) {
        this.responsavelTurno = responsavelTurno;
    }

    public ResponsavelAreaFormacaoDTO getResponsavelAreaFormacao() {
        return responsavelAreaFormacao;
    }

    public void setResponsavelAreaFormacao(ResponsavelAreaFormacaoDTO responsavelAreaFormacao) {
        this.responsavelAreaFormacao = responsavelAreaFormacao;
    }

    public ResponsavelCursoDTO getResponsavelCurso() {
        return responsavelCurso;
    }

    public void setResponsavelCurso(ResponsavelCursoDTO responsavelCurso) {
        this.responsavelCurso = responsavelCurso;
    }

    public ResponsavelDisciplinaDTO getResponsavelDisciplina() {
        return responsavelDisciplina;
    }

    public void setResponsavelDisciplina(ResponsavelDisciplinaDTO responsavelDisciplina) {
        this.responsavelDisciplina = responsavelDisciplina;
    }

    public ResponsavelTurmaDTO getResponsavelTurma() {
        return responsavelTurma;
    }

    public void setResponsavelTurma(ResponsavelTurmaDTO responsavelTurma) {
        this.responsavelTurma = responsavelTurma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocenteDTO)) {
            return false;
        }

        DocenteDTO docenteDTO = (DocenteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, docenteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocenteDTO{" +
            "id=" + getId() +
            ", fotografia='" + getFotografia() + "'" +
            ", nome='" + getNome() + "'" +
            ", nascimento='" + getNascimento() + "'" +
            ", nif='" + getNif() + "'" +
            ", inss='" + getInss() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", pai='" + getPai() + "'" +
            ", mae='" + getMae() + "'" +
            ", documentoNumero='" + getDocumentoNumero() + "'" +
            ", documentoEmissao='" + getDocumentoEmissao() + "'" +
            ", documentoValidade='" + getDocumentoValidade() + "'" +
            ", residencia='" + getResidencia() + "'" +
            ", dataInicioFuncoes='" + getDataInicioFuncoes() + "'" +
            ", telefonePrincipal='" + getTelefonePrincipal() + "'" +
            ", telefoneParente='" + getTelefoneParente() + "'" +
            ", email='" + getEmail() + "'" +
            ", numeroAgente='" + getNumeroAgente() + "'" +
            ", temAgregacaoPedagogica='" + getTemAgregacaoPedagogica() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", hash='" + getHash() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", nacionalidade=" + getNacionalidade() +
            ", naturalidade=" + getNaturalidade() +
            ", tipoDocumento=" + getTipoDocumento() +
            ", grauAcademico=" + getGrauAcademico() +
            ", categoriaProfissional=" + getCategoriaProfissional() +
            ", unidadeOrganica=" + getUnidadeOrganica() +
            ", estadoCivil=" + getEstadoCivil() +
            ", responsavelTurno=" + getResponsavelTurno() +
            ", responsavelAreaFormacao=" + getResponsavelAreaFormacao() +
            ", responsavelCurso=" + getResponsavelCurso() +
            ", responsavelDisciplina=" + getResponsavelDisciplina() +
            ", responsavelTurma=" + getResponsavelTurma() +
            "}";
    }
}
