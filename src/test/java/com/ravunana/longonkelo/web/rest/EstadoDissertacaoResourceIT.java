package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.DissertacaoFinalCurso;
import com.ravunana.longonkelo.domain.EstadoDissertacao;
import com.ravunana.longonkelo.repository.EstadoDissertacaoRepository;
import com.ravunana.longonkelo.service.criteria.EstadoDissertacaoCriteria;
import com.ravunana.longonkelo.service.dto.EstadoDissertacaoDTO;
import com.ravunana.longonkelo.service.mapper.EstadoDissertacaoMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link EstadoDissertacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstadoDissertacaoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ETAPA = 0;
    private static final Integer UPDATED_ETAPA = 1;
    private static final Integer SMALLER_ETAPA = 0 - 1;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estado-dissertacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstadoDissertacaoRepository estadoDissertacaoRepository;

    @Autowired
    private EstadoDissertacaoMapper estadoDissertacaoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoDissertacaoMockMvc;

    private EstadoDissertacao estadoDissertacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoDissertacao createEntity(EntityManager em) {
        EstadoDissertacao estadoDissertacao = new EstadoDissertacao()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .etapa(DEFAULT_ETAPA)
            .descricao(DEFAULT_DESCRICAO);
        return estadoDissertacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoDissertacao createUpdatedEntity(EntityManager em) {
        EstadoDissertacao estadoDissertacao = new EstadoDissertacao()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .etapa(UPDATED_ETAPA)
            .descricao(UPDATED_DESCRICAO);
        return estadoDissertacao;
    }

    @BeforeEach
    public void initTest() {
        estadoDissertacao = createEntity(em);
    }

    @Test
    @Transactional
    void createEstadoDissertacao() throws Exception {
        int databaseSizeBeforeCreate = estadoDissertacaoRepository.findAll().size();
        // Create the EstadoDissertacao
        EstadoDissertacaoDTO estadoDissertacaoDTO = estadoDissertacaoMapper.toDto(estadoDissertacao);
        restEstadoDissertacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDissertacaoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EstadoDissertacao in the database
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoDissertacao testEstadoDissertacao = estadoDissertacaoList.get(estadoDissertacaoList.size() - 1);
        assertThat(testEstadoDissertacao.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testEstadoDissertacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEstadoDissertacao.getEtapa()).isEqualTo(DEFAULT_ETAPA);
        assertThat(testEstadoDissertacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createEstadoDissertacaoWithExistingId() throws Exception {
        // Create the EstadoDissertacao with an existing ID
        estadoDissertacao.setId(1L);
        EstadoDissertacaoDTO estadoDissertacaoDTO = estadoDissertacaoMapper.toDto(estadoDissertacao);

        int databaseSizeBeforeCreate = estadoDissertacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoDissertacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoDissertacao in the database
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoDissertacaoRepository.findAll().size();
        // set the field null
        estadoDissertacao.setCodigo(null);

        // Create the EstadoDissertacao, which fails.
        EstadoDissertacaoDTO estadoDissertacaoDTO = estadoDissertacaoMapper.toDto(estadoDissertacao);

        restEstadoDissertacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoDissertacaoRepository.findAll().size();
        // set the field null
        estadoDissertacao.setNome(null);

        // Create the EstadoDissertacao, which fails.
        EstadoDissertacaoDTO estadoDissertacaoDTO = estadoDissertacaoMapper.toDto(estadoDissertacao);

        restEstadoDissertacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaos() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList
        restEstadoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoDissertacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].etapa").value(hasItem(DEFAULT_ETAPA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getEstadoDissertacao() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get the estadoDissertacao
        restEstadoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, estadoDissertacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoDissertacao.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.etapa").value(DEFAULT_ETAPA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getEstadoDissertacaosByIdFiltering() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        Long id = estadoDissertacao.getId();

        defaultEstadoDissertacaoShouldBeFound("id.equals=" + id);
        defaultEstadoDissertacaoShouldNotBeFound("id.notEquals=" + id);

        defaultEstadoDissertacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEstadoDissertacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultEstadoDissertacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEstadoDissertacaoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where codigo equals to DEFAULT_CODIGO
        defaultEstadoDissertacaoShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the estadoDissertacaoList where codigo equals to UPDATED_CODIGO
        defaultEstadoDissertacaoShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultEstadoDissertacaoShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the estadoDissertacaoList where codigo equals to UPDATED_CODIGO
        defaultEstadoDissertacaoShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where codigo is not null
        defaultEstadoDissertacaoShouldBeFound("codigo.specified=true");

        // Get all the estadoDissertacaoList where codigo is null
        defaultEstadoDissertacaoShouldNotBeFound("codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByCodigoContainsSomething() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where codigo contains DEFAULT_CODIGO
        defaultEstadoDissertacaoShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the estadoDissertacaoList where codigo contains UPDATED_CODIGO
        defaultEstadoDissertacaoShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where codigo does not contain DEFAULT_CODIGO
        defaultEstadoDissertacaoShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the estadoDissertacaoList where codigo does not contain UPDATED_CODIGO
        defaultEstadoDissertacaoShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where nome equals to DEFAULT_NOME
        defaultEstadoDissertacaoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the estadoDissertacaoList where nome equals to UPDATED_NOME
        defaultEstadoDissertacaoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultEstadoDissertacaoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the estadoDissertacaoList where nome equals to UPDATED_NOME
        defaultEstadoDissertacaoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where nome is not null
        defaultEstadoDissertacaoShouldBeFound("nome.specified=true");

        // Get all the estadoDissertacaoList where nome is null
        defaultEstadoDissertacaoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByNomeContainsSomething() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where nome contains DEFAULT_NOME
        defaultEstadoDissertacaoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the estadoDissertacaoList where nome contains UPDATED_NOME
        defaultEstadoDissertacaoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where nome does not contain DEFAULT_NOME
        defaultEstadoDissertacaoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the estadoDissertacaoList where nome does not contain UPDATED_NOME
        defaultEstadoDissertacaoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByEtapaIsEqualToSomething() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where etapa equals to DEFAULT_ETAPA
        defaultEstadoDissertacaoShouldBeFound("etapa.equals=" + DEFAULT_ETAPA);

        // Get all the estadoDissertacaoList where etapa equals to UPDATED_ETAPA
        defaultEstadoDissertacaoShouldNotBeFound("etapa.equals=" + UPDATED_ETAPA);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByEtapaIsInShouldWork() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where etapa in DEFAULT_ETAPA or UPDATED_ETAPA
        defaultEstadoDissertacaoShouldBeFound("etapa.in=" + DEFAULT_ETAPA + "," + UPDATED_ETAPA);

        // Get all the estadoDissertacaoList where etapa equals to UPDATED_ETAPA
        defaultEstadoDissertacaoShouldNotBeFound("etapa.in=" + UPDATED_ETAPA);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByEtapaIsNullOrNotNull() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where etapa is not null
        defaultEstadoDissertacaoShouldBeFound("etapa.specified=true");

        // Get all the estadoDissertacaoList where etapa is null
        defaultEstadoDissertacaoShouldNotBeFound("etapa.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByEtapaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where etapa is greater than or equal to DEFAULT_ETAPA
        defaultEstadoDissertacaoShouldBeFound("etapa.greaterThanOrEqual=" + DEFAULT_ETAPA);

        // Get all the estadoDissertacaoList where etapa is greater than or equal to UPDATED_ETAPA
        defaultEstadoDissertacaoShouldNotBeFound("etapa.greaterThanOrEqual=" + UPDATED_ETAPA);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByEtapaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where etapa is less than or equal to DEFAULT_ETAPA
        defaultEstadoDissertacaoShouldBeFound("etapa.lessThanOrEqual=" + DEFAULT_ETAPA);

        // Get all the estadoDissertacaoList where etapa is less than or equal to SMALLER_ETAPA
        defaultEstadoDissertacaoShouldNotBeFound("etapa.lessThanOrEqual=" + SMALLER_ETAPA);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByEtapaIsLessThanSomething() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where etapa is less than DEFAULT_ETAPA
        defaultEstadoDissertacaoShouldNotBeFound("etapa.lessThan=" + DEFAULT_ETAPA);

        // Get all the estadoDissertacaoList where etapa is less than UPDATED_ETAPA
        defaultEstadoDissertacaoShouldBeFound("etapa.lessThan=" + UPDATED_ETAPA);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByEtapaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        // Get all the estadoDissertacaoList where etapa is greater than DEFAULT_ETAPA
        defaultEstadoDissertacaoShouldNotBeFound("etapa.greaterThan=" + DEFAULT_ETAPA);

        // Get all the estadoDissertacaoList where etapa is greater than SMALLER_ETAPA
        defaultEstadoDissertacaoShouldBeFound("etapa.greaterThan=" + SMALLER_ETAPA);
    }

    @Test
    @Transactional
    void getAllEstadoDissertacaosByDissertacaoFinalCursoIsEqualToSomething() throws Exception {
        DissertacaoFinalCurso dissertacaoFinalCurso;
        if (TestUtil.findAll(em, DissertacaoFinalCurso.class).isEmpty()) {
            estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);
            dissertacaoFinalCurso = DissertacaoFinalCursoResourceIT.createEntity(em);
        } else {
            dissertacaoFinalCurso = TestUtil.findAll(em, DissertacaoFinalCurso.class).get(0);
        }
        em.persist(dissertacaoFinalCurso);
        em.flush();
        estadoDissertacao.addDissertacaoFinalCurso(dissertacaoFinalCurso);
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);
        Long dissertacaoFinalCursoId = dissertacaoFinalCurso.getId();

        // Get all the estadoDissertacaoList where dissertacaoFinalCurso equals to dissertacaoFinalCursoId
        defaultEstadoDissertacaoShouldBeFound("dissertacaoFinalCursoId.equals=" + dissertacaoFinalCursoId);

        // Get all the estadoDissertacaoList where dissertacaoFinalCurso equals to (dissertacaoFinalCursoId + 1)
        defaultEstadoDissertacaoShouldNotBeFound("dissertacaoFinalCursoId.equals=" + (dissertacaoFinalCursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstadoDissertacaoShouldBeFound(String filter) throws Exception {
        restEstadoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoDissertacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].etapa").value(hasItem(DEFAULT_ETAPA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restEstadoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstadoDissertacaoShouldNotBeFound(String filter) throws Exception {
        restEstadoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstadoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEstadoDissertacao() throws Exception {
        // Get the estadoDissertacao
        restEstadoDissertacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstadoDissertacao() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        int databaseSizeBeforeUpdate = estadoDissertacaoRepository.findAll().size();

        // Update the estadoDissertacao
        EstadoDissertacao updatedEstadoDissertacao = estadoDissertacaoRepository.findById(estadoDissertacao.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoDissertacao are not directly saved in db
        em.detach(updatedEstadoDissertacao);
        updatedEstadoDissertacao.codigo(UPDATED_CODIGO).nome(UPDATED_NOME).etapa(UPDATED_ETAPA).descricao(UPDATED_DESCRICAO);
        EstadoDissertacaoDTO estadoDissertacaoDTO = estadoDissertacaoMapper.toDto(updatedEstadoDissertacao);

        restEstadoDissertacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoDissertacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDissertacaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the EstadoDissertacao in the database
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
        EstadoDissertacao testEstadoDissertacao = estadoDissertacaoList.get(estadoDissertacaoList.size() - 1);
        assertThat(testEstadoDissertacao.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testEstadoDissertacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEstadoDissertacao.getEtapa()).isEqualTo(UPDATED_ETAPA);
        assertThat(testEstadoDissertacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingEstadoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = estadoDissertacaoRepository.findAll().size();
        estadoDissertacao.setId(count.incrementAndGet());

        // Create the EstadoDissertacao
        EstadoDissertacaoDTO estadoDissertacaoDTO = estadoDissertacaoMapper.toDto(estadoDissertacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoDissertacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoDissertacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoDissertacao in the database
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstadoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = estadoDissertacaoRepository.findAll().size();
        estadoDissertacao.setId(count.incrementAndGet());

        // Create the EstadoDissertacao
        EstadoDissertacaoDTO estadoDissertacaoDTO = estadoDissertacaoMapper.toDto(estadoDissertacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoDissertacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoDissertacao in the database
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstadoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = estadoDissertacaoRepository.findAll().size();
        estadoDissertacao.setId(count.incrementAndGet());

        // Create the EstadoDissertacao
        EstadoDissertacaoDTO estadoDissertacaoDTO = estadoDissertacaoMapper.toDto(estadoDissertacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoDissertacaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoDissertacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoDissertacao in the database
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadoDissertacaoWithPatch() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        int databaseSizeBeforeUpdate = estadoDissertacaoRepository.findAll().size();

        // Update the estadoDissertacao using partial update
        EstadoDissertacao partialUpdatedEstadoDissertacao = new EstadoDissertacao();
        partialUpdatedEstadoDissertacao.setId(estadoDissertacao.getId());

        restEstadoDissertacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoDissertacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoDissertacao))
            )
            .andExpect(status().isOk());

        // Validate the EstadoDissertacao in the database
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
        EstadoDissertacao testEstadoDissertacao = estadoDissertacaoList.get(estadoDissertacaoList.size() - 1);
        assertThat(testEstadoDissertacao.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testEstadoDissertacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEstadoDissertacao.getEtapa()).isEqualTo(DEFAULT_ETAPA);
        assertThat(testEstadoDissertacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateEstadoDissertacaoWithPatch() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        int databaseSizeBeforeUpdate = estadoDissertacaoRepository.findAll().size();

        // Update the estadoDissertacao using partial update
        EstadoDissertacao partialUpdatedEstadoDissertacao = new EstadoDissertacao();
        partialUpdatedEstadoDissertacao.setId(estadoDissertacao.getId());

        partialUpdatedEstadoDissertacao.codigo(UPDATED_CODIGO).nome(UPDATED_NOME).etapa(UPDATED_ETAPA).descricao(UPDATED_DESCRICAO);

        restEstadoDissertacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoDissertacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoDissertacao))
            )
            .andExpect(status().isOk());

        // Validate the EstadoDissertacao in the database
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
        EstadoDissertacao testEstadoDissertacao = estadoDissertacaoList.get(estadoDissertacaoList.size() - 1);
        assertThat(testEstadoDissertacao.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testEstadoDissertacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEstadoDissertacao.getEtapa()).isEqualTo(UPDATED_ETAPA);
        assertThat(testEstadoDissertacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingEstadoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = estadoDissertacaoRepository.findAll().size();
        estadoDissertacao.setId(count.incrementAndGet());

        // Create the EstadoDissertacao
        EstadoDissertacaoDTO estadoDissertacaoDTO = estadoDissertacaoMapper.toDto(estadoDissertacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoDissertacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estadoDissertacaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoDissertacao in the database
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstadoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = estadoDissertacaoRepository.findAll().size();
        estadoDissertacao.setId(count.incrementAndGet());

        // Create the EstadoDissertacao
        EstadoDissertacaoDTO estadoDissertacaoDTO = estadoDissertacaoMapper.toDto(estadoDissertacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoDissertacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoDissertacao in the database
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstadoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = estadoDissertacaoRepository.findAll().size();
        estadoDissertacao.setId(count.incrementAndGet());

        // Create the EstadoDissertacao
        EstadoDissertacaoDTO estadoDissertacaoDTO = estadoDissertacaoMapper.toDto(estadoDissertacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoDissertacaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoDissertacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoDissertacao in the database
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstadoDissertacao() throws Exception {
        // Initialize the database
        estadoDissertacaoRepository.saveAndFlush(estadoDissertacao);

        int databaseSizeBeforeDelete = estadoDissertacaoRepository.findAll().size();

        // Delete the estadoDissertacao
        restEstadoDissertacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, estadoDissertacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoDissertacao> estadoDissertacaoList = estadoDissertacaoRepository.findAll();
        assertThat(estadoDissertacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
