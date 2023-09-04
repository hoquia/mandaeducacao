package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.LongonkeloHistorico;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.LongonkeloHistoricoRepository;
import com.ravunana.longonkelo.service.LongonkeloHistoricoService;
import com.ravunana.longonkelo.service.criteria.LongonkeloHistoricoCriteria;
import com.ravunana.longonkelo.service.dto.LongonkeloHistoricoDTO;
import com.ravunana.longonkelo.service.mapper.LongonkeloHistoricoMapper;
import java.time.Instant;
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
 * Integration tests for the {@link LongonkeloHistoricoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LongonkeloHistoricoResourceIT {

    private static final String DEFAULT_OPERACAO = "AAAAAAAAAA";
    private static final String UPDATED_OPERACAO = "BBBBBBBBBB";

    private static final String DEFAULT_ENTIDADE_NOME = "AAAAAAAAAA";
    private static final String UPDATED_ENTIDADE_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_ENTIDADE_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_ENTIDADE_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_PAYLOAD = "BBBBBBBBBB";

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/longonkelo-historicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LongonkeloHistoricoRepository longonkeloHistoricoRepository;

    @Mock
    private LongonkeloHistoricoRepository longonkeloHistoricoRepositoryMock;

    @Autowired
    private LongonkeloHistoricoMapper longonkeloHistoricoMapper;

    @Mock
    private LongonkeloHistoricoService longonkeloHistoricoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLongonkeloHistoricoMockMvc;

    private LongonkeloHistorico longonkeloHistorico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LongonkeloHistorico createEntity(EntityManager em) {
        LongonkeloHistorico longonkeloHistorico = new LongonkeloHistorico()
            .operacao(DEFAULT_OPERACAO)
            .entidadeNome(DEFAULT_ENTIDADE_NOME)
            .entidadeCodigo(DEFAULT_ENTIDADE_CODIGO)
            .payload(DEFAULT_PAYLOAD)
            .host(DEFAULT_HOST)
            .hash(DEFAULT_HASH)
            .timestamp(DEFAULT_TIMESTAMP);
        return longonkeloHistorico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LongonkeloHistorico createUpdatedEntity(EntityManager em) {
        LongonkeloHistorico longonkeloHistorico = new LongonkeloHistorico()
            .operacao(UPDATED_OPERACAO)
            .entidadeNome(UPDATED_ENTIDADE_NOME)
            .entidadeCodigo(UPDATED_ENTIDADE_CODIGO)
            .payload(UPDATED_PAYLOAD)
            .host(UPDATED_HOST)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);
        return longonkeloHistorico;
    }

    @BeforeEach
    public void initTest() {
        longonkeloHistorico = createEntity(em);
    }

    @Test
    @Transactional
    void createLongonkeloHistorico() throws Exception {
        int databaseSizeBeforeCreate = longonkeloHistoricoRepository.findAll().size();
        // Create the LongonkeloHistorico
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);
        restLongonkeloHistoricoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LongonkeloHistorico in the database
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeCreate + 1);
        LongonkeloHistorico testLongonkeloHistorico = longonkeloHistoricoList.get(longonkeloHistoricoList.size() - 1);
        assertThat(testLongonkeloHistorico.getOperacao()).isEqualTo(DEFAULT_OPERACAO);
        assertThat(testLongonkeloHistorico.getEntidadeNome()).isEqualTo(DEFAULT_ENTIDADE_NOME);
        assertThat(testLongonkeloHistorico.getEntidadeCodigo()).isEqualTo(DEFAULT_ENTIDADE_CODIGO);
        assertThat(testLongonkeloHistorico.getPayload()).isEqualTo(DEFAULT_PAYLOAD);
        assertThat(testLongonkeloHistorico.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testLongonkeloHistorico.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testLongonkeloHistorico.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createLongonkeloHistoricoWithExistingId() throws Exception {
        // Create the LongonkeloHistorico with an existing ID
        longonkeloHistorico.setId(1L);
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        int databaseSizeBeforeCreate = longonkeloHistoricoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLongonkeloHistoricoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LongonkeloHistorico in the database
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOperacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = longonkeloHistoricoRepository.findAll().size();
        // set the field null
        longonkeloHistorico.setOperacao(null);

        // Create the LongonkeloHistorico, which fails.
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        restLongonkeloHistoricoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isBadRequest());

        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntidadeNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = longonkeloHistoricoRepository.findAll().size();
        // set the field null
        longonkeloHistorico.setEntidadeNome(null);

        // Create the LongonkeloHistorico, which fails.
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        restLongonkeloHistoricoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isBadRequest());

        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntidadeCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = longonkeloHistoricoRepository.findAll().size();
        // set the field null
        longonkeloHistorico.setEntidadeCodigo(null);

        // Create the LongonkeloHistorico, which fails.
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        restLongonkeloHistoricoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isBadRequest());

        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHostIsRequired() throws Exception {
        int databaseSizeBeforeTest = longonkeloHistoricoRepository.findAll().size();
        // set the field null
        longonkeloHistorico.setHost(null);

        // Create the LongonkeloHistorico, which fails.
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        restLongonkeloHistoricoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isBadRequest());

        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = longonkeloHistoricoRepository.findAll().size();
        // set the field null
        longonkeloHistorico.setTimestamp(null);

        // Create the LongonkeloHistorico, which fails.
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        restLongonkeloHistoricoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isBadRequest());

        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricos() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList
        restLongonkeloHistoricoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(longonkeloHistorico.getId().intValue())))
            .andExpect(jsonPath("$.[*].operacao").value(hasItem(DEFAULT_OPERACAO)))
            .andExpect(jsonPath("$.[*].entidadeNome").value(hasItem(DEFAULT_ENTIDADE_NOME)))
            .andExpect(jsonPath("$.[*].entidadeCodigo").value(hasItem(DEFAULT_ENTIDADE_CODIGO)))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD.toString())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLongonkeloHistoricosWithEagerRelationshipsIsEnabled() throws Exception {
        when(longonkeloHistoricoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLongonkeloHistoricoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(longonkeloHistoricoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLongonkeloHistoricosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(longonkeloHistoricoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLongonkeloHistoricoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(longonkeloHistoricoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLongonkeloHistorico() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get the longonkeloHistorico
        restLongonkeloHistoricoMockMvc
            .perform(get(ENTITY_API_URL_ID, longonkeloHistorico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(longonkeloHistorico.getId().intValue()))
            .andExpect(jsonPath("$.operacao").value(DEFAULT_OPERACAO))
            .andExpect(jsonPath("$.entidadeNome").value(DEFAULT_ENTIDADE_NOME))
            .andExpect(jsonPath("$.entidadeCodigo").value(DEFAULT_ENTIDADE_CODIGO))
            .andExpect(jsonPath("$.payload").value(DEFAULT_PAYLOAD.toString()))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getLongonkeloHistoricosByIdFiltering() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        Long id = longonkeloHistorico.getId();

        defaultLongonkeloHistoricoShouldBeFound("id.equals=" + id);
        defaultLongonkeloHistoricoShouldNotBeFound("id.notEquals=" + id);

        defaultLongonkeloHistoricoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLongonkeloHistoricoShouldNotBeFound("id.greaterThan=" + id);

        defaultLongonkeloHistoricoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLongonkeloHistoricoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByOperacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where operacao equals to DEFAULT_OPERACAO
        defaultLongonkeloHistoricoShouldBeFound("operacao.equals=" + DEFAULT_OPERACAO);

        // Get all the longonkeloHistoricoList where operacao equals to UPDATED_OPERACAO
        defaultLongonkeloHistoricoShouldNotBeFound("operacao.equals=" + UPDATED_OPERACAO);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByOperacaoIsInShouldWork() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where operacao in DEFAULT_OPERACAO or UPDATED_OPERACAO
        defaultLongonkeloHistoricoShouldBeFound("operacao.in=" + DEFAULT_OPERACAO + "," + UPDATED_OPERACAO);

        // Get all the longonkeloHistoricoList where operacao equals to UPDATED_OPERACAO
        defaultLongonkeloHistoricoShouldNotBeFound("operacao.in=" + UPDATED_OPERACAO);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByOperacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where operacao is not null
        defaultLongonkeloHistoricoShouldBeFound("operacao.specified=true");

        // Get all the longonkeloHistoricoList where operacao is null
        defaultLongonkeloHistoricoShouldNotBeFound("operacao.specified=false");
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByOperacaoContainsSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where operacao contains DEFAULT_OPERACAO
        defaultLongonkeloHistoricoShouldBeFound("operacao.contains=" + DEFAULT_OPERACAO);

        // Get all the longonkeloHistoricoList where operacao contains UPDATED_OPERACAO
        defaultLongonkeloHistoricoShouldNotBeFound("operacao.contains=" + UPDATED_OPERACAO);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByOperacaoNotContainsSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where operacao does not contain DEFAULT_OPERACAO
        defaultLongonkeloHistoricoShouldNotBeFound("operacao.doesNotContain=" + DEFAULT_OPERACAO);

        // Get all the longonkeloHistoricoList where operacao does not contain UPDATED_OPERACAO
        defaultLongonkeloHistoricoShouldBeFound("operacao.doesNotContain=" + UPDATED_OPERACAO);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByEntidadeNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where entidadeNome equals to DEFAULT_ENTIDADE_NOME
        defaultLongonkeloHistoricoShouldBeFound("entidadeNome.equals=" + DEFAULT_ENTIDADE_NOME);

        // Get all the longonkeloHistoricoList where entidadeNome equals to UPDATED_ENTIDADE_NOME
        defaultLongonkeloHistoricoShouldNotBeFound("entidadeNome.equals=" + UPDATED_ENTIDADE_NOME);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByEntidadeNomeIsInShouldWork() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where entidadeNome in DEFAULT_ENTIDADE_NOME or UPDATED_ENTIDADE_NOME
        defaultLongonkeloHistoricoShouldBeFound("entidadeNome.in=" + DEFAULT_ENTIDADE_NOME + "," + UPDATED_ENTIDADE_NOME);

        // Get all the longonkeloHistoricoList where entidadeNome equals to UPDATED_ENTIDADE_NOME
        defaultLongonkeloHistoricoShouldNotBeFound("entidadeNome.in=" + UPDATED_ENTIDADE_NOME);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByEntidadeNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where entidadeNome is not null
        defaultLongonkeloHistoricoShouldBeFound("entidadeNome.specified=true");

        // Get all the longonkeloHistoricoList where entidadeNome is null
        defaultLongonkeloHistoricoShouldNotBeFound("entidadeNome.specified=false");
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByEntidadeNomeContainsSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where entidadeNome contains DEFAULT_ENTIDADE_NOME
        defaultLongonkeloHistoricoShouldBeFound("entidadeNome.contains=" + DEFAULT_ENTIDADE_NOME);

        // Get all the longonkeloHistoricoList where entidadeNome contains UPDATED_ENTIDADE_NOME
        defaultLongonkeloHistoricoShouldNotBeFound("entidadeNome.contains=" + UPDATED_ENTIDADE_NOME);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByEntidadeNomeNotContainsSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where entidadeNome does not contain DEFAULT_ENTIDADE_NOME
        defaultLongonkeloHistoricoShouldNotBeFound("entidadeNome.doesNotContain=" + DEFAULT_ENTIDADE_NOME);

        // Get all the longonkeloHistoricoList where entidadeNome does not contain UPDATED_ENTIDADE_NOME
        defaultLongonkeloHistoricoShouldBeFound("entidadeNome.doesNotContain=" + UPDATED_ENTIDADE_NOME);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByEntidadeCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where entidadeCodigo equals to DEFAULT_ENTIDADE_CODIGO
        defaultLongonkeloHistoricoShouldBeFound("entidadeCodigo.equals=" + DEFAULT_ENTIDADE_CODIGO);

        // Get all the longonkeloHistoricoList where entidadeCodigo equals to UPDATED_ENTIDADE_CODIGO
        defaultLongonkeloHistoricoShouldNotBeFound("entidadeCodigo.equals=" + UPDATED_ENTIDADE_CODIGO);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByEntidadeCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where entidadeCodigo in DEFAULT_ENTIDADE_CODIGO or UPDATED_ENTIDADE_CODIGO
        defaultLongonkeloHistoricoShouldBeFound("entidadeCodigo.in=" + DEFAULT_ENTIDADE_CODIGO + "," + UPDATED_ENTIDADE_CODIGO);

        // Get all the longonkeloHistoricoList where entidadeCodigo equals to UPDATED_ENTIDADE_CODIGO
        defaultLongonkeloHistoricoShouldNotBeFound("entidadeCodigo.in=" + UPDATED_ENTIDADE_CODIGO);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByEntidadeCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where entidadeCodigo is not null
        defaultLongonkeloHistoricoShouldBeFound("entidadeCodigo.specified=true");

        // Get all the longonkeloHistoricoList where entidadeCodigo is null
        defaultLongonkeloHistoricoShouldNotBeFound("entidadeCodigo.specified=false");
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByEntidadeCodigoContainsSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where entidadeCodigo contains DEFAULT_ENTIDADE_CODIGO
        defaultLongonkeloHistoricoShouldBeFound("entidadeCodigo.contains=" + DEFAULT_ENTIDADE_CODIGO);

        // Get all the longonkeloHistoricoList where entidadeCodigo contains UPDATED_ENTIDADE_CODIGO
        defaultLongonkeloHistoricoShouldNotBeFound("entidadeCodigo.contains=" + UPDATED_ENTIDADE_CODIGO);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByEntidadeCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where entidadeCodigo does not contain DEFAULT_ENTIDADE_CODIGO
        defaultLongonkeloHistoricoShouldNotBeFound("entidadeCodigo.doesNotContain=" + DEFAULT_ENTIDADE_CODIGO);

        // Get all the longonkeloHistoricoList where entidadeCodigo does not contain UPDATED_ENTIDADE_CODIGO
        defaultLongonkeloHistoricoShouldBeFound("entidadeCodigo.doesNotContain=" + UPDATED_ENTIDADE_CODIGO);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByHostIsEqualToSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where host equals to DEFAULT_HOST
        defaultLongonkeloHistoricoShouldBeFound("host.equals=" + DEFAULT_HOST);

        // Get all the longonkeloHistoricoList where host equals to UPDATED_HOST
        defaultLongonkeloHistoricoShouldNotBeFound("host.equals=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByHostIsInShouldWork() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where host in DEFAULT_HOST or UPDATED_HOST
        defaultLongonkeloHistoricoShouldBeFound("host.in=" + DEFAULT_HOST + "," + UPDATED_HOST);

        // Get all the longonkeloHistoricoList where host equals to UPDATED_HOST
        defaultLongonkeloHistoricoShouldNotBeFound("host.in=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByHostIsNullOrNotNull() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where host is not null
        defaultLongonkeloHistoricoShouldBeFound("host.specified=true");

        // Get all the longonkeloHistoricoList where host is null
        defaultLongonkeloHistoricoShouldNotBeFound("host.specified=false");
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByHostContainsSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where host contains DEFAULT_HOST
        defaultLongonkeloHistoricoShouldBeFound("host.contains=" + DEFAULT_HOST);

        // Get all the longonkeloHistoricoList where host contains UPDATED_HOST
        defaultLongonkeloHistoricoShouldNotBeFound("host.contains=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByHostNotContainsSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where host does not contain DEFAULT_HOST
        defaultLongonkeloHistoricoShouldNotBeFound("host.doesNotContain=" + DEFAULT_HOST);

        // Get all the longonkeloHistoricoList where host does not contain UPDATED_HOST
        defaultLongonkeloHistoricoShouldBeFound("host.doesNotContain=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where hash equals to DEFAULT_HASH
        defaultLongonkeloHistoricoShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the longonkeloHistoricoList where hash equals to UPDATED_HASH
        defaultLongonkeloHistoricoShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByHashIsInShouldWork() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultLongonkeloHistoricoShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the longonkeloHistoricoList where hash equals to UPDATED_HASH
        defaultLongonkeloHistoricoShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where hash is not null
        defaultLongonkeloHistoricoShouldBeFound("hash.specified=true");

        // Get all the longonkeloHistoricoList where hash is null
        defaultLongonkeloHistoricoShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByHashContainsSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where hash contains DEFAULT_HASH
        defaultLongonkeloHistoricoShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the longonkeloHistoricoList where hash contains UPDATED_HASH
        defaultLongonkeloHistoricoShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByHashNotContainsSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where hash does not contain DEFAULT_HASH
        defaultLongonkeloHistoricoShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the longonkeloHistoricoList where hash does not contain UPDATED_HASH
        defaultLongonkeloHistoricoShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where timestamp equals to DEFAULT_TIMESTAMP
        defaultLongonkeloHistoricoShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the longonkeloHistoricoList where timestamp equals to UPDATED_TIMESTAMP
        defaultLongonkeloHistoricoShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultLongonkeloHistoricoShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the longonkeloHistoricoList where timestamp equals to UPDATED_TIMESTAMP
        defaultLongonkeloHistoricoShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where timestamp is not null
        defaultLongonkeloHistoricoShouldBeFound("timestamp.specified=true");

        // Get all the longonkeloHistoricoList where timestamp is null
        defaultLongonkeloHistoricoShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultLongonkeloHistoricoShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the longonkeloHistoricoList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultLongonkeloHistoricoShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultLongonkeloHistoricoShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the longonkeloHistoricoList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultLongonkeloHistoricoShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where timestamp is less than DEFAULT_TIMESTAMP
        defaultLongonkeloHistoricoShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the longonkeloHistoricoList where timestamp is less than UPDATED_TIMESTAMP
        defaultLongonkeloHistoricoShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        // Get all the longonkeloHistoricoList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultLongonkeloHistoricoShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the longonkeloHistoricoList where timestamp is greater than SMALLER_TIMESTAMP
        defaultLongonkeloHistoricoShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllLongonkeloHistoricosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        longonkeloHistorico.setUtilizador(utilizador);
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);
        Long utilizadorId = utilizador.getId();

        // Get all the longonkeloHistoricoList where utilizador equals to utilizadorId
        defaultLongonkeloHistoricoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the longonkeloHistoricoList where utilizador equals to (utilizadorId + 1)
        defaultLongonkeloHistoricoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLongonkeloHistoricoShouldBeFound(String filter) throws Exception {
        restLongonkeloHistoricoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(longonkeloHistorico.getId().intValue())))
            .andExpect(jsonPath("$.[*].operacao").value(hasItem(DEFAULT_OPERACAO)))
            .andExpect(jsonPath("$.[*].entidadeNome").value(hasItem(DEFAULT_ENTIDADE_NOME)))
            .andExpect(jsonPath("$.[*].entidadeCodigo").value(hasItem(DEFAULT_ENTIDADE_CODIGO)))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD.toString())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restLongonkeloHistoricoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLongonkeloHistoricoShouldNotBeFound(String filter) throws Exception {
        restLongonkeloHistoricoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLongonkeloHistoricoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLongonkeloHistorico() throws Exception {
        // Get the longonkeloHistorico
        restLongonkeloHistoricoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLongonkeloHistorico() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        int databaseSizeBeforeUpdate = longonkeloHistoricoRepository.findAll().size();

        // Update the longonkeloHistorico
        LongonkeloHistorico updatedLongonkeloHistorico = longonkeloHistoricoRepository.findById(longonkeloHistorico.getId()).get();
        // Disconnect from session so that the updates on updatedLongonkeloHistorico are not directly saved in db
        em.detach(updatedLongonkeloHistorico);
        updatedLongonkeloHistorico
            .operacao(UPDATED_OPERACAO)
            .entidadeNome(UPDATED_ENTIDADE_NOME)
            .entidadeCodigo(UPDATED_ENTIDADE_CODIGO)
            .payload(UPDATED_PAYLOAD)
            .host(UPDATED_HOST)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(updatedLongonkeloHistorico);

        restLongonkeloHistoricoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longonkeloHistoricoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isOk());

        // Validate the LongonkeloHistorico in the database
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeUpdate);
        LongonkeloHistorico testLongonkeloHistorico = longonkeloHistoricoList.get(longonkeloHistoricoList.size() - 1);
        assertThat(testLongonkeloHistorico.getOperacao()).isEqualTo(UPDATED_OPERACAO);
        assertThat(testLongonkeloHistorico.getEntidadeNome()).isEqualTo(UPDATED_ENTIDADE_NOME);
        assertThat(testLongonkeloHistorico.getEntidadeCodigo()).isEqualTo(UPDATED_ENTIDADE_CODIGO);
        assertThat(testLongonkeloHistorico.getPayload()).isEqualTo(UPDATED_PAYLOAD);
        assertThat(testLongonkeloHistorico.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testLongonkeloHistorico.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testLongonkeloHistorico.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingLongonkeloHistorico() throws Exception {
        int databaseSizeBeforeUpdate = longonkeloHistoricoRepository.findAll().size();
        longonkeloHistorico.setId(count.incrementAndGet());

        // Create the LongonkeloHistorico
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLongonkeloHistoricoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longonkeloHistoricoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LongonkeloHistorico in the database
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLongonkeloHistorico() throws Exception {
        int databaseSizeBeforeUpdate = longonkeloHistoricoRepository.findAll().size();
        longonkeloHistorico.setId(count.incrementAndGet());

        // Create the LongonkeloHistorico
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLongonkeloHistoricoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LongonkeloHistorico in the database
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLongonkeloHistorico() throws Exception {
        int databaseSizeBeforeUpdate = longonkeloHistoricoRepository.findAll().size();
        longonkeloHistorico.setId(count.incrementAndGet());

        // Create the LongonkeloHistorico
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLongonkeloHistoricoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LongonkeloHistorico in the database
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLongonkeloHistoricoWithPatch() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        int databaseSizeBeforeUpdate = longonkeloHistoricoRepository.findAll().size();

        // Update the longonkeloHistorico using partial update
        LongonkeloHistorico partialUpdatedLongonkeloHistorico = new LongonkeloHistorico();
        partialUpdatedLongonkeloHistorico.setId(longonkeloHistorico.getId());

        partialUpdatedLongonkeloHistorico
            .operacao(UPDATED_OPERACAO)
            .entidadeNome(UPDATED_ENTIDADE_NOME)
            .entidadeCodigo(UPDATED_ENTIDADE_CODIGO)
            .payload(UPDATED_PAYLOAD)
            .timestamp(UPDATED_TIMESTAMP);

        restLongonkeloHistoricoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLongonkeloHistorico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLongonkeloHistorico))
            )
            .andExpect(status().isOk());

        // Validate the LongonkeloHistorico in the database
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeUpdate);
        LongonkeloHistorico testLongonkeloHistorico = longonkeloHistoricoList.get(longonkeloHistoricoList.size() - 1);
        assertThat(testLongonkeloHistorico.getOperacao()).isEqualTo(UPDATED_OPERACAO);
        assertThat(testLongonkeloHistorico.getEntidadeNome()).isEqualTo(UPDATED_ENTIDADE_NOME);
        assertThat(testLongonkeloHistorico.getEntidadeCodigo()).isEqualTo(UPDATED_ENTIDADE_CODIGO);
        assertThat(testLongonkeloHistorico.getPayload()).isEqualTo(UPDATED_PAYLOAD);
        assertThat(testLongonkeloHistorico.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testLongonkeloHistorico.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testLongonkeloHistorico.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateLongonkeloHistoricoWithPatch() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        int databaseSizeBeforeUpdate = longonkeloHistoricoRepository.findAll().size();

        // Update the longonkeloHistorico using partial update
        LongonkeloHistorico partialUpdatedLongonkeloHistorico = new LongonkeloHistorico();
        partialUpdatedLongonkeloHistorico.setId(longonkeloHistorico.getId());

        partialUpdatedLongonkeloHistorico
            .operacao(UPDATED_OPERACAO)
            .entidadeNome(UPDATED_ENTIDADE_NOME)
            .entidadeCodigo(UPDATED_ENTIDADE_CODIGO)
            .payload(UPDATED_PAYLOAD)
            .host(UPDATED_HOST)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);

        restLongonkeloHistoricoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLongonkeloHistorico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLongonkeloHistorico))
            )
            .andExpect(status().isOk());

        // Validate the LongonkeloHistorico in the database
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeUpdate);
        LongonkeloHistorico testLongonkeloHistorico = longonkeloHistoricoList.get(longonkeloHistoricoList.size() - 1);
        assertThat(testLongonkeloHistorico.getOperacao()).isEqualTo(UPDATED_OPERACAO);
        assertThat(testLongonkeloHistorico.getEntidadeNome()).isEqualTo(UPDATED_ENTIDADE_NOME);
        assertThat(testLongonkeloHistorico.getEntidadeCodigo()).isEqualTo(UPDATED_ENTIDADE_CODIGO);
        assertThat(testLongonkeloHistorico.getPayload()).isEqualTo(UPDATED_PAYLOAD);
        assertThat(testLongonkeloHistorico.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testLongonkeloHistorico.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testLongonkeloHistorico.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingLongonkeloHistorico() throws Exception {
        int databaseSizeBeforeUpdate = longonkeloHistoricoRepository.findAll().size();
        longonkeloHistorico.setId(count.incrementAndGet());

        // Create the LongonkeloHistorico
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLongonkeloHistoricoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longonkeloHistoricoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LongonkeloHistorico in the database
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLongonkeloHistorico() throws Exception {
        int databaseSizeBeforeUpdate = longonkeloHistoricoRepository.findAll().size();
        longonkeloHistorico.setId(count.incrementAndGet());

        // Create the LongonkeloHistorico
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLongonkeloHistoricoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LongonkeloHistorico in the database
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLongonkeloHistorico() throws Exception {
        int databaseSizeBeforeUpdate = longonkeloHistoricoRepository.findAll().size();
        longonkeloHistorico.setId(count.incrementAndGet());

        // Create the LongonkeloHistorico
        LongonkeloHistoricoDTO longonkeloHistoricoDTO = longonkeloHistoricoMapper.toDto(longonkeloHistorico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLongonkeloHistoricoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(longonkeloHistoricoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LongonkeloHistorico in the database
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLongonkeloHistorico() throws Exception {
        // Initialize the database
        longonkeloHistoricoRepository.saveAndFlush(longonkeloHistorico);

        int databaseSizeBeforeDelete = longonkeloHistoricoRepository.findAll().size();

        // Delete the longonkeloHistorico
        restLongonkeloHistoricoMockMvc
            .perform(delete(ENTITY_API_URL_ID, longonkeloHistorico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LongonkeloHistorico> longonkeloHistoricoList = longonkeloHistoricoRepository.findAll();
        assertThat(longonkeloHistoricoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
