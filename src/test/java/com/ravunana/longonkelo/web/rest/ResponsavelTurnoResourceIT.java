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
import com.ravunana.longonkelo.domain.ResponsavelTurno;
import com.ravunana.longonkelo.domain.Turno;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.ResponsavelTurnoRepository;
import com.ravunana.longonkelo.service.ResponsavelTurnoService;
import com.ravunana.longonkelo.service.criteria.ResponsavelTurnoCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelTurnoDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelTurnoMapper;
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
 * Integration tests for the {@link ResponsavelTurnoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ResponsavelTurnoResourceIT {

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

    private static final String ENTITY_API_URL = "/api/responsavel-turnos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResponsavelTurnoRepository responsavelTurnoRepository;

    @Mock
    private ResponsavelTurnoRepository responsavelTurnoRepositoryMock;

    @Autowired
    private ResponsavelTurnoMapper responsavelTurnoMapper;

    @Mock
    private ResponsavelTurnoService responsavelTurnoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsavelTurnoMockMvc;

    private ResponsavelTurno responsavelTurno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelTurno createEntity(EntityManager em) {
        ResponsavelTurno responsavelTurno = new ResponsavelTurno()
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .descricao(DEFAULT_DESCRICAO)
            .timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        Turno turno;
        if (TestUtil.findAll(em, Turno.class).isEmpty()) {
            turno = TurnoResourceIT.createEntity(em);
            em.persist(turno);
            em.flush();
        } else {
            turno = TestUtil.findAll(em, Turno.class).get(0);
        }
        responsavelTurno.setTurno(turno);
        return responsavelTurno;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelTurno createUpdatedEntity(EntityManager em) {
        ResponsavelTurno responsavelTurno = new ResponsavelTurno()
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .descricao(UPDATED_DESCRICAO)
            .timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        Turno turno;
        if (TestUtil.findAll(em, Turno.class).isEmpty()) {
            turno = TurnoResourceIT.createUpdatedEntity(em);
            em.persist(turno);
            em.flush();
        } else {
            turno = TestUtil.findAll(em, Turno.class).get(0);
        }
        responsavelTurno.setTurno(turno);
        return responsavelTurno;
    }

    @BeforeEach
    public void initTest() {
        responsavelTurno = createEntity(em);
    }

    @Test
    @Transactional
    void createResponsavelTurno() throws Exception {
        int databaseSizeBeforeCreate = responsavelTurnoRepository.findAll().size();
        // Create the ResponsavelTurno
        ResponsavelTurnoDTO responsavelTurnoDTO = responsavelTurnoMapper.toDto(responsavelTurno);
        restResponsavelTurnoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelTurnoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResponsavelTurno in the database
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeCreate + 1);
        ResponsavelTurno testResponsavelTurno = responsavelTurnoList.get(responsavelTurnoList.size() - 1);
        assertThat(testResponsavelTurno.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testResponsavelTurno.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testResponsavelTurno.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testResponsavelTurno.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createResponsavelTurnoWithExistingId() throws Exception {
        // Create the ResponsavelTurno with an existing ID
        responsavelTurno.setId(1L);
        ResponsavelTurnoDTO responsavelTurnoDTO = responsavelTurnoMapper.toDto(responsavelTurno);

        int databaseSizeBeforeCreate = responsavelTurnoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelTurnoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelTurnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelTurno in the database
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDeIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelTurnoRepository.findAll().size();
        // set the field null
        responsavelTurno.setDe(null);

        // Create the ResponsavelTurno, which fails.
        ResponsavelTurnoDTO responsavelTurnoDTO = responsavelTurnoMapper.toDto(responsavelTurno);

        restResponsavelTurnoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelTurnoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAteIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelTurnoRepository.findAll().size();
        // set the field null
        responsavelTurno.setAte(null);

        // Create the ResponsavelTurno, which fails.
        ResponsavelTurnoDTO responsavelTurnoDTO = responsavelTurnoMapper.toDto(responsavelTurno);

        restResponsavelTurnoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelTurnoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnos() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList
        restResponsavelTurnoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelTurno.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelTurnosWithEagerRelationshipsIsEnabled() throws Exception {
        when(responsavelTurnoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelTurnoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(responsavelTurnoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelTurnosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(responsavelTurnoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelTurnoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(responsavelTurnoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getResponsavelTurno() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get the responsavelTurno
        restResponsavelTurnoMockMvc
            .perform(get(ENTITY_API_URL_ID, responsavelTurno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsavelTurno.getId().intValue()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.ate").value(DEFAULT_ATE.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getResponsavelTurnosByIdFiltering() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        Long id = responsavelTurno.getId();

        defaultResponsavelTurnoShouldBeFound("id.equals=" + id);
        defaultResponsavelTurnoShouldNotBeFound("id.notEquals=" + id);

        defaultResponsavelTurnoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResponsavelTurnoShouldNotBeFound("id.greaterThan=" + id);

        defaultResponsavelTurnoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResponsavelTurnoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByDeIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where de equals to DEFAULT_DE
        defaultResponsavelTurnoShouldBeFound("de.equals=" + DEFAULT_DE);

        // Get all the responsavelTurnoList where de equals to UPDATED_DE
        defaultResponsavelTurnoShouldNotBeFound("de.equals=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByDeIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where de in DEFAULT_DE or UPDATED_DE
        defaultResponsavelTurnoShouldBeFound("de.in=" + DEFAULT_DE + "," + UPDATED_DE);

        // Get all the responsavelTurnoList where de equals to UPDATED_DE
        defaultResponsavelTurnoShouldNotBeFound("de.in=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByDeIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where de is not null
        defaultResponsavelTurnoShouldBeFound("de.specified=true");

        // Get all the responsavelTurnoList where de is null
        defaultResponsavelTurnoShouldNotBeFound("de.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByDeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where de is greater than or equal to DEFAULT_DE
        defaultResponsavelTurnoShouldBeFound("de.greaterThanOrEqual=" + DEFAULT_DE);

        // Get all the responsavelTurnoList where de is greater than or equal to UPDATED_DE
        defaultResponsavelTurnoShouldNotBeFound("de.greaterThanOrEqual=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByDeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where de is less than or equal to DEFAULT_DE
        defaultResponsavelTurnoShouldBeFound("de.lessThanOrEqual=" + DEFAULT_DE);

        // Get all the responsavelTurnoList where de is less than or equal to SMALLER_DE
        defaultResponsavelTurnoShouldNotBeFound("de.lessThanOrEqual=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByDeIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where de is less than DEFAULT_DE
        defaultResponsavelTurnoShouldNotBeFound("de.lessThan=" + DEFAULT_DE);

        // Get all the responsavelTurnoList where de is less than UPDATED_DE
        defaultResponsavelTurnoShouldBeFound("de.lessThan=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByDeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where de is greater than DEFAULT_DE
        defaultResponsavelTurnoShouldNotBeFound("de.greaterThan=" + DEFAULT_DE);

        // Get all the responsavelTurnoList where de is greater than SMALLER_DE
        defaultResponsavelTurnoShouldBeFound("de.greaterThan=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByAteIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where ate equals to DEFAULT_ATE
        defaultResponsavelTurnoShouldBeFound("ate.equals=" + DEFAULT_ATE);

        // Get all the responsavelTurnoList where ate equals to UPDATED_ATE
        defaultResponsavelTurnoShouldNotBeFound("ate.equals=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByAteIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where ate in DEFAULT_ATE or UPDATED_ATE
        defaultResponsavelTurnoShouldBeFound("ate.in=" + DEFAULT_ATE + "," + UPDATED_ATE);

        // Get all the responsavelTurnoList where ate equals to UPDATED_ATE
        defaultResponsavelTurnoShouldNotBeFound("ate.in=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByAteIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where ate is not null
        defaultResponsavelTurnoShouldBeFound("ate.specified=true");

        // Get all the responsavelTurnoList where ate is null
        defaultResponsavelTurnoShouldNotBeFound("ate.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByAteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where ate is greater than or equal to DEFAULT_ATE
        defaultResponsavelTurnoShouldBeFound("ate.greaterThanOrEqual=" + DEFAULT_ATE);

        // Get all the responsavelTurnoList where ate is greater than or equal to UPDATED_ATE
        defaultResponsavelTurnoShouldNotBeFound("ate.greaterThanOrEqual=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByAteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where ate is less than or equal to DEFAULT_ATE
        defaultResponsavelTurnoShouldBeFound("ate.lessThanOrEqual=" + DEFAULT_ATE);

        // Get all the responsavelTurnoList where ate is less than or equal to SMALLER_ATE
        defaultResponsavelTurnoShouldNotBeFound("ate.lessThanOrEqual=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByAteIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where ate is less than DEFAULT_ATE
        defaultResponsavelTurnoShouldNotBeFound("ate.lessThan=" + DEFAULT_ATE);

        // Get all the responsavelTurnoList where ate is less than UPDATED_ATE
        defaultResponsavelTurnoShouldBeFound("ate.lessThan=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByAteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where ate is greater than DEFAULT_ATE
        defaultResponsavelTurnoShouldNotBeFound("ate.greaterThan=" + DEFAULT_ATE);

        // Get all the responsavelTurnoList where ate is greater than SMALLER_ATE
        defaultResponsavelTurnoShouldBeFound("ate.greaterThan=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where timestamp equals to DEFAULT_TIMESTAMP
        defaultResponsavelTurnoShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelTurnoList where timestamp equals to UPDATED_TIMESTAMP
        defaultResponsavelTurnoShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultResponsavelTurnoShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the responsavelTurnoList where timestamp equals to UPDATED_TIMESTAMP
        defaultResponsavelTurnoShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where timestamp is not null
        defaultResponsavelTurnoShouldBeFound("timestamp.specified=true");

        // Get all the responsavelTurnoList where timestamp is null
        defaultResponsavelTurnoShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultResponsavelTurnoShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelTurnoList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultResponsavelTurnoShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultResponsavelTurnoShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelTurnoList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultResponsavelTurnoShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where timestamp is less than DEFAULT_TIMESTAMP
        defaultResponsavelTurnoShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelTurnoList where timestamp is less than UPDATED_TIMESTAMP
        defaultResponsavelTurnoShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        // Get all the responsavelTurnoList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultResponsavelTurnoShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelTurnoList where timestamp is greater than SMALLER_TIMESTAMP
        defaultResponsavelTurnoShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByResponsavelIsEqualToSomething() throws Exception {
        Docente responsavel;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            responsavelTurnoRepository.saveAndFlush(responsavelTurno);
            responsavel = DocenteResourceIT.createEntity(em);
        } else {
            responsavel = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(responsavel);
        em.flush();
        responsavelTurno.addResponsavel(responsavel);
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);
        Long responsavelId = responsavel.getId();

        // Get all the responsavelTurnoList where responsavel equals to responsavelId
        defaultResponsavelTurnoShouldBeFound("responsavelId.equals=" + responsavelId);

        // Get all the responsavelTurnoList where responsavel equals to (responsavelId + 1)
        defaultResponsavelTurnoShouldNotBeFound("responsavelId.equals=" + (responsavelId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            responsavelTurnoRepository.saveAndFlush(responsavelTurno);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        responsavelTurno.addAnoLectivo(anoLectivo);
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the responsavelTurnoList where anoLectivo equals to anoLectivoId
        defaultResponsavelTurnoShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the responsavelTurnoList where anoLectivo equals to (anoLectivoId + 1)
        defaultResponsavelTurnoShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            responsavelTurnoRepository.saveAndFlush(responsavelTurno);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        responsavelTurno.setUtilizador(utilizador);
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);
        Long utilizadorId = utilizador.getId();

        // Get all the responsavelTurnoList where utilizador equals to utilizadorId
        defaultResponsavelTurnoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the responsavelTurnoList where utilizador equals to (utilizadorId + 1)
        defaultResponsavelTurnoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelTurnosByTurnoIsEqualToSomething() throws Exception {
        Turno turno;
        if (TestUtil.findAll(em, Turno.class).isEmpty()) {
            responsavelTurnoRepository.saveAndFlush(responsavelTurno);
            turno = TurnoResourceIT.createEntity(em);
        } else {
            turno = TestUtil.findAll(em, Turno.class).get(0);
        }
        em.persist(turno);
        em.flush();
        responsavelTurno.setTurno(turno);
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);
        Long turnoId = turno.getId();

        // Get all the responsavelTurnoList where turno equals to turnoId
        defaultResponsavelTurnoShouldBeFound("turnoId.equals=" + turnoId);

        // Get all the responsavelTurnoList where turno equals to (turnoId + 1)
        defaultResponsavelTurnoShouldNotBeFound("turnoId.equals=" + (turnoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResponsavelTurnoShouldBeFound(String filter) throws Exception {
        restResponsavelTurnoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelTurno.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restResponsavelTurnoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResponsavelTurnoShouldNotBeFound(String filter) throws Exception {
        restResponsavelTurnoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResponsavelTurnoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResponsavelTurno() throws Exception {
        // Get the responsavelTurno
        restResponsavelTurnoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResponsavelTurno() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        int databaseSizeBeforeUpdate = responsavelTurnoRepository.findAll().size();

        // Update the responsavelTurno
        ResponsavelTurno updatedResponsavelTurno = responsavelTurnoRepository.findById(responsavelTurno.getId()).get();
        // Disconnect from session so that the updates on updatedResponsavelTurno are not directly saved in db
        em.detach(updatedResponsavelTurno);
        updatedResponsavelTurno.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);
        ResponsavelTurnoDTO responsavelTurnoDTO = responsavelTurnoMapper.toDto(updatedResponsavelTurno);

        restResponsavelTurnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelTurnoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurnoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelTurno in the database
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelTurno testResponsavelTurno = responsavelTurnoList.get(responsavelTurnoList.size() - 1);
        assertThat(testResponsavelTurno.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelTurno.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelTurno.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelTurno.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingResponsavelTurno() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurnoRepository.findAll().size();
        responsavelTurno.setId(count.incrementAndGet());

        // Create the ResponsavelTurno
        ResponsavelTurnoDTO responsavelTurnoDTO = responsavelTurnoMapper.toDto(responsavelTurno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelTurnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelTurnoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelTurno in the database
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponsavelTurno() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurnoRepository.findAll().size();
        responsavelTurno.setId(count.incrementAndGet());

        // Create the ResponsavelTurno
        ResponsavelTurnoDTO responsavelTurnoDTO = responsavelTurnoMapper.toDto(responsavelTurno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelTurnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelTurno in the database
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponsavelTurno() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurnoRepository.findAll().size();
        responsavelTurno.setId(count.incrementAndGet());

        // Create the ResponsavelTurno
        ResponsavelTurnoDTO responsavelTurnoDTO = responsavelTurnoMapper.toDto(responsavelTurno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelTurnoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelTurnoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelTurno in the database
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponsavelTurnoWithPatch() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        int databaseSizeBeforeUpdate = responsavelTurnoRepository.findAll().size();

        // Update the responsavelTurno using partial update
        ResponsavelTurno partialUpdatedResponsavelTurno = new ResponsavelTurno();
        partialUpdatedResponsavelTurno.setId(responsavelTurno.getId());

        partialUpdatedResponsavelTurno.de(UPDATED_DE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);

        restResponsavelTurnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelTurno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelTurno))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelTurno in the database
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelTurno testResponsavelTurno = responsavelTurnoList.get(responsavelTurnoList.size() - 1);
        assertThat(testResponsavelTurno.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelTurno.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testResponsavelTurno.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelTurno.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateResponsavelTurnoWithPatch() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        int databaseSizeBeforeUpdate = responsavelTurnoRepository.findAll().size();

        // Update the responsavelTurno using partial update
        ResponsavelTurno partialUpdatedResponsavelTurno = new ResponsavelTurno();
        partialUpdatedResponsavelTurno.setId(responsavelTurno.getId());

        partialUpdatedResponsavelTurno.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);

        restResponsavelTurnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelTurno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelTurno))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelTurno in the database
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelTurno testResponsavelTurno = responsavelTurnoList.get(responsavelTurnoList.size() - 1);
        assertThat(testResponsavelTurno.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelTurno.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelTurno.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelTurno.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingResponsavelTurno() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurnoRepository.findAll().size();
        responsavelTurno.setId(count.incrementAndGet());

        // Create the ResponsavelTurno
        ResponsavelTurnoDTO responsavelTurnoDTO = responsavelTurnoMapper.toDto(responsavelTurno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelTurnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responsavelTurnoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelTurno in the database
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponsavelTurno() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurnoRepository.findAll().size();
        responsavelTurno.setId(count.incrementAndGet());

        // Create the ResponsavelTurno
        ResponsavelTurnoDTO responsavelTurnoDTO = responsavelTurnoMapper.toDto(responsavelTurno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelTurnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelTurno in the database
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponsavelTurno() throws Exception {
        int databaseSizeBeforeUpdate = responsavelTurnoRepository.findAll().size();
        responsavelTurno.setId(count.incrementAndGet());

        // Create the ResponsavelTurno
        ResponsavelTurnoDTO responsavelTurnoDTO = responsavelTurnoMapper.toDto(responsavelTurno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelTurnoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelTurnoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelTurno in the database
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponsavelTurno() throws Exception {
        // Initialize the database
        responsavelTurnoRepository.saveAndFlush(responsavelTurno);

        int databaseSizeBeforeDelete = responsavelTurnoRepository.findAll().size();

        // Delete the responsavelTurno
        restResponsavelTurnoMockMvc
            .perform(delete(ENTITY_API_URL_ID, responsavelTurno.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResponsavelTurno> responsavelTurnoList = responsavelTurnoRepository.findAll();
        assertThat(responsavelTurnoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
