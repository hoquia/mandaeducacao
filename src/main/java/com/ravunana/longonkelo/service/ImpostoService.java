package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ImpostoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Imposto}.
 */
public interface ImpostoService {
    /**
     * Save a imposto.
     *
     * @param impostoDTO the entity to save.
     * @return the persisted entity.
     */
    ImpostoDTO save(ImpostoDTO impostoDTO);

    /**
     * Updates a imposto.
     *
     * @param impostoDTO the entity to update.
     * @return the persisted entity.
     */
    ImpostoDTO update(ImpostoDTO impostoDTO);

    /**
     * Partially updates a imposto.
     *
     * @param impostoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ImpostoDTO> partialUpdate(ImpostoDTO impostoDTO);

    /**
     * Get all the impostos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImpostoDTO> findAll(Pageable pageable);

    /**
     * Get all the impostos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImpostoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" imposto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImpostoDTO> findOne(Long id);

    /**
     * Delete the "id" imposto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
