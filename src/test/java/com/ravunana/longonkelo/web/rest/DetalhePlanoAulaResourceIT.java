package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.DetalhePlanoAula;
import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.repository.DetalhePlanoAulaRepository;
import com.ravunana.longonkelo.service.DetalhePlanoAulaService;
import com.ravunana.longonkelo.service.criteria.DetalhePlanoAulaCriteria;
import com.ravunana.longonkelo.service.dto.DetalhePlanoAulaDTO;
import com.ravunana.longonkelo.service.mapper.DetalhePlanoAulaMapper;
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
 * Integration tests for the {@link DetalhePlanoAulaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DetalhePlanoAulaResourceIT {

    private static final String DEFAULT_ESTRATEGIA_AULA = "AAAAAAAAAA";
    private static final String UPDATED_ESTRATEGIA_AULA = "BBBBBBBBBB";

    private static final Double DEFAULT_TEMPO_ACTIVIDADE = 0D;
    private static final Double UPDATED_TEMPO_ACTIVIDADE = 1D;
    private static final Double SMALLER_TEMPO_ACTIVIDADE = 0D - 1D;

    private static final String DEFAULT_RECURSOS_ENSINO = "AAAAAAAAAA";
    private static final String UPDATED_RECURSOS_ENSINO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_ACTIVIDADE = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_ACTIVIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVIDADES_DOCENTE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVIDADES_DOCENTE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVIDADES_DISCENTES = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVIDADES_DISCENTES = "BBBBBBBBBB";

    private static final String DEFAULT_AVALIACAO = "AAAAAAAAAA";
    private static final String UPDATED_AVALIACAO = "BBBBBBBBBB";

    private static final String DEFAULT_BIBLIOGRAFIA = "AAAAAAAAAA";
    private static final String UPDATED_BIBLIOGRAFIA = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PDF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PDF = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PDF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PDF_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_VIDEO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VIDEO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_AUDIO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AUDIO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AUDIO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AUDIO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/detalhe-plano-aulas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetalhePlanoAulaRepository detalhePlanoAulaRepository;

    @Mock
    private DetalhePlanoAulaRepository detalhePlanoAulaRepositoryMock;

    @Autowired
    private DetalhePlanoAulaMapper detalhePlanoAulaMapper;

    @Mock
    private DetalhePlanoAulaService detalhePlanoAulaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetalhePlanoAulaMockMvc;

    private DetalhePlanoAula detalhePlanoAula;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalhePlanoAula createEntity(EntityManager em) {
        DetalhePlanoAula detalhePlanoAula = new DetalhePlanoAula()
            .estrategiaAula(DEFAULT_ESTRATEGIA_AULA)
            .tempoActividade(DEFAULT_TEMPO_ACTIVIDADE)
            .recursosEnsino(DEFAULT_RECURSOS_ENSINO)
            .tituloActividade(DEFAULT_TITULO_ACTIVIDADE)
            .actividadesDocente(DEFAULT_ACTIVIDADES_DOCENTE)
            .actividadesDiscentes(DEFAULT_ACTIVIDADES_DISCENTES)
            .avaliacao(DEFAULT_AVALIACAO)
            .bibliografia(DEFAULT_BIBLIOGRAFIA)
            .observacao(DEFAULT_OBSERVACAO)
            .pdf(DEFAULT_PDF)
            .pdfContentType(DEFAULT_PDF_CONTENT_TYPE)
            .video(DEFAULT_VIDEO)
            .videoContentType(DEFAULT_VIDEO_CONTENT_TYPE)
            .audio(DEFAULT_AUDIO)
            .audioContentType(DEFAULT_AUDIO_CONTENT_TYPE);
        // Add required entity
        PlanoAula planoAula;
        if (TestUtil.findAll(em, PlanoAula.class).isEmpty()) {
            planoAula = PlanoAulaResourceIT.createEntity(em);
            em.persist(planoAula);
            em.flush();
        } else {
            planoAula = TestUtil.findAll(em, PlanoAula.class).get(0);
        }
        detalhePlanoAula.setPlanoAula(planoAula);
        return detalhePlanoAula;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalhePlanoAula createUpdatedEntity(EntityManager em) {
        DetalhePlanoAula detalhePlanoAula = new DetalhePlanoAula()
            .estrategiaAula(UPDATED_ESTRATEGIA_AULA)
            .tempoActividade(UPDATED_TEMPO_ACTIVIDADE)
            .recursosEnsino(UPDATED_RECURSOS_ENSINO)
            .tituloActividade(UPDATED_TITULO_ACTIVIDADE)
            .actividadesDocente(UPDATED_ACTIVIDADES_DOCENTE)
            .actividadesDiscentes(UPDATED_ACTIVIDADES_DISCENTES)
            .avaliacao(UPDATED_AVALIACAO)
            .bibliografia(UPDATED_BIBLIOGRAFIA)
            .observacao(UPDATED_OBSERVACAO)
            .pdf(UPDATED_PDF)
            .pdfContentType(UPDATED_PDF_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .audio(UPDATED_AUDIO)
            .audioContentType(UPDATED_AUDIO_CONTENT_TYPE);
        // Add required entity
        PlanoAula planoAula;
        if (TestUtil.findAll(em, PlanoAula.class).isEmpty()) {
            planoAula = PlanoAulaResourceIT.createUpdatedEntity(em);
            em.persist(planoAula);
            em.flush();
        } else {
            planoAula = TestUtil.findAll(em, PlanoAula.class).get(0);
        }
        detalhePlanoAula.setPlanoAula(planoAula);
        return detalhePlanoAula;
    }

    @BeforeEach
    public void initTest() {
        detalhePlanoAula = createEntity(em);
    }

    @Test
    @Transactional
    void createDetalhePlanoAula() throws Exception {
        int databaseSizeBeforeCreate = detalhePlanoAulaRepository.findAll().size();
        // Create the DetalhePlanoAula
        DetalhePlanoAulaDTO detalhePlanoAulaDTO = detalhePlanoAulaMapper.toDto(detalhePlanoAula);
        restDetalhePlanoAulaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalhePlanoAulaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DetalhePlanoAula in the database
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeCreate + 1);
        DetalhePlanoAula testDetalhePlanoAula = detalhePlanoAulaList.get(detalhePlanoAulaList.size() - 1);
        assertThat(testDetalhePlanoAula.getEstrategiaAula()).isEqualTo(DEFAULT_ESTRATEGIA_AULA);
        assertThat(testDetalhePlanoAula.getTempoActividade()).isEqualTo(DEFAULT_TEMPO_ACTIVIDADE);
        assertThat(testDetalhePlanoAula.getRecursosEnsino()).isEqualTo(DEFAULT_RECURSOS_ENSINO);
        assertThat(testDetalhePlanoAula.getTituloActividade()).isEqualTo(DEFAULT_TITULO_ACTIVIDADE);
        assertThat(testDetalhePlanoAula.getActividadesDocente()).isEqualTo(DEFAULT_ACTIVIDADES_DOCENTE);
        assertThat(testDetalhePlanoAula.getActividadesDiscentes()).isEqualTo(DEFAULT_ACTIVIDADES_DISCENTES);
        assertThat(testDetalhePlanoAula.getAvaliacao()).isEqualTo(DEFAULT_AVALIACAO);
        assertThat(testDetalhePlanoAula.getBibliografia()).isEqualTo(DEFAULT_BIBLIOGRAFIA);
        assertThat(testDetalhePlanoAula.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testDetalhePlanoAula.getPdf()).isEqualTo(DEFAULT_PDF);
        assertThat(testDetalhePlanoAula.getPdfContentType()).isEqualTo(DEFAULT_PDF_CONTENT_TYPE);
        assertThat(testDetalhePlanoAula.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testDetalhePlanoAula.getVideoContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_TYPE);
        assertThat(testDetalhePlanoAula.getAudio()).isEqualTo(DEFAULT_AUDIO);
        assertThat(testDetalhePlanoAula.getAudioContentType()).isEqualTo(DEFAULT_AUDIO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createDetalhePlanoAulaWithExistingId() throws Exception {
        // Create the DetalhePlanoAula with an existing ID
        detalhePlanoAula.setId(1L);
        DetalhePlanoAulaDTO detalhePlanoAulaDTO = detalhePlanoAulaMapper.toDto(detalhePlanoAula);

        int databaseSizeBeforeCreate = detalhePlanoAulaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalhePlanoAulaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalhePlanoAulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhePlanoAula in the database
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTempoActividadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalhePlanoAulaRepository.findAll().size();
        // set the field null
        detalhePlanoAula.setTempoActividade(null);

        // Create the DetalhePlanoAula, which fails.
        DetalhePlanoAulaDTO detalhePlanoAulaDTO = detalhePlanoAulaMapper.toDto(detalhePlanoAula);

        restDetalhePlanoAulaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalhePlanoAulaDTO))
            )
            .andExpect(status().isBadRequest());

        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTituloActividadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalhePlanoAulaRepository.findAll().size();
        // set the field null
        detalhePlanoAula.setTituloActividade(null);

        // Create the DetalhePlanoAula, which fails.
        DetalhePlanoAulaDTO detalhePlanoAulaDTO = detalhePlanoAulaMapper.toDto(detalhePlanoAula);

        restDetalhePlanoAulaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalhePlanoAulaDTO))
            )
            .andExpect(status().isBadRequest());

        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulas() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList
        restDetalhePlanoAulaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalhePlanoAula.getId().intValue())))
            .andExpect(jsonPath("$.[*].estrategiaAula").value(hasItem(DEFAULT_ESTRATEGIA_AULA.toString())))
            .andExpect(jsonPath("$.[*].tempoActividade").value(hasItem(DEFAULT_TEMPO_ACTIVIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].recursosEnsino").value(hasItem(DEFAULT_RECURSOS_ENSINO.toString())))
            .andExpect(jsonPath("$.[*].tituloActividade").value(hasItem(DEFAULT_TITULO_ACTIVIDADE)))
            .andExpect(jsonPath("$.[*].actividadesDocente").value(hasItem(DEFAULT_ACTIVIDADES_DOCENTE.toString())))
            .andExpect(jsonPath("$.[*].actividadesDiscentes").value(hasItem(DEFAULT_ACTIVIDADES_DISCENTES.toString())))
            .andExpect(jsonPath("$.[*].avaliacao").value(hasItem(DEFAULT_AVALIACAO.toString())))
            .andExpect(jsonPath("$.[*].bibliografia").value(hasItem(DEFAULT_BIBLIOGRAFIA.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].pdfContentType").value(hasItem(DEFAULT_PDF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pdf").value(hasItem(Base64Utils.encodeToString(DEFAULT_PDF))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].audioContentType").value(hasItem(DEFAULT_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDetalhePlanoAulasWithEagerRelationshipsIsEnabled() throws Exception {
        when(detalhePlanoAulaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDetalhePlanoAulaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(detalhePlanoAulaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDetalhePlanoAulasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(detalhePlanoAulaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDetalhePlanoAulaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(detalhePlanoAulaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDetalhePlanoAula() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get the detalhePlanoAula
        restDetalhePlanoAulaMockMvc
            .perform(get(ENTITY_API_URL_ID, detalhePlanoAula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detalhePlanoAula.getId().intValue()))
            .andExpect(jsonPath("$.estrategiaAula").value(DEFAULT_ESTRATEGIA_AULA.toString()))
            .andExpect(jsonPath("$.tempoActividade").value(DEFAULT_TEMPO_ACTIVIDADE.doubleValue()))
            .andExpect(jsonPath("$.recursosEnsino").value(DEFAULT_RECURSOS_ENSINO.toString()))
            .andExpect(jsonPath("$.tituloActividade").value(DEFAULT_TITULO_ACTIVIDADE))
            .andExpect(jsonPath("$.actividadesDocente").value(DEFAULT_ACTIVIDADES_DOCENTE.toString()))
            .andExpect(jsonPath("$.actividadesDiscentes").value(DEFAULT_ACTIVIDADES_DISCENTES.toString()))
            .andExpect(jsonPath("$.avaliacao").value(DEFAULT_AVALIACAO.toString()))
            .andExpect(jsonPath("$.bibliografia").value(DEFAULT_BIBLIOGRAFIA.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.pdfContentType").value(DEFAULT_PDF_CONTENT_TYPE))
            .andExpect(jsonPath("$.pdf").value(Base64Utils.encodeToString(DEFAULT_PDF)))
            .andExpect(jsonPath("$.videoContentType").value(DEFAULT_VIDEO_CONTENT_TYPE))
            .andExpect(jsonPath("$.video").value(Base64Utils.encodeToString(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.audioContentType").value(DEFAULT_AUDIO_CONTENT_TYPE))
            .andExpect(jsonPath("$.audio").value(Base64Utils.encodeToString(DEFAULT_AUDIO)));
    }

    @Test
    @Transactional
    void getDetalhePlanoAulasByIdFiltering() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        Long id = detalhePlanoAula.getId();

        defaultDetalhePlanoAulaShouldBeFound("id.equals=" + id);
        defaultDetalhePlanoAulaShouldNotBeFound("id.notEquals=" + id);

        defaultDetalhePlanoAulaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDetalhePlanoAulaShouldNotBeFound("id.greaterThan=" + id);

        defaultDetalhePlanoAulaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDetalhePlanoAulaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTempoActividadeIsEqualToSomething() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tempoActividade equals to DEFAULT_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldBeFound("tempoActividade.equals=" + DEFAULT_TEMPO_ACTIVIDADE);

        // Get all the detalhePlanoAulaList where tempoActividade equals to UPDATED_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldNotBeFound("tempoActividade.equals=" + UPDATED_TEMPO_ACTIVIDADE);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTempoActividadeIsInShouldWork() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tempoActividade in DEFAULT_TEMPO_ACTIVIDADE or UPDATED_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldBeFound("tempoActividade.in=" + DEFAULT_TEMPO_ACTIVIDADE + "," + UPDATED_TEMPO_ACTIVIDADE);

        // Get all the detalhePlanoAulaList where tempoActividade equals to UPDATED_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldNotBeFound("tempoActividade.in=" + UPDATED_TEMPO_ACTIVIDADE);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTempoActividadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tempoActividade is not null
        defaultDetalhePlanoAulaShouldBeFound("tempoActividade.specified=true");

        // Get all the detalhePlanoAulaList where tempoActividade is null
        defaultDetalhePlanoAulaShouldNotBeFound("tempoActividade.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTempoActividadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tempoActividade is greater than or equal to DEFAULT_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldBeFound("tempoActividade.greaterThanOrEqual=" + DEFAULT_TEMPO_ACTIVIDADE);

        // Get all the detalhePlanoAulaList where tempoActividade is greater than or equal to UPDATED_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldNotBeFound("tempoActividade.greaterThanOrEqual=" + UPDATED_TEMPO_ACTIVIDADE);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTempoActividadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tempoActividade is less than or equal to DEFAULT_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldBeFound("tempoActividade.lessThanOrEqual=" + DEFAULT_TEMPO_ACTIVIDADE);

        // Get all the detalhePlanoAulaList where tempoActividade is less than or equal to SMALLER_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldNotBeFound("tempoActividade.lessThanOrEqual=" + SMALLER_TEMPO_ACTIVIDADE);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTempoActividadeIsLessThanSomething() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tempoActividade is less than DEFAULT_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldNotBeFound("tempoActividade.lessThan=" + DEFAULT_TEMPO_ACTIVIDADE);

        // Get all the detalhePlanoAulaList where tempoActividade is less than UPDATED_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldBeFound("tempoActividade.lessThan=" + UPDATED_TEMPO_ACTIVIDADE);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTempoActividadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tempoActividade is greater than DEFAULT_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldNotBeFound("tempoActividade.greaterThan=" + DEFAULT_TEMPO_ACTIVIDADE);

        // Get all the detalhePlanoAulaList where tempoActividade is greater than SMALLER_TEMPO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldBeFound("tempoActividade.greaterThan=" + SMALLER_TEMPO_ACTIVIDADE);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTituloActividadeIsEqualToSomething() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tituloActividade equals to DEFAULT_TITULO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldBeFound("tituloActividade.equals=" + DEFAULT_TITULO_ACTIVIDADE);

        // Get all the detalhePlanoAulaList where tituloActividade equals to UPDATED_TITULO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldNotBeFound("tituloActividade.equals=" + UPDATED_TITULO_ACTIVIDADE);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTituloActividadeIsInShouldWork() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tituloActividade in DEFAULT_TITULO_ACTIVIDADE or UPDATED_TITULO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldBeFound("tituloActividade.in=" + DEFAULT_TITULO_ACTIVIDADE + "," + UPDATED_TITULO_ACTIVIDADE);

        // Get all the detalhePlanoAulaList where tituloActividade equals to UPDATED_TITULO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldNotBeFound("tituloActividade.in=" + UPDATED_TITULO_ACTIVIDADE);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTituloActividadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tituloActividade is not null
        defaultDetalhePlanoAulaShouldBeFound("tituloActividade.specified=true");

        // Get all the detalhePlanoAulaList where tituloActividade is null
        defaultDetalhePlanoAulaShouldNotBeFound("tituloActividade.specified=false");
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTituloActividadeContainsSomething() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tituloActividade contains DEFAULT_TITULO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldBeFound("tituloActividade.contains=" + DEFAULT_TITULO_ACTIVIDADE);

        // Get all the detalhePlanoAulaList where tituloActividade contains UPDATED_TITULO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldNotBeFound("tituloActividade.contains=" + UPDATED_TITULO_ACTIVIDADE);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByTituloActividadeNotContainsSomething() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        // Get all the detalhePlanoAulaList where tituloActividade does not contain DEFAULT_TITULO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldNotBeFound("tituloActividade.doesNotContain=" + DEFAULT_TITULO_ACTIVIDADE);

        // Get all the detalhePlanoAulaList where tituloActividade does not contain UPDATED_TITULO_ACTIVIDADE
        defaultDetalhePlanoAulaShouldBeFound("tituloActividade.doesNotContain=" + UPDATED_TITULO_ACTIVIDADE);
    }

    @Test
    @Transactional
    void getAllDetalhePlanoAulasByPlanoAulaIsEqualToSomething() throws Exception {
        PlanoAula planoAula;
        if (TestUtil.findAll(em, PlanoAula.class).isEmpty()) {
            detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);
            planoAula = PlanoAulaResourceIT.createEntity(em);
        } else {
            planoAula = TestUtil.findAll(em, PlanoAula.class).get(0);
        }
        em.persist(planoAula);
        em.flush();
        detalhePlanoAula.setPlanoAula(planoAula);
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);
        Long planoAulaId = planoAula.getId();

        // Get all the detalhePlanoAulaList where planoAula equals to planoAulaId
        defaultDetalhePlanoAulaShouldBeFound("planoAulaId.equals=" + planoAulaId);

        // Get all the detalhePlanoAulaList where planoAula equals to (planoAulaId + 1)
        defaultDetalhePlanoAulaShouldNotBeFound("planoAulaId.equals=" + (planoAulaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDetalhePlanoAulaShouldBeFound(String filter) throws Exception {
        restDetalhePlanoAulaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalhePlanoAula.getId().intValue())))
            .andExpect(jsonPath("$.[*].estrategiaAula").value(hasItem(DEFAULT_ESTRATEGIA_AULA.toString())))
            .andExpect(jsonPath("$.[*].tempoActividade").value(hasItem(DEFAULT_TEMPO_ACTIVIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].recursosEnsino").value(hasItem(DEFAULT_RECURSOS_ENSINO.toString())))
            .andExpect(jsonPath("$.[*].tituloActividade").value(hasItem(DEFAULT_TITULO_ACTIVIDADE)))
            .andExpect(jsonPath("$.[*].actividadesDocente").value(hasItem(DEFAULT_ACTIVIDADES_DOCENTE.toString())))
            .andExpect(jsonPath("$.[*].actividadesDiscentes").value(hasItem(DEFAULT_ACTIVIDADES_DISCENTES.toString())))
            .andExpect(jsonPath("$.[*].avaliacao").value(hasItem(DEFAULT_AVALIACAO.toString())))
            .andExpect(jsonPath("$.[*].bibliografia").value(hasItem(DEFAULT_BIBLIOGRAFIA.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].pdfContentType").value(hasItem(DEFAULT_PDF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pdf").value(hasItem(Base64Utils.encodeToString(DEFAULT_PDF))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].audioContentType").value(hasItem(DEFAULT_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO))));

        // Check, that the count call also returns 1
        restDetalhePlanoAulaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDetalhePlanoAulaShouldNotBeFound(String filter) throws Exception {
        restDetalhePlanoAulaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDetalhePlanoAulaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDetalhePlanoAula() throws Exception {
        // Get the detalhePlanoAula
        restDetalhePlanoAulaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetalhePlanoAula() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        int databaseSizeBeforeUpdate = detalhePlanoAulaRepository.findAll().size();

        // Update the detalhePlanoAula
        DetalhePlanoAula updatedDetalhePlanoAula = detalhePlanoAulaRepository.findById(detalhePlanoAula.getId()).get();
        // Disconnect from session so that the updates on updatedDetalhePlanoAula are not directly saved in db
        em.detach(updatedDetalhePlanoAula);
        updatedDetalhePlanoAula
            .estrategiaAula(UPDATED_ESTRATEGIA_AULA)
            .tempoActividade(UPDATED_TEMPO_ACTIVIDADE)
            .recursosEnsino(UPDATED_RECURSOS_ENSINO)
            .tituloActividade(UPDATED_TITULO_ACTIVIDADE)
            .actividadesDocente(UPDATED_ACTIVIDADES_DOCENTE)
            .actividadesDiscentes(UPDATED_ACTIVIDADES_DISCENTES)
            .avaliacao(UPDATED_AVALIACAO)
            .bibliografia(UPDATED_BIBLIOGRAFIA)
            .observacao(UPDATED_OBSERVACAO)
            .pdf(UPDATED_PDF)
            .pdfContentType(UPDATED_PDF_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .audio(UPDATED_AUDIO)
            .audioContentType(UPDATED_AUDIO_CONTENT_TYPE);
        DetalhePlanoAulaDTO detalhePlanoAulaDTO = detalhePlanoAulaMapper.toDto(updatedDetalhePlanoAula);

        restDetalhePlanoAulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalhePlanoAulaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhePlanoAulaDTO))
            )
            .andExpect(status().isOk());

        // Validate the DetalhePlanoAula in the database
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeUpdate);
        DetalhePlanoAula testDetalhePlanoAula = detalhePlanoAulaList.get(detalhePlanoAulaList.size() - 1);
        assertThat(testDetalhePlanoAula.getEstrategiaAula()).isEqualTo(UPDATED_ESTRATEGIA_AULA);
        assertThat(testDetalhePlanoAula.getTempoActividade()).isEqualTo(UPDATED_TEMPO_ACTIVIDADE);
        assertThat(testDetalhePlanoAula.getRecursosEnsino()).isEqualTo(UPDATED_RECURSOS_ENSINO);
        assertThat(testDetalhePlanoAula.getTituloActividade()).isEqualTo(UPDATED_TITULO_ACTIVIDADE);
        assertThat(testDetalhePlanoAula.getActividadesDocente()).isEqualTo(UPDATED_ACTIVIDADES_DOCENTE);
        assertThat(testDetalhePlanoAula.getActividadesDiscentes()).isEqualTo(UPDATED_ACTIVIDADES_DISCENTES);
        assertThat(testDetalhePlanoAula.getAvaliacao()).isEqualTo(UPDATED_AVALIACAO);
        assertThat(testDetalhePlanoAula.getBibliografia()).isEqualTo(UPDATED_BIBLIOGRAFIA);
        assertThat(testDetalhePlanoAula.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testDetalhePlanoAula.getPdf()).isEqualTo(UPDATED_PDF);
        assertThat(testDetalhePlanoAula.getPdfContentType()).isEqualTo(UPDATED_PDF_CONTENT_TYPE);
        assertThat(testDetalhePlanoAula.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testDetalhePlanoAula.getVideoContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_TYPE);
        assertThat(testDetalhePlanoAula.getAudio()).isEqualTo(UPDATED_AUDIO);
        assertThat(testDetalhePlanoAula.getAudioContentType()).isEqualTo(UPDATED_AUDIO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingDetalhePlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = detalhePlanoAulaRepository.findAll().size();
        detalhePlanoAula.setId(count.incrementAndGet());

        // Create the DetalhePlanoAula
        DetalhePlanoAulaDTO detalhePlanoAulaDTO = detalhePlanoAulaMapper.toDto(detalhePlanoAula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalhePlanoAulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalhePlanoAulaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhePlanoAulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhePlanoAula in the database
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetalhePlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = detalhePlanoAulaRepository.findAll().size();
        detalhePlanoAula.setId(count.incrementAndGet());

        // Create the DetalhePlanoAula
        DetalhePlanoAulaDTO detalhePlanoAulaDTO = detalhePlanoAulaMapper.toDto(detalhePlanoAula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhePlanoAulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detalhePlanoAulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhePlanoAula in the database
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetalhePlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = detalhePlanoAulaRepository.findAll().size();
        detalhePlanoAula.setId(count.incrementAndGet());

        // Create the DetalhePlanoAula
        DetalhePlanoAulaDTO detalhePlanoAulaDTO = detalhePlanoAulaMapper.toDto(detalhePlanoAula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhePlanoAulaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalhePlanoAulaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalhePlanoAula in the database
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetalhePlanoAulaWithPatch() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        int databaseSizeBeforeUpdate = detalhePlanoAulaRepository.findAll().size();

        // Update the detalhePlanoAula using partial update
        DetalhePlanoAula partialUpdatedDetalhePlanoAula = new DetalhePlanoAula();
        partialUpdatedDetalhePlanoAula.setId(detalhePlanoAula.getId());

        partialUpdatedDetalhePlanoAula
            .tituloActividade(UPDATED_TITULO_ACTIVIDADE)
            .actividadesDocente(UPDATED_ACTIVIDADES_DOCENTE)
            .avaliacao(UPDATED_AVALIACAO)
            .observacao(UPDATED_OBSERVACAO);

        restDetalhePlanoAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalhePlanoAula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalhePlanoAula))
            )
            .andExpect(status().isOk());

        // Validate the DetalhePlanoAula in the database
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeUpdate);
        DetalhePlanoAula testDetalhePlanoAula = detalhePlanoAulaList.get(detalhePlanoAulaList.size() - 1);
        assertThat(testDetalhePlanoAula.getEstrategiaAula()).isEqualTo(DEFAULT_ESTRATEGIA_AULA);
        assertThat(testDetalhePlanoAula.getTempoActividade()).isEqualTo(DEFAULT_TEMPO_ACTIVIDADE);
        assertThat(testDetalhePlanoAula.getRecursosEnsino()).isEqualTo(DEFAULT_RECURSOS_ENSINO);
        assertThat(testDetalhePlanoAula.getTituloActividade()).isEqualTo(UPDATED_TITULO_ACTIVIDADE);
        assertThat(testDetalhePlanoAula.getActividadesDocente()).isEqualTo(UPDATED_ACTIVIDADES_DOCENTE);
        assertThat(testDetalhePlanoAula.getActividadesDiscentes()).isEqualTo(DEFAULT_ACTIVIDADES_DISCENTES);
        assertThat(testDetalhePlanoAula.getAvaliacao()).isEqualTo(UPDATED_AVALIACAO);
        assertThat(testDetalhePlanoAula.getBibliografia()).isEqualTo(DEFAULT_BIBLIOGRAFIA);
        assertThat(testDetalhePlanoAula.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testDetalhePlanoAula.getPdf()).isEqualTo(DEFAULT_PDF);
        assertThat(testDetalhePlanoAula.getPdfContentType()).isEqualTo(DEFAULT_PDF_CONTENT_TYPE);
        assertThat(testDetalhePlanoAula.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testDetalhePlanoAula.getVideoContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_TYPE);
        assertThat(testDetalhePlanoAula.getAudio()).isEqualTo(DEFAULT_AUDIO);
        assertThat(testDetalhePlanoAula.getAudioContentType()).isEqualTo(DEFAULT_AUDIO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateDetalhePlanoAulaWithPatch() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        int databaseSizeBeforeUpdate = detalhePlanoAulaRepository.findAll().size();

        // Update the detalhePlanoAula using partial update
        DetalhePlanoAula partialUpdatedDetalhePlanoAula = new DetalhePlanoAula();
        partialUpdatedDetalhePlanoAula.setId(detalhePlanoAula.getId());

        partialUpdatedDetalhePlanoAula
            .estrategiaAula(UPDATED_ESTRATEGIA_AULA)
            .tempoActividade(UPDATED_TEMPO_ACTIVIDADE)
            .recursosEnsino(UPDATED_RECURSOS_ENSINO)
            .tituloActividade(UPDATED_TITULO_ACTIVIDADE)
            .actividadesDocente(UPDATED_ACTIVIDADES_DOCENTE)
            .actividadesDiscentes(UPDATED_ACTIVIDADES_DISCENTES)
            .avaliacao(UPDATED_AVALIACAO)
            .bibliografia(UPDATED_BIBLIOGRAFIA)
            .observacao(UPDATED_OBSERVACAO)
            .pdf(UPDATED_PDF)
            .pdfContentType(UPDATED_PDF_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .audio(UPDATED_AUDIO)
            .audioContentType(UPDATED_AUDIO_CONTENT_TYPE);

        restDetalhePlanoAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalhePlanoAula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalhePlanoAula))
            )
            .andExpect(status().isOk());

        // Validate the DetalhePlanoAula in the database
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeUpdate);
        DetalhePlanoAula testDetalhePlanoAula = detalhePlanoAulaList.get(detalhePlanoAulaList.size() - 1);
        assertThat(testDetalhePlanoAula.getEstrategiaAula()).isEqualTo(UPDATED_ESTRATEGIA_AULA);
        assertThat(testDetalhePlanoAula.getTempoActividade()).isEqualTo(UPDATED_TEMPO_ACTIVIDADE);
        assertThat(testDetalhePlanoAula.getRecursosEnsino()).isEqualTo(UPDATED_RECURSOS_ENSINO);
        assertThat(testDetalhePlanoAula.getTituloActividade()).isEqualTo(UPDATED_TITULO_ACTIVIDADE);
        assertThat(testDetalhePlanoAula.getActividadesDocente()).isEqualTo(UPDATED_ACTIVIDADES_DOCENTE);
        assertThat(testDetalhePlanoAula.getActividadesDiscentes()).isEqualTo(UPDATED_ACTIVIDADES_DISCENTES);
        assertThat(testDetalhePlanoAula.getAvaliacao()).isEqualTo(UPDATED_AVALIACAO);
        assertThat(testDetalhePlanoAula.getBibliografia()).isEqualTo(UPDATED_BIBLIOGRAFIA);
        assertThat(testDetalhePlanoAula.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testDetalhePlanoAula.getPdf()).isEqualTo(UPDATED_PDF);
        assertThat(testDetalhePlanoAula.getPdfContentType()).isEqualTo(UPDATED_PDF_CONTENT_TYPE);
        assertThat(testDetalhePlanoAula.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testDetalhePlanoAula.getVideoContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_TYPE);
        assertThat(testDetalhePlanoAula.getAudio()).isEqualTo(UPDATED_AUDIO);
        assertThat(testDetalhePlanoAula.getAudioContentType()).isEqualTo(UPDATED_AUDIO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingDetalhePlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = detalhePlanoAulaRepository.findAll().size();
        detalhePlanoAula.setId(count.incrementAndGet());

        // Create the DetalhePlanoAula
        DetalhePlanoAulaDTO detalhePlanoAulaDTO = detalhePlanoAulaMapper.toDto(detalhePlanoAula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalhePlanoAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detalhePlanoAulaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalhePlanoAulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhePlanoAula in the database
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetalhePlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = detalhePlanoAulaRepository.findAll().size();
        detalhePlanoAula.setId(count.incrementAndGet());

        // Create the DetalhePlanoAula
        DetalhePlanoAulaDTO detalhePlanoAulaDTO = detalhePlanoAulaMapper.toDto(detalhePlanoAula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhePlanoAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalhePlanoAulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalhePlanoAula in the database
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetalhePlanoAula() throws Exception {
        int databaseSizeBeforeUpdate = detalhePlanoAulaRepository.findAll().size();
        detalhePlanoAula.setId(count.incrementAndGet());

        // Create the DetalhePlanoAula
        DetalhePlanoAulaDTO detalhePlanoAulaDTO = detalhePlanoAulaMapper.toDto(detalhePlanoAula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalhePlanoAulaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detalhePlanoAulaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalhePlanoAula in the database
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetalhePlanoAula() throws Exception {
        // Initialize the database
        detalhePlanoAulaRepository.saveAndFlush(detalhePlanoAula);

        int databaseSizeBeforeDelete = detalhePlanoAulaRepository.findAll().size();

        // Delete the detalhePlanoAula
        restDetalhePlanoAulaMockMvc
            .perform(delete(ENTITY_API_URL_ID, detalhePlanoAula.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetalhePlanoAula> detalhePlanoAulaList = detalhePlanoAulaRepository.findAll();
        assertThat(detalhePlanoAulaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
