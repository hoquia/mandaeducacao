package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.domain.enumeration.Comporamento;
import com.ravunana.longonkelo.repository.NotasPeriodicaDisciplinaRepository;
import com.ravunana.longonkelo.service.NotasPeriodicaDisciplinaService;
import com.ravunana.longonkelo.service.criteria.NotasPeriodicaDisciplinaCriteria;
import com.ravunana.longonkelo.service.dto.NotasPeriodicaDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.NotasPeriodicaDisciplinaMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link NotasPeriodicaDisciplinaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NotasPeriodicaDisciplinaResourceIT {

    private static final String DEFAULT_CHAVE_COMPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE_COMPOSTA = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERIODO_LANCAMENTO = 0;
    private static final Integer UPDATED_PERIODO_LANCAMENTO = 1;
    private static final Integer SMALLER_PERIODO_LANCAMENTO = 0 - 1;

    private static final Double DEFAULT_NOTA_1 = 0D;
    private static final Double UPDATED_NOTA_1 = 1D;
    private static final Double SMALLER_NOTA_1 = 0D - 1D;

    private static final Double DEFAULT_NOTA_2 = 0D;
    private static final Double UPDATED_NOTA_2 = 1D;
    private static final Double SMALLER_NOTA_2 = 0D - 1D;

    private static final Double DEFAULT_NOTA_3 = 0D;
    private static final Double UPDATED_NOTA_3 = 1D;
    private static final Double SMALLER_NOTA_3 = 0D - 1D;

    private static final Double DEFAULT_MEDIA = 0D;
    private static final Double UPDATED_MEDIA = 1D;
    private static final Double SMALLER_MEDIA = 0D - 1D;

    private static final Integer DEFAULT_FALTA_JUSTICADA = 0;
    private static final Integer UPDATED_FALTA_JUSTICADA = 1;
    private static final Integer SMALLER_FALTA_JUSTICADA = 0 - 1;

    private static final Integer DEFAULT_FALTA_INJUSTIFICADA = 0;
    private static final Integer UPDATED_FALTA_INJUSTIFICADA = 1;
    private static final Integer SMALLER_FALTA_INJUSTIFICADA = 0 - 1;

    private static final Comporamento DEFAULT_COMPORTAMENTO = Comporamento.BOM;
    private static final Comporamento UPDATED_COMPORTAMENTO = Comporamento.MEDIO;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/notas-periodica-disciplinas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepository;

    @Mock
    private NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepositoryMock;

    @Autowired
    private NotasPeriodicaDisciplinaMapper notasPeriodicaDisciplinaMapper;

    @Mock
    private NotasPeriodicaDisciplinaService notasPeriodicaDisciplinaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotasPeriodicaDisciplinaMockMvc;

    private NotasPeriodicaDisciplina notasPeriodicaDisciplina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotasPeriodicaDisciplina createEntity(EntityManager em) {
        NotasPeriodicaDisciplina notasPeriodicaDisciplina = new NotasPeriodicaDisciplina()
            .chaveComposta(DEFAULT_CHAVE_COMPOSTA)
            .periodoLancamento(DEFAULT_PERIODO_LANCAMENTO)
            .nota1(DEFAULT_NOTA_1)
            .nota2(DEFAULT_NOTA_2)
            .nota3(DEFAULT_NOTA_3)
            .media(DEFAULT_MEDIA)
            .faltaJusticada(DEFAULT_FALTA_JUSTICADA)
            .faltaInjustificada(DEFAULT_FALTA_INJUSTIFICADA)
            .comportamento(DEFAULT_COMPORTAMENTO)
            .hash(DEFAULT_HASH)
            .timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        notasPeriodicaDisciplina.setTurma(turma);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        notasPeriodicaDisciplina.setDocente(docente);
        // Add required entity
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            disciplinaCurricular = DisciplinaCurricularResourceIT.createEntity(em);
            em.persist(disciplinaCurricular);
            em.flush();
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        notasPeriodicaDisciplina.setDisciplinaCurricular(disciplinaCurricular);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        notasPeriodicaDisciplina.setMatricula(matricula);
        return notasPeriodicaDisciplina;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotasPeriodicaDisciplina createUpdatedEntity(EntityManager em) {
        NotasPeriodicaDisciplina notasPeriodicaDisciplina = new NotasPeriodicaDisciplina()
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .periodoLancamento(UPDATED_PERIODO_LANCAMENTO)
            .nota1(UPDATED_NOTA_1)
            .nota2(UPDATED_NOTA_2)
            .nota3(UPDATED_NOTA_3)
            .media(UPDATED_MEDIA)
            .faltaJusticada(UPDATED_FALTA_JUSTICADA)
            .faltaInjustificada(UPDATED_FALTA_INJUSTIFICADA)
            .comportamento(UPDATED_COMPORTAMENTO)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createUpdatedEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        notasPeriodicaDisciplina.setTurma(turma);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createUpdatedEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        notasPeriodicaDisciplina.setDocente(docente);
        // Add required entity
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            disciplinaCurricular = DisciplinaCurricularResourceIT.createUpdatedEntity(em);
            em.persist(disciplinaCurricular);
            em.flush();
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        notasPeriodicaDisciplina.setDisciplinaCurricular(disciplinaCurricular);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createUpdatedEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        notasPeriodicaDisciplina.setMatricula(matricula);
        return notasPeriodicaDisciplina;
    }

    @BeforeEach
    public void initTest() {
        notasPeriodicaDisciplina = createEntity(em);
    }

    @Test
    @Transactional
    void createNotasPeriodicaDisciplina() throws Exception {
        int databaseSizeBeforeCreate = notasPeriodicaDisciplinaRepository.findAll().size();
        // Create the NotasPeriodicaDisciplina
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);
        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasPeriodicaDisciplinaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NotasPeriodicaDisciplina in the database
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeCreate + 1);
        NotasPeriodicaDisciplina testNotasPeriodicaDisciplina = notasPeriodicaDisciplinaList.get(notasPeriodicaDisciplinaList.size() - 1);
        assertThat(testNotasPeriodicaDisciplina.getChaveComposta()).isEqualTo(DEFAULT_CHAVE_COMPOSTA);
        assertThat(testNotasPeriodicaDisciplina.getPeriodoLancamento()).isEqualTo(DEFAULT_PERIODO_LANCAMENTO);
        assertThat(testNotasPeriodicaDisciplina.getNota1()).isEqualTo(DEFAULT_NOTA_1);
        assertThat(testNotasPeriodicaDisciplina.getNota2()).isEqualTo(DEFAULT_NOTA_2);
        assertThat(testNotasPeriodicaDisciplina.getNota3()).isEqualTo(DEFAULT_NOTA_3);
        assertThat(testNotasPeriodicaDisciplina.getMedia()).isEqualTo(DEFAULT_MEDIA);
        assertThat(testNotasPeriodicaDisciplina.getFaltaJusticada()).isEqualTo(DEFAULT_FALTA_JUSTICADA);
        assertThat(testNotasPeriodicaDisciplina.getFaltaInjustificada()).isEqualTo(DEFAULT_FALTA_INJUSTIFICADA);
        assertThat(testNotasPeriodicaDisciplina.getComportamento()).isEqualTo(DEFAULT_COMPORTAMENTO);
        assertThat(testNotasPeriodicaDisciplina.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testNotasPeriodicaDisciplina.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createNotasPeriodicaDisciplinaWithExistingId() throws Exception {
        // Create the NotasPeriodicaDisciplina with an existing ID
        notasPeriodicaDisciplina.setId(1L);
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);

        int databaseSizeBeforeCreate = notasPeriodicaDisciplinaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasPeriodicaDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotasPeriodicaDisciplina in the database
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMediaIsRequired() throws Exception {
        int databaseSizeBeforeTest = notasPeriodicaDisciplinaRepository.findAll().size();
        // set the field null
        notasPeriodicaDisciplina.setMedia(null);

        // Create the NotasPeriodicaDisciplina, which fails.
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);

        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasPeriodicaDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = notasPeriodicaDisciplinaRepository.findAll().size();
        // set the field null
        notasPeriodicaDisciplina.setTimestamp(null);

        // Create the NotasPeriodicaDisciplina, which fails.
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);

        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasPeriodicaDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinas() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList
        restNotasPeriodicaDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notasPeriodicaDisciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta").value(hasItem(DEFAULT_CHAVE_COMPOSTA)))
            .andExpect(jsonPath("$.[*].periodoLancamento").value(hasItem(DEFAULT_PERIODO_LANCAMENTO)))
            .andExpect(jsonPath("$.[*].nota1").value(hasItem(DEFAULT_NOTA_1.doubleValue())))
            .andExpect(jsonPath("$.[*].nota2").value(hasItem(DEFAULT_NOTA_2.doubleValue())))
            .andExpect(jsonPath("$.[*].nota3").value(hasItem(DEFAULT_NOTA_3.doubleValue())))
            .andExpect(jsonPath("$.[*].media").value(hasItem(DEFAULT_MEDIA.doubleValue())))
            .andExpect(jsonPath("$.[*].faltaJusticada").value(hasItem(DEFAULT_FALTA_JUSTICADA)))
            .andExpect(jsonPath("$.[*].faltaInjustificada").value(hasItem(DEFAULT_FALTA_INJUSTIFICADA)))
            .andExpect(jsonPath("$.[*].comportamento").value(hasItem(DEFAULT_COMPORTAMENTO.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNotasPeriodicaDisciplinasWithEagerRelationshipsIsEnabled() throws Exception {
        when(notasPeriodicaDisciplinaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNotasPeriodicaDisciplinaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(notasPeriodicaDisciplinaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNotasPeriodicaDisciplinasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(notasPeriodicaDisciplinaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNotasPeriodicaDisciplinaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(notasPeriodicaDisciplinaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNotasPeriodicaDisciplina() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get the notasPeriodicaDisciplina
        restNotasPeriodicaDisciplinaMockMvc
            .perform(get(ENTITY_API_URL_ID, notasPeriodicaDisciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notasPeriodicaDisciplina.getId().intValue()))
            .andExpect(jsonPath("$.chaveComposta").value(DEFAULT_CHAVE_COMPOSTA))
            .andExpect(jsonPath("$.periodoLancamento").value(DEFAULT_PERIODO_LANCAMENTO))
            .andExpect(jsonPath("$.nota1").value(DEFAULT_NOTA_1.doubleValue()))
            .andExpect(jsonPath("$.nota2").value(DEFAULT_NOTA_2.doubleValue()))
            .andExpect(jsonPath("$.nota3").value(DEFAULT_NOTA_3.doubleValue()))
            .andExpect(jsonPath("$.media").value(DEFAULT_MEDIA.doubleValue()))
            .andExpect(jsonPath("$.faltaJusticada").value(DEFAULT_FALTA_JUSTICADA))
            .andExpect(jsonPath("$.faltaInjustificada").value(DEFAULT_FALTA_INJUSTIFICADA))
            .andExpect(jsonPath("$.comportamento").value(DEFAULT_COMPORTAMENTO.toString()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getNotasPeriodicaDisciplinasByIdFiltering() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        Long id = notasPeriodicaDisciplina.getId();

        defaultNotasPeriodicaDisciplinaShouldBeFound("id.equals=" + id);
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("id.notEquals=" + id);

        defaultNotasPeriodicaDisciplinaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("id.greaterThan=" + id);

        defaultNotasPeriodicaDisciplinaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByChaveCompostaIsEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where chaveComposta equals to DEFAULT_CHAVE_COMPOSTA
        defaultNotasPeriodicaDisciplinaShouldBeFound("chaveComposta.equals=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the notasPeriodicaDisciplinaList where chaveComposta equals to UPDATED_CHAVE_COMPOSTA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("chaveComposta.equals=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByChaveCompostaIsInShouldWork() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where chaveComposta in DEFAULT_CHAVE_COMPOSTA or UPDATED_CHAVE_COMPOSTA
        defaultNotasPeriodicaDisciplinaShouldBeFound("chaveComposta.in=" + DEFAULT_CHAVE_COMPOSTA + "," + UPDATED_CHAVE_COMPOSTA);

        // Get all the notasPeriodicaDisciplinaList where chaveComposta equals to UPDATED_CHAVE_COMPOSTA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("chaveComposta.in=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByChaveCompostaIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where chaveComposta is not null
        defaultNotasPeriodicaDisciplinaShouldBeFound("chaveComposta.specified=true");

        // Get all the notasPeriodicaDisciplinaList where chaveComposta is null
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("chaveComposta.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByChaveCompostaContainsSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where chaveComposta contains DEFAULT_CHAVE_COMPOSTA
        defaultNotasPeriodicaDisciplinaShouldBeFound("chaveComposta.contains=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the notasPeriodicaDisciplinaList where chaveComposta contains UPDATED_CHAVE_COMPOSTA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("chaveComposta.contains=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByChaveCompostaNotContainsSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where chaveComposta does not contain DEFAULT_CHAVE_COMPOSTA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("chaveComposta.doesNotContain=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the notasPeriodicaDisciplinaList where chaveComposta does not contain UPDATED_CHAVE_COMPOSTA
        defaultNotasPeriodicaDisciplinaShouldBeFound("chaveComposta.doesNotContain=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByPeriodoLancamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento equals to DEFAULT_PERIODO_LANCAMENTO
        defaultNotasPeriodicaDisciplinaShouldBeFound("periodoLancamento.equals=" + DEFAULT_PERIODO_LANCAMENTO);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento equals to UPDATED_PERIODO_LANCAMENTO
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("periodoLancamento.equals=" + UPDATED_PERIODO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByPeriodoLancamentoIsInShouldWork() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento in DEFAULT_PERIODO_LANCAMENTO or UPDATED_PERIODO_LANCAMENTO
        defaultNotasPeriodicaDisciplinaShouldBeFound(
            "periodoLancamento.in=" + DEFAULT_PERIODO_LANCAMENTO + "," + UPDATED_PERIODO_LANCAMENTO
        );

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento equals to UPDATED_PERIODO_LANCAMENTO
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("periodoLancamento.in=" + UPDATED_PERIODO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByPeriodoLancamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento is not null
        defaultNotasPeriodicaDisciplinaShouldBeFound("periodoLancamento.specified=true");

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento is null
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("periodoLancamento.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByPeriodoLancamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento is greater than or equal to DEFAULT_PERIODO_LANCAMENTO
        defaultNotasPeriodicaDisciplinaShouldBeFound("periodoLancamento.greaterThanOrEqual=" + DEFAULT_PERIODO_LANCAMENTO);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento is greater than or equal to (DEFAULT_PERIODO_LANCAMENTO + 1)
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("periodoLancamento.greaterThanOrEqual=" + (DEFAULT_PERIODO_LANCAMENTO + 1));
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByPeriodoLancamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento is less than or equal to DEFAULT_PERIODO_LANCAMENTO
        defaultNotasPeriodicaDisciplinaShouldBeFound("periodoLancamento.lessThanOrEqual=" + DEFAULT_PERIODO_LANCAMENTO);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento is less than or equal to SMALLER_PERIODO_LANCAMENTO
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("periodoLancamento.lessThanOrEqual=" + SMALLER_PERIODO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByPeriodoLancamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento is less than DEFAULT_PERIODO_LANCAMENTO
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("periodoLancamento.lessThan=" + DEFAULT_PERIODO_LANCAMENTO);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento is less than (DEFAULT_PERIODO_LANCAMENTO + 1)
        defaultNotasPeriodicaDisciplinaShouldBeFound("periodoLancamento.lessThan=" + (DEFAULT_PERIODO_LANCAMENTO + 1));
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByPeriodoLancamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento is greater than DEFAULT_PERIODO_LANCAMENTO
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("periodoLancamento.greaterThan=" + DEFAULT_PERIODO_LANCAMENTO);

        // Get all the notasPeriodicaDisciplinaList where periodoLancamento is greater than SMALLER_PERIODO_LANCAMENTO
        defaultNotasPeriodicaDisciplinaShouldBeFound("periodoLancamento.greaterThan=" + SMALLER_PERIODO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota1IsEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota1 equals to DEFAULT_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota1.equals=" + DEFAULT_NOTA_1);

        // Get all the notasPeriodicaDisciplinaList where nota1 equals to UPDATED_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota1.equals=" + UPDATED_NOTA_1);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota1IsInShouldWork() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota1 in DEFAULT_NOTA_1 or UPDATED_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota1.in=" + DEFAULT_NOTA_1 + "," + UPDATED_NOTA_1);

        // Get all the notasPeriodicaDisciplinaList where nota1 equals to UPDATED_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota1.in=" + UPDATED_NOTA_1);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota1IsNullOrNotNull() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota1 is not null
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota1.specified=true");

        // Get all the notasPeriodicaDisciplinaList where nota1 is null
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota1.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota1 is greater than or equal to DEFAULT_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota1.greaterThanOrEqual=" + DEFAULT_NOTA_1);

        // Get all the notasPeriodicaDisciplinaList where nota1 is greater than or equal to UPDATED_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota1.greaterThanOrEqual=" + UPDATED_NOTA_1);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota1 is less than or equal to DEFAULT_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota1.lessThanOrEqual=" + DEFAULT_NOTA_1);

        // Get all the notasPeriodicaDisciplinaList where nota1 is less than or equal to SMALLER_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota1.lessThanOrEqual=" + SMALLER_NOTA_1);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota1IsLessThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota1 is less than DEFAULT_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota1.lessThan=" + DEFAULT_NOTA_1);

        // Get all the notasPeriodicaDisciplinaList where nota1 is less than UPDATED_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota1.lessThan=" + UPDATED_NOTA_1);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota1 is greater than DEFAULT_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota1.greaterThan=" + DEFAULT_NOTA_1);

        // Get all the notasPeriodicaDisciplinaList where nota1 is greater than SMALLER_NOTA_1
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota1.greaterThan=" + SMALLER_NOTA_1);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota2IsEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota2 equals to DEFAULT_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota2.equals=" + DEFAULT_NOTA_2);

        // Get all the notasPeriodicaDisciplinaList where nota2 equals to UPDATED_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota2.equals=" + UPDATED_NOTA_2);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota2IsInShouldWork() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota2 in DEFAULT_NOTA_2 or UPDATED_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota2.in=" + DEFAULT_NOTA_2 + "," + UPDATED_NOTA_2);

        // Get all the notasPeriodicaDisciplinaList where nota2 equals to UPDATED_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota2.in=" + UPDATED_NOTA_2);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota2IsNullOrNotNull() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota2 is not null
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota2.specified=true");

        // Get all the notasPeriodicaDisciplinaList where nota2 is null
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota2.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota2 is greater than or equal to DEFAULT_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota2.greaterThanOrEqual=" + DEFAULT_NOTA_2);

        // Get all the notasPeriodicaDisciplinaList where nota2 is greater than or equal to UPDATED_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota2.greaterThanOrEqual=" + UPDATED_NOTA_2);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota2 is less than or equal to DEFAULT_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota2.lessThanOrEqual=" + DEFAULT_NOTA_2);

        // Get all the notasPeriodicaDisciplinaList where nota2 is less than or equal to SMALLER_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota2.lessThanOrEqual=" + SMALLER_NOTA_2);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota2IsLessThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota2 is less than DEFAULT_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota2.lessThan=" + DEFAULT_NOTA_2);

        // Get all the notasPeriodicaDisciplinaList where nota2 is less than UPDATED_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota2.lessThan=" + UPDATED_NOTA_2);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota2 is greater than DEFAULT_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota2.greaterThan=" + DEFAULT_NOTA_2);

        // Get all the notasPeriodicaDisciplinaList where nota2 is greater than SMALLER_NOTA_2
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota2.greaterThan=" + SMALLER_NOTA_2);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota3IsEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota3 equals to DEFAULT_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota3.equals=" + DEFAULT_NOTA_3);

        // Get all the notasPeriodicaDisciplinaList where nota3 equals to UPDATED_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota3.equals=" + UPDATED_NOTA_3);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota3IsInShouldWork() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota3 in DEFAULT_NOTA_3 or UPDATED_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota3.in=" + DEFAULT_NOTA_3 + "," + UPDATED_NOTA_3);

        // Get all the notasPeriodicaDisciplinaList where nota3 equals to UPDATED_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota3.in=" + UPDATED_NOTA_3);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota3IsNullOrNotNull() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota3 is not null
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota3.specified=true");

        // Get all the notasPeriodicaDisciplinaList where nota3 is null
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota3.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota3 is greater than or equal to DEFAULT_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota3.greaterThanOrEqual=" + DEFAULT_NOTA_3);

        // Get all the notasPeriodicaDisciplinaList where nota3 is greater than or equal to UPDATED_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota3.greaterThanOrEqual=" + UPDATED_NOTA_3);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota3 is less than or equal to DEFAULT_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota3.lessThanOrEqual=" + DEFAULT_NOTA_3);

        // Get all the notasPeriodicaDisciplinaList where nota3 is less than or equal to SMALLER_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota3.lessThanOrEqual=" + SMALLER_NOTA_3);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota3IsLessThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota3 is less than DEFAULT_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota3.lessThan=" + DEFAULT_NOTA_3);

        // Get all the notasPeriodicaDisciplinaList where nota3 is less than UPDATED_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota3.lessThan=" + UPDATED_NOTA_3);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByNota3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where nota3 is greater than DEFAULT_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("nota3.greaterThan=" + DEFAULT_NOTA_3);

        // Get all the notasPeriodicaDisciplinaList where nota3 is greater than SMALLER_NOTA_3
        defaultNotasPeriodicaDisciplinaShouldBeFound("nota3.greaterThan=" + SMALLER_NOTA_3);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByMediaIsEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where media equals to DEFAULT_MEDIA
        defaultNotasPeriodicaDisciplinaShouldBeFound("media.equals=" + DEFAULT_MEDIA);

        // Get all the notasPeriodicaDisciplinaList where media equals to UPDATED_MEDIA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("media.equals=" + UPDATED_MEDIA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByMediaIsInShouldWork() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where media in DEFAULT_MEDIA or UPDATED_MEDIA
        defaultNotasPeriodicaDisciplinaShouldBeFound("media.in=" + DEFAULT_MEDIA + "," + UPDATED_MEDIA);

        // Get all the notasPeriodicaDisciplinaList where media equals to UPDATED_MEDIA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("media.in=" + UPDATED_MEDIA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByMediaIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where media is not null
        defaultNotasPeriodicaDisciplinaShouldBeFound("media.specified=true");

        // Get all the notasPeriodicaDisciplinaList where media is null
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("media.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByMediaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where media is greater than or equal to DEFAULT_MEDIA
        defaultNotasPeriodicaDisciplinaShouldBeFound("media.greaterThanOrEqual=" + DEFAULT_MEDIA);

        // Get all the notasPeriodicaDisciplinaList where media is greater than or equal to UPDATED_MEDIA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("media.greaterThanOrEqual=" + UPDATED_MEDIA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByMediaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where media is less than or equal to DEFAULT_MEDIA
        defaultNotasPeriodicaDisciplinaShouldBeFound("media.lessThanOrEqual=" + DEFAULT_MEDIA);

        // Get all the notasPeriodicaDisciplinaList where media is less than or equal to SMALLER_MEDIA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("media.lessThanOrEqual=" + SMALLER_MEDIA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByMediaIsLessThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where media is less than DEFAULT_MEDIA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("media.lessThan=" + DEFAULT_MEDIA);

        // Get all the notasPeriodicaDisciplinaList where media is less than UPDATED_MEDIA
        defaultNotasPeriodicaDisciplinaShouldBeFound("media.lessThan=" + UPDATED_MEDIA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByMediaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where media is greater than DEFAULT_MEDIA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("media.greaterThan=" + DEFAULT_MEDIA);

        // Get all the notasPeriodicaDisciplinaList where media is greater than SMALLER_MEDIA
        defaultNotasPeriodicaDisciplinaShouldBeFound("media.greaterThan=" + SMALLER_MEDIA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaJusticadaIsEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada equals to DEFAULT_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaJusticada.equals=" + DEFAULT_FALTA_JUSTICADA);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada equals to UPDATED_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaJusticada.equals=" + UPDATED_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaJusticadaIsInShouldWork() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada in DEFAULT_FALTA_JUSTICADA or UPDATED_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaJusticada.in=" + DEFAULT_FALTA_JUSTICADA + "," + UPDATED_FALTA_JUSTICADA);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada equals to UPDATED_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaJusticada.in=" + UPDATED_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaJusticadaIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada is not null
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaJusticada.specified=true");

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada is null
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaJusticada.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaJusticadaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada is greater than or equal to DEFAULT_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaJusticada.greaterThanOrEqual=" + DEFAULT_FALTA_JUSTICADA);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada is greater than or equal to UPDATED_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaJusticada.greaterThanOrEqual=" + UPDATED_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaJusticadaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada is less than or equal to DEFAULT_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaJusticada.lessThanOrEqual=" + DEFAULT_FALTA_JUSTICADA);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada is less than or equal to SMALLER_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaJusticada.lessThanOrEqual=" + SMALLER_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaJusticadaIsLessThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada is less than DEFAULT_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaJusticada.lessThan=" + DEFAULT_FALTA_JUSTICADA);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada is less than UPDATED_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaJusticada.lessThan=" + UPDATED_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaJusticadaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada is greater than DEFAULT_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaJusticada.greaterThan=" + DEFAULT_FALTA_JUSTICADA);

        // Get all the notasPeriodicaDisciplinaList where faltaJusticada is greater than SMALLER_FALTA_JUSTICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaJusticada.greaterThan=" + SMALLER_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaInjustificadaIsEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada equals to DEFAULT_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaInjustificada.equals=" + DEFAULT_FALTA_INJUSTIFICADA);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada equals to UPDATED_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaInjustificada.equals=" + UPDATED_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaInjustificadaIsInShouldWork() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada in DEFAULT_FALTA_INJUSTIFICADA or UPDATED_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound(
            "faltaInjustificada.in=" + DEFAULT_FALTA_INJUSTIFICADA + "," + UPDATED_FALTA_INJUSTIFICADA
        );

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada equals to UPDATED_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaInjustificada.in=" + UPDATED_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaInjustificadaIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada is not null
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaInjustificada.specified=true");

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada is null
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaInjustificada.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaInjustificadaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada is greater than or equal to DEFAULT_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaInjustificada.greaterThanOrEqual=" + DEFAULT_FALTA_INJUSTIFICADA);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada is greater than or equal to UPDATED_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaInjustificada.greaterThanOrEqual=" + UPDATED_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaInjustificadaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada is less than or equal to DEFAULT_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaInjustificada.lessThanOrEqual=" + DEFAULT_FALTA_INJUSTIFICADA);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada is less than or equal to SMALLER_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaInjustificada.lessThanOrEqual=" + SMALLER_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaInjustificadaIsLessThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada is less than DEFAULT_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaInjustificada.lessThan=" + DEFAULT_FALTA_INJUSTIFICADA);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada is less than UPDATED_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaInjustificada.lessThan=" + UPDATED_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByFaltaInjustificadaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada is greater than DEFAULT_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("faltaInjustificada.greaterThan=" + DEFAULT_FALTA_INJUSTIFICADA);

        // Get all the notasPeriodicaDisciplinaList where faltaInjustificada is greater than SMALLER_FALTA_INJUSTIFICADA
        defaultNotasPeriodicaDisciplinaShouldBeFound("faltaInjustificada.greaterThan=" + SMALLER_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByComportamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where comportamento equals to DEFAULT_COMPORTAMENTO
        defaultNotasPeriodicaDisciplinaShouldBeFound("comportamento.equals=" + DEFAULT_COMPORTAMENTO);

        // Get all the notasPeriodicaDisciplinaList where comportamento equals to UPDATED_COMPORTAMENTO
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("comportamento.equals=" + UPDATED_COMPORTAMENTO);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByComportamentoIsInShouldWork() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where comportamento in DEFAULT_COMPORTAMENTO or UPDATED_COMPORTAMENTO
        defaultNotasPeriodicaDisciplinaShouldBeFound("comportamento.in=" + DEFAULT_COMPORTAMENTO + "," + UPDATED_COMPORTAMENTO);

        // Get all the notasPeriodicaDisciplinaList where comportamento equals to UPDATED_COMPORTAMENTO
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("comportamento.in=" + UPDATED_COMPORTAMENTO);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByComportamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where comportamento is not null
        defaultNotasPeriodicaDisciplinaShouldBeFound("comportamento.specified=true");

        // Get all the notasPeriodicaDisciplinaList where comportamento is null
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("comportamento.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where hash equals to DEFAULT_HASH
        defaultNotasPeriodicaDisciplinaShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the notasPeriodicaDisciplinaList where hash equals to UPDATED_HASH
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByHashIsInShouldWork() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultNotasPeriodicaDisciplinaShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the notasPeriodicaDisciplinaList where hash equals to UPDATED_HASH
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where hash is not null
        defaultNotasPeriodicaDisciplinaShouldBeFound("hash.specified=true");

        // Get all the notasPeriodicaDisciplinaList where hash is null
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByHashContainsSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where hash contains DEFAULT_HASH
        defaultNotasPeriodicaDisciplinaShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the notasPeriodicaDisciplinaList where hash contains UPDATED_HASH
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByHashNotContainsSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where hash does not contain DEFAULT_HASH
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the notasPeriodicaDisciplinaList where hash does not contain UPDATED_HASH
        defaultNotasPeriodicaDisciplinaShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where timestamp equals to DEFAULT_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the notasPeriodicaDisciplinaList where timestamp equals to UPDATED_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the notasPeriodicaDisciplinaList where timestamp equals to UPDATED_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where timestamp is not null
        defaultNotasPeriodicaDisciplinaShouldBeFound("timestamp.specified=true");

        // Get all the notasPeriodicaDisciplinaList where timestamp is null
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the notasPeriodicaDisciplinaList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the notasPeriodicaDisciplinaList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where timestamp is less than DEFAULT_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the notasPeriodicaDisciplinaList where timestamp is less than UPDATED_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        // Get all the notasPeriodicaDisciplinaList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the notasPeriodicaDisciplinaList where timestamp is greater than SMALLER_TIMESTAMP
        defaultNotasPeriodicaDisciplinaShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        notasPeriodicaDisciplina.addAnoLectivo(anoLectivo);
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the notasPeriodicaDisciplinaList where anoLectivo equals to anoLectivoId
        defaultNotasPeriodicaDisciplinaShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the notasPeriodicaDisciplinaList where anoLectivo equals to (anoLectivoId + 1)
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        notasPeriodicaDisciplina.setUtilizador(utilizador);
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
        Long utilizadorId = utilizador.getId();

        // Get all the notasPeriodicaDisciplinaList where utilizador equals to utilizadorId
        defaultNotasPeriodicaDisciplinaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the notasPeriodicaDisciplinaList where utilizador equals to (utilizadorId + 1)
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByTurmaIsEqualToSomething() throws Exception {
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
            turma = TurmaResourceIT.createEntity(em);
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(turma);
        em.flush();
        notasPeriodicaDisciplina.setTurma(turma);
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
        Long turmaId = turma.getId();

        // Get all the notasPeriodicaDisciplinaList where turma equals to turmaId
        defaultNotasPeriodicaDisciplinaShouldBeFound("turmaId.equals=" + turmaId);

        // Get all the notasPeriodicaDisciplinaList where turma equals to (turmaId + 1)
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("turmaId.equals=" + (turmaId + 1));
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByDocenteIsEqualToSomething() throws Exception {
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
            docente = DocenteResourceIT.createEntity(em);
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(docente);
        em.flush();
        notasPeriodicaDisciplina.setDocente(docente);
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
        Long docenteId = docente.getId();

        // Get all the notasPeriodicaDisciplinaList where docente equals to docenteId
        defaultNotasPeriodicaDisciplinaShouldBeFound("docenteId.equals=" + docenteId);

        // Get all the notasPeriodicaDisciplinaList where docente equals to (docenteId + 1)
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("docenteId.equals=" + (docenteId + 1));
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByDisciplinaCurricularIsEqualToSomething() throws Exception {
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
            disciplinaCurricular = DisciplinaCurricularResourceIT.createEntity(em);
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        em.persist(disciplinaCurricular);
        em.flush();
        notasPeriodicaDisciplina.setDisciplinaCurricular(disciplinaCurricular);
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
        Long disciplinaCurricularId = disciplinaCurricular.getId();

        // Get all the notasPeriodicaDisciplinaList where disciplinaCurricular equals to disciplinaCurricularId
        defaultNotasPeriodicaDisciplinaShouldBeFound("disciplinaCurricularId.equals=" + disciplinaCurricularId);

        // Get all the notasPeriodicaDisciplinaList where disciplinaCurricular equals to (disciplinaCurricularId + 1)
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("disciplinaCurricularId.equals=" + (disciplinaCurricularId + 1));
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByMatriculaIsEqualToSomething() throws Exception {
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
            matricula = MatriculaResourceIT.createEntity(em);
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(matricula);
        em.flush();
        notasPeriodicaDisciplina.setMatricula(matricula);
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
        Long matriculaId = matricula.getId();

        // Get all the notasPeriodicaDisciplinaList where matricula equals to matriculaId
        defaultNotasPeriodicaDisciplinaShouldBeFound("matriculaId.equals=" + matriculaId);

        // Get all the notasPeriodicaDisciplinaList where matricula equals to (matriculaId + 1)
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("matriculaId.equals=" + (matriculaId + 1));
    }

    @Test
    @Transactional
    void getAllNotasPeriodicaDisciplinasByEstadoIsEqualToSomething() throws Exception {
        EstadoDisciplinaCurricular estado;
        if (TestUtil.findAll(em, EstadoDisciplinaCurricular.class).isEmpty()) {
            notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
            estado = EstadoDisciplinaCurricularResourceIT.createEntity(em);
        } else {
            estado = TestUtil.findAll(em, EstadoDisciplinaCurricular.class).get(0);
        }
        em.persist(estado);
        em.flush();
        notasPeriodicaDisciplina.setEstado(estado);
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);
        Long estadoId = estado.getId();

        // Get all the notasPeriodicaDisciplinaList where estado equals to estadoId
        defaultNotasPeriodicaDisciplinaShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the notasPeriodicaDisciplinaList where estado equals to (estadoId + 1)
        defaultNotasPeriodicaDisciplinaShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotasPeriodicaDisciplinaShouldBeFound(String filter) throws Exception {
        restNotasPeriodicaDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notasPeriodicaDisciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta").value(hasItem(DEFAULT_CHAVE_COMPOSTA)))
            .andExpect(jsonPath("$.[*].periodoLancamento").value(hasItem(DEFAULT_PERIODO_LANCAMENTO)))
            .andExpect(jsonPath("$.[*].nota1").value(hasItem(DEFAULT_NOTA_1.doubleValue())))
            .andExpect(jsonPath("$.[*].nota2").value(hasItem(DEFAULT_NOTA_2.doubleValue())))
            .andExpect(jsonPath("$.[*].nota3").value(hasItem(DEFAULT_NOTA_3.doubleValue())))
            .andExpect(jsonPath("$.[*].media").value(hasItem(DEFAULT_MEDIA.doubleValue())))
            .andExpect(jsonPath("$.[*].faltaJusticada").value(hasItem(DEFAULT_FALTA_JUSTICADA)))
            .andExpect(jsonPath("$.[*].faltaInjustificada").value(hasItem(DEFAULT_FALTA_INJUSTIFICADA)))
            .andExpect(jsonPath("$.[*].comportamento").value(hasItem(DEFAULT_COMPORTAMENTO.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restNotasPeriodicaDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotasPeriodicaDisciplinaShouldNotBeFound(String filter) throws Exception {
        restNotasPeriodicaDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotasPeriodicaDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotasPeriodicaDisciplina() throws Exception {
        // Get the notasPeriodicaDisciplina
        restNotasPeriodicaDisciplinaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotasPeriodicaDisciplina() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        int databaseSizeBeforeUpdate = notasPeriodicaDisciplinaRepository.findAll().size();

        // Update the notasPeriodicaDisciplina
        NotasPeriodicaDisciplina updatedNotasPeriodicaDisciplina = notasPeriodicaDisciplinaRepository
            .findById(notasPeriodicaDisciplina.getId())
            .get();
        // Disconnect from session so that the updates on updatedNotasPeriodicaDisciplina are not directly saved in db
        em.detach(updatedNotasPeriodicaDisciplina);
        updatedNotasPeriodicaDisciplina
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .periodoLancamento(UPDATED_PERIODO_LANCAMENTO)
            .nota1(UPDATED_NOTA_1)
            .nota2(UPDATED_NOTA_2)
            .nota3(UPDATED_NOTA_3)
            .media(UPDATED_MEDIA)
            .faltaJusticada(UPDATED_FALTA_JUSTICADA)
            .faltaInjustificada(UPDATED_FALTA_INJUSTIFICADA)
            .comportamento(UPDATED_COMPORTAMENTO)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaMapper.toDto(updatedNotasPeriodicaDisciplina);

        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notasPeriodicaDisciplinaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasPeriodicaDisciplinaDTO))
            )
            .andExpect(status().isOk());

        // Validate the NotasPeriodicaDisciplina in the database
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeUpdate);
        NotasPeriodicaDisciplina testNotasPeriodicaDisciplina = notasPeriodicaDisciplinaList.get(notasPeriodicaDisciplinaList.size() - 1);
        assertThat(testNotasPeriodicaDisciplina.getChaveComposta()).isEqualTo(UPDATED_CHAVE_COMPOSTA);
        assertThat(testNotasPeriodicaDisciplina.getPeriodoLancamento()).isEqualTo(UPDATED_PERIODO_LANCAMENTO);
        assertThat(testNotasPeriodicaDisciplina.getNota1()).isEqualTo(UPDATED_NOTA_1);
        assertThat(testNotasPeriodicaDisciplina.getNota2()).isEqualTo(UPDATED_NOTA_2);
        assertThat(testNotasPeriodicaDisciplina.getNota3()).isEqualTo(UPDATED_NOTA_3);
        assertThat(testNotasPeriodicaDisciplina.getMedia()).isEqualTo(UPDATED_MEDIA);
        assertThat(testNotasPeriodicaDisciplina.getFaltaJusticada()).isEqualTo(UPDATED_FALTA_JUSTICADA);
        assertThat(testNotasPeriodicaDisciplina.getFaltaInjustificada()).isEqualTo(UPDATED_FALTA_INJUSTIFICADA);
        assertThat(testNotasPeriodicaDisciplina.getComportamento()).isEqualTo(UPDATED_COMPORTAMENTO);
        assertThat(testNotasPeriodicaDisciplina.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testNotasPeriodicaDisciplina.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingNotasPeriodicaDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasPeriodicaDisciplinaRepository.findAll().size();
        notasPeriodicaDisciplina.setId(count.incrementAndGet());

        // Create the NotasPeriodicaDisciplina
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notasPeriodicaDisciplinaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasPeriodicaDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotasPeriodicaDisciplina in the database
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotasPeriodicaDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasPeriodicaDisciplinaRepository.findAll().size();
        notasPeriodicaDisciplina.setId(count.incrementAndGet());

        // Create the NotasPeriodicaDisciplina
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasPeriodicaDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotasPeriodicaDisciplina in the database
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotasPeriodicaDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasPeriodicaDisciplinaRepository.findAll().size();
        notasPeriodicaDisciplina.setId(count.incrementAndGet());

        // Create the NotasPeriodicaDisciplina
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasPeriodicaDisciplinaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotasPeriodicaDisciplina in the database
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotasPeriodicaDisciplinaWithPatch() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        int databaseSizeBeforeUpdate = notasPeriodicaDisciplinaRepository.findAll().size();

        // Update the notasPeriodicaDisciplina using partial update
        NotasPeriodicaDisciplina partialUpdatedNotasPeriodicaDisciplina = new NotasPeriodicaDisciplina();
        partialUpdatedNotasPeriodicaDisciplina.setId(notasPeriodicaDisciplina.getId());

        partialUpdatedNotasPeriodicaDisciplina
            .periodoLancamento(UPDATED_PERIODO_LANCAMENTO)
            .nota2(UPDATED_NOTA_2)
            .faltaInjustificada(UPDATED_FALTA_INJUSTIFICADA)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);

        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotasPeriodicaDisciplina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotasPeriodicaDisciplina))
            )
            .andExpect(status().isOk());

        // Validate the NotasPeriodicaDisciplina in the database
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeUpdate);
        NotasPeriodicaDisciplina testNotasPeriodicaDisciplina = notasPeriodicaDisciplinaList.get(notasPeriodicaDisciplinaList.size() - 1);
        assertThat(testNotasPeriodicaDisciplina.getChaveComposta()).isEqualTo(DEFAULT_CHAVE_COMPOSTA);
        assertThat(testNotasPeriodicaDisciplina.getPeriodoLancamento()).isEqualTo(UPDATED_PERIODO_LANCAMENTO);
        assertThat(testNotasPeriodicaDisciplina.getNota1()).isEqualTo(DEFAULT_NOTA_1);
        assertThat(testNotasPeriodicaDisciplina.getNota2()).isEqualTo(UPDATED_NOTA_2);
        assertThat(testNotasPeriodicaDisciplina.getNota3()).isEqualTo(DEFAULT_NOTA_3);
        assertThat(testNotasPeriodicaDisciplina.getMedia()).isEqualTo(DEFAULT_MEDIA);
        assertThat(testNotasPeriodicaDisciplina.getFaltaJusticada()).isEqualTo(DEFAULT_FALTA_JUSTICADA);
        assertThat(testNotasPeriodicaDisciplina.getFaltaInjustificada()).isEqualTo(UPDATED_FALTA_INJUSTIFICADA);
        assertThat(testNotasPeriodicaDisciplina.getComportamento()).isEqualTo(DEFAULT_COMPORTAMENTO);
        assertThat(testNotasPeriodicaDisciplina.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testNotasPeriodicaDisciplina.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateNotasPeriodicaDisciplinaWithPatch() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        int databaseSizeBeforeUpdate = notasPeriodicaDisciplinaRepository.findAll().size();

        // Update the notasPeriodicaDisciplina using partial update
        NotasPeriodicaDisciplina partialUpdatedNotasPeriodicaDisciplina = new NotasPeriodicaDisciplina();
        partialUpdatedNotasPeriodicaDisciplina.setId(notasPeriodicaDisciplina.getId());

        partialUpdatedNotasPeriodicaDisciplina
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .periodoLancamento(UPDATED_PERIODO_LANCAMENTO)
            .nota1(UPDATED_NOTA_1)
            .nota2(UPDATED_NOTA_2)
            .nota3(UPDATED_NOTA_3)
            .media(UPDATED_MEDIA)
            .faltaJusticada(UPDATED_FALTA_JUSTICADA)
            .faltaInjustificada(UPDATED_FALTA_INJUSTIFICADA)
            .comportamento(UPDATED_COMPORTAMENTO)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);

        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotasPeriodicaDisciplina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotasPeriodicaDisciplina))
            )
            .andExpect(status().isOk());

        // Validate the NotasPeriodicaDisciplina in the database
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeUpdate);
        NotasPeriodicaDisciplina testNotasPeriodicaDisciplina = notasPeriodicaDisciplinaList.get(notasPeriodicaDisciplinaList.size() - 1);
        assertThat(testNotasPeriodicaDisciplina.getChaveComposta()).isEqualTo(UPDATED_CHAVE_COMPOSTA);
        assertThat(testNotasPeriodicaDisciplina.getPeriodoLancamento()).isEqualTo(UPDATED_PERIODO_LANCAMENTO);
        assertThat(testNotasPeriodicaDisciplina.getNota1()).isEqualTo(UPDATED_NOTA_1);
        assertThat(testNotasPeriodicaDisciplina.getNota2()).isEqualTo(UPDATED_NOTA_2);
        assertThat(testNotasPeriodicaDisciplina.getNota3()).isEqualTo(UPDATED_NOTA_3);
        assertThat(testNotasPeriodicaDisciplina.getMedia()).isEqualTo(UPDATED_MEDIA);
        assertThat(testNotasPeriodicaDisciplina.getFaltaJusticada()).isEqualTo(UPDATED_FALTA_JUSTICADA);
        assertThat(testNotasPeriodicaDisciplina.getFaltaInjustificada()).isEqualTo(UPDATED_FALTA_INJUSTIFICADA);
        assertThat(testNotasPeriodicaDisciplina.getComportamento()).isEqualTo(UPDATED_COMPORTAMENTO);
        assertThat(testNotasPeriodicaDisciplina.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testNotasPeriodicaDisciplina.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingNotasPeriodicaDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasPeriodicaDisciplinaRepository.findAll().size();
        notasPeriodicaDisciplina.setId(count.incrementAndGet());

        // Create the NotasPeriodicaDisciplina
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notasPeriodicaDisciplinaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notasPeriodicaDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotasPeriodicaDisciplina in the database
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotasPeriodicaDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasPeriodicaDisciplinaRepository.findAll().size();
        notasPeriodicaDisciplina.setId(count.incrementAndGet());

        // Create the NotasPeriodicaDisciplina
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notasPeriodicaDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotasPeriodicaDisciplina in the database
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotasPeriodicaDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasPeriodicaDisciplinaRepository.findAll().size();
        notasPeriodicaDisciplina.setId(count.incrementAndGet());

        // Create the NotasPeriodicaDisciplina
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasPeriodicaDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notasPeriodicaDisciplinaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotasPeriodicaDisciplina in the database
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotasPeriodicaDisciplina() throws Exception {
        // Initialize the database
        notasPeriodicaDisciplinaRepository.saveAndFlush(notasPeriodicaDisciplina);

        int databaseSizeBeforeDelete = notasPeriodicaDisciplinaRepository.findAll().size();

        // Delete the notasPeriodicaDisciplina
        restNotasPeriodicaDisciplinaMockMvc
            .perform(delete(ENTITY_API_URL_ID, notasPeriodicaDisciplina.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotasPeriodicaDisciplina> notasPeriodicaDisciplinaList = notasPeriodicaDisciplinaRepository.findAll();
        assertThat(notasPeriodicaDisciplinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
