package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.DetalhePlanoAula;
import com.ravunana.longonkelo.service.dto.DetalhePlanoAulaDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.DetalhePlanoAula}.
 */
public interface DetalhePlanoAulaService {
    /**
     * Save a detalhePlanoAula.
     *
     * @param detalhePlanoAulaDTO the entity to save.
     * @return the persisted entity.
     */
    DetalhePlanoAulaDTO save(DetalhePlanoAulaDTO detalhePlanoAulaDTO);

    /**
     * Updates a detalhePlanoAula.
     *
     * @param detalhePlanoAulaDTO the entity to update.
     * @return the persisted entity.
     */
    DetalhePlanoAulaDTO update(DetalhePlanoAulaDTO detalhePlanoAulaDTO);

    /**
     * Partially updates a detalhePlanoAula.
     *
     * @param detalhePlanoAulaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DetalhePlanoAulaDTO> partialUpdate(DetalhePlanoAulaDTO detalhePlanoAulaDTO);

    /**
     * Get all the detalhePlanoAulas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetalhePlanoAulaDTO> findAll(Pageable pageable);

    /**
     * Get all the detalhePlanoAulas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetalhePlanoAulaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" detalhePlanoAula.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DetalhePlanoAulaDTO> findOne(Long id);

    /**
     * Delete the "id" detalhePlanoAula.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<DetalhePlanoAula> getDetalhePlanoAula(Long planoAulaID);
}
