package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.SerieDocumentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.SerieDocumento}.
 */
public interface SerieDocumentoService {
    /**
     * Save a serieDocumento.
     *
     * @param serieDocumentoDTO the entity to save.
     * @return the persisted entity.
     */
    SerieDocumentoDTO save(SerieDocumentoDTO serieDocumentoDTO);

    /**
     * Updates a serieDocumento.
     *
     * @param serieDocumentoDTO the entity to update.
     * @return the persisted entity.
     */
    SerieDocumentoDTO update(SerieDocumentoDTO serieDocumentoDTO);

    /**
     * Partially updates a serieDocumento.
     *
     * @param serieDocumentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SerieDocumentoDTO> partialUpdate(SerieDocumentoDTO serieDocumentoDTO);

    /**
     * Get all the serieDocumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SerieDocumentoDTO> findAll(Pageable pageable);

    /**
     * Get all the serieDocumentos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SerieDocumentoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" serieDocumento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SerieDocumentoDTO> findOne(Long id);

    /**
     * Delete the "id" serieDocumento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
