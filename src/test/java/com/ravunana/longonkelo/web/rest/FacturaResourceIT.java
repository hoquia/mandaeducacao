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
import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.domain.ItemFactura;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.ResumoImpostoFactura;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.domain.enumeration.EstadoDocumentoComercial;
import com.ravunana.longonkelo.repository.FacturaRepository;
import com.ravunana.longonkelo.service.FacturaService;
import com.ravunana.longonkelo.service.criteria.FacturaCriteria;
import com.ravunana.longonkelo.service.dto.FacturaDTO;
import com.ravunana.longonkelo.service.mapper.FacturaMapper;
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
 * Integration tests for the {@link FacturaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FacturaResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_ENTREGA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ENTREGA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_EMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_EMISSAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_EMISSAO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATA_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_VENCIMENTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CAE = "AAAAAAAAAA";
    private static final String UPDATED_CAE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_INICIO_TRANSPORTE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INICIO_TRANSPORTE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_INICIO_TRANSPORTE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_FIM_TRANSPORTE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FIM_TRANSPORTE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FIM_TRANSPORTE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_OBSERVACAO_GERAL = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO_GERAL = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO_INTERNA = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO_INTERNA = "BBBBBBBBBB";

    private static final EstadoDocumentoComercial DEFAULT_ESTADO = EstadoDocumentoComercial.P;
    private static final EstadoDocumentoComercial UPDATED_ESTADO = EstadoDocumentoComercial.N;

    private static final String DEFAULT_ORIGEM = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEM = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_IS_MOEDA_ENTRANGEIRA = false;
    private static final Boolean UPDATED_IS_MOEDA_ENTRANGEIRA = true;

    private static final String DEFAULT_MOEDA = "AAAAAAAAAA";
    private static final String UPDATED_MOEDA = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CAMBIO = new BigDecimal(0);
    private static final BigDecimal UPDATED_CAMBIO = new BigDecimal(1);
    private static final BigDecimal SMALLER_CAMBIO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_MOEDA_ENTRANGEIRA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_MOEDA_ENTRANGEIRA = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_MOEDA_ENTRANGEIRA = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_ILIQUIDO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_ILIQUIDO = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_ILIQUIDO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_DESCONTO_COMERCIAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_DESCONTO_COMERCIAL = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_DESCONTO_COMERCIAL = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_LIQUIDO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_LIQUIDO = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_LIQUIDO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_IMPOSTO_IVA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_IMPOSTO_IVA = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_IMPOSTO_IVA = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_IMPOSTO_ESPECIAL_CONSUMO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_DESCONTO_FINANCEIRO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_DESCONTO_FINANCEIRO = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_DESCONTO_FINANCEIRO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_FACTURA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_FACTURA = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_FACTURA = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_IMPOSTO_RETENCAO_FONTE = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PAGAR = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_PAGAR = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_PAGAR = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_DEBITO = new BigDecimal(0);
    private static final BigDecimal UPDATED_DEBITO = new BigDecimal(1);
    private static final BigDecimal SMALLER_DEBITO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_CREDITO = new BigDecimal(0);
    private static final BigDecimal UPDATED_CREDITO = new BigDecimal(1);
    private static final BigDecimal SMALLER_CREDITO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PAGO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_PAGO = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_PAGO = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_DIFERENCA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_DIFERENCA = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_DIFERENCA = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_IS_AUTO_FACTURACAO = false;
    private static final Boolean UPDATED_IS_AUTO_FACTURACAO = true;

    private static final Boolean DEFAULT_IS_REGIME_CAIXA = false;
    private static final Boolean UPDATED_IS_REGIME_CAIXA = true;

    private static final Boolean DEFAULT_IS_EMITIDA_NOME_E_CONTA_TERCEIRO = false;
    private static final Boolean UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO = true;

    private static final Boolean DEFAULT_IS_NOVO = false;
    private static final Boolean UPDATED_IS_NOVO = true;

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

    private static final String ENTITY_API_URL = "/api/facturas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FacturaRepository facturaRepository;

    @Mock
    private FacturaRepository facturaRepositoryMock;

    @Autowired
    private FacturaMapper facturaMapper;

    @Mock
    private FacturaService facturaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacturaMockMvc;

    private Factura factura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factura createEntity(EntityManager em) {
        Factura factura = new Factura()
            .numero(DEFAULT_NUMERO)
            .codigoEntrega(DEFAULT_CODIGO_ENTREGA)
            .dataEmissao(DEFAULT_DATA_EMISSAO)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .cae(DEFAULT_CAE)
            .inicioTransporte(DEFAULT_INICIO_TRANSPORTE)
            .fimTransporte(DEFAULT_FIM_TRANSPORTE)
            .observacaoGeral(DEFAULT_OBSERVACAO_GERAL)
            .observacaoInterna(DEFAULT_OBSERVACAO_INTERNA)
            .estado(DEFAULT_ESTADO)
            .origem(DEFAULT_ORIGEM)
            .timestamp(DEFAULT_TIMESTAMP)
            .isMoedaEntrangeira(DEFAULT_IS_MOEDA_ENTRANGEIRA)
            .moeda(DEFAULT_MOEDA)
            .cambio(DEFAULT_CAMBIO)
            .totalMoedaEntrangeira(DEFAULT_TOTAL_MOEDA_ENTRANGEIRA)
            .totalIliquido(DEFAULT_TOTAL_ILIQUIDO)
            .totalDescontoComercial(DEFAULT_TOTAL_DESCONTO_COMERCIAL)
            .totalLiquido(DEFAULT_TOTAL_LIQUIDO)
            .totalImpostoIVA(DEFAULT_TOTAL_IMPOSTO_IVA)
            .totalImpostoEspecialConsumo(DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO)
            .totalDescontoFinanceiro(DEFAULT_TOTAL_DESCONTO_FINANCEIRO)
            .totalFactura(DEFAULT_TOTAL_FACTURA)
            .totalImpostoRetencaoFonte(DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE)
            .totalPagar(DEFAULT_TOTAL_PAGAR)
            .debito(DEFAULT_DEBITO)
            .credito(DEFAULT_CREDITO)
            .totalPago(DEFAULT_TOTAL_PAGO)
            .totalDiferenca(DEFAULT_TOTAL_DIFERENCA)
            .isAutoFacturacao(DEFAULT_IS_AUTO_FACTURACAO)
            .isRegimeCaixa(DEFAULT_IS_REGIME_CAIXA)
            .isEmitidaNomeEContaTerceiro(DEFAULT_IS_EMITIDA_NOME_E_CONTA_TERCEIRO)
            .isNovo(DEFAULT_IS_NOVO)
            .isFiscalizado(DEFAULT_IS_FISCALIZADO)
            .signText(DEFAULT_SIGN_TEXT)
            .hash(DEFAULT_HASH)
            .hashShort(DEFAULT_HASH_SHORT)
            .hashControl(DEFAULT_HASH_CONTROL)
            .keyVersion(DEFAULT_KEY_VERSION);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        factura.setMatricula(matricula);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        factura.setDocumentoComercial(documentoComercial);
        return factura;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factura createUpdatedEntity(EntityManager em) {
        Factura factura = new Factura()
            .numero(UPDATED_NUMERO)
            .codigoEntrega(UPDATED_CODIGO_ENTREGA)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .cae(UPDATED_CAE)
            .inicioTransporte(UPDATED_INICIO_TRANSPORTE)
            .fimTransporte(UPDATED_FIM_TRANSPORTE)
            .observacaoGeral(UPDATED_OBSERVACAO_GERAL)
            .observacaoInterna(UPDATED_OBSERVACAO_INTERNA)
            .estado(UPDATED_ESTADO)
            .origem(UPDATED_ORIGEM)
            .timestamp(UPDATED_TIMESTAMP)
            .isMoedaEntrangeira(UPDATED_IS_MOEDA_ENTRANGEIRA)
            .moeda(UPDATED_MOEDA)
            .cambio(UPDATED_CAMBIO)
            .totalMoedaEntrangeira(UPDATED_TOTAL_MOEDA_ENTRANGEIRA)
            .totalIliquido(UPDATED_TOTAL_ILIQUIDO)
            .totalDescontoComercial(UPDATED_TOTAL_DESCONTO_COMERCIAL)
            .totalLiquido(UPDATED_TOTAL_LIQUIDO)
            .totalImpostoIVA(UPDATED_TOTAL_IMPOSTO_IVA)
            .totalImpostoEspecialConsumo(UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO)
            .totalDescontoFinanceiro(UPDATED_TOTAL_DESCONTO_FINANCEIRO)
            .totalFactura(UPDATED_TOTAL_FACTURA)
            .totalImpostoRetencaoFonte(UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE)
            .totalPagar(UPDATED_TOTAL_PAGAR)
            .debito(UPDATED_DEBITO)
            .credito(UPDATED_CREDITO)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalDiferenca(UPDATED_TOTAL_DIFERENCA)
            .isAutoFacturacao(UPDATED_IS_AUTO_FACTURACAO)
            .isRegimeCaixa(UPDATED_IS_REGIME_CAIXA)
            .isEmitidaNomeEContaTerceiro(UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO)
            .isNovo(UPDATED_IS_NOVO)
            .isFiscalizado(UPDATED_IS_FISCALIZADO)
            .signText(UPDATED_SIGN_TEXT)
            .hash(UPDATED_HASH)
            .hashShort(UPDATED_HASH_SHORT)
            .hashControl(UPDATED_HASH_CONTROL)
            .keyVersion(UPDATED_KEY_VERSION);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createUpdatedEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        factura.setMatricula(matricula);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createUpdatedEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        factura.setDocumentoComercial(documentoComercial);
        return factura;
    }

    @BeforeEach
    public void initTest() {
        factura = createEntity(em);
    }

    @Test
    @Transactional
    void createFactura() throws Exception {
        int databaseSizeBeforeCreate = facturaRepository.findAll().size();
        // Create the Factura
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);
        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isCreated());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeCreate + 1);
        Factura testFactura = facturaList.get(facturaList.size() - 1);
        assertThat(testFactura.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testFactura.getCodigoEntrega()).isEqualTo(DEFAULT_CODIGO_ENTREGA);
        assertThat(testFactura.getDataEmissao()).isEqualTo(DEFAULT_DATA_EMISSAO);
        assertThat(testFactura.getDataVencimento()).isEqualTo(DEFAULT_DATA_VENCIMENTO);
        assertThat(testFactura.getCae()).isEqualTo(DEFAULT_CAE);
        assertThat(testFactura.getInicioTransporte()).isEqualTo(DEFAULT_INICIO_TRANSPORTE);
        assertThat(testFactura.getFimTransporte()).isEqualTo(DEFAULT_FIM_TRANSPORTE);
        assertThat(testFactura.getObservacaoGeral()).isEqualTo(DEFAULT_OBSERVACAO_GERAL);
        assertThat(testFactura.getObservacaoInterna()).isEqualTo(DEFAULT_OBSERVACAO_INTERNA);
        assertThat(testFactura.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testFactura.getOrigem()).isEqualTo(DEFAULT_ORIGEM);
        assertThat(testFactura.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testFactura.getIsMoedaEntrangeira()).isEqualTo(DEFAULT_IS_MOEDA_ENTRANGEIRA);
        assertThat(testFactura.getMoeda()).isEqualTo(DEFAULT_MOEDA);
        assertThat(testFactura.getCambio()).isEqualByComparingTo(DEFAULT_CAMBIO);
        assertThat(testFactura.getTotalMoedaEntrangeira()).isEqualByComparingTo(DEFAULT_TOTAL_MOEDA_ENTRANGEIRA);
        assertThat(testFactura.getTotalIliquido()).isEqualByComparingTo(DEFAULT_TOTAL_ILIQUIDO);
        assertThat(testFactura.getTotalDescontoComercial()).isEqualByComparingTo(DEFAULT_TOTAL_DESCONTO_COMERCIAL);
        assertThat(testFactura.getTotalLiquido()).isEqualByComparingTo(DEFAULT_TOTAL_LIQUIDO);
        assertThat(testFactura.getTotalImpostoIVA()).isEqualByComparingTo(DEFAULT_TOTAL_IMPOSTO_IVA);
        assertThat(testFactura.getTotalImpostoEspecialConsumo()).isEqualByComparingTo(DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);
        assertThat(testFactura.getTotalDescontoFinanceiro()).isEqualByComparingTo(DEFAULT_TOTAL_DESCONTO_FINANCEIRO);
        assertThat(testFactura.getTotalFactura()).isEqualByComparingTo(DEFAULT_TOTAL_FACTURA);
        assertThat(testFactura.getTotalImpostoRetencaoFonte()).isEqualByComparingTo(DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE);
        assertThat(testFactura.getTotalPagar()).isEqualByComparingTo(DEFAULT_TOTAL_PAGAR);
        assertThat(testFactura.getDebito()).isEqualByComparingTo(DEFAULT_DEBITO);
        assertThat(testFactura.getCredito()).isEqualByComparingTo(DEFAULT_CREDITO);
        assertThat(testFactura.getTotalPago()).isEqualByComparingTo(DEFAULT_TOTAL_PAGO);
        assertThat(testFactura.getTotalDiferenca()).isEqualByComparingTo(DEFAULT_TOTAL_DIFERENCA);
        assertThat(testFactura.getIsAutoFacturacao()).isEqualTo(DEFAULT_IS_AUTO_FACTURACAO);
        assertThat(testFactura.getIsRegimeCaixa()).isEqualTo(DEFAULT_IS_REGIME_CAIXA);
        assertThat(testFactura.getIsEmitidaNomeEContaTerceiro()).isEqualTo(DEFAULT_IS_EMITIDA_NOME_E_CONTA_TERCEIRO);
        assertThat(testFactura.getIsNovo()).isEqualTo(DEFAULT_IS_NOVO);
        assertThat(testFactura.getIsFiscalizado()).isEqualTo(DEFAULT_IS_FISCALIZADO);
        assertThat(testFactura.getSignText()).isEqualTo(DEFAULT_SIGN_TEXT);
        assertThat(testFactura.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testFactura.getHashShort()).isEqualTo(DEFAULT_HASH_SHORT);
        assertThat(testFactura.getHashControl()).isEqualTo(DEFAULT_HASH_CONTROL);
        assertThat(testFactura.getKeyVersion()).isEqualTo(DEFAULT_KEY_VERSION);
    }

    @Test
    @Transactional
    void createFacturaWithExistingId() throws Exception {
        // Create the Factura with an existing ID
        factura.setId(1L);
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        int databaseSizeBeforeCreate = facturaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setNumero(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataEmissaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setDataEmissao(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataVencimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setDataVencimento(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInicioTransporteIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setInicioTransporte(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFimTransporteIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setFimTransporte(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setEstado(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrigemIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setOrigem(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTimestamp(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoedaIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setMoeda(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalIliquidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotalIliquido(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDescontoComercialIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotalDescontoComercial(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalLiquidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotalLiquido(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalImpostoIVAIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotalImpostoIVA(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalImpostoEspecialConsumoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotalImpostoEspecialConsumo(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDescontoFinanceiroIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotalDescontoFinanceiro(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalFacturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotalFactura(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalImpostoRetencaoFonteIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotalImpostoRetencaoFonte(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalPagarIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotalPagar(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotalPago(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDiferencaIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setTotalDiferenca(null);

        // Create the Factura, which fails.
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        restFacturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isBadRequest());

        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFacturas() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList
        restFacturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factura.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].codigoEntrega").value(hasItem(DEFAULT_CODIGO_ENTREGA)))
            .andExpect(jsonPath("$.[*].dataEmissao").value(hasItem(DEFAULT_DATA_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].cae").value(hasItem(DEFAULT_CAE)))
            .andExpect(jsonPath("$.[*].inicioTransporte").value(hasItem(sameInstant(DEFAULT_INICIO_TRANSPORTE))))
            .andExpect(jsonPath("$.[*].fimTransporte").value(hasItem(sameInstant(DEFAULT_FIM_TRANSPORTE))))
            .andExpect(jsonPath("$.[*].observacaoGeral").value(hasItem(DEFAULT_OBSERVACAO_GERAL.toString())))
            .andExpect(jsonPath("$.[*].observacaoInterna").value(hasItem(DEFAULT_OBSERVACAO_INTERNA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].isMoedaEntrangeira").value(hasItem(DEFAULT_IS_MOEDA_ENTRANGEIRA.booleanValue())))
            .andExpect(jsonPath("$.[*].moeda").value(hasItem(DEFAULT_MOEDA)))
            .andExpect(jsonPath("$.[*].cambio").value(hasItem(sameNumber(DEFAULT_CAMBIO))))
            .andExpect(jsonPath("$.[*].totalMoedaEntrangeira").value(hasItem(sameNumber(DEFAULT_TOTAL_MOEDA_ENTRANGEIRA))))
            .andExpect(jsonPath("$.[*].totalIliquido").value(hasItem(sameNumber(DEFAULT_TOTAL_ILIQUIDO))))
            .andExpect(jsonPath("$.[*].totalDescontoComercial").value(hasItem(sameNumber(DEFAULT_TOTAL_DESCONTO_COMERCIAL))))
            .andExpect(jsonPath("$.[*].totalLiquido").value(hasItem(sameNumber(DEFAULT_TOTAL_LIQUIDO))))
            .andExpect(jsonPath("$.[*].totalImpostoIVA").value(hasItem(sameNumber(DEFAULT_TOTAL_IMPOSTO_IVA))))
            .andExpect(jsonPath("$.[*].totalImpostoEspecialConsumo").value(hasItem(sameNumber(DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO))))
            .andExpect(jsonPath("$.[*].totalDescontoFinanceiro").value(hasItem(sameNumber(DEFAULT_TOTAL_DESCONTO_FINANCEIRO))))
            .andExpect(jsonPath("$.[*].totalFactura").value(hasItem(sameNumber(DEFAULT_TOTAL_FACTURA))))
            .andExpect(jsonPath("$.[*].totalImpostoRetencaoFonte").value(hasItem(sameNumber(DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE))))
            .andExpect(jsonPath("$.[*].totalPagar").value(hasItem(sameNumber(DEFAULT_TOTAL_PAGAR))))
            .andExpect(jsonPath("$.[*].debito").value(hasItem(sameNumber(DEFAULT_DEBITO))))
            .andExpect(jsonPath("$.[*].credito").value(hasItem(sameNumber(DEFAULT_CREDITO))))
            .andExpect(jsonPath("$.[*].totalPago").value(hasItem(sameNumber(DEFAULT_TOTAL_PAGO))))
            .andExpect(jsonPath("$.[*].totalDiferenca").value(hasItem(sameNumber(DEFAULT_TOTAL_DIFERENCA))))
            .andExpect(jsonPath("$.[*].isAutoFacturacao").value(hasItem(DEFAULT_IS_AUTO_FACTURACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].isRegimeCaixa").value(hasItem(DEFAULT_IS_REGIME_CAIXA.booleanValue())))
            .andExpect(
                jsonPath("$.[*].isEmitidaNomeEContaTerceiro").value(hasItem(DEFAULT_IS_EMITIDA_NOME_E_CONTA_TERCEIRO.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].isNovo").value(hasItem(DEFAULT_IS_NOVO.booleanValue())))
            .andExpect(jsonPath("$.[*].isFiscalizado").value(hasItem(DEFAULT_IS_FISCALIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].signText").value(hasItem(DEFAULT_SIGN_TEXT)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].hashShort").value(hasItem(DEFAULT_HASH_SHORT)))
            .andExpect(jsonPath("$.[*].hashControl").value(hasItem(DEFAULT_HASH_CONTROL)))
            .andExpect(jsonPath("$.[*].keyVersion").value(hasItem(DEFAULT_KEY_VERSION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFacturasWithEagerRelationshipsIsEnabled() throws Exception {
        when(facturaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFacturaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(facturaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFacturasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(facturaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFacturaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(facturaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFactura() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get the factura
        restFacturaMockMvc
            .perform(get(ENTITY_API_URL_ID, factura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factura.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.codigoEntrega").value(DEFAULT_CODIGO_ENTREGA))
            .andExpect(jsonPath("$.dataEmissao").value(DEFAULT_DATA_EMISSAO.toString()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.cae").value(DEFAULT_CAE))
            .andExpect(jsonPath("$.inicioTransporte").value(sameInstant(DEFAULT_INICIO_TRANSPORTE)))
            .andExpect(jsonPath("$.fimTransporte").value(sameInstant(DEFAULT_FIM_TRANSPORTE)))
            .andExpect(jsonPath("$.observacaoGeral").value(DEFAULT_OBSERVACAO_GERAL.toString()))
            .andExpect(jsonPath("$.observacaoInterna").value(DEFAULT_OBSERVACAO_INTERNA.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.origem").value(DEFAULT_ORIGEM))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.isMoedaEntrangeira").value(DEFAULT_IS_MOEDA_ENTRANGEIRA.booleanValue()))
            .andExpect(jsonPath("$.moeda").value(DEFAULT_MOEDA))
            .andExpect(jsonPath("$.cambio").value(sameNumber(DEFAULT_CAMBIO)))
            .andExpect(jsonPath("$.totalMoedaEntrangeira").value(sameNumber(DEFAULT_TOTAL_MOEDA_ENTRANGEIRA)))
            .andExpect(jsonPath("$.totalIliquido").value(sameNumber(DEFAULT_TOTAL_ILIQUIDO)))
            .andExpect(jsonPath("$.totalDescontoComercial").value(sameNumber(DEFAULT_TOTAL_DESCONTO_COMERCIAL)))
            .andExpect(jsonPath("$.totalLiquido").value(sameNumber(DEFAULT_TOTAL_LIQUIDO)))
            .andExpect(jsonPath("$.totalImpostoIVA").value(sameNumber(DEFAULT_TOTAL_IMPOSTO_IVA)))
            .andExpect(jsonPath("$.totalImpostoEspecialConsumo").value(sameNumber(DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO)))
            .andExpect(jsonPath("$.totalDescontoFinanceiro").value(sameNumber(DEFAULT_TOTAL_DESCONTO_FINANCEIRO)))
            .andExpect(jsonPath("$.totalFactura").value(sameNumber(DEFAULT_TOTAL_FACTURA)))
            .andExpect(jsonPath("$.totalImpostoRetencaoFonte").value(sameNumber(DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE)))
            .andExpect(jsonPath("$.totalPagar").value(sameNumber(DEFAULT_TOTAL_PAGAR)))
            .andExpect(jsonPath("$.debito").value(sameNumber(DEFAULT_DEBITO)))
            .andExpect(jsonPath("$.credito").value(sameNumber(DEFAULT_CREDITO)))
            .andExpect(jsonPath("$.totalPago").value(sameNumber(DEFAULT_TOTAL_PAGO)))
            .andExpect(jsonPath("$.totalDiferenca").value(sameNumber(DEFAULT_TOTAL_DIFERENCA)))
            .andExpect(jsonPath("$.isAutoFacturacao").value(DEFAULT_IS_AUTO_FACTURACAO.booleanValue()))
            .andExpect(jsonPath("$.isRegimeCaixa").value(DEFAULT_IS_REGIME_CAIXA.booleanValue()))
            .andExpect(jsonPath("$.isEmitidaNomeEContaTerceiro").value(DEFAULT_IS_EMITIDA_NOME_E_CONTA_TERCEIRO.booleanValue()))
            .andExpect(jsonPath("$.isNovo").value(DEFAULT_IS_NOVO.booleanValue()))
            .andExpect(jsonPath("$.isFiscalizado").value(DEFAULT_IS_FISCALIZADO.booleanValue()))
            .andExpect(jsonPath("$.signText").value(DEFAULT_SIGN_TEXT))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.hashShort").value(DEFAULT_HASH_SHORT))
            .andExpect(jsonPath("$.hashControl").value(DEFAULT_HASH_CONTROL))
            .andExpect(jsonPath("$.keyVersion").value(DEFAULT_KEY_VERSION));
    }

    @Test
    @Transactional
    void getFacturasByIdFiltering() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        Long id = factura.getId();

        defaultFacturaShouldBeFound("id.equals=" + id);
        defaultFacturaShouldNotBeFound("id.notEquals=" + id);

        defaultFacturaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFacturaShouldNotBeFound("id.greaterThan=" + id);

        defaultFacturaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFacturaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFacturasByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where numero equals to DEFAULT_NUMERO
        defaultFacturaShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the facturaList where numero equals to UPDATED_NUMERO
        defaultFacturaShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllFacturasByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultFacturaShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the facturaList where numero equals to UPDATED_NUMERO
        defaultFacturaShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllFacturasByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where numero is not null
        defaultFacturaShouldBeFound("numero.specified=true");

        // Get all the facturaList where numero is null
        defaultFacturaShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByNumeroContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where numero contains DEFAULT_NUMERO
        defaultFacturaShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the facturaList where numero contains UPDATED_NUMERO
        defaultFacturaShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllFacturasByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where numero does not contain DEFAULT_NUMERO
        defaultFacturaShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the facturaList where numero does not contain UPDATED_NUMERO
        defaultFacturaShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllFacturasByCodigoEntregaIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where codigoEntrega equals to DEFAULT_CODIGO_ENTREGA
        defaultFacturaShouldBeFound("codigoEntrega.equals=" + DEFAULT_CODIGO_ENTREGA);

        // Get all the facturaList where codigoEntrega equals to UPDATED_CODIGO_ENTREGA
        defaultFacturaShouldNotBeFound("codigoEntrega.equals=" + UPDATED_CODIGO_ENTREGA);
    }

    @Test
    @Transactional
    void getAllFacturasByCodigoEntregaIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where codigoEntrega in DEFAULT_CODIGO_ENTREGA or UPDATED_CODIGO_ENTREGA
        defaultFacturaShouldBeFound("codigoEntrega.in=" + DEFAULT_CODIGO_ENTREGA + "," + UPDATED_CODIGO_ENTREGA);

        // Get all the facturaList where codigoEntrega equals to UPDATED_CODIGO_ENTREGA
        defaultFacturaShouldNotBeFound("codigoEntrega.in=" + UPDATED_CODIGO_ENTREGA);
    }

    @Test
    @Transactional
    void getAllFacturasByCodigoEntregaIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where codigoEntrega is not null
        defaultFacturaShouldBeFound("codigoEntrega.specified=true");

        // Get all the facturaList where codigoEntrega is null
        defaultFacturaShouldNotBeFound("codigoEntrega.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByCodigoEntregaContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where codigoEntrega contains DEFAULT_CODIGO_ENTREGA
        defaultFacturaShouldBeFound("codigoEntrega.contains=" + DEFAULT_CODIGO_ENTREGA);

        // Get all the facturaList where codigoEntrega contains UPDATED_CODIGO_ENTREGA
        defaultFacturaShouldNotBeFound("codigoEntrega.contains=" + UPDATED_CODIGO_ENTREGA);
    }

    @Test
    @Transactional
    void getAllFacturasByCodigoEntregaNotContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where codigoEntrega does not contain DEFAULT_CODIGO_ENTREGA
        defaultFacturaShouldNotBeFound("codigoEntrega.doesNotContain=" + DEFAULT_CODIGO_ENTREGA);

        // Get all the facturaList where codigoEntrega does not contain UPDATED_CODIGO_ENTREGA
        defaultFacturaShouldBeFound("codigoEntrega.doesNotContain=" + UPDATED_CODIGO_ENTREGA);
    }

    @Test
    @Transactional
    void getAllFacturasByDataEmissaoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataEmissao equals to DEFAULT_DATA_EMISSAO
        defaultFacturaShouldBeFound("dataEmissao.equals=" + DEFAULT_DATA_EMISSAO);

        // Get all the facturaList where dataEmissao equals to UPDATED_DATA_EMISSAO
        defaultFacturaShouldNotBeFound("dataEmissao.equals=" + UPDATED_DATA_EMISSAO);
    }

    @Test
    @Transactional
    void getAllFacturasByDataEmissaoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataEmissao in DEFAULT_DATA_EMISSAO or UPDATED_DATA_EMISSAO
        defaultFacturaShouldBeFound("dataEmissao.in=" + DEFAULT_DATA_EMISSAO + "," + UPDATED_DATA_EMISSAO);

        // Get all the facturaList where dataEmissao equals to UPDATED_DATA_EMISSAO
        defaultFacturaShouldNotBeFound("dataEmissao.in=" + UPDATED_DATA_EMISSAO);
    }

    @Test
    @Transactional
    void getAllFacturasByDataEmissaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataEmissao is not null
        defaultFacturaShouldBeFound("dataEmissao.specified=true");

        // Get all the facturaList where dataEmissao is null
        defaultFacturaShouldNotBeFound("dataEmissao.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByDataEmissaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataEmissao is greater than or equal to DEFAULT_DATA_EMISSAO
        defaultFacturaShouldBeFound("dataEmissao.greaterThanOrEqual=" + DEFAULT_DATA_EMISSAO);

        // Get all the facturaList where dataEmissao is greater than or equal to UPDATED_DATA_EMISSAO
        defaultFacturaShouldNotBeFound("dataEmissao.greaterThanOrEqual=" + UPDATED_DATA_EMISSAO);
    }

    @Test
    @Transactional
    void getAllFacturasByDataEmissaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataEmissao is less than or equal to DEFAULT_DATA_EMISSAO
        defaultFacturaShouldBeFound("dataEmissao.lessThanOrEqual=" + DEFAULT_DATA_EMISSAO);

        // Get all the facturaList where dataEmissao is less than or equal to SMALLER_DATA_EMISSAO
        defaultFacturaShouldNotBeFound("dataEmissao.lessThanOrEqual=" + SMALLER_DATA_EMISSAO);
    }

    @Test
    @Transactional
    void getAllFacturasByDataEmissaoIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataEmissao is less than DEFAULT_DATA_EMISSAO
        defaultFacturaShouldNotBeFound("dataEmissao.lessThan=" + DEFAULT_DATA_EMISSAO);

        // Get all the facturaList where dataEmissao is less than UPDATED_DATA_EMISSAO
        defaultFacturaShouldBeFound("dataEmissao.lessThan=" + UPDATED_DATA_EMISSAO);
    }

    @Test
    @Transactional
    void getAllFacturasByDataEmissaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataEmissao is greater than DEFAULT_DATA_EMISSAO
        defaultFacturaShouldNotBeFound("dataEmissao.greaterThan=" + DEFAULT_DATA_EMISSAO);

        // Get all the facturaList where dataEmissao is greater than SMALLER_DATA_EMISSAO
        defaultFacturaShouldBeFound("dataEmissao.greaterThan=" + SMALLER_DATA_EMISSAO);
    }

    @Test
    @Transactional
    void getAllFacturasByDataVencimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataVencimento equals to DEFAULT_DATA_VENCIMENTO
        defaultFacturaShouldBeFound("dataVencimento.equals=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the facturaList where dataVencimento equals to UPDATED_DATA_VENCIMENTO
        defaultFacturaShouldNotBeFound("dataVencimento.equals=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllFacturasByDataVencimentoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataVencimento in DEFAULT_DATA_VENCIMENTO or UPDATED_DATA_VENCIMENTO
        defaultFacturaShouldBeFound("dataVencimento.in=" + DEFAULT_DATA_VENCIMENTO + "," + UPDATED_DATA_VENCIMENTO);

        // Get all the facturaList where dataVencimento equals to UPDATED_DATA_VENCIMENTO
        defaultFacturaShouldNotBeFound("dataVencimento.in=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllFacturasByDataVencimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataVencimento is not null
        defaultFacturaShouldBeFound("dataVencimento.specified=true");

        // Get all the facturaList where dataVencimento is null
        defaultFacturaShouldNotBeFound("dataVencimento.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByDataVencimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataVencimento is greater than or equal to DEFAULT_DATA_VENCIMENTO
        defaultFacturaShouldBeFound("dataVencimento.greaterThanOrEqual=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the facturaList where dataVencimento is greater than or equal to UPDATED_DATA_VENCIMENTO
        defaultFacturaShouldNotBeFound("dataVencimento.greaterThanOrEqual=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllFacturasByDataVencimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataVencimento is less than or equal to DEFAULT_DATA_VENCIMENTO
        defaultFacturaShouldBeFound("dataVencimento.lessThanOrEqual=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the facturaList where dataVencimento is less than or equal to SMALLER_DATA_VENCIMENTO
        defaultFacturaShouldNotBeFound("dataVencimento.lessThanOrEqual=" + SMALLER_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllFacturasByDataVencimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataVencimento is less than DEFAULT_DATA_VENCIMENTO
        defaultFacturaShouldNotBeFound("dataVencimento.lessThan=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the facturaList where dataVencimento is less than UPDATED_DATA_VENCIMENTO
        defaultFacturaShouldBeFound("dataVencimento.lessThan=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllFacturasByDataVencimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where dataVencimento is greater than DEFAULT_DATA_VENCIMENTO
        defaultFacturaShouldNotBeFound("dataVencimento.greaterThan=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the facturaList where dataVencimento is greater than SMALLER_DATA_VENCIMENTO
        defaultFacturaShouldBeFound("dataVencimento.greaterThan=" + SMALLER_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllFacturasByCaeIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cae equals to DEFAULT_CAE
        defaultFacturaShouldBeFound("cae.equals=" + DEFAULT_CAE);

        // Get all the facturaList where cae equals to UPDATED_CAE
        defaultFacturaShouldNotBeFound("cae.equals=" + UPDATED_CAE);
    }

    @Test
    @Transactional
    void getAllFacturasByCaeIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cae in DEFAULT_CAE or UPDATED_CAE
        defaultFacturaShouldBeFound("cae.in=" + DEFAULT_CAE + "," + UPDATED_CAE);

        // Get all the facturaList where cae equals to UPDATED_CAE
        defaultFacturaShouldNotBeFound("cae.in=" + UPDATED_CAE);
    }

    @Test
    @Transactional
    void getAllFacturasByCaeIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cae is not null
        defaultFacturaShouldBeFound("cae.specified=true");

        // Get all the facturaList where cae is null
        defaultFacturaShouldNotBeFound("cae.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByCaeContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cae contains DEFAULT_CAE
        defaultFacturaShouldBeFound("cae.contains=" + DEFAULT_CAE);

        // Get all the facturaList where cae contains UPDATED_CAE
        defaultFacturaShouldNotBeFound("cae.contains=" + UPDATED_CAE);
    }

    @Test
    @Transactional
    void getAllFacturasByCaeNotContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cae does not contain DEFAULT_CAE
        defaultFacturaShouldNotBeFound("cae.doesNotContain=" + DEFAULT_CAE);

        // Get all the facturaList where cae does not contain UPDATED_CAE
        defaultFacturaShouldBeFound("cae.doesNotContain=" + UPDATED_CAE);
    }

    @Test
    @Transactional
    void getAllFacturasByInicioTransporteIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where inicioTransporte equals to DEFAULT_INICIO_TRANSPORTE
        defaultFacturaShouldBeFound("inicioTransporte.equals=" + DEFAULT_INICIO_TRANSPORTE);

        // Get all the facturaList where inicioTransporte equals to UPDATED_INICIO_TRANSPORTE
        defaultFacturaShouldNotBeFound("inicioTransporte.equals=" + UPDATED_INICIO_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByInicioTransporteIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where inicioTransporte in DEFAULT_INICIO_TRANSPORTE or UPDATED_INICIO_TRANSPORTE
        defaultFacturaShouldBeFound("inicioTransporte.in=" + DEFAULT_INICIO_TRANSPORTE + "," + UPDATED_INICIO_TRANSPORTE);

        // Get all the facturaList where inicioTransporte equals to UPDATED_INICIO_TRANSPORTE
        defaultFacturaShouldNotBeFound("inicioTransporte.in=" + UPDATED_INICIO_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByInicioTransporteIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where inicioTransporte is not null
        defaultFacturaShouldBeFound("inicioTransporte.specified=true");

        // Get all the facturaList where inicioTransporte is null
        defaultFacturaShouldNotBeFound("inicioTransporte.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByInicioTransporteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where inicioTransporte is greater than or equal to DEFAULT_INICIO_TRANSPORTE
        defaultFacturaShouldBeFound("inicioTransporte.greaterThanOrEqual=" + DEFAULT_INICIO_TRANSPORTE);

        // Get all the facturaList where inicioTransporte is greater than or equal to UPDATED_INICIO_TRANSPORTE
        defaultFacturaShouldNotBeFound("inicioTransporte.greaterThanOrEqual=" + UPDATED_INICIO_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByInicioTransporteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where inicioTransporte is less than or equal to DEFAULT_INICIO_TRANSPORTE
        defaultFacturaShouldBeFound("inicioTransporte.lessThanOrEqual=" + DEFAULT_INICIO_TRANSPORTE);

        // Get all the facturaList where inicioTransporte is less than or equal to SMALLER_INICIO_TRANSPORTE
        defaultFacturaShouldNotBeFound("inicioTransporte.lessThanOrEqual=" + SMALLER_INICIO_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByInicioTransporteIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where inicioTransporte is less than DEFAULT_INICIO_TRANSPORTE
        defaultFacturaShouldNotBeFound("inicioTransporte.lessThan=" + DEFAULT_INICIO_TRANSPORTE);

        // Get all the facturaList where inicioTransporte is less than UPDATED_INICIO_TRANSPORTE
        defaultFacturaShouldBeFound("inicioTransporte.lessThan=" + UPDATED_INICIO_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByInicioTransporteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where inicioTransporte is greater than DEFAULT_INICIO_TRANSPORTE
        defaultFacturaShouldNotBeFound("inicioTransporte.greaterThan=" + DEFAULT_INICIO_TRANSPORTE);

        // Get all the facturaList where inicioTransporte is greater than SMALLER_INICIO_TRANSPORTE
        defaultFacturaShouldBeFound("inicioTransporte.greaterThan=" + SMALLER_INICIO_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByFimTransporteIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where fimTransporte equals to DEFAULT_FIM_TRANSPORTE
        defaultFacturaShouldBeFound("fimTransporte.equals=" + DEFAULT_FIM_TRANSPORTE);

        // Get all the facturaList where fimTransporte equals to UPDATED_FIM_TRANSPORTE
        defaultFacturaShouldNotBeFound("fimTransporte.equals=" + UPDATED_FIM_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByFimTransporteIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where fimTransporte in DEFAULT_FIM_TRANSPORTE or UPDATED_FIM_TRANSPORTE
        defaultFacturaShouldBeFound("fimTransporte.in=" + DEFAULT_FIM_TRANSPORTE + "," + UPDATED_FIM_TRANSPORTE);

        // Get all the facturaList where fimTransporte equals to UPDATED_FIM_TRANSPORTE
        defaultFacturaShouldNotBeFound("fimTransporte.in=" + UPDATED_FIM_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByFimTransporteIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where fimTransporte is not null
        defaultFacturaShouldBeFound("fimTransporte.specified=true");

        // Get all the facturaList where fimTransporte is null
        defaultFacturaShouldNotBeFound("fimTransporte.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByFimTransporteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where fimTransporte is greater than or equal to DEFAULT_FIM_TRANSPORTE
        defaultFacturaShouldBeFound("fimTransporte.greaterThanOrEqual=" + DEFAULT_FIM_TRANSPORTE);

        // Get all the facturaList where fimTransporte is greater than or equal to UPDATED_FIM_TRANSPORTE
        defaultFacturaShouldNotBeFound("fimTransporte.greaterThanOrEqual=" + UPDATED_FIM_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByFimTransporteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where fimTransporte is less than or equal to DEFAULT_FIM_TRANSPORTE
        defaultFacturaShouldBeFound("fimTransporte.lessThanOrEqual=" + DEFAULT_FIM_TRANSPORTE);

        // Get all the facturaList where fimTransporte is less than or equal to SMALLER_FIM_TRANSPORTE
        defaultFacturaShouldNotBeFound("fimTransporte.lessThanOrEqual=" + SMALLER_FIM_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByFimTransporteIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where fimTransporte is less than DEFAULT_FIM_TRANSPORTE
        defaultFacturaShouldNotBeFound("fimTransporte.lessThan=" + DEFAULT_FIM_TRANSPORTE);

        // Get all the facturaList where fimTransporte is less than UPDATED_FIM_TRANSPORTE
        defaultFacturaShouldBeFound("fimTransporte.lessThan=" + UPDATED_FIM_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByFimTransporteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where fimTransporte is greater than DEFAULT_FIM_TRANSPORTE
        defaultFacturaShouldNotBeFound("fimTransporte.greaterThan=" + DEFAULT_FIM_TRANSPORTE);

        // Get all the facturaList where fimTransporte is greater than SMALLER_FIM_TRANSPORTE
        defaultFacturaShouldBeFound("fimTransporte.greaterThan=" + SMALLER_FIM_TRANSPORTE);
    }

    @Test
    @Transactional
    void getAllFacturasByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where estado equals to DEFAULT_ESTADO
        defaultFacturaShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the facturaList where estado equals to UPDATED_ESTADO
        defaultFacturaShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllFacturasByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultFacturaShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the facturaList where estado equals to UPDATED_ESTADO
        defaultFacturaShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllFacturasByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where estado is not null
        defaultFacturaShouldBeFound("estado.specified=true");

        // Get all the facturaList where estado is null
        defaultFacturaShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByOrigemIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where origem equals to DEFAULT_ORIGEM
        defaultFacturaShouldBeFound("origem.equals=" + DEFAULT_ORIGEM);

        // Get all the facturaList where origem equals to UPDATED_ORIGEM
        defaultFacturaShouldNotBeFound("origem.equals=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllFacturasByOrigemIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where origem in DEFAULT_ORIGEM or UPDATED_ORIGEM
        defaultFacturaShouldBeFound("origem.in=" + DEFAULT_ORIGEM + "," + UPDATED_ORIGEM);

        // Get all the facturaList where origem equals to UPDATED_ORIGEM
        defaultFacturaShouldNotBeFound("origem.in=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllFacturasByOrigemIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where origem is not null
        defaultFacturaShouldBeFound("origem.specified=true");

        // Get all the facturaList where origem is null
        defaultFacturaShouldNotBeFound("origem.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByOrigemContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where origem contains DEFAULT_ORIGEM
        defaultFacturaShouldBeFound("origem.contains=" + DEFAULT_ORIGEM);

        // Get all the facturaList where origem contains UPDATED_ORIGEM
        defaultFacturaShouldNotBeFound("origem.contains=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllFacturasByOrigemNotContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where origem does not contain DEFAULT_ORIGEM
        defaultFacturaShouldNotBeFound("origem.doesNotContain=" + DEFAULT_ORIGEM);

        // Get all the facturaList where origem does not contain UPDATED_ORIGEM
        defaultFacturaShouldBeFound("origem.doesNotContain=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllFacturasByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where timestamp equals to DEFAULT_TIMESTAMP
        defaultFacturaShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the facturaList where timestamp equals to UPDATED_TIMESTAMP
        defaultFacturaShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllFacturasByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultFacturaShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the facturaList where timestamp equals to UPDATED_TIMESTAMP
        defaultFacturaShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllFacturasByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where timestamp is not null
        defaultFacturaShouldBeFound("timestamp.specified=true");

        // Get all the facturaList where timestamp is null
        defaultFacturaShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultFacturaShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the facturaList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultFacturaShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllFacturasByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultFacturaShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the facturaList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultFacturaShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllFacturasByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where timestamp is less than DEFAULT_TIMESTAMP
        defaultFacturaShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the facturaList where timestamp is less than UPDATED_TIMESTAMP
        defaultFacturaShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllFacturasByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultFacturaShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the facturaList where timestamp is greater than SMALLER_TIMESTAMP
        defaultFacturaShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllFacturasByIsMoedaEntrangeiraIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isMoedaEntrangeira equals to DEFAULT_IS_MOEDA_ENTRANGEIRA
        defaultFacturaShouldBeFound("isMoedaEntrangeira.equals=" + DEFAULT_IS_MOEDA_ENTRANGEIRA);

        // Get all the facturaList where isMoedaEntrangeira equals to UPDATED_IS_MOEDA_ENTRANGEIRA
        defaultFacturaShouldNotBeFound("isMoedaEntrangeira.equals=" + UPDATED_IS_MOEDA_ENTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllFacturasByIsMoedaEntrangeiraIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isMoedaEntrangeira in DEFAULT_IS_MOEDA_ENTRANGEIRA or UPDATED_IS_MOEDA_ENTRANGEIRA
        defaultFacturaShouldBeFound("isMoedaEntrangeira.in=" + DEFAULT_IS_MOEDA_ENTRANGEIRA + "," + UPDATED_IS_MOEDA_ENTRANGEIRA);

        // Get all the facturaList where isMoedaEntrangeira equals to UPDATED_IS_MOEDA_ENTRANGEIRA
        defaultFacturaShouldNotBeFound("isMoedaEntrangeira.in=" + UPDATED_IS_MOEDA_ENTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllFacturasByIsMoedaEntrangeiraIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isMoedaEntrangeira is not null
        defaultFacturaShouldBeFound("isMoedaEntrangeira.specified=true");

        // Get all the facturaList where isMoedaEntrangeira is null
        defaultFacturaShouldNotBeFound("isMoedaEntrangeira.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByMoedaIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where moeda equals to DEFAULT_MOEDA
        defaultFacturaShouldBeFound("moeda.equals=" + DEFAULT_MOEDA);

        // Get all the facturaList where moeda equals to UPDATED_MOEDA
        defaultFacturaShouldNotBeFound("moeda.equals=" + UPDATED_MOEDA);
    }

    @Test
    @Transactional
    void getAllFacturasByMoedaIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where moeda in DEFAULT_MOEDA or UPDATED_MOEDA
        defaultFacturaShouldBeFound("moeda.in=" + DEFAULT_MOEDA + "," + UPDATED_MOEDA);

        // Get all the facturaList where moeda equals to UPDATED_MOEDA
        defaultFacturaShouldNotBeFound("moeda.in=" + UPDATED_MOEDA);
    }

    @Test
    @Transactional
    void getAllFacturasByMoedaIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where moeda is not null
        defaultFacturaShouldBeFound("moeda.specified=true");

        // Get all the facturaList where moeda is null
        defaultFacturaShouldNotBeFound("moeda.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByMoedaContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where moeda contains DEFAULT_MOEDA
        defaultFacturaShouldBeFound("moeda.contains=" + DEFAULT_MOEDA);

        // Get all the facturaList where moeda contains UPDATED_MOEDA
        defaultFacturaShouldNotBeFound("moeda.contains=" + UPDATED_MOEDA);
    }

    @Test
    @Transactional
    void getAllFacturasByMoedaNotContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where moeda does not contain DEFAULT_MOEDA
        defaultFacturaShouldNotBeFound("moeda.doesNotContain=" + DEFAULT_MOEDA);

        // Get all the facturaList where moeda does not contain UPDATED_MOEDA
        defaultFacturaShouldBeFound("moeda.doesNotContain=" + UPDATED_MOEDA);
    }

    @Test
    @Transactional
    void getAllFacturasByCambioIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cambio equals to DEFAULT_CAMBIO
        defaultFacturaShouldBeFound("cambio.equals=" + DEFAULT_CAMBIO);

        // Get all the facturaList where cambio equals to UPDATED_CAMBIO
        defaultFacturaShouldNotBeFound("cambio.equals=" + UPDATED_CAMBIO);
    }

    @Test
    @Transactional
    void getAllFacturasByCambioIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cambio in DEFAULT_CAMBIO or UPDATED_CAMBIO
        defaultFacturaShouldBeFound("cambio.in=" + DEFAULT_CAMBIO + "," + UPDATED_CAMBIO);

        // Get all the facturaList where cambio equals to UPDATED_CAMBIO
        defaultFacturaShouldNotBeFound("cambio.in=" + UPDATED_CAMBIO);
    }

    @Test
    @Transactional
    void getAllFacturasByCambioIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cambio is not null
        defaultFacturaShouldBeFound("cambio.specified=true");

        // Get all the facturaList where cambio is null
        defaultFacturaShouldNotBeFound("cambio.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByCambioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cambio is greater than or equal to DEFAULT_CAMBIO
        defaultFacturaShouldBeFound("cambio.greaterThanOrEqual=" + DEFAULT_CAMBIO);

        // Get all the facturaList where cambio is greater than or equal to UPDATED_CAMBIO
        defaultFacturaShouldNotBeFound("cambio.greaterThanOrEqual=" + UPDATED_CAMBIO);
    }

    @Test
    @Transactional
    void getAllFacturasByCambioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cambio is less than or equal to DEFAULT_CAMBIO
        defaultFacturaShouldBeFound("cambio.lessThanOrEqual=" + DEFAULT_CAMBIO);

        // Get all the facturaList where cambio is less than or equal to SMALLER_CAMBIO
        defaultFacturaShouldNotBeFound("cambio.lessThanOrEqual=" + SMALLER_CAMBIO);
    }

    @Test
    @Transactional
    void getAllFacturasByCambioIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cambio is less than DEFAULT_CAMBIO
        defaultFacturaShouldNotBeFound("cambio.lessThan=" + DEFAULT_CAMBIO);

        // Get all the facturaList where cambio is less than UPDATED_CAMBIO
        defaultFacturaShouldBeFound("cambio.lessThan=" + UPDATED_CAMBIO);
    }

    @Test
    @Transactional
    void getAllFacturasByCambioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where cambio is greater than DEFAULT_CAMBIO
        defaultFacturaShouldNotBeFound("cambio.greaterThan=" + DEFAULT_CAMBIO);

        // Get all the facturaList where cambio is greater than SMALLER_CAMBIO
        defaultFacturaShouldBeFound("cambio.greaterThan=" + SMALLER_CAMBIO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalMoedaEntrangeiraIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalMoedaEntrangeira equals to DEFAULT_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldBeFound("totalMoedaEntrangeira.equals=" + DEFAULT_TOTAL_MOEDA_ENTRANGEIRA);

        // Get all the facturaList where totalMoedaEntrangeira equals to UPDATED_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldNotBeFound("totalMoedaEntrangeira.equals=" + UPDATED_TOTAL_MOEDA_ENTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalMoedaEntrangeiraIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalMoedaEntrangeira in DEFAULT_TOTAL_MOEDA_ENTRANGEIRA or UPDATED_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldBeFound("totalMoedaEntrangeira.in=" + DEFAULT_TOTAL_MOEDA_ENTRANGEIRA + "," + UPDATED_TOTAL_MOEDA_ENTRANGEIRA);

        // Get all the facturaList where totalMoedaEntrangeira equals to UPDATED_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldNotBeFound("totalMoedaEntrangeira.in=" + UPDATED_TOTAL_MOEDA_ENTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalMoedaEntrangeiraIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalMoedaEntrangeira is not null
        defaultFacturaShouldBeFound("totalMoedaEntrangeira.specified=true");

        // Get all the facturaList where totalMoedaEntrangeira is null
        defaultFacturaShouldNotBeFound("totalMoedaEntrangeira.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalMoedaEntrangeiraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalMoedaEntrangeira is greater than or equal to DEFAULT_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldBeFound("totalMoedaEntrangeira.greaterThanOrEqual=" + DEFAULT_TOTAL_MOEDA_ENTRANGEIRA);

        // Get all the facturaList where totalMoedaEntrangeira is greater than or equal to UPDATED_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldNotBeFound("totalMoedaEntrangeira.greaterThanOrEqual=" + UPDATED_TOTAL_MOEDA_ENTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalMoedaEntrangeiraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalMoedaEntrangeira is less than or equal to DEFAULT_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldBeFound("totalMoedaEntrangeira.lessThanOrEqual=" + DEFAULT_TOTAL_MOEDA_ENTRANGEIRA);

        // Get all the facturaList where totalMoedaEntrangeira is less than or equal to SMALLER_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldNotBeFound("totalMoedaEntrangeira.lessThanOrEqual=" + SMALLER_TOTAL_MOEDA_ENTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalMoedaEntrangeiraIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalMoedaEntrangeira is less than DEFAULT_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldNotBeFound("totalMoedaEntrangeira.lessThan=" + DEFAULT_TOTAL_MOEDA_ENTRANGEIRA);

        // Get all the facturaList where totalMoedaEntrangeira is less than UPDATED_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldBeFound("totalMoedaEntrangeira.lessThan=" + UPDATED_TOTAL_MOEDA_ENTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalMoedaEntrangeiraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalMoedaEntrangeira is greater than DEFAULT_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldNotBeFound("totalMoedaEntrangeira.greaterThan=" + DEFAULT_TOTAL_MOEDA_ENTRANGEIRA);

        // Get all the facturaList where totalMoedaEntrangeira is greater than SMALLER_TOTAL_MOEDA_ENTRANGEIRA
        defaultFacturaShouldBeFound("totalMoedaEntrangeira.greaterThan=" + SMALLER_TOTAL_MOEDA_ENTRANGEIRA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalIliquidoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalIliquido equals to DEFAULT_TOTAL_ILIQUIDO
        defaultFacturaShouldBeFound("totalIliquido.equals=" + DEFAULT_TOTAL_ILIQUIDO);

        // Get all the facturaList where totalIliquido equals to UPDATED_TOTAL_ILIQUIDO
        defaultFacturaShouldNotBeFound("totalIliquido.equals=" + UPDATED_TOTAL_ILIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalIliquidoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalIliquido in DEFAULT_TOTAL_ILIQUIDO or UPDATED_TOTAL_ILIQUIDO
        defaultFacturaShouldBeFound("totalIliquido.in=" + DEFAULT_TOTAL_ILIQUIDO + "," + UPDATED_TOTAL_ILIQUIDO);

        // Get all the facturaList where totalIliquido equals to UPDATED_TOTAL_ILIQUIDO
        defaultFacturaShouldNotBeFound("totalIliquido.in=" + UPDATED_TOTAL_ILIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalIliquidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalIliquido is not null
        defaultFacturaShouldBeFound("totalIliquido.specified=true");

        // Get all the facturaList where totalIliquido is null
        defaultFacturaShouldNotBeFound("totalIliquido.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalIliquidoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalIliquido is greater than or equal to DEFAULT_TOTAL_ILIQUIDO
        defaultFacturaShouldBeFound("totalIliquido.greaterThanOrEqual=" + DEFAULT_TOTAL_ILIQUIDO);

        // Get all the facturaList where totalIliquido is greater than or equal to UPDATED_TOTAL_ILIQUIDO
        defaultFacturaShouldNotBeFound("totalIliquido.greaterThanOrEqual=" + UPDATED_TOTAL_ILIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalIliquidoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalIliquido is less than or equal to DEFAULT_TOTAL_ILIQUIDO
        defaultFacturaShouldBeFound("totalIliquido.lessThanOrEqual=" + DEFAULT_TOTAL_ILIQUIDO);

        // Get all the facturaList where totalIliquido is less than or equal to SMALLER_TOTAL_ILIQUIDO
        defaultFacturaShouldNotBeFound("totalIliquido.lessThanOrEqual=" + SMALLER_TOTAL_ILIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalIliquidoIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalIliquido is less than DEFAULT_TOTAL_ILIQUIDO
        defaultFacturaShouldNotBeFound("totalIliquido.lessThan=" + DEFAULT_TOTAL_ILIQUIDO);

        // Get all the facturaList where totalIliquido is less than UPDATED_TOTAL_ILIQUIDO
        defaultFacturaShouldBeFound("totalIliquido.lessThan=" + UPDATED_TOTAL_ILIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalIliquidoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalIliquido is greater than DEFAULT_TOTAL_ILIQUIDO
        defaultFacturaShouldNotBeFound("totalIliquido.greaterThan=" + DEFAULT_TOTAL_ILIQUIDO);

        // Get all the facturaList where totalIliquido is greater than SMALLER_TOTAL_ILIQUIDO
        defaultFacturaShouldBeFound("totalIliquido.greaterThan=" + SMALLER_TOTAL_ILIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoComercialIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoComercial equals to DEFAULT_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldBeFound("totalDescontoComercial.equals=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL);

        // Get all the facturaList where totalDescontoComercial equals to UPDATED_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldNotBeFound("totalDescontoComercial.equals=" + UPDATED_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoComercialIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoComercial in DEFAULT_TOTAL_DESCONTO_COMERCIAL or UPDATED_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldBeFound(
            "totalDescontoComercial.in=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL + "," + UPDATED_TOTAL_DESCONTO_COMERCIAL
        );

        // Get all the facturaList where totalDescontoComercial equals to UPDATED_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldNotBeFound("totalDescontoComercial.in=" + UPDATED_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoComercialIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoComercial is not null
        defaultFacturaShouldBeFound("totalDescontoComercial.specified=true");

        // Get all the facturaList where totalDescontoComercial is null
        defaultFacturaShouldNotBeFound("totalDescontoComercial.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoComercialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoComercial is greater than or equal to DEFAULT_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldBeFound("totalDescontoComercial.greaterThanOrEqual=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL);

        // Get all the facturaList where totalDescontoComercial is greater than or equal to UPDATED_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldNotBeFound("totalDescontoComercial.greaterThanOrEqual=" + UPDATED_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoComercialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoComercial is less than or equal to DEFAULT_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldBeFound("totalDescontoComercial.lessThanOrEqual=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL);

        // Get all the facturaList where totalDescontoComercial is less than or equal to SMALLER_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldNotBeFound("totalDescontoComercial.lessThanOrEqual=" + SMALLER_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoComercialIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoComercial is less than DEFAULT_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldNotBeFound("totalDescontoComercial.lessThan=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL);

        // Get all the facturaList where totalDescontoComercial is less than UPDATED_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldBeFound("totalDescontoComercial.lessThan=" + UPDATED_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoComercialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoComercial is greater than DEFAULT_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldNotBeFound("totalDescontoComercial.greaterThan=" + DEFAULT_TOTAL_DESCONTO_COMERCIAL);

        // Get all the facturaList where totalDescontoComercial is greater than SMALLER_TOTAL_DESCONTO_COMERCIAL
        defaultFacturaShouldBeFound("totalDescontoComercial.greaterThan=" + SMALLER_TOTAL_DESCONTO_COMERCIAL);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalLiquidoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalLiquido equals to DEFAULT_TOTAL_LIQUIDO
        defaultFacturaShouldBeFound("totalLiquido.equals=" + DEFAULT_TOTAL_LIQUIDO);

        // Get all the facturaList where totalLiquido equals to UPDATED_TOTAL_LIQUIDO
        defaultFacturaShouldNotBeFound("totalLiquido.equals=" + UPDATED_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalLiquidoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalLiquido in DEFAULT_TOTAL_LIQUIDO or UPDATED_TOTAL_LIQUIDO
        defaultFacturaShouldBeFound("totalLiquido.in=" + DEFAULT_TOTAL_LIQUIDO + "," + UPDATED_TOTAL_LIQUIDO);

        // Get all the facturaList where totalLiquido equals to UPDATED_TOTAL_LIQUIDO
        defaultFacturaShouldNotBeFound("totalLiquido.in=" + UPDATED_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalLiquidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalLiquido is not null
        defaultFacturaShouldBeFound("totalLiquido.specified=true");

        // Get all the facturaList where totalLiquido is null
        defaultFacturaShouldNotBeFound("totalLiquido.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalLiquidoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalLiquido is greater than or equal to DEFAULT_TOTAL_LIQUIDO
        defaultFacturaShouldBeFound("totalLiquido.greaterThanOrEqual=" + DEFAULT_TOTAL_LIQUIDO);

        // Get all the facturaList where totalLiquido is greater than or equal to UPDATED_TOTAL_LIQUIDO
        defaultFacturaShouldNotBeFound("totalLiquido.greaterThanOrEqual=" + UPDATED_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalLiquidoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalLiquido is less than or equal to DEFAULT_TOTAL_LIQUIDO
        defaultFacturaShouldBeFound("totalLiquido.lessThanOrEqual=" + DEFAULT_TOTAL_LIQUIDO);

        // Get all the facturaList where totalLiquido is less than or equal to SMALLER_TOTAL_LIQUIDO
        defaultFacturaShouldNotBeFound("totalLiquido.lessThanOrEqual=" + SMALLER_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalLiquidoIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalLiquido is less than DEFAULT_TOTAL_LIQUIDO
        defaultFacturaShouldNotBeFound("totalLiquido.lessThan=" + DEFAULT_TOTAL_LIQUIDO);

        // Get all the facturaList where totalLiquido is less than UPDATED_TOTAL_LIQUIDO
        defaultFacturaShouldBeFound("totalLiquido.lessThan=" + UPDATED_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalLiquidoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalLiquido is greater than DEFAULT_TOTAL_LIQUIDO
        defaultFacturaShouldNotBeFound("totalLiquido.greaterThan=" + DEFAULT_TOTAL_LIQUIDO);

        // Get all the facturaList where totalLiquido is greater than SMALLER_TOTAL_LIQUIDO
        defaultFacturaShouldBeFound("totalLiquido.greaterThan=" + SMALLER_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoIVAIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoIVA equals to DEFAULT_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldBeFound("totalImpostoIVA.equals=" + DEFAULT_TOTAL_IMPOSTO_IVA);

        // Get all the facturaList where totalImpostoIVA equals to UPDATED_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldNotBeFound("totalImpostoIVA.equals=" + UPDATED_TOTAL_IMPOSTO_IVA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoIVAIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoIVA in DEFAULT_TOTAL_IMPOSTO_IVA or UPDATED_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldBeFound("totalImpostoIVA.in=" + DEFAULT_TOTAL_IMPOSTO_IVA + "," + UPDATED_TOTAL_IMPOSTO_IVA);

        // Get all the facturaList where totalImpostoIVA equals to UPDATED_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldNotBeFound("totalImpostoIVA.in=" + UPDATED_TOTAL_IMPOSTO_IVA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoIVAIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoIVA is not null
        defaultFacturaShouldBeFound("totalImpostoIVA.specified=true");

        // Get all the facturaList where totalImpostoIVA is null
        defaultFacturaShouldNotBeFound("totalImpostoIVA.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoIVAIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoIVA is greater than or equal to DEFAULT_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldBeFound("totalImpostoIVA.greaterThanOrEqual=" + DEFAULT_TOTAL_IMPOSTO_IVA);

        // Get all the facturaList where totalImpostoIVA is greater than or equal to UPDATED_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldNotBeFound("totalImpostoIVA.greaterThanOrEqual=" + UPDATED_TOTAL_IMPOSTO_IVA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoIVAIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoIVA is less than or equal to DEFAULT_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldBeFound("totalImpostoIVA.lessThanOrEqual=" + DEFAULT_TOTAL_IMPOSTO_IVA);

        // Get all the facturaList where totalImpostoIVA is less than or equal to SMALLER_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldNotBeFound("totalImpostoIVA.lessThanOrEqual=" + SMALLER_TOTAL_IMPOSTO_IVA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoIVAIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoIVA is less than DEFAULT_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldNotBeFound("totalImpostoIVA.lessThan=" + DEFAULT_TOTAL_IMPOSTO_IVA);

        // Get all the facturaList where totalImpostoIVA is less than UPDATED_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldBeFound("totalImpostoIVA.lessThan=" + UPDATED_TOTAL_IMPOSTO_IVA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoIVAIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoIVA is greater than DEFAULT_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldNotBeFound("totalImpostoIVA.greaterThan=" + DEFAULT_TOTAL_IMPOSTO_IVA);

        // Get all the facturaList where totalImpostoIVA is greater than SMALLER_TOTAL_IMPOSTO_IVA
        defaultFacturaShouldBeFound("totalImpostoIVA.greaterThan=" + SMALLER_TOTAL_IMPOSTO_IVA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoEspecialConsumoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoEspecialConsumo equals to DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldBeFound("totalImpostoEspecialConsumo.equals=" + DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);

        // Get all the facturaList where totalImpostoEspecialConsumo equals to UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldNotBeFound("totalImpostoEspecialConsumo.equals=" + UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoEspecialConsumoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoEspecialConsumo in DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO or UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldBeFound(
            "totalImpostoEspecialConsumo.in=" + DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO + "," + UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        );

        // Get all the facturaList where totalImpostoEspecialConsumo equals to UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldNotBeFound("totalImpostoEspecialConsumo.in=" + UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoEspecialConsumoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoEspecialConsumo is not null
        defaultFacturaShouldBeFound("totalImpostoEspecialConsumo.specified=true");

        // Get all the facturaList where totalImpostoEspecialConsumo is null
        defaultFacturaShouldNotBeFound("totalImpostoEspecialConsumo.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoEspecialConsumoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoEspecialConsumo is greater than or equal to DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldBeFound("totalImpostoEspecialConsumo.greaterThanOrEqual=" + DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);

        // Get all the facturaList where totalImpostoEspecialConsumo is greater than or equal to UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldNotBeFound("totalImpostoEspecialConsumo.greaterThanOrEqual=" + UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoEspecialConsumoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoEspecialConsumo is less than or equal to DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldBeFound("totalImpostoEspecialConsumo.lessThanOrEqual=" + DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);

        // Get all the facturaList where totalImpostoEspecialConsumo is less than or equal to SMALLER_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldNotBeFound("totalImpostoEspecialConsumo.lessThanOrEqual=" + SMALLER_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoEspecialConsumoIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoEspecialConsumo is less than DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldNotBeFound("totalImpostoEspecialConsumo.lessThan=" + DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);

        // Get all the facturaList where totalImpostoEspecialConsumo is less than UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldBeFound("totalImpostoEspecialConsumo.lessThan=" + UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoEspecialConsumoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoEspecialConsumo is greater than DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldNotBeFound("totalImpostoEspecialConsumo.greaterThan=" + DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);

        // Get all the facturaList where totalImpostoEspecialConsumo is greater than SMALLER_TOTAL_IMPOSTO_ESPECIAL_CONSUMO
        defaultFacturaShouldBeFound("totalImpostoEspecialConsumo.greaterThan=" + SMALLER_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoFinanceiroIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoFinanceiro equals to DEFAULT_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldBeFound("totalDescontoFinanceiro.equals=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO);

        // Get all the facturaList where totalDescontoFinanceiro equals to UPDATED_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldNotBeFound("totalDescontoFinanceiro.equals=" + UPDATED_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoFinanceiroIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoFinanceiro in DEFAULT_TOTAL_DESCONTO_FINANCEIRO or UPDATED_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldBeFound(
            "totalDescontoFinanceiro.in=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO + "," + UPDATED_TOTAL_DESCONTO_FINANCEIRO
        );

        // Get all the facturaList where totalDescontoFinanceiro equals to UPDATED_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldNotBeFound("totalDescontoFinanceiro.in=" + UPDATED_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoFinanceiroIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoFinanceiro is not null
        defaultFacturaShouldBeFound("totalDescontoFinanceiro.specified=true");

        // Get all the facturaList where totalDescontoFinanceiro is null
        defaultFacturaShouldNotBeFound("totalDescontoFinanceiro.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoFinanceiroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoFinanceiro is greater than or equal to DEFAULT_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldBeFound("totalDescontoFinanceiro.greaterThanOrEqual=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO);

        // Get all the facturaList where totalDescontoFinanceiro is greater than or equal to UPDATED_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldNotBeFound("totalDescontoFinanceiro.greaterThanOrEqual=" + UPDATED_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoFinanceiroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoFinanceiro is less than or equal to DEFAULT_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldBeFound("totalDescontoFinanceiro.lessThanOrEqual=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO);

        // Get all the facturaList where totalDescontoFinanceiro is less than or equal to SMALLER_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldNotBeFound("totalDescontoFinanceiro.lessThanOrEqual=" + SMALLER_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoFinanceiroIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoFinanceiro is less than DEFAULT_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldNotBeFound("totalDescontoFinanceiro.lessThan=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO);

        // Get all the facturaList where totalDescontoFinanceiro is less than UPDATED_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldBeFound("totalDescontoFinanceiro.lessThan=" + UPDATED_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDescontoFinanceiroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDescontoFinanceiro is greater than DEFAULT_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldNotBeFound("totalDescontoFinanceiro.greaterThan=" + DEFAULT_TOTAL_DESCONTO_FINANCEIRO);

        // Get all the facturaList where totalDescontoFinanceiro is greater than SMALLER_TOTAL_DESCONTO_FINANCEIRO
        defaultFacturaShouldBeFound("totalDescontoFinanceiro.greaterThan=" + SMALLER_TOTAL_DESCONTO_FINANCEIRO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalFacturaIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalFactura equals to DEFAULT_TOTAL_FACTURA
        defaultFacturaShouldBeFound("totalFactura.equals=" + DEFAULT_TOTAL_FACTURA);

        // Get all the facturaList where totalFactura equals to UPDATED_TOTAL_FACTURA
        defaultFacturaShouldNotBeFound("totalFactura.equals=" + UPDATED_TOTAL_FACTURA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalFacturaIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalFactura in DEFAULT_TOTAL_FACTURA or UPDATED_TOTAL_FACTURA
        defaultFacturaShouldBeFound("totalFactura.in=" + DEFAULT_TOTAL_FACTURA + "," + UPDATED_TOTAL_FACTURA);

        // Get all the facturaList where totalFactura equals to UPDATED_TOTAL_FACTURA
        defaultFacturaShouldNotBeFound("totalFactura.in=" + UPDATED_TOTAL_FACTURA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalFacturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalFactura is not null
        defaultFacturaShouldBeFound("totalFactura.specified=true");

        // Get all the facturaList where totalFactura is null
        defaultFacturaShouldNotBeFound("totalFactura.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalFacturaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalFactura is greater than or equal to DEFAULT_TOTAL_FACTURA
        defaultFacturaShouldBeFound("totalFactura.greaterThanOrEqual=" + DEFAULT_TOTAL_FACTURA);

        // Get all the facturaList where totalFactura is greater than or equal to UPDATED_TOTAL_FACTURA
        defaultFacturaShouldNotBeFound("totalFactura.greaterThanOrEqual=" + UPDATED_TOTAL_FACTURA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalFacturaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalFactura is less than or equal to DEFAULT_TOTAL_FACTURA
        defaultFacturaShouldBeFound("totalFactura.lessThanOrEqual=" + DEFAULT_TOTAL_FACTURA);

        // Get all the facturaList where totalFactura is less than or equal to SMALLER_TOTAL_FACTURA
        defaultFacturaShouldNotBeFound("totalFactura.lessThanOrEqual=" + SMALLER_TOTAL_FACTURA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalFacturaIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalFactura is less than DEFAULT_TOTAL_FACTURA
        defaultFacturaShouldNotBeFound("totalFactura.lessThan=" + DEFAULT_TOTAL_FACTURA);

        // Get all the facturaList where totalFactura is less than UPDATED_TOTAL_FACTURA
        defaultFacturaShouldBeFound("totalFactura.lessThan=" + UPDATED_TOTAL_FACTURA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalFacturaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalFactura is greater than DEFAULT_TOTAL_FACTURA
        defaultFacturaShouldNotBeFound("totalFactura.greaterThan=" + DEFAULT_TOTAL_FACTURA);

        // Get all the facturaList where totalFactura is greater than SMALLER_TOTAL_FACTURA
        defaultFacturaShouldBeFound("totalFactura.greaterThan=" + SMALLER_TOTAL_FACTURA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoRetencaoFonteIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoRetencaoFonte equals to DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldBeFound("totalImpostoRetencaoFonte.equals=" + DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE);

        // Get all the facturaList where totalImpostoRetencaoFonte equals to UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldNotBeFound("totalImpostoRetencaoFonte.equals=" + UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoRetencaoFonteIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoRetencaoFonte in DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE or UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldBeFound(
            "totalImpostoRetencaoFonte.in=" + DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE + "," + UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE
        );

        // Get all the facturaList where totalImpostoRetencaoFonte equals to UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldNotBeFound("totalImpostoRetencaoFonte.in=" + UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoRetencaoFonteIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoRetencaoFonte is not null
        defaultFacturaShouldBeFound("totalImpostoRetencaoFonte.specified=true");

        // Get all the facturaList where totalImpostoRetencaoFonte is null
        defaultFacturaShouldNotBeFound("totalImpostoRetencaoFonte.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoRetencaoFonteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoRetencaoFonte is greater than or equal to DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldBeFound("totalImpostoRetencaoFonte.greaterThanOrEqual=" + DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE);

        // Get all the facturaList where totalImpostoRetencaoFonte is greater than or equal to UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldNotBeFound("totalImpostoRetencaoFonte.greaterThanOrEqual=" + UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoRetencaoFonteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoRetencaoFonte is less than or equal to DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldBeFound("totalImpostoRetencaoFonte.lessThanOrEqual=" + DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE);

        // Get all the facturaList where totalImpostoRetencaoFonte is less than or equal to SMALLER_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldNotBeFound("totalImpostoRetencaoFonte.lessThanOrEqual=" + SMALLER_TOTAL_IMPOSTO_RETENCAO_FONTE);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoRetencaoFonteIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoRetencaoFonte is less than DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldNotBeFound("totalImpostoRetencaoFonte.lessThan=" + DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE);

        // Get all the facturaList where totalImpostoRetencaoFonte is less than UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldBeFound("totalImpostoRetencaoFonte.lessThan=" + UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalImpostoRetencaoFonteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalImpostoRetencaoFonte is greater than DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldNotBeFound("totalImpostoRetencaoFonte.greaterThan=" + DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE);

        // Get all the facturaList where totalImpostoRetencaoFonte is greater than SMALLER_TOTAL_IMPOSTO_RETENCAO_FONTE
        defaultFacturaShouldBeFound("totalImpostoRetencaoFonte.greaterThan=" + SMALLER_TOTAL_IMPOSTO_RETENCAO_FONTE);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagarIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPagar equals to DEFAULT_TOTAL_PAGAR
        defaultFacturaShouldBeFound("totalPagar.equals=" + DEFAULT_TOTAL_PAGAR);

        // Get all the facturaList where totalPagar equals to UPDATED_TOTAL_PAGAR
        defaultFacturaShouldNotBeFound("totalPagar.equals=" + UPDATED_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagarIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPagar in DEFAULT_TOTAL_PAGAR or UPDATED_TOTAL_PAGAR
        defaultFacturaShouldBeFound("totalPagar.in=" + DEFAULT_TOTAL_PAGAR + "," + UPDATED_TOTAL_PAGAR);

        // Get all the facturaList where totalPagar equals to UPDATED_TOTAL_PAGAR
        defaultFacturaShouldNotBeFound("totalPagar.in=" + UPDATED_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagarIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPagar is not null
        defaultFacturaShouldBeFound("totalPagar.specified=true");

        // Get all the facturaList where totalPagar is null
        defaultFacturaShouldNotBeFound("totalPagar.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPagar is greater than or equal to DEFAULT_TOTAL_PAGAR
        defaultFacturaShouldBeFound("totalPagar.greaterThanOrEqual=" + DEFAULT_TOTAL_PAGAR);

        // Get all the facturaList where totalPagar is greater than or equal to UPDATED_TOTAL_PAGAR
        defaultFacturaShouldNotBeFound("totalPagar.greaterThanOrEqual=" + UPDATED_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagarIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPagar is less than or equal to DEFAULT_TOTAL_PAGAR
        defaultFacturaShouldBeFound("totalPagar.lessThanOrEqual=" + DEFAULT_TOTAL_PAGAR);

        // Get all the facturaList where totalPagar is less than or equal to SMALLER_TOTAL_PAGAR
        defaultFacturaShouldNotBeFound("totalPagar.lessThanOrEqual=" + SMALLER_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagarIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPagar is less than DEFAULT_TOTAL_PAGAR
        defaultFacturaShouldNotBeFound("totalPagar.lessThan=" + DEFAULT_TOTAL_PAGAR);

        // Get all the facturaList where totalPagar is less than UPDATED_TOTAL_PAGAR
        defaultFacturaShouldBeFound("totalPagar.lessThan=" + UPDATED_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagarIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPagar is greater than DEFAULT_TOTAL_PAGAR
        defaultFacturaShouldNotBeFound("totalPagar.greaterThan=" + DEFAULT_TOTAL_PAGAR);

        // Get all the facturaList where totalPagar is greater than SMALLER_TOTAL_PAGAR
        defaultFacturaShouldBeFound("totalPagar.greaterThan=" + SMALLER_TOTAL_PAGAR);
    }

    @Test
    @Transactional
    void getAllFacturasByDebitoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where debito equals to DEFAULT_DEBITO
        defaultFacturaShouldBeFound("debito.equals=" + DEFAULT_DEBITO);

        // Get all the facturaList where debito equals to UPDATED_DEBITO
        defaultFacturaShouldNotBeFound("debito.equals=" + UPDATED_DEBITO);
    }

    @Test
    @Transactional
    void getAllFacturasByDebitoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where debito in DEFAULT_DEBITO or UPDATED_DEBITO
        defaultFacturaShouldBeFound("debito.in=" + DEFAULT_DEBITO + "," + UPDATED_DEBITO);

        // Get all the facturaList where debito equals to UPDATED_DEBITO
        defaultFacturaShouldNotBeFound("debito.in=" + UPDATED_DEBITO);
    }

    @Test
    @Transactional
    void getAllFacturasByDebitoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where debito is not null
        defaultFacturaShouldBeFound("debito.specified=true");

        // Get all the facturaList where debito is null
        defaultFacturaShouldNotBeFound("debito.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByDebitoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where debito is greater than or equal to DEFAULT_DEBITO
        defaultFacturaShouldBeFound("debito.greaterThanOrEqual=" + DEFAULT_DEBITO);

        // Get all the facturaList where debito is greater than or equal to UPDATED_DEBITO
        defaultFacturaShouldNotBeFound("debito.greaterThanOrEqual=" + UPDATED_DEBITO);
    }

    @Test
    @Transactional
    void getAllFacturasByDebitoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where debito is less than or equal to DEFAULT_DEBITO
        defaultFacturaShouldBeFound("debito.lessThanOrEqual=" + DEFAULT_DEBITO);

        // Get all the facturaList where debito is less than or equal to SMALLER_DEBITO
        defaultFacturaShouldNotBeFound("debito.lessThanOrEqual=" + SMALLER_DEBITO);
    }

    @Test
    @Transactional
    void getAllFacturasByDebitoIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where debito is less than DEFAULT_DEBITO
        defaultFacturaShouldNotBeFound("debito.lessThan=" + DEFAULT_DEBITO);

        // Get all the facturaList where debito is less than UPDATED_DEBITO
        defaultFacturaShouldBeFound("debito.lessThan=" + UPDATED_DEBITO);
    }

    @Test
    @Transactional
    void getAllFacturasByDebitoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where debito is greater than DEFAULT_DEBITO
        defaultFacturaShouldNotBeFound("debito.greaterThan=" + DEFAULT_DEBITO);

        // Get all the facturaList where debito is greater than SMALLER_DEBITO
        defaultFacturaShouldBeFound("debito.greaterThan=" + SMALLER_DEBITO);
    }

    @Test
    @Transactional
    void getAllFacturasByCreditoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where credito equals to DEFAULT_CREDITO
        defaultFacturaShouldBeFound("credito.equals=" + DEFAULT_CREDITO);

        // Get all the facturaList where credito equals to UPDATED_CREDITO
        defaultFacturaShouldNotBeFound("credito.equals=" + UPDATED_CREDITO);
    }

    @Test
    @Transactional
    void getAllFacturasByCreditoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where credito in DEFAULT_CREDITO or UPDATED_CREDITO
        defaultFacturaShouldBeFound("credito.in=" + DEFAULT_CREDITO + "," + UPDATED_CREDITO);

        // Get all the facturaList where credito equals to UPDATED_CREDITO
        defaultFacturaShouldNotBeFound("credito.in=" + UPDATED_CREDITO);
    }

    @Test
    @Transactional
    void getAllFacturasByCreditoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where credito is not null
        defaultFacturaShouldBeFound("credito.specified=true");

        // Get all the facturaList where credito is null
        defaultFacturaShouldNotBeFound("credito.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByCreditoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where credito is greater than or equal to DEFAULT_CREDITO
        defaultFacturaShouldBeFound("credito.greaterThanOrEqual=" + DEFAULT_CREDITO);

        // Get all the facturaList where credito is greater than or equal to UPDATED_CREDITO
        defaultFacturaShouldNotBeFound("credito.greaterThanOrEqual=" + UPDATED_CREDITO);
    }

    @Test
    @Transactional
    void getAllFacturasByCreditoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where credito is less than or equal to DEFAULT_CREDITO
        defaultFacturaShouldBeFound("credito.lessThanOrEqual=" + DEFAULT_CREDITO);

        // Get all the facturaList where credito is less than or equal to SMALLER_CREDITO
        defaultFacturaShouldNotBeFound("credito.lessThanOrEqual=" + SMALLER_CREDITO);
    }

    @Test
    @Transactional
    void getAllFacturasByCreditoIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where credito is less than DEFAULT_CREDITO
        defaultFacturaShouldNotBeFound("credito.lessThan=" + DEFAULT_CREDITO);

        // Get all the facturaList where credito is less than UPDATED_CREDITO
        defaultFacturaShouldBeFound("credito.lessThan=" + UPDATED_CREDITO);
    }

    @Test
    @Transactional
    void getAllFacturasByCreditoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where credito is greater than DEFAULT_CREDITO
        defaultFacturaShouldNotBeFound("credito.greaterThan=" + DEFAULT_CREDITO);

        // Get all the facturaList where credito is greater than SMALLER_CREDITO
        defaultFacturaShouldBeFound("credito.greaterThan=" + SMALLER_CREDITO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPago equals to DEFAULT_TOTAL_PAGO
        defaultFacturaShouldBeFound("totalPago.equals=" + DEFAULT_TOTAL_PAGO);

        // Get all the facturaList where totalPago equals to UPDATED_TOTAL_PAGO
        defaultFacturaShouldNotBeFound("totalPago.equals=" + UPDATED_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPago in DEFAULT_TOTAL_PAGO or UPDATED_TOTAL_PAGO
        defaultFacturaShouldBeFound("totalPago.in=" + DEFAULT_TOTAL_PAGO + "," + UPDATED_TOTAL_PAGO);

        // Get all the facturaList where totalPago equals to UPDATED_TOTAL_PAGO
        defaultFacturaShouldNotBeFound("totalPago.in=" + UPDATED_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPago is not null
        defaultFacturaShouldBeFound("totalPago.specified=true");

        // Get all the facturaList where totalPago is null
        defaultFacturaShouldNotBeFound("totalPago.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPago is greater than or equal to DEFAULT_TOTAL_PAGO
        defaultFacturaShouldBeFound("totalPago.greaterThanOrEqual=" + DEFAULT_TOTAL_PAGO);

        // Get all the facturaList where totalPago is greater than or equal to UPDATED_TOTAL_PAGO
        defaultFacturaShouldNotBeFound("totalPago.greaterThanOrEqual=" + UPDATED_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPago is less than or equal to DEFAULT_TOTAL_PAGO
        defaultFacturaShouldBeFound("totalPago.lessThanOrEqual=" + DEFAULT_TOTAL_PAGO);

        // Get all the facturaList where totalPago is less than or equal to SMALLER_TOTAL_PAGO
        defaultFacturaShouldNotBeFound("totalPago.lessThanOrEqual=" + SMALLER_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagoIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPago is less than DEFAULT_TOTAL_PAGO
        defaultFacturaShouldNotBeFound("totalPago.lessThan=" + DEFAULT_TOTAL_PAGO);

        // Get all the facturaList where totalPago is less than UPDATED_TOTAL_PAGO
        defaultFacturaShouldBeFound("totalPago.lessThan=" + UPDATED_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalPagoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalPago is greater than DEFAULT_TOTAL_PAGO
        defaultFacturaShouldNotBeFound("totalPago.greaterThan=" + DEFAULT_TOTAL_PAGO);

        // Get all the facturaList where totalPago is greater than SMALLER_TOTAL_PAGO
        defaultFacturaShouldBeFound("totalPago.greaterThan=" + SMALLER_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDiferencaIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDiferenca equals to DEFAULT_TOTAL_DIFERENCA
        defaultFacturaShouldBeFound("totalDiferenca.equals=" + DEFAULT_TOTAL_DIFERENCA);

        // Get all the facturaList where totalDiferenca equals to UPDATED_TOTAL_DIFERENCA
        defaultFacturaShouldNotBeFound("totalDiferenca.equals=" + UPDATED_TOTAL_DIFERENCA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDiferencaIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDiferenca in DEFAULT_TOTAL_DIFERENCA or UPDATED_TOTAL_DIFERENCA
        defaultFacturaShouldBeFound("totalDiferenca.in=" + DEFAULT_TOTAL_DIFERENCA + "," + UPDATED_TOTAL_DIFERENCA);

        // Get all the facturaList where totalDiferenca equals to UPDATED_TOTAL_DIFERENCA
        defaultFacturaShouldNotBeFound("totalDiferenca.in=" + UPDATED_TOTAL_DIFERENCA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDiferencaIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDiferenca is not null
        defaultFacturaShouldBeFound("totalDiferenca.specified=true");

        // Get all the facturaList where totalDiferenca is null
        defaultFacturaShouldNotBeFound("totalDiferenca.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDiferencaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDiferenca is greater than or equal to DEFAULT_TOTAL_DIFERENCA
        defaultFacturaShouldBeFound("totalDiferenca.greaterThanOrEqual=" + DEFAULT_TOTAL_DIFERENCA);

        // Get all the facturaList where totalDiferenca is greater than or equal to UPDATED_TOTAL_DIFERENCA
        defaultFacturaShouldNotBeFound("totalDiferenca.greaterThanOrEqual=" + UPDATED_TOTAL_DIFERENCA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDiferencaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDiferenca is less than or equal to DEFAULT_TOTAL_DIFERENCA
        defaultFacturaShouldBeFound("totalDiferenca.lessThanOrEqual=" + DEFAULT_TOTAL_DIFERENCA);

        // Get all the facturaList where totalDiferenca is less than or equal to SMALLER_TOTAL_DIFERENCA
        defaultFacturaShouldNotBeFound("totalDiferenca.lessThanOrEqual=" + SMALLER_TOTAL_DIFERENCA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDiferencaIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDiferenca is less than DEFAULT_TOTAL_DIFERENCA
        defaultFacturaShouldNotBeFound("totalDiferenca.lessThan=" + DEFAULT_TOTAL_DIFERENCA);

        // Get all the facturaList where totalDiferenca is less than UPDATED_TOTAL_DIFERENCA
        defaultFacturaShouldBeFound("totalDiferenca.lessThan=" + UPDATED_TOTAL_DIFERENCA);
    }

    @Test
    @Transactional
    void getAllFacturasByTotalDiferencaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where totalDiferenca is greater than DEFAULT_TOTAL_DIFERENCA
        defaultFacturaShouldNotBeFound("totalDiferenca.greaterThan=" + DEFAULT_TOTAL_DIFERENCA);

        // Get all the facturaList where totalDiferenca is greater than SMALLER_TOTAL_DIFERENCA
        defaultFacturaShouldBeFound("totalDiferenca.greaterThan=" + SMALLER_TOTAL_DIFERENCA);
    }

    @Test
    @Transactional
    void getAllFacturasByIsAutoFacturacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isAutoFacturacao equals to DEFAULT_IS_AUTO_FACTURACAO
        defaultFacturaShouldBeFound("isAutoFacturacao.equals=" + DEFAULT_IS_AUTO_FACTURACAO);

        // Get all the facturaList where isAutoFacturacao equals to UPDATED_IS_AUTO_FACTURACAO
        defaultFacturaShouldNotBeFound("isAutoFacturacao.equals=" + UPDATED_IS_AUTO_FACTURACAO);
    }

    @Test
    @Transactional
    void getAllFacturasByIsAutoFacturacaoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isAutoFacturacao in DEFAULT_IS_AUTO_FACTURACAO or UPDATED_IS_AUTO_FACTURACAO
        defaultFacturaShouldBeFound("isAutoFacturacao.in=" + DEFAULT_IS_AUTO_FACTURACAO + "," + UPDATED_IS_AUTO_FACTURACAO);

        // Get all the facturaList where isAutoFacturacao equals to UPDATED_IS_AUTO_FACTURACAO
        defaultFacturaShouldNotBeFound("isAutoFacturacao.in=" + UPDATED_IS_AUTO_FACTURACAO);
    }

    @Test
    @Transactional
    void getAllFacturasByIsAutoFacturacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isAutoFacturacao is not null
        defaultFacturaShouldBeFound("isAutoFacturacao.specified=true");

        // Get all the facturaList where isAutoFacturacao is null
        defaultFacturaShouldNotBeFound("isAutoFacturacao.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByIsRegimeCaixaIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isRegimeCaixa equals to DEFAULT_IS_REGIME_CAIXA
        defaultFacturaShouldBeFound("isRegimeCaixa.equals=" + DEFAULT_IS_REGIME_CAIXA);

        // Get all the facturaList where isRegimeCaixa equals to UPDATED_IS_REGIME_CAIXA
        defaultFacturaShouldNotBeFound("isRegimeCaixa.equals=" + UPDATED_IS_REGIME_CAIXA);
    }

    @Test
    @Transactional
    void getAllFacturasByIsRegimeCaixaIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isRegimeCaixa in DEFAULT_IS_REGIME_CAIXA or UPDATED_IS_REGIME_CAIXA
        defaultFacturaShouldBeFound("isRegimeCaixa.in=" + DEFAULT_IS_REGIME_CAIXA + "," + UPDATED_IS_REGIME_CAIXA);

        // Get all the facturaList where isRegimeCaixa equals to UPDATED_IS_REGIME_CAIXA
        defaultFacturaShouldNotBeFound("isRegimeCaixa.in=" + UPDATED_IS_REGIME_CAIXA);
    }

    @Test
    @Transactional
    void getAllFacturasByIsRegimeCaixaIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isRegimeCaixa is not null
        defaultFacturaShouldBeFound("isRegimeCaixa.specified=true");

        // Get all the facturaList where isRegimeCaixa is null
        defaultFacturaShouldNotBeFound("isRegimeCaixa.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByIsEmitidaNomeEContaTerceiroIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isEmitidaNomeEContaTerceiro equals to DEFAULT_IS_EMITIDA_NOME_E_CONTA_TERCEIRO
        defaultFacturaShouldBeFound("isEmitidaNomeEContaTerceiro.equals=" + DEFAULT_IS_EMITIDA_NOME_E_CONTA_TERCEIRO);

        // Get all the facturaList where isEmitidaNomeEContaTerceiro equals to UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO
        defaultFacturaShouldNotBeFound("isEmitidaNomeEContaTerceiro.equals=" + UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO);
    }

    @Test
    @Transactional
    void getAllFacturasByIsEmitidaNomeEContaTerceiroIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isEmitidaNomeEContaTerceiro in DEFAULT_IS_EMITIDA_NOME_E_CONTA_TERCEIRO or UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO
        defaultFacturaShouldBeFound(
            "isEmitidaNomeEContaTerceiro.in=" + DEFAULT_IS_EMITIDA_NOME_E_CONTA_TERCEIRO + "," + UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO
        );

        // Get all the facturaList where isEmitidaNomeEContaTerceiro equals to UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO
        defaultFacturaShouldNotBeFound("isEmitidaNomeEContaTerceiro.in=" + UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO);
    }

    @Test
    @Transactional
    void getAllFacturasByIsEmitidaNomeEContaTerceiroIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isEmitidaNomeEContaTerceiro is not null
        defaultFacturaShouldBeFound("isEmitidaNomeEContaTerceiro.specified=true");

        // Get all the facturaList where isEmitidaNomeEContaTerceiro is null
        defaultFacturaShouldNotBeFound("isEmitidaNomeEContaTerceiro.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByIsNovoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isNovo equals to DEFAULT_IS_NOVO
        defaultFacturaShouldBeFound("isNovo.equals=" + DEFAULT_IS_NOVO);

        // Get all the facturaList where isNovo equals to UPDATED_IS_NOVO
        defaultFacturaShouldNotBeFound("isNovo.equals=" + UPDATED_IS_NOVO);
    }

    @Test
    @Transactional
    void getAllFacturasByIsNovoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isNovo in DEFAULT_IS_NOVO or UPDATED_IS_NOVO
        defaultFacturaShouldBeFound("isNovo.in=" + DEFAULT_IS_NOVO + "," + UPDATED_IS_NOVO);

        // Get all the facturaList where isNovo equals to UPDATED_IS_NOVO
        defaultFacturaShouldNotBeFound("isNovo.in=" + UPDATED_IS_NOVO);
    }

    @Test
    @Transactional
    void getAllFacturasByIsNovoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isNovo is not null
        defaultFacturaShouldBeFound("isNovo.specified=true");

        // Get all the facturaList where isNovo is null
        defaultFacturaShouldNotBeFound("isNovo.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByIsFiscalizadoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isFiscalizado equals to DEFAULT_IS_FISCALIZADO
        defaultFacturaShouldBeFound("isFiscalizado.equals=" + DEFAULT_IS_FISCALIZADO);

        // Get all the facturaList where isFiscalizado equals to UPDATED_IS_FISCALIZADO
        defaultFacturaShouldNotBeFound("isFiscalizado.equals=" + UPDATED_IS_FISCALIZADO);
    }

    @Test
    @Transactional
    void getAllFacturasByIsFiscalizadoIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isFiscalizado in DEFAULT_IS_FISCALIZADO or UPDATED_IS_FISCALIZADO
        defaultFacturaShouldBeFound("isFiscalizado.in=" + DEFAULT_IS_FISCALIZADO + "," + UPDATED_IS_FISCALIZADO);

        // Get all the facturaList where isFiscalizado equals to UPDATED_IS_FISCALIZADO
        defaultFacturaShouldNotBeFound("isFiscalizado.in=" + UPDATED_IS_FISCALIZADO);
    }

    @Test
    @Transactional
    void getAllFacturasByIsFiscalizadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where isFiscalizado is not null
        defaultFacturaShouldBeFound("isFiscalizado.specified=true");

        // Get all the facturaList where isFiscalizado is null
        defaultFacturaShouldNotBeFound("isFiscalizado.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasBySignTextIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where signText equals to DEFAULT_SIGN_TEXT
        defaultFacturaShouldBeFound("signText.equals=" + DEFAULT_SIGN_TEXT);

        // Get all the facturaList where signText equals to UPDATED_SIGN_TEXT
        defaultFacturaShouldNotBeFound("signText.equals=" + UPDATED_SIGN_TEXT);
    }

    @Test
    @Transactional
    void getAllFacturasBySignTextIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where signText in DEFAULT_SIGN_TEXT or UPDATED_SIGN_TEXT
        defaultFacturaShouldBeFound("signText.in=" + DEFAULT_SIGN_TEXT + "," + UPDATED_SIGN_TEXT);

        // Get all the facturaList where signText equals to UPDATED_SIGN_TEXT
        defaultFacturaShouldNotBeFound("signText.in=" + UPDATED_SIGN_TEXT);
    }

    @Test
    @Transactional
    void getAllFacturasBySignTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where signText is not null
        defaultFacturaShouldBeFound("signText.specified=true");

        // Get all the facturaList where signText is null
        defaultFacturaShouldNotBeFound("signText.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasBySignTextContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where signText contains DEFAULT_SIGN_TEXT
        defaultFacturaShouldBeFound("signText.contains=" + DEFAULT_SIGN_TEXT);

        // Get all the facturaList where signText contains UPDATED_SIGN_TEXT
        defaultFacturaShouldNotBeFound("signText.contains=" + UPDATED_SIGN_TEXT);
    }

    @Test
    @Transactional
    void getAllFacturasBySignTextNotContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where signText does not contain DEFAULT_SIGN_TEXT
        defaultFacturaShouldNotBeFound("signText.doesNotContain=" + DEFAULT_SIGN_TEXT);

        // Get all the facturaList where signText does not contain UPDATED_SIGN_TEXT
        defaultFacturaShouldBeFound("signText.doesNotContain=" + UPDATED_SIGN_TEXT);
    }

    @Test
    @Transactional
    void getAllFacturasByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hash equals to DEFAULT_HASH
        defaultFacturaShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the facturaList where hash equals to UPDATED_HASH
        defaultFacturaShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllFacturasByHashIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultFacturaShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the facturaList where hash equals to UPDATED_HASH
        defaultFacturaShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllFacturasByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hash is not null
        defaultFacturaShouldBeFound("hash.specified=true");

        // Get all the facturaList where hash is null
        defaultFacturaShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByHashContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hash contains DEFAULT_HASH
        defaultFacturaShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the facturaList where hash contains UPDATED_HASH
        defaultFacturaShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllFacturasByHashNotContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hash does not contain DEFAULT_HASH
        defaultFacturaShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the facturaList where hash does not contain UPDATED_HASH
        defaultFacturaShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllFacturasByHashShortIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hashShort equals to DEFAULT_HASH_SHORT
        defaultFacturaShouldBeFound("hashShort.equals=" + DEFAULT_HASH_SHORT);

        // Get all the facturaList where hashShort equals to UPDATED_HASH_SHORT
        defaultFacturaShouldNotBeFound("hashShort.equals=" + UPDATED_HASH_SHORT);
    }

    @Test
    @Transactional
    void getAllFacturasByHashShortIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hashShort in DEFAULT_HASH_SHORT or UPDATED_HASH_SHORT
        defaultFacturaShouldBeFound("hashShort.in=" + DEFAULT_HASH_SHORT + "," + UPDATED_HASH_SHORT);

        // Get all the facturaList where hashShort equals to UPDATED_HASH_SHORT
        defaultFacturaShouldNotBeFound("hashShort.in=" + UPDATED_HASH_SHORT);
    }

    @Test
    @Transactional
    void getAllFacturasByHashShortIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hashShort is not null
        defaultFacturaShouldBeFound("hashShort.specified=true");

        // Get all the facturaList where hashShort is null
        defaultFacturaShouldNotBeFound("hashShort.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByHashShortContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hashShort contains DEFAULT_HASH_SHORT
        defaultFacturaShouldBeFound("hashShort.contains=" + DEFAULT_HASH_SHORT);

        // Get all the facturaList where hashShort contains UPDATED_HASH_SHORT
        defaultFacturaShouldNotBeFound("hashShort.contains=" + UPDATED_HASH_SHORT);
    }

    @Test
    @Transactional
    void getAllFacturasByHashShortNotContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hashShort does not contain DEFAULT_HASH_SHORT
        defaultFacturaShouldNotBeFound("hashShort.doesNotContain=" + DEFAULT_HASH_SHORT);

        // Get all the facturaList where hashShort does not contain UPDATED_HASH_SHORT
        defaultFacturaShouldBeFound("hashShort.doesNotContain=" + UPDATED_HASH_SHORT);
    }

    @Test
    @Transactional
    void getAllFacturasByHashControlIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hashControl equals to DEFAULT_HASH_CONTROL
        defaultFacturaShouldBeFound("hashControl.equals=" + DEFAULT_HASH_CONTROL);

        // Get all the facturaList where hashControl equals to UPDATED_HASH_CONTROL
        defaultFacturaShouldNotBeFound("hashControl.equals=" + UPDATED_HASH_CONTROL);
    }

    @Test
    @Transactional
    void getAllFacturasByHashControlIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hashControl in DEFAULT_HASH_CONTROL or UPDATED_HASH_CONTROL
        defaultFacturaShouldBeFound("hashControl.in=" + DEFAULT_HASH_CONTROL + "," + UPDATED_HASH_CONTROL);

        // Get all the facturaList where hashControl equals to UPDATED_HASH_CONTROL
        defaultFacturaShouldNotBeFound("hashControl.in=" + UPDATED_HASH_CONTROL);
    }

    @Test
    @Transactional
    void getAllFacturasByHashControlIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hashControl is not null
        defaultFacturaShouldBeFound("hashControl.specified=true");

        // Get all the facturaList where hashControl is null
        defaultFacturaShouldNotBeFound("hashControl.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByHashControlContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hashControl contains DEFAULT_HASH_CONTROL
        defaultFacturaShouldBeFound("hashControl.contains=" + DEFAULT_HASH_CONTROL);

        // Get all the facturaList where hashControl contains UPDATED_HASH_CONTROL
        defaultFacturaShouldNotBeFound("hashControl.contains=" + UPDATED_HASH_CONTROL);
    }

    @Test
    @Transactional
    void getAllFacturasByHashControlNotContainsSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where hashControl does not contain DEFAULT_HASH_CONTROL
        defaultFacturaShouldNotBeFound("hashControl.doesNotContain=" + DEFAULT_HASH_CONTROL);

        // Get all the facturaList where hashControl does not contain UPDATED_HASH_CONTROL
        defaultFacturaShouldBeFound("hashControl.doesNotContain=" + UPDATED_HASH_CONTROL);
    }

    @Test
    @Transactional
    void getAllFacturasByKeyVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where keyVersion equals to DEFAULT_KEY_VERSION
        defaultFacturaShouldBeFound("keyVersion.equals=" + DEFAULT_KEY_VERSION);

        // Get all the facturaList where keyVersion equals to UPDATED_KEY_VERSION
        defaultFacturaShouldNotBeFound("keyVersion.equals=" + UPDATED_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllFacturasByKeyVersionIsInShouldWork() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where keyVersion in DEFAULT_KEY_VERSION or UPDATED_KEY_VERSION
        defaultFacturaShouldBeFound("keyVersion.in=" + DEFAULT_KEY_VERSION + "," + UPDATED_KEY_VERSION);

        // Get all the facturaList where keyVersion equals to UPDATED_KEY_VERSION
        defaultFacturaShouldNotBeFound("keyVersion.in=" + UPDATED_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllFacturasByKeyVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where keyVersion is not null
        defaultFacturaShouldBeFound("keyVersion.specified=true");

        // Get all the facturaList where keyVersion is null
        defaultFacturaShouldNotBeFound("keyVersion.specified=false");
    }

    @Test
    @Transactional
    void getAllFacturasByKeyVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where keyVersion is greater than or equal to DEFAULT_KEY_VERSION
        defaultFacturaShouldBeFound("keyVersion.greaterThanOrEqual=" + DEFAULT_KEY_VERSION);

        // Get all the facturaList where keyVersion is greater than or equal to UPDATED_KEY_VERSION
        defaultFacturaShouldNotBeFound("keyVersion.greaterThanOrEqual=" + UPDATED_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllFacturasByKeyVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where keyVersion is less than or equal to DEFAULT_KEY_VERSION
        defaultFacturaShouldBeFound("keyVersion.lessThanOrEqual=" + DEFAULT_KEY_VERSION);

        // Get all the facturaList where keyVersion is less than or equal to SMALLER_KEY_VERSION
        defaultFacturaShouldNotBeFound("keyVersion.lessThanOrEqual=" + SMALLER_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllFacturasByKeyVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where keyVersion is less than DEFAULT_KEY_VERSION
        defaultFacturaShouldNotBeFound("keyVersion.lessThan=" + DEFAULT_KEY_VERSION);

        // Get all the facturaList where keyVersion is less than UPDATED_KEY_VERSION
        defaultFacturaShouldBeFound("keyVersion.lessThan=" + UPDATED_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllFacturasByKeyVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList where keyVersion is greater than DEFAULT_KEY_VERSION
        defaultFacturaShouldNotBeFound("keyVersion.greaterThan=" + DEFAULT_KEY_VERSION);

        // Get all the facturaList where keyVersion is greater than SMALLER_KEY_VERSION
        defaultFacturaShouldBeFound("keyVersion.greaterThan=" + SMALLER_KEY_VERSION);
    }

    @Test
    @Transactional
    void getAllFacturasByFacturaIsEqualToSomething() throws Exception {
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            facturaRepository.saveAndFlush(factura);
            factura = FacturaResourceIT.createEntity(em);
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        em.persist(factura);
        em.flush();
        factura.addFactura(factura);
        facturaRepository.saveAndFlush(factura);
        Long facturaId = factura.getId();

        // Get all the facturaList where factura equals to facturaId
        defaultFacturaShouldBeFound("facturaId.equals=" + facturaId);

        // Get all the facturaList where factura equals to (facturaId + 1)
        defaultFacturaShouldNotBeFound("facturaId.equals=" + (facturaId + 1));
    }

    @Test
    @Transactional
    void getAllFacturasByItemsFacturaIsEqualToSomething() throws Exception {
        ItemFactura itemsFactura;
        if (TestUtil.findAll(em, ItemFactura.class).isEmpty()) {
            facturaRepository.saveAndFlush(factura);
            itemsFactura = ItemFacturaResourceIT.createEntity(em);
        } else {
            itemsFactura = TestUtil.findAll(em, ItemFactura.class).get(0);
        }
        em.persist(itemsFactura);
        em.flush();
        factura.addItemsFactura(itemsFactura);
        facturaRepository.saveAndFlush(factura);
        Long itemsFacturaId = itemsFactura.getId();

        // Get all the facturaList where itemsFactura equals to itemsFacturaId
        defaultFacturaShouldBeFound("itemsFacturaId.equals=" + itemsFacturaId);

        // Get all the facturaList where itemsFactura equals to (itemsFacturaId + 1)
        defaultFacturaShouldNotBeFound("itemsFacturaId.equals=" + (itemsFacturaId + 1));
    }

    @Test
    @Transactional
    void getAllFacturasByAplicacoesFacturaIsEqualToSomething() throws Exception {
        AplicacaoRecibo aplicacoesFactura;
        if (TestUtil.findAll(em, AplicacaoRecibo.class).isEmpty()) {
            facturaRepository.saveAndFlush(factura);
            aplicacoesFactura = AplicacaoReciboResourceIT.createEntity(em);
        } else {
            aplicacoesFactura = TestUtil.findAll(em, AplicacaoRecibo.class).get(0);
        }
        em.persist(aplicacoesFactura);
        em.flush();
        factura.addAplicacoesFactura(aplicacoesFactura);
        facturaRepository.saveAndFlush(factura);
        Long aplicacoesFacturaId = aplicacoesFactura.getId();

        // Get all the facturaList where aplicacoesFactura equals to aplicacoesFacturaId
        defaultFacturaShouldBeFound("aplicacoesFacturaId.equals=" + aplicacoesFacturaId);

        // Get all the facturaList where aplicacoesFactura equals to (aplicacoesFacturaId + 1)
        defaultFacturaShouldNotBeFound("aplicacoesFacturaId.equals=" + (aplicacoesFacturaId + 1));
    }

    @Test
    @Transactional
    void getAllFacturasByResumosImpostoIsEqualToSomething() throws Exception {
        ResumoImpostoFactura resumosImposto;
        if (TestUtil.findAll(em, ResumoImpostoFactura.class).isEmpty()) {
            facturaRepository.saveAndFlush(factura);
            resumosImposto = ResumoImpostoFacturaResourceIT.createEntity(em);
        } else {
            resumosImposto = TestUtil.findAll(em, ResumoImpostoFactura.class).get(0);
        }
        em.persist(resumosImposto);
        em.flush();
        factura.addResumosImposto(resumosImposto);
        facturaRepository.saveAndFlush(factura);
        Long resumosImpostoId = resumosImposto.getId();

        // Get all the facturaList where resumosImposto equals to resumosImpostoId
        defaultFacturaShouldBeFound("resumosImpostoId.equals=" + resumosImpostoId);

        // Get all the facturaList where resumosImposto equals to (resumosImpostoId + 1)
        defaultFacturaShouldNotBeFound("resumosImpostoId.equals=" + (resumosImpostoId + 1));
    }

    @Test
    @Transactional
    void getAllFacturasByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            facturaRepository.saveAndFlush(factura);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        factura.addAnoLectivo(anoLectivo);
        facturaRepository.saveAndFlush(factura);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the facturaList where anoLectivo equals to anoLectivoId
        defaultFacturaShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the facturaList where anoLectivo equals to (anoLectivoId + 1)
        defaultFacturaShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllFacturasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            facturaRepository.saveAndFlush(factura);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        factura.setUtilizador(utilizador);
        facturaRepository.saveAndFlush(factura);
        Long utilizadorId = utilizador.getId();

        // Get all the facturaList where utilizador equals to utilizadorId
        defaultFacturaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the facturaList where utilizador equals to (utilizadorId + 1)
        defaultFacturaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllFacturasByMotivoAnulacaoIsEqualToSomething() throws Exception {
        LookupItem motivoAnulacao;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            facturaRepository.saveAndFlush(factura);
            motivoAnulacao = LookupItemResourceIT.createEntity(em);
        } else {
            motivoAnulacao = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(motivoAnulacao);
        em.flush();
        factura.setMotivoAnulacao(motivoAnulacao);
        facturaRepository.saveAndFlush(factura);
        Long motivoAnulacaoId = motivoAnulacao.getId();

        // Get all the facturaList where motivoAnulacao equals to motivoAnulacaoId
        defaultFacturaShouldBeFound("motivoAnulacaoId.equals=" + motivoAnulacaoId);

        // Get all the facturaList where motivoAnulacao equals to (motivoAnulacaoId + 1)
        defaultFacturaShouldNotBeFound("motivoAnulacaoId.equals=" + (motivoAnulacaoId + 1));
    }

    @Test
    @Transactional
    void getAllFacturasByMatriculaIsEqualToSomething() throws Exception {
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            facturaRepository.saveAndFlush(factura);
            matricula = MatriculaResourceIT.createEntity(em);
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(matricula);
        em.flush();
        factura.setMatricula(matricula);
        facturaRepository.saveAndFlush(factura);
        Long matriculaId = matricula.getId();

        // Get all the facturaList where matricula equals to matriculaId
        defaultFacturaShouldBeFound("matriculaId.equals=" + matriculaId);

        // Get all the facturaList where matricula equals to (matriculaId + 1)
        defaultFacturaShouldNotBeFound("matriculaId.equals=" + (matriculaId + 1));
    }

    @Test
    @Transactional
    void getAllFacturasByReferenciaIsEqualToSomething() throws Exception {
        Factura referencia;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            facturaRepository.saveAndFlush(factura);
            referencia = FacturaResourceIT.createEntity(em);
        } else {
            referencia = TestUtil.findAll(em, Factura.class).get(0);
        }
        em.persist(referencia);
        em.flush();
        factura.setReferencia(referencia);
        facturaRepository.saveAndFlush(factura);
        Long referenciaId = referencia.getId();

        // Get all the facturaList where referencia equals to referenciaId
        defaultFacturaShouldBeFound("referenciaId.equals=" + referenciaId);

        // Get all the facturaList where referencia equals to (referenciaId + 1)
        defaultFacturaShouldNotBeFound("referenciaId.equals=" + (referenciaId + 1));
    }

    @Test
    @Transactional
    void getAllFacturasByDocumentoComercialIsEqualToSomething() throws Exception {
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            facturaRepository.saveAndFlush(factura);
            documentoComercial = DocumentoComercialResourceIT.createEntity(em);
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        em.persist(documentoComercial);
        em.flush();
        factura.setDocumentoComercial(documentoComercial);
        facturaRepository.saveAndFlush(factura);
        Long documentoComercialId = documentoComercial.getId();

        // Get all the facturaList where documentoComercial equals to documentoComercialId
        defaultFacturaShouldBeFound("documentoComercialId.equals=" + documentoComercialId);

        // Get all the facturaList where documentoComercial equals to (documentoComercialId + 1)
        defaultFacturaShouldNotBeFound("documentoComercialId.equals=" + (documentoComercialId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFacturaShouldBeFound(String filter) throws Exception {
        restFacturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factura.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].codigoEntrega").value(hasItem(DEFAULT_CODIGO_ENTREGA)))
            .andExpect(jsonPath("$.[*].dataEmissao").value(hasItem(DEFAULT_DATA_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].cae").value(hasItem(DEFAULT_CAE)))
            .andExpect(jsonPath("$.[*].inicioTransporte").value(hasItem(sameInstant(DEFAULT_INICIO_TRANSPORTE))))
            .andExpect(jsonPath("$.[*].fimTransporte").value(hasItem(sameInstant(DEFAULT_FIM_TRANSPORTE))))
            .andExpect(jsonPath("$.[*].observacaoGeral").value(hasItem(DEFAULT_OBSERVACAO_GERAL.toString())))
            .andExpect(jsonPath("$.[*].observacaoInterna").value(hasItem(DEFAULT_OBSERVACAO_INTERNA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].isMoedaEntrangeira").value(hasItem(DEFAULT_IS_MOEDA_ENTRANGEIRA.booleanValue())))
            .andExpect(jsonPath("$.[*].moeda").value(hasItem(DEFAULT_MOEDA)))
            .andExpect(jsonPath("$.[*].cambio").value(hasItem(sameNumber(DEFAULT_CAMBIO))))
            .andExpect(jsonPath("$.[*].totalMoedaEntrangeira").value(hasItem(sameNumber(DEFAULT_TOTAL_MOEDA_ENTRANGEIRA))))
            .andExpect(jsonPath("$.[*].totalIliquido").value(hasItem(sameNumber(DEFAULT_TOTAL_ILIQUIDO))))
            .andExpect(jsonPath("$.[*].totalDescontoComercial").value(hasItem(sameNumber(DEFAULT_TOTAL_DESCONTO_COMERCIAL))))
            .andExpect(jsonPath("$.[*].totalLiquido").value(hasItem(sameNumber(DEFAULT_TOTAL_LIQUIDO))))
            .andExpect(jsonPath("$.[*].totalImpostoIVA").value(hasItem(sameNumber(DEFAULT_TOTAL_IMPOSTO_IVA))))
            .andExpect(jsonPath("$.[*].totalImpostoEspecialConsumo").value(hasItem(sameNumber(DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO))))
            .andExpect(jsonPath("$.[*].totalDescontoFinanceiro").value(hasItem(sameNumber(DEFAULT_TOTAL_DESCONTO_FINANCEIRO))))
            .andExpect(jsonPath("$.[*].totalFactura").value(hasItem(sameNumber(DEFAULT_TOTAL_FACTURA))))
            .andExpect(jsonPath("$.[*].totalImpostoRetencaoFonte").value(hasItem(sameNumber(DEFAULT_TOTAL_IMPOSTO_RETENCAO_FONTE))))
            .andExpect(jsonPath("$.[*].totalPagar").value(hasItem(sameNumber(DEFAULT_TOTAL_PAGAR))))
            .andExpect(jsonPath("$.[*].debito").value(hasItem(sameNumber(DEFAULT_DEBITO))))
            .andExpect(jsonPath("$.[*].credito").value(hasItem(sameNumber(DEFAULT_CREDITO))))
            .andExpect(jsonPath("$.[*].totalPago").value(hasItem(sameNumber(DEFAULT_TOTAL_PAGO))))
            .andExpect(jsonPath("$.[*].totalDiferenca").value(hasItem(sameNumber(DEFAULT_TOTAL_DIFERENCA))))
            .andExpect(jsonPath("$.[*].isAutoFacturacao").value(hasItem(DEFAULT_IS_AUTO_FACTURACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].isRegimeCaixa").value(hasItem(DEFAULT_IS_REGIME_CAIXA.booleanValue())))
            .andExpect(
                jsonPath("$.[*].isEmitidaNomeEContaTerceiro").value(hasItem(DEFAULT_IS_EMITIDA_NOME_E_CONTA_TERCEIRO.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].isNovo").value(hasItem(DEFAULT_IS_NOVO.booleanValue())))
            .andExpect(jsonPath("$.[*].isFiscalizado").value(hasItem(DEFAULT_IS_FISCALIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].signText").value(hasItem(DEFAULT_SIGN_TEXT)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].hashShort").value(hasItem(DEFAULT_HASH_SHORT)))
            .andExpect(jsonPath("$.[*].hashControl").value(hasItem(DEFAULT_HASH_CONTROL)))
            .andExpect(jsonPath("$.[*].keyVersion").value(hasItem(DEFAULT_KEY_VERSION)));

        // Check, that the count call also returns 1
        restFacturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFacturaShouldNotBeFound(String filter) throws Exception {
        restFacturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFacturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFactura() throws Exception {
        // Get the factura
        restFacturaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFactura() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();

        // Update the factura
        Factura updatedFactura = facturaRepository.findById(factura.getId()).get();
        // Disconnect from session so that the updates on updatedFactura are not directly saved in db
        em.detach(updatedFactura);
        updatedFactura
            .numero(UPDATED_NUMERO)
            .codigoEntrega(UPDATED_CODIGO_ENTREGA)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .cae(UPDATED_CAE)
            .inicioTransporte(UPDATED_INICIO_TRANSPORTE)
            .fimTransporte(UPDATED_FIM_TRANSPORTE)
            .observacaoGeral(UPDATED_OBSERVACAO_GERAL)
            .observacaoInterna(UPDATED_OBSERVACAO_INTERNA)
            .estado(UPDATED_ESTADO)
            .origem(UPDATED_ORIGEM)
            .timestamp(UPDATED_TIMESTAMP)
            .isMoedaEntrangeira(UPDATED_IS_MOEDA_ENTRANGEIRA)
            .moeda(UPDATED_MOEDA)
            .cambio(UPDATED_CAMBIO)
            .totalMoedaEntrangeira(UPDATED_TOTAL_MOEDA_ENTRANGEIRA)
            .totalIliquido(UPDATED_TOTAL_ILIQUIDO)
            .totalDescontoComercial(UPDATED_TOTAL_DESCONTO_COMERCIAL)
            .totalLiquido(UPDATED_TOTAL_LIQUIDO)
            .totalImpostoIVA(UPDATED_TOTAL_IMPOSTO_IVA)
            .totalImpostoEspecialConsumo(UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO)
            .totalDescontoFinanceiro(UPDATED_TOTAL_DESCONTO_FINANCEIRO)
            .totalFactura(UPDATED_TOTAL_FACTURA)
            .totalImpostoRetencaoFonte(UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE)
            .totalPagar(UPDATED_TOTAL_PAGAR)
            .debito(UPDATED_DEBITO)
            .credito(UPDATED_CREDITO)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalDiferenca(UPDATED_TOTAL_DIFERENCA)
            .isAutoFacturacao(UPDATED_IS_AUTO_FACTURACAO)
            .isRegimeCaixa(UPDATED_IS_REGIME_CAIXA)
            .isEmitidaNomeEContaTerceiro(UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO)
            .isNovo(UPDATED_IS_NOVO)
            .isFiscalizado(UPDATED_IS_FISCALIZADO)
            .signText(UPDATED_SIGN_TEXT)
            .hash(UPDATED_HASH)
            .hashShort(UPDATED_HASH_SHORT)
            .hashControl(UPDATED_HASH_CONTROL)
            .keyVersion(UPDATED_KEY_VERSION);
        FacturaDTO facturaDTO = facturaMapper.toDto(updatedFactura);

        restFacturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, facturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facturaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
        Factura testFactura = facturaList.get(facturaList.size() - 1);
        assertThat(testFactura.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testFactura.getCodigoEntrega()).isEqualTo(UPDATED_CODIGO_ENTREGA);
        assertThat(testFactura.getDataEmissao()).isEqualTo(UPDATED_DATA_EMISSAO);
        assertThat(testFactura.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testFactura.getCae()).isEqualTo(UPDATED_CAE);
        assertThat(testFactura.getInicioTransporte()).isEqualTo(UPDATED_INICIO_TRANSPORTE);
        assertThat(testFactura.getFimTransporte()).isEqualTo(UPDATED_FIM_TRANSPORTE);
        assertThat(testFactura.getObservacaoGeral()).isEqualTo(UPDATED_OBSERVACAO_GERAL);
        assertThat(testFactura.getObservacaoInterna()).isEqualTo(UPDATED_OBSERVACAO_INTERNA);
        assertThat(testFactura.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testFactura.getOrigem()).isEqualTo(UPDATED_ORIGEM);
        assertThat(testFactura.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testFactura.getIsMoedaEntrangeira()).isEqualTo(UPDATED_IS_MOEDA_ENTRANGEIRA);
        assertThat(testFactura.getMoeda()).isEqualTo(UPDATED_MOEDA);
        assertThat(testFactura.getCambio()).isEqualByComparingTo(UPDATED_CAMBIO);
        assertThat(testFactura.getTotalMoedaEntrangeira()).isEqualByComparingTo(UPDATED_TOTAL_MOEDA_ENTRANGEIRA);
        assertThat(testFactura.getTotalIliquido()).isEqualByComparingTo(UPDATED_TOTAL_ILIQUIDO);
        assertThat(testFactura.getTotalDescontoComercial()).isEqualByComparingTo(UPDATED_TOTAL_DESCONTO_COMERCIAL);
        assertThat(testFactura.getTotalLiquido()).isEqualByComparingTo(UPDATED_TOTAL_LIQUIDO);
        assertThat(testFactura.getTotalImpostoIVA()).isEqualByComparingTo(UPDATED_TOTAL_IMPOSTO_IVA);
        assertThat(testFactura.getTotalImpostoEspecialConsumo()).isEqualByComparingTo(UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);
        assertThat(testFactura.getTotalDescontoFinanceiro()).isEqualByComparingTo(UPDATED_TOTAL_DESCONTO_FINANCEIRO);
        assertThat(testFactura.getTotalFactura()).isEqualByComparingTo(UPDATED_TOTAL_FACTURA);
        assertThat(testFactura.getTotalImpostoRetencaoFonte()).isEqualByComparingTo(UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE);
        assertThat(testFactura.getTotalPagar()).isEqualByComparingTo(UPDATED_TOTAL_PAGAR);
        assertThat(testFactura.getDebito()).isEqualByComparingTo(UPDATED_DEBITO);
        assertThat(testFactura.getCredito()).isEqualByComparingTo(UPDATED_CREDITO);
        assertThat(testFactura.getTotalPago()).isEqualByComparingTo(UPDATED_TOTAL_PAGO);
        assertThat(testFactura.getTotalDiferenca()).isEqualByComparingTo(UPDATED_TOTAL_DIFERENCA);
        assertThat(testFactura.getIsAutoFacturacao()).isEqualTo(UPDATED_IS_AUTO_FACTURACAO);
        assertThat(testFactura.getIsRegimeCaixa()).isEqualTo(UPDATED_IS_REGIME_CAIXA);
        assertThat(testFactura.getIsEmitidaNomeEContaTerceiro()).isEqualTo(UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO);
        assertThat(testFactura.getIsNovo()).isEqualTo(UPDATED_IS_NOVO);
        assertThat(testFactura.getIsFiscalizado()).isEqualTo(UPDATED_IS_FISCALIZADO);
        assertThat(testFactura.getSignText()).isEqualTo(UPDATED_SIGN_TEXT);
        assertThat(testFactura.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testFactura.getHashShort()).isEqualTo(UPDATED_HASH_SHORT);
        assertThat(testFactura.getHashControl()).isEqualTo(UPDATED_HASH_CONTROL);
        assertThat(testFactura.getKeyVersion()).isEqualTo(UPDATED_KEY_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingFactura() throws Exception {
        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();
        factura.setId(count.incrementAndGet());

        // Create the Factura
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, facturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFactura() throws Exception {
        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();
        factura.setId(count.incrementAndGet());

        // Create the Factura
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFactura() throws Exception {
        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();
        factura.setId(count.incrementAndGet());

        // Create the Factura
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacturaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facturaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFacturaWithPatch() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();

        // Update the factura using partial update
        Factura partialUpdatedFactura = new Factura();
        partialUpdatedFactura.setId(factura.getId());

        partialUpdatedFactura
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .cae(UPDATED_CAE)
            .inicioTransporte(UPDATED_INICIO_TRANSPORTE)
            .observacaoInterna(UPDATED_OBSERVACAO_INTERNA)
            .cambio(UPDATED_CAMBIO)
            .totalMoedaEntrangeira(UPDATED_TOTAL_MOEDA_ENTRANGEIRA)
            .totalDescontoFinanceiro(UPDATED_TOTAL_DESCONTO_FINANCEIRO)
            .totalImpostoRetencaoFonte(UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE)
            .totalPagar(UPDATED_TOTAL_PAGAR)
            .debito(UPDATED_DEBITO)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalDiferenca(UPDATED_TOTAL_DIFERENCA)
            .isAutoFacturacao(UPDATED_IS_AUTO_FACTURACAO)
            .isFiscalizado(UPDATED_IS_FISCALIZADO)
            .signText(UPDATED_SIGN_TEXT)
            .hashShort(UPDATED_HASH_SHORT)
            .hashControl(UPDATED_HASH_CONTROL)
            .keyVersion(UPDATED_KEY_VERSION);

        restFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactura))
            )
            .andExpect(status().isOk());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
        Factura testFactura = facturaList.get(facturaList.size() - 1);
        assertThat(testFactura.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testFactura.getCodigoEntrega()).isEqualTo(DEFAULT_CODIGO_ENTREGA);
        assertThat(testFactura.getDataEmissao()).isEqualTo(UPDATED_DATA_EMISSAO);
        assertThat(testFactura.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testFactura.getCae()).isEqualTo(UPDATED_CAE);
        assertThat(testFactura.getInicioTransporte()).isEqualTo(UPDATED_INICIO_TRANSPORTE);
        assertThat(testFactura.getFimTransporte()).isEqualTo(DEFAULT_FIM_TRANSPORTE);
        assertThat(testFactura.getObservacaoGeral()).isEqualTo(DEFAULT_OBSERVACAO_GERAL);
        assertThat(testFactura.getObservacaoInterna()).isEqualTo(UPDATED_OBSERVACAO_INTERNA);
        assertThat(testFactura.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testFactura.getOrigem()).isEqualTo(DEFAULT_ORIGEM);
        assertThat(testFactura.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testFactura.getIsMoedaEntrangeira()).isEqualTo(DEFAULT_IS_MOEDA_ENTRANGEIRA);
        assertThat(testFactura.getMoeda()).isEqualTo(DEFAULT_MOEDA);
        assertThat(testFactura.getCambio()).isEqualByComparingTo(UPDATED_CAMBIO);
        assertThat(testFactura.getTotalMoedaEntrangeira()).isEqualByComparingTo(UPDATED_TOTAL_MOEDA_ENTRANGEIRA);
        assertThat(testFactura.getTotalIliquido()).isEqualByComparingTo(DEFAULT_TOTAL_ILIQUIDO);
        assertThat(testFactura.getTotalDescontoComercial()).isEqualByComparingTo(DEFAULT_TOTAL_DESCONTO_COMERCIAL);
        assertThat(testFactura.getTotalLiquido()).isEqualByComparingTo(DEFAULT_TOTAL_LIQUIDO);
        assertThat(testFactura.getTotalImpostoIVA()).isEqualByComparingTo(DEFAULT_TOTAL_IMPOSTO_IVA);
        assertThat(testFactura.getTotalImpostoEspecialConsumo()).isEqualByComparingTo(DEFAULT_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);
        assertThat(testFactura.getTotalDescontoFinanceiro()).isEqualByComparingTo(UPDATED_TOTAL_DESCONTO_FINANCEIRO);
        assertThat(testFactura.getTotalFactura()).isEqualByComparingTo(DEFAULT_TOTAL_FACTURA);
        assertThat(testFactura.getTotalImpostoRetencaoFonte()).isEqualByComparingTo(UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE);
        assertThat(testFactura.getTotalPagar()).isEqualByComparingTo(UPDATED_TOTAL_PAGAR);
        assertThat(testFactura.getDebito()).isEqualByComparingTo(UPDATED_DEBITO);
        assertThat(testFactura.getCredito()).isEqualByComparingTo(DEFAULT_CREDITO);
        assertThat(testFactura.getTotalPago()).isEqualByComparingTo(UPDATED_TOTAL_PAGO);
        assertThat(testFactura.getTotalDiferenca()).isEqualByComparingTo(UPDATED_TOTAL_DIFERENCA);
        assertThat(testFactura.getIsAutoFacturacao()).isEqualTo(UPDATED_IS_AUTO_FACTURACAO);
        assertThat(testFactura.getIsRegimeCaixa()).isEqualTo(DEFAULT_IS_REGIME_CAIXA);
        assertThat(testFactura.getIsEmitidaNomeEContaTerceiro()).isEqualTo(DEFAULT_IS_EMITIDA_NOME_E_CONTA_TERCEIRO);
        assertThat(testFactura.getIsNovo()).isEqualTo(DEFAULT_IS_NOVO);
        assertThat(testFactura.getIsFiscalizado()).isEqualTo(UPDATED_IS_FISCALIZADO);
        assertThat(testFactura.getSignText()).isEqualTo(UPDATED_SIGN_TEXT);
        assertThat(testFactura.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testFactura.getHashShort()).isEqualTo(UPDATED_HASH_SHORT);
        assertThat(testFactura.getHashControl()).isEqualTo(UPDATED_HASH_CONTROL);
        assertThat(testFactura.getKeyVersion()).isEqualTo(UPDATED_KEY_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateFacturaWithPatch() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();

        // Update the factura using partial update
        Factura partialUpdatedFactura = new Factura();
        partialUpdatedFactura.setId(factura.getId());

        partialUpdatedFactura
            .numero(UPDATED_NUMERO)
            .codigoEntrega(UPDATED_CODIGO_ENTREGA)
            .dataEmissao(UPDATED_DATA_EMISSAO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .cae(UPDATED_CAE)
            .inicioTransporte(UPDATED_INICIO_TRANSPORTE)
            .fimTransporte(UPDATED_FIM_TRANSPORTE)
            .observacaoGeral(UPDATED_OBSERVACAO_GERAL)
            .observacaoInterna(UPDATED_OBSERVACAO_INTERNA)
            .estado(UPDATED_ESTADO)
            .origem(UPDATED_ORIGEM)
            .timestamp(UPDATED_TIMESTAMP)
            .isMoedaEntrangeira(UPDATED_IS_MOEDA_ENTRANGEIRA)
            .moeda(UPDATED_MOEDA)
            .cambio(UPDATED_CAMBIO)
            .totalMoedaEntrangeira(UPDATED_TOTAL_MOEDA_ENTRANGEIRA)
            .totalIliquido(UPDATED_TOTAL_ILIQUIDO)
            .totalDescontoComercial(UPDATED_TOTAL_DESCONTO_COMERCIAL)
            .totalLiquido(UPDATED_TOTAL_LIQUIDO)
            .totalImpostoIVA(UPDATED_TOTAL_IMPOSTO_IVA)
            .totalImpostoEspecialConsumo(UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO)
            .totalDescontoFinanceiro(UPDATED_TOTAL_DESCONTO_FINANCEIRO)
            .totalFactura(UPDATED_TOTAL_FACTURA)
            .totalImpostoRetencaoFonte(UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE)
            .totalPagar(UPDATED_TOTAL_PAGAR)
            .debito(UPDATED_DEBITO)
            .credito(UPDATED_CREDITO)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalDiferenca(UPDATED_TOTAL_DIFERENCA)
            .isAutoFacturacao(UPDATED_IS_AUTO_FACTURACAO)
            .isRegimeCaixa(UPDATED_IS_REGIME_CAIXA)
            .isEmitidaNomeEContaTerceiro(UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO)
            .isNovo(UPDATED_IS_NOVO)
            .isFiscalizado(UPDATED_IS_FISCALIZADO)
            .signText(UPDATED_SIGN_TEXT)
            .hash(UPDATED_HASH)
            .hashShort(UPDATED_HASH_SHORT)
            .hashControl(UPDATED_HASH_CONTROL)
            .keyVersion(UPDATED_KEY_VERSION);

        restFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactura))
            )
            .andExpect(status().isOk());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
        Factura testFactura = facturaList.get(facturaList.size() - 1);
        assertThat(testFactura.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testFactura.getCodigoEntrega()).isEqualTo(UPDATED_CODIGO_ENTREGA);
        assertThat(testFactura.getDataEmissao()).isEqualTo(UPDATED_DATA_EMISSAO);
        assertThat(testFactura.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testFactura.getCae()).isEqualTo(UPDATED_CAE);
        assertThat(testFactura.getInicioTransporte()).isEqualTo(UPDATED_INICIO_TRANSPORTE);
        assertThat(testFactura.getFimTransporte()).isEqualTo(UPDATED_FIM_TRANSPORTE);
        assertThat(testFactura.getObservacaoGeral()).isEqualTo(UPDATED_OBSERVACAO_GERAL);
        assertThat(testFactura.getObservacaoInterna()).isEqualTo(UPDATED_OBSERVACAO_INTERNA);
        assertThat(testFactura.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testFactura.getOrigem()).isEqualTo(UPDATED_ORIGEM);
        assertThat(testFactura.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testFactura.getIsMoedaEntrangeira()).isEqualTo(UPDATED_IS_MOEDA_ENTRANGEIRA);
        assertThat(testFactura.getMoeda()).isEqualTo(UPDATED_MOEDA);
        assertThat(testFactura.getCambio()).isEqualByComparingTo(UPDATED_CAMBIO);
        assertThat(testFactura.getTotalMoedaEntrangeira()).isEqualByComparingTo(UPDATED_TOTAL_MOEDA_ENTRANGEIRA);
        assertThat(testFactura.getTotalIliquido()).isEqualByComparingTo(UPDATED_TOTAL_ILIQUIDO);
        assertThat(testFactura.getTotalDescontoComercial()).isEqualByComparingTo(UPDATED_TOTAL_DESCONTO_COMERCIAL);
        assertThat(testFactura.getTotalLiquido()).isEqualByComparingTo(UPDATED_TOTAL_LIQUIDO);
        assertThat(testFactura.getTotalImpostoIVA()).isEqualByComparingTo(UPDATED_TOTAL_IMPOSTO_IVA);
        assertThat(testFactura.getTotalImpostoEspecialConsumo()).isEqualByComparingTo(UPDATED_TOTAL_IMPOSTO_ESPECIAL_CONSUMO);
        assertThat(testFactura.getTotalDescontoFinanceiro()).isEqualByComparingTo(UPDATED_TOTAL_DESCONTO_FINANCEIRO);
        assertThat(testFactura.getTotalFactura()).isEqualByComparingTo(UPDATED_TOTAL_FACTURA);
        assertThat(testFactura.getTotalImpostoRetencaoFonte()).isEqualByComparingTo(UPDATED_TOTAL_IMPOSTO_RETENCAO_FONTE);
        assertThat(testFactura.getTotalPagar()).isEqualByComparingTo(UPDATED_TOTAL_PAGAR);
        assertThat(testFactura.getDebito()).isEqualByComparingTo(UPDATED_DEBITO);
        assertThat(testFactura.getCredito()).isEqualByComparingTo(UPDATED_CREDITO);
        assertThat(testFactura.getTotalPago()).isEqualByComparingTo(UPDATED_TOTAL_PAGO);
        assertThat(testFactura.getTotalDiferenca()).isEqualByComparingTo(UPDATED_TOTAL_DIFERENCA);
        assertThat(testFactura.getIsAutoFacturacao()).isEqualTo(UPDATED_IS_AUTO_FACTURACAO);
        assertThat(testFactura.getIsRegimeCaixa()).isEqualTo(UPDATED_IS_REGIME_CAIXA);
        assertThat(testFactura.getIsEmitidaNomeEContaTerceiro()).isEqualTo(UPDATED_IS_EMITIDA_NOME_E_CONTA_TERCEIRO);
        assertThat(testFactura.getIsNovo()).isEqualTo(UPDATED_IS_NOVO);
        assertThat(testFactura.getIsFiscalizado()).isEqualTo(UPDATED_IS_FISCALIZADO);
        assertThat(testFactura.getSignText()).isEqualTo(UPDATED_SIGN_TEXT);
        assertThat(testFactura.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testFactura.getHashShort()).isEqualTo(UPDATED_HASH_SHORT);
        assertThat(testFactura.getHashControl()).isEqualTo(UPDATED_HASH_CONTROL);
        assertThat(testFactura.getKeyVersion()).isEqualTo(UPDATED_KEY_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingFactura() throws Exception {
        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();
        factura.setId(count.incrementAndGet());

        // Create the Factura
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, facturaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(facturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFactura() throws Exception {
        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();
        factura.setId(count.incrementAndGet());

        // Create the Factura
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(facturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFactura() throws Exception {
        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();
        factura.setId(count.incrementAndGet());

        // Create the Factura
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(facturaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFactura() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        int databaseSizeBeforeDelete = facturaRepository.findAll().size();

        // Delete the factura
        restFacturaMockMvc
            .perform(delete(ENTITY_API_URL_ID, factura.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
