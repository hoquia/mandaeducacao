package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.HistoricoSaude;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.HistoricoSaudeRepository;
import com.ravunana.longonkelo.service.HistoricoSaudeService;
import com.ravunana.longonkelo.service.criteria.HistoricoSaudeCriteria;
import com.ravunana.longonkelo.service.dto.HistoricoSaudeDTO;
import com.ravunana.longonkelo.service.mapper.HistoricoSaudeMapper;
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
 * Integration tests for the {@link HistoricoSaudeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class HistoricoSaudeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_FIM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FIM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FIM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_SITUACAO_PRESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_SITUACAO_PRESCRICAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/historico-saudes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HistoricoSaudeRepository historicoSaudeRepository;

    @Mock
    private HistoricoSaudeRepository historicoSaudeRepositoryMock;

    @Autowired
    private HistoricoSaudeMapper historicoSaudeMapper;

    @Mock
    private HistoricoSaudeService historicoSaudeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoricoSaudeMockMvc;

    private HistoricoSaude historicoSaude;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoricoSaude createEntity(EntityManager em) {
        HistoricoSaude historicoSaude = new HistoricoSaude()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .inicio(DEFAULT_INICIO)
            .fim(DEFAULT_FIM)
            .situacaoPrescricao(DEFAULT_SITUACAO_PRESCRICAO)
            .timestamp(DEFAULT_TIMESTAMP)
            .hash(DEFAULT_HASH);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        historicoSaude.setDiscente(discente);
        return historicoSaude;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoricoSaude createUpdatedEntity(EntityManager em) {
        HistoricoSaude historicoSaude = new HistoricoSaude()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .situacaoPrescricao(UPDATED_SITUACAO_PRESCRICAO)
            .timestamp(UPDATED_TIMESTAMP)
            .hash(UPDATED_HASH);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createUpdatedEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        historicoSaude.setDiscente(discente);
        return historicoSaude;
    }

    @BeforeEach
    public void initTest() {
        historicoSaude = createEntity(em);
    }

    @Test
    @Transactional
    void createHistoricoSaude() throws Exception {
        int databaseSizeBeforeCreate = historicoSaudeRepository.findAll().size();
        // Create the HistoricoSaude
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(historicoSaude);
        restHistoricoSaudeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HistoricoSaude in the database
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeCreate + 1);
        HistoricoSaude testHistoricoSaude = historicoSaudeList.get(historicoSaudeList.size() - 1);
        assertThat(testHistoricoSaude.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testHistoricoSaude.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testHistoricoSaude.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testHistoricoSaude.getFim()).isEqualTo(DEFAULT_FIM);
        assertThat(testHistoricoSaude.getSituacaoPrescricao()).isEqualTo(DEFAULT_SITUACAO_PRESCRICAO);
        assertThat(testHistoricoSaude.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testHistoricoSaude.getHash()).isEqualTo(DEFAULT_HASH);
    }

    @Test
    @Transactional
    void createHistoricoSaudeWithExistingId() throws Exception {
        // Create the HistoricoSaude with an existing ID
        historicoSaude.setId(1L);
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(historicoSaude);

        int databaseSizeBeforeCreate = historicoSaudeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoricoSaudeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricoSaude in the database
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicoSaudeRepository.findAll().size();
        // set the field null
        historicoSaude.setNome(null);

        // Create the HistoricoSaude, which fails.
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(historicoSaude);

        restHistoricoSaudeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isBadRequest());

        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicoSaudeRepository.findAll().size();
        // set the field null
        historicoSaude.setInicio(null);

        // Create the HistoricoSaude, which fails.
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(historicoSaude);

        restHistoricoSaudeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isBadRequest());

        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicoSaudeRepository.findAll().size();
        // set the field null
        historicoSaude.setTimestamp(null);

        // Create the HistoricoSaude, which fails.
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(historicoSaude);

        restHistoricoSaudeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isBadRequest());

        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudes() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList
        restHistoricoSaudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historicoSaude.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(sameInstant(DEFAULT_INICIO))))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(sameInstant(DEFAULT_FIM))))
            .andExpect(jsonPath("$.[*].situacaoPrescricao").value(hasItem(DEFAULT_SITUACAO_PRESCRICAO)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHistoricoSaudesWithEagerRelationshipsIsEnabled() throws Exception {
        when(historicoSaudeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHistoricoSaudeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(historicoSaudeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHistoricoSaudesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(historicoSaudeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHistoricoSaudeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(historicoSaudeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getHistoricoSaude() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get the historicoSaude
        restHistoricoSaudeMockMvc
            .perform(get(ENTITY_API_URL_ID, historicoSaude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historicoSaude.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.inicio").value(sameInstant(DEFAULT_INICIO)))
            .andExpect(jsonPath("$.fim").value(sameInstant(DEFAULT_FIM)))
            .andExpect(jsonPath("$.situacaoPrescricao").value(DEFAULT_SITUACAO_PRESCRICAO))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH));
    }

    @Test
    @Transactional
    void getHistoricoSaudesByIdFiltering() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        Long id = historicoSaude.getId();

        defaultHistoricoSaudeShouldBeFound("id.equals=" + id);
        defaultHistoricoSaudeShouldNotBeFound("id.notEquals=" + id);

        defaultHistoricoSaudeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHistoricoSaudeShouldNotBeFound("id.greaterThan=" + id);

        defaultHistoricoSaudeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHistoricoSaudeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where nome equals to DEFAULT_NOME
        defaultHistoricoSaudeShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the historicoSaudeList where nome equals to UPDATED_NOME
        defaultHistoricoSaudeShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultHistoricoSaudeShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the historicoSaudeList where nome equals to UPDATED_NOME
        defaultHistoricoSaudeShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where nome is not null
        defaultHistoricoSaudeShouldBeFound("nome.specified=true");

        // Get all the historicoSaudeList where nome is null
        defaultHistoricoSaudeShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByNomeContainsSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where nome contains DEFAULT_NOME
        defaultHistoricoSaudeShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the historicoSaudeList where nome contains UPDATED_NOME
        defaultHistoricoSaudeShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where nome does not contain DEFAULT_NOME
        defaultHistoricoSaudeShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the historicoSaudeList where nome does not contain UPDATED_NOME
        defaultHistoricoSaudeShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where inicio equals to DEFAULT_INICIO
        defaultHistoricoSaudeShouldBeFound("inicio.equals=" + DEFAULT_INICIO);

        // Get all the historicoSaudeList where inicio equals to UPDATED_INICIO
        defaultHistoricoSaudeShouldNotBeFound("inicio.equals=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByInicioIsInShouldWork() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where inicio in DEFAULT_INICIO or UPDATED_INICIO
        defaultHistoricoSaudeShouldBeFound("inicio.in=" + DEFAULT_INICIO + "," + UPDATED_INICIO);

        // Get all the historicoSaudeList where inicio equals to UPDATED_INICIO
        defaultHistoricoSaudeShouldNotBeFound("inicio.in=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where inicio is not null
        defaultHistoricoSaudeShouldBeFound("inicio.specified=true");

        // Get all the historicoSaudeList where inicio is null
        defaultHistoricoSaudeShouldNotBeFound("inicio.specified=false");
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByInicioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where inicio is greater than or equal to DEFAULT_INICIO
        defaultHistoricoSaudeShouldBeFound("inicio.greaterThanOrEqual=" + DEFAULT_INICIO);

        // Get all the historicoSaudeList where inicio is greater than or equal to UPDATED_INICIO
        defaultHistoricoSaudeShouldNotBeFound("inicio.greaterThanOrEqual=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByInicioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where inicio is less than or equal to DEFAULT_INICIO
        defaultHistoricoSaudeShouldBeFound("inicio.lessThanOrEqual=" + DEFAULT_INICIO);

        // Get all the historicoSaudeList where inicio is less than or equal to SMALLER_INICIO
        defaultHistoricoSaudeShouldNotBeFound("inicio.lessThanOrEqual=" + SMALLER_INICIO);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByInicioIsLessThanSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where inicio is less than DEFAULT_INICIO
        defaultHistoricoSaudeShouldNotBeFound("inicio.lessThan=" + DEFAULT_INICIO);

        // Get all the historicoSaudeList where inicio is less than UPDATED_INICIO
        defaultHistoricoSaudeShouldBeFound("inicio.lessThan=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByInicioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where inicio is greater than DEFAULT_INICIO
        defaultHistoricoSaudeShouldNotBeFound("inicio.greaterThan=" + DEFAULT_INICIO);

        // Get all the historicoSaudeList where inicio is greater than SMALLER_INICIO
        defaultHistoricoSaudeShouldBeFound("inicio.greaterThan=" + SMALLER_INICIO);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByFimIsEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where fim equals to DEFAULT_FIM
        defaultHistoricoSaudeShouldBeFound("fim.equals=" + DEFAULT_FIM);

        // Get all the historicoSaudeList where fim equals to UPDATED_FIM
        defaultHistoricoSaudeShouldNotBeFound("fim.equals=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByFimIsInShouldWork() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where fim in DEFAULT_FIM or UPDATED_FIM
        defaultHistoricoSaudeShouldBeFound("fim.in=" + DEFAULT_FIM + "," + UPDATED_FIM);

        // Get all the historicoSaudeList where fim equals to UPDATED_FIM
        defaultHistoricoSaudeShouldNotBeFound("fim.in=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByFimIsNullOrNotNull() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where fim is not null
        defaultHistoricoSaudeShouldBeFound("fim.specified=true");

        // Get all the historicoSaudeList where fim is null
        defaultHistoricoSaudeShouldNotBeFound("fim.specified=false");
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByFimIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where fim is greater than or equal to DEFAULT_FIM
        defaultHistoricoSaudeShouldBeFound("fim.greaterThanOrEqual=" + DEFAULT_FIM);

        // Get all the historicoSaudeList where fim is greater than or equal to UPDATED_FIM
        defaultHistoricoSaudeShouldNotBeFound("fim.greaterThanOrEqual=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByFimIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where fim is less than or equal to DEFAULT_FIM
        defaultHistoricoSaudeShouldBeFound("fim.lessThanOrEqual=" + DEFAULT_FIM);

        // Get all the historicoSaudeList where fim is less than or equal to SMALLER_FIM
        defaultHistoricoSaudeShouldNotBeFound("fim.lessThanOrEqual=" + SMALLER_FIM);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByFimIsLessThanSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where fim is less than DEFAULT_FIM
        defaultHistoricoSaudeShouldNotBeFound("fim.lessThan=" + DEFAULT_FIM);

        // Get all the historicoSaudeList where fim is less than UPDATED_FIM
        defaultHistoricoSaudeShouldBeFound("fim.lessThan=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByFimIsGreaterThanSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where fim is greater than DEFAULT_FIM
        defaultHistoricoSaudeShouldNotBeFound("fim.greaterThan=" + DEFAULT_FIM);

        // Get all the historicoSaudeList where fim is greater than SMALLER_FIM
        defaultHistoricoSaudeShouldBeFound("fim.greaterThan=" + SMALLER_FIM);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesBySituacaoPrescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where situacaoPrescricao equals to DEFAULT_SITUACAO_PRESCRICAO
        defaultHistoricoSaudeShouldBeFound("situacaoPrescricao.equals=" + DEFAULT_SITUACAO_PRESCRICAO);

        // Get all the historicoSaudeList where situacaoPrescricao equals to UPDATED_SITUACAO_PRESCRICAO
        defaultHistoricoSaudeShouldNotBeFound("situacaoPrescricao.equals=" + UPDATED_SITUACAO_PRESCRICAO);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesBySituacaoPrescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where situacaoPrescricao in DEFAULT_SITUACAO_PRESCRICAO or UPDATED_SITUACAO_PRESCRICAO
        defaultHistoricoSaudeShouldBeFound("situacaoPrescricao.in=" + DEFAULT_SITUACAO_PRESCRICAO + "," + UPDATED_SITUACAO_PRESCRICAO);

        // Get all the historicoSaudeList where situacaoPrescricao equals to UPDATED_SITUACAO_PRESCRICAO
        defaultHistoricoSaudeShouldNotBeFound("situacaoPrescricao.in=" + UPDATED_SITUACAO_PRESCRICAO);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesBySituacaoPrescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where situacaoPrescricao is not null
        defaultHistoricoSaudeShouldBeFound("situacaoPrescricao.specified=true");

        // Get all the historicoSaudeList where situacaoPrescricao is null
        defaultHistoricoSaudeShouldNotBeFound("situacaoPrescricao.specified=false");
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesBySituacaoPrescricaoContainsSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where situacaoPrescricao contains DEFAULT_SITUACAO_PRESCRICAO
        defaultHistoricoSaudeShouldBeFound("situacaoPrescricao.contains=" + DEFAULT_SITUACAO_PRESCRICAO);

        // Get all the historicoSaudeList where situacaoPrescricao contains UPDATED_SITUACAO_PRESCRICAO
        defaultHistoricoSaudeShouldNotBeFound("situacaoPrescricao.contains=" + UPDATED_SITUACAO_PRESCRICAO);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesBySituacaoPrescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where situacaoPrescricao does not contain DEFAULT_SITUACAO_PRESCRICAO
        defaultHistoricoSaudeShouldNotBeFound("situacaoPrescricao.doesNotContain=" + DEFAULT_SITUACAO_PRESCRICAO);

        // Get all the historicoSaudeList where situacaoPrescricao does not contain UPDATED_SITUACAO_PRESCRICAO
        defaultHistoricoSaudeShouldBeFound("situacaoPrescricao.doesNotContain=" + UPDATED_SITUACAO_PRESCRICAO);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where timestamp equals to DEFAULT_TIMESTAMP
        defaultHistoricoSaudeShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the historicoSaudeList where timestamp equals to UPDATED_TIMESTAMP
        defaultHistoricoSaudeShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultHistoricoSaudeShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the historicoSaudeList where timestamp equals to UPDATED_TIMESTAMP
        defaultHistoricoSaudeShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where timestamp is not null
        defaultHistoricoSaudeShouldBeFound("timestamp.specified=true");

        // Get all the historicoSaudeList where timestamp is null
        defaultHistoricoSaudeShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultHistoricoSaudeShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the historicoSaudeList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultHistoricoSaudeShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultHistoricoSaudeShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the historicoSaudeList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultHistoricoSaudeShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where timestamp is less than DEFAULT_TIMESTAMP
        defaultHistoricoSaudeShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the historicoSaudeList where timestamp is less than UPDATED_TIMESTAMP
        defaultHistoricoSaudeShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultHistoricoSaudeShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the historicoSaudeList where timestamp is greater than SMALLER_TIMESTAMP
        defaultHistoricoSaudeShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where hash equals to DEFAULT_HASH
        defaultHistoricoSaudeShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the historicoSaudeList where hash equals to UPDATED_HASH
        defaultHistoricoSaudeShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByHashIsInShouldWork() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultHistoricoSaudeShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the historicoSaudeList where hash equals to UPDATED_HASH
        defaultHistoricoSaudeShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where hash is not null
        defaultHistoricoSaudeShouldBeFound("hash.specified=true");

        // Get all the historicoSaudeList where hash is null
        defaultHistoricoSaudeShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByHashContainsSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where hash contains DEFAULT_HASH
        defaultHistoricoSaudeShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the historicoSaudeList where hash contains UPDATED_HASH
        defaultHistoricoSaudeShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByHashNotContainsSomething() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        // Get all the historicoSaudeList where hash does not contain DEFAULT_HASH
        defaultHistoricoSaudeShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the historicoSaudeList where hash does not contain UPDATED_HASH
        defaultHistoricoSaudeShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            historicoSaudeRepository.saveAndFlush(historicoSaude);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        historicoSaude.setUtilizador(utilizador);
        historicoSaudeRepository.saveAndFlush(historicoSaude);
        Long utilizadorId = utilizador.getId();

        // Get all the historicoSaudeList where utilizador equals to utilizadorId
        defaultHistoricoSaudeShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the historicoSaudeList where utilizador equals to (utilizadorId + 1)
        defaultHistoricoSaudeShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllHistoricoSaudesByDiscenteIsEqualToSomething() throws Exception {
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            historicoSaudeRepository.saveAndFlush(historicoSaude);
            discente = DiscenteResourceIT.createEntity(em);
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        em.persist(discente);
        em.flush();
        historicoSaude.setDiscente(discente);
        historicoSaudeRepository.saveAndFlush(historicoSaude);
        Long discenteId = discente.getId();

        // Get all the historicoSaudeList where discente equals to discenteId
        defaultHistoricoSaudeShouldBeFound("discenteId.equals=" + discenteId);

        // Get all the historicoSaudeList where discente equals to (discenteId + 1)
        defaultHistoricoSaudeShouldNotBeFound("discenteId.equals=" + (discenteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHistoricoSaudeShouldBeFound(String filter) throws Exception {
        restHistoricoSaudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historicoSaude.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(sameInstant(DEFAULT_INICIO))))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(sameInstant(DEFAULT_FIM))))
            .andExpect(jsonPath("$.[*].situacaoPrescricao").value(hasItem(DEFAULT_SITUACAO_PRESCRICAO)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)));

        // Check, that the count call also returns 1
        restHistoricoSaudeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHistoricoSaudeShouldNotBeFound(String filter) throws Exception {
        restHistoricoSaudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHistoricoSaudeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHistoricoSaude() throws Exception {
        // Get the historicoSaude
        restHistoricoSaudeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHistoricoSaude() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        int databaseSizeBeforeUpdate = historicoSaudeRepository.findAll().size();

        // Update the historicoSaude
        HistoricoSaude updatedHistoricoSaude = historicoSaudeRepository.findById(historicoSaude.getId()).get();
        // Disconnect from session so that the updates on updatedHistoricoSaude are not directly saved in db
        em.detach(updatedHistoricoSaude);
        updatedHistoricoSaude
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .situacaoPrescricao(UPDATED_SITUACAO_PRESCRICAO)
            .timestamp(UPDATED_TIMESTAMP)
            .hash(UPDATED_HASH);
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(updatedHistoricoSaude);

        restHistoricoSaudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historicoSaudeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isOk());

        // Validate the HistoricoSaude in the database
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeUpdate);
        HistoricoSaude testHistoricoSaude = historicoSaudeList.get(historicoSaudeList.size() - 1);
        assertThat(testHistoricoSaude.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testHistoricoSaude.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testHistoricoSaude.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testHistoricoSaude.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testHistoricoSaude.getSituacaoPrescricao()).isEqualTo(UPDATED_SITUACAO_PRESCRICAO);
        assertThat(testHistoricoSaude.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testHistoricoSaude.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    void putNonExistingHistoricoSaude() throws Exception {
        int databaseSizeBeforeUpdate = historicoSaudeRepository.findAll().size();
        historicoSaude.setId(count.incrementAndGet());

        // Create the HistoricoSaude
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(historicoSaude);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoricoSaudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historicoSaudeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricoSaude in the database
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistoricoSaude() throws Exception {
        int databaseSizeBeforeUpdate = historicoSaudeRepository.findAll().size();
        historicoSaude.setId(count.incrementAndGet());

        // Create the HistoricoSaude
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(historicoSaude);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricoSaudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricoSaude in the database
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistoricoSaude() throws Exception {
        int databaseSizeBeforeUpdate = historicoSaudeRepository.findAll().size();
        historicoSaude.setId(count.incrementAndGet());

        // Create the HistoricoSaude
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(historicoSaude);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricoSaudeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoricoSaude in the database
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistoricoSaudeWithPatch() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        int databaseSizeBeforeUpdate = historicoSaudeRepository.findAll().size();

        // Update the historicoSaude using partial update
        HistoricoSaude partialUpdatedHistoricoSaude = new HistoricoSaude();
        partialUpdatedHistoricoSaude.setId(historicoSaude.getId());

        partialUpdatedHistoricoSaude
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .hash(UPDATED_HASH);

        restHistoricoSaudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoricoSaude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoricoSaude))
            )
            .andExpect(status().isOk());

        // Validate the HistoricoSaude in the database
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeUpdate);
        HistoricoSaude testHistoricoSaude = historicoSaudeList.get(historicoSaudeList.size() - 1);
        assertThat(testHistoricoSaude.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testHistoricoSaude.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testHistoricoSaude.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testHistoricoSaude.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testHistoricoSaude.getSituacaoPrescricao()).isEqualTo(DEFAULT_SITUACAO_PRESCRICAO);
        assertThat(testHistoricoSaude.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testHistoricoSaude.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    void fullUpdateHistoricoSaudeWithPatch() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        int databaseSizeBeforeUpdate = historicoSaudeRepository.findAll().size();

        // Update the historicoSaude using partial update
        HistoricoSaude partialUpdatedHistoricoSaude = new HistoricoSaude();
        partialUpdatedHistoricoSaude.setId(historicoSaude.getId());

        partialUpdatedHistoricoSaude
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .situacaoPrescricao(UPDATED_SITUACAO_PRESCRICAO)
            .timestamp(UPDATED_TIMESTAMP)
            .hash(UPDATED_HASH);

        restHistoricoSaudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoricoSaude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoricoSaude))
            )
            .andExpect(status().isOk());

        // Validate the HistoricoSaude in the database
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeUpdate);
        HistoricoSaude testHistoricoSaude = historicoSaudeList.get(historicoSaudeList.size() - 1);
        assertThat(testHistoricoSaude.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testHistoricoSaude.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testHistoricoSaude.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testHistoricoSaude.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testHistoricoSaude.getSituacaoPrescricao()).isEqualTo(UPDATED_SITUACAO_PRESCRICAO);
        assertThat(testHistoricoSaude.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testHistoricoSaude.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    void patchNonExistingHistoricoSaude() throws Exception {
        int databaseSizeBeforeUpdate = historicoSaudeRepository.findAll().size();
        historicoSaude.setId(count.incrementAndGet());

        // Create the HistoricoSaude
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(historicoSaude);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoricoSaudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historicoSaudeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricoSaude in the database
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistoricoSaude() throws Exception {
        int databaseSizeBeforeUpdate = historicoSaudeRepository.findAll().size();
        historicoSaude.setId(count.incrementAndGet());

        // Create the HistoricoSaude
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(historicoSaude);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricoSaudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricoSaude in the database
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistoricoSaude() throws Exception {
        int databaseSizeBeforeUpdate = historicoSaudeRepository.findAll().size();
        historicoSaude.setId(count.incrementAndGet());

        // Create the HistoricoSaude
        HistoricoSaudeDTO historicoSaudeDTO = historicoSaudeMapper.toDto(historicoSaude);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricoSaudeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historicoSaudeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoricoSaude in the database
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistoricoSaude() throws Exception {
        // Initialize the database
        historicoSaudeRepository.saveAndFlush(historicoSaude);

        int databaseSizeBeforeDelete = historicoSaudeRepository.findAll().size();

        // Delete the historicoSaude
        restHistoricoSaudeMockMvc
            .perform(delete(ENTITY_API_URL_ID, historicoSaude.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistoricoSaude> historicoSaudeList = historicoSaudeRepository.findAll();
        assertThat(historicoSaudeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
