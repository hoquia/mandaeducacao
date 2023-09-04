package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AreaFormacao;
import com.ravunana.longonkelo.domain.Classe;
import com.ravunana.longonkelo.domain.Curso;
import com.ravunana.longonkelo.domain.Emolumento;
import com.ravunana.longonkelo.domain.PlanoMulta;
import com.ravunana.longonkelo.domain.PrecoEmolumento;
import com.ravunana.longonkelo.domain.Turno;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.PrecoEmolumentoRepository;
import com.ravunana.longonkelo.service.PrecoEmolumentoService;
import com.ravunana.longonkelo.service.criteria.PrecoEmolumentoCriteria;
import com.ravunana.longonkelo.service.dto.PrecoEmolumentoDTO;
import com.ravunana.longonkelo.service.mapper.PrecoEmolumentoMapper;
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
 * Integration tests for the {@link PrecoEmolumentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PrecoEmolumentoResourceIT {

    private static final BigDecimal DEFAULT_PRECO = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRECO = new BigDecimal(1);
    private static final BigDecimal SMALLER_PRECO = new BigDecimal(0 - 1);

    private static final Boolean DEFAULT_IS_ESPECIFICO_CURSO = false;
    private static final Boolean UPDATED_IS_ESPECIFICO_CURSO = true;

    private static final Boolean DEFAULT_IS_ESPECIFICO_AREA_FORMACAO = false;
    private static final Boolean UPDATED_IS_ESPECIFICO_AREA_FORMACAO = true;

    private static final Boolean DEFAULT_IS_ESPECIFICO_CLASSE = false;
    private static final Boolean UPDATED_IS_ESPECIFICO_CLASSE = true;

    private static final Boolean DEFAULT_IS_ESPECIFICO_TURNO = false;
    private static final Boolean UPDATED_IS_ESPECIFICO_TURNO = true;

    private static final String ENTITY_API_URL = "/api/preco-emolumentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrecoEmolumentoRepository precoEmolumentoRepository;

    @Mock
    private PrecoEmolumentoRepository precoEmolumentoRepositoryMock;

    @Autowired
    private PrecoEmolumentoMapper precoEmolumentoMapper;

    @Mock
    private PrecoEmolumentoService precoEmolumentoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrecoEmolumentoMockMvc;

    private PrecoEmolumento precoEmolumento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrecoEmolumento createEntity(EntityManager em) {
        PrecoEmolumento precoEmolumento = new PrecoEmolumento()
            .preco(DEFAULT_PRECO)
            .isEspecificoCurso(DEFAULT_IS_ESPECIFICO_CURSO)
            .isEspecificoAreaFormacao(DEFAULT_IS_ESPECIFICO_AREA_FORMACAO)
            .isEspecificoClasse(DEFAULT_IS_ESPECIFICO_CLASSE)
            .isEspecificoTurno(DEFAULT_IS_ESPECIFICO_TURNO);
        // Add required entity
        Emolumento emolumento;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            emolumento = EmolumentoResourceIT.createEntity(em);
            em.persist(emolumento);
            em.flush();
        } else {
            emolumento = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        precoEmolumento.setEmolumento(emolumento);
        return precoEmolumento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrecoEmolumento createUpdatedEntity(EntityManager em) {
        PrecoEmolumento precoEmolumento = new PrecoEmolumento()
            .preco(UPDATED_PRECO)
            .isEspecificoCurso(UPDATED_IS_ESPECIFICO_CURSO)
            .isEspecificoAreaFormacao(UPDATED_IS_ESPECIFICO_AREA_FORMACAO)
            .isEspecificoClasse(UPDATED_IS_ESPECIFICO_CLASSE)
            .isEspecificoTurno(UPDATED_IS_ESPECIFICO_TURNO);
        // Add required entity
        Emolumento emolumento;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            emolumento = EmolumentoResourceIT.createUpdatedEntity(em);
            em.persist(emolumento);
            em.flush();
        } else {
            emolumento = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        precoEmolumento.setEmolumento(emolumento);
        return precoEmolumento;
    }

    @BeforeEach
    public void initTest() {
        precoEmolumento = createEntity(em);
    }

    @Test
    @Transactional
    void createPrecoEmolumento() throws Exception {
        int databaseSizeBeforeCreate = precoEmolumentoRepository.findAll().size();
        // Create the PrecoEmolumento
        PrecoEmolumentoDTO precoEmolumentoDTO = precoEmolumentoMapper.toDto(precoEmolumento);
        restPrecoEmolumentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precoEmolumentoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PrecoEmolumento in the database
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeCreate + 1);
        PrecoEmolumento testPrecoEmolumento = precoEmolumentoList.get(precoEmolumentoList.size() - 1);
        assertThat(testPrecoEmolumento.getPreco()).isEqualByComparingTo(DEFAULT_PRECO);
        assertThat(testPrecoEmolumento.getIsEspecificoCurso()).isEqualTo(DEFAULT_IS_ESPECIFICO_CURSO);
        assertThat(testPrecoEmolumento.getIsEspecificoAreaFormacao()).isEqualTo(DEFAULT_IS_ESPECIFICO_AREA_FORMACAO);
        assertThat(testPrecoEmolumento.getIsEspecificoClasse()).isEqualTo(DEFAULT_IS_ESPECIFICO_CLASSE);
        assertThat(testPrecoEmolumento.getIsEspecificoTurno()).isEqualTo(DEFAULT_IS_ESPECIFICO_TURNO);
    }

    @Test
    @Transactional
    void createPrecoEmolumentoWithExistingId() throws Exception {
        // Create the PrecoEmolumento with an existing ID
        precoEmolumento.setId(1L);
        PrecoEmolumentoDTO precoEmolumentoDTO = precoEmolumentoMapper.toDto(precoEmolumento);

        int databaseSizeBeforeCreate = precoEmolumentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrecoEmolumentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precoEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecoEmolumento in the database
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = precoEmolumentoRepository.findAll().size();
        // set the field null
        precoEmolumento.setPreco(null);

        // Create the PrecoEmolumento, which fails.
        PrecoEmolumentoDTO precoEmolumentoDTO = precoEmolumentoMapper.toDto(precoEmolumento);

        restPrecoEmolumentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precoEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentos() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList
        restPrecoEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(precoEmolumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(sameNumber(DEFAULT_PRECO))))
            .andExpect(jsonPath("$.[*].isEspecificoCurso").value(hasItem(DEFAULT_IS_ESPECIFICO_CURSO.booleanValue())))
            .andExpect(jsonPath("$.[*].isEspecificoAreaFormacao").value(hasItem(DEFAULT_IS_ESPECIFICO_AREA_FORMACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].isEspecificoClasse").value(hasItem(DEFAULT_IS_ESPECIFICO_CLASSE.booleanValue())))
            .andExpect(jsonPath("$.[*].isEspecificoTurno").value(hasItem(DEFAULT_IS_ESPECIFICO_TURNO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPrecoEmolumentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(precoEmolumentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPrecoEmolumentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(precoEmolumentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPrecoEmolumentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(precoEmolumentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPrecoEmolumentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(precoEmolumentoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPrecoEmolumento() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get the precoEmolumento
        restPrecoEmolumentoMockMvc
            .perform(get(ENTITY_API_URL_ID, precoEmolumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(precoEmolumento.getId().intValue()))
            .andExpect(jsonPath("$.preco").value(sameNumber(DEFAULT_PRECO)))
            .andExpect(jsonPath("$.isEspecificoCurso").value(DEFAULT_IS_ESPECIFICO_CURSO.booleanValue()))
            .andExpect(jsonPath("$.isEspecificoAreaFormacao").value(DEFAULT_IS_ESPECIFICO_AREA_FORMACAO.booleanValue()))
            .andExpect(jsonPath("$.isEspecificoClasse").value(DEFAULT_IS_ESPECIFICO_CLASSE.booleanValue()))
            .andExpect(jsonPath("$.isEspecificoTurno").value(DEFAULT_IS_ESPECIFICO_TURNO.booleanValue()));
    }

    @Test
    @Transactional
    void getPrecoEmolumentosByIdFiltering() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        Long id = precoEmolumento.getId();

        defaultPrecoEmolumentoShouldBeFound("id.equals=" + id);
        defaultPrecoEmolumentoShouldNotBeFound("id.notEquals=" + id);

        defaultPrecoEmolumentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPrecoEmolumentoShouldNotBeFound("id.greaterThan=" + id);

        defaultPrecoEmolumentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPrecoEmolumentoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByPrecoIsEqualToSomething() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where preco equals to DEFAULT_PRECO
        defaultPrecoEmolumentoShouldBeFound("preco.equals=" + DEFAULT_PRECO);

        // Get all the precoEmolumentoList where preco equals to UPDATED_PRECO
        defaultPrecoEmolumentoShouldNotBeFound("preco.equals=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByPrecoIsInShouldWork() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where preco in DEFAULT_PRECO or UPDATED_PRECO
        defaultPrecoEmolumentoShouldBeFound("preco.in=" + DEFAULT_PRECO + "," + UPDATED_PRECO);

        // Get all the precoEmolumentoList where preco equals to UPDATED_PRECO
        defaultPrecoEmolumentoShouldNotBeFound("preco.in=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByPrecoIsNullOrNotNull() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where preco is not null
        defaultPrecoEmolumentoShouldBeFound("preco.specified=true");

        // Get all the precoEmolumentoList where preco is null
        defaultPrecoEmolumentoShouldNotBeFound("preco.specified=false");
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByPrecoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where preco is greater than or equal to DEFAULT_PRECO
        defaultPrecoEmolumentoShouldBeFound("preco.greaterThanOrEqual=" + DEFAULT_PRECO);

        // Get all the precoEmolumentoList where preco is greater than or equal to UPDATED_PRECO
        defaultPrecoEmolumentoShouldNotBeFound("preco.greaterThanOrEqual=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByPrecoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where preco is less than or equal to DEFAULT_PRECO
        defaultPrecoEmolumentoShouldBeFound("preco.lessThanOrEqual=" + DEFAULT_PRECO);

        // Get all the precoEmolumentoList where preco is less than or equal to SMALLER_PRECO
        defaultPrecoEmolumentoShouldNotBeFound("preco.lessThanOrEqual=" + SMALLER_PRECO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByPrecoIsLessThanSomething() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where preco is less than DEFAULT_PRECO
        defaultPrecoEmolumentoShouldNotBeFound("preco.lessThan=" + DEFAULT_PRECO);

        // Get all the precoEmolumentoList where preco is less than UPDATED_PRECO
        defaultPrecoEmolumentoShouldBeFound("preco.lessThan=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByPrecoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where preco is greater than DEFAULT_PRECO
        defaultPrecoEmolumentoShouldNotBeFound("preco.greaterThan=" + DEFAULT_PRECO);

        // Get all the precoEmolumentoList where preco is greater than SMALLER_PRECO
        defaultPrecoEmolumentoShouldBeFound("preco.greaterThan=" + SMALLER_PRECO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoCursoIsEqualToSomething() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoCurso equals to DEFAULT_IS_ESPECIFICO_CURSO
        defaultPrecoEmolumentoShouldBeFound("isEspecificoCurso.equals=" + DEFAULT_IS_ESPECIFICO_CURSO);

        // Get all the precoEmolumentoList where isEspecificoCurso equals to UPDATED_IS_ESPECIFICO_CURSO
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoCurso.equals=" + UPDATED_IS_ESPECIFICO_CURSO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoCursoIsInShouldWork() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoCurso in DEFAULT_IS_ESPECIFICO_CURSO or UPDATED_IS_ESPECIFICO_CURSO
        defaultPrecoEmolumentoShouldBeFound("isEspecificoCurso.in=" + DEFAULT_IS_ESPECIFICO_CURSO + "," + UPDATED_IS_ESPECIFICO_CURSO);

        // Get all the precoEmolumentoList where isEspecificoCurso equals to UPDATED_IS_ESPECIFICO_CURSO
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoCurso.in=" + UPDATED_IS_ESPECIFICO_CURSO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoCursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoCurso is not null
        defaultPrecoEmolumentoShouldBeFound("isEspecificoCurso.specified=true");

        // Get all the precoEmolumentoList where isEspecificoCurso is null
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoCurso.specified=false");
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoAreaFormacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoAreaFormacao equals to DEFAULT_IS_ESPECIFICO_AREA_FORMACAO
        defaultPrecoEmolumentoShouldBeFound("isEspecificoAreaFormacao.equals=" + DEFAULT_IS_ESPECIFICO_AREA_FORMACAO);

        // Get all the precoEmolumentoList where isEspecificoAreaFormacao equals to UPDATED_IS_ESPECIFICO_AREA_FORMACAO
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoAreaFormacao.equals=" + UPDATED_IS_ESPECIFICO_AREA_FORMACAO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoAreaFormacaoIsInShouldWork() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoAreaFormacao in DEFAULT_IS_ESPECIFICO_AREA_FORMACAO or UPDATED_IS_ESPECIFICO_AREA_FORMACAO
        defaultPrecoEmolumentoShouldBeFound(
            "isEspecificoAreaFormacao.in=" + DEFAULT_IS_ESPECIFICO_AREA_FORMACAO + "," + UPDATED_IS_ESPECIFICO_AREA_FORMACAO
        );

        // Get all the precoEmolumentoList where isEspecificoAreaFormacao equals to UPDATED_IS_ESPECIFICO_AREA_FORMACAO
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoAreaFormacao.in=" + UPDATED_IS_ESPECIFICO_AREA_FORMACAO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoAreaFormacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoAreaFormacao is not null
        defaultPrecoEmolumentoShouldBeFound("isEspecificoAreaFormacao.specified=true");

        // Get all the precoEmolumentoList where isEspecificoAreaFormacao is null
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoAreaFormacao.specified=false");
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoClasseIsEqualToSomething() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoClasse equals to DEFAULT_IS_ESPECIFICO_CLASSE
        defaultPrecoEmolumentoShouldBeFound("isEspecificoClasse.equals=" + DEFAULT_IS_ESPECIFICO_CLASSE);

        // Get all the precoEmolumentoList where isEspecificoClasse equals to UPDATED_IS_ESPECIFICO_CLASSE
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoClasse.equals=" + UPDATED_IS_ESPECIFICO_CLASSE);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoClasseIsInShouldWork() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoClasse in DEFAULT_IS_ESPECIFICO_CLASSE or UPDATED_IS_ESPECIFICO_CLASSE
        defaultPrecoEmolumentoShouldBeFound("isEspecificoClasse.in=" + DEFAULT_IS_ESPECIFICO_CLASSE + "," + UPDATED_IS_ESPECIFICO_CLASSE);

        // Get all the precoEmolumentoList where isEspecificoClasse equals to UPDATED_IS_ESPECIFICO_CLASSE
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoClasse.in=" + UPDATED_IS_ESPECIFICO_CLASSE);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoClasseIsNullOrNotNull() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoClasse is not null
        defaultPrecoEmolumentoShouldBeFound("isEspecificoClasse.specified=true");

        // Get all the precoEmolumentoList where isEspecificoClasse is null
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoClasse.specified=false");
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoTurnoIsEqualToSomething() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoTurno equals to DEFAULT_IS_ESPECIFICO_TURNO
        defaultPrecoEmolumentoShouldBeFound("isEspecificoTurno.equals=" + DEFAULT_IS_ESPECIFICO_TURNO);

        // Get all the precoEmolumentoList where isEspecificoTurno equals to UPDATED_IS_ESPECIFICO_TURNO
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoTurno.equals=" + UPDATED_IS_ESPECIFICO_TURNO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoTurnoIsInShouldWork() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoTurno in DEFAULT_IS_ESPECIFICO_TURNO or UPDATED_IS_ESPECIFICO_TURNO
        defaultPrecoEmolumentoShouldBeFound("isEspecificoTurno.in=" + DEFAULT_IS_ESPECIFICO_TURNO + "," + UPDATED_IS_ESPECIFICO_TURNO);

        // Get all the precoEmolumentoList where isEspecificoTurno equals to UPDATED_IS_ESPECIFICO_TURNO
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoTurno.in=" + UPDATED_IS_ESPECIFICO_TURNO);
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByIsEspecificoTurnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        // Get all the precoEmolumentoList where isEspecificoTurno is not null
        defaultPrecoEmolumentoShouldBeFound("isEspecificoTurno.specified=true");

        // Get all the precoEmolumentoList where isEspecificoTurno is null
        defaultPrecoEmolumentoShouldNotBeFound("isEspecificoTurno.specified=false");
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            precoEmolumentoRepository.saveAndFlush(precoEmolumento);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        precoEmolumento.setUtilizador(utilizador);
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);
        Long utilizadorId = utilizador.getId();

        // Get all the precoEmolumentoList where utilizador equals to utilizadorId
        defaultPrecoEmolumentoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the precoEmolumentoList where utilizador equals to (utilizadorId + 1)
        defaultPrecoEmolumentoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByEmolumentoIsEqualToSomething() throws Exception {
        Emolumento emolumento;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            precoEmolumentoRepository.saveAndFlush(precoEmolumento);
            emolumento = EmolumentoResourceIT.createEntity(em);
        } else {
            emolumento = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        em.persist(emolumento);
        em.flush();
        precoEmolumento.setEmolumento(emolumento);
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);
        Long emolumentoId = emolumento.getId();

        // Get all the precoEmolumentoList where emolumento equals to emolumentoId
        defaultPrecoEmolumentoShouldBeFound("emolumentoId.equals=" + emolumentoId);

        // Get all the precoEmolumentoList where emolumento equals to (emolumentoId + 1)
        defaultPrecoEmolumentoShouldNotBeFound("emolumentoId.equals=" + (emolumentoId + 1));
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByAreaFormacaoIsEqualToSomething() throws Exception {
        AreaFormacao areaFormacao;
        if (TestUtil.findAll(em, AreaFormacao.class).isEmpty()) {
            precoEmolumentoRepository.saveAndFlush(precoEmolumento);
            areaFormacao = AreaFormacaoResourceIT.createEntity(em);
        } else {
            areaFormacao = TestUtil.findAll(em, AreaFormacao.class).get(0);
        }
        em.persist(areaFormacao);
        em.flush();
        precoEmolumento.setAreaFormacao(areaFormacao);
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);
        Long areaFormacaoId = areaFormacao.getId();

        // Get all the precoEmolumentoList where areaFormacao equals to areaFormacaoId
        defaultPrecoEmolumentoShouldBeFound("areaFormacaoId.equals=" + areaFormacaoId);

        // Get all the precoEmolumentoList where areaFormacao equals to (areaFormacaoId + 1)
        defaultPrecoEmolumentoShouldNotBeFound("areaFormacaoId.equals=" + (areaFormacaoId + 1));
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByCursoIsEqualToSomething() throws Exception {
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            precoEmolumentoRepository.saveAndFlush(precoEmolumento);
            curso = CursoResourceIT.createEntity(em);
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        em.persist(curso);
        em.flush();
        precoEmolumento.setCurso(curso);
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);
        Long cursoId = curso.getId();

        // Get all the precoEmolumentoList where curso equals to cursoId
        defaultPrecoEmolumentoShouldBeFound("cursoId.equals=" + cursoId);

        // Get all the precoEmolumentoList where curso equals to (cursoId + 1)
        defaultPrecoEmolumentoShouldNotBeFound("cursoId.equals=" + (cursoId + 1));
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByClasseIsEqualToSomething() throws Exception {
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            precoEmolumentoRepository.saveAndFlush(precoEmolumento);
            classe = ClasseResourceIT.createEntity(em);
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        em.persist(classe);
        em.flush();
        precoEmolumento.setClasse(classe);
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);
        Long classeId = classe.getId();

        // Get all the precoEmolumentoList where classe equals to classeId
        defaultPrecoEmolumentoShouldBeFound("classeId.equals=" + classeId);

        // Get all the precoEmolumentoList where classe equals to (classeId + 1)
        defaultPrecoEmolumentoShouldNotBeFound("classeId.equals=" + (classeId + 1));
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByTurnoIsEqualToSomething() throws Exception {
        Turno turno;
        if (TestUtil.findAll(em, Turno.class).isEmpty()) {
            precoEmolumentoRepository.saveAndFlush(precoEmolumento);
            turno = TurnoResourceIT.createEntity(em);
        } else {
            turno = TestUtil.findAll(em, Turno.class).get(0);
        }
        em.persist(turno);
        em.flush();
        precoEmolumento.setTurno(turno);
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);
        Long turnoId = turno.getId();

        // Get all the precoEmolumentoList where turno equals to turnoId
        defaultPrecoEmolumentoShouldBeFound("turnoId.equals=" + turnoId);

        // Get all the precoEmolumentoList where turno equals to (turnoId + 1)
        defaultPrecoEmolumentoShouldNotBeFound("turnoId.equals=" + (turnoId + 1));
    }

    @Test
    @Transactional
    void getAllPrecoEmolumentosByPlanoMultaIsEqualToSomething() throws Exception {
        PlanoMulta planoMulta;
        if (TestUtil.findAll(em, PlanoMulta.class).isEmpty()) {
            precoEmolumentoRepository.saveAndFlush(precoEmolumento);
            planoMulta = PlanoMultaResourceIT.createEntity(em);
        } else {
            planoMulta = TestUtil.findAll(em, PlanoMulta.class).get(0);
        }
        em.persist(planoMulta);
        em.flush();
        precoEmolumento.setPlanoMulta(planoMulta);
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);
        Long planoMultaId = planoMulta.getId();

        // Get all the precoEmolumentoList where planoMulta equals to planoMultaId
        defaultPrecoEmolumentoShouldBeFound("planoMultaId.equals=" + planoMultaId);

        // Get all the precoEmolumentoList where planoMulta equals to (planoMultaId + 1)
        defaultPrecoEmolumentoShouldNotBeFound("planoMultaId.equals=" + (planoMultaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrecoEmolumentoShouldBeFound(String filter) throws Exception {
        restPrecoEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(precoEmolumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(sameNumber(DEFAULT_PRECO))))
            .andExpect(jsonPath("$.[*].isEspecificoCurso").value(hasItem(DEFAULT_IS_ESPECIFICO_CURSO.booleanValue())))
            .andExpect(jsonPath("$.[*].isEspecificoAreaFormacao").value(hasItem(DEFAULT_IS_ESPECIFICO_AREA_FORMACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].isEspecificoClasse").value(hasItem(DEFAULT_IS_ESPECIFICO_CLASSE.booleanValue())))
            .andExpect(jsonPath("$.[*].isEspecificoTurno").value(hasItem(DEFAULT_IS_ESPECIFICO_TURNO.booleanValue())));

        // Check, that the count call also returns 1
        restPrecoEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrecoEmolumentoShouldNotBeFound(String filter) throws Exception {
        restPrecoEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrecoEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPrecoEmolumento() throws Exception {
        // Get the precoEmolumento
        restPrecoEmolumentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrecoEmolumento() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        int databaseSizeBeforeUpdate = precoEmolumentoRepository.findAll().size();

        // Update the precoEmolumento
        PrecoEmolumento updatedPrecoEmolumento = precoEmolumentoRepository.findById(precoEmolumento.getId()).get();
        // Disconnect from session so that the updates on updatedPrecoEmolumento are not directly saved in db
        em.detach(updatedPrecoEmolumento);
        updatedPrecoEmolumento
            .preco(UPDATED_PRECO)
            .isEspecificoCurso(UPDATED_IS_ESPECIFICO_CURSO)
            .isEspecificoAreaFormacao(UPDATED_IS_ESPECIFICO_AREA_FORMACAO)
            .isEspecificoClasse(UPDATED_IS_ESPECIFICO_CLASSE)
            .isEspecificoTurno(UPDATED_IS_ESPECIFICO_TURNO);
        PrecoEmolumentoDTO precoEmolumentoDTO = precoEmolumentoMapper.toDto(updatedPrecoEmolumento);

        restPrecoEmolumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, precoEmolumentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precoEmolumentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the PrecoEmolumento in the database
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeUpdate);
        PrecoEmolumento testPrecoEmolumento = precoEmolumentoList.get(precoEmolumentoList.size() - 1);
        assertThat(testPrecoEmolumento.getPreco()).isEqualByComparingTo(UPDATED_PRECO);
        assertThat(testPrecoEmolumento.getIsEspecificoCurso()).isEqualTo(UPDATED_IS_ESPECIFICO_CURSO);
        assertThat(testPrecoEmolumento.getIsEspecificoAreaFormacao()).isEqualTo(UPDATED_IS_ESPECIFICO_AREA_FORMACAO);
        assertThat(testPrecoEmolumento.getIsEspecificoClasse()).isEqualTo(UPDATED_IS_ESPECIFICO_CLASSE);
        assertThat(testPrecoEmolumento.getIsEspecificoTurno()).isEqualTo(UPDATED_IS_ESPECIFICO_TURNO);
    }

    @Test
    @Transactional
    void putNonExistingPrecoEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = precoEmolumentoRepository.findAll().size();
        precoEmolumento.setId(count.incrementAndGet());

        // Create the PrecoEmolumento
        PrecoEmolumentoDTO precoEmolumentoDTO = precoEmolumentoMapper.toDto(precoEmolumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrecoEmolumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, precoEmolumentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precoEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecoEmolumento in the database
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrecoEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = precoEmolumentoRepository.findAll().size();
        precoEmolumento.setId(count.incrementAndGet());

        // Create the PrecoEmolumento
        PrecoEmolumentoDTO precoEmolumentoDTO = precoEmolumentoMapper.toDto(precoEmolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecoEmolumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precoEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecoEmolumento in the database
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrecoEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = precoEmolumentoRepository.findAll().size();
        precoEmolumento.setId(count.incrementAndGet());

        // Create the PrecoEmolumento
        PrecoEmolumentoDTO precoEmolumentoDTO = precoEmolumentoMapper.toDto(precoEmolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecoEmolumentoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precoEmolumentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrecoEmolumento in the database
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrecoEmolumentoWithPatch() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        int databaseSizeBeforeUpdate = precoEmolumentoRepository.findAll().size();

        // Update the precoEmolumento using partial update
        PrecoEmolumento partialUpdatedPrecoEmolumento = new PrecoEmolumento();
        partialUpdatedPrecoEmolumento.setId(precoEmolumento.getId());

        partialUpdatedPrecoEmolumento
            .preco(UPDATED_PRECO)
            .isEspecificoClasse(UPDATED_IS_ESPECIFICO_CLASSE)
            .isEspecificoTurno(UPDATED_IS_ESPECIFICO_TURNO);

        restPrecoEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrecoEmolumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrecoEmolumento))
            )
            .andExpect(status().isOk());

        // Validate the PrecoEmolumento in the database
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeUpdate);
        PrecoEmolumento testPrecoEmolumento = precoEmolumentoList.get(precoEmolumentoList.size() - 1);
        assertThat(testPrecoEmolumento.getPreco()).isEqualByComparingTo(UPDATED_PRECO);
        assertThat(testPrecoEmolumento.getIsEspecificoCurso()).isEqualTo(DEFAULT_IS_ESPECIFICO_CURSO);
        assertThat(testPrecoEmolumento.getIsEspecificoAreaFormacao()).isEqualTo(DEFAULT_IS_ESPECIFICO_AREA_FORMACAO);
        assertThat(testPrecoEmolumento.getIsEspecificoClasse()).isEqualTo(UPDATED_IS_ESPECIFICO_CLASSE);
        assertThat(testPrecoEmolumento.getIsEspecificoTurno()).isEqualTo(UPDATED_IS_ESPECIFICO_TURNO);
    }

    @Test
    @Transactional
    void fullUpdatePrecoEmolumentoWithPatch() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        int databaseSizeBeforeUpdate = precoEmolumentoRepository.findAll().size();

        // Update the precoEmolumento using partial update
        PrecoEmolumento partialUpdatedPrecoEmolumento = new PrecoEmolumento();
        partialUpdatedPrecoEmolumento.setId(precoEmolumento.getId());

        partialUpdatedPrecoEmolumento
            .preco(UPDATED_PRECO)
            .isEspecificoCurso(UPDATED_IS_ESPECIFICO_CURSO)
            .isEspecificoAreaFormacao(UPDATED_IS_ESPECIFICO_AREA_FORMACAO)
            .isEspecificoClasse(UPDATED_IS_ESPECIFICO_CLASSE)
            .isEspecificoTurno(UPDATED_IS_ESPECIFICO_TURNO);

        restPrecoEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrecoEmolumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrecoEmolumento))
            )
            .andExpect(status().isOk());

        // Validate the PrecoEmolumento in the database
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeUpdate);
        PrecoEmolumento testPrecoEmolumento = precoEmolumentoList.get(precoEmolumentoList.size() - 1);
        assertThat(testPrecoEmolumento.getPreco()).isEqualByComparingTo(UPDATED_PRECO);
        assertThat(testPrecoEmolumento.getIsEspecificoCurso()).isEqualTo(UPDATED_IS_ESPECIFICO_CURSO);
        assertThat(testPrecoEmolumento.getIsEspecificoAreaFormacao()).isEqualTo(UPDATED_IS_ESPECIFICO_AREA_FORMACAO);
        assertThat(testPrecoEmolumento.getIsEspecificoClasse()).isEqualTo(UPDATED_IS_ESPECIFICO_CLASSE);
        assertThat(testPrecoEmolumento.getIsEspecificoTurno()).isEqualTo(UPDATED_IS_ESPECIFICO_TURNO);
    }

    @Test
    @Transactional
    void patchNonExistingPrecoEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = precoEmolumentoRepository.findAll().size();
        precoEmolumento.setId(count.incrementAndGet());

        // Create the PrecoEmolumento
        PrecoEmolumentoDTO precoEmolumentoDTO = precoEmolumentoMapper.toDto(precoEmolumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrecoEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, precoEmolumentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(precoEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecoEmolumento in the database
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrecoEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = precoEmolumentoRepository.findAll().size();
        precoEmolumento.setId(count.incrementAndGet());

        // Create the PrecoEmolumento
        PrecoEmolumentoDTO precoEmolumentoDTO = precoEmolumentoMapper.toDto(precoEmolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecoEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(precoEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecoEmolumento in the database
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrecoEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = precoEmolumentoRepository.findAll().size();
        precoEmolumento.setId(count.incrementAndGet());

        // Create the PrecoEmolumento
        PrecoEmolumentoDTO precoEmolumentoDTO = precoEmolumentoMapper.toDto(precoEmolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecoEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(precoEmolumentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrecoEmolumento in the database
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrecoEmolumento() throws Exception {
        // Initialize the database
        precoEmolumentoRepository.saveAndFlush(precoEmolumento);

        int databaseSizeBeforeDelete = precoEmolumentoRepository.findAll().size();

        // Delete the precoEmolumento
        restPrecoEmolumentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, precoEmolumento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrecoEmolumento> precoEmolumentoList = precoEmolumentoRepository.findAll();
        assertThat(precoEmolumentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
