package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.domain.Licao;
import com.ravunana.longonkelo.domain.Ocorrencia;
import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.domain.enumeration.EstadoLicao;
import com.ravunana.longonkelo.repository.LicaoRepository;
import com.ravunana.longonkelo.service.LicaoService;
import com.ravunana.longonkelo.service.criteria.LicaoCriteria;
import com.ravunana.longonkelo.service.dto.LicaoDTO;
import com.ravunana.longonkelo.service.mapper.LicaoMapper;
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
 * Integration tests for the {@link LicaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LicaoResourceIT {

    private static final String DEFAULT_CHAVE_COMPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE_COMPOSTA = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;
    private static final Integer SMALLER_NUMERO = 1 - 1;

    private static final EstadoLicao DEFAULT_ESTADO = EstadoLicao.ADIADA;
    private static final EstadoLicao UPDATED_ESTADO = EstadoLicao.CANCELADA;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/licaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LicaoRepository licaoRepository;

    @Mock
    private LicaoRepository licaoRepositoryMock;

    @Autowired
    private LicaoMapper licaoMapper;

    @Mock
    private LicaoService licaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLicaoMockMvc;

    private Licao licao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Licao createEntity(EntityManager em) {
        Licao licao = new Licao()
            .chaveComposta(DEFAULT_CHAVE_COMPOSTA)
            .numero(DEFAULT_NUMERO)
            .estado(DEFAULT_ESTADO)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        PlanoAula planoAula;
        if (TestUtil.findAll(em, PlanoAula.class).isEmpty()) {
            planoAula = PlanoAulaResourceIT.createEntity(em);
            em.persist(planoAula);
            em.flush();
        } else {
            planoAula = TestUtil.findAll(em, PlanoAula.class).get(0);
        }
        licao.setPlanoAula(planoAula);
        // Add required entity
        Horario horario;
        if (TestUtil.findAll(em, Horario.class).isEmpty()) {
            horario = HorarioResourceIT.createEntity(em);
            em.persist(horario);
            em.flush();
        } else {
            horario = TestUtil.findAll(em, Horario.class).get(0);
        }
        licao.setHorario(horario);
        return licao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Licao createUpdatedEntity(EntityManager em) {
        Licao licao = new Licao()
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .numero(UPDATED_NUMERO)
            .estado(UPDATED_ESTADO)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        PlanoAula planoAula;
        if (TestUtil.findAll(em, PlanoAula.class).isEmpty()) {
            planoAula = PlanoAulaResourceIT.createUpdatedEntity(em);
            em.persist(planoAula);
            em.flush();
        } else {
            planoAula = TestUtil.findAll(em, PlanoAula.class).get(0);
        }
        licao.setPlanoAula(planoAula);
        // Add required entity
        Horario horario;
        if (TestUtil.findAll(em, Horario.class).isEmpty()) {
            horario = HorarioResourceIT.createUpdatedEntity(em);
            em.persist(horario);
            em.flush();
        } else {
            horario = TestUtil.findAll(em, Horario.class).get(0);
        }
        licao.setHorario(horario);
        return licao;
    }

    @BeforeEach
    public void initTest() {
        licao = createEntity(em);
    }

    @Test
    @Transactional
    void createLicao() throws Exception {
        int databaseSizeBeforeCreate = licaoRepository.findAll().size();
        // Create the Licao
        LicaoDTO licaoDTO = licaoMapper.toDto(licao);
        restLicaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Licao in the database
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeCreate + 1);
        Licao testLicao = licaoList.get(licaoList.size() - 1);
        assertThat(testLicao.getChaveComposta()).isEqualTo(DEFAULT_CHAVE_COMPOSTA);
        assertThat(testLicao.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testLicao.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testLicao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createLicaoWithExistingId() throws Exception {
        // Create the Licao with an existing ID
        licao.setId(1L);
        LicaoDTO licaoDTO = licaoMapper.toDto(licao);

        int databaseSizeBeforeCreate = licaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Licao in the database
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = licaoRepository.findAll().size();
        // set the field null
        licao.setNumero(null);

        // Create the Licao, which fails.
        LicaoDTO licaoDTO = licaoMapper.toDto(licao);

        restLicaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licaoDTO)))
            .andExpect(status().isBadRequest());

        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = licaoRepository.findAll().size();
        // set the field null
        licao.setEstado(null);

        // Create the Licao, which fails.
        LicaoDTO licaoDTO = licaoMapper.toDto(licao);

        restLicaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licaoDTO)))
            .andExpect(status().isBadRequest());

        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLicaos() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList
        restLicaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licao.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta").value(hasItem(DEFAULT_CHAVE_COMPOSTA)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLicaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(licaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLicaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(licaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLicaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(licaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLicaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(licaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLicao() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get the licao
        restLicaoMockMvc
            .perform(get(ENTITY_API_URL_ID, licao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(licao.getId().intValue()))
            .andExpect(jsonPath("$.chaveComposta").value(DEFAULT_CHAVE_COMPOSTA))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getLicaosByIdFiltering() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        Long id = licao.getId();

        defaultLicaoShouldBeFound("id.equals=" + id);
        defaultLicaoShouldNotBeFound("id.notEquals=" + id);

        defaultLicaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLicaoShouldNotBeFound("id.greaterThan=" + id);

        defaultLicaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLicaoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLicaosByChaveCompostaIsEqualToSomething() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where chaveComposta equals to DEFAULT_CHAVE_COMPOSTA
        defaultLicaoShouldBeFound("chaveComposta.equals=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the licaoList where chaveComposta equals to UPDATED_CHAVE_COMPOSTA
        defaultLicaoShouldNotBeFound("chaveComposta.equals=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllLicaosByChaveCompostaIsInShouldWork() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where chaveComposta in DEFAULT_CHAVE_COMPOSTA or UPDATED_CHAVE_COMPOSTA
        defaultLicaoShouldBeFound("chaveComposta.in=" + DEFAULT_CHAVE_COMPOSTA + "," + UPDATED_CHAVE_COMPOSTA);

        // Get all the licaoList where chaveComposta equals to UPDATED_CHAVE_COMPOSTA
        defaultLicaoShouldNotBeFound("chaveComposta.in=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllLicaosByChaveCompostaIsNullOrNotNull() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where chaveComposta is not null
        defaultLicaoShouldBeFound("chaveComposta.specified=true");

        // Get all the licaoList where chaveComposta is null
        defaultLicaoShouldNotBeFound("chaveComposta.specified=false");
    }

    @Test
    @Transactional
    void getAllLicaosByChaveCompostaContainsSomething() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where chaveComposta contains DEFAULT_CHAVE_COMPOSTA
        defaultLicaoShouldBeFound("chaveComposta.contains=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the licaoList where chaveComposta contains UPDATED_CHAVE_COMPOSTA
        defaultLicaoShouldNotBeFound("chaveComposta.contains=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllLicaosByChaveCompostaNotContainsSomething() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where chaveComposta does not contain DEFAULT_CHAVE_COMPOSTA
        defaultLicaoShouldNotBeFound("chaveComposta.doesNotContain=" + DEFAULT_CHAVE_COMPOSTA);

        // Get all the licaoList where chaveComposta does not contain UPDATED_CHAVE_COMPOSTA
        defaultLicaoShouldBeFound("chaveComposta.doesNotContain=" + UPDATED_CHAVE_COMPOSTA);
    }

    @Test
    @Transactional
    void getAllLicaosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where numero equals to DEFAULT_NUMERO
        defaultLicaoShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the licaoList where numero equals to UPDATED_NUMERO
        defaultLicaoShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllLicaosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultLicaoShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the licaoList where numero equals to UPDATED_NUMERO
        defaultLicaoShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllLicaosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where numero is not null
        defaultLicaoShouldBeFound("numero.specified=true");

        // Get all the licaoList where numero is null
        defaultLicaoShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    void getAllLicaosByNumeroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where numero is greater than or equal to DEFAULT_NUMERO
        defaultLicaoShouldBeFound("numero.greaterThanOrEqual=" + DEFAULT_NUMERO);

        // Get all the licaoList where numero is greater than or equal to UPDATED_NUMERO
        defaultLicaoShouldNotBeFound("numero.greaterThanOrEqual=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllLicaosByNumeroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where numero is less than or equal to DEFAULT_NUMERO
        defaultLicaoShouldBeFound("numero.lessThanOrEqual=" + DEFAULT_NUMERO);

        // Get all the licaoList where numero is less than or equal to SMALLER_NUMERO
        defaultLicaoShouldNotBeFound("numero.lessThanOrEqual=" + SMALLER_NUMERO);
    }

    @Test
    @Transactional
    void getAllLicaosByNumeroIsLessThanSomething() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where numero is less than DEFAULT_NUMERO
        defaultLicaoShouldNotBeFound("numero.lessThan=" + DEFAULT_NUMERO);

        // Get all the licaoList where numero is less than UPDATED_NUMERO
        defaultLicaoShouldBeFound("numero.lessThan=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllLicaosByNumeroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where numero is greater than DEFAULT_NUMERO
        defaultLicaoShouldNotBeFound("numero.greaterThan=" + DEFAULT_NUMERO);

        // Get all the licaoList where numero is greater than SMALLER_NUMERO
        defaultLicaoShouldBeFound("numero.greaterThan=" + SMALLER_NUMERO);
    }

    @Test
    @Transactional
    void getAllLicaosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where estado equals to DEFAULT_ESTADO
        defaultLicaoShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the licaoList where estado equals to UPDATED_ESTADO
        defaultLicaoShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllLicaosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultLicaoShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the licaoList where estado equals to UPDATED_ESTADO
        defaultLicaoShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllLicaosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        // Get all the licaoList where estado is not null
        defaultLicaoShouldBeFound("estado.specified=true");

        // Get all the licaoList where estado is null
        defaultLicaoShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllLicaosByOcorrenciasIsEqualToSomething() throws Exception {
        Ocorrencia ocorrencias;
        if (TestUtil.findAll(em, Ocorrencia.class).isEmpty()) {
            licaoRepository.saveAndFlush(licao);
            ocorrencias = OcorrenciaResourceIT.createEntity(em);
        } else {
            ocorrencias = TestUtil.findAll(em, Ocorrencia.class).get(0);
        }
        em.persist(ocorrencias);
        em.flush();
        licao.addOcorrencias(ocorrencias);
        licaoRepository.saveAndFlush(licao);
        Long ocorrenciasId = ocorrencias.getId();

        // Get all the licaoList where ocorrencias equals to ocorrenciasId
        defaultLicaoShouldBeFound("ocorrenciasId.equals=" + ocorrenciasId);

        // Get all the licaoList where ocorrencias equals to (ocorrenciasId + 1)
        defaultLicaoShouldNotBeFound("ocorrenciasId.equals=" + (ocorrenciasId + 1));
    }

    @Test
    @Transactional
    void getAllLicaosByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            licaoRepository.saveAndFlush(licao);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        licao.addAnoLectivo(anoLectivo);
        licaoRepository.saveAndFlush(licao);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the licaoList where anoLectivo equals to anoLectivoId
        defaultLicaoShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the licaoList where anoLectivo equals to (anoLectivoId + 1)
        defaultLicaoShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllLicaosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            licaoRepository.saveAndFlush(licao);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        licao.setUtilizador(utilizador);
        licaoRepository.saveAndFlush(licao);
        Long utilizadorId = utilizador.getId();

        // Get all the licaoList where utilizador equals to utilizadorId
        defaultLicaoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the licaoList where utilizador equals to (utilizadorId + 1)
        defaultLicaoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllLicaosByPlanoAulaIsEqualToSomething() throws Exception {
        PlanoAula planoAula;
        if (TestUtil.findAll(em, PlanoAula.class).isEmpty()) {
            licaoRepository.saveAndFlush(licao);
            planoAula = PlanoAulaResourceIT.createEntity(em);
        } else {
            planoAula = TestUtil.findAll(em, PlanoAula.class).get(0);
        }
        em.persist(planoAula);
        em.flush();
        licao.setPlanoAula(planoAula);
        licaoRepository.saveAndFlush(licao);
        Long planoAulaId = planoAula.getId();

        // Get all the licaoList where planoAula equals to planoAulaId
        defaultLicaoShouldBeFound("planoAulaId.equals=" + planoAulaId);

        // Get all the licaoList where planoAula equals to (planoAulaId + 1)
        defaultLicaoShouldNotBeFound("planoAulaId.equals=" + (planoAulaId + 1));
    }

    @Test
    @Transactional
    void getAllLicaosByHorarioIsEqualToSomething() throws Exception {
        Horario horario;
        if (TestUtil.findAll(em, Horario.class).isEmpty()) {
            licaoRepository.saveAndFlush(licao);
            horario = HorarioResourceIT.createEntity(em);
        } else {
            horario = TestUtil.findAll(em, Horario.class).get(0);
        }
        em.persist(horario);
        em.flush();
        licao.setHorario(horario);
        licaoRepository.saveAndFlush(licao);
        Long horarioId = horario.getId();

        // Get all the licaoList where horario equals to horarioId
        defaultLicaoShouldBeFound("horarioId.equals=" + horarioId);

        // Get all the licaoList where horario equals to (horarioId + 1)
        defaultLicaoShouldNotBeFound("horarioId.equals=" + (horarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLicaoShouldBeFound(String filter) throws Exception {
        restLicaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licao.getId().intValue())))
            .andExpect(jsonPath("$.[*].chaveComposta").value(hasItem(DEFAULT_CHAVE_COMPOSTA)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restLicaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLicaoShouldNotBeFound(String filter) throws Exception {
        restLicaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLicaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLicao() throws Exception {
        // Get the licao
        restLicaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLicao() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        int databaseSizeBeforeUpdate = licaoRepository.findAll().size();

        // Update the licao
        Licao updatedLicao = licaoRepository.findById(licao.getId()).get();
        // Disconnect from session so that the updates on updatedLicao are not directly saved in db
        em.detach(updatedLicao);
        updatedLicao.chaveComposta(UPDATED_CHAVE_COMPOSTA).numero(UPDATED_NUMERO).estado(UPDATED_ESTADO).descricao(UPDATED_DESCRICAO);
        LicaoDTO licaoDTO = licaoMapper.toDto(updatedLicao);

        restLicaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, licaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Licao in the database
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeUpdate);
        Licao testLicao = licaoList.get(licaoList.size() - 1);
        assertThat(testLicao.getChaveComposta()).isEqualTo(UPDATED_CHAVE_COMPOSTA);
        assertThat(testLicao.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testLicao.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testLicao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingLicao() throws Exception {
        int databaseSizeBeforeUpdate = licaoRepository.findAll().size();
        licao.setId(count.incrementAndGet());

        // Create the Licao
        LicaoDTO licaoDTO = licaoMapper.toDto(licao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, licaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Licao in the database
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLicao() throws Exception {
        int databaseSizeBeforeUpdate = licaoRepository.findAll().size();
        licao.setId(count.incrementAndGet());

        // Create the Licao
        LicaoDTO licaoDTO = licaoMapper.toDto(licao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Licao in the database
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLicao() throws Exception {
        int databaseSizeBeforeUpdate = licaoRepository.findAll().size();
        licao.setId(count.incrementAndGet());

        // Create the Licao
        LicaoDTO licaoDTO = licaoMapper.toDto(licao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licaoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Licao in the database
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLicaoWithPatch() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        int databaseSizeBeforeUpdate = licaoRepository.findAll().size();

        // Update the licao using partial update
        Licao partialUpdatedLicao = new Licao();
        partialUpdatedLicao.setId(licao.getId());

        partialUpdatedLicao.numero(UPDATED_NUMERO).estado(UPDATED_ESTADO);

        restLicaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLicao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLicao))
            )
            .andExpect(status().isOk());

        // Validate the Licao in the database
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeUpdate);
        Licao testLicao = licaoList.get(licaoList.size() - 1);
        assertThat(testLicao.getChaveComposta()).isEqualTo(DEFAULT_CHAVE_COMPOSTA);
        assertThat(testLicao.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testLicao.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testLicao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateLicaoWithPatch() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        int databaseSizeBeforeUpdate = licaoRepository.findAll().size();

        // Update the licao using partial update
        Licao partialUpdatedLicao = new Licao();
        partialUpdatedLicao.setId(licao.getId());

        partialUpdatedLicao
            .chaveComposta(UPDATED_CHAVE_COMPOSTA)
            .numero(UPDATED_NUMERO)
            .estado(UPDATED_ESTADO)
            .descricao(UPDATED_DESCRICAO);

        restLicaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLicao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLicao))
            )
            .andExpect(status().isOk());

        // Validate the Licao in the database
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeUpdate);
        Licao testLicao = licaoList.get(licaoList.size() - 1);
        assertThat(testLicao.getChaveComposta()).isEqualTo(UPDATED_CHAVE_COMPOSTA);
        assertThat(testLicao.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testLicao.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testLicao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingLicao() throws Exception {
        int databaseSizeBeforeUpdate = licaoRepository.findAll().size();
        licao.setId(count.incrementAndGet());

        // Create the Licao
        LicaoDTO licaoDTO = licaoMapper.toDto(licao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, licaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(licaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Licao in the database
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLicao() throws Exception {
        int databaseSizeBeforeUpdate = licaoRepository.findAll().size();
        licao.setId(count.incrementAndGet());

        // Create the Licao
        LicaoDTO licaoDTO = licaoMapper.toDto(licao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(licaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Licao in the database
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLicao() throws Exception {
        int databaseSizeBeforeUpdate = licaoRepository.findAll().size();
        licao.setId(count.incrementAndGet());

        // Create the Licao
        LicaoDTO licaoDTO = licaoMapper.toDto(licao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(licaoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Licao in the database
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLicao() throws Exception {
        // Initialize the database
        licaoRepository.saveAndFlush(licao);

        int databaseSizeBeforeDelete = licaoRepository.findAll().size();

        // Delete the licao
        restLicaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, licao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Licao> licaoList = licaoRepository.findAll();
        assertThat(licaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
