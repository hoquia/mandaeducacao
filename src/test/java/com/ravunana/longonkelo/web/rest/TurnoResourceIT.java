package com.ravunana.longonkelo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.longonkelo.IntegrationTest;
import com.ravunana.longonkelo.domain.Turno;
import com.ravunana.longonkelo.repository.TurnoRepository;
import com.ravunana.longonkelo.service.dto.TurnoDTO;
import com.ravunana.longonkelo.service.mapper.TurnoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TurnoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TurnoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/turnos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private TurnoMapper turnoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTurnoMockMvc;

    private Turno turno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turno createEntity(EntityManager em) {
        Turno turno = new Turno().codigo(DEFAULT_CODIGO).nome(DEFAULT_NOME);
        return turno;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turno createUpdatedEntity(EntityManager em) {
        Turno turno = new Turno().codigo(UPDATED_CODIGO).nome(UPDATED_NOME);
        return turno;
    }

    @BeforeEach
    public void initTest() {
        turno = createEntity(em);
    }

    @Test
    @Transactional
    void createTurno() throws Exception {
        int databaseSizeBeforeCreate = turnoRepository.findAll().size();
        // Create the Turno
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);
        restTurnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isCreated());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeCreate + 1);
        Turno testTurno = turnoList.get(turnoList.size() - 1);
        assertThat(testTurno.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTurno.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createTurnoWithExistingId() throws Exception {
        // Create the Turno with an existing ID
        turno.setId(1L);
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        int databaseSizeBeforeCreate = turnoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTurnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnoRepository.findAll().size();
        // set the field null
        turno.setCodigo(null);

        // Create the Turno, which fails.
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        restTurnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isBadRequest());

        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnoRepository.findAll().size();
        // set the field null
        turno.setNome(null);

        // Create the Turno, which fails.
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        restTurnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isBadRequest());

        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTurnos() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        // Get all the turnoList
        restTurnoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turno.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getTurno() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        // Get the turno
        restTurnoMockMvc
            .perform(get(ENTITY_API_URL_ID, turno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(turno.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingTurno() throws Exception {
        // Get the turno
        restTurnoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTurno() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();

        // Update the turno
        Turno updatedTurno = turnoRepository.findById(turno.getId()).get();
        // Disconnect from session so that the updates on updatedTurno are not directly saved in db
        em.detach(updatedTurno);
        updatedTurno.codigo(UPDATED_CODIGO).nome(UPDATED_NOME);
        TurnoDTO turnoDTO = turnoMapper.toDto(updatedTurno);

        restTurnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, turnoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(turnoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
        Turno testTurno = turnoList.get(turnoList.size() - 1);
        assertThat(testTurno.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTurno.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingTurno() throws Exception {
        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();
        turno.setId(count.incrementAndGet());

        // Create the Turno
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, turnoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(turnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTurno() throws Exception {
        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();
        turno.setId(count.incrementAndGet());

        // Create the Turno
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(turnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTurno() throws Exception {
        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();
        turno.setId(count.incrementAndGet());

        // Create the Turno
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurnoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTurnoWithPatch() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();

        // Update the turno using partial update
        Turno partialUpdatedTurno = new Turno();
        partialUpdatedTurno.setId(turno.getId());

        restTurnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTurno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTurno))
            )
            .andExpect(status().isOk());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
        Turno testTurno = turnoList.get(turnoList.size() - 1);
        assertThat(testTurno.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTurno.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void fullUpdateTurnoWithPatch() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();

        // Update the turno using partial update
        Turno partialUpdatedTurno = new Turno();
        partialUpdatedTurno.setId(turno.getId());

        partialUpdatedTurno.codigo(UPDATED_CODIGO).nome(UPDATED_NOME);

        restTurnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTurno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTurno))
            )
            .andExpect(status().isOk());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
        Turno testTurno = turnoList.get(turnoList.size() - 1);
        assertThat(testTurno.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTurno.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingTurno() throws Exception {
        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();
        turno.setId(count.incrementAndGet());

        // Create the Turno
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, turnoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(turnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTurno() throws Exception {
        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();
        turno.setId(count.incrementAndGet());

        // Create the Turno
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(turnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTurno() throws Exception {
        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();
        turno.setId(count.incrementAndGet());

        // Create the Turno
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurnoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTurno() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        int databaseSizeBeforeDelete = turnoRepository.findAll().size();

        // Delete the turno
        restTurnoMockMvc
            .perform(delete(ENTITY_API_URL_ID, turno.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
