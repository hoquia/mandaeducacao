package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Disciplina;
import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.NotasGeralDisciplina;
import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.domain.PlanoCurricular;
import com.ravunana.longonkelo.repository.DisciplinaCurricularRepository;
import com.ravunana.longonkelo.service.DisciplinaCurricularService;
import com.ravunana.longonkelo.service.criteria.DisciplinaCurricularCriteria;
import com.ravunana.longonkelo.service.dto.DisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.mapper.DisciplinaCurricularMapper;
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
 * Integration tests for the {@link DisciplinaCurricularResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DisciplinaCurricularResourceIT {

    private static final String DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_DISCIPLINA_CURRICULAR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Double DEFAULT_CARGA_SEMANAL = 0D;
    private static final Double UPDATED_CARGA_SEMANAL = 1D;
    private static final Double SMALLER_CARGA_SEMANAL = 0D - 1D;

    private static final Boolean DEFAULT_IS_TERMINAL = false;
    private static final Boolean UPDATED_IS_TERMINAL = true;

    private static final Double DEFAULT_MEDIA_PARA_EXAME = 0D;
    private static final Double UPDATED_MEDIA_PARA_EXAME = 1D;
    private static final Double SMALLER_MEDIA_PARA_EXAME = 0D - 1D;

    private static final Double DEFAULT_MEDIA_PARA_RECURSO = 0D;
    private static final Double UPDATED_MEDIA_PARA_RECURSO = 1D;
    private static final Double SMALLER_MEDIA_PARA_RECURSO = 0D - 1D;

    private static final Double DEFAULT_MEDIA_PARA_EXAME_ESPECIAL = 0D;
    private static final Double UPDATED_MEDIA_PARA_EXAME_ESPECIAL = 1D;
    private static final Double SMALLER_MEDIA_PARA_EXAME_ESPECIAL = 0D - 1D;

    private static final Double DEFAULT_MEDIA_PARA_DESPENSAR = 0D;
    private static final Double UPDATED_MEDIA_PARA_DESPENSAR = 1D;
    private static final Double SMALLER_MEDIA_PARA_DESPENSAR = 0D - 1D;

    private static final String ENTITY_API_URL = "/api/disciplina-curriculars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DisciplinaCurricularRepository disciplinaCurricularRepository;

    @Mock
    private DisciplinaCurricularRepository disciplinaCurricularRepositoryMock;

    @Autowired
    private DisciplinaCurricularMapper disciplinaCurricularMapper;

    @Mock
    private DisciplinaCurricularService disciplinaCurricularServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisciplinaCurricularMockMvc;

    private DisciplinaCurricular disciplinaCurricular;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisciplinaCurricular createEntity(EntityManager em) {
        DisciplinaCurricular disciplinaCurricular = new DisciplinaCurricular()
            .uniqueDisciplinaCurricular(DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR)
            .descricao(DEFAULT_DESCRICAO)
            .cargaSemanal(DEFAULT_CARGA_SEMANAL)
            .isTerminal(DEFAULT_IS_TERMINAL)
            .mediaParaExame(DEFAULT_MEDIA_PARA_EXAME)
            .mediaParaRecurso(DEFAULT_MEDIA_PARA_RECURSO)
            .mediaParaExameEspecial(DEFAULT_MEDIA_PARA_EXAME_ESPECIAL)
            .mediaParaDespensar(DEFAULT_MEDIA_PARA_DESPENSAR);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        disciplinaCurricular.setDisciplina(disciplina);
        return disciplinaCurricular;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisciplinaCurricular createUpdatedEntity(EntityManager em) {
        DisciplinaCurricular disciplinaCurricular = new DisciplinaCurricular()
            .uniqueDisciplinaCurricular(UPDATED_UNIQUE_DISCIPLINA_CURRICULAR)
            .descricao(UPDATED_DESCRICAO)
            .cargaSemanal(UPDATED_CARGA_SEMANAL)
            .isTerminal(UPDATED_IS_TERMINAL)
            .mediaParaExame(UPDATED_MEDIA_PARA_EXAME)
            .mediaParaRecurso(UPDATED_MEDIA_PARA_RECURSO)
            .mediaParaExameEspecial(UPDATED_MEDIA_PARA_EXAME_ESPECIAL)
            .mediaParaDespensar(UPDATED_MEDIA_PARA_DESPENSAR);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createUpdatedEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        disciplinaCurricular.setDisciplina(disciplina);
        return disciplinaCurricular;
    }

    @BeforeEach
    public void initTest() {
        disciplinaCurricular = createEntity(em);
    }

    @Test
    @Transactional
    void createDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeCreate = disciplinaCurricularRepository.findAll().size();
        // Create the DisciplinaCurricular
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);
        restDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DisciplinaCurricular in the database
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeCreate + 1);
        DisciplinaCurricular testDisciplinaCurricular = disciplinaCurricularList.get(disciplinaCurricularList.size() - 1);
        assertThat(testDisciplinaCurricular.getUniqueDisciplinaCurricular()).isEqualTo(DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR);
        assertThat(testDisciplinaCurricular.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDisciplinaCurricular.getCargaSemanal()).isEqualTo(DEFAULT_CARGA_SEMANAL);
        assertThat(testDisciplinaCurricular.getIsTerminal()).isEqualTo(DEFAULT_IS_TERMINAL);
        assertThat(testDisciplinaCurricular.getMediaParaExame()).isEqualTo(DEFAULT_MEDIA_PARA_EXAME);
        assertThat(testDisciplinaCurricular.getMediaParaRecurso()).isEqualTo(DEFAULT_MEDIA_PARA_RECURSO);
        assertThat(testDisciplinaCurricular.getMediaParaExameEspecial()).isEqualTo(DEFAULT_MEDIA_PARA_EXAME_ESPECIAL);
        assertThat(testDisciplinaCurricular.getMediaParaDespensar()).isEqualTo(DEFAULT_MEDIA_PARA_DESPENSAR);
    }

    @Test
    @Transactional
    void createDisciplinaCurricularWithExistingId() throws Exception {
        // Create the DisciplinaCurricular with an existing ID
        disciplinaCurricular.setId(1L);
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        int databaseSizeBeforeCreate = disciplinaCurricularRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DisciplinaCurricular in the database
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaCurricularRepository.findAll().size();
        // set the field null
        disciplinaCurricular.setDescricao(null);

        // Create the DisciplinaCurricular, which fails.
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        restDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMediaParaExameIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaCurricularRepository.findAll().size();
        // set the field null
        disciplinaCurricular.setMediaParaExame(null);

        // Create the DisciplinaCurricular, which fails.
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        restDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMediaParaRecursoIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaCurricularRepository.findAll().size();
        // set the field null
        disciplinaCurricular.setMediaParaRecurso(null);

        // Create the DisciplinaCurricular, which fails.
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        restDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMediaParaExameEspecialIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaCurricularRepository.findAll().size();
        // set the field null
        disciplinaCurricular.setMediaParaExameEspecial(null);

        // Create the DisciplinaCurricular, which fails.
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        restDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMediaParaDespensarIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaCurricularRepository.findAll().size();
        // set the field null
        disciplinaCurricular.setMediaParaDespensar(null);

        // Create the DisciplinaCurricular, which fails.
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        restDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurriculars() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList
        restDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplinaCurricular.getId().intValue())))
            .andExpect(jsonPath("$.[*].uniqueDisciplinaCurricular").value(hasItem(DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].cargaSemanal").value(hasItem(DEFAULT_CARGA_SEMANAL.doubleValue())))
            .andExpect(jsonPath("$.[*].isTerminal").value(hasItem(DEFAULT_IS_TERMINAL.booleanValue())))
            .andExpect(jsonPath("$.[*].mediaParaExame").value(hasItem(DEFAULT_MEDIA_PARA_EXAME.doubleValue())))
            .andExpect(jsonPath("$.[*].mediaParaRecurso").value(hasItem(DEFAULT_MEDIA_PARA_RECURSO.doubleValue())))
            .andExpect(jsonPath("$.[*].mediaParaExameEspecial").value(hasItem(DEFAULT_MEDIA_PARA_EXAME_ESPECIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].mediaParaDespensar").value(hasItem(DEFAULT_MEDIA_PARA_DESPENSAR.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDisciplinaCurricularsWithEagerRelationshipsIsEnabled() throws Exception {
        when(disciplinaCurricularServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDisciplinaCurricularMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(disciplinaCurricularServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDisciplinaCurricularsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(disciplinaCurricularServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDisciplinaCurricularMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(disciplinaCurricularRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDisciplinaCurricular() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get the disciplinaCurricular
        restDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL_ID, disciplinaCurricular.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disciplinaCurricular.getId().intValue()))
            .andExpect(jsonPath("$.uniqueDisciplinaCurricular").value(DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.cargaSemanal").value(DEFAULT_CARGA_SEMANAL.doubleValue()))
            .andExpect(jsonPath("$.isTerminal").value(DEFAULT_IS_TERMINAL.booleanValue()))
            .andExpect(jsonPath("$.mediaParaExame").value(DEFAULT_MEDIA_PARA_EXAME.doubleValue()))
            .andExpect(jsonPath("$.mediaParaRecurso").value(DEFAULT_MEDIA_PARA_RECURSO.doubleValue()))
            .andExpect(jsonPath("$.mediaParaExameEspecial").value(DEFAULT_MEDIA_PARA_EXAME_ESPECIAL.doubleValue()))
            .andExpect(jsonPath("$.mediaParaDespensar").value(DEFAULT_MEDIA_PARA_DESPENSAR.doubleValue()));
    }

    @Test
    @Transactional
    void getDisciplinaCurricularsByIdFiltering() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        Long id = disciplinaCurricular.getId();

        defaultDisciplinaCurricularShouldBeFound("id.equals=" + id);
        defaultDisciplinaCurricularShouldNotBeFound("id.notEquals=" + id);

        defaultDisciplinaCurricularShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDisciplinaCurricularShouldNotBeFound("id.greaterThan=" + id);

        defaultDisciplinaCurricularShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDisciplinaCurricularShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByUniqueDisciplinaCurricularIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where uniqueDisciplinaCurricular equals to DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR
        defaultDisciplinaCurricularShouldBeFound("uniqueDisciplinaCurricular.equals=" + DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR);

        // Get all the disciplinaCurricularList where uniqueDisciplinaCurricular equals to UPDATED_UNIQUE_DISCIPLINA_CURRICULAR
        defaultDisciplinaCurricularShouldNotBeFound("uniqueDisciplinaCurricular.equals=" + UPDATED_UNIQUE_DISCIPLINA_CURRICULAR);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByUniqueDisciplinaCurricularIsInShouldWork() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where uniqueDisciplinaCurricular in DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR or UPDATED_UNIQUE_DISCIPLINA_CURRICULAR
        defaultDisciplinaCurricularShouldBeFound(
            "uniqueDisciplinaCurricular.in=" + DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR + "," + UPDATED_UNIQUE_DISCIPLINA_CURRICULAR
        );

        // Get all the disciplinaCurricularList where uniqueDisciplinaCurricular equals to UPDATED_UNIQUE_DISCIPLINA_CURRICULAR
        defaultDisciplinaCurricularShouldNotBeFound("uniqueDisciplinaCurricular.in=" + UPDATED_UNIQUE_DISCIPLINA_CURRICULAR);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByUniqueDisciplinaCurricularIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where uniqueDisciplinaCurricular is not null
        defaultDisciplinaCurricularShouldBeFound("uniqueDisciplinaCurricular.specified=true");

        // Get all the disciplinaCurricularList where uniqueDisciplinaCurricular is null
        defaultDisciplinaCurricularShouldNotBeFound("uniqueDisciplinaCurricular.specified=false");
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByUniqueDisciplinaCurricularContainsSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where uniqueDisciplinaCurricular contains DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR
        defaultDisciplinaCurricularShouldBeFound("uniqueDisciplinaCurricular.contains=" + DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR);

        // Get all the disciplinaCurricularList where uniqueDisciplinaCurricular contains UPDATED_UNIQUE_DISCIPLINA_CURRICULAR
        defaultDisciplinaCurricularShouldNotBeFound("uniqueDisciplinaCurricular.contains=" + UPDATED_UNIQUE_DISCIPLINA_CURRICULAR);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByUniqueDisciplinaCurricularNotContainsSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where uniqueDisciplinaCurricular does not contain DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR
        defaultDisciplinaCurricularShouldNotBeFound("uniqueDisciplinaCurricular.doesNotContain=" + DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR);

        // Get all the disciplinaCurricularList where uniqueDisciplinaCurricular does not contain UPDATED_UNIQUE_DISCIPLINA_CURRICULAR
        defaultDisciplinaCurricularShouldBeFound("uniqueDisciplinaCurricular.doesNotContain=" + UPDATED_UNIQUE_DISCIPLINA_CURRICULAR);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where descricao equals to DEFAULT_DESCRICAO
        defaultDisciplinaCurricularShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the disciplinaCurricularList where descricao equals to UPDATED_DESCRICAO
        defaultDisciplinaCurricularShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultDisciplinaCurricularShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the disciplinaCurricularList where descricao equals to UPDATED_DESCRICAO
        defaultDisciplinaCurricularShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where descricao is not null
        defaultDisciplinaCurricularShouldBeFound("descricao.specified=true");

        // Get all the disciplinaCurricularList where descricao is null
        defaultDisciplinaCurricularShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where descricao contains DEFAULT_DESCRICAO
        defaultDisciplinaCurricularShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the disciplinaCurricularList where descricao contains UPDATED_DESCRICAO
        defaultDisciplinaCurricularShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where descricao does not contain DEFAULT_DESCRICAO
        defaultDisciplinaCurricularShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the disciplinaCurricularList where descricao does not contain UPDATED_DESCRICAO
        defaultDisciplinaCurricularShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByCargaSemanalIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where cargaSemanal equals to DEFAULT_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldBeFound("cargaSemanal.equals=" + DEFAULT_CARGA_SEMANAL);

        // Get all the disciplinaCurricularList where cargaSemanal equals to UPDATED_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldNotBeFound("cargaSemanal.equals=" + UPDATED_CARGA_SEMANAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByCargaSemanalIsInShouldWork() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where cargaSemanal in DEFAULT_CARGA_SEMANAL or UPDATED_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldBeFound("cargaSemanal.in=" + DEFAULT_CARGA_SEMANAL + "," + UPDATED_CARGA_SEMANAL);

        // Get all the disciplinaCurricularList where cargaSemanal equals to UPDATED_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldNotBeFound("cargaSemanal.in=" + UPDATED_CARGA_SEMANAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByCargaSemanalIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where cargaSemanal is not null
        defaultDisciplinaCurricularShouldBeFound("cargaSemanal.specified=true");

        // Get all the disciplinaCurricularList where cargaSemanal is null
        defaultDisciplinaCurricularShouldNotBeFound("cargaSemanal.specified=false");
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByCargaSemanalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where cargaSemanal is greater than or equal to DEFAULT_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldBeFound("cargaSemanal.greaterThanOrEqual=" + DEFAULT_CARGA_SEMANAL);

        // Get all the disciplinaCurricularList where cargaSemanal is greater than or equal to UPDATED_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldNotBeFound("cargaSemanal.greaterThanOrEqual=" + UPDATED_CARGA_SEMANAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByCargaSemanalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where cargaSemanal is less than or equal to DEFAULT_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldBeFound("cargaSemanal.lessThanOrEqual=" + DEFAULT_CARGA_SEMANAL);

        // Get all the disciplinaCurricularList where cargaSemanal is less than or equal to SMALLER_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldNotBeFound("cargaSemanal.lessThanOrEqual=" + SMALLER_CARGA_SEMANAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByCargaSemanalIsLessThanSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where cargaSemanal is less than DEFAULT_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldNotBeFound("cargaSemanal.lessThan=" + DEFAULT_CARGA_SEMANAL);

        // Get all the disciplinaCurricularList where cargaSemanal is less than UPDATED_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldBeFound("cargaSemanal.lessThan=" + UPDATED_CARGA_SEMANAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByCargaSemanalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where cargaSemanal is greater than DEFAULT_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldNotBeFound("cargaSemanal.greaterThan=" + DEFAULT_CARGA_SEMANAL);

        // Get all the disciplinaCurricularList where cargaSemanal is greater than SMALLER_CARGA_SEMANAL
        defaultDisciplinaCurricularShouldBeFound("cargaSemanal.greaterThan=" + SMALLER_CARGA_SEMANAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByIsTerminalIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where isTerminal equals to DEFAULT_IS_TERMINAL
        defaultDisciplinaCurricularShouldBeFound("isTerminal.equals=" + DEFAULT_IS_TERMINAL);

        // Get all the disciplinaCurricularList where isTerminal equals to UPDATED_IS_TERMINAL
        defaultDisciplinaCurricularShouldNotBeFound("isTerminal.equals=" + UPDATED_IS_TERMINAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByIsTerminalIsInShouldWork() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where isTerminal in DEFAULT_IS_TERMINAL or UPDATED_IS_TERMINAL
        defaultDisciplinaCurricularShouldBeFound("isTerminal.in=" + DEFAULT_IS_TERMINAL + "," + UPDATED_IS_TERMINAL);

        // Get all the disciplinaCurricularList where isTerminal equals to UPDATED_IS_TERMINAL
        defaultDisciplinaCurricularShouldNotBeFound("isTerminal.in=" + UPDATED_IS_TERMINAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByIsTerminalIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where isTerminal is not null
        defaultDisciplinaCurricularShouldBeFound("isTerminal.specified=true");

        // Get all the disciplinaCurricularList where isTerminal is null
        defaultDisciplinaCurricularShouldNotBeFound("isTerminal.specified=false");
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExame equals to DEFAULT_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldBeFound("mediaParaExame.equals=" + DEFAULT_MEDIA_PARA_EXAME);

        // Get all the disciplinaCurricularList where mediaParaExame equals to UPDATED_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExame.equals=" + UPDATED_MEDIA_PARA_EXAME);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameIsInShouldWork() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExame in DEFAULT_MEDIA_PARA_EXAME or UPDATED_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldBeFound("mediaParaExame.in=" + DEFAULT_MEDIA_PARA_EXAME + "," + UPDATED_MEDIA_PARA_EXAME);

        // Get all the disciplinaCurricularList where mediaParaExame equals to UPDATED_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExame.in=" + UPDATED_MEDIA_PARA_EXAME);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExame is not null
        defaultDisciplinaCurricularShouldBeFound("mediaParaExame.specified=true");

        // Get all the disciplinaCurricularList where mediaParaExame is null
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExame.specified=false");
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExame is greater than or equal to DEFAULT_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldBeFound("mediaParaExame.greaterThanOrEqual=" + DEFAULT_MEDIA_PARA_EXAME);

        // Get all the disciplinaCurricularList where mediaParaExame is greater than or equal to UPDATED_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExame.greaterThanOrEqual=" + UPDATED_MEDIA_PARA_EXAME);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExame is less than or equal to DEFAULT_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldBeFound("mediaParaExame.lessThanOrEqual=" + DEFAULT_MEDIA_PARA_EXAME);

        // Get all the disciplinaCurricularList where mediaParaExame is less than or equal to SMALLER_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExame.lessThanOrEqual=" + SMALLER_MEDIA_PARA_EXAME);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameIsLessThanSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExame is less than DEFAULT_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExame.lessThan=" + DEFAULT_MEDIA_PARA_EXAME);

        // Get all the disciplinaCurricularList where mediaParaExame is less than UPDATED_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldBeFound("mediaParaExame.lessThan=" + UPDATED_MEDIA_PARA_EXAME);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameIsGreaterThanSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExame is greater than DEFAULT_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExame.greaterThan=" + DEFAULT_MEDIA_PARA_EXAME);

        // Get all the disciplinaCurricularList where mediaParaExame is greater than SMALLER_MEDIA_PARA_EXAME
        defaultDisciplinaCurricularShouldBeFound("mediaParaExame.greaterThan=" + SMALLER_MEDIA_PARA_EXAME);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaRecurso equals to DEFAULT_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldBeFound("mediaParaRecurso.equals=" + DEFAULT_MEDIA_PARA_RECURSO);

        // Get all the disciplinaCurricularList where mediaParaRecurso equals to UPDATED_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaRecurso.equals=" + UPDATED_MEDIA_PARA_RECURSO);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaRecursoIsInShouldWork() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaRecurso in DEFAULT_MEDIA_PARA_RECURSO or UPDATED_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldBeFound("mediaParaRecurso.in=" + DEFAULT_MEDIA_PARA_RECURSO + "," + UPDATED_MEDIA_PARA_RECURSO);

        // Get all the disciplinaCurricularList where mediaParaRecurso equals to UPDATED_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaRecurso.in=" + UPDATED_MEDIA_PARA_RECURSO);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaRecursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaRecurso is not null
        defaultDisciplinaCurricularShouldBeFound("mediaParaRecurso.specified=true");

        // Get all the disciplinaCurricularList where mediaParaRecurso is null
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaRecurso.specified=false");
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaRecursoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaRecurso is greater than or equal to DEFAULT_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldBeFound("mediaParaRecurso.greaterThanOrEqual=" + DEFAULT_MEDIA_PARA_RECURSO);

        // Get all the disciplinaCurricularList where mediaParaRecurso is greater than or equal to UPDATED_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaRecurso.greaterThanOrEqual=" + UPDATED_MEDIA_PARA_RECURSO);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaRecursoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaRecurso is less than or equal to DEFAULT_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldBeFound("mediaParaRecurso.lessThanOrEqual=" + DEFAULT_MEDIA_PARA_RECURSO);

        // Get all the disciplinaCurricularList where mediaParaRecurso is less than or equal to SMALLER_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaRecurso.lessThanOrEqual=" + SMALLER_MEDIA_PARA_RECURSO);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaRecursoIsLessThanSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaRecurso is less than DEFAULT_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaRecurso.lessThan=" + DEFAULT_MEDIA_PARA_RECURSO);

        // Get all the disciplinaCurricularList where mediaParaRecurso is less than UPDATED_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldBeFound("mediaParaRecurso.lessThan=" + UPDATED_MEDIA_PARA_RECURSO);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaRecursoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaRecurso is greater than DEFAULT_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaRecurso.greaterThan=" + DEFAULT_MEDIA_PARA_RECURSO);

        // Get all the disciplinaCurricularList where mediaParaRecurso is greater than SMALLER_MEDIA_PARA_RECURSO
        defaultDisciplinaCurricularShouldBeFound("mediaParaRecurso.greaterThan=" + SMALLER_MEDIA_PARA_RECURSO);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameEspecialIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial equals to DEFAULT_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldBeFound("mediaParaExameEspecial.equals=" + DEFAULT_MEDIA_PARA_EXAME_ESPECIAL);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial equals to UPDATED_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExameEspecial.equals=" + UPDATED_MEDIA_PARA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameEspecialIsInShouldWork() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial in DEFAULT_MEDIA_PARA_EXAME_ESPECIAL or UPDATED_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldBeFound(
            "mediaParaExameEspecial.in=" + DEFAULT_MEDIA_PARA_EXAME_ESPECIAL + "," + UPDATED_MEDIA_PARA_EXAME_ESPECIAL
        );

        // Get all the disciplinaCurricularList where mediaParaExameEspecial equals to UPDATED_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExameEspecial.in=" + UPDATED_MEDIA_PARA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameEspecialIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial is not null
        defaultDisciplinaCurricularShouldBeFound("mediaParaExameEspecial.specified=true");

        // Get all the disciplinaCurricularList where mediaParaExameEspecial is null
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExameEspecial.specified=false");
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameEspecialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial is greater than or equal to DEFAULT_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldBeFound("mediaParaExameEspecial.greaterThanOrEqual=" + DEFAULT_MEDIA_PARA_EXAME_ESPECIAL);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial is greater than or equal to UPDATED_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExameEspecial.greaterThanOrEqual=" + UPDATED_MEDIA_PARA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameEspecialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial is less than or equal to DEFAULT_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldBeFound("mediaParaExameEspecial.lessThanOrEqual=" + DEFAULT_MEDIA_PARA_EXAME_ESPECIAL);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial is less than or equal to SMALLER_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExameEspecial.lessThanOrEqual=" + SMALLER_MEDIA_PARA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameEspecialIsLessThanSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial is less than DEFAULT_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExameEspecial.lessThan=" + DEFAULT_MEDIA_PARA_EXAME_ESPECIAL);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial is less than UPDATED_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldBeFound("mediaParaExameEspecial.lessThan=" + UPDATED_MEDIA_PARA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaExameEspecialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial is greater than DEFAULT_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaExameEspecial.greaterThan=" + DEFAULT_MEDIA_PARA_EXAME_ESPECIAL);

        // Get all the disciplinaCurricularList where mediaParaExameEspecial is greater than SMALLER_MEDIA_PARA_EXAME_ESPECIAL
        defaultDisciplinaCurricularShouldBeFound("mediaParaExameEspecial.greaterThan=" + SMALLER_MEDIA_PARA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaDespensarIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaDespensar equals to DEFAULT_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldBeFound("mediaParaDespensar.equals=" + DEFAULT_MEDIA_PARA_DESPENSAR);

        // Get all the disciplinaCurricularList where mediaParaDespensar equals to UPDATED_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaDespensar.equals=" + UPDATED_MEDIA_PARA_DESPENSAR);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaDespensarIsInShouldWork() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaDespensar in DEFAULT_MEDIA_PARA_DESPENSAR or UPDATED_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldBeFound(
            "mediaParaDespensar.in=" + DEFAULT_MEDIA_PARA_DESPENSAR + "," + UPDATED_MEDIA_PARA_DESPENSAR
        );

        // Get all the disciplinaCurricularList where mediaParaDespensar equals to UPDATED_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaDespensar.in=" + UPDATED_MEDIA_PARA_DESPENSAR);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaDespensarIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaDespensar is not null
        defaultDisciplinaCurricularShouldBeFound("mediaParaDespensar.specified=true");

        // Get all the disciplinaCurricularList where mediaParaDespensar is null
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaDespensar.specified=false");
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaDespensarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaDespensar is greater than or equal to DEFAULT_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldBeFound("mediaParaDespensar.greaterThanOrEqual=" + DEFAULT_MEDIA_PARA_DESPENSAR);

        // Get all the disciplinaCurricularList where mediaParaDespensar is greater than or equal to UPDATED_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaDespensar.greaterThanOrEqual=" + UPDATED_MEDIA_PARA_DESPENSAR);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaDespensarIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaDespensar is less than or equal to DEFAULT_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldBeFound("mediaParaDespensar.lessThanOrEqual=" + DEFAULT_MEDIA_PARA_DESPENSAR);

        // Get all the disciplinaCurricularList where mediaParaDespensar is less than or equal to SMALLER_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaDespensar.lessThanOrEqual=" + SMALLER_MEDIA_PARA_DESPENSAR);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaDespensarIsLessThanSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaDespensar is less than DEFAULT_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaDespensar.lessThan=" + DEFAULT_MEDIA_PARA_DESPENSAR);

        // Get all the disciplinaCurricularList where mediaParaDespensar is less than UPDATED_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldBeFound("mediaParaDespensar.lessThan=" + UPDATED_MEDIA_PARA_DESPENSAR);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByMediaParaDespensarIsGreaterThanSomething() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        // Get all the disciplinaCurricularList where mediaParaDespensar is greater than DEFAULT_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldNotBeFound("mediaParaDespensar.greaterThan=" + DEFAULT_MEDIA_PARA_DESPENSAR);

        // Get all the disciplinaCurricularList where mediaParaDespensar is greater than SMALLER_MEDIA_PARA_DESPENSAR
        defaultDisciplinaCurricularShouldBeFound("mediaParaDespensar.greaterThan=" + SMALLER_MEDIA_PARA_DESPENSAR);
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByDisciplinaCurricularIsEqualToSomething() throws Exception {
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
            disciplinaCurricular = DisciplinaCurricularResourceIT.createEntity(em);
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        em.persist(disciplinaCurricular);
        em.flush();
        disciplinaCurricular.addDisciplinaCurricular(disciplinaCurricular);
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
        Long disciplinaCurricularId = disciplinaCurricular.getId();

        // Get all the disciplinaCurricularList where disciplinaCurricular equals to disciplinaCurricularId
        defaultDisciplinaCurricularShouldBeFound("disciplinaCurricularId.equals=" + disciplinaCurricularId);

        // Get all the disciplinaCurricularList where disciplinaCurricular equals to (disciplinaCurricularId + 1)
        defaultDisciplinaCurricularShouldNotBeFound("disciplinaCurricularId.equals=" + (disciplinaCurricularId + 1));
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByHorarioIsEqualToSomething() throws Exception {
        Horario horario;
        if (TestUtil.findAll(em, Horario.class).isEmpty()) {
            disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
            horario = HorarioResourceIT.createEntity(em);
        } else {
            horario = TestUtil.findAll(em, Horario.class).get(0);
        }
        em.persist(horario);
        em.flush();
        disciplinaCurricular.addHorario(horario);
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
        Long horarioId = horario.getId();

        // Get all the disciplinaCurricularList where horario equals to horarioId
        defaultDisciplinaCurricularShouldBeFound("horarioId.equals=" + horarioId);

        // Get all the disciplinaCurricularList where horario equals to (horarioId + 1)
        defaultDisciplinaCurricularShouldNotBeFound("horarioId.equals=" + (horarioId + 1));
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByPlanoAulaIsEqualToSomething() throws Exception {
        PlanoAula planoAula;
        if (TestUtil.findAll(em, PlanoAula.class).isEmpty()) {
            disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
            planoAula = PlanoAulaResourceIT.createEntity(em);
        } else {
            planoAula = TestUtil.findAll(em, PlanoAula.class).get(0);
        }
        em.persist(planoAula);
        em.flush();
        disciplinaCurricular.addPlanoAula(planoAula);
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
        Long planoAulaId = planoAula.getId();

        // Get all the disciplinaCurricularList where planoAula equals to planoAulaId
        defaultDisciplinaCurricularShouldBeFound("planoAulaId.equals=" + planoAulaId);

        // Get all the disciplinaCurricularList where planoAula equals to (planoAulaId + 1)
        defaultDisciplinaCurricularShouldNotBeFound("planoAulaId.equals=" + (planoAulaId + 1));
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByNotasGeralDisciplinaIsEqualToSomething() throws Exception {
        NotasGeralDisciplina notasGeralDisciplina;
        if (TestUtil.findAll(em, NotasGeralDisciplina.class).isEmpty()) {
            disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
            notasGeralDisciplina = NotasGeralDisciplinaResourceIT.createEntity(em);
        } else {
            notasGeralDisciplina = TestUtil.findAll(em, NotasGeralDisciplina.class).get(0);
        }
        em.persist(notasGeralDisciplina);
        em.flush();
        disciplinaCurricular.addNotasGeralDisciplina(notasGeralDisciplina);
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
        Long notasGeralDisciplinaId = notasGeralDisciplina.getId();

        // Get all the disciplinaCurricularList where notasGeralDisciplina equals to notasGeralDisciplinaId
        defaultDisciplinaCurricularShouldBeFound("notasGeralDisciplinaId.equals=" + notasGeralDisciplinaId);

        // Get all the disciplinaCurricularList where notasGeralDisciplina equals to (notasGeralDisciplinaId + 1)
        defaultDisciplinaCurricularShouldNotBeFound("notasGeralDisciplinaId.equals=" + (notasGeralDisciplinaId + 1));
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByNotasPeriodicaDisciplinaIsEqualToSomething() throws Exception {
        NotasPeriodicaDisciplina notasPeriodicaDisciplina;
        if (TestUtil.findAll(em, NotasPeriodicaDisciplina.class).isEmpty()) {
            disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
            notasPeriodicaDisciplina = NotasPeriodicaDisciplinaResourceIT.createEntity(em);
        } else {
            notasPeriodicaDisciplina = TestUtil.findAll(em, NotasPeriodicaDisciplina.class).get(0);
        }
        em.persist(notasPeriodicaDisciplina);
        em.flush();
        disciplinaCurricular.addNotasPeriodicaDisciplina(notasPeriodicaDisciplina);
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
        Long notasPeriodicaDisciplinaId = notasPeriodicaDisciplina.getId();

        // Get all the disciplinaCurricularList where notasPeriodicaDisciplina equals to notasPeriodicaDisciplinaId
        defaultDisciplinaCurricularShouldBeFound("notasPeriodicaDisciplinaId.equals=" + notasPeriodicaDisciplinaId);

        // Get all the disciplinaCurricularList where notasPeriodicaDisciplina equals to (notasPeriodicaDisciplinaId + 1)
        defaultDisciplinaCurricularShouldNotBeFound("notasPeriodicaDisciplinaId.equals=" + (notasPeriodicaDisciplinaId + 1));
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByComponenteIsEqualToSomething() throws Exception {
        LookupItem componente;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
            componente = LookupItemResourceIT.createEntity(em);
        } else {
            componente = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(componente);
        em.flush();
        disciplinaCurricular.setComponente(componente);
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
        Long componenteId = componente.getId();

        // Get all the disciplinaCurricularList where componente equals to componenteId
        defaultDisciplinaCurricularShouldBeFound("componenteId.equals=" + componenteId);

        // Get all the disciplinaCurricularList where componente equals to (componenteId + 1)
        defaultDisciplinaCurricularShouldNotBeFound("componenteId.equals=" + (componenteId + 1));
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByRegimeIsEqualToSomething() throws Exception {
        LookupItem regime;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
            regime = LookupItemResourceIT.createEntity(em);
        } else {
            regime = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(regime);
        em.flush();
        disciplinaCurricular.setRegime(regime);
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
        Long regimeId = regime.getId();

        // Get all the disciplinaCurricularList where regime equals to regimeId
        defaultDisciplinaCurricularShouldBeFound("regimeId.equals=" + regimeId);

        // Get all the disciplinaCurricularList where regime equals to (regimeId + 1)
        defaultDisciplinaCurricularShouldNotBeFound("regimeId.equals=" + (regimeId + 1));
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByPlanosCurricularIsEqualToSomething() throws Exception {
        PlanoCurricular planosCurricular;
        if (TestUtil.findAll(em, PlanoCurricular.class).isEmpty()) {
            disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
            planosCurricular = PlanoCurricularResourceIT.createEntity(em);
        } else {
            planosCurricular = TestUtil.findAll(em, PlanoCurricular.class).get(0);
        }
        em.persist(planosCurricular);
        em.flush();
        disciplinaCurricular.addPlanosCurricular(planosCurricular);
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
        Long planosCurricularId = planosCurricular.getId();

        // Get all the disciplinaCurricularList where planosCurricular equals to planosCurricularId
        defaultDisciplinaCurricularShouldBeFound("planosCurricularId.equals=" + planosCurricularId);

        // Get all the disciplinaCurricularList where planosCurricular equals to (planosCurricularId + 1)
        defaultDisciplinaCurricularShouldNotBeFound("planosCurricularId.equals=" + (planosCurricularId + 1));
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByDisciplinaIsEqualToSomething() throws Exception {
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
            disciplina = DisciplinaResourceIT.createEntity(em);
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        em.persist(disciplina);
        em.flush();
        disciplinaCurricular.setDisciplina(disciplina);
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
        Long disciplinaId = disciplina.getId();

        // Get all the disciplinaCurricularList where disciplina equals to disciplinaId
        defaultDisciplinaCurricularShouldBeFound("disciplinaId.equals=" + disciplinaId);

        // Get all the disciplinaCurricularList where disciplina equals to (disciplinaId + 1)
        defaultDisciplinaCurricularShouldNotBeFound("disciplinaId.equals=" + (disciplinaId + 1));
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByReferenciaIsEqualToSomething() throws Exception {
        DisciplinaCurricular referencia;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
            referencia = DisciplinaCurricularResourceIT.createEntity(em);
        } else {
            referencia = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        em.persist(referencia);
        em.flush();
        disciplinaCurricular.setReferencia(referencia);
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
        Long referenciaId = referencia.getId();

        // Get all the disciplinaCurricularList where referencia equals to referenciaId
        defaultDisciplinaCurricularShouldBeFound("referenciaId.equals=" + referenciaId);

        // Get all the disciplinaCurricularList where referencia equals to (referenciaId + 1)
        defaultDisciplinaCurricularShouldNotBeFound("referenciaId.equals=" + (referenciaId + 1));
    }

    @Test
    @Transactional
    void getAllDisciplinaCurricularsByEstadosIsEqualToSomething() throws Exception {
        EstadoDisciplinaCurricular estados;
        if (TestUtil.findAll(em, EstadoDisciplinaCurricular.class).isEmpty()) {
            disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
            estados = EstadoDisciplinaCurricularResourceIT.createEntity(em);
        } else {
            estados = TestUtil.findAll(em, EstadoDisciplinaCurricular.class).get(0);
        }
        em.persist(estados);
        em.flush();
        disciplinaCurricular.addEstados(estados);
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);
        Long estadosId = estados.getId();

        // Get all the disciplinaCurricularList where estados equals to estadosId
        defaultDisciplinaCurricularShouldBeFound("estadosId.equals=" + estadosId);

        // Get all the disciplinaCurricularList where estados equals to (estadosId + 1)
        defaultDisciplinaCurricularShouldNotBeFound("estadosId.equals=" + (estadosId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDisciplinaCurricularShouldBeFound(String filter) throws Exception {
        restDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplinaCurricular.getId().intValue())))
            .andExpect(jsonPath("$.[*].uniqueDisciplinaCurricular").value(hasItem(DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].cargaSemanal").value(hasItem(DEFAULT_CARGA_SEMANAL.doubleValue())))
            .andExpect(jsonPath("$.[*].isTerminal").value(hasItem(DEFAULT_IS_TERMINAL.booleanValue())))
            .andExpect(jsonPath("$.[*].mediaParaExame").value(hasItem(DEFAULT_MEDIA_PARA_EXAME.doubleValue())))
            .andExpect(jsonPath("$.[*].mediaParaRecurso").value(hasItem(DEFAULT_MEDIA_PARA_RECURSO.doubleValue())))
            .andExpect(jsonPath("$.[*].mediaParaExameEspecial").value(hasItem(DEFAULT_MEDIA_PARA_EXAME_ESPECIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].mediaParaDespensar").value(hasItem(DEFAULT_MEDIA_PARA_DESPENSAR.doubleValue())));

        // Check, that the count call also returns 1
        restDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDisciplinaCurricularShouldNotBeFound(String filter) throws Exception {
        restDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDisciplinaCurricular() throws Exception {
        // Get the disciplinaCurricular
        restDisciplinaCurricularMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDisciplinaCurricular() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        int databaseSizeBeforeUpdate = disciplinaCurricularRepository.findAll().size();

        // Update the disciplinaCurricular
        DisciplinaCurricular updatedDisciplinaCurricular = disciplinaCurricularRepository.findById(disciplinaCurricular.getId()).get();
        // Disconnect from session so that the updates on updatedDisciplinaCurricular are not directly saved in db
        em.detach(updatedDisciplinaCurricular);
        updatedDisciplinaCurricular
            .uniqueDisciplinaCurricular(UPDATED_UNIQUE_DISCIPLINA_CURRICULAR)
            .descricao(UPDATED_DESCRICAO)
            .cargaSemanal(UPDATED_CARGA_SEMANAL)
            .isTerminal(UPDATED_IS_TERMINAL)
            .mediaParaExame(UPDATED_MEDIA_PARA_EXAME)
            .mediaParaRecurso(UPDATED_MEDIA_PARA_RECURSO)
            .mediaParaExameEspecial(UPDATED_MEDIA_PARA_EXAME_ESPECIAL)
            .mediaParaDespensar(UPDATED_MEDIA_PARA_DESPENSAR);
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(updatedDisciplinaCurricular);

        restDisciplinaCurricularMockMvc
            .perform(
                put(ENTITY_API_URL_ID, disciplinaCurricularDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isOk());

        // Validate the DisciplinaCurricular in the database
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
        DisciplinaCurricular testDisciplinaCurricular = disciplinaCurricularList.get(disciplinaCurricularList.size() - 1);
        assertThat(testDisciplinaCurricular.getUniqueDisciplinaCurricular()).isEqualTo(UPDATED_UNIQUE_DISCIPLINA_CURRICULAR);
        assertThat(testDisciplinaCurricular.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDisciplinaCurricular.getCargaSemanal()).isEqualTo(UPDATED_CARGA_SEMANAL);
        assertThat(testDisciplinaCurricular.getIsTerminal()).isEqualTo(UPDATED_IS_TERMINAL);
        assertThat(testDisciplinaCurricular.getMediaParaExame()).isEqualTo(UPDATED_MEDIA_PARA_EXAME);
        assertThat(testDisciplinaCurricular.getMediaParaRecurso()).isEqualTo(UPDATED_MEDIA_PARA_RECURSO);
        assertThat(testDisciplinaCurricular.getMediaParaExameEspecial()).isEqualTo(UPDATED_MEDIA_PARA_EXAME_ESPECIAL);
        assertThat(testDisciplinaCurricular.getMediaParaDespensar()).isEqualTo(UPDATED_MEDIA_PARA_DESPENSAR);
    }

    @Test
    @Transactional
    void putNonExistingDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = disciplinaCurricularRepository.findAll().size();
        disciplinaCurricular.setId(count.incrementAndGet());

        // Create the DisciplinaCurricular
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplinaCurricularMockMvc
            .perform(
                put(ENTITY_API_URL_ID, disciplinaCurricularDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DisciplinaCurricular in the database
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = disciplinaCurricularRepository.findAll().size();
        disciplinaCurricular.setId(count.incrementAndGet());

        // Create the DisciplinaCurricular
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinaCurricularMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DisciplinaCurricular in the database
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = disciplinaCurricularRepository.findAll().size();
        disciplinaCurricular.setId(count.incrementAndGet());

        // Create the DisciplinaCurricular
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinaCurricularMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DisciplinaCurricular in the database
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDisciplinaCurricularWithPatch() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        int databaseSizeBeforeUpdate = disciplinaCurricularRepository.findAll().size();

        // Update the disciplinaCurricular using partial update
        DisciplinaCurricular partialUpdatedDisciplinaCurricular = new DisciplinaCurricular();
        partialUpdatedDisciplinaCurricular.setId(disciplinaCurricular.getId());

        partialUpdatedDisciplinaCurricular
            .descricao(UPDATED_DESCRICAO)
            .cargaSemanal(UPDATED_CARGA_SEMANAL)
            .mediaParaDespensar(UPDATED_MEDIA_PARA_DESPENSAR);

        restDisciplinaCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisciplinaCurricular.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDisciplinaCurricular))
            )
            .andExpect(status().isOk());

        // Validate the DisciplinaCurricular in the database
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
        DisciplinaCurricular testDisciplinaCurricular = disciplinaCurricularList.get(disciplinaCurricularList.size() - 1);
        assertThat(testDisciplinaCurricular.getUniqueDisciplinaCurricular()).isEqualTo(DEFAULT_UNIQUE_DISCIPLINA_CURRICULAR);
        assertThat(testDisciplinaCurricular.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDisciplinaCurricular.getCargaSemanal()).isEqualTo(UPDATED_CARGA_SEMANAL);
        assertThat(testDisciplinaCurricular.getIsTerminal()).isEqualTo(DEFAULT_IS_TERMINAL);
        assertThat(testDisciplinaCurricular.getMediaParaExame()).isEqualTo(DEFAULT_MEDIA_PARA_EXAME);
        assertThat(testDisciplinaCurricular.getMediaParaRecurso()).isEqualTo(DEFAULT_MEDIA_PARA_RECURSO);
        assertThat(testDisciplinaCurricular.getMediaParaExameEspecial()).isEqualTo(DEFAULT_MEDIA_PARA_EXAME_ESPECIAL);
        assertThat(testDisciplinaCurricular.getMediaParaDespensar()).isEqualTo(UPDATED_MEDIA_PARA_DESPENSAR);
    }

    @Test
    @Transactional
    void fullUpdateDisciplinaCurricularWithPatch() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        int databaseSizeBeforeUpdate = disciplinaCurricularRepository.findAll().size();

        // Update the disciplinaCurricular using partial update
        DisciplinaCurricular partialUpdatedDisciplinaCurricular = new DisciplinaCurricular();
        partialUpdatedDisciplinaCurricular.setId(disciplinaCurricular.getId());

        partialUpdatedDisciplinaCurricular
            .uniqueDisciplinaCurricular(UPDATED_UNIQUE_DISCIPLINA_CURRICULAR)
            .descricao(UPDATED_DESCRICAO)
            .cargaSemanal(UPDATED_CARGA_SEMANAL)
            .isTerminal(UPDATED_IS_TERMINAL)
            .mediaParaExame(UPDATED_MEDIA_PARA_EXAME)
            .mediaParaRecurso(UPDATED_MEDIA_PARA_RECURSO)
            .mediaParaExameEspecial(UPDATED_MEDIA_PARA_EXAME_ESPECIAL)
            .mediaParaDespensar(UPDATED_MEDIA_PARA_DESPENSAR);

        restDisciplinaCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisciplinaCurricular.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDisciplinaCurricular))
            )
            .andExpect(status().isOk());

        // Validate the DisciplinaCurricular in the database
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
        DisciplinaCurricular testDisciplinaCurricular = disciplinaCurricularList.get(disciplinaCurricularList.size() - 1);
        assertThat(testDisciplinaCurricular.getUniqueDisciplinaCurricular()).isEqualTo(UPDATED_UNIQUE_DISCIPLINA_CURRICULAR);
        assertThat(testDisciplinaCurricular.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDisciplinaCurricular.getCargaSemanal()).isEqualTo(UPDATED_CARGA_SEMANAL);
        assertThat(testDisciplinaCurricular.getIsTerminal()).isEqualTo(UPDATED_IS_TERMINAL);
        assertThat(testDisciplinaCurricular.getMediaParaExame()).isEqualTo(UPDATED_MEDIA_PARA_EXAME);
        assertThat(testDisciplinaCurricular.getMediaParaRecurso()).isEqualTo(UPDATED_MEDIA_PARA_RECURSO);
        assertThat(testDisciplinaCurricular.getMediaParaExameEspecial()).isEqualTo(UPDATED_MEDIA_PARA_EXAME_ESPECIAL);
        assertThat(testDisciplinaCurricular.getMediaParaDespensar()).isEqualTo(UPDATED_MEDIA_PARA_DESPENSAR);
    }

    @Test
    @Transactional
    void patchNonExistingDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = disciplinaCurricularRepository.findAll().size();
        disciplinaCurricular.setId(count.incrementAndGet());

        // Create the DisciplinaCurricular
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplinaCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, disciplinaCurricularDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DisciplinaCurricular in the database
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = disciplinaCurricularRepository.findAll().size();
        disciplinaCurricular.setId(count.incrementAndGet());

        // Create the DisciplinaCurricular
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinaCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DisciplinaCurricular in the database
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = disciplinaCurricularRepository.findAll().size();
        disciplinaCurricular.setId(count.incrementAndGet());

        // Create the DisciplinaCurricular
        DisciplinaCurricularDTO disciplinaCurricularDTO = disciplinaCurricularMapper.toDto(disciplinaCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinaCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(disciplinaCurricularDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DisciplinaCurricular in the database
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDisciplinaCurricular() throws Exception {
        // Initialize the database
        disciplinaCurricularRepository.saveAndFlush(disciplinaCurricular);

        int databaseSizeBeforeDelete = disciplinaCurricularRepository.findAll().size();

        // Delete the disciplinaCurricular
        restDisciplinaCurricularMockMvc
            .perform(delete(ENTITY_API_URL_ID, disciplinaCurricular.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DisciplinaCurricular> disciplinaCurricularList = disciplinaCurricularRepository.findAll();
        assertThat(disciplinaCurricularList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
