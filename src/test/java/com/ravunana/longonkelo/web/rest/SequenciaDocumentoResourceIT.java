package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.SequenciaDocumento;
import com.ravunana.longonkelo.domain.SerieDocumento;
import com.ravunana.longonkelo.repository.SequenciaDocumentoRepository;
import com.ravunana.longonkelo.service.SequenciaDocumentoService;
import com.ravunana.longonkelo.service.criteria.SequenciaDocumentoCriteria;
import com.ravunana.longonkelo.service.dto.SequenciaDocumentoDTO;
import com.ravunana.longonkelo.service.mapper.SequenciaDocumentoMapper;
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

/**
 * Integration tests for the {@link SequenciaDocumentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SequenciaDocumentoResourceIT {

    private static final Long DEFAULT_SEQUENCIA = 1L;
    private static final Long UPDATED_SEQUENCIA = 2L;
    private static final Long SMALLER_SEQUENCIA = 1L - 1L;

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/sequencia-documentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SequenciaDocumentoRepository sequenciaDocumentoRepository;

    @Mock
    private SequenciaDocumentoRepository sequenciaDocumentoRepositoryMock;

    @Autowired
    private SequenciaDocumentoMapper sequenciaDocumentoMapper;

    @Mock
    private SequenciaDocumentoService sequenciaDocumentoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSequenciaDocumentoMockMvc;

    private SequenciaDocumento sequenciaDocumento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenciaDocumento createEntity(EntityManager em) {
        SequenciaDocumento sequenciaDocumento = new SequenciaDocumento()
            .sequencia(DEFAULT_SEQUENCIA)
            .data(DEFAULT_DATA)
            .hash(DEFAULT_HASH)
            .timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        SerieDocumento serieDocumento;
        if (TestUtil.findAll(em, SerieDocumento.class).isEmpty()) {
            serieDocumento = SerieDocumentoResourceIT.createEntity(em);
            em.persist(serieDocumento);
            em.flush();
        } else {
            serieDocumento = TestUtil.findAll(em, SerieDocumento.class).get(0);
        }
        sequenciaDocumento.setSerie(serieDocumento);
        return sequenciaDocumento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenciaDocumento createUpdatedEntity(EntityManager em) {
        SequenciaDocumento sequenciaDocumento = new SequenciaDocumento()
            .sequencia(UPDATED_SEQUENCIA)
            .data(UPDATED_DATA)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        SerieDocumento serieDocumento;
        if (TestUtil.findAll(em, SerieDocumento.class).isEmpty()) {
            serieDocumento = SerieDocumentoResourceIT.createUpdatedEntity(em);
            em.persist(serieDocumento);
            em.flush();
        } else {
            serieDocumento = TestUtil.findAll(em, SerieDocumento.class).get(0);
        }
        sequenciaDocumento.setSerie(serieDocumento);
        return sequenciaDocumento;
    }

    @BeforeEach
    public void initTest() {
        sequenciaDocumento = createEntity(em);
    }

    @Test
    @Transactional
    void createSequenciaDocumento() throws Exception {
        int databaseSizeBeforeCreate = sequenciaDocumentoRepository.findAll().size();
        // Create the SequenciaDocumento
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);
        restSequenciaDocumentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SequenciaDocumento in the database
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeCreate + 1);
        SequenciaDocumento testSequenciaDocumento = sequenciaDocumentoList.get(sequenciaDocumentoList.size() - 1);
        assertThat(testSequenciaDocumento.getSequencia()).isEqualTo(DEFAULT_SEQUENCIA);
        assertThat(testSequenciaDocumento.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testSequenciaDocumento.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testSequenciaDocumento.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createSequenciaDocumentoWithExistingId() throws Exception {
        // Create the SequenciaDocumento with an existing ID
        sequenciaDocumento.setId(1L);
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);

        int databaseSizeBeforeCreate = sequenciaDocumentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSequenciaDocumentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenciaDocumento in the database
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSequenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenciaDocumentoRepository.findAll().size();
        // set the field null
        sequenciaDocumento.setSequencia(null);

        // Create the SequenciaDocumento, which fails.
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);

        restSequenciaDocumentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenciaDocumentoRepository.findAll().size();
        // set the field null
        sequenciaDocumento.setData(null);

        // Create the SequenciaDocumento, which fails.
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);

        restSequenciaDocumentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenciaDocumentoRepository.findAll().size();
        // set the field null
        sequenciaDocumento.setHash(null);

        // Create the SequenciaDocumento, which fails.
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);

        restSequenciaDocumentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenciaDocumentoRepository.findAll().size();
        // set the field null
        sequenciaDocumento.setTimestamp(null);

        // Create the SequenciaDocumento, which fails.
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);

        restSequenciaDocumentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentos() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList
        restSequenciaDocumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenciaDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequencia").value(hasItem(DEFAULT_SEQUENCIA.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSequenciaDocumentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(sequenciaDocumentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSequenciaDocumentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sequenciaDocumentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSequenciaDocumentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sequenciaDocumentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSequenciaDocumentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(sequenciaDocumentoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSequenciaDocumento() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get the sequenciaDocumento
        restSequenciaDocumentoMockMvc
            .perform(get(ENTITY_API_URL_ID, sequenciaDocumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sequenciaDocumento.getId().intValue()))
            .andExpect(jsonPath("$.sequencia").value(DEFAULT_SEQUENCIA.intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getSequenciaDocumentosByIdFiltering() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        Long id = sequenciaDocumento.getId();

        defaultSequenciaDocumentoShouldBeFound("id.equals=" + id);
        defaultSequenciaDocumentoShouldNotBeFound("id.notEquals=" + id);

        defaultSequenciaDocumentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSequenciaDocumentoShouldNotBeFound("id.greaterThan=" + id);

        defaultSequenciaDocumentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSequenciaDocumentoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosBySequenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where sequencia equals to DEFAULT_SEQUENCIA
        defaultSequenciaDocumentoShouldBeFound("sequencia.equals=" + DEFAULT_SEQUENCIA);

        // Get all the sequenciaDocumentoList where sequencia equals to UPDATED_SEQUENCIA
        defaultSequenciaDocumentoShouldNotBeFound("sequencia.equals=" + UPDATED_SEQUENCIA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosBySequenciaIsInShouldWork() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where sequencia in DEFAULT_SEQUENCIA or UPDATED_SEQUENCIA
        defaultSequenciaDocumentoShouldBeFound("sequencia.in=" + DEFAULT_SEQUENCIA + "," + UPDATED_SEQUENCIA);

        // Get all the sequenciaDocumentoList where sequencia equals to UPDATED_SEQUENCIA
        defaultSequenciaDocumentoShouldNotBeFound("sequencia.in=" + UPDATED_SEQUENCIA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosBySequenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where sequencia is not null
        defaultSequenciaDocumentoShouldBeFound("sequencia.specified=true");

        // Get all the sequenciaDocumentoList where sequencia is null
        defaultSequenciaDocumentoShouldNotBeFound("sequencia.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosBySequenciaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where sequencia is greater than or equal to DEFAULT_SEQUENCIA
        defaultSequenciaDocumentoShouldBeFound("sequencia.greaterThanOrEqual=" + DEFAULT_SEQUENCIA);

        // Get all the sequenciaDocumentoList where sequencia is greater than or equal to UPDATED_SEQUENCIA
        defaultSequenciaDocumentoShouldNotBeFound("sequencia.greaterThanOrEqual=" + UPDATED_SEQUENCIA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosBySequenciaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where sequencia is less than or equal to DEFAULT_SEQUENCIA
        defaultSequenciaDocumentoShouldBeFound("sequencia.lessThanOrEqual=" + DEFAULT_SEQUENCIA);

        // Get all the sequenciaDocumentoList where sequencia is less than or equal to SMALLER_SEQUENCIA
        defaultSequenciaDocumentoShouldNotBeFound("sequencia.lessThanOrEqual=" + SMALLER_SEQUENCIA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosBySequenciaIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where sequencia is less than DEFAULT_SEQUENCIA
        defaultSequenciaDocumentoShouldNotBeFound("sequencia.lessThan=" + DEFAULT_SEQUENCIA);

        // Get all the sequenciaDocumentoList where sequencia is less than UPDATED_SEQUENCIA
        defaultSequenciaDocumentoShouldBeFound("sequencia.lessThan=" + UPDATED_SEQUENCIA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosBySequenciaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where sequencia is greater than DEFAULT_SEQUENCIA
        defaultSequenciaDocumentoShouldNotBeFound("sequencia.greaterThan=" + DEFAULT_SEQUENCIA);

        // Get all the sequenciaDocumentoList where sequencia is greater than SMALLER_SEQUENCIA
        defaultSequenciaDocumentoShouldBeFound("sequencia.greaterThan=" + SMALLER_SEQUENCIA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where data equals to DEFAULT_DATA
        defaultSequenciaDocumentoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the sequenciaDocumentoList where data equals to UPDATED_DATA
        defaultSequenciaDocumentoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultSequenciaDocumentoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the sequenciaDocumentoList where data equals to UPDATED_DATA
        defaultSequenciaDocumentoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where data is not null
        defaultSequenciaDocumentoShouldBeFound("data.specified=true");

        // Get all the sequenciaDocumentoList where data is null
        defaultSequenciaDocumentoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where data is greater than or equal to DEFAULT_DATA
        defaultSequenciaDocumentoShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the sequenciaDocumentoList where data is greater than or equal to UPDATED_DATA
        defaultSequenciaDocumentoShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where data is less than or equal to DEFAULT_DATA
        defaultSequenciaDocumentoShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the sequenciaDocumentoList where data is less than or equal to SMALLER_DATA
        defaultSequenciaDocumentoShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where data is less than DEFAULT_DATA
        defaultSequenciaDocumentoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the sequenciaDocumentoList where data is less than UPDATED_DATA
        defaultSequenciaDocumentoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where data is greater than DEFAULT_DATA
        defaultSequenciaDocumentoShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the sequenciaDocumentoList where data is greater than SMALLER_DATA
        defaultSequenciaDocumentoShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where hash equals to DEFAULT_HASH
        defaultSequenciaDocumentoShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the sequenciaDocumentoList where hash equals to UPDATED_HASH
        defaultSequenciaDocumentoShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByHashIsInShouldWork() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultSequenciaDocumentoShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the sequenciaDocumentoList where hash equals to UPDATED_HASH
        defaultSequenciaDocumentoShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where hash is not null
        defaultSequenciaDocumentoShouldBeFound("hash.specified=true");

        // Get all the sequenciaDocumentoList where hash is null
        defaultSequenciaDocumentoShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByHashContainsSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where hash contains DEFAULT_HASH
        defaultSequenciaDocumentoShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the sequenciaDocumentoList where hash contains UPDATED_HASH
        defaultSequenciaDocumentoShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByHashNotContainsSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where hash does not contain DEFAULT_HASH
        defaultSequenciaDocumentoShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the sequenciaDocumentoList where hash does not contain UPDATED_HASH
        defaultSequenciaDocumentoShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where timestamp equals to DEFAULT_TIMESTAMP
        defaultSequenciaDocumentoShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the sequenciaDocumentoList where timestamp equals to UPDATED_TIMESTAMP
        defaultSequenciaDocumentoShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultSequenciaDocumentoShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the sequenciaDocumentoList where timestamp equals to UPDATED_TIMESTAMP
        defaultSequenciaDocumentoShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where timestamp is not null
        defaultSequenciaDocumentoShouldBeFound("timestamp.specified=true");

        // Get all the sequenciaDocumentoList where timestamp is null
        defaultSequenciaDocumentoShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultSequenciaDocumentoShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the sequenciaDocumentoList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultSequenciaDocumentoShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultSequenciaDocumentoShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the sequenciaDocumentoList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultSequenciaDocumentoShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where timestamp is less than DEFAULT_TIMESTAMP
        defaultSequenciaDocumentoShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the sequenciaDocumentoList where timestamp is less than UPDATED_TIMESTAMP
        defaultSequenciaDocumentoShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        // Get all the sequenciaDocumentoList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultSequenciaDocumentoShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the sequenciaDocumentoList where timestamp is greater than SMALLER_TIMESTAMP
        defaultSequenciaDocumentoShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllSequenciaDocumentosBySerieIsEqualToSomething() throws Exception {
        SerieDocumento serie;
        if (TestUtil.findAll(em, SerieDocumento.class).isEmpty()) {
            sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);
            serie = SerieDocumentoResourceIT.createEntity(em);
        } else {
            serie = TestUtil.findAll(em, SerieDocumento.class).get(0);
        }
        em.persist(serie);
        em.flush();
        sequenciaDocumento.setSerie(serie);
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);
        Long serieId = serie.getId();

        // Get all the sequenciaDocumentoList where serie equals to serieId
        defaultSequenciaDocumentoShouldBeFound("serieId.equals=" + serieId);

        // Get all the sequenciaDocumentoList where serie equals to (serieId + 1)
        defaultSequenciaDocumentoShouldNotBeFound("serieId.equals=" + (serieId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSequenciaDocumentoShouldBeFound(String filter) throws Exception {
        restSequenciaDocumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenciaDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequencia").value(hasItem(DEFAULT_SEQUENCIA.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restSequenciaDocumentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSequenciaDocumentoShouldNotBeFound(String filter) throws Exception {
        restSequenciaDocumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSequenciaDocumentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSequenciaDocumento() throws Exception {
        // Get the sequenciaDocumento
        restSequenciaDocumentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSequenciaDocumento() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        int databaseSizeBeforeUpdate = sequenciaDocumentoRepository.findAll().size();

        // Update the sequenciaDocumento
        SequenciaDocumento updatedSequenciaDocumento = sequenciaDocumentoRepository.findById(sequenciaDocumento.getId()).get();
        // Disconnect from session so that the updates on updatedSequenciaDocumento are not directly saved in db
        em.detach(updatedSequenciaDocumento);
        updatedSequenciaDocumento.sequencia(UPDATED_SEQUENCIA).data(UPDATED_DATA).hash(UPDATED_HASH).timestamp(UPDATED_TIMESTAMP);
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(updatedSequenciaDocumento);

        restSequenciaDocumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sequenciaDocumentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the SequenciaDocumento in the database
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeUpdate);
        SequenciaDocumento testSequenciaDocumento = sequenciaDocumentoList.get(sequenciaDocumentoList.size() - 1);
        assertThat(testSequenciaDocumento.getSequencia()).isEqualTo(UPDATED_SEQUENCIA);
        assertThat(testSequenciaDocumento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testSequenciaDocumento.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testSequenciaDocumento.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingSequenciaDocumento() throws Exception {
        int databaseSizeBeforeUpdate = sequenciaDocumentoRepository.findAll().size();
        sequenciaDocumento.setId(count.incrementAndGet());

        // Create the SequenciaDocumento
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenciaDocumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sequenciaDocumentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenciaDocumento in the database
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSequenciaDocumento() throws Exception {
        int databaseSizeBeforeUpdate = sequenciaDocumentoRepository.findAll().size();
        sequenciaDocumento.setId(count.incrementAndGet());

        // Create the SequenciaDocumento
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenciaDocumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenciaDocumento in the database
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSequenciaDocumento() throws Exception {
        int databaseSizeBeforeUpdate = sequenciaDocumentoRepository.findAll().size();
        sequenciaDocumento.setId(count.incrementAndGet());

        // Create the SequenciaDocumento
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenciaDocumentoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SequenciaDocumento in the database
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSequenciaDocumentoWithPatch() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        int databaseSizeBeforeUpdate = sequenciaDocumentoRepository.findAll().size();

        // Update the sequenciaDocumento using partial update
        SequenciaDocumento partialUpdatedSequenciaDocumento = new SequenciaDocumento();
        partialUpdatedSequenciaDocumento.setId(sequenciaDocumento.getId());

        partialUpdatedSequenciaDocumento.data(UPDATED_DATA).hash(UPDATED_HASH).timestamp(UPDATED_TIMESTAMP);

        restSequenciaDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequenciaDocumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequenciaDocumento))
            )
            .andExpect(status().isOk());

        // Validate the SequenciaDocumento in the database
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeUpdate);
        SequenciaDocumento testSequenciaDocumento = sequenciaDocumentoList.get(sequenciaDocumentoList.size() - 1);
        assertThat(testSequenciaDocumento.getSequencia()).isEqualTo(DEFAULT_SEQUENCIA);
        assertThat(testSequenciaDocumento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testSequenciaDocumento.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testSequenciaDocumento.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateSequenciaDocumentoWithPatch() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        int databaseSizeBeforeUpdate = sequenciaDocumentoRepository.findAll().size();

        // Update the sequenciaDocumento using partial update
        SequenciaDocumento partialUpdatedSequenciaDocumento = new SequenciaDocumento();
        partialUpdatedSequenciaDocumento.setId(sequenciaDocumento.getId());

        partialUpdatedSequenciaDocumento.sequencia(UPDATED_SEQUENCIA).data(UPDATED_DATA).hash(UPDATED_HASH).timestamp(UPDATED_TIMESTAMP);

        restSequenciaDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequenciaDocumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequenciaDocumento))
            )
            .andExpect(status().isOk());

        // Validate the SequenciaDocumento in the database
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeUpdate);
        SequenciaDocumento testSequenciaDocumento = sequenciaDocumentoList.get(sequenciaDocumentoList.size() - 1);
        assertThat(testSequenciaDocumento.getSequencia()).isEqualTo(UPDATED_SEQUENCIA);
        assertThat(testSequenciaDocumento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testSequenciaDocumento.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testSequenciaDocumento.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingSequenciaDocumento() throws Exception {
        int databaseSizeBeforeUpdate = sequenciaDocumentoRepository.findAll().size();
        sequenciaDocumento.setId(count.incrementAndGet());

        // Create the SequenciaDocumento
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenciaDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sequenciaDocumentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenciaDocumento in the database
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSequenciaDocumento() throws Exception {
        int databaseSizeBeforeUpdate = sequenciaDocumentoRepository.findAll().size();
        sequenciaDocumento.setId(count.incrementAndGet());

        // Create the SequenciaDocumento
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenciaDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenciaDocumento in the database
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSequenciaDocumento() throws Exception {
        int databaseSizeBeforeUpdate = sequenciaDocumentoRepository.findAll().size();
        sequenciaDocumento.setId(count.incrementAndGet());

        // Create the SequenciaDocumento
        SequenciaDocumentoDTO sequenciaDocumentoDTO = sequenciaDocumentoMapper.toDto(sequenciaDocumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenciaDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequenciaDocumentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SequenciaDocumento in the database
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSequenciaDocumento() throws Exception {
        // Initialize the database
        sequenciaDocumentoRepository.saveAndFlush(sequenciaDocumento);

        int databaseSizeBeforeDelete = sequenciaDocumentoRepository.findAll().size();

        // Delete the sequenciaDocumento
        restSequenciaDocumentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, sequenciaDocumento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SequenciaDocumento> sequenciaDocumentoList = sequenciaDocumentoRepository.findAll();
        assertThat(sequenciaDocumentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
