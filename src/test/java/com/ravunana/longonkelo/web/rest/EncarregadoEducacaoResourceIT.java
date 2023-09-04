package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.EncarregadoEducacao;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.enumeration.Sexo;
import com.ravunana.longonkelo.repository.EncarregadoEducacaoRepository;
import com.ravunana.longonkelo.service.EncarregadoEducacaoService;
import com.ravunana.longonkelo.service.criteria.EncarregadoEducacaoCriteria;
import com.ravunana.longonkelo.service.dto.EncarregadoEducacaoDTO;
import com.ravunana.longonkelo.service.mapper.EncarregadoEducacaoMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link EncarregadoEducacaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EncarregadoEducacaoResourceIT {

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

    private static final Sexo DEFAULT_SEXO = Sexo.MASCULINO;
    private static final Sexo UPDATED_SEXO = Sexo.FEMENINO;

    private static final String DEFAULT_DOCUMENTO_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_ALTERNATIVO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_ALTERNATIVO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENCIA = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO_TRABALHO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO_TRABALHO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_RENDA_MENSAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_RENDA_MENSAL = new BigDecimal(1);
    private static final BigDecimal SMALLER_RENDA_MENSAL = new BigDecimal(0 - 1);

    private static final String DEFAULT_EMPRESA_TRABALHO = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_TRABALHO = "BBBBBBBBBB";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/encarregado-educacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EncarregadoEducacaoRepository encarregadoEducacaoRepository;

    @Mock
    private EncarregadoEducacaoRepository encarregadoEducacaoRepositoryMock;

    @Autowired
    private EncarregadoEducacaoMapper encarregadoEducacaoMapper;

    @Mock
    private EncarregadoEducacaoService encarregadoEducacaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEncarregadoEducacaoMockMvc;

    private EncarregadoEducacao encarregadoEducacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EncarregadoEducacao createEntity(EntityManager em) {
        EncarregadoEducacao encarregadoEducacao = new EncarregadoEducacao()
            .fotografia(DEFAULT_FOTOGRAFIA)
            .fotografiaContentType(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)
            .nome(DEFAULT_NOME)
            .nascimento(DEFAULT_NASCIMENTO)
            .nif(DEFAULT_NIF)
            .sexo(DEFAULT_SEXO)
            .documentoNumero(DEFAULT_DOCUMENTO_NUMERO)
            .telefonePrincipal(DEFAULT_TELEFONE_PRINCIPAL)
            .telefoneAlternativo(DEFAULT_TELEFONE_ALTERNATIVO)
            .email(DEFAULT_EMAIL)
            .residencia(DEFAULT_RESIDENCIA)
            .enderecoTrabalho(DEFAULT_ENDERECO_TRABALHO)
            .rendaMensal(DEFAULT_RENDA_MENSAL)
            .empresaTrabalho(DEFAULT_EMPRESA_TRABALHO)
            .hash(DEFAULT_HASH);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        encarregadoEducacao.setGrauParentesco(lookupItem);
        // Add required entity
        encarregadoEducacao.setTipoDocumento(lookupItem);
        return encarregadoEducacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EncarregadoEducacao createUpdatedEntity(EntityManager em) {
        EncarregadoEducacao encarregadoEducacao = new EncarregadoEducacao()
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .nascimento(UPDATED_NASCIMENTO)
            .nif(UPDATED_NIF)
            .sexo(UPDATED_SEXO)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneAlternativo(UPDATED_TELEFONE_ALTERNATIVO)
            .email(UPDATED_EMAIL)
            .residencia(UPDATED_RESIDENCIA)
            .enderecoTrabalho(UPDATED_ENDERECO_TRABALHO)
            .rendaMensal(UPDATED_RENDA_MENSAL)
            .empresaTrabalho(UPDATED_EMPRESA_TRABALHO)
            .hash(UPDATED_HASH);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createUpdatedEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        encarregadoEducacao.setGrauParentesco(lookupItem);
        // Add required entity
        encarregadoEducacao.setTipoDocumento(lookupItem);
        return encarregadoEducacao;
    }

    @BeforeEach
    public void initTest() {
        encarregadoEducacao = createEntity(em);
    }

    @Test
    @Transactional
    void createEncarregadoEducacao() throws Exception {
        int databaseSizeBeforeCreate = encarregadoEducacaoRepository.findAll().size();
        // Create the EncarregadoEducacao
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);
        restEncarregadoEducacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeCreate + 1);
        EncarregadoEducacao testEncarregadoEducacao = encarregadoEducacaoList.get(encarregadoEducacaoList.size() - 1);
        assertThat(testEncarregadoEducacao.getFotografia()).isEqualTo(DEFAULT_FOTOGRAFIA);
        assertThat(testEncarregadoEducacao.getFotografiaContentType()).isEqualTo(DEFAULT_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testEncarregadoEducacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEncarregadoEducacao.getNascimento()).isEqualTo(DEFAULT_NASCIMENTO);
        assertThat(testEncarregadoEducacao.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testEncarregadoEducacao.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testEncarregadoEducacao.getDocumentoNumero()).isEqualTo(DEFAULT_DOCUMENTO_NUMERO);
        assertThat(testEncarregadoEducacao.getTelefonePrincipal()).isEqualTo(DEFAULT_TELEFONE_PRINCIPAL);
        assertThat(testEncarregadoEducacao.getTelefoneAlternativo()).isEqualTo(DEFAULT_TELEFONE_ALTERNATIVO);
        assertThat(testEncarregadoEducacao.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEncarregadoEducacao.getResidencia()).isEqualTo(DEFAULT_RESIDENCIA);
        assertThat(testEncarregadoEducacao.getEnderecoTrabalho()).isEqualTo(DEFAULT_ENDERECO_TRABALHO);
        assertThat(testEncarregadoEducacao.getRendaMensal()).isEqualByComparingTo(DEFAULT_RENDA_MENSAL);
        assertThat(testEncarregadoEducacao.getEmpresaTrabalho()).isEqualTo(DEFAULT_EMPRESA_TRABALHO);
        assertThat(testEncarregadoEducacao.getHash()).isEqualTo(DEFAULT_HASH);
    }

    @Test
    @Transactional
    void createEncarregadoEducacaoWithExistingId() throws Exception {
        // Create the EncarregadoEducacao with an existing ID
        encarregadoEducacao.setId(1L);
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        int databaseSizeBeforeCreate = encarregadoEducacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncarregadoEducacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = encarregadoEducacaoRepository.findAll().size();
        // set the field null
        encarregadoEducacao.setNome(null);

        // Create the EncarregadoEducacao, which fails.
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        restEncarregadoEducacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = encarregadoEducacaoRepository.findAll().size();
        // set the field null
        encarregadoEducacao.setNascimento(null);

        // Create the EncarregadoEducacao, which fails.
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        restEncarregadoEducacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = encarregadoEducacaoRepository.findAll().size();
        // set the field null
        encarregadoEducacao.setSexo(null);

        // Create the EncarregadoEducacao, which fails.
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        restEncarregadoEducacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentoNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = encarregadoEducacaoRepository.findAll().size();
        // set the field null
        encarregadoEducacao.setDocumentoNumero(null);

        // Create the EncarregadoEducacao, which fails.
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        restEncarregadoEducacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonePrincipalIsRequired() throws Exception {
        int databaseSizeBeforeTest = encarregadoEducacaoRepository.findAll().size();
        // set the field null
        encarregadoEducacao.setTelefonePrincipal(null);

        // Create the EncarregadoEducacao, which fails.
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        restEncarregadoEducacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaos() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList
        restEncarregadoEducacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encarregadoEducacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotografiaContentType").value(hasItem(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA))))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nascimento").value(hasItem(DEFAULT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].documentoNumero").value(hasItem(DEFAULT_DOCUMENTO_NUMERO)))
            .andExpect(jsonPath("$.[*].telefonePrincipal").value(hasItem(DEFAULT_TELEFONE_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].telefoneAlternativo").value(hasItem(DEFAULT_TELEFONE_ALTERNATIVO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].residencia").value(hasItem(DEFAULT_RESIDENCIA)))
            .andExpect(jsonPath("$.[*].enderecoTrabalho").value(hasItem(DEFAULT_ENDERECO_TRABALHO)))
            .andExpect(jsonPath("$.[*].rendaMensal").value(hasItem(sameNumber(DEFAULT_RENDA_MENSAL))))
            .andExpect(jsonPath("$.[*].empresaTrabalho").value(hasItem(DEFAULT_EMPRESA_TRABALHO)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEncarregadoEducacaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(encarregadoEducacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEncarregadoEducacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(encarregadoEducacaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEncarregadoEducacaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(encarregadoEducacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEncarregadoEducacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(encarregadoEducacaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEncarregadoEducacao() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get the encarregadoEducacao
        restEncarregadoEducacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, encarregadoEducacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(encarregadoEducacao.getId().intValue()))
            .andExpect(jsonPath("$.fotografiaContentType").value(DEFAULT_FOTOGRAFIA_CONTENT_TYPE))
            .andExpect(jsonPath("$.fotografia").value(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA)))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.nascimento").value(DEFAULT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.documentoNumero").value(DEFAULT_DOCUMENTO_NUMERO))
            .andExpect(jsonPath("$.telefonePrincipal").value(DEFAULT_TELEFONE_PRINCIPAL))
            .andExpect(jsonPath("$.telefoneAlternativo").value(DEFAULT_TELEFONE_ALTERNATIVO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.residencia").value(DEFAULT_RESIDENCIA))
            .andExpect(jsonPath("$.enderecoTrabalho").value(DEFAULT_ENDERECO_TRABALHO))
            .andExpect(jsonPath("$.rendaMensal").value(sameNumber(DEFAULT_RENDA_MENSAL)))
            .andExpect(jsonPath("$.empresaTrabalho").value(DEFAULT_EMPRESA_TRABALHO))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH));
    }

    @Test
    @Transactional
    void getEncarregadoEducacaosByIdFiltering() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        Long id = encarregadoEducacao.getId();

        defaultEncarregadoEducacaoShouldBeFound("id.equals=" + id);
        defaultEncarregadoEducacaoShouldNotBeFound("id.notEquals=" + id);

        defaultEncarregadoEducacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEncarregadoEducacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultEncarregadoEducacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEncarregadoEducacaoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nome equals to DEFAULT_NOME
        defaultEncarregadoEducacaoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the encarregadoEducacaoList where nome equals to UPDATED_NOME
        defaultEncarregadoEducacaoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultEncarregadoEducacaoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the encarregadoEducacaoList where nome equals to UPDATED_NOME
        defaultEncarregadoEducacaoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nome is not null
        defaultEncarregadoEducacaoShouldBeFound("nome.specified=true");

        // Get all the encarregadoEducacaoList where nome is null
        defaultEncarregadoEducacaoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNomeContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nome contains DEFAULT_NOME
        defaultEncarregadoEducacaoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the encarregadoEducacaoList where nome contains UPDATED_NOME
        defaultEncarregadoEducacaoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nome does not contain DEFAULT_NOME
        defaultEncarregadoEducacaoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the encarregadoEducacaoList where nome does not contain UPDATED_NOME
        defaultEncarregadoEducacaoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNascimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nascimento equals to DEFAULT_NASCIMENTO
        defaultEncarregadoEducacaoShouldBeFound("nascimento.equals=" + DEFAULT_NASCIMENTO);

        // Get all the encarregadoEducacaoList where nascimento equals to UPDATED_NASCIMENTO
        defaultEncarregadoEducacaoShouldNotBeFound("nascimento.equals=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNascimentoIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nascimento in DEFAULT_NASCIMENTO or UPDATED_NASCIMENTO
        defaultEncarregadoEducacaoShouldBeFound("nascimento.in=" + DEFAULT_NASCIMENTO + "," + UPDATED_NASCIMENTO);

        // Get all the encarregadoEducacaoList where nascimento equals to UPDATED_NASCIMENTO
        defaultEncarregadoEducacaoShouldNotBeFound("nascimento.in=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNascimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nascimento is not null
        defaultEncarregadoEducacaoShouldBeFound("nascimento.specified=true");

        // Get all the encarregadoEducacaoList where nascimento is null
        defaultEncarregadoEducacaoShouldNotBeFound("nascimento.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNascimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nascimento is greater than or equal to DEFAULT_NASCIMENTO
        defaultEncarregadoEducacaoShouldBeFound("nascimento.greaterThanOrEqual=" + DEFAULT_NASCIMENTO);

        // Get all the encarregadoEducacaoList where nascimento is greater than or equal to UPDATED_NASCIMENTO
        defaultEncarregadoEducacaoShouldNotBeFound("nascimento.greaterThanOrEqual=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNascimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nascimento is less than or equal to DEFAULT_NASCIMENTO
        defaultEncarregadoEducacaoShouldBeFound("nascimento.lessThanOrEqual=" + DEFAULT_NASCIMENTO);

        // Get all the encarregadoEducacaoList where nascimento is less than or equal to SMALLER_NASCIMENTO
        defaultEncarregadoEducacaoShouldNotBeFound("nascimento.lessThanOrEqual=" + SMALLER_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNascimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nascimento is less than DEFAULT_NASCIMENTO
        defaultEncarregadoEducacaoShouldNotBeFound("nascimento.lessThan=" + DEFAULT_NASCIMENTO);

        // Get all the encarregadoEducacaoList where nascimento is less than UPDATED_NASCIMENTO
        defaultEncarregadoEducacaoShouldBeFound("nascimento.lessThan=" + UPDATED_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNascimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nascimento is greater than DEFAULT_NASCIMENTO
        defaultEncarregadoEducacaoShouldNotBeFound("nascimento.greaterThan=" + DEFAULT_NASCIMENTO);

        // Get all the encarregadoEducacaoList where nascimento is greater than SMALLER_NASCIMENTO
        defaultEncarregadoEducacaoShouldBeFound("nascimento.greaterThan=" + SMALLER_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNifIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nif equals to DEFAULT_NIF
        defaultEncarregadoEducacaoShouldBeFound("nif.equals=" + DEFAULT_NIF);

        // Get all the encarregadoEducacaoList where nif equals to UPDATED_NIF
        defaultEncarregadoEducacaoShouldNotBeFound("nif.equals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNifIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nif in DEFAULT_NIF or UPDATED_NIF
        defaultEncarregadoEducacaoShouldBeFound("nif.in=" + DEFAULT_NIF + "," + UPDATED_NIF);

        // Get all the encarregadoEducacaoList where nif equals to UPDATED_NIF
        defaultEncarregadoEducacaoShouldNotBeFound("nif.in=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNifIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nif is not null
        defaultEncarregadoEducacaoShouldBeFound("nif.specified=true");

        // Get all the encarregadoEducacaoList where nif is null
        defaultEncarregadoEducacaoShouldNotBeFound("nif.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNifContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nif contains DEFAULT_NIF
        defaultEncarregadoEducacaoShouldBeFound("nif.contains=" + DEFAULT_NIF);

        // Get all the encarregadoEducacaoList where nif contains UPDATED_NIF
        defaultEncarregadoEducacaoShouldNotBeFound("nif.contains=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByNifNotContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where nif does not contain DEFAULT_NIF
        defaultEncarregadoEducacaoShouldNotBeFound("nif.doesNotContain=" + DEFAULT_NIF);

        // Get all the encarregadoEducacaoList where nif does not contain UPDATED_NIF
        defaultEncarregadoEducacaoShouldBeFound("nif.doesNotContain=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosBySexoIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where sexo equals to DEFAULT_SEXO
        defaultEncarregadoEducacaoShouldBeFound("sexo.equals=" + DEFAULT_SEXO);

        // Get all the encarregadoEducacaoList where sexo equals to UPDATED_SEXO
        defaultEncarregadoEducacaoShouldNotBeFound("sexo.equals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosBySexoIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where sexo in DEFAULT_SEXO or UPDATED_SEXO
        defaultEncarregadoEducacaoShouldBeFound("sexo.in=" + DEFAULT_SEXO + "," + UPDATED_SEXO);

        // Get all the encarregadoEducacaoList where sexo equals to UPDATED_SEXO
        defaultEncarregadoEducacaoShouldNotBeFound("sexo.in=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosBySexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where sexo is not null
        defaultEncarregadoEducacaoShouldBeFound("sexo.specified=true");

        // Get all the encarregadoEducacaoList where sexo is null
        defaultEncarregadoEducacaoShouldNotBeFound("sexo.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByDocumentoNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where documentoNumero equals to DEFAULT_DOCUMENTO_NUMERO
        defaultEncarregadoEducacaoShouldBeFound("documentoNumero.equals=" + DEFAULT_DOCUMENTO_NUMERO);

        // Get all the encarregadoEducacaoList where documentoNumero equals to UPDATED_DOCUMENTO_NUMERO
        defaultEncarregadoEducacaoShouldNotBeFound("documentoNumero.equals=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByDocumentoNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where documentoNumero in DEFAULT_DOCUMENTO_NUMERO or UPDATED_DOCUMENTO_NUMERO
        defaultEncarregadoEducacaoShouldBeFound("documentoNumero.in=" + DEFAULT_DOCUMENTO_NUMERO + "," + UPDATED_DOCUMENTO_NUMERO);

        // Get all the encarregadoEducacaoList where documentoNumero equals to UPDATED_DOCUMENTO_NUMERO
        defaultEncarregadoEducacaoShouldNotBeFound("documentoNumero.in=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByDocumentoNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where documentoNumero is not null
        defaultEncarregadoEducacaoShouldBeFound("documentoNumero.specified=true");

        // Get all the encarregadoEducacaoList where documentoNumero is null
        defaultEncarregadoEducacaoShouldNotBeFound("documentoNumero.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByDocumentoNumeroContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where documentoNumero contains DEFAULT_DOCUMENTO_NUMERO
        defaultEncarregadoEducacaoShouldBeFound("documentoNumero.contains=" + DEFAULT_DOCUMENTO_NUMERO);

        // Get all the encarregadoEducacaoList where documentoNumero contains UPDATED_DOCUMENTO_NUMERO
        defaultEncarregadoEducacaoShouldNotBeFound("documentoNumero.contains=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByDocumentoNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where documentoNumero does not contain DEFAULT_DOCUMENTO_NUMERO
        defaultEncarregadoEducacaoShouldNotBeFound("documentoNumero.doesNotContain=" + DEFAULT_DOCUMENTO_NUMERO);

        // Get all the encarregadoEducacaoList where documentoNumero does not contain UPDATED_DOCUMENTO_NUMERO
        defaultEncarregadoEducacaoShouldBeFound("documentoNumero.doesNotContain=" + UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByTelefonePrincipalIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where telefonePrincipal equals to DEFAULT_TELEFONE_PRINCIPAL
        defaultEncarregadoEducacaoShouldBeFound("telefonePrincipal.equals=" + DEFAULT_TELEFONE_PRINCIPAL);

        // Get all the encarregadoEducacaoList where telefonePrincipal equals to UPDATED_TELEFONE_PRINCIPAL
        defaultEncarregadoEducacaoShouldNotBeFound("telefonePrincipal.equals=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByTelefonePrincipalIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where telefonePrincipal in DEFAULT_TELEFONE_PRINCIPAL or UPDATED_TELEFONE_PRINCIPAL
        defaultEncarregadoEducacaoShouldBeFound("telefonePrincipal.in=" + DEFAULT_TELEFONE_PRINCIPAL + "," + UPDATED_TELEFONE_PRINCIPAL);

        // Get all the encarregadoEducacaoList where telefonePrincipal equals to UPDATED_TELEFONE_PRINCIPAL
        defaultEncarregadoEducacaoShouldNotBeFound("telefonePrincipal.in=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByTelefonePrincipalIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where telefonePrincipal is not null
        defaultEncarregadoEducacaoShouldBeFound("telefonePrincipal.specified=true");

        // Get all the encarregadoEducacaoList where telefonePrincipal is null
        defaultEncarregadoEducacaoShouldNotBeFound("telefonePrincipal.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByTelefonePrincipalContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where telefonePrincipal contains DEFAULT_TELEFONE_PRINCIPAL
        defaultEncarregadoEducacaoShouldBeFound("telefonePrincipal.contains=" + DEFAULT_TELEFONE_PRINCIPAL);

        // Get all the encarregadoEducacaoList where telefonePrincipal contains UPDATED_TELEFONE_PRINCIPAL
        defaultEncarregadoEducacaoShouldNotBeFound("telefonePrincipal.contains=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByTelefonePrincipalNotContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where telefonePrincipal does not contain DEFAULT_TELEFONE_PRINCIPAL
        defaultEncarregadoEducacaoShouldNotBeFound("telefonePrincipal.doesNotContain=" + DEFAULT_TELEFONE_PRINCIPAL);

        // Get all the encarregadoEducacaoList where telefonePrincipal does not contain UPDATED_TELEFONE_PRINCIPAL
        defaultEncarregadoEducacaoShouldBeFound("telefonePrincipal.doesNotContain=" + UPDATED_TELEFONE_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByTelefoneAlternativoIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where telefoneAlternativo equals to DEFAULT_TELEFONE_ALTERNATIVO
        defaultEncarregadoEducacaoShouldBeFound("telefoneAlternativo.equals=" + DEFAULT_TELEFONE_ALTERNATIVO);

        // Get all the encarregadoEducacaoList where telefoneAlternativo equals to UPDATED_TELEFONE_ALTERNATIVO
        defaultEncarregadoEducacaoShouldNotBeFound("telefoneAlternativo.equals=" + UPDATED_TELEFONE_ALTERNATIVO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByTelefoneAlternativoIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where telefoneAlternativo in DEFAULT_TELEFONE_ALTERNATIVO or UPDATED_TELEFONE_ALTERNATIVO
        defaultEncarregadoEducacaoShouldBeFound(
            "telefoneAlternativo.in=" + DEFAULT_TELEFONE_ALTERNATIVO + "," + UPDATED_TELEFONE_ALTERNATIVO
        );

        // Get all the encarregadoEducacaoList where telefoneAlternativo equals to UPDATED_TELEFONE_ALTERNATIVO
        defaultEncarregadoEducacaoShouldNotBeFound("telefoneAlternativo.in=" + UPDATED_TELEFONE_ALTERNATIVO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByTelefoneAlternativoIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where telefoneAlternativo is not null
        defaultEncarregadoEducacaoShouldBeFound("telefoneAlternativo.specified=true");

        // Get all the encarregadoEducacaoList where telefoneAlternativo is null
        defaultEncarregadoEducacaoShouldNotBeFound("telefoneAlternativo.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByTelefoneAlternativoContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where telefoneAlternativo contains DEFAULT_TELEFONE_ALTERNATIVO
        defaultEncarregadoEducacaoShouldBeFound("telefoneAlternativo.contains=" + DEFAULT_TELEFONE_ALTERNATIVO);

        // Get all the encarregadoEducacaoList where telefoneAlternativo contains UPDATED_TELEFONE_ALTERNATIVO
        defaultEncarregadoEducacaoShouldNotBeFound("telefoneAlternativo.contains=" + UPDATED_TELEFONE_ALTERNATIVO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByTelefoneAlternativoNotContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where telefoneAlternativo does not contain DEFAULT_TELEFONE_ALTERNATIVO
        defaultEncarregadoEducacaoShouldNotBeFound("telefoneAlternativo.doesNotContain=" + DEFAULT_TELEFONE_ALTERNATIVO);

        // Get all the encarregadoEducacaoList where telefoneAlternativo does not contain UPDATED_TELEFONE_ALTERNATIVO
        defaultEncarregadoEducacaoShouldBeFound("telefoneAlternativo.doesNotContain=" + UPDATED_TELEFONE_ALTERNATIVO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where email equals to DEFAULT_EMAIL
        defaultEncarregadoEducacaoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the encarregadoEducacaoList where email equals to UPDATED_EMAIL
        defaultEncarregadoEducacaoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEncarregadoEducacaoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the encarregadoEducacaoList where email equals to UPDATED_EMAIL
        defaultEncarregadoEducacaoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where email is not null
        defaultEncarregadoEducacaoShouldBeFound("email.specified=true");

        // Get all the encarregadoEducacaoList where email is null
        defaultEncarregadoEducacaoShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEmailContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where email contains DEFAULT_EMAIL
        defaultEncarregadoEducacaoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the encarregadoEducacaoList where email contains UPDATED_EMAIL
        defaultEncarregadoEducacaoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where email does not contain DEFAULT_EMAIL
        defaultEncarregadoEducacaoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the encarregadoEducacaoList where email does not contain UPDATED_EMAIL
        defaultEncarregadoEducacaoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByResidenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where residencia equals to DEFAULT_RESIDENCIA
        defaultEncarregadoEducacaoShouldBeFound("residencia.equals=" + DEFAULT_RESIDENCIA);

        // Get all the encarregadoEducacaoList where residencia equals to UPDATED_RESIDENCIA
        defaultEncarregadoEducacaoShouldNotBeFound("residencia.equals=" + UPDATED_RESIDENCIA);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByResidenciaIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where residencia in DEFAULT_RESIDENCIA or UPDATED_RESIDENCIA
        defaultEncarregadoEducacaoShouldBeFound("residencia.in=" + DEFAULT_RESIDENCIA + "," + UPDATED_RESIDENCIA);

        // Get all the encarregadoEducacaoList where residencia equals to UPDATED_RESIDENCIA
        defaultEncarregadoEducacaoShouldNotBeFound("residencia.in=" + UPDATED_RESIDENCIA);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByResidenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where residencia is not null
        defaultEncarregadoEducacaoShouldBeFound("residencia.specified=true");

        // Get all the encarregadoEducacaoList where residencia is null
        defaultEncarregadoEducacaoShouldNotBeFound("residencia.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByResidenciaContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where residencia contains DEFAULT_RESIDENCIA
        defaultEncarregadoEducacaoShouldBeFound("residencia.contains=" + DEFAULT_RESIDENCIA);

        // Get all the encarregadoEducacaoList where residencia contains UPDATED_RESIDENCIA
        defaultEncarregadoEducacaoShouldNotBeFound("residencia.contains=" + UPDATED_RESIDENCIA);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByResidenciaNotContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where residencia does not contain DEFAULT_RESIDENCIA
        defaultEncarregadoEducacaoShouldNotBeFound("residencia.doesNotContain=" + DEFAULT_RESIDENCIA);

        // Get all the encarregadoEducacaoList where residencia does not contain UPDATED_RESIDENCIA
        defaultEncarregadoEducacaoShouldBeFound("residencia.doesNotContain=" + UPDATED_RESIDENCIA);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEnderecoTrabalhoIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where enderecoTrabalho equals to DEFAULT_ENDERECO_TRABALHO
        defaultEncarregadoEducacaoShouldBeFound("enderecoTrabalho.equals=" + DEFAULT_ENDERECO_TRABALHO);

        // Get all the encarregadoEducacaoList where enderecoTrabalho equals to UPDATED_ENDERECO_TRABALHO
        defaultEncarregadoEducacaoShouldNotBeFound("enderecoTrabalho.equals=" + UPDATED_ENDERECO_TRABALHO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEnderecoTrabalhoIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where enderecoTrabalho in DEFAULT_ENDERECO_TRABALHO or UPDATED_ENDERECO_TRABALHO
        defaultEncarregadoEducacaoShouldBeFound("enderecoTrabalho.in=" + DEFAULT_ENDERECO_TRABALHO + "," + UPDATED_ENDERECO_TRABALHO);

        // Get all the encarregadoEducacaoList where enderecoTrabalho equals to UPDATED_ENDERECO_TRABALHO
        defaultEncarregadoEducacaoShouldNotBeFound("enderecoTrabalho.in=" + UPDATED_ENDERECO_TRABALHO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEnderecoTrabalhoIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where enderecoTrabalho is not null
        defaultEncarregadoEducacaoShouldBeFound("enderecoTrabalho.specified=true");

        // Get all the encarregadoEducacaoList where enderecoTrabalho is null
        defaultEncarregadoEducacaoShouldNotBeFound("enderecoTrabalho.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEnderecoTrabalhoContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where enderecoTrabalho contains DEFAULT_ENDERECO_TRABALHO
        defaultEncarregadoEducacaoShouldBeFound("enderecoTrabalho.contains=" + DEFAULT_ENDERECO_TRABALHO);

        // Get all the encarregadoEducacaoList where enderecoTrabalho contains UPDATED_ENDERECO_TRABALHO
        defaultEncarregadoEducacaoShouldNotBeFound("enderecoTrabalho.contains=" + UPDATED_ENDERECO_TRABALHO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEnderecoTrabalhoNotContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where enderecoTrabalho does not contain DEFAULT_ENDERECO_TRABALHO
        defaultEncarregadoEducacaoShouldNotBeFound("enderecoTrabalho.doesNotContain=" + DEFAULT_ENDERECO_TRABALHO);

        // Get all the encarregadoEducacaoList where enderecoTrabalho does not contain UPDATED_ENDERECO_TRABALHO
        defaultEncarregadoEducacaoShouldBeFound("enderecoTrabalho.doesNotContain=" + UPDATED_ENDERECO_TRABALHO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByRendaMensalIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where rendaMensal equals to DEFAULT_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldBeFound("rendaMensal.equals=" + DEFAULT_RENDA_MENSAL);

        // Get all the encarregadoEducacaoList where rendaMensal equals to UPDATED_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldNotBeFound("rendaMensal.equals=" + UPDATED_RENDA_MENSAL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByRendaMensalIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where rendaMensal in DEFAULT_RENDA_MENSAL or UPDATED_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldBeFound("rendaMensal.in=" + DEFAULT_RENDA_MENSAL + "," + UPDATED_RENDA_MENSAL);

        // Get all the encarregadoEducacaoList where rendaMensal equals to UPDATED_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldNotBeFound("rendaMensal.in=" + UPDATED_RENDA_MENSAL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByRendaMensalIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where rendaMensal is not null
        defaultEncarregadoEducacaoShouldBeFound("rendaMensal.specified=true");

        // Get all the encarregadoEducacaoList where rendaMensal is null
        defaultEncarregadoEducacaoShouldNotBeFound("rendaMensal.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByRendaMensalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where rendaMensal is greater than or equal to DEFAULT_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldBeFound("rendaMensal.greaterThanOrEqual=" + DEFAULT_RENDA_MENSAL);

        // Get all the encarregadoEducacaoList where rendaMensal is greater than or equal to UPDATED_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldNotBeFound("rendaMensal.greaterThanOrEqual=" + UPDATED_RENDA_MENSAL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByRendaMensalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where rendaMensal is less than or equal to DEFAULT_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldBeFound("rendaMensal.lessThanOrEqual=" + DEFAULT_RENDA_MENSAL);

        // Get all the encarregadoEducacaoList where rendaMensal is less than or equal to SMALLER_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldNotBeFound("rendaMensal.lessThanOrEqual=" + SMALLER_RENDA_MENSAL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByRendaMensalIsLessThanSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where rendaMensal is less than DEFAULT_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldNotBeFound("rendaMensal.lessThan=" + DEFAULT_RENDA_MENSAL);

        // Get all the encarregadoEducacaoList where rendaMensal is less than UPDATED_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldBeFound("rendaMensal.lessThan=" + UPDATED_RENDA_MENSAL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByRendaMensalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where rendaMensal is greater than DEFAULT_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldNotBeFound("rendaMensal.greaterThan=" + DEFAULT_RENDA_MENSAL);

        // Get all the encarregadoEducacaoList where rendaMensal is greater than SMALLER_RENDA_MENSAL
        defaultEncarregadoEducacaoShouldBeFound("rendaMensal.greaterThan=" + SMALLER_RENDA_MENSAL);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEmpresaTrabalhoIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where empresaTrabalho equals to DEFAULT_EMPRESA_TRABALHO
        defaultEncarregadoEducacaoShouldBeFound("empresaTrabalho.equals=" + DEFAULT_EMPRESA_TRABALHO);

        // Get all the encarregadoEducacaoList where empresaTrabalho equals to UPDATED_EMPRESA_TRABALHO
        defaultEncarregadoEducacaoShouldNotBeFound("empresaTrabalho.equals=" + UPDATED_EMPRESA_TRABALHO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEmpresaTrabalhoIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where empresaTrabalho in DEFAULT_EMPRESA_TRABALHO or UPDATED_EMPRESA_TRABALHO
        defaultEncarregadoEducacaoShouldBeFound("empresaTrabalho.in=" + DEFAULT_EMPRESA_TRABALHO + "," + UPDATED_EMPRESA_TRABALHO);

        // Get all the encarregadoEducacaoList where empresaTrabalho equals to UPDATED_EMPRESA_TRABALHO
        defaultEncarregadoEducacaoShouldNotBeFound("empresaTrabalho.in=" + UPDATED_EMPRESA_TRABALHO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEmpresaTrabalhoIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where empresaTrabalho is not null
        defaultEncarregadoEducacaoShouldBeFound("empresaTrabalho.specified=true");

        // Get all the encarregadoEducacaoList where empresaTrabalho is null
        defaultEncarregadoEducacaoShouldNotBeFound("empresaTrabalho.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEmpresaTrabalhoContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where empresaTrabalho contains DEFAULT_EMPRESA_TRABALHO
        defaultEncarregadoEducacaoShouldBeFound("empresaTrabalho.contains=" + DEFAULT_EMPRESA_TRABALHO);

        // Get all the encarregadoEducacaoList where empresaTrabalho contains UPDATED_EMPRESA_TRABALHO
        defaultEncarregadoEducacaoShouldNotBeFound("empresaTrabalho.contains=" + UPDATED_EMPRESA_TRABALHO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByEmpresaTrabalhoNotContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where empresaTrabalho does not contain DEFAULT_EMPRESA_TRABALHO
        defaultEncarregadoEducacaoShouldNotBeFound("empresaTrabalho.doesNotContain=" + DEFAULT_EMPRESA_TRABALHO);

        // Get all the encarregadoEducacaoList where empresaTrabalho does not contain UPDATED_EMPRESA_TRABALHO
        defaultEncarregadoEducacaoShouldBeFound("empresaTrabalho.doesNotContain=" + UPDATED_EMPRESA_TRABALHO);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where hash equals to DEFAULT_HASH
        defaultEncarregadoEducacaoShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the encarregadoEducacaoList where hash equals to UPDATED_HASH
        defaultEncarregadoEducacaoShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByHashIsInShouldWork() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultEncarregadoEducacaoShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the encarregadoEducacaoList where hash equals to UPDATED_HASH
        defaultEncarregadoEducacaoShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where hash is not null
        defaultEncarregadoEducacaoShouldBeFound("hash.specified=true");

        // Get all the encarregadoEducacaoList where hash is null
        defaultEncarregadoEducacaoShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByHashContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where hash contains DEFAULT_HASH
        defaultEncarregadoEducacaoShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the encarregadoEducacaoList where hash contains UPDATED_HASH
        defaultEncarregadoEducacaoShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByHashNotContainsSomething() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList where hash does not contain DEFAULT_HASH
        defaultEncarregadoEducacaoShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the encarregadoEducacaoList where hash does not contain UPDATED_HASH
        defaultEncarregadoEducacaoShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByDiscentesIsEqualToSomething() throws Exception {
        Discente discentes;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);
            discentes = DiscenteResourceIT.createEntity(em);
        } else {
            discentes = TestUtil.findAll(em, Discente.class).get(0);
        }
        em.persist(discentes);
        em.flush();
        encarregadoEducacao.addDiscentes(discentes);
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);
        Long discentesId = discentes.getId();

        // Get all the encarregadoEducacaoList where discentes equals to discentesId
        defaultEncarregadoEducacaoShouldBeFound("discentesId.equals=" + discentesId);

        // Get all the encarregadoEducacaoList where discentes equals to (discentesId + 1)
        defaultEncarregadoEducacaoShouldNotBeFound("discentesId.equals=" + (discentesId + 1));
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByMatriculaIsEqualToSomething() throws Exception {
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);
            matricula = MatriculaResourceIT.createEntity(em);
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(matricula);
        em.flush();
        encarregadoEducacao.addMatricula(matricula);
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);
        Long matriculaId = matricula.getId();

        // Get all the encarregadoEducacaoList where matricula equals to matriculaId
        defaultEncarregadoEducacaoShouldBeFound("matriculaId.equals=" + matriculaId);

        // Get all the encarregadoEducacaoList where matricula equals to (matriculaId + 1)
        defaultEncarregadoEducacaoShouldNotBeFound("matriculaId.equals=" + (matriculaId + 1));
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByGrauParentescoIsEqualToSomething() throws Exception {
        LookupItem grauParentesco;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);
            grauParentesco = LookupItemResourceIT.createEntity(em);
        } else {
            grauParentesco = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(grauParentesco);
        em.flush();
        encarregadoEducacao.setGrauParentesco(grauParentesco);
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);
        Long grauParentescoId = grauParentesco.getId();

        // Get all the encarregadoEducacaoList where grauParentesco equals to grauParentescoId
        defaultEncarregadoEducacaoShouldBeFound("grauParentescoId.equals=" + grauParentescoId);

        // Get all the encarregadoEducacaoList where grauParentesco equals to (grauParentescoId + 1)
        defaultEncarregadoEducacaoShouldNotBeFound("grauParentescoId.equals=" + (grauParentescoId + 1));
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByTipoDocumentoIsEqualToSomething() throws Exception {
        LookupItem tipoDocumento;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);
            tipoDocumento = LookupItemResourceIT.createEntity(em);
        } else {
            tipoDocumento = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(tipoDocumento);
        em.flush();
        encarregadoEducacao.setTipoDocumento(tipoDocumento);
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);
        Long tipoDocumentoId = tipoDocumento.getId();

        // Get all the encarregadoEducacaoList where tipoDocumento equals to tipoDocumentoId
        defaultEncarregadoEducacaoShouldBeFound("tipoDocumentoId.equals=" + tipoDocumentoId);

        // Get all the encarregadoEducacaoList where tipoDocumento equals to (tipoDocumentoId + 1)
        defaultEncarregadoEducacaoShouldNotBeFound("tipoDocumentoId.equals=" + (tipoDocumentoId + 1));
    }

    @Test
    @Transactional
    void getAllEncarregadoEducacaosByProfissaoIsEqualToSomething() throws Exception {
        LookupItem profissao;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);
            profissao = LookupItemResourceIT.createEntity(em);
        } else {
            profissao = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(profissao);
        em.flush();
        encarregadoEducacao.setProfissao(profissao);
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);
        Long profissaoId = profissao.getId();

        // Get all the encarregadoEducacaoList where profissao equals to profissaoId
        defaultEncarregadoEducacaoShouldBeFound("profissaoId.equals=" + profissaoId);

        // Get all the encarregadoEducacaoList where profissao equals to (profissaoId + 1)
        defaultEncarregadoEducacaoShouldNotBeFound("profissaoId.equals=" + (profissaoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEncarregadoEducacaoShouldBeFound(String filter) throws Exception {
        restEncarregadoEducacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encarregadoEducacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotografiaContentType").value(hasItem(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA))))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nascimento").value(hasItem(DEFAULT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].documentoNumero").value(hasItem(DEFAULT_DOCUMENTO_NUMERO)))
            .andExpect(jsonPath("$.[*].telefonePrincipal").value(hasItem(DEFAULT_TELEFONE_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].telefoneAlternativo").value(hasItem(DEFAULT_TELEFONE_ALTERNATIVO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].residencia").value(hasItem(DEFAULT_RESIDENCIA)))
            .andExpect(jsonPath("$.[*].enderecoTrabalho").value(hasItem(DEFAULT_ENDERECO_TRABALHO)))
            .andExpect(jsonPath("$.[*].rendaMensal").value(hasItem(sameNumber(DEFAULT_RENDA_MENSAL))))
            .andExpect(jsonPath("$.[*].empresaTrabalho").value(hasItem(DEFAULT_EMPRESA_TRABALHO)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)));

        // Check, that the count call also returns 1
        restEncarregadoEducacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEncarregadoEducacaoShouldNotBeFound(String filter) throws Exception {
        restEncarregadoEducacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEncarregadoEducacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEncarregadoEducacao() throws Exception {
        // Get the encarregadoEducacao
        restEncarregadoEducacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEncarregadoEducacao() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        int databaseSizeBeforeUpdate = encarregadoEducacaoRepository.findAll().size();

        // Update the encarregadoEducacao
        EncarregadoEducacao updatedEncarregadoEducacao = encarregadoEducacaoRepository.findById(encarregadoEducacao.getId()).get();
        // Disconnect from session so that the updates on updatedEncarregadoEducacao are not directly saved in db
        em.detach(updatedEncarregadoEducacao);
        updatedEncarregadoEducacao
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .nascimento(UPDATED_NASCIMENTO)
            .nif(UPDATED_NIF)
            .sexo(UPDATED_SEXO)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneAlternativo(UPDATED_TELEFONE_ALTERNATIVO)
            .email(UPDATED_EMAIL)
            .residencia(UPDATED_RESIDENCIA)
            .enderecoTrabalho(UPDATED_ENDERECO_TRABALHO)
            .rendaMensal(UPDATED_RENDA_MENSAL)
            .empresaTrabalho(UPDATED_EMPRESA_TRABALHO)
            .hash(UPDATED_HASH);
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(updatedEncarregadoEducacao);

        restEncarregadoEducacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, encarregadoEducacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeUpdate);
        EncarregadoEducacao testEncarregadoEducacao = encarregadoEducacaoList.get(encarregadoEducacaoList.size() - 1);
        assertThat(testEncarregadoEducacao.getFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testEncarregadoEducacao.getFotografiaContentType()).isEqualTo(UPDATED_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testEncarregadoEducacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEncarregadoEducacao.getNascimento()).isEqualTo(UPDATED_NASCIMENTO);
        assertThat(testEncarregadoEducacao.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testEncarregadoEducacao.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testEncarregadoEducacao.getDocumentoNumero()).isEqualTo(UPDATED_DOCUMENTO_NUMERO);
        assertThat(testEncarregadoEducacao.getTelefonePrincipal()).isEqualTo(UPDATED_TELEFONE_PRINCIPAL);
        assertThat(testEncarregadoEducacao.getTelefoneAlternativo()).isEqualTo(UPDATED_TELEFONE_ALTERNATIVO);
        assertThat(testEncarregadoEducacao.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEncarregadoEducacao.getResidencia()).isEqualTo(UPDATED_RESIDENCIA);
        assertThat(testEncarregadoEducacao.getEnderecoTrabalho()).isEqualTo(UPDATED_ENDERECO_TRABALHO);
        assertThat(testEncarregadoEducacao.getRendaMensal()).isEqualByComparingTo(UPDATED_RENDA_MENSAL);
        assertThat(testEncarregadoEducacao.getEmpresaTrabalho()).isEqualTo(UPDATED_EMPRESA_TRABALHO);
        assertThat(testEncarregadoEducacao.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    void putNonExistingEncarregadoEducacao() throws Exception {
        int databaseSizeBeforeUpdate = encarregadoEducacaoRepository.findAll().size();
        encarregadoEducacao.setId(count.incrementAndGet());

        // Create the EncarregadoEducacao
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncarregadoEducacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, encarregadoEducacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEncarregadoEducacao() throws Exception {
        int databaseSizeBeforeUpdate = encarregadoEducacaoRepository.findAll().size();
        encarregadoEducacao.setId(count.incrementAndGet());

        // Create the EncarregadoEducacao
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncarregadoEducacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEncarregadoEducacao() throws Exception {
        int databaseSizeBeforeUpdate = encarregadoEducacaoRepository.findAll().size();
        encarregadoEducacao.setId(count.incrementAndGet());

        // Create the EncarregadoEducacao
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncarregadoEducacaoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEncarregadoEducacaoWithPatch() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        int databaseSizeBeforeUpdate = encarregadoEducacaoRepository.findAll().size();

        // Update the encarregadoEducacao using partial update
        EncarregadoEducacao partialUpdatedEncarregadoEducacao = new EncarregadoEducacao();
        partialUpdatedEncarregadoEducacao.setId(encarregadoEducacao.getId());

        partialUpdatedEncarregadoEducacao
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .nif(UPDATED_NIF)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneAlternativo(UPDATED_TELEFONE_ALTERNATIVO)
            .residencia(UPDATED_RESIDENCIA)
            .enderecoTrabalho(UPDATED_ENDERECO_TRABALHO)
            .empresaTrabalho(UPDATED_EMPRESA_TRABALHO)
            .hash(UPDATED_HASH);

        restEncarregadoEducacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEncarregadoEducacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEncarregadoEducacao))
            )
            .andExpect(status().isOk());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeUpdate);
        EncarregadoEducacao testEncarregadoEducacao = encarregadoEducacaoList.get(encarregadoEducacaoList.size() - 1);
        assertThat(testEncarregadoEducacao.getFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testEncarregadoEducacao.getFotografiaContentType()).isEqualTo(UPDATED_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testEncarregadoEducacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEncarregadoEducacao.getNascimento()).isEqualTo(DEFAULT_NASCIMENTO);
        assertThat(testEncarregadoEducacao.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testEncarregadoEducacao.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testEncarregadoEducacao.getDocumentoNumero()).isEqualTo(DEFAULT_DOCUMENTO_NUMERO);
        assertThat(testEncarregadoEducacao.getTelefonePrincipal()).isEqualTo(UPDATED_TELEFONE_PRINCIPAL);
        assertThat(testEncarregadoEducacao.getTelefoneAlternativo()).isEqualTo(UPDATED_TELEFONE_ALTERNATIVO);
        assertThat(testEncarregadoEducacao.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEncarregadoEducacao.getResidencia()).isEqualTo(UPDATED_RESIDENCIA);
        assertThat(testEncarregadoEducacao.getEnderecoTrabalho()).isEqualTo(UPDATED_ENDERECO_TRABALHO);
        assertThat(testEncarregadoEducacao.getRendaMensal()).isEqualByComparingTo(DEFAULT_RENDA_MENSAL);
        assertThat(testEncarregadoEducacao.getEmpresaTrabalho()).isEqualTo(UPDATED_EMPRESA_TRABALHO);
        assertThat(testEncarregadoEducacao.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    void fullUpdateEncarregadoEducacaoWithPatch() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        int databaseSizeBeforeUpdate = encarregadoEducacaoRepository.findAll().size();

        // Update the encarregadoEducacao using partial update
        EncarregadoEducacao partialUpdatedEncarregadoEducacao = new EncarregadoEducacao();
        partialUpdatedEncarregadoEducacao.setId(encarregadoEducacao.getId());

        partialUpdatedEncarregadoEducacao
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .nascimento(UPDATED_NASCIMENTO)
            .nif(UPDATED_NIF)
            .sexo(UPDATED_SEXO)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneAlternativo(UPDATED_TELEFONE_ALTERNATIVO)
            .email(UPDATED_EMAIL)
            .residencia(UPDATED_RESIDENCIA)
            .enderecoTrabalho(UPDATED_ENDERECO_TRABALHO)
            .rendaMensal(UPDATED_RENDA_MENSAL)
            .empresaTrabalho(UPDATED_EMPRESA_TRABALHO)
            .hash(UPDATED_HASH);

        restEncarregadoEducacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEncarregadoEducacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEncarregadoEducacao))
            )
            .andExpect(status().isOk());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeUpdate);
        EncarregadoEducacao testEncarregadoEducacao = encarregadoEducacaoList.get(encarregadoEducacaoList.size() - 1);
        assertThat(testEncarregadoEducacao.getFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testEncarregadoEducacao.getFotografiaContentType()).isEqualTo(UPDATED_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testEncarregadoEducacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEncarregadoEducacao.getNascimento()).isEqualTo(UPDATED_NASCIMENTO);
        assertThat(testEncarregadoEducacao.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testEncarregadoEducacao.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testEncarregadoEducacao.getDocumentoNumero()).isEqualTo(UPDATED_DOCUMENTO_NUMERO);
        assertThat(testEncarregadoEducacao.getTelefonePrincipal()).isEqualTo(UPDATED_TELEFONE_PRINCIPAL);
        assertThat(testEncarregadoEducacao.getTelefoneAlternativo()).isEqualTo(UPDATED_TELEFONE_ALTERNATIVO);
        assertThat(testEncarregadoEducacao.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEncarregadoEducacao.getResidencia()).isEqualTo(UPDATED_RESIDENCIA);
        assertThat(testEncarregadoEducacao.getEnderecoTrabalho()).isEqualTo(UPDATED_ENDERECO_TRABALHO);
        assertThat(testEncarregadoEducacao.getRendaMensal()).isEqualByComparingTo(UPDATED_RENDA_MENSAL);
        assertThat(testEncarregadoEducacao.getEmpresaTrabalho()).isEqualTo(UPDATED_EMPRESA_TRABALHO);
        assertThat(testEncarregadoEducacao.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    void patchNonExistingEncarregadoEducacao() throws Exception {
        int databaseSizeBeforeUpdate = encarregadoEducacaoRepository.findAll().size();
        encarregadoEducacao.setId(count.incrementAndGet());

        // Create the EncarregadoEducacao
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncarregadoEducacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, encarregadoEducacaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEncarregadoEducacao() throws Exception {
        int databaseSizeBeforeUpdate = encarregadoEducacaoRepository.findAll().size();
        encarregadoEducacao.setId(count.incrementAndGet());

        // Create the EncarregadoEducacao
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncarregadoEducacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEncarregadoEducacao() throws Exception {
        int databaseSizeBeforeUpdate = encarregadoEducacaoRepository.findAll().size();
        encarregadoEducacao.setId(count.incrementAndGet());

        // Create the EncarregadoEducacao
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncarregadoEducacaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEncarregadoEducacao() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        int databaseSizeBeforeDelete = encarregadoEducacaoRepository.findAll().size();

        // Delete the encarregadoEducacao
        restEncarregadoEducacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, encarregadoEducacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
