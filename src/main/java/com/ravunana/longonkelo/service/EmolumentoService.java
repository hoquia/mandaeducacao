package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.EmolumentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Emolumento}.
 */
public interface EmolumentoService {
    /**
     * Save a emolumento.
     *
     * @param emolumentoDTO the entity to save.
     * @return the persisted entity.
     */
    EmolumentoDTO save(EmolumentoDTO emolumentoDTO);

    /**
     * Updates a emolumento.
     *
     * @param emolumentoDTO the entity to update.
     * @return the persisted entity.
     */
    EmolumentoDTO update(EmolumentoDTO emolumentoDTO);

    /**
     * Partially updates a emolumento.
     *
     * @param emolumentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmolumentoDTO> partialUpdate(EmolumentoDTO emolumentoDTO);

    /**
     * Get all the emolumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmolumentoDTO> findAll(Pageable pageable);

    /**
     * Get all the emolumentos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmolumentoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" emolumento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmolumentoDTO> findOne(Long id);

    /**
     * Delete the "id" emolumento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
