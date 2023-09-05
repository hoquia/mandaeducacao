package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.domain.Licao;
import com.ravunana.longonkelo.domain.PeriodoHorario;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.domain.enumeration.DiaSemana;
import com.ravunana.longonkelo.repository.HorarioRepository;
import com.ravunana.longonkelo.service.HorarioService;
import com.ravunana.longonkelo.service.criteria.HorarioCriteria;
import com.ravunana.longonkelo.service.dto.HorarioDTO;
import com.ravunana.longonkelo.service.mapper.HorarioMapper;
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
 * Integration tests for the {@link HorarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class HorarioResourceIT {

    private static final String DEFAULT_CHAVE_COMPOSTA_1 = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE_COMPOSTA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CHAVE_COMPOSTA_2 = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE_COMPOSTA_2 = "BBBBBBBBBB";

    private static final DiaSemana DEFAULT_DIA_SEMANA = DiaSemana.SEGUNDA;
    private static final DiaSemana UPDATED_DIA_SEMANA = DiaSemana.TERCA;

    private static final String ENTITY_API_URL = "/api/horarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HorarioRepository horarioRepository;

    @Mock
    private HorarioRepository horarioRepositoryMock;

    @Autowired
    private HorarioMapper horarioMapper;

    @Mock
    private HorarioService horarioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHorarioMockMvc;

    private Horario horario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horario createEntity(EntityManager em) {
        Horario horario = new Horario()
            .chaveComposta1(DEFAULT_CHAVE_COMPOSTA_1)
            .chaveComposta2(DEFAULT_CHAVE_COMPOSTA_2)
            .diaSemana(DEFAULT_DIA_SEMANA);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        horario.setTurma(turma);
        // Add required entity
        PeriodoHorario periodoHorario;
        if (TestUtil.findAll(em, PeriodoHorario.class).isEmpty()) {
            periodoHorario = PeriodoHorarioResourceIT.createEntity(em);
            em.persist(periodoHorario);
            em.flush();
        } else {
            periodoHorario = TestUtil.findAll(em, PeriodoHorario.class).get(0);
        }
        horario.setPeriodo(periodoHorario);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        horario.setDocente(docente);
        // Add required entity
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            disciplinaCurricular = DisciplinaCurricularResourceIT.createEntity(em);
            em.persist(disciplinaCurricular);
            em.flush();
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        horario.setDisciplinaCurricular(disciplinaCurricular);
        return horario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horario createUpdatedEntity(EntityManager em) {
        Horario horario = new Horario()
            .chaveComposta1(UPDATED_CHAVE_COMPOSTA_1)
            .chaveComposta2(UPDATED_CHAVE_COMPOSTA_2)
            .diaSemana(UPDATED_DIA_SEMANA);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createUpdatedEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        horario.setTurma(turma);
        // Add required entity
        PeriodoHorario periodoHorario;
        if (TestUtil.findAll(em, PeriodoHorario.class).isEmpty()) {
            periodoHorario = PeriodoHorarioResourceIT.createUpdatedEntity(em);
            em.persist(periodoHorario);
            em.flush();
        } else {
            periodoHorario = TestUtil.findAll(em, PeriodoHorario.class).get(0);
        }
        horario.setPeriodo(periodoHorario);
        // Add required entity
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            docente = DocenteResourceIT.createUpdatedEntity(em);
            em.persist(docente);
            em.flush();
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        horario.setDocente(docente);
        // Add required entity
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            disciplinaCurricular = DisciplinaCurricularResourceIT.createUpdatedEntity(em);
            em.persist(disciplinaCurricular);
            em.flush();
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        horario.setDisciplinaCurricular(disciplinaCurricular);
        return horario;
    }

    @BeforeEach
    public void initTest() {
        horario = createEntity(em);
    }

    @Test
    @Transactional
    void createHorario() throws Exception {
        int databaseSizeBeforeCreate = horarioRepository.findAll().size();
        // Create the Horario
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);
        restHorarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeCreate + 1);
        Horario testHorario = horarioList.get(horarioList.size() - 1);
        assertThat(testHorario.getChaveComposta1()).isEqualTo(DEFAULT_CHAVE_COMPOSTA_1);
        assertThat(testHorario.getChaveComposta2()).isEqualTo(DEFAULT_CHAVE_COMPOSTA_2);
        assertThat(testHorario.getDiaSemana()).isEqualTo(DEFAULT_DIA_SEMANA);
    }

    @Test
    @Transactional
    void createHorarioWithExistingId() throws Exception {
        // Create the Horario with an existing ID
        horario.setId(1L);
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        int databaseSizeBeforeCreate = horarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDiaSemanaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioRepository.findAll().size();
        // set the field null
        horario.setDiaSemana(null);

        // Create the Horario, which fails.
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        restHorarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHorarios() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList
        restHorarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horario.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta1").value(hasItem(DEFAULT_CHAVE_COMPOSTA_1)))
            .andExpect(jsonPath("$.[*].chaveComposta2").value(hasItem(DEFAULT_CHAVE_COMPOSTA_2)))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHorariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(horarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHorarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(horarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHorariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(horarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHorarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(horarioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getHorario() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get the horario
        restHorarioMockMvc
            .perform(get(ENTITY_API_URL_ID, horario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(horario.getId().intValue()))
            .andExpect(jsonPath("$.chaveComposta1").value(DEFAULT_CHAVE_COMPOSTA_1))
            .andExpect(jsonPath("$.chaveComposta2").value(DEFAULT_CHAVE_COMPOSTA_2))
            .andExpect(jsonPath("$.diaSemana").value(DEFAULT_DIA_SEMANA.toString()));
    }

    @Test
    @Transactional
    void getHorariosByIdFiltering() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        Long id = horario.getId();

        defaultHorarioShouldBeFound("id.equals=" + id);
        defaultHorarioShouldNotBeFound("id.notEquals=" + id);

        defaultHorarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHorarioShouldNotBeFound("id.greaterThan=" + id);

        defaultHorarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHorarioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllHorariosByChaveComposta1IsEqualToSomething() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where chaveComposta1 equals to DEFAULT_CHAVE_COMPOSTA_1
        defaultHorarioShouldBeFound("chaveComposta1.equals=" + DEFAULT_CHAVE_COMPOSTA_1);

        // Get all the horarioList where chaveComposta1 equals to UPDATED_CHAVE_COMPOSTA_1
        defaultHorarioShouldNotBeFound("chaveComposta1.equals=" + UPDATED_CHAVE_COMPOSTA_1);
    }

    @Test
    @Transactional
    void getAllHorariosByChaveComposta1IsInShouldWork() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where chaveComposta1 in DEFAULT_CHAVE_COMPOSTA_1 or UPDATED_CHAVE_COMPOSTA_1
        defaultHorarioShouldBeFound("chaveComposta1.in=" + DEFAULT_CHAVE_COMPOSTA_1 + "," + UPDATED_CHAVE_COMPOSTA_1);

        // Get all the horarioList where chaveComposta1 equals to UPDATED_CHAVE_COMPOSTA_1
        defaultHorarioShouldNotBeFound("chaveComposta1.in=" + UPDATED_CHAVE_COMPOSTA_1);
    }

    @Test
    @Transactional
    void getAllHorariosByChaveComposta1IsNullOrNotNull() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where chaveComposta1 is not null
        defaultHorarioShouldBeFound("chaveComposta1.specified=true");

        // Get all the horarioList where chaveComposta1 is null
        defaultHorarioShouldNotBeFound("chaveComposta1.specified=false");
    }

    @Test
    @Transactional
    void getAllHorariosByChaveComposta1ContainsSomething() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where chaveComposta1 contains DEFAULT_CHAVE_COMPOSTA_1
        defaultHorarioShouldBeFound("chaveComposta1.contains=" + DEFAULT_CHAVE_COMPOSTA_1);

        // Get all the horarioList where chaveComposta1 contains UPDATED_CHAVE_COMPOSTA_1
        defaultHorarioShouldNotBeFound("chaveComposta1.contains=" + UPDATED_CHAVE_COMPOSTA_1);
    }

    @Test
    @Transactional
    void getAllHorariosByChaveComposta1NotContainsSomething() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where chaveComposta1 does not contain DEFAULT_CHAVE_COMPOSTA_1
        defaultHorarioShouldNotBeFound("chaveComposta1.doesNotContain=" + DEFAULT_CHAVE_COMPOSTA_1);

        // Get all the horarioList where chaveComposta1 does not contain UPDATED_CHAVE_COMPOSTA_1
        defaultHorarioShouldBeFound("chaveComposta1.doesNotContain=" + UPDATED_CHAVE_COMPOSTA_1);
    }

    @Test
    @Transactional
    void getAllHorariosByChaveComposta2IsEqualToSomething() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where chaveComposta2 equals to DEFAULT_CHAVE_COMPOSTA_2
        defaultHorarioShouldBeFound("chaveComposta2.equals=" + DEFAULT_CHAVE_COMPOSTA_2);

        // Get all the horarioList where chaveComposta2 equals to UPDATED_CHAVE_COMPOSTA_2
        defaultHorarioShouldNotBeFound("chaveComposta2.equals=" + UPDATED_CHAVE_COMPOSTA_2);
    }

    @Test
    @Transactional
    void getAllHorariosByChaveComposta2IsInShouldWork() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where chaveComposta2 in DEFAULT_CHAVE_COMPOSTA_2 or UPDATED_CHAVE_COMPOSTA_2
        defaultHorarioShouldBeFound("chaveComposta2.in=" + DEFAULT_CHAVE_COMPOSTA_2 + "," + UPDATED_CHAVE_COMPOSTA_2);

        // Get all the horarioList where chaveComposta2 equals to UPDATED_CHAVE_COMPOSTA_2
        defaultHorarioShouldNotBeFound("chaveComposta2.in=" + UPDATED_CHAVE_COMPOSTA_2);
    }

    @Test
    @Transactional
    void getAllHorariosByChaveComposta2IsNullOrNotNull() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where chaveComposta2 is not null
        defaultHorarioShouldBeFound("chaveComposta2.specified=true");

        // Get all the horarioList where chaveComposta2 is null
        defaultHorarioShouldNotBeFound("chaveComposta2.specified=false");
    }

    @Test
    @Transactional
    void getAllHorariosByChaveComposta2ContainsSomething() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where chaveComposta2 contains DEFAULT_CHAVE_COMPOSTA_2
        defaultHorarioShouldBeFound("chaveComposta2.contains=" + DEFAULT_CHAVE_COMPOSTA_2);

        // Get all the horarioList where chaveComposta2 contains UPDATED_CHAVE_COMPOSTA_2
        defaultHorarioShouldNotBeFound("chaveComposta2.contains=" + UPDATED_CHAVE_COMPOSTA_2);
    }

    @Test
    @Transactional
    void getAllHorariosByChaveComposta2NotContainsSomething() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where chaveComposta2 does not contain DEFAULT_CHAVE_COMPOSTA_2
        defaultHorarioShouldNotBeFound("chaveComposta2.doesNotContain=" + DEFAULT_CHAVE_COMPOSTA_2);

        // Get all the horarioList where chaveComposta2 does not contain UPDATED_CHAVE_COMPOSTA_2
        defaultHorarioShouldBeFound("chaveComposta2.doesNotContain=" + UPDATED_CHAVE_COMPOSTA_2);
    }

    @Test
    @Transactional
    void getAllHorariosByDiaSemanaIsEqualToSomething() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where diaSemana equals to DEFAULT_DIA_SEMANA
        defaultHorarioShouldBeFound("diaSemana.equals=" + DEFAULT_DIA_SEMANA);

        // Get all the horarioList where diaSemana equals to UPDATED_DIA_SEMANA
        defaultHorarioShouldNotBeFound("diaSemana.equals=" + UPDATED_DIA_SEMANA);
    }

    @Test
    @Transactional
    void getAllHorariosByDiaSemanaIsInShouldWork() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where diaSemana in DEFAULT_DIA_SEMANA or UPDATED_DIA_SEMANA
        defaultHorarioShouldBeFound("diaSemana.in=" + DEFAULT_DIA_SEMANA + "," + UPDATED_DIA_SEMANA);

        // Get all the horarioList where diaSemana equals to UPDATED_DIA_SEMANA
        defaultHorarioShouldNotBeFound("diaSemana.in=" + UPDATED_DIA_SEMANA);
    }

    @Test
    @Transactional
    void getAllHorariosByDiaSemanaIsNullOrNotNull() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList where diaSemana is not null
        defaultHorarioShouldBeFound("diaSemana.specified=true");

        // Get all the horarioList where diaSemana is null
        defaultHorarioShouldNotBeFound("diaSemana.specified=false");
    }

    // @Test
    // @Transactional
    // void getAllHorariosByHorarioIsEqualToSomething() throws Exception {
    //     Horario horario;
    //     if (TestUtil.findAll(em, Horario.class).isEmpty()) {
    //         horarioRepository.saveAndFlush(horario);
    //         horario = HorarioResourceIT.createEntity(em);
    //     } else {
    //         horario = TestUtil.findAll(em, Horario.class).get(0);
    //     }
    //     em.persist(horario);
    //     em.flush();
    //     horario.addHorario(horario);
    //     horarioRepository.saveAndFlush(horario);
    //     Long horarioId = horario.getId();

    //     // Get all the horarioList where horario equals to horarioId
    //     defaultHorarioShouldBeFound("horarioId.equals=" + horarioId);

    //     // Get all the horarioList where horario equals to (horarioId + 1)
    //     defaultHorarioShouldNotBeFound("horarioId.equals=" + (horarioId + 1));
    // }

    @Test
    @Transactional
    void getAllHorariosByLicaoIsEqualToSomething() throws Exception {
        Licao licao;
        if (TestUtil.findAll(em, Licao.class).isEmpty()) {
            horarioRepository.saveAndFlush(horario);
            licao = LicaoResourceIT.createEntity(em);
        } else {
            licao = TestUtil.findAll(em, Licao.class).get(0);
        }
        em.persist(licao);
        em.flush();
        horario.addLicao(licao);
        horarioRepository.saveAndFlush(horario);
        Long licaoId = licao.getId();

        // Get all the horarioList where licao equals to licaoId
        defaultHorarioShouldBeFound("licaoId.equals=" + licaoId);

        // Get all the horarioList where licao equals to (licaoId + 1)
        defaultHorarioShouldNotBeFound("licaoId.equals=" + (licaoId + 1));
    }

    @Test
    @Transactional
    void getAllHorariosByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            horarioRepository.saveAndFlush(horario);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        horario.addAnoLectivo(anoLectivo);
        horarioRepository.saveAndFlush(horario);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the horarioList where anoLectivo equals to anoLectivoId
        defaultHorarioShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the horarioList where anoLectivo equals to (anoLectivoId + 1)
        defaultHorarioShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllHorariosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            horarioRepository.saveAndFlush(horario);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        horario.setUtilizador(utilizador);
        horarioRepository.saveAndFlush(horario);
        Long utilizadorId = utilizador.getId();

        // Get all the horarioList where utilizador equals to utilizadorId
        defaultHorarioShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the horarioList where utilizador equals to (utilizadorId + 1)
        defaultHorarioShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllHorariosByTurmaIsEqualToSomething() throws Exception {
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            horarioRepository.saveAndFlush(horario);
            turma = TurmaResourceIT.createEntity(em);
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(turma);
        em.flush();
        horario.setTurma(turma);
        horarioRepository.saveAndFlush(horario);
        Long turmaId = turma.getId();

        // Get all the horarioList where turma equals to turmaId
        defaultHorarioShouldBeFound("turmaId.equals=" + turmaId);

        // Get all the horarioList where turma equals to (turmaId + 1)
        defaultHorarioShouldNotBeFound("turmaId.equals=" + (turmaId + 1));
    }

    @Test
    @Transactional
    void getAllHorariosByReferenciaIsEqualToSomething() throws Exception {
        Horario referencia;
        if (TestUtil.findAll(em, Horario.class).isEmpty()) {
            horarioRepository.saveAndFlush(horario);
            referencia = HorarioResourceIT.createEntity(em);
        } else {
            referencia = TestUtil.findAll(em, Horario.class).get(0);
        }
        em.persist(referencia);
        em.flush();
        horario.setReferencia(referencia);
        horarioRepository.saveAndFlush(horario);
        Long referenciaId = referencia.getId();

        // Get all the horarioList where referencia equals to referenciaId
        defaultHorarioShouldBeFound("referenciaId.equals=" + referenciaId);

        // Get all the horarioList where referencia equals to (referenciaId + 1)
        defaultHorarioShouldNotBeFound("referenciaId.equals=" + (referenciaId + 1));
    }

    @Test
    @Transactional
    void getAllHorariosByPeriodoIsEqualToSomething() throws Exception {
        PeriodoHorario periodo;
        if (TestUtil.findAll(em, PeriodoHorario.class).isEmpty()) {
            horarioRepository.saveAndFlush(horario);
            periodo = PeriodoHorarioResourceIT.createEntity(em);
        } else {
            periodo = TestUtil.findAll(em, PeriodoHorario.class).get(0);
        }
        em.persist(periodo);
        em.flush();
        horario.setPeriodo(periodo);
        horarioRepository.saveAndFlush(horario);
        Long periodoId = periodo.getId();

        // Get all the horarioList where periodo equals to periodoId
        defaultHorarioShouldBeFound("periodoId.equals=" + periodoId);

        // Get all the horarioList where periodo equals to (periodoId + 1)
        defaultHorarioShouldNotBeFound("periodoId.equals=" + (periodoId + 1));
    }

    @Test
    @Transactional
    void getAllHorariosByDocenteIsEqualToSomething() throws Exception {
        Docente docente;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            horarioRepository.saveAndFlush(horario);
            docente = DocenteResourceIT.createEntity(em);
        } else {
            docente = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(docente);
        em.flush();
        horario.setDocente(docente);
        horarioRepository.saveAndFlush(horario);
        Long docenteId = docente.getId();

        // Get all the horarioList where docente equals to docenteId
        defaultHorarioShouldBeFound("docenteId.equals=" + docenteId);

        // Get all the horarioList where docente equals to (docenteId + 1)
        defaultHorarioShouldNotBeFound("docenteId.equals=" + (docenteId + 1));
    }

    @Test
    @Transactional
    void getAllHorariosByDisciplinaCurricularIsEqualToSomething() throws Exception {
        DisciplinaCurricular disciplinaCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            horarioRepository.saveAndFlush(horario);
            disciplinaCurricular = DisciplinaCurricularResourceIT.createEntity(em);
        } else {
            disciplinaCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        em.persist(disciplinaCurricular);
        em.flush();
        horario.setDisciplinaCurricular(disciplinaCurricular);
        horarioRepository.saveAndFlush(horario);
        Long disciplinaCurricularId = disciplinaCurricular.getId();

        // Get all the horarioList where disciplinaCurricular equals to disciplinaCurricularId
        defaultHorarioShouldBeFound("disciplinaCurricularId.equals=" + disciplinaCurricularId);

        // Get all the horarioList where disciplinaCurricular equals to (disciplinaCurricularId + 1)
        defaultHorarioShouldNotBeFound("disciplinaCurricularId.equals=" + (disciplinaCurricularId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHorarioShouldBeFound(String filter) throws Exception {
        restHorarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horario.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta1").value(hasItem(DEFAULT_CHAVE_COMPOSTA_1)))
            .andExpect(jsonPath("$.[*].chaveComposta2").value(hasItem(DEFAULT_CHAVE_COMPOSTA_2)))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA.toString())));

        // Check, that the count call also returns 1
        restHorarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHorarioShouldNotBeFound(String filter) throws Exception {
        restHorarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHorarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHorario() throws Exception {
        // Get the horario
        restHorarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHorario() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();

        // Update the horario
        Horario updatedHorario = horarioRepository.findById(horario.getId()).get();
        // Disconnect from session so that the updates on updatedHorario are not directly saved in db
        em.detach(updatedHorario);
        updatedHorario.chaveComposta1(UPDATED_CHAVE_COMPOSTA_1).chaveComposta2(UPDATED_CHAVE_COMPOSTA_2).diaSemana(UPDATED_DIA_SEMANA);
        HorarioDTO horarioDTO = horarioMapper.toDto(updatedHorario);

        restHorarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, horarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(horarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);
        Horario testHorario = horarioList.get(horarioList.size() - 1);
        assertThat(testHorario.getChaveComposta1()).isEqualTo(UPDATED_CHAVE_COMPOSTA_1);
        assertThat(testHorario.getChaveComposta2()).isEqualTo(UPDATED_CHAVE_COMPOSTA_2);
        assertThat(testHorario.getDiaSemana()).isEqualTo(UPDATED_DIA_SEMANA);
    }

    @Test
    @Transactional
    void putNonExistingHorario() throws Exception {
        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();
        horario.setId(count.incrementAndGet());

        // Create the Horario
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, horarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(horarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHorario() throws Exception {
        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();
        horario.setId(count.incrementAndGet());

        // Create the Horario
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHorarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(horarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHorario() throws Exception {
        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();
        horario.setId(count.incrementAndGet());

        // Create the Horario
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHorarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHorarioWithPatch() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();

        // Update the horario using partial update
        Horario partialUpdatedHorario = new Horario();
        partialUpdatedHorario.setId(horario.getId());

        partialUpdatedHorario.chaveComposta1(UPDATED_CHAVE_COMPOSTA_1);

        restHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHorario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHorario))
            )
            .andExpect(status().isOk());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);
        Horario testHorario = horarioList.get(horarioList.size() - 1);
        assertThat(testHorario.getChaveComposta1()).isEqualTo(UPDATED_CHAVE_COMPOSTA_1);
        assertThat(testHorario.getChaveComposta2()).isEqualTo(DEFAULT_CHAVE_COMPOSTA_2);
        assertThat(testHorario.getDiaSemana()).isEqualTo(DEFAULT_DIA_SEMANA);
    }

    @Test
    @Transactional
    void fullUpdateHorarioWithPatch() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();

        // Update the horario using partial update
        Horario partialUpdatedHorario = new Horario();
        partialUpdatedHorario.setId(horario.getId());

        partialUpdatedHorario
            .chaveComposta1(UPDATED_CHAVE_COMPOSTA_1)
            .chaveComposta2(UPDATED_CHAVE_COMPOSTA_2)
            .diaSemana(UPDATED_DIA_SEMANA);

        restHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHorario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHorario))
            )
            .andExpect(status().isOk());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);
        Horario testHorario = horarioList.get(horarioList.size() - 1);
        assertThat(testHorario.getChaveComposta1()).isEqualTo(UPDATED_CHAVE_COMPOSTA_1);
        assertThat(testHorario.getChaveComposta2()).isEqualTo(UPDATED_CHAVE_COMPOSTA_2);
        assertThat(testHorario.getDiaSemana()).isEqualTo(UPDATED_DIA_SEMANA);
    }

    @Test
    @Transactional
    void patchNonExistingHorario() throws Exception {
        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();
        horario.setId(count.incrementAndGet());

        // Create the Horario
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, horarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(horarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHorario() throws Exception {
        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();
        horario.setId(count.incrementAndGet());

        // Create the Horario
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(horarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHorario() throws Exception {
        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();
        horario.setId(count.incrementAndGet());

        // Create the Horario
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(horarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHorario() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        int databaseSizeBeforeDelete = horarioRepository.findAll().size();

        // Delete the horario
        restHorarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, horario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
