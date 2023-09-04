package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.FormacaoDocente;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.repository.FormacaoDocenteRepository;
import com.ravunana.longonkelo.service.FormacaoDocenteService;
import com.ravunana.longonkelo.service.criteria.FormacaoDocenteCriteria;
import com.ravunana.longonkelo.service.dto.FormacaoDocenteDTO;
import com.ravunana.longonkelo.service.mapper.FormacaoDocenteMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link FormacaoDocenteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FormacaoDocenteResourceIT {

    private static final String DEFAULT_INSTITUICAO_ENSINO = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUICAO_ENSINO = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_FORMACAO = "AAAAAAAAAA";
    private static final String UPDATED_AREA_FORMACAO = "BBBBBBBBBB";

    private static final String DEFAULT_CURSO = "AAAAAAAAAA";
    private static final String UPDATED_CURSO = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_GRAU = "AAAAAAAAAA";
    private static final String UPDATED_GRAU = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INICIO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_INICIO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FIM = LocalDate.ofEpochDay(-1L);

    private static final byte[] DEFAULT_ANEXO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANEXO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ANEXO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANEXO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/formacao-docentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormacaoDocenteRepository formacaoDocenteRepository;

    @Mock
    private FormacaoDocenteRepository formacaoDocenteRepositoryMock;

    @Autowired
    private FormacaoDocenteMapper formacaoDocenteMapper;

    @Mock
    private FormacaoDocenteService formacaoDocenteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormacaoDocenteMockMvc;

    private FormacaoDocente formacaoDocente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormacaoDocente createEntity(EntityManager em) {
        FormacaoDocente formacaoDocente = new FormacaoDocente()
            .instituicaoEnsino(DEFAULT_INSTITUICAO_ENSINO)
            .areaFormacao(DEFAULT_AREA_FORMACAO)
            .curso(DEFAULT_CURSO)
            .especialidade(DEFAULT_ESPECIALIDADE)
            .grau(DEFAULT_GRAU)
            .inicio(DEFAULT_INICIO)
            .fim(DEFAULT_FIM)
            .anexo(DEFAULT_ANEXO)
            .anexoContentType(DEFAULT_ANEXO_CONTENT_TYPE);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        formacaoDocente.setGrauAcademico(lookupItem);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        formacaoDocente.setDocente(docente);
        return formacaoDocente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormacaoDocente createUpdatedEntity(EntityManager em) {
        FormacaoDocente formacaoDocente = new FormacaoDocente()
            .instituicaoEnsino(UPDATED_INSTITUICAO_ENSINO)
            .areaFormacao(UPDATED_AREA_FORMACAO)
            .curso(UPDATED_CURSO)
            .especialidade(UPDATED_ESPECIALIDADE)
            .grau(UPDATED_GRAU)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createUpdatedEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        formacaoDocente.setGrauAcademico(lookupItem);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createUpdatedEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        formacaoDocente.setDocente(docente);
        return formacaoDocente;
    }

    @BeforeEach
    public void initTest() {
        formacaoDocente = createEntity(em);
    }

    @Test
    @Transactional
    void createFormacaoDocente() throws Exception {
        int databaseSizeBeforeCreate = formacaoDocenteRepository.findAll().size();
        // Create the FormacaoDocente
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);
        restFormacaoDocenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FormacaoDocente in the database
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeCreate + 1);
        FormacaoDocente testFormacaoDocente = formacaoDocenteList.get(formacaoDocenteList.size() - 1);
        assertThat(testFormacaoDocente.getInstituicaoEnsino()).isEqualTo(DEFAULT_INSTITUICAO_ENSINO);
        assertThat(testFormacaoDocente.getAreaFormacao()).isEqualTo(DEFAULT_AREA_FORMACAO);
        assertThat(testFormacaoDocente.getCurso()).isEqualTo(DEFAULT_CURSO);
        assertThat(testFormacaoDocente.getEspecialidade()).isEqualTo(DEFAULT_ESPECIALIDADE);
        assertThat(testFormacaoDocente.getGrau()).isEqualTo(DEFAULT_GRAU);
        assertThat(testFormacaoDocente.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testFormacaoDocente.getFim()).isEqualTo(DEFAULT_FIM);
        assertThat(testFormacaoDocente.getAnexo()).isEqualTo(DEFAULT_ANEXO);
        assertThat(testFormacaoDocente.getAnexoContentType()).isEqualTo(DEFAULT_ANEXO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFormacaoDocenteWithExistingId() throws Exception {
        // Create the FormacaoDocente with an existing ID
        formacaoDocente.setId(1L);
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);

        int databaseSizeBeforeCreate = formacaoDocenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormacaoDocenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormacaoDocente in the database
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInstituicaoEnsinoIsRequired() throws Exception {
        int databaseSizeBeforeTest = formacaoDocenteRepository.findAll().size();
        // set the field null
        formacaoDocente.setInstituicaoEnsino(null);

        // Create the FormacaoDocente, which fails.
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);

        restFormacaoDocenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAreaFormacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = formacaoDocenteRepository.findAll().size();
        // set the field null
        formacaoDocente.setAreaFormacao(null);

        // Create the FormacaoDocente, which fails.
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);

        restFormacaoDocenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGrauIsRequired() throws Exception {
        int databaseSizeBeforeTest = formacaoDocenteRepository.findAll().size();
        // set the field null
        formacaoDocente.setGrau(null);

        // Create the FormacaoDocente, which fails.
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);

        restFormacaoDocenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = formacaoDocenteRepository.findAll().size();
        // set the field null
        formacaoDocente.setInicio(null);

        // Create the FormacaoDocente, which fails.
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);

        restFormacaoDocenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentes() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList
        restFormacaoDocenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formacaoDocente.getId().intValue())))
            .andExpect(jsonPath("$.[*].instituicaoEnsino").value(hasItem(DEFAULT_INSTITUICAO_ENSINO)))
            .andExpect(jsonPath("$.[*].areaFormacao").value(hasItem(DEFAULT_AREA_FORMACAO)))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)))
            .andExpect(jsonPath("$.[*].especialidade").value(hasItem(DEFAULT_ESPECIALIDADE)))
            .andExpect(jsonPath("$.[*].grau").value(hasItem(DEFAULT_GRAU)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(DEFAULT_FIM.toString())))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFormacaoDocentesWithEagerRelationshipsIsEnabled() throws Exception {
        when(formacaoDocenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFormacaoDocenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(formacaoDocenteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFormacaoDocentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(formacaoDocenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFormacaoDocenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(formacaoDocenteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFormacaoDocente() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get the formacaoDocente
        restFormacaoDocenteMockMvc
            .perform(get(ENTITY_API_URL_ID, formacaoDocente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formacaoDocente.getId().intValue()))
            .andExpect(jsonPath("$.instituicaoEnsino").value(DEFAULT_INSTITUICAO_ENSINO))
            .andExpect(jsonPath("$.areaFormacao").value(DEFAULT_AREA_FORMACAO))
            .andExpect(jsonPath("$.curso").value(DEFAULT_CURSO))
            .andExpect(jsonPath("$.especialidade").value(DEFAULT_ESPECIALIDADE))
            .andExpect(jsonPath("$.grau").value(DEFAULT_GRAU))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fim").value(DEFAULT_FIM.toString()))
            .andExpect(jsonPath("$.anexoContentType").value(DEFAULT_ANEXO_CONTENT_TYPE))
            .andExpect(jsonPath("$.anexo").value(Base64Utils.encodeToString(DEFAULT_ANEXO)));
    }

    @Test
    @Transactional
    void getFormacaoDocentesByIdFiltering() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        Long id = formacaoDocente.getId();

        defaultFormacaoDocenteShouldBeFound("id.equals=" + id);
        defaultFormacaoDocenteShouldNotBeFound("id.notEquals=" + id);

        defaultFormacaoDocenteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFormacaoDocenteShouldNotBeFound("id.greaterThan=" + id);

        defaultFormacaoDocenteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFormacaoDocenteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInstituicaoEnsinoIsEqualToSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where instituicaoEnsino equals to DEFAULT_INSTITUICAO_ENSINO
        defaultFormacaoDocenteShouldBeFound("instituicaoEnsino.equals=" + DEFAULT_INSTITUICAO_ENSINO);

        // Get all the formacaoDocenteList where instituicaoEnsino equals to UPDATED_INSTITUICAO_ENSINO
        defaultFormacaoDocenteShouldNotBeFound("instituicaoEnsino.equals=" + UPDATED_INSTITUICAO_ENSINO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInstituicaoEnsinoIsInShouldWork() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where instituicaoEnsino in DEFAULT_INSTITUICAO_ENSINO or UPDATED_INSTITUICAO_ENSINO
        defaultFormacaoDocenteShouldBeFound("instituicaoEnsino.in=" + DEFAULT_INSTITUICAO_ENSINO + "," + UPDATED_INSTITUICAO_ENSINO);

        // Get all the formacaoDocenteList where instituicaoEnsino equals to UPDATED_INSTITUICAO_ENSINO
        defaultFormacaoDocenteShouldNotBeFound("instituicaoEnsino.in=" + UPDATED_INSTITUICAO_ENSINO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInstituicaoEnsinoIsNullOrNotNull() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where instituicaoEnsino is not null
        defaultFormacaoDocenteShouldBeFound("instituicaoEnsino.specified=true");

        // Get all the formacaoDocenteList where instituicaoEnsino is null
        defaultFormacaoDocenteShouldNotBeFound("instituicaoEnsino.specified=false");
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInstituicaoEnsinoContainsSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where instituicaoEnsino contains DEFAULT_INSTITUICAO_ENSINO
        defaultFormacaoDocenteShouldBeFound("instituicaoEnsino.contains=" + DEFAULT_INSTITUICAO_ENSINO);

        // Get all the formacaoDocenteList where instituicaoEnsino contains UPDATED_INSTITUICAO_ENSINO
        defaultFormacaoDocenteShouldNotBeFound("instituicaoEnsino.contains=" + UPDATED_INSTITUICAO_ENSINO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInstituicaoEnsinoNotContainsSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where instituicaoEnsino does not contain DEFAULT_INSTITUICAO_ENSINO
        defaultFormacaoDocenteShouldNotBeFound("instituicaoEnsino.doesNotContain=" + DEFAULT_INSTITUICAO_ENSINO);

        // Get all the formacaoDocenteList where instituicaoEnsino does not contain UPDATED_INSTITUICAO_ENSINO
        defaultFormacaoDocenteShouldBeFound("instituicaoEnsino.doesNotContain=" + UPDATED_INSTITUICAO_ENSINO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByAreaFormacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where areaFormacao equals to DEFAULT_AREA_FORMACAO
        defaultFormacaoDocenteShouldBeFound("areaFormacao.equals=" + DEFAULT_AREA_FORMACAO);

        // Get all the formacaoDocenteList where areaFormacao equals to UPDATED_AREA_FORMACAO
        defaultFormacaoDocenteShouldNotBeFound("areaFormacao.equals=" + UPDATED_AREA_FORMACAO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByAreaFormacaoIsInShouldWork() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where areaFormacao in DEFAULT_AREA_FORMACAO or UPDATED_AREA_FORMACAO
        defaultFormacaoDocenteShouldBeFound("areaFormacao.in=" + DEFAULT_AREA_FORMACAO + "," + UPDATED_AREA_FORMACAO);

        // Get all the formacaoDocenteList where areaFormacao equals to UPDATED_AREA_FORMACAO
        defaultFormacaoDocenteShouldNotBeFound("areaFormacao.in=" + UPDATED_AREA_FORMACAO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByAreaFormacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where areaFormacao is not null
        defaultFormacaoDocenteShouldBeFound("areaFormacao.specified=true");

        // Get all the formacaoDocenteList where areaFormacao is null
        defaultFormacaoDocenteShouldNotBeFound("areaFormacao.specified=false");
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByAreaFormacaoContainsSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where areaFormacao contains DEFAULT_AREA_FORMACAO
        defaultFormacaoDocenteShouldBeFound("areaFormacao.contains=" + DEFAULT_AREA_FORMACAO);

        // Get all the formacaoDocenteList where areaFormacao contains UPDATED_AREA_FORMACAO
        defaultFormacaoDocenteShouldNotBeFound("areaFormacao.contains=" + UPDATED_AREA_FORMACAO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByAreaFormacaoNotContainsSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where areaFormacao does not contain DEFAULT_AREA_FORMACAO
        defaultFormacaoDocenteShouldNotBeFound("areaFormacao.doesNotContain=" + DEFAULT_AREA_FORMACAO);

        // Get all the formacaoDocenteList where areaFormacao does not contain UPDATED_AREA_FORMACAO
        defaultFormacaoDocenteShouldBeFound("areaFormacao.doesNotContain=" + UPDATED_AREA_FORMACAO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByCursoIsEqualToSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where curso equals to DEFAULT_CURSO
        defaultFormacaoDocenteShouldBeFound("curso.equals=" + DEFAULT_CURSO);

        // Get all the formacaoDocenteList where curso equals to UPDATED_CURSO
        defaultFormacaoDocenteShouldNotBeFound("curso.equals=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByCursoIsInShouldWork() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where curso in DEFAULT_CURSO or UPDATED_CURSO
        defaultFormacaoDocenteShouldBeFound("curso.in=" + DEFAULT_CURSO + "," + UPDATED_CURSO);

        // Get all the formacaoDocenteList where curso equals to UPDATED_CURSO
        defaultFormacaoDocenteShouldNotBeFound("curso.in=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByCursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where curso is not null
        defaultFormacaoDocenteShouldBeFound("curso.specified=true");

        // Get all the formacaoDocenteList where curso is null
        defaultFormacaoDocenteShouldNotBeFound("curso.specified=false");
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByCursoContainsSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where curso contains DEFAULT_CURSO
        defaultFormacaoDocenteShouldBeFound("curso.contains=" + DEFAULT_CURSO);

        // Get all the formacaoDocenteList where curso contains UPDATED_CURSO
        defaultFormacaoDocenteShouldNotBeFound("curso.contains=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByCursoNotContainsSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where curso does not contain DEFAULT_CURSO
        defaultFormacaoDocenteShouldNotBeFound("curso.doesNotContain=" + DEFAULT_CURSO);

        // Get all the formacaoDocenteList where curso does not contain UPDATED_CURSO
        defaultFormacaoDocenteShouldBeFound("curso.doesNotContain=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByEspecialidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where especialidade equals to DEFAULT_ESPECIALIDADE
        defaultFormacaoDocenteShouldBeFound("especialidade.equals=" + DEFAULT_ESPECIALIDADE);

        // Get all the formacaoDocenteList where especialidade equals to UPDATED_ESPECIALIDADE
        defaultFormacaoDocenteShouldNotBeFound("especialidade.equals=" + UPDATED_ESPECIALIDADE);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByEspecialidadeIsInShouldWork() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where especialidade in DEFAULT_ESPECIALIDADE or UPDATED_ESPECIALIDADE
        defaultFormacaoDocenteShouldBeFound("especialidade.in=" + DEFAULT_ESPECIALIDADE + "," + UPDATED_ESPECIALIDADE);

        // Get all the formacaoDocenteList where especialidade equals to UPDATED_ESPECIALIDADE
        defaultFormacaoDocenteShouldNotBeFound("especialidade.in=" + UPDATED_ESPECIALIDADE);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByEspecialidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where especialidade is not null
        defaultFormacaoDocenteShouldBeFound("especialidade.specified=true");

        // Get all the formacaoDocenteList where especialidade is null
        defaultFormacaoDocenteShouldNotBeFound("especialidade.specified=false");
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByEspecialidadeContainsSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where especialidade contains DEFAULT_ESPECIALIDADE
        defaultFormacaoDocenteShouldBeFound("especialidade.contains=" + DEFAULT_ESPECIALIDADE);

        // Get all the formacaoDocenteList where especialidade contains UPDATED_ESPECIALIDADE
        defaultFormacaoDocenteShouldNotBeFound("especialidade.contains=" + UPDATED_ESPECIALIDADE);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByEspecialidadeNotContainsSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where especialidade does not contain DEFAULT_ESPECIALIDADE
        defaultFormacaoDocenteShouldNotBeFound("especialidade.doesNotContain=" + DEFAULT_ESPECIALIDADE);

        // Get all the formacaoDocenteList where especialidade does not contain UPDATED_ESPECIALIDADE
        defaultFormacaoDocenteShouldBeFound("especialidade.doesNotContain=" + UPDATED_ESPECIALIDADE);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByGrauIsEqualToSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where grau equals to DEFAULT_GRAU
        defaultFormacaoDocenteShouldBeFound("grau.equals=" + DEFAULT_GRAU);

        // Get all the formacaoDocenteList where grau equals to UPDATED_GRAU
        defaultFormacaoDocenteShouldNotBeFound("grau.equals=" + UPDATED_GRAU);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByGrauIsInShouldWork() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where grau in DEFAULT_GRAU or UPDATED_GRAU
        defaultFormacaoDocenteShouldBeFound("grau.in=" + DEFAULT_GRAU + "," + UPDATED_GRAU);

        // Get all the formacaoDocenteList where grau equals to UPDATED_GRAU
        defaultFormacaoDocenteShouldNotBeFound("grau.in=" + UPDATED_GRAU);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByGrauIsNullOrNotNull() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where grau is not null
        defaultFormacaoDocenteShouldBeFound("grau.specified=true");

        // Get all the formacaoDocenteList where grau is null
        defaultFormacaoDocenteShouldNotBeFound("grau.specified=false");
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByGrauContainsSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where grau contains DEFAULT_GRAU
        defaultFormacaoDocenteShouldBeFound("grau.contains=" + DEFAULT_GRAU);

        // Get all the formacaoDocenteList where grau contains UPDATED_GRAU
        defaultFormacaoDocenteShouldNotBeFound("grau.contains=" + UPDATED_GRAU);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByGrauNotContainsSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where grau does not contain DEFAULT_GRAU
        defaultFormacaoDocenteShouldNotBeFound("grau.doesNotContain=" + DEFAULT_GRAU);

        // Get all the formacaoDocenteList where grau does not contain UPDATED_GRAU
        defaultFormacaoDocenteShouldBeFound("grau.doesNotContain=" + UPDATED_GRAU);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where inicio equals to DEFAULT_INICIO
        defaultFormacaoDocenteShouldBeFound("inicio.equals=" + DEFAULT_INICIO);

        // Get all the formacaoDocenteList where inicio equals to UPDATED_INICIO
        defaultFormacaoDocenteShouldNotBeFound("inicio.equals=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInicioIsInShouldWork() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where inicio in DEFAULT_INICIO or UPDATED_INICIO
        defaultFormacaoDocenteShouldBeFound("inicio.in=" + DEFAULT_INICIO + "," + UPDATED_INICIO);

        // Get all the formacaoDocenteList where inicio equals to UPDATED_INICIO
        defaultFormacaoDocenteShouldNotBeFound("inicio.in=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where inicio is not null
        defaultFormacaoDocenteShouldBeFound("inicio.specified=true");

        // Get all the formacaoDocenteList where inicio is null
        defaultFormacaoDocenteShouldNotBeFound("inicio.specified=false");
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInicioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where inicio is greater than or equal to DEFAULT_INICIO
        defaultFormacaoDocenteShouldBeFound("inicio.greaterThanOrEqual=" + DEFAULT_INICIO);

        // Get all the formacaoDocenteList where inicio is greater than or equal to UPDATED_INICIO
        defaultFormacaoDocenteShouldNotBeFound("inicio.greaterThanOrEqual=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInicioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where inicio is less than or equal to DEFAULT_INICIO
        defaultFormacaoDocenteShouldBeFound("inicio.lessThanOrEqual=" + DEFAULT_INICIO);

        // Get all the formacaoDocenteList where inicio is less than or equal to SMALLER_INICIO
        defaultFormacaoDocenteShouldNotBeFound("inicio.lessThanOrEqual=" + SMALLER_INICIO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInicioIsLessThanSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where inicio is less than DEFAULT_INICIO
        defaultFormacaoDocenteShouldNotBeFound("inicio.lessThan=" + DEFAULT_INICIO);

        // Get all the formacaoDocenteList where inicio is less than UPDATED_INICIO
        defaultFormacaoDocenteShouldBeFound("inicio.lessThan=" + UPDATED_INICIO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByInicioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where inicio is greater than DEFAULT_INICIO
        defaultFormacaoDocenteShouldNotBeFound("inicio.greaterThan=" + DEFAULT_INICIO);

        // Get all the formacaoDocenteList where inicio is greater than SMALLER_INICIO
        defaultFormacaoDocenteShouldBeFound("inicio.greaterThan=" + SMALLER_INICIO);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByFimIsEqualToSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where fim equals to DEFAULT_FIM
        defaultFormacaoDocenteShouldBeFound("fim.equals=" + DEFAULT_FIM);

        // Get all the formacaoDocenteList where fim equals to UPDATED_FIM
        defaultFormacaoDocenteShouldNotBeFound("fim.equals=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByFimIsInShouldWork() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where fim in DEFAULT_FIM or UPDATED_FIM
        defaultFormacaoDocenteShouldBeFound("fim.in=" + DEFAULT_FIM + "," + UPDATED_FIM);

        // Get all the formacaoDocenteList where fim equals to UPDATED_FIM
        defaultFormacaoDocenteShouldNotBeFound("fim.in=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByFimIsNullOrNotNull() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where fim is not null
        defaultFormacaoDocenteShouldBeFound("fim.specified=true");

        // Get all the formacaoDocenteList where fim is null
        defaultFormacaoDocenteShouldNotBeFound("fim.specified=false");
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByFimIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where fim is greater than or equal to DEFAULT_FIM
        defaultFormacaoDocenteShouldBeFound("fim.greaterThanOrEqual=" + DEFAULT_FIM);

        // Get all the formacaoDocenteList where fim is greater than or equal to UPDATED_FIM
        defaultFormacaoDocenteShouldNotBeFound("fim.greaterThanOrEqual=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByFimIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where fim is less than or equal to DEFAULT_FIM
        defaultFormacaoDocenteShouldBeFound("fim.lessThanOrEqual=" + DEFAULT_FIM);

        // Get all the formacaoDocenteList where fim is less than or equal to SMALLER_FIM
        defaultFormacaoDocenteShouldNotBeFound("fim.lessThanOrEqual=" + SMALLER_FIM);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByFimIsLessThanSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where fim is less than DEFAULT_FIM
        defaultFormacaoDocenteShouldNotBeFound("fim.lessThan=" + DEFAULT_FIM);

        // Get all the formacaoDocenteList where fim is less than UPDATED_FIM
        defaultFormacaoDocenteShouldBeFound("fim.lessThan=" + UPDATED_FIM);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByFimIsGreaterThanSomething() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        // Get all the formacaoDocenteList where fim is greater than DEFAULT_FIM
        defaultFormacaoDocenteShouldNotBeFound("fim.greaterThan=" + DEFAULT_FIM);

        // Get all the formacaoDocenteList where fim is greater than SMALLER_FIM
        defaultFormacaoDocenteShouldBeFound("fim.greaterThan=" + SMALLER_FIM);
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByGrauAcademicoIsEqualToSomething() throws Exception {
        LookupItem grauAcademico;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            formacaoDocenteRepository.saveAndFlush(formacaoDocente);
            grauAcademico = LookupItemResourceIT.createEntity(em);
        } else {
            grauAcademico = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(grauAcademico);
        em.flush();
        formacaoDocente.setGrauAcademico(grauAcademico);
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);
        Long grauAcademicoId = grauAcademico.getId();

        // Get all the formacaoDocenteList where grauAcademico equals to grauAcademicoId
        defaultFormacaoDocenteShouldBeFound("grauAcademicoId.equals=" + grauAcademicoId);

        // Get all the formacaoDocenteList where grauAcademico equals to (grauAcademicoId + 1)
        defaultFormacaoDocenteShouldNotBeFound("grauAcademicoId.equals=" + (grauAcademicoId + 1));
    }

    @Test
    @Transactional
    void getAllFormacaoDocentesByDocenteIsEqualToSomething() throws Exception {
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            formacaoDocenteRepository.saveAndFlush(formacaoDocente);
            docente = DocenteResourceIT.createEntity(em);
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(docente);
        em.flush();
        formacaoDocente.setDocente(docente);
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);
        Long docenteId = docente.getId();

        // Get all the formacaoDocenteList where docente equals to docenteId
        defaultFormacaoDocenteShouldBeFound("docenteId.equals=" + docenteId);

        // Get all the formacaoDocenteList where docente equals to (docenteId + 1)
        defaultFormacaoDocenteShouldNotBeFound("docenteId.equals=" + (docenteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFormacaoDocenteShouldBeFound(String filter) throws Exception {
        restFormacaoDocenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formacaoDocente.getId().intValue())))
            .andExpect(jsonPath("$.[*].instituicaoEnsino").value(hasItem(DEFAULT_INSTITUICAO_ENSINO)))
            .andExpect(jsonPath("$.[*].areaFormacao").value(hasItem(DEFAULT_AREA_FORMACAO)))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)))
            .andExpect(jsonPath("$.[*].especialidade").value(hasItem(DEFAULT_ESPECIALIDADE)))
            .andExpect(jsonPath("$.[*].grau").value(hasItem(DEFAULT_GRAU)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(DEFAULT_FIM.toString())))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))));

        // Check, that the count call also returns 1
        restFormacaoDocenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFormacaoDocenteShouldNotBeFound(String filter) throws Exception {
        restFormacaoDocenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFormacaoDocenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFormacaoDocente() throws Exception {
        // Get the formacaoDocente
        restFormacaoDocenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormacaoDocente() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        int databaseSizeBeforeUpdate = formacaoDocenteRepository.findAll().size();

        // Update the formacaoDocente
        FormacaoDocente updatedFormacaoDocente = formacaoDocenteRepository.findById(formacaoDocente.getId()).get();
        // Disconnect from session so that the updates on updatedFormacaoDocente are not directly saved in db
        em.detach(updatedFormacaoDocente);
        updatedFormacaoDocente
            .instituicaoEnsino(UPDATED_INSTITUICAO_ENSINO)
            .areaFormacao(UPDATED_AREA_FORMACAO)
            .curso(UPDATED_CURSO)
            .especialidade(UPDATED_ESPECIALIDADE)
            .grau(UPDATED_GRAU)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE);
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(updatedFormacaoDocente);

        restFormacaoDocenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formacaoDocenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isOk());

        // Validate the FormacaoDocente in the database
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeUpdate);
        FormacaoDocente testFormacaoDocente = formacaoDocenteList.get(formacaoDocenteList.size() - 1);
        assertThat(testFormacaoDocente.getInstituicaoEnsino()).isEqualTo(UPDATED_INSTITUICAO_ENSINO);
        assertThat(testFormacaoDocente.getAreaFormacao()).isEqualTo(UPDATED_AREA_FORMACAO);
        assertThat(testFormacaoDocente.getCurso()).isEqualTo(UPDATED_CURSO);
        assertThat(testFormacaoDocente.getEspecialidade()).isEqualTo(UPDATED_ESPECIALIDADE);
        assertThat(testFormacaoDocente.getGrau()).isEqualTo(UPDATED_GRAU);
        assertThat(testFormacaoDocente.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testFormacaoDocente.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testFormacaoDocente.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testFormacaoDocente.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFormacaoDocente() throws Exception {
        int databaseSizeBeforeUpdate = formacaoDocenteRepository.findAll().size();
        formacaoDocente.setId(count.incrementAndGet());

        // Create the FormacaoDocente
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormacaoDocenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formacaoDocenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormacaoDocente in the database
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormacaoDocente() throws Exception {
        int databaseSizeBeforeUpdate = formacaoDocenteRepository.findAll().size();
        formacaoDocente.setId(count.incrementAndGet());

        // Create the FormacaoDocente
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormacaoDocenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormacaoDocente in the database
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormacaoDocente() throws Exception {
        int databaseSizeBeforeUpdate = formacaoDocenteRepository.findAll().size();
        formacaoDocente.setId(count.incrementAndGet());

        // Create the FormacaoDocente
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormacaoDocenteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormacaoDocente in the database
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormacaoDocenteWithPatch() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        int databaseSizeBeforeUpdate = formacaoDocenteRepository.findAll().size();

        // Update the formacaoDocente using partial update
        FormacaoDocente partialUpdatedFormacaoDocente = new FormacaoDocente();
        partialUpdatedFormacaoDocente.setId(formacaoDocente.getId());

        partialUpdatedFormacaoDocente
            .instituicaoEnsino(UPDATED_INSTITUICAO_ENSINO)
            .grau(UPDATED_GRAU)
            .inicio(UPDATED_INICIO)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE);

        restFormacaoDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormacaoDocente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormacaoDocente))
            )
            .andExpect(status().isOk());

        // Validate the FormacaoDocente in the database
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeUpdate);
        FormacaoDocente testFormacaoDocente = formacaoDocenteList.get(formacaoDocenteList.size() - 1);
        assertThat(testFormacaoDocente.getInstituicaoEnsino()).isEqualTo(UPDATED_INSTITUICAO_ENSINO);
        assertThat(testFormacaoDocente.getAreaFormacao()).isEqualTo(DEFAULT_AREA_FORMACAO);
        assertThat(testFormacaoDocente.getCurso()).isEqualTo(DEFAULT_CURSO);
        assertThat(testFormacaoDocente.getEspecialidade()).isEqualTo(DEFAULT_ESPECIALIDADE);
        assertThat(testFormacaoDocente.getGrau()).isEqualTo(UPDATED_GRAU);
        assertThat(testFormacaoDocente.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testFormacaoDocente.getFim()).isEqualTo(DEFAULT_FIM);
        assertThat(testFormacaoDocente.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testFormacaoDocente.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFormacaoDocenteWithPatch() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        int databaseSizeBeforeUpdate = formacaoDocenteRepository.findAll().size();

        // Update the formacaoDocente using partial update
        FormacaoDocente partialUpdatedFormacaoDocente = new FormacaoDocente();
        partialUpdatedFormacaoDocente.setId(formacaoDocente.getId());

        partialUpdatedFormacaoDocente
            .instituicaoEnsino(UPDATED_INSTITUICAO_ENSINO)
            .areaFormacao(UPDATED_AREA_FORMACAO)
            .curso(UPDATED_CURSO)
            .especialidade(UPDATED_ESPECIALIDADE)
            .grau(UPDATED_GRAU)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE);

        restFormacaoDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormacaoDocente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormacaoDocente))
            )
            .andExpect(status().isOk());

        // Validate the FormacaoDocente in the database
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeUpdate);
        FormacaoDocente testFormacaoDocente = formacaoDocenteList.get(formacaoDocenteList.size() - 1);
        assertThat(testFormacaoDocente.getInstituicaoEnsino()).isEqualTo(UPDATED_INSTITUICAO_ENSINO);
        assertThat(testFormacaoDocente.getAreaFormacao()).isEqualTo(UPDATED_AREA_FORMACAO);
        assertThat(testFormacaoDocente.getCurso()).isEqualTo(UPDATED_CURSO);
        assertThat(testFormacaoDocente.getEspecialidade()).isEqualTo(UPDATED_ESPECIALIDADE);
        assertThat(testFormacaoDocente.getGrau()).isEqualTo(UPDATED_GRAU);
        assertThat(testFormacaoDocente.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testFormacaoDocente.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testFormacaoDocente.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testFormacaoDocente.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFormacaoDocente() throws Exception {
        int databaseSizeBeforeUpdate = formacaoDocenteRepository.findAll().size();
        formacaoDocente.setId(count.incrementAndGet());

        // Create the FormacaoDocente
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormacaoDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formacaoDocenteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormacaoDocente in the database
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormacaoDocente() throws Exception {
        int databaseSizeBeforeUpdate = formacaoDocenteRepository.findAll().size();
        formacaoDocente.setId(count.incrementAndGet());

        // Create the FormacaoDocente
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormacaoDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormacaoDocente in the database
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormacaoDocente() throws Exception {
        int databaseSizeBeforeUpdate = formacaoDocenteRepository.findAll().size();
        formacaoDocente.setId(count.incrementAndGet());

        // Create the FormacaoDocente
        FormacaoDocenteDTO formacaoDocenteDTO = formacaoDocenteMapper.toDto(formacaoDocente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormacaoDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formacaoDocenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormacaoDocente in the database
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormacaoDocente() throws Exception {
        // Initialize the database
        formacaoDocenteRepository.saveAndFlush(formacaoDocente);

        int databaseSizeBeforeDelete = formacaoDocenteRepository.findAll().size();

        // Delete the formacaoDocente
        restFormacaoDocenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, formacaoDocente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormacaoDocente> formacaoDocenteList = formacaoDocenteRepository.findAll();
        assertThat(formacaoDocenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
