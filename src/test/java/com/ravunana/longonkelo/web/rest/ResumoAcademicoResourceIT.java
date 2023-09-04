package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.domain.ResumoAcademico;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.ResumoAcademicoRepository;
import com.ravunana.longonkelo.service.ResumoAcademicoService;
import com.ravunana.longonkelo.service.criteria.ResumoAcademicoCriteria;
import com.ravunana.longonkelo.service.dto.ResumoAcademicoDTO;
import com.ravunana.longonkelo.service.mapper.ResumoAcademicoMapper;
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
 * Integration tests for the {@link ResumoAcademicoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ResumoAcademicoResourceIT {

    private static final String DEFAULT_TEMA_PROJECTO = "AAAAAAAAAA";
    private static final String UPDATED_TEMA_PROJECTO = "BBBBBBBBBB";

    private static final Double DEFAULT_NOTA_PROJECTO = 0D;
    private static final Double UPDATED_NOTA_PROJECTO = 1D;
    private static final Double SMALLER_NOTA_PROJECTO = 0D - 1D;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_ESTAGIO = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_ESTAGIO = "BBBBBBBBBB";

    private static final Double DEFAULT_NOTA_ESTAGIO = 0D;
    private static final Double UPDATED_NOTA_ESTAGIO = 1D;
    private static final Double SMALLER_NOTA_ESTAGIO = 0D - 1D;

    private static final Double DEFAULT_MEDIA_FINAL_DISCIPLINA = 0D;
    private static final Double UPDATED_MEDIA_FINAL_DISCIPLINA = 1D;
    private static final Double SMALLER_MEDIA_FINAL_DISCIPLINA = 0D - 1D;

    private static final Double DEFAULT_CLASSIFICACAO_FINAL = 0D;
    private static final Double UPDATED_CLASSIFICACAO_FINAL = 1D;
    private static final Double SMALLER_CLASSIFICACAO_FINAL = 0D - 1D;

    private static final String DEFAULT_NUMERO_GRUPO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_GRUPO = "BBBBBBBBBB";

    private static final String DEFAULT_MESA_DEFESA = "AAAAAAAAAA";
    private static final String UPDATED_MESA_DEFESA = "BBBBBBBBBB";

    private static final String DEFAULT_LIVRO_REGISTRO = "AAAAAAAAAA";
    private static final String UPDATED_LIVRO_REGISTRO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_FOLHA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_FOLHA = "BBBBBBBBBB";

    private static final String DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA = "AAAAAAAAAA";
    private static final String UPDATED_CHEFE_SECRETARIA_PEDAGOGICA = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_DIRECTOR_PEDAGOGICO = "AAAAAAAAAA";
    private static final String UPDATED_SUB_DIRECTOR_PEDAGOGICO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECTOR_GERAL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTOR_GERAL = "BBBBBBBBBB";

    private static final String DEFAULT_TUTOR_PROJECTO = "AAAAAAAAAA";
    private static final String UPDATED_TUTOR_PROJECTO = "BBBBBBBBBB";

    private static final String DEFAULT_JURI_MESA = "AAAAAAAAAA";
    private static final String UPDATED_JURI_MESA = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ESTAGIO = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ESTAGIO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ASSINATURA_DIGITAL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ASSINATURA_DIGITAL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ASSINATURA_DIGITAL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ASSINATURA_DIGITAL_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/resumo-academicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResumoAcademicoRepository resumoAcademicoRepository;

    @Mock
    private ResumoAcademicoRepository resumoAcademicoRepositoryMock;

    @Autowired
    private ResumoAcademicoMapper resumoAcademicoMapper;

    @Mock
    private ResumoAcademicoService resumoAcademicoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResumoAcademicoMockMvc;

    private ResumoAcademico resumoAcademico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumoAcademico createEntity(EntityManager em) {
        ResumoAcademico resumoAcademico = new ResumoAcademico()
            .temaProjecto(DEFAULT_TEMA_PROJECTO)
            .notaProjecto(DEFAULT_NOTA_PROJECTO)
            .observacao(DEFAULT_OBSERVACAO)
            .localEstagio(DEFAULT_LOCAL_ESTAGIO)
            .notaEstagio(DEFAULT_NOTA_ESTAGIO)
            .mediaFinalDisciplina(DEFAULT_MEDIA_FINAL_DISCIPLINA)
            .classificacaoFinal(DEFAULT_CLASSIFICACAO_FINAL)
            .numeroGrupo(DEFAULT_NUMERO_GRUPO)
            .mesaDefesa(DEFAULT_MESA_DEFESA)
            .livroRegistro(DEFAULT_LIVRO_REGISTRO)
            .numeroFolha(DEFAULT_NUMERO_FOLHA)
            .chefeSecretariaPedagogica(DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA)
            .subDirectorPedagogico(DEFAULT_SUB_DIRECTOR_PEDAGOGICO)
            .directorGeral(DEFAULT_DIRECTOR_GERAL)
            .tutorProjecto(DEFAULT_TUTOR_PROJECTO)
            .juriMesa(DEFAULT_JURI_MESA)
            .empresaEstagio(DEFAULT_EMPRESA_ESTAGIO)
            .assinaturaDigital(DEFAULT_ASSINATURA_DIGITAL)
            .assinaturaDigitalContentType(DEFAULT_ASSINATURA_DIGITAL_CONTENT_TYPE)
            .hash(DEFAULT_HASH);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        resumoAcademico.setUltimaTurmaMatriculada(turma);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        resumoAcademico.setDiscente(discente);
        // Add required entity
        EstadoDisciplinaCurricular estadoDisciplinaCurricular;
        if (TestUtil.findAll(em, EstadoDisciplinaCurricular.class).isEmpty()) {
            estadoDisciplinaCurricular = EstadoDisciplinaCurricularResourceIT.createEntity(em);
            em.persist(estadoDisciplinaCurricular);
            em.flush();
        } else {
            estadoDisciplinaCurricular = TestUtil.findAll(em, EstadoDisciplinaCurricular.class).get(0);
        }
        resumoAcademico.setSituacao(estadoDisciplinaCurricular);
        return resumoAcademico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumoAcademico createUpdatedEntity(EntityManager em) {
        ResumoAcademico resumoAcademico = new ResumoAcademico()
            .temaProjecto(UPDATED_TEMA_PROJECTO)
            .notaProjecto(UPDATED_NOTA_PROJECTO)
            .observacao(UPDATED_OBSERVACAO)
            .localEstagio(UPDATED_LOCAL_ESTAGIO)
            .notaEstagio(UPDATED_NOTA_ESTAGIO)
            .mediaFinalDisciplina(UPDATED_MEDIA_FINAL_DISCIPLINA)
            .classificacaoFinal(UPDATED_CLASSIFICACAO_FINAL)
            .numeroGrupo(UPDATED_NUMERO_GRUPO)
            .mesaDefesa(UPDATED_MESA_DEFESA)
            .livroRegistro(UPDATED_LIVRO_REGISTRO)
            .numeroFolha(UPDATED_NUMERO_FOLHA)
            .chefeSecretariaPedagogica(UPDATED_CHEFE_SECRETARIA_PEDAGOGICA)
            .subDirectorPedagogico(UPDATED_SUB_DIRECTOR_PEDAGOGICO)
            .directorGeral(UPDATED_DIRECTOR_GERAL)
            .tutorProjecto(UPDATED_TUTOR_PROJECTO)
            .juriMesa(UPDATED_JURI_MESA)
            .empresaEstagio(UPDATED_EMPRESA_ESTAGIO)
            .assinaturaDigital(UPDATED_ASSINATURA_DIGITAL)
            .assinaturaDigitalContentType(UPDATED_ASSINATURA_DIGITAL_CONTENT_TYPE)
            .hash(UPDATED_HASH);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createUpdatedEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        resumoAcademico.setUltimaTurmaMatriculada(turma);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createUpdatedEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        resumoAcademico.setDiscente(discente);
        // Add required entity
        EstadoDisciplinaCurricular estadoDisciplinaCurricular;
        if (TestUtil.findAll(em, EstadoDisciplinaCurricular.class).isEmpty()) {
            estadoDisciplinaCurricular = EstadoDisciplinaCurricularResourceIT.createUpdatedEntity(em);
            em.persist(estadoDisciplinaCurricular);
            em.flush();
        } else {
            estadoDisciplinaCurricular = TestUtil.findAll(em, EstadoDisciplinaCurricular.class).get(0);
        }
        resumoAcademico.setSituacao(estadoDisciplinaCurricular);
        return resumoAcademico;
    }

    @BeforeEach
    public void initTest() {
        resumoAcademico = createEntity(em);
    }

    @Test
    @Transactional
    void createResumoAcademico() throws Exception {
        int databaseSizeBeforeCreate = resumoAcademicoRepository.findAll().size();
        // Create the ResumoAcademico
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);
        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResumoAcademico in the database
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeCreate + 1);
        ResumoAcademico testResumoAcademico = resumoAcademicoList.get(resumoAcademicoList.size() - 1);
        assertThat(testResumoAcademico.getTemaProjecto()).isEqualTo(DEFAULT_TEMA_PROJECTO);
        assertThat(testResumoAcademico.getNotaProjecto()).isEqualTo(DEFAULT_NOTA_PROJECTO);
        assertThat(testResumoAcademico.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testResumoAcademico.getLocalEstagio()).isEqualTo(DEFAULT_LOCAL_ESTAGIO);
        assertThat(testResumoAcademico.getNotaEstagio()).isEqualTo(DEFAULT_NOTA_ESTAGIO);
        assertThat(testResumoAcademico.getMediaFinalDisciplina()).isEqualTo(DEFAULT_MEDIA_FINAL_DISCIPLINA);
        assertThat(testResumoAcademico.getClassificacaoFinal()).isEqualTo(DEFAULT_CLASSIFICACAO_FINAL);
        assertThat(testResumoAcademico.getNumeroGrupo()).isEqualTo(DEFAULT_NUMERO_GRUPO);
        assertThat(testResumoAcademico.getMesaDefesa()).isEqualTo(DEFAULT_MESA_DEFESA);
        assertThat(testResumoAcademico.getLivroRegistro()).isEqualTo(DEFAULT_LIVRO_REGISTRO);
        assertThat(testResumoAcademico.getNumeroFolha()).isEqualTo(DEFAULT_NUMERO_FOLHA);
        assertThat(testResumoAcademico.getChefeSecretariaPedagogica()).isEqualTo(DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA);
        assertThat(testResumoAcademico.getSubDirectorPedagogico()).isEqualTo(DEFAULT_SUB_DIRECTOR_PEDAGOGICO);
        assertThat(testResumoAcademico.getDirectorGeral()).isEqualTo(DEFAULT_DIRECTOR_GERAL);
        assertThat(testResumoAcademico.getTutorProjecto()).isEqualTo(DEFAULT_TUTOR_PROJECTO);
        assertThat(testResumoAcademico.getJuriMesa()).isEqualTo(DEFAULT_JURI_MESA);
        assertThat(testResumoAcademico.getEmpresaEstagio()).isEqualTo(DEFAULT_EMPRESA_ESTAGIO);
        assertThat(testResumoAcademico.getAssinaturaDigital()).isEqualTo(DEFAULT_ASSINATURA_DIGITAL);
        assertThat(testResumoAcademico.getAssinaturaDigitalContentType()).isEqualTo(DEFAULT_ASSINATURA_DIGITAL_CONTENT_TYPE);
        assertThat(testResumoAcademico.getHash()).isEqualTo(DEFAULT_HASH);
    }

    @Test
    @Transactional
    void createResumoAcademicoWithExistingId() throws Exception {
        // Create the ResumoAcademico with an existing ID
        resumoAcademico.setId(1L);
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        int databaseSizeBeforeCreate = resumoAcademicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumoAcademico in the database
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTemaProjectoIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setTemaProjecto(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotaProjectoIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setNotaProjecto(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotaEstagioIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setNotaEstagio(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMediaFinalDisciplinaIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setMediaFinalDisciplina(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClassificacaoFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setClassificacaoFinal(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroGrupoIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setNumeroGrupo(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMesaDefesaIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setMesaDefesa(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLivroRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setLivroRegistro(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroFolhaIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setNumeroFolha(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChefeSecretariaPedagogicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setChefeSecretariaPedagogica(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSubDirectorPedagogicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setSubDirectorPedagogico(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDirectorGeralIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setDirectorGeral(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTutorProjectoIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setTutorProjecto(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJuriMesaIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setJuriMesa(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmpresaEstagioIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setEmpresaEstagio(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoAcademicoRepository.findAll().size();
        // set the field null
        resumoAcademico.setHash(null);

        // Create the ResumoAcademico, which fails.
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResumoAcademicos() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList
        restResumoAcademicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumoAcademico.getId().intValue())))
            .andExpect(jsonPath("$.[*].temaProjecto").value(hasItem(DEFAULT_TEMA_PROJECTO)))
            .andExpect(jsonPath("$.[*].notaProjecto").value(hasItem(DEFAULT_NOTA_PROJECTO.doubleValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].localEstagio").value(hasItem(DEFAULT_LOCAL_ESTAGIO)))
            .andExpect(jsonPath("$.[*].notaEstagio").value(hasItem(DEFAULT_NOTA_ESTAGIO.doubleValue())))
            .andExpect(jsonPath("$.[*].mediaFinalDisciplina").value(hasItem(DEFAULT_MEDIA_FINAL_DISCIPLINA.doubleValue())))
            .andExpect(jsonPath("$.[*].classificacaoFinal").value(hasItem(DEFAULT_CLASSIFICACAO_FINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].numeroGrupo").value(hasItem(DEFAULT_NUMERO_GRUPO)))
            .andExpect(jsonPath("$.[*].mesaDefesa").value(hasItem(DEFAULT_MESA_DEFESA)))
            .andExpect(jsonPath("$.[*].livroRegistro").value(hasItem(DEFAULT_LIVRO_REGISTRO)))
            .andExpect(jsonPath("$.[*].numeroFolha").value(hasItem(DEFAULT_NUMERO_FOLHA)))
            .andExpect(jsonPath("$.[*].chefeSecretariaPedagogica").value(hasItem(DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA)))
            .andExpect(jsonPath("$.[*].subDirectorPedagogico").value(hasItem(DEFAULT_SUB_DIRECTOR_PEDAGOGICO)))
            .andExpect(jsonPath("$.[*].directorGeral").value(hasItem(DEFAULT_DIRECTOR_GERAL)))
            .andExpect(jsonPath("$.[*].tutorProjecto").value(hasItem(DEFAULT_TUTOR_PROJECTO)))
            .andExpect(jsonPath("$.[*].juriMesa").value(hasItem(DEFAULT_JURI_MESA)))
            .andExpect(jsonPath("$.[*].empresaEstagio").value(hasItem(DEFAULT_EMPRESA_ESTAGIO)))
            .andExpect(jsonPath("$.[*].assinaturaDigitalContentType").value(hasItem(DEFAULT_ASSINATURA_DIGITAL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].assinaturaDigital").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSINATURA_DIGITAL))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResumoAcademicosWithEagerRelationshipsIsEnabled() throws Exception {
        when(resumoAcademicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResumoAcademicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(resumoAcademicoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResumoAcademicosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(resumoAcademicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResumoAcademicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(resumoAcademicoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getResumoAcademico() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get the resumoAcademico
        restResumoAcademicoMockMvc
            .perform(get(ENTITY_API_URL_ID, resumoAcademico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resumoAcademico.getId().intValue()))
            .andExpect(jsonPath("$.temaProjecto").value(DEFAULT_TEMA_PROJECTO))
            .andExpect(jsonPath("$.notaProjecto").value(DEFAULT_NOTA_PROJECTO.doubleValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.localEstagio").value(DEFAULT_LOCAL_ESTAGIO))
            .andExpect(jsonPath("$.notaEstagio").value(DEFAULT_NOTA_ESTAGIO.doubleValue()))
            .andExpect(jsonPath("$.mediaFinalDisciplina").value(DEFAULT_MEDIA_FINAL_DISCIPLINA.doubleValue()))
            .andExpect(jsonPath("$.classificacaoFinal").value(DEFAULT_CLASSIFICACAO_FINAL.doubleValue()))
            .andExpect(jsonPath("$.numeroGrupo").value(DEFAULT_NUMERO_GRUPO))
            .andExpect(jsonPath("$.mesaDefesa").value(DEFAULT_MESA_DEFESA))
            .andExpect(jsonPath("$.livroRegistro").value(DEFAULT_LIVRO_REGISTRO))
            .andExpect(jsonPath("$.numeroFolha").value(DEFAULT_NUMERO_FOLHA))
            .andExpect(jsonPath("$.chefeSecretariaPedagogica").value(DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA))
            .andExpect(jsonPath("$.subDirectorPedagogico").value(DEFAULT_SUB_DIRECTOR_PEDAGOGICO))
            .andExpect(jsonPath("$.directorGeral").value(DEFAULT_DIRECTOR_GERAL))
            .andExpect(jsonPath("$.tutorProjecto").value(DEFAULT_TUTOR_PROJECTO))
            .andExpect(jsonPath("$.juriMesa").value(DEFAULT_JURI_MESA))
            .andExpect(jsonPath("$.empresaEstagio").value(DEFAULT_EMPRESA_ESTAGIO))
            .andExpect(jsonPath("$.assinaturaDigitalContentType").value(DEFAULT_ASSINATURA_DIGITAL_CONTENT_TYPE))
            .andExpect(jsonPath("$.assinaturaDigital").value(Base64Utils.encodeToString(DEFAULT_ASSINATURA_DIGITAL)))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH));
    }

    @Test
    @Transactional
    void getResumoAcademicosByIdFiltering() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        Long id = resumoAcademico.getId();

        defaultResumoAcademicoShouldBeFound("id.equals=" + id);
        defaultResumoAcademicoShouldNotBeFound("id.notEquals=" + id);

        defaultResumoAcademicoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResumoAcademicoShouldNotBeFound("id.greaterThan=" + id);

        defaultResumoAcademicoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResumoAcademicoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByTemaProjectoIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where temaProjecto equals to DEFAULT_TEMA_PROJECTO
        defaultResumoAcademicoShouldBeFound("temaProjecto.equals=" + DEFAULT_TEMA_PROJECTO);

        // Get all the resumoAcademicoList where temaProjecto equals to UPDATED_TEMA_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("temaProjecto.equals=" + UPDATED_TEMA_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByTemaProjectoIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where temaProjecto in DEFAULT_TEMA_PROJECTO or UPDATED_TEMA_PROJECTO
        defaultResumoAcademicoShouldBeFound("temaProjecto.in=" + DEFAULT_TEMA_PROJECTO + "," + UPDATED_TEMA_PROJECTO);

        // Get all the resumoAcademicoList where temaProjecto equals to UPDATED_TEMA_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("temaProjecto.in=" + UPDATED_TEMA_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByTemaProjectoIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where temaProjecto is not null
        defaultResumoAcademicoShouldBeFound("temaProjecto.specified=true");

        // Get all the resumoAcademicoList where temaProjecto is null
        defaultResumoAcademicoShouldNotBeFound("temaProjecto.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByTemaProjectoContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where temaProjecto contains DEFAULT_TEMA_PROJECTO
        defaultResumoAcademicoShouldBeFound("temaProjecto.contains=" + DEFAULT_TEMA_PROJECTO);

        // Get all the resumoAcademicoList where temaProjecto contains UPDATED_TEMA_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("temaProjecto.contains=" + UPDATED_TEMA_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByTemaProjectoNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where temaProjecto does not contain DEFAULT_TEMA_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("temaProjecto.doesNotContain=" + DEFAULT_TEMA_PROJECTO);

        // Get all the resumoAcademicoList where temaProjecto does not contain UPDATED_TEMA_PROJECTO
        defaultResumoAcademicoShouldBeFound("temaProjecto.doesNotContain=" + UPDATED_TEMA_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaProjectoIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaProjecto equals to DEFAULT_NOTA_PROJECTO
        defaultResumoAcademicoShouldBeFound("notaProjecto.equals=" + DEFAULT_NOTA_PROJECTO);

        // Get all the resumoAcademicoList where notaProjecto equals to UPDATED_NOTA_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("notaProjecto.equals=" + UPDATED_NOTA_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaProjectoIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaProjecto in DEFAULT_NOTA_PROJECTO or UPDATED_NOTA_PROJECTO
        defaultResumoAcademicoShouldBeFound("notaProjecto.in=" + DEFAULT_NOTA_PROJECTO + "," + UPDATED_NOTA_PROJECTO);

        // Get all the resumoAcademicoList where notaProjecto equals to UPDATED_NOTA_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("notaProjecto.in=" + UPDATED_NOTA_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaProjectoIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaProjecto is not null
        defaultResumoAcademicoShouldBeFound("notaProjecto.specified=true");

        // Get all the resumoAcademicoList where notaProjecto is null
        defaultResumoAcademicoShouldNotBeFound("notaProjecto.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaProjectoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaProjecto is greater than or equal to DEFAULT_NOTA_PROJECTO
        defaultResumoAcademicoShouldBeFound("notaProjecto.greaterThanOrEqual=" + DEFAULT_NOTA_PROJECTO);

        // Get all the resumoAcademicoList where notaProjecto is greater than or equal to (DEFAULT_NOTA_PROJECTO + 1)
        defaultResumoAcademicoShouldNotBeFound("notaProjecto.greaterThanOrEqual=" + (DEFAULT_NOTA_PROJECTO + 1));
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaProjectoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaProjecto is less than or equal to DEFAULT_NOTA_PROJECTO
        defaultResumoAcademicoShouldBeFound("notaProjecto.lessThanOrEqual=" + DEFAULT_NOTA_PROJECTO);

        // Get all the resumoAcademicoList where notaProjecto is less than or equal to SMALLER_NOTA_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("notaProjecto.lessThanOrEqual=" + SMALLER_NOTA_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaProjectoIsLessThanSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaProjecto is less than DEFAULT_NOTA_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("notaProjecto.lessThan=" + DEFAULT_NOTA_PROJECTO);

        // Get all the resumoAcademicoList where notaProjecto is less than (DEFAULT_NOTA_PROJECTO + 1)
        defaultResumoAcademicoShouldBeFound("notaProjecto.lessThan=" + (DEFAULT_NOTA_PROJECTO + 1));
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaProjectoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaProjecto is greater than DEFAULT_NOTA_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("notaProjecto.greaterThan=" + DEFAULT_NOTA_PROJECTO);

        // Get all the resumoAcademicoList where notaProjecto is greater than SMALLER_NOTA_PROJECTO
        defaultResumoAcademicoShouldBeFound("notaProjecto.greaterThan=" + SMALLER_NOTA_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByLocalEstagioIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where localEstagio equals to DEFAULT_LOCAL_ESTAGIO
        defaultResumoAcademicoShouldBeFound("localEstagio.equals=" + DEFAULT_LOCAL_ESTAGIO);

        // Get all the resumoAcademicoList where localEstagio equals to UPDATED_LOCAL_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("localEstagio.equals=" + UPDATED_LOCAL_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByLocalEstagioIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where localEstagio in DEFAULT_LOCAL_ESTAGIO or UPDATED_LOCAL_ESTAGIO
        defaultResumoAcademicoShouldBeFound("localEstagio.in=" + DEFAULT_LOCAL_ESTAGIO + "," + UPDATED_LOCAL_ESTAGIO);

        // Get all the resumoAcademicoList where localEstagio equals to UPDATED_LOCAL_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("localEstagio.in=" + UPDATED_LOCAL_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByLocalEstagioIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where localEstagio is not null
        defaultResumoAcademicoShouldBeFound("localEstagio.specified=true");

        // Get all the resumoAcademicoList where localEstagio is null
        defaultResumoAcademicoShouldNotBeFound("localEstagio.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByLocalEstagioContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where localEstagio contains DEFAULT_LOCAL_ESTAGIO
        defaultResumoAcademicoShouldBeFound("localEstagio.contains=" + DEFAULT_LOCAL_ESTAGIO);

        // Get all the resumoAcademicoList where localEstagio contains UPDATED_LOCAL_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("localEstagio.contains=" + UPDATED_LOCAL_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByLocalEstagioNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where localEstagio does not contain DEFAULT_LOCAL_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("localEstagio.doesNotContain=" + DEFAULT_LOCAL_ESTAGIO);

        // Get all the resumoAcademicoList where localEstagio does not contain UPDATED_LOCAL_ESTAGIO
        defaultResumoAcademicoShouldBeFound("localEstagio.doesNotContain=" + UPDATED_LOCAL_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaEstagioIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaEstagio equals to DEFAULT_NOTA_ESTAGIO
        defaultResumoAcademicoShouldBeFound("notaEstagio.equals=" + DEFAULT_NOTA_ESTAGIO);

        // Get all the resumoAcademicoList where notaEstagio equals to UPDATED_NOTA_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("notaEstagio.equals=" + UPDATED_NOTA_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaEstagioIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaEstagio in DEFAULT_NOTA_ESTAGIO or UPDATED_NOTA_ESTAGIO
        defaultResumoAcademicoShouldBeFound("notaEstagio.in=" + DEFAULT_NOTA_ESTAGIO + "," + UPDATED_NOTA_ESTAGIO);

        // Get all the resumoAcademicoList where notaEstagio equals to UPDATED_NOTA_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("notaEstagio.in=" + UPDATED_NOTA_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaEstagioIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaEstagio is not null
        defaultResumoAcademicoShouldBeFound("notaEstagio.specified=true");

        // Get all the resumoAcademicoList where notaEstagio is null
        defaultResumoAcademicoShouldNotBeFound("notaEstagio.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaEstagioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaEstagio is greater than or equal to DEFAULT_NOTA_ESTAGIO
        defaultResumoAcademicoShouldBeFound("notaEstagio.greaterThanOrEqual=" + DEFAULT_NOTA_ESTAGIO);

        // Get all the resumoAcademicoList where notaEstagio is greater than or equal to (DEFAULT_NOTA_ESTAGIO + 1)
        defaultResumoAcademicoShouldNotBeFound("notaEstagio.greaterThanOrEqual=" + (DEFAULT_NOTA_ESTAGIO + 1));
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaEstagioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaEstagio is less than or equal to DEFAULT_NOTA_ESTAGIO
        defaultResumoAcademicoShouldBeFound("notaEstagio.lessThanOrEqual=" + DEFAULT_NOTA_ESTAGIO);

        // Get all the resumoAcademicoList where notaEstagio is less than or equal to SMALLER_NOTA_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("notaEstagio.lessThanOrEqual=" + SMALLER_NOTA_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaEstagioIsLessThanSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaEstagio is less than DEFAULT_NOTA_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("notaEstagio.lessThan=" + DEFAULT_NOTA_ESTAGIO);

        // Get all the resumoAcademicoList where notaEstagio is less than (DEFAULT_NOTA_ESTAGIO + 1)
        defaultResumoAcademicoShouldBeFound("notaEstagio.lessThan=" + (DEFAULT_NOTA_ESTAGIO + 1));
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNotaEstagioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where notaEstagio is greater than DEFAULT_NOTA_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("notaEstagio.greaterThan=" + DEFAULT_NOTA_ESTAGIO);

        // Get all the resumoAcademicoList where notaEstagio is greater than SMALLER_NOTA_ESTAGIO
        defaultResumoAcademicoShouldBeFound("notaEstagio.greaterThan=" + SMALLER_NOTA_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMediaFinalDisciplinaIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mediaFinalDisciplina equals to DEFAULT_MEDIA_FINAL_DISCIPLINA
        defaultResumoAcademicoShouldBeFound("mediaFinalDisciplina.equals=" + DEFAULT_MEDIA_FINAL_DISCIPLINA);

        // Get all the resumoAcademicoList where mediaFinalDisciplina equals to UPDATED_MEDIA_FINAL_DISCIPLINA
        defaultResumoAcademicoShouldNotBeFound("mediaFinalDisciplina.equals=" + UPDATED_MEDIA_FINAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMediaFinalDisciplinaIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mediaFinalDisciplina in DEFAULT_MEDIA_FINAL_DISCIPLINA or UPDATED_MEDIA_FINAL_DISCIPLINA
        defaultResumoAcademicoShouldBeFound(
            "mediaFinalDisciplina.in=" + DEFAULT_MEDIA_FINAL_DISCIPLINA + "," + UPDATED_MEDIA_FINAL_DISCIPLINA
        );

        // Get all the resumoAcademicoList where mediaFinalDisciplina equals to UPDATED_MEDIA_FINAL_DISCIPLINA
        defaultResumoAcademicoShouldNotBeFound("mediaFinalDisciplina.in=" + UPDATED_MEDIA_FINAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMediaFinalDisciplinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mediaFinalDisciplina is not null
        defaultResumoAcademicoShouldBeFound("mediaFinalDisciplina.specified=true");

        // Get all the resumoAcademicoList where mediaFinalDisciplina is null
        defaultResumoAcademicoShouldNotBeFound("mediaFinalDisciplina.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMediaFinalDisciplinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mediaFinalDisciplina is greater than or equal to DEFAULT_MEDIA_FINAL_DISCIPLINA
        defaultResumoAcademicoShouldBeFound("mediaFinalDisciplina.greaterThanOrEqual=" + DEFAULT_MEDIA_FINAL_DISCIPLINA);

        // Get all the resumoAcademicoList where mediaFinalDisciplina is greater than or equal to (DEFAULT_MEDIA_FINAL_DISCIPLINA + 1)
        defaultResumoAcademicoShouldNotBeFound("mediaFinalDisciplina.greaterThanOrEqual=" + (DEFAULT_MEDIA_FINAL_DISCIPLINA + 1));
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMediaFinalDisciplinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mediaFinalDisciplina is less than or equal to DEFAULT_MEDIA_FINAL_DISCIPLINA
        defaultResumoAcademicoShouldBeFound("mediaFinalDisciplina.lessThanOrEqual=" + DEFAULT_MEDIA_FINAL_DISCIPLINA);

        // Get all the resumoAcademicoList where mediaFinalDisciplina is less than or equal to SMALLER_MEDIA_FINAL_DISCIPLINA
        defaultResumoAcademicoShouldNotBeFound("mediaFinalDisciplina.lessThanOrEqual=" + SMALLER_MEDIA_FINAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMediaFinalDisciplinaIsLessThanSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mediaFinalDisciplina is less than DEFAULT_MEDIA_FINAL_DISCIPLINA
        defaultResumoAcademicoShouldNotBeFound("mediaFinalDisciplina.lessThan=" + DEFAULT_MEDIA_FINAL_DISCIPLINA);

        // Get all the resumoAcademicoList where mediaFinalDisciplina is less than (DEFAULT_MEDIA_FINAL_DISCIPLINA + 1)
        defaultResumoAcademicoShouldBeFound("mediaFinalDisciplina.lessThan=" + (DEFAULT_MEDIA_FINAL_DISCIPLINA + 1));
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMediaFinalDisciplinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mediaFinalDisciplina is greater than DEFAULT_MEDIA_FINAL_DISCIPLINA
        defaultResumoAcademicoShouldNotBeFound("mediaFinalDisciplina.greaterThan=" + DEFAULT_MEDIA_FINAL_DISCIPLINA);

        // Get all the resumoAcademicoList where mediaFinalDisciplina is greater than SMALLER_MEDIA_FINAL_DISCIPLINA
        defaultResumoAcademicoShouldBeFound("mediaFinalDisciplina.greaterThan=" + SMALLER_MEDIA_FINAL_DISCIPLINA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByClassificacaoFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where classificacaoFinal equals to DEFAULT_CLASSIFICACAO_FINAL
        defaultResumoAcademicoShouldBeFound("classificacaoFinal.equals=" + DEFAULT_CLASSIFICACAO_FINAL);

        // Get all the resumoAcademicoList where classificacaoFinal equals to UPDATED_CLASSIFICACAO_FINAL
        defaultResumoAcademicoShouldNotBeFound("classificacaoFinal.equals=" + UPDATED_CLASSIFICACAO_FINAL);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByClassificacaoFinalIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where classificacaoFinal in DEFAULT_CLASSIFICACAO_FINAL or UPDATED_CLASSIFICACAO_FINAL
        defaultResumoAcademicoShouldBeFound("classificacaoFinal.in=" + DEFAULT_CLASSIFICACAO_FINAL + "," + UPDATED_CLASSIFICACAO_FINAL);

        // Get all the resumoAcademicoList where classificacaoFinal equals to UPDATED_CLASSIFICACAO_FINAL
        defaultResumoAcademicoShouldNotBeFound("classificacaoFinal.in=" + UPDATED_CLASSIFICACAO_FINAL);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByClassificacaoFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where classificacaoFinal is not null
        defaultResumoAcademicoShouldBeFound("classificacaoFinal.specified=true");

        // Get all the resumoAcademicoList where classificacaoFinal is null
        defaultResumoAcademicoShouldNotBeFound("classificacaoFinal.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByClassificacaoFinalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where classificacaoFinal is greater than or equal to DEFAULT_CLASSIFICACAO_FINAL
        defaultResumoAcademicoShouldBeFound("classificacaoFinal.greaterThanOrEqual=" + DEFAULT_CLASSIFICACAO_FINAL);

        // Get all the resumoAcademicoList where classificacaoFinal is greater than or equal to (DEFAULT_CLASSIFICACAO_FINAL + 1)
        defaultResumoAcademicoShouldNotBeFound("classificacaoFinal.greaterThanOrEqual=" + (DEFAULT_CLASSIFICACAO_FINAL + 1));
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByClassificacaoFinalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where classificacaoFinal is less than or equal to DEFAULT_CLASSIFICACAO_FINAL
        defaultResumoAcademicoShouldBeFound("classificacaoFinal.lessThanOrEqual=" + DEFAULT_CLASSIFICACAO_FINAL);

        // Get all the resumoAcademicoList where classificacaoFinal is less than or equal to SMALLER_CLASSIFICACAO_FINAL
        defaultResumoAcademicoShouldNotBeFound("classificacaoFinal.lessThanOrEqual=" + SMALLER_CLASSIFICACAO_FINAL);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByClassificacaoFinalIsLessThanSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where classificacaoFinal is less than DEFAULT_CLASSIFICACAO_FINAL
        defaultResumoAcademicoShouldNotBeFound("classificacaoFinal.lessThan=" + DEFAULT_CLASSIFICACAO_FINAL);

        // Get all the resumoAcademicoList where classificacaoFinal is less than (DEFAULT_CLASSIFICACAO_FINAL + 1)
        defaultResumoAcademicoShouldBeFound("classificacaoFinal.lessThan=" + (DEFAULT_CLASSIFICACAO_FINAL + 1));
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByClassificacaoFinalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where classificacaoFinal is greater than DEFAULT_CLASSIFICACAO_FINAL
        defaultResumoAcademicoShouldNotBeFound("classificacaoFinal.greaterThan=" + DEFAULT_CLASSIFICACAO_FINAL);

        // Get all the resumoAcademicoList where classificacaoFinal is greater than SMALLER_CLASSIFICACAO_FINAL
        defaultResumoAcademicoShouldBeFound("classificacaoFinal.greaterThan=" + SMALLER_CLASSIFICACAO_FINAL);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNumeroGrupoIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where numeroGrupo equals to DEFAULT_NUMERO_GRUPO
        defaultResumoAcademicoShouldBeFound("numeroGrupo.equals=" + DEFAULT_NUMERO_GRUPO);

        // Get all the resumoAcademicoList where numeroGrupo equals to UPDATED_NUMERO_GRUPO
        defaultResumoAcademicoShouldNotBeFound("numeroGrupo.equals=" + UPDATED_NUMERO_GRUPO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNumeroGrupoIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where numeroGrupo in DEFAULT_NUMERO_GRUPO or UPDATED_NUMERO_GRUPO
        defaultResumoAcademicoShouldBeFound("numeroGrupo.in=" + DEFAULT_NUMERO_GRUPO + "," + UPDATED_NUMERO_GRUPO);

        // Get all the resumoAcademicoList where numeroGrupo equals to UPDATED_NUMERO_GRUPO
        defaultResumoAcademicoShouldNotBeFound("numeroGrupo.in=" + UPDATED_NUMERO_GRUPO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNumeroGrupoIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where numeroGrupo is not null
        defaultResumoAcademicoShouldBeFound("numeroGrupo.specified=true");

        // Get all the resumoAcademicoList where numeroGrupo is null
        defaultResumoAcademicoShouldNotBeFound("numeroGrupo.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNumeroGrupoContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where numeroGrupo contains DEFAULT_NUMERO_GRUPO
        defaultResumoAcademicoShouldBeFound("numeroGrupo.contains=" + DEFAULT_NUMERO_GRUPO);

        // Get all the resumoAcademicoList where numeroGrupo contains UPDATED_NUMERO_GRUPO
        defaultResumoAcademicoShouldNotBeFound("numeroGrupo.contains=" + UPDATED_NUMERO_GRUPO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNumeroGrupoNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where numeroGrupo does not contain DEFAULT_NUMERO_GRUPO
        defaultResumoAcademicoShouldNotBeFound("numeroGrupo.doesNotContain=" + DEFAULT_NUMERO_GRUPO);

        // Get all the resumoAcademicoList where numeroGrupo does not contain UPDATED_NUMERO_GRUPO
        defaultResumoAcademicoShouldBeFound("numeroGrupo.doesNotContain=" + UPDATED_NUMERO_GRUPO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMesaDefesaIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mesaDefesa equals to DEFAULT_MESA_DEFESA
        defaultResumoAcademicoShouldBeFound("mesaDefesa.equals=" + DEFAULT_MESA_DEFESA);

        // Get all the resumoAcademicoList where mesaDefesa equals to UPDATED_MESA_DEFESA
        defaultResumoAcademicoShouldNotBeFound("mesaDefesa.equals=" + UPDATED_MESA_DEFESA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMesaDefesaIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mesaDefesa in DEFAULT_MESA_DEFESA or UPDATED_MESA_DEFESA
        defaultResumoAcademicoShouldBeFound("mesaDefesa.in=" + DEFAULT_MESA_DEFESA + "," + UPDATED_MESA_DEFESA);

        // Get all the resumoAcademicoList where mesaDefesa equals to UPDATED_MESA_DEFESA
        defaultResumoAcademicoShouldNotBeFound("mesaDefesa.in=" + UPDATED_MESA_DEFESA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMesaDefesaIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mesaDefesa is not null
        defaultResumoAcademicoShouldBeFound("mesaDefesa.specified=true");

        // Get all the resumoAcademicoList where mesaDefesa is null
        defaultResumoAcademicoShouldNotBeFound("mesaDefesa.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMesaDefesaContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mesaDefesa contains DEFAULT_MESA_DEFESA
        defaultResumoAcademicoShouldBeFound("mesaDefesa.contains=" + DEFAULT_MESA_DEFESA);

        // Get all the resumoAcademicoList where mesaDefesa contains UPDATED_MESA_DEFESA
        defaultResumoAcademicoShouldNotBeFound("mesaDefesa.contains=" + UPDATED_MESA_DEFESA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByMesaDefesaNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where mesaDefesa does not contain DEFAULT_MESA_DEFESA
        defaultResumoAcademicoShouldNotBeFound("mesaDefesa.doesNotContain=" + DEFAULT_MESA_DEFESA);

        // Get all the resumoAcademicoList where mesaDefesa does not contain UPDATED_MESA_DEFESA
        defaultResumoAcademicoShouldBeFound("mesaDefesa.doesNotContain=" + UPDATED_MESA_DEFESA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByLivroRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where livroRegistro equals to DEFAULT_LIVRO_REGISTRO
        defaultResumoAcademicoShouldBeFound("livroRegistro.equals=" + DEFAULT_LIVRO_REGISTRO);

        // Get all the resumoAcademicoList where livroRegistro equals to UPDATED_LIVRO_REGISTRO
        defaultResumoAcademicoShouldNotBeFound("livroRegistro.equals=" + UPDATED_LIVRO_REGISTRO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByLivroRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where livroRegistro in DEFAULT_LIVRO_REGISTRO or UPDATED_LIVRO_REGISTRO
        defaultResumoAcademicoShouldBeFound("livroRegistro.in=" + DEFAULT_LIVRO_REGISTRO + "," + UPDATED_LIVRO_REGISTRO);

        // Get all the resumoAcademicoList where livroRegistro equals to UPDATED_LIVRO_REGISTRO
        defaultResumoAcademicoShouldNotBeFound("livroRegistro.in=" + UPDATED_LIVRO_REGISTRO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByLivroRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where livroRegistro is not null
        defaultResumoAcademicoShouldBeFound("livroRegistro.specified=true");

        // Get all the resumoAcademicoList where livroRegistro is null
        defaultResumoAcademicoShouldNotBeFound("livroRegistro.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByLivroRegistroContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where livroRegistro contains DEFAULT_LIVRO_REGISTRO
        defaultResumoAcademicoShouldBeFound("livroRegistro.contains=" + DEFAULT_LIVRO_REGISTRO);

        // Get all the resumoAcademicoList where livroRegistro contains UPDATED_LIVRO_REGISTRO
        defaultResumoAcademicoShouldNotBeFound("livroRegistro.contains=" + UPDATED_LIVRO_REGISTRO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByLivroRegistroNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where livroRegistro does not contain DEFAULT_LIVRO_REGISTRO
        defaultResumoAcademicoShouldNotBeFound("livroRegistro.doesNotContain=" + DEFAULT_LIVRO_REGISTRO);

        // Get all the resumoAcademicoList where livroRegistro does not contain UPDATED_LIVRO_REGISTRO
        defaultResumoAcademicoShouldBeFound("livroRegistro.doesNotContain=" + UPDATED_LIVRO_REGISTRO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNumeroFolhaIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where numeroFolha equals to DEFAULT_NUMERO_FOLHA
        defaultResumoAcademicoShouldBeFound("numeroFolha.equals=" + DEFAULT_NUMERO_FOLHA);

        // Get all the resumoAcademicoList where numeroFolha equals to UPDATED_NUMERO_FOLHA
        defaultResumoAcademicoShouldNotBeFound("numeroFolha.equals=" + UPDATED_NUMERO_FOLHA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNumeroFolhaIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where numeroFolha in DEFAULT_NUMERO_FOLHA or UPDATED_NUMERO_FOLHA
        defaultResumoAcademicoShouldBeFound("numeroFolha.in=" + DEFAULT_NUMERO_FOLHA + "," + UPDATED_NUMERO_FOLHA);

        // Get all the resumoAcademicoList where numeroFolha equals to UPDATED_NUMERO_FOLHA
        defaultResumoAcademicoShouldNotBeFound("numeroFolha.in=" + UPDATED_NUMERO_FOLHA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNumeroFolhaIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where numeroFolha is not null
        defaultResumoAcademicoShouldBeFound("numeroFolha.specified=true");

        // Get all the resumoAcademicoList where numeroFolha is null
        defaultResumoAcademicoShouldNotBeFound("numeroFolha.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNumeroFolhaContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where numeroFolha contains DEFAULT_NUMERO_FOLHA
        defaultResumoAcademicoShouldBeFound("numeroFolha.contains=" + DEFAULT_NUMERO_FOLHA);

        // Get all the resumoAcademicoList where numeroFolha contains UPDATED_NUMERO_FOLHA
        defaultResumoAcademicoShouldNotBeFound("numeroFolha.contains=" + UPDATED_NUMERO_FOLHA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByNumeroFolhaNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where numeroFolha does not contain DEFAULT_NUMERO_FOLHA
        defaultResumoAcademicoShouldNotBeFound("numeroFolha.doesNotContain=" + DEFAULT_NUMERO_FOLHA);

        // Get all the resumoAcademicoList where numeroFolha does not contain UPDATED_NUMERO_FOLHA
        defaultResumoAcademicoShouldBeFound("numeroFolha.doesNotContain=" + UPDATED_NUMERO_FOLHA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByChefeSecretariaPedagogicaIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where chefeSecretariaPedagogica equals to DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA
        defaultResumoAcademicoShouldBeFound("chefeSecretariaPedagogica.equals=" + DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA);

        // Get all the resumoAcademicoList where chefeSecretariaPedagogica equals to UPDATED_CHEFE_SECRETARIA_PEDAGOGICA
        defaultResumoAcademicoShouldNotBeFound("chefeSecretariaPedagogica.equals=" + UPDATED_CHEFE_SECRETARIA_PEDAGOGICA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByChefeSecretariaPedagogicaIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where chefeSecretariaPedagogica in DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA or UPDATED_CHEFE_SECRETARIA_PEDAGOGICA
        defaultResumoAcademicoShouldBeFound(
            "chefeSecretariaPedagogica.in=" + DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA + "," + UPDATED_CHEFE_SECRETARIA_PEDAGOGICA
        );

        // Get all the resumoAcademicoList where chefeSecretariaPedagogica equals to UPDATED_CHEFE_SECRETARIA_PEDAGOGICA
        defaultResumoAcademicoShouldNotBeFound("chefeSecretariaPedagogica.in=" + UPDATED_CHEFE_SECRETARIA_PEDAGOGICA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByChefeSecretariaPedagogicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where chefeSecretariaPedagogica is not null
        defaultResumoAcademicoShouldBeFound("chefeSecretariaPedagogica.specified=true");

        // Get all the resumoAcademicoList where chefeSecretariaPedagogica is null
        defaultResumoAcademicoShouldNotBeFound("chefeSecretariaPedagogica.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByChefeSecretariaPedagogicaContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where chefeSecretariaPedagogica contains DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA
        defaultResumoAcademicoShouldBeFound("chefeSecretariaPedagogica.contains=" + DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA);

        // Get all the resumoAcademicoList where chefeSecretariaPedagogica contains UPDATED_CHEFE_SECRETARIA_PEDAGOGICA
        defaultResumoAcademicoShouldNotBeFound("chefeSecretariaPedagogica.contains=" + UPDATED_CHEFE_SECRETARIA_PEDAGOGICA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByChefeSecretariaPedagogicaNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where chefeSecretariaPedagogica does not contain DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA
        defaultResumoAcademicoShouldNotBeFound("chefeSecretariaPedagogica.doesNotContain=" + DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA);

        // Get all the resumoAcademicoList where chefeSecretariaPedagogica does not contain UPDATED_CHEFE_SECRETARIA_PEDAGOGICA
        defaultResumoAcademicoShouldBeFound("chefeSecretariaPedagogica.doesNotContain=" + UPDATED_CHEFE_SECRETARIA_PEDAGOGICA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosBySubDirectorPedagogicoIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where subDirectorPedagogico equals to DEFAULT_SUB_DIRECTOR_PEDAGOGICO
        defaultResumoAcademicoShouldBeFound("subDirectorPedagogico.equals=" + DEFAULT_SUB_DIRECTOR_PEDAGOGICO);

        // Get all the resumoAcademicoList where subDirectorPedagogico equals to UPDATED_SUB_DIRECTOR_PEDAGOGICO
        defaultResumoAcademicoShouldNotBeFound("subDirectorPedagogico.equals=" + UPDATED_SUB_DIRECTOR_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosBySubDirectorPedagogicoIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where subDirectorPedagogico in DEFAULT_SUB_DIRECTOR_PEDAGOGICO or UPDATED_SUB_DIRECTOR_PEDAGOGICO
        defaultResumoAcademicoShouldBeFound(
            "subDirectorPedagogico.in=" + DEFAULT_SUB_DIRECTOR_PEDAGOGICO + "," + UPDATED_SUB_DIRECTOR_PEDAGOGICO
        );

        // Get all the resumoAcademicoList where subDirectorPedagogico equals to UPDATED_SUB_DIRECTOR_PEDAGOGICO
        defaultResumoAcademicoShouldNotBeFound("subDirectorPedagogico.in=" + UPDATED_SUB_DIRECTOR_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosBySubDirectorPedagogicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where subDirectorPedagogico is not null
        defaultResumoAcademicoShouldBeFound("subDirectorPedagogico.specified=true");

        // Get all the resumoAcademicoList where subDirectorPedagogico is null
        defaultResumoAcademicoShouldNotBeFound("subDirectorPedagogico.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosBySubDirectorPedagogicoContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where subDirectorPedagogico contains DEFAULT_SUB_DIRECTOR_PEDAGOGICO
        defaultResumoAcademicoShouldBeFound("subDirectorPedagogico.contains=" + DEFAULT_SUB_DIRECTOR_PEDAGOGICO);

        // Get all the resumoAcademicoList where subDirectorPedagogico contains UPDATED_SUB_DIRECTOR_PEDAGOGICO
        defaultResumoAcademicoShouldNotBeFound("subDirectorPedagogico.contains=" + UPDATED_SUB_DIRECTOR_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosBySubDirectorPedagogicoNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where subDirectorPedagogico does not contain DEFAULT_SUB_DIRECTOR_PEDAGOGICO
        defaultResumoAcademicoShouldNotBeFound("subDirectorPedagogico.doesNotContain=" + DEFAULT_SUB_DIRECTOR_PEDAGOGICO);

        // Get all the resumoAcademicoList where subDirectorPedagogico does not contain UPDATED_SUB_DIRECTOR_PEDAGOGICO
        defaultResumoAcademicoShouldBeFound("subDirectorPedagogico.doesNotContain=" + UPDATED_SUB_DIRECTOR_PEDAGOGICO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByDirectorGeralIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where directorGeral equals to DEFAULT_DIRECTOR_GERAL
        defaultResumoAcademicoShouldBeFound("directorGeral.equals=" + DEFAULT_DIRECTOR_GERAL);

        // Get all the resumoAcademicoList where directorGeral equals to UPDATED_DIRECTOR_GERAL
        defaultResumoAcademicoShouldNotBeFound("directorGeral.equals=" + UPDATED_DIRECTOR_GERAL);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByDirectorGeralIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where directorGeral in DEFAULT_DIRECTOR_GERAL or UPDATED_DIRECTOR_GERAL
        defaultResumoAcademicoShouldBeFound("directorGeral.in=" + DEFAULT_DIRECTOR_GERAL + "," + UPDATED_DIRECTOR_GERAL);

        // Get all the resumoAcademicoList where directorGeral equals to UPDATED_DIRECTOR_GERAL
        defaultResumoAcademicoShouldNotBeFound("directorGeral.in=" + UPDATED_DIRECTOR_GERAL);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByDirectorGeralIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where directorGeral is not null
        defaultResumoAcademicoShouldBeFound("directorGeral.specified=true");

        // Get all the resumoAcademicoList where directorGeral is null
        defaultResumoAcademicoShouldNotBeFound("directorGeral.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByDirectorGeralContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where directorGeral contains DEFAULT_DIRECTOR_GERAL
        defaultResumoAcademicoShouldBeFound("directorGeral.contains=" + DEFAULT_DIRECTOR_GERAL);

        // Get all the resumoAcademicoList where directorGeral contains UPDATED_DIRECTOR_GERAL
        defaultResumoAcademicoShouldNotBeFound("directorGeral.contains=" + UPDATED_DIRECTOR_GERAL);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByDirectorGeralNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where directorGeral does not contain DEFAULT_DIRECTOR_GERAL
        defaultResumoAcademicoShouldNotBeFound("directorGeral.doesNotContain=" + DEFAULT_DIRECTOR_GERAL);

        // Get all the resumoAcademicoList where directorGeral does not contain UPDATED_DIRECTOR_GERAL
        defaultResumoAcademicoShouldBeFound("directorGeral.doesNotContain=" + UPDATED_DIRECTOR_GERAL);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByTutorProjectoIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where tutorProjecto equals to DEFAULT_TUTOR_PROJECTO
        defaultResumoAcademicoShouldBeFound("tutorProjecto.equals=" + DEFAULT_TUTOR_PROJECTO);

        // Get all the resumoAcademicoList where tutorProjecto equals to UPDATED_TUTOR_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("tutorProjecto.equals=" + UPDATED_TUTOR_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByTutorProjectoIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where tutorProjecto in DEFAULT_TUTOR_PROJECTO or UPDATED_TUTOR_PROJECTO
        defaultResumoAcademicoShouldBeFound("tutorProjecto.in=" + DEFAULT_TUTOR_PROJECTO + "," + UPDATED_TUTOR_PROJECTO);

        // Get all the resumoAcademicoList where tutorProjecto equals to UPDATED_TUTOR_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("tutorProjecto.in=" + UPDATED_TUTOR_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByTutorProjectoIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where tutorProjecto is not null
        defaultResumoAcademicoShouldBeFound("tutorProjecto.specified=true");

        // Get all the resumoAcademicoList where tutorProjecto is null
        defaultResumoAcademicoShouldNotBeFound("tutorProjecto.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByTutorProjectoContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where tutorProjecto contains DEFAULT_TUTOR_PROJECTO
        defaultResumoAcademicoShouldBeFound("tutorProjecto.contains=" + DEFAULT_TUTOR_PROJECTO);

        // Get all the resumoAcademicoList where tutorProjecto contains UPDATED_TUTOR_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("tutorProjecto.contains=" + UPDATED_TUTOR_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByTutorProjectoNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where tutorProjecto does not contain DEFAULT_TUTOR_PROJECTO
        defaultResumoAcademicoShouldNotBeFound("tutorProjecto.doesNotContain=" + DEFAULT_TUTOR_PROJECTO);

        // Get all the resumoAcademicoList where tutorProjecto does not contain UPDATED_TUTOR_PROJECTO
        defaultResumoAcademicoShouldBeFound("tutorProjecto.doesNotContain=" + UPDATED_TUTOR_PROJECTO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByJuriMesaIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where juriMesa equals to DEFAULT_JURI_MESA
        defaultResumoAcademicoShouldBeFound("juriMesa.equals=" + DEFAULT_JURI_MESA);

        // Get all the resumoAcademicoList where juriMesa equals to UPDATED_JURI_MESA
        defaultResumoAcademicoShouldNotBeFound("juriMesa.equals=" + UPDATED_JURI_MESA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByJuriMesaIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where juriMesa in DEFAULT_JURI_MESA or UPDATED_JURI_MESA
        defaultResumoAcademicoShouldBeFound("juriMesa.in=" + DEFAULT_JURI_MESA + "," + UPDATED_JURI_MESA);

        // Get all the resumoAcademicoList where juriMesa equals to UPDATED_JURI_MESA
        defaultResumoAcademicoShouldNotBeFound("juriMesa.in=" + UPDATED_JURI_MESA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByJuriMesaIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where juriMesa is not null
        defaultResumoAcademicoShouldBeFound("juriMesa.specified=true");

        // Get all the resumoAcademicoList where juriMesa is null
        defaultResumoAcademicoShouldNotBeFound("juriMesa.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByJuriMesaContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where juriMesa contains DEFAULT_JURI_MESA
        defaultResumoAcademicoShouldBeFound("juriMesa.contains=" + DEFAULT_JURI_MESA);

        // Get all the resumoAcademicoList where juriMesa contains UPDATED_JURI_MESA
        defaultResumoAcademicoShouldNotBeFound("juriMesa.contains=" + UPDATED_JURI_MESA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByJuriMesaNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where juriMesa does not contain DEFAULT_JURI_MESA
        defaultResumoAcademicoShouldNotBeFound("juriMesa.doesNotContain=" + DEFAULT_JURI_MESA);

        // Get all the resumoAcademicoList where juriMesa does not contain UPDATED_JURI_MESA
        defaultResumoAcademicoShouldBeFound("juriMesa.doesNotContain=" + UPDATED_JURI_MESA);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByEmpresaEstagioIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where empresaEstagio equals to DEFAULT_EMPRESA_ESTAGIO
        defaultResumoAcademicoShouldBeFound("empresaEstagio.equals=" + DEFAULT_EMPRESA_ESTAGIO);

        // Get all the resumoAcademicoList where empresaEstagio equals to UPDATED_EMPRESA_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("empresaEstagio.equals=" + UPDATED_EMPRESA_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByEmpresaEstagioIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where empresaEstagio in DEFAULT_EMPRESA_ESTAGIO or UPDATED_EMPRESA_ESTAGIO
        defaultResumoAcademicoShouldBeFound("empresaEstagio.in=" + DEFAULT_EMPRESA_ESTAGIO + "," + UPDATED_EMPRESA_ESTAGIO);

        // Get all the resumoAcademicoList where empresaEstagio equals to UPDATED_EMPRESA_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("empresaEstagio.in=" + UPDATED_EMPRESA_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByEmpresaEstagioIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where empresaEstagio is not null
        defaultResumoAcademicoShouldBeFound("empresaEstagio.specified=true");

        // Get all the resumoAcademicoList where empresaEstagio is null
        defaultResumoAcademicoShouldNotBeFound("empresaEstagio.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByEmpresaEstagioContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where empresaEstagio contains DEFAULT_EMPRESA_ESTAGIO
        defaultResumoAcademicoShouldBeFound("empresaEstagio.contains=" + DEFAULT_EMPRESA_ESTAGIO);

        // Get all the resumoAcademicoList where empresaEstagio contains UPDATED_EMPRESA_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("empresaEstagio.contains=" + UPDATED_EMPRESA_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByEmpresaEstagioNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where empresaEstagio does not contain DEFAULT_EMPRESA_ESTAGIO
        defaultResumoAcademicoShouldNotBeFound("empresaEstagio.doesNotContain=" + DEFAULT_EMPRESA_ESTAGIO);

        // Get all the resumoAcademicoList where empresaEstagio does not contain UPDATED_EMPRESA_ESTAGIO
        defaultResumoAcademicoShouldBeFound("empresaEstagio.doesNotContain=" + UPDATED_EMPRESA_ESTAGIO);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where hash equals to DEFAULT_HASH
        defaultResumoAcademicoShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the resumoAcademicoList where hash equals to UPDATED_HASH
        defaultResumoAcademicoShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByHashIsInShouldWork() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultResumoAcademicoShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the resumoAcademicoList where hash equals to UPDATED_HASH
        defaultResumoAcademicoShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where hash is not null
        defaultResumoAcademicoShouldBeFound("hash.specified=true");

        // Get all the resumoAcademicoList where hash is null
        defaultResumoAcademicoShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByHashContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where hash contains DEFAULT_HASH
        defaultResumoAcademicoShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the resumoAcademicoList where hash contains UPDATED_HASH
        defaultResumoAcademicoShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByHashNotContainsSomething() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        // Get all the resumoAcademicoList where hash does not contain DEFAULT_HASH
        defaultResumoAcademicoShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the resumoAcademicoList where hash does not contain UPDATED_HASH
        defaultResumoAcademicoShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            resumoAcademicoRepository.saveAndFlush(resumoAcademico);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        resumoAcademico.setUtilizador(utilizador);
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);
        Long utilizadorId = utilizador.getId();

        // Get all the resumoAcademicoList where utilizador equals to utilizadorId
        defaultResumoAcademicoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the resumoAcademicoList where utilizador equals to (utilizadorId + 1)
        defaultResumoAcademicoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByUltimaTurmaMatriculadaIsEqualToSomething() throws Exception {
        Turma ultimaTurmaMatriculada;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            resumoAcademicoRepository.saveAndFlush(resumoAcademico);
            ultimaTurmaMatriculada = TurmaResourceIT.createEntity(em);
        } else {
            ultimaTurmaMatriculada = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(ultimaTurmaMatriculada);
        em.flush();
        resumoAcademico.setUltimaTurmaMatriculada(ultimaTurmaMatriculada);
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);
        Long ultimaTurmaMatriculadaId = ultimaTurmaMatriculada.getId();

        // Get all the resumoAcademicoList where ultimaTurmaMatriculada equals to ultimaTurmaMatriculadaId
        defaultResumoAcademicoShouldBeFound("ultimaTurmaMatriculadaId.equals=" + ultimaTurmaMatriculadaId);

        // Get all the resumoAcademicoList where ultimaTurmaMatriculada equals to (ultimaTurmaMatriculadaId + 1)
        defaultResumoAcademicoShouldNotBeFound("ultimaTurmaMatriculadaId.equals=" + (ultimaTurmaMatriculadaId + 1));
    }

    @Test
    @Transactional
    void getAllResumoAcademicosByDiscenteIsEqualToSomething() throws Exception {
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            resumoAcademicoRepository.saveAndFlush(resumoAcademico);
            discente = DiscenteResourceIT.createEntity(em);
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        em.persist(discente);
        em.flush();
        resumoAcademico.setDiscente(discente);
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);
        Long discenteId = discente.getId();

        // Get all the resumoAcademicoList where discente equals to discenteId
        defaultResumoAcademicoShouldBeFound("discenteId.equals=" + discenteId);

        // Get all the resumoAcademicoList where discente equals to (discenteId + 1)
        defaultResumoAcademicoShouldNotBeFound("discenteId.equals=" + (discenteId + 1));
    }

    @Test
    @Transactional
    void getAllResumoAcademicosBySituacaoIsEqualToSomething() throws Exception {
        EstadoDisciplinaCurricular situacao;
        if (TestUtil.findAll(em, EstadoDisciplinaCurricular.class).isEmpty()) {
            resumoAcademicoRepository.saveAndFlush(resumoAcademico);
            situacao = EstadoDisciplinaCurricularResourceIT.createEntity(em);
        } else {
            situacao = TestUtil.findAll(em, EstadoDisciplinaCurricular.class).get(0);
        }
        em.persist(situacao);
        em.flush();
        resumoAcademico.setSituacao(situacao);
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);
        Long situacaoId = situacao.getId();

        // Get all the resumoAcademicoList where situacao equals to situacaoId
        defaultResumoAcademicoShouldBeFound("situacaoId.equals=" + situacaoId);

        // Get all the resumoAcademicoList where situacao equals to (situacaoId + 1)
        defaultResumoAcademicoShouldNotBeFound("situacaoId.equals=" + (situacaoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResumoAcademicoShouldBeFound(String filter) throws Exception {
        restResumoAcademicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumoAcademico.getId().intValue())))
            .andExpect(jsonPath("$.[*].temaProjecto").value(hasItem(DEFAULT_TEMA_PROJECTO)))
            .andExpect(jsonPath("$.[*].notaProjecto").value(hasItem(DEFAULT_NOTA_PROJECTO.doubleValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].localEstagio").value(hasItem(DEFAULT_LOCAL_ESTAGIO)))
            .andExpect(jsonPath("$.[*].notaEstagio").value(hasItem(DEFAULT_NOTA_ESTAGIO.doubleValue())))
            .andExpect(jsonPath("$.[*].mediaFinalDisciplina").value(hasItem(DEFAULT_MEDIA_FINAL_DISCIPLINA.doubleValue())))
            .andExpect(jsonPath("$.[*].classificacaoFinal").value(hasItem(DEFAULT_CLASSIFICACAO_FINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].numeroGrupo").value(hasItem(DEFAULT_NUMERO_GRUPO)))
            .andExpect(jsonPath("$.[*].mesaDefesa").value(hasItem(DEFAULT_MESA_DEFESA)))
            .andExpect(jsonPath("$.[*].livroRegistro").value(hasItem(DEFAULT_LIVRO_REGISTRO)))
            .andExpect(jsonPath("$.[*].numeroFolha").value(hasItem(DEFAULT_NUMERO_FOLHA)))
            .andExpect(jsonPath("$.[*].chefeSecretariaPedagogica").value(hasItem(DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA)))
            .andExpect(jsonPath("$.[*].subDirectorPedagogico").value(hasItem(DEFAULT_SUB_DIRECTOR_PEDAGOGICO)))
            .andExpect(jsonPath("$.[*].directorGeral").value(hasItem(DEFAULT_DIRECTOR_GERAL)))
            .andExpect(jsonPath("$.[*].tutorProjecto").value(hasItem(DEFAULT_TUTOR_PROJECTO)))
            .andExpect(jsonPath("$.[*].juriMesa").value(hasItem(DEFAULT_JURI_MESA)))
            .andExpect(jsonPath("$.[*].empresaEstagio").value(hasItem(DEFAULT_EMPRESA_ESTAGIO)))
            .andExpect(jsonPath("$.[*].assinaturaDigitalContentType").value(hasItem(DEFAULT_ASSINATURA_DIGITAL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].assinaturaDigital").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSINATURA_DIGITAL))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)));

        // Check, that the count call also returns 1
        restResumoAcademicoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResumoAcademicoShouldNotBeFound(String filter) throws Exception {
        restResumoAcademicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResumoAcademicoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResumoAcademico() throws Exception {
        // Get the resumoAcademico
        restResumoAcademicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResumoAcademico() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        int databaseSizeBeforeUpdate = resumoAcademicoRepository.findAll().size();

        // Update the resumoAcademico
        ResumoAcademico updatedResumoAcademico = resumoAcademicoRepository.findById(resumoAcademico.getId()).get();
        // Disconnect from session so that the updates on updatedResumoAcademico are not directly saved in db
        em.detach(updatedResumoAcademico);
        updatedResumoAcademico
            .temaProjecto(UPDATED_TEMA_PROJECTO)
            .notaProjecto(UPDATED_NOTA_PROJECTO)
            .observacao(UPDATED_OBSERVACAO)
            .localEstagio(UPDATED_LOCAL_ESTAGIO)
            .notaEstagio(UPDATED_NOTA_ESTAGIO)
            .mediaFinalDisciplina(UPDATED_MEDIA_FINAL_DISCIPLINA)
            .classificacaoFinal(UPDATED_CLASSIFICACAO_FINAL)
            .numeroGrupo(UPDATED_NUMERO_GRUPO)
            .mesaDefesa(UPDATED_MESA_DEFESA)
            .livroRegistro(UPDATED_LIVRO_REGISTRO)
            .numeroFolha(UPDATED_NUMERO_FOLHA)
            .chefeSecretariaPedagogica(UPDATED_CHEFE_SECRETARIA_PEDAGOGICA)
            .subDirectorPedagogico(UPDATED_SUB_DIRECTOR_PEDAGOGICO)
            .directorGeral(UPDATED_DIRECTOR_GERAL)
            .tutorProjecto(UPDATED_TUTOR_PROJECTO)
            .juriMesa(UPDATED_JURI_MESA)
            .empresaEstagio(UPDATED_EMPRESA_ESTAGIO)
            .assinaturaDigital(UPDATED_ASSINATURA_DIGITAL)
            .assinaturaDigitalContentType(UPDATED_ASSINATURA_DIGITAL_CONTENT_TYPE)
            .hash(UPDATED_HASH);
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(updatedResumoAcademico);

        restResumoAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumoAcademicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResumoAcademico in the database
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeUpdate);
        ResumoAcademico testResumoAcademico = resumoAcademicoList.get(resumoAcademicoList.size() - 1);
        assertThat(testResumoAcademico.getTemaProjecto()).isEqualTo(UPDATED_TEMA_PROJECTO);
        assertThat(testResumoAcademico.getNotaProjecto()).isEqualTo(UPDATED_NOTA_PROJECTO);
        assertThat(testResumoAcademico.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testResumoAcademico.getLocalEstagio()).isEqualTo(UPDATED_LOCAL_ESTAGIO);
        assertThat(testResumoAcademico.getNotaEstagio()).isEqualTo(UPDATED_NOTA_ESTAGIO);
        assertThat(testResumoAcademico.getMediaFinalDisciplina()).isEqualTo(UPDATED_MEDIA_FINAL_DISCIPLINA);
        assertThat(testResumoAcademico.getClassificacaoFinal()).isEqualTo(UPDATED_CLASSIFICACAO_FINAL);
        assertThat(testResumoAcademico.getNumeroGrupo()).isEqualTo(UPDATED_NUMERO_GRUPO);
        assertThat(testResumoAcademico.getMesaDefesa()).isEqualTo(UPDATED_MESA_DEFESA);
        assertThat(testResumoAcademico.getLivroRegistro()).isEqualTo(UPDATED_LIVRO_REGISTRO);
        assertThat(testResumoAcademico.getNumeroFolha()).isEqualTo(UPDATED_NUMERO_FOLHA);
        assertThat(testResumoAcademico.getChefeSecretariaPedagogica()).isEqualTo(UPDATED_CHEFE_SECRETARIA_PEDAGOGICA);
        assertThat(testResumoAcademico.getSubDirectorPedagogico()).isEqualTo(UPDATED_SUB_DIRECTOR_PEDAGOGICO);
        assertThat(testResumoAcademico.getDirectorGeral()).isEqualTo(UPDATED_DIRECTOR_GERAL);
        assertThat(testResumoAcademico.getTutorProjecto()).isEqualTo(UPDATED_TUTOR_PROJECTO);
        assertThat(testResumoAcademico.getJuriMesa()).isEqualTo(UPDATED_JURI_MESA);
        assertThat(testResumoAcademico.getEmpresaEstagio()).isEqualTo(UPDATED_EMPRESA_ESTAGIO);
        assertThat(testResumoAcademico.getAssinaturaDigital()).isEqualTo(UPDATED_ASSINATURA_DIGITAL);
        assertThat(testResumoAcademico.getAssinaturaDigitalContentType()).isEqualTo(UPDATED_ASSINATURA_DIGITAL_CONTENT_TYPE);
        assertThat(testResumoAcademico.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    void putNonExistingResumoAcademico() throws Exception {
        int databaseSizeBeforeUpdate = resumoAcademicoRepository.findAll().size();
        resumoAcademico.setId(count.incrementAndGet());

        // Create the ResumoAcademico
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumoAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumoAcademicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumoAcademico in the database
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResumoAcademico() throws Exception {
        int databaseSizeBeforeUpdate = resumoAcademicoRepository.findAll().size();
        resumoAcademico.setId(count.incrementAndGet());

        // Create the ResumoAcademico
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumoAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumoAcademico in the database
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResumoAcademico() throws Exception {
        int databaseSizeBeforeUpdate = resumoAcademicoRepository.findAll().size();
        resumoAcademico.setId(count.incrementAndGet());

        // Create the ResumoAcademico
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumoAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumoAcademico in the database
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResumoAcademicoWithPatch() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        int databaseSizeBeforeUpdate = resumoAcademicoRepository.findAll().size();

        // Update the resumoAcademico using partial update
        ResumoAcademico partialUpdatedResumoAcademico = new ResumoAcademico();
        partialUpdatedResumoAcademico.setId(resumoAcademico.getId());

        partialUpdatedResumoAcademico
            .temaProjecto(UPDATED_TEMA_PROJECTO)
            .observacao(UPDATED_OBSERVACAO)
            .classificacaoFinal(UPDATED_CLASSIFICACAO_FINAL)
            .numeroFolha(UPDATED_NUMERO_FOLHA)
            .directorGeral(UPDATED_DIRECTOR_GERAL)
            .assinaturaDigital(UPDATED_ASSINATURA_DIGITAL)
            .assinaturaDigitalContentType(UPDATED_ASSINATURA_DIGITAL_CONTENT_TYPE)
            .hash(UPDATED_HASH);

        restResumoAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumoAcademico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumoAcademico))
            )
            .andExpect(status().isOk());

        // Validate the ResumoAcademico in the database
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeUpdate);
        ResumoAcademico testResumoAcademico = resumoAcademicoList.get(resumoAcademicoList.size() - 1);
        assertThat(testResumoAcademico.getTemaProjecto()).isEqualTo(UPDATED_TEMA_PROJECTO);
        assertThat(testResumoAcademico.getNotaProjecto()).isEqualTo(DEFAULT_NOTA_PROJECTO);
        assertThat(testResumoAcademico.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testResumoAcademico.getLocalEstagio()).isEqualTo(DEFAULT_LOCAL_ESTAGIO);
        assertThat(testResumoAcademico.getNotaEstagio()).isEqualTo(DEFAULT_NOTA_ESTAGIO);
        assertThat(testResumoAcademico.getMediaFinalDisciplina()).isEqualTo(DEFAULT_MEDIA_FINAL_DISCIPLINA);
        assertThat(testResumoAcademico.getClassificacaoFinal()).isEqualTo(UPDATED_CLASSIFICACAO_FINAL);
        assertThat(testResumoAcademico.getNumeroGrupo()).isEqualTo(DEFAULT_NUMERO_GRUPO);
        assertThat(testResumoAcademico.getMesaDefesa()).isEqualTo(DEFAULT_MESA_DEFESA);
        assertThat(testResumoAcademico.getLivroRegistro()).isEqualTo(DEFAULT_LIVRO_REGISTRO);
        assertThat(testResumoAcademico.getNumeroFolha()).isEqualTo(UPDATED_NUMERO_FOLHA);
        assertThat(testResumoAcademico.getChefeSecretariaPedagogica()).isEqualTo(DEFAULT_CHEFE_SECRETARIA_PEDAGOGICA);
        assertThat(testResumoAcademico.getSubDirectorPedagogico()).isEqualTo(DEFAULT_SUB_DIRECTOR_PEDAGOGICO);
        assertThat(testResumoAcademico.getDirectorGeral()).isEqualTo(UPDATED_DIRECTOR_GERAL);
        assertThat(testResumoAcademico.getTutorProjecto()).isEqualTo(DEFAULT_TUTOR_PROJECTO);
        assertThat(testResumoAcademico.getJuriMesa()).isEqualTo(DEFAULT_JURI_MESA);
        assertThat(testResumoAcademico.getEmpresaEstagio()).isEqualTo(DEFAULT_EMPRESA_ESTAGIO);
        assertThat(testResumoAcademico.getAssinaturaDigital()).isEqualTo(UPDATED_ASSINATURA_DIGITAL);
        assertThat(testResumoAcademico.getAssinaturaDigitalContentType()).isEqualTo(UPDATED_ASSINATURA_DIGITAL_CONTENT_TYPE);
        assertThat(testResumoAcademico.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    void fullUpdateResumoAcademicoWithPatch() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        int databaseSizeBeforeUpdate = resumoAcademicoRepository.findAll().size();

        // Update the resumoAcademico using partial update
        ResumoAcademico partialUpdatedResumoAcademico = new ResumoAcademico();
        partialUpdatedResumoAcademico.setId(resumoAcademico.getId());

        partialUpdatedResumoAcademico
            .temaProjecto(UPDATED_TEMA_PROJECTO)
            .notaProjecto(UPDATED_NOTA_PROJECTO)
            .observacao(UPDATED_OBSERVACAO)
            .localEstagio(UPDATED_LOCAL_ESTAGIO)
            .notaEstagio(UPDATED_NOTA_ESTAGIO)
            .mediaFinalDisciplina(UPDATED_MEDIA_FINAL_DISCIPLINA)
            .classificacaoFinal(UPDATED_CLASSIFICACAO_FINAL)
            .numeroGrupo(UPDATED_NUMERO_GRUPO)
            .mesaDefesa(UPDATED_MESA_DEFESA)
            .livroRegistro(UPDATED_LIVRO_REGISTRO)
            .numeroFolha(UPDATED_NUMERO_FOLHA)
            .chefeSecretariaPedagogica(UPDATED_CHEFE_SECRETARIA_PEDAGOGICA)
            .subDirectorPedagogico(UPDATED_SUB_DIRECTOR_PEDAGOGICO)
            .directorGeral(UPDATED_DIRECTOR_GERAL)
            .tutorProjecto(UPDATED_TUTOR_PROJECTO)
            .juriMesa(UPDATED_JURI_MESA)
            .empresaEstagio(UPDATED_EMPRESA_ESTAGIO)
            .assinaturaDigital(UPDATED_ASSINATURA_DIGITAL)
            .assinaturaDigitalContentType(UPDATED_ASSINATURA_DIGITAL_CONTENT_TYPE)
            .hash(UPDATED_HASH);

        restResumoAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumoAcademico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumoAcademico))
            )
            .andExpect(status().isOk());

        // Validate the ResumoAcademico in the database
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeUpdate);
        ResumoAcademico testResumoAcademico = resumoAcademicoList.get(resumoAcademicoList.size() - 1);
        assertThat(testResumoAcademico.getTemaProjecto()).isEqualTo(UPDATED_TEMA_PROJECTO);
        assertThat(testResumoAcademico.getNotaProjecto()).isEqualTo(UPDATED_NOTA_PROJECTO);
        assertThat(testResumoAcademico.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testResumoAcademico.getLocalEstagio()).isEqualTo(UPDATED_LOCAL_ESTAGIO);
        assertThat(testResumoAcademico.getNotaEstagio()).isEqualTo(UPDATED_NOTA_ESTAGIO);
        assertThat(testResumoAcademico.getMediaFinalDisciplina()).isEqualTo(UPDATED_MEDIA_FINAL_DISCIPLINA);
        assertThat(testResumoAcademico.getClassificacaoFinal()).isEqualTo(UPDATED_CLASSIFICACAO_FINAL);
        assertThat(testResumoAcademico.getNumeroGrupo()).isEqualTo(UPDATED_NUMERO_GRUPO);
        assertThat(testResumoAcademico.getMesaDefesa()).isEqualTo(UPDATED_MESA_DEFESA);
        assertThat(testResumoAcademico.getLivroRegistro()).isEqualTo(UPDATED_LIVRO_REGISTRO);
        assertThat(testResumoAcademico.getNumeroFolha()).isEqualTo(UPDATED_NUMERO_FOLHA);
        assertThat(testResumoAcademico.getChefeSecretariaPedagogica()).isEqualTo(UPDATED_CHEFE_SECRETARIA_PEDAGOGICA);
        assertThat(testResumoAcademico.getSubDirectorPedagogico()).isEqualTo(UPDATED_SUB_DIRECTOR_PEDAGOGICO);
        assertThat(testResumoAcademico.getDirectorGeral()).isEqualTo(UPDATED_DIRECTOR_GERAL);
        assertThat(testResumoAcademico.getTutorProjecto()).isEqualTo(UPDATED_TUTOR_PROJECTO);
        assertThat(testResumoAcademico.getJuriMesa()).isEqualTo(UPDATED_JURI_MESA);
        assertThat(testResumoAcademico.getEmpresaEstagio()).isEqualTo(UPDATED_EMPRESA_ESTAGIO);
        assertThat(testResumoAcademico.getAssinaturaDigital()).isEqualTo(UPDATED_ASSINATURA_DIGITAL);
        assertThat(testResumoAcademico.getAssinaturaDigitalContentType()).isEqualTo(UPDATED_ASSINATURA_DIGITAL_CONTENT_TYPE);
        assertThat(testResumoAcademico.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    void patchNonExistingResumoAcademico() throws Exception {
        int databaseSizeBeforeUpdate = resumoAcademicoRepository.findAll().size();
        resumoAcademico.setId(count.incrementAndGet());

        // Create the ResumoAcademico
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumoAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resumoAcademicoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumoAcademico in the database
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResumoAcademico() throws Exception {
        int databaseSizeBeforeUpdate = resumoAcademicoRepository.findAll().size();
        resumoAcademico.setId(count.incrementAndGet());

        // Create the ResumoAcademico
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumoAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumoAcademico in the database
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResumoAcademico() throws Exception {
        int databaseSizeBeforeUpdate = resumoAcademicoRepository.findAll().size();
        resumoAcademico.setId(count.incrementAndGet());

        // Create the ResumoAcademico
        ResumoAcademicoDTO resumoAcademicoDTO = resumoAcademicoMapper.toDto(resumoAcademico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumoAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumoAcademicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumoAcademico in the database
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResumoAcademico() throws Exception {
        // Initialize the database
        resumoAcademicoRepository.saveAndFlush(resumoAcademico);

        int databaseSizeBeforeDelete = resumoAcademicoRepository.findAll().size();

        // Delete the resumoAcademico
        restResumoAcademicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, resumoAcademico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResumoAcademico> resumoAcademicoList = resumoAcademicoRepository.findAll();
        assertThat(resumoAcademicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
