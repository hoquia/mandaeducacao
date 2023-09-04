package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.Disciplina;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.ResponsavelDisciplina;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.ResponsavelDisciplinaRepository;
import com.ravunana.longonkelo.service.ResponsavelDisciplinaService;
import com.ravunana.longonkelo.service.criteria.ResponsavelDisciplinaCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelDisciplinaMapper;
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
 * Integration tests for the {@link ResponsavelDisciplinaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ResponsavelDisciplinaResourceIT {

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

    private static final String ENTITY_API_URL = "/api/responsavel-disciplinas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResponsavelDisciplinaRepository responsavelDisciplinaRepository;

    @Mock
    private ResponsavelDisciplinaRepository responsavelDisciplinaRepositoryMock;

    @Autowired
    private ResponsavelDisciplinaMapper responsavelDisciplinaMapper;

    @Mock
    private ResponsavelDisciplinaService responsavelDisciplinaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsavelDisciplinaMockMvc;

    private ResponsavelDisciplina responsavelDisciplina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelDisciplina createEntity(EntityManager em) {
        ResponsavelDisciplina responsavelDisciplina = new ResponsavelDisciplina()
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .descricao(DEFAULT_DESCRICAO)
            .timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        responsavelDisciplina.setDisciplina(disciplina);
        return responsavelDisciplina;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelDisciplina createUpdatedEntity(EntityManager em) {
        ResponsavelDisciplina responsavelDisciplina = new ResponsavelDisciplina()
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .descricao(UPDATED_DESCRICAO)
            .timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createUpdatedEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        responsavelDisciplina.setDisciplina(disciplina);
        return responsavelDisciplina;
    }

    @BeforeEach
    public void initTest() {
        responsavelDisciplina = createEntity(em);
    }

    @Test
    @Transactional
    void createResponsavelDisciplina() throws Exception {
        int databaseSizeBeforeCreate = responsavelDisciplinaRepository.findAll().size();
        // Create the ResponsavelDisciplina
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO = responsavelDisciplinaMapper.toDto(responsavelDisciplina);
        restResponsavelDisciplinaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDisciplinaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResponsavelDisciplina in the database
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeCreate + 1);
        ResponsavelDisciplina testResponsavelDisciplina = responsavelDisciplinaList.get(responsavelDisciplinaList.size() - 1);
        assertThat(testResponsavelDisciplina.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testResponsavelDisciplina.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testResponsavelDisciplina.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testResponsavelDisciplina.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createResponsavelDisciplinaWithExistingId() throws Exception {
        // Create the ResponsavelDisciplina with an existing ID
        responsavelDisciplina.setId(1L);
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO = responsavelDisciplinaMapper.toDto(responsavelDisciplina);

        int databaseSizeBeforeCreate = responsavelDisciplinaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelDisciplinaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelDisciplina in the database
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDeIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelDisciplinaRepository.findAll().size();
        // set the field null
        responsavelDisciplina.setDe(null);

        // Create the ResponsavelDisciplina, which fails.
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO = responsavelDisciplinaMapper.toDto(responsavelDisciplina);

        restResponsavelDisciplinaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAteIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelDisciplinaRepository.findAll().size();
        // set the field null
        responsavelDisciplina.setAte(null);

        // Create the ResponsavelDisciplina, which fails.
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO = responsavelDisciplinaMapper.toDto(responsavelDisciplina);

        restResponsavelDisciplinaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinas() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList
        restResponsavelDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelDisciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelDisciplinasWithEagerRelationshipsIsEnabled() throws Exception {
        when(responsavelDisciplinaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelDisciplinaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(responsavelDisciplinaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelDisciplinasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(responsavelDisciplinaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelDisciplinaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(responsavelDisciplinaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getResponsavelDisciplina() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get the responsavelDisciplina
        restResponsavelDisciplinaMockMvc
            .perform(get(ENTITY_API_URL_ID, responsavelDisciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsavelDisciplina.getId().intValue()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.ate").value(DEFAULT_ATE.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getResponsavelDisciplinasByIdFiltering() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        Long id = responsavelDisciplina.getId();

        defaultResponsavelDisciplinaShouldBeFound("id.equals=" + id);
        defaultResponsavelDisciplinaShouldNotBeFound("id.notEquals=" + id);

        defaultResponsavelDisciplinaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResponsavelDisciplinaShouldNotBeFound("id.greaterThan=" + id);

        defaultResponsavelDisciplinaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResponsavelDisciplinaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByDeIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where de equals to DEFAULT_DE
        defaultResponsavelDisciplinaShouldBeFound("de.equals=" + DEFAULT_DE);

        // Get all the responsavelDisciplinaList where de equals to UPDATED_DE
        defaultResponsavelDisciplinaShouldNotBeFound("de.equals=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByDeIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where de in DEFAULT_DE or UPDATED_DE
        defaultResponsavelDisciplinaShouldBeFound("de.in=" + DEFAULT_DE + "," + UPDATED_DE);

        // Get all the responsavelDisciplinaList where de equals to UPDATED_DE
        defaultResponsavelDisciplinaShouldNotBeFound("de.in=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByDeIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where de is not null
        defaultResponsavelDisciplinaShouldBeFound("de.specified=true");

        // Get all the responsavelDisciplinaList where de is null
        defaultResponsavelDisciplinaShouldNotBeFound("de.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByDeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where de is greater than or equal to DEFAULT_DE
        defaultResponsavelDisciplinaShouldBeFound("de.greaterThanOrEqual=" + DEFAULT_DE);

        // Get all the responsavelDisciplinaList where de is greater than or equal to UPDATED_DE
        defaultResponsavelDisciplinaShouldNotBeFound("de.greaterThanOrEqual=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByDeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where de is less than or equal to DEFAULT_DE
        defaultResponsavelDisciplinaShouldBeFound("de.lessThanOrEqual=" + DEFAULT_DE);

        // Get all the responsavelDisciplinaList where de is less than or equal to SMALLER_DE
        defaultResponsavelDisciplinaShouldNotBeFound("de.lessThanOrEqual=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByDeIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where de is less than DEFAULT_DE
        defaultResponsavelDisciplinaShouldNotBeFound("de.lessThan=" + DEFAULT_DE);

        // Get all the responsavelDisciplinaList where de is less than UPDATED_DE
        defaultResponsavelDisciplinaShouldBeFound("de.lessThan=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByDeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where de is greater than DEFAULT_DE
        defaultResponsavelDisciplinaShouldNotBeFound("de.greaterThan=" + DEFAULT_DE);

        // Get all the responsavelDisciplinaList where de is greater than SMALLER_DE
        defaultResponsavelDisciplinaShouldBeFound("de.greaterThan=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByAteIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where ate equals to DEFAULT_ATE
        defaultResponsavelDisciplinaShouldBeFound("ate.equals=" + DEFAULT_ATE);

        // Get all the responsavelDisciplinaList where ate equals to UPDATED_ATE
        defaultResponsavelDisciplinaShouldNotBeFound("ate.equals=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByAteIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where ate in DEFAULT_ATE or UPDATED_ATE
        defaultResponsavelDisciplinaShouldBeFound("ate.in=" + DEFAULT_ATE + "," + UPDATED_ATE);

        // Get all the responsavelDisciplinaList where ate equals to UPDATED_ATE
        defaultResponsavelDisciplinaShouldNotBeFound("ate.in=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByAteIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where ate is not null
        defaultResponsavelDisciplinaShouldBeFound("ate.specified=true");

        // Get all the responsavelDisciplinaList where ate is null
        defaultResponsavelDisciplinaShouldNotBeFound("ate.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByAteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where ate is greater than or equal to DEFAULT_ATE
        defaultResponsavelDisciplinaShouldBeFound("ate.greaterThanOrEqual=" + DEFAULT_ATE);

        // Get all the responsavelDisciplinaList where ate is greater than or equal to UPDATED_ATE
        defaultResponsavelDisciplinaShouldNotBeFound("ate.greaterThanOrEqual=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByAteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where ate is less than or equal to DEFAULT_ATE
        defaultResponsavelDisciplinaShouldBeFound("ate.lessThanOrEqual=" + DEFAULT_ATE);

        // Get all the responsavelDisciplinaList where ate is less than or equal to SMALLER_ATE
        defaultResponsavelDisciplinaShouldNotBeFound("ate.lessThanOrEqual=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByAteIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where ate is less than DEFAULT_ATE
        defaultResponsavelDisciplinaShouldNotBeFound("ate.lessThan=" + DEFAULT_ATE);

        // Get all the responsavelDisciplinaList where ate is less than UPDATED_ATE
        defaultResponsavelDisciplinaShouldBeFound("ate.lessThan=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByAteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where ate is greater than DEFAULT_ATE
        defaultResponsavelDisciplinaShouldNotBeFound("ate.greaterThan=" + DEFAULT_ATE);

        // Get all the responsavelDisciplinaList where ate is greater than SMALLER_ATE
        defaultResponsavelDisciplinaShouldBeFound("ate.greaterThan=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where timestamp equals to DEFAULT_TIMESTAMP
        defaultResponsavelDisciplinaShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelDisciplinaList where timestamp equals to UPDATED_TIMESTAMP
        defaultResponsavelDisciplinaShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultResponsavelDisciplinaShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the responsavelDisciplinaList where timestamp equals to UPDATED_TIMESTAMP
        defaultResponsavelDisciplinaShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where timestamp is not null
        defaultResponsavelDisciplinaShouldBeFound("timestamp.specified=true");

        // Get all the responsavelDisciplinaList where timestamp is null
        defaultResponsavelDisciplinaShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultResponsavelDisciplinaShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelDisciplinaList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultResponsavelDisciplinaShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultResponsavelDisciplinaShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelDisciplinaList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultResponsavelDisciplinaShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where timestamp is less than DEFAULT_TIMESTAMP
        defaultResponsavelDisciplinaShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelDisciplinaList where timestamp is less than UPDATED_TIMESTAMP
        defaultResponsavelDisciplinaShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        // Get all the responsavelDisciplinaList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultResponsavelDisciplinaShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelDisciplinaList where timestamp is greater than SMALLER_TIMESTAMP
        defaultResponsavelDisciplinaShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByResponsavelIsEqualToSomething() throws Exception {
        Docente responsavel;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);
            responsavel = DocenteResourceIT.createEntity(em);
        } else {
            responsavel = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(responsavel);
        em.flush();
        responsavelDisciplina.addResponsavel(responsavel);
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);
        Long responsavelId = responsavel.getId();

        // Get all the responsavelDisciplinaList where responsavel equals to responsavelId
        defaultResponsavelDisciplinaShouldBeFound("responsavelId.equals=" + responsavelId);

        // Get all the responsavelDisciplinaList where responsavel equals to (responsavelId + 1)
        defaultResponsavelDisciplinaShouldNotBeFound("responsavelId.equals=" + (responsavelId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        responsavelDisciplina.addAnoLectivo(anoLectivo);
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the responsavelDisciplinaList where anoLectivo equals to anoLectivoId
        defaultResponsavelDisciplinaShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the responsavelDisciplinaList where anoLectivo equals to (anoLectivoId + 1)
        defaultResponsavelDisciplinaShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        responsavelDisciplina.setUtilizador(utilizador);
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);
        Long utilizadorId = utilizador.getId();

        // Get all the responsavelDisciplinaList where utilizador equals to utilizadorId
        defaultResponsavelDisciplinaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the responsavelDisciplinaList where utilizador equals to (utilizadorId + 1)
        defaultResponsavelDisciplinaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelDisciplinasByDisciplinaIsEqualToSomething() throws Exception {
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);
            disciplina = DisciplinaResourceIT.createEntity(em);
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        em.persist(disciplina);
        em.flush();
        responsavelDisciplina.setDisciplina(disciplina);
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);
        Long disciplinaId = disciplina.getId();

        // Get all the responsavelDisciplinaList where disciplina equals to disciplinaId
        defaultResponsavelDisciplinaShouldBeFound("disciplinaId.equals=" + disciplinaId);

        // Get all the responsavelDisciplinaList where disciplina equals to (disciplinaId + 1)
        defaultResponsavelDisciplinaShouldNotBeFound("disciplinaId.equals=" + (disciplinaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResponsavelDisciplinaShouldBeFound(String filter) throws Exception {
        restResponsavelDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelDisciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restResponsavelDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResponsavelDisciplinaShouldNotBeFound(String filter) throws Exception {
        restResponsavelDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResponsavelDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResponsavelDisciplina() throws Exception {
        // Get the responsavelDisciplina
        restResponsavelDisciplinaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResponsavelDisciplina() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        int databaseSizeBeforeUpdate = responsavelDisciplinaRepository.findAll().size();

        // Update the responsavelDisciplina
        ResponsavelDisciplina updatedResponsavelDisciplina = responsavelDisciplinaRepository.findById(responsavelDisciplina.getId()).get();
        // Disconnect from session so that the updates on updatedResponsavelDisciplina are not directly saved in db
        em.detach(updatedResponsavelDisciplina);
        updatedResponsavelDisciplina.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO = responsavelDisciplinaMapper.toDto(updatedResponsavelDisciplina);

        restResponsavelDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelDisciplinaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDisciplinaDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelDisciplina in the database
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelDisciplina testResponsavelDisciplina = responsavelDisciplinaList.get(responsavelDisciplinaList.size() - 1);
        assertThat(testResponsavelDisciplina.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelDisciplina.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelDisciplina.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelDisciplina.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingResponsavelDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = responsavelDisciplinaRepository.findAll().size();
        responsavelDisciplina.setId(count.incrementAndGet());

        // Create the ResponsavelDisciplina
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO = responsavelDisciplinaMapper.toDto(responsavelDisciplina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelDisciplinaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelDisciplina in the database
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponsavelDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = responsavelDisciplinaRepository.findAll().size();
        responsavelDisciplina.setId(count.incrementAndGet());

        // Create the ResponsavelDisciplina
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO = responsavelDisciplinaMapper.toDto(responsavelDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelDisciplina in the database
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponsavelDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = responsavelDisciplinaRepository.findAll().size();
        responsavelDisciplina.setId(count.incrementAndGet());

        // Create the ResponsavelDisciplina
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO = responsavelDisciplinaMapper.toDto(responsavelDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDisciplinaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelDisciplina in the database
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponsavelDisciplinaWithPatch() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        int databaseSizeBeforeUpdate = responsavelDisciplinaRepository.findAll().size();

        // Update the responsavelDisciplina using partial update
        ResponsavelDisciplina partialUpdatedResponsavelDisciplina = new ResponsavelDisciplina();
        partialUpdatedResponsavelDisciplina.setId(responsavelDisciplina.getId());

        partialUpdatedResponsavelDisciplina.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);

        restResponsavelDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelDisciplina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelDisciplina))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelDisciplina in the database
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelDisciplina testResponsavelDisciplina = responsavelDisciplinaList.get(responsavelDisciplinaList.size() - 1);
        assertThat(testResponsavelDisciplina.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelDisciplina.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelDisciplina.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelDisciplina.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateResponsavelDisciplinaWithPatch() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        int databaseSizeBeforeUpdate = responsavelDisciplinaRepository.findAll().size();

        // Update the responsavelDisciplina using partial update
        ResponsavelDisciplina partialUpdatedResponsavelDisciplina = new ResponsavelDisciplina();
        partialUpdatedResponsavelDisciplina.setId(responsavelDisciplina.getId());

        partialUpdatedResponsavelDisciplina.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);

        restResponsavelDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelDisciplina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelDisciplina))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelDisciplina in the database
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelDisciplina testResponsavelDisciplina = responsavelDisciplinaList.get(responsavelDisciplinaList.size() - 1);
        assertThat(testResponsavelDisciplina.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelDisciplina.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelDisciplina.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelDisciplina.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingResponsavelDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = responsavelDisciplinaRepository.findAll().size();
        responsavelDisciplina.setId(count.incrementAndGet());

        // Create the ResponsavelDisciplina
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO = responsavelDisciplinaMapper.toDto(responsavelDisciplina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responsavelDisciplinaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelDisciplina in the database
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponsavelDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = responsavelDisciplinaRepository.findAll().size();
        responsavelDisciplina.setId(count.incrementAndGet());

        // Create the ResponsavelDisciplina
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO = responsavelDisciplinaMapper.toDto(responsavelDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDisciplinaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelDisciplina in the database
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponsavelDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = responsavelDisciplinaRepository.findAll().size();
        responsavelDisciplina.setId(count.incrementAndGet());

        // Create the ResponsavelDisciplina
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO = responsavelDisciplinaMapper.toDto(responsavelDisciplina);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDisciplinaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelDisciplina in the database
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponsavelDisciplina() throws Exception {
        // Initialize the database
        responsavelDisciplinaRepository.saveAndFlush(responsavelDisciplina);

        int databaseSizeBeforeDelete = responsavelDisciplinaRepository.findAll().size();

        // Delete the responsavelDisciplina
        restResponsavelDisciplinaMockMvc
            .perform(delete(ENTITY_API_URL_ID, responsavelDisciplina.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResponsavelDisciplina> responsavelDisciplinaList = responsavelDisciplinaRepository.findAll();
        assertThat(responsavelDisciplinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
