package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.Curso;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.ResponsavelCurso;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.ResponsavelCursoRepository;
import com.ravunana.longonkelo.service.ResponsavelCursoService;
import com.ravunana.longonkelo.service.criteria.ResponsavelCursoCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelCursoDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelCursoMapper;
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
 * Integration tests for the {@link ResponsavelCursoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ResponsavelCursoResourceIT {

    private static final LocalDate DEFAULT_DE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_ATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/responsavel-cursos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResponsavelCursoRepository responsavelCursoRepository;

    @Mock
    private ResponsavelCursoRepository responsavelCursoRepositoryMock;

    @Autowired
    private ResponsavelCursoMapper responsavelCursoMapper;

    @Mock
    private ResponsavelCursoService responsavelCursoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsavelCursoMockMvc;

    private ResponsavelCurso responsavelCurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelCurso createEntity(EntityManager em) {
        ResponsavelCurso responsavelCurso = new ResponsavelCurso()
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .descricao(DEFAULT_DESCRICAO)
            .timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            curso = CursoResourceIT.createEntity(em);
            em.persist(curso);
            em.flush();
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        responsavelCurso.setCurso(curso);
        return responsavelCurso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelCurso createUpdatedEntity(EntityManager em) {
        ResponsavelCurso responsavelCurso = new ResponsavelCurso()
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .descricao(UPDATED_DESCRICAO)
            .timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            curso = CursoResourceIT.createUpdatedEntity(em);
            em.persist(curso);
            em.flush();
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        responsavelCurso.setCurso(curso);
        return responsavelCurso;
    }

    @BeforeEach
    public void initTest() {
        responsavelCurso = createEntity(em);
    }

    @Test
    @Transactional
    void createResponsavelCurso() throws Exception {
        int databaseSizeBeforeCreate = responsavelCursoRepository.findAll().size();
        // Create the ResponsavelCurso
        ResponsavelCursoDTO responsavelCursoDTO = responsavelCursoMapper.toDto(responsavelCurso);
        restResponsavelCursoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelCursoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResponsavelCurso in the database
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeCreate + 1);
        ResponsavelCurso testResponsavelCurso = responsavelCursoList.get(responsavelCursoList.size() - 1);
        assertThat(testResponsavelCurso.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testResponsavelCurso.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testResponsavelCurso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testResponsavelCurso.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createResponsavelCursoWithExistingId() throws Exception {
        // Create the ResponsavelCurso with an existing ID
        responsavelCurso.setId(1L);
        ResponsavelCursoDTO responsavelCursoDTO = responsavelCursoMapper.toDto(responsavelCurso);

        int databaseSizeBeforeCreate = responsavelCursoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelCursoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelCurso in the database
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDeIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelCursoRepository.findAll().size();
        // set the field null
        responsavelCurso.setDe(null);

        // Create the ResponsavelCurso, which fails.
        ResponsavelCursoDTO responsavelCursoDTO = responsavelCursoMapper.toDto(responsavelCurso);

        restResponsavelCursoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelCursoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAteIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelCursoRepository.findAll().size();
        // set the field null
        responsavelCurso.setAte(null);

        // Create the ResponsavelCurso, which fails.
        ResponsavelCursoDTO responsavelCursoDTO = responsavelCursoMapper.toDto(responsavelCurso);

        restResponsavelCursoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelCursoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResponsavelCursos() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList
        restResponsavelCursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelCurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelCursosWithEagerRelationshipsIsEnabled() throws Exception {
        when(responsavelCursoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelCursoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(responsavelCursoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelCursosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(responsavelCursoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelCursoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(responsavelCursoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getResponsavelCurso() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get the responsavelCurso
        restResponsavelCursoMockMvc
            .perform(get(ENTITY_API_URL_ID, responsavelCurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsavelCurso.getId().intValue()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.ate").value(DEFAULT_ATE.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getResponsavelCursosByIdFiltering() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        Long id = responsavelCurso.getId();

        defaultResponsavelCursoShouldBeFound("id.equals=" + id);
        defaultResponsavelCursoShouldNotBeFound("id.notEquals=" + id);

        defaultResponsavelCursoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResponsavelCursoShouldNotBeFound("id.greaterThan=" + id);

        defaultResponsavelCursoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResponsavelCursoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByDeIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where de equals to DEFAULT_DE
        defaultResponsavelCursoShouldBeFound("de.equals=" + DEFAULT_DE);

        // Get all the responsavelCursoList where de equals to UPDATED_DE
        defaultResponsavelCursoShouldNotBeFound("de.equals=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByDeIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where de in DEFAULT_DE or UPDATED_DE
        defaultResponsavelCursoShouldBeFound("de.in=" + DEFAULT_DE + "," + UPDATED_DE);

        // Get all the responsavelCursoList where de equals to UPDATED_DE
        defaultResponsavelCursoShouldNotBeFound("de.in=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByDeIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where de is not null
        defaultResponsavelCursoShouldBeFound("de.specified=true");

        // Get all the responsavelCursoList where de is null
        defaultResponsavelCursoShouldNotBeFound("de.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByDeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where de is greater than or equal to DEFAULT_DE
        defaultResponsavelCursoShouldBeFound("de.greaterThanOrEqual=" + DEFAULT_DE);

        // Get all the responsavelCursoList where de is greater than or equal to UPDATED_DE
        defaultResponsavelCursoShouldNotBeFound("de.greaterThanOrEqual=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByDeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where de is less than or equal to DEFAULT_DE
        defaultResponsavelCursoShouldBeFound("de.lessThanOrEqual=" + DEFAULT_DE);

        // Get all the responsavelCursoList where de is less than or equal to SMALLER_DE
        defaultResponsavelCursoShouldNotBeFound("de.lessThanOrEqual=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByDeIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where de is less than DEFAULT_DE
        defaultResponsavelCursoShouldNotBeFound("de.lessThan=" + DEFAULT_DE);

        // Get all the responsavelCursoList where de is less than UPDATED_DE
        defaultResponsavelCursoShouldBeFound("de.lessThan=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByDeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where de is greater than DEFAULT_DE
        defaultResponsavelCursoShouldNotBeFound("de.greaterThan=" + DEFAULT_DE);

        // Get all the responsavelCursoList where de is greater than SMALLER_DE
        defaultResponsavelCursoShouldBeFound("de.greaterThan=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByAteIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where ate equals to DEFAULT_ATE
        defaultResponsavelCursoShouldBeFound("ate.equals=" + DEFAULT_ATE);

        // Get all the responsavelCursoList where ate equals to UPDATED_ATE
        defaultResponsavelCursoShouldNotBeFound("ate.equals=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByAteIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where ate in DEFAULT_ATE or UPDATED_ATE
        defaultResponsavelCursoShouldBeFound("ate.in=" + DEFAULT_ATE + "," + UPDATED_ATE);

        // Get all the responsavelCursoList where ate equals to UPDATED_ATE
        defaultResponsavelCursoShouldNotBeFound("ate.in=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByAteIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where ate is not null
        defaultResponsavelCursoShouldBeFound("ate.specified=true");

        // Get all the responsavelCursoList where ate is null
        defaultResponsavelCursoShouldNotBeFound("ate.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByAteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where ate is greater than or equal to DEFAULT_ATE
        defaultResponsavelCursoShouldBeFound("ate.greaterThanOrEqual=" + DEFAULT_ATE);

        // Get all the responsavelCursoList where ate is greater than or equal to UPDATED_ATE
        defaultResponsavelCursoShouldNotBeFound("ate.greaterThanOrEqual=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByAteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where ate is less than or equal to DEFAULT_ATE
        defaultResponsavelCursoShouldBeFound("ate.lessThanOrEqual=" + DEFAULT_ATE);

        // Get all the responsavelCursoList where ate is less than or equal to SMALLER_ATE
        defaultResponsavelCursoShouldNotBeFound("ate.lessThanOrEqual=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByAteIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where ate is less than DEFAULT_ATE
        defaultResponsavelCursoShouldNotBeFound("ate.lessThan=" + DEFAULT_ATE);

        // Get all the responsavelCursoList where ate is less than UPDATED_ATE
        defaultResponsavelCursoShouldBeFound("ate.lessThan=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByAteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where ate is greater than DEFAULT_ATE
        defaultResponsavelCursoShouldNotBeFound("ate.greaterThan=" + DEFAULT_ATE);

        // Get all the responsavelCursoList where ate is greater than SMALLER_ATE
        defaultResponsavelCursoShouldBeFound("ate.greaterThan=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where timestamp equals to DEFAULT_TIMESTAMP
        defaultResponsavelCursoShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelCursoList where timestamp equals to UPDATED_TIMESTAMP
        defaultResponsavelCursoShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultResponsavelCursoShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the responsavelCursoList where timestamp equals to UPDATED_TIMESTAMP
        defaultResponsavelCursoShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where timestamp is not null
        defaultResponsavelCursoShouldBeFound("timestamp.specified=true");

        // Get all the responsavelCursoList where timestamp is null
        defaultResponsavelCursoShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultResponsavelCursoShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelCursoList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultResponsavelCursoShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultResponsavelCursoShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelCursoList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultResponsavelCursoShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where timestamp is less than DEFAULT_TIMESTAMP
        defaultResponsavelCursoShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelCursoList where timestamp is less than UPDATED_TIMESTAMP
        defaultResponsavelCursoShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        // Get all the responsavelCursoList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultResponsavelCursoShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelCursoList where timestamp is greater than SMALLER_TIMESTAMP
        defaultResponsavelCursoShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByResponsavelIsEqualToSomething() throws Exception {
        Docente responsavel;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            responsavelCursoRepository.saveAndFlush(responsavelCurso);
            responsavel = DocenteResourceIT.createEntity(em);
        } else {
            responsavel = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(responsavel);
        em.flush();
        responsavelCurso.addResponsavel(responsavel);
        responsavelCursoRepository.saveAndFlush(responsavelCurso);
        Long responsavelId = responsavel.getId();

        // Get all the responsavelCursoList where responsavel equals to responsavelId
        defaultResponsavelCursoShouldBeFound("responsavelId.equals=" + responsavelId);

        // Get all the responsavelCursoList where responsavel equals to (responsavelId + 1)
        defaultResponsavelCursoShouldNotBeFound("responsavelId.equals=" + (responsavelId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            responsavelCursoRepository.saveAndFlush(responsavelCurso);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        responsavelCurso.addAnoLectivo(anoLectivo);
        responsavelCursoRepository.saveAndFlush(responsavelCurso);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the responsavelCursoList where anoLectivo equals to anoLectivoId
        defaultResponsavelCursoShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the responsavelCursoList where anoLectivo equals to (anoLectivoId + 1)
        defaultResponsavelCursoShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            responsavelCursoRepository.saveAndFlush(responsavelCurso);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        responsavelCurso.setUtilizador(utilizador);
        responsavelCursoRepository.saveAndFlush(responsavelCurso);
        Long utilizadorId = utilizador.getId();

        // Get all the responsavelCursoList where utilizador equals to utilizadorId
        defaultResponsavelCursoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the responsavelCursoList where utilizador equals to (utilizadorId + 1)
        defaultResponsavelCursoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelCursosByCursoIsEqualToSomething() throws Exception {
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            responsavelCursoRepository.saveAndFlush(responsavelCurso);
            curso = CursoResourceIT.createEntity(em);
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        em.persist(curso);
        em.flush();
        responsavelCurso.setCurso(curso);
        responsavelCursoRepository.saveAndFlush(responsavelCurso);
        Long cursoId = curso.getId();

        // Get all the responsavelCursoList where curso equals to cursoId
        defaultResponsavelCursoShouldBeFound("cursoId.equals=" + cursoId);

        // Get all the responsavelCursoList where curso equals to (cursoId + 1)
        defaultResponsavelCursoShouldNotBeFound("cursoId.equals=" + (cursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResponsavelCursoShouldBeFound(String filter) throws Exception {
        restResponsavelCursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelCurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restResponsavelCursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResponsavelCursoShouldNotBeFound(String filter) throws Exception {
        restResponsavelCursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResponsavelCursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResponsavelCurso() throws Exception {
        // Get the responsavelCurso
        restResponsavelCursoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResponsavelCurso() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        int databaseSizeBeforeUpdate = responsavelCursoRepository.findAll().size();

        // Update the responsavelCurso
        ResponsavelCurso updatedResponsavelCurso = responsavelCursoRepository.findById(responsavelCurso.getId()).get();
        // Disconnect from session so that the updates on updatedResponsavelCurso are not directly saved in db
        em.detach(updatedResponsavelCurso);
        updatedResponsavelCurso.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);
        ResponsavelCursoDTO responsavelCursoDTO = responsavelCursoMapper.toDto(updatedResponsavelCurso);

        restResponsavelCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelCursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelCursoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelCurso in the database
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelCurso testResponsavelCurso = responsavelCursoList.get(responsavelCursoList.size() - 1);
        assertThat(testResponsavelCurso.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelCurso.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelCurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelCurso.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingResponsavelCurso() throws Exception {
        int databaseSizeBeforeUpdate = responsavelCursoRepository.findAll().size();
        responsavelCurso.setId(count.incrementAndGet());

        // Create the ResponsavelCurso
        ResponsavelCursoDTO responsavelCursoDTO = responsavelCursoMapper.toDto(responsavelCurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelCursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelCurso in the database
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponsavelCurso() throws Exception {
        int databaseSizeBeforeUpdate = responsavelCursoRepository.findAll().size();
        responsavelCurso.setId(count.incrementAndGet());

        // Create the ResponsavelCurso
        ResponsavelCursoDTO responsavelCursoDTO = responsavelCursoMapper.toDto(responsavelCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelCurso in the database
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponsavelCurso() throws Exception {
        int databaseSizeBeforeUpdate = responsavelCursoRepository.findAll().size();
        responsavelCurso.setId(count.incrementAndGet());

        // Create the ResponsavelCurso
        ResponsavelCursoDTO responsavelCursoDTO = responsavelCursoMapper.toDto(responsavelCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelCursoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelCursoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelCurso in the database
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponsavelCursoWithPatch() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        int databaseSizeBeforeUpdate = responsavelCursoRepository.findAll().size();

        // Update the responsavelCurso using partial update
        ResponsavelCurso partialUpdatedResponsavelCurso = new ResponsavelCurso();
        partialUpdatedResponsavelCurso.setId(responsavelCurso.getId());

        partialUpdatedResponsavelCurso.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO);

        restResponsavelCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelCurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelCurso))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelCurso in the database
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelCurso testResponsavelCurso = responsavelCursoList.get(responsavelCursoList.size() - 1);
        assertThat(testResponsavelCurso.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelCurso.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelCurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelCurso.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateResponsavelCursoWithPatch() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        int databaseSizeBeforeUpdate = responsavelCursoRepository.findAll().size();

        // Update the responsavelCurso using partial update
        ResponsavelCurso partialUpdatedResponsavelCurso = new ResponsavelCurso();
        partialUpdatedResponsavelCurso.setId(responsavelCurso.getId());

        partialUpdatedResponsavelCurso.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);

        restResponsavelCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelCurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelCurso))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelCurso in the database
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelCurso testResponsavelCurso = responsavelCursoList.get(responsavelCursoList.size() - 1);
        assertThat(testResponsavelCurso.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelCurso.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelCurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelCurso.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingResponsavelCurso() throws Exception {
        int databaseSizeBeforeUpdate = responsavelCursoRepository.findAll().size();
        responsavelCurso.setId(count.incrementAndGet());

        // Create the ResponsavelCurso
        ResponsavelCursoDTO responsavelCursoDTO = responsavelCursoMapper.toDto(responsavelCurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responsavelCursoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelCurso in the database
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponsavelCurso() throws Exception {
        int databaseSizeBeforeUpdate = responsavelCursoRepository.findAll().size();
        responsavelCurso.setId(count.incrementAndGet());

        // Create the ResponsavelCurso
        ResponsavelCursoDTO responsavelCursoDTO = responsavelCursoMapper.toDto(responsavelCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelCurso in the database
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponsavelCurso() throws Exception {
        int databaseSizeBeforeUpdate = responsavelCursoRepository.findAll().size();
        responsavelCurso.setId(count.incrementAndGet());

        // Create the ResponsavelCurso
        ResponsavelCursoDTO responsavelCursoDTO = responsavelCursoMapper.toDto(responsavelCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelCursoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelCursoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelCurso in the database
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponsavelCurso() throws Exception {
        // Initialize the database
        responsavelCursoRepository.saveAndFlush(responsavelCurso);

        int databaseSizeBeforeDelete = responsavelCursoRepository.findAll().size();

        // Delete the responsavelCurso
        restResponsavelCursoMockMvc
            .perform(delete(ENTITY_API_URL_ID, responsavelCurso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResponsavelCurso> responsavelCursoList = responsavelCursoRepository.findAll();
        assertThat(responsavelCursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
