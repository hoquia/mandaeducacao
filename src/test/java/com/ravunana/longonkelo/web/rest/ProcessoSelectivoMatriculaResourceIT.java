package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.ProcessoSelectivoMatriculaRepository;
import com.ravunana.longonkelo.service.ProcessoSelectivoMatriculaService;
import com.ravunana.longonkelo.service.criteria.ProcessoSelectivoMatriculaCriteria;
import com.ravunana.longonkelo.service.dto.ProcessoSelectivoMatriculaDTO;
import com.ravunana.longonkelo.service.mapper.ProcessoSelectivoMatriculaMapper;
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
 * Integration tests for the {@link ProcessoSelectivoMatriculaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProcessoSelectivoMatriculaResourceIT {

    private static final String DEFAULT_LOCAL_TESTE = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_TESTE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_TESTE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_TESTE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_TESTE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Double DEFAULT_NOTA_TESTE = 0D;
    private static final Double UPDATED_NOTA_TESTE = 1D;
    private static final Double SMALLER_NOTA_TESTE = 0D - 1D;

    private static final Boolean DEFAULT_IS_ADMITIDO = false;
    private static final Boolean UPDATED_IS_ADMITIDO = true;

    private static final String ENTITY_API_URL = "/api/processo-selectivo-matriculas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcessoSelectivoMatriculaRepository processoSelectivoMatriculaRepository;

    @Mock
    private ProcessoSelectivoMatriculaRepository processoSelectivoMatriculaRepositoryMock;

    @Autowired
    private ProcessoSelectivoMatriculaMapper processoSelectivoMatriculaMapper;

    @Mock
    private ProcessoSelectivoMatriculaService processoSelectivoMatriculaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessoSelectivoMatriculaMockMvc;

    private ProcessoSelectivoMatricula processoSelectivoMatricula;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessoSelectivoMatricula createEntity(EntityManager em) {
        ProcessoSelectivoMatricula processoSelectivoMatricula = new ProcessoSelectivoMatricula()
            .localTeste(DEFAULT_LOCAL_TESTE)
            .dataTeste(DEFAULT_DATA_TESTE)
            .notaTeste(DEFAULT_NOTA_TESTE)
            .isAdmitido(DEFAULT_IS_ADMITIDO);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        processoSelectivoMatricula.setTurma(turma);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        processoSelectivoMatricula.setDiscente(discente);
        return processoSelectivoMatricula;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessoSelectivoMatricula createUpdatedEntity(EntityManager em) {
        ProcessoSelectivoMatricula processoSelectivoMatricula = new ProcessoSelectivoMatricula()
            .localTeste(UPDATED_LOCAL_TESTE)
            .dataTeste(UPDATED_DATA_TESTE)
            .notaTeste(UPDATED_NOTA_TESTE)
            .isAdmitido(UPDATED_IS_ADMITIDO);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createUpdatedEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        processoSelectivoMatricula.setTurma(turma);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createUpdatedEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        processoSelectivoMatricula.setDiscente(discente);
        return processoSelectivoMatricula;
    }

    @BeforeEach
    public void initTest() {
        processoSelectivoMatricula = createEntity(em);
    }

    @Test
    @Transactional
    void createProcessoSelectivoMatricula() throws Exception {
        int databaseSizeBeforeCreate = processoSelectivoMatriculaRepository.findAll().size();
        // Create the ProcessoSelectivoMatricula
        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO = processoSelectivoMatriculaMapper.toDto(processoSelectivoMatricula);
        restProcessoSelectivoMatriculaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processoSelectivoMatriculaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProcessoSelectivoMatricula in the database
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessoSelectivoMatricula testProcessoSelectivoMatricula = processoSelectivoMatriculaList.get(
            processoSelectivoMatriculaList.size() - 1
        );
        assertThat(testProcessoSelectivoMatricula.getLocalTeste()).isEqualTo(DEFAULT_LOCAL_TESTE);
        assertThat(testProcessoSelectivoMatricula.getDataTeste()).isEqualTo(DEFAULT_DATA_TESTE);
        assertThat(testProcessoSelectivoMatricula.getNotaTeste()).isEqualTo(DEFAULT_NOTA_TESTE);
        assertThat(testProcessoSelectivoMatricula.getIsAdmitido()).isEqualTo(DEFAULT_IS_ADMITIDO);
    }

    @Test
    @Transactional
    void createProcessoSelectivoMatriculaWithExistingId() throws Exception {
        // Create the ProcessoSelectivoMatricula with an existing ID
        processoSelectivoMatricula.setId(1L);
        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO = processoSelectivoMatriculaMapper.toDto(processoSelectivoMatricula);

        int databaseSizeBeforeCreate = processoSelectivoMatriculaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessoSelectivoMatriculaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processoSelectivoMatriculaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessoSelectivoMatricula in the database
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculas() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList
        restProcessoSelectivoMatriculaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processoSelectivoMatricula.getId().intValue())))
            .andExpect(jsonPath("$.[*].localTeste").value(hasItem(DEFAULT_LOCAL_TESTE)))
            .andExpect(jsonPath("$.[*].dataTeste").value(hasItem(sameInstant(DEFAULT_DATA_TESTE))))
            .andExpect(jsonPath("$.[*].notaTeste").value(hasItem(DEFAULT_NOTA_TESTE.doubleValue())))
            .andExpect(jsonPath("$.[*].isAdmitido").value(hasItem(DEFAULT_IS_ADMITIDO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProcessoSelectivoMatriculasWithEagerRelationshipsIsEnabled() throws Exception {
        when(processoSelectivoMatriculaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProcessoSelectivoMatriculaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(processoSelectivoMatriculaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProcessoSelectivoMatriculasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(processoSelectivoMatriculaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProcessoSelectivoMatriculaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(processoSelectivoMatriculaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProcessoSelectivoMatricula() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get the processoSelectivoMatricula
        restProcessoSelectivoMatriculaMockMvc
            .perform(get(ENTITY_API_URL_ID, processoSelectivoMatricula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processoSelectivoMatricula.getId().intValue()))
            .andExpect(jsonPath("$.localTeste").value(DEFAULT_LOCAL_TESTE))
            .andExpect(jsonPath("$.dataTeste").value(sameInstant(DEFAULT_DATA_TESTE)))
            .andExpect(jsonPath("$.notaTeste").value(DEFAULT_NOTA_TESTE.doubleValue()))
            .andExpect(jsonPath("$.isAdmitido").value(DEFAULT_IS_ADMITIDO.booleanValue()));
    }

    @Test
    @Transactional
    void getProcessoSelectivoMatriculasByIdFiltering() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        Long id = processoSelectivoMatricula.getId();

        defaultProcessoSelectivoMatriculaShouldBeFound("id.equals=" + id);
        defaultProcessoSelectivoMatriculaShouldNotBeFound("id.notEquals=" + id);

        defaultProcessoSelectivoMatriculaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessoSelectivoMatriculaShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessoSelectivoMatriculaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessoSelectivoMatriculaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByLocalTesteIsEqualToSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where localTeste equals to DEFAULT_LOCAL_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("localTeste.equals=" + DEFAULT_LOCAL_TESTE);

        // Get all the processoSelectivoMatriculaList where localTeste equals to UPDATED_LOCAL_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("localTeste.equals=" + UPDATED_LOCAL_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByLocalTesteIsInShouldWork() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where localTeste in DEFAULT_LOCAL_TESTE or UPDATED_LOCAL_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("localTeste.in=" + DEFAULT_LOCAL_TESTE + "," + UPDATED_LOCAL_TESTE);

        // Get all the processoSelectivoMatriculaList where localTeste equals to UPDATED_LOCAL_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("localTeste.in=" + UPDATED_LOCAL_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByLocalTesteIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where localTeste is not null
        defaultProcessoSelectivoMatriculaShouldBeFound("localTeste.specified=true");

        // Get all the processoSelectivoMatriculaList where localTeste is null
        defaultProcessoSelectivoMatriculaShouldNotBeFound("localTeste.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByLocalTesteContainsSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where localTeste contains DEFAULT_LOCAL_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("localTeste.contains=" + DEFAULT_LOCAL_TESTE);

        // Get all the processoSelectivoMatriculaList where localTeste contains UPDATED_LOCAL_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("localTeste.contains=" + UPDATED_LOCAL_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByLocalTesteNotContainsSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where localTeste does not contain DEFAULT_LOCAL_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("localTeste.doesNotContain=" + DEFAULT_LOCAL_TESTE);

        // Get all the processoSelectivoMatriculaList where localTeste does not contain UPDATED_LOCAL_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("localTeste.doesNotContain=" + UPDATED_LOCAL_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByDataTesteIsEqualToSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where dataTeste equals to DEFAULT_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("dataTeste.equals=" + DEFAULT_DATA_TESTE);

        // Get all the processoSelectivoMatriculaList where dataTeste equals to UPDATED_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("dataTeste.equals=" + UPDATED_DATA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByDataTesteIsInShouldWork() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where dataTeste in DEFAULT_DATA_TESTE or UPDATED_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("dataTeste.in=" + DEFAULT_DATA_TESTE + "," + UPDATED_DATA_TESTE);

        // Get all the processoSelectivoMatriculaList where dataTeste equals to UPDATED_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("dataTeste.in=" + UPDATED_DATA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByDataTesteIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where dataTeste is not null
        defaultProcessoSelectivoMatriculaShouldBeFound("dataTeste.specified=true");

        // Get all the processoSelectivoMatriculaList where dataTeste is null
        defaultProcessoSelectivoMatriculaShouldNotBeFound("dataTeste.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByDataTesteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where dataTeste is greater than or equal to DEFAULT_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("dataTeste.greaterThanOrEqual=" + DEFAULT_DATA_TESTE);

        // Get all the processoSelectivoMatriculaList where dataTeste is greater than or equal to UPDATED_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("dataTeste.greaterThanOrEqual=" + UPDATED_DATA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByDataTesteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where dataTeste is less than or equal to DEFAULT_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("dataTeste.lessThanOrEqual=" + DEFAULT_DATA_TESTE);

        // Get all the processoSelectivoMatriculaList where dataTeste is less than or equal to SMALLER_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("dataTeste.lessThanOrEqual=" + SMALLER_DATA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByDataTesteIsLessThanSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where dataTeste is less than DEFAULT_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("dataTeste.lessThan=" + DEFAULT_DATA_TESTE);

        // Get all the processoSelectivoMatriculaList where dataTeste is less than UPDATED_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("dataTeste.lessThan=" + UPDATED_DATA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByDataTesteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where dataTeste is greater than DEFAULT_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("dataTeste.greaterThan=" + DEFAULT_DATA_TESTE);

        // Get all the processoSelectivoMatriculaList where dataTeste is greater than SMALLER_DATA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("dataTeste.greaterThan=" + SMALLER_DATA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByNotaTesteIsEqualToSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where notaTeste equals to DEFAULT_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("notaTeste.equals=" + DEFAULT_NOTA_TESTE);

        // Get all the processoSelectivoMatriculaList where notaTeste equals to UPDATED_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("notaTeste.equals=" + UPDATED_NOTA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByNotaTesteIsInShouldWork() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where notaTeste in DEFAULT_NOTA_TESTE or UPDATED_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("notaTeste.in=" + DEFAULT_NOTA_TESTE + "," + UPDATED_NOTA_TESTE);

        // Get all the processoSelectivoMatriculaList where notaTeste equals to UPDATED_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("notaTeste.in=" + UPDATED_NOTA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByNotaTesteIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where notaTeste is not null
        defaultProcessoSelectivoMatriculaShouldBeFound("notaTeste.specified=true");

        // Get all the processoSelectivoMatriculaList where notaTeste is null
        defaultProcessoSelectivoMatriculaShouldNotBeFound("notaTeste.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByNotaTesteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where notaTeste is greater than or equal to DEFAULT_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("notaTeste.greaterThanOrEqual=" + DEFAULT_NOTA_TESTE);

        // Get all the processoSelectivoMatriculaList where notaTeste is greater than or equal to UPDATED_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("notaTeste.greaterThanOrEqual=" + UPDATED_NOTA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByNotaTesteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where notaTeste is less than or equal to DEFAULT_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("notaTeste.lessThanOrEqual=" + DEFAULT_NOTA_TESTE);

        // Get all the processoSelectivoMatriculaList where notaTeste is less than or equal to SMALLER_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("notaTeste.lessThanOrEqual=" + SMALLER_NOTA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByNotaTesteIsLessThanSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where notaTeste is less than DEFAULT_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("notaTeste.lessThan=" + DEFAULT_NOTA_TESTE);

        // Get all the processoSelectivoMatriculaList where notaTeste is less than UPDATED_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("notaTeste.lessThan=" + UPDATED_NOTA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByNotaTesteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where notaTeste is greater than DEFAULT_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldNotBeFound("notaTeste.greaterThan=" + DEFAULT_NOTA_TESTE);

        // Get all the processoSelectivoMatriculaList where notaTeste is greater than SMALLER_NOTA_TESTE
        defaultProcessoSelectivoMatriculaShouldBeFound("notaTeste.greaterThan=" + SMALLER_NOTA_TESTE);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByIsAdmitidoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where isAdmitido equals to DEFAULT_IS_ADMITIDO
        defaultProcessoSelectivoMatriculaShouldBeFound("isAdmitido.equals=" + DEFAULT_IS_ADMITIDO);

        // Get all the processoSelectivoMatriculaList where isAdmitido equals to UPDATED_IS_ADMITIDO
        defaultProcessoSelectivoMatriculaShouldNotBeFound("isAdmitido.equals=" + UPDATED_IS_ADMITIDO);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByIsAdmitidoIsInShouldWork() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where isAdmitido in DEFAULT_IS_ADMITIDO or UPDATED_IS_ADMITIDO
        defaultProcessoSelectivoMatriculaShouldBeFound("isAdmitido.in=" + DEFAULT_IS_ADMITIDO + "," + UPDATED_IS_ADMITIDO);

        // Get all the processoSelectivoMatriculaList where isAdmitido equals to UPDATED_IS_ADMITIDO
        defaultProcessoSelectivoMatriculaShouldNotBeFound("isAdmitido.in=" + UPDATED_IS_ADMITIDO);
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByIsAdmitidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        // Get all the processoSelectivoMatriculaList where isAdmitido is not null
        defaultProcessoSelectivoMatriculaShouldBeFound("isAdmitido.specified=true");

        // Get all the processoSelectivoMatriculaList where isAdmitido is null
        defaultProcessoSelectivoMatriculaShouldNotBeFound("isAdmitido.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        processoSelectivoMatricula.addAnoLectivo(anoLectivo);
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the processoSelectivoMatriculaList where anoLectivo equals to anoLectivoId
        defaultProcessoSelectivoMatriculaShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the processoSelectivoMatriculaList where anoLectivo equals to (anoLectivoId + 1)
        defaultProcessoSelectivoMatriculaShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        processoSelectivoMatricula.setUtilizador(utilizador);
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);
        Long utilizadorId = utilizador.getId();

        // Get all the processoSelectivoMatriculaList where utilizador equals to utilizadorId
        defaultProcessoSelectivoMatriculaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the processoSelectivoMatriculaList where utilizador equals to (utilizadorId + 1)
        defaultProcessoSelectivoMatriculaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByTurmaIsEqualToSomething() throws Exception {
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);
            turma = TurmaResourceIT.createEntity(em);
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(turma);
        em.flush();
        processoSelectivoMatricula.setTurma(turma);
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);
        Long turmaId = turma.getId();

        // Get all the processoSelectivoMatriculaList where turma equals to turmaId
        defaultProcessoSelectivoMatriculaShouldBeFound("turmaId.equals=" + turmaId);

        // Get all the processoSelectivoMatriculaList where turma equals to (turmaId + 1)
        defaultProcessoSelectivoMatriculaShouldNotBeFound("turmaId.equals=" + (turmaId + 1));
    }

    @Test
    @Transactional
    void getAllProcessoSelectivoMatriculasByDiscenteIsEqualToSomething() throws Exception {
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);
            discente = DiscenteResourceIT.createEntity(em);
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        em.persist(discente);
        em.flush();
        processoSelectivoMatricula.setDiscente(discente);
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);
        Long discenteId = discente.getId();

        // Get all the processoSelectivoMatriculaList where discente equals to discenteId
        defaultProcessoSelectivoMatriculaShouldBeFound("discenteId.equals=" + discenteId);

        // Get all the processoSelectivoMatriculaList where discente equals to (discenteId + 1)
        defaultProcessoSelectivoMatriculaShouldNotBeFound("discenteId.equals=" + (discenteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessoSelectivoMatriculaShouldBeFound(String filter) throws Exception {
        restProcessoSelectivoMatriculaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processoSelectivoMatricula.getId().intValue())))
            .andExpect(jsonPath("$.[*].localTeste").value(hasItem(DEFAULT_LOCAL_TESTE)))
            .andExpect(jsonPath("$.[*].dataTeste").value(hasItem(sameInstant(DEFAULT_DATA_TESTE))))
            .andExpect(jsonPath("$.[*].notaTeste").value(hasItem(DEFAULT_NOTA_TESTE.doubleValue())))
            .andExpect(jsonPath("$.[*].isAdmitido").value(hasItem(DEFAULT_IS_ADMITIDO.booleanValue())));

        // Check, that the count call also returns 1
        restProcessoSelectivoMatriculaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessoSelectivoMatriculaShouldNotBeFound(String filter) throws Exception {
        restProcessoSelectivoMatriculaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessoSelectivoMatriculaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProcessoSelectivoMatricula() throws Exception {
        // Get the processoSelectivoMatricula
        restProcessoSelectivoMatriculaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProcessoSelectivoMatricula() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        int databaseSizeBeforeUpdate = processoSelectivoMatriculaRepository.findAll().size();

        // Update the processoSelectivoMatricula
        ProcessoSelectivoMatricula updatedProcessoSelectivoMatricula = processoSelectivoMatriculaRepository
            .findById(processoSelectivoMatricula.getId())
            .get();
        // Disconnect from session so that the updates on updatedProcessoSelectivoMatricula are not directly saved in db
        em.detach(updatedProcessoSelectivoMatricula);
        updatedProcessoSelectivoMatricula
            .localTeste(UPDATED_LOCAL_TESTE)
            .dataTeste(UPDATED_DATA_TESTE)
            .notaTeste(UPDATED_NOTA_TESTE)
            .isAdmitido(UPDATED_IS_ADMITIDO);
        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO = processoSelectivoMatriculaMapper.toDto(
            updatedProcessoSelectivoMatricula
        );

        restProcessoSelectivoMatriculaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processoSelectivoMatriculaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processoSelectivoMatriculaDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProcessoSelectivoMatricula in the database
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeUpdate);
        ProcessoSelectivoMatricula testProcessoSelectivoMatricula = processoSelectivoMatriculaList.get(
            processoSelectivoMatriculaList.size() - 1
        );
        assertThat(testProcessoSelectivoMatricula.getLocalTeste()).isEqualTo(UPDATED_LOCAL_TESTE);
        assertThat(testProcessoSelectivoMatricula.getDataTeste()).isEqualTo(UPDATED_DATA_TESTE);
        assertThat(testProcessoSelectivoMatricula.getNotaTeste()).isEqualTo(UPDATED_NOTA_TESTE);
        assertThat(testProcessoSelectivoMatricula.getIsAdmitido()).isEqualTo(UPDATED_IS_ADMITIDO);
    }

    @Test
    @Transactional
    void putNonExistingProcessoSelectivoMatricula() throws Exception {
        int databaseSizeBeforeUpdate = processoSelectivoMatriculaRepository.findAll().size();
        processoSelectivoMatricula.setId(count.incrementAndGet());

        // Create the ProcessoSelectivoMatricula
        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO = processoSelectivoMatriculaMapper.toDto(processoSelectivoMatricula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoSelectivoMatriculaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processoSelectivoMatriculaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processoSelectivoMatriculaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessoSelectivoMatricula in the database
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcessoSelectivoMatricula() throws Exception {
        int databaseSizeBeforeUpdate = processoSelectivoMatriculaRepository.findAll().size();
        processoSelectivoMatricula.setId(count.incrementAndGet());

        // Create the ProcessoSelectivoMatricula
        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO = processoSelectivoMatriculaMapper.toDto(processoSelectivoMatricula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoSelectivoMatriculaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processoSelectivoMatriculaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessoSelectivoMatricula in the database
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcessoSelectivoMatricula() throws Exception {
        int databaseSizeBeforeUpdate = processoSelectivoMatriculaRepository.findAll().size();
        processoSelectivoMatricula.setId(count.incrementAndGet());

        // Create the ProcessoSelectivoMatricula
        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO = processoSelectivoMatriculaMapper.toDto(processoSelectivoMatricula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoSelectivoMatriculaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processoSelectivoMatriculaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcessoSelectivoMatricula in the database
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcessoSelectivoMatriculaWithPatch() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        int databaseSizeBeforeUpdate = processoSelectivoMatriculaRepository.findAll().size();

        // Update the processoSelectivoMatricula using partial update
        ProcessoSelectivoMatricula partialUpdatedProcessoSelectivoMatricula = new ProcessoSelectivoMatricula();
        partialUpdatedProcessoSelectivoMatricula.setId(processoSelectivoMatricula.getId());

        partialUpdatedProcessoSelectivoMatricula
            .localTeste(UPDATED_LOCAL_TESTE)
            .dataTeste(UPDATED_DATA_TESTE)
            .notaTeste(UPDATED_NOTA_TESTE)
            .isAdmitido(UPDATED_IS_ADMITIDO);

        restProcessoSelectivoMatriculaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessoSelectivoMatricula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessoSelectivoMatricula))
            )
            .andExpect(status().isOk());

        // Validate the ProcessoSelectivoMatricula in the database
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeUpdate);
        ProcessoSelectivoMatricula testProcessoSelectivoMatricula = processoSelectivoMatriculaList.get(
            processoSelectivoMatriculaList.size() - 1
        );
        assertThat(testProcessoSelectivoMatricula.getLocalTeste()).isEqualTo(UPDATED_LOCAL_TESTE);
        assertThat(testProcessoSelectivoMatricula.getDataTeste()).isEqualTo(UPDATED_DATA_TESTE);
        assertThat(testProcessoSelectivoMatricula.getNotaTeste()).isEqualTo(UPDATED_NOTA_TESTE);
        assertThat(testProcessoSelectivoMatricula.getIsAdmitido()).isEqualTo(UPDATED_IS_ADMITIDO);
    }

    @Test
    @Transactional
    void fullUpdateProcessoSelectivoMatriculaWithPatch() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        int databaseSizeBeforeUpdate = processoSelectivoMatriculaRepository.findAll().size();

        // Update the processoSelectivoMatricula using partial update
        ProcessoSelectivoMatricula partialUpdatedProcessoSelectivoMatricula = new ProcessoSelectivoMatricula();
        partialUpdatedProcessoSelectivoMatricula.setId(processoSelectivoMatricula.getId());

        partialUpdatedProcessoSelectivoMatricula
            .localTeste(UPDATED_LOCAL_TESTE)
            .dataTeste(UPDATED_DATA_TESTE)
            .notaTeste(UPDATED_NOTA_TESTE)
            .isAdmitido(UPDATED_IS_ADMITIDO);

        restProcessoSelectivoMatriculaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessoSelectivoMatricula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessoSelectivoMatricula))
            )
            .andExpect(status().isOk());

        // Validate the ProcessoSelectivoMatricula in the database
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeUpdate);
        ProcessoSelectivoMatricula testProcessoSelectivoMatricula = processoSelectivoMatriculaList.get(
            processoSelectivoMatriculaList.size() - 1
        );
        assertThat(testProcessoSelectivoMatricula.getLocalTeste()).isEqualTo(UPDATED_LOCAL_TESTE);
        assertThat(testProcessoSelectivoMatricula.getDataTeste()).isEqualTo(UPDATED_DATA_TESTE);
        assertThat(testProcessoSelectivoMatricula.getNotaTeste()).isEqualTo(UPDATED_NOTA_TESTE);
        assertThat(testProcessoSelectivoMatricula.getIsAdmitido()).isEqualTo(UPDATED_IS_ADMITIDO);
    }

    @Test
    @Transactional
    void patchNonExistingProcessoSelectivoMatricula() throws Exception {
        int databaseSizeBeforeUpdate = processoSelectivoMatriculaRepository.findAll().size();
        processoSelectivoMatricula.setId(count.incrementAndGet());

        // Create the ProcessoSelectivoMatricula
        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO = processoSelectivoMatriculaMapper.toDto(processoSelectivoMatricula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoSelectivoMatriculaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, processoSelectivoMatriculaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processoSelectivoMatriculaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessoSelectivoMatricula in the database
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcessoSelectivoMatricula() throws Exception {
        int databaseSizeBeforeUpdate = processoSelectivoMatriculaRepository.findAll().size();
        processoSelectivoMatricula.setId(count.incrementAndGet());

        // Create the ProcessoSelectivoMatricula
        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO = processoSelectivoMatriculaMapper.toDto(processoSelectivoMatricula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoSelectivoMatriculaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processoSelectivoMatriculaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessoSelectivoMatricula in the database
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcessoSelectivoMatricula() throws Exception {
        int databaseSizeBeforeUpdate = processoSelectivoMatriculaRepository.findAll().size();
        processoSelectivoMatricula.setId(count.incrementAndGet());

        // Create the ProcessoSelectivoMatricula
        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO = processoSelectivoMatriculaMapper.toDto(processoSelectivoMatricula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoSelectivoMatriculaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processoSelectivoMatriculaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcessoSelectivoMatricula in the database
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcessoSelectivoMatricula() throws Exception {
        // Initialize the database
        processoSelectivoMatriculaRepository.saveAndFlush(processoSelectivoMatricula);

        int databaseSizeBeforeDelete = processoSelectivoMatriculaRepository.findAll().size();

        // Delete the processoSelectivoMatricula
        restProcessoSelectivoMatriculaMockMvc
            .perform(delete(ENTITY_API_URL_ID, processoSelectivoMatricula.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessoSelectivoMatricula> processoSelectivoMatriculaList = processoSelectivoMatriculaRepository.findAll();
        assertThat(processoSelectivoMatriculaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
