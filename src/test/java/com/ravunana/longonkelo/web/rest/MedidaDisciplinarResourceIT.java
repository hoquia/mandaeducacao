package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.CategoriaOcorrencia;
import com.ravunana.longonkelo.domain.MedidaDisciplinar;
import com.ravunana.longonkelo.domain.enumeration.Suspensao;
import com.ravunana.longonkelo.domain.enumeration.UnidadeDuracao;
import com.ravunana.longonkelo.repository.MedidaDisciplinarRepository;
import com.ravunana.longonkelo.service.criteria.MedidaDisciplinarCriteria;
import com.ravunana.longonkelo.service.dto.MedidaDisciplinarDTO;
import com.ravunana.longonkelo.service.mapper.MedidaDisciplinarMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MedidaDisciplinarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedidaDisciplinarResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final UnidadeDuracao DEFAULT_PERIODO = UnidadeDuracao.HORA;
    private static final UnidadeDuracao UPDATED_PERIODO = UnidadeDuracao.DIA;

    private static final Suspensao DEFAULT_SUSPENSAO = Suspensao.NENHUMA;
    private static final Suspensao UPDATED_SUSPENSAO = Suspensao.AULA;

    private static final Integer DEFAULT_TEMPO = 0;
    private static final Integer UPDATED_TEMPO = 1;
    private static final Integer SMALLER_TEMPO = 0 - 1;

    private static final String ENTITY_API_URL = "/api/medida-disciplinars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MedidaDisciplinarRepository medidaDisciplinarRepository;

    @Autowired
    private MedidaDisciplinarMapper medidaDisciplinarMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedidaDisciplinarMockMvc;

    private MedidaDisciplinar medidaDisciplinar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedidaDisciplinar createEntity(EntityManager em) {
        MedidaDisciplinar medidaDisciplinar = new MedidaDisciplinar()
            .descricao(DEFAULT_DESCRICAO)
            .periodo(DEFAULT_PERIODO)
            .suspensao(DEFAULT_SUSPENSAO)
            .tempo(DEFAULT_TEMPO);
        return medidaDisciplinar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedidaDisciplinar createUpdatedEntity(EntityManager em) {
        MedidaDisciplinar medidaDisciplinar = new MedidaDisciplinar()
            .descricao(UPDATED_DESCRICAO)
            .periodo(UPDATED_PERIODO)
            .suspensao(UPDATED_SUSPENSAO)
            .tempo(UPDATED_TEMPO);
        return medidaDisciplinar;
    }

    @BeforeEach
    public void initTest() {
        medidaDisciplinar = createEntity(em);
    }

    @Test
    @Transactional
    void createMedidaDisciplinar() throws Exception {
        int databaseSizeBeforeCreate = medidaDisciplinarRepository.findAll().size();
        // Create the MedidaDisciplinar
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(medidaDisciplinar);
        restMedidaDisciplinarMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MedidaDisciplinar in the database
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeCreate + 1);
        MedidaDisciplinar testMedidaDisciplinar = medidaDisciplinarList.get(medidaDisciplinarList.size() - 1);
        assertThat(testMedidaDisciplinar.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMedidaDisciplinar.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testMedidaDisciplinar.getSuspensao()).isEqualTo(DEFAULT_SUSPENSAO);
        assertThat(testMedidaDisciplinar.getTempo()).isEqualTo(DEFAULT_TEMPO);
    }

    @Test
    @Transactional
    void createMedidaDisciplinarWithExistingId() throws Exception {
        // Create the MedidaDisciplinar with an existing ID
        medidaDisciplinar.setId(1L);
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(medidaDisciplinar);

        int databaseSizeBeforeCreate = medidaDisciplinarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedidaDisciplinarMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedidaDisciplinar in the database
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = medidaDisciplinarRepository.findAll().size();
        // set the field null
        medidaDisciplinar.setDescricao(null);

        // Create the MedidaDisciplinar, which fails.
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(medidaDisciplinar);

        restMedidaDisciplinarMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isBadRequest());

        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPeriodoIsRequired() throws Exception {
        int databaseSizeBeforeTest = medidaDisciplinarRepository.findAll().size();
        // set the field null
        medidaDisciplinar.setPeriodo(null);

        // Create the MedidaDisciplinar, which fails.
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(medidaDisciplinar);

        restMedidaDisciplinarMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isBadRequest());

        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSuspensaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = medidaDisciplinarRepository.findAll().size();
        // set the field null
        medidaDisciplinar.setSuspensao(null);

        // Create the MedidaDisciplinar, which fails.
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(medidaDisciplinar);

        restMedidaDisciplinarMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isBadRequest());

        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinars() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList
        restMedidaDisciplinarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medidaDisciplinar.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())))
            .andExpect(jsonPath("$.[*].suspensao").value(hasItem(DEFAULT_SUSPENSAO.toString())))
            .andExpect(jsonPath("$.[*].tempo").value(hasItem(DEFAULT_TEMPO)));
    }

    @Test
    @Transactional
    void getMedidaDisciplinar() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get the medidaDisciplinar
        restMedidaDisciplinarMockMvc
            .perform(get(ENTITY_API_URL_ID, medidaDisciplinar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medidaDisciplinar.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO.toString()))
            .andExpect(jsonPath("$.suspensao").value(DEFAULT_SUSPENSAO.toString()))
            .andExpect(jsonPath("$.tempo").value(DEFAULT_TEMPO));
    }

    @Test
    @Transactional
    void getMedidaDisciplinarsByIdFiltering() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        Long id = medidaDisciplinar.getId();

        defaultMedidaDisciplinarShouldBeFound("id.equals=" + id);
        defaultMedidaDisciplinarShouldNotBeFound("id.notEquals=" + id);

        defaultMedidaDisciplinarShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMedidaDisciplinarShouldNotBeFound("id.greaterThan=" + id);

        defaultMedidaDisciplinarShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMedidaDisciplinarShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where descricao equals to DEFAULT_DESCRICAO
        defaultMedidaDisciplinarShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the medidaDisciplinarList where descricao equals to UPDATED_DESCRICAO
        defaultMedidaDisciplinarShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultMedidaDisciplinarShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the medidaDisciplinarList where descricao equals to UPDATED_DESCRICAO
        defaultMedidaDisciplinarShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where descricao is not null
        defaultMedidaDisciplinarShouldBeFound("descricao.specified=true");

        // Get all the medidaDisciplinarList where descricao is null
        defaultMedidaDisciplinarShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where descricao contains DEFAULT_DESCRICAO
        defaultMedidaDisciplinarShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the medidaDisciplinarList where descricao contains UPDATED_DESCRICAO
        defaultMedidaDisciplinarShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where descricao does not contain DEFAULT_DESCRICAO
        defaultMedidaDisciplinarShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the medidaDisciplinarList where descricao does not contain UPDATED_DESCRICAO
        defaultMedidaDisciplinarShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where periodo equals to DEFAULT_PERIODO
        defaultMedidaDisciplinarShouldBeFound("periodo.equals=" + DEFAULT_PERIODO);

        // Get all the medidaDisciplinarList where periodo equals to UPDATED_PERIODO
        defaultMedidaDisciplinarShouldNotBeFound("periodo.equals=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where periodo in DEFAULT_PERIODO or UPDATED_PERIODO
        defaultMedidaDisciplinarShouldBeFound("periodo.in=" + DEFAULT_PERIODO + "," + UPDATED_PERIODO);

        // Get all the medidaDisciplinarList where periodo equals to UPDATED_PERIODO
        defaultMedidaDisciplinarShouldNotBeFound("periodo.in=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where periodo is not null
        defaultMedidaDisciplinarShouldBeFound("periodo.specified=true");

        // Get all the medidaDisciplinarList where periodo is null
        defaultMedidaDisciplinarShouldNotBeFound("periodo.specified=false");
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsBySuspensaoIsEqualToSomething() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where suspensao equals to DEFAULT_SUSPENSAO
        defaultMedidaDisciplinarShouldBeFound("suspensao.equals=" + DEFAULT_SUSPENSAO);

        // Get all the medidaDisciplinarList where suspensao equals to UPDATED_SUSPENSAO
        defaultMedidaDisciplinarShouldNotBeFound("suspensao.equals=" + UPDATED_SUSPENSAO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsBySuspensaoIsInShouldWork() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where suspensao in DEFAULT_SUSPENSAO or UPDATED_SUSPENSAO
        defaultMedidaDisciplinarShouldBeFound("suspensao.in=" + DEFAULT_SUSPENSAO + "," + UPDATED_SUSPENSAO);

        // Get all the medidaDisciplinarList where suspensao equals to UPDATED_SUSPENSAO
        defaultMedidaDisciplinarShouldNotBeFound("suspensao.in=" + UPDATED_SUSPENSAO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsBySuspensaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where suspensao is not null
        defaultMedidaDisciplinarShouldBeFound("suspensao.specified=true");

        // Get all the medidaDisciplinarList where suspensao is null
        defaultMedidaDisciplinarShouldNotBeFound("suspensao.specified=false");
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByTempoIsEqualToSomething() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where tempo equals to DEFAULT_TEMPO
        defaultMedidaDisciplinarShouldBeFound("tempo.equals=" + DEFAULT_TEMPO);

        // Get all the medidaDisciplinarList where tempo equals to UPDATED_TEMPO
        defaultMedidaDisciplinarShouldNotBeFound("tempo.equals=" + UPDATED_TEMPO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByTempoIsInShouldWork() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where tempo in DEFAULT_TEMPO or UPDATED_TEMPO
        defaultMedidaDisciplinarShouldBeFound("tempo.in=" + DEFAULT_TEMPO + "," + UPDATED_TEMPO);

        // Get all the medidaDisciplinarList where tempo equals to UPDATED_TEMPO
        defaultMedidaDisciplinarShouldNotBeFound("tempo.in=" + UPDATED_TEMPO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByTempoIsNullOrNotNull() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where tempo is not null
        defaultMedidaDisciplinarShouldBeFound("tempo.specified=true");

        // Get all the medidaDisciplinarList where tempo is null
        defaultMedidaDisciplinarShouldNotBeFound("tempo.specified=false");
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByTempoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where tempo is greater than or equal to DEFAULT_TEMPO
        defaultMedidaDisciplinarShouldBeFound("tempo.greaterThanOrEqual=" + DEFAULT_TEMPO);

        // Get all the medidaDisciplinarList where tempo is greater than or equal to UPDATED_TEMPO
        defaultMedidaDisciplinarShouldNotBeFound("tempo.greaterThanOrEqual=" + UPDATED_TEMPO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByTempoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where tempo is less than or equal to DEFAULT_TEMPO
        defaultMedidaDisciplinarShouldBeFound("tempo.lessThanOrEqual=" + DEFAULT_TEMPO);

        // Get all the medidaDisciplinarList where tempo is less than or equal to SMALLER_TEMPO
        defaultMedidaDisciplinarShouldNotBeFound("tempo.lessThanOrEqual=" + SMALLER_TEMPO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByTempoIsLessThanSomething() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where tempo is less than DEFAULT_TEMPO
        defaultMedidaDisciplinarShouldNotBeFound("tempo.lessThan=" + DEFAULT_TEMPO);

        // Get all the medidaDisciplinarList where tempo is less than UPDATED_TEMPO
        defaultMedidaDisciplinarShouldBeFound("tempo.lessThan=" + UPDATED_TEMPO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByTempoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        // Get all the medidaDisciplinarList where tempo is greater than DEFAULT_TEMPO
        defaultMedidaDisciplinarShouldNotBeFound("tempo.greaterThan=" + DEFAULT_TEMPO);

        // Get all the medidaDisciplinarList where tempo is greater than SMALLER_TEMPO
        defaultMedidaDisciplinarShouldBeFound("tempo.greaterThan=" + SMALLER_TEMPO);
    }

    @Test
    @Transactional
    void getAllMedidaDisciplinarsByCategoriaOcorrenciaIsEqualToSomething() throws Exception {
        CategoriaOcorrencia categoriaOcorrencia;
        if (TestUtil.findAll(em, CategoriaOcorrencia.class).isEmpty()) {
            medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);
            categoriaOcorrencia = CategoriaOcorrenciaResourceIT.createEntity(em);
        } else {
            categoriaOcorrencia = TestUtil.findAll(em, CategoriaOcorrencia.class).get(0);
        }
        em.persist(categoriaOcorrencia);
        em.flush();
        medidaDisciplinar.addCategoriaOcorrencia(categoriaOcorrencia);
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);
        Long categoriaOcorrenciaId = categoriaOcorrencia.getId();

        // Get all the medidaDisciplinarList where categoriaOcorrencia equals to categoriaOcorrenciaId
        defaultMedidaDisciplinarShouldBeFound("categoriaOcorrenciaId.equals=" + categoriaOcorrenciaId);

        // Get all the medidaDisciplinarList where categoriaOcorrencia equals to (categoriaOcorrenciaId + 1)
        defaultMedidaDisciplinarShouldNotBeFound("categoriaOcorrenciaId.equals=" + (categoriaOcorrenciaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMedidaDisciplinarShouldBeFound(String filter) throws Exception {
        restMedidaDisciplinarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medidaDisciplinar.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())))
            .andExpect(jsonPath("$.[*].suspensao").value(hasItem(DEFAULT_SUSPENSAO.toString())))
            .andExpect(jsonPath("$.[*].tempo").value(hasItem(DEFAULT_TEMPO)));

        // Check, that the count call also returns 1
        restMedidaDisciplinarMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMedidaDisciplinarShouldNotBeFound(String filter) throws Exception {
        restMedidaDisciplinarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMedidaDisciplinarMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMedidaDisciplinar() throws Exception {
        // Get the medidaDisciplinar
        restMedidaDisciplinarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMedidaDisciplinar() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        int databaseSizeBeforeUpdate = medidaDisciplinarRepository.findAll().size();

        // Update the medidaDisciplinar
        MedidaDisciplinar updatedMedidaDisciplinar = medidaDisciplinarRepository.findById(medidaDisciplinar.getId()).get();
        // Disconnect from session so that the updates on updatedMedidaDisciplinar are not directly saved in db
        em.detach(updatedMedidaDisciplinar);
        updatedMedidaDisciplinar.descricao(UPDATED_DESCRICAO).periodo(UPDATED_PERIODO).suspensao(UPDATED_SUSPENSAO).tempo(UPDATED_TEMPO);
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(updatedMedidaDisciplinar);

        restMedidaDisciplinarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medidaDisciplinarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isOk());

        // Validate the MedidaDisciplinar in the database
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeUpdate);
        MedidaDisciplinar testMedidaDisciplinar = medidaDisciplinarList.get(medidaDisciplinarList.size() - 1);
        assertThat(testMedidaDisciplinar.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMedidaDisciplinar.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testMedidaDisciplinar.getSuspensao()).isEqualTo(UPDATED_SUSPENSAO);
        assertThat(testMedidaDisciplinar.getTempo()).isEqualTo(UPDATED_TEMPO);
    }

    @Test
    @Transactional
    void putNonExistingMedidaDisciplinar() throws Exception {
        int databaseSizeBeforeUpdate = medidaDisciplinarRepository.findAll().size();
        medidaDisciplinar.setId(count.incrementAndGet());

        // Create the MedidaDisciplinar
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(medidaDisciplinar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedidaDisciplinarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medidaDisciplinarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedidaDisciplinar in the database
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedidaDisciplinar() throws Exception {
        int databaseSizeBeforeUpdate = medidaDisciplinarRepository.findAll().size();
        medidaDisciplinar.setId(count.incrementAndGet());

        // Create the MedidaDisciplinar
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(medidaDisciplinar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedidaDisciplinarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedidaDisciplinar in the database
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedidaDisciplinar() throws Exception {
        int databaseSizeBeforeUpdate = medidaDisciplinarRepository.findAll().size();
        medidaDisciplinar.setId(count.incrementAndGet());

        // Create the MedidaDisciplinar
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(medidaDisciplinar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedidaDisciplinarMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MedidaDisciplinar in the database
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedidaDisciplinarWithPatch() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        int databaseSizeBeforeUpdate = medidaDisciplinarRepository.findAll().size();

        // Update the medidaDisciplinar using partial update
        MedidaDisciplinar partialUpdatedMedidaDisciplinar = new MedidaDisciplinar();
        partialUpdatedMedidaDisciplinar.setId(medidaDisciplinar.getId());

        partialUpdatedMedidaDisciplinar.suspensao(UPDATED_SUSPENSAO).tempo(UPDATED_TEMPO);

        restMedidaDisciplinarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedidaDisciplinar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedidaDisciplinar))
            )
            .andExpect(status().isOk());

        // Validate the MedidaDisciplinar in the database
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeUpdate);
        MedidaDisciplinar testMedidaDisciplinar = medidaDisciplinarList.get(medidaDisciplinarList.size() - 1);
        assertThat(testMedidaDisciplinar.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMedidaDisciplinar.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testMedidaDisciplinar.getSuspensao()).isEqualTo(UPDATED_SUSPENSAO);
        assertThat(testMedidaDisciplinar.getTempo()).isEqualTo(UPDATED_TEMPO);
    }

    @Test
    @Transactional
    void fullUpdateMedidaDisciplinarWithPatch() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        int databaseSizeBeforeUpdate = medidaDisciplinarRepository.findAll().size();

        // Update the medidaDisciplinar using partial update
        MedidaDisciplinar partialUpdatedMedidaDisciplinar = new MedidaDisciplinar();
        partialUpdatedMedidaDisciplinar.setId(medidaDisciplinar.getId());

        partialUpdatedMedidaDisciplinar
            .descricao(UPDATED_DESCRICAO)
            .periodo(UPDATED_PERIODO)
            .suspensao(UPDATED_SUSPENSAO)
            .tempo(UPDATED_TEMPO);

        restMedidaDisciplinarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedidaDisciplinar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedidaDisciplinar))
            )
            .andExpect(status().isOk());

        // Validate the MedidaDisciplinar in the database
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeUpdate);
        MedidaDisciplinar testMedidaDisciplinar = medidaDisciplinarList.get(medidaDisciplinarList.size() - 1);
        assertThat(testMedidaDisciplinar.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMedidaDisciplinar.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testMedidaDisciplinar.getSuspensao()).isEqualTo(UPDATED_SUSPENSAO);
        assertThat(testMedidaDisciplinar.getTempo()).isEqualTo(UPDATED_TEMPO);
    }

    @Test
    @Transactional
    void patchNonExistingMedidaDisciplinar() throws Exception {
        int databaseSizeBeforeUpdate = medidaDisciplinarRepository.findAll().size();
        medidaDisciplinar.setId(count.incrementAndGet());

        // Create the MedidaDisciplinar
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(medidaDisciplinar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedidaDisciplinarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medidaDisciplinarDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedidaDisciplinar in the database
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedidaDisciplinar() throws Exception {
        int databaseSizeBeforeUpdate = medidaDisciplinarRepository.findAll().size();
        medidaDisciplinar.setId(count.incrementAndGet());

        // Create the MedidaDisciplinar
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(medidaDisciplinar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedidaDisciplinarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedidaDisciplinar in the database
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedidaDisciplinar() throws Exception {
        int databaseSizeBeforeUpdate = medidaDisciplinarRepository.findAll().size();
        medidaDisciplinar.setId(count.incrementAndGet());

        // Create the MedidaDisciplinar
        MedidaDisciplinarDTO medidaDisciplinarDTO = medidaDisciplinarMapper.toDto(medidaDisciplinar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedidaDisciplinarMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medidaDisciplinarDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MedidaDisciplinar in the database
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedidaDisciplinar() throws Exception {
        // Initialize the database
        medidaDisciplinarRepository.saveAndFlush(medidaDisciplinar);

        int databaseSizeBeforeDelete = medidaDisciplinarRepository.findAll().size();

        // Delete the medidaDisciplinar
        restMedidaDisciplinarMockMvc
            .perform(delete(ENTITY_API_URL_ID, medidaDisciplinar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedidaDisciplinar> medidaDisciplinarList = medidaDisciplinarRepository.findAll();
        assertThat(medidaDisciplinarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
