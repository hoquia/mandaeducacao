package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.NotasGeralDisciplinaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.NotasGeralDisciplina}.
 */
public interface NotasGeralDisciplinaService {
    /**
     * Save a notasGeralDisciplina.
     *
     * @param notasGeralDisciplinaDTO the entity to save.
     * @return the persisted entity.
     */
    NotasGeralDisciplinaDTO save(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO);

    /**
     * Updates a notasGeralDisciplina.
     *
     * @param notasGeralDisciplinaDTO the entity to update.
     * @return the persisted entity.
     */
    NotasGeralDisciplinaDTO update(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO);

    /**
     * Partially updates a notasGeralDisciplina.
     *
     * @param notasGeralDisciplinaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NotasGeralDisciplinaDTO> partialUpdate(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO);

    /**
     * Get all the notasGeralDisciplinas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotasGeralDisciplinaDTO> findAll(Pageable pageable);

    /**
     * Get all the notasGeralDisciplinas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotasGeralDisciplinaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" notasGeralDisciplina.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotasGeralDisciplinaDTO> findOne(Long id);

    /**
     * Delete the "id" notasGeralDisciplina.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    // matricula-periodoLectivo-disciplina
    String getChaveComposta(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO);

    Double calcularMediaFinalDisciplina(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO);
}
