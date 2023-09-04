package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnexoDiscente;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.enumeration.CategoriaAnexo;
import com.ravunana.longonkelo.repository.AnexoDiscenteRepository;
import com.ravunana.longonkelo.service.AnexoDiscenteService;
import com.ravunana.longonkelo.service.dto.AnexoDiscenteDTO;
import com.ravunana.longonkelo.service.mapper.AnexoDiscenteMapper;
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
 * Integration tests for the {@link AnexoDiscenteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnexoDiscenteResourceIT {

    private static final CategoriaAnexo DEFAULT_CATEGORIA = CategoriaAnexo.ACADEMICO;
    private static final CategoriaAnexo UPDATED_CATEGORIA = CategoriaAnexo.IDENTIFICACAO;

    private static final byte[] DEFAULT_ANEXO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANEXO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ANEXO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANEXO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALIDADE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDADE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/anexo-discentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnexoDiscenteRepository anexoDiscenteRepository;

    @Mock
    private AnexoDiscenteRepository anexoDiscenteRepositoryMock;

    @Autowired
    private AnexoDiscenteMapper anexoDiscenteMapper;

    @Mock
    private AnexoDiscenteService anexoDiscenteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoDiscenteMockMvc;

    private AnexoDiscente anexoDiscente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoDiscente createEntity(EntityManager em) {
        AnexoDiscente anexoDiscente = new AnexoDiscente()
            .categoria(DEFAULT_CATEGORIA)
            .anexo(DEFAULT_ANEXO)
            .anexoContentType(DEFAULT_ANEXO_CONTENT_TYPE)
            .descricao(DEFAULT_DESCRICAO)
            .validade(DEFAULT_VALIDADE)
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
        anexoDiscente.setDiscente(discente);
        return anexoDiscente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoDiscente createUpdatedEntity(EntityManager em) {
        AnexoDiscente anexoDiscente = new AnexoDiscente()
            .categoria(UPDATED_CATEGORIA)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .descricao(UPDATED_DESCRICAO)
            .validade(UPDATED_VALIDADE)
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
        anexoDiscente.setDiscente(discente);
        return anexoDiscente;
    }

    @BeforeEach
    public void initTest() {
        anexoDiscente = createEntity(em);
    }

    @Test
    @Transactional
    void createAnexoDiscente() throws Exception {
        int databaseSizeBeforeCreate = anexoDiscenteRepository.findAll().size();
        // Create the AnexoDiscente
        AnexoDiscenteDTO anexoDiscenteDTO = anexoDiscenteMapper.toDto(anexoDiscente);
        restAnexoDiscenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anexoDiscenteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AnexoDiscente in the database
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeCreate + 1);
        AnexoDiscente testAnexoDiscente = anexoDiscenteList.get(anexoDiscenteList.size() - 1);
        assertThat(testAnexoDiscente.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testAnexoDiscente.getAnexo()).isEqualTo(DEFAULT_ANEXO);
        assertThat(testAnexoDiscente.getAnexoContentType()).isEqualTo(DEFAULT_ANEXO_CONTENT_TYPE);
        assertThat(testAnexoDiscente.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAnexoDiscente.getValidade()).isEqualTo(DEFAULT_VALIDADE);
        assertThat(testAnexoDiscente.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createAnexoDiscenteWithExistingId() throws Exception {
        // Create the AnexoDiscente with an existing ID
        anexoDiscente.setId(1L);
        AnexoDiscenteDTO anexoDiscenteDTO = anexoDiscenteMapper.toDto(anexoDiscente);

        int databaseSizeBeforeCreate = anexoDiscenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoDiscenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anexoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoDiscente in the database
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = anexoDiscenteRepository.findAll().size();
        // set the field null
        anexoDiscente.setCategoria(null);

        // Create the AnexoDiscente, which fails.
        AnexoDiscenteDTO anexoDiscenteDTO = anexoDiscenteMapper.toDto(anexoDiscente);

        restAnexoDiscenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anexoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnexoDiscentes() throws Exception {
        // Initialize the database
        anexoDiscenteRepository.saveAndFlush(anexoDiscente);

        // Get all the anexoDiscenteList
        restAnexoDiscenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoDiscente.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoDiscentesWithEagerRelationshipsIsEnabled() throws Exception {
        when(anexoDiscenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoDiscenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anexoDiscenteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoDiscentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anexoDiscenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoDiscenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anexoDiscenteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnexoDiscente() throws Exception {
        // Initialize the database
        anexoDiscenteRepository.saveAndFlush(anexoDiscente);

        // Get the anexoDiscente
        restAnexoDiscenteMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoDiscente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoDiscente.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
            .andExpect(jsonPath("$.anexoContentType").value(DEFAULT_ANEXO_CONTENT_TYPE))
            .andExpect(jsonPath("$.anexo").value(Base64Utils.encodeToString(DEFAULT_ANEXO)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.validade").value(DEFAULT_VALIDADE.toString()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getNonExistingAnexoDiscente() throws Exception {
        // Get the anexoDiscente
        restAnexoDiscenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoDiscente() throws Exception {
        // Initialize the database
        anexoDiscenteRepository.saveAndFlush(anexoDiscente);

        int databaseSizeBeforeUpdate = anexoDiscenteRepository.findAll().size();

        // Update the anexoDiscente
        AnexoDiscente updatedAnexoDiscente = anexoDiscenteRepository.findById(anexoDiscente.getId()).get();
        // Disconnect from session so that the updates on updatedAnexoDiscente are not directly saved in db
        em.detach(updatedAnexoDiscente);
        updatedAnexoDiscente
            .categoria(UPDATED_CATEGORIA)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .descricao(UPDATED_DESCRICAO)
            .validade(UPDATED_VALIDADE)
            .timestamp(UPDATED_TIMESTAMP);
        AnexoDiscenteDTO anexoDiscenteDTO = anexoDiscenteMapper.toDto(updatedAnexoDiscente);

        restAnexoDiscenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoDiscenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anexoDiscenteDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnexoDiscente in the database
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeUpdate);
        AnexoDiscente testAnexoDiscente = anexoDiscenteList.get(anexoDiscenteList.size() - 1);
        assertThat(testAnexoDiscente.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testAnexoDiscente.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testAnexoDiscente.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
        assertThat(testAnexoDiscente.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAnexoDiscente.getValidade()).isEqualTo(UPDATED_VALIDADE);
        assertThat(testAnexoDiscente.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingAnexoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = anexoDiscenteRepository.findAll().size();
        anexoDiscente.setId(count.incrementAndGet());

        // Create the AnexoDiscente
        AnexoDiscenteDTO anexoDiscenteDTO = anexoDiscenteMapper.toDto(anexoDiscente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoDiscenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoDiscenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anexoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoDiscente in the database
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = anexoDiscenteRepository.findAll().size();
        anexoDiscente.setId(count.incrementAndGet());

        // Create the AnexoDiscente
        AnexoDiscenteDTO anexoDiscenteDTO = anexoDiscenteMapper.toDto(anexoDiscente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoDiscenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anexoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoDiscente in the database
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = anexoDiscenteRepository.findAll().size();
        anexoDiscente.setId(count.incrementAndGet());

        // Create the AnexoDiscente
        AnexoDiscenteDTO anexoDiscenteDTO = anexoDiscenteMapper.toDto(anexoDiscente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoDiscenteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anexoDiscenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoDiscente in the database
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoDiscenteWithPatch() throws Exception {
        // Initialize the database
        anexoDiscenteRepository.saveAndFlush(anexoDiscente);

        int databaseSizeBeforeUpdate = anexoDiscenteRepository.findAll().size();

        // Update the anexoDiscente using partial update
        AnexoDiscente partialUpdatedAnexoDiscente = new AnexoDiscente();
        partialUpdatedAnexoDiscente.setId(anexoDiscente.getId());

        partialUpdatedAnexoDiscente
            .categoria(UPDATED_CATEGORIA)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .validade(UPDATED_VALIDADE)
            .timestamp(UPDATED_TIMESTAMP);

        restAnexoDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoDiscente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnexoDiscente))
            )
            .andExpect(status().isOk());

        // Validate the AnexoDiscente in the database
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeUpdate);
        AnexoDiscente testAnexoDiscente = anexoDiscenteList.get(anexoDiscenteList.size() - 1);
        assertThat(testAnexoDiscente.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testAnexoDiscente.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testAnexoDiscente.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
        assertThat(testAnexoDiscente.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAnexoDiscente.getValidade()).isEqualTo(UPDATED_VALIDADE);
        assertThat(testAnexoDiscente.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateAnexoDiscenteWithPatch() throws Exception {
        // Initialize the database
        anexoDiscenteRepository.saveAndFlush(anexoDiscente);

        int databaseSizeBeforeUpdate = anexoDiscenteRepository.findAll().size();

        // Update the anexoDiscente using partial update
        AnexoDiscente partialUpdatedAnexoDiscente = new AnexoDiscente();
        partialUpdatedAnexoDiscente.setId(anexoDiscente.getId());

        partialUpdatedAnexoDiscente
            .categoria(UPDATED_CATEGORIA)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .descricao(UPDATED_DESCRICAO)
            .validade(UPDATED_VALIDADE)
            .timestamp(UPDATED_TIMESTAMP);

        restAnexoDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoDiscente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnexoDiscente))
            )
            .andExpect(status().isOk());

        // Validate the AnexoDiscente in the database
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeUpdate);
        AnexoDiscente testAnexoDiscente = anexoDiscenteList.get(anexoDiscenteList.size() - 1);
        assertThat(testAnexoDiscente.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testAnexoDiscente.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testAnexoDiscente.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
        assertThat(testAnexoDiscente.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAnexoDiscente.getValidade()).isEqualTo(UPDATED_VALIDADE);
        assertThat(testAnexoDiscente.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingAnexoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = anexoDiscenteRepository.findAll().size();
        anexoDiscente.setId(count.incrementAndGet());

        // Create the AnexoDiscente
        AnexoDiscenteDTO anexoDiscenteDTO = anexoDiscenteMapper.toDto(anexoDiscente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoDiscenteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anexoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoDiscente in the database
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = anexoDiscenteRepository.findAll().size();
        anexoDiscente.setId(count.incrementAndGet());

        // Create the AnexoDiscente
        AnexoDiscenteDTO anexoDiscenteDTO = anexoDiscenteMapper.toDto(anexoDiscente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anexoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoDiscente in the database
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = anexoDiscenteRepository.findAll().size();
        anexoDiscente.setId(count.incrementAndGet());

        // Create the AnexoDiscente
        AnexoDiscenteDTO anexoDiscenteDTO = anexoDiscenteMapper.toDto(anexoDiscente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anexoDiscenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoDiscente in the database
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoDiscente() throws Exception {
        // Initialize the database
        anexoDiscenteRepository.saveAndFlush(anexoDiscente);

        int databaseSizeBeforeDelete = anexoDiscenteRepository.findAll().size();

        // Delete the anexoDiscente
        restAnexoDiscenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoDiscente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnexoDiscente> anexoDiscenteList = anexoDiscenteRepository.findAll();
        assertThat(anexoDiscenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
