package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.TurnoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Turno}.
 */
public interface TurnoService {
    /**
     * Save a turno.
     *
     * @param turnoDTO the entity to save.
     * @return the persisted entity.
     */
    TurnoDTO save(TurnoDTO turnoDTO);

    /**
     * Updates a turno.
     *
     * @param turnoDTO the entity to update.
     * @return the persisted entity.
     */
    TurnoDTO update(TurnoDTO turnoDTO);

    /**
     * Partially updates a turno.
     *
     * @param turnoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TurnoDTO> partialUpdate(TurnoDTO turnoDTO);

    /**
     * Get all the turnos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TurnoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" turno.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TurnoDTO> findOne(Long id);

    /**
     * Delete the "id" turno.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
