package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.EnderecoDiscente;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.enumeration.TipoEndereco;
import com.ravunana.longonkelo.repository.EnderecoDiscenteRepository;
import com.ravunana.longonkelo.service.EnderecoDiscenteService;
import com.ravunana.longonkelo.service.criteria.EnderecoDiscenteCriteria;
import com.ravunana.longonkelo.service.dto.EnderecoDiscenteDTO;
import com.ravunana.longonkelo.service.mapper.EnderecoDiscenteMapper;
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
 * Integration tests for the {@link EnderecoDiscenteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EnderecoDiscenteResourceIT {

    private static final TipoEndereco DEFAULT_TIPO = TipoEndereco.RESIDECIAL;
    private static final TipoEndereco UPDATED_TIPO = TipoEndereco.FACTURACAO;

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_RUA = "AAAAAAAAAA";
    private static final String UPDATED_RUA = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CASA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CASA = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_POSTAL = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/endereco-discentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnderecoDiscenteRepository enderecoDiscenteRepository;

    @Mock
    private EnderecoDiscenteRepository enderecoDiscenteRepositoryMock;

    @Autowired
    private EnderecoDiscenteMapper enderecoDiscenteMapper;

    @Mock
    private EnderecoDiscenteService enderecoDiscenteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnderecoDiscenteMockMvc;

    private EnderecoDiscente enderecoDiscente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoDiscente createEntity(EntityManager em) {
        EnderecoDiscente enderecoDiscente = new EnderecoDiscente()
            .tipo(DEFAULT_TIPO)
            .bairro(DEFAULT_BAIRRO)
            .rua(DEFAULT_RUA)
            .numeroCasa(DEFAULT_NUMERO_CASA)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        enderecoDiscente.setDiscente(discente);
        return enderecoDiscente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoDiscente createUpdatedEntity(EntityManager em) {
        EnderecoDiscente enderecoDiscente = new EnderecoDiscente()
            .tipo(UPDATED_TIPO)
            .bairro(UPDATED_BAIRRO)
            .rua(UPDATED_RUA)
            .numeroCasa(UPDATED_NUMERO_CASA)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        // Add required entity
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            discente = DiscenteResourceIT.createUpdatedEntity(em);
            em.persist(discente);
            em.flush();
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        enderecoDiscente.setDiscente(discente);
        return enderecoDiscente;
    }

    @BeforeEach
    public void initTest() {
        enderecoDiscente = createEntity(em);
    }

    @Test
    @Transactional
    void createEnderecoDiscente() throws Exception {
        int databaseSizeBeforeCreate = enderecoDiscenteRepository.findAll().size();
        // Create the EnderecoDiscente
        EnderecoDiscenteDTO enderecoDiscenteDTO = enderecoDiscenteMapper.toDto(enderecoDiscente);
        restEnderecoDiscenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoDiscenteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EnderecoDiscente in the database
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeCreate + 1);
        EnderecoDiscente testEnderecoDiscente = enderecoDiscenteList.get(enderecoDiscenteList.size() - 1);
        assertThat(testEnderecoDiscente.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testEnderecoDiscente.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEnderecoDiscente.getRua()).isEqualTo(DEFAULT_RUA);
        assertThat(testEnderecoDiscente.getNumeroCasa()).isEqualTo(DEFAULT_NUMERO_CASA);
        assertThat(testEnderecoDiscente.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testEnderecoDiscente.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testEnderecoDiscente.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    void createEnderecoDiscenteWithExistingId() throws Exception {
        // Create the EnderecoDiscente with an existing ID
        enderecoDiscente.setId(1L);
        EnderecoDiscenteDTO enderecoDiscenteDTO = enderecoDiscenteMapper.toDto(enderecoDiscente);

        int databaseSizeBeforeCreate = enderecoDiscenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnderecoDiscenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoDiscente in the database
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = enderecoDiscenteRepository.findAll().size();
        // set the field null
        enderecoDiscente.setTipo(null);

        // Create the EnderecoDiscente, which fails.
        EnderecoDiscenteDTO enderecoDiscenteDTO = enderecoDiscenteMapper.toDto(enderecoDiscente);

        restEnderecoDiscenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentes() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList
        restEnderecoDiscenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enderecoDiscente.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA)))
            .andExpect(jsonPath("$.[*].numeroCasa").value(hasItem(DEFAULT_NUMERO_CASA)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEnderecoDiscentesWithEagerRelationshipsIsEnabled() throws Exception {
        when(enderecoDiscenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEnderecoDiscenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(enderecoDiscenteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEnderecoDiscentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(enderecoDiscenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEnderecoDiscenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(enderecoDiscenteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEnderecoDiscente() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get the enderecoDiscente
        restEnderecoDiscenteMockMvc
            .perform(get(ENTITY_API_URL_ID, enderecoDiscente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enderecoDiscente.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.rua").value(DEFAULT_RUA))
            .andExpect(jsonPath("$.numeroCasa").value(DEFAULT_NUMERO_CASA))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }

    @Test
    @Transactional
    void getEnderecoDiscentesByIdFiltering() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        Long id = enderecoDiscente.getId();

        defaultEnderecoDiscenteShouldBeFound("id.equals=" + id);
        defaultEnderecoDiscenteShouldNotBeFound("id.notEquals=" + id);

        defaultEnderecoDiscenteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnderecoDiscenteShouldNotBeFound("id.greaterThan=" + id);

        defaultEnderecoDiscenteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnderecoDiscenteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where tipo equals to DEFAULT_TIPO
        defaultEnderecoDiscenteShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the enderecoDiscenteList where tipo equals to UPDATED_TIPO
        defaultEnderecoDiscenteShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultEnderecoDiscenteShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the enderecoDiscenteList where tipo equals to UPDATED_TIPO
        defaultEnderecoDiscenteShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where tipo is not null
        defaultEnderecoDiscenteShouldBeFound("tipo.specified=true");

        // Get all the enderecoDiscenteList where tipo is null
        defaultEnderecoDiscenteShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByBairroIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where bairro equals to DEFAULT_BAIRRO
        defaultEnderecoDiscenteShouldBeFound("bairro.equals=" + DEFAULT_BAIRRO);

        // Get all the enderecoDiscenteList where bairro equals to UPDATED_BAIRRO
        defaultEnderecoDiscenteShouldNotBeFound("bairro.equals=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByBairroIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where bairro in DEFAULT_BAIRRO or UPDATED_BAIRRO
        defaultEnderecoDiscenteShouldBeFound("bairro.in=" + DEFAULT_BAIRRO + "," + UPDATED_BAIRRO);

        // Get all the enderecoDiscenteList where bairro equals to UPDATED_BAIRRO
        defaultEnderecoDiscenteShouldNotBeFound("bairro.in=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByBairroIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where bairro is not null
        defaultEnderecoDiscenteShouldBeFound("bairro.specified=true");

        // Get all the enderecoDiscenteList where bairro is null
        defaultEnderecoDiscenteShouldNotBeFound("bairro.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByBairroContainsSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where bairro contains DEFAULT_BAIRRO
        defaultEnderecoDiscenteShouldBeFound("bairro.contains=" + DEFAULT_BAIRRO);

        // Get all the enderecoDiscenteList where bairro contains UPDATED_BAIRRO
        defaultEnderecoDiscenteShouldNotBeFound("bairro.contains=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByBairroNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where bairro does not contain DEFAULT_BAIRRO
        defaultEnderecoDiscenteShouldNotBeFound("bairro.doesNotContain=" + DEFAULT_BAIRRO);

        // Get all the enderecoDiscenteList where bairro does not contain UPDATED_BAIRRO
        defaultEnderecoDiscenteShouldBeFound("bairro.doesNotContain=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByRuaIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where rua equals to DEFAULT_RUA
        defaultEnderecoDiscenteShouldBeFound("rua.equals=" + DEFAULT_RUA);

        // Get all the enderecoDiscenteList where rua equals to UPDATED_RUA
        defaultEnderecoDiscenteShouldNotBeFound("rua.equals=" + UPDATED_RUA);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByRuaIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where rua in DEFAULT_RUA or UPDATED_RUA
        defaultEnderecoDiscenteShouldBeFound("rua.in=" + DEFAULT_RUA + "," + UPDATED_RUA);

        // Get all the enderecoDiscenteList where rua equals to UPDATED_RUA
        defaultEnderecoDiscenteShouldNotBeFound("rua.in=" + UPDATED_RUA);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByRuaIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where rua is not null
        defaultEnderecoDiscenteShouldBeFound("rua.specified=true");

        // Get all the enderecoDiscenteList where rua is null
        defaultEnderecoDiscenteShouldNotBeFound("rua.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByRuaContainsSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where rua contains DEFAULT_RUA
        defaultEnderecoDiscenteShouldBeFound("rua.contains=" + DEFAULT_RUA);

        // Get all the enderecoDiscenteList where rua contains UPDATED_RUA
        defaultEnderecoDiscenteShouldNotBeFound("rua.contains=" + UPDATED_RUA);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByRuaNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where rua does not contain DEFAULT_RUA
        defaultEnderecoDiscenteShouldNotBeFound("rua.doesNotContain=" + DEFAULT_RUA);

        // Get all the enderecoDiscenteList where rua does not contain UPDATED_RUA
        defaultEnderecoDiscenteShouldBeFound("rua.doesNotContain=" + UPDATED_RUA);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByNumeroCasaIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where numeroCasa equals to DEFAULT_NUMERO_CASA
        defaultEnderecoDiscenteShouldBeFound("numeroCasa.equals=" + DEFAULT_NUMERO_CASA);

        // Get all the enderecoDiscenteList where numeroCasa equals to UPDATED_NUMERO_CASA
        defaultEnderecoDiscenteShouldNotBeFound("numeroCasa.equals=" + UPDATED_NUMERO_CASA);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByNumeroCasaIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where numeroCasa in DEFAULT_NUMERO_CASA or UPDATED_NUMERO_CASA
        defaultEnderecoDiscenteShouldBeFound("numeroCasa.in=" + DEFAULT_NUMERO_CASA + "," + UPDATED_NUMERO_CASA);

        // Get all the enderecoDiscenteList where numeroCasa equals to UPDATED_NUMERO_CASA
        defaultEnderecoDiscenteShouldNotBeFound("numeroCasa.in=" + UPDATED_NUMERO_CASA);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByNumeroCasaIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where numeroCasa is not null
        defaultEnderecoDiscenteShouldBeFound("numeroCasa.specified=true");

        // Get all the enderecoDiscenteList where numeroCasa is null
        defaultEnderecoDiscenteShouldNotBeFound("numeroCasa.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByNumeroCasaContainsSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where numeroCasa contains DEFAULT_NUMERO_CASA
        defaultEnderecoDiscenteShouldBeFound("numeroCasa.contains=" + DEFAULT_NUMERO_CASA);

        // Get all the enderecoDiscenteList where numeroCasa contains UPDATED_NUMERO_CASA
        defaultEnderecoDiscenteShouldNotBeFound("numeroCasa.contains=" + UPDATED_NUMERO_CASA);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByNumeroCasaNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where numeroCasa does not contain DEFAULT_NUMERO_CASA
        defaultEnderecoDiscenteShouldNotBeFound("numeroCasa.doesNotContain=" + DEFAULT_NUMERO_CASA);

        // Get all the enderecoDiscenteList where numeroCasa does not contain UPDATED_NUMERO_CASA
        defaultEnderecoDiscenteShouldBeFound("numeroCasa.doesNotContain=" + UPDATED_NUMERO_CASA);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByCodigoPostalIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where codigoPostal equals to DEFAULT_CODIGO_POSTAL
        defaultEnderecoDiscenteShouldBeFound("codigoPostal.equals=" + DEFAULT_CODIGO_POSTAL);

        // Get all the enderecoDiscenteList where codigoPostal equals to UPDATED_CODIGO_POSTAL
        defaultEnderecoDiscenteShouldNotBeFound("codigoPostal.equals=" + UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByCodigoPostalIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where codigoPostal in DEFAULT_CODIGO_POSTAL or UPDATED_CODIGO_POSTAL
        defaultEnderecoDiscenteShouldBeFound("codigoPostal.in=" + DEFAULT_CODIGO_POSTAL + "," + UPDATED_CODIGO_POSTAL);

        // Get all the enderecoDiscenteList where codigoPostal equals to UPDATED_CODIGO_POSTAL
        defaultEnderecoDiscenteShouldNotBeFound("codigoPostal.in=" + UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByCodigoPostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where codigoPostal is not null
        defaultEnderecoDiscenteShouldBeFound("codigoPostal.specified=true");

        // Get all the enderecoDiscenteList where codigoPostal is null
        defaultEnderecoDiscenteShouldNotBeFound("codigoPostal.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByCodigoPostalContainsSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where codigoPostal contains DEFAULT_CODIGO_POSTAL
        defaultEnderecoDiscenteShouldBeFound("codigoPostal.contains=" + DEFAULT_CODIGO_POSTAL);

        // Get all the enderecoDiscenteList where codigoPostal contains UPDATED_CODIGO_POSTAL
        defaultEnderecoDiscenteShouldNotBeFound("codigoPostal.contains=" + UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByCodigoPostalNotContainsSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where codigoPostal does not contain DEFAULT_CODIGO_POSTAL
        defaultEnderecoDiscenteShouldNotBeFound("codigoPostal.doesNotContain=" + DEFAULT_CODIGO_POSTAL);

        // Get all the enderecoDiscenteList where codigoPostal does not contain UPDATED_CODIGO_POSTAL
        defaultEnderecoDiscenteShouldBeFound("codigoPostal.doesNotContain=" + UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where latitude equals to DEFAULT_LATITUDE
        defaultEnderecoDiscenteShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the enderecoDiscenteList where latitude equals to UPDATED_LATITUDE
        defaultEnderecoDiscenteShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultEnderecoDiscenteShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the enderecoDiscenteList where latitude equals to UPDATED_LATITUDE
        defaultEnderecoDiscenteShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where latitude is not null
        defaultEnderecoDiscenteShouldBeFound("latitude.specified=true");

        // Get all the enderecoDiscenteList where latitude is null
        defaultEnderecoDiscenteShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultEnderecoDiscenteShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the enderecoDiscenteList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultEnderecoDiscenteShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultEnderecoDiscenteShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the enderecoDiscenteList where latitude is less than or equal to SMALLER_LATITUDE
        defaultEnderecoDiscenteShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where latitude is less than DEFAULT_LATITUDE
        defaultEnderecoDiscenteShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the enderecoDiscenteList where latitude is less than UPDATED_LATITUDE
        defaultEnderecoDiscenteShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where latitude is greater than DEFAULT_LATITUDE
        defaultEnderecoDiscenteShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the enderecoDiscenteList where latitude is greater than SMALLER_LATITUDE
        defaultEnderecoDiscenteShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where longitude equals to DEFAULT_LONGITUDE
        defaultEnderecoDiscenteShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the enderecoDiscenteList where longitude equals to UPDATED_LONGITUDE
        defaultEnderecoDiscenteShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultEnderecoDiscenteShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the enderecoDiscenteList where longitude equals to UPDATED_LONGITUDE
        defaultEnderecoDiscenteShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where longitude is not null
        defaultEnderecoDiscenteShouldBeFound("longitude.specified=true");

        // Get all the enderecoDiscenteList where longitude is null
        defaultEnderecoDiscenteShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultEnderecoDiscenteShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the enderecoDiscenteList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultEnderecoDiscenteShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultEnderecoDiscenteShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the enderecoDiscenteList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultEnderecoDiscenteShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where longitude is less than DEFAULT_LONGITUDE
        defaultEnderecoDiscenteShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the enderecoDiscenteList where longitude is less than UPDATED_LONGITUDE
        defaultEnderecoDiscenteShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        // Get all the enderecoDiscenteList where longitude is greater than DEFAULT_LONGITUDE
        defaultEnderecoDiscenteShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the enderecoDiscenteList where longitude is greater than SMALLER_LONGITUDE
        defaultEnderecoDiscenteShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByPaisIsEqualToSomething() throws Exception {
        LookupItem pais;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);
            pais = LookupItemResourceIT.createEntity(em);
        } else {
            pais = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(pais);
        em.flush();
        enderecoDiscente.setPais(pais);
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);
        Long paisId = pais.getId();

        // Get all the enderecoDiscenteList where pais equals to paisId
        defaultEnderecoDiscenteShouldBeFound("paisId.equals=" + paisId);

        // Get all the enderecoDiscenteList where pais equals to (paisId + 1)
        defaultEnderecoDiscenteShouldNotBeFound("paisId.equals=" + (paisId + 1));
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByProvinciaIsEqualToSomething() throws Exception {
        LookupItem provincia;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);
            provincia = LookupItemResourceIT.createEntity(em);
        } else {
            provincia = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(provincia);
        em.flush();
        enderecoDiscente.setProvincia(provincia);
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);
        Long provinciaId = provincia.getId();

        // Get all the enderecoDiscenteList where provincia equals to provinciaId
        defaultEnderecoDiscenteShouldBeFound("provinciaId.equals=" + provinciaId);

        // Get all the enderecoDiscenteList where provincia equals to (provinciaId + 1)
        defaultEnderecoDiscenteShouldNotBeFound("provinciaId.equals=" + (provinciaId + 1));
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByMunicipioIsEqualToSomething() throws Exception {
        LookupItem municipio;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);
            municipio = LookupItemResourceIT.createEntity(em);
        } else {
            municipio = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(municipio);
        em.flush();
        enderecoDiscente.setMunicipio(municipio);
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);
        Long municipioId = municipio.getId();

        // Get all the enderecoDiscenteList where municipio equals to municipioId
        defaultEnderecoDiscenteShouldBeFound("municipioId.equals=" + municipioId);

        // Get all the enderecoDiscenteList where municipio equals to (municipioId + 1)
        defaultEnderecoDiscenteShouldNotBeFound("municipioId.equals=" + (municipioId + 1));
    }

    @Test
    @Transactional
    void getAllEnderecoDiscentesByDiscenteIsEqualToSomething() throws Exception {
        Discente discente;
        if (TestUtil.findAll(em, Discente.class).isEmpty()) {
            enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);
            discente = DiscenteResourceIT.createEntity(em);
        } else {
            discente = TestUtil.findAll(em, Discente.class).get(0);
        }
        em.persist(discente);
        em.flush();
        enderecoDiscente.setDiscente(discente);
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);
        Long discenteId = discente.getId();

        // Get all the enderecoDiscenteList where discente equals to discenteId
        defaultEnderecoDiscenteShouldBeFound("discenteId.equals=" + discenteId);

        // Get all the enderecoDiscenteList where discente equals to (discenteId + 1)
        defaultEnderecoDiscenteShouldNotBeFound("discenteId.equals=" + (discenteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnderecoDiscenteShouldBeFound(String filter) throws Exception {
        restEnderecoDiscenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enderecoDiscente.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA)))
            .andExpect(jsonPath("$.[*].numeroCasa").value(hasItem(DEFAULT_NUMERO_CASA)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));

        // Check, that the count call also returns 1
        restEnderecoDiscenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnderecoDiscenteShouldNotBeFound(String filter) throws Exception {
        restEnderecoDiscenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnderecoDiscenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEnderecoDiscente() throws Exception {
        // Get the enderecoDiscente
        restEnderecoDiscenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnderecoDiscente() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        int databaseSizeBeforeUpdate = enderecoDiscenteRepository.findAll().size();

        // Update the enderecoDiscente
        EnderecoDiscente updatedEnderecoDiscente = enderecoDiscenteRepository.findById(enderecoDiscente.getId()).get();
        // Disconnect from session so that the updates on updatedEnderecoDiscente are not directly saved in db
        em.detach(updatedEnderecoDiscente);
        updatedEnderecoDiscente
            .tipo(UPDATED_TIPO)
            .bairro(UPDATED_BAIRRO)
            .rua(UPDATED_RUA)
            .numeroCasa(UPDATED_NUMERO_CASA)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        EnderecoDiscenteDTO enderecoDiscenteDTO = enderecoDiscenteMapper.toDto(updatedEnderecoDiscente);

        restEnderecoDiscenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enderecoDiscenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enderecoDiscenteDTO))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoDiscente in the database
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeUpdate);
        EnderecoDiscente testEnderecoDiscente = enderecoDiscenteList.get(enderecoDiscenteList.size() - 1);
        assertThat(testEnderecoDiscente.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testEnderecoDiscente.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEnderecoDiscente.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testEnderecoDiscente.getNumeroCasa()).isEqualTo(UPDATED_NUMERO_CASA);
        assertThat(testEnderecoDiscente.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testEnderecoDiscente.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEnderecoDiscente.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void putNonExistingEnderecoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = enderecoDiscenteRepository.findAll().size();
        enderecoDiscente.setId(count.incrementAndGet());

        // Create the EnderecoDiscente
        EnderecoDiscenteDTO enderecoDiscenteDTO = enderecoDiscenteMapper.toDto(enderecoDiscente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoDiscenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enderecoDiscenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enderecoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoDiscente in the database
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnderecoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = enderecoDiscenteRepository.findAll().size();
        enderecoDiscente.setId(count.incrementAndGet());

        // Create the EnderecoDiscente
        EnderecoDiscenteDTO enderecoDiscenteDTO = enderecoDiscenteMapper.toDto(enderecoDiscente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoDiscenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enderecoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoDiscente in the database
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnderecoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = enderecoDiscenteRepository.findAll().size();
        enderecoDiscente.setId(count.incrementAndGet());

        // Create the EnderecoDiscente
        EnderecoDiscenteDTO enderecoDiscenteDTO = enderecoDiscenteMapper.toDto(enderecoDiscente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoDiscenteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoDiscenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoDiscente in the database
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnderecoDiscenteWithPatch() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        int databaseSizeBeforeUpdate = enderecoDiscenteRepository.findAll().size();

        // Update the enderecoDiscente using partial update
        EnderecoDiscente partialUpdatedEnderecoDiscente = new EnderecoDiscente();
        partialUpdatedEnderecoDiscente.setId(enderecoDiscente.getId());

        partialUpdatedEnderecoDiscente
            .rua(UPDATED_RUA)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restEnderecoDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoDiscente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnderecoDiscente))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoDiscente in the database
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeUpdate);
        EnderecoDiscente testEnderecoDiscente = enderecoDiscenteList.get(enderecoDiscenteList.size() - 1);
        assertThat(testEnderecoDiscente.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testEnderecoDiscente.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEnderecoDiscente.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testEnderecoDiscente.getNumeroCasa()).isEqualTo(DEFAULT_NUMERO_CASA);
        assertThat(testEnderecoDiscente.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testEnderecoDiscente.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEnderecoDiscente.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void fullUpdateEnderecoDiscenteWithPatch() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        int databaseSizeBeforeUpdate = enderecoDiscenteRepository.findAll().size();

        // Update the enderecoDiscente using partial update
        EnderecoDiscente partialUpdatedEnderecoDiscente = new EnderecoDiscente();
        partialUpdatedEnderecoDiscente.setId(enderecoDiscente.getId());

        partialUpdatedEnderecoDiscente
            .tipo(UPDATED_TIPO)
            .bairro(UPDATED_BAIRRO)
            .rua(UPDATED_RUA)
            .numeroCasa(UPDATED_NUMERO_CASA)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restEnderecoDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoDiscente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnderecoDiscente))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoDiscente in the database
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeUpdate);
        EnderecoDiscente testEnderecoDiscente = enderecoDiscenteList.get(enderecoDiscenteList.size() - 1);
        assertThat(testEnderecoDiscente.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testEnderecoDiscente.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEnderecoDiscente.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testEnderecoDiscente.getNumeroCasa()).isEqualTo(UPDATED_NUMERO_CASA);
        assertThat(testEnderecoDiscente.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testEnderecoDiscente.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEnderecoDiscente.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void patchNonExistingEnderecoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = enderecoDiscenteRepository.findAll().size();
        enderecoDiscente.setId(count.incrementAndGet());

        // Create the EnderecoDiscente
        EnderecoDiscenteDTO enderecoDiscenteDTO = enderecoDiscenteMapper.toDto(enderecoDiscente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enderecoDiscenteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enderecoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoDiscente in the database
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnderecoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = enderecoDiscenteRepository.findAll().size();
        enderecoDiscente.setId(count.incrementAndGet());

        // Create the EnderecoDiscente
        EnderecoDiscenteDTO enderecoDiscenteDTO = enderecoDiscenteMapper.toDto(enderecoDiscente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enderecoDiscenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoDiscente in the database
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnderecoDiscente() throws Exception {
        int databaseSizeBeforeUpdate = enderecoDiscenteRepository.findAll().size();
        enderecoDiscente.setId(count.incrementAndGet());

        // Create the EnderecoDiscente
        EnderecoDiscenteDTO enderecoDiscenteDTO = enderecoDiscenteMapper.toDto(enderecoDiscente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoDiscenteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enderecoDiscenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoDiscente in the database
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnderecoDiscente() throws Exception {
        // Initialize the database
        enderecoDiscenteRepository.saveAndFlush(enderecoDiscente);

        int databaseSizeBeforeDelete = enderecoDiscenteRepository.findAll().size();

        // Delete the enderecoDiscente
        restEnderecoDiscenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, enderecoDiscente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnderecoDiscente> enderecoDiscenteList = enderecoDiscenteRepository.findAll();
        assertThat(enderecoDiscenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
