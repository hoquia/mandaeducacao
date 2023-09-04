package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.DetalhePlanoAula;
import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.Licao;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.domain.enumeration.EstadoLicao;
import com.ravunana.longonkelo.domain.enumeration.TipoAula;
import com.ravunana.longonkelo.repository.PlanoAulaRepository;
import com.ravunana.longonkelo.service.PlanoAulaService;
import com.ravunana.longonkelo.service.criteria.PlanoAulaCriteria;
import com.ravunana.longonkelo.service.dto.PlanoAulaDTO;
import com.ravunana.longonkelo.service.mapper.PlanoAulaMapper;
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
 * Integration tests for the {@link PlanoAulaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlanoAulaResourceIT {

    private static final TipoAula DEFAULT_TIPO_AULA = TipoAula.PRATICA;
    private static final TipoAula UPDATED_TIPO_AULA = TipoAula.TEORICA;

    private static final Integer DEFAULT_SEMANA_LECTIVA = 0;
    private static final Integer UPDATED_SEMANA_LECTIVA = 1;
    private static final Integer SMALLER_SEMANA_LECTIVA = 0 - 1;

    private static final String DEFAULT_PERFIL_ENTRADA = "AAAAAAAAAA";
    private static final String UPDATED_PERFIL_ENTRADA = "BBBBBBBBBB";

    private static final String DEFAULT_PERFIL_SAIDA = "AAAAAAAAAA";
    private static final String UPDATED_PERFIL_SAIDA = "BBBBBBBBBB";

    private static final String DEFAULT_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIVO_GERAL = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVO_GERAL = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIVOS_ESPECIFICOS = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVOS_ESPECIFICOS = "BBBBBBBBBB";

    private static final Integer DEFAULT_TEMPO_TOTAL_LICAO = 0;
    private static final Integer UPDATED_TEMPO_TOTAL_LICAO = 1;
    private static final Integer SMALLER_TEMPO_TOTAL_LICAO = 0 - 1;

    private static final EstadoLicao DEFAULT_ESTADO = EstadoLicao.ADIADA;
    private static final EstadoLicao UPDATED_ESTADO = EstadoLicao.CANCELADA;

    private static final String ENTITY_API_URL = "/api/plano-aulas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanoAulaRepository planoAulaRepository;

    @Mock
    private PlanoAulaRepository planoAulaRepositoryMock;

    @Autowired
    private PlanoAulaMapper planoAulaMapper;

    @Mock
    private PlanoAulaService planoAulaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoAulaMockMvc;

    private PlanoAula planoAula;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoAula createEntity(EntityManager em) {
        PlanoAula planoAula = new PlanoAula()
            .tipoAula(DEFAULT_TIPO_AULA)
            .semanaLectiva(DEFAULT_SEMANA_LECTIVA)
            .perfilEntrada(DEFAULT_PERFIL_ENTRADA)
            .perfilSaida(DEFAULT_PERFIL_SAIDA)
            .assunto(DEFAULT_ASSUNTO)
            .objectivoGeral(DEFAULT_OBJECTIVO_GERAL)
            .objectivosEspecificos(DEFAULT_OBJECTIVOS_ESPECIFICOS)
            .tempoTotalLicao(DEFAULT_TEMPO_TOTAL_LICAO)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        planoAula.setUnidadeTematica(lookupItem);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        planoAula.setTurma(turma);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        planoAula.setDocente(docente);
        // Add required entity
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            disciplinaCurricular = DisciplinaCurricularResourceIT.createEntity(em);
            em.persist(disciplinaCurricular);
            em.flush();
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        planoAula.setDisciplinaCurricular(disciplinaCurricular);
        return planoAula;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoAula createUpdatedEntity(EntityManager em) {
        PlanoAula planoAula = new PlanoAula()
            .tipoAula(UPDATED_TIPO_AULA)
            .semanaLectiva(UPDATED_SEMANA_LECTIVA)
            .perfilEntrada(UPDATED_PERFIL_ENTRADA)
            .perfilSaida(UPDATED_PERFIL_SAIDA)
            .assunto(UPDATED_ASSUNTO)
            .objectivoGeral(UPDATED_OBJECTIVO_GERAL)
            .objectivosEspecificos(UPDATED_OBJECTIVOS_ESPECIFICOS)
            .tempoTotalLicao(UPDATED_TEMPO_TOTAL_LICAO)
            .estado(UPDATED_ESTADO);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createUpdatedEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        planoAula.setUnidadeTematica(lookupItem);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createUpdatedEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        planoAula.setTurma(turma);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createUpdatedEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        planoAula.setDocente(docente);
        // Add required entity
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            disciplinaCurricular = DisciplinaCurricularResourceIT.createUpdatedEntity(em);
            em.persist(disciplinaCurricular);
            em.flush();
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        planoAula.setDisciplinaCurricular(disciplinaCurricular);
        return planoAula;
    }

    @BeforeEach
    public void initTest() {
        planoAula = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanoAula() throws Exception {
        int databaseSizeBeforeCreate = planoAulaRepository.findAll().size();
        // Create the PlanoAula
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);
        restPlanoAulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoAulaDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoAula in the database
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoAula testPlanoAula = planoAulaList.get(planoAulaList.size() - 1);
        assertThat(testPlanoAula.getTipoAula()).isEqualTo(DEFAULT_TIPO_AULA);
        assertThat(testPlanoAula.getSemanaLectiva()).isEqualTo(DEFAULT_SEMANA_LECTIVA);
        assertThat(testPlanoAula.getPerfilEntrada()).isEqualTo(DEFAULT_PERFIL_ENTRADA);
        assertThat(testPlanoAula.getPerfilSaida()).isEqualTo(DEFAULT_PERFIL_SAIDA);
        assertThat(testPlanoAula.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testPlanoAula.getObjectivoGeral()).isEqualTo(DEFAULT_OBJECTIVO_GERAL);
        assertThat(testPlanoAula.getObjectivosEspecificos()).isEqualTo(DEFAULT_OBJECTIVOS_ESPECIFICOS);
        assertThat(testPlanoAula.getTempoTotalLicao()).isEqualTo(DEFAULT_TEMPO_TOTAL_LICAO);
        assertThat(testPlanoAula.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    void createPlanoAulaWithExistingId() throws Exception {
        // Create the PlanoAula with an existing ID
        planoAula.setId(1L);
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);

        int databaseSizeBeforeCreate = planoAulaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoAulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoAulaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoAula in the database
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoAulaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoAulaRepository.findAll().size();
        // set the field null
        planoAula.setTipoAula(null);

        // Create the PlanoAula, which fails.
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);

        restPlanoAulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoAulaDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSemanaLectivaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoAulaRepository.findAll().size();
        // set the field null
        planoAula.setSemanaLectiva(null);

        // Create the PlanoAula, which fails.
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);

        restPlanoAulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoAulaDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssuntoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoAulaRepository.findAll().size();
        // set the field null
        planoAula.setAssunto(null);

        // Create the PlanoAula, which fails.
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);

        restPlanoAulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoAulaDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoAulaRepository.findAll().size();
        // set the field null
        planoAula.setEstado(null);

        // Create the PlanoAula, which fails.
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);

        restPlanoAulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoAulaDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanoAulas() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList
        restPlanoAulaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoAula.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoAula").value(hasItem(DEFAULT_TIPO_AULA.toString())))
            .andExpect(jsonPath("$.[*].semanaLectiva").value(hasItem(DEFAULT_SEMANA_LECTIVA)))
            .andExpect(jsonPath("$.[*].perfilEntrada").value(hasItem(DEFAULT_PERFIL_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].perfilSaida").value(hasItem(DEFAULT_PERFIL_SAIDA.toString())))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].objectivoGeral").value(hasItem(DEFAULT_OBJECTIVO_GERAL.toString())))
            .andExpect(jsonPath("$.[*].objectivosEspecificos").value(hasItem(DEFAULT_OBJECTIVOS_ESPECIFICOS.toString())))
            .andExpect(jsonPath("$.[*].tempoTotalLicao").value(hasItem(DEFAULT_TEMPO_TOTAL_LICAO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlanoAulasWithEagerRelationshipsIsEnabled() throws Exception {
        when(planoAulaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlanoAulaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(planoAulaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlanoAulasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(planoAulaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlanoAulaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(planoAulaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPlanoAula() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get the planoAula
        restPlanoAulaMockMvc
            .perform(get(ENTITY_API_URL_ID, planoAula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoAula.getId().intValue()))
            .andExpect(jsonPath("$.tipoAula").value(DEFAULT_TIPO_AULA.toString()))
            .andExpect(jsonPath("$.semanaLectiva").value(DEFAULT_SEMANA_LECTIVA))
            .andExpect(jsonPath("$.perfilEntrada").value(DEFAULT_PERFIL_ENTRADA.toString()))
            .andExpect(jsonPath("$.perfilSaida").value(DEFAULT_PERFIL_SAIDA.toString()))
            .andExpect(jsonPath("$.assunto").value(DEFAULT_ASSUNTO))
            .andExpect(jsonPath("$.objectivoGeral").value(DEFAULT_OBJECTIVO_GERAL.toString()))
            .andExpect(jsonPath("$.objectivosEspecificos").value(DEFAULT_OBJECTIVOS_ESPECIFICOS.toString()))
            .andExpect(jsonPath("$.tempoTotalLicao").value(DEFAULT_TEMPO_TOTAL_LICAO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    void getPlanoAulasByIdFiltering() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        Long id = planoAula.getId();

        defaultPlanoAulaShouldBeFound("id.equals=" + id);
        defaultPlanoAulaShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoAulaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoAulaShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoAulaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoAulaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByTipoAulaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where tipoAula equals to DEFAULT_TIPO_AULA
        defaultPlanoAulaShouldBeFound("tipoAula.equals=" + DEFAULT_TIPO_AULA);

        // Get all the planoAulaList where tipoAula equals to UPDATED_TIPO_AULA
        defaultPlanoAulaShouldNotBeFound("tipoAula.equals=" + UPDATED_TIPO_AULA);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByTipoAulaIsInShouldWork() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where tipoAula in DEFAULT_TIPO_AULA or UPDATED_TIPO_AULA
        defaultPlanoAulaShouldBeFound("tipoAula.in=" + DEFAULT_TIPO_AULA + "," + UPDATED_TIPO_AULA);

        // Get all the planoAulaList where tipoAula equals to UPDATED_TIPO_AULA
        defaultPlanoAulaShouldNotBeFound("tipoAula.in=" + UPDATED_TIPO_AULA);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByTipoAulaIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where tipoAula is not null
        defaultPlanoAulaShouldBeFound("tipoAula.specified=true");

        // Get all the planoAulaList where tipoAula is null
        defaultPlanoAulaShouldNotBeFound("tipoAula.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAulasBySemanaLectivaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where semanaLectiva equals to DEFAULT_SEMANA_LECTIVA
        defaultPlanoAulaShouldBeFound("semanaLectiva.equals=" + DEFAULT_SEMANA_LECTIVA);

        // Get all the planoAulaList where semanaLectiva equals to UPDATED_SEMANA_LECTIVA
        defaultPlanoAulaShouldNotBeFound("semanaLectiva.equals=" + UPDATED_SEMANA_LECTIVA);
    }

    @Test
    @Transactional
    void getAllPlanoAulasBySemanaLectivaIsInShouldWork() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where semanaLectiva in DEFAULT_SEMANA_LECTIVA or UPDATED_SEMANA_LECTIVA
        defaultPlanoAulaShouldBeFound("semanaLectiva.in=" + DEFAULT_SEMANA_LECTIVA + "," + UPDATED_SEMANA_LECTIVA);

        // Get all the planoAulaList where semanaLectiva equals to UPDATED_SEMANA_LECTIVA
        defaultPlanoAulaShouldNotBeFound("semanaLectiva.in=" + UPDATED_SEMANA_LECTIVA);
    }

    @Test
    @Transactional
    void getAllPlanoAulasBySemanaLectivaIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where semanaLectiva is not null
        defaultPlanoAulaShouldBeFound("semanaLectiva.specified=true");

        // Get all the planoAulaList where semanaLectiva is null
        defaultPlanoAulaShouldNotBeFound("semanaLectiva.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAulasBySemanaLectivaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where semanaLectiva is greater than or equal to DEFAULT_SEMANA_LECTIVA
        defaultPlanoAulaShouldBeFound("semanaLectiva.greaterThanOrEqual=" + DEFAULT_SEMANA_LECTIVA);

        // Get all the planoAulaList where semanaLectiva is greater than or equal to UPDATED_SEMANA_LECTIVA
        defaultPlanoAulaShouldNotBeFound("semanaLectiva.greaterThanOrEqual=" + UPDATED_SEMANA_LECTIVA);
    }

    @Test
    @Transactional
    void getAllPlanoAulasBySemanaLectivaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where semanaLectiva is less than or equal to DEFAULT_SEMANA_LECTIVA
        defaultPlanoAulaShouldBeFound("semanaLectiva.lessThanOrEqual=" + DEFAULT_SEMANA_LECTIVA);

        // Get all the planoAulaList where semanaLectiva is less than or equal to SMALLER_SEMANA_LECTIVA
        defaultPlanoAulaShouldNotBeFound("semanaLectiva.lessThanOrEqual=" + SMALLER_SEMANA_LECTIVA);
    }

    @Test
    @Transactional
    void getAllPlanoAulasBySemanaLectivaIsLessThanSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where semanaLectiva is less than DEFAULT_SEMANA_LECTIVA
        defaultPlanoAulaShouldNotBeFound("semanaLectiva.lessThan=" + DEFAULT_SEMANA_LECTIVA);

        // Get all the planoAulaList where semanaLectiva is less than UPDATED_SEMANA_LECTIVA
        defaultPlanoAulaShouldBeFound("semanaLectiva.lessThan=" + UPDATED_SEMANA_LECTIVA);
    }

    @Test
    @Transactional
    void getAllPlanoAulasBySemanaLectivaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where semanaLectiva is greater than DEFAULT_SEMANA_LECTIVA
        defaultPlanoAulaShouldNotBeFound("semanaLectiva.greaterThan=" + DEFAULT_SEMANA_LECTIVA);

        // Get all the planoAulaList where semanaLectiva is greater than SMALLER_SEMANA_LECTIVA
        defaultPlanoAulaShouldBeFound("semanaLectiva.greaterThan=" + SMALLER_SEMANA_LECTIVA);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByAssuntoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where assunto equals to DEFAULT_ASSUNTO
        defaultPlanoAulaShouldBeFound("assunto.equals=" + DEFAULT_ASSUNTO);

        // Get all the planoAulaList where assunto equals to UPDATED_ASSUNTO
        defaultPlanoAulaShouldNotBeFound("assunto.equals=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByAssuntoIsInShouldWork() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where assunto in DEFAULT_ASSUNTO or UPDATED_ASSUNTO
        defaultPlanoAulaShouldBeFound("assunto.in=" + DEFAULT_ASSUNTO + "," + UPDATED_ASSUNTO);

        // Get all the planoAulaList where assunto equals to UPDATED_ASSUNTO
        defaultPlanoAulaShouldNotBeFound("assunto.in=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByAssuntoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where assunto is not null
        defaultPlanoAulaShouldBeFound("assunto.specified=true");

        // Get all the planoAulaList where assunto is null
        defaultPlanoAulaShouldNotBeFound("assunto.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAulasByAssuntoContainsSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where assunto contains DEFAULT_ASSUNTO
        defaultPlanoAulaShouldBeFound("assunto.contains=" + DEFAULT_ASSUNTO);

        // Get all the planoAulaList where assunto contains UPDATED_ASSUNTO
        defaultPlanoAulaShouldNotBeFound("assunto.contains=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByAssuntoNotContainsSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where assunto does not contain DEFAULT_ASSUNTO
        defaultPlanoAulaShouldNotBeFound("assunto.doesNotContain=" + DEFAULT_ASSUNTO);

        // Get all the planoAulaList where assunto does not contain UPDATED_ASSUNTO
        defaultPlanoAulaShouldBeFound("assunto.doesNotContain=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByTempoTotalLicaoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where tempoTotalLicao equals to DEFAULT_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldBeFound("tempoTotalLicao.equals=" + DEFAULT_TEMPO_TOTAL_LICAO);

        // Get all the planoAulaList where tempoTotalLicao equals to UPDATED_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldNotBeFound("tempoTotalLicao.equals=" + UPDATED_TEMPO_TOTAL_LICAO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByTempoTotalLicaoIsInShouldWork() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where tempoTotalLicao in DEFAULT_TEMPO_TOTAL_LICAO or UPDATED_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldBeFound("tempoTotalLicao.in=" + DEFAULT_TEMPO_TOTAL_LICAO + "," + UPDATED_TEMPO_TOTAL_LICAO);

        // Get all the planoAulaList where tempoTotalLicao equals to UPDATED_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldNotBeFound("tempoTotalLicao.in=" + UPDATED_TEMPO_TOTAL_LICAO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByTempoTotalLicaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where tempoTotalLicao is not null
        defaultPlanoAulaShouldBeFound("tempoTotalLicao.specified=true");

        // Get all the planoAulaList where tempoTotalLicao is null
        defaultPlanoAulaShouldNotBeFound("tempoTotalLicao.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAulasByTempoTotalLicaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where tempoTotalLicao is greater than or equal to DEFAULT_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldBeFound("tempoTotalLicao.greaterThanOrEqual=" + DEFAULT_TEMPO_TOTAL_LICAO);

        // Get all the planoAulaList where tempoTotalLicao is greater than or equal to UPDATED_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldNotBeFound("tempoTotalLicao.greaterThanOrEqual=" + UPDATED_TEMPO_TOTAL_LICAO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByTempoTotalLicaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where tempoTotalLicao is less than or equal to DEFAULT_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldBeFound("tempoTotalLicao.lessThanOrEqual=" + DEFAULT_TEMPO_TOTAL_LICAO);

        // Get all the planoAulaList where tempoTotalLicao is less than or equal to SMALLER_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldNotBeFound("tempoTotalLicao.lessThanOrEqual=" + SMALLER_TEMPO_TOTAL_LICAO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByTempoTotalLicaoIsLessThanSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where tempoTotalLicao is less than DEFAULT_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldNotBeFound("tempoTotalLicao.lessThan=" + DEFAULT_TEMPO_TOTAL_LICAO);

        // Get all the planoAulaList where tempoTotalLicao is less than UPDATED_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldBeFound("tempoTotalLicao.lessThan=" + UPDATED_TEMPO_TOTAL_LICAO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByTempoTotalLicaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where tempoTotalLicao is greater than DEFAULT_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldNotBeFound("tempoTotalLicao.greaterThan=" + DEFAULT_TEMPO_TOTAL_LICAO);

        // Get all the planoAulaList where tempoTotalLicao is greater than SMALLER_TEMPO_TOTAL_LICAO
        defaultPlanoAulaShouldBeFound("tempoTotalLicao.greaterThan=" + SMALLER_TEMPO_TOTAL_LICAO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where estado equals to DEFAULT_ESTADO
        defaultPlanoAulaShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the planoAulaList where estado equals to UPDATED_ESTADO
        defaultPlanoAulaShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultPlanoAulaShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the planoAulaList where estado equals to UPDATED_ESTADO
        defaultPlanoAulaShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllPlanoAulasByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        // Get all the planoAulaList where estado is not null
        defaultPlanoAulaShouldBeFound("estado.specified=true");

        // Get all the planoAulaList where estado is null
        defaultPlanoAulaShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAulasByDetalhesIsEqualToSomething() throws Exception {
        DetalhePlanoAula detalhes;
        if (TestUtil.findAll(em, DetalhePlanoAula.class).isEmpty()) {
            planoAulaRepository.saveAndFlush(planoAula);
            detalhes = DetalhePlanoAulaResourceIT.createEntity(em);
        } else {
            detalhes = TestUtil.findAll(em, DetalhePlanoAula.class).get(0);
        }
        em.persist(detalhes);
        em.flush();
        planoAula.addDetalhes(detalhes);
        planoAulaRepository.saveAndFlush(planoAula);
        Long detalhesId = detalhes.getId();

        // Get all the planoAulaList where detalhes equals to detalhesId
        defaultPlanoAulaShouldBeFound("detalhesId.equals=" + detalhesId);

        // Get all the planoAulaList where detalhes equals to (detalhesId + 1)
        defaultPlanoAulaShouldNotBeFound("detalhesId.equals=" + (detalhesId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoAulasByLicaoIsEqualToSomething() throws Exception {
        Licao licao;
        if (TestUtil.findAll(em, Licao.class).isEmpty()) {
            planoAulaRepository.saveAndFlush(planoAula);
            licao = LicaoResourceIT.createEntity(em);
        } else {
            licao = TestUtil.findAll(em, Licao.class).get(0);
        }
        em.persist(licao);
        em.flush();
        planoAula.addLicao(licao);
        planoAulaRepository.saveAndFlush(planoAula);
        Long licaoId = licao.getId();

        // Get all the planoAulaList where licao equals to licaoId
        defaultPlanoAulaShouldBeFound("licaoId.equals=" + licaoId);

        // Get all the planoAulaList where licao equals to (licaoId + 1)
        defaultPlanoAulaShouldNotBeFound("licaoId.equals=" + (licaoId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoAulasByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            planoAulaRepository.saveAndFlush(planoAula);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        planoAula.addAnoLectivo(anoLectivo);
        planoAulaRepository.saveAndFlush(planoAula);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the planoAulaList where anoLectivo equals to anoLectivoId
        defaultPlanoAulaShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the planoAulaList where anoLectivo equals to (anoLectivoId + 1)
        defaultPlanoAulaShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoAulasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            planoAulaRepository.saveAndFlush(planoAula);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        planoAula.setUtilizador(utilizador);
        planoAulaRepository.saveAndFlush(planoAula);
        Long utilizadorId = utilizador.getId();

        // Get all the planoAulaList where utilizador equals to utilizadorId
        defaultPlanoAulaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the planoAulaList where utilizador equals to (utilizadorId + 1)
        defaultPlanoAulaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoAulasByUnidadeTematicaIsEqualToSomething() throws Exception {
        LookupItem unidadeTematica;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            planoAulaRepository.saveAndFlush(planoAula);
            unidadeTematica = LookupItemResourceIT.createEntity(em);
        } else {
            unidadeTematica = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(unidadeTematica);
        em.flush();
        planoAula.setUnidadeTematica(unidadeTematica);
        planoAulaRepository.saveAndFlush(planoAula);
        Long unidadeTematicaId = unidadeTematica.getId();

        // Get all the planoAulaList where unidadeTematica equals to unidadeTematicaId
        defaultPlanoAulaShouldBeFound("unidadeTematicaId.equals=" + unidadeTematicaId);

        // Get all the planoAulaList where unidadeTematica equals to (unidadeTematicaId + 1)
        defaultPlanoAulaShouldNotBeFound("unidadeTematicaId.equals=" + (unidadeTematicaId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoAulasBySubUnidadeTematicaIsEqualToSomething() throws Exception {
        LookupItem subUnidadeTematica;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            planoAulaRepository.saveAndFlush(planoAula);
            subUnidadeTematica = LookupItemResourceIT.createEntity(em);
        } else {
            subUnidadeTematica = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(subUnidadeTematica);
        em.flush();
        planoAula.setSubUnidadeTematica(subUnidadeTematica);
        planoAulaRepository.saveAndFlush(planoAula);
        Long subUnidadeTematicaId = subUnidadeTematica.getId();

        // Get all the planoAulaList where subUnidadeTematica equals to subUnidadeTematicaId
        defaultPlanoAulaShouldBeFound("subUnidadeTematicaId.equals=" + subUnidadeTematicaId);

        // Get all the planoAulaList where subUnidadeTematica equals to (subUnidadeTematicaId + 1)
        defaultPlanoAulaShouldNotBeFound("subUnidadeTematicaId.equals=" + (subUnidadeTematicaId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoAulasByTurmaIsEqualToSomething() throws Exception {
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            planoAulaRepository.saveAndFlush(planoAula);
            turma = TurmaResourceIT.createEntity(em);
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(turma);
        em.flush();
        planoAula.setTurma(turma);
        planoAulaRepository.saveAndFlush(planoAula);
        Long turmaId = turma.getId();

        // Get all the planoAulaList where turma equals to turmaId
        defaultPlanoAulaShouldBeFound("turmaId.equals=" + turmaId);

        // Get all the planoAulaList where turma equals to (turmaId + 1)
        defaultPlanoAulaShouldNotBeFound("turmaId.equals=" + (turmaId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoAulasByDocenteIsEqualToSomething() throws Exception {
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            planoAulaRepository.saveAndFlush(planoAula);
            docente = DocenteResourceIT.createEntity(em);
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(docente);
        em.flush();
        planoAula.setDocente(docente);
        planoAulaRepository.saveAndFlush(planoAula);
        Long docenteId = docente.getId();

        // Get all the planoAulaList where docente equals to docenteId
        defaultPlanoAulaShouldBeFound("docenteId.equals=" + docenteId);

        // Get all the planoAulaList where docente equals to (docenteId + 1)
        defaultPlanoAulaShouldNotBeFound("docenteId.equals=" + (docenteId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoAulasByDisciplinaCurricularIsEqualToSomething() throws Exception {
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            planoAulaRepository.saveAndFlush(planoAula);
            disciplinaCurricular = DisciplinaCurricularResourceIT.createEntity(em);
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        em.persist(disciplinaCurricular);
        em.flush();
        planoAula.setDisciplinaCurricular(disciplinaCurricular);
        planoAulaRepository.saveAndFlush(planoAula);
        Long disciplinaCurricularId = disciplinaCurricular.getId();

        // Get all the planoAulaList where disciplinaCurricular equals to disciplinaCurricularId
        defaultPlanoAulaShouldBeFound("disciplinaCurricularId.equals=" + disciplinaCurricularId);

        // Get all the planoAulaList where disciplinaCurricular equals to (disciplinaCurricularId + 1)
        defaultPlanoAulaShouldNotBeFound("disciplinaCurricularId.equals=" + (disciplinaCurricularId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoAulaShouldBeFound(String filter) throws Exception {
        restPlanoAulaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoAula.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoAula").value(hasItem(DEFAULT_TIPO_AULA.toString())))
            .andExpect(jsonPath("$.[*].semanaLectiva").value(hasItem(DEFAULT_SEMANA_LECTIVA)))
            .andExpect(jsonPath("$.[*].perfilEntrada").value(hasItem(DEFAULT_PERFIL_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].perfilSaida").value(hasItem(DEFAULT_PERFIL_SAIDA.toString())))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].objectivoGeral").value(hasItem(DEFAULT_OBJECTIVO_GERAL.toString())))
            .andExpect(jsonPath("$.[*].objectivosEspecificos").value(hasItem(DEFAULT_OBJECTIVOS_ESPECIFICOS.toString())))
            .andExpect(jsonPath("$.[*].tempoTotalLicao").value(hasItem(DEFAULT_TEMPO_TOTAL_LICAO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));

        // Check, that the count call also returns 1
        restPlanoAulaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoAulaShouldNotBeFound(String filter) throws Exception {
        restPlanoAulaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoAulaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPlanoAula() throws Exception {
        // Get the planoAula
        restPlanoAulaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanoAula() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        int databaseSizeBeforeUpdate = planoAulaRepository.findAll().size();

        // Update the planoAula
        PlanoAula updatedPlanoAula = planoAulaRepository.findById(planoAula.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoAula are not directly saved in db
        em.detach(updatedPlanoAula);
        updatedPlanoAula
            .tipoAula(UPDATED_TIPO_AULA)
            .semanaLectiva(UPDATED_SEMANA_LECTIVA)
            .perfilEntrada(UPDATED_PERFIL_ENTRADA)
            .perfilSaida(UPDATED_PERFIL_SAIDA)
            .assunto(UPDATED_ASSUNTO)
            .objectivoGeral(UPDATED_OBJECTIVO_GERAL)
            .objectivosEspecificos(UPDATED_OBJECTIVOS_ESPECIFICOS)
            .tempoTotalLicao(UPDATED_TEMPO_TOTAL_LICAO)
            .estado(UPDATED_ESTADO);
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(updatedPlanoAula);

        restPlanoAulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoAulaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoAulaDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlanoAula in the database
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeUpdate);
        PlanoAula testPlanoAula = planoAulaList.get(planoAulaList.size() - 1);
        assertThat(testPlanoAula.getTipoAula()).isEqualTo(UPDATED_TIPO_AULA);
        assertThat(testPlanoAula.getSemanaLectiva()).isEqualTo(UPDATED_SEMANA_LECTIVA);
        assertThat(testPlanoAula.getPerfilEntrada()).isEqualTo(UPDATED_PERFIL_ENTRADA);
        assertThat(testPlanoAula.getPerfilSaida()).isEqualTo(UPDATED_PERFIL_SAIDA);
        assertThat(testPlanoAula.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testPlanoAula.getObjectivoGeral()).isEqualTo(UPDATED_OBJECTIVO_GERAL);
        assertThat(testPlanoAula.getObjectivosEspecificos()).isEqualTo(UPDATED_OBJECTIVOS_ESPECIFICOS);
        assertThat(testPlanoAula.getTempoTotalLicao()).isEqualTo(UPDATED_TEMPO_TOTAL_LICAO);
        assertThat(testPlanoAula.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void putNonExistingPlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = planoAulaRepository.findAll().size();
        planoAula.setId(count.incrementAndGet());

        // Create the PlanoAula
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoAulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoAulaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoAulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAula in the database
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = planoAulaRepository.findAll().size();
        planoAula.setId(count.incrementAndGet());

        // Create the PlanoAula
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoAulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAula in the database
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = planoAulaRepository.findAll().size();
        planoAula.setId(count.incrementAndGet());

        // Create the PlanoAula
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAulaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoAulaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoAula in the database
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanoAulaWithPatch() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        int databaseSizeBeforeUpdate = planoAulaRepository.findAll().size();

        // Update the planoAula using partial update
        PlanoAula partialUpdatedPlanoAula = new PlanoAula();
        partialUpdatedPlanoAula.setId(planoAula.getId());

        partialUpdatedPlanoAula.tipoAula(UPDATED_TIPO_AULA).perfilEntrada(UPDATED_PERFIL_ENTRADA).assunto(UPDATED_ASSUNTO);

        restPlanoAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoAula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanoAula))
            )
            .andExpect(status().isOk());

        // Validate the PlanoAula in the database
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeUpdate);
        PlanoAula testPlanoAula = planoAulaList.get(planoAulaList.size() - 1);
        assertThat(testPlanoAula.getTipoAula()).isEqualTo(UPDATED_TIPO_AULA);
        assertThat(testPlanoAula.getSemanaLectiva()).isEqualTo(DEFAULT_SEMANA_LECTIVA);
        assertThat(testPlanoAula.getPerfilEntrada()).isEqualTo(UPDATED_PERFIL_ENTRADA);
        assertThat(testPlanoAula.getPerfilSaida()).isEqualTo(DEFAULT_PERFIL_SAIDA);
        assertThat(testPlanoAula.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testPlanoAula.getObjectivoGeral()).isEqualTo(DEFAULT_OBJECTIVO_GERAL);
        assertThat(testPlanoAula.getObjectivosEspecificos()).isEqualTo(DEFAULT_OBJECTIVOS_ESPECIFICOS);
        assertThat(testPlanoAula.getTempoTotalLicao()).isEqualTo(DEFAULT_TEMPO_TOTAL_LICAO);
        assertThat(testPlanoAula.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    void fullUpdatePlanoAulaWithPatch() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        int databaseSizeBeforeUpdate = planoAulaRepository.findAll().size();

        // Update the planoAula using partial update
        PlanoAula partialUpdatedPlanoAula = new PlanoAula();
        partialUpdatedPlanoAula.setId(planoAula.getId());

        partialUpdatedPlanoAula
            .tipoAula(UPDATED_TIPO_AULA)
            .semanaLectiva(UPDATED_SEMANA_LECTIVA)
            .perfilEntrada(UPDATED_PERFIL_ENTRADA)
            .perfilSaida(UPDATED_PERFIL_SAIDA)
            .assunto(UPDATED_ASSUNTO)
            .objectivoGeral(UPDATED_OBJECTIVO_GERAL)
            .objectivosEspecificos(UPDATED_OBJECTIVOS_ESPECIFICOS)
            .tempoTotalLicao(UPDATED_TEMPO_TOTAL_LICAO)
            .estado(UPDATED_ESTADO);

        restPlanoAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoAula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanoAula))
            )
            .andExpect(status().isOk());

        // Validate the PlanoAula in the database
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeUpdate);
        PlanoAula testPlanoAula = planoAulaList.get(planoAulaList.size() - 1);
        assertThat(testPlanoAula.getTipoAula()).isEqualTo(UPDATED_TIPO_AULA);
        assertThat(testPlanoAula.getSemanaLectiva()).isEqualTo(UPDATED_SEMANA_LECTIVA);
        assertThat(testPlanoAula.getPerfilEntrada()).isEqualTo(UPDATED_PERFIL_ENTRADA);
        assertThat(testPlanoAula.getPerfilSaida()).isEqualTo(UPDATED_PERFIL_SAIDA);
        assertThat(testPlanoAula.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testPlanoAula.getObjectivoGeral()).isEqualTo(UPDATED_OBJECTIVO_GERAL);
        assertThat(testPlanoAula.getObjectivosEspecificos()).isEqualTo(UPDATED_OBJECTIVOS_ESPECIFICOS);
        assertThat(testPlanoAula.getTempoTotalLicao()).isEqualTo(UPDATED_TEMPO_TOTAL_LICAO);
        assertThat(testPlanoAula.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void patchNonExistingPlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = planoAulaRepository.findAll().size();
        planoAula.setId(count.incrementAndGet());

        // Create the PlanoAula
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planoAulaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planoAulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAula in the database
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = planoAulaRepository.findAll().size();
        planoAula.setId(count.incrementAndGet());

        // Create the PlanoAula
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planoAulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAula in the database
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = planoAulaRepository.findAll().size();
        planoAula.setId(count.incrementAndGet());

        // Create the PlanoAula
        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAulaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(planoAulaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoAula in the database
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanoAula() throws Exception {
        // Initialize the database
        planoAulaRepository.saveAndFlush(planoAula);

        int databaseSizeBeforeDelete = planoAulaRepository.findAll().size();

        // Delete the planoAula
        restPlanoAulaMockMvc
            .perform(delete(ENTITY_API_URL_ID, planoAula.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoAula> planoAulaList = planoAulaRepository.findAll();
        assertThat(planoAulaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
