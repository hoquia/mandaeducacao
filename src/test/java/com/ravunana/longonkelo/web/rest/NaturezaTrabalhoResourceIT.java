package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.DissertacaoFinalCurso;
import com.ravunana.longonkelo.domain.NaturezaTrabalho;
import com.ravunana.longonkelo.repository.NaturezaTrabalhoRepository;
import com.ravunana.longonkelo.service.criteria.NaturezaTrabalhoCriteria;
import com.ravunana.longonkelo.service.dto.NaturezaTrabalhoDTO;
import com.ravunana.longonkelo.service.mapper.NaturezaTrabalhoMapper;
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
 * Integration tests for the {@link NaturezaTrabalhoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NaturezaTrabalhoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVO = false;
    private static final Boolean UPDATED_IS_ACTIVO = true;

    private static final String ENTITY_API_URL = "/api/natureza-trabalhos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NaturezaTrabalhoRepository naturezaTrabalhoRepository;

    @Autowired
    private NaturezaTrabalhoMapper naturezaTrabalhoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNaturezaTrabalhoMockMvc;

    private NaturezaTrabalho naturezaTrabalho;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NaturezaTrabalho createEntity(EntityManager em) {
        NaturezaTrabalho naturezaTrabalho = new NaturezaTrabalho()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .isActivo(DEFAULT_IS_ACTIVO);
        return naturezaTrabalho;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NaturezaTrabalho createUpdatedEntity(EntityManager em) {
        NaturezaTrabalho naturezaTrabalho = new NaturezaTrabalho()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .isActivo(UPDATED_IS_ACTIVO);
        return naturezaTrabalho;
    }

    @BeforeEach
    public void initTest() {
        naturezaTrabalho = createEntity(em);
    }

    @Test
    @Transactional
    void createNaturezaTrabalho() throws Exception {
        int databaseSizeBeforeCreate = naturezaTrabalhoRepository.findAll().size();
        // Create the NaturezaTrabalho
        NaturezaTrabalhoDTO naturezaTrabalhoDTO = naturezaTrabalhoMapper.toDto(naturezaTrabalho);
        restNaturezaTrabalhoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(naturezaTrabalhoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NaturezaTrabalho in the database
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeCreate + 1);
        NaturezaTrabalho testNaturezaTrabalho = naturezaTrabalhoList.get(naturezaTrabalhoList.size() - 1);
        assertThat(testNaturezaTrabalho.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testNaturezaTrabalho.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testNaturezaTrabalho.getIsActivo()).isEqualTo(DEFAULT_IS_ACTIVO);
    }

    @Test
    @Transactional
    void createNaturezaTrabalhoWithExistingId() throws Exception {
        // Create the NaturezaTrabalho with an existing ID
        naturezaTrabalho.setId(1L);
        NaturezaTrabalhoDTO naturezaTrabalhoDTO = naturezaTrabalhoMapper.toDto(naturezaTrabalho);

        int databaseSizeBeforeCreate = naturezaTrabalhoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaturezaTrabalhoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(naturezaTrabalhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NaturezaTrabalho in the database
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = naturezaTrabalhoRepository.findAll().size();
        // set the field null
        naturezaTrabalho.setNome(null);

        // Create the NaturezaTrabalho, which fails.
        NaturezaTrabalhoDTO naturezaTrabalhoDTO = naturezaTrabalhoMapper.toDto(naturezaTrabalho);

        restNaturezaTrabalhoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(naturezaTrabalhoDTO))
            )
            .andExpect(status().isBadRequest());

        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNaturezaTrabalhos() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        // Get all the naturezaTrabalhoList
        restNaturezaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naturezaTrabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].isActivo").value(hasItem(DEFAULT_IS_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getNaturezaTrabalho() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        // Get the naturezaTrabalho
        restNaturezaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL_ID, naturezaTrabalho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(naturezaTrabalho.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.isActivo").value(DEFAULT_IS_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNaturezaTrabalhosByIdFiltering() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        Long id = naturezaTrabalho.getId();

        defaultNaturezaTrabalhoShouldBeFound("id.equals=" + id);
        defaultNaturezaTrabalhoShouldNotBeFound("id.notEquals=" + id);

        defaultNaturezaTrabalhoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNaturezaTrabalhoShouldNotBeFound("id.greaterThan=" + id);

        defaultNaturezaTrabalhoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNaturezaTrabalhoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNaturezaTrabalhosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        // Get all the naturezaTrabalhoList where nome equals to DEFAULT_NOME
        defaultNaturezaTrabalhoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the naturezaTrabalhoList where nome equals to UPDATED_NOME
        defaultNaturezaTrabalhoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllNaturezaTrabalhosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        // Get all the naturezaTrabalhoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultNaturezaTrabalhoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the naturezaTrabalhoList where nome equals to UPDATED_NOME
        defaultNaturezaTrabalhoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllNaturezaTrabalhosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        // Get all the naturezaTrabalhoList where nome is not null
        defaultNaturezaTrabalhoShouldBeFound("nome.specified=true");

        // Get all the naturezaTrabalhoList where nome is null
        defaultNaturezaTrabalhoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllNaturezaTrabalhosByNomeContainsSomething() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        // Get all the naturezaTrabalhoList where nome contains DEFAULT_NOME
        defaultNaturezaTrabalhoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the naturezaTrabalhoList where nome contains UPDATED_NOME
        defaultNaturezaTrabalhoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllNaturezaTrabalhosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        // Get all the naturezaTrabalhoList where nome does not contain DEFAULT_NOME
        defaultNaturezaTrabalhoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the naturezaTrabalhoList where nome does not contain UPDATED_NOME
        defaultNaturezaTrabalhoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllNaturezaTrabalhosByIsActivoIsEqualToSomething() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        // Get all the naturezaTrabalhoList where isActivo equals to DEFAULT_IS_ACTIVO
        defaultNaturezaTrabalhoShouldBeFound("isActivo.equals=" + DEFAULT_IS_ACTIVO);

        // Get all the naturezaTrabalhoList where isActivo equals to UPDATED_IS_ACTIVO
        defaultNaturezaTrabalhoShouldNotBeFound("isActivo.equals=" + UPDATED_IS_ACTIVO);
    }

    @Test
    @Transactional
    void getAllNaturezaTrabalhosByIsActivoIsInShouldWork() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        // Get all the naturezaTrabalhoList where isActivo in DEFAULT_IS_ACTIVO or UPDATED_IS_ACTIVO
        defaultNaturezaTrabalhoShouldBeFound("isActivo.in=" + DEFAULT_IS_ACTIVO + "," + UPDATED_IS_ACTIVO);

        // Get all the naturezaTrabalhoList where isActivo equals to UPDATED_IS_ACTIVO
        defaultNaturezaTrabalhoShouldNotBeFound("isActivo.in=" + UPDATED_IS_ACTIVO);
    }

    @Test
    @Transactional
    void getAllNaturezaTrabalhosByIsActivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        // Get all the naturezaTrabalhoList where isActivo is not null
        defaultNaturezaTrabalhoShouldBeFound("isActivo.specified=true");

        // Get all the naturezaTrabalhoList where isActivo is null
        defaultNaturezaTrabalhoShouldNotBeFound("isActivo.specified=false");
    }

    @Test
    @Transactional
    void getAllNaturezaTrabalhosByDissertacaoFinalCursoIsEqualToSomething() throws Exception {
        DissertacaoFinalCurso dissertacaoFinalCurso;
        if (TestUtil.findAll(em, DissertacaoFinalCurso.class).isEmpty()) {
            naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);
            dissertacaoFinalCurso = DissertacaoFinalCursoResourceIT.createEntity(em);
        } else {
            dissertacaoFinalCurso = TestUtil.findAll(em, DissertacaoFinalCurso.class).get(0);
        }
        em.persist(dissertacaoFinalCurso);
        em.flush();
        naturezaTrabalho.addDissertacaoFinalCurso(dissertacaoFinalCurso);
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);
        Long dissertacaoFinalCursoId = dissertacaoFinalCurso.getId();

        // Get all the naturezaTrabalhoList where dissertacaoFinalCurso equals to dissertacaoFinalCursoId
        defaultNaturezaTrabalhoShouldBeFound("dissertacaoFinalCursoId.equals=" + dissertacaoFinalCursoId);

        // Get all the naturezaTrabalhoList where dissertacaoFinalCurso equals to (dissertacaoFinalCursoId + 1)
        defaultNaturezaTrabalhoShouldNotBeFound("dissertacaoFinalCursoId.equals=" + (dissertacaoFinalCursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNaturezaTrabalhoShouldBeFound(String filter) throws Exception {
        restNaturezaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naturezaTrabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].isActivo").value(hasItem(DEFAULT_IS_ACTIVO.booleanValue())));

        // Check, that the count call also returns 1
        restNaturezaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNaturezaTrabalhoShouldNotBeFound(String filter) throws Exception {
        restNaturezaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNaturezaTrabalhoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNaturezaTrabalho() throws Exception {
        // Get the naturezaTrabalho
        restNaturezaTrabalhoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNaturezaTrabalho() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        int databaseSizeBeforeUpdate = naturezaTrabalhoRepository.findAll().size();

        // Update the naturezaTrabalho
        NaturezaTrabalho updatedNaturezaTrabalho = naturezaTrabalhoRepository.findById(naturezaTrabalho.getId()).get();
        // Disconnect from session so that the updates on updatedNaturezaTrabalho are not directly saved in db
        em.detach(updatedNaturezaTrabalho);
        updatedNaturezaTrabalho.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).isActivo(UPDATED_IS_ACTIVO);
        NaturezaTrabalhoDTO naturezaTrabalhoDTO = naturezaTrabalhoMapper.toDto(updatedNaturezaTrabalho);

        restNaturezaTrabalhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, naturezaTrabalhoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(naturezaTrabalhoDTO))
            )
            .andExpect(status().isOk());

        // Validate the NaturezaTrabalho in the database
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
        NaturezaTrabalho testNaturezaTrabalho = naturezaTrabalhoList.get(naturezaTrabalhoList.size() - 1);
        assertThat(testNaturezaTrabalho.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testNaturezaTrabalho.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testNaturezaTrabalho.getIsActivo()).isEqualTo(UPDATED_IS_ACTIVO);
    }

    @Test
    @Transactional
    void putNonExistingNaturezaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = naturezaTrabalhoRepository.findAll().size();
        naturezaTrabalho.setId(count.incrementAndGet());

        // Create the NaturezaTrabalho
        NaturezaTrabalhoDTO naturezaTrabalhoDTO = naturezaTrabalhoMapper.toDto(naturezaTrabalho);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaturezaTrabalhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, naturezaTrabalhoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(naturezaTrabalhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NaturezaTrabalho in the database
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNaturezaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = naturezaTrabalhoRepository.findAll().size();
        naturezaTrabalho.setId(count.incrementAndGet());

        // Create the NaturezaTrabalho
        NaturezaTrabalhoDTO naturezaTrabalhoDTO = naturezaTrabalhoMapper.toDto(naturezaTrabalho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaturezaTrabalhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(naturezaTrabalhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NaturezaTrabalho in the database
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNaturezaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = naturezaTrabalhoRepository.findAll().size();
        naturezaTrabalho.setId(count.incrementAndGet());

        // Create the NaturezaTrabalho
        NaturezaTrabalhoDTO naturezaTrabalhoDTO = naturezaTrabalhoMapper.toDto(naturezaTrabalho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaturezaTrabalhoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(naturezaTrabalhoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NaturezaTrabalho in the database
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNaturezaTrabalhoWithPatch() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        int databaseSizeBeforeUpdate = naturezaTrabalhoRepository.findAll().size();

        // Update the naturezaTrabalho using partial update
        NaturezaTrabalho partialUpdatedNaturezaTrabalho = new NaturezaTrabalho();
        partialUpdatedNaturezaTrabalho.setId(naturezaTrabalho.getId());

        partialUpdatedNaturezaTrabalho.isActivo(UPDATED_IS_ACTIVO);

        restNaturezaTrabalhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaturezaTrabalho.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNaturezaTrabalho))
            )
            .andExpect(status().isOk());

        // Validate the NaturezaTrabalho in the database
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
        NaturezaTrabalho testNaturezaTrabalho = naturezaTrabalhoList.get(naturezaTrabalhoList.size() - 1);
        assertThat(testNaturezaTrabalho.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testNaturezaTrabalho.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testNaturezaTrabalho.getIsActivo()).isEqualTo(UPDATED_IS_ACTIVO);
    }

    @Test
    @Transactional
    void fullUpdateNaturezaTrabalhoWithPatch() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        int databaseSizeBeforeUpdate = naturezaTrabalhoRepository.findAll().size();

        // Update the naturezaTrabalho using partial update
        NaturezaTrabalho partialUpdatedNaturezaTrabalho = new NaturezaTrabalho();
        partialUpdatedNaturezaTrabalho.setId(naturezaTrabalho.getId());

        partialUpdatedNaturezaTrabalho.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).isActivo(UPDATED_IS_ACTIVO);

        restNaturezaTrabalhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaturezaTrabalho.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNaturezaTrabalho))
            )
            .andExpect(status().isOk());

        // Validate the NaturezaTrabalho in the database
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
        NaturezaTrabalho testNaturezaTrabalho = naturezaTrabalhoList.get(naturezaTrabalhoList.size() - 1);
        assertThat(testNaturezaTrabalho.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testNaturezaTrabalho.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testNaturezaTrabalho.getIsActivo()).isEqualTo(UPDATED_IS_ACTIVO);
    }

    @Test
    @Transactional
    void patchNonExistingNaturezaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = naturezaTrabalhoRepository.findAll().size();
        naturezaTrabalho.setId(count.incrementAndGet());

        // Create the NaturezaTrabalho
        NaturezaTrabalhoDTO naturezaTrabalhoDTO = naturezaTrabalhoMapper.toDto(naturezaTrabalho);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaturezaTrabalhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, naturezaTrabalhoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(naturezaTrabalhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NaturezaTrabalho in the database
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNaturezaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = naturezaTrabalhoRepository.findAll().size();
        naturezaTrabalho.setId(count.incrementAndGet());

        // Create the NaturezaTrabalho
        NaturezaTrabalhoDTO naturezaTrabalhoDTO = naturezaTrabalhoMapper.toDto(naturezaTrabalho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaturezaTrabalhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(naturezaTrabalhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NaturezaTrabalho in the database
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNaturezaTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = naturezaTrabalhoRepository.findAll().size();
        naturezaTrabalho.setId(count.incrementAndGet());

        // Create the NaturezaTrabalho
        NaturezaTrabalhoDTO naturezaTrabalhoDTO = naturezaTrabalhoMapper.toDto(naturezaTrabalho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaturezaTrabalhoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(naturezaTrabalhoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NaturezaTrabalho in the database
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNaturezaTrabalho() throws Exception {
        // Initialize the database
        naturezaTrabalhoRepository.saveAndFlush(naturezaTrabalho);

        int databaseSizeBeforeDelete = naturezaTrabalhoRepository.findAll().size();

        // Delete the naturezaTrabalho
        restNaturezaTrabalhoMockMvc
            .perform(delete(ENTITY_API_URL_ID, naturezaTrabalho.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NaturezaTrabalho> naturezaTrabalhoList = naturezaTrabalhoRepository.findAll();
        assertThat(naturezaTrabalhoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
