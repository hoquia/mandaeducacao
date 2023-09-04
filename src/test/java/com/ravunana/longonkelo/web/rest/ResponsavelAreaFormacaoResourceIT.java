package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.domain.AreaFormacao;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.ResponsavelAreaFormacao;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.ResponsavelAreaFormacaoRepository;
import com.ravunana.longonkelo.service.ResponsavelAreaFormacaoService;
import com.ravunana.longonkelo.service.criteria.ResponsavelAreaFormacaoCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelAreaFormacaoDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelAreaFormacaoMapper;
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
 * Integration tests for the {@link ResponsavelAreaFormacaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ResponsavelAreaFormacaoResourceIT {

    private static final LocalDate DEFAULT_DE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_ATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/responsavel-area-formacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResponsavelAreaFormacaoRepository responsavelAreaFormacaoRepository;

    @Mock
    private ResponsavelAreaFormacaoRepository responsavelAreaFormacaoRepositoryMock;

    @Autowired
    private ResponsavelAreaFormacaoMapper responsavelAreaFormacaoMapper;

    @Mock
    private ResponsavelAreaFormacaoService responsavelAreaFormacaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsavelAreaFormacaoMockMvc;

    private ResponsavelAreaFormacao responsavelAreaFormacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelAreaFormacao createEntity(EntityManager em) {
        ResponsavelAreaFormacao responsavelAreaFormacao = new ResponsavelAreaFormacao()
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .descricao(DEFAULT_DESCRICAO)
            .timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        AreaFormacao areaFormacao;
        if (TestUtil.findAll(em, AreaFormacao.class).isEmpty()) {
            areaFormacao = AreaFormacaoResourceIT.createEntity(em);
            em.persist(areaFormacao);
            em.flush();
        } else {
            areaFormacao = TestUtil.findAll(em, AreaFormacao.class).get(0);
        }
        responsavelAreaFormacao.setAreaFormacao(areaFormacao);
        return responsavelAreaFormacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelAreaFormacao createUpdatedEntity(EntityManager em) {
        ResponsavelAreaFormacao responsavelAreaFormacao = new ResponsavelAreaFormacao()
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .descricao(UPDATED_DESCRICAO)
            .timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        AreaFormacao areaFormacao;
        if (TestUtil.findAll(em, AreaFormacao.class).isEmpty()) {
            areaFormacao = AreaFormacaoResourceIT.createUpdatedEntity(em);
            em.persist(areaFormacao);
            em.flush();
        } else {
            areaFormacao = TestUtil.findAll(em, AreaFormacao.class).get(0);
        }
        responsavelAreaFormacao.setAreaFormacao(areaFormacao);
        return responsavelAreaFormacao;
    }

    @BeforeEach
    public void initTest() {
        responsavelAreaFormacao = createEntity(em);
    }

    @Test
    @Transactional
    void createResponsavelAreaFormacao() throws Exception {
        int databaseSizeBeforeCreate = responsavelAreaFormacaoRepository.findAll().size();
        // Create the ResponsavelAreaFormacao
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);
        restResponsavelAreaFormacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelAreaFormacaoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResponsavelAreaFormacao in the database
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeCreate + 1);
        ResponsavelAreaFormacao testResponsavelAreaFormacao = responsavelAreaFormacaoList.get(responsavelAreaFormacaoList.size() - 1);
        assertThat(testResponsavelAreaFormacao.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testResponsavelAreaFormacao.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testResponsavelAreaFormacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testResponsavelAreaFormacao.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createResponsavelAreaFormacaoWithExistingId() throws Exception {
        // Create the ResponsavelAreaFormacao with an existing ID
        responsavelAreaFormacao.setId(1L);
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);

        int databaseSizeBeforeCreate = responsavelAreaFormacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelAreaFormacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelAreaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelAreaFormacao in the database
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDeIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelAreaFormacaoRepository.findAll().size();
        // set the field null
        responsavelAreaFormacao.setDe(null);

        // Create the ResponsavelAreaFormacao, which fails.
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);

        restResponsavelAreaFormacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelAreaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAteIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelAreaFormacaoRepository.findAll().size();
        // set the field null
        responsavelAreaFormacao.setAte(null);

        // Create the ResponsavelAreaFormacao, which fails.
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);

        restResponsavelAreaFormacaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelAreaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaos() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList
        restResponsavelAreaFormacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelAreaFormacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelAreaFormacaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(responsavelAreaFormacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelAreaFormacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(responsavelAreaFormacaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelAreaFormacaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(responsavelAreaFormacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelAreaFormacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(responsavelAreaFormacaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getResponsavelAreaFormacao() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get the responsavelAreaFormacao
        restResponsavelAreaFormacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, responsavelAreaFormacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsavelAreaFormacao.getId().intValue()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.ate").value(DEFAULT_ATE.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getResponsavelAreaFormacaosByIdFiltering() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        Long id = responsavelAreaFormacao.getId();

        defaultResponsavelAreaFormacaoShouldBeFound("id.equals=" + id);
        defaultResponsavelAreaFormacaoShouldNotBeFound("id.notEquals=" + id);

        defaultResponsavelAreaFormacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResponsavelAreaFormacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultResponsavelAreaFormacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResponsavelAreaFormacaoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByDeIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where de equals to DEFAULT_DE
        defaultResponsavelAreaFormacaoShouldBeFound("de.equals=" + DEFAULT_DE);

        // Get all the responsavelAreaFormacaoList where de equals to UPDATED_DE
        defaultResponsavelAreaFormacaoShouldNotBeFound("de.equals=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByDeIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where de in DEFAULT_DE or UPDATED_DE
        defaultResponsavelAreaFormacaoShouldBeFound("de.in=" + DEFAULT_DE + "," + UPDATED_DE);

        // Get all the responsavelAreaFormacaoList where de equals to UPDATED_DE
        defaultResponsavelAreaFormacaoShouldNotBeFound("de.in=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByDeIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where de is not null
        defaultResponsavelAreaFormacaoShouldBeFound("de.specified=true");

        // Get all the responsavelAreaFormacaoList where de is null
        defaultResponsavelAreaFormacaoShouldNotBeFound("de.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByDeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where de is greater than or equal to DEFAULT_DE
        defaultResponsavelAreaFormacaoShouldBeFound("de.greaterThanOrEqual=" + DEFAULT_DE);

        // Get all the responsavelAreaFormacaoList where de is greater than or equal to UPDATED_DE
        defaultResponsavelAreaFormacaoShouldNotBeFound("de.greaterThanOrEqual=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByDeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where de is less than or equal to DEFAULT_DE
        defaultResponsavelAreaFormacaoShouldBeFound("de.lessThanOrEqual=" + DEFAULT_DE);

        // Get all the responsavelAreaFormacaoList where de is less than or equal to SMALLER_DE
        defaultResponsavelAreaFormacaoShouldNotBeFound("de.lessThanOrEqual=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByDeIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where de is less than DEFAULT_DE
        defaultResponsavelAreaFormacaoShouldNotBeFound("de.lessThan=" + DEFAULT_DE);

        // Get all the responsavelAreaFormacaoList where de is less than UPDATED_DE
        defaultResponsavelAreaFormacaoShouldBeFound("de.lessThan=" + UPDATED_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByDeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where de is greater than DEFAULT_DE
        defaultResponsavelAreaFormacaoShouldNotBeFound("de.greaterThan=" + DEFAULT_DE);

        // Get all the responsavelAreaFormacaoList where de is greater than SMALLER_DE
        defaultResponsavelAreaFormacaoShouldBeFound("de.greaterThan=" + SMALLER_DE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByAteIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where ate equals to DEFAULT_ATE
        defaultResponsavelAreaFormacaoShouldBeFound("ate.equals=" + DEFAULT_ATE);

        // Get all the responsavelAreaFormacaoList where ate equals to UPDATED_ATE
        defaultResponsavelAreaFormacaoShouldNotBeFound("ate.equals=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByAteIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where ate in DEFAULT_ATE or UPDATED_ATE
        defaultResponsavelAreaFormacaoShouldBeFound("ate.in=" + DEFAULT_ATE + "," + UPDATED_ATE);

        // Get all the responsavelAreaFormacaoList where ate equals to UPDATED_ATE
        defaultResponsavelAreaFormacaoShouldNotBeFound("ate.in=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByAteIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where ate is not null
        defaultResponsavelAreaFormacaoShouldBeFound("ate.specified=true");

        // Get all the responsavelAreaFormacaoList where ate is null
        defaultResponsavelAreaFormacaoShouldNotBeFound("ate.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByAteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where ate is greater than or equal to DEFAULT_ATE
        defaultResponsavelAreaFormacaoShouldBeFound("ate.greaterThanOrEqual=" + DEFAULT_ATE);

        // Get all the responsavelAreaFormacaoList where ate is greater than or equal to UPDATED_ATE
        defaultResponsavelAreaFormacaoShouldNotBeFound("ate.greaterThanOrEqual=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByAteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where ate is less than or equal to DEFAULT_ATE
        defaultResponsavelAreaFormacaoShouldBeFound("ate.lessThanOrEqual=" + DEFAULT_ATE);

        // Get all the responsavelAreaFormacaoList where ate is less than or equal to SMALLER_ATE
        defaultResponsavelAreaFormacaoShouldNotBeFound("ate.lessThanOrEqual=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByAteIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where ate is less than DEFAULT_ATE
        defaultResponsavelAreaFormacaoShouldNotBeFound("ate.lessThan=" + DEFAULT_ATE);

        // Get all the responsavelAreaFormacaoList where ate is less than UPDATED_ATE
        defaultResponsavelAreaFormacaoShouldBeFound("ate.lessThan=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByAteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where ate is greater than DEFAULT_ATE
        defaultResponsavelAreaFormacaoShouldNotBeFound("ate.greaterThan=" + DEFAULT_ATE);

        // Get all the responsavelAreaFormacaoList where ate is greater than SMALLER_ATE
        defaultResponsavelAreaFormacaoShouldBeFound("ate.greaterThan=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where timestamp equals to DEFAULT_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelAreaFormacaoList where timestamp equals to UPDATED_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the responsavelAreaFormacaoList where timestamp equals to UPDATED_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where timestamp is not null
        defaultResponsavelAreaFormacaoShouldBeFound("timestamp.specified=true");

        // Get all the responsavelAreaFormacaoList where timestamp is null
        defaultResponsavelAreaFormacaoShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelAreaFormacaoList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelAreaFormacaoList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where timestamp is less than DEFAULT_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelAreaFormacaoList where timestamp is less than UPDATED_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        // Get all the responsavelAreaFormacaoList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the responsavelAreaFormacaoList where timestamp is greater than SMALLER_TIMESTAMP
        defaultResponsavelAreaFormacaoShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByResponsavelIsEqualToSomething() throws Exception {
        Docente responsavel;
        if (TestUtil.findAll(em, Docente.class).isEmpty()) {
            responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);
            responsavel = DocenteResourceIT.createEntity(em);
        } else {
            responsavel = TestUtil.findAll(em, Docente.class).get(0);
        }
        em.persist(responsavel);
        em.flush();
        responsavelAreaFormacao.addResponsavel(responsavel);
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);
        Long responsavelId = responsavel.getId();

        // Get all the responsavelAreaFormacaoList where responsavel equals to responsavelId
        defaultResponsavelAreaFormacaoShouldBeFound("responsavelId.equals=" + responsavelId);

        // Get all the responsavelAreaFormacaoList where responsavel equals to (responsavelId + 1)
        defaultResponsavelAreaFormacaoShouldNotBeFound("responsavelId.equals=" + (responsavelId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByAnoLectivoIsEqualToSomething() throws Exception {
        AnoLectivo anoLectivo;
        if (TestUtil.findAll(em, AnoLectivo.class).isEmpty()) {
            responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);
            anoLectivo = AnoLectivoResourceIT.createEntity(em);
        } else {
            anoLectivo = TestUtil.findAll(em, AnoLectivo.class).get(0);
        }
        em.persist(anoLectivo);
        em.flush();
        responsavelAreaFormacao.addAnoLectivo(anoLectivo);
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);
        Long anoLectivoId = anoLectivo.getId();

        // Get all the responsavelAreaFormacaoList where anoLectivo equals to anoLectivoId
        defaultResponsavelAreaFormacaoShouldBeFound("anoLectivoId.equals=" + anoLectivoId);

        // Get all the responsavelAreaFormacaoList where anoLectivo equals to (anoLectivoId + 1)
        defaultResponsavelAreaFormacaoShouldNotBeFound("anoLectivoId.equals=" + (anoLectivoId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        responsavelAreaFormacao.setUtilizador(utilizador);
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);
        Long utilizadorId = utilizador.getId();

        // Get all the responsavelAreaFormacaoList where utilizador equals to utilizadorId
        defaultResponsavelAreaFormacaoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the responsavelAreaFormacaoList where utilizador equals to (utilizadorId + 1)
        defaultResponsavelAreaFormacaoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllResponsavelAreaFormacaosByAreaFormacaoIsEqualToSomething() throws Exception {
        AreaFormacao areaFormacao;
        if (TestUtil.findAll(em, AreaFormacao.class).isEmpty()) {
            responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);
            areaFormacao = AreaFormacaoResourceIT.createEntity(em);
        } else {
            areaFormacao = TestUtil.findAll(em, AreaFormacao.class).get(0);
        }
        em.persist(areaFormacao);
        em.flush();
        responsavelAreaFormacao.setAreaFormacao(areaFormacao);
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);
        Long areaFormacaoId = areaFormacao.getId();

        // Get all the responsavelAreaFormacaoList where areaFormacao equals to areaFormacaoId
        defaultResponsavelAreaFormacaoShouldBeFound("areaFormacaoId.equals=" + areaFormacaoId);

        // Get all the responsavelAreaFormacaoList where areaFormacao equals to (areaFormacaoId + 1)
        defaultResponsavelAreaFormacaoShouldNotBeFound("areaFormacaoId.equals=" + (areaFormacaoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResponsavelAreaFormacaoShouldBeFound(String filter) throws Exception {
        restResponsavelAreaFormacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelAreaFormacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restResponsavelAreaFormacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResponsavelAreaFormacaoShouldNotBeFound(String filter) throws Exception {
        restResponsavelAreaFormacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResponsavelAreaFormacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResponsavelAreaFormacao() throws Exception {
        // Get the responsavelAreaFormacao
        restResponsavelAreaFormacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResponsavelAreaFormacao() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        int databaseSizeBeforeUpdate = responsavelAreaFormacaoRepository.findAll().size();

        // Update the responsavelAreaFormacao
        ResponsavelAreaFormacao updatedResponsavelAreaFormacao = responsavelAreaFormacaoRepository
            .findById(responsavelAreaFormacao.getId())
            .get();
        // Disconnect from session so that the updates on updatedResponsavelAreaFormacao are not directly saved in db
        em.detach(updatedResponsavelAreaFormacao);
        updatedResponsavelAreaFormacao.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = responsavelAreaFormacaoMapper.toDto(updatedResponsavelAreaFormacao);

        restResponsavelAreaFormacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelAreaFormacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelAreaFormacaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelAreaFormacao in the database
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelAreaFormacao testResponsavelAreaFormacao = responsavelAreaFormacaoList.get(responsavelAreaFormacaoList.size() - 1);
        assertThat(testResponsavelAreaFormacao.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelAreaFormacao.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelAreaFormacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelAreaFormacao.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingResponsavelAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = responsavelAreaFormacaoRepository.findAll().size();
        responsavelAreaFormacao.setId(count.incrementAndGet());

        // Create the ResponsavelAreaFormacao
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelAreaFormacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelAreaFormacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelAreaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelAreaFormacao in the database
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponsavelAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = responsavelAreaFormacaoRepository.findAll().size();
        responsavelAreaFormacao.setId(count.incrementAndGet());

        // Create the ResponsavelAreaFormacao
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelAreaFormacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelAreaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelAreaFormacao in the database
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponsavelAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = responsavelAreaFormacaoRepository.findAll().size();
        responsavelAreaFormacao.setId(count.incrementAndGet());

        // Create the ResponsavelAreaFormacao
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelAreaFormacaoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelAreaFormacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelAreaFormacao in the database
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponsavelAreaFormacaoWithPatch() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        int databaseSizeBeforeUpdate = responsavelAreaFormacaoRepository.findAll().size();

        // Update the responsavelAreaFormacao using partial update
        ResponsavelAreaFormacao partialUpdatedResponsavelAreaFormacao = new ResponsavelAreaFormacao();
        partialUpdatedResponsavelAreaFormacao.setId(responsavelAreaFormacao.getId());

        partialUpdatedResponsavelAreaFormacao.ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO);

        restResponsavelAreaFormacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelAreaFormacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelAreaFormacao))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelAreaFormacao in the database
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelAreaFormacao testResponsavelAreaFormacao = responsavelAreaFormacaoList.get(responsavelAreaFormacaoList.size() - 1);
        assertThat(testResponsavelAreaFormacao.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testResponsavelAreaFormacao.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelAreaFormacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelAreaFormacao.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateResponsavelAreaFormacaoWithPatch() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        int databaseSizeBeforeUpdate = responsavelAreaFormacaoRepository.findAll().size();

        // Update the responsavelAreaFormacao using partial update
        ResponsavelAreaFormacao partialUpdatedResponsavelAreaFormacao = new ResponsavelAreaFormacao();
        partialUpdatedResponsavelAreaFormacao.setId(responsavelAreaFormacao.getId());

        partialUpdatedResponsavelAreaFormacao.de(UPDATED_DE).ate(UPDATED_ATE).descricao(UPDATED_DESCRICAO).timestamp(UPDATED_TIMESTAMP);

        restResponsavelAreaFormacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelAreaFormacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelAreaFormacao))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelAreaFormacao in the database
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelAreaFormacao testResponsavelAreaFormacao = responsavelAreaFormacaoList.get(responsavelAreaFormacaoList.size() - 1);
        assertThat(testResponsavelAreaFormacao.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testResponsavelAreaFormacao.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testResponsavelAreaFormacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testResponsavelAreaFormacao.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingResponsavelAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = responsavelAreaFormacaoRepository.findAll().size();
        responsavelAreaFormacao.setId(count.incrementAndGet());

        // Create the ResponsavelAreaFormacao
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelAreaFormacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responsavelAreaFormacaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelAreaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelAreaFormacao in the database
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponsavelAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = responsavelAreaFormacaoRepository.findAll().size();
        responsavelAreaFormacao.setId(count.incrementAndGet());

        // Create the ResponsavelAreaFormacao
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelAreaFormacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelAreaFormacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelAreaFormacao in the database
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponsavelAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = responsavelAreaFormacaoRepository.findAll().size();
        responsavelAreaFormacao.setId(count.incrementAndGet());

        // Create the ResponsavelAreaFormacao
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO = responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelAreaFormacaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelAreaFormacaoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelAreaFormacao in the database
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponsavelAreaFormacao() throws Exception {
        // Initialize the database
        responsavelAreaFormacaoRepository.saveAndFlush(responsavelAreaFormacao);

        int databaseSizeBeforeDelete = responsavelAreaFormacaoRepository.findAll().size();

        // Delete the responsavelAreaFormacao
        restResponsavelAreaFormacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, responsavelAreaFormacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResponsavelAreaFormacao> responsavelAreaFormacaoList = responsavelAreaFormacaoRepository.findAll();
        assertThat(responsavelAreaFormacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
