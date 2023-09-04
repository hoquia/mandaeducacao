package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.domain.ResumoImpostoFactura;
import com.ravunana.longonkelo.repository.ResumoImpostoFacturaRepository;
import com.ravunana.longonkelo.service.ResumoImpostoFacturaService;
import com.ravunana.longonkelo.service.dto.ResumoImpostoFacturaDTO;
import com.ravunana.longonkelo.service.mapper.ResumoImpostoFacturaMapper;
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

/**
 * Integration tests for the {@link ResumoImpostoFacturaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ResumoImpostoFacturaResourceIT {

    private static final Boolean DEFAULT_IS_RETENCAO = false;
    private static final Boolean UPDATED_IS_RETENCAO = true;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TAXA = new BigDecimal(0);
    private static final BigDecimal UPDATED_TAXA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_INCIDENCIA = new BigDecimal(0);
    private static final BigDecimal UPDATED_INCIDENCIA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_MONTANTE = new BigDecimal(0);
    private static final BigDecimal UPDATED_MONTANTE = new BigDecimal(1);

    private static final String ENTITY_API_URL = "/api/resumo-imposto-facturas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResumoImpostoFacturaRepository resumoImpostoFacturaRepository;

    @Mock
    private ResumoImpostoFacturaRepository resumoImpostoFacturaRepositoryMock;

    @Autowired
    private ResumoImpostoFacturaMapper resumoImpostoFacturaMapper;

    @Mock
    private ResumoImpostoFacturaService resumoImpostoFacturaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResumoImpostoFacturaMockMvc;

    private ResumoImpostoFactura resumoImpostoFactura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumoImpostoFactura createEntity(EntityManager em) {
        ResumoImpostoFactura resumoImpostoFactura = new ResumoImpostoFactura()
            .isRetencao(DEFAULT_IS_RETENCAO)
            .descricao(DEFAULT_DESCRICAO)
            .tipo(DEFAULT_TIPO)
            .taxa(DEFAULT_TAXA)
            .incidencia(DEFAULT_INCIDENCIA)
            .montante(DEFAULT_MONTANTE);
        // Add required entity
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            factura = FacturaResourceIT.createEntity(em);
            em.persist(factura);
            em.flush();
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        resumoImpostoFactura.setFactura(factura);
        return resumoImpostoFactura;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumoImpostoFactura createUpdatedEntity(EntityManager em) {
        ResumoImpostoFactura resumoImpostoFactura = new ResumoImpostoFactura()
            .isRetencao(UPDATED_IS_RETENCAO)
            .descricao(UPDATED_DESCRICAO)
            .tipo(UPDATED_TIPO)
            .taxa(UPDATED_TAXA)
            .incidencia(UPDATED_INCIDENCIA)
            .montante(UPDATED_MONTANTE);
        // Add required entity
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            factura = FacturaResourceIT.createUpdatedEntity(em);
            em.persist(factura);
            em.flush();
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        resumoImpostoFactura.setFactura(factura);
        return resumoImpostoFactura;
    }

    @BeforeEach
    public void initTest() {
        resumoImpostoFactura = createEntity(em);
    }

    @Test
    @Transactional
    void createResumoImpostoFactura() throws Exception {
        int databaseSizeBeforeCreate = resumoImpostoFacturaRepository.findAll().size();
        // Create the ResumoImpostoFactura
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);
        restResumoImpostoFacturaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResumoImpostoFactura in the database
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeCreate + 1);
        ResumoImpostoFactura testResumoImpostoFactura = resumoImpostoFacturaList.get(resumoImpostoFacturaList.size() - 1);
        assertThat(testResumoImpostoFactura.getIsRetencao()).isEqualTo(DEFAULT_IS_RETENCAO);
        assertThat(testResumoImpostoFactura.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testResumoImpostoFactura.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testResumoImpostoFactura.getTaxa()).isEqualByComparingTo(DEFAULT_TAXA);
        assertThat(testResumoImpostoFactura.getIncidencia()).isEqualByComparingTo(DEFAULT_INCIDENCIA);
        assertThat(testResumoImpostoFactura.getMontante()).isEqualByComparingTo(DEFAULT_MONTANTE);
    }

    @Test
    @Transactional
    void createResumoImpostoFacturaWithExistingId() throws Exception {
        // Create the ResumoImpostoFactura with an existing ID
        resumoImpostoFactura.setId(1L);
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        int databaseSizeBeforeCreate = resumoImpostoFacturaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumoImpostoFacturaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumoImpostoFactura in the database
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoImpostoFacturaRepository.findAll().size();
        // set the field null
        resumoImpostoFactura.setDescricao(null);

        // Create the ResumoImpostoFactura, which fails.
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        restResumoImpostoFacturaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoImpostoFacturaRepository.findAll().size();
        // set the field null
        resumoImpostoFactura.setTipo(null);

        // Create the ResumoImpostoFactura, which fails.
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        restResumoImpostoFacturaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxaIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoImpostoFacturaRepository.findAll().size();
        // set the field null
        resumoImpostoFactura.setTaxa(null);

        // Create the ResumoImpostoFactura, which fails.
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        restResumoImpostoFacturaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIncidenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoImpostoFacturaRepository.findAll().size();
        // set the field null
        resumoImpostoFactura.setIncidencia(null);

        // Create the ResumoImpostoFactura, which fails.
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        restResumoImpostoFacturaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumoImpostoFacturaRepository.findAll().size();
        // set the field null
        resumoImpostoFactura.setMontante(null);

        // Create the ResumoImpostoFactura, which fails.
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        restResumoImpostoFacturaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResumoImpostoFacturas() throws Exception {
        // Initialize the database
        resumoImpostoFacturaRepository.saveAndFlush(resumoImpostoFactura);

        // Get all the resumoImpostoFacturaList
        restResumoImpostoFacturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumoImpostoFactura.getId().intValue())))
            .andExpect(jsonPath("$.[*].isRetencao").value(hasItem(DEFAULT_IS_RETENCAO.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].taxa").value(hasItem(sameNumber(DEFAULT_TAXA))))
            .andExpect(jsonPath("$.[*].incidencia").value(hasItem(sameNumber(DEFAULT_INCIDENCIA))))
            .andExpect(jsonPath("$.[*].montante").value(hasItem(sameNumber(DEFAULT_MONTANTE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResumoImpostoFacturasWithEagerRelationshipsIsEnabled() throws Exception {
        when(resumoImpostoFacturaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResumoImpostoFacturaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(resumoImpostoFacturaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResumoImpostoFacturasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(resumoImpostoFacturaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResumoImpostoFacturaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(resumoImpostoFacturaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getResumoImpostoFactura() throws Exception {
        // Initialize the database
        resumoImpostoFacturaRepository.saveAndFlush(resumoImpostoFactura);

        // Get the resumoImpostoFactura
        restResumoImpostoFacturaMockMvc
            .perform(get(ENTITY_API_URL_ID, resumoImpostoFactura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resumoImpostoFactura.getId().intValue()))
            .andExpect(jsonPath("$.isRetencao").value(DEFAULT_IS_RETENCAO.booleanValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.taxa").value(sameNumber(DEFAULT_TAXA)))
            .andExpect(jsonPath("$.incidencia").value(sameNumber(DEFAULT_INCIDENCIA)))
            .andExpect(jsonPath("$.montante").value(sameNumber(DEFAULT_MONTANTE)));
    }

    @Test
    @Transactional
    void getNonExistingResumoImpostoFactura() throws Exception {
        // Get the resumoImpostoFactura
        restResumoImpostoFacturaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResumoImpostoFactura() throws Exception {
        // Initialize the database
        resumoImpostoFacturaRepository.saveAndFlush(resumoImpostoFactura);

        int databaseSizeBeforeUpdate = resumoImpostoFacturaRepository.findAll().size();

        // Update the resumoImpostoFactura
        ResumoImpostoFactura updatedResumoImpostoFactura = resumoImpostoFacturaRepository.findById(resumoImpostoFactura.getId()).get();
        // Disconnect from session so that the updates on updatedResumoImpostoFactura are not directly saved in db
        em.detach(updatedResumoImpostoFactura);
        updatedResumoImpostoFactura
            .isRetencao(UPDATED_IS_RETENCAO)
            .descricao(UPDATED_DESCRICAO)
            .tipo(UPDATED_TIPO)
            .taxa(UPDATED_TAXA)
            .incidencia(UPDATED_INCIDENCIA)
            .montante(UPDATED_MONTANTE);
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(updatedResumoImpostoFactura);

        restResumoImpostoFacturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumoImpostoFacturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResumoImpostoFactura in the database
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeUpdate);
        ResumoImpostoFactura testResumoImpostoFactura = resumoImpostoFacturaList.get(resumoImpostoFacturaList.size() - 1);
        assertThat(testResumoImpostoFactura.getIsRetencao()).isEqualTo(UPDATED_IS_RETENCAO);
        assertThat(testResumoImpostoFactura.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResumoImpostoFactura.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testResumoImpostoFactura.getTaxa()).isEqualByComparingTo(UPDATED_TAXA);
        assertThat(testResumoImpostoFactura.getIncidencia()).isEqualByComparingTo(UPDATED_INCIDENCIA);
        assertThat(testResumoImpostoFactura.getMontante()).isEqualByComparingTo(UPDATED_MONTANTE);
    }

    @Test
    @Transactional
    void putNonExistingResumoImpostoFactura() throws Exception {
        int databaseSizeBeforeUpdate = resumoImpostoFacturaRepository.findAll().size();
        resumoImpostoFactura.setId(count.incrementAndGet());

        // Create the ResumoImpostoFactura
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumoImpostoFacturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resumoImpostoFacturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumoImpostoFactura in the database
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResumoImpostoFactura() throws Exception {
        int databaseSizeBeforeUpdate = resumoImpostoFacturaRepository.findAll().size();
        resumoImpostoFactura.setId(count.incrementAndGet());

        // Create the ResumoImpostoFactura
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumoImpostoFacturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumoImpostoFactura in the database
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResumoImpostoFactura() throws Exception {
        int databaseSizeBeforeUpdate = resumoImpostoFacturaRepository.findAll().size();
        resumoImpostoFactura.setId(count.incrementAndGet());

        // Create the ResumoImpostoFactura
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumoImpostoFacturaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumoImpostoFactura in the database
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResumoImpostoFacturaWithPatch() throws Exception {
        // Initialize the database
        resumoImpostoFacturaRepository.saveAndFlush(resumoImpostoFactura);

        int databaseSizeBeforeUpdate = resumoImpostoFacturaRepository.findAll().size();

        // Update the resumoImpostoFactura using partial update
        ResumoImpostoFactura partialUpdatedResumoImpostoFactura = new ResumoImpostoFactura();
        partialUpdatedResumoImpostoFactura.setId(resumoImpostoFactura.getId());

        partialUpdatedResumoImpostoFactura.taxa(UPDATED_TAXA);

        restResumoImpostoFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumoImpostoFactura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumoImpostoFactura))
            )
            .andExpect(status().isOk());

        // Validate the ResumoImpostoFactura in the database
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeUpdate);
        ResumoImpostoFactura testResumoImpostoFactura = resumoImpostoFacturaList.get(resumoImpostoFacturaList.size() - 1);
        assertThat(testResumoImpostoFactura.getIsRetencao()).isEqualTo(DEFAULT_IS_RETENCAO);
        assertThat(testResumoImpostoFactura.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testResumoImpostoFactura.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testResumoImpostoFactura.getTaxa()).isEqualByComparingTo(UPDATED_TAXA);
        assertThat(testResumoImpostoFactura.getIncidencia()).isEqualByComparingTo(DEFAULT_INCIDENCIA);
        assertThat(testResumoImpostoFactura.getMontante()).isEqualByComparingTo(DEFAULT_MONTANTE);
    }

    @Test
    @Transactional
    void fullUpdateResumoImpostoFacturaWithPatch() throws Exception {
        // Initialize the database
        resumoImpostoFacturaRepository.saveAndFlush(resumoImpostoFactura);

        int databaseSizeBeforeUpdate = resumoImpostoFacturaRepository.findAll().size();

        // Update the resumoImpostoFactura using partial update
        ResumoImpostoFactura partialUpdatedResumoImpostoFactura = new ResumoImpostoFactura();
        partialUpdatedResumoImpostoFactura.setId(resumoImpostoFactura.getId());

        partialUpdatedResumoImpostoFactura
            .isRetencao(UPDATED_IS_RETENCAO)
            .descricao(UPDATED_DESCRICAO)
            .tipo(UPDATED_TIPO)
            .taxa(UPDATED_TAXA)
            .incidencia(UPDATED_INCIDENCIA)
            .montante(UPDATED_MONTANTE);

        restResumoImpostoFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResumoImpostoFactura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResumoImpostoFactura))
            )
            .andExpect(status().isOk());

        // Validate the ResumoImpostoFactura in the database
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeUpdate);
        ResumoImpostoFactura testResumoImpostoFactura = resumoImpostoFacturaList.get(resumoImpostoFacturaList.size() - 1);
        assertThat(testResumoImpostoFactura.getIsRetencao()).isEqualTo(UPDATED_IS_RETENCAO);
        assertThat(testResumoImpostoFactura.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResumoImpostoFactura.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testResumoImpostoFactura.getTaxa()).isEqualByComparingTo(UPDATED_TAXA);
        assertThat(testResumoImpostoFactura.getIncidencia()).isEqualByComparingTo(UPDATED_INCIDENCIA);
        assertThat(testResumoImpostoFactura.getMontante()).isEqualByComparingTo(UPDATED_MONTANTE);
    }

    @Test
    @Transactional
    void patchNonExistingResumoImpostoFactura() throws Exception {
        int databaseSizeBeforeUpdate = resumoImpostoFacturaRepository.findAll().size();
        resumoImpostoFactura.setId(count.incrementAndGet());

        // Create the ResumoImpostoFactura
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumoImpostoFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resumoImpostoFacturaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumoImpostoFactura in the database
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResumoImpostoFactura() throws Exception {
        int databaseSizeBeforeUpdate = resumoImpostoFacturaRepository.findAll().size();
        resumoImpostoFactura.setId(count.incrementAndGet());

        // Create the ResumoImpostoFactura
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumoImpostoFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResumoImpostoFactura in the database
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResumoImpostoFactura() throws Exception {
        int databaseSizeBeforeUpdate = resumoImpostoFacturaRepository.findAll().size();
        resumoImpostoFactura.setId(count.incrementAndGet());

        // Create the ResumoImpostoFactura
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResumoImpostoFacturaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resumoImpostoFacturaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResumoImpostoFactura in the database
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResumoImpostoFactura() throws Exception {
        // Initialize the database
        resumoImpostoFacturaRepository.saveAndFlush(resumoImpostoFactura);

        int databaseSizeBeforeDelete = resumoImpostoFacturaRepository.findAll().size();

        // Delete the resumoImpostoFactura
        restResumoImpostoFacturaMockMvc
            .perform(delete(ENTITY_API_URL_ID, resumoImpostoFactura.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResumoImpostoFactura> resumoImpostoFacturaList = resumoImpostoFacturaRepository.findAll();
        assertThat(resumoImpostoFacturaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
