package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.ResponsavelTurma;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.ResponsavelTurmaRepository;
import com.ravunana.longonkelo.service.ResponsavelTurmaService;
import com.ravunana.longonkelo.service.criteria.ResponsavelTurmaCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelTurmaDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelTurmaMapper;
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
 * Integration tests for the {@link ResponsavelTurmaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ResponsavelTurmaResourceIT {

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

    private static final String ENTITY_API_URL = "/api/responsavel-turmas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResponsavelTurmaRepository responsavelTurmaRepository;

    @Mock
    private ResponsavelTurmaRepository responsavelTurmaRepositoryMock;

    @Autowired
    private ResponsavelTurmaMapper responsavelTurmaMapper;

    @Mock
    private ResponsavelTurmaService responsavelTurmaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsavelTurmaMockMvc;

    private ResponsavelTurma responsavelTurma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelTurma createEntity(EntityManager em) {
        ResponsavelTurma responsavelTurma = new ResponsavelTurma()
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .descricao(DEFAULT_DESCRICAO)
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
        responsavelTurma.setTurma(turma);
        return responsavelTurma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelTurma createUpdatedEntity(EntityManager em) {
        ResponsavelTurma responsavelTurma = new ResponsavelTurma()
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .descricao(UPDATED_DESCRICAO)
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
        responsavelTurma.setTurma(turma);
        return responsavelTurma;
    }

    @BeforeEach
    public void initTest() {
        responsavelTurma = createEntity(em);
    }

    @Test
    @Transactional
    void createResponsavelTurma() throws Exception {
        int databaseSizeBeforeCreate = responsavelTurmaRepository.findAll().size();
        // Create the ResponsavelTurma
        ResponsavelTurmaDTO responsavelTurmaDTO = responsavelTurmaMapper.toDto(responsavelTurma);
        restResponsavelTurmaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelTurmaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResponsavelTurma in the database
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeCreate + 1);
        ResponsavelTurma testResponsavelTurma = responsavelTurmaList.get(responsavelTurmaList.size() - 1);
        assertThat(testResponsavelTurma.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testResponsavelTurma.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testResponsavelTurma.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testResponsavelTurma.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createResponsavelTurmaWithExistingId() throws Exception {
        // Create the ResponsavelTurma with an existing ID
        responsavelTurma.setId(1L);
        ResponsavelTurmaDTO responsavelTurmaDTO = responsavelTurmaMapper.toDto(responsavelTurma);

        int databaseSizeBeforeCreate = responsavelTurmaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelTurmaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelTurma in the database
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDeIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelTurmaRepository.findAll().size();
        // set the field null
        responsavelTurma.setDe(null);

        // Create the ResponsavelTurma, which fails.
        ResponsavelTurmaDTO responsavelTurmaDTO = responsavelTurmaMapper.toDto(responsavelTurma);

        restResponsavelTurmaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAteIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelTurmaRepository.findAll().size();
        // set the field null
        responsavelTurma.setAte(null);

        // Create the ResponsavelTurma, which fails.
        ResponsavelTurmaDTO responsavelTurmaDTO = responsavelTurmaMapper.toDto(responsavelTurma);

        restResponsavelTurmaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmas() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList
        restResponsavelTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelTurma.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelTurmasWithEagerRelationshipsIsEnabled() throws Exception {
        when(responsavelTurmaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelTurmaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(responsavelTurmaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelTurmasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(responsavelTurmaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelTurmaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(responsavelTurmaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getResponsavelTurma() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get the responsavelTurma
        restResponsavelTurmaMockMvc
            .perform(get(ENTITY_API_URL_ID, responsavelTurma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsavelTurma.getId().intValue()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.ate").value(DEFAULT_ATE.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getResponsavelTurmasByIdFiltering() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        Long id = responsavelTurma.getId();

        defaultResponsavelTurmaShouldBeFound("id.equals=" + id);
        defaultResponsavelTurmaShouldNotBeFound("id.notEquals=" + id);

        defaultResponsavelTurmaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResponsavelTurmaShouldNotBeFound("id.greaterThan=" + id);

        defaultResponsavelTurmaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResponsavelTurmaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByDeIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where de equals to DEFAULT_DE
        defaultResponsavelTurmaShouldBeFound("de.equals=" + DEFAULT_DE);

        // Get all the responsavelTurmaList where de equals to UPDATED_DE
        defaultResponsavelTurmaShouldNotBeFound("de.equals=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByDeIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where de in DEFAULT_DE or UPDATED_DE
        defaultResponsavelTurmaShouldBeFound("de.in=" + DEFAULT_DE + "," + UPDATED_DE);

        // Get all the responsavelTurmaList where de equals to UPDATED_DE
        defaultResponsavelTurmaShouldNotBeFound("de.in=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByDeIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where de is not null
        defaultResponsavelTurmaShouldBeFound("de.specified=true");

        // Get all the responsavelTurmaList where de is null
        defaultResponsavelTurmaShouldNotBeFound("de.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByDeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where de is greater than or equal to DEFAULT_DE
        defaultResponsavelTurmaShouldBeFound("de.greaterThanOrEqual=" + DEFAULT_DE);

        // Get all the responsavelTurmaList where de is greater than or equal to UPDATED_DE
        defaultResponsavelTurmaShouldNotBeFound("de.greaterThanOrEqual=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByDeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where de is less than or equal to DEFAULT_DE
        defaultResponsavelTurmaShouldBeFound("de.lessThanOrEqual=" + DEFAULT_DE);

        // Get all the responsavelTurmaList where de is less than or equal to SMALLER_DE
        defaultResponsavelTurmaShouldNotBeFound("de.lessThanOrEqual=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByDeIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where de is less than DEFAULT_DE
        defaultResponsavelTurmaShouldNotBeFound("de.lessThan=" + DEFAULT_DE);

        // Get all the responsavelTurmaList where de is less than UPDATED_DE
        defaultResponsavelTurmaShouldBeFound("de.lessThan=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByDeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where de is greater than DEFAULT_DE
        defaultResponsavelTurmaShouldNotBeFound("de.greaterThan=" + DEFAULT_DE);

        // Get all the responsavelTurmaList where de is greater than SMALLER_DE
        defaultResponsavelTurmaShouldBeFound("de.greaterThan=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByAteIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where ate equals to DEFAULT_ATE
        defaultResponsavelTurmaShouldBeFound("ate.equals=" + DEFAULT_ATE);

        // Get all the responsavelTurmaList where ate equals to UPDATED_ATE
        defaultResponsavelTurmaShouldNotBeFound("ate.equals=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByAteIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where ate in DEFAULT_ATE or UPDATED_ATE
        defaultResponsavelTurmaShouldBeFound("ate.in=" + DEFAULT_ATE + "," + UPDATED_ATE);

        // Get all the responsavelTurmaList where ate equals to UPDATED_ATE
        defaultResponsavelTurmaShouldNotBeFound("ate.in=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByAteIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where ate is not null
        defaultResponsavelTurmaShouldBeFound("ate.specified=true");

        // Get all the responsavelTurmaList where ate is null
        defaultResponsavelTurmaShouldNotBeFound("ate.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByAteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where ate is greater than or equal to DEFAULT_ATE
        defaultResponsavelTurmaShouldBeFound("ate.greaterThanOrEqual=" + DEFAULT_ATE);

        // Get all the responsavelTurmaList where ate is greater than or equal to UPDATED_ATE
        defaultResponsavelTurmaShouldNotBeFound("ate.greaterThanOrEqual=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByAteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where ate is less than or equal to DEFAULT_ATE
        defaultResponsavelTurmaShouldBeFound("ate.lessThanOrEqual=" + DEFAULT_ATE);

        // Get all the responsavelTurmaList where ate is less than or equal to SMALLER_ATE
        defaultResponsavelTurmaShouldNotBeFound("ate.lessThanOrEqual=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByAteIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where ate is less than DEFAULT_ATE
        defaultResponsavelTurmaShouldNotBeFound("ate.lessThan=" + DEFAULT_ATE);

        // Get all the responsavelTurmaList where ate is less than UPDATED_ATE
        defaultResponsavelTurmaShouldBeFound("ate.lessThan=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByAteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where ate is greater than DEFAULT_ATE
        defaultResponsavelTurmaShouldNotBeFound("ate.greaterThan=" + DEFAULT_ATE);

        // Get all the responsavelTurmaList where ate is greater than SMALLER_ATE
        defaultResponsavelTurmaShouldBeFound("ate.greaterThan=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where timestamp equals to DEFAULT_TIMESTAMP
        defaultResponsavelTurmaShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelTurmaList where timestamp equals to UPDATED_TIMESTAMP
        defaultResponsavelTurmaShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultResponsavelTurmaShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the responsavelTurmaList where timestamp equals to UPDATED_TIMESTAMP
        defaultResponsavelTurmaShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where timestamp is not null
        defaultResponsavelTurmaShouldBeFound("timestamp.specified=true");

        // Get all the responsavelTurmaList where timestamp is null
        defaultResponsavelTurmaShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultResponsavelTurmaShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelTurmaList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultResponsavelTurmaShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultResponsavelTurmaShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelTurmaList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultResponsavelTurmaShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where timestamp is less than DEFAULT_TIMESTAMP
        defaultResponsavelTurmaShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelTurmaList where timestamp is less than UPDATED_TIMESTAMP
        defaultResponsavelTurmaShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        // Get all the responsavelTurmaList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultResponsavelTurmaShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelTurmaList where timestamp is greater than SMALLER_TIMESTAMP
        defaultResponsavelTurmaShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByResponsavelIsEqualToSomething() throws Exception {
        Docente responsavel;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            responsavelTurmaRepository.saveAndFlush(responsavelTurma);
            responsavel = DocenteResourceIT.createEntity(em);
        } else {
            responsavel = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(responsavel);
        em.flush();
        responsavelTurma.addResponsavel(responsavel);
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);
        Long responsavelId = responsavel.getId();

        // Get all the responsavelTurmaList where responsavel equals to responsavelId
        defaultResponsavelTurmaShouldBeFound("responsavelId.equals=" + responsavelId);

        // Get all the responsavelTurmaList where responsavel equals to (responsavelId + 1)
        defaultResponsavelTurmaShouldNotBeFound("responsavelId.equals=" + (responsavelId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            responsavelTurmaRepository.saveAndFlush(responsavelTurma);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        responsavelTurma.addAnoLectivo(anoLectivo);
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the responsavelTurmaList where anoLectivo equals to anoLectivoId
        defaultResponsavelTurmaShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the responsavelTurmaList where anoLectivo equals to (anoLectivoId + 1)
        defaultResponsavelTurmaShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            responsavelTurmaRepository.saveAndFlush(responsavelTurma);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        responsavelTurma.setUtilizador(utilizador);
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);
        Long utilizadorId = utilizador.getId();

        // Get all the responsavelTurmaList where utilizador equals to utilizadorId
        defaultResponsavelTurmaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the responsavelTurmaList where utilizador equals to (utilizadorId + 1)
        defaultResponsavelTurmaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelTurmasByTurmaIsEqualToSomething() throws Exception {
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            responsavelTurmaRepository.saveAndFlush(responsavelTurma);
            turma = TurmaResourceIT.createEntity(em);
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(turma);
        em.flush();
        responsavelTurma.setTurma(turma);
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);
        Long turmaId = turma.getId();

        // Get all the responsavelTurmaList where turma equals to turmaId
        defaultResponsavelTurmaShouldBeFound("turmaId.equals=" + turmaId);

        // Get all the responsavelTurmaList where turma equals to (turmaId + 1)
        defaultResponsavelTurmaShouldNotBeFound("turmaId.equals=" + (turmaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResponsavelTurmaShouldBeFound(String filter) throws Exception {
        restResponsavelTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelTurma.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restResponsavelTurmaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResponsavelTurmaShouldNotBeFound(String filter) throws Exception {
        restResponsavelTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResponsavelTurmaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResponsavelTurma() throws Exception {
        // Get the responsavelTurma
        restResponsavelTurmaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResponsavelTurma() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        int databaseSizeBeforeUpdate = responsavelTurmaRepository.findAll().size();

        // Update the responsavelTurma
        ResponsavelTurma updatedResponsavelTurma = responsavelTurmaRepository.findById(responsavelTurma.getId()).get();
        // Disconnect from session so that the updates on updatedResponsavelTurma are not directly saved in db
        em.detach(updatedResponsavelTurma);
        updatedResponsavelTurma.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);
        ResponsavelTurmaDTO responsavelTurmaDTO = responsavelTurmaMapper.toDto(updatedResponsavelTurma);

        restResponsavelTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelTurmaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurmaDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelTurma in the database
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelTurma testResponsavelTurma = responsavelTurmaList.get(responsavelTurmaList.size() - 1);
        assertThat(testResponsavelTurma.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelTurma.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelTurma.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelTurma.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingResponsavelTurma() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurmaRepository.findAll().size();
        responsavelTurma.setId(count.incrementAndGet());

        // Create the ResponsavelTurma
        ResponsavelTurmaDTO responsavelTurmaDTO = responsavelTurmaMapper.toDto(responsavelTurma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelTurmaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelTurma in the database
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponsavelTurma() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurmaRepository.findAll().size();
        responsavelTurma.setId(count.incrementAndGet());

        // Create the ResponsavelTurma
        ResponsavelTurmaDTO responsavelTurmaDTO = responsavelTurmaMapper.toDto(responsavelTurma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelTurma in the database
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponsavelTurma() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurmaRepository.findAll().size();
        responsavelTurma.setId(count.incrementAndGet());

        // Create the ResponsavelTurma
        ResponsavelTurmaDTO responsavelTurmaDTO = responsavelTurmaMapper.toDto(responsavelTurma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelTurmaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelTurmaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelTurma in the database
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponsavelTurmaWithPatch() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        int databaseSizeBeforeUpdate = responsavelTurmaRepository.findAll().size();

        // Update the responsavelTurma using partial update
        ResponsavelTurma partialUpdatedResponsavelTurma = new ResponsavelTurma();
        partialUpdatedResponsavelTurma.setId(responsavelTurma.getId());

        partialUpdatedResponsavelTurma.ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);

        restResponsavelTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelTurma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelTurma))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelTurma in the database
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelTurma testResponsavelTurma = responsavelTurmaList.get(responsavelTurmaList.size() - 1);
        assertThat(testResponsavelTurma.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testResponsavelTurma.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelTurma.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelTurma.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateResponsavelTurmaWithPatch() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        int databaseSizeBeforeUpdate = responsavelTurmaRepository.findAll().size();

        // Update the responsavelTurma using partial update
        ResponsavelTurma partialUpdatedResponsavelTurma = new ResponsavelTurma();
        partialUpdatedResponsavelTurma.setId(responsavelTurma.getId());

        partialUpdatedResponsavelTurma.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);

        restResponsavelTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelTurma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelTurma))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelTurma in the database
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelTurma testResponsavelTurma = responsavelTurmaList.get(responsavelTurmaList.size() - 1);
        assertThat(testResponsavelTurma.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelTurma.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelTurma.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelTurma.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingResponsavelTurma() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurmaRepository.findAll().size();
        responsavelTurma.setId(count.incrementAndGet());

        // Create the ResponsavelTurma
        ResponsavelTurmaDTO responsavelTurmaDTO = responsavelTurmaMapper.toDto(responsavelTurma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responsavelTurmaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelTurma in the database
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponsavelTurma() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurmaRepository.findAll().size();
        responsavelTurma.setId(count.incrementAndGet());

        // Create the ResponsavelTurma
        ResponsavelTurmaDTO responsavelTurmaDTO = responsavelTurmaMapper.toDto(responsavelTurma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelTurma in the database
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponsavelTurma() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurmaRepository.findAll().size();
        responsavelTurma.setId(count.incrementAndGet());

        // Create the ResponsavelTurma
        ResponsavelTurmaDTO responsavelTurmaDTO = responsavelTurmaMapper.toDto(responsavelTurma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurmaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelTurma in the database
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponsavelTurma() throws Exception {
        // Initialize the database
        responsavelTurmaRepository.saveAndFlush(responsavelTurma);

        int databaseSizeBeforeDelete = responsavelTurmaRepository.findAll().size();

        // Delete the responsavelTurma
        restResponsavelTurmaMockMvc
            .perform(delete(ENTITY_API_URL_ID, responsavelTurma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResponsavelTurma> responsavelTurmaList = responsavelTurmaRepository.findAll();
        assertThat(responsavelTurmaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
