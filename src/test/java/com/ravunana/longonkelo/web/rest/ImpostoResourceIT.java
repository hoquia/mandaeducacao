package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Imposto;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.repository.ImpostoRepository;
import com.ravunana.longonkelo.service.ImpostoService;
import com.ravunana.longonkelo.service.dto.ImpostoDTO;
import com.ravunana.longonkelo.service.mapper.ImpostoMapper;
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
 * Integration tests for the {@link ImpostoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ImpostoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final Double DEFAULT_TAXA = 0D;
    private static final Double UPDATED_TAXA = 1D;

    private static final Boolean DEFAULT_IS_RETENCAO = false;
    private static final Boolean UPDATED_IS_RETENCAO = true;

    private static final String DEFAULT_MOTIVO_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIVO_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO_CODIGO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/impostos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ImpostoRepository impostoRepository;

    @Mock
    private ImpostoRepository impostoRepositoryMock;

    @Autowired
    private ImpostoMapper impostoMapper;

    @Mock
    private ImpostoService impostoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImpostoMockMvc;

    private Imposto imposto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imposto createEntity(EntityManager em) {
        Imposto imposto = new Imposto()
            .descricao(DEFAULT_DESCRICAO)
            .pais(DEFAULT_PAIS)
            .taxa(DEFAULT_TAXA)
            .isRetencao(DEFAULT_IS_RETENCAO)
            .motivoDescricao(DEFAULT_MOTIVO_DESCRICAO)
            .motivoCodigo(DEFAULT_MOTIVO_CODIGO);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        imposto.setTipoImposto(lookupItem);
        // Add required entity
        imposto.setCodigoImposto(lookupItem);
        return imposto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imposto createUpdatedEntity(EntityManager em) {
        Imposto imposto = new Imposto()
            .descricao(UPDATED_DESCRICAO)
            .pais(UPDATED_PAIS)
            .taxa(UPDATED_TAXA)
            .isRetencao(UPDATED_IS_RETENCAO)
            .motivoDescricao(UPDATED_MOTIVO_DESCRICAO)
            .motivoCodigo(UPDATED_MOTIVO_CODIGO);
        // Add required entity
        LookupItem lookupItem;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            lookupItem = LookupItemResourceIT.createUpdatedEntity(em);
            em.persist(lookupItem);
            em.flush();
        } else {
            lookupItem = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        imposto.setTipoImposto(lookupItem);
        // Add required entity
        imposto.setCodigoImposto(lookupItem);
        return imposto;
    }

    @BeforeEach
    public void initTest() {
        imposto = createEntity(em);
    }

    @Test
    @Transactional
    void createImposto() throws Exception {
        int databaseSizeBeforeCreate = impostoRepository.findAll().size();
        // Create the Imposto
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);
        restImpostoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isCreated());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeCreate + 1);
        Imposto testImposto = impostoList.get(impostoList.size() - 1);
        assertThat(testImposto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testImposto.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testImposto.getTaxa()).isEqualTo(DEFAULT_TAXA);
        assertThat(testImposto.getIsRetencao()).isEqualTo(DEFAULT_IS_RETENCAO);
        assertThat(testImposto.getMotivoDescricao()).isEqualTo(DEFAULT_MOTIVO_DESCRICAO);
        assertThat(testImposto.getMotivoCodigo()).isEqualTo(DEFAULT_MOTIVO_CODIGO);
    }

    @Test
    @Transactional
    void createImpostoWithExistingId() throws Exception {
        // Create the Imposto with an existing ID
        imposto.setId(1L);
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        int databaseSizeBeforeCreate = impostoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpostoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = impostoRepository.findAll().size();
        // set the field null
        imposto.setDescricao(null);

        // Create the Imposto, which fails.
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        restImpostoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isBadRequest());

        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxaIsRequired() throws Exception {
        int databaseSizeBeforeTest = impostoRepository.findAll().size();
        // set the field null
        imposto.setTaxa(null);

        // Create the Imposto, which fails.
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        restImpostoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isBadRequest());

        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllImpostos() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList
        restImpostoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imposto.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].taxa").value(hasItem(DEFAULT_TAXA.doubleValue())))
            .andExpect(jsonPath("$.[*].isRetencao").value(hasItem(DEFAULT_IS_RETENCAO.booleanValue())))
            .andExpect(jsonPath("$.[*].motivoDescricao").value(hasItem(DEFAULT_MOTIVO_DESCRICAO)))
            .andExpect(jsonPath("$.[*].motivoCodigo").value(hasItem(DEFAULT_MOTIVO_CODIGO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpostosWithEagerRelationshipsIsEnabled() throws Exception {
        when(impostoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpostoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(impostoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpostosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(impostoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpostoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(impostoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getImposto() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get the imposto
        restImpostoMockMvc
            .perform(get(ENTITY_API_URL_ID, imposto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imposto.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.taxa").value(DEFAULT_TAXA.doubleValue()))
            .andExpect(jsonPath("$.isRetencao").value(DEFAULT_IS_RETENCAO.booleanValue()))
            .andExpect(jsonPath("$.motivoDescricao").value(DEFAULT_MOTIVO_DESCRICAO))
            .andExpect(jsonPath("$.motivoCodigo").value(DEFAULT_MOTIVO_CODIGO));
    }

    @Test
    @Transactional
    void getNonExistingImposto() throws Exception {
        // Get the imposto
        restImpostoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImposto() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        int databaseSizeBeforeUpdate = impostoRepository.findAll().size();

        // Update the imposto
        Imposto updatedImposto = impostoRepository.findById(imposto.getId()).get();
        // Disconnect from session so that the updates on updatedImposto are not directly saved in db
        em.detach(updatedImposto);
        updatedImposto
            .descricao(UPDATED_DESCRICAO)
            .pais(UPDATED_PAIS)
            .taxa(UPDATED_TAXA)
            .isRetencao(UPDATED_IS_RETENCAO)
            .motivoDescricao(UPDATED_MOTIVO_DESCRICAO)
            .motivoCodigo(UPDATED_MOTIVO_CODIGO);
        ImpostoDTO impostoDTO = impostoMapper.toDto(updatedImposto);

        restImpostoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, impostoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(impostoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeUpdate);
        Imposto testImposto = impostoList.get(impostoList.size() - 1);
        assertThat(testImposto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testImposto.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testImposto.getTaxa()).isEqualTo(UPDATED_TAXA);
        assertThat(testImposto.getIsRetencao()).isEqualTo(UPDATED_IS_RETENCAO);
        assertThat(testImposto.getMotivoDescricao()).isEqualTo(UPDATED_MOTIVO_DESCRICAO);
        assertThat(testImposto.getMotivoCodigo()).isEqualTo(UPDATED_MOTIVO_CODIGO);
    }

    @Test
    @Transactional
    void putNonExistingImposto() throws Exception {
        int databaseSizeBeforeUpdate = impostoRepository.findAll().size();
        imposto.setId(count.incrementAndGet());

        // Create the Imposto
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, impostoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(impostoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImposto() throws Exception {
        int databaseSizeBeforeUpdate = impostoRepository.findAll().size();
        imposto.setId(count.incrementAndGet());

        // Create the Imposto
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(impostoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImposto() throws Exception {
        int databaseSizeBeforeUpdate = impostoRepository.findAll().size();
        imposto.setId(count.incrementAndGet());

        // Create the Imposto
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImpostoWithPatch() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        int databaseSizeBeforeUpdate = impostoRepository.findAll().size();

        // Update the imposto using partial update
        Imposto partialUpdatedImposto = new Imposto();
        partialUpdatedImposto.setId(imposto.getId());

        partialUpdatedImposto
            .descricao(UPDATED_DESCRICAO)
            .isRetencao(UPDATED_IS_RETENCAO)
            .motivoDescricao(UPDATED_MOTIVO_DESCRICAO)
            .motivoCodigo(UPDATED_MOTIVO_CODIGO);

        restImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImposto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImposto))
            )
            .andExpect(status().isOk());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeUpdate);
        Imposto testImposto = impostoList.get(impostoList.size() - 1);
        assertThat(testImposto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testImposto.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testImposto.getTaxa()).isEqualTo(DEFAULT_TAXA);
        assertThat(testImposto.getIsRetencao()).isEqualTo(UPDATED_IS_RETENCAO);
        assertThat(testImposto.getMotivoDescricao()).isEqualTo(UPDATED_MOTIVO_DESCRICAO);
        assertThat(testImposto.getMotivoCodigo()).isEqualTo(UPDATED_MOTIVO_CODIGO);
    }

    @Test
    @Transactional
    void fullUpdateImpostoWithPatch() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        int databaseSizeBeforeUpdate = impostoRepository.findAll().size();

        // Update the imposto using partial update
        Imposto partialUpdatedImposto = new Imposto();
        partialUpdatedImposto.setId(imposto.getId());

        partialUpdatedImposto
            .descricao(UPDATED_DESCRICAO)
            .pais(UPDATED_PAIS)
            .taxa(UPDATED_TAXA)
            .isRetencao(UPDATED_IS_RETENCAO)
            .motivoDescricao(UPDATED_MOTIVO_DESCRICAO)
            .motivoCodigo(UPDATED_MOTIVO_CODIGO);

        restImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImposto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImposto))
            )
            .andExpect(status().isOk());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeUpdate);
        Imposto testImposto = impostoList.get(impostoList.size() - 1);
        assertThat(testImposto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testImposto.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testImposto.getTaxa()).isEqualTo(UPDATED_TAXA);
        assertThat(testImposto.getIsRetencao()).isEqualTo(UPDATED_IS_RETENCAO);
        assertThat(testImposto.getMotivoDescricao()).isEqualTo(UPDATED_MOTIVO_DESCRICAO);
        assertThat(testImposto.getMotivoCodigo()).isEqualTo(UPDATED_MOTIVO_CODIGO);
    }

    @Test
    @Transactional
    void patchNonExistingImposto() throws Exception {
        int databaseSizeBeforeUpdate = impostoRepository.findAll().size();
        imposto.setId(count.incrementAndGet());

        // Create the Imposto
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, impostoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(impostoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImposto() throws Exception {
        int databaseSizeBeforeUpdate = impostoRepository.findAll().size();
        imposto.setId(count.incrementAndGet());

        // Create the Imposto
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(impostoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImposto() throws Exception {
        int databaseSizeBeforeUpdate = impostoRepository.findAll().size();
        imposto.setId(count.incrementAndGet());

        // Create the Imposto
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpostoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(impostoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImposto() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        int databaseSizeBeforeDelete = impostoRepository.findAll().size();

        // Delete the imposto
        restImpostoMockMvc
            .perform(delete(ENTITY_API_URL_ID, imposto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
