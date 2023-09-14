package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.NotasPeriodicaDisciplinaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina}.
 */
public interface NotasPeriodicaDisciplinaService {
    /**
     * Save a notasPeriodicaDisciplina.
     *
     * @param notasPeriodicaDisciplinaDTO the entity to save.
     * @return the persisted entity.
     */
    NotasPeriodicaDisciplinaDTO save(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO);

    /**
     * Updates a notasPeriodicaDisciplina.
     *
     * @param notasPeriodicaDisciplinaDTO the entity to update.
     * @return the persisted entity.
     */
    NotasPeriodicaDisciplinaDTO update(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO);

    /**
     * Partially updates a notasPeriodicaDisciplina.
     *
     * @param notasPeriodicaDisciplinaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NotasPeriodicaDisciplinaDTO> partialUpdate(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO);

    /**
     * Get all the notasPeriodicaDisciplinas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotasPeriodicaDisciplinaDTO> findAll(Pageable pageable);

    /**
     * Get all the notasPeriodicaDisciplinas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotasPeriodicaDisciplinaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" notasPeriodicaDisciplina.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotasPeriodicaDisciplinaDTO> findOne(Long id);

    /**
     * Delete the "id" notasPeriodicaDisciplina.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    String getChaveComposta(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO);

    Double calcularMedia(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO);
}
