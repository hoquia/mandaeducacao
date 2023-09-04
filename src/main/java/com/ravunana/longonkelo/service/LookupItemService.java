package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.LookupItem}.
 */
public interface LookupItemService {
    /**
     * Save a lookupItem.
     *
     * @param lookupItemDTO the entity to save.
     * @return the persisted entity.
     */
    LookupItemDTO save(LookupItemDTO lookupItemDTO);

    /**
     * Updates a lookupItem.
     *
     * @param lookupItemDTO the entity to update.
     * @return the persisted entity.
     */
    LookupItemDTO update(LookupItemDTO lookupItemDTO);

    /**
     * Partially updates a lookupItem.
     *
     * @param lookupItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LookupItemDTO> partialUpdate(LookupItemDTO lookupItemDTO);

    /**
     * Get all the lookupItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LookupItemDTO> findAll(Pageable pageable);

    /**
     * Get all the lookupItems with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LookupItemDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" lookupItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LookupItemDTO> findOne(Long id);

    /**
     * Delete the "id" lookupItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
