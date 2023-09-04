package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.SequenciaDocumentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.SequenciaDocumento}.
 */
public interface SequenciaDocumentoService {
    /**
     * Save a sequenciaDocumento.
     *
     * @param sequenciaDocumentoDTO the entity to save.
     * @return the persisted entity.
     */
    SequenciaDocumentoDTO save(SequenciaDocumentoDTO sequenciaDocumentoDTO);

    /**
     * Updates a sequenciaDocumento.
     *
     * @param sequenciaDocumentoDTO the entity to update.
     * @return the persisted entity.
     */
    SequenciaDocumentoDTO update(SequenciaDocumentoDTO sequenciaDocumentoDTO);

    /**
     * Partially updates a sequenciaDocumento.
     *
     * @param sequenciaDocumentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SequenciaDocumentoDTO> partialUpdate(SequenciaDocumentoDTO sequenciaDocumentoDTO);

    /**
     * Get all the sequenciaDocumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SequenciaDocumentoDTO> findAll(Pageable pageable);

    /**
     * Get all the sequenciaDocumentos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SequenciaDocumentoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" sequenciaDocumento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SequenciaDocumentoDTO> findOne(Long id);

    /**
     * Delete the "id" sequenciaDocumento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
