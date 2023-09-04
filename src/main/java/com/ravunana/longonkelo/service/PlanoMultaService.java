package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.PlanoMultaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.PlanoMulta}.
 */
public interface PlanoMultaService {
    /**
     * Save a planoMulta.
     *
     * @param planoMultaDTO the entity to save.
     * @return the persisted entity.
     */
    PlanoMultaDTO save(PlanoMultaDTO planoMultaDTO);

    /**
     * Updates a planoMulta.
     *
     * @param planoMultaDTO the entity to update.
     * @return the persisted entity.
     */
    PlanoMultaDTO update(PlanoMultaDTO planoMultaDTO);

    /**
     * Partially updates a planoMulta.
     *
     * @param planoMultaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlanoMultaDTO> partialUpdate(PlanoMultaDTO planoMultaDTO);

    /**
     * Get all the planoMultas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoMultaDTO> findAll(Pageable pageable);

    /**
     * Get all the planoMultas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoMultaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" planoMulta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanoMultaDTO> findOne(Long id);

    /**
     * Delete the "id" planoMulta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
