package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.domain.NotasGeralDisciplina;
import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.domain.ResumoAcademico;
import com.ravunana.longonkelo.domain.enumeration.CategoriaClassificacao;
import com.ravunana.longonkelo.repository.EstadoDisciplinaCurricularRepository;
import com.ravunana.longonkelo.service.EstadoDisciplinaCurricularService;
import com.ravunana.longonkelo.service.criteria.EstadoDisciplinaCurricularCriteria;
import com.ravunana.longonkelo.service.dto.EstadoDisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.mapper.EstadoDisciplinaCurricularMapper;
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
 * Integration tests for the {@link EstadoDisciplinaCurricularResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EstadoDisciplinaCurricularResourceIT {

    private static final String DEFAULT_UNIQUE_SITUACAO_DISCIPLINA = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_SITUACAO_DISCIPLINA = "BBBBBBBBBB";

    private static final CategoriaClassificacao DEFAULT_CLASSIFICACAO = CategoriaClassificacao.APROVADO;
    private static final CategoriaClassificacao UPDATED_CLASSIFICACAO = CategoriaClassificacao.REPROVADO;

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_COR = "AAAAAAAAAA";
    private static final String UPDATED_COR = "BBBBBBBBBB";

    private static final Double DEFAULT_VALOR = 0D;
    private static final Double UPDATED_VALOR = 1D;
    private static final Double SMALLER_VALOR = 0D - 1D;

    private static final String ENTITY_API_URL = "/api/estado-disciplina-curriculars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstadoDisciplinaCurricularRepository estadoDisciplinaCurricularRepository;

    @Mock
    private EstadoDisciplinaCurricularRepository estadoDisciplinaCurricularRepositoryMock;

    @Autowired
    private EstadoDisciplinaCurricularMapper estadoDisciplinaCurricularMapper;

    @Mock
    private EstadoDisciplinaCurricularService estadoDisciplinaCurricularServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoDisciplinaCurricularMockMvc;

    private EstadoDisciplinaCurricular estadoDisciplinaCurricular;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoDisciplinaCurricular createEntity(EntityManager em) {
        EstadoDisciplinaCurricular estadoDisciplinaCurricular = new EstadoDisciplinaCurricular()
            .uniqueSituacaoDisciplina(DEFAULT_UNIQUE_SITUACAO_DISCIPLINA)
            .classificacao(DEFAULT_CLASSIFICACAO)
            .codigo(DEFAULT_CODIGO)
            .descricao(DEFAULT_DESCRICAO)
            .cor(DEFAULT_COR)
            .valor(DEFAULT_VALOR);
        return estadoDisciplinaCurricular;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoDisciplinaCurricular createUpdatedEntity(EntityManager em) {
        EstadoDisciplinaCurricular estadoDisciplinaCurricular = new EstadoDisciplinaCurricular()
            .uniqueSituacaoDisciplina(UPDATED_UNIQUE_SITUACAO_DISCIPLINA)
            .classificacao(UPDATED_CLASSIFICACAO)
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .cor(UPDATED_COR)
            .valor(UPDATED_VALOR);
        return estadoDisciplinaCurricular;
    }

    @BeforeEach
    public void initTest() {
        estadoDisciplinaCurricular = createEntity(em);
    }

    @Test
    @Transactional
    void createEstadoDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeCreate = estadoDisciplinaCurricularRepository.findAll().size();
        // Create the EstadoDisciplinaCurricular
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);
        restEstadoDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EstadoDisciplinaCurricular in the database
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoDisciplinaCurricular testEstadoDisciplinaCurricular = estadoDisciplinaCurricularList.get(
            estadoDisciplinaCurricularList.size() - 1
        );
        assertThat(testEstadoDisciplinaCurricular.getUniqueSituacaoDisciplina()).isEqualTo(DEFAULT_UNIQUE_SITUACAO_DISCIPLINA);
        assertThat(testEstadoDisciplinaCurricular.getClassificacao()).isEqualTo(DEFAULT_CLASSIFICACAO);
        assertThat(testEstadoDisciplinaCurricular.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testEstadoDisciplinaCurricular.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testEstadoDisciplinaCurricular.getCor()).isEqualTo(DEFAULT_COR);
        assertThat(testEstadoDisciplinaCurricular.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void createEstadoDisciplinaCurricularWithExistingId() throws Exception {
        // Create the EstadoDisciplinaCurricular with an existing ID
        estadoDisciplinaCurricular.setId(1L);
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        int databaseSizeBeforeCreate = estadoDisciplinaCurricularRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoDisciplinaCurricular in the database
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkClassificacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoDisciplinaCurricularRepository.findAll().size();
        // set the field null
        estadoDisciplinaCurricular.setClassificacao(null);

        // Create the EstadoDisciplinaCurricular, which fails.
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        restEstadoDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoDisciplinaCurricularRepository.findAll().size();
        // set the field null
        estadoDisciplinaCurricular.setCodigo(null);

        // Create the EstadoDisciplinaCurricular, which fails.
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        restEstadoDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoDisciplinaCurricularRepository.findAll().size();
        // set the field null
        estadoDisciplinaCurricular.setDescricao(null);

        // Create the EstadoDisciplinaCurricular, which fails.
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        restEstadoDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCorIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoDisciplinaCurricularRepository.findAll().size();
        // set the field null
        estadoDisciplinaCurricular.setCor(null);

        // Create the EstadoDisciplinaCurricular, which fails.
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        restEstadoDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoDisciplinaCurricularRepository.findAll().size();
        // set the field null
        estadoDisciplinaCurricular.setValor(null);

        // Create the EstadoDisciplinaCurricular, which fails.
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        restEstadoDisciplinaCurricularMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurriculars() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList
        restEstadoDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoDisciplinaCurricular.getId().intValue())))
            .andExpect(jsonPath("$.[*].uniqueSituacaoDisciplina").value(hasItem(DEFAULT_UNIQUE_SITUACAO_DISCIPLINA)))
            .andExpect(jsonPath("$.[*].classificacao").value(hasItem(DEFAULT_CLASSIFICACAO.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEstadoDisciplinaCurricularsWithEagerRelationshipsIsEnabled() throws Exception {
        when(estadoDisciplinaCurricularServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEstadoDisciplinaCurricularMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(estadoDisciplinaCurricularServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEstadoDisciplinaCurricularsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(estadoDisciplinaCurricularServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEstadoDisciplinaCurricularMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(estadoDisciplinaCurricularRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEstadoDisciplinaCurricular() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get the estadoDisciplinaCurricular
        restEstadoDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL_ID, estadoDisciplinaCurricular.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoDisciplinaCurricular.getId().intValue()))
            .andExpect(jsonPath("$.uniqueSituacaoDisciplina").value(DEFAULT_UNIQUE_SITUACAO_DISCIPLINA))
            .andExpect(jsonPath("$.classificacao").value(DEFAULT_CLASSIFICACAO.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    void getEstadoDisciplinaCurricularsByIdFiltering() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        Long id = estadoDisciplinaCurricular.getId();

        defaultEstadoDisciplinaCurricularShouldBeFound("id.equals=" + id);
        defaultEstadoDisciplinaCurricularShouldNotBeFound("id.notEquals=" + id);

        defaultEstadoDisciplinaCurricularShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEstadoDisciplinaCurricularShouldNotBeFound("id.greaterThan=" + id);

        defaultEstadoDisciplinaCurricularShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEstadoDisciplinaCurricularShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByUniqueSituacaoDisciplinaIsEqualToSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where uniqueSituacaoDisciplina equals to DEFAULT_UNIQUE_SITUACAO_DISCIPLINA
        defaultEstadoDisciplinaCurricularShouldBeFound("uniqueSituacaoDisciplina.equals=" + DEFAULT_UNIQUE_SITUACAO_DISCIPLINA);

        // Get all the estadoDisciplinaCurricularList where uniqueSituacaoDisciplina equals to UPDATED_UNIQUE_SITUACAO_DISCIPLINA
        defaultEstadoDisciplinaCurricularShouldNotBeFound("uniqueSituacaoDisciplina.equals=" + UPDATED_UNIQUE_SITUACAO_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByUniqueSituacaoDisciplinaIsInShouldWork() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where uniqueSituacaoDisciplina in DEFAULT_UNIQUE_SITUACAO_DISCIPLINA or UPDATED_UNIQUE_SITUACAO_DISCIPLINA
        defaultEstadoDisciplinaCurricularShouldBeFound(
            "uniqueSituacaoDisciplina.in=" + DEFAULT_UNIQUE_SITUACAO_DISCIPLINA + "," + UPDATED_UNIQUE_SITUACAO_DISCIPLINA
        );

        // Get all the estadoDisciplinaCurricularList where uniqueSituacaoDisciplina equals to UPDATED_UNIQUE_SITUACAO_DISCIPLINA
        defaultEstadoDisciplinaCurricularShouldNotBeFound("uniqueSituacaoDisciplina.in=" + UPDATED_UNIQUE_SITUACAO_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByUniqueSituacaoDisciplinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where uniqueSituacaoDisciplina is not null
        defaultEstadoDisciplinaCurricularShouldBeFound("uniqueSituacaoDisciplina.specified=true");

        // Get all the estadoDisciplinaCurricularList where uniqueSituacaoDisciplina is null
        defaultEstadoDisciplinaCurricularShouldNotBeFound("uniqueSituacaoDisciplina.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByUniqueSituacaoDisciplinaContainsSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where uniqueSituacaoDisciplina contains DEFAULT_UNIQUE_SITUACAO_DISCIPLINA
        defaultEstadoDisciplinaCurricularShouldBeFound("uniqueSituacaoDisciplina.contains=" + DEFAULT_UNIQUE_SITUACAO_DISCIPLINA);

        // Get all the estadoDisciplinaCurricularList where uniqueSituacaoDisciplina contains UPDATED_UNIQUE_SITUACAO_DISCIPLINA
        defaultEstadoDisciplinaCurricularShouldNotBeFound("uniqueSituacaoDisciplina.contains=" + UPDATED_UNIQUE_SITUACAO_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByUniqueSituacaoDisciplinaNotContainsSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where uniqueSituacaoDisciplina does not contain DEFAULT_UNIQUE_SITUACAO_DISCIPLINA
        defaultEstadoDisciplinaCurricularShouldNotBeFound("uniqueSituacaoDisciplina.doesNotContain=" + DEFAULT_UNIQUE_SITUACAO_DISCIPLINA);

        // Get all the estadoDisciplinaCurricularList where uniqueSituacaoDisciplina does not contain UPDATED_UNIQUE_SITUACAO_DISCIPLINA
        defaultEstadoDisciplinaCurricularShouldBeFound("uniqueSituacaoDisciplina.doesNotContain=" + UPDATED_UNIQUE_SITUACAO_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByClassificacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where classificacao equals to DEFAULT_CLASSIFICACAO
        defaultEstadoDisciplinaCurricularShouldBeFound("classificacao.equals=" + DEFAULT_CLASSIFICACAO);

        // Get all the estadoDisciplinaCurricularList where classificacao equals to UPDATED_CLASSIFICACAO
        defaultEstadoDisciplinaCurricularShouldNotBeFound("classificacao.equals=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByClassificacaoIsInShouldWork() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where classificacao in DEFAULT_CLASSIFICACAO or UPDATED_CLASSIFICACAO
        defaultEstadoDisciplinaCurricularShouldBeFound("classificacao.in=" + DEFAULT_CLASSIFICACAO + "," + UPDATED_CLASSIFICACAO);

        // Get all the estadoDisciplinaCurricularList where classificacao equals to UPDATED_CLASSIFICACAO
        defaultEstadoDisciplinaCurricularShouldNotBeFound("classificacao.in=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByClassificacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where classificacao is not null
        defaultEstadoDisciplinaCurricularShouldBeFound("classificacao.specified=true");

        // Get all the estadoDisciplinaCurricularList where classificacao is null
        defaultEstadoDisciplinaCurricularShouldNotBeFound("classificacao.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where codigo equals to DEFAULT_CODIGO
        defaultEstadoDisciplinaCurricularShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the estadoDisciplinaCurricularList where codigo equals to UPDATED_CODIGO
        defaultEstadoDisciplinaCurricularShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultEstadoDisciplinaCurricularShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the estadoDisciplinaCurricularList where codigo equals to UPDATED_CODIGO
        defaultEstadoDisciplinaCurricularShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where codigo is not null
        defaultEstadoDisciplinaCurricularShouldBeFound("codigo.specified=true");

        // Get all the estadoDisciplinaCurricularList where codigo is null
        defaultEstadoDisciplinaCurricularShouldNotBeFound("codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByCodigoContainsSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where codigo contains DEFAULT_CODIGO
        defaultEstadoDisciplinaCurricularShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the estadoDisciplinaCurricularList where codigo contains UPDATED_CODIGO
        defaultEstadoDisciplinaCurricularShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where codigo does not contain DEFAULT_CODIGO
        defaultEstadoDisciplinaCurricularShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the estadoDisciplinaCurricularList where codigo does not contain UPDATED_CODIGO
        defaultEstadoDisciplinaCurricularShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where descricao equals to DEFAULT_DESCRICAO
        defaultEstadoDisciplinaCurricularShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the estadoDisciplinaCurricularList where descricao equals to UPDATED_DESCRICAO
        defaultEstadoDisciplinaCurricularShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultEstadoDisciplinaCurricularShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the estadoDisciplinaCurricularList where descricao equals to UPDATED_DESCRICAO
        defaultEstadoDisciplinaCurricularShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where descricao is not null
        defaultEstadoDisciplinaCurricularShouldBeFound("descricao.specified=true");

        // Get all the estadoDisciplinaCurricularList where descricao is null
        defaultEstadoDisciplinaCurricularShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where descricao contains DEFAULT_DESCRICAO
        defaultEstadoDisciplinaCurricularShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the estadoDisciplinaCurricularList where descricao contains UPDATED_DESCRICAO
        defaultEstadoDisciplinaCurricularShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where descricao does not contain DEFAULT_DESCRICAO
        defaultEstadoDisciplinaCurricularShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the estadoDisciplinaCurricularList where descricao does not contain UPDATED_DESCRICAO
        defaultEstadoDisciplinaCurricularShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByCorIsEqualToSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where cor equals to DEFAULT_COR
        defaultEstadoDisciplinaCurricularShouldBeFound("cor.equals=" + DEFAULT_COR);

        // Get all the estadoDisciplinaCurricularList where cor equals to UPDATED_COR
        defaultEstadoDisciplinaCurricularShouldNotBeFound("cor.equals=" + UPDATED_COR);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByCorIsInShouldWork() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where cor in DEFAULT_COR or UPDATED_COR
        defaultEstadoDisciplinaCurricularShouldBeFound("cor.in=" + DEFAULT_COR + "," + UPDATED_COR);

        // Get all the estadoDisciplinaCurricularList where cor equals to UPDATED_COR
        defaultEstadoDisciplinaCurricularShouldNotBeFound("cor.in=" + UPDATED_COR);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByCorIsNullOrNotNull() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where cor is not null
        defaultEstadoDisciplinaCurricularShouldBeFound("cor.specified=true");

        // Get all the estadoDisciplinaCurricularList where cor is null
        defaultEstadoDisciplinaCurricularShouldNotBeFound("cor.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByCorContainsSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where cor contains DEFAULT_COR
        defaultEstadoDisciplinaCurricularShouldBeFound("cor.contains=" + DEFAULT_COR);

        // Get all the estadoDisciplinaCurricularList where cor contains UPDATED_COR
        defaultEstadoDisciplinaCurricularShouldNotBeFound("cor.contains=" + UPDATED_COR);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByCorNotContainsSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where cor does not contain DEFAULT_COR
        defaultEstadoDisciplinaCurricularShouldNotBeFound("cor.doesNotContain=" + DEFAULT_COR);

        // Get all the estadoDisciplinaCurricularList where cor does not contain UPDATED_COR
        defaultEstadoDisciplinaCurricularShouldBeFound("cor.doesNotContain=" + UPDATED_COR);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where valor equals to DEFAULT_VALOR
        defaultEstadoDisciplinaCurricularShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the estadoDisciplinaCurricularList where valor equals to UPDATED_VALOR
        defaultEstadoDisciplinaCurricularShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByValorIsInShouldWork() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultEstadoDisciplinaCurricularShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the estadoDisciplinaCurricularList where valor equals to UPDATED_VALOR
        defaultEstadoDisciplinaCurricularShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where valor is not null
        defaultEstadoDisciplinaCurricularShouldBeFound("valor.specified=true");

        // Get all the estadoDisciplinaCurricularList where valor is null
        defaultEstadoDisciplinaCurricularShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where valor is greater than or equal to DEFAULT_VALOR
        defaultEstadoDisciplinaCurricularShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the estadoDisciplinaCurricularList where valor is greater than or equal to UPDATED_VALOR
        defaultEstadoDisciplinaCurricularShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where valor is less than or equal to DEFAULT_VALOR
        defaultEstadoDisciplinaCurricularShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the estadoDisciplinaCurricularList where valor is less than or equal to SMALLER_VALOR
        defaultEstadoDisciplinaCurricularShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where valor is less than DEFAULT_VALOR
        defaultEstadoDisciplinaCurricularShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the estadoDisciplinaCurricularList where valor is less than UPDATED_VALOR
        defaultEstadoDisciplinaCurricularShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        // Get all the estadoDisciplinaCurricularList where valor is greater than DEFAULT_VALOR
        defaultEstadoDisciplinaCurricularShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the estadoDisciplinaCurricularList where valor is greater than SMALLER_VALOR
        defaultEstadoDisciplinaCurricularShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }

    // @Test
    // @Transactional
    // void getAllEstadoDisciplinaCurricularsByEstadoDisciplinaCurricularIsEqualToSomething() throws Exception {
    //     EstadoDisciplinaCurricular estadoDisciplinaCurricular;
    //     if (TestUtil.findAll(em, EstadoDisciplinaCurricular.class).isEmpty()) {
    //         estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
    //         estadoDisciplinaCurricular = EstadoDisciplinaCurricularResourceIT.createEntity(em);
    //     } else {
    //         estadoDisciplinaCurricular = TestUtil.findAll(em, EstadoDisciplinaCurricular.class).get(0);
    //     }
    //     em.persist(estadoDisciplinaCurricular);
    //     em.flush();
    //     estadoDisciplinaCurricular.addEstadoDisciplinaCurricular(estadoDisciplinaCurricular);
    //     estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
    //     Long estadoDisciplinaCurricularId = estadoDisciplinaCurricular.getId();

    //     // Get all the estadoDisciplinaCurricularList where estadoDisciplinaCurricular equals to estadoDisciplinaCurricularId
    //     defaultEstadoDisciplinaCurricularShouldBeFound("estadoDisciplinaCurricularId.equals=" + estadoDisciplinaCurricularId);

    //     // Get all the estadoDisciplinaCurricularList where estadoDisciplinaCurricular equals to (estadoDisciplinaCurricularId + 1)
    //     defaultEstadoDisciplinaCurricularShouldNotBeFound("estadoDisciplinaCurricularId.equals=" + (estadoDisciplinaCurricularId + 1));
    // }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByNotasPeriodicaDisciplinaIsEqualToSomething() throws Exception {
        NotasPeriodicaDisciplina notasPeriodicaDisciplina;
        if (TestUtil.findAll(em, NotasPeriodicaDisciplina.class).isEmpty()) {
            estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
            notasPeriodicaDisciplina = NotasPeriodicaDisciplinaResourceIT.createEntity(em);
        } else {
            notasPeriodicaDisciplina = TestUtil.findAll(em, NotasPeriodicaDisciplina.class).get(0);
        }
        em.persist(notasPeriodicaDisciplina);
        em.flush();
        estadoDisciplinaCurricular.addNotasPeriodicaDisciplina(notasPeriodicaDisciplina);
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
        Long notasPeriodicaDisciplinaId = notasPeriodicaDisciplina.getId();

        // Get all the estadoDisciplinaCurricularList where notasPeriodicaDisciplina equals to notasPeriodicaDisciplinaId
        defaultEstadoDisciplinaCurricularShouldBeFound("notasPeriodicaDisciplinaId.equals=" + notasPeriodicaDisciplinaId);

        // Get all the estadoDisciplinaCurricularList where notasPeriodicaDisciplina equals to (notasPeriodicaDisciplinaId + 1)
        defaultEstadoDisciplinaCurricularShouldNotBeFound("notasPeriodicaDisciplinaId.equals=" + (notasPeriodicaDisciplinaId + 1));
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByNotasGeralDisciplinaIsEqualToSomething() throws Exception {
        NotasGeralDisciplina notasGeralDisciplina;
        if (TestUtil.findAll(em, NotasGeralDisciplina.class).isEmpty()) {
            estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
            notasGeralDisciplina = NotasGeralDisciplinaResourceIT.createEntity(em);
        } else {
            notasGeralDisciplina = TestUtil.findAll(em, NotasGeralDisciplina.class).get(0);
        }
        em.persist(notasGeralDisciplina);
        em.flush();
        estadoDisciplinaCurricular.addNotasGeralDisciplina(notasGeralDisciplina);
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
        Long notasGeralDisciplinaId = notasGeralDisciplina.getId();

        // Get all the estadoDisciplinaCurricularList where notasGeralDisciplina equals to notasGeralDisciplinaId
        defaultEstadoDisciplinaCurricularShouldBeFound("notasGeralDisciplinaId.equals=" + notasGeralDisciplinaId);

        // Get all the estadoDisciplinaCurricularList where notasGeralDisciplina equals to (notasGeralDisciplinaId + 1)
        defaultEstadoDisciplinaCurricularShouldNotBeFound("notasGeralDisciplinaId.equals=" + (notasGeralDisciplinaId + 1));
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByResumoAcademicoIsEqualToSomething() throws Exception {
        ResumoAcademico resumoAcademico;
        if (TestUtil.findAll(em, ResumoAcademico.class).isEmpty()) {
            estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
            resumoAcademico = ResumoAcademicoResourceIT.createEntity(em);
        } else {
            resumoAcademico = TestUtil.findAll(em, ResumoAcademico.class).get(0);
        }
        em.persist(resumoAcademico);
        em.flush();
        estadoDisciplinaCurricular.addResumoAcademico(resumoAcademico);
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
        Long resumoAcademicoId = resumoAcademico.getId();

        // Get all the estadoDisciplinaCurricularList where resumoAcademico equals to resumoAcademicoId
        defaultEstadoDisciplinaCurricularShouldBeFound("resumoAcademicoId.equals=" + resumoAcademicoId);

        // Get all the estadoDisciplinaCurricularList where resumoAcademico equals to (resumoAcademicoId + 1)
        defaultEstadoDisciplinaCurricularShouldNotBeFound("resumoAcademicoId.equals=" + (resumoAcademicoId + 1));
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByDisciplinasCurricularsIsEqualToSomething() throws Exception {
        DisciplinaCurricular disciplinasCurriculars;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
            disciplinasCurriculars = DisciplinaCurricularResourceIT.createEntity(em);
        } else {
            disciplinasCurriculars = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        em.persist(disciplinasCurriculars);
        em.flush();
        estadoDisciplinaCurricular.addDisciplinasCurriculars(disciplinasCurriculars);
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
        Long disciplinasCurricularsId = disciplinasCurriculars.getId();

        // Get all the estadoDisciplinaCurricularList where disciplinasCurriculars equals to disciplinasCurricularsId
        defaultEstadoDisciplinaCurricularShouldBeFound("disciplinasCurricularsId.equals=" + disciplinasCurricularsId);

        // Get all the estadoDisciplinaCurricularList where disciplinasCurriculars equals to (disciplinasCurricularsId + 1)
        defaultEstadoDisciplinaCurricularShouldNotBeFound("disciplinasCurricularsId.equals=" + (disciplinasCurricularsId + 1));
    }

    @Test
    @Transactional
    void getAllEstadoDisciplinaCurricularsByReferenciaIsEqualToSomething() throws Exception {
        EstadoDisciplinaCurricular referencia;
        if (TestUtil.findAll(em, EstadoDisciplinaCurricular.class).isEmpty()) {
            estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
            referencia = EstadoDisciplinaCurricularResourceIT.createEntity(em);
        } else {
            referencia = TestUtil.findAll(em, EstadoDisciplinaCurricular.class).get(0);
        }
        em.persist(referencia);
        em.flush();
        estadoDisciplinaCurricular.setReferencia(referencia);
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);
        Long referenciaId = referencia.getId();

        // Get all the estadoDisciplinaCurricularList where referencia equals to referenciaId
        defaultEstadoDisciplinaCurricularShouldBeFound("referenciaId.equals=" + referenciaId);

        // Get all the estadoDisciplinaCurricularList where referencia equals to (referenciaId + 1)
        defaultEstadoDisciplinaCurricularShouldNotBeFound("referenciaId.equals=" + (referenciaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstadoDisciplinaCurricularShouldBeFound(String filter) throws Exception {
        restEstadoDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoDisciplinaCurricular.getId().intValue())))
            .andExpect(jsonPath("$.[*].uniqueSituacaoDisciplina").value(hasItem(DEFAULT_UNIQUE_SITUACAO_DISCIPLINA)))
            .andExpect(jsonPath("$.[*].classificacao").value(hasItem(DEFAULT_CLASSIFICACAO.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));

        // Check, that the count call also returns 1
        restEstadoDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstadoDisciplinaCurricularShouldNotBeFound(String filter) throws Exception {
        restEstadoDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstadoDisciplinaCurricularMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEstadoDisciplinaCurricular() throws Exception {
        // Get the estadoDisciplinaCurricular
        restEstadoDisciplinaCurricularMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstadoDisciplinaCurricular() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        int databaseSizeBeforeUpdate = estadoDisciplinaCurricularRepository.findAll().size();

        // Update the estadoDisciplinaCurricular
        EstadoDisciplinaCurricular updatedEstadoDisciplinaCurricular = estadoDisciplinaCurricularRepository
            .findById(estadoDisciplinaCurricular.getId())
            .get();
        // Disconnect from session so that the updates on updatedEstadoDisciplinaCurricular are not directly saved in db
        em.detach(updatedEstadoDisciplinaCurricular);
        updatedEstadoDisciplinaCurricular
            .uniqueSituacaoDisciplina(UPDATED_UNIQUE_SITUACAO_DISCIPLINA)
            .classificacao(UPDATED_CLASSIFICACAO)
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .cor(UPDATED_COR)
            .valor(UPDATED_VALOR);
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(
            updatedEstadoDisciplinaCurricular
        );

        restEstadoDisciplinaCurricularMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoDisciplinaCurricularDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isOk());

        // Validate the EstadoDisciplinaCurricular in the database
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
        EstadoDisciplinaCurricular testEstadoDisciplinaCurricular = estadoDisciplinaCurricularList.get(
            estadoDisciplinaCurricularList.size() - 1
        );
        assertThat(testEstadoDisciplinaCurricular.getUniqueSituacaoDisciplina()).isEqualTo(UPDATED_UNIQUE_SITUACAO_DISCIPLINA);
        assertThat(testEstadoDisciplinaCurricular.getClassificacao()).isEqualTo(UPDATED_CLASSIFICACAO);
        assertThat(testEstadoDisciplinaCurricular.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testEstadoDisciplinaCurricular.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testEstadoDisciplinaCurricular.getCor()).isEqualTo(UPDATED_COR);
        assertThat(testEstadoDisciplinaCurricular.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void putNonExistingEstadoDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = estadoDisciplinaCurricularRepository.findAll().size();
        estadoDisciplinaCurricular.setId(count.incrementAndGet());

        // Create the EstadoDisciplinaCurricular
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoDisciplinaCurricularMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoDisciplinaCurricularDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoDisciplinaCurricular in the database
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstadoDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = estadoDisciplinaCurricularRepository.findAll().size();
        estadoDisciplinaCurricular.setId(count.incrementAndGet());

        // Create the EstadoDisciplinaCurricular
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoDisciplinaCurricularMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoDisciplinaCurricular in the database
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstadoDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = estadoDisciplinaCurricularRepository.findAll().size();
        estadoDisciplinaCurricular.setId(count.incrementAndGet());

        // Create the EstadoDisciplinaCurricular
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoDisciplinaCurricularMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoDisciplinaCurricular in the database
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadoDisciplinaCurricularWithPatch() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        int databaseSizeBeforeUpdate = estadoDisciplinaCurricularRepository.findAll().size();

        // Update the estadoDisciplinaCurricular using partial update
        EstadoDisciplinaCurricular partialUpdatedEstadoDisciplinaCurricular = new EstadoDisciplinaCurricular();
        partialUpdatedEstadoDisciplinaCurricular.setId(estadoDisciplinaCurricular.getId());

        partialUpdatedEstadoDisciplinaCurricular.cor(UPDATED_COR);

        restEstadoDisciplinaCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoDisciplinaCurricular.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoDisciplinaCurricular))
            )
            .andExpect(status().isOk());

        // Validate the EstadoDisciplinaCurricular in the database
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
        EstadoDisciplinaCurricular testEstadoDisciplinaCurricular = estadoDisciplinaCurricularList.get(
            estadoDisciplinaCurricularList.size() - 1
        );
        assertThat(testEstadoDisciplinaCurricular.getUniqueSituacaoDisciplina()).isEqualTo(DEFAULT_UNIQUE_SITUACAO_DISCIPLINA);
        assertThat(testEstadoDisciplinaCurricular.getClassificacao()).isEqualTo(DEFAULT_CLASSIFICACAO);
        assertThat(testEstadoDisciplinaCurricular.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testEstadoDisciplinaCurricular.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testEstadoDisciplinaCurricular.getCor()).isEqualTo(UPDATED_COR);
        assertThat(testEstadoDisciplinaCurricular.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateEstadoDisciplinaCurricularWithPatch() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        int databaseSizeBeforeUpdate = estadoDisciplinaCurricularRepository.findAll().size();

        // Update the estadoDisciplinaCurricular using partial update
        EstadoDisciplinaCurricular partialUpdatedEstadoDisciplinaCurricular = new EstadoDisciplinaCurricular();
        partialUpdatedEstadoDisciplinaCurricular.setId(estadoDisciplinaCurricular.getId());

        partialUpdatedEstadoDisciplinaCurricular
            .uniqueSituacaoDisciplina(UPDATED_UNIQUE_SITUACAO_DISCIPLINA)
            .classificacao(UPDATED_CLASSIFICACAO)
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .cor(UPDATED_COR)
            .valor(UPDATED_VALOR);

        restEstadoDisciplinaCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoDisciplinaCurricular.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoDisciplinaCurricular))
            )
            .andExpect(status().isOk());

        // Validate the EstadoDisciplinaCurricular in the database
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
        EstadoDisciplinaCurricular testEstadoDisciplinaCurricular = estadoDisciplinaCurricularList.get(
            estadoDisciplinaCurricularList.size() - 1
        );
        assertThat(testEstadoDisciplinaCurricular.getUniqueSituacaoDisciplina()).isEqualTo(UPDATED_UNIQUE_SITUACAO_DISCIPLINA);
        assertThat(testEstadoDisciplinaCurricular.getClassificacao()).isEqualTo(UPDATED_CLASSIFICACAO);
        assertThat(testEstadoDisciplinaCurricular.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testEstadoDisciplinaCurricular.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testEstadoDisciplinaCurricular.getCor()).isEqualTo(UPDATED_COR);
        assertThat(testEstadoDisciplinaCurricular.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void patchNonExistingEstadoDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = estadoDisciplinaCurricularRepository.findAll().size();
        estadoDisciplinaCurricular.setId(count.incrementAndGet());

        // Create the EstadoDisciplinaCurricular
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoDisciplinaCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estadoDisciplinaCurricularDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoDisciplinaCurricular in the database
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstadoDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = estadoDisciplinaCurricularRepository.findAll().size();
        estadoDisciplinaCurricular.setId(count.incrementAndGet());

        // Create the EstadoDisciplinaCurricular
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoDisciplinaCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoDisciplinaCurricular in the database
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstadoDisciplinaCurricular() throws Exception {
        int databaseSizeBeforeUpdate = estadoDisciplinaCurricularRepository.findAll().size();
        estadoDisciplinaCurricular.setId(count.incrementAndGet());

        // Create the EstadoDisciplinaCurricular
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoDisciplinaCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoDisciplinaCurricularDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoDisciplinaCurricular in the database
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstadoDisciplinaCurricular() throws Exception {
        // Initialize the database
        estadoDisciplinaCurricularRepository.saveAndFlush(estadoDisciplinaCurricular);

        int databaseSizeBeforeDelete = estadoDisciplinaCurricularRepository.findAll().size();

        // Delete the estadoDisciplinaCurricular
        restEstadoDisciplinaCurricularMockMvc
            .perform(delete(ENTITY_API_URL_ID, estadoDisciplinaCurricular.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoDisciplinaCurricular> estadoDisciplinaCurricularList = estadoDisciplinaCurricularRepository.findAll();
        assertThat(estadoDisciplinaCurricularList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
