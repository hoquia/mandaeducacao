package com.ravunana.longonkelo.web.rest;

import static com.ravunana.longonkelo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.TransferenciaTurma;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.repository.TransferenciaTurmaRepository;
import com.ravunana.longonkelo.service.TransferenciaTurmaService;
import com.ravunana.longonkelo.service.criteria.TransferenciaTurmaCriteria;
import com.ravunana.longonkelo.service.dto.TransferenciaTurmaDTO;
import com.ravunana.longonkelo.service.mapper.TransferenciaTurmaMapper;
import java.time.Instant;
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
 * Integration tests for the {@link TransferenciaTurmaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TransferenciaTurmaResourceIT {

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/transferencia-turmas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransferenciaTurmaRepository transferenciaTurmaRepository;

    @Mock
    private TransferenciaTurmaRepository transferenciaTurmaRepositoryMock;

    @Autowired
    private TransferenciaTurmaMapper transferenciaTurmaMapper;

    @Mock
    private TransferenciaTurmaService transferenciaTurmaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferenciaTurmaMockMvc;

    private TransferenciaTurma transferenciaTurma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferenciaTurma createEntity(EntityManager em) {
        TransferenciaTurma transferenciaTurma = new TransferenciaTurma().timestamp(DEFAULT_TIMESTAMP);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        transferenciaTurma.setDe(turma);
        // Add required entity
        transferenciaTurma.setPara(turma);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        transferenciaTurma.setMatricula(matricula);
        return transferenciaTurma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferenciaTurma createUpdatedEntity(EntityManager em) {
        TransferenciaTurma transferenciaTurma = new TransferenciaTurma().timestamp(UPDATED_TIMESTAMP);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createUpdatedEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        transferenciaTurma.setDe(turma);
        // Add required entity
        transferenciaTurma.setPara(turma);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createUpdatedEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        transferenciaTurma.setMatricula(matricula);
        return transferenciaTurma;
    }

    @BeforeEach
    public void initTest() {
        transferenciaTurma = createEntity(em);
    }

    @Test
    @Transactional
    void createTransferenciaTurma() throws Exception {
        int databaseSizeBeforeCreate = transferenciaTurmaRepository.findAll().size();
        // Create the TransferenciaTurma
        TransferenciaTurmaDTO transferenciaTurmaDTO = transferenciaTurmaMapper.toDto(transferenciaTurma);
        restTransferenciaTurmaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaTurmaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransferenciaTurma in the database
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeCreate + 1);
        TransferenciaTurma testTransferenciaTurma = transferenciaTurmaList.get(transferenciaTurmaList.size() - 1);
        assertThat(testTransferenciaTurma.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createTransferenciaTurmaWithExistingId() throws Exception {
        // Create the TransferenciaTurma with an existing ID
        transferenciaTurma.setId(1L);
        TransferenciaTurmaDTO transferenciaTurmaDTO = transferenciaTurmaMapper.toDto(transferenciaTurma);

        int databaseSizeBeforeCreate = transferenciaTurmaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferenciaTurmaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaTurma in the database
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmas() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        // Get all the transferenciaTurmaList
        restTransferenciaTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferenciaTurma.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTransferenciaTurmasWithEagerRelationshipsIsEnabled() throws Exception {
        when(transferenciaTurmaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTransferenciaTurmaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(transferenciaTurmaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTransferenciaTurmasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(transferenciaTurmaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTransferenciaTurmaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(transferenciaTurmaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTransferenciaTurma() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        // Get the transferenciaTurma
        restTransferenciaTurmaMockMvc
            .perform(get(ENTITY_API_URL_ID, transferenciaTurma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transferenciaTurma.getId().intValue()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getTransferenciaTurmasByIdFiltering() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        Long id = transferenciaTurma.getId();

        defaultTransferenciaTurmaShouldBeFound("id.equals=" + id);
        defaultTransferenciaTurmaShouldNotBeFound("id.notEquals=" + id);

        defaultTransferenciaTurmaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransferenciaTurmaShouldNotBeFound("id.greaterThan=" + id);

        defaultTransferenciaTurmaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransferenciaTurmaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        // Get all the transferenciaTurmaList where timestamp equals to DEFAULT_TIMESTAMP
        defaultTransferenciaTurmaShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the transferenciaTurmaList where timestamp equals to UPDATED_TIMESTAMP
        defaultTransferenciaTurmaShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        // Get all the transferenciaTurmaList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultTransferenciaTurmaShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the transferenciaTurmaList where timestamp equals to UPDATED_TIMESTAMP
        defaultTransferenciaTurmaShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        // Get all the transferenciaTurmaList where timestamp is not null
        defaultTransferenciaTurmaShouldBeFound("timestamp.specified=true");

        // Get all the transferenciaTurmaList where timestamp is null
        defaultTransferenciaTurmaShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        // Get all the transferenciaTurmaList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultTransferenciaTurmaShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the transferenciaTurmaList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultTransferenciaTurmaShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        // Get all the transferenciaTurmaList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultTransferenciaTurmaShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the transferenciaTurmaList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultTransferenciaTurmaShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        // Get all the transferenciaTurmaList where timestamp is less than DEFAULT_TIMESTAMP
        defaultTransferenciaTurmaShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the transferenciaTurmaList where timestamp is less than UPDATED_TIMESTAMP
        defaultTransferenciaTurmaShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        // Get all the transferenciaTurmaList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultTransferenciaTurmaShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the transferenciaTurmaList where timestamp is greater than SMALLER_TIMESTAMP
        defaultTransferenciaTurmaShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByDeIsEqualToSomething() throws Exception {
        Turma de;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);
            de = TurmaResourceIT.createEntity(em);
        } else {
            de = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(de);
        em.flush();
        transferenciaTurma.setDe(de);
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);
        Long deId = de.getId();

        // Get all the transferenciaTurmaList where de equals to deId
        defaultTransferenciaTurmaShouldBeFound("deId.equals=" + deId);

        // Get all the transferenciaTurmaList where de equals to (deId + 1)
        defaultTransferenciaTurmaShouldNotBeFound("deId.equals=" + (deId + 1));
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByParaIsEqualToSomething() throws Exception {
        Turma para;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);
            para = TurmaResourceIT.createEntity(em);
        } else {
            para = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(para);
        em.flush();
        transferenciaTurma.setPara(para);
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);
        Long paraId = para.getId();

        // Get all the transferenciaTurmaList where para equals to paraId
        defaultTransferenciaTurmaShouldBeFound("paraId.equals=" + paraId);

        // Get all the transferenciaTurmaList where para equals to (paraId + 1)
        defaultTransferenciaTurmaShouldNotBeFound("paraId.equals=" + (paraId + 1));
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByUtilizadorIsEqualToSomething() throws Exception {
        User utilizador;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);
            utilizador = UserResourceIT.createEntity(em);
        } else {
            utilizador = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(utilizador);
        em.flush();
        transferenciaTurma.setUtilizador(utilizador);
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);
        Long utilizadorId = utilizador.getId();

        // Get all the transferenciaTurmaList where utilizador equals to utilizadorId
        defaultTransferenciaTurmaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the transferenciaTurmaList where utilizador equals to (utilizadorId + 1)
        defaultTransferenciaTurmaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByMotivoTransferenciaIsEqualToSomething() throws Exception {
        LookupItem motivoTransferencia;
        if (TestUtil.findAll(em, LookupItem.class).isEmpty()) {
            transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);
            motivoTransferencia = LookupItemResourceIT.createEntity(em);
        } else {
            motivoTransferencia = TestUtil.findAll(em, LookupItem.class).get(0);
        }
        em.persist(motivoTransferencia);
        em.flush();
        transferenciaTurma.setMotivoTransferencia(motivoTransferencia);
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);
        Long motivoTransferenciaId = motivoTransferencia.getId();

        // Get all the transferenciaTurmaList where motivoTransferencia equals to motivoTransferenciaId
        defaultTransferenciaTurmaShouldBeFound("motivoTransferenciaId.equals=" + motivoTransferenciaId);

        // Get all the transferenciaTurmaList where motivoTransferencia equals to (motivoTransferenciaId + 1)
        defaultTransferenciaTurmaShouldNotBeFound("motivoTransferenciaId.equals=" + (motivoTransferenciaId + 1));
    }

    @Test
    @Transactional
    void getAllTransferenciaTurmasByMatriculaIsEqualToSomething() throws Exception {
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);
            matricula = MatriculaResourceIT.createEntity(em);
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        em.persist(matricula);
        em.flush();
        transferenciaTurma.setMatricula(matricula);
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);
        Long matriculaId = matricula.getId();

        // Get all the transferenciaTurmaList where matricula equals to matriculaId
        defaultTransferenciaTurmaShouldBeFound("matriculaId.equals=" + matriculaId);

        // Get all the transferenciaTurmaList where matricula equals to (matriculaId + 1)
        defaultTransferenciaTurmaShouldNotBeFound("matriculaId.equals=" + (matriculaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransferenciaTurmaShouldBeFound(String filter) throws Exception {
        restTransferenciaTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferenciaTurma.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));

        // Check, that the count call also returns 1
        restTransferenciaTurmaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransferenciaTurmaShouldNotBeFound(String filter) throws Exception {
        restTransferenciaTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransferenciaTurmaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransferenciaTurma() throws Exception {
        // Get the transferenciaTurma
        restTransferenciaTurmaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransferenciaTurma() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        int databaseSizeBeforeUpdate = transferenciaTurmaRepository.findAll().size();

        // Update the transferenciaTurma
        TransferenciaTurma updatedTransferenciaTurma = transferenciaTurmaRepository.findById(transferenciaTurma.getId()).get();
        // Disconnect from session so that the updates on updatedTransferenciaTurma are not directly saved in db
        em.detach(updatedTransferenciaTurma);
        updatedTransferenciaTurma.timestamp(UPDATED_TIMESTAMP);
        TransferenciaTurmaDTO transferenciaTurmaDTO = transferenciaTurmaMapper.toDto(updatedTransferenciaTurma);

        restTransferenciaTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferenciaTurmaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaTurmaDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransferenciaTurma in the database
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeUpdate);
        TransferenciaTurma testTransferenciaTurma = transferenciaTurmaList.get(transferenciaTurmaList.size() - 1);
        assertThat(testTransferenciaTurma.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingTransferenciaTurma() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaTurmaRepository.findAll().size();
        transferenciaTurma.setId(count.incrementAndGet());

        // Create the TransferenciaTurma
        TransferenciaTurmaDTO transferenciaTurmaDTO = transferenciaTurmaMapper.toDto(transferenciaTurma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferenciaTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferenciaTurmaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaTurma in the database
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransferenciaTurma() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaTurmaRepository.findAll().size();
        transferenciaTurma.setId(count.incrementAndGet());

        // Create the TransferenciaTurma
        TransferenciaTurmaDTO transferenciaTurmaDTO = transferenciaTurmaMapper.toDto(transferenciaTurma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferenciaTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaTurma in the database
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransferenciaTurma() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaTurmaRepository.findAll().size();
        transferenciaTurma.setId(count.incrementAndGet());

        // Create the TransferenciaTurma
        TransferenciaTurmaDTO transferenciaTurmaDTO = transferenciaTurmaMapper.toDto(transferenciaTurma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferenciaTurmaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaTurmaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferenciaTurma in the database
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransferenciaTurmaWithPatch() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        int databaseSizeBeforeUpdate = transferenciaTurmaRepository.findAll().size();

        // Update the transferenciaTurma using partial update
        TransferenciaTurma partialUpdatedTransferenciaTurma = new TransferenciaTurma();
        partialUpdatedTransferenciaTurma.setId(transferenciaTurma.getId());

        partialUpdatedTransferenciaTurma.timestamp(UPDATED_TIMESTAMP);

        restTransferenciaTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferenciaTurma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferenciaTurma))
            )
            .andExpect(status().isOk());

        // Validate the TransferenciaTurma in the database
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeUpdate);
        TransferenciaTurma testTransferenciaTurma = transferenciaTurmaList.get(transferenciaTurmaList.size() - 1);
        assertThat(testTransferenciaTurma.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateTransferenciaTurmaWithPatch() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        int databaseSizeBeforeUpdate = transferenciaTurmaRepository.findAll().size();

        // Update the transferenciaTurma using partial update
        TransferenciaTurma partialUpdatedTransferenciaTurma = new TransferenciaTurma();
        partialUpdatedTransferenciaTurma.setId(transferenciaTurma.getId());

        partialUpdatedTransferenciaTurma.timestamp(UPDATED_TIMESTAMP);

        restTransferenciaTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferenciaTurma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferenciaTurma))
            )
            .andExpect(status().isOk());

        // Validate the TransferenciaTurma in the database
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeUpdate);
        TransferenciaTurma testTransferenciaTurma = transferenciaTurmaList.get(transferenciaTurmaList.size() - 1);
        assertThat(testTransferenciaTurma.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingTransferenciaTurma() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaTurmaRepository.findAll().size();
        transferenciaTurma.setId(count.incrementAndGet());

        // Create the TransferenciaTurma
        TransferenciaTurmaDTO transferenciaTurmaDTO = transferenciaTurmaMapper.toDto(transferenciaTurma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferenciaTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transferenciaTurmaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaTurma in the database
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransferenciaTurma() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaTurmaRepository.findAll().size();
        transferenciaTurma.setId(count.incrementAndGet());

        // Create the TransferenciaTurma
        TransferenciaTurmaDTO transferenciaTurmaDTO = transferenciaTurmaMapper.toDto(transferenciaTurma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferenciaTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaTurmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaTurma in the database
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransferenciaTurma() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaTurmaRepository.findAll().size();
        transferenciaTurma.setId(count.incrementAndGet());

        // Create the TransferenciaTurma
        TransferenciaTurmaDTO transferenciaTurmaDTO = transferenciaTurmaMapper.toDto(transferenciaTurma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferenciaTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferenciaTurmaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferenciaTurma in the database
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransferenciaTurma() throws Exception {
        // Initialize the database
        transferenciaTurmaRepository.saveAndFlush(transferenciaTurma);

        int databaseSizeBeforeDelete = transferenciaTurmaRepository.findAll().size();

        // Delete the transferenciaTurma
        restTransferenciaTurmaMockMvc
            .perform(delete(ENTITY_API_URL_ID, transferenciaTurma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransferenciaTurma> transferenciaTurmaList = transferenciaTurmaRepository.findAll();
        assertThat(transferenciaTurmaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
