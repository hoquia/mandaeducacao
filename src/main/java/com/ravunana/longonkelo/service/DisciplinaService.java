package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.DisciplinaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Disciplina}.
 */
public interface DisciplinaService {
    /**
     * Save a disciplina.
     *
     * @param disciplinaDTO the entity to save.
     * @return the persisted entity.
     */
    DisciplinaDTO save(DisciplinaDTO disciplinaDTO);

    /**
     * Updates a disciplina.
     *
     * @param disciplinaDTO the entity to update.
     * @return the persisted entity.
     */
    DisciplinaDTO update(DisciplinaDTO disciplinaDTO);

    /**
     * Partially updates a disciplina.
     *
     * @param disciplinaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DisciplinaDTO> partialUpdate(DisciplinaDTO disciplinaDTO);

    /**
     * Get all the disciplinas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisciplinaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" disciplina.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DisciplinaDTO> findOne(Long id);

    /**
     * Delete the "id" disciplina.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
