package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnexoDiscente;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.DissertacaoFinalCurso;
import com.ravunana.longonkelo.domain.EncarregadoEducacao;
import com.ravunana.longonkelo.domain.EnderecoDiscente;
import com.ravunana.longonkelo.domain.HistoricoSaude;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula;
import com.ravunana.longonkelo.domain.ResumoAcademico;
import com.ravunana.longonkelo.domain.enumeration.Sexo;
import com.ravunana.longonkelo.repository.DiscenteRepository;
import com.ravunana.longonkelo.service.DiscenteService;
import com.ravunana.longonkelo.service.criteria.DiscenteCriteria;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import com.ravunana.longonkelo.service.mapper.DiscenteMapper;
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
 * Integration tests for the {@link DiscenteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DiscenteResourceIT {

    private static final byte[] DEFAULT_FOTOGRAFIA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTOGRAFIA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTOGRAFIA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTOGRAFIA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_NASCIMENTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DOCUMENTO_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO_NUMERO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOCUMENTO_EMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENTO_EMISSAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOCUMENTO_EMISSAO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DOCUMENTO_VALIDADE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENTO_VALIDADE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOCUMENTO_VALIDADE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    private static final Sexo DEFAULT_SEXO = Sexo.MASCULINO;
    private static final Sexo UPDATED_SEXO = Sexo.FEMENINO;

    private static final String DEFAULT_PAI = "AAAAAAAAAA";
    private static final String UPDATED_PAI = "BBBBBBBBBB";

    private static final String DEFAULT_MAE = "AAAAAAAAAA";
    private static final String UPDATED_MAE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_PARENTE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_PARENTE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENCARREGADO_EDUCACAO = false;
    private static final Boolean UPDATED_IS_ENCARREGADO_EDUCACAO = true;

    private static final Boolean DEFAULT_IS_TRABALHADOR = false;
    private static final Boolean UPDATED_IS_TRABALHADOR = true;

    private static final Boolean DEFAULT_IS_FILHO_ANTIGO_CONBATENTE = false;
    private static final Boolean UPDATED_IS_FILHO_ANTIGO_CONBATENTE = true;

    private static final Boolean DEFAULT_IS_ATESTADO_POBREZA = false;
    private static final Boolean UPDATED_IS_ATESTADO_POBREZA = true;

    private static final String DEFAULT_NOME_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MEDICO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_MEDICO = "AAAAAAAAA";
    private static final String UPDATED_TELEFONE_MEDICO = "BBBBBBBBB";

    private static final String DEFAULT_INSTITUICAO_PARTICULAR_SAUDE = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUICAO_PARTICULAR_SAUDE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALTURA = 0;
    private static final Integer UPDATED_ALTURA = 1;
    private static final Integer SMALLER_ALTURA = 0 - 1;

    private static final Double DEFAULT_PESO = 0D;
    private static final Double UPDATED_PESO = 1D;
    private static final Double SMALLER_PESO = 0D - 1D;

    private static final Boolean DEFAULT_IS_ASMATICO = false;
    private static final Boolean UPDATED_IS_ASMATICO = true;

    private static final Boolean DEFAULT_IS_ALERGICO = false;
    private static final Boolean UPDATED_IS_ALERGICO = true;

    private static final Boolean DEFAULT_IS_PRATICA_EDUCACAO_FISICA = false;
    private static final Boolean UPDATED_IS_PRATICA_EDUCACAO_FISICA = true;

    private static final Boolean DEFAULT_IS_AUTORIZADO_MEDICACAO = false;
    private static final Boolean UPDATED_IS_AUTORIZADO_MEDICACAO = true;

    private static final String DEFAULT_CUIDADOS_ESPECIAIS_SAUDE = "AAAAAAAAAA";
    private static final String UPDATED_CUIDADOS_ESPECIAIS_SAUDE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_INGRESSO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_INGRESSO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_INGRESSO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/discentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiscenteRepository discenteRepository;

    @Mock
    private DiscenteRepository discenteRepositoryMock;

    @Autowired
    private DiscenteMapper discenteMapper;

    @Mock
    private DiscenteService discenteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiscenteMockMvc;

    private Discente discente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discente createEntity(EntityManager em) {
        Discente discente = new Discente()
            .fotografia(DEFAULT_FOTOGRAFIA)
            .fotografiaContentType(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)
            .nome(DEFAULT_NOME)
            .nascimento(DEFAULT_NASCIMENTO)
            .documentoNumero(DEFAULT_DOCUMENTO_NUMERO)
            .documentoEmissao(DEFAULT_DOCUMENTO_EMISSAO)
            .documentoValidade(DEFAULT_DOCUMENTO_VALIDADE)
            .nif(DEFAULT_NIF)
            .sexo(DEFAULT_SEXO)
            .pai(DEFAULT_PAI)
            .mae(DEFAULT_MAE)
            .telefonePrincipal(DEFAULT_TELEFONE_PRINCIPAL)
            .telefoneParente(DEFAULT_TELEFONE_PARENTE)
            .email(DEFAULT_EMAIL)
            .isEncarregadoEducacao(DEFAULT_IS_ENCARREGADO_EDUCACAO)
            .isTrabalhador(DEFAULT_IS_TRABALHADOR)
            .isFilhoAntigoConbatente(DEFAULT_IS_FILHO_ANTIGO_CONBATENTE)
            .isAtestadoPobreza(DEFAULT_IS_ATESTADO_POBREZA)
            .nomeMedico(DEFAULT_NOME_MEDICO)
            .telefoneMedico(DEFAULT_TELEFONE_MEDICO)
            .instituicaoParticularSaude(DEFAULT_INSTITUICAO_PARTICULAR_SAUDE)
            .altura(DEFAULT_ALTURA)
            .peso(DEFAULT_PESO)
            .isAsmatico(DEFAULT_IS_ASMATICO)
            .isAlergico(DEFAULT_IS_ALERGICO)
            .isPraticaEducacaoFisica(DEFAULT_IS_PRATICA_EDUCACAO_FISICA)
            .isAutorizadoMedicacao(DEFAULT_IS_AUTORIZADO_MEDICACAO)
            .cuidadosEspeciaisSaude(DEFAULT_CUIDADOS_ESPECIAIS_SAUDE)
            .numeroProcesso(DEFAULT_NUMERO_PROCESSO)
            .dataIngresso(DEFAULT_DATA_INGRESSO)
            .hash(DEFAULT_HASH)
            .observacao(DEFAULT_OBSERVACAO);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        discente.setNacionalidade(lookupItem);
        // Add required entity
        discente.setNaturalidade(lookupItem);
        // Add required entity
        discente.setTipoDocumento(lookupItem);
        return discente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discente createUpdatedEntity(EntityManager em) {
        Discente discente = new Discente()
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .nascimento(UPDATED_NASCIMENTO)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO)
            .documentoEmissao(UPDATED_DOCUMENTO_EMISSAO)
            .documentoValidade(UPDATED_DOCUMENTO_VALIDADE)
            .nif(UPDATED_NIF)
            .sexo(UPDATED_SEXO)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneParente(UPDATED_TELEFONE_PARENTE)
            .email(UPDATED_EMAIL)
            .isEncarregadoEducacao(UPDATED_IS_ENCARREGADO_EDUCACAO)
            .isTrabalhador(UPDATED_IS_TRABALHADOR)
            .isFilhoAntigoConbatente(UPDATED_IS_FILHO_ANTIGO_CONBATENTE)
            .isAtestadoPobreza(UPDATED_IS_ATESTADO_POBREZA)
            .nomeMedico(UPDATED_NOME_MEDICO)
            .telefoneMedico(UPDATED_TELEFONE_MEDICO)
            .instituicaoParticularSaude(UPDATED_INSTITUICAO_PARTICULAR_SAUDE)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO)
            .isAsmatico(UPDATED_IS_ASMATICO)
            .isAlergico(UPDATED_IS_ALERGICO)
            .isPraticaEducacaoFisica(UPDATED_IS_PRATICA_EDUCACAO_FISICA)
            .isAutorizadoMedicacao(UPDATED_IS_AUTORIZADO_MEDICACAO)
            .cuidadosEspeciaisSaude(UPDATED_CUIDADOS_ESPECIAIS_SAUDE)
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .dataIngresso(UPDATED_DATA_INGRESSO)
            .hash(UPDATED_HASH)
            .observacao(UPDATED_OBSERVACAO);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createUpdatedEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        discente.setNacionalidade(lookupItem);
        // Add required entity
        discente.setNaturalidade(lookupItem);
        // Add required entity
        discente.setTipoDocumento(lookupItem);
        return discente;
    }

    @BeforeEach
    public void initTest() {
        discente = createEntity(em);
    }

    @Test
    @Transactional
    void createDiscente() throws Exception {
        int databaseSizeBeforeCreate = discenteRepository.findAll().size();
        // Create the Discente
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);
        restDiscenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isCreated());

        // Validate the Discente in the database
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeCreate + 1);
        Discente testDiscente = discenteList.get(discenteList.size() - 1);
        assertThat(testDiscente.getFotografia()).isEqualTo(DEFAULT_FOTOGRAFIA);
        assertThat(testDiscente.getFotografiaContentType()).isEqualTo(DEFAULT_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testDiscente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDiscente.getNascimento()).isEqualTo(DEFAULT_NASCIMENTO);
        assertThat(testDiscente.getDocumentoNumero()).isEqualTo(DEFAULT_DOCUMENTO_NUMERO);
        assertThat(testDiscente.getDocumentoEmissao()).isEqualTo(DEFAULT_DOCUMENTO_EMISSAO);
        assertThat(testDiscente.getDocumentoValidade()).isEqualTo(DEFAULT_DOCUMENTO_VALIDADE);
        assertThat(testDiscente.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testDiscente.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testDiscente.getPai()).isEqualTo(DEFAULT_PAI);
        assertThat(testDiscente.getMae()).isEqualTo(DEFAULT_MAE);
        assertThat(testDiscente.getTelefonePrincipal()).isEqualTo(DEFAULT_TELEFONE_PRINCIPAL);
        assertThat(testDiscente.getTelefoneParente()).isEqualTo(DEFAULT_TELEFONE_PARENTE);
        assertThat(testDiscente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDiscente.getIsEncarregadoEducacao()).isEqualTo(DEFAULT_IS_ENCARREGADO_EDUCACAO);
        assertThat(testDiscente.getIsTrabalhador()).isEqualTo(DEFAULT_IS_TRABALHADOR);
        assertThat(testDiscente.getIsFilhoAntigoConbatente()).isEqualTo(DEFAULT_IS_FILHO_ANTIGO_CONBATENTE);
        assertThat(testDiscente.getIsAtestadoPobreza()).isEqualTo(DEFAULT_IS_ATESTADO_POBREZA);
        assertThat(testDiscente.getNomeMedico()).isEqualTo(DEFAULT_NOME_MEDICO);
        assertThat(testDiscente.getTelefoneMedico()).isEqualTo(DEFAULT_TELEFONE_MEDICO);
        assertThat(testDiscente.getInstituicaoParticularSaude()).isEqualTo(DEFAULT_INSTITUICAO_PARTICULAR_SAUDE);
        assertThat(testDiscente.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testDiscente.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testDiscente.getIsAsmatico()).isEqualTo(DEFAULT_IS_ASMATICO);
        assertThat(testDiscente.getIsAlergico()).isEqualTo(DEFAULT_IS_ALERGICO);
        assertThat(testDiscente.getIsPraticaEducacaoFisica()).isEqualTo(DEFAULT_IS_PRATICA_EDUCACAO_FISICA);
        assertThat(testDiscente.getIsAutorizadoMedicacao()).isEqualTo(DEFAULT_IS_AUTORIZADO_MEDICACAO);
        assertThat(testDiscente.getCuidadosEspeciaisSaude()).isEqualTo(DEFAULT_CUIDADOS_ESPECIAIS_SAUDE);
        assertThat(testDiscente.getNumeroProcesso()).isEqualTo(DEFAULT_NUMERO_PROCESSO);
        assertThat(testDiscente.getDataIngresso()).isEqualTo(DEFAULT_DATA_INGRESSO);
        assertThat(testDiscente.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testDiscente.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    void createDiscenteWithExistingId() throws Exception {
        // Create the Discente with an existing ID
        discente.setId(1L);
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        int databaseSizeBeforeCreate = discenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discente in the database
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = discenteRepository.findAll().size();
        // set the field null
        discente.setNome(null);

        // Create the Discente, which fails.
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        restDiscenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isBadRequest());

        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = discenteRepository.findAll().size();
        // set the field null
        discente.setNascimento(null);

        // Create the Discente, which fails.
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        restDiscenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isBadRequest());

        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentoNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = discenteRepository.findAll().size();
        // set the field null
        discente.setDocumentoNumero(null);

        // Create the Discente, which fails.
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        restDiscenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isBadRequest());

        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentoEmissaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = discenteRepository.findAll().size();
        // set the field null
        discente.setDocumentoEmissao(null);

        // Create the Discente, which fails.
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        restDiscenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isBadRequest());

        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentoValidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = discenteRepository.findAll().size();
        // set the field null
        discente.setDocumentoValidade(null);

        // Create the Discente, which fails.
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        restDiscenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isBadRequest());

        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = discenteRepository.findAll().size();
        // set the field null
        discente.setSexo(null);

        // Create the Discente, which fails.
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        restDiscenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isBadRequest());

        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = discenteRepository.findAll().size();
        // set the field null
        discente.setPai(null);

        // Create the Discente, which fails.
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        restDiscenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isBadRequest());

        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaeIsRequired() throws Exception {
        int databaseSizeBeforeTest = discenteRepository.findAll().size();
        // set the field null
        discente.setMae(null);

        // Create the Discente, which fails.
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        restDiscenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isBadRequest());

        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = discenteRepository.findAll().size();
        // set the field null
        discente.setNumeroProcesso(null);

        // Create the Discente, which fails.
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        restDiscenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isBadRequest());

        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDiscentes() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList
        restDiscenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discente.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotografiaContentType").value(hasItem(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA))))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nascimento").value(hasItem(DEFAULT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].documentoNumero").value(hasItem(DEFAULT_DOCUMENTO_NUMERO)))
            .andExpect(jsonPath("$.[*].documentoEmissao").value(hasItem(DEFAULT_DOCUMENTO_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].documentoValidade").value(hasItem(DEFAULT_DOCUMENTO_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].pai").value(hasItem(DEFAULT_PAI)))
            .andExpect(jsonPath("$.[*].mae").value(hasItem(DEFAULT_MAE)))
            .andExpect(jsonPath("$.[*].telefonePrincipal").value(hasItem(DEFAULT_TELEFONE_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].telefoneParente").value(hasItem(DEFAULT_TELEFONE_PARENTE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].isEncarregadoEducacao").value(hasItem(DEFAULT_IS_ENCARREGADO_EDUCACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].isTrabalhador").value(hasItem(DEFAULT_IS_TRABALHADOR.booleanValue())))
            .andExpect(jsonPath("$.[*].isFilhoAntigoConbatente").value(hasItem(DEFAULT_IS_FILHO_ANTIGO_CONBATENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].isAtestadoPobreza").value(hasItem(DEFAULT_IS_ATESTADO_POBREZA.booleanValue())))
            .andExpect(jsonPath("$.[*].nomeMedico").value(hasItem(DEFAULT_NOME_MEDICO)))
            .andExpect(jsonPath("$.[*].telefoneMedico").value(hasItem(DEFAULT_TELEFONE_MEDICO)))
            .andExpect(jsonPath("$.[*].instituicaoParticularSaude").value(hasItem(DEFAULT_INSTITUICAO_PARTICULAR_SAUDE)))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].isAsmatico").value(hasItem(DEFAULT_IS_ASMATICO.booleanValue())))
            .andExpect(jsonPath("$.[*].isAlergico").value(hasItem(DEFAULT_IS_ALERGICO.booleanValue())))
            .andExpect(jsonPath("$.[*].isPraticaEducacaoFisica").value(hasItem(DEFAULT_IS_PRATICA_EDUCACAO_FISICA.booleanValue())))
            .andExpect(jsonPath("$.[*].isAutorizadoMedicacao").value(hasItem(DEFAULT_IS_AUTORIZADO_MEDICACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].cuidadosEspeciaisSaude").value(hasItem(DEFAULT_CUIDADOS_ESPECIAIS_SAUDE.toString())))
            .andExpect(jsonPath("$.[*].numeroProcesso").value(hasItem(DEFAULT_NUMERO_PROCESSO)))
            .andExpect(jsonPath("$.[*].dataIngresso").value(hasItem(sameInstant(DEFAULT_DATA_INGRESSO))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDiscentesWithEagerRelationshipsIsEnabled() throws Exception {
        when(discenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDiscenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(discenteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDiscentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(discenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDiscenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(discenteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDiscente() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get the discente
        restDiscenteMockMvc
            .perform(get(ENTITY_API_URL_ID, discente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discente.getId().intValue()))
            .andExpect(jsonPath("$.fotografiaContentType").value(DEFAULT_FOTOGRAFIA_CONTENT_TYPE))
            .andExpect(jsonPath("$.fotografia").value(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA)))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.nascimento").value(DEFAULT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.documentoNumero").value(DEFAULT_DOCUMENTO_NUMERO))
            .andExpect(jsonPath("$.documentoEmissao").value(DEFAULT_DOCUMENTO_EMISSAO.toString()))
            .andExpect(jsonPath("$.documentoValidade").value(DEFAULT_DOCUMENTO_VALIDADE.toString()))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.pai").value(DEFAULT_PAI))
            .andExpect(jsonPath("$.mae").value(DEFAULT_MAE))
            .andExpect(jsonPath("$.telefonePrincipal").value(DEFAULT_TELEFONE_PRINCIPAL))
            .andExpect(jsonPath("$.telefoneParente").value(DEFAULT_TELEFONE_PARENTE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.isEncarregadoEducacao").value(DEFAULT_IS_ENCARREGADO_EDUCACAO.booleanValue()))
            .andExpect(jsonPath("$.isTrabalhador").value(DEFAULT_IS_TRABALHADOR.booleanValue()))
            .andExpect(jsonPath("$.isFilhoAntigoConbatente").value(DEFAULT_IS_FILHO_ANTIGO_CONBATENTE.booleanValue()))
            .andExpect(jsonPath("$.isAtestadoPobreza").value(DEFAULT_IS_ATESTADO_POBREZA.booleanValue()))
            .andExpect(jsonPath("$.nomeMedico").value(DEFAULT_NOME_MEDICO))
            .andExpect(jsonPath("$.telefoneMedico").value(DEFAULT_TELEFONE_MEDICO))
            .andExpect(jsonPath("$.instituicaoParticularSaude").value(DEFAULT_INSTITUICAO_PARTICULAR_SAUDE))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.isAsmatico").value(DEFAULT_IS_ASMATICO.booleanValue()))
            .andExpect(jsonPath("$.isAlergico").value(DEFAULT_IS_ALERGICO.booleanValue()))
            .andExpect(jsonPath("$.isPraticaEducacaoFisica").value(DEFAULT_IS_PRATICA_EDUCACAO_FISICA.booleanValue()))
            .andExpect(jsonPath("$.isAutorizadoMedicacao").value(DEFAULT_IS_AUTORIZADO_MEDICACAO.booleanValue()))
            .andExpect(jsonPath("$.cuidadosEspeciaisSaude").value(DEFAULT_CUIDADOS_ESPECIAIS_SAUDE.toString()))
            .andExpect(jsonPath("$.numeroProcesso").value(DEFAULT_NUMERO_PROCESSO))
            .andExpect(jsonPath("$.dataIngresso").value(sameInstant(DEFAULT_DATA_INGRESSO)))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()));
    }

    @Test
    @Transactional
    void getDiscentesByIdFiltering() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        Long id = discente.getId();

        defaultDiscenteShouldBeFound("id.equals=" + id);
        defaultDiscenteShouldNotBeFound("id.notEquals=" + id);

        defaultDiscenteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDiscenteShouldNotBeFound("id.greaterThan=" + id);

        defaultDiscenteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDiscenteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDiscentesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nome equals to DEFAULT_NOME
        defaultDiscenteShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the discenteList where nome equals to UPDATED_NOME
        defaultDiscenteShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDiscentesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultDiscenteShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the discenteList where nome equals to UPDATED_NOME
        defaultDiscenteShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDiscentesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nome is not null
        defaultDiscenteShouldBeFound("nome.specified=true");

        // Get all the discenteList where nome is null
        defaultDiscenteShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByNomeContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nome contains DEFAULT_NOME
        defaultDiscenteShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the discenteList where nome contains UPDATED_NOME
        defaultDiscenteShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDiscentesByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nome does not contain DEFAULT_NOME
        defaultDiscenteShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the discenteList where nome does not contain UPDATED_NOME
        defaultDiscenteShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDiscentesByNascimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nascimento equals to DEFAULT_NASCIMENTO
        defaultDiscenteShouldBeFound("nascimento.equals=" + DEFAULT_NASCIMENTO);

        // Get all the discenteList where nascimento equals to UPDATED_NASCIMENTO
        defaultDiscenteShouldNotBeFound("nascimento.equals=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDiscentesByNascimentoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nascimento in DEFAULT_NASCIMENTO or UPDATED_NASCIMENTO
        defaultDiscenteShouldBeFound("nascimento.in=" + DEFAULT_NASCIMENTO + "," + UPDATED_NASCIMENTO);

        // Get all the discenteList where nascimento equals to UPDATED_NASCIMENTO
        defaultDiscenteShouldNotBeFound("nascimento.in=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDiscentesByNascimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nascimento is not null
        defaultDiscenteShouldBeFound("nascimento.specified=true");

        // Get all the discenteList where nascimento is null
        defaultDiscenteShouldNotBeFound("nascimento.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByNascimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nascimento is greater than or equal to DEFAULT_NASCIMENTO
        defaultDiscenteShouldBeFound("nascimento.greaterThanOrEqual=" + DEFAULT_NASCIMENTO);

        // Get all the discenteList where nascimento is greater than or equal to UPDATED_NASCIMENTO
        defaultDiscenteShouldNotBeFound("nascimento.greaterThanOrEqual=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDiscentesByNascimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nascimento is less than or equal to DEFAULT_NASCIMENTO
        defaultDiscenteShouldBeFound("nascimento.lessThanOrEqual=" + DEFAULT_NASCIMENTO);

        // Get all the discenteList where nascimento is less than or equal to SMALLER_NASCIMENTO
        defaultDiscenteShouldNotBeFound("nascimento.lessThanOrEqual=" + SMALLER_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDiscentesByNascimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nascimento is less than DEFAULT_NASCIMENTO
        defaultDiscenteShouldNotBeFound("nascimento.lessThan=" + DEFAULT_NASCIMENTO);

        // Get all the discenteList where nascimento is less than UPDATED_NASCIMENTO
        defaultDiscenteShouldBeFound("nascimento.lessThan=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDiscentesByNascimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nascimento is greater than DEFAULT_NASCIMENTO
        defaultDiscenteShouldNotBeFound("nascimento.greaterThan=" + DEFAULT_NASCIMENTO);

        // Get all the discenteList where nascimento is greater than SMALLER_NASCIMENTO
        defaultDiscenteShouldBeFound("nascimento.greaterThan=" + SMALLER_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoNumero equals to DEFAULT_DOCUMENTO_NUMERO
        defaultDiscenteShouldBeFound("documentoNumero.equals=" + DEFAULT_DOCUMENTO_NUMERO);

        // Get all the discenteList where documentoNumero equals to UPDATED_DOCUMENTO_NUMERO
        defaultDiscenteShouldNotBeFound("documentoNumero.equals=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoNumero in DEFAULT_DOCUMENTO_NUMERO or UPDATED_DOCUMENTO_NUMERO
        defaultDiscenteShouldBeFound("documentoNumero.in=" + DEFAULT_DOCUMENTO_NUMERO + "," + UPDATED_DOCUMENTO_NUMERO);

        // Get all the discenteList where documentoNumero equals to UPDATED_DOCUMENTO_NUMERO
        defaultDiscenteShouldNotBeFound("documentoNumero.in=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoNumero is not null
        defaultDiscenteShouldBeFound("documentoNumero.specified=true");

        // Get all the discenteList where documentoNumero is null
        defaultDiscenteShouldNotBeFound("documentoNumero.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoNumeroContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoNumero contains DEFAULT_DOCUMENTO_NUMERO
        defaultDiscenteShouldBeFound("documentoNumero.contains=" + DEFAULT_DOCUMENTO_NUMERO);

        // Get all the discenteList where documentoNumero contains UPDATED_DOCUMENTO_NUMERO
        defaultDiscenteShouldNotBeFound("documentoNumero.contains=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoNumero does not contain DEFAULT_DOCUMENTO_NUMERO
        defaultDiscenteShouldNotBeFound("documentoNumero.doesNotContain=" + DEFAULT_DOCUMENTO_NUMERO);

        // Get all the discenteList where documentoNumero does not contain UPDATED_DOCUMENTO_NUMERO
        defaultDiscenteShouldBeFound("documentoNumero.doesNotContain=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoEmissaoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoEmissao equals to DEFAULT_DOCUMENTO_EMISSAO
        defaultDiscenteShouldBeFound("documentoEmissao.equals=" + DEFAULT_DOCUMENTO_EMISSAO);

        // Get all the discenteList where documentoEmissao equals to UPDATED_DOCUMENTO_EMISSAO
        defaultDiscenteShouldNotBeFound("documentoEmissao.equals=" + UPDATED_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoEmissaoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoEmissao in DEFAULT_DOCUMENTO_EMISSAO or UPDATED_DOCUMENTO_EMISSAO
        defaultDiscenteShouldBeFound("documentoEmissao.in=" + DEFAULT_DOCUMENTO_EMISSAO + "," + UPDATED_DOCUMENTO_EMISSAO);

        // Get all the discenteList where documentoEmissao equals to UPDATED_DOCUMENTO_EMISSAO
        defaultDiscenteShouldNotBeFound("documentoEmissao.in=" + UPDATED_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoEmissaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoEmissao is not null
        defaultDiscenteShouldBeFound("documentoEmissao.specified=true");

        // Get all the discenteList where documentoEmissao is null
        defaultDiscenteShouldNotBeFound("documentoEmissao.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoEmissaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoEmissao is greater than or equal to DEFAULT_DOCUMENTO_EMISSAO
        defaultDiscenteShouldBeFound("documentoEmissao.greaterThanOrEqual=" + DEFAULT_DOCUMENTO_EMISSAO);

        // Get all the discenteList where documentoEmissao is greater than or equal to UPDATED_DOCUMENTO_EMISSAO
        defaultDiscenteShouldNotBeFound("documentoEmissao.greaterThanOrEqual=" + UPDATED_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoEmissaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoEmissao is less than or equal to DEFAULT_DOCUMENTO_EMISSAO
        defaultDiscenteShouldBeFound("documentoEmissao.lessThanOrEqual=" + DEFAULT_DOCUMENTO_EMISSAO);

        // Get all the discenteList where documentoEmissao is less than or equal to SMALLER_DOCUMENTO_EMISSAO
        defaultDiscenteShouldNotBeFound("documentoEmissao.lessThanOrEqual=" + SMALLER_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoEmissaoIsLessThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoEmissao is less than DEFAULT_DOCUMENTO_EMISSAO
        defaultDiscenteShouldNotBeFound("documentoEmissao.lessThan=" + DEFAULT_DOCUMENTO_EMISSAO);

        // Get all the discenteList where documentoEmissao is less than UPDATED_DOCUMENTO_EMISSAO
        defaultDiscenteShouldBeFound("documentoEmissao.lessThan=" + UPDATED_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoEmissaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoEmissao is greater than DEFAULT_DOCUMENTO_EMISSAO
        defaultDiscenteShouldNotBeFound("documentoEmissao.greaterThan=" + DEFAULT_DOCUMENTO_EMISSAO);

        // Get all the discenteList where documentoEmissao is greater than SMALLER_DOCUMENTO_EMISSAO
        defaultDiscenteShouldBeFound("documentoEmissao.greaterThan=" + SMALLER_DOCUMENTO_EMISSAO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoValidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoValidade equals to DEFAULT_DOCUMENTO_VALIDADE
        defaultDiscenteShouldBeFound("documentoValidade.equals=" + DEFAULT_DOCUMENTO_VALIDADE);

        // Get all the discenteList where documentoValidade equals to UPDATED_DOCUMENTO_VALIDADE
        defaultDiscenteShouldNotBeFound("documentoValidade.equals=" + UPDATED_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoValidadeIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoValidade in DEFAULT_DOCUMENTO_VALIDADE or UPDATED_DOCUMENTO_VALIDADE
        defaultDiscenteShouldBeFound("documentoValidade.in=" + DEFAULT_DOCUMENTO_VALIDADE + "," + UPDATED_DOCUMENTO_VALIDADE);

        // Get all the discenteList where documentoValidade equals to UPDATED_DOCUMENTO_VALIDADE
        defaultDiscenteShouldNotBeFound("documentoValidade.in=" + UPDATED_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoValidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoValidade is not null
        defaultDiscenteShouldBeFound("documentoValidade.specified=true");

        // Get all the discenteList where documentoValidade is null
        defaultDiscenteShouldNotBeFound("documentoValidade.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoValidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoValidade is greater than or equal to DEFAULT_DOCUMENTO_VALIDADE
        defaultDiscenteShouldBeFound("documentoValidade.greaterThanOrEqual=" + DEFAULT_DOCUMENTO_VALIDADE);

        // Get all the discenteList where documentoValidade is greater than or equal to UPDATED_DOCUMENTO_VALIDADE
        defaultDiscenteShouldNotBeFound("documentoValidade.greaterThanOrEqual=" + UPDATED_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoValidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoValidade is less than or equal to DEFAULT_DOCUMENTO_VALIDADE
        defaultDiscenteShouldBeFound("documentoValidade.lessThanOrEqual=" + DEFAULT_DOCUMENTO_VALIDADE);

        // Get all the discenteList where documentoValidade is less than or equal to SMALLER_DOCUMENTO_VALIDADE
        defaultDiscenteShouldNotBeFound("documentoValidade.lessThanOrEqual=" + SMALLER_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoValidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoValidade is less than DEFAULT_DOCUMENTO_VALIDADE
        defaultDiscenteShouldNotBeFound("documentoValidade.lessThan=" + DEFAULT_DOCUMENTO_VALIDADE);

        // Get all the discenteList where documentoValidade is less than UPDATED_DOCUMENTO_VALIDADE
        defaultDiscenteShouldBeFound("documentoValidade.lessThan=" + UPDATED_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDiscentesByDocumentoValidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where documentoValidade is greater than DEFAULT_DOCUMENTO_VALIDADE
        defaultDiscenteShouldNotBeFound("documentoValidade.greaterThan=" + DEFAULT_DOCUMENTO_VALIDADE);

        // Get all the discenteList where documentoValidade is greater than SMALLER_DOCUMENTO_VALIDADE
        defaultDiscenteShouldBeFound("documentoValidade.greaterThan=" + SMALLER_DOCUMENTO_VALIDADE);
    }

    @Test
    @Transactional
    void getAllDiscentesByNifIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nif equals to DEFAULT_NIF
        defaultDiscenteShouldBeFound("nif.equals=" + DEFAULT_NIF);

        // Get all the discenteList where nif equals to UPDATED_NIF
        defaultDiscenteShouldNotBeFound("nif.equals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDiscentesByNifIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nif in DEFAULT_NIF or UPDATED_NIF
        defaultDiscenteShouldBeFound("nif.in=" + DEFAULT_NIF + "," + UPDATED_NIF);

        // Get all the discenteList where nif equals to UPDATED_NIF
        defaultDiscenteShouldNotBeFound("nif.in=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDiscentesByNifIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nif is not null
        defaultDiscenteShouldBeFound("nif.specified=true");

        // Get all the discenteList where nif is null
        defaultDiscenteShouldNotBeFound("nif.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByNifContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nif contains DEFAULT_NIF
        defaultDiscenteShouldBeFound("nif.contains=" + DEFAULT_NIF);

        // Get all the discenteList where nif contains UPDATED_NIF
        defaultDiscenteShouldNotBeFound("nif.contains=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDiscentesByNifNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nif does not contain DEFAULT_NIF
        defaultDiscenteShouldNotBeFound("nif.doesNotContain=" + DEFAULT_NIF);

        // Get all the discenteList where nif does not contain UPDATED_NIF
        defaultDiscenteShouldBeFound("nif.doesNotContain=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDiscentesBySexoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where sexo equals to DEFAULT_SEXO
        defaultDiscenteShouldBeFound("sexo.equals=" + DEFAULT_SEXO);

        // Get all the discenteList where sexo equals to UPDATED_SEXO
        defaultDiscenteShouldNotBeFound("sexo.equals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllDiscentesBySexoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where sexo in DEFAULT_SEXO or UPDATED_SEXO
        defaultDiscenteShouldBeFound("sexo.in=" + DEFAULT_SEXO + "," + UPDATED_SEXO);

        // Get all the discenteList where sexo equals to UPDATED_SEXO
        defaultDiscenteShouldNotBeFound("sexo.in=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllDiscentesBySexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where sexo is not null
        defaultDiscenteShouldBeFound("sexo.specified=true");

        // Get all the discenteList where sexo is null
        defaultDiscenteShouldNotBeFound("sexo.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByPaiIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where pai equals to DEFAULT_PAI
        defaultDiscenteShouldBeFound("pai.equals=" + DEFAULT_PAI);

        // Get all the discenteList where pai equals to UPDATED_PAI
        defaultDiscenteShouldNotBeFound("pai.equals=" + UPDATED_PAI);
    }

    @Test
    @Transactional
    void getAllDiscentesByPaiIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where pai in DEFAULT_PAI or UPDATED_PAI
        defaultDiscenteShouldBeFound("pai.in=" + DEFAULT_PAI + "," + UPDATED_PAI);

        // Get all the discenteList where pai equals to UPDATED_PAI
        defaultDiscenteShouldNotBeFound("pai.in=" + UPDATED_PAI);
    }

    @Test
    @Transactional
    void getAllDiscentesByPaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where pai is not null
        defaultDiscenteShouldBeFound("pai.specified=true");

        // Get all the discenteList where pai is null
        defaultDiscenteShouldNotBeFound("pai.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByPaiContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where pai contains DEFAULT_PAI
        defaultDiscenteShouldBeFound("pai.contains=" + DEFAULT_PAI);

        // Get all the discenteList where pai contains UPDATED_PAI
        defaultDiscenteShouldNotBeFound("pai.contains=" + UPDATED_PAI);
    }

    @Test
    @Transactional
    void getAllDiscentesByPaiNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where pai does not contain DEFAULT_PAI
        defaultDiscenteShouldNotBeFound("pai.doesNotContain=" + DEFAULT_PAI);

        // Get all the discenteList where pai does not contain UPDATED_PAI
        defaultDiscenteShouldBeFound("pai.doesNotContain=" + UPDATED_PAI);
    }

    @Test
    @Transactional
    void getAllDiscentesByMaeIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where mae equals to DEFAULT_MAE
        defaultDiscenteShouldBeFound("mae.equals=" + DEFAULT_MAE);

        // Get all the discenteList where mae equals to UPDATED_MAE
        defaultDiscenteShouldNotBeFound("mae.equals=" + UPDATED_MAE);
    }

    @Test
    @Transactional
    void getAllDiscentesByMaeIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where mae in DEFAULT_MAE or UPDATED_MAE
        defaultDiscenteShouldBeFound("mae.in=" + DEFAULT_MAE + "," + UPDATED_MAE);

        // Get all the discenteList where mae equals to UPDATED_MAE
        defaultDiscenteShouldNotBeFound("mae.in=" + UPDATED_MAE);
    }

    @Test
    @Transactional
    void getAllDiscentesByMaeIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where mae is not null
        defaultDiscenteShouldBeFound("mae.specified=true");

        // Get all the discenteList where mae is null
        defaultDiscenteShouldNotBeFound("mae.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByMaeContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where mae contains DEFAULT_MAE
        defaultDiscenteShouldBeFound("mae.contains=" + DEFAULT_MAE);

        // Get all the discenteList where mae contains UPDATED_MAE
        defaultDiscenteShouldNotBeFound("mae.contains=" + UPDATED_MAE);
    }

    @Test
    @Transactional
    void getAllDiscentesByMaeNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where mae does not contain DEFAULT_MAE
        defaultDiscenteShouldNotBeFound("mae.doesNotContain=" + DEFAULT_MAE);

        // Get all the discenteList where mae does not contain UPDATED_MAE
        defaultDiscenteShouldBeFound("mae.doesNotContain=" + UPDATED_MAE);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefonePrincipalIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefonePrincipal equals to DEFAULT_TELEFONE_PRINCIPAL
        defaultDiscenteShouldBeFound("telefonePrincipal.equals=" + DEFAULT_TELEFONE_PRINCIPAL);

        // Get all the discenteList where telefonePrincipal equals to UPDATED_TELEFONE_PRINCIPAL
        defaultDiscenteShouldNotBeFound("telefonePrincipal.equals=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefonePrincipalIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefonePrincipal in DEFAULT_TELEFONE_PRINCIPAL or UPDATED_TELEFONE_PRINCIPAL
        defaultDiscenteShouldBeFound("telefonePrincipal.in=" + DEFAULT_TELEFONE_PRINCIPAL + "," + UPDATED_TELEFONE_PRINCIPAL);

        // Get all the discenteList where telefonePrincipal equals to UPDATED_TELEFONE_PRINCIPAL
        defaultDiscenteShouldNotBeFound("telefonePrincipal.in=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefonePrincipalIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefonePrincipal is not null
        defaultDiscenteShouldBeFound("telefonePrincipal.specified=true");

        // Get all the discenteList where telefonePrincipal is null
        defaultDiscenteShouldNotBeFound("telefonePrincipal.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefonePrincipalContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefonePrincipal contains DEFAULT_TELEFONE_PRINCIPAL
        defaultDiscenteShouldBeFound("telefonePrincipal.contains=" + DEFAULT_TELEFONE_PRINCIPAL);

        // Get all the discenteList where telefonePrincipal contains UPDATED_TELEFONE_PRINCIPAL
        defaultDiscenteShouldNotBeFound("telefonePrincipal.contains=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefonePrincipalNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefonePrincipal does not contain DEFAULT_TELEFONE_PRINCIPAL
        defaultDiscenteShouldNotBeFound("telefonePrincipal.doesNotContain=" + DEFAULT_TELEFONE_PRINCIPAL);

        // Get all the discenteList where telefonePrincipal does not contain UPDATED_TELEFONE_PRINCIPAL
        defaultDiscenteShouldBeFound("telefonePrincipal.doesNotContain=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefoneParenteIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefoneParente equals to DEFAULT_TELEFONE_PARENTE
        defaultDiscenteShouldBeFound("telefoneParente.equals=" + DEFAULT_TELEFONE_PARENTE);

        // Get all the discenteList where telefoneParente equals to UPDATED_TELEFONE_PARENTE
        defaultDiscenteShouldNotBeFound("telefoneParente.equals=" + UPDATED_TELEFONE_PARENTE);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefoneParenteIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefoneParente in DEFAULT_TELEFONE_PARENTE or UPDATED_TELEFONE_PARENTE
        defaultDiscenteShouldBeFound("telefoneParente.in=" + DEFAULT_TELEFONE_PARENTE + "," + UPDATED_TELEFONE_PARENTE);

        // Get all the discenteList where telefoneParente equals to UPDATED_TELEFONE_PARENTE
        defaultDiscenteShouldNotBeFound("telefoneParente.in=" + UPDATED_TELEFONE_PARENTE);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefoneParenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefoneParente is not null
        defaultDiscenteShouldBeFound("telefoneParente.specified=true");

        // Get all the discenteList where telefoneParente is null
        defaultDiscenteShouldNotBeFound("telefoneParente.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefoneParenteContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefoneParente contains DEFAULT_TELEFONE_PARENTE
        defaultDiscenteShouldBeFound("telefoneParente.contains=" + DEFAULT_TELEFONE_PARENTE);

        // Get all the discenteList where telefoneParente contains UPDATED_TELEFONE_PARENTE
        defaultDiscenteShouldNotBeFound("telefoneParente.contains=" + UPDATED_TELEFONE_PARENTE);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefoneParenteNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefoneParente does not contain DEFAULT_TELEFONE_PARENTE
        defaultDiscenteShouldNotBeFound("telefoneParente.doesNotContain=" + DEFAULT_TELEFONE_PARENTE);

        // Get all the discenteList where telefoneParente does not contain UPDATED_TELEFONE_PARENTE
        defaultDiscenteShouldBeFound("telefoneParente.doesNotContain=" + UPDATED_TELEFONE_PARENTE);
    }

    @Test
    @Transactional
    void getAllDiscentesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where email equals to DEFAULT_EMAIL
        defaultDiscenteShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the discenteList where email equals to UPDATED_EMAIL
        defaultDiscenteShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDiscentesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultDiscenteShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the discenteList where email equals to UPDATED_EMAIL
        defaultDiscenteShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDiscentesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where email is not null
        defaultDiscenteShouldBeFound("email.specified=true");

        // Get all the discenteList where email is null
        defaultDiscenteShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByEmailContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where email contains DEFAULT_EMAIL
        defaultDiscenteShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the discenteList where email contains UPDATED_EMAIL
        defaultDiscenteShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDiscentesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where email does not contain DEFAULT_EMAIL
        defaultDiscenteShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the discenteList where email does not contain UPDATED_EMAIL
        defaultDiscenteShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsEncarregadoEducacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isEncarregadoEducacao equals to DEFAULT_IS_ENCARREGADO_EDUCACAO
        defaultDiscenteShouldBeFound("isEncarregadoEducacao.equals=" + DEFAULT_IS_ENCARREGADO_EDUCACAO);

        // Get all the discenteList where isEncarregadoEducacao equals to UPDATED_IS_ENCARREGADO_EDUCACAO
        defaultDiscenteShouldNotBeFound("isEncarregadoEducacao.equals=" + UPDATED_IS_ENCARREGADO_EDUCACAO);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsEncarregadoEducacaoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isEncarregadoEducacao in DEFAULT_IS_ENCARREGADO_EDUCACAO or UPDATED_IS_ENCARREGADO_EDUCACAO
        defaultDiscenteShouldBeFound("isEncarregadoEducacao.in=" + DEFAULT_IS_ENCARREGADO_EDUCACAO + "," + UPDATED_IS_ENCARREGADO_EDUCACAO);

        // Get all the discenteList where isEncarregadoEducacao equals to UPDATED_IS_ENCARREGADO_EDUCACAO
        defaultDiscenteShouldNotBeFound("isEncarregadoEducacao.in=" + UPDATED_IS_ENCARREGADO_EDUCACAO);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsEncarregadoEducacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isEncarregadoEducacao is not null
        defaultDiscenteShouldBeFound("isEncarregadoEducacao.specified=true");

        // Get all the discenteList where isEncarregadoEducacao is null
        defaultDiscenteShouldNotBeFound("isEncarregadoEducacao.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByIsTrabalhadorIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isTrabalhador equals to DEFAULT_IS_TRABALHADOR
        defaultDiscenteShouldBeFound("isTrabalhador.equals=" + DEFAULT_IS_TRABALHADOR);

        // Get all the discenteList where isTrabalhador equals to UPDATED_IS_TRABALHADOR
        defaultDiscenteShouldNotBeFound("isTrabalhador.equals=" + UPDATED_IS_TRABALHADOR);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsTrabalhadorIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isTrabalhador in DEFAULT_IS_TRABALHADOR or UPDATED_IS_TRABALHADOR
        defaultDiscenteShouldBeFound("isTrabalhador.in=" + DEFAULT_IS_TRABALHADOR + "," + UPDATED_IS_TRABALHADOR);

        // Get all the discenteList where isTrabalhador equals to UPDATED_IS_TRABALHADOR
        defaultDiscenteShouldNotBeFound("isTrabalhador.in=" + UPDATED_IS_TRABALHADOR);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsTrabalhadorIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isTrabalhador is not null
        defaultDiscenteShouldBeFound("isTrabalhador.specified=true");

        // Get all the discenteList where isTrabalhador is null
        defaultDiscenteShouldNotBeFound("isTrabalhador.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByIsFilhoAntigoConbatenteIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isFilhoAntigoConbatente equals to DEFAULT_IS_FILHO_ANTIGO_CONBATENTE
        defaultDiscenteShouldBeFound("isFilhoAntigoConbatente.equals=" + DEFAULT_IS_FILHO_ANTIGO_CONBATENTE);

        // Get all the discenteList where isFilhoAntigoConbatente equals to UPDATED_IS_FILHO_ANTIGO_CONBATENTE
        defaultDiscenteShouldNotBeFound("isFilhoAntigoConbatente.equals=" + UPDATED_IS_FILHO_ANTIGO_CONBATENTE);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsFilhoAntigoConbatenteIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isFilhoAntigoConbatente in DEFAULT_IS_FILHO_ANTIGO_CONBATENTE or UPDATED_IS_FILHO_ANTIGO_CONBATENTE
        defaultDiscenteShouldBeFound(
            "isFilhoAntigoConbatente.in=" + DEFAULT_IS_FILHO_ANTIGO_CONBATENTE + "," + UPDATED_IS_FILHO_ANTIGO_CONBATENTE
        );

        // Get all the discenteList where isFilhoAntigoConbatente equals to UPDATED_IS_FILHO_ANTIGO_CONBATENTE
        defaultDiscenteShouldNotBeFound("isFilhoAntigoConbatente.in=" + UPDATED_IS_FILHO_ANTIGO_CONBATENTE);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsFilhoAntigoConbatenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isFilhoAntigoConbatente is not null
        defaultDiscenteShouldBeFound("isFilhoAntigoConbatente.specified=true");

        // Get all the discenteList where isFilhoAntigoConbatente is null
        defaultDiscenteShouldNotBeFound("isFilhoAntigoConbatente.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAtestadoPobrezaIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAtestadoPobreza equals to DEFAULT_IS_ATESTADO_POBREZA
        defaultDiscenteShouldBeFound("isAtestadoPobreza.equals=" + DEFAULT_IS_ATESTADO_POBREZA);

        // Get all the discenteList where isAtestadoPobreza equals to UPDATED_IS_ATESTADO_POBREZA
        defaultDiscenteShouldNotBeFound("isAtestadoPobreza.equals=" + UPDATED_IS_ATESTADO_POBREZA);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAtestadoPobrezaIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAtestadoPobreza in DEFAULT_IS_ATESTADO_POBREZA or UPDATED_IS_ATESTADO_POBREZA
        defaultDiscenteShouldBeFound("isAtestadoPobreza.in=" + DEFAULT_IS_ATESTADO_POBREZA + "," + UPDATED_IS_ATESTADO_POBREZA);

        // Get all the discenteList where isAtestadoPobreza equals to UPDATED_IS_ATESTADO_POBREZA
        defaultDiscenteShouldNotBeFound("isAtestadoPobreza.in=" + UPDATED_IS_ATESTADO_POBREZA);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAtestadoPobrezaIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAtestadoPobreza is not null
        defaultDiscenteShouldBeFound("isAtestadoPobreza.specified=true");

        // Get all the discenteList where isAtestadoPobreza is null
        defaultDiscenteShouldNotBeFound("isAtestadoPobreza.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByNomeMedicoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nomeMedico equals to DEFAULT_NOME_MEDICO
        defaultDiscenteShouldBeFound("nomeMedico.equals=" + DEFAULT_NOME_MEDICO);

        // Get all the discenteList where nomeMedico equals to UPDATED_NOME_MEDICO
        defaultDiscenteShouldNotBeFound("nomeMedico.equals=" + UPDATED_NOME_MEDICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByNomeMedicoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nomeMedico in DEFAULT_NOME_MEDICO or UPDATED_NOME_MEDICO
        defaultDiscenteShouldBeFound("nomeMedico.in=" + DEFAULT_NOME_MEDICO + "," + UPDATED_NOME_MEDICO);

        // Get all the discenteList where nomeMedico equals to UPDATED_NOME_MEDICO
        defaultDiscenteShouldNotBeFound("nomeMedico.in=" + UPDATED_NOME_MEDICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByNomeMedicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nomeMedico is not null
        defaultDiscenteShouldBeFound("nomeMedico.specified=true");

        // Get all the discenteList where nomeMedico is null
        defaultDiscenteShouldNotBeFound("nomeMedico.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByNomeMedicoContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nomeMedico contains DEFAULT_NOME_MEDICO
        defaultDiscenteShouldBeFound("nomeMedico.contains=" + DEFAULT_NOME_MEDICO);

        // Get all the discenteList where nomeMedico contains UPDATED_NOME_MEDICO
        defaultDiscenteShouldNotBeFound("nomeMedico.contains=" + UPDATED_NOME_MEDICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByNomeMedicoNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where nomeMedico does not contain DEFAULT_NOME_MEDICO
        defaultDiscenteShouldNotBeFound("nomeMedico.doesNotContain=" + DEFAULT_NOME_MEDICO);

        // Get all the discenteList where nomeMedico does not contain UPDATED_NOME_MEDICO
        defaultDiscenteShouldBeFound("nomeMedico.doesNotContain=" + UPDATED_NOME_MEDICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefoneMedicoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefoneMedico equals to DEFAULT_TELEFONE_MEDICO
        defaultDiscenteShouldBeFound("telefoneMedico.equals=" + DEFAULT_TELEFONE_MEDICO);

        // Get all the discenteList where telefoneMedico equals to UPDATED_TELEFONE_MEDICO
        defaultDiscenteShouldNotBeFound("telefoneMedico.equals=" + UPDATED_TELEFONE_MEDICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefoneMedicoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefoneMedico in DEFAULT_TELEFONE_MEDICO or UPDATED_TELEFONE_MEDICO
        defaultDiscenteShouldBeFound("telefoneMedico.in=" + DEFAULT_TELEFONE_MEDICO + "," + UPDATED_TELEFONE_MEDICO);

        // Get all the discenteList where telefoneMedico equals to UPDATED_TELEFONE_MEDICO
        defaultDiscenteShouldNotBeFound("telefoneMedico.in=" + UPDATED_TELEFONE_MEDICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefoneMedicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefoneMedico is not null
        defaultDiscenteShouldBeFound("telefoneMedico.specified=true");

        // Get all the discenteList where telefoneMedico is null
        defaultDiscenteShouldNotBeFound("telefoneMedico.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefoneMedicoContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefoneMedico contains DEFAULT_TELEFONE_MEDICO
        defaultDiscenteShouldBeFound("telefoneMedico.contains=" + DEFAULT_TELEFONE_MEDICO);

        // Get all the discenteList where telefoneMedico contains UPDATED_TELEFONE_MEDICO
        defaultDiscenteShouldNotBeFound("telefoneMedico.contains=" + UPDATED_TELEFONE_MEDICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByTelefoneMedicoNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where telefoneMedico does not contain DEFAULT_TELEFONE_MEDICO
        defaultDiscenteShouldNotBeFound("telefoneMedico.doesNotContain=" + DEFAULT_TELEFONE_MEDICO);

        // Get all the discenteList where telefoneMedico does not contain UPDATED_TELEFONE_MEDICO
        defaultDiscenteShouldBeFound("telefoneMedico.doesNotContain=" + UPDATED_TELEFONE_MEDICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByInstituicaoParticularSaudeIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where instituicaoParticularSaude equals to DEFAULT_INSTITUICAO_PARTICULAR_SAUDE
        defaultDiscenteShouldBeFound("instituicaoParticularSaude.equals=" + DEFAULT_INSTITUICAO_PARTICULAR_SAUDE);

        // Get all the discenteList where instituicaoParticularSaude equals to UPDATED_INSTITUICAO_PARTICULAR_SAUDE
        defaultDiscenteShouldNotBeFound("instituicaoParticularSaude.equals=" + UPDATED_INSTITUICAO_PARTICULAR_SAUDE);
    }

    @Test
    @Transactional
    void getAllDiscentesByInstituicaoParticularSaudeIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where instituicaoParticularSaude in DEFAULT_INSTITUICAO_PARTICULAR_SAUDE or UPDATED_INSTITUICAO_PARTICULAR_SAUDE
        defaultDiscenteShouldBeFound(
            "instituicaoParticularSaude.in=" + DEFAULT_INSTITUICAO_PARTICULAR_SAUDE + "," + UPDATED_INSTITUICAO_PARTICULAR_SAUDE
        );

        // Get all the discenteList where instituicaoParticularSaude equals to UPDATED_INSTITUICAO_PARTICULAR_SAUDE
        defaultDiscenteShouldNotBeFound("instituicaoParticularSaude.in=" + UPDATED_INSTITUICAO_PARTICULAR_SAUDE);
    }

    @Test
    @Transactional
    void getAllDiscentesByInstituicaoParticularSaudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where instituicaoParticularSaude is not null
        defaultDiscenteShouldBeFound("instituicaoParticularSaude.specified=true");

        // Get all the discenteList where instituicaoParticularSaude is null
        defaultDiscenteShouldNotBeFound("instituicaoParticularSaude.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByInstituicaoParticularSaudeContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where instituicaoParticularSaude contains DEFAULT_INSTITUICAO_PARTICULAR_SAUDE
        defaultDiscenteShouldBeFound("instituicaoParticularSaude.contains=" + DEFAULT_INSTITUICAO_PARTICULAR_SAUDE);

        // Get all the discenteList where instituicaoParticularSaude contains UPDATED_INSTITUICAO_PARTICULAR_SAUDE
        defaultDiscenteShouldNotBeFound("instituicaoParticularSaude.contains=" + UPDATED_INSTITUICAO_PARTICULAR_SAUDE);
    }

    @Test
    @Transactional
    void getAllDiscentesByInstituicaoParticularSaudeNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where instituicaoParticularSaude does not contain DEFAULT_INSTITUICAO_PARTICULAR_SAUDE
        defaultDiscenteShouldNotBeFound("instituicaoParticularSaude.doesNotContain=" + DEFAULT_INSTITUICAO_PARTICULAR_SAUDE);

        // Get all the discenteList where instituicaoParticularSaude does not contain UPDATED_INSTITUICAO_PARTICULAR_SAUDE
        defaultDiscenteShouldBeFound("instituicaoParticularSaude.doesNotContain=" + UPDATED_INSTITUICAO_PARTICULAR_SAUDE);
    }

    @Test
    @Transactional
    void getAllDiscentesByAlturaIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where altura equals to DEFAULT_ALTURA
        defaultDiscenteShouldBeFound("altura.equals=" + DEFAULT_ALTURA);

        // Get all the discenteList where altura equals to UPDATED_ALTURA
        defaultDiscenteShouldNotBeFound("altura.equals=" + UPDATED_ALTURA);
    }

    @Test
    @Transactional
    void getAllDiscentesByAlturaIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where altura in DEFAULT_ALTURA or UPDATED_ALTURA
        defaultDiscenteShouldBeFound("altura.in=" + DEFAULT_ALTURA + "," + UPDATED_ALTURA);

        // Get all the discenteList where altura equals to UPDATED_ALTURA
        defaultDiscenteShouldNotBeFound("altura.in=" + UPDATED_ALTURA);
    }

    @Test
    @Transactional
    void getAllDiscentesByAlturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where altura is not null
        defaultDiscenteShouldBeFound("altura.specified=true");

        // Get all the discenteList where altura is null
        defaultDiscenteShouldNotBeFound("altura.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByAlturaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where altura is greater than or equal to DEFAULT_ALTURA
        defaultDiscenteShouldBeFound("altura.greaterThanOrEqual=" + DEFAULT_ALTURA);

        // Get all the discenteList where altura is greater than or equal to UPDATED_ALTURA
        defaultDiscenteShouldNotBeFound("altura.greaterThanOrEqual=" + UPDATED_ALTURA);
    }

    @Test
    @Transactional
    void getAllDiscentesByAlturaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where altura is less than or equal to DEFAULT_ALTURA
        defaultDiscenteShouldBeFound("altura.lessThanOrEqual=" + DEFAULT_ALTURA);

        // Get all the discenteList where altura is less than or equal to SMALLER_ALTURA
        defaultDiscenteShouldNotBeFound("altura.lessThanOrEqual=" + SMALLER_ALTURA);
    }

    @Test
    @Transactional
    void getAllDiscentesByAlturaIsLessThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where altura is less than DEFAULT_ALTURA
        defaultDiscenteShouldNotBeFound("altura.lessThan=" + DEFAULT_ALTURA);

        // Get all the discenteList where altura is less than UPDATED_ALTURA
        defaultDiscenteShouldBeFound("altura.lessThan=" + UPDATED_ALTURA);
    }

    @Test
    @Transactional
    void getAllDiscentesByAlturaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where altura is greater than DEFAULT_ALTURA
        defaultDiscenteShouldNotBeFound("altura.greaterThan=" + DEFAULT_ALTURA);

        // Get all the discenteList where altura is greater than SMALLER_ALTURA
        defaultDiscenteShouldBeFound("altura.greaterThan=" + SMALLER_ALTURA);
    }

    @Test
    @Transactional
    void getAllDiscentesByPesoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where peso equals to DEFAULT_PESO
        defaultDiscenteShouldBeFound("peso.equals=" + DEFAULT_PESO);

        // Get all the discenteList where peso equals to UPDATED_PESO
        defaultDiscenteShouldNotBeFound("peso.equals=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllDiscentesByPesoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where peso in DEFAULT_PESO or UPDATED_PESO
        defaultDiscenteShouldBeFound("peso.in=" + DEFAULT_PESO + "," + UPDATED_PESO);

        // Get all the discenteList where peso equals to UPDATED_PESO
        defaultDiscenteShouldNotBeFound("peso.in=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllDiscentesByPesoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where peso is not null
        defaultDiscenteShouldBeFound("peso.specified=true");

        // Get all the discenteList where peso is null
        defaultDiscenteShouldNotBeFound("peso.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByPesoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where peso is greater than or equal to DEFAULT_PESO
        defaultDiscenteShouldBeFound("peso.greaterThanOrEqual=" + DEFAULT_PESO);

        // Get all the discenteList where peso is greater than or equal to UPDATED_PESO
        defaultDiscenteShouldNotBeFound("peso.greaterThanOrEqual=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllDiscentesByPesoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where peso is less than or equal to DEFAULT_PESO
        defaultDiscenteShouldBeFound("peso.lessThanOrEqual=" + DEFAULT_PESO);

        // Get all the discenteList where peso is less than or equal to SMALLER_PESO
        defaultDiscenteShouldNotBeFound("peso.lessThanOrEqual=" + SMALLER_PESO);
    }

    @Test
    @Transactional
    void getAllDiscentesByPesoIsLessThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where peso is less than DEFAULT_PESO
        defaultDiscenteShouldNotBeFound("peso.lessThan=" + DEFAULT_PESO);

        // Get all the discenteList where peso is less than UPDATED_PESO
        defaultDiscenteShouldBeFound("peso.lessThan=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllDiscentesByPesoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where peso is greater than DEFAULT_PESO
        defaultDiscenteShouldNotBeFound("peso.greaterThan=" + DEFAULT_PESO);

        // Get all the discenteList where peso is greater than SMALLER_PESO
        defaultDiscenteShouldBeFound("peso.greaterThan=" + SMALLER_PESO);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAsmaticoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAsmatico equals to DEFAULT_IS_ASMATICO
        defaultDiscenteShouldBeFound("isAsmatico.equals=" + DEFAULT_IS_ASMATICO);

        // Get all the discenteList where isAsmatico equals to UPDATED_IS_ASMATICO
        defaultDiscenteShouldNotBeFound("isAsmatico.equals=" + UPDATED_IS_ASMATICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAsmaticoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAsmatico in DEFAULT_IS_ASMATICO or UPDATED_IS_ASMATICO
        defaultDiscenteShouldBeFound("isAsmatico.in=" + DEFAULT_IS_ASMATICO + "," + UPDATED_IS_ASMATICO);

        // Get all the discenteList where isAsmatico equals to UPDATED_IS_ASMATICO
        defaultDiscenteShouldNotBeFound("isAsmatico.in=" + UPDATED_IS_ASMATICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAsmaticoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAsmatico is not null
        defaultDiscenteShouldBeFound("isAsmatico.specified=true");

        // Get all the discenteList where isAsmatico is null
        defaultDiscenteShouldNotBeFound("isAsmatico.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAlergicoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAlergico equals to DEFAULT_IS_ALERGICO
        defaultDiscenteShouldBeFound("isAlergico.equals=" + DEFAULT_IS_ALERGICO);

        // Get all the discenteList where isAlergico equals to UPDATED_IS_ALERGICO
        defaultDiscenteShouldNotBeFound("isAlergico.equals=" + UPDATED_IS_ALERGICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAlergicoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAlergico in DEFAULT_IS_ALERGICO or UPDATED_IS_ALERGICO
        defaultDiscenteShouldBeFound("isAlergico.in=" + DEFAULT_IS_ALERGICO + "," + UPDATED_IS_ALERGICO);

        // Get all the discenteList where isAlergico equals to UPDATED_IS_ALERGICO
        defaultDiscenteShouldNotBeFound("isAlergico.in=" + UPDATED_IS_ALERGICO);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAlergicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAlergico is not null
        defaultDiscenteShouldBeFound("isAlergico.specified=true");

        // Get all the discenteList where isAlergico is null
        defaultDiscenteShouldNotBeFound("isAlergico.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByIsPraticaEducacaoFisicaIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isPraticaEducacaoFisica equals to DEFAULT_IS_PRATICA_EDUCACAO_FISICA
        defaultDiscenteShouldBeFound("isPraticaEducacaoFisica.equals=" + DEFAULT_IS_PRATICA_EDUCACAO_FISICA);

        // Get all the discenteList where isPraticaEducacaoFisica equals to UPDATED_IS_PRATICA_EDUCACAO_FISICA
        defaultDiscenteShouldNotBeFound("isPraticaEducacaoFisica.equals=" + UPDATED_IS_PRATICA_EDUCACAO_FISICA);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsPraticaEducacaoFisicaIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isPraticaEducacaoFisica in DEFAULT_IS_PRATICA_EDUCACAO_FISICA or UPDATED_IS_PRATICA_EDUCACAO_FISICA
        defaultDiscenteShouldBeFound(
            "isPraticaEducacaoFisica.in=" + DEFAULT_IS_PRATICA_EDUCACAO_FISICA + "," + UPDATED_IS_PRATICA_EDUCACAO_FISICA
        );

        // Get all the discenteList where isPraticaEducacaoFisica equals to UPDATED_IS_PRATICA_EDUCACAO_FISICA
        defaultDiscenteShouldNotBeFound("isPraticaEducacaoFisica.in=" + UPDATED_IS_PRATICA_EDUCACAO_FISICA);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsPraticaEducacaoFisicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isPraticaEducacaoFisica is not null
        defaultDiscenteShouldBeFound("isPraticaEducacaoFisica.specified=true");

        // Get all the discenteList where isPraticaEducacaoFisica is null
        defaultDiscenteShouldNotBeFound("isPraticaEducacaoFisica.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAutorizadoMedicacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAutorizadoMedicacao equals to DEFAULT_IS_AUTORIZADO_MEDICACAO
        defaultDiscenteShouldBeFound("isAutorizadoMedicacao.equals=" + DEFAULT_IS_AUTORIZADO_MEDICACAO);

        // Get all the discenteList where isAutorizadoMedicacao equals to UPDATED_IS_AUTORIZADO_MEDICACAO
        defaultDiscenteShouldNotBeFound("isAutorizadoMedicacao.equals=" + UPDATED_IS_AUTORIZADO_MEDICACAO);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAutorizadoMedicacaoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAutorizadoMedicacao in DEFAULT_IS_AUTORIZADO_MEDICACAO or UPDATED_IS_AUTORIZADO_MEDICACAO
        defaultDiscenteShouldBeFound("isAutorizadoMedicacao.in=" + DEFAULT_IS_AUTORIZADO_MEDICACAO + "," + UPDATED_IS_AUTORIZADO_MEDICACAO);

        // Get all the discenteList where isAutorizadoMedicacao equals to UPDATED_IS_AUTORIZADO_MEDICACAO
        defaultDiscenteShouldNotBeFound("isAutorizadoMedicacao.in=" + UPDATED_IS_AUTORIZADO_MEDICACAO);
    }

    @Test
    @Transactional
    void getAllDiscentesByIsAutorizadoMedicacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where isAutorizadoMedicacao is not null
        defaultDiscenteShouldBeFound("isAutorizadoMedicacao.specified=true");

        // Get all the discenteList where isAutorizadoMedicacao is null
        defaultDiscenteShouldNotBeFound("isAutorizadoMedicacao.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByNumeroProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where numeroProcesso equals to DEFAULT_NUMERO_PROCESSO
        defaultDiscenteShouldBeFound("numeroProcesso.equals=" + DEFAULT_NUMERO_PROCESSO);

        // Get all the discenteList where numeroProcesso equals to UPDATED_NUMERO_PROCESSO
        defaultDiscenteShouldNotBeFound("numeroProcesso.equals=" + UPDATED_NUMERO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllDiscentesByNumeroProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where numeroProcesso in DEFAULT_NUMERO_PROCESSO or UPDATED_NUMERO_PROCESSO
        defaultDiscenteShouldBeFound("numeroProcesso.in=" + DEFAULT_NUMERO_PROCESSO + "," + UPDATED_NUMERO_PROCESSO);

        // Get all the discenteList where numeroProcesso equals to UPDATED_NUMERO_PROCESSO
        defaultDiscenteShouldNotBeFound("numeroProcesso.in=" + UPDATED_NUMERO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllDiscentesByNumeroProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where numeroProcesso is not null
        defaultDiscenteShouldBeFound("numeroProcesso.specified=true");

        // Get all the discenteList where numeroProcesso is null
        defaultDiscenteShouldNotBeFound("numeroProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByNumeroProcessoContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where numeroProcesso contains DEFAULT_NUMERO_PROCESSO
        defaultDiscenteShouldBeFound("numeroProcesso.contains=" + DEFAULT_NUMERO_PROCESSO);

        // Get all the discenteList where numeroProcesso contains UPDATED_NUMERO_PROCESSO
        defaultDiscenteShouldNotBeFound("numeroProcesso.contains=" + UPDATED_NUMERO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllDiscentesByNumeroProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where numeroProcesso does not contain DEFAULT_NUMERO_PROCESSO
        defaultDiscenteShouldNotBeFound("numeroProcesso.doesNotContain=" + DEFAULT_NUMERO_PROCESSO);

        // Get all the discenteList where numeroProcesso does not contain UPDATED_NUMERO_PROCESSO
        defaultDiscenteShouldBeFound("numeroProcesso.doesNotContain=" + UPDATED_NUMERO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDataIngressoIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where dataIngresso equals to DEFAULT_DATA_INGRESSO
        defaultDiscenteShouldBeFound("dataIngresso.equals=" + DEFAULT_DATA_INGRESSO);

        // Get all the discenteList where dataIngresso equals to UPDATED_DATA_INGRESSO
        defaultDiscenteShouldNotBeFound("dataIngresso.equals=" + UPDATED_DATA_INGRESSO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDataIngressoIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where dataIngresso in DEFAULT_DATA_INGRESSO or UPDATED_DATA_INGRESSO
        defaultDiscenteShouldBeFound("dataIngresso.in=" + DEFAULT_DATA_INGRESSO + "," + UPDATED_DATA_INGRESSO);

        // Get all the discenteList where dataIngresso equals to UPDATED_DATA_INGRESSO
        defaultDiscenteShouldNotBeFound("dataIngresso.in=" + UPDATED_DATA_INGRESSO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDataIngressoIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where dataIngresso is not null
        defaultDiscenteShouldBeFound("dataIngresso.specified=true");

        // Get all the discenteList where dataIngresso is null
        defaultDiscenteShouldNotBeFound("dataIngresso.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByDataIngressoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where dataIngresso is greater than or equal to DEFAULT_DATA_INGRESSO
        defaultDiscenteShouldBeFound("dataIngresso.greaterThanOrEqual=" + DEFAULT_DATA_INGRESSO);

        // Get all the discenteList where dataIngresso is greater than or equal to UPDATED_DATA_INGRESSO
        defaultDiscenteShouldNotBeFound("dataIngresso.greaterThanOrEqual=" + UPDATED_DATA_INGRESSO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDataIngressoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where dataIngresso is less than or equal to DEFAULT_DATA_INGRESSO
        defaultDiscenteShouldBeFound("dataIngresso.lessThanOrEqual=" + DEFAULT_DATA_INGRESSO);

        // Get all the discenteList where dataIngresso is less than or equal to SMALLER_DATA_INGRESSO
        defaultDiscenteShouldNotBeFound("dataIngresso.lessThanOrEqual=" + SMALLER_DATA_INGRESSO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDataIngressoIsLessThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where dataIngresso is less than DEFAULT_DATA_INGRESSO
        defaultDiscenteShouldNotBeFound("dataIngresso.lessThan=" + DEFAULT_DATA_INGRESSO);

        // Get all the discenteList where dataIngresso is less than UPDATED_DATA_INGRESSO
        defaultDiscenteShouldBeFound("dataIngresso.lessThan=" + UPDATED_DATA_INGRESSO);
    }

    @Test
    @Transactional
    void getAllDiscentesByDataIngressoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where dataIngresso is greater than DEFAULT_DATA_INGRESSO
        defaultDiscenteShouldNotBeFound("dataIngresso.greaterThan=" + DEFAULT_DATA_INGRESSO);

        // Get all the discenteList where dataIngresso is greater than SMALLER_DATA_INGRESSO
        defaultDiscenteShouldBeFound("dataIngresso.greaterThan=" + SMALLER_DATA_INGRESSO);
    }

    @Test
    @Transactional
    void getAllDiscentesByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where hash equals to DEFAULT_HASH
        defaultDiscenteShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the discenteList where hash equals to UPDATED_HASH
        defaultDiscenteShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDiscentesByHashIsInShouldWork() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultDiscenteShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the discenteList where hash equals to UPDATED_HASH
        defaultDiscenteShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDiscentesByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where hash is not null
        defaultDiscenteShouldBeFound("hash.specified=true");

        // Get all the discenteList where hash is null
        defaultDiscenteShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllDiscentesByHashContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where hash contains DEFAULT_HASH
        defaultDiscenteShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the discenteList where hash contains UPDATED_HASH
        defaultDiscenteShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDiscentesByHashNotContainsSomething() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        // Get all the discenteList where hash does not contain DEFAULT_HASH
        defaultDiscenteShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the discenteList where hash does not contain UPDATED_HASH
        defaultDiscenteShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllDiscentesByEnderecosIsEqualToSomething() throws Exception {
        EnderecoDiscente enderecos;
        if (TestUtil.findAll(em, EnderecoDiscente.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            enderecos = EnderecoDiscenteResourceIT.createEntity(em);
        } else {
            enderecos = TestUtil.findAll(em, EnderecoDiscente.class).get(0);
        }
        em.persist(enderecos);
        em.flush();
        discente.addEnderecos(enderecos);
        discenteRepository.saveAndFlush(discente);
        Long enderecosId = enderecos.getId();

        // Get all the discenteList where enderecos equals to enderecosId
        defaultDiscenteShouldBeFound("enderecosId.equals=" + enderecosId);

        // Get all the discenteList where enderecos equals to (enderecosId + 1)
        defaultDiscenteShouldNotBeFound("enderecosId.equals=" + (enderecosId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByProcessosSelectivoIsEqualToSomething() throws Exception {
        ProcessoSelectivoMatricula processosSelectivo;
        if (TestUtil.findAll(em, ProcessoSelectivoMatricula.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            processosSelectivo = ProcessoSelectivoMatriculaResourceIT.createEntity(em);
        } else {
            processosSelectivo = TestUtil.findAll(em, ProcessoSelectivoMatricula.class).get(0);
        }
        em.persist(processosSelectivo);
        em.flush();
        discente.addProcessosSelectivo(processosSelectivo);
        discenteRepository.saveAndFlush(discente);
        Long processosSelectivoId = processosSelectivo.getId();

        // Get all the discenteList where processosSelectivo equals to processosSelectivoId
        defaultDiscenteShouldBeFound("processosSelectivoId.equals=" + processosSelectivoId);

        // Get all the discenteList where processosSelectivo equals to (processosSelectivoId + 1)
        defaultDiscenteShouldNotBeFound("processosSelectivoId.equals=" + (processosSelectivoId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByAnexoDiscenteIsEqualToSomething() throws Exception {
        AnexoDiscente anexoDiscente;
        if (TestUtil.findAll(em, AnexoDiscente.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            anexoDiscente = AnexoDiscenteResourceIT.createEntity(em);
        } else {
            anexoDiscente = TestUtil.findAll(em, AnexoDiscente.class).get(0);
        }
        em.persist(anexoDiscente);
        em.flush();
        discente.addAnexoDiscente(anexoDiscente);
        discenteRepository.saveAndFlush(discente);
        Long anexoDiscenteId = anexoDiscente.getId();

        // Get all the discenteList where anexoDiscente equals to anexoDiscenteId
        defaultDiscenteShouldBeFound("anexoDiscenteId.equals=" + anexoDiscenteId);

        // Get all the discenteList where anexoDiscente equals to (anexoDiscenteId + 1)
        defaultDiscenteShouldNotBeFound("anexoDiscenteId.equals=" + (anexoDiscenteId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByMatriculasIsEqualToSomething() throws Exception {
        Matricula matriculas;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            matriculas = MatriculaResourceIT.createEntity(em);
        } else {
            matriculas = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(matriculas);
        em.flush();
        discente.addMatriculas(matriculas);
        discenteRepository.saveAndFlush(discente);
        Long matriculasId = matriculas.getId();

        // Get all the discenteList where matriculas equals to matriculasId
        defaultDiscenteShouldBeFound("matriculasId.equals=" + matriculasId);

        // Get all the discenteList where matriculas equals to (matriculasId + 1)
        defaultDiscenteShouldNotBeFound("matriculasId.equals=" + (matriculasId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByResumoAcademicoIsEqualToSomething() throws Exception {
        ResumoAcademico resumoAcademico;
        if (TestUtil.findAll(em, ResumoAcademico.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            resumoAcademico = ResumoAcademicoResourceIT.createEntity(em);
        } else {
            resumoAcademico = TestUtil.findAll(em, ResumoAcademico.class).get(0);
        }
        em.persist(resumoAcademico);
        em.flush();
        discente.addResumoAcademico(resumoAcademico);
        discenteRepository.saveAndFlush(discente);
        Long resumoAcademicoId = resumoAcademico.getId();

        // Get all the discenteList where resumoAcademico equals to resumoAcademicoId
        defaultDiscenteShouldBeFound("resumoAcademicoId.equals=" + resumoAcademicoId);

        // Get all the discenteList where resumoAcademico equals to (resumoAcademicoId + 1)
        defaultDiscenteShouldNotBeFound("resumoAcademicoId.equals=" + (resumoAcademicoId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByHistoricosSaudeIsEqualToSomething() throws Exception {
        HistoricoSaude historicosSaude;
        if (TestUtil.findAll(em, HistoricoSaude.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            historicosSaude = HistoricoSaudeResourceIT.createEntity(em);
        } else {
            historicosSaude = TestUtil.findAll(em, HistoricoSaude.class).get(0);
        }
        em.persist(historicosSaude);
        em.flush();
        discente.addHistoricosSaude(historicosSaude);
        discenteRepository.saveAndFlush(discente);
        Long historicosSaudeId = historicosSaude.getId();

        // Get all the discenteList where historicosSaude equals to historicosSaudeId
        defaultDiscenteShouldBeFound("historicosSaudeId.equals=" + historicosSaudeId);

        // Get all the discenteList where historicosSaude equals to (historicosSaudeId + 1)
        defaultDiscenteShouldNotBeFound("historicosSaudeId.equals=" + (historicosSaudeId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByDissertacaoFinalCursoIsEqualToSomething() throws Exception {
        DissertacaoFinalCurso dissertacaoFinalCurso;
        if (TestUtil.findAll(em, DissertacaoFinalCurso.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            dissertacaoFinalCurso = DissertacaoFinalCursoResourceIT.createEntity(em);
        } else {
            dissertacaoFinalCurso = TestUtil.findAll(em, DissertacaoFinalCurso.class).get(0);
        }
        em.persist(dissertacaoFinalCurso);
        em.flush();
        discente.addDissertacaoFinalCurso(dissertacaoFinalCurso);
        discenteRepository.saveAndFlush(discente);
        Long dissertacaoFinalCursoId = dissertacaoFinalCurso.getId();

        // Get all the discenteList where dissertacaoFinalCurso equals to dissertacaoFinalCursoId
        defaultDiscenteShouldBeFound("dissertacaoFinalCursoId.equals=" + dissertacaoFinalCursoId);

        // Get all the discenteList where dissertacaoFinalCurso equals to (dissertacaoFinalCursoId + 1)
        defaultDiscenteShouldNotBeFound("dissertacaoFinalCursoId.equals=" + (dissertacaoFinalCursoId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByNacionalidadeIsEqualToSomething() throws Exception {
        LookupItem nacionalidade;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            nacionalidade = LookupItemResourceIT.createEntity(em);
        } else {
            nacionalidade = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(nacionalidade);
        em.flush();
        discente.setNacionalidade(nacionalidade);
        discenteRepository.saveAndFlush(discente);
        Long nacionalidadeId = nacionalidade.getId();

        // Get all the discenteList where nacionalidade equals to nacionalidadeId
        defaultDiscenteShouldBeFound("nacionalidadeId.equals=" + nacionalidadeId);

        // Get all the discenteList where nacionalidade equals to (nacionalidadeId + 1)
        defaultDiscenteShouldNotBeFound("nacionalidadeId.equals=" + (nacionalidadeId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByNaturalidadeIsEqualToSomething() throws Exception {
        LookupItem naturalidade;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            naturalidade = LookupItemResourceIT.createEntity(em);
        } else {
            naturalidade = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(naturalidade);
        em.flush();
        discente.setNaturalidade(naturalidade);
        discenteRepository.saveAndFlush(discente);
        Long naturalidadeId = naturalidade.getId();

        // Get all the discenteList where naturalidade equals to naturalidadeId
        defaultDiscenteShouldBeFound("naturalidadeId.equals=" + naturalidadeId);

        // Get all the discenteList where naturalidade equals to (naturalidadeId + 1)
        defaultDiscenteShouldNotBeFound("naturalidadeId.equals=" + (naturalidadeId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByTipoDocumentoIsEqualToSomething() throws Exception {
        LookupItem tipoDocumento;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            tipoDocumento = LookupItemResourceIT.createEntity(em);
        } else {
            tipoDocumento = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(tipoDocumento);
        em.flush();
        discente.setTipoDocumento(tipoDocumento);
        discenteRepository.saveAndFlush(discente);
        Long tipoDocumentoId = tipoDocumento.getId();

        // Get all the discenteList where tipoDocumento equals to tipoDocumentoId
        defaultDiscenteShouldBeFound("tipoDocumentoId.equals=" + tipoDocumentoId);

        // Get all the discenteList where tipoDocumento equals to (tipoDocumentoId + 1)
        defaultDiscenteShouldNotBeFound("tipoDocumentoId.equals=" + (tipoDocumentoId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByProfissaoIsEqualToSomething() throws Exception {
        LookupItem profissao;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            profissao = LookupItemResourceIT.createEntity(em);
        } else {
            profissao = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(profissao);
        em.flush();
        discente.setProfissao(profissao);
        discenteRepository.saveAndFlush(discente);
        Long profissaoId = profissao.getId();

        // Get all the discenteList where profissao equals to profissaoId
        defaultDiscenteShouldBeFound("profissaoId.equals=" + profissaoId);

        // Get all the discenteList where profissao equals to (profissaoId + 1)
        defaultDiscenteShouldNotBeFound("profissaoId.equals=" + (profissaoId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByGrupoSanguinioIsEqualToSomething() throws Exception {
        LookupItem grupoSanguinio;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            grupoSanguinio = LookupItemResourceIT.createEntity(em);
        } else {
            grupoSanguinio = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(grupoSanguinio);
        em.flush();
        discente.setGrupoSanguinio(grupoSanguinio);
        discenteRepository.saveAndFlush(discente);
        Long grupoSanguinioId = grupoSanguinio.getId();

        // Get all the discenteList where grupoSanguinio equals to grupoSanguinioId
        defaultDiscenteShouldBeFound("grupoSanguinioId.equals=" + grupoSanguinioId);

        // Get all the discenteList where grupoSanguinio equals to (grupoSanguinioId + 1)
        defaultDiscenteShouldNotBeFound("grupoSanguinioId.equals=" + (grupoSanguinioId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByNecessidadeEspecialIsEqualToSomething() throws Exception {
        LookupItem necessidadeEspecial;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            necessidadeEspecial = LookupItemResourceIT.createEntity(em);
        } else {
            necessidadeEspecial = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(necessidadeEspecial);
        em.flush();
        discente.setNecessidadeEspecial(necessidadeEspecial);
        discenteRepository.saveAndFlush(discente);
        Long necessidadeEspecialId = necessidadeEspecial.getId();

        // Get all the discenteList where necessidadeEspecial equals to necessidadeEspecialId
        defaultDiscenteShouldBeFound("necessidadeEspecialId.equals=" + necessidadeEspecialId);

        // Get all the discenteList where necessidadeEspecial equals to (necessidadeEspecialId + 1)
        defaultDiscenteShouldNotBeFound("necessidadeEspecialId.equals=" + (necessidadeEspecialId + 1));
    }

    @Test
    @Transactional
    void getAllDiscentesByEncarregadoEducacaoIsEqualToSomething() throws Exception {
        EncarregadoEducacao encarregadoEducacao;
        if (TestUtil.findAll(em, EncarregadoEducacao.class).isEmpty()) {
            discenteRepository.saveAndFlush(discente);
            encarregadoEducacao = EncarregadoEducacaoResourceIT.createEntity(em);
        } else {
            encarregadoEducacao = TestUtil.findAll(em, EncarregadoEducacao.class).get(0);
        }
        em.persist(encarregadoEducacao);
        em.flush();
        discente.setEncarregadoEducacao(encarregadoEducacao);
        discenteRepository.saveAndFlush(discente);
        Long encarregadoEducacaoId = encarregadoEducacao.getId();

        // Get all the discenteList where encarregadoEducacao equals to encarregadoEducacaoId
        defaultDiscenteShouldBeFound("encarregadoEducacaoId.equals=" + encarregadoEducacaoId);

        // Get all the discenteList where encarregadoEducacao equals to (encarregadoEducacaoId + 1)
        defaultDiscenteShouldNotBeFound("encarregadoEducacaoId.equals=" + (encarregadoEducacaoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDiscenteShouldBeFound(String filter) throws Exception {
        restDiscenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discente.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotografiaContentType").value(hasItem(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA))))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nascimento").value(hasItem(DEFAULT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].documentoNumero").value(hasItem(DEFAULT_DOCUMENTO_NUMERO)))
            .andExpect(jsonPath("$.[*].documentoEmissao").value(hasItem(DEFAULT_DOCUMENTO_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].documentoValidade").value(hasItem(DEFAULT_DOCUMENTO_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].pai").value(hasItem(DEFAULT_PAI)))
            .andExpect(jsonPath("$.[*].mae").value(hasItem(DEFAULT_MAE)))
            .andExpect(jsonPath("$.[*].telefonePrincipal").value(hasItem(DEFAULT_TELEFONE_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].telefoneParente").value(hasItem(DEFAULT_TELEFONE_PARENTE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].isEncarregadoEducacao").value(hasItem(DEFAULT_IS_ENCARREGADO_EDUCACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].isTrabalhador").value(hasItem(DEFAULT_IS_TRABALHADOR.booleanValue())))
            .andExpect(jsonPath("$.[*].isFilhoAntigoConbatente").value(hasItem(DEFAULT_IS_FILHO_ANTIGO_CONBATENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].isAtestadoPobreza").value(hasItem(DEFAULT_IS_ATESTADO_POBREZA.booleanValue())))
            .andExpect(jsonPath("$.[*].nomeMedico").value(hasItem(DEFAULT_NOME_MEDICO)))
            .andExpect(jsonPath("$.[*].telefoneMedico").value(hasItem(DEFAULT_TELEFONE_MEDICO)))
            .andExpect(jsonPath("$.[*].instituicaoParticularSaude").value(hasItem(DEFAULT_INSTITUICAO_PARTICULAR_SAUDE)))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].isAsmatico").value(hasItem(DEFAULT_IS_ASMATICO.booleanValue())))
            .andExpect(jsonPath("$.[*].isAlergico").value(hasItem(DEFAULT_IS_ALERGICO.booleanValue())))
            .andExpect(jsonPath("$.[*].isPraticaEducacaoFisica").value(hasItem(DEFAULT_IS_PRATICA_EDUCACAO_FISICA.booleanValue())))
            .andExpect(jsonPath("$.[*].isAutorizadoMedicacao").value(hasItem(DEFAULT_IS_AUTORIZADO_MEDICACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].cuidadosEspeciaisSaude").value(hasItem(DEFAULT_CUIDADOS_ESPECIAIS_SAUDE.toString())))
            .andExpect(jsonPath("$.[*].numeroProcesso").value(hasItem(DEFAULT_NUMERO_PROCESSO)))
            .andExpect(jsonPath("$.[*].dataIngresso").value(hasItem(sameInstant(DEFAULT_DATA_INGRESSO))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));

        // Check, that the count call also returns 1
        restDiscenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDiscenteShouldNotBeFound(String filter) throws Exception {
        restDiscenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDiscenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDiscente() throws Exception {
        // Get the discente
        restDiscenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDiscente() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        int databaseSizeBeforeUpdate = discenteRepository.findAll().size();

        // Update the discente
        Discente updatedDiscente = discenteRepository.findById(discente.getId()).get();
        // Disconnect from session so that the updates on updatedDiscente are not directly saved in db
        em.detach(updatedDiscente);
        updatedDiscente
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .nascimento(UPDATED_NASCIMENTO)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO)
            .documentoEmissao(UPDATED_DOCUMENTO_EMISSAO)
            .documentoValidade(UPDATED_DOCUMENTO_VALIDADE)
            .nif(UPDATED_NIF)
            .sexo(UPDATED_SEXO)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneParente(UPDATED_TELEFONE_PARENTE)
            .email(UPDATED_EMAIL)
            .isEncarregadoEducacao(UPDATED_IS_ENCARREGADO_EDUCACAO)
            .isTrabalhador(UPDATED_IS_TRABALHADOR)
            .isFilhoAntigoConbatente(UPDATED_IS_FILHO_ANTIGO_CONBATENTE)
            .isAtestadoPobreza(UPDATED_IS_ATESTADO_POBREZA)
            .nomeMedico(UPDATED_NOME_MEDICO)
            .telefoneMedico(UPDATED_TELEFONE_MEDICO)
            .instituicaoParticularSaude(UPDATED_INSTITUICAO_PARTICULAR_SAUDE)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO)
            .isAsmatico(UPDATED_IS_ASMATICO)
            .isAlergico(UPDATED_IS_ALERGICO)
            .isPraticaEducacaoFisica(UPDATED_IS_PRATICA_EDUCACAO_FISICA)
            .isAutorizadoMedicacao(UPDATED_IS_AUTORIZADO_MEDICACAO)
            .cuidadosEspeciaisSaude(UPDATED_CUIDADOS_ESPECIAIS_SAUDE)
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .dataIngresso(UPDATED_DATA_INGRESSO)
            .hash(UPDATED_HASH)
            .observacao(UPDATED_OBSERVACAO);
        DiscenteDTO discenteDTO = discenteMapper.toDto(updatedDiscente);

        restDiscenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, discenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(discenteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Discente in the database
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeUpdate);
        Discente testDiscente = discenteList.get(discenteList.size() - 1);
        assertThat(testDiscente.getFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testDiscente.getFotografiaContentType()).isEqualTo(UPDATED_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testDiscente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDiscente.getNascimento()).isEqualTo(UPDATED_NASCIMENTO);
        assertThat(testDiscente.getDocumentoNumero()).isEqualTo(UPDATED_DOCUMENTO_NUMERO);
        assertThat(testDiscente.getDocumentoEmissao()).isEqualTo(UPDATED_DOCUMENTO_EMISSAO);
        assertThat(testDiscente.getDocumentoValidade()).isEqualTo(UPDATED_DOCUMENTO_VALIDADE);
        assertThat(testDiscente.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testDiscente.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testDiscente.getPai()).isEqualTo(UPDATED_PAI);
        assertThat(testDiscente.getMae()).isEqualTo(UPDATED_MAE);
        assertThat(testDiscente.getTelefonePrincipal()).isEqualTo(UPDATED_TELEFONE_PRINCIPAL);
        assertThat(testDiscente.getTelefoneParente()).isEqualTo(UPDATED_TELEFONE_PARENTE);
        assertThat(testDiscente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDiscente.getIsEncarregadoEducacao()).isEqualTo(UPDATED_IS_ENCARREGADO_EDUCACAO);
        assertThat(testDiscente.getIsTrabalhador()).isEqualTo(UPDATED_IS_TRABALHADOR);
        assertThat(testDiscente.getIsFilhoAntigoConbatente()).isEqualTo(UPDATED_IS_FILHO_ANTIGO_CONBATENTE);
        assertThat(testDiscente.getIsAtestadoPobreza()).isEqualTo(UPDATED_IS_ATESTADO_POBREZA);
        assertThat(testDiscente.getNomeMedico()).isEqualTo(UPDATED_NOME_MEDICO);
        assertThat(testDiscente.getTelefoneMedico()).isEqualTo(UPDATED_TELEFONE_MEDICO);
        assertThat(testDiscente.getInstituicaoParticularSaude()).isEqualTo(UPDATED_INSTITUICAO_PARTICULAR_SAUDE);
        assertThat(testDiscente.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testDiscente.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testDiscente.getIsAsmatico()).isEqualTo(UPDATED_IS_ASMATICO);
        assertThat(testDiscente.getIsAlergico()).isEqualTo(UPDATED_IS_ALERGICO);
        assertThat(testDiscente.getIsPraticaEducacaoFisica()).isEqualTo(UPDATED_IS_PRATICA_EDUCACAO_FISICA);
        assertThat(testDiscente.getIsAutorizadoMedicacao()).isEqualTo(UPDATED_IS_AUTORIZADO_MEDICACAO);
        assertThat(testDiscente.getCuidadosEspeciaisSaude()).isEqualTo(UPDATED_CUIDADOS_ESPECIAIS_SAUDE);
        assertThat(testDiscente.getNumeroProcesso()).isEqualTo(UPDATED_NUMERO_PROCESSO);
        assertThat(testDiscente.getDataIngresso()).isEqualTo(UPDATED_DATA_INGRESSO);
        assertThat(testDiscente.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testDiscente.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void putNonExistingDiscente() throws Exception {
        int databaseSizeBeforeUpdate = discenteRepository.findAll().size();
        discente.setId(count.incrementAndGet());

        // Create the Discente
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, discenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(discenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discente in the database
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiscente() throws Exception {
        int databaseSizeBeforeUpdate = discenteRepository.findAll().size();
        discente.setId(count.incrementAndGet());

        // Create the Discente
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(discenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discente in the database
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiscente() throws Exception {
        int databaseSizeBeforeUpdate = discenteRepository.findAll().size();
        discente.setId(count.incrementAndGet());

        // Create the Discente
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discenteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Discente in the database
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiscenteWithPatch() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        int databaseSizeBeforeUpdate = discenteRepository.findAll().size();

        // Update the discente using partial update
        Discente partialUpdatedDiscente = new Discente();
        partialUpdatedDiscente.setId(discente.getId());

        partialUpdatedDiscente
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO)
            .documentoEmissao(UPDATED_DOCUMENTO_EMISSAO)
            .sexo(UPDATED_SEXO)
            .pai(UPDATED_PAI)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .peso(UPDATED_PESO)
            .isAsmatico(UPDATED_IS_ASMATICO)
            .isAlergico(UPDATED_IS_ALERGICO)
            .cuidadosEspeciaisSaude(UPDATED_CUIDADOS_ESPECIAIS_SAUDE)
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .hash(UPDATED_HASH)
            .observacao(UPDATED_OBSERVACAO);

        restDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiscente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiscente))
            )
            .andExpect(status().isOk());

        // Validate the Discente in the database
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeUpdate);
        Discente testDiscente = discenteList.get(discenteList.size() - 1);
        assertThat(testDiscente.getFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testDiscente.getFotografiaContentType()).isEqualTo(UPDATED_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testDiscente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDiscente.getNascimento()).isEqualTo(DEFAULT_NASCIMENTO);
        assertThat(testDiscente.getDocumentoNumero()).isEqualTo(UPDATED_DOCUMENTO_NUMERO);
        assertThat(testDiscente.getDocumentoEmissao()).isEqualTo(UPDATED_DOCUMENTO_EMISSAO);
        assertThat(testDiscente.getDocumentoValidade()).isEqualTo(DEFAULT_DOCUMENTO_VALIDADE);
        assertThat(testDiscente.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testDiscente.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testDiscente.getPai()).isEqualTo(UPDATED_PAI);
        assertThat(testDiscente.getMae()).isEqualTo(DEFAULT_MAE);
        assertThat(testDiscente.getTelefonePrincipal()).isEqualTo(UPDATED_TELEFONE_PRINCIPAL);
        assertThat(testDiscente.getTelefoneParente()).isEqualTo(DEFAULT_TELEFONE_PARENTE);
        assertThat(testDiscente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDiscente.getIsEncarregadoEducacao()).isEqualTo(DEFAULT_IS_ENCARREGADO_EDUCACAO);
        assertThat(testDiscente.getIsTrabalhador()).isEqualTo(DEFAULT_IS_TRABALHADOR);
        assertThat(testDiscente.getIsFilhoAntigoConbatente()).isEqualTo(DEFAULT_IS_FILHO_ANTIGO_CONBATENTE);
        assertThat(testDiscente.getIsAtestadoPobreza()).isEqualTo(DEFAULT_IS_ATESTADO_POBREZA);
        assertThat(testDiscente.getNomeMedico()).isEqualTo(DEFAULT_NOME_MEDICO);
        assertThat(testDiscente.getTelefoneMedico()).isEqualTo(DEFAULT_TELEFONE_MEDICO);
        assertThat(testDiscente.getInstituicaoParticularSaude()).isEqualTo(DEFAULT_INSTITUICAO_PARTICULAR_SAUDE);
        assertThat(testDiscente.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testDiscente.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testDiscente.getIsAsmatico()).isEqualTo(UPDATED_IS_ASMATICO);
        assertThat(testDiscente.getIsAlergico()).isEqualTo(UPDATED_IS_ALERGICO);
        assertThat(testDiscente.getIsPraticaEducacaoFisica()).isEqualTo(DEFAULT_IS_PRATICA_EDUCACAO_FISICA);
        assertThat(testDiscente.getIsAutorizadoMedicacao()).isEqualTo(DEFAULT_IS_AUTORIZADO_MEDICACAO);
        assertThat(testDiscente.getCuidadosEspeciaisSaude()).isEqualTo(UPDATED_CUIDADOS_ESPECIAIS_SAUDE);
        assertThat(testDiscente.getNumeroProcesso()).isEqualTo(UPDATED_NUMERO_PROCESSO);
        assertThat(testDiscente.getDataIngresso()).isEqualTo(DEFAULT_DATA_INGRESSO);
        assertThat(testDiscente.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testDiscente.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void fullUpdateDiscenteWithPatch() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        int databaseSizeBeforeUpdate = discenteRepository.findAll().size();

        // Update the discente using partial update
        Discente partialUpdatedDiscente = new Discente();
        partialUpdatedDiscente.setId(discente.getId());

        partialUpdatedDiscente
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .nascimento(UPDATED_NASCIMENTO)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO)
            .documentoEmissao(UPDATED_DOCUMENTO_EMISSAO)
            .documentoValidade(UPDATED_DOCUMENTO_VALIDADE)
            .nif(UPDATED_NIF)
            .sexo(UPDATED_SEXO)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneParente(UPDATED_TELEFONE_PARENTE)
            .email(UPDATED_EMAIL)
            .isEncarregadoEducacao(UPDATED_IS_ENCARREGADO_EDUCACAO)
            .isTrabalhador(UPDATED_IS_TRABALHADOR)
            .isFilhoAntigoConbatente(UPDATED_IS_FILHO_ANTIGO_CONBATENTE)
            .isAtestadoPobreza(UPDATED_IS_ATESTADO_POBREZA)
            .nomeMedico(UPDATED_NOME_MEDICO)
            .telefoneMedico(UPDATED_TELEFONE_MEDICO)
            .instituicaoParticularSaude(UPDATED_INSTITUICAO_PARTICULAR_SAUDE)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO)
            .isAsmatico(UPDATED_IS_ASMATICO)
            .isAlergico(UPDATED_IS_ALERGICO)
            .isPraticaEducacaoFisica(UPDATED_IS_PRATICA_EDUCACAO_FISICA)
            .isAutorizadoMedicacao(UPDATED_IS_AUTORIZADO_MEDICACAO)
            .cuidadosEspeciaisSaude(UPDATED_CUIDADOS_ESPECIAIS_SAUDE)
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .dataIngresso(UPDATED_DATA_INGRESSO)
            .hash(UPDATED_HASH)
            .observacao(UPDATED_OBSERVACAO);

        restDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiscente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiscente))
            )
            .andExpect(status().isOk());

        // Validate the Discente in the database
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeUpdate);
        Discente testDiscente = discenteList.get(discenteList.size() - 1);
        assertThat(testDiscente.getFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testDiscente.getFotografiaContentType()).isEqualTo(UPDATED_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testDiscente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDiscente.getNascimento()).isEqualTo(UPDATED_NASCIMENTO);
        assertThat(testDiscente.getDocumentoNumero()).isEqualTo(UPDATED_DOCUMENTO_NUMERO);
        assertThat(testDiscente.getDocumentoEmissao()).isEqualTo(UPDATED_DOCUMENTO_EMISSAO);
        assertThat(testDiscente.getDocumentoValidade()).isEqualTo(UPDATED_DOCUMENTO_VALIDADE);
        assertThat(testDiscente.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testDiscente.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testDiscente.getPai()).isEqualTo(UPDATED_PAI);
        assertThat(testDiscente.getMae()).isEqualTo(UPDATED_MAE);
        assertThat(testDiscente.getTelefonePrincipal()).isEqualTo(UPDATED_TELEFONE_PRINCIPAL);
        assertThat(testDiscente.getTelefoneParente()).isEqualTo(UPDATED_TELEFONE_PARENTE);
        assertThat(testDiscente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDiscente.getIsEncarregadoEducacao()).isEqualTo(UPDATED_IS_ENCARREGADO_EDUCACAO);
        assertThat(testDiscente.getIsTrabalhador()).isEqualTo(UPDATED_IS_TRABALHADOR);
        assertThat(testDiscente.getIsFilhoAntigoConbatente()).isEqualTo(UPDATED_IS_FILHO_ANTIGO_CONBATENTE);
        assertThat(testDiscente.getIsAtestadoPobreza()).isEqualTo(UPDATED_IS_ATESTADO_POBREZA);
        assertThat(testDiscente.getNomeMedico()).isEqualTo(UPDATED_NOME_MEDICO);
        assertThat(testDiscente.getTelefoneMedico()).isEqualTo(UPDATED_TELEFONE_MEDICO);
        assertThat(testDiscente.getInstituicaoParticularSaude()).isEqualTo(UPDATED_INSTITUICAO_PARTICULAR_SAUDE);
        assertThat(testDiscente.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testDiscente.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testDiscente.getIsAsmatico()).isEqualTo(UPDATED_IS_ASMATICO);
        assertThat(testDiscente.getIsAlergico()).isEqualTo(UPDATED_IS_ALERGICO);
        assertThat(testDiscente.getIsPraticaEducacaoFisica()).isEqualTo(UPDATED_IS_PRATICA_EDUCACAO_FISICA);
        assertThat(testDiscente.getIsAutorizadoMedicacao()).isEqualTo(UPDATED_IS_AUTORIZADO_MEDICACAO);
        assertThat(testDiscente.getCuidadosEspeciaisSaude()).isEqualTo(UPDATED_CUIDADOS_ESPECIAIS_SAUDE);
        assertThat(testDiscente.getNumeroProcesso()).isEqualTo(UPDATED_NUMERO_PROCESSO);
        assertThat(testDiscente.getDataIngresso()).isEqualTo(UPDATED_DATA_INGRESSO);
        assertThat(testDiscente.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testDiscente.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void patchNonExistingDiscente() throws Exception {
        int databaseSizeBeforeUpdate = discenteRepository.findAll().size();
        discente.setId(count.incrementAndGet());

        // Create the Discente
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, discenteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(discenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discente in the database
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiscente() throws Exception {
        int databaseSizeBeforeUpdate = discenteRepository.findAll().size();
        discente.setId(count.incrementAndGet());

        // Create the Discente
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(discenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discente in the database
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiscente() throws Exception {
        int databaseSizeBeforeUpdate = discenteRepository.findAll().size();
        discente.setId(count.incrementAndGet());

        // Create the Discente
        DiscenteDTO discenteDTO = discenteMapper.toDto(discente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(discenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Discente in the database
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiscente() throws Exception {
        // Initialize the database
        discenteRepository.saveAndFlush(discente);

        int databaseSizeBeforeDelete = discenteRepository.findAll().size();

        // Delete the discente
        restDiscenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, discente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Discente> discenteList = discenteRepository.findAll();
        assertThat(discenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
