package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.CategoriaEmolumento;
import com.ravunana.longonkelo.domain.Emolumento;
import com.ravunana.longonkelo.domain.PlanoDesconto;
import com.ravunana.longonkelo.domain.PlanoMulta;
import com.ravunana.longonkelo.repository.CategoriaEmolumentoRepository;
import com.ravunana.longonkelo.service.CategoriaEmolumentoService;
import com.ravunana.longonkelo.service.criteria.CategoriaEmolumentoCriteria;
import com.ravunana.longonkelo.service.dto.CategoriaEmolumentoDTO;
import com.ravunana.longonkelo.service.mapper.CategoriaEmolumentoMapper;
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
 * Integration tests for the {@link CategoriaEmolumentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CategoriaEmolumentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SERVICO = false;
    private static final Boolean UPDATED_IS_SERVICO = true;

    private static final String DEFAULT_COR = "AAAAAAAAAA";
    private static final String UPDATED_COR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ISENTO_MULTA = false;
    private static final Boolean UPDATED_IS_ISENTO_MULTA = true;

    private static final Boolean DEFAULT_IS_ISENTO_JURO = false;
    private static final Boolean UPDATED_IS_ISENTO_JURO = true;

    private static final String ENTITY_API_URL = "/api/categoria-emolumentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoriaEmolumentoRepository categoriaEmolumentoRepository;

    @Mock
    private CategoriaEmolumentoRepository categoriaEmolumentoRepositoryMock;

    @Autowired
    private CategoriaEmolumentoMapper categoriaEmolumentoMapper;

    @Mock
    private CategoriaEmolumentoService categoriaEmolumentoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriaEmolumentoMockMvc;

    private CategoriaEmolumento categoriaEmolumento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaEmolumento createEntity(EntityManager em) {
        CategoriaEmolumento categoriaEmolumento = new CategoriaEmolumento()
            .nome(DEFAULT_NOME)
            .isServico(DEFAULT_IS_SERVICO)
            .cor(DEFAULT_COR)
            .descricao(DEFAULT_DESCRICAO)
            .isIsentoMulta(DEFAULT_IS_ISENTO_MULTA)
            .isIsentoJuro(DEFAULT_IS_ISENTO_JURO);
        return categoriaEmolumento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaEmolumento createUpdatedEntity(EntityManager em) {
        CategoriaEmolumento categoriaEmolumento = new CategoriaEmolumento()
            .nome(UPDATED_NOME)
            .isServico(UPDATED_IS_SERVICO)
            .cor(UPDATED_COR)
            .descricao(UPDATED_DESCRICAO)
            .isIsentoMulta(UPDATED_IS_ISENTO_MULTA)
            .isIsentoJuro(UPDATED_IS_ISENTO_JURO);
        return categoriaEmolumento;
    }

    @BeforeEach
    public void initTest() {
        categoriaEmolumento = createEntity(em);
    }

    @Test
    @Transactional
    void createCategoriaEmolumento() throws Exception {
        int databaseSizeBeforeCreate = categoriaEmolumentoRepository.findAll().size();
        // Create the CategoriaEmolumento
        CategoriaEmolumentoDTO categoriaEmolumentoDTO = categoriaEmolumentoMapper.toDto(categoriaEmolumento);
        restCategoriaEmolumentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaEmolumentoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategoriaEmolumento in the database
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaEmolumento testCategoriaEmolumento = categoriaEmolumentoList.get(categoriaEmolumentoList.size() - 1);
        assertThat(testCategoriaEmolumento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCategoriaEmolumento.getIsServico()).isEqualTo(DEFAULT_IS_SERVICO);
        assertThat(testCategoriaEmolumento.getCor()).isEqualTo(DEFAULT_COR);
        assertThat(testCategoriaEmolumento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCategoriaEmolumento.getIsIsentoMulta()).isEqualTo(DEFAULT_IS_ISENTO_MULTA);
        assertThat(testCategoriaEmolumento.getIsIsentoJuro()).isEqualTo(DEFAULT_IS_ISENTO_JURO);
    }

    @Test
    @Transactional
    void createCategoriaEmolumentoWithExistingId() throws Exception {
        // Create the CategoriaEmolumento with an existing ID
        categoriaEmolumento.setId(1L);
        CategoriaEmolumentoDTO categoriaEmolumentoDTO = categoriaEmolumentoMapper.toDto(categoriaEmolumento);

        int databaseSizeBeforeCreate = categoriaEmolumentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaEmolumentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaEmolumento in the database
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaEmolumentoRepository.findAll().size();
        // set the field null
        categoriaEmolumento.setNome(null);

        // Create the CategoriaEmolumento, which fails.
        CategoriaEmolumentoDTO categoriaEmolumentoDTO = categoriaEmolumentoMapper.toDto(categoriaEmolumento);

        restCategoriaEmolumentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentos() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList
        restCategoriaEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaEmolumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].isServico").value(hasItem(DEFAULT_IS_SERVICO.booleanValue())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].isIsentoMulta").value(hasItem(DEFAULT_IS_ISENTO_MULTA.booleanValue())))
            .andExpect(jsonPath("$.[*].isIsentoJuro").value(hasItem(DEFAULT_IS_ISENTO_JURO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCategoriaEmolumentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(categoriaEmolumentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCategoriaEmolumentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(categoriaEmolumentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCategoriaEmolumentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(categoriaEmolumentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCategoriaEmolumentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(categoriaEmolumentoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCategoriaEmolumento() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get the categoriaEmolumento
        restCategoriaEmolumentoMockMvc
            .perform(get(ENTITY_API_URL_ID, categoriaEmolumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaEmolumento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.isServico").value(DEFAULT_IS_SERVICO.booleanValue()))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.isIsentoMulta").value(DEFAULT_IS_ISENTO_MULTA.booleanValue()))
            .andExpect(jsonPath("$.isIsentoJuro").value(DEFAULT_IS_ISENTO_JURO.booleanValue()));
    }

    @Test
    @Transactional
    void getCategoriaEmolumentosByIdFiltering() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        Long id = categoriaEmolumento.getId();

        defaultCategoriaEmolumentoShouldBeFound("id.equals=" + id);
        defaultCategoriaEmolumentoShouldNotBeFound("id.notEquals=" + id);

        defaultCategoriaEmolumentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoriaEmolumentoShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoriaEmolumentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoriaEmolumentoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where nome equals to DEFAULT_NOME
        defaultCategoriaEmolumentoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the categoriaEmolumentoList where nome equals to UPDATED_NOME
        defaultCategoriaEmolumentoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultCategoriaEmolumentoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the categoriaEmolumentoList where nome equals to UPDATED_NOME
        defaultCategoriaEmolumentoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where nome is not null
        defaultCategoriaEmolumentoShouldBeFound("nome.specified=true");

        // Get all the categoriaEmolumentoList where nome is null
        defaultCategoriaEmolumentoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByNomeContainsSomething() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where nome contains DEFAULT_NOME
        defaultCategoriaEmolumentoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the categoriaEmolumentoList where nome contains UPDATED_NOME
        defaultCategoriaEmolumentoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where nome does not contain DEFAULT_NOME
        defaultCategoriaEmolumentoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the categoriaEmolumentoList where nome does not contain UPDATED_NOME
        defaultCategoriaEmolumentoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByIsServicoIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where isServico equals to DEFAULT_IS_SERVICO
        defaultCategoriaEmolumentoShouldBeFound("isServico.equals=" + DEFAULT_IS_SERVICO);

        // Get all the categoriaEmolumentoList where isServico equals to UPDATED_IS_SERVICO
        defaultCategoriaEmolumentoShouldNotBeFound("isServico.equals=" + UPDATED_IS_SERVICO);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByIsServicoIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where isServico in DEFAULT_IS_SERVICO or UPDATED_IS_SERVICO
        defaultCategoriaEmolumentoShouldBeFound("isServico.in=" + DEFAULT_IS_SERVICO + "," + UPDATED_IS_SERVICO);

        // Get all the categoriaEmolumentoList where isServico equals to UPDATED_IS_SERVICO
        defaultCategoriaEmolumentoShouldNotBeFound("isServico.in=" + UPDATED_IS_SERVICO);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByIsServicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where isServico is not null
        defaultCategoriaEmolumentoShouldBeFound("isServico.specified=true");

        // Get all the categoriaEmolumentoList where isServico is null
        defaultCategoriaEmolumentoShouldNotBeFound("isServico.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByCorIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where cor equals to DEFAULT_COR
        defaultCategoriaEmolumentoShouldBeFound("cor.equals=" + DEFAULT_COR);

        // Get all the categoriaEmolumentoList where cor equals to UPDATED_COR
        defaultCategoriaEmolumentoShouldNotBeFound("cor.equals=" + UPDATED_COR);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByCorIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where cor in DEFAULT_COR or UPDATED_COR
        defaultCategoriaEmolumentoShouldBeFound("cor.in=" + DEFAULT_COR + "," + UPDATED_COR);

        // Get all the categoriaEmolumentoList where cor equals to UPDATED_COR
        defaultCategoriaEmolumentoShouldNotBeFound("cor.in=" + UPDATED_COR);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByCorIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where cor is not null
        defaultCategoriaEmolumentoShouldBeFound("cor.specified=true");

        // Get all the categoriaEmolumentoList where cor is null
        defaultCategoriaEmolumentoShouldNotBeFound("cor.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByCorContainsSomething() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where cor contains DEFAULT_COR
        defaultCategoriaEmolumentoShouldBeFound("cor.contains=" + DEFAULT_COR);

        // Get all the categoriaEmolumentoList where cor contains UPDATED_COR
        defaultCategoriaEmolumentoShouldNotBeFound("cor.contains=" + UPDATED_COR);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByCorNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where cor does not contain DEFAULT_COR
        defaultCategoriaEmolumentoShouldNotBeFound("cor.doesNotContain=" + DEFAULT_COR);

        // Get all the categoriaEmolumentoList where cor does not contain UPDATED_COR
        defaultCategoriaEmolumentoShouldBeFound("cor.doesNotContain=" + UPDATED_COR);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByIsIsentoMultaIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where isIsentoMulta equals to DEFAULT_IS_ISENTO_MULTA
        defaultCategoriaEmolumentoShouldBeFound("isIsentoMulta.equals=" + DEFAULT_IS_ISENTO_MULTA);

        // Get all the categoriaEmolumentoList where isIsentoMulta equals to UPDATED_IS_ISENTO_MULTA
        defaultCategoriaEmolumentoShouldNotBeFound("isIsentoMulta.equals=" + UPDATED_IS_ISENTO_MULTA);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByIsIsentoMultaIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where isIsentoMulta in DEFAULT_IS_ISENTO_MULTA or UPDATED_IS_ISENTO_MULTA
        defaultCategoriaEmolumentoShouldBeFound("isIsentoMulta.in=" + DEFAULT_IS_ISENTO_MULTA + "," + UPDATED_IS_ISENTO_MULTA);

        // Get all the categoriaEmolumentoList where isIsentoMulta equals to UPDATED_IS_ISENTO_MULTA
        defaultCategoriaEmolumentoShouldNotBeFound("isIsentoMulta.in=" + UPDATED_IS_ISENTO_MULTA);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByIsIsentoMultaIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where isIsentoMulta is not null
        defaultCategoriaEmolumentoShouldBeFound("isIsentoMulta.specified=true");

        // Get all the categoriaEmolumentoList where isIsentoMulta is null
        defaultCategoriaEmolumentoShouldNotBeFound("isIsentoMulta.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByIsIsentoJuroIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where isIsentoJuro equals to DEFAULT_IS_ISENTO_JURO
        defaultCategoriaEmolumentoShouldBeFound("isIsentoJuro.equals=" + DEFAULT_IS_ISENTO_JURO);

        // Get all the categoriaEmolumentoList where isIsentoJuro equals to UPDATED_IS_ISENTO_JURO
        defaultCategoriaEmolumentoShouldNotBeFound("isIsentoJuro.equals=" + UPDATED_IS_ISENTO_JURO);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByIsIsentoJuroIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where isIsentoJuro in DEFAULT_IS_ISENTO_JURO or UPDATED_IS_ISENTO_JURO
        defaultCategoriaEmolumentoShouldBeFound("isIsentoJuro.in=" + DEFAULT_IS_ISENTO_JURO + "," + UPDATED_IS_ISENTO_JURO);

        // Get all the categoriaEmolumentoList where isIsentoJuro equals to UPDATED_IS_ISENTO_JURO
        defaultCategoriaEmolumentoShouldNotBeFound("isIsentoJuro.in=" + UPDATED_IS_ISENTO_JURO);
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByIsIsentoJuroIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        // Get all the categoriaEmolumentoList where isIsentoJuro is not null
        defaultCategoriaEmolumentoShouldBeFound("isIsentoJuro.specified=true");

        // Get all the categoriaEmolumentoList where isIsentoJuro is null
        defaultCategoriaEmolumentoShouldNotBeFound("isIsentoJuro.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByEmolumentoIsEqualToSomething() throws Exception {
        Emolumento emolumento;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);
            emolumento = EmolumentoResourceIT.createEntity(em);
        } else {
            emolumento = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        em.persist(emolumento);
        em.flush();
        categoriaEmolumento.addEmolumento(emolumento);
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);
        Long emolumentoId = emolumento.getId();

        // Get all the categoriaEmolumentoList where emolumento equals to emolumentoId
        defaultCategoriaEmolumentoShouldBeFound("emolumentoId.equals=" + emolumentoId);

        // Get all the categoriaEmolumentoList where emolumento equals to (emolumentoId + 1)
        defaultCategoriaEmolumentoShouldNotBeFound("emolumentoId.equals=" + (emolumentoId + 1));
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByPlanoMultaIsEqualToSomething() throws Exception {
        PlanoMulta planoMulta;
        if (TestUtil.findAll(em, PlanoMulta.class).isEmpty()) {
            categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);
            planoMulta = PlanoMultaResourceIT.createEntity(em);
        } else {
            planoMulta = TestUtil.findAll(em, PlanoMulta.class).get(0);
        }
        em.persist(planoMulta);
        em.flush();
        categoriaEmolumento.setPlanoMulta(planoMulta);
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);
        Long planoMultaId = planoMulta.getId();

        // Get all the categoriaEmolumentoList where planoMulta equals to planoMultaId
        defaultCategoriaEmolumentoShouldBeFound("planoMultaId.equals=" + planoMultaId);

        // Get all the categoriaEmolumentoList where planoMulta equals to (planoMultaId + 1)
        defaultCategoriaEmolumentoShouldNotBeFound("planoMultaId.equals=" + (planoMultaId + 1));
    }

    @Test
    @Transactional
    void getAllCategoriaEmolumentosByPlanosDescontoIsEqualToSomething() throws Exception {
        PlanoDesconto planosDesconto;
        if (TestUtil.findAll(em, PlanoDesconto.class).isEmpty()) {
            categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);
            planosDesconto = PlanoDescontoResourceIT.createEntity(em);
        } else {
            planosDesconto = TestUtil.findAll(em, PlanoDesconto.class).get(0);
        }
        em.persist(planosDesconto);
        em.flush();
        categoriaEmolumento.addPlanosDesconto(planosDesconto);
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);
        Long planosDescontoId = planosDesconto.getId();

        // Get all the categoriaEmolumentoList where planosDesconto equals to planosDescontoId
        defaultCategoriaEmolumentoShouldBeFound("planosDescontoId.equals=" + planosDescontoId);

        // Get all the categoriaEmolumentoList where planosDesconto equals to (planosDescontoId + 1)
        defaultCategoriaEmolumentoShouldNotBeFound("planosDescontoId.equals=" + (planosDescontoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriaEmolumentoShouldBeFound(String filter) throws Exception {
        restCategoriaEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaEmolumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].isServico").value(hasItem(DEFAULT_IS_SERVICO.booleanValue())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].isIsentoMulta").value(hasItem(DEFAULT_IS_ISENTO_MULTA.booleanValue())))
            .andExpect(jsonPath("$.[*].isIsentoJuro").value(hasItem(DEFAULT_IS_ISENTO_JURO.booleanValue())));

        // Check, that the count call also returns 1
        restCategoriaEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriaEmolumentoShouldNotBeFound(String filter) throws Exception {
        restCategoriaEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriaEmolumentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCategoriaEmolumento() throws Exception {
        // Get the categoriaEmolumento
        restCategoriaEmolumentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategoriaEmolumento() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        int databaseSizeBeforeUpdate = categoriaEmolumentoRepository.findAll().size();

        // Update the categoriaEmolumento
        CategoriaEmolumento updatedCategoriaEmolumento = categoriaEmolumentoRepository.findById(categoriaEmolumento.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaEmolumento are not directly saved in db
        em.detach(updatedCategoriaEmolumento);
        updatedCategoriaEmolumento
            .nome(UPDATED_NOME)
            .isServico(UPDATED_IS_SERVICO)
            .cor(UPDATED_COR)
            .descricao(UPDATED_DESCRICAO)
            .isIsentoMulta(UPDATED_IS_ISENTO_MULTA)
            .isIsentoJuro(UPDATED_IS_ISENTO_JURO);
        CategoriaEmolumentoDTO categoriaEmolumentoDTO = categoriaEmolumentoMapper.toDto(updatedCategoriaEmolumento);

        restCategoriaEmolumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaEmolumentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaEmolumentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaEmolumento in the database
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaEmolumento testCategoriaEmolumento = categoriaEmolumentoList.get(categoriaEmolumentoList.size() - 1);
        assertThat(testCategoriaEmolumento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCategoriaEmolumento.getIsServico()).isEqualTo(UPDATED_IS_SERVICO);
        assertThat(testCategoriaEmolumento.getCor()).isEqualTo(UPDATED_COR);
        assertThat(testCategoriaEmolumento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCategoriaEmolumento.getIsIsentoMulta()).isEqualTo(UPDATED_IS_ISENTO_MULTA);
        assertThat(testCategoriaEmolumento.getIsIsentoJuro()).isEqualTo(UPDATED_IS_ISENTO_JURO);
    }

    @Test
    @Transactional
    void putNonExistingCategoriaEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = categoriaEmolumentoRepository.findAll().size();
        categoriaEmolumento.setId(count.incrementAndGet());

        // Create the CategoriaEmolumento
        CategoriaEmolumentoDTO categoriaEmolumentoDTO = categoriaEmolumentoMapper.toDto(categoriaEmolumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaEmolumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaEmolumentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaEmolumento in the database
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategoriaEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = categoriaEmolumentoRepository.findAll().size();
        categoriaEmolumento.setId(count.incrementAndGet());

        // Create the CategoriaEmolumento
        CategoriaEmolumentoDTO categoriaEmolumentoDTO = categoriaEmolumentoMapper.toDto(categoriaEmolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaEmolumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaEmolumento in the database
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategoriaEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = categoriaEmolumentoRepository.findAll().size();
        categoriaEmolumento.setId(count.incrementAndGet());

        // Create the CategoriaEmolumento
        CategoriaEmolumentoDTO categoriaEmolumentoDTO = categoriaEmolumentoMapper.toDto(categoriaEmolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaEmolumentoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaEmolumentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaEmolumento in the database
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriaEmolumentoWithPatch() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        int databaseSizeBeforeUpdate = categoriaEmolumentoRepository.findAll().size();

        // Update the categoriaEmolumento using partial update
        CategoriaEmolumento partialUpdatedCategoriaEmolumento = new CategoriaEmolumento();
        partialUpdatedCategoriaEmolumento.setId(categoriaEmolumento.getId());

        partialUpdatedCategoriaEmolumento.nome(UPDATED_NOME).isIsentoMulta(UPDATED_IS_ISENTO_MULTA).isIsentoJuro(UPDATED_IS_ISENTO_JURO);

        restCategoriaEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaEmolumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaEmolumento))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaEmolumento in the database
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaEmolumento testCategoriaEmolumento = categoriaEmolumentoList.get(categoriaEmolumentoList.size() - 1);
        assertThat(testCategoriaEmolumento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCategoriaEmolumento.getIsServico()).isEqualTo(DEFAULT_IS_SERVICO);
        assertThat(testCategoriaEmolumento.getCor()).isEqualTo(DEFAULT_COR);
        assertThat(testCategoriaEmolumento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCategoriaEmolumento.getIsIsentoMulta()).isEqualTo(UPDATED_IS_ISENTO_MULTA);
        assertThat(testCategoriaEmolumento.getIsIsentoJuro()).isEqualTo(UPDATED_IS_ISENTO_JURO);
    }

    @Test
    @Transactional
    void fullUpdateCategoriaEmolumentoWithPatch() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        int databaseSizeBeforeUpdate = categoriaEmolumentoRepository.findAll().size();

        // Update the categoriaEmolumento using partial update
        CategoriaEmolumento partialUpdatedCategoriaEmolumento = new CategoriaEmolumento();
        partialUpdatedCategoriaEmolumento.setId(categoriaEmolumento.getId());

        partialUpdatedCategoriaEmolumento
            .nome(UPDATED_NOME)
            .isServico(UPDATED_IS_SERVICO)
            .cor(UPDATED_COR)
            .descricao(UPDATED_DESCRICAO)
            .isIsentoMulta(UPDATED_IS_ISENTO_MULTA)
            .isIsentoJuro(UPDATED_IS_ISENTO_JURO);

        restCategoriaEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaEmolumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaEmolumento))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaEmolumento in the database
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaEmolumento testCategoriaEmolumento = categoriaEmolumentoList.get(categoriaEmolumentoList.size() - 1);
        assertThat(testCategoriaEmolumento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCategoriaEmolumento.getIsServico()).isEqualTo(UPDATED_IS_SERVICO);
        assertThat(testCategoriaEmolumento.getCor()).isEqualTo(UPDATED_COR);
        assertThat(testCategoriaEmolumento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCategoriaEmolumento.getIsIsentoMulta()).isEqualTo(UPDATED_IS_ISENTO_MULTA);
        assertThat(testCategoriaEmolumento.getIsIsentoJuro()).isEqualTo(UPDATED_IS_ISENTO_JURO);
    }

    @Test
    @Transactional
    void patchNonExistingCategoriaEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = categoriaEmolumentoRepository.findAll().size();
        categoriaEmolumento.setId(count.incrementAndGet());

        // Create the CategoriaEmolumento
        CategoriaEmolumentoDTO categoriaEmolumentoDTO = categoriaEmolumentoMapper.toDto(categoriaEmolumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoriaEmolumentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaEmolumento in the database
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategoriaEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = categoriaEmolumentoRepository.findAll().size();
        categoriaEmolumento.setId(count.incrementAndGet());

        // Create the CategoriaEmolumento
        CategoriaEmolumentoDTO categoriaEmolumentoDTO = categoriaEmolumentoMapper.toDto(categoriaEmolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaEmolumentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaEmolumento in the database
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategoriaEmolumento() throws Exception {
        int databaseSizeBeforeUpdate = categoriaEmolumentoRepository.findAll().size();
        categoriaEmolumento.setId(count.incrementAndGet());

        // Create the CategoriaEmolumento
        CategoriaEmolumentoDTO categoriaEmolumentoDTO = categoriaEmolumentoMapper.toDto(categoriaEmolumento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaEmolumentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaEmolumentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaEmolumento in the database
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategoriaEmolumento() throws Exception {
        // Initialize the database
        categoriaEmolumentoRepository.saveAndFlush(categoriaEmolumento);

        int databaseSizeBeforeDelete = categoriaEmolumentoRepository.findAll().size();

        // Delete the categoriaEmolumento
        restCategoriaEmolumentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoriaEmolumento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaEmolumento> categoriaEmolumentoList = categoriaEmolumentoRepository.findAll();
        assertThat(categoriaEmolumentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
