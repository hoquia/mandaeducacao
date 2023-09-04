package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.AnexoDiscenteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.AnexoDiscente}.
 */
public interface AnexoDiscenteService {
    /**
     * Save a anexoDiscente.
     *
     * @param anexoDiscenteDTO the entity to save.
     * @return the persisted entity.
     */
    AnexoDiscenteDTO save(AnexoDiscenteDTO anexoDiscenteDTO);

    /**
     * Updates a anexoDiscente.
     *
     * @param anexoDiscenteDTO the entity to update.
     * @return the persisted entity.
     */
    AnexoDiscenteDTO update(AnexoDiscenteDTO anexoDiscenteDTO);

    /**
     * Partially updates a anexoDiscente.
     *
     * @param anexoDiscenteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnexoDiscenteDTO> partialUpdate(AnexoDiscenteDTO anexoDiscenteDTO);

    /**
     * Get all the anexoDiscentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnexoDiscenteDTO> findAll(Pageable pageable);

    /**
     * Get all the anexoDiscentes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnexoDiscenteDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" anexoDiscente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnexoDiscenteDTO> findOne(Long id);

    /**
     * Delete the "id" anexoDiscente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
