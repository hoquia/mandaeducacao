package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.LookupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Lookup}.
 */
public interface LookupService {
    /**
     * Save a lookup.
     *
     * @param lookupDTO the entity to save.
     * @return the persisted entity.
     */
    LookupDTO save(LookupDTO lookupDTO);

    /**
     * Updates a lookup.
     *
     * @param lookupDTO the entity to update.
     * @return the persisted entity.
     */
    LookupDTO update(LookupDTO lookupDTO);

    /**
     * Partially updates a lookup.
     *
     * @param lookupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LookupDTO> partialUpdate(LookupDTO lookupDTO);

    /**
     * Get all the lookups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LookupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" lookup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LookupDTO> findOne(Long id);

    /**
     * Delete the "id" lookup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
