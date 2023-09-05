package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.EncarregadoEducacao;
import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.NotasGeralDisciplina;
import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.domain.Ocorrencia;
import com.ravunana.longonkelo.domain.PlanoDesconto;
import com.ravunana.longonkelo.domain.Recibo;
import com.ravunana.longonkelo.domain.Transacao;
import com.ravunana.longonkelo.domain.TransferenciaTurma;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.domain.enumeration.EstadoAcademico;
import com.ravunana.longonkelo.repository.MatriculaRepository;
import com.ravunana.longonkelo.service.MatriculaService;
import com.ravunana.longonkelo.service.criteria.MatriculaCriteria;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.mapper.MatriculaMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link MatriculaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MatriculaResourceIT {

    private static final String DEFAULT_CHAVE_COMPOSTA_1 = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE_COMPOSTA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CHAVE_COMPOSTA_2 = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE_COMPOSTA_2 = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_MATRICULA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_MATRICULA = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_CHAMADA = 0;
    private static final Integer UPDATED_NUMERO_CHAMADA = 1;
    private static final Integer SMALLER_NUMERO_CHAMADA = 0 - 1;

    private static final EstadoAcademico DEFAULT_ESTADO = EstadoAcademico.INSCRITO;
    private static final EstadoAcademico UPDATED_ESTADO = EstadoAcademico.MATRICULADO;

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_TERMOS_COMPROMISSOS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TERMOS_COMPROMISSOS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TERMOS_COMPROMISSOS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TERMOS_COMPROMISSOS_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO = false;
    private static final Boolean UPDATED_IS_ACEITE_TERMOS_COMPROMISSO = true;

    private static final String ENTITY_API_URL = "/api/matriculas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Mock
    private MatriculaRepository matriculaRepositoryMock;

    @Autowired
    private MatriculaMapper matriculaMapper;

    @Mock
    private MatriculaService matriculaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatriculaMockMvc;

    private Matricula matricula;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matricula createEntity(EntityManager em) {
        Matricula matricula = new Matricula()
            .chaveComposta1(DEFAULT_CHAVE_COMPOSTA_1)
            .chaveComposta2(DEFAULT_CHAVE_COMPOSTA_2)
            .numeroMatricula(DEFAULT_NUMERO_MATRICULA)
            .numeroChamada(DEFAULT_NUMERO_CHAMADA)
            .estado(DEFAULT_ESTADO)
            .timestamp(DEFAULT_TIMESTAMP)
            .descricao(DEFAULT_DESCRICAO)
            .termosCompromissos(DEFAULT_TERMOS_COMPROMISSOS)
            .termosCompromissosContentType(DEFAULT_TERMOS_COMPROMISSOS_CONTENT_TYPE)
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
        matricula.setTurma(turma);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        matricula.setDiscente(discente);
        return matricula;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matricula createUpdatedEntity(EntityManager em) {
        Matricula matricula = new Matricula()
            .chaveComposta1(UPDATED_CHAVE_COMPOSTA_1)
            .chaveComposta2(UPDATED_CHAVE_COMPOSTA_2)
            .numeroMatricula(UPDATED_NUMERO_MATRICULA)
            .numeroChamada(UPDATED_NUMERO_CHAMADA)
            .estado(UPDATED_ESTADO)
            .timestamp(UPDATED_TIMESTAMP)
            .descricao(UPDATED_DESCRICAO)
            .termosCompromissos(UPDATED_TERMOS_COMPROMISSOS)
            .termosCompromissosContentType(UPDATED_TERMOS_COMPROMISSOS_CONTENT_TYPE)
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
        matricula.setTurma(turma);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createUpdatedEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        matricula.setDiscente(discente);
        return matricula;
    }

    @BeforeEach
    public void initTest() {
        matricula = createEntity(em);
    }

    @Test
    @Transactional
    void createMatricula() throws Exception {
        int databaseSizeBeforeCreate = matriculaRepository.findAll().size();
        // Create the Matricula
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);
        restMatriculaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isCreated());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeCreate + 1);
        Matricula testMatricula = matriculaList.get(matriculaList.size() - 1);
        assertThat(testMatricula.getChaveComposta1()).isEqualTo(DEFAULT_CHAVE_COMPOSTA_1);
        assertThat(testMatricula.getChaveComposta2()).isEqualTo(DEFAULT_CHAVE_COMPOSTA_2);
        assertThat(testMatricula.getNumeroMatricula()).isEqualTo(DEFAULT_NUMERO_MATRICULA);
        assertThat(testMatricula.getNumeroChamada()).isEqualTo(DEFAULT_NUMERO_CHAMADA);
        assertThat(testMatricula.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMatricula.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testMatricula.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMatricula.getTermosCompromissos()).isEqualTo(DEFAULT_TERMOS_COMPROMISSOS);
        assertThat(testMatricula.getTermosCompromissosContentType()).isEqualTo(DEFAULT_TERMOS_COMPROMISSOS_CONTENT_TYPE);
        assertThat(testMatricula.getIsAceiteTermosCompromisso()).isEqualTo(DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void createMatriculaWithExistingId() throws Exception {
        // Create the Matricula with an existing ID
        matricula.setId(1L);
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        int databaseSizeBeforeCreate = matriculaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatriculaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroMatriculaIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setNumeroMatricula(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setEstado(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMatriculas() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList
        restMatriculaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matricula.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta1").value(hasItem(DEFAULT_CHAVE_COMPOSTA_1)))
            .andExpect(jsonPath("$.[*].chaveComposta2").value(hasItem(DEFAULT_CHAVE_COMPOSTA_2)))
            .andExpect(jsonPath("$.[*].numeroMatricula").value(hasItem(DEFAULT_NUMERO_MATRICULA)))
            .andExpect(jsonPath("$.[*].numeroChamada").value(hasItem(DEFAULT_NUMERO_CHAMADA)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].termosCompromissosContentType").value(hasItem(DEFAULT_TERMOS_COMPROMISSOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].termosCompromissos").value(hasItem(Base64Utils.encodeToString(DEFAULT_TERMOS_COMPROMISSOS))))
            .andExpect(jsonPath("$.[*].isAceiteTermosCompromisso").value(hasItem(DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMatriculasWithEagerRelationshipsIsEnabled() throws Exception {
        when(matriculaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMatriculaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(matriculaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMatriculasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(matriculaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMatriculaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(matriculaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMatricula() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get the matricula
        restMatriculaMockMvc
            .perform(get(ENTITY_API_URL_ID, matricula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matricula.getId().intValue()))
            .andExpect(jsonPath("$.chaveComposta1").value(DEFAULT_CHAVE_COMPOSTA_1))
            .andExpect(jsonPath("$.chaveComposta2").value(DEFAULT_CHAVE_COMPOSTA_2))
            .andExpect(jsonPath("$.numeroMatricula").value(DEFAULT_NUMERO_MATRICULA))
            .andExpect(jsonPath("$.numeroChamada").value(DEFAULT_NUMERO_CHAMADA))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.termosCompromissosContentType").value(DEFAULT_TERMOS_COMPROMISSOS_CONTENT_TYPE))
            .andExpect(jsonPath("$.termosCompromissos").value(Base64Utils.encodeToString(DEFAULT_TERMOS_COMPROMISSOS)))
            .andExpect(jsonPath("$.isAceiteTermosCompromisso").value(DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO.booleanValue()));
    }

    @Test
    @Transactional
    void getMatriculasByIdFiltering() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        Long id = matricula.getId();

        defaultMatriculaShouldBeFound("id.equals=" + id);
        defaultMatriculaShouldNotBeFound("id.notEquals=" + id);

        defaultMatriculaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMatriculaShouldNotBeFound("id.greaterThan=" + id);

        defaultMatriculaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMatriculaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMatriculasByChaveComposta1IsEqualToSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where chaveComposta1 equals to DEFAULT_CHAVE_COMPOSTA_1
        defaultMatriculaShouldBeFound("chaveComposta1.equals=" + DEFAULT_CHAVE_COMPOSTA_1);

        // Get all the matriculaList where chaveComposta1 equals to UPDATED_CHAVE_COMPOSTA_1
        defaultMatriculaShouldNotBeFound("chaveComposta1.equals=" + UPDATED_CHAVE_COMPOSTA_1);
    }

    @Test
    @Transactional
    void getAllMatriculasByChaveComposta1IsInShouldWork() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where chaveComposta1 in DEFAULT_CHAVE_COMPOSTA_1 or UPDATED_CHAVE_COMPOSTA_1
        defaultMatriculaShouldBeFound("chaveComposta1.in=" + DEFAULT_CHAVE_COMPOSTA_1 + "," + UPDATED_CHAVE_COMPOSTA_1);

        // Get all the matriculaList where chaveComposta1 equals to UPDATED_CHAVE_COMPOSTA_1
        defaultMatriculaShouldNotBeFound("chaveComposta1.in=" + UPDATED_CHAVE_COMPOSTA_1);
    }

    @Test
    @Transactional
    void getAllMatriculasByChaveComposta1IsNullOrNotNull() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where chaveComposta1 is not null
        defaultMatriculaShouldBeFound("chaveComposta1.specified=true");

        // Get all the matriculaList where chaveComposta1 is null
        defaultMatriculaShouldNotBeFound("chaveComposta1.specified=false");
    }

    @Test
    @Transactional
    void getAllMatriculasByChaveComposta1ContainsSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where chaveComposta1 contains DEFAULT_CHAVE_COMPOSTA_1
        defaultMatriculaShouldBeFound("chaveComposta1.contains=" + DEFAULT_CHAVE_COMPOSTA_1);

        // Get all the matriculaList where chaveComposta1 contains UPDATED_CHAVE_COMPOSTA_1
        defaultMatriculaShouldNotBeFound("chaveComposta1.contains=" + UPDATED_CHAVE_COMPOSTA_1);
    }

    @Test
    @Transactional
    void getAllMatriculasByChaveComposta1NotContainsSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where chaveComposta1 does not contain DEFAULT_CHAVE_COMPOSTA_1
        defaultMatriculaShouldNotBeFound("chaveComposta1.doesNotContain=" + DEFAULT_CHAVE_COMPOSTA_1);

        // Get all the matriculaList where chaveComposta1 does not contain UPDATED_CHAVE_COMPOSTA_1
        defaultMatriculaShouldBeFound("chaveComposta1.doesNotContain=" + UPDATED_CHAVE_COMPOSTA_1);
    }

    @Test
    @Transactional
    void getAllMatriculasByChaveComposta2IsEqualToSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where chaveComposta2 equals to DEFAULT_CHAVE_COMPOSTA_2
        defaultMatriculaShouldBeFound("chaveComposta2.equals=" + DEFAULT_CHAVE_COMPOSTA_2);

        // Get all the matriculaList where chaveComposta2 equals to UPDATED_CHAVE_COMPOSTA_2
        defaultMatriculaShouldNotBeFound("chaveComposta2.equals=" + UPDATED_CHAVE_COMPOSTA_2);
    }

    @Test
    @Transactional
    void getAllMatriculasByChaveComposta2IsInShouldWork() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where chaveComposta2 in DEFAULT_CHAVE_COMPOSTA_2 or UPDATED_CHAVE_COMPOSTA_2
        defaultMatriculaShouldBeFound("chaveComposta2.in=" + DEFAULT_CHAVE_COMPOSTA_2 + "," + UPDATED_CHAVE_COMPOSTA_2);

        // Get all the matriculaList where chaveComposta2 equals to UPDATED_CHAVE_COMPOSTA_2
        defaultMatriculaShouldNotBeFound("chaveComposta2.in=" + UPDATED_CHAVE_COMPOSTA_2);
    }

    @Test
    @Transactional
    void getAllMatriculasByChaveComposta2IsNullOrNotNull() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where chaveComposta2 is not null
        defaultMatriculaShouldBeFound("chaveComposta2.specified=true");

        // Get all the matriculaList where chaveComposta2 is null
        defaultMatriculaShouldNotBeFound("chaveComposta2.specified=false");
    }

    @Test
    @Transactional
    void getAllMatriculasByChaveComposta2ContainsSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where chaveComposta2 contains DEFAULT_CHAVE_COMPOSTA_2
        defaultMatriculaShouldBeFound("chaveComposta2.contains=" + DEFAULT_CHAVE_COMPOSTA_2);

        // Get all the matriculaList where chaveComposta2 contains UPDATED_CHAVE_COMPOSTA_2
        defaultMatriculaShouldNotBeFound("chaveComposta2.contains=" + UPDATED_CHAVE_COMPOSTA_2);
    }

    @Test
    @Transactional
    void getAllMatriculasByChaveComposta2NotContainsSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where chaveComposta2 does not contain DEFAULT_CHAVE_COMPOSTA_2
        defaultMatriculaShouldNotBeFound("chaveComposta2.doesNotContain=" + DEFAULT_CHAVE_COMPOSTA_2);

        // Get all the matriculaList where chaveComposta2 does not contain UPDATED_CHAVE_COMPOSTA_2
        defaultMatriculaShouldBeFound("chaveComposta2.doesNotContain=" + UPDATED_CHAVE_COMPOSTA_2);
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroMatriculaIsEqualToSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroMatricula equals to DEFAULT_NUMERO_MATRICULA
        defaultMatriculaShouldBeFound("numeroMatricula.equals=" + DEFAULT_NUMERO_MATRICULA);

        // Get all the matriculaList where numeroMatricula equals to UPDATED_NUMERO_MATRICULA
        defaultMatriculaShouldNotBeFound("numeroMatricula.equals=" + UPDATED_NUMERO_MATRICULA);
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroMatriculaIsInShouldWork() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroMatricula in DEFAULT_NUMERO_MATRICULA or UPDATED_NUMERO_MATRICULA
        defaultMatriculaShouldBeFound("numeroMatricula.in=" + DEFAULT_NUMERO_MATRICULA + "," + UPDATED_NUMERO_MATRICULA);

        // Get all the matriculaList where numeroMatricula equals to UPDATED_NUMERO_MATRICULA
        defaultMatriculaShouldNotBeFound("numeroMatricula.in=" + UPDATED_NUMERO_MATRICULA);
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroMatriculaIsNullOrNotNull() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroMatricula is not null
        defaultMatriculaShouldBeFound("numeroMatricula.specified=true");

        // Get all the matriculaList where numeroMatricula is null
        defaultMatriculaShouldNotBeFound("numeroMatricula.specified=false");
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroMatriculaContainsSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroMatricula contains DEFAULT_NUMERO_MATRICULA
        defaultMatriculaShouldBeFound("numeroMatricula.contains=" + DEFAULT_NUMERO_MATRICULA);

        // Get all the matriculaList where numeroMatricula contains UPDATED_NUMERO_MATRICULA
        defaultMatriculaShouldNotBeFound("numeroMatricula.contains=" + UPDATED_NUMERO_MATRICULA);
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroMatriculaNotContainsSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroMatricula does not contain DEFAULT_NUMERO_MATRICULA
        defaultMatriculaShouldNotBeFound("numeroMatricula.doesNotContain=" + DEFAULT_NUMERO_MATRICULA);

        // Get all the matriculaList where numeroMatricula does not contain UPDATED_NUMERO_MATRICULA
        defaultMatriculaShouldBeFound("numeroMatricula.doesNotContain=" + UPDATED_NUMERO_MATRICULA);
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroChamadaIsEqualToSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroChamada equals to DEFAULT_NUMERO_CHAMADA
        defaultMatriculaShouldBeFound("numeroChamada.equals=" + DEFAULT_NUMERO_CHAMADA);

        // Get all the matriculaList where numeroChamada equals to UPDATED_NUMERO_CHAMADA
        defaultMatriculaShouldNotBeFound("numeroChamada.equals=" + UPDATED_NUMERO_CHAMADA);
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroChamadaIsInShouldWork() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroChamada in DEFAULT_NUMERO_CHAMADA or UPDATED_NUMERO_CHAMADA
        defaultMatriculaShouldBeFound("numeroChamada.in=" + DEFAULT_NUMERO_CHAMADA + "," + UPDATED_NUMERO_CHAMADA);

        // Get all the matriculaList where numeroChamada equals to UPDATED_NUMERO_CHAMADA
        defaultMatriculaShouldNotBeFound("numeroChamada.in=" + UPDATED_NUMERO_CHAMADA);
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroChamadaIsNullOrNotNull() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroChamada is not null
        defaultMatriculaShouldBeFound("numeroChamada.specified=true");

        // Get all the matriculaList where numeroChamada is null
        defaultMatriculaShouldNotBeFound("numeroChamada.specified=false");
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroChamadaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroChamada is greater than or equal to DEFAULT_NUMERO_CHAMADA
        defaultMatriculaShouldBeFound("numeroChamada.greaterThanOrEqual=" + DEFAULT_NUMERO_CHAMADA);

        // Get all the matriculaList where numeroChamada is greater than or equal to UPDATED_NUMERO_CHAMADA
        defaultMatriculaShouldNotBeFound("numeroChamada.greaterThanOrEqual=" + UPDATED_NUMERO_CHAMADA);
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroChamadaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroChamada is less than or equal to DEFAULT_NUMERO_CHAMADA
        defaultMatriculaShouldBeFound("numeroChamada.lessThanOrEqual=" + DEFAULT_NUMERO_CHAMADA);

        // Get all the matriculaList where numeroChamada is less than or equal to SMALLER_NUMERO_CHAMADA
        defaultMatriculaShouldNotBeFound("numeroChamada.lessThanOrEqual=" + SMALLER_NUMERO_CHAMADA);
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroChamadaIsLessThanSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroChamada is less than DEFAULT_NUMERO_CHAMADA
        defaultMatriculaShouldNotBeFound("numeroChamada.lessThan=" + DEFAULT_NUMERO_CHAMADA);

        // Get all the matriculaList where numeroChamada is less than UPDATED_NUMERO_CHAMADA
        defaultMatriculaShouldBeFound("numeroChamada.lessThan=" + UPDATED_NUMERO_CHAMADA);
    }

    @Test
    @Transactional
    void getAllMatriculasByNumeroChamadaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where numeroChamada is greater than DEFAULT_NUMERO_CHAMADA
        defaultMatriculaShouldNotBeFound("numeroChamada.greaterThan=" + DEFAULT_NUMERO_CHAMADA);

        // Get all the matriculaList where numeroChamada is greater than SMALLER_NUMERO_CHAMADA
        defaultMatriculaShouldBeFound("numeroChamada.greaterThan=" + SMALLER_NUMERO_CHAMADA);
    }

    @Test
    @Transactional
    void getAllMatriculasByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where estado equals to DEFAULT_ESTADO
        defaultMatriculaShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the matriculaList where estado equals to UPDATED_ESTADO
        defaultMatriculaShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllMatriculasByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultMatriculaShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the matriculaList where estado equals to UPDATED_ESTADO
        defaultMatriculaShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllMatriculasByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where estado is not null
        defaultMatriculaShouldBeFound("estado.specified=true");

        // Get all the matriculaList where estado is null
        defaultMatriculaShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllMatriculasByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where timestamp equals to DEFAULT_TIMESTAMP
        defaultMatriculaShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the matriculaList where timestamp equals to UPDATED_TIMESTAMP
        defaultMatriculaShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllMatriculasByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultMatriculaShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the matriculaList where timestamp equals to UPDATED_TIMESTAMP
        defaultMatriculaShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllMatriculasByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where timestamp is not null
        defaultMatriculaShouldBeFound("timestamp.specified=true");

        // Get all the matriculaList where timestamp is null
        defaultMatriculaShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllMatriculasByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultMatriculaShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the matriculaList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultMatriculaShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllMatriculasByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultMatriculaShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the matriculaList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultMatriculaShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllMatriculasByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where timestamp is less than DEFAULT_TIMESTAMP
        defaultMatriculaShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the matriculaList where timestamp is less than UPDATED_TIMESTAMP
        defaultMatriculaShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllMatriculasByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultMatriculaShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the matriculaList where timestamp is greater than SMALLER_TIMESTAMP
        defaultMatriculaShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllMatriculasByIsAceiteTermosCompromissoIsEqualToSomething() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where isAceiteTermosCompromisso equals to DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO
        defaultMatriculaShouldBeFound("isAceiteTermosCompromisso.equals=" + DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO);

        // Get all the matriculaList where isAceiteTermosCompromisso equals to UPDATED_IS_ACEITE_TERMOS_COMPROMISSO
        defaultMatriculaShouldNotBeFound("isAceiteTermosCompromisso.equals=" + UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void getAllMatriculasByIsAceiteTermosCompromissoIsInShouldWork() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where isAceiteTermosCompromisso in DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO or UPDATED_IS_ACEITE_TERMOS_COMPROMISSO
        defaultMatriculaShouldBeFound(
            "isAceiteTermosCompromisso.in=" + DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO + "," + UPDATED_IS_ACEITE_TERMOS_COMPROMISSO
        );

        // Get all the matriculaList where isAceiteTermosCompromisso equals to UPDATED_IS_ACEITE_TERMOS_COMPROMISSO
        defaultMatriculaShouldNotBeFound("isAceiteTermosCompromisso.in=" + UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void getAllMatriculasByIsAceiteTermosCompromissoIsNullOrNotNull() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList where isAceiteTermosCompromisso is not null
        defaultMatriculaShouldBeFound("isAceiteTermosCompromisso.specified=true");

        // Get all the matriculaList where isAceiteTermosCompromisso is null
        defaultMatriculaShouldNotBeFound("isAceiteTermosCompromisso.specified=false");
    }

    // @Test
    // @Transactional
    // void getAllMatriculasByMatriculaIsEqualToSomething() throws Exception {
    //     Matricula matricula;
    //     if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
    //         matriculaRepository.saveAndFlush(matricula);
    //         matricula = MatriculaResourceIT.createEntity(em);
    //     } else {
    //         matricula = TestUtil.findAll(em, Matricula.class).get(0);
    //     }
    //     em.persist(matricula);
    //     em.flush();
    //     matricula.addMatricula(matricula);
    //     matriculaRepository.saveAndFlush(matricula);
    //     Long matriculaId = matricula.getId();

    //     // Get all the matriculaList where matricula equals to matriculaId
    //     defaultMatriculaShouldBeFound("matriculaId.equals=" + matriculaId);

    //     // Get all the matriculaList where matricula equals to (matriculaId + 1)
    //     defaultMatriculaShouldNotBeFound("matriculaId.equals=" + (matriculaId + 1));
    // }

    @Test
    @Transactional
    void getAllMatriculasByFacturasIsEqualToSomething() throws Exception {
        Factura facturas;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            facturas = FacturaResourceIT.createEntity(em);
        } else {
            facturas = TestUtil.findAll(em, Factura.class).get(0);
        }
        em.persist(facturas);
        em.flush();
        matricula.addFacturas(facturas);
        matriculaRepository.saveAndFlush(matricula);
        Long facturasId = facturas.getId();

        // Get all the matriculaList where facturas equals to facturasId
        defaultMatriculaShouldBeFound("facturasId.equals=" + facturasId);

        // Get all the matriculaList where facturas equals to (facturasId + 1)
        defaultMatriculaShouldNotBeFound("facturasId.equals=" + (facturasId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByTransacoesIsEqualToSomething() throws Exception {
        Transacao transacoes;
        if (TestUtil.findAll(em, Transacao.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            transacoes = TransacaoResourceIT.createEntity(em);
        } else {
            transacoes = TestUtil.findAll(em, Transacao.class).get(0);
        }
        em.persist(transacoes);
        em.flush();
        matricula.addTransacoes(transacoes);
        matriculaRepository.saveAndFlush(matricula);
        Long transacoesId = transacoes.getId();

        // Get all the matriculaList where transacoes equals to transacoesId
        defaultMatriculaShouldBeFound("transacoesId.equals=" + transacoesId);

        // Get all the matriculaList where transacoes equals to (transacoesId + 1)
        defaultMatriculaShouldNotBeFound("transacoesId.equals=" + (transacoesId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByRecibosIsEqualToSomething() throws Exception {
        Recibo recibos;
        if (TestUtil.findAll(em, Recibo.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            recibos = ReciboResourceIT.createEntity(em);
        } else {
            recibos = TestUtil.findAll(em, Recibo.class).get(0);
        }
        em.persist(recibos);
        em.flush();
        matricula.addRecibos(recibos);
        matriculaRepository.saveAndFlush(matricula);
        Long recibosId = recibos.getId();

        // Get all the matriculaList where recibos equals to recibosId
        defaultMatriculaShouldBeFound("recibosId.equals=" + recibosId);

        // Get all the matriculaList where recibos equals to (recibosId + 1)
        defaultMatriculaShouldNotBeFound("recibosId.equals=" + (recibosId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByNotasPeriodicaDisciplinaIsEqualToSomething() throws Exception {
        NotasPeriodicaDisciplina notasPeriodicaDisciplina;
        if (TestUtil.findAll(em, NotasPeriodicaDisciplina.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            notasPeriodicaDisciplina = NotasPeriodicaDisciplinaResourceIT.createEntity(em);
        } else {
            notasPeriodicaDisciplina = TestUtil.findAll(em, NotasPeriodicaDisciplina.class).get(0);
        }
        em.persist(notasPeriodicaDisciplina);
        em.flush();
        matricula.addNotasPeriodicaDisciplina(notasPeriodicaDisciplina);
        matriculaRepository.saveAndFlush(matricula);
        Long notasPeriodicaDisciplinaId = notasPeriodicaDisciplina.getId();

        // Get all the matriculaList where notasPeriodicaDisciplina equals to notasPeriodicaDisciplinaId
        defaultMatriculaShouldBeFound("notasPeriodicaDisciplinaId.equals=" + notasPeriodicaDisciplinaId);

        // Get all the matriculaList where notasPeriodicaDisciplina equals to (notasPeriodicaDisciplinaId + 1)
        defaultMatriculaShouldNotBeFound("notasPeriodicaDisciplinaId.equals=" + (notasPeriodicaDisciplinaId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByNotasGeralDisciplinaIsEqualToSomething() throws Exception {
        NotasGeralDisciplina notasGeralDisciplina;
        if (TestUtil.findAll(em, NotasGeralDisciplina.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            notasGeralDisciplina = NotasGeralDisciplinaResourceIT.createEntity(em);
        } else {
            notasGeralDisciplina = TestUtil.findAll(em, NotasGeralDisciplina.class).get(0);
        }
        em.persist(notasGeralDisciplina);
        em.flush();
        matricula.addNotasGeralDisciplina(notasGeralDisciplina);
        matriculaRepository.saveAndFlush(matricula);
        Long notasGeralDisciplinaId = notasGeralDisciplina.getId();

        // Get all the matriculaList where notasGeralDisciplina equals to notasGeralDisciplinaId
        defaultMatriculaShouldBeFound("notasGeralDisciplinaId.equals=" + notasGeralDisciplinaId);

        // Get all the matriculaList where notasGeralDisciplina equals to (notasGeralDisciplinaId + 1)
        defaultMatriculaShouldNotBeFound("notasGeralDisciplinaId.equals=" + (notasGeralDisciplinaId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByTransferenciaTurmaIsEqualToSomething() throws Exception {
        TransferenciaTurma transferenciaTurma;
        if (TestUtil.findAll(em, TransferenciaTurma.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            transferenciaTurma = TransferenciaTurmaResourceIT.createEntity(em);
        } else {
            transferenciaTurma = TestUtil.findAll(em, TransferenciaTurma.class).get(0);
        }
        em.persist(transferenciaTurma);
        em.flush();
        matricula.addTransferenciaTurma(transferenciaTurma);
        matriculaRepository.saveAndFlush(matricula);
        Long transferenciaTurmaId = transferenciaTurma.getId();

        // Get all the matriculaList where transferenciaTurma equals to transferenciaTurmaId
        defaultMatriculaShouldBeFound("transferenciaTurmaId.equals=" + transferenciaTurmaId);

        // Get all the matriculaList where transferenciaTurma equals to (transferenciaTurmaId + 1)
        defaultMatriculaShouldNotBeFound("transferenciaTurmaId.equals=" + (transferenciaTurmaId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByOcorrenciaIsEqualToSomething() throws Exception {
        Ocorrencia ocorrencia;
        if (TestUtil.findAll(em, Ocorrencia.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            ocorrencia = OcorrenciaResourceIT.createEntity(em);
        } else {
            ocorrencia = TestUtil.findAll(em, Ocorrencia.class).get(0);
        }
        em.persist(ocorrencia);
        em.flush();
        matricula.addOcorrencia(ocorrencia);
        matriculaRepository.saveAndFlush(matricula);
        Long ocorrenciaId = ocorrencia.getId();

        // Get all the matriculaList where ocorrencia equals to ocorrenciaId
        defaultMatriculaShouldBeFound("ocorrenciaId.equals=" + ocorrenciaId);

        // Get all the matriculaList where ocorrencia equals to (ocorrenciaId + 1)
        defaultMatriculaShouldNotBeFound("ocorrenciaId.equals=" + (ocorrenciaId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        matricula.setUtilizador(utilizador);
        matriculaRepository.saveAndFlush(matricula);
        Long utilizadorId = utilizador.getId();

        // Get all the matriculaList where utilizador equals to utilizadorId
        defaultMatriculaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the matriculaList where utilizador equals to (utilizadorId + 1)
        defaultMatriculaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByCategoriasMatriculasIsEqualToSomething() throws Exception {
        PlanoDesconto categoriasMatriculas;
        if (TestUtil.findAll(em, PlanoDesconto.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            categoriasMatriculas = PlanoDescontoResourceIT.createEntity(em);
        } else {
            categoriasMatriculas = TestUtil.findAll(em, PlanoDesconto.class).get(0);
        }
        em.persist(categoriasMatriculas);
        em.flush();
        matricula.addCategoriasMatriculas(categoriasMatriculas);
        matriculaRepository.saveAndFlush(matricula);
        Long categoriasMatriculasId = categoriasMatriculas.getId();

        // Get all the matriculaList where categoriasMatriculas equals to categoriasMatriculasId
        defaultMatriculaShouldBeFound("categoriasMatriculasId.equals=" + categoriasMatriculasId);

        // Get all the matriculaList where categoriasMatriculas equals to (categoriasMatriculasId + 1)
        defaultMatriculaShouldNotBeFound("categoriasMatriculasId.equals=" + (categoriasMatriculasId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByTurmaIsEqualToSomething() throws Exception {
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            turma = TurmaResourceIT.createEntity(em);
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(turma);
        em.flush();
        matricula.setTurma(turma);
        matriculaRepository.saveAndFlush(matricula);
        Long turmaId = turma.getId();

        // Get all the matriculaList where turma equals to turmaId
        defaultMatriculaShouldBeFound("turmaId.equals=" + turmaId);

        // Get all the matriculaList where turma equals to (turmaId + 1)
        defaultMatriculaShouldNotBeFound("turmaId.equals=" + (turmaId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByResponsavelFinanceiroIsEqualToSomething() throws Exception {
        EncarregadoEducacao responsavelFinanceiro;
        if (TestUtil.findAll(em, EncarregadoEducacao.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            responsavelFinanceiro = EncarregadoEducacaoResourceIT.createEntity(em);
        } else {
            responsavelFinanceiro = TestUtil.findAll(em, EncarregadoEducacao.class).get(0);
        }
        em.persist(responsavelFinanceiro);
        em.flush();
        matricula.setResponsavelFinanceiro(responsavelFinanceiro);
        matriculaRepository.saveAndFlush(matricula);
        Long responsavelFinanceiroId = responsavelFinanceiro.getId();

        // Get all the matriculaList where responsavelFinanceiro equals to responsavelFinanceiroId
        defaultMatriculaShouldBeFound("responsavelFinanceiroId.equals=" + responsavelFinanceiroId);

        // Get all the matriculaList where responsavelFinanceiro equals to (responsavelFinanceiroId + 1)
        defaultMatriculaShouldNotBeFound("responsavelFinanceiroId.equals=" + (responsavelFinanceiroId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByDiscenteIsEqualToSomething() throws Exception {
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            discente = DiscenteResourceIT.createEntity(em);
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        em.persist(discente);
        em.flush();
        matricula.setDiscente(discente);
        matriculaRepository.saveAndFlush(matricula);
        Long discenteId = discente.getId();

        // Get all the matriculaList where discente equals to discenteId
        defaultMatriculaShouldBeFound("discenteId.equals=" + discenteId);

        // Get all the matriculaList where discente equals to (discenteId + 1)
        defaultMatriculaShouldNotBeFound("discenteId.equals=" + (discenteId + 1));
    }

    @Test
    @Transactional
    void getAllMatriculasByReferenciaIsEqualToSomething() throws Exception {
        Matricula referencia;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matriculaRepository.saveAndFlush(matricula);
            referencia = MatriculaResourceIT.createEntity(em);
        } else {
            referencia = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(referencia);
        em.flush();
        matricula.setReferencia(referencia);
        matriculaRepository.saveAndFlush(matricula);
        Long referenciaId = referencia.getId();

        // Get all the matriculaList where referencia equals to referenciaId
        defaultMatriculaShouldBeFound("referenciaId.equals=" + referenciaId);

        // Get all the matriculaList where referencia equals to (referenciaId + 1)
        defaultMatriculaShouldNotBeFound("referenciaId.equals=" + (referenciaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMatriculaShouldBeFound(String filter) throws Exception {
        restMatriculaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matricula.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta1").value(hasItem(DEFAULT_CHAVE_COMPOSTA_1)))
            .andExpect(jsonPath("$.[*].chaveComposta2").value(hasItem(DEFAULT_CHAVE_COMPOSTA_2)))
            .andExpect(jsonPath("$.[*].numeroMatricula").value(hasItem(DEFAULT_NUMERO_MATRICULA)))
            .andExpect(jsonPath("$.[*].numeroChamada").value(hasItem(DEFAULT_NUMERO_CHAMADA)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].termosCompromissosContentType").value(hasItem(DEFAULT_TERMOS_COMPROMISSOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].termosCompromissos").value(hasItem(Base64Utils.encodeToString(DEFAULT_TERMOS_COMPROMISSOS))))
            .andExpect(jsonPath("$.[*].isAceiteTermosCompromisso").value(hasItem(DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO.booleanValue())));

        // Check, that the count call also returns 1
        restMatriculaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMatriculaShouldNotBeFound(String filter) throws Exception {
        restMatriculaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMatriculaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMatricula() throws Exception {
        // Get the matricula
        restMatriculaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatricula() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();

        // Update the matricula
        Matricula updatedMatricula = matriculaRepository.findById(matricula.getId()).get();
        // Disconnect from session so that the updates on updatedMatricula are not directly saved in db
        em.detach(updatedMatricula);
        updatedMatricula
            .chaveComposta1(UPDATED_CHAVE_COMPOSTA_1)
            .chaveComposta2(UPDATED_CHAVE_COMPOSTA_2)
            .numeroMatricula(UPDATED_NUMERO_MATRICULA)
            .numeroChamada(UPDATED_NUMERO_CHAMADA)
            .estado(UPDATED_ESTADO)
            .timestamp(UPDATED_TIMESTAMP)
            .descricao(UPDATED_DESCRICAO)
            .termosCompromissos(UPDATED_TERMOS_COMPROMISSOS)
            .termosCompromissosContentType(UPDATED_TERMOS_COMPROMISSOS_CONTENT_TYPE)
            .isAceiteTermosCompromisso(UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(updatedMatricula);

        restMatriculaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matriculaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matriculaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);
        Matricula testMatricula = matriculaList.get(matriculaList.size() - 1);
        assertThat(testMatricula.getChaveComposta1()).isEqualTo(UPDATED_CHAVE_COMPOSTA_1);
        assertThat(testMatricula.getChaveComposta2()).isEqualTo(UPDATED_CHAVE_COMPOSTA_2);
        assertThat(testMatricula.getNumeroMatricula()).isEqualTo(UPDATED_NUMERO_MATRICULA);
        assertThat(testMatricula.getNumeroChamada()).isEqualTo(UPDATED_NUMERO_CHAMADA);
        assertThat(testMatricula.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMatricula.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testMatricula.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMatricula.getTermosCompromissos()).isEqualTo(UPDATED_TERMOS_COMPROMISSOS);
        assertThat(testMatricula.getTermosCompromissosContentType()).isEqualTo(UPDATED_TERMOS_COMPROMISSOS_CONTENT_TYPE);
        assertThat(testMatricula.getIsAceiteTermosCompromisso()).isEqualTo(UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void putNonExistingMatricula() throws Exception {
        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();
        matricula.setId(count.incrementAndGet());

        // Create the Matricula
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatriculaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matriculaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matriculaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatricula() throws Exception {
        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();
        matricula.setId(count.incrementAndGet());

        // Create the Matricula
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatriculaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matriculaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatricula() throws Exception {
        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();
        matricula.setId(count.incrementAndGet());

        // Create the Matricula
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatriculaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatriculaWithPatch() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();

        // Update the matricula using partial update
        Matricula partialUpdatedMatricula = new Matricula();
        partialUpdatedMatricula.setId(matricula.getId());

        partialUpdatedMatricula
            .chaveComposta2(UPDATED_CHAVE_COMPOSTA_2)
            .numeroChamada(UPDATED_NUMERO_CHAMADA)
            .timestamp(UPDATED_TIMESTAMP)
            .descricao(UPDATED_DESCRICAO);

        restMatriculaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatricula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatricula))
            )
            .andExpect(status().isOk());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);
        Matricula testMatricula = matriculaList.get(matriculaList.size() - 1);
        assertThat(testMatricula.getChaveComposta1()).isEqualTo(DEFAULT_CHAVE_COMPOSTA_1);
        assertThat(testMatricula.getChaveComposta2()).isEqualTo(UPDATED_CHAVE_COMPOSTA_2);
        assertThat(testMatricula.getNumeroMatricula()).isEqualTo(DEFAULT_NUMERO_MATRICULA);
        assertThat(testMatricula.getNumeroChamada()).isEqualTo(UPDATED_NUMERO_CHAMADA);
        assertThat(testMatricula.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMatricula.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testMatricula.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMatricula.getTermosCompromissos()).isEqualTo(DEFAULT_TERMOS_COMPROMISSOS);
        assertThat(testMatricula.getTermosCompromissosContentType()).isEqualTo(DEFAULT_TERMOS_COMPROMISSOS_CONTENT_TYPE);
        assertThat(testMatricula.getIsAceiteTermosCompromisso()).isEqualTo(DEFAULT_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void fullUpdateMatriculaWithPatch() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();

        // Update the matricula using partial update
        Matricula partialUpdatedMatricula = new Matricula();
        partialUpdatedMatricula.setId(matricula.getId());

        partialUpdatedMatricula
            .chaveComposta1(UPDATED_CHAVE_COMPOSTA_1)
            .chaveComposta2(UPDATED_CHAVE_COMPOSTA_2)
            .numeroMatricula(UPDATED_NUMERO_MATRICULA)
            .numeroChamada(UPDATED_NUMERO_CHAMADA)
            .estado(UPDATED_ESTADO)
            .timestamp(UPDATED_TIMESTAMP)
            .descricao(UPDATED_DESCRICAO)
            .termosCompromissos(UPDATED_TERMOS_COMPROMISSOS)
            .termosCompromissosContentType(UPDATED_TERMOS_COMPROMISSOS_CONTENT_TYPE)
            .isAceiteTermosCompromisso(UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);

        restMatriculaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatricula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatricula))
            )
            .andExpect(status().isOk());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);
        Matricula testMatricula = matriculaList.get(matriculaList.size() - 1);
        assertThat(testMatricula.getChaveComposta1()).isEqualTo(UPDATED_CHAVE_COMPOSTA_1);
        assertThat(testMatricula.getChaveComposta2()).isEqualTo(UPDATED_CHAVE_COMPOSTA_2);
        assertThat(testMatricula.getNumeroMatricula()).isEqualTo(UPDATED_NUMERO_MATRICULA);
        assertThat(testMatricula.getNumeroChamada()).isEqualTo(UPDATED_NUMERO_CHAMADA);
        assertThat(testMatricula.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMatricula.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testMatricula.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMatricula.getTermosCompromissos()).isEqualTo(UPDATED_TERMOS_COMPROMISSOS);
        assertThat(testMatricula.getTermosCompromissosContentType()).isEqualTo(UPDATED_TERMOS_COMPROMISSOS_CONTENT_TYPE);
        assertThat(testMatricula.getIsAceiteTermosCompromisso()).isEqualTo(UPDATED_IS_ACEITE_TERMOS_COMPROMISSO);
    }

    @Test
    @Transactional
    void patchNonExistingMatricula() throws Exception {
        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();
        matricula.setId(count.incrementAndGet());

        // Create the Matricula
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatriculaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matriculaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matriculaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatricula() throws Exception {
        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();
        matricula.setId(count.incrementAndGet());

        // Create the Matricula
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatriculaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matriculaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatricula() throws Exception {
        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();
        matricula.setId(count.incrementAndGet());

        // Create the Matricula
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatriculaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(matriculaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatricula() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        int databaseSizeBeforeDelete = matriculaRepository.findAll().size();

        // Delete the matricula
        restMatriculaMockMvc
            .perform(delete(ENTITY_API_URL_ID, matricula.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
