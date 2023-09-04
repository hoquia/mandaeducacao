package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Lookup;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.repository.LookupRepository;
import com.ravunana.longonkelo.service.criteria.LookupCriteria;
import com.ravunana.longonkelo.service.dto.LookupDTO;
import com.ravunana.longonkelo.service.mapper.LookupMapper;
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
 * Integration tests for the {@link LookupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LookupResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SISTEMA = false;
    private static final Boolean UPDATED_IS_SISTEMA = true;

    private static final Boolean DEFAULT_IS_MODIFICAVEL = false;
    private static final Boolean UPDATED_IS_MODIFICAVEL = true;

    private static final String ENTITY_API_URL = "/api/lookups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LookupRepository lookupRepository;

    @Autowired
    private LookupMapper lookupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLookupMockMvc;

    private Lookup lookup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lookup createEntity(EntityManager em) {
        Lookup lookup = new Lookup()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .isSistema(DEFAULT_IS_SISTEMA)
            .isModificavel(DEFAULT_IS_MODIFICAVEL);
        return lookup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lookup createUpdatedEntity(EntityManager em) {
        Lookup lookup = new Lookup()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .isSistema(UPDATED_IS_SISTEMA)
            .isModificavel(UPDATED_IS_MODIFICAVEL);
        return lookup;
    }

    @BeforeEach
    public void initTest() {
        lookup = createEntity(em);
    }

    @Test
    @Transactional
    void createLookup() throws Exception {
        int databaseSizeBeforeCreate = lookupRepository.findAll().size();
        // Create the Lookup
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);
        restLookupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isCreated());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeCreate + 1);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testLookup.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLookup.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testLookup.getIsSistema()).isEqualTo(DEFAULT_IS_SISTEMA);
        assertThat(testLookup.getIsModificavel()).isEqualTo(DEFAULT_IS_MODIFICAVEL);
    }

    @Test
    @Transactional
    void createLookupWithExistingId() throws Exception {
        // Create the Lookup with an existing ID
        lookup.setId(1L);
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        int databaseSizeBeforeCreate = lookupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLookupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupRepository.findAll().size();
        // set the field null
        lookup.setCodigo(null);

        // Create the Lookup, which fails.
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        restLookupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isBadRequest());

        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupRepository.findAll().size();
        // set the field null
        lookup.setNome(null);

        // Create the Lookup, which fails.
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        restLookupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isBadRequest());

        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLookups() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList
        restLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].isSistema").value(hasItem(DEFAULT_IS_SISTEMA.booleanValue())))
            .andExpect(jsonPath("$.[*].isModificavel").value(hasItem(DEFAULT_IS_MODIFICAVEL.booleanValue())));
    }

    @Test
    @Transactional
    void getLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get the lookup
        restLookupMockMvc
            .perform(get(ENTITY_API_URL_ID, lookup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lookup.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.isSistema").value(DEFAULT_IS_SISTEMA.booleanValue()))
            .andExpect(jsonPath("$.isModificavel").value(DEFAULT_IS_MODIFICAVEL.booleanValue()));
    }

    @Test
    @Transactional
    void getLookupsByIdFiltering() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        Long id = lookup.getId();

        defaultLookupShouldBeFound("id.equals=" + id);
        defaultLookupShouldNotBeFound("id.notEquals=" + id);

        defaultLookupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLookupShouldNotBeFound("id.greaterThan=" + id);

        defaultLookupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLookupShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLookupsByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where codigo equals to DEFAULT_CODIGO
        defaultLookupShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the lookupList where codigo equals to UPDATED_CODIGO
        defaultLookupShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllLookupsByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultLookupShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the lookupList where codigo equals to UPDATED_CODIGO
        defaultLookupShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllLookupsByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where codigo is not null
        defaultLookupShouldBeFound("codigo.specified=true");

        // Get all the lookupList where codigo is null
        defaultLookupShouldNotBeFound("codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllLookupsByCodigoContainsSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where codigo contains DEFAULT_CODIGO
        defaultLookupShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the lookupList where codigo contains UPDATED_CODIGO
        defaultLookupShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllLookupsByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where codigo does not contain DEFAULT_CODIGO
        defaultLookupShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the lookupList where codigo does not contain UPDATED_CODIGO
        defaultLookupShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllLookupsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where nome equals to DEFAULT_NOME
        defaultLookupShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the lookupList where nome equals to UPDATED_NOME
        defaultLookupShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllLookupsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultLookupShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the lookupList where nome equals to UPDATED_NOME
        defaultLookupShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllLookupsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where nome is not null
        defaultLookupShouldBeFound("nome.specified=true");

        // Get all the lookupList where nome is null
        defaultLookupShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllLookupsByNomeContainsSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where nome contains DEFAULT_NOME
        defaultLookupShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the lookupList where nome contains UPDATED_NOME
        defaultLookupShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllLookupsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where nome does not contain DEFAULT_NOME
        defaultLookupShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the lookupList where nome does not contain UPDATED_NOME
        defaultLookupShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllLookupsByIsSistemaIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where isSistema equals to DEFAULT_IS_SISTEMA
        defaultLookupShouldBeFound("isSistema.equals=" + DEFAULT_IS_SISTEMA);

        // Get all the lookupList where isSistema equals to UPDATED_IS_SISTEMA
        defaultLookupShouldNotBeFound("isSistema.equals=" + UPDATED_IS_SISTEMA);
    }

    @Test
    @Transactional
    void getAllLookupsByIsSistemaIsInShouldWork() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where isSistema in DEFAULT_IS_SISTEMA or UPDATED_IS_SISTEMA
        defaultLookupShouldBeFound("isSistema.in=" + DEFAULT_IS_SISTEMA + "," + UPDATED_IS_SISTEMA);

        // Get all the lookupList where isSistema equals to UPDATED_IS_SISTEMA
        defaultLookupShouldNotBeFound("isSistema.in=" + UPDATED_IS_SISTEMA);
    }

    @Test
    @Transactional
    void getAllLookupsByIsSistemaIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where isSistema is not null
        defaultLookupShouldBeFound("isSistema.specified=true");

        // Get all the lookupList where isSistema is null
        defaultLookupShouldNotBeFound("isSistema.specified=false");
    }

    @Test
    @Transactional
    void getAllLookupsByIsModificavelIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where isModificavel equals to DEFAULT_IS_MODIFICAVEL
        defaultLookupShouldBeFound("isModificavel.equals=" + DEFAULT_IS_MODIFICAVEL);

        // Get all the lookupList where isModificavel equals to UPDATED_IS_MODIFICAVEL
        defaultLookupShouldNotBeFound("isModificavel.equals=" + UPDATED_IS_MODIFICAVEL);
    }

    @Test
    @Transactional
    void getAllLookupsByIsModificavelIsInShouldWork() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where isModificavel in DEFAULT_IS_MODIFICAVEL or UPDATED_IS_MODIFICAVEL
        defaultLookupShouldBeFound("isModificavel.in=" + DEFAULT_IS_MODIFICAVEL + "," + UPDATED_IS_MODIFICAVEL);

        // Get all the lookupList where isModificavel equals to UPDATED_IS_MODIFICAVEL
        defaultLookupShouldNotBeFound("isModificavel.in=" + UPDATED_IS_MODIFICAVEL);
    }

    @Test
    @Transactional
    void getAllLookupsByIsModificavelIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where isModificavel is not null
        defaultLookupShouldBeFound("isModificavel.specified=true");

        // Get all the lookupList where isModificavel is null
        defaultLookupShouldNotBeFound("isModificavel.specified=false");
    }

    @Test
    @Transactional
    void getAllLookupsByLookupItemsIsEqualToSomething() throws Exception {
        LookupItem lookupItems;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupRepository.saveAndFlush(lookup);
            lookupItems = LookupItemResourceIT.createEntity(em);
        } else {
            lookupItems = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(lookupItems);
        em.flush();
        lookup.addLookupItems(lookupItems);
        lookupRepository.saveAndFlush(lookup);
        Long lookupItemsId = lookupItems.getId();

        // Get all the lookupList where lookupItems equals to lookupItemsId
        defaultLookupShouldBeFound("lookupItemsId.equals=" + lookupItemsId);

        // Get all the lookupList where lookupItems equals to (lookupItemsId + 1)
        defaultLookupShouldNotBeFound("lookupItemsId.equals=" + (lookupItemsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLookupShouldBeFound(String filter) throws Exception {
        restLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].isSistema").value(hasItem(DEFAULT_IS_SISTEMA.booleanValue())))
            .andExpect(jsonPath("$.[*].isModificavel").value(hasItem(DEFAULT_IS_MODIFICAVEL.booleanValue())));

        // Check, that the count call also returns 1
        restLookupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLookupShouldNotBeFound(String filter) throws Exception {
        restLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLookupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLookup() throws Exception {
        // Get the lookup
        restLookupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();

        // Update the lookup
        Lookup updatedLookup = lookupRepository.findById(lookup.getId()).get();
        // Disconnect from session so that the updates on updatedLookup are not directly saved in db
        em.detach(updatedLookup);
        updatedLookup
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .isSistema(UPDATED_IS_SISTEMA)
            .isModificavel(UPDATED_IS_MODIFICAVEL);
        LookupDTO lookupDTO = lookupMapper.toDto(updatedLookup);

        restLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lookupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lookupDTO))
            )
            .andExpect(status().isOk());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testLookup.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLookup.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testLookup.getIsSistema()).isEqualTo(UPDATED_IS_SISTEMA);
        assertThat(testLookup.getIsModificavel()).isEqualTo(UPDATED_IS_MODIFICAVEL);
    }

    @Test
    @Transactional
    void putNonExistingLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // Create the Lookup
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lookupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // Create the Lookup
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // Create the Lookup
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLookupWithPatch() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();

        // Update the lookup using partial update
        Lookup partialUpdatedLookup = new Lookup();
        partialUpdatedLookup.setId(lookup.getId());

        partialUpdatedLookup.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLookup))
            )
            .andExpect(status().isOk());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testLookup.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLookup.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testLookup.getIsSistema()).isEqualTo(DEFAULT_IS_SISTEMA);
        assertThat(testLookup.getIsModificavel()).isEqualTo(DEFAULT_IS_MODIFICAVEL);
    }

    @Test
    @Transactional
    void fullUpdateLookupWithPatch() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();

        // Update the lookup using partial update
        Lookup partialUpdatedLookup = new Lookup();
        partialUpdatedLookup.setId(lookup.getId());

        partialUpdatedLookup
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .isSistema(UPDATED_IS_SISTEMA)
            .isModificavel(UPDATED_IS_MODIFICAVEL);

        restLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLookup))
            )
            .andExpect(status().isOk());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testLookup.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLookup.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testLookup.getIsSistema()).isEqualTo(UPDATED_IS_SISTEMA);
        assertThat(testLookup.getIsModificavel()).isEqualTo(UPDATED_IS_MODIFICAVEL);
    }

    @Test
    @Transactional
    void patchNonExistingLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // Create the Lookup
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lookupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // Create the Lookup
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // Create the Lookup
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lookupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeDelete = lookupRepository.findAll().size();

        // Delete the lookup
        restLookupMockMvc
            .perform(delete(ENTITY_API_URL_ID, lookup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
