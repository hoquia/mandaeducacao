package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static com.ravunana.longonkelo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Conta;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.MeioPagamento;
import com.ravunana.longonkelo.domain.Recibo;
import com.ravunana.longonkelo.domain.Transacao;
import com.ravunana.longonkelo.domain.TransferenciaSaldo;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.domain.enumeration.EstadoPagamento;
import com.ravunana.longonkelo.repository.TransacaoRepository;
import com.ravunana.longonkelo.service.TransacaoService;
import com.ravunana.longonkelo.service.criteria.TransacaoCriteria;
import com.ravunana.longonkelo.service.dto.TransacaoDTO;
import com.ravunana.longonkelo.service.mapper.TransacaoMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link TransacaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TransacaoResourceIT {

    private static final BigDecimal DEFAULT_MONTANTE = new BigDecimal(0);
    private static final BigDecimal UPDATED_MONTANTE = new BigDecimal(1);
    private static final BigDecimal SMALLER_MONTANTE = new BigDecimal(0 - 1);

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCIA = "BBBBBBBBBB";

    private static final EstadoPagamento DEFAULT_ESTADO = EstadoPagamento.VALIDO;
    private static final EstadoPagamento UPDATED_ESTADO = EstadoPagamento.REJEITADO;

    private static final BigDecimal DEFAULT_SALDO = new BigDecimal(0);
    private static final BigDecimal UPDATED_SALDO = new BigDecimal(1);
    private static final BigDecimal SMALLER_SALDO = new BigDecimal(0 - 1);

    private static final byte[] DEFAULT_ANEXO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANEXO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ANEXO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANEXO_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/transacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Mock
    private TransacaoRepository transacaoRepositoryMock;

    @Autowired
    private TransacaoMapper transacaoMapper;

    @Mock
    private TransacaoService transacaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransacaoMockMvc;

    private Transacao transacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transacao createEntity(EntityManager em) {
        Transacao transacao = new Transacao()
            .montante(DEFAULT_MONTANTE)
            .data(DEFAULT_DATA)
            .referencia(DEFAULT_REFERENCIA)
            .estado(DEFAULT_ESTADO)
            .saldo(DEFAULT_SALDO)
            .anexo(DEFAULT_ANEXO)
            .anexoContentType(DEFAULT_ANEXO_CONTENT_TYPE)
            .timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        transacao.setMatricula(matricula);
        // Add required entity
        MeioPagamento meioPagamento;
        if (TestUtil.findAll(em, MeioPagamento.class).isEmpty()) {
            meioPagamento = MeioPagamentoResourceIT.createEntity(em);
            em.persist(meioPagamento);
            em.flush();
        } else {
            meioPagamento = TestUtil.findAll(em, MeioPagamento.class).get(0);
        }
        transacao.setMeioPagamento(meioPagamento);
        // Add required entity
        Conta conta;
        if (TestUtil.findAll(em, Conta.class).isEmpty()) {
            conta = ContaResourceIT.createEntity(em);
            em.persist(conta);
            em.flush();
        } else {
            conta = TestUtil.findAll(em, Conta.class).get(0);
        }
        transacao.setConta(conta);
        return transacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transacao createUpdatedEntity(EntityManager em) {
        Transacao transacao = new Transacao()
            .montante(UPDATED_MONTANTE)
            .data(UPDATED_DATA)
            .referencia(UPDATED_REFERENCIA)
            .estado(UPDATED_ESTADO)
            .saldo(UPDATED_SALDO)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createUpdatedEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        transacao.setMatricula(matricula);
        // Add required entity
        MeioPagamento meioPagamento;
        if (TestUtil.findAll(em, MeioPagamento.class).isEmpty()) {
            meioPagamento = MeioPagamentoResourceIT.createUpdatedEntity(em);
            em.persist(meioPagamento);
            em.flush();
        } else {
            meioPagamento = TestUtil.findAll(em, MeioPagamento.class).get(0);
        }
        transacao.setMeioPagamento(meioPagamento);
        // Add required entity
        Conta conta;
        if (TestUtil.findAll(em, Conta.class).isEmpty()) {
            conta = ContaResourceIT.createUpdatedEntity(em);
            em.persist(conta);
            em.flush();
        } else {
            conta = TestUtil.findAll(em, Conta.class).get(0);
        }
        transacao.setConta(conta);
        return transacao;
    }

    @BeforeEach
    public void initTest() {
        transacao = createEntity(em);
    }

    @Test
    @Transactional
    void createTransacao() throws Exception {
        int databaseSizeBeforeCreate = transacaoRepository.findAll().size();
        // Create the Transacao
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);
        restTransacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Transacao testTransacao = transacaoList.get(transacaoList.size() - 1);
        assertThat(testTransacao.getMontante()).isEqualByComparingTo(DEFAULT_MONTANTE);
        assertThat(testTransacao.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testTransacao.getReferencia()).isEqualTo(DEFAULT_REFERENCIA);
        assertThat(testTransacao.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testTransacao.getSaldo()).isEqualByComparingTo(DEFAULT_SALDO);
        assertThat(testTransacao.getAnexo()).isEqualTo(DEFAULT_ANEXO);
        assertThat(testTransacao.getAnexoContentType()).isEqualTo(DEFAULT_ANEXO_CONTENT_TYPE);
        assertThat(testTransacao.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createTransacaoWithExistingId() throws Exception {
        // Create the Transacao with an existing ID
        transacao.setId(1L);
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        int databaseSizeBeforeCreate = transacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMontanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = transacaoRepository.findAll().size();
        // set the field null
        transacao.setMontante(null);

        // Create the Transacao, which fails.
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        restTransacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = transacaoRepository.findAll().size();
        // set the field null
        transacao.setData(null);

        // Create the Transacao, which fails.
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        restTransacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReferenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = transacaoRepository.findAll().size();
        // set the field null
        transacao.setReferencia(null);

        // Create the Transacao, which fails.
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        restTransacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = transacaoRepository.findAll().size();
        // set the field null
        transacao.setEstado(null);

        // Create the Transacao, which fails.
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        restTransacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSaldoIsRequired() throws Exception {
        int databaseSizeBeforeTest = transacaoRepository.findAll().size();
        // set the field null
        transacao.setSaldo(null);

        // Create the Transacao, which fails.
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        restTransacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransacaos() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList
        restTransacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].montante").value(hasItem(sameNumber(DEFAULT_MONTANTE))))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].referencia").value(hasItem(DEFAULT_REFERENCIA)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(sameNumber(DEFAULT_SALDO))))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTransacaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(transacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTransacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(transacaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTransacaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(transacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTransacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(transacaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTransacao() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get the transacao
        restTransacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, transacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transacao.getId().intValue()))
            .andExpect(jsonPath("$.montante").value(sameNumber(DEFAULT_MONTANTE)))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.referencia").value(DEFAULT_REFERENCIA))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.saldo").value(sameNumber(DEFAULT_SALDO)))
            .andExpect(jsonPath("$.anexoContentType").value(DEFAULT_ANEXO_CONTENT_TYPE))
            .andExpect(jsonPath("$.anexo").value(Base64Utils.encodeToString(DEFAULT_ANEXO)))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getTransacaosByIdFiltering() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        Long id = transacao.getId();

        defaultTransacaoShouldBeFound("id.equals=" + id);
        defaultTransacaoShouldNotBeFound("id.notEquals=" + id);

        defaultTransacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultTransacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransacaoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransacaosByMontanteIsEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where montante equals to DEFAULT_MONTANTE
        defaultTransacaoShouldBeFound("montante.equals=" + DEFAULT_MONTANTE);

        // Get all the transacaoList where montante equals to UPDATED_MONTANTE
        defaultTransacaoShouldNotBeFound("montante.equals=" + UPDATED_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransacaosByMontanteIsInShouldWork() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where montante in DEFAULT_MONTANTE or UPDATED_MONTANTE
        defaultTransacaoShouldBeFound("montante.in=" + DEFAULT_MONTANTE + "," + UPDATED_MONTANTE);

        // Get all the transacaoList where montante equals to UPDATED_MONTANTE
        defaultTransacaoShouldNotBeFound("montante.in=" + UPDATED_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransacaosByMontanteIsNullOrNotNull() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where montante is not null
        defaultTransacaoShouldBeFound("montante.specified=true");

        // Get all the transacaoList where montante is null
        defaultTransacaoShouldNotBeFound("montante.specified=false");
    }

    @Test
    @Transactional
    void getAllTransacaosByMontanteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where montante is greater than or equal to DEFAULT_MONTANTE
        defaultTransacaoShouldBeFound("montante.greaterThanOrEqual=" + DEFAULT_MONTANTE);

        // Get all the transacaoList where montante is greater than or equal to UPDATED_MONTANTE
        defaultTransacaoShouldNotBeFound("montante.greaterThanOrEqual=" + UPDATED_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransacaosByMontanteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where montante is less than or equal to DEFAULT_MONTANTE
        defaultTransacaoShouldBeFound("montante.lessThanOrEqual=" + DEFAULT_MONTANTE);

        // Get all the transacaoList where montante is less than or equal to SMALLER_MONTANTE
        defaultTransacaoShouldNotBeFound("montante.lessThanOrEqual=" + SMALLER_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransacaosByMontanteIsLessThanSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where montante is less than DEFAULT_MONTANTE
        defaultTransacaoShouldNotBeFound("montante.lessThan=" + DEFAULT_MONTANTE);

        // Get all the transacaoList where montante is less than UPDATED_MONTANTE
        defaultTransacaoShouldBeFound("montante.lessThan=" + UPDATED_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransacaosByMontanteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where montante is greater than DEFAULT_MONTANTE
        defaultTransacaoShouldNotBeFound("montante.greaterThan=" + DEFAULT_MONTANTE);

        // Get all the transacaoList where montante is greater than SMALLER_MONTANTE
        defaultTransacaoShouldBeFound("montante.greaterThan=" + SMALLER_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransacaosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where data equals to DEFAULT_DATA
        defaultTransacaoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the transacaoList where data equals to UPDATED_DATA
        defaultTransacaoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllTransacaosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultTransacaoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the transacaoList where data equals to UPDATED_DATA
        defaultTransacaoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllTransacaosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where data is not null
        defaultTransacaoShouldBeFound("data.specified=true");

        // Get all the transacaoList where data is null
        defaultTransacaoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    void getAllTransacaosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where data is greater than or equal to DEFAULT_DATA
        defaultTransacaoShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the transacaoList where data is greater than or equal to UPDATED_DATA
        defaultTransacaoShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllTransacaosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where data is less than or equal to DEFAULT_DATA
        defaultTransacaoShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the transacaoList where data is less than or equal to SMALLER_DATA
        defaultTransacaoShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllTransacaosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where data is less than DEFAULT_DATA
        defaultTransacaoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the transacaoList where data is less than UPDATED_DATA
        defaultTransacaoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllTransacaosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where data is greater than DEFAULT_DATA
        defaultTransacaoShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the transacaoList where data is greater than SMALLER_DATA
        defaultTransacaoShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllTransacaosByReferenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where referencia equals to DEFAULT_REFERENCIA
        defaultTransacaoShouldBeFound("referencia.equals=" + DEFAULT_REFERENCIA);

        // Get all the transacaoList where referencia equals to UPDATED_REFERENCIA
        defaultTransacaoShouldNotBeFound("referencia.equals=" + UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllTransacaosByReferenciaIsInShouldWork() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where referencia in DEFAULT_REFERENCIA or UPDATED_REFERENCIA
        defaultTransacaoShouldBeFound("referencia.in=" + DEFAULT_REFERENCIA + "," + UPDATED_REFERENCIA);

        // Get all the transacaoList where referencia equals to UPDATED_REFERENCIA
        defaultTransacaoShouldNotBeFound("referencia.in=" + UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllTransacaosByReferenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where referencia is not null
        defaultTransacaoShouldBeFound("referencia.specified=true");

        // Get all the transacaoList where referencia is null
        defaultTransacaoShouldNotBeFound("referencia.specified=false");
    }

    @Test
    @Transactional
    void getAllTransacaosByReferenciaContainsSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where referencia contains DEFAULT_REFERENCIA
        defaultTransacaoShouldBeFound("referencia.contains=" + DEFAULT_REFERENCIA);

        // Get all the transacaoList where referencia contains UPDATED_REFERENCIA
        defaultTransacaoShouldNotBeFound("referencia.contains=" + UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllTransacaosByReferenciaNotContainsSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where referencia does not contain DEFAULT_REFERENCIA
        defaultTransacaoShouldNotBeFound("referencia.doesNotContain=" + DEFAULT_REFERENCIA);

        // Get all the transacaoList where referencia does not contain UPDATED_REFERENCIA
        defaultTransacaoShouldBeFound("referencia.doesNotContain=" + UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllTransacaosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where estado equals to DEFAULT_ESTADO
        defaultTransacaoShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the transacaoList where estado equals to UPDATED_ESTADO
        defaultTransacaoShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllTransacaosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultTransacaoShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the transacaoList where estado equals to UPDATED_ESTADO
        defaultTransacaoShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllTransacaosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where estado is not null
        defaultTransacaoShouldBeFound("estado.specified=true");

        // Get all the transacaoList where estado is null
        defaultTransacaoShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllTransacaosBySaldoIsEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where saldo equals to DEFAULT_SALDO
        defaultTransacaoShouldBeFound("saldo.equals=" + DEFAULT_SALDO);

        // Get all the transacaoList where saldo equals to UPDATED_SALDO
        defaultTransacaoShouldNotBeFound("saldo.equals=" + UPDATED_SALDO);
    }

    @Test
    @Transactional
    void getAllTransacaosBySaldoIsInShouldWork() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where saldo in DEFAULT_SALDO or UPDATED_SALDO
        defaultTransacaoShouldBeFound("saldo.in=" + DEFAULT_SALDO + "," + UPDATED_SALDO);

        // Get all the transacaoList where saldo equals to UPDATED_SALDO
        defaultTransacaoShouldNotBeFound("saldo.in=" + UPDATED_SALDO);
    }

    @Test
    @Transactional
    void getAllTransacaosBySaldoIsNullOrNotNull() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where saldo is not null
        defaultTransacaoShouldBeFound("saldo.specified=true");

        // Get all the transacaoList where saldo is null
        defaultTransacaoShouldNotBeFound("saldo.specified=false");
    }

    @Test
    @Transactional
    void getAllTransacaosBySaldoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where saldo is greater than or equal to DEFAULT_SALDO
        defaultTransacaoShouldBeFound("saldo.greaterThanOrEqual=" + DEFAULT_SALDO);

        // Get all the transacaoList where saldo is greater than or equal to UPDATED_SALDO
        defaultTransacaoShouldNotBeFound("saldo.greaterThanOrEqual=" + UPDATED_SALDO);
    }

    @Test
    @Transactional
    void getAllTransacaosBySaldoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where saldo is less than or equal to DEFAULT_SALDO
        defaultTransacaoShouldBeFound("saldo.lessThanOrEqual=" + DEFAULT_SALDO);

        // Get all the transacaoList where saldo is less than or equal to SMALLER_SALDO
        defaultTransacaoShouldNotBeFound("saldo.lessThanOrEqual=" + SMALLER_SALDO);
    }

    @Test
    @Transactional
    void getAllTransacaosBySaldoIsLessThanSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where saldo is less than DEFAULT_SALDO
        defaultTransacaoShouldNotBeFound("saldo.lessThan=" + DEFAULT_SALDO);

        // Get all the transacaoList where saldo is less than UPDATED_SALDO
        defaultTransacaoShouldBeFound("saldo.lessThan=" + UPDATED_SALDO);
    }

    @Test
    @Transactional
    void getAllTransacaosBySaldoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where saldo is greater than DEFAULT_SALDO
        defaultTransacaoShouldNotBeFound("saldo.greaterThan=" + DEFAULT_SALDO);

        // Get all the transacaoList where saldo is greater than SMALLER_SALDO
        defaultTransacaoShouldBeFound("saldo.greaterThan=" + SMALLER_SALDO);
    }

    @Test
    @Transactional
    void getAllTransacaosByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where timestamp equals to DEFAULT_TIMESTAMP
        defaultTransacaoShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the transacaoList where timestamp equals to UPDATED_TIMESTAMP
        defaultTransacaoShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransacaosByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultTransacaoShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the transacaoList where timestamp equals to UPDATED_TIMESTAMP
        defaultTransacaoShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransacaosByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where timestamp is not null
        defaultTransacaoShouldBeFound("timestamp.specified=true");

        // Get all the transacaoList where timestamp is null
        defaultTransacaoShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllTransacaosByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultTransacaoShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the transacaoList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultTransacaoShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransacaosByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultTransacaoShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the transacaoList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultTransacaoShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransacaosByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where timestamp is less than DEFAULT_TIMESTAMP
        defaultTransacaoShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the transacaoList where timestamp is less than UPDATED_TIMESTAMP
        defaultTransacaoShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransacaosByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultTransacaoShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the transacaoList where timestamp is greater than SMALLER_TIMESTAMP
        defaultTransacaoShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransacaosByRecibosIsEqualToSomething() throws Exception {
        Recibo recibos;
        if (TestUtil.findAll(em, Recibo.class).isEmpty()) {
            transacaoRepository.saveAndFlush(transacao);
            recibos = ReciboResourceIT.createEntity(em);
        } else {
            recibos = TestUtil.findAll(em, Recibo.class).get(0);
        }
        em.persist(recibos);
        em.flush();
        transacao.addRecibos(recibos);
        transacaoRepository.saveAndFlush(transacao);
        Long recibosId = recibos.getId();

        // Get all the transacaoList where recibos equals to recibosId
        defaultTransacaoShouldBeFound("recibosId.equals=" + recibosId);

        // Get all the transacaoList where recibos equals to (recibosId + 1)
        defaultTransacaoShouldNotBeFound("recibosId.equals=" + (recibosId + 1));
    }

    @Test
    @Transactional
    void getAllTransacaosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            transacaoRepository.saveAndFlush(transacao);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        transacao.setUtilizador(utilizador);
        transacaoRepository.saveAndFlush(transacao);
        Long utilizadorId = utilizador.getId();

        // Get all the transacaoList where utilizador equals to utilizadorId
        defaultTransacaoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the transacaoList where utilizador equals to (utilizadorId + 1)
        defaultTransacaoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllTransacaosByMoedaIsEqualToSomething() throws Exception {
        LookupItem moeda;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            transacaoRepository.saveAndFlush(transacao);
            moeda = LookupItemResourceIT.createEntity(em);
        } else {
            moeda = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(moeda);
        em.flush();
        transacao.setMoeda(moeda);
        transacaoRepository.saveAndFlush(transacao);
        Long moedaId = moeda.getId();

        // Get all the transacaoList where moeda equals to moedaId
        defaultTransacaoShouldBeFound("moedaId.equals=" + moedaId);

        // Get all the transacaoList where moeda equals to (moedaId + 1)
        defaultTransacaoShouldNotBeFound("moedaId.equals=" + (moedaId + 1));
    }

    @Test
    @Transactional
    void getAllTransacaosByMatriculaIsEqualToSomething() throws Exception {
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            transacaoRepository.saveAndFlush(transacao);
            matricula = MatriculaResourceIT.createEntity(em);
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(matricula);
        em.flush();
        transacao.setMatricula(matricula);
        transacaoRepository.saveAndFlush(transacao);
        Long matriculaId = matricula.getId();

        // Get all the transacaoList where matricula equals to matriculaId
        defaultTransacaoShouldBeFound("matriculaId.equals=" + matriculaId);

        // Get all the transacaoList where matricula equals to (matriculaId + 1)
        defaultTransacaoShouldNotBeFound("matriculaId.equals=" + (matriculaId + 1));
    }

    @Test
    @Transactional
    void getAllTransacaosByMeioPagamentoIsEqualToSomething() throws Exception {
        MeioPagamento meioPagamento;
        if (TestUtil.findAll(em, MeioPagamento.class).isEmpty()) {
            transacaoRepository.saveAndFlush(transacao);
            meioPagamento = MeioPagamentoResourceIT.createEntity(em);
        } else {
            meioPagamento = TestUtil.findAll(em, MeioPagamento.class).get(0);
        }
        em.persist(meioPagamento);
        em.flush();
        transacao.setMeioPagamento(meioPagamento);
        transacaoRepository.saveAndFlush(transacao);
        Long meioPagamentoId = meioPagamento.getId();

        // Get all the transacaoList where meioPagamento equals to meioPagamentoId
        defaultTransacaoShouldBeFound("meioPagamentoId.equals=" + meioPagamentoId);

        // Get all the transacaoList where meioPagamento equals to (meioPagamentoId + 1)
        defaultTransacaoShouldNotBeFound("meioPagamentoId.equals=" + (meioPagamentoId + 1));
    }

    @Test
    @Transactional
    void getAllTransacaosByContaIsEqualToSomething() throws Exception {
        Conta conta;
        if (TestUtil.findAll(em, Conta.class).isEmpty()) {
            transacaoRepository.saveAndFlush(transacao);
            conta = ContaResourceIT.createEntity(em);
        } else {
            conta = TestUtil.findAll(em, Conta.class).get(0);
        }
        em.persist(conta);
        em.flush();
        transacao.setConta(conta);
        transacaoRepository.saveAndFlush(transacao);
        Long contaId = conta.getId();

        // Get all the transacaoList where conta equals to contaId
        defaultTransacaoShouldBeFound("contaId.equals=" + contaId);

        // Get all the transacaoList where conta equals to (contaId + 1)
        defaultTransacaoShouldNotBeFound("contaId.equals=" + (contaId + 1));
    }

    @Test
    @Transactional
    void getAllTransacaosByTransferenciaSaldoIsEqualToSomething() throws Exception {
        TransferenciaSaldo transferenciaSaldo;
        if (TestUtil.findAll(em, TransferenciaSaldo.class).isEmpty()) {
            transacaoRepository.saveAndFlush(transacao);
            transferenciaSaldo = TransferenciaSaldoResourceIT.createEntity(em);
        } else {
            transferenciaSaldo = TestUtil.findAll(em, TransferenciaSaldo.class).get(0);
        }
        em.persist(transferenciaSaldo);
        em.flush();
        transacao.addTransferenciaSaldo(transferenciaSaldo);
        transacaoRepository.saveAndFlush(transacao);
        Long transferenciaSaldoId = transferenciaSaldo.getId();

        // Get all the transacaoList where transferenciaSaldo equals to transferenciaSaldoId
        defaultTransacaoShouldBeFound("transferenciaSaldoId.equals=" + transferenciaSaldoId);

        // Get all the transacaoList where transferenciaSaldo equals to (transferenciaSaldoId + 1)
        defaultTransacaoShouldNotBeFound("transferenciaSaldoId.equals=" + (transferenciaSaldoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransacaoShouldBeFound(String filter) throws Exception {
        restTransacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].montante").value(hasItem(sameNumber(DEFAULT_MONTANTE))))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].referencia").value(hasItem(DEFAULT_REFERENCIA)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(sameNumber(DEFAULT_SALDO))))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restTransacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransacaoShouldNotBeFound(String filter) throws Exception {
        restTransacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransacao() throws Exception {
        // Get the transacao
        restTransacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransacao() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();

        // Update the transacao
        Transacao updatedTransacao = transacaoRepository.findById(transacao.getId()).get();
        // Disconnect from session so that the updates on updatedTransacao are not directly saved in db
        em.detach(updatedTransacao);
        updatedTransacao
            .montante(UPDATED_MONTANTE)
            .data(UPDATED_DATA)
            .referencia(UPDATED_REFERENCIA)
            .estado(UPDATED_ESTADO)
            .saldo(UPDATED_SALDO)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .timestamp(UPDATED_TIMESTAMP);
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(updatedTransacao);

        restTransacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transacaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
        Transacao testTransacao = transacaoList.get(transacaoList.size() - 1);
        assertThat(testTransacao.getMontante()).isEqualByComparingTo(UPDATED_MONTANTE);
        assertThat(testTransacao.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testTransacao.getReferencia()).isEqualTo(UPDATED_REFERENCIA);
        assertThat(testTransacao.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTransacao.getSaldo()).isEqualByComparingTo(UPDATED_SALDO);
        assertThat(testTransacao.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testTransacao.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
        assertThat(testTransacao.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingTransacao() throws Exception {
        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();
        transacao.setId(count.incrementAndGet());

        // Create the Transacao
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransacao() throws Exception {
        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();
        transacao.setId(count.incrementAndGet());

        // Create the Transacao
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransacao() throws Exception {
        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();
        transacao.setId(count.incrementAndGet());

        // Create the Transacao
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransacaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransacaoWithPatch() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();

        // Update the transacao using partial update
        Transacao partialUpdatedTransacao = new Transacao();
        partialUpdatedTransacao.setId(transacao.getId());

        partialUpdatedTransacao.montante(UPDATED_MONTANTE).estado(UPDATED_ESTADO).timestamp(UPDATED_TIMESTAMP);

        restTransacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransacao))
            )
            .andExpect(status().isOk());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
        Transacao testTransacao = transacaoList.get(transacaoList.size() - 1);
        assertThat(testTransacao.getMontante()).isEqualByComparingTo(UPDATED_MONTANTE);
        assertThat(testTransacao.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testTransacao.getReferencia()).isEqualTo(DEFAULT_REFERENCIA);
        assertThat(testTransacao.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTransacao.getSaldo()).isEqualByComparingTo(DEFAULT_SALDO);
        assertThat(testTransacao.getAnexo()).isEqualTo(DEFAULT_ANEXO);
        assertThat(testTransacao.getAnexoContentType()).isEqualTo(DEFAULT_ANEXO_CONTENT_TYPE);
        assertThat(testTransacao.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateTransacaoWithPatch() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();

        // Update the transacao using partial update
        Transacao partialUpdatedTransacao = new Transacao();
        partialUpdatedTransacao.setId(transacao.getId());

        partialUpdatedTransacao
            .montante(UPDATED_MONTANTE)
            .data(UPDATED_DATA)
            .referencia(UPDATED_REFERENCIA)
            .estado(UPDATED_ESTADO)
            .saldo(UPDATED_SALDO)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .timestamp(UPDATED_TIMESTAMP);

        restTransacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransacao))
            )
            .andExpect(status().isOk());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
        Transacao testTransacao = transacaoList.get(transacaoList.size() - 1);
        assertThat(testTransacao.getMontante()).isEqualByComparingTo(UPDATED_MONTANTE);
        assertThat(testTransacao.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testTransacao.getReferencia()).isEqualTo(UPDATED_REFERENCIA);
        assertThat(testTransacao.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTransacao.getSaldo()).isEqualByComparingTo(UPDATED_SALDO);
        assertThat(testTransacao.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testTransacao.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
        assertThat(testTransacao.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingTransacao() throws Exception {
        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();
        transacao.setId(count.incrementAndGet());

        // Create the Transacao
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transacaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransacao() throws Exception {
        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();
        transacao.setId(count.incrementAndGet());

        // Create the Transacao
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransacao() throws Exception {
        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();
        transacao.setId(count.incrementAndGet());

        // Create the Transacao
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransacaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(transacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransacao() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        int databaseSizeBeforeDelete = transacaoRepository.findAll().size();

        // Delete the transacao
        restTransacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, transacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
