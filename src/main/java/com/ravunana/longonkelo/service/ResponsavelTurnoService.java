package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ResponsavelTurnoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.ResponsavelTurno}.
 */
public interface ResponsavelTurnoService {
    /**
     * Save a responsavelTurno.
     *
     * @param responsavelTurnoDTO the entity to save.
     * @return the persisted entity.
     */
    ResponsavelTurnoDTO save(ResponsavelTurnoDTO responsavelTurnoDTO);

    /**
     * Updates a responsavelTurno.
     *
     * @param responsavelTurnoDTO the entity to update.
     * @return the persisted entity.
     */
    ResponsavelTurnoDTO update(ResponsavelTurnoDTO responsavelTurnoDTO);

    /**
     * Partially updates a responsavelTurno.
     *
     * @param responsavelTurnoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResponsavelTurnoDTO> partialUpdate(ResponsavelTurnoDTO responsavelTurnoDTO);

    /**
     * Get all the responsavelTurnos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelTurnoDTO> findAll(Pageable pageable);

    /**
     * Get all the responsavelTurnos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelTurnoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" responsavelTurno.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponsavelTurnoDTO> findOne(Long id);

    /**
     * Delete the "id" responsavelTurno.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
