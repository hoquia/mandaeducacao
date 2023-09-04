package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ResponsavelTurmaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.ResponsavelTurma}.
 */
public interface ResponsavelTurmaService {
    /**
     * Save a responsavelTurma.
     *
     * @param responsavelTurmaDTO the entity to save.
     * @return the persisted entity.
     */
    ResponsavelTurmaDTO save(ResponsavelTurmaDTO responsavelTurmaDTO);

    /**
     * Updates a responsavelTurma.
     *
     * @param responsavelTurmaDTO the entity to update.
     * @return the persisted entity.
     */
    ResponsavelTurmaDTO update(ResponsavelTurmaDTO responsavelTurmaDTO);

    /**
     * Partially updates a responsavelTurma.
     *
     * @param responsavelTurmaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResponsavelTurmaDTO> partialUpdate(ResponsavelTurmaDTO responsavelTurmaDTO);

    /**
     * Get all the responsavelTurmas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelTurmaDTO> findAll(Pageable pageable);

    /**
     * Get all the responsavelTurmas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelTurmaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" responsavelTurma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponsavelTurmaDTO> findOne(Long id);

    /**
     * Delete the "id" responsavelTurma.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
