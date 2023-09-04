package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.InstituicaoEnsino;
import com.ravunana.longonkelo.domain.ProvedorNotificacao;
import com.ravunana.longonkelo.repository.ProvedorNotificacaoRepository;
import com.ravunana.longonkelo.service.ProvedorNotificacaoService;
import com.ravunana.longonkelo.service.dto.ProvedorNotificacaoDTO;
import com.ravunana.longonkelo.service.mapper.ProvedorNotificacaoMapper;
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
 * Integration tests for the {@link ProvedorNotificacaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProvedorNotificacaoResourceIT {

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PADRAO = false;
    private static final Boolean UPDATED_IS_PADRAO = true;

    private static final String ENTITY_API_URL = "/api/provedor-notificacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProvedorNotificacaoRepository provedorNotificacaoRepository;

    @Mock
    private ProvedorNotificacaoRepository provedorNotificacaoRepositoryMock;

    @Autowired
    private ProvedorNotificacaoMapper provedorNotificacaoMapper;

    @Mock
    private ProvedorNotificacaoService provedorNotificacaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProvedorNotificacaoMockMvc;

    private ProvedorNotificacao provedorNotificacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProvedorNotificacao createEntity(EntityManager em) {
        ProvedorNotificacao provedorNotificacao = new ProvedorNotificacao()
            .telefone(DEFAULT_TELEFONE)
            .email(DEFAULT_EMAIL)
            .link(DEFAULT_LINK)
            .token(DEFAULT_TOKEN)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .hash(DEFAULT_HASH)
            .isPadrao(DEFAULT_IS_PADRAO);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        provedorNotificacao.setInstituicao(instituicaoEnsino);
        return provedorNotificacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProvedorNotificacao createUpdatedEntity(EntityManager em) {
        ProvedorNotificacao provedorNotificacao = new ProvedorNotificacao()
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .link(UPDATED_LINK)
            .token(UPDATED_TOKEN)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .hash(UPDATED_HASH)
            .isPadrao(UPDATED_IS_PADRAO);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createUpdatedEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        provedorNotificacao.setInstituicao(instituicaoEnsino);
        return provedorNotificacao;
    }

    @BeforeEach
    public void initTest() {
        provedorNotificacao = createEntity(em);
    }

    @Test
    @Transactional
    void createProvedorNotificacao() throws Exception {
        int databaseSizeBeforeCreate = provedorNotificacaoRepository.findAll().size();
        // Create the ProvedorNotificacao
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);
        restProvedorNotificacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProvedorNotificacao in the database
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeCreate + 1);
        ProvedorNotificacao testProvedorNotificacao = provedorNotificacaoList.get(provedorNotificacaoList.size() - 1);
        assertThat(testProvedorNotificacao.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testProvedorNotificacao.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProvedorNotificacao.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testProvedorNotificacao.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testProvedorNotificacao.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testProvedorNotificacao.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testProvedorNotificacao.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testProvedorNotificacao.getIsPadrao()).isEqualTo(DEFAULT_IS_PADRAO);
    }

    @Test
    @Transactional
    void createProvedorNotificacaoWithExistingId() throws Exception {
        // Create the ProvedorNotificacao with an existing ID
        provedorNotificacao.setId(1L);
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        int databaseSizeBeforeCreate = provedorNotificacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvedorNotificacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProvedorNotificacao in the database
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = provedorNotificacaoRepository.findAll().size();
        // set the field null
        provedorNotificacao.setTelefone(null);

        // Create the ProvedorNotificacao, which fails.
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        restProvedorNotificacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = provedorNotificacaoRepository.findAll().size();
        // set the field null
        provedorNotificacao.setEmail(null);

        // Create the ProvedorNotificacao, which fails.
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        restProvedorNotificacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = provedorNotificacaoRepository.findAll().size();
        // set the field null
        provedorNotificacao.setLink(null);

        // Create the ProvedorNotificacao, which fails.
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        restProvedorNotificacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = provedorNotificacaoRepository.findAll().size();
        // set the field null
        provedorNotificacao.setToken(null);

        // Create the ProvedorNotificacao, which fails.
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        restProvedorNotificacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = provedorNotificacaoRepository.findAll().size();
        // set the field null
        provedorNotificacao.setUsername(null);

        // Create the ProvedorNotificacao, which fails.
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        restProvedorNotificacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = provedorNotificacaoRepository.findAll().size();
        // set the field null
        provedorNotificacao.setPassword(null);

        // Create the ProvedorNotificacao, which fails.
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        restProvedorNotificacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = provedorNotificacaoRepository.findAll().size();
        // set the field null
        provedorNotificacao.setHash(null);

        // Create the ProvedorNotificacao, which fails.
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        restProvedorNotificacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProvedorNotificacaos() throws Exception {
        // Initialize the database
        provedorNotificacaoRepository.saveAndFlush(provedorNotificacao);

        // Get all the provedorNotificacaoList
        restProvedorNotificacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(provedorNotificacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].isPadrao").value(hasItem(DEFAULT_IS_PADRAO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProvedorNotificacaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(provedorNotificacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProvedorNotificacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(provedorNotificacaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProvedorNotificacaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(provedorNotificacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProvedorNotificacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(provedorNotificacaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProvedorNotificacao() throws Exception {
        // Initialize the database
        provedorNotificacaoRepository.saveAndFlush(provedorNotificacao);

        // Get the provedorNotificacao
        restProvedorNotificacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, provedorNotificacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(provedorNotificacao.getId().intValue()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.isPadrao").value(DEFAULT_IS_PADRAO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingProvedorNotificacao() throws Exception {
        // Get the provedorNotificacao
        restProvedorNotificacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProvedorNotificacao() throws Exception {
        // Initialize the database
        provedorNotificacaoRepository.saveAndFlush(provedorNotificacao);

        int databaseSizeBeforeUpdate = provedorNotificacaoRepository.findAll().size();

        // Update the provedorNotificacao
        ProvedorNotificacao updatedProvedorNotificacao = provedorNotificacaoRepository.findById(provedorNotificacao.getId()).get();
        // Disconnect from session so that the updates on updatedProvedorNotificacao are not directly saved in db
        em.detach(updatedProvedorNotificacao);
        updatedProvedorNotificacao
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .link(UPDATED_LINK)
            .token(UPDATED_TOKEN)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .hash(UPDATED_HASH)
            .isPadrao(UPDATED_IS_PADRAO);
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(updatedProvedorNotificacao);

        restProvedorNotificacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, provedorNotificacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProvedorNotificacao in the database
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeUpdate);
        ProvedorNotificacao testProvedorNotificacao = provedorNotificacaoList.get(provedorNotificacaoList.size() - 1);
        assertThat(testProvedorNotificacao.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testProvedorNotificacao.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProvedorNotificacao.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testProvedorNotificacao.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testProvedorNotificacao.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testProvedorNotificacao.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testProvedorNotificacao.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testProvedorNotificacao.getIsPadrao()).isEqualTo(UPDATED_IS_PADRAO);
    }

    @Test
    @Transactional
    void putNonExistingProvedorNotificacao() throws Exception {
        int databaseSizeBeforeUpdate = provedorNotificacaoRepository.findAll().size();
        provedorNotificacao.setId(count.incrementAndGet());

        // Create the ProvedorNotificacao
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvedorNotificacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, provedorNotificacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProvedorNotificacao in the database
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProvedorNotificacao() throws Exception {
        int databaseSizeBeforeUpdate = provedorNotificacaoRepository.findAll().size();
        provedorNotificacao.setId(count.incrementAndGet());

        // Create the ProvedorNotificacao
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvedorNotificacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProvedorNotificacao in the database
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProvedorNotificacao() throws Exception {
        int databaseSizeBeforeUpdate = provedorNotificacaoRepository.findAll().size();
        provedorNotificacao.setId(count.incrementAndGet());

        // Create the ProvedorNotificacao
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvedorNotificacaoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProvedorNotificacao in the database
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProvedorNotificacaoWithPatch() throws Exception {
        // Initialize the database
        provedorNotificacaoRepository.saveAndFlush(provedorNotificacao);

        int databaseSizeBeforeUpdate = provedorNotificacaoRepository.findAll().size();

        // Update the provedorNotificacao using partial update
        ProvedorNotificacao partialUpdatedProvedorNotificacao = new ProvedorNotificacao();
        partialUpdatedProvedorNotificacao.setId(provedorNotificacao.getId());

        partialUpdatedProvedorNotificacao.telefone(UPDATED_TELEFONE).link(UPDATED_LINK).token(UPDATED_TOKEN).username(UPDATED_USERNAME);

        restProvedorNotificacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvedorNotificacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProvedorNotificacao))
            )
            .andExpect(status().isOk());

        // Validate the ProvedorNotificacao in the database
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeUpdate);
        ProvedorNotificacao testProvedorNotificacao = provedorNotificacaoList.get(provedorNotificacaoList.size() - 1);
        assertThat(testProvedorNotificacao.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testProvedorNotificacao.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProvedorNotificacao.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testProvedorNotificacao.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testProvedorNotificacao.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testProvedorNotificacao.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testProvedorNotificacao.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testProvedorNotificacao.getIsPadrao()).isEqualTo(DEFAULT_IS_PADRAO);
    }

    @Test
    @Transactional
    void fullUpdateProvedorNotificacaoWithPatch() throws Exception {
        // Initialize the database
        provedorNotificacaoRepository.saveAndFlush(provedorNotificacao);

        int databaseSizeBeforeUpdate = provedorNotificacaoRepository.findAll().size();

        // Update the provedorNotificacao using partial update
        ProvedorNotificacao partialUpdatedProvedorNotificacao = new ProvedorNotificacao();
        partialUpdatedProvedorNotificacao.setId(provedorNotificacao.getId());

        partialUpdatedProvedorNotificacao
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL)
            .link(UPDATED_LINK)
            .token(UPDATED_TOKEN)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .hash(UPDATED_HASH)
            .isPadrao(UPDATED_IS_PADRAO);

        restProvedorNotificacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvedorNotificacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProvedorNotificacao))
            )
            .andExpect(status().isOk());

        // Validate the ProvedorNotificacao in the database
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeUpdate);
        ProvedorNotificacao testProvedorNotificacao = provedorNotificacaoList.get(provedorNotificacaoList.size() - 1);
        assertThat(testProvedorNotificacao.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testProvedorNotificacao.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProvedorNotificacao.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testProvedorNotificacao.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testProvedorNotificacao.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testProvedorNotificacao.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testProvedorNotificacao.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testProvedorNotificacao.getIsPadrao()).isEqualTo(UPDATED_IS_PADRAO);
    }

    @Test
    @Transactional
    void patchNonExistingProvedorNotificacao() throws Exception {
        int databaseSizeBeforeUpdate = provedorNotificacaoRepository.findAll().size();
        provedorNotificacao.setId(count.incrementAndGet());

        // Create the ProvedorNotificacao
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvedorNotificacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, provedorNotificacaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProvedorNotificacao in the database
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProvedorNotificacao() throws Exception {
        int databaseSizeBeforeUpdate = provedorNotificacaoRepository.findAll().size();
        provedorNotificacao.setId(count.incrementAndGet());

        // Create the ProvedorNotificacao
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvedorNotificacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProvedorNotificacao in the database
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProvedorNotificacao() throws Exception {
        int databaseSizeBeforeUpdate = provedorNotificacaoRepository.findAll().size();
        provedorNotificacao.setId(count.incrementAndGet());

        // Create the ProvedorNotificacao
        ProvedorNotificacaoDTO provedorNotificacaoDTO = provedorNotificacaoMapper.toDto(provedorNotificacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvedorNotificacaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(provedorNotificacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProvedorNotificacao in the database
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProvedorNotificacao() throws Exception {
        // Initialize the database
        provedorNotificacaoRepository.saveAndFlush(provedorNotificacao);

        int databaseSizeBeforeDelete = provedorNotificacaoRepository.findAll().size();

        // Delete the provedorNotificacao
        restProvedorNotificacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, provedorNotificacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProvedorNotificacao> provedorNotificacaoList = provedorNotificacaoRepository.findAll();
        assertThat(provedorNotificacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
