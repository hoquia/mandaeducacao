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
import com.ravunana.longonkelo.domain.NotasGeralDisciplina;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.NotasGeralDisciplinaRepository;
import com.ravunana.longonkelo.service.NotasGeralDisciplinaService;
import com.ravunana.longonkelo.service.criteria.NotasGeralDisciplinaCriteria;
import com.ravunana.longonkelo.service.dto.NotasGeralDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.NotasGeralDisciplinaMapper;
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
 * Integration tests for the {@link NotasGeralDisciplinaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NotasGeralDisciplinaResourceIT {

    private static final String DEFAULT_CHAVE_COMPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE_COMPOSTA = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERIODO_LANCAMENTO = 0;
    private static final Integer UPDATED_PERIODO_LANCAMENTO = 1;
    private static final Integer SMALLER_PERIODO_LANCAMENTO = 0 - 1;

    private static final Double DEFAULT_MEDIA_1 = 0D;
    private static final Double UPDATED_MEDIA_1 = 1D;
    private static final Double SMALLER_MEDIA_1 = 0D - 1D;

    private static final Double DEFAULT_MEDIA_2 = 0D;
    private static final Double UPDATED_MEDIA_2 = 1D;
    private static final Double SMALLER_MEDIA_2 = 0D - 1D;

    private static final Double DEFAULT_MEDIA_3 = 0D;
    private static final Double UPDATED_MEDIA_3 = 1D;
    private static final Double SMALLER_MEDIA_3 = 0D - 1D;

    private static final Double DEFAULT_EXAME = 0D;
    private static final Double UPDATED_EXAME = 1D;
    private static final Double SMALLER_EXAME = 0D - 1D;

    private static final Double DEFAULT_RECURSO = 0D;
    private static final Double UPDATED_RECURSO = 1D;
    private static final Double SMALLER_RECURSO = 0D - 1D;

    private static final Double DEFAULT_EXAME_ESPECIAL = 0D;
    private static final Double UPDATED_EXAME_ESPECIAL = 1D;
    private static final Double SMALLER_EXAME_ESPECIAL = 0D - 1D;

    private static final Double DEFAULT_NOTA_CONSELHO = 0D;
    private static final Double UPDATED_NOTA_CONSELHO = 1D;
    private static final Double SMALLER_NOTA_CONSELHO = 0D - 1D;

    private static final Double DEFAULT_MEDIA_FINAL_DISCIPLINA = 0D;
    private static final Double UPDATED_MEDIA_FINAL_DISCIPLINA = 1D;
    private static final Double SMALLER_MEDIA_FINAL_DISCIPLINA = 0D - 1D;

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final Integer DEFAULT_FALTA_JUSTICADA = 0;
    private static final Integer UPDATED_FALTA_JUSTICADA = 1;
    private static final Integer SMALLER_FALTA_JUSTICADA = 0 - 1;

    private static final Integer DEFAULT_FALTA_INJUSTIFICADA = 0;
    private static final Integer UPDATED_FALTA_INJUSTIFICADA = 1;
    private static final Integer SMALLER_FALTA_INJUSTIFICADA = 0 - 1;

    private static final String ENTITY_API_URL = "/api/notas-geral-disciplinas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotasGeralDisciplinaRepository notasGeralDisciplinaRepository;

    @Mock
    private NotasGeralDisciplinaRepository notasGeralDisciplinaRepositoryMock;

    @Autowired
    private NotasGeralDisciplinaMapper notasGeralDisciplinaMapper;

    @Mock
    private NotasGeralDisciplinaService notasGeralDisciplinaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotasGeralDisciplinaMockMvc;

    private NotasGeralDisciplina notasGeralDisciplina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotasGeralDisciplina createEntity(EntityManager em) {
        NotasGeralDisciplina notasGeralDisciplina = new NotasGeralDisciplina()
            .chaveComposta(DEFAULT_CHAVE_COMPOSTA)
            .periodoLancamento(DEFAULT_PERIODO_LANCAMENTO)
            .media1(DEFAULT_MEDIA_1)
            .media2(DEFAULT_MEDIA_2)
            .media3(DEFAULT_MEDIA_3)
            .exame(DEFAULT_EXAME)
            .recurso(DEFAULT_RECURSO)
            .exameEspecial(DEFAULT_EXAME_ESPECIAL)
            .notaConselho(DEFAULT_NOTA_CONSELHO)
            .mediaFinalDisciplina(DEFAULT_MEDIA_FINAL_DISCIPLINA)
            .timestamp(DEFAULT_TIMESTAMP)
            .hash(DEFAULT_HASH)
            .faltaJusticada(DEFAULT_FALTA_JUSTICADA)
            .faltaInjustificada(DEFAULT_FALTA_INJUSTIFICADA);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        notasGeralDisciplina.setDocente(docente);
        // Add required entity
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            disciplinaCurricular = DisciplinaCurricularResourceIT.createEntity(em);
            em.persist(disciplinaCurricular);
            em.flush();
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        notasGeralDisciplina.setDisciplinaCurricular(disciplinaCurricular);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        notasGeralDisciplina.setMatricula(matricula);
        return notasGeralDisciplina;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotasGeralDisciplina createUpdatedEntity(EntityManager em) {
        NotasGeralDisciplina notasGeralDisciplina = new NotasGeralDisciplina()
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .periodoLancamento(UPDATED_PERIODO_LANCAMENTO)
            .media1(UPDATED_MEDIA_1)
            .media2(UPDATED_MEDIA_2)
            .media3(UPDATED_MEDIA_3)
            .exame(UPDATED_EXAME)
            .recurso(UPDATED_RECURSO)
            .exameEspecial(UPDATED_EXAME_ESPECIAL)
            .notaConselho(UPDATED_NOTA_CONSELHO)
            .mediaFinalDisciplina(UPDATED_MEDIA_FINAL_DISCIPLINA)
            .timestamp(UPDATED_TIMESTAMP)
            .hash(UPDATED_HASH)
            .faltaJusticada(UPDATED_FALTA_JUSTICADA)
            .faltaInjustificada(UPDATED_FALTA_INJUSTIFICADA);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createUpdatedEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        notasGeralDisciplina.setDocente(docente);
        // Add required entity
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            disciplinaCurricular = DisciplinaCurricularResourceIT.createUpdatedEntity(em);
            em.persist(disciplinaCurricular);
            em.flush();
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        notasGeralDisciplina.setDisciplinaCurricular(disciplinaCurricular);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createUpdatedEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        notasGeralDisciplina.setMatricula(matricula);
        return notasGeralDisciplina;
    }

    @BeforeEach
    public void initTest() {
        notasGeralDisciplina = createEntity(em);
    }

    @Test
    @Transactional
    void createNotasGeralDisciplina() throws Exception {
        int databaseSizeBeforeCreate = notasGeralDisciplinaRepository.findAll().size();
        // Create the NotasGeralDisciplina
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO = notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);
        restNotasGeralDisciplinaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasGeralDisciplinaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NotasGeralDisciplina in the database
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeCreate + 1);
        NotasGeralDisciplina testNotasGeralDisciplina = notasGeralDisciplinaList.get(notasGeralDisciplinaList.size() - 1);
        assertThat(testNotasGeralDisciplina.getChaveComposta()).isEqualTo(DEFAULT_CHAVE_COMPOSTA);
        assertThat(testNotasGeralDisciplina.getPeriodoLancamento()).isEqualTo(DEFAULT_PERIODO_LANCAMENTO);
        assertThat(testNotasGeralDisciplina.getMedia1()).isEqualTo(DEFAULT_MEDIA_1);
        assertThat(testNotasGeralDisciplina.getMedia2()).isEqualTo(DEFAULT_MEDIA_2);
        assertThat(testNotasGeralDisciplina.getMedia3()).isEqualTo(DEFAULT_MEDIA_3);
        assertThat(testNotasGeralDisciplina.getExame()).isEqualTo(DEFAULT_EXAME);
        assertThat(testNotasGeralDisciplina.getRecurso()).isEqualTo(DEFAULT_RECURSO);
        assertThat(testNotasGeralDisciplina.getExameEspecial()).isEqualTo(DEFAULT_EXAME_ESPECIAL);
        assertThat(testNotasGeralDisciplina.getNotaConselho()).isEqualTo(DEFAULT_NOTA_CONSELHO);
        assertThat(testNotasGeralDisciplina.getMediaFinalDisciplina()).isEqualTo(DEFAULT_MEDIA_FINAL_DISCIPLINA);
        assertThat(testNotasGeralDisciplina.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testNotasGeralDisciplina.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testNotasGeralDisciplina.getFaltaJusticada()).isEqualTo(DEFAULT_FALTA_JUSTICADA);
        assertThat(testNotasGeralDisciplina.getFaltaInjustificada()).isEqualTo(DEFAULT_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void createNotasGeralDisciplinaWithExistingId() throws Exception {
        // Create the NotasGeralDisciplina with an existing ID
        notasGeralDisciplina.setId(1L);
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO = notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);

        int databaseSizeBeforeCreate = notasGeralDisciplinaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotasGeralDisciplinaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasGeralDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotasGeralDisciplina in the database
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = notasGeralDisciplinaRepository.findAll().size();
        // set the field null
        notasGeralDisciplina.setTimestamp(null);

        // Create the NotasGeralDisciplina, which fails.
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO = notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);

        restNotasGeralDisciplinaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasGeralDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinas() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList
        restNotasGeralDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notasGeralDisciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta").value(hasItem(DEFAULT_CHAVE_COMPOSTA)))
            .andExpect(jsonPath("$.[*].periodoLancamento").value(hasItem(DEFAULT_PERIODO_LANCAMENTO)))
            .andExpect(jsonPath("$.[*].media1").value(hasItem(DEFAULT_MEDIA_1.doubleValue())))
            .andExpect(jsonPath("$.[*].media2").value(hasItem(DEFAULT_MEDIA_2.doubleValue())))
            .andExpect(jsonPath("$.[*].media3").value(hasItem(DEFAULT_MEDIA_3.doubleValue())))
            .andExpect(jsonPath("$.[*].exame").value(hasItem(DEFAULT_EXAME.doubleValue())))
            .andExpect(jsonPath("$.[*].recurso").value(hasItem(DEFAULT_RECURSO.doubleValue())))
            .andExpect(jsonPath("$.[*].exameEspecial").value(hasItem(DEFAULT_EXAME_ESPECIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].notaConselho").value(hasItem(DEFAULT_NOTA_CONSELHO.doubleValue())))
            .andExpect(jsonPath("$.[*].mediaFinalDisciplina").value(hasItem(DEFAULT_MEDIA_FINAL_DISCIPLINA.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].faltaJusticada").value(hasItem(DEFAULT_FALTA_JUSTICADA)))
            .andExpect(jsonPath("$.[*].faltaInjustificada").value(hasItem(DEFAULT_FALTA_INJUSTIFICADA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNotasGeralDisciplinasWithEagerRelationshipsIsEnabled() throws Exception {
        when(notasGeralDisciplinaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNotasGeralDisciplinaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(notasGeralDisciplinaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNotasGeralDisciplinasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(notasGeralDisciplinaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNotasGeralDisciplinaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(notasGeralDisciplinaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNotasGeralDisciplina() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get the notasGeralDisciplina
        restNotasGeralDisciplinaMockMvc
            .perform(get(ENTITY_API_URL_ID, notasGeralDisciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notasGeralDisciplina.getId().intValue()))
            .andExpect(jsonPath("$.chaveComposta").value(DEFAULT_CHAVE_COMPOSTA))
            .andExpect(jsonPath("$.periodoLancamento").value(DEFAULT_PERIODO_LANCAMENTO))
            .andExpect(jsonPath("$.media1").value(DEFAULT_MEDIA_1.doubleValue()))
            .andExpect(jsonPath("$.media2").value(DEFAULT_MEDIA_2.doubleValue()))
            .andExpect(jsonPath("$.media3").value(DEFAULT_MEDIA_3.doubleValue()))
            .andExpect(jsonPath("$.exame").value(DEFAULT_EXAME.doubleValue()))
            .andExpect(jsonPath("$.recurso").value(DEFAULT_RECURSO.doubleValue()))
            .andExpect(jsonPath("$.exameEspecial").value(DEFAULT_EXAME_ESPECIAL.doubleValue()))
            .andExpect(jsonPath("$.notaConselho").value(DEFAULT_NOTA_CONSELHO.doubleValue()))
            .andExpect(jsonPath("$.mediaFinalDisciplina").value(DEFAULT_MEDIA_FINAL_DISCIPLINA.doubleValue()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.faltaJusticada").value(DEFAULT_FALTA_JUSTICADA))
            .andExpect(jsonPath("$.faltaInjustificada").value(DEFAULT_FALTA_INJUSTIFICADA));
    }

    @Test
    @Transactional
    void getNotasGeralDisciplinasByIdFiltering() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        Long id = notasGeralDisciplina.getId();

        defaultNotasGeralDisciplinaShouldBeFound("id.equals=" + id);
        defaultNotasGeralDisciplinaShouldNotBeFound("id.notEquals=" + id);

        defaultNotasGeralDisciplinaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotasGeralDisciplinaShouldNotBeFound("id.greaterThan=" + id);

        defaultNotasGeralDisciplinaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotasGeralDisciplinaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByChaveCompostaIsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where chaveComposta equals to DEFAULT_CHAVE_COMPOSTA
        defaultNotasGeralDisciplinaShouldBeFound("chaveComposta.equals=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the notasGeralDisciplinaList where chaveComposta equals to UPDATED_CHAVE_COMPOSTA
        defaultNotasGeralDisciplinaShouldNotBeFound("chaveComposta.equals=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByChaveCompostaIsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where chaveComposta in DEFAULT_CHAVE_COMPOSTA or UPDATED_CHAVE_COMPOSTA
        defaultNotasGeralDisciplinaShouldBeFound("chaveComposta.in=" + DEFAULT_CHAVE_COMPOSTA + "," + UPDATED_CHAVE_COMPOSTA);

        // Get all the notasGeralDisciplinaList where chaveComposta equals to UPDATED_CHAVE_COMPOSTA
        defaultNotasGeralDisciplinaShouldNotBeFound("chaveComposta.in=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByChaveCompostaIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where chaveComposta is not null
        defaultNotasGeralDisciplinaShouldBeFound("chaveComposta.specified=true");

        // Get all the notasGeralDisciplinaList where chaveComposta is null
        defaultNotasGeralDisciplinaShouldNotBeFound("chaveComposta.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByChaveCompostaContainsSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where chaveComposta contains DEFAULT_CHAVE_COMPOSTA
        defaultNotasGeralDisciplinaShouldBeFound("chaveComposta.contains=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the notasGeralDisciplinaList where chaveComposta contains UPDATED_CHAVE_COMPOSTA
        defaultNotasGeralDisciplinaShouldNotBeFound("chaveComposta.contains=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByChaveCompostaNotContainsSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where chaveComposta does not contain DEFAULT_CHAVE_COMPOSTA
        defaultNotasGeralDisciplinaShouldNotBeFound("chaveComposta.doesNotContain=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the notasGeralDisciplinaList where chaveComposta does not contain UPDATED_CHAVE_COMPOSTA
        defaultNotasGeralDisciplinaShouldBeFound("chaveComposta.doesNotContain=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByPeriodoLancamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where periodoLancamento equals to DEFAULT_PERIODO_LANCAMENTO
        defaultNotasGeralDisciplinaShouldBeFound("periodoLancamento.equals=" + DEFAULT_PERIODO_LANCAMENTO);

        // Get all the notasGeralDisciplinaList where periodoLancamento equals to UPDATED_PERIODO_LANCAMENTO
        defaultNotasGeralDisciplinaShouldNotBeFound("periodoLancamento.equals=" + UPDATED_PERIODO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByPeriodoLancamentoIsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where periodoLancamento in DEFAULT_PERIODO_LANCAMENTO or UPDATED_PERIODO_LANCAMENTO
        defaultNotasGeralDisciplinaShouldBeFound("periodoLancamento.in=" + DEFAULT_PERIODO_LANCAMENTO + "," + UPDATED_PERIODO_LANCAMENTO);

        // Get all the notasGeralDisciplinaList where periodoLancamento equals to UPDATED_PERIODO_LANCAMENTO
        defaultNotasGeralDisciplinaShouldNotBeFound("periodoLancamento.in=" + UPDATED_PERIODO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByPeriodoLancamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where periodoLancamento is not null
        defaultNotasGeralDisciplinaShouldBeFound("periodoLancamento.specified=true");

        // Get all the notasGeralDisciplinaList where periodoLancamento is null
        defaultNotasGeralDisciplinaShouldNotBeFound("periodoLancamento.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByPeriodoLancamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where periodoLancamento is greater than or equal to DEFAULT_PERIODO_LANCAMENTO
        defaultNotasGeralDisciplinaShouldBeFound("periodoLancamento.greaterThanOrEqual=" + DEFAULT_PERIODO_LANCAMENTO);

        // Get all the notasGeralDisciplinaList where periodoLancamento is greater than or equal to (DEFAULT_PERIODO_LANCAMENTO + 1)
        defaultNotasGeralDisciplinaShouldNotBeFound("periodoLancamento.greaterThanOrEqual=" + (DEFAULT_PERIODO_LANCAMENTO + 1));
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByPeriodoLancamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where periodoLancamento is less than or equal to DEFAULT_PERIODO_LANCAMENTO
        defaultNotasGeralDisciplinaShouldBeFound("periodoLancamento.lessThanOrEqual=" + DEFAULT_PERIODO_LANCAMENTO);

        // Get all the notasGeralDisciplinaList where periodoLancamento is less than or equal to SMALLER_PERIODO_LANCAMENTO
        defaultNotasGeralDisciplinaShouldNotBeFound("periodoLancamento.lessThanOrEqual=" + SMALLER_PERIODO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByPeriodoLancamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where periodoLancamento is less than DEFAULT_PERIODO_LANCAMENTO
        defaultNotasGeralDisciplinaShouldNotBeFound("periodoLancamento.lessThan=" + DEFAULT_PERIODO_LANCAMENTO);

        // Get all the notasGeralDisciplinaList where periodoLancamento is less than (DEFAULT_PERIODO_LANCAMENTO + 1)
        defaultNotasGeralDisciplinaShouldBeFound("periodoLancamento.lessThan=" + (DEFAULT_PERIODO_LANCAMENTO + 1));
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByPeriodoLancamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where periodoLancamento is greater than DEFAULT_PERIODO_LANCAMENTO
        defaultNotasGeralDisciplinaShouldNotBeFound("periodoLancamento.greaterThan=" + DEFAULT_PERIODO_LANCAMENTO);

        // Get all the notasGeralDisciplinaList where periodoLancamento is greater than SMALLER_PERIODO_LANCAMENTO
        defaultNotasGeralDisciplinaShouldBeFound("periodoLancamento.greaterThan=" + SMALLER_PERIODO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia1IsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media1 equals to DEFAULT_MEDIA_1
        defaultNotasGeralDisciplinaShouldBeFound("media1.equals=" + DEFAULT_MEDIA_1);

        // Get all the notasGeralDisciplinaList where media1 equals to UPDATED_MEDIA_1
        defaultNotasGeralDisciplinaShouldNotBeFound("media1.equals=" + UPDATED_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia1IsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media1 in DEFAULT_MEDIA_1 or UPDATED_MEDIA_1
        defaultNotasGeralDisciplinaShouldBeFound("media1.in=" + DEFAULT_MEDIA_1 + "," + UPDATED_MEDIA_1);

        // Get all the notasGeralDisciplinaList where media1 equals to UPDATED_MEDIA_1
        defaultNotasGeralDisciplinaShouldNotBeFound("media1.in=" + UPDATED_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia1IsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media1 is not null
        defaultNotasGeralDisciplinaShouldBeFound("media1.specified=true");

        // Get all the notasGeralDisciplinaList where media1 is null
        defaultNotasGeralDisciplinaShouldNotBeFound("media1.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media1 is greater than or equal to DEFAULT_MEDIA_1
        defaultNotasGeralDisciplinaShouldBeFound("media1.greaterThanOrEqual=" + DEFAULT_MEDIA_1);

        // Get all the notasGeralDisciplinaList where media1 is greater than or equal to UPDATED_MEDIA_1
        defaultNotasGeralDisciplinaShouldNotBeFound("media1.greaterThanOrEqual=" + UPDATED_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media1 is less than or equal to DEFAULT_MEDIA_1
        defaultNotasGeralDisciplinaShouldBeFound("media1.lessThanOrEqual=" + DEFAULT_MEDIA_1);

        // Get all the notasGeralDisciplinaList where media1 is less than or equal to SMALLER_MEDIA_1
        defaultNotasGeralDisciplinaShouldNotBeFound("media1.lessThanOrEqual=" + SMALLER_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia1IsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media1 is less than DEFAULT_MEDIA_1
        defaultNotasGeralDisciplinaShouldNotBeFound("media1.lessThan=" + DEFAULT_MEDIA_1);

        // Get all the notasGeralDisciplinaList where media1 is less than UPDATED_MEDIA_1
        defaultNotasGeralDisciplinaShouldBeFound("media1.lessThan=" + UPDATED_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media1 is greater than DEFAULT_MEDIA_1
        defaultNotasGeralDisciplinaShouldNotBeFound("media1.greaterThan=" + DEFAULT_MEDIA_1);

        // Get all the notasGeralDisciplinaList where media1 is greater than SMALLER_MEDIA_1
        defaultNotasGeralDisciplinaShouldBeFound("media1.greaterThan=" + SMALLER_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia2IsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media2 equals to DEFAULT_MEDIA_2
        defaultNotasGeralDisciplinaShouldBeFound("media2.equals=" + DEFAULT_MEDIA_2);

        // Get all the notasGeralDisciplinaList where media2 equals to UPDATED_MEDIA_2
        defaultNotasGeralDisciplinaShouldNotBeFound("media2.equals=" + UPDATED_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia2IsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media2 in DEFAULT_MEDIA_2 or UPDATED_MEDIA_2
        defaultNotasGeralDisciplinaShouldBeFound("media2.in=" + DEFAULT_MEDIA_2 + "," + UPDATED_MEDIA_2);

        // Get all the notasGeralDisciplinaList where media2 equals to UPDATED_MEDIA_2
        defaultNotasGeralDisciplinaShouldNotBeFound("media2.in=" + UPDATED_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia2IsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media2 is not null
        defaultNotasGeralDisciplinaShouldBeFound("media2.specified=true");

        // Get all the notasGeralDisciplinaList where media2 is null
        defaultNotasGeralDisciplinaShouldNotBeFound("media2.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media2 is greater than or equal to DEFAULT_MEDIA_2
        defaultNotasGeralDisciplinaShouldBeFound("media2.greaterThanOrEqual=" + DEFAULT_MEDIA_2);

        // Get all the notasGeralDisciplinaList where media2 is greater than or equal to UPDATED_MEDIA_2
        defaultNotasGeralDisciplinaShouldNotBeFound("media2.greaterThanOrEqual=" + UPDATED_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media2 is less than or equal to DEFAULT_MEDIA_2
        defaultNotasGeralDisciplinaShouldBeFound("media2.lessThanOrEqual=" + DEFAULT_MEDIA_2);

        // Get all the notasGeralDisciplinaList where media2 is less than or equal to SMALLER_MEDIA_2
        defaultNotasGeralDisciplinaShouldNotBeFound("media2.lessThanOrEqual=" + SMALLER_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia2IsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media2 is less than DEFAULT_MEDIA_2
        defaultNotasGeralDisciplinaShouldNotBeFound("media2.lessThan=" + DEFAULT_MEDIA_2);

        // Get all the notasGeralDisciplinaList where media2 is less than UPDATED_MEDIA_2
        defaultNotasGeralDisciplinaShouldBeFound("media2.lessThan=" + UPDATED_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media2 is greater than DEFAULT_MEDIA_2
        defaultNotasGeralDisciplinaShouldNotBeFound("media2.greaterThan=" + DEFAULT_MEDIA_2);

        // Get all the notasGeralDisciplinaList where media2 is greater than SMALLER_MEDIA_2
        defaultNotasGeralDisciplinaShouldBeFound("media2.greaterThan=" + SMALLER_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia3IsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media3 equals to DEFAULT_MEDIA_3
        defaultNotasGeralDisciplinaShouldBeFound("media3.equals=" + DEFAULT_MEDIA_3);

        // Get all the notasGeralDisciplinaList where media3 equals to UPDATED_MEDIA_3
        defaultNotasGeralDisciplinaShouldNotBeFound("media3.equals=" + UPDATED_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia3IsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media3 in DEFAULT_MEDIA_3 or UPDATED_MEDIA_3
        defaultNotasGeralDisciplinaShouldBeFound("media3.in=" + DEFAULT_MEDIA_3 + "," + UPDATED_MEDIA_3);

        // Get all the notasGeralDisciplinaList where media3 equals to UPDATED_MEDIA_3
        defaultNotasGeralDisciplinaShouldNotBeFound("media3.in=" + UPDATED_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia3IsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media3 is not null
        defaultNotasGeralDisciplinaShouldBeFound("media3.specified=true");

        // Get all the notasGeralDisciplinaList where media3 is null
        defaultNotasGeralDisciplinaShouldNotBeFound("media3.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media3 is greater than or equal to DEFAULT_MEDIA_3
        defaultNotasGeralDisciplinaShouldBeFound("media3.greaterThanOrEqual=" + DEFAULT_MEDIA_3);

        // Get all the notasGeralDisciplinaList where media3 is greater than or equal to UPDATED_MEDIA_3
        defaultNotasGeralDisciplinaShouldNotBeFound("media3.greaterThanOrEqual=" + UPDATED_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media3 is less than or equal to DEFAULT_MEDIA_3
        defaultNotasGeralDisciplinaShouldBeFound("media3.lessThanOrEqual=" + DEFAULT_MEDIA_3);

        // Get all the notasGeralDisciplinaList where media3 is less than or equal to SMALLER_MEDIA_3
        defaultNotasGeralDisciplinaShouldNotBeFound("media3.lessThanOrEqual=" + SMALLER_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia3IsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media3 is less than DEFAULT_MEDIA_3
        defaultNotasGeralDisciplinaShouldNotBeFound("media3.lessThan=" + DEFAULT_MEDIA_3);

        // Get all the notasGeralDisciplinaList where media3 is less than UPDATED_MEDIA_3
        defaultNotasGeralDisciplinaShouldBeFound("media3.lessThan=" + UPDATED_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMedia3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where media3 is greater than DEFAULT_MEDIA_3
        defaultNotasGeralDisciplinaShouldNotBeFound("media3.greaterThan=" + DEFAULT_MEDIA_3);

        // Get all the notasGeralDisciplinaList where media3 is greater than SMALLER_MEDIA_3
        defaultNotasGeralDisciplinaShouldBeFound("media3.greaterThan=" + SMALLER_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameIsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exame equals to DEFAULT_EXAME
        defaultNotasGeralDisciplinaShouldBeFound("exame.equals=" + DEFAULT_EXAME);

        // Get all the notasGeralDisciplinaList where exame equals to UPDATED_EXAME
        defaultNotasGeralDisciplinaShouldNotBeFound("exame.equals=" + UPDATED_EXAME);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameIsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exame in DEFAULT_EXAME or UPDATED_EXAME
        defaultNotasGeralDisciplinaShouldBeFound("exame.in=" + DEFAULT_EXAME + "," + UPDATED_EXAME);

        // Get all the notasGeralDisciplinaList where exame equals to UPDATED_EXAME
        defaultNotasGeralDisciplinaShouldNotBeFound("exame.in=" + UPDATED_EXAME);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exame is not null
        defaultNotasGeralDisciplinaShouldBeFound("exame.specified=true");

        // Get all the notasGeralDisciplinaList where exame is null
        defaultNotasGeralDisciplinaShouldNotBeFound("exame.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exame is greater than or equal to DEFAULT_EXAME
        defaultNotasGeralDisciplinaShouldBeFound("exame.greaterThanOrEqual=" + DEFAULT_EXAME);

        // Get all the notasGeralDisciplinaList where exame is greater than or equal to UPDATED_EXAME
        defaultNotasGeralDisciplinaShouldNotBeFound("exame.greaterThanOrEqual=" + UPDATED_EXAME);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exame is less than or equal to DEFAULT_EXAME
        defaultNotasGeralDisciplinaShouldBeFound("exame.lessThanOrEqual=" + DEFAULT_EXAME);

        // Get all the notasGeralDisciplinaList where exame is less than or equal to SMALLER_EXAME
        defaultNotasGeralDisciplinaShouldNotBeFound("exame.lessThanOrEqual=" + SMALLER_EXAME);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameIsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exame is less than DEFAULT_EXAME
        defaultNotasGeralDisciplinaShouldNotBeFound("exame.lessThan=" + DEFAULT_EXAME);

        // Get all the notasGeralDisciplinaList where exame is less than UPDATED_EXAME
        defaultNotasGeralDisciplinaShouldBeFound("exame.lessThan=" + UPDATED_EXAME);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exame is greater than DEFAULT_EXAME
        defaultNotasGeralDisciplinaShouldNotBeFound("exame.greaterThan=" + DEFAULT_EXAME);

        // Get all the notasGeralDisciplinaList where exame is greater than SMALLER_EXAME
        defaultNotasGeralDisciplinaShouldBeFound("exame.greaterThan=" + SMALLER_EXAME);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where recurso equals to DEFAULT_RECURSO
        defaultNotasGeralDisciplinaShouldBeFound("recurso.equals=" + DEFAULT_RECURSO);

        // Get all the notasGeralDisciplinaList where recurso equals to UPDATED_RECURSO
        defaultNotasGeralDisciplinaShouldNotBeFound("recurso.equals=" + UPDATED_RECURSO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByRecursoIsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where recurso in DEFAULT_RECURSO or UPDATED_RECURSO
        defaultNotasGeralDisciplinaShouldBeFound("recurso.in=" + DEFAULT_RECURSO + "," + UPDATED_RECURSO);

        // Get all the notasGeralDisciplinaList where recurso equals to UPDATED_RECURSO
        defaultNotasGeralDisciplinaShouldNotBeFound("recurso.in=" + UPDATED_RECURSO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByRecursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where recurso is not null
        defaultNotasGeralDisciplinaShouldBeFound("recurso.specified=true");

        // Get all the notasGeralDisciplinaList where recurso is null
        defaultNotasGeralDisciplinaShouldNotBeFound("recurso.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByRecursoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where recurso is greater than or equal to DEFAULT_RECURSO
        defaultNotasGeralDisciplinaShouldBeFound("recurso.greaterThanOrEqual=" + DEFAULT_RECURSO);

        // Get all the notasGeralDisciplinaList where recurso is greater than or equal to UPDATED_RECURSO
        defaultNotasGeralDisciplinaShouldNotBeFound("recurso.greaterThanOrEqual=" + UPDATED_RECURSO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByRecursoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where recurso is less than or equal to DEFAULT_RECURSO
        defaultNotasGeralDisciplinaShouldBeFound("recurso.lessThanOrEqual=" + DEFAULT_RECURSO);

        // Get all the notasGeralDisciplinaList where recurso is less than or equal to SMALLER_RECURSO
        defaultNotasGeralDisciplinaShouldNotBeFound("recurso.lessThanOrEqual=" + SMALLER_RECURSO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByRecursoIsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where recurso is less than DEFAULT_RECURSO
        defaultNotasGeralDisciplinaShouldNotBeFound("recurso.lessThan=" + DEFAULT_RECURSO);

        // Get all the notasGeralDisciplinaList where recurso is less than UPDATED_RECURSO
        defaultNotasGeralDisciplinaShouldBeFound("recurso.lessThan=" + UPDATED_RECURSO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByRecursoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where recurso is greater than DEFAULT_RECURSO
        defaultNotasGeralDisciplinaShouldNotBeFound("recurso.greaterThan=" + DEFAULT_RECURSO);

        // Get all the notasGeralDisciplinaList where recurso is greater than SMALLER_RECURSO
        defaultNotasGeralDisciplinaShouldBeFound("recurso.greaterThan=" + SMALLER_RECURSO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameEspecialIsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exameEspecial equals to DEFAULT_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldBeFound("exameEspecial.equals=" + DEFAULT_EXAME_ESPECIAL);

        // Get all the notasGeralDisciplinaList where exameEspecial equals to UPDATED_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldNotBeFound("exameEspecial.equals=" + UPDATED_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameEspecialIsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exameEspecial in DEFAULT_EXAME_ESPECIAL or UPDATED_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldBeFound("exameEspecial.in=" + DEFAULT_EXAME_ESPECIAL + "," + UPDATED_EXAME_ESPECIAL);

        // Get all the notasGeralDisciplinaList where exameEspecial equals to UPDATED_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldNotBeFound("exameEspecial.in=" + UPDATED_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameEspecialIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exameEspecial is not null
        defaultNotasGeralDisciplinaShouldBeFound("exameEspecial.specified=true");

        // Get all the notasGeralDisciplinaList where exameEspecial is null
        defaultNotasGeralDisciplinaShouldNotBeFound("exameEspecial.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameEspecialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exameEspecial is greater than or equal to DEFAULT_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldBeFound("exameEspecial.greaterThanOrEqual=" + DEFAULT_EXAME_ESPECIAL);

        // Get all the notasGeralDisciplinaList where exameEspecial is greater than or equal to UPDATED_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldNotBeFound("exameEspecial.greaterThanOrEqual=" + UPDATED_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameEspecialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exameEspecial is less than or equal to DEFAULT_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldBeFound("exameEspecial.lessThanOrEqual=" + DEFAULT_EXAME_ESPECIAL);

        // Get all the notasGeralDisciplinaList where exameEspecial is less than or equal to SMALLER_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldNotBeFound("exameEspecial.lessThanOrEqual=" + SMALLER_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameEspecialIsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exameEspecial is less than DEFAULT_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldNotBeFound("exameEspecial.lessThan=" + DEFAULT_EXAME_ESPECIAL);

        // Get all the notasGeralDisciplinaList where exameEspecial is less than UPDATED_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldBeFound("exameEspecial.lessThan=" + UPDATED_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByExameEspecialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where exameEspecial is greater than DEFAULT_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldNotBeFound("exameEspecial.greaterThan=" + DEFAULT_EXAME_ESPECIAL);

        // Get all the notasGeralDisciplinaList where exameEspecial is greater than SMALLER_EXAME_ESPECIAL
        defaultNotasGeralDisciplinaShouldBeFound("exameEspecial.greaterThan=" + SMALLER_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByNotaConselhoIsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where notaConselho equals to DEFAULT_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldBeFound("notaConselho.equals=" + DEFAULT_NOTA_CONSELHO);

        // Get all the notasGeralDisciplinaList where notaConselho equals to UPDATED_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldNotBeFound("notaConselho.equals=" + UPDATED_NOTA_CONSELHO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByNotaConselhoIsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where notaConselho in DEFAULT_NOTA_CONSELHO or UPDATED_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldBeFound("notaConselho.in=" + DEFAULT_NOTA_CONSELHO + "," + UPDATED_NOTA_CONSELHO);

        // Get all the notasGeralDisciplinaList where notaConselho equals to UPDATED_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldNotBeFound("notaConselho.in=" + UPDATED_NOTA_CONSELHO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByNotaConselhoIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where notaConselho is not null
        defaultNotasGeralDisciplinaShouldBeFound("notaConselho.specified=true");

        // Get all the notasGeralDisciplinaList where notaConselho is null
        defaultNotasGeralDisciplinaShouldNotBeFound("notaConselho.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByNotaConselhoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where notaConselho is greater than or equal to DEFAULT_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldBeFound("notaConselho.greaterThanOrEqual=" + DEFAULT_NOTA_CONSELHO);

        // Get all the notasGeralDisciplinaList where notaConselho is greater than or equal to UPDATED_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldNotBeFound("notaConselho.greaterThanOrEqual=" + UPDATED_NOTA_CONSELHO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByNotaConselhoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where notaConselho is less than or equal to DEFAULT_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldBeFound("notaConselho.lessThanOrEqual=" + DEFAULT_NOTA_CONSELHO);

        // Get all the notasGeralDisciplinaList where notaConselho is less than or equal to SMALLER_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldNotBeFound("notaConselho.lessThanOrEqual=" + SMALLER_NOTA_CONSELHO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByNotaConselhoIsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where notaConselho is less than DEFAULT_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldNotBeFound("notaConselho.lessThan=" + DEFAULT_NOTA_CONSELHO);

        // Get all the notasGeralDisciplinaList where notaConselho is less than UPDATED_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldBeFound("notaConselho.lessThan=" + UPDATED_NOTA_CONSELHO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByNotaConselhoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where notaConselho is greater than DEFAULT_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldNotBeFound("notaConselho.greaterThan=" + DEFAULT_NOTA_CONSELHO);

        // Get all the notasGeralDisciplinaList where notaConselho is greater than SMALLER_NOTA_CONSELHO
        defaultNotasGeralDisciplinaShouldBeFound("notaConselho.greaterThan=" + SMALLER_NOTA_CONSELHO);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMediaFinalDisciplinaIsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina equals to DEFAULT_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldBeFound("mediaFinalDisciplina.equals=" + DEFAULT_MEDIA_FINAL_DISCIPLINA);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina equals to UPDATED_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldNotBeFound("mediaFinalDisciplina.equals=" + UPDATED_MEDIA_FINAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMediaFinalDisciplinaIsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina in DEFAULT_MEDIA_FINAL_DISCIPLINA or UPDATED_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldBeFound(
            "mediaFinalDisciplina.in=" + DEFAULT_MEDIA_FINAL_DISCIPLINA + "," + UPDATED_MEDIA_FINAL_DISCIPLINA
        );

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina equals to UPDATED_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldNotBeFound("mediaFinalDisciplina.in=" + UPDATED_MEDIA_FINAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMediaFinalDisciplinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina is not null
        defaultNotasGeralDisciplinaShouldBeFound("mediaFinalDisciplina.specified=true");

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina is null
        defaultNotasGeralDisciplinaShouldNotBeFound("mediaFinalDisciplina.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMediaFinalDisciplinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina is greater than or equal to DEFAULT_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldBeFound("mediaFinalDisciplina.greaterThanOrEqual=" + DEFAULT_MEDIA_FINAL_DISCIPLINA);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina is greater than or equal to UPDATED_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldNotBeFound("mediaFinalDisciplina.greaterThanOrEqual=" + UPDATED_MEDIA_FINAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMediaFinalDisciplinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina is less than or equal to DEFAULT_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldBeFound("mediaFinalDisciplina.lessThanOrEqual=" + DEFAULT_MEDIA_FINAL_DISCIPLINA);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina is less than or equal to SMALLER_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldNotBeFound("mediaFinalDisciplina.lessThanOrEqual=" + SMALLER_MEDIA_FINAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMediaFinalDisciplinaIsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina is less than DEFAULT_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldNotBeFound("mediaFinalDisciplina.lessThan=" + DEFAULT_MEDIA_FINAL_DISCIPLINA);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina is less than UPDATED_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldBeFound("mediaFinalDisciplina.lessThan=" + UPDATED_MEDIA_FINAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMediaFinalDisciplinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina is greater than DEFAULT_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldNotBeFound("mediaFinalDisciplina.greaterThan=" + DEFAULT_MEDIA_FINAL_DISCIPLINA);

        // Get all the notasGeralDisciplinaList where mediaFinalDisciplina is greater than SMALLER_MEDIA_FINAL_DISCIPLINA
        defaultNotasGeralDisciplinaShouldBeFound("mediaFinalDisciplina.greaterThan=" + SMALLER_MEDIA_FINAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where timestamp equals to DEFAULT_TIMESTAMP
        defaultNotasGeralDisciplinaShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the notasGeralDisciplinaList where timestamp equals to UPDATED_TIMESTAMP
        defaultNotasGeralDisciplinaShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultNotasGeralDisciplinaShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the notasGeralDisciplinaList where timestamp equals to UPDATED_TIMESTAMP
        defaultNotasGeralDisciplinaShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where timestamp is not null
        defaultNotasGeralDisciplinaShouldBeFound("timestamp.specified=true");

        // Get all the notasGeralDisciplinaList where timestamp is null
        defaultNotasGeralDisciplinaShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultNotasGeralDisciplinaShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the notasGeralDisciplinaList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultNotasGeralDisciplinaShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultNotasGeralDisciplinaShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the notasGeralDisciplinaList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultNotasGeralDisciplinaShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where timestamp is less than DEFAULT_TIMESTAMP
        defaultNotasGeralDisciplinaShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the notasGeralDisciplinaList where timestamp is less than UPDATED_TIMESTAMP
        defaultNotasGeralDisciplinaShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultNotasGeralDisciplinaShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the notasGeralDisciplinaList where timestamp is greater than SMALLER_TIMESTAMP
        defaultNotasGeralDisciplinaShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where hash equals to DEFAULT_HASH
        defaultNotasGeralDisciplinaShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the notasGeralDisciplinaList where hash equals to UPDATED_HASH
        defaultNotasGeralDisciplinaShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByHashIsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultNotasGeralDisciplinaShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the notasGeralDisciplinaList where hash equals to UPDATED_HASH
        defaultNotasGeralDisciplinaShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where hash is not null
        defaultNotasGeralDisciplinaShouldBeFound("hash.specified=true");

        // Get all the notasGeralDisciplinaList where hash is null
        defaultNotasGeralDisciplinaShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByHashContainsSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where hash contains DEFAULT_HASH
        defaultNotasGeralDisciplinaShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the notasGeralDisciplinaList where hash contains UPDATED_HASH
        defaultNotasGeralDisciplinaShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByHashNotContainsSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where hash does not contain DEFAULT_HASH
        defaultNotasGeralDisciplinaShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the notasGeralDisciplinaList where hash does not contain UPDATED_HASH
        defaultNotasGeralDisciplinaShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaJusticadaIsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaJusticada equals to DEFAULT_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldBeFound("faltaJusticada.equals=" + DEFAULT_FALTA_JUSTICADA);

        // Get all the notasGeralDisciplinaList where faltaJusticada equals to UPDATED_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaJusticada.equals=" + UPDATED_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaJusticadaIsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaJusticada in DEFAULT_FALTA_JUSTICADA or UPDATED_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldBeFound("faltaJusticada.in=" + DEFAULT_FALTA_JUSTICADA + "," + UPDATED_FALTA_JUSTICADA);

        // Get all the notasGeralDisciplinaList where faltaJusticada equals to UPDATED_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaJusticada.in=" + UPDATED_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaJusticadaIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaJusticada is not null
        defaultNotasGeralDisciplinaShouldBeFound("faltaJusticada.specified=true");

        // Get all the notasGeralDisciplinaList where faltaJusticada is null
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaJusticada.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaJusticadaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaJusticada is greater than or equal to DEFAULT_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldBeFound("faltaJusticada.greaterThanOrEqual=" + DEFAULT_FALTA_JUSTICADA);

        // Get all the notasGeralDisciplinaList where faltaJusticada is greater than or equal to UPDATED_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaJusticada.greaterThanOrEqual=" + UPDATED_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaJusticadaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaJusticada is less than or equal to DEFAULT_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldBeFound("faltaJusticada.lessThanOrEqual=" + DEFAULT_FALTA_JUSTICADA);

        // Get all the notasGeralDisciplinaList where faltaJusticada is less than or equal to SMALLER_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaJusticada.lessThanOrEqual=" + SMALLER_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaJusticadaIsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaJusticada is less than DEFAULT_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaJusticada.lessThan=" + DEFAULT_FALTA_JUSTICADA);

        // Get all the notasGeralDisciplinaList where faltaJusticada is less than UPDATED_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldBeFound("faltaJusticada.lessThan=" + UPDATED_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaJusticadaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaJusticada is greater than DEFAULT_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaJusticada.greaterThan=" + DEFAULT_FALTA_JUSTICADA);

        // Get all the notasGeralDisciplinaList where faltaJusticada is greater than SMALLER_FALTA_JUSTICADA
        defaultNotasGeralDisciplinaShouldBeFound("faltaJusticada.greaterThan=" + SMALLER_FALTA_JUSTICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaInjustificadaIsEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaInjustificada equals to DEFAULT_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldBeFound("faltaInjustificada.equals=" + DEFAULT_FALTA_INJUSTIFICADA);

        // Get all the notasGeralDisciplinaList where faltaInjustificada equals to UPDATED_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaInjustificada.equals=" + UPDATED_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaInjustificadaIsInShouldWork() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaInjustificada in DEFAULT_FALTA_INJUSTIFICADA or UPDATED_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldBeFound(
            "faltaInjustificada.in=" + DEFAULT_FALTA_INJUSTIFICADA + "," + UPDATED_FALTA_INJUSTIFICADA
        );

        // Get all the notasGeralDisciplinaList where faltaInjustificada equals to UPDATED_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaInjustificada.in=" + UPDATED_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaInjustificadaIsNullOrNotNull() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaInjustificada is not null
        defaultNotasGeralDisciplinaShouldBeFound("faltaInjustificada.specified=true");

        // Get all the notasGeralDisciplinaList where faltaInjustificada is null
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaInjustificada.specified=false");
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaInjustificadaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaInjustificada is greater than or equal to DEFAULT_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldBeFound("faltaInjustificada.greaterThanOrEqual=" + DEFAULT_FALTA_INJUSTIFICADA);

        // Get all the notasGeralDisciplinaList where faltaInjustificada is greater than or equal to UPDATED_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaInjustificada.greaterThanOrEqual=" + UPDATED_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaInjustificadaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaInjustificada is less than or equal to DEFAULT_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldBeFound("faltaInjustificada.lessThanOrEqual=" + DEFAULT_FALTA_INJUSTIFICADA);

        // Get all the notasGeralDisciplinaList where faltaInjustificada is less than or equal to SMALLER_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaInjustificada.lessThanOrEqual=" + SMALLER_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaInjustificadaIsLessThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaInjustificada is less than DEFAULT_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaInjustificada.lessThan=" + DEFAULT_FALTA_INJUSTIFICADA);

        // Get all the notasGeralDisciplinaList where faltaInjustificada is less than UPDATED_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldBeFound("faltaInjustificada.lessThan=" + UPDATED_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByFaltaInjustificadaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        // Get all the notasGeralDisciplinaList where faltaInjustificada is greater than DEFAULT_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldNotBeFound("faltaInjustificada.greaterThan=" + DEFAULT_FALTA_INJUSTIFICADA);

        // Get all the notasGeralDisciplinaList where faltaInjustificada is greater than SMALLER_FALTA_INJUSTIFICADA
        defaultNotasGeralDisciplinaShouldBeFound("faltaInjustificada.greaterThan=" + SMALLER_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        notasGeralDisciplina.addAnoLectivo(anoLectivo);
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the notasGeralDisciplinaList where anoLectivo equals to anoLectivoId
        defaultNotasGeralDisciplinaShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the notasGeralDisciplinaList where anoLectivo equals to (anoLectivoId + 1)
        defaultNotasGeralDisciplinaShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        notasGeralDisciplina.setUtilizador(utilizador);
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
        Long utilizadorId = utilizador.getId();

        // Get all the notasGeralDisciplinaList where utilizador equals to utilizadorId
        defaultNotasGeralDisciplinaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the notasGeralDisciplinaList where utilizador equals to (utilizadorId + 1)
        defaultNotasGeralDisciplinaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByDocenteIsEqualToSomething() throws Exception {
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
            docente = DocenteResourceIT.createEntity(em);
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(docente);
        em.flush();
        notasGeralDisciplina.setDocente(docente);
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
        Long docenteId = docente.getId();

        // Get all the notasGeralDisciplinaList where docente equals to docenteId
        defaultNotasGeralDisciplinaShouldBeFound("docenteId.equals=" + docenteId);

        // Get all the notasGeralDisciplinaList where docente equals to (docenteId + 1)
        defaultNotasGeralDisciplinaShouldNotBeFound("docenteId.equals=" + (docenteId + 1));
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByDisciplinaCurricularIsEqualToSomething() throws Exception {
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
            disciplinaCurricular = DisciplinaCurricularResourceIT.createEntity(em);
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        em.persist(disciplinaCurricular);
        em.flush();
        notasGeralDisciplina.setDisciplinaCurricular(disciplinaCurricular);
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
        Long disciplinaCurricularId = disciplinaCurricular.getId();

        // Get all the notasGeralDisciplinaList where disciplinaCurricular equals to disciplinaCurricularId
        defaultNotasGeralDisciplinaShouldBeFound("disciplinaCurricularId.equals=" + disciplinaCurricularId);

        // Get all the notasGeralDisciplinaList where disciplinaCurricular equals to (disciplinaCurricularId + 1)
        defaultNotasGeralDisciplinaShouldNotBeFound("disciplinaCurricularId.equals=" + (disciplinaCurricularId + 1));
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByMatriculaIsEqualToSomething() throws Exception {
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
            matricula = MatriculaResourceIT.createEntity(em);
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(matricula);
        em.flush();
        notasGeralDisciplina.setMatricula(matricula);
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
        Long matriculaId = matricula.getId();

        // Get all the notasGeralDisciplinaList where matricula equals to matriculaId
        defaultNotasGeralDisciplinaShouldBeFound("matriculaId.equals=" + matriculaId);

        // Get all the notasGeralDisciplinaList where matricula equals to (matriculaId + 1)
        defaultNotasGeralDisciplinaShouldNotBeFound("matriculaId.equals=" + (matriculaId + 1));
    }

    @Test
    @Transactional
    void getAllNotasGeralDisciplinasByEstadoIsEqualToSomething() throws Exception {
        EstadoDisciplinaCurricular estado;
        if (TestUtil.findAll(em, EstadoDisciplinaCurricular.class).isEmpty()) {
            notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
            estado = EstadoDisciplinaCurricularResourceIT.createEntity(em);
        } else {
            estado = TestUtil.findAll(em, EstadoDisciplinaCurricular.class).get(0);
        }
        em.persist(estado);
        em.flush();
        notasGeralDisciplina.setEstado(estado);
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);
        Long estadoId = estado.getId();

        // Get all the notasGeralDisciplinaList where estado equals to estadoId
        defaultNotasGeralDisciplinaShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the notasGeralDisciplinaList where estado equals to (estadoId + 1)
        defaultNotasGeralDisciplinaShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotasGeralDisciplinaShouldBeFound(String filter) throws Exception {
        restNotasGeralDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notasGeralDisciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta").value(hasItem(DEFAULT_CHAVE_COMPOSTA)))
            .andExpect(jsonPath("$.[*].periodoLancamento").value(hasItem(DEFAULT_PERIODO_LANCAMENTO)))
            .andExpect(jsonPath("$.[*].media1").value(hasItem(DEFAULT_MEDIA_1.doubleValue())))
            .andExpect(jsonPath("$.[*].media2").value(hasItem(DEFAULT_MEDIA_2.doubleValue())))
            .andExpect(jsonPath("$.[*].media3").value(hasItem(DEFAULT_MEDIA_3.doubleValue())))
            .andExpect(jsonPath("$.[*].exame").value(hasItem(DEFAULT_EXAME.doubleValue())))
            .andExpect(jsonPath("$.[*].recurso").value(hasItem(DEFAULT_RECURSO.doubleValue())))
            .andExpect(jsonPath("$.[*].exameEspecial").value(hasItem(DEFAULT_EXAME_ESPECIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].notaConselho").value(hasItem(DEFAULT_NOTA_CONSELHO.doubleValue())))
            .andExpect(jsonPath("$.[*].mediaFinalDisciplina").value(hasItem(DEFAULT_MEDIA_FINAL_DISCIPLINA.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].faltaJusticada").value(hasItem(DEFAULT_FALTA_JUSTICADA)))
            .andExpect(jsonPath("$.[*].faltaInjustificada").value(hasItem(DEFAULT_FALTA_INJUSTIFICADA)));

        // Check, that the count call also returns 1
        restNotasGeralDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotasGeralDisciplinaShouldNotBeFound(String filter) throws Exception {
        restNotasGeralDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotasGeralDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotasGeralDisciplina() throws Exception {
        // Get the notasGeralDisciplina
        restNotasGeralDisciplinaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotasGeralDisciplina() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        int databaseSizeBeforeUpdate = notasGeralDisciplinaRepository.findAll().size();

        // Update the notasGeralDisciplina
        NotasGeralDisciplina updatedNotasGeralDisciplina = notasGeralDisciplinaRepository.findById(notasGeralDisciplina.getId()).get();
        // Disconnect from session so that the updates on updatedNotasGeralDisciplina are not directly saved in db
        em.detach(updatedNotasGeralDisciplina);
        updatedNotasGeralDisciplina
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .periodoLancamento(UPDATED_PERIODO_LANCAMENTO)
            .media1(UPDATED_MEDIA_1)
            .media2(UPDATED_MEDIA_2)
            .media3(UPDATED_MEDIA_3)
            .exame(UPDATED_EXAME)
            .recurso(UPDATED_RECURSO)
            .exameEspecial(UPDATED_EXAME_ESPECIAL)
            .notaConselho(UPDATED_NOTA_CONSELHO)
            .mediaFinalDisciplina(UPDATED_MEDIA_FINAL_DISCIPLINA)
            .timestamp(UPDATED_TIMESTAMP)
            .hash(UPDATED_HASH)
            .faltaJusticada(UPDATED_FALTA_JUSTICADA)
            .faltaInjustificada(UPDATED_FALTA_INJUSTIFICADA);
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO = notasGeralDisciplinaMapper.toDto(updatedNotasGeralDisciplina);

        restNotasGeralDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notasGeralDisciplinaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasGeralDisciplinaDTO))
            )
            .andExpect(status().isOk());

        // Validate the NotasGeralDisciplina in the database
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeUpdate);
        NotasGeralDisciplina testNotasGeralDisciplina = notasGeralDisciplinaList.get(notasGeralDisciplinaList.size() - 1);
        assertThat(testNotasGeralDisciplina.getChaveComposta()).isEqualTo(UPDATED_CHAVE_COMPOSTA);
        assertThat(testNotasGeralDisciplina.getPeriodoLancamento()).isEqualTo(UPDATED_PERIODO_LANCAMENTO);
        assertThat(testNotasGeralDisciplina.getMedia1()).isEqualTo(UPDATED_MEDIA_1);
        assertThat(testNotasGeralDisciplina.getMedia2()).isEqualTo(UPDATED_MEDIA_2);
        assertThat(testNotasGeralDisciplina.getMedia3()).isEqualTo(UPDATED_MEDIA_3);
        assertThat(testNotasGeralDisciplina.getExame()).isEqualTo(UPDATED_EXAME);
        assertThat(testNotasGeralDisciplina.getRecurso()).isEqualTo(UPDATED_RECURSO);
        assertThat(testNotasGeralDisciplina.getExameEspecial()).isEqualTo(UPDATED_EXAME_ESPECIAL);
        assertThat(testNotasGeralDisciplina.getNotaConselho()).isEqualTo(UPDATED_NOTA_CONSELHO);
        assertThat(testNotasGeralDisciplina.getMediaFinalDisciplina()).isEqualTo(UPDATED_MEDIA_FINAL_DISCIPLINA);
        assertThat(testNotasGeralDisciplina.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testNotasGeralDisciplina.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testNotasGeralDisciplina.getFaltaJusticada()).isEqualTo(UPDATED_FALTA_JUSTICADA);
        assertThat(testNotasGeralDisciplina.getFaltaInjustificada()).isEqualTo(UPDATED_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void putNonExistingNotasGeralDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasGeralDisciplinaRepository.findAll().size();
        notasGeralDisciplina.setId(count.incrementAndGet());

        // Create the NotasGeralDisciplina
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO = notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotasGeralDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notasGeralDisciplinaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasGeralDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotasGeralDisciplina in the database
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotasGeralDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasGeralDisciplinaRepository.findAll().size();
        notasGeralDisciplina.setId(count.incrementAndGet());

        // Create the NotasGeralDisciplina
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO = notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasGeralDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasGeralDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotasGeralDisciplina in the database
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotasGeralDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasGeralDisciplinaRepository.findAll().size();
        notasGeralDisciplina.setId(count.incrementAndGet());

        // Create the NotasGeralDisciplina
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO = notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasGeralDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notasGeralDisciplinaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotasGeralDisciplina in the database
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotasGeralDisciplinaWithPatch() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        int databaseSizeBeforeUpdate = notasGeralDisciplinaRepository.findAll().size();

        // Update the notasGeralDisciplina using partial update
        NotasGeralDisciplina partialUpdatedNotasGeralDisciplina = new NotasGeralDisciplina();
        partialUpdatedNotasGeralDisciplina.setId(notasGeralDisciplina.getId());

        partialUpdatedNotasGeralDisciplina
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .periodoLancamento(UPDATED_PERIODO_LANCAMENTO)
            .media1(UPDATED_MEDIA_1)
            .media2(UPDATED_MEDIA_2)
            .media3(UPDATED_MEDIA_3)
            .notaConselho(UPDATED_NOTA_CONSELHO)
            .mediaFinalDisciplina(UPDATED_MEDIA_FINAL_DISCIPLINA)
            .faltaJusticada(UPDATED_FALTA_JUSTICADA);

        restNotasGeralDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotasGeralDisciplina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotasGeralDisciplina))
            )
            .andExpect(status().isOk());

        // Validate the NotasGeralDisciplina in the database
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeUpdate);
        NotasGeralDisciplina testNotasGeralDisciplina = notasGeralDisciplinaList.get(notasGeralDisciplinaList.size() - 1);
        assertThat(testNotasGeralDisciplina.getChaveComposta()).isEqualTo(UPDATED_CHAVE_COMPOSTA);
        assertThat(testNotasGeralDisciplina.getPeriodoLancamento()).isEqualTo(UPDATED_PERIODO_LANCAMENTO);
        assertThat(testNotasGeralDisciplina.getMedia1()).isEqualTo(UPDATED_MEDIA_1);
        assertThat(testNotasGeralDisciplina.getMedia2()).isEqualTo(UPDATED_MEDIA_2);
        assertThat(testNotasGeralDisciplina.getMedia3()).isEqualTo(UPDATED_MEDIA_3);
        assertThat(testNotasGeralDisciplina.getExame()).isEqualTo(DEFAULT_EXAME);
        assertThat(testNotasGeralDisciplina.getRecurso()).isEqualTo(DEFAULT_RECURSO);
        assertThat(testNotasGeralDisciplina.getExameEspecial()).isEqualTo(DEFAULT_EXAME_ESPECIAL);
        assertThat(testNotasGeralDisciplina.getNotaConselho()).isEqualTo(UPDATED_NOTA_CONSELHO);
        assertThat(testNotasGeralDisciplina.getMediaFinalDisciplina()).isEqualTo(UPDATED_MEDIA_FINAL_DISCIPLINA);
        assertThat(testNotasGeralDisciplina.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testNotasGeralDisciplina.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testNotasGeralDisciplina.getFaltaJusticada()).isEqualTo(UPDATED_FALTA_JUSTICADA);
        assertThat(testNotasGeralDisciplina.getFaltaInjustificada()).isEqualTo(DEFAULT_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void fullUpdateNotasGeralDisciplinaWithPatch() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        int databaseSizeBeforeUpdate = notasGeralDisciplinaRepository.findAll().size();

        // Update the notasGeralDisciplina using partial update
        NotasGeralDisciplina partialUpdatedNotasGeralDisciplina = new NotasGeralDisciplina();
        partialUpdatedNotasGeralDisciplina.setId(notasGeralDisciplina.getId());

        partialUpdatedNotasGeralDisciplina
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .periodoLancamento(UPDATED_PERIODO_LANCAMENTO)
            .media1(UPDATED_MEDIA_1)
            .media2(UPDATED_MEDIA_2)
            .media3(UPDATED_MEDIA_3)
            .exame(UPDATED_EXAME)
            .recurso(UPDATED_RECURSO)
            .exameEspecial(UPDATED_EXAME_ESPECIAL)
            .notaConselho(UPDATED_NOTA_CONSELHO)
            .mediaFinalDisciplina(UPDATED_MEDIA_FINAL_DISCIPLINA)
            .timestamp(UPDATED_TIMESTAMP)
            .hash(UPDATED_HASH)
            .faltaJusticada(UPDATED_FALTA_JUSTICADA)
            .faltaInjustificada(UPDATED_FALTA_INJUSTIFICADA);

        restNotasGeralDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotasGeralDisciplina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotasGeralDisciplina))
            )
            .andExpect(status().isOk());

        // Validate the NotasGeralDisciplina in the database
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeUpdate);
        NotasGeralDisciplina testNotasGeralDisciplina = notasGeralDisciplinaList.get(notasGeralDisciplinaList.size() - 1);
        assertThat(testNotasGeralDisciplina.getChaveComposta()).isEqualTo(UPDATED_CHAVE_COMPOSTA);
        assertThat(testNotasGeralDisciplina.getPeriodoLancamento()).isEqualTo(UPDATED_PERIODO_LANCAMENTO);
        assertThat(testNotasGeralDisciplina.getMedia1()).isEqualTo(UPDATED_MEDIA_1);
        assertThat(testNotasGeralDisciplina.getMedia2()).isEqualTo(UPDATED_MEDIA_2);
        assertThat(testNotasGeralDisciplina.getMedia3()).isEqualTo(UPDATED_MEDIA_3);
        assertThat(testNotasGeralDisciplina.getExame()).isEqualTo(UPDATED_EXAME);
        assertThat(testNotasGeralDisciplina.getRecurso()).isEqualTo(UPDATED_RECURSO);
        assertThat(testNotasGeralDisciplina.getExameEspecial()).isEqualTo(UPDATED_EXAME_ESPECIAL);
        assertThat(testNotasGeralDisciplina.getNotaConselho()).isEqualTo(UPDATED_NOTA_CONSELHO);
        assertThat(testNotasGeralDisciplina.getMediaFinalDisciplina()).isEqualTo(UPDATED_MEDIA_FINAL_DISCIPLINA);
        assertThat(testNotasGeralDisciplina.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testNotasGeralDisciplina.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testNotasGeralDisciplina.getFaltaJusticada()).isEqualTo(UPDATED_FALTA_JUSTICADA);
        assertThat(testNotasGeralDisciplina.getFaltaInjustificada()).isEqualTo(UPDATED_FALTA_INJUSTIFICADA);
    }

    @Test
    @Transactional
    void patchNonExistingNotasGeralDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasGeralDisciplinaRepository.findAll().size();
        notasGeralDisciplina.setId(count.incrementAndGet());

        // Create the NotasGeralDisciplina
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO = notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotasGeralDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notasGeralDisciplinaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notasGeralDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotasGeralDisciplina in the database
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotasGeralDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasGeralDisciplinaRepository.findAll().size();
        notasGeralDisciplina.setId(count.incrementAndGet());

        // Create the NotasGeralDisciplina
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO = notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasGeralDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notasGeralDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotasGeralDisciplina in the database
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotasGeralDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = notasGeralDisciplinaRepository.findAll().size();
        notasGeralDisciplina.setId(count.incrementAndGet());

        // Create the NotasGeralDisciplina
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO = notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasGeralDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notasGeralDisciplinaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotasGeralDisciplina in the database
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotasGeralDisciplina() throws Exception {
        // Initialize the database
        notasGeralDisciplinaRepository.saveAndFlush(notasGeralDisciplina);

        int databaseSizeBeforeDelete = notasGeralDisciplinaRepository.findAll().size();

        // Delete the notasGeralDisciplina
        restNotasGeralDisciplinaMockMvc
            .perform(delete(ENTITY_API_URL_ID, notasGeralDisciplina.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotasGeralDisciplina> notasGeralDisciplinaList = notasGeralDisciplinaRepository.findAll();
        assertThat(notasGeralDisciplinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
