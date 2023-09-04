package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.PeriodoHorarioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.PeriodoHorario}.
 */
public interface PeriodoHorarioService {
    /**
     * Save a periodoHorario.
     *
     * @param periodoHorarioDTO the entity to save.
     * @return the persisted entity.
     */
    PeriodoHorarioDTO save(PeriodoHorarioDTO periodoHorarioDTO);

    /**
     * Updates a periodoHorario.
     *
     * @param periodoHorarioDTO the entity to update.
     * @return the persisted entity.
     */
    PeriodoHorarioDTO update(PeriodoHorarioDTO periodoHorarioDTO);

    /**
     * Partially updates a periodoHorario.
     *
     * @param periodoHorarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PeriodoHorarioDTO> partialUpdate(PeriodoHorarioDTO periodoHorarioDTO);

    /**
     * Get all the periodoHorarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PeriodoHorarioDTO> findAll(Pageable pageable);

    /**
     * Get all the periodoHorarios with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PeriodoHorarioDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" periodoHorario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PeriodoHorarioDTO> findOne(Long id);

    /**
     * Delete the "id" periodoHorario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
