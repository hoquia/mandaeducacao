package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static com.ravunana.longonkelo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.AplicacaoRecibo;
import com.ravunana.longonkelo.domain.DocumentoComercial;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.Recibo;
import com.ravunana.longonkelo.domain.Transacao;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.domain.enumeration.EstadoDocumentoComercial;
import com.ravunana.longonkelo.repository.ReciboRepository;
import com.ravunana.longonkelo.service.ReciboService;
import com.ravunana.longonkelo.service.criteria.ReciboCriteria;
import com.ravunana.longonkelo.service.dto.ReciboDTO;
import com.ravunana.longonkelo.service.mapper.ReciboMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ReciboResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ReciboResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VENCIMENTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_SEM_IMPOSTO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_SEM_IMPOSTO = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_SEM_IMPOSTO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_COM_IMPOSTO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_COM_IMPOSTO = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_COM_IMPOSTO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_DESCONTO_COMERCIAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_DESCONTO_COMERCIAL = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_DESCONTO_COMERCIAL = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_DESCONTO_FINANCEIRO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_DESCONTO_FINANCEIRO = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_DESCONTO_FINANCEIRO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_IVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_IVA = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_IVA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_RETENCAO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_RETENCAO = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_RETENCAO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_JURO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_JURO = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_JURO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CAMBIO = new BigDecimal(0);
    private static final BigDecimal UPDATED_CAMBIO = new BigDecimal(1);
    private static final BigDecimal SMALLER_CAMBIO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_MOEDA_ESTRANGEIRA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_MOEDA_ESTRANGEIRA = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_MOEDA_ESTRANGEIRA = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PAGAR = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_PAGAR = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_PAGAR = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PAGO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_PAGO = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_PAGO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_FALTA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_FALTA = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_FALTA = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_TROCO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_TROCO = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_TROCO = new BigDecimal(0 - 1);

    private static final Boolean DEFAULT_IS_NOVO = false;
    private static final Boolean UPDATED_IS_NOVO = true;

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_DEBITO = new BigDecimal(0);
    private static final BigDecimal UPDATED_DEBITO = new BigDecimal(1);
    private static final BigDecimal SMALLER_DEBITO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_CREDITO = new BigDecimal(0);
    private static final BigDecimal UPDATED_CREDITO = new BigDecimal(1);
    private static final BigDecimal SMALLER_CREDITO = new BigDecimal(0 - 1);

    private static final Boolean DEFAULT_IS_FISCALIZADO = false;
    private static final Boolean UPDATED_IS_FISCALIZADO = true;

    private static final String DEFAULT_SIGN_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SIGN_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_SHORT = "AAAAAAAAAA";
    private static final String UPDATED_HASH_SHORT = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_CONTROL = "AAAAAAAAAA";
    private static final String UPDATED_HASH_CONTROL = "BBBBBBBBBB";

    private static final Integer DEFAULT_KEY_VERSION = 1;
    private static final Integer UPDATED_KEY_VERSION = 2;
    private static final Integer SMALLER_KEY_VERSION = 1 - 1;

    private static final EstadoDocumentoComercial DEFAULT_ESTADO = EstadoDocumentoComercial.P;
    private static final EstadoDocumentoComercial UPDATED_ESTADO = EstadoDocumentoComercial.N;

    private static final String DEFAULT_ORIGEM = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/recibos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReciboRepository reciboRepository;

    @Mock
    private ReciboRepository reciboRepositoryMock;

    @Autowired
    private ReciboMapper reciboMapper;

    @Mock
    private ReciboService reciboServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReciboMockMvc;

    private Recibo recibo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recibo createEntity(EntityManager em) {
        Recibo recibo = new Recibo()
            .data(DEFAULT_DATA)
            .vencimento(DEFAULT_VENCIMENTO)
            .numero(DEFAULT_NUMERO)
            .totalSemImposto(DEFAULT_TOTAL_SEM_IMPOSTO)
            .totalComImposto(DEFAULT_TOTAL_COM_IMPOSTO)
            .totalDescontoComercial(DEFAULT_TOTAL_DESCONTO_COMERCIAL)
            .totalDescontoFinanceiro(DEFAULT_TOTAL_DESCONTO_FINANCEIRO)
            .totalIVA(DEFAULT_TOTAL_IVA)
            .totalRetencao(DEFAULT_TOTAL_RETENCAO)
            .totalJuro(DEFAULT_TOTAL_JURO)
            .cambio(DEFAULT_CAMBIO)
            .totalMoedaEstrangeira(DEFAULT_TOTAL_MOEDA_ESTRANGEIRA)
            .totalPagar(DEFAULT_TOTAL_PAGAR)
            .totalPago(DEFAULT_TOTAL_PAGO)
            .totalFalta(DEFAULT_TOTAL_FALTA)
            .totalTroco(DEFAULT_TOTAL_TROCO)
            .isNovo(DEFAULT_IS_NOVO)
            .timestamp(DEFAULT_TIMESTAMP)
            .descricao(DEFAULT_DESCRICAO)
            .debito(DEFAULT_DEBITO)
            .credito(DEFAULT_CREDITO)
            .isFiscalizado(DEFAULT_IS_FISCALIZADO)
            .signText(DEFAULT_SIGN_TEXT)
            .hash(DEFAULT_HASH)
            .hashShort(DEFAULT_HASH_SHORT)
            .hashControl(DEFAULT_HASH_CONTROL)
            .keyVersion(DEFAULT_KEY_VERSION)
            .estado(DEFAULT_ESTADO)
            .origem(DEFAULT_ORIGEM);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        recibo.setMatricula(matricula);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        recibo.setDocumentoComercial(documentoComercial);
        // Add required entity
        Transacao transacao;
        if (TestUtil.findAll(em, Transacao.class).isEmpty()) {
            transacao = TransacaoResourceIT.createEntity(em);
            em.persist(transacao);
            em.flush();
        } else {
            transacao = TestUtil.findAll(em, Transacao.class).get(0);
        }
        recibo.setTransacao(transacao);
        return recibo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recibo createUpdatedEntity(EntityManager em) {
        Recibo recibo = new Recibo()
            .data(UPDATED_DATA)
            .vencimento(UPDATED_VENCIMENTO)
            .numero(UPDATED_NUMERO)
            .totalSemImposto(UPDATED_TOTAL_SEM_IMPOSTO)
            .totalComImposto(UPDATED_TOTAL_COM_IMPOSTO)
            .totalDescontoComercial(UPDATED_TOTAL_DESCONTO_COMERCIAL)
            .totalDescontoFinanceiro(UPDATED_TOTAL_DESCONTO_FINANCEIRO)
            .totalIVA(UPDATED_TOTAL_IVA)
            .totalRetencao(UPDATED_TOTAL_RETENCAO)
            .totalJuro(UPDATED_TOTAL_JURO)
            .cambio(UPDATED_CAMBIO)
            .totalMoedaEstrangeira(UPDATED_TOTAL_MOEDA_ESTRANGEIRA)
            .totalPagar(UPDATED_TOTAL_PAGAR)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalFalta(UPDATED_TOTAL_FALTA)
            .totalTroco(UPDATED_TOTAL_TROCO)
            .isNovo(UPDATED_IS_NOVO)
            .timestamp(UPDATED_TIMESTAMP)
            .descricao(UPDATED_DESCRICAO)
            .debito(UPDATED_DEBITO)
            .credito(UPDATED_CREDITO)
            .isFiscalizado(UPDATED_IS_FISCALIZADO)
            .signText(UPDATED_SIGN_TEXT)
            .hash(UPDATED_HASH)
            .hashShort(UPDATED_HASH_SHORT)
            .hashControl(UPDATED_HASH_CONTROL)
            .keyVersion(UPDATED_KEY_VERSION)
            .estado(UPDATED_ESTADO)
            .origem(UPDATED_ORIGEM);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createUpdatedEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        recibo.setMatricula(matricula);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createUpdatedEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        recibo.setDocumentoComercial(documentoComercial);
        // Add required entity
        Transacao transacao;
        if (TestUtil.findAll(em, Transacao.class).isEmpty()) {
            transacao = TransacaoResourceIT.createUpdatedEntity(em);
            em.persist(transacao);
            em.flush();
        } else {
            transacao = TestUtil.findAll(em, Transacao.class).get(0);
        }
        recibo.setTransacao(transacao);
        return recibo;
    }

    @BeforeEach
    public void initTest() {
        recibo = createEntity(em);
    }

    @Test
    @Transactional
    void createRecibo() throws Exception {
        int databaseSizeBeforeCreate = reciboRepository.findAll().size();
        // Create the Recibo
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);
        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isCreated());

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeCreate + 1);
        Recibo testRecibo = reciboList.get(reciboList.size() - 1);
        assertThat(testRecibo.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testRecibo.getVencimento()).isEqualTo(DEFAULT_VENCIMENTO);
        assertThat(testRecibo.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testRecibo.getTotalSemImposto()).isEqualByComparingTo(DEFAULT_TOTAL_SEM_IMPOSTO);
        assertThat(testRecibo.getTotalComImposto()).isEqualByComparingTo(DEFAULT_TOTAL_COM_IMPOSTO);
        assertThat(testRecibo.getTotalDescontoComercial()).isEqualByComparingTo(DEFAULT_TOTAL_DESCONTO_COMERCIAL);
        assertThat(testRecibo.getTotalDescontoFinanceiro()).isEqualByComparingTo(DEFAULT_TOTAL_DESCONTO_FINANCEIRO);
        assertThat(testRecibo.getTotalIVA()).isEqualByComparingTo(DEFAULT_TOTAL_IVA);
        assertThat(testRecibo.getTotalRetencao()).isEqualByComparingTo(DEFAULT_TOTAL_RETENCAO);
        assertThat(testRecibo.getTotalJuro()).isEqualByComparingTo(DEFAULT_TOTAL_JURO);
        assertThat(testRecibo.getCambio()).isEqualByComparingTo(DEFAULT_CAMBIO);
        assertThat(testRecibo.getTotalMoedaEstrangeira()).isEqualByComparingTo(DEFAULT_TOTAL_MOEDA_ESTRANGEIRA);
        assertThat(testRecibo.getTotalPagar()).isEqualByComparingTo(DEFAULT_TOTAL_PAGAR);
        assertThat(testRecibo.getTotalPago()).isEqualByComparingTo(DEFAULT_TOTAL_PAGO);
        assertThat(testRecibo.getTotalFalta()).isEqualByComparingTo(DEFAULT_TOTAL_FALTA);
        assertThat(testRecibo.getTotalTroco()).isEqualByComparingTo(DEFAULT_TOTAL_TROCO);
        assertThat(testRecibo.getIsNovo()).isEqualTo(DEFAULT_IS_NOVO);
        assertThat(testRecibo.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testRecibo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testRecibo.getDebito()).isEqualByComparingTo(DEFAULT_DEBITO);
        assertThat(testRecibo.getCredito()).isEqualByComparingTo(DEFAULT_CREDITO);
        assertThat(testRecibo.getIsFiscalizado()).isEqualTo(DEFAULT_IS_FISCALIZADO);
        assertThat(testRecibo.getSignText()).isEqualTo(DEFAULT_SIGN_TEXT);
        assertThat(testRecibo.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testRecibo.getHashShort()).isEqualTo(DEFAULT_HASH_SHORT);
        assertThat(testRecibo.getHashControl()).isEqualTo(DEFAULT_HASH_CONTROL);
        assertThat(testRecibo.getKeyVersion()).isEqualTo(DEFAULT_KEY_VERSION);
        assertThat(testRecibo.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testRecibo.getOrigem()).isEqualTo(DEFAULT_ORIGEM);
    }

    @Test
    @Transactional
    void createReciboWithExistingId() throws Exception {
        // Create the Recibo with an existing ID
        recibo.setId(1L);
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        int databaseSizeBeforeCreate = reciboRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setData(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setNumero(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalSemImpostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalSemImposto(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalComImpostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalComImposto(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDescontoComercialIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalDescontoComercial(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDescontoFinanceiroIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalDescontoFinanceiro(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalIVAIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalIVA(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalRetencaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalRetencao(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalJuroIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalJuro(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCambioIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setCambio(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalMoedaEstrangeiraIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalMoedaEstrangeira(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalPagarIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalPagar(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalPago(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalFaltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalFalta(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalTrocoIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTotalTroco(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setTimestamp(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setEstado(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrigemIsRequired() throws Exception {
        int databaseSizeBeforeTest = reciboRepository.findAll().size();
        // set the field null
        recibo.setOrigem(null);

        // Create the Recibo, which fails.
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        restReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isBadRequest());

        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecibos() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList
        restReciboMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recibo.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].vencimento").value(hasItem(DEFAULT_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].totalSemImposto").value(hasItem(sameNumber(DEFAULT_TOTAL_SEM_IMPOSTO))))
            .andExpect(jsonPath("$.[*].totalComImposto").value(hasItem(sameNumber(DEFAULT_TOTAL_COM_IMPOSTO))))
            .andExpect(jsonPath("$.[*].totalDescontoComercial").value(hasItem(sameNumber(DEFAULT_TOTAL_DESCONTO_COMERCIAL))))
            .andExpect(jsonPath("$.[*].totalDescontoFinanceiro").value(hasItem(sameNumber(DEFAULT_TOTAL_DESCONTO_FINANCEIRO))))
            .andExpect(jsonPath("$.[*].totalIVA").value(hasItem(sameNumber(DEFAULT_TOTAL_IVA))))
            .andExpect(jsonPath("$.[*].totalRetencao").value(hasItem(sameNumber(DEFAULT_TOTAL_RETENCAO))))
            .andExpect(jsonPath("$.[*].totalJuro").value(hasItem(sameNumber(DEFAULT_TOTAL_JURO))))
            .andExpect(jsonPath("$.[*].cambio").value(hasItem(sameNumber(DEFAULT_CAMBIO))))
            .andExpect(jsonPath("$.[*].totalMoedaEstrangeira").value(hasItem(sameNumber(DEFAULT_TOTAL_MOEDA_ESTRANGEIRA))))
            .andExpect(jsonPath("$.[*].totalPagar").value(hasItem(sameNumber(DEFAULT_TOTAL_PAGAR))))
            .andExpect(jsonPath("$.[*].totalPago").value(hasItem(sameNumber(DEFAULT_TOTAL_PAGO))))
            .andExpect(jsonPath("$.[*].totalFalta").value(hasItem(sameNumber(DEFAULT_TOTAL_FALTA))))
            .andExpect(jsonPath("$.[*].totalTroco").value(hasItem(sameNumber(DEFAULT_TOTAL_TROCO))))
            .andExpect(jsonPath("$.[*].isNovo").value(hasItem(DEFAULT_IS_NOVO.booleanValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].debito").value(hasItem(sameNumber(DEFAULT_DEBITO))))
            .andExpect(jsonPath("$.[*].credito").value(hasItem(sameNumber(DEFAULT_CREDITO))))
            .andExpect(jsonPath("$.[*].isFiscalizado").value(hasItem(DEFAULT_IS_FISCALIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].signText").value(hasItem(DEFAULT_SIGN_TEXT)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].hashShort").value(hasItem(DEFAULT_HASH_SHORT)))
            .andExpect(jsonPath("$.[*].hashControl").value(hasItem(DEFAULT_HASH_CONTROL)))
            .andExpect(jsonPath("$.[*].keyVersion").value(hasItem(DEFAULT_KEY_VERSION)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecibosWithEagerRelationshipsIsEnabled() throws Exception {
        when(reciboServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReciboMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(reciboServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecibosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(reciboServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReciboMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(reciboRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRecibo() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get the recibo
        restReciboMockMvc
            .perform(get(ENTITY_API_URL_ID, recibo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recibo.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.vencimento").value(DEFAULT_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.totalSemImposto").value(sameNumber(DEFAULT_TOTAL_SEM_IMPOSTO)))
            .andExpect(jsonPath("$.totalComImposto").value(sameNumber(DEFAULT_TOTAL_COM_IMPOSTO)))
            .andExpect(jsonPath("$.totalDescontoComercial").value(sameNumber(DEFAULT_TOTAL_DESCONTO_COMERCIAL)))
            .andExpect(jsonPath("$.totalDescontoFinanceiro").value(sameNumber(DEFAULT_TOTAL_DESCONTO_FINANCEIRO)))
            .andExpect(jsonPath("$.totalIVA").value(sameNumber(DEFAULT_TOTAL_IVA)))
            .andExpect(jsonPath("$.totalRetencao").value(sameNumber(DEFAULT_TOTAL_RETENCAO)))
            .andExpect(jsonPath("$.totalJuro").value(sameNumber(DEFAULT_TOTAL_JURO)))
            .andExpect(jsonPath("$.cambio").value(sameNumber(DEFAULT_CAMBIO)))
            .andExpect(jsonPath("$.totalMoedaEstrangeira").value(sameNumber(DEFAULT_TOTAL_MOEDA_ESTRANGEIRA)))
            .andExpect(jsonPath("$.totalPagar").value(sameNumber(DEFAULT_TOTAL_PAGAR)))
            .andExpect(jsonPath("$.totalPago").value(sameNumber(DEFAULT_TOTAL_PAGO)))
            .andExpect(jsonPath("$.totalFalta").value(sameNumber(DEFAULT_TOTAL_FALTA)))
            .andExpect(jsonPath("$.totalTroco").value(sameNumber(DEFAULT_TOTAL_TROCO)))
            .andExpect(jsonPath("$.isNovo").value(DEFAULT_IS_NOVO.booleanValue()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.debito").value(sameNumber(DEFAULT_DEBITO)))
            .andExpect(jsonPath("$.credito").value(sameNumber(DEFAULT_CREDITO)))
            .andExpect(jsonPath("$.isFiscalizado").value(DEFAULT_IS_FISCALIZADO.booleanValue()))
            .andExpect(jsonPath("$.signText").value(DEFAULT_SIGN_TEXT))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.hashShort").value(DEFAULT_HASH_SHORT))
            .andExpect(jsonPath("$.hashControl").value(DEFAULT_HASH_CONTROL))
            .andExpect(jsonPath("$.keyVersion").value(DEFAULT_KEY_VERSION))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.origem").value(DEFAULT_ORIGEM));
    }

    @Test
    @Transactional
    void getRecibosByIdFiltering() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        Long id = recibo.getId();

        defaultReciboShouldBeFound("id.equals=" + id);
        defaultReciboShouldNotBeFound("id.notEquals=" + id);

        defaultReciboShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReciboShouldNotBeFound("id.greaterThan=" + id);

        defaultReciboShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReciboShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRecibosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where data equals to DEFAULT_DATA
        defaultReciboShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the reciboList where data equals to UPDATED_DATA
        defaultReciboShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllRecibosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where data in DEFAULT_DATA or UPDATED_DATA
        defaultReciboShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the reciboList where data equals to UPDATED_DATA
        defaultReciboShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllRecibosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where data is not null
        defaultReciboShouldBeFound("data.specified=true");

        // Get all the reciboList where data is null
        defaultReciboShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where data is greater than or equal to DEFAULT_DATA
        defaultReciboShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the reciboList where data is greater than or equal to UPDATED_DATA
        defaultReciboShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllRecibosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where data is less than or equal to DEFAULT_DATA
        defaultReciboShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the reciboList where data is less than or equal to SMALLER_DATA
        defaultReciboShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllRecibosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where data is less than DEFAULT_DATA
        defaultReciboShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the reciboList where data is less than UPDATED_DATA
        defaultReciboShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    void getAllRecibosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where data is greater than DEFAULT_DATA
        defaultReciboShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the reciboList where data is greater than SMALLER_DATA
        defaultReciboShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    void getAllRecibosByVencimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where vencimento equals to DEFAULT_VENCIMENTO
        defaultReciboShouldBeFound("vencimento.equals=" + DEFAULT_VENCIMENTO);

        // Get all the reciboList where vencimento equals to UPDATED_VENCIMENTO
        defaultReciboShouldNotBeFound("vencimento.equals=" + UPDATED_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllRecibosByVencimentoIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where vencimento in DEFAULT_VENCIMENTO or UPDATED_VENCIMENTO
        defaultReciboShouldBeFound("vencimento.in=" + DEFAULT_VENCIMENTO + "," + UPDATED_VENCIMENTO);

        // Get all the reciboList where vencimento equals to UPDATED_VENCIMENTO
        defaultReciboShouldNotBeFound("vencimento.in=" + UPDATED_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllRecibosByVencimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where vencimento is not null
        defaultReciboShouldBeFound("vencimento.specified=true");

        // Get all the reciboList where vencimento is null
        defaultReciboShouldNotBeFound("vencimento.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByVencimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where vencimento is greater than or equal to DEFAULT_VENCIMENTO
        defaultReciboShouldBeFound("vencimento.greaterThanOrEqual=" + DEFAULT_VENCIMENTO);

        // Get all the reciboList where vencimento is greater than or equal to UPDATED_VENCIMENTO
        defaultReciboShouldNotBeFound("vencimento.greaterThanOrEqual=" + UPDATED_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllRecibosByVencimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where vencimento is less than or equal to DEFAULT_VENCIMENTO
        defaultReciboShouldBeFound("vencimento.lessThanOrEqual=" + DEFAULT_VENCIMENTO);

        // Get all the reciboList where vencimento is less than or equal to SMALLER_VENCIMENTO
        defaultReciboShouldNotBeFound("vencimento.lessThanOrEqual=" + SMALLER_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllRecibosByVencimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where vencimento is less than DEFAULT_VENCIMENTO
        defaultReciboShouldNotBeFound("vencimento.lessThan=" + DEFAULT_VENCIMENTO);

        // Get all the reciboList where vencimento is less than UPDATED_VENCIMENTO
        defaultReciboShouldBeFound("vencimento.lessThan=" + UPDATED_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllRecibosByVencimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where vencimento is greater than DEFAULT_VENCIMENTO
        defaultReciboShouldNotBeFound("vencimento.greaterThan=" + DEFAULT_VENCIMENTO);

        // Get all the reciboList where vencimento is greater than SMALLER_VENCIMENTO
        defaultReciboShouldBeFound("vencimento.greaterThan=" + SMALLER_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllRecibosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where numero equals to DEFAULT_NUMERO
        defaultReciboShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the reciboList where numero equals to UPDATED_NUMERO
        defaultReciboShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllRecibosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultReciboShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the reciboList where numero equals to UPDATED_NUMERO
        defaultReciboShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllRecibosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where numero is not null
        defaultReciboShouldBeFound("numero.specified=true");

        // Get all the reciboList where numero is null
        defaultReciboShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByNumeroContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where numero contains DEFAULT_NUMERO
        defaultReciboShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the reciboList where numero contains UPDATED_NUMERO
        defaultReciboShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllRecibosByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where numero does not contain DEFAULT_NUMERO
        defaultReciboShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the reciboList where numero does not contain UPDATED_NUMERO
        defaultReciboShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalSemImpostoIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalSemImposto equals to DEFAULT_TOTAL_SEM_IMPOSTO
        defaultReciboShouldBeFound("totalSemImposto.equals=" + DEFAULT_TOTAL_SEM_IMPOSTO);

        // Get all the reciboList where totalSemImposto equals to UPDATED_TOTAL_SEM_IMPOSTO
        defaultReciboShouldNotBeFound("totalSemImposto.equals=" + UPDATED_TOTAL_SEM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalSemImpostoIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalSemImposto in DEFAULT_TOTAL_SEM_IMPOSTO or UPDATED_TOTAL_SEM_IMPOSTO
        defaultReciboShouldBeFound("totalSemImposto.in=" + DEFAULT_TOTAL_SEM_IMPOSTO + "," + UPDATED_TOTAL_SEM_IMPOSTO);

        // Get all the reciboList where totalSemImposto equals to UPDATED_TOTAL_SEM_IMPOSTO
        defaultReciboShouldNotBeFound("totalSemImposto.in=" + UPDATED_TOTAL_SEM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalSemImpostoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalSemImposto is not null
        defaultReciboShouldBeFound("totalSemImposto.specified=true");

        // Get all the reciboList where totalSemImposto is null
        defaultReciboShouldNotBeFound("totalSemImposto.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalSemImpostoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalSemImposto is greater than or equal to DEFAULT_TOTAL_SEM_IMPOSTO
        defaultReciboShouldBeFound("totalSemImposto.greaterThanOrEqual=" + DEFAULT_TOTAL_SEM_IMPOSTO);

        // Get all the reciboList where totalSemImposto is greater than or equal to UPDATED_TOTAL_SEM_IMPOSTO
        defaultReciboShouldNotBeFound("totalSemImposto.greaterThanOrEqual=" + UPDATED_TOTAL_SEM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalSemImpostoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalSemImposto is less than or equal to DEFAULT_TOTAL_SEM_IMPOSTO
        defaultReciboShouldBeFound("totalSemImposto.lessThanOrEqual=" + DEFAULT_TOTAL_SEM_IMPOSTO);

        // Get all the reciboList where totalSemImposto is less than or equal to SMALLER_TOTAL_SEM_IMPOSTO
        defaultReciboShouldNotBeFound("totalSemImposto.lessThanOrEqual=" + SMALLER_TOTAL_SEM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalSemImpostoIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalSemImposto is less than DEFAULT_TOTAL_SEM_IMPOSTO
        defaultReciboShouldNotBeFound("totalSemImposto.lessThan=" + DEFAULT_TOTAL_SEM_IMPOSTO);

        // Get all the reciboList where totalSemImposto is less than UPDATED_TOTAL_SEM_IMPOSTO
        defaultReciboShouldBeFound("totalSemImposto.lessThan=" + UPDATED_TOTAL_SEM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalSemImpostoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalSemImposto is greater than DEFAULT_TOTAL_SEM_IMPOSTO
        defaultReciboShouldNotBeFound("totalSemImposto.greaterThan=" + DEFAULT_TOTAL_SEM_IMPOSTO);

        // Get all the reciboList where totalSemImposto is greater than SMALLER_TOTAL_SEM_IMPOSTO
        defaultReciboShouldBeFound("totalSemImposto.greaterThan=" + SMALLER_TOTAL_SEM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalComImpostoIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalComImposto equals to DEFAULT_TOTAL_COM_IMPOSTO
        defaultReciboShouldBeFound("totalComImposto.equals=" + DEFAULT_TOTAL_COM_IMPOSTO);

        // Get all the reciboList where totalComImposto equals to UPDATED_TOTAL_COM_IMPOSTO
        defaultReciboShouldNotBeFound("totalComImposto.equals=" + UPDATED_TOTAL_COM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalComImpostoIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalComImposto in DEFAULT_TOTAL_COM_IMPOSTO or UPDATED_TOTAL_COM_IMPOSTO
        defaultReciboShouldBeFound("totalComImposto.in=" + DEFAULT_TOTAL_COM_IMPOSTO + "," + UPDATED_TOTAL_COM_IMPOSTO);

        // Get all the reciboList where totalComImposto equals to UPDATED_TOTAL_COM_IMPOSTO
        defaultReciboShouldNotBeFound("totalComImposto.in=" + UPDATED_TOTAL_COM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalComImpostoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalComImposto is not null
        defaultReciboShouldBeFound("totalComImposto.specified=true");

        // Get all the reciboList where totalComImposto is null
        defaultReciboShouldNotBeFound("totalComImposto.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalComImpostoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalComImposto is greater than or equal to DEFAULT_TOTAL_COM_IMPOSTO
        defaultReciboShouldBeFound("totalComImposto.greaterThanOrEqual=" + DEFAULT_TOTAL_COM_IMPOSTO);

        // Get all the reciboList where totalComImposto is greater than or equal to UPDATED_TOTAL_COM_IMPOSTO
        defaultReciboShouldNotBeFound("totalComImposto.greaterThanOrEqual=" + UPDATED_TOTAL_COM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalComImpostoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalComImposto is less than or equal to DEFAULT_TOTAL_COM_IMPOSTO
        defaultReciboShouldBeFound("totalComImposto.lessThanOrEqual=" + DEFAULT_TOTAL_COM_IMPOSTO);

        // Get all the reciboList where totalComImposto is less than or equal to SMALLER_TOTAL_COM_IMPOSTO
        defaultReciboShouldNotBeFound("totalComImposto.lessThanOrEqual=" + SMALLER_TOTAL_COM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalComImpostoIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalComImposto is less than DEFAULT_TOTAL_COM_IMPOSTO
        defaultReciboShouldNotBeFound("totalComImposto.lessThan=" + DEFAULT_TOTAL_COM_IMPOSTO);

        // Get all the reciboList where totalComImposto is less than UPDATED_TOTAL_COM_IMPOSTO
        defaultReciboShouldBeFound("totalComImposto.lessThan=" + UPDATED_TOTAL_COM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalComImpostoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalComImposto is greater than DEFAULT_TOTAL_COM_IMPOSTO
        defaultReciboShouldNotBeFound("totalComImposto.greaterThan=" + DEFAULT_TOTAL_COM_IMPOSTO);

        // Get all the reciboList where totalComImposto is greater than SMALLER_TOTAL_COM_IMPOSTO
        defaultReciboShouldBeFound("totalComImposto.greaterThan=" + SMALLER_TOTAL_COM_IMPOSTO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoComercialIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoComercial equals to DEFAULT_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldBeFound("totalDescontoComercial.equals=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL);

        // Get all the reciboList where totalDescontoComercial equals to UPDATED_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldNotBeFound("totalDescontoComercial.equals=" + UPDATED_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoComercialIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoComercial in DEFAULT_TOTAL_DESCONTO_COMERCIAL or UPDATED_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldBeFound(
            "totalDescontoComercial.in=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL + "," + UPDATED_TOTAL_DESCONTO_COMERCIAL
        );

        // Get all the reciboList where totalDescontoComercial equals to UPDATED_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldNotBeFound("totalDescontoComercial.in=" + UPDATED_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoComercialIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoComercial is not null
        defaultReciboShouldBeFound("totalDescontoComercial.specified=true");

        // Get all the reciboList where totalDescontoComercial is null
        defaultReciboShouldNotBeFound("totalDescontoComercial.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoComercialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoComercial is greater than or equal to DEFAULT_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldBeFound("totalDescontoComercial.greaterThanOrEqual=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL);

        // Get all the reciboList where totalDescontoComercial is greater than or equal to UPDATED_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldNotBeFound("totalDescontoComercial.greaterThanOrEqual=" + UPDATED_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoComercialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoComercial is less than or equal to DEFAULT_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldBeFound("totalDescontoComercial.lessThanOrEqual=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL);

        // Get all the reciboList where totalDescontoComercial is less than or equal to SMALLER_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldNotBeFound("totalDescontoComercial.lessThanOrEqual=" + SMALLER_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoComercialIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoComercial is less than DEFAULT_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldNotBeFound("totalDescontoComercial.lessThan=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL);

        // Get all the reciboList where totalDescontoComercial is less than UPDATED_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldBeFound("totalDescontoComercial.lessThan=" + UPDATED_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoComercialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoComercial is greater than DEFAULT_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldNotBeFound("totalDescontoComercial.greaterThan=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL);

        // Get all the reciboList where totalDescontoComercial is greater than SMALLER_TOTAL_DESCONTO_COMERCIAL
        defaultReciboShouldBeFound("totalDescontoComercial.greaterThan=" + SMALLER_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoFinanceiroIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoFinanceiro equals to DEFAULT_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldBeFound("totalDescontoFinanceiro.equals=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO);

        // Get all the reciboList where totalDescontoFinanceiro equals to UPDATED_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldNotBeFound("totalDescontoFinanceiro.equals=" + UPDATED_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoFinanceiroIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoFinanceiro in DEFAULT_TOTAL_DESCONTO_FINANCEIRO or UPDATED_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldBeFound(
            "totalDescontoFinanceiro.in=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO + "," + UPDATED_TOTAL_DESCONTO_FINANCEIRO
        );

        // Get all the reciboList where totalDescontoFinanceiro equals to UPDATED_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldNotBeFound("totalDescontoFinanceiro.in=" + UPDATED_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoFinanceiroIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoFinanceiro is not null
        defaultReciboShouldBeFound("totalDescontoFinanceiro.specified=true");

        // Get all the reciboList where totalDescontoFinanceiro is null
        defaultReciboShouldNotBeFound("totalDescontoFinanceiro.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoFinanceiroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoFinanceiro is greater than or equal to DEFAULT_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldBeFound("totalDescontoFinanceiro.greaterThanOrEqual=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO);

        // Get all the reciboList where totalDescontoFinanceiro is greater than or equal to UPDATED_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldNotBeFound("totalDescontoFinanceiro.greaterThanOrEqual=" + UPDATED_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoFinanceiroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoFinanceiro is less than or equal to DEFAULT_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldBeFound("totalDescontoFinanceiro.lessThanOrEqual=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO);

        // Get all the reciboList where totalDescontoFinanceiro is less than or equal to SMALLER_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldNotBeFound("totalDescontoFinanceiro.lessThanOrEqual=" + SMALLER_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoFinanceiroIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoFinanceiro is less than DEFAULT_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldNotBeFound("totalDescontoFinanceiro.lessThan=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO);

        // Get all the reciboList where totalDescontoFinanceiro is less than UPDATED_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldBeFound("totalDescontoFinanceiro.lessThan=" + UPDATED_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalDescontoFinanceiroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalDescontoFinanceiro is greater than DEFAULT_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldNotBeFound("totalDescontoFinanceiro.greaterThan=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO);

        // Get all the reciboList where totalDescontoFinanceiro is greater than SMALLER_TOTAL_DESCONTO_FINANCEIRO
        defaultReciboShouldBeFound("totalDescontoFinanceiro.greaterThan=" + SMALLER_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalIVAIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalIVA equals to DEFAULT_TOTAL_IVA
        defaultReciboShouldBeFound("totalIVA.equals=" + DEFAULT_TOTAL_IVA);

        // Get all the reciboList where totalIVA equals to UPDATED_TOTAL_IVA
        defaultReciboShouldNotBeFound("totalIVA.equals=" + UPDATED_TOTAL_IVA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalIVAIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalIVA in DEFAULT_TOTAL_IVA or UPDATED_TOTAL_IVA
        defaultReciboShouldBeFound("totalIVA.in=" + DEFAULT_TOTAL_IVA + "," + UPDATED_TOTAL_IVA);

        // Get all the reciboList where totalIVA equals to UPDATED_TOTAL_IVA
        defaultReciboShouldNotBeFound("totalIVA.in=" + UPDATED_TOTAL_IVA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalIVAIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalIVA is not null
        defaultReciboShouldBeFound("totalIVA.specified=true");

        // Get all the reciboList where totalIVA is null
        defaultReciboShouldNotBeFound("totalIVA.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalIVAIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalIVA is greater than or equal to DEFAULT_TOTAL_IVA
        defaultReciboShouldBeFound("totalIVA.greaterThanOrEqual=" + DEFAULT_TOTAL_IVA);

        // Get all the reciboList where totalIVA is greater than or equal to UPDATED_TOTAL_IVA
        defaultReciboShouldNotBeFound("totalIVA.greaterThanOrEqual=" + UPDATED_TOTAL_IVA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalIVAIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalIVA is less than or equal to DEFAULT_TOTAL_IVA
        defaultReciboShouldBeFound("totalIVA.lessThanOrEqual=" + DEFAULT_TOTAL_IVA);

        // Get all the reciboList where totalIVA is less than or equal to SMALLER_TOTAL_IVA
        defaultReciboShouldNotBeFound("totalIVA.lessThanOrEqual=" + SMALLER_TOTAL_IVA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalIVAIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalIVA is less than DEFAULT_TOTAL_IVA
        defaultReciboShouldNotBeFound("totalIVA.lessThan=" + DEFAULT_TOTAL_IVA);

        // Get all the reciboList where totalIVA is less than UPDATED_TOTAL_IVA
        defaultReciboShouldBeFound("totalIVA.lessThan=" + UPDATED_TOTAL_IVA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalIVAIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalIVA is greater than DEFAULT_TOTAL_IVA
        defaultReciboShouldNotBeFound("totalIVA.greaterThan=" + DEFAULT_TOTAL_IVA);

        // Get all the reciboList where totalIVA is greater than SMALLER_TOTAL_IVA
        defaultReciboShouldBeFound("totalIVA.greaterThan=" + SMALLER_TOTAL_IVA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalRetencaoIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalRetencao equals to DEFAULT_TOTAL_RETENCAO
        defaultReciboShouldBeFound("totalRetencao.equals=" + DEFAULT_TOTAL_RETENCAO);

        // Get all the reciboList where totalRetencao equals to UPDATED_TOTAL_RETENCAO
        defaultReciboShouldNotBeFound("totalRetencao.equals=" + UPDATED_TOTAL_RETENCAO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalRetencaoIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalRetencao in DEFAULT_TOTAL_RETENCAO or UPDATED_TOTAL_RETENCAO
        defaultReciboShouldBeFound("totalRetencao.in=" + DEFAULT_TOTAL_RETENCAO + "," + UPDATED_TOTAL_RETENCAO);

        // Get all the reciboList where totalRetencao equals to UPDATED_TOTAL_RETENCAO
        defaultReciboShouldNotBeFound("totalRetencao.in=" + UPDATED_TOTAL_RETENCAO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalRetencaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalRetencao is not null
        defaultReciboShouldBeFound("totalRetencao.specified=true");

        // Get all the reciboList where totalRetencao is null
        defaultReciboShouldNotBeFound("totalRetencao.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalRetencaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalRetencao is greater than or equal to DEFAULT_TOTAL_RETENCAO
        defaultReciboShouldBeFound("totalRetencao.greaterThanOrEqual=" + DEFAULT_TOTAL_RETENCAO);

        // Get all the reciboList where totalRetencao is greater than or equal to UPDATED_TOTAL_RETENCAO
        defaultReciboShouldNotBeFound("totalRetencao.greaterThanOrEqual=" + UPDATED_TOTAL_RETENCAO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalRetencaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalRetencao is less than or equal to DEFAULT_TOTAL_RETENCAO
        defaultReciboShouldBeFound("totalRetencao.lessThanOrEqual=" + DEFAULT_TOTAL_RETENCAO);

        // Get all the reciboList where totalRetencao is less than or equal to SMALLER_TOTAL_RETENCAO
        defaultReciboShouldNotBeFound("totalRetencao.lessThanOrEqual=" + SMALLER_TOTAL_RETENCAO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalRetencaoIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalRetencao is less than DEFAULT_TOTAL_RETENCAO
        defaultReciboShouldNotBeFound("totalRetencao.lessThan=" + DEFAULT_TOTAL_RETENCAO);

        // Get all the reciboList where totalRetencao is less than UPDATED_TOTAL_RETENCAO
        defaultReciboShouldBeFound("totalRetencao.lessThan=" + UPDATED_TOTAL_RETENCAO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalRetencaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalRetencao is greater than DEFAULT_TOTAL_RETENCAO
        defaultReciboShouldNotBeFound("totalRetencao.greaterThan=" + DEFAULT_TOTAL_RETENCAO);

        // Get all the reciboList where totalRetencao is greater than SMALLER_TOTAL_RETENCAO
        defaultReciboShouldBeFound("totalRetencao.greaterThan=" + SMALLER_TOTAL_RETENCAO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalJuroIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalJuro equals to DEFAULT_TOTAL_JURO
        defaultReciboShouldBeFound("totalJuro.equals=" + DEFAULT_TOTAL_JURO);

        // Get all the reciboList where totalJuro equals to UPDATED_TOTAL_JURO
        defaultReciboShouldNotBeFound("totalJuro.equals=" + UPDATED_TOTAL_JURO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalJuroIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalJuro in DEFAULT_TOTAL_JURO or UPDATED_TOTAL_JURO
        defaultReciboShouldBeFound("totalJuro.in=" + DEFAULT_TOTAL_JURO + "," + UPDATED_TOTAL_JURO);

        // Get all the reciboList where totalJuro equals to UPDATED_TOTAL_JURO
        defaultReciboShouldNotBeFound("totalJuro.in=" + UPDATED_TOTAL_JURO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalJuroIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalJuro is not null
        defaultReciboShouldBeFound("totalJuro.specified=true");

        // Get all the reciboList where totalJuro is null
        defaultReciboShouldNotBeFound("totalJuro.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalJuroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalJuro is greater than or equal to DEFAULT_TOTAL_JURO
        defaultReciboShouldBeFound("totalJuro.greaterThanOrEqual=" + DEFAULT_TOTAL_JURO);

        // Get all the reciboList where totalJuro is greater than or equal to UPDATED_TOTAL_JURO
        defaultReciboShouldNotBeFound("totalJuro.greaterThanOrEqual=" + UPDATED_TOTAL_JURO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalJuroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalJuro is less than or equal to DEFAULT_TOTAL_JURO
        defaultReciboShouldBeFound("totalJuro.lessThanOrEqual=" + DEFAULT_TOTAL_JURO);

        // Get all the reciboList where totalJuro is less than or equal to SMALLER_TOTAL_JURO
        defaultReciboShouldNotBeFound("totalJuro.lessThanOrEqual=" + SMALLER_TOTAL_JURO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalJuroIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalJuro is less than DEFAULT_TOTAL_JURO
        defaultReciboShouldNotBeFound("totalJuro.lessThan=" + DEFAULT_TOTAL_JURO);

        // Get all the reciboList where totalJuro is less than UPDATED_TOTAL_JURO
        defaultReciboShouldBeFound("totalJuro.lessThan=" + UPDATED_TOTAL_JURO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalJuroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalJuro is greater than DEFAULT_TOTAL_JURO
        defaultReciboShouldNotBeFound("totalJuro.greaterThan=" + DEFAULT_TOTAL_JURO);

        // Get all the reciboList where totalJuro is greater than SMALLER_TOTAL_JURO
        defaultReciboShouldBeFound("totalJuro.greaterThan=" + SMALLER_TOTAL_JURO);
    }

    @Test
    @Transactional
    void getAllRecibosByCambioIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where cambio equals to DEFAULT_CAMBIO
        defaultReciboShouldBeFound("cambio.equals=" + DEFAULT_CAMBIO);

        // Get all the reciboList where cambio equals to UPDATED_CAMBIO
        defaultReciboShouldNotBeFound("cambio.equals=" + UPDATED_CAMBIO);
    }

    @Test
    @Transactional
    void getAllRecibosByCambioIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where cambio in DEFAULT_CAMBIO or UPDATED_CAMBIO
        defaultReciboShouldBeFound("cambio.in=" + DEFAULT_CAMBIO + "," + UPDATED_CAMBIO);

        // Get all the reciboList where cambio equals to UPDATED_CAMBIO
        defaultReciboShouldNotBeFound("cambio.in=" + UPDATED_CAMBIO);
    }

    @Test
    @Transactional
    void getAllRecibosByCambioIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where cambio is not null
        defaultReciboShouldBeFound("cambio.specified=true");

        // Get all the reciboList where cambio is null
        defaultReciboShouldNotBeFound("cambio.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByCambioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where cambio is greater than or equal to DEFAULT_CAMBIO
        defaultReciboShouldBeFound("cambio.greaterThanOrEqual=" + DEFAULT_CAMBIO);

        // Get all the reciboList where cambio is greater than or equal to UPDATED_CAMBIO
        defaultReciboShouldNotBeFound("cambio.greaterThanOrEqual=" + UPDATED_CAMBIO);
    }

    @Test
    @Transactional
    void getAllRecibosByCambioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where cambio is less than or equal to DEFAULT_CAMBIO
        defaultReciboShouldBeFound("cambio.lessThanOrEqual=" + DEFAULT_CAMBIO);

        // Get all the reciboList where cambio is less than or equal to SMALLER_CAMBIO
        defaultReciboShouldNotBeFound("cambio.lessThanOrEqual=" + SMALLER_CAMBIO);
    }

    @Test
    @Transactional
    void getAllRecibosByCambioIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where cambio is less than DEFAULT_CAMBIO
        defaultReciboShouldNotBeFound("cambio.lessThan=" + DEFAULT_CAMBIO);

        // Get all the reciboList where cambio is less than UPDATED_CAMBIO
        defaultReciboShouldBeFound("cambio.lessThan=" + UPDATED_CAMBIO);
    }

    @Test
    @Transactional
    void getAllRecibosByCambioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where cambio is greater than DEFAULT_CAMBIO
        defaultReciboShouldNotBeFound("cambio.greaterThan=" + DEFAULT_CAMBIO);

        // Get all the reciboList where cambio is greater than SMALLER_CAMBIO
        defaultReciboShouldBeFound("cambio.greaterThan=" + SMALLER_CAMBIO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalMoedaEstrangeiraIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalMoedaEstrangeira equals to DEFAULT_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldBeFound("totalMoedaEstrangeira.equals=" + DEFAULT_TOTAL_MOEDA_ESTRANGEIRA);

        // Get all the reciboList where totalMoedaEstrangeira equals to UPDATED_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldNotBeFound("totalMoedaEstrangeira.equals=" + UPDATED_TOTAL_MOEDA_ESTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalMoedaEstrangeiraIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalMoedaEstrangeira in DEFAULT_TOTAL_MOEDA_ESTRANGEIRA or UPDATED_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldBeFound("totalMoedaEstrangeira.in=" + DEFAULT_TOTAL_MOEDA_ESTRANGEIRA + "," + UPDATED_TOTAL_MOEDA_ESTRANGEIRA);

        // Get all the reciboList where totalMoedaEstrangeira equals to UPDATED_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldNotBeFound("totalMoedaEstrangeira.in=" + UPDATED_TOTAL_MOEDA_ESTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalMoedaEstrangeiraIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalMoedaEstrangeira is not null
        defaultReciboShouldBeFound("totalMoedaEstrangeira.specified=true");

        // Get all the reciboList where totalMoedaEstrangeira is null
        defaultReciboShouldNotBeFound("totalMoedaEstrangeira.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalMoedaEstrangeiraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalMoedaEstrangeira is greater than or equal to DEFAULT_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldBeFound("totalMoedaEstrangeira.greaterThanOrEqual=" + DEFAULT_TOTAL_MOEDA_ESTRANGEIRA);

        // Get all the reciboList where totalMoedaEstrangeira is greater than or equal to UPDATED_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldNotBeFound("totalMoedaEstrangeira.greaterThanOrEqual=" + UPDATED_TOTAL_MOEDA_ESTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalMoedaEstrangeiraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalMoedaEstrangeira is less than or equal to DEFAULT_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldBeFound("totalMoedaEstrangeira.lessThanOrEqual=" + DEFAULT_TOTAL_MOEDA_ESTRANGEIRA);

        // Get all the reciboList where totalMoedaEstrangeira is less than or equal to SMALLER_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldNotBeFound("totalMoedaEstrangeira.lessThanOrEqual=" + SMALLER_TOTAL_MOEDA_ESTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalMoedaEstrangeiraIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalMoedaEstrangeira is less than DEFAULT_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldNotBeFound("totalMoedaEstrangeira.lessThan=" + DEFAULT_TOTAL_MOEDA_ESTRANGEIRA);

        // Get all the reciboList where totalMoedaEstrangeira is less than UPDATED_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldBeFound("totalMoedaEstrangeira.lessThan=" + UPDATED_TOTAL_MOEDA_ESTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalMoedaEstrangeiraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalMoedaEstrangeira is greater than DEFAULT_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldNotBeFound("totalMoedaEstrangeira.greaterThan=" + DEFAULT_TOTAL_MOEDA_ESTRANGEIRA);

        // Get all the reciboList where totalMoedaEstrangeira is greater than SMALLER_TOTAL_MOEDA_ESTRANGEIRA
        defaultReciboShouldBeFound("totalMoedaEstrangeira.greaterThan=" + SMALLER_TOTAL_MOEDA_ESTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagarIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPagar equals to DEFAULT_TOTAL_PAGAR
        defaultReciboShouldBeFound("totalPagar.equals=" + DEFAULT_TOTAL_PAGAR);

        // Get all the reciboList where totalPagar equals to UPDATED_TOTAL_PAGAR
        defaultReciboShouldNotBeFound("totalPagar.equals=" + UPDATED_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagarIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPagar in DEFAULT_TOTAL_PAGAR or UPDATED_TOTAL_PAGAR
        defaultReciboShouldBeFound("totalPagar.in=" + DEFAULT_TOTAL_PAGAR + "," + UPDATED_TOTAL_PAGAR);

        // Get all the reciboList where totalPagar equals to UPDATED_TOTAL_PAGAR
        defaultReciboShouldNotBeFound("totalPagar.in=" + UPDATED_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagarIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPagar is not null
        defaultReciboShouldBeFound("totalPagar.specified=true");

        // Get all the reciboList where totalPagar is null
        defaultReciboShouldNotBeFound("totalPagar.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPagar is greater than or equal to DEFAULT_TOTAL_PAGAR
        defaultReciboShouldBeFound("totalPagar.greaterThanOrEqual=" + DEFAULT_TOTAL_PAGAR);

        // Get all the reciboList where totalPagar is greater than or equal to UPDATED_TOTAL_PAGAR
        defaultReciboShouldNotBeFound("totalPagar.greaterThanOrEqual=" + UPDATED_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagarIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPagar is less than or equal to DEFAULT_TOTAL_PAGAR
        defaultReciboShouldBeFound("totalPagar.lessThanOrEqual=" + DEFAULT_TOTAL_PAGAR);

        // Get all the reciboList where totalPagar is less than or equal to SMALLER_TOTAL_PAGAR
        defaultReciboShouldNotBeFound("totalPagar.lessThanOrEqual=" + SMALLER_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagarIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPagar is less than DEFAULT_TOTAL_PAGAR
        defaultReciboShouldNotBeFound("totalPagar.lessThan=" + DEFAULT_TOTAL_PAGAR);

        // Get all the reciboList where totalPagar is less than UPDATED_TOTAL_PAGAR
        defaultReciboShouldBeFound("totalPagar.lessThan=" + UPDATED_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagarIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPagar is greater than DEFAULT_TOTAL_PAGAR
        defaultReciboShouldNotBeFound("totalPagar.greaterThan=" + DEFAULT_TOTAL_PAGAR);

        // Get all the reciboList where totalPagar is greater than SMALLER_TOTAL_PAGAR
        defaultReciboShouldBeFound("totalPagar.greaterThan=" + SMALLER_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagoIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPago equals to DEFAULT_TOTAL_PAGO
        defaultReciboShouldBeFound("totalPago.equals=" + DEFAULT_TOTAL_PAGO);

        // Get all the reciboList where totalPago equals to UPDATED_TOTAL_PAGO
        defaultReciboShouldNotBeFound("totalPago.equals=" + UPDATED_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagoIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPago in DEFAULT_TOTAL_PAGO or UPDATED_TOTAL_PAGO
        defaultReciboShouldBeFound("totalPago.in=" + DEFAULT_TOTAL_PAGO + "," + UPDATED_TOTAL_PAGO);

        // Get all the reciboList where totalPago equals to UPDATED_TOTAL_PAGO
        defaultReciboShouldNotBeFound("totalPago.in=" + UPDATED_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPago is not null
        defaultReciboShouldBeFound("totalPago.specified=true");

        // Get all the reciboList where totalPago is null
        defaultReciboShouldNotBeFound("totalPago.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPago is greater than or equal to DEFAULT_TOTAL_PAGO
        defaultReciboShouldBeFound("totalPago.greaterThanOrEqual=" + DEFAULT_TOTAL_PAGO);

        // Get all the reciboList where totalPago is greater than or equal to UPDATED_TOTAL_PAGO
        defaultReciboShouldNotBeFound("totalPago.greaterThanOrEqual=" + UPDATED_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPago is less than or equal to DEFAULT_TOTAL_PAGO
        defaultReciboShouldBeFound("totalPago.lessThanOrEqual=" + DEFAULT_TOTAL_PAGO);

        // Get all the reciboList where totalPago is less than or equal to SMALLER_TOTAL_PAGO
        defaultReciboShouldNotBeFound("totalPago.lessThanOrEqual=" + SMALLER_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagoIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPago is less than DEFAULT_TOTAL_PAGO
        defaultReciboShouldNotBeFound("totalPago.lessThan=" + DEFAULT_TOTAL_PAGO);

        // Get all the reciboList where totalPago is less than UPDATED_TOTAL_PAGO
        defaultReciboShouldBeFound("totalPago.lessThan=" + UPDATED_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalPagoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalPago is greater than DEFAULT_TOTAL_PAGO
        defaultReciboShouldNotBeFound("totalPago.greaterThan=" + DEFAULT_TOTAL_PAGO);

        // Get all the reciboList where totalPago is greater than SMALLER_TOTAL_PAGO
        defaultReciboShouldBeFound("totalPago.greaterThan=" + SMALLER_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalFaltaIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalFalta equals to DEFAULT_TOTAL_FALTA
        defaultReciboShouldBeFound("totalFalta.equals=" + DEFAULT_TOTAL_FALTA);

        // Get all the reciboList where totalFalta equals to UPDATED_TOTAL_FALTA
        defaultReciboShouldNotBeFound("totalFalta.equals=" + UPDATED_TOTAL_FALTA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalFaltaIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalFalta in DEFAULT_TOTAL_FALTA or UPDATED_TOTAL_FALTA
        defaultReciboShouldBeFound("totalFalta.in=" + DEFAULT_TOTAL_FALTA + "," + UPDATED_TOTAL_FALTA);

        // Get all the reciboList where totalFalta equals to UPDATED_TOTAL_FALTA
        defaultReciboShouldNotBeFound("totalFalta.in=" + UPDATED_TOTAL_FALTA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalFaltaIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalFalta is not null
        defaultReciboShouldBeFound("totalFalta.specified=true");

        // Get all the reciboList where totalFalta is null
        defaultReciboShouldNotBeFound("totalFalta.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalFaltaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalFalta is greater than or equal to DEFAULT_TOTAL_FALTA
        defaultReciboShouldBeFound("totalFalta.greaterThanOrEqual=" + DEFAULT_TOTAL_FALTA);

        // Get all the reciboList where totalFalta is greater than or equal to UPDATED_TOTAL_FALTA
        defaultReciboShouldNotBeFound("totalFalta.greaterThanOrEqual=" + UPDATED_TOTAL_FALTA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalFaltaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalFalta is less than or equal to DEFAULT_TOTAL_FALTA
        defaultReciboShouldBeFound("totalFalta.lessThanOrEqual=" + DEFAULT_TOTAL_FALTA);

        // Get all the reciboList where totalFalta is less than or equal to SMALLER_TOTAL_FALTA
        defaultReciboShouldNotBeFound("totalFalta.lessThanOrEqual=" + SMALLER_TOTAL_FALTA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalFaltaIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalFalta is less than DEFAULT_TOTAL_FALTA
        defaultReciboShouldNotBeFound("totalFalta.lessThan=" + DEFAULT_TOTAL_FALTA);

        // Get all the reciboList where totalFalta is less than UPDATED_TOTAL_FALTA
        defaultReciboShouldBeFound("totalFalta.lessThan=" + UPDATED_TOTAL_FALTA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalFaltaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalFalta is greater than DEFAULT_TOTAL_FALTA
        defaultReciboShouldNotBeFound("totalFalta.greaterThan=" + DEFAULT_TOTAL_FALTA);

        // Get all the reciboList where totalFalta is greater than SMALLER_TOTAL_FALTA
        defaultReciboShouldBeFound("totalFalta.greaterThan=" + SMALLER_TOTAL_FALTA);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalTrocoIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalTroco equals to DEFAULT_TOTAL_TROCO
        defaultReciboShouldBeFound("totalTroco.equals=" + DEFAULT_TOTAL_TROCO);

        // Get all the reciboList where totalTroco equals to UPDATED_TOTAL_TROCO
        defaultReciboShouldNotBeFound("totalTroco.equals=" + UPDATED_TOTAL_TROCO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalTrocoIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalTroco in DEFAULT_TOTAL_TROCO or UPDATED_TOTAL_TROCO
        defaultReciboShouldBeFound("totalTroco.in=" + DEFAULT_TOTAL_TROCO + "," + UPDATED_TOTAL_TROCO);

        // Get all the reciboList where totalTroco equals to UPDATED_TOTAL_TROCO
        defaultReciboShouldNotBeFound("totalTroco.in=" + UPDATED_TOTAL_TROCO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalTrocoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalTroco is not null
        defaultReciboShouldBeFound("totalTroco.specified=true");

        // Get all the reciboList where totalTroco is null
        defaultReciboShouldNotBeFound("totalTroco.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTotalTrocoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalTroco is greater than or equal to DEFAULT_TOTAL_TROCO
        defaultReciboShouldBeFound("totalTroco.greaterThanOrEqual=" + DEFAULT_TOTAL_TROCO);

        // Get all the reciboList where totalTroco is greater than or equal to UPDATED_TOTAL_TROCO
        defaultReciboShouldNotBeFound("totalTroco.greaterThanOrEqual=" + UPDATED_TOTAL_TROCO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalTrocoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalTroco is less than or equal to DEFAULT_TOTAL_TROCO
        defaultReciboShouldBeFound("totalTroco.lessThanOrEqual=" + DEFAULT_TOTAL_TROCO);

        // Get all the reciboList where totalTroco is less than or equal to SMALLER_TOTAL_TROCO
        defaultReciboShouldNotBeFound("totalTroco.lessThanOrEqual=" + SMALLER_TOTAL_TROCO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalTrocoIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalTroco is less than DEFAULT_TOTAL_TROCO
        defaultReciboShouldNotBeFound("totalTroco.lessThan=" + DEFAULT_TOTAL_TROCO);

        // Get all the reciboList where totalTroco is less than UPDATED_TOTAL_TROCO
        defaultReciboShouldBeFound("totalTroco.lessThan=" + UPDATED_TOTAL_TROCO);
    }

    @Test
    @Transactional
    void getAllRecibosByTotalTrocoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where totalTroco is greater than DEFAULT_TOTAL_TROCO
        defaultReciboShouldNotBeFound("totalTroco.greaterThan=" + DEFAULT_TOTAL_TROCO);

        // Get all the reciboList where totalTroco is greater than SMALLER_TOTAL_TROCO
        defaultReciboShouldBeFound("totalTroco.greaterThan=" + SMALLER_TOTAL_TROCO);
    }

    @Test
    @Transactional
    void getAllRecibosByIsNovoIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where isNovo equals to DEFAULT_IS_NOVO
        defaultReciboShouldBeFound("isNovo.equals=" + DEFAULT_IS_NOVO);

        // Get all the reciboList where isNovo equals to UPDATED_IS_NOVO
        defaultReciboShouldNotBeFound("isNovo.equals=" + UPDATED_IS_NOVO);
    }

    @Test
    @Transactional
    void getAllRecibosByIsNovoIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where isNovo in DEFAULT_IS_NOVO or UPDATED_IS_NOVO
        defaultReciboShouldBeFound("isNovo.in=" + DEFAULT_IS_NOVO + "," + UPDATED_IS_NOVO);

        // Get all the reciboList where isNovo equals to UPDATED_IS_NOVO
        defaultReciboShouldNotBeFound("isNovo.in=" + UPDATED_IS_NOVO);
    }

    @Test
    @Transactional
    void getAllRecibosByIsNovoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where isNovo is not null
        defaultReciboShouldBeFound("isNovo.specified=true");

        // Get all the reciboList where isNovo is null
        defaultReciboShouldNotBeFound("isNovo.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where timestamp equals to DEFAULT_TIMESTAMP
        defaultReciboShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the reciboList where timestamp equals to UPDATED_TIMESTAMP
        defaultReciboShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllRecibosByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultReciboShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the reciboList where timestamp equals to UPDATED_TIMESTAMP
        defaultReciboShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllRecibosByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where timestamp is not null
        defaultReciboShouldBeFound("timestamp.specified=true");

        // Get all the reciboList where timestamp is null
        defaultReciboShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultReciboShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the reciboList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultReciboShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllRecibosByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultReciboShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the reciboList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultReciboShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllRecibosByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where timestamp is less than DEFAULT_TIMESTAMP
        defaultReciboShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the reciboList where timestamp is less than UPDATED_TIMESTAMP
        defaultReciboShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllRecibosByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultReciboShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the reciboList where timestamp is greater than SMALLER_TIMESTAMP
        defaultReciboShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllRecibosByDebitoIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where debito equals to DEFAULT_DEBITO
        defaultReciboShouldBeFound("debito.equals=" + DEFAULT_DEBITO);

        // Get all the reciboList where debito equals to UPDATED_DEBITO
        defaultReciboShouldNotBeFound("debito.equals=" + UPDATED_DEBITO);
    }

    @Test
    @Transactional
    void getAllRecibosByDebitoIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where debito in DEFAULT_DEBITO or UPDATED_DEBITO
        defaultReciboShouldBeFound("debito.in=" + DEFAULT_DEBITO + "," + UPDATED_DEBITO);

        // Get all the reciboList where debito equals to UPDATED_DEBITO
        defaultReciboShouldNotBeFound("debito.in=" + UPDATED_DEBITO);
    }

    @Test
    @Transactional
    void getAllRecibosByDebitoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where debito is not null
        defaultReciboShouldBeFound("debito.specified=true");

        // Get all the reciboList where debito is null
        defaultReciboShouldNotBeFound("debito.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByDebitoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where debito is greater than or equal to DEFAULT_DEBITO
        defaultReciboShouldBeFound("debito.greaterThanOrEqual=" + DEFAULT_DEBITO);

        // Get all the reciboList where debito is greater than or equal to UPDATED_DEBITO
        defaultReciboShouldNotBeFound("debito.greaterThanOrEqual=" + UPDATED_DEBITO);
    }

    @Test
    @Transactional
    void getAllRecibosByDebitoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where debito is less than or equal to DEFAULT_DEBITO
        defaultReciboShouldBeFound("debito.lessThanOrEqual=" + DEFAULT_DEBITO);

        // Get all the reciboList where debito is less than or equal to SMALLER_DEBITO
        defaultReciboShouldNotBeFound("debito.lessThanOrEqual=" + SMALLER_DEBITO);
    }

    @Test
    @Transactional
    void getAllRecibosByDebitoIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where debito is less than DEFAULT_DEBITO
        defaultReciboShouldNotBeFound("debito.lessThan=" + DEFAULT_DEBITO);

        // Get all the reciboList where debito is less than UPDATED_DEBITO
        defaultReciboShouldBeFound("debito.lessThan=" + UPDATED_DEBITO);
    }

    @Test
    @Transactional
    void getAllRecibosByDebitoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where debito is greater than DEFAULT_DEBITO
        defaultReciboShouldNotBeFound("debito.greaterThan=" + DEFAULT_DEBITO);

        // Get all the reciboList where debito is greater than SMALLER_DEBITO
        defaultReciboShouldBeFound("debito.greaterThan=" + SMALLER_DEBITO);
    }

    @Test
    @Transactional
    void getAllRecibosByCreditoIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where credito equals to DEFAULT_CREDITO
        defaultReciboShouldBeFound("credito.equals=" + DEFAULT_CREDITO);

        // Get all the reciboList where credito equals to UPDATED_CREDITO
        defaultReciboShouldNotBeFound("credito.equals=" + UPDATED_CREDITO);
    }

    @Test
    @Transactional
    void getAllRecibosByCreditoIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where credito in DEFAULT_CREDITO or UPDATED_CREDITO
        defaultReciboShouldBeFound("credito.in=" + DEFAULT_CREDITO + "," + UPDATED_CREDITO);

        // Get all the reciboList where credito equals to UPDATED_CREDITO
        defaultReciboShouldNotBeFound("credito.in=" + UPDATED_CREDITO);
    }

    @Test
    @Transactional
    void getAllRecibosByCreditoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where credito is not null
        defaultReciboShouldBeFound("credito.specified=true");

        // Get all the reciboList where credito is null
        defaultReciboShouldNotBeFound("credito.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByCreditoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where credito is greater than or equal to DEFAULT_CREDITO
        defaultReciboShouldBeFound("credito.greaterThanOrEqual=" + DEFAULT_CREDITO);

        // Get all the reciboList where credito is greater than or equal to UPDATED_CREDITO
        defaultReciboShouldNotBeFound("credito.greaterThanOrEqual=" + UPDATED_CREDITO);
    }

    @Test
    @Transactional
    void getAllRecibosByCreditoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where credito is less than or equal to DEFAULT_CREDITO
        defaultReciboShouldBeFound("credito.lessThanOrEqual=" + DEFAULT_CREDITO);

        // Get all the reciboList where credito is less than or equal to SMALLER_CREDITO
        defaultReciboShouldNotBeFound("credito.lessThanOrEqual=" + SMALLER_CREDITO);
    }

    @Test
    @Transactional
    void getAllRecibosByCreditoIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where credito is less than DEFAULT_CREDITO
        defaultReciboShouldNotBeFound("credito.lessThan=" + DEFAULT_CREDITO);

        // Get all the reciboList where credito is less than UPDATED_CREDITO
        defaultReciboShouldBeFound("credito.lessThan=" + UPDATED_CREDITO);
    }

    @Test
    @Transactional
    void getAllRecibosByCreditoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where credito is greater than DEFAULT_CREDITO
        defaultReciboShouldNotBeFound("credito.greaterThan=" + DEFAULT_CREDITO);

        // Get all the reciboList where credito is greater than SMALLER_CREDITO
        defaultReciboShouldBeFound("credito.greaterThan=" + SMALLER_CREDITO);
    }

    @Test
    @Transactional
    void getAllRecibosByIsFiscalizadoIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where isFiscalizado equals to DEFAULT_IS_FISCALIZADO
        defaultReciboShouldBeFound("isFiscalizado.equals=" + DEFAULT_IS_FISCALIZADO);

        // Get all the reciboList where isFiscalizado equals to UPDATED_IS_FISCALIZADO
        defaultReciboShouldNotBeFound("isFiscalizado.equals=" + UPDATED_IS_FISCALIZADO);
    }

    @Test
    @Transactional
    void getAllRecibosByIsFiscalizadoIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where isFiscalizado in DEFAULT_IS_FISCALIZADO or UPDATED_IS_FISCALIZADO
        defaultReciboShouldBeFound("isFiscalizado.in=" + DEFAULT_IS_FISCALIZADO + "," + UPDATED_IS_FISCALIZADO);

        // Get all the reciboList where isFiscalizado equals to UPDATED_IS_FISCALIZADO
        defaultReciboShouldNotBeFound("isFiscalizado.in=" + UPDATED_IS_FISCALIZADO);
    }

    @Test
    @Transactional
    void getAllRecibosByIsFiscalizadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where isFiscalizado is not null
        defaultReciboShouldBeFound("isFiscalizado.specified=true");

        // Get all the reciboList where isFiscalizado is null
        defaultReciboShouldNotBeFound("isFiscalizado.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosBySignTextIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where signText equals to DEFAULT_SIGN_TEXT
        defaultReciboShouldBeFound("signText.equals=" + DEFAULT_SIGN_TEXT);

        // Get all the reciboList where signText equals to UPDATED_SIGN_TEXT
        defaultReciboShouldNotBeFound("signText.equals=" + UPDATED_SIGN_TEXT);
    }

    @Test
    @Transactional
    void getAllRecibosBySignTextIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where signText in DEFAULT_SIGN_TEXT or UPDATED_SIGN_TEXT
        defaultReciboShouldBeFound("signText.in=" + DEFAULT_SIGN_TEXT + "," + UPDATED_SIGN_TEXT);

        // Get all the reciboList where signText equals to UPDATED_SIGN_TEXT
        defaultReciboShouldNotBeFound("signText.in=" + UPDATED_SIGN_TEXT);
    }

    @Test
    @Transactional
    void getAllRecibosBySignTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where signText is not null
        defaultReciboShouldBeFound("signText.specified=true");

        // Get all the reciboList where signText is null
        defaultReciboShouldNotBeFound("signText.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosBySignTextContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where signText contains DEFAULT_SIGN_TEXT
        defaultReciboShouldBeFound("signText.contains=" + DEFAULT_SIGN_TEXT);

        // Get all the reciboList where signText contains UPDATED_SIGN_TEXT
        defaultReciboShouldNotBeFound("signText.contains=" + UPDATED_SIGN_TEXT);
    }

    @Test
    @Transactional
    void getAllRecibosBySignTextNotContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where signText does not contain DEFAULT_SIGN_TEXT
        defaultReciboShouldNotBeFound("signText.doesNotContain=" + DEFAULT_SIGN_TEXT);

        // Get all the reciboList where signText does not contain UPDATED_SIGN_TEXT
        defaultReciboShouldBeFound("signText.doesNotContain=" + UPDATED_SIGN_TEXT);
    }

    @Test
    @Transactional
    void getAllRecibosByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hash equals to DEFAULT_HASH
        defaultReciboShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the reciboList where hash equals to UPDATED_HASH
        defaultReciboShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllRecibosByHashIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultReciboShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the reciboList where hash equals to UPDATED_HASH
        defaultReciboShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllRecibosByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hash is not null
        defaultReciboShouldBeFound("hash.specified=true");

        // Get all the reciboList where hash is null
        defaultReciboShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByHashContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hash contains DEFAULT_HASH
        defaultReciboShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the reciboList where hash contains UPDATED_HASH
        defaultReciboShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllRecibosByHashNotContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hash does not contain DEFAULT_HASH
        defaultReciboShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the reciboList where hash does not contain UPDATED_HASH
        defaultReciboShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllRecibosByHashShortIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hashShort equals to DEFAULT_HASH_SHORT
        defaultReciboShouldBeFound("hashShort.equals=" + DEFAULT_HASH_SHORT);

        // Get all the reciboList where hashShort equals to UPDATED_HASH_SHORT
        defaultReciboShouldNotBeFound("hashShort.equals=" + UPDATED_HASH_SHORT);
    }

    @Test
    @Transactional
    void getAllRecibosByHashShortIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hashShort in DEFAULT_HASH_SHORT or UPDATED_HASH_SHORT
        defaultReciboShouldBeFound("hashShort.in=" + DEFAULT_HASH_SHORT + "," + UPDATED_HASH_SHORT);

        // Get all the reciboList where hashShort equals to UPDATED_HASH_SHORT
        defaultReciboShouldNotBeFound("hashShort.in=" + UPDATED_HASH_SHORT);
    }

    @Test
    @Transactional
    void getAllRecibosByHashShortIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hashShort is not null
        defaultReciboShouldBeFound("hashShort.specified=true");

        // Get all the reciboList where hashShort is null
        defaultReciboShouldNotBeFound("hashShort.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByHashShortContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hashShort contains DEFAULT_HASH_SHORT
        defaultReciboShouldBeFound("hashShort.contains=" + DEFAULT_HASH_SHORT);

        // Get all the reciboList where hashShort contains UPDATED_HASH_SHORT
        defaultReciboShouldNotBeFound("hashShort.contains=" + UPDATED_HASH_SHORT);
    }

    @Test
    @Transactional
    void getAllRecibosByHashShortNotContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hashShort does not contain DEFAULT_HASH_SHORT
        defaultReciboShouldNotBeFound("hashShort.doesNotContain=" + DEFAULT_HASH_SHORT);

        // Get all the reciboList where hashShort does not contain UPDATED_HASH_SHORT
        defaultReciboShouldBeFound("hashShort.doesNotContain=" + UPDATED_HASH_SHORT);
    }

    @Test
    @Transactional
    void getAllRecibosByHashControlIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hashControl equals to DEFAULT_HASH_CONTROL
        defaultReciboShouldBeFound("hashControl.equals=" + DEFAULT_HASH_CONTROL);

        // Get all the reciboList where hashControl equals to UPDATED_HASH_CONTROL
        defaultReciboShouldNotBeFound("hashControl.equals=" + UPDATED_HASH_CONTROL);
    }

    @Test
    @Transactional
    void getAllRecibosByHashControlIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hashControl in DEFAULT_HASH_CONTROL or UPDATED_HASH_CONTROL
        defaultReciboShouldBeFound("hashControl.in=" + DEFAULT_HASH_CONTROL + "," + UPDATED_HASH_CONTROL);

        // Get all the reciboList where hashControl equals to UPDATED_HASH_CONTROL
        defaultReciboShouldNotBeFound("hashControl.in=" + UPDATED_HASH_CONTROL);
    }

    @Test
    @Transactional
    void getAllRecibosByHashControlIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hashControl is not null
        defaultReciboShouldBeFound("hashControl.specified=true");

        // Get all the reciboList where hashControl is null
        defaultReciboShouldNotBeFound("hashControl.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByHashControlContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hashControl contains DEFAULT_HASH_CONTROL
        defaultReciboShouldBeFound("hashControl.contains=" + DEFAULT_HASH_CONTROL);

        // Get all the reciboList where hashControl contains UPDATED_HASH_CONTROL
        defaultReciboShouldNotBeFound("hashControl.contains=" + UPDATED_HASH_CONTROL);
    }

    @Test
    @Transactional
    void getAllRecibosByHashControlNotContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where hashControl does not contain DEFAULT_HASH_CONTROL
        defaultReciboShouldNotBeFound("hashControl.doesNotContain=" + DEFAULT_HASH_CONTROL);

        // Get all the reciboList where hashControl does not contain UPDATED_HASH_CONTROL
        defaultReciboShouldBeFound("hashControl.doesNotContain=" + UPDATED_HASH_CONTROL);
    }

    @Test
    @Transactional
    void getAllRecibosByKeyVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where keyVersion equals to DEFAULT_KEY_VERSION
        defaultReciboShouldBeFound("keyVersion.equals=" + DEFAULT_KEY_VERSION);

        // Get all the reciboList where keyVersion equals to UPDATED_KEY_VERSION
        defaultReciboShouldNotBeFound("keyVersion.equals=" + UPDATED_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllRecibosByKeyVersionIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where keyVersion in DEFAULT_KEY_VERSION or UPDATED_KEY_VERSION
        defaultReciboShouldBeFound("keyVersion.in=" + DEFAULT_KEY_VERSION + "," + UPDATED_KEY_VERSION);

        // Get all the reciboList where keyVersion equals to UPDATED_KEY_VERSION
        defaultReciboShouldNotBeFound("keyVersion.in=" + UPDATED_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllRecibosByKeyVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where keyVersion is not null
        defaultReciboShouldBeFound("keyVersion.specified=true");

        // Get all the reciboList where keyVersion is null
        defaultReciboShouldNotBeFound("keyVersion.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByKeyVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where keyVersion is greater than or equal to DEFAULT_KEY_VERSION
        defaultReciboShouldBeFound("keyVersion.greaterThanOrEqual=" + DEFAULT_KEY_VERSION);

        // Get all the reciboList where keyVersion is greater than or equal to UPDATED_KEY_VERSION
        defaultReciboShouldNotBeFound("keyVersion.greaterThanOrEqual=" + UPDATED_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllRecibosByKeyVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where keyVersion is less than or equal to DEFAULT_KEY_VERSION
        defaultReciboShouldBeFound("keyVersion.lessThanOrEqual=" + DEFAULT_KEY_VERSION);

        // Get all the reciboList where keyVersion is less than or equal to SMALLER_KEY_VERSION
        defaultReciboShouldNotBeFound("keyVersion.lessThanOrEqual=" + SMALLER_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllRecibosByKeyVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where keyVersion is less than DEFAULT_KEY_VERSION
        defaultReciboShouldNotBeFound("keyVersion.lessThan=" + DEFAULT_KEY_VERSION);

        // Get all the reciboList where keyVersion is less than UPDATED_KEY_VERSION
        defaultReciboShouldBeFound("keyVersion.lessThan=" + UPDATED_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllRecibosByKeyVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where keyVersion is greater than DEFAULT_KEY_VERSION
        defaultReciboShouldNotBeFound("keyVersion.greaterThan=" + DEFAULT_KEY_VERSION);

        // Get all the reciboList where keyVersion is greater than SMALLER_KEY_VERSION
        defaultReciboShouldBeFound("keyVersion.greaterThan=" + SMALLER_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllRecibosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where estado equals to DEFAULT_ESTADO
        defaultReciboShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the reciboList where estado equals to UPDATED_ESTADO
        defaultReciboShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllRecibosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultReciboShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the reciboList where estado equals to UPDATED_ESTADO
        defaultReciboShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllRecibosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where estado is not null
        defaultReciboShouldBeFound("estado.specified=true");

        // Get all the reciboList where estado is null
        defaultReciboShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByOrigemIsEqualToSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where origem equals to DEFAULT_ORIGEM
        defaultReciboShouldBeFound("origem.equals=" + DEFAULT_ORIGEM);

        // Get all the reciboList where origem equals to UPDATED_ORIGEM
        defaultReciboShouldNotBeFound("origem.equals=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllRecibosByOrigemIsInShouldWork() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where origem in DEFAULT_ORIGEM or UPDATED_ORIGEM
        defaultReciboShouldBeFound("origem.in=" + DEFAULT_ORIGEM + "," + UPDATED_ORIGEM);

        // Get all the reciboList where origem equals to UPDATED_ORIGEM
        defaultReciboShouldNotBeFound("origem.in=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllRecibosByOrigemIsNullOrNotNull() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where origem is not null
        defaultReciboShouldBeFound("origem.specified=true");

        // Get all the reciboList where origem is null
        defaultReciboShouldNotBeFound("origem.specified=false");
    }

    @Test
    @Transactional
    void getAllRecibosByOrigemContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where origem contains DEFAULT_ORIGEM
        defaultReciboShouldBeFound("origem.contains=" + DEFAULT_ORIGEM);

        // Get all the reciboList where origem contains UPDATED_ORIGEM
        defaultReciboShouldNotBeFound("origem.contains=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllRecibosByOrigemNotContainsSomething() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        // Get all the reciboList where origem does not contain DEFAULT_ORIGEM
        defaultReciboShouldNotBeFound("origem.doesNotContain=" + DEFAULT_ORIGEM);

        // Get all the reciboList where origem does not contain UPDATED_ORIGEM
        defaultReciboShouldBeFound("origem.doesNotContain=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllRecibosByAplicacoesReciboIsEqualToSomething() throws Exception {
        AplicacaoRecibo aplicacoesRecibo;
        if (TestUtil.findAll(em, AplicacaoRecibo.class).isEmpty()) {
            reciboRepository.saveAndFlush(recibo);
            aplicacoesRecibo = AplicacaoReciboResourceIT.createEntity(em);
        } else {
            aplicacoesRecibo = TestUtil.findAll(em, AplicacaoRecibo.class).get(0);
        }
        em.persist(aplicacoesRecibo);
        em.flush();
        recibo.addAplicacoesRecibo(aplicacoesRecibo);
        reciboRepository.saveAndFlush(recibo);
        Long aplicacoesReciboId = aplicacoesRecibo.getId();

        // Get all the reciboList where aplicacoesRecibo equals to aplicacoesReciboId
        defaultReciboShouldBeFound("aplicacoesReciboId.equals=" + aplicacoesReciboId);

        // Get all the reciboList where aplicacoesRecibo equals to (aplicacoesReciboId + 1)
        defaultReciboShouldNotBeFound("aplicacoesReciboId.equals=" + (aplicacoesReciboId + 1));
    }

    @Test
    @Transactional
    void getAllRecibosByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            reciboRepository.saveAndFlush(recibo);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        recibo.addAnoLectivo(anoLectivo);
        reciboRepository.saveAndFlush(recibo);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the reciboList where anoLectivo equals to anoLectivoId
        defaultReciboShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the reciboList where anoLectivo equals to (anoLectivoId + 1)
        defaultReciboShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllRecibosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            reciboRepository.saveAndFlush(recibo);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        recibo.setUtilizador(utilizador);
        reciboRepository.saveAndFlush(recibo);
        Long utilizadorId = utilizador.getId();

        // Get all the reciboList where utilizador equals to utilizadorId
        defaultReciboShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the reciboList where utilizador equals to (utilizadorId + 1)
        defaultReciboShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllRecibosByMatriculaIsEqualToSomething() throws Exception {
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            reciboRepository.saveAndFlush(recibo);
            matricula = MatriculaResourceIT.createEntity(em);
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(matricula);
        em.flush();
        recibo.setMatricula(matricula);
        reciboRepository.saveAndFlush(recibo);
        Long matriculaId = matricula.getId();

        // Get all the reciboList where matricula equals to matriculaId
        defaultReciboShouldBeFound("matriculaId.equals=" + matriculaId);

        // Get all the reciboList where matricula equals to (matriculaId + 1)
        defaultReciboShouldNotBeFound("matriculaId.equals=" + (matriculaId + 1));
    }

    @Test
    @Transactional
    void getAllRecibosByDocumentoComercialIsEqualToSomething() throws Exception {
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            reciboRepository.saveAndFlush(recibo);
            documentoComercial = DocumentoComercialResourceIT.createEntity(em);
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        em.persist(documentoComercial);
        em.flush();
        recibo.setDocumentoComercial(documentoComercial);
        reciboRepository.saveAndFlush(recibo);
        Long documentoComercialId = documentoComercial.getId();

        // Get all the reciboList where documentoComercial equals to documentoComercialId
        defaultReciboShouldBeFound("documentoComercialId.equals=" + documentoComercialId);

        // Get all the reciboList where documentoComercial equals to (documentoComercialId + 1)
        defaultReciboShouldNotBeFound("documentoComercialId.equals=" + (documentoComercialId + 1));
    }

    @Test
    @Transactional
    void getAllRecibosByTransacaoIsEqualToSomething() throws Exception {
        Transacao transacao;
        if (TestUtil.findAll(em, Transacao.class).isEmpty()) {
            reciboRepository.saveAndFlush(recibo);
            transacao = TransacaoResourceIT.createEntity(em);
        } else {
            transacao = TestUtil.findAll(em, Transacao.class).get(0);
        }
        em.persist(transacao);
        em.flush();
        recibo.setTransacao(transacao);
        reciboRepository.saveAndFlush(recibo);
        Long transacaoId = transacao.getId();

        // Get all the reciboList where transacao equals to transacaoId
        defaultReciboShouldBeFound("transacaoId.equals=" + transacaoId);

        // Get all the reciboList where transacao equals to (transacaoId + 1)
        defaultReciboShouldNotBeFound("transacaoId.equals=" + (transacaoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReciboShouldBeFound(String filter) throws Exception {
        restReciboMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recibo.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].vencimento").value(hasItem(DEFAULT_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].totalSemImposto").value(hasItem(sameNumber(DEFAULT_TOTAL_SEM_IMPOSTO))))
            .andExpect(jsonPath("$.[*].totalComImposto").value(hasItem(sameNumber(DEFAULT_TOTAL_COM_IMPOSTO))))
            .andExpect(jsonPath("$.[*].totalDescontoComercial").value(hasItem(sameNumber(DEFAULT_TOTAL_DESCONTO_COMERCIAL))))
            .andExpect(jsonPath("$.[*].totalDescontoFinanceiro").value(hasItem(sameNumber(DEFAULT_TOTAL_DESCONTO_FINANCEIRO))))
            .andExpect(jsonPath("$.[*].totalIVA").value(hasItem(sameNumber(DEFAULT_TOTAL_IVA))))
            .andExpect(jsonPath("$.[*].totalRetencao").value(hasItem(sameNumber(DEFAULT_TOTAL_RETENCAO))))
            .andExpect(jsonPath("$.[*].totalJuro").value(hasItem(sameNumber(DEFAULT_TOTAL_JURO))))
            .andExpect(jsonPath("$.[*].cambio").value(hasItem(sameNumber(DEFAULT_CAMBIO))))
            .andExpect(jsonPath("$.[*].totalMoedaEstrangeira").value(hasItem(sameNumber(DEFAULT_TOTAL_MOEDA_ESTRANGEIRA))))
            .andExpect(jsonPath("$.[*].totalPagar").value(hasItem(sameNumber(DEFAULT_TOTAL_PAGAR))))
            .andExpect(jsonPath("$.[*].totalPago").value(hasItem(sameNumber(DEFAULT_TOTAL_PAGO))))
            .andExpect(jsonPath("$.[*].totalFalta").value(hasItem(sameNumber(DEFAULT_TOTAL_FALTA))))
            .andExpect(jsonPath("$.[*].totalTroco").value(hasItem(sameNumber(DEFAULT_TOTAL_TROCO))))
            .andExpect(jsonPath("$.[*].isNovo").value(hasItem(DEFAULT_IS_NOVO.booleanValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].debito").value(hasItem(sameNumber(DEFAULT_DEBITO))))
            .andExpect(jsonPath("$.[*].credito").value(hasItem(sameNumber(DEFAULT_CREDITO))))
            .andExpect(jsonPath("$.[*].isFiscalizado").value(hasItem(DEFAULT_IS_FISCALIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].signText").value(hasItem(DEFAULT_SIGN_TEXT)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].hashShort").value(hasItem(DEFAULT_HASH_SHORT)))
            .andExpect(jsonPath("$.[*].hashControl").value(hasItem(DEFAULT_HASH_CONTROL)))
            .andExpect(jsonPath("$.[*].keyVersion").value(hasItem(DEFAULT_KEY_VERSION)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM)));

        // Check, that the count call also returns 1
        restReciboMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReciboShouldNotBeFound(String filter) throws Exception {
        restReciboMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReciboMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRecibo() throws Exception {
        // Get the recibo
        restReciboMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecibo() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        int databaseSizeBeforeUpdate = reciboRepository.findAll().size();

        // Update the recibo
        Recibo updatedRecibo = reciboRepository.findById(recibo.getId()).get();
        // Disconnect from session so that the updates on updatedRecibo are not directly saved in db
        em.detach(updatedRecibo);
        updatedRecibo
            .data(UPDATED_DATA)
            .vencimento(UPDATED_VENCIMENTO)
            .numero(UPDATED_NUMERO)
            .totalSemImposto(UPDATED_TOTAL_SEM_IMPOSTO)
            .totalComImposto(UPDATED_TOTAL_COM_IMPOSTO)
            .totalDescontoComercial(UPDATED_TOTAL_DESCONTO_COMERCIAL)
            .totalDescontoFinanceiro(UPDATED_TOTAL_DESCONTO_FINANCEIRO)
            .totalIVA(UPDATED_TOTAL_IVA)
            .totalRetencao(UPDATED_TOTAL_RETENCAO)
            .totalJuro(UPDATED_TOTAL_JURO)
            .cambio(UPDATED_CAMBIO)
            .totalMoedaEstrangeira(UPDATED_TOTAL_MOEDA_ESTRANGEIRA)
            .totalPagar(UPDATED_TOTAL_PAGAR)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalFalta(UPDATED_TOTAL_FALTA)
            .totalTroco(UPDATED_TOTAL_TROCO)
            .isNovo(UPDATED_IS_NOVO)
            .timestamp(UPDATED_TIMESTAMP)
            .descricao(UPDATED_DESCRICAO)
            .debito(UPDATED_DEBITO)
            .credito(UPDATED_CREDITO)
            .isFiscalizado(UPDATED_IS_FISCALIZADO)
            .signText(UPDATED_SIGN_TEXT)
            .hash(UPDATED_HASH)
            .hashShort(UPDATED_HASH_SHORT)
            .hashControl(UPDATED_HASH_CONTROL)
            .keyVersion(UPDATED_KEY_VERSION)
            .estado(UPDATED_ESTADO)
            .origem(UPDATED_ORIGEM);
        ReciboDTO reciboDTO = reciboMapper.toDto(updatedRecibo);

        restReciboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reciboDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reciboDTO))
            )
            .andExpect(status().isOk());

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
        Recibo testRecibo = reciboList.get(reciboList.size() - 1);
        assertThat(testRecibo.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testRecibo.getVencimento()).isEqualTo(UPDATED_VENCIMENTO);
        assertThat(testRecibo.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testRecibo.getTotalSemImposto()).isEqualByComparingTo(UPDATED_TOTAL_SEM_IMPOSTO);
        assertThat(testRecibo.getTotalComImposto()).isEqualByComparingTo(UPDATED_TOTAL_COM_IMPOSTO);
        assertThat(testRecibo.getTotalDescontoComercial()).isEqualByComparingTo(UPDATED_TOTAL_DESCONTO_COMERCIAL);
        assertThat(testRecibo.getTotalDescontoFinanceiro()).isEqualByComparingTo(UPDATED_TOTAL_DESCONTO_FINANCEIRO);
        assertThat(testRecibo.getTotalIVA()).isEqualByComparingTo(UPDATED_TOTAL_IVA);
        assertThat(testRecibo.getTotalRetencao()).isEqualByComparingTo(UPDATED_TOTAL_RETENCAO);
        assertThat(testRecibo.getTotalJuro()).isEqualByComparingTo(UPDATED_TOTAL_JURO);
        assertThat(testRecibo.getCambio()).isEqualByComparingTo(UPDATED_CAMBIO);
        assertThat(testRecibo.getTotalMoedaEstrangeira()).isEqualByComparingTo(UPDATED_TOTAL_MOEDA_ESTRANGEIRA);
        assertThat(testRecibo.getTotalPagar()).isEqualByComparingTo(UPDATED_TOTAL_PAGAR);
        assertThat(testRecibo.getTotalPago()).isEqualByComparingTo(UPDATED_TOTAL_PAGO);
        assertThat(testRecibo.getTotalFalta()).isEqualByComparingTo(UPDATED_TOTAL_FALTA);
        assertThat(testRecibo.getTotalTroco()).isEqualByComparingTo(UPDATED_TOTAL_TROCO);
        assertThat(testRecibo.getIsNovo()).isEqualTo(UPDATED_IS_NOVO);
        assertThat(testRecibo.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testRecibo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testRecibo.getDebito()).isEqualByComparingTo(UPDATED_DEBITO);
        assertThat(testRecibo.getCredito()).isEqualByComparingTo(UPDATED_CREDITO);
        assertThat(testRecibo.getIsFiscalizado()).isEqualTo(UPDATED_IS_FISCALIZADO);
        assertThat(testRecibo.getSignText()).isEqualTo(UPDATED_SIGN_TEXT);
        assertThat(testRecibo.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testRecibo.getHashShort()).isEqualTo(UPDATED_HASH_SHORT);
        assertThat(testRecibo.getHashControl()).isEqualTo(UPDATED_HASH_CONTROL);
        assertThat(testRecibo.getKeyVersion()).isEqualTo(UPDATED_KEY_VERSION);
        assertThat(testRecibo.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testRecibo.getOrigem()).isEqualTo(UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void putNonExistingRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().size();
        recibo.setId(count.incrementAndGet());

        // Create the Recibo
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReciboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reciboDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().size();
        recibo.setId(count.incrementAndGet());

        // Create the Recibo
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReciboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().size();
        recibo.setId(count.incrementAndGet());

        // Create the Recibo
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReciboMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reciboDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReciboWithPatch() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        int databaseSizeBeforeUpdate = reciboRepository.findAll().size();

        // Update the recibo using partial update
        Recibo partialUpdatedRecibo = new Recibo();
        partialUpdatedRecibo.setId(recibo.getId());

        partialUpdatedRecibo
            .data(UPDATED_DATA)
            .vencimento(UPDATED_VENCIMENTO)
            .numero(UPDATED_NUMERO)
            .totalRetencao(UPDATED_TOTAL_RETENCAO)
            .totalJuro(UPDATED_TOTAL_JURO)
            .totalMoedaEstrangeira(UPDATED_TOTAL_MOEDA_ESTRANGEIRA)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalFalta(UPDATED_TOTAL_FALTA)
            .totalTroco(UPDATED_TOTAL_TROCO)
            .isNovo(UPDATED_IS_NOVO)
            .timestamp(UPDATED_TIMESTAMP)
            .debito(UPDATED_DEBITO)
            .isFiscalizado(UPDATED_IS_FISCALIZADO)
            .signText(UPDATED_SIGN_TEXT)
            .keyVersion(UPDATED_KEY_VERSION)
            .origem(UPDATED_ORIGEM);

        restReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecibo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecibo))
            )
            .andExpect(status().isOk());

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
        Recibo testRecibo = reciboList.get(reciboList.size() - 1);
        assertThat(testRecibo.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testRecibo.getVencimento()).isEqualTo(UPDATED_VENCIMENTO);
        assertThat(testRecibo.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testRecibo.getTotalSemImposto()).isEqualByComparingTo(DEFAULT_TOTAL_SEM_IMPOSTO);
        assertThat(testRecibo.getTotalComImposto()).isEqualByComparingTo(DEFAULT_TOTAL_COM_IMPOSTO);
        assertThat(testRecibo.getTotalDescontoComercial()).isEqualByComparingTo(DEFAULT_TOTAL_DESCONTO_COMERCIAL);
        assertThat(testRecibo.getTotalDescontoFinanceiro()).isEqualByComparingTo(DEFAULT_TOTAL_DESCONTO_FINANCEIRO);
        assertThat(testRecibo.getTotalIVA()).isEqualByComparingTo(DEFAULT_TOTAL_IVA);
        assertThat(testRecibo.getTotalRetencao()).isEqualByComparingTo(UPDATED_TOTAL_RETENCAO);
        assertThat(testRecibo.getTotalJuro()).isEqualByComparingTo(UPDATED_TOTAL_JURO);
        assertThat(testRecibo.getCambio()).isEqualByComparingTo(DEFAULT_CAMBIO);
        assertThat(testRecibo.getTotalMoedaEstrangeira()).isEqualByComparingTo(UPDATED_TOTAL_MOEDA_ESTRANGEIRA);
        assertThat(testRecibo.getTotalPagar()).isEqualByComparingTo(DEFAULT_TOTAL_PAGAR);
        assertThat(testRecibo.getTotalPago()).isEqualByComparingTo(UPDATED_TOTAL_PAGO);
        assertThat(testRecibo.getTotalFalta()).isEqualByComparingTo(UPDATED_TOTAL_FALTA);
        assertThat(testRecibo.getTotalTroco()).isEqualByComparingTo(UPDATED_TOTAL_TROCO);
        assertThat(testRecibo.getIsNovo()).isEqualTo(UPDATED_IS_NOVO);
        assertThat(testRecibo.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testRecibo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testRecibo.getDebito()).isEqualByComparingTo(UPDATED_DEBITO);
        assertThat(testRecibo.getCredito()).isEqualByComparingTo(DEFAULT_CREDITO);
        assertThat(testRecibo.getIsFiscalizado()).isEqualTo(UPDATED_IS_FISCALIZADO);
        assertThat(testRecibo.getSignText()).isEqualTo(UPDATED_SIGN_TEXT);
        assertThat(testRecibo.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testRecibo.getHashShort()).isEqualTo(DEFAULT_HASH_SHORT);
        assertThat(testRecibo.getHashControl()).isEqualTo(DEFAULT_HASH_CONTROL);
        assertThat(testRecibo.getKeyVersion()).isEqualTo(UPDATED_KEY_VERSION);
        assertThat(testRecibo.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testRecibo.getOrigem()).isEqualTo(UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void fullUpdateReciboWithPatch() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        int databaseSizeBeforeUpdate = reciboRepository.findAll().size();

        // Update the recibo using partial update
        Recibo partialUpdatedRecibo = new Recibo();
        partialUpdatedRecibo.setId(recibo.getId());

        partialUpdatedRecibo
            .data(UPDATED_DATA)
            .vencimento(UPDATED_VENCIMENTO)
            .numero(UPDATED_NUMERO)
            .totalSemImposto(UPDATED_TOTAL_SEM_IMPOSTO)
            .totalComImposto(UPDATED_TOTAL_COM_IMPOSTO)
            .totalDescontoComercial(UPDATED_TOTAL_DESCONTO_COMERCIAL)
            .totalDescontoFinanceiro(UPDATED_TOTAL_DESCONTO_FINANCEIRO)
            .totalIVA(UPDATED_TOTAL_IVA)
            .totalRetencao(UPDATED_TOTAL_RETENCAO)
            .totalJuro(UPDATED_TOTAL_JURO)
            .cambio(UPDATED_CAMBIO)
            .totalMoedaEstrangeira(UPDATED_TOTAL_MOEDA_ESTRANGEIRA)
            .totalPagar(UPDATED_TOTAL_PAGAR)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalFalta(UPDATED_TOTAL_FALTA)
            .totalTroco(UPDATED_TOTAL_TROCO)
            .isNovo(UPDATED_IS_NOVO)
            .timestamp(UPDATED_TIMESTAMP)
            .descricao(UPDATED_DESCRICAO)
            .debito(UPDATED_DEBITO)
            .credito(UPDATED_CREDITO)
            .isFiscalizado(UPDATED_IS_FISCALIZADO)
            .signText(UPDATED_SIGN_TEXT)
            .hash(UPDATED_HASH)
            .hashShort(UPDATED_HASH_SHORT)
            .hashControl(UPDATED_HASH_CONTROL)
            .keyVersion(UPDATED_KEY_VERSION)
            .estado(UPDATED_ESTADO)
            .origem(UPDATED_ORIGEM);

        restReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecibo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecibo))
            )
            .andExpect(status().isOk());

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
        Recibo testRecibo = reciboList.get(reciboList.size() - 1);
        assertThat(testRecibo.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testRecibo.getVencimento()).isEqualTo(UPDATED_VENCIMENTO);
        assertThat(testRecibo.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testRecibo.getTotalSemImposto()).isEqualByComparingTo(UPDATED_TOTAL_SEM_IMPOSTO);
        assertThat(testRecibo.getTotalComImposto()).isEqualByComparingTo(UPDATED_TOTAL_COM_IMPOSTO);
        assertThat(testRecibo.getTotalDescontoComercial()).isEqualByComparingTo(UPDATED_TOTAL_DESCONTO_COMERCIAL);
        assertThat(testRecibo.getTotalDescontoFinanceiro()).isEqualByComparingTo(UPDATED_TOTAL_DESCONTO_FINANCEIRO);
        assertThat(testRecibo.getTotalIVA()).isEqualByComparingTo(UPDATED_TOTAL_IVA);
        assertThat(testRecibo.getTotalRetencao()).isEqualByComparingTo(UPDATED_TOTAL_RETENCAO);
        assertThat(testRecibo.getTotalJuro()).isEqualByComparingTo(UPDATED_TOTAL_JURO);
        assertThat(testRecibo.getCambio()).isEqualByComparingTo(UPDATED_CAMBIO);
        assertThat(testRecibo.getTotalMoedaEstrangeira()).isEqualByComparingTo(UPDATED_TOTAL_MOEDA_ESTRANGEIRA);
        assertThat(testRecibo.getTotalPagar()).isEqualByComparingTo(UPDATED_TOTAL_PAGAR);
        assertThat(testRecibo.getTotalPago()).isEqualByComparingTo(UPDATED_TOTAL_PAGO);
        assertThat(testRecibo.getTotalFalta()).isEqualByComparingTo(UPDATED_TOTAL_FALTA);
        assertThat(testRecibo.getTotalTroco()).isEqualByComparingTo(UPDATED_TOTAL_TROCO);
        assertThat(testRecibo.getIsNovo()).isEqualTo(UPDATED_IS_NOVO);
        assertThat(testRecibo.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testRecibo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testRecibo.getDebito()).isEqualByComparingTo(UPDATED_DEBITO);
        assertThat(testRecibo.getCredito()).isEqualByComparingTo(UPDATED_CREDITO);
        assertThat(testRecibo.getIsFiscalizado()).isEqualTo(UPDATED_IS_FISCALIZADO);
        assertThat(testRecibo.getSignText()).isEqualTo(UPDATED_SIGN_TEXT);
        assertThat(testRecibo.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testRecibo.getHashShort()).isEqualTo(UPDATED_HASH_SHORT);
        assertThat(testRecibo.getHashControl()).isEqualTo(UPDATED_HASH_CONTROL);
        assertThat(testRecibo.getKeyVersion()).isEqualTo(UPDATED_KEY_VERSION);
        assertThat(testRecibo.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testRecibo.getOrigem()).isEqualTo(UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void patchNonExistingRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().size();
        recibo.setId(count.incrementAndGet());

        // Create the Recibo
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reciboDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().size();
        recibo.setId(count.incrementAndGet());

        // Create the Recibo
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecibo() throws Exception {
        int databaseSizeBeforeUpdate = reciboRepository.findAll().size();
        recibo.setId(count.incrementAndGet());

        // Create the Recibo
        ReciboDTO reciboDTO = reciboMapper.toDto(recibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReciboMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(reciboDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recibo in the database
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecibo() throws Exception {
        // Initialize the database
        reciboRepository.saveAndFlush(recibo);

        int databaseSizeBeforeDelete = reciboRepository.findAll().size();

        // Delete the recibo
        restReciboMockMvc
            .perform(delete(ENTITY_API_URL_ID, recibo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recibo> reciboList = reciboRepository.findAll();
        assertThat(reciboList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
