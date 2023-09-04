package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.CategoriaOcorrencia;
import com.ravunana.longonkelo.domain.DissertacaoFinalCurso;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.FormacaoDocente;
import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.NotasGeralDisciplina;
import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.domain.Ocorrencia;
import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.domain.ResponsavelAreaFormacao;
import com.ravunana.longonkelo.domain.ResponsavelCurso;
import com.ravunana.longonkelo.domain.ResponsavelDisciplina;
import com.ravunana.longonkelo.domain.ResponsavelTurma;
import com.ravunana.longonkelo.domain.ResponsavelTurno;
import com.ravunana.longonkelo.domain.enumeration.Sexo;
import com.ravunana.longonkelo.repository.DocenteRepository;
import com.ravunana.longonkelo.service.DocenteService;
import com.ravunana.longonkelo.service.criteria.DocenteCriteria;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.mapper.DocenteMapper;
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
 * Integration tests for the {@link DocenteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DocenteResourceIT {

    private static final byte[] DEFAULT_FOTOGRAFIA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTOGRAFIA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTOGRAFIA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTOGRAFIA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_NASCIMENTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    private static final String DEFAULT_INSS = "AAAAAAAAAA";
    private static final String UPDATED_INSS = "BBBBBBBBBB";

    private static final Sexo DEFAULT_SEXO = Sexo.MASCULINO;
    private static final Sexo UPDATED_SEXO = Sexo.FEMENINO;

    private static final String DEFAULT_PAI = "AAAAAAAAAA";
    private static final String UPDATED_PAI = "BBBBBBBBBB";

    private static final String DEFAULT_MAE = "AAAAAAAAAA";
    private static final String UPDATED_MAE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO_NUMERO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOCUMENTO_EMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENTO_EMISSAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOCUMENTO_EMISSAO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DOCUMENTO_VALIDADE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENTO_VALIDADE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOCUMENTO_VALIDADE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_RESIDENCIA = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCIA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_INICIO_FUNCOES = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO_FUNCOES = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_INICIO_FUNCOES = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TELEFONE_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_PARENTE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_PARENTE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_AGENTE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_AGENTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TEM_AGREGACAO_PEDAGOGICA = false;
    private static final Boolean UPDATED_TEM_AGREGACAO_PEDAGOGICA = true;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/docentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocenteRepository docenteRepository;

    @Mock
    private DocenteRepository docenteRepositoryMock;

    @Autowired
    private DocenteMapper docenteMapper;

    @Mock
    private DocenteService docenteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocenteMockMvc;

    private Docente docente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Docente createEntity(EntityManager em) {
        Docente docente = new Docente()
            .fotografia(DEFAULT_FOTOGRAFIA)
            .fotografiaContentType(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)
            .nome(DEFAULT_NOME)
            .nascimento(DEFAULT_NASCIMENTO)
            .nif(DEFAULT_NIF)
            .inss(DEFAULT_INSS)
            .sexo(DEFAULT_SEXO)
            .pai(DEFAULT_PAI)
            .mae(DEFAULT_MAE)
            .documentoNumero(DEFAULT_DOCUMENTO_NUMERO)
            .documentoEmissao(DEFAULT_DOCUMENTO_EMISSAO)
            .documentoValidade(DEFAULT_DOCUMENTO_VALIDADE)
            .residencia(DEFAULT_RESIDENCIA)
            .dataInicioFuncoes(DEFAULT_DATA_INICIO_FUNCOES)
            .telefonePrincipal(DEFAULT_TELEFONE_PRINCIPAL)
            .telefoneParente(DEFAULT_TELEFONE_PARENTE)
            .email(DEFAULT_EMAIL)
            .numeroAgente(DEFAULT_NUMERO_AGENTE)
            .temAgregacaoPedagogica(DEFAULT_TEM_AGREGACAO_PEDAGOGICA)
            .observacao(DEFAULT_OBSERVACAO)
            .hash(DEFAULT_HASH)
            .timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        docente.setNacionalidade(lookupItem);
        // Add required entity
        docente.setNaturalidade(lookupItem);
        // Add required entity
        docente.setTipoDocumento(lookupItem);
        // Add required entity
        docente.setGrauAcademico(lookupItem);
        // Add required entity
        docente.setEstadoCivil(lookupItem);
        return docente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Docente createUpdatedEntity(EntityManager em) {
        Docente docente = new Docente()
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .nascimento(UPDATED_NASCIMENTO)
            .nif(UPDATED_NIF)
            .inss(UPDATED_INSS)
            .sexo(UPDATED_SEXO)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO)
            .documentoEmissao(UPDATED_DOCUMENTO_EMISSAO)
            .documentoValidade(UPDATED_DOCUMENTO_VALIDADE)
            .residencia(UPDATED_RESIDENCIA)
            .dataInicioFuncoes(UPDATED_DATA_INICIO_FUNCOES)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneParente(UPDATED_TELEFONE_PARENTE)
            .email(UPDATED_EMAIL)
            .numeroAgente(UPDATED_NUMERO_AGENTE)
            .temAgregacaoPedagogica(UPDATED_TEM_AGREGACAO_PEDAGOGICA)
            .observacao(UPDATED_OBSERVACAO)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createUpdatedEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        docente.setNacionalidade(lookupItem);
        // Add required entity
        docente.setNaturalidade(lookupItem);
        // Add required entity
        docente.setTipoDocumento(lookupItem);
        // Add required entity
        docente.setGrauAcademico(lookupItem);
        // Add required entity
        docente.setEstadoCivil(lookupItem);
        return docente;
    }

    @BeforeEach
    public void initTest() {
        docente = createEntity(em);
    }

    @Test
    @Transactional
    void createDocente() throws Exception {
        int databaseSizeBeforeCreate = docenteRepository.findAll().size();
        // Create the Docente
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);
        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isCreated());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeCreate + 1);
        Docente testDocente = docenteList.get(docenteList.size() - 1);
        assertThat(testDocente.getFotografia()).isEqualTo(DEFAULT_FOTOGRAFIA);
        assertThat(testDocente.getFotografiaContentType()).isEqualTo(DEFAULT_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testDocente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDocente.getNascimento()).isEqualTo(DEFAULT_NASCIMENTO);
        assertThat(testDocente.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testDocente.getInss()).isEqualTo(DEFAULT_INSS);
        assertThat(testDocente.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testDocente.getPai()).isEqualTo(DEFAULT_PAI);
        assertThat(testDocente.getMae()).isEqualTo(DEFAULT_MAE);
        assertThat(testDocente.getDocumentoNumero()).isEqualTo(DEFAULT_DOCUMENTO_NUMERO);
        assertThat(testDocente.getDocumentoEmissao()).isEqualTo(DEFAULT_DOCUMENTO_EMISSAO);
        assertThat(testDocente.getDocumentoValidade()).isEqualTo(DEFAULT_DOCUMENTO_VALIDADE);
        assertThat(testDocente.getResidencia()).isEqualTo(DEFAULT_RESIDENCIA);
        assertThat(testDocente.getDataInicioFuncoes()).isEqualTo(DEFAULT_DATA_INICIO_FUNCOES);
        assertThat(testDocente.getTelefonePrincipal()).isEqualTo(DEFAULT_TELEFONE_PRINCIPAL);
        assertThat(testDocente.getTelefoneParente()).isEqualTo(DEFAULT_TELEFONE_PARENTE);
        assertThat(testDocente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDocente.getNumeroAgente()).isEqualTo(DEFAULT_NUMERO_AGENTE);
        assertThat(testDocente.getTemAgregacaoPedagogica()).isEqualTo(DEFAULT_TEM_AGREGACAO_PEDAGOGICA);
        assertThat(testDocente.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testDocente.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testDocente.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createDocenteWithExistingId() throws Exception {
        // Create the Docente with an existing ID
        docente.setId(1L);
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        int databaseSizeBeforeCreate = docenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = docenteRepository.findAll().size();
        // set the field null
        docente.setNome(null);

        // Create the Docente, which fails.
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = docenteRepository.findAll().size();
        // set the field null
        docente.setNascimento(null);

        // Create the Docente, which fails.
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = docenteRepository.findAll().size();
        // set the field null
        docente.setSexo(null);

        // Create the Docente, which fails.
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = docenteRepository.findAll().size();
        // set the field null
        docente.setPai(null);

        // Create the Docente, which fails.
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaeIsRequired() throws Exception {
        int databaseSizeBeforeTest = docenteRepository.findAll().size();
        // set the field null
        docente.setMae(null);

        // Create the Docente, which fails.
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentoNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = docenteRepository.findAll().size();
        // set the field null
        docente.setDocumentoNumero(null);

        // Create the Docente, which fails.
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentoEmissaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = docenteRepository.findAll().size();
        // set the field null
        docente.setDocumentoEmissao(null);

        // Create the Docente, which fails.
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentoValidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = docenteRepository.findAll().size();
        // set the field null
        docente.setDocumentoValidade(null);

        // Create the Docente, which fails.
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkResidenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = docenteRepository.findAll().size();
        // set the field null
        docente.setResidencia(null);

        // Create the Docente, which fails.
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataInicioFuncoesIsRequired() throws Exception {
        int databaseSizeBeforeTest = docenteRepository.findAll().size();
        // set the field null
        docente.setDataInicioFuncoes(null);

        // Create the Docente, which fails.
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonePrincipalIsRequired() throws Exception {
        int databaseSizeBeforeTest = docenteRepository.findAll().size();
        // set the field null
        docente.setTelefonePrincipal(null);

        // Create the Docente, which fails.
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        restDocenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isBadRequest());

        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDocentes() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList
        restDocenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docente.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotografiaContentType").value(hasItem(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA))))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nascimento").value(hasItem(DEFAULT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].inss").value(hasItem(DEFAULT_INSS)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].pai").value(hasItem(DEFAULT_PAI)))
            .andExpect(jsonPath("$.[*].mae").value(hasItem(DEFAULT_MAE)))
            .andExpect(jsonPath("$.[*].documentoNumero").value(hasItem(DEFAULT_DOCUMENTO_NUMERO)))
            .andExpect(jsonPath("$.[*].documentoEmissao").value(hasItem(DEFAULT_DOCUMENTO_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].documentoValidade").value(hasItem(DEFAULT_DOCUMENTO_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].residencia").value(hasItem(DEFAULT_RESIDENCIA)))
            .andExpect(jsonPath("$.[*].dataInicioFuncoes").value(hasItem(DEFAULT_DATA_INICIO_FUNCOES.toString())))
            .andExpect(jsonPath("$.[*].telefonePrincipal").value(hasItem(DEFAULT_TELEFONE_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].telefoneParente").value(hasItem(DEFAULT_TELEFONE_PARENTE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].numeroAgente").value(hasItem(DEFAULT_NUMERO_AGENTE)))
            .andExpect(jsonPath("$.[*].temAgregacaoPedagogica").value(hasItem(DEFAULT_TEM_AGREGACAO_PEDAGOGICA.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocentesWithEagerRelationshipsIsEnabled() throws Exception {
        when(docenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(docenteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(docenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(docenteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDocente() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get the docente
        restDocenteMockMvc
            .perform(get(ENTITY_API_URL_ID, docente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(docente.getId().intValue()))
            .andExpect(jsonPath("$.fotografiaContentType").value(DEFAULT_FOTOGRAFIA_CONTENT_TYPE))
            .andExpect(jsonPath("$.fotografia").value(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA)))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.nascimento").value(DEFAULT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.inss").value(DEFAULT_INSS))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.pai").value(DEFAULT_PAI))
            .andExpect(jsonPath("$.mae").value(DEFAULT_MAE))
            .andExpect(jsonPath("$.documentoNumero").value(DEFAULT_DOCUMENTO_NUMERO))
            .andExpect(jsonPath("$.documentoEmissao").value(DEFAULT_DOCUMENTO_EMISSAO.toString()))
            .andExpect(jsonPath("$.documentoValidade").value(DEFAULT_DOCUMENTO_VALIDADE.toString()))
            .andExpect(jsonPath("$.residencia").value(DEFAULT_RESIDENCIA))
            .andExpect(jsonPath("$.dataInicioFuncoes").value(DEFAULT_DATA_INICIO_FUNCOES.toString()))
            .andExpect(jsonPath("$.telefonePrincipal").value(DEFAULT_TELEFONE_PRINCIPAL))
            .andExpect(jsonPath("$.telefoneParente").value(DEFAULT_TELEFONE_PARENTE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.numeroAgente").value(DEFAULT_NUMERO_AGENTE))
            .andExpect(jsonPath("$.temAgregacaoPedagogica").value(DEFAULT_TEM_AGREGACAO_PEDAGOGICA.booleanValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getDocentesByIdFiltering() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        Long id = docente.getId();

        defaultDocenteShouldBeFound("id.equals=" + id);
        defaultDocenteShouldNotBeFound("id.notEquals=" + id);

        defaultDocenteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocenteShouldNotBeFound("id.greaterThan=" + id);

        defaultDocenteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocenteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocentesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nome equals to DEFAULT_NOME
        defaultDocenteShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the docenteList where nome equals to UPDATED_NOME
        defaultDocenteShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDocentesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultDocenteShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the docenteList where nome equals to UPDATED_NOME
        defaultDocenteShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDocentesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nome is not null
        defaultDocenteShouldBeFound("nome.specified=true");

        // Get all the docenteList where nome is null
        defaultDocenteShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByNomeContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nome contains DEFAULT_NOME
        defaultDocenteShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the docenteList where nome contains UPDATED_NOME
        defaultDocenteShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDocentesByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nome does not contain DEFAULT_NOME
        defaultDocenteShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the docenteList where nome does not contain UPDATED_NOME
        defaultDocenteShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDocentesByNascimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nascimento equals to DEFAULT_NASCIMENTO
        defaultDocenteShouldBeFound("nascimento.equals=" + DEFAULT_NASCIMENTO);

        // Get all the docenteList where nascimento equals to UPDATED_NASCIMENTO
        defaultDocenteShouldNotBeFound("nascimento.equals=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDocentesByNascimentoIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nascimento in DEFAULT_NASCIMENTO or UPDATED_NASCIMENTO
        defaultDocenteShouldBeFound("nascimento.in=" + DEFAULT_NASCIMENTO + "," + UPDATED_NASCIMENTO);

        // Get all the docenteList where nascimento equals to UPDATED_NASCIMENTO
        defaultDocenteShouldNotBeFound("nascimento.in=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDocentesByNascimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nascimento is not null
        defaultDocenteShouldBeFound("nascimento.specified=true");

        // Get all the docenteList where nascimento is null
        defaultDocenteShouldNotBeFound("nascimento.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByNascimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nascimento is greater than or equal to DEFAULT_NASCIMENTO
        defaultDocenteShouldBeFound("nascimento.greaterThanOrEqual=" + DEFAULT_NASCIMENTO);

        // Get all the docenteList where nascimento is greater than or equal to UPDATED_NASCIMENTO
        defaultDocenteShouldNotBeFound("nascimento.greaterThanOrEqual=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDocentesByNascimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nascimento is less than or equal to DEFAULT_NASCIMENTO
        defaultDocenteShouldBeFound("nascimento.lessThanOrEqual=" + DEFAULT_NASCIMENTO);

        // Get all the docenteList where nascimento is less than or equal to SMALLER_NASCIMENTO
        defaultDocenteShouldNotBeFound("nascimento.lessThanOrEqual=" + SMALLER_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDocentesByNascimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nascimento is less than DEFAULT_NASCIMENTO
        defaultDocenteShouldNotBeFound("nascimento.lessThan=" + DEFAULT_NASCIMENTO);

        // Get all the docenteList where nascimento is less than UPDATED_NASCIMENTO
        defaultDocenteShouldBeFound("nascimento.lessThan=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDocentesByNascimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nascimento is greater than DEFAULT_NASCIMENTO
        defaultDocenteShouldNotBeFound("nascimento.greaterThan=" + DEFAULT_NASCIMENTO);

        // Get all the docenteList where nascimento is greater than SMALLER_NASCIMENTO
        defaultDocenteShouldBeFound("nascimento.greaterThan=" + SMALLER_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDocentesByNifIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nif equals to DEFAULT_NIF
        defaultDocenteShouldBeFound("nif.equals=" + DEFAULT_NIF);

        // Get all the docenteList where nif equals to UPDATED_NIF
        defaultDocenteShouldNotBeFound("nif.equals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDocentesByNifIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nif in DEFAULT_NIF or UPDATED_NIF
        defaultDocenteShouldBeFound("nif.in=" + DEFAULT_NIF + "," + UPDATED_NIF);

        // Get all the docenteList where nif equals to UPDATED_NIF
        defaultDocenteShouldNotBeFound("nif.in=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDocentesByNifIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nif is not null
        defaultDocenteShouldBeFound("nif.specified=true");

        // Get all the docenteList where nif is null
        defaultDocenteShouldNotBeFound("nif.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByNifContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nif contains DEFAULT_NIF
        defaultDocenteShouldBeFound("nif.contains=" + DEFAULT_NIF);

        // Get all the docenteList where nif contains UPDATED_NIF
        defaultDocenteShouldNotBeFound("nif.contains=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDocentesByNifNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where nif does not contain DEFAULT_NIF
        defaultDocenteShouldNotBeFound("nif.doesNotContain=" + DEFAULT_NIF);

        // Get all the docenteList where nif does not contain UPDATED_NIF
        defaultDocenteShouldBeFound("nif.doesNotContain=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDocentesByInssIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where inss equals to DEFAULT_INSS
        defaultDocenteShouldBeFound("inss.equals=" + DEFAULT_INSS);

        // Get all the docenteList where inss equals to UPDATED_INSS
        defaultDocenteShouldNotBeFound("inss.equals=" + UPDATED_INSS);
    }

    @Test
    @Transactional
    void getAllDocentesByInssIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where inss in DEFAULT_INSS or UPDATED_INSS
        defaultDocenteShouldBeFound("inss.in=" + DEFAULT_INSS + "," + UPDATED_INSS);

        // Get all the docenteList where inss equals to UPDATED_INSS
        defaultDocenteShouldNotBeFound("inss.in=" + UPDATED_INSS);
    }

    @Test
    @Transactional
    void getAllDocentesByInssIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where inss is not null
        defaultDocenteShouldBeFound("inss.specified=true");

        // Get all the docenteList where inss is null
        defaultDocenteShouldNotBeFound("inss.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByInssContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where inss contains DEFAULT_INSS
        defaultDocenteShouldBeFound("inss.contains=" + DEFAULT_INSS);

        // Get all the docenteList where inss contains UPDATED_INSS
        defaultDocenteShouldNotBeFound("inss.contains=" + UPDATED_INSS);
    }

    @Test
    @Transactional
    void getAllDocentesByInssNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where inss does not contain DEFAULT_INSS
        defaultDocenteShouldNotBeFound("inss.doesNotContain=" + DEFAULT_INSS);

        // Get all the docenteList where inss does not contain UPDATED_INSS
        defaultDocenteShouldBeFound("inss.doesNotContain=" + UPDATED_INSS);
    }

    @Test
    @Transactional
    void getAllDocentesBySexoIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where sexo equals to DEFAULT_SEXO
        defaultDocenteShouldBeFound("sexo.equals=" + DEFAULT_SEXO);

        // Get all the docenteList where sexo equals to UPDATED_SEXO
        defaultDocenteShouldNotBeFound("sexo.equals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllDocentesBySexoIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where sexo in DEFAULT_SEXO or UPDATED_SEXO
        defaultDocenteShouldBeFound("sexo.in=" + DEFAULT_SEXO + "," + UPDATED_SEXO);

        // Get all the docenteList where sexo equals to UPDATED_SEXO
        defaultDocenteShouldNotBeFound("sexo.in=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllDocentesBySexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where sexo is not null
        defaultDocenteShouldBeFound("sexo.specified=true");

        // Get all the docenteList where sexo is null
        defaultDocenteShouldNotBeFound("sexo.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByPaiIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where pai equals to DEFAULT_PAI
        defaultDocenteShouldBeFound("pai.equals=" + DEFAULT_PAI);

        // Get all the docenteList where pai equals to UPDATED_PAI
        defaultDocenteShouldNotBeFound("pai.equals=" + UPDATED_PAI);
    }

    @Test
    @Transactional
    void getAllDocentesByPaiIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where pai in DEFAULT_PAI or UPDATED_PAI
        defaultDocenteShouldBeFound("pai.in=" + DEFAULT_PAI + "," + UPDATED_PAI);

        // Get all the docenteList where pai equals to UPDATED_PAI
        defaultDocenteShouldNotBeFound("pai.in=" + UPDATED_PAI);
    }

    @Test
    @Transactional
    void getAllDocentesByPaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where pai is not null
        defaultDocenteShouldBeFound("pai.specified=true");

        // Get all the docenteList where pai is null
        defaultDocenteShouldNotBeFound("pai.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByPaiContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where pai contains DEFAULT_PAI
        defaultDocenteShouldBeFound("pai.contains=" + DEFAULT_PAI);

        // Get all the docenteList where pai contains UPDATED_PAI
        defaultDocenteShouldNotBeFound("pai.contains=" + UPDATED_PAI);
    }

    @Test
    @Transactional
    void getAllDocentesByPaiNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where pai does not contain DEFAULT_PAI
        defaultDocenteShouldNotBeFound("pai.doesNotContain=" + DEFAULT_PAI);

        // Get all the docenteList where pai does not contain UPDATED_PAI
        defaultDocenteShouldBeFound("pai.doesNotContain=" + UPDATED_PAI);
    }

    @Test
    @Transactional
    void getAllDocentesByMaeIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where mae equals to DEFAULT_MAE
        defaultDocenteShouldBeFound("mae.equals=" + DEFAULT_MAE);

        // Get all the docenteList where mae equals to UPDATED_MAE
        defaultDocenteShouldNotBeFound("mae.equals=" + UPDATED_MAE);
    }

    @Test
    @Transactional
    void getAllDocentesByMaeIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where mae in DEFAULT_MAE or UPDATED_MAE
        defaultDocenteShouldBeFound("mae.in=" + DEFAULT_MAE + "," + UPDATED_MAE);

        // Get all the docenteList where mae equals to UPDATED_MAE
        defaultDocenteShouldNotBeFound("mae.in=" + UPDATED_MAE);
    }

    @Test
    @Transactional
    void getAllDocentesByMaeIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where mae is not null
        defaultDocenteShouldBeFound("mae.specified=true");

        // Get all the docenteList where mae is null
        defaultDocenteShouldNotBeFound("mae.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByMaeContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where mae contains DEFAULT_MAE
        defaultDocenteShouldBeFound("mae.contains=" + DEFAULT_MAE);

        // Get all the docenteList where mae contains UPDATED_MAE
        defaultDocenteShouldNotBeFound("mae.contains=" + UPDATED_MAE);
    }

    @Test
    @Transactional
    void getAllDocentesByMaeNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where mae does not contain DEFAULT_MAE
        defaultDocenteShouldNotBeFound("mae.doesNotContain=" + DEFAULT_MAE);

        // Get all the docenteList where mae does not contain UPDATED_MAE
        defaultDocenteShouldBeFound("mae.doesNotContain=" + UPDATED_MAE);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoNumero equals to DEFAULT_DOCUMENTO_NUMERO
        defaultDocenteShouldBeFound("documentoNumero.equals=" + DEFAULT_DOCUMENTO_NUMERO);

        // Get all the docenteList where documentoNumero equals to UPDATED_DOCUMENTO_NUMERO
        defaultDocenteShouldNotBeFound("documentoNumero.equals=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoNumero in DEFAULT_DOCUMENTO_NUMERO or UPDATED_DOCUMENTO_NUMERO
        defaultDocenteShouldBeFound("documentoNumero.in=" + DEFAULT_DOCUMENTO_NUMERO + "," + UPDATED_DOCUMENTO_NUMERO);

        // Get all the docenteList where documentoNumero equals to UPDATED_DOCUMENTO_NUMERO
        defaultDocenteShouldNotBeFound("documentoNumero.in=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoNumero is not null
        defaultDocenteShouldBeFound("documentoNumero.specified=true");

        // Get all the docenteList where documentoNumero is null
        defaultDocenteShouldNotBeFound("documentoNumero.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoNumeroContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoNumero contains DEFAULT_DOCUMENTO_NUMERO
        defaultDocenteShouldBeFound("documentoNumero.contains=" + DEFAULT_DOCUMENTO_NUMERO);

        // Get all the docenteList where documentoNumero contains UPDATED_DOCUMENTO_NUMERO
        defaultDocenteShouldNotBeFound("documentoNumero.contains=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoNumero does not contain DEFAULT_DOCUMENTO_NUMERO
        defaultDocenteShouldNotBeFound("documentoNumero.doesNotContain=" + DEFAULT_DOCUMENTO_NUMERO);

        // Get all the docenteList where documentoNumero does not contain UPDATED_DOCUMENTO_NUMERO
        defaultDocenteShouldBeFound("documentoNumero.doesNotContain=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoEmissaoIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoEmissao equals to DEFAULT_DOCUMENTO_EMISSAO
        defaultDocenteShouldBeFound("documentoEmissao.equals=" + DEFAULT_DOCUMENTO_EMISSAO);

        // Get all the docenteList where documentoEmissao equals to UPDATED_DOCUMENTO_EMISSAO
        defaultDocenteShouldNotBeFound("documentoEmissao.equals=" + UPDATED_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoEmissaoIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoEmissao in DEFAULT_DOCUMENTO_EMISSAO or UPDATED_DOCUMENTO_EMISSAO
        defaultDocenteShouldBeFound("documentoEmissao.in=" + DEFAULT_DOCUMENTO_EMISSAO + "," + UPDATED_DOCUMENTO_EMISSAO);

        // Get all the docenteList where documentoEmissao equals to UPDATED_DOCUMENTO_EMISSAO
        defaultDocenteShouldNotBeFound("documentoEmissao.in=" + UPDATED_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoEmissaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoEmissao is not null
        defaultDocenteShouldBeFound("documentoEmissao.specified=true");

        // Get all the docenteList where documentoEmissao is null
        defaultDocenteShouldNotBeFound("documentoEmissao.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoEmissaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoEmissao is greater than or equal to DEFAULT_DOCUMENTO_EMISSAO
        defaultDocenteShouldBeFound("documentoEmissao.greaterThanOrEqual=" + DEFAULT_DOCUMENTO_EMISSAO);

        // Get all the docenteList where documentoEmissao is greater than or equal to UPDATED_DOCUMENTO_EMISSAO
        defaultDocenteShouldNotBeFound("documentoEmissao.greaterThanOrEqual=" + UPDATED_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoEmissaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoEmissao is less than or equal to DEFAULT_DOCUMENTO_EMISSAO
        defaultDocenteShouldBeFound("documentoEmissao.lessThanOrEqual=" + DEFAULT_DOCUMENTO_EMISSAO);

        // Get all the docenteList where documentoEmissao is less than or equal to SMALLER_DOCUMENTO_EMISSAO
        defaultDocenteShouldNotBeFound("documentoEmissao.lessThanOrEqual=" + SMALLER_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoEmissaoIsLessThanSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoEmissao is less than DEFAULT_DOCUMENTO_EMISSAO
        defaultDocenteShouldNotBeFound("documentoEmissao.lessThan=" + DEFAULT_DOCUMENTO_EMISSAO);

        // Get all the docenteList where documentoEmissao is less than UPDATED_DOCUMENTO_EMISSAO
        defaultDocenteShouldBeFound("documentoEmissao.lessThan=" + UPDATED_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoEmissaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoEmissao is greater than DEFAULT_DOCUMENTO_EMISSAO
        defaultDocenteShouldNotBeFound("documentoEmissao.greaterThan=" + DEFAULT_DOCUMENTO_EMISSAO);

        // Get all the docenteList where documentoEmissao is greater than SMALLER_DOCUMENTO_EMISSAO
        defaultDocenteShouldBeFound("documentoEmissao.greaterThan=" + SMALLER_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoValidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoValidade equals to DEFAULT_DOCUMENTO_VALIDADE
        defaultDocenteShouldBeFound("documentoValidade.equals=" + DEFAULT_DOCUMENTO_VALIDADE);

        // Get all the docenteList where documentoValidade equals to UPDATED_DOCUMENTO_VALIDADE
        defaultDocenteShouldNotBeFound("documentoValidade.equals=" + UPDATED_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoValidadeIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoValidade in DEFAULT_DOCUMENTO_VALIDADE or UPDATED_DOCUMENTO_VALIDADE
        defaultDocenteShouldBeFound("documentoValidade.in=" + DEFAULT_DOCUMENTO_VALIDADE + "," + UPDATED_DOCUMENTO_VALIDADE);

        // Get all the docenteList where documentoValidade equals to UPDATED_DOCUMENTO_VALIDADE
        defaultDocenteShouldNotBeFound("documentoValidade.in=" + UPDATED_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoValidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoValidade is not null
        defaultDocenteShouldBeFound("documentoValidade.specified=true");

        // Get all the docenteList where documentoValidade is null
        defaultDocenteShouldNotBeFound("documentoValidade.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoValidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoValidade is greater than or equal to DEFAULT_DOCUMENTO_VALIDADE
        defaultDocenteShouldBeFound("documentoValidade.greaterThanOrEqual=" + DEFAULT_DOCUMENTO_VALIDADE);

        // Get all the docenteList where documentoValidade is greater than or equal to UPDATED_DOCUMENTO_VALIDADE
        defaultDocenteShouldNotBeFound("documentoValidade.greaterThanOrEqual=" + UPDATED_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoValidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoValidade is less than or equal to DEFAULT_DOCUMENTO_VALIDADE
        defaultDocenteShouldBeFound("documentoValidade.lessThanOrEqual=" + DEFAULT_DOCUMENTO_VALIDADE);

        // Get all the docenteList where documentoValidade is less than or equal to SMALLER_DOCUMENTO_VALIDADE
        defaultDocenteShouldNotBeFound("documentoValidade.lessThanOrEqual=" + SMALLER_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoValidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoValidade is less than DEFAULT_DOCUMENTO_VALIDADE
        defaultDocenteShouldNotBeFound("documentoValidade.lessThan=" + DEFAULT_DOCUMENTO_VALIDADE);

        // Get all the docenteList where documentoValidade is less than UPDATED_DOCUMENTO_VALIDADE
        defaultDocenteShouldBeFound("documentoValidade.lessThan=" + UPDATED_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDocentesByDocumentoValidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where documentoValidade is greater than DEFAULT_DOCUMENTO_VALIDADE
        defaultDocenteShouldNotBeFound("documentoValidade.greaterThan=" + DEFAULT_DOCUMENTO_VALIDADE);

        // Get all the docenteList where documentoValidade is greater than SMALLER_DOCUMENTO_VALIDADE
        defaultDocenteShouldBeFound("documentoValidade.greaterThan=" + SMALLER_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDocentesByResidenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where residencia equals to DEFAULT_RESIDENCIA
        defaultDocenteShouldBeFound("residencia.equals=" + DEFAULT_RESIDENCIA);

        // Get all the docenteList where residencia equals to UPDATED_RESIDENCIA
        defaultDocenteShouldNotBeFound("residencia.equals=" + UPDATED_RESIDENCIA);
    }

    @Test
    @Transactional
    void getAllDocentesByResidenciaIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where residencia in DEFAULT_RESIDENCIA or UPDATED_RESIDENCIA
        defaultDocenteShouldBeFound("residencia.in=" + DEFAULT_RESIDENCIA + "," + UPDATED_RESIDENCIA);

        // Get all the docenteList where residencia equals to UPDATED_RESIDENCIA
        defaultDocenteShouldNotBeFound("residencia.in=" + UPDATED_RESIDENCIA);
    }

    @Test
    @Transactional
    void getAllDocentesByResidenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where residencia is not null
        defaultDocenteShouldBeFound("residencia.specified=true");

        // Get all the docenteList where residencia is null
        defaultDocenteShouldNotBeFound("residencia.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByResidenciaContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where residencia contains DEFAULT_RESIDENCIA
        defaultDocenteShouldBeFound("residencia.contains=" + DEFAULT_RESIDENCIA);

        // Get all the docenteList where residencia contains UPDATED_RESIDENCIA
        defaultDocenteShouldNotBeFound("residencia.contains=" + UPDATED_RESIDENCIA);
    }

    @Test
    @Transactional
    void getAllDocentesByResidenciaNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where residencia does not contain DEFAULT_RESIDENCIA
        defaultDocenteShouldNotBeFound("residencia.doesNotContain=" + DEFAULT_RESIDENCIA);

        // Get all the docenteList where residencia does not contain UPDATED_RESIDENCIA
        defaultDocenteShouldBeFound("residencia.doesNotContain=" + UPDATED_RESIDENCIA);
    }

    @Test
    @Transactional
    void getAllDocentesByDataInicioFuncoesIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where dataInicioFuncoes equals to DEFAULT_DATA_INICIO_FUNCOES
        defaultDocenteShouldBeFound("dataInicioFuncoes.equals=" + DEFAULT_DATA_INICIO_FUNCOES);

        // Get all the docenteList where dataInicioFuncoes equals to UPDATED_DATA_INICIO_FUNCOES
        defaultDocenteShouldNotBeFound("dataInicioFuncoes.equals=" + UPDATED_DATA_INICIO_FUNCOES);
    }

    @Test
    @Transactional
    void getAllDocentesByDataInicioFuncoesIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where dataInicioFuncoes in DEFAULT_DATA_INICIO_FUNCOES or UPDATED_DATA_INICIO_FUNCOES
        defaultDocenteShouldBeFound("dataInicioFuncoes.in=" + DEFAULT_DATA_INICIO_FUNCOES + "," + UPDATED_DATA_INICIO_FUNCOES);

        // Get all the docenteList where dataInicioFuncoes equals to UPDATED_DATA_INICIO_FUNCOES
        defaultDocenteShouldNotBeFound("dataInicioFuncoes.in=" + UPDATED_DATA_INICIO_FUNCOES);
    }

    @Test
    @Transactional
    void getAllDocentesByDataInicioFuncoesIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where dataInicioFuncoes is not null
        defaultDocenteShouldBeFound("dataInicioFuncoes.specified=true");

        // Get all the docenteList where dataInicioFuncoes is null
        defaultDocenteShouldNotBeFound("dataInicioFuncoes.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByDataInicioFuncoesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where dataInicioFuncoes is greater than or equal to DEFAULT_DATA_INICIO_FUNCOES
        defaultDocenteShouldBeFound("dataInicioFuncoes.greaterThanOrEqual=" + DEFAULT_DATA_INICIO_FUNCOES);

        // Get all the docenteList where dataInicioFuncoes is greater than or equal to UPDATED_DATA_INICIO_FUNCOES
        defaultDocenteShouldNotBeFound("dataInicioFuncoes.greaterThanOrEqual=" + UPDATED_DATA_INICIO_FUNCOES);
    }

    @Test
    @Transactional
    void getAllDocentesByDataInicioFuncoesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where dataInicioFuncoes is less than or equal to DEFAULT_DATA_INICIO_FUNCOES
        defaultDocenteShouldBeFound("dataInicioFuncoes.lessThanOrEqual=" + DEFAULT_DATA_INICIO_FUNCOES);

        // Get all the docenteList where dataInicioFuncoes is less than or equal to SMALLER_DATA_INICIO_FUNCOES
        defaultDocenteShouldNotBeFound("dataInicioFuncoes.lessThanOrEqual=" + SMALLER_DATA_INICIO_FUNCOES);
    }

    @Test
    @Transactional
    void getAllDocentesByDataInicioFuncoesIsLessThanSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where dataInicioFuncoes is less than DEFAULT_DATA_INICIO_FUNCOES
        defaultDocenteShouldNotBeFound("dataInicioFuncoes.lessThan=" + DEFAULT_DATA_INICIO_FUNCOES);

        // Get all the docenteList where dataInicioFuncoes is less than UPDATED_DATA_INICIO_FUNCOES
        defaultDocenteShouldBeFound("dataInicioFuncoes.lessThan=" + UPDATED_DATA_INICIO_FUNCOES);
    }

    @Test
    @Transactional
    void getAllDocentesByDataInicioFuncoesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where dataInicioFuncoes is greater than DEFAULT_DATA_INICIO_FUNCOES
        defaultDocenteShouldNotBeFound("dataInicioFuncoes.greaterThan=" + DEFAULT_DATA_INICIO_FUNCOES);

        // Get all the docenteList where dataInicioFuncoes is greater than SMALLER_DATA_INICIO_FUNCOES
        defaultDocenteShouldBeFound("dataInicioFuncoes.greaterThan=" + SMALLER_DATA_INICIO_FUNCOES);
    }

    @Test
    @Transactional
    void getAllDocentesByTelefonePrincipalIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where telefonePrincipal equals to DEFAULT_TELEFONE_PRINCIPAL
        defaultDocenteShouldBeFound("telefonePrincipal.equals=" + DEFAULT_TELEFONE_PRINCIPAL);

        // Get all the docenteList where telefonePrincipal equals to UPDATED_TELEFONE_PRINCIPAL
        defaultDocenteShouldNotBeFound("telefonePrincipal.equals=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllDocentesByTelefonePrincipalIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where telefonePrincipal in DEFAULT_TELEFONE_PRINCIPAL or UPDATED_TELEFONE_PRINCIPAL
        defaultDocenteShouldBeFound("telefonePrincipal.in=" + DEFAULT_TELEFONE_PRINCIPAL + "," + UPDATED_TELEFONE_PRINCIPAL);

        // Get all the docenteList where telefonePrincipal equals to UPDATED_TELEFONE_PRINCIPAL
        defaultDocenteShouldNotBeFound("telefonePrincipal.in=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllDocentesByTelefonePrincipalIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where telefonePrincipal is not null
        defaultDocenteShouldBeFound("telefonePrincipal.specified=true");

        // Get all the docenteList where telefonePrincipal is null
        defaultDocenteShouldNotBeFound("telefonePrincipal.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByTelefonePrincipalContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where telefonePrincipal contains DEFAULT_TELEFONE_PRINCIPAL
        defaultDocenteShouldBeFound("telefonePrincipal.contains=" + DEFAULT_TELEFONE_PRINCIPAL);

        // Get all the docenteList where telefonePrincipal contains UPDATED_TELEFONE_PRINCIPAL
        defaultDocenteShouldNotBeFound("telefonePrincipal.contains=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllDocentesByTelefonePrincipalNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where telefonePrincipal does not contain DEFAULT_TELEFONE_PRINCIPAL
        defaultDocenteShouldNotBeFound("telefonePrincipal.doesNotContain=" + DEFAULT_TELEFONE_PRINCIPAL);

        // Get all the docenteList where telefonePrincipal does not contain UPDATED_TELEFONE_PRINCIPAL
        defaultDocenteShouldBeFound("telefonePrincipal.doesNotContain=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllDocentesByTelefoneParenteIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where telefoneParente equals to DEFAULT_TELEFONE_PARENTE
        defaultDocenteShouldBeFound("telefoneParente.equals=" + DEFAULT_TELEFONE_PARENTE);

        // Get all the docenteList where telefoneParente equals to UPDATED_TELEFONE_PARENTE
        defaultDocenteShouldNotBeFound("telefoneParente.equals=" + UPDATED_TELEFONE_PARENTE);
    }

    @Test
    @Transactional
    void getAllDocentesByTelefoneParenteIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where telefoneParente in DEFAULT_TELEFONE_PARENTE or UPDATED_TELEFONE_PARENTE
        defaultDocenteShouldBeFound("telefoneParente.in=" + DEFAULT_TELEFONE_PARENTE + "," + UPDATED_TELEFONE_PARENTE);

        // Get all the docenteList where telefoneParente equals to UPDATED_TELEFONE_PARENTE
        defaultDocenteShouldNotBeFound("telefoneParente.in=" + UPDATED_TELEFONE_PARENTE);
    }

    @Test
    @Transactional
    void getAllDocentesByTelefoneParenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where telefoneParente is not null
        defaultDocenteShouldBeFound("telefoneParente.specified=true");

        // Get all the docenteList where telefoneParente is null
        defaultDocenteShouldNotBeFound("telefoneParente.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByTelefoneParenteContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where telefoneParente contains DEFAULT_TELEFONE_PARENTE
        defaultDocenteShouldBeFound("telefoneParente.contains=" + DEFAULT_TELEFONE_PARENTE);

        // Get all the docenteList where telefoneParente contains UPDATED_TELEFONE_PARENTE
        defaultDocenteShouldNotBeFound("telefoneParente.contains=" + UPDATED_TELEFONE_PARENTE);
    }

    @Test
    @Transactional
    void getAllDocentesByTelefoneParenteNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where telefoneParente does not contain DEFAULT_TELEFONE_PARENTE
        defaultDocenteShouldNotBeFound("telefoneParente.doesNotContain=" + DEFAULT_TELEFONE_PARENTE);

        // Get all the docenteList where telefoneParente does not contain UPDATED_TELEFONE_PARENTE
        defaultDocenteShouldBeFound("telefoneParente.doesNotContain=" + UPDATED_TELEFONE_PARENTE);
    }

    @Test
    @Transactional
    void getAllDocentesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where email equals to DEFAULT_EMAIL
        defaultDocenteShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the docenteList where email equals to UPDATED_EMAIL
        defaultDocenteShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDocentesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultDocenteShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the docenteList where email equals to UPDATED_EMAIL
        defaultDocenteShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDocentesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where email is not null
        defaultDocenteShouldBeFound("email.specified=true");

        // Get all the docenteList where email is null
        defaultDocenteShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByEmailContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where email contains DEFAULT_EMAIL
        defaultDocenteShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the docenteList where email contains UPDATED_EMAIL
        defaultDocenteShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDocentesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where email does not contain DEFAULT_EMAIL
        defaultDocenteShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the docenteList where email does not contain UPDATED_EMAIL
        defaultDocenteShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDocentesByNumeroAgenteIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where numeroAgente equals to DEFAULT_NUMERO_AGENTE
        defaultDocenteShouldBeFound("numeroAgente.equals=" + DEFAULT_NUMERO_AGENTE);

        // Get all the docenteList where numeroAgente equals to UPDATED_NUMERO_AGENTE
        defaultDocenteShouldNotBeFound("numeroAgente.equals=" + UPDATED_NUMERO_AGENTE);
    }

    @Test
    @Transactional
    void getAllDocentesByNumeroAgenteIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where numeroAgente in DEFAULT_NUMERO_AGENTE or UPDATED_NUMERO_AGENTE
        defaultDocenteShouldBeFound("numeroAgente.in=" + DEFAULT_NUMERO_AGENTE + "," + UPDATED_NUMERO_AGENTE);

        // Get all the docenteList where numeroAgente equals to UPDATED_NUMERO_AGENTE
        defaultDocenteShouldNotBeFound("numeroAgente.in=" + UPDATED_NUMERO_AGENTE);
    }

    @Test
    @Transactional
    void getAllDocentesByNumeroAgenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where numeroAgente is not null
        defaultDocenteShouldBeFound("numeroAgente.specified=true");

        // Get all the docenteList where numeroAgente is null
        defaultDocenteShouldNotBeFound("numeroAgente.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByNumeroAgenteContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where numeroAgente contains DEFAULT_NUMERO_AGENTE
        defaultDocenteShouldBeFound("numeroAgente.contains=" + DEFAULT_NUMERO_AGENTE);

        // Get all the docenteList where numeroAgente contains UPDATED_NUMERO_AGENTE
        defaultDocenteShouldNotBeFound("numeroAgente.contains=" + UPDATED_NUMERO_AGENTE);
    }

    @Test
    @Transactional
    void getAllDocentesByNumeroAgenteNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where numeroAgente does not contain DEFAULT_NUMERO_AGENTE
        defaultDocenteShouldNotBeFound("numeroAgente.doesNotContain=" + DEFAULT_NUMERO_AGENTE);

        // Get all the docenteList where numeroAgente does not contain UPDATED_NUMERO_AGENTE
        defaultDocenteShouldBeFound("numeroAgente.doesNotContain=" + UPDATED_NUMERO_AGENTE);
    }

    @Test
    @Transactional
    void getAllDocentesByTemAgregacaoPedagogicaIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where temAgregacaoPedagogica equals to DEFAULT_TEM_AGREGACAO_PEDAGOGICA
        defaultDocenteShouldBeFound("temAgregacaoPedagogica.equals=" + DEFAULT_TEM_AGREGACAO_PEDAGOGICA);

        // Get all the docenteList where temAgregacaoPedagogica equals to UPDATED_TEM_AGREGACAO_PEDAGOGICA
        defaultDocenteShouldNotBeFound("temAgregacaoPedagogica.equals=" + UPDATED_TEM_AGREGACAO_PEDAGOGICA);
    }

    @Test
    @Transactional
    void getAllDocentesByTemAgregacaoPedagogicaIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where temAgregacaoPedagogica in DEFAULT_TEM_AGREGACAO_PEDAGOGICA or UPDATED_TEM_AGREGACAO_PEDAGOGICA
        defaultDocenteShouldBeFound(
            "temAgregacaoPedagogica.in=" + DEFAULT_TEM_AGREGACAO_PEDAGOGICA + "," + UPDATED_TEM_AGREGACAO_PEDAGOGICA
        );

        // Get all the docenteList where temAgregacaoPedagogica equals to UPDATED_TEM_AGREGACAO_PEDAGOGICA
        defaultDocenteShouldNotBeFound("temAgregacaoPedagogica.in=" + UPDATED_TEM_AGREGACAO_PEDAGOGICA);
    }

    @Test
    @Transactional
    void getAllDocentesByTemAgregacaoPedagogicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where temAgregacaoPedagogica is not null
        defaultDocenteShouldBeFound("temAgregacaoPedagogica.specified=true");

        // Get all the docenteList where temAgregacaoPedagogica is null
        defaultDocenteShouldNotBeFound("temAgregacaoPedagogica.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where hash equals to DEFAULT_HASH
        defaultDocenteShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the docenteList where hash equals to UPDATED_HASH
        defaultDocenteShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDocentesByHashIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultDocenteShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the docenteList where hash equals to UPDATED_HASH
        defaultDocenteShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDocentesByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where hash is not null
        defaultDocenteShouldBeFound("hash.specified=true");

        // Get all the docenteList where hash is null
        defaultDocenteShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByHashContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where hash contains DEFAULT_HASH
        defaultDocenteShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the docenteList where hash contains UPDATED_HASH
        defaultDocenteShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDocentesByHashNotContainsSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where hash does not contain DEFAULT_HASH
        defaultDocenteShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the docenteList where hash does not contain UPDATED_HASH
        defaultDocenteShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDocentesByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where timestamp equals to DEFAULT_TIMESTAMP
        defaultDocenteShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the docenteList where timestamp equals to UPDATED_TIMESTAMP
        defaultDocenteShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDocentesByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultDocenteShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the docenteList where timestamp equals to UPDATED_TIMESTAMP
        defaultDocenteShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDocentesByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where timestamp is not null
        defaultDocenteShouldBeFound("timestamp.specified=true");

        // Get all the docenteList where timestamp is null
        defaultDocenteShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllDocentesByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultDocenteShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the docenteList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultDocenteShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDocentesByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultDocenteShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the docenteList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultDocenteShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDocentesByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where timestamp is less than DEFAULT_TIMESTAMP
        defaultDocenteShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the docenteList where timestamp is less than UPDATED_TIMESTAMP
        defaultDocenteShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDocentesByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultDocenteShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the docenteList where timestamp is greater than SMALLER_TIMESTAMP
        defaultDocenteShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllDocentesByOcorrenciaIsEqualToSomething() throws Exception {
        Ocorrencia ocorrencia;
        if (TestUtil.findAll(em, Ocorrencia.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            ocorrencia = OcorrenciaResourceIT.createEntity(em);
        } else {
            ocorrencia = TestUtil.findAll(em, Ocorrencia.class).get(0);
        }
        em.persist(ocorrencia);
        em.flush();
        docente.addOcorrencia(ocorrencia);
        docenteRepository.saveAndFlush(docente);
        Long ocorrenciaId = ocorrencia.getId();

        // Get all the docenteList where ocorrencia equals to ocorrenciaId
        defaultDocenteShouldBeFound("ocorrenciaId.equals=" + ocorrenciaId);

        // Get all the docenteList where ocorrencia equals to (ocorrenciaId + 1)
        defaultDocenteShouldNotBeFound("ocorrenciaId.equals=" + (ocorrenciaId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByHorariosIsEqualToSomething() throws Exception {
        Horario horarios;
        if (TestUtil.findAll(em, Horario.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            horarios = HorarioResourceIT.createEntity(em);
        } else {
            horarios = TestUtil.findAll(em, Horario.class).get(0);
        }
        em.persist(horarios);
        em.flush();
        docente.addHorarios(horarios);
        docenteRepository.saveAndFlush(docente);
        Long horariosId = horarios.getId();

        // Get all the docenteList where horarios equals to horariosId
        defaultDocenteShouldBeFound("horariosId.equals=" + horariosId);

        // Get all the docenteList where horarios equals to (horariosId + 1)
        defaultDocenteShouldNotBeFound("horariosId.equals=" + (horariosId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByPlanoAulaIsEqualToSomething() throws Exception {
        PlanoAula planoAula;
        if (TestUtil.findAll(em, PlanoAula.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            planoAula = PlanoAulaResourceIT.createEntity(em);
        } else {
            planoAula = TestUtil.findAll(em, PlanoAula.class).get(0);
        }
        em.persist(planoAula);
        em.flush();
        docente.addPlanoAula(planoAula);
        docenteRepository.saveAndFlush(docente);
        Long planoAulaId = planoAula.getId();

        // Get all the docenteList where planoAula equals to planoAulaId
        defaultDocenteShouldBeFound("planoAulaId.equals=" + planoAulaId);

        // Get all the docenteList where planoAula equals to (planoAulaId + 1)
        defaultDocenteShouldNotBeFound("planoAulaId.equals=" + (planoAulaId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByNotasPeriodicaDisciplinaIsEqualToSomething() throws Exception {
        NotasPeriodicaDisciplina notasPeriodicaDisciplina;
        if (TestUtil.findAll(em, NotasPeriodicaDisciplina.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            notasPeriodicaDisciplina = NotasPeriodicaDisciplinaResourceIT.createEntity(em);
        } else {
            notasPeriodicaDisciplina = TestUtil.findAll(em, NotasPeriodicaDisciplina.class).get(0);
        }
        em.persist(notasPeriodicaDisciplina);
        em.flush();
        docente.addNotasPeriodicaDisciplina(notasPeriodicaDisciplina);
        docenteRepository.saveAndFlush(docente);
        Long notasPeriodicaDisciplinaId = notasPeriodicaDisciplina.getId();

        // Get all the docenteList where notasPeriodicaDisciplina equals to notasPeriodicaDisciplinaId
        defaultDocenteShouldBeFound("notasPeriodicaDisciplinaId.equals=" + notasPeriodicaDisciplinaId);

        // Get all the docenteList where notasPeriodicaDisciplina equals to (notasPeriodicaDisciplinaId + 1)
        defaultDocenteShouldNotBeFound("notasPeriodicaDisciplinaId.equals=" + (notasPeriodicaDisciplinaId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByNotasGeralDisciplinaIsEqualToSomething() throws Exception {
        NotasGeralDisciplina notasGeralDisciplina;
        if (TestUtil.findAll(em, NotasGeralDisciplina.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            notasGeralDisciplina = NotasGeralDisciplinaResourceIT.createEntity(em);
        } else {
            notasGeralDisciplina = TestUtil.findAll(em, NotasGeralDisciplina.class).get(0);
        }
        em.persist(notasGeralDisciplina);
        em.flush();
        docente.addNotasGeralDisciplina(notasGeralDisciplina);
        docenteRepository.saveAndFlush(docente);
        Long notasGeralDisciplinaId = notasGeralDisciplina.getId();

        // Get all the docenteList where notasGeralDisciplina equals to notasGeralDisciplinaId
        defaultDocenteShouldBeFound("notasGeralDisciplinaId.equals=" + notasGeralDisciplinaId);

        // Get all the docenteList where notasGeralDisciplina equals to (notasGeralDisciplinaId + 1)
        defaultDocenteShouldNotBeFound("notasGeralDisciplinaId.equals=" + (notasGeralDisciplinaId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByDissertacaoFinalCursoIsEqualToSomething() throws Exception {
        DissertacaoFinalCurso dissertacaoFinalCurso;
        if (TestUtil.findAll(em, DissertacaoFinalCurso.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            dissertacaoFinalCurso = DissertacaoFinalCursoResourceIT.createEntity(em);
        } else {
            dissertacaoFinalCurso = TestUtil.findAll(em, DissertacaoFinalCurso.class).get(0);
        }
        em.persist(dissertacaoFinalCurso);
        em.flush();
        docente.addDissertacaoFinalCurso(dissertacaoFinalCurso);
        docenteRepository.saveAndFlush(docente);
        Long dissertacaoFinalCursoId = dissertacaoFinalCurso.getId();

        // Get all the docenteList where dissertacaoFinalCurso equals to dissertacaoFinalCursoId
        defaultDocenteShouldBeFound("dissertacaoFinalCursoId.equals=" + dissertacaoFinalCursoId);

        // Get all the docenteList where dissertacaoFinalCurso equals to (dissertacaoFinalCursoId + 1)
        defaultDocenteShouldNotBeFound("dissertacaoFinalCursoId.equals=" + (dissertacaoFinalCursoId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByCategoriaOcorrenciaIsEqualToSomething() throws Exception {
        CategoriaOcorrencia categoriaOcorrencia;
        if (TestUtil.findAll(em, CategoriaOcorrencia.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            categoriaOcorrencia = CategoriaOcorrenciaResourceIT.createEntity(em);
        } else {
            categoriaOcorrencia = TestUtil.findAll(em, CategoriaOcorrencia.class).get(0);
        }
        em.persist(categoriaOcorrencia);
        em.flush();
        docente.addCategoriaOcorrencia(categoriaOcorrencia);
        docenteRepository.saveAndFlush(docente);
        Long categoriaOcorrenciaId = categoriaOcorrencia.getId();

        // Get all the docenteList where categoriaOcorrencia equals to categoriaOcorrenciaId
        defaultDocenteShouldBeFound("categoriaOcorrenciaId.equals=" + categoriaOcorrenciaId);

        // Get all the docenteList where categoriaOcorrencia equals to (categoriaOcorrenciaId + 1)
        defaultDocenteShouldNotBeFound("categoriaOcorrenciaId.equals=" + (categoriaOcorrenciaId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByFormacoesIsEqualToSomething() throws Exception {
        FormacaoDocente formacoes;
        if (TestUtil.findAll(em, FormacaoDocente.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            formacoes = FormacaoDocenteResourceIT.createEntity(em);
        } else {
            formacoes = TestUtil.findAll(em, FormacaoDocente.class).get(0);
        }
        em.persist(formacoes);
        em.flush();
        docente.addFormacoes(formacoes);
        docenteRepository.saveAndFlush(docente);
        Long formacoesId = formacoes.getId();

        // Get all the docenteList where formacoes equals to formacoesId
        defaultDocenteShouldBeFound("formacoesId.equals=" + formacoesId);

        // Get all the docenteList where formacoes equals to (formacoesId + 1)
        defaultDocenteShouldNotBeFound("formacoesId.equals=" + (formacoesId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByNacionalidadeIsEqualToSomething() throws Exception {
        LookupItem nacionalidade;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            nacionalidade = LookupItemResourceIT.createEntity(em);
        } else {
            nacionalidade = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(nacionalidade);
        em.flush();
        docente.setNacionalidade(nacionalidade);
        docenteRepository.saveAndFlush(docente);
        Long nacionalidadeId = nacionalidade.getId();

        // Get all the docenteList where nacionalidade equals to nacionalidadeId
        defaultDocenteShouldBeFound("nacionalidadeId.equals=" + nacionalidadeId);

        // Get all the docenteList where nacionalidade equals to (nacionalidadeId + 1)
        defaultDocenteShouldNotBeFound("nacionalidadeId.equals=" + (nacionalidadeId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByNaturalidadeIsEqualToSomething() throws Exception {
        LookupItem naturalidade;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            naturalidade = LookupItemResourceIT.createEntity(em);
        } else {
            naturalidade = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(naturalidade);
        em.flush();
        docente.setNaturalidade(naturalidade);
        docenteRepository.saveAndFlush(docente);
        Long naturalidadeId = naturalidade.getId();

        // Get all the docenteList where naturalidade equals to naturalidadeId
        defaultDocenteShouldBeFound("naturalidadeId.equals=" + naturalidadeId);

        // Get all the docenteList where naturalidade equals to (naturalidadeId + 1)
        defaultDocenteShouldNotBeFound("naturalidadeId.equals=" + (naturalidadeId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByTipoDocumentoIsEqualToSomething() throws Exception {
        LookupItem tipoDocumento;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            tipoDocumento = LookupItemResourceIT.createEntity(em);
        } else {
            tipoDocumento = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(tipoDocumento);
        em.flush();
        docente.setTipoDocumento(tipoDocumento);
        docenteRepository.saveAndFlush(docente);
        Long tipoDocumentoId = tipoDocumento.getId();

        // Get all the docenteList where tipoDocumento equals to tipoDocumentoId
        defaultDocenteShouldBeFound("tipoDocumentoId.equals=" + tipoDocumentoId);

        // Get all the docenteList where tipoDocumento equals to (tipoDocumentoId + 1)
        defaultDocenteShouldNotBeFound("tipoDocumentoId.equals=" + (tipoDocumentoId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByGrauAcademicoIsEqualToSomething() throws Exception {
        LookupItem grauAcademico;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            grauAcademico = LookupItemResourceIT.createEntity(em);
        } else {
            grauAcademico = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(grauAcademico);
        em.flush();
        docente.setGrauAcademico(grauAcademico);
        docenteRepository.saveAndFlush(docente);
        Long grauAcademicoId = grauAcademico.getId();

        // Get all the docenteList where grauAcademico equals to grauAcademicoId
        defaultDocenteShouldBeFound("grauAcademicoId.equals=" + grauAcademicoId);

        // Get all the docenteList where grauAcademico equals to (grauAcademicoId + 1)
        defaultDocenteShouldNotBeFound("grauAcademicoId.equals=" + (grauAcademicoId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByCategoriaProfissionalIsEqualToSomething() throws Exception {
        LookupItem categoriaProfissional;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            categoriaProfissional = LookupItemResourceIT.createEntity(em);
        } else {
            categoriaProfissional = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(categoriaProfissional);
        em.flush();
        docente.setCategoriaProfissional(categoriaProfissional);
        docenteRepository.saveAndFlush(docente);
        Long categoriaProfissionalId = categoriaProfissional.getId();

        // Get all the docenteList where categoriaProfissional equals to categoriaProfissionalId
        defaultDocenteShouldBeFound("categoriaProfissionalId.equals=" + categoriaProfissionalId);

        // Get all the docenteList where categoriaProfissional equals to (categoriaProfissionalId + 1)
        defaultDocenteShouldNotBeFound("categoriaProfissionalId.equals=" + (categoriaProfissionalId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByUnidadeOrganicaIsEqualToSomething() throws Exception {
        LookupItem unidadeOrganica;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            unidadeOrganica = LookupItemResourceIT.createEntity(em);
        } else {
            unidadeOrganica = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(unidadeOrganica);
        em.flush();
        docente.setUnidadeOrganica(unidadeOrganica);
        docenteRepository.saveAndFlush(docente);
        Long unidadeOrganicaId = unidadeOrganica.getId();

        // Get all the docenteList where unidadeOrganica equals to unidadeOrganicaId
        defaultDocenteShouldBeFound("unidadeOrganicaId.equals=" + unidadeOrganicaId);

        // Get all the docenteList where unidadeOrganica equals to (unidadeOrganicaId + 1)
        defaultDocenteShouldNotBeFound("unidadeOrganicaId.equals=" + (unidadeOrganicaId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByEstadoCivilIsEqualToSomething() throws Exception {
        LookupItem estadoCivil;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            estadoCivil = LookupItemResourceIT.createEntity(em);
        } else {
            estadoCivil = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(estadoCivil);
        em.flush();
        docente.setEstadoCivil(estadoCivil);
        docenteRepository.saveAndFlush(docente);
        Long estadoCivilId = estadoCivil.getId();

        // Get all the docenteList where estadoCivil equals to estadoCivilId
        defaultDocenteShouldBeFound("estadoCivilId.equals=" + estadoCivilId);

        // Get all the docenteList where estadoCivil equals to (estadoCivilId + 1)
        defaultDocenteShouldNotBeFound("estadoCivilId.equals=" + (estadoCivilId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByResponsavelTurnoIsEqualToSomething() throws Exception {
        ResponsavelTurno responsavelTurno;
        if (TestUtil.findAll(em, ResponsavelTurno.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            responsavelTurno = ResponsavelTurnoResourceIT.createEntity(em);
        } else {
            responsavelTurno = TestUtil.findAll(em, ResponsavelTurno.class).get(0);
        }
        em.persist(responsavelTurno);
        em.flush();
        docente.setResponsavelTurno(responsavelTurno);
        docenteRepository.saveAndFlush(docente);
        Long responsavelTurnoId = responsavelTurno.getId();

        // Get all the docenteList where responsavelTurno equals to responsavelTurnoId
        defaultDocenteShouldBeFound("responsavelTurnoId.equals=" + responsavelTurnoId);

        // Get all the docenteList where responsavelTurno equals to (responsavelTurnoId + 1)
        defaultDocenteShouldNotBeFound("responsavelTurnoId.equals=" + (responsavelTurnoId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByResponsavelAreaFormacaoIsEqualToSomething() throws Exception {
        ResponsavelAreaFormacao responsavelAreaFormacao;
        if (TestUtil.findAll(em, ResponsavelAreaFormacao.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            responsavelAreaFormacao = ResponsavelAreaFormacaoResourceIT.createEntity(em);
        } else {
            responsavelAreaFormacao = TestUtil.findAll(em, ResponsavelAreaFormacao.class).get(0);
        }
        em.persist(responsavelAreaFormacao);
        em.flush();
        docente.setResponsavelAreaFormacao(responsavelAreaFormacao);
        docenteRepository.saveAndFlush(docente);
        Long responsavelAreaFormacaoId = responsavelAreaFormacao.getId();

        // Get all the docenteList where responsavelAreaFormacao equals to responsavelAreaFormacaoId
        defaultDocenteShouldBeFound("responsavelAreaFormacaoId.equals=" + responsavelAreaFormacaoId);

        // Get all the docenteList where responsavelAreaFormacao equals to (responsavelAreaFormacaoId + 1)
        defaultDocenteShouldNotBeFound("responsavelAreaFormacaoId.equals=" + (responsavelAreaFormacaoId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByResponsavelCursoIsEqualToSomething() throws Exception {
        ResponsavelCurso responsavelCurso;
        if (TestUtil.findAll(em, ResponsavelCurso.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            responsavelCurso = ResponsavelCursoResourceIT.createEntity(em);
        } else {
            responsavelCurso = TestUtil.findAll(em, ResponsavelCurso.class).get(0);
        }
        em.persist(responsavelCurso);
        em.flush();
        docente.setResponsavelCurso(responsavelCurso);
        docenteRepository.saveAndFlush(docente);
        Long responsavelCursoId = responsavelCurso.getId();

        // Get all the docenteList where responsavelCurso equals to responsavelCursoId
        defaultDocenteShouldBeFound("responsavelCursoId.equals=" + responsavelCursoId);

        // Get all the docenteList where responsavelCurso equals to (responsavelCursoId + 1)
        defaultDocenteShouldNotBeFound("responsavelCursoId.equals=" + (responsavelCursoId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByResponsavelDisciplinaIsEqualToSomething() throws Exception {
        ResponsavelDisciplina responsavelDisciplina;
        if (TestUtil.findAll(em, ResponsavelDisciplina.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            responsavelDisciplina = ResponsavelDisciplinaResourceIT.createEntity(em);
        } else {
            responsavelDisciplina = TestUtil.findAll(em, ResponsavelDisciplina.class).get(0);
        }
        em.persist(responsavelDisciplina);
        em.flush();
        docente.setResponsavelDisciplina(responsavelDisciplina);
        docenteRepository.saveAndFlush(docente);
        Long responsavelDisciplinaId = responsavelDisciplina.getId();

        // Get all the docenteList where responsavelDisciplina equals to responsavelDisciplinaId
        defaultDocenteShouldBeFound("responsavelDisciplinaId.equals=" + responsavelDisciplinaId);

        // Get all the docenteList where responsavelDisciplina equals to (responsavelDisciplinaId + 1)
        defaultDocenteShouldNotBeFound("responsavelDisciplinaId.equals=" + (responsavelDisciplinaId + 1));
    }

    @Test
    @Transactional
    void getAllDocentesByResponsavelTurmaIsEqualToSomething() throws Exception {
        ResponsavelTurma responsavelTurma;
        if (TestUtil.findAll(em, ResponsavelTurma.class).isEmpty()) {
            docenteRepository.saveAndFlush(docente);
            responsavelTurma = ResponsavelTurmaResourceIT.createEntity(em);
        } else {
            responsavelTurma = TestUtil.findAll(em, ResponsavelTurma.class).get(0);
        }
        em.persist(responsavelTurma);
        em.flush();
        docente.setResponsavelTurma(responsavelTurma);
        docenteRepository.saveAndFlush(docente);
        Long responsavelTurmaId = responsavelTurma.getId();

        // Get all the docenteList where responsavelTurma equals to responsavelTurmaId
        defaultDocenteShouldBeFound("responsavelTurmaId.equals=" + responsavelTurmaId);

        // Get all the docenteList where responsavelTurma equals to (responsavelTurmaId + 1)
        defaultDocenteShouldNotBeFound("responsavelTurmaId.equals=" + (responsavelTurmaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocenteShouldBeFound(String filter) throws Exception {
        restDocenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docente.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotografiaContentType").value(hasItem(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA))))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nascimento").value(hasItem(DEFAULT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].inss").value(hasItem(DEFAULT_INSS)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].pai").value(hasItem(DEFAULT_PAI)))
            .andExpect(jsonPath("$.[*].mae").value(hasItem(DEFAULT_MAE)))
            .andExpect(jsonPath("$.[*].documentoNumero").value(hasItem(DEFAULT_DOCUMENTO_NUMERO)))
            .andExpect(jsonPath("$.[*].documentoEmissao").value(hasItem(DEFAULT_DOCUMENTO_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].documentoValidade").value(hasItem(DEFAULT_DOCUMENTO_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].residencia").value(hasItem(DEFAULT_RESIDENCIA)))
            .andExpect(jsonPath("$.[*].dataInicioFuncoes").value(hasItem(DEFAULT_DATA_INICIO_FUNCOES.toString())))
            .andExpect(jsonPath("$.[*].telefonePrincipal").value(hasItem(DEFAULT_TELEFONE_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].telefoneParente").value(hasItem(DEFAULT_TELEFONE_PARENTE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].numeroAgente").value(hasItem(DEFAULT_NUMERO_AGENTE)))
            .andExpect(jsonPath("$.[*].temAgregacaoPedagogica").value(hasItem(DEFAULT_TEM_AGREGACAO_PEDAGOGICA.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restDocenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocenteShouldNotBeFound(String filter) throws Exception {
        restDocenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocente() throws Exception {
        // Get the docente
        restDocenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocente() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        int databaseSizeBeforeUpdate = docenteRepository.findAll().size();

        // Update the docente
        Docente updatedDocente = docenteRepository.findById(docente.getId()).get();
        // Disconnect from session so that the updates on updatedDocente are not directly saved in db
        em.detach(updatedDocente);
        updatedDocente
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .nascimento(UPDATED_NASCIMENTO)
            .nif(UPDATED_NIF)
            .inss(UPDATED_INSS)
            .sexo(UPDATED_SEXO)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO)
            .documentoEmissao(UPDATED_DOCUMENTO_EMISSAO)
            .documentoValidade(UPDATED_DOCUMENTO_VALIDADE)
            .residencia(UPDATED_RESIDENCIA)
            .dataInicioFuncoes(UPDATED_DATA_INICIO_FUNCOES)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneParente(UPDATED_TELEFONE_PARENTE)
            .email(UPDATED_EMAIL)
            .numeroAgente(UPDATED_NUMERO_AGENTE)
            .temAgregacaoPedagogica(UPDATED_TEM_AGREGACAO_PEDAGOGICA)
            .observacao(UPDATED_OBSERVACAO)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);
        DocenteDTO docenteDTO = docenteMapper.toDto(updatedDocente);

        restDocenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, docenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(docenteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeUpdate);
        Docente testDocente = docenteList.get(docenteList.size() - 1);
        assertThat(testDocente.getFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testDocente.getFotografiaContentType()).isEqualTo(UPDATED_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testDocente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDocente.getNascimento()).isEqualTo(UPDATED_NASCIMENTO);
        assertThat(testDocente.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testDocente.getInss()).isEqualTo(UPDATED_INSS);
        assertThat(testDocente.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testDocente.getPai()).isEqualTo(UPDATED_PAI);
        assertThat(testDocente.getMae()).isEqualTo(UPDATED_MAE);
        assertThat(testDocente.getDocumentoNumero()).isEqualTo(UPDATED_DOCUMENTO_NUMERO);
        assertThat(testDocente.getDocumentoEmissao()).isEqualTo(UPDATED_DOCUMENTO_EMISSAO);
        assertThat(testDocente.getDocumentoValidade()).isEqualTo(UPDATED_DOCUMENTO_VALIDADE);
        assertThat(testDocente.getResidencia()).isEqualTo(UPDATED_RESIDENCIA);
        assertThat(testDocente.getDataInicioFuncoes()).isEqualTo(UPDATED_DATA_INICIO_FUNCOES);
        assertThat(testDocente.getTelefonePrincipal()).isEqualTo(UPDATED_TELEFONE_PRINCIPAL);
        assertThat(testDocente.getTelefoneParente()).isEqualTo(UPDATED_TELEFONE_PARENTE);
        assertThat(testDocente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDocente.getNumeroAgente()).isEqualTo(UPDATED_NUMERO_AGENTE);
        assertThat(testDocente.getTemAgregacaoPedagogica()).isEqualTo(UPDATED_TEM_AGREGACAO_PEDAGOGICA);
        assertThat(testDocente.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testDocente.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testDocente.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingDocente() throws Exception {
        int databaseSizeBeforeUpdate = docenteRepository.findAll().size();
        docente.setId(count.incrementAndGet());

        // Create the Docente
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, docenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(docenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocente() throws Exception {
        int databaseSizeBeforeUpdate = docenteRepository.findAll().size();
        docente.setId(count.incrementAndGet());

        // Create the Docente
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(docenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocente() throws Exception {
        int databaseSizeBeforeUpdate = docenteRepository.findAll().size();
        docente.setId(count.incrementAndGet());

        // Create the Docente
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docenteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocenteWithPatch() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        int databaseSizeBeforeUpdate = docenteRepository.findAll().size();

        // Update the docente using partial update
        Docente partialUpdatedDocente = new Docente();
        partialUpdatedDocente.setId(docente.getId());

        partialUpdatedDocente
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .inss(UPDATED_INSS)
            .sexo(UPDATED_SEXO)
            .dataInicioFuncoes(UPDATED_DATA_INICIO_FUNCOES)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .numeroAgente(UPDATED_NUMERO_AGENTE)
            .observacao(UPDATED_OBSERVACAO)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);

        restDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocente))
            )
            .andExpect(status().isOk());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeUpdate);
        Docente testDocente = docenteList.get(docenteList.size() - 1);
        assertThat(testDocente.getFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testDocente.getFotografiaContentType()).isEqualTo(UPDATED_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testDocente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDocente.getNascimento()).isEqualTo(DEFAULT_NASCIMENTO);
        assertThat(testDocente.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testDocente.getInss()).isEqualTo(UPDATED_INSS);
        assertThat(testDocente.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testDocente.getPai()).isEqualTo(DEFAULT_PAI);
        assertThat(testDocente.getMae()).isEqualTo(DEFAULT_MAE);
        assertThat(testDocente.getDocumentoNumero()).isEqualTo(DEFAULT_DOCUMENTO_NUMERO);
        assertThat(testDocente.getDocumentoEmissao()).isEqualTo(DEFAULT_DOCUMENTO_EMISSAO);
        assertThat(testDocente.getDocumentoValidade()).isEqualTo(DEFAULT_DOCUMENTO_VALIDADE);
        assertThat(testDocente.getResidencia()).isEqualTo(DEFAULT_RESIDENCIA);
        assertThat(testDocente.getDataInicioFuncoes()).isEqualTo(UPDATED_DATA_INICIO_FUNCOES);
        assertThat(testDocente.getTelefonePrincipal()).isEqualTo(UPDATED_TELEFONE_PRINCIPAL);
        assertThat(testDocente.getTelefoneParente()).isEqualTo(DEFAULT_TELEFONE_PARENTE);
        assertThat(testDocente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDocente.getNumeroAgente()).isEqualTo(UPDATED_NUMERO_AGENTE);
        assertThat(testDocente.getTemAgregacaoPedagogica()).isEqualTo(DEFAULT_TEM_AGREGACAO_PEDAGOGICA);
        assertThat(testDocente.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testDocente.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testDocente.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateDocenteWithPatch() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        int databaseSizeBeforeUpdate = docenteRepository.findAll().size();

        // Update the docente using partial update
        Docente partialUpdatedDocente = new Docente();
        partialUpdatedDocente.setId(docente.getId());

        partialUpdatedDocente
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .nascimento(UPDATED_NASCIMENTO)
            .nif(UPDATED_NIF)
            .inss(UPDATED_INSS)
            .sexo(UPDATED_SEXO)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO)
            .documentoEmissao(UPDATED_DOCUMENTO_EMISSAO)
            .documentoValidade(UPDATED_DOCUMENTO_VALIDADE)
            .residencia(UPDATED_RESIDENCIA)
            .dataInicioFuncoes(UPDATED_DATA_INICIO_FUNCOES)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneParente(UPDATED_TELEFONE_PARENTE)
            .email(UPDATED_EMAIL)
            .numeroAgente(UPDATED_NUMERO_AGENTE)
            .temAgregacaoPedagogica(UPDATED_TEM_AGREGACAO_PEDAGOGICA)
            .observacao(UPDATED_OBSERVACAO)
            .hash(UPDATED_HASH)
            .timestamp(UPDATED_TIMESTAMP);

        restDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocente))
            )
            .andExpect(status().isOk());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeUpdate);
        Docente testDocente = docenteList.get(docenteList.size() - 1);
        assertThat(testDocente.getFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testDocente.getFotografiaContentType()).isEqualTo(UPDATED_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testDocente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDocente.getNascimento()).isEqualTo(UPDATED_NASCIMENTO);
        assertThat(testDocente.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testDocente.getInss()).isEqualTo(UPDATED_INSS);
        assertThat(testDocente.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testDocente.getPai()).isEqualTo(UPDATED_PAI);
        assertThat(testDocente.getMae()).isEqualTo(UPDATED_MAE);
        assertThat(testDocente.getDocumentoNumero()).isEqualTo(UPDATED_DOCUMENTO_NUMERO);
        assertThat(testDocente.getDocumentoEmissao()).isEqualTo(UPDATED_DOCUMENTO_EMISSAO);
        assertThat(testDocente.getDocumentoValidade()).isEqualTo(UPDATED_DOCUMENTO_VALIDADE);
        assertThat(testDocente.getResidencia()).isEqualTo(UPDATED_RESIDENCIA);
        assertThat(testDocente.getDataInicioFuncoes()).isEqualTo(UPDATED_DATA_INICIO_FUNCOES);
        assertThat(testDocente.getTelefonePrincipal()).isEqualTo(UPDATED_TELEFONE_PRINCIPAL);
        assertThat(testDocente.getTelefoneParente()).isEqualTo(UPDATED_TELEFONE_PARENTE);
        assertThat(testDocente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDocente.getNumeroAgente()).isEqualTo(UPDATED_NUMERO_AGENTE);
        assertThat(testDocente.getTemAgregacaoPedagogica()).isEqualTo(UPDATED_TEM_AGREGACAO_PEDAGOGICA);
        assertThat(testDocente.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testDocente.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testDocente.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingDocente() throws Exception {
        int databaseSizeBeforeUpdate = docenteRepository.findAll().size();
        docente.setId(count.incrementAndGet());

        // Create the Docente
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, docenteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(docenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocente() throws Exception {
        int databaseSizeBeforeUpdate = docenteRepository.findAll().size();
        docente.setId(count.incrementAndGet());

        // Create the Docente
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(docenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocente() throws Exception {
        int databaseSizeBeforeUpdate = docenteRepository.findAll().size();
        docente.setId(count.incrementAndGet());

        // Create the Docente
        DocenteDTO docenteDTO = docenteMapper.toDto(docente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(docenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocente() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        int databaseSizeBeforeDelete = docenteRepository.findAll().size();

        // Delete the docente
        restDocenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, docente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
