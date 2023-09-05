package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.CategoriaOcorrencia;
import com.ravunana.longonkelo.domain.CategoriaOcorrencia;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.MedidaDisciplinar;
import com.ravunana.longonkelo.domain.Ocorrencia;
import com.ravunana.longonkelo.repository.CategoriaOcorrenciaRepository;
import com.ravunana.longonkelo.service.CategoriaOcorrenciaService;
import com.ravunana.longonkelo.service.criteria.CategoriaOcorrenciaCriteria;
import com.ravunana.longonkelo.service.dto.CategoriaOcorrenciaDTO;
import com.ravunana.longonkelo.service.mapper.CategoriaOcorrenciaMapper;
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
 * Integration tests for the {@link CategoriaOcorrenciaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CategoriaOcorrenciaResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_SANSAO_DISICPLINAR = "AAAAAAAAAA";
    private static final String UPDATED_SANSAO_DISICPLINAR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_NOTIFICA_ENCAREGADO = false;
    private static final Boolean UPDATED_IS_NOTIFICA_ENCAREGADO = true;

    private static final Boolean DEFAULT_IS_SEND_EMAIL = false;
    private static final Boolean UPDATED_IS_SEND_EMAIL = true;

    private static final Boolean DEFAULT_IS_SEND_SMS = false;
    private static final Boolean UPDATED_IS_SEND_SMS = true;

    private static final Boolean DEFAULT_IS_SEND_PUSH = false;
    private static final Boolean UPDATED_IS_SEND_PUSH = true;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categoria-ocorrencias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoriaOcorrenciaRepository categoriaOcorrenciaRepository;

    @Mock
    private CategoriaOcorrenciaRepository categoriaOcorrenciaRepositoryMock;

    @Autowired
    private CategoriaOcorrenciaMapper categoriaOcorrenciaMapper;

    @Mock
    private CategoriaOcorrenciaService categoriaOcorrenciaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriaOcorrenciaMockMvc;

    private CategoriaOcorrencia categoriaOcorrencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaOcorrencia createEntity(EntityManager em) {
        CategoriaOcorrencia categoriaOcorrencia = new CategoriaOcorrencia()
            .codigo(DEFAULT_CODIGO)
            .sansaoDisicplinar(DEFAULT_SANSAO_DISICPLINAR)
            .isNotificaEncaregado(DEFAULT_IS_NOTIFICA_ENCAREGADO)
            .isSendEmail(DEFAULT_IS_SEND_EMAIL)
            .isSendSms(DEFAULT_IS_SEND_SMS)
            .isSendPush(DEFAULT_IS_SEND_PUSH)
            .descricao(DEFAULT_DESCRICAO)
            .observacao(DEFAULT_OBSERVACAO);
        // Add required entity
        MedidaDisciplinar medidaDisciplinar;
        if (TestUtil.findAll(em, MedidaDisciplinar.class).isEmpty()) {
            medidaDisciplinar = MedidaDisciplinarResourceIT.createEntity(em);
            em.persist(medidaDisciplinar);
            em.flush();
        } else {
            medidaDisciplinar = TestUtil.findAll(em, MedidaDisciplinar.class).get(0);
        }
        categoriaOcorrencia.setMedidaDisciplinar(medidaDisciplinar);
        return categoriaOcorrencia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaOcorrencia createUpdatedEntity(EntityManager em) {
        CategoriaOcorrencia categoriaOcorrencia = new CategoriaOcorrencia()
            .codigo(UPDATED_CODIGO)
            .sansaoDisicplinar(UPDATED_SANSAO_DISICPLINAR)
            .isNotificaEncaregado(UPDATED_IS_NOTIFICA_ENCAREGADO)
            .isSendEmail(UPDATED_IS_SEND_EMAIL)
            .isSendSms(UPDATED_IS_SEND_SMS)
            .isSendPush(UPDATED_IS_SEND_PUSH)
            .descricao(UPDATED_DESCRICAO)
            .observacao(UPDATED_OBSERVACAO);
        // Add required entity
        MedidaDisciplinar medidaDisciplinar;
        if (TestUtil.findAll(em, MedidaDisciplinar.class).isEmpty()) {
            medidaDisciplinar = MedidaDisciplinarResourceIT.createUpdatedEntity(em);
            em.persist(medidaDisciplinar);
            em.flush();
        } else {
            medidaDisciplinar = TestUtil.findAll(em, MedidaDisciplinar.class).get(0);
        }
        categoriaOcorrencia.setMedidaDisciplinar(medidaDisciplinar);
        return categoriaOcorrencia;
    }

    @BeforeEach
    public void initTest() {
        categoriaOcorrencia = createEntity(em);
    }

    @Test
    @Transactional
    void createCategoriaOcorrencia() throws Exception {
        int databaseSizeBeforeCreate = categoriaOcorrenciaRepository.findAll().size();
        // Create the CategoriaOcorrencia
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);
        restCategoriaOcorrenciaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaOcorrenciaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategoriaOcorrencia in the database
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaOcorrencia testCategoriaOcorrencia = categoriaOcorrenciaList.get(categoriaOcorrenciaList.size() - 1);
        assertThat(testCategoriaOcorrencia.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testCategoriaOcorrencia.getSansaoDisicplinar()).isEqualTo(DEFAULT_SANSAO_DISICPLINAR);
        assertThat(testCategoriaOcorrencia.getIsNotificaEncaregado()).isEqualTo(DEFAULT_IS_NOTIFICA_ENCAREGADO);
        assertThat(testCategoriaOcorrencia.getIsSendEmail()).isEqualTo(DEFAULT_IS_SEND_EMAIL);
        assertThat(testCategoriaOcorrencia.getIsSendSms()).isEqualTo(DEFAULT_IS_SEND_SMS);
        assertThat(testCategoriaOcorrencia.getIsSendPush()).isEqualTo(DEFAULT_IS_SEND_PUSH);
        assertThat(testCategoriaOcorrencia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCategoriaOcorrencia.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    void createCategoriaOcorrenciaWithExistingId() throws Exception {
        // Create the CategoriaOcorrencia with an existing ID
        categoriaOcorrencia.setId(1L);
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);

        int databaseSizeBeforeCreate = categoriaOcorrenciaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaOcorrenciaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaOcorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaOcorrencia in the database
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaOcorrenciaRepository.findAll().size();
        // set the field null
        categoriaOcorrencia.setCodigo(null);

        // Create the CategoriaOcorrencia, which fails.
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);

        restCategoriaOcorrenciaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaOcorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaOcorrenciaRepository.findAll().size();
        // set the field null
        categoriaOcorrencia.setDescricao(null);

        // Create the CategoriaOcorrencia, which fails.
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);

        restCategoriaOcorrenciaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaOcorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrencias() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList
        restCategoriaOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaOcorrencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].sansaoDisicplinar").value(hasItem(DEFAULT_SANSAO_DISICPLINAR)))
            .andExpect(jsonPath("$.[*].isNotificaEncaregado").value(hasItem(DEFAULT_IS_NOTIFICA_ENCAREGADO.booleanValue())))
            .andExpect(jsonPath("$.[*].isSendEmail").value(hasItem(DEFAULT_IS_SEND_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].isSendSms").value(hasItem(DEFAULT_IS_SEND_SMS.booleanValue())))
            .andExpect(jsonPath("$.[*].isSendPush").value(hasItem(DEFAULT_IS_SEND_PUSH.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCategoriaOcorrenciasWithEagerRelationshipsIsEnabled() throws Exception {
        when(categoriaOcorrenciaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCategoriaOcorrenciaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(categoriaOcorrenciaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCategoriaOcorrenciasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(categoriaOcorrenciaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCategoriaOcorrenciaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(categoriaOcorrenciaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCategoriaOcorrencia() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get the categoriaOcorrencia
        restCategoriaOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL_ID, categoriaOcorrencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaOcorrencia.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.sansaoDisicplinar").value(DEFAULT_SANSAO_DISICPLINAR))
            .andExpect(jsonPath("$.isNotificaEncaregado").value(DEFAULT_IS_NOTIFICA_ENCAREGADO.booleanValue()))
            .andExpect(jsonPath("$.isSendEmail").value(DEFAULT_IS_SEND_EMAIL.booleanValue()))
            .andExpect(jsonPath("$.isSendSms").value(DEFAULT_IS_SEND_SMS.booleanValue()))
            .andExpect(jsonPath("$.isSendPush").value(DEFAULT_IS_SEND_PUSH.booleanValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()));
    }

    @Test
    @Transactional
    void getCategoriaOcorrenciasByIdFiltering() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        Long id = categoriaOcorrencia.getId();

        defaultCategoriaOcorrenciaShouldBeFound("id.equals=" + id);
        defaultCategoriaOcorrenciaShouldNotBeFound("id.notEquals=" + id);

        defaultCategoriaOcorrenciaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoriaOcorrenciaShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoriaOcorrenciaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoriaOcorrenciaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where codigo equals to DEFAULT_CODIGO
        defaultCategoriaOcorrenciaShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the categoriaOcorrenciaList where codigo equals to UPDATED_CODIGO
        defaultCategoriaOcorrenciaShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultCategoriaOcorrenciaShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the categoriaOcorrenciaList where codigo equals to UPDATED_CODIGO
        defaultCategoriaOcorrenciaShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where codigo is not null
        defaultCategoriaOcorrenciaShouldBeFound("codigo.specified=true");

        // Get all the categoriaOcorrenciaList where codigo is null
        defaultCategoriaOcorrenciaShouldNotBeFound("codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByCodigoContainsSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where codigo contains DEFAULT_CODIGO
        defaultCategoriaOcorrenciaShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the categoriaOcorrenciaList where codigo contains UPDATED_CODIGO
        defaultCategoriaOcorrenciaShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where codigo does not contain DEFAULT_CODIGO
        defaultCategoriaOcorrenciaShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the categoriaOcorrenciaList where codigo does not contain UPDATED_CODIGO
        defaultCategoriaOcorrenciaShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasBySansaoDisicplinarIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where sansaoDisicplinar equals to DEFAULT_SANSAO_DISICPLINAR
        defaultCategoriaOcorrenciaShouldBeFound("sansaoDisicplinar.equals=" + DEFAULT_SANSAO_DISICPLINAR);

        // Get all the categoriaOcorrenciaList where sansaoDisicplinar equals to UPDATED_SANSAO_DISICPLINAR
        defaultCategoriaOcorrenciaShouldNotBeFound("sansaoDisicplinar.equals=" + UPDATED_SANSAO_DISICPLINAR);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasBySansaoDisicplinarIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where sansaoDisicplinar in DEFAULT_SANSAO_DISICPLINAR or UPDATED_SANSAO_DISICPLINAR
        defaultCategoriaOcorrenciaShouldBeFound("sansaoDisicplinar.in=" + DEFAULT_SANSAO_DISICPLINAR + "," + UPDATED_SANSAO_DISICPLINAR);

        // Get all the categoriaOcorrenciaList where sansaoDisicplinar equals to UPDATED_SANSAO_DISICPLINAR
        defaultCategoriaOcorrenciaShouldNotBeFound("sansaoDisicplinar.in=" + UPDATED_SANSAO_DISICPLINAR);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasBySansaoDisicplinarIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where sansaoDisicplinar is not null
        defaultCategoriaOcorrenciaShouldBeFound("sansaoDisicplinar.specified=true");

        // Get all the categoriaOcorrenciaList where sansaoDisicplinar is null
        defaultCategoriaOcorrenciaShouldNotBeFound("sansaoDisicplinar.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasBySansaoDisicplinarContainsSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where sansaoDisicplinar contains DEFAULT_SANSAO_DISICPLINAR
        defaultCategoriaOcorrenciaShouldBeFound("sansaoDisicplinar.contains=" + DEFAULT_SANSAO_DISICPLINAR);

        // Get all the categoriaOcorrenciaList where sansaoDisicplinar contains UPDATED_SANSAO_DISICPLINAR
        defaultCategoriaOcorrenciaShouldNotBeFound("sansaoDisicplinar.contains=" + UPDATED_SANSAO_DISICPLINAR);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasBySansaoDisicplinarNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where sansaoDisicplinar does not contain DEFAULT_SANSAO_DISICPLINAR
        defaultCategoriaOcorrenciaShouldNotBeFound("sansaoDisicplinar.doesNotContain=" + DEFAULT_SANSAO_DISICPLINAR);

        // Get all the categoriaOcorrenciaList where sansaoDisicplinar does not contain UPDATED_SANSAO_DISICPLINAR
        defaultCategoriaOcorrenciaShouldBeFound("sansaoDisicplinar.doesNotContain=" + UPDATED_SANSAO_DISICPLINAR);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsNotificaEncaregadoIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isNotificaEncaregado equals to DEFAULT_IS_NOTIFICA_ENCAREGADO
        defaultCategoriaOcorrenciaShouldBeFound("isNotificaEncaregado.equals=" + DEFAULT_IS_NOTIFICA_ENCAREGADO);

        // Get all the categoriaOcorrenciaList where isNotificaEncaregado equals to UPDATED_IS_NOTIFICA_ENCAREGADO
        defaultCategoriaOcorrenciaShouldNotBeFound("isNotificaEncaregado.equals=" + UPDATED_IS_NOTIFICA_ENCAREGADO);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsNotificaEncaregadoIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isNotificaEncaregado in DEFAULT_IS_NOTIFICA_ENCAREGADO or UPDATED_IS_NOTIFICA_ENCAREGADO
        defaultCategoriaOcorrenciaShouldBeFound(
            "isNotificaEncaregado.in=" + DEFAULT_IS_NOTIFICA_ENCAREGADO + "," + UPDATED_IS_NOTIFICA_ENCAREGADO
        );

        // Get all the categoriaOcorrenciaList where isNotificaEncaregado equals to UPDATED_IS_NOTIFICA_ENCAREGADO
        defaultCategoriaOcorrenciaShouldNotBeFound("isNotificaEncaregado.in=" + UPDATED_IS_NOTIFICA_ENCAREGADO);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsNotificaEncaregadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isNotificaEncaregado is not null
        defaultCategoriaOcorrenciaShouldBeFound("isNotificaEncaregado.specified=true");

        // Get all the categoriaOcorrenciaList where isNotificaEncaregado is null
        defaultCategoriaOcorrenciaShouldNotBeFound("isNotificaEncaregado.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsSendEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isSendEmail equals to DEFAULT_IS_SEND_EMAIL
        defaultCategoriaOcorrenciaShouldBeFound("isSendEmail.equals=" + DEFAULT_IS_SEND_EMAIL);

        // Get all the categoriaOcorrenciaList where isSendEmail equals to UPDATED_IS_SEND_EMAIL
        defaultCategoriaOcorrenciaShouldNotBeFound("isSendEmail.equals=" + UPDATED_IS_SEND_EMAIL);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsSendEmailIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isSendEmail in DEFAULT_IS_SEND_EMAIL or UPDATED_IS_SEND_EMAIL
        defaultCategoriaOcorrenciaShouldBeFound("isSendEmail.in=" + DEFAULT_IS_SEND_EMAIL + "," + UPDATED_IS_SEND_EMAIL);

        // Get all the categoriaOcorrenciaList where isSendEmail equals to UPDATED_IS_SEND_EMAIL
        defaultCategoriaOcorrenciaShouldNotBeFound("isSendEmail.in=" + UPDATED_IS_SEND_EMAIL);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsSendEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isSendEmail is not null
        defaultCategoriaOcorrenciaShouldBeFound("isSendEmail.specified=true");

        // Get all the categoriaOcorrenciaList where isSendEmail is null
        defaultCategoriaOcorrenciaShouldNotBeFound("isSendEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsSendSmsIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isSendSms equals to DEFAULT_IS_SEND_SMS
        defaultCategoriaOcorrenciaShouldBeFound("isSendSms.equals=" + DEFAULT_IS_SEND_SMS);

        // Get all the categoriaOcorrenciaList where isSendSms equals to UPDATED_IS_SEND_SMS
        defaultCategoriaOcorrenciaShouldNotBeFound("isSendSms.equals=" + UPDATED_IS_SEND_SMS);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsSendSmsIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isSendSms in DEFAULT_IS_SEND_SMS or UPDATED_IS_SEND_SMS
        defaultCategoriaOcorrenciaShouldBeFound("isSendSms.in=" + DEFAULT_IS_SEND_SMS + "," + UPDATED_IS_SEND_SMS);

        // Get all the categoriaOcorrenciaList where isSendSms equals to UPDATED_IS_SEND_SMS
        defaultCategoriaOcorrenciaShouldNotBeFound("isSendSms.in=" + UPDATED_IS_SEND_SMS);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsSendSmsIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isSendSms is not null
        defaultCategoriaOcorrenciaShouldBeFound("isSendSms.specified=true");

        // Get all the categoriaOcorrenciaList where isSendSms is null
        defaultCategoriaOcorrenciaShouldNotBeFound("isSendSms.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsSendPushIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isSendPush equals to DEFAULT_IS_SEND_PUSH
        defaultCategoriaOcorrenciaShouldBeFound("isSendPush.equals=" + DEFAULT_IS_SEND_PUSH);

        // Get all the categoriaOcorrenciaList where isSendPush equals to UPDATED_IS_SEND_PUSH
        defaultCategoriaOcorrenciaShouldNotBeFound("isSendPush.equals=" + UPDATED_IS_SEND_PUSH);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsSendPushIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isSendPush in DEFAULT_IS_SEND_PUSH or UPDATED_IS_SEND_PUSH
        defaultCategoriaOcorrenciaShouldBeFound("isSendPush.in=" + DEFAULT_IS_SEND_PUSH + "," + UPDATED_IS_SEND_PUSH);

        // Get all the categoriaOcorrenciaList where isSendPush equals to UPDATED_IS_SEND_PUSH
        defaultCategoriaOcorrenciaShouldNotBeFound("isSendPush.in=" + UPDATED_IS_SEND_PUSH);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByIsSendPushIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where isSendPush is not null
        defaultCategoriaOcorrenciaShouldBeFound("isSendPush.specified=true");

        // Get all the categoriaOcorrenciaList where isSendPush is null
        defaultCategoriaOcorrenciaShouldNotBeFound("isSendPush.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where descricao equals to DEFAULT_DESCRICAO
        defaultCategoriaOcorrenciaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the categoriaOcorrenciaList where descricao equals to UPDATED_DESCRICAO
        defaultCategoriaOcorrenciaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultCategoriaOcorrenciaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the categoriaOcorrenciaList where descricao equals to UPDATED_DESCRICAO
        defaultCategoriaOcorrenciaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where descricao is not null
        defaultCategoriaOcorrenciaShouldBeFound("descricao.specified=true");

        // Get all the categoriaOcorrenciaList where descricao is null
        defaultCategoriaOcorrenciaShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where descricao contains DEFAULT_DESCRICAO
        defaultCategoriaOcorrenciaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the categoriaOcorrenciaList where descricao contains UPDATED_DESCRICAO
        defaultCategoriaOcorrenciaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        // Get all the categoriaOcorrenciaList where descricao does not contain DEFAULT_DESCRICAO
        defaultCategoriaOcorrenciaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the categoriaOcorrenciaList where descricao does not contain UPDATED_DESCRICAO
        defaultCategoriaOcorrenciaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    // @Test
    // @Transactional
    // void getAllCategoriaOcorrenciasByCategoriaOcorrenciaIsEqualToSomething() throws Exception {
    //     CategoriaOcorrencia categoriaOcorrencia;
    //     if (TestUtil.findAll(em, CategoriaOcorrencia.class).isEmpty()) {
    //         categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);
    //         categoriaOcorrencia = CategoriaOcorrenciaResourceIT.createEntity(em);
    //     } else {
    //         categoriaOcorrencia = TestUtil.findAll(em, CategoriaOcorrencia.class).get(0);
    //     }
    //     em.persist(categoriaOcorrencia);
    //     em.flush();
    //     categoriaOcorrencia.addCategoriaOcorrencia(categoriaOcorrencia);
    //     categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);
    //     Long categoriaOcorrenciaId = categoriaOcorrencia.getId();

    //     // Get all the categoriaOcorrenciaList where categoriaOcorrencia equals to categoriaOcorrenciaId
    //     defaultCategoriaOcorrenciaShouldBeFound("categoriaOcorrenciaId.equals=" + categoriaOcorrenciaId);

    //     // Get all the categoriaOcorrenciaList where categoriaOcorrencia equals to (categoriaOcorrenciaId + 1)
    //     defaultCategoriaOcorrenciaShouldNotBeFound("categoriaOcorrenciaId.equals=" + (categoriaOcorrenciaId + 1));
    // }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByOcorrenciaIsEqualToSomething() throws Exception {
        Ocorrencia ocorrencia;
        if (TestUtil.findAll(em, Ocorrencia.class).isEmpty()) {
            categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);
            ocorrencia = OcorrenciaResourceIT.createEntity(em);
        } else {
            ocorrencia = TestUtil.findAll(em, Ocorrencia.class).get(0);
        }
        em.persist(ocorrencia);
        em.flush();
        categoriaOcorrencia.addOcorrencia(ocorrencia);
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);
        Long ocorrenciaId = ocorrencia.getId();

        // Get all the categoriaOcorrenciaList where ocorrencia equals to ocorrenciaId
        defaultCategoriaOcorrenciaShouldBeFound("ocorrenciaId.equals=" + ocorrenciaId);

        // Get all the categoriaOcorrenciaList where ocorrencia equals to (ocorrenciaId + 1)
        defaultCategoriaOcorrenciaShouldNotBeFound("ocorrenciaId.equals=" + (ocorrenciaId + 1));
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByEncaminharIsEqualToSomething() throws Exception {
        Docente encaminhar;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);
            encaminhar = DocenteResourceIT.createEntity(em);
        } else {
            encaminhar = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(encaminhar);
        em.flush();
        categoriaOcorrencia.setEncaminhar(encaminhar);
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);
        Long encaminharId = encaminhar.getId();

        // Get all the categoriaOcorrenciaList where encaminhar equals to encaminharId
        defaultCategoriaOcorrenciaShouldBeFound("encaminharId.equals=" + encaminharId);

        // Get all the categoriaOcorrenciaList where encaminhar equals to (encaminharId + 1)
        defaultCategoriaOcorrenciaShouldNotBeFound("encaminharId.equals=" + (encaminharId + 1));
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByReferenciaIsEqualToSomething() throws Exception {
        CategoriaOcorrencia referencia;
        if (TestUtil.findAll(em, CategoriaOcorrencia.class).isEmpty()) {
            categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);
            referencia = CategoriaOcorrenciaResourceIT.createEntity(em);
        } else {
            referencia = TestUtil.findAll(em, CategoriaOcorrencia.class).get(0);
        }
        em.persist(referencia);
        em.flush();
        categoriaOcorrencia.setReferencia(referencia);
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);
        Long referenciaId = referencia.getId();

        // Get all the categoriaOcorrenciaList where referencia equals to referenciaId
        defaultCategoriaOcorrenciaShouldBeFound("referenciaId.equals=" + referenciaId);

        // Get all the categoriaOcorrenciaList where referencia equals to (referenciaId + 1)
        defaultCategoriaOcorrenciaShouldNotBeFound("referenciaId.equals=" + (referenciaId + 1));
    }

    @Test
    @Transactional
    void getAllCategoriaOcorrenciasByMedidaDisciplinarIsEqualToSomething() throws Exception {
        MedidaDisciplinar medidaDisciplinar;
        if (TestUtil.findAll(em, MedidaDisciplinar.class).isEmpty()) {
            categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);
            medidaDisciplinar = MedidaDisciplinarResourceIT.createEntity(em);
        } else {
            medidaDisciplinar = TestUtil.findAll(em, MedidaDisciplinar.class).get(0);
        }
        em.persist(medidaDisciplinar);
        em.flush();
        categoriaOcorrencia.setMedidaDisciplinar(medidaDisciplinar);
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);
        Long medidaDisciplinarId = medidaDisciplinar.getId();

        // Get all the categoriaOcorrenciaList where medidaDisciplinar equals to medidaDisciplinarId
        defaultCategoriaOcorrenciaShouldBeFound("medidaDisciplinarId.equals=" + medidaDisciplinarId);

        // Get all the categoriaOcorrenciaList where medidaDisciplinar equals to (medidaDisciplinarId + 1)
        defaultCategoriaOcorrenciaShouldNotBeFound("medidaDisciplinarId.equals=" + (medidaDisciplinarId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriaOcorrenciaShouldBeFound(String filter) throws Exception {
        restCategoriaOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaOcorrencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].sansaoDisicplinar").value(hasItem(DEFAULT_SANSAO_DISICPLINAR)))
            .andExpect(jsonPath("$.[*].isNotificaEncaregado").value(hasItem(DEFAULT_IS_NOTIFICA_ENCAREGADO.booleanValue())))
            .andExpect(jsonPath("$.[*].isSendEmail").value(hasItem(DEFAULT_IS_SEND_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].isSendSms").value(hasItem(DEFAULT_IS_SEND_SMS.booleanValue())))
            .andExpect(jsonPath("$.[*].isSendPush").value(hasItem(DEFAULT_IS_SEND_PUSH.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));

        // Check, that the count call also returns 1
        restCategoriaOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriaOcorrenciaShouldNotBeFound(String filter) throws Exception {
        restCategoriaOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriaOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCategoriaOcorrencia() throws Exception {
        // Get the categoriaOcorrencia
        restCategoriaOcorrenciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategoriaOcorrencia() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        int databaseSizeBeforeUpdate = categoriaOcorrenciaRepository.findAll().size();

        // Update the categoriaOcorrencia
        CategoriaOcorrencia updatedCategoriaOcorrencia = categoriaOcorrenciaRepository.findById(categoriaOcorrencia.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaOcorrencia are not directly saved in db
        em.detach(updatedCategoriaOcorrencia);
        updatedCategoriaOcorrencia
            .codigo(UPDATED_CODIGO)
            .sansaoDisicplinar(UPDATED_SANSAO_DISICPLINAR)
            .isNotificaEncaregado(UPDATED_IS_NOTIFICA_ENCAREGADO)
            .isSendEmail(UPDATED_IS_SEND_EMAIL)
            .isSendSms(UPDATED_IS_SEND_SMS)
            .isSendPush(UPDATED_IS_SEND_PUSH)
            .descricao(UPDATED_DESCRICAO)
            .observacao(UPDATED_OBSERVACAO);
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaMapper.toDto(updatedCategoriaOcorrencia);

        restCategoriaOcorrenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaOcorrenciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaOcorrenciaDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaOcorrencia in the database
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeUpdate);
        CategoriaOcorrencia testCategoriaOcorrencia = categoriaOcorrenciaList.get(categoriaOcorrenciaList.size() - 1);
        assertThat(testCategoriaOcorrencia.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testCategoriaOcorrencia.getSansaoDisicplinar()).isEqualTo(UPDATED_SANSAO_DISICPLINAR);
        assertThat(testCategoriaOcorrencia.getIsNotificaEncaregado()).isEqualTo(UPDATED_IS_NOTIFICA_ENCAREGADO);
        assertThat(testCategoriaOcorrencia.getIsSendEmail()).isEqualTo(UPDATED_IS_SEND_EMAIL);
        assertThat(testCategoriaOcorrencia.getIsSendSms()).isEqualTo(UPDATED_IS_SEND_SMS);
        assertThat(testCategoriaOcorrencia.getIsSendPush()).isEqualTo(UPDATED_IS_SEND_PUSH);
        assertThat(testCategoriaOcorrencia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCategoriaOcorrencia.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void putNonExistingCategoriaOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = categoriaOcorrenciaRepository.findAll().size();
        categoriaOcorrencia.setId(count.incrementAndGet());

        // Create the CategoriaOcorrencia
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaOcorrenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaOcorrenciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaOcorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaOcorrencia in the database
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategoriaOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = categoriaOcorrenciaRepository.findAll().size();
        categoriaOcorrencia.setId(count.incrementAndGet());

        // Create the CategoriaOcorrencia
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaOcorrenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaOcorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaOcorrencia in the database
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategoriaOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = categoriaOcorrenciaRepository.findAll().size();
        categoriaOcorrencia.setId(count.incrementAndGet());

        // Create the CategoriaOcorrencia
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaOcorrenciaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaOcorrenciaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaOcorrencia in the database
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriaOcorrenciaWithPatch() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        int databaseSizeBeforeUpdate = categoriaOcorrenciaRepository.findAll().size();

        // Update the categoriaOcorrencia using partial update
        CategoriaOcorrencia partialUpdatedCategoriaOcorrencia = new CategoriaOcorrencia();
        partialUpdatedCategoriaOcorrencia.setId(categoriaOcorrencia.getId());

        partialUpdatedCategoriaOcorrencia
            .sansaoDisicplinar(UPDATED_SANSAO_DISICPLINAR)
            .isNotificaEncaregado(UPDATED_IS_NOTIFICA_ENCAREGADO)
            .isSendEmail(UPDATED_IS_SEND_EMAIL)
            .isSendPush(UPDATED_IS_SEND_PUSH);

        restCategoriaOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaOcorrencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaOcorrencia))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaOcorrencia in the database
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeUpdate);
        CategoriaOcorrencia testCategoriaOcorrencia = categoriaOcorrenciaList.get(categoriaOcorrenciaList.size() - 1);
        assertThat(testCategoriaOcorrencia.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testCategoriaOcorrencia.getSansaoDisicplinar()).isEqualTo(UPDATED_SANSAO_DISICPLINAR);
        assertThat(testCategoriaOcorrencia.getIsNotificaEncaregado()).isEqualTo(UPDATED_IS_NOTIFICA_ENCAREGADO);
        assertThat(testCategoriaOcorrencia.getIsSendEmail()).isEqualTo(UPDATED_IS_SEND_EMAIL);
        assertThat(testCategoriaOcorrencia.getIsSendSms()).isEqualTo(DEFAULT_IS_SEND_SMS);
        assertThat(testCategoriaOcorrencia.getIsSendPush()).isEqualTo(UPDATED_IS_SEND_PUSH);
        assertThat(testCategoriaOcorrencia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCategoriaOcorrencia.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    void fullUpdateCategoriaOcorrenciaWithPatch() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        int databaseSizeBeforeUpdate = categoriaOcorrenciaRepository.findAll().size();

        // Update the categoriaOcorrencia using partial update
        CategoriaOcorrencia partialUpdatedCategoriaOcorrencia = new CategoriaOcorrencia();
        partialUpdatedCategoriaOcorrencia.setId(categoriaOcorrencia.getId());

        partialUpdatedCategoriaOcorrencia
            .codigo(UPDATED_CODIGO)
            .sansaoDisicplinar(UPDATED_SANSAO_DISICPLINAR)
            .isNotificaEncaregado(UPDATED_IS_NOTIFICA_ENCAREGADO)
            .isSendEmail(UPDATED_IS_SEND_EMAIL)
            .isSendSms(UPDATED_IS_SEND_SMS)
            .isSendPush(UPDATED_IS_SEND_PUSH)
            .descricao(UPDATED_DESCRICAO)
            .observacao(UPDATED_OBSERVACAO);

        restCategoriaOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaOcorrencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaOcorrencia))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaOcorrencia in the database
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeUpdate);
        CategoriaOcorrencia testCategoriaOcorrencia = categoriaOcorrenciaList.get(categoriaOcorrenciaList.size() - 1);
        assertThat(testCategoriaOcorrencia.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testCategoriaOcorrencia.getSansaoDisicplinar()).isEqualTo(UPDATED_SANSAO_DISICPLINAR);
        assertThat(testCategoriaOcorrencia.getIsNotificaEncaregado()).isEqualTo(UPDATED_IS_NOTIFICA_ENCAREGADO);
        assertThat(testCategoriaOcorrencia.getIsSendEmail()).isEqualTo(UPDATED_IS_SEND_EMAIL);
        assertThat(testCategoriaOcorrencia.getIsSendSms()).isEqualTo(UPDATED_IS_SEND_SMS);
        assertThat(testCategoriaOcorrencia.getIsSendPush()).isEqualTo(UPDATED_IS_SEND_PUSH);
        assertThat(testCategoriaOcorrencia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCategoriaOcorrencia.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void patchNonExistingCategoriaOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = categoriaOcorrenciaRepository.findAll().size();
        categoriaOcorrencia.setId(count.incrementAndGet());

        // Create the CategoriaOcorrencia
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoriaOcorrenciaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaOcorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaOcorrencia in the database
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategoriaOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = categoriaOcorrenciaRepository.findAll().size();
        categoriaOcorrencia.setId(count.incrementAndGet());

        // Create the CategoriaOcorrencia
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaOcorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaOcorrencia in the database
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategoriaOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = categoriaOcorrenciaRepository.findAll().size();
        categoriaOcorrencia.setId(count.incrementAndGet());

        // Create the CategoriaOcorrencia
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaOcorrenciaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaOcorrencia in the database
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategoriaOcorrencia() throws Exception {
        // Initialize the database
        categoriaOcorrenciaRepository.saveAndFlush(categoriaOcorrencia);

        int databaseSizeBeforeDelete = categoriaOcorrenciaRepository.findAll().size();

        // Delete the categoriaOcorrencia
        restCategoriaOcorrenciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoriaOcorrencia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaOcorrencia> categoriaOcorrenciaList = categoriaOcorrenciaRepository.findAll();
        assertThat(categoriaOcorrenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
