package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.CampoActuacaoDissertacao;
import com.ravunana.longonkelo.domain.Curso;
import com.ravunana.longonkelo.repository.CampoActuacaoDissertacaoRepository;
import com.ravunana.longonkelo.service.CampoActuacaoDissertacaoService;
import com.ravunana.longonkelo.service.criteria.CampoActuacaoDissertacaoCriteria;
import com.ravunana.longonkelo.service.dto.CampoActuacaoDissertacaoDTO;
import com.ravunana.longonkelo.service.mapper.CampoActuacaoDissertacaoMapper;
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
 * Integration tests for the {@link CampoActuacaoDissertacaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CampoActuacaoDissertacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVO = false;
    private static final Boolean UPDATED_IS_ACTIVO = true;

    private static final String ENTITY_API_URL = "/api/campo-actuacao-dissertacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CampoActuacaoDissertacaoRepository campoActuacaoDissertacaoRepository;

    @Mock
    private CampoActuacaoDissertacaoRepository campoActuacaoDissertacaoRepositoryMock;

    @Autowired
    private CampoActuacaoDissertacaoMapper campoActuacaoDissertacaoMapper;

    @Mock
    private CampoActuacaoDissertacaoService campoActuacaoDissertacaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCampoActuacaoDissertacaoMockMvc;

    private CampoActuacaoDissertacao campoActuacaoDissertacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CampoActuacaoDissertacao createEntity(EntityManager em) {
        CampoActuacaoDissertacao campoActuacaoDissertacao = new CampoActuacaoDissertacao()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .isActivo(DEFAULT_IS_ACTIVO);
        return campoActuacaoDissertacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CampoActuacaoDissertacao createUpdatedEntity(EntityManager em) {
        CampoActuacaoDissertacao campoActuacaoDissertacao = new CampoActuacaoDissertacao()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .isActivo(UPDATED_IS_ACTIVO);
        return campoActuacaoDissertacao;
    }

    @BeforeEach
    public void initTest() {
        campoActuacaoDissertacao = createEntity(em);
    }

    @Test
    @Transactional
    void createCampoActuacaoDissertacao() throws Exception {
        int databaseSizeBeforeCreate = campoActuacaoDissertacaoRepository.findAll().size();
        // Create the CampoActuacaoDissertacao
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO = campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacao);
        restCampoActuacaoDissertacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campoActuacaoDissertacaoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CampoActuacaoDissertacao in the database
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeCreate + 1);
        CampoActuacaoDissertacao testCampoActuacaoDissertacao = campoActuacaoDissertacaoList.get(campoActuacaoDissertacaoList.size() - 1);
        assertThat(testCampoActuacaoDissertacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCampoActuacaoDissertacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCampoActuacaoDissertacao.getIsActivo()).isEqualTo(DEFAULT_IS_ACTIVO);
    }

    @Test
    @Transactional
    void createCampoActuacaoDissertacaoWithExistingId() throws Exception {
        // Create the CampoActuacaoDissertacao with an existing ID
        campoActuacaoDissertacao.setId(1L);
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO = campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacao);

        int databaseSizeBeforeCreate = campoActuacaoDissertacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampoActuacaoDissertacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campoActuacaoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CampoActuacaoDissertacao in the database
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = campoActuacaoDissertacaoRepository.findAll().size();
        // set the field null
        campoActuacaoDissertacao.setNome(null);

        // Create the CampoActuacaoDissertacao, which fails.
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO = campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacao);

        restCampoActuacaoDissertacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campoActuacaoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCampoActuacaoDissertacaos() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        // Get all the campoActuacaoDissertacaoList
        restCampoActuacaoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campoActuacaoDissertacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].isActivo").value(hasItem(DEFAULT_IS_ACTIVO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCampoActuacaoDissertacaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(campoActuacaoDissertacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCampoActuacaoDissertacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(campoActuacaoDissertacaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCampoActuacaoDissertacaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(campoActuacaoDissertacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCampoActuacaoDissertacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(campoActuacaoDissertacaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCampoActuacaoDissertacao() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        // Get the campoActuacaoDissertacao
        restCampoActuacaoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, campoActuacaoDissertacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(campoActuacaoDissertacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.isActivo").value(DEFAULT_IS_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getCampoActuacaoDissertacaosByIdFiltering() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        Long id = campoActuacaoDissertacao.getId();

        defaultCampoActuacaoDissertacaoShouldBeFound("id.equals=" + id);
        defaultCampoActuacaoDissertacaoShouldNotBeFound("id.notEquals=" + id);

        defaultCampoActuacaoDissertacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCampoActuacaoDissertacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultCampoActuacaoDissertacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCampoActuacaoDissertacaoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCampoActuacaoDissertacaosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        // Get all the campoActuacaoDissertacaoList where nome equals to DEFAULT_NOME
        defaultCampoActuacaoDissertacaoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the campoActuacaoDissertacaoList where nome equals to UPDATED_NOME
        defaultCampoActuacaoDissertacaoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCampoActuacaoDissertacaosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        // Get all the campoActuacaoDissertacaoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultCampoActuacaoDissertacaoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the campoActuacaoDissertacaoList where nome equals to UPDATED_NOME
        defaultCampoActuacaoDissertacaoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCampoActuacaoDissertacaosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        // Get all the campoActuacaoDissertacaoList where nome is not null
        defaultCampoActuacaoDissertacaoShouldBeFound("nome.specified=true");

        // Get all the campoActuacaoDissertacaoList where nome is null
        defaultCampoActuacaoDissertacaoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllCampoActuacaoDissertacaosByNomeContainsSomething() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        // Get all the campoActuacaoDissertacaoList where nome contains DEFAULT_NOME
        defaultCampoActuacaoDissertacaoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the campoActuacaoDissertacaoList where nome contains UPDATED_NOME
        defaultCampoActuacaoDissertacaoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCampoActuacaoDissertacaosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        // Get all the campoActuacaoDissertacaoList where nome does not contain DEFAULT_NOME
        defaultCampoActuacaoDissertacaoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the campoActuacaoDissertacaoList where nome does not contain UPDATED_NOME
        defaultCampoActuacaoDissertacaoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCampoActuacaoDissertacaosByIsActivoIsEqualToSomething() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        // Get all the campoActuacaoDissertacaoList where isActivo equals to DEFAULT_IS_ACTIVO
        defaultCampoActuacaoDissertacaoShouldBeFound("isActivo.equals=" + DEFAULT_IS_ACTIVO);

        // Get all the campoActuacaoDissertacaoList where isActivo equals to UPDATED_IS_ACTIVO
        defaultCampoActuacaoDissertacaoShouldNotBeFound("isActivo.equals=" + UPDATED_IS_ACTIVO);
    }

    @Test
    @Transactional
    void getAllCampoActuacaoDissertacaosByIsActivoIsInShouldWork() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        // Get all the campoActuacaoDissertacaoList where isActivo in DEFAULT_IS_ACTIVO or UPDATED_IS_ACTIVO
        defaultCampoActuacaoDissertacaoShouldBeFound("isActivo.in=" + DEFAULT_IS_ACTIVO + "," + UPDATED_IS_ACTIVO);

        // Get all the campoActuacaoDissertacaoList where isActivo equals to UPDATED_IS_ACTIVO
        defaultCampoActuacaoDissertacaoShouldNotBeFound("isActivo.in=" + UPDATED_IS_ACTIVO);
    }

    @Test
    @Transactional
    void getAllCampoActuacaoDissertacaosByIsActivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        // Get all the campoActuacaoDissertacaoList where isActivo is not null
        defaultCampoActuacaoDissertacaoShouldBeFound("isActivo.specified=true");

        // Get all the campoActuacaoDissertacaoList where isActivo is null
        defaultCampoActuacaoDissertacaoShouldNotBeFound("isActivo.specified=false");
    }

    @Test
    @Transactional
    void getAllCampoActuacaoDissertacaosByCursosIsEqualToSomething() throws Exception {
        Curso cursos;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);
            cursos = CursoResourceIT.createEntity(em);
        } else {
            cursos = TestUtil.findAll(em, Curso.class).get(0);
        }
        em.persist(cursos);
        em.flush();
        campoActuacaoDissertacao.addCursos(cursos);
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);
        Long cursosId = cursos.getId();

        // Get all the campoActuacaoDissertacaoList where cursos equals to cursosId
        defaultCampoActuacaoDissertacaoShouldBeFound("cursosId.equals=" + cursosId);

        // Get all the campoActuacaoDissertacaoList where cursos equals to (cursosId + 1)
        defaultCampoActuacaoDissertacaoShouldNotBeFound("cursosId.equals=" + (cursosId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCampoActuacaoDissertacaoShouldBeFound(String filter) throws Exception {
        restCampoActuacaoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campoActuacaoDissertacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].isActivo").value(hasItem(DEFAULT_IS_ACTIVO.booleanValue())));

        // Check, that the count call also returns 1
        restCampoActuacaoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCampoActuacaoDissertacaoShouldNotBeFound(String filter) throws Exception {
        restCampoActuacaoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCampoActuacaoDissertacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCampoActuacaoDissertacao() throws Exception {
        // Get the campoActuacaoDissertacao
        restCampoActuacaoDissertacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCampoActuacaoDissertacao() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        int databaseSizeBeforeUpdate = campoActuacaoDissertacaoRepository.findAll().size();

        // Update the campoActuacaoDissertacao
        CampoActuacaoDissertacao updatedCampoActuacaoDissertacao = campoActuacaoDissertacaoRepository
            .findById(campoActuacaoDissertacao.getId())
            .get();
        // Disconnect from session so that the updates on updatedCampoActuacaoDissertacao are not directly saved in db
        em.detach(updatedCampoActuacaoDissertacao);
        updatedCampoActuacaoDissertacao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).isActivo(UPDATED_IS_ACTIVO);
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO = campoActuacaoDissertacaoMapper.toDto(updatedCampoActuacaoDissertacao);

        restCampoActuacaoDissertacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, campoActuacaoDissertacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campoActuacaoDissertacaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CampoActuacaoDissertacao in the database
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
        CampoActuacaoDissertacao testCampoActuacaoDissertacao = campoActuacaoDissertacaoList.get(campoActuacaoDissertacaoList.size() - 1);
        assertThat(testCampoActuacaoDissertacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCampoActuacaoDissertacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCampoActuacaoDissertacao.getIsActivo()).isEqualTo(UPDATED_IS_ACTIVO);
    }

    @Test
    @Transactional
    void putNonExistingCampoActuacaoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = campoActuacaoDissertacaoRepository.findAll().size();
        campoActuacaoDissertacao.setId(count.incrementAndGet());

        // Create the CampoActuacaoDissertacao
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO = campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampoActuacaoDissertacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, campoActuacaoDissertacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campoActuacaoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CampoActuacaoDissertacao in the database
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCampoActuacaoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = campoActuacaoDissertacaoRepository.findAll().size();
        campoActuacaoDissertacao.setId(count.incrementAndGet());

        // Create the CampoActuacaoDissertacao
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO = campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampoActuacaoDissertacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campoActuacaoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CampoActuacaoDissertacao in the database
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCampoActuacaoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = campoActuacaoDissertacaoRepository.findAll().size();
        campoActuacaoDissertacao.setId(count.incrementAndGet());

        // Create the CampoActuacaoDissertacao
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO = campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampoActuacaoDissertacaoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campoActuacaoDissertacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CampoActuacaoDissertacao in the database
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCampoActuacaoDissertacaoWithPatch() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        int databaseSizeBeforeUpdate = campoActuacaoDissertacaoRepository.findAll().size();

        // Update the campoActuacaoDissertacao using partial update
        CampoActuacaoDissertacao partialUpdatedCampoActuacaoDissertacao = new CampoActuacaoDissertacao();
        partialUpdatedCampoActuacaoDissertacao.setId(campoActuacaoDissertacao.getId());

        partialUpdatedCampoActuacaoDissertacao.nome(UPDATED_NOME).isActivo(UPDATED_IS_ACTIVO);

        restCampoActuacaoDissertacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCampoActuacaoDissertacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCampoActuacaoDissertacao))
            )
            .andExpect(status().isOk());

        // Validate the CampoActuacaoDissertacao in the database
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
        CampoActuacaoDissertacao testCampoActuacaoDissertacao = campoActuacaoDissertacaoList.get(campoActuacaoDissertacaoList.size() - 1);
        assertThat(testCampoActuacaoDissertacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCampoActuacaoDissertacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCampoActuacaoDissertacao.getIsActivo()).isEqualTo(UPDATED_IS_ACTIVO);
    }

    @Test
    @Transactional
    void fullUpdateCampoActuacaoDissertacaoWithPatch() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        int databaseSizeBeforeUpdate = campoActuacaoDissertacaoRepository.findAll().size();

        // Update the campoActuacaoDissertacao using partial update
        CampoActuacaoDissertacao partialUpdatedCampoActuacaoDissertacao = new CampoActuacaoDissertacao();
        partialUpdatedCampoActuacaoDissertacao.setId(campoActuacaoDissertacao.getId());

        partialUpdatedCampoActuacaoDissertacao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).isActivo(UPDATED_IS_ACTIVO);

        restCampoActuacaoDissertacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCampoActuacaoDissertacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCampoActuacaoDissertacao))
            )
            .andExpect(status().isOk());

        // Validate the CampoActuacaoDissertacao in the database
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
        CampoActuacaoDissertacao testCampoActuacaoDissertacao = campoActuacaoDissertacaoList.get(campoActuacaoDissertacaoList.size() - 1);
        assertThat(testCampoActuacaoDissertacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCampoActuacaoDissertacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCampoActuacaoDissertacao.getIsActivo()).isEqualTo(UPDATED_IS_ACTIVO);
    }

    @Test
    @Transactional
    void patchNonExistingCampoActuacaoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = campoActuacaoDissertacaoRepository.findAll().size();
        campoActuacaoDissertacao.setId(count.incrementAndGet());

        // Create the CampoActuacaoDissertacao
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO = campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampoActuacaoDissertacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, campoActuacaoDissertacaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(campoActuacaoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CampoActuacaoDissertacao in the database
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCampoActuacaoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = campoActuacaoDissertacaoRepository.findAll().size();
        campoActuacaoDissertacao.setId(count.incrementAndGet());

        // Create the CampoActuacaoDissertacao
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO = campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampoActuacaoDissertacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(campoActuacaoDissertacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CampoActuacaoDissertacao in the database
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCampoActuacaoDissertacao() throws Exception {
        int databaseSizeBeforeUpdate = campoActuacaoDissertacaoRepository.findAll().size();
        campoActuacaoDissertacao.setId(count.incrementAndGet());

        // Create the CampoActuacaoDissertacao
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO = campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampoActuacaoDissertacaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(campoActuacaoDissertacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CampoActuacaoDissertacao in the database
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCampoActuacaoDissertacao() throws Exception {
        // Initialize the database
        campoActuacaoDissertacaoRepository.saveAndFlush(campoActuacaoDissertacao);

        int databaseSizeBeforeDelete = campoActuacaoDissertacaoRepository.findAll().size();

        // Delete the campoActuacaoDissertacao
        restCampoActuacaoDissertacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, campoActuacaoDissertacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CampoActuacaoDissertacao> campoActuacaoDissertacaoList = campoActuacaoDissertacaoRepository.findAll();
        assertThat(campoActuacaoDissertacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
