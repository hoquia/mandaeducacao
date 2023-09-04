package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.DocumentoComercialDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.DocumentoComercial}.
 */
public interface DocumentoComercialService {
    /**
     * Save a documentoComercial.
     *
     * @param documentoComercialDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentoComercialDTO save(DocumentoComercialDTO documentoComercialDTO);

    /**
     * Updates a documentoComercial.
     *
     * @param documentoComercialDTO the entity to update.
     * @return the persisted entity.
     */
    DocumentoComercialDTO update(DocumentoComercialDTO documentoComercialDTO);

    /**
     * Partially updates a documentoComercial.
     *
     * @param documentoComercialDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocumentoComercialDTO> partialUpdate(DocumentoComercialDTO documentoComercialDTO);

    /**
     * Get all the documentoComercials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentoComercialDTO> findAll(Pageable pageable);

    /**
     * Get all the documentoComercials with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentoComercialDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" documentoComercial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentoComercialDTO> findOne(Long id);

    /**
     * Delete the "id" documentoComercial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
