package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.DocumentoComercial;
import com.ravunana.longonkelo.domain.DocumentoComercial;
import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.domain.Recibo;
import com.ravunana.longonkelo.domain.SerieDocumento;
import com.ravunana.longonkelo.domain.enumeration.DocumentoFiscal;
import com.ravunana.longonkelo.domain.enumeration.ModuloDocumento;
import com.ravunana.longonkelo.domain.enumeration.OrigemDocumento;
import com.ravunana.longonkelo.repository.DocumentoComercialRepository;
import com.ravunana.longonkelo.service.DocumentoComercialService;
import com.ravunana.longonkelo.service.criteria.DocumentoComercialCriteria;
import com.ravunana.longonkelo.service.dto.DocumentoComercialDTO;
import com.ravunana.longonkelo.service.mapper.DocumentoComercialMapper;
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
 * Integration tests for the {@link DocumentoComercialResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DocumentoComercialResourceIT {

    private static final ModuloDocumento DEFAULT_MODULO = ModuloDocumento.VENDA;
    private static final ModuloDocumento UPDATED_MODULO = ModuloDocumento.COMPRA;

    private static final OrigemDocumento DEFAULT_ORIGEM = OrigemDocumento.P;
    private static final OrigemDocumento UPDATED_ORIGEM = OrigemDocumento.I;

    private static final String DEFAULT_SIGLA_INTERNA = "AAAAAA";
    private static final String UPDATED_SIGLA_INTERNA = "BBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final DocumentoFiscal DEFAULT_SIGLA_FISCAL = DocumentoFiscal.FT;
    private static final DocumentoFiscal UPDATED_SIGLA_FISCAL = DocumentoFiscal.FR;

    private static final Boolean DEFAULT_IS_MOVIMENTA_ESTOQUE = false;
    private static final Boolean UPDATED_IS_MOVIMENTA_ESTOQUE = true;

    private static final Boolean DEFAULT_IS_MOVIMENTA_CAIXA = false;
    private static final Boolean UPDATED_IS_MOVIMENTA_CAIXA = true;

    private static final Boolean DEFAULT_IS_NOTIFICA_ENTIDADE = false;
    private static final Boolean UPDATED_IS_NOTIFICA_ENTIDADE = true;

    private static final Boolean DEFAULT_IS_NOTIFICA_GERENTE = false;
    private static final Boolean UPDATED_IS_NOTIFICA_GERENTE = true;

    private static final Boolean DEFAULT_IS_ENVIA_SMS = false;
    private static final Boolean UPDATED_IS_ENVIA_SMS = true;

    private static final Boolean DEFAULT_IS_ENVIA_EMAIL = false;
    private static final Boolean UPDATED_IS_ENVIA_EMAIL = true;

    private static final Boolean DEFAULT_IS_ENVIA_PUSH = false;
    private static final Boolean UPDATED_IS_ENVIA_PUSH = true;

    private static final Boolean DEFAULT_VALIDA_CREDITO_DISPONIVEL = false;
    private static final Boolean UPDATED_VALIDA_CREDITO_DISPONIVEL = true;

    private static final String ENTITY_API_URL = "/api/documento-comercials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentoComercialRepository documentoComercialRepository;

    @Mock
    private DocumentoComercialRepository documentoComercialRepositoryMock;

    @Autowired
    private DocumentoComercialMapper documentoComercialMapper;

    @Mock
    private DocumentoComercialService documentoComercialServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentoComercialMockMvc;

    private DocumentoComercial documentoComercial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentoComercial createEntity(EntityManager em) {
        DocumentoComercial documentoComercial = new DocumentoComercial()
            .modulo(DEFAULT_MODULO)
            .origem(DEFAULT_ORIGEM)
            .siglaInterna(DEFAULT_SIGLA_INTERNA)
            .descricao(DEFAULT_DESCRICAO)
            .siglaFiscal(DEFAULT_SIGLA_FISCAL)
            .isMovimentaEstoque(DEFAULT_IS_MOVIMENTA_ESTOQUE)
            .isMovimentaCaixa(DEFAULT_IS_MOVIMENTA_CAIXA)
            .isNotificaEntidade(DEFAULT_IS_NOTIFICA_ENTIDADE)
            .isNotificaGerente(DEFAULT_IS_NOTIFICA_GERENTE)
            .isEnviaSMS(DEFAULT_IS_ENVIA_SMS)
            .isEnviaEmail(DEFAULT_IS_ENVIA_EMAIL)
            .isEnviaPush(DEFAULT_IS_ENVIA_PUSH)
            .validaCreditoDisponivel(DEFAULT_VALIDA_CREDITO_DISPONIVEL);
        return documentoComercial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentoComercial createUpdatedEntity(EntityManager em) {
        DocumentoComercial documentoComercial = new DocumentoComercial()
            .modulo(UPDATED_MODULO)
            .origem(UPDATED_ORIGEM)
            .siglaInterna(UPDATED_SIGLA_INTERNA)
            .descricao(UPDATED_DESCRICAO)
            .siglaFiscal(UPDATED_SIGLA_FISCAL)
            .isMovimentaEstoque(UPDATED_IS_MOVIMENTA_ESTOQUE)
            .isMovimentaCaixa(UPDATED_IS_MOVIMENTA_CAIXA)
            .isNotificaEntidade(UPDATED_IS_NOTIFICA_ENTIDADE)
            .isNotificaGerente(UPDATED_IS_NOTIFICA_GERENTE)
            .isEnviaSMS(UPDATED_IS_ENVIA_SMS)
            .isEnviaEmail(UPDATED_IS_ENVIA_EMAIL)
            .isEnviaPush(UPDATED_IS_ENVIA_PUSH)
            .validaCreditoDisponivel(UPDATED_VALIDA_CREDITO_DISPONIVEL);
        return documentoComercial;
    }

    @BeforeEach
    public void initTest() {
        documentoComercial = createEntity(em);
    }

    @Test
    @Transactional
    void createDocumentoComercial() throws Exception {
        int databaseSizeBeforeCreate = documentoComercialRepository.findAll().size();
        // Create the DocumentoComercial
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);
        restDocumentoComercialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentoComercial testDocumentoComercial = documentoComercialList.get(documentoComercialList.size() - 1);
        assertThat(testDocumentoComercial.getModulo()).isEqualTo(DEFAULT_MODULO);
        assertThat(testDocumentoComercial.getOrigem()).isEqualTo(DEFAULT_ORIGEM);
        assertThat(testDocumentoComercial.getSiglaInterna()).isEqualTo(DEFAULT_SIGLA_INTERNA);
        assertThat(testDocumentoComercial.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDocumentoComercial.getSiglaFiscal()).isEqualTo(DEFAULT_SIGLA_FISCAL);
        assertThat(testDocumentoComercial.getIsMovimentaEstoque()).isEqualTo(DEFAULT_IS_MOVIMENTA_ESTOQUE);
        assertThat(testDocumentoComercial.getIsMovimentaCaixa()).isEqualTo(DEFAULT_IS_MOVIMENTA_CAIXA);
        assertThat(testDocumentoComercial.getIsNotificaEntidade()).isEqualTo(DEFAULT_IS_NOTIFICA_ENTIDADE);
        assertThat(testDocumentoComercial.getIsNotificaGerente()).isEqualTo(DEFAULT_IS_NOTIFICA_GERENTE);
        assertThat(testDocumentoComercial.getIsEnviaSMS()).isEqualTo(DEFAULT_IS_ENVIA_SMS);
        assertThat(testDocumentoComercial.getIsEnviaEmail()).isEqualTo(DEFAULT_IS_ENVIA_EMAIL);
        assertThat(testDocumentoComercial.getIsEnviaPush()).isEqualTo(DEFAULT_IS_ENVIA_PUSH);
        assertThat(testDocumentoComercial.getValidaCreditoDisponivel()).isEqualTo(DEFAULT_VALIDA_CREDITO_DISPONIVEL);
    }

    @Test
    @Transactional
    void createDocumentoComercialWithExistingId() throws Exception {
        // Create the DocumentoComercial with an existing ID
        documentoComercial.setId(1L);
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        int databaseSizeBeforeCreate = documentoComercialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentoComercialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkModuloIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoComercialRepository.findAll().size();
        // set the field null
        documentoComercial.setModulo(null);

        // Create the DocumentoComercial, which fails.
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        restDocumentoComercialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isBadRequest());

        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrigemIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoComercialRepository.findAll().size();
        // set the field null
        documentoComercial.setOrigem(null);

        // Create the DocumentoComercial, which fails.
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        restDocumentoComercialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isBadRequest());

        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSiglaInternaIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoComercialRepository.findAll().size();
        // set the field null
        documentoComercial.setSiglaInterna(null);

        // Create the DocumentoComercial, which fails.
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        restDocumentoComercialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isBadRequest());

        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoComercialRepository.findAll().size();
        // set the field null
        documentoComercial.setDescricao(null);

        // Create the DocumentoComercial, which fails.
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        restDocumentoComercialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isBadRequest());

        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSiglaFiscalIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoComercialRepository.findAll().size();
        // set the field null
        documentoComercial.setSiglaFiscal(null);

        // Create the DocumentoComercial, which fails.
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        restDocumentoComercialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isBadRequest());

        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDocumentoComercials() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList
        restDocumentoComercialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentoComercial.getId().intValue())))
            .andExpect(jsonPath("$.[*].modulo").value(hasItem(DEFAULT_MODULO.toString())))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM.toString())))
            .andExpect(jsonPath("$.[*].siglaInterna").value(hasItem(DEFAULT_SIGLA_INTERNA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].siglaFiscal").value(hasItem(DEFAULT_SIGLA_FISCAL.toString())))
            .andExpect(jsonPath("$.[*].isMovimentaEstoque").value(hasItem(DEFAULT_IS_MOVIMENTA_ESTOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].isMovimentaCaixa").value(hasItem(DEFAULT_IS_MOVIMENTA_CAIXA.booleanValue())))
            .andExpect(jsonPath("$.[*].isNotificaEntidade").value(hasItem(DEFAULT_IS_NOTIFICA_ENTIDADE.booleanValue())))
            .andExpect(jsonPath("$.[*].isNotificaGerente").value(hasItem(DEFAULT_IS_NOTIFICA_GERENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].isEnviaSMS").value(hasItem(DEFAULT_IS_ENVIA_SMS.booleanValue())))
            .andExpect(jsonPath("$.[*].isEnviaEmail").value(hasItem(DEFAULT_IS_ENVIA_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].isEnviaPush").value(hasItem(DEFAULT_IS_ENVIA_PUSH.booleanValue())))
            .andExpect(jsonPath("$.[*].validaCreditoDisponivel").value(hasItem(DEFAULT_VALIDA_CREDITO_DISPONIVEL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocumentoComercialsWithEagerRelationshipsIsEnabled() throws Exception {
        when(documentoComercialServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocumentoComercialMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(documentoComercialServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocumentoComercialsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(documentoComercialServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocumentoComercialMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(documentoComercialRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDocumentoComercial() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get the documentoComercial
        restDocumentoComercialMockMvc
            .perform(get(ENTITY_API_URL_ID, documentoComercial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentoComercial.getId().intValue()))
            .andExpect(jsonPath("$.modulo").value(DEFAULT_MODULO.toString()))
            .andExpect(jsonPath("$.origem").value(DEFAULT_ORIGEM.toString()))
            .andExpect(jsonPath("$.siglaInterna").value(DEFAULT_SIGLA_INTERNA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.siglaFiscal").value(DEFAULT_SIGLA_FISCAL.toString()))
            .andExpect(jsonPath("$.isMovimentaEstoque").value(DEFAULT_IS_MOVIMENTA_ESTOQUE.booleanValue()))
            .andExpect(jsonPath("$.isMovimentaCaixa").value(DEFAULT_IS_MOVIMENTA_CAIXA.booleanValue()))
            .andExpect(jsonPath("$.isNotificaEntidade").value(DEFAULT_IS_NOTIFICA_ENTIDADE.booleanValue()))
            .andExpect(jsonPath("$.isNotificaGerente").value(DEFAULT_IS_NOTIFICA_GERENTE.booleanValue()))
            .andExpect(jsonPath("$.isEnviaSMS").value(DEFAULT_IS_ENVIA_SMS.booleanValue()))
            .andExpect(jsonPath("$.isEnviaEmail").value(DEFAULT_IS_ENVIA_EMAIL.booleanValue()))
            .andExpect(jsonPath("$.isEnviaPush").value(DEFAULT_IS_ENVIA_PUSH.booleanValue()))
            .andExpect(jsonPath("$.validaCreditoDisponivel").value(DEFAULT_VALIDA_CREDITO_DISPONIVEL.booleanValue()));
    }

    @Test
    @Transactional
    void getDocumentoComercialsByIdFiltering() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        Long id = documentoComercial.getId();

        defaultDocumentoComercialShouldBeFound("id.equals=" + id);
        defaultDocumentoComercialShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentoComercialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentoComercialShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentoComercialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentoComercialShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByModuloIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where modulo equals to DEFAULT_MODULO
        defaultDocumentoComercialShouldBeFound("modulo.equals=" + DEFAULT_MODULO);

        // Get all the documentoComercialList where modulo equals to UPDATED_MODULO
        defaultDocumentoComercialShouldNotBeFound("modulo.equals=" + UPDATED_MODULO);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByModuloIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where modulo in DEFAULT_MODULO or UPDATED_MODULO
        defaultDocumentoComercialShouldBeFound("modulo.in=" + DEFAULT_MODULO + "," + UPDATED_MODULO);

        // Get all the documentoComercialList where modulo equals to UPDATED_MODULO
        defaultDocumentoComercialShouldNotBeFound("modulo.in=" + UPDATED_MODULO);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByModuloIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where modulo is not null
        defaultDocumentoComercialShouldBeFound("modulo.specified=true");

        // Get all the documentoComercialList where modulo is null
        defaultDocumentoComercialShouldNotBeFound("modulo.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByOrigemIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where origem equals to DEFAULT_ORIGEM
        defaultDocumentoComercialShouldBeFound("origem.equals=" + DEFAULT_ORIGEM);

        // Get all the documentoComercialList where origem equals to UPDATED_ORIGEM
        defaultDocumentoComercialShouldNotBeFound("origem.equals=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByOrigemIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where origem in DEFAULT_ORIGEM or UPDATED_ORIGEM
        defaultDocumentoComercialShouldBeFound("origem.in=" + DEFAULT_ORIGEM + "," + UPDATED_ORIGEM);

        // Get all the documentoComercialList where origem equals to UPDATED_ORIGEM
        defaultDocumentoComercialShouldNotBeFound("origem.in=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByOrigemIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where origem is not null
        defaultDocumentoComercialShouldBeFound("origem.specified=true");

        // Get all the documentoComercialList where origem is null
        defaultDocumentoComercialShouldNotBeFound("origem.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsBySiglaInternaIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where siglaInterna equals to DEFAULT_SIGLA_INTERNA
        defaultDocumentoComercialShouldBeFound("siglaInterna.equals=" + DEFAULT_SIGLA_INTERNA);

        // Get all the documentoComercialList where siglaInterna equals to UPDATED_SIGLA_INTERNA
        defaultDocumentoComercialShouldNotBeFound("siglaInterna.equals=" + UPDATED_SIGLA_INTERNA);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsBySiglaInternaIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where siglaInterna in DEFAULT_SIGLA_INTERNA or UPDATED_SIGLA_INTERNA
        defaultDocumentoComercialShouldBeFound("siglaInterna.in=" + DEFAULT_SIGLA_INTERNA + "," + UPDATED_SIGLA_INTERNA);

        // Get all the documentoComercialList where siglaInterna equals to UPDATED_SIGLA_INTERNA
        defaultDocumentoComercialShouldNotBeFound("siglaInterna.in=" + UPDATED_SIGLA_INTERNA);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsBySiglaInternaIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where siglaInterna is not null
        defaultDocumentoComercialShouldBeFound("siglaInterna.specified=true");

        // Get all the documentoComercialList where siglaInterna is null
        defaultDocumentoComercialShouldNotBeFound("siglaInterna.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsBySiglaInternaContainsSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where siglaInterna contains DEFAULT_SIGLA_INTERNA
        defaultDocumentoComercialShouldBeFound("siglaInterna.contains=" + DEFAULT_SIGLA_INTERNA);

        // Get all the documentoComercialList where siglaInterna contains UPDATED_SIGLA_INTERNA
        defaultDocumentoComercialShouldNotBeFound("siglaInterna.contains=" + UPDATED_SIGLA_INTERNA);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsBySiglaInternaNotContainsSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where siglaInterna does not contain DEFAULT_SIGLA_INTERNA
        defaultDocumentoComercialShouldNotBeFound("siglaInterna.doesNotContain=" + DEFAULT_SIGLA_INTERNA);

        // Get all the documentoComercialList where siglaInterna does not contain UPDATED_SIGLA_INTERNA
        defaultDocumentoComercialShouldBeFound("siglaInterna.doesNotContain=" + UPDATED_SIGLA_INTERNA);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where descricao equals to DEFAULT_DESCRICAO
        defaultDocumentoComercialShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the documentoComercialList where descricao equals to UPDATED_DESCRICAO
        defaultDocumentoComercialShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultDocumentoComercialShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the documentoComercialList where descricao equals to UPDATED_DESCRICAO
        defaultDocumentoComercialShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where descricao is not null
        defaultDocumentoComercialShouldBeFound("descricao.specified=true");

        // Get all the documentoComercialList where descricao is null
        defaultDocumentoComercialShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where descricao contains DEFAULT_DESCRICAO
        defaultDocumentoComercialShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the documentoComercialList where descricao contains UPDATED_DESCRICAO
        defaultDocumentoComercialShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where descricao does not contain DEFAULT_DESCRICAO
        defaultDocumentoComercialShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the documentoComercialList where descricao does not contain UPDATED_DESCRICAO
        defaultDocumentoComercialShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsBySiglaFiscalIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where siglaFiscal equals to DEFAULT_SIGLA_FISCAL
        defaultDocumentoComercialShouldBeFound("siglaFiscal.equals=" + DEFAULT_SIGLA_FISCAL);

        // Get all the documentoComercialList where siglaFiscal equals to UPDATED_SIGLA_FISCAL
        defaultDocumentoComercialShouldNotBeFound("siglaFiscal.equals=" + UPDATED_SIGLA_FISCAL);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsBySiglaFiscalIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where siglaFiscal in DEFAULT_SIGLA_FISCAL or UPDATED_SIGLA_FISCAL
        defaultDocumentoComercialShouldBeFound("siglaFiscal.in=" + DEFAULT_SIGLA_FISCAL + "," + UPDATED_SIGLA_FISCAL);

        // Get all the documentoComercialList where siglaFiscal equals to UPDATED_SIGLA_FISCAL
        defaultDocumentoComercialShouldNotBeFound("siglaFiscal.in=" + UPDATED_SIGLA_FISCAL);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsBySiglaFiscalIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where siglaFiscal is not null
        defaultDocumentoComercialShouldBeFound("siglaFiscal.specified=true");

        // Get all the documentoComercialList where siglaFiscal is null
        defaultDocumentoComercialShouldNotBeFound("siglaFiscal.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsMovimentaEstoqueIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isMovimentaEstoque equals to DEFAULT_IS_MOVIMENTA_ESTOQUE
        defaultDocumentoComercialShouldBeFound("isMovimentaEstoque.equals=" + DEFAULT_IS_MOVIMENTA_ESTOQUE);

        // Get all the documentoComercialList where isMovimentaEstoque equals to UPDATED_IS_MOVIMENTA_ESTOQUE
        defaultDocumentoComercialShouldNotBeFound("isMovimentaEstoque.equals=" + UPDATED_IS_MOVIMENTA_ESTOQUE);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsMovimentaEstoqueIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isMovimentaEstoque in DEFAULT_IS_MOVIMENTA_ESTOQUE or UPDATED_IS_MOVIMENTA_ESTOQUE
        defaultDocumentoComercialShouldBeFound(
            "isMovimentaEstoque.in=" + DEFAULT_IS_MOVIMENTA_ESTOQUE + "," + UPDATED_IS_MOVIMENTA_ESTOQUE
        );

        // Get all the documentoComercialList where isMovimentaEstoque equals to UPDATED_IS_MOVIMENTA_ESTOQUE
        defaultDocumentoComercialShouldNotBeFound("isMovimentaEstoque.in=" + UPDATED_IS_MOVIMENTA_ESTOQUE);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsMovimentaEstoqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isMovimentaEstoque is not null
        defaultDocumentoComercialShouldBeFound("isMovimentaEstoque.specified=true");

        // Get all the documentoComercialList where isMovimentaEstoque is null
        defaultDocumentoComercialShouldNotBeFound("isMovimentaEstoque.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsMovimentaCaixaIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isMovimentaCaixa equals to DEFAULT_IS_MOVIMENTA_CAIXA
        defaultDocumentoComercialShouldBeFound("isMovimentaCaixa.equals=" + DEFAULT_IS_MOVIMENTA_CAIXA);

        // Get all the documentoComercialList where isMovimentaCaixa equals to UPDATED_IS_MOVIMENTA_CAIXA
        defaultDocumentoComercialShouldNotBeFound("isMovimentaCaixa.equals=" + UPDATED_IS_MOVIMENTA_CAIXA);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsMovimentaCaixaIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isMovimentaCaixa in DEFAULT_IS_MOVIMENTA_CAIXA or UPDATED_IS_MOVIMENTA_CAIXA
        defaultDocumentoComercialShouldBeFound("isMovimentaCaixa.in=" + DEFAULT_IS_MOVIMENTA_CAIXA + "," + UPDATED_IS_MOVIMENTA_CAIXA);

        // Get all the documentoComercialList where isMovimentaCaixa equals to UPDATED_IS_MOVIMENTA_CAIXA
        defaultDocumentoComercialShouldNotBeFound("isMovimentaCaixa.in=" + UPDATED_IS_MOVIMENTA_CAIXA);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsMovimentaCaixaIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isMovimentaCaixa is not null
        defaultDocumentoComercialShouldBeFound("isMovimentaCaixa.specified=true");

        // Get all the documentoComercialList where isMovimentaCaixa is null
        defaultDocumentoComercialShouldNotBeFound("isMovimentaCaixa.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsNotificaEntidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isNotificaEntidade equals to DEFAULT_IS_NOTIFICA_ENTIDADE
        defaultDocumentoComercialShouldBeFound("isNotificaEntidade.equals=" + DEFAULT_IS_NOTIFICA_ENTIDADE);

        // Get all the documentoComercialList where isNotificaEntidade equals to UPDATED_IS_NOTIFICA_ENTIDADE
        defaultDocumentoComercialShouldNotBeFound("isNotificaEntidade.equals=" + UPDATED_IS_NOTIFICA_ENTIDADE);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsNotificaEntidadeIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isNotificaEntidade in DEFAULT_IS_NOTIFICA_ENTIDADE or UPDATED_IS_NOTIFICA_ENTIDADE
        defaultDocumentoComercialShouldBeFound(
            "isNotificaEntidade.in=" + DEFAULT_IS_NOTIFICA_ENTIDADE + "," + UPDATED_IS_NOTIFICA_ENTIDADE
        );

        // Get all the documentoComercialList where isNotificaEntidade equals to UPDATED_IS_NOTIFICA_ENTIDADE
        defaultDocumentoComercialShouldNotBeFound("isNotificaEntidade.in=" + UPDATED_IS_NOTIFICA_ENTIDADE);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsNotificaEntidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isNotificaEntidade is not null
        defaultDocumentoComercialShouldBeFound("isNotificaEntidade.specified=true");

        // Get all the documentoComercialList where isNotificaEntidade is null
        defaultDocumentoComercialShouldNotBeFound("isNotificaEntidade.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsNotificaGerenteIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isNotificaGerente equals to DEFAULT_IS_NOTIFICA_GERENTE
        defaultDocumentoComercialShouldBeFound("isNotificaGerente.equals=" + DEFAULT_IS_NOTIFICA_GERENTE);

        // Get all the documentoComercialList where isNotificaGerente equals to UPDATED_IS_NOTIFICA_GERENTE
        defaultDocumentoComercialShouldNotBeFound("isNotificaGerente.equals=" + UPDATED_IS_NOTIFICA_GERENTE);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsNotificaGerenteIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isNotificaGerente in DEFAULT_IS_NOTIFICA_GERENTE or UPDATED_IS_NOTIFICA_GERENTE
        defaultDocumentoComercialShouldBeFound("isNotificaGerente.in=" + DEFAULT_IS_NOTIFICA_GERENTE + "," + UPDATED_IS_NOTIFICA_GERENTE);

        // Get all the documentoComercialList where isNotificaGerente equals to UPDATED_IS_NOTIFICA_GERENTE
        defaultDocumentoComercialShouldNotBeFound("isNotificaGerente.in=" + UPDATED_IS_NOTIFICA_GERENTE);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsNotificaGerenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isNotificaGerente is not null
        defaultDocumentoComercialShouldBeFound("isNotificaGerente.specified=true");

        // Get all the documentoComercialList where isNotificaGerente is null
        defaultDocumentoComercialShouldNotBeFound("isNotificaGerente.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsEnviaSMSIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isEnviaSMS equals to DEFAULT_IS_ENVIA_SMS
        defaultDocumentoComercialShouldBeFound("isEnviaSMS.equals=" + DEFAULT_IS_ENVIA_SMS);

        // Get all the documentoComercialList where isEnviaSMS equals to UPDATED_IS_ENVIA_SMS
        defaultDocumentoComercialShouldNotBeFound("isEnviaSMS.equals=" + UPDATED_IS_ENVIA_SMS);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsEnviaSMSIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isEnviaSMS in DEFAULT_IS_ENVIA_SMS or UPDATED_IS_ENVIA_SMS
        defaultDocumentoComercialShouldBeFound("isEnviaSMS.in=" + DEFAULT_IS_ENVIA_SMS + "," + UPDATED_IS_ENVIA_SMS);

        // Get all the documentoComercialList where isEnviaSMS equals to UPDATED_IS_ENVIA_SMS
        defaultDocumentoComercialShouldNotBeFound("isEnviaSMS.in=" + UPDATED_IS_ENVIA_SMS);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsEnviaSMSIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isEnviaSMS is not null
        defaultDocumentoComercialShouldBeFound("isEnviaSMS.specified=true");

        // Get all the documentoComercialList where isEnviaSMS is null
        defaultDocumentoComercialShouldNotBeFound("isEnviaSMS.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsEnviaEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isEnviaEmail equals to DEFAULT_IS_ENVIA_EMAIL
        defaultDocumentoComercialShouldBeFound("isEnviaEmail.equals=" + DEFAULT_IS_ENVIA_EMAIL);

        // Get all the documentoComercialList where isEnviaEmail equals to UPDATED_IS_ENVIA_EMAIL
        defaultDocumentoComercialShouldNotBeFound("isEnviaEmail.equals=" + UPDATED_IS_ENVIA_EMAIL);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsEnviaEmailIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isEnviaEmail in DEFAULT_IS_ENVIA_EMAIL or UPDATED_IS_ENVIA_EMAIL
        defaultDocumentoComercialShouldBeFound("isEnviaEmail.in=" + DEFAULT_IS_ENVIA_EMAIL + "," + UPDATED_IS_ENVIA_EMAIL);

        // Get all the documentoComercialList where isEnviaEmail equals to UPDATED_IS_ENVIA_EMAIL
        defaultDocumentoComercialShouldNotBeFound("isEnviaEmail.in=" + UPDATED_IS_ENVIA_EMAIL);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsEnviaEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isEnviaEmail is not null
        defaultDocumentoComercialShouldBeFound("isEnviaEmail.specified=true");

        // Get all the documentoComercialList where isEnviaEmail is null
        defaultDocumentoComercialShouldNotBeFound("isEnviaEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsEnviaPushIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isEnviaPush equals to DEFAULT_IS_ENVIA_PUSH
        defaultDocumentoComercialShouldBeFound("isEnviaPush.equals=" + DEFAULT_IS_ENVIA_PUSH);

        // Get all the documentoComercialList where isEnviaPush equals to UPDATED_IS_ENVIA_PUSH
        defaultDocumentoComercialShouldNotBeFound("isEnviaPush.equals=" + UPDATED_IS_ENVIA_PUSH);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsEnviaPushIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isEnviaPush in DEFAULT_IS_ENVIA_PUSH or UPDATED_IS_ENVIA_PUSH
        defaultDocumentoComercialShouldBeFound("isEnviaPush.in=" + DEFAULT_IS_ENVIA_PUSH + "," + UPDATED_IS_ENVIA_PUSH);

        // Get all the documentoComercialList where isEnviaPush equals to UPDATED_IS_ENVIA_PUSH
        defaultDocumentoComercialShouldNotBeFound("isEnviaPush.in=" + UPDATED_IS_ENVIA_PUSH);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByIsEnviaPushIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where isEnviaPush is not null
        defaultDocumentoComercialShouldBeFound("isEnviaPush.specified=true");

        // Get all the documentoComercialList where isEnviaPush is null
        defaultDocumentoComercialShouldNotBeFound("isEnviaPush.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByValidaCreditoDisponivelIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where validaCreditoDisponivel equals to DEFAULT_VALIDA_CREDITO_DISPONIVEL
        defaultDocumentoComercialShouldBeFound("validaCreditoDisponivel.equals=" + DEFAULT_VALIDA_CREDITO_DISPONIVEL);

        // Get all the documentoComercialList where validaCreditoDisponivel equals to UPDATED_VALIDA_CREDITO_DISPONIVEL
        defaultDocumentoComercialShouldNotBeFound("validaCreditoDisponivel.equals=" + UPDATED_VALIDA_CREDITO_DISPONIVEL);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByValidaCreditoDisponivelIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where validaCreditoDisponivel in DEFAULT_VALIDA_CREDITO_DISPONIVEL or UPDATED_VALIDA_CREDITO_DISPONIVEL
        defaultDocumentoComercialShouldBeFound(
            "validaCreditoDisponivel.in=" + DEFAULT_VALIDA_CREDITO_DISPONIVEL + "," + UPDATED_VALIDA_CREDITO_DISPONIVEL
        );

        // Get all the documentoComercialList where validaCreditoDisponivel equals to UPDATED_VALIDA_CREDITO_DISPONIVEL
        defaultDocumentoComercialShouldNotBeFound("validaCreditoDisponivel.in=" + UPDATED_VALIDA_CREDITO_DISPONIVEL);
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByValidaCreditoDisponivelIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where validaCreditoDisponivel is not null
        defaultDocumentoComercialShouldBeFound("validaCreditoDisponivel.specified=true");

        // Get all the documentoComercialList where validaCreditoDisponivel is null
        defaultDocumentoComercialShouldNotBeFound("validaCreditoDisponivel.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsBySerieDocumentoIsEqualToSomething() throws Exception {
        SerieDocumento serieDocumento;
        if (TestUtil.findAll(em, SerieDocumento.class).isEmpty()) {
            documentoComercialRepository.saveAndFlush(documentoComercial);
            serieDocumento = SerieDocumentoResourceIT.createEntity(em);
        } else {
            serieDocumento = TestUtil.findAll(em, SerieDocumento.class).get(0);
        }
        em.persist(serieDocumento);
        em.flush();
        documentoComercial.addSerieDocumento(serieDocumento);
        documentoComercialRepository.saveAndFlush(documentoComercial);
        Long serieDocumentoId = serieDocumento.getId();

        // Get all the documentoComercialList where serieDocumento equals to serieDocumentoId
        defaultDocumentoComercialShouldBeFound("serieDocumentoId.equals=" + serieDocumentoId);

        // Get all the documentoComercialList where serieDocumento equals to (serieDocumentoId + 1)
        defaultDocumentoComercialShouldNotBeFound("serieDocumentoId.equals=" + (serieDocumentoId + 1));
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByFacturaIsEqualToSomething() throws Exception {
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            documentoComercialRepository.saveAndFlush(documentoComercial);
            factura = FacturaResourceIT.createEntity(em);
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        em.persist(factura);
        em.flush();
        documentoComercial.addFactura(factura);
        documentoComercialRepository.saveAndFlush(documentoComercial);
        Long facturaId = factura.getId();

        // Get all the documentoComercialList where factura equals to facturaId
        defaultDocumentoComercialShouldBeFound("facturaId.equals=" + facturaId);

        // Get all the documentoComercialList where factura equals to (facturaId + 1)
        defaultDocumentoComercialShouldNotBeFound("facturaId.equals=" + (facturaId + 1));
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByReciboIsEqualToSomething() throws Exception {
        Recibo recibo;
        if (TestUtil.findAll(em, Recibo.class).isEmpty()) {
            documentoComercialRepository.saveAndFlush(documentoComercial);
            recibo = ReciboResourceIT.createEntity(em);
        } else {
            recibo = TestUtil.findAll(em, Recibo.class).get(0);
        }
        em.persist(recibo);
        em.flush();
        documentoComercial.addRecibo(recibo);
        documentoComercialRepository.saveAndFlush(documentoComercial);
        Long reciboId = recibo.getId();

        // Get all the documentoComercialList where recibo equals to reciboId
        defaultDocumentoComercialShouldBeFound("reciboId.equals=" + reciboId);

        // Get all the documentoComercialList where recibo equals to (reciboId + 1)
        defaultDocumentoComercialShouldNotBeFound("reciboId.equals=" + (reciboId + 1));
    }

    @Test
    @Transactional
    void getAllDocumentoComercialsByTransformaEmIsEqualToSomething() throws Exception {
        DocumentoComercial transformaEm;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercialRepository.saveAndFlush(documentoComercial);
            transformaEm = DocumentoComercialResourceIT.createEntity(em);
        } else {
            transformaEm = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        em.persist(transformaEm);
        em.flush();
        documentoComercial.setTransformaEm(transformaEm);
        documentoComercialRepository.saveAndFlush(documentoComercial);
        Long transformaEmId = transformaEm.getId();

        // Get all the documentoComercialList where transformaEm equals to transformaEmId
        defaultDocumentoComercialShouldBeFound("transformaEmId.equals=" + transformaEmId);

        // Get all the documentoComercialList where transformaEm equals to (transformaEmId + 1)
        defaultDocumentoComercialShouldNotBeFound("transformaEmId.equals=" + (transformaEmId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentoComercialShouldBeFound(String filter) throws Exception {
        restDocumentoComercialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentoComercial.getId().intValue())))
            .andExpect(jsonPath("$.[*].modulo").value(hasItem(DEFAULT_MODULO.toString())))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM.toString())))
            .andExpect(jsonPath("$.[*].siglaInterna").value(hasItem(DEFAULT_SIGLA_INTERNA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].siglaFiscal").value(hasItem(DEFAULT_SIGLA_FISCAL.toString())))
            .andExpect(jsonPath("$.[*].isMovimentaEstoque").value(hasItem(DEFAULT_IS_MOVIMENTA_ESTOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].isMovimentaCaixa").value(hasItem(DEFAULT_IS_MOVIMENTA_CAIXA.booleanValue())))
            .andExpect(jsonPath("$.[*].isNotificaEntidade").value(hasItem(DEFAULT_IS_NOTIFICA_ENTIDADE.booleanValue())))
            .andExpect(jsonPath("$.[*].isNotificaGerente").value(hasItem(DEFAULT_IS_NOTIFICA_GERENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].isEnviaSMS").value(hasItem(DEFAULT_IS_ENVIA_SMS.booleanValue())))
            .andExpect(jsonPath("$.[*].isEnviaEmail").value(hasItem(DEFAULT_IS_ENVIA_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].isEnviaPush").value(hasItem(DEFAULT_IS_ENVIA_PUSH.booleanValue())))
            .andExpect(jsonPath("$.[*].validaCreditoDisponivel").value(hasItem(DEFAULT_VALIDA_CREDITO_DISPONIVEL.booleanValue())));

        // Check, that the count call also returns 1
        restDocumentoComercialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentoComercialShouldNotBeFound(String filter) throws Exception {
        restDocumentoComercialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentoComercialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocumentoComercial() throws Exception {
        // Get the documentoComercial
        restDocumentoComercialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentoComercial() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        int databaseSizeBeforeUpdate = documentoComercialRepository.findAll().size();

        // Update the documentoComercial
        DocumentoComercial updatedDocumentoComercial = documentoComercialRepository.findById(documentoComercial.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentoComercial are not directly saved in db
        em.detach(updatedDocumentoComercial);
        updatedDocumentoComercial
            .modulo(UPDATED_MODULO)
            .origem(UPDATED_ORIGEM)
            .siglaInterna(UPDATED_SIGLA_INTERNA)
            .descricao(UPDATED_DESCRICAO)
            .siglaFiscal(UPDATED_SIGLA_FISCAL)
            .isMovimentaEstoque(UPDATED_IS_MOVIMENTA_ESTOQUE)
            .isMovimentaCaixa(UPDATED_IS_MOVIMENTA_CAIXA)
            .isNotificaEntidade(UPDATED_IS_NOTIFICA_ENTIDADE)
            .isNotificaGerente(UPDATED_IS_NOTIFICA_GERENTE)
            .isEnviaSMS(UPDATED_IS_ENVIA_SMS)
            .isEnviaEmail(UPDATED_IS_ENVIA_EMAIL)
            .isEnviaPush(UPDATED_IS_ENVIA_PUSH)
            .validaCreditoDisponivel(UPDATED_VALIDA_CREDITO_DISPONIVEL);
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(updatedDocumentoComercial);

        restDocumentoComercialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentoComercialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isOk());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeUpdate);
        DocumentoComercial testDocumentoComercial = documentoComercialList.get(documentoComercialList.size() - 1);
        assertThat(testDocumentoComercial.getModulo()).isEqualTo(UPDATED_MODULO);
        assertThat(testDocumentoComercial.getOrigem()).isEqualTo(UPDATED_ORIGEM);
        assertThat(testDocumentoComercial.getSiglaInterna()).isEqualTo(UPDATED_SIGLA_INTERNA);
        assertThat(testDocumentoComercial.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDocumentoComercial.getSiglaFiscal()).isEqualTo(UPDATED_SIGLA_FISCAL);
        assertThat(testDocumentoComercial.getIsMovimentaEstoque()).isEqualTo(UPDATED_IS_MOVIMENTA_ESTOQUE);
        assertThat(testDocumentoComercial.getIsMovimentaCaixa()).isEqualTo(UPDATED_IS_MOVIMENTA_CAIXA);
        assertThat(testDocumentoComercial.getIsNotificaEntidade()).isEqualTo(UPDATED_IS_NOTIFICA_ENTIDADE);
        assertThat(testDocumentoComercial.getIsNotificaGerente()).isEqualTo(UPDATED_IS_NOTIFICA_GERENTE);
        assertThat(testDocumentoComercial.getIsEnviaSMS()).isEqualTo(UPDATED_IS_ENVIA_SMS);
        assertThat(testDocumentoComercial.getIsEnviaEmail()).isEqualTo(UPDATED_IS_ENVIA_EMAIL);
        assertThat(testDocumentoComercial.getIsEnviaPush()).isEqualTo(UPDATED_IS_ENVIA_PUSH);
        assertThat(testDocumentoComercial.getValidaCreditoDisponivel()).isEqualTo(UPDATED_VALIDA_CREDITO_DISPONIVEL);
    }

    @Test
    @Transactional
    void putNonExistingDocumentoComercial() throws Exception {
        int databaseSizeBeforeUpdate = documentoComercialRepository.findAll().size();
        documentoComercial.setId(count.incrementAndGet());

        // Create the DocumentoComercial
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoComercialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentoComercialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentoComercial() throws Exception {
        int databaseSizeBeforeUpdate = documentoComercialRepository.findAll().size();
        documentoComercial.setId(count.incrementAndGet());

        // Create the DocumentoComercial
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoComercialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentoComercial() throws Exception {
        int databaseSizeBeforeUpdate = documentoComercialRepository.findAll().size();
        documentoComercial.setId(count.incrementAndGet());

        // Create the DocumentoComercial
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoComercialMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentoComercialWithPatch() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        int databaseSizeBeforeUpdate = documentoComercialRepository.findAll().size();

        // Update the documentoComercial using partial update
        DocumentoComercial partialUpdatedDocumentoComercial = new DocumentoComercial();
        partialUpdatedDocumentoComercial.setId(documentoComercial.getId());

        partialUpdatedDocumentoComercial
            .modulo(UPDATED_MODULO)
            .siglaFiscal(UPDATED_SIGLA_FISCAL)
            .isMovimentaEstoque(UPDATED_IS_MOVIMENTA_ESTOQUE)
            .isNotificaGerente(UPDATED_IS_NOTIFICA_GERENTE)
            .isEnviaEmail(UPDATED_IS_ENVIA_EMAIL)
            .isEnviaPush(UPDATED_IS_ENVIA_PUSH);

        restDocumentoComercialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentoComercial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentoComercial))
            )
            .andExpect(status().isOk());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeUpdate);
        DocumentoComercial testDocumentoComercial = documentoComercialList.get(documentoComercialList.size() - 1);
        assertThat(testDocumentoComercial.getModulo()).isEqualTo(UPDATED_MODULO);
        assertThat(testDocumentoComercial.getOrigem()).isEqualTo(DEFAULT_ORIGEM);
        assertThat(testDocumentoComercial.getSiglaInterna()).isEqualTo(DEFAULT_SIGLA_INTERNA);
        assertThat(testDocumentoComercial.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDocumentoComercial.getSiglaFiscal()).isEqualTo(UPDATED_SIGLA_FISCAL);
        assertThat(testDocumentoComercial.getIsMovimentaEstoque()).isEqualTo(UPDATED_IS_MOVIMENTA_ESTOQUE);
        assertThat(testDocumentoComercial.getIsMovimentaCaixa()).isEqualTo(DEFAULT_IS_MOVIMENTA_CAIXA);
        assertThat(testDocumentoComercial.getIsNotificaEntidade()).isEqualTo(DEFAULT_IS_NOTIFICA_ENTIDADE);
        assertThat(testDocumentoComercial.getIsNotificaGerente()).isEqualTo(UPDATED_IS_NOTIFICA_GERENTE);
        assertThat(testDocumentoComercial.getIsEnviaSMS()).isEqualTo(DEFAULT_IS_ENVIA_SMS);
        assertThat(testDocumentoComercial.getIsEnviaEmail()).isEqualTo(UPDATED_IS_ENVIA_EMAIL);
        assertThat(testDocumentoComercial.getIsEnviaPush()).isEqualTo(UPDATED_IS_ENVIA_PUSH);
        assertThat(testDocumentoComercial.getValidaCreditoDisponivel()).isEqualTo(DEFAULT_VALIDA_CREDITO_DISPONIVEL);
    }

    @Test
    @Transactional
    void fullUpdateDocumentoComercialWithPatch() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        int databaseSizeBeforeUpdate = documentoComercialRepository.findAll().size();

        // Update the documentoComercial using partial update
        DocumentoComercial partialUpdatedDocumentoComercial = new DocumentoComercial();
        partialUpdatedDocumentoComercial.setId(documentoComercial.getId());

        partialUpdatedDocumentoComercial
            .modulo(UPDATED_MODULO)
            .origem(UPDATED_ORIGEM)
            .siglaInterna(UPDATED_SIGLA_INTERNA)
            .descricao(UPDATED_DESCRICAO)
            .siglaFiscal(UPDATED_SIGLA_FISCAL)
            .isMovimentaEstoque(UPDATED_IS_MOVIMENTA_ESTOQUE)
            .isMovimentaCaixa(UPDATED_IS_MOVIMENTA_CAIXA)
            .isNotificaEntidade(UPDATED_IS_NOTIFICA_ENTIDADE)
            .isNotificaGerente(UPDATED_IS_NOTIFICA_GERENTE)
            .isEnviaSMS(UPDATED_IS_ENVIA_SMS)
            .isEnviaEmail(UPDATED_IS_ENVIA_EMAIL)
            .isEnviaPush(UPDATED_IS_ENVIA_PUSH)
            .validaCreditoDisponivel(UPDATED_VALIDA_CREDITO_DISPONIVEL);

        restDocumentoComercialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentoComercial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentoComercial))
            )
            .andExpect(status().isOk());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeUpdate);
        DocumentoComercial testDocumentoComercial = documentoComercialList.get(documentoComercialList.size() - 1);
        assertThat(testDocumentoComercial.getModulo()).isEqualTo(UPDATED_MODULO);
        assertThat(testDocumentoComercial.getOrigem()).isEqualTo(UPDATED_ORIGEM);
        assertThat(testDocumentoComercial.getSiglaInterna()).isEqualTo(UPDATED_SIGLA_INTERNA);
        assertThat(testDocumentoComercial.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDocumentoComercial.getSiglaFiscal()).isEqualTo(UPDATED_SIGLA_FISCAL);
        assertThat(testDocumentoComercial.getIsMovimentaEstoque()).isEqualTo(UPDATED_IS_MOVIMENTA_ESTOQUE);
        assertThat(testDocumentoComercial.getIsMovimentaCaixa()).isEqualTo(UPDATED_IS_MOVIMENTA_CAIXA);
        assertThat(testDocumentoComercial.getIsNotificaEntidade()).isEqualTo(UPDATED_IS_NOTIFICA_ENTIDADE);
        assertThat(testDocumentoComercial.getIsNotificaGerente()).isEqualTo(UPDATED_IS_NOTIFICA_GERENTE);
        assertThat(testDocumentoComercial.getIsEnviaSMS()).isEqualTo(UPDATED_IS_ENVIA_SMS);
        assertThat(testDocumentoComercial.getIsEnviaEmail()).isEqualTo(UPDATED_IS_ENVIA_EMAIL);
        assertThat(testDocumentoComercial.getIsEnviaPush()).isEqualTo(UPDATED_IS_ENVIA_PUSH);
        assertThat(testDocumentoComercial.getValidaCreditoDisponivel()).isEqualTo(UPDATED_VALIDA_CREDITO_DISPONIVEL);
    }

    @Test
    @Transactional
    void patchNonExistingDocumentoComercial() throws Exception {
        int databaseSizeBeforeUpdate = documentoComercialRepository.findAll().size();
        documentoComercial.setId(count.incrementAndGet());

        // Create the DocumentoComercial
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoComercialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentoComercialDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentoComercial() throws Exception {
        int databaseSizeBeforeUpdate = documentoComercialRepository.findAll().size();
        documentoComercial.setId(count.incrementAndGet());

        // Create the DocumentoComercial
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoComercialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentoComercial() throws Exception {
        int databaseSizeBeforeUpdate = documentoComercialRepository.findAll().size();
        documentoComercial.setId(count.incrementAndGet());

        // Create the DocumentoComercial
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoComercialMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentoComercial() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        int databaseSizeBeforeDelete = documentoComercialRepository.findAll().size();

        // Delete the documentoComercial
        restDocumentoComercialMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentoComercial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
