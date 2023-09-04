package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.PrecoEmolumentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.PrecoEmolumento}.
 */
public interface PrecoEmolumentoService {
    /**
     * Save a precoEmolumento.
     *
     * @param precoEmolumentoDTO the entity to save.
     * @return the persisted entity.
     */
    PrecoEmolumentoDTO save(PrecoEmolumentoDTO precoEmolumentoDTO);

    /**
     * Updates a precoEmolumento.
     *
     * @param precoEmolumentoDTO the entity to update.
     * @return the persisted entity.
     */
    PrecoEmolumentoDTO update(PrecoEmolumentoDTO precoEmolumentoDTO);

    /**
     * Partially updates a precoEmolumento.
     *
     * @param precoEmolumentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PrecoEmolumentoDTO> partialUpdate(PrecoEmolumentoDTO precoEmolumentoDTO);

    /**
     * Get all the precoEmolumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrecoEmolumentoDTO> findAll(Pageable pageable);

    /**
     * Get all the precoEmolumentos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrecoEmolumentoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" precoEmolumento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrecoEmolumentoDTO> findOne(Long id);

    /**
     * Delete the "id" precoEmolumento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
