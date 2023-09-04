package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Classe;
import com.ravunana.longonkelo.domain.Curso;
import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.PlanoCurricular;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.PlanoCurricularRepository;
import com.ravunana.longonkelo.service.PlanoCurricularService;
import com.ravunana.longonkelo.service.criteria.PlanoCurricularCriteria;
import com.ravunana.longonkelo.service.dto.PlanoCurricularDTO;
import com.ravunana.longonkelo.service.mapper.PlanoCurricularMapper;
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
 * Integration tests for the {@link PlanoCurricularResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlanoCurricularResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_FORMULA_CLASSIFICACAO_FINAL = "AAAAAAAAAA";
    private static final String UPDATED_FORMULA_CLASSIFICACAO_FINAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_DISCIPLINA_APROVA = 0;
    private static final Integer UPDATED_NUMERO_DISCIPLINA_APROVA = 1;
    private static final Integer SMALLER_NUMERO_DISCIPLINA_APROVA = 0 - 1;

    private static final Integer DEFAULT_NUMERO_DISCIPLINA_REPROVA = 0;
    private static final Integer UPDATED_NUMERO_DISCIPLINA_REPROVA = 1;
    private static final Integer SMALLER_NUMERO_DISCIPLINA_REPROVA = 0 - 1;

    private static final Integer DEFAULT_NUMERO_DISCIPLINA_RECURSO = 0;
    private static final Integer UPDATED_NUMERO_DISCIPLINA_RECURSO = 1;
    private static final Integer SMALLER_NUMERO_DISCIPLINA_RECURSO = 0 - 1;

    private static final Integer DEFAULT_NUMERO_DISCIPLINA_EXAME = 0;
    private static final Integer UPDATED_NUMERO_DISCIPLINA_EXAME = 1;
    private static final Integer SMALLER_NUMERO_DISCIPLINA_EXAME = 0 - 1;

    private static final Integer DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL = 0;
    private static final Integer UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL = 1;
    private static final Integer SMALLER_NUMERO_DISCIPLINA_EXAME_ESPECIAL = 0 - 1;

    private static final Integer DEFAULT_NUMERO_FALTA_REPROVA = 0;
    private static final Integer UPDATED_NUMERO_FALTA_REPROVA = 1;
    private static final Integer SMALLER_NUMERO_FALTA_REPROVA = 0 - 1;

    private static final Double DEFAULT_PESO_MEDIA_1 = 0D;
    private static final Double UPDATED_PESO_MEDIA_1 = 1D;
    private static final Double SMALLER_PESO_MEDIA_1 = 0D - 1D;

    private static final Double DEFAULT_PESO_MEDIA_2 = 0D;
    private static final Double UPDATED_PESO_MEDIA_2 = 1D;
    private static final Double SMALLER_PESO_MEDIA_2 = 0D - 1D;

    private static final Double DEFAULT_PESO_MEDIA_3 = 0D;
    private static final Double UPDATED_PESO_MEDIA_3 = 1D;
    private static final Double SMALLER_PESO_MEDIA_3 = 0D - 1D;

    private static final Double DEFAULT_PESO_RECURSO = 0D;
    private static final Double UPDATED_PESO_RECURSO = 1D;
    private static final Double SMALLER_PESO_RECURSO = 0D - 1D;

    private static final Double DEFAULT_PESO_EXAME = 0D;
    private static final Double UPDATED_PESO_EXAME = 1D;
    private static final Double SMALLER_PESO_EXAME = 0D - 1D;

    private static final Double DEFAULT_PESO_EXAME_ESPECIAL = 0D;
    private static final Double UPDATED_PESO_EXAME_ESPECIAL = 1D;
    private static final Double SMALLER_PESO_EXAME_ESPECIAL = 0D - 1D;

    private static final Double DEFAULT_PESO_NOTA_COSELHO = 0D;
    private static final Double UPDATED_PESO_NOTA_COSELHO = 1D;
    private static final Double SMALLER_PESO_NOTA_COSELHO = 0D - 1D;

    private static final String DEFAULT_SIGLA_PROVA_1 = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA_PROVA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA_PROVA_2 = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA_PROVA_2 = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA_PROVA_3 = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA_PROVA_3 = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA_MEDIA_1 = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA_MEDIA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA_MEDIA_2 = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA_MEDIA_2 = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA_MEDIA_3 = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA_MEDIA_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FORMULA_MEDIA = "AAAAAAAAAA";
    private static final String UPDATED_FORMULA_MEDIA = "BBBBBBBBBB";

    private static final String DEFAULT_FORMULA_DISPENSA = "AAAAAAAAAA";
    private static final String UPDATED_FORMULA_DISPENSA = "BBBBBBBBBB";

    private static final String DEFAULT_FORMULA_EXAME = "AAAAAAAAAA";
    private static final String UPDATED_FORMULA_EXAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORMULA_RECURSO = "AAAAAAAAAA";
    private static final String UPDATED_FORMULA_RECURSO = "BBBBBBBBBB";

    private static final String DEFAULT_FORMULA_EXAME_ESPECIAL = "AAAAAAAAAA";
    private static final String UPDATED_FORMULA_EXAME_ESPECIAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/plano-curriculars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanoCurricularRepository planoCurricularRepository;

    @Mock
    private PlanoCurricularRepository planoCurricularRepositoryMock;

    @Autowired
    private PlanoCurricularMapper planoCurricularMapper;

    @Mock
    private PlanoCurricularService planoCurricularServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoCurricularMockMvc;

    private PlanoCurricular planoCurricular;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoCurricular createEntity(EntityManager em) {
        PlanoCurricular planoCurricular = new PlanoCurricular()
            .descricao(DEFAULT_DESCRICAO)
            .formulaClassificacaoFinal(DEFAULT_FORMULA_CLASSIFICACAO_FINAL)
            .numeroDisciplinaAprova(DEFAULT_NUMERO_DISCIPLINA_APROVA)
            .numeroDisciplinaReprova(DEFAULT_NUMERO_DISCIPLINA_REPROVA)
            .numeroDisciplinaRecurso(DEFAULT_NUMERO_DISCIPLINA_RECURSO)
            .numeroDisciplinaExame(DEFAULT_NUMERO_DISCIPLINA_EXAME)
            .numeroDisciplinaExameEspecial(DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL)
            .numeroFaltaReprova(DEFAULT_NUMERO_FALTA_REPROVA)
            .pesoMedia1(DEFAULT_PESO_MEDIA_1)
            .pesoMedia2(DEFAULT_PESO_MEDIA_2)
            .pesoMedia3(DEFAULT_PESO_MEDIA_3)
            .pesoRecurso(DEFAULT_PESO_RECURSO)
            .pesoExame(DEFAULT_PESO_EXAME)
            .pesoExameEspecial(DEFAULT_PESO_EXAME_ESPECIAL)
            .pesoNotaCoselho(DEFAULT_PESO_NOTA_COSELHO)
            .siglaProva1(DEFAULT_SIGLA_PROVA_1)
            .siglaProva2(DEFAULT_SIGLA_PROVA_2)
            .siglaProva3(DEFAULT_SIGLA_PROVA_3)
            .siglaMedia1(DEFAULT_SIGLA_MEDIA_1)
            .siglaMedia2(DEFAULT_SIGLA_MEDIA_2)
            .siglaMedia3(DEFAULT_SIGLA_MEDIA_3)
            .formulaMedia(DEFAULT_FORMULA_MEDIA)
            .formulaDispensa(DEFAULT_FORMULA_DISPENSA)
            .formulaExame(DEFAULT_FORMULA_EXAME)
            .formulaRecurso(DEFAULT_FORMULA_RECURSO)
            .formulaExameEspecial(DEFAULT_FORMULA_EXAME_ESPECIAL);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        planoCurricular.setClasse(classe);
        // Add required entity
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            curso = CursoResourceIT.createEntity(em);
            em.persist(curso);
            em.flush();
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        planoCurricular.setCurso(curso);
        return planoCurricular;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoCurricular createUpdatedEntity(EntityManager em) {
        PlanoCurricular planoCurricular = new PlanoCurricular()
            .descricao(UPDATED_DESCRICAO)
            .formulaClassificacaoFinal(UPDATED_FORMULA_CLASSIFICACAO_FINAL)
            .numeroDisciplinaAprova(UPDATED_NUMERO_DISCIPLINA_APROVA)
            .numeroDisciplinaReprova(UPDATED_NUMERO_DISCIPLINA_REPROVA)
            .numeroDisciplinaRecurso(UPDATED_NUMERO_DISCIPLINA_RECURSO)
            .numeroDisciplinaExame(UPDATED_NUMERO_DISCIPLINA_EXAME)
            .numeroDisciplinaExameEspecial(UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL)
            .numeroFaltaReprova(UPDATED_NUMERO_FALTA_REPROVA)
            .pesoMedia1(UPDATED_PESO_MEDIA_1)
            .pesoMedia2(UPDATED_PESO_MEDIA_2)
            .pesoMedia3(UPDATED_PESO_MEDIA_3)
            .pesoRecurso(UPDATED_PESO_RECURSO)
            .pesoExame(UPDATED_PESO_EXAME)
            .pesoExameEspecial(UPDATED_PESO_EXAME_ESPECIAL)
            .pesoNotaCoselho(UPDATED_PESO_NOTA_COSELHO)
            .siglaProva1(UPDATED_SIGLA_PROVA_1)
            .siglaProva2(UPDATED_SIGLA_PROVA_2)
            .siglaProva3(UPDATED_SIGLA_PROVA_3)
            .siglaMedia1(UPDATED_SIGLA_MEDIA_1)
            .siglaMedia2(UPDATED_SIGLA_MEDIA_2)
            .siglaMedia3(UPDATED_SIGLA_MEDIA_3)
            .formulaMedia(UPDATED_FORMULA_MEDIA)
            .formulaDispensa(UPDATED_FORMULA_DISPENSA)
            .formulaExame(UPDATED_FORMULA_EXAME)
            .formulaRecurso(UPDATED_FORMULA_RECURSO)
            .formulaExameEspecial(UPDATED_FORMULA_EXAME_ESPECIAL);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createUpdatedEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        planoCurricular.setClasse(classe);
        // Add required entity
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            curso = CursoResourceIT.createUpdatedEntity(em);
            em.persist(curso);
            em.flush();
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        planoCurricular.setCurso(curso);
        return planoCurricular;
    }

    @BeforeEach
    public void initTest() {
        planoCurricular = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanoCurricular() throws Exception {
        int databaseSizeBeforeCreate = planoCurricularRepository.findAll().size();
        // Create the PlanoCurricular
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);
        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoCurricular testPlanoCurricular = planoCurricularList.get(planoCurricularList.size() - 1);
        assertThat(testPlanoCurricular.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoCurricular.getFormulaClassificacaoFinal()).isEqualTo(DEFAULT_FORMULA_CLASSIFICACAO_FINAL);
        assertThat(testPlanoCurricular.getNumeroDisciplinaAprova()).isEqualTo(DEFAULT_NUMERO_DISCIPLINA_APROVA);
        assertThat(testPlanoCurricular.getNumeroDisciplinaReprova()).isEqualTo(DEFAULT_NUMERO_DISCIPLINA_REPROVA);
        assertThat(testPlanoCurricular.getNumeroDisciplinaRecurso()).isEqualTo(DEFAULT_NUMERO_DISCIPLINA_RECURSO);
        assertThat(testPlanoCurricular.getNumeroDisciplinaExame()).isEqualTo(DEFAULT_NUMERO_DISCIPLINA_EXAME);
        assertThat(testPlanoCurricular.getNumeroDisciplinaExameEspecial()).isEqualTo(DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL);
        assertThat(testPlanoCurricular.getNumeroFaltaReprova()).isEqualTo(DEFAULT_NUMERO_FALTA_REPROVA);
        assertThat(testPlanoCurricular.getPesoMedia1()).isEqualTo(DEFAULT_PESO_MEDIA_1);
        assertThat(testPlanoCurricular.getPesoMedia2()).isEqualTo(DEFAULT_PESO_MEDIA_2);
        assertThat(testPlanoCurricular.getPesoMedia3()).isEqualTo(DEFAULT_PESO_MEDIA_3);
        assertThat(testPlanoCurricular.getPesoRecurso()).isEqualTo(DEFAULT_PESO_RECURSO);
        assertThat(testPlanoCurricular.getPesoExame()).isEqualTo(DEFAULT_PESO_EXAME);
        assertThat(testPlanoCurricular.getPesoExameEspecial()).isEqualTo(DEFAULT_PESO_EXAME_ESPECIAL);
        assertThat(testPlanoCurricular.getPesoNotaCoselho()).isEqualTo(DEFAULT_PESO_NOTA_COSELHO);
        assertThat(testPlanoCurricular.getSiglaProva1()).isEqualTo(DEFAULT_SIGLA_PROVA_1);
        assertThat(testPlanoCurricular.getSiglaProva2()).isEqualTo(DEFAULT_SIGLA_PROVA_2);
        assertThat(testPlanoCurricular.getSiglaProva3()).isEqualTo(DEFAULT_SIGLA_PROVA_3);
        assertThat(testPlanoCurricular.getSiglaMedia1()).isEqualTo(DEFAULT_SIGLA_MEDIA_1);
        assertThat(testPlanoCurricular.getSiglaMedia2()).isEqualTo(DEFAULT_SIGLA_MEDIA_2);
        assertThat(testPlanoCurricular.getSiglaMedia3()).isEqualTo(DEFAULT_SIGLA_MEDIA_3);
        assertThat(testPlanoCurricular.getFormulaMedia()).isEqualTo(DEFAULT_FORMULA_MEDIA);
        assertThat(testPlanoCurricular.getFormulaDispensa()).isEqualTo(DEFAULT_FORMULA_DISPENSA);
        assertThat(testPlanoCurricular.getFormulaExame()).isEqualTo(DEFAULT_FORMULA_EXAME);
        assertThat(testPlanoCurricular.getFormulaRecurso()).isEqualTo(DEFAULT_FORMULA_RECURSO);
        assertThat(testPlanoCurricular.getFormulaExameEspecial()).isEqualTo(DEFAULT_FORMULA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void createPlanoCurricularWithExistingId() throws Exception {
        // Create the PlanoCurricular with an existing ID
        planoCurricular.setId(1L);
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        int databaseSizeBeforeCreate = planoCurricularRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setDescricao(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormulaClassificacaoFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setFormulaClassificacaoFinal(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroDisciplinaAprovaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setNumeroDisciplinaAprova(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroDisciplinaReprovaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setNumeroDisciplinaReprova(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroDisciplinaRecursoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setNumeroDisciplinaRecurso(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroDisciplinaExameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setNumeroDisciplinaExame(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroDisciplinaExameEspecialIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setNumeroDisciplinaExameEspecial(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroFaltaReprovaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setNumeroFaltaReprova(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSiglaProva1IsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setSiglaProva1(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSiglaProva2IsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setSiglaProva2(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSiglaProva3IsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setSiglaProva3(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSiglaMedia1IsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setSiglaMedia1(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSiglaMedia2IsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setSiglaMedia2(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSiglaMedia3IsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setSiglaMedia3(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormulaMediaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setFormulaMedia(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormulaDispensaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setFormulaDispensa(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormulaExameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setFormulaExame(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormulaRecursoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setFormulaRecurso(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormulaExameEspecialIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setFormulaExameEspecial(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanoCurriculars() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList
        restPlanoCurricularMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoCurricular.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].formulaClassificacaoFinal").value(hasItem(DEFAULT_FORMULA_CLASSIFICACAO_FINAL)))
            .andExpect(jsonPath("$.[*].numeroDisciplinaAprova").value(hasItem(DEFAULT_NUMERO_DISCIPLINA_APROVA)))
            .andExpect(jsonPath("$.[*].numeroDisciplinaReprova").value(hasItem(DEFAULT_NUMERO_DISCIPLINA_REPROVA)))
            .andExpect(jsonPath("$.[*].numeroDisciplinaRecurso").value(hasItem(DEFAULT_NUMERO_DISCIPLINA_RECURSO)))
            .andExpect(jsonPath("$.[*].numeroDisciplinaExame").value(hasItem(DEFAULT_NUMERO_DISCIPLINA_EXAME)))
            .andExpect(jsonPath("$.[*].numeroDisciplinaExameEspecial").value(hasItem(DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL)))
            .andExpect(jsonPath("$.[*].numeroFaltaReprova").value(hasItem(DEFAULT_NUMERO_FALTA_REPROVA)))
            .andExpect(jsonPath("$.[*].pesoMedia1").value(hasItem(DEFAULT_PESO_MEDIA_1.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoMedia2").value(hasItem(DEFAULT_PESO_MEDIA_2.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoMedia3").value(hasItem(DEFAULT_PESO_MEDIA_3.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoRecurso").value(hasItem(DEFAULT_PESO_RECURSO.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoExame").value(hasItem(DEFAULT_PESO_EXAME.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoExameEspecial").value(hasItem(DEFAULT_PESO_EXAME_ESPECIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoNotaCoselho").value(hasItem(DEFAULT_PESO_NOTA_COSELHO.doubleValue())))
            .andExpect(jsonPath("$.[*].siglaProva1").value(hasItem(DEFAULT_SIGLA_PROVA_1)))
            .andExpect(jsonPath("$.[*].siglaProva2").value(hasItem(DEFAULT_SIGLA_PROVA_2)))
            .andExpect(jsonPath("$.[*].siglaProva3").value(hasItem(DEFAULT_SIGLA_PROVA_3)))
            .andExpect(jsonPath("$.[*].siglaMedia1").value(hasItem(DEFAULT_SIGLA_MEDIA_1)))
            .andExpect(jsonPath("$.[*].siglaMedia2").value(hasItem(DEFAULT_SIGLA_MEDIA_2)))
            .andExpect(jsonPath("$.[*].siglaMedia3").value(hasItem(DEFAULT_SIGLA_MEDIA_3)))
            .andExpect(jsonPath("$.[*].formulaMedia").value(hasItem(DEFAULT_FORMULA_MEDIA)))
            .andExpect(jsonPath("$.[*].formulaDispensa").value(hasItem(DEFAULT_FORMULA_DISPENSA)))
            .andExpect(jsonPath("$.[*].formulaExame").value(hasItem(DEFAULT_FORMULA_EXAME)))
            .andExpect(jsonPath("$.[*].formulaRecurso").value(hasItem(DEFAULT_FORMULA_RECURSO)))
            .andExpect(jsonPath("$.[*].formulaExameEspecial").value(hasItem(DEFAULT_FORMULA_EXAME_ESPECIAL)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlanoCurricularsWithEagerRelationshipsIsEnabled() throws Exception {
        when(planoCurricularServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlanoCurricularMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(planoCurricularServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlanoCurricularsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(planoCurricularServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlanoCurricularMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(planoCurricularRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPlanoCurricular() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get the planoCurricular
        restPlanoCurricularMockMvc
            .perform(get(ENTITY_API_URL_ID, planoCurricular.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoCurricular.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.formulaClassificacaoFinal").value(DEFAULT_FORMULA_CLASSIFICACAO_FINAL))
            .andExpect(jsonPath("$.numeroDisciplinaAprova").value(DEFAULT_NUMERO_DISCIPLINA_APROVA))
            .andExpect(jsonPath("$.numeroDisciplinaReprova").value(DEFAULT_NUMERO_DISCIPLINA_REPROVA))
            .andExpect(jsonPath("$.numeroDisciplinaRecurso").value(DEFAULT_NUMERO_DISCIPLINA_RECURSO))
            .andExpect(jsonPath("$.numeroDisciplinaExame").value(DEFAULT_NUMERO_DISCIPLINA_EXAME))
            .andExpect(jsonPath("$.numeroDisciplinaExameEspecial").value(DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL))
            .andExpect(jsonPath("$.numeroFaltaReprova").value(DEFAULT_NUMERO_FALTA_REPROVA))
            .andExpect(jsonPath("$.pesoMedia1").value(DEFAULT_PESO_MEDIA_1.doubleValue()))
            .andExpect(jsonPath("$.pesoMedia2").value(DEFAULT_PESO_MEDIA_2.doubleValue()))
            .andExpect(jsonPath("$.pesoMedia3").value(DEFAULT_PESO_MEDIA_3.doubleValue()))
            .andExpect(jsonPath("$.pesoRecurso").value(DEFAULT_PESO_RECURSO.doubleValue()))
            .andExpect(jsonPath("$.pesoExame").value(DEFAULT_PESO_EXAME.doubleValue()))
            .andExpect(jsonPath("$.pesoExameEspecial").value(DEFAULT_PESO_EXAME_ESPECIAL.doubleValue()))
            .andExpect(jsonPath("$.pesoNotaCoselho").value(DEFAULT_PESO_NOTA_COSELHO.doubleValue()))
            .andExpect(jsonPath("$.siglaProva1").value(DEFAULT_SIGLA_PROVA_1))
            .andExpect(jsonPath("$.siglaProva2").value(DEFAULT_SIGLA_PROVA_2))
            .andExpect(jsonPath("$.siglaProva3").value(DEFAULT_SIGLA_PROVA_3))
            .andExpect(jsonPath("$.siglaMedia1").value(DEFAULT_SIGLA_MEDIA_1))
            .andExpect(jsonPath("$.siglaMedia2").value(DEFAULT_SIGLA_MEDIA_2))
            .andExpect(jsonPath("$.siglaMedia3").value(DEFAULT_SIGLA_MEDIA_3))
            .andExpect(jsonPath("$.formulaMedia").value(DEFAULT_FORMULA_MEDIA))
            .andExpect(jsonPath("$.formulaDispensa").value(DEFAULT_FORMULA_DISPENSA))
            .andExpect(jsonPath("$.formulaExame").value(DEFAULT_FORMULA_EXAME))
            .andExpect(jsonPath("$.formulaRecurso").value(DEFAULT_FORMULA_RECURSO))
            .andExpect(jsonPath("$.formulaExameEspecial").value(DEFAULT_FORMULA_EXAME_ESPECIAL));
    }

    @Test
    @Transactional
    void getPlanoCurricularsByIdFiltering() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        Long id = planoCurricular.getId();

        defaultPlanoCurricularShouldBeFound("id.equals=" + id);
        defaultPlanoCurricularShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoCurricularShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoCurricularShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoCurricularShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoCurricularShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where descricao equals to DEFAULT_DESCRICAO
        defaultPlanoCurricularShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the planoCurricularList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoCurricularShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPlanoCurricularShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the planoCurricularList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoCurricularShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where descricao is not null
        defaultPlanoCurricularShouldBeFound("descricao.specified=true");

        // Get all the planoCurricularList where descricao is null
        defaultPlanoCurricularShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where descricao contains DEFAULT_DESCRICAO
        defaultPlanoCurricularShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the planoCurricularList where descricao contains UPDATED_DESCRICAO
        defaultPlanoCurricularShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where descricao does not contain DEFAULT_DESCRICAO
        defaultPlanoCurricularShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the planoCurricularList where descricao does not contain UPDATED_DESCRICAO
        defaultPlanoCurricularShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaClassificacaoFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaClassificacaoFinal equals to DEFAULT_FORMULA_CLASSIFICACAO_FINAL
        defaultPlanoCurricularShouldBeFound("formulaClassificacaoFinal.equals=" + DEFAULT_FORMULA_CLASSIFICACAO_FINAL);

        // Get all the planoCurricularList where formulaClassificacaoFinal equals to UPDATED_FORMULA_CLASSIFICACAO_FINAL
        defaultPlanoCurricularShouldNotBeFound("formulaClassificacaoFinal.equals=" + UPDATED_FORMULA_CLASSIFICACAO_FINAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaClassificacaoFinalIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaClassificacaoFinal in DEFAULT_FORMULA_CLASSIFICACAO_FINAL or UPDATED_FORMULA_CLASSIFICACAO_FINAL
        defaultPlanoCurricularShouldBeFound(
            "formulaClassificacaoFinal.in=" + DEFAULT_FORMULA_CLASSIFICACAO_FINAL + "," + UPDATED_FORMULA_CLASSIFICACAO_FINAL
        );

        // Get all the planoCurricularList where formulaClassificacaoFinal equals to UPDATED_FORMULA_CLASSIFICACAO_FINAL
        defaultPlanoCurricularShouldNotBeFound("formulaClassificacaoFinal.in=" + UPDATED_FORMULA_CLASSIFICACAO_FINAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaClassificacaoFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaClassificacaoFinal is not null
        defaultPlanoCurricularShouldBeFound("formulaClassificacaoFinal.specified=true");

        // Get all the planoCurricularList where formulaClassificacaoFinal is null
        defaultPlanoCurricularShouldNotBeFound("formulaClassificacaoFinal.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaClassificacaoFinalContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaClassificacaoFinal contains DEFAULT_FORMULA_CLASSIFICACAO_FINAL
        defaultPlanoCurricularShouldBeFound("formulaClassificacaoFinal.contains=" + DEFAULT_FORMULA_CLASSIFICACAO_FINAL);

        // Get all the planoCurricularList where formulaClassificacaoFinal contains UPDATED_FORMULA_CLASSIFICACAO_FINAL
        defaultPlanoCurricularShouldNotBeFound("formulaClassificacaoFinal.contains=" + UPDATED_FORMULA_CLASSIFICACAO_FINAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaClassificacaoFinalNotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaClassificacaoFinal does not contain DEFAULT_FORMULA_CLASSIFICACAO_FINAL
        defaultPlanoCurricularShouldNotBeFound("formulaClassificacaoFinal.doesNotContain=" + DEFAULT_FORMULA_CLASSIFICACAO_FINAL);

        // Get all the planoCurricularList where formulaClassificacaoFinal does not contain UPDATED_FORMULA_CLASSIFICACAO_FINAL
        defaultPlanoCurricularShouldBeFound("formulaClassificacaoFinal.doesNotContain=" + UPDATED_FORMULA_CLASSIFICACAO_FINAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaAprovaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaAprova equals to DEFAULT_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaAprova.equals=" + DEFAULT_NUMERO_DISCIPLINA_APROVA);

        // Get all the planoCurricularList where numeroDisciplinaAprova equals to UPDATED_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaAprova.equals=" + UPDATED_NUMERO_DISCIPLINA_APROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaAprovaIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaAprova in DEFAULT_NUMERO_DISCIPLINA_APROVA or UPDATED_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldBeFound(
            "numeroDisciplinaAprova.in=" + DEFAULT_NUMERO_DISCIPLINA_APROVA + "," + UPDATED_NUMERO_DISCIPLINA_APROVA
        );

        // Get all the planoCurricularList where numeroDisciplinaAprova equals to UPDATED_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaAprova.in=" + UPDATED_NUMERO_DISCIPLINA_APROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaAprovaIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaAprova is not null
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaAprova.specified=true");

        // Get all the planoCurricularList where numeroDisciplinaAprova is null
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaAprova.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaAprovaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaAprova is greater than or equal to DEFAULT_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaAprova.greaterThanOrEqual=" + DEFAULT_NUMERO_DISCIPLINA_APROVA);

        // Get all the planoCurricularList where numeroDisciplinaAprova is greater than or equal to UPDATED_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaAprova.greaterThanOrEqual=" + UPDATED_NUMERO_DISCIPLINA_APROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaAprovaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaAprova is less than or equal to DEFAULT_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaAprova.lessThanOrEqual=" + DEFAULT_NUMERO_DISCIPLINA_APROVA);

        // Get all the planoCurricularList where numeroDisciplinaAprova is less than or equal to SMALLER_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaAprova.lessThanOrEqual=" + SMALLER_NUMERO_DISCIPLINA_APROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaAprovaIsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaAprova is less than DEFAULT_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaAprova.lessThan=" + DEFAULT_NUMERO_DISCIPLINA_APROVA);

        // Get all the planoCurricularList where numeroDisciplinaAprova is less than UPDATED_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaAprova.lessThan=" + UPDATED_NUMERO_DISCIPLINA_APROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaAprovaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaAprova is greater than DEFAULT_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaAprova.greaterThan=" + DEFAULT_NUMERO_DISCIPLINA_APROVA);

        // Get all the planoCurricularList where numeroDisciplinaAprova is greater than SMALLER_NUMERO_DISCIPLINA_APROVA
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaAprova.greaterThan=" + SMALLER_NUMERO_DISCIPLINA_APROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaReprovaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaReprova equals to DEFAULT_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaReprova.equals=" + DEFAULT_NUMERO_DISCIPLINA_REPROVA);

        // Get all the planoCurricularList where numeroDisciplinaReprova equals to UPDATED_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaReprova.equals=" + UPDATED_NUMERO_DISCIPLINA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaReprovaIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaReprova in DEFAULT_NUMERO_DISCIPLINA_REPROVA or UPDATED_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldBeFound(
            "numeroDisciplinaReprova.in=" + DEFAULT_NUMERO_DISCIPLINA_REPROVA + "," + UPDATED_NUMERO_DISCIPLINA_REPROVA
        );

        // Get all the planoCurricularList where numeroDisciplinaReprova equals to UPDATED_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaReprova.in=" + UPDATED_NUMERO_DISCIPLINA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaReprovaIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaReprova is not null
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaReprova.specified=true");

        // Get all the planoCurricularList where numeroDisciplinaReprova is null
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaReprova.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaReprovaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaReprova is greater than or equal to DEFAULT_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaReprova.greaterThanOrEqual=" + DEFAULT_NUMERO_DISCIPLINA_REPROVA);

        // Get all the planoCurricularList where numeroDisciplinaReprova is greater than or equal to UPDATED_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaReprova.greaterThanOrEqual=" + UPDATED_NUMERO_DISCIPLINA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaReprovaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaReprova is less than or equal to DEFAULT_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaReprova.lessThanOrEqual=" + DEFAULT_NUMERO_DISCIPLINA_REPROVA);

        // Get all the planoCurricularList where numeroDisciplinaReprova is less than or equal to SMALLER_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaReprova.lessThanOrEqual=" + SMALLER_NUMERO_DISCIPLINA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaReprovaIsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaReprova is less than DEFAULT_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaReprova.lessThan=" + DEFAULT_NUMERO_DISCIPLINA_REPROVA);

        // Get all the planoCurricularList where numeroDisciplinaReprova is less than UPDATED_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaReprova.lessThan=" + UPDATED_NUMERO_DISCIPLINA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaReprovaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaReprova is greater than DEFAULT_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaReprova.greaterThan=" + DEFAULT_NUMERO_DISCIPLINA_REPROVA);

        // Get all the planoCurricularList where numeroDisciplinaReprova is greater than SMALLER_NUMERO_DISCIPLINA_REPROVA
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaReprova.greaterThan=" + SMALLER_NUMERO_DISCIPLINA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaRecurso equals to DEFAULT_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaRecurso.equals=" + DEFAULT_NUMERO_DISCIPLINA_RECURSO);

        // Get all the planoCurricularList where numeroDisciplinaRecurso equals to UPDATED_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaRecurso.equals=" + UPDATED_NUMERO_DISCIPLINA_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaRecursoIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaRecurso in DEFAULT_NUMERO_DISCIPLINA_RECURSO or UPDATED_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldBeFound(
            "numeroDisciplinaRecurso.in=" + DEFAULT_NUMERO_DISCIPLINA_RECURSO + "," + UPDATED_NUMERO_DISCIPLINA_RECURSO
        );

        // Get all the planoCurricularList where numeroDisciplinaRecurso equals to UPDATED_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaRecurso.in=" + UPDATED_NUMERO_DISCIPLINA_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaRecursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaRecurso is not null
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaRecurso.specified=true");

        // Get all the planoCurricularList where numeroDisciplinaRecurso is null
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaRecurso.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaRecursoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaRecurso is greater than or equal to DEFAULT_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaRecurso.greaterThanOrEqual=" + DEFAULT_NUMERO_DISCIPLINA_RECURSO);

        // Get all the planoCurricularList where numeroDisciplinaRecurso is greater than or equal to UPDATED_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaRecurso.greaterThanOrEqual=" + UPDATED_NUMERO_DISCIPLINA_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaRecursoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaRecurso is less than or equal to DEFAULT_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaRecurso.lessThanOrEqual=" + DEFAULT_NUMERO_DISCIPLINA_RECURSO);

        // Get all the planoCurricularList where numeroDisciplinaRecurso is less than or equal to SMALLER_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaRecurso.lessThanOrEqual=" + SMALLER_NUMERO_DISCIPLINA_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaRecursoIsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaRecurso is less than DEFAULT_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaRecurso.lessThan=" + DEFAULT_NUMERO_DISCIPLINA_RECURSO);

        // Get all the planoCurricularList where numeroDisciplinaRecurso is less than UPDATED_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaRecurso.lessThan=" + UPDATED_NUMERO_DISCIPLINA_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaRecursoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaRecurso is greater than DEFAULT_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaRecurso.greaterThan=" + DEFAULT_NUMERO_DISCIPLINA_RECURSO);

        // Get all the planoCurricularList where numeroDisciplinaRecurso is greater than SMALLER_NUMERO_DISCIPLINA_RECURSO
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaRecurso.greaterThan=" + SMALLER_NUMERO_DISCIPLINA_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExame equals to DEFAULT_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExame.equals=" + DEFAULT_NUMERO_DISCIPLINA_EXAME);

        // Get all the planoCurricularList where numeroDisciplinaExame equals to UPDATED_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExame.equals=" + UPDATED_NUMERO_DISCIPLINA_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExame in DEFAULT_NUMERO_DISCIPLINA_EXAME or UPDATED_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldBeFound(
            "numeroDisciplinaExame.in=" + DEFAULT_NUMERO_DISCIPLINA_EXAME + "," + UPDATED_NUMERO_DISCIPLINA_EXAME
        );

        // Get all the planoCurricularList where numeroDisciplinaExame equals to UPDATED_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExame.in=" + UPDATED_NUMERO_DISCIPLINA_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExame is not null
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExame.specified=true");

        // Get all the planoCurricularList where numeroDisciplinaExame is null
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExame.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExame is greater than or equal to DEFAULT_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExame.greaterThanOrEqual=" + DEFAULT_NUMERO_DISCIPLINA_EXAME);

        // Get all the planoCurricularList where numeroDisciplinaExame is greater than or equal to UPDATED_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExame.greaterThanOrEqual=" + UPDATED_NUMERO_DISCIPLINA_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExame is less than or equal to DEFAULT_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExame.lessThanOrEqual=" + DEFAULT_NUMERO_DISCIPLINA_EXAME);

        // Get all the planoCurricularList where numeroDisciplinaExame is less than or equal to SMALLER_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExame.lessThanOrEqual=" + SMALLER_NUMERO_DISCIPLINA_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameIsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExame is less than DEFAULT_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExame.lessThan=" + DEFAULT_NUMERO_DISCIPLINA_EXAME);

        // Get all the planoCurricularList where numeroDisciplinaExame is less than UPDATED_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExame.lessThan=" + UPDATED_NUMERO_DISCIPLINA_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExame is greater than DEFAULT_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExame.greaterThan=" + DEFAULT_NUMERO_DISCIPLINA_EXAME);

        // Get all the planoCurricularList where numeroDisciplinaExame is greater than SMALLER_NUMERO_DISCIPLINA_EXAME
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExame.greaterThan=" + SMALLER_NUMERO_DISCIPLINA_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameEspecialIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial equals to DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExameEspecial.equals=" + DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial equals to UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExameEspecial.equals=" + UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameEspecialIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial in DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL or UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound(
            "numeroDisciplinaExameEspecial.in=" + DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL + "," + UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        );

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial equals to UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExameEspecial.in=" + UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameEspecialIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial is not null
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExameEspecial.specified=true");

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial is null
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExameEspecial.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameEspecialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial is greater than or equal to DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExameEspecial.greaterThanOrEqual=" + DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial is greater than or equal to UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound(
            "numeroDisciplinaExameEspecial.greaterThanOrEqual=" + UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        );
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameEspecialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial is less than or equal to DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExameEspecial.lessThanOrEqual=" + DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial is less than or equal to SMALLER_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExameEspecial.lessThanOrEqual=" + SMALLER_NUMERO_DISCIPLINA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameEspecialIsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial is less than DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExameEspecial.lessThan=" + DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial is less than UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExameEspecial.lessThan=" + UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroDisciplinaExameEspecialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial is greater than DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("numeroDisciplinaExameEspecial.greaterThan=" + DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL);

        // Get all the planoCurricularList where numeroDisciplinaExameEspecial is greater than SMALLER_NUMERO_DISCIPLINA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("numeroDisciplinaExameEspecial.greaterThan=" + SMALLER_NUMERO_DISCIPLINA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroFaltaReprovaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroFaltaReprova equals to DEFAULT_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldBeFound("numeroFaltaReprova.equals=" + DEFAULT_NUMERO_FALTA_REPROVA);

        // Get all the planoCurricularList where numeroFaltaReprova equals to UPDATED_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroFaltaReprova.equals=" + UPDATED_NUMERO_FALTA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroFaltaReprovaIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroFaltaReprova in DEFAULT_NUMERO_FALTA_REPROVA or UPDATED_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldBeFound("numeroFaltaReprova.in=" + DEFAULT_NUMERO_FALTA_REPROVA + "," + UPDATED_NUMERO_FALTA_REPROVA);

        // Get all the planoCurricularList where numeroFaltaReprova equals to UPDATED_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroFaltaReprova.in=" + UPDATED_NUMERO_FALTA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroFaltaReprovaIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroFaltaReprova is not null
        defaultPlanoCurricularShouldBeFound("numeroFaltaReprova.specified=true");

        // Get all the planoCurricularList where numeroFaltaReprova is null
        defaultPlanoCurricularShouldNotBeFound("numeroFaltaReprova.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroFaltaReprovaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroFaltaReprova is greater than or equal to DEFAULT_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldBeFound("numeroFaltaReprova.greaterThanOrEqual=" + DEFAULT_NUMERO_FALTA_REPROVA);

        // Get all the planoCurricularList where numeroFaltaReprova is greater than or equal to UPDATED_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroFaltaReprova.greaterThanOrEqual=" + UPDATED_NUMERO_FALTA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroFaltaReprovaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroFaltaReprova is less than or equal to DEFAULT_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldBeFound("numeroFaltaReprova.lessThanOrEqual=" + DEFAULT_NUMERO_FALTA_REPROVA);

        // Get all the planoCurricularList where numeroFaltaReprova is less than or equal to SMALLER_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroFaltaReprova.lessThanOrEqual=" + SMALLER_NUMERO_FALTA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroFaltaReprovaIsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroFaltaReprova is less than DEFAULT_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroFaltaReprova.lessThan=" + DEFAULT_NUMERO_FALTA_REPROVA);

        // Get all the planoCurricularList where numeroFaltaReprova is less than UPDATED_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldBeFound("numeroFaltaReprova.lessThan=" + UPDATED_NUMERO_FALTA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByNumeroFaltaReprovaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where numeroFaltaReprova is greater than DEFAULT_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldNotBeFound("numeroFaltaReprova.greaterThan=" + DEFAULT_NUMERO_FALTA_REPROVA);

        // Get all the planoCurricularList where numeroFaltaReprova is greater than SMALLER_NUMERO_FALTA_REPROVA
        defaultPlanoCurricularShouldBeFound("numeroFaltaReprova.greaterThan=" + SMALLER_NUMERO_FALTA_REPROVA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia1IsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia1 equals to DEFAULT_PESO_MEDIA_1
        defaultPlanoCurricularShouldBeFound("pesoMedia1.equals=" + DEFAULT_PESO_MEDIA_1);

        // Get all the planoCurricularList where pesoMedia1 equals to UPDATED_PESO_MEDIA_1
        defaultPlanoCurricularShouldNotBeFound("pesoMedia1.equals=" + UPDATED_PESO_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia1IsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia1 in DEFAULT_PESO_MEDIA_1 or UPDATED_PESO_MEDIA_1
        defaultPlanoCurricularShouldBeFound("pesoMedia1.in=" + DEFAULT_PESO_MEDIA_1 + "," + UPDATED_PESO_MEDIA_1);

        // Get all the planoCurricularList where pesoMedia1 equals to UPDATED_PESO_MEDIA_1
        defaultPlanoCurricularShouldNotBeFound("pesoMedia1.in=" + UPDATED_PESO_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia1IsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia1 is not null
        defaultPlanoCurricularShouldBeFound("pesoMedia1.specified=true");

        // Get all the planoCurricularList where pesoMedia1 is null
        defaultPlanoCurricularShouldNotBeFound("pesoMedia1.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia1 is greater than or equal to DEFAULT_PESO_MEDIA_1
        defaultPlanoCurricularShouldBeFound("pesoMedia1.greaterThanOrEqual=" + DEFAULT_PESO_MEDIA_1);

        // Get all the planoCurricularList where pesoMedia1 is greater than or equal to (DEFAULT_PESO_MEDIA_1 + 1)
        defaultPlanoCurricularShouldNotBeFound("pesoMedia1.greaterThanOrEqual=" + (DEFAULT_PESO_MEDIA_1 + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia1 is less than or equal to DEFAULT_PESO_MEDIA_1
        defaultPlanoCurricularShouldBeFound("pesoMedia1.lessThanOrEqual=" + DEFAULT_PESO_MEDIA_1);

        // Get all the planoCurricularList where pesoMedia1 is less than or equal to SMALLER_PESO_MEDIA_1
        defaultPlanoCurricularShouldNotBeFound("pesoMedia1.lessThanOrEqual=" + SMALLER_PESO_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia1IsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia1 is less than DEFAULT_PESO_MEDIA_1
        defaultPlanoCurricularShouldNotBeFound("pesoMedia1.lessThan=" + DEFAULT_PESO_MEDIA_1);

        // Get all the planoCurricularList where pesoMedia1 is less than (DEFAULT_PESO_MEDIA_1 + 1)
        defaultPlanoCurricularShouldBeFound("pesoMedia1.lessThan=" + (DEFAULT_PESO_MEDIA_1 + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia1 is greater than DEFAULT_PESO_MEDIA_1
        defaultPlanoCurricularShouldNotBeFound("pesoMedia1.greaterThan=" + DEFAULT_PESO_MEDIA_1);

        // Get all the planoCurricularList where pesoMedia1 is greater than SMALLER_PESO_MEDIA_1
        defaultPlanoCurricularShouldBeFound("pesoMedia1.greaterThan=" + SMALLER_PESO_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia2IsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia2 equals to DEFAULT_PESO_MEDIA_2
        defaultPlanoCurricularShouldBeFound("pesoMedia2.equals=" + DEFAULT_PESO_MEDIA_2);

        // Get all the planoCurricularList where pesoMedia2 equals to UPDATED_PESO_MEDIA_2
        defaultPlanoCurricularShouldNotBeFound("pesoMedia2.equals=" + UPDATED_PESO_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia2IsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia2 in DEFAULT_PESO_MEDIA_2 or UPDATED_PESO_MEDIA_2
        defaultPlanoCurricularShouldBeFound("pesoMedia2.in=" + DEFAULT_PESO_MEDIA_2 + "," + UPDATED_PESO_MEDIA_2);

        // Get all the planoCurricularList where pesoMedia2 equals to UPDATED_PESO_MEDIA_2
        defaultPlanoCurricularShouldNotBeFound("pesoMedia2.in=" + UPDATED_PESO_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia2IsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia2 is not null
        defaultPlanoCurricularShouldBeFound("pesoMedia2.specified=true");

        // Get all the planoCurricularList where pesoMedia2 is null
        defaultPlanoCurricularShouldNotBeFound("pesoMedia2.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia2 is greater than or equal to DEFAULT_PESO_MEDIA_2
        defaultPlanoCurricularShouldBeFound("pesoMedia2.greaterThanOrEqual=" + DEFAULT_PESO_MEDIA_2);

        // Get all the planoCurricularList where pesoMedia2 is greater than or equal to (DEFAULT_PESO_MEDIA_2 + 1)
        defaultPlanoCurricularShouldNotBeFound("pesoMedia2.greaterThanOrEqual=" + (DEFAULT_PESO_MEDIA_2 + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia2 is less than or equal to DEFAULT_PESO_MEDIA_2
        defaultPlanoCurricularShouldBeFound("pesoMedia2.lessThanOrEqual=" + DEFAULT_PESO_MEDIA_2);

        // Get all the planoCurricularList where pesoMedia2 is less than or equal to SMALLER_PESO_MEDIA_2
        defaultPlanoCurricularShouldNotBeFound("pesoMedia2.lessThanOrEqual=" + SMALLER_PESO_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia2IsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia2 is less than DEFAULT_PESO_MEDIA_2
        defaultPlanoCurricularShouldNotBeFound("pesoMedia2.lessThan=" + DEFAULT_PESO_MEDIA_2);

        // Get all the planoCurricularList where pesoMedia2 is less than (DEFAULT_PESO_MEDIA_2 + 1)
        defaultPlanoCurricularShouldBeFound("pesoMedia2.lessThan=" + (DEFAULT_PESO_MEDIA_2 + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia2 is greater than DEFAULT_PESO_MEDIA_2
        defaultPlanoCurricularShouldNotBeFound("pesoMedia2.greaterThan=" + DEFAULT_PESO_MEDIA_2);

        // Get all the planoCurricularList where pesoMedia2 is greater than SMALLER_PESO_MEDIA_2
        defaultPlanoCurricularShouldBeFound("pesoMedia2.greaterThan=" + SMALLER_PESO_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia3IsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia3 equals to DEFAULT_PESO_MEDIA_3
        defaultPlanoCurricularShouldBeFound("pesoMedia3.equals=" + DEFAULT_PESO_MEDIA_3);

        // Get all the planoCurricularList where pesoMedia3 equals to UPDATED_PESO_MEDIA_3
        defaultPlanoCurricularShouldNotBeFound("pesoMedia3.equals=" + UPDATED_PESO_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia3IsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia3 in DEFAULT_PESO_MEDIA_3 or UPDATED_PESO_MEDIA_3
        defaultPlanoCurricularShouldBeFound("pesoMedia3.in=" + DEFAULT_PESO_MEDIA_3 + "," + UPDATED_PESO_MEDIA_3);

        // Get all the planoCurricularList where pesoMedia3 equals to UPDATED_PESO_MEDIA_3
        defaultPlanoCurricularShouldNotBeFound("pesoMedia3.in=" + UPDATED_PESO_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia3IsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia3 is not null
        defaultPlanoCurricularShouldBeFound("pesoMedia3.specified=true");

        // Get all the planoCurricularList where pesoMedia3 is null
        defaultPlanoCurricularShouldNotBeFound("pesoMedia3.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia3 is greater than or equal to DEFAULT_PESO_MEDIA_3
        defaultPlanoCurricularShouldBeFound("pesoMedia3.greaterThanOrEqual=" + DEFAULT_PESO_MEDIA_3);

        // Get all the planoCurricularList where pesoMedia3 is greater than or equal to (DEFAULT_PESO_MEDIA_3 + 1)
        defaultPlanoCurricularShouldNotBeFound("pesoMedia3.greaterThanOrEqual=" + (DEFAULT_PESO_MEDIA_3 + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia3 is less than or equal to DEFAULT_PESO_MEDIA_3
        defaultPlanoCurricularShouldBeFound("pesoMedia3.lessThanOrEqual=" + DEFAULT_PESO_MEDIA_3);

        // Get all the planoCurricularList where pesoMedia3 is less than or equal to SMALLER_PESO_MEDIA_3
        defaultPlanoCurricularShouldNotBeFound("pesoMedia3.lessThanOrEqual=" + SMALLER_PESO_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia3IsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia3 is less than DEFAULT_PESO_MEDIA_3
        defaultPlanoCurricularShouldNotBeFound("pesoMedia3.lessThan=" + DEFAULT_PESO_MEDIA_3);

        // Get all the planoCurricularList where pesoMedia3 is less than (DEFAULT_PESO_MEDIA_3 + 1)
        defaultPlanoCurricularShouldBeFound("pesoMedia3.lessThan=" + (DEFAULT_PESO_MEDIA_3 + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoMedia3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoMedia3 is greater than DEFAULT_PESO_MEDIA_3
        defaultPlanoCurricularShouldNotBeFound("pesoMedia3.greaterThan=" + DEFAULT_PESO_MEDIA_3);

        // Get all the planoCurricularList where pesoMedia3 is greater than SMALLER_PESO_MEDIA_3
        defaultPlanoCurricularShouldBeFound("pesoMedia3.greaterThan=" + SMALLER_PESO_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoRecurso equals to DEFAULT_PESO_RECURSO
        defaultPlanoCurricularShouldBeFound("pesoRecurso.equals=" + DEFAULT_PESO_RECURSO);

        // Get all the planoCurricularList where pesoRecurso equals to UPDATED_PESO_RECURSO
        defaultPlanoCurricularShouldNotBeFound("pesoRecurso.equals=" + UPDATED_PESO_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoRecursoIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoRecurso in DEFAULT_PESO_RECURSO or UPDATED_PESO_RECURSO
        defaultPlanoCurricularShouldBeFound("pesoRecurso.in=" + DEFAULT_PESO_RECURSO + "," + UPDATED_PESO_RECURSO);

        // Get all the planoCurricularList where pesoRecurso equals to UPDATED_PESO_RECURSO
        defaultPlanoCurricularShouldNotBeFound("pesoRecurso.in=" + UPDATED_PESO_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoRecursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoRecurso is not null
        defaultPlanoCurricularShouldBeFound("pesoRecurso.specified=true");

        // Get all the planoCurricularList where pesoRecurso is null
        defaultPlanoCurricularShouldNotBeFound("pesoRecurso.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoRecursoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoRecurso is greater than or equal to DEFAULT_PESO_RECURSO
        defaultPlanoCurricularShouldBeFound("pesoRecurso.greaterThanOrEqual=" + DEFAULT_PESO_RECURSO);

        // Get all the planoCurricularList where pesoRecurso is greater than or equal to (DEFAULT_PESO_RECURSO + 1)
        defaultPlanoCurricularShouldNotBeFound("pesoRecurso.greaterThanOrEqual=" + (DEFAULT_PESO_RECURSO + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoRecursoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoRecurso is less than or equal to DEFAULT_PESO_RECURSO
        defaultPlanoCurricularShouldBeFound("pesoRecurso.lessThanOrEqual=" + DEFAULT_PESO_RECURSO);

        // Get all the planoCurricularList where pesoRecurso is less than or equal to SMALLER_PESO_RECURSO
        defaultPlanoCurricularShouldNotBeFound("pesoRecurso.lessThanOrEqual=" + SMALLER_PESO_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoRecursoIsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoRecurso is less than DEFAULT_PESO_RECURSO
        defaultPlanoCurricularShouldNotBeFound("pesoRecurso.lessThan=" + DEFAULT_PESO_RECURSO);

        // Get all the planoCurricularList where pesoRecurso is less than (DEFAULT_PESO_RECURSO + 1)
        defaultPlanoCurricularShouldBeFound("pesoRecurso.lessThan=" + (DEFAULT_PESO_RECURSO + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoRecursoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoRecurso is greater than DEFAULT_PESO_RECURSO
        defaultPlanoCurricularShouldNotBeFound("pesoRecurso.greaterThan=" + DEFAULT_PESO_RECURSO);

        // Get all the planoCurricularList where pesoRecurso is greater than SMALLER_PESO_RECURSO
        defaultPlanoCurricularShouldBeFound("pesoRecurso.greaterThan=" + SMALLER_PESO_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExame equals to DEFAULT_PESO_EXAME
        defaultPlanoCurricularShouldBeFound("pesoExame.equals=" + DEFAULT_PESO_EXAME);

        // Get all the planoCurricularList where pesoExame equals to UPDATED_PESO_EXAME
        defaultPlanoCurricularShouldNotBeFound("pesoExame.equals=" + UPDATED_PESO_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExame in DEFAULT_PESO_EXAME or UPDATED_PESO_EXAME
        defaultPlanoCurricularShouldBeFound("pesoExame.in=" + DEFAULT_PESO_EXAME + "," + UPDATED_PESO_EXAME);

        // Get all the planoCurricularList where pesoExame equals to UPDATED_PESO_EXAME
        defaultPlanoCurricularShouldNotBeFound("pesoExame.in=" + UPDATED_PESO_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExame is not null
        defaultPlanoCurricularShouldBeFound("pesoExame.specified=true");

        // Get all the planoCurricularList where pesoExame is null
        defaultPlanoCurricularShouldNotBeFound("pesoExame.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExame is greater than or equal to DEFAULT_PESO_EXAME
        defaultPlanoCurricularShouldBeFound("pesoExame.greaterThanOrEqual=" + DEFAULT_PESO_EXAME);

        // Get all the planoCurricularList where pesoExame is greater than or equal to (DEFAULT_PESO_EXAME + 1)
        defaultPlanoCurricularShouldNotBeFound("pesoExame.greaterThanOrEqual=" + (DEFAULT_PESO_EXAME + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExame is less than or equal to DEFAULT_PESO_EXAME
        defaultPlanoCurricularShouldBeFound("pesoExame.lessThanOrEqual=" + DEFAULT_PESO_EXAME);

        // Get all the planoCurricularList where pesoExame is less than or equal to SMALLER_PESO_EXAME
        defaultPlanoCurricularShouldNotBeFound("pesoExame.lessThanOrEqual=" + SMALLER_PESO_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameIsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExame is less than DEFAULT_PESO_EXAME
        defaultPlanoCurricularShouldNotBeFound("pesoExame.lessThan=" + DEFAULT_PESO_EXAME);

        // Get all the planoCurricularList where pesoExame is less than (DEFAULT_PESO_EXAME + 1)
        defaultPlanoCurricularShouldBeFound("pesoExame.lessThan=" + (DEFAULT_PESO_EXAME + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExame is greater than DEFAULT_PESO_EXAME
        defaultPlanoCurricularShouldNotBeFound("pesoExame.greaterThan=" + DEFAULT_PESO_EXAME);

        // Get all the planoCurricularList where pesoExame is greater than SMALLER_PESO_EXAME
        defaultPlanoCurricularShouldBeFound("pesoExame.greaterThan=" + SMALLER_PESO_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameEspecialIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExameEspecial equals to DEFAULT_PESO_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("pesoExameEspecial.equals=" + DEFAULT_PESO_EXAME_ESPECIAL);

        // Get all the planoCurricularList where pesoExameEspecial equals to UPDATED_PESO_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("pesoExameEspecial.equals=" + UPDATED_PESO_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameEspecialIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExameEspecial in DEFAULT_PESO_EXAME_ESPECIAL or UPDATED_PESO_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("pesoExameEspecial.in=" + DEFAULT_PESO_EXAME_ESPECIAL + "," + UPDATED_PESO_EXAME_ESPECIAL);

        // Get all the planoCurricularList where pesoExameEspecial equals to UPDATED_PESO_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("pesoExameEspecial.in=" + UPDATED_PESO_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameEspecialIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExameEspecial is not null
        defaultPlanoCurricularShouldBeFound("pesoExameEspecial.specified=true");

        // Get all the planoCurricularList where pesoExameEspecial is null
        defaultPlanoCurricularShouldNotBeFound("pesoExameEspecial.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameEspecialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExameEspecial is greater than or equal to DEFAULT_PESO_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("pesoExameEspecial.greaterThanOrEqual=" + DEFAULT_PESO_EXAME_ESPECIAL);

        // Get all the planoCurricularList where pesoExameEspecial is greater than or equal to (DEFAULT_PESO_EXAME_ESPECIAL + 1)
        defaultPlanoCurricularShouldNotBeFound("pesoExameEspecial.greaterThanOrEqual=" + (DEFAULT_PESO_EXAME_ESPECIAL + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameEspecialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExameEspecial is less than or equal to DEFAULT_PESO_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("pesoExameEspecial.lessThanOrEqual=" + DEFAULT_PESO_EXAME_ESPECIAL);

        // Get all the planoCurricularList where pesoExameEspecial is less than or equal to SMALLER_PESO_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("pesoExameEspecial.lessThanOrEqual=" + SMALLER_PESO_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameEspecialIsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExameEspecial is less than DEFAULT_PESO_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("pesoExameEspecial.lessThan=" + DEFAULT_PESO_EXAME_ESPECIAL);

        // Get all the planoCurricularList where pesoExameEspecial is less than (DEFAULT_PESO_EXAME_ESPECIAL + 1)
        defaultPlanoCurricularShouldBeFound("pesoExameEspecial.lessThan=" + (DEFAULT_PESO_EXAME_ESPECIAL + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoExameEspecialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoExameEspecial is greater than DEFAULT_PESO_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("pesoExameEspecial.greaterThan=" + DEFAULT_PESO_EXAME_ESPECIAL);

        // Get all the planoCurricularList where pesoExameEspecial is greater than SMALLER_PESO_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("pesoExameEspecial.greaterThan=" + SMALLER_PESO_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoNotaCoselhoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoNotaCoselho equals to DEFAULT_PESO_NOTA_COSELHO
        defaultPlanoCurricularShouldBeFound("pesoNotaCoselho.equals=" + DEFAULT_PESO_NOTA_COSELHO);

        // Get all the planoCurricularList where pesoNotaCoselho equals to UPDATED_PESO_NOTA_COSELHO
        defaultPlanoCurricularShouldNotBeFound("pesoNotaCoselho.equals=" + UPDATED_PESO_NOTA_COSELHO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoNotaCoselhoIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoNotaCoselho in DEFAULT_PESO_NOTA_COSELHO or UPDATED_PESO_NOTA_COSELHO
        defaultPlanoCurricularShouldBeFound("pesoNotaCoselho.in=" + DEFAULT_PESO_NOTA_COSELHO + "," + UPDATED_PESO_NOTA_COSELHO);

        // Get all the planoCurricularList where pesoNotaCoselho equals to UPDATED_PESO_NOTA_COSELHO
        defaultPlanoCurricularShouldNotBeFound("pesoNotaCoselho.in=" + UPDATED_PESO_NOTA_COSELHO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoNotaCoselhoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoNotaCoselho is not null
        defaultPlanoCurricularShouldBeFound("pesoNotaCoselho.specified=true");

        // Get all the planoCurricularList where pesoNotaCoselho is null
        defaultPlanoCurricularShouldNotBeFound("pesoNotaCoselho.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoNotaCoselhoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoNotaCoselho is greater than or equal to DEFAULT_PESO_NOTA_COSELHO
        defaultPlanoCurricularShouldBeFound("pesoNotaCoselho.greaterThanOrEqual=" + DEFAULT_PESO_NOTA_COSELHO);

        // Get all the planoCurricularList where pesoNotaCoselho is greater than or equal to (DEFAULT_PESO_NOTA_COSELHO + 1)
        defaultPlanoCurricularShouldNotBeFound("pesoNotaCoselho.greaterThanOrEqual=" + (DEFAULT_PESO_NOTA_COSELHO + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoNotaCoselhoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoNotaCoselho is less than or equal to DEFAULT_PESO_NOTA_COSELHO
        defaultPlanoCurricularShouldBeFound("pesoNotaCoselho.lessThanOrEqual=" + DEFAULT_PESO_NOTA_COSELHO);

        // Get all the planoCurricularList where pesoNotaCoselho is less than or equal to SMALLER_PESO_NOTA_COSELHO
        defaultPlanoCurricularShouldNotBeFound("pesoNotaCoselho.lessThanOrEqual=" + SMALLER_PESO_NOTA_COSELHO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoNotaCoselhoIsLessThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoNotaCoselho is less than DEFAULT_PESO_NOTA_COSELHO
        defaultPlanoCurricularShouldNotBeFound("pesoNotaCoselho.lessThan=" + DEFAULT_PESO_NOTA_COSELHO);

        // Get all the planoCurricularList where pesoNotaCoselho is less than (DEFAULT_PESO_NOTA_COSELHO + 1)
        defaultPlanoCurricularShouldBeFound("pesoNotaCoselho.lessThan=" + (DEFAULT_PESO_NOTA_COSELHO + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByPesoNotaCoselhoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where pesoNotaCoselho is greater than DEFAULT_PESO_NOTA_COSELHO
        defaultPlanoCurricularShouldNotBeFound("pesoNotaCoselho.greaterThan=" + DEFAULT_PESO_NOTA_COSELHO);

        // Get all the planoCurricularList where pesoNotaCoselho is greater than SMALLER_PESO_NOTA_COSELHO
        defaultPlanoCurricularShouldBeFound("pesoNotaCoselho.greaterThan=" + SMALLER_PESO_NOTA_COSELHO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva1IsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva1 equals to DEFAULT_SIGLA_PROVA_1
        defaultPlanoCurricularShouldBeFound("siglaProva1.equals=" + DEFAULT_SIGLA_PROVA_1);

        // Get all the planoCurricularList where siglaProva1 equals to UPDATED_SIGLA_PROVA_1
        defaultPlanoCurricularShouldNotBeFound("siglaProva1.equals=" + UPDATED_SIGLA_PROVA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva1IsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva1 in DEFAULT_SIGLA_PROVA_1 or UPDATED_SIGLA_PROVA_1
        defaultPlanoCurricularShouldBeFound("siglaProva1.in=" + DEFAULT_SIGLA_PROVA_1 + "," + UPDATED_SIGLA_PROVA_1);

        // Get all the planoCurricularList where siglaProva1 equals to UPDATED_SIGLA_PROVA_1
        defaultPlanoCurricularShouldNotBeFound("siglaProva1.in=" + UPDATED_SIGLA_PROVA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva1IsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva1 is not null
        defaultPlanoCurricularShouldBeFound("siglaProva1.specified=true");

        // Get all the planoCurricularList where siglaProva1 is null
        defaultPlanoCurricularShouldNotBeFound("siglaProva1.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva1ContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva1 contains DEFAULT_SIGLA_PROVA_1
        defaultPlanoCurricularShouldBeFound("siglaProva1.contains=" + DEFAULT_SIGLA_PROVA_1);

        // Get all the planoCurricularList where siglaProva1 contains UPDATED_SIGLA_PROVA_1
        defaultPlanoCurricularShouldNotBeFound("siglaProva1.contains=" + UPDATED_SIGLA_PROVA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva1NotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva1 does not contain DEFAULT_SIGLA_PROVA_1
        defaultPlanoCurricularShouldNotBeFound("siglaProva1.doesNotContain=" + DEFAULT_SIGLA_PROVA_1);

        // Get all the planoCurricularList where siglaProva1 does not contain UPDATED_SIGLA_PROVA_1
        defaultPlanoCurricularShouldBeFound("siglaProva1.doesNotContain=" + UPDATED_SIGLA_PROVA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva2IsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva2 equals to DEFAULT_SIGLA_PROVA_2
        defaultPlanoCurricularShouldBeFound("siglaProva2.equals=" + DEFAULT_SIGLA_PROVA_2);

        // Get all the planoCurricularList where siglaProva2 equals to UPDATED_SIGLA_PROVA_2
        defaultPlanoCurricularShouldNotBeFound("siglaProva2.equals=" + UPDATED_SIGLA_PROVA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva2IsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva2 in DEFAULT_SIGLA_PROVA_2 or UPDATED_SIGLA_PROVA_2
        defaultPlanoCurricularShouldBeFound("siglaProva2.in=" + DEFAULT_SIGLA_PROVA_2 + "," + UPDATED_SIGLA_PROVA_2);

        // Get all the planoCurricularList where siglaProva2 equals to UPDATED_SIGLA_PROVA_2
        defaultPlanoCurricularShouldNotBeFound("siglaProva2.in=" + UPDATED_SIGLA_PROVA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva2IsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva2 is not null
        defaultPlanoCurricularShouldBeFound("siglaProva2.specified=true");

        // Get all the planoCurricularList where siglaProva2 is null
        defaultPlanoCurricularShouldNotBeFound("siglaProva2.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva2ContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva2 contains DEFAULT_SIGLA_PROVA_2
        defaultPlanoCurricularShouldBeFound("siglaProva2.contains=" + DEFAULT_SIGLA_PROVA_2);

        // Get all the planoCurricularList where siglaProva2 contains UPDATED_SIGLA_PROVA_2
        defaultPlanoCurricularShouldNotBeFound("siglaProva2.contains=" + UPDATED_SIGLA_PROVA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva2NotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva2 does not contain DEFAULT_SIGLA_PROVA_2
        defaultPlanoCurricularShouldNotBeFound("siglaProva2.doesNotContain=" + DEFAULT_SIGLA_PROVA_2);

        // Get all the planoCurricularList where siglaProva2 does not contain UPDATED_SIGLA_PROVA_2
        defaultPlanoCurricularShouldBeFound("siglaProva2.doesNotContain=" + UPDATED_SIGLA_PROVA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva3IsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva3 equals to DEFAULT_SIGLA_PROVA_3
        defaultPlanoCurricularShouldBeFound("siglaProva3.equals=" + DEFAULT_SIGLA_PROVA_3);

        // Get all the planoCurricularList where siglaProva3 equals to UPDATED_SIGLA_PROVA_3
        defaultPlanoCurricularShouldNotBeFound("siglaProva3.equals=" + UPDATED_SIGLA_PROVA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva3IsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva3 in DEFAULT_SIGLA_PROVA_3 or UPDATED_SIGLA_PROVA_3
        defaultPlanoCurricularShouldBeFound("siglaProva3.in=" + DEFAULT_SIGLA_PROVA_3 + "," + UPDATED_SIGLA_PROVA_3);

        // Get all the planoCurricularList where siglaProva3 equals to UPDATED_SIGLA_PROVA_3
        defaultPlanoCurricularShouldNotBeFound("siglaProva3.in=" + UPDATED_SIGLA_PROVA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva3IsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva3 is not null
        defaultPlanoCurricularShouldBeFound("siglaProva3.specified=true");

        // Get all the planoCurricularList where siglaProva3 is null
        defaultPlanoCurricularShouldNotBeFound("siglaProva3.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva3ContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva3 contains DEFAULT_SIGLA_PROVA_3
        defaultPlanoCurricularShouldBeFound("siglaProva3.contains=" + DEFAULT_SIGLA_PROVA_3);

        // Get all the planoCurricularList where siglaProva3 contains UPDATED_SIGLA_PROVA_3
        defaultPlanoCurricularShouldNotBeFound("siglaProva3.contains=" + UPDATED_SIGLA_PROVA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaProva3NotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaProva3 does not contain DEFAULT_SIGLA_PROVA_3
        defaultPlanoCurricularShouldNotBeFound("siglaProva3.doesNotContain=" + DEFAULT_SIGLA_PROVA_3);

        // Get all the planoCurricularList where siglaProva3 does not contain UPDATED_SIGLA_PROVA_3
        defaultPlanoCurricularShouldBeFound("siglaProva3.doesNotContain=" + UPDATED_SIGLA_PROVA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia1IsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia1 equals to DEFAULT_SIGLA_MEDIA_1
        defaultPlanoCurricularShouldBeFound("siglaMedia1.equals=" + DEFAULT_SIGLA_MEDIA_1);

        // Get all the planoCurricularList where siglaMedia1 equals to UPDATED_SIGLA_MEDIA_1
        defaultPlanoCurricularShouldNotBeFound("siglaMedia1.equals=" + UPDATED_SIGLA_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia1IsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia1 in DEFAULT_SIGLA_MEDIA_1 or UPDATED_SIGLA_MEDIA_1
        defaultPlanoCurricularShouldBeFound("siglaMedia1.in=" + DEFAULT_SIGLA_MEDIA_1 + "," + UPDATED_SIGLA_MEDIA_1);

        // Get all the planoCurricularList where siglaMedia1 equals to UPDATED_SIGLA_MEDIA_1
        defaultPlanoCurricularShouldNotBeFound("siglaMedia1.in=" + UPDATED_SIGLA_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia1IsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia1 is not null
        defaultPlanoCurricularShouldBeFound("siglaMedia1.specified=true");

        // Get all the planoCurricularList where siglaMedia1 is null
        defaultPlanoCurricularShouldNotBeFound("siglaMedia1.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia1ContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia1 contains DEFAULT_SIGLA_MEDIA_1
        defaultPlanoCurricularShouldBeFound("siglaMedia1.contains=" + DEFAULT_SIGLA_MEDIA_1);

        // Get all the planoCurricularList where siglaMedia1 contains UPDATED_SIGLA_MEDIA_1
        defaultPlanoCurricularShouldNotBeFound("siglaMedia1.contains=" + UPDATED_SIGLA_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia1NotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia1 does not contain DEFAULT_SIGLA_MEDIA_1
        defaultPlanoCurricularShouldNotBeFound("siglaMedia1.doesNotContain=" + DEFAULT_SIGLA_MEDIA_1);

        // Get all the planoCurricularList where siglaMedia1 does not contain UPDATED_SIGLA_MEDIA_1
        defaultPlanoCurricularShouldBeFound("siglaMedia1.doesNotContain=" + UPDATED_SIGLA_MEDIA_1);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia2IsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia2 equals to DEFAULT_SIGLA_MEDIA_2
        defaultPlanoCurricularShouldBeFound("siglaMedia2.equals=" + DEFAULT_SIGLA_MEDIA_2);

        // Get all the planoCurricularList where siglaMedia2 equals to UPDATED_SIGLA_MEDIA_2
        defaultPlanoCurricularShouldNotBeFound("siglaMedia2.equals=" + UPDATED_SIGLA_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia2IsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia2 in DEFAULT_SIGLA_MEDIA_2 or UPDATED_SIGLA_MEDIA_2
        defaultPlanoCurricularShouldBeFound("siglaMedia2.in=" + DEFAULT_SIGLA_MEDIA_2 + "," + UPDATED_SIGLA_MEDIA_2);

        // Get all the planoCurricularList where siglaMedia2 equals to UPDATED_SIGLA_MEDIA_2
        defaultPlanoCurricularShouldNotBeFound("siglaMedia2.in=" + UPDATED_SIGLA_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia2IsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia2 is not null
        defaultPlanoCurricularShouldBeFound("siglaMedia2.specified=true");

        // Get all the planoCurricularList where siglaMedia2 is null
        defaultPlanoCurricularShouldNotBeFound("siglaMedia2.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia2ContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia2 contains DEFAULT_SIGLA_MEDIA_2
        defaultPlanoCurricularShouldBeFound("siglaMedia2.contains=" + DEFAULT_SIGLA_MEDIA_2);

        // Get all the planoCurricularList where siglaMedia2 contains UPDATED_SIGLA_MEDIA_2
        defaultPlanoCurricularShouldNotBeFound("siglaMedia2.contains=" + UPDATED_SIGLA_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia2NotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia2 does not contain DEFAULT_SIGLA_MEDIA_2
        defaultPlanoCurricularShouldNotBeFound("siglaMedia2.doesNotContain=" + DEFAULT_SIGLA_MEDIA_2);

        // Get all the planoCurricularList where siglaMedia2 does not contain UPDATED_SIGLA_MEDIA_2
        defaultPlanoCurricularShouldBeFound("siglaMedia2.doesNotContain=" + UPDATED_SIGLA_MEDIA_2);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia3IsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia3 equals to DEFAULT_SIGLA_MEDIA_3
        defaultPlanoCurricularShouldBeFound("siglaMedia3.equals=" + DEFAULT_SIGLA_MEDIA_3);

        // Get all the planoCurricularList where siglaMedia3 equals to UPDATED_SIGLA_MEDIA_3
        defaultPlanoCurricularShouldNotBeFound("siglaMedia3.equals=" + UPDATED_SIGLA_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia3IsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia3 in DEFAULT_SIGLA_MEDIA_3 or UPDATED_SIGLA_MEDIA_3
        defaultPlanoCurricularShouldBeFound("siglaMedia3.in=" + DEFAULT_SIGLA_MEDIA_3 + "," + UPDATED_SIGLA_MEDIA_3);

        // Get all the planoCurricularList where siglaMedia3 equals to UPDATED_SIGLA_MEDIA_3
        defaultPlanoCurricularShouldNotBeFound("siglaMedia3.in=" + UPDATED_SIGLA_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia3IsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia3 is not null
        defaultPlanoCurricularShouldBeFound("siglaMedia3.specified=true");

        // Get all the planoCurricularList where siglaMedia3 is null
        defaultPlanoCurricularShouldNotBeFound("siglaMedia3.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia3ContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia3 contains DEFAULT_SIGLA_MEDIA_3
        defaultPlanoCurricularShouldBeFound("siglaMedia3.contains=" + DEFAULT_SIGLA_MEDIA_3);

        // Get all the planoCurricularList where siglaMedia3 contains UPDATED_SIGLA_MEDIA_3
        defaultPlanoCurricularShouldNotBeFound("siglaMedia3.contains=" + UPDATED_SIGLA_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsBySiglaMedia3NotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where siglaMedia3 does not contain DEFAULT_SIGLA_MEDIA_3
        defaultPlanoCurricularShouldNotBeFound("siglaMedia3.doesNotContain=" + DEFAULT_SIGLA_MEDIA_3);

        // Get all the planoCurricularList where siglaMedia3 does not contain UPDATED_SIGLA_MEDIA_3
        defaultPlanoCurricularShouldBeFound("siglaMedia3.doesNotContain=" + UPDATED_SIGLA_MEDIA_3);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaMediaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaMedia equals to DEFAULT_FORMULA_MEDIA
        defaultPlanoCurricularShouldBeFound("formulaMedia.equals=" + DEFAULT_FORMULA_MEDIA);

        // Get all the planoCurricularList where formulaMedia equals to UPDATED_FORMULA_MEDIA
        defaultPlanoCurricularShouldNotBeFound("formulaMedia.equals=" + UPDATED_FORMULA_MEDIA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaMediaIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaMedia in DEFAULT_FORMULA_MEDIA or UPDATED_FORMULA_MEDIA
        defaultPlanoCurricularShouldBeFound("formulaMedia.in=" + DEFAULT_FORMULA_MEDIA + "," + UPDATED_FORMULA_MEDIA);

        // Get all the planoCurricularList where formulaMedia equals to UPDATED_FORMULA_MEDIA
        defaultPlanoCurricularShouldNotBeFound("formulaMedia.in=" + UPDATED_FORMULA_MEDIA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaMediaIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaMedia is not null
        defaultPlanoCurricularShouldBeFound("formulaMedia.specified=true");

        // Get all the planoCurricularList where formulaMedia is null
        defaultPlanoCurricularShouldNotBeFound("formulaMedia.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaMediaContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaMedia contains DEFAULT_FORMULA_MEDIA
        defaultPlanoCurricularShouldBeFound("formulaMedia.contains=" + DEFAULT_FORMULA_MEDIA);

        // Get all the planoCurricularList where formulaMedia contains UPDATED_FORMULA_MEDIA
        defaultPlanoCurricularShouldNotBeFound("formulaMedia.contains=" + UPDATED_FORMULA_MEDIA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaMediaNotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaMedia does not contain DEFAULT_FORMULA_MEDIA
        defaultPlanoCurricularShouldNotBeFound("formulaMedia.doesNotContain=" + DEFAULT_FORMULA_MEDIA);

        // Get all the planoCurricularList where formulaMedia does not contain UPDATED_FORMULA_MEDIA
        defaultPlanoCurricularShouldBeFound("formulaMedia.doesNotContain=" + UPDATED_FORMULA_MEDIA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaDispensaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaDispensa equals to DEFAULT_FORMULA_DISPENSA
        defaultPlanoCurricularShouldBeFound("formulaDispensa.equals=" + DEFAULT_FORMULA_DISPENSA);

        // Get all the planoCurricularList where formulaDispensa equals to UPDATED_FORMULA_DISPENSA
        defaultPlanoCurricularShouldNotBeFound("formulaDispensa.equals=" + UPDATED_FORMULA_DISPENSA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaDispensaIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaDispensa in DEFAULT_FORMULA_DISPENSA or UPDATED_FORMULA_DISPENSA
        defaultPlanoCurricularShouldBeFound("formulaDispensa.in=" + DEFAULT_FORMULA_DISPENSA + "," + UPDATED_FORMULA_DISPENSA);

        // Get all the planoCurricularList where formulaDispensa equals to UPDATED_FORMULA_DISPENSA
        defaultPlanoCurricularShouldNotBeFound("formulaDispensa.in=" + UPDATED_FORMULA_DISPENSA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaDispensaIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaDispensa is not null
        defaultPlanoCurricularShouldBeFound("formulaDispensa.specified=true");

        // Get all the planoCurricularList where formulaDispensa is null
        defaultPlanoCurricularShouldNotBeFound("formulaDispensa.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaDispensaContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaDispensa contains DEFAULT_FORMULA_DISPENSA
        defaultPlanoCurricularShouldBeFound("formulaDispensa.contains=" + DEFAULT_FORMULA_DISPENSA);

        // Get all the planoCurricularList where formulaDispensa contains UPDATED_FORMULA_DISPENSA
        defaultPlanoCurricularShouldNotBeFound("formulaDispensa.contains=" + UPDATED_FORMULA_DISPENSA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaDispensaNotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaDispensa does not contain DEFAULT_FORMULA_DISPENSA
        defaultPlanoCurricularShouldNotBeFound("formulaDispensa.doesNotContain=" + DEFAULT_FORMULA_DISPENSA);

        // Get all the planoCurricularList where formulaDispensa does not contain UPDATED_FORMULA_DISPENSA
        defaultPlanoCurricularShouldBeFound("formulaDispensa.doesNotContain=" + UPDATED_FORMULA_DISPENSA);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaExameIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaExame equals to DEFAULT_FORMULA_EXAME
        defaultPlanoCurricularShouldBeFound("formulaExame.equals=" + DEFAULT_FORMULA_EXAME);

        // Get all the planoCurricularList where formulaExame equals to UPDATED_FORMULA_EXAME
        defaultPlanoCurricularShouldNotBeFound("formulaExame.equals=" + UPDATED_FORMULA_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaExameIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaExame in DEFAULT_FORMULA_EXAME or UPDATED_FORMULA_EXAME
        defaultPlanoCurricularShouldBeFound("formulaExame.in=" + DEFAULT_FORMULA_EXAME + "," + UPDATED_FORMULA_EXAME);

        // Get all the planoCurricularList where formulaExame equals to UPDATED_FORMULA_EXAME
        defaultPlanoCurricularShouldNotBeFound("formulaExame.in=" + UPDATED_FORMULA_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaExameIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaExame is not null
        defaultPlanoCurricularShouldBeFound("formulaExame.specified=true");

        // Get all the planoCurricularList where formulaExame is null
        defaultPlanoCurricularShouldNotBeFound("formulaExame.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaExameContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaExame contains DEFAULT_FORMULA_EXAME
        defaultPlanoCurricularShouldBeFound("formulaExame.contains=" + DEFAULT_FORMULA_EXAME);

        // Get all the planoCurricularList where formulaExame contains UPDATED_FORMULA_EXAME
        defaultPlanoCurricularShouldNotBeFound("formulaExame.contains=" + UPDATED_FORMULA_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaExameNotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaExame does not contain DEFAULT_FORMULA_EXAME
        defaultPlanoCurricularShouldNotBeFound("formulaExame.doesNotContain=" + DEFAULT_FORMULA_EXAME);

        // Get all the planoCurricularList where formulaExame does not contain UPDATED_FORMULA_EXAME
        defaultPlanoCurricularShouldBeFound("formulaExame.doesNotContain=" + UPDATED_FORMULA_EXAME);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaRecurso equals to DEFAULT_FORMULA_RECURSO
        defaultPlanoCurricularShouldBeFound("formulaRecurso.equals=" + DEFAULT_FORMULA_RECURSO);

        // Get all the planoCurricularList where formulaRecurso equals to UPDATED_FORMULA_RECURSO
        defaultPlanoCurricularShouldNotBeFound("formulaRecurso.equals=" + UPDATED_FORMULA_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaRecursoIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaRecurso in DEFAULT_FORMULA_RECURSO or UPDATED_FORMULA_RECURSO
        defaultPlanoCurricularShouldBeFound("formulaRecurso.in=" + DEFAULT_FORMULA_RECURSO + "," + UPDATED_FORMULA_RECURSO);

        // Get all the planoCurricularList where formulaRecurso equals to UPDATED_FORMULA_RECURSO
        defaultPlanoCurricularShouldNotBeFound("formulaRecurso.in=" + UPDATED_FORMULA_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaRecursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaRecurso is not null
        defaultPlanoCurricularShouldBeFound("formulaRecurso.specified=true");

        // Get all the planoCurricularList where formulaRecurso is null
        defaultPlanoCurricularShouldNotBeFound("formulaRecurso.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaRecursoContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaRecurso contains DEFAULT_FORMULA_RECURSO
        defaultPlanoCurricularShouldBeFound("formulaRecurso.contains=" + DEFAULT_FORMULA_RECURSO);

        // Get all the planoCurricularList where formulaRecurso contains UPDATED_FORMULA_RECURSO
        defaultPlanoCurricularShouldNotBeFound("formulaRecurso.contains=" + UPDATED_FORMULA_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaRecursoNotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaRecurso does not contain DEFAULT_FORMULA_RECURSO
        defaultPlanoCurricularShouldNotBeFound("formulaRecurso.doesNotContain=" + DEFAULT_FORMULA_RECURSO);

        // Get all the planoCurricularList where formulaRecurso does not contain UPDATED_FORMULA_RECURSO
        defaultPlanoCurricularShouldBeFound("formulaRecurso.doesNotContain=" + UPDATED_FORMULA_RECURSO);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaExameEspecialIsEqualToSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaExameEspecial equals to DEFAULT_FORMULA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("formulaExameEspecial.equals=" + DEFAULT_FORMULA_EXAME_ESPECIAL);

        // Get all the planoCurricularList where formulaExameEspecial equals to UPDATED_FORMULA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("formulaExameEspecial.equals=" + UPDATED_FORMULA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaExameEspecialIsInShouldWork() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaExameEspecial in DEFAULT_FORMULA_EXAME_ESPECIAL or UPDATED_FORMULA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound(
            "formulaExameEspecial.in=" + DEFAULT_FORMULA_EXAME_ESPECIAL + "," + UPDATED_FORMULA_EXAME_ESPECIAL
        );

        // Get all the planoCurricularList where formulaExameEspecial equals to UPDATED_FORMULA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("formulaExameEspecial.in=" + UPDATED_FORMULA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaExameEspecialIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaExameEspecial is not null
        defaultPlanoCurricularShouldBeFound("formulaExameEspecial.specified=true");

        // Get all the planoCurricularList where formulaExameEspecial is null
        defaultPlanoCurricularShouldNotBeFound("formulaExameEspecial.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaExameEspecialContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaExameEspecial contains DEFAULT_FORMULA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("formulaExameEspecial.contains=" + DEFAULT_FORMULA_EXAME_ESPECIAL);

        // Get all the planoCurricularList where formulaExameEspecial contains UPDATED_FORMULA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("formulaExameEspecial.contains=" + UPDATED_FORMULA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByFormulaExameEspecialNotContainsSomething() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList where formulaExameEspecial does not contain DEFAULT_FORMULA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldNotBeFound("formulaExameEspecial.doesNotContain=" + DEFAULT_FORMULA_EXAME_ESPECIAL);

        // Get all the planoCurricularList where formulaExameEspecial does not contain UPDATED_FORMULA_EXAME_ESPECIAL
        defaultPlanoCurricularShouldBeFound("formulaExameEspecial.doesNotContain=" + UPDATED_FORMULA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByTurmaIsEqualToSomething() throws Exception {
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            planoCurricularRepository.saveAndFlush(planoCurricular);
            turma = TurmaResourceIT.createEntity(em);
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(turma);
        em.flush();
        planoCurricular.addTurma(turma);
        planoCurricularRepository.saveAndFlush(planoCurricular);
        Long turmaId = turma.getId();

        // Get all the planoCurricularList where turma equals to turmaId
        defaultPlanoCurricularShouldBeFound("turmaId.equals=" + turmaId);

        // Get all the planoCurricularList where turma equals to (turmaId + 1)
        defaultPlanoCurricularShouldNotBeFound("turmaId.equals=" + (turmaId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            planoCurricularRepository.saveAndFlush(planoCurricular);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        planoCurricular.setUtilizador(utilizador);
        planoCurricularRepository.saveAndFlush(planoCurricular);
        Long utilizadorId = utilizador.getId();

        // Get all the planoCurricularList where utilizador equals to utilizadorId
        defaultPlanoCurricularShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the planoCurricularList where utilizador equals to (utilizadorId + 1)
        defaultPlanoCurricularShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByClasseIsEqualToSomething() throws Exception {
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            planoCurricularRepository.saveAndFlush(planoCurricular);
            classe = ClasseResourceIT.createEntity(em);
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        em.persist(classe);
        em.flush();
        planoCurricular.setClasse(classe);
        planoCurricularRepository.saveAndFlush(planoCurricular);
        Long classeId = classe.getId();

        // Get all the planoCurricularList where classe equals to classeId
        defaultPlanoCurricularShouldBeFound("classeId.equals=" + classeId);

        // Get all the planoCurricularList where classe equals to (classeId + 1)
        defaultPlanoCurricularShouldNotBeFound("classeId.equals=" + (classeId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByCursoIsEqualToSomething() throws Exception {
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            planoCurricularRepository.saveAndFlush(planoCurricular);
            curso = CursoResourceIT.createEntity(em);
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        em.persist(curso);
        em.flush();
        planoCurricular.setCurso(curso);
        planoCurricularRepository.saveAndFlush(planoCurricular);
        Long cursoId = curso.getId();

        // Get all the planoCurricularList where curso equals to cursoId
        defaultPlanoCurricularShouldBeFound("cursoId.equals=" + cursoId);

        // Get all the planoCurricularList where curso equals to (cursoId + 1)
        defaultPlanoCurricularShouldNotBeFound("cursoId.equals=" + (cursoId + 1));
    }

    @Test
    @Transactional
    void getAllPlanoCurricularsByDisciplinasCurricularIsEqualToSomething() throws Exception {
        DisciplinaCurricular disciplinasCurricular;
        if (TestUtil.findAll(em, DisciplinaCurricular.class).isEmpty()) {
            planoCurricularRepository.saveAndFlush(planoCurricular);
            disciplinasCurricular = DisciplinaCurricularResourceIT.createEntity(em);
        } else {
            disciplinasCurricular = TestUtil.findAll(em, DisciplinaCurricular.class).get(0);
        }
        em.persist(disciplinasCurricular);
        em.flush();
        planoCurricular.addDisciplinasCurricular(disciplinasCurricular);
        planoCurricularRepository.saveAndFlush(planoCurricular);
        Long disciplinasCurricularId = disciplinasCurricular.getId();

        // Get all the planoCurricularList where disciplinasCurricular equals to disciplinasCurricularId
        defaultPlanoCurricularShouldBeFound("disciplinasCurricularId.equals=" + disciplinasCurricularId);

        // Get all the planoCurricularList where disciplinasCurricular equals to (disciplinasCurricularId + 1)
        defaultPlanoCurricularShouldNotBeFound("disciplinasCurricularId.equals=" + (disciplinasCurricularId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoCurricularShouldBeFound(String filter) throws Exception {
        restPlanoCurricularMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoCurricular.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].formulaClassificacaoFinal").value(hasItem(DEFAULT_FORMULA_CLASSIFICACAO_FINAL)))
            .andExpect(jsonPath("$.[*].numeroDisciplinaAprova").value(hasItem(DEFAULT_NUMERO_DISCIPLINA_APROVA)))
            .andExpect(jsonPath("$.[*].numeroDisciplinaReprova").value(hasItem(DEFAULT_NUMERO_DISCIPLINA_REPROVA)))
            .andExpect(jsonPath("$.[*].numeroDisciplinaRecurso").value(hasItem(DEFAULT_NUMERO_DISCIPLINA_RECURSO)))
            .andExpect(jsonPath("$.[*].numeroDisciplinaExame").value(hasItem(DEFAULT_NUMERO_DISCIPLINA_EXAME)))
            .andExpect(jsonPath("$.[*].numeroDisciplinaExameEspecial").value(hasItem(DEFAULT_NUMERO_DISCIPLINA_EXAME_ESPECIAL)))
            .andExpect(jsonPath("$.[*].numeroFaltaReprova").value(hasItem(DEFAULT_NUMERO_FALTA_REPROVA)))
            .andExpect(jsonPath("$.[*].pesoMedia1").value(hasItem(DEFAULT_PESO_MEDIA_1.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoMedia2").value(hasItem(DEFAULT_PESO_MEDIA_2.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoMedia3").value(hasItem(DEFAULT_PESO_MEDIA_3.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoRecurso").value(hasItem(DEFAULT_PESO_RECURSO.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoExame").value(hasItem(DEFAULT_PESO_EXAME.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoExameEspecial").value(hasItem(DEFAULT_PESO_EXAME_ESPECIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoNotaCoselho").value(hasItem(DEFAULT_PESO_NOTA_COSELHO.doubleValue())))
            .andExpect(jsonPath("$.[*].siglaProva1").value(hasItem(DEFAULT_SIGLA_PROVA_1)))
            .andExpect(jsonPath("$.[*].siglaProva2").value(hasItem(DEFAULT_SIGLA_PROVA_2)))
            .andExpect(jsonPath("$.[*].siglaProva3").value(hasItem(DEFAULT_SIGLA_PROVA_3)))
            .andExpect(jsonPath("$.[*].siglaMedia1").value(hasItem(DEFAULT_SIGLA_MEDIA_1)))
            .andExpect(jsonPath("$.[*].siglaMedia2").value(hasItem(DEFAULT_SIGLA_MEDIA_2)))
            .andExpect(jsonPath("$.[*].siglaMedia3").value(hasItem(DEFAULT_SIGLA_MEDIA_3)))
            .andExpect(jsonPath("$.[*].formulaMedia").value(hasItem(DEFAULT_FORMULA_MEDIA)))
            .andExpect(jsonPath("$.[*].formulaDispensa").value(hasItem(DEFAULT_FORMULA_DISPENSA)))
            .andExpect(jsonPath("$.[*].formulaExame").value(hasItem(DEFAULT_FORMULA_EXAME)))
            .andExpect(jsonPath("$.[*].formulaRecurso").value(hasItem(DEFAULT_FORMULA_RECURSO)))
            .andExpect(jsonPath("$.[*].formulaExameEspecial").value(hasItem(DEFAULT_FORMULA_EXAME_ESPECIAL)));

        // Check, that the count call also returns 1
        restPlanoCurricularMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoCurricularShouldNotBeFound(String filter) throws Exception {
        restPlanoCurricularMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoCurricularMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPlanoCurricular() throws Exception {
        // Get the planoCurricular
        restPlanoCurricularMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanoCurricular() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        int databaseSizeBeforeUpdate = planoCurricularRepository.findAll().size();

        // Update the planoCurricular
        PlanoCurricular updatedPlanoCurricular = planoCurricularRepository.findById(planoCurricular.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoCurricular are not directly saved in db
        em.detach(updatedPlanoCurricular);
        updatedPlanoCurricular
            .descricao(UPDATED_DESCRICAO)
            .formulaClassificacaoFinal(UPDATED_FORMULA_CLASSIFICACAO_FINAL)
            .numeroDisciplinaAprova(UPDATED_NUMERO_DISCIPLINA_APROVA)
            .numeroDisciplinaReprova(UPDATED_NUMERO_DISCIPLINA_REPROVA)
            .numeroDisciplinaRecurso(UPDATED_NUMERO_DISCIPLINA_RECURSO)
            .numeroDisciplinaExame(UPDATED_NUMERO_DISCIPLINA_EXAME)
            .numeroDisciplinaExameEspecial(UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL)
            .numeroFaltaReprova(UPDATED_NUMERO_FALTA_REPROVA)
            .pesoMedia1(UPDATED_PESO_MEDIA_1)
            .pesoMedia2(UPDATED_PESO_MEDIA_2)
            .pesoMedia3(UPDATED_PESO_MEDIA_3)
            .pesoRecurso(UPDATED_PESO_RECURSO)
            .pesoExame(UPDATED_PESO_EXAME)
            .pesoExameEspecial(UPDATED_PESO_EXAME_ESPECIAL)
            .pesoNotaCoselho(UPDATED_PESO_NOTA_COSELHO)
            .siglaProva1(UPDATED_SIGLA_PROVA_1)
            .siglaProva2(UPDATED_SIGLA_PROVA_2)
            .siglaProva3(UPDATED_SIGLA_PROVA_3)
            .siglaMedia1(UPDATED_SIGLA_MEDIA_1)
            .siglaMedia2(UPDATED_SIGLA_MEDIA_2)
            .siglaMedia3(UPDATED_SIGLA_MEDIA_3)
            .formulaMedia(UPDATED_FORMULA_MEDIA)
            .formulaDispensa(UPDATED_FORMULA_DISPENSA)
            .formulaExame(UPDATED_FORMULA_EXAME)
            .formulaRecurso(UPDATED_FORMULA_RECURSO)
            .formulaExameEspecial(UPDATED_FORMULA_EXAME_ESPECIAL);
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(updatedPlanoCurricular);

        restPlanoCurricularMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoCurricularDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeUpdate);
        PlanoCurricular testPlanoCurricular = planoCurricularList.get(planoCurricularList.size() - 1);
        assertThat(testPlanoCurricular.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoCurricular.getFormulaClassificacaoFinal()).isEqualTo(UPDATED_FORMULA_CLASSIFICACAO_FINAL);
        assertThat(testPlanoCurricular.getNumeroDisciplinaAprova()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_APROVA);
        assertThat(testPlanoCurricular.getNumeroDisciplinaReprova()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_REPROVA);
        assertThat(testPlanoCurricular.getNumeroDisciplinaRecurso()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_RECURSO);
        assertThat(testPlanoCurricular.getNumeroDisciplinaExame()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_EXAME);
        assertThat(testPlanoCurricular.getNumeroDisciplinaExameEspecial()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL);
        assertThat(testPlanoCurricular.getNumeroFaltaReprova()).isEqualTo(UPDATED_NUMERO_FALTA_REPROVA);
        assertThat(testPlanoCurricular.getPesoMedia1()).isEqualTo(UPDATED_PESO_MEDIA_1);
        assertThat(testPlanoCurricular.getPesoMedia2()).isEqualTo(UPDATED_PESO_MEDIA_2);
        assertThat(testPlanoCurricular.getPesoMedia3()).isEqualTo(UPDATED_PESO_MEDIA_3);
        assertThat(testPlanoCurricular.getPesoRecurso()).isEqualTo(UPDATED_PESO_RECURSO);
        assertThat(testPlanoCurricular.getPesoExame()).isEqualTo(UPDATED_PESO_EXAME);
        assertThat(testPlanoCurricular.getPesoExameEspecial()).isEqualTo(UPDATED_PESO_EXAME_ESPECIAL);
        assertThat(testPlanoCurricular.getPesoNotaCoselho()).isEqualTo(UPDATED_PESO_NOTA_COSELHO);
        assertThat(testPlanoCurricular.getSiglaProva1()).isEqualTo(UPDATED_SIGLA_PROVA_1);
        assertThat(testPlanoCurricular.getSiglaProva2()).isEqualTo(UPDATED_SIGLA_PROVA_2);
        assertThat(testPlanoCurricular.getSiglaProva3()).isEqualTo(UPDATED_SIGLA_PROVA_3);
        assertThat(testPlanoCurricular.getSiglaMedia1()).isEqualTo(UPDATED_SIGLA_MEDIA_1);
        assertThat(testPlanoCurricular.getSiglaMedia2()).isEqualTo(UPDATED_SIGLA_MEDIA_2);
        assertThat(testPlanoCurricular.getSiglaMedia3()).isEqualTo(UPDATED_SIGLA_MEDIA_3);
        assertThat(testPlanoCurricular.getFormulaMedia()).isEqualTo(UPDATED_FORMULA_MEDIA);
        assertThat(testPlanoCurricular.getFormulaDispensa()).isEqualTo(UPDATED_FORMULA_DISPENSA);
        assertThat(testPlanoCurricular.getFormulaExame()).isEqualTo(UPDATED_FORMULA_EXAME);
        assertThat(testPlanoCurricular.getFormulaRecurso()).isEqualTo(UPDATED_FORMULA_RECURSO);
        assertThat(testPlanoCurricular.getFormulaExameEspecial()).isEqualTo(UPDATED_FORMULA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void putNonExistingPlanoCurricular() throws Exception {
        int databaseSizeBeforeUpdate = planoCurricularRepository.findAll().size();
        planoCurricular.setId(count.incrementAndGet());

        // Create the PlanoCurricular
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoCurricularMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoCurricularDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanoCurricular() throws Exception {
        int databaseSizeBeforeUpdate = planoCurricularRepository.findAll().size();
        planoCurricular.setId(count.incrementAndGet());

        // Create the PlanoCurricular
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoCurricularMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanoCurricular() throws Exception {
        int databaseSizeBeforeUpdate = planoCurricularRepository.findAll().size();
        planoCurricular.setId(count.incrementAndGet());

        // Create the PlanoCurricular
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoCurricularMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanoCurricularWithPatch() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        int databaseSizeBeforeUpdate = planoCurricularRepository.findAll().size();

        // Update the planoCurricular using partial update
        PlanoCurricular partialUpdatedPlanoCurricular = new PlanoCurricular();
        partialUpdatedPlanoCurricular.setId(planoCurricular.getId());

        partialUpdatedPlanoCurricular
            .descricao(UPDATED_DESCRICAO)
            .formulaClassificacaoFinal(UPDATED_FORMULA_CLASSIFICACAO_FINAL)
            .numeroDisciplinaAprova(UPDATED_NUMERO_DISCIPLINA_APROVA)
            .numeroDisciplinaExameEspecial(UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL)
            .numeroFaltaReprova(UPDATED_NUMERO_FALTA_REPROVA)
            .pesoMedia3(UPDATED_PESO_MEDIA_3)
            .pesoRecurso(UPDATED_PESO_RECURSO)
            .pesoExame(UPDATED_PESO_EXAME)
            .pesoExameEspecial(UPDATED_PESO_EXAME_ESPECIAL)
            .pesoNotaCoselho(UPDATED_PESO_NOTA_COSELHO)
            .siglaProva2(UPDATED_SIGLA_PROVA_2)
            .siglaMedia1(UPDATED_SIGLA_MEDIA_1)
            .siglaMedia3(UPDATED_SIGLA_MEDIA_3)
            .formulaDispensa(UPDATED_FORMULA_DISPENSA)
            .formulaExameEspecial(UPDATED_FORMULA_EXAME_ESPECIAL);

        restPlanoCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoCurricular.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanoCurricular))
            )
            .andExpect(status().isOk());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeUpdate);
        PlanoCurricular testPlanoCurricular = planoCurricularList.get(planoCurricularList.size() - 1);
        assertThat(testPlanoCurricular.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoCurricular.getFormulaClassificacaoFinal()).isEqualTo(UPDATED_FORMULA_CLASSIFICACAO_FINAL);
        assertThat(testPlanoCurricular.getNumeroDisciplinaAprova()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_APROVA);
        assertThat(testPlanoCurricular.getNumeroDisciplinaReprova()).isEqualTo(DEFAULT_NUMERO_DISCIPLINA_REPROVA);
        assertThat(testPlanoCurricular.getNumeroDisciplinaRecurso()).isEqualTo(DEFAULT_NUMERO_DISCIPLINA_RECURSO);
        assertThat(testPlanoCurricular.getNumeroDisciplinaExame()).isEqualTo(DEFAULT_NUMERO_DISCIPLINA_EXAME);
        assertThat(testPlanoCurricular.getNumeroDisciplinaExameEspecial()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL);
        assertThat(testPlanoCurricular.getNumeroFaltaReprova()).isEqualTo(UPDATED_NUMERO_FALTA_REPROVA);
        assertThat(testPlanoCurricular.getPesoMedia1()).isEqualTo(DEFAULT_PESO_MEDIA_1);
        assertThat(testPlanoCurricular.getPesoMedia2()).isEqualTo(DEFAULT_PESO_MEDIA_2);
        assertThat(testPlanoCurricular.getPesoMedia3()).isEqualTo(UPDATED_PESO_MEDIA_3);
        assertThat(testPlanoCurricular.getPesoRecurso()).isEqualTo(UPDATED_PESO_RECURSO);
        assertThat(testPlanoCurricular.getPesoExame()).isEqualTo(UPDATED_PESO_EXAME);
        assertThat(testPlanoCurricular.getPesoExameEspecial()).isEqualTo(UPDATED_PESO_EXAME_ESPECIAL);
        assertThat(testPlanoCurricular.getPesoNotaCoselho()).isEqualTo(UPDATED_PESO_NOTA_COSELHO);
        assertThat(testPlanoCurricular.getSiglaProva1()).isEqualTo(DEFAULT_SIGLA_PROVA_1);
        assertThat(testPlanoCurricular.getSiglaProva2()).isEqualTo(UPDATED_SIGLA_PROVA_2);
        assertThat(testPlanoCurricular.getSiglaProva3()).isEqualTo(DEFAULT_SIGLA_PROVA_3);
        assertThat(testPlanoCurricular.getSiglaMedia1()).isEqualTo(UPDATED_SIGLA_MEDIA_1);
        assertThat(testPlanoCurricular.getSiglaMedia2()).isEqualTo(DEFAULT_SIGLA_MEDIA_2);
        assertThat(testPlanoCurricular.getSiglaMedia3()).isEqualTo(UPDATED_SIGLA_MEDIA_3);
        assertThat(testPlanoCurricular.getFormulaMedia()).isEqualTo(DEFAULT_FORMULA_MEDIA);
        assertThat(testPlanoCurricular.getFormulaDispensa()).isEqualTo(UPDATED_FORMULA_DISPENSA);
        assertThat(testPlanoCurricular.getFormulaExame()).isEqualTo(DEFAULT_FORMULA_EXAME);
        assertThat(testPlanoCurricular.getFormulaRecurso()).isEqualTo(DEFAULT_FORMULA_RECURSO);
        assertThat(testPlanoCurricular.getFormulaExameEspecial()).isEqualTo(UPDATED_FORMULA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void fullUpdatePlanoCurricularWithPatch() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        int databaseSizeBeforeUpdate = planoCurricularRepository.findAll().size();

        // Update the planoCurricular using partial update
        PlanoCurricular partialUpdatedPlanoCurricular = new PlanoCurricular();
        partialUpdatedPlanoCurricular.setId(planoCurricular.getId());

        partialUpdatedPlanoCurricular
            .descricao(UPDATED_DESCRICAO)
            .formulaClassificacaoFinal(UPDATED_FORMULA_CLASSIFICACAO_FINAL)
            .numeroDisciplinaAprova(UPDATED_NUMERO_DISCIPLINA_APROVA)
            .numeroDisciplinaReprova(UPDATED_NUMERO_DISCIPLINA_REPROVA)
            .numeroDisciplinaRecurso(UPDATED_NUMERO_DISCIPLINA_RECURSO)
            .numeroDisciplinaExame(UPDATED_NUMERO_DISCIPLINA_EXAME)
            .numeroDisciplinaExameEspecial(UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL)
            .numeroFaltaReprova(UPDATED_NUMERO_FALTA_REPROVA)
            .pesoMedia1(UPDATED_PESO_MEDIA_1)
            .pesoMedia2(UPDATED_PESO_MEDIA_2)
            .pesoMedia3(UPDATED_PESO_MEDIA_3)
            .pesoRecurso(UPDATED_PESO_RECURSO)
            .pesoExame(UPDATED_PESO_EXAME)
            .pesoExameEspecial(UPDATED_PESO_EXAME_ESPECIAL)
            .pesoNotaCoselho(UPDATED_PESO_NOTA_COSELHO)
            .siglaProva1(UPDATED_SIGLA_PROVA_1)
            .siglaProva2(UPDATED_SIGLA_PROVA_2)
            .siglaProva3(UPDATED_SIGLA_PROVA_3)
            .siglaMedia1(UPDATED_SIGLA_MEDIA_1)
            .siglaMedia2(UPDATED_SIGLA_MEDIA_2)
            .siglaMedia3(UPDATED_SIGLA_MEDIA_3)
            .formulaMedia(UPDATED_FORMULA_MEDIA)
            .formulaDispensa(UPDATED_FORMULA_DISPENSA)
            .formulaExame(UPDATED_FORMULA_EXAME)
            .formulaRecurso(UPDATED_FORMULA_RECURSO)
            .formulaExameEspecial(UPDATED_FORMULA_EXAME_ESPECIAL);

        restPlanoCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoCurricular.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanoCurricular))
            )
            .andExpect(status().isOk());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeUpdate);
        PlanoCurricular testPlanoCurricular = planoCurricularList.get(planoCurricularList.size() - 1);
        assertThat(testPlanoCurricular.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoCurricular.getFormulaClassificacaoFinal()).isEqualTo(UPDATED_FORMULA_CLASSIFICACAO_FINAL);
        assertThat(testPlanoCurricular.getNumeroDisciplinaAprova()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_APROVA);
        assertThat(testPlanoCurricular.getNumeroDisciplinaReprova()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_REPROVA);
        assertThat(testPlanoCurricular.getNumeroDisciplinaRecurso()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_RECURSO);
        assertThat(testPlanoCurricular.getNumeroDisciplinaExame()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_EXAME);
        assertThat(testPlanoCurricular.getNumeroDisciplinaExameEspecial()).isEqualTo(UPDATED_NUMERO_DISCIPLINA_EXAME_ESPECIAL);
        assertThat(testPlanoCurricular.getNumeroFaltaReprova()).isEqualTo(UPDATED_NUMERO_FALTA_REPROVA);
        assertThat(testPlanoCurricular.getPesoMedia1()).isEqualTo(UPDATED_PESO_MEDIA_1);
        assertThat(testPlanoCurricular.getPesoMedia2()).isEqualTo(UPDATED_PESO_MEDIA_2);
        assertThat(testPlanoCurricular.getPesoMedia3()).isEqualTo(UPDATED_PESO_MEDIA_3);
        assertThat(testPlanoCurricular.getPesoRecurso()).isEqualTo(UPDATED_PESO_RECURSO);
        assertThat(testPlanoCurricular.getPesoExame()).isEqualTo(UPDATED_PESO_EXAME);
        assertThat(testPlanoCurricular.getPesoExameEspecial()).isEqualTo(UPDATED_PESO_EXAME_ESPECIAL);
        assertThat(testPlanoCurricular.getPesoNotaCoselho()).isEqualTo(UPDATED_PESO_NOTA_COSELHO);
        assertThat(testPlanoCurricular.getSiglaProva1()).isEqualTo(UPDATED_SIGLA_PROVA_1);
        assertThat(testPlanoCurricular.getSiglaProva2()).isEqualTo(UPDATED_SIGLA_PROVA_2);
        assertThat(testPlanoCurricular.getSiglaProva3()).isEqualTo(UPDATED_SIGLA_PROVA_3);
        assertThat(testPlanoCurricular.getSiglaMedia1()).isEqualTo(UPDATED_SIGLA_MEDIA_1);
        assertThat(testPlanoCurricular.getSiglaMedia2()).isEqualTo(UPDATED_SIGLA_MEDIA_2);
        assertThat(testPlanoCurricular.getSiglaMedia3()).isEqualTo(UPDATED_SIGLA_MEDIA_3);
        assertThat(testPlanoCurricular.getFormulaMedia()).isEqualTo(UPDATED_FORMULA_MEDIA);
        assertThat(testPlanoCurricular.getFormulaDispensa()).isEqualTo(UPDATED_FORMULA_DISPENSA);
        assertThat(testPlanoCurricular.getFormulaExame()).isEqualTo(UPDATED_FORMULA_EXAME);
        assertThat(testPlanoCurricular.getFormulaRecurso()).isEqualTo(UPDATED_FORMULA_RECURSO);
        assertThat(testPlanoCurricular.getFormulaExameEspecial()).isEqualTo(UPDATED_FORMULA_EXAME_ESPECIAL);
    }

    @Test
    @Transactional
    void patchNonExistingPlanoCurricular() throws Exception {
        int databaseSizeBeforeUpdate = planoCurricularRepository.findAll().size();
        planoCurricular.setId(count.incrementAndGet());

        // Create the PlanoCurricular
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planoCurricularDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanoCurricular() throws Exception {
        int databaseSizeBeforeUpdate = planoCurricularRepository.findAll().size();
        planoCurricular.setId(count.incrementAndGet());

        // Create the PlanoCurricular
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanoCurricular() throws Exception {
        int databaseSizeBeforeUpdate = planoCurricularRepository.findAll().size();
        planoCurricular.setId(count.incrementAndGet());

        // Create the PlanoCurricular
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoCurricularMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanoCurricular() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        int databaseSizeBeforeDelete = planoCurricularRepository.findAll().size();

        // Delete the planoCurricular
        restPlanoCurricularMockMvc
            .perform(delete(ENTITY_API_URL_ID, planoCurricular.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
