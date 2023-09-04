package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.PlanoAulaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.PlanoAula}.
 */
public interface PlanoAulaService {
    /**
     * Save a planoAula.
     *
     * @param planoAulaDTO the entity to save.
     * @return the persisted entity.
     */
    PlanoAulaDTO save(PlanoAulaDTO planoAulaDTO);

    /**
     * Updates a planoAula.
     *
     * @param planoAulaDTO the entity to update.
     * @return the persisted entity.
     */
    PlanoAulaDTO update(PlanoAulaDTO planoAulaDTO);

    /**
     * Partially updates a planoAula.
     *
     * @param planoAulaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlanoAulaDTO> partialUpdate(PlanoAulaDTO planoAulaDTO);

    /**
     * Get all the planoAulas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoAulaDTO> findAll(Pageable pageable);

    /**
     * Get all the planoAulas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoAulaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" planoAula.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanoAulaDTO> findOne(Long id);

    /**
     * Delete the "id" planoAula.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
