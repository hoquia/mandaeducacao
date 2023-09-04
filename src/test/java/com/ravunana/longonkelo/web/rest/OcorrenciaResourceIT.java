package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.CategoriaOcorrencia;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.Licao;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.Ocorrencia;
import com.ravunana.longonkelo.domain.Ocorrencia;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.OcorrenciaRepository;
import com.ravunana.longonkelo.service.OcorrenciaService;
import com.ravunana.longonkelo.service.criteria.OcorrenciaCriteria;
import com.ravunana.longonkelo.service.dto.OcorrenciaDTO;
import com.ravunana.longonkelo.service.mapper.OcorrenciaMapper;
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
 * Integration tests for the {@link OcorrenciaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OcorrenciaResourceIT {

    private static final String DEFAULT_UNIQUE_OCORRENCIA = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_OCORRENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_EVIDENCIA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EVIDENCIA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EVIDENCIA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EVIDENCIA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/ocorrencias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Mock
    private OcorrenciaRepository ocorrenciaRepositoryMock;

    @Autowired
    private OcorrenciaMapper ocorrenciaMapper;

    @Mock
    private OcorrenciaService ocorrenciaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOcorrenciaMockMvc;

    private Ocorrencia ocorrencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ocorrencia createEntity(EntityManager em) {
        Ocorrencia ocorrencia = new Ocorrencia()
            .uniqueOcorrencia(DEFAULT_UNIQUE_OCORRENCIA)
            .descricao(DEFAULT_DESCRICAO)
            .evidencia(DEFAULT_EVIDENCIA)
            .evidenciaContentType(DEFAULT_EVIDENCIA_CONTENT_TYPE)
            .hash(DEFAULT_HASH)
            .timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        ocorrencia.setDocente(docente);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        ocorrencia.setMatricula(matricula);
        // Add required entity
        CategoriaOcorrencia categoriaOcorrencia;
        if (TestUtil.findAll(em, CategoriaOcorrencia.class).isEmpty()) {
            categoriaOcorrencia = CategoriaOcorrenciaResourceIT.createEntity(em);
            em.persist(categoriaOcorrencia);
            em.flush();
        } else {
            categoriaOcorrencia = TestUtil.findAll(em, CategoriaOcorrencia.class).get(0);
        }
        ocorrencia.setEstado(categoriaOcorrencia);
        // Add required entity
        Licao licao;
        if (TestUtil.findAll(em, Licao.class).isEmpty()) {
            licao = LicaoResourceIT.createEntity(em);
            em.persist(licao);
            em.flush();
        } else {
            licao = TestUtil.findAll(em, Licao.class).get(0);
        }
        ocorrencia.setLicao(licao);
        return ocorrencia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ocorrencia createUpdatedEntity(EntityManager em) {
        Ocorrencia ocorrencia = new Ocorrencia()
            .uniqueOcorrencia(UPDATED_UNIQUE_OCORRENCIA)
            .descricao(UPDATED_DESCRICAO)
            .evidencia(UPDATED_EVIDENCIA)
            .evidenciaContentType(UPDATED_EVIDENCIA_CONTENT_TYPE)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createUpdatedEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        ocorrencia.setDocente(docente);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createUpdatedEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        ocorrencia.setMatricula(matricula);
        // Add required entity
        CategoriaOcorrencia categoriaOcorrencia;
        if (TestUtil.findAll(em, CategoriaOcorrencia.class).isEmpty()) {
            categoriaOcorrencia = CategoriaOcorrenciaResourceIT.createUpdatedEntity(em);
            em.persist(categoriaOcorrencia);
            em.flush();
        } else {
            categoriaOcorrencia = TestUtil.findAll(em, CategoriaOcorrencia.class).get(0);
        }
        ocorrencia.setEstado(categoriaOcorrencia);
        // Add required entity
        Licao licao;
        if (TestUtil.findAll(em, Licao.class).isEmpty()) {
            licao = LicaoResourceIT.createUpdatedEntity(em);
            em.persist(licao);
            em.flush();
        } else {
            licao = TestUtil.findAll(em, Licao.class).get(0);
        }
        ocorrencia.setLicao(licao);
        return ocorrencia;
    }

    @BeforeEach
    public void initTest() {
        ocorrencia = createEntity(em);
    }

    @Test
    @Transactional
    void createOcorrencia() throws Exception {
        int databaseSizeBeforeCreate = ocorrenciaRepository.findAll().size();
        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);
        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Ocorrencia testOcorrencia = ocorrenciaList.get(ocorrenciaList.size() - 1);
        assertThat(testOcorrencia.getUniqueOcorrencia()).isEqualTo(DEFAULT_UNIQUE_OCORRENCIA);
        assertThat(testOcorrencia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testOcorrencia.getEvidencia()).isEqualTo(DEFAULT_EVIDENCIA);
        assertThat(testOcorrencia.getEvidenciaContentType()).isEqualTo(DEFAULT_EVIDENCIA_CONTENT_TYPE);
        assertThat(testOcorrencia.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testOcorrencia.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createOcorrenciaWithExistingId() throws Exception {
        // Create the Ocorrencia with an existing ID
        ocorrencia.setId(1L);
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        int databaseSizeBeforeCreate = ocorrenciaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = ocorrenciaRepository.findAll().size();
        // set the field null
        ocorrencia.setTimestamp(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOcorrencias() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList
        restOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocorrencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].uniqueOcorrencia").value(hasItem(DEFAULT_UNIQUE_OCORRENCIA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].evidenciaContentType").value(hasItem(DEFAULT_EVIDENCIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].evidencia").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVIDENCIA))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOcorrenciasWithEagerRelationshipsIsEnabled() throws Exception {
        when(ocorrenciaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOcorrenciaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ocorrenciaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOcorrenciasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ocorrenciaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOcorrenciaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(ocorrenciaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOcorrencia() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get the ocorrencia
        restOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL_ID, ocorrencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ocorrencia.getId().intValue()))
            .andExpect(jsonPath("$.uniqueOcorrencia").value(DEFAULT_UNIQUE_OCORRENCIA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.evidenciaContentType").value(DEFAULT_EVIDENCIA_CONTENT_TYPE))
            .andExpect(jsonPath("$.evidencia").value(Base64Utils.encodeToString(DEFAULT_EVIDENCIA)))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getOcorrenciasByIdFiltering() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        Long id = ocorrencia.getId();

        defaultOcorrenciaShouldBeFound("id.equals=" + id);
        defaultOcorrenciaShouldNotBeFound("id.notEquals=" + id);

        defaultOcorrenciaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOcorrenciaShouldNotBeFound("id.greaterThan=" + id);

        defaultOcorrenciaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOcorrenciaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByUniqueOcorrenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where uniqueOcorrencia equals to DEFAULT_UNIQUE_OCORRENCIA
        defaultOcorrenciaShouldBeFound("uniqueOcorrencia.equals=" + DEFAULT_UNIQUE_OCORRENCIA);

        // Get all the ocorrenciaList where uniqueOcorrencia equals to UPDATED_UNIQUE_OCORRENCIA
        defaultOcorrenciaShouldNotBeFound("uniqueOcorrencia.equals=" + UPDATED_UNIQUE_OCORRENCIA);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByUniqueOcorrenciaIsInShouldWork() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where uniqueOcorrencia in DEFAULT_UNIQUE_OCORRENCIA or UPDATED_UNIQUE_OCORRENCIA
        defaultOcorrenciaShouldBeFound("uniqueOcorrencia.in=" + DEFAULT_UNIQUE_OCORRENCIA + "," + UPDATED_UNIQUE_OCORRENCIA);

        // Get all the ocorrenciaList where uniqueOcorrencia equals to UPDATED_UNIQUE_OCORRENCIA
        defaultOcorrenciaShouldNotBeFound("uniqueOcorrencia.in=" + UPDATED_UNIQUE_OCORRENCIA);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByUniqueOcorrenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where uniqueOcorrencia is not null
        defaultOcorrenciaShouldBeFound("uniqueOcorrencia.specified=true");

        // Get all the ocorrenciaList where uniqueOcorrencia is null
        defaultOcorrenciaShouldNotBeFound("uniqueOcorrencia.specified=false");
    }

    @Test
    @Transactional
    void getAllOcorrenciasByUniqueOcorrenciaContainsSomething() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where uniqueOcorrencia contains DEFAULT_UNIQUE_OCORRENCIA
        defaultOcorrenciaShouldBeFound("uniqueOcorrencia.contains=" + DEFAULT_UNIQUE_OCORRENCIA);

        // Get all the ocorrenciaList where uniqueOcorrencia contains UPDATED_UNIQUE_OCORRENCIA
        defaultOcorrenciaShouldNotBeFound("uniqueOcorrencia.contains=" + UPDATED_UNIQUE_OCORRENCIA);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByUniqueOcorrenciaNotContainsSomething() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where uniqueOcorrencia does not contain DEFAULT_UNIQUE_OCORRENCIA
        defaultOcorrenciaShouldNotBeFound("uniqueOcorrencia.doesNotContain=" + DEFAULT_UNIQUE_OCORRENCIA);

        // Get all the ocorrenciaList where uniqueOcorrencia does not contain UPDATED_UNIQUE_OCORRENCIA
        defaultOcorrenciaShouldBeFound("uniqueOcorrencia.doesNotContain=" + UPDATED_UNIQUE_OCORRENCIA);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where hash equals to DEFAULT_HASH
        defaultOcorrenciaShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the ocorrenciaList where hash equals to UPDATED_HASH
        defaultOcorrenciaShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByHashIsInShouldWork() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultOcorrenciaShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the ocorrenciaList where hash equals to UPDATED_HASH
        defaultOcorrenciaShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where hash is not null
        defaultOcorrenciaShouldBeFound("hash.specified=true");

        // Get all the ocorrenciaList where hash is null
        defaultOcorrenciaShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllOcorrenciasByHashContainsSomething() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where hash contains DEFAULT_HASH
        defaultOcorrenciaShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the ocorrenciaList where hash contains UPDATED_HASH
        defaultOcorrenciaShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByHashNotContainsSomething() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where hash does not contain DEFAULT_HASH
        defaultOcorrenciaShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the ocorrenciaList where hash does not contain UPDATED_HASH
        defaultOcorrenciaShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where timestamp equals to DEFAULT_TIMESTAMP
        defaultOcorrenciaShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the ocorrenciaList where timestamp equals to UPDATED_TIMESTAMP
        defaultOcorrenciaShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultOcorrenciaShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the ocorrenciaList where timestamp equals to UPDATED_TIMESTAMP
        defaultOcorrenciaShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where timestamp is not null
        defaultOcorrenciaShouldBeFound("timestamp.specified=true");

        // Get all the ocorrenciaList where timestamp is null
        defaultOcorrenciaShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllOcorrenciasByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultOcorrenciaShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the ocorrenciaList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultOcorrenciaShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultOcorrenciaShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the ocorrenciaList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultOcorrenciaShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where timestamp is less than DEFAULT_TIMESTAMP
        defaultOcorrenciaShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the ocorrenciaList where timestamp is less than UPDATED_TIMESTAMP
        defaultOcorrenciaShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultOcorrenciaShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the ocorrenciaList where timestamp is greater than SMALLER_TIMESTAMP
        defaultOcorrenciaShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllOcorrenciasByOcorrenciaIsEqualToSomething() throws Exception {
        Ocorrencia ocorrencia;
        if (TestUtil.findAll(em, Ocorrencia.class).isEmpty()) {
            ocorrenciaRepository.saveAndFlush(ocorrencia);
            ocorrencia = OcorrenciaResourceIT.createEntity(em);
        } else {
            ocorrencia = TestUtil.findAll(em, Ocorrencia.class).get(0);
        }
        em.persist(ocorrencia);
        em.flush();
        ocorrencia.addOcorrencia(ocorrencia);
        ocorrenciaRepository.saveAndFlush(ocorrencia);
        Long ocorrenciaId = ocorrencia.getId();

        // Get all the ocorrenciaList where ocorrencia equals to ocorrenciaId
        defaultOcorrenciaShouldBeFound("ocorrenciaId.equals=" + ocorrenciaId);

        // Get all the ocorrenciaList where ocorrencia equals to (ocorrenciaId + 1)
        defaultOcorrenciaShouldNotBeFound("ocorrenciaId.equals=" + (ocorrenciaId + 1));
    }

    @Test
    @Transactional
    void getAllOcorrenciasByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            ocorrenciaRepository.saveAndFlush(ocorrencia);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        ocorrencia.addAnoLectivo(anoLectivo);
        ocorrenciaRepository.saveAndFlush(ocorrencia);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the ocorrenciaList where anoLectivo equals to anoLectivoId
        defaultOcorrenciaShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the ocorrenciaList where anoLectivo equals to (anoLectivoId + 1)
        defaultOcorrenciaShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllOcorrenciasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            ocorrenciaRepository.saveAndFlush(ocorrencia);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        ocorrencia.setUtilizador(utilizador);
        ocorrenciaRepository.saveAndFlush(ocorrencia);
        Long utilizadorId = utilizador.getId();

        // Get all the ocorrenciaList where utilizador equals to utilizadorId
        defaultOcorrenciaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the ocorrenciaList where utilizador equals to (utilizadorId + 1)
        defaultOcorrenciaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllOcorrenciasByReferenciaIsEqualToSomething() throws Exception {
        Ocorrencia referencia;
        if (TestUtil.findAll(em, Ocorrencia.class).isEmpty()) {
            ocorrenciaRepository.saveAndFlush(ocorrencia);
            referencia = OcorrenciaResourceIT.createEntity(em);
        } else {
            referencia = TestUtil.findAll(em, Ocorrencia.class).get(0);
        }
        em.persist(referencia);
        em.flush();
        ocorrencia.setReferencia(referencia);
        ocorrenciaRepository.saveAndFlush(ocorrencia);
        Long referenciaId = referencia.getId();

        // Get all the ocorrenciaList where referencia equals to referenciaId
        defaultOcorrenciaShouldBeFound("referenciaId.equals=" + referenciaId);

        // Get all the ocorrenciaList where referencia equals to (referenciaId + 1)
        defaultOcorrenciaShouldNotBeFound("referenciaId.equals=" + (referenciaId + 1));
    }

    @Test
    @Transactional
    void getAllOcorrenciasByDocenteIsEqualToSomething() throws Exception {
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            ocorrenciaRepository.saveAndFlush(ocorrencia);
            docente = DocenteResourceIT.createEntity(em);
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(docente);
        em.flush();
        ocorrencia.setDocente(docente);
        ocorrenciaRepository.saveAndFlush(ocorrencia);
        Long docenteId = docente.getId();

        // Get all the ocorrenciaList where docente equals to docenteId
        defaultOcorrenciaShouldBeFound("docenteId.equals=" + docenteId);

        // Get all the ocorrenciaList where docente equals to (docenteId + 1)
        defaultOcorrenciaShouldNotBeFound("docenteId.equals=" + (docenteId + 1));
    }

    @Test
    @Transactional
    void getAllOcorrenciasByMatriculaIsEqualToSomething() throws Exception {
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            ocorrenciaRepository.saveAndFlush(ocorrencia);
            matricula = MatriculaResourceIT.createEntity(em);
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(matricula);
        em.flush();
        ocorrencia.setMatricula(matricula);
        ocorrenciaRepository.saveAndFlush(ocorrencia);
        Long matriculaId = matricula.getId();

        // Get all the ocorrenciaList where matricula equals to matriculaId
        defaultOcorrenciaShouldBeFound("matriculaId.equals=" + matriculaId);

        // Get all the ocorrenciaList where matricula equals to (matriculaId + 1)
        defaultOcorrenciaShouldNotBeFound("matriculaId.equals=" + (matriculaId + 1));
    }

    @Test
    @Transactional
    void getAllOcorrenciasByEstadoIsEqualToSomething() throws Exception {
        CategoriaOcorrencia estado;
        if (TestUtil.findAll(em, CategoriaOcorrencia.class).isEmpty()) {
            ocorrenciaRepository.saveAndFlush(ocorrencia);
            estado = CategoriaOcorrenciaResourceIT.createEntity(em);
        } else {
            estado = TestUtil.findAll(em, CategoriaOcorrencia.class).get(0);
        }
        em.persist(estado);
        em.flush();
        ocorrencia.setEstado(estado);
        ocorrenciaRepository.saveAndFlush(ocorrencia);
        Long estadoId = estado.getId();

        // Get all the ocorrenciaList where estado equals to estadoId
        defaultOcorrenciaShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the ocorrenciaList where estado equals to (estadoId + 1)
        defaultOcorrenciaShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }

    @Test
    @Transactional
    void getAllOcorrenciasByLicaoIsEqualToSomething() throws Exception {
        Licao licao;
        if (TestUtil.findAll(em, Licao.class).isEmpty()) {
            ocorrenciaRepository.saveAndFlush(ocorrencia);
            licao = LicaoResourceIT.createEntity(em);
        } else {
            licao = TestUtil.findAll(em, Licao.class).get(0);
        }
        em.persist(licao);
        em.flush();
        ocorrencia.setLicao(licao);
        ocorrenciaRepository.saveAndFlush(ocorrencia);
        Long licaoId = licao.getId();

        // Get all the ocorrenciaList where licao equals to licaoId
        defaultOcorrenciaShouldBeFound("licaoId.equals=" + licaoId);

        // Get all the ocorrenciaList where licao equals to (licaoId + 1)
        defaultOcorrenciaShouldNotBeFound("licaoId.equals=" + (licaoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOcorrenciaShouldBeFound(String filter) throws Exception {
        restOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocorrencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].uniqueOcorrencia").value(hasItem(DEFAULT_UNIQUE_OCORRENCIA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].evidenciaContentType").value(hasItem(DEFAULT_EVIDENCIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].evidencia").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVIDENCIA))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOcorrenciaShouldNotBeFound(String filter) throws Exception {
        restOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOcorrencia() throws Exception {
        // Get the ocorrencia
        restOcorrenciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOcorrencia() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        int databaseSizeBeforeUpdate = ocorrenciaRepository.findAll().size();

        // Update the ocorrencia
        Ocorrencia updatedOcorrencia = ocorrenciaRepository.findById(ocorrencia.getId()).get();
        // Disconnect from session so that the updates on updatedOcorrencia are not directly saved in db
        em.detach(updatedOcorrencia);
        updatedOcorrencia
            .uniqueOcorrencia(UPDATED_UNIQUE_OCORRENCIA)
            .descricao(UPDATED_DESCRICAO)
            .evidencia(UPDATED_EVIDENCIA)
            .evidenciaContentType(UPDATED_EVIDENCIA_CONTENT_TYPE)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(updatedOcorrencia);

        restOcorrenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ocorrenciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeUpdate);
        Ocorrencia testOcorrencia = ocorrenciaList.get(ocorrenciaList.size() - 1);
        assertThat(testOcorrencia.getUniqueOcorrencia()).isEqualTo(UPDATED_UNIQUE_OCORRENCIA);
        assertThat(testOcorrencia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testOcorrencia.getEvidencia()).isEqualTo(UPDATED_EVIDENCIA);
        assertThat(testOcorrencia.getEvidenciaContentType()).isEqualTo(UPDATED_EVIDENCIA_CONTENT_TYPE);
        assertThat(testOcorrencia.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testOcorrencia.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = ocorrenciaRepository.findAll().size();
        ocorrencia.setId(count.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ocorrenciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = ocorrenciaRepository.findAll().size();
        ocorrencia.setId(count.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = ocorrenciaRepository.findAll().size();
        ocorrencia.setId(count.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOcorrenciaWithPatch() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        int databaseSizeBeforeUpdate = ocorrenciaRepository.findAll().size();

        // Update the ocorrencia using partial update
        Ocorrencia partialUpdatedOcorrencia = new Ocorrencia();
        partialUpdatedOcorrencia.setId(ocorrencia.getId());

        partialUpdatedOcorrencia.evidencia(UPDATED_EVIDENCIA).evidenciaContentType(UPDATED_EVIDENCIA_CONTENT_TYPE);

        restOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOcorrencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOcorrencia))
            )
            .andExpect(status().isOk());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeUpdate);
        Ocorrencia testOcorrencia = ocorrenciaList.get(ocorrenciaList.size() - 1);
        assertThat(testOcorrencia.getUniqueOcorrencia()).isEqualTo(DEFAULT_UNIQUE_OCORRENCIA);
        assertThat(testOcorrencia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testOcorrencia.getEvidencia()).isEqualTo(UPDATED_EVIDENCIA);
        assertThat(testOcorrencia.getEvidenciaContentType()).isEqualTo(UPDATED_EVIDENCIA_CONTENT_TYPE);
        assertThat(testOcorrencia.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testOcorrencia.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateOcorrenciaWithPatch() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        int databaseSizeBeforeUpdate = ocorrenciaRepository.findAll().size();

        // Update the ocorrencia using partial update
        Ocorrencia partialUpdatedOcorrencia = new Ocorrencia();
        partialUpdatedOcorrencia.setId(ocorrencia.getId());

        partialUpdatedOcorrencia
            .uniqueOcorrencia(UPDATED_UNIQUE_OCORRENCIA)
            .descricao(UPDATED_DESCRICAO)
            .evidencia(UPDATED_EVIDENCIA)
            .evidenciaContentType(UPDATED_EVIDENCIA_CONTENT_TYPE)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);

        restOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOcorrencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOcorrencia))
            )
            .andExpect(status().isOk());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeUpdate);
        Ocorrencia testOcorrencia = ocorrenciaList.get(ocorrenciaList.size() - 1);
        assertThat(testOcorrencia.getUniqueOcorrencia()).isEqualTo(UPDATED_UNIQUE_OCORRENCIA);
        assertThat(testOcorrencia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testOcorrencia.getEvidencia()).isEqualTo(UPDATED_EVIDENCIA);
        assertThat(testOcorrencia.getEvidenciaContentType()).isEqualTo(UPDATED_EVIDENCIA_CONTENT_TYPE);
        assertThat(testOcorrencia.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testOcorrencia.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = ocorrenciaRepository.findAll().size();
        ocorrencia.setId(count.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ocorrenciaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = ocorrenciaRepository.findAll().size();
        ocorrencia.setId(count.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = ocorrenciaRepository.findAll().size();
        ocorrencia.setId(count.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOcorrencia() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        int databaseSizeBeforeDelete = ocorrenciaRepository.findAll().size();

        // Delete the ocorrencia
        restOcorrenciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, ocorrencia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
