package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ProcessoSelectivoMatriculaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula}.
 */
public interface ProcessoSelectivoMatriculaService {
    /**
     * Save a processoSelectivoMatricula.
     *
     * @param processoSelectivoMatriculaDTO the entity to save.
     * @return the persisted entity.
     */
    ProcessoSelectivoMatriculaDTO save(ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO);

    /**
     * Updates a processoSelectivoMatricula.
     *
     * @param processoSelectivoMatriculaDTO the entity to update.
     * @return the persisted entity.
     */
    ProcessoSelectivoMatriculaDTO update(ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO);

    /**
     * Partially updates a processoSelectivoMatricula.
     *
     * @param processoSelectivoMatriculaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProcessoSelectivoMatriculaDTO> partialUpdate(ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO);

    /**
     * Get all the processoSelectivoMatriculas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessoSelectivoMatriculaDTO> findAll(Pageable pageable);

    /**
     * Get all the processoSelectivoMatriculas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessoSelectivoMatriculaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" processoSelectivoMatricula.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcessoSelectivoMatriculaDTO> findOne(Long id);

    /**
     * Delete the "id" processoSelectivoMatricula.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
