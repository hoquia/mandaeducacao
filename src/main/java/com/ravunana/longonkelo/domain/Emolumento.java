package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Emolumento.
 */
@Entity
@Table(name = "emolumento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Emolumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "preco", precision = 21, scale = 2, nullable = false)
    private BigDecimal preco;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "quantidade", nullable = false)
    private Double quantidade;

    @Min(value = 1)
    @Max(value = 12)
    @Column(name = "periodo")
    private Integer periodo;

    @Min(value = 1)
    @Max(value = 12)
    @Column(name = "inicio_periodo")
    private Integer inicioPeriodo;

    @Min(value = 1)
    @Max(value = 12)
    @Column(name = "fim_periodo")
    private Integer fimPeriodo;

    @Column(name = "is_obrigatorio_matricula")
    private Boolean isObrigatorioMatricula;

    @Column(name = "is_obrigatorio_confirmacao")
    private Boolean isObrigatorioConfirmacao;

    @OneToMany(mappedBy = "emolumento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "factura", "emolumento" }, allowSetters = true)
    private Set<ItemFactura> itemFacturas = new HashSet<>();

    @OneToMany(mappedBy = "referencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "itemFacturas", "emolumentos", "precosEmolumentos", "categoria", "imposto", "referencia", "planoMulta" },
        allowSetters = true
    )
    private Set<Emolumento> emolumentos = new HashSet<>();

    @OneToMany(mappedBy = "emolumento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "utilizador", "emolumento", "areaFormacao", "curso", "classe", "turno", "planoMulta" },
        allowSetters = true
    )
    private Set<PrecoEmolumento> precosEmolumentos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "emolumentos", "planoMulta", "planosDescontos" }, allowSetters = true)
    private CategoriaEmolumento categoria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "emolumentos", "tipoImposto", "codigoImposto", "motivoIsencaoCodigo", "motivoIsencaoDescricao" },
        allowSetters = true
    )
    private Imposto imposto;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "itemFacturas", "emolumentos", "precosEmolumentos", "categoria", "imposto", "referencia", "planoMulta" },
        allowSetters = true
    )
    private Emolumento referencia;

    @ManyToOne
    @JsonIgnoreProperties(value = { "categoriaEmolumentos", "emolumentos", "precoEmolumentos", "utilizador" }, allowSetters = true)
    private PlanoMulta planoMulta;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Emolumento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return this.imagem;
    }

    public Emolumento imagem(byte[] imagem) {
        this.setImagem(imagem);
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return this.imagemContentType;
    }

    public Emolumento imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public String getNumero() {
        return this.numero;
    }

    public Emolumento numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return this.nome;
    }

    public Emolumento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return this.preco;
    }

    public Emolumento preco(BigDecimal preco) {
        this.setPreco(preco);
        return this;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Double getQuantidade() {
        return this.quantidade;
    }

    public Emolumento quantidade(Double quantidade) {
        this.setQuantidade(quantidade);
        return this;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getPeriodo() {
        return this.periodo;
    }

    public Emolumento periodo(Integer periodo) {
        this.setPeriodo(periodo);
        return this;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getInicioPeriodo() {
        return this.inicioPeriodo;
    }

    public Emolumento inicioPeriodo(Integer inicioPeriodo) {
        this.setInicioPeriodo(inicioPeriodo);
        return this;
    }

    public void setInicioPeriodo(Integer inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    public Integer getFimPeriodo() {
        return this.fimPeriodo;
    }

    public Emolumento fimPeriodo(Integer fimPeriodo) {
        this.setFimPeriodo(fimPeriodo);
        return this;
    }

    public void setFimPeriodo(Integer fimPeriodo) {
        this.fimPeriodo = fimPeriodo;
    }

    public Boolean getIsObrigatorioMatricula() {
        return this.isObrigatorioMatricula;
    }

    public Emolumento isObrigatorioMatricula(Boolean isObrigatorioMatricula) {
        this.setIsObrigatorioMatricula(isObrigatorioMatricula);
        return this;
    }

    public void setIsObrigatorioMatricula(Boolean isObrigatorioMatricula) {
        this.isObrigatorioMatricula = isObrigatorioMatricula;
    }

    public Boolean getIsObrigatorioConfirmacao() {
        return this.isObrigatorioConfirmacao;
    }

    public Emolumento isObrigatorioConfirmacao(Boolean isObrigatorioConfirmacao) {
        this.setIsObrigatorioConfirmacao(isObrigatorioConfirmacao);
        return this;
    }

    public void setIsObrigatorioConfirmacao(Boolean isObrigatorioConfirmacao) {
        this.isObrigatorioConfirmacao = isObrigatorioConfirmacao;
    }

    public Set<ItemFactura> getItemFacturas() {
        return this.itemFacturas;
    }

    public void setItemFacturas(Set<ItemFactura> itemFacturas) {
        if (this.itemFacturas != null) {
            this.itemFacturas.forEach(i -> i.setEmolumento(null));
        }
        if (itemFacturas != null) {
            itemFacturas.forEach(i -> i.setEmolumento(this));
        }
        this.itemFacturas = itemFacturas;
    }

    public Emolumento itemFacturas(Set<ItemFactura> itemFacturas) {
        this.setItemFacturas(itemFacturas);
        return this;
    }

    public Emolumento addItemFactura(ItemFactura itemFactura) {
        this.itemFacturas.add(itemFactura);
        itemFactura.setEmolumento(this);
        return this;
    }

    public Emolumento removeItemFactura(ItemFactura itemFactura) {
        this.itemFacturas.remove(itemFactura);
        itemFactura.setEmolumento(null);
        return this;
    }

    public Set<Emolumento> getEmolumentos() {
        return this.emolumentos;
    }

    public void setEmolumentos(Set<Emolumento> emolumentos) {
        if (this.emolumentos != null) {
            this.emolumentos.forEach(i -> i.setReferencia(null));
        }
        if (emolumentos != null) {
            emolumentos.forEach(i -> i.setReferencia(this));
        }
        this.emolumentos = emolumentos;
    }

    public Emolumento emolumentos(Set<Emolumento> emolumentos) {
        this.setEmolumentos(emolumentos);
        return this;
    }

    public Emolumento addEmolumento(Emolumento emolumento) {
        this.emolumentos.add(emolumento);
        emolumento.setReferencia(this);
        return this;
    }

    public Emolumento removeEmolumento(Emolumento emolumento) {
        this.emolumentos.remove(emolumento);
        emolumento.setReferencia(null);
        return this;
    }

    public Set<PrecoEmolumento> getPrecosEmolumentos() {
        return this.precosEmolumentos;
    }

    public void setPrecosEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        if (this.precosEmolumentos != null) {
            this.precosEmolumentos.forEach(i -> i.setEmolumento(null));
        }
        if (precoEmolumentos != null) {
            precoEmolumentos.forEach(i -> i.setEmolumento(this));
        }
        this.precosEmolumentos = precoEmolumentos;
    }

    public Emolumento precosEmolumentos(Set<PrecoEmolumento> precoEmolumentos) {
        this.setPrecosEmolumentos(precoEmolumentos);
        return this;
    }

    public Emolumento addPrecosEmolumento(PrecoEmolumento precoEmolumento) {
        this.precosEmolumentos.add(precoEmolumento);
        precoEmolumento.setEmolumento(this);
        return this;
    }

    public Emolumento removePrecosEmolumento(PrecoEmolumento precoEmolumento) {
        this.precosEmolumentos.remove(precoEmolumento);
        precoEmolumento.setEmolumento(null);
        return this;
    }

    public CategoriaEmolumento getCategoria() {
        return this.categoria;
    }

    public void setCategoria(CategoriaEmolumento categoriaEmolumento) {
        this.categoria = categoriaEmolumento;
    }

    public Emolumento categoria(CategoriaEmolumento categoriaEmolumento) {
        this.setCategoria(categoriaEmolumento);
        return this;
    }

    public Imposto getImposto() {
        return this.imposto;
    }

    public void setImposto(Imposto imposto) {
        this.imposto = imposto;
    }

    public Emolumento imposto(Imposto imposto) {
        this.setImposto(imposto);
        return this;
    }

    public Emolumento getReferencia() {
        return this.referencia;
    }

    public void setReferencia(Emolumento emolumento) {
        this.referencia = emolumento;
    }

    public Emolumento referencia(Emolumento emolumento) {
        this.setReferencia(emolumento);
        return this;
    }

    public PlanoMulta getPlanoMulta() {
        return this.planoMulta;
    }

    public void setPlanoMulta(PlanoMulta planoMulta) {
        this.planoMulta = planoMulta;
    }

    public Emolumento planoMulta(PlanoMulta planoMulta) {
        this.setPlanoMulta(planoMulta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emolumento)) {
            return false;
        }
        return id != null && id.equals(((Emolumento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Emolumento{" +
            "id=" + getId() +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", numero='" + getNumero() + "'" +
            ", nome='" + getNome() + "'" +
            ", preco=" + getPreco() +
            ", quantidade=" + getQuantidade() +
            ", periodo=" + getPeriodo() +
            ", inicioPeriodo=" + getInicioPeriodo() +
            ", fimPeriodo=" + getFimPeriodo() +
            ", isObrigatorioMatricula='" + getIsObrigatorioMatricula() + "'" +
            ", isObrigatorioConfirmacao='" + getIsObrigatorioConfirmacao() + "'" +
            "}";
    }
}
