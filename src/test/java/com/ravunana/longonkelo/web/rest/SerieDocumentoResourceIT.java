package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.DocumentoComercial;
import com.ravunana.longonkelo.domain.SequenciaDocumento;
import com.ravunana.longonkelo.domain.SerieDocumento;
import com.ravunana.longonkelo.repository.SerieDocumentoRepository;
import com.ravunana.longonkelo.service.SerieDocumentoService;
import com.ravunana.longonkelo.service.criteria.SerieDocumentoCriteria;
import com.ravunana.longonkelo.service.dto.SerieDocumentoDTO;
import com.ravunana.longonkelo.service.mapper.SerieDocumentoMapper;
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
 * Integration tests for the {@link SerieDocumentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SerieDocumentoResourceIT {

    private static final Integer DEFAULT_ANO_FISCAL = 1;
    private static final Integer UPDATED_ANO_FISCAL = 2;
    private static final Integer SMALLER_ANO_FISCAL = 1 - 1;

    private static final Integer DEFAULT_VERSAO = 0;
    private static final Integer UPDATED_VERSAO = 1;
    private static final Integer SMALLER_VERSAO = 0 - 1;

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ATIVO = false;
    private static final Boolean UPDATED_IS_ATIVO = true;

    private static final Boolean DEFAULT_IS_PADRAO = false;
    private static final Boolean UPDATED_IS_PADRAO = true;

    private static final String ENTITY_API_URL = "/api/serie-documentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SerieDocumentoRepository serieDocumentoRepository;

    @Mock
    private SerieDocumentoRepository serieDocumentoRepositoryMock;

    @Autowired
    private SerieDocumentoMapper serieDocumentoMapper;

    @Mock
    private SerieDocumentoService serieDocumentoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSerieDocumentoMockMvc;

    private SerieDocumento serieDocumento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SerieDocumento createEntity(EntityManager em) {
        SerieDocumento serieDocumento = new SerieDocumento()
            .anoFiscal(DEFAULT_ANO_FISCAL)
            .versao(DEFAULT_VERSAO)
            .serie(DEFAULT_SERIE)
            .isAtivo(DEFAULT_IS_ATIVO)
            .isPadrao(DEFAULT_IS_PADRAO);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        serieDocumento.setTipoDocumento(documentoComercial);
        return serieDocumento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SerieDocumento createUpdatedEntity(EntityManager em) {
        SerieDocumento serieDocumento = new SerieDocumento()
            .anoFiscal(UPDATED_ANO_FISCAL)
            .versao(UPDATED_VERSAO)
            .serie(UPDATED_SERIE)
            .isAtivo(UPDATED_IS_ATIVO)
            .isPadrao(UPDATED_IS_PADRAO);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createUpdatedEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        serieDocumento.setTipoDocumento(documentoComercial);
        return serieDocumento;
    }

    @BeforeEach
    public void initTest() {
        serieDocumento = createEntity(em);
    }

    @Test
    @Transactional
    void createSerieDocumento() throws Exception {
        int databaseSizeBeforeCreate = serieDocumentoRepository.findAll().size();
        // Create the SerieDocumento
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);
        restSerieDocumentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeCreate + 1);
        SerieDocumento testSerieDocumento = serieDocumentoList.get(serieDocumentoList.size() - 1);
        assertThat(testSerieDocumento.getAnoFiscal()).isEqualTo(DEFAULT_ANO_FISCAL);
        assertThat(testSerieDocumento.getVersao()).isEqualTo(DEFAULT_VERSAO);
        assertThat(testSerieDocumento.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testSerieDocumento.getIsAtivo()).isEqualTo(DEFAULT_IS_ATIVO);
        assertThat(testSerieDocumento.getIsPadrao()).isEqualTo(DEFAULT_IS_PADRAO);
    }

    @Test
    @Transactional
    void createSerieDocumentoWithExistingId() throws Exception {
        // Create the SerieDocumento with an existing ID
        serieDocumento.setId(1L);
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        int databaseSizeBeforeCreate = serieDocumentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSerieDocumentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVersaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = serieDocumentoRepository.findAll().size();
        // set the field null
        serieDocumento.setVersao(null);

        // Create the SerieDocumento, which fails.
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        restSerieDocumentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSerieIsRequired() throws Exception {
        int databaseSizeBeforeTest = serieDocumentoRepository.findAll().size();
        // set the field null
        serieDocumento.setSerie(null);

        // Create the SerieDocumento, which fails.
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        restSerieDocumentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSerieDocumentos() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList
        restSerieDocumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serieDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].anoFiscal").value(hasItem(DEFAULT_ANO_FISCAL)))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO)))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].isAtivo").value(hasItem(DEFAULT_IS_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].isPadrao").value(hasItem(DEFAULT_IS_PADRAO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSerieDocumentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(serieDocumentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSerieDocumentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(serieDocumentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSerieDocumentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(serieDocumentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSerieDocumentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(serieDocumentoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSerieDocumento() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get the serieDocumento
        restSerieDocumentoMockMvc
            .perform(get(ENTITY_API_URL_ID, serieDocumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serieDocumento.getId().intValue()))
            .andExpect(jsonPath("$.anoFiscal").value(DEFAULT_ANO_FISCAL))
            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE))
            .andExpect(jsonPath("$.isAtivo").value(DEFAULT_IS_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.isPadrao").value(DEFAULT_IS_PADRAO.booleanValue()));
    }

    @Test
    @Transactional
    void getSerieDocumentosByIdFiltering() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        Long id = serieDocumento.getId();

        defaultSerieDocumentoShouldBeFound("id.equals=" + id);
        defaultSerieDocumentoShouldNotBeFound("id.notEquals=" + id);

        defaultSerieDocumentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSerieDocumentoShouldNotBeFound("id.greaterThan=" + id);

        defaultSerieDocumentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSerieDocumentoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByAnoFiscalIsEqualToSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where anoFiscal equals to DEFAULT_ANO_FISCAL
        defaultSerieDocumentoShouldBeFound("anoFiscal.equals=" + DEFAULT_ANO_FISCAL);

        // Get all the serieDocumentoList where anoFiscal equals to UPDATED_ANO_FISCAL
        defaultSerieDocumentoShouldNotBeFound("anoFiscal.equals=" + UPDATED_ANO_FISCAL);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByAnoFiscalIsInShouldWork() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where anoFiscal in DEFAULT_ANO_FISCAL or UPDATED_ANO_FISCAL
        defaultSerieDocumentoShouldBeFound("anoFiscal.in=" + DEFAULT_ANO_FISCAL + "," + UPDATED_ANO_FISCAL);

        // Get all the serieDocumentoList where anoFiscal equals to UPDATED_ANO_FISCAL
        defaultSerieDocumentoShouldNotBeFound("anoFiscal.in=" + UPDATED_ANO_FISCAL);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByAnoFiscalIsNullOrNotNull() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where anoFiscal is not null
        defaultSerieDocumentoShouldBeFound("anoFiscal.specified=true");

        // Get all the serieDocumentoList where anoFiscal is null
        defaultSerieDocumentoShouldNotBeFound("anoFiscal.specified=false");
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByAnoFiscalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where anoFiscal is greater than or equal to DEFAULT_ANO_FISCAL
        defaultSerieDocumentoShouldBeFound("anoFiscal.greaterThanOrEqual=" + DEFAULT_ANO_FISCAL);

        // Get all the serieDocumentoList where anoFiscal is greater than or equal to UPDATED_ANO_FISCAL
        defaultSerieDocumentoShouldNotBeFound("anoFiscal.greaterThanOrEqual=" + UPDATED_ANO_FISCAL);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByAnoFiscalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where anoFiscal is less than or equal to DEFAULT_ANO_FISCAL
        defaultSerieDocumentoShouldBeFound("anoFiscal.lessThanOrEqual=" + DEFAULT_ANO_FISCAL);

        // Get all the serieDocumentoList where anoFiscal is less than or equal to SMALLER_ANO_FISCAL
        defaultSerieDocumentoShouldNotBeFound("anoFiscal.lessThanOrEqual=" + SMALLER_ANO_FISCAL);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByAnoFiscalIsLessThanSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where anoFiscal is less than DEFAULT_ANO_FISCAL
        defaultSerieDocumentoShouldNotBeFound("anoFiscal.lessThan=" + DEFAULT_ANO_FISCAL);

        // Get all the serieDocumentoList where anoFiscal is less than UPDATED_ANO_FISCAL
        defaultSerieDocumentoShouldBeFound("anoFiscal.lessThan=" + UPDATED_ANO_FISCAL);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByAnoFiscalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where anoFiscal is greater than DEFAULT_ANO_FISCAL
        defaultSerieDocumentoShouldNotBeFound("anoFiscal.greaterThan=" + DEFAULT_ANO_FISCAL);

        // Get all the serieDocumentoList where anoFiscal is greater than SMALLER_ANO_FISCAL
        defaultSerieDocumentoShouldBeFound("anoFiscal.greaterThan=" + SMALLER_ANO_FISCAL);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByVersaoIsEqualToSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where versao equals to DEFAULT_VERSAO
        defaultSerieDocumentoShouldBeFound("versao.equals=" + DEFAULT_VERSAO);

        // Get all the serieDocumentoList where versao equals to UPDATED_VERSAO
        defaultSerieDocumentoShouldNotBeFound("versao.equals=" + UPDATED_VERSAO);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByVersaoIsInShouldWork() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where versao in DEFAULT_VERSAO or UPDATED_VERSAO
        defaultSerieDocumentoShouldBeFound("versao.in=" + DEFAULT_VERSAO + "," + UPDATED_VERSAO);

        // Get all the serieDocumentoList where versao equals to UPDATED_VERSAO
        defaultSerieDocumentoShouldNotBeFound("versao.in=" + UPDATED_VERSAO);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByVersaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where versao is not null
        defaultSerieDocumentoShouldBeFound("versao.specified=true");

        // Get all the serieDocumentoList where versao is null
        defaultSerieDocumentoShouldNotBeFound("versao.specified=false");
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByVersaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where versao is greater than or equal to DEFAULT_VERSAO
        defaultSerieDocumentoShouldBeFound("versao.greaterThanOrEqual=" + DEFAULT_VERSAO);

        // Get all the serieDocumentoList where versao is greater than or equal to UPDATED_VERSAO
        defaultSerieDocumentoShouldNotBeFound("versao.greaterThanOrEqual=" + UPDATED_VERSAO);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByVersaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where versao is less than or equal to DEFAULT_VERSAO
        defaultSerieDocumentoShouldBeFound("versao.lessThanOrEqual=" + DEFAULT_VERSAO);

        // Get all the serieDocumentoList where versao is less than or equal to SMALLER_VERSAO
        defaultSerieDocumentoShouldNotBeFound("versao.lessThanOrEqual=" + SMALLER_VERSAO);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByVersaoIsLessThanSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where versao is less than DEFAULT_VERSAO
        defaultSerieDocumentoShouldNotBeFound("versao.lessThan=" + DEFAULT_VERSAO);

        // Get all the serieDocumentoList where versao is less than UPDATED_VERSAO
        defaultSerieDocumentoShouldBeFound("versao.lessThan=" + UPDATED_VERSAO);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByVersaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where versao is greater than DEFAULT_VERSAO
        defaultSerieDocumentoShouldNotBeFound("versao.greaterThan=" + DEFAULT_VERSAO);

        // Get all the serieDocumentoList where versao is greater than SMALLER_VERSAO
        defaultSerieDocumentoShouldBeFound("versao.greaterThan=" + SMALLER_VERSAO);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosBySerieIsEqualToSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where serie equals to DEFAULT_SERIE
        defaultSerieDocumentoShouldBeFound("serie.equals=" + DEFAULT_SERIE);

        // Get all the serieDocumentoList where serie equals to UPDATED_SERIE
        defaultSerieDocumentoShouldNotBeFound("serie.equals=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosBySerieIsInShouldWork() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where serie in DEFAULT_SERIE or UPDATED_SERIE
        defaultSerieDocumentoShouldBeFound("serie.in=" + DEFAULT_SERIE + "," + UPDATED_SERIE);

        // Get all the serieDocumentoList where serie equals to UPDATED_SERIE
        defaultSerieDocumentoShouldNotBeFound("serie.in=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosBySerieIsNullOrNotNull() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where serie is not null
        defaultSerieDocumentoShouldBeFound("serie.specified=true");

        // Get all the serieDocumentoList where serie is null
        defaultSerieDocumentoShouldNotBeFound("serie.specified=false");
    }

    @Test
    @Transactional
    void getAllSerieDocumentosBySerieContainsSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where serie contains DEFAULT_SERIE
        defaultSerieDocumentoShouldBeFound("serie.contains=" + DEFAULT_SERIE);

        // Get all the serieDocumentoList where serie contains UPDATED_SERIE
        defaultSerieDocumentoShouldNotBeFound("serie.contains=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosBySerieNotContainsSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where serie does not contain DEFAULT_SERIE
        defaultSerieDocumentoShouldNotBeFound("serie.doesNotContain=" + DEFAULT_SERIE);

        // Get all the serieDocumentoList where serie does not contain UPDATED_SERIE
        defaultSerieDocumentoShouldBeFound("serie.doesNotContain=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByIsAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where isAtivo equals to DEFAULT_IS_ATIVO
        defaultSerieDocumentoShouldBeFound("isAtivo.equals=" + DEFAULT_IS_ATIVO);

        // Get all the serieDocumentoList where isAtivo equals to UPDATED_IS_ATIVO
        defaultSerieDocumentoShouldNotBeFound("isAtivo.equals=" + UPDATED_IS_ATIVO);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByIsAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where isAtivo in DEFAULT_IS_ATIVO or UPDATED_IS_ATIVO
        defaultSerieDocumentoShouldBeFound("isAtivo.in=" + DEFAULT_IS_ATIVO + "," + UPDATED_IS_ATIVO);

        // Get all the serieDocumentoList where isAtivo equals to UPDATED_IS_ATIVO
        defaultSerieDocumentoShouldNotBeFound("isAtivo.in=" + UPDATED_IS_ATIVO);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByIsAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where isAtivo is not null
        defaultSerieDocumentoShouldBeFound("isAtivo.specified=true");

        // Get all the serieDocumentoList where isAtivo is null
        defaultSerieDocumentoShouldNotBeFound("isAtivo.specified=false");
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByIsPadraoIsEqualToSomething() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where isPadrao equals to DEFAULT_IS_PADRAO
        defaultSerieDocumentoShouldBeFound("isPadrao.equals=" + DEFAULT_IS_PADRAO);

        // Get all the serieDocumentoList where isPadrao equals to UPDATED_IS_PADRAO
        defaultSerieDocumentoShouldNotBeFound("isPadrao.equals=" + UPDATED_IS_PADRAO);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByIsPadraoIsInShouldWork() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where isPadrao in DEFAULT_IS_PADRAO or UPDATED_IS_PADRAO
        defaultSerieDocumentoShouldBeFound("isPadrao.in=" + DEFAULT_IS_PADRAO + "," + UPDATED_IS_PADRAO);

        // Get all the serieDocumentoList where isPadrao equals to UPDATED_IS_PADRAO
        defaultSerieDocumentoShouldNotBeFound("isPadrao.in=" + UPDATED_IS_PADRAO);
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByIsPadraoIsNullOrNotNull() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList where isPadrao is not null
        defaultSerieDocumentoShouldBeFound("isPadrao.specified=true");

        // Get all the serieDocumentoList where isPadrao is null
        defaultSerieDocumentoShouldNotBeFound("isPadrao.specified=false");
    }

    @Test
    @Transactional
    void getAllSerieDocumentosBySequenciaDocumentoIsEqualToSomething() throws Exception {
        SequenciaDocumento sequenciaDocumento;
        if (TestUtil.findAll(em, SequenciaDocumento.class).isEmpty()) {
            serieDocumentoRepository.saveAndFlush(serieDocumento);
            sequenciaDocumento = SequenciaDocumentoResourceIT.createEntity(em);
        } else {
            sequenciaDocumento = TestUtil.findAll(em, SequenciaDocumento.class).get(0);
        }
        em.persist(sequenciaDocumento);
        em.flush();
        serieDocumento.addSequenciaDocumento(sequenciaDocumento);
        serieDocumentoRepository.saveAndFlush(serieDocumento);
        Long sequenciaDocumentoId = sequenciaDocumento.getId();

        // Get all the serieDocumentoList where sequenciaDocumento equals to sequenciaDocumentoId
        defaultSerieDocumentoShouldBeFound("sequenciaDocumentoId.equals=" + sequenciaDocumentoId);

        // Get all the serieDocumentoList where sequenciaDocumento equals to (sequenciaDocumentoId + 1)
        defaultSerieDocumentoShouldNotBeFound("sequenciaDocumentoId.equals=" + (sequenciaDocumentoId + 1));
    }

    @Test
    @Transactional
    void getAllSerieDocumentosByTipoDocumentoIsEqualToSomething() throws Exception {
        DocumentoComercial tipoDocumento;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            serieDocumentoRepository.saveAndFlush(serieDocumento);
            tipoDocumento = DocumentoComercialResourceIT.createEntity(em);
        } else {
            tipoDocumento = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        em.persist(tipoDocumento);
        em.flush();
        serieDocumento.setTipoDocumento(tipoDocumento);
        serieDocumentoRepository.saveAndFlush(serieDocumento);
        Long tipoDocumentoId = tipoDocumento.getId();

        // Get all the serieDocumentoList where tipoDocumento equals to tipoDocumentoId
        defaultSerieDocumentoShouldBeFound("tipoDocumentoId.equals=" + tipoDocumentoId);

        // Get all the serieDocumentoList where tipoDocumento equals to (tipoDocumentoId + 1)
        defaultSerieDocumentoShouldNotBeFound("tipoDocumentoId.equals=" + (tipoDocumentoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSerieDocumentoShouldBeFound(String filter) throws Exception {
        restSerieDocumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serieDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].anoFiscal").value(hasItem(DEFAULT_ANO_FISCAL)))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO)))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].isAtivo").value(hasItem(DEFAULT_IS_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].isPadrao").value(hasItem(DEFAULT_IS_PADRAO.booleanValue())));

        // Check, that the count call also returns 1
        restSerieDocumentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSerieDocumentoShouldNotBeFound(String filter) throws Exception {
        restSerieDocumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSerieDocumentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSerieDocumento() throws Exception {
        // Get the serieDocumento
        restSerieDocumentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSerieDocumento() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();

        // Update the serieDocumento
        SerieDocumento updatedSerieDocumento = serieDocumentoRepository.findById(serieDocumento.getId()).get();
        // Disconnect from session so that the updates on updatedSerieDocumento are not directly saved in db
        em.detach(updatedSerieDocumento);
        updatedSerieDocumento
            .anoFiscal(UPDATED_ANO_FISCAL)
            .versao(UPDATED_VERSAO)
            .serie(UPDATED_SERIE)
            .isAtivo(UPDATED_IS_ATIVO)
            .isPadrao(UPDATED_IS_PADRAO);
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(updatedSerieDocumento);

        restSerieDocumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serieDocumentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
        SerieDocumento testSerieDocumento = serieDocumentoList.get(serieDocumentoList.size() - 1);
        assertThat(testSerieDocumento.getAnoFiscal()).isEqualTo(UPDATED_ANO_FISCAL);
        assertThat(testSerieDocumento.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testSerieDocumento.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testSerieDocumento.getIsAtivo()).isEqualTo(UPDATED_IS_ATIVO);
        assertThat(testSerieDocumento.getIsPadrao()).isEqualTo(UPDATED_IS_PADRAO);
    }

    @Test
    @Transactional
    void putNonExistingSerieDocumento() throws Exception {
        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();
        serieDocumento.setId(count.incrementAndGet());

        // Create the SerieDocumento
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSerieDocumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serieDocumentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSerieDocumento() throws Exception {
        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();
        serieDocumento.setId(count.incrementAndGet());

        // Create the SerieDocumento
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSerieDocumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSerieDocumento() throws Exception {
        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();
        serieDocumento.setId(count.incrementAndGet());

        // Create the SerieDocumento
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSerieDocumentoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSerieDocumentoWithPatch() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();

        // Update the serieDocumento using partial update
        SerieDocumento partialUpdatedSerieDocumento = new SerieDocumento();
        partialUpdatedSerieDocumento.setId(serieDocumento.getId());

        partialUpdatedSerieDocumento.anoFiscal(UPDATED_ANO_FISCAL).versao(UPDATED_VERSAO);

        restSerieDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSerieDocumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSerieDocumento))
            )
            .andExpect(status().isOk());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
        SerieDocumento testSerieDocumento = serieDocumentoList.get(serieDocumentoList.size() - 1);
        assertThat(testSerieDocumento.getAnoFiscal()).isEqualTo(UPDATED_ANO_FISCAL);
        assertThat(testSerieDocumento.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testSerieDocumento.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testSerieDocumento.getIsAtivo()).isEqualTo(DEFAULT_IS_ATIVO);
        assertThat(testSerieDocumento.getIsPadrao()).isEqualTo(DEFAULT_IS_PADRAO);
    }

    @Test
    @Transactional
    void fullUpdateSerieDocumentoWithPatch() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();

        // Update the serieDocumento using partial update
        SerieDocumento partialUpdatedSerieDocumento = new SerieDocumento();
        partialUpdatedSerieDocumento.setId(serieDocumento.getId());

        partialUpdatedSerieDocumento
            .anoFiscal(UPDATED_ANO_FISCAL)
            .versao(UPDATED_VERSAO)
            .serie(UPDATED_SERIE)
            .isAtivo(UPDATED_IS_ATIVO)
            .isPadrao(UPDATED_IS_PADRAO);

        restSerieDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSerieDocumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSerieDocumento))
            )
            .andExpect(status().isOk());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
        SerieDocumento testSerieDocumento = serieDocumentoList.get(serieDocumentoList.size() - 1);
        assertThat(testSerieDocumento.getAnoFiscal()).isEqualTo(UPDATED_ANO_FISCAL);
        assertThat(testSerieDocumento.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testSerieDocumento.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testSerieDocumento.getIsAtivo()).isEqualTo(UPDATED_IS_ATIVO);
        assertThat(testSerieDocumento.getIsPadrao()).isEqualTo(UPDATED_IS_PADRAO);
    }

    @Test
    @Transactional
    void patchNonExistingSerieDocumento() throws Exception {
        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();
        serieDocumento.setId(count.incrementAndGet());

        // Create the SerieDocumento
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSerieDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serieDocumentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSerieDocumento() throws Exception {
        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();
        serieDocumento.setId(count.incrementAndGet());

        // Create the SerieDocumento
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSerieDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSerieDocumento() throws Exception {
        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();
        serieDocumento.setId(count.incrementAndGet());

        // Create the SerieDocumento
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSerieDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSerieDocumento() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        int databaseSizeBeforeDelete = serieDocumentoRepository.findAll().size();

        // Delete the serieDocumento
        restSerieDocumentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, serieDocumento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
