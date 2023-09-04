package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Classe;
import com.ravunana.longonkelo.domain.PeriodoLancamentoNota;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.domain.enumeration.TipoAvaliacao;
import com.ravunana.longonkelo.repository.PeriodoLancamentoNotaRepository;
import com.ravunana.longonkelo.service.PeriodoLancamentoNotaService;
import com.ravunana.longonkelo.service.criteria.PeriodoLancamentoNotaCriteria;
import com.ravunana.longonkelo.service.dto.PeriodoLancamentoNotaDTO;
import com.ravunana.longonkelo.service.mapper.PeriodoLancamentoNotaMapper;
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

/**
 * Integration tests for the {@link PeriodoLancamentoNotaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PeriodoLancamentoNotaResourceIT {

    private static final TipoAvaliacao DEFAULT_TIPO_AVALIACAO = TipoAvaliacao.NOTA1;
    private static final TipoAvaliacao UPDATED_TIPO_AVALIACAO = TipoAvaliacao.NOTA2;

    private static final ZonedDateTime DEFAULT_DE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_ATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/periodo-lancamento-notas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PeriodoLancamentoNotaRepository periodoLancamentoNotaRepository;

    @Mock
    private PeriodoLancamentoNotaRepository periodoLancamentoNotaRepositoryMock;

    @Autowired
    private PeriodoLancamentoNotaMapper periodoLancamentoNotaMapper;

    @Mock
    private PeriodoLancamentoNotaService periodoLancamentoNotaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodoLancamentoNotaMockMvc;

    private PeriodoLancamentoNota periodoLancamentoNota;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodoLancamentoNota createEntity(EntityManager em) {
        PeriodoLancamentoNota periodoLancamentoNota = new PeriodoLancamentoNota()
            .tipoAvaliacao(DEFAULT_TIPO_AVALIACAO)
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .timestamp(DEFAULT_TIMESTAMP);
        return periodoLancamentoNota;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodoLancamentoNota createUpdatedEntity(EntityManager em) {
        PeriodoLancamentoNota periodoLancamentoNota = new PeriodoLancamentoNota()
            .tipoAvaliacao(UPDATED_TIPO_AVALIACAO)
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .timestamp(UPDATED_TIMESTAMP);
        return periodoLancamentoNota;
    }

    @BeforeEach
    public void initTest() {
        periodoLancamentoNota = createEntity(em);
    }

    @Test
    @Transactional
    void createPeriodoLancamentoNota() throws Exception {
        int databaseSizeBeforeCreate = periodoLancamentoNotaRepository.findAll().size();
        // Create the PeriodoLancamentoNota
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);
        restPeriodoLancamentoNotaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PeriodoLancamentoNota in the database
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeCreate + 1);
        PeriodoLancamentoNota testPeriodoLancamentoNota = periodoLancamentoNotaList.get(periodoLancamentoNotaList.size() - 1);
        assertThat(testPeriodoLancamentoNota.getTipoAvaliacao()).isEqualTo(DEFAULT_TIPO_AVALIACAO);
        assertThat(testPeriodoLancamentoNota.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testPeriodoLancamentoNota.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testPeriodoLancamentoNota.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createPeriodoLancamentoNotaWithExistingId() throws Exception {
        // Create the PeriodoLancamentoNota with an existing ID
        periodoLancamentoNota.setId(1L);
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);

        int databaseSizeBeforeCreate = periodoLancamentoNotaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodoLancamentoNotaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoLancamentoNota in the database
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoAvaliacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoLancamentoNotaRepository.findAll().size();
        // set the field null
        periodoLancamentoNota.setTipoAvaliacao(null);

        // Create the PeriodoLancamentoNota, which fails.
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);

        restPeriodoLancamentoNotaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoLancamentoNotaRepository.findAll().size();
        // set the field null
        periodoLancamentoNota.setDe(null);

        // Create the PeriodoLancamentoNota, which fails.
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);

        restPeriodoLancamentoNotaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAteIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoLancamentoNotaRepository.findAll().size();
        // set the field null
        periodoLancamentoNota.setAte(null);

        // Create the PeriodoLancamentoNota, which fails.
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);

        restPeriodoLancamentoNotaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotas() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList
        restPeriodoLancamentoNotaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodoLancamentoNota.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoAvaliacao").value(hasItem(DEFAULT_TIPO_AVALIACAO.toString())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(sameInstant(DEFAULT_DE))))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(sameInstant(DEFAULT_ATE))))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPeriodoLancamentoNotasWithEagerRelationshipsIsEnabled() throws Exception {
        when(periodoLancamentoNotaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPeriodoLancamentoNotaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(periodoLancamentoNotaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPeriodoLancamentoNotasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(periodoLancamentoNotaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPeriodoLancamentoNotaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(periodoLancamentoNotaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPeriodoLancamentoNota() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get the periodoLancamentoNota
        restPeriodoLancamentoNotaMockMvc
            .perform(get(ENTITY_API_URL_ID, periodoLancamentoNota.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(periodoLancamentoNota.getId().intValue()))
            .andExpect(jsonPath("$.tipoAvaliacao").value(DEFAULT_TIPO_AVALIACAO.toString()))
            .andExpect(jsonPath("$.de").value(sameInstant(DEFAULT_DE)))
            .andExpect(jsonPath("$.ate").value(sameInstant(DEFAULT_ATE)))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getPeriodoLancamentoNotasByIdFiltering() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        Long id = periodoLancamentoNota.getId();

        defaultPeriodoLancamentoNotaShouldBeFound("id.equals=" + id);
        defaultPeriodoLancamentoNotaShouldNotBeFound("id.notEquals=" + id);

        defaultPeriodoLancamentoNotaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPeriodoLancamentoNotaShouldNotBeFound("id.greaterThan=" + id);

        defaultPeriodoLancamentoNotaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPeriodoLancamentoNotaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByTipoAvaliacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where tipoAvaliacao equals to DEFAULT_TIPO_AVALIACAO
        defaultPeriodoLancamentoNotaShouldBeFound("tipoAvaliacao.equals=" + DEFAULT_TIPO_AVALIACAO);

        // Get all the periodoLancamentoNotaList where tipoAvaliacao equals to UPDATED_TIPO_AVALIACAO
        defaultPeriodoLancamentoNotaShouldNotBeFound("tipoAvaliacao.equals=" + UPDATED_TIPO_AVALIACAO);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByTipoAvaliacaoIsInShouldWork() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where tipoAvaliacao in DEFAULT_TIPO_AVALIACAO or UPDATED_TIPO_AVALIACAO
        defaultPeriodoLancamentoNotaShouldBeFound("tipoAvaliacao.in=" + DEFAULT_TIPO_AVALIACAO + "," + UPDATED_TIPO_AVALIACAO);

        // Get all the periodoLancamentoNotaList where tipoAvaliacao equals to UPDATED_TIPO_AVALIACAO
        defaultPeriodoLancamentoNotaShouldNotBeFound("tipoAvaliacao.in=" + UPDATED_TIPO_AVALIACAO);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByTipoAvaliacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where tipoAvaliacao is not null
        defaultPeriodoLancamentoNotaShouldBeFound("tipoAvaliacao.specified=true");

        // Get all the periodoLancamentoNotaList where tipoAvaliacao is null
        defaultPeriodoLancamentoNotaShouldNotBeFound("tipoAvaliacao.specified=false");
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByDeIsEqualToSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where de equals to DEFAULT_DE
        defaultPeriodoLancamentoNotaShouldBeFound("de.equals=" + DEFAULT_DE);

        // Get all the periodoLancamentoNotaList where de equals to UPDATED_DE
        defaultPeriodoLancamentoNotaShouldNotBeFound("de.equals=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByDeIsInShouldWork() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where de in DEFAULT_DE or UPDATED_DE
        defaultPeriodoLancamentoNotaShouldBeFound("de.in=" + DEFAULT_DE + "," + UPDATED_DE);

        // Get all the periodoLancamentoNotaList where de equals to UPDATED_DE
        defaultPeriodoLancamentoNotaShouldNotBeFound("de.in=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByDeIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where de is not null
        defaultPeriodoLancamentoNotaShouldBeFound("de.specified=true");

        // Get all the periodoLancamentoNotaList where de is null
        defaultPeriodoLancamentoNotaShouldNotBeFound("de.specified=false");
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByDeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where de is greater than or equal to DEFAULT_DE
        defaultPeriodoLancamentoNotaShouldBeFound("de.greaterThanOrEqual=" + DEFAULT_DE);

        // Get all the periodoLancamentoNotaList where de is greater than or equal to UPDATED_DE
        defaultPeriodoLancamentoNotaShouldNotBeFound("de.greaterThanOrEqual=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByDeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where de is less than or equal to DEFAULT_DE
        defaultPeriodoLancamentoNotaShouldBeFound("de.lessThanOrEqual=" + DEFAULT_DE);

        // Get all the periodoLancamentoNotaList where de is less than or equal to SMALLER_DE
        defaultPeriodoLancamentoNotaShouldNotBeFound("de.lessThanOrEqual=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByDeIsLessThanSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where de is less than DEFAULT_DE
        defaultPeriodoLancamentoNotaShouldNotBeFound("de.lessThan=" + DEFAULT_DE);

        // Get all the periodoLancamentoNotaList where de is less than UPDATED_DE
        defaultPeriodoLancamentoNotaShouldBeFound("de.lessThan=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByDeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where de is greater than DEFAULT_DE
        defaultPeriodoLancamentoNotaShouldNotBeFound("de.greaterThan=" + DEFAULT_DE);

        // Get all the periodoLancamentoNotaList where de is greater than SMALLER_DE
        defaultPeriodoLancamentoNotaShouldBeFound("de.greaterThan=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByAteIsEqualToSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where ate equals to DEFAULT_ATE
        defaultPeriodoLancamentoNotaShouldBeFound("ate.equals=" + DEFAULT_ATE);

        // Get all the periodoLancamentoNotaList where ate equals to UPDATED_ATE
        defaultPeriodoLancamentoNotaShouldNotBeFound("ate.equals=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByAteIsInShouldWork() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where ate in DEFAULT_ATE or UPDATED_ATE
        defaultPeriodoLancamentoNotaShouldBeFound("ate.in=" + DEFAULT_ATE + "," + UPDATED_ATE);

        // Get all the periodoLancamentoNotaList where ate equals to UPDATED_ATE
        defaultPeriodoLancamentoNotaShouldNotBeFound("ate.in=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByAteIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where ate is not null
        defaultPeriodoLancamentoNotaShouldBeFound("ate.specified=true");

        // Get all the periodoLancamentoNotaList where ate is null
        defaultPeriodoLancamentoNotaShouldNotBeFound("ate.specified=false");
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByAteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where ate is greater than or equal to DEFAULT_ATE
        defaultPeriodoLancamentoNotaShouldBeFound("ate.greaterThanOrEqual=" + DEFAULT_ATE);

        // Get all the periodoLancamentoNotaList where ate is greater than or equal to UPDATED_ATE
        defaultPeriodoLancamentoNotaShouldNotBeFound("ate.greaterThanOrEqual=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByAteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where ate is less than or equal to DEFAULT_ATE
        defaultPeriodoLancamentoNotaShouldBeFound("ate.lessThanOrEqual=" + DEFAULT_ATE);

        // Get all the periodoLancamentoNotaList where ate is less than or equal to SMALLER_ATE
        defaultPeriodoLancamentoNotaShouldNotBeFound("ate.lessThanOrEqual=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByAteIsLessThanSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where ate is less than DEFAULT_ATE
        defaultPeriodoLancamentoNotaShouldNotBeFound("ate.lessThan=" + DEFAULT_ATE);

        // Get all the periodoLancamentoNotaList where ate is less than UPDATED_ATE
        defaultPeriodoLancamentoNotaShouldBeFound("ate.lessThan=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByAteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where ate is greater than DEFAULT_ATE
        defaultPeriodoLancamentoNotaShouldNotBeFound("ate.greaterThan=" + DEFAULT_ATE);

        // Get all the periodoLancamentoNotaList where ate is greater than SMALLER_ATE
        defaultPeriodoLancamentoNotaShouldBeFound("ate.greaterThan=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where timestamp equals to DEFAULT_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the periodoLancamentoNotaList where timestamp equals to UPDATED_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the periodoLancamentoNotaList where timestamp equals to UPDATED_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where timestamp is not null
        defaultPeriodoLancamentoNotaShouldBeFound("timestamp.specified=true");

        // Get all the periodoLancamentoNotaList where timestamp is null
        defaultPeriodoLancamentoNotaShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the periodoLancamentoNotaList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the periodoLancamentoNotaList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where timestamp is less than DEFAULT_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the periodoLancamentoNotaList where timestamp is less than UPDATED_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        // Get all the periodoLancamentoNotaList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the periodoLancamentoNotaList where timestamp is greater than SMALLER_TIMESTAMP
        defaultPeriodoLancamentoNotaShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        periodoLancamentoNota.setUtilizador(utilizador);
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);
        Long utilizadorId = utilizador.getId();

        // Get all the periodoLancamentoNotaList where utilizador equals to utilizadorId
        defaultPeriodoLancamentoNotaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the periodoLancamentoNotaList where utilizador equals to (utilizadorId + 1)
        defaultPeriodoLancamentoNotaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllPeriodoLancamentoNotasByClasseIsEqualToSomething() throws Exception {
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);
            classe = ClasseResourceIT.createEntity(em);
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        em.persist(classe);
        em.flush();
        periodoLancamentoNota.addClasse(classe);
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);
        Long classeId = classe.getId();

        // Get all the periodoLancamentoNotaList where classe equals to classeId
        defaultPeriodoLancamentoNotaShouldBeFound("classeId.equals=" + classeId);

        // Get all the periodoLancamentoNotaList where classe equals to (classeId + 1)
        defaultPeriodoLancamentoNotaShouldNotBeFound("classeId.equals=" + (classeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPeriodoLancamentoNotaShouldBeFound(String filter) throws Exception {
        restPeriodoLancamentoNotaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodoLancamentoNota.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoAvaliacao").value(hasItem(DEFAULT_TIPO_AVALIACAO.toString())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(sameInstant(DEFAULT_DE))))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(sameInstant(DEFAULT_ATE))))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restPeriodoLancamentoNotaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPeriodoLancamentoNotaShouldNotBeFound(String filter) throws Exception {
        restPeriodoLancamentoNotaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPeriodoLancamentoNotaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPeriodoLancamentoNota() throws Exception {
        // Get the periodoLancamentoNota
        restPeriodoLancamentoNotaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPeriodoLancamentoNota() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        int databaseSizeBeforeUpdate = periodoLancamentoNotaRepository.findAll().size();

        // Update the periodoLancamentoNota
        PeriodoLancamentoNota updatedPeriodoLancamentoNota = periodoLancamentoNotaRepository.findById(periodoLancamentoNota.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodoLancamentoNota are not directly saved in db
        em.detach(updatedPeriodoLancamentoNota);
        updatedPeriodoLancamentoNota.tipoAvaliacao(UPDATED_TIPO_AVALIACAO).de(UPDATED_DE).ate(UPDATED_ATE).timestamp(UPDATED_TIMESTAMP);
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(updatedPeriodoLancamentoNota);

        restPeriodoLancamentoNotaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodoLancamentoNotaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoLancamentoNota in the database
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeUpdate);
        PeriodoLancamentoNota testPeriodoLancamentoNota = periodoLancamentoNotaList.get(periodoLancamentoNotaList.size() - 1);
        assertThat(testPeriodoLancamentoNota.getTipoAvaliacao()).isEqualTo(UPDATED_TIPO_AVALIACAO);
        assertThat(testPeriodoLancamentoNota.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testPeriodoLancamentoNota.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testPeriodoLancamentoNota.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingPeriodoLancamentoNota() throws Exception {
        int databaseSizeBeforeUpdate = periodoLancamentoNotaRepository.findAll().size();
        periodoLancamentoNota.setId(count.incrementAndGet());

        // Create the PeriodoLancamentoNota
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoLancamentoNotaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodoLancamentoNotaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoLancamentoNota in the database
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPeriodoLancamentoNota() throws Exception {
        int databaseSizeBeforeUpdate = periodoLancamentoNotaRepository.findAll().size();
        periodoLancamentoNota.setId(count.incrementAndGet());

        // Create the PeriodoLancamentoNota
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoLancamentoNotaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoLancamentoNota in the database
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPeriodoLancamentoNota() throws Exception {
        int databaseSizeBeforeUpdate = periodoLancamentoNotaRepository.findAll().size();
        periodoLancamentoNota.setId(count.incrementAndGet());

        // Create the PeriodoLancamentoNota
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoLancamentoNotaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PeriodoLancamentoNota in the database
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePeriodoLancamentoNotaWithPatch() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        int databaseSizeBeforeUpdate = periodoLancamentoNotaRepository.findAll().size();

        // Update the periodoLancamentoNota using partial update
        PeriodoLancamentoNota partialUpdatedPeriodoLancamentoNota = new PeriodoLancamentoNota();
        partialUpdatedPeriodoLancamentoNota.setId(periodoLancamentoNota.getId());

        partialUpdatedPeriodoLancamentoNota.tipoAvaliacao(UPDATED_TIPO_AVALIACAO).de(UPDATED_DE);

        restPeriodoLancamentoNotaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodoLancamentoNota.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriodoLancamentoNota))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoLancamentoNota in the database
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeUpdate);
        PeriodoLancamentoNota testPeriodoLancamentoNota = periodoLancamentoNotaList.get(periodoLancamentoNotaList.size() - 1);
        assertThat(testPeriodoLancamentoNota.getTipoAvaliacao()).isEqualTo(UPDATED_TIPO_AVALIACAO);
        assertThat(testPeriodoLancamentoNota.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testPeriodoLancamentoNota.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testPeriodoLancamentoNota.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdatePeriodoLancamentoNotaWithPatch() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        int databaseSizeBeforeUpdate = periodoLancamentoNotaRepository.findAll().size();

        // Update the periodoLancamentoNota using partial update
        PeriodoLancamentoNota partialUpdatedPeriodoLancamentoNota = new PeriodoLancamentoNota();
        partialUpdatedPeriodoLancamentoNota.setId(periodoLancamentoNota.getId());

        partialUpdatedPeriodoLancamentoNota
            .tipoAvaliacao(UPDATED_TIPO_AVALIACAO)
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .timestamp(UPDATED_TIMESTAMP);

        restPeriodoLancamentoNotaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodoLancamentoNota.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriodoLancamentoNota))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoLancamentoNota in the database
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeUpdate);
        PeriodoLancamentoNota testPeriodoLancamentoNota = periodoLancamentoNotaList.get(periodoLancamentoNotaList.size() - 1);
        assertThat(testPeriodoLancamentoNota.getTipoAvaliacao()).isEqualTo(UPDATED_TIPO_AVALIACAO);
        assertThat(testPeriodoLancamentoNota.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testPeriodoLancamentoNota.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testPeriodoLancamentoNota.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingPeriodoLancamentoNota() throws Exception {
        int databaseSizeBeforeUpdate = periodoLancamentoNotaRepository.findAll().size();
        periodoLancamentoNota.setId(count.incrementAndGet());

        // Create the PeriodoLancamentoNota
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoLancamentoNotaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, periodoLancamentoNotaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoLancamentoNota in the database
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPeriodoLancamentoNota() throws Exception {
        int databaseSizeBeforeUpdate = periodoLancamentoNotaRepository.findAll().size();
        periodoLancamentoNota.setId(count.incrementAndGet());

        // Create the PeriodoLancamentoNota
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoLancamentoNotaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoLancamentoNota in the database
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPeriodoLancamentoNota() throws Exception {
        int databaseSizeBeforeUpdate = periodoLancamentoNotaRepository.findAll().size();
        periodoLancamentoNota.setId(count.incrementAndGet());

        // Create the PeriodoLancamentoNota
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO = periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoLancamentoNotaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodoLancamentoNotaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PeriodoLancamentoNota in the database
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePeriodoLancamentoNota() throws Exception {
        // Initialize the database
        periodoLancamentoNotaRepository.saveAndFlush(periodoLancamentoNota);

        int databaseSizeBeforeDelete = periodoLancamentoNotaRepository.findAll().size();

        // Delete the periodoLancamentoNota
        restPeriodoLancamentoNotaMockMvc
            .perform(delete(ENTITY_API_URL_ID, periodoLancamentoNota.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PeriodoLancamentoNota> periodoLancamentoNotaList = periodoLancamentoNotaRepository.findAll();
        assertThat(periodoLancamentoNotaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
