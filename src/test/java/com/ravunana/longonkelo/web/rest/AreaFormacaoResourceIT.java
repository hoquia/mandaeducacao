package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AreaFormacao;
import com.ravunana.longonkelo.domain.NivelEnsino;
import com.ravunana.longonkelo.repository.AreaFormacaoRepository;
import com.ravunana.longonkelo.service.AreaFormacaoService;
import com.ravunana.longonkelo.service.dto.AreaFormacaoDTO;
import com.ravunana.longonkelo.service.mapper.AreaFormacaoMapper;
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
 * Integration tests for the {@link AreaFormacaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AreaFormacaoResourceIT {

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/area-formacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AreaFormacaoRepository areaFormacaoRepository;

    @Mock
    private AreaFormacaoRepository areaFormacaoRepositoryMock;

    @Autowired
    private AreaFormacaoMapper areaFormacaoMapper;

    @Mock
    private AreaFormacaoService areaFormacaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaFormacaoMockMvc;

    private AreaFormacao areaFormacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaFormacao createEntity(EntityManager em) {
        AreaFormacao areaFormacao = new AreaFormacao()
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        NivelEnsino nivelEnsino;
        if (TestUtil.findAll(em, NivelEnsino.class).isEmpty()) {
            nivelEnsino = NivelEnsinoResourceIT.createEntity(em);
            em.persist(nivelEnsino);
            em.flush();
        } else {
            nivelEnsino = TestUtil.findAll(em, NivelEnsino.class).get(0);
        }
        areaFormacao.setNivelEnsino(nivelEnsino);
        return areaFormacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaFormacao createUpdatedEntity(EntityManager em) {
        AreaFormacao areaFormacao = new AreaFormacao()
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        NivelEnsino nivelEnsino;
        if (TestUtil.findAll(em, NivelEnsino.class).isEmpty()) {
            nivelEnsino = NivelEnsinoResourceIT.createUpdatedEntity(em);
            em.persist(nivelEnsino);
            em.flush();
        } else {
            nivelEnsino = TestUtil.findAll(em, NivelEnsino.class).get(0);
        }
        areaFormacao.setNivelEnsino(nivelEnsino);
        return areaFormacao;
    }

    @BeforeEach
    public void initTest() {
        areaFormacao = createEntity(em);
    }

    @Test
    @Transactional
    void createAreaFormacao() throws Exception {
        int databaseSizeBeforeCreate = areaFormacaoRepository.findAll().size();
        // Create the AreaFormacao
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);
        restAreaFormacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeCreate + 1);
        AreaFormacao testAreaFormacao = areaFormacaoList.get(areaFormacaoList.size() - 1);
        assertThat(testAreaFormacao.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testAreaFormacao.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testAreaFormacao.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testAreaFormacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAreaFormacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createAreaFormacaoWithExistingId() throws Exception {
        // Create the AreaFormacao with an existing ID
        areaFormacao.setId(1L);
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        int databaseSizeBeforeCreate = areaFormacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaFormacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaFormacaoRepository.findAll().size();
        // set the field null
        areaFormacao.setCodigo(null);

        // Create the AreaFormacao, which fails.
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        restAreaFormacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaFormacaoRepository.findAll().size();
        // set the field null
        areaFormacao.setNome(null);

        // Create the AreaFormacao, which fails.
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        restAreaFormacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAreaFormacaos() throws Exception {
        // Initialize the database
        areaFormacaoRepository.saveAndFlush(areaFormacao);

        // Get all the areaFormacaoList
        restAreaFormacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaFormacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaFormacaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(areaFormacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaFormacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(areaFormacaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaFormacaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(areaFormacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaFormacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(areaFormacaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAreaFormacao() throws Exception {
        // Initialize the database
        areaFormacaoRepository.saveAndFlush(areaFormacao);

        // Get the areaFormacao
        restAreaFormacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, areaFormacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(areaFormacao.getId().intValue()))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAreaFormacao() throws Exception {
        // Get the areaFormacao
        restAreaFormacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAreaFormacao() throws Exception {
        // Initialize the database
        areaFormacaoRepository.saveAndFlush(areaFormacao);

        int databaseSizeBeforeUpdate = areaFormacaoRepository.findAll().size();

        // Update the areaFormacao
        AreaFormacao updatedAreaFormacao = areaFormacaoRepository.findById(areaFormacao.getId()).get();
        // Disconnect from session so that the updates on updatedAreaFormacao are not directly saved in db
        em.detach(updatedAreaFormacao);
        updatedAreaFormacao
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(updatedAreaFormacao);

        restAreaFormacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaFormacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeUpdate);
        AreaFormacao testAreaFormacao = areaFormacaoList.get(areaFormacaoList.size() - 1);
        assertThat(testAreaFormacao.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testAreaFormacao.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testAreaFormacao.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testAreaFormacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAreaFormacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = areaFormacaoRepository.findAll().size();
        areaFormacao.setId(count.incrementAndGet());

        // Create the AreaFormacao
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaFormacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaFormacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = areaFormacaoRepository.findAll().size();
        areaFormacao.setId(count.incrementAndGet());

        // Create the AreaFormacao
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaFormacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = areaFormacaoRepository.findAll().size();
        areaFormacao.setId(count.incrementAndGet());

        // Create the AreaFormacao
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaFormacaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAreaFormacaoWithPatch() throws Exception {
        // Initialize the database
        areaFormacaoRepository.saveAndFlush(areaFormacao);

        int databaseSizeBeforeUpdate = areaFormacaoRepository.findAll().size();

        // Update the areaFormacao using partial update
        AreaFormacao partialUpdatedAreaFormacao = new AreaFormacao();
        partialUpdatedAreaFormacao.setId(areaFormacao.getId());

        partialUpdatedAreaFormacao.imagem(UPDATED_IMAGEM).imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE);

        restAreaFormacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaFormacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAreaFormacao))
            )
            .andExpect(status().isOk());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeUpdate);
        AreaFormacao testAreaFormacao = areaFormacaoList.get(areaFormacaoList.size() - 1);
        assertThat(testAreaFormacao.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testAreaFormacao.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testAreaFormacao.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testAreaFormacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAreaFormacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateAreaFormacaoWithPatch() throws Exception {
        // Initialize the database
        areaFormacaoRepository.saveAndFlush(areaFormacao);

        int databaseSizeBeforeUpdate = areaFormacaoRepository.findAll().size();

        // Update the areaFormacao using partial update
        AreaFormacao partialUpdatedAreaFormacao = new AreaFormacao();
        partialUpdatedAreaFormacao.setId(areaFormacao.getId());

        partialUpdatedAreaFormacao
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);

        restAreaFormacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaFormacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAreaFormacao))
            )
            .andExpect(status().isOk());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeUpdate);
        AreaFormacao testAreaFormacao = areaFormacaoList.get(areaFormacaoList.size() - 1);
        assertThat(testAreaFormacao.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testAreaFormacao.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testAreaFormacao.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testAreaFormacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAreaFormacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = areaFormacaoRepository.findAll().size();
        areaFormacao.setId(count.incrementAndGet());

        // Create the AreaFormacao
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaFormacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, areaFormacaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = areaFormacaoRepository.findAll().size();
        areaFormacao.setId(count.incrementAndGet());

        // Create the AreaFormacao
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaFormacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = areaFormacaoRepository.findAll().size();
        areaFormacao.setId(count.incrementAndGet());

        // Create the AreaFormacao
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaFormacaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAreaFormacao() throws Exception {
        // Initialize the database
        areaFormacaoRepository.saveAndFlush(areaFormacao);

        int databaseSizeBeforeDelete = areaFormacaoRepository.findAll().size();

        // Delete the areaFormacao
        restAreaFormacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, areaFormacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
