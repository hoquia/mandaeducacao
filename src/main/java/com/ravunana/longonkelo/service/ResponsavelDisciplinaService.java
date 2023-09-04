package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ResponsavelDisciplinaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.ResponsavelDisciplina}.
 */
public interface ResponsavelDisciplinaService {
    /**
     * Save a responsavelDisciplina.
     *
     * @param responsavelDisciplinaDTO the entity to save.
     * @return the persisted entity.
     */
    ResponsavelDisciplinaDTO save(ResponsavelDisciplinaDTO responsavelDisciplinaDTO);

    /**
     * Updates a responsavelDisciplina.
     *
     * @param responsavelDisciplinaDTO the entity to update.
     * @return the persisted entity.
     */
    ResponsavelDisciplinaDTO update(ResponsavelDisciplinaDTO responsavelDisciplinaDTO);

    /**
     * Partially updates a responsavelDisciplina.
     *
     * @param responsavelDisciplinaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResponsavelDisciplinaDTO> partialUpdate(ResponsavelDisciplinaDTO responsavelDisciplinaDTO);

    /**
     * Get all the responsavelDisciplinas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelDisciplinaDTO> findAll(Pageable pageable);

    /**
     * Get all the responsavelDisciplinas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelDisciplinaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" responsavelDisciplina.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponsavelDisciplinaDTO> findOne(Long id);

    /**
     * Delete the "id" responsavelDisciplina.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
