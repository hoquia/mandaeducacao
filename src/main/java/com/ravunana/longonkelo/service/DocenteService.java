package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.DocenteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Docente}.
 */
public interface DocenteService {
    /**
     * Save a docente.
     *
     * @param docenteDTO the entity to save.
     * @return the persisted entity.
     */
    DocenteDTO save(DocenteDTO docenteDTO);

    /**
     * Updates a docente.
     *
     * @param docenteDTO the entity to update.
     * @return the persisted entity.
     */
    DocenteDTO update(DocenteDTO docenteDTO);

    /**
     * Partially updates a docente.
     *
     * @param docenteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocenteDTO> partialUpdate(DocenteDTO docenteDTO);

    /**
     * Get all the docentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocenteDTO> findAll(Pageable pageable);

    /**
     * Get all the docentes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocenteDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" docente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocenteDTO> findOne(Long id);

    /**
     * Delete the "id" docente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
