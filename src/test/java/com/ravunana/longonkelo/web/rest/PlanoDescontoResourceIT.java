package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.CategoriaEmolumento;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.PlanoDesconto;
import com.ravunana.longonkelo.repository.PlanoDescontoRepository;
import com.ravunana.longonkelo.service.PlanoDescontoService;
import com.ravunana.longonkelo.service.criteria.PlanoDescontoCriteria;
import com.ravunana.longonkelo.service.dto.PlanoDescontoDTO;
import com.ravunana.longonkelo.service.mapper.PlanoDescontoMapper;
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
 * Integration tests for the {@link PlanoDescontoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlanoDescontoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ISENTO_MULTA = false;
    private static final Boolean UPDATED_IS_ISENTO_MULTA = true;

    private static final Boolean DEFAULT_IS_ISENTO_JURO = false;
    private static final Boolean UPDATED_IS_ISENTO_JURO = true;

    private static final BigDecimal DEFAULT_DESCONTO = new BigDecimal(0);
    private static final BigDecimal UPDATED_DESCONTO = new BigDecimal(1);
    private static final BigDecimal SMALLER_DESCONTO = new BigDecimal(0 - 1);

    private static final String ENTITY_API_URL = "/api/plano-descontos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanoDescontoRepository planoDescontoRepository;

    @Mock
    private PlanoDescontoRepository planoDescontoRepositoryMock;

    @Autowired
    private PlanoDescontoMapper planoDescontoMapper;

    @Mock
    private PlanoDescontoService planoDescontoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoDescontoMockMvc;

    private PlanoDesconto planoDesconto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoDesconto createEntity(EntityManager em) {
        PlanoDesconto planoDesconto = new PlanoDesconto()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .isIsentoMulta(DEFAULT_IS_ISENTO_MULTA)
            .isIsentoJuro(DEFAULT_IS_ISENTO_JURO)
            .desconto(DEFAULT_DESCONTO);
        return planoDesconto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoDesconto createUpdatedEntity(EntityManager em) {
        PlanoDesconto planoDesconto = new PlanoDesconto()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .isIsentoMulta(UPDATED_IS_ISENTO_MULTA)
            .isIsentoJuro(UPDATED_IS_ISENTO_JURO)
            .desconto(UPDATED_DESCONTO);
        return planoDesconto;
    }

    @BeforeEach
    public void initTest() {
        planoDesconto = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanoDesconto() throws Exception {
        int databaseSizeBeforeCreate = planoDescontoRepository.findAll().size();
        // Create the PlanoDesconto
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(planoDesconto);
        restPlanoDescontoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PlanoDesconto in the database
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoDesconto testPlanoDesconto = planoDescontoList.get(planoDescontoList.size() - 1);
        assertThat(testPlanoDesconto.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testPlanoDesconto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPlanoDesconto.getIsIsentoMulta()).isEqualTo(DEFAULT_IS_ISENTO_MULTA);
        assertThat(testPlanoDesconto.getIsIsentoJuro()).isEqualTo(DEFAULT_IS_ISENTO_JURO);
        assertThat(testPlanoDesconto.getDesconto()).isEqualByComparingTo(DEFAULT_DESCONTO);
    }

    @Test
    @Transactional
    void createPlanoDescontoWithExistingId() throws Exception {
        // Create the PlanoDesconto with an existing ID
        planoDesconto.setId(1L);
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(planoDesconto);

        int databaseSizeBeforeCreate = planoDescontoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoDescontoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoDesconto in the database
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoDescontoRepository.findAll().size();
        // set the field null
        planoDesconto.setCodigo(null);

        // Create the PlanoDesconto, which fails.
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(planoDesconto);

        restPlanoDescontoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoDescontoRepository.findAll().size();
        // set the field null
        planoDesconto.setNome(null);

        // Create the PlanoDesconto, which fails.
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(planoDesconto);

        restPlanoDescontoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescontoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoDescontoRepository.findAll().size();
        // set the field null
        planoDesconto.setDesconto(null);

        // Create the PlanoDesconto, which fails.
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(planoDesconto);

        restPlanoDescontoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanoDescontos() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList
        restPlanoDescontoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoDesconto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].isIsentoMulta").value(hasItem(DEFAULT_IS_ISENTO_MULTA.booleanValue())))
            .andExpect(jsonPath("$.[*].isIsentoJuro").value(hasItem(DEFAULT_IS_ISENTO_JURO.booleanValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(sameNumber(DEFAULT_DESCONTO))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlanoDescontosWithEagerRelationshipsIsEnabled() throws Exception {
        when(planoDescontoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlanoDescontoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(planoDescontoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlanoDescontosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(planoDescontoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlanoDescontoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(planoDescontoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPlanoDesconto() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get the planoDesconto
        restPlanoDescontoMockMvc
            .perform(get(ENTITY_API_URL_ID, planoDesconto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoDesconto.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.isIsentoMulta").value(DEFAULT_IS_ISENTO_MULTA.booleanValue()))
            .andExpect(jsonPath("$.isIsentoJuro").value(DEFAULT_IS_ISENTO_JURO.booleanValue()))
            .andExpect(jsonPath("$.desconto").value(sameNumber(DEFAULT_DESCONTO)));
    }

    @Test
    @Transactional
    void getPlanoDescontosByIdFiltering() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        Long id = planoDesconto.getId();

        defaultPlanoDescontoShouldBeFound("id.equals=" + id);
        defaultPlanoDescontoShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoDescontoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoDescontoShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoDescontoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoDescontoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where codigo equals to DEFAULT_CODIGO
        defaultPlanoDescontoShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the planoDescontoList where codigo equals to UPDATED_CODIGO
        defaultPlanoDescontoShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultPlanoDescontoShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the planoDescontoList where codigo equals to UPDATED_CODIGO
        defaultPlanoDescontoShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where codigo is not null
        defaultPlanoDescontoShouldBeFound("codigo.specified=true");

        // Get all the planoDescontoList where codigo is null
        defaultPlanoDescontoShouldNotBeFound("codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByCodigoContainsSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where codigo contains DEFAULT_CODIGO
        defaultPlanoDescontoShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the planoDescontoList where codigo contains UPDATED_CODIGO
        defaultPlanoDescontoShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where codigo does not contain DEFAULT_CODIGO
        defaultPlanoDescontoShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the planoDescontoList where codigo does not contain UPDATED_CODIGO
        defaultPlanoDescontoShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where nome equals to DEFAULT_NOME
        defaultPlanoDescontoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the planoDescontoList where nome equals to UPDATED_NOME
        defaultPlanoDescontoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultPlanoDescontoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the planoDescontoList where nome equals to UPDATED_NOME
        defaultPlanoDescontoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where nome is not null
        defaultPlanoDescontoShouldBeFound("nome.specified=true");

        // Get all the planoDescontoList where nome is null
        defaultPlanoDescontoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByNomeContainsSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where nome contains DEFAULT_NOME
        defaultPlanoDescontoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the planoDescontoList where nome contains UPDATED_NOME
        defaultPlanoDescontoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where nome does not contain DEFAULT_NOME
        defaultPlanoDescontoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the planoDescontoList where nome does not contain UPDATED_NOME
        defaultPlanoDescontoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByIsIsentoMultaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where isIsentoMulta equals to DEFAULT_IS_ISENTO_MULTA
        defaultPlanoDescontoShouldBeFound("isIsentoMulta.equals=" + DEFAULT_IS_ISENTO_MULTA);

        // Get all the planoDescontoList where isIsentoMulta equals to UPDATED_IS_ISENTO_MULTA
        defaultPlanoDescontoShouldNotBeFound("isIsentoMulta.equals=" + UPDATED_IS_ISENTO_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByIsIsentoMultaIsInShouldWork() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where isIsentoMulta in DEFAULT_IS_ISENTO_MULTA or UPDATED_IS_ISENTO_MULTA
        defaultPlanoDescontoShouldBeFound("isIsentoMulta.in=" + DEFAULT_IS_ISENTO_MULTA + "," + UPDATED_IS_ISENTO_MULTA);

        // Get all the planoDescontoList where isIsentoMulta equals to UPDATED_IS_ISENTO_MULTA
        defaultPlanoDescontoShouldNotBeFound("isIsentoMulta.in=" + UPDATED_IS_ISENTO_MULTA);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByIsIsentoMultaIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where isIsentoMulta is not null
        defaultPlanoDescontoShouldBeFound("isIsentoMulta.specified=true");

        // Get all the planoDescontoList where isIsentoMulta is null
        defaultPlanoDescontoShouldNotBeFound("isIsentoMulta.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByIsIsentoJuroIsEqualToSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where isIsentoJuro equals to DEFAULT_IS_ISENTO_JURO
        defaultPlanoDescontoShouldBeFound("isIsentoJuro.equals=" + DEFAULT_IS_ISENTO_JURO);

        // Get all the planoDescontoList where isIsentoJuro equals to UPDATED_IS_ISENTO_JURO
        defaultPlanoDescontoShouldNotBeFound("isIsentoJuro.equals=" + UPDATED_IS_ISENTO_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByIsIsentoJuroIsInShouldWork() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where isIsentoJuro in DEFAULT_IS_ISENTO_JURO or UPDATED_IS_ISENTO_JURO
        defaultPlanoDescontoShouldBeFound("isIsentoJuro.in=" + DEFAULT_IS_ISENTO_JURO + "," + UPDATED_IS_ISENTO_JURO);

        // Get all the planoDescontoList where isIsentoJuro equals to UPDATED_IS_ISENTO_JURO
        defaultPlanoDescontoShouldNotBeFound("isIsentoJuro.in=" + UPDATED_IS_ISENTO_JURO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByIsIsentoJuroIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where isIsentoJuro is not null
        defaultPlanoDescontoShouldBeFound("isIsentoJuro.specified=true");

        // Get all the planoDescontoList where isIsentoJuro is null
        defaultPlanoDescontoShouldNotBeFound("isIsentoJuro.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where desconto equals to DEFAULT_DESCONTO
        defaultPlanoDescontoShouldBeFound("desconto.equals=" + DEFAULT_DESCONTO);

        // Get all the planoDescontoList where desconto equals to UPDATED_DESCONTO
        defaultPlanoDescontoShouldNotBeFound("desconto.equals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where desconto in DEFAULT_DESCONTO or UPDATED_DESCONTO
        defaultPlanoDescontoShouldBeFound("desconto.in=" + DEFAULT_DESCONTO + "," + UPDATED_DESCONTO);

        // Get all the planoDescontoList where desconto equals to UPDATED_DESCONTO
        defaultPlanoDescontoShouldNotBeFound("desconto.in=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where desconto is not null
        defaultPlanoDescontoShouldBeFound("desconto.specified=true");

        // Get all the planoDescontoList where desconto is null
        defaultPlanoDescontoShouldNotBeFound("desconto.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where desconto is greater than or equal to DEFAULT_DESCONTO
        defaultPlanoDescontoShouldBeFound("desconto.greaterThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the planoDescontoList where desconto is greater than or equal to UPDATED_DESCONTO
        defaultPlanoDescontoShouldNotBeFound("desconto.greaterThanOrEqual=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where desconto is less than or equal to DEFAULT_DESCONTO
        defaultPlanoDescontoShouldBeFound("desconto.lessThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the planoDescontoList where desconto is less than or equal to SMALLER_DESCONTO
        defaultPlanoDescontoShouldNotBeFound("desconto.lessThanOrEqual=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where desconto is less than DEFAULT_DESCONTO
        defaultPlanoDescontoShouldNotBeFound("desconto.lessThan=" + DEFAULT_DESCONTO);

        // Get all the planoDescontoList where desconto is less than UPDATED_DESCONTO
        defaultPlanoDescontoShouldBeFound("desconto.lessThan=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        // Get all the planoDescontoList where desconto is greater than DEFAULT_DESCONTO
        defaultPlanoDescontoShouldNotBeFound("desconto.greaterThan=" + DEFAULT_DESCONTO);

        // Get all the planoDescontoList where desconto is greater than SMALLER_DESCONTO
        defaultPlanoDescontoShouldBeFound("desconto.greaterThan=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByCategoriasEmolumentoIsEqualToSomething() throws Exception {
        CategoriaEmolumento categoriasEmolumento;
        if (TestUtil.findAll(em, CategoriaEmolumento.class).isEmpty()) {
            planoDescontoRepository.saveAndFlush(planoDesconto);
            categoriasEmolumento = CategoriaEmolumentoResourceIT.createEntity(em);
        } else {
            categoriasEmolumento = TestUtil.findAll(em, CategoriaEmolumento.class).get(0);
        }
        em.persist(categoriasEmolumento);
        em.flush();
        planoDesconto.addCategoriasEmolumento(categoriasEmolumento);
        planoDescontoRepository.saveAndFlush(planoDesconto);
        Long categoriasEmolumentoId = categoriasEmolumento.getId();

        // Get all the planoDescontoList where categoriasEmolumento equals to categoriasEmolumentoId
        defaultPlanoDescontoShouldBeFound("categoriasEmolumentoId.equals=" + categoriasEmolumentoId);

        // Get all the planoDescontoList where categoriasEmolumento equals to (categoriasEmolumentoId + 1)
        defaultPlanoDescontoShouldNotBeFound("categoriasEmolumentoId.equals=" + (categoriasEmolumentoId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoDescontosByMatriculasIsEqualToSomething() throws Exception {
        Matricula matriculas;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            planoDescontoRepository.saveAndFlush(planoDesconto);
            matriculas = MatriculaResourceIT.createEntity(em);
        } else {
            matriculas = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(matriculas);
        em.flush();
        planoDesconto.addMatriculas(matriculas);
        planoDescontoRepository.saveAndFlush(planoDesconto);
        Long matriculasId = matriculas.getId();

        // Get all the planoDescontoList where matriculas equals to matriculasId
        defaultPlanoDescontoShouldBeFound("matriculasId.equals=" + matriculasId);

        // Get all the planoDescontoList where matriculas equals to (matriculasId + 1)
        defaultPlanoDescontoShouldNotBeFound("matriculasId.equals=" + (matriculasId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoDescontoShouldBeFound(String filter) throws Exception {
        restPlanoDescontoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoDesconto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].isIsentoMulta").value(hasItem(DEFAULT_IS_ISENTO_MULTA.booleanValue())))
            .andExpect(jsonPath("$.[*].isIsentoJuro").value(hasItem(DEFAULT_IS_ISENTO_JURO.booleanValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(sameNumber(DEFAULT_DESCONTO))));

        // Check, that the count call also returns 1
        restPlanoDescontoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoDescontoShouldNotBeFound(String filter) throws Exception {
        restPlanoDescontoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoDescontoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPlanoDesconto() throws Exception {
        // Get the planoDesconto
        restPlanoDescontoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanoDesconto() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        int databaseSizeBeforeUpdate = planoDescontoRepository.findAll().size();

        // Update the planoDesconto
        PlanoDesconto updatedPlanoDesconto = planoDescontoRepository.findById(planoDesconto.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoDesconto are not directly saved in db
        em.detach(updatedPlanoDesconto);
        updatedPlanoDesconto
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .isIsentoMulta(UPDATED_IS_ISENTO_MULTA)
            .isIsentoJuro(UPDATED_IS_ISENTO_JURO)
            .desconto(UPDATED_DESCONTO);
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(updatedPlanoDesconto);

        restPlanoDescontoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoDescontoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlanoDesconto in the database
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeUpdate);
        PlanoDesconto testPlanoDesconto = planoDescontoList.get(planoDescontoList.size() - 1);
        assertThat(testPlanoDesconto.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testPlanoDesconto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPlanoDesconto.getIsIsentoMulta()).isEqualTo(UPDATED_IS_ISENTO_MULTA);
        assertThat(testPlanoDesconto.getIsIsentoJuro()).isEqualTo(UPDATED_IS_ISENTO_JURO);
        assertThat(testPlanoDesconto.getDesconto()).isEqualByComparingTo(UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void putNonExistingPlanoDesconto() throws Exception {
        int databaseSizeBeforeUpdate = planoDescontoRepository.findAll().size();
        planoDesconto.setId(count.incrementAndGet());

        // Create the PlanoDesconto
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(planoDesconto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoDescontoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoDescontoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoDesconto in the database
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanoDesconto() throws Exception {
        int databaseSizeBeforeUpdate = planoDescontoRepository.findAll().size();
        planoDesconto.setId(count.incrementAndGet());

        // Create the PlanoDesconto
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(planoDesconto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoDescontoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoDesconto in the database
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanoDesconto() throws Exception {
        int databaseSizeBeforeUpdate = planoDescontoRepository.findAll().size();
        planoDesconto.setId(count.incrementAndGet());

        // Create the PlanoDesconto
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(planoDesconto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoDescontoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoDesconto in the database
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanoDescontoWithPatch() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        int databaseSizeBeforeUpdate = planoDescontoRepository.findAll().size();

        // Update the planoDesconto using partial update
        PlanoDesconto partialUpdatedPlanoDesconto = new PlanoDesconto();
        partialUpdatedPlanoDesconto.setId(planoDesconto.getId());

        partialUpdatedPlanoDesconto
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .isIsentoMulta(UPDATED_IS_ISENTO_MULTA)
            .isIsentoJuro(UPDATED_IS_ISENTO_JURO);

        restPlanoDescontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoDesconto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanoDesconto))
            )
            .andExpect(status().isOk());

        // Validate the PlanoDesconto in the database
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeUpdate);
        PlanoDesconto testPlanoDesconto = planoDescontoList.get(planoDescontoList.size() - 1);
        assertThat(testPlanoDesconto.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testPlanoDesconto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPlanoDesconto.getIsIsentoMulta()).isEqualTo(UPDATED_IS_ISENTO_MULTA);
        assertThat(testPlanoDesconto.getIsIsentoJuro()).isEqualTo(UPDATED_IS_ISENTO_JURO);
        assertThat(testPlanoDesconto.getDesconto()).isEqualByComparingTo(DEFAULT_DESCONTO);
    }

    @Test
    @Transactional
    void fullUpdatePlanoDescontoWithPatch() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        int databaseSizeBeforeUpdate = planoDescontoRepository.findAll().size();

        // Update the planoDesconto using partial update
        PlanoDesconto partialUpdatedPlanoDesconto = new PlanoDesconto();
        partialUpdatedPlanoDesconto.setId(planoDesconto.getId());

        partialUpdatedPlanoDesconto
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .isIsentoMulta(UPDATED_IS_ISENTO_MULTA)
            .isIsentoJuro(UPDATED_IS_ISENTO_JURO)
            .desconto(UPDATED_DESCONTO);

        restPlanoDescontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoDesconto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanoDesconto))
            )
            .andExpect(status().isOk());

        // Validate the PlanoDesconto in the database
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeUpdate);
        PlanoDesconto testPlanoDesconto = planoDescontoList.get(planoDescontoList.size() - 1);
        assertThat(testPlanoDesconto.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testPlanoDesconto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPlanoDesconto.getIsIsentoMulta()).isEqualTo(UPDATED_IS_ISENTO_MULTA);
        assertThat(testPlanoDesconto.getIsIsentoJuro()).isEqualTo(UPDATED_IS_ISENTO_JURO);
        assertThat(testPlanoDesconto.getDesconto()).isEqualByComparingTo(UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    void patchNonExistingPlanoDesconto() throws Exception {
        int databaseSizeBeforeUpdate = planoDescontoRepository.findAll().size();
        planoDesconto.setId(count.incrementAndGet());

        // Create the PlanoDesconto
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(planoDesconto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoDescontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planoDescontoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoDesconto in the database
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanoDesconto() throws Exception {
        int databaseSizeBeforeUpdate = planoDescontoRepository.findAll().size();
        planoDesconto.setId(count.incrementAndGet());

        // Create the PlanoDesconto
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(planoDesconto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoDescontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoDesconto in the database
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanoDesconto() throws Exception {
        int databaseSizeBeforeUpdate = planoDescontoRepository.findAll().size();
        planoDesconto.setId(count.incrementAndGet());

        // Create the PlanoDesconto
        PlanoDescontoDTO planoDescontoDTO = planoDescontoMapper.toDto(planoDesconto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoDescontoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planoDescontoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoDesconto in the database
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanoDesconto() throws Exception {
        // Initialize the database
        planoDescontoRepository.saveAndFlush(planoDesconto);

        int databaseSizeBeforeDelete = planoDescontoRepository.findAll().size();

        // Delete the planoDesconto
        restPlanoDescontoMockMvc
            .perform(delete(ENTITY_API_URL_ID, planoDesconto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoDesconto> planoDescontoList = planoDescontoRepository.findAll();
        assertThat(planoDescontoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
