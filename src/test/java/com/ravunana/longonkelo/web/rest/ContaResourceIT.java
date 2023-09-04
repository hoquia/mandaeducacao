package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Conta;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.Transacao;
import com.ravunana.longonkelo.domain.enumeration.TipoConta;
import com.ravunana.longonkelo.repository.ContaRepository;
import com.ravunana.longonkelo.service.ContaService;
import com.ravunana.longonkelo.service.criteria.ContaCriteria;
import com.ravunana.longonkelo.service.dto.ContaDTO;
import com.ravunana.longonkelo.service.mapper.ContaMapper;
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
 * Integration tests for the {@link ContaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContaResourceIT {

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    private static final TipoConta DEFAULT_TIPO = TipoConta.CAIXA;
    private static final TipoConta UPDATED_TIPO = TipoConta.BANCO;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final String DEFAULT_TITULAR = "AAAAAAAAAA";
    private static final String UPDATED_TITULAR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PADRAO = false;
    private static final Boolean UPDATED_IS_PADRAO = true;

    private static final String ENTITY_API_URL = "/api/contas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContaRepository contaRepository;

    @Mock
    private ContaRepository contaRepositoryMock;

    @Autowired
    private ContaMapper contaMapper;

    @Mock
    private ContaService contaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContaMockMvc;

    private Conta conta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conta createEntity(EntityManager em) {
        Conta conta = new Conta()
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .tipo(DEFAULT_TIPO)
            .titulo(DEFAULT_TITULO)
            .numero(DEFAULT_NUMERO)
            .iban(DEFAULT_IBAN)
            .titular(DEFAULT_TITULAR)
            .isPadrao(DEFAULT_IS_PADRAO);
        return conta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conta createUpdatedEntity(EntityManager em) {
        Conta conta = new Conta()
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .numero(UPDATED_NUMERO)
            .iban(UPDATED_IBAN)
            .titular(UPDATED_TITULAR)
            .isPadrao(UPDATED_IS_PADRAO);
        return conta;
    }

    @BeforeEach
    public void initTest() {
        conta = createEntity(em);
    }

    @Test
    @Transactional
    void createConta() throws Exception {
        int databaseSizeBeforeCreate = contaRepository.findAll().size();
        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);
        restContaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isCreated());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeCreate + 1);
        Conta testConta = contaList.get(contaList.size() - 1);
        assertThat(testConta.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testConta.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testConta.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testConta.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testConta.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testConta.getIban()).isEqualTo(DEFAULT_IBAN);
        assertThat(testConta.getTitular()).isEqualTo(DEFAULT_TITULAR);
        assertThat(testConta.getIsPadrao()).isEqualTo(DEFAULT_IS_PADRAO);
    }

    @Test
    @Transactional
    void createContaWithExistingId() throws Exception {
        // Create the Conta with an existing ID
        conta.setId(1L);
        ContaDTO contaDTO = contaMapper.toDto(conta);

        int databaseSizeBeforeCreate = contaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaRepository.findAll().size();
        // set the field null
        conta.setTipo(null);

        // Create the Conta, which fails.
        ContaDTO contaDTO = contaMapper.toDto(conta);

        restContaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaRepository.findAll().size();
        // set the field null
        conta.setTitulo(null);

        // Create the Conta, which fails.
        ContaDTO contaDTO = contaMapper.toDto(conta);

        restContaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaRepository.findAll().size();
        // set the field null
        conta.setNumero(null);

        // Create the Conta, which fails.
        ContaDTO contaDTO = contaMapper.toDto(conta);

        restContaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitularIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaRepository.findAll().size();
        // set the field null
        conta.setTitular(null);

        // Create the Conta, which fails.
        ContaDTO contaDTO = contaMapper.toDto(conta);

        restContaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContas() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList
        restContaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conta.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
            .andExpect(jsonPath("$.[*].titular").value(hasItem(DEFAULT_TITULAR)))
            .andExpect(jsonPath("$.[*].isPadrao").value(hasItem(DEFAULT_IS_PADRAO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContasWithEagerRelationshipsIsEnabled() throws Exception {
        when(contaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(contaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getConta() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get the conta
        restContaMockMvc
            .perform(get(ENTITY_API_URL_ID, conta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conta.getId().intValue()))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN))
            .andExpect(jsonPath("$.titular").value(DEFAULT_TITULAR))
            .andExpect(jsonPath("$.isPadrao").value(DEFAULT_IS_PADRAO.booleanValue()));
    }

    @Test
    @Transactional
    void getContasByIdFiltering() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        Long id = conta.getId();

        defaultContaShouldBeFound("id.equals=" + id);
        defaultContaShouldNotBeFound("id.notEquals=" + id);

        defaultContaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContaShouldNotBeFound("id.greaterThan=" + id);

        defaultContaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContasByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where tipo equals to DEFAULT_TIPO
        defaultContaShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the contaList where tipo equals to UPDATED_TIPO
        defaultContaShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllContasByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultContaShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the contaList where tipo equals to UPDATED_TIPO
        defaultContaShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllContasByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where tipo is not null
        defaultContaShouldBeFound("tipo.specified=true");

        // Get all the contaList where tipo is null
        defaultContaShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    void getAllContasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where titulo equals to DEFAULT_TITULO
        defaultContaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the contaList where titulo equals to UPDATED_TITULO
        defaultContaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllContasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultContaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the contaList where titulo equals to UPDATED_TITULO
        defaultContaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllContasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where titulo is not null
        defaultContaShouldBeFound("titulo.specified=true");

        // Get all the contaList where titulo is null
        defaultContaShouldNotBeFound("titulo.specified=false");
    }

    @Test
    @Transactional
    void getAllContasByTituloContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where titulo contains DEFAULT_TITULO
        defaultContaShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the contaList where titulo contains UPDATED_TITULO
        defaultContaShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllContasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where titulo does not contain DEFAULT_TITULO
        defaultContaShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the contaList where titulo does not contain UPDATED_TITULO
        defaultContaShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllContasByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where numero equals to DEFAULT_NUMERO
        defaultContaShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the contaList where numero equals to UPDATED_NUMERO
        defaultContaShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllContasByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultContaShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the contaList where numero equals to UPDATED_NUMERO
        defaultContaShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllContasByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where numero is not null
        defaultContaShouldBeFound("numero.specified=true");

        // Get all the contaList where numero is null
        defaultContaShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    void getAllContasByNumeroContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where numero contains DEFAULT_NUMERO
        defaultContaShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the contaList where numero contains UPDATED_NUMERO
        defaultContaShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllContasByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where numero does not contain DEFAULT_NUMERO
        defaultContaShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the contaList where numero does not contain UPDATED_NUMERO
        defaultContaShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllContasByIbanIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where iban equals to DEFAULT_IBAN
        defaultContaShouldBeFound("iban.equals=" + DEFAULT_IBAN);

        // Get all the contaList where iban equals to UPDATED_IBAN
        defaultContaShouldNotBeFound("iban.equals=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    void getAllContasByIbanIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where iban in DEFAULT_IBAN or UPDATED_IBAN
        defaultContaShouldBeFound("iban.in=" + DEFAULT_IBAN + "," + UPDATED_IBAN);

        // Get all the contaList where iban equals to UPDATED_IBAN
        defaultContaShouldNotBeFound("iban.in=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    void getAllContasByIbanIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where iban is not null
        defaultContaShouldBeFound("iban.specified=true");

        // Get all the contaList where iban is null
        defaultContaShouldNotBeFound("iban.specified=false");
    }

    @Test
    @Transactional
    void getAllContasByIbanContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where iban contains DEFAULT_IBAN
        defaultContaShouldBeFound("iban.contains=" + DEFAULT_IBAN);

        // Get all the contaList where iban contains UPDATED_IBAN
        defaultContaShouldNotBeFound("iban.contains=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    void getAllContasByIbanNotContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where iban does not contain DEFAULT_IBAN
        defaultContaShouldNotBeFound("iban.doesNotContain=" + DEFAULT_IBAN);

        // Get all the contaList where iban does not contain UPDATED_IBAN
        defaultContaShouldBeFound("iban.doesNotContain=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    void getAllContasByTitularIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where titular equals to DEFAULT_TITULAR
        defaultContaShouldBeFound("titular.equals=" + DEFAULT_TITULAR);

        // Get all the contaList where titular equals to UPDATED_TITULAR
        defaultContaShouldNotBeFound("titular.equals=" + UPDATED_TITULAR);
    }

    @Test
    @Transactional
    void getAllContasByTitularIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where titular in DEFAULT_TITULAR or UPDATED_TITULAR
        defaultContaShouldBeFound("titular.in=" + DEFAULT_TITULAR + "," + UPDATED_TITULAR);

        // Get all the contaList where titular equals to UPDATED_TITULAR
        defaultContaShouldNotBeFound("titular.in=" + UPDATED_TITULAR);
    }

    @Test
    @Transactional
    void getAllContasByTitularIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where titular is not null
        defaultContaShouldBeFound("titular.specified=true");

        // Get all the contaList where titular is null
        defaultContaShouldNotBeFound("titular.specified=false");
    }

    @Test
    @Transactional
    void getAllContasByTitularContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where titular contains DEFAULT_TITULAR
        defaultContaShouldBeFound("titular.contains=" + DEFAULT_TITULAR);

        // Get all the contaList where titular contains UPDATED_TITULAR
        defaultContaShouldNotBeFound("titular.contains=" + UPDATED_TITULAR);
    }

    @Test
    @Transactional
    void getAllContasByTitularNotContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where titular does not contain DEFAULT_TITULAR
        defaultContaShouldNotBeFound("titular.doesNotContain=" + DEFAULT_TITULAR);

        // Get all the contaList where titular does not contain UPDATED_TITULAR
        defaultContaShouldBeFound("titular.doesNotContain=" + UPDATED_TITULAR);
    }

    @Test
    @Transactional
    void getAllContasByIsPadraoIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where isPadrao equals to DEFAULT_IS_PADRAO
        defaultContaShouldBeFound("isPadrao.equals=" + DEFAULT_IS_PADRAO);

        // Get all the contaList where isPadrao equals to UPDATED_IS_PADRAO
        defaultContaShouldNotBeFound("isPadrao.equals=" + UPDATED_IS_PADRAO);
    }

    @Test
    @Transactional
    void getAllContasByIsPadraoIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where isPadrao in DEFAULT_IS_PADRAO or UPDATED_IS_PADRAO
        defaultContaShouldBeFound("isPadrao.in=" + DEFAULT_IS_PADRAO + "," + UPDATED_IS_PADRAO);

        // Get all the contaList where isPadrao equals to UPDATED_IS_PADRAO
        defaultContaShouldNotBeFound("isPadrao.in=" + UPDATED_IS_PADRAO);
    }

    @Test
    @Transactional
    void getAllContasByIsPadraoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where isPadrao is not null
        defaultContaShouldBeFound("isPadrao.specified=true");

        // Get all the contaList where isPadrao is null
        defaultContaShouldNotBeFound("isPadrao.specified=false");
    }

    @Test
    @Transactional
    void getAllContasByTransacoesIsEqualToSomething() throws Exception {
        Transacao transacoes;
        if (TestUtil.findAll(em, Transacao.class).isEmpty()) {
            contaRepository.saveAndFlush(conta);
            transacoes = TransacaoResourceIT.createEntity(em);
        } else {
            transacoes = TestUtil.findAll(em, Transacao.class).get(0);
        }
        em.persist(transacoes);
        em.flush();
        conta.addTransacoes(transacoes);
        contaRepository.saveAndFlush(conta);
        Long transacoesId = transacoes.getId();

        // Get all the contaList where transacoes equals to transacoesId
        defaultContaShouldBeFound("transacoesId.equals=" + transacoesId);

        // Get all the contaList where transacoes equals to (transacoesId + 1)
        defaultContaShouldNotBeFound("transacoesId.equals=" + (transacoesId + 1));
    }

    @Test
    @Transactional
    void getAllContasByMoedaIsEqualToSomething() throws Exception {
        LookupItem moeda;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            contaRepository.saveAndFlush(conta);
            moeda = LookupItemResourceIT.createEntity(em);
        } else {
            moeda = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(moeda);
        em.flush();
        conta.setMoeda(moeda);
        contaRepository.saveAndFlush(conta);
        Long moedaId = moeda.getId();

        // Get all the contaList where moeda equals to moedaId
        defaultContaShouldBeFound("moedaId.equals=" + moedaId);

        // Get all the contaList where moeda equals to (moedaId + 1)
        defaultContaShouldNotBeFound("moedaId.equals=" + (moedaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContaShouldBeFound(String filter) throws Exception {
        restContaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conta.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
            .andExpect(jsonPath("$.[*].titular").value(hasItem(DEFAULT_TITULAR)))
            .andExpect(jsonPath("$.[*].isPadrao").value(hasItem(DEFAULT_IS_PADRAO.booleanValue())));

        // Check, that the count call also returns 1
        restContaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContaShouldNotBeFound(String filter) throws Exception {
        restContaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConta() throws Exception {
        // Get the conta
        restContaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConta() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        int databaseSizeBeforeUpdate = contaRepository.findAll().size();

        // Update the conta
        Conta updatedConta = contaRepository.findById(conta.getId()).get();
        // Disconnect from session so that the updates on updatedConta are not directly saved in db
        em.detach(updatedConta);
        updatedConta
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .numero(UPDATED_NUMERO)
            .iban(UPDATED_IBAN)
            .titular(UPDATED_TITULAR)
            .isPadrao(UPDATED_IS_PADRAO);
        ContaDTO contaDTO = contaMapper.toDto(updatedConta);

        restContaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
        Conta testConta = contaList.get(contaList.size() - 1);
        assertThat(testConta.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testConta.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testConta.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testConta.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testConta.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testConta.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testConta.getTitular()).isEqualTo(UPDATED_TITULAR);
        assertThat(testConta.getIsPadrao()).isEqualTo(UPDATED_IS_PADRAO);
    }

    @Test
    @Transactional
    void putNonExistingConta() throws Exception {
        int databaseSizeBeforeUpdate = contaRepository.findAll().size();
        conta.setId(count.incrementAndGet());

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConta() throws Exception {
        int databaseSizeBeforeUpdate = contaRepository.findAll().size();
        conta.setId(count.incrementAndGet());

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConta() throws Exception {
        int databaseSizeBeforeUpdate = contaRepository.findAll().size();
        conta.setId(count.incrementAndGet());

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContaWithPatch() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        int databaseSizeBeforeUpdate = contaRepository.findAll().size();

        // Update the conta using partial update
        Conta partialUpdatedConta = new Conta();
        partialUpdatedConta.setId(conta.getId());

        partialUpdatedConta.titulo(UPDATED_TITULO).numero(UPDATED_NUMERO).titular(UPDATED_TITULAR).isPadrao(UPDATED_IS_PADRAO);

        restContaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConta))
            )
            .andExpect(status().isOk());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
        Conta testConta = contaList.get(contaList.size() - 1);
        assertThat(testConta.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testConta.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testConta.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testConta.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testConta.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testConta.getIban()).isEqualTo(DEFAULT_IBAN);
        assertThat(testConta.getTitular()).isEqualTo(UPDATED_TITULAR);
        assertThat(testConta.getIsPadrao()).isEqualTo(UPDATED_IS_PADRAO);
    }

    @Test
    @Transactional
    void fullUpdateContaWithPatch() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        int databaseSizeBeforeUpdate = contaRepository.findAll().size();

        // Update the conta using partial update
        Conta partialUpdatedConta = new Conta();
        partialUpdatedConta.setId(conta.getId());

        partialUpdatedConta
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .numero(UPDATED_NUMERO)
            .iban(UPDATED_IBAN)
            .titular(UPDATED_TITULAR)
            .isPadrao(UPDATED_IS_PADRAO);

        restContaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConta))
            )
            .andExpect(status().isOk());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
        Conta testConta = contaList.get(contaList.size() - 1);
        assertThat(testConta.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testConta.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testConta.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testConta.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testConta.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testConta.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testConta.getTitular()).isEqualTo(UPDATED_TITULAR);
        assertThat(testConta.getIsPadrao()).isEqualTo(UPDATED_IS_PADRAO);
    }

    @Test
    @Transactional
    void patchNonExistingConta() throws Exception {
        int databaseSizeBeforeUpdate = contaRepository.findAll().size();
        conta.setId(count.incrementAndGet());

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConta() throws Exception {
        int databaseSizeBeforeUpdate = contaRepository.findAll().size();
        conta.setId(count.incrementAndGet());

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConta() throws Exception {
        int databaseSizeBeforeUpdate = contaRepository.findAll().size();
        conta.setId(count.incrementAndGet());

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConta() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        int databaseSizeBeforeDelete = contaRepository.findAll().size();

        // Delete the conta
        restContaMockMvc
            .perform(delete(ENTITY_API_URL_ID, conta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
