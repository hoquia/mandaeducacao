package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.TurmaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Turma}.
 */
public interface TurmaService {
    /**
     * Save a turma.
     *
     * @param turmaDTO the entity to save.
     * @return the persisted entity.
     */
    TurmaDTO save(TurmaDTO turmaDTO);

    /**
     * Updates a turma.
     *
     * @param turmaDTO the entity to update.
     * @return the persisted entity.
     */
    TurmaDTO update(TurmaDTO turmaDTO);

    /**
     * Partially updates a turma.
     *
     * @param turmaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TurmaDTO> partialUpdate(TurmaDTO turmaDTO);

    /**
     * Get all the turmas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TurmaDTO> findAll(Pageable pageable);

    /**
     * Get all the turmas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TurmaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" turma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TurmaDTO> findOne(Long id);

    /**
     * Delete the "id" turma.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
