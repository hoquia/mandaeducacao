package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static com.ravunana.longonkelo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AplicacaoRecibo;
import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.domain.Recibo;
import com.ravunana.longonkelo.repository.AplicacaoReciboRepository;
import com.ravunana.longonkelo.service.AplicacaoReciboService;
import com.ravunana.longonkelo.service.dto.AplicacaoReciboDTO;
import com.ravunana.longonkelo.service.mapper.AplicacaoReciboMapper;
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

/**
 * Integration tests for the {@link AplicacaoReciboResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AplicacaoReciboResourceIT {

    private static final BigDecimal DEFAULT_TOTAL_FACTURA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_FACTURA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTAL_PAGO = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_PAGO = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TOTAL_DIFERENCA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_DIFERENCA = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/aplicacao-recibos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AplicacaoReciboRepository aplicacaoReciboRepository;

    @Mock
    private AplicacaoReciboRepository aplicacaoReciboRepositoryMock;

    @Autowired
    private AplicacaoReciboMapper aplicacaoReciboMapper;

    @Mock
    private AplicacaoReciboService aplicacaoReciboServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAplicacaoReciboMockMvc;

    private AplicacaoRecibo aplicacaoRecibo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AplicacaoRecibo createEntity(EntityManager em) {
        AplicacaoRecibo aplicacaoRecibo = new AplicacaoRecibo()
            .totalFactura(DEFAULT_TOTAL_FACTURA)
            .totalPago(DEFAULT_TOTAL_PAGO)
            .totalDiferenca(DEFAULT_TOTAL_DIFERENCA)
            .timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            factura = FacturaResourceIT.createEntity(em);
            em.persist(factura);
            em.flush();
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        aplicacaoRecibo.setFactura(factura);
        // Add required entity
        Recibo recibo;
        if (TestUtil.findAll(em, Recibo.class).isEmpty()) {
            recibo = ReciboResourceIT.createEntity(em);
            em.persist(recibo);
            em.flush();
        } else {
            recibo = TestUtil.findAll(em, Recibo.class).get(0);
        }
        aplicacaoRecibo.setRecibo(recibo);
        return aplicacaoRecibo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AplicacaoRecibo createUpdatedEntity(EntityManager em) {
        AplicacaoRecibo aplicacaoRecibo = new AplicacaoRecibo()
            .totalFactura(UPDATED_TOTAL_FACTURA)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalDiferenca(UPDATED_TOTAL_DIFERENCA)
            .timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            factura = FacturaResourceIT.createUpdatedEntity(em);
            em.persist(factura);
            em.flush();
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        aplicacaoRecibo.setFactura(factura);
        // Add required entity
        Recibo recibo;
        if (TestUtil.findAll(em, Recibo.class).isEmpty()) {
            recibo = ReciboResourceIT.createUpdatedEntity(em);
            em.persist(recibo);
            em.flush();
        } else {
            recibo = TestUtil.findAll(em, Recibo.class).get(0);
        }
        aplicacaoRecibo.setRecibo(recibo);
        return aplicacaoRecibo;
    }

    @BeforeEach
    public void initTest() {
        aplicacaoRecibo = createEntity(em);
    }

    @Test
    @Transactional
    void createAplicacaoRecibo() throws Exception {
        int databaseSizeBeforeCreate = aplicacaoReciboRepository.findAll().size();
        // Create the AplicacaoRecibo
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(aplicacaoRecibo);
        restAplicacaoReciboMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AplicacaoRecibo in the database
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeCreate + 1);
        AplicacaoRecibo testAplicacaoRecibo = aplicacaoReciboList.get(aplicacaoReciboList.size() - 1);
        assertThat(testAplicacaoRecibo.getTotalFactura()).isEqualByComparingTo(DEFAULT_TOTAL_FACTURA);
        assertThat(testAplicacaoRecibo.getTotalPago()).isEqualByComparingTo(DEFAULT_TOTAL_PAGO);
        assertThat(testAplicacaoRecibo.getTotalDiferenca()).isEqualByComparingTo(DEFAULT_TOTAL_DIFERENCA);
        assertThat(testAplicacaoRecibo.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createAplicacaoReciboWithExistingId() throws Exception {
        // Create the AplicacaoRecibo with an existing ID
        aplicacaoRecibo.setId(1L);
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(aplicacaoRecibo);

        int databaseSizeBeforeCreate = aplicacaoReciboRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAplicacaoReciboMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AplicacaoRecibo in the database
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTotalFacturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = aplicacaoReciboRepository.findAll().size();
        // set the field null
        aplicacaoRecibo.setTotalFactura(null);

        // Create the AplicacaoRecibo, which fails.
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(aplicacaoRecibo);

        restAplicacaoReciboMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = aplicacaoReciboRepository.findAll().size();
        // set the field null
        aplicacaoRecibo.setTotalPago(null);

        // Create the AplicacaoRecibo, which fails.
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(aplicacaoRecibo);

        restAplicacaoReciboMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDiferencaIsRequired() throws Exception {
        int databaseSizeBeforeTest = aplicacaoReciboRepository.findAll().size();
        // set the field null
        aplicacaoRecibo.setTotalDiferenca(null);

        // Create the AplicacaoRecibo, which fails.
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(aplicacaoRecibo);

        restAplicacaoReciboMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAplicacaoRecibos() throws Exception {
        // Initialize the database
        aplicacaoReciboRepository.saveAndFlush(aplicacaoRecibo);

        // Get all the aplicacaoReciboList
        restAplicacaoReciboMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aplicacaoRecibo.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalFactura").value(hasItem(sameNumber(DEFAULT_TOTAL_FACTURA))))
            .andExpect(jsonPath("$.[*].totalPago").value(hasItem(sameNumber(DEFAULT_TOTAL_PAGO))))
            .andExpect(jsonPath("$.[*].totalDiferenca").value(hasItem(sameNumber(DEFAULT_TOTAL_DIFERENCA))))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAplicacaoRecibosWithEagerRelationshipsIsEnabled() throws Exception {
        when(aplicacaoReciboServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAplicacaoReciboMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(aplicacaoReciboServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAplicacaoRecibosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(aplicacaoReciboServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAplicacaoReciboMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(aplicacaoReciboRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAplicacaoRecibo() throws Exception {
        // Initialize the database
        aplicacaoReciboRepository.saveAndFlush(aplicacaoRecibo);

        // Get the aplicacaoRecibo
        restAplicacaoReciboMockMvc
            .perform(get(ENTITY_API_URL_ID, aplicacaoRecibo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aplicacaoRecibo.getId().intValue()))
            .andExpect(jsonPath("$.totalFactura").value(sameNumber(DEFAULT_TOTAL_FACTURA)))
            .andExpect(jsonPath("$.totalPago").value(sameNumber(DEFAULT_TOTAL_PAGO)))
            .andExpect(jsonPath("$.totalDiferenca").value(sameNumber(DEFAULT_TOTAL_DIFERENCA)))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getNonExistingAplicacaoRecibo() throws Exception {
        // Get the aplicacaoRecibo
        restAplicacaoReciboMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAplicacaoRecibo() throws Exception {
        // Initialize the database
        aplicacaoReciboRepository.saveAndFlush(aplicacaoRecibo);

        int databaseSizeBeforeUpdate = aplicacaoReciboRepository.findAll().size();

        // Update the aplicacaoRecibo
        AplicacaoRecibo updatedAplicacaoRecibo = aplicacaoReciboRepository.findById(aplicacaoRecibo.getId()).get();
        // Disconnect from session so that the updates on updatedAplicacaoRecibo are not directly saved in db
        em.detach(updatedAplicacaoRecibo);
        updatedAplicacaoRecibo
            .totalFactura(UPDATED_TOTAL_FACTURA)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalDiferenca(UPDATED_TOTAL_DIFERENCA)
            .timestamp(UPDATED_TIMESTAMP);
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(updatedAplicacaoRecibo);

        restAplicacaoReciboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aplicacaoReciboDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isOk());

        // Validate the AplicacaoRecibo in the database
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeUpdate);
        AplicacaoRecibo testAplicacaoRecibo = aplicacaoReciboList.get(aplicacaoReciboList.size() - 1);
        assertThat(testAplicacaoRecibo.getTotalFactura()).isEqualByComparingTo(UPDATED_TOTAL_FACTURA);
        assertThat(testAplicacaoRecibo.getTotalPago()).isEqualByComparingTo(UPDATED_TOTAL_PAGO);
        assertThat(testAplicacaoRecibo.getTotalDiferenca()).isEqualByComparingTo(UPDATED_TOTAL_DIFERENCA);
        assertThat(testAplicacaoRecibo.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingAplicacaoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = aplicacaoReciboRepository.findAll().size();
        aplicacaoRecibo.setId(count.incrementAndGet());

        // Create the AplicacaoRecibo
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(aplicacaoRecibo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAplicacaoReciboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aplicacaoReciboDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AplicacaoRecibo in the database
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAplicacaoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = aplicacaoReciboRepository.findAll().size();
        aplicacaoRecibo.setId(count.incrementAndGet());

        // Create the AplicacaoRecibo
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(aplicacaoRecibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAplicacaoReciboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AplicacaoRecibo in the database
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAplicacaoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = aplicacaoReciboRepository.findAll().size();
        aplicacaoRecibo.setId(count.incrementAndGet());

        // Create the AplicacaoRecibo
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(aplicacaoRecibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAplicacaoReciboMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AplicacaoRecibo in the database
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAplicacaoReciboWithPatch() throws Exception {
        // Initialize the database
        aplicacaoReciboRepository.saveAndFlush(aplicacaoRecibo);

        int databaseSizeBeforeUpdate = aplicacaoReciboRepository.findAll().size();

        // Update the aplicacaoRecibo using partial update
        AplicacaoRecibo partialUpdatedAplicacaoRecibo = new AplicacaoRecibo();
        partialUpdatedAplicacaoRecibo.setId(aplicacaoRecibo.getId());

        partialUpdatedAplicacaoRecibo
            .totalFactura(UPDATED_TOTAL_FACTURA)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalDiferenca(UPDATED_TOTAL_DIFERENCA)
            .timestamp(UPDATED_TIMESTAMP);

        restAplicacaoReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAplicacaoRecibo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAplicacaoRecibo))
            )
            .andExpect(status().isOk());

        // Validate the AplicacaoRecibo in the database
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeUpdate);
        AplicacaoRecibo testAplicacaoRecibo = aplicacaoReciboList.get(aplicacaoReciboList.size() - 1);
        assertThat(testAplicacaoRecibo.getTotalFactura()).isEqualByComparingTo(UPDATED_TOTAL_FACTURA);
        assertThat(testAplicacaoRecibo.getTotalPago()).isEqualByComparingTo(UPDATED_TOTAL_PAGO);
        assertThat(testAplicacaoRecibo.getTotalDiferenca()).isEqualByComparingTo(UPDATED_TOTAL_DIFERENCA);
        assertThat(testAplicacaoRecibo.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateAplicacaoReciboWithPatch() throws Exception {
        // Initialize the database
        aplicacaoReciboRepository.saveAndFlush(aplicacaoRecibo);

        int databaseSizeBeforeUpdate = aplicacaoReciboRepository.findAll().size();

        // Update the aplicacaoRecibo using partial update
        AplicacaoRecibo partialUpdatedAplicacaoRecibo = new AplicacaoRecibo();
        partialUpdatedAplicacaoRecibo.setId(aplicacaoRecibo.getId());

        partialUpdatedAplicacaoRecibo
            .totalFactura(UPDATED_TOTAL_FACTURA)
            .totalPago(UPDATED_TOTAL_PAGO)
            .totalDiferenca(UPDATED_TOTAL_DIFERENCA)
            .timestamp(UPDATED_TIMESTAMP);

        restAplicacaoReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAplicacaoRecibo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAplicacaoRecibo))
            )
            .andExpect(status().isOk());

        // Validate the AplicacaoRecibo in the database
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeUpdate);
        AplicacaoRecibo testAplicacaoRecibo = aplicacaoReciboList.get(aplicacaoReciboList.size() - 1);
        assertThat(testAplicacaoRecibo.getTotalFactura()).isEqualByComparingTo(UPDATED_TOTAL_FACTURA);
        assertThat(testAplicacaoRecibo.getTotalPago()).isEqualByComparingTo(UPDATED_TOTAL_PAGO);
        assertThat(testAplicacaoRecibo.getTotalDiferenca()).isEqualByComparingTo(UPDATED_TOTAL_DIFERENCA);
        assertThat(testAplicacaoRecibo.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingAplicacaoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = aplicacaoReciboRepository.findAll().size();
        aplicacaoRecibo.setId(count.incrementAndGet());

        // Create the AplicacaoRecibo
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(aplicacaoRecibo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAplicacaoReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aplicacaoReciboDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AplicacaoRecibo in the database
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAplicacaoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = aplicacaoReciboRepository.findAll().size();
        aplicacaoRecibo.setId(count.incrementAndGet());

        // Create the AplicacaoRecibo
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(aplicacaoRecibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAplicacaoReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AplicacaoRecibo in the database
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAplicacaoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = aplicacaoReciboRepository.findAll().size();
        aplicacaoRecibo.setId(count.incrementAndGet());

        // Create the AplicacaoRecibo
        AplicacaoReciboDTO aplicacaoReciboDTO = aplicacaoReciboMapper.toDto(aplicacaoRecibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAplicacaoReciboMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aplicacaoReciboDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AplicacaoRecibo in the database
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAplicacaoRecibo() throws Exception {
        // Initialize the database
        aplicacaoReciboRepository.saveAndFlush(aplicacaoRecibo);

        int databaseSizeBeforeDelete = aplicacaoReciboRepository.findAll().size();

        // Delete the aplicacaoRecibo
        restAplicacaoReciboMockMvc
            .perform(delete(ENTITY_API_URL_ID, aplicacaoRecibo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AplicacaoRecibo> aplicacaoReciboList = aplicacaoReciboRepository.findAll();
        assertThat(aplicacaoReciboList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
