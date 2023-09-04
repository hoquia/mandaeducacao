package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.InstituicaoEnsinoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.InstituicaoEnsino}.
 */
public interface InstituicaoEnsinoService {
    /**
     * Save a instituicaoEnsino.
     *
     * @param instituicaoEnsinoDTO the entity to save.
     * @return the persisted entity.
     */
    InstituicaoEnsinoDTO save(InstituicaoEnsinoDTO instituicaoEnsinoDTO);

    /**
     * Updates a instituicaoEnsino.
     *
     * @param instituicaoEnsinoDTO the entity to update.
     * @return the persisted entity.
     */
    InstituicaoEnsinoDTO update(InstituicaoEnsinoDTO instituicaoEnsinoDTO);

    /**
     * Partially updates a instituicaoEnsino.
     *
     * @param instituicaoEnsinoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InstituicaoEnsinoDTO> partialUpdate(InstituicaoEnsinoDTO instituicaoEnsinoDTO);

    /**
     * Get all the instituicaoEnsinos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InstituicaoEnsinoDTO> findAll(Pageable pageable);

    /**
     * Get all the instituicaoEnsinos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InstituicaoEnsinoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" instituicaoEnsino.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InstituicaoEnsinoDTO> findOne(Long id);

    /**
     * Delete the "id" instituicaoEnsino.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
