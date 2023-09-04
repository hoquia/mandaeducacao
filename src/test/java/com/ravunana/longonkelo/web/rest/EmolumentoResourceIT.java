package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.CategoriaEmolumento;
import com.ravunana.longonkelo.domain.Emolumento;
import com.ravunana.longonkelo.domain.Emolumento;
import com.ravunana.longonkelo.domain.Imposto;
import com.ravunana.longonkelo.domain.ItemFactura;
import com.ravunana.longonkelo.domain.PlanoMulta;
import com.ravunana.longonkelo.domain.PrecoEmolumento;
import com.ravunana.longonkelo.repository.EmolumentoRepository;
import com.ravunana.longonkelo.service.EmolumentoService;
import com.ravunana.longonkelo.service.criteria.EmolumentoCriteria;
import com.ravunana.longonkelo.service.dto.EmolumentoDTO;
import com.ravunana.longonkelo.service.mapper.EmolumentoMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link EmolumentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmolumentoResourceIT {

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRECO = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRECO = new BigDecimal(1);
    private static final BigDecimal SMALLER_PRECO = new BigDecimal(0 - 1);

    private static final Double DEFAULT_QUANTIDADE = 0D;
    private static final Double UPDATED_QUANTIDADE = 1D;
    private static final Double SMALLER_QUANTIDADE = 0D - 1D;

    private static final Integer DEFAULT_PERIODO = 1;
    private static final Integer UPDATED_PERIODO = 2;
    private static final Integer SMALLER_PERIODO = 1 - 1;

    private static final Integer DEFAULT_INICIO_PERIODO = 1;
    private static final Integer UPDATED_INICIO_PERIODO = 2;
    private static final Integer SMALLER_INICIO_PERIODO = 1 - 1;

    private static final Integer DEFAULT_FIM_PERIODO = 1;
    private static final Integer UPDATED_FIM_PERIODO = 2;
    private static final Integer SMALLER_FIM_PERIODO = 1 - 1;

    private static final Boolean DEFAULT_IS_OBRIGATORIO_MATRICULA = false;
    private static final Boolean UPDATED_IS_OBRIGATORIO_MATRICULA = true;

    private static final Boolean DEFAULT_IS_OBRIGATORIO_CONFIRMACAO = false;
    private static final Boolean UPDATED_IS_OBRIGATORIO_CONFIRMACAO = true;

    private static final String ENTITY_API_URL = "/api/emolumentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmolumentoRepository emolumentoRepository;

    @Mock
    private EmolumentoRepository emolumentoRepositoryMock;

    @Autowired
    private EmolumentoMapper emolumentoMapper;

    @Mock
    private EmolumentoService emolumentoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmolumentoMockMvc;

    private Emolumento emolumento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emolumento createEntity(EntityManager em) {
        Emolumento emolumento = new Emolumento()
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .numero(DEFAULT_NUMERO)
            .nome(DEFAULT_NOME)
            .preco(DEFAULT_PRECO)
            .quantidade(DEFAULT_QUANTIDADE)
            .periodo(DEFAULT_PERIODO)
            .inicioPeriodo(DEFAULT_INICIO_PERIODO)
            .fimPeriodo(DEFAULT_FIM_PERIODO)
            .isObrigatorioMatricula(DEFAULT_IS_OBRIGATORIO_MATRICULA)
            .isObrigatorioConfirmacao(DEFAULT_IS_OBRIGATORIO_CONFIRMACAO);
        // Add required entity
        CategoriaEmolumento categoriaEmolumento;
        if (TestUtil.findAll(em, CategoriaEmolumento.class).isEmpty()) {
            categoriaEmolumento = CategoriaEmolumentoResourceIT.createEntity(em);
            em.persist(categoriaEmolumento);
            em.flush();
        } else {
            categoriaEmolumento = TestUtil.findAll(em, CategoriaEmolumento.class).get(0);
        }
        emolumento.setCategoria(categoriaEmolumento);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        emolumento.setImposto(imposto);
        return emolumento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emolumento createUpdatedEntity(EntityManager em) {
        Emolumento emolumento = new Emolumento()
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .numero(UPDATED_NUMERO)
            .nome(UPDATED_NOME)
            .preco(UPDATED_PRECO)
            .quantidade(UPDATED_QUANTIDADE)
            .periodo(UPDATED_PERIODO)
            .inicioPeriodo(UPDATED_INICIO_PERIODO)
            .fimPeriodo(UPDATED_FIM_PERIODO)
            .isObrigatorioMatricula(UPDATED_IS_OBRIGATORIO_MATRICULA)
            .isObrigatorioConfirmacao(UPDATED_IS_OBRIGATORIO_CONFIRMACAO);
        // Add required entity
        CategoriaEmolumento categoriaEmolumento;
        if (TestUtil.findAll(em, CategoriaEmolumento.class).isEmpty()) {
            categoriaEmolumento = CategoriaEmolumentoResourceIT.createUpdatedEntity(em);
            em.persist(categoriaEmolumento);
            em.flush();
        } else {
            categoriaEmolumento = TestUtil.findAll(em, CategoriaEmolumento.class).get(0);
        }
        emolumento.setCategoria(categoriaEmolumento);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createUpdatedEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        emolumento.setImposto(imposto);
        return emolumento;
    }

    @BeforeEach
    public void initTest() {
        emolumento = createEntity(em);
    }

    @Test
    @Transactional
    void createEmolumento() throws Exception {
        int databaseSizeBeforeCreate = emolumentoRepository.findAll().size();
        // Create the Emolumento
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);
        restEmolumentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emolumentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Emolumento in the database
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeCreate + 1);
        Emolumento testEmolumento = emolumentoList.get(emolumentoList.size() - 1);
        assertThat(testEmolumento.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testEmolumento.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testEmolumento.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEmolumento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEmolumento.getPreco()).isEqualByComparingTo(DEFAULT_PRECO);
        assertThat(testEmolumento.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testEmolumento.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testEmolumento.getInicioPeriodo()).isEqualTo(DEFAULT_INICIO_PERIODO);
        assertThat(testEmolumento.getFimPeriodo()).isEqualTo(DEFAULT_FIM_PERIODO);
        assertThat(testEmolumento.getIsObrigatorioMatricula()).isEqualTo(DEFAULT_IS_OBRIGATORIO_MATRICULA);
        assertThat(testEmolumento.getIsObrigatorioConfirmacao()).isEqualTo(DEFAULT_IS_OBRIGATORIO_CONFIRMACAO);
    }

    @Test
    @Transactional
    void createEmolumentoWithExistingId() throws Exception {
        // Create the Emolumento with an existing ID
        emolumento.setId(1L);
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);

        int databaseSizeBeforeCreate = emolumentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmolumentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emolumentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Emolumento in the database
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = emolumentoRepository.findAll().size();
        // set the field null
        emolumento.setNumero(null);

        // Create the Emolumento, which fails.
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);

        restEmolumentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emolumentoDTO)))
            .andExpect(status().isBadRequest());

        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = emolumentoRepository.findAll().size();
        // set the field null
        emolumento.setNome(null);

        // Create the Emolumento, which fails.
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);

        restEmolumentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emolumentoDTO)))
            .andExpect(status().isBadRequest());

        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = emolumentoRepository.findAll().size();
        // set the field null
        emolumento.setPreco(null);

        // Create the Emolumento, which fails.
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);

        restEmolumentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emolumentoDTO)))
            .andExpect(status().isBadRequest());

        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = emolumentoRepository.findAll().size();
        // set the field null
        emolumento.setQuantidade(null);

        // Create the Emolumento, which fails.
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);

        restEmolumentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emolumentoDTO)))
            .andExpect(status().isBadRequest());

        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmolumentos() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList
        restEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emolumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(sameNumber(DEFAULT_PRECO))))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO)))
            .andExpect(jsonPath("$.[*].inicioPeriodo").value(hasItem(DEFAULT_INICIO_PERIODO)))
            .andExpect(jsonPath("$.[*].fimPeriodo").value(hasItem(DEFAULT_FIM_PERIODO)))
            .andExpect(jsonPath("$.[*].isObrigatorioMatricula").value(hasItem(DEFAULT_IS_OBRIGATORIO_MATRICULA.booleanValue())))
            .andExpect(jsonPath("$.[*].isObrigatorioConfirmacao").value(hasItem(DEFAULT_IS_OBRIGATORIO_CONFIRMACAO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmolumentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(emolumentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmolumentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(emolumentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmolumentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(emolumentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmolumentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(emolumentoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEmolumento() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get the emolumento
        restEmolumentoMockMvc
            .perform(get(ENTITY_API_URL_ID, emolumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emolumento.getId().intValue()))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.preco").value(sameNumber(DEFAULT_PRECO)))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.doubleValue()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO))
            .andExpect(jsonPath("$.inicioPeriodo").value(DEFAULT_INICIO_PERIODO))
            .andExpect(jsonPath("$.fimPeriodo").value(DEFAULT_FIM_PERIODO))
            .andExpect(jsonPath("$.isObrigatorioMatricula").value(DEFAULT_IS_OBRIGATORIO_MATRICULA.booleanValue()))
            .andExpect(jsonPath("$.isObrigatorioConfirmacao").value(DEFAULT_IS_OBRIGATORIO_CONFIRMACAO.booleanValue()));
    }

    @Test
    @Transactional
    void getEmolumentosByIdFiltering() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        Long id = emolumento.getId();

        defaultEmolumentoShouldBeFound("id.equals=" + id);
        defaultEmolumentoShouldNotBeFound("id.notEquals=" + id);

        defaultEmolumentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmolumentoShouldNotBeFound("id.greaterThan=" + id);

        defaultEmolumentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmolumentoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmolumentosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where numero equals to DEFAULT_NUMERO
        defaultEmolumentoShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the emolumentoList where numero equals to UPDATED_NUMERO
        defaultEmolumentoShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultEmolumentoShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the emolumentoList where numero equals to UPDATED_NUMERO
        defaultEmolumentoShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where numero is not null
        defaultEmolumentoShouldBeFound("numero.specified=true");

        // Get all the emolumentoList where numero is null
        defaultEmolumentoShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    void getAllEmolumentosByNumeroContainsSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where numero contains DEFAULT_NUMERO
        defaultEmolumentoShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the emolumentoList where numero contains UPDATED_NUMERO
        defaultEmolumentoShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where numero does not contain DEFAULT_NUMERO
        defaultEmolumentoShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the emolumentoList where numero does not contain UPDATED_NUMERO
        defaultEmolumentoShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where nome equals to DEFAULT_NOME
        defaultEmolumentoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the emolumentoList where nome equals to UPDATED_NOME
        defaultEmolumentoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEmolumentosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultEmolumentoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the emolumentoList where nome equals to UPDATED_NOME
        defaultEmolumentoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEmolumentosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where nome is not null
        defaultEmolumentoShouldBeFound("nome.specified=true");

        // Get all the emolumentoList where nome is null
        defaultEmolumentoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllEmolumentosByNomeContainsSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where nome contains DEFAULT_NOME
        defaultEmolumentoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the emolumentoList where nome contains UPDATED_NOME
        defaultEmolumentoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEmolumentosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where nome does not contain DEFAULT_NOME
        defaultEmolumentoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the emolumentoList where nome does not contain UPDATED_NOME
        defaultEmolumentoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEmolumentosByPrecoIsEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where preco equals to DEFAULT_PRECO
        defaultEmolumentoShouldBeFound("preco.equals=" + DEFAULT_PRECO);

        // Get all the emolumentoList where preco equals to UPDATED_PRECO
        defaultEmolumentoShouldNotBeFound("preco.equals=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByPrecoIsInShouldWork() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where preco in DEFAULT_PRECO or UPDATED_PRECO
        defaultEmolumentoShouldBeFound("preco.in=" + DEFAULT_PRECO + "," + UPDATED_PRECO);

        // Get all the emolumentoList where preco equals to UPDATED_PRECO
        defaultEmolumentoShouldNotBeFound("preco.in=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByPrecoIsNullOrNotNull() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where preco is not null
        defaultEmolumentoShouldBeFound("preco.specified=true");

        // Get all the emolumentoList where preco is null
        defaultEmolumentoShouldNotBeFound("preco.specified=false");
    }

    @Test
    @Transactional
    void getAllEmolumentosByPrecoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where preco is greater than or equal to DEFAULT_PRECO
        defaultEmolumentoShouldBeFound("preco.greaterThanOrEqual=" + DEFAULT_PRECO);

        // Get all the emolumentoList where preco is greater than or equal to UPDATED_PRECO
        defaultEmolumentoShouldNotBeFound("preco.greaterThanOrEqual=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByPrecoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where preco is less than or equal to DEFAULT_PRECO
        defaultEmolumentoShouldBeFound("preco.lessThanOrEqual=" + DEFAULT_PRECO);

        // Get all the emolumentoList where preco is less than or equal to SMALLER_PRECO
        defaultEmolumentoShouldNotBeFound("preco.lessThanOrEqual=" + SMALLER_PRECO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByPrecoIsLessThanSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where preco is less than DEFAULT_PRECO
        defaultEmolumentoShouldNotBeFound("preco.lessThan=" + DEFAULT_PRECO);

        // Get all the emolumentoList where preco is less than UPDATED_PRECO
        defaultEmolumentoShouldBeFound("preco.lessThan=" + UPDATED_PRECO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByPrecoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where preco is greater than DEFAULT_PRECO
        defaultEmolumentoShouldNotBeFound("preco.greaterThan=" + DEFAULT_PRECO);

        // Get all the emolumentoList where preco is greater than SMALLER_PRECO
        defaultEmolumentoShouldBeFound("preco.greaterThan=" + SMALLER_PRECO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where quantidade equals to DEFAULT_QUANTIDADE
        defaultEmolumentoShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the emolumentoList where quantidade equals to UPDATED_QUANTIDADE
        defaultEmolumentoShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEmolumentosByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultEmolumentoShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the emolumentoList where quantidade equals to UPDATED_QUANTIDADE
        defaultEmolumentoShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEmolumentosByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where quantidade is not null
        defaultEmolumentoShouldBeFound("quantidade.specified=true");

        // Get all the emolumentoList where quantidade is null
        defaultEmolumentoShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    void getAllEmolumentosByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultEmolumentoShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the emolumentoList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultEmolumentoShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEmolumentosByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultEmolumentoShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the emolumentoList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultEmolumentoShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEmolumentosByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where quantidade is less than DEFAULT_QUANTIDADE
        defaultEmolumentoShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the emolumentoList where quantidade is less than UPDATED_QUANTIDADE
        defaultEmolumentoShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEmolumentosByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultEmolumentoShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the emolumentoList where quantidade is greater than SMALLER_QUANTIDADE
        defaultEmolumentoShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllEmolumentosByPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where periodo equals to DEFAULT_PERIODO
        defaultEmolumentoShouldBeFound("periodo.equals=" + DEFAULT_PERIODO);

        // Get all the emolumentoList where periodo equals to UPDATED_PERIODO
        defaultEmolumentoShouldNotBeFound("periodo.equals=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where periodo in DEFAULT_PERIODO or UPDATED_PERIODO
        defaultEmolumentoShouldBeFound("periodo.in=" + DEFAULT_PERIODO + "," + UPDATED_PERIODO);

        // Get all the emolumentoList where periodo equals to UPDATED_PERIODO
        defaultEmolumentoShouldNotBeFound("periodo.in=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where periodo is not null
        defaultEmolumentoShouldBeFound("periodo.specified=true");

        // Get all the emolumentoList where periodo is null
        defaultEmolumentoShouldNotBeFound("periodo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmolumentosByPeriodoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where periodo is greater than or equal to DEFAULT_PERIODO
        defaultEmolumentoShouldBeFound("periodo.greaterThanOrEqual=" + DEFAULT_PERIODO);

        // Get all the emolumentoList where periodo is greater than or equal to (DEFAULT_PERIODO + 1)
        defaultEmolumentoShouldNotBeFound("periodo.greaterThanOrEqual=" + (DEFAULT_PERIODO + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByPeriodoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where periodo is less than or equal to DEFAULT_PERIODO
        defaultEmolumentoShouldBeFound("periodo.lessThanOrEqual=" + DEFAULT_PERIODO);

        // Get all the emolumentoList where periodo is less than or equal to SMALLER_PERIODO
        defaultEmolumentoShouldNotBeFound("periodo.lessThanOrEqual=" + SMALLER_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByPeriodoIsLessThanSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where periodo is less than DEFAULT_PERIODO
        defaultEmolumentoShouldNotBeFound("periodo.lessThan=" + DEFAULT_PERIODO);

        // Get all the emolumentoList where periodo is less than (DEFAULT_PERIODO + 1)
        defaultEmolumentoShouldBeFound("periodo.lessThan=" + (DEFAULT_PERIODO + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByPeriodoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where periodo is greater than DEFAULT_PERIODO
        defaultEmolumentoShouldNotBeFound("periodo.greaterThan=" + DEFAULT_PERIODO);

        // Get all the emolumentoList where periodo is greater than SMALLER_PERIODO
        defaultEmolumentoShouldBeFound("periodo.greaterThan=" + SMALLER_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByInicioPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where inicioPeriodo equals to DEFAULT_INICIO_PERIODO
        defaultEmolumentoShouldBeFound("inicioPeriodo.equals=" + DEFAULT_INICIO_PERIODO);

        // Get all the emolumentoList where inicioPeriodo equals to UPDATED_INICIO_PERIODO
        defaultEmolumentoShouldNotBeFound("inicioPeriodo.equals=" + UPDATED_INICIO_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByInicioPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where inicioPeriodo in DEFAULT_INICIO_PERIODO or UPDATED_INICIO_PERIODO
        defaultEmolumentoShouldBeFound("inicioPeriodo.in=" + DEFAULT_INICIO_PERIODO + "," + UPDATED_INICIO_PERIODO);

        // Get all the emolumentoList where inicioPeriodo equals to UPDATED_INICIO_PERIODO
        defaultEmolumentoShouldNotBeFound("inicioPeriodo.in=" + UPDATED_INICIO_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByInicioPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where inicioPeriodo is not null
        defaultEmolumentoShouldBeFound("inicioPeriodo.specified=true");

        // Get all the emolumentoList where inicioPeriodo is null
        defaultEmolumentoShouldNotBeFound("inicioPeriodo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmolumentosByInicioPeriodoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where inicioPeriodo is greater than or equal to DEFAULT_INICIO_PERIODO
        defaultEmolumentoShouldBeFound("inicioPeriodo.greaterThanOrEqual=" + DEFAULT_INICIO_PERIODO);

        // Get all the emolumentoList where inicioPeriodo is greater than or equal to (DEFAULT_INICIO_PERIODO + 1)
        defaultEmolumentoShouldNotBeFound("inicioPeriodo.greaterThanOrEqual=" + (DEFAULT_INICIO_PERIODO + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByInicioPeriodoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where inicioPeriodo is less than or equal to DEFAULT_INICIO_PERIODO
        defaultEmolumentoShouldBeFound("inicioPeriodo.lessThanOrEqual=" + DEFAULT_INICIO_PERIODO);

        // Get all the emolumentoList where inicioPeriodo is less than or equal to SMALLER_INICIO_PERIODO
        defaultEmolumentoShouldNotBeFound("inicioPeriodo.lessThanOrEqual=" + SMALLER_INICIO_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByInicioPeriodoIsLessThanSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where inicioPeriodo is less than DEFAULT_INICIO_PERIODO
        defaultEmolumentoShouldNotBeFound("inicioPeriodo.lessThan=" + DEFAULT_INICIO_PERIODO);

        // Get all the emolumentoList where inicioPeriodo is less than (DEFAULT_INICIO_PERIODO + 1)
        defaultEmolumentoShouldBeFound("inicioPeriodo.lessThan=" + (DEFAULT_INICIO_PERIODO + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByInicioPeriodoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where inicioPeriodo is greater than DEFAULT_INICIO_PERIODO
        defaultEmolumentoShouldNotBeFound("inicioPeriodo.greaterThan=" + DEFAULT_INICIO_PERIODO);

        // Get all the emolumentoList where inicioPeriodo is greater than SMALLER_INICIO_PERIODO
        defaultEmolumentoShouldBeFound("inicioPeriodo.greaterThan=" + SMALLER_INICIO_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByFimPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where fimPeriodo equals to DEFAULT_FIM_PERIODO
        defaultEmolumentoShouldBeFound("fimPeriodo.equals=" + DEFAULT_FIM_PERIODO);

        // Get all the emolumentoList where fimPeriodo equals to UPDATED_FIM_PERIODO
        defaultEmolumentoShouldNotBeFound("fimPeriodo.equals=" + UPDATED_FIM_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByFimPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where fimPeriodo in DEFAULT_FIM_PERIODO or UPDATED_FIM_PERIODO
        defaultEmolumentoShouldBeFound("fimPeriodo.in=" + DEFAULT_FIM_PERIODO + "," + UPDATED_FIM_PERIODO);

        // Get all the emolumentoList where fimPeriodo equals to UPDATED_FIM_PERIODO
        defaultEmolumentoShouldNotBeFound("fimPeriodo.in=" + UPDATED_FIM_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByFimPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where fimPeriodo is not null
        defaultEmolumentoShouldBeFound("fimPeriodo.specified=true");

        // Get all the emolumentoList where fimPeriodo is null
        defaultEmolumentoShouldNotBeFound("fimPeriodo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmolumentosByFimPeriodoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where fimPeriodo is greater than or equal to DEFAULT_FIM_PERIODO
        defaultEmolumentoShouldBeFound("fimPeriodo.greaterThanOrEqual=" + DEFAULT_FIM_PERIODO);

        // Get all the emolumentoList where fimPeriodo is greater than or equal to (DEFAULT_FIM_PERIODO + 1)
        defaultEmolumentoShouldNotBeFound("fimPeriodo.greaterThanOrEqual=" + (DEFAULT_FIM_PERIODO + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByFimPeriodoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where fimPeriodo is less than or equal to DEFAULT_FIM_PERIODO
        defaultEmolumentoShouldBeFound("fimPeriodo.lessThanOrEqual=" + DEFAULT_FIM_PERIODO);

        // Get all the emolumentoList where fimPeriodo is less than or equal to SMALLER_FIM_PERIODO
        defaultEmolumentoShouldNotBeFound("fimPeriodo.lessThanOrEqual=" + SMALLER_FIM_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByFimPeriodoIsLessThanSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where fimPeriodo is less than DEFAULT_FIM_PERIODO
        defaultEmolumentoShouldNotBeFound("fimPeriodo.lessThan=" + DEFAULT_FIM_PERIODO);

        // Get all the emolumentoList where fimPeriodo is less than (DEFAULT_FIM_PERIODO + 1)
        defaultEmolumentoShouldBeFound("fimPeriodo.lessThan=" + (DEFAULT_FIM_PERIODO + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByFimPeriodoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where fimPeriodo is greater than DEFAULT_FIM_PERIODO
        defaultEmolumentoShouldNotBeFound("fimPeriodo.greaterThan=" + DEFAULT_FIM_PERIODO);

        // Get all the emolumentoList where fimPeriodo is greater than SMALLER_FIM_PERIODO
        defaultEmolumentoShouldBeFound("fimPeriodo.greaterThan=" + SMALLER_FIM_PERIODO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByIsObrigatorioMatriculaIsEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where isObrigatorioMatricula equals to DEFAULT_IS_OBRIGATORIO_MATRICULA
        defaultEmolumentoShouldBeFound("isObrigatorioMatricula.equals=" + DEFAULT_IS_OBRIGATORIO_MATRICULA);

        // Get all the emolumentoList where isObrigatorioMatricula equals to UPDATED_IS_OBRIGATORIO_MATRICULA
        defaultEmolumentoShouldNotBeFound("isObrigatorioMatricula.equals=" + UPDATED_IS_OBRIGATORIO_MATRICULA);
    }

    @Test
    @Transactional
    void getAllEmolumentosByIsObrigatorioMatriculaIsInShouldWork() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where isObrigatorioMatricula in DEFAULT_IS_OBRIGATORIO_MATRICULA or UPDATED_IS_OBRIGATORIO_MATRICULA
        defaultEmolumentoShouldBeFound(
            "isObrigatorioMatricula.in=" + DEFAULT_IS_OBRIGATORIO_MATRICULA + "," + UPDATED_IS_OBRIGATORIO_MATRICULA
        );

        // Get all the emolumentoList where isObrigatorioMatricula equals to UPDATED_IS_OBRIGATORIO_MATRICULA
        defaultEmolumentoShouldNotBeFound("isObrigatorioMatricula.in=" + UPDATED_IS_OBRIGATORIO_MATRICULA);
    }

    @Test
    @Transactional
    void getAllEmolumentosByIsObrigatorioMatriculaIsNullOrNotNull() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where isObrigatorioMatricula is not null
        defaultEmolumentoShouldBeFound("isObrigatorioMatricula.specified=true");

        // Get all the emolumentoList where isObrigatorioMatricula is null
        defaultEmolumentoShouldNotBeFound("isObrigatorioMatricula.specified=false");
    }

    @Test
    @Transactional
    void getAllEmolumentosByIsObrigatorioConfirmacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where isObrigatorioConfirmacao equals to DEFAULT_IS_OBRIGATORIO_CONFIRMACAO
        defaultEmolumentoShouldBeFound("isObrigatorioConfirmacao.equals=" + DEFAULT_IS_OBRIGATORIO_CONFIRMACAO);

        // Get all the emolumentoList where isObrigatorioConfirmacao equals to UPDATED_IS_OBRIGATORIO_CONFIRMACAO
        defaultEmolumentoShouldNotBeFound("isObrigatorioConfirmacao.equals=" + UPDATED_IS_OBRIGATORIO_CONFIRMACAO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByIsObrigatorioConfirmacaoIsInShouldWork() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where isObrigatorioConfirmacao in DEFAULT_IS_OBRIGATORIO_CONFIRMACAO or UPDATED_IS_OBRIGATORIO_CONFIRMACAO
        defaultEmolumentoShouldBeFound(
            "isObrigatorioConfirmacao.in=" + DEFAULT_IS_OBRIGATORIO_CONFIRMACAO + "," + UPDATED_IS_OBRIGATORIO_CONFIRMACAO
        );

        // Get all the emolumentoList where isObrigatorioConfirmacao equals to UPDATED_IS_OBRIGATORIO_CONFIRMACAO
        defaultEmolumentoShouldNotBeFound("isObrigatorioConfirmacao.in=" + UPDATED_IS_OBRIGATORIO_CONFIRMACAO);
    }

    @Test
    @Transactional
    void getAllEmolumentosByIsObrigatorioConfirmacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        // Get all the emolumentoList where isObrigatorioConfirmacao is not null
        defaultEmolumentoShouldBeFound("isObrigatorioConfirmacao.specified=true");

        // Get all the emolumentoList where isObrigatorioConfirmacao is null
        defaultEmolumentoShouldNotBeFound("isObrigatorioConfirmacao.specified=false");
    }

    @Test
    @Transactional
    void getAllEmolumentosByItemFacturaIsEqualToSomething() throws Exception {
        ItemFactura itemFactura;
        if (TestUtil.findAll(em, ItemFactura.class).isEmpty()) {
            emolumentoRepository.saveAndFlush(emolumento);
            itemFactura = ItemFacturaResourceIT.createEntity(em);
        } else {
            itemFactura = TestUtil.findAll(em, ItemFactura.class).get(0);
        }
        em.persist(itemFactura);
        em.flush();
        emolumento.addItemFactura(itemFactura);
        emolumentoRepository.saveAndFlush(emolumento);
        Long itemFacturaId = itemFactura.getId();

        // Get all the emolumentoList where itemFactura equals to itemFacturaId
        defaultEmolumentoShouldBeFound("itemFacturaId.equals=" + itemFacturaId);

        // Get all the emolumentoList where itemFactura equals to (itemFacturaId + 1)
        defaultEmolumentoShouldNotBeFound("itemFacturaId.equals=" + (itemFacturaId + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByEmolumentoIsEqualToSomething() throws Exception {
        Emolumento emolumento;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            emolumentoRepository.saveAndFlush(emolumento);
            emolumento = EmolumentoResourceIT.createEntity(em);
        } else {
            emolumento = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        em.persist(emolumento);
        em.flush();
        emolumento.addEmolumento(emolumento);
        emolumentoRepository.saveAndFlush(emolumento);
        Long emolumentoId = emolumento.getId();

        // Get all the emolumentoList where emolumento equals to emolumentoId
        defaultEmolumentoShouldBeFound("emolumentoId.equals=" + emolumentoId);

        // Get all the emolumentoList where emolumento equals to (emolumentoId + 1)
        defaultEmolumentoShouldNotBeFound("emolumentoId.equals=" + (emolumentoId + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByPrecosEmolumentoIsEqualToSomething() throws Exception {
        PrecoEmolumento precosEmolumento;
        if (TestUtil.findAll(em, PrecoEmolumento.class).isEmpty()) {
            emolumentoRepository.saveAndFlush(emolumento);
            precosEmolumento = PrecoEmolumentoResourceIT.createEntity(em);
        } else {
            precosEmolumento = TestUtil.findAll(em, PrecoEmolumento.class).get(0);
        }
        em.persist(precosEmolumento);
        em.flush();
        emolumento.addPrecosEmolumento(precosEmolumento);
        emolumentoRepository.saveAndFlush(emolumento);
        Long precosEmolumentoId = precosEmolumento.getId();

        // Get all the emolumentoList where precosEmolumento equals to precosEmolumentoId
        defaultEmolumentoShouldBeFound("precosEmolumentoId.equals=" + precosEmolumentoId);

        // Get all the emolumentoList where precosEmolumento equals to (precosEmolumentoId + 1)
        defaultEmolumentoShouldNotBeFound("precosEmolumentoId.equals=" + (precosEmolumentoId + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByCategoriaIsEqualToSomething() throws Exception {
        CategoriaEmolumento categoria;
        if (TestUtil.findAll(em, CategoriaEmolumento.class).isEmpty()) {
            emolumentoRepository.saveAndFlush(emolumento);
            categoria = CategoriaEmolumentoResourceIT.createEntity(em);
        } else {
            categoria = TestUtil.findAll(em, CategoriaEmolumento.class).get(0);
        }
        em.persist(categoria);
        em.flush();
        emolumento.setCategoria(categoria);
        emolumentoRepository.saveAndFlush(emolumento);
        Long categoriaId = categoria.getId();

        // Get all the emolumentoList where categoria equals to categoriaId
        defaultEmolumentoShouldBeFound("categoriaId.equals=" + categoriaId);

        // Get all the emolumentoList where categoria equals to (categoriaId + 1)
        defaultEmolumentoShouldNotBeFound("categoriaId.equals=" + (categoriaId + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByImpostoIsEqualToSomething() throws Exception {
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            emolumentoRepository.saveAndFlush(emolumento);
            imposto = ImpostoResourceIT.createEntity(em);
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        em.persist(imposto);
        em.flush();
        emolumento.setImposto(imposto);
        emolumentoRepository.saveAndFlush(emolumento);
        Long impostoId = imposto.getId();

        // Get all the emolumentoList where imposto equals to impostoId
        defaultEmolumentoShouldBeFound("impostoId.equals=" + impostoId);

        // Get all the emolumentoList where imposto equals to (impostoId + 1)
        defaultEmolumentoShouldNotBeFound("impostoId.equals=" + (impostoId + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByReferenciaIsEqualToSomething() throws Exception {
        Emolumento referencia;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            emolumentoRepository.saveAndFlush(emolumento);
            referencia = EmolumentoResourceIT.createEntity(em);
        } else {
            referencia = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        em.persist(referencia);
        em.flush();
        emolumento.setReferencia(referencia);
        emolumentoRepository.saveAndFlush(emolumento);
        Long referenciaId = referencia.getId();

        // Get all the emolumentoList where referencia equals to referenciaId
        defaultEmolumentoShouldBeFound("referenciaId.equals=" + referenciaId);

        // Get all the emolumentoList where referencia equals to (referenciaId + 1)
        defaultEmolumentoShouldNotBeFound("referenciaId.equals=" + (referenciaId + 1));
    }

    @Test
    @Transactional
    void getAllEmolumentosByPlanoMultaIsEqualToSomething() throws Exception {
        PlanoMulta planoMulta;
        if (TestUtil.findAll(em, PlanoMulta.class).isEmpty()) {
            emolumentoRepository.saveAndFlush(emolumento);
            planoMulta = PlanoMultaResourceIT.createEntity(em);
        } else {
            planoMulta = TestUtil.findAll(em, PlanoMulta.class).get(0);
        }
        em.persist(planoMulta);
        em.flush();
        emolumento.setPlanoMulta(planoMulta);
        emolumentoRepository.saveAndFlush(emolumento);
        Long planoMultaId = planoMulta.getId();

        // Get all the emolumentoList where planoMulta equals to planoMultaId
        defaultEmolumentoShouldBeFound("planoMultaId.equals=" + planoMultaId);

        // Get all the emolumentoList where planoMulta equals to (planoMultaId + 1)
        defaultEmolumentoShouldNotBeFound("planoMultaId.equals=" + (planoMultaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmolumentoShouldBeFound(String filter) throws Exception {
        restEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emolumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(sameNumber(DEFAULT_PRECO))))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO)))
            .andExpect(jsonPath("$.[*].inicioPeriodo").value(hasItem(DEFAULT_INICIO_PERIODO)))
            .andExpect(jsonPath("$.[*].fimPeriodo").value(hasItem(DEFAULT_FIM_PERIODO)))
            .andExpect(jsonPath("$.[*].isObrigatorioMatricula").value(hasItem(DEFAULT_IS_OBRIGATORIO_MATRICULA.booleanValue())))
            .andExpect(jsonPath("$.[*].isObrigatorioConfirmacao").value(hasItem(DEFAULT_IS_OBRIGATORIO_CONFIRMACAO.booleanValue())));

        // Check, that the count call also returns 1
        restEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmolumentoShouldNotBeFound(String filter) throws Exception {
        restEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmolumento() throws Exception {
        // Get the emolumento
        restEmolumentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmolumento() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        int databaseSizeBeforeUpdate = emolumentoRepository.findAll().size();

        // Update the emolumento
        Emolumento updatedEmolumento = emolumentoRepository.findById(emolumento.getId()).get();
        // Disconnect from session so that the updates on updatedEmolumento are not directly saved in db
        em.detach(updatedEmolumento);
        updatedEmolumento
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .numero(UPDATED_NUMERO)
            .nome(UPDATED_NOME)
            .preco(UPDATED_PRECO)
            .quantidade(UPDATED_QUANTIDADE)
            .periodo(UPDATED_PERIODO)
            .inicioPeriodo(UPDATED_INICIO_PERIODO)
            .fimPeriodo(UPDATED_FIM_PERIODO)
            .isObrigatorioMatricula(UPDATED_IS_OBRIGATORIO_MATRICULA)
            .isObrigatorioConfirmacao(UPDATED_IS_OBRIGATORIO_CONFIRMACAO);
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(updatedEmolumento);

        restEmolumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emolumentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emolumentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Emolumento in the database
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeUpdate);
        Emolumento testEmolumento = emolumentoList.get(emolumentoList.size() - 1);
        assertThat(testEmolumento.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testEmolumento.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testEmolumento.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEmolumento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEmolumento.getPreco()).isEqualByComparingTo(UPDATED_PRECO);
        assertThat(testEmolumento.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testEmolumento.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testEmolumento.getInicioPeriodo()).isEqualTo(UPDATED_INICIO_PERIODO);
        assertThat(testEmolumento.getFimPeriodo()).isEqualTo(UPDATED_FIM_PERIODO);
        assertThat(testEmolumento.getIsObrigatorioMatricula()).isEqualTo(UPDATED_IS_OBRIGATORIO_MATRICULA);
        assertThat(testEmolumento.getIsObrigatorioConfirmacao()).isEqualTo(UPDATED_IS_OBRIGATORIO_CONFIRMACAO);
    }

    @Test
    @Transactional
    void putNonExistingEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = emolumentoRepository.findAll().size();
        emolumento.setId(count.incrementAndGet());

        // Create the Emolumento
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmolumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emolumentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emolumento in the database
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = emolumentoRepository.findAll().size();
        emolumento.setId(count.incrementAndGet());

        // Create the Emolumento
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmolumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emolumento in the database
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = emolumentoRepository.findAll().size();
        emolumento.setId(count.incrementAndGet());

        // Create the Emolumento
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmolumentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emolumentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emolumento in the database
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmolumentoWithPatch() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        int databaseSizeBeforeUpdate = emolumentoRepository.findAll().size();

        // Update the emolumento using partial update
        Emolumento partialUpdatedEmolumento = new Emolumento();
        partialUpdatedEmolumento.setId(emolumento.getId());

        partialUpdatedEmolumento
            .numero(UPDATED_NUMERO)
            .periodo(UPDATED_PERIODO)
            .inicioPeriodo(UPDATED_INICIO_PERIODO)
            .fimPeriodo(UPDATED_FIM_PERIODO)
            .isObrigatorioMatricula(UPDATED_IS_OBRIGATORIO_MATRICULA);

        restEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmolumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmolumento))
            )
            .andExpect(status().isOk());

        // Validate the Emolumento in the database
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeUpdate);
        Emolumento testEmolumento = emolumentoList.get(emolumentoList.size() - 1);
        assertThat(testEmolumento.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testEmolumento.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testEmolumento.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEmolumento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEmolumento.getPreco()).isEqualByComparingTo(DEFAULT_PRECO);
        assertThat(testEmolumento.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testEmolumento.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testEmolumento.getInicioPeriodo()).isEqualTo(UPDATED_INICIO_PERIODO);
        assertThat(testEmolumento.getFimPeriodo()).isEqualTo(UPDATED_FIM_PERIODO);
        assertThat(testEmolumento.getIsObrigatorioMatricula()).isEqualTo(UPDATED_IS_OBRIGATORIO_MATRICULA);
        assertThat(testEmolumento.getIsObrigatorioConfirmacao()).isEqualTo(DEFAULT_IS_OBRIGATORIO_CONFIRMACAO);
    }

    @Test
    @Transactional
    void fullUpdateEmolumentoWithPatch() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        int databaseSizeBeforeUpdate = emolumentoRepository.findAll().size();

        // Update the emolumento using partial update
        Emolumento partialUpdatedEmolumento = new Emolumento();
        partialUpdatedEmolumento.setId(emolumento.getId());

        partialUpdatedEmolumento
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .numero(UPDATED_NUMERO)
            .nome(UPDATED_NOME)
            .preco(UPDATED_PRECO)
            .quantidade(UPDATED_QUANTIDADE)
            .periodo(UPDATED_PERIODO)
            .inicioPeriodo(UPDATED_INICIO_PERIODO)
            .fimPeriodo(UPDATED_FIM_PERIODO)
            .isObrigatorioMatricula(UPDATED_IS_OBRIGATORIO_MATRICULA)
            .isObrigatorioConfirmacao(UPDATED_IS_OBRIGATORIO_CONFIRMACAO);

        restEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmolumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmolumento))
            )
            .andExpect(status().isOk());

        // Validate the Emolumento in the database
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeUpdate);
        Emolumento testEmolumento = emolumentoList.get(emolumentoList.size() - 1);
        assertThat(testEmolumento.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testEmolumento.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testEmolumento.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEmolumento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEmolumento.getPreco()).isEqualByComparingTo(UPDATED_PRECO);
        assertThat(testEmolumento.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testEmolumento.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testEmolumento.getInicioPeriodo()).isEqualTo(UPDATED_INICIO_PERIODO);
        assertThat(testEmolumento.getFimPeriodo()).isEqualTo(UPDATED_FIM_PERIODO);
        assertThat(testEmolumento.getIsObrigatorioMatricula()).isEqualTo(UPDATED_IS_OBRIGATORIO_MATRICULA);
        assertThat(testEmolumento.getIsObrigatorioConfirmacao()).isEqualTo(UPDATED_IS_OBRIGATORIO_CONFIRMACAO);
    }

    @Test
    @Transactional
    void patchNonExistingEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = emolumentoRepository.findAll().size();
        emolumento.setId(count.incrementAndGet());

        // Create the Emolumento
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emolumentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emolumento in the database
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = emolumentoRepository.findAll().size();
        emolumento.setId(count.incrementAndGet());

        // Create the Emolumento
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emolumento in the database
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = emolumentoRepository.findAll().size();
        emolumento.setId(count.incrementAndGet());

        // Create the Emolumento
        EmolumentoDTO emolumentoDTO = emolumentoMapper.toDto(emolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(emolumentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emolumento in the database
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmolumento() throws Exception {
        // Initialize the database
        emolumentoRepository.saveAndFlush(emolumento);

        int databaseSizeBeforeDelete = emolumentoRepository.findAll().size();

        // Delete the emolumento
        restEmolumentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, emolumento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emolumento> emolumentoList = emolumentoRepository.findAll();
        assertThat(emolumentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
