package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.MeioPagamento;
import com.ravunana.longonkelo.domain.Transacao;
import com.ravunana.longonkelo.repository.MeioPagamentoRepository;
import com.ravunana.longonkelo.service.criteria.MeioPagamentoCriteria;
import com.ravunana.longonkelo.service.dto.MeioPagamentoDTO;
import com.ravunana.longonkelo.service.mapper.MeioPagamentoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link MeioPagamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MeioPagamentoResourceIT {

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_DIGITO_REFERENCIA = 0;
    private static final Integer UPDATED_NUMERO_DIGITO_REFERENCIA = 1;
    private static final Integer SMALLER_NUMERO_DIGITO_REFERENCIA = 0 - 1;

    private static final Boolean DEFAULT_IS_PAGAMENTO_INSTANTANIO = false;
    private static final Boolean UPDATED_IS_PAGAMENTO_INSTANTANIO = true;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FORMATO_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_FORMATO_REFERENCIA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/meio-pagamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MeioPagamentoRepository meioPagamentoRepository;

    @Autowired
    private MeioPagamentoMapper meioPagamentoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMeioPagamentoMockMvc;

    private MeioPagamento meioPagamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeioPagamento createEntity(EntityManager em) {
        MeioPagamento meioPagamento = new MeioPagamento()
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .numeroDigitoReferencia(DEFAULT_NUMERO_DIGITO_REFERENCIA)
            .isPagamentoInstantanio(DEFAULT_IS_PAGAMENTO_INSTANTANIO)
            .hash(DEFAULT_HASH)
            .link(DEFAULT_LINK)
            .token(DEFAULT_TOKEN)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .formatoReferencia(DEFAULT_FORMATO_REFERENCIA);
        return meioPagamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeioPagamento createUpdatedEntity(EntityManager em) {
        MeioPagamento meioPagamento = new MeioPagamento()
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .numeroDigitoReferencia(UPDATED_NUMERO_DIGITO_REFERENCIA)
            .isPagamentoInstantanio(UPDATED_IS_PAGAMENTO_INSTANTANIO)
            .hash(UPDATED_HASH)
            .link(UPDATED_LINK)
            .token(UPDATED_TOKEN)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .formatoReferencia(UPDATED_FORMATO_REFERENCIA);
        return meioPagamento;
    }

    @BeforeEach
    public void initTest() {
        meioPagamento = createEntity(em);
    }

    @Test
    @Transactional
    void createMeioPagamento() throws Exception {
        int databaseSizeBeforeCreate = meioPagamentoRepository.findAll().size();
        // Create the MeioPagamento
        MeioPagamentoDTO meioPagamentoDTO = meioPagamentoMapper.toDto(meioPagamento);
        restMeioPagamentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(meioPagamentoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MeioPagamento in the database
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeCreate + 1);
        MeioPagamento testMeioPagamento = meioPagamentoList.get(meioPagamentoList.size() - 1);
        assertThat(testMeioPagamento.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testMeioPagamento.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testMeioPagamento.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testMeioPagamento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMeioPagamento.getNumeroDigitoReferencia()).isEqualTo(DEFAULT_NUMERO_DIGITO_REFERENCIA);
        assertThat(testMeioPagamento.getIsPagamentoInstantanio()).isEqualTo(DEFAULT_IS_PAGAMENTO_INSTANTANIO);
        assertThat(testMeioPagamento.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testMeioPagamento.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testMeioPagamento.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testMeioPagamento.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testMeioPagamento.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testMeioPagamento.getFormatoReferencia()).isEqualTo(DEFAULT_FORMATO_REFERENCIA);
    }

    @Test
    @Transactional
    void createMeioPagamentoWithExistingId() throws Exception {
        // Create the MeioPagamento with an existing ID
        meioPagamento.setId(1L);
        MeioPagamentoDTO meioPagamentoDTO = meioPagamentoMapper.toDto(meioPagamento);

        int databaseSizeBeforeCreate = meioPagamentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeioPagamentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(meioPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MeioPagamento in the database
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = meioPagamentoRepository.findAll().size();
        // set the field null
        meioPagamento.setCodigo(null);

        // Create the MeioPagamento, which fails.
        MeioPagamentoDTO meioPagamentoDTO = meioPagamentoMapper.toDto(meioPagamento);

        restMeioPagamentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(meioPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meioPagamentoRepository.findAll().size();
        // set the field null
        meioPagamento.setNome(null);

        // Create the MeioPagamento, which fails.
        MeioPagamentoDTO meioPagamentoDTO = meioPagamentoMapper.toDto(meioPagamento);

        restMeioPagamentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(meioPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMeioPagamentos() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList
        restMeioPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meioPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].numeroDigitoReferencia").value(hasItem(DEFAULT_NUMERO_DIGITO_REFERENCIA)))
            .andExpect(jsonPath("$.[*].isPagamentoInstantanio").value(hasItem(DEFAULT_IS_PAGAMENTO_INSTANTANIO.booleanValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].formatoReferencia").value(hasItem(DEFAULT_FORMATO_REFERENCIA)));
    }

    @Test
    @Transactional
    void getMeioPagamento() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get the meioPagamento
        restMeioPagamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, meioPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(meioPagamento.getId().intValue()))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.numeroDigitoReferencia").value(DEFAULT_NUMERO_DIGITO_REFERENCIA))
            .andExpect(jsonPath("$.isPagamentoInstantanio").value(DEFAULT_IS_PAGAMENTO_INSTANTANIO.booleanValue()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.formatoReferencia").value(DEFAULT_FORMATO_REFERENCIA));
    }

    @Test
    @Transactional
    void getMeioPagamentosByIdFiltering() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        Long id = meioPagamento.getId();

        defaultMeioPagamentoShouldBeFound("id.equals=" + id);
        defaultMeioPagamentoShouldNotBeFound("id.notEquals=" + id);

        defaultMeioPagamentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMeioPagamentoShouldNotBeFound("id.greaterThan=" + id);

        defaultMeioPagamentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMeioPagamentoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where codigo equals to DEFAULT_CODIGO
        defaultMeioPagamentoShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the meioPagamentoList where codigo equals to UPDATED_CODIGO
        defaultMeioPagamentoShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultMeioPagamentoShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the meioPagamentoList where codigo equals to UPDATED_CODIGO
        defaultMeioPagamentoShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where codigo is not null
        defaultMeioPagamentoShouldBeFound("codigo.specified=true");

        // Get all the meioPagamentoList where codigo is null
        defaultMeioPagamentoShouldNotBeFound("codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByCodigoContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where codigo contains DEFAULT_CODIGO
        defaultMeioPagamentoShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the meioPagamentoList where codigo contains UPDATED_CODIGO
        defaultMeioPagamentoShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where codigo does not contain DEFAULT_CODIGO
        defaultMeioPagamentoShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the meioPagamentoList where codigo does not contain UPDATED_CODIGO
        defaultMeioPagamentoShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where nome equals to DEFAULT_NOME
        defaultMeioPagamentoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the meioPagamentoList where nome equals to UPDATED_NOME
        defaultMeioPagamentoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultMeioPagamentoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the meioPagamentoList where nome equals to UPDATED_NOME
        defaultMeioPagamentoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where nome is not null
        defaultMeioPagamentoShouldBeFound("nome.specified=true");

        // Get all the meioPagamentoList where nome is null
        defaultMeioPagamentoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNomeContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where nome contains DEFAULT_NOME
        defaultMeioPagamentoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the meioPagamentoList where nome contains UPDATED_NOME
        defaultMeioPagamentoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where nome does not contain DEFAULT_NOME
        defaultMeioPagamentoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the meioPagamentoList where nome does not contain UPDATED_NOME
        defaultMeioPagamentoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNumeroDigitoReferenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where numeroDigitoReferencia equals to DEFAULT_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldBeFound("numeroDigitoReferencia.equals=" + DEFAULT_NUMERO_DIGITO_REFERENCIA);

        // Get all the meioPagamentoList where numeroDigitoReferencia equals to UPDATED_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldNotBeFound("numeroDigitoReferencia.equals=" + UPDATED_NUMERO_DIGITO_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNumeroDigitoReferenciaIsInShouldWork() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where numeroDigitoReferencia in DEFAULT_NUMERO_DIGITO_REFERENCIA or UPDATED_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldBeFound(
            "numeroDigitoReferencia.in=" + DEFAULT_NUMERO_DIGITO_REFERENCIA + "," + UPDATED_NUMERO_DIGITO_REFERENCIA
        );

        // Get all the meioPagamentoList where numeroDigitoReferencia equals to UPDATED_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldNotBeFound("numeroDigitoReferencia.in=" + UPDATED_NUMERO_DIGITO_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNumeroDigitoReferenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where numeroDigitoReferencia is not null
        defaultMeioPagamentoShouldBeFound("numeroDigitoReferencia.specified=true");

        // Get all the meioPagamentoList where numeroDigitoReferencia is null
        defaultMeioPagamentoShouldNotBeFound("numeroDigitoReferencia.specified=false");
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNumeroDigitoReferenciaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where numeroDigitoReferencia is greater than or equal to DEFAULT_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldBeFound("numeroDigitoReferencia.greaterThanOrEqual=" + DEFAULT_NUMERO_DIGITO_REFERENCIA);

        // Get all the meioPagamentoList where numeroDigitoReferencia is greater than or equal to UPDATED_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldNotBeFound("numeroDigitoReferencia.greaterThanOrEqual=" + UPDATED_NUMERO_DIGITO_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNumeroDigitoReferenciaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where numeroDigitoReferencia is less than or equal to DEFAULT_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldBeFound("numeroDigitoReferencia.lessThanOrEqual=" + DEFAULT_NUMERO_DIGITO_REFERENCIA);

        // Get all the meioPagamentoList where numeroDigitoReferencia is less than or equal to SMALLER_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldNotBeFound("numeroDigitoReferencia.lessThanOrEqual=" + SMALLER_NUMERO_DIGITO_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNumeroDigitoReferenciaIsLessThanSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where numeroDigitoReferencia is less than DEFAULT_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldNotBeFound("numeroDigitoReferencia.lessThan=" + DEFAULT_NUMERO_DIGITO_REFERENCIA);

        // Get all the meioPagamentoList where numeroDigitoReferencia is less than UPDATED_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldBeFound("numeroDigitoReferencia.lessThan=" + UPDATED_NUMERO_DIGITO_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByNumeroDigitoReferenciaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where numeroDigitoReferencia is greater than DEFAULT_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldNotBeFound("numeroDigitoReferencia.greaterThan=" + DEFAULT_NUMERO_DIGITO_REFERENCIA);

        // Get all the meioPagamentoList where numeroDigitoReferencia is greater than SMALLER_NUMERO_DIGITO_REFERENCIA
        defaultMeioPagamentoShouldBeFound("numeroDigitoReferencia.greaterThan=" + SMALLER_NUMERO_DIGITO_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByIsPagamentoInstantanioIsEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where isPagamentoInstantanio equals to DEFAULT_IS_PAGAMENTO_INSTANTANIO
        defaultMeioPagamentoShouldBeFound("isPagamentoInstantanio.equals=" + DEFAULT_IS_PAGAMENTO_INSTANTANIO);

        // Get all the meioPagamentoList where isPagamentoInstantanio equals to UPDATED_IS_PAGAMENTO_INSTANTANIO
        defaultMeioPagamentoShouldNotBeFound("isPagamentoInstantanio.equals=" + UPDATED_IS_PAGAMENTO_INSTANTANIO);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByIsPagamentoInstantanioIsInShouldWork() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where isPagamentoInstantanio in DEFAULT_IS_PAGAMENTO_INSTANTANIO or UPDATED_IS_PAGAMENTO_INSTANTANIO
        defaultMeioPagamentoShouldBeFound(
            "isPagamentoInstantanio.in=" + DEFAULT_IS_PAGAMENTO_INSTANTANIO + "," + UPDATED_IS_PAGAMENTO_INSTANTANIO
        );

        // Get all the meioPagamentoList where isPagamentoInstantanio equals to UPDATED_IS_PAGAMENTO_INSTANTANIO
        defaultMeioPagamentoShouldNotBeFound("isPagamentoInstantanio.in=" + UPDATED_IS_PAGAMENTO_INSTANTANIO);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByIsPagamentoInstantanioIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where isPagamentoInstantanio is not null
        defaultMeioPagamentoShouldBeFound("isPagamentoInstantanio.specified=true");

        // Get all the meioPagamentoList where isPagamentoInstantanio is null
        defaultMeioPagamentoShouldNotBeFound("isPagamentoInstantanio.specified=false");
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where hash equals to DEFAULT_HASH
        defaultMeioPagamentoShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the meioPagamentoList where hash equals to UPDATED_HASH
        defaultMeioPagamentoShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByHashIsInShouldWork() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultMeioPagamentoShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the meioPagamentoList where hash equals to UPDATED_HASH
        defaultMeioPagamentoShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where hash is not null
        defaultMeioPagamentoShouldBeFound("hash.specified=true");

        // Get all the meioPagamentoList where hash is null
        defaultMeioPagamentoShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByHashContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where hash contains DEFAULT_HASH
        defaultMeioPagamentoShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the meioPagamentoList where hash contains UPDATED_HASH
        defaultMeioPagamentoShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByHashNotContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where hash does not contain DEFAULT_HASH
        defaultMeioPagamentoShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the meioPagamentoList where hash does not contain UPDATED_HASH
        defaultMeioPagamentoShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where link equals to DEFAULT_LINK
        defaultMeioPagamentoShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the meioPagamentoList where link equals to UPDATED_LINK
        defaultMeioPagamentoShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where link in DEFAULT_LINK or UPDATED_LINK
        defaultMeioPagamentoShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the meioPagamentoList where link equals to UPDATED_LINK
        defaultMeioPagamentoShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where link is not null
        defaultMeioPagamentoShouldBeFound("link.specified=true");

        // Get all the meioPagamentoList where link is null
        defaultMeioPagamentoShouldNotBeFound("link.specified=false");
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByLinkContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where link contains DEFAULT_LINK
        defaultMeioPagamentoShouldBeFound("link.contains=" + DEFAULT_LINK);

        // Get all the meioPagamentoList where link contains UPDATED_LINK
        defaultMeioPagamentoShouldNotBeFound("link.contains=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByLinkNotContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where link does not contain DEFAULT_LINK
        defaultMeioPagamentoShouldNotBeFound("link.doesNotContain=" + DEFAULT_LINK);

        // Get all the meioPagamentoList where link does not contain UPDATED_LINK
        defaultMeioPagamentoShouldBeFound("link.doesNotContain=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where token equals to DEFAULT_TOKEN
        defaultMeioPagamentoShouldBeFound("token.equals=" + DEFAULT_TOKEN);

        // Get all the meioPagamentoList where token equals to UPDATED_TOKEN
        defaultMeioPagamentoShouldNotBeFound("token.equals=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByTokenIsInShouldWork() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where token in DEFAULT_TOKEN or UPDATED_TOKEN
        defaultMeioPagamentoShouldBeFound("token.in=" + DEFAULT_TOKEN + "," + UPDATED_TOKEN);

        // Get all the meioPagamentoList where token equals to UPDATED_TOKEN
        defaultMeioPagamentoShouldNotBeFound("token.in=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where token is not null
        defaultMeioPagamentoShouldBeFound("token.specified=true");

        // Get all the meioPagamentoList where token is null
        defaultMeioPagamentoShouldNotBeFound("token.specified=false");
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByTokenContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where token contains DEFAULT_TOKEN
        defaultMeioPagamentoShouldBeFound("token.contains=" + DEFAULT_TOKEN);

        // Get all the meioPagamentoList where token contains UPDATED_TOKEN
        defaultMeioPagamentoShouldNotBeFound("token.contains=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByTokenNotContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where token does not contain DEFAULT_TOKEN
        defaultMeioPagamentoShouldNotBeFound("token.doesNotContain=" + DEFAULT_TOKEN);

        // Get all the meioPagamentoList where token does not contain UPDATED_TOKEN
        defaultMeioPagamentoShouldBeFound("token.doesNotContain=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where username equals to DEFAULT_USERNAME
        defaultMeioPagamentoShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the meioPagamentoList where username equals to UPDATED_USERNAME
        defaultMeioPagamentoShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultMeioPagamentoShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the meioPagamentoList where username equals to UPDATED_USERNAME
        defaultMeioPagamentoShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where username is not null
        defaultMeioPagamentoShouldBeFound("username.specified=true");

        // Get all the meioPagamentoList where username is null
        defaultMeioPagamentoShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByUsernameContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where username contains DEFAULT_USERNAME
        defaultMeioPagamentoShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the meioPagamentoList where username contains UPDATED_USERNAME
        defaultMeioPagamentoShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where username does not contain DEFAULT_USERNAME
        defaultMeioPagamentoShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the meioPagamentoList where username does not contain UPDATED_USERNAME
        defaultMeioPagamentoShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where password equals to DEFAULT_PASSWORD
        defaultMeioPagamentoShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the meioPagamentoList where password equals to UPDATED_PASSWORD
        defaultMeioPagamentoShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultMeioPagamentoShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the meioPagamentoList where password equals to UPDATED_PASSWORD
        defaultMeioPagamentoShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where password is not null
        defaultMeioPagamentoShouldBeFound("password.specified=true");

        // Get all the meioPagamentoList where password is null
        defaultMeioPagamentoShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByPasswordContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where password contains DEFAULT_PASSWORD
        defaultMeioPagamentoShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the meioPagamentoList where password contains UPDATED_PASSWORD
        defaultMeioPagamentoShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where password does not contain DEFAULT_PASSWORD
        defaultMeioPagamentoShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the meioPagamentoList where password does not contain UPDATED_PASSWORD
        defaultMeioPagamentoShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByFormatoReferenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where formatoReferencia equals to DEFAULT_FORMATO_REFERENCIA
        defaultMeioPagamentoShouldBeFound("formatoReferencia.equals=" + DEFAULT_FORMATO_REFERENCIA);

        // Get all the meioPagamentoList where formatoReferencia equals to UPDATED_FORMATO_REFERENCIA
        defaultMeioPagamentoShouldNotBeFound("formatoReferencia.equals=" + UPDATED_FORMATO_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByFormatoReferenciaIsInShouldWork() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where formatoReferencia in DEFAULT_FORMATO_REFERENCIA or UPDATED_FORMATO_REFERENCIA
        defaultMeioPagamentoShouldBeFound("formatoReferencia.in=" + DEFAULT_FORMATO_REFERENCIA + "," + UPDATED_FORMATO_REFERENCIA);

        // Get all the meioPagamentoList where formatoReferencia equals to UPDATED_FORMATO_REFERENCIA
        defaultMeioPagamentoShouldNotBeFound("formatoReferencia.in=" + UPDATED_FORMATO_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByFormatoReferenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where formatoReferencia is not null
        defaultMeioPagamentoShouldBeFound("formatoReferencia.specified=true");

        // Get all the meioPagamentoList where formatoReferencia is null
        defaultMeioPagamentoShouldNotBeFound("formatoReferencia.specified=false");
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByFormatoReferenciaContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where formatoReferencia contains DEFAULT_FORMATO_REFERENCIA
        defaultMeioPagamentoShouldBeFound("formatoReferencia.contains=" + DEFAULT_FORMATO_REFERENCIA);

        // Get all the meioPagamentoList where formatoReferencia contains UPDATED_FORMATO_REFERENCIA
        defaultMeioPagamentoShouldNotBeFound("formatoReferencia.contains=" + UPDATED_FORMATO_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByFormatoReferenciaNotContainsSomething() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        // Get all the meioPagamentoList where formatoReferencia does not contain DEFAULT_FORMATO_REFERENCIA
        defaultMeioPagamentoShouldNotBeFound("formatoReferencia.doesNotContain=" + DEFAULT_FORMATO_REFERENCIA);

        // Get all the meioPagamentoList where formatoReferencia does not contain UPDATED_FORMATO_REFERENCIA
        defaultMeioPagamentoShouldBeFound("formatoReferencia.doesNotContain=" + UPDATED_FORMATO_REFERENCIA);
    }

    @Test
    @Transactional
    void getAllMeioPagamentosByTransacaoIsEqualToSomething() throws Exception {
        Transacao transacao;
        if (TestUtil.findAll(em, Transacao.class).isEmpty()) {
            meioPagamentoRepository.saveAndFlush(meioPagamento);
            transacao = TransacaoResourceIT.createEntity(em);
        } else {
            transacao = TestUtil.findAll(em, Transacao.class).get(0);
        }
        em.persist(transacao);
        em.flush();
        meioPagamento.addTransacao(transacao);
        meioPagamentoRepository.saveAndFlush(meioPagamento);
        Long transacaoId = transacao.getId();

        // Get all the meioPagamentoList where transacao equals to transacaoId
        defaultMeioPagamentoShouldBeFound("transacaoId.equals=" + transacaoId);

        // Get all the meioPagamentoList where transacao equals to (transacaoId + 1)
        defaultMeioPagamentoShouldNotBeFound("transacaoId.equals=" + (transacaoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMeioPagamentoShouldBeFound(String filter) throws Exception {
        restMeioPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meioPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].numeroDigitoReferencia").value(hasItem(DEFAULT_NUMERO_DIGITO_REFERENCIA)))
            .andExpect(jsonPath("$.[*].isPagamentoInstantanio").value(hasItem(DEFAULT_IS_PAGAMENTO_INSTANTANIO.booleanValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].formatoReferencia").value(hasItem(DEFAULT_FORMATO_REFERENCIA)));

        // Check, that the count call also returns 1
        restMeioPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMeioPagamentoShouldNotBeFound(String filter) throws Exception {
        restMeioPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMeioPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMeioPagamento() throws Exception {
        // Get the meioPagamento
        restMeioPagamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMeioPagamento() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        int databaseSizeBeforeUpdate = meioPagamentoRepository.findAll().size();

        // Update the meioPagamento
        MeioPagamento updatedMeioPagamento = meioPagamentoRepository.findById(meioPagamento.getId()).get();
        // Disconnect from session so that the updates on updatedMeioPagamento are not directly saved in db
        em.detach(updatedMeioPagamento);
        updatedMeioPagamento
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .numeroDigitoReferencia(UPDATED_NUMERO_DIGITO_REFERENCIA)
            .isPagamentoInstantanio(UPDATED_IS_PAGAMENTO_INSTANTANIO)
            .hash(UPDATED_HASH)
            .link(UPDATED_LINK)
            .token(UPDATED_TOKEN)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .formatoReferencia(UPDATED_FORMATO_REFERENCIA);
        MeioPagamentoDTO meioPagamentoDTO = meioPagamentoMapper.toDto(updatedMeioPagamento);

        restMeioPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, meioPagamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(meioPagamentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the MeioPagamento in the database
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeUpdate);
        MeioPagamento testMeioPagamento = meioPagamentoList.get(meioPagamentoList.size() - 1);
        assertThat(testMeioPagamento.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testMeioPagamento.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testMeioPagamento.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testMeioPagamento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMeioPagamento.getNumeroDigitoReferencia()).isEqualTo(UPDATED_NUMERO_DIGITO_REFERENCIA);
        assertThat(testMeioPagamento.getIsPagamentoInstantanio()).isEqualTo(UPDATED_IS_PAGAMENTO_INSTANTANIO);
        assertThat(testMeioPagamento.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testMeioPagamento.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testMeioPagamento.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testMeioPagamento.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testMeioPagamento.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMeioPagamento.getFormatoReferencia()).isEqualTo(UPDATED_FORMATO_REFERENCIA);
    }

    @Test
    @Transactional
    void putNonExistingMeioPagamento() throws Exception {
        int databaseSizeBeforeUpdate = meioPagamentoRepository.findAll().size();
        meioPagamento.setId(count.incrementAndGet());

        // Create the MeioPagamento
        MeioPagamentoDTO meioPagamentoDTO = meioPagamentoMapper.toDto(meioPagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeioPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, meioPagamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(meioPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MeioPagamento in the database
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMeioPagamento() throws Exception {
        int databaseSizeBeforeUpdate = meioPagamentoRepository.findAll().size();
        meioPagamento.setId(count.incrementAndGet());

        // Create the MeioPagamento
        MeioPagamentoDTO meioPagamentoDTO = meioPagamentoMapper.toDto(meioPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeioPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(meioPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MeioPagamento in the database
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMeioPagamento() throws Exception {
        int databaseSizeBeforeUpdate = meioPagamentoRepository.findAll().size();
        meioPagamento.setId(count.incrementAndGet());

        // Create the MeioPagamento
        MeioPagamentoDTO meioPagamentoDTO = meioPagamentoMapper.toDto(meioPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeioPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(meioPagamentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MeioPagamento in the database
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMeioPagamentoWithPatch() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        int databaseSizeBeforeUpdate = meioPagamentoRepository.findAll().size();

        // Update the meioPagamento using partial update
        MeioPagamento partialUpdatedMeioPagamento = new MeioPagamento();
        partialUpdatedMeioPagamento.setId(meioPagamento.getId());

        partialUpdatedMeioPagamento
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .codigo(UPDATED_CODIGO)
            .numeroDigitoReferencia(UPDATED_NUMERO_DIGITO_REFERENCIA)
            .link(UPDATED_LINK);

        restMeioPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeioPagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMeioPagamento))
            )
            .andExpect(status().isOk());

        // Validate the MeioPagamento in the database
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeUpdate);
        MeioPagamento testMeioPagamento = meioPagamentoList.get(meioPagamentoList.size() - 1);
        assertThat(testMeioPagamento.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testMeioPagamento.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testMeioPagamento.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testMeioPagamento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMeioPagamento.getNumeroDigitoReferencia()).isEqualTo(UPDATED_NUMERO_DIGITO_REFERENCIA);
        assertThat(testMeioPagamento.getIsPagamentoInstantanio()).isEqualTo(DEFAULT_IS_PAGAMENTO_INSTANTANIO);
        assertThat(testMeioPagamento.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testMeioPagamento.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testMeioPagamento.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testMeioPagamento.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testMeioPagamento.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testMeioPagamento.getFormatoReferencia()).isEqualTo(DEFAULT_FORMATO_REFERENCIA);
    }

    @Test
    @Transactional
    void fullUpdateMeioPagamentoWithPatch() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        int databaseSizeBeforeUpdate = meioPagamentoRepository.findAll().size();

        // Update the meioPagamento using partial update
        MeioPagamento partialUpdatedMeioPagamento = new MeioPagamento();
        partialUpdatedMeioPagamento.setId(meioPagamento.getId());

        partialUpdatedMeioPagamento
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .numeroDigitoReferencia(UPDATED_NUMERO_DIGITO_REFERENCIA)
            .isPagamentoInstantanio(UPDATED_IS_PAGAMENTO_INSTANTANIO)
            .hash(UPDATED_HASH)
            .link(UPDATED_LINK)
            .token(UPDATED_TOKEN)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .formatoReferencia(UPDATED_FORMATO_REFERENCIA);

        restMeioPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeioPagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMeioPagamento))
            )
            .andExpect(status().isOk());

        // Validate the MeioPagamento in the database
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeUpdate);
        MeioPagamento testMeioPagamento = meioPagamentoList.get(meioPagamentoList.size() - 1);
        assertThat(testMeioPagamento.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testMeioPagamento.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testMeioPagamento.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testMeioPagamento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMeioPagamento.getNumeroDigitoReferencia()).isEqualTo(UPDATED_NUMERO_DIGITO_REFERENCIA);
        assertThat(testMeioPagamento.getIsPagamentoInstantanio()).isEqualTo(UPDATED_IS_PAGAMENTO_INSTANTANIO);
        assertThat(testMeioPagamento.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testMeioPagamento.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testMeioPagamento.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testMeioPagamento.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testMeioPagamento.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMeioPagamento.getFormatoReferencia()).isEqualTo(UPDATED_FORMATO_REFERENCIA);
    }

    @Test
    @Transactional
    void patchNonExistingMeioPagamento() throws Exception {
        int databaseSizeBeforeUpdate = meioPagamentoRepository.findAll().size();
        meioPagamento.setId(count.incrementAndGet());

        // Create the MeioPagamento
        MeioPagamentoDTO meioPagamentoDTO = meioPagamentoMapper.toDto(meioPagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeioPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, meioPagamentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(meioPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MeioPagamento in the database
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMeioPagamento() throws Exception {
        int databaseSizeBeforeUpdate = meioPagamentoRepository.findAll().size();
        meioPagamento.setId(count.incrementAndGet());

        // Create the MeioPagamento
        MeioPagamentoDTO meioPagamentoDTO = meioPagamentoMapper.toDto(meioPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeioPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(meioPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MeioPagamento in the database
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMeioPagamento() throws Exception {
        int databaseSizeBeforeUpdate = meioPagamentoRepository.findAll().size();
        meioPagamento.setId(count.incrementAndGet());

        // Create the MeioPagamento
        MeioPagamentoDTO meioPagamentoDTO = meioPagamentoMapper.toDto(meioPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeioPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(meioPagamentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MeioPagamento in the database
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMeioPagamento() throws Exception {
        // Initialize the database
        meioPagamentoRepository.saveAndFlush(meioPagamento);

        int databaseSizeBeforeDelete = meioPagamentoRepository.findAll().size();

        // Delete the meioPagamento
        restMeioPagamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, meioPagamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MeioPagamento> meioPagamentoList = meioPagamentoRepository.findAll();
        assertThat(meioPagamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
