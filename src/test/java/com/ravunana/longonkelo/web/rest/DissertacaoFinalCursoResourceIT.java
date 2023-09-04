package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.AreaFormacao;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.DissertacaoFinalCurso;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.EstadoDissertacao;
import com.ravunana.longonkelo.domain.NaturezaTrabalho;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.DissertacaoFinalCursoRepository;
import com.ravunana.longonkelo.service.DissertacaoFinalCursoService;
import com.ravunana.longonkelo.service.criteria.DissertacaoFinalCursoCriteria;
import com.ravunana.longonkelo.service.dto.DissertacaoFinalCursoDTO;
import com.ravunana.longonkelo.service.mapper.DissertacaoFinalCursoMapper;
import java.time.Instant;
import java.time.LocalDate;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DissertacaoFinalCursoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DissertacaoFinalCursoResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TEMA = "AAAAAAAAAA";
    private static final String UPDATED_TEMA = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIVO_GERAL = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVO_GERAL = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIVOS_ESPECIFICOS = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVOS_ESPECIFICOS = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCAO = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCAO = "BBBBBBBBBB";

    private static final String DEFAULT_RESUMO = "AAAAAAAAAA";
    private static final String UPDATED_RESUMO = "BBBBBBBBBB";

    private static final String DEFAULT_PROBLEMA = "AAAAAAAAAA";
    private static final String UPDATED_PROBLEMA = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    private static final String DEFAULT_METODOLOGIA = "AAAAAAAAAA";
    private static final String UPDATED_METODOLOGIA = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCIAS_BIBLIOGRAFICAS = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCIAS_BIBLIOGRAFICAS = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO_ORIENTADOR = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO_ORIENTADOR = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO_AREA_FORMACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO_AREA_FORMACAO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO_INSTITUICAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO_INSTITUICAO = "BBBBBBBBBB";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_TERMOS_COMPROMISSOS = "AAAAAAAAAA";
    private static final String UPDATED_TERMOS_COMPROMISSOS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO = false;
    private static final Boolean UPDATED_IS_ACEITE_TERMOS_COMPROMISSO = true;

    private static final String ENTITY_API_URL = "/api/dissertacao-final-cursos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DissertacaoFinalCursoRepository dissertacaoFinalCursoRepository;

    @Mock
    private DissertacaoFinalCursoRepository dissertacaoFinalCursoRepositoryMock;

    @Autowired
    private DissertacaoFinalCursoMapper dissertacaoFinalCursoMapper;

    @Mock
    private DissertacaoFinalCursoService dissertacaoFinalCursoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDissertacaoFinalCursoMockMvc;

    private DissertacaoFinalCurso dissertacaoFinalCurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DissertacaoFinalCurso createEntity(EntityManager em) {
        DissertacaoFinalCurso dissertacaoFinalCurso = new DissertacaoFinalCurso()
            .numero(DEFAULT_NUMERO)
            .timestamp(DEFAULT_TIMESTAMP)
            .data(DEFAULT_DATA)
            .tema(DEFAULT_TEMA)
            .objectivoGeral(DEFAULT_OBJECTIVO_GERAL)
            .objectivosEspecificos(DEFAULT_OBJECTIVOS_ESPECIFICOS)
            .introducao(DEFAULT_INTRODUCAO)
            .resumo(DEFAULT_RESUMO)
            .problema(DEFAULT_PROBLEMA)
            .resultado(DEFAULT_RESULTADO)
            .metodologia(DEFAULT_METODOLOGIA)
            .referenciasBibliograficas(DEFAULT_REFERENCIAS_BIBLIOGRAFICAS)
            .observacaoOrientador(DEFAULT_OBSERVACAO_ORIENTADOR)
            .observacaoAreaFormacao(DEFAULT_OBSERVACAO_AREA_FORMACAO)
            .observacaoInstituicao(DEFAULT_OBSERVACAO_INSTITUICAO)
            .hash(DEFAULT_HASH)
            .termosCompromissos(DEFAULT_TERMOS_COMPROMISSOS)
            .isAceiteTermosCompromisso(DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        dissertacaoFinalCurso.setTurma(turma);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        dissertacaoFinalCurso.setOrientador(docente);
        // Add required entity
        AreaFormacao areaFormacao;
        if (TestUtil.findAll(em, AreaFormacao.class).isEmpty()) {
            areaFormacao = AreaFormacaoResourceIT.createEntity(em);
            em.persist(areaFormacao);
            em.flush();
        } else {
            areaFormacao = TestUtil.findAll(em, AreaFormacao.class).get(0);
        }
        dissertacaoFinalCurso.setEspecialidade(areaFormacao);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        dissertacaoFinalCurso.setDiscente(discente);
        // Add required entity
        EstadoDissertacao estadoDissertacao;
        if (TestUtil.findAll(em, EstadoDissertacao.class).isEmpty()) {
            estadoDissertacao = EstadoDissertacaoResourceIT.createEntity(em);
            em.persist(estadoDissertacao);
            em.flush();
        } else {
            estadoDissertacao = TestUtil.findAll(em, EstadoDissertacao.class).get(0);
        }
        dissertacaoFinalCurso.setEstado(estadoDissertacao);
        // Add required entity
        NaturezaTrabalho naturezaTrabalho;
        if (TestUtil.findAll(em, NaturezaTrabalho.class).isEmpty()) {
            naturezaTrabalho = NaturezaTrabalhoResourceIT.createEntity(em);
            em.persist(naturezaTrabalho);
            em.flush();
        } else {
            naturezaTrabalho = TestUtil.findAll(em, NaturezaTrabalho.class).get(0);
        }
        dissertacaoFinalCurso.setNatureza(naturezaTrabalho);
        return dissertacaoFinalCurso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DissertacaoFinalCurso createUpdatedEntity(EntityManager em) {
        DissertacaoFinalCurso dissertacaoFinalCurso = new DissertacaoFinalCurso()
            .numero(UPDATED_NUMERO)
            .timestamp(UPDATED_TIMESTAMP)
            .data(UPDATED_DATA)
            .tema(UPDATED_TEMA)
            .objectivoGeral(UPDATED_OBJECTIVO_GERAL)
            .objectivosEspecificos(UPDATED_OBJECTIVOS_ESPECIFICOS)
            .introducao(UPDATED_INTRODUCAO)
            .resumo(UPDATED_RESUMO)
            .problema(UPDATED_PROBLEMA)
            .resultado(UPDATED_RESULTADO)
            .metodologia(UPDATED_METODOLOGIA)
            .referenciasBibliograficas(UPDATED_REFERENCIAS_BIBLIOGRAFICAS)
            .observacaoOrientador(UPDATED_OBSERVACAO_ORIENTADOR)
            .observacaoAreaFormacao(UPDATED_OBSERVACAO_AREA_FORMACAO)
            .observacaoInstituicao(UPDATED_OBSERVACAO_INSTITUICAO)
            .hash(UPDATED_HASH)
            .termosCompromissos(UPDATED_TERMOS_COMPROMISSOS)
            .isAceiteTermosCompromisso(UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createUpdatedEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        dissertacaoFinalCurso.setTurma(turma);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createUpdatedEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        dissertacaoFinalCurso.setOrientador(docente);
        // Add required entity
        AreaFormacao areaFormacao;
        if (TestUtil.findAll(em, AreaFormacao.class).isEmpty()) {
            areaFormacao = AreaFormacaoResourceIT.createUpdatedEntity(em);
            em.persist(areaFormacao);
            em.flush();
        } else {
            areaFormacao = TestUtil.findAll(em, AreaFormacao.class).get(0);
        }
        dissertacaoFinalCurso.setEspecialidade(areaFormacao);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createUpdatedEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        dissertacaoFinalCurso.setDiscente(discente);
        // Add required entity
        EstadoDissertacao estadoDissertacao;
        if (TestUtil.findAll(em, EstadoDissertacao.class).isEmpty()) {
            estadoDissertacao = EstadoDissertacaoResourceIT.createUpdatedEntity(em);
            em.persist(estadoDissertacao);
            em.flush();
        } else {
            estadoDissertacao = TestUtil.findAll(em, EstadoDissertacao.class).get(0);
        }
        dissertacaoFinalCurso.setEstado(estadoDissertacao);
        // Add required entity
        NaturezaTrabalho naturezaTrabalho;
        if (TestUtil.findAll(em, NaturezaTrabalho.class).isEmpty()) {
            naturezaTrabalho = NaturezaTrabalhoResourceIT.createUpdatedEntity(em);
            em.persist(naturezaTrabalho);
            em.flush();
        } else {
            naturezaTrabalho = TestUtil.findAll(em, NaturezaTrabalho.class).get(0);
        }
        dissertacaoFinalCurso.setNatureza(naturezaTrabalho);
        return dissertacaoFinalCurso;
    }

    @BeforeEach
    public void initTest() {
        dissertacaoFinalCurso = createEntity(em);
    }

    @Test
    @Transactional
    void createDissertacaoFinalCurso() throws Exception {
        int databaseSizeBeforeCreate = dissertacaoFinalCursoRepository.findAll().size();
        // Create the DissertacaoFinalCurso
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);
        restDissertacaoFinalCursoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DissertacaoFinalCurso in the database
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeCreate + 1);
        DissertacaoFinalCurso testDissertacaoFinalCurso = dissertacaoFinalCursoList.get(dissertacaoFinalCursoList.size() - 1);
        assertThat(testDissertacaoFinalCurso.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDissertacaoFinalCurso.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testDissertacaoFinalCurso.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDissertacaoFinalCurso.getTema()).isEqualTo(DEFAULT_TEMA);
        assertThat(testDissertacaoFinalCurso.getObjectivoGeral()).isEqualTo(DEFAULT_OBJECTIVO_GERAL);
        assertThat(testDissertacaoFinalCurso.getObjectivosEspecificos()).isEqualTo(DEFAULT_OBJECTIVOS_ESPECIFICOS);
        assertThat(testDissertacaoFinalCurso.getIntroducao()).isEqualTo(DEFAULT_INTRODUCAO);
        assertThat(testDissertacaoFinalCurso.getResumo()).isEqualTo(DEFAULT_RESUMO);
        assertThat(testDissertacaoFinalCurso.getProblema()).isEqualTo(DEFAULT_PROBLEMA);
        assertThat(testDissertacaoFinalCurso.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testDissertacaoFinalCurso.getMetodologia()).isEqualTo(DEFAULT_METODOLOGIA);
        assertThat(testDissertacaoFinalCurso.getReferenciasBibliograficas()).isEqualTo(DEFAULT_REFERENCIAS_BIBLIOGRAFICAS);
        assertThat(testDissertacaoFinalCurso.getObservacaoOrientador()).isEqualTo(DEFAULT_OBSERVACAO_ORIENTADOR);
        assertThat(testDissertacaoFinalCurso.getObservacaoAreaFormacao()).isEqualTo(DEFAULT_OBSERVACAO_AREA_FORMACAO);
        assertThat(testDissertacaoFinalCurso.getObservacaoInstituicao()).isEqualTo(DEFAULT_OBSERVACAO_INSTITUICAO);
        assertThat(testDissertacaoFinalCurso.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testDissertacaoFinalCurso.getTermosCompromissos()).isEqualTo(DEFAULT_TERMOS_COMPROMISSOS);
        assertThat(testDissertacaoFinalCurso.getIsAceiteTermosCompromisso()).isEqualTo(DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void createDissertacaoFinalCursoWithExistingId() throws Exception {
        // Create the DissertacaoFinalCurso with an existing ID
        dissertacaoFinalCurso.setId(1L);
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);

        int databaseSizeBeforeCreate = dissertacaoFinalCursoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDissertacaoFinalCursoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DissertacaoFinalCurso in the database
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = dissertacaoFinalCursoRepository.findAll().size();
        // set the field null
        dissertacaoFinalCurso.setNumero(null);

        // Create the DissertacaoFinalCurso, which fails.
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);

        restDissertacaoFinalCursoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isBadRequest());

        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = dissertacaoFinalCursoRepository.findAll().size();
        // set the field null
        dissertacaoFinalCurso.setData(null);

        // Create the DissertacaoFinalCurso, which fails.
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);

        restDissertacaoFinalCursoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isBadRequest());

        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTemaIsRequired() throws Exception {
        int databaseSizeBeforeTest = dissertacaoFinalCursoRepository.findAll().size();
        // set the field null
        dissertacaoFinalCurso.setTema(null);

        // Create the DissertacaoFinalCurso, which fails.
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);

        restDissertacaoFinalCursoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isBadRequest());

        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkObjectivoGeralIsRequired() throws Exception {
        int databaseSizeBeforeTest = dissertacaoFinalCursoRepository.findAll().size();
        // set the field null
        dissertacaoFinalCurso.setObjectivoGeral(null);

        // Create the DissertacaoFinalCurso, which fails.
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);

        restDissertacaoFinalCursoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isBadRequest());

        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursos() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList
        restDissertacaoFinalCursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dissertacaoFinalCurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].tema").value(hasItem(DEFAULT_TEMA)))
            .andExpect(jsonPath("$.[*].objectivoGeral").value(hasItem(DEFAULT_OBJECTIVO_GERAL)))
            .andExpect(jsonPath("$.[*].objectivosEspecificos").value(hasItem(DEFAULT_OBJECTIVOS_ESPECIFICOS.toString())))
            .andExpect(jsonPath("$.[*].introducao").value(hasItem(DEFAULT_INTRODUCAO.toString())))
            .andExpect(jsonPath("$.[*].resumo").value(hasItem(DEFAULT_RESUMO.toString())))
            .andExpect(jsonPath("$.[*].problema").value(hasItem(DEFAULT_PROBLEMA.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.toString())))
            .andExpect(jsonPath("$.[*].metodologia").value(hasItem(DEFAULT_METODOLOGIA.toString())))
            .andExpect(jsonPath("$.[*].referenciasBibliograficas").value(hasItem(DEFAULT_REFERENCIAS_BIBLIOGRAFICAS.toString())))
            .andExpect(jsonPath("$.[*].observacaoOrientador").value(hasItem(DEFAULT_OBSERVACAO_ORIENTADOR.toString())))
            .andExpect(jsonPath("$.[*].observacaoAreaFormacao").value(hasItem(DEFAULT_OBSERVACAO_AREA_FORMACAO.toString())))
            .andExpect(jsonPath("$.[*].observacaoInstituicao").value(hasItem(DEFAULT_OBSERVACAO_INSTITUICAO.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].termosCompromissos").value(hasItem(DEFAULT_TERMOS_COMPROMISSOS.toString())))
            .andExpect(jsonPath("$.[*].isAceiteTermosCompromisso").value(hasItem(DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDissertacaoFinalCursosWithEagerRelationshipsIsEnabled() throws Exception {
        when(dissertacaoFinalCursoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDissertacaoFinalCursoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dissertacaoFinalCursoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDissertacaoFinalCursosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dissertacaoFinalCursoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDissertacaoFinalCursoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(dissertacaoFinalCursoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDissertacaoFinalCurso() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get the dissertacaoFinalCurso
        restDissertacaoFinalCursoMockMvc
            .perform(get(ENTITY_API_URL_ID, dissertacaoFinalCurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dissertacaoFinalCurso.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.tema").value(DEFAULT_TEMA))
            .andExpect(jsonPath("$.objectivoGeral").value(DEFAULT_OBJECTIVO_GERAL))
            .andExpect(jsonPath("$.objectivosEspecificos").value(DEFAULT_OBJECTIVOS_ESPECIFICOS.toString()))
            .andExpect(jsonPath("$.introducao").value(DEFAULT_INTRODUCAO.toString()))
            .andExpect(jsonPath("$.resumo").value(DEFAULT_RESUMO.toString()))
            .andExpect(jsonPath("$.problema").value(DEFAULT_PROBLEMA.toString()))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO.toString()))
            .andExpect(jsonPath("$.metodologia").value(DEFAULT_METODOLOGIA.toString()))
            .andExpect(jsonPath("$.referenciasBibliograficas").value(DEFAULT_REFERENCIAS_BIBLIOGRAFICAS.toString()))
            .andExpect(jsonPath("$.observacaoOrientador").value(DEFAULT_OBSERVACAO_ORIENTADOR.toString()))
            .andExpect(jsonPath("$.observacaoAreaFormacao").value(DEFAULT_OBSERVACAO_AREA_FORMACAO.toString()))
            .andExpect(jsonPath("$.observacaoInstituicao").value(DEFAULT_OBSERVACAO_INSTITUICAO.toString()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.termosCompromissos").value(DEFAULT_TERMOS_COMPROMISSOS.toString()))
            .andExpect(jsonPath("$.isAceiteTermosCompromisso").value(DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO.booleanValue()));
    }

    @Test
    @Transactional
    void getDissertacaoFinalCursosByIdFiltering() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        Long id = dissertacaoFinalCurso.getId();

        defaultDissertacaoFinalCursoShouldBeFound("id.equals=" + id);
        defaultDissertacaoFinalCursoShouldNotBeFound("id.notEquals=" + id);

        defaultDissertacaoFinalCursoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDissertacaoFinalCursoShouldNotBeFound("id.greaterThan=" + id);

        defaultDissertacaoFinalCursoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDissertacaoFinalCursoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where numero equals to DEFAULT_NUMERO
        defaultDissertacaoFinalCursoShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the dissertacaoFinalCursoList where numero equals to UPDATED_NUMERO
        defaultDissertacaoFinalCursoShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultDissertacaoFinalCursoShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the dissertacaoFinalCursoList where numero equals to UPDATED_NUMERO
        defaultDissertacaoFinalCursoShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where numero is not null
        defaultDissertacaoFinalCursoShouldBeFound("numero.specified=true");

        // Get all the dissertacaoFinalCursoList where numero is null
        defaultDissertacaoFinalCursoShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByNumeroContainsSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where numero contains DEFAULT_NUMERO
        defaultDissertacaoFinalCursoShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the dissertacaoFinalCursoList where numero contains UPDATED_NUMERO
        defaultDissertacaoFinalCursoShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where numero does not contain DEFAULT_NUMERO
        defaultDissertacaoFinalCursoShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the dissertacaoFinalCursoList where numero does not contain UPDATED_NUMERO
        defaultDissertacaoFinalCursoShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where timestamp equals to DEFAULT_TIMESTAMP
        defaultDissertacaoFinalCursoShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the dissertacaoFinalCursoList where timestamp equals to UPDATED_TIMESTAMP
        defaultDissertacaoFinalCursoShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultDissertacaoFinalCursoShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the dissertacaoFinalCursoList where timestamp equals to UPDATED_TIMESTAMP
        defaultDissertacaoFinalCursoShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where timestamp is not null
        defaultDissertacaoFinalCursoShouldBeFound("timestamp.specified=true");

        // Get all the dissertacaoFinalCursoList where timestamp is null
        defaultDissertacaoFinalCursoShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultDissertacaoFinalCursoShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the dissertacaoFinalCursoList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultDissertacaoFinalCursoShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultDissertacaoFinalCursoShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the dissertacaoFinalCursoList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultDissertacaoFinalCursoShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where timestamp is less than DEFAULT_TIMESTAMP
        defaultDissertacaoFinalCursoShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the dissertacaoFinalCursoList where timestamp is less than UPDATED_TIMESTAMP
        defaultDissertacaoFinalCursoShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultDissertacaoFinalCursoShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the dissertacaoFinalCursoList where timestamp is greater than SMALLER_TIMESTAMP
        defaultDissertacaoFinalCursoShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where data equals to DEFAULT_DATA
        defaultDissertacaoFinalCursoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the dissertacaoFinalCursoList where data equals to UPDATED_DATA
        defaultDissertacaoFinalCursoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultDissertacaoFinalCursoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the dissertacaoFinalCursoList where data equals to UPDATED_DATA
        defaultDissertacaoFinalCursoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where data is not null
        defaultDissertacaoFinalCursoShouldBeFound("data.specified=true");

        // Get all the dissertacaoFinalCursoList where data is null
        defaultDissertacaoFinalCursoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where data is greater than or equal to DEFAULT_DATA
        defaultDissertacaoFinalCursoShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the dissertacaoFinalCursoList where data is greater than or equal to UPDATED_DATA
        defaultDissertacaoFinalCursoShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where data is less than or equal to DEFAULT_DATA
        defaultDissertacaoFinalCursoShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the dissertacaoFinalCursoList where data is less than or equal to SMALLER_DATA
        defaultDissertacaoFinalCursoShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where data is less than DEFAULT_DATA
        defaultDissertacaoFinalCursoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the dissertacaoFinalCursoList where data is less than UPDATED_DATA
        defaultDissertacaoFinalCursoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where data is greater than DEFAULT_DATA
        defaultDissertacaoFinalCursoShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the dissertacaoFinalCursoList where data is greater than SMALLER_DATA
        defaultDissertacaoFinalCursoShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTemaIsEqualToSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where tema equals to DEFAULT_TEMA
        defaultDissertacaoFinalCursoShouldBeFound("tema.equals=" + DEFAULT_TEMA);

        // Get all the dissertacaoFinalCursoList where tema equals to UPDATED_TEMA
        defaultDissertacaoFinalCursoShouldNotBeFound("tema.equals=" + UPDATED_TEMA);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTemaIsInShouldWork() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where tema in DEFAULT_TEMA or UPDATED_TEMA
        defaultDissertacaoFinalCursoShouldBeFound("tema.in=" + DEFAULT_TEMA + "," + UPDATED_TEMA);

        // Get all the dissertacaoFinalCursoList where tema equals to UPDATED_TEMA
        defaultDissertacaoFinalCursoShouldNotBeFound("tema.in=" + UPDATED_TEMA);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTemaIsNullOrNotNull() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where tema is not null
        defaultDissertacaoFinalCursoShouldBeFound("tema.specified=true");

        // Get all the dissertacaoFinalCursoList where tema is null
        defaultDissertacaoFinalCursoShouldNotBeFound("tema.specified=false");
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTemaContainsSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where tema contains DEFAULT_TEMA
        defaultDissertacaoFinalCursoShouldBeFound("tema.contains=" + DEFAULT_TEMA);

        // Get all the dissertacaoFinalCursoList where tema contains UPDATED_TEMA
        defaultDissertacaoFinalCursoShouldNotBeFound("tema.contains=" + UPDATED_TEMA);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTemaNotContainsSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where tema does not contain DEFAULT_TEMA
        defaultDissertacaoFinalCursoShouldNotBeFound("tema.doesNotContain=" + DEFAULT_TEMA);

        // Get all the dissertacaoFinalCursoList where tema does not contain UPDATED_TEMA
        defaultDissertacaoFinalCursoShouldBeFound("tema.doesNotContain=" + UPDATED_TEMA);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByObjectivoGeralIsEqualToSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where objectivoGeral equals to DEFAULT_OBJECTIVO_GERAL
        defaultDissertacaoFinalCursoShouldBeFound("objectivoGeral.equals=" + DEFAULT_OBJECTIVO_GERAL);

        // Get all the dissertacaoFinalCursoList where objectivoGeral equals to UPDATED_OBJECTIVO_GERAL
        defaultDissertacaoFinalCursoShouldNotBeFound("objectivoGeral.equals=" + UPDATED_OBJECTIVO_GERAL);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByObjectivoGeralIsInShouldWork() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where objectivoGeral in DEFAULT_OBJECTIVO_GERAL or UPDATED_OBJECTIVO_GERAL
        defaultDissertacaoFinalCursoShouldBeFound("objectivoGeral.in=" + DEFAULT_OBJECTIVO_GERAL + "," + UPDATED_OBJECTIVO_GERAL);

        // Get all the dissertacaoFinalCursoList where objectivoGeral equals to UPDATED_OBJECTIVO_GERAL
        defaultDissertacaoFinalCursoShouldNotBeFound("objectivoGeral.in=" + UPDATED_OBJECTIVO_GERAL);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByObjectivoGeralIsNullOrNotNull() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where objectivoGeral is not null
        defaultDissertacaoFinalCursoShouldBeFound("objectivoGeral.specified=true");

        // Get all the dissertacaoFinalCursoList where objectivoGeral is null
        defaultDissertacaoFinalCursoShouldNotBeFound("objectivoGeral.specified=false");
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByObjectivoGeralContainsSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where objectivoGeral contains DEFAULT_OBJECTIVO_GERAL
        defaultDissertacaoFinalCursoShouldBeFound("objectivoGeral.contains=" + DEFAULT_OBJECTIVO_GERAL);

        // Get all the dissertacaoFinalCursoList where objectivoGeral contains UPDATED_OBJECTIVO_GERAL
        defaultDissertacaoFinalCursoShouldNotBeFound("objectivoGeral.contains=" + UPDATED_OBJECTIVO_GERAL);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByObjectivoGeralNotContainsSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where objectivoGeral does not contain DEFAULT_OBJECTIVO_GERAL
        defaultDissertacaoFinalCursoShouldNotBeFound("objectivoGeral.doesNotContain=" + DEFAULT_OBJECTIVO_GERAL);

        // Get all the dissertacaoFinalCursoList where objectivoGeral does not contain UPDATED_OBJECTIVO_GERAL
        defaultDissertacaoFinalCursoShouldBeFound("objectivoGeral.doesNotContain=" + UPDATED_OBJECTIVO_GERAL);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where hash equals to DEFAULT_HASH
        defaultDissertacaoFinalCursoShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the dissertacaoFinalCursoList where hash equals to UPDATED_HASH
        defaultDissertacaoFinalCursoShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByHashIsInShouldWork() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultDissertacaoFinalCursoShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the dissertacaoFinalCursoList where hash equals to UPDATED_HASH
        defaultDissertacaoFinalCursoShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where hash is not null
        defaultDissertacaoFinalCursoShouldBeFound("hash.specified=true");

        // Get all the dissertacaoFinalCursoList where hash is null
        defaultDissertacaoFinalCursoShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByHashContainsSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where hash contains DEFAULT_HASH
        defaultDissertacaoFinalCursoShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the dissertacaoFinalCursoList where hash contains UPDATED_HASH
        defaultDissertacaoFinalCursoShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByHashNotContainsSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where hash does not contain DEFAULT_HASH
        defaultDissertacaoFinalCursoShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the dissertacaoFinalCursoList where hash does not contain UPDATED_HASH
        defaultDissertacaoFinalCursoShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByIsAceiteTermosCompromissoIsEqualToSomething() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where isAceiteTermosCompromisso equals to DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO
        defaultDissertacaoFinalCursoShouldBeFound("isAceiteTermosCompromisso.equals=" + DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO);

        // Get all the dissertacaoFinalCursoList where isAceiteTermosCompromisso equals to UPDATED_IS_ACEITE_TERMOS_COMPROMISSO
        defaultDissertacaoFinalCursoShouldNotBeFound("isAceiteTermosCompromisso.equals=" + UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByIsAceiteTermosCompromissoIsInShouldWork() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where isAceiteTermosCompromisso in DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO or UPDATED_IS_ACEITE_TERMOS_COMPROMISSO
        defaultDissertacaoFinalCursoShouldBeFound(
            "isAceiteTermosCompromisso.in=" + DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO + "," + UPDATED_IS_ACEITE_TERMOS_COMPROMISSO
        );

        // Get all the dissertacaoFinalCursoList where isAceiteTermosCompromisso equals to UPDATED_IS_ACEITE_TERMOS_COMPROMISSO
        defaultDissertacaoFinalCursoShouldNotBeFound("isAceiteTermosCompromisso.in=" + UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByIsAceiteTermosCompromissoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        // Get all the dissertacaoFinalCursoList where isAceiteTermosCompromisso is not null
        defaultDissertacaoFinalCursoShouldBeFound("isAceiteTermosCompromisso.specified=true");

        // Get all the dissertacaoFinalCursoList where isAceiteTermosCompromisso is null
        defaultDissertacaoFinalCursoShouldNotBeFound("isAceiteTermosCompromisso.specified=false");
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        dissertacaoFinalCurso.addAnoLectivo(anoLectivo);
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the dissertacaoFinalCursoList where anoLectivo equals to anoLectivoId
        defaultDissertacaoFinalCursoShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the dissertacaoFinalCursoList where anoLectivo equals to (anoLectivoId + 1)
        defaultDissertacaoFinalCursoShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        dissertacaoFinalCurso.setUtilizador(utilizador);
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
        Long utilizadorId = utilizador.getId();

        // Get all the dissertacaoFinalCursoList where utilizador equals to utilizadorId
        defaultDissertacaoFinalCursoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the dissertacaoFinalCursoList where utilizador equals to (utilizadorId + 1)
        defaultDissertacaoFinalCursoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByTurmaIsEqualToSomething() throws Exception {
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
            turma = TurmaResourceIT.createEntity(em);
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(turma);
        em.flush();
        dissertacaoFinalCurso.setTurma(turma);
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
        Long turmaId = turma.getId();

        // Get all the dissertacaoFinalCursoList where turma equals to turmaId
        defaultDissertacaoFinalCursoShouldBeFound("turmaId.equals=" + turmaId);

        // Get all the dissertacaoFinalCursoList where turma equals to (turmaId + 1)
        defaultDissertacaoFinalCursoShouldNotBeFound("turmaId.equals=" + (turmaId + 1));
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByOrientadorIsEqualToSomething() throws Exception {
        Docente orientador;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
            orientador = DocenteResourceIT.createEntity(em);
        } else {
            orientador = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(orientador);
        em.flush();
        dissertacaoFinalCurso.setOrientador(orientador);
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
        Long orientadorId = orientador.getId();

        // Get all the dissertacaoFinalCursoList where orientador equals to orientadorId
        defaultDissertacaoFinalCursoShouldBeFound("orientadorId.equals=" + orientadorId);

        // Get all the dissertacaoFinalCursoList where orientador equals to (orientadorId + 1)
        defaultDissertacaoFinalCursoShouldNotBeFound("orientadorId.equals=" + (orientadorId + 1));
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByEspecialidadeIsEqualToSomething() throws Exception {
        AreaFormacao especialidade;
        if (TestUtil.findAll(em, AreaFormacao.class).isEmpty()) {
            dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
            especialidade = AreaFormacaoResourceIT.createEntity(em);
        } else {
            especialidade = TestUtil.findAll(em, AreaFormacao.class).get(0);
        }
        em.persist(especialidade);
        em.flush();
        dissertacaoFinalCurso.setEspecialidade(especialidade);
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
        Long especialidadeId = especialidade.getId();

        // Get all the dissertacaoFinalCursoList where especialidade equals to especialidadeId
        defaultDissertacaoFinalCursoShouldBeFound("especialidadeId.equals=" + especialidadeId);

        // Get all the dissertacaoFinalCursoList where especialidade equals to (especialidadeId + 1)
        defaultDissertacaoFinalCursoShouldNotBeFound("especialidadeId.equals=" + (especialidadeId + 1));
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByDiscenteIsEqualToSomething() throws Exception {
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
            discente = DiscenteResourceIT.createEntity(em);
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        em.persist(discente);
        em.flush();
        dissertacaoFinalCurso.setDiscente(discente);
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
        Long discenteId = discente.getId();

        // Get all the dissertacaoFinalCursoList where discente equals to discenteId
        defaultDissertacaoFinalCursoShouldBeFound("discenteId.equals=" + discenteId);

        // Get all the dissertacaoFinalCursoList where discente equals to (discenteId + 1)
        defaultDissertacaoFinalCursoShouldNotBeFound("discenteId.equals=" + (discenteId + 1));
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByEstadoIsEqualToSomething() throws Exception {
        EstadoDissertacao estado;
        if (TestUtil.findAll(em, EstadoDissertacao.class).isEmpty()) {
            dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
            estado = EstadoDissertacaoResourceIT.createEntity(em);
        } else {
            estado = TestUtil.findAll(em, EstadoDissertacao.class).get(0);
        }
        em.persist(estado);
        em.flush();
        dissertacaoFinalCurso.setEstado(estado);
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
        Long estadoId = estado.getId();

        // Get all the dissertacaoFinalCursoList where estado equals to estadoId
        defaultDissertacaoFinalCursoShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the dissertacaoFinalCursoList where estado equals to (estadoId + 1)
        defaultDissertacaoFinalCursoShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }

    @Test
    @Transactional
    void getAllDissertacaoFinalCursosByNaturezaIsEqualToSomething() throws Exception {
        NaturezaTrabalho natureza;
        if (TestUtil.findAll(em, NaturezaTrabalho.class).isEmpty()) {
            dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
            natureza = NaturezaTrabalhoResourceIT.createEntity(em);
        } else {
            natureza = TestUtil.findAll(em, NaturezaTrabalho.class).get(0);
        }
        em.persist(natureza);
        em.flush();
        dissertacaoFinalCurso.setNatureza(natureza);
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);
        Long naturezaId = natureza.getId();

        // Get all the dissertacaoFinalCursoList where natureza equals to naturezaId
        defaultDissertacaoFinalCursoShouldBeFound("naturezaId.equals=" + naturezaId);

        // Get all the dissertacaoFinalCursoList where natureza equals to (naturezaId + 1)
        defaultDissertacaoFinalCursoShouldNotBeFound("naturezaId.equals=" + (naturezaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDissertacaoFinalCursoShouldBeFound(String filter) throws Exception {
        restDissertacaoFinalCursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dissertacaoFinalCurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].tema").value(hasItem(DEFAULT_TEMA)))
            .andExpect(jsonPath("$.[*].objectivoGeral").value(hasItem(DEFAULT_OBJECTIVO_GERAL)))
            .andExpect(jsonPath("$.[*].objectivosEspecificos").value(hasItem(DEFAULT_OBJECTIVOS_ESPECIFICOS.toString())))
            .andExpect(jsonPath("$.[*].introducao").value(hasItem(DEFAULT_INTRODUCAO.toString())))
            .andExpect(jsonPath("$.[*].resumo").value(hasItem(DEFAULT_RESUMO.toString())))
            .andExpect(jsonPath("$.[*].problema").value(hasItem(DEFAULT_PROBLEMA.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.toString())))
            .andExpect(jsonPath("$.[*].metodologia").value(hasItem(DEFAULT_METODOLOGIA.toString())))
            .andExpect(jsonPath("$.[*].referenciasBibliograficas").value(hasItem(DEFAULT_REFERENCIAS_BIBLIOGRAFICAS.toString())))
            .andExpect(jsonPath("$.[*].observacaoOrientador").value(hasItem(DEFAULT_OBSERVACAO_ORIENTADOR.toString())))
            .andExpect(jsonPath("$.[*].observacaoAreaFormacao").value(hasItem(DEFAULT_OBSERVACAO_AREA_FORMACAO.toString())))
            .andExpect(jsonPath("$.[*].observacaoInstituicao").value(hasItem(DEFAULT_OBSERVACAO_INSTITUICAO.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].termosCompromissos").value(hasItem(DEFAULT_TERMOS_COMPROMISSOS.toString())))
            .andExpect(jsonPath("$.[*].isAceiteTermosCompromisso").value(hasItem(DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO.booleanValue())));

        // Check, that the count call also returns 1
        restDissertacaoFinalCursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDissertacaoFinalCursoShouldNotBeFound(String filter) throws Exception {
        restDissertacaoFinalCursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDissertacaoFinalCursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDissertacaoFinalCurso() throws Exception {
        // Get the dissertacaoFinalCurso
        restDissertacaoFinalCursoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDissertacaoFinalCurso() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        int databaseSizeBeforeUpdate = dissertacaoFinalCursoRepository.findAll().size();

        // Update the dissertacaoFinalCurso
        DissertacaoFinalCurso updatedDissertacaoFinalCurso = dissertacaoFinalCursoRepository.findById(dissertacaoFinalCurso.getId()).get();
        // Disconnect from session so that the updates on updatedDissertacaoFinalCurso are not directly saved in db
        em.detach(updatedDissertacaoFinalCurso);
        updatedDissertacaoFinalCurso
            .numero(UPDATED_NUMERO)
            .timestamp(UPDATED_TIMESTAMP)
            .data(UPDATED_DATA)
            .tema(UPDATED_TEMA)
            .objectivoGeral(UPDATED_OBJECTIVO_GERAL)
            .objectivosEspecificos(UPDATED_OBJECTIVOS_ESPECIFICOS)
            .introducao(UPDATED_INTRODUCAO)
            .resumo(UPDATED_RESUMO)
            .problema(UPDATED_PROBLEMA)
            .resultado(UPDATED_RESULTADO)
            .metodologia(UPDATED_METODOLOGIA)
            .referenciasBibliograficas(UPDATED_REFERENCIAS_BIBLIOGRAFICAS)
            .observacaoOrientador(UPDATED_OBSERVACAO_ORIENTADOR)
            .observacaoAreaFormacao(UPDATED_OBSERVACAO_AREA_FORMACAO)
            .observacaoInstituicao(UPDATED_OBSERVACAO_INSTITUICAO)
            .hash(UPDATED_HASH)
            .termosCompromissos(UPDATED_TERMOS_COMPROMISSOS)
            .isAceiteTermosCompromisso(UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(updatedDissertacaoFinalCurso);

        restDissertacaoFinalCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dissertacaoFinalCursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isOk());

        // Validate the DissertacaoFinalCurso in the database
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeUpdate);
        DissertacaoFinalCurso testDissertacaoFinalCurso = dissertacaoFinalCursoList.get(dissertacaoFinalCursoList.size() - 1);
        assertThat(testDissertacaoFinalCurso.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDissertacaoFinalCurso.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testDissertacaoFinalCurso.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDissertacaoFinalCurso.getTema()).isEqualTo(UPDATED_TEMA);
        assertThat(testDissertacaoFinalCurso.getObjectivoGeral()).isEqualTo(UPDATED_OBJECTIVO_GERAL);
        assertThat(testDissertacaoFinalCurso.getObjectivosEspecificos()).isEqualTo(UPDATED_OBJECTIVOS_ESPECIFICOS);
        assertThat(testDissertacaoFinalCurso.getIntroducao()).isEqualTo(UPDATED_INTRODUCAO);
        assertThat(testDissertacaoFinalCurso.getResumo()).isEqualTo(UPDATED_RESUMO);
        assertThat(testDissertacaoFinalCurso.getProblema()).isEqualTo(UPDATED_PROBLEMA);
        assertThat(testDissertacaoFinalCurso.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testDissertacaoFinalCurso.getMetodologia()).isEqualTo(UPDATED_METODOLOGIA);
        assertThat(testDissertacaoFinalCurso.getReferenciasBibliograficas()).isEqualTo(UPDATED_REFERENCIAS_BIBLIOGRAFICAS);
        assertThat(testDissertacaoFinalCurso.getObservacaoOrientador()).isEqualTo(UPDATED_OBSERVACAO_ORIENTADOR);
        assertThat(testDissertacaoFinalCurso.getObservacaoAreaFormacao()).isEqualTo(UPDATED_OBSERVACAO_AREA_FORMACAO);
        assertThat(testDissertacaoFinalCurso.getObservacaoInstituicao()).isEqualTo(UPDATED_OBSERVACAO_INSTITUICAO);
        assertThat(testDissertacaoFinalCurso.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testDissertacaoFinalCurso.getTermosCompromissos()).isEqualTo(UPDATED_TERMOS_COMPROMISSOS);
        assertThat(testDissertacaoFinalCurso.getIsAceiteTermosCompromisso()).isEqualTo(UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void putNonExistingDissertacaoFinalCurso() throws Exception {
        int databaseSizeBeforeUpdate = dissertacaoFinalCursoRepository.findAll().size();
        dissertacaoFinalCurso.setId(count.incrementAndGet());

        // Create the DissertacaoFinalCurso
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDissertacaoFinalCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dissertacaoFinalCursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DissertacaoFinalCurso in the database
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDissertacaoFinalCurso() throws Exception {
        int databaseSizeBeforeUpdate = dissertacaoFinalCursoRepository.findAll().size();
        dissertacaoFinalCurso.setId(count.incrementAndGet());

        // Create the DissertacaoFinalCurso
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDissertacaoFinalCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DissertacaoFinalCurso in the database
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDissertacaoFinalCurso() throws Exception {
        int databaseSizeBeforeUpdate = dissertacaoFinalCursoRepository.findAll().size();
        dissertacaoFinalCurso.setId(count.incrementAndGet());

        // Create the DissertacaoFinalCurso
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDissertacaoFinalCursoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DissertacaoFinalCurso in the database
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDissertacaoFinalCursoWithPatch() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        int databaseSizeBeforeUpdate = dissertacaoFinalCursoRepository.findAll().size();

        // Update the dissertacaoFinalCurso using partial update
        DissertacaoFinalCurso partialUpdatedDissertacaoFinalCurso = new DissertacaoFinalCurso();
        partialUpdatedDissertacaoFinalCurso.setId(dissertacaoFinalCurso.getId());

        partialUpdatedDissertacaoFinalCurso
            .timestamp(UPDATED_TIMESTAMP)
            .tema(UPDATED_TEMA)
            .objectivoGeral(UPDATED_OBJECTIVO_GERAL)
            .objectivosEspecificos(UPDATED_OBJECTIVOS_ESPECIFICOS)
            .problema(UPDATED_PROBLEMA)
            .referenciasBibliograficas(UPDATED_REFERENCIAS_BIBLIOGRAFICAS)
            .observacaoOrientador(UPDATED_OBSERVACAO_ORIENTADOR)
            .observacaoAreaFormacao(UPDATED_OBSERVACAO_AREA_FORMACAO)
            .termosCompromissos(UPDATED_TERMOS_COMPROMISSOS)
            .isAceiteTermosCompromisso(UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);

        restDissertacaoFinalCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDissertacaoFinalCurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDissertacaoFinalCurso))
            )
            .andExpect(status().isOk());

        // Validate the DissertacaoFinalCurso in the database
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeUpdate);
        DissertacaoFinalCurso testDissertacaoFinalCurso = dissertacaoFinalCursoList.get(dissertacaoFinalCursoList.size() - 1);
        assertThat(testDissertacaoFinalCurso.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDissertacaoFinalCurso.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testDissertacaoFinalCurso.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDissertacaoFinalCurso.getTema()).isEqualTo(UPDATED_TEMA);
        assertThat(testDissertacaoFinalCurso.getObjectivoGeral()).isEqualTo(UPDATED_OBJECTIVO_GERAL);
        assertThat(testDissertacaoFinalCurso.getObjectivosEspecificos()).isEqualTo(UPDATED_OBJECTIVOS_ESPECIFICOS);
        assertThat(testDissertacaoFinalCurso.getIntroducao()).isEqualTo(DEFAULT_INTRODUCAO);
        assertThat(testDissertacaoFinalCurso.getResumo()).isEqualTo(DEFAULT_RESUMO);
        assertThat(testDissertacaoFinalCurso.getProblema()).isEqualTo(UPDATED_PROBLEMA);
        assertThat(testDissertacaoFinalCurso.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testDissertacaoFinalCurso.getMetodologia()).isEqualTo(DEFAULT_METODOLOGIA);
        assertThat(testDissertacaoFinalCurso.getReferenciasBibliograficas()).isEqualTo(UPDATED_REFERENCIAS_BIBLIOGRAFICAS);
        assertThat(testDissertacaoFinalCurso.getObservacaoOrientador()).isEqualTo(UPDATED_OBSERVACAO_ORIENTADOR);
        assertThat(testDissertacaoFinalCurso.getObservacaoAreaFormacao()).isEqualTo(UPDATED_OBSERVACAO_AREA_FORMACAO);
        assertThat(testDissertacaoFinalCurso.getObservacaoInstituicao()).isEqualTo(DEFAULT_OBSERVACAO_INSTITUICAO);
        assertThat(testDissertacaoFinalCurso.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testDissertacaoFinalCurso.getTermosCompromissos()).isEqualTo(UPDATED_TERMOS_COMPROMISSOS);
        assertThat(testDissertacaoFinalCurso.getIsAceiteTermosCompromisso()).isEqualTo(UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void fullUpdateDissertacaoFinalCursoWithPatch() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        int databaseSizeBeforeUpdate = dissertacaoFinalCursoRepository.findAll().size();

        // Update the dissertacaoFinalCurso using partial update
        DissertacaoFinalCurso partialUpdatedDissertacaoFinalCurso = new DissertacaoFinalCurso();
        partialUpdatedDissertacaoFinalCurso.setId(dissertacaoFinalCurso.getId());

        partialUpdatedDissertacaoFinalCurso
            .numero(UPDATED_NUMERO)
            .timestamp(UPDATED_TIMESTAMP)
            .data(UPDATED_DATA)
            .tema(UPDATED_TEMA)
            .objectivoGeral(UPDATED_OBJECTIVO_GERAL)
            .objectivosEspecificos(UPDATED_OBJECTIVOS_ESPECIFICOS)
            .introducao(UPDATED_INTRODUCAO)
            .resumo(UPDATED_RESUMO)
            .problema(UPDATED_PROBLEMA)
            .resultado(UPDATED_RESULTADO)
            .metodologia(UPDATED_METODOLOGIA)
            .referenciasBibliograficas(UPDATED_REFERENCIAS_BIBLIOGRAFICAS)
            .observacaoOrientador(UPDATED_OBSERVACAO_ORIENTADOR)
            .observacaoAreaFormacao(UPDATED_OBSERVACAO_AREA_FORMACAO)
            .observacaoInstituicao(UPDATED_OBSERVACAO_INSTITUICAO)
            .hash(UPDATED_HASH)
            .termosCompromissos(UPDATED_TERMOS_COMPROMISSOS)
            .isAceiteTermosCompromisso(UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);

        restDissertacaoFinalCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDissertacaoFinalCurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDissertacaoFinalCurso))
            )
            .andExpect(status().isOk());

        // Validate the DissertacaoFinalCurso in the database
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeUpdate);
        DissertacaoFinalCurso testDissertacaoFinalCurso = dissertacaoFinalCursoList.get(dissertacaoFinalCursoList.size() - 1);
        assertThat(testDissertacaoFinalCurso.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDissertacaoFinalCurso.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testDissertacaoFinalCurso.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDissertacaoFinalCurso.getTema()).isEqualTo(UPDATED_TEMA);
        assertThat(testDissertacaoFinalCurso.getObjectivoGeral()).isEqualTo(UPDATED_OBJECTIVO_GERAL);
        assertThat(testDissertacaoFinalCurso.getObjectivosEspecificos()).isEqualTo(UPDATED_OBJECTIVOS_ESPECIFICOS);
        assertThat(testDissertacaoFinalCurso.getIntroducao()).isEqualTo(UPDATED_INTRODUCAO);
        assertThat(testDissertacaoFinalCurso.getResumo()).isEqualTo(UPDATED_RESUMO);
        assertThat(testDissertacaoFinalCurso.getProblema()).isEqualTo(UPDATED_PROBLEMA);
        assertThat(testDissertacaoFinalCurso.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testDissertacaoFinalCurso.getMetodologia()).isEqualTo(UPDATED_METODOLOGIA);
        assertThat(testDissertacaoFinalCurso.getReferenciasBibliograficas()).isEqualTo(UPDATED_REFERENCIAS_BIBLIOGRAFICAS);
        assertThat(testDissertacaoFinalCurso.getObservacaoOrientador()).isEqualTo(UPDATED_OBSERVACAO_ORIENTADOR);
        assertThat(testDissertacaoFinalCurso.getObservacaoAreaFormacao()).isEqualTo(UPDATED_OBSERVACAO_AREA_FORMACAO);
        assertThat(testDissertacaoFinalCurso.getObservacaoInstituicao()).isEqualTo(UPDATED_OBSERVACAO_INSTITUICAO);
        assertThat(testDissertacaoFinalCurso.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testDissertacaoFinalCurso.getTermosCompromissos()).isEqualTo(UPDATED_TERMOS_COMPROMISSOS);
        assertThat(testDissertacaoFinalCurso.getIsAceiteTermosCompromisso()).isEqualTo(UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void patchNonExistingDissertacaoFinalCurso() throws Exception {
        int databaseSizeBeforeUpdate = dissertacaoFinalCursoRepository.findAll().size();
        dissertacaoFinalCurso.setId(count.incrementAndGet());

        // Create the DissertacaoFinalCurso
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDissertacaoFinalCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dissertacaoFinalCursoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DissertacaoFinalCurso in the database
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDissertacaoFinalCurso() throws Exception {
        int databaseSizeBeforeUpdate = dissertacaoFinalCursoRepository.findAll().size();
        dissertacaoFinalCurso.setId(count.incrementAndGet());

        // Create the DissertacaoFinalCurso
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDissertacaoFinalCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DissertacaoFinalCurso in the database
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDissertacaoFinalCurso() throws Exception {
        int databaseSizeBeforeUpdate = dissertacaoFinalCursoRepository.findAll().size();
        dissertacaoFinalCurso.setId(count.incrementAndGet());

        // Create the DissertacaoFinalCurso
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO = dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDissertacaoFinalCursoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dissertacaoFinalCursoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DissertacaoFinalCurso in the database
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDissertacaoFinalCurso() throws Exception {
        // Initialize the database
        dissertacaoFinalCursoRepository.saveAndFlush(dissertacaoFinalCurso);

        int databaseSizeBeforeDelete = dissertacaoFinalCursoRepository.findAll().size();

        // Delete the dissertacaoFinalCurso
        restDissertacaoFinalCursoMockMvc
            .perform(delete(ENTITY_API_URL_ID, dissertacaoFinalCurso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DissertacaoFinalCurso> dissertacaoFinalCursoList = dissertacaoFinalCursoRepository.findAll();
        assertThat(dissertacaoFinalCursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
