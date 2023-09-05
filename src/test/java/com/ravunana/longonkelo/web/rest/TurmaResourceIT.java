package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.DissertacaoFinalCurso;
import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.domain.PlanoCurricular;
import com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula;
import com.ravunana.longonkelo.domain.ResponsavelTurma;
import com.ravunana.longonkelo.domain.ResumoAcademico;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.Turno;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.domain.enumeration.CriterioDescricaoTurma;
import com.ravunana.longonkelo.domain.enumeration.CriterioNumeroChamada;
import com.ravunana.longonkelo.domain.enumeration.TipoTurma;
import com.ravunana.longonkelo.repository.TurmaRepository;
import com.ravunana.longonkelo.service.TurmaService;
import com.ravunana.longonkelo.service.criteria.TurmaCriteria;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.mapper.TurmaMapper;
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

/**
 * Integration tests for the {@link TurmaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TurmaResourceIT {

    private static final String DEFAULT_CHAVE_COMPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE_COMPOSTA = "BBBBBBBBBB";

    private static final TipoTurma DEFAULT_TIPO_TURMA = TipoTurma.AULA;
    private static final TipoTurma UPDATED_TIPO_TURMA = TipoTurma.LABORATORIO;

    private static final Integer DEFAULT_SALA = 1;
    private static final Integer UPDATED_SALA = 2;
    private static final Integer SMALLER_SALA = 1 - 1;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_LOTACAO = 1;
    private static final Integer UPDATED_LOTACAO = 2;
    private static final Integer SMALLER_LOTACAO = 1 - 1;

    private static final Integer DEFAULT_CONFIRMADO = 0;
    private static final Integer UPDATED_CONFIRMADO = 1;
    private static final Integer SMALLER_CONFIRMADO = 0 - 1;

    private static final LocalDate DEFAULT_ABERTURA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ABERTURA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ABERTURA = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_ENCERRAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENCERRAMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ENCERRAMENTO = LocalDate.ofEpochDay(-1L);

    private static final CriterioDescricaoTurma DEFAULT_CRITERIO_DESCRICAO = CriterioDescricaoTurma.ALFABETICA;
    private static final CriterioDescricaoTurma UPDATED_CRITERIO_DESCRICAO = CriterioDescricaoTurma.CURSO;

    private static final CriterioNumeroChamada DEFAULT_CRITERIO_ORDENACAO_NUMERO = CriterioNumeroChamada.ALFABETICA;
    private static final CriterioNumeroChamada UPDATED_CRITERIO_ORDENACAO_NUMERO = CriterioNumeroChamada.DATA_CONFIRMACAO;

    private static final Boolean DEFAULT_FAZ_INSCRICAO_DEPOIS_MATRICULA = false;
    private static final Boolean UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA = true;

    private static final Boolean DEFAULT_IS_DISPONIVEL = false;
    private static final Boolean UPDATED_IS_DISPONIVEL = true;

    private static final String ENTITY_API_URL = "/api/turmas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TurmaRepository turmaRepository;

    @Mock
    private TurmaRepository turmaRepositoryMock;

    @Autowired
    private TurmaMapper turmaMapper;

    @Mock
    private TurmaService turmaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTurmaMockMvc;

    private Turma turma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turma createEntity(EntityManager em) {
        Turma turma = new Turma()
            .chaveComposta(DEFAULT_CHAVE_COMPOSTA)
            .tipoTurma(DEFAULT_TIPO_TURMA)
            .sala(DEFAULT_SALA)
            .descricao(DEFAULT_DESCRICAO)
            .lotacao(DEFAULT_LOTACAO)
            .confirmado(DEFAULT_CONFIRMADO)
            .abertura(DEFAULT_ABERTURA)
            .encerramento(DEFAULT_ENCERRAMENTO)
            .criterioDescricao(DEFAULT_CRITERIO_DESCRICAO)
            .criterioOrdenacaoNumero(DEFAULT_CRITERIO_ORDENACAO_NUMERO)
            .fazInscricaoDepoisMatricula(DEFAULT_FAZ_INSCRICAO_DEPOIS_MATRICULA)
            .isDisponivel(DEFAULT_IS_DISPONIVEL);
        // Add required entity
        PlanoCurricular planoCurricular;
        if (TestUtil.findAll(em, PlanoCurricular.class).isEmpty()) {
            planoCurricular = PlanoCurricularResourceIT.createEntity(em);
            em.persist(planoCurricular);
            em.flush();
        } else {
            planoCurricular = TestUtil.findAll(em, PlanoCurricular.class).get(0);
        }
        turma.setPlanoCurricular(planoCurricular);
        // Add required entity
        Turno turno;
        if (TestUtil.findAll(em, Turno.class).isEmpty()) {
            turno = TurnoResourceIT.createEntity(em);
            em.persist(turno);
            em.flush();
        } else {
            turno = TestUtil.findAll(em, Turno.class).get(0);
        }
        turma.setTurno(turno);
        return turma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turma createUpdatedEntity(EntityManager em) {
        Turma turma = new Turma()
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .tipoTurma(UPDATED_TIPO_TURMA)
            .sala(UPDATED_SALA)
            .descricao(UPDATED_DESCRICAO)
            .lotacao(UPDATED_LOTACAO)
            .confirmado(UPDATED_CONFIRMADO)
            .abertura(UPDATED_ABERTURA)
            .encerramento(UPDATED_ENCERRAMENTO)
            .criterioDescricao(UPDATED_CRITERIO_DESCRICAO)
            .criterioOrdenacaoNumero(UPDATED_CRITERIO_ORDENACAO_NUMERO)
            .fazInscricaoDepoisMatricula(UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA)
            .isDisponivel(UPDATED_IS_DISPONIVEL);
        // Add required entity
        PlanoCurricular planoCurricular;
        if (TestUtil.findAll(em, PlanoCurricular.class).isEmpty()) {
            planoCurricular = PlanoCurricularResourceIT.createUpdatedEntity(em);
            em.persist(planoCurricular);
            em.flush();
        } else {
            planoCurricular = TestUtil.findAll(em, PlanoCurricular.class).get(0);
        }
        turma.setPlanoCurricular(planoCurricular);
        // Add required entity
        Turno turno;
        if (TestUtil.findAll(em, Turno.class).isEmpty()) {
            turno = TurnoResourceIT.createUpdatedEntity(em);
            em.persist(turno);
            em.flush();
        } else {
            turno = TestUtil.findAll(em, Turno.class).get(0);
        }
        turma.setTurno(turno);
        return turma;
    }

    @BeforeEach
    public void initTest() {
        turma = createEntity(em);
    }

    @Test
    @Transactional
    void createTurma() throws Exception {
        int databaseSizeBeforeCreate = turmaRepository.findAll().size();
        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);
        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isCreated());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeCreate + 1);
        Turma testTurma = turmaList.get(turmaList.size() - 1);
        assertThat(testTurma.getChaveComposta()).isEqualTo(DEFAULT_CHAVE_COMPOSTA);
        assertThat(testTurma.getTipoTurma()).isEqualTo(DEFAULT_TIPO_TURMA);
        assertThat(testTurma.getSala()).isEqualTo(DEFAULT_SALA);
        assertThat(testTurma.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTurma.getLotacao()).isEqualTo(DEFAULT_LOTACAO);
        assertThat(testTurma.getConfirmado()).isEqualTo(DEFAULT_CONFIRMADO);
        assertThat(testTurma.getAbertura()).isEqualTo(DEFAULT_ABERTURA);
        assertThat(testTurma.getEncerramento()).isEqualTo(DEFAULT_ENCERRAMENTO);
        assertThat(testTurma.getCriterioDescricao()).isEqualTo(DEFAULT_CRITERIO_DESCRICAO);
        assertThat(testTurma.getCriterioOrdenacaoNumero()).isEqualTo(DEFAULT_CRITERIO_ORDENACAO_NUMERO);
        assertThat(testTurma.getFazInscricaoDepoisMatricula()).isEqualTo(DEFAULT_FAZ_INSCRICAO_DEPOIS_MATRICULA);
        assertThat(testTurma.getIsDisponivel()).isEqualTo(DEFAULT_IS_DISPONIVEL);
    }

    @Test
    @Transactional
    void createTurmaWithExistingId() throws Exception {
        // Create the Turma with an existing ID
        turma.setId(1L);
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        int databaseSizeBeforeCreate = turmaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoTurmaIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setTipoTurma(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSalaIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setSala(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setDescricao(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLotacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setLotacao(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConfirmadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setConfirmado(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTurmas() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turma.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta").value(hasItem(DEFAULT_CHAVE_COMPOSTA)))
            .andExpect(jsonPath("$.[*].tipoTurma").value(hasItem(DEFAULT_TIPO_TURMA.toString())))
            .andExpect(jsonPath("$.[*].sala").value(hasItem(DEFAULT_SALA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].lotacao").value(hasItem(DEFAULT_LOTACAO)))
            .andExpect(jsonPath("$.[*].confirmado").value(hasItem(DEFAULT_CONFIRMADO)))
            .andExpect(jsonPath("$.[*].abertura").value(hasItem(DEFAULT_ABERTURA.toString())))
            .andExpect(jsonPath("$.[*].encerramento").value(hasItem(DEFAULT_ENCERRAMENTO.toString())))
            .andExpect(jsonPath("$.[*].criterioDescricao").value(hasItem(DEFAULT_CRITERIO_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].criterioOrdenacaoNumero").value(hasItem(DEFAULT_CRITERIO_ORDENACAO_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].fazInscricaoDepoisMatricula").value(hasItem(DEFAULT_FAZ_INSCRICAO_DEPOIS_MATRICULA.booleanValue())))
            .andExpect(jsonPath("$.[*].isDisponivel").value(hasItem(DEFAULT_IS_DISPONIVEL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTurmasWithEagerRelationshipsIsEnabled() throws Exception {
        when(turmaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTurmaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(turmaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTurmasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(turmaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTurmaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(turmaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get the turma
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL_ID, turma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(turma.getId().intValue()))
            .andExpect(jsonPath("$.chaveComposta").value(DEFAULT_CHAVE_COMPOSTA))
            .andExpect(jsonPath("$.tipoTurma").value(DEFAULT_TIPO_TURMA.toString()))
            .andExpect(jsonPath("$.sala").value(DEFAULT_SALA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.lotacao").value(DEFAULT_LOTACAO))
            .andExpect(jsonPath("$.confirmado").value(DEFAULT_CONFIRMADO))
            .andExpect(jsonPath("$.abertura").value(DEFAULT_ABERTURA.toString()))
            .andExpect(jsonPath("$.encerramento").value(DEFAULT_ENCERRAMENTO.toString()))
            .andExpect(jsonPath("$.criterioDescricao").value(DEFAULT_CRITERIO_DESCRICAO.toString()))
            .andExpect(jsonPath("$.criterioOrdenacaoNumero").value(DEFAULT_CRITERIO_ORDENACAO_NUMERO.toString()))
            .andExpect(jsonPath("$.fazInscricaoDepoisMatricula").value(DEFAULT_FAZ_INSCRICAO_DEPOIS_MATRICULA.booleanValue()))
            .andExpect(jsonPath("$.isDisponivel").value(DEFAULT_IS_DISPONIVEL.booleanValue()));
    }

    @Test
    @Transactional
    void getTurmasByIdFiltering() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        Long id = turma.getId();

        defaultTurmaShouldBeFound("id.equals=" + id);
        defaultTurmaShouldNotBeFound("id.notEquals=" + id);

        defaultTurmaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTurmaShouldNotBeFound("id.greaterThan=" + id);

        defaultTurmaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTurmaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTurmasByChaveCompostaIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where chaveComposta equals to DEFAULT_CHAVE_COMPOSTA
        defaultTurmaShouldBeFound("chaveComposta.equals=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the turmaList where chaveComposta equals to UPDATED_CHAVE_COMPOSTA
        defaultTurmaShouldNotBeFound("chaveComposta.equals=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllTurmasByChaveCompostaIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where chaveComposta in DEFAULT_CHAVE_COMPOSTA or UPDATED_CHAVE_COMPOSTA
        defaultTurmaShouldBeFound("chaveComposta.in=" + DEFAULT_CHAVE_COMPOSTA + "," + UPDATED_CHAVE_COMPOSTA);

        // Get all the turmaList where chaveComposta equals to UPDATED_CHAVE_COMPOSTA
        defaultTurmaShouldNotBeFound("chaveComposta.in=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllTurmasByChaveCompostaIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where chaveComposta is not null
        defaultTurmaShouldBeFound("chaveComposta.specified=true");

        // Get all the turmaList where chaveComposta is null
        defaultTurmaShouldNotBeFound("chaveComposta.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByChaveCompostaContainsSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where chaveComposta contains DEFAULT_CHAVE_COMPOSTA
        defaultTurmaShouldBeFound("chaveComposta.contains=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the turmaList where chaveComposta contains UPDATED_CHAVE_COMPOSTA
        defaultTurmaShouldNotBeFound("chaveComposta.contains=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllTurmasByChaveCompostaNotContainsSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where chaveComposta does not contain DEFAULT_CHAVE_COMPOSTA
        defaultTurmaShouldNotBeFound("chaveComposta.doesNotContain=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the turmaList where chaveComposta does not contain UPDATED_CHAVE_COMPOSTA
        defaultTurmaShouldBeFound("chaveComposta.doesNotContain=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllTurmasByTipoTurmaIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where tipoTurma equals to DEFAULT_TIPO_TURMA
        defaultTurmaShouldBeFound("tipoTurma.equals=" + DEFAULT_TIPO_TURMA);

        // Get all the turmaList where tipoTurma equals to UPDATED_TIPO_TURMA
        defaultTurmaShouldNotBeFound("tipoTurma.equals=" + UPDATED_TIPO_TURMA);
    }

    @Test
    @Transactional
    void getAllTurmasByTipoTurmaIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where tipoTurma in DEFAULT_TIPO_TURMA or UPDATED_TIPO_TURMA
        defaultTurmaShouldBeFound("tipoTurma.in=" + DEFAULT_TIPO_TURMA + "," + UPDATED_TIPO_TURMA);

        // Get all the turmaList where tipoTurma equals to UPDATED_TIPO_TURMA
        defaultTurmaShouldNotBeFound("tipoTurma.in=" + UPDATED_TIPO_TURMA);
    }

    @Test
    @Transactional
    void getAllTurmasByTipoTurmaIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where tipoTurma is not null
        defaultTurmaShouldBeFound("tipoTurma.specified=true");

        // Get all the turmaList where tipoTurma is null
        defaultTurmaShouldNotBeFound("tipoTurma.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasBySalaIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where sala equals to DEFAULT_SALA
        defaultTurmaShouldBeFound("sala.equals=" + DEFAULT_SALA);

        // Get all the turmaList where sala equals to UPDATED_SALA
        defaultTurmaShouldNotBeFound("sala.equals=" + UPDATED_SALA);
    }

    @Test
    @Transactional
    void getAllTurmasBySalaIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where sala in DEFAULT_SALA or UPDATED_SALA
        defaultTurmaShouldBeFound("sala.in=" + DEFAULT_SALA + "," + UPDATED_SALA);

        // Get all the turmaList where sala equals to UPDATED_SALA
        defaultTurmaShouldNotBeFound("sala.in=" + UPDATED_SALA);
    }

    @Test
    @Transactional
    void getAllTurmasBySalaIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where sala is not null
        defaultTurmaShouldBeFound("sala.specified=true");

        // Get all the turmaList where sala is null
        defaultTurmaShouldNotBeFound("sala.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasBySalaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where sala is greater than or equal to DEFAULT_SALA
        defaultTurmaShouldBeFound("sala.greaterThanOrEqual=" + DEFAULT_SALA);

        // Get all the turmaList where sala is greater than or equal to UPDATED_SALA
        defaultTurmaShouldNotBeFound("sala.greaterThanOrEqual=" + UPDATED_SALA);
    }

    @Test
    @Transactional
    void getAllTurmasBySalaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where sala is less than or equal to DEFAULT_SALA
        defaultTurmaShouldBeFound("sala.lessThanOrEqual=" + DEFAULT_SALA);

        // Get all the turmaList where sala is less than or equal to SMALLER_SALA
        defaultTurmaShouldNotBeFound("sala.lessThanOrEqual=" + SMALLER_SALA);
    }

    @Test
    @Transactional
    void getAllTurmasBySalaIsLessThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where sala is less than DEFAULT_SALA
        defaultTurmaShouldNotBeFound("sala.lessThan=" + DEFAULT_SALA);

        // Get all the turmaList where sala is less than UPDATED_SALA
        defaultTurmaShouldBeFound("sala.lessThan=" + UPDATED_SALA);
    }

    @Test
    @Transactional
    void getAllTurmasBySalaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where sala is greater than DEFAULT_SALA
        defaultTurmaShouldNotBeFound("sala.greaterThan=" + DEFAULT_SALA);

        // Get all the turmaList where sala is greater than SMALLER_SALA
        defaultTurmaShouldBeFound("sala.greaterThan=" + SMALLER_SALA);
    }

    @Test
    @Transactional
    void getAllTurmasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where descricao equals to DEFAULT_DESCRICAO
        defaultTurmaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the turmaList where descricao equals to UPDATED_DESCRICAO
        defaultTurmaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTurmasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTurmaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the turmaList where descricao equals to UPDATED_DESCRICAO
        defaultTurmaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTurmasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where descricao is not null
        defaultTurmaShouldBeFound("descricao.specified=true");

        // Get all the turmaList where descricao is null
        defaultTurmaShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where descricao contains DEFAULT_DESCRICAO
        defaultTurmaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the turmaList where descricao contains UPDATED_DESCRICAO
        defaultTurmaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTurmasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where descricao does not contain DEFAULT_DESCRICAO
        defaultTurmaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the turmaList where descricao does not contain UPDATED_DESCRICAO
        defaultTurmaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTurmasByLotacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where lotacao equals to DEFAULT_LOTACAO
        defaultTurmaShouldBeFound("lotacao.equals=" + DEFAULT_LOTACAO);

        // Get all the turmaList where lotacao equals to UPDATED_LOTACAO
        defaultTurmaShouldNotBeFound("lotacao.equals=" + UPDATED_LOTACAO);
    }

    @Test
    @Transactional
    void getAllTurmasByLotacaoIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where lotacao in DEFAULT_LOTACAO or UPDATED_LOTACAO
        defaultTurmaShouldBeFound("lotacao.in=" + DEFAULT_LOTACAO + "," + UPDATED_LOTACAO);

        // Get all the turmaList where lotacao equals to UPDATED_LOTACAO
        defaultTurmaShouldNotBeFound("lotacao.in=" + UPDATED_LOTACAO);
    }

    @Test
    @Transactional
    void getAllTurmasByLotacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where lotacao is not null
        defaultTurmaShouldBeFound("lotacao.specified=true");

        // Get all the turmaList where lotacao is null
        defaultTurmaShouldNotBeFound("lotacao.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByLotacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where lotacao is greater than or equal to DEFAULT_LOTACAO
        defaultTurmaShouldBeFound("lotacao.greaterThanOrEqual=" + DEFAULT_LOTACAO);

        // Get all the turmaList where lotacao is greater than or equal to UPDATED_LOTACAO
        defaultTurmaShouldNotBeFound("lotacao.greaterThanOrEqual=" + UPDATED_LOTACAO);
    }

    @Test
    @Transactional
    void getAllTurmasByLotacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where lotacao is less than or equal to DEFAULT_LOTACAO
        defaultTurmaShouldBeFound("lotacao.lessThanOrEqual=" + DEFAULT_LOTACAO);

        // Get all the turmaList where lotacao is less than or equal to SMALLER_LOTACAO
        defaultTurmaShouldNotBeFound("lotacao.lessThanOrEqual=" + SMALLER_LOTACAO);
    }

    @Test
    @Transactional
    void getAllTurmasByLotacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where lotacao is less than DEFAULT_LOTACAO
        defaultTurmaShouldNotBeFound("lotacao.lessThan=" + DEFAULT_LOTACAO);

        // Get all the turmaList where lotacao is less than UPDATED_LOTACAO
        defaultTurmaShouldBeFound("lotacao.lessThan=" + UPDATED_LOTACAO);
    }

    @Test
    @Transactional
    void getAllTurmasByLotacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where lotacao is greater than DEFAULT_LOTACAO
        defaultTurmaShouldNotBeFound("lotacao.greaterThan=" + DEFAULT_LOTACAO);

        // Get all the turmaList where lotacao is greater than SMALLER_LOTACAO
        defaultTurmaShouldBeFound("lotacao.greaterThan=" + SMALLER_LOTACAO);
    }

    @Test
    @Transactional
    void getAllTurmasByConfirmadoIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where confirmado equals to DEFAULT_CONFIRMADO
        defaultTurmaShouldBeFound("confirmado.equals=" + DEFAULT_CONFIRMADO);

        // Get all the turmaList where confirmado equals to UPDATED_CONFIRMADO
        defaultTurmaShouldNotBeFound("confirmado.equals=" + UPDATED_CONFIRMADO);
    }

    @Test
    @Transactional
    void getAllTurmasByConfirmadoIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where confirmado in DEFAULT_CONFIRMADO or UPDATED_CONFIRMADO
        defaultTurmaShouldBeFound("confirmado.in=" + DEFAULT_CONFIRMADO + "," + UPDATED_CONFIRMADO);

        // Get all the turmaList where confirmado equals to UPDATED_CONFIRMADO
        defaultTurmaShouldNotBeFound("confirmado.in=" + UPDATED_CONFIRMADO);
    }

    @Test
    @Transactional
    void getAllTurmasByConfirmadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where confirmado is not null
        defaultTurmaShouldBeFound("confirmado.specified=true");

        // Get all the turmaList where confirmado is null
        defaultTurmaShouldNotBeFound("confirmado.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByConfirmadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where confirmado is greater than or equal to DEFAULT_CONFIRMADO
        defaultTurmaShouldBeFound("confirmado.greaterThanOrEqual=" + DEFAULT_CONFIRMADO);

        // Get all the turmaList where confirmado is greater than or equal to UPDATED_CONFIRMADO
        defaultTurmaShouldNotBeFound("confirmado.greaterThanOrEqual=" + UPDATED_CONFIRMADO);
    }

    @Test
    @Transactional
    void getAllTurmasByConfirmadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where confirmado is less than or equal to DEFAULT_CONFIRMADO
        defaultTurmaShouldBeFound("confirmado.lessThanOrEqual=" + DEFAULT_CONFIRMADO);

        // Get all the turmaList where confirmado is less than or equal to SMALLER_CONFIRMADO
        defaultTurmaShouldNotBeFound("confirmado.lessThanOrEqual=" + SMALLER_CONFIRMADO);
    }

    @Test
    @Transactional
    void getAllTurmasByConfirmadoIsLessThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where confirmado is less than DEFAULT_CONFIRMADO
        defaultTurmaShouldNotBeFound("confirmado.lessThan=" + DEFAULT_CONFIRMADO);

        // Get all the turmaList where confirmado is less than UPDATED_CONFIRMADO
        defaultTurmaShouldBeFound("confirmado.lessThan=" + UPDATED_CONFIRMADO);
    }

    @Test
    @Transactional
    void getAllTurmasByConfirmadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where confirmado is greater than DEFAULT_CONFIRMADO
        defaultTurmaShouldNotBeFound("confirmado.greaterThan=" + DEFAULT_CONFIRMADO);

        // Get all the turmaList where confirmado is greater than SMALLER_CONFIRMADO
        defaultTurmaShouldBeFound("confirmado.greaterThan=" + SMALLER_CONFIRMADO);
    }

    @Test
    @Transactional
    void getAllTurmasByAberturaIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where abertura equals to DEFAULT_ABERTURA
        defaultTurmaShouldBeFound("abertura.equals=" + DEFAULT_ABERTURA);

        // Get all the turmaList where abertura equals to UPDATED_ABERTURA
        defaultTurmaShouldNotBeFound("abertura.equals=" + UPDATED_ABERTURA);
    }

    @Test
    @Transactional
    void getAllTurmasByAberturaIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where abertura in DEFAULT_ABERTURA or UPDATED_ABERTURA
        defaultTurmaShouldBeFound("abertura.in=" + DEFAULT_ABERTURA + "," + UPDATED_ABERTURA);

        // Get all the turmaList where abertura equals to UPDATED_ABERTURA
        defaultTurmaShouldNotBeFound("abertura.in=" + UPDATED_ABERTURA);
    }

    @Test
    @Transactional
    void getAllTurmasByAberturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where abertura is not null
        defaultTurmaShouldBeFound("abertura.specified=true");

        // Get all the turmaList where abertura is null
        defaultTurmaShouldNotBeFound("abertura.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByAberturaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where abertura is greater than or equal to DEFAULT_ABERTURA
        defaultTurmaShouldBeFound("abertura.greaterThanOrEqual=" + DEFAULT_ABERTURA);

        // Get all the turmaList where abertura is greater than or equal to UPDATED_ABERTURA
        defaultTurmaShouldNotBeFound("abertura.greaterThanOrEqual=" + UPDATED_ABERTURA);
    }

    @Test
    @Transactional
    void getAllTurmasByAberturaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where abertura is less than or equal to DEFAULT_ABERTURA
        defaultTurmaShouldBeFound("abertura.lessThanOrEqual=" + DEFAULT_ABERTURA);

        // Get all the turmaList where abertura is less than or equal to SMALLER_ABERTURA
        defaultTurmaShouldNotBeFound("abertura.lessThanOrEqual=" + SMALLER_ABERTURA);
    }

    @Test
    @Transactional
    void getAllTurmasByAberturaIsLessThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where abertura is less than DEFAULT_ABERTURA
        defaultTurmaShouldNotBeFound("abertura.lessThan=" + DEFAULT_ABERTURA);

        // Get all the turmaList where abertura is less than UPDATED_ABERTURA
        defaultTurmaShouldBeFound("abertura.lessThan=" + UPDATED_ABERTURA);
    }

    @Test
    @Transactional
    void getAllTurmasByAberturaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where abertura is greater than DEFAULT_ABERTURA
        defaultTurmaShouldNotBeFound("abertura.greaterThan=" + DEFAULT_ABERTURA);

        // Get all the turmaList where abertura is greater than SMALLER_ABERTURA
        defaultTurmaShouldBeFound("abertura.greaterThan=" + SMALLER_ABERTURA);
    }

    @Test
    @Transactional
    void getAllTurmasByEncerramentoIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where encerramento equals to DEFAULT_ENCERRAMENTO
        defaultTurmaShouldBeFound("encerramento.equals=" + DEFAULT_ENCERRAMENTO);

        // Get all the turmaList where encerramento equals to UPDATED_ENCERRAMENTO
        defaultTurmaShouldNotBeFound("encerramento.equals=" + UPDATED_ENCERRAMENTO);
    }

    @Test
    @Transactional
    void getAllTurmasByEncerramentoIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where encerramento in DEFAULT_ENCERRAMENTO or UPDATED_ENCERRAMENTO
        defaultTurmaShouldBeFound("encerramento.in=" + DEFAULT_ENCERRAMENTO + "," + UPDATED_ENCERRAMENTO);

        // Get all the turmaList where encerramento equals to UPDATED_ENCERRAMENTO
        defaultTurmaShouldNotBeFound("encerramento.in=" + UPDATED_ENCERRAMENTO);
    }

    @Test
    @Transactional
    void getAllTurmasByEncerramentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where encerramento is not null
        defaultTurmaShouldBeFound("encerramento.specified=true");

        // Get all the turmaList where encerramento is null
        defaultTurmaShouldNotBeFound("encerramento.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByEncerramentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where encerramento is greater than or equal to DEFAULT_ENCERRAMENTO
        defaultTurmaShouldBeFound("encerramento.greaterThanOrEqual=" + DEFAULT_ENCERRAMENTO);

        // Get all the turmaList where encerramento is greater than or equal to UPDATED_ENCERRAMENTO
        defaultTurmaShouldNotBeFound("encerramento.greaterThanOrEqual=" + UPDATED_ENCERRAMENTO);
    }

    @Test
    @Transactional
    void getAllTurmasByEncerramentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where encerramento is less than or equal to DEFAULT_ENCERRAMENTO
        defaultTurmaShouldBeFound("encerramento.lessThanOrEqual=" + DEFAULT_ENCERRAMENTO);

        // Get all the turmaList where encerramento is less than or equal to SMALLER_ENCERRAMENTO
        defaultTurmaShouldNotBeFound("encerramento.lessThanOrEqual=" + SMALLER_ENCERRAMENTO);
    }

    @Test
    @Transactional
    void getAllTurmasByEncerramentoIsLessThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where encerramento is less than DEFAULT_ENCERRAMENTO
        defaultTurmaShouldNotBeFound("encerramento.lessThan=" + DEFAULT_ENCERRAMENTO);

        // Get all the turmaList where encerramento is less than UPDATED_ENCERRAMENTO
        defaultTurmaShouldBeFound("encerramento.lessThan=" + UPDATED_ENCERRAMENTO);
    }

    @Test
    @Transactional
    void getAllTurmasByEncerramentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where encerramento is greater than DEFAULT_ENCERRAMENTO
        defaultTurmaShouldNotBeFound("encerramento.greaterThan=" + DEFAULT_ENCERRAMENTO);

        // Get all the turmaList where encerramento is greater than SMALLER_ENCERRAMENTO
        defaultTurmaShouldBeFound("encerramento.greaterThan=" + SMALLER_ENCERRAMENTO);
    }

    @Test
    @Transactional
    void getAllTurmasByCriterioDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where criterioDescricao equals to DEFAULT_CRITERIO_DESCRICAO
        defaultTurmaShouldBeFound("criterioDescricao.equals=" + DEFAULT_CRITERIO_DESCRICAO);

        // Get all the turmaList where criterioDescricao equals to UPDATED_CRITERIO_DESCRICAO
        defaultTurmaShouldNotBeFound("criterioDescricao.equals=" + UPDATED_CRITERIO_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTurmasByCriterioDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where criterioDescricao in DEFAULT_CRITERIO_DESCRICAO or UPDATED_CRITERIO_DESCRICAO
        defaultTurmaShouldBeFound("criterioDescricao.in=" + DEFAULT_CRITERIO_DESCRICAO + "," + UPDATED_CRITERIO_DESCRICAO);

        // Get all the turmaList where criterioDescricao equals to UPDATED_CRITERIO_DESCRICAO
        defaultTurmaShouldNotBeFound("criterioDescricao.in=" + UPDATED_CRITERIO_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTurmasByCriterioDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where criterioDescricao is not null
        defaultTurmaShouldBeFound("criterioDescricao.specified=true");

        // Get all the turmaList where criterioDescricao is null
        defaultTurmaShouldNotBeFound("criterioDescricao.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByCriterioOrdenacaoNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where criterioOrdenacaoNumero equals to DEFAULT_CRITERIO_ORDENACAO_NUMERO
        defaultTurmaShouldBeFound("criterioOrdenacaoNumero.equals=" + DEFAULT_CRITERIO_ORDENACAO_NUMERO);

        // Get all the turmaList where criterioOrdenacaoNumero equals to UPDATED_CRITERIO_ORDENACAO_NUMERO
        defaultTurmaShouldNotBeFound("criterioOrdenacaoNumero.equals=" + UPDATED_CRITERIO_ORDENACAO_NUMERO);
    }

    @Test
    @Transactional
    void getAllTurmasByCriterioOrdenacaoNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where criterioOrdenacaoNumero in DEFAULT_CRITERIO_ORDENACAO_NUMERO or UPDATED_CRITERIO_ORDENACAO_NUMERO
        defaultTurmaShouldBeFound(
            "criterioOrdenacaoNumero.in=" + DEFAULT_CRITERIO_ORDENACAO_NUMERO + "," + UPDATED_CRITERIO_ORDENACAO_NUMERO
        );

        // Get all the turmaList where criterioOrdenacaoNumero equals to UPDATED_CRITERIO_ORDENACAO_NUMERO
        defaultTurmaShouldNotBeFound("criterioOrdenacaoNumero.in=" + UPDATED_CRITERIO_ORDENACAO_NUMERO);
    }

    @Test
    @Transactional
    void getAllTurmasByCriterioOrdenacaoNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where criterioOrdenacaoNumero is not null
        defaultTurmaShouldBeFound("criterioOrdenacaoNumero.specified=true");

        // Get all the turmaList where criterioOrdenacaoNumero is null
        defaultTurmaShouldNotBeFound("criterioOrdenacaoNumero.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByFazInscricaoDepoisMatriculaIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where fazInscricaoDepoisMatricula equals to DEFAULT_FAZ_INSCRICAO_DEPOIS_MATRICULA
        defaultTurmaShouldBeFound("fazInscricaoDepoisMatricula.equals=" + DEFAULT_FAZ_INSCRICAO_DEPOIS_MATRICULA);

        // Get all the turmaList where fazInscricaoDepoisMatricula equals to UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA
        defaultTurmaShouldNotBeFound("fazInscricaoDepoisMatricula.equals=" + UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA);
    }

    @Test
    @Transactional
    void getAllTurmasByFazInscricaoDepoisMatriculaIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where fazInscricaoDepoisMatricula in DEFAULT_FAZ_INSCRICAO_DEPOIS_MATRICULA or UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA
        defaultTurmaShouldBeFound(
            "fazInscricaoDepoisMatricula.in=" + DEFAULT_FAZ_INSCRICAO_DEPOIS_MATRICULA + "," + UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA
        );

        // Get all the turmaList where fazInscricaoDepoisMatricula equals to UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA
        defaultTurmaShouldNotBeFound("fazInscricaoDepoisMatricula.in=" + UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA);
    }

    @Test
    @Transactional
    void getAllTurmasByFazInscricaoDepoisMatriculaIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where fazInscricaoDepoisMatricula is not null
        defaultTurmaShouldBeFound("fazInscricaoDepoisMatricula.specified=true");

        // Get all the turmaList where fazInscricaoDepoisMatricula is null
        defaultTurmaShouldNotBeFound("fazInscricaoDepoisMatricula.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByIsDisponivelIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where isDisponivel equals to DEFAULT_IS_DISPONIVEL
        defaultTurmaShouldBeFound("isDisponivel.equals=" + DEFAULT_IS_DISPONIVEL);

        // Get all the turmaList where isDisponivel equals to UPDATED_IS_DISPONIVEL
        defaultTurmaShouldNotBeFound("isDisponivel.equals=" + UPDATED_IS_DISPONIVEL);
    }

    @Test
    @Transactional
    void getAllTurmasByIsDisponivelIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where isDisponivel in DEFAULT_IS_DISPONIVEL or UPDATED_IS_DISPONIVEL
        defaultTurmaShouldBeFound("isDisponivel.in=" + DEFAULT_IS_DISPONIVEL + "," + UPDATED_IS_DISPONIVEL);

        // Get all the turmaList where isDisponivel equals to UPDATED_IS_DISPONIVEL
        defaultTurmaShouldNotBeFound("isDisponivel.in=" + UPDATED_IS_DISPONIVEL);
    }

    @Test
    @Transactional
    void getAllTurmasByIsDisponivelIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where isDisponivel is not null
        defaultTurmaShouldBeFound("isDisponivel.specified=true");

        // Get all the turmaList where isDisponivel is null
        defaultTurmaShouldNotBeFound("isDisponivel.specified=false");
    }

    // @Test
    // @Transactional
    // void getAllTurmasByTurmaIsEqualToSomething() throws Exception {
    //     Turma turma;
    //     if (TestUtil.findAll(em, Turma.class).isEmpty()) {
    //         turmaRepository.saveAndFlush(turma);
    //         turma = TurmaResourceIT.createEntity(em);
    //     } else {
    //         turma = TestUtil.findAll(em, Turma.class).get(0);
    //     }
    //     em.persist(turma);
    //     em.flush();
    //     turma.addTurma(turma);
    //     turmaRepository.saveAndFlush(turma);
    //     Long turmaId = turma.getId();

    //     // Get all the turmaList where turma equals to turmaId
    //     defaultTurmaShouldBeFound("turmaId.equals=" + turmaId);

    //     // Get all the turmaList where turma equals to (turmaId + 1)
    //     defaultTurmaShouldNotBeFound("turmaId.equals=" + (turmaId + 1));
    // }

    @Test
    @Transactional
    void getAllTurmasByHorariosIsEqualToSomething() throws Exception {
        Horario horarios;
        if (TestUtil.findAll(em, Horario.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            horarios = HorarioResourceIT.createEntity(em);
        } else {
            horarios = TestUtil.findAll(em, Horario.class).get(0);
        }
        em.persist(horarios);
        em.flush();
        turma.addHorarios(horarios);
        turmaRepository.saveAndFlush(turma);
        Long horariosId = horarios.getId();

        // Get all the turmaList where horarios equals to horariosId
        defaultTurmaShouldBeFound("horariosId.equals=" + horariosId);

        // Get all the turmaList where horarios equals to (horariosId + 1)
        defaultTurmaShouldNotBeFound("horariosId.equals=" + (horariosId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByNotasPeriodicaDisciplinaIsEqualToSomething() throws Exception {
        NotasPeriodicaDisciplina notasPeriodicaDisciplina;
        if (TestUtil.findAll(em, NotasPeriodicaDisciplina.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            notasPeriodicaDisciplina = NotasPeriodicaDisciplinaResourceIT.createEntity(em);
        } else {
            notasPeriodicaDisciplina = TestUtil.findAll(em, NotasPeriodicaDisciplina.class).get(0);
        }
        em.persist(notasPeriodicaDisciplina);
        em.flush();
        turma.addNotasPeriodicaDisciplina(notasPeriodicaDisciplina);
        turmaRepository.saveAndFlush(turma);
        Long notasPeriodicaDisciplinaId = notasPeriodicaDisciplina.getId();

        // Get all the turmaList where notasPeriodicaDisciplina equals to notasPeriodicaDisciplinaId
        defaultTurmaShouldBeFound("notasPeriodicaDisciplinaId.equals=" + notasPeriodicaDisciplinaId);

        // Get all the turmaList where notasPeriodicaDisciplina equals to (notasPeriodicaDisciplinaId + 1)
        defaultTurmaShouldNotBeFound("notasPeriodicaDisciplinaId.equals=" + (notasPeriodicaDisciplinaId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByProcessoSelectivoMatriculaIsEqualToSomething() throws Exception {
        ProcessoSelectivoMatricula processoSelectivoMatricula;
        if (TestUtil.findAll(em, ProcessoSelectivoMatricula.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            processoSelectivoMatricula = ProcessoSelectivoMatriculaResourceIT.createEntity(em);
        } else {
            processoSelectivoMatricula = TestUtil.findAll(em, ProcessoSelectivoMatricula.class).get(0);
        }
        em.persist(processoSelectivoMatricula);
        em.flush();
        turma.addProcessoSelectivoMatricula(processoSelectivoMatricula);
        turmaRepository.saveAndFlush(turma);
        Long processoSelectivoMatriculaId = processoSelectivoMatricula.getId();

        // Get all the turmaList where processoSelectivoMatricula equals to processoSelectivoMatriculaId
        defaultTurmaShouldBeFound("processoSelectivoMatriculaId.equals=" + processoSelectivoMatriculaId);

        // Get all the turmaList where processoSelectivoMatricula equals to (processoSelectivoMatriculaId + 1)
        defaultTurmaShouldNotBeFound("processoSelectivoMatriculaId.equals=" + (processoSelectivoMatriculaId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByPlanoAulaIsEqualToSomething() throws Exception {
        PlanoAula planoAula;
        if (TestUtil.findAll(em, PlanoAula.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            planoAula = PlanoAulaResourceIT.createEntity(em);
        } else {
            planoAula = TestUtil.findAll(em, PlanoAula.class).get(0);
        }
        em.persist(planoAula);
        em.flush();
        turma.addPlanoAula(planoAula);
        turmaRepository.saveAndFlush(turma);
        Long planoAulaId = planoAula.getId();

        // Get all the turmaList where planoAula equals to planoAulaId
        defaultTurmaShouldBeFound("planoAulaId.equals=" + planoAulaId);

        // Get all the turmaList where planoAula equals to (planoAulaId + 1)
        defaultTurmaShouldNotBeFound("planoAulaId.equals=" + (planoAulaId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByMatriculasIsEqualToSomething() throws Exception {
        Matricula matriculas;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            matriculas = MatriculaResourceIT.createEntity(em);
        } else {
            matriculas = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(matriculas);
        em.flush();
        turma.addMatriculas(matriculas);
        turmaRepository.saveAndFlush(turma);
        Long matriculasId = matriculas.getId();

        // Get all the turmaList where matriculas equals to matriculasId
        defaultTurmaShouldBeFound("matriculasId.equals=" + matriculasId);

        // Get all the turmaList where matriculas equals to (matriculasId + 1)
        defaultTurmaShouldNotBeFound("matriculasId.equals=" + (matriculasId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByResumoAcademicoIsEqualToSomething() throws Exception {
        ResumoAcademico resumoAcademico;
        if (TestUtil.findAll(em, ResumoAcademico.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            resumoAcademico = ResumoAcademicoResourceIT.createEntity(em);
        } else {
            resumoAcademico = TestUtil.findAll(em, ResumoAcademico.class).get(0);
        }
        em.persist(resumoAcademico);
        em.flush();
        turma.addResumoAcademico(resumoAcademico);
        turmaRepository.saveAndFlush(turma);
        Long resumoAcademicoId = resumoAcademico.getId();

        // Get all the turmaList where resumoAcademico equals to resumoAcademicoId
        defaultTurmaShouldBeFound("resumoAcademicoId.equals=" + resumoAcademicoId);

        // Get all the turmaList where resumoAcademico equals to (resumoAcademicoId + 1)
        defaultTurmaShouldNotBeFound("resumoAcademicoId.equals=" + (resumoAcademicoId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByResponsaveisIsEqualToSomething() throws Exception {
        ResponsavelTurma responsaveis;
        if (TestUtil.findAll(em, ResponsavelTurma.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            responsaveis = ResponsavelTurmaResourceIT.createEntity(em);
        } else {
            responsaveis = TestUtil.findAll(em, ResponsavelTurma.class).get(0);
        }
        em.persist(responsaveis);
        em.flush();
        turma.addResponsaveis(responsaveis);
        turmaRepository.saveAndFlush(turma);
        Long responsaveisId = responsaveis.getId();

        // Get all the turmaList where responsaveis equals to responsaveisId
        defaultTurmaShouldBeFound("responsaveisId.equals=" + responsaveisId);

        // Get all the turmaList where responsaveis equals to (responsaveisId + 1)
        defaultTurmaShouldNotBeFound("responsaveisId.equals=" + (responsaveisId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByDissertacaoFinalCursoIsEqualToSomething() throws Exception {
        DissertacaoFinalCurso dissertacaoFinalCurso;
        if (TestUtil.findAll(em, DissertacaoFinalCurso.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            dissertacaoFinalCurso = DissertacaoFinalCursoResourceIT.createEntity(em);
        } else {
            dissertacaoFinalCurso = TestUtil.findAll(em, DissertacaoFinalCurso.class).get(0);
        }
        em.persist(dissertacaoFinalCurso);
        em.flush();
        turma.addDissertacaoFinalCurso(dissertacaoFinalCurso);
        turmaRepository.saveAndFlush(turma);
        Long dissertacaoFinalCursoId = dissertacaoFinalCurso.getId();

        // Get all the turmaList where dissertacaoFinalCurso equals to dissertacaoFinalCursoId
        defaultTurmaShouldBeFound("dissertacaoFinalCursoId.equals=" + dissertacaoFinalCursoId);

        // Get all the turmaList where dissertacaoFinalCurso equals to (dissertacaoFinalCursoId + 1)
        defaultTurmaShouldNotBeFound("dissertacaoFinalCursoId.equals=" + (dissertacaoFinalCursoId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        turma.addAnoLectivo(anoLectivo);
        turmaRepository.saveAndFlush(turma);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the turmaList where anoLectivo equals to anoLectivoId
        defaultTurmaShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the turmaList where anoLectivo equals to (anoLectivoId + 1)
        defaultTurmaShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        turma.setUtilizador(utilizador);
        turmaRepository.saveAndFlush(turma);
        Long utilizadorId = utilizador.getId();

        // Get all the turmaList where utilizador equals to utilizadorId
        defaultTurmaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the turmaList where utilizador equals to (utilizadorId + 1)
        defaultTurmaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByReferenciaIsEqualToSomething() throws Exception {
        Turma referencia;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            referencia = TurmaResourceIT.createEntity(em);
        } else {
            referencia = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(referencia);
        em.flush();
        turma.setReferencia(referencia);
        turmaRepository.saveAndFlush(turma);
        Long referenciaId = referencia.getId();

        // Get all the turmaList where referencia equals to referenciaId
        defaultTurmaShouldBeFound("referenciaId.equals=" + referenciaId);

        // Get all the turmaList where referencia equals to (referenciaId + 1)
        defaultTurmaShouldNotBeFound("referenciaId.equals=" + (referenciaId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByPlanoCurricularIsEqualToSomething() throws Exception {
        PlanoCurricular planoCurricular;
        if (TestUtil.findAll(em, PlanoCurricular.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            planoCurricular = PlanoCurricularResourceIT.createEntity(em);
        } else {
            planoCurricular = TestUtil.findAll(em, PlanoCurricular.class).get(0);
        }
        em.persist(planoCurricular);
        em.flush();
        turma.setPlanoCurricular(planoCurricular);
        turmaRepository.saveAndFlush(turma);
        Long planoCurricularId = planoCurricular.getId();

        // Get all the turmaList where planoCurricular equals to planoCurricularId
        defaultTurmaShouldBeFound("planoCurricularId.equals=" + planoCurricularId);

        // Get all the turmaList where planoCurricular equals to (planoCurricularId + 1)
        defaultTurmaShouldNotBeFound("planoCurricularId.equals=" + (planoCurricularId + 1));
    }

    @Test
    @Transactional
    void getAllTurmasByTurnoIsEqualToSomething() throws Exception {
        Turno turno;
        if (TestUtil.findAll(em, Turno.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            turno = TurnoResourceIT.createEntity(em);
        } else {
            turno = TestUtil.findAll(em, Turno.class).get(0);
        }
        em.persist(turno);
        em.flush();
        turma.setTurno(turno);
        turmaRepository.saveAndFlush(turma);
        Long turnoId = turno.getId();

        // Get all the turmaList where turno equals to turnoId
        defaultTurmaShouldBeFound("turnoId.equals=" + turnoId);

        // Get all the turmaList where turno equals to (turnoId + 1)
        defaultTurmaShouldNotBeFound("turnoId.equals=" + (turnoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTurmaShouldBeFound(String filter) throws Exception {
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turma.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta").value(hasItem(DEFAULT_CHAVE_COMPOSTA)))
            .andExpect(jsonPath("$.[*].tipoTurma").value(hasItem(DEFAULT_TIPO_TURMA.toString())))
            .andExpect(jsonPath("$.[*].sala").value(hasItem(DEFAULT_SALA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].lotacao").value(hasItem(DEFAULT_LOTACAO)))
            .andExpect(jsonPath("$.[*].confirmado").value(hasItem(DEFAULT_CONFIRMADO)))
            .andExpect(jsonPath("$.[*].abertura").value(hasItem(DEFAULT_ABERTURA.toString())))
            .andExpect(jsonPath("$.[*].encerramento").value(hasItem(DEFAULT_ENCERRAMENTO.toString())))
            .andExpect(jsonPath("$.[*].criterioDescricao").value(hasItem(DEFAULT_CRITERIO_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].criterioOrdenacaoNumero").value(hasItem(DEFAULT_CRITERIO_ORDENACAO_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].fazInscricaoDepoisMatricula").value(hasItem(DEFAULT_FAZ_INSCRICAO_DEPOIS_MATRICULA.booleanValue())))
            .andExpect(jsonPath("$.[*].isDisponivel").value(hasItem(DEFAULT_IS_DISPONIVEL.booleanValue())));

        // Check, that the count call also returns 1
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTurmaShouldNotBeFound(String filter) throws Exception {
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTurma() throws Exception {
        // Get the turma
        restTurmaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();

        // Update the turma
        Turma updatedTurma = turmaRepository.findById(turma.getId()).get();
        // Disconnect from session so that the updates on updatedTurma are not directly saved in db
        em.detach(updatedTurma);
        updatedTurma
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .tipoTurma(UPDATED_TIPO_TURMA)
            .sala(UPDATED_SALA)
            .descricao(UPDATED_DESCRICAO)
            .lotacao(UPDATED_LOTACAO)
            .confirmado(UPDATED_CONFIRMADO)
            .abertura(UPDATED_ABERTURA)
            .encerramento(UPDATED_ENCERRAMENTO)
            .criterioDescricao(UPDATED_CRITERIO_DESCRICAO)
            .criterioOrdenacaoNumero(UPDATED_CRITERIO_ORDENACAO_NUMERO)
            .fazInscricaoDepoisMatricula(UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA)
            .isDisponivel(UPDATED_IS_DISPONIVEL);
        TurmaDTO turmaDTO = turmaMapper.toDto(updatedTurma);

        restTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, turmaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(turmaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
        Turma testTurma = turmaList.get(turmaList.size() - 1);
        assertThat(testTurma.getChaveComposta()).isEqualTo(UPDATED_CHAVE_COMPOSTA);
        assertThat(testTurma.getTipoTurma()).isEqualTo(UPDATED_TIPO_TURMA);
        assertThat(testTurma.getSala()).isEqualTo(UPDATED_SALA);
        assertThat(testTurma.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTurma.getLotacao()).isEqualTo(UPDATED_LOTACAO);
        assertThat(testTurma.getConfirmado()).isEqualTo(UPDATED_CONFIRMADO);
        assertThat(testTurma.getAbertura()).isEqualTo(UPDATED_ABERTURA);
        assertThat(testTurma.getEncerramento()).isEqualTo(UPDATED_ENCERRAMENTO);
        assertThat(testTurma.getCriterioDescricao()).isEqualTo(UPDATED_CRITERIO_DESCRICAO);
        assertThat(testTurma.getCriterioOrdenacaoNumero()).isEqualTo(UPDATED_CRITERIO_ORDENACAO_NUMERO);
        assertThat(testTurma.getFazInscricaoDepoisMatricula()).isEqualTo(UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA);
        assertThat(testTurma.getIsDisponivel()).isEqualTo(UPDATED_IS_DISPONIVEL);
    }

    @Test
    @Transactional
    void putNonExistingTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(count.incrementAndGet());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, turmaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(turmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(count.incrementAndGet());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(turmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(count.incrementAndGet());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTurmaWithPatch() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();

        // Update the turma using partial update
        Turma partialUpdatedTurma = new Turma();
        partialUpdatedTurma.setId(turma.getId());

        partialUpdatedTurma
            .confirmado(UPDATED_CONFIRMADO)
            .abertura(UPDATED_ABERTURA)
            .encerramento(UPDATED_ENCERRAMENTO)
            .criterioDescricao(UPDATED_CRITERIO_DESCRICAO)
            .criterioOrdenacaoNumero(UPDATED_CRITERIO_ORDENACAO_NUMERO)
            .fazInscricaoDepoisMatricula(UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA);

        restTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTurma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTurma))
            )
            .andExpect(status().isOk());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
        Turma testTurma = turmaList.get(turmaList.size() - 1);
        assertThat(testTurma.getChaveComposta()).isEqualTo(DEFAULT_CHAVE_COMPOSTA);
        assertThat(testTurma.getTipoTurma()).isEqualTo(DEFAULT_TIPO_TURMA);
        assertThat(testTurma.getSala()).isEqualTo(DEFAULT_SALA);
        assertThat(testTurma.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTurma.getLotacao()).isEqualTo(DEFAULT_LOTACAO);
        assertThat(testTurma.getConfirmado()).isEqualTo(UPDATED_CONFIRMADO);
        assertThat(testTurma.getAbertura()).isEqualTo(UPDATED_ABERTURA);
        assertThat(testTurma.getEncerramento()).isEqualTo(UPDATED_ENCERRAMENTO);
        assertThat(testTurma.getCriterioDescricao()).isEqualTo(UPDATED_CRITERIO_DESCRICAO);
        assertThat(testTurma.getCriterioOrdenacaoNumero()).isEqualTo(UPDATED_CRITERIO_ORDENACAO_NUMERO);
        assertThat(testTurma.getFazInscricaoDepoisMatricula()).isEqualTo(UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA);
        assertThat(testTurma.getIsDisponivel()).isEqualTo(DEFAULT_IS_DISPONIVEL);
    }

    @Test
    @Transactional
    void fullUpdateTurmaWithPatch() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();

        // Update the turma using partial update
        Turma partialUpdatedTurma = new Turma();
        partialUpdatedTurma.setId(turma.getId());

        partialUpdatedTurma
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .tipoTurma(UPDATED_TIPO_TURMA)
            .sala(UPDATED_SALA)
            .descricao(UPDATED_DESCRICAO)
            .lotacao(UPDATED_LOTACAO)
            .confirmado(UPDATED_CONFIRMADO)
            .abertura(UPDATED_ABERTURA)
            .encerramento(UPDATED_ENCERRAMENTO)
            .criterioDescricao(UPDATED_CRITERIO_DESCRICAO)
            .criterioOrdenacaoNumero(UPDATED_CRITERIO_ORDENACAO_NUMERO)
            .fazInscricaoDepoisMatricula(UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA)
            .isDisponivel(UPDATED_IS_DISPONIVEL);

        restTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTurma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTurma))
            )
            .andExpect(status().isOk());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
        Turma testTurma = turmaList.get(turmaList.size() - 1);
        assertThat(testTurma.getChaveComposta()).isEqualTo(UPDATED_CHAVE_COMPOSTA);
        assertThat(testTurma.getTipoTurma()).isEqualTo(UPDATED_TIPO_TURMA);
        assertThat(testTurma.getSala()).isEqualTo(UPDATED_SALA);
        assertThat(testTurma.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTurma.getLotacao()).isEqualTo(UPDATED_LOTACAO);
        assertThat(testTurma.getConfirmado()).isEqualTo(UPDATED_CONFIRMADO);
        assertThat(testTurma.getAbertura()).isEqualTo(UPDATED_ABERTURA);
        assertThat(testTurma.getEncerramento()).isEqualTo(UPDATED_ENCERRAMENTO);
        assertThat(testTurma.getCriterioDescricao()).isEqualTo(UPDATED_CRITERIO_DESCRICAO);
        assertThat(testTurma.getCriterioOrdenacaoNumero()).isEqualTo(UPDATED_CRITERIO_ORDENACAO_NUMERO);
        assertThat(testTurma.getFazInscricaoDepoisMatricula()).isEqualTo(UPDATED_FAZ_INSCRICAO_DEPOIS_MATRICULA);
        assertThat(testTurma.getIsDisponivel()).isEqualTo(UPDATED_IS_DISPONIVEL);
    }

    @Test
    @Transactional
    void patchNonExistingTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(count.incrementAndGet());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, turmaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(turmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(count.incrementAndGet());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(turmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(count.incrementAndGet());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        int databaseSizeBeforeDelete = turmaRepository.findAll().size();

        // Delete the turma
        restTurmaMockMvc
            .perform(delete(ENTITY_API_URL_ID, turma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
