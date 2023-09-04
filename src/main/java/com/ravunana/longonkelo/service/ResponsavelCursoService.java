package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ResponsavelCursoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.ResponsavelCurso}.
 */
public interface ResponsavelCursoService {
    /**
     * Save a responsavelCurso.
     *
     * @param responsavelCursoDTO the entity to save.
     * @return the persisted entity.
     */
    ResponsavelCursoDTO save(ResponsavelCursoDTO responsavelCursoDTO);

    /**
     * Updates a responsavelCurso.
     *
     * @param responsavelCursoDTO the entity to update.
     * @return the persisted entity.
     */
    ResponsavelCursoDTO update(ResponsavelCursoDTO responsavelCursoDTO);

    /**
     * Partially updates a responsavelCurso.
     *
     * @param responsavelCursoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResponsavelCursoDTO> partialUpdate(ResponsavelCursoDTO responsavelCursoDTO);

    /**
     * Get all the responsavelCursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelCursoDTO> findAll(Pageable pageable);

    /**
     * Get all the responsavelCursos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelCursoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" responsavelCurso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponsavelCursoDTO> findOne(Long id);

    /**
     * Delete the "id" responsavelCurso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
