package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.Sexo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EncarregadoEducacao.
 */
@Entity
@Table(name = "encarregado_educacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EncarregadoEducacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "fotografia")
    private byte[] fotografia;

    @Column(name = "fotografia_content_type")
    private String fotografiaContentType;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "nascimento", nullable = false)
    private LocalDate nascimento;

    @Column(name = "nif", unique = true)
    private String nif;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    @NotNull
    @Column(name = "documento_numero", nullable = false, unique = true)
    private String documentoNumero;

    @NotNull
    @Column(name = "telefone_principal", nullable = false, unique = true)
    private String telefonePrincipal;

    @Column(name = "telefone_alternativo")
    private String telefoneAlternativo;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "residencia")
    private String residencia;

    @Column(name = "endereco_trabalho")
    private String enderecoTrabalho;

    @DecimalMin(value = "0")
    @Column(name = "renda_mensal", precision = 21, scale = 2)
    private BigDecimal rendaMensal;

    @Column(name = "empresa_trabalho")
    private String empresaTrabalho;

    @Column(name = "hash")
    private String hash;

    @OneToMany(mappedBy = "encarregadoEducacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "enderecos",
            "processosSelectivos",
            "anexoDiscentes",
            "matriculas",
            "resumoAcademicos",
            "historicosSaudes",
            "dissertacaoFinalCursos",
            "nacionalidade",
            "naturalidade",
            "tipoDocumento",
            "profissao",
            "grupoSanguinio",
            "necessidadeEspecial",
            "encarregadoEducacao",
        },
        allowSetters = true
    )
    private Set<Discente> discentes = new HashSet<>();

    @OneToMany(mappedBy = "responsavelFinanceiro")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "matriculas",
            "facturas",
            "transacoes",
            "recibos",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "transferenciaTurmas",
            "ocorrencias",
            "utilizador",
            "categoriasMatriculas",
            "turma",
            "responsavelFinanceiro",
            "discente",
            "referencia",
        },
        allowSetters = true
    )
    private Set<Matricula> matriculas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem grauParentesco;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem tipoDocumento;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem profissao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EncarregadoEducacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFotografia() {
        return this.fotografia;
    }

    public EncarregadoEducacao fotografia(byte[] fotografia) {
        this.setFotografia(fotografia);
        return this;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public String getFotografiaContentType() {
        return this.fotografiaContentType;
    }

    public EncarregadoEducacao fotografiaContentType(String fotografiaContentType) {
        this.fotografiaContentType = fotografiaContentType;
        return this;
    }

    public void setFotografiaContentType(String fotografiaContentType) {
        this.fotografiaContentType = fotografiaContentType;
    }

    public String getNome() {
        return this.nome;
    }

    public EncarregadoEducacao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return this.nascimento;
    }

    public EncarregadoEducacao nascimento(LocalDate nascimento) {
        this.setNascimento(nascimento);
        return this;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getNif() {
        return this.nif;
    }

    public EncarregadoEducacao nif(String nif) {
        this.setNif(nif);
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Sexo getSexo() {
        return this.sexo;
    }

    public EncarregadoEducacao sexo(Sexo sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getDocumentoNumero() {
        return this.documentoNumero;
    }

    public EncarregadoEducacao documentoNumero(String documentoNumero) {
        this.setDocumentoNumero(documentoNumero);
        return this;
    }

    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    public String getTelefonePrincipal() {
        return this.telefonePrincipal;
    }

    public EncarregadoEducacao telefonePrincipal(String telefonePrincipal) {
        this.setTelefonePrincipal(telefonePrincipal);
        return this;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneAlternativo() {
        return this.telefoneAlternativo;
    }

    public EncarregadoEducacao telefoneAlternativo(String telefoneAlternativo) {
        this.setTelefoneAlternativo(telefoneAlternativo);
        return this;
    }

    public void setTelefoneAlternativo(String telefoneAlternativo) {
        this.telefoneAlternativo = telefoneAlternativo;
    }

    public String getEmail() {
        return this.email;
    }

    public EncarregadoEducacao email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResidencia() {
        return this.residencia;
    }

    public EncarregadoEducacao residencia(String residencia) {
        this.setResidencia(residencia);
        return this;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getEnderecoTrabalho() {
        return this.enderecoTrabalho;
    }

    public EncarregadoEducacao enderecoTrabalho(String enderecoTrabalho) {
        this.setEnderecoTrabalho(enderecoTrabalho);
        return this;
    }

    public void setEnderecoTrabalho(String enderecoTrabalho) {
        this.enderecoTrabalho = enderecoTrabalho;
    }

    public BigDecimal getRendaMensal() {
        return this.rendaMensal;
    }

    public EncarregadoEducacao rendaMensal(BigDecimal rendaMensal) {
        this.setRendaMensal(rendaMensal);
        return this;
    }

    public void setRendaMensal(BigDecimal rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public String getEmpresaTrabalho() {
        return this.empresaTrabalho;
    }

    public EncarregadoEducacao empresaTrabalho(String empresaTrabalho) {
        this.setEmpresaTrabalho(empresaTrabalho);
        return this;
    }

    public void setEmpresaTrabalho(String empresaTrabalho) {
        this.empresaTrabalho = empresaTrabalho;
    }

    public String getHash() {
        return this.hash;
    }

    public EncarregadoEducacao hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Set<Discente> getDiscentes() {
        return this.discentes;
    }

    public void setDiscentes(Set<Discente> discentes) {
        if (this.discentes != null) {
            this.discentes.forEach(i -> i.setEncarregadoEducacao(null));
        }
        if (discentes != null) {
            discentes.forEach(i -> i.setEncarregadoEducacao(this));
        }
        this.discentes = discentes;
    }

    public EncarregadoEducacao discentes(Set<Discente> discentes) {
        this.setDiscentes(discentes);
        return this;
    }

    public EncarregadoEducacao addDiscentes(Discente discente) {
        this.discentes.add(discente);
        discente.setEncarregadoEducacao(this);
        return this;
    }

    public EncarregadoEducacao removeDiscentes(Discente discente) {
        this.discentes.remove(discente);
        discente.setEncarregadoEducacao(null);
        return this;
    }

    public Set<Matricula> getMatriculas() {
        return this.matriculas;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        if (this.matriculas != null) {
            this.matriculas.forEach(i -> i.setResponsavelFinanceiro(null));
        }
        if (matriculas != null) {
            matriculas.forEach(i -> i.setResponsavelFinanceiro(this));
        }
        this.matriculas = matriculas;
    }

    public EncarregadoEducacao matriculas(Set<Matricula> matriculas) {
        this.setMatriculas(matriculas);
        return this;
    }

    public EncarregadoEducacao addMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setResponsavelFinanceiro(this);
        return this;
    }

    public EncarregadoEducacao removeMatricula(Matricula matricula) {
        this.matriculas.remove(matricula);
        matricula.setResponsavelFinanceiro(null);
        return this;
    }

    public LookupItem getGrauParentesco() {
        return this.grauParentesco;
    }

    public void setGrauParentesco(LookupItem lookupItem) {
        this.grauParentesco = lookupItem;
    }

    public EncarregadoEducacao grauParentesco(LookupItem lookupItem) {
        this.setGrauParentesco(lookupItem);
        return this;
    }

    public LookupItem getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(LookupItem lookupItem) {
        this.tipoDocumento = lookupItem;
    }

    public EncarregadoEducacao tipoDocumento(LookupItem lookupItem) {
        this.setTipoDocumento(lookupItem);
        return this;
    }

    public LookupItem getProfissao() {
        return this.profissao;
    }

    public void setProfissao(LookupItem lookupItem) {
        this.profissao = lookupItem;
    }

    public EncarregadoEducacao profissao(LookupItem lookupItem) {
        this.setProfissao(lookupItem);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EncarregadoEducacao)) {
            return false;
        }
        return id != null && id.equals(((EncarregadoEducacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EncarregadoEducacao{" +
            "id=" + getId() +
            ", fotografia='" + getFotografia() + "'" +
            ", fotografiaContentType='" + getFotografiaContentType() + "'" +
            ", nome='" + getNome() + "'" +
            ", nascimento='" + getNascimento() + "'" +
            ", nif='" + getNif() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", documentoNumero='" + getDocumentoNumero() + "'" +
            ", telefonePrincipal='" + getTelefonePrincipal() + "'" +
            ", telefoneAlternativo='" + getTelefoneAlternativo() + "'" +
            ", email='" + getEmail() + "'" +
            ", residencia='" + getResidencia() + "'" +
            ", enderecoTrabalho='" + getEnderecoTrabalho() + "'" +
            ", rendaMensal=" + getRendaMensal() +
            ", empresaTrabalho='" + getEmpresaTrabalho() + "'" +
            ", hash='" + getHash() + "'" +
            "}";
    }
}
