package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.CategoriaEmolumento;
import com.ravunana.longonkelo.domain.Emolumento;
import com.ravunana.longonkelo.domain.PlanoMulta;
import com.ravunana.longonkelo.domain.PrecoEmolumento;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.domain.enumeration.MetodoAplicacaoMulta;
import com.ravunana.longonkelo.domain.enumeration.MetodoAplicacaoMulta;
import com.ravunana.longonkelo.repository.PlanoMultaRepository;
import com.ravunana.longonkelo.service.PlanoMultaService;
import com.ravunana.longonkelo.service.criteria.PlanoMultaCriteria;
import com.ravunana.longonkelo.service.dto.PlanoMultaDTO;
import com.ravunana.longonkelo.service.mapper.PlanoMultaMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link PlanoMultaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlanoMultaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIA_APLICACAO_MULTA = 1;
    private static final Integer UPDATED_DIA_APLICACAO_MULTA = 2;
    private static final Integer SMALLER_DIA_APLICACAO_MULTA = 1 - 1;

    private static final MetodoAplicacaoMulta DEFAULT_METODO_APLICACAO_MULTA = MetodoAplicacaoMulta.DEPOIS_MES_EMOLUMENTO;
    private static final MetodoAplicacaoMulta UPDATED_METODO_APLICACAO_MULTA = MetodoAplicacaoMulta.EXACTO_MES_EMOLUMENTO;

    private static final BigDecimal DEFAULT_TAXA_MULTA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TAXA_MULTA = new BigDecimal(1);
    private static final BigDecimal SMALLER_TAXA_MULTA = new BigDecimal(0 - 1);

    private static final Boolean DEFAULT_IS_TAXA_MULTA_PERCENTUAL = false;
    private static final Boolean UPDATED_IS_TAXA_MULTA_PERCENTUAL = true;

    private static final Integer DEFAULT_DIA_APLICACAO_JURO = 1;
    private static final Integer UPDATED_DIA_APLICACAO_JURO = 2;
    private static final Integer SMALLER_DIA_APLICACAO_JURO = 1 - 1;

    private static final MetodoAplicacaoMulta DEFAULT_METODO_APLICACAO_JURO = MetodoAplicacaoMulta.DEPOIS_MES_EMOLUMENTO;
    private static final MetodoAplicacaoMulta UPDATED_METODO_APLICACAO_JURO = MetodoAplicacaoMulta.EXACTO_MES_EMOLUMENTO;

    private static final BigDecimal DEFAULT_TAXA_JURO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TAXA_JURO = new BigDecimal(1);
    private static final BigDecimal SMALLER_TAXA_JURO = new BigDecimal(0 - 1);

    private static final Boolean DEFAULT_IS_TAXA_JURO_PERCENTUAL = false;
    private static final Boolean UPDATED_IS_TAXA_JURO_PERCENTUAL = true;

    private static final Integer DEFAULT_AUMENTAR_JURO_EM_DIAS = 0;
    private static final Integer UPDATED_AUMENTAR_JURO_EM_DIAS = 1;
    private static final Integer SMALLER_AUMENTAR_JURO_EM_DIAS = 0 - 1;

    private static final Boolean DEFAULT_IS_ATIVO = false;
    private static final Boolean UPDATED_IS_ATIVO = true;

    private static final String ENTITY_API_URL = "/api/plano-multas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanoMultaRepository planoMultaRepository;

    @Mock
    private PlanoMultaRepository planoMultaRepositoryMock;

    @Autowired
    private PlanoMultaMapper planoMultaMapper;

    @Mock
    private PlanoMultaService planoMultaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoMultaMockMvc;

    private PlanoMulta planoMulta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoMulta createEntity(EntityManager em) {
        PlanoMulta planoMulta = new PlanoMulta()
            .descricao(DEFAULT_DESCRICAO)
            .diaAplicacaoMulta(DEFAULT_DIA_APLICACAO_MULTA)
            .metodoAplicacaoMulta(DEFAULT_METODO_APLICACAO_MULTA)
            .taxaMulta(DEFAULT_TAXA_MULTA)
            .isTaxaMultaPercentual(DEFAULT_IS_TAXA_MULTA_PERCENTUAL)
            .diaAplicacaoJuro(DEFAULT_DIA_APLICACAO_JURO)
            .metodoAplicacaoJuro(DEFAULT_METODO_APLICACAO_JURO)
            .taxaJuro(DEFAULT_TAXA_JURO)
            .isTaxaJuroPercentual(DEFAULT_IS_TAXA_JURO_PERCENTUAL)
            .aumentarJuroEmDias(DEFAULT_AUMENTAR_JURO_EM_DIAS)
            .isAtivo(DEFAULT_IS_ATIVO);
        return planoMulta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoMulta createUpdatedEntity(EntityManager em) {
        PlanoMulta planoMulta = new PlanoMulta()
            .descricao(UPDATED_DESCRICAO)
            .diaAplicacaoMulta(UPDATED_DIA_APLICACAO_MULTA)
            .metodoAplicacaoMulta(UPDATED_METODO_APLICACAO_MULTA)
            .taxaMulta(UPDATED_TAXA_MULTA)
            .isTaxaMultaPercentual(UPDATED_IS_TAXA_MULTA_PERCENTUAL)
            .diaAplicacaoJuro(UPDATED_DIA_APLICACAO_JURO)
            .metodoAplicacaoJuro(UPDATED_METODO_APLICACAO_JURO)
            .taxaJuro(UPDATED_TAXA_JURO)
            .isTaxaJuroPercentual(UPDATED_IS_TAXA_JURO_PERCENTUAL)
            .aumentarJuroEmDias(UPDATED_AUMENTAR_JURO_EM_DIAS)
            .isAtivo(UPDATED_IS_ATIVO);
        return planoMulta;
    }

    @BeforeEach
    public void initTest() {
        planoMulta = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanoMulta() throws Exception {
        int databaseSizeBeforeCreate = planoMultaRepository.findAll().size();
        // Create the PlanoMulta
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);
        restPlanoMultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoMultaDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoMulta in the database
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoMulta testPlanoMulta = planoMultaList.get(planoMultaList.size() - 1);
        assertThat(testPlanoMulta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoMulta.getDiaAplicacaoMulta()).isEqualTo(DEFAULT_DIA_APLICACAO_MULTA);
        assertThat(testPlanoMulta.getMetodoAplicacaoMulta()).isEqualTo(DEFAULT_METODO_APLICACAO_MULTA);
        assertThat(testPlanoMulta.getTaxaMulta()).isEqualByComparingTo(DEFAULT_TAXA_MULTA);
        assertThat(testPlanoMulta.getIsTaxaMultaPercentual()).isEqualTo(DEFAULT_IS_TAXA_MULTA_PERCENTUAL);
        assertThat(testPlanoMulta.getDiaAplicacaoJuro()).isEqualTo(DEFAULT_DIA_APLICACAO_JURO);
        assertThat(testPlanoMulta.getMetodoAplicacaoJuro()).isEqualTo(DEFAULT_METODO_APLICACAO_JURO);
        assertThat(testPlanoMulta.getTaxaJuro()).isEqualByComparingTo(DEFAULT_TAXA_JURO);
        assertThat(testPlanoMulta.getIsTaxaJuroPercentual()).isEqualTo(DEFAULT_IS_TAXA_JURO_PERCENTUAL);
        assertThat(testPlanoMulta.getAumentarJuroEmDias()).isEqualTo(DEFAULT_AUMENTAR_JURO_EM_DIAS);
        assertThat(testPlanoMulta.getIsAtivo()).isEqualTo(DEFAULT_IS_ATIVO);
    }

    @Test
    @Transactional
    void createPlanoMultaWithExistingId() throws Exception {
        // Create the PlanoMulta with an existing ID
        planoMulta.setId(1L);
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);

        int databaseSizeBeforeCreate = planoMultaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoMultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoMultaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoMulta in the database
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoMultaRepository.findAll().size();
        // set the field null
        planoMulta.setDescricao(null);

        // Create the PlanoMulta, which fails.
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);

        restPlanoMultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoMultaDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDiaAplicacaoMultaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoMultaRepository.findAll().size();
        // set the field null
        planoMulta.setDiaAplicacaoMulta(null);

        // Create the PlanoMulta, which fails.
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);

        restPlanoMultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoMultaDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMetodoAplicacaoMultaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoMultaRepository.findAll().size();
        // set the field null
        planoMulta.setMetodoAplicacaoMulta(null);

        // Create the PlanoMulta, which fails.
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);

        restPlanoMultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoMultaDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxaMultaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoMultaRepository.findAll().size();
        // set the field null
        planoMulta.setTaxaMulta(null);

        // Create the PlanoMulta, which fails.
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);

        restPlanoMultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoMultaDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanoMultas() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList
        restPlanoMultaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoMulta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].diaAplicacaoMulta").value(hasItem(DEFAULT_DIA_APLICACAO_MULTA)))
            .andExpect(jsonPath("$.[*].metodoAplicacaoMulta").value(hasItem(DEFAULT_METODO_APLICACAO_MULTA.toString())))
            .andExpect(jsonPath("$.[*].taxaMulta").value(hasItem(sameNumber(DEFAULT_TAXA_MULTA))))
            .andExpect(jsonPath("$.[*].isTaxaMultaPercentual").value(hasItem(DEFAULT_IS_TAXA_MULTA_PERCENTUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].diaAplicacaoJuro").value(hasItem(DEFAULT_DIA_APLICACAO_JURO)))
            .andExpect(jsonPath("$.[*].metodoAplicacaoJuro").value(hasItem(DEFAULT_METODO_APLICACAO_JURO.toString())))
            .andExpect(jsonPath("$.[*].taxaJuro").value(hasItem(sameNumber(DEFAULT_TAXA_JURO))))
            .andExpect(jsonPath("$.[*].isTaxaJuroPercentual").value(hasItem(DEFAULT_IS_TAXA_JURO_PERCENTUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].aumentarJuroEmDias").value(hasItem(DEFAULT_AUMENTAR_JURO_EM_DIAS)))
            .andExpect(jsonPath("$.[*].isAtivo").value(hasItem(DEFAULT_IS_ATIVO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlanoMultasWithEagerRelationshipsIsEnabled() throws Exception {
        when(planoMultaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlanoMultaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(planoMultaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlanoMultasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(planoMultaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlanoMultaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(planoMultaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPlanoMulta() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get the planoMulta
        restPlanoMultaMockMvc
            .perform(get(ENTITY_API_URL_ID, planoMulta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoMulta.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.diaAplicacaoMulta").value(DEFAULT_DIA_APLICACAO_MULTA))
            .andExpect(jsonPath("$.metodoAplicacaoMulta").value(DEFAULT_METODO_APLICACAO_MULTA.toString()))
            .andExpect(jsonPath("$.taxaMulta").value(sameNumber(DEFAULT_TAXA_MULTA)))
            .andExpect(jsonPath("$.isTaxaMultaPercentual").value(DEFAULT_IS_TAXA_MULTA_PERCENTUAL.booleanValue()))
            .andExpect(jsonPath("$.diaAplicacaoJuro").value(DEFAULT_DIA_APLICACAO_JURO))
            .andExpect(jsonPath("$.metodoAplicacaoJuro").value(DEFAULT_METODO_APLICACAO_JURO.toString()))
            .andExpect(jsonPath("$.taxaJuro").value(sameNumber(DEFAULT_TAXA_JURO)))
            .andExpect(jsonPath("$.isTaxaJuroPercentual").value(DEFAULT_IS_TAXA_JURO_PERCENTUAL.booleanValue()))
            .andExpect(jsonPath("$.aumentarJuroEmDias").value(DEFAULT_AUMENTAR_JURO_EM_DIAS))
            .andExpect(jsonPath("$.isAtivo").value(DEFAULT_IS_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getPlanoMultasByIdFiltering() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        Long id = planoMulta.getId();

        defaultPlanoMultaShouldBeFound("id.equals=" + id);
        defaultPlanoMultaShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoMultaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoMultaShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoMultaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoMultaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where descricao equals to DEFAULT_DESCRICAO
        defaultPlanoMultaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the planoMultaList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoMultaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPlanoMultaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the planoMultaList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoMultaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where descricao is not null
        defaultPlanoMultaShouldBeFound("descricao.specified=true");

        // Get all the planoMultaList where descricao is null
        defaultPlanoMultaShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where descricao contains DEFAULT_DESCRICAO
        defaultPlanoMultaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the planoMultaList where descricao contains UPDATED_DESCRICAO
        defaultPlanoMultaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where descricao does not contain DEFAULT_DESCRICAO
        defaultPlanoMultaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the planoMultaList where descricao does not contain UPDATED_DESCRICAO
        defaultPlanoMultaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoMultaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoMulta equals to DEFAULT_DIA_APLICACAO_MULTA
        defaultPlanoMultaShouldBeFound("diaAplicacaoMulta.equals=" + DEFAULT_DIA_APLICACAO_MULTA);

        // Get all the planoMultaList where diaAplicacaoMulta equals to UPDATED_DIA_APLICACAO_MULTA
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoMulta.equals=" + UPDATED_DIA_APLICACAO_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoMultaIsInShouldWork() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoMulta in DEFAULT_DIA_APLICACAO_MULTA or UPDATED_DIA_APLICACAO_MULTA
        defaultPlanoMultaShouldBeFound("diaAplicacaoMulta.in=" + DEFAULT_DIA_APLICACAO_MULTA + "," + UPDATED_DIA_APLICACAO_MULTA);

        // Get all the planoMultaList where diaAplicacaoMulta equals to UPDATED_DIA_APLICACAO_MULTA
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoMulta.in=" + UPDATED_DIA_APLICACAO_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoMultaIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoMulta is not null
        defaultPlanoMultaShouldBeFound("diaAplicacaoMulta.specified=true");

        // Get all the planoMultaList where diaAplicacaoMulta is null
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoMulta.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoMultaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoMulta is greater than or equal to DEFAULT_DIA_APLICACAO_MULTA
        defaultPlanoMultaShouldBeFound("diaAplicacaoMulta.greaterThanOrEqual=" + DEFAULT_DIA_APLICACAO_MULTA);

        // Get all the planoMultaList where diaAplicacaoMulta is greater than or equal to (DEFAULT_DIA_APLICACAO_MULTA + 1)
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoMulta.greaterThanOrEqual=" + (DEFAULT_DIA_APLICACAO_MULTA + 1));
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoMultaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoMulta is less than or equal to DEFAULT_DIA_APLICACAO_MULTA
        defaultPlanoMultaShouldBeFound("diaAplicacaoMulta.lessThanOrEqual=" + DEFAULT_DIA_APLICACAO_MULTA);

        // Get all the planoMultaList where diaAplicacaoMulta is less than or equal to SMALLER_DIA_APLICACAO_MULTA
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoMulta.lessThanOrEqual=" + SMALLER_DIA_APLICACAO_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoMultaIsLessThanSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoMulta is less than DEFAULT_DIA_APLICACAO_MULTA
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoMulta.lessThan=" + DEFAULT_DIA_APLICACAO_MULTA);

        // Get all the planoMultaList where diaAplicacaoMulta is less than (DEFAULT_DIA_APLICACAO_MULTA + 1)
        defaultPlanoMultaShouldBeFound("diaAplicacaoMulta.lessThan=" + (DEFAULT_DIA_APLICACAO_MULTA + 1));
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoMultaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoMulta is greater than DEFAULT_DIA_APLICACAO_MULTA
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoMulta.greaterThan=" + DEFAULT_DIA_APLICACAO_MULTA);

        // Get all the planoMultaList where diaAplicacaoMulta is greater than SMALLER_DIA_APLICACAO_MULTA
        defaultPlanoMultaShouldBeFound("diaAplicacaoMulta.greaterThan=" + SMALLER_DIA_APLICACAO_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByMetodoAplicacaoMultaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where metodoAplicacaoMulta equals to DEFAULT_METODO_APLICACAO_MULTA
        defaultPlanoMultaShouldBeFound("metodoAplicacaoMulta.equals=" + DEFAULT_METODO_APLICACAO_MULTA);

        // Get all the planoMultaList where metodoAplicacaoMulta equals to UPDATED_METODO_APLICACAO_MULTA
        defaultPlanoMultaShouldNotBeFound("metodoAplicacaoMulta.equals=" + UPDATED_METODO_APLICACAO_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByMetodoAplicacaoMultaIsInShouldWork() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where metodoAplicacaoMulta in DEFAULT_METODO_APLICACAO_MULTA or UPDATED_METODO_APLICACAO_MULTA
        defaultPlanoMultaShouldBeFound("metodoAplicacaoMulta.in=" + DEFAULT_METODO_APLICACAO_MULTA + "," + UPDATED_METODO_APLICACAO_MULTA);

        // Get all the planoMultaList where metodoAplicacaoMulta equals to UPDATED_METODO_APLICACAO_MULTA
        defaultPlanoMultaShouldNotBeFound("metodoAplicacaoMulta.in=" + UPDATED_METODO_APLICACAO_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByMetodoAplicacaoMultaIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where metodoAplicacaoMulta is not null
        defaultPlanoMultaShouldBeFound("metodoAplicacaoMulta.specified=true");

        // Get all the planoMultaList where metodoAplicacaoMulta is null
        defaultPlanoMultaShouldNotBeFound("metodoAplicacaoMulta.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaMultaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaMulta equals to DEFAULT_TAXA_MULTA
        defaultPlanoMultaShouldBeFound("taxaMulta.equals=" + DEFAULT_TAXA_MULTA);

        // Get all the planoMultaList where taxaMulta equals to UPDATED_TAXA_MULTA
        defaultPlanoMultaShouldNotBeFound("taxaMulta.equals=" + UPDATED_TAXA_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaMultaIsInShouldWork() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaMulta in DEFAULT_TAXA_MULTA or UPDATED_TAXA_MULTA
        defaultPlanoMultaShouldBeFound("taxaMulta.in=" + DEFAULT_TAXA_MULTA + "," + UPDATED_TAXA_MULTA);

        // Get all the planoMultaList where taxaMulta equals to UPDATED_TAXA_MULTA
        defaultPlanoMultaShouldNotBeFound("taxaMulta.in=" + UPDATED_TAXA_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaMultaIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaMulta is not null
        defaultPlanoMultaShouldBeFound("taxaMulta.specified=true");

        // Get all the planoMultaList where taxaMulta is null
        defaultPlanoMultaShouldNotBeFound("taxaMulta.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaMultaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaMulta is greater than or equal to DEFAULT_TAXA_MULTA
        defaultPlanoMultaShouldBeFound("taxaMulta.greaterThanOrEqual=" + DEFAULT_TAXA_MULTA);

        // Get all the planoMultaList where taxaMulta is greater than or equal to UPDATED_TAXA_MULTA
        defaultPlanoMultaShouldNotBeFound("taxaMulta.greaterThanOrEqual=" + UPDATED_TAXA_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaMultaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaMulta is less than or equal to DEFAULT_TAXA_MULTA
        defaultPlanoMultaShouldBeFound("taxaMulta.lessThanOrEqual=" + DEFAULT_TAXA_MULTA);

        // Get all the planoMultaList where taxaMulta is less than or equal to SMALLER_TAXA_MULTA
        defaultPlanoMultaShouldNotBeFound("taxaMulta.lessThanOrEqual=" + SMALLER_TAXA_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaMultaIsLessThanSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaMulta is less than DEFAULT_TAXA_MULTA
        defaultPlanoMultaShouldNotBeFound("taxaMulta.lessThan=" + DEFAULT_TAXA_MULTA);

        // Get all the planoMultaList where taxaMulta is less than UPDATED_TAXA_MULTA
        defaultPlanoMultaShouldBeFound("taxaMulta.lessThan=" + UPDATED_TAXA_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaMultaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaMulta is greater than DEFAULT_TAXA_MULTA
        defaultPlanoMultaShouldNotBeFound("taxaMulta.greaterThan=" + DEFAULT_TAXA_MULTA);

        // Get all the planoMultaList where taxaMulta is greater than SMALLER_TAXA_MULTA
        defaultPlanoMultaShouldBeFound("taxaMulta.greaterThan=" + SMALLER_TAXA_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByIsTaxaMultaPercentualIsEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where isTaxaMultaPercentual equals to DEFAULT_IS_TAXA_MULTA_PERCENTUAL
        defaultPlanoMultaShouldBeFound("isTaxaMultaPercentual.equals=" + DEFAULT_IS_TAXA_MULTA_PERCENTUAL);

        // Get all the planoMultaList where isTaxaMultaPercentual equals to UPDATED_IS_TAXA_MULTA_PERCENTUAL
        defaultPlanoMultaShouldNotBeFound("isTaxaMultaPercentual.equals=" + UPDATED_IS_TAXA_MULTA_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByIsTaxaMultaPercentualIsInShouldWork() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where isTaxaMultaPercentual in DEFAULT_IS_TAXA_MULTA_PERCENTUAL or UPDATED_IS_TAXA_MULTA_PERCENTUAL
        defaultPlanoMultaShouldBeFound(
            "isTaxaMultaPercentual.in=" + DEFAULT_IS_TAXA_MULTA_PERCENTUAL + "," + UPDATED_IS_TAXA_MULTA_PERCENTUAL
        );

        // Get all the planoMultaList where isTaxaMultaPercentual equals to UPDATED_IS_TAXA_MULTA_PERCENTUAL
        defaultPlanoMultaShouldNotBeFound("isTaxaMultaPercentual.in=" + UPDATED_IS_TAXA_MULTA_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByIsTaxaMultaPercentualIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where isTaxaMultaPercentual is not null
        defaultPlanoMultaShouldBeFound("isTaxaMultaPercentual.specified=true");

        // Get all the planoMultaList where isTaxaMultaPercentual is null
        defaultPlanoMultaShouldNotBeFound("isTaxaMultaPercentual.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoJuroIsEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoJuro equals to DEFAULT_DIA_APLICACAO_JURO
        defaultPlanoMultaShouldBeFound("diaAplicacaoJuro.equals=" + DEFAULT_DIA_APLICACAO_JURO);

        // Get all the planoMultaList where diaAplicacaoJuro equals to UPDATED_DIA_APLICACAO_JURO
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoJuro.equals=" + UPDATED_DIA_APLICACAO_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoJuroIsInShouldWork() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoJuro in DEFAULT_DIA_APLICACAO_JURO or UPDATED_DIA_APLICACAO_JURO
        defaultPlanoMultaShouldBeFound("diaAplicacaoJuro.in=" + DEFAULT_DIA_APLICACAO_JURO + "," + UPDATED_DIA_APLICACAO_JURO);

        // Get all the planoMultaList where diaAplicacaoJuro equals to UPDATED_DIA_APLICACAO_JURO
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoJuro.in=" + UPDATED_DIA_APLICACAO_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoJuroIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoJuro is not null
        defaultPlanoMultaShouldBeFound("diaAplicacaoJuro.specified=true");

        // Get all the planoMultaList where diaAplicacaoJuro is null
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoJuro.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoJuroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoJuro is greater than or equal to DEFAULT_DIA_APLICACAO_JURO
        defaultPlanoMultaShouldBeFound("diaAplicacaoJuro.greaterThanOrEqual=" + DEFAULT_DIA_APLICACAO_JURO);

        // Get all the planoMultaList where diaAplicacaoJuro is greater than or equal to (DEFAULT_DIA_APLICACAO_JURO + 1)
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoJuro.greaterThanOrEqual=" + (DEFAULT_DIA_APLICACAO_JURO + 1));
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoJuroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoJuro is less than or equal to DEFAULT_DIA_APLICACAO_JURO
        defaultPlanoMultaShouldBeFound("diaAplicacaoJuro.lessThanOrEqual=" + DEFAULT_DIA_APLICACAO_JURO);

        // Get all the planoMultaList where diaAplicacaoJuro is less than or equal to SMALLER_DIA_APLICACAO_JURO
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoJuro.lessThanOrEqual=" + SMALLER_DIA_APLICACAO_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoJuroIsLessThanSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoJuro is less than DEFAULT_DIA_APLICACAO_JURO
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoJuro.lessThan=" + DEFAULT_DIA_APLICACAO_JURO);

        // Get all the planoMultaList where diaAplicacaoJuro is less than (DEFAULT_DIA_APLICACAO_JURO + 1)
        defaultPlanoMultaShouldBeFound("diaAplicacaoJuro.lessThan=" + (DEFAULT_DIA_APLICACAO_JURO + 1));
    }

    @Test
    @Transactional
    void getAllPlanoMultasByDiaAplicacaoJuroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where diaAplicacaoJuro is greater than DEFAULT_DIA_APLICACAO_JURO
        defaultPlanoMultaShouldNotBeFound("diaAplicacaoJuro.greaterThan=" + DEFAULT_DIA_APLICACAO_JURO);

        // Get all the planoMultaList where diaAplicacaoJuro is greater than SMALLER_DIA_APLICACAO_JURO
        defaultPlanoMultaShouldBeFound("diaAplicacaoJuro.greaterThan=" + SMALLER_DIA_APLICACAO_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByMetodoAplicacaoJuroIsEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where metodoAplicacaoJuro equals to DEFAULT_METODO_APLICACAO_JURO
        defaultPlanoMultaShouldBeFound("metodoAplicacaoJuro.equals=" + DEFAULT_METODO_APLICACAO_JURO);

        // Get all the planoMultaList where metodoAplicacaoJuro equals to UPDATED_METODO_APLICACAO_JURO
        defaultPlanoMultaShouldNotBeFound("metodoAplicacaoJuro.equals=" + UPDATED_METODO_APLICACAO_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByMetodoAplicacaoJuroIsInShouldWork() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where metodoAplicacaoJuro in DEFAULT_METODO_APLICACAO_JURO or UPDATED_METODO_APLICACAO_JURO
        defaultPlanoMultaShouldBeFound("metodoAplicacaoJuro.in=" + DEFAULT_METODO_APLICACAO_JURO + "," + UPDATED_METODO_APLICACAO_JURO);

        // Get all the planoMultaList where metodoAplicacaoJuro equals to UPDATED_METODO_APLICACAO_JURO
        defaultPlanoMultaShouldNotBeFound("metodoAplicacaoJuro.in=" + UPDATED_METODO_APLICACAO_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByMetodoAplicacaoJuroIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where metodoAplicacaoJuro is not null
        defaultPlanoMultaShouldBeFound("metodoAplicacaoJuro.specified=true");

        // Get all the planoMultaList where metodoAplicacaoJuro is null
        defaultPlanoMultaShouldNotBeFound("metodoAplicacaoJuro.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaJuroIsEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaJuro equals to DEFAULT_TAXA_JURO
        defaultPlanoMultaShouldBeFound("taxaJuro.equals=" + DEFAULT_TAXA_JURO);

        // Get all the planoMultaList where taxaJuro equals to UPDATED_TAXA_JURO
        defaultPlanoMultaShouldNotBeFound("taxaJuro.equals=" + UPDATED_TAXA_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaJuroIsInShouldWork() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaJuro in DEFAULT_TAXA_JURO or UPDATED_TAXA_JURO
        defaultPlanoMultaShouldBeFound("taxaJuro.in=" + DEFAULT_TAXA_JURO + "," + UPDATED_TAXA_JURO);

        // Get all the planoMultaList where taxaJuro equals to UPDATED_TAXA_JURO
        defaultPlanoMultaShouldNotBeFound("taxaJuro.in=" + UPDATED_TAXA_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaJuroIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaJuro is not null
        defaultPlanoMultaShouldBeFound("taxaJuro.specified=true");

        // Get all the planoMultaList where taxaJuro is null
        defaultPlanoMultaShouldNotBeFound("taxaJuro.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaJuroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaJuro is greater than or equal to DEFAULT_TAXA_JURO
        defaultPlanoMultaShouldBeFound("taxaJuro.greaterThanOrEqual=" + DEFAULT_TAXA_JURO);

        // Get all the planoMultaList where taxaJuro is greater than or equal to UPDATED_TAXA_JURO
        defaultPlanoMultaShouldNotBeFound("taxaJuro.greaterThanOrEqual=" + UPDATED_TAXA_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaJuroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaJuro is less than or equal to DEFAULT_TAXA_JURO
        defaultPlanoMultaShouldBeFound("taxaJuro.lessThanOrEqual=" + DEFAULT_TAXA_JURO);

        // Get all the planoMultaList where taxaJuro is less than or equal to SMALLER_TAXA_JURO
        defaultPlanoMultaShouldNotBeFound("taxaJuro.lessThanOrEqual=" + SMALLER_TAXA_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaJuroIsLessThanSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaJuro is less than DEFAULT_TAXA_JURO
        defaultPlanoMultaShouldNotBeFound("taxaJuro.lessThan=" + DEFAULT_TAXA_JURO);

        // Get all the planoMultaList where taxaJuro is less than UPDATED_TAXA_JURO
        defaultPlanoMultaShouldBeFound("taxaJuro.lessThan=" + UPDATED_TAXA_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByTaxaJuroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where taxaJuro is greater than DEFAULT_TAXA_JURO
        defaultPlanoMultaShouldNotBeFound("taxaJuro.greaterThan=" + DEFAULT_TAXA_JURO);

        // Get all the planoMultaList where taxaJuro is greater than SMALLER_TAXA_JURO
        defaultPlanoMultaShouldBeFound("taxaJuro.greaterThan=" + SMALLER_TAXA_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByIsTaxaJuroPercentualIsEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where isTaxaJuroPercentual equals to DEFAULT_IS_TAXA_JURO_PERCENTUAL
        defaultPlanoMultaShouldBeFound("isTaxaJuroPercentual.equals=" + DEFAULT_IS_TAXA_JURO_PERCENTUAL);

        // Get all the planoMultaList where isTaxaJuroPercentual equals to UPDATED_IS_TAXA_JURO_PERCENTUAL
        defaultPlanoMultaShouldNotBeFound("isTaxaJuroPercentual.equals=" + UPDATED_IS_TAXA_JURO_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByIsTaxaJuroPercentualIsInShouldWork() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where isTaxaJuroPercentual in DEFAULT_IS_TAXA_JURO_PERCENTUAL or UPDATED_IS_TAXA_JURO_PERCENTUAL
        defaultPlanoMultaShouldBeFound(
            "isTaxaJuroPercentual.in=" + DEFAULT_IS_TAXA_JURO_PERCENTUAL + "," + UPDATED_IS_TAXA_JURO_PERCENTUAL
        );

        // Get all the planoMultaList where isTaxaJuroPercentual equals to UPDATED_IS_TAXA_JURO_PERCENTUAL
        defaultPlanoMultaShouldNotBeFound("isTaxaJuroPercentual.in=" + UPDATED_IS_TAXA_JURO_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByIsTaxaJuroPercentualIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where isTaxaJuroPercentual is not null
        defaultPlanoMultaShouldBeFound("isTaxaJuroPercentual.specified=true");

        // Get all the planoMultaList where isTaxaJuroPercentual is null
        defaultPlanoMultaShouldNotBeFound("isTaxaJuroPercentual.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoMultasByAumentarJuroEmDiasIsEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where aumentarJuroEmDias equals to DEFAULT_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldBeFound("aumentarJuroEmDias.equals=" + DEFAULT_AUMENTAR_JURO_EM_DIAS);

        // Get all the planoMultaList where aumentarJuroEmDias equals to UPDATED_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldNotBeFound("aumentarJuroEmDias.equals=" + UPDATED_AUMENTAR_JURO_EM_DIAS);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByAumentarJuroEmDiasIsInShouldWork() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where aumentarJuroEmDias in DEFAULT_AUMENTAR_JURO_EM_DIAS or UPDATED_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldBeFound("aumentarJuroEmDias.in=" + DEFAULT_AUMENTAR_JURO_EM_DIAS + "," + UPDATED_AUMENTAR_JURO_EM_DIAS);

        // Get all the planoMultaList where aumentarJuroEmDias equals to UPDATED_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldNotBeFound("aumentarJuroEmDias.in=" + UPDATED_AUMENTAR_JURO_EM_DIAS);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByAumentarJuroEmDiasIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where aumentarJuroEmDias is not null
        defaultPlanoMultaShouldBeFound("aumentarJuroEmDias.specified=true");

        // Get all the planoMultaList where aumentarJuroEmDias is null
        defaultPlanoMultaShouldNotBeFound("aumentarJuroEmDias.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoMultasByAumentarJuroEmDiasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where aumentarJuroEmDias is greater than or equal to DEFAULT_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldBeFound("aumentarJuroEmDias.greaterThanOrEqual=" + DEFAULT_AUMENTAR_JURO_EM_DIAS);

        // Get all the planoMultaList where aumentarJuroEmDias is greater than or equal to UPDATED_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldNotBeFound("aumentarJuroEmDias.greaterThanOrEqual=" + UPDATED_AUMENTAR_JURO_EM_DIAS);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByAumentarJuroEmDiasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where aumentarJuroEmDias is less than or equal to DEFAULT_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldBeFound("aumentarJuroEmDias.lessThanOrEqual=" + DEFAULT_AUMENTAR_JURO_EM_DIAS);

        // Get all the planoMultaList where aumentarJuroEmDias is less than or equal to SMALLER_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldNotBeFound("aumentarJuroEmDias.lessThanOrEqual=" + SMALLER_AUMENTAR_JURO_EM_DIAS);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByAumentarJuroEmDiasIsLessThanSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where aumentarJuroEmDias is less than DEFAULT_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldNotBeFound("aumentarJuroEmDias.lessThan=" + DEFAULT_AUMENTAR_JURO_EM_DIAS);

        // Get all the planoMultaList where aumentarJuroEmDias is less than UPDATED_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldBeFound("aumentarJuroEmDias.lessThan=" + UPDATED_AUMENTAR_JURO_EM_DIAS);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByAumentarJuroEmDiasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where aumentarJuroEmDias is greater than DEFAULT_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldNotBeFound("aumentarJuroEmDias.greaterThan=" + DEFAULT_AUMENTAR_JURO_EM_DIAS);

        // Get all the planoMultaList where aumentarJuroEmDias is greater than SMALLER_AUMENTAR_JURO_EM_DIAS
        defaultPlanoMultaShouldBeFound("aumentarJuroEmDias.greaterThan=" + SMALLER_AUMENTAR_JURO_EM_DIAS);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByIsAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where isAtivo equals to DEFAULT_IS_ATIVO
        defaultPlanoMultaShouldBeFound("isAtivo.equals=" + DEFAULT_IS_ATIVO);

        // Get all the planoMultaList where isAtivo equals to UPDATED_IS_ATIVO
        defaultPlanoMultaShouldNotBeFound("isAtivo.equals=" + UPDATED_IS_ATIVO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByIsAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where isAtivo in DEFAULT_IS_ATIVO or UPDATED_IS_ATIVO
        defaultPlanoMultaShouldBeFound("isAtivo.in=" + DEFAULT_IS_ATIVO + "," + UPDATED_IS_ATIVO);

        // Get all the planoMultaList where isAtivo equals to UPDATED_IS_ATIVO
        defaultPlanoMultaShouldNotBeFound("isAtivo.in=" + UPDATED_IS_ATIVO);
    }

    @Test
    @Transactional
    void getAllPlanoMultasByIsAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        // Get all the planoMultaList where isAtivo is not null
        defaultPlanoMultaShouldBeFound("isAtivo.specified=true");

        // Get all the planoMultaList where isAtivo is null
        defaultPlanoMultaShouldNotBeFound("isAtivo.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoMultasByCategoriaEmolumentoIsEqualToSomething() throws Exception {
        CategoriaEmolumento categoriaEmolumento;
        if (TestUtil.findAll(em, CategoriaEmolumento.class).isEmpty()) {
            planoMultaRepository.saveAndFlush(planoMulta);
            categoriaEmolumento = CategoriaEmolumentoResourceIT.createEntity(em);
        } else {
            categoriaEmolumento = TestUtil.findAll(em, CategoriaEmolumento.class).get(0);
        }
        em.persist(categoriaEmolumento);
        em.flush();
        planoMulta.addCategoriaEmolumento(categoriaEmolumento);
        planoMultaRepository.saveAndFlush(planoMulta);
        Long categoriaEmolumentoId = categoriaEmolumento.getId();

        // Get all the planoMultaList where categoriaEmolumento equals to categoriaEmolumentoId
        defaultPlanoMultaShouldBeFound("categoriaEmolumentoId.equals=" + categoriaEmolumentoId);

        // Get all the planoMultaList where categoriaEmolumento equals to (categoriaEmolumentoId + 1)
        defaultPlanoMultaShouldNotBeFound("categoriaEmolumentoId.equals=" + (categoriaEmolumentoId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoMultasByEmolumentoIsEqualToSomething() throws Exception {
        Emolumento emolumento;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            planoMultaRepository.saveAndFlush(planoMulta);
            emolumento = EmolumentoResourceIT.createEntity(em);
        } else {
            emolumento = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        em.persist(emolumento);
        em.flush();
        planoMulta.addEmolumento(emolumento);
        planoMultaRepository.saveAndFlush(planoMulta);
        Long emolumentoId = emolumento.getId();

        // Get all the planoMultaList where emolumento equals to emolumentoId
        defaultPlanoMultaShouldBeFound("emolumentoId.equals=" + emolumentoId);

        // Get all the planoMultaList where emolumento equals to (emolumentoId + 1)
        defaultPlanoMultaShouldNotBeFound("emolumentoId.equals=" + (emolumentoId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoMultasByPrecoEmolumentoIsEqualToSomething() throws Exception {
        PrecoEmolumento precoEmolumento;
        if (TestUtil.findAll(em, PrecoEmolumento.class).isEmpty()) {
            planoMultaRepository.saveAndFlush(planoMulta);
            precoEmolumento = PrecoEmolumentoResourceIT.createEntity(em);
        } else {
            precoEmolumento = TestUtil.findAll(em, PrecoEmolumento.class).get(0);
        }
        em.persist(precoEmolumento);
        em.flush();
        planoMulta.addPrecoEmolumento(precoEmolumento);
        planoMultaRepository.saveAndFlush(planoMulta);
        Long precoEmolumentoId = precoEmolumento.getId();

        // Get all the planoMultaList where precoEmolumento equals to precoEmolumentoId
        defaultPlanoMultaShouldBeFound("precoEmolumentoId.equals=" + precoEmolumentoId);

        // Get all the planoMultaList where precoEmolumento equals to (precoEmolumentoId + 1)
        defaultPlanoMultaShouldNotBeFound("precoEmolumentoId.equals=" + (precoEmolumentoId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoMultasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            planoMultaRepository.saveAndFlush(planoMulta);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        planoMulta.setUtilizador(utilizador);
        planoMultaRepository.saveAndFlush(planoMulta);
        Long utilizadorId = utilizador.getId();

        // Get all the planoMultaList where utilizador equals to utilizadorId
        defaultPlanoMultaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the planoMultaList where utilizador equals to (utilizadorId + 1)
        defaultPlanoMultaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoMultaShouldBeFound(String filter) throws Exception {
        restPlanoMultaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoMulta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].diaAplicacaoMulta").value(hasItem(DEFAULT_DIA_APLICACAO_MULTA)))
            .andExpect(jsonPath("$.[*].metodoAplicacaoMulta").value(hasItem(DEFAULT_METODO_APLICACAO_MULTA.toString())))
            .andExpect(jsonPath("$.[*].taxaMulta").value(hasItem(sameNumber(DEFAULT_TAXA_MULTA))))
            .andExpect(jsonPath("$.[*].isTaxaMultaPercentual").value(hasItem(DEFAULT_IS_TAXA_MULTA_PERCENTUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].diaAplicacaoJuro").value(hasItem(DEFAULT_DIA_APLICACAO_JURO)))
            .andExpect(jsonPath("$.[*].metodoAplicacaoJuro").value(hasItem(DEFAULT_METODO_APLICACAO_JURO.toString())))
            .andExpect(jsonPath("$.[*].taxaJuro").value(hasItem(sameNumber(DEFAULT_TAXA_JURO))))
            .andExpect(jsonPath("$.[*].isTaxaJuroPercentual").value(hasItem(DEFAULT_IS_TAXA_JURO_PERCENTUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].aumentarJuroEmDias").value(hasItem(DEFAULT_AUMENTAR_JURO_EM_DIAS)))
            .andExpect(jsonPath("$.[*].isAtivo").value(hasItem(DEFAULT_IS_ATIVO.booleanValue())));

        // Check, that the count call also returns 1
        restPlanoMultaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoMultaShouldNotBeFound(String filter) throws Exception {
        restPlanoMultaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoMultaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPlanoMulta() throws Exception {
        // Get the planoMulta
        restPlanoMultaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanoMulta() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        int databaseSizeBeforeUpdate = planoMultaRepository.findAll().size();

        // Update the planoMulta
        PlanoMulta updatedPlanoMulta = planoMultaRepository.findById(planoMulta.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoMulta are not directly saved in db
        em.detach(updatedPlanoMulta);
        updatedPlanoMulta
            .descricao(UPDATED_DESCRICAO)
            .diaAplicacaoMulta(UPDATED_DIA_APLICACAO_MULTA)
            .metodoAplicacaoMulta(UPDATED_METODO_APLICACAO_MULTA)
            .taxaMulta(UPDATED_TAXA_MULTA)
            .isTaxaMultaPercentual(UPDATED_IS_TAXA_MULTA_PERCENTUAL)
            .diaAplicacaoJuro(UPDATED_DIA_APLICACAO_JURO)
            .metodoAplicacaoJuro(UPDATED_METODO_APLICACAO_JURO)
            .taxaJuro(UPDATED_TAXA_JURO)
            .isTaxaJuroPercentual(UPDATED_IS_TAXA_JURO_PERCENTUAL)
            .aumentarJuroEmDias(UPDATED_AUMENTAR_JURO_EM_DIAS)
            .isAtivo(UPDATED_IS_ATIVO);
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(updatedPlanoMulta);

        restPlanoMultaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoMultaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoMultaDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlanoMulta in the database
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeUpdate);
        PlanoMulta testPlanoMulta = planoMultaList.get(planoMultaList.size() - 1);
        assertThat(testPlanoMulta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoMulta.getDiaAplicacaoMulta()).isEqualTo(UPDATED_DIA_APLICACAO_MULTA);
        assertThat(testPlanoMulta.getMetodoAplicacaoMulta()).isEqualTo(UPDATED_METODO_APLICACAO_MULTA);
        assertThat(testPlanoMulta.getTaxaMulta()).isEqualByComparingTo(UPDATED_TAXA_MULTA);
        assertThat(testPlanoMulta.getIsTaxaMultaPercentual()).isEqualTo(UPDATED_IS_TAXA_MULTA_PERCENTUAL);
        assertThat(testPlanoMulta.getDiaAplicacaoJuro()).isEqualTo(UPDATED_DIA_APLICACAO_JURO);
        assertThat(testPlanoMulta.getMetodoAplicacaoJuro()).isEqualTo(UPDATED_METODO_APLICACAO_JURO);
        assertThat(testPlanoMulta.getTaxaJuro()).isEqualByComparingTo(UPDATED_TAXA_JURO);
        assertThat(testPlanoMulta.getIsTaxaJuroPercentual()).isEqualTo(UPDATED_IS_TAXA_JURO_PERCENTUAL);
        assertThat(testPlanoMulta.getAumentarJuroEmDias()).isEqualTo(UPDATED_AUMENTAR_JURO_EM_DIAS);
        assertThat(testPlanoMulta.getIsAtivo()).isEqualTo(UPDATED_IS_ATIVO);
    }

    @Test
    @Transactional
    void putNonExistingPlanoMulta() throws Exception {
        int databaseSizeBeforeUpdate = planoMultaRepository.findAll().size();
        planoMulta.setId(count.incrementAndGet());

        // Create the PlanoMulta
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoMultaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoMultaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoMultaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoMulta in the database
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanoMulta() throws Exception {
        int databaseSizeBeforeUpdate = planoMultaRepository.findAll().size();
        planoMulta.setId(count.incrementAndGet());

        // Create the PlanoMulta
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoMultaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoMultaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoMulta in the database
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanoMulta() throws Exception {
        int databaseSizeBeforeUpdate = planoMultaRepository.findAll().size();
        planoMulta.setId(count.incrementAndGet());

        // Create the PlanoMulta
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoMultaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoMultaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoMulta in the database
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanoMultaWithPatch() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        int databaseSizeBeforeUpdate = planoMultaRepository.findAll().size();

        // Update the planoMulta using partial update
        PlanoMulta partialUpdatedPlanoMulta = new PlanoMulta();
        partialUpdatedPlanoMulta.setId(planoMulta.getId());

        partialUpdatedPlanoMulta.taxaJuro(UPDATED_TAXA_JURO).aumentarJuroEmDias(UPDATED_AUMENTAR_JURO_EM_DIAS).isAtivo(UPDATED_IS_ATIVO);

        restPlanoMultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoMulta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanoMulta))
            )
            .andExpect(status().isOk());

        // Validate the PlanoMulta in the database
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeUpdate);
        PlanoMulta testPlanoMulta = planoMultaList.get(planoMultaList.size() - 1);
        assertThat(testPlanoMulta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoMulta.getDiaAplicacaoMulta()).isEqualTo(DEFAULT_DIA_APLICACAO_MULTA);
        assertThat(testPlanoMulta.getMetodoAplicacaoMulta()).isEqualTo(DEFAULT_METODO_APLICACAO_MULTA);
        assertThat(testPlanoMulta.getTaxaMulta()).isEqualByComparingTo(DEFAULT_TAXA_MULTA);
        assertThat(testPlanoMulta.getIsTaxaMultaPercentual()).isEqualTo(DEFAULT_IS_TAXA_MULTA_PERCENTUAL);
        assertThat(testPlanoMulta.getDiaAplicacaoJuro()).isEqualTo(DEFAULT_DIA_APLICACAO_JURO);
        assertThat(testPlanoMulta.getMetodoAplicacaoJuro()).isEqualTo(DEFAULT_METODO_APLICACAO_JURO);
        assertThat(testPlanoMulta.getTaxaJuro()).isEqualByComparingTo(UPDATED_TAXA_JURO);
        assertThat(testPlanoMulta.getIsTaxaJuroPercentual()).isEqualTo(DEFAULT_IS_TAXA_JURO_PERCENTUAL);
        assertThat(testPlanoMulta.getAumentarJuroEmDias()).isEqualTo(UPDATED_AUMENTAR_JURO_EM_DIAS);
        assertThat(testPlanoMulta.getIsAtivo()).isEqualTo(UPDATED_IS_ATIVO);
    }

    @Test
    @Transactional
    void fullUpdatePlanoMultaWithPatch() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        int databaseSizeBeforeUpdate = planoMultaRepository.findAll().size();

        // Update the planoMulta using partial update
        PlanoMulta partialUpdatedPlanoMulta = new PlanoMulta();
        partialUpdatedPlanoMulta.setId(planoMulta.getId());

        partialUpdatedPlanoMulta
            .descricao(UPDATED_DESCRICAO)
            .diaAplicacaoMulta(UPDATED_DIA_APLICACAO_MULTA)
            .metodoAplicacaoMulta(UPDATED_METODO_APLICACAO_MULTA)
            .taxaMulta(UPDATED_TAXA_MULTA)
            .isTaxaMultaPercentual(UPDATED_IS_TAXA_MULTA_PERCENTUAL)
            .diaAplicacaoJuro(UPDATED_DIA_APLICACAO_JURO)
            .metodoAplicacaoJuro(UPDATED_METODO_APLICACAO_JURO)
            .taxaJuro(UPDATED_TAXA_JURO)
            .isTaxaJuroPercentual(UPDATED_IS_TAXA_JURO_PERCENTUAL)
            .aumentarJuroEmDias(UPDATED_AUMENTAR_JURO_EM_DIAS)
            .isAtivo(UPDATED_IS_ATIVO);

        restPlanoMultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoMulta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanoMulta))
            )
            .andExpect(status().isOk());

        // Validate the PlanoMulta in the database
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeUpdate);
        PlanoMulta testPlanoMulta = planoMultaList.get(planoMultaList.size() - 1);
        assertThat(testPlanoMulta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoMulta.getDiaAplicacaoMulta()).isEqualTo(UPDATED_DIA_APLICACAO_MULTA);
        assertThat(testPlanoMulta.getMetodoAplicacaoMulta()).isEqualTo(UPDATED_METODO_APLICACAO_MULTA);
        assertThat(testPlanoMulta.getTaxaMulta()).isEqualByComparingTo(UPDATED_TAXA_MULTA);
        assertThat(testPlanoMulta.getIsTaxaMultaPercentual()).isEqualTo(UPDATED_IS_TAXA_MULTA_PERCENTUAL);
        assertThat(testPlanoMulta.getDiaAplicacaoJuro()).isEqualTo(UPDATED_DIA_APLICACAO_JURO);
        assertThat(testPlanoMulta.getMetodoAplicacaoJuro()).isEqualTo(UPDATED_METODO_APLICACAO_JURO);
        assertThat(testPlanoMulta.getTaxaJuro()).isEqualByComparingTo(UPDATED_TAXA_JURO);
        assertThat(testPlanoMulta.getIsTaxaJuroPercentual()).isEqualTo(UPDATED_IS_TAXA_JURO_PERCENTUAL);
        assertThat(testPlanoMulta.getAumentarJuroEmDias()).isEqualTo(UPDATED_AUMENTAR_JURO_EM_DIAS);
        assertThat(testPlanoMulta.getIsAtivo()).isEqualTo(UPDATED_IS_ATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingPlanoMulta() throws Exception {
        int databaseSizeBeforeUpdate = planoMultaRepository.findAll().size();
        planoMulta.setId(count.incrementAndGet());

        // Create the PlanoMulta
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoMultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planoMultaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planoMultaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoMulta in the database
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanoMulta() throws Exception {
        int databaseSizeBeforeUpdate = planoMultaRepository.findAll().size();
        planoMulta.setId(count.incrementAndGet());

        // Create the PlanoMulta
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoMultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planoMultaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoMulta in the database
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanoMulta() throws Exception {
        int databaseSizeBeforeUpdate = planoMultaRepository.findAll().size();
        planoMulta.setId(count.incrementAndGet());

        // Create the PlanoMulta
        PlanoMultaDTO planoMultaDTO = planoMultaMapper.toDto(planoMulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoMultaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(planoMultaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoMulta in the database
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanoMulta() throws Exception {
        // Initialize the database
        planoMultaRepository.saveAndFlush(planoMulta);

        int databaseSizeBeforeDelete = planoMultaRepository.findAll().size();

        // Delete the planoMulta
        restPlanoMultaMockMvc
            .perform(delete(ENTITY_API_URL_ID, planoMulta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoMulta> planoMultaList = planoMultaRepository.findAll();
        assertThat(planoMultaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
