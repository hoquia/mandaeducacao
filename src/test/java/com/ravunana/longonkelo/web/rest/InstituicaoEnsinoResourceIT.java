package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.InstituicaoEnsino;
import com.ravunana.longonkelo.domain.InstituicaoEnsino;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.ProvedorNotificacao;
import com.ravunana.longonkelo.repository.InstituicaoEnsinoRepository;
import com.ravunana.longonkelo.service.InstituicaoEnsinoService;
import com.ravunana.longonkelo.service.criteria.InstituicaoEnsinoCriteria;
import com.ravunana.longonkelo.service.dto.InstituicaoEnsinoDTO;
import com.ravunana.longonkelo.service.mapper.InstituicaoEnsinoMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link InstituicaoEnsinoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InstituicaoEnsinoResourceIT {

    private static final byte[] DEFAULT_LOGOTIPO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGOTIPO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGOTIPO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGOTIPO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_UNIDADE_ORGANICA = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_ORGANICA = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_FISCAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_FISCAL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    private static final String DEFAULT_CAE = "AAAAAAAAAA";
    private static final String UPDATED_CAE = "BBBBBBBBBB";

    private static final String DEFAULT_NISS = "AAAAAAAAAA";
    private static final String UPDATED_NISS = "BBBBBBBBBB";

    private static final String DEFAULT_FUNDADOR = "AAAAAAAAAA";
    private static final String UPDATED_FUNDADOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FUNDACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FUNDACAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FUNDACAO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DIMENSAO = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSAO = "BBBBBBBBBB";

    private static final String DEFAULT_SLOGAM = "AAAAAAAAAA";
    private static final String UPDATED_SLOGAM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEMOVEL = "AAAAAAAAAA";
    private static final String UPDATED_TELEMOVEL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO_DETALHADO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO_DETALHADO = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_COMPARTICIPADA = false;
    private static final Boolean UPDATED_IS_COMPARTICIPADA = true;

    private static final String DEFAULT_TERMOS_COMPROMISSOS = "AAAAAAAAAA";
    private static final String UPDATED_TERMOS_COMPROMISSOS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/instituicao-ensinos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InstituicaoEnsinoRepository instituicaoEnsinoRepository;

    @Mock
    private InstituicaoEnsinoRepository instituicaoEnsinoRepositoryMock;

    @Autowired
    private InstituicaoEnsinoMapper instituicaoEnsinoMapper;

    @Mock
    private InstituicaoEnsinoService instituicaoEnsinoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstituicaoEnsinoMockMvc;

    private InstituicaoEnsino instituicaoEnsino;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstituicaoEnsino createEntity(EntityManager em) {
        InstituicaoEnsino instituicaoEnsino = new InstituicaoEnsino()
            .logotipo(DEFAULT_LOGOTIPO)
            .logotipoContentType(DEFAULT_LOGOTIPO_CONTENT_TYPE)
            .unidadeOrganica(DEFAULT_UNIDADE_ORGANICA)
            .nomeFiscal(DEFAULT_NOME_FISCAL)
            .numero(DEFAULT_NUMERO)
            .nif(DEFAULT_NIF)
            .cae(DEFAULT_CAE)
            .niss(DEFAULT_NISS)
            .fundador(DEFAULT_FUNDADOR)
            .fundacao(DEFAULT_FUNDACAO)
            .dimensao(DEFAULT_DIMENSAO)
            .slogam(DEFAULT_SLOGAM)
            .telefone(DEFAULT_TELEFONE)
            .telemovel(DEFAULT_TELEMOVEL)
            .email(DEFAULT_EMAIL)
            .website(DEFAULT_WEBSITE)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .enderecoDetalhado(DEFAULT_ENDERECO_DETALHADO)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .descricao(DEFAULT_DESCRICAO)
            .isComparticipada(DEFAULT_IS_COMPARTICIPADA)
            .termosCompromissos(DEFAULT_TERMOS_COMPROMISSOS);
        return instituicaoEnsino;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstituicaoEnsino createUpdatedEntity(EntityManager em) {
        InstituicaoEnsino instituicaoEnsino = new InstituicaoEnsino()
            .logotipo(UPDATED_LOGOTIPO)
            .logotipoContentType(UPDATED_LOGOTIPO_CONTENT_TYPE)
            .unidadeOrganica(UPDATED_UNIDADE_ORGANICA)
            .nomeFiscal(UPDATED_NOME_FISCAL)
            .numero(UPDATED_NUMERO)
            .nif(UPDATED_NIF)
            .cae(UPDATED_CAE)
            .niss(UPDATED_NISS)
            .fundador(UPDATED_FUNDADOR)
            .fundacao(UPDATED_FUNDACAO)
            .dimensao(UPDATED_DIMENSAO)
            .slogam(UPDATED_SLOGAM)
            .telefone(UPDATED_TELEFONE)
            .telemovel(UPDATED_TELEMOVEL)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .enderecoDetalhado(UPDATED_ENDERECO_DETALHADO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .descricao(UPDATED_DESCRICAO)
            .isComparticipada(UPDATED_IS_COMPARTICIPADA)
            .termosCompromissos(UPDATED_TERMOS_COMPROMISSOS);
        return instituicaoEnsino;
    }

    @BeforeEach
    public void initTest() {
        instituicaoEnsino = createEntity(em);
    }

    @Test
    @Transactional
    void createInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeCreate = instituicaoEnsinoRepository.findAll().size();
        // Create the InstituicaoEnsino
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);
        restInstituicaoEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeCreate + 1);
        InstituicaoEnsino testInstituicaoEnsino = instituicaoEnsinoList.get(instituicaoEnsinoList.size() - 1);
        assertThat(testInstituicaoEnsino.getLogotipo()).isEqualTo(DEFAULT_LOGOTIPO);
        assertThat(testInstituicaoEnsino.getLogotipoContentType()).isEqualTo(DEFAULT_LOGOTIPO_CONTENT_TYPE);
        assertThat(testInstituicaoEnsino.getUnidadeOrganica()).isEqualTo(DEFAULT_UNIDADE_ORGANICA);
        assertThat(testInstituicaoEnsino.getNomeFiscal()).isEqualTo(DEFAULT_NOME_FISCAL);
        assertThat(testInstituicaoEnsino.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testInstituicaoEnsino.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testInstituicaoEnsino.getCae()).isEqualTo(DEFAULT_CAE);
        assertThat(testInstituicaoEnsino.getNiss()).isEqualTo(DEFAULT_NISS);
        assertThat(testInstituicaoEnsino.getFundador()).isEqualTo(DEFAULT_FUNDADOR);
        assertThat(testInstituicaoEnsino.getFundacao()).isEqualTo(DEFAULT_FUNDACAO);
        assertThat(testInstituicaoEnsino.getDimensao()).isEqualTo(DEFAULT_DIMENSAO);
        assertThat(testInstituicaoEnsino.getSlogam()).isEqualTo(DEFAULT_SLOGAM);
        assertThat(testInstituicaoEnsino.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testInstituicaoEnsino.getTelemovel()).isEqualTo(DEFAULT_TELEMOVEL);
        assertThat(testInstituicaoEnsino.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInstituicaoEnsino.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testInstituicaoEnsino.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testInstituicaoEnsino.getEnderecoDetalhado()).isEqualTo(DEFAULT_ENDERECO_DETALHADO);
        assertThat(testInstituicaoEnsino.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testInstituicaoEnsino.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testInstituicaoEnsino.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testInstituicaoEnsino.getIsComparticipada()).isEqualTo(DEFAULT_IS_COMPARTICIPADA);
        assertThat(testInstituicaoEnsino.getTermosCompromissos()).isEqualTo(DEFAULT_TERMOS_COMPROMISSOS);
    }

    @Test
    @Transactional
    void createInstituicaoEnsinoWithExistingId() throws Exception {
        // Create the InstituicaoEnsino with an existing ID
        instituicaoEnsino.setId(1L);
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        int databaseSizeBeforeCreate = instituicaoEnsinoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstituicaoEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUnidadeOrganicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituicaoEnsinoRepository.findAll().size();
        // set the field null
        instituicaoEnsino.setUnidadeOrganica(null);

        // Create the InstituicaoEnsino, which fails.
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        restInstituicaoEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituicaoEnsinoRepository.findAll().size();
        // set the field null
        instituicaoEnsino.setNumero(null);

        // Create the InstituicaoEnsino, which fails.
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        restInstituicaoEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituicaoEnsinoRepository.findAll().size();
        // set the field null
        instituicaoEnsino.setTelefone(null);

        // Create the InstituicaoEnsino, which fails.
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        restInstituicaoEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituicaoEnsinoRepository.findAll().size();
        // set the field null
        instituicaoEnsino.setEmail(null);

        // Create the InstituicaoEnsino, which fails.
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        restInstituicaoEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnderecoDetalhadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituicaoEnsinoRepository.findAll().size();
        // set the field null
        instituicaoEnsino.setEnderecoDetalhado(null);

        // Create the InstituicaoEnsino, which fails.
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        restInstituicaoEnsinoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinos() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList
        restInstituicaoEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instituicaoEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].logotipoContentType").value(hasItem(DEFAULT_LOGOTIPO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logotipo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOTIPO))))
            .andExpect(jsonPath("$.[*].unidadeOrganica").value(hasItem(DEFAULT_UNIDADE_ORGANICA)))
            .andExpect(jsonPath("$.[*].nomeFiscal").value(hasItem(DEFAULT_NOME_FISCAL)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].cae").value(hasItem(DEFAULT_CAE)))
            .andExpect(jsonPath("$.[*].niss").value(hasItem(DEFAULT_NISS)))
            .andExpect(jsonPath("$.[*].fundador").value(hasItem(DEFAULT_FUNDADOR)))
            .andExpect(jsonPath("$.[*].fundacao").value(hasItem(DEFAULT_FUNDACAO.toString())))
            .andExpect(jsonPath("$.[*].dimensao").value(hasItem(DEFAULT_DIMENSAO)))
            .andExpect(jsonPath("$.[*].slogam").value(hasItem(DEFAULT_SLOGAM)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].telemovel").value(hasItem(DEFAULT_TELEMOVEL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].enderecoDetalhado").value(hasItem(DEFAULT_ENDERECO_DETALHADO)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].isComparticipada").value(hasItem(DEFAULT_IS_COMPARTICIPADA.booleanValue())))
            .andExpect(jsonPath("$.[*].termosCompromissos").value(hasItem(DEFAULT_TERMOS_COMPROMISSOS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInstituicaoEnsinosWithEagerRelationshipsIsEnabled() throws Exception {
        when(instituicaoEnsinoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInstituicaoEnsinoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(instituicaoEnsinoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInstituicaoEnsinosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(instituicaoEnsinoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInstituicaoEnsinoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(instituicaoEnsinoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInstituicaoEnsino() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get the instituicaoEnsino
        restInstituicaoEnsinoMockMvc
            .perform(get(ENTITY_API_URL_ID, instituicaoEnsino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instituicaoEnsino.getId().intValue()))
            .andExpect(jsonPath("$.logotipoContentType").value(DEFAULT_LOGOTIPO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logotipo").value(Base64Utils.encodeToString(DEFAULT_LOGOTIPO)))
            .andExpect(jsonPath("$.unidadeOrganica").value(DEFAULT_UNIDADE_ORGANICA))
            .andExpect(jsonPath("$.nomeFiscal").value(DEFAULT_NOME_FISCAL))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.cae").value(DEFAULT_CAE))
            .andExpect(jsonPath("$.niss").value(DEFAULT_NISS))
            .andExpect(jsonPath("$.fundador").value(DEFAULT_FUNDADOR))
            .andExpect(jsonPath("$.fundacao").value(DEFAULT_FUNDACAO.toString()))
            .andExpect(jsonPath("$.dimensao").value(DEFAULT_DIMENSAO))
            .andExpect(jsonPath("$.slogam").value(DEFAULT_SLOGAM))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.telemovel").value(DEFAULT_TELEMOVEL))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL))
            .andExpect(jsonPath("$.enderecoDetalhado").value(DEFAULT_ENDERECO_DETALHADO))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.isComparticipada").value(DEFAULT_IS_COMPARTICIPADA.booleanValue()))
            .andExpect(jsonPath("$.termosCompromissos").value(DEFAULT_TERMOS_COMPROMISSOS.toString()));
    }

    @Test
    @Transactional
    void getInstituicaoEnsinosByIdFiltering() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        Long id = instituicaoEnsino.getId();

        defaultInstituicaoEnsinoShouldBeFound("id.equals=" + id);
        defaultInstituicaoEnsinoShouldNotBeFound("id.notEquals=" + id);

        defaultInstituicaoEnsinoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInstituicaoEnsinoShouldNotBeFound("id.greaterThan=" + id);

        defaultInstituicaoEnsinoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInstituicaoEnsinoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByUnidadeOrganicaIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where unidadeOrganica equals to DEFAULT_UNIDADE_ORGANICA
        defaultInstituicaoEnsinoShouldBeFound("unidadeOrganica.equals=" + DEFAULT_UNIDADE_ORGANICA);

        // Get all the instituicaoEnsinoList where unidadeOrganica equals to UPDATED_UNIDADE_ORGANICA
        defaultInstituicaoEnsinoShouldNotBeFound("unidadeOrganica.equals=" + UPDATED_UNIDADE_ORGANICA);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByUnidadeOrganicaIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where unidadeOrganica in DEFAULT_UNIDADE_ORGANICA or UPDATED_UNIDADE_ORGANICA
        defaultInstituicaoEnsinoShouldBeFound("unidadeOrganica.in=" + DEFAULT_UNIDADE_ORGANICA + "," + UPDATED_UNIDADE_ORGANICA);

        // Get all the instituicaoEnsinoList where unidadeOrganica equals to UPDATED_UNIDADE_ORGANICA
        defaultInstituicaoEnsinoShouldNotBeFound("unidadeOrganica.in=" + UPDATED_UNIDADE_ORGANICA);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByUnidadeOrganicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where unidadeOrganica is not null
        defaultInstituicaoEnsinoShouldBeFound("unidadeOrganica.specified=true");

        // Get all the instituicaoEnsinoList where unidadeOrganica is null
        defaultInstituicaoEnsinoShouldNotBeFound("unidadeOrganica.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByUnidadeOrganicaContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where unidadeOrganica contains DEFAULT_UNIDADE_ORGANICA
        defaultInstituicaoEnsinoShouldBeFound("unidadeOrganica.contains=" + DEFAULT_UNIDADE_ORGANICA);

        // Get all the instituicaoEnsinoList where unidadeOrganica contains UPDATED_UNIDADE_ORGANICA
        defaultInstituicaoEnsinoShouldNotBeFound("unidadeOrganica.contains=" + UPDATED_UNIDADE_ORGANICA);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByUnidadeOrganicaNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where unidadeOrganica does not contain DEFAULT_UNIDADE_ORGANICA
        defaultInstituicaoEnsinoShouldNotBeFound("unidadeOrganica.doesNotContain=" + DEFAULT_UNIDADE_ORGANICA);

        // Get all the instituicaoEnsinoList where unidadeOrganica does not contain UPDATED_UNIDADE_ORGANICA
        defaultInstituicaoEnsinoShouldBeFound("unidadeOrganica.doesNotContain=" + UPDATED_UNIDADE_ORGANICA);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNomeFiscalIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where nomeFiscal equals to DEFAULT_NOME_FISCAL
        defaultInstituicaoEnsinoShouldBeFound("nomeFiscal.equals=" + DEFAULT_NOME_FISCAL);

        // Get all the instituicaoEnsinoList where nomeFiscal equals to UPDATED_NOME_FISCAL
        defaultInstituicaoEnsinoShouldNotBeFound("nomeFiscal.equals=" + UPDATED_NOME_FISCAL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNomeFiscalIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where nomeFiscal in DEFAULT_NOME_FISCAL or UPDATED_NOME_FISCAL
        defaultInstituicaoEnsinoShouldBeFound("nomeFiscal.in=" + DEFAULT_NOME_FISCAL + "," + UPDATED_NOME_FISCAL);

        // Get all the instituicaoEnsinoList where nomeFiscal equals to UPDATED_NOME_FISCAL
        defaultInstituicaoEnsinoShouldNotBeFound("nomeFiscal.in=" + UPDATED_NOME_FISCAL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNomeFiscalIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where nomeFiscal is not null
        defaultInstituicaoEnsinoShouldBeFound("nomeFiscal.specified=true");

        // Get all the instituicaoEnsinoList where nomeFiscal is null
        defaultInstituicaoEnsinoShouldNotBeFound("nomeFiscal.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNomeFiscalContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where nomeFiscal contains DEFAULT_NOME_FISCAL
        defaultInstituicaoEnsinoShouldBeFound("nomeFiscal.contains=" + DEFAULT_NOME_FISCAL);

        // Get all the instituicaoEnsinoList where nomeFiscal contains UPDATED_NOME_FISCAL
        defaultInstituicaoEnsinoShouldNotBeFound("nomeFiscal.contains=" + UPDATED_NOME_FISCAL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNomeFiscalNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where nomeFiscal does not contain DEFAULT_NOME_FISCAL
        defaultInstituicaoEnsinoShouldNotBeFound("nomeFiscal.doesNotContain=" + DEFAULT_NOME_FISCAL);

        // Get all the instituicaoEnsinoList where nomeFiscal does not contain UPDATED_NOME_FISCAL
        defaultInstituicaoEnsinoShouldBeFound("nomeFiscal.doesNotContain=" + UPDATED_NOME_FISCAL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where numero equals to DEFAULT_NUMERO
        defaultInstituicaoEnsinoShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the instituicaoEnsinoList where numero equals to UPDATED_NUMERO
        defaultInstituicaoEnsinoShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultInstituicaoEnsinoShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the instituicaoEnsinoList where numero equals to UPDATED_NUMERO
        defaultInstituicaoEnsinoShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where numero is not null
        defaultInstituicaoEnsinoShouldBeFound("numero.specified=true");

        // Get all the instituicaoEnsinoList where numero is null
        defaultInstituicaoEnsinoShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNumeroContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where numero contains DEFAULT_NUMERO
        defaultInstituicaoEnsinoShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the instituicaoEnsinoList where numero contains UPDATED_NUMERO
        defaultInstituicaoEnsinoShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where numero does not contain DEFAULT_NUMERO
        defaultInstituicaoEnsinoShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the instituicaoEnsinoList where numero does not contain UPDATED_NUMERO
        defaultInstituicaoEnsinoShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNifIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where nif equals to DEFAULT_NIF
        defaultInstituicaoEnsinoShouldBeFound("nif.equals=" + DEFAULT_NIF);

        // Get all the instituicaoEnsinoList where nif equals to UPDATED_NIF
        defaultInstituicaoEnsinoShouldNotBeFound("nif.equals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNifIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where nif in DEFAULT_NIF or UPDATED_NIF
        defaultInstituicaoEnsinoShouldBeFound("nif.in=" + DEFAULT_NIF + "," + UPDATED_NIF);

        // Get all the instituicaoEnsinoList where nif equals to UPDATED_NIF
        defaultInstituicaoEnsinoShouldNotBeFound("nif.in=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNifIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where nif is not null
        defaultInstituicaoEnsinoShouldBeFound("nif.specified=true");

        // Get all the instituicaoEnsinoList where nif is null
        defaultInstituicaoEnsinoShouldNotBeFound("nif.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNifContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where nif contains DEFAULT_NIF
        defaultInstituicaoEnsinoShouldBeFound("nif.contains=" + DEFAULT_NIF);

        // Get all the instituicaoEnsinoList where nif contains UPDATED_NIF
        defaultInstituicaoEnsinoShouldNotBeFound("nif.contains=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNifNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where nif does not contain DEFAULT_NIF
        defaultInstituicaoEnsinoShouldNotBeFound("nif.doesNotContain=" + DEFAULT_NIF);

        // Get all the instituicaoEnsinoList where nif does not contain UPDATED_NIF
        defaultInstituicaoEnsinoShouldBeFound("nif.doesNotContain=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByCaeIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where cae equals to DEFAULT_CAE
        defaultInstituicaoEnsinoShouldBeFound("cae.equals=" + DEFAULT_CAE);

        // Get all the instituicaoEnsinoList where cae equals to UPDATED_CAE
        defaultInstituicaoEnsinoShouldNotBeFound("cae.equals=" + UPDATED_CAE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByCaeIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where cae in DEFAULT_CAE or UPDATED_CAE
        defaultInstituicaoEnsinoShouldBeFound("cae.in=" + DEFAULT_CAE + "," + UPDATED_CAE);

        // Get all the instituicaoEnsinoList where cae equals to UPDATED_CAE
        defaultInstituicaoEnsinoShouldNotBeFound("cae.in=" + UPDATED_CAE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByCaeIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where cae is not null
        defaultInstituicaoEnsinoShouldBeFound("cae.specified=true");

        // Get all the instituicaoEnsinoList where cae is null
        defaultInstituicaoEnsinoShouldNotBeFound("cae.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByCaeContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where cae contains DEFAULT_CAE
        defaultInstituicaoEnsinoShouldBeFound("cae.contains=" + DEFAULT_CAE);

        // Get all the instituicaoEnsinoList where cae contains UPDATED_CAE
        defaultInstituicaoEnsinoShouldNotBeFound("cae.contains=" + UPDATED_CAE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByCaeNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where cae does not contain DEFAULT_CAE
        defaultInstituicaoEnsinoShouldNotBeFound("cae.doesNotContain=" + DEFAULT_CAE);

        // Get all the instituicaoEnsinoList where cae does not contain UPDATED_CAE
        defaultInstituicaoEnsinoShouldBeFound("cae.doesNotContain=" + UPDATED_CAE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNissIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where niss equals to DEFAULT_NISS
        defaultInstituicaoEnsinoShouldBeFound("niss.equals=" + DEFAULT_NISS);

        // Get all the instituicaoEnsinoList where niss equals to UPDATED_NISS
        defaultInstituicaoEnsinoShouldNotBeFound("niss.equals=" + UPDATED_NISS);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNissIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where niss in DEFAULT_NISS or UPDATED_NISS
        defaultInstituicaoEnsinoShouldBeFound("niss.in=" + DEFAULT_NISS + "," + UPDATED_NISS);

        // Get all the instituicaoEnsinoList where niss equals to UPDATED_NISS
        defaultInstituicaoEnsinoShouldNotBeFound("niss.in=" + UPDATED_NISS);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNissIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where niss is not null
        defaultInstituicaoEnsinoShouldBeFound("niss.specified=true");

        // Get all the instituicaoEnsinoList where niss is null
        defaultInstituicaoEnsinoShouldNotBeFound("niss.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNissContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where niss contains DEFAULT_NISS
        defaultInstituicaoEnsinoShouldBeFound("niss.contains=" + DEFAULT_NISS);

        // Get all the instituicaoEnsinoList where niss contains UPDATED_NISS
        defaultInstituicaoEnsinoShouldNotBeFound("niss.contains=" + UPDATED_NISS);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByNissNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where niss does not contain DEFAULT_NISS
        defaultInstituicaoEnsinoShouldNotBeFound("niss.doesNotContain=" + DEFAULT_NISS);

        // Get all the instituicaoEnsinoList where niss does not contain UPDATED_NISS
        defaultInstituicaoEnsinoShouldBeFound("niss.doesNotContain=" + UPDATED_NISS);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundadorIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundador equals to DEFAULT_FUNDADOR
        defaultInstituicaoEnsinoShouldBeFound("fundador.equals=" + DEFAULT_FUNDADOR);

        // Get all the instituicaoEnsinoList where fundador equals to UPDATED_FUNDADOR
        defaultInstituicaoEnsinoShouldNotBeFound("fundador.equals=" + UPDATED_FUNDADOR);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundadorIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundador in DEFAULT_FUNDADOR or UPDATED_FUNDADOR
        defaultInstituicaoEnsinoShouldBeFound("fundador.in=" + DEFAULT_FUNDADOR + "," + UPDATED_FUNDADOR);

        // Get all the instituicaoEnsinoList where fundador equals to UPDATED_FUNDADOR
        defaultInstituicaoEnsinoShouldNotBeFound("fundador.in=" + UPDATED_FUNDADOR);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundadorIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundador is not null
        defaultInstituicaoEnsinoShouldBeFound("fundador.specified=true");

        // Get all the instituicaoEnsinoList where fundador is null
        defaultInstituicaoEnsinoShouldNotBeFound("fundador.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundadorContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundador contains DEFAULT_FUNDADOR
        defaultInstituicaoEnsinoShouldBeFound("fundador.contains=" + DEFAULT_FUNDADOR);

        // Get all the instituicaoEnsinoList where fundador contains UPDATED_FUNDADOR
        defaultInstituicaoEnsinoShouldNotBeFound("fundador.contains=" + UPDATED_FUNDADOR);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundadorNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundador does not contain DEFAULT_FUNDADOR
        defaultInstituicaoEnsinoShouldNotBeFound("fundador.doesNotContain=" + DEFAULT_FUNDADOR);

        // Get all the instituicaoEnsinoList where fundador does not contain UPDATED_FUNDADOR
        defaultInstituicaoEnsinoShouldBeFound("fundador.doesNotContain=" + UPDATED_FUNDADOR);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundacao equals to DEFAULT_FUNDACAO
        defaultInstituicaoEnsinoShouldBeFound("fundacao.equals=" + DEFAULT_FUNDACAO);

        // Get all the instituicaoEnsinoList where fundacao equals to UPDATED_FUNDACAO
        defaultInstituicaoEnsinoShouldNotBeFound("fundacao.equals=" + UPDATED_FUNDACAO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundacaoIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundacao in DEFAULT_FUNDACAO or UPDATED_FUNDACAO
        defaultInstituicaoEnsinoShouldBeFound("fundacao.in=" + DEFAULT_FUNDACAO + "," + UPDATED_FUNDACAO);

        // Get all the instituicaoEnsinoList where fundacao equals to UPDATED_FUNDACAO
        defaultInstituicaoEnsinoShouldNotBeFound("fundacao.in=" + UPDATED_FUNDACAO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundacao is not null
        defaultInstituicaoEnsinoShouldBeFound("fundacao.specified=true");

        // Get all the instituicaoEnsinoList where fundacao is null
        defaultInstituicaoEnsinoShouldNotBeFound("fundacao.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundacao is greater than or equal to DEFAULT_FUNDACAO
        defaultInstituicaoEnsinoShouldBeFound("fundacao.greaterThanOrEqual=" + DEFAULT_FUNDACAO);

        // Get all the instituicaoEnsinoList where fundacao is greater than or equal to UPDATED_FUNDACAO
        defaultInstituicaoEnsinoShouldNotBeFound("fundacao.greaterThanOrEqual=" + UPDATED_FUNDACAO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundacao is less than or equal to DEFAULT_FUNDACAO
        defaultInstituicaoEnsinoShouldBeFound("fundacao.lessThanOrEqual=" + DEFAULT_FUNDACAO);

        // Get all the instituicaoEnsinoList where fundacao is less than or equal to SMALLER_FUNDACAO
        defaultInstituicaoEnsinoShouldNotBeFound("fundacao.lessThanOrEqual=" + SMALLER_FUNDACAO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundacao is less than DEFAULT_FUNDACAO
        defaultInstituicaoEnsinoShouldNotBeFound("fundacao.lessThan=" + DEFAULT_FUNDACAO);

        // Get all the instituicaoEnsinoList where fundacao is less than UPDATED_FUNDACAO
        defaultInstituicaoEnsinoShouldBeFound("fundacao.lessThan=" + UPDATED_FUNDACAO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByFundacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where fundacao is greater than DEFAULT_FUNDACAO
        defaultInstituicaoEnsinoShouldNotBeFound("fundacao.greaterThan=" + DEFAULT_FUNDACAO);

        // Get all the instituicaoEnsinoList where fundacao is greater than SMALLER_FUNDACAO
        defaultInstituicaoEnsinoShouldBeFound("fundacao.greaterThan=" + SMALLER_FUNDACAO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByDimensaoIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where dimensao equals to DEFAULT_DIMENSAO
        defaultInstituicaoEnsinoShouldBeFound("dimensao.equals=" + DEFAULT_DIMENSAO);

        // Get all the instituicaoEnsinoList where dimensao equals to UPDATED_DIMENSAO
        defaultInstituicaoEnsinoShouldNotBeFound("dimensao.equals=" + UPDATED_DIMENSAO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByDimensaoIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where dimensao in DEFAULT_DIMENSAO or UPDATED_DIMENSAO
        defaultInstituicaoEnsinoShouldBeFound("dimensao.in=" + DEFAULT_DIMENSAO + "," + UPDATED_DIMENSAO);

        // Get all the instituicaoEnsinoList where dimensao equals to UPDATED_DIMENSAO
        defaultInstituicaoEnsinoShouldNotBeFound("dimensao.in=" + UPDATED_DIMENSAO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByDimensaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where dimensao is not null
        defaultInstituicaoEnsinoShouldBeFound("dimensao.specified=true");

        // Get all the instituicaoEnsinoList where dimensao is null
        defaultInstituicaoEnsinoShouldNotBeFound("dimensao.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByDimensaoContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where dimensao contains DEFAULT_DIMENSAO
        defaultInstituicaoEnsinoShouldBeFound("dimensao.contains=" + DEFAULT_DIMENSAO);

        // Get all the instituicaoEnsinoList where dimensao contains UPDATED_DIMENSAO
        defaultInstituicaoEnsinoShouldNotBeFound("dimensao.contains=" + UPDATED_DIMENSAO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByDimensaoNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where dimensao does not contain DEFAULT_DIMENSAO
        defaultInstituicaoEnsinoShouldNotBeFound("dimensao.doesNotContain=" + DEFAULT_DIMENSAO);

        // Get all the instituicaoEnsinoList where dimensao does not contain UPDATED_DIMENSAO
        defaultInstituicaoEnsinoShouldBeFound("dimensao.doesNotContain=" + UPDATED_DIMENSAO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosBySlogamIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where slogam equals to DEFAULT_SLOGAM
        defaultInstituicaoEnsinoShouldBeFound("slogam.equals=" + DEFAULT_SLOGAM);

        // Get all the instituicaoEnsinoList where slogam equals to UPDATED_SLOGAM
        defaultInstituicaoEnsinoShouldNotBeFound("slogam.equals=" + UPDATED_SLOGAM);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosBySlogamIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where slogam in DEFAULT_SLOGAM or UPDATED_SLOGAM
        defaultInstituicaoEnsinoShouldBeFound("slogam.in=" + DEFAULT_SLOGAM + "," + UPDATED_SLOGAM);

        // Get all the instituicaoEnsinoList where slogam equals to UPDATED_SLOGAM
        defaultInstituicaoEnsinoShouldNotBeFound("slogam.in=" + UPDATED_SLOGAM);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosBySlogamIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where slogam is not null
        defaultInstituicaoEnsinoShouldBeFound("slogam.specified=true");

        // Get all the instituicaoEnsinoList where slogam is null
        defaultInstituicaoEnsinoShouldNotBeFound("slogam.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosBySlogamContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where slogam contains DEFAULT_SLOGAM
        defaultInstituicaoEnsinoShouldBeFound("slogam.contains=" + DEFAULT_SLOGAM);

        // Get all the instituicaoEnsinoList where slogam contains UPDATED_SLOGAM
        defaultInstituicaoEnsinoShouldNotBeFound("slogam.contains=" + UPDATED_SLOGAM);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosBySlogamNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where slogam does not contain DEFAULT_SLOGAM
        defaultInstituicaoEnsinoShouldNotBeFound("slogam.doesNotContain=" + DEFAULT_SLOGAM);

        // Get all the instituicaoEnsinoList where slogam does not contain UPDATED_SLOGAM
        defaultInstituicaoEnsinoShouldBeFound("slogam.doesNotContain=" + UPDATED_SLOGAM);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where telefone equals to DEFAULT_TELEFONE
        defaultInstituicaoEnsinoShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the instituicaoEnsinoList where telefone equals to UPDATED_TELEFONE
        defaultInstituicaoEnsinoShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultInstituicaoEnsinoShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the instituicaoEnsinoList where telefone equals to UPDATED_TELEFONE
        defaultInstituicaoEnsinoShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where telefone is not null
        defaultInstituicaoEnsinoShouldBeFound("telefone.specified=true");

        // Get all the instituicaoEnsinoList where telefone is null
        defaultInstituicaoEnsinoShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where telefone contains DEFAULT_TELEFONE
        defaultInstituicaoEnsinoShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the instituicaoEnsinoList where telefone contains UPDATED_TELEFONE
        defaultInstituicaoEnsinoShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where telefone does not contain DEFAULT_TELEFONE
        defaultInstituicaoEnsinoShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the instituicaoEnsinoList where telefone does not contain UPDATED_TELEFONE
        defaultInstituicaoEnsinoShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTelemovelIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where telemovel equals to DEFAULT_TELEMOVEL
        defaultInstituicaoEnsinoShouldBeFound("telemovel.equals=" + DEFAULT_TELEMOVEL);

        // Get all the instituicaoEnsinoList where telemovel equals to UPDATED_TELEMOVEL
        defaultInstituicaoEnsinoShouldNotBeFound("telemovel.equals=" + UPDATED_TELEMOVEL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTelemovelIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where telemovel in DEFAULT_TELEMOVEL or UPDATED_TELEMOVEL
        defaultInstituicaoEnsinoShouldBeFound("telemovel.in=" + DEFAULT_TELEMOVEL + "," + UPDATED_TELEMOVEL);

        // Get all the instituicaoEnsinoList where telemovel equals to UPDATED_TELEMOVEL
        defaultInstituicaoEnsinoShouldNotBeFound("telemovel.in=" + UPDATED_TELEMOVEL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTelemovelIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where telemovel is not null
        defaultInstituicaoEnsinoShouldBeFound("telemovel.specified=true");

        // Get all the instituicaoEnsinoList where telemovel is null
        defaultInstituicaoEnsinoShouldNotBeFound("telemovel.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTelemovelContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where telemovel contains DEFAULT_TELEMOVEL
        defaultInstituicaoEnsinoShouldBeFound("telemovel.contains=" + DEFAULT_TELEMOVEL);

        // Get all the instituicaoEnsinoList where telemovel contains UPDATED_TELEMOVEL
        defaultInstituicaoEnsinoShouldNotBeFound("telemovel.contains=" + UPDATED_TELEMOVEL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTelemovelNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where telemovel does not contain DEFAULT_TELEMOVEL
        defaultInstituicaoEnsinoShouldNotBeFound("telemovel.doesNotContain=" + DEFAULT_TELEMOVEL);

        // Get all the instituicaoEnsinoList where telemovel does not contain UPDATED_TELEMOVEL
        defaultInstituicaoEnsinoShouldBeFound("telemovel.doesNotContain=" + UPDATED_TELEMOVEL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where email equals to DEFAULT_EMAIL
        defaultInstituicaoEnsinoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the instituicaoEnsinoList where email equals to UPDATED_EMAIL
        defaultInstituicaoEnsinoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultInstituicaoEnsinoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the instituicaoEnsinoList where email equals to UPDATED_EMAIL
        defaultInstituicaoEnsinoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where email is not null
        defaultInstituicaoEnsinoShouldBeFound("email.specified=true");

        // Get all the instituicaoEnsinoList where email is null
        defaultInstituicaoEnsinoShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByEmailContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where email contains DEFAULT_EMAIL
        defaultInstituicaoEnsinoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the instituicaoEnsinoList where email contains UPDATED_EMAIL
        defaultInstituicaoEnsinoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where email does not contain DEFAULT_EMAIL
        defaultInstituicaoEnsinoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the instituicaoEnsinoList where email does not contain UPDATED_EMAIL
        defaultInstituicaoEnsinoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where website equals to DEFAULT_WEBSITE
        defaultInstituicaoEnsinoShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the instituicaoEnsinoList where website equals to UPDATED_WEBSITE
        defaultInstituicaoEnsinoShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultInstituicaoEnsinoShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the instituicaoEnsinoList where website equals to UPDATED_WEBSITE
        defaultInstituicaoEnsinoShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where website is not null
        defaultInstituicaoEnsinoShouldBeFound("website.specified=true");

        // Get all the instituicaoEnsinoList where website is null
        defaultInstituicaoEnsinoShouldNotBeFound("website.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByWebsiteContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where website contains DEFAULT_WEBSITE
        defaultInstituicaoEnsinoShouldBeFound("website.contains=" + DEFAULT_WEBSITE);

        // Get all the instituicaoEnsinoList where website contains UPDATED_WEBSITE
        defaultInstituicaoEnsinoShouldNotBeFound("website.contains=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByWebsiteNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where website does not contain DEFAULT_WEBSITE
        defaultInstituicaoEnsinoShouldNotBeFound("website.doesNotContain=" + DEFAULT_WEBSITE);

        // Get all the instituicaoEnsinoList where website does not contain UPDATED_WEBSITE
        defaultInstituicaoEnsinoShouldBeFound("website.doesNotContain=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByCodigoPostalIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where codigoPostal equals to DEFAULT_CODIGO_POSTAL
        defaultInstituicaoEnsinoShouldBeFound("codigoPostal.equals=" + DEFAULT_CODIGO_POSTAL);

        // Get all the instituicaoEnsinoList where codigoPostal equals to UPDATED_CODIGO_POSTAL
        defaultInstituicaoEnsinoShouldNotBeFound("codigoPostal.equals=" + UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByCodigoPostalIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where codigoPostal in DEFAULT_CODIGO_POSTAL or UPDATED_CODIGO_POSTAL
        defaultInstituicaoEnsinoShouldBeFound("codigoPostal.in=" + DEFAULT_CODIGO_POSTAL + "," + UPDATED_CODIGO_POSTAL);

        // Get all the instituicaoEnsinoList where codigoPostal equals to UPDATED_CODIGO_POSTAL
        defaultInstituicaoEnsinoShouldNotBeFound("codigoPostal.in=" + UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByCodigoPostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where codigoPostal is not null
        defaultInstituicaoEnsinoShouldBeFound("codigoPostal.specified=true");

        // Get all the instituicaoEnsinoList where codigoPostal is null
        defaultInstituicaoEnsinoShouldNotBeFound("codigoPostal.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByCodigoPostalContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where codigoPostal contains DEFAULT_CODIGO_POSTAL
        defaultInstituicaoEnsinoShouldBeFound("codigoPostal.contains=" + DEFAULT_CODIGO_POSTAL);

        // Get all the instituicaoEnsinoList where codigoPostal contains UPDATED_CODIGO_POSTAL
        defaultInstituicaoEnsinoShouldNotBeFound("codigoPostal.contains=" + UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByCodigoPostalNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where codigoPostal does not contain DEFAULT_CODIGO_POSTAL
        defaultInstituicaoEnsinoShouldNotBeFound("codigoPostal.doesNotContain=" + DEFAULT_CODIGO_POSTAL);

        // Get all the instituicaoEnsinoList where codigoPostal does not contain UPDATED_CODIGO_POSTAL
        defaultInstituicaoEnsinoShouldBeFound("codigoPostal.doesNotContain=" + UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByEnderecoDetalhadoIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where enderecoDetalhado equals to DEFAULT_ENDERECO_DETALHADO
        defaultInstituicaoEnsinoShouldBeFound("enderecoDetalhado.equals=" + DEFAULT_ENDERECO_DETALHADO);

        // Get all the instituicaoEnsinoList where enderecoDetalhado equals to UPDATED_ENDERECO_DETALHADO
        defaultInstituicaoEnsinoShouldNotBeFound("enderecoDetalhado.equals=" + UPDATED_ENDERECO_DETALHADO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByEnderecoDetalhadoIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where enderecoDetalhado in DEFAULT_ENDERECO_DETALHADO or UPDATED_ENDERECO_DETALHADO
        defaultInstituicaoEnsinoShouldBeFound("enderecoDetalhado.in=" + DEFAULT_ENDERECO_DETALHADO + "," + UPDATED_ENDERECO_DETALHADO);

        // Get all the instituicaoEnsinoList where enderecoDetalhado equals to UPDATED_ENDERECO_DETALHADO
        defaultInstituicaoEnsinoShouldNotBeFound("enderecoDetalhado.in=" + UPDATED_ENDERECO_DETALHADO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByEnderecoDetalhadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where enderecoDetalhado is not null
        defaultInstituicaoEnsinoShouldBeFound("enderecoDetalhado.specified=true");

        // Get all the instituicaoEnsinoList where enderecoDetalhado is null
        defaultInstituicaoEnsinoShouldNotBeFound("enderecoDetalhado.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByEnderecoDetalhadoContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where enderecoDetalhado contains DEFAULT_ENDERECO_DETALHADO
        defaultInstituicaoEnsinoShouldBeFound("enderecoDetalhado.contains=" + DEFAULT_ENDERECO_DETALHADO);

        // Get all the instituicaoEnsinoList where enderecoDetalhado contains UPDATED_ENDERECO_DETALHADO
        defaultInstituicaoEnsinoShouldNotBeFound("enderecoDetalhado.contains=" + UPDATED_ENDERECO_DETALHADO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByEnderecoDetalhadoNotContainsSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where enderecoDetalhado does not contain DEFAULT_ENDERECO_DETALHADO
        defaultInstituicaoEnsinoShouldNotBeFound("enderecoDetalhado.doesNotContain=" + DEFAULT_ENDERECO_DETALHADO);

        // Get all the instituicaoEnsinoList where enderecoDetalhado does not contain UPDATED_ENDERECO_DETALHADO
        defaultInstituicaoEnsinoShouldBeFound("enderecoDetalhado.doesNotContain=" + UPDATED_ENDERECO_DETALHADO);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where latitude equals to DEFAULT_LATITUDE
        defaultInstituicaoEnsinoShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the instituicaoEnsinoList where latitude equals to UPDATED_LATITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultInstituicaoEnsinoShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the instituicaoEnsinoList where latitude equals to UPDATED_LATITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where latitude is not null
        defaultInstituicaoEnsinoShouldBeFound("latitude.specified=true");

        // Get all the instituicaoEnsinoList where latitude is null
        defaultInstituicaoEnsinoShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultInstituicaoEnsinoShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the instituicaoEnsinoList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultInstituicaoEnsinoShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the instituicaoEnsinoList where latitude is less than or equal to SMALLER_LATITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where latitude is less than DEFAULT_LATITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the instituicaoEnsinoList where latitude is less than UPDATED_LATITUDE
        defaultInstituicaoEnsinoShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where latitude is greater than DEFAULT_LATITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the instituicaoEnsinoList where latitude is greater than SMALLER_LATITUDE
        defaultInstituicaoEnsinoShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where longitude equals to DEFAULT_LONGITUDE
        defaultInstituicaoEnsinoShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the instituicaoEnsinoList where longitude equals to UPDATED_LONGITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultInstituicaoEnsinoShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the instituicaoEnsinoList where longitude equals to UPDATED_LONGITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where longitude is not null
        defaultInstituicaoEnsinoShouldBeFound("longitude.specified=true");

        // Get all the instituicaoEnsinoList where longitude is null
        defaultInstituicaoEnsinoShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultInstituicaoEnsinoShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the instituicaoEnsinoList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultInstituicaoEnsinoShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the instituicaoEnsinoList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where longitude is less than DEFAULT_LONGITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the instituicaoEnsinoList where longitude is less than UPDATED_LONGITUDE
        defaultInstituicaoEnsinoShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where longitude is greater than DEFAULT_LONGITUDE
        defaultInstituicaoEnsinoShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the instituicaoEnsinoList where longitude is greater than SMALLER_LONGITUDE
        defaultInstituicaoEnsinoShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByIsComparticipadaIsEqualToSomething() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where isComparticipada equals to DEFAULT_IS_COMPARTICIPADA
        defaultInstituicaoEnsinoShouldBeFound("isComparticipada.equals=" + DEFAULT_IS_COMPARTICIPADA);

        // Get all the instituicaoEnsinoList where isComparticipada equals to UPDATED_IS_COMPARTICIPADA
        defaultInstituicaoEnsinoShouldNotBeFound("isComparticipada.equals=" + UPDATED_IS_COMPARTICIPADA);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByIsComparticipadaIsInShouldWork() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where isComparticipada in DEFAULT_IS_COMPARTICIPADA or UPDATED_IS_COMPARTICIPADA
        defaultInstituicaoEnsinoShouldBeFound("isComparticipada.in=" + DEFAULT_IS_COMPARTICIPADA + "," + UPDATED_IS_COMPARTICIPADA);

        // Get all the instituicaoEnsinoList where isComparticipada equals to UPDATED_IS_COMPARTICIPADA
        defaultInstituicaoEnsinoShouldNotBeFound("isComparticipada.in=" + UPDATED_IS_COMPARTICIPADA);
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByIsComparticipadaIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList where isComparticipada is not null
        defaultInstituicaoEnsinoShouldBeFound("isComparticipada.specified=true");

        // Get all the instituicaoEnsinoList where isComparticipada is null
        defaultInstituicaoEnsinoShouldNotBeFound("isComparticipada.specified=false");
    }

    // @Test
    // @Transactional
    // void getAllInstituicaoEnsinosByInstituicaoEnsinoIsEqualToSomething() throws Exception {
    //     InstituicaoEnsino instituicaoEnsino;
    //     if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
    //         instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
    //         instituicaoEnsino = InstituicaoEnsinoResourceIT.createEntity(em);
    //     } else {
    //         instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
    //     }
    //     em.persist(instituicaoEnsino);
    //     em.flush();
    //     instituicaoEnsino.addInstituicaoEnsino(instituicaoEnsino);
    //     instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
    //     Long instituicaoEnsinoId = instituicaoEnsino.getId();

    //     // Get all the instituicaoEnsinoList where instituicaoEnsino equals to instituicaoEnsinoId
    //     defaultInstituicaoEnsinoShouldBeFound("instituicaoEnsinoId.equals=" + instituicaoEnsinoId);

    //     // Get all the instituicaoEnsinoList where instituicaoEnsino equals to (instituicaoEnsinoId + 1)
    //     defaultInstituicaoEnsinoShouldNotBeFound("instituicaoEnsinoId.equals=" + (instituicaoEnsinoId + 1));
    // }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByProvedorNotificacaoIsEqualToSomething() throws Exception {
        ProvedorNotificacao provedorNotificacao;
        if (TestUtil.findAll(em, ProvedorNotificacao.class).isEmpty()) {
            instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
            provedorNotificacao = ProvedorNotificacaoResourceIT.createEntity(em);
        } else {
            provedorNotificacao = TestUtil.findAll(em, ProvedorNotificacao.class).get(0);
        }
        em.persist(provedorNotificacao);
        em.flush();
        instituicaoEnsino.addProvedorNotificacao(provedorNotificacao);
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
        Long provedorNotificacaoId = provedorNotificacao.getId();

        // Get all the instituicaoEnsinoList where provedorNotificacao equals to provedorNotificacaoId
        defaultInstituicaoEnsinoShouldBeFound("provedorNotificacaoId.equals=" + provedorNotificacaoId);

        // Get all the instituicaoEnsinoList where provedorNotificacao equals to (provedorNotificacaoId + 1)
        defaultInstituicaoEnsinoShouldNotBeFound("provedorNotificacaoId.equals=" + (provedorNotificacaoId + 1));
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByCategoriaInstituicaoIsEqualToSomething() throws Exception {
        LookupItem categoriaInstituicao;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
            categoriaInstituicao = LookupItemResourceIT.createEntity(em);
        } else {
            categoriaInstituicao = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(categoriaInstituicao);
        em.flush();
        instituicaoEnsino.setCategoriaInstituicao(categoriaInstituicao);
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
        Long categoriaInstituicaoId = categoriaInstituicao.getId();

        // Get all the instituicaoEnsinoList where categoriaInstituicao equals to categoriaInstituicaoId
        defaultInstituicaoEnsinoShouldBeFound("categoriaInstituicaoId.equals=" + categoriaInstituicaoId);

        // Get all the instituicaoEnsinoList where categoriaInstituicao equals to (categoriaInstituicaoId + 1)
        defaultInstituicaoEnsinoShouldNotBeFound("categoriaInstituicaoId.equals=" + (categoriaInstituicaoId + 1));
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByUnidadePagadoraIsEqualToSomething() throws Exception {
        LookupItem unidadePagadora;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
            unidadePagadora = LookupItemResourceIT.createEntity(em);
        } else {
            unidadePagadora = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(unidadePagadora);
        em.flush();
        instituicaoEnsino.setUnidadePagadora(unidadePagadora);
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
        Long unidadePagadoraId = unidadePagadora.getId();

        // Get all the instituicaoEnsinoList where unidadePagadora equals to unidadePagadoraId
        defaultInstituicaoEnsinoShouldBeFound("unidadePagadoraId.equals=" + unidadePagadoraId);

        // Get all the instituicaoEnsinoList where unidadePagadora equals to (unidadePagadoraId + 1)
        defaultInstituicaoEnsinoShouldNotBeFound("unidadePagadoraId.equals=" + (unidadePagadoraId + 1));
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTipoVinculoIsEqualToSomething() throws Exception {
        LookupItem tipoVinculo;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
            tipoVinculo = LookupItemResourceIT.createEntity(em);
        } else {
            tipoVinculo = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(tipoVinculo);
        em.flush();
        instituicaoEnsino.setTipoVinculo(tipoVinculo);
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
        Long tipoVinculoId = tipoVinculo.getId();

        // Get all the instituicaoEnsinoList where tipoVinculo equals to tipoVinculoId
        defaultInstituicaoEnsinoShouldBeFound("tipoVinculoId.equals=" + tipoVinculoId);

        // Get all the instituicaoEnsinoList where tipoVinculo equals to (tipoVinculoId + 1)
        defaultInstituicaoEnsinoShouldNotBeFound("tipoVinculoId.equals=" + (tipoVinculoId + 1));
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosByTipoInstalacaoIsEqualToSomething() throws Exception {
        LookupItem tipoInstalacao;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
            tipoInstalacao = LookupItemResourceIT.createEntity(em);
        } else {
            tipoInstalacao = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(tipoInstalacao);
        em.flush();
        instituicaoEnsino.setTipoInstalacao(tipoInstalacao);
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
        Long tipoInstalacaoId = tipoInstalacao.getId();

        // Get all the instituicaoEnsinoList where tipoInstalacao equals to tipoInstalacaoId
        defaultInstituicaoEnsinoShouldBeFound("tipoInstalacaoId.equals=" + tipoInstalacaoId);

        // Get all the instituicaoEnsinoList where tipoInstalacao equals to (tipoInstalacaoId + 1)
        defaultInstituicaoEnsinoShouldNotBeFound("tipoInstalacaoId.equals=" + (tipoInstalacaoId + 1));
    }

    @Test
    @Transactional
    void getAllInstituicaoEnsinosBySedeIsEqualToSomething() throws Exception {
        InstituicaoEnsino sede;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
            sede = InstituicaoEnsinoResourceIT.createEntity(em);
        } else {
            sede = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        em.persist(sede);
        em.flush();
        instituicaoEnsino.setSede(sede);
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
        Long sedeId = sede.getId();

        // Get all the instituicaoEnsinoList where sede equals to sedeId
        defaultInstituicaoEnsinoShouldBeFound("sedeId.equals=" + sedeId);

        // Get all the instituicaoEnsinoList where sede equals to (sedeId + 1)
        defaultInstituicaoEnsinoShouldNotBeFound("sedeId.equals=" + (sedeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInstituicaoEnsinoShouldBeFound(String filter) throws Exception {
        restInstituicaoEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instituicaoEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].logotipoContentType").value(hasItem(DEFAULT_LOGOTIPO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logotipo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOTIPO))))
            .andExpect(jsonPath("$.[*].unidadeOrganica").value(hasItem(DEFAULT_UNIDADE_ORGANICA)))
            .andExpect(jsonPath("$.[*].nomeFiscal").value(hasItem(DEFAULT_NOME_FISCAL)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].cae").value(hasItem(DEFAULT_CAE)))
            .andExpect(jsonPath("$.[*].niss").value(hasItem(DEFAULT_NISS)))
            .andExpect(jsonPath("$.[*].fundador").value(hasItem(DEFAULT_FUNDADOR)))
            .andExpect(jsonPath("$.[*].fundacao").value(hasItem(DEFAULT_FUNDACAO.toString())))
            .andExpect(jsonPath("$.[*].dimensao").value(hasItem(DEFAULT_DIMENSAO)))
            .andExpect(jsonPath("$.[*].slogam").value(hasItem(DEFAULT_SLOGAM)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].telemovel").value(hasItem(DEFAULT_TELEMOVEL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].enderecoDetalhado").value(hasItem(DEFAULT_ENDERECO_DETALHADO)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].isComparticipada").value(hasItem(DEFAULT_IS_COMPARTICIPADA.booleanValue())))
            .andExpect(jsonPath("$.[*].termosCompromissos").value(hasItem(DEFAULT_TERMOS_COMPROMISSOS.toString())));

        // Check, that the count call also returns 1
        restInstituicaoEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInstituicaoEnsinoShouldNotBeFound(String filter) throws Exception {
        restInstituicaoEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInstituicaoEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInstituicaoEnsino() throws Exception {
        // Get the instituicaoEnsino
        restInstituicaoEnsinoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInstituicaoEnsino() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        int databaseSizeBeforeUpdate = instituicaoEnsinoRepository.findAll().size();

        // Update the instituicaoEnsino
        InstituicaoEnsino updatedInstituicaoEnsino = instituicaoEnsinoRepository.findById(instituicaoEnsino.getId()).get();
        // Disconnect from session so that the updates on updatedInstituicaoEnsino are not directly saved in db
        em.detach(updatedInstituicaoEnsino);
        updatedInstituicaoEnsino
            .logotipo(UPDATED_LOGOTIPO)
            .logotipoContentType(UPDATED_LOGOTIPO_CONTENT_TYPE)
            .unidadeOrganica(UPDATED_UNIDADE_ORGANICA)
            .nomeFiscal(UPDATED_NOME_FISCAL)
            .numero(UPDATED_NUMERO)
            .nif(UPDATED_NIF)
            .cae(UPDATED_CAE)
            .niss(UPDATED_NISS)
            .fundador(UPDATED_FUNDADOR)
            .fundacao(UPDATED_FUNDACAO)
            .dimensao(UPDATED_DIMENSAO)
            .slogam(UPDATED_SLOGAM)
            .telefone(UPDATED_TELEFONE)
            .telemovel(UPDATED_TELEMOVEL)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .enderecoDetalhado(UPDATED_ENDERECO_DETALHADO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .descricao(UPDATED_DESCRICAO)
            .isComparticipada(UPDATED_IS_COMPARTICIPADA)
            .termosCompromissos(UPDATED_TERMOS_COMPROMISSOS);
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(updatedInstituicaoEnsino);

        restInstituicaoEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, instituicaoEnsinoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isOk());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
        InstituicaoEnsino testInstituicaoEnsino = instituicaoEnsinoList.get(instituicaoEnsinoList.size() - 1);
        assertThat(testInstituicaoEnsino.getLogotipo()).isEqualTo(UPDATED_LOGOTIPO);
        assertThat(testInstituicaoEnsino.getLogotipoContentType()).isEqualTo(UPDATED_LOGOTIPO_CONTENT_TYPE);
        assertThat(testInstituicaoEnsino.getUnidadeOrganica()).isEqualTo(UPDATED_UNIDADE_ORGANICA);
        assertThat(testInstituicaoEnsino.getNomeFiscal()).isEqualTo(UPDATED_NOME_FISCAL);
        assertThat(testInstituicaoEnsino.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testInstituicaoEnsino.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testInstituicaoEnsino.getCae()).isEqualTo(UPDATED_CAE);
        assertThat(testInstituicaoEnsino.getNiss()).isEqualTo(UPDATED_NISS);
        assertThat(testInstituicaoEnsino.getFundador()).isEqualTo(UPDATED_FUNDADOR);
        assertThat(testInstituicaoEnsino.getFundacao()).isEqualTo(UPDATED_FUNDACAO);
        assertThat(testInstituicaoEnsino.getDimensao()).isEqualTo(UPDATED_DIMENSAO);
        assertThat(testInstituicaoEnsino.getSlogam()).isEqualTo(UPDATED_SLOGAM);
        assertThat(testInstituicaoEnsino.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testInstituicaoEnsino.getTelemovel()).isEqualTo(UPDATED_TELEMOVEL);
        assertThat(testInstituicaoEnsino.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstituicaoEnsino.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testInstituicaoEnsino.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testInstituicaoEnsino.getEnderecoDetalhado()).isEqualTo(UPDATED_ENDERECO_DETALHADO);
        assertThat(testInstituicaoEnsino.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testInstituicaoEnsino.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testInstituicaoEnsino.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testInstituicaoEnsino.getIsComparticipada()).isEqualTo(UPDATED_IS_COMPARTICIPADA);
        assertThat(testInstituicaoEnsino.getTermosCompromissos()).isEqualTo(UPDATED_TERMOS_COMPROMISSOS);
    }

    @Test
    @Transactional
    void putNonExistingInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeUpdate = instituicaoEnsinoRepository.findAll().size();
        instituicaoEnsino.setId(count.incrementAndGet());

        // Create the InstituicaoEnsino
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, instituicaoEnsinoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeUpdate = instituicaoEnsinoRepository.findAll().size();
        instituicaoEnsino.setId(count.incrementAndGet());

        // Create the InstituicaoEnsino
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeUpdate = instituicaoEnsinoRepository.findAll().size();
        instituicaoEnsino.setId(count.incrementAndGet());

        // Create the InstituicaoEnsino
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInstituicaoEnsinoWithPatch() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        int databaseSizeBeforeUpdate = instituicaoEnsinoRepository.findAll().size();

        // Update the instituicaoEnsino using partial update
        InstituicaoEnsino partialUpdatedInstituicaoEnsino = new InstituicaoEnsino();
        partialUpdatedInstituicaoEnsino.setId(instituicaoEnsino.getId());

        partialUpdatedInstituicaoEnsino
            .logotipo(UPDATED_LOGOTIPO)
            .logotipoContentType(UPDATED_LOGOTIPO_CONTENT_TYPE)
            .niss(UPDATED_NISS)
            .fundador(UPDATED_FUNDADOR)
            .fundacao(UPDATED_FUNDACAO)
            .dimensao(UPDATED_DIMENSAO)
            .slogam(UPDATED_SLOGAM)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .enderecoDetalhado(UPDATED_ENDERECO_DETALHADO)
            .latitude(UPDATED_LATITUDE)
            .descricao(UPDATED_DESCRICAO)
            .isComparticipada(UPDATED_IS_COMPARTICIPADA);

        restInstituicaoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstituicaoEnsino.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInstituicaoEnsino))
            )
            .andExpect(status().isOk());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
        InstituicaoEnsino testInstituicaoEnsino = instituicaoEnsinoList.get(instituicaoEnsinoList.size() - 1);
        assertThat(testInstituicaoEnsino.getLogotipo()).isEqualTo(UPDATED_LOGOTIPO);
        assertThat(testInstituicaoEnsino.getLogotipoContentType()).isEqualTo(UPDATED_LOGOTIPO_CONTENT_TYPE);
        assertThat(testInstituicaoEnsino.getUnidadeOrganica()).isEqualTo(DEFAULT_UNIDADE_ORGANICA);
        assertThat(testInstituicaoEnsino.getNomeFiscal()).isEqualTo(DEFAULT_NOME_FISCAL);
        assertThat(testInstituicaoEnsino.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testInstituicaoEnsino.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testInstituicaoEnsino.getCae()).isEqualTo(DEFAULT_CAE);
        assertThat(testInstituicaoEnsino.getNiss()).isEqualTo(UPDATED_NISS);
        assertThat(testInstituicaoEnsino.getFundador()).isEqualTo(UPDATED_FUNDADOR);
        assertThat(testInstituicaoEnsino.getFundacao()).isEqualTo(UPDATED_FUNDACAO);
        assertThat(testInstituicaoEnsino.getDimensao()).isEqualTo(UPDATED_DIMENSAO);
        assertThat(testInstituicaoEnsino.getSlogam()).isEqualTo(UPDATED_SLOGAM);
        assertThat(testInstituicaoEnsino.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testInstituicaoEnsino.getTelemovel()).isEqualTo(DEFAULT_TELEMOVEL);
        assertThat(testInstituicaoEnsino.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstituicaoEnsino.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testInstituicaoEnsino.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testInstituicaoEnsino.getEnderecoDetalhado()).isEqualTo(UPDATED_ENDERECO_DETALHADO);
        assertThat(testInstituicaoEnsino.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testInstituicaoEnsino.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testInstituicaoEnsino.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testInstituicaoEnsino.getIsComparticipada()).isEqualTo(UPDATED_IS_COMPARTICIPADA);
        assertThat(testInstituicaoEnsino.getTermosCompromissos()).isEqualTo(DEFAULT_TERMOS_COMPROMISSOS);
    }

    @Test
    @Transactional
    void fullUpdateInstituicaoEnsinoWithPatch() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        int databaseSizeBeforeUpdate = instituicaoEnsinoRepository.findAll().size();

        // Update the instituicaoEnsino using partial update
        InstituicaoEnsino partialUpdatedInstituicaoEnsino = new InstituicaoEnsino();
        partialUpdatedInstituicaoEnsino.setId(instituicaoEnsino.getId());

        partialUpdatedInstituicaoEnsino
            .logotipo(UPDATED_LOGOTIPO)
            .logotipoContentType(UPDATED_LOGOTIPO_CONTENT_TYPE)
            .unidadeOrganica(UPDATED_UNIDADE_ORGANICA)
            .nomeFiscal(UPDATED_NOME_FISCAL)
            .numero(UPDATED_NUMERO)
            .nif(UPDATED_NIF)
            .cae(UPDATED_CAE)
            .niss(UPDATED_NISS)
            .fundador(UPDATED_FUNDADOR)
            .fundacao(UPDATED_FUNDACAO)
            .dimensao(UPDATED_DIMENSAO)
            .slogam(UPDATED_SLOGAM)
            .telefone(UPDATED_TELEFONE)
            .telemovel(UPDATED_TELEMOVEL)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .enderecoDetalhado(UPDATED_ENDERECO_DETALHADO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .descricao(UPDATED_DESCRICAO)
            .isComparticipada(UPDATED_IS_COMPARTICIPADA)
            .termosCompromissos(UPDATED_TERMOS_COMPROMISSOS);

        restInstituicaoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstituicaoEnsino.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInstituicaoEnsino))
            )
            .andExpect(status().isOk());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
        InstituicaoEnsino testInstituicaoEnsino = instituicaoEnsinoList.get(instituicaoEnsinoList.size() - 1);
        assertThat(testInstituicaoEnsino.getLogotipo()).isEqualTo(UPDATED_LOGOTIPO);
        assertThat(testInstituicaoEnsino.getLogotipoContentType()).isEqualTo(UPDATED_LOGOTIPO_CONTENT_TYPE);
        assertThat(testInstituicaoEnsino.getUnidadeOrganica()).isEqualTo(UPDATED_UNIDADE_ORGANICA);
        assertThat(testInstituicaoEnsino.getNomeFiscal()).isEqualTo(UPDATED_NOME_FISCAL);
        assertThat(testInstituicaoEnsino.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testInstituicaoEnsino.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testInstituicaoEnsino.getCae()).isEqualTo(UPDATED_CAE);
        assertThat(testInstituicaoEnsino.getNiss()).isEqualTo(UPDATED_NISS);
        assertThat(testInstituicaoEnsino.getFundador()).isEqualTo(UPDATED_FUNDADOR);
        assertThat(testInstituicaoEnsino.getFundacao()).isEqualTo(UPDATED_FUNDACAO);
        assertThat(testInstituicaoEnsino.getDimensao()).isEqualTo(UPDATED_DIMENSAO);
        assertThat(testInstituicaoEnsino.getSlogam()).isEqualTo(UPDATED_SLOGAM);
        assertThat(testInstituicaoEnsino.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testInstituicaoEnsino.getTelemovel()).isEqualTo(UPDATED_TELEMOVEL);
        assertThat(testInstituicaoEnsino.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstituicaoEnsino.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testInstituicaoEnsino.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testInstituicaoEnsino.getEnderecoDetalhado()).isEqualTo(UPDATED_ENDERECO_DETALHADO);
        assertThat(testInstituicaoEnsino.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testInstituicaoEnsino.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testInstituicaoEnsino.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testInstituicaoEnsino.getIsComparticipada()).isEqualTo(UPDATED_IS_COMPARTICIPADA);
        assertThat(testInstituicaoEnsino.getTermosCompromissos()).isEqualTo(UPDATED_TERMOS_COMPROMISSOS);
    }

    @Test
    @Transactional
    void patchNonExistingInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeUpdate = instituicaoEnsinoRepository.findAll().size();
        instituicaoEnsino.setId(count.incrementAndGet());

        // Create the InstituicaoEnsino
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, instituicaoEnsinoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeUpdate = instituicaoEnsinoRepository.findAll().size();
        instituicaoEnsino.setId(count.incrementAndGet());

        // Create the InstituicaoEnsino
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeUpdate = instituicaoEnsinoRepository.findAll().size();
        instituicaoEnsino.setId(count.incrementAndGet());

        // Create the InstituicaoEnsino
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInstituicaoEnsino() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        int databaseSizeBeforeDelete = instituicaoEnsinoRepository.findAll().size();

        // Delete the instituicaoEnsino
        restInstituicaoEnsinoMockMvc
            .perform(delete(ENTITY_API_URL_ID, instituicaoEnsino.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
