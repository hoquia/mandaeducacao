package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.domain.PeriodoHorario;
import com.ravunana.longonkelo.domain.Turno;
import com.ravunana.longonkelo.repository.PeriodoHorarioRepository;
import com.ravunana.longonkelo.service.PeriodoHorarioService;
import com.ravunana.longonkelo.service.criteria.PeriodoHorarioCriteria;
import com.ravunana.longonkelo.service.dto.PeriodoHorarioDTO;
import com.ravunana.longonkelo.service.mapper.PeriodoHorarioMapper;
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
 * Integration tests for the {@link PeriodoHorarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PeriodoHorarioResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TEMPO = 1;
    private static final Integer UPDATED_TEMPO = 2;
    private static final Integer SMALLER_TEMPO = 1 - 1;

    private static final String DEFAULT_INICIO = "AAAAAAAAAA";
    private static final String UPDATED_INICIO = "BBBBBBBBBB";

    private static final String DEFAULT_FIM = "AAAAAAAAAA";
    private static final String UPDATED_FIM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/periodo-horarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PeriodoHorarioRepository periodoHorarioRepository;

    @Mock
    private PeriodoHorarioRepository periodoHorarioRepositoryMock;

    @Autowired
    private PeriodoHorarioMapper periodoHorarioMapper;

    @Mock
    private PeriodoHorarioService periodoHorarioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodoHorarioMockMvc;

    private PeriodoHorario periodoHorario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodoHorario createEntity(EntityManager em) {
        PeriodoHorario periodoHorario = new PeriodoHorario()
            .descricao(DEFAULT_DESCRICAO)
            .tempo(DEFAULT_TEMPO)
            .inicio(DEFAULT_INICIO)
            .fim(DEFAULT_FIM);
        // Add required entity
        Turno turno;
        if (TestUtil.findAll(em, Turno.class).isEmpty()) {
            turno = TurnoResourceIT.createEntity(em);
            em.persist(turno);
            em.flush();
        } else {
            turno = TestUtil.findAll(em, Turno.class).get(0);
        }
        periodoHorario.setTurno(turno);
        return periodoHorario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodoHorario createUpdatedEntity(EntityManager em) {
        PeriodoHorario periodoHorario = new PeriodoHorario()
            .descricao(UPDATED_DESCRICAO)
            .tempo(UPDATED_TEMPO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM);
        // Add required entity
        Turno turno;
        if (TestUtil.findAll(em, Turno.class).isEmpty()) {
            turno = TurnoResourceIT.createUpdatedEntity(em);
            em.persist(turno);
            em.flush();
        } else {
            turno = TestUtil.findAll(em, Turno.class).get(0);
        }
        periodoHorario.setTurno(turno);
        return periodoHorario;
    }

    @BeforeEach
    public void initTest() {
        periodoHorario = createEntity(em);
    }

    @Test
    @Transactional
    void createPeriodoHorario() throws Exception {
        int databaseSizeBeforeCreate = periodoHorarioRepository.findAll().size();
        // Create the PeriodoHorario
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);
        restPeriodoHorarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PeriodoHorario in the database
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeCreate + 1);
        PeriodoHorario testPeriodoHorario = periodoHorarioList.get(periodoHorarioList.size() - 1);
        assertThat(testPeriodoHorario.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPeriodoHorario.getTempo()).isEqualTo(DEFAULT_TEMPO);
        assertThat(testPeriodoHorario.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testPeriodoHorario.getFim()).isEqualTo(DEFAULT_FIM);
    }

    @Test
    @Transactional
    void createPeriodoHorarioWithExistingId() throws Exception {
        // Create the PeriodoHorario with an existing ID
        periodoHorario.setId(1L);
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);

        int databaseSizeBeforeCreate = periodoHorarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodoHorarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoHorario in the database
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoHorarioRepository.findAll().size();
        // set the field null
        periodoHorario.setDescricao(null);

        // Create the PeriodoHorario, which fails.
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);

        restPeriodoHorarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTempoIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoHorarioRepository.findAll().size();
        // set the field null
        periodoHorario.setTempo(null);

        // Create the PeriodoHorario, which fails.
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);

        restPeriodoHorarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoHorarioRepository.findAll().size();
        // set the field null
        periodoHorario.setInicio(null);

        // Create the PeriodoHorario, which fails.
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);

        restPeriodoHorarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoHorarioRepository.findAll().size();
        // set the field null
        periodoHorario.setFim(null);

        // Create the PeriodoHorario, which fails.
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);

        restPeriodoHorarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPeriodoHorarios() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList
        restPeriodoHorarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodoHorario.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].tempo").value(hasItem(DEFAULT_TEMPO)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO)))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(DEFAULT_FIM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPeriodoHorariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(periodoHorarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPeriodoHorarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(periodoHorarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPeriodoHorariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(periodoHorarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPeriodoHorarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(periodoHorarioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPeriodoHorario() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get the periodoHorario
        restPeriodoHorarioMockMvc
            .perform(get(ENTITY_API_URL_ID, periodoHorario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(periodoHorario.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.tempo").value(DEFAULT_TEMPO))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO))
            .andExpect(jsonPath("$.fim").value(DEFAULT_FIM));
    }

    @Test
    @Transactional
    void getPeriodoHorariosByIdFiltering() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        Long id = periodoHorario.getId();

        defaultPeriodoHorarioShouldBeFound("id.equals=" + id);
        defaultPeriodoHorarioShouldNotBeFound("id.notEquals=" + id);

        defaultPeriodoHorarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPeriodoHorarioShouldNotBeFound("id.greaterThan=" + id);

        defaultPeriodoHorarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPeriodoHorarioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where descricao equals to DEFAULT_DESCRICAO
        defaultPeriodoHorarioShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the periodoHorarioList where descricao equals to UPDATED_DESCRICAO
        defaultPeriodoHorarioShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPeriodoHorarioShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the periodoHorarioList where descricao equals to UPDATED_DESCRICAO
        defaultPeriodoHorarioShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where descricao is not null
        defaultPeriodoHorarioShouldBeFound("descricao.specified=true");

        // Get all the periodoHorarioList where descricao is null
        defaultPeriodoHorarioShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where descricao contains DEFAULT_DESCRICAO
        defaultPeriodoHorarioShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the periodoHorarioList where descricao contains UPDATED_DESCRICAO
        defaultPeriodoHorarioShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where descricao does not contain DEFAULT_DESCRICAO
        defaultPeriodoHorarioShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the periodoHorarioList where descricao does not contain UPDATED_DESCRICAO
        defaultPeriodoHorarioShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByTempoIsEqualToSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where tempo equals to DEFAULT_TEMPO
        defaultPeriodoHorarioShouldBeFound("tempo.equals=" + DEFAULT_TEMPO);

        // Get all the periodoHorarioList where tempo equals to UPDATED_TEMPO
        defaultPeriodoHorarioShouldNotBeFound("tempo.equals=" + UPDATED_TEMPO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByTempoIsInShouldWork() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where tempo in DEFAULT_TEMPO or UPDATED_TEMPO
        defaultPeriodoHorarioShouldBeFound("tempo.in=" + DEFAULT_TEMPO + "," + UPDATED_TEMPO);

        // Get all the periodoHorarioList where tempo equals to UPDATED_TEMPO
        defaultPeriodoHorarioShouldNotBeFound("tempo.in=" + UPDATED_TEMPO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByTempoIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where tempo is not null
        defaultPeriodoHorarioShouldBeFound("tempo.specified=true");

        // Get all the periodoHorarioList where tempo is null
        defaultPeriodoHorarioShouldNotBeFound("tempo.specified=false");
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByTempoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where tempo is greater than or equal to DEFAULT_TEMPO
        defaultPeriodoHorarioShouldBeFound("tempo.greaterThanOrEqual=" + DEFAULT_TEMPO);

        // Get all the periodoHorarioList where tempo is greater than or equal to UPDATED_TEMPO
        defaultPeriodoHorarioShouldNotBeFound("tempo.greaterThanOrEqual=" + UPDATED_TEMPO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByTempoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where tempo is less than or equal to DEFAULT_TEMPO
        defaultPeriodoHorarioShouldBeFound("tempo.lessThanOrEqual=" + DEFAULT_TEMPO);

        // Get all the periodoHorarioList where tempo is less than or equal to SMALLER_TEMPO
        defaultPeriodoHorarioShouldNotBeFound("tempo.lessThanOrEqual=" + SMALLER_TEMPO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByTempoIsLessThanSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where tempo is less than DEFAULT_TEMPO
        defaultPeriodoHorarioShouldNotBeFound("tempo.lessThan=" + DEFAULT_TEMPO);

        // Get all the periodoHorarioList where tempo is less than UPDATED_TEMPO
        defaultPeriodoHorarioShouldBeFound("tempo.lessThan=" + UPDATED_TEMPO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByTempoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where tempo is greater than DEFAULT_TEMPO
        defaultPeriodoHorarioShouldNotBeFound("tempo.greaterThan=" + DEFAULT_TEMPO);

        // Get all the periodoHorarioList where tempo is greater than SMALLER_TEMPO
        defaultPeriodoHorarioShouldBeFound("tempo.greaterThan=" + SMALLER_TEMPO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where inicio equals to DEFAULT_INICIO
        defaultPeriodoHorarioShouldBeFound("inicio.equals=" + DEFAULT_INICIO);

        // Get all the periodoHorarioList where inicio equals to UPDATED_INICIO
        defaultPeriodoHorarioShouldNotBeFound("inicio.equals=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByInicioIsInShouldWork() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where inicio in DEFAULT_INICIO or UPDATED_INICIO
        defaultPeriodoHorarioShouldBeFound("inicio.in=" + DEFAULT_INICIO + "," + UPDATED_INICIO);

        // Get all the periodoHorarioList where inicio equals to UPDATED_INICIO
        defaultPeriodoHorarioShouldNotBeFound("inicio.in=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where inicio is not null
        defaultPeriodoHorarioShouldBeFound("inicio.specified=true");

        // Get all the periodoHorarioList where inicio is null
        defaultPeriodoHorarioShouldNotBeFound("inicio.specified=false");
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByInicioContainsSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where inicio contains DEFAULT_INICIO
        defaultPeriodoHorarioShouldBeFound("inicio.contains=" + DEFAULT_INICIO);

        // Get all the periodoHorarioList where inicio contains UPDATED_INICIO
        defaultPeriodoHorarioShouldNotBeFound("inicio.contains=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByInicioNotContainsSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where inicio does not contain DEFAULT_INICIO
        defaultPeriodoHorarioShouldNotBeFound("inicio.doesNotContain=" + DEFAULT_INICIO);

        // Get all the periodoHorarioList where inicio does not contain UPDATED_INICIO
        defaultPeriodoHorarioShouldBeFound("inicio.doesNotContain=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByFimIsEqualToSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where fim equals to DEFAULT_FIM
        defaultPeriodoHorarioShouldBeFound("fim.equals=" + DEFAULT_FIM);

        // Get all the periodoHorarioList where fim equals to UPDATED_FIM
        defaultPeriodoHorarioShouldNotBeFound("fim.equals=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByFimIsInShouldWork() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where fim in DEFAULT_FIM or UPDATED_FIM
        defaultPeriodoHorarioShouldBeFound("fim.in=" + DEFAULT_FIM + "," + UPDATED_FIM);

        // Get all the periodoHorarioList where fim equals to UPDATED_FIM
        defaultPeriodoHorarioShouldNotBeFound("fim.in=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByFimIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where fim is not null
        defaultPeriodoHorarioShouldBeFound("fim.specified=true");

        // Get all the periodoHorarioList where fim is null
        defaultPeriodoHorarioShouldNotBeFound("fim.specified=false");
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByFimContainsSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where fim contains DEFAULT_FIM
        defaultPeriodoHorarioShouldBeFound("fim.contains=" + DEFAULT_FIM);

        // Get all the periodoHorarioList where fim contains UPDATED_FIM
        defaultPeriodoHorarioShouldNotBeFound("fim.contains=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByFimNotContainsSomething() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        // Get all the periodoHorarioList where fim does not contain DEFAULT_FIM
        defaultPeriodoHorarioShouldNotBeFound("fim.doesNotContain=" + DEFAULT_FIM);

        // Get all the periodoHorarioList where fim does not contain UPDATED_FIM
        defaultPeriodoHorarioShouldBeFound("fim.doesNotContain=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByHorarioIsEqualToSomething() throws Exception {
        Horario horario;
        if (TestUtil.findAll(em, Horario.class).isEmpty()) {
            periodoHorarioRepository.saveAndFlush(periodoHorario);
            horario = HorarioResourceIT.createEntity(em);
        } else {
            horario = TestUtil.findAll(em, Horario.class).get(0);
        }
        em.persist(horario);
        em.flush();
        periodoHorario.addHorario(horario);
        periodoHorarioRepository.saveAndFlush(periodoHorario);
        Long horarioId = horario.getId();

        // Get all the periodoHorarioList where horario equals to horarioId
        defaultPeriodoHorarioShouldBeFound("horarioId.equals=" + horarioId);

        // Get all the periodoHorarioList where horario equals to (horarioId + 1)
        defaultPeriodoHorarioShouldNotBeFound("horarioId.equals=" + (horarioId + 1));
    }

    @Test
    @Transactional
    void getAllPeriodoHorariosByTurnoIsEqualToSomething() throws Exception {
        Turno turno;
        if (TestUtil.findAll(em, Turno.class).isEmpty()) {
            periodoHorarioRepository.saveAndFlush(periodoHorario);
            turno = TurnoResourceIT.createEntity(em);
        } else {
            turno = TestUtil.findAll(em, Turno.class).get(0);
        }
        em.persist(turno);
        em.flush();
        periodoHorario.setTurno(turno);
        periodoHorarioRepository.saveAndFlush(periodoHorario);
        Long turnoId = turno.getId();

        // Get all the periodoHorarioList where turno equals to turnoId
        defaultPeriodoHorarioShouldBeFound("turnoId.equals=" + turnoId);

        // Get all the periodoHorarioList where turno equals to (turnoId + 1)
        defaultPeriodoHorarioShouldNotBeFound("turnoId.equals=" + (turnoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPeriodoHorarioShouldBeFound(String filter) throws Exception {
        restPeriodoHorarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodoHorario.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].tempo").value(hasItem(DEFAULT_TEMPO)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO)))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(DEFAULT_FIM)));

        // Check, that the count call also returns 1
        restPeriodoHorarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPeriodoHorarioShouldNotBeFound(String filter) throws Exception {
        restPeriodoHorarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPeriodoHorarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPeriodoHorario() throws Exception {
        // Get the periodoHorario
        restPeriodoHorarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPeriodoHorario() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        int databaseSizeBeforeUpdate = periodoHorarioRepository.findAll().size();

        // Update the periodoHorario
        PeriodoHorario updatedPeriodoHorario = periodoHorarioRepository.findById(periodoHorario.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodoHorario are not directly saved in db
        em.detach(updatedPeriodoHorario);
        updatedPeriodoHorario.descricao(UPDATED_DESCRICAO).tempo(UPDATED_TEMPO).inicio(UPDATED_INICIO).fim(UPDATED_FIM);
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(updatedPeriodoHorario);

        restPeriodoHorarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodoHorarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoHorario in the database
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeUpdate);
        PeriodoHorario testPeriodoHorario = periodoHorarioList.get(periodoHorarioList.size() - 1);
        assertThat(testPeriodoHorario.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPeriodoHorario.getTempo()).isEqualTo(UPDATED_TEMPO);
        assertThat(testPeriodoHorario.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testPeriodoHorario.getFim()).isEqualTo(UPDATED_FIM);
    }

    @Test
    @Transactional
    void putNonExistingPeriodoHorario() throws Exception {
        int databaseSizeBeforeUpdate = periodoHorarioRepository.findAll().size();
        periodoHorario.setId(count.incrementAndGet());

        // Create the PeriodoHorario
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoHorarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodoHorarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoHorario in the database
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPeriodoHorario() throws Exception {
        int databaseSizeBeforeUpdate = periodoHorarioRepository.findAll().size();
        periodoHorario.setId(count.incrementAndGet());

        // Create the PeriodoHorario
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoHorarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoHorario in the database
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPeriodoHorario() throws Exception {
        int databaseSizeBeforeUpdate = periodoHorarioRepository.findAll().size();
        periodoHorario.setId(count.incrementAndGet());

        // Create the PeriodoHorario
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoHorarioMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PeriodoHorario in the database
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePeriodoHorarioWithPatch() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        int databaseSizeBeforeUpdate = periodoHorarioRepository.findAll().size();

        // Update the periodoHorario using partial update
        PeriodoHorario partialUpdatedPeriodoHorario = new PeriodoHorario();
        partialUpdatedPeriodoHorario.setId(periodoHorario.getId());

        partialUpdatedPeriodoHorario.fim(UPDATED_FIM);

        restPeriodoHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodoHorario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriodoHorario))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoHorario in the database
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeUpdate);
        PeriodoHorario testPeriodoHorario = periodoHorarioList.get(periodoHorarioList.size() - 1);
        assertThat(testPeriodoHorario.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPeriodoHorario.getTempo()).isEqualTo(DEFAULT_TEMPO);
        assertThat(testPeriodoHorario.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testPeriodoHorario.getFim()).isEqualTo(UPDATED_FIM);
    }

    @Test
    @Transactional
    void fullUpdatePeriodoHorarioWithPatch() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        int databaseSizeBeforeUpdate = periodoHorarioRepository.findAll().size();

        // Update the periodoHorario using partial update
        PeriodoHorario partialUpdatedPeriodoHorario = new PeriodoHorario();
        partialUpdatedPeriodoHorario.setId(periodoHorario.getId());

        partialUpdatedPeriodoHorario.descricao(UPDATED_DESCRICAO).tempo(UPDATED_TEMPO).inicio(UPDATED_INICIO).fim(UPDATED_FIM);

        restPeriodoHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodoHorario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriodoHorario))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoHorario in the database
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeUpdate);
        PeriodoHorario testPeriodoHorario = periodoHorarioList.get(periodoHorarioList.size() - 1);
        assertThat(testPeriodoHorario.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPeriodoHorario.getTempo()).isEqualTo(UPDATED_TEMPO);
        assertThat(testPeriodoHorario.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testPeriodoHorario.getFim()).isEqualTo(UPDATED_FIM);
    }

    @Test
    @Transactional
    void patchNonExistingPeriodoHorario() throws Exception {
        int databaseSizeBeforeUpdate = periodoHorarioRepository.findAll().size();
        periodoHorario.setId(count.incrementAndGet());

        // Create the PeriodoHorario
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, periodoHorarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoHorario in the database
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPeriodoHorario() throws Exception {
        int databaseSizeBeforeUpdate = periodoHorarioRepository.findAll().size();
        periodoHorario.setId(count.incrementAndGet());

        // Create the PeriodoHorario
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoHorario in the database
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPeriodoHorario() throws Exception {
        int databaseSizeBeforeUpdate = periodoHorarioRepository.findAll().size();
        periodoHorario.setId(count.incrementAndGet());

        // Create the PeriodoHorario
        PeriodoHorarioDTO periodoHorarioDTO = periodoHorarioMapper.toDto(periodoHorario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodoHorarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PeriodoHorario in the database
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePeriodoHorario() throws Exception {
        // Initialize the database
        periodoHorarioRepository.saveAndFlush(periodoHorario);

        int databaseSizeBeforeDelete = periodoHorarioRepository.findAll().size();

        // Delete the periodoHorario
        restPeriodoHorarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, periodoHorario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PeriodoHorario> periodoHorarioList = periodoHorarioRepository.findAll();
        assertThat(periodoHorarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
