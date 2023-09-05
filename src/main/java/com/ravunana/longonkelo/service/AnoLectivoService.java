package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.AnoLectivoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.AnoLectivo}.
 */
public interface AnoLectivoService {
    /**
     * Save a anoLectivo.
     *
     * @param anoLectivoDTO the entity to save.
     * @return the persisted entity.
     */
    AnoLectivoDTO save(AnoLectivoDTO anoLectivoDTO);

    /**
     * Updates a anoLectivo.
     *
     * @param anoLectivoDTO the entity to update.
     * @return the persisted entity.
     */
    AnoLectivoDTO update(AnoLectivoDTO anoLectivoDTO);

    /**
     * Partially updates a anoLectivo.
     *
     * @param anoLectivoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnoLectivoDTO> partialUpdate(AnoLectivoDTO anoLectivoDTO);

    /**
     * Get all the anoLectivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnoLectivoDTO> findAll(Pageable pageable);

    /**
     * Get all the anoLectivos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnoLectivoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" anoLectivo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnoLectivoDTO> findOne(Long id);

    /**
     * Delete the "id" anoLectivo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    AnoLectivoDTO getAnoLectivoActual();
}
