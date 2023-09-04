package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.OcorrenciaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Ocorrencia}.
 */
public interface OcorrenciaService {
    /**
     * Save a ocorrencia.
     *
     * @param ocorrenciaDTO the entity to save.
     * @return the persisted entity.
     */
    OcorrenciaDTO save(OcorrenciaDTO ocorrenciaDTO);

    /**
     * Updates a ocorrencia.
     *
     * @param ocorrenciaDTO the entity to update.
     * @return the persisted entity.
     */
    OcorrenciaDTO update(OcorrenciaDTO ocorrenciaDTO);

    /**
     * Partially updates a ocorrencia.
     *
     * @param ocorrenciaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OcorrenciaDTO> partialUpdate(OcorrenciaDTO ocorrenciaDTO);

    /**
     * Get all the ocorrencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OcorrenciaDTO> findAll(Pageable pageable);

    /**
     * Get all the ocorrencias with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OcorrenciaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ocorrencia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OcorrenciaDTO> findOne(Long id);

    /**
     * Delete the "id" ocorrencia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
