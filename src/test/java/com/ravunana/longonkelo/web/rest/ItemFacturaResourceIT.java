package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Emolumento;
import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.domain.ItemFactura;
import com.ravunana.longonkelo.domain.enumeration.EstadoItemFactura;
import com.ravunana.longonkelo.repository.ItemFacturaRepository;
import com.ravunana.longonkelo.service.ItemFacturaService;
import com.ravunana.longonkelo.service.criteria.ItemFacturaCriteria;
import com.ravunana.longonkelo.service.dto.ItemFacturaDTO;
import com.ravunana.longonkelo.service.mapper.ItemFacturaMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ItemFacturaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ItemFacturaResourceIT {

    private static final Double DEFAULT_QUANTIDADE = 0D;
    private static final Double UPDATED_QUANTIDADE = 1D;
    private static final Double SMALLER_QUANTIDADE = 0D - 1D;

    private static final BigDecimal DEFAULT_PRECO_UNITARIO = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRECO_UNITARIO = new BigDecimal(1);
    private static final BigDecimal SMALLER_PRECO_UNITARIO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_DESCONTO = new BigDecimal(0);
    private static final BigDecimal UPDATED_DESCONTO = new BigDecimal(1);
    private static final BigDecimal SMALLER_DESCONTO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_MULTA = new BigDecimal(0);
    private static final BigDecimal UPDATED_MULTA = new BigDecimal(1);
    private static final BigDecimal SMALLER_MULTA = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_JURO = new BigDecimal(0);
    private static final BigDecimal UPDATED_JURO = new BigDecimal(1);
    private static final BigDecimal SMALLER_JURO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_PRECO_TOTAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRECO_TOTAL = new BigDecimal(1);
    private static final BigDecimal SMALLER_PRECO_TOTAL = new BigDecimal(0 - 1);

    private static final EstadoItemFactura DEFAULT_ESTADO = EstadoItemFactura.PENDENTE;
    private static final EstadoItemFactura UPDATED_ESTADO = EstadoItemFactura.PAGO;

    private static final String DEFAULT_TAX_TYPE = "AAA";
    private static final String UPDATED_TAX_TYPE = "BBB";

    private static final String DEFAULT_TAX_COUNTRY_REGION = "AAAAAA";
    private static final String UPDATED_TAX_COUNTRY_REGION = "BBBBBB";

    private static final String DEFAULT_TAX_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_TAX_PERCENTAGE = 0D;
    private static final Double UPDATED_TAX_PERCENTAGE = 1D;
    private static final Double SMALLER_TAX_PERCENTAGE = 0D - 1D;

    private static final String DEFAULT_TAX_EXEMPTION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_TAX_EXEMPTION_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_EXEMPTION_CODE = "AAA";
    private static final String UPDATED_TAX_EXEMPTION_CODE = "BBB";

    private static final LocalDate DEFAULT_EMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMISSAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EMISSAO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_EXPIRACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRACAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EXPIRACAO = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_PERIODO = 1;
    private static final Integer UPDATED_PERIODO = 2;
    private static final Integer SMALLER_PERIODO = 1 - 1;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/item-facturas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemFacturaRepository itemFacturaRepository;

    @Mock
    private ItemFacturaRepository itemFacturaRepositoryMock;

    @Autowired
    private ItemFacturaMapper itemFacturaMapper;

    @Mock
    private ItemFacturaService itemFacturaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemFacturaMockMvc;

    private ItemFactura itemFactura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemFactura createEntity(EntityManager em) {
        ItemFactura itemFactura = new ItemFactura()
            .quantidade(DEFAULT_QUANTIDADE)
            .precoUnitario(DEFAULT_PRECO_UNITARIO)
            .desconto(DEFAULT_DESCONTO)
            .multa(DEFAULT_MULTA)
            .juro(DEFAULT_JURO)
            .precoTotal(DEFAULT_PRECO_TOTAL)
            .estado(DEFAULT_ESTADO)
            .taxType(DEFAULT_TAX_TYPE)
            .taxCountryRegion(DEFAULT_TAX_COUNTRY_REGION)
            .taxCode(DEFAULT_TAX_CODE)
            .taxPercentage(DEFAULT_TAX_PERCENTAGE)
            .taxExemptionReason(DEFAULT_TAX_EXEMPTION_REASON)
            .taxExemptionCode(DEFAULT_TAX_EXEMPTION_CODE)
            .emissao(DEFAULT_EMISSAO)
            .expiracao(DEFAULT_EXPIRACAO)
            .periodo(DEFAULT_PERIODO)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            factura = FacturaResourceIT.createEntity(em);
            em.persist(factura);
            em.flush();
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        itemFactura.setFactura(factura);
        // Add required entity
        Emolumento emolumento;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            emolumento = EmolumentoResourceIT.createEntity(em);
            em.persist(emolumento);
            em.flush();
        } else {
            emolumento = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        itemFactura.setEmolumento(emolumento);
        return itemFactura;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemFactura createUpdatedEntity(EntityManager em) {
        ItemFactura itemFactura = new ItemFactura()
            .quantidade(UPDATED_QUANTIDADE)
            .precoUnitario(UPDATED_PRECO_UNITARIO)
            .desconto(UPDATED_DESCONTO)
            .multa(UPDATED_MULTA)
            .juro(UPDATED_JURO)
            .precoTotal(UPDATED_PRECO_TOTAL)
            .estado(UPDATED_ESTADO)
            .taxType(UPDATED_TAX_TYPE)
            .taxCountryRegion(UPDATED_TAX_COUNTRY_REGION)
            .taxCode(UPDATED_TAX_CODE)
            .taxPercentage(UPDATED_TAX_PERCENTAGE)
            .taxExemptionReason(UPDATED_TAX_EXEMPTION_REASON)
            .taxExemptionCode(UPDATED_TAX_EXEMPTION_CODE)
            .emissao(UPDATED_EMISSAO)
            .expiracao(UPDATED_EXPIRACAO)
            .periodo(UPDATED_PERIODO)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            factura = FacturaResourceIT.createUpdatedEntity(em);
            em.persist(factura);
            em.flush();
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        itemFactura.setFactura(factura);
        // Add required entity
        Emolumento emolumento;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            emolumento = EmolumentoResourceIT.createUpdatedEntity(em);
            em.persist(emolumento);
            em.flush();
        } else {
            emolumento = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        itemFactura.setEmolumento(emolumento);
        return itemFactura;
    }

    @BeforeEach
    public void initTest() {
        itemFactura = createEntity(em);
    }

    @Test
    @Transactional
    void createItemFactura() throws Exception {
        int databaseSizeBeforeCreate = itemFacturaRepository.findAll().size();
        // Create the ItemFactura
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);
        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ItemFactura in the database
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeCreate + 1);
        ItemFactura testItemFactura = itemFacturaList.get(itemFacturaList.size() - 1);
        assertThat(testItemFactura.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testItemFactura.getPrecoUnitario()).isEqualByComparingTo(DEFAULT_PRECO_UNITARIO);
        assertThat(testItemFactura.getDesconto()).isEqualByComparingTo(DEFAULT_DESCONTO);
        assertThat(testItemFactura.getMulta()).isEqualByComparingTo(DEFAULT_MULTA);
        assertThat(testItemFactura.getJuro()).isEqualByComparingTo(DEFAULT_JURO);
        assertThat(testItemFactura.getPrecoTotal()).isEqualByComparingTo(DEFAULT_PRECO_TOTAL);
        assertThat(testItemFactura.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testItemFactura.getTaxType()).isEqualTo(DEFAULT_TAX_TYPE);
        assertThat(testItemFactura.getTaxCountryRegion()).isEqualTo(DEFAULT_TAX_COUNTRY_REGION);
        assertThat(testItemFactura.getTaxCode()).isEqualTo(DEFAULT_TAX_CODE);
        assertThat(testItemFactura.getTaxPercentage()).isEqualTo(DEFAULT_TAX_PERCENTAGE);
        assertThat(testItemFactura.getTaxExemptionReason()).isEqualTo(DEFAULT_TAX_EXEMPTION_REASON);
        assertThat(testItemFactura.getTaxExemptionCode()).isEqualTo(DEFAULT_TAX_EXEMPTION_CODE);
        assertThat(testItemFactura.getEmissao()).isEqualTo(DEFAULT_EMISSAO);
        assertThat(testItemFactura.getExpiracao()).isEqualTo(DEFAULT_EXPIRACAO);
        assertThat(testItemFactura.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testItemFactura.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createItemFacturaWithExistingId() throws Exception {
        // Create the ItemFactura with an existing ID
        itemFactura.setId(1L);
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        int databaseSizeBeforeCreate = itemFacturaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemFactura in the database
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFacturaRepository.findAll().size();
        // set the field null
        itemFactura.setQuantidade(null);

        // Create the ItemFactura, which fails.
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecoUnitarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFacturaRepository.findAll().size();
        // set the field null
        itemFactura.setPrecoUnitario(null);

        // Create the ItemFactura, which fails.
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescontoIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFacturaRepository.findAll().size();
        // set the field null
        itemFactura.setDesconto(null);

        // Create the ItemFactura, which fails.
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMultaIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFacturaRepository.findAll().size();
        // set the field null
        itemFactura.setMulta(null);

        // Create the ItemFactura, which fails.
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJuroIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFacturaRepository.findAll().size();
        // set the field null
        itemFactura.setJuro(null);

        // Create the ItemFactura, which fails.
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecoTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFacturaRepository.findAll().size();
        // set the field null
        itemFactura.setPrecoTotal(null);

        // Create the ItemFactura, which fails.
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFacturaRepository.findAll().size();
        // set the field null
        itemFactura.setEstado(null);

        // Create the ItemFactura, which fails.
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFacturaRepository.findAll().size();
        // set the field null
        itemFactura.setTaxType(null);

        // Create the ItemFactura, which fails.
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxCountryRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFacturaRepository.findAll().size();
        // set the field null
        itemFactura.setTaxCountryRegion(null);

        // Create the ItemFactura, which fails.
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemFacturaRepository.findAll().size();
        // set the field null
        itemFactura.setTaxCode(null);

        // Create the ItemFactura, which fails.
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        restItemFacturaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllItemFacturas() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList
        restItemFacturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemFactura.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].precoUnitario").value(hasItem(sameNumber(DEFAULT_PRECO_UNITARIO))))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(sameNumber(DEFAULT_DESCONTO))))
            .andExpect(jsonPath("$.[*].multa").value(hasItem(sameNumber(DEFAULT_MULTA))))
            .andExpect(jsonPath("$.[*].juro").value(hasItem(sameNumber(DEFAULT_JURO))))
            .andExpect(jsonPath("$.[*].precoTotal").value(hasItem(sameNumber(DEFAULT_PRECO_TOTAL))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].taxType").value(hasItem(DEFAULT_TAX_TYPE)))
            .andExpect(jsonPath("$.[*].taxCountryRegion").value(hasItem(DEFAULT_TAX_COUNTRY_REGION)))
            .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE)))
            .andExpect(jsonPath("$.[*].taxPercentage").value(hasItem(DEFAULT_TAX_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].taxExemptionReason").value(hasItem(DEFAULT_TAX_EXEMPTION_REASON)))
            .andExpect(jsonPath("$.[*].taxExemptionCode").value(hasItem(DEFAULT_TAX_EXEMPTION_CODE)))
            .andExpect(jsonPath("$.[*].emissao").value(hasItem(DEFAULT_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].expiracao").value(hasItem(DEFAULT_EXPIRACAO.toString())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllItemFacturasWithEagerRelationshipsIsEnabled() throws Exception {
        when(itemFacturaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restItemFacturaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(itemFacturaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllItemFacturasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(itemFacturaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restItemFacturaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(itemFacturaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getItemFactura() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get the itemFactura
        restItemFacturaMockMvc
            .perform(get(ENTITY_API_URL_ID, itemFactura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemFactura.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.doubleValue()))
            .andExpect(jsonPath("$.precoUnitario").value(sameNumber(DEFAULT_PRECO_UNITARIO)))
            .andExpect(jsonPath("$.desconto").value(sameNumber(DEFAULT_DESCONTO)))
            .andExpect(jsonPath("$.multa").value(sameNumber(DEFAULT_MULTA)))
            .andExpect(jsonPath("$.juro").value(sameNumber(DEFAULT_JURO)))
            .andExpect(jsonPath("$.precoTotal").value(sameNumber(DEFAULT_PRECO_TOTAL)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.taxType").value(DEFAULT_TAX_TYPE))
            .andExpect(jsonPath("$.taxCountryRegion").value(DEFAULT_TAX_COUNTRY_REGION))
            .andExpect(jsonPath("$.taxCode").value(DEFAULT_TAX_CODE))
            .andExpect(jsonPath("$.taxPercentage").value(DEFAULT_TAX_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.taxExemptionReason").value(DEFAULT_TAX_EXEMPTION_REASON))
            .andExpect(jsonPath("$.taxExemptionCode").value(DEFAULT_TAX_EXEMPTION_CODE))
            .andExpect(jsonPath("$.emissao").value(DEFAULT_EMISSAO.toString()))
            .andExpect(jsonPath("$.expiracao").value(DEFAULT_EXPIRACAO.toString()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getItemFacturasByIdFiltering() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        Long id = itemFactura.getId();

        defaultItemFacturaShouldBeFound("id.equals=" + id);
        defaultItemFacturaShouldNotBeFound("id.notEquals=" + id);

        defaultItemFacturaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemFacturaShouldNotBeFound("id.greaterThan=" + id);

        defaultItemFacturaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemFacturaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllItemFacturasByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where quantidade equals to DEFAULT_QUANTIDADE
        defaultItemFacturaShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the itemFacturaList where quantidade equals to UPDATED_QUANTIDADE
        defaultItemFacturaShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultItemFacturaShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the itemFacturaList where quantidade equals to UPDATED_QUANTIDADE
        defaultItemFacturaShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where quantidade is not null
        defaultItemFacturaShouldBeFound("quantidade.specified=true");

        // Get all the itemFacturaList where quantidade is null
        defaultItemFacturaShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultItemFacturaShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the itemFacturaList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultItemFacturaShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultItemFacturaShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the itemFacturaList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultItemFacturaShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where quantidade is less than DEFAULT_QUANTIDADE
        defaultItemFacturaShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the itemFacturaList where quantidade is less than UPDATED_QUANTIDADE
        defaultItemFacturaShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultItemFacturaShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the itemFacturaList where quantidade is greater than SMALLER_QUANTIDADE
        defaultItemFacturaShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoUnitarioIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoUnitario equals to DEFAULT_PRECO_UNITARIO
        defaultItemFacturaShouldBeFound("precoUnitario.equals=" + DEFAULT_PRECO_UNITARIO);

        // Get all the itemFacturaList where precoUnitario equals to UPDATED_PRECO_UNITARIO
        defaultItemFacturaShouldNotBeFound("precoUnitario.equals=" + UPDATED_PRECO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoUnitarioIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoUnitario in DEFAULT_PRECO_UNITARIO or UPDATED_PRECO_UNITARIO
        defaultItemFacturaShouldBeFound("precoUnitario.in=" + DEFAULT_PRECO_UNITARIO + "," + UPDATED_PRECO_UNITARIO);

        // Get all the itemFacturaList where precoUnitario equals to UPDATED_PRECO_UNITARIO
        defaultItemFacturaShouldNotBeFound("precoUnitario.in=" + UPDATED_PRECO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoUnitarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoUnitario is not null
        defaultItemFacturaShouldBeFound("precoUnitario.specified=true");

        // Get all the itemFacturaList where precoUnitario is null
        defaultItemFacturaShouldNotBeFound("precoUnitario.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoUnitarioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoUnitario is greater than or equal to DEFAULT_PRECO_UNITARIO
        defaultItemFacturaShouldBeFound("precoUnitario.greaterThanOrEqual=" + DEFAULT_PRECO_UNITARIO);

        // Get all the itemFacturaList where precoUnitario is greater than or equal to UPDATED_PRECO_UNITARIO
        defaultItemFacturaShouldNotBeFound("precoUnitario.greaterThanOrEqual=" + UPDATED_PRECO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoUnitarioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoUnitario is less than or equal to DEFAULT_PRECO_UNITARIO
        defaultItemFacturaShouldBeFound("precoUnitario.lessThanOrEqual=" + DEFAULT_PRECO_UNITARIO);

        // Get all the itemFacturaList where precoUnitario is less than or equal to SMALLER_PRECO_UNITARIO
        defaultItemFacturaShouldNotBeFound("precoUnitario.lessThanOrEqual=" + SMALLER_PRECO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoUnitarioIsLessThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoUnitario is less than DEFAULT_PRECO_UNITARIO
        defaultItemFacturaShouldNotBeFound("precoUnitario.lessThan=" + DEFAULT_PRECO_UNITARIO);

        // Get all the itemFacturaList where precoUnitario is less than UPDATED_PRECO_UNITARIO
        defaultItemFacturaShouldBeFound("precoUnitario.lessThan=" + UPDATED_PRECO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoUnitarioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoUnitario is greater than DEFAULT_PRECO_UNITARIO
        defaultItemFacturaShouldNotBeFound("precoUnitario.greaterThan=" + DEFAULT_PRECO_UNITARIO);

        // Get all the itemFacturaList where precoUnitario is greater than SMALLER_PRECO_UNITARIO
        defaultItemFacturaShouldBeFound("precoUnitario.greaterThan=" + SMALLER_PRECO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where desconto equals to DEFAULT_DESCONTO
        defaultItemFacturaShouldBeFound("desconto.equals=" + DEFAULT_DESCONTO);

        // Get all the itemFacturaList where desconto equals to UPDATED_DESCONTO
        defaultItemFacturaShouldNotBeFound("desconto.equals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where desconto in DEFAULT_DESCONTO or UPDATED_DESCONTO
        defaultItemFacturaShouldBeFound("desconto.in=" + DEFAULT_DESCONTO + "," + UPDATED_DESCONTO);

        // Get all the itemFacturaList where desconto equals to UPDATED_DESCONTO
        defaultItemFacturaShouldNotBeFound("desconto.in=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where desconto is not null
        defaultItemFacturaShouldBeFound("desconto.specified=true");

        // Get all the itemFacturaList where desconto is null
        defaultItemFacturaShouldNotBeFound("desconto.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where desconto is greater than or equal to DEFAULT_DESCONTO
        defaultItemFacturaShouldBeFound("desconto.greaterThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the itemFacturaList where desconto is greater than or equal to UPDATED_DESCONTO
        defaultItemFacturaShouldNotBeFound("desconto.greaterThanOrEqual=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where desconto is less than or equal to DEFAULT_DESCONTO
        defaultItemFacturaShouldBeFound("desconto.lessThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the itemFacturaList where desconto is less than or equal to SMALLER_DESCONTO
        defaultItemFacturaShouldNotBeFound("desconto.lessThanOrEqual=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where desconto is less than DEFAULT_DESCONTO
        defaultItemFacturaShouldNotBeFound("desconto.lessThan=" + DEFAULT_DESCONTO);

        // Get all the itemFacturaList where desconto is less than UPDATED_DESCONTO
        defaultItemFacturaShouldBeFound("desconto.lessThan=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where desconto is greater than DEFAULT_DESCONTO
        defaultItemFacturaShouldNotBeFound("desconto.greaterThan=" + DEFAULT_DESCONTO);

        // Get all the itemFacturaList where desconto is greater than SMALLER_DESCONTO
        defaultItemFacturaShouldBeFound("desconto.greaterThan=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByMultaIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where multa equals to DEFAULT_MULTA
        defaultItemFacturaShouldBeFound("multa.equals=" + DEFAULT_MULTA);

        // Get all the itemFacturaList where multa equals to UPDATED_MULTA
        defaultItemFacturaShouldNotBeFound("multa.equals=" + UPDATED_MULTA);
    }

    @Test
    @Transactional
    void getAllItemFacturasByMultaIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where multa in DEFAULT_MULTA or UPDATED_MULTA
        defaultItemFacturaShouldBeFound("multa.in=" + DEFAULT_MULTA + "," + UPDATED_MULTA);

        // Get all the itemFacturaList where multa equals to UPDATED_MULTA
        defaultItemFacturaShouldNotBeFound("multa.in=" + UPDATED_MULTA);
    }

    @Test
    @Transactional
    void getAllItemFacturasByMultaIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where multa is not null
        defaultItemFacturaShouldBeFound("multa.specified=true");

        // Get all the itemFacturaList where multa is null
        defaultItemFacturaShouldNotBeFound("multa.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByMultaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where multa is greater than or equal to DEFAULT_MULTA
        defaultItemFacturaShouldBeFound("multa.greaterThanOrEqual=" + DEFAULT_MULTA);

        // Get all the itemFacturaList where multa is greater than or equal to UPDATED_MULTA
        defaultItemFacturaShouldNotBeFound("multa.greaterThanOrEqual=" + UPDATED_MULTA);
    }

    @Test
    @Transactional
    void getAllItemFacturasByMultaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where multa is less than or equal to DEFAULT_MULTA
        defaultItemFacturaShouldBeFound("multa.lessThanOrEqual=" + DEFAULT_MULTA);

        // Get all the itemFacturaList where multa is less than or equal to SMALLER_MULTA
        defaultItemFacturaShouldNotBeFound("multa.lessThanOrEqual=" + SMALLER_MULTA);
    }

    @Test
    @Transactional
    void getAllItemFacturasByMultaIsLessThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where multa is less than DEFAULT_MULTA
        defaultItemFacturaShouldNotBeFound("multa.lessThan=" + DEFAULT_MULTA);

        // Get all the itemFacturaList where multa is less than UPDATED_MULTA
        defaultItemFacturaShouldBeFound("multa.lessThan=" + UPDATED_MULTA);
    }

    @Test
    @Transactional
    void getAllItemFacturasByMultaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where multa is greater than DEFAULT_MULTA
        defaultItemFacturaShouldNotBeFound("multa.greaterThan=" + DEFAULT_MULTA);

        // Get all the itemFacturaList where multa is greater than SMALLER_MULTA
        defaultItemFacturaShouldBeFound("multa.greaterThan=" + SMALLER_MULTA);
    }

    @Test
    @Transactional
    void getAllItemFacturasByJuroIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where juro equals to DEFAULT_JURO
        defaultItemFacturaShouldBeFound("juro.equals=" + DEFAULT_JURO);

        // Get all the itemFacturaList where juro equals to UPDATED_JURO
        defaultItemFacturaShouldNotBeFound("juro.equals=" + UPDATED_JURO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByJuroIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where juro in DEFAULT_JURO or UPDATED_JURO
        defaultItemFacturaShouldBeFound("juro.in=" + DEFAULT_JURO + "," + UPDATED_JURO);

        // Get all the itemFacturaList where juro equals to UPDATED_JURO
        defaultItemFacturaShouldNotBeFound("juro.in=" + UPDATED_JURO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByJuroIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where juro is not null
        defaultItemFacturaShouldBeFound("juro.specified=true");

        // Get all the itemFacturaList where juro is null
        defaultItemFacturaShouldNotBeFound("juro.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByJuroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where juro is greater than or equal to DEFAULT_JURO
        defaultItemFacturaShouldBeFound("juro.greaterThanOrEqual=" + DEFAULT_JURO);

        // Get all the itemFacturaList where juro is greater than or equal to UPDATED_JURO
        defaultItemFacturaShouldNotBeFound("juro.greaterThanOrEqual=" + UPDATED_JURO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByJuroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where juro is less than or equal to DEFAULT_JURO
        defaultItemFacturaShouldBeFound("juro.lessThanOrEqual=" + DEFAULT_JURO);

        // Get all the itemFacturaList where juro is less than or equal to SMALLER_JURO
        defaultItemFacturaShouldNotBeFound("juro.lessThanOrEqual=" + SMALLER_JURO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByJuroIsLessThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where juro is less than DEFAULT_JURO
        defaultItemFacturaShouldNotBeFound("juro.lessThan=" + DEFAULT_JURO);

        // Get all the itemFacturaList where juro is less than UPDATED_JURO
        defaultItemFacturaShouldBeFound("juro.lessThan=" + UPDATED_JURO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByJuroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where juro is greater than DEFAULT_JURO
        defaultItemFacturaShouldNotBeFound("juro.greaterThan=" + DEFAULT_JURO);

        // Get all the itemFacturaList where juro is greater than SMALLER_JURO
        defaultItemFacturaShouldBeFound("juro.greaterThan=" + SMALLER_JURO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoTotal equals to DEFAULT_PRECO_TOTAL
        defaultItemFacturaShouldBeFound("precoTotal.equals=" + DEFAULT_PRECO_TOTAL);

        // Get all the itemFacturaList where precoTotal equals to UPDATED_PRECO_TOTAL
        defaultItemFacturaShouldNotBeFound("precoTotal.equals=" + UPDATED_PRECO_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoTotalIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoTotal in DEFAULT_PRECO_TOTAL or UPDATED_PRECO_TOTAL
        defaultItemFacturaShouldBeFound("precoTotal.in=" + DEFAULT_PRECO_TOTAL + "," + UPDATED_PRECO_TOTAL);

        // Get all the itemFacturaList where precoTotal equals to UPDATED_PRECO_TOTAL
        defaultItemFacturaShouldNotBeFound("precoTotal.in=" + UPDATED_PRECO_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoTotal is not null
        defaultItemFacturaShouldBeFound("precoTotal.specified=true");

        // Get all the itemFacturaList where precoTotal is null
        defaultItemFacturaShouldNotBeFound("precoTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoTotal is greater than or equal to DEFAULT_PRECO_TOTAL
        defaultItemFacturaShouldBeFound("precoTotal.greaterThanOrEqual=" + DEFAULT_PRECO_TOTAL);

        // Get all the itemFacturaList where precoTotal is greater than or equal to UPDATED_PRECO_TOTAL
        defaultItemFacturaShouldNotBeFound("precoTotal.greaterThanOrEqual=" + UPDATED_PRECO_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoTotal is less than or equal to DEFAULT_PRECO_TOTAL
        defaultItemFacturaShouldBeFound("precoTotal.lessThanOrEqual=" + DEFAULT_PRECO_TOTAL);

        // Get all the itemFacturaList where precoTotal is less than or equal to SMALLER_PRECO_TOTAL
        defaultItemFacturaShouldNotBeFound("precoTotal.lessThanOrEqual=" + SMALLER_PRECO_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoTotal is less than DEFAULT_PRECO_TOTAL
        defaultItemFacturaShouldNotBeFound("precoTotal.lessThan=" + DEFAULT_PRECO_TOTAL);

        // Get all the itemFacturaList where precoTotal is less than UPDATED_PRECO_TOTAL
        defaultItemFacturaShouldBeFound("precoTotal.lessThan=" + UPDATED_PRECO_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPrecoTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where precoTotal is greater than DEFAULT_PRECO_TOTAL
        defaultItemFacturaShouldNotBeFound("precoTotal.greaterThan=" + DEFAULT_PRECO_TOTAL);

        // Get all the itemFacturaList where precoTotal is greater than SMALLER_PRECO_TOTAL
        defaultItemFacturaShouldBeFound("precoTotal.greaterThan=" + SMALLER_PRECO_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemFacturasByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where estado equals to DEFAULT_ESTADO
        defaultItemFacturaShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the itemFacturaList where estado equals to UPDATED_ESTADO
        defaultItemFacturaShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultItemFacturaShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the itemFacturaList where estado equals to UPDATED_ESTADO
        defaultItemFacturaShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where estado is not null
        defaultItemFacturaShouldBeFound("estado.specified=true");

        // Get all the itemFacturaList where estado is null
        defaultItemFacturaShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxType equals to DEFAULT_TAX_TYPE
        defaultItemFacturaShouldBeFound("taxType.equals=" + DEFAULT_TAX_TYPE);

        // Get all the itemFacturaList where taxType equals to UPDATED_TAX_TYPE
        defaultItemFacturaShouldNotBeFound("taxType.equals=" + UPDATED_TAX_TYPE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxTypeIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxType in DEFAULT_TAX_TYPE or UPDATED_TAX_TYPE
        defaultItemFacturaShouldBeFound("taxType.in=" + DEFAULT_TAX_TYPE + "," + UPDATED_TAX_TYPE);

        // Get all the itemFacturaList where taxType equals to UPDATED_TAX_TYPE
        defaultItemFacturaShouldNotBeFound("taxType.in=" + UPDATED_TAX_TYPE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxType is not null
        defaultItemFacturaShouldBeFound("taxType.specified=true");

        // Get all the itemFacturaList where taxType is null
        defaultItemFacturaShouldNotBeFound("taxType.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxTypeContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxType contains DEFAULT_TAX_TYPE
        defaultItemFacturaShouldBeFound("taxType.contains=" + DEFAULT_TAX_TYPE);

        // Get all the itemFacturaList where taxType contains UPDATED_TAX_TYPE
        defaultItemFacturaShouldNotBeFound("taxType.contains=" + UPDATED_TAX_TYPE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxTypeNotContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxType does not contain DEFAULT_TAX_TYPE
        defaultItemFacturaShouldNotBeFound("taxType.doesNotContain=" + DEFAULT_TAX_TYPE);

        // Get all the itemFacturaList where taxType does not contain UPDATED_TAX_TYPE
        defaultItemFacturaShouldBeFound("taxType.doesNotContain=" + UPDATED_TAX_TYPE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxCountryRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxCountryRegion equals to DEFAULT_TAX_COUNTRY_REGION
        defaultItemFacturaShouldBeFound("taxCountryRegion.equals=" + DEFAULT_TAX_COUNTRY_REGION);

        // Get all the itemFacturaList where taxCountryRegion equals to UPDATED_TAX_COUNTRY_REGION
        defaultItemFacturaShouldNotBeFound("taxCountryRegion.equals=" + UPDATED_TAX_COUNTRY_REGION);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxCountryRegionIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxCountryRegion in DEFAULT_TAX_COUNTRY_REGION or UPDATED_TAX_COUNTRY_REGION
        defaultItemFacturaShouldBeFound("taxCountryRegion.in=" + DEFAULT_TAX_COUNTRY_REGION + "," + UPDATED_TAX_COUNTRY_REGION);

        // Get all the itemFacturaList where taxCountryRegion equals to UPDATED_TAX_COUNTRY_REGION
        defaultItemFacturaShouldNotBeFound("taxCountryRegion.in=" + UPDATED_TAX_COUNTRY_REGION);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxCountryRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxCountryRegion is not null
        defaultItemFacturaShouldBeFound("taxCountryRegion.specified=true");

        // Get all the itemFacturaList where taxCountryRegion is null
        defaultItemFacturaShouldNotBeFound("taxCountryRegion.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxCountryRegionContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxCountryRegion contains DEFAULT_TAX_COUNTRY_REGION
        defaultItemFacturaShouldBeFound("taxCountryRegion.contains=" + DEFAULT_TAX_COUNTRY_REGION);

        // Get all the itemFacturaList where taxCountryRegion contains UPDATED_TAX_COUNTRY_REGION
        defaultItemFacturaShouldNotBeFound("taxCountryRegion.contains=" + UPDATED_TAX_COUNTRY_REGION);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxCountryRegionNotContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxCountryRegion does not contain DEFAULT_TAX_COUNTRY_REGION
        defaultItemFacturaShouldNotBeFound("taxCountryRegion.doesNotContain=" + DEFAULT_TAX_COUNTRY_REGION);

        // Get all the itemFacturaList where taxCountryRegion does not contain UPDATED_TAX_COUNTRY_REGION
        defaultItemFacturaShouldBeFound("taxCountryRegion.doesNotContain=" + UPDATED_TAX_COUNTRY_REGION);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxCode equals to DEFAULT_TAX_CODE
        defaultItemFacturaShouldBeFound("taxCode.equals=" + DEFAULT_TAX_CODE);

        // Get all the itemFacturaList where taxCode equals to UPDATED_TAX_CODE
        defaultItemFacturaShouldNotBeFound("taxCode.equals=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxCodeIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxCode in DEFAULT_TAX_CODE or UPDATED_TAX_CODE
        defaultItemFacturaShouldBeFound("taxCode.in=" + DEFAULT_TAX_CODE + "," + UPDATED_TAX_CODE);

        // Get all the itemFacturaList where taxCode equals to UPDATED_TAX_CODE
        defaultItemFacturaShouldNotBeFound("taxCode.in=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxCode is not null
        defaultItemFacturaShouldBeFound("taxCode.specified=true");

        // Get all the itemFacturaList where taxCode is null
        defaultItemFacturaShouldNotBeFound("taxCode.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxCodeContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxCode contains DEFAULT_TAX_CODE
        defaultItemFacturaShouldBeFound("taxCode.contains=" + DEFAULT_TAX_CODE);

        // Get all the itemFacturaList where taxCode contains UPDATED_TAX_CODE
        defaultItemFacturaShouldNotBeFound("taxCode.contains=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxCodeNotContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxCode does not contain DEFAULT_TAX_CODE
        defaultItemFacturaShouldNotBeFound("taxCode.doesNotContain=" + DEFAULT_TAX_CODE);

        // Get all the itemFacturaList where taxCode does not contain UPDATED_TAX_CODE
        defaultItemFacturaShouldBeFound("taxCode.doesNotContain=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxPercentage equals to DEFAULT_TAX_PERCENTAGE
        defaultItemFacturaShouldBeFound("taxPercentage.equals=" + DEFAULT_TAX_PERCENTAGE);

        // Get all the itemFacturaList where taxPercentage equals to UPDATED_TAX_PERCENTAGE
        defaultItemFacturaShouldNotBeFound("taxPercentage.equals=" + UPDATED_TAX_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxPercentage in DEFAULT_TAX_PERCENTAGE or UPDATED_TAX_PERCENTAGE
        defaultItemFacturaShouldBeFound("taxPercentage.in=" + DEFAULT_TAX_PERCENTAGE + "," + UPDATED_TAX_PERCENTAGE);

        // Get all the itemFacturaList where taxPercentage equals to UPDATED_TAX_PERCENTAGE
        defaultItemFacturaShouldNotBeFound("taxPercentage.in=" + UPDATED_TAX_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxPercentage is not null
        defaultItemFacturaShouldBeFound("taxPercentage.specified=true");

        // Get all the itemFacturaList where taxPercentage is null
        defaultItemFacturaShouldNotBeFound("taxPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxPercentage is greater than or equal to DEFAULT_TAX_PERCENTAGE
        defaultItemFacturaShouldBeFound("taxPercentage.greaterThanOrEqual=" + DEFAULT_TAX_PERCENTAGE);

        // Get all the itemFacturaList where taxPercentage is greater than or equal to (DEFAULT_TAX_PERCENTAGE + 1)
        defaultItemFacturaShouldNotBeFound("taxPercentage.greaterThanOrEqual=" + (DEFAULT_TAX_PERCENTAGE + 1));
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxPercentage is less than or equal to DEFAULT_TAX_PERCENTAGE
        defaultItemFacturaShouldBeFound("taxPercentage.lessThanOrEqual=" + DEFAULT_TAX_PERCENTAGE);

        // Get all the itemFacturaList where taxPercentage is less than or equal to SMALLER_TAX_PERCENTAGE
        defaultItemFacturaShouldNotBeFound("taxPercentage.lessThanOrEqual=" + SMALLER_TAX_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxPercentage is less than DEFAULT_TAX_PERCENTAGE
        defaultItemFacturaShouldNotBeFound("taxPercentage.lessThan=" + DEFAULT_TAX_PERCENTAGE);

        // Get all the itemFacturaList where taxPercentage is less than (DEFAULT_TAX_PERCENTAGE + 1)
        defaultItemFacturaShouldBeFound("taxPercentage.lessThan=" + (DEFAULT_TAX_PERCENTAGE + 1));
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxPercentage is greater than DEFAULT_TAX_PERCENTAGE
        defaultItemFacturaShouldNotBeFound("taxPercentage.greaterThan=" + DEFAULT_TAX_PERCENTAGE);

        // Get all the itemFacturaList where taxPercentage is greater than SMALLER_TAX_PERCENTAGE
        defaultItemFacturaShouldBeFound("taxPercentage.greaterThan=" + SMALLER_TAX_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxExemptionReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxExemptionReason equals to DEFAULT_TAX_EXEMPTION_REASON
        defaultItemFacturaShouldBeFound("taxExemptionReason.equals=" + DEFAULT_TAX_EXEMPTION_REASON);

        // Get all the itemFacturaList where taxExemptionReason equals to UPDATED_TAX_EXEMPTION_REASON
        defaultItemFacturaShouldNotBeFound("taxExemptionReason.equals=" + UPDATED_TAX_EXEMPTION_REASON);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxExemptionReasonIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxExemptionReason in DEFAULT_TAX_EXEMPTION_REASON or UPDATED_TAX_EXEMPTION_REASON
        defaultItemFacturaShouldBeFound("taxExemptionReason.in=" + DEFAULT_TAX_EXEMPTION_REASON + "," + UPDATED_TAX_EXEMPTION_REASON);

        // Get all the itemFacturaList where taxExemptionReason equals to UPDATED_TAX_EXEMPTION_REASON
        defaultItemFacturaShouldNotBeFound("taxExemptionReason.in=" + UPDATED_TAX_EXEMPTION_REASON);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxExemptionReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxExemptionReason is not null
        defaultItemFacturaShouldBeFound("taxExemptionReason.specified=true");

        // Get all the itemFacturaList where taxExemptionReason is null
        defaultItemFacturaShouldNotBeFound("taxExemptionReason.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxExemptionReasonContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxExemptionReason contains DEFAULT_TAX_EXEMPTION_REASON
        defaultItemFacturaShouldBeFound("taxExemptionReason.contains=" + DEFAULT_TAX_EXEMPTION_REASON);

        // Get all the itemFacturaList where taxExemptionReason contains UPDATED_TAX_EXEMPTION_REASON
        defaultItemFacturaShouldNotBeFound("taxExemptionReason.contains=" + UPDATED_TAX_EXEMPTION_REASON);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxExemptionReasonNotContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxExemptionReason does not contain DEFAULT_TAX_EXEMPTION_REASON
        defaultItemFacturaShouldNotBeFound("taxExemptionReason.doesNotContain=" + DEFAULT_TAX_EXEMPTION_REASON);

        // Get all the itemFacturaList where taxExemptionReason does not contain UPDATED_TAX_EXEMPTION_REASON
        defaultItemFacturaShouldBeFound("taxExemptionReason.doesNotContain=" + UPDATED_TAX_EXEMPTION_REASON);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxExemptionCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxExemptionCode equals to DEFAULT_TAX_EXEMPTION_CODE
        defaultItemFacturaShouldBeFound("taxExemptionCode.equals=" + DEFAULT_TAX_EXEMPTION_CODE);

        // Get all the itemFacturaList where taxExemptionCode equals to UPDATED_TAX_EXEMPTION_CODE
        defaultItemFacturaShouldNotBeFound("taxExemptionCode.equals=" + UPDATED_TAX_EXEMPTION_CODE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxExemptionCodeIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxExemptionCode in DEFAULT_TAX_EXEMPTION_CODE or UPDATED_TAX_EXEMPTION_CODE
        defaultItemFacturaShouldBeFound("taxExemptionCode.in=" + DEFAULT_TAX_EXEMPTION_CODE + "," + UPDATED_TAX_EXEMPTION_CODE);

        // Get all the itemFacturaList where taxExemptionCode equals to UPDATED_TAX_EXEMPTION_CODE
        defaultItemFacturaShouldNotBeFound("taxExemptionCode.in=" + UPDATED_TAX_EXEMPTION_CODE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxExemptionCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxExemptionCode is not null
        defaultItemFacturaShouldBeFound("taxExemptionCode.specified=true");

        // Get all the itemFacturaList where taxExemptionCode is null
        defaultItemFacturaShouldNotBeFound("taxExemptionCode.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxExemptionCodeContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxExemptionCode contains DEFAULT_TAX_EXEMPTION_CODE
        defaultItemFacturaShouldBeFound("taxExemptionCode.contains=" + DEFAULT_TAX_EXEMPTION_CODE);

        // Get all the itemFacturaList where taxExemptionCode contains UPDATED_TAX_EXEMPTION_CODE
        defaultItemFacturaShouldNotBeFound("taxExemptionCode.contains=" + UPDATED_TAX_EXEMPTION_CODE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByTaxExemptionCodeNotContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where taxExemptionCode does not contain DEFAULT_TAX_EXEMPTION_CODE
        defaultItemFacturaShouldNotBeFound("taxExemptionCode.doesNotContain=" + DEFAULT_TAX_EXEMPTION_CODE);

        // Get all the itemFacturaList where taxExemptionCode does not contain UPDATED_TAX_EXEMPTION_CODE
        defaultItemFacturaShouldBeFound("taxExemptionCode.doesNotContain=" + UPDATED_TAX_EXEMPTION_CODE);
    }

    @Test
    @Transactional
    void getAllItemFacturasByEmissaoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where emissao equals to DEFAULT_EMISSAO
        defaultItemFacturaShouldBeFound("emissao.equals=" + DEFAULT_EMISSAO);

        // Get all the itemFacturaList where emissao equals to UPDATED_EMISSAO
        defaultItemFacturaShouldNotBeFound("emissao.equals=" + UPDATED_EMISSAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByEmissaoIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where emissao in DEFAULT_EMISSAO or UPDATED_EMISSAO
        defaultItemFacturaShouldBeFound("emissao.in=" + DEFAULT_EMISSAO + "," + UPDATED_EMISSAO);

        // Get all the itemFacturaList where emissao equals to UPDATED_EMISSAO
        defaultItemFacturaShouldNotBeFound("emissao.in=" + UPDATED_EMISSAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByEmissaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where emissao is not null
        defaultItemFacturaShouldBeFound("emissao.specified=true");

        // Get all the itemFacturaList where emissao is null
        defaultItemFacturaShouldNotBeFound("emissao.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByEmissaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where emissao is greater than or equal to DEFAULT_EMISSAO
        defaultItemFacturaShouldBeFound("emissao.greaterThanOrEqual=" + DEFAULT_EMISSAO);

        // Get all the itemFacturaList where emissao is greater than or equal to UPDATED_EMISSAO
        defaultItemFacturaShouldNotBeFound("emissao.greaterThanOrEqual=" + UPDATED_EMISSAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByEmissaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where emissao is less than or equal to DEFAULT_EMISSAO
        defaultItemFacturaShouldBeFound("emissao.lessThanOrEqual=" + DEFAULT_EMISSAO);

        // Get all the itemFacturaList where emissao is less than or equal to SMALLER_EMISSAO
        defaultItemFacturaShouldNotBeFound("emissao.lessThanOrEqual=" + SMALLER_EMISSAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByEmissaoIsLessThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where emissao is less than DEFAULT_EMISSAO
        defaultItemFacturaShouldNotBeFound("emissao.lessThan=" + DEFAULT_EMISSAO);

        // Get all the itemFacturaList where emissao is less than UPDATED_EMISSAO
        defaultItemFacturaShouldBeFound("emissao.lessThan=" + UPDATED_EMISSAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByEmissaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where emissao is greater than DEFAULT_EMISSAO
        defaultItemFacturaShouldNotBeFound("emissao.greaterThan=" + DEFAULT_EMISSAO);

        // Get all the itemFacturaList where emissao is greater than SMALLER_EMISSAO
        defaultItemFacturaShouldBeFound("emissao.greaterThan=" + SMALLER_EMISSAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByExpiracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where expiracao equals to DEFAULT_EXPIRACAO
        defaultItemFacturaShouldBeFound("expiracao.equals=" + DEFAULT_EXPIRACAO);

        // Get all the itemFacturaList where expiracao equals to UPDATED_EXPIRACAO
        defaultItemFacturaShouldNotBeFound("expiracao.equals=" + UPDATED_EXPIRACAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByExpiracaoIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where expiracao in DEFAULT_EXPIRACAO or UPDATED_EXPIRACAO
        defaultItemFacturaShouldBeFound("expiracao.in=" + DEFAULT_EXPIRACAO + "," + UPDATED_EXPIRACAO);

        // Get all the itemFacturaList where expiracao equals to UPDATED_EXPIRACAO
        defaultItemFacturaShouldNotBeFound("expiracao.in=" + UPDATED_EXPIRACAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByExpiracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where expiracao is not null
        defaultItemFacturaShouldBeFound("expiracao.specified=true");

        // Get all the itemFacturaList where expiracao is null
        defaultItemFacturaShouldNotBeFound("expiracao.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByExpiracaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where expiracao is greater than or equal to DEFAULT_EXPIRACAO
        defaultItemFacturaShouldBeFound("expiracao.greaterThanOrEqual=" + DEFAULT_EXPIRACAO);

        // Get all the itemFacturaList where expiracao is greater than or equal to UPDATED_EXPIRACAO
        defaultItemFacturaShouldNotBeFound("expiracao.greaterThanOrEqual=" + UPDATED_EXPIRACAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByExpiracaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where expiracao is less than or equal to DEFAULT_EXPIRACAO
        defaultItemFacturaShouldBeFound("expiracao.lessThanOrEqual=" + DEFAULT_EXPIRACAO);

        // Get all the itemFacturaList where expiracao is less than or equal to SMALLER_EXPIRACAO
        defaultItemFacturaShouldNotBeFound("expiracao.lessThanOrEqual=" + SMALLER_EXPIRACAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByExpiracaoIsLessThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where expiracao is less than DEFAULT_EXPIRACAO
        defaultItemFacturaShouldNotBeFound("expiracao.lessThan=" + DEFAULT_EXPIRACAO);

        // Get all the itemFacturaList where expiracao is less than UPDATED_EXPIRACAO
        defaultItemFacturaShouldBeFound("expiracao.lessThan=" + UPDATED_EXPIRACAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByExpiracaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where expiracao is greater than DEFAULT_EXPIRACAO
        defaultItemFacturaShouldNotBeFound("expiracao.greaterThan=" + DEFAULT_EXPIRACAO);

        // Get all the itemFacturaList where expiracao is greater than SMALLER_EXPIRACAO
        defaultItemFacturaShouldBeFound("expiracao.greaterThan=" + SMALLER_EXPIRACAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where periodo equals to DEFAULT_PERIODO
        defaultItemFacturaShouldBeFound("periodo.equals=" + DEFAULT_PERIODO);

        // Get all the itemFacturaList where periodo equals to UPDATED_PERIODO
        defaultItemFacturaShouldNotBeFound("periodo.equals=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where periodo in DEFAULT_PERIODO or UPDATED_PERIODO
        defaultItemFacturaShouldBeFound("periodo.in=" + DEFAULT_PERIODO + "," + UPDATED_PERIODO);

        // Get all the itemFacturaList where periodo equals to UPDATED_PERIODO
        defaultItemFacturaShouldNotBeFound("periodo.in=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where periodo is not null
        defaultItemFacturaShouldBeFound("periodo.specified=true");

        // Get all the itemFacturaList where periodo is null
        defaultItemFacturaShouldNotBeFound("periodo.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByPeriodoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where periodo is greater than or equal to DEFAULT_PERIODO
        defaultItemFacturaShouldBeFound("periodo.greaterThanOrEqual=" + DEFAULT_PERIODO);

        // Get all the itemFacturaList where periodo is greater than or equal to UPDATED_PERIODO
        defaultItemFacturaShouldNotBeFound("periodo.greaterThanOrEqual=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPeriodoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where periodo is less than or equal to DEFAULT_PERIODO
        defaultItemFacturaShouldBeFound("periodo.lessThanOrEqual=" + DEFAULT_PERIODO);

        // Get all the itemFacturaList where periodo is less than or equal to SMALLER_PERIODO
        defaultItemFacturaShouldNotBeFound("periodo.lessThanOrEqual=" + SMALLER_PERIODO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPeriodoIsLessThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where periodo is less than DEFAULT_PERIODO
        defaultItemFacturaShouldNotBeFound("periodo.lessThan=" + DEFAULT_PERIODO);

        // Get all the itemFacturaList where periodo is less than UPDATED_PERIODO
        defaultItemFacturaShouldBeFound("periodo.lessThan=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByPeriodoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where periodo is greater than DEFAULT_PERIODO
        defaultItemFacturaShouldNotBeFound("periodo.greaterThan=" + DEFAULT_PERIODO);

        // Get all the itemFacturaList where periodo is greater than SMALLER_PERIODO
        defaultItemFacturaShouldBeFound("periodo.greaterThan=" + SMALLER_PERIODO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where descricao equals to DEFAULT_DESCRICAO
        defaultItemFacturaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the itemFacturaList where descricao equals to UPDATED_DESCRICAO
        defaultItemFacturaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultItemFacturaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the itemFacturaList where descricao equals to UPDATED_DESCRICAO
        defaultItemFacturaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where descricao is not null
        defaultItemFacturaShouldBeFound("descricao.specified=true");

        // Get all the itemFacturaList where descricao is null
        defaultItemFacturaShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where descricao contains DEFAULT_DESCRICAO
        defaultItemFacturaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the itemFacturaList where descricao contains UPDATED_DESCRICAO
        defaultItemFacturaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        // Get all the itemFacturaList where descricao does not contain DEFAULT_DESCRICAO
        defaultItemFacturaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the itemFacturaList where descricao does not contain UPDATED_DESCRICAO
        defaultItemFacturaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllItemFacturasByFacturaIsEqualToSomething() throws Exception {
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            itemFacturaRepository.saveAndFlush(itemFactura);
            factura = FacturaResourceIT.createEntity(em);
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        em.persist(factura);
        em.flush();
        itemFactura.setFactura(factura);
        itemFacturaRepository.saveAndFlush(itemFactura);
        Long facturaId = factura.getId();

        // Get all the itemFacturaList where factura equals to facturaId
        defaultItemFacturaShouldBeFound("facturaId.equals=" + facturaId);

        // Get all the itemFacturaList where factura equals to (facturaId + 1)
        defaultItemFacturaShouldNotBeFound("facturaId.equals=" + (facturaId + 1));
    }

    @Test
    @Transactional
    void getAllItemFacturasByEmolumentoIsEqualToSomething() throws Exception {
        Emolumento emolumento;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            itemFacturaRepository.saveAndFlush(itemFactura);
            emolumento = EmolumentoResourceIT.createEntity(em);
        } else {
            emolumento = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        em.persist(emolumento);
        em.flush();
        itemFactura.setEmolumento(emolumento);
        itemFacturaRepository.saveAndFlush(itemFactura);
        Long emolumentoId = emolumento.getId();

        // Get all the itemFacturaList where emolumento equals to emolumentoId
        defaultItemFacturaShouldBeFound("emolumentoId.equals=" + emolumentoId);

        // Get all the itemFacturaList where emolumento equals to (emolumentoId + 1)
        defaultItemFacturaShouldNotBeFound("emolumentoId.equals=" + (emolumentoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemFacturaShouldBeFound(String filter) throws Exception {
        restItemFacturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemFactura.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].precoUnitario").value(hasItem(sameNumber(DEFAULT_PRECO_UNITARIO))))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(sameNumber(DEFAULT_DESCONTO))))
            .andExpect(jsonPath("$.[*].multa").value(hasItem(sameNumber(DEFAULT_MULTA))))
            .andExpect(jsonPath("$.[*].juro").value(hasItem(sameNumber(DEFAULT_JURO))))
            .andExpect(jsonPath("$.[*].precoTotal").value(hasItem(sameNumber(DEFAULT_PRECO_TOTAL))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].taxType").value(hasItem(DEFAULT_TAX_TYPE)))
            .andExpect(jsonPath("$.[*].taxCountryRegion").value(hasItem(DEFAULT_TAX_COUNTRY_REGION)))
            .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE)))
            .andExpect(jsonPath("$.[*].taxPercentage").value(hasItem(DEFAULT_TAX_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].taxExemptionReason").value(hasItem(DEFAULT_TAX_EXEMPTION_REASON)))
            .andExpect(jsonPath("$.[*].taxExemptionCode").value(hasItem(DEFAULT_TAX_EXEMPTION_CODE)))
            .andExpect(jsonPath("$.[*].emissao").value(hasItem(DEFAULT_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].expiracao").value(hasItem(DEFAULT_EXPIRACAO.toString())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restItemFacturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemFacturaShouldNotBeFound(String filter) throws Exception {
        restItemFacturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemFacturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingItemFactura() throws Exception {
        // Get the itemFactura
        restItemFacturaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingItemFactura() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        int databaseSizeBeforeUpdate = itemFacturaRepository.findAll().size();

        // Update the itemFactura
        ItemFactura updatedItemFactura = itemFacturaRepository.findById(itemFactura.getId()).get();
        // Disconnect from session so that the updates on updatedItemFactura are not directly saved in db
        em.detach(updatedItemFactura);
        updatedItemFactura
            .quantidade(UPDATED_QUANTIDADE)
            .precoUnitario(UPDATED_PRECO_UNITARIO)
            .desconto(UPDATED_DESCONTO)
            .multa(UPDATED_MULTA)
            .juro(UPDATED_JURO)
            .precoTotal(UPDATED_PRECO_TOTAL)
            .estado(UPDATED_ESTADO)
            .taxType(UPDATED_TAX_TYPE)
            .taxCountryRegion(UPDATED_TAX_COUNTRY_REGION)
            .taxCode(UPDATED_TAX_CODE)
            .taxPercentage(UPDATED_TAX_PERCENTAGE)
            .taxExemptionReason(UPDATED_TAX_EXEMPTION_REASON)
            .taxExemptionCode(UPDATED_TAX_EXEMPTION_CODE)
            .emissao(UPDATED_EMISSAO)
            .expiracao(UPDATED_EXPIRACAO)
            .periodo(UPDATED_PERIODO)
            .descricao(UPDATED_DESCRICAO);
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(updatedItemFactura);

        restItemFacturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemFacturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isOk());

        // Validate the ItemFactura in the database
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeUpdate);
        ItemFactura testItemFactura = itemFacturaList.get(itemFacturaList.size() - 1);
        assertThat(testItemFactura.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testItemFactura.getPrecoUnitario()).isEqualByComparingTo(UPDATED_PRECO_UNITARIO);
        assertThat(testItemFactura.getDesconto()).isEqualByComparingTo(UPDATED_DESCONTO);
        assertThat(testItemFactura.getMulta()).isEqualByComparingTo(UPDATED_MULTA);
        assertThat(testItemFactura.getJuro()).isEqualByComparingTo(UPDATED_JURO);
        assertThat(testItemFactura.getPrecoTotal()).isEqualByComparingTo(UPDATED_PRECO_TOTAL);
        assertThat(testItemFactura.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testItemFactura.getTaxType()).isEqualTo(UPDATED_TAX_TYPE);
        assertThat(testItemFactura.getTaxCountryRegion()).isEqualTo(UPDATED_TAX_COUNTRY_REGION);
        assertThat(testItemFactura.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testItemFactura.getTaxPercentage()).isEqualTo(UPDATED_TAX_PERCENTAGE);
        assertThat(testItemFactura.getTaxExemptionReason()).isEqualTo(UPDATED_TAX_EXEMPTION_REASON);
        assertThat(testItemFactura.getTaxExemptionCode()).isEqualTo(UPDATED_TAX_EXEMPTION_CODE);
        assertThat(testItemFactura.getEmissao()).isEqualTo(UPDATED_EMISSAO);
        assertThat(testItemFactura.getExpiracao()).isEqualTo(UPDATED_EXPIRACAO);
        assertThat(testItemFactura.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testItemFactura.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingItemFactura() throws Exception {
        int databaseSizeBeforeUpdate = itemFacturaRepository.findAll().size();
        itemFactura.setId(count.incrementAndGet());

        // Create the ItemFactura
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemFacturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemFacturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemFactura in the database
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItemFactura() throws Exception {
        int databaseSizeBeforeUpdate = itemFacturaRepository.findAll().size();
        itemFactura.setId(count.incrementAndGet());

        // Create the ItemFactura
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemFacturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemFactura in the database
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItemFactura() throws Exception {
        int databaseSizeBeforeUpdate = itemFacturaRepository.findAll().size();
        itemFactura.setId(count.incrementAndGet());

        // Create the ItemFactura
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemFacturaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemFactura in the database
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemFacturaWithPatch() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        int databaseSizeBeforeUpdate = itemFacturaRepository.findAll().size();

        // Update the itemFactura using partial update
        ItemFactura partialUpdatedItemFactura = new ItemFactura();
        partialUpdatedItemFactura.setId(itemFactura.getId());

        partialUpdatedItemFactura
            .quantidade(UPDATED_QUANTIDADE)
            .precoUnitario(UPDATED_PRECO_UNITARIO)
            .multa(UPDATED_MULTA)
            .estado(UPDATED_ESTADO)
            .taxCode(UPDATED_TAX_CODE)
            .taxPercentage(UPDATED_TAX_PERCENTAGE)
            .expiracao(UPDATED_EXPIRACAO);

        restItemFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemFactura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemFactura))
            )
            .andExpect(status().isOk());

        // Validate the ItemFactura in the database
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeUpdate);
        ItemFactura testItemFactura = itemFacturaList.get(itemFacturaList.size() - 1);
        assertThat(testItemFactura.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testItemFactura.getPrecoUnitario()).isEqualByComparingTo(UPDATED_PRECO_UNITARIO);
        assertThat(testItemFactura.getDesconto()).isEqualByComparingTo(DEFAULT_DESCONTO);
        assertThat(testItemFactura.getMulta()).isEqualByComparingTo(UPDATED_MULTA);
        assertThat(testItemFactura.getJuro()).isEqualByComparingTo(DEFAULT_JURO);
        assertThat(testItemFactura.getPrecoTotal()).isEqualByComparingTo(DEFAULT_PRECO_TOTAL);
        assertThat(testItemFactura.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testItemFactura.getTaxType()).isEqualTo(DEFAULT_TAX_TYPE);
        assertThat(testItemFactura.getTaxCountryRegion()).isEqualTo(DEFAULT_TAX_COUNTRY_REGION);
        assertThat(testItemFactura.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testItemFactura.getTaxPercentage()).isEqualTo(UPDATED_TAX_PERCENTAGE);
        assertThat(testItemFactura.getTaxExemptionReason()).isEqualTo(DEFAULT_TAX_EXEMPTION_REASON);
        assertThat(testItemFactura.getTaxExemptionCode()).isEqualTo(DEFAULT_TAX_EXEMPTION_CODE);
        assertThat(testItemFactura.getEmissao()).isEqualTo(DEFAULT_EMISSAO);
        assertThat(testItemFactura.getExpiracao()).isEqualTo(UPDATED_EXPIRACAO);
        assertThat(testItemFactura.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testItemFactura.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateItemFacturaWithPatch() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        int databaseSizeBeforeUpdate = itemFacturaRepository.findAll().size();

        // Update the itemFactura using partial update
        ItemFactura partialUpdatedItemFactura = new ItemFactura();
        partialUpdatedItemFactura.setId(itemFactura.getId());

        partialUpdatedItemFactura
            .quantidade(UPDATED_QUANTIDADE)
            .precoUnitario(UPDATED_PRECO_UNITARIO)
            .desconto(UPDATED_DESCONTO)
            .multa(UPDATED_MULTA)
            .juro(UPDATED_JURO)
            .precoTotal(UPDATED_PRECO_TOTAL)
            .estado(UPDATED_ESTADO)
            .taxType(UPDATED_TAX_TYPE)
            .taxCountryRegion(UPDATED_TAX_COUNTRY_REGION)
            .taxCode(UPDATED_TAX_CODE)
            .taxPercentage(UPDATED_TAX_PERCENTAGE)
            .taxExemptionReason(UPDATED_TAX_EXEMPTION_REASON)
            .taxExemptionCode(UPDATED_TAX_EXEMPTION_CODE)
            .emissao(UPDATED_EMISSAO)
            .expiracao(UPDATED_EXPIRACAO)
            .periodo(UPDATED_PERIODO)
            .descricao(UPDATED_DESCRICAO);

        restItemFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemFactura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemFactura))
            )
            .andExpect(status().isOk());

        // Validate the ItemFactura in the database
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeUpdate);
        ItemFactura testItemFactura = itemFacturaList.get(itemFacturaList.size() - 1);
        assertThat(testItemFactura.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testItemFactura.getPrecoUnitario()).isEqualByComparingTo(UPDATED_PRECO_UNITARIO);
        assertThat(testItemFactura.getDesconto()).isEqualByComparingTo(UPDATED_DESCONTO);
        assertThat(testItemFactura.getMulta()).isEqualByComparingTo(UPDATED_MULTA);
        assertThat(testItemFactura.getJuro()).isEqualByComparingTo(UPDATED_JURO);
        assertThat(testItemFactura.getPrecoTotal()).isEqualByComparingTo(UPDATED_PRECO_TOTAL);
        assertThat(testItemFactura.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testItemFactura.getTaxType()).isEqualTo(UPDATED_TAX_TYPE);
        assertThat(testItemFactura.getTaxCountryRegion()).isEqualTo(UPDATED_TAX_COUNTRY_REGION);
        assertThat(testItemFactura.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testItemFactura.getTaxPercentage()).isEqualTo(UPDATED_TAX_PERCENTAGE);
        assertThat(testItemFactura.getTaxExemptionReason()).isEqualTo(UPDATED_TAX_EXEMPTION_REASON);
        assertThat(testItemFactura.getTaxExemptionCode()).isEqualTo(UPDATED_TAX_EXEMPTION_CODE);
        assertThat(testItemFactura.getEmissao()).isEqualTo(UPDATED_EMISSAO);
        assertThat(testItemFactura.getExpiracao()).isEqualTo(UPDATED_EXPIRACAO);
        assertThat(testItemFactura.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testItemFactura.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingItemFactura() throws Exception {
        int databaseSizeBeforeUpdate = itemFacturaRepository.findAll().size();
        itemFactura.setId(count.incrementAndGet());

        // Create the ItemFactura
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemFacturaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemFactura in the database
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItemFactura() throws Exception {
        int databaseSizeBeforeUpdate = itemFacturaRepository.findAll().size();
        itemFactura.setId(count.incrementAndGet());

        // Create the ItemFactura
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemFactura in the database
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItemFactura() throws Exception {
        int databaseSizeBeforeUpdate = itemFacturaRepository.findAll().size();
        itemFactura.setId(count.incrementAndGet());

        // Create the ItemFactura
        ItemFacturaDTO itemFacturaDTO = itemFacturaMapper.toDto(itemFactura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itemFacturaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemFactura in the database
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItemFactura() throws Exception {
        // Initialize the database
        itemFacturaRepository.saveAndFlush(itemFactura);

        int databaseSizeBeforeDelete = itemFacturaRepository.findAll().size();

        // Delete the itemFactura
        restItemFacturaMockMvc
            .perform(delete(ENTITY_API_URL_ID, itemFactura.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemFactura> itemFacturaList = itemFacturaRepository.findAll();
        assertThat(itemFacturaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
