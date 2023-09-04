package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Lookup;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.repository.LookupItemRepository;
import com.ravunana.longonkelo.service.LookupItemService;
import com.ravunana.longonkelo.service.criteria.LookupItemCriteria;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import com.ravunana.longonkelo.service.mapper.LookupItemMapper;
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
 * Integration tests for the {@link LookupItemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LookupItemResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDEM = 0;
    private static final Integer UPDATED_ORDEM = 1;
    private static final Integer SMALLER_ORDEM = 0 - 1;

    private static final Boolean DEFAULT_IS_SISTEMA = false;
    private static final Boolean UPDATED_IS_SISTEMA = true;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lookup-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LookupItemRepository lookupItemRepository;

    @Mock
    private LookupItemRepository lookupItemRepositoryMock;

    @Autowired
    private LookupItemMapper lookupItemMapper;

    @Mock
    private LookupItemService lookupItemServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLookupItemMockMvc;

    private LookupItem lookupItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LookupItem createEntity(EntityManager em) {
        LookupItem lookupItem = new LookupItem()
            .codigo(DEFAULT_CODIGO)
            .ordem(DEFAULT_ORDEM)
            .isSistema(DEFAULT_IS_SISTEMA)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Lookup lookup;
        if (TestUtil.findAll(em, Lookup.class).isEmpty()) {
            lookup = LookupResourceIT.createEntity(em);
            em.persist(lookup);
            em.flush();
        } else {
            lookup = TestUtil.findAll(em, Lookup.class).get(0);
        }
        lookupItem.setLookup(lookup);
        return lookupItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LookupItem createUpdatedEntity(EntityManager em) {
        LookupItem lookupItem = new LookupItem()
            .codigo(UPDATED_CODIGO)
            .ordem(UPDATED_ORDEM)
            .isSistema(UPDATED_IS_SISTEMA)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        Lookup lookup;
        if (TestUtil.findAll(em, Lookup.class).isEmpty()) {
            lookup = LookupResourceIT.createUpdatedEntity(em);
            em.persist(lookup);
            em.flush();
        } else {
            lookup = TestUtil.findAll(em, Lookup.class).get(0);
        }
        lookupItem.setLookup(lookup);
        return lookupItem;
    }

    @BeforeEach
    public void initTest() {
        lookupItem = createEntity(em);
    }

    @Test
    @Transactional
    void createLookupItem() throws Exception {
        int databaseSizeBeforeCreate = lookupItemRepository.findAll().size();
        // Create the LookupItem
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);
        restLookupItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeCreate + 1);
        LookupItem testLookupItem = lookupItemList.get(lookupItemList.size() - 1);
        assertThat(testLookupItem.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testLookupItem.getOrdem()).isEqualTo(DEFAULT_ORDEM);
        assertThat(testLookupItem.getIsSistema()).isEqualTo(DEFAULT_IS_SISTEMA);
        assertThat(testLookupItem.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createLookupItemWithExistingId() throws Exception {
        // Create the LookupItem with an existing ID
        lookupItem.setId(1L);
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        int databaseSizeBeforeCreate = lookupItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLookupItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupItemRepository.findAll().size();
        // set the field null
        lookupItem.setDescricao(null);

        // Create the LookupItem, which fails.
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        restLookupItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isBadRequest());

        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLookupItems() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList
        restLookupItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookupItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].isSistema").value(hasItem(DEFAULT_IS_SISTEMA.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLookupItemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(lookupItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLookupItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(lookupItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLookupItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(lookupItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLookupItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(lookupItemRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLookupItem() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get the lookupItem
        restLookupItemMockMvc
            .perform(get(ENTITY_API_URL_ID, lookupItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lookupItem.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.isSistema").value(DEFAULT_IS_SISTEMA.booleanValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getLookupItemsByIdFiltering() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        Long id = lookupItem.getId();

        defaultLookupItemShouldBeFound("id.equals=" + id);
        defaultLookupItemShouldNotBeFound("id.notEquals=" + id);

        defaultLookupItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLookupItemShouldNotBeFound("id.greaterThan=" + id);

        defaultLookupItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLookupItemShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLookupItemsByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where codigo equals to DEFAULT_CODIGO
        defaultLookupItemShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the lookupItemList where codigo equals to UPDATED_CODIGO
        defaultLookupItemShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllLookupItemsByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultLookupItemShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the lookupItemList where codigo equals to UPDATED_CODIGO
        defaultLookupItemShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllLookupItemsByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where codigo is not null
        defaultLookupItemShouldBeFound("codigo.specified=true");

        // Get all the lookupItemList where codigo is null
        defaultLookupItemShouldNotBeFound("codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllLookupItemsByCodigoContainsSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where codigo contains DEFAULT_CODIGO
        defaultLookupItemShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the lookupItemList where codigo contains UPDATED_CODIGO
        defaultLookupItemShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllLookupItemsByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where codigo does not contain DEFAULT_CODIGO
        defaultLookupItemShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the lookupItemList where codigo does not contain UPDATED_CODIGO
        defaultLookupItemShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllLookupItemsByOrdemIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem equals to DEFAULT_ORDEM
        defaultLookupItemShouldBeFound("ordem.equals=" + DEFAULT_ORDEM);

        // Get all the lookupItemList where ordem equals to UPDATED_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.equals=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllLookupItemsByOrdemIsInShouldWork() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem in DEFAULT_ORDEM or UPDATED_ORDEM
        defaultLookupItemShouldBeFound("ordem.in=" + DEFAULT_ORDEM + "," + UPDATED_ORDEM);

        // Get all the lookupItemList where ordem equals to UPDATED_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.in=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllLookupItemsByOrdemIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem is not null
        defaultLookupItemShouldBeFound("ordem.specified=true");

        // Get all the lookupItemList where ordem is null
        defaultLookupItemShouldNotBeFound("ordem.specified=false");
    }

    @Test
    @Transactional
    void getAllLookupItemsByOrdemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem is greater than or equal to DEFAULT_ORDEM
        defaultLookupItemShouldBeFound("ordem.greaterThanOrEqual=" + DEFAULT_ORDEM);

        // Get all the lookupItemList where ordem is greater than or equal to UPDATED_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.greaterThanOrEqual=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllLookupItemsByOrdemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem is less than or equal to DEFAULT_ORDEM
        defaultLookupItemShouldBeFound("ordem.lessThanOrEqual=" + DEFAULT_ORDEM);

        // Get all the lookupItemList where ordem is less than or equal to SMALLER_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.lessThanOrEqual=" + SMALLER_ORDEM);
    }

    @Test
    @Transactional
    void getAllLookupItemsByOrdemIsLessThanSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem is less than DEFAULT_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.lessThan=" + DEFAULT_ORDEM);

        // Get all the lookupItemList where ordem is less than UPDATED_ORDEM
        defaultLookupItemShouldBeFound("ordem.lessThan=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllLookupItemsByOrdemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem is greater than DEFAULT_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.greaterThan=" + DEFAULT_ORDEM);

        // Get all the lookupItemList where ordem is greater than SMALLER_ORDEM
        defaultLookupItemShouldBeFound("ordem.greaterThan=" + SMALLER_ORDEM);
    }

    @Test
    @Transactional
    void getAllLookupItemsByIsSistemaIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where isSistema equals to DEFAULT_IS_SISTEMA
        defaultLookupItemShouldBeFound("isSistema.equals=" + DEFAULT_IS_SISTEMA);

        // Get all the lookupItemList where isSistema equals to UPDATED_IS_SISTEMA
        defaultLookupItemShouldNotBeFound("isSistema.equals=" + UPDATED_IS_SISTEMA);
    }

    @Test
    @Transactional
    void getAllLookupItemsByIsSistemaIsInShouldWork() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where isSistema in DEFAULT_IS_SISTEMA or UPDATED_IS_SISTEMA
        defaultLookupItemShouldBeFound("isSistema.in=" + DEFAULT_IS_SISTEMA + "," + UPDATED_IS_SISTEMA);

        // Get all the lookupItemList where isSistema equals to UPDATED_IS_SISTEMA
        defaultLookupItemShouldNotBeFound("isSistema.in=" + UPDATED_IS_SISTEMA);
    }

    @Test
    @Transactional
    void getAllLookupItemsByIsSistemaIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where isSistema is not null
        defaultLookupItemShouldBeFound("isSistema.specified=true");

        // Get all the lookupItemList where isSistema is null
        defaultLookupItemShouldNotBeFound("isSistema.specified=false");
    }

    @Test
    @Transactional
    void getAllLookupItemsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where descricao equals to DEFAULT_DESCRICAO
        defaultLookupItemShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the lookupItemList where descricao equals to UPDATED_DESCRICAO
        defaultLookupItemShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllLookupItemsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultLookupItemShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the lookupItemList where descricao equals to UPDATED_DESCRICAO
        defaultLookupItemShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllLookupItemsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where descricao is not null
        defaultLookupItemShouldBeFound("descricao.specified=true");

        // Get all the lookupItemList where descricao is null
        defaultLookupItemShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllLookupItemsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where descricao contains DEFAULT_DESCRICAO
        defaultLookupItemShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the lookupItemList where descricao contains UPDATED_DESCRICAO
        defaultLookupItemShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllLookupItemsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where descricao does not contain DEFAULT_DESCRICAO
        defaultLookupItemShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the lookupItemList where descricao does not contain UPDATED_DESCRICAO
        defaultLookupItemShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllLookupItemsByLookupIsEqualToSomething() throws Exception {
        Lookup lookup;
        if (TestUtil.findAll(em, Lookup.class).isEmpty()) {
            lookupItemRepository.saveAndFlush(lookupItem);
            lookup = LookupResourceIT.createEntity(em);
        } else {
            lookup = TestUtil.findAll(em, Lookup.class).get(0);
        }
        em.persist(lookup);
        em.flush();
        lookupItem.setLookup(lookup);
        lookupItemRepository.saveAndFlush(lookupItem);
        Long lookupId = lookup.getId();

        // Get all the lookupItemList where lookup equals to lookupId
        defaultLookupItemShouldBeFound("lookupId.equals=" + lookupId);

        // Get all the lookupItemList where lookup equals to (lookupId + 1)
        defaultLookupItemShouldNotBeFound("lookupId.equals=" + (lookupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLookupItemShouldBeFound(String filter) throws Exception {
        restLookupItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookupItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].isSistema").value(hasItem(DEFAULT_IS_SISTEMA.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restLookupItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLookupItemShouldNotBeFound(String filter) throws Exception {
        restLookupItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLookupItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLookupItem() throws Exception {
        // Get the lookupItem
        restLookupItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLookupItem() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();

        // Update the lookupItem
        LookupItem updatedLookupItem = lookupItemRepository.findById(lookupItem.getId()).get();
        // Disconnect from session so that the updates on updatedLookupItem are not directly saved in db
        em.detach(updatedLookupItem);
        updatedLookupItem.codigo(UPDATED_CODIGO).ordem(UPDATED_ORDEM).isSistema(UPDATED_IS_SISTEMA).descricao(UPDATED_DESCRICAO);
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(updatedLookupItem);

        restLookupItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lookupItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
        LookupItem testLookupItem = lookupItemList.get(lookupItemList.size() - 1);
        assertThat(testLookupItem.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testLookupItem.getOrdem()).isEqualTo(UPDATED_ORDEM);
        assertThat(testLookupItem.getIsSistema()).isEqualTo(UPDATED_IS_SISTEMA);
        assertThat(testLookupItem.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingLookupItem() throws Exception {
        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();
        lookupItem.setId(count.incrementAndGet());

        // Create the LookupItem
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lookupItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLookupItem() throws Exception {
        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();
        lookupItem.setId(count.incrementAndGet());

        // Create the LookupItem
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLookupItem() throws Exception {
        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();
        lookupItem.setId(count.incrementAndGet());

        // Create the LookupItem
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLookupItemWithPatch() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();

        // Update the lookupItem using partial update
        LookupItem partialUpdatedLookupItem = new LookupItem();
        partialUpdatedLookupItem.setId(lookupItem.getId());

        partialUpdatedLookupItem.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restLookupItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLookupItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLookupItem))
            )
            .andExpect(status().isOk());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
        LookupItem testLookupItem = lookupItemList.get(lookupItemList.size() - 1);
        assertThat(testLookupItem.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testLookupItem.getOrdem()).isEqualTo(DEFAULT_ORDEM);
        assertThat(testLookupItem.getIsSistema()).isEqualTo(DEFAULT_IS_SISTEMA);
        assertThat(testLookupItem.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateLookupItemWithPatch() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();

        // Update the lookupItem using partial update
        LookupItem partialUpdatedLookupItem = new LookupItem();
        partialUpdatedLookupItem.setId(lookupItem.getId());

        partialUpdatedLookupItem.codigo(UPDATED_CODIGO).ordem(UPDATED_ORDEM).isSistema(UPDATED_IS_SISTEMA).descricao(UPDATED_DESCRICAO);

        restLookupItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLookupItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLookupItem))
            )
            .andExpect(status().isOk());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
        LookupItem testLookupItem = lookupItemList.get(lookupItemList.size() - 1);
        assertThat(testLookupItem.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testLookupItem.getOrdem()).isEqualTo(UPDATED_ORDEM);
        assertThat(testLookupItem.getIsSistema()).isEqualTo(UPDATED_IS_SISTEMA);
        assertThat(testLookupItem.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingLookupItem() throws Exception {
        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();
        lookupItem.setId(count.incrementAndGet());

        // Create the LookupItem
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lookupItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLookupItem() throws Exception {
        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();
        lookupItem.setId(count.incrementAndGet());

        // Create the LookupItem
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLookupItem() throws Exception {
        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();
        lookupItem.setId(count.incrementAndGet());

        // Create the LookupItem
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lookupItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLookupItem() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        int databaseSizeBeforeDelete = lookupItemRepository.findAll().size();

        // Delete the lookupItem
        restLookupItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, lookupItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
