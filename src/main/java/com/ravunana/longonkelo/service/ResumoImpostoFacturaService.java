package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ResumoImpostoFacturaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.ResumoImpostoFactura}.
 */
public interface ResumoImpostoFacturaService {
    /**
     * Save a resumoImpostoFactura.
     *
     * @param resumoImpostoFacturaDTO the entity to save.
     * @return the persisted entity.
     */
    ResumoImpostoFacturaDTO save(ResumoImpostoFacturaDTO resumoImpostoFacturaDTO);

    /**
     * Updates a resumoImpostoFactura.
     *
     * @param resumoImpostoFacturaDTO the entity to update.
     * @return the persisted entity.
     */
    ResumoImpostoFacturaDTO update(ResumoImpostoFacturaDTO resumoImpostoFacturaDTO);

    /**
     * Partially updates a resumoImpostoFactura.
     *
     * @param resumoImpostoFacturaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResumoImpostoFacturaDTO> partialUpdate(ResumoImpostoFacturaDTO resumoImpostoFacturaDTO);

    /**
     * Get all the resumoImpostoFacturas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResumoImpostoFacturaDTO> findAll(Pageable pageable);

    /**
     * Get all the resumoImpostoFacturas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResumoImpostoFacturaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" resumoImpostoFactura.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResumoImpostoFacturaDTO> findOne(Long id);

    /**
     * Delete the "id" resumoImpostoFactura.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
