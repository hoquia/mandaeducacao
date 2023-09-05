package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.AreaFormacao;
import com.ravunana.longonkelo.domain.Classe;
import com.ravunana.longonkelo.domain.NivelEnsino;
import com.ravunana.longonkelo.domain.NivelEnsino;
import com.ravunana.longonkelo.domain.enumeration.UnidadeDuracao;
import com.ravunana.longonkelo.repository.NivelEnsinoRepository;
import com.ravunana.longonkelo.service.NivelEnsinoService;
import com.ravunana.longonkelo.service.criteria.NivelEnsinoCriteria;
import com.ravunana.longonkelo.service.dto.NivelEnsinoDTO;
import com.ravunana.longonkelo.service.mapper.NivelEnsinoMapper;
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
 * Integration tests for the {@link NivelEnsinoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NivelEnsinoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDADE_MINIMA = 0;
    private static final Integer UPDATED_IDADE_MINIMA = 1;
    private static final Integer SMALLER_IDADE_MINIMA = 0 - 1;

    private static final Integer DEFAULT_IDADE_MAXIMA = 0;
    private static final Integer UPDATED_IDADE_MAXIMA = 1;
    private static final Integer SMALLER_IDADE_MAXIMA = 0 - 1;

    private static final Double DEFAULT_DURACAO = 0D;
    private static final Double UPDATED_DURACAO = 1D;
    private static final Double SMALLER_DURACAO = 0D - 1D;

    private static final UnidadeDuracao DEFAULT_UNIDADE_DURACAO = UnidadeDuracao.HORA;
    private static final UnidadeDuracao UPDATED_UNIDADE_DURACAO = UnidadeDuracao.DIA;

    private static final Integer DEFAULT_CLASSE_INICIAL = 0;
    private static final Integer UPDATED_CLASSE_INICIAL = 1;
    private static final Integer SMALLER_CLASSE_INICIAL = 0 - 1;

    private static final Integer DEFAULT_CLASSE_FINAL = 0;
    private static final Integer UPDATED_CLASSE_FINAL = 1;
    private static final Integer SMALLER_CLASSE_FINAL = 0 - 1;

    private static final Integer DEFAULT_CLASSE_EXAME = 1;
    private static final Integer UPDATED_CLASSE_EXAME = 2;
    private static final Integer SMALLER_CLASSE_EXAME = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DISCIPLINA = 1;
    private static final Integer UPDATED_TOTAL_DISCIPLINA = 2;
    private static final Integer SMALLER_TOTAL_DISCIPLINA = 1 - 1;

    private static final String DEFAULT_RESPONSAVEL_TURNO = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL_TURNO = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSAVEL_AREA_FORMACAO = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL_AREA_FORMACAO = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSAVEL_CURSO = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL_CURSO = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSAVEL_DISCIPLINA = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL_DISCIPLINA = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSAVEL_TURMA = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL_TURMA = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSAVEL_GERAL = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL_GERAL = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSAVEL_PEDAGOGICO = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL_PEDAGOGICO = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSAVEL_ADMINISTRATIVO = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL_ADMINISTRATIVO = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSAVEL_SECRETARIA_GERAL = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL_SECRETARIA_GERAL = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_DOCENTE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_DOCENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_DISCENTE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_DISCENTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nivel-ensinos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NivelEnsinoRepository nivelEnsinoRepository;

    @Mock
    private NivelEnsinoRepository nivelEnsinoRepositoryMock;

    @Autowired
    private NivelEnsinoMapper nivelEnsinoMapper;

    @Mock
    private NivelEnsinoService nivelEnsinoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNivelEnsinoMockMvc;

    private NivelEnsino nivelEnsino;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NivelEnsino createEntity(EntityManager em) {
        NivelEnsino nivelEnsino = new NivelEnsino()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .idadeMinima(DEFAULT_IDADE_MINIMA)
            .idadeMaxima(DEFAULT_IDADE_MAXIMA)
            .duracao(DEFAULT_DURACAO)
            .unidadeDuracao(DEFAULT_UNIDADE_DURACAO)
            .classeInicial(DEFAULT_CLASSE_INICIAL)
            .classeFinal(DEFAULT_CLASSE_FINAL)
            .classeExame(DEFAULT_CLASSE_EXAME)
            .totalDisciplina(DEFAULT_TOTAL_DISCIPLINA)
            .responsavelTurno(DEFAULT_RESPONSAVEL_TURNO)
            .responsavelAreaFormacao(DEFAULT_RESPONSAVEL_AREA_FORMACAO)
            .responsavelCurso(DEFAULT_RESPONSAVEL_CURSO)
            .responsavelDisciplina(DEFAULT_RESPONSAVEL_DISCIPLINA)
            .responsavelTurma(DEFAULT_RESPONSAVEL_TURMA)
            .responsavelGeral(DEFAULT_RESPONSAVEL_GERAL)
            .responsavelPedagogico(DEFAULT_RESPONSAVEL_PEDAGOGICO)
            .responsavelAdministrativo(DEFAULT_RESPONSAVEL_ADMINISTRATIVO)
            .responsavelSecretariaGeral(DEFAULT_RESPONSAVEL_SECRETARIA_GERAL)
            .responsavelSecretariaPedagogico(DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO)
            .descricaoDocente(DEFAULT_DESCRICAO_DOCENTE)
            .descricaoDiscente(DEFAULT_DESCRICAO_DISCENTE);
        return nivelEnsino;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NivelEnsino createUpdatedEntity(EntityManager em) {
        NivelEnsino nivelEnsino = new NivelEnsino()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .idadeMinima(UPDATED_IDADE_MINIMA)
            .idadeMaxima(UPDATED_IDADE_MAXIMA)
            .duracao(UPDATED_DURACAO)
            .unidadeDuracao(UPDATED_UNIDADE_DURACAO)
            .classeInicial(UPDATED_CLASSE_INICIAL)
            .classeFinal(UPDATED_CLASSE_FINAL)
            .classeExame(UPDATED_CLASSE_EXAME)
            .totalDisciplina(UPDATED_TOTAL_DISCIPLINA)
            .responsavelTurno(UPDATED_RESPONSAVEL_TURNO)
            .responsavelAreaFormacao(UPDATED_RESPONSAVEL_AREA_FORMACAO)
            .responsavelCurso(UPDATED_RESPONSAVEL_CURSO)
            .responsavelDisciplina(UPDATED_RESPONSAVEL_DISCIPLINA)
            .responsavelTurma(UPDATED_RESPONSAVEL_TURMA)
            .responsavelGeral(UPDATED_RESPONSAVEL_GERAL)
            .responsavelPedagogico(UPDATED_RESPONSAVEL_PEDAGOGICO)
            .responsavelAdministrativo(UPDATED_RESPONSAVEL_ADMINISTRATIVO)
            .responsavelSecretariaGeral(UPDATED_RESPONSAVEL_SECRETARIA_GERAL)
            .responsavelSecretariaPedagogico(UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO)
            .descricaoDocente(UPDATED_DESCRICAO_DOCENTE)
            .descricaoDiscente(UPDATED_DESCRICAO_DISCENTE);
        return nivelEnsino;
    }

    @BeforeEach
    public void initTest() {
        nivelEnsino = createEntity(em);
    }

    @Test
    @Transactional
    void createNivelEnsino() throws Exception {
        int databaseSizeBeforeCreate = nivelEnsinoRepository.findAll().size();
        // Create the NivelEnsino
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(nivelEnsino);
        restNivelEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NivelEnsino in the database
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeCreate + 1);
        NivelEnsino testNivelEnsino = nivelEnsinoList.get(nivelEnsinoList.size() - 1);
        assertThat(testNivelEnsino.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testNivelEnsino.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testNivelEnsino.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testNivelEnsino.getIdadeMinima()).isEqualTo(DEFAULT_IDADE_MINIMA);
        assertThat(testNivelEnsino.getIdadeMaxima()).isEqualTo(DEFAULT_IDADE_MAXIMA);
        assertThat(testNivelEnsino.getDuracao()).isEqualTo(DEFAULT_DURACAO);
        assertThat(testNivelEnsino.getUnidadeDuracao()).isEqualTo(DEFAULT_UNIDADE_DURACAO);
        assertThat(testNivelEnsino.getClasseInicial()).isEqualTo(DEFAULT_CLASSE_INICIAL);
        assertThat(testNivelEnsino.getClasseFinal()).isEqualTo(DEFAULT_CLASSE_FINAL);
        assertThat(testNivelEnsino.getClasseExame()).isEqualTo(DEFAULT_CLASSE_EXAME);
        assertThat(testNivelEnsino.getTotalDisciplina()).isEqualTo(DEFAULT_TOTAL_DISCIPLINA);
        assertThat(testNivelEnsino.getResponsavelTurno()).isEqualTo(DEFAULT_RESPONSAVEL_TURNO);
        assertThat(testNivelEnsino.getResponsavelAreaFormacao()).isEqualTo(DEFAULT_RESPONSAVEL_AREA_FORMACAO);
        assertThat(testNivelEnsino.getResponsavelCurso()).isEqualTo(DEFAULT_RESPONSAVEL_CURSO);
        assertThat(testNivelEnsino.getResponsavelDisciplina()).isEqualTo(DEFAULT_RESPONSAVEL_DISCIPLINA);
        assertThat(testNivelEnsino.getResponsavelTurma()).isEqualTo(DEFAULT_RESPONSAVEL_TURMA);
        assertThat(testNivelEnsino.getResponsavelGeral()).isEqualTo(DEFAULT_RESPONSAVEL_GERAL);
        assertThat(testNivelEnsino.getResponsavelPedagogico()).isEqualTo(DEFAULT_RESPONSAVEL_PEDAGOGICO);
        assertThat(testNivelEnsino.getResponsavelAdministrativo()).isEqualTo(DEFAULT_RESPONSAVEL_ADMINISTRATIVO);
        assertThat(testNivelEnsino.getResponsavelSecretariaGeral()).isEqualTo(DEFAULT_RESPONSAVEL_SECRETARIA_GERAL);
        assertThat(testNivelEnsino.getResponsavelSecretariaPedagogico()).isEqualTo(DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO);
        assertThat(testNivelEnsino.getDescricaoDocente()).isEqualTo(DEFAULT_DESCRICAO_DOCENTE);
        assertThat(testNivelEnsino.getDescricaoDiscente()).isEqualTo(DEFAULT_DESCRICAO_DISCENTE);
    }

    @Test
    @Transactional
    void createNivelEnsinoWithExistingId() throws Exception {
        // Create the NivelEnsino with an existing ID
        nivelEnsino.setId(1L);
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(nivelEnsino);

        int databaseSizeBeforeCreate = nivelEnsinoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNivelEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NivelEnsino in the database
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelEnsinoRepository.findAll().size();
        // set the field null
        nivelEnsino.setCodigo(null);

        // Create the NivelEnsino, which fails.
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(nivelEnsino);

        restNivelEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelEnsinoRepository.findAll().size();
        // set the field null
        nivelEnsino.setNome(null);

        // Create the NivelEnsino, which fails.
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(nivelEnsino);

        restNivelEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnidadeDuracaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelEnsinoRepository.findAll().size();
        // set the field null
        nivelEnsino.setUnidadeDuracao(null);

        // Create the NivelEnsino, which fails.
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(nivelEnsino);

        restNivelEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNivelEnsinos() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList
        restNivelEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nivelEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].idadeMinima").value(hasItem(DEFAULT_IDADE_MINIMA)))
            .andExpect(jsonPath("$.[*].idadeMaxima").value(hasItem(DEFAULT_IDADE_MAXIMA)))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].unidadeDuracao").value(hasItem(DEFAULT_UNIDADE_DURACAO.toString())))
            .andExpect(jsonPath("$.[*].classeInicial").value(hasItem(DEFAULT_CLASSE_INICIAL)))
            .andExpect(jsonPath("$.[*].classeFinal").value(hasItem(DEFAULT_CLASSE_FINAL)))
            .andExpect(jsonPath("$.[*].classeExame").value(hasItem(DEFAULT_CLASSE_EXAME)))
            .andExpect(jsonPath("$.[*].totalDisciplina").value(hasItem(DEFAULT_TOTAL_DISCIPLINA)))
            .andExpect(jsonPath("$.[*].responsavelTurno").value(hasItem(DEFAULT_RESPONSAVEL_TURNO)))
            .andExpect(jsonPath("$.[*].responsavelAreaFormacao").value(hasItem(DEFAULT_RESPONSAVEL_AREA_FORMACAO)))
            .andExpect(jsonPath("$.[*].responsavelCurso").value(hasItem(DEFAULT_RESPONSAVEL_CURSO)))
            .andExpect(jsonPath("$.[*].responsavelDisciplina").value(hasItem(DEFAULT_RESPONSAVEL_DISCIPLINA)))
            .andExpect(jsonPath("$.[*].responsavelTurma").value(hasItem(DEFAULT_RESPONSAVEL_TURMA)))
            .andExpect(jsonPath("$.[*].responsavelGeral").value(hasItem(DEFAULT_RESPONSAVEL_GERAL)))
            .andExpect(jsonPath("$.[*].responsavelPedagogico").value(hasItem(DEFAULT_RESPONSAVEL_PEDAGOGICO)))
            .andExpect(jsonPath("$.[*].responsavelAdministrativo").value(hasItem(DEFAULT_RESPONSAVEL_ADMINISTRATIVO)))
            .andExpect(jsonPath("$.[*].responsavelSecretariaGeral").value(hasItem(DEFAULT_RESPONSAVEL_SECRETARIA_GERAL)))
            .andExpect(jsonPath("$.[*].responsavelSecretariaPedagogico").value(hasItem(DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO)))
            .andExpect(jsonPath("$.[*].descricaoDocente").value(hasItem(DEFAULT_DESCRICAO_DOCENTE)))
            .andExpect(jsonPath("$.[*].descricaoDiscente").value(hasItem(DEFAULT_DESCRICAO_DISCENTE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNivelEnsinosWithEagerRelationshipsIsEnabled() throws Exception {
        when(nivelEnsinoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNivelEnsinoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(nivelEnsinoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNivelEnsinosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(nivelEnsinoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNivelEnsinoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(nivelEnsinoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNivelEnsino() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get the nivelEnsino
        restNivelEnsinoMockMvc
            .perform(get(ENTITY_API_URL_ID, nivelEnsino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nivelEnsino.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.idadeMinima").value(DEFAULT_IDADE_MINIMA))
            .andExpect(jsonPath("$.idadeMaxima").value(DEFAULT_IDADE_MAXIMA))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO.doubleValue()))
            .andExpect(jsonPath("$.unidadeDuracao").value(DEFAULT_UNIDADE_DURACAO.toString()))
            .andExpect(jsonPath("$.classeInicial").value(DEFAULT_CLASSE_INICIAL))
            .andExpect(jsonPath("$.classeFinal").value(DEFAULT_CLASSE_FINAL))
            .andExpect(jsonPath("$.classeExame").value(DEFAULT_CLASSE_EXAME))
            .andExpect(jsonPath("$.totalDisciplina").value(DEFAULT_TOTAL_DISCIPLINA))
            .andExpect(jsonPath("$.responsavelTurno").value(DEFAULT_RESPONSAVEL_TURNO))
            .andExpect(jsonPath("$.responsavelAreaFormacao").value(DEFAULT_RESPONSAVEL_AREA_FORMACAO))
            .andExpect(jsonPath("$.responsavelCurso").value(DEFAULT_RESPONSAVEL_CURSO))
            .andExpect(jsonPath("$.responsavelDisciplina").value(DEFAULT_RESPONSAVEL_DISCIPLINA))
            .andExpect(jsonPath("$.responsavelTurma").value(DEFAULT_RESPONSAVEL_TURMA))
            .andExpect(jsonPath("$.responsavelGeral").value(DEFAULT_RESPONSAVEL_GERAL))
            .andExpect(jsonPath("$.responsavelPedagogico").value(DEFAULT_RESPONSAVEL_PEDAGOGICO))
            .andExpect(jsonPath("$.responsavelAdministrativo").value(DEFAULT_RESPONSAVEL_ADMINISTRATIVO))
            .andExpect(jsonPath("$.responsavelSecretariaGeral").value(DEFAULT_RESPONSAVEL_SECRETARIA_GERAL))
            .andExpect(jsonPath("$.responsavelSecretariaPedagogico").value(DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO))
            .andExpect(jsonPath("$.descricaoDocente").value(DEFAULT_DESCRICAO_DOCENTE))
            .andExpect(jsonPath("$.descricaoDiscente").value(DEFAULT_DESCRICAO_DISCENTE));
    }

    @Test
    @Transactional
    void getNivelEnsinosByIdFiltering() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        Long id = nivelEnsino.getId();

        defaultNivelEnsinoShouldBeFound("id.equals=" + id);
        defaultNivelEnsinoShouldNotBeFound("id.notEquals=" + id);

        defaultNivelEnsinoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNivelEnsinoShouldNotBeFound("id.greaterThan=" + id);

        defaultNivelEnsinoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNivelEnsinoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where codigo equals to DEFAULT_CODIGO
        defaultNivelEnsinoShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the nivelEnsinoList where codigo equals to UPDATED_CODIGO
        defaultNivelEnsinoShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultNivelEnsinoShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the nivelEnsinoList where codigo equals to UPDATED_CODIGO
        defaultNivelEnsinoShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where codigo is not null
        defaultNivelEnsinoShouldBeFound("codigo.specified=true");

        // Get all the nivelEnsinoList where codigo is null
        defaultNivelEnsinoShouldNotBeFound("codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByCodigoContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where codigo contains DEFAULT_CODIGO
        defaultNivelEnsinoShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the nivelEnsinoList where codigo contains UPDATED_CODIGO
        defaultNivelEnsinoShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where codigo does not contain DEFAULT_CODIGO
        defaultNivelEnsinoShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the nivelEnsinoList where codigo does not contain UPDATED_CODIGO
        defaultNivelEnsinoShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where nome equals to DEFAULT_NOME
        defaultNivelEnsinoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the nivelEnsinoList where nome equals to UPDATED_NOME
        defaultNivelEnsinoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultNivelEnsinoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the nivelEnsinoList where nome equals to UPDATED_NOME
        defaultNivelEnsinoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where nome is not null
        defaultNivelEnsinoShouldBeFound("nome.specified=true");

        // Get all the nivelEnsinoList where nome is null
        defaultNivelEnsinoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByNomeContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where nome contains DEFAULT_NOME
        defaultNivelEnsinoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the nivelEnsinoList where nome contains UPDATED_NOME
        defaultNivelEnsinoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where nome does not contain DEFAULT_NOME
        defaultNivelEnsinoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the nivelEnsinoList where nome does not contain UPDATED_NOME
        defaultNivelEnsinoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMinimaIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMinima equals to DEFAULT_IDADE_MINIMA
        defaultNivelEnsinoShouldBeFound("idadeMinima.equals=" + DEFAULT_IDADE_MINIMA);

        // Get all the nivelEnsinoList where idadeMinima equals to UPDATED_IDADE_MINIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMinima.equals=" + UPDATED_IDADE_MINIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMinimaIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMinima in DEFAULT_IDADE_MINIMA or UPDATED_IDADE_MINIMA
        defaultNivelEnsinoShouldBeFound("idadeMinima.in=" + DEFAULT_IDADE_MINIMA + "," + UPDATED_IDADE_MINIMA);

        // Get all the nivelEnsinoList where idadeMinima equals to UPDATED_IDADE_MINIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMinima.in=" + UPDATED_IDADE_MINIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMinimaIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMinima is not null
        defaultNivelEnsinoShouldBeFound("idadeMinima.specified=true");

        // Get all the nivelEnsinoList where idadeMinima is null
        defaultNivelEnsinoShouldNotBeFound("idadeMinima.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMinimaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMinima is greater than or equal to DEFAULT_IDADE_MINIMA
        defaultNivelEnsinoShouldBeFound("idadeMinima.greaterThanOrEqual=" + DEFAULT_IDADE_MINIMA);

        // Get all the nivelEnsinoList where idadeMinima is greater than or equal to UPDATED_IDADE_MINIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMinima.greaterThanOrEqual=" + UPDATED_IDADE_MINIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMinimaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMinima is less than or equal to DEFAULT_IDADE_MINIMA
        defaultNivelEnsinoShouldBeFound("idadeMinima.lessThanOrEqual=" + DEFAULT_IDADE_MINIMA);

        // Get all the nivelEnsinoList where idadeMinima is less than or equal to SMALLER_IDADE_MINIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMinima.lessThanOrEqual=" + SMALLER_IDADE_MINIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMinimaIsLessThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMinima is less than DEFAULT_IDADE_MINIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMinima.lessThan=" + DEFAULT_IDADE_MINIMA);

        // Get all the nivelEnsinoList where idadeMinima is less than UPDATED_IDADE_MINIMA
        defaultNivelEnsinoShouldBeFound("idadeMinima.lessThan=" + UPDATED_IDADE_MINIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMinimaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMinima is greater than DEFAULT_IDADE_MINIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMinima.greaterThan=" + DEFAULT_IDADE_MINIMA);

        // Get all the nivelEnsinoList where idadeMinima is greater than SMALLER_IDADE_MINIMA
        defaultNivelEnsinoShouldBeFound("idadeMinima.greaterThan=" + SMALLER_IDADE_MINIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMaximaIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMaxima equals to DEFAULT_IDADE_MAXIMA
        defaultNivelEnsinoShouldBeFound("idadeMaxima.equals=" + DEFAULT_IDADE_MAXIMA);

        // Get all the nivelEnsinoList where idadeMaxima equals to UPDATED_IDADE_MAXIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMaxima.equals=" + UPDATED_IDADE_MAXIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMaximaIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMaxima in DEFAULT_IDADE_MAXIMA or UPDATED_IDADE_MAXIMA
        defaultNivelEnsinoShouldBeFound("idadeMaxima.in=" + DEFAULT_IDADE_MAXIMA + "," + UPDATED_IDADE_MAXIMA);

        // Get all the nivelEnsinoList where idadeMaxima equals to UPDATED_IDADE_MAXIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMaxima.in=" + UPDATED_IDADE_MAXIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMaximaIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMaxima is not null
        defaultNivelEnsinoShouldBeFound("idadeMaxima.specified=true");

        // Get all the nivelEnsinoList where idadeMaxima is null
        defaultNivelEnsinoShouldNotBeFound("idadeMaxima.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMaximaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMaxima is greater than or equal to DEFAULT_IDADE_MAXIMA
        defaultNivelEnsinoShouldBeFound("idadeMaxima.greaterThanOrEqual=" + DEFAULT_IDADE_MAXIMA);

        // Get all the nivelEnsinoList where idadeMaxima is greater than or equal to UPDATED_IDADE_MAXIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMaxima.greaterThanOrEqual=" + UPDATED_IDADE_MAXIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMaximaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMaxima is less than or equal to DEFAULT_IDADE_MAXIMA
        defaultNivelEnsinoShouldBeFound("idadeMaxima.lessThanOrEqual=" + DEFAULT_IDADE_MAXIMA);

        // Get all the nivelEnsinoList where idadeMaxima is less than or equal to SMALLER_IDADE_MAXIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMaxima.lessThanOrEqual=" + SMALLER_IDADE_MAXIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMaximaIsLessThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMaxima is less than DEFAULT_IDADE_MAXIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMaxima.lessThan=" + DEFAULT_IDADE_MAXIMA);

        // Get all the nivelEnsinoList where idadeMaxima is less than UPDATED_IDADE_MAXIMA
        defaultNivelEnsinoShouldBeFound("idadeMaxima.lessThan=" + UPDATED_IDADE_MAXIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByIdadeMaximaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where idadeMaxima is greater than DEFAULT_IDADE_MAXIMA
        defaultNivelEnsinoShouldNotBeFound("idadeMaxima.greaterThan=" + DEFAULT_IDADE_MAXIMA);

        // Get all the nivelEnsinoList where idadeMaxima is greater than SMALLER_IDADE_MAXIMA
        defaultNivelEnsinoShouldBeFound("idadeMaxima.greaterThan=" + SMALLER_IDADE_MAXIMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDuracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where duracao equals to DEFAULT_DURACAO
        defaultNivelEnsinoShouldBeFound("duracao.equals=" + DEFAULT_DURACAO);

        // Get all the nivelEnsinoList where duracao equals to UPDATED_DURACAO
        defaultNivelEnsinoShouldNotBeFound("duracao.equals=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDuracaoIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where duracao in DEFAULT_DURACAO or UPDATED_DURACAO
        defaultNivelEnsinoShouldBeFound("duracao.in=" + DEFAULT_DURACAO + "," + UPDATED_DURACAO);

        // Get all the nivelEnsinoList where duracao equals to UPDATED_DURACAO
        defaultNivelEnsinoShouldNotBeFound("duracao.in=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDuracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where duracao is not null
        defaultNivelEnsinoShouldBeFound("duracao.specified=true");

        // Get all the nivelEnsinoList where duracao is null
        defaultNivelEnsinoShouldNotBeFound("duracao.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDuracaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where duracao is greater than or equal to DEFAULT_DURACAO
        defaultNivelEnsinoShouldBeFound("duracao.greaterThanOrEqual=" + DEFAULT_DURACAO);

        // Get all the nivelEnsinoList where duracao is greater than or equal to UPDATED_DURACAO
        defaultNivelEnsinoShouldNotBeFound("duracao.greaterThanOrEqual=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDuracaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where duracao is less than or equal to DEFAULT_DURACAO
        defaultNivelEnsinoShouldBeFound("duracao.lessThanOrEqual=" + DEFAULT_DURACAO);

        // Get all the nivelEnsinoList where duracao is less than or equal to SMALLER_DURACAO
        defaultNivelEnsinoShouldNotBeFound("duracao.lessThanOrEqual=" + SMALLER_DURACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDuracaoIsLessThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where duracao is less than DEFAULT_DURACAO
        defaultNivelEnsinoShouldNotBeFound("duracao.lessThan=" + DEFAULT_DURACAO);

        // Get all the nivelEnsinoList where duracao is less than UPDATED_DURACAO
        defaultNivelEnsinoShouldBeFound("duracao.lessThan=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDuracaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where duracao is greater than DEFAULT_DURACAO
        defaultNivelEnsinoShouldNotBeFound("duracao.greaterThan=" + DEFAULT_DURACAO);

        // Get all the nivelEnsinoList where duracao is greater than SMALLER_DURACAO
        defaultNivelEnsinoShouldBeFound("duracao.greaterThan=" + SMALLER_DURACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByUnidadeDuracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where unidadeDuracao equals to DEFAULT_UNIDADE_DURACAO
        defaultNivelEnsinoShouldBeFound("unidadeDuracao.equals=" + DEFAULT_UNIDADE_DURACAO);

        // Get all the nivelEnsinoList where unidadeDuracao equals to UPDATED_UNIDADE_DURACAO
        defaultNivelEnsinoShouldNotBeFound("unidadeDuracao.equals=" + UPDATED_UNIDADE_DURACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByUnidadeDuracaoIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where unidadeDuracao in DEFAULT_UNIDADE_DURACAO or UPDATED_UNIDADE_DURACAO
        defaultNivelEnsinoShouldBeFound("unidadeDuracao.in=" + DEFAULT_UNIDADE_DURACAO + "," + UPDATED_UNIDADE_DURACAO);

        // Get all the nivelEnsinoList where unidadeDuracao equals to UPDATED_UNIDADE_DURACAO
        defaultNivelEnsinoShouldNotBeFound("unidadeDuracao.in=" + UPDATED_UNIDADE_DURACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByUnidadeDuracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where unidadeDuracao is not null
        defaultNivelEnsinoShouldBeFound("unidadeDuracao.specified=true");

        // Get all the nivelEnsinoList where unidadeDuracao is null
        defaultNivelEnsinoShouldNotBeFound("unidadeDuracao.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseInicialIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeInicial equals to DEFAULT_CLASSE_INICIAL
        defaultNivelEnsinoShouldBeFound("classeInicial.equals=" + DEFAULT_CLASSE_INICIAL);

        // Get all the nivelEnsinoList where classeInicial equals to UPDATED_CLASSE_INICIAL
        defaultNivelEnsinoShouldNotBeFound("classeInicial.equals=" + UPDATED_CLASSE_INICIAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseInicialIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeInicial in DEFAULT_CLASSE_INICIAL or UPDATED_CLASSE_INICIAL
        defaultNivelEnsinoShouldBeFound("classeInicial.in=" + DEFAULT_CLASSE_INICIAL + "," + UPDATED_CLASSE_INICIAL);

        // Get all the nivelEnsinoList where classeInicial equals to UPDATED_CLASSE_INICIAL
        defaultNivelEnsinoShouldNotBeFound("classeInicial.in=" + UPDATED_CLASSE_INICIAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseInicialIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeInicial is not null
        defaultNivelEnsinoShouldBeFound("classeInicial.specified=true");

        // Get all the nivelEnsinoList where classeInicial is null
        defaultNivelEnsinoShouldNotBeFound("classeInicial.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseInicialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeInicial is greater than or equal to DEFAULT_CLASSE_INICIAL
        defaultNivelEnsinoShouldBeFound("classeInicial.greaterThanOrEqual=" + DEFAULT_CLASSE_INICIAL);

        // Get all the nivelEnsinoList where classeInicial is greater than or equal to UPDATED_CLASSE_INICIAL
        defaultNivelEnsinoShouldNotBeFound("classeInicial.greaterThanOrEqual=" + UPDATED_CLASSE_INICIAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseInicialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeInicial is less than or equal to DEFAULT_CLASSE_INICIAL
        defaultNivelEnsinoShouldBeFound("classeInicial.lessThanOrEqual=" + DEFAULT_CLASSE_INICIAL);

        // Get all the nivelEnsinoList where classeInicial is less than or equal to SMALLER_CLASSE_INICIAL
        defaultNivelEnsinoShouldNotBeFound("classeInicial.lessThanOrEqual=" + SMALLER_CLASSE_INICIAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseInicialIsLessThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeInicial is less than DEFAULT_CLASSE_INICIAL
        defaultNivelEnsinoShouldNotBeFound("classeInicial.lessThan=" + DEFAULT_CLASSE_INICIAL);

        // Get all the nivelEnsinoList where classeInicial is less than UPDATED_CLASSE_INICIAL
        defaultNivelEnsinoShouldBeFound("classeInicial.lessThan=" + UPDATED_CLASSE_INICIAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseInicialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeInicial is greater than DEFAULT_CLASSE_INICIAL
        defaultNivelEnsinoShouldNotBeFound("classeInicial.greaterThan=" + DEFAULT_CLASSE_INICIAL);

        // Get all the nivelEnsinoList where classeInicial is greater than SMALLER_CLASSE_INICIAL
        defaultNivelEnsinoShouldBeFound("classeInicial.greaterThan=" + SMALLER_CLASSE_INICIAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeFinal equals to DEFAULT_CLASSE_FINAL
        defaultNivelEnsinoShouldBeFound("classeFinal.equals=" + DEFAULT_CLASSE_FINAL);

        // Get all the nivelEnsinoList where classeFinal equals to UPDATED_CLASSE_FINAL
        defaultNivelEnsinoShouldNotBeFound("classeFinal.equals=" + UPDATED_CLASSE_FINAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseFinalIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeFinal in DEFAULT_CLASSE_FINAL or UPDATED_CLASSE_FINAL
        defaultNivelEnsinoShouldBeFound("classeFinal.in=" + DEFAULT_CLASSE_FINAL + "," + UPDATED_CLASSE_FINAL);

        // Get all the nivelEnsinoList where classeFinal equals to UPDATED_CLASSE_FINAL
        defaultNivelEnsinoShouldNotBeFound("classeFinal.in=" + UPDATED_CLASSE_FINAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeFinal is not null
        defaultNivelEnsinoShouldBeFound("classeFinal.specified=true");

        // Get all the nivelEnsinoList where classeFinal is null
        defaultNivelEnsinoShouldNotBeFound("classeFinal.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseFinalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeFinal is greater than or equal to DEFAULT_CLASSE_FINAL
        defaultNivelEnsinoShouldBeFound("classeFinal.greaterThanOrEqual=" + DEFAULT_CLASSE_FINAL);

        // Get all the nivelEnsinoList where classeFinal is greater than or equal to UPDATED_CLASSE_FINAL
        defaultNivelEnsinoShouldNotBeFound("classeFinal.greaterThanOrEqual=" + UPDATED_CLASSE_FINAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseFinalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeFinal is less than or equal to DEFAULT_CLASSE_FINAL
        defaultNivelEnsinoShouldBeFound("classeFinal.lessThanOrEqual=" + DEFAULT_CLASSE_FINAL);

        // Get all the nivelEnsinoList where classeFinal is less than or equal to SMALLER_CLASSE_FINAL
        defaultNivelEnsinoShouldNotBeFound("classeFinal.lessThanOrEqual=" + SMALLER_CLASSE_FINAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseFinalIsLessThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeFinal is less than DEFAULT_CLASSE_FINAL
        defaultNivelEnsinoShouldNotBeFound("classeFinal.lessThan=" + DEFAULT_CLASSE_FINAL);

        // Get all the nivelEnsinoList where classeFinal is less than UPDATED_CLASSE_FINAL
        defaultNivelEnsinoShouldBeFound("classeFinal.lessThan=" + UPDATED_CLASSE_FINAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseFinalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeFinal is greater than DEFAULT_CLASSE_FINAL
        defaultNivelEnsinoShouldNotBeFound("classeFinal.greaterThan=" + DEFAULT_CLASSE_FINAL);

        // Get all the nivelEnsinoList where classeFinal is greater than SMALLER_CLASSE_FINAL
        defaultNivelEnsinoShouldBeFound("classeFinal.greaterThan=" + SMALLER_CLASSE_FINAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseExameIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeExame equals to DEFAULT_CLASSE_EXAME
        defaultNivelEnsinoShouldBeFound("classeExame.equals=" + DEFAULT_CLASSE_EXAME);

        // Get all the nivelEnsinoList where classeExame equals to UPDATED_CLASSE_EXAME
        defaultNivelEnsinoShouldNotBeFound("classeExame.equals=" + UPDATED_CLASSE_EXAME);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseExameIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeExame in DEFAULT_CLASSE_EXAME or UPDATED_CLASSE_EXAME
        defaultNivelEnsinoShouldBeFound("classeExame.in=" + DEFAULT_CLASSE_EXAME + "," + UPDATED_CLASSE_EXAME);

        // Get all the nivelEnsinoList where classeExame equals to UPDATED_CLASSE_EXAME
        defaultNivelEnsinoShouldNotBeFound("classeExame.in=" + UPDATED_CLASSE_EXAME);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseExameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeExame is not null
        defaultNivelEnsinoShouldBeFound("classeExame.specified=true");

        // Get all the nivelEnsinoList where classeExame is null
        defaultNivelEnsinoShouldNotBeFound("classeExame.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseExameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeExame is greater than or equal to DEFAULT_CLASSE_EXAME
        defaultNivelEnsinoShouldBeFound("classeExame.greaterThanOrEqual=" + DEFAULT_CLASSE_EXAME);

        // Get all the nivelEnsinoList where classeExame is greater than or equal to UPDATED_CLASSE_EXAME
        defaultNivelEnsinoShouldNotBeFound("classeExame.greaterThanOrEqual=" + UPDATED_CLASSE_EXAME);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseExameIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeExame is less than or equal to DEFAULT_CLASSE_EXAME
        defaultNivelEnsinoShouldBeFound("classeExame.lessThanOrEqual=" + DEFAULT_CLASSE_EXAME);

        // Get all the nivelEnsinoList where classeExame is less than or equal to SMALLER_CLASSE_EXAME
        defaultNivelEnsinoShouldNotBeFound("classeExame.lessThanOrEqual=" + SMALLER_CLASSE_EXAME);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseExameIsLessThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeExame is less than DEFAULT_CLASSE_EXAME
        defaultNivelEnsinoShouldNotBeFound("classeExame.lessThan=" + DEFAULT_CLASSE_EXAME);

        // Get all the nivelEnsinoList where classeExame is less than UPDATED_CLASSE_EXAME
        defaultNivelEnsinoShouldBeFound("classeExame.lessThan=" + UPDATED_CLASSE_EXAME);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClasseExameIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where classeExame is greater than DEFAULT_CLASSE_EXAME
        defaultNivelEnsinoShouldNotBeFound("classeExame.greaterThan=" + DEFAULT_CLASSE_EXAME);

        // Get all the nivelEnsinoList where classeExame is greater than SMALLER_CLASSE_EXAME
        defaultNivelEnsinoShouldBeFound("classeExame.greaterThan=" + SMALLER_CLASSE_EXAME);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByTotalDisciplinaIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where totalDisciplina equals to DEFAULT_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldBeFound("totalDisciplina.equals=" + DEFAULT_TOTAL_DISCIPLINA);

        // Get all the nivelEnsinoList where totalDisciplina equals to UPDATED_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldNotBeFound("totalDisciplina.equals=" + UPDATED_TOTAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByTotalDisciplinaIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where totalDisciplina in DEFAULT_TOTAL_DISCIPLINA or UPDATED_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldBeFound("totalDisciplina.in=" + DEFAULT_TOTAL_DISCIPLINA + "," + UPDATED_TOTAL_DISCIPLINA);

        // Get all the nivelEnsinoList where totalDisciplina equals to UPDATED_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldNotBeFound("totalDisciplina.in=" + UPDATED_TOTAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByTotalDisciplinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where totalDisciplina is not null
        defaultNivelEnsinoShouldBeFound("totalDisciplina.specified=true");

        // Get all the nivelEnsinoList where totalDisciplina is null
        defaultNivelEnsinoShouldNotBeFound("totalDisciplina.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByTotalDisciplinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where totalDisciplina is greater than or equal to DEFAULT_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldBeFound("totalDisciplina.greaterThanOrEqual=" + DEFAULT_TOTAL_DISCIPLINA);

        // Get all the nivelEnsinoList where totalDisciplina is greater than or equal to UPDATED_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldNotBeFound("totalDisciplina.greaterThanOrEqual=" + UPDATED_TOTAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByTotalDisciplinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where totalDisciplina is less than or equal to DEFAULT_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldBeFound("totalDisciplina.lessThanOrEqual=" + DEFAULT_TOTAL_DISCIPLINA);

        // Get all the nivelEnsinoList where totalDisciplina is less than or equal to SMALLER_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldNotBeFound("totalDisciplina.lessThanOrEqual=" + SMALLER_TOTAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByTotalDisciplinaIsLessThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where totalDisciplina is less than DEFAULT_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldNotBeFound("totalDisciplina.lessThan=" + DEFAULT_TOTAL_DISCIPLINA);

        // Get all the nivelEnsinoList where totalDisciplina is less than UPDATED_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldBeFound("totalDisciplina.lessThan=" + UPDATED_TOTAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByTotalDisciplinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where totalDisciplina is greater than DEFAULT_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldNotBeFound("totalDisciplina.greaterThan=" + DEFAULT_TOTAL_DISCIPLINA);

        // Get all the nivelEnsinoList where totalDisciplina is greater than SMALLER_TOTAL_DISCIPLINA
        defaultNivelEnsinoShouldBeFound("totalDisciplina.greaterThan=" + SMALLER_TOTAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelTurnoIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelTurno equals to DEFAULT_RESPONSAVEL_TURNO
        defaultNivelEnsinoShouldBeFound("responsavelTurno.equals=" + DEFAULT_RESPONSAVEL_TURNO);

        // Get all the nivelEnsinoList where responsavelTurno equals to UPDATED_RESPONSAVEL_TURNO
        defaultNivelEnsinoShouldNotBeFound("responsavelTurno.equals=" + UPDATED_RESPONSAVEL_TURNO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelTurnoIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelTurno in DEFAULT_RESPONSAVEL_TURNO or UPDATED_RESPONSAVEL_TURNO
        defaultNivelEnsinoShouldBeFound("responsavelTurno.in=" + DEFAULT_RESPONSAVEL_TURNO + "," + UPDATED_RESPONSAVEL_TURNO);

        // Get all the nivelEnsinoList where responsavelTurno equals to UPDATED_RESPONSAVEL_TURNO
        defaultNivelEnsinoShouldNotBeFound("responsavelTurno.in=" + UPDATED_RESPONSAVEL_TURNO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelTurnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelTurno is not null
        defaultNivelEnsinoShouldBeFound("responsavelTurno.specified=true");

        // Get all the nivelEnsinoList where responsavelTurno is null
        defaultNivelEnsinoShouldNotBeFound("responsavelTurno.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelTurnoContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelTurno contains DEFAULT_RESPONSAVEL_TURNO
        defaultNivelEnsinoShouldBeFound("responsavelTurno.contains=" + DEFAULT_RESPONSAVEL_TURNO);

        // Get all the nivelEnsinoList where responsavelTurno contains UPDATED_RESPONSAVEL_TURNO
        defaultNivelEnsinoShouldNotBeFound("responsavelTurno.contains=" + UPDATED_RESPONSAVEL_TURNO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelTurnoNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelTurno does not contain DEFAULT_RESPONSAVEL_TURNO
        defaultNivelEnsinoShouldNotBeFound("responsavelTurno.doesNotContain=" + DEFAULT_RESPONSAVEL_TURNO);

        // Get all the nivelEnsinoList where responsavelTurno does not contain UPDATED_RESPONSAVEL_TURNO
        defaultNivelEnsinoShouldBeFound("responsavelTurno.doesNotContain=" + UPDATED_RESPONSAVEL_TURNO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelAreaFormacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelAreaFormacao equals to DEFAULT_RESPONSAVEL_AREA_FORMACAO
        defaultNivelEnsinoShouldBeFound("responsavelAreaFormacao.equals=" + DEFAULT_RESPONSAVEL_AREA_FORMACAO);

        // Get all the nivelEnsinoList where responsavelAreaFormacao equals to UPDATED_RESPONSAVEL_AREA_FORMACAO
        defaultNivelEnsinoShouldNotBeFound("responsavelAreaFormacao.equals=" + UPDATED_RESPONSAVEL_AREA_FORMACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelAreaFormacaoIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelAreaFormacao in DEFAULT_RESPONSAVEL_AREA_FORMACAO or UPDATED_RESPONSAVEL_AREA_FORMACAO
        defaultNivelEnsinoShouldBeFound(
            "responsavelAreaFormacao.in=" + DEFAULT_RESPONSAVEL_AREA_FORMACAO + "," + UPDATED_RESPONSAVEL_AREA_FORMACAO
        );

        // Get all the nivelEnsinoList where responsavelAreaFormacao equals to UPDATED_RESPONSAVEL_AREA_FORMACAO
        defaultNivelEnsinoShouldNotBeFound("responsavelAreaFormacao.in=" + UPDATED_RESPONSAVEL_AREA_FORMACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelAreaFormacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelAreaFormacao is not null
        defaultNivelEnsinoShouldBeFound("responsavelAreaFormacao.specified=true");

        // Get all the nivelEnsinoList where responsavelAreaFormacao is null
        defaultNivelEnsinoShouldNotBeFound("responsavelAreaFormacao.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelAreaFormacaoContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelAreaFormacao contains DEFAULT_RESPONSAVEL_AREA_FORMACAO
        defaultNivelEnsinoShouldBeFound("responsavelAreaFormacao.contains=" + DEFAULT_RESPONSAVEL_AREA_FORMACAO);

        // Get all the nivelEnsinoList where responsavelAreaFormacao contains UPDATED_RESPONSAVEL_AREA_FORMACAO
        defaultNivelEnsinoShouldNotBeFound("responsavelAreaFormacao.contains=" + UPDATED_RESPONSAVEL_AREA_FORMACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelAreaFormacaoNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelAreaFormacao does not contain DEFAULT_RESPONSAVEL_AREA_FORMACAO
        defaultNivelEnsinoShouldNotBeFound("responsavelAreaFormacao.doesNotContain=" + DEFAULT_RESPONSAVEL_AREA_FORMACAO);

        // Get all the nivelEnsinoList where responsavelAreaFormacao does not contain UPDATED_RESPONSAVEL_AREA_FORMACAO
        defaultNivelEnsinoShouldBeFound("responsavelAreaFormacao.doesNotContain=" + UPDATED_RESPONSAVEL_AREA_FORMACAO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelCursoIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelCurso equals to DEFAULT_RESPONSAVEL_CURSO
        defaultNivelEnsinoShouldBeFound("responsavelCurso.equals=" + DEFAULT_RESPONSAVEL_CURSO);

        // Get all the nivelEnsinoList where responsavelCurso equals to UPDATED_RESPONSAVEL_CURSO
        defaultNivelEnsinoShouldNotBeFound("responsavelCurso.equals=" + UPDATED_RESPONSAVEL_CURSO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelCursoIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelCurso in DEFAULT_RESPONSAVEL_CURSO or UPDATED_RESPONSAVEL_CURSO
        defaultNivelEnsinoShouldBeFound("responsavelCurso.in=" + DEFAULT_RESPONSAVEL_CURSO + "," + UPDATED_RESPONSAVEL_CURSO);

        // Get all the nivelEnsinoList where responsavelCurso equals to UPDATED_RESPONSAVEL_CURSO
        defaultNivelEnsinoShouldNotBeFound("responsavelCurso.in=" + UPDATED_RESPONSAVEL_CURSO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelCursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelCurso is not null
        defaultNivelEnsinoShouldBeFound("responsavelCurso.specified=true");

        // Get all the nivelEnsinoList where responsavelCurso is null
        defaultNivelEnsinoShouldNotBeFound("responsavelCurso.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelCursoContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelCurso contains DEFAULT_RESPONSAVEL_CURSO
        defaultNivelEnsinoShouldBeFound("responsavelCurso.contains=" + DEFAULT_RESPONSAVEL_CURSO);

        // Get all the nivelEnsinoList where responsavelCurso contains UPDATED_RESPONSAVEL_CURSO
        defaultNivelEnsinoShouldNotBeFound("responsavelCurso.contains=" + UPDATED_RESPONSAVEL_CURSO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelCursoNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelCurso does not contain DEFAULT_RESPONSAVEL_CURSO
        defaultNivelEnsinoShouldNotBeFound("responsavelCurso.doesNotContain=" + DEFAULT_RESPONSAVEL_CURSO);

        // Get all the nivelEnsinoList where responsavelCurso does not contain UPDATED_RESPONSAVEL_CURSO
        defaultNivelEnsinoShouldBeFound("responsavelCurso.doesNotContain=" + UPDATED_RESPONSAVEL_CURSO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelDisciplinaIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelDisciplina equals to DEFAULT_RESPONSAVEL_DISCIPLINA
        defaultNivelEnsinoShouldBeFound("responsavelDisciplina.equals=" + DEFAULT_RESPONSAVEL_DISCIPLINA);

        // Get all the nivelEnsinoList where responsavelDisciplina equals to UPDATED_RESPONSAVEL_DISCIPLINA
        defaultNivelEnsinoShouldNotBeFound("responsavelDisciplina.equals=" + UPDATED_RESPONSAVEL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelDisciplinaIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelDisciplina in DEFAULT_RESPONSAVEL_DISCIPLINA or UPDATED_RESPONSAVEL_DISCIPLINA
        defaultNivelEnsinoShouldBeFound(
            "responsavelDisciplina.in=" + DEFAULT_RESPONSAVEL_DISCIPLINA + "," + UPDATED_RESPONSAVEL_DISCIPLINA
        );

        // Get all the nivelEnsinoList where responsavelDisciplina equals to UPDATED_RESPONSAVEL_DISCIPLINA
        defaultNivelEnsinoShouldNotBeFound("responsavelDisciplina.in=" + UPDATED_RESPONSAVEL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelDisciplinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelDisciplina is not null
        defaultNivelEnsinoShouldBeFound("responsavelDisciplina.specified=true");

        // Get all the nivelEnsinoList where responsavelDisciplina is null
        defaultNivelEnsinoShouldNotBeFound("responsavelDisciplina.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelDisciplinaContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelDisciplina contains DEFAULT_RESPONSAVEL_DISCIPLINA
        defaultNivelEnsinoShouldBeFound("responsavelDisciplina.contains=" + DEFAULT_RESPONSAVEL_DISCIPLINA);

        // Get all the nivelEnsinoList where responsavelDisciplina contains UPDATED_RESPONSAVEL_DISCIPLINA
        defaultNivelEnsinoShouldNotBeFound("responsavelDisciplina.contains=" + UPDATED_RESPONSAVEL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelDisciplinaNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelDisciplina does not contain DEFAULT_RESPONSAVEL_DISCIPLINA
        defaultNivelEnsinoShouldNotBeFound("responsavelDisciplina.doesNotContain=" + DEFAULT_RESPONSAVEL_DISCIPLINA);

        // Get all the nivelEnsinoList where responsavelDisciplina does not contain UPDATED_RESPONSAVEL_DISCIPLINA
        defaultNivelEnsinoShouldBeFound("responsavelDisciplina.doesNotContain=" + UPDATED_RESPONSAVEL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelTurmaIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelTurma equals to DEFAULT_RESPONSAVEL_TURMA
        defaultNivelEnsinoShouldBeFound("responsavelTurma.equals=" + DEFAULT_RESPONSAVEL_TURMA);

        // Get all the nivelEnsinoList where responsavelTurma equals to UPDATED_RESPONSAVEL_TURMA
        defaultNivelEnsinoShouldNotBeFound("responsavelTurma.equals=" + UPDATED_RESPONSAVEL_TURMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelTurmaIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelTurma in DEFAULT_RESPONSAVEL_TURMA or UPDATED_RESPONSAVEL_TURMA
        defaultNivelEnsinoShouldBeFound("responsavelTurma.in=" + DEFAULT_RESPONSAVEL_TURMA + "," + UPDATED_RESPONSAVEL_TURMA);

        // Get all the nivelEnsinoList where responsavelTurma equals to UPDATED_RESPONSAVEL_TURMA
        defaultNivelEnsinoShouldNotBeFound("responsavelTurma.in=" + UPDATED_RESPONSAVEL_TURMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelTurmaIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelTurma is not null
        defaultNivelEnsinoShouldBeFound("responsavelTurma.specified=true");

        // Get all the nivelEnsinoList where responsavelTurma is null
        defaultNivelEnsinoShouldNotBeFound("responsavelTurma.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelTurmaContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelTurma contains DEFAULT_RESPONSAVEL_TURMA
        defaultNivelEnsinoShouldBeFound("responsavelTurma.contains=" + DEFAULT_RESPONSAVEL_TURMA);

        // Get all the nivelEnsinoList where responsavelTurma contains UPDATED_RESPONSAVEL_TURMA
        defaultNivelEnsinoShouldNotBeFound("responsavelTurma.contains=" + UPDATED_RESPONSAVEL_TURMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelTurmaNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelTurma does not contain DEFAULT_RESPONSAVEL_TURMA
        defaultNivelEnsinoShouldNotBeFound("responsavelTurma.doesNotContain=" + DEFAULT_RESPONSAVEL_TURMA);

        // Get all the nivelEnsinoList where responsavelTurma does not contain UPDATED_RESPONSAVEL_TURMA
        defaultNivelEnsinoShouldBeFound("responsavelTurma.doesNotContain=" + UPDATED_RESPONSAVEL_TURMA);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelGeralIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelGeral equals to DEFAULT_RESPONSAVEL_GERAL
        defaultNivelEnsinoShouldBeFound("responsavelGeral.equals=" + DEFAULT_RESPONSAVEL_GERAL);

        // Get all the nivelEnsinoList where responsavelGeral equals to UPDATED_RESPONSAVEL_GERAL
        defaultNivelEnsinoShouldNotBeFound("responsavelGeral.equals=" + UPDATED_RESPONSAVEL_GERAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelGeralIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelGeral in DEFAULT_RESPONSAVEL_GERAL or UPDATED_RESPONSAVEL_GERAL
        defaultNivelEnsinoShouldBeFound("responsavelGeral.in=" + DEFAULT_RESPONSAVEL_GERAL + "," + UPDATED_RESPONSAVEL_GERAL);

        // Get all the nivelEnsinoList where responsavelGeral equals to UPDATED_RESPONSAVEL_GERAL
        defaultNivelEnsinoShouldNotBeFound("responsavelGeral.in=" + UPDATED_RESPONSAVEL_GERAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelGeralIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelGeral is not null
        defaultNivelEnsinoShouldBeFound("responsavelGeral.specified=true");

        // Get all the nivelEnsinoList where responsavelGeral is null
        defaultNivelEnsinoShouldNotBeFound("responsavelGeral.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelGeralContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelGeral contains DEFAULT_RESPONSAVEL_GERAL
        defaultNivelEnsinoShouldBeFound("responsavelGeral.contains=" + DEFAULT_RESPONSAVEL_GERAL);

        // Get all the nivelEnsinoList where responsavelGeral contains UPDATED_RESPONSAVEL_GERAL
        defaultNivelEnsinoShouldNotBeFound("responsavelGeral.contains=" + UPDATED_RESPONSAVEL_GERAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelGeralNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelGeral does not contain DEFAULT_RESPONSAVEL_GERAL
        defaultNivelEnsinoShouldNotBeFound("responsavelGeral.doesNotContain=" + DEFAULT_RESPONSAVEL_GERAL);

        // Get all the nivelEnsinoList where responsavelGeral does not contain UPDATED_RESPONSAVEL_GERAL
        defaultNivelEnsinoShouldBeFound("responsavelGeral.doesNotContain=" + UPDATED_RESPONSAVEL_GERAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelPedagogicoIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelPedagogico equals to DEFAULT_RESPONSAVEL_PEDAGOGICO
        defaultNivelEnsinoShouldBeFound("responsavelPedagogico.equals=" + DEFAULT_RESPONSAVEL_PEDAGOGICO);

        // Get all the nivelEnsinoList where responsavelPedagogico equals to UPDATED_RESPONSAVEL_PEDAGOGICO
        defaultNivelEnsinoShouldNotBeFound("responsavelPedagogico.equals=" + UPDATED_RESPONSAVEL_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelPedagogicoIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelPedagogico in DEFAULT_RESPONSAVEL_PEDAGOGICO or UPDATED_RESPONSAVEL_PEDAGOGICO
        defaultNivelEnsinoShouldBeFound(
            "responsavelPedagogico.in=" + DEFAULT_RESPONSAVEL_PEDAGOGICO + "," + UPDATED_RESPONSAVEL_PEDAGOGICO
        );

        // Get all the nivelEnsinoList where responsavelPedagogico equals to UPDATED_RESPONSAVEL_PEDAGOGICO
        defaultNivelEnsinoShouldNotBeFound("responsavelPedagogico.in=" + UPDATED_RESPONSAVEL_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelPedagogicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelPedagogico is not null
        defaultNivelEnsinoShouldBeFound("responsavelPedagogico.specified=true");

        // Get all the nivelEnsinoList where responsavelPedagogico is null
        defaultNivelEnsinoShouldNotBeFound("responsavelPedagogico.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelPedagogicoContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelPedagogico contains DEFAULT_RESPONSAVEL_PEDAGOGICO
        defaultNivelEnsinoShouldBeFound("responsavelPedagogico.contains=" + DEFAULT_RESPONSAVEL_PEDAGOGICO);

        // Get all the nivelEnsinoList where responsavelPedagogico contains UPDATED_RESPONSAVEL_PEDAGOGICO
        defaultNivelEnsinoShouldNotBeFound("responsavelPedagogico.contains=" + UPDATED_RESPONSAVEL_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelPedagogicoNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelPedagogico does not contain DEFAULT_RESPONSAVEL_PEDAGOGICO
        defaultNivelEnsinoShouldNotBeFound("responsavelPedagogico.doesNotContain=" + DEFAULT_RESPONSAVEL_PEDAGOGICO);

        // Get all the nivelEnsinoList where responsavelPedagogico does not contain UPDATED_RESPONSAVEL_PEDAGOGICO
        defaultNivelEnsinoShouldBeFound("responsavelPedagogico.doesNotContain=" + UPDATED_RESPONSAVEL_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelAdministrativoIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelAdministrativo equals to DEFAULT_RESPONSAVEL_ADMINISTRATIVO
        defaultNivelEnsinoShouldBeFound("responsavelAdministrativo.equals=" + DEFAULT_RESPONSAVEL_ADMINISTRATIVO);

        // Get all the nivelEnsinoList where responsavelAdministrativo equals to UPDATED_RESPONSAVEL_ADMINISTRATIVO
        defaultNivelEnsinoShouldNotBeFound("responsavelAdministrativo.equals=" + UPDATED_RESPONSAVEL_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelAdministrativoIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelAdministrativo in DEFAULT_RESPONSAVEL_ADMINISTRATIVO or UPDATED_RESPONSAVEL_ADMINISTRATIVO
        defaultNivelEnsinoShouldBeFound(
            "responsavelAdministrativo.in=" + DEFAULT_RESPONSAVEL_ADMINISTRATIVO + "," + UPDATED_RESPONSAVEL_ADMINISTRATIVO
        );

        // Get all the nivelEnsinoList where responsavelAdministrativo equals to UPDATED_RESPONSAVEL_ADMINISTRATIVO
        defaultNivelEnsinoShouldNotBeFound("responsavelAdministrativo.in=" + UPDATED_RESPONSAVEL_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelAdministrativoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelAdministrativo is not null
        defaultNivelEnsinoShouldBeFound("responsavelAdministrativo.specified=true");

        // Get all the nivelEnsinoList where responsavelAdministrativo is null
        defaultNivelEnsinoShouldNotBeFound("responsavelAdministrativo.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelAdministrativoContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelAdministrativo contains DEFAULT_RESPONSAVEL_ADMINISTRATIVO
        defaultNivelEnsinoShouldBeFound("responsavelAdministrativo.contains=" + DEFAULT_RESPONSAVEL_ADMINISTRATIVO);

        // Get all the nivelEnsinoList where responsavelAdministrativo contains UPDATED_RESPONSAVEL_ADMINISTRATIVO
        defaultNivelEnsinoShouldNotBeFound("responsavelAdministrativo.contains=" + UPDATED_RESPONSAVEL_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelAdministrativoNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelAdministrativo does not contain DEFAULT_RESPONSAVEL_ADMINISTRATIVO
        defaultNivelEnsinoShouldNotBeFound("responsavelAdministrativo.doesNotContain=" + DEFAULT_RESPONSAVEL_ADMINISTRATIVO);

        // Get all the nivelEnsinoList where responsavelAdministrativo does not contain UPDATED_RESPONSAVEL_ADMINISTRATIVO
        defaultNivelEnsinoShouldBeFound("responsavelAdministrativo.doesNotContain=" + UPDATED_RESPONSAVEL_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelSecretariaGeralIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelSecretariaGeral equals to DEFAULT_RESPONSAVEL_SECRETARIA_GERAL
        defaultNivelEnsinoShouldBeFound("responsavelSecretariaGeral.equals=" + DEFAULT_RESPONSAVEL_SECRETARIA_GERAL);

        // Get all the nivelEnsinoList where responsavelSecretariaGeral equals to UPDATED_RESPONSAVEL_SECRETARIA_GERAL
        defaultNivelEnsinoShouldNotBeFound("responsavelSecretariaGeral.equals=" + UPDATED_RESPONSAVEL_SECRETARIA_GERAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelSecretariaGeralIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelSecretariaGeral in DEFAULT_RESPONSAVEL_SECRETARIA_GERAL or UPDATED_RESPONSAVEL_SECRETARIA_GERAL
        defaultNivelEnsinoShouldBeFound(
            "responsavelSecretariaGeral.in=" + DEFAULT_RESPONSAVEL_SECRETARIA_GERAL + "," + UPDATED_RESPONSAVEL_SECRETARIA_GERAL
        );

        // Get all the nivelEnsinoList where responsavelSecretariaGeral equals to UPDATED_RESPONSAVEL_SECRETARIA_GERAL
        defaultNivelEnsinoShouldNotBeFound("responsavelSecretariaGeral.in=" + UPDATED_RESPONSAVEL_SECRETARIA_GERAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelSecretariaGeralIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelSecretariaGeral is not null
        defaultNivelEnsinoShouldBeFound("responsavelSecretariaGeral.specified=true");

        // Get all the nivelEnsinoList where responsavelSecretariaGeral is null
        defaultNivelEnsinoShouldNotBeFound("responsavelSecretariaGeral.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelSecretariaGeralContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelSecretariaGeral contains DEFAULT_RESPONSAVEL_SECRETARIA_GERAL
        defaultNivelEnsinoShouldBeFound("responsavelSecretariaGeral.contains=" + DEFAULT_RESPONSAVEL_SECRETARIA_GERAL);

        // Get all the nivelEnsinoList where responsavelSecretariaGeral contains UPDATED_RESPONSAVEL_SECRETARIA_GERAL
        defaultNivelEnsinoShouldNotBeFound("responsavelSecretariaGeral.contains=" + UPDATED_RESPONSAVEL_SECRETARIA_GERAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelSecretariaGeralNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelSecretariaGeral does not contain DEFAULT_RESPONSAVEL_SECRETARIA_GERAL
        defaultNivelEnsinoShouldNotBeFound("responsavelSecretariaGeral.doesNotContain=" + DEFAULT_RESPONSAVEL_SECRETARIA_GERAL);

        // Get all the nivelEnsinoList where responsavelSecretariaGeral does not contain UPDATED_RESPONSAVEL_SECRETARIA_GERAL
        defaultNivelEnsinoShouldBeFound("responsavelSecretariaGeral.doesNotContain=" + UPDATED_RESPONSAVEL_SECRETARIA_GERAL);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelSecretariaPedagogicoIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelSecretariaPedagogico equals to DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO
        defaultNivelEnsinoShouldBeFound("responsavelSecretariaPedagogico.equals=" + DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO);

        // Get all the nivelEnsinoList where responsavelSecretariaPedagogico equals to UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO
        defaultNivelEnsinoShouldNotBeFound("responsavelSecretariaPedagogico.equals=" + UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelSecretariaPedagogicoIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelSecretariaPedagogico in DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO or UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO
        defaultNivelEnsinoShouldBeFound(
            "responsavelSecretariaPedagogico.in=" +
            DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO +
            "," +
            UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO
        );

        // Get all the nivelEnsinoList where responsavelSecretariaPedagogico equals to UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO
        defaultNivelEnsinoShouldNotBeFound("responsavelSecretariaPedagogico.in=" + UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelSecretariaPedagogicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelSecretariaPedagogico is not null
        defaultNivelEnsinoShouldBeFound("responsavelSecretariaPedagogico.specified=true");

        // Get all the nivelEnsinoList where responsavelSecretariaPedagogico is null
        defaultNivelEnsinoShouldNotBeFound("responsavelSecretariaPedagogico.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelSecretariaPedagogicoContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelSecretariaPedagogico contains DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO
        defaultNivelEnsinoShouldBeFound("responsavelSecretariaPedagogico.contains=" + DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO);

        // Get all the nivelEnsinoList where responsavelSecretariaPedagogico contains UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO
        defaultNivelEnsinoShouldNotBeFound("responsavelSecretariaPedagogico.contains=" + UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByResponsavelSecretariaPedagogicoNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where responsavelSecretariaPedagogico does not contain DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO
        defaultNivelEnsinoShouldNotBeFound("responsavelSecretariaPedagogico.doesNotContain=" + DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO);

        // Get all the nivelEnsinoList where responsavelSecretariaPedagogico does not contain UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO
        defaultNivelEnsinoShouldBeFound("responsavelSecretariaPedagogico.doesNotContain=" + UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDescricaoDocenteIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where descricaoDocente equals to DEFAULT_DESCRICAO_DOCENTE
        defaultNivelEnsinoShouldBeFound("descricaoDocente.equals=" + DEFAULT_DESCRICAO_DOCENTE);

        // Get all the nivelEnsinoList where descricaoDocente equals to UPDATED_DESCRICAO_DOCENTE
        defaultNivelEnsinoShouldNotBeFound("descricaoDocente.equals=" + UPDATED_DESCRICAO_DOCENTE);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDescricaoDocenteIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where descricaoDocente in DEFAULT_DESCRICAO_DOCENTE or UPDATED_DESCRICAO_DOCENTE
        defaultNivelEnsinoShouldBeFound("descricaoDocente.in=" + DEFAULT_DESCRICAO_DOCENTE + "," + UPDATED_DESCRICAO_DOCENTE);

        // Get all the nivelEnsinoList where descricaoDocente equals to UPDATED_DESCRICAO_DOCENTE
        defaultNivelEnsinoShouldNotBeFound("descricaoDocente.in=" + UPDATED_DESCRICAO_DOCENTE);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDescricaoDocenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where descricaoDocente is not null
        defaultNivelEnsinoShouldBeFound("descricaoDocente.specified=true");

        // Get all the nivelEnsinoList where descricaoDocente is null
        defaultNivelEnsinoShouldNotBeFound("descricaoDocente.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDescricaoDocenteContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where descricaoDocente contains DEFAULT_DESCRICAO_DOCENTE
        defaultNivelEnsinoShouldBeFound("descricaoDocente.contains=" + DEFAULT_DESCRICAO_DOCENTE);

        // Get all the nivelEnsinoList where descricaoDocente contains UPDATED_DESCRICAO_DOCENTE
        defaultNivelEnsinoShouldNotBeFound("descricaoDocente.contains=" + UPDATED_DESCRICAO_DOCENTE);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDescricaoDocenteNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where descricaoDocente does not contain DEFAULT_DESCRICAO_DOCENTE
        defaultNivelEnsinoShouldNotBeFound("descricaoDocente.doesNotContain=" + DEFAULT_DESCRICAO_DOCENTE);

        // Get all the nivelEnsinoList where descricaoDocente does not contain UPDATED_DESCRICAO_DOCENTE
        defaultNivelEnsinoShouldBeFound("descricaoDocente.doesNotContain=" + UPDATED_DESCRICAO_DOCENTE);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDescricaoDiscenteIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where descricaoDiscente equals to DEFAULT_DESCRICAO_DISCENTE
        defaultNivelEnsinoShouldBeFound("descricaoDiscente.equals=" + DEFAULT_DESCRICAO_DISCENTE);

        // Get all the nivelEnsinoList where descricaoDiscente equals to UPDATED_DESCRICAO_DISCENTE
        defaultNivelEnsinoShouldNotBeFound("descricaoDiscente.equals=" + UPDATED_DESCRICAO_DISCENTE);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDescricaoDiscenteIsInShouldWork() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where descricaoDiscente in DEFAULT_DESCRICAO_DISCENTE or UPDATED_DESCRICAO_DISCENTE
        defaultNivelEnsinoShouldBeFound("descricaoDiscente.in=" + DEFAULT_DESCRICAO_DISCENTE + "," + UPDATED_DESCRICAO_DISCENTE);

        // Get all the nivelEnsinoList where descricaoDiscente equals to UPDATED_DESCRICAO_DISCENTE
        defaultNivelEnsinoShouldNotBeFound("descricaoDiscente.in=" + UPDATED_DESCRICAO_DISCENTE);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDescricaoDiscenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where descricaoDiscente is not null
        defaultNivelEnsinoShouldBeFound("descricaoDiscente.specified=true");

        // Get all the nivelEnsinoList where descricaoDiscente is null
        defaultNivelEnsinoShouldNotBeFound("descricaoDiscente.specified=false");
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDescricaoDiscenteContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where descricaoDiscente contains DEFAULT_DESCRICAO_DISCENTE
        defaultNivelEnsinoShouldBeFound("descricaoDiscente.contains=" + DEFAULT_DESCRICAO_DISCENTE);

        // Get all the nivelEnsinoList where descricaoDiscente contains UPDATED_DESCRICAO_DISCENTE
        defaultNivelEnsinoShouldNotBeFound("descricaoDiscente.contains=" + UPDATED_DESCRICAO_DISCENTE);
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByDescricaoDiscenteNotContainsSomething() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        // Get all the nivelEnsinoList where descricaoDiscente does not contain DEFAULT_DESCRICAO_DISCENTE
        defaultNivelEnsinoShouldNotBeFound("descricaoDiscente.doesNotContain=" + DEFAULT_DESCRICAO_DISCENTE);

        // Get all the nivelEnsinoList where descricaoDiscente does not contain UPDATED_DESCRICAO_DISCENTE
        defaultNivelEnsinoShouldBeFound("descricaoDiscente.doesNotContain=" + UPDATED_DESCRICAO_DISCENTE);
    }

    // @Test
    // @Transactional
    // void getAllNivelEnsinosByNivelEnsinoIsEqualToSomething() throws Exception {
    //     NivelEnsino nivelEnsino;
    //     if (TestUtil.findAll(em, NivelEnsino.class).isEmpty()) {
    //         nivelEnsinoRepository.saveAndFlush(nivelEnsino);
    //         nivelEnsino = NivelEnsinoResourceIT.createEntity(em);
    //     } else {
    //         nivelEnsino = TestUtil.findAll(em, NivelEnsino.class).get(0);
    //     }
    //     em.persist(nivelEnsino);
    //     em.flush();
    //     nivelEnsino.addNivelEnsino(nivelEnsino);
    //     nivelEnsinoRepository.saveAndFlush(nivelEnsino);
    //     Long nivelEnsinoId = nivelEnsino.getId();

    //     // Get all the nivelEnsinoList where nivelEnsino equals to nivelEnsinoId
    //     defaultNivelEnsinoShouldBeFound("nivelEnsinoId.equals=" + nivelEnsinoId);

    //     // Get all the nivelEnsinoList where nivelEnsino equals to (nivelEnsinoId + 1)
    //     defaultNivelEnsinoShouldNotBeFound("nivelEnsinoId.equals=" + (nivelEnsinoId + 1));
    // }

    @Test
    @Transactional
    void getAllNivelEnsinosByAreaFormacaoIsEqualToSomething() throws Exception {
        AreaFormacao areaFormacao;
        if (TestUtil.findAll(em, AreaFormacao.class).isEmpty()) {
            nivelEnsinoRepository.saveAndFlush(nivelEnsino);
            areaFormacao = AreaFormacaoResourceIT.createEntity(em);
        } else {
            areaFormacao = TestUtil.findAll(em, AreaFormacao.class).get(0);
        }
        em.persist(areaFormacao);
        em.flush();
        nivelEnsino.addAreaFormacao(areaFormacao);
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);
        Long areaFormacaoId = areaFormacao.getId();

        // Get all the nivelEnsinoList where areaFormacao equals to areaFormacaoId
        defaultNivelEnsinoShouldBeFound("areaFormacaoId.equals=" + areaFormacaoId);

        // Get all the nivelEnsinoList where areaFormacao equals to (areaFormacaoId + 1)
        defaultNivelEnsinoShouldNotBeFound("areaFormacaoId.equals=" + (areaFormacaoId + 1));
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByReferenciaIsEqualToSomething() throws Exception {
        NivelEnsino referencia;
        if (TestUtil.findAll(em, NivelEnsino.class).isEmpty()) {
            nivelEnsinoRepository.saveAndFlush(nivelEnsino);
            referencia = NivelEnsinoResourceIT.createEntity(em);
        } else {
            referencia = TestUtil.findAll(em, NivelEnsino.class).get(0);
        }
        em.persist(referencia);
        em.flush();
        nivelEnsino.setReferencia(referencia);
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);
        Long referenciaId = referencia.getId();

        // Get all the nivelEnsinoList where referencia equals to referenciaId
        defaultNivelEnsinoShouldBeFound("referenciaId.equals=" + referenciaId);

        // Get all the nivelEnsinoList where referencia equals to (referenciaId + 1)
        defaultNivelEnsinoShouldNotBeFound("referenciaId.equals=" + (referenciaId + 1));
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByAnoLectivosIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivos;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            nivelEnsinoRepository.saveAndFlush(nivelEnsino);
            anoLectivos = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivos = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivos);
        em.flush();
        nivelEnsino.addAnoLectivos(anoLectivos);
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);
        Long anoLectivosId = anoLectivos.getId();

        // Get all the nivelEnsinoList where anoLectivos equals to anoLectivosId
        defaultNivelEnsinoShouldBeFound("anoLectivosId.equals=" + anoLectivosId);

        // Get all the nivelEnsinoList where anoLectivos equals to (anoLectivosId + 1)
        defaultNivelEnsinoShouldNotBeFound("anoLectivosId.equals=" + (anoLectivosId + 1));
    }

    @Test
    @Transactional
    void getAllNivelEnsinosByClassesIsEqualToSomething() throws Exception {
        Classe classes;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            nivelEnsinoRepository.saveAndFlush(nivelEnsino);
            classes = ClasseResourceIT.createEntity(em);
        } else {
            classes = TestUtil.findAll(em, Classe.class).get(0);
        }
        em.persist(classes);
        em.flush();
        nivelEnsino.addClasses(classes);
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);
        Long classesId = classes.getId();

        // Get all the nivelEnsinoList where classes equals to classesId
        defaultNivelEnsinoShouldBeFound("classesId.equals=" + classesId);

        // Get all the nivelEnsinoList where classes equals to (classesId + 1)
        defaultNivelEnsinoShouldNotBeFound("classesId.equals=" + (classesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNivelEnsinoShouldBeFound(String filter) throws Exception {
        restNivelEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nivelEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].idadeMinima").value(hasItem(DEFAULT_IDADE_MINIMA)))
            .andExpect(jsonPath("$.[*].idadeMaxima").value(hasItem(DEFAULT_IDADE_MAXIMA)))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].unidadeDuracao").value(hasItem(DEFAULT_UNIDADE_DURACAO.toString())))
            .andExpect(jsonPath("$.[*].classeInicial").value(hasItem(DEFAULT_CLASSE_INICIAL)))
            .andExpect(jsonPath("$.[*].classeFinal").value(hasItem(DEFAULT_CLASSE_FINAL)))
            .andExpect(jsonPath("$.[*].classeExame").value(hasItem(DEFAULT_CLASSE_EXAME)))
            .andExpect(jsonPath("$.[*].totalDisciplina").value(hasItem(DEFAULT_TOTAL_DISCIPLINA)))
            .andExpect(jsonPath("$.[*].responsavelTurno").value(hasItem(DEFAULT_RESPONSAVEL_TURNO)))
            .andExpect(jsonPath("$.[*].responsavelAreaFormacao").value(hasItem(DEFAULT_RESPONSAVEL_AREA_FORMACAO)))
            .andExpect(jsonPath("$.[*].responsavelCurso").value(hasItem(DEFAULT_RESPONSAVEL_CURSO)))
            .andExpect(jsonPath("$.[*].responsavelDisciplina").value(hasItem(DEFAULT_RESPONSAVEL_DISCIPLINA)))
            .andExpect(jsonPath("$.[*].responsavelTurma").value(hasItem(DEFAULT_RESPONSAVEL_TURMA)))
            .andExpect(jsonPath("$.[*].responsavelGeral").value(hasItem(DEFAULT_RESPONSAVEL_GERAL)))
            .andExpect(jsonPath("$.[*].responsavelPedagogico").value(hasItem(DEFAULT_RESPONSAVEL_PEDAGOGICO)))
            .andExpect(jsonPath("$.[*].responsavelAdministrativo").value(hasItem(DEFAULT_RESPONSAVEL_ADMINISTRATIVO)))
            .andExpect(jsonPath("$.[*].responsavelSecretariaGeral").value(hasItem(DEFAULT_RESPONSAVEL_SECRETARIA_GERAL)))
            .andExpect(jsonPath("$.[*].responsavelSecretariaPedagogico").value(hasItem(DEFAULT_RESPONSAVEL_SECRETARIA_PEDAGOGICO)))
            .andExpect(jsonPath("$.[*].descricaoDocente").value(hasItem(DEFAULT_DESCRICAO_DOCENTE)))
            .andExpect(jsonPath("$.[*].descricaoDiscente").value(hasItem(DEFAULT_DESCRICAO_DISCENTE)));

        // Check, that the count call also returns 1
        restNivelEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNivelEnsinoShouldNotBeFound(String filter) throws Exception {
        restNivelEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNivelEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNivelEnsino() throws Exception {
        // Get the nivelEnsino
        restNivelEnsinoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNivelEnsino() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        int databaseSizeBeforeUpdate = nivelEnsinoRepository.findAll().size();

        // Update the nivelEnsino
        NivelEnsino updatedNivelEnsino = nivelEnsinoRepository.findById(nivelEnsino.getId()).get();
        // Disconnect from session so that the updates on updatedNivelEnsino are not directly saved in db
        em.detach(updatedNivelEnsino);
        updatedNivelEnsino
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .idadeMinima(UPDATED_IDADE_MINIMA)
            .idadeMaxima(UPDATED_IDADE_MAXIMA)
            .duracao(UPDATED_DURACAO)
            .unidadeDuracao(UPDATED_UNIDADE_DURACAO)
            .classeInicial(UPDATED_CLASSE_INICIAL)
            .classeFinal(UPDATED_CLASSE_FINAL)
            .classeExame(UPDATED_CLASSE_EXAME)
            .totalDisciplina(UPDATED_TOTAL_DISCIPLINA)
            .responsavelTurno(UPDATED_RESPONSAVEL_TURNO)
            .responsavelAreaFormacao(UPDATED_RESPONSAVEL_AREA_FORMACAO)
            .responsavelCurso(UPDATED_RESPONSAVEL_CURSO)
            .responsavelDisciplina(UPDATED_RESPONSAVEL_DISCIPLINA)
            .responsavelTurma(UPDATED_RESPONSAVEL_TURMA)
            .responsavelGeral(UPDATED_RESPONSAVEL_GERAL)
            .responsavelPedagogico(UPDATED_RESPONSAVEL_PEDAGOGICO)
            .responsavelAdministrativo(UPDATED_RESPONSAVEL_ADMINISTRATIVO)
            .responsavelSecretariaGeral(UPDATED_RESPONSAVEL_SECRETARIA_GERAL)
            .responsavelSecretariaPedagogico(UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO)
            .descricaoDocente(UPDATED_DESCRICAO_DOCENTE)
            .descricaoDiscente(UPDATED_DESCRICAO_DISCENTE);
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(updatedNivelEnsino);

        restNivelEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nivelEnsinoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO))
            )
            .andExpect(status().isOk());

        // Validate the NivelEnsino in the database
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeUpdate);
        NivelEnsino testNivelEnsino = nivelEnsinoList.get(nivelEnsinoList.size() - 1);
        assertThat(testNivelEnsino.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testNivelEnsino.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testNivelEnsino.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testNivelEnsino.getIdadeMinima()).isEqualTo(UPDATED_IDADE_MINIMA);
        assertThat(testNivelEnsino.getIdadeMaxima()).isEqualTo(UPDATED_IDADE_MAXIMA);
        assertThat(testNivelEnsino.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testNivelEnsino.getUnidadeDuracao()).isEqualTo(UPDATED_UNIDADE_DURACAO);
        assertThat(testNivelEnsino.getClasseInicial()).isEqualTo(UPDATED_CLASSE_INICIAL);
        assertThat(testNivelEnsino.getClasseFinal()).isEqualTo(UPDATED_CLASSE_FINAL);
        assertThat(testNivelEnsino.getClasseExame()).isEqualTo(UPDATED_CLASSE_EXAME);
        assertThat(testNivelEnsino.getTotalDisciplina()).isEqualTo(UPDATED_TOTAL_DISCIPLINA);
        assertThat(testNivelEnsino.getResponsavelTurno()).isEqualTo(UPDATED_RESPONSAVEL_TURNO);
        assertThat(testNivelEnsino.getResponsavelAreaFormacao()).isEqualTo(UPDATED_RESPONSAVEL_AREA_FORMACAO);
        assertThat(testNivelEnsino.getResponsavelCurso()).isEqualTo(UPDATED_RESPONSAVEL_CURSO);
        assertThat(testNivelEnsino.getResponsavelDisciplina()).isEqualTo(UPDATED_RESPONSAVEL_DISCIPLINA);
        assertThat(testNivelEnsino.getResponsavelTurma()).isEqualTo(UPDATED_RESPONSAVEL_TURMA);
        assertThat(testNivelEnsino.getResponsavelGeral()).isEqualTo(UPDATED_RESPONSAVEL_GERAL);
        assertThat(testNivelEnsino.getResponsavelPedagogico()).isEqualTo(UPDATED_RESPONSAVEL_PEDAGOGICO);
        assertThat(testNivelEnsino.getResponsavelAdministrativo()).isEqualTo(UPDATED_RESPONSAVEL_ADMINISTRATIVO);
        assertThat(testNivelEnsino.getResponsavelSecretariaGeral()).isEqualTo(UPDATED_RESPONSAVEL_SECRETARIA_GERAL);
        assertThat(testNivelEnsino.getResponsavelSecretariaPedagogico()).isEqualTo(UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO);
        assertThat(testNivelEnsino.getDescricaoDocente()).isEqualTo(UPDATED_DESCRICAO_DOCENTE);
        assertThat(testNivelEnsino.getDescricaoDiscente()).isEqualTo(UPDATED_DESCRICAO_DISCENTE);
    }

    @Test
    @Transactional
    void putNonExistingNivelEnsino() throws Exception {
        int databaseSizeBeforeUpdate = nivelEnsinoRepository.findAll().size();
        nivelEnsino.setId(count.incrementAndGet());

        // Create the NivelEnsino
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(nivelEnsino);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNivelEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nivelEnsinoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NivelEnsino in the database
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNivelEnsino() throws Exception {
        int databaseSizeBeforeUpdate = nivelEnsinoRepository.findAll().size();
        nivelEnsino.setId(count.incrementAndGet());

        // Create the NivelEnsino
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(nivelEnsino);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NivelEnsino in the database
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNivelEnsino() throws Exception {
        int databaseSizeBeforeUpdate = nivelEnsinoRepository.findAll().size();
        nivelEnsino.setId(count.incrementAndGet());

        // Create the NivelEnsino
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(nivelEnsino);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelEnsinoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NivelEnsino in the database
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNivelEnsinoWithPatch() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        int databaseSizeBeforeUpdate = nivelEnsinoRepository.findAll().size();

        // Update the nivelEnsino using partial update
        NivelEnsino partialUpdatedNivelEnsino = new NivelEnsino();
        partialUpdatedNivelEnsino.setId(nivelEnsino.getId());

        partialUpdatedNivelEnsino
            .nome(UPDATED_NOME)
            .idadeMinima(UPDATED_IDADE_MINIMA)
            .duracao(UPDATED_DURACAO)
            .unidadeDuracao(UPDATED_UNIDADE_DURACAO)
            .classeInicial(UPDATED_CLASSE_INICIAL)
            .classeFinal(UPDATED_CLASSE_FINAL)
            .classeExame(UPDATED_CLASSE_EXAME)
            .responsavelAreaFormacao(UPDATED_RESPONSAVEL_AREA_FORMACAO)
            .responsavelCurso(UPDATED_RESPONSAVEL_CURSO)
            .responsavelDisciplina(UPDATED_RESPONSAVEL_DISCIPLINA)
            .responsavelAdministrativo(UPDATED_RESPONSAVEL_ADMINISTRATIVO)
            .responsavelSecretariaPedagogico(UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO)
            .descricaoDocente(UPDATED_DESCRICAO_DOCENTE);

        restNivelEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNivelEnsino.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNivelEnsino))
            )
            .andExpect(status().isOk());

        // Validate the NivelEnsino in the database
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeUpdate);
        NivelEnsino testNivelEnsino = nivelEnsinoList.get(nivelEnsinoList.size() - 1);
        assertThat(testNivelEnsino.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testNivelEnsino.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testNivelEnsino.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testNivelEnsino.getIdadeMinima()).isEqualTo(UPDATED_IDADE_MINIMA);
        assertThat(testNivelEnsino.getIdadeMaxima()).isEqualTo(DEFAULT_IDADE_MAXIMA);
        assertThat(testNivelEnsino.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testNivelEnsino.getUnidadeDuracao()).isEqualTo(UPDATED_UNIDADE_DURACAO);
        assertThat(testNivelEnsino.getClasseInicial()).isEqualTo(UPDATED_CLASSE_INICIAL);
        assertThat(testNivelEnsino.getClasseFinal()).isEqualTo(UPDATED_CLASSE_FINAL);
        assertThat(testNivelEnsino.getClasseExame()).isEqualTo(UPDATED_CLASSE_EXAME);
        assertThat(testNivelEnsino.getTotalDisciplina()).isEqualTo(DEFAULT_TOTAL_DISCIPLINA);
        assertThat(testNivelEnsino.getResponsavelTurno()).isEqualTo(DEFAULT_RESPONSAVEL_TURNO);
        assertThat(testNivelEnsino.getResponsavelAreaFormacao()).isEqualTo(UPDATED_RESPONSAVEL_AREA_FORMACAO);
        assertThat(testNivelEnsino.getResponsavelCurso()).isEqualTo(UPDATED_RESPONSAVEL_CURSO);
        assertThat(testNivelEnsino.getResponsavelDisciplina()).isEqualTo(UPDATED_RESPONSAVEL_DISCIPLINA);
        assertThat(testNivelEnsino.getResponsavelTurma()).isEqualTo(DEFAULT_RESPONSAVEL_TURMA);
        assertThat(testNivelEnsino.getResponsavelGeral()).isEqualTo(DEFAULT_RESPONSAVEL_GERAL);
        assertThat(testNivelEnsino.getResponsavelPedagogico()).isEqualTo(DEFAULT_RESPONSAVEL_PEDAGOGICO);
        assertThat(testNivelEnsino.getResponsavelAdministrativo()).isEqualTo(UPDATED_RESPONSAVEL_ADMINISTRATIVO);
        assertThat(testNivelEnsino.getResponsavelSecretariaGeral()).isEqualTo(DEFAULT_RESPONSAVEL_SECRETARIA_GERAL);
        assertThat(testNivelEnsino.getResponsavelSecretariaPedagogico()).isEqualTo(UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO);
        assertThat(testNivelEnsino.getDescricaoDocente()).isEqualTo(UPDATED_DESCRICAO_DOCENTE);
        assertThat(testNivelEnsino.getDescricaoDiscente()).isEqualTo(DEFAULT_DESCRICAO_DISCENTE);
    }

    @Test
    @Transactional
    void fullUpdateNivelEnsinoWithPatch() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        int databaseSizeBeforeUpdate = nivelEnsinoRepository.findAll().size();

        // Update the nivelEnsino using partial update
        NivelEnsino partialUpdatedNivelEnsino = new NivelEnsino();
        partialUpdatedNivelEnsino.setId(nivelEnsino.getId());

        partialUpdatedNivelEnsino
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .idadeMinima(UPDATED_IDADE_MINIMA)
            .idadeMaxima(UPDATED_IDADE_MAXIMA)
            .duracao(UPDATED_DURACAO)
            .unidadeDuracao(UPDATED_UNIDADE_DURACAO)
            .classeInicial(UPDATED_CLASSE_INICIAL)
            .classeFinal(UPDATED_CLASSE_FINAL)
            .classeExame(UPDATED_CLASSE_EXAME)
            .totalDisciplina(UPDATED_TOTAL_DISCIPLINA)
            .responsavelTurno(UPDATED_RESPONSAVEL_TURNO)
            .responsavelAreaFormacao(UPDATED_RESPONSAVEL_AREA_FORMACAO)
            .responsavelCurso(UPDATED_RESPONSAVEL_CURSO)
            .responsavelDisciplina(UPDATED_RESPONSAVEL_DISCIPLINA)
            .responsavelTurma(UPDATED_RESPONSAVEL_TURMA)
            .responsavelGeral(UPDATED_RESPONSAVEL_GERAL)
            .responsavelPedagogico(UPDATED_RESPONSAVEL_PEDAGOGICO)
            .responsavelAdministrativo(UPDATED_RESPONSAVEL_ADMINISTRATIVO)
            .responsavelSecretariaGeral(UPDATED_RESPONSAVEL_SECRETARIA_GERAL)
            .responsavelSecretariaPedagogico(UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO)
            .descricaoDocente(UPDATED_DESCRICAO_DOCENTE)
            .descricaoDiscente(UPDATED_DESCRICAO_DISCENTE);

        restNivelEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNivelEnsino.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNivelEnsino))
            )
            .andExpect(status().isOk());

        // Validate the NivelEnsino in the database
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeUpdate);
        NivelEnsino testNivelEnsino = nivelEnsinoList.get(nivelEnsinoList.size() - 1);
        assertThat(testNivelEnsino.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testNivelEnsino.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testNivelEnsino.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testNivelEnsino.getIdadeMinima()).isEqualTo(UPDATED_IDADE_MINIMA);
        assertThat(testNivelEnsino.getIdadeMaxima()).isEqualTo(UPDATED_IDADE_MAXIMA);
        assertThat(testNivelEnsino.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testNivelEnsino.getUnidadeDuracao()).isEqualTo(UPDATED_UNIDADE_DURACAO);
        assertThat(testNivelEnsino.getClasseInicial()).isEqualTo(UPDATED_CLASSE_INICIAL);
        assertThat(testNivelEnsino.getClasseFinal()).isEqualTo(UPDATED_CLASSE_FINAL);
        assertThat(testNivelEnsino.getClasseExame()).isEqualTo(UPDATED_CLASSE_EXAME);
        assertThat(testNivelEnsino.getTotalDisciplina()).isEqualTo(UPDATED_TOTAL_DISCIPLINA);
        assertThat(testNivelEnsino.getResponsavelTurno()).isEqualTo(UPDATED_RESPONSAVEL_TURNO);
        assertThat(testNivelEnsino.getResponsavelAreaFormacao()).isEqualTo(UPDATED_RESPONSAVEL_AREA_FORMACAO);
        assertThat(testNivelEnsino.getResponsavelCurso()).isEqualTo(UPDATED_RESPONSAVEL_CURSO);
        assertThat(testNivelEnsino.getResponsavelDisciplina()).isEqualTo(UPDATED_RESPONSAVEL_DISCIPLINA);
        assertThat(testNivelEnsino.getResponsavelTurma()).isEqualTo(UPDATED_RESPONSAVEL_TURMA);
        assertThat(testNivelEnsino.getResponsavelGeral()).isEqualTo(UPDATED_RESPONSAVEL_GERAL);
        assertThat(testNivelEnsino.getResponsavelPedagogico()).isEqualTo(UPDATED_RESPONSAVEL_PEDAGOGICO);
        assertThat(testNivelEnsino.getResponsavelAdministrativo()).isEqualTo(UPDATED_RESPONSAVEL_ADMINISTRATIVO);
        assertThat(testNivelEnsino.getResponsavelSecretariaGeral()).isEqualTo(UPDATED_RESPONSAVEL_SECRETARIA_GERAL);
        assertThat(testNivelEnsino.getResponsavelSecretariaPedagogico()).isEqualTo(UPDATED_RESPONSAVEL_SECRETARIA_PEDAGOGICO);
        assertThat(testNivelEnsino.getDescricaoDocente()).isEqualTo(UPDATED_DESCRICAO_DOCENTE);
        assertThat(testNivelEnsino.getDescricaoDiscente()).isEqualTo(UPDATED_DESCRICAO_DISCENTE);
    }

    @Test
    @Transactional
    void patchNonExistingNivelEnsino() throws Exception {
        int databaseSizeBeforeUpdate = nivelEnsinoRepository.findAll().size();
        nivelEnsino.setId(count.incrementAndGet());

        // Create the NivelEnsino
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(nivelEnsino);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNivelEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nivelEnsinoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NivelEnsino in the database
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNivelEnsino() throws Exception {
        int databaseSizeBeforeUpdate = nivelEnsinoRepository.findAll().size();
        nivelEnsino.setId(count.incrementAndGet());

        // Create the NivelEnsino
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(nivelEnsino);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NivelEnsino in the database
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNivelEnsino() throws Exception {
        int databaseSizeBeforeUpdate = nivelEnsinoRepository.findAll().size();
        nivelEnsino.setId(count.incrementAndGet());

        // Create the NivelEnsino
        NivelEnsinoDTO nivelEnsinoDTO = nivelEnsinoMapper.toDto(nivelEnsino);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nivelEnsinoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NivelEnsino in the database
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNivelEnsino() throws Exception {
        // Initialize the database
        nivelEnsinoRepository.saveAndFlush(nivelEnsino);

        int databaseSizeBeforeDelete = nivelEnsinoRepository.findAll().size();

        // Delete the nivelEnsino
        restNivelEnsinoMockMvc
            .perform(delete(ENTITY_API_URL_ID, nivelEnsino.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NivelEnsino> nivelEnsinoList = nivelEnsinoRepository.findAll();
        assertThat(nivelEnsinoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
