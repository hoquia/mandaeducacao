package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static com.ravunana.longonkelo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.Transacao;
import com.ravunana.longonkelo.domain.TransferenciaSaldo;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.TransferenciaSaldoRepository;
import com.ravunana.longonkelo.service.TransferenciaSaldoService;
import com.ravunana.longonkelo.service.criteria.TransferenciaSaldoCriteria;
import com.ravunana.longonkelo.service.dto.TransferenciaSaldoDTO;
import com.ravunana.longonkelo.service.mapper.TransferenciaSaldoMapper;
import java.math.BigDecimal;
import java.time.Instant;
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
 * Integration tests for the {@link TransferenciaSaldoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TransferenciaSaldoResourceIT {

    private static final BigDecimal DEFAULT_MONTANTE = new BigDecimal(0);
    private static final BigDecimal UPDATED_MONTANTE = new BigDecimal(1);
    private static final BigDecimal SMALLER_MONTANTE = new BigDecimal(0 - 1);

    private static final Boolean DEFAULT_IS_MESMA_CONTA = false;
    private static final Boolean UPDATED_IS_MESMA_CONTA = true;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/transferencia-saldos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransferenciaSaldoRepository transferenciaSaldoRepository;

    @Mock
    private TransferenciaSaldoRepository transferenciaSaldoRepositoryMock;

    @Autowired
    private TransferenciaSaldoMapper transferenciaSaldoMapper;

    @Mock
    private TransferenciaSaldoService transferenciaSaldoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferenciaSaldoMockMvc;

    private TransferenciaSaldo transferenciaSaldo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferenciaSaldo createEntity(EntityManager em) {
        TransferenciaSaldo transferenciaSaldo = new TransferenciaSaldo()
            .montante(DEFAULT_MONTANTE)
            .isMesmaConta(DEFAULT_IS_MESMA_CONTA)
            .descricao(DEFAULT_DESCRICAO)
            .timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        transferenciaSaldo.setDe(discente);
        // Add required entity
        transferenciaSaldo.setPara(discente);
        return transferenciaSaldo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferenciaSaldo createUpdatedEntity(EntityManager em) {
        TransferenciaSaldo transferenciaSaldo = new TransferenciaSaldo()
            .montante(UPDATED_MONTANTE)
            .isMesmaConta(UPDATED_IS_MESMA_CONTA)
            .descricao(UPDATED_DESCRICAO)
            .timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createUpdatedEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        transferenciaSaldo.setDe(discente);
        // Add required entity
        transferenciaSaldo.setPara(discente);
        return transferenciaSaldo;
    }

    @BeforeEach
    public void initTest() {
        transferenciaSaldo = createEntity(em);
    }

    @Test
    @Transactional
    void createTransferenciaSaldo() throws Exception {
        int databaseSizeBeforeCreate = transferenciaSaldoRepository.findAll().size();
        // Create the TransferenciaSaldo
        TransferenciaSaldoDTO transferenciaSaldoDTO = transferenciaSaldoMapper.toDto(transferenciaSaldo);
        restTransferenciaSaldoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaSaldoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransferenciaSaldo in the database
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeCreate + 1);
        TransferenciaSaldo testTransferenciaSaldo = transferenciaSaldoList.get(transferenciaSaldoList.size() - 1);
        assertThat(testTransferenciaSaldo.getMontante()).isEqualByComparingTo(DEFAULT_MONTANTE);
        assertThat(testTransferenciaSaldo.getIsMesmaConta()).isEqualTo(DEFAULT_IS_MESMA_CONTA);
        assertThat(testTransferenciaSaldo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTransferenciaSaldo.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createTransferenciaSaldoWithExistingId() throws Exception {
        // Create the TransferenciaSaldo with an existing ID
        transferenciaSaldo.setId(1L);
        TransferenciaSaldoDTO transferenciaSaldoDTO = transferenciaSaldoMapper.toDto(transferenciaSaldo);

        int databaseSizeBeforeCreate = transferenciaSaldoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferenciaSaldoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaSaldoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaSaldo in the database
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldos() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList
        restTransferenciaSaldoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferenciaSaldo.getId().intValue())))
            .andExpect(jsonPath("$.[*].montante").value(hasItem(sameNumber(DEFAULT_MONTANTE))))
            .andExpect(jsonPath("$.[*].isMesmaConta").value(hasItem(DEFAULT_IS_MESMA_CONTA.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTransferenciaSaldosWithEagerRelationshipsIsEnabled() throws Exception {
        when(transferenciaSaldoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTransferenciaSaldoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(transferenciaSaldoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTransferenciaSaldosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(transferenciaSaldoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTransferenciaSaldoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(transferenciaSaldoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTransferenciaSaldo() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get the transferenciaSaldo
        restTransferenciaSaldoMockMvc
            .perform(get(ENTITY_API_URL_ID, transferenciaSaldo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transferenciaSaldo.getId().intValue()))
            .andExpect(jsonPath("$.montante").value(sameNumber(DEFAULT_MONTANTE)))
            .andExpect(jsonPath("$.isMesmaConta").value(DEFAULT_IS_MESMA_CONTA.booleanValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getTransferenciaSaldosByIdFiltering() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        Long id = transferenciaSaldo.getId();

        defaultTransferenciaSaldoShouldBeFound("id.equals=" + id);
        defaultTransferenciaSaldoShouldNotBeFound("id.notEquals=" + id);

        defaultTransferenciaSaldoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransferenciaSaldoShouldNotBeFound("id.greaterThan=" + id);

        defaultTransferenciaSaldoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransferenciaSaldoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByMontanteIsEqualToSomething() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where montante equals to DEFAULT_MONTANTE
        defaultTransferenciaSaldoShouldBeFound("montante.equals=" + DEFAULT_MONTANTE);

        // Get all the transferenciaSaldoList where montante equals to UPDATED_MONTANTE
        defaultTransferenciaSaldoShouldNotBeFound("montante.equals=" + UPDATED_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByMontanteIsInShouldWork() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where montante in DEFAULT_MONTANTE or UPDATED_MONTANTE
        defaultTransferenciaSaldoShouldBeFound("montante.in=" + DEFAULT_MONTANTE + "," + UPDATED_MONTANTE);

        // Get all the transferenciaSaldoList where montante equals to UPDATED_MONTANTE
        defaultTransferenciaSaldoShouldNotBeFound("montante.in=" + UPDATED_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByMontanteIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where montante is not null
        defaultTransferenciaSaldoShouldBeFound("montante.specified=true");

        // Get all the transferenciaSaldoList where montante is null
        defaultTransferenciaSaldoShouldNotBeFound("montante.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByMontanteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where montante is greater than or equal to DEFAULT_MONTANTE
        defaultTransferenciaSaldoShouldBeFound("montante.greaterThanOrEqual=" + DEFAULT_MONTANTE);

        // Get all the transferenciaSaldoList where montante is greater than or equal to UPDATED_MONTANTE
        defaultTransferenciaSaldoShouldNotBeFound("montante.greaterThanOrEqual=" + UPDATED_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByMontanteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where montante is less than or equal to DEFAULT_MONTANTE
        defaultTransferenciaSaldoShouldBeFound("montante.lessThanOrEqual=" + DEFAULT_MONTANTE);

        // Get all the transferenciaSaldoList where montante is less than or equal to SMALLER_MONTANTE
        defaultTransferenciaSaldoShouldNotBeFound("montante.lessThanOrEqual=" + SMALLER_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByMontanteIsLessThanSomething() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where montante is less than DEFAULT_MONTANTE
        defaultTransferenciaSaldoShouldNotBeFound("montante.lessThan=" + DEFAULT_MONTANTE);

        // Get all the transferenciaSaldoList where montante is less than UPDATED_MONTANTE
        defaultTransferenciaSaldoShouldBeFound("montante.lessThan=" + UPDATED_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByMontanteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where montante is greater than DEFAULT_MONTANTE
        defaultTransferenciaSaldoShouldNotBeFound("montante.greaterThan=" + DEFAULT_MONTANTE);

        // Get all the transferenciaSaldoList where montante is greater than SMALLER_MONTANTE
        defaultTransferenciaSaldoShouldBeFound("montante.greaterThan=" + SMALLER_MONTANTE);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByIsMesmaContaIsEqualToSomething() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where isMesmaConta equals to DEFAULT_IS_MESMA_CONTA
        defaultTransferenciaSaldoShouldBeFound("isMesmaConta.equals=" + DEFAULT_IS_MESMA_CONTA);

        // Get all the transferenciaSaldoList where isMesmaConta equals to UPDATED_IS_MESMA_CONTA
        defaultTransferenciaSaldoShouldNotBeFound("isMesmaConta.equals=" + UPDATED_IS_MESMA_CONTA);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByIsMesmaContaIsInShouldWork() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where isMesmaConta in DEFAULT_IS_MESMA_CONTA or UPDATED_IS_MESMA_CONTA
        defaultTransferenciaSaldoShouldBeFound("isMesmaConta.in=" + DEFAULT_IS_MESMA_CONTA + "," + UPDATED_IS_MESMA_CONTA);

        // Get all the transferenciaSaldoList where isMesmaConta equals to UPDATED_IS_MESMA_CONTA
        defaultTransferenciaSaldoShouldNotBeFound("isMesmaConta.in=" + UPDATED_IS_MESMA_CONTA);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByIsMesmaContaIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where isMesmaConta is not null
        defaultTransferenciaSaldoShouldBeFound("isMesmaConta.specified=true");

        // Get all the transferenciaSaldoList where isMesmaConta is null
        defaultTransferenciaSaldoShouldNotBeFound("isMesmaConta.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where timestamp equals to DEFAULT_TIMESTAMP
        defaultTransferenciaSaldoShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the transferenciaSaldoList where timestamp equals to UPDATED_TIMESTAMP
        defaultTransferenciaSaldoShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultTransferenciaSaldoShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the transferenciaSaldoList where timestamp equals to UPDATED_TIMESTAMP
        defaultTransferenciaSaldoShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where timestamp is not null
        defaultTransferenciaSaldoShouldBeFound("timestamp.specified=true");

        // Get all the transferenciaSaldoList where timestamp is null
        defaultTransferenciaSaldoShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultTransferenciaSaldoShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the transferenciaSaldoList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultTransferenciaSaldoShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultTransferenciaSaldoShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the transferenciaSaldoList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultTransferenciaSaldoShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where timestamp is less than DEFAULT_TIMESTAMP
        defaultTransferenciaSaldoShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the transferenciaSaldoList where timestamp is less than UPDATED_TIMESTAMP
        defaultTransferenciaSaldoShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        // Get all the transferenciaSaldoList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultTransferenciaSaldoShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the transferenciaSaldoList where timestamp is greater than SMALLER_TIMESTAMP
        defaultTransferenciaSaldoShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByDeIsEqualToSomething() throws Exception {
        Discente de;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);
            de = DiscenteResourceIT.createEntity(em);
        } else {
            de = TestUtil.findAll(em, Discente.class).get(0);
        }
        em.persist(de);
        em.flush();
        transferenciaSaldo.setDe(de);
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);
        Long deId = de.getId();

        // Get all the transferenciaSaldoList where de equals to deId
        defaultTransferenciaSaldoShouldBeFound("deId.equals=" + deId);

        // Get all the transferenciaSaldoList where de equals to (deId + 1)
        defaultTransferenciaSaldoShouldNotBeFound("deId.equals=" + (deId + 1));
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByParaIsEqualToSomething() throws Exception {
        Discente para;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);
            para = DiscenteResourceIT.createEntity(em);
        } else {
            para = TestUtil.findAll(em, Discente.class).get(0);
        }
        em.persist(para);
        em.flush();
        transferenciaSaldo.setPara(para);
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);
        Long paraId = para.getId();

        // Get all the transferenciaSaldoList where para equals to paraId
        defaultTransferenciaSaldoShouldBeFound("paraId.equals=" + paraId);

        // Get all the transferenciaSaldoList where para equals to (paraId + 1)
        defaultTransferenciaSaldoShouldNotBeFound("paraId.equals=" + (paraId + 1));
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        transferenciaSaldo.setUtilizador(utilizador);
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);
        Long utilizadorId = utilizador.getId();

        // Get all the transferenciaSaldoList where utilizador equals to utilizadorId
        defaultTransferenciaSaldoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the transferenciaSaldoList where utilizador equals to (utilizadorId + 1)
        defaultTransferenciaSaldoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByMotivoTransferenciaIsEqualToSomething() throws Exception {
        LookupItem motivoTransferencia;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);
            motivoTransferencia = LookupItemResourceIT.createEntity(em);
        } else {
            motivoTransferencia = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(motivoTransferencia);
        em.flush();
        transferenciaSaldo.setMotivoTransferencia(motivoTransferencia);
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);
        Long motivoTransferenciaId = motivoTransferencia.getId();

        // Get all the transferenciaSaldoList where motivoTransferencia equals to motivoTransferenciaId
        defaultTransferenciaSaldoShouldBeFound("motivoTransferenciaId.equals=" + motivoTransferenciaId);

        // Get all the transferenciaSaldoList where motivoTransferencia equals to (motivoTransferenciaId + 1)
        defaultTransferenciaSaldoShouldNotBeFound("motivoTransferenciaId.equals=" + (motivoTransferenciaId + 1));
    }

    @Test
    @Transactional
    void getAllTransferenciaSaldosByTransacoesIsEqualToSomething() throws Exception {
        Transacao transacoes;
        if (TestUtil.findAll(em, Transacao.class).isEmpty()) {
            transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);
            transacoes = TransacaoResourceIT.createEntity(em);
        } else {
            transacoes = TestUtil.findAll(em, Transacao.class).get(0);
        }
        em.persist(transacoes);
        em.flush();
        transferenciaSaldo.addTransacoes(transacoes);
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);
        Long transacoesId = transacoes.getId();

        // Get all the transferenciaSaldoList where transacoes equals to transacoesId
        defaultTransferenciaSaldoShouldBeFound("transacoesId.equals=" + transacoesId);

        // Get all the transferenciaSaldoList where transacoes equals to (transacoesId + 1)
        defaultTransferenciaSaldoShouldNotBeFound("transacoesId.equals=" + (transacoesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransferenciaSaldoShouldBeFound(String filter) throws Exception {
        restTransferenciaSaldoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferenciaSaldo.getId().intValue())))
            .andExpect(jsonPath("$.[*].montante").value(hasItem(sameNumber(DEFAULT_MONTANTE))))
            .andExpect(jsonPath("$.[*].isMesmaConta").value(hasItem(DEFAULT_IS_MESMA_CONTA.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restTransferenciaSaldoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransferenciaSaldoShouldNotBeFound(String filter) throws Exception {
        restTransferenciaSaldoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransferenciaSaldoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransferenciaSaldo() throws Exception {
        // Get the transferenciaSaldo
        restTransferenciaSaldoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransferenciaSaldo() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        int databaseSizeBeforeUpdate = transferenciaSaldoRepository.findAll().size();

        // Update the transferenciaSaldo
        TransferenciaSaldo updatedTransferenciaSaldo = transferenciaSaldoRepository.findById(transferenciaSaldo.getId()).get();
        // Disconnect from session so that the updates on updatedTransferenciaSaldo are not directly saved in db
        em.detach(updatedTransferenciaSaldo);
        updatedTransferenciaSaldo
            .montante(UPDATED_MONTANTE)
            .isMesmaConta(UPDATED_IS_MESMA_CONTA)
            .descricao(UPDATED_DESCRICAO)
            .timestamp(UPDATED_TIMESTAMP);
        TransferenciaSaldoDTO transferenciaSaldoDTO = transferenciaSaldoMapper.toDto(updatedTransferenciaSaldo);

        restTransferenciaSaldoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferenciaSaldoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaSaldoDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransferenciaSaldo in the database
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeUpdate);
        TransferenciaSaldo testTransferenciaSaldo = transferenciaSaldoList.get(transferenciaSaldoList.size() - 1);
        assertThat(testTransferenciaSaldo.getMontante()).isEqualByComparingTo(UPDATED_MONTANTE);
        assertThat(testTransferenciaSaldo.getIsMesmaConta()).isEqualTo(UPDATED_IS_MESMA_CONTA);
        assertThat(testTransferenciaSaldo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTransferenciaSaldo.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingTransferenciaSaldo() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaSaldoRepository.findAll().size();
        transferenciaSaldo.setId(count.incrementAndGet());

        // Create the TransferenciaSaldo
        TransferenciaSaldoDTO transferenciaSaldoDTO = transferenciaSaldoMapper.toDto(transferenciaSaldo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferenciaSaldoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferenciaSaldoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaSaldoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaSaldo in the database
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransferenciaSaldo() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaSaldoRepository.findAll().size();
        transferenciaSaldo.setId(count.incrementAndGet());

        // Create the TransferenciaSaldo
        TransferenciaSaldoDTO transferenciaSaldoDTO = transferenciaSaldoMapper.toDto(transferenciaSaldo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferenciaSaldoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaSaldoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaSaldo in the database
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransferenciaSaldo() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaSaldoRepository.findAll().size();
        transferenciaSaldo.setId(count.incrementAndGet());

        // Create the TransferenciaSaldo
        TransferenciaSaldoDTO transferenciaSaldoDTO = transferenciaSaldoMapper.toDto(transferenciaSaldo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferenciaSaldoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaSaldoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferenciaSaldo in the database
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransferenciaSaldoWithPatch() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        int databaseSizeBeforeUpdate = transferenciaSaldoRepository.findAll().size();

        // Update the transferenciaSaldo using partial update
        TransferenciaSaldo partialUpdatedTransferenciaSaldo = new TransferenciaSaldo();
        partialUpdatedTransferenciaSaldo.setId(transferenciaSaldo.getId());

        partialUpdatedTransferenciaSaldo.montante(UPDATED_MONTANTE).descricao(UPDATED_DESCRICAO);

        restTransferenciaSaldoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferenciaSaldo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferenciaSaldo))
            )
            .andExpect(status().isOk());

        // Validate the TransferenciaSaldo in the database
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeUpdate);
        TransferenciaSaldo testTransferenciaSaldo = transferenciaSaldoList.get(transferenciaSaldoList.size() - 1);
        assertThat(testTransferenciaSaldo.getMontante()).isEqualByComparingTo(UPDATED_MONTANTE);
        assertThat(testTransferenciaSaldo.getIsMesmaConta()).isEqualTo(DEFAULT_IS_MESMA_CONTA);
        assertThat(testTransferenciaSaldo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTransferenciaSaldo.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateTransferenciaSaldoWithPatch() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        int databaseSizeBeforeUpdate = transferenciaSaldoRepository.findAll().size();

        // Update the transferenciaSaldo using partial update
        TransferenciaSaldo partialUpdatedTransferenciaSaldo = new TransferenciaSaldo();
        partialUpdatedTransferenciaSaldo.setId(transferenciaSaldo.getId());

        partialUpdatedTransferenciaSaldo
            .montante(UPDATED_MONTANTE)
            .isMesmaConta(UPDATED_IS_MESMA_CONTA)
            .descricao(UPDATED_DESCRICAO)
            .timestamp(UPDATED_TIMESTAMP);

        restTransferenciaSaldoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferenciaSaldo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferenciaSaldo))
            )
            .andExpect(status().isOk());

        // Validate the TransferenciaSaldo in the database
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeUpdate);
        TransferenciaSaldo testTransferenciaSaldo = transferenciaSaldoList.get(transferenciaSaldoList.size() - 1);
        assertThat(testTransferenciaSaldo.getMontante()).isEqualByComparingTo(UPDATED_MONTANTE);
        assertThat(testTransferenciaSaldo.getIsMesmaConta()).isEqualTo(UPDATED_IS_MESMA_CONTA);
        assertThat(testTransferenciaSaldo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTransferenciaSaldo.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingTransferenciaSaldo() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaSaldoRepository.findAll().size();
        transferenciaSaldo.setId(count.incrementAndGet());

        // Create the TransferenciaSaldo
        TransferenciaSaldoDTO transferenciaSaldoDTO = transferenciaSaldoMapper.toDto(transferenciaSaldo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferenciaSaldoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transferenciaSaldoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaSaldoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaSaldo in the database
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransferenciaSaldo() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaSaldoRepository.findAll().size();
        transferenciaSaldo.setId(count.incrementAndGet());

        // Create the TransferenciaSaldo
        TransferenciaSaldoDTO transferenciaSaldoDTO = transferenciaSaldoMapper.toDto(transferenciaSaldo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferenciaSaldoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaSaldoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaSaldo in the database
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransferenciaSaldo() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaSaldoRepository.findAll().size();
        transferenciaSaldo.setId(count.incrementAndGet());

        // Create the TransferenciaSaldo
        TransferenciaSaldoDTO transferenciaSaldoDTO = transferenciaSaldoMapper.toDto(transferenciaSaldo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferenciaSaldoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaSaldoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferenciaSaldo in the database
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransferenciaSaldo() throws Exception {
        // Initialize the database
        transferenciaSaldoRepository.saveAndFlush(transferenciaSaldo);

        int databaseSizeBeforeDelete = transferenciaSaldoRepository.findAll().size();

        // Delete the transferenciaSaldo
        restTransferenciaSaldoMockMvc
            .perform(delete(ENTITY_API_URL_ID, transferenciaSaldo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransferenciaSaldo> transferenciaSaldoList = transferenciaSaldoRepository.findAll();
        assertThat(transferenciaSaldoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
