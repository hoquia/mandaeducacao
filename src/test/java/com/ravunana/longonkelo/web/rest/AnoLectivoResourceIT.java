package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.repository.AnoLectivoRepository;
import com.ravunana.longonkelo.service.AnoLectivoService;
import com.ravunana.longonkelo.service.dto.AnoLectivoDTO;
import com.ravunana.longonkelo.service.mapper.AnoLectivoMapper;
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

/**
 * Integration tests for the {@link AnoLectivoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnoLectivoResourceIT {

    private static final Integer DEFAULT_ANO = 1;
    private static final Integer UPDATED_ANO = 2;

    private static final LocalDate DEFAULT_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IS_ACTUAL = false;
    private static final Boolean UPDATED_IS_ACTUAL = true;

    private static final String ENTITY_API_URL = "/api/ano-lectivos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnoLectivoRepository anoLectivoRepository;

    @Mock
    private AnoLectivoRepository anoLectivoRepositoryMock;

    @Autowired
    private AnoLectivoMapper anoLectivoMapper;

    @Mock
    private AnoLectivoService anoLectivoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnoLectivoMockMvc;

    private AnoLectivo anoLectivo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnoLectivo createEntity(EntityManager em) {
        AnoLectivo anoLectivo = new AnoLectivo()
            .ano(DEFAULT_ANO)
            .inicio(DEFAULT_INICIO)
            .fim(DEFAULT_FIM)
            .descricao(DEFAULT_DESCRICAO)
            .timestam(DEFAULT_TIMESTAM)
            .isActual(DEFAULT_IS_ACTUAL);
        return anoLectivo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnoLectivo createUpdatedEntity(EntityManager em) {
        AnoLectivo anoLectivo = new AnoLectivo()
            .ano(UPDATED_ANO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .descricao(UPDATED_DESCRICAO)
            .timestam(UPDATED_TIMESTAM)
            .isActual(UPDATED_IS_ACTUAL);
        return anoLectivo;
    }

    @BeforeEach
    public void initTest() {
        anoLectivo = createEntity(em);
    }

    @Test
    @Transactional
    void createAnoLectivo() throws Exception {
        int databaseSizeBeforeCreate = anoLectivoRepository.findAll().size();
        // Create the AnoLectivo
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);
        restAnoLectivoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO)))
            .andExpect(status().isCreated());

        // Validate the AnoLectivo in the database
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeCreate + 1);
        AnoLectivo testAnoLectivo = anoLectivoList.get(anoLectivoList.size() - 1);
        assertThat(testAnoLectivo.getAno()).isEqualTo(DEFAULT_ANO);
        assertThat(testAnoLectivo.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testAnoLectivo.getFim()).isEqualTo(DEFAULT_FIM);
        assertThat(testAnoLectivo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAnoLectivo.getTimestam()).isEqualTo(DEFAULT_TIMESTAM);
        assertThat(testAnoLectivo.getIsActual()).isEqualTo(DEFAULT_IS_ACTUAL);
    }

    @Test
    @Transactional
    void createAnoLectivoWithExistingId() throws Exception {
        // Create the AnoLectivo with an existing ID
        anoLectivo.setId(1L);
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);

        int databaseSizeBeforeCreate = anoLectivoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnoLectivoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnoLectivo in the database
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = anoLectivoRepository.findAll().size();
        // set the field null
        anoLectivo.setAno(null);

        // Create the AnoLectivo, which fails.
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);

        restAnoLectivoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO)))
            .andExpect(status().isBadRequest());

        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = anoLectivoRepository.findAll().size();
        // set the field null
        anoLectivo.setInicio(null);

        // Create the AnoLectivo, which fails.
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);

        restAnoLectivoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO)))
            .andExpect(status().isBadRequest());

        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = anoLectivoRepository.findAll().size();
        // set the field null
        anoLectivo.setFim(null);

        // Create the AnoLectivo, which fails.
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);

        restAnoLectivoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO)))
            .andExpect(status().isBadRequest());

        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = anoLectivoRepository.findAll().size();
        // set the field null
        anoLectivo.setDescricao(null);

        // Create the AnoLectivo, which fails.
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);

        restAnoLectivoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO)))
            .andExpect(status().isBadRequest());

        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnoLectivos() throws Exception {
        // Initialize the database
        anoLectivoRepository.saveAndFlush(anoLectivo);

        // Get all the anoLectivoList
        restAnoLectivoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anoLectivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(DEFAULT_FIM.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].timestam").value(hasItem(sameInstant(DEFAULT_TIMESTAM))))
            .andExpect(jsonPath("$.[*].isActual").value(hasItem(DEFAULT_IS_ACTUAL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnoLectivosWithEagerRelationshipsIsEnabled() throws Exception {
        when(anoLectivoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnoLectivoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anoLectivoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnoLectivosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anoLectivoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnoLectivoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anoLectivoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnoLectivo() throws Exception {
        // Initialize the database
        anoLectivoRepository.saveAndFlush(anoLectivo);

        // Get the anoLectivo
        restAnoLectivoMockMvc
            .perform(get(ENTITY_API_URL_ID, anoLectivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anoLectivo.getId().intValue()))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fim").value(DEFAULT_FIM.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.timestam").value(sameInstant(DEFAULT_TIMESTAM)))
            .andExpect(jsonPath("$.isActual").value(DEFAULT_IS_ACTUAL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAnoLectivo() throws Exception {
        // Get the anoLectivo
        restAnoLectivoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnoLectivo() throws Exception {
        // Initialize the database
        anoLectivoRepository.saveAndFlush(anoLectivo);

        int databaseSizeBeforeUpdate = anoLectivoRepository.findAll().size();

        // Update the anoLectivo
        AnoLectivo updatedAnoLectivo = anoLectivoRepository.findById(anoLectivo.getId()).get();
        // Disconnect from session so that the updates on updatedAnoLectivo are not directly saved in db
        em.detach(updatedAnoLectivo);
        updatedAnoLectivo
            .ano(UPDATED_ANO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .descricao(UPDATED_DESCRICAO)
            .timestam(UPDATED_TIMESTAM)
            .isActual(UPDATED_IS_ACTUAL);
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(updatedAnoLectivo);

        restAnoLectivoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anoLectivoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnoLectivo in the database
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeUpdate);
        AnoLectivo testAnoLectivo = anoLectivoList.get(anoLectivoList.size() - 1);
        assertThat(testAnoLectivo.getAno()).isEqualTo(UPDATED_ANO);
        assertThat(testAnoLectivo.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testAnoLectivo.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testAnoLectivo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAnoLectivo.getTimestam()).isEqualTo(UPDATED_TIMESTAM);
        assertThat(testAnoLectivo.getIsActual()).isEqualTo(UPDATED_IS_ACTUAL);
    }

    @Test
    @Transactional
    void putNonExistingAnoLectivo() throws Exception {
        int databaseSizeBeforeUpdate = anoLectivoRepository.findAll().size();
        anoLectivo.setId(count.incrementAndGet());

        // Create the AnoLectivo
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnoLectivoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anoLectivoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnoLectivo in the database
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnoLectivo() throws Exception {
        int databaseSizeBeforeUpdate = anoLectivoRepository.findAll().size();
        anoLectivo.setId(count.incrementAndGet());

        // Create the AnoLectivo
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnoLectivoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnoLectivo in the database
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnoLectivo() throws Exception {
        int databaseSizeBeforeUpdate = anoLectivoRepository.findAll().size();
        anoLectivo.setId(count.incrementAndGet());

        // Create the AnoLectivo
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnoLectivoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnoLectivo in the database
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnoLectivoWithPatch() throws Exception {
        // Initialize the database
        anoLectivoRepository.saveAndFlush(anoLectivo);

        int databaseSizeBeforeUpdate = anoLectivoRepository.findAll().size();

        // Update the anoLectivo using partial update
        AnoLectivo partialUpdatedAnoLectivo = new AnoLectivo();
        partialUpdatedAnoLectivo.setId(anoLectivo.getId());

        partialUpdatedAnoLectivo.ano(UPDATED_ANO).timestam(UPDATED_TIMESTAM);

        restAnoLectivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnoLectivo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnoLectivo))
            )
            .andExpect(status().isOk());

        // Validate the AnoLectivo in the database
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeUpdate);
        AnoLectivo testAnoLectivo = anoLectivoList.get(anoLectivoList.size() - 1);
        assertThat(testAnoLectivo.getAno()).isEqualTo(UPDATED_ANO);
        assertThat(testAnoLectivo.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testAnoLectivo.getFim()).isEqualTo(DEFAULT_FIM);
        assertThat(testAnoLectivo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAnoLectivo.getTimestam()).isEqualTo(UPDATED_TIMESTAM);
        assertThat(testAnoLectivo.getIsActual()).isEqualTo(DEFAULT_IS_ACTUAL);
    }

    @Test
    @Transactional
    void fullUpdateAnoLectivoWithPatch() throws Exception {
        // Initialize the database
        anoLectivoRepository.saveAndFlush(anoLectivo);

        int databaseSizeBeforeUpdate = anoLectivoRepository.findAll().size();

        // Update the anoLectivo using partial update
        AnoLectivo partialUpdatedAnoLectivo = new AnoLectivo();
        partialUpdatedAnoLectivo.setId(anoLectivo.getId());

        partialUpdatedAnoLectivo
            .ano(UPDATED_ANO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .descricao(UPDATED_DESCRICAO)
            .timestam(UPDATED_TIMESTAM)
            .isActual(UPDATED_IS_ACTUAL);

        restAnoLectivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnoLectivo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnoLectivo))
            )
            .andExpect(status().isOk());

        // Validate the AnoLectivo in the database
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeUpdate);
        AnoLectivo testAnoLectivo = anoLectivoList.get(anoLectivoList.size() - 1);
        assertThat(testAnoLectivo.getAno()).isEqualTo(UPDATED_ANO);
        assertThat(testAnoLectivo.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testAnoLectivo.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testAnoLectivo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAnoLectivo.getTimestam()).isEqualTo(UPDATED_TIMESTAM);
        assertThat(testAnoLectivo.getIsActual()).isEqualTo(UPDATED_IS_ACTUAL);
    }

    @Test
    @Transactional
    void patchNonExistingAnoLectivo() throws Exception {
        int databaseSizeBeforeUpdate = anoLectivoRepository.findAll().size();
        anoLectivo.setId(count.incrementAndGet());

        // Create the AnoLectivo
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnoLectivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anoLectivoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnoLectivo in the database
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnoLectivo() throws Exception {
        int databaseSizeBeforeUpdate = anoLectivoRepository.findAll().size();
        anoLectivo.setId(count.incrementAndGet());

        // Create the AnoLectivo
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnoLectivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnoLectivo in the database
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnoLectivo() throws Exception {
        int databaseSizeBeforeUpdate = anoLectivoRepository.findAll().size();
        anoLectivo.setId(count.incrementAndGet());

        // Create the AnoLectivo
        AnoLectivoDTO anoLectivoDTO = anoLectivoMapper.toDto(anoLectivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnoLectivoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(anoLectivoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnoLectivo in the database
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnoLectivo() throws Exception {
        // Initialize the database
        anoLectivoRepository.saveAndFlush(anoLectivo);

        int databaseSizeBeforeDelete = anoLectivoRepository.findAll().size();

        // Delete the anoLectivo
        restAnoLectivoMockMvc
            .perform(delete(ENTITY_API_URL_ID, anoLectivo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnoLectivo> anoLectivoList = anoLectivoRepository.findAll();
        assertThat(anoLectivoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
