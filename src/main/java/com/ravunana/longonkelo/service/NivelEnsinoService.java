package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.NivelEnsinoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.NivelEnsino}.
 */
public interface NivelEnsinoService {
    /**
     * Save a nivelEnsino.
     *
     * @param nivelEnsinoDTO the entity to save.
     * @return the persisted entity.
     */
    NivelEnsinoDTO save(NivelEnsinoDTO nivelEnsinoDTO);

    /**
     * Updates a nivelEnsino.
     *
     * @param nivelEnsinoDTO the entity to update.
     * @return the persisted entity.
     */
    NivelEnsinoDTO update(NivelEnsinoDTO nivelEnsinoDTO);

    /**
     * Partially updates a nivelEnsino.
     *
     * @param nivelEnsinoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NivelEnsinoDTO> partialUpdate(NivelEnsinoDTO nivelEnsinoDTO);

    /**
     * Get all the nivelEnsinos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NivelEnsinoDTO> findAll(Pageable pageable);

    /**
     * Get all the nivelEnsinos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NivelEnsinoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" nivelEnsino.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NivelEnsinoDTO> findOne(Long id);

    /**
     * Delete the "id" nivelEnsino.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
