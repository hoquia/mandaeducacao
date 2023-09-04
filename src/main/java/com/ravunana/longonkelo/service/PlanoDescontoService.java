package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.PlanoDescontoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.PlanoDesconto}.
 */
public interface PlanoDescontoService {
    /**
     * Save a planoDesconto.
     *
     * @param planoDescontoDTO the entity to save.
     * @return the persisted entity.
     */
    PlanoDescontoDTO save(PlanoDescontoDTO planoDescontoDTO);

    /**
     * Updates a planoDesconto.
     *
     * @param planoDescontoDTO the entity to update.
     * @return the persisted entity.
     */
    PlanoDescontoDTO update(PlanoDescontoDTO planoDescontoDTO);

    /**
     * Partially updates a planoDesconto.
     *
     * @param planoDescontoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlanoDescontoDTO> partialUpdate(PlanoDescontoDTO planoDescontoDTO);

    /**
     * Get all the planoDescontos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoDescontoDTO> findAll(Pageable pageable);

    /**
     * Get all the planoDescontos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoDescontoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" planoDesconto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanoDescontoDTO> findOne(Long id);

    /**
     * Delete the "id" planoDesconto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
